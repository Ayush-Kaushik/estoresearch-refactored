package estore.presentation.command;

import estore.business.IProductService;
import estore.presentation.IConsole;
import estore.presentation.ProductAddView;

public class AddOperationHandler implements IOperationHandler {
    private final IProductService productService;
    private final IConsole console;

    public AddOperationHandler(
        IProductService productService,
        IConsole console) {
        this.productService = productService;
        this.console = console;
    }

    @Override
    public void execute() {
        new ProductAddView(productService, console).add();
    }
}