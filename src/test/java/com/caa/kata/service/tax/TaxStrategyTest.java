package com.caa.kata.service.tax;

import com.caa.kata.model.product.Product;
import com.caa.kata.model.product.ProductFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaxStrategyTest {

    private Product product;

    @BeforeEach
    void setUp(){
        product = mock(Product.class);
    }

    static Stream<Arguments> taxStrategyProvider() {
        return Stream.of(
                Arguments.of(new ImportedTaxStrategy(),new BigDecimal("25.00")),
                Arguments.of(new NormalTaxStrategy(),new BigDecimal("20.0")),
                Arguments.of(new NoTaxStrategy(), BigDecimal.ZERO),
                Arguments.of(new ReducedTaxStrategy(), new BigDecimal("10.0"))
        );
    }
    @ParameterizedTest
    @MethodSource("taxStrategyProvider")
    void calculateTax_validPrice_returnsExpectedTax(TaxStrategy taxStrategy, BigDecimal expectedTax) {
        BigDecimal price = BigDecimal.valueOf(100);
        if (taxStrategy instanceof ImportedTaxStrategy) {
            ((ImportedTaxStrategy) taxStrategy).setInitialTaxStrategy(new NormalTaxStrategy());
        }
        BigDecimal result = taxStrategy.calculateTax(price);
        assertEquals(expectedTax, result);
    }
    @ParameterizedTest
    @MethodSource("taxStrategyProvider")
    void calculateTax_nullPrice_throwsIllegalArgumentException(TaxStrategy taxStrategy) {
        if(!(taxStrategy instanceof NoTaxStrategy)) {
            assertThrows(IllegalArgumentException.class, () -> taxStrategy.calculateTax(null));
        }
    }

    static Stream<Arguments> strategyProvider() {
        return Stream.of(
                Arguments.of(new NormalTaxStrategy(), ProductFamily.OTHER, true),
                Arguments.of(new NoTaxStrategy(), ProductFamily.BASIC, true),
                Arguments.of(new ReducedTaxStrategy(), ProductFamily.BOOK, true),
                Arguments.of(new NormalTaxStrategy(), ProductFamily.BASIC, false),
                Arguments.of(new NoTaxStrategy(), ProductFamily.OTHER, false),
                Arguments.of(new ReducedTaxStrategy(), ProductFamily.OTHER, false)
        );
    }
    @ParameterizedTest
    @MethodSource("strategyProvider")
    void appliesTo_returnsExpectedResult(TaxStrategy taxStrategy, ProductFamily productFamily, boolean expectedResult) {
        when(product.productFamily()).thenReturn(productFamily);
        boolean result = taxStrategy.appliesTo(product);
        assertEquals(expectedResult, result);
    }

}