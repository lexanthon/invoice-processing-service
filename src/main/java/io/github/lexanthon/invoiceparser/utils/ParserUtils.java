package io.github.lexanthon.invoiceparser.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.github.lexanthon.invoiceparser.dao.InvoiceFileRepo;
import io.github.lexanthon.invoiceparser.model.InvoiceFile;

@Component
@PropertySource("classpath:application.properties")
public class ParserUtils {

    private static final Logger logger = LoggerFactory.getLogger(ParserUtils.class);
    private static final int STATUS_PENDING = 1;
    private static final int STATUS_PROCESSING = 4;

    @Autowired
    private InvoiceFileRepo invoiceFileRepo;

    @Autowired
    private InvoiceProcessingService invoiceProcessingService;

    @Autowired
    private InvoiceStatusService invoiceStatusService;

    @Scheduled(cron = "${timer}")
    public void checkInvoicesByStatus() {
        List<InvoiceFile> invoiceList = invoiceFileRepo.findInvoicesByStatus(STATUS_PENDING);

        for (InvoiceFile invoiceFile : invoiceList) {
            int claimed = invoiceFileRepo.updateStatusIfCurrent(
                    invoiceFile.getId(),
                    STATUS_PROCESSING,
                    STATUS_PENDING);
            if (claimed != 1) {
                continue;
            }

            if (!hasText(invoiceFile.getXml())) {
                invoiceStatusService.markFailed(invoiceFile.getId(), new IllegalArgumentException("Invoice XML is blank"));
                continue;
            }

            try {
                invoiceProcessingService.processInvoice(invoiceFile);
                invoiceStatusService.markParsed(invoiceFile.getId());
            } catch (Exception e) {
                logger.error("Failed to process invoiceFileId={}", invoiceFile.getId(), e);
                invoiceStatusService.markFailed(invoiceFile.getId(), e);
            }
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
