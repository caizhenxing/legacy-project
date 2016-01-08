<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.apache.commons.dbcp.BasicDataSource"%>
<%@ page import="org.springframework.web.struts.ContextLoaderPlugIn"%>

<%
	ApplicationContext ac = (ApplicationContext) application.getAttribute(ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
	BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");
	
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	String t = request.getParameter("t");//ȡ�����������ֶ����ĺϳɴ����� table.id
	String table = t.substring(0, t.indexOf("."));
	String field = t.substring(t.indexOf(".")+1, t.length());
	String id = request.getParameter("id");
	String index = request.getParameter("i");

	if(table !=null && !table.equals("") && id !=null && !id.equals("") && index !=null && !index.equals("")){
		int j = Integer.valueOf( index ).intValue();
		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("select cust_banner from "+ table +" where "+ field +" = '" + id + "'");
			if(rs.next()){
				String uploadfile = rs.getString(1);
				String[] imgs = uploadfile.split(",");
				String newUploadFile = "";
				for(int i = 0; i<imgs.length; i++){
					if(i == j){										//�����Ҫɾ�����ļ�����ɾ������
						String[] name = imgs[i].split(":");
						java.io.File file = new java.io.File(request.getRealPath("/UploadFile/"+name[1]));
						if(file.exists()){
							file.delete();	//ɾ���ļ�
							stmt.executeUpdate("update "+table+" set cust_banner = '' where cust_id = '"+id+"'");
						}
					}else{
						newUploadFile += imgs[i] + ",";				//�������ɾ�����ļ�������ϳ��ַ���
					}
				}
				if(!newUploadFile.equals("")){
					newUploadFile = newUploadFile.substring(0, newUploadFile.length()-1);//ȥ�����һ�����ţ���ʱ�Ĵ�Ӧ����ɾ���ļ���Ĵ���
				}
				int i = stmt.executeUpdate("update "+ table +" set cust_banner = '"+ newUploadFile +"' where "+ field +" = '" + id + "'");
				if(i > 0){
					out.print("<script>");
					out.print("alert('ɾ���ɹ���');");
				    out.print("window.location.href='"+ request.getHeader("Referer") +"';");
				    out.print("</script>");
				}else{
					out.print("<script>");
					out.print("alert('ɾ��ʧ�������ԣ�����ϵ����Ա��');");
				    out.print("window.location.href='"+ request.getHeader("Referer") +"';");
				    out.print("</script>");
				}
			}

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
%>