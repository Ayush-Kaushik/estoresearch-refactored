package estore.business;

public interface ProductCreationRequest {
    String getProductId();

    String getName();

    double getPrice();

    int getYear();
}
