package com.caa.kata.service.invoice.display;

import com.caa.kata.utils.constant.Constants;
import com.caa.kata.model.invoice.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceInputDisplayService extends InvoiceDisplayService {
    /**
     * Display invoice's input data
     * @param invoice
     */
    private static final Logger logger = LoggerFactory.getLogger(InvoiceInputDisplayService.class);
    @Override
    public void display(Invoice invoice) {
        logger.info(Constants.INPUT);
        if (invoice.getCommands() != null && !invoice.getCommands().isEmpty()) {
            invoice.getCommands().forEach(command -> logger.info(buildInvoiceCommandLine(command)));
        }
    }
}
