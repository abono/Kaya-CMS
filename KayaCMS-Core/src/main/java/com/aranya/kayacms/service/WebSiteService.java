package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;

public interface WebSiteService {

  WebSite getWebSiteByDomainName(String domainName) throws KayaServiceException;

  WebSite createWebSite(WebSite webSite) throws KayaServiceException;

  WebSite updateWebSite(WebSite entity) throws KayaServiceException;
}
