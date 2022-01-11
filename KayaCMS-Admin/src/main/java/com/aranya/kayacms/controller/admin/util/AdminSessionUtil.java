package com.aranya.kayacms.controller.admin.util;

import com.aranya.kayacms.controller.admin.login.LogInResponse;
import javax.servlet.http.HttpServletRequest;

public class AdminSessionUtil {

  private static final String ADMIN_USER_ATTRIBUTE_KEY = "adminUser";

  public static LogInResponse getAdminUser(HttpServletRequest request) {
    return (LogInResponse) request.getSession(true).getAttribute(ADMIN_USER_ATTRIBUTE_KEY);
  }

  public static void setAdminUser(HttpServletRequest request, LogInResponse adminUser) {
    if (adminUser == null) {
      request.getSession(true).removeAttribute(ADMIN_USER_ATTRIBUTE_KEY);
    } else {
      request.getSession(true).setAttribute(ADMIN_USER_ATTRIBUTE_KEY, adminUser);
    }
  }
}
