package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateHistory;
import com.aranya.kayacms.exception.KayaServiceException;

public interface WebPageTemplateHistoryService {

  // TODO
  //  SearchResults<WebPageTemplateHistory> searchWebPageTemplateHistory(
  //      WebPageTemplateHistorySearchCriteria criteria) throws KayaServiceException;

  WebPageTemplateHistory createWebPageTemplateHistory(WebPageTemplateHistory entity)
      throws KayaServiceException;
}
