<%@page pageEncoding="MS932" contentType="text/html; charset=MS932" %>
<%@page import="jp.go.jsps.kaken.util.CheckDiditUtil" %>

<%
	String[] ID=new String[20];
	String[] checkDigitID=new String[20];

	for(int i=0;i<20;i++){
		ID[i]=request.getParameter("id"+i);
		if(ID[i]==null){
			ID[i]="";
		}
		checkDigitID[i]="";
		if(ID[i]!=null&&!ID[i].equals("")){
			checkDigitID[i]=CheckDiditUtil.getCheckDigit(ID[i], CheckDiditUtil.FORM_ALP);
		}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=MS932"/>
<title></title>
</head>
<body>
<form action="./digit.jsp">
<input name="id0"><%=ID[0]%>-&gt;<%=checkDigitID[0]%><br />
<input name="id1"><%=ID[1]%>-&gt;<%=checkDigitID[1]%><br />
<input name="id2"><%=ID[2]%>-&gt;<%=checkDigitID[2]%><br />
<input name="id3"><%=ID[3]%>-&gt;<%=checkDigitID[3]%><br />
<input name="id4"><%=ID[4]%>-&gt;<%=checkDigitID[4]%><br />
<input name="id5"><%=ID[5]%>-&gt;<%=checkDigitID[5]%><br />
<input name="id6"><%=ID[6]%>-&gt;<%=checkDigitID[6]%><br />
<input name="id7"><%=ID[7]%>-&gt;<%=checkDigitID[7]%><br />
<input name="id8"><%=ID[8]%>-&gt;<%=checkDigitID[8]%><br />
<input name="id9"><%=ID[9]%>-&gt;<%=checkDigitID[9]%><br />
<input name="id10"><%=ID[10]%>-&gt;<%=checkDigitID[10]%><br />
<input name="id11"><%=ID[11]%>-&gt;<%=checkDigitID[11]%><br />
<input name="id12"><%=ID[12]%>-&gt;<%=checkDigitID[12]%><br />
<input name="id13"><%=ID[13]%>-&gt;<%=checkDigitID[13]%><br />
<input name="id14"><%=ID[14]%>-&gt;<%=checkDigitID[14]%><br />
<input name="id15"><%=ID[15]%>-&gt;<%=checkDigitID[15]%><br />
<input name="id16"><%=ID[16]%>-&gt;<%=checkDigitID[16]%><br />
<input name="id17"><%=ID[17]%>-&gt;<%=checkDigitID[17]%><br />
<input name="id18"><%=ID[18]%>-&gt;<%=checkDigitID[18]%><br />
<input name="id19"><%=ID[19]%>-&gt;<%=checkDigitID[19]%><br />
<input type="submit" value="check">
</form>
</body>
</html>
