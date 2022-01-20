package com.aranya.kayacms.dao;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateId;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.util.SearchResults;
import java.sql.SQLException;
import java.util.List;

public interface WebPageTemplateDAO {

  SearchResults<WebPageTemplate> searchWebPageTemplate(WebPageTemplateSearchCriteria criteria)
      throws SQLException;

  List<WebPageTemplate> getUnpublishedWebPageTemplate(WebSiteId webSiteId) throws SQLException;

  WebPageTemplate getWebPageTemplate(WebPageTemplateId webPageTemplateId) throws SQLException;

  WebPageTemplateId insertWebPageTemplate(WebPageTemplate webPageTemplate) throws SQLException;

  void updateWebPageTemplate(WebPageTemplate webPageTemplate) throws SQLException;

  void publishWebPageTemplate(WebPageTemplateId webPageTemplateId) throws SQLException;

  void deleteWebPageTemplate(WebPageTemplateId webPageTemplateId) throws SQLException;
}
