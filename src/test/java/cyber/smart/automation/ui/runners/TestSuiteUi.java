package cyber.smart.automation.ui.runners;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;


@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        tags = {"@ui"},
        plugin = {"pretty"},
        features = "src/test/resources/features/ui",
        glue = "cyber.smart.automation.ui.steps"
)

public class TestSuiteUi {


}
