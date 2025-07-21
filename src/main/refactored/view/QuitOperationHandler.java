package view;

import java.util.Scanner;

import main.refactored.constants.ProductType;
import main.refactored.io.IProductDataExporter;
import main.refactored.model.Book;
import main.refactored.model.Electronics;
import main.refactored.model.Product;
import main.refactored.service.ProductService;

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