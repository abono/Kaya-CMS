package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpage.WebPageSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.util.SearchResults;

public interface WebPageService {

  boolean isWebPageSetUp(WebSite webSite) throws KayaServiceException;

  SearchResults<WebPage> searchWebPages(WebPageSearchCriteria criteria) throws KayaServiceException;

  WebPage getWebPage(WebSite webSite, String path) throws KayaServiceException;

  WebPage getWebPage(Long webPageId) throws KayaServiceException;

  WebPage createWebPage(WebPage webPage) throws KayaServiceException;

  WebPage updateWebPage(WebPage entity) throws KayaServiceException;

  void deleteWebPage(Long webPageId) throws KayaServiceException;
}
