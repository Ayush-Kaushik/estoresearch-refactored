package estore.presentation.command;

import java.util.List;

import estore.data.Book;
import estore.data.Electronics;
import estore.data.IProductCatalogRepository;
import estore.data.Product;
import estore.logging.ILogger;
import estore.logging.Logger;
import estore.presentation.ConsoleMessages;
import estore.presentation.IConsole;

public class ViewOperationHandler implements IOperationHandler {
    private final IProductCatalogRepository catalogRepository;
    private final IConsole console;
    private final ILogger logger;

    public ViewOperationHandler(
        IProductCatalogRepository catalogRepository,
        IConsole console) {
        this(catalogRepository, console, new Logger());
    }

    public ViewOperationHandler(
        IProductCatalogRepository catalogRepository,
        IConsole console,
        ILogger logger) {
        this.catalogRepository = catalogRepository;
        this.console = console;
        this.logger = logger;
    }

    @Override
    public void execute() {
        logger.info("Executing catalog view operation");
        List<Product> productList = catalogRepository.findAll();
        int bookCount = 0;
        int electronicsCount = 0;
        for (Product product : productList) {
            if (product instanceof Book) {
                bookCount++;
            } else if (product instanceof Electronics) {
                electronicsCount++;
            }
        }

        logger.info("Catalog metadata counts: total=" + productList.size() + ", books=" + bookCount + ", electronics=" + electronicsCount);
        console.printMessage(ConsoleMessages.CATALOG_METADATA);
        console.printMessage(ConsoleMessages.TOTAL_PRODUCTS + productList.size());
        console.printMessage(ConsoleMessages.BOOKS + bookCount);
        console.printMessage(ConsoleMessages.ELECTRONICS + electronicsCount);
    }
}
