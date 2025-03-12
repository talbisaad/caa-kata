package com.caa.kata.service.invoice;

import com.caa.kata.model.invoice.Invoice;
import com.caa.kata.service.invoice.calculation.InvoiceCalculatorService;
import com.caa.kata.service.invoice.generation.InvoiceGeneratorService;


public class InvoiceService implements IInvoiceService {

    private final InvoiceGeneratorService invoiceOutputDisplayService;
    private final InvoiceGeneratorService invoiceInputDisplayService;
    private final InvoiceCalculatorService invoiceTaxAmountCalculator;
    private final InvoiceCalculatorService invoiceTotalAmountCalculatorService;

    public InvoiceService(InvoiceGeneratorService invoiceOutputDisplayService, InvoiceGeneratorService invoiceInputDisplayService, InvoiceCalculatorService invoiceTaxAmountCalculator, InvoiceCalculatorService invoiceTotalAmountCalculatorService) {
        this.invoiceOutputDisplayService = invoiceOutputDisplayService;
        this.invoiceInputDisplayService = invoiceInputDisplayService;
        this.invoiceTaxAmountCalculator = invoiceTaxAmountCalculator;
        this.invoiceTotalAmountCalculatorService = invoiceTotalAmountCalculatorService;
    }

    /**
     * Calculate Invoice Total taxes Amount
     *
     * @param invoice
     */
    @Override
    public void calculateTotalTaxAmount(Invoice invoice) {
        invoice.setTaxAmount(invoiceTaxAmountCalculator.calculateAmount(invoice));
    }

    /**
     * Calculate Invoice TTC Amount
     *
     * @param invoice
     */
    @Override
    public void calculateTotalTtcAmount(Invoice invoice) {
        invoice.setTotalTtcAmount(invoiceTotalAmountCalculatorService.calculateAmount(invoice));
    }

    /**
     * Generate invoice's input data
     *
     * @param invoice
     */
    @Override
    public String generateInvoiceInput(Invoice invoice) {
        return invoiceInputDisplayService.generateInvoice(invoice);
    }

    /**
     * Generate invoice's output data
     *
     * @param invoice
     */
    @Override
    public String generateInvoiceOutput(Invoice invoice) {
        return invoiceOutputDisplayService.generateInvoice(invoice);
    }


}
