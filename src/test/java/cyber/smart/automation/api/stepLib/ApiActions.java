package cyber.smart.automation.api.stepLib;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import cyber.smart.automation.utils.PropertyReader;

import java.io.*;

import static io.restassured.RestAssured.given;


public class ApiActions {

    public ApiActions() {
    }
    private Response response;
    private ValidatableResponse json;
    private static String endpointRoot = null;

    @Step
    public static void restEndpointIsAvailable(String name) throws IOException {
        endpointRoot = PropertyReader.getPropValues(name);
        Assert.assertNotNull(endpointRoot);
        System.out.println(endpointRoot);
    }

    @Step
    public void hitEndpoint() {
        given()
                .log().all()
                .when()
                .get(endpointRoot);
    }

    @Step
    public void setEndpoint() {
        //SerenityRest.given().baseUri(endpointRoot);
        RestAssured.baseURI = endpointRoot;
    }

    @Step
    public void getResource(String resourceName) throws IOException {
        String resourcePath=PropertyReader.getPropValues(resourceName);
        response = given().when().get(resourcePath);
        System.out.println("response: " + response.prettyPrint());
    }

    @Step
    public void assertStatusCode(int code) {
        json = response.then().statusCode(code).log().all();
    }
}

