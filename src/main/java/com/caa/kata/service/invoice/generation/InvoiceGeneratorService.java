package com.caa.kata.service.invoice.generation;

import com.caa.kata.utils.constant.Constants;
import com.caa.kata.model.command.Command;
import com.caa.kata.model.invoice.Invoice;

public abstract class InvoiceGeneratorService {


    /**
     * Generate invoice's input/output data
     * @param invoice
     */
    public abstract String generateInvoice(Invoice invoice);


    /**
     * Display common command line in invoice
     * @param command
     */
    protected String buildInvoiceCommandLine(Command command) {
        return String.format(Constants.COMMAND_LINE_FORMAT, command.getQuantity()
                , command.getProduct().label()
                , command.getProduct().imported() ? Constants.IMPORTED : Constants.EMPTY_STRING
                , command.getProduct().price());
    }

}
