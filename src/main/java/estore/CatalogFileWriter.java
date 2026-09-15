package estore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import estore.dataaccess.Product;

/**
 * Writes the in-memory product catalog in the application's text-file format.
 */
public class CatalogFileWriter {

    private final ArrayList<Product> _productList;

    public CatalogFileWriter(ArrayList<Product> productList) {
        this._productList = productList;
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

            System.out.println("Successful write to file");
            outputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file");
            System.exit(0);
        }
    }
}
