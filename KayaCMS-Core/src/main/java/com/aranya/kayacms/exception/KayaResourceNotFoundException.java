package com.aranya.kayacms.exception;

import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public class KayaResourceNotFoundException extends Exception {

  private static final long serialVersionUID = 7093321766876864015L;

  @Getter private String exceptionCode;

  private Map<String, Object> exceptionArgs;

  public KayaResourceNotFoundException(String message) {
    super(message);
  }

  public KayaResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public KayaResourceNotFoundException(Throwable cause) {
    super(cause);
  }

  public KayaResourceNotFoundException(
      String message, String exceptionCode, Map<String, Object> exceptionArgs) {
    super(message);
    this.exceptionCode = exceptionCode;
    this.exceptionArgs = Collections.unmodifiableMap(exceptionArgs);
  }

  public KayaResourceNotFoundException(
      String message, Throwable cause, String exceptionCode, Map<String, Object> exceptionArgs) {
    super(message, cause);
    this.exceptionCode = exceptionCode;
    this.exceptionArgs = Collections.unmodifiableMap(exceptionArgs);
  }

  public KayaResourceNotFoundException(
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
