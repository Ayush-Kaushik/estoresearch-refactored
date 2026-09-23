package estore.data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

import estore.logging.ILogger;
import estore.logging.Logger;

/**
 * Writes the repository-backed catalog in the application's text-file format.
 */
public class CatalogFileWriter {

    private final IProductCatalogRepository catalogRepository;
    private final ILogger logger;

    public CatalogFileWriter(IProductCatalogRepository catalogRepository) {
        this(catalogRepository, new Logger());
    }

    public CatalogFileWriter(IProductCatalogRepository catalogRepository, ILogger logger) {
        this.catalogRepository = catalogRepository;
        this.logger = logger;
    }

    /**
     * Writes the catalog to {@code java.txt}, preserving the existing export
     * format and console messages.
     */
    public void write() {
        try {
            PrintWriter outputStream = new PrintWriter(new FileOutputStream("java.txt"));
            List<Product> products = catalogRepository.findAll();

            for (Product product : products) {
                outputStream.println(product.toString());
            }

            logger.log("Successful write to file");
            outputStream.close();
        } catch (FileNotFoundException e) {
            logger.log("Unable to open file");
            System.exit(0);
        }
    }
}
