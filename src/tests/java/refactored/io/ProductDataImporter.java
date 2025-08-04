package refactored.io;

import org.junit.Test;

import junit.framework.Assert;
import refactored.service.IProductService;
import refactored.service.ProductService;

public class ProductDataImporterTest {

  public ProductDataImporterTest() {

  }

  @Test
  public void givenValidImportFile_ImporterMustLoadFile() {

    IProductService service = new ProductService();
    IProductDataImporter importer = new ProductDataImporter();
    importer.load("/resources/import.txt");

    int actual = service.getProductList().size();
    assertEquals(100, actual);
  }

}