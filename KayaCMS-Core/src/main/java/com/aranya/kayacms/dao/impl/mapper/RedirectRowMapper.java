package com.aranya.kayacms.dao.impl.mapper;

import com.aranya.kayacms.beans.redirect.Redirect;
import com.aranya.kayacms.beans.redirect.RedirectId;
import com.aranya.kayacms.beans.website.WebSiteId;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RedirectRowMapper extends AbstractRowMapper<Redirect> {

  @Override
  public Redirect mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Redirect.builder()
        .redirectId(new RedirectId(rs.getLong("redirect_id")))
        .fromPath(rs.getString("from_path"))
        .toPath(rs.getString("to_path"))
        .createDate(extractDayAndTime(rs, "create_date"))
        .modifyDate(extractDayAndTime(rs, "modify_date"))
        .webSiteId(new WebSiteId(rs.getLong("web_site_id")))
        .build();
  }
}
