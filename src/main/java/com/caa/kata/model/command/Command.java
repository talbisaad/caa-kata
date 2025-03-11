package com.caa.kata.model.command;

import com.caa.kata.model.product.Product;

import java.math.BigDecimal;


public class Command {
    /**
     * Product
     */
    private Product product;
    /**
     * product quantity in single command
     */
    private int quantity;
    /**
     * calculated tax amount
     */
    private BigDecimal taxAmount;
    /**
     * calculated TTC amount
     */
    private BigDecimal ttcAmount;


    public Command(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTtcAmount() {
        return ttcAmount;
    }

    public void setTtcAmount(BigDecimal ttcAmount) {
        this.ttcAmount = ttcAmount;
    }
}
