package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.repository.MediaRepository;
import com.aranya.kayacms.service.PublisherService;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class PublisherServiceImpl implements PublisherService {

  private MediaRepository mediaRepository;

  @Override
  public List<Media> getUnpublishedMedia(WebSite webSite) {
    return mediaRepository.findByWebSite(webSite);
  }

  // When publishing:
  // * Web Pages w/ Redirects
  // * Media w/ Redirects
  // * Macros

  // Operations (all in a single transaction):
  // * Only if edits != live data
  // * Save to History
  // * Save edits to live data
  // * Add Redirects

}
