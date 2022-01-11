package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.repository.WebPageTemplateRepository;
import com.aranya.kayacms.service.PublisherService;
import com.aranya.kayacms.service.WebPageTemplateService;
import com.aranya.kayacms.util.ListSearchResults;
import com.aranya.kayacms.util.SearchResults;
import java.time.Instant;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebPageTemplateServiceImpl implements WebPageTemplateService {

  private final WebPageTemplateRepository webPageTemplateRepository;

  @Override
  public boolean isWebPageTemplateSetUp(WebSite webSite) throws KayaServiceException {
    try {
      WebPageTemplate webPageTemplate = WebPageTemplate.builder().webSite(webSite).build();
      Example<WebPageTemplate> example = Example.of(webPageTemplate);
      long count = webPageTemplateRepository.count(example);
      return count > 0;
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public SearchResults<WebPageTemplate> searchWebPageTemplates(
      WebPageTemplateSearchCriteria criteria) throws KayaServiceException {
    try {
      Sort sort = Sort.by("name");
      Pageable pageable = PageRequest.of(criteria.getPage() - 1, criteria.getItemsPerPage(), sort);

      WebPageTemplate webPageTemplate =
          WebPageTemplate.builder().webSite(criteria.getWebSite()).build();
      Example<WebPageTemplate> example = Example.of(webPageTemplate);

      Page<WebPageTemplate> results = webPageTemplateRepository.findAll(example, pageable);

      return new ListSearchResults<WebPageTemplate>(
          results.getContent(),
          criteria.getPage(),
          criteria.getItemsPerPage(),
          results.getTotalPages());
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public WebPageTemplate getWebPageTemplate(Long webPageTemplateId) throws KayaServiceException {
    try {
      Optional<WebPageTemplate> optional = webPageTemplateRepository.findById(webPageTemplateId);
      if (optional.isPresent()) {
        return optional.get();
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  /** Creates an UNPUBLISHED web page template. */
  @Override
  public WebPageTemplate createWebPageTemplate(WebPageTemplate webPageTemplate)
      throws KayaServiceException {
    try {
      WebPageTemplate newWebPageTemplate =
          WebPageTemplate.builderClone(webPageTemplate)
              .webPageTemplateId(null)
              .createDate(Instant.now())
              .modifyDate(Instant.now())
              .build();

      webPageTemplate = webPageTemplateRepository.save(newWebPageTemplate);

      return webPageTemplate;
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  /**
   * This method ONLY saves the edits and records a new modify date. It will ignore all other
   * changes. If you want to change the actual live values (name, content, etc.), you much call this
   * method to save the EDITS and then publish in order to copy those edits over to the live values.
   *
   * @see PublisherService
   */
  @Override
  public WebPageTemplate updateWebPageTemplate(WebPageTemplate entity) throws KayaServiceException {
    try {
      if (entity.getWebPageTemplateId() == null) {
        throw new KayaServiceException("Web page template ID not set.");
      } else {
        Optional<WebPageTemplate> webPageTemplate =
            webPageTemplateRepository.findById(entity.getWebPageTemplateId());

        if (webPageTemplate.isPresent()) {
          WebPageTemplate newEntity =
              WebPageTemplate.builderClone(webPageTemplate.get())
                  .nameEdits(entity.getNameEdits())
                  .contentEdits(entity.getContentEdits())
                  .modifyDate(Instant.now())
                  .publishDate(null)
                  .build();

          newEntity = webPageTemplateRepository.save(newEntity);

          return newEntity;
        } else {
          throw new KayaServiceException("Web page template with given ID does not exist!");
        }
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public void deleteWebPageTemplate(Long webPageTemplateId) throws KayaServiceException {
    try {
      Optional<WebPageTemplate> webPageTemplate =
          webPageTemplateRepository.findById(webPageTemplateId);

      if (webPageTemplate.isPresent()) {
        webPageTemplateRepository.deleteById(webPageTemplateId);
      } else {
        throw new KayaServiceException("Web page template with given ID does not exist!");
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }
}
