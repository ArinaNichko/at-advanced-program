package org.example.exception;

public class TestExecutionException extends RuntimeException {
  public TestExecutionException(String message, Object... params) {
    super(String.format(message, params));
  }
}
