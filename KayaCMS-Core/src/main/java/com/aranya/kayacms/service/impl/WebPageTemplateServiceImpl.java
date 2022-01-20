package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateId;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.dao.WebPageTemplateDAO;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.PublisherService;
import com.aranya.kayacms.service.WebPageTemplateService;
import com.aranya.kayacms.util.SearchResults;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class WebPageTemplateServiceImpl implements WebPageTemplateService {

  private final WebPageTemplateDAO webPageTemplateDAO;

  @Override
  public boolean isWebPageTemplateSetUp(WebSiteId webSiteId) throws KayaServiceException {
    try {
      WebPageTemplateSearchCriteria criteria =
          new WebPageTemplateSearchCriteria(1, 1, true, webSiteId);
      long count = webPageTemplateDAO.searchWebPageTemplate(criteria).getTotalItems();
      return count > 0;
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public SearchResults<WebPageTemplate> searchWebPageTemplates(
      WebPageTemplateSearchCriteria criteria) throws KayaServiceException {
    try {
      return webPageTemplateDAO.searchWebPageTemplate(criteria);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public List<WebPageTemplate> getUnpublishedWebPageTemplate(WebSiteId webSiteId)
      throws KayaServiceException {
    try {
      return webPageTemplateDAO.getUnpublishedWebPageTemplate(webSiteId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public WebPageTemplate getWebPageTemplate(WebPageTemplateId webPageTemplateId)
      throws KayaServiceException {
    try {
      return webPageTemplateDAO.getWebPageTemplate(webPageTemplateId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  /** Creates an UNPUBLISHED web page template. */
  @Override
  public WebPageTemplate createWebPageTemplate(WebPageTemplate webPageTemplate)
      throws KayaServiceException {
    try {
      WebPageTemplateId webPageTemplateId =
          webPageTemplateDAO.insertWebPageTemplate(webPageTemplate);
      return webPageTemplateDAO.getWebPageTemplate(webPageTemplateId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  /**
   * This method ONLY saves the edits and records a new modify date. It will ignore all other
   * changes. If you want to change the actual live values (name, content, etc.), you must call this
   * method to save the EDITS and then publish in order to copy those edits over to the live values.
   *
   * @see PublisherService
   */
  @Override
  public WebPageTemplate updateWebPageTemplate(WebPageTemplate webPageTemplate)
      throws KayaServiceException {
    try {
      if (webPageTemplate.getWebPageTemplateId() == null) {
        throw new KayaServiceException("Web page template ID not set.");
      } else {
        webPageTemplateDAO.updateWebPageTemplate(webPageTemplate);
        return webPageTemplateDAO.getWebPageTemplate(webPageTemplate.getWebPageTemplateId());
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  @Transactional
  public void publishWebPageTemplate(List<WebPageTemplateId> webPageTemplateIds)
      throws KayaServiceException {
    try {
      for (WebPageTemplateId webPageTemplateId : webPageTemplateIds) {
        webPageTemplateDAO.publishWebPageTemplate(webPageTemplateId);
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public void deleteWebPageTemplate(WebPageTemplateId webPageTemplateId)
      throws KayaServiceException {
    try {
      if (webPageTemplateDAO.getWebPageTemplate(webPageTemplateId) == null) {
        throw new KayaServiceException("Web page template with given ID does not exist!");
      }
      webPageTemplateDAO.deleteWebPageTemplate(webPageTemplateId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }
}
