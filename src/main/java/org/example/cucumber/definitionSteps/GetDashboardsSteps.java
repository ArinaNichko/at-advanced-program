package org.example.cucumber.definitionSteps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.example.clients.RestClient;
import org.example.cucumber.context.CucumberContext;
import org.example.models.Dashboard;
import org.example.session.SessionKey;
import org.hamcrest.Matchers;

import static org.example.session.SessionKey.*;
import static org.example.utils.FileHelper.readJsonFileAsString;

import java.util.Collections;
import java.util.Map;


import static org.example.cucumber.context.CucumberContext.*;
import static org.example.utils.FileHelper.readJsonStringAsObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
public class GetDashboardsSteps {
    private final CucumberContext context;
    public static RestClient restClient = new RestClient();

    @After(value = "@apiCleanup")
    public void cleanUp() {
        context.cleanupApi();
    }
    @After(value = "@apiInitialState")
    public void putInitialState() {
        context.putInitialState();
    }

    @Before(value = "@createDashboard")
    public void createDashboard() {
        context.createDashboard();
    }

    public GetDashboardsSteps(CucumberContext context) {
        this.context = context;
    }

    @When("the GET request is sent to the {string} endpoint without params")
    public void sendGetRequestWithoutParams(String endpoint) {
        Response response = restClient.sendRequestWithParams(Method.GET, endpoint, Collections.emptyMap());
        session.put(RESPONSE, response);
    }

    @Then("the status code is {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        Response response = session.get(RESPONSE, Response.class);
        assertThat(response.statusCode(), Matchers.equalTo(expectedStatusCode));
        if(response.statusCode() == 201) {
            session.put(DASHBOARD_ID, response.jsonPath().getString("id"));
        }
    }

    @And("retrieved data size is equal to {int}")
    public void verifyDataSize(int expectedSize) {
        Response response = session.get(RESPONSE, Response.class);
        Dashboard dashboard = readJsonStringAsObject(response.asPrettyString(), Dashboard.class);
        int actualSize = dashboard.getWidgets().size();
        assertThat(actualSize, Matchers.equalTo(expectedSize));
    }

    @When("the POST request is sent with body from file:")
    public void thePOSTRequestIsSentWithBodyFromFile(Map<String, String> params) {
        String dashboard = readJsonFileAsString(params.get("path"));
        Response response = restClient.sendRequestWithBody(Method.POST, "" , dashboard);
        session.put(RESPONSE, response);
    }

    @And("message contains: {string}")
    public void verifyMessage(String expectedMessage) {
        Response response = session.get(RESPONSE, Response.class);
        String actualMessage = response.jsonPath().getString("message");
        assertThat(actualMessage, containsString(expectedMessage));
    }

    @When("the PUT request is sent to endpoint {string} with body from file:")
    public void thePUTRequestIsSentToEndpointWithBodyFromFile(String endpoint, Map<String, String> params ) {
        String dashboard = readJsonFileAsString(params.get("path"));
        Response response = restClient.sendRequestWithBody(Method.PUT, endpoint , dashboard);
        session.put(RESPONSE, response);
        session.put(DASHBOARD_ID, endpoint);
    }

    @When("the PATCH request is sent to endpoint {string} with body from file:")
    public void thePATCHRequestIsSentToEndpointWithBodyFromFile(String endpoint, Map<String, String> params) {
        String dashboard = readJsonFileAsString(params.get("path"));
        Response response = restClient.sendRequestWithBody(Method.PATCH, endpoint , dashboard);
        session.put(RESPONSE, response);
    }

    @When("the DELETE request is sent with endpoint {string}")
    public void theDELETERequestIsSentWithEndpointDASHBOARD_ID(String sessionKey) {
        String id = session.get(SessionKey.valueOf(sessionKey), String.class);
        Response response = restClient.sendRequestWithParams(Method.DELETE, id, Collections.emptyMap());
        session.put(RESPONSE, response);
    }

    @And("the message contains the ID of the deleted dashboard: {string}")
    public void theMessageContainsTheIDOfTheDeletedDashboardDASHBOARD_ID(String sessionKey) {
        Response response = session.get(RESPONSE, Response.class);
        String id = session.get(SessionKey.valueOf(sessionKey), String.class);
        String actualMessage = response.jsonPath().getString("message");
        assertThat(actualMessage, containsString(id));
    }
}
