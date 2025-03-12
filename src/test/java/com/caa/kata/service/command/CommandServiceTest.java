package com.caa.kata.service.command;


import com.caa.kata.exception.TechnicalException;
import com.caa.kata.model.command.Command;
import com.caa.kata.model.invoice.Invoice;
import com.caa.kata.model.product.Product;
import com.caa.kata.model.product.ProductFamily;
import com.caa.kata.service.tax.TaxStrategy;
import com.caa.kata.service.tax.TaxStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommandServiceTest {

    private CommandService commandService;
    private TaxStrategy taxStrategy;
    private TaxStrategyFactory taxStrategyFactory;

    private Command command;


    @BeforeEach
    void setUp(){
        command = mock(Command.class);
        taxStrategyFactory = mock(TaxStrategyFactory.class);
        taxStrategy = mock(TaxStrategy.class);
        commandService = new CommandService(taxStrategyFactory);

    }

    @Test
    void calculateCommandAmounts_nominal(){
        when(command.getProduct()).thenReturn(getProduct());
        when(command.getQuantity()).thenReturn(2);
        when(taxStrategyFactory.getTaxStrategy(any())).thenReturn(taxStrategy);
        when(taxStrategy.calculateTax(any())).thenReturn(new BigDecimal("20.00"));

        commandService.calculateCommandAmounts(command);

        verify(command).setTtcAmount(new BigDecimal("240.00"));
        verify(command).setTaxAmount(new BigDecimal("40.00"));
    }

    @Test
    void calculateCommandAmounts_nullCommand_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> commandService.calculateCommandAmounts(null));
    }
    @Test
    void calculateCommandAmounts_nullProduct_throwsIllegalArgumentException() {
        when(command.getProduct()).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> commandService.calculateCommandAmounts(command));
    }

    @Test
    void calculateCommandAmounts_throwsTechnicalException() {
        when(command.getProduct()).thenReturn(getProduct());
        when(command.getQuantity()).thenThrow(new RuntimeException());
        assertThrows(TechnicalException.class, () -> commandService.calculateCommandAmounts(command));
    }

    @Test
    void purchaseCommand_nominal() {
        Invoice invoice = mock(Invoice.class);
        when(invoice.getCommands()).thenReturn(new ArrayList<>());
        when(command.getProduct()).thenReturn(getProduct());
        when(command.getQuantity()).thenReturn(2);
        when(taxStrategyFactory.getTaxStrategy(any())).thenReturn(taxStrategy);
        when(taxStrategy.calculateTax(any())).thenReturn(new BigDecimal("20.00"));
        commandService.purchaseCommand(invoice, command);
        assertTrue(invoice.getCommands().contains(command));
    }
    @Test
    void purchaseCommand_nullInvoice_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> commandService.purchaseCommand(null, command));
    }
    @Test
    void purchaseCommand_nullCommand_throwsIllegalArgumentException() {
        Invoice invoice = mock(Invoice.class);
        assertThrows(IllegalArgumentException.class, () -> commandService.purchaseCommand(invoice, null));
    }

    private static Product getProduct() {
        return new Product("Book", new BigDecimal("100.00"), ProductFamily.BOOK, false);
    }

}