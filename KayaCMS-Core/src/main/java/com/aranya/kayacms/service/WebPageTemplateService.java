package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.util.SearchResults;

public interface WebPageTemplateService {

  boolean isWebPageTemplateSetUp(WebSite webSite) throws KayaServiceException;

  SearchResults<WebPageTemplate> searchWebPageTemplates(WebPageTemplateSearchCriteria criteria)
      throws KayaServiceException;

  WebPageTemplate getWebPageTemplate(Long webPageTemplateId) throws KayaServiceException;

  WebPageTemplate createWebPageTemplate(WebPageTemplate webPageTemplate)
      throws KayaServiceException;

  WebPageTemplate updateWebPageTemplate(WebPageTemplate entity) throws KayaServiceException;

  void deleteWebPageTemplate(Long webPageTemplateId) throws KayaServiceException;
}
