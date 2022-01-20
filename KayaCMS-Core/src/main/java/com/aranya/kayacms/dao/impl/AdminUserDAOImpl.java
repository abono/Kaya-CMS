package com.aranya.kayacms.dao.impl;

import com.aranya.kayacms.beans.adminuser.AdminUser;
import com.aranya.kayacms.beans.adminuser.AdminUserId;
import com.aranya.kayacms.beans.adminuser.AdminUserSearchCriteria;
import com.aranya.kayacms.beans.website.WebSiteId;
import com.aranya.kayacms.dao.AdminUserDAO;
import com.aranya.kayacms.dao.impl.extractor.SearchExtractor;
import com.aranya.kayacms.dao.impl.mapper.AdminUserMapper;
import com.aranya.kayacms.db.DetailedSQLException;
import com.aranya.kayacms.util.SearchResults;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserDAOImpl extends AbstractDAO implements AdminUserDAO {

  private final AdminUserMapper rowMapper = new AdminUserMapper();

  @Autowired
  public AdminUserDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    super(jdbcTemplate, "admin_user");
  }

  private String buildWhereClause(AdminUserSearchCriteria criteria, Map<String, Object> paramMap) {
    StringBuilder builder = new StringBuilder("where 1 = 1 ");

    WebSiteId webSiteId = criteria.getWebSiteId();
    if (webSiteId != null) {
      builder.append("and web_site_id = :webSiteId ");
      paramMap.put("webSiteId", webSiteId.getId());
    }

    return builder.toString();
  }

  @Override
  public SearchResults<AdminUser> searchAdminUser(AdminUserSearchCriteria criteria)
      throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    String whereClause = buildWhereClause(criteria, paramMap);

    String sql = getSQL("select.sql");

    try {
      return jdbcTemplate.query(
          sql + " " + whereClause, paramMap, new SearchExtractor<AdminUser>(criteria, rowMapper));
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public AdminUser getAdminUser(WebSiteId webSiteId, String userName, String password)
      throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("webSiteId", webSiteId.getId());
    paramMap.put("userName", userName);
    paramMap.put("password", password);

    String sql =
        getSQL("select.sql")
            + " where web_site_id = :webSiteId"
            + "   and user_name = :userName"
            + "   and password = :password";

    try {
      return jdbcTemplate.queryForObject(sql, paramMap, rowMapper);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public AdminUser getAdminUser(AdminUserId adminUserId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("adminUserId", adminUserId.getId());

    String sql = getSQL("select.sql") + " where admin_user_id = :adminUserId";

    try {
      return jdbcTemplate.queryForObject(sql, paramMap, rowMapper);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public AdminUserId insertAdminUser(AdminUser adminUser) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("firstName", adminUser.getFirstName());
    paramMap.put("lastName", adminUser.getLastName());
    paramMap.put("email", adminUser.getEmail());
    paramMap.put("userName", adminUser.getUserName());
    paramMap.put("password", adminUser.getPassword());
    paramMap.put("webSiteId", adminUser.getWebSiteId().getId());

    String sql = getSQL("insert.sql");
    String sqlForId = getSQL("getLastId.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
      return jdbcTemplate.queryForObject(
          sqlForId, paramMap, (rs, rowNum) -> new AdminUserId(rs.getLong("admin_user_id")));
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void updateAdminUser(AdminUser adminUser) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("adminUserId", adminUser.getAdminUserId().getId());
    paramMap.put("firstName", adminUser.getFirstName());
    paramMap.put("lastName", adminUser.getLastName());
    paramMap.put("email", adminUser.getEmail());
    paramMap.put("userName", adminUser.getUserName());
    paramMap.put("password", adminUser.getPassword());

    String sql = getSQL("update.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }

  @Override
  public void deleteAdminUser(AdminUserId adminUserId) throws SQLException {

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("adminUserId", adminUserId.getId());

    String sql = getSQL("delete.sql");

    try {
      jdbcTemplate.update(sql, paramMap);
    } catch (DataAccessException e) {
      throw new DetailedSQLException(e, sql, paramMap);
    }
  }
}
