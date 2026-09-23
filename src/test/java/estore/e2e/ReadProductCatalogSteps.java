package estore.e2e;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import estore.presentation.ConsoleMessages;

import static org.junit.Assert.assertTrue;

public class ReadProductCatalogSteps {

    private final EStoreSearchWorld world = EStoreSearchWorld.current();

    @Given("a new catalog file with products")
    public void aNewCatalogFileWithProducts() throws Exception {
        world.createCatalogWithProducts();
    }

    @And("catalog file contains {int} books products")
    public void catalogFileContainsBooks(int count) {
        for (int i = 0; i < count; i++) {
            world.addBookProduct();
        }
    }

    @And("catalog file contains {int} electronics product")
    public void catalogFileContainsElectronics(int count) {
        for (int i = 0; i < count; i++) {
            world.addElectronicsProduct();
        }
    }

    @When("the operator views the product catalog metadata")
    public void theOperatorViewsProductCatalogMetadata() {
        world.viewMetadata();
    }

    @Then("the program should indicate that that the catalog contains {int} products")
    public void theProgramShouldIndicateCatalogCount(int count) throws Exception {
        assertOutputContains(ConsoleMessages.TOTAL_PRODUCTS + count, world);
    }

    @Then("the program should display the catalog metadata")
    public void theProgramShouldDisplayCatalogMetadata() throws Exception {
        assertOutputContains(ConsoleMessages.CATALOG_METADATA, world);
    }

    @Then("catalog metadata should indicate that the catalog contains {int} products")
    public void catalogMetadataShouldIndicateCatalogCount(int count) throws Exception {
        assertOutputContains(ConsoleMessages.TOTAL_PRODUCTS + count, world);
    }

    @Then("catalog metadata should indicate {int} books products")
    public void catalogMetadataShouldIndicateBooks(int count) throws Exception {
        assertOutputContains(ConsoleMessages.BOOKS + count, world);
    }

    @Then("catalog metadata should indicate {int} electronics products")
    public void catalogMetadataShouldIndicateElectronics(int count) throws Exception {
        assertOutputContains(ConsoleMessages.ELECTRONICS + count, world);
    }

    @Then("catalog metadata should indicate {int} electronics product")
    public void catalogMetadataShouldIndicateOneElectronics(int count) throws Exception {
        catalogMetadataShouldIndicateElectronics(count);
    }

    private static void assertOutputContains(String expected, EStoreSearchWorld world) throws Exception {
        assertTrue("Expected output to contain [" + expected + "] but was:\n" + world.getOutput(),
                world.getOutput().contains(expected));
    }
}