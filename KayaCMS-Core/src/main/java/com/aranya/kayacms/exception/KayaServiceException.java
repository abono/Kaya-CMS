package com.aranya.kayacms.exception;

import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public class KayaServiceException extends Exception {

  private static final long serialVersionUID = 7938365987685954934L;

  @Getter private String exceptionCode;

  private Map<String, Object> exceptionArgs;

  public KayaServiceException(String message) {
    super(message);
  }

  public KayaServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public KayaServiceException(Throwable cause) {
    super(cause);
  }

  public KayaServiceException(
      String message, String exceptionCode, Map<String, Object> exceptionArgs) {
    super(message);
    this.exceptionCode = exceptionCode;
    this.exceptionArgs = Collections.unmodifiableMap(exceptionArgs);
  }

  public KayaServiceException(
      String message, Throwable cause, String exceptionCode, Map<String, Object> exceptionArgs) {
    super(message, cause);
    this.exceptionCode = exceptionCode;
    this.exceptionArgs = Collections.unmodifiableMap(exceptionArgs);
  }

  public KayaServiceException(
      Throwable cause, String exceptionCode, Map<String, Object> exceptionArgs) {
    super(cause);
    this.exceptionCode = exceptionCode;
    this.exceptionArgs = Collections.unmodifiableMap(exceptionArgs);
  }

  public Map<String, Object> getExceptionArgs() {
    return (exceptionArgs == null)
        ? Collections.emptyMap()
        : Collections.unmodifiableMap(exceptionArgs);
  }
}
