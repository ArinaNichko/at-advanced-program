package org.example.clients;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.utils.PropertiesHelper;

import java.io.IOException;

import static org.example.cucumber.context.CucumberContext.session;
import static org.example.session.SessionKey.JSON_STRING;
import static org.example.session.SessionKey.STATUS_CODE;

public class HttpClient {
    public static String baseURL = PropertiesHelper.getInstance().getProperty("baseApiUrl");
    public static String token = PropertiesHelper.getInstance().getProperty("token");

    public void sendRequest(String endpoint, String method, HttpEntity entity) {
        HttpRequestBase request = getHttpRequestBase(endpoint, method);

        if (entity != null && (request instanceof HttpPost || request instanceof HttpPut)) {
            ((HttpEntityEnclosingRequestBase) request).setEntity(entity);
        }

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpclient.execute(request);
            session.put(JSON_STRING, EntityUtils.toString(response.getEntity()));
            session.put(STATUS_CODE, response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static HttpRequestBase getHttpRequestBase(String method, String endpoint) {
        HttpRequestBase request = switch (method.toUpperCase()) {
            case "GET" -> new HttpGet(baseURL + endpoint);
            case "POST" -> new HttpPost(baseURL + endpoint);
            case "PUT" -> new HttpPut(baseURL + endpoint);
            case "DELETE" -> new HttpDelete(baseURL + endpoint);
            default -> throw new IllegalArgumentException("Invalid HTTP method: " + method);
        };

        request.setHeader("Authorization", "Bearer " + token);
        request.addHeader("Content-Type", "application/json");
        return request;
    }
}
