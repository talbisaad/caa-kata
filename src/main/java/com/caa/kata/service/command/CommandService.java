package com.caa.kata.service.command;

import com.caa.kata.model.invoice.Invoice;
import com.caa.kata.utils.constant.Constants;
import com.caa.kata.utils.service.FormatService;
import com.caa.kata.utils.service.ValidatorService;
import com.caa.kata.exception.TechnicalException;
import com.caa.kata.model.command.Command;
import com.caa.kata.model.product.Product;
import com.caa.kata.service.tax.TaxStrategy;
import com.caa.kata.service.tax.TaxStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.math.BigDecimal;


public class CommandService implements ICommandService {
    private static final Logger logger = LoggerFactory.getLogger(CommandService.class);

    private final TaxStrategyFactory taxStrategyFactory;
    private final String LOG_PREFIX = "[CommandService] : ";

    public CommandService(TaxStrategyFactory taxStrategyFactory) {
        this.taxStrategyFactory = taxStrategyFactory;
    }

    /**
     * @param command a line of invoice
     *                This method set TTC and TAX amount for given command
     */
    @Override
    public void calculateCommandAmounts(Command command) {
        try {

            ValidatorService.validateObject(command, Constants.COMMAND);
            ValidatorService.validateObject(command.getQuantity(), Constants.COMMAND_QUANTITY);

            Product product = command.getProduct();
            ValidatorService.validateObject(product, Constants.PRODUCT);
            //recuperation du bon taxStrategy
            TaxStrategy taxStrategy = taxStrategyFactory.getTaxStrategy(product);
            //calcul du Montant des taxes de la commande
            BigDecimal taxAmount = getTaxAmount(command, product, taxStrategy);
            //calcul du Montant hors taxe de la commande
            BigDecimal HtAmount = getHtAmount(command, product);

            command.setTtcAmount(HtAmount.add(taxAmount));
            command.setTaxAmount(taxAmount);

        } catch (IllegalArgumentException e) {
            logger.warn(LOG_PREFIX + Constants.ERROR, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            logger.warn(LOG_PREFIX + Constants.ERROR, e.getLocalizedMessage());
            throw new TechnicalException(e.getMessage(), e.getCause());
        }
    }


    /**
     * Calculate Command Tax and HT amounts then add it to invoice command list
     *
     * @param invoice
     * @param command
     */
    @Override
    public void purchaseCommand(Invoice invoice, Command command) {
        calculateCommandAmounts(command);
        invoice.getCommands().add(command);
    }

    private BigDecimal getHtAmount(Command command, Product product) {
        ValidatorService.validateObject(product.price(), Constants.PRODUCT_PRICE);
        return product.price().multiply(BigDecimal.valueOf(command.getQuantity()));

    }

    private BigDecimal getTaxAmount(Command command, Product product, TaxStrategy taxStrategy) {
        ValidatorService.validateObject(product.price(), Constants.PRODUCT_PRICE);
        return FormatService.format(taxStrategy.calculateTax(product.price())).multiply(BigDecimal.valueOf(command.getQuantity()));
    }


}
