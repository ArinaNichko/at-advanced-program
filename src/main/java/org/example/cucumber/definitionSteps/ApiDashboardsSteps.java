package org.example.cucumber.definitionSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Dashboard;
import org.example.session.SessionKey;
import org.hamcrest.Matchers;

import java.util.Collections;
import java.util.Map;

import static org.example.cucumber.context.CucumberContext.restClient;
import static org.example.cucumber.context.CucumberContext.session;
import static org.example.session.SessionKey.DASHBOARD_ID;
import static org.example.session.SessionKey.RESPONSE;
import static org.example.utils.JsonHelper.readJsonFileAsString;
import static org.example.utils.JsonHelper.readJsonStringAsObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@Slf4j
public class ApiDashboardsSteps {

    @When("the GET request is sent to the {string} endpoint without params")
    public void sendGetRequestWithoutParams(String endpoint) {
        Response response = restClient.sendRequestWithParams(Method.GET, endpoint, Collections.emptyMap());
        session.put(RESPONSE, response);
    }

    @Then("the status code is {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        Response response = session.get(RESPONSE, Response.class);
        if (response.statusCode() == 201) {
            session.put(DASHBOARD_ID, response.jsonPath().getString("id"));
        }
        assertThat(response.statusCode(), Matchers.equalTo(expectedStatusCode));
    }

    @And("retrieved data size is equal to {int}")
    public void verifyDataSize(int expectedSize) {
        Response response = session.get(RESPONSE, Response.class);
        Dashboard dashboard = readJsonStringAsObject(response.asPrettyString(), Dashboard.class);
        int actualSize = dashboard.getWidgets().size();
        assertThat(actualSize, Matchers.equalTo(expectedSize));
    }

    @When("the POST request is sent with body from file:")
    public void sendPostRequestFromFile(Map<String, String> params) {
        String dashboard = readJsonFileAsString(params.get("path"));
        Response response = restClient.sendRequestWithBody(Method.POST, "", dashboard);
        session.put(RESPONSE, response);
    }

    @And("message contains: {string}")
    public void verifyMessage(String expectedMessage) {
        Response response = session.get(RESPONSE, Response.class);
        String actualMessage = response.jsonPath().getString("message");
        assertThat(actualMessage, containsString(expectedMessage));
    }

    @When("the PUT request is sent to endpoint {string} with body from file:")
    public void sendPutRequestFromFile(String endpoint, Map<String, String> params) {
        String dashboard = readJsonFileAsString(params.get("path"));
        Response response = restClient.sendRequestWithBody(Method.PUT, endpoint, dashboard);
        session.put(RESPONSE, response);
        session.put(DASHBOARD_ID, endpoint);
    }

    @And("the message contains the ID of the deleted dashboard: {string}")
    public void verifyDashboardId(String sessionKey) {
        Response response = session.get(RESPONSE, Response.class);
        String id = session.get(SessionKey.valueOf(sessionKey), String.class);
        String actualMessage = response.jsonPath().getString("message");
        assertThat(actualMessage, containsString(id));
    }

    @When("the DELETE request is sent with endpoint from precondition")
    public void sendDeleteRequestFromPrecondition() {
        String id = session.get(DASHBOARD_ID, String.class);
        Response response = restClient.sendRequestWithParams(Method.DELETE, id, Collections.emptyMap());
        session.put(RESPONSE, response);
    }

    @And("the message contains the ID of the deleted dashboard")
    public void verifyDeletedMessag() {
        String id = session.get(DASHBOARD_ID, String.class);
        Response response = session.get(RESPONSE, Response.class);
        String actualMessage = response.jsonPath().getString("message");
        String expectedMessage = "Dashboard with ID = '" + id + "' successfully deleted.";
        assertThat(actualMessage, Matchers.equalTo(expectedMessage));
    }

    @And("error message dashboard with ID from precondition is not found")
    public void verifyNotFoundMessage() {
        String id = session.get(DASHBOARD_ID, String.class);
        Response response = session.get(RESPONSE, Response.class);
        String actualMessage = response.jsonPath().getString("message");
        String expectedMessage = "Dashboard with ID '" + id +
                "' not found on project 'arina_nichko_personal'. Did you use correct Dashboard ID?";
        assertThat(actualMessage, Matchers.equalTo(expectedMessage));
    }
}
