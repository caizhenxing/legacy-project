<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.struts.ContextLoaderPlugIn" %>
<%@ page import="excellence.common.classtree.ClassTreeService" %>
<%@ page import="excellence.common.tools.LabelValueBean" %>

<%
String s = "";
ApplicationContext ac = (ApplicationContext)application.getAttribute(ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
ClassTreeService cts = (ClassTreeService) ac.getBean("ClassTreeService");
String selectName = request.getParameter("selectName");
List<LabelValueBean> list = cts.getLabelVaList(selectName);
List<String> vList = new ArrayList<String>();
if(list != null){	
	for(int i = 0; i<list.size(); i++){
		LabelValueBean lvb = list.get(i);
		vList.add(lvb.getValue());
		//s += " <option value="+ lvb.getValue() +">"+ lvb.getLabel() +"</option>";
	}
}
Collections.sort(vList);
for(int i = 0; i<vList.size(); i++){
	for(int j = 0; j<list.size(); j++){
		LabelValueBean lvb = list.get(j);
		if(lvb.getValue().equals(vList.get(i))){
			s += "<option value="+ lvb.getValue() +">"+ lvb.getLabel() +"</option>";
			break;
		}
	}
}
out.print(s);
%>
