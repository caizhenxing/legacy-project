package et.test.db;

import java.sql.*;

public class DBConnection {
  public DBConnection() {
  }

  /**Ŀ�ģ��õ������ݿ������
   * @���ߣ��ŷ�
   */
  private static Connection con = null;
  public static Connection getconnection() {
    try {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      con = DriverManager.getConnection("jdbc:odbc:gbook");
    }
    catch (ClassNotFoundException ex) {
      System.err.println("DbConnectin:classnotfound" + ex.getMessage());
    }
    catch (SQLException ex) {
      System.err.println("DbConnection:sqlexception" + ex.getMessage());
    }
    return con;
  }

  /**Ŀ�ģ��ر����ݿ������
   * @���ߣ��ŷ�
   */
  public static void closeconnection() {
    try {
      if (con != null) {
        con.close();
      }
    }
    catch (SQLException ex) {
      System.err.println("DbConnection:closeconnection()" + ex.getMessage());
    }
  }

}
