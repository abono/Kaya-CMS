package com.aranya.kayacms.filter;

import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.AdminUserService;
import com.aranya.kayacms.service.WebPageService;
import com.aranya.kayacms.service.WebPageTemplateService;
import com.aranya.kayacms.service.WebSiteService;
import com.aranya.kayacms.util.RequestUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Slf4j
public class WebSiteFilter implements Filter {

  @Autowired private AdminUserService adminUserService;

  @Autowired private WebPageTemplateService webPageTemplateService;

  @Autowired private WebPageService webPageService;

  @Autowired private WebSiteService webSiteService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      HttpServletRequest hRequest = (HttpServletRequest) request;
      log.info("Request coming for URL {}", hRequest.getRequestURI());
      String serverName = hRequest.getServerName();
      WebSite webSite = webSiteService.getWebSiteByDomainName(serverName);
      if (webSite == null) {
        HttpServletResponse hResponse = (HttpServletResponse) response;
        hResponse.setStatus(500);
        try (OutputStream out = hResponse.getOutputStream()) {
          IOUtils.write("Web site not found in data store.", out, Charset.defaultCharset());
        }
      } else {
        // Make sure it isn't actually set up and the web site just didn't get updated properly.
        WebSiteId webSiteId = webSite.getWebSiteId();
        if (!webSite.getSetUpComplete()
            && adminUserService.isAdminUserSetUp(webSiteId)
            && webPageTemplateService.isWebPageTemplateSetUp(webSiteId)
            && webPageService.isWebPageSetUp(webSiteId)) {
          webSite = WebSite.builderClone(webSite).setUpComplete(true).build();
          webSite = webSiteService.updateWebSite(webSite);
        }

        log.debug("Setting web site: {}", webSite);
        RequestUtil.setWebSite(hRequest, webSite);

        chain.doFilter(request, response);
      }
    } catch (KayaServiceException e) {
      throw new ServletException(e);
    }
  }
}
