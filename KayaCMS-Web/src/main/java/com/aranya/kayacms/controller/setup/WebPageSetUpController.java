package com.aranya.kayacms.controller.setup;

import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.WebPageService;
import com.aranya.kayacms.service.WebPageTemplateService;
import com.aranya.kayacms.service.WebSiteService;
import com.aranya.kayacms.util.RequestUtil;
import com.aranya.kayacms.util.SearchResults;
import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/setup/webPage")
public class WebPageSetUpController {

  @Autowired private WebSiteService webSiteService;

  @Autowired private WebPageTemplateService webPageTemplateService;

  @Autowired private WebPageService webPageService;

  @RequestMapping(value = "/exists", method = RequestMethod.GET)
  public @ResponseBody boolean webPageExists(HttpServletRequest request)
      throws KayaServiceException {
    WebSite webSite = RequestUtil.getWebSite(request);
    return webPageService.isWebPageSetUp(webSite);
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public @ResponseBody boolean createWebPage(
      @RequestBody WebPage webPage, HttpServletRequest request) throws KayaServiceException {
    if (webPageExists(request)) {
      throw new KayaServiceException("Web page exists!");
    }

    WebSite webSite = RequestUtil.getWebSite(request);

    WebPageTemplateSearchCriteria criteria =
        new WebPageTemplateSearchCriteria(1, 1, false, webSite);
    SearchResults<WebPageTemplate> results =
        webPageTemplateService.searchWebPageTemplates(criteria);
    WebPageTemplate template = results.getItems().get(0);

    webPage =
        WebPage.builderClone(webPage)
            .webPageId(null)
            .type("CONTENT")
            .path("/index.html")
            .parameters("{}")
            .typeEdits("")
            .pathEdits("")
            .titleEdits("")
            .descriptionEdits("")
            .contentEdits("")
            .parametersEdits("")
            .publishDate(Instant.now())
            .createDate(Instant.now())
            .modifyDate(Instant.now())
            .webSite(webSite)
            .webPageTemplate(template)
            .webPageTemplateEdits(template)
            .build();

    webPageService.createWebPage(webPage);

    webSite = WebSite.builderClone(webSite).setUpComplete(true).build();

    webSite = webSiteService.updateWebSite(webSite);
    RequestUtil.setWebSite(request, webSite);

    return true;
  }
}
