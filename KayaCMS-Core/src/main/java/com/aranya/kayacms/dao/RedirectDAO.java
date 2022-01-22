package com.aranya.kayacms.dao;

import com.aranya.kayacms.beans.redirect.Redirect;
import com.aranya.kayacms.beans.redirect.RedirectId;
import com.aranya.kayacms.beans.redirect.RedirectSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.util.SearchResults;
import java.sql.SQLException;

public interface RedirectDAO {

  SearchResults<Redirect> searchRedirects(RedirectSearchCriteria criteria) throws SQLException;

  Redirect getRedirect(WebSiteId webSiteId, String fromPath) throws SQLException;

  Redirect getRedirect(RedirectId redirectId) throws SQLException;

  RedirectId insertRedirect(Redirect redirect) throws SQLException;

  void updateRedirect(Redirect redirect) throws SQLException;

  void deleteRedirect(RedirectId redirectId) throws SQLException;
}
