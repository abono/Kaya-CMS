package com.aranya.kayacms.dao.impl.mapper;

import com.aranya.kayacms.properties.DayAndTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;

public abstract class AbstractRowMaster<T> implements RowMapper<T> {

  protected DayAndTime extractDayAndTime(ResultSet rs, String columnName) throws SQLException {
    Timestamp ts = rs.getTimestamp(columnName);
    if (ts == null) {
      throw new SQLException("Timestamp for column " + columnName + " cannot be null");
    }
    return new DayAndTime(ts.getTime());
  }

  protected DayAndTime extractNullableDayAndTime(ResultSet rs, String columnName)
      throws SQLException {
    Timestamp ts = rs.getTimestamp(columnName);
    return ts == null ? null : new DayAndTime(ts.getTime());
  }
}
