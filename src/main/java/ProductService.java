import java.util.ArrayList;
import java.util.HashMap;

public class ProductService {
    private ArrayList<Product> productList;
    public HashMap<String, ArrayList<Integer>> productSearchMap;

    public ProductService() {
        this.productList = new ArrayList<Product>();
        this.productSearchMap = new HashMap<String, ArrayList<Integer>>();
    }

    public boolean productExists(String productId) {
        for (Product product : productList) {
            if (product.getID().equals(productId)) {
                return true;
            }
        }

        return false;
    }

    public void addProduct(Product product) {
        productList.add(product);
        updateProductSearchMap(product.getName(), productList.size() - 1);
    }

    public int getSize() {
        return productList.size() - 1;
    }

    public int getIndex(String id) {
        int index = 0;
        for (Product product : productList) {
            if (product.getID().equals(id)) {
                return index;
            }

            index++;
        }

        return -1;
    }

    public Product findProductById(String id) {
        for (Product product : productList) {
            if (product.getID().equals(id)) {
                return product;
            }
        }

        return null;
    }

    private void updateProductSearchMap(String name, int index) {
        String[] searchTokens = name.toLowerCase().split(" ");

        for (String token : searchTokens) {
            if (productSearchMap.containsKey(token)) {
                productSearchMap.get(token).add(index);
            } else {
                productSearchMap.put(token.toLowerCase(), new ArrayList<Integer>());
                productSearchMap.get(token.toLowerCase()).add(index);
            }
        }
    }

    public ArrayList<Integer> getProductIndexes(String searchToken) {
        return productSearchMap.get(searchToken);
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }
}
