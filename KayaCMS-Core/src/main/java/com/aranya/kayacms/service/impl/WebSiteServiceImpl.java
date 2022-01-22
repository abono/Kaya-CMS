package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.dao.WebSiteDAO;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.WebSiteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class WebSiteServiceImpl implements WebSiteService {

  private final WebSiteDAO webSiteDAO;

  private String cleanDomainName(String domainName) {
    domainName = domainName.toLowerCase();
    if (domainName.startsWith("www.")) {
      domainName = domainName.substring(4);
    }
    return domainName;
  }

  @Override
  public WebSite getWebSiteByDomainName(String domainName) throws KayaServiceException {
    try {
      domainName = cleanDomainName(domainName);
      return webSiteDAO.getWebSite(domainName);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public WebSite createWebSite(WebSite webSite) throws KayaServiceException {
    try {
      WebSiteId webSiteId = webSiteDAO.insertWebSite(webSite);
      return webSiteDAO.getWebSite(webSiteId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public WebSite updateWebSite(WebSite webSite) throws KayaServiceException {
    try {
      if (webSite.getWebSiteId() == null) {
        throw new KayaServiceException("Web site ID not set.");
      } else {
        webSiteDAO.updateWebSite(webSite);
        return webSiteDAO.getWebSite(webSite.getWebSiteId());
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }
}
