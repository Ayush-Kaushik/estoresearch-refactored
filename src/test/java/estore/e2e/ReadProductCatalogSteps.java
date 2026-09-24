package estore.e2e;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ReadProductCatalogSteps {

    @Given("a new catalog file with products")
    public void aNewCatalogFileWithProducts() {

    }

    @And("catalog file contains {int} books products")
    public void catalogFileContainsBooks(int count) {

    }

    @And("catalog file contains {int} electronics product")
    public void catalogFileContainsElectronics(int count) {

    }

    @When("the operator views the product catalog metadata")
    public void theOperatorViewsProductCatalogMetadata() {

    }

    @Then("the program should indicate that that the catalog contains {int} products")
    public void theProgramShouldIndicateCatalogCount(int count) {

    }

    @Then("the program should display the catalog metadata")
    public void theProgramShouldDisplayCatalogMetadata() {

    }

    @Then("catalog metadata should indicate that the catalog contains {int} products")
    public void catalogMetadataShouldIndicateCatalogCount(int count) {

    }

    @Then("catalog metadata should indicate {int} books products")
    public void catalogMetadataShouldIndicateBooks(int count) {

    }

    @Then("catalog metadata should indicate {int} electronics products")
    public void catalogMetadataShouldIndicateElectronics(int count) {
    }

    @Then("catalog metadata should indicate {int} electronics product")
    public void catalogMetadataShouldIndicateOneElectronics(int count) {
    }
}