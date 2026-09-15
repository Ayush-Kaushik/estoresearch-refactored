package estore.business;

import estore.dataaccess.Book;
import estore.dataaccess.Electronics;

/** TODO: Move the creation of product to a factory */
public class ProductAdditionService {
    private final IProductService productService;

    public ProductAdditionService(IProductService productService) {
        this.productService = productService;
    }

    public void addBook(String productId, int year, String name, double price, String author, String publisher) {
        productService.addProduct(new Book(productId, name, price, year, author, publisher));
    }

    public void addElectronics(String productId, int year, String name, double price, String maker) {
        productService.addProduct(new Electronics(productId, name, price, year, maker));
    }
}