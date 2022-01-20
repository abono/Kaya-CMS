package com.aranya.kayacms.dao.impl.mapper;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateId;
import com.aranya.kayacms.beans.website.WebSiteId;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WebPageTemplateMapper extends AbstractRowMaster<WebPageTemplate> {

  @Override
  public WebPageTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
    return WebPageTemplate.builder()
        .webPageTemplateId(new WebPageTemplateId(rs.getLong("web_page_template_id")))
        .name(rs.getString("name"))
        .content(rs.getString("content"))
        .nameEdits(rs.getString("name_edits"))
        .contentEdits(rs.getString("content_edits"))
        .createDate(extractDayAndTime(rs, "create_date"))
        .modifyDate(extractDayAndTime(rs, "modify_date"))
        .publishDate(extractNullableDayAndTime(rs, "publish_date"))
        .webSiteId(new WebSiteId(rs.getLong("web_site_id")))
        .build();
  }
}
