package com.caa.kata.model.invoice;

import com.caa.kata.model.command.Command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Invoice {
    /**
     * invoice command list
     */
    private List<Command> commands;
    /**
     * sum of tax amounts
     */
    private BigDecimal taxAmount;
    /**
     * sum of ttc amounts
     */
    private BigDecimal totalTtcAmount;


    public Invoice(){
        commands = new ArrayList<>();
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalTtcAmount() {
        return totalTtcAmount;
    }

    public void setTotalTtcAmount(BigDecimal totalTtcAmount) {
        this.totalTtcAmount = totalTtcAmount;
    }
}
