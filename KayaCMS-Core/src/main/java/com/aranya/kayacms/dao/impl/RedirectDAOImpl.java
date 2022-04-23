package com.aranya.kayacms.dao.impl;

import com.aranya.kayacms.beans.redirect.Redirect;
import com.aranya.kayacms.beans.redirect.RedirectId;
import com.aranya.kayacms.beans.redirect.RedirectSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.dao.RedirectDAO;
import com.aranya.kayacms.dao.impl.extractor.SearchExtractor;
import com.aranya.kayacms.dao.impl.mapper.RedirectRowMapper;
import com.aranya.kayacms.db.DetailedSQLException;
import com.aranya.kayacms.util.SearchResults;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedirectDAOImpl extends AbstractDAO implements RedirectDAO {

  private final RedirectRowMapper rowMapper = new RedirectRowMapper();

  public RedirectDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    super(jdbcTemplate, "redirect");
  }

  private String buildWhereClause(RedirectSearchCriteria criteria, Map<String, Object> paramMap) {
    StringBuilder builder = new StringBuilder("where 1 = 1 ");

    WebSiteId webSiteId = criteria.getWebSiteId();
    if (webSiteId != null) {
      builder.append("and web_site_id = :webSiteId ");
      paramMap.put("webSiteId", webSiteId.getId());
    }

    return builder.toString();
  }

  @Override
  public SearchResults<Redirect> searchRedirects(RedirectSearchCriteria criteria)
      throws SQLException { // TODO Auto-generated method stub

    Map<String, Object> paramMap = new HashMap<>();
    String whereClause = buildWhereClause(criteria, paramMap);

    String sql = getSQL("select.sql");

    try {
      return jdbcTemplate.query(
          sql + " " + whereClause, paramMap, new SearchExtractor<Redirect>(criteria, rowMapper));
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public Redirect getRedirect(WebSiteId webSiteId, String fromPath) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webSiteId", webSiteId.getId());
    paramMap.put("fromPath", fromPath);

    String sql = getSQL("select.sql") + " where web_site_id = :webSiteId and from_path = :fromPath";

    try {
      return jdbcTemplate.queryForObject(sql, paramMap, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public Redirect getRedirect(RedirectId redirectId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("redirectId", redirectId.getId());

    String sql = getSQL("select.sql") + " where redirect_id = :redirectId";

    try {
      return jdbcTemplate.queryForObject(sql, paramMap, rowMapper);
    } catch (EmptyResultDataAccessException e) {
      return null;
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public RedirectId insertRedirect(Redirect redirect) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("fromPath", redirect.getFromPath());
    paramMap.put("toPath", redirect.getToPath());
    paramMap.put("webSiteId", redirect.getWebSiteId().getId());

    String sql = getSQL("insert.sql");
    String sqlForId = getSQL("getLastId.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
      return jdbcTemplate.queryForObject(
          sqlForId, paramMap, (rs, rowNum) -> new RedirectId(rs.getLong("redirect_id")));
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void updateRedirect(Redirect redirect) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("redirectId", redirect.getRedirectId().getId());
    paramMap.put("fromPath", redirect.getFromPath());
    paramMap.put("toPath", redirect.getToPath());

    String sql = getSQL("update.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void deleteRedirect(RedirectId redirectId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("redirectId", redirectId.getId());

    String sql = getSQL("delete.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }
}
