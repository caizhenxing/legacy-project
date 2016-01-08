<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="GB2312"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>formatLoad.jsp</title>
    
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <html:form action="" method="post">
		<table width="691" border="0" align="center">
		  <tr>
		    <td height="22"><div align="center">
		    
		    <table width="302" border="0" align="center">
		      <tr>
		        <td width="120" height="24"><div align="center" class="style4">
		          <div align="left">��ǰ����ʽ</div>
		        </div></td>
		        <td width="84" class="style5"><bean:write name="oldFormat"/></td>
		        <td width="86" class="style5">&nbsp;</td>
		      </tr>
		      <tr>
		        <td width="266" height="36"><div align="center" class="style4">
		          <div align="left">��ѡ����ʽ</div>
		        </div></td>
		        <td>
		            <span class="style7">
		            <html:select property="classId" onchange="selectTypeInfo()">
		         		<html:options collection="typeList" property="value" labelProperty="label"/>
		        	</html:select>
		           </span>
		        </td>
		        <td>
		          <input type="submit" name="Submit" value="ѡ ��">
		     	</td>
		      </tr>
		    </table>
		   
		    </div></td>
		  </tr>
		  <tr>
		    <td><div align="center"><table width="691" border="1">
		         <tr>
		           <td width="507" colspan="2"><div align="center" class="style6"></div>
		              <div align="center"><span class="style4"><span class="style5"></span></span></div>��             </td>
		         </tr>
		         <tr>
		           <td><div align="center" class="style6">������Ŀ��</div></td>
		           <td><html:hidden name='formatInfo' property='articleNum' write="true"/></td>
		         </tr>
		         <tr>
		           <td><div align="center" class="style6">��ʾ���ͣ�</div></td>
		           <td>
		           <html:hidden name='formatInfo' property='showType' write="true"/></td>
		         </tr>
		         <tr>
		           <td height="68"><div align="center" class="style6">��ʾ���ݣ�</div></td>
		           <td><p>
		             <input type="checkbox" name="showProperty" disabled value="true" <logic:equal name='formatInfo' property='showProperty' value="true" scope="request">checked</logic:equal> />
		             <span class="style7">��������</span>             
		             <input type="checkbox" name="showTile" disabled value="true" <logic:equal name='formatInfo' property='showTile' value="true" scope="request">checked</logic:equal> />
		             ���±���
		             <input type="checkbox" name="author" disabled value="true" <logic:equal name='formatInfo' property='author' value="true" scope="request">checked</logic:equal> />
		�� ��           </p>
		             <p>      <input type="checkbox" name="clickNum" disabled value="true" <logic:equal name='formatInfo' property='clickNum' value="true" scope="request">checked</logic:equal> />
		               �����
		                 <input type="checkbox" name="showDate" disabled value="true" <logic:equal name='formatInfo' property='showDate' value="true" scope="request">checked</logic:equal> />
		               ����ʱ��
		               <input type="checkbox" name="showMore" disabled value="true" <logic:equal name='formatInfo' property='showMore' value="true" scope="request">checked</logic:equal> /> 
		           �����࡭��������             </p></td>
		         </tr>
		         <tr>
		           <td><div align="center" class="style6">��������ַ�����</div></td>
		           <td><html:hidden name='formatInfo' property='showTileMax' write="true"/></td>
		         </tr>
		         <tr>
		           <td><div align="center" class="style6">������ʽ��</div></td>
		           <td>�� ɫ ��<input type="text" name="textfield" style="background-color:<bean:write name='formatInfo' property='titleFontColor'/>"/>
		                         &nbsp;&nbsp;�� �ͣ�             
		               <logic:equal name='formatInfo' property='titleFontType' value="1" scope="request">
		               	����
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontType' value="2" scope="request">
		           		б��
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontType' value="3" scope="request">
		           		��+б
		           		</logic:equal>
		                        &nbsp;&nbsp;�ִ�С��
		                <logic:equal name='formatInfo' property='titleFontSize' value="10px" scope="request">
		           		10
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontSize' value="12px" scope="request">
		           		12
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontSize' value="14px" scope="request">
		           		14
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontSize' value="16px" scope="request">
		           		16
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontSize' value="18px" scope="request">
		           		18
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontSize' value="24px" scope="request">
		           		24
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontSize' value="36px" scope="request">
		           		36
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontSize' value="xx-small" scope="request">
		           		��С
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontSize' value="x-small" scope="request">
		           		��С
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontSize' value="small" scope="request">
		           		С
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontSize' value="medium" scope="request">
		           		��
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontSize' value="large" scope="request">
		           		��
		           		</logic:equal>
		           		<logic:equal name='formatInfo' property='titleFontSize' value="x-large" scope="request">
		           		�ش�
		           		</logic:equal>
		   		   </td>
		         </tr>
		         <tr>
		           <td><div align="center" class="style6">���������ַ���:</div></td>
		           <td><html:hidden name='formatInfo' property='contextNum'/></td>
		         </tr>
		         <tr>
		           <td><div align="center" class="style6">���ڷ�Χ:</div></td>
		           <td>ֻ��ʾ���
		             <html:hidden name='formatInfo' property='orderDate' write="true"/>���ڵ�����</td>
		         </tr>
		         <tr>
		           <td><div align="center" class="style6">�����ֶΣ�</div></td>
		           <td>
		           	<logic:equal name='formatInfo' property='showOrderColumn' value="newsId" scope="request">
		           		����ID
		           	</logic:equal>
		           	<logic:equal name='formatInfo' property='showOrderColumn' value="lastDate" scope="request">
		           		����ʱ��
		           	</logic:equal>
		           	<logic:equal name='formatInfo' property='showOrderColumn' value="clickNum" scope="request">
		           		�������
		           	</logic:equal>
					&nbsp;</td>
		         </tr>
		         <tr>
		           <td><div align="center" class="style6">���򷽷���</div></td>
		           <td>
		          	<logic:equal name='formatInfo' property='showOrderType' value="asc" scope="request">
		           		����
		           	</logic:equal>
		           	<logic:equal name='formatInfo' property='showOrderType' value="desc" scope="request">
		           		����
		           	</logic:equal>
		          &nbsp;</td>
		         </tr>
		
		         <tr>
		           <td colspan="2"><div align="center"></div>             <div align="center"></div>             <div align="center"></div>             <div align="center"></div>             <div align="center"></div>
		           ��</td>
		         </tr>
		       </table></div></td>
		  </tr>
		</table>
    </html:form>
  </body>
</html:html>
