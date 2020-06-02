package cyber.smart.automation.api.runners;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;


@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        tags = {"@api1"},
        plugin = {"pretty"},
        features = "src/test/resources/features/api",
        glue = "cyber.smart.automation.api.steps"
)

public class TestSuiteApi  {

}
