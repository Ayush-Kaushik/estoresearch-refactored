package estore.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import estore.business.BookCreationRequest;
import estore.business.ElectronicsCreationRequest;
import estore.business.IProductCreationRequest;
import estore.business.ProductFactory;
import estore.data.Book;
import estore.data.Electronics;
import estore.data.Product;

public class ProductFactoryTest {

    @Test
    public void shouldCreateBookFromBookCreationRequest() {
        IProductCreationRequest request = new BookCreationRequest(
                "123456", "Learning Java", 45.99, 2023, "Jane Doe", "Education Press");

        Product product = ProductFactory.createProduct(request);

        assertTrue(product instanceof Book);
        assertEquals("123456", product.getID());
        assertEquals("Jane Doe", ((Book) product).getAuthor());
        assertEquals("Education Press", ((Book) product).getPublisher());
    }

    @Test
    public void shouldCreateElectronicsFromElectronicsCreationRequest() {
        IProductCreationRequest request = new ElectronicsCreationRequest(
                "654321", "Laptop", 999.99, 2024, "Dell");

        Product product = ProductFactory.createProduct(request);

        assertTrue(product instanceof Electronics);
        assertEquals("654321", product.getID());
        assertEquals("Dell", ((Electronics) product).getMaker());
    }
}
