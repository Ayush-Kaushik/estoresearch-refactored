package refactored.factory;

import java.util.Map;
import java.util.Optional;

import refactored.model.Product;
import refactored.model.Book;
import refactored.model.Electronics;

/**
 * ProductFactory - a factory class for creating Product objects from a map of
 * strings
 */
public class ProductFactory implements IProductFactory {

  public Optional<Product> fromMap(Map<String, String> inputMap) {
    String type = inputMap.get("type");
    if ("book".equalsIgnoreCase(type)) {

      Product book = new Book(
          inputMap.get("id"),
          inputMap.get("name"),
          Double.parseDouble(inputMap.get("price")),
          Integer.parseInt(inputMap.get("year")),
          inputMap.getOrDefault("author", ""),
          inputMap.getOrDefault("publisher", ""));

      return Optional.of(book);

    } else if ("electronics".equalsIgnoreCase(type)) {
      Product electronics = new Electronics(
          inputMap.get("id"),
          inputMap.get("name"),
          Double.parseDouble(inputMap.get("price")),
          Integer.parseInt(inputMap.get("year")),
          inputMap.getOrDefault("maker", ""));

      return Optional.of(electronics);
    }
    return Optional.empty();
  }
}