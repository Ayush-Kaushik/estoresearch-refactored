package estore.command;

import estore.CatalogFileWriter;
import estore.data.IProductCatalogRepository;
import estore.logging.ILogger;

public class QuitOperationHandler implements IOperationHandler {
    private final IProductCatalogRepository catalogRepository;
    private final ILogger logger;

    public QuitOperationHandler(IProductCatalogRepository catalogRepository, ILogger logger) {
        this.catalogRepository = catalogRepository;
        this.logger = logger;
    }

    @Override
    public void execute() {
        logger.log("Selected option Quit\n");
        new CatalogFileWriter(catalogRepository, logger).write();
        System.exit(0);
    }
}