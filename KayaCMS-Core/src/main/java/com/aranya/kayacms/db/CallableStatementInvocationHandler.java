package com.aranya.kayacms.db;

import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * This object is simply an extension of the {@link PreparedStatementInvocationHandler}. For
 * details, see that class's JavaDocs.
 *
 * @author Aaron Bono
 */
public class CallableStatementInvocationHandler extends PreparedStatementInvocationHandler {

  private static final int ROW_FETCH_SIZE = 100;

  static CallableStatement wrapCallableStatement(String sql, CallableStatement stmt)
      throws SQLException {
    stmt.setFetchSize(ROW_FETCH_SIZE);
    CallableStatementInvocationHandler handler = new CallableStatementInvocationHandler(sql, stmt);
    return (CallableStatement)
        Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(),
            new Class[] {CallableStatement.class},
            handler);
  }

  CallableStatementInvocationHandler(String sql, CallableStatement statement) {
    super(sql, statement);
  }
}
