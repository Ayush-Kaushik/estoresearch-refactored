package estore.e2e;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import estore.data.Book;
import estore.data.Electronics;
import estore.data.Product;
import estore.presentation.ConsolePrompts;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ReadProductCatalogSteps {
    private final EStoreSearchWorld eStoreSearchWorld;

    public ReadProductCatalogSteps(EStoreSearchWorld eStoreSearchWorld) {
        this.eStoreSearchWorld = eStoreSearchWorld;
    }

    @Given("a new catalog file")
    public void aNewCatalogFileWithProducts() throws IOException {
        Files.createFile(eStoreSearchWorld.getCatalogFile());
    }

    @And("catalog file contains an unsupported product type")
    public void catalogFileContainsUnsupportedProductType() throws IOException {
        String unsupportedProduct = "type = \"unsupported\"" + System.lineSeparator()
                + "productID = \"300001\"" + System.lineSeparator()
                + "name = \"Unsupported Product\"" + System.lineSeparator()
                + "price = \"10.0\"" + System.lineSeparator()
                + "year = \"2024\"" + System.lineSeparator() + System.lineSeparator();
        Files.write(eStoreSearchWorld.getCatalogFile(), unsupportedProduct.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.APPEND);
    }

    @And("catalog file contains {int} books")
    public void catalogFileContainsBooks(int count) throws IOException {
        for (int index = 0; index < count; index++) {
            int id = 100001 + index;
            String name = "Book " + (index + 1);

            Product book = new Book("" + id, name, index, id, "Scenario Author", "Scenario Press");
            appendProduct(book);
        }
    }

    @And("catalog file contains {int} electronics")
    public void catalogFileContainsElectronics(int count) throws IOException {
        for (int index = 0; index < count; index++) {
            int id = 200001 + index;
            String name = "Electronics " + (index + 1);
            int year = 2000 + index;
            double price = 10.0 + index;

            Product electronic = new Electronics("" + id, name, price, year, "Scenario Maker");
            appendProduct(electronic);
        }
    }

    @When("the operator views the product catalog metadata")
    public void theOperatorViewsProductCatalogMetadata() throws IOException {
        eStoreSearchWorld.getApplication().sendInput(ConsolePrompts.VIEW_METADATA_OPTION);
    }

    @Then("the program should display the catalog metadata")
    public void theProgramShouldDisplayCatalogMetadata() {
        assertOutputContains("Catalog metadata");
    }

    @Then("catalog metadata should indicate {int} products")
    public void catalogMetadataShouldIndicateCatalogCount(int count) {
        assertOutputContains("Total products: " + count);
    }

    @Then("catalog metadata should indicate {int} books")
    public void catalogMetadataShouldIndicateBooks(int count) {
        assertOutputContains("Books: " + count);
    }

    @Then("catalog metadata should indicate {int} electronics")
    public void catalogMetadataShouldIndicateElectronics(int count) {
        assertOutputContains("Electronics: " + count);
    }

    private void appendProduct(Product product) throws IOException {
        String serializedProduct = product.toString() + System.lineSeparator();
        Files.write(eStoreSearchWorld.getCatalogFile(), serializedProduct.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    private void assertOutputContains(String expected) {
        String output = eStoreSearchWorld.getApplication().getOutput();
        assertTrue("Expected program output to contain '" + expected + "' but was: " + output,
                output.contains(expected));
    }
}