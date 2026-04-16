package io.github.lexanthon.invoiceparser.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.github.lexanthon.invoiceparser.dao.InvoiceFileRepo;

@Service
public class InvoiceStatusService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceStatusService.class);
    private static final int STATUS_PARSED = 2;
    private static final int STATUS_FAILED = 3;
    private static final int STATUS_PROCESSING = 4;

    @Autowired
    private InvoiceFileRepo invoiceFileRepo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markParsed(Long invoiceFileId) {
        int updated = invoiceFileRepo.markParsedIfCurrent(invoiceFileId, STATUS_PARSED, STATUS_PROCESSING);
        if (updated != 1) {
            logger.warn("markParsed skipped for invoiceFileId={} because row was not in PROCESSING state", invoiceFileId);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markFailed(Long invoiceFileId, Exception e) {
        String message = rootMessage(e);
        int updated = invoiceFileRepo.markFailedIfCurrent(invoiceFileId, STATUS_FAILED, STATUS_PROCESSING, truncate(message, 1000));
        if (updated != 1) {
            logger.warn("markFailed skipped for invoiceFileId={} because row was not in PROCESSING state", invoiceFileId);
        }
    }

    private String rootMessage(Throwable t) {
        Throwable current = t;
        while (current.getCause() != null) {
            current = current.getCause();
        }
        return current.getMessage() != null ? current.getMessage() : current.toString();
    }

    private String truncate(String value, int maxLen) {
        if (value == null || value.length() <= maxLen) {
            return value;
        }
        return value.substring(0, maxLen);
    }
}
