package com.caa.kata.launcher;

import com.caa.kata.model.command.Command;
import com.caa.kata.model.invoice.Invoice;
import com.caa.kata.service.command.CommandService;
import com.caa.kata.service.invoice.InvoiceService;
import com.caa.kata.utils.constant.Constants;
import com.caa.kata.utils.service.ValidatorService;

import java.util.List;

public class InvoiceHandler implements IInvoiceHandler{

    private final CommandService commandService;
    private final InvoiceService invoiceService;

    public InvoiceHandler(CommandService commandService, InvoiceService invoiceService) {
        this.commandService = commandService;
        this.invoiceService = invoiceService;
    }

    @Override
    public void handleInvoice(List<Command> commands) {
        try {

            ValidatorService.validateList(commands, Constants.COMMAND_LIST);

            Invoice invoice = new Invoice();

            //calcul des montants des taxes et hors taxe de chaque commande puis ajout dans la facture
            commands.forEach(command -> commandService.purchaseCommand(invoice, command));

            //calcul des montants TTC total de la facture
            invoiceService.calculateTotalTtcAmount(invoice);

            //calcul des montants des taxes total de la facture
            invoiceService.calculateTotalTaxAmount(invoice);

            //affichage de l'input
            invoiceService.displayInvoiceInput(invoice);

            //affichage de l'output
            invoiceService.displayInvoiceOutput(invoice);
        }catch (IllegalArgumentException e){

        }catch (Exception e){

        }
    }
}
