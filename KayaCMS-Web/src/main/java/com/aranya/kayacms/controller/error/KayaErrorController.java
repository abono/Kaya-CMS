package com.aranya.kayacms.controller.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class KayaErrorController implements ErrorController {

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());

      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        log.info("Not found: {}", request.getRequestURI());
        return "error-404";
      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        log.info("Internal server error: {}", request.getRequestURI());
        return "error-500";
      } else {
        log.info("Error: {}", status);
      }
    }
    return "error";
  }
}
