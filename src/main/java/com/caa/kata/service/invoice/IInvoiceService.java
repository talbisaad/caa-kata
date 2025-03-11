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
     * Display invoice's input data
     * @param invoice
     */
    void displayInvoiceInput(Invoice invoice);
    /**
     * Display invoice's output data
     * @param invoice
     */
    void displayInvoiceOutput(Invoice invoice);


}
