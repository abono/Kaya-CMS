package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.media.MediaId;
import com.aranya.kayacms.beans.media.MediaSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.util.SearchResults;
import java.util.List;

public interface MediaService {

  SearchResults<Media> searchMedias(MediaSearchCriteria criteria) throws KayaServiceException;

  List<Media> getUnpublishedMedia(WebSiteId webSiteId) throws KayaServiceException;

  Media getMedia(WebSiteId webSiteId, String path) throws KayaServiceException;

  Media getMedia(MediaId mediaId) throws KayaServiceException;

  Media createMedia(Media media) throws KayaServiceException;

  Media updateMedia(Media entity) throws KayaServiceException;

  void publishMedia(List<MediaId> mediaIds) throws KayaServiceException;

  void deleteMedia(MediaId mediaId) throws KayaServiceException;
}
