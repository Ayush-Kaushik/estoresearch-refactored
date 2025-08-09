package refactored.io;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.io.File;
import java.nio.file.Files;

import refactored.model.Book;
import refactored.model.Electronics;
import refactored.service.IProductService;
import refactored.service.ProductService;
import refactored.factory.IProductFactory;
import refactored.factory.ProductFactory;

public class ProductDataExporterTest {
  private static Logger logger;

  @Before
  public void enableVerboseLogging() {
    Logger root = Logger.getLogger("");
    root.setLevel(Level.FINEST);

    for (Handler h : root.getHandlers()) {
      h.setLevel(Level.FINEST);
      if (h instanceof ConsoleHandler) {
        h.setFormatter(new java.util.logging.SimpleFormatter());
      }
    }

    logger = Logger.getLogger(ProductDataImporterTest.class.getName());
    logger.setLevel(Level.FINEST);
  }

  private String createTempFile(String fileName) throws Exception {
    File file = Files.createTempFile(fileName, ".txt").toFile();
    file.deleteOnExit();

    return file.getAbsolutePath();
  }

  @Test
  public void givenValidData_ExporterMustExportToFile() throws Exception {
    logger.log(Level.FINE, "\n\nstart givenValidData_ExporterMustExportToFile");

    String filePath = createTempFile("export-product-data-");

    IProductService service = new ProductService();

    service.addProduct(new Book("123456", "Effective Java", 49.99, 2023, "Joshua Bloch", "Addison-Wesley"));
    service.addProduct(new Electronics("654321", "Smartphone", 599.99, 2022, "Samsung"));

    IProductDataExporter exporter = new ProductDataExporter(service);
    exporter.export(filePath);

    IProductService service2 = new ProductService();
    IProductFactory factory = new ProductFactory(logger);
    IProductDataImporter importer = new ProductDataImporter(service2, logger, factory);

    importer.importData(filePath);

    assertEquals(2, service2.getProductList().size());
    assertEquals("123456", service2.getProductList().get(0).getID());
    assertEquals("Effective Java", service2.getProductList().get(0).getName());
    assertEquals(49.99, service2.getProductList().get(0).getPrice(), 0.001);
    assertEquals(2023, service2.getProductList().get(0).getYear());
    assertEquals("Joshua Bloch", ((Book) service2.getProductList().get(0)).getAuthor());
    assertEquals("Addison-Wesley", ((Book) service2.getProductList().get(0)).getPublisher());
  }
}