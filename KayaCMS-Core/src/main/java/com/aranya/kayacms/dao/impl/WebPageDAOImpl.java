package com.aranya.kayacms.dao.impl;

import com.aranya.kayacms.beans.webpage.WebPage;
import com.aranya.kayacms.beans.webpage.WebPageId;
import com.aranya.kayacms.beans.webpage.WebPageSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.dao.WebPageDAO;
import com.aranya.kayacms.dao.impl.extractor.SearchExtractor;
import com.aranya.kayacms.dao.impl.mapper.WebPageMapper;
import com.aranya.kayacms.db.DetailedSQLException;
import com.aranya.kayacms.util.SearchResults;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WebPageDAOImpl extends AbstractDAO implements WebPageDAO {

  private final WebPageMapper rowMapper = new WebPageMapper();

  public WebPageDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    super(jdbcTemplate, "web_page");
  }

  private String buildWhereClause(WebPageSearchCriteria criteria, Map<String, Object> paramMap) {
    StringBuilder builder = new StringBuilder("where 1 = 1 ");

    WebSiteId webSiteId = criteria.getWebSiteId();
    if (webSiteId != null) {
      builder.append("and web_site_id = :webSiteId ");
      paramMap.put("webSiteId", webSiteId.getId());
    }

    return builder.toString();
  }

  @Override
  public SearchResults<WebPage> searchWebPage(WebPageSearchCriteria criteria) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    String whereClause = buildWhereClause(criteria, paramMap);

    String sql = getSQL("select.sql");

    try {
      return jdbcTemplate.query(
          sql + " " + whereClause, paramMap, new SearchExtractor<WebPage>(criteria, rowMapper));
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public List<WebPage> getUnpublishedWebPage(WebSiteId webSiteId) throws SQLException {

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
  public WebPage getWebPage(WebSiteId webSiteId, String path) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webSiteId", webSiteId.getId());
    paramMap.put("path", path);

    String sql = getSQL("select.sql") + " where web_site_id = :webSiteId and path = :path";

    try {
      return jdbcTemplate.queryForObject(sql, paramMap, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public WebPage getWebPage(WebPageId webPageId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webPageId", webPageId.getId());

    String sql = getSQL("select.sql") + " where web_page_id = :webPageId";

    try {
      return jdbcTemplate.queryForObject(sql, paramMap, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public WebPageId insertWebPage(WebPage webPage) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("typeEdits", webPage.getTypeEdits());
    paramMap.put("pathEdits", webPage.getPathEdits());
    paramMap.put("titleEdits", webPage.getTitleEdits());
    paramMap.put("descriptionEdits", webPage.getDescriptionEdits());
    paramMap.put("contentEdits", webPage.getContentEdits());
    paramMap.put("parametersEdits", webPage.getParametersEdits());
    paramMap.put("webSiteId", webPage.getWebSiteId().getId());
    paramMap.put("webPageTemplateIdEdits", webPage.getWebPageTemplateIdEdits().getId());

    String sql = getSQL("insert.sql");
    String sqlForId = getSQL("getLastId.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
      return jdbcTemplate.queryForObject(
          sqlForId, paramMap, (rs, rowNum) -> new WebPageId(rs.getLong("web_page_id")));
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void updateWebPage(WebPage webPage) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("typeEdits", webPage.getTypeEdits());
    paramMap.put("pathEdits", webPage.getPathEdits());
    paramMap.put("titleEdits", webPage.getTitleEdits());
    paramMap.put("descriptionEdits", webPage.getDescriptionEdits());
    paramMap.put("contentEdits", webPage.getContentEdits());
    paramMap.put("parametersEdits", webPage.getParametersEdits());
    paramMap.put("webPageTemplateIdEdits", webPage.getWebPageTemplateIdEdits().getId());
    paramMap.put("webPageId", webPage.getWebPageId().getId());

    String sql = getSQL("update.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void publishWebPage(WebPageId webPageId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webPageId", webPageId.getId());

    String sql = getSQL("publish.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void deleteWebPage(WebPageId webPageId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webPageId", webPageId.getId());

    String sql = getSQL("delete.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }
}
