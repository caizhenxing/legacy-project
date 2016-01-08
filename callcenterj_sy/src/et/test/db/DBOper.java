package et.test.db;

import java.sql.*;

/**
 * 程序编写：张  锋
 * 程序目的：连接数据库并执行相应的操作
 *
 */
public class DBOper {
  private Connection con = null; //
  private Statement st = null; //连接数据库的变量
  private ResultSet rs = null; //

  public DBOper() {
  }

  /**目的：使executeQuery方法可以返回执行记录的个数
   * @作者：张锋
   */
  public int executeQuery(String sql) {
    int count = 0;
    try {
      con = DBConnection.getconnection();
      st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                               ResultSet.CONCUR_READ_ONLY);
      //此处只能用rs进行取值，用executeupdate不能对select进行操作
      rs = st.executeQuery(sql);
      while (rs.next()) {
        count += 1;
        System.out.println("" + rs.getString(1));
      }
      if (con != null) {
        con.close();
      }
    }
    catch (SQLException ex) {
      System.err.println("sqlexception:int executequery()" + ex.getMessage());
    }
    return count;
  }

  /**目的：执行传过来的sql语句并返回结果集
   * @作者：张锋
   */
  public ResultSet executeUpdate(String sql) {
    try {
      con = DBConnection.getconnection();
      st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                               ResultSet.CONCUR_READ_ONLY);
      rs = st.executeQuery(sql);
    }
    catch (Exception ex) {
      System.err.println("exception:resultset executequery" + ex.getMessage());
    }
    return rs;
  }

  /**目的：用在登陆时对sql是否执行进行判断，是否能够正确登陆
   * @作者：张锋
   */
  public boolean execute(String sql) {
    boolean flag = false;
    try {
      con = DBConnection.getconnection();
      st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                               ResultSet.CONCUR_READ_ONLY);
      if (st.execute(sql)) {
        flag = true;
      }
      else {
        flag = false;
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return flag;
  }

  public void executecommon(String sql) {
    try {
      con = DBConnection.getconnection();
      st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                               ResultSet.CONCUR_READ_ONLY);
      st.execute(sql);
      System.out.println("语句插入成功！");
    }
    catch (Exception ex) {
      System.err.println("error message void executecommon"+ex.getMessage());
    }
  }

  public int executeint(String sql){
    int i = 0;
    try {
      con = DBConnection.getconnection();
      st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                               ResultSet.CONCUR_READ_ONLY);
      i = st.executeUpdate(sql);
    }
    catch (Exception ex) {
      System.err.println("error int executeint"+ex.getMessage());
    }
    return i;
  }

}
