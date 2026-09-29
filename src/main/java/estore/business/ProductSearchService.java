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

    public List<Product> searchByName(String name) {
        return search(new SearchQuery("", name, ""));
    }

    public List<Product> searchByIdAndName(String id, String name) {
        return search(new SearchQuery(id, name, ""));
    }

    public List<Product> searchByYear(String year) {
        return search(new SearchQuery("", "", year));
    }

    public List<Product> handleSearch(String id, String name, String year) {
        return search(new SearchQuery(id, name, year));
    }

    public static int[] parseYearRange(String year) {
        if (year == null || year.isEmpty()) {
            return new int[] { 0, 0, 0 };
        }

        int[] values = new int[3];
        values[0] = 0;
        values[1] = 0;
        values[2] = 0;

        if (!year.contains("-")) {
            values[0] = Integer.parseInt(year.substring(0, 4));
            values[1] = 1;
            return values;
        }

        if (year.length() == 5) {
            if (year.indexOf('-') == 0) {
                values[0] = Integer.parseInt(year.substring(1, 5));
                values[1] = 0;
            }
            if (year.indexOf('-') == 4) {
                values[0] = Integer.parseInt(year.substring(0, 4));
                values[1] = 4;
            }
            return values;
        }

        if (year.length() == 9) {
            values[0] = Integer.parseInt(year.substring(0, 4));
            values[2] = Integer.parseInt(year.substring(5, 9));
            values[1] = 4;
        }

        return values;
    }

    public static boolean hasMultiWordName(String name) {
        return name != null && name.contains(" ");
    }
}
