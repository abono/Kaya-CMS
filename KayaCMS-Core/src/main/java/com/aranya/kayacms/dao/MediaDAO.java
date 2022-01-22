package com.aranya.kayacms.dao;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.media.MediaId;
import com.aranya.kayacms.beans.media.MediaSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.util.SearchResults;
import java.sql.SQLException;
import java.util.List;

public interface MediaDAO {

  SearchResults<Media> searchMedia(MediaSearchCriteria criteria) throws SQLException;

  List<Media> getUnpublishedMedia(WebSiteId webSiteId) throws SQLException;

  Media getMedia(MediaId mediaId) throws SQLException;

  Media getMedia(WebSiteId webSiteId, String path) throws SQLException;

  MediaId insertMedia(Media media) throws SQLException;

  void updateMedia(Media media) throws SQLException;

  void publishMedia(MediaId mediaId) throws SQLException;

  void deleteMedia(MediaId mediaId) throws SQLException;
}
