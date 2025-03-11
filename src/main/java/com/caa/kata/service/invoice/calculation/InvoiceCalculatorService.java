package com.caa.kata.service.invoice.calculation;

import com.caa.kata.service.command.CommandService;
import com.caa.kata.utils.constant.Constants;
import com.caa.kata.utils.service.ValidatorService;
import com.caa.kata.exception.TechnicalException;
import com.caa.kata.model.command.Command;
import com.caa.kata.model.invoice.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public abstract class InvoiceCalculatorService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceCalculatorService.class);
    private final String LOG_PREFIX = "[InvoiceCalculatorService] : ";
    /**
     * @param invoice invoice
     * @return global invoice TTC or taxes amount
     */
    public BigDecimal calculateAmount(Invoice invoice) throws IllegalArgumentException, TechnicalException {

        BigDecimal sumAmount = BigDecimal.ZERO;
        try {
            ValidatorService.validateObject(invoice, Constants.INVOICE);
            if (invoice.getCommands() != null && !invoice.getCommands().isEmpty()) {
                sumAmount = invoice.getCommands().stream().map(this::compute).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            return sumAmount;
        } catch (IllegalArgumentException e) {
            logger.warn(LOG_PREFIX + Constants.ERROR, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            logger.warn(LOG_PREFIX + Constants.ERROR, e.getLocalizedMessage());
            throw new TechnicalException(e.getMessage(), e.getCause());
        }
    }

    /**
     * @param command invoice command
     * @return command's TTC or tax amount
     */
    protected abstract BigDecimal compute(Command command);
}
