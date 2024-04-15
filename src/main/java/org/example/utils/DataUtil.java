package org.example.utils;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;


public class DataUtil {
  private static final String CSV = ".csv";
  private static final String ROOT_PATH = "src/test/resources/testData/";

  @DataProvider(name = "dashboard")
  public static Object[][] testDataProvider(Method method) {
    return CsvCollectionUtil.getDataMap(constructPath(method));
  }

  private static String constructPath(Method method) {
    return ROOT_PATH + method.getName() + CSV;
  }
}
