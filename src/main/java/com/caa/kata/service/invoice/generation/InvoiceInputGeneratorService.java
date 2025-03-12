package com.caa.kata.service.invoice.generation;

import com.caa.kata.utils.constant.Constants;
import com.caa.kata.model.invoice.Invoice;


public class InvoiceInputGeneratorService extends InvoiceGeneratorService {

    /**
     * Generate invoice's input data
     * @param invoice
     */
    @Override
    public String generateInvoice(Invoice invoice) {
        StringBuilder result = new StringBuilder(Constants.INPUT + "\n");
        if (invoice.getCommands() != null && !invoice.getCommands().isEmpty()) {
            invoice.getCommands().forEach(command -> result.append(buildInvoiceCommandLine(command)).append("\n"));
        }
        return result.toString();
    }

}
