package refactored.factory;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import refactored.model.Product;
import refactored.model.Book;
import refactored.model.Electronics;

/**
 * ProductFactory - a factory class for creating Product objects
 * from a map of strings
 */
public class ProductFactory implements IProductFactory {

  private Logger logger;

  public ProductFactory(Logger logger) {
    this.logger = logger;
  }

  public Optional<Product> fromMap(Map<String, String> inputMap) {
    try {
      String type = inputMap.get("type");
      if ("book".equalsIgnoreCase(type)) {
        Product book = new Book(
            inputMap.get("id"),
            inputMap.get("name"),
            Double.parseDouble(inputMap.get("price")),
            Integer.parseInt(inputMap.get("year")),
            inputMap.getOrDefault("authors", ""),
            inputMap.getOrDefault("publisher", ""));
        return Optional.of(book);
      }

      if ("electronics".equalsIgnoreCase(type)) {
        Product electronics = new Electronics(
            inputMap.get("id"),
            inputMap.get("name"),
            Double.parseDouble(inputMap.get("price")),
            Integer.parseInt(inputMap.get("year")),
            inputMap.getOrDefault("maker", ""));
        return Optional.of(electronics);

      }
    } catch (Exception ex) {
      logger.warning("Error creating product from map: " + ex.getMessage());
    }

    return Optional.empty();
  }
}