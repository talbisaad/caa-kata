package com.caa.kata.utils.service;

import com.caa.kata.utils.constant.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class FormatService {
    private FormatService() {

    }

    public static BigDecimal format(BigDecimal amount) {
        ValidatorService.validateObject(amount, Constants.AMOUNT);
        return amount.divide(new BigDecimal("0.05"), 0, RoundingMode.CEILING).multiply(new BigDecimal("0.05"));
    }
}
