package refactored.service;

import java.util.ArrayList;
import refactored.model.Product;

public interface IProductService {

  /**
   * Checks if a product with the given ID exists in the product list.
   */
  boolean productExists(String productId);

  /**
   * Adds a new product to the product list.
   */
  void addProduct(Product product);

  /**
   * Tries to add a new product to the product list.
   */
  boolean tryAddProduct(Product product);

  /**
   * Returns the list of all products.
   */
  ArrayList<Product> getProductList();

  /**
   * Finds a product by its ID.
   */
  Product findProductById(String id);
}