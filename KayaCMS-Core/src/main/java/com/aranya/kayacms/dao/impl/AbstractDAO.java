package com.aranya.kayacms.dao.impl;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@RequiredArgsConstructor
public abstract class AbstractDAO {

  private final Map<String, String> sqlCache = new HashMap<>();

  protected final NamedParameterJdbcTemplate jdbcTemplate;

  private final String name;

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
