package com.aranya.kayacms.service;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.media.MediaSearchCriteria;
import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.exception.KayaServiceException;
import com.aranya.kayacms.util.SearchResults;

public interface MediaService {

  SearchResults<Media> searchMedias(MediaSearchCriteria criteria);

  Media getMedia(WebSite webSite, String path) throws KayaServiceException;

  Media getMedia(Long mediaId) throws KayaServiceException;

  Media createMedia(Media media) throws KayaServiceException;

  Media updateMedia(Media entity) throws KayaServiceException;

  void deleteMedia(Long mediaId) throws KayaServiceException;
}
