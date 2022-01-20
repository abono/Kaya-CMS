package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateId;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.util.SearchResults;
import java.util.List;

public interface WebPageTemplateService {

  boolean isWebPageTemplateSetUp(WebSiteId webSiteId) throws KayaServiceException;

  SearchResults<WebPageTemplate> searchWebPageTemplates(WebPageTemplateSearchCriteria criteria)
      throws KayaServiceException;

  List<WebPageTemplate> getUnpublishedWebPageTemplate(WebSiteId webSiteId)
      throws KayaServiceException;

  WebPageTemplate getWebPageTemplate(WebPageTemplateId webPageTemplateId)
      throws KayaServiceException;

  WebPageTemplate createWebPageTemplate(WebPageTemplate webPageTemplate)
      throws KayaServiceException;

  WebPageTemplate updateWebPageTemplate(WebPageTemplate entity) throws KayaServiceException;

  void publishWebPageTemplate(List<WebPageTemplateId> webPageTemplateIds)
      throws KayaServiceException;

  void deleteWebPageTemplate(WebPageTemplateId webPageTemplateId) throws KayaServiceException;
}
