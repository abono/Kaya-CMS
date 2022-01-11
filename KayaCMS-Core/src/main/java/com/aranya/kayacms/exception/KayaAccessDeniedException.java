package com.aranya.kayacms.exception;

import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public class KayaAccessDeniedException extends Exception {

  private static final long serialVersionUID = -980092699765361787L;

  @Getter private String exceptionCode;

  private Map<String, Object> exceptionArgs;

  public KayaAccessDeniedException(String message) {
    super(message);
  }

  public KayaAccessDeniedException(String message, Throwable cause) {
    super(message, cause);
  }

  public KayaAccessDeniedException(Throwable cause) {
    super(cause);
  }

  public KayaAccessDeniedException(
      String message, String exceptionCode, Map<String, Object> exceptionArgs) {
    super(message);
    this.exceptionCode = exceptionCode;
    this.exceptionArgs = Collections.unmodifiableMap(exceptionArgs);
  }

  public KayaAccessDeniedException(
      String message, Throwable cause, String exceptionCode, Map<String, Object> exceptionArgs) {
    super(message, cause);
    this.exceptionCode = exceptionCode;
    this.exceptionArgs = Collections.unmodifiableMap(exceptionArgs);
  }

  public KayaAccessDeniedException(
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
