package com.caa.kata.service.invoice;

import com.caa.kata.model.invoice.Invoice;
import com.caa.kata.service.invoice.calculation.InvoiceCalculatorService;
import com.caa.kata.service.invoice.display.InvoiceDisplayService;


public class InvoiceService implements IInvoiceService {

    private final InvoiceDisplayService invoiceOutputDisplayService;
    private final InvoiceDisplayService invoiceInputDisplayService ;
    private final InvoiceCalculatorService invoiceTaxAmountCalculator;
    private final InvoiceCalculatorService invoiceTotalAmountCalculatorService;

    public InvoiceService(InvoiceDisplayService invoiceOutputDisplayService, InvoiceDisplayService invoiceInputDisplayService, InvoiceCalculatorService invoiceTaxAmountCalculator, InvoiceCalculatorService invoiceTotalAmountCalculatorService) {
        this.invoiceOutputDisplayService = invoiceOutputDisplayService;
        this.invoiceInputDisplayService = invoiceInputDisplayService;
        this.invoiceTaxAmountCalculator = invoiceTaxAmountCalculator;
        this.invoiceTotalAmountCalculatorService = invoiceTotalAmountCalculatorService;
    }
    /**
     * Calculate Invoice Total taxes Amount
     * @param invoice
     */
    @Override
    public void calculateTotalTaxAmount(Invoice invoice) {
        invoice.setTaxAmount(invoiceTaxAmountCalculator.calculateAmount(invoice));
    }
    /**
     * Calculate Invoice TTC Amount
     * @param invoice
     */
    @Override
    public void calculateTotalTtcAmount(Invoice invoice) {
         invoice.setTotalTtcAmount(invoiceTotalAmountCalculatorService.calculateAmount(invoice));
    }
    /**
     * Display invoice's input data
     * @param invoice
     */
    @Override
    public void displayInvoiceInput(Invoice invoice) {
        invoiceInputDisplayService.display(invoice);
    }

    /**
     * Display invoice's output data
     * @param invoice
     */
    @Override
    public void displayInvoiceOutput(Invoice invoice) {
         invoiceOutputDisplayService.display(invoice);
    }


}
