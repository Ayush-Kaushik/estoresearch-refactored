package estore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import estore.data.Product;
import estore.logging.ConsoleLogger;
import estore.logging.ILogger;

/**
 * Writes the in-memory product catalog in the application's text-file format.
 */
public class CatalogFileWriter {

    private final ArrayList<Product> _productList;
    private final ILogger logger;

    public CatalogFileWriter(ArrayList<Product> productList) {
        this(productList, new ConsoleLogger());
    }

    public CatalogFileWriter(ArrayList<Product> productList, ILogger logger) {
        this._productList = productList;
        this.logger = logger;
    }

    /**
     * Writes the catalog to {@code java.txt}, preserving the existing export
     * format and console messages.
     */
    public void write() {
        try {
            PrintWriter outputStream = new PrintWriter(new FileOutputStream("java.txt"));

            for (int i = 0; i < this._productList.size(); i++) {
                outputStream.println(this._productList.get(i).toString());
            }

            logger.log("Successful write to file");
            outputStream.close();
        } catch (FileNotFoundException e) {
            logger.log("Unable to open file");
            System.exit(0);
        }
    }
}
