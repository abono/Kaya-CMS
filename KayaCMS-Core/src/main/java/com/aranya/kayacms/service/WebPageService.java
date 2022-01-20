package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpage.WebPageId;
import com.aranya.kayacms.beans.webpage.WebPageSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.util.SearchResults;
import java.util.List;

public interface WebPageService {

  boolean isWebPageSetUp(WebSiteId webSiteId) throws KayaServiceException;

  SearchResults<WebPage> searchWebPages(WebPageSearchCriteria criteria) throws KayaServiceException;

  List<WebPage> getUnpublishedWebPage(WebSiteId webSiteId) throws KayaServiceException;

  WebPage getWebPage(WebSiteId webSiteId, String path) throws KayaServiceException;

  WebPage getWebPage(WebPageId webPageId) throws KayaServiceException;

  WebPage createWebPage(WebPage webPage) throws KayaServiceException;

  WebPage updateWebPage(WebPage webPage) throws KayaServiceException;

  void publishWebPage(List<WebPageId> webPageIds) throws KayaServiceException;

  void deleteWebPage(WebPageId webPageId) throws KayaServiceException;
}
