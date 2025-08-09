package refactored.service;

import java.util.ArrayList;
import java.util.Objects;

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
        Product product = findProductById(productId);

        if (Objects.isNull(product)) {
            return false;
        }

        return true;
    }

    public boolean tryAddProduct(Product product) {
        if (Objects.isNull(product)) {
            return false;
        }

        if (productExists(product.getID())) {
            return false;
        }

        addProduct(product);
        return true;
    }

    public void addProduct(Product product) {
        if (Objects.isNull(product)) {
            throw new IllegalArgumentException("Product cannot be null.");
        }

        if (productExists(product.getID())) {
            throw new IllegalArgumentException("Product with id " + product.getID() + " already exists.");
        }

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
}
