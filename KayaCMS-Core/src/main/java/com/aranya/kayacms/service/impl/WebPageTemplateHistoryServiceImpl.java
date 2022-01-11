package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateHistory;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.repository.WebPageTemplateHistoryRepository;
import com.aranya.kayacms.service.WebPageTemplateHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebPageTemplateHistoryServiceImpl implements WebPageTemplateHistoryService {

  private final WebPageTemplateHistoryRepository webPageTemplateHistoryRepository;

  @Override
  public WebPageTemplateHistory createWebPageTemplateHistory(WebPageTemplateHistory entity)
      throws KayaServiceException {
    try {
      WebPageTemplateHistory webPageTemplateHistory = webPageTemplateHistoryRepository.save(entity);

      return webPageTemplateHistory;
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }
}
