package com.aranya.kayacms.filter;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.redirect.Redirect;
import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.context.DateContext;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.MediaService;
import com.aranya.kayacms.service.RedirectService;
import com.aranya.kayacms.service.WebPageService;
import com.aranya.kayacms.service.WebPageTemplateService;
import com.aranya.kayacms.util.RequestUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@Slf4j
public class WebPageFilter implements Filter {

  @Autowired private WebPageTemplateService webPageTemplateService;

  @Autowired private MediaService mediaService;

  @Autowired private RedirectService redirectService;

  @Autowired private WebPageService webPageService;

  private void serveStaticAssets(HttpServletResponse response, String path) throws IOException {
    if (path.indexOf("..") >= 0) {
      throw new IllegalArgumentException("Invalid path.");
    } else if (path.endsWith(".class") || path.endsWith(".java")) {
      throw new IllegalArgumentException("Invalid file.");
    } else {
      // Determine if this is a folder or a file.  If a folder, add index.html on the end.
      path = path.replaceAll("\\\\", "/");
      int cut = path.lastIndexOf('/');
      String fileName = path.substring(cut + 1);
      if (fileName.indexOf(".") <= 0) {
        if (path.endsWith("/")) {
          path = path + "index.html";
        } else {
          path = path + "/index.html";
        }
      }

      // Serve up the content
      try (InputStream in = getClass().getResourceAsStream(path)) {
        if (in == null) {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
          try (OutputStream out = response.getOutputStream()) {
            IOUtils.copy(in, out);
          }
        }
      }
    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest hRequest = (HttpServletRequest) request;
    HttpServletResponse hResponse = (HttpServletResponse) response;

    String path = hRequest.getServletPath();
    if (path.startsWith("/api/")) {
      // Pass through to Spring controllers
      chain.doFilter(request, response);
    } else {
      // If the web site is not yet set up, we need to force user to the set up first.
      WebSite webSite = RequestUtil.getWebSite(request);

      if (!webSite.getSetUpComplete()) {
        if (path.startsWith("/setup/")) {
          serveStaticAssets(hResponse, "/static" + path);
        } else {
          hResponse.sendRedirect("/setup/index.html");
        }
      } else {
        if (path.equals("/admin") || path.startsWith("/admin/")) {
          // Pass through to admin interface
          serveStaticAssets(hResponse, "/static" + path);
        } else {
          // Process web resources (web pages, media)
          serveUpSiteContent(hResponse, webSite, path);
        }
      }
    }
  }

  private void serveUpSiteContent(HttpServletResponse response, WebSite webSite, String path)
      throws IOException, ServletException {
    log.info("Processing request path {}", path);
    path = path.trim();
    if (path.endsWith("/")) {
      path = path + "index.html";
    }
    int cut = path.lastIndexOf('/');
    String fileName = path.substring(cut + 1);
    cut = fileName.lastIndexOf('.');
    if (cut < 0) {
      path = path + "/index.html";
      fileName = "index.html";
      cut = fileName.lastIndexOf('.');
    }
    String extension = cut < 0 ? "" : fileName.substring(cut + 1).toLowerCase(Locale.getDefault());
    log.info("Serving up resource at {} ({} : {})", path, fileName, extension);

    try {
      if (StringUtils.isBlank(extension) || extension.equals("htm") || extension.equals("html")) {
        WebSiteId webSiteId = new WebSiteId(webSite.getWebSiteId());
        WebPage webPage = webPageService.getWebPage(webSiteId, path);
        if (webPage == null) {
          Redirect redirect = redirectService.getRedirect(webSite, path);
          if (redirect == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
          } else {
            response.sendRedirect(redirect.getToPath());
          }
        } else {
          serveWebPageContent(webSite, webPage, response);
        }
      } else {
        Media media = mediaService.getMedia(webSite, path);
        if (media == null) {
          Redirect redirect = redirectService.getRedirect(webSite, path);
          if (redirect == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
          } else {
            response.sendRedirect(redirect.getToPath());
          }
        } else {
          try (OutputStream out = response.getOutputStream()) {
            out.write(media.getContent());
            out.flush();
          }
        }
      }
    } catch (KayaServiceException e) {
      throw new ServletException(e);
    }
  }

  private void serveWebPageContent(WebSite webSite, WebPage webPage, HttpServletResponse response)
      throws IOException, KayaServiceException {
    WebPageTemplate webPageTemplate =
        webPageTemplateService.getWebPageTemplate(webPage.getWebPageTemplateId());

    VelocityEngine velocityEngine = new VelocityEngine();
    velocityEngine.init();

    VelocityContext context = new VelocityContext();
    context.put("webSite", webSite);
    context.put("webPage", webPage);
    context.put("date", new DateContext());

    try (OutputStream out = response.getOutputStream();
        Writer writer = new OutputStreamWriter(out, Charset.defaultCharset()); ) {
      velocityEngine.evaluate(context, writer, webPage.getPath(), webPageTemplate.getContent());
      writer.flush();
      out.flush();
    }
  }
}
