package com.aranya.kayacms.controller.setup;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.properties.DayAndTime;
import com.aranya.kayacms.service.WebPageTemplateService;
import com.aranya.kayacms.util.RequestUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/setup/webPageTemplate")
public class WebPageTemplateSetUpController {

  @Autowired private WebPageTemplateService webPageTemplateService;

  @RequestMapping(value = "/exists", method = RequestMethod.GET)
  public @ResponseBody boolean webPageTemplateExists(HttpServletRequest request)
      throws KayaServiceException {
    WebSite webSite = RequestUtil.getWebSite(request);
    return webPageTemplateService.isWebPageTemplateSetUp(new WebSiteId(webSite.getWebSiteId()));
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public @ResponseBody boolean createWebPageTemplate(
      @RequestBody WebPageTemplate webPageTemplate, HttpServletRequest request)
      throws KayaServiceException {
    if (webPageTemplateExists(request)) {
      throw new KayaServiceException("Web page template already exists!");
    }

    WebSite webSite = RequestUtil.getWebSite(request);

    webPageTemplate =
        WebPageTemplate.builderClone(webPageTemplate)
            .webPageTemplateId(null)
            .nameEdits("")
            .contentEdits("")
            .createDate(new DayAndTime())
            .modifyDate(new DayAndTime())
            .publishDate(new DayAndTime())
            .webSiteId(new WebSiteId(webSite.getWebSiteId()))
            .build();

    webPageTemplateService.createWebPageTemplate(webPageTemplate);

    return true;
  }
}
