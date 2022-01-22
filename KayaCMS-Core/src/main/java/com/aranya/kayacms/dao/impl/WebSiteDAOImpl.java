package com.aranya.kayacms.dao.impl;

import com.aranya.kayacms.beans.website.WebSite;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.dao.WebSiteDAO;
import com.aranya.kayacms.dao.impl.mapper.WebSiteMapper;
import com.aranya.kayacms.db.DetailedSQLException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WebSiteDAOImpl extends AbstractDAO implements WebSiteDAO {

  private final WebSiteMapper rowMapper = new WebSiteMapper();

  @Autowired
  public WebSiteDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    super(jdbcTemplate, "web_site");
  }

  @Override
  public WebSite getWebSite(String domainName) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("domainName", domainName);

    String sql = getSQL("select.sql") + " where domain_name = :domainName";

    try {
      return jdbcTemplate.queryForObject(sql, paramMap, rowMapper);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public WebSite getWebSite(WebSiteId webSiteId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webSiteId", webSiteId.getId());

    String sql = getSQL("select.sql") + " where web_site_id = :webSiteId";

    try {
      return jdbcTemplate.queryForObject(sql, paramMap, rowMapper);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public WebSiteId insertWebSite(WebSite webSite) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("domainName", webSite.getDomainName());
    paramMap.put("setUpComplete", webSite.getSetUpComplete());

    String sql = getSQL("insert.sql");
    String sqlForId = getSQL("getLastId.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
      return jdbcTemplate.queryForObject(
          sqlForId, paramMap, (rs, rowNum) -> new WebSiteId(rs.getLong("admin_user_id")));
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void updateWebSite(WebSite webSite) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webSiteId", webSite.getWebSiteId().getId());
    paramMap.put("domainName", webSite.getDomainName());
    paramMap.put("setUpComplete", webSite.getSetUpComplete());

    String sql = getSQL("update.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void deleteWebSite(WebSiteId webSiteId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webSiteId", webSiteId.getId());

    String sql = getSQL("delete.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }
}
