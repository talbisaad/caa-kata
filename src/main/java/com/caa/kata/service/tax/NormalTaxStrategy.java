package com.caa.kata.service.tax;

import com.caa.kata.utils.constant.Constants;
import com.caa.kata.utils.service.ValidatorService;
import com.caa.kata.exception.TechnicalException;
import com.caa.kata.model.product.Product;
import com.caa.kata.model.product.ProductFamily;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class NormalTaxStrategy implements TaxStrategy {
    private static final Logger logger = LoggerFactory.getLogger(ImportedTaxStrategy.class);
    private final String LOG_PREFIX = "[NormalTaxStrategy] : ";
    @Override
    public BigDecimal calculateTax(BigDecimal price) {
        try {
            ValidatorService.validateObject(price, Constants.PRODUCT_PRICE);
            return price.multiply(BigDecimal.valueOf(0.20));
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
        return ProductFamily.OTHER.equals(product.productFamily());
    }

}
