package com.caa.kata.service.invoice.generation;

import com.caa.kata.utils.constant.Constants;
import com.caa.kata.model.invoice.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceOutputGeneratorService extends InvoiceGeneratorService {
    /**
     * Generate invoice's output data
     *
     * @param invoice
     */
    @Override
    public String generateInvoice(Invoice invoice) {
        StringBuilder output = new StringBuilder();
        output.append(Constants.OUTPUT).append("\n");
        invoice.getCommands().forEach(command ->
                output.append(buildInvoiceCommandLine(command))
                        .append(Constants.COLON_MARK)
                        .append(command.getTtcAmount())
                        .append(Constants.EURO)
                        .append("\n")
        );
        output.append(String.format(Constants.TAX_AMOUNT_FORMAT, invoice.getTaxAmount())).append("\n");
        output.append(String.format(Constants.TOTAL_AMOUNT_FORMAT, invoice.getTotalTtcAmount())).append("\n");
        return output.toString();
    }
}
