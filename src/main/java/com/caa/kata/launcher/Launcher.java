package com.caa.kata.launcher;

import com.caa.kata.model.command.Command;
import com.caa.kata.model.product.Product;
import com.caa.kata.model.product.ProductFamily;
import com.caa.kata.service.command.CommandService;
import com.caa.kata.service.invoice.InvoiceService;
import com.caa.kata.service.invoice.calculation.InvoiceTaxAmountCalculator;
import com.caa.kata.service.invoice.calculation.InvoiceTotalAmountCalculatorService;
import com.caa.kata.service.invoice.display.InvoiceInputDisplayService;
import com.caa.kata.service.invoice.display.InvoiceOutputDisplayService;
import com.caa.kata.service.tax.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * THIS LAUNCHER HAS BEEN SPECIALLY IMPLEMENTED TO DISPLAY EXERCISE OUTPUT !
 */
public class Launcher {
    private static final List<TaxStrategy> strategies = List.of(new NormalTaxStrategy(), new NoTaxStrategy(), new ReducedTaxStrategy());
    private static final TaxStrategyFactory taxStrategyFactory = new TaxStrategyFactory(strategies, new ImportedTaxStrategy());
    private static final CommandService commandService = new CommandService(taxStrategyFactory);
    private static final InvoiceService invoiceService = new InvoiceService(new InvoiceOutputDisplayService(), new InvoiceInputDisplayService(), new InvoiceTaxAmountCalculator(), new InvoiceTotalAmountCalculatorService());
    private static final InvoiceHandler invoiceHandler = new InvoiceHandler(commandService, invoiceService);
    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {

        //Panier 1
        logger.info("### Panier 1");
        // lancement des calculs des montants et impression de la facture
        invoiceHandler.handleInvoice(getCart1());

        logger.info("-----------------------------------------------------");

        //Panier 2
        logger.info("### Panier 2");
        // lancement des calculs des montants et impression de la facture
        invoiceHandler.handleInvoice(getCommandsCart2());

        logger.info("-----------------------------------------------------");

        //Panier 3
        logger.info("### Panier 3");
        // lancement des calculs des montants et impression de la facture
        invoiceHandler.handleInvoice(getCommandsCart3());
    }

    private static List<Command> getCart1() {
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