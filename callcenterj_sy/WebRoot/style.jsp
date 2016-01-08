<%
	String styleLocation=session.getAttribute("style")==null?"chun":session.getAttribute("style").toString();
	pageContext.setAttribute("styleLocation",styleLocation);
%>
<%
String commonPath = request.getContextPath();
String commonBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+commonPath+"/";

%>
<%@ taglib uri="/WEB-INF/leaf_right.tld" prefix="leafRight" %>
<div style="display:none;position:absolute;top:-100px;left:-100px;">
<SCRIPT language=javascript src="<%=commonBasePath %>/js/ajax.js" type=text/javascript></SCRIPT>
<SCRIPT language=javascript src="<%=commonBasePath %>/js/all.js" type=text/javascript></SCRIPT>
<SCRIPT language=javascript src="<%=commonBasePath %>/js/agentState.js" type=text/javascript></SCRIPT>
<script language="javascript">
		function selecttype1(){
		var billnum = document.getElementById('bill_num').value;
		getBClassExpertsInfo('expert_name','','billnum','<%=commonBasePath%>/callcenterj_sy/')
	}
</script>
<!-- import jquery.js(jquery-1.3.1.min.js) -->
<script type="text/javascript" src="<%=commonBasePath %>/js/jquery/jquery-1.3.1.min.js"></script>
<!-- import list style css -->
<script language="javascript" src="<%=commonBasePath %>/js/et/style.js"></script>
</div>