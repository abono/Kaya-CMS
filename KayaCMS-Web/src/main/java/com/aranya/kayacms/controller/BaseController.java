package com.aranya.kayacms.controller;

import com.aranya.kayacms.db.DetailedSQLException;
import com.aranya.kayacms.exception.KayaAccessDeniedException;
import com.aranya.kayacms.exception.KayaResourceNotFoundException;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.exception.KayaValidationException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
public class BaseController {

  protected Throwable getRootCause(Throwable t) {
    Throwable cause = t.getCause();
    if (cause == null) {
      return t;
    } else {
      return getRootCause(cause);
    }
  }

  @SuppressWarnings("unchecked")
  protected <T extends Throwable> T getCause(Throwable t, Class<T> clazz) {
    Throwable cause = t.getCause();
    if (cause == null) {
      return null;
    } else if (clazz.isAssignableFrom(cause.getClass())) {
      return (T) cause;
    } else {
      return getCause(cause, clazz);
    }
  }

  protected Object[] buildExceptionArguments(Throwable t, Object... additionalArgs) {
    Throwable cause = getRootCause(t);

    Object[] args = null;
    if (additionalArgs == null) {
      args = new Object[2];
    } else {
      args = new Object[2 + additionalArgs.length];
      System.arraycopy(additionalArgs, 0, args, 0, additionalArgs.length);
    }
    args[args.length - 2] = cause.getClass().getSimpleName();
    args[args.length - 1] = t.getMessage();

    return args;
  }

  @ExceptionHandler(KayaValidationException.class)
  @ResponseBody
  public Map<String, Object> handleValidationException(
      KayaValidationException e, HttpServletResponse response) {
    log.debug("Validation failed", e);

    response.setStatus(400);

    Map<String, Object> message = new HashMap<String, Object>();
    message.put("exceptionCode", e.getExceptionCode());
    message.put("exceptionArgs", buildExceptionArguments(e, e.getExceptionArgs()));
    message.put("message", e.getMessage());
    message.put("errors", e.getErrors());

    return message;
  }

  @ExceptionHandler(KayaAccessDeniedException.class)
  @ResponseBody
  public Map<String, Object> handleAuthorizationFailureException(
      KayaAccessDeniedException e, HttpServletResponse response) {
    log.warn("Authorization failed", e);

    response.setStatus(401);

    Map<String, Object> message = new HashMap<String, Object>();
    message.put("type", e.getClass().getSimpleName());
    message.put("exceptionCode", e.getExceptionCode());
    message.put("exceptionArgs", buildExceptionArguments(e, e.getExceptionArgs()));
    message.put("message", e.getMessage());

    return message;
  }

  @ExceptionHandler(KayaResourceNotFoundException.class)
  @ResponseBody
  public Map<String, Object> handleResourceNotFoundException(
      KayaResourceNotFoundException e, HttpServletResponse response) {
    log.error("Resource not found", e);

    response.setStatus(404);

    Map<String, Object> message = new HashMap<String, Object>();
    message.put("exceptionCode", e.getExceptionCode());
    message.put("exceptionArgs", buildExceptionArguments(e, e.getExceptionArgs()));
    message.put("message", e.getMessage());

    return message;
  }

  @ExceptionHandler(KayaServiceException.class)
  @ResponseBody
  public Map<String, Object> handleRequestErrorException(
      KayaServiceException e, HttpServletResponse response) {
    log.error("Request failed", e);

    response.setStatus(500);

    Map<String, Object> message = new HashMap<String, Object>();
    message.put("type", e.getClass().getSimpleName());
    message.put("exceptionCode", e.getExceptionCode());
    message.put("exceptionArgs", buildExceptionArguments(e, e.getExceptionArgs()));

    // Hide the actual exception message from the UI as it could contain sensitive database
    // information.
    DetailedSQLException sqlException = getCause(e, DetailedSQLException.class);
    if (sqlException != null) {
      log.debug("Reducing exception before presenting to user front end", e);
      message.put("message", "Unexpected data source error");
    } else {
      message.put("message", e.getMessage());
    }

    return message;
  }

  @ExceptionHandler(NullPointerException.class)
  @ResponseBody
  public Map<String, Object> handleNullPointerException(
      NullPointerException e, HttpServletResponse response) {
    log.error("Request failed", e);

    response.setStatus(500);

    Map<String, Object> message = new HashMap<String, Object>();
    message.put("type", e.getClass().getSimpleName());
    message.put("exceptionCode", NullPointerException.class.getName());
    message.put("exceptionArgs", buildExceptionArguments(e));
    message.put("message", e.getClass().getSimpleName() + ": " + e.getMessage());

    return message;
  }

  @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
  @ResponseBody
  public Map<String, Object> handleArrayIndexOutOfBoundsException(
      ArrayIndexOutOfBoundsException e, HttpServletResponse response) {
    log.error("Request failed", e);

    response.setStatus(500);

    Map<String, Object> message = new HashMap<String, Object>();
    message.put("type", e.getClass().getSimpleName());
    message.put("exceptionCode", ArrayIndexOutOfBoundsException.class.getName());
    message.put("exceptionArgs", buildExceptionArguments(e));
    message.put("message", e.getClass().getSimpleName() + ": " + e.getMessage());

    return message;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  public Map<String, Object> handleIllegalArgumentException(
      IllegalArgumentException e, HttpServletResponse response) {
    log.error("Request failed", e);

    response.setStatus(500);

    Map<String, Object> message = new HashMap<String, Object>();
    message.put("type", e.getClass().getSimpleName());
    message.put("exceptionCode", IllegalArgumentException.class.getName());
    message.put("exceptionArgs", buildExceptionArguments(e));
    message.put("message", e.getClass().getSimpleName() + ": " + e.getMessage());

    return message;
  }

  @ExceptionHandler(ClassCastException.class)
  @ResponseBody
  public Map<String, Object> handleIllegalArgumentException(
      ClassCastException e, HttpServletResponse response) {
    log.error("Request failed", e);

    response.setStatus(500);

    Map<String, Object> message = new HashMap<String, Object>();
    message.put("type", e.getClass().getSimpleName());
    message.put("exceptionCode", ClassCastException.class.getName());
    message.put("exceptionArgs", buildExceptionArguments(e));
    message.put("message", e.getClass().getSimpleName() + ": " + e.getMessage());

    return message;
  }

  @ExceptionHandler(HttpMessageConversionException.class)
  @ResponseBody
  public Map<String, Object> handleHttpMessageConversionException(
      HttpMessageConversionException e, HttpServletResponse response) {
    log.error("Request failed", e);

    response.setStatus(500);

    Map<String, Object> message = new HashMap<String, Object>();
    message.put("type", e.getClass().getSimpleName());
    message.put("exceptionCode", HttpMessageConversionException.class.getName());
    message.put("exceptionArgs", buildExceptionArguments(e));
    message.put("message", e.getClass().getSimpleName() + ": " + e.getMessage());

    return message;
  }

  @ExceptionHandler(URISyntaxException.class)
  @ResponseBody
  public Map<String, Object> handleURISyntaxException(
      URISyntaxException e, HttpServletResponse response) {
    log.error("Formulating URL response failed", e);

    response.setStatus(500);

    Map<String, Object> message = new HashMap<String, Object>();
    message.put("type", e.getClass().getSimpleName());
    message.put("exceptionCode", HttpMessageConversionException.class.getName());
    message.put("exceptionArgs", buildExceptionArguments(e));
    message.put("message", e.getClass().getSimpleName() + ": " + e.getMessage());

    return message;
  }

  @ExceptionHandler(ServletException.class)
  @ResponseBody
  public Map<String, Object> handleServletException(
      ServletException e, HttpServletResponse response) {
    log.error("Request failed", e);

    response.setStatus(500);

    Map<String, Object> message = new HashMap<String, Object>();
    message.put("type", e.getClass().getSimpleName());
    message.put("exceptionCode", ServletException.class.getName());
    message.put("exceptionArgs", buildExceptionArguments(e));

    // Hide the actual exception message from the UI as it could contain sensitive database
    // information.
    DetailedSQLException sqlException = getCause(e, DetailedSQLException.class);
    if (sqlException != null) {
      log.debug("Reducing exception before presenting to user front end", e);
      message.put("message", "Unexpected data source error");
    } else {
      message.put("message", e.getMessage());
    }

    return message;
  }
}
