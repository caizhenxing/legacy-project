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
	//�õ��ύ���˵���ϯ���ţ�������ύ�ˣ��ͷ��ύ�˿��������ݲ�һ��
	String dealCaseId = "";
	if(request.getAttribute("caseid")!=null){
		dealCaseId = (String)request.getAttribute("caseid");
	}
	
	
	//System.out.println(dealCaseId+" ԭ����userid�� "+userId);
	
	if(state == null || state.equals("")){
		if(form.equals("focusPursueBean") || form.equals("markanainfoBean")){//����ǽ���׷�ٻ��г�����
			state = "����";
		}else{
			state = "ԭʼ";
		}
	}

	out.println("<select name=\""+ stateName +"\" id=\""+ stateName +"\" class=\"selectStyle\" style=\"width:120px\">");
	if(form.equals("focusPursueBean") || form.equals("markanainfoBean")){//����ǽ���׷�ٻ��г�����
		if(dealCaseId.equals(userId)&&!isAdmin(userId, form, state)){
			if(opertype.equals("update")){
				if(state.equals("����"))
					out.println("<option>һ��</option>");
				if(state.equals("һ��") || state.equals("���󲵻�")){
					out.println("<option>һ�󲵻�</option>");
					out.println("<option>����</option>");
				}
				if(state.equals("����")){
					out.println("<option>���󲵻�</option>");
					out.println("<option>����</option>");
				}
				if(state.equals("һ�󲵻�")){
					out.println("<option>һ��</option>");
				}
			}
		}
	}else{
		//���������ϯ�����ڵ�½����ϯ��һ���˵Ļ�����������������
		//if(dealCaseId.equals(userId)){
		if(dealCaseId.equals(userId)&&!isAdmin(userId, form, state)){
			//���״̬���޸ģ����б���ʾ�Ĳ�ͬ
			if(opertype.equals("update")){
				if(state.equals("ԭʼ"))
					out.println("<option>����</option>");
				if(state.equals("����")){
					out.println("<option>����</option>");	
					out.println("<option>����</option>");
				}
				if(state.equals("����"))
					out.println("<option>����</option>");
				if(state.equals("����"))
					out.println("<option>����</option>");	
			}else{
				out.println("<option>"+ state +"</option>");
			}

		}
		//���������ϯ�����ڵ�½����ϯ����ͬһ���ˣ�ֻ�ܿ���״̬
		if(!dealCaseId.equals(userId)){
			out.println("<option>"+ state +"</option>");
		}
	}
	
	//out.println("<option>"+ state +"</option>");
	
	//������ǽ���׷�ٻ��г�����������ԭʼ״̬�������޸Ĳ���
	//if(!form.equals("focusPursueBean")&&!form.equals("markanainfoBean")&&opertype.equals("update")&&state.equals("ԭʼ"))
		//out.println("<option>����</option>");
		
	//if(!form.equals("focusPursueBean")&&!form.equals("markanainfoBean")&&opertype.equals("update")&&state.equals("����"))
		//out.println("<option>����</option>");
	
	
	if(isAdmin(userId, form, state)){	//����ǹ���Ա
	
		if(form.equals("focusPursueBean") || form.equals("markanainfoBean")){//����ǽ���׷�ٻ��г�����
			
			if(state.equals("����") || state.equals("һ�󲵻�")){
				out.println("<option>һ��</option>");
				
			}else if(state.equals("һ��") || state.equals("���󲵻�")){
				out.println("<option>һ�󲵻�</option>");
				out.println("<option>����</option>");
				
			}else if(state.equals("����") ){//|| state.equals("���󲵻�")
				out.println("<option>���󲵻�</option>");
				//out.println("<option>����</option>");
				out.println("<option>����</option>");
			}
			/*
			else if(state.equals("����")){
				out.println("<option>���󲵻�</option>");
				out.println("<option>����</option>");
				
			}
			*/
			else if(state.equals("����")){
				//out.println("<option>����</option>");
				out.println("<option>����</option>");
			}
		}else{
		
			if(state.equals("ԭʼ")){
				if(!opertype.equals("update"))
					out.println("<option>����</option>");
				
				out.println("<option>����</option>");
				out.println("<option>����</option>");				
			}else if(state.equals("����")){
				out.println("<option>����</option>");
				out.println("<option>����</option>");				
			}else if(state.equals("����")){
				out.println("<option>����</option>");			
			}else if(state.equals("����")){
				out.println("<option>����</option>");				
			}else if(state.equals("����")){
				out.println("<option>����</option>");
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
		String type = "";//�ж���Ȩ�޴˴���Ӧ���ж����ж�
		//��䰸��
		if(form.equals("generalCaseinfoBean")){
			type = "��ͨ������";
		}else if(form.equals("focusCaseinfoBean")){
			type = "���㰸����";
		}else if(form.equals("hzinfoBean")){
			type = "���ﰸ����";
		}else if(form.equals("effectCaseinfoBean")){
			type = "Ч��������";
		}
		/**/
		//����۸�
		else if(form.equals("asdBean")){
			type = "ũ��Ʒ�����";
		}else if(form.equals("priceinfoBean")){
			type = "ũ��Ʒ�۸��";
		}
		//��ũ��Ʒ ����׷�ٿ�
		else if(form.equals("focusPursueBean")){
			if(state.equals("����") || state.equals("һ�󲵻�")){
				type = "";//����׷�ٿ�һ��						
			}else if(state.equals("һ��") || state.equals("���󲵻�")){
				type = "����׷�ٿ�һ��";				
			}else if(state.equals("����") ){//|| state.equals("����")
				type = "����׷�ٿ����";
			}else if(state.equals("����")){
				type = "����׷�ٿ�����";
			}
		}
		/* */ 		
		//��ũ��Ʒ �г�������
		else if(form.equals("markanainfoBean")){			
			if(state.equals("����") || state.equals("һ�󲵻�")){
				type = "";						
			}else if(state.equals("һ��") || state.equals("���󲵻�")){
				type = "�г�������һ��";				
			}else if(state.equals("����") ){//|| state.equals("����")
				type = "�г����������";
			}else if(state.equals("����")){
				type = "�г�����������";
			}
		}		
  		//��ҵ����
		else if(form.equals("operCorpinfoBean")){
			type = "��ҵ��Ϣ��";
		}		
  		//ҽ�Ʒ���
		else if(form.equals("medicinfoBean")){
			type = "��ͨҽ�Ʒ�����Ϣ��";
		}else if(form.equals("BookMedicinfoBean")){
			type = "ԤԼҽ�Ʒ�����Ϣ��";
		}
		//ר�����
		else if(form.equals("inquiryForm")){
			type = "�����ʾ���ƿ�";
		}else if(form.equals("inquiryForm")){
			type = "������Ϣ������";
		}
		
		return type;
	}
%>