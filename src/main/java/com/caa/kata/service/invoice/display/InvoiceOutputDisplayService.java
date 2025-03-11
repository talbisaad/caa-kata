package com.caa.kata.service.invoice.display;

import com.caa.kata.utils.constant.Constants;
import com.caa.kata.model.invoice.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceOutputDisplayService extends InvoiceDisplayService {
    /**
     * Display invoice's output data
     *
     * @param invoice
     */
    private static final Logger logger = LoggerFactory.getLogger(InvoiceInputDisplayService.class);

    @Override
    public void display(Invoice invoice) {
        logger.info(Constants.OUTPUT);
        invoice.getCommands().forEach(command -> logger.info(buildInvoiceCommandLine(command) + Constants.COLON_MARK + command.getTtcAmount() + Constants.EURO));
        logger.info(String.format(Constants.TAX_AMOUNT_FORMAT, invoice.getTaxAmount()));
        logger.info(String.format(Constants.TOTAL_AMOUNT_FORMAT, invoice.getTotalTtcAmount()));
    }
}
