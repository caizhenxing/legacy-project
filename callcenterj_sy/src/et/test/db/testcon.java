package et.test.db;

import java.sql.*;

public class testcon {
  public testcon() {
  }

  public static void main(String[] args) {
    try {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      Connection con = DriverManager.getConnection("jdbc:odbc:record");
      Connection con1 = DriverManager.getConnection("jdbc:odbc:record");
      Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                         ResultSet.CONCUR_READ_ONLY);
      Statement st1 = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_READ_ONLY);
      System.out.println("Ö´ÐÐ......");
      ResultSet rs = st.executeQuery("select * from record");
      while (rs.next()) {
        //rs.absolute(1);
        System.out.println("aaaaaaaaaaaaa    "+rs.getString(1)+"bbb     "+rs.getString(2));
      }
      
      ResultSet rs1 = st1.executeQuery("select * from record");
      while (rs1.next()) {
        //rs.absolute(1);
        System.out.println("aaaaaaaaaaaaa    "+rs1.getString(1)+"bbb     "+rs1.getString(2));
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
