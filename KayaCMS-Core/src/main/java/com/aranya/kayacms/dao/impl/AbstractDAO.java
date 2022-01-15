package com.aranya.kayacms.dao.impl;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class AbstractDAO {

  private final Map<String, String> sqlCache = new HashMap<>();

  protected final NamedParameterJdbcTemplate jdbcTemplate;

  private final String name;

  public AbstractDAO(NamedParameterJdbcTemplate jdbcTemplate, String name) {
    this.jdbcTemplate = jdbcTemplate;
    this.name = name;
  }

  protected String getSQL(String fileName) throws SQLException {
    String sql = sqlCache.get(fileName);
    if (sql == null) {
      try (InputStream in =
          getClass().getResourceAsStream("/db/scripts/" + name + "/" + fileName)) {
        sql = IOUtils.toString(in, Charset.defaultCharset());
        sqlCache.put(fileName, sql);
      } catch (Exception e) {
        throw new SQLException("Error reading sql file " + fileName, e);
      }
    }
    return sql;
  }
}
