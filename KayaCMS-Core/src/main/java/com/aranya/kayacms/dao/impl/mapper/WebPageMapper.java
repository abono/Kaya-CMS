package com.aranya.kayacms.dao.impl.mapper;

import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpage.WebPageId;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateId;
import com.aranya.kayacms.beans.website.WebSiteId;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WebPageMapper extends AbstractRowMapper<WebPage> {

  @Override
  public WebPage mapRow(ResultSet rs, int rowNum) throws SQLException {
    return WebPage.builder()
        .webPageId(new WebPageId(rs.getLong("web_page_id")))
        .type(rs.getString("type"))
        .path(rs.getString("path"))
        .title(rs.getString("title"))
        .description(rs.getString("description"))
        .content(rs.getString("content"))
        .parameters(rs.getString("parameters"))
        .typeEdits(rs.getString("type_edits"))
        .pathEdits(rs.getString("path_edits"))
        .titleEdits(rs.getString("title_edits"))
        .descriptionEdits(rs.getString("description_edits"))
        .contentEdits(rs.getString("content_edits"))
        .parametersEdits(rs.getString("parameters_edits"))
        .createDate(extractDayAndTime(rs, "create_date"))
        .modifyDate(extractDayAndTime(rs, "modify_date"))
        .publishDate(extractNullableDayAndTime(rs, "publish_date"))
        .webSiteId(new WebSiteId(rs.getLong("web_site_id")))
        .webPageTemplateId(new WebPageTemplateId(rs.getLong("web_page_template_id")))
        .webPageTemplateIdEdits(new WebPageTemplateId(rs.getLong("web_page_template_id_edits")))
        .build();
  }
}
