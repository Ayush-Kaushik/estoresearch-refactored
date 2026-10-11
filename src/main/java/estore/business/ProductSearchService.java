package estore.business;

import java.util.List;

import estore.data.Product;
import estore.logging.ILogger;
import estore.logging.Logger;

public class ProductSearchService {
    private final IProductService productService;
    private final ILogger logger;

    public ProductSearchService(IProductService productService) {
        this(productService, new Logger());
    }

    public ProductSearchService(IProductService productService, ILogger logger) {
        this.productService = productService;
        this.logger = logger;
    }

    public List<Product> search(SearchQuery query) {
        logger.info("Executing search query with id=" + query.getId() + ", name=" + query.getName() + ", year=" + query.getYear());
        return productService.searchProducts(query.getId(), query.getName(), query.getYear());
    }
}
