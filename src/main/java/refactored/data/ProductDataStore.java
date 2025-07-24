package refactored.data;

import java.util.ArrayList;
import refactored.model.Product;

public class ProductDataStore {
  private IInvertedIndex nameSearchInvertedIndex;
  private ArrayList<Product> productList;

  public ProductDataStore() {
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

  public int getSize() {
    return productList.size() - 1;
  }

  public Product findProductById(String id) {
    for (Product product : productList) {
      if (product.getID().equals(id)) {
        return product;
      }
    }

    return null;
  }

  public ArrayList<Product> getProductList() {
    return productList;
  }

}