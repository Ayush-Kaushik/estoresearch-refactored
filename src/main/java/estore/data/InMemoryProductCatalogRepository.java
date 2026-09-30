package estore.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import estore.logging.ILogger;
import estore.logging.Logger;

/**
 * In-memory repository implementation.
 *
 * This class stores catalog state and maintains the lookup indexes required for
 * search operations. Storage details are isolated behind the repository
 * interface.
 */
public class InMemoryProductCatalogRepository implements IProductCatalogRepository {
    private final List<Product> products;
    private final HashMap<String, ArrayList<Integer>> index;
    private final ILogger logger;

    public InMemoryProductCatalogRepository() {
        this(new ArrayList<Product>(), new HashMap<String, ArrayList<Integer>>(), new Logger());
    }

    public InMemoryProductCatalogRepository(List<Product> products, HashMap<String, ArrayList<Integer>> index) {
        this(products, index, new Logger());
    }

    public InMemoryProductCatalogRepository(List<Product> products, HashMap<String, ArrayList<Integer>> index,
            ILogger logger) {
        this.products = products;
        this.index = index;
        this.logger = logger;
    }

    @Override
    public void add(Product product) {
        logger.info("Adding product to repository: " + product.getID());
        products.add(product);
        rebuildIndex();
    }

    @Override
    public List<Product> search(String id, String name, String year) {
        logger.info("Repository search id=" + id + ", name=" + name + ", year=" + year);
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (matches(product, id, name, year)) {
                results.add(product);
            }
        }
        logger.info("Repository search returned " + results.size() + " matches");
        return results;
    }

    @Override
    public List<Product> findAll() {
        logger.info("Fetching all products from repository");
        return Collections.unmodifiableList(new ArrayList<>(products));
    }

    private void rebuildIndex() {
        index.clear();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            String normalizedName = product.getName().toLowerCase();
            String[] tokens = normalizedName.split("\\s+");
            for (String token : tokens) {
                index.computeIfAbsent(token, key -> new ArrayList<>()).add(i);
            }
        }
    }

    private boolean matches(Product product, String id, String name, String year) {
        if (id != null && !id.isEmpty() && !product.getID().equals(id)) {
            return false;
        }
        if (name != null && !name.isEmpty() && !product.getName().toLowerCase().contains(name.toLowerCase())) {
            return false;
        }
        if (year != null && !year.isEmpty() && !matchesYear(product.getYear(), year)) {
            return false;
        }
        return true;
    }

    private boolean matchesYear(int productYear, String query) {
        if (query.matches("\\d{4}")) {
            return productYear == Integer.parseInt(query);
        }
        if (query.matches("-\\d{4}")) {
            return productYear <= Integer.parseInt(query.substring(1));
        }
        if (query.matches("\\d{4}-")) {
            return productYear >= Integer.parseInt(query.substring(0, 4));
        }
        if (query.matches("\\d{4}-\\d{4}")) {
            int minimumYear = Integer.parseInt(query.substring(0, 4));
            int maximumYear = Integer.parseInt(query.substring(5, 9));
            return productYear >= minimumYear && productYear <= maximumYear;
        }
        return false;
    }
}
