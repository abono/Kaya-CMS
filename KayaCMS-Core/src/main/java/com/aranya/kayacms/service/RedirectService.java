package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.redirect.Redirect;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;

public interface RedirectService {

  Redirect getRedirect(WebSite webSite, String fromPath) throws KayaServiceException;

  Redirect getRedirect(Long redirectId) throws KayaServiceException;

  Redirect createRedirect(Redirect redirect) throws KayaServiceException;

  Redirect updateRedirect(Redirect redirect) throws KayaServiceException;

  void deleteRedirect(Long redirectId) throws KayaServiceException;
}
