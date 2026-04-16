package io.github.lexanthon.invoiceparser.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.github.lexanthon.invoiceparser.dao.InvoiceFileRepo;

@Service
public class InvoiceStatusService {

    private static final int STATUS_PARSED = 2;
    private static final int STATUS_FAILED = 3;

    @Autowired
    private InvoiceFileRepo invoiceFileRepo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markParsed(Long invoiceFileId) {
        invoiceFileRepo.markParsed(invoiceFileId, STATUS_PARSED);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markFailed(Long invoiceFileId, Exception e) {
        String message = rootMessage(e);
        invoiceFileRepo.markFailed(invoiceFileId, STATUS_FAILED, truncate(message, 1000));
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
