package estore.e2e;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import estore.presentation.ConsolePrompts;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;

public class AddAndSearchSteps {

    private final EStoreSearchWorld eStoreSearchWorld;

    public AddAndSearchSteps(EStoreSearchWorld eStoreSearchWorld) {
        this.eStoreSearchWorld = eStoreSearchWorld;
    }

    @Given("a catalog file with no products")
    public void aCatalogFileWithNoProducts() throws IOException {
        Files.createFile(this.eStoreSearchWorld.getCatalogFile());
    }

    @When("the operator starts E-Search")
    public void theOperatorStartsESearch() throws IOException, TimeoutException {
        this.eStoreSearchWorld.startApplication();
    }

    @When("the operator adds products with:")
    public void theOperatorAddsProductsWith(DataTable table) throws IOException {
        List<Map<String, String>> products = table.asMaps(String.class, String.class);

        EStoreSearchProcess application = this.eStoreSearchWorld.getApplication();

        for (Map<String, String> product : products) {
            application.sendInput(ConsolePrompts.ADD_PRODUCT_OPTION);

            switch (product.get("type")) {
                case "book":
                    addBook(application, product);
                    break;

                case "electronics":
                    addElectronics(application, product);
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Unsupported product type: " + product.get("type"));
            }
        }
    }

    private void addBook(
            EStoreSearchProcess application,
            Map<String, String> book) throws IOException {

        application.sendInput(ConsolePrompts.ADD_BOOK_OPTION);
        application.sendInput(book.get("productID"));
        application.sendInput(book.get("year"));
        application.sendInput(book.get("name"));
        application.sendInput(book.get("price"));
        application.sendInput(book.get("authors"));
        application.sendInput(book.get("publisher"));
    }

    private void addElectronics(
            EStoreSearchProcess application,
            Map<String, String> electronics) throws IOException {

        application.sendInput(ConsolePrompts.ADD_ELECTRONICS_OPTION);
        application.sendInput(electronics.get("productID"));
        application.sendInput(electronics.get("year"));
        application.sendInput(electronics.get("name"));
        application.sendInput(electronics.get("price"));
        application.sendInput(electronics.get("manufacturer"));
    }

    @When("the operator searches for products named {string}")
    public void theOperatorSearchesForProductsNamed(String name) throws IOException {
        EStoreSearchProcess application = this.eStoreSearchWorld.getApplication();

        application.sendInput(ConsolePrompts.SEARCH_OPTION);
        application.sendInput(ConsolePrompts.SKIP_ENTER_SEARCH_QUERY_PARAMETER); // skip entering product id
        application.sendInput(ConsolePrompts.SHOULD_ENTER_SEARCH_QUERY_PARAMETER);
        application.sendInput(name);
        application.sendInput(ConsolePrompts.SKIP_ENTER_SEARCH_QUERY_PARAMETER); // skip adding year
    }

    @When("the operator quits")
    public void theOperatorQuits() {
        this.eStoreSearchWorld.stopApplication();
    }

    @Then("the search result should contain {int} products")
    public void theSearchResultContains(int productCount) {
        EStoreSearchProcess application = this.eStoreSearchWorld.getApplication();
        String output = application.getOutput();
        int actualCount = output.split("item ID: ", -1).length - 1;
        assertEquals("Unexpected number of products in search output: " + output, productCount, actualCount);
    }

    @Then("the program output includes {string}")
    public void theProgramOutputIncludes(String expected) {
        assertTrue("Expected program output to contain: " + expected,
                this.eStoreSearchWorld.getApplication().getOutput().contains(expected));
    }

    @And("the search results include product id {string}")
    public void theSearchResultsIncludeProductId(String productId) {
        assertTrue("Expected search output to contain product ID: " + productId,
                this.eStoreSearchWorld.getApplication().getOutput().contains("item ID: " + productId));
    }

    @And("the search result should contain")
    public void theSearchResultShouldContain(DataTable table) {
        String output = this.eStoreSearchWorld.getApplication().getOutput();
        List<Map<String, String>> expectedProducts = table.asMaps(String.class, String.class);
        for (Map<String, String> product : expectedProducts) {
            assertTrue("Expected search output to contain product ID: " + product.get("productID"),
                    output.contains("item ID: " + product.get("productID")));
            assertTrue("Expected search output to contain product name: " + product.get("name"),
                    output.contains(product.get("name")));
        }
    }

    @And("the search results include {string}")
    public void theSearchResultsInclude(String text) {
        assertTrue("Expected search output to contain: " + text,
                this.eStoreSearchWorld.getApplication().getOutput().contains(text));

    }
}
