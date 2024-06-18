package org.example.cucumber.definitionSteps;

import com.google.gson.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.StringEntity;
import org.example.exception.TestExecutionException;
import org.example.models.Dashboard;
import org.hamcrest.Matchers;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.example.cucumber.context.CucumberContext.httpClient;
import static org.example.cucumber.context.CucumberContext.session;
import static org.example.session.SessionKey.*;
import static org.example.utils.JsonHelper.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@Slf4j
public class ApacheApiDashboardsSteps {

    @Then("the status code is {int} using Apache Http")
    public void verifyStatusCode(int expectedStatusCode) {
        int actualStatusCode = session.get(STATUS_CODE, Integer.class);
        if (actualStatusCode == 201) {
            String jsonString = session.get(JSON_STRING, String.class);
            JsonObject jsonObject = readStringAsJsonElement(jsonString);
            session.put(DASHBOARD_ID, getId(jsonObject));
        }

        assertThat(actualStatusCode, Matchers.equalTo(expectedStatusCode));
    }

    @And("retrieved data size is equal to {int} using Apache Http")
    public void verifyDataSize(int expectedSize) {
        String jsonString = session.get(JSON_STRING, String.class);
        Dashboard dashboard = readJsonStringAsObject(jsonString, Dashboard.class);
        int actualSize = dashboard.getWidgets().size();

        assertThat(actualSize, Matchers.equalTo(expectedSize));
    }

    @And("message contains: {string} using Apache Http")
    public void verifyMessage(String expectedMessage) {
        String jsonString = session.get(JSON_STRING, String.class);
        JsonObject jsonObject = readStringAsJsonElement(jsonString);

        assertThat(getMessage(jsonObject), containsString(expectedMessage));
    }

    @And("the message contains the ID of the deleted dashboard using Apache Http")
    public void verifyDeleteMessage() {
        String id = session.get(DASHBOARD_ID, String.class);
        String jsonString = session.get(JSON_STRING, String.class);
        JsonObject jsonObject = readStringAsJsonElement(jsonString);
        String expectedMessage = "Dashboard with ID = '" + id + "' successfully deleted.";

        assertThat(getMessage(jsonObject), Matchers.equalTo(expectedMessage));

    }

    @And("error message dashboard with ID from precondition is not found using Apache Http")
    public void verifyNotFoundMessage() {
        String id = session.get(DASHBOARD_ID, String.class);
        String jsonString = session.get(JSON_STRING, String.class);
        JsonObject jsonObject = readStringAsJsonElement(jsonString);
        String expectedMessage = "Dashboard with ID '" + id +
                "' not found on project 'arina_nichko_personal'. Did you use correct Dashboard ID?";

        assertThat(getMessage(jsonObject), Matchers.equalTo(expectedMessage));
    }

    @When("the Apache {string} request is sent with body from file:")
    public void sendRequestFromFile(String method, Map<String, String> params) {
        String dashboard = readJsonFileAsString(params.get("path"));
        try {
            httpClient.sendRequest(method, "", new StringEntity(dashboard));
        } catch (UnsupportedEncodingException e) {
            throw new TestExecutionException("Can not encode string as StringEntity");
        }
    }

    @When("the Apache {string} request is sent to endpoint {string} with body from file:")
    public void sendRequestToEndpointFromFile(String method, String endpoint, Map<String, String> params) {
        String dashboard = readJsonFileAsString(params.get("path"));
        try {
            httpClient.sendRequest(method, endpoint, new StringEntity(dashboard));
        } catch (UnsupportedEncodingException e) {
            throw new TestExecutionException("Can not encode string as StringEntity");
        }
        session.put(DASHBOARD_ID, endpoint);
    }

    @When("the Apache {string} request is sent with endpoint from precondition")
    public void sendRequestToPreconditionEndpoint(String method) {
        String id = session.get(DASHBOARD_ID, String.class);
        httpClient.sendRequest(method, id, null);
    }

    @When("the Apache {string} request is sent to the {string} endpoint without params")
    public void sendRequestToEndpoint(String method, String endpoint) {
        httpClient.sendRequest(method, endpoint, null);

    }
}
