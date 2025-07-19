package io;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import service.ProductService;
import model.Product;

public class ProductDataExporter {

  private ProductService productService;
  private PrintWriter outputStream;

  public ProductDataExporter(ProductService productService) {
    this.productService = productService;
  }

  public void export(String outputFilePath) {
    try {
      this.outputStream = new PrintWriter(new FileOutputStream(outputFilePath));

      for (Product product : this.productService.getProductList()) {
        outputStream.println(product.datadump());
      }

      System.out.println("Successful write to file");
      outputStream.close();
    }

    catch (FileNotFoundException e) {
      // TODO: this needs a retry mechanism for failing safely
      System.out.println("Unable to open file");
      System.exit(0);
    }
  }
}