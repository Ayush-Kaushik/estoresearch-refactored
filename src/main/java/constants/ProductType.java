package constants;

import java.util.HashMap;

public enum ProductType {
  BOOK(0),
  ELECTRONICS(1);

  private final int index;

  ProductType(int value) {
    this.index = value;
  }

  public int getIndex() {
    return this.index;
  }

  private static String productTypes = "";
  private static final HashMap<Integer, ProductType> productTypeMap = new HashMap<Integer, ProductType>();

  static {
    for (ProductType type : ProductType.values()) {
      productTypeMap.put(type.getIndex(), type);
    }

    StringBuilder sb = new StringBuilder();
    int counter = 0;

    for (ProductType productType : ProductType.values()) {
      sb.append("[" + counter + "] " + productType.name()).append(" ");
      counter++;
    }

    productTypes = sb.toString();
  }

  public static String getProductTypeOptions() {
    return productTypes;
  }

  public static ProductType findByType(int type) {
    return productTypeMap.get(type);
  }
}
