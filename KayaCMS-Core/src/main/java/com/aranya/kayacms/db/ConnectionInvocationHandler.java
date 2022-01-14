package com.aranya.kayacms.db;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.ConnectionProxy;

/**
 * This class is a DB connection wrapper designed to intercept calls to create Statements,
 * PreparedStatements and CallableStatements and wrap them as well. The whole purpose of these
 * wrappers are to:
 *
 * <ol>
 *   <li>Make sure the Connection also implements the Spring ConnectionProxy so the underlying
 *       original DB connection is available
 *   <li>Provide reliable, consistent and complete logging of all SQL and DDL run against the
 *       database
 * </ol>
 *
 * Note that this code assumes that the Connection object we delegate to when proxying calls to the
 * JDBC underlying Connection could be different from the original raw Connection object responsible
 * for talking to the database. This is because this might not be the first Connection wrapper in
 * use - the Connection we are wrapping here might be yet another Connection wrapper. The creator of
 * a ConnectionInvocationHandler can pass in two Connection objects where the first is the
 * connection that this handler proxies all DB calls to but the second Connection argument is what
 * is returned if the getTargetConnection() method is called.
 *
 * @author Aaron Bono
 */
@ToString
@EqualsAndHashCode
@Slf4j
public class ConnectionInvocationHandler implements InvocationHandler {

  private final Connection conn;

  private final Connection targetConn;

  /**
   * This is a convenience method that not only creates the InvocationHandler but also creates the
   * proxy which utilizes that handler.
   *
   * @param conn The connection we are proxying.
   * @return A new connection wrapper.
   */
  static Connection wrapConnection(Connection conn) {
    Connection targetConn = conn;
    if (conn instanceof ConnectionProxy) {
      targetConn = ((ConnectionProxy) conn).getTargetConnection();
    }
    return wrapConnection(conn, targetConn);
  }

  /**
   * This is a convenience method that not only creates the InvocationHandler but also creates the
   * proxy which utilizes that handler.
   *
   * @param conn The connection we are proxying.
   * @param targetConn The underlying connection. This connection object should be the RAW
   *     connection to the database which is returned by the getTargetConnection() method call. For
   *     everything else the conn object is used.
   * @return A new connection wrapper.
   */
  static Connection wrapConnection(Connection conn, Connection targetConn) {
    ConnectionInvocationHandler handler = new ConnectionInvocationHandler(conn, targetConn);
    return (Connection)
        Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(),
            new Class[] {Connection.class, ConnectionProxy.class},
            handler);
  }

  private ConnectionInvocationHandler(Connection connection, Connection targetConn) {
    this.conn = connection;
    this.targetConn = targetConn;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      if (log.isDebugEnabled()) {
        log.debug("Invoking method on Connection: {}", method.getName());
      }
      return invoke(method, args);
    } catch (InvocationTargetException e) {
      Throwable t = e.getTargetException();
      if ((t instanceof SQLException) && (!(t instanceof DetailedSQLException))) {
        t = new DetailedSQLException((SQLException) t, null);
      }
      if (log.isWarnEnabled()) {
        log.warn("{} thrown in Connection {}:", t.getClass().getName(), method.getName(), t);
      }
      throw t;
    }
  }

  /**
   * This method was broken out of the above invoke method in order to simplify the methods so they
   * pass the Cyclomatic complexity checks. The name of this method is probably not the best but a
   * better name could not be devised. Perhaps it can be renamed in the future by someone more
   * creative than the original author :) .
   *
   * <p>If a Statement, PreparedStatement or CallableStatement are being created, we will wrap the
   * object created in our own wrapper object so we can further capture and log calls to the JDBC
   * layer.
   *
   * @param method The method being called.
   * @param args The parameters / arguments passed to the called method.
   * @return The return value to the caller.
   * @throws ReflectiveOperationException Thrown if there is a problem passing the method call on
   *     down to the Connection being wrapped here.
   * @throws SQLException Thrown if there is a problem creating a Statement.
   */
  private Object invoke(Method method, Object... args)
      throws ReflectiveOperationException, SQLException {
    String methodName = method.getName();
    if ("createStatement".equals(methodName)) {
      return StatementInvocationHandler.wrapStatement((Statement) method.invoke(conn, args));
    } else if ("prepareStatement".equals(methodName)) {
      return PreparedStatementInvocationHandler.wrapPreparedStatement(
          (String) args[0], (PreparedStatement) method.invoke(conn, args));
    } else if ("prepareCall".equals(methodName)) {
      return CallableStatementInvocationHandler.wrapCallableStatement(
          (String) args[0], (CallableStatement) method.invoke(conn, args));
    } else if ("getTargetConnection".equals(methodName)) {
      return targetConn;
    } else {
      return method.invoke(conn, args);
    }
  }
}
