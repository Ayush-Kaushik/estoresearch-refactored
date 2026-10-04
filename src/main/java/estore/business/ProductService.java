package estore.business;

import java.util.List;

import estore.data.IProductCatalogRepository;
import estore.data.Product;
import estore.logging.ILogger;
import estore.logging.Logger;

public class ProductService implements IProductService {
    private final IProductCatalogRepository catalogRepository;
    private final ILogger logger;

    public ProductService(IProductCatalogRepository catalogRepository) {
        this(catalogRepository, new Logger());
    }

    public ProductService(IProductCatalogRepository catalogRepository, ILogger logger) {
        this.catalogRepository = catalogRepository;
        this.logger = logger;
    }

    @Override
    public void addProduct(Product product) {
        logger.info("Adding product with id=" + product.getID());
        catalogRepository.add(product);
    }

    @Override
    public List<Product> searchProducts(String id, String name, String year) {
        logger.info("Searching products with id=" + id + ", name=" + name + ", year=" + year);
        return catalogRepository.search(id, name, year);
    }
}
