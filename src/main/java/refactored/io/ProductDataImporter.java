package refactored.io;

import java.util.logging.Logger;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Optional;

import refactored.factory.IProductFactory;
import refactored.model.Product;
import refactored.service.IProductService;

import java.util.logging.Level;

public class ProductDataImporter implements IProductDataImporter {
    private HashMap<String, String> inputMap;
    private Scanner scanner;
    private IProductService productService;
    private IProductFactory productFactory;
    private Logger logger;

    public ProductDataImporter(IProductService productService, Logger logger, IProductFactory productFactory) {
        this.inputMap = new HashMap<String, String>();
        this.productService = productService;
        this.logger = logger;
        this.productFactory = productFactory;
    }

    public void importData(String filePath) {
        this.logger.info("Loading data from file: " + filePath);

        try {
            this.scanner = new Scanner(new FileInputStream(filePath));

            while (this.scanner.hasNextLine()) {
                String line = this.scanner.nextLine();

                this.logger.log(Level.FINE, "Processing line: {0}", line);

                String[] parts = line.split("=");

                // TODO: Support edge case: if there are two delimeters in a line
                // TODO: support edge case: if there are no delimeters in a line
                // TODO: support edge case: if there are no key value pairs in a line
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    this.inputMap.put(key, value);

                } else if (parts.length == 1) {
                    logger.log(Level.FINE, "Setting up new product");

                    Optional<Product> output = this.productFactory.fromMap(inputMap);
                    Product product = output.get();

                    if (!this.productService.tryAddProduct(product)) {
                        logger.log(Level.WARNING, "Product with id {0} already exists, skipping", product.getID());
                    } else {
                        logger.log(Level.FINE, "Added new product: {0}", product.datadump());
                    }

                    this.inputMap.clear();

                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error reading file", e.getStackTrace());
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
