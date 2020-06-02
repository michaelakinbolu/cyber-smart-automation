package cyber.smart.automation.ui.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import cyber.smart.automation.ui.pages.GoogleHomePage;
import cyber.smart.automation.utils.PropertyReader;

import java.io.IOException;


public class StepDefinitions {

    GoogleHomePage googleHomePage;


    @Given("^I am on the Google homepage$")
    public void iAmOnGoogleHomePage() throws IOException {
        googleHomePage.openUrl(PropertyReader.getPropValues("url"));

    }

    @When("^I enter \"([^\"]*)\" into the search box$")
    public void iEnterSearchTerm(String term) {
        googleHomePage.searchTerm(term);


    }

    @When("^I click the search button$")
    public void iClickSearchButton(){
        googleHomePage.clickButton();

    }

    @Then("^I read a page that is titled \"([^\"]*)\"$")
    public void isResultTitleAsExpected(String eTitle) {
        String aTitle = googleHomePage.readTitle();
        System.out.println("Actual: " + aTitle);
        System.out.println("Expected: " + eTitle);
        Assert.assertEquals(aTitle, eTitle);

    }

}