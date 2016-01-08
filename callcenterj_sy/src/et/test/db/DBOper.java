package et.test.db;

import java.sql.*;

/**
 * �����д����  ��
 * ����Ŀ�ģ��������ݿⲢִ����Ӧ�Ĳ���
 *
 */
public class DBOper {
  private Connection con = null; //
  private Statement st = null; //�������ݿ�ı���
  private ResultSet rs = null; //

  public DBOper() {
  }

  /**Ŀ�ģ�ʹexecuteQuery�������Է���ִ�м�¼�ĸ���
   * @���ߣ��ŷ�
   */
  public int executeQuery(String sql) {
    int count = 0;
    try {
      con = DBConnection.getconnection();
      st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                               ResultSet.CONCUR_READ_ONLY);
      //�˴�ֻ����rs����ȡֵ����executeupdate���ܶ�select���в���
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

  /**Ŀ�ģ�ִ�д�������sql��䲢���ؽ����
   * @���ߣ��ŷ�
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

  /**Ŀ�ģ����ڵ�½ʱ��sql�Ƿ�ִ�н����жϣ��Ƿ��ܹ���ȷ��½
   * @���ߣ��ŷ�
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
      System.out.println("������ɹ���");
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
