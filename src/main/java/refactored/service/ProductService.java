package refactored.service;

import java.util.ArrayList;

import refactored.data.IInvertedIndex;
import refactored.data.InvertedIndex;

import refactored.model.Product;

public class ProductService implements IProductService {
    private ArrayList<Product> productList;
    private IInvertedIndex nameSearchInvertedIndex;

    public ProductService() {
        this.productList = new ArrayList<Product>();
        this.nameSearchInvertedIndex = new InvertedIndex();
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
        String[] searchTokens = product.getName().toLowerCase().split(" ");
        this.nameSearchInvertedIndex.add(searchTokens, productList.size() - 1);
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public Product findProductById(String id) {
        for (Product product : productList) {
            if (product.getID().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public int getSize() {
        return productList.size();
    }
}
