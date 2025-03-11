package com.caa.kata.service.tax;

import com.caa.kata.model.product.Product;
import com.caa.kata.model.product.ProductFamily;

import java.math.BigDecimal;

public class NoTaxStrategy implements TaxStrategy{
    @Override
    public BigDecimal calculateTax(BigDecimal price) {
        return BigDecimal.ZERO;
    }

    @Override
    public boolean appliesTo(Product product) {
        return ProductFamily.BASIC.equals(product.productFamily());
    }
}
