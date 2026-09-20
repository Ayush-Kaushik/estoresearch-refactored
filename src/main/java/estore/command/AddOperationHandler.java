package estore.command;

import estore.business.IProductService;
import estore.logging.ILogger;
import estore.presentation.ConsoleView;
import estore.presentation.ProductAddView;

public class AddOperationHandler implements IOperationHandler {
    private final IProductService productService;
    private final ConsoleView consoleView;
    private final ILogger logger;

    public AddOperationHandler(
        IProductService productService,
        ConsoleView consoleView,
        ILogger logger) {
        this.productService = productService;
        this.consoleView = consoleView;
        this.logger = logger;
    }

    @Override
    public void execute() {
        new ProductAddView(productService, consoleView, logger).add();
    }
}