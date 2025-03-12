package com.caa.kata.utils.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FormatServiceTest {


    static Stream<Arguments> formatValues() {
        return Stream.of(
                Arguments.of(new BigDecimal("0.95"),new BigDecimal("0.95")),
                Arguments.of(new BigDecimal("0.99"),new BigDecimal("1.00")),
                Arguments.of(new BigDecimal("1.01"), new BigDecimal("1.05")),
                Arguments.of(new BigDecimal("1.02"), new BigDecimal("1.05"))
        );
    }

    @ParameterizedTest
    @MethodSource("formatValues")
    void formatValues_returnsExpectedValue(BigDecimal tax, BigDecimal expectedTax) {
        BigDecimal result = FormatService.format(tax);
        assertEquals(expectedTax, result);
    }

    }