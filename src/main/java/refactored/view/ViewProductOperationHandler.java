package refactored.view;

import java.util.ArrayList;

import refactored.service.IProductService;
import refactored.model.Product;

public class ViewProductOperationHandler implements IOperationHandler {
  private IProductService productService;

  public ViewProductOperationHandler(IProductService productService) {
    this.productService = productService;
  }

  public void execute() {
    System.out.println("Items:");

    ArrayList<Product> products = this.productService.getProductList();

    for (Product product : products) {
      System.out.println(product.datadump());
    }
  }
}