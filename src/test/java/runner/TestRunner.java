package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/featureFiles"},
        tags = "@TEST1",
        glue = {"step.definitions"},
        monochrome = true
)
public class TestRunner {
}
