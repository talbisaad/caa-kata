package com.caa.kata.service.cart;

import com.caa.kata.exception.TechnicalException;
import com.caa.kata.model.command.Command;
import com.caa.kata.model.invoice.Invoice;
import com.caa.kata.service.command.ICommandService;
import com.caa.kata.service.invoice.IInvoiceService;
import com.caa.kata.service.printing.IPrintingService;
import com.caa.kata.utils.constant.Constants;
import com.caa.kata.utils.service.ValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CartHandlerService implements ICartHandlerService {

    private final ICommandService commandService;
    private final IInvoiceService invoiceService;
    private final IPrintingService printingService;

    private final String LOG_PREFIX = "[CartHandler] : ";

    private static final Logger logger = LoggerFactory.getLogger(CartHandlerService.class);
    public CartHandlerService(ICommandService commandService, IInvoiceService invoiceService, IPrintingService printingService) {
        this.commandService = commandService;
        this.invoiceService = invoiceService;
        this.printingService = printingService;
    }

    /**
     * purchase commands then display invoice
     * @param commands
     */
    @Override
    public void handleCart(List<Command> commands) {
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
            String invoiceInput = invoiceService.generateInvoiceInput(invoice);
            printingService.display(invoiceInput);

            //affichage de l'output
            String invoiceOutput = invoiceService.generateInvoiceOutput(invoice);
            printingService.display(invoiceOutput);

        } catch (IllegalArgumentException e) {
            logger.warn(LOG_PREFIX + Constants.ERROR, e.getLocalizedMessage());
            throw e;
        } catch (Exception e) {
            logger.warn(LOG_PREFIX + Constants.ERROR, e.getLocalizedMessage());
            throw new TechnicalException(e.getMessage(), e.getCause());
        }
    }
}
