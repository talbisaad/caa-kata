package com.caa.kata.service.invoice;

import com.caa.kata.model.invoice.Invoice;

public interface IInvoiceService {

    /**
     * Calculate Invoice Total taxes Amount
     * @param invoice
     */
    void calculateTotalTaxAmount(Invoice invoice);
    /**
     * Calculate Invoice TTC Amount
     * @param invoice
     */
    void calculateTotalTtcAmount(Invoice invoice);

    /**
     * Generate invoice's input data
     * @param invoice
     */
    String generateInvoiceInput(Invoice invoice);
    /**
     * Generate invoice's output data
     * @param invoice
     */
    String generateInvoiceOutput(Invoice invoice);


}
