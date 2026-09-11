package estore;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:estore",
        glue = "estore",
        plugin = {"pretty", "summary"},
        snippets = io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE)
public class RunCucumberTest {
}
