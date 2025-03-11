package com.caa.kata.service.tax;

import com.caa.kata.model.product.Product;

import java.math.BigDecimal;

public interface TaxStrategy {
    /**
     * @param price product HT price
     * @return calculated tax amount
     */
    BigDecimal calculateTax(BigDecimal price);

    /**
     * @param product Product
     * @return if TaxStrategy could be applied to product Family
     */
    boolean appliesTo(Product product);

}
