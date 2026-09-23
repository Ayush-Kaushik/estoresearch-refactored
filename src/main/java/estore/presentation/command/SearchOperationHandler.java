package estore.presentation.command;

import estore.business.ProductSearchService;
import estore.presentation.IConsole;
import estore.presentation.ProductSearchView;

public class SearchOperationHandler implements IOperationHandler {
    private final ProductSearchService productSearchService;
    private final IConsole console;

    public SearchOperationHandler(
            ProductSearchService productSearchService,
            IConsole console) {
        this.productSearchService = productSearchService;
        this.console = console;
    }

    @Override
    public void execute() {
        new ProductSearchView(productSearchService, console).search();
    }
}