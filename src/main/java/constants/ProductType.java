package constants;

public enum ProductType {
  BOOK,
  ELECTRONICS;

  private static String productTypes;

  public static ProductType fromString(int type) {
    return ProductType.valueOf(type);
  }

  public static String getProductTypes() {

    if (productTypes == null) {
      StringBuilder sb = new StringBuilder();
      int counter = 0;

      for (ProductType productType : ProductType.values()) {
        sb.append("[" + counter + "] " + productType.name()).append(" ");
        counter++;
      }

      productTypes = sb.toString();
    }

    return productTypes;
  }
}
