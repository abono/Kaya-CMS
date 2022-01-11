package com.aranya.kayacms.exception;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public class KayaValidationException extends Exception {

  private static final long serialVersionUID = 1L;

  @Getter private String exceptionCode;

  private Map<String, Object> exceptionArgs;

  private List<ValidationError> validationErrors;

  public KayaValidationException(String message, ValidationError... errors) {
    super(message);
    this.validationErrors = Arrays.asList(errors);
  }

  public KayaValidationException(String message, Throwable cause, ValidationError... errors) {
    super(message, cause);
    this.validationErrors = Arrays.asList(errors);
  }

  public KayaValidationException(Throwable cause, ValidationError... errors) {
    super(cause);
    this.validationErrors = Arrays.asList(errors);
  }

  public KayaValidationException(
      String message,
      String exceptionCode,
      Map<String, Object> exceptionArgs,
      ValidationError... errors) {
    super(message);
    this.exceptionCode = exceptionCode;
    this.exceptionArgs = Collections.unmodifiableMap(exceptionArgs);
    this.validationErrors = Arrays.asList(errors);
  }

  public KayaValidationException(
      String message,
      Throwable cause,
      String exceptionCode,
      Map<String, Object> exceptionArgs,
      ValidationError... errors) {
    super(message, cause);
    this.exceptionCode = exceptionCode;
    this.exceptionArgs = Collections.unmodifiableMap(exceptionArgs);
    this.validationErrors = Arrays.asList(errors);
  }

  public KayaValidationException(
      Throwable cause,
      String exceptionCode,
      Map<String, Object> exceptionArgs,
      ValidationError... errors) {
    super(cause);
    this.exceptionCode = exceptionCode;
    this.exceptionArgs = Collections.unmodifiableMap(exceptionArgs);
    this.validationErrors = Arrays.asList(errors);
  }

  public Map<String, Object> getExceptionArgs() {
    return (exceptionArgs == null)
        ? Collections.emptyMap()
        : Collections.unmodifiableMap(exceptionArgs);
  }

  public List<ValidationError> getErrors() {
    return (validationErrors == null)
        ? Collections.emptyList()
        : Collections.unmodifiableList(validationErrors);
  }
}
