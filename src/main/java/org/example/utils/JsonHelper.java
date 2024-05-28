package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.exception.TestExecutionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonHelper {
  static ObjectMapper objectMapper = new ObjectMapper();

  public static <T> T readJsonStringAsObject(String jsonString, Class<T> classToCast) {
    try {
      return objectMapper.readValue(jsonString, classToCast);
    } catch (JsonProcessingException e) {
      throw new TestExecutionException(e.getMessage());
    }
  }

  public static String readJsonFileAsString(String path) {
    try {
      return Files.readString(Path.of(path));
    } catch (IOException ioException) {
      throw new TestExecutionException(ioException.getMessage());
    }
  }

  public static JsonObject readStringAsJsonElement(String jsonString) {
    JsonElement jsonElement = JsonParser.parseString(jsonString);
    return jsonElement.getAsJsonObject();
  }

  public static String getId(JsonObject jsonObject) {
    return  jsonObject.get("id").getAsString();
  }
  public static String getMessage(JsonObject jsonObject) {
    return  jsonObject.get("message").getAsString();
  }
}

