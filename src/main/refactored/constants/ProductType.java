package main.refactored.constants;

import java.util.HashMap;

public enum ProductType {
  BOOK(1),
  ELECTRONICS(2);

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
    StringBuilder sb = new StringBuilder();

    for (ProductType type : ProductType.values()) {
      productTypeMap.put(type.getIndex(), type);
      sb.append("[" + type.getIndex() + "] " + type.name()).append(" ");
    }

    productTypes = sb.toString();
  }

  public static String getOptions() {
    return productTypes;
  }

  public static ProductType findByType(int type) {
    return productTypeMap.get(type);
  }
}
