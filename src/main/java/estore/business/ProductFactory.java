package estore.business;

import estore.data.Book;
import estore.data.Electronics;
import estore.data.Product;

public final class ProductFactory {
    private ProductFactory() {
    }

    public static Product createProduct(ProductCreationRequest request) {
        if (request instanceof BookCreationRequest) {
            BookCreationRequest bookRequest = (BookCreationRequest) request;
            return new Book(
                    bookRequest.getProductId(),
                    bookRequest.getName(),
                    bookRequest.getPrice(),
                    bookRequest.getYear(),
                    bookRequest.getAuthor(),
                    bookRequest.getPublisher());
        }

        if (request instanceof ElectronicsCreationRequest) {
            ElectronicsCreationRequest electronicsRequest = (ElectronicsCreationRequest) request;
            return new Electronics(
                    electronicsRequest.getProductId(),
                    electronicsRequest.getName(),
                    electronicsRequest.getPrice(),
                    electronicsRequest.getYear(),
                    electronicsRequest.getMaker());
        }

        throw new IllegalArgumentException("Unsupported product creation request: " + request.getClass().getName());
    }
}
