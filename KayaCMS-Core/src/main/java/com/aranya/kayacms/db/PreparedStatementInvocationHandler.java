package com.aranya.kayacms.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This object is responsible for capturing all calls to build, populate and execute a
 * PreparedStatement. It will delegate those calls to the statement it proxies for and will also log
 * all SQL / DDL executed against the database. The logs contain the raw SQL before values are
 * inserted in as well as the SQL after the values are inserted in (where the "?" occur).
 *
 * <p>Another feature of this class is that it wraps all SQLExceptions thrown with a
 * DetailedSQLException which also contains the raw SQL and SQL with values inserted so, as the
 * exception bubbles up through the application, the SQL causing the problem is not lost.
 *
 * <p>Note that, when configuring the logging, the Logger used for logging the executed SQL is
 * different from the logging for the rest of this class (it has two different loggers). So if you
 * want to configure all logging for this class, just configure for
 *
 * <pre>com.aranya.kayacms.db.StatementInvocationHandler</pre>
 *
 * However, if you just want the SQL logged (this is handy if you are wanting to save off all SQL
 * executed during a specific run of Schema Updater), you can configure for
 *
 * <pre>com.aranya.kayacms.db.StatementInvocationHandler.SQL</pre>
 *
 * For example, if you are using Logback and want to send all SQL (and only the SQL) to a file, you
 * can add this:
 *
 * <pre>
 * &lt;appender name="SQL-FILE" class="ch.qos.logback.core.FileAppender"&gt;
 *   &lt;file&gt;target/sql.log&lt;/file&gt;
 *   &lt;append&gt;true&lt;/append&gt;
 *   &lt;encoder&gt;
 *     &lt;pattern&gt;%msg %n&lt;/pattern&gt;
 *   &lt;/encoder&gt;
 * &lt;/appender&gt;
 *
 * &lt;logger name="com.aranya.kayacms.db.PreparedStatementInvocationHandler.SQL" level="DEBUG"&gt;
 *   &lt;appender-ref ref="SQL-FILE"/&gt;
 * &lt;/logger&gt;
 * &lt;logger name="com.aranya.kayacms.db.CallableStatementInvocationHandler.SQL" level="DEBUG"&gt;
 *   &lt;appender-ref ref="SQL-FILE"/&gt;
 * &lt;/logger&gt;
 * &lt;logger name="com.aranya.kayacms.db.StatementInvocationHandler.SQL" level="DEBUG"&gt;
 *   &lt;appender-ref ref="SQL-FILE"/&gt;
 * &lt;/logger&gt;
 * &lt;logger name="SQL-FILE" level="NONE" additivity="true"&gt;
 *   &lt;appender-ref ref="SQL-FILE"/&gt;
 * &lt;/logger&gt;
 * </pre>
 *
 * @author Aaron Bono
 */
@ToString
@EqualsAndHashCode
@Slf4j
public class PreparedStatementInvocationHandler implements InvocationHandler {

  private static final int ROW_FETCH_SIZE = 100;

  public static final String SQL_DATE_FORMAT = "yyyyMMdd";

  public static final String SQL_TIMESTAMP_FORMAT = "yyyyMMdd HH:mm:ss";

  public static final String SQL_TIME_FORMAT = "HH:mm:ss";

  private static final SimpleDateFormat dateFormat =
      new SimpleDateFormat(SQL_DATE_FORMAT, Locale.getDefault());

  private static final SimpleDateFormat timestampFormat =
      new SimpleDateFormat(SQL_TIMESTAMP_FORMAT, Locale.getDefault());

  private static final SimpleDateFormat timeFormat =
      new SimpleDateFormat(SQL_TIME_FORMAT, Locale.getDefault());

  /** This logger is for logging the SQL statements executed against the DB. */
  private final Logger sqlLogger = LoggerFactory.getLogger(getClass().getCanonicalName() + ".SQL");

  private final PreparedStatement stmt;

  private final String sql;

  private final String[] sqlPieces;

  private final Object[] values;

  static PreparedStatement wrapPreparedStatement(String sql, PreparedStatement stmt)
      throws SQLException {
    stmt.setFetchSize(ROW_FETCH_SIZE);
    PreparedStatementInvocationHandler handler = new PreparedStatementInvocationHandler(sql, stmt);
    return (PreparedStatement)
        Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(),
            new Class[] {PreparedStatement.class},
            handler);
  }

  PreparedStatementInvocationHandler(String sql, PreparedStatement statement) {
    this.sql = sql;
    this.stmt = statement;

    List<String> pieces = new ArrayList<>();
    StringTokenizer tok = new StringTokenizer(sql, "?");
    while (tok.hasMoreTokens()) {
      pieces.add(tok.nextToken());
    }
    if (sql.endsWith("?")) {
      pieces.add("");
    }
    sqlPieces = pieces.toArray(new String[0]);
    values = new Object[sqlPieces.length - 1];
  }

  String valueToString(Object value) {
    if (value == null) {
      return "NULL";
    } else if (value instanceof java.sql.Date) {
      synchronized (dateFormat) {
        return "'" + dateFormat.format((java.util.Date) value) + "'";
      }
    } else if (value instanceof java.sql.Timestamp) {
      synchronized (timestampFormat) {
        return "'" + timestampFormat.format((java.util.Date) value) + "'";
      }
    } else if (value instanceof java.sql.Time) {
      synchronized (timeFormat) {
        return "'" + timeFormat.format((java.util.Date) value) + "'";
      }
    } else if (value instanceof String) {
      return "'" + replaceSpecialChars((String) value) + "'";
    } else {
      return value.toString();
    }
  }

  private String replaceSpecialChars(String s) {
    StringBuilder buf = new StringBuilder(s);
    replaceString(buf, "'", "''");
    return buf.toString();
  }

  private void replaceString(StringBuilder buf, String from, String to) {
    int index = buf.toString().indexOf(from);
    while (index >= 0) {
      buf.replace(index, index + from.length(), to);
      index = buf.indexOf(from, index + to.length());
    }
  }

  private String prettyFormat(String str) {
    return str.trim().replaceAll("\\n", "\n\t");
  }

  private Object processSetter(Reader reader, String methodName, Object... args) {
    Object value = args[1];
    try {
      String str = IOUtils.toString(reader);
      if (args.length > 2) {
        if (args[2].getClass().isAssignableFrom(Long.class)) {
          value = str.substring(0, (int) ((long) args[2]));
        } else {
          value = str.substring(0, (int) args[2]);
        }
      } else {
        value = str;
      }
      args[1] = new StringReader(str);
    } catch (IOException e) {
      log.error("Error reading value for method {} with arguments {}", methodName, args, e);
    }
    return value;
  }

  private Object processSetter(InputStream in, String methodName, Object... args) {
    Object value = args[1];
    try {
      byte[] bytes;
      if (args.length > 2) {
        if (args[2].getClass().isAssignableFrom(Long.class)) {
          bytes = new byte[(int) ((long) args[2])];
        } else {
          bytes = new byte[(int) args[2]];
        }
        IOUtils.read(in, bytes);
      } else {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        IOUtils.copy(in, byteOut);
        bytes = byteOut.toByteArray();
      }
      value = new String(bytes, StandardCharsets.UTF_8);
      args[1] = new ByteArrayInputStream(bytes);
    } catch (IOException e) {
      log.error("Error reading value for method {} with arguments {}", methodName, args, e);
    }
    return value;
  }

  private void processSetter(String methodName, Object... args) {
    Object value = args[1];
    if (value instanceof Reader) {
      value = processSetter((Reader) value, methodName, args);
    } else if (value instanceof InputStream) {
      value = processSetter((InputStream) value, methodName, args);
    } else if (value instanceof byte[]) {
      value = new String((byte[]) args[1], Charset.defaultCharset());
    }
    values[((Number) args[0]).intValue() - 1] = value;
  }

  private void checkIfSetterAndProcess(Method method, Object... args) {
    String methodName = method.getName();
    if (args != null
        && args.length > 0
        && args[0] instanceof Number
        && "setNull".equals(methodName)) {
      values[((Number) args[0]).intValue() - 1] = null;
    } else if (args != null && args.length >= 2 && methodName.startsWith("set")) {
      processSetter(methodName, args);
    }
  }

  void logQuery(Method method, Object... args) {
    // Do not bother logging if debug is not enabled or if this is a PING request.
    if (sqlLogger.isDebugEnabled() && !StringUtils.equalsIgnoreCase("select 1 from dual", sql)) {
      String executedSQL = getExecutedSQL(method, args);
      if (StringUtils.isNotBlank(executedSQL)) {
        String sqlWithValues = getSQLWithValues(method, args, executedSQL);
        if (args == null || args.length == 0) {
          executedSQL = sql;
        } else {
          executedSQL = (String) args[0];
        }
        if (executedSQL != null) {
          executedSQL = prettyFormat(executedSQL);
        }
        sqlLogger.debug("Executing SQL:\n\t{}\n\n\t{}", executedSQL, sqlWithValues);
      }
    }
  }

  private boolean shouldLog(String methodName) {
    return "execute".equals(methodName)
        || "executeQuery".equals(methodName)
        || "executeUpdate".equals(methodName)
        || "executeLargeUpdate".equals(methodName)
        || "addBatch".equals(methodName);
  }

  String getExecutedSQL(Method method, Object... args) {
    String executedSQL = null;
    if (shouldLog(method.getName())) {
      if (args == null || args.length == 0) {
        executedSQL = sql;
      } else {
        executedSQL = (String) args[0];
      }
      if (executedSQL != null) {
        executedSQL = prettyFormat(executedSQL);
      }
    }
    return executedSQL;
  }

  String getSQLWithValues() {
    StringBuilder buf = new StringBuilder();

    int i;
    for (i = 0; i < (sqlPieces.length - 1); ++i) {
      buf.append(sqlPieces[i]);
      buf.append(valueToString(values[i]));
    }
    buf.append(sqlPieces[i]);

    return buf.toString();
  }

  String getSQLWithValues(Method method, Object[] args, String executedSQL) {
    String sqlWithValues = null;
    if (StringUtils.isNotBlank(executedSQL) && shouldLog(method.getName())) {
      if (args == null || args.length == 0) {
        sqlWithValues = getSQLWithValues();
      } else {
        sqlWithValues = executedSQL;
      }
      if (sqlWithValues != null) {
        sqlWithValues = prettyFormat(sqlWithValues);
      }
    }
    return sqlWithValues;
  }

  private Throwable handleInvocationTargetException(
      InvocationTargetException e, Method method, Object... args) {
    Throwable t = e.getTargetException();
    if (t instanceof SQLException && !(t instanceof DetailedSQLException)) {
      String executedSQL = getExecutedSQL(method, args);
      String sqlWithValues = getSQLWithValues(method, args, executedSQL);
      t = new DetailedSQLException((SQLException) t, executedSQL, sqlWithValues);
    }
    if (log.isWarnEnabled()) {
      log.warn("Exception thrown in CallableStatement:", t);
    }
    return t;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    log.debug("Calling prepared statement method: {}", method.getName());
    try {
      checkIfSetterAndProcess(method, args);

      logQuery(method, args);

      return method.invoke(stmt, args);
    } catch (InvocationTargetException e) {
      throw handleInvocationTargetException(e, method, args);
    }
  }
}
