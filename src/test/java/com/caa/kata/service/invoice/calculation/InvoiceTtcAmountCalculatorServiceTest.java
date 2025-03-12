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

class InvoiceTtcAmountCalculatorServiceTest {
    private InvoiceTtcAmountCalculatorService invoiceTtcAmountCalculatorService;
    private Invoice invoice;
    private Command command1;
    private Command command2;

    @BeforeEach
    void setUp() {
        invoiceTtcAmountCalculatorService = new InvoiceTtcAmountCalculatorService();
        invoice = mock(Invoice.class);
        command1 = mock(Command.class);
        command2 = mock(Command.class);
    }

    @Test
    void calculateTtcAmount_validInvoice_returnsSumAmount() {
        when(invoice.getCommands()).thenReturn(Arrays.asList(command1, command2));
        when(command1.getTtcAmount()).thenReturn(BigDecimal.valueOf(300));
        when(command2.getTtcAmount()).thenReturn(BigDecimal.valueOf(80));

        BigDecimal result = invoiceTtcAmountCalculatorService.calculateAmount(invoice);

        assertEquals(BigDecimal.valueOf(380), result);
    }

    @Test
    void calculateTtcAmount_nullInvoice_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> invoiceTtcAmountCalculatorService.calculateAmount(null));
    }

    @Test
    void calculateTtcAmount_emptyCommands_returnsZero() {
        when(invoice.getCommands()).thenReturn(Arrays.asList());

        BigDecimal result = invoiceTtcAmountCalculatorService.calculateAmount(invoice);

        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void calculateTtcAmount_exceptionThrown_throwsTechnicalException() {
        when(invoice.getCommands()).thenThrow(new RuntimeException("Unexpected error"));

        assertThrows(TechnicalException.class, () -> invoiceTtcAmountCalculatorService.calculateAmount(invoice));
    }
}