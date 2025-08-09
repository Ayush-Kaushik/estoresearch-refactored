package refactored.factory;

import java.util.Map;
import java.util.Optional;

import refactored.model.Product;

public interface IProductFactory {
  public Optional<Product> fromMap(Map<String, String> inputMap);
}