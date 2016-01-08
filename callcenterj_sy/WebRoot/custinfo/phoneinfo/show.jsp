<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.apache.commons.dbcp.BasicDataSource"%>
<%@ page import="org.springframework.web.struts.ContextLoaderPlugIn"%>
<%@ include file="../../style.jsp"%>
<link href="../../style/<%=styleLocation%>/style.css" rel="stylesheet" type="text/css" />
<body class="labelStyle">
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
			rs = stmt.executeQuery("select cust_banner from "+ table +" where "+ field +" = '" + id + "'");
			if(rs.next()){
				String uploadfile = rs.getString(1);
				if(uploadfile == null || uploadfile.equals("")){
					return;//如果空的程序到此就中断
				}
				String[] imgs = uploadfile.split(",");
				String[] name = new String[2];//name[0]是原文件名,name[1]是新文件名
				out.println("<table width='100%' class='labelStyle'>");
				for(int i = 0; i<imgs.length; i++){
					name = imgs[i].split(":");
					String src = basePath + "UploadFile/" + name[1];						//默认显示图片
					if(name[1].endsWith("rar") || name[1].endsWith("zip")){			//如果是压缩文件，显示rar图标
						src = basePath + "upload/rar.jpg";
					}else if(name[1].endsWith("doc")){								//显示word图标
						src = basePath + "upload/doc.jpg";
					}else if(name[1].endsWith("xls")){								//显示Excel图标
						src = basePath + "upload/xls.jpg";
					}
					String outStr = "<tr><td><a href=\""+ basePath +"UploadFile/"+name[1]+"\" target=\"_blank\"><img width=\"90\" height=\"77\" src=\""+src+"\" border=\"0\"></a>";
					outStr += "<br>"+name[0]+"[<a href=\""+ basePath +"custinfo/phoneinfo/del.jsp?t="+ t +"&id="+ id +"&i="+ i +"\">删除</a>]</td></tr>";	
					out.println(outStr);//输出
				}

				out.println("</table>");
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
</body>