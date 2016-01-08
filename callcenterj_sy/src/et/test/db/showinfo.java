package et.test.db;

/**
 * 程序编写：张  锋
 * 程序目的：动态的打印信息并将其显示在指定的页面之中,并且实现分页的功能
 */
import java.sql.ResultSet;

import javax.servlet.jsp.JspWriter;

public class showinfo {
  /**
   * sql是要执行的sql语句
   * pagecount是一页显示的记录的个数
   * pagenum是要显示第几页
   * k是要显示的字段
   * nowpage是当前要显示的页数
   * totalpage是一共能够显示的页数
   */
  private String sql;
  private int pagecount;
  private int pagenum;
  private String k; //没有用
  public int nowpage;
  public int totalpage;
  public showinfo() {
  }

  public String getK() {
    return k;
  }

  public int getNowpage() {
    return nowpage;
  }

  public int getPagecount() {
    return pagecount;
  }

  public int getPagenum() {
    return pagenum;
  }

  public String getSql() {
    return sql;
  }

  public int getTotalpage() {
    return totalpage;
  }

  public void setK(String k) {
    this.k = k;
  }

  public void setNowpage(int nowpage) {
    this.nowpage = nowpage;
  }

  public void setPagecount(int pagecount) {
    this.pagecount = pagecount;
  }

  public void setPagenum(int pagenum) {
    this.pagenum = pagenum;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public void setTotalpage(int totalpage) {
    this.totalpage = totalpage;
  }

  //实现动态打印的方法
  public void print(JspWriter out) {
    DBOper db = new DBOper();
    //计算记录的总个数
    int total = db.executeQuery(sql);
    //判断总的页数
    int i = 1;
    if ( (total % pagecount) != 0) {
      i = total / pagecount + 1;
    }
    else {
      i = total / pagecount;
    }
    totalpage = i; //将总的页数返回
    //做判断判断起始页数是小于1或是大于最大的页数，并进行处理
    if (pagenum < 1) {
      pagenum = 1;
    }
    else if (pagenum > i) {
      pagenum = i;
    }
    nowpage = pagenum; //用来记录当前的页数并返回
    /**
     * 记录开始的位置
     */
    int start = (pagenum - 1) * pagecount + 1;
    /**
     * 做判断，有记录就显示，没有就打印一段信息
     */
    if (total == 0) {
      try {
        out.println("<div align=\"center\">");
        out.println("没有记录!");
        out.println("</div>");
      }
      catch (Exception ex) {
        System.err.println("输出时遇到错误！............." + ex.getMessage());
      }
    }
    else {
      try {
        ResultSet tmprs = db.executeUpdate("select * from gbook");
        /**
         * 将游标定位在指定的位置，因为数据库和其中的记录相差1，所以，
         * 须将记录再向上移一下。
         */
        tmprs.absolute( (int) start);
        tmprs.previous();
        int p = 0;
        while (tmprs.next()) {
          out.println("<table>");
          out.println("<tr>");
          out.println("<td>");
          out.println("<img src=\"../images/spacer.gif\">");
          out.println("<hr width=\"560\" color=\"#99FF99\">");
          out.println("</td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>");
          out.println("<img src=\"../images/friend.gif\" alt=\"姓名\">");
          out.println(tmprs.getString("name"));
          out.println("<img src=\"../images/arrow.gif\" alt=\"性别\">");
          String ptr = tmprs.getString("gender");
          out.println("[" + (ptr.equals("0") ? "男" : "女") + "]");
          out.println("</td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>");
          out.println("<img src=\"../images/IE.gif\" alt=\"邮件地址\">");
          out.println(tmprs.getString("email"));
          out.println("</td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>");
          out.println("<img src=\"../images/logon.gif\" alt=\"留言主题\">");
          out.println(tmprs.getString("title"));
          out.println("</td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>");
          out.println("<img src=\"../images/new.gif\" alt=\"留言内容\">");
          out.println(tmprs.getString("content"));
          out.println("</td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>");
          out.println("<img src=\"../images/ip.gif\" alt=\"留言时间\">");
          out.println("[" + tmprs.getString("gtime") + "]");
          out.println("</td>");
          out.println("</tr>");
          out.println("</table>");
          p++;
          if (p == pagecount) {
            break;
          }
        }
      }
      catch (Exception ex) {
        System.err.println("输出页面的内容时遇到错误！..................." + ex.getMessage());
      }
    }
  }
}
