package com.caa.kata.service.invoice.calculation;

import com.caa.kata.utils.constant.Constants;
import com.caa.kata.utils.service.ValidatorService;
import com.caa.kata.model.command.Command;

import java.math.BigDecimal;

public class InvoiceTotalAmountCalculatorService extends InvoiceCalculatorService{

    /**
     * @param command command
     * @return Command TTC amount
     */
    @Override
    protected BigDecimal compute(Command command) {
        ValidatorService.validateObject(command, Constants.COMMAND);
        return command.getTtcAmount();
    }
}
