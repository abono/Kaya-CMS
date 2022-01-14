package com.aranya.kayacms.db;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.dao.DataAccessException;

/**
 * Quite often JDBC drivers, when they throw SQLException's, supply the error but you loose the
 * actual SQL being run and/or the arguments injected into that SQL. This makes debugging and
 * troubleshooting very difficult.
 *
 * <p>The purpose of this class is to include the original SQL statement (before the setXXX methods
 * are called on PreparedStatements for example) as well as the SQL once the values have been
 * injected. When you get this exception and it is logged, you can then take the SQL with values and
 * copy/paste it into your favorite DB tool and troubleshoot from there. This alleviates you from
 * the problem of having to dig through the code, chase down the SQL being run and have to dredge up
 * the values (probably in debug mode or other logs) and then glue them into the statement manually.
 *
 * <p>This exception can also be used in integration tests where an SQLException is expected - it
 * can look for the SQL run and make sure it was the SQL with values there were supposed to be in
 * the failure.
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DetailedSQLException extends SQLException {

  private static final long serialVersionUID = 4604915157018389148L;

  /** The SQL with all the "?" placeholders. */
  private final String originalSQL;

  /**
   * The SQL where the "?" have been replaced with the values injected into the PreparedStatement or
   * CallableStatement.
   */
  private final String sqlWithValues;

  public DetailedSQLException(SQLException e, String sql) {
    this(e, sql, null);
  }

  public DetailedSQLException(SQLException e, String originalSQL, String sqlWithValues) {
    super(e.getMessage(), e.getSQLState(), e);
    this.originalSQL = originalSQL;
    this.sqlWithValues = sqlWithValues;
    setNextException(e);
  }

  public DetailedSQLException(
      DataAccessException e, String originalSQL, List<Map<String, Object>> batchParams) {
    super(e.getMessage(), e);

    this.originalSQL = originalSQL;

    StringBuilder builder = new StringBuilder();
    for (Map<String, Object> params : batchParams) {
      builder.append('\n').append(buildDisplaySQL(originalSQL, params));
    }
    this.sqlWithValues = builder.toString().trim();
  }

  public DetailedSQLException(
      DataAccessException e, String originalSQL, Map<String, Object> params) {
    super(e.getMessage(), e);

    this.originalSQL = originalSQL;

    this.sqlWithValues = buildDisplaySQL(originalSQL, params);
  }

  public DetailedSQLException(DataAccessException e, String originalSQL, Object... params) {
    super(e.getMessage(), e);

    this.originalSQL = originalSQL;

    this.sqlWithValues = buildDisplaySQL(originalSQL, params);
  }

  /**
   * Get the original SQL and the SQL with values in a nice easy to display and read format but
   * without the exception message.
   *
   * @return A nice human readable string with the SQL in pre and post argument format.
   */
  public String getDetails() {
    StringBuilder builder = new StringBuilder();

    if (originalSQL != null) {
      builder.append("Original SQL:\n\t");
      builder.append(originalSQL);
      builder.append('\n');
    }
    if (sqlWithValues != null) {
      builder.append("SQL with values:\n\t");
      builder.append(sqlWithValues);
      builder.append('\n');
    }

    return builder.toString();
  }

  @Override
  public String getMessage() {
    String message = super.getMessage();
    return (message == null ? null : message.trim()) + ":\n" + getDetails();
  }

  @Override
  public void printStackTrace(PrintStream stream) {
    printStackTrace(new PrintWriter(new OutputStreamWriter(stream, Charset.defaultCharset())));
  }

  @Override
  public void printStackTrace(PrintWriter writer) {
    writer.print(getDetails());

    super.printStackTrace(writer);
    writer.println();
  }

  private String buildDisplaySQL(String sql, Map<String, Object> params) {
    String sqlWithValues = sql;
    for (Entry<String, Object> entry : params.entrySet()) {
      String name = entry.getKey();
      Object value = entry.getValue();
      if (value == null) {
        sqlWithValues = sqlWithValues.replaceAll(":" + name, "null");
      } else if (value instanceof String) {
        sqlWithValues =
            sqlWithValues.replaceAll(
                ":" + name, '\'' + value.toString().replaceAll("'", "''") + '\'');
      } else {
        sqlWithValues = sqlWithValues.replaceAll(":" + name, value.toString());
      }
    }
    return sqlWithValues;
  }

  private String buildDisplaySQL(String sql, Object... params) {
    StringBuilder builder = new StringBuilder();
    int start = 0;
    int end = sql.indexOf('?');
    int index = 0;
    while (end >= 0) {
      builder.append(sql, start, end);
      Object value = params[index++];
      if (value == null) {
        builder.append("null");
      } else if (value instanceof String) {
        builder.append('\'').append(value.toString().replaceAll("'", "''")).append('\'');
      } else {
        builder.append(value);
      }
      start = end + 1;
      end = sql.indexOf('?', start);
    }
    builder.append(sql.substring(start));
    return builder.toString();
  }
}
