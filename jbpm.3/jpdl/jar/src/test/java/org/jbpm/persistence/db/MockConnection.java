package org.jbpm.persistence.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;

public class MockConnection implements Connection {

  boolean wasClosed = false;
  boolean wasRolledBack = false;
  boolean wasCommitted = false;

  public void close() throws SQLException {
    wasClosed = true;
  }

  public boolean isClosed() throws SQLException {
    return wasClosed;
  }

  public void commit() throws SQLException {
    wasCommitted = true;
  }

  public void rollback() throws SQLException {
    wasRolledBack = true;
  }


  public Statement createStatement() throws SQLException {
    throw new UnsupportedOperationException();
  }

  public PreparedStatement prepareStatement(String arg0) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public CallableStatement prepareCall(String arg0) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public String nativeSQL(String arg0) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public void setAutoCommit(boolean arg0) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public boolean getAutoCommit() throws SQLException {
    throw new UnsupportedOperationException();
  }

  public DatabaseMetaData getMetaData() throws SQLException {
    throw new UnsupportedOperationException();
  }

  public void setReadOnly(boolean arg0) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public boolean isReadOnly() throws SQLException {
    throw new UnsupportedOperationException();
  }

  public void setCatalog(String arg0) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public String getCatalog() throws SQLException {
    throw new UnsupportedOperationException();
  }

  public void setTransactionIsolation(int arg0) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public int getTransactionIsolation() throws SQLException {
    throw new UnsupportedOperationException();
  }

  public SQLWarning getWarnings() throws SQLException {
    throw new UnsupportedOperationException();
  }

  public void clearWarnings() throws SQLException {
    throw new UnsupportedOperationException();
  }

  public Statement createStatement(int arg0, int arg1) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public PreparedStatement prepareStatement(String arg0, int arg1, int arg2) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public CallableStatement prepareCall(String arg0, int arg1, int arg2) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public Map getTypeMap() throws SQLException {
    throw new UnsupportedOperationException();
  }

  public void setTypeMap(Map arg0) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public void setHoldability(int arg0) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public int getHoldability() throws SQLException {
    throw new UnsupportedOperationException();
  }

  public Savepoint setSavepoint() throws SQLException {
    throw new UnsupportedOperationException();
  }

  public Savepoint setSavepoint(String arg0) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public void rollback(Savepoint arg0) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public void releaseSavepoint(Savepoint arg0) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
    throw new UnsupportedOperationException();
  }

  public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
    throw new UnsupportedOperationException();
  }
}
