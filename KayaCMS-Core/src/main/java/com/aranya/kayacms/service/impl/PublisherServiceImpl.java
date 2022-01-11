package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PublisherServiceImpl implements PublisherService {

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
