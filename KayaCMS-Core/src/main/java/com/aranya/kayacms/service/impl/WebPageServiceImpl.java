package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpage.WebPageSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.repository.WebPageRepository;
import com.aranya.kayacms.service.PublisherService;
import com.aranya.kayacms.service.WebPageService;
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
public class WebPageServiceImpl implements WebPageService {

  private final WebPageRepository webPageRepository;

  @Override
  public boolean isWebPageSetUp(WebSite webSite) throws KayaServiceException {
    try {
      WebPage webPage = WebPage.builder().webSite(webSite).build();
      Example<WebPage> example = Example.of(webPage);
      long count = webPageRepository.count(example);
      return count > 0;
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public SearchResults<WebPage> searchWebPages(WebPageSearchCriteria criteria)
      throws KayaServiceException {
    try {
      Sort sort = Sort.by("path");
      Pageable pageable = PageRequest.of(criteria.getPage() - 1, criteria.getItemsPerPage(), sort);

      WebPage webPage = WebPage.builder().webSite(criteria.getWebSite()).build();
      Example<WebPage> example = Example.of(webPage);

      Page<WebPage> results = webPageRepository.findAll(example, pageable);

      return new ListSearchResults<WebPage>(
          results.getContent(),
          criteria.getPage(),
          criteria.getItemsPerPage(),
          results.getTotalPages());
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public WebPage getWebPage(WebSite webSite, String path) throws KayaServiceException {
    try {
      WebPage webPage = WebPage.builder().path(path).webSite(webSite).build();
      Example<WebPage> example = Example.of(webPage);
      Optional<WebPage> results = webPageRepository.findOne(example);
      if (results.isPresent()) {
        return results.get();
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public WebPage getWebPage(Long webPageId) throws KayaServiceException {
    try {
      Optional<WebPage> optional = webPageRepository.findById(webPageId);
      if (optional.isPresent()) {
        return optional.get();
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  /** Creates an UNPUBLISHED web page. */
  @Override
  public WebPage createWebPage(WebPage webPage) throws KayaServiceException {
    try {
      WebPage newWebPage =
          WebPage.builderClone(webPage)
              .webPageId(null)
              .createDate(Instant.now())
              .modifyDate(Instant.now())
              .publishDate(null)
              .build();

      webPage = webPageRepository.save(newWebPage);

      return webPage;
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
  public WebPage updateWebPage(WebPage entity) throws KayaServiceException {
    try {
      if (entity.getWebPageId() == null) {
        throw new KayaServiceException("Web page ID not set.");
      } else {
        Optional<WebPage> webPage = webPageRepository.findById(entity.getWebPageId());

        if (webPage.isPresent()) {
          WebPage newEntity =
              WebPage.builderClone(webPage.get())
                  .pathEdits(entity.getPathEdits())
                  .typeEdits(entity.getTypeEdits())
                  .titleEdits(entity.getTitleEdits())
                  .parametersEdits(entity.getParametersEdits())
                  .descriptionEdits(entity.getDescriptionEdits())
                  .contentEdits(entity.getContentEdits())
                  .modifyDate(Instant.now())
                  .build();

          newEntity = webPageRepository.save(newEntity);

          return newEntity;
        } else {
          throw new KayaServiceException("Web page with given ID does not exist!");
        }
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public void deleteWebPage(Long webPageId) throws KayaServiceException {
    try {
      Optional<WebPage> webPage = webPageRepository.findById(webPageId);

      if (webPage.isPresent()) {
        webPageRepository.deleteById(webPageId);
      } else {
        throw new KayaServiceException("Web page with given ID does not exist!");
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }
}
