package org.example.cucumber;

import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.restassured.http.Method;
import org.example.models.*;
import org.example.session.SessionKey;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypeRegistryConfiguration {


  @ParameterType("[A-Z]+")
  public SessionKey sessionKey(String sessionKey) {
    return SessionKey.valueOf(sessionKey);
  }
}
