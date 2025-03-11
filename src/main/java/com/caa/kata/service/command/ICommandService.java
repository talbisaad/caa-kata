package com.caa.kata.service.command;

import com.caa.kata.exception.TechnicalException;
import com.caa.kata.model.command.Command;
import com.caa.kata.model.invoice.Invoice;
import com.caa.kata.model.product.Product;

public interface ICommandService {

    /**
     * Calculate Command Tax and HT amounts then add it to invoice command list
     * @param invoice
     * @param command
     */
    void purchaseCommand(Invoice invoice, Command command);

    /**
     * @param command a line of invoice
     *                This method set TTC and TAX amount for given command
     */
    void calculateCommandAmounts(Command command) throws IllegalArgumentException, TechnicalException;

}
