package com.aranya.kayacms.util;

import com.aranya.kayacms.beans.website.WebSite;
import javax.servlet.ServletRequest;

public class RequestUtil {

  private static final String WEB_SITE_ATTRIBUTE_KEY = "webSite";

  public static WebSite getWebSite(ServletRequest request) {
    return (WebSite) request.getAttribute(WEB_SITE_ATTRIBUTE_KEY);
  }

  public static void setWebSite(ServletRequest request, WebSite webSite) {
    request.setAttribute(WEB_SITE_ATTRIBUTE_KEY, webSite);
  }
}
