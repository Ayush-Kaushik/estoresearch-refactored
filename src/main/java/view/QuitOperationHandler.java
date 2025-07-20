package view;

import java.util.Scanner;

import constants.ProductType;
import io.IProductDataExporter;
import model.Book;
import model.Electronics;
import model.Product;
import service.ProductService;

public class QuitOperationHandler implements IOperationHandler {

  private IProductDataExporter ProductDataExporter;

  public QuitOperationHandler(IProductDataExporter productDataExporter) {
    this.ProductDataExporter = productDataExporter;
  }

  public void execute() {
    // TODO: pass the file name as an argument to the export method
    this.ProductDataExporter.export(); // arguments[0]
    System.exit(0);
  }
}