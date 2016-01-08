<%@ page contentType="text/html; charset=gbk"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.sql.*"%>
<%@ page import="et.bo.sys.common.SysStaticParameter" %>
<%@ page import="org.apache.commons.dbcp.BasicDataSource"%>
<%@ page import="excellence.framework.base.container.SpringRunningContainer"%>
<%
	Map infoMap = (Map) request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
	String userId = (String) infoMap.get("userId");
				
	String form = request.getParameter("form");
	if(form == null){
		form = "";
	}
	
	String opertype = request.getParameter("opertype");
	if(opertype == null){
		opertype = "";
	}
	
	String path = request.getServletPath();
	String stateName = "";
	if(path.indexOf("inquiryReport.jsp") != -1){
		stateName = "reportState";
	}else{
		stateName = "state";
	}
	
	String state = (String) ((excellence.framework.base.dto.IBaseDTO) request.getAttribute(form)).get(stateName);
	//得到提交的人的座席工号，如果是提交人，和非提交人看到的内容不一样
	String dealCaseId = "";
	if(request.getAttribute("caseid")!=null){
		dealCaseId = (String)request.getAttribute("caseid");
	}
	
	
	//System.out.println(dealCaseId+" 原来的userid是 "+userId);
	
	if(state == null || state.equals("")){
		if(form.equals("focusPursueBean") || form.equals("markanainfoBean")){//如果是焦点追踪或市场分析
			state = "初稿";
		}else{
			state = "原始";
		}
	}

	out.println("<select name=\""+ stateName +"\" id=\""+ stateName +"\" class=\"selectStyle\" style=\"width:120px\">");
	if(form.equals("focusPursueBean") || form.equals("markanainfoBean")){//如果是焦点追踪或市场分析
		if(dealCaseId.equals(userId)&&!isAdmin(userId, form, state)){
			if(opertype.equals("update")){
				if(state.equals("初稿"))
					out.println("<option>一审</option>");
				if(state.equals("一审") || state.equals("二审驳回")){
					out.println("<option>一审驳回</option>");
					out.println("<option>二审</option>");
				}
				if(state.equals("二审")){
					out.println("<option>二审驳回</option>");
					out.println("<option>发布</option>");
				}
				if(state.equals("一审驳回")){
					out.println("<option>一审</option>");
				}
			}
		}
	}else{
		//如果受理座席和现在登陆的座席是一个人的话，可以向下做处理
		//if(dealCaseId.equals(userId)){
		if(dealCaseId.equals(userId)&&!isAdmin(userId, form, state)){
			//如果状态是修改，则列表显示的不同
			if(opertype.equals("update")){
				if(state.equals("原始"))
					out.println("<option>待审</option>");
				if(state.equals("待审")){
					out.println("<option>已审</option>");	
					out.println("<option>驳回</option>");
				}
				if(state.equals("已审"))
					out.println("<option>发布</option>");
				if(state.equals("驳回"))
					out.println("<option>待审</option>");	
			}else{
				out.println("<option>"+ state +"</option>");
			}

		}
		//如果受理座席和现在登陆的座席不是同一个人，只能看到状态
		if(!dealCaseId.equals(userId)){
			out.println("<option>"+ state +"</option>");
		}
	}
	
	//out.println("<option>"+ state +"</option>");
	
	//如果不是焦点追踪或市场分析并且是原始状态并且是修改操作
	//if(!form.equals("focusPursueBean")&&!form.equals("markanainfoBean")&&opertype.equals("update")&&state.equals("原始"))
		//out.println("<option>待审</option>");
		
	//if(!form.equals("focusPursueBean")&&!form.equals("markanainfoBean")&&opertype.equals("update")&&state.equals("驳回"))
		//out.println("<option>待审</option>");
	
	
	if(isAdmin(userId, form, state)){	//如果是管理员
	
		if(form.equals("focusPursueBean") || form.equals("markanainfoBean")){//如果是焦点追踪或市场分析
			
			if(state.equals("初稿") || state.equals("一审驳回")){
				out.println("<option>一审</option>");
				
			}else if(state.equals("一审") || state.equals("二审驳回")){
				out.println("<option>一审驳回</option>");
				out.println("<option>二审</option>");
				
			}else if(state.equals("二审") ){//|| state.equals("三审驳回")
				out.println("<option>二审驳回</option>");
				//out.println("<option>三审</option>");
				out.println("<option>发布</option>");
			}
			/*
			else if(state.equals("三审")){
				out.println("<option>三审驳回</option>");
				out.println("<option>发布</option>");
				
			}
			*/
			else if(state.equals("发布")){
				//out.println("<option>三审</option>");
				out.println("<option>二审</option>");
			}
		}else{
		
			if(state.equals("原始")){
				if(!opertype.equals("update"))
					out.println("<option>待审</option>");
				
				out.println("<option>已审</option>");
				out.println("<option>发布</option>");				
			}else if(state.equals("待审")){
				out.println("<option>驳回</option>");
				out.println("<option>已审</option>");				
			}else if(state.equals("驳回")){
				out.println("<option>待审</option>");			
			}else if(state.equals("已审")){
				out.println("<option>发布</option>");				
			}else if(state.equals("发布")){
				out.println("<option>已审</option>");
			}
			
		}
	}

	out.println("</select>");
%>
<%!
	public boolean isAdmin(String userId, String form, String state){
	
		boolean b = false;
		BasicDataSource bds = (BasicDataSource) SpringRunningContainer.getInstance().getBean("datasource");
			
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("select * from sys_user where user_id = '"+ userId +"'");
			if(rs.next()){
				String auditing = rs.getString("auditing");//System.out.println("auditing=["+auditing+"]");
				String group_id = rs.getString("group_id");
				String type = form2type(form, state);
				if( (auditing != null && auditing.indexOf(type) != -1) || (group_id != null && group_id.equals("administrator")) ){
					b = true;
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
		return b;
	}
	
	public String form2type(String form, String state){//System.out.println("form=["+form+"]");System.out.println("state=["+state+"]");
		String type = "";//有多少权限此处就应该有多少判断
		//金典案例
		if(form.equals("generalCaseinfoBean")){
			type = "普通案例库";
		}else if(form.equals("focusCaseinfoBean")){
			type = "焦点案例库";
		}else if(form.equals("hzinfoBean")){
			type = "会诊案例库";
		}else if(form.equals("effectCaseinfoBean")){
			type = "效果案例库";
		}
		/**/
		//供求价格
		else if(form.equals("asdBean")){
			type = "农产品供求库";
		}else if(form.equals("priceinfoBean")){
			type = "农产品价格库";
		}
		//金农产品 焦点追踪库
		else if(form.equals("focusPursueBean")){
			if(state.equals("初稿") || state.equals("一审驳回")){
				type = "";//焦点追踪库一审						
			}else if(state.equals("一审") || state.equals("二审驳回")){
				type = "焦点追踪库一审";				
			}else if(state.equals("二审") ){//|| state.equals("发布")
				type = "焦点追踪库二审";
			}else if(state.equals("发布")){
				type = "焦点追踪库三审";
			}
		}
		/* */ 		
		//金农产品 市场分析库
		else if(form.equals("markanainfoBean")){			
			if(state.equals("初稿") || state.equals("一审驳回")){
				type = "";						
			}else if(state.equals("一审") || state.equals("二审驳回")){
				type = "市场分析库一审";				
			}else if(state.equals("二审") ){//|| state.equals("发布")
				type = "市场分析库二审";
			}else if(state.equals("发布")){
				type = "市场分析库三审";
			}
		}		
  		//企业服务
		else if(form.equals("operCorpinfoBean")){
			type = "企业信息库";
		}		
  		//医疗服务
		else if(form.equals("medicinfoBean")){
			type = "普通医疗服务信息库";
		}else if(form.equals("BookMedicinfoBean")){
			type = "预约医疗服务信息库";
		}
		//专题调查
		else if(form.equals("inquiryForm")){
			type = "调查问卷设计库";
		}else if(form.equals("inquiryForm")){
			type = "调查信息分析库";
		}
		
		return type;
	}
%>