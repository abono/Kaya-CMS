package com.aranya.kayacms.db;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.ConnectionProxy;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.lang.Nullable;

@Slf4j
@ToString(callSuper = true)
public class PooledDelegatingDataSource extends DelegatingDataSource {

  protected final List<Connection> connections = new ArrayList<>(2);

  protected final List<Connection> availableConnections = new ArrayList<>(2);

  /** Synchronization monitor for the shared Connection. */
  private final Object connectionMonitor = new Object();

  public PooledDelegatingDataSource(DataSource targetDataSource) {
    super(targetDataSource);
    log.info("Creating data source {}", this);
  }

  @Override
  public Connection getConnection() throws SQLException {
    synchronized (this.connectionMonitor) {
      if (availableConnections.isEmpty()) {
        initConnection();
      }
      log.info("Getting connection");
      Connection conn = availableConnections.remove(0);
      log.info("Connection Received {}", conn.getClass().getCanonicalName());

      Connection closeSuppressConnection = getCloseSuppressingConnectionProxy(conn);
      return ConnectionInvocationHandler.wrapConnection(closeSuppressConnection);
    }
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    throw new SQLException(
        "DisposableDelegatingDataSource does not support custom username and password");
  }

  private void initConnection() throws SQLException {
    synchronized (this.connectionMonitor) {
      Connection conn = obtainTargetDataSource().getConnection();
      conn.setAutoCommit(false);
      if (log.isDebugEnabled()) {
        log.info("Established JDBC Connection: {}", conn);
      }
      connections.add(conn);
      availableConnections.add(conn);
    }
  }

  /**
   * Wrap the given Connection with a proxy that delegates every method call to it with the
   * exclusion of the close which returns the connection to the pool.
   *
   * @param target the original Connection to wrap
   * @return the wrapped Connection
   */
  protected Connection getCloseSuppressingConnectionProxy(Connection target) {
    return (Connection)
        Proxy.newProxyInstance(
            ConnectionProxy.class.getClassLoader(),
            new Class<?>[] {ConnectionProxy.class},
            new CloseSuppressingInvocationHandler(target));
  }

  private Object unwrap(Connection target, Object proxy, Method method, Object[] args)
      throws ReflectiveOperationException {
    if (((Class<?>) args[0]).isInstance(proxy)) {
      return proxy;
    } else {
      return method.invoke(target, args);
    }
  }

  private Boolean isWrappedFor(Connection target, Object proxy, Method method, Object[] args)
      throws ReflectiveOperationException {
    if (((Class<?>) args[0]).isInstance(proxy)) {
      return Boolean.TRUE;
    } else {
      return (Boolean) method.invoke(target, args);
    }
  }

  @Nullable
  private Object close(Connection target) {
    log.info("Closing connection {}", target);
    availableConnections.add(target);
    return null;
  }

  /** Invocation handler that suppresses close calls on JDBC Connections. */
  private class CloseSuppressingInvocationHandler implements InvocationHandler {

    private Connection target;

    public CloseSuppressingInvocationHandler(Connection target) {
      log.debug("Wrapping connection {}", target);
      this.target = target;
    }

    @Override
    @Nullable
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      // Invocation on ConnectionProxy interface coming in...
      String methodName = method.getName();
      log.debug("Calling {}", methodName);

      try {
        if ("equals".equals(methodName)) {
          // Only consider equal when proxies are identical.
          return (proxy == args[0]);
        } else if ("hashCode".equals(methodName)) {
          // Use hashCode of Connection proxy.
          return System.identityHashCode(proxy);
        } else if ("unwrap".equals(methodName)) {
          return unwrap(target, proxy, method, args);
        } else if ("isWrapperFor".equals(methodName)) {
          return isWrappedFor(target, proxy, method, args);
        } else if ("close".equals(methodName)) {
          close(target);
          target = null;
          return null;
        } else if ("isClosed".equals(methodName)) {
          return target == null;
        } else if ("getTargetConnection".equals(methodName)) {
          // Handle getTargetConnection method: return underlying Connection.
          return target;
        } else {
          // Invoke method on target Connection.
          return method.invoke(target, args);
        }
      } catch (InvocationTargetException ex) {
        throw ex.getTargetException();
      }
    }
  }
}
