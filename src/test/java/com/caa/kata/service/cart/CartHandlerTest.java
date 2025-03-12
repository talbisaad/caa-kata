package com.caa.kata.service.cart;

import com.caa.kata.model.command.Command;
import com.caa.kata.model.invoice.Invoice;
import com.caa.kata.model.product.Product;
import com.caa.kata.model.product.ProductFamily;
import com.caa.kata.service.command.CommandService;
import com.caa.kata.service.invoice.InvoiceService;
import com.caa.kata.service.invoice.calculation.InvoiceTaxAmountCalculator;
import com.caa.kata.service.invoice.calculation.InvoiceTtcAmountCalculatorService;
import com.caa.kata.service.invoice.generation.InvoiceInputGeneratorService;
import com.caa.kata.service.invoice.generation.InvoiceOutputGeneratorService;
import com.caa.kata.service.printing.IPrintingService;
import com.caa.kata.service.printing.PrintingService;
import com.caa.kata.service.tax.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartHandlerTest {

    private static final List<TaxStrategy> strategies = List.of(new NormalTaxStrategy(), new NoTaxStrategy(), new ReducedTaxStrategy());
    private static final TaxStrategyFactory taxStrategyFactory = new TaxStrategyFactory(strategies, new ImportedTaxStrategy());
    private static final IPrintingService printingService = new PrintingService();
    @Captor
    private ArgumentCaptor<Invoice> invoiceCaptor;

    @InjectMocks
    private CartHandlerService cartHandler;
    @Spy
    private InvoiceService invoiceService = new InvoiceService(new InvoiceOutputGeneratorService(), new InvoiceInputGeneratorService(), new InvoiceTaxAmountCalculator(), new InvoiceTtcAmountCalculatorService());
    @Spy
    private CommandService commandService = new CommandService(taxStrategyFactory);

    @BeforeEach
    void setup() {
        cartHandler = new CartHandlerService(commandService, invoiceService, printingService);
    }

    @Test
    void shouldHandleCart1Successfully() {

        cartHandler.handleCart(getCommandsCart1());

        verify(invoiceService, times(1)).generateInvoiceInput(invoiceCaptor.capture());

        List<Invoice> capturedInvoices = invoiceCaptor.getAllValues();
        Invoice invoice = capturedInvoices.get(0);
        assertNotNull(invoice);
        assertNotNull(invoice.getCommands());
        assertEquals(3, invoice.getCommands().size());
        assertEquals(new BigDecimal("5.50"), invoice.getTaxAmount());
        assertEquals(new BigDecimal("48.02"), invoice.getTotalTtcAmount());
    }

    @Test
    void shouldHandleCart2Successfully() {

        cartHandler.handleCart(getCommandsCart2());

        verify(invoiceService, times(1)).generateInvoiceInput(invoiceCaptor.capture());

        List<Invoice> capturedInvoices = invoiceCaptor.getAllValues();
        Invoice invoice = capturedInvoices.get(0);
        assertNotNull(invoice);
        assertNotNull(invoice.getCommands());
        assertEquals(2, invoice.getCommands().size());
        assertEquals(new BigDecimal("36.70"), invoice.getTaxAmount());
        assertEquals(new BigDecimal("199.20"), invoice.getTotalTtcAmount());
    }

    @Test
    void shouldHandleCart3Successfully() {

        cartHandler.handleCart(getCommandsCart3());

        verify(invoiceService, times(1)).generateInvoiceInput(invoiceCaptor.capture());

        List<Invoice> capturedInvoices = invoiceCaptor.getAllValues();
        Invoice invoice = capturedInvoices.get(0);
        assertNotNull(invoice);
        assertNotNull(invoice.getCommands());
        assertEquals(4, invoice.getCommands().size());
        assertEquals(new BigDecimal("19.00"), invoice.getTaxAmount());
        assertEquals(new BigDecimal("145.72"), invoice.getTotalTtcAmount());
    }

    @Test
    void shouldThrowIllegal() {
        assertThrows(IllegalArgumentException.class, () -> cartHandler.handleCart(null));
    }


    private static List<Command> getCommandsCart1() {
        // création des commandes de la factures à partir des produits pour le Panier 1
        return List.of(
                new Command(new Product("livre", BigDecimal.valueOf(12.49), ProductFamily.BOOK, false), 2),
                new Command(new Product("CD", BigDecimal.valueOf(14.99), ProductFamily.OTHER, false), 1),
                new Command(new Product("barres de chocolat", BigDecimal.valueOf(0.85), ProductFamily.BASIC, false), 3));
    }

    private static List<Command> getCommandsCart2() {
        // création des commandes de la factures à partir des produits pour le Panier 2
        return List.of(
                new Command(new Product("barres de chocolat", BigDecimal.valueOf(10), ProductFamily.BASIC, true), 2),
                new Command(new Product("flacons de parfum", BigDecimal.valueOf(47.50), ProductFamily.OTHER, true), 3));
    }

    private static List<Command> getCommandsCart3() {
        // création des commandes de la factures à partir des produits pour le Panier 3
        return List.of(
                new Command(new Product("flacons de parfum", BigDecimal.valueOf(27.99), ProductFamily.OTHER, true), 2),
                new Command(new Product("flacons de parfum", BigDecimal.valueOf(18.99), ProductFamily.OTHER, false), 1),
                new Command(new Product("boîtes de pilules contre la migraine", BigDecimal.valueOf(9.75), ProductFamily.BASIC, false), 3),
                new Command(new Product("boites de chocolat", BigDecimal.valueOf(11.25), ProductFamily.BASIC, true), 2));
    }
}