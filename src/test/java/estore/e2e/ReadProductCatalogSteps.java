package estore.e2e;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

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

    @And("catalog file contains {int} books")
    public void catalogFileContainsBooks(int count) throws IOException {
        for (int index = 0; index < count; index++) {
            appendProduct("book", 100001 + index, "Book " + (index + 1),
                    "authors = \"Scenario Author\"\npublisher = \"Scenario Press\"\n");
        }
    }

    @And("catalog file contains {int} electronics")
    public void catalogFileContainsElectronics(int count) throws IOException {
        for (int index = 0; index < count; index++) {
            appendProduct("electronics", 200001 + index, "Electronics " + (index + 1),
                    "maker = \"Scenario Maker\"\n");
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

    private void appendProduct(String type, int id, String name, String typeSpecificFields) throws IOException {
        String product = "type = \"" + type + "\"\n"
                + "productID = \"" + id + "\"\n"
                + "name = \"" + name + "\"\n"
                + "price = \"10.00\"\n"
                + "year = \"2024\"\n"
                + typeSpecificFields
                + "\n";
        Files.write(eStoreSearchWorld.getCatalogFile(), product.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.APPEND);
    }

    private void assertOutputContains(String expected) {
        String output = eStoreSearchWorld.getApplication().getOutput();
        assertTrue("Expected program output to contain '" + expected + "' but was: " + output,
                output.contains(expected));
    }
}