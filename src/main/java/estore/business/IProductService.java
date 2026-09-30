package estore.business;

import java.util.List;

import estore.data.Product;

/**
 * Business-layer contract for managing catalog operations.
 *
 * This interface encapsulates product-related operations without exposing
 * storage details or console concerns.
 */
public interface IProductService {
    void addProduct(Product product);

    List<Product> searchProducts(String id, String name, String year);
}
