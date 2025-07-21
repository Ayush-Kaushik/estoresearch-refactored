package refactored.io;

import java.util.logging.Logger;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;

import refactored.model.Book;
import refactored.model.Electronics;
import refactored.service.ProductService;

public class ProductDataImporter implements IProductDataImporter {
    private HashMap<String, String> inputMap;
    private Scanner scanner;
    private ProductService productService;
    private Logger logger;

    public ProductDataImporter(ProductService productService) {
        this.inputMap = new HashMap<String, String>();
        this.productService = productService;
        this.logger = Logger.getLogger(ProductDataImporter.class.getName());
    }

    public void load(String filePath) {
        try {
            this.scanner = new Scanner(new FileInputStream(filePath));
            while (this.scanner.hasNextLine()) {
                String line = this.scanner.nextLine();
                String[] parts = line.split("=");
                if (parts.length == 2) {

                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    this.inputMap.put(key, value);

                } else {

                    String type = this.inputMap.get("type");
                    String productId = this.inputMap.get("productID");
                    String name = this.inputMap.get("name");
                    String price = this.inputMap.get("price");
                    String year = this.inputMap.get("year");

                    if (type.equals("book")) {
                        String author = inputMap.get("author") == null ? inputMap.get("author") : "";
                        String publisher = inputMap.get("publisher") == null ? inputMap.get("publisher") : "";

                        Book book = new Book(
                                productId,
                                name,
                                Double.parseDouble(price),
                                Integer.parseInt(year),
                                author,
                                publisher);

                        this.productService.addProduct(book);
                        this.inputMap.clear();

                        logger.info("Adding new entry in list: " + book.datadump());
                    } else if (type.equals("electronics")) {

                        String maker = inputMap.get("maker") == null ? inputMap.get("maker") : "";
                        Electronics electronic = new Electronics(
                                productId,
                                name,
                                Double.parseDouble(price),
                                Integer.parseInt(year),
                                maker);

                        this.productService.addProduct(electronic);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
