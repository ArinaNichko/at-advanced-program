package org.example.clients;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.utils.PropertiesHelper;


import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;

@Slf4j
public class RestClient {

  public static String baseURL = PropertiesHelper.getInstance().getProperty("baseApiUrl");
  public static String token = PropertiesHelper.getInstance().getProperty("token");

  public Response sendRequestWithParams(
          Method requestType, String url, Map<String, String> queryParams) {
    return sendRequestForHttpMethods(requestType, url, null, queryParams);
  }

  public Response sendRequestWithBody(Method requestType, String url, Object requestBody) {
    return sendRequestForHttpMethods(requestType, url, requestBody, emptyMap());
  }

  private Response sendRequestForHttpMethods(
          Method httpMethod, String url, Object requestBody, Map<String, String> params) {
    RequestSpecification requestSpecification =
            given()
                    .header("Authorization", "Bearer " + token)
                    .contentType(ContentType.JSON)
                    .body(ofNullable(requestBody).orElse(StringUtils.EMPTY))
                    .queryParams(ofNullable(params).orElse(emptyMap()));
    Response response = requestSpecification.request(httpMethod, baseURL + url);
    log.info("{} request was sent for URI: {}", httpMethod, baseURL + url);
    log.info("Response is: {}", response.asString());
    return response;
  }
}
