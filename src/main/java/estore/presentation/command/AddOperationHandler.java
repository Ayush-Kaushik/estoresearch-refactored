package estore.presentation.command;

import estore.business.IProductService;
import estore.logging.ILogger;
import estore.presentation.IConsole;
import estore.presentation.ProductAddView;

public class AddOperationHandler implements IOperationHandler {
    private final IProductService productService;
    private final IConsole console;
    private final ILogger logger;

    public AddOperationHandler(
        IProductService productService,
        IConsole console,
        ILogger logger) {
        this.productService = productService;
        this.console = console;
        this.logger = logger;
    }

    @Override
    public void execute() {
        logger.info("Executing add product operation");
        new ProductAddView(productService, console, logger).add();
    }
}