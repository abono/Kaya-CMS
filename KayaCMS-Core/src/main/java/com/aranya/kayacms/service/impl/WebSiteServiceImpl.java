package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.repository.WebSiteRepository;
import com.aranya.kayacms.service.WebSiteService;
import java.time.Instant;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebSiteServiceImpl implements WebSiteService {

  private final WebSiteRepository webSiteRepository;

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

      WebSite webSiteExample = WebSite.builder().domainName(domainName).build();
      Example<WebSite> example = Example.of(webSiteExample);
      Optional<WebSite> webSite = webSiteRepository.findOne(example);
      if (webSite.isPresent()) {
        return webSite.get();
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public WebSite createWebSite(WebSite webSite) throws KayaServiceException {
    try {
      WebSite newWebSite =
          WebSite.builderClone(webSite)
              .webSiteId(null)
              .createDate(Instant.now())
              .modifyDate(Instant.now())
              .build();

      webSite = webSiteRepository.save(newWebSite);

      return webSite;
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public WebSite updateWebSite(WebSite entity) throws KayaServiceException {
    try {
      if (entity.getWebSiteId() == null) {
        throw new KayaServiceException("Web site ID not set.");
      } else {
        Optional<WebSite> webSite = webSiteRepository.findById(entity.getWebSiteId());

        if (webSite.isPresent()) {
          WebSite newEntity =
              WebSite.builderClone(webSite.get())
                  .domainName(cleanDomainName(entity.getDomainName()))
                  .setUpComplete(entity.getSetUpComplete())
                  .modifyDate(Instant.now())
                  .build();

          newEntity = webSiteRepository.save(newEntity);

          return newEntity;
        } else {
          throw new KayaServiceException("Web site with given ID does not exist!");
        }
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }
}
