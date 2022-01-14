package com.aranya.kayacms.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * This class is a version of the {@link SingleConnectionDataSource} class. Here we want to
 * add/preserve the following functionality:
 *
 * <ul>
 *   <li>Persist a single DB connection
 *   <li>NOT allow the closing of the connection until Spring disposes of the bean (note this means
 *       any use of this class should be as a Spring bean so we know to close the connection when
 *       the app shuts down)
 *   <li>If the connection is severed, reconnect the next time a connection is requested
 * </ul>
 */
@ToString
@Slf4j
public class SchemaDeploymentManagedDataSource extends SingleConnectionDataSource {

  /** Object to provide locking to help synchronize threads */
  private final Object locker = new Object();

  private final String checkSQLStatement;

  private Connection rawConnection;

  public SchemaDeploymentManagedDataSource(
      String url, String username, String password, String checkSQLStatement) {
    super(url, username, password, true);
    this.checkSQLStatement = checkSQLStatement;
  }

  @Override
  public Connection getConnection() throws SQLException {
    synchronized (locker) {
      try {
        pingDB();

        log.debug("Getting connection");

        Connection connection = super.getConnection();

        log.debug("Got connection {}", connection);

        return connection;
      } catch (SQLException e) {
        log.error("Error getting connection!", e);
        throw new SQLException(e);
      }
    }
  }

  public Connection getRawConnection() {
    return rawConnection;
  }

  @Override
  public void initConnection() throws SQLException {
    synchronized (locker) {
      log.info("Initializing DB connection");
      super.initConnection();
    }
  }

  @Override
  protected Connection getCloseSuppressingConnectionProxy(Connection target) {
    this.rawConnection = target;
    Connection closeSuppressConnection = super.getCloseSuppressingConnectionProxy(target);
    return ConnectionInvocationHandler.wrapConnection(closeSuppressConnection);
  }

  /**
   * Check to see if the database connection is still working. If it is, return without doing
   * anything else. If it is not, reset the connection and do all the things necessary to prep that
   * new connection for use, then return successfully.
   *
   * @throws SQLException In the event the ping fails AND a new connection cannot be reestablished
   *     successfully, this exception will be thrown.
   */
  private void pingDB() throws SQLException {
    try {
      log.debug("Pinging DB");
      try (PreparedStatement stmt = super.getConnection().prepareStatement(checkSQLStatement)) {
        stmt.execute();
        log.debug("Ping clompleted successfully");
      }
    } catch (SQLException e) {
      // If something goes wrong when "pinging" the database with a simple query, reset the
      // connection to force a new connection to be served up the next time it is needed.
      log.error("Error pinging DB, resetting connection", e);
      resetConnection();
    }
  }
}
