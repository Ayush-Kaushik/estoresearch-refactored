package estore.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import estore.logging.ILogger;

/**
 * Loads catalog products from the application's text-file format and stores
 * them
 * through the repository, which owns the in-memory data structure and indexes.
 */
public class CatalogFileReader {

    private final IProductCatalogRepository catalogRepository;
    private final ILogger logger;

    public CatalogFileReader(IProductCatalogRepository catalogRepository, ILogger logger) {
        this.catalogRepository = catalogRepository;
        this.logger = logger;
    }

    /**
     * Reads products into the repository-backed catalog.
     *
     * @param fileName the catalog file to read
     * @throws FileNotFoundException if the catalog file cannot be opened
     */
    public void read(String fileName) throws FileNotFoundException {
        logger.info("Reading catalog from file: " + fileName);
        HashMap<String, String> inputMap = new HashMap<String, String>();

        try (Scanner inputStream = new Scanner(new FileInputStream(fileName))) {
            while (inputStream.hasNextLine()) {
                String line = inputStream.nextLine();

                if (line.trim().isEmpty()) {
                    addProductToRepository(inputMap);
                    inputMap.clear();
                    continue;
                }

                String[] values = line.split("=");

                if (values.length == 2) {
                    String key = values[0].trim();
                    String value = values[1].trim();
                    if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    logger.debug("processed: " + key + " " + value);
                    inputMap.put(key, value);
                } else {
                    logger.debug("invalid catalog line item" + line);
                    inputMap.clear();
                }
            }
        }
    }

    private void addProductToRepository(HashMap<String, String> inputMap) {
        Product product = createProduct(inputMap);

        if (product != null) {
            logger.info("Loaded product into catalog: " + product.getID());
            catalogRepository.add(product);
        }
    }

    private Product createProduct(HashMap<String, String> inputMap) {
        String type = inputMap.get("type");
        String id = inputMap.get("productID");
        String name = inputMap.get("name");
        String price = inputMap.get("price");
        String year = inputMap.get("year");

        if(type == null || id == null || name == null || price == null || year == null) {
            logger.debug("Invalid product data: " + inputMap);
            return null;
        }

        switch (type) {
            case "book":
                String author = inputMap.get("authors");
                String publisher = inputMap.get("publisher");
                return new Book(
                        id,
                        name,
                        Double.parseDouble(price),
                        Integer.parseInt(year),
                        author == null ? "" : author,
                        publisher == null ? "" : publisher);

            case "electronics":
                String maker = inputMap.get("maker");
                return new Electronics(id, name, Double.parseDouble(price), Integer.parseInt(year),
                        maker == null ? "" : maker);
            default:
                logger.debug("Unknown product type: " + type);
                return null;
        }
    }
}
