package estore.dataaccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

    public InMemoryProductCatalogRepository() {
        this(new ArrayList<Product>(), new HashMap<String, ArrayList<Integer>>());
    }

    public InMemoryProductCatalogRepository(List<Product> products, HashMap<String, ArrayList<Integer>> index) {
        this.products = products;
        this.index = index;
    }

    @Override
    public void add(Product product) {
        products.add(product);
        rebuildIndex();
    }

    @Override
    public List<Product> search(String id, String name, String year) {
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (matches(product, id, name, year)) {
                results.add(product);
            }
        }
        return results;
    }

    @Override
    public void delete(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getID().equals(id)) {
                products.remove(i);
                rebuildIndex();
                return;
            }
        }
    }

    @Override
    public List<Product> findAll() {
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
        if (year != null && !year.isEmpty() && !String.valueOf(product.getYear()).contains(year)) {
            return false;
        }
        return true;
    }
}
