package com.caa.kata.service.tax;

import com.caa.kata.service.command.CommandService;
import com.caa.kata.utils.constant.Constants;
import com.caa.kata.utils.service.ValidatorService;
import com.caa.kata.exception.TechnicalException;
import com.caa.kata.model.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class ImportedTaxStrategy implements TaxStrategy {

    private TaxStrategy initialTaxStrategy;
    private static final Logger logger = LoggerFactory.getLogger(ImportedTaxStrategy.class);
    private final String LOG_PREFIX = "[ImportedTaxStrategy] : ";

    public void setInitialTaxStrategy(TaxStrategy initialTaxStrategy) {
        this.initialTaxStrategy = initialTaxStrategy;
    }

    @Override
    public BigDecimal calculateTax(BigDecimal price) {
        try {
            ValidatorService.validateObject(price, Constants.PRODUCT_PRICE);
            BigDecimal initialTax = this.initialTaxStrategy.calculateTax(price);
            BigDecimal importTax = price.multiply(BigDecimal.valueOf(0.05));
            return initialTax.add(importTax);
        } catch (IllegalArgumentException e) {
            logger.warn(LOG_PREFIX + Constants.ERROR, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            logger.warn(LOG_PREFIX + Constants.ERROR, e.getLocalizedMessage());
            throw new TechnicalException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public boolean appliesTo(Product product) {
        return initialTaxStrategy.appliesTo(product);
    }


}
