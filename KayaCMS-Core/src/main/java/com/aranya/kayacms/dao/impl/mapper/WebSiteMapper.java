package com.aranya.kayacms.dao.impl.mapper;

import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.beans.website.WebSiteId;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WebSiteMapper extends AbstractRowMapper<WebSite> {

  @Override
  public WebSite mapRow(ResultSet rs, int rowNum) throws SQLException {
    return WebSite.builder()
        .webSiteId(new WebSiteId(rs.getLong("web_site_id")))
        .domainName(rs.getString("domain_name"))
        .setUpComplete(rs.getBoolean("set_up_complete"))
        .createDate(extractDayAndTime(rs, "create_date"))
        .modifyDate(extractDayAndTime(rs, "modify_date"))
        .build();
  }
}
