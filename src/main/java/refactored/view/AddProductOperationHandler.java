package refactored.view;

import java.util.Scanner;

import refactored.service.IProductService;
import refactored.constants.ProductType;
import refactored.model.Book;
import refactored.model.Electronics;
import refactored.model.Product;

public class AddProductOperationHandler implements IOperationHandler {

  private Scanner scanner;
  private IProductService productService;

  public AddProductOperationHandler(IProductService productService, Scanner scanner) {
    this.scanner = scanner;
    this.productService = productService;
  }

  public AddProductOperationHandler(Scanner scanner, IProductService productService) {
    this.scanner = scanner;
    this.productService = productService;
  }

  private String promptId() {
    while (true) {
      try {
        System.out.println("Enter product id (6 digits):");
        String productId = scanner.nextLine();
        Product.validateID(productId);

        if (this.productService.productExists(productId)) {
          System.out.println("Product with id " + productId + " already exists.");
          continue;
        }

        return productId;
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private int promptYear() {
    while (true) {
      try {
        System.out.println("Enter product year: ");
        String input = scanner.nextLine();
        int year = Integer.parseInt(input);

        Product.validateYear(year);
        return year;
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private String promptName() {
    while (true) {
      try {
        System.out.println("Enter product name: ");
        String name = scanner.nextLine();

        Product.validateName(name);
        return name;

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private double promptPrice() {
    while (true) {
      try {
        System.out.println("Enter the price of item: ");

        String inputPrice = scanner.nextLine();
        double price = Product.validatePrice(inputPrice);
        return price;

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private String promptPublisher() {
    while (true) {
      try {
        System.out.println("Enter book publisher: ");
        String publisher = this.scanner.nextLine();
        Book.validatePublisher(publisher);
        return publisher;

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private String promptAuthor() {
    while (true) {
      try {
        System.out.println("Enter book author: ");
        String author = this.scanner.nextLine();
        Book.validateAuthor(author);
        return author;

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private String promptMaker() {
    while (true) {
      try {
        System.out.println("Enter electronics maker: ");
        String maker = this.scanner.nextLine();
        Electronics.validateMaker(maker);
        return maker;

      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public void execute() {
    int option = 0;
    ProductType productType = null;

    while (true) {
      System.out.println("[Add New Product] Enter product type " + ProductType.getOptions());

      try {
        String selection = scanner.nextLine();

        option = Integer.parseInt(selection);
        productType = ProductType.findByType(option);

        if (productType == null) {
          System.out.println("Invalid option, Try again.");
          continue;
        } else {
          break;
        }

      } catch (Exception exception) {
        System.out.println("Invalid option, Try again.");
        continue;
      }
    }

    String productID = this.promptId();
    String name = this.promptName();
    int year = this.promptYear();
    double price = this.promptPrice();

    switch (productType) {
      case BOOK:
        String author = this.promptAuthor();
        String publisher = this.promptPublisher();
        Product book = new Book(productID, name, price, year, author, publisher);
        this.productService.addProduct(book);
        break;

      case ELECTRONICS:
        String maker = this.promptMaker();
        Product electronic = new Electronics(productID, name, price, year, maker);
        this.productService.addProduct(electronic);
        break;
    }
  }
}