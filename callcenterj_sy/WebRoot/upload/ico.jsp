<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.apache.commons.dbcp.BasicDataSource"%>
<%@ page import="org.springframework.web.struts.ContextLoaderPlugIn"%>

<%	
	String scheme = request.getScheme();				//取协议，http
	String serverName = request.getServerName();
	String serverPort = String.valueOf(request.getServerPort());
	String contextPath = request.getContextPath();
	String basePath = "";
	if(serverPort.equals("80")){
		basePath = scheme + "://"+ serverName + contextPath +"/";
	}else{
		basePath = scheme + "://"+ serverName +":"+ serverPort + contextPath +"/";
	}

	ApplicationContext ac = (ApplicationContext) application.getAttribute(ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
	BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");
	
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	String t = request.getParameter("t");//取表名加主键字段名的合成串，如 table.id
	String table = t.substring(0, t.indexOf("."));
	String field = t.substring(t.indexOf(".")+1, t.length());
	String id = request.getParameter("id");

	if(table !=null && !table.equals("") && id !=null && !id.equals("")){
		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("select uploadfile from "+ table +" where "+ field +" = '" + id + "'");
			if(rs.next()){
				String uploadfile = rs.getString(1);
				if(uploadfile == null || uploadfile.equals("")){
					out.print(basePath + "upload/zw.jpg");
					return;//如果空的程序到此就中断
				}
				String[] imgs = uploadfile.split(",");
				String[] name = new String[2];//name[0]是原文件名,name[1]是新文件名

				for(int i = imgs.length-1; i >= 0; i--){
					name = imgs[i].split(":");
					if(name[1].endsWith("jpg") 
					|| name[1].endsWith("bmp") 
					|| name[1].endsWith("gif")
					|| name[1].endsWith("png") ){
						String src = basePath + "UploadFile/" + name[1];
						out.print(src);
						return;
					}
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
	out.print(basePath + "upload/zw.jpg");
%>