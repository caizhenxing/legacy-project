package et.test.db;

/**
 * �����д����  ��
 * ����Ŀ�ģ���̬�Ĵ�ӡ��Ϣ��������ʾ��ָ����ҳ��֮��,����ʵ�ַ�ҳ�Ĺ���
 */
import java.sql.ResultSet;

import javax.servlet.jsp.JspWriter;

public class showinfo {
  /**
   * sql��Ҫִ�е�sql���
   * pagecount��һҳ��ʾ�ļ�¼�ĸ���
   * pagenum��Ҫ��ʾ�ڼ�ҳ
   * k��Ҫ��ʾ���ֶ�
   * nowpage�ǵ�ǰҪ��ʾ��ҳ��
   * totalpage��һ���ܹ���ʾ��ҳ��
   */
  private String sql;
  private int pagecount;
  private int pagenum;
  private String k; //û����
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

  //ʵ�ֶ�̬��ӡ�ķ���
  public void print(JspWriter out) {
    DBOper db = new DBOper();
    //�����¼���ܸ���
    int total = db.executeQuery(sql);
    //�ж��ܵ�ҳ��
    int i = 1;
    if ( (total % pagecount) != 0) {
      i = total / pagecount + 1;
    }
    else {
      i = total / pagecount;
    }
    totalpage = i; //���ܵ�ҳ������
    //���ж��ж���ʼҳ����С��1���Ǵ�������ҳ���������д���
    if (pagenum < 1) {
      pagenum = 1;
    }
    else if (pagenum > i) {
      pagenum = i;
    }
    nowpage = pagenum; //������¼��ǰ��ҳ��������
    /**
     * ��¼��ʼ��λ��
     */
    int start = (pagenum - 1) * pagecount + 1;
    /**
     * ���жϣ��м�¼����ʾ��û�оʹ�ӡһ����Ϣ
     */
    if (total == 0) {
      try {
        out.println("<div align=\"center\">");
        out.println("û�м�¼!");
        out.println("</div>");
      }
      catch (Exception ex) {
        System.err.println("���ʱ��������............." + ex.getMessage());
      }
    }
    else {
      try {
        ResultSet tmprs = db.executeUpdate("select * from gbook");
        /**
         * ���α궨λ��ָ����λ�ã���Ϊ���ݿ�����еļ�¼���1�����ԣ�
         * �뽫��¼��������һ�¡�
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
          out.println("<img src=\"../images/friend.gif\" alt=\"����\">");
          out.println(tmprs.getString("name"));
          out.println("<img src=\"../images/arrow.gif\" alt=\"�Ա�\">");
          String ptr = tmprs.getString("gender");
          out.println("[" + (ptr.equals("0") ? "��" : "Ů") + "]");
          out.println("</td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>");
          out.println("<img src=\"../images/IE.gif\" alt=\"�ʼ���ַ\">");
          out.println(tmprs.getString("email"));
          out.println("</td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>");
          out.println("<img src=\"../images/logon.gif\" alt=\"��������\">");
          out.println(tmprs.getString("title"));
          out.println("</td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>");
          out.println("<img src=\"../images/new.gif\" alt=\"��������\">");
          out.println(tmprs.getString("content"));
          out.println("</td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>");
          out.println("<img src=\"../images/ip.gif\" alt=\"����ʱ��\">");
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
        System.err.println("���ҳ�������ʱ��������..................." + ex.getMessage());
      }
    }
  }
}
