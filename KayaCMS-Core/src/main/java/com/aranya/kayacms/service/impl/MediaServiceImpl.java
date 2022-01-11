package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.media.MediaSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.repository.MediaRepository;
import com.aranya.kayacms.service.MediaService;
import com.aranya.kayacms.service.PublisherService;
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
public class MediaServiceImpl implements MediaService {

  private final MediaRepository mediaRepository;

  @Override
  public SearchResults<Media> searchMedias(MediaSearchCriteria criteria) {
    Sort sort = Sort.by("path");
    Pageable pageable = PageRequest.of(criteria.getPage() - 1, criteria.getItemsPerPage(), sort);

    Media media = Media.builder().webSite(criteria.getWebSite()).build();
    Example<Media> example = Example.of(media);

    Page<Media> results = mediaRepository.findAll(example, pageable);

    return new ListSearchResults<Media>(
        results.getContent(),
        criteria.getPage(),
        criteria.getItemsPerPage(),
        results.getTotalPages());
  }

  @Override
  public Media getMedia(WebSite webSite, String path) throws KayaServiceException {
    try {
      Media media = Media.builder().path(path).webSite(webSite).build();
      Example<Media> example = Example.of(media);
      Optional<Media> results = mediaRepository.findOne(example);
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
  public Media getMedia(Long mediaId) throws KayaServiceException {
    try {
      Optional<Media> optional = mediaRepository.findById(mediaId);
      if (optional.isPresent()) {
        return optional.get();
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  /** Creates an UNPUBLISHED media. */
  @Override
  public Media createMedia(Media media) throws KayaServiceException {
    try {
      Media newMedia =
          Media.builder()
              .mediaId(null)
              .createDate(Instant.now())
              .modifyDate(Instant.now())
              .publishDate(null)
              .build();

      media = mediaRepository.save(newMedia);

      return media;
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
  public Media updateMedia(Media entity) throws KayaServiceException {
    try {
      if (entity.getMediaId() == null) {
        throw new KayaServiceException("Media ID not set.");
      } else {
        Optional<Media> media = mediaRepository.findById(entity.getMediaId());

        if (media.isPresent()) {
          Media newEntity =
              Media.builderClone(media.get())
                  .pathEdits(entity.getPathEdits())
                  .typeEdits(entity.getTypeEdits())
                  .contentEdits(entity.getContentEdits())
                  .modifyDate(Instant.now())
                  .build();

          newEntity = mediaRepository.save(newEntity);

          return newEntity;
        } else {
          throw new KayaServiceException("Media with given ID does not exist!");
        }
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public void deleteMedia(Long mediaId) throws KayaServiceException {
    try {
      Optional<Media> media = mediaRepository.findById(mediaId);

      if (media.isPresent()) {
        mediaRepository.deleteById(mediaId);
      } else {
        throw new KayaServiceException("Media with given ID does not exist!");
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }
}
