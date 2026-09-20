package estore.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:estore",
        glue = "estore.e2e",
        plugin = {"pretty", "summary", "html:target/cucumber-report.html"},
        snippets = io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE)
public class TestRunnerIT {
}
