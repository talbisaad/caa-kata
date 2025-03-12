package com.caa.kata.service.cart;

import com.caa.kata.model.command.Command;

import java.util.List;

public interface ICartHandlerService {

    /**
     * purchase commands then display invoice
     * @param commands
     */
    void handleCart(List<Command> commands);

}
