package com.aranya.kayacms.dao.impl;

import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplate;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateId;
import com.aranya.kayacms.beans.webpagetemplate.WebPageTemplateSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.dao.WebPageTemplateDAO;
import com.aranya.kayacms.dao.impl.extractor.SearchExtractor;
import com.aranya.kayacms.dao.impl.mapper.WebPageTemplateMapper;
import com.aranya.kayacms.db.DetailedSQLException;
import com.aranya.kayacms.util.SearchResults;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WebPageTemplateDAOImpl extends AbstractDAO implements WebPageTemplateDAO {

  private final WebPageTemplateMapper rowMapper = new WebPageTemplateMapper();

  @Autowired
  public WebPageTemplateDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    super(jdbcTemplate, "web_page_template");
  }

  private String buildWhereClause(
      WebPageTemplateSearchCriteria criteria, Map<String, Object> paramMap) {
    StringBuilder builder = new StringBuilder("where 1 = 1 ");

    WebSiteId webSiteId = criteria.getWebSiteId();
    if (webSiteId != null) {
      builder.append("and web_site_id = :webSiteId ");
      paramMap.put("webSiteId", webSiteId.getId());
    }

    return builder.toString();
  }

  @Override
  public SearchResults<WebPageTemplate> searchWebPageTemplate(
      WebPageTemplateSearchCriteria criteria) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    String whereClause = buildWhereClause(criteria, paramMap);

    String sql = getSQL("select.sql");

    try {
      return jdbcTemplate.query(
          sql + " " + whereClause,
          paramMap,
          new SearchExtractor<WebPageTemplate>(criteria, rowMapper));
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public List<WebPageTemplate> getUnpublishedWebPageTemplate(WebSiteId webSiteId)
      throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webSiteId", webSiteId.getId());

    String sql = getSQL("selectUnpublished.sql");

    try {
      return jdbcTemplate.query(sql, paramMap, rowMapper);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public WebPageTemplate getWebPageTemplate(WebPageTemplateId webPageTemplateId)
      throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webPageTemplateId", webPageTemplateId.getId());

    String sql = getSQL("select.sql") + " where web_page_template_id = :webPageTemplateId";

    try {
      return jdbcTemplate.queryForObject(sql, paramMap, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public WebPageTemplateId insertWebPageTemplate(WebPageTemplate webPageTemplate)
      throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("nameEdits", webPageTemplate.getNameEdits());
    paramMap.put("contentEdits", webPageTemplate.getContentEdits());
    paramMap.put("webSiteId", webPageTemplate.getWebSiteId().getId());

    String sql = getSQL("insert.sql");
    String sqlForId = getSQL("getLastId.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
      return jdbcTemplate.queryForObject(
          sqlForId,
          paramMap,
          (rs, rowNum) -> new WebPageTemplateId(rs.getLong("web_page_template_id")));
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void updateWebPageTemplate(WebPageTemplate webPageTemplate) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webPageTemplateId", webPageTemplate.getWebPageTemplateId().getId());
    paramMap.put("nameEdits", webPageTemplate.getNameEdits());
    paramMap.put("contentEdits", webPageTemplate.getContentEdits());

    String sql = getSQL("update.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void publishWebPageTemplate(WebPageTemplateId webPageTemplateId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webPageTemplateId", webPageTemplateId.getId());

    String sql = getSQL("publish.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void deleteWebPageTemplate(WebPageTemplateId webPageTemplateId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webPageTemplateId", webPageTemplateId.getId());

    String sql = getSQL("delete.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }
}
