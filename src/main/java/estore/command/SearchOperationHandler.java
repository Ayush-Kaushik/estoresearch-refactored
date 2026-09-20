package estore.command;

import estore.business.ProductSearchService;
import estore.logging.ILogger;
import estore.presentation.ConsoleView;
import estore.presentation.ProductSearchView;

public class SearchOperationHandler implements IOperationHandler {
    private final ProductSearchService productSearchService;
    private final ConsoleView consoleView;
    private final ILogger logger;

    public SearchOperationHandler(ProductSearchService productSearchService,
            ConsoleView consoleView, ILogger logger) {
        this.productSearchService = productSearchService;
        this.consoleView = consoleView;
        this.logger = logger;
    }

    @Override
    public void execute() {
        new ProductSearchView(productSearchService, consoleView, logger).search();
    }
}