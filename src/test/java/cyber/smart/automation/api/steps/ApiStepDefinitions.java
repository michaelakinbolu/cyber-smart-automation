package cyber.smart.automation.api.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import cyber.smart.automation.api.stepLib.ApiActions;

import java.io.IOException;

public class ApiStepDefinitions {

    @Steps
    private static ApiActions apiActions;

    @Given("^the \"([^\"]*)\" endpoint is available$")
    public void  theEndpointIsAvailable(String name) throws IOException {
        ApiActions.restEndpointIsAvailable(name);
    }

    @When("^I hit the endpoint$")
    public void iHitTheEndpoint()  {
        apiActions.hitEndpoint();
    }

    @Then("^I should get status code (\\d+) back$")
    public void iShouldGetStatusCode(int responseCode) {
        apiActions.assertStatusCode(responseCode);
    }

    @When("^I set the endpoint$")
    public void iSetTheEndpoint() {
        apiActions.setEndpoint();
    }

    @When("^I get \"([^\"]*)\" resource$")
    public void iGetResource(String resourceName) throws IOException {

        apiActions.getResource(resourceName);

    }






}