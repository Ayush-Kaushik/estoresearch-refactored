package estore.integration;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import estore.business.IProductService;
import estore.business.ProductSearchService;
import estore.business.ProductService;
import estore.business.SearchQuery;
import estore.data.Book;
import estore.data.InMemoryProductCatalogRepository;
import estore.data.Product;

public class ProductSearchServiceTest {

    @Test
    public void shouldSearchProductsByName() {
        List<Product> products = new ArrayList<Product>();
        products.add(new Book("123456", "Learning Java", 45.99, 2023, "Jane Doe", "Education Press"));
        products.add(new Book("654321", "Other Book", 20.00, 2022, "Test Author", "Other Press"));

        IProductService productService = new ProductService(
                new InMemoryProductCatalogRepository(products, new HashMap<String, ArrayList<Integer>>()));
        ProductSearchService searchService = new ProductSearchService(productService);

        List<Product> results = searchService.search(new SearchQuery("", "Learning Java", ""));

        assertEquals(1, results.size());
        assertEquals("123456", results.get(0).getID());
    }

    @Test
    public void shouldKeepCatalogStateInsideRepository() {
        InMemoryProductCatalogRepository catalogRepository = new InMemoryProductCatalogRepository();
        catalogRepository.add(new Book("123456", "Learning Java", 45.99, 2023, "Jane Doe", "Education Press"));
        catalogRepository.add(new Book("654321", "Other Book", 20.00, 2022, "Test Author", "Other Press"));

        assertEquals(2, catalogRepository.findAll().size());

        IProductService productService = new ProductService(catalogRepository);
        ProductSearchService searchService = new ProductSearchService(productService);
        List<Product> results = searchService.search(new SearchQuery("", "Learning Java", ""));

        assertEquals(1, results.size());
        assertEquals("123456", results.get(0).getID());
    }
}
