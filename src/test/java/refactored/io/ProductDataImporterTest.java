package refactored.io;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.junit.Before;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;

import refactored.service.IProductService;
import refactored.service.ProductService;
import refactored.factory.IProductFactory;
import refactored.factory.ProductFactory;
import refactored.model.Product;
import refactored.model.Book;
import refactored.model.Electronics;

public class ProductDataImporterTest {

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

  private String createTempFile(String fileName, List<String> lines) throws Exception {
    File file = Files.createTempFile(fileName, ".txt").toFile();
    file.deleteOnExit();

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      for (String line : lines) {
        writer.write(line);
        writer.newLine();
      }
    }

    return file.getAbsolutePath();
  }

  @Test
  public void givenValidImportFile_ImporterMustLoadFile() throws Exception {

    logger.log(Level.FINE, "\n\nstart givenValidImportFile_ImporterMustLoadFile");

    List<String> lines = List.of(
        "type=book",
        "id=123456",
        "name=Effective Java",
        "price=49.99",
        "year=2023",
        "authors=Joshua Bloch",
        "publisher=Addison-Wesley",
        "",
        "type=electronics",
        "id=654321",
        "name=Smartphone",
        "price=599.99",
        "year=2022",
        "maker=Samsung",
        "");

    String filePath = createTempFile("product-data-", lines);

    IProductService service = new ProductService();
    IProductFactory factory = new ProductFactory(logger);

    IProductDataImporter importer = new ProductDataImporter(service, logger, factory);
    importer.importData(filePath);

    ArrayList<Product> actualList = service.getProductList();

    assertEquals(2, actualList.size());
    assertEquals("123456", actualList.get(0).getID());
    assertEquals("Effective Java", actualList.get(0).getName());
    assertEquals(49.99, actualList.get(0).getPrice(), 0.001);
    assertEquals(2023, actualList.get(0).getYear());
    assertEquals("Joshua Bloch", ((Book) actualList.get(0)).getAuthor());
    assertEquals("Addison-Wesley", ((Book) actualList.get(0)).getPublisher());

    assertEquals("654321", actualList.get(1).getID());
    assertEquals("Smartphone", actualList.get(1).getName());
    assertEquals(599.99, actualList.get(1).getPrice(), 0.001);
    assertEquals(2022, actualList.get(1).getYear());
    assertEquals("Samsung", ((Electronics) actualList.get(1)).getMaker());
  }

  @Test
  public void givenInValidImportFile_WithDuplicateEntries_ImporterMustSkipDuplicates() throws Exception {

    logger.log(Level.FINE, "\n\nstart givenInValidImportFile_WithDuplicateEntries_ImporterMustSkipDuplicates");

    List<String> lines = List.of(
        "type=book",
        "id=123456",
        "name=Effective Java",
        "price=49.99",
        "year=2023",
        "authors=Joshua Bloch",
        "publisher=Addison-Wesley",
        "",
        "type=book",
        "id=123456",
        "name=Effective Java",
        "price=49.99",
        "year=2023",
        "authors=Joshua Bloch",
        "publisher=Addison-Wesley",
        "",
        "type=electronics",
        "id=654321",
        "name=Smartphone",
        "price=599.99",
        "year=2022",
        "maker=Samsung",
        "");

    String filePath = createTempFile("product-data-", lines);

    IProductService service = new ProductService();
    IProductFactory factory = new ProductFactory(logger);

    IProductDataImporter importer = new ProductDataImporter(service, logger, factory);
    importer.importData(filePath);

    ArrayList<Product> actualList = service.getProductList();
    assertEquals(2, actualList.size());
  }

  @Test
  public void givenInValidImportFile_WithCorruptData_ImporterMustSkipCorruptedData() throws Exception {

    logger.log(Level.FINE, "\n\nstart givenInValidImportFile_WithCorruptData_ImporterMustSkipCorruptedData");

    List<String> lines = List.of(
        "type=book",
        "id=CorruptId",
        "name=Effective Java",
        "price=49.99",
        "year=2023",
        "author=Joshua Bloch",
        "publisher=Addison-Wesley",
        "",
        "type=book",
        "id=123456",
        "name=Effective Java",
        "price=49.99",
        "year=2023",
        "author=Joshua Bloch",
        "publisher=Addison-Wesley",
        "",
        "type=electronics",
        "id=654321",
        "name=Smartphone",
        "price=599.99",
        "year=2022",
        "maker=Samsung",
        "");

    String filePath = createTempFile("product-data-", lines);

    IProductService service = new ProductService();
    IProductFactory factory = new ProductFactory(logger);

    IProductDataImporter importer = new ProductDataImporter(service, logger, factory);
    importer.importData(filePath);

    ArrayList<Product> actualList = service.getProductList();
    assertEquals(2, actualList.size());
  }
}