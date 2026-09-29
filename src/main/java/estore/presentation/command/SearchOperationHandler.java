package estore.presentation.command;

import estore.business.ProductSearchService;
import estore.logging.ILogger;
import estore.logging.Logger;
import estore.presentation.IConsole;
import estore.presentation.ProductSearchView;

public class SearchOperationHandler implements IOperationHandler {
    private final ProductSearchService productSearchService;
    private final IConsole console;
    private final ILogger logger;

    public SearchOperationHandler(
            ProductSearchService productSearchService,
            IConsole console) {
        this(productSearchService, console, new Logger());
    }

    public SearchOperationHandler(
            ProductSearchService productSearchService,
            IConsole console,
            ILogger logger) {
        this.productSearchService = productSearchService;
        this.console = console;
        this.logger = logger;
    }

    @Override
    public void execute() {
        logger.info("Executing search operation");
        new ProductSearchView(productSearchService, console, logger).search();
    }
}