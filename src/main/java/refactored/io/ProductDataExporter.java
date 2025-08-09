package refactored.io;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.logging.Logger;
import java.util.logging.Level;

import refactored.service.IProductService;
import refactored.model.Product;

public class ProductDataExporter implements IProductDataExporter {

  private IProductService productService;
  private PrintWriter outputStream;
  private Logger logger;

  public ProductDataExporter(IProductService productService, Logger logger) {
    this.productService = productService;
    this.logger = logger;
  }

  public void export(String outputFilePath) {
    try {
      this.outputStream = new PrintWriter(new FileOutputStream(outputFilePath));

      for (Product product : this.productService.getProductList()) {
        outputStream.println(product.datadump());
      }

      outputStream.flush();

      System.out.println("Successful exported to file: " + outputFilePath);
      logger.log(Level.INFO, "Unable to open export file: %s", outputFilePath);

      outputStream.close();

    } catch (FileNotFoundException e) {

      System.out.println("Unable to open export file");
      logger.log(Level.SEVERE, "Unable to open export file", e);
      System.exit(0);
    }
  }
}