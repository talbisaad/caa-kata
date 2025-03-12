package com.caa.kata.service.tax;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.caa.kata.model.product.Product;
import com.caa.kata.model.product.ProductFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class TaxStrategyFactoryTest {

    private TaxStrategyFactory taxStrategyFactory;
    private ImportedTaxStrategy importedTaxStrategy;
    private Product product;
    private TaxStrategy taxStrategy;

    @BeforeEach
    void setUp() {
        importedTaxStrategy = mock(ImportedTaxStrategy.class);
        taxStrategyFactory = new TaxStrategyFactory(List.of(new NormalTaxStrategy(), new NoTaxStrategy(), new ReducedTaxStrategy()), importedTaxStrategy);
        product = mock(Product.class);

    }
    @Test
    void getTaxStrategy_NormalTaxStrategy_nominal() {
        taxStrategy = mock(NormalTaxStrategy.class);
        when(product.productFamily()).thenReturn(ProductFamily.OTHER);
        when(taxStrategy.appliesTo(any())).thenReturn(true);
        TaxStrategy result = taxStrategyFactory.getTaxStrategy(product);
        assertEquals(taxStrategy.getClass(), result.getClass());
    }

    @Test
    void getTaxStrategy_NoTaxStrategy_nominal() {
        taxStrategy = mock(NoTaxStrategy.class);
        when(product.productFamily()).thenReturn(ProductFamily.BASIC);
        when(taxStrategy.appliesTo(any())).thenReturn(true);
        TaxStrategy result = taxStrategyFactory.getTaxStrategy(product);
        assertEquals(taxStrategy.getClass(), result.getClass());
    }

    @Test
    void getTaxStrategy_ReducedTaxStrategy_nominal() {
        taxStrategy = mock(ReducedTaxStrategy.class);
        when(product.productFamily()).thenReturn(ProductFamily.BOOK);
        when(taxStrategy.appliesTo(any())).thenReturn(true);
        TaxStrategy result = taxStrategyFactory.getTaxStrategy(product);
        assertEquals(taxStrategy.getClass(), result.getClass());
    }

    @Test
    void getTaxStrategy_ImportedTaxStrategy_nominal() {
        taxStrategy = mock(ImportedTaxStrategy.class);
        when(product.productFamily()).thenReturn(ProductFamily.BOOK);
        when(taxStrategy.appliesTo(any())).thenReturn(true);
        when(product.imported()).thenReturn(true);
        TaxStrategy result = taxStrategyFactory.getTaxStrategy(product);
        assertEquals(importedTaxStrategy.getClass(), result.getClass());
    }

    @Test
    void getTaxStrategy_noMatchingStrategy_throwsIllegalArgumentException() {
        taxStrategy = mock(ReducedTaxStrategy.class);
        when(taxStrategy.appliesTo(any())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> taxStrategyFactory.getTaxStrategy(product));
    }

    @Test
    void getTaxStrategy_nullProduct_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> taxStrategyFactory.getTaxStrategy(null));
    }

}