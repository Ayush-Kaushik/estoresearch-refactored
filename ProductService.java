
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ProductService {
    private HashMap<String, Product> productMap;

    public ProductService() {
        this.productMap = new HashMap<>();
    }

    public String validateID(String productId) {

        if (productId.length() != 6 || !productId.matches("[0-9]+")) {
            throw new IllegalArgumentException("Cannot add product: ID must be 6 digits long and contain only numbers");
        }

        if (productMap.containsKey(productId)) {
            throw new IllegalArgumentException("Cannot add product: ID already exists");
        }

        return productId;
    }

    public void addProduct(Product product) {
        validateID(product.getID());

        productMap.put(product.getID(), product);
    }

    public Product findProductById(String id) {
        if (productMap.containsKey(id)) {
            return productMap.get(id);
        }

        return null;
    }
}
