package estore.business;

public interface IProductCreationRequest {
    String getProductId();

    String getName();

    double getPrice();

    int getYear();
}
