package com.caa.kata.utils.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FormatService {

    public static BigDecimal format(BigDecimal amount) {
        return amount.divide(new BigDecimal("0.05"), 0, RoundingMode.CEILING).multiply(new BigDecimal("0.05"));
    }
}
