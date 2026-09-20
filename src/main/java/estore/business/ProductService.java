package estore.business;

import java.util.ArrayList;
import java.util.List;

import estore.data.IProductCatalogRepository;
import estore.data.Product;

public class ProductService implements IProductService {
    private final IProductCatalogRepository catalogRepository;

    public ProductService(IProductCatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public void addProduct(Product product) {
        catalogRepository.add(product);
    }

    @Override
    public List<Product> searchProducts(String id, String name, String year) {
        return catalogRepository.search(id, name, year);
    }

    @Override
    public void deleteProduct(String id) {
        catalogRepository.delete(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(catalogRepository.findAll());
    }
}
