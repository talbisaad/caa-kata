package com.caa.kata.service.invoice;

import com.caa.kata.model.invoice.Invoice;
import com.caa.kata.service.invoice.calculation.InvoiceCalculatorService;
import com.caa.kata.service.invoice.generation.InvoiceGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class InvoiceServiceTest {
    private InvoiceService invoiceService;
    private InvoiceGeneratorService invoiceOutputDisplayService;
    private InvoiceGeneratorService invoiceInputDisplayService;
    private InvoiceCalculatorService invoiceTaxAmountCalculator;
    private InvoiceCalculatorService invoiceTotalAmountCalculatorService;
    private Invoice invoice;

    @BeforeEach
    void setUp() {
        invoiceOutputDisplayService = mock(InvoiceGeneratorService.class);
        invoiceInputDisplayService = mock(InvoiceGeneratorService.class);
        invoiceTaxAmountCalculator = mock(InvoiceCalculatorService.class);
        invoiceTotalAmountCalculatorService = mock(InvoiceCalculatorService.class);
        invoiceService = new InvoiceService(invoiceOutputDisplayService, invoiceInputDisplayService, invoiceTaxAmountCalculator, invoiceTotalAmountCalculatorService);
        invoice = mock(Invoice.class);
    }

    @Test
    void calculateTotalTaxAmount_nominal_setsTaxAmount() {
        when(invoiceTaxAmountCalculator.calculateAmount(invoice)).thenReturn(BigDecimal.valueOf(50));
        invoiceService.calculateTotalTaxAmount(invoice);
        verify(invoice).setTaxAmount(BigDecimal.valueOf(50));
    }

    @Test
    void calculateTotalTtcAmount_nominal_setsTotalTtcAmount() {
        when(invoiceTotalAmountCalculatorService.calculateAmount(invoice)).thenReturn(BigDecimal.valueOf(200));
        invoiceService.calculateTotalTtcAmount(invoice);
        verify(invoice).setTotalTtcAmount(BigDecimal.valueOf(200));
    }

    @Test
    void displayInvoiceInput_nominal_callsDisplay() {
        invoiceService.generateInvoiceInput(invoice);
        verify(invoiceInputDisplayService).generateInvoice(invoice);
    }

    @Test
    void displayInvoiceOutput_nominal_callsDisplay() {
        invoiceService.generateInvoiceOutput(invoice);
        verify(invoiceOutputDisplayService).generateInvoice(invoice);
    }
}