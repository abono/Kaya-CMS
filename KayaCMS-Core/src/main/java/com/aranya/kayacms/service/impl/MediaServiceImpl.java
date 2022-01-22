package com.aranya.kayacms.service.impl;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.media.MediaId;
import com.aranya.kayacms.beans.media.MediaSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.dao.MediaDAO;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.service.MediaService;
import com.aranya.kayacms.util.SearchResults;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class MediaServiceImpl implements MediaService {

  private final MediaDAO mediaDAO;

  @Override
  public SearchResults<Media> searchMedias(MediaSearchCriteria criteria)
      throws KayaServiceException {
    try {
      return mediaDAO.searchMedia(criteria);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public List<Media> getUnpublishedMedia(WebSiteId webSiteId) throws KayaServiceException {
    try {
      return mediaDAO.getUnpublishedMedia(webSiteId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public Media getMedia(MediaId mediaId) throws KayaServiceException {
    try {
      return mediaDAO.getMedia(mediaId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public Media getMedia(WebSiteId webSiteId, String path) throws KayaServiceException {
    try {
      return mediaDAO.getMedia(webSiteId, path);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  /** Creates an UNPUBLISHED media. */
  @Override
  public Media createMedia(Media media) throws KayaServiceException {
    try {
      MediaId mediaId = mediaDAO.insertMedia(media);
      return mediaDAO.getMedia(mediaId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  /**
   * This method ONLY saves the edits and records a new modify date. It will ignore all other
   * changes. If you want to change the actual live values (type, path, content, etc.), you must
   * call this method to save the EDITS and then publish in order to copy those edits over to the
   * live values.
   */
  @Override
  public Media updateMedia(Media media) throws KayaServiceException {
    try {
      if (media.getMediaId() == null) {
        throw new KayaServiceException("Media ID not set.");
      } else {
        mediaDAO.updateMedia(media);
        return mediaDAO.getMedia(media.getMediaId());
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  @Transactional
  public void publishMedia(List<MediaId> mediaIds) throws KayaServiceException {
    try {
      for (MediaId mediaId : mediaIds) {
        mediaDAO.publishMedia(mediaId);
      }
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }

  @Override
  public void deleteMedia(MediaId mediaId) throws KayaServiceException {
    try {
      if (mediaDAO.getMedia(mediaId) == null) {
        throw new KayaServiceException("Media with given ID does not exist!");
      }
      mediaDAO.deleteMedia(mediaId);
    } catch (Exception e) {
      throw new KayaServiceException(e);
    }
  }
}
