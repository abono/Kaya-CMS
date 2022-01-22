package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpage.WebPageId;
import com.aranya.kayacms.beans.webpage.WebPageSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.dao.WebPageDAO;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.WebPageService;
import com.aranya.kayacms.util.SearchResults;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class WebPageServiceImpl implements WebPageService {

  private final WebPageDAO webPageDAO;

  @Override
  public boolean isWebPageSetUp(WebSiteId webSiteId) throws KayaServiceException {
    try {
      WebPageSearchCriteria criteria = new WebPageSearchCriteria(1, 1, true, webSiteId);
      long count = webPageDAO.searchWebPage(criteria).getTotalItems();
      return count > 0;
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public SearchResults<WebPage> searchWebPages(WebPageSearchCriteria criteria)
      throws KayaServiceException {
    try {
      return webPageDAO.searchWebPage(criteria);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public List<WebPage> getUnpublishedWebPage(WebSiteId webSiteId) throws KayaServiceException {
    try {
      return webPageDAO.getUnpublishedWebPage(webSiteId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public WebPage getWebPage(WebSiteId webSiteId, String path) throws KayaServiceException {
    try {
      return webPageDAO.getWebPage(webSiteId, path);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public WebPage getWebPage(WebPageId webPageId) throws KayaServiceException {
    try {
      return webPageDAO.getWebPage(webPageId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  /** Creates an UNPUBLISHED web page. */
  @Override
  public WebPage createWebPage(WebPage webPage) throws KayaServiceException {
    try {
      WebPageId webPageId = webPageDAO.insertWebPage(webPage);
      return webPageDAO.getWebPage(webPageId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  /**
   * This method ONLY saves the edits and records a new modify date. It will ignore all other
   * changes. If you want to change the actual live values (name, content, etc.), you much call this
   * method to save the EDITS and then publish in order to copy those edits over to the live values.
   */
  @Override
  public WebPage updateWebPage(WebPage webPage) throws KayaServiceException {
    try {
      if (webPage.getWebPageId() == null) {
        throw new KayaServiceException("Web page ID not set.");
      } else {
        webPageDAO.updateWebPage(webPage);
        return webPageDAO.getWebPage(webPage.getWebPageId());
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public void publishWebPage(List<WebPageId> webPageIds) throws KayaServiceException {
    try {
      for (WebPageId webPageId : webPageIds) {
        webPageDAO.publishWebPage(webPageId);
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public void deleteWebPage(WebPageId webPageId) throws KayaServiceException {
    try {
      if (webPageDAO.getWebPage(webPageId) == null) {
        throw new KayaServiceException("Web page with given ID does not exist!");
      }
      webPageDAO.deleteWebPage(webPageId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }
}
