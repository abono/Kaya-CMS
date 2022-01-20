package com.aranya.kayacms.dao;

import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpage.WebPageId;
import com.aranya.kayacms.beans.webpage.WebPageSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.util.SearchResults;
import java.sql.SQLException;
import java.util.List;

public interface WebPageDAO {

  SearchResults<WebPage> searchWebPage(WebPageSearchCriteria criteria) throws SQLException;

  List<WebPage> getUnpublishedWebPage(WebSiteId webSiteId) throws SQLException;

  WebPage getWebPage(WebSiteId webSiteId, String path) throws SQLException;

  WebPage getWebPage(WebPageId webPageId) throws SQLException;

  WebPageId insertWebPage(WebPage webPage) throws SQLException;

  void updateWebPage(WebPage webPage) throws SQLException;

  void publishWebPage(WebPageId webPageId) throws SQLException;

  void deleteWebPage(WebPageId webPageId) throws SQLException;
}
