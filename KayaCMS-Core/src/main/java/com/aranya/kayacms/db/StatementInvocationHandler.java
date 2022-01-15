package com.aranya.kayacms.db;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This object is responsible for capturing all calls to build, populate and execute a Statement. It
 * will delegate those calls to the statement it proxies for and will also log all SQL / DDL
 * executed against the database.
 *
 * <p>Another feature of this class is that it wraps all SQLExceptions thrown with a
 * DetailedSQLException which also contains the raw SQL so, as the exception bubbles up through the
 * application, the SQL causing the problem is not lost.
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
 * &lt;log name="com.aranya.kayacms.db.PreparedStatementInvocationHandler.SQL" level="DEBUG"&gt;
 *   &lt;appender-ref ref="SQL-FILE"/&gt;
 * &lt;/log&gt;
 * &lt;log name="com.aranya.kayacms.db.CallableStatementInvocationHandler.SQL" level="DEBUG"&gt;
 *   &lt;appender-ref ref="SQL-FILE"/&gt;
 * &lt;/log&gt;
 * &lt;log name="com.aranya.kayacms.db.StatementInvocationHandler.SQL" level="DEBUG"&gt;
 *   &lt;appender-ref ref="SQL-FILE"/&gt;
 * &lt;/log&gt;
 * &lt;log name="SQL-FILE" level="NONE" additivity="true"&gt;
 *   &lt;appender-ref ref="SQL-FILE"/&gt;
 * &lt;/log&gt;
 * </pre>
 *
 * @author Aaron Bono
 */
@ToString
@EqualsAndHashCode
@Slf4j
public class StatementInvocationHandler implements InvocationHandler {

  private static final int ROW_FETCH_SIZE = 100;

  /** This log is for logging the SQL statements executed against the DB. */
  private final Logger sqlLogger = LoggerFactory.getLogger(getClass().getCanonicalName() + ".SQL");

  private final Statement stmt;

  static Statement wrapStatement(Statement stmt) throws SQLException {
    stmt.setFetchSize(ROW_FETCH_SIZE);
    StatementInvocationHandler handler = new StatementInvocationHandler(stmt);
    return (Statement)
        Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(), new Class[] {Statement.class}, handler);
  }

  private StatementInvocationHandler(Statement statement) {
    this.stmt = statement;
  }

  private String prettyFormat(String str) {
    return str.trim().replaceAll("\\n", "\n\t");
  }

  private void logSQL(String sql) {
    if (sqlLogger.isDebugEnabled()) {
      sqlLogger.debug("Executing SQL:\n\t{}", sql);
    }
  }

  private Throwable handleInvocationTargetException(InvocationTargetException e, String sql) {
    Throwable t = e.getTargetException();
    if (t instanceof SQLException && !(t instanceof DetailedSQLException)) {
      t = new DetailedSQLException((SQLException) t, sql);
    }
    if (log.isWarnEnabled()) {
      log.warn("Exception thrown in Statement:", t);
    }
    return t;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    String methodName = method.getName();
    String sql = null;
    if (log.isDebugEnabled()) {
      log.debug("Invoking statement method: {}", method.getName());
    }
    try {
      if ("execute".equals(methodName)
          || "executeQuery".equals(methodName)
          || "executeUpdate".equals(methodName)
          || "executeLargeUpdate".equals(methodName)
          || "addBatch".equals(methodName)) {
        sql = prettyFormat((String) args[0]);
        logSQL(sql);
      }
      return method.invoke(stmt, args);
    } catch (InvocationTargetException e) {
      throw handleInvocationTargetException(e, sql);
    }
  }
}
