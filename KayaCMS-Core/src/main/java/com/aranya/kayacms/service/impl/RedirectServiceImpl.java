package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.redirect.Redirect;
import com.aranya.kayacms.beans.redirect.RedirectId;
import com.aranya.kayacms.beans.redirect.RedirectSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.dao.RedirectDAO;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.RedirectService;
import com.aranya.kayacms.util.SearchResults;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class RedirectServiceImpl implements RedirectService {

  private final RedirectDAO redirectDAO;

  @Override
  public SearchResults<Redirect> searchRedirects(RedirectSearchCriteria criteria)
      throws KayaServiceException {
    try {
      return redirectDAO.searchRedirects(criteria);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public Redirect getRedirect(WebSiteId webSiteId, String fromPath) throws KayaServiceException {
    try {
      return redirectDAO.getRedirect(webSiteId, fromPath);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public Redirect getRedirect(RedirectId redirectId) throws KayaServiceException {
    try {
      return redirectDAO.getRedirect(redirectId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public Redirect createRedirect(Redirect redirect) throws KayaServiceException {
    try {
      RedirectId redirectId = redirectDAO.insertRedirect(redirect);
      return redirectDAO.getRedirect(redirectId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public Redirect updateRedirect(Redirect redirect) throws KayaServiceException {
    try {
      if (redirect.getRedirectId() == null) {
        throw new KayaServiceException("Redirect ID not set.");
      } else {
        redirectDAO.updateRedirect(redirect);
        return redirectDAO.getRedirect(redirect.getRedirectId());
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public void deleteRedirect(RedirectId redirectId) throws KayaServiceException {
    try {
      if (redirectDAO.getRedirect(redirectId) == null) {
        throw new KayaServiceException("Redirect with given ID does not exist!");
      }
      redirectDAO.deleteRedirect(redirectId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }
}
