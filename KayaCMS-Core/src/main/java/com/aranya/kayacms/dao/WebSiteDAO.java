package com.aranya.kayacms.dao;

import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.beans.website.WebSiteId;
import java.sql.SQLException;

public interface WebSiteDAO {

  WebSite getWebSite(String domainName) throws SQLException;

  WebSite getWebSite(WebSiteId webSiteId) throws SQLException;

  WebSiteId insertWebSite(WebSite webSite) throws SQLException;

  void updateWebSite(WebSite webSite) throws SQLException;

  void deleteWebSite(WebSiteId webSiteId) throws SQLException;
}
