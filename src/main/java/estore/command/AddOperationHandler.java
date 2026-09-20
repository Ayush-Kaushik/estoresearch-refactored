package estore.command;

import java.util.ArrayList;

import estore.business.ProductAdditionService;
import estore.data.Product;
import estore.logging.ILogger;
import estore.presentation.ConsoleView;
import estore.presentation.ProductAddView;

public class AddOperationHandler implements IOperationHandler {
    private final ProductAdditionService productAdditionService;
    private final ArrayList<Product> productList;
    private final ConsoleView consoleView;
    private final ILogger logger;

    public AddOperationHandler(ProductAdditionService productAdditionService,
            ArrayList<Product> productList, ConsoleView consoleView, ILogger logger) {
        this.productAdditionService = productAdditionService;
        this.productList = productList;
        this.consoleView = consoleView;
        this.logger = logger;
    }

    @Override
    public void execute() {
        new ProductAddView(productAdditionService, productList, consoleView, logger).add();
    }
}