
// import static org.junit.jupiter.api.Assertions.assertEquals;

// import org.junit.jupiter.api.Test;

public class Main {
  public static void main(String[] args) {
    ProductService productService = new ProductService();
    
    // Create a Book instance instead of abstract Product
    Book book = new Book("123456", "Java Programming", 12.99, 2020, "John Doe", "Tech Books");
    
    productService.addProduct(book);
    
    System.out.println("Book added successfully!");
    System.out.println(book.datadump());
  }
}
