package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.repository.MediaRepository;
import com.aranya.kayacms.repository.WebPageRepository;
import com.aranya.kayacms.repository.WebPageTemplateRepository;
import com.aranya.kayacms.service.PublisherService;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class PublisherServiceImpl implements PublisherService {

  private WebPageTemplateRepository webPageTemplateRepository;

  private WebPageRepository webPageRepository;

  private MediaRepository mediaRepository;

  @Override
  public List<WebPageTemplate> getUnpublishedWebPageTemplate(WebSite webSite) {
    return webPageTemplateRepository.findByWebSite(webSite);
  }

  @Override
  public List<WebPage> getUnpublishedWebPage(WebSite webSite) {
    return webPageRepository.findByWebSite(webSite);
  }

  @Override
  public List<Media> getUnpublishedMedia(WebSite webSite) {
    return mediaRepository.findByWebSite(webSite);
  }

  // When publishing:
  // * Web Page Templates
  // * Web Pages w/ Redirects
  // * Media w/ Redirects
  // * Macros

  // Operations (all in a single transaction):
  // * Only if edits != live data
  // * Save to History
  // * Save edits to live data
  // * Add Redirects

  // @RequestMapping(value = "/publish", method = RequestMethod.PUT)
  // public @ResponseBody boolean publish(
  // HttpServletRequest request,
  // @RequestBody Long id)
  // throws KayaServiceException {
  // LogInResponse logIn = (LogInResponse)
  // request.getSession(true).getAttribute(LogInController.ADMIN_USER_KEY);
  // if (logIn == null) {
  // throw new KayaServiceException("Not logged in");
  // } else {
  // try {
  // WebPageTemplate webPageTemplate = webPageTemplateService.getWebPageTemplate(id);
  //
  // WebPageTemplateHistory history = new WebPageTemplateHistory();
  // BeanUtils.copyProperties(history, webPageTemplate);
  // webPageTemplateHistoryService.createWebPageTemplateHistory(history);
  //
  // webPageTemplate.setName(webPageTemplate.getNameEdits());
  // webPageTemplate.setContent(webPageTemplate.getContentEdits());
  // webPageTemplate.setPublishDate(new Date());
  // webPageTemplate = webPageTemplateService.updateWebPageTemplate(webPageTemplate);
  // return true;
  // } catch (Exception e) {
  // throw new KayaServiceException(e);
  // }
  // }
  // }

}
