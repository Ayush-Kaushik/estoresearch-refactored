package estore.command;

import java.util.ArrayList;

import estore.CatalogFileWriter;
import estore.data.Product;
import estore.logging.ILogger;

public class QuitOperationHandler implements IOperationHandler {
    private final ArrayList<Product> productList;
    private final ILogger logger;

    public QuitOperationHandler(ArrayList<Product> productList, ILogger logger) {
        this.productList = productList;
        this.logger = logger;
    }

    @Override
    public void execute() {
        logger.log("Selected option Quit\n");
        new CatalogFileWriter(productList, logger).write();
        System.exit(0);
    }
}