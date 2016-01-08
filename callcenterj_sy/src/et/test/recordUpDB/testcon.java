package et.test.recordUpDB;

import java.sql.*;

public class testcon {
  public testcon() {
  }

  public static void main(String[] args) {
    try {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      Connection con = DriverManager.getConnection("jdbc:odbc:easytq");
      Connection con1 = DriverManager.getConnection("jdbc:odbc:callcenterj_sy","sa","123456");
      Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                         ResultSet.CONCUR_READ_ONLY);
      Statement st1 = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
              ResultSet.CONCUR_READ_ONLY);
      Statement st2=con1.createStatement();
      Statement st3=con.createStatement();
      System.out.println("需要处理的文件如下：......");
      ResultSet rs = st.executeQuery("SELECT * FROM record where caller<>'' ");
      while (rs.next()) {
    	  String id=rs.getString("id");
    	  String wavpath=rs.getString("filename");
    	  String filename=wavpath.substring(wavpath.indexOf(":")+2);
    	  //String filename="moontest";
    	  String starttime=rs.getString("starttime");
    	  String caller=rs.getString("caller");
        System.out.println("id: "+id+" filename: "+filename+" starttime: "+starttime+" caller: "+caller);
        
       String sql="update cc_talk set record_path='"+filename+"' where CONVERT(varchar,touch_begintime,120)='"+starttime+"' and phone_num='"+caller+"'";
System.out.println("sql: "+sql);
        int i=st2.executeUpdate(sql);
        if(i>0){
        	String sql2="update record set linkMark='N' where id="+id;
        	st3.executeUpdate(sql2);
        }
      }
      
//      ResultSet rs1 = st1.executeQuery("select * from record");
//      while (rs1.next()) {
//        System.out.println("aaaaaaaaaaaaa    "+rs1.getString(1)+"bbb     "+rs1.getString(2));
//      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
