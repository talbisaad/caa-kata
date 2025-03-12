package com.caa.kata.service.invoice.calculation;

import com.caa.kata.exception.TechnicalException;
import com.caa.kata.model.command.Command;
import com.caa.kata.model.invoice.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InvoiceTaxAmountCalculatorTest {
    private InvoiceTaxAmountCalculator invoiceTaxAmountCalculator;
    private Invoice invoice;
    private Command command1;
    private Command command2;

    @BeforeEach
    void setUp() {
        invoiceTaxAmountCalculator = new InvoiceTaxAmountCalculator();
        invoice = mock(Invoice.class);
        command1 = mock(Command.class);
        command2 = mock(Command.class);
    }

    @Test
    void calculateTaxAmount_validInvoice_returnsSumAmount() {
        when(invoice.getCommands()).thenReturn(Arrays.asList(command1, command2));
        when(command1.getTaxAmount()).thenReturn(BigDecimal.valueOf(15));
        when(command2.getTaxAmount()).thenReturn(BigDecimal.valueOf(40));

        BigDecimal result = invoiceTaxAmountCalculator.calculateAmount(invoice);

        assertEquals(BigDecimal.valueOf(55), result);
    }

    @Test
    void calculateTaxAmount_nullInvoice_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> invoiceTaxAmountCalculator.calculateAmount(null));
    }

    @Test
    void calculateTaxAmount_emptyCommands_returnsZero() {
        when(invoice.getCommands()).thenReturn(Arrays.asList());

        BigDecimal result = invoiceTaxAmountCalculator.calculateAmount(invoice);

        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void calculateTaxAmount_exceptionThrown_throwsTechnicalException() {
        when(invoice.getCommands()).thenThrow(new RuntimeException("Unexpected error"));

        assertThrows(TechnicalException.class, () -> invoiceTaxAmountCalculator.calculateAmount(invoice));
    }
}