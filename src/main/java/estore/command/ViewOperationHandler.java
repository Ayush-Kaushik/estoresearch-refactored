package estore.command;

import java.util.List;

import estore.data.Book;
import estore.data.Electronics;
import estore.data.IProductCatalogRepository;
import estore.data.Product;
import estore.logging.ILogger;

public class ViewOperationHandler implements IOperationHandler {
    private final IProductCatalogRepository catalogRepository;
    private final ILogger logger;

    public ViewOperationHandler(IProductCatalogRepository catalogRepository, ILogger logger) {
        this.catalogRepository = catalogRepository;
        this.logger = logger;
    }

    @Override
    public void execute() {
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

        logger.log("Catalog metadata");
        logger.log("Total products: " + productList.size());
        logger.log("Books: " + bookCount);
        logger.log("Electronics: " + electronicsCount);
    }
}