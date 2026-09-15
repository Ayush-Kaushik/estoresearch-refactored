package estore;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class AddAndSearchSteps {

    private final EStoreSearchWorld world = EStoreSearchWorld.current();

    @Given("a catalog file with no products")
    public void aCatalogFileWithNoProducts() throws Exception {
        world.createEmptyCatalog();
    }

    @When("the operator starts E-Search")
    public void theOperatorStartsESearch() throws IOException {
        world.startProgram();
    }

    @When("the operator adds a book with:")
    public void theOperatorAddsABookWith(io.cucumber.datatable.DataTable table) {
        Map<String, String> fields = table.asMap(String.class, String.class);
        world.addBook(
                fields.get("productID"),
                fields.get("year"),
                fields.get("name"),
                fields.get("price"),
                fields.get("authors"),
                fields.get("publisher"));
    }

    @When("the operator searches for products named {string}")
    public void theOperatorSearchesForProductsNamed(String name) {
        world.searchByName(name);
    }

    @When("the operator quits")
    public void theOperatorQuits() {
        world.quit();
    }

    @Then("the program output includes {string}")
    public void theProgramOutputIncludes(String expected) throws Exception {
        assertTrue(
                "Expected output to contain [" + expected + "] but was:\n" + world.getOutput(),
                world.getOutput().contains(expected));
    }

    @And("the search results include product id {string}")
    public void theSearchResultsIncludeProductId(String productId) throws Exception {
        assertTrue(
                "Expected search results to include product id [" + productId + "] but was:\n" + world.getOutput(),
                world.getOutput().contains(productId));
    }

    @And("the search results include {string}")
    public void theSearchResultsInclude(String text) throws Exception {
        assertTrue(
                "Expected search results to include [" + text + "] but was:\n" + world.getOutput(),
                world.getOutput().contains(text));
    }
}
