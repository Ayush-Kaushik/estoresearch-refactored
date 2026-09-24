package estore.e2e;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

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
    public void theOperatorStartsESearch() throws IOException {
        this.eStoreSearchWorld.startApplication();
    }

    @When("the operator adds a book with:")
    public void theOperatorAddsABookWith(io.cucumber.datatable.DataTable table) throws IOException {
        Map<String, String> fields = table.asMap(String.class, String.class);
        EStoreSearchProcess application = this.eStoreSearchWorld.getApplication();

        application.sendInput("1");
        application.sendInput("1");
        application.sendInput(fields.get("productID"));
        application.sendInput(fields.get("year"));
        application.sendInput(fields.get("name"));
        application.sendInput(fields.get("price"));
        application.sendInput(fields.get("authors"));
        application.sendInput(fields.get("publisher"));
    }

    @When("the operator searches for products named {string}")
    public void theOperatorSearchesForProductsNamed(String name) {

    }

    @When("the operator quits")
    public void theOperatorQuits() {
        this.eStoreSearchWorld.stopApplication();
    }

    @Then("the program output includes {string}")
    public void theProgramOutputIncludes(String expected) {

    }

    @And("the search results include product id {string}")
    public void theSearchResultsIncludeProductId(String productId) {

    }

    @And("the search results include {string}")
    public void theSearchResultsInclude(String text) {

    }
}
