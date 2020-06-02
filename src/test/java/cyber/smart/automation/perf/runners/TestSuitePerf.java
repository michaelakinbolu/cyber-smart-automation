package cyber.smart.automation.perf.runners;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        tags = {"@perf"},
        plugin = {"pretty"},
        features = "src/test/resources/features/perf",
        glue = "cyber.smart.automation.perf.steps"
)
public class TestSuitePerf {


}
