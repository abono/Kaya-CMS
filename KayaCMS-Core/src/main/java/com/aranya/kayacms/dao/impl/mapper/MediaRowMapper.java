package com.aranya.kayacms.dao.impl.mapper;

import com.aranya.kayacms.beans.media.Media;
import com.aranya.kayacms.beans.media.MediaId;
import com.aranya.kayacms.beans.website.WebSiteId;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MediaRowMapper extends AbstractRowMapper<Media> {

  private final boolean summary;

  public MediaRowMapper(boolean summary) {
    this.summary = summary;
  }

  @Override
  public Media mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Media.builder()
        .mediaId(new MediaId(rs.getLong("media_id")))
        .type(rs.getString("type"))
        .path(rs.getString("path"))
        .content(summary ? null : getBinaryContent(rs, "content"))
        .typeEdits(rs.getString("type_edits"))
        .pathEdits(rs.getString("path_edits"))
        .contentEdits(summary ? null : getBinaryContent(rs, "content_edits"))
        .createDate(extractDayAndTime(rs, "create_date"))
        .modifyDate(extractDayAndTime(rs, "modify_date"))
        .publishDate(extractNullableDayAndTime(rs, "publish_date"))
        .webSiteId(new WebSiteId(rs.getLong("web_site_id")))
        .build();
  }
}
