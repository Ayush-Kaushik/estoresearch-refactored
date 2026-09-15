package estore.dataaccess;

import java.util.List;

/**
 * Data-access contract for catalog persistence and retrieval.
 *
 * This abstraction allows the business layer to remain independent from the
 * physical storage mechanism.
 */
public interface IProductCatalogRepository {
    void add(Product product);

    List<Product> search(String id, String name, String year);

    void delete(String id);

    List<Product> findAll();
}
