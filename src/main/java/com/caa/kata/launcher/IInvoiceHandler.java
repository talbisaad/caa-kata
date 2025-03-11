package com.caa.kata.launcher;

import com.caa.kata.model.command.Command;

import java.util.List;

public interface IInvoiceHandler {
    void handleInvoice(List<Command> commands);

}
