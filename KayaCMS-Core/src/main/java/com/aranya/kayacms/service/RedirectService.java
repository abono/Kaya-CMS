package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.redirect.Redirect;
import com.aranya.kayacms.beans.redirect.RedirectId;
import com.aranya.kayacms.beans.redirect.RedirectSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.util.SearchResults;

public interface RedirectService {

  SearchResults<Redirect> searchRedirects(RedirectSearchCriteria criteria)
      throws KayaServiceException;

  Redirect getRedirect(WebSiteId webSiteId, String fromPath) throws KayaServiceException;

  Redirect getRedirect(RedirectId redirectId) throws KayaServiceException;

  Redirect createRedirect(Redirect redirect) throws KayaServiceException;

  Redirect updateRedirect(Redirect redirect) throws KayaServiceException;

  void deleteRedirect(RedirectId redirectId) throws KayaServiceException;
}
