package estore.command;

import java.util.ArrayList;

import estore.data.Book;
import estore.data.Electronics;
import estore.data.Product;
import estore.logging.ILogger;

public class ViewOperationHandler implements IOperationHandler {
    private final ArrayList<Product> productList;
    private final ILogger logger;

    public ViewOperationHandler(ArrayList<Product> productList, ILogger logger) {
        this.productList = productList;
        this.logger = logger;
    }

    @Override
    public void execute() {

        if (productList.isEmpty()) {
            this.logger.log("Catalog is empty");
            return;
        }

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