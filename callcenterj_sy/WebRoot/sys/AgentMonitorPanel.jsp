<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="excellence.framework.base.container.SpringRunningContainer" %>
<%@ page import="et.bo.sys.user.service.UserService" %>
<%@ page import="java.util.List" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/newtreeview.tld" prefix="newtree"%>
<%@ taglib uri="/WEB-INF/page.tld" prefix="page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>��ϯ������</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" src="./js/Table.js"></script>
	<link rel="stylesheet" type="text/css" href="./style/agentMonitor.css">
	<script language="javascript">

		var MAXAGENTNUM = 20;
		var selectWork = '-1';
		function printInfo(msg)
		{
			alert(msg);
			document.getElementById('txtLog').value = document.getElementById('txtLog').value+'\r\n'+msg;
		}
		function getFreeWorkids(tblId)
		{
			try
			{
				var workArr = opener.getFreeWorkids();
				var freeWorks = '';
				for(var i=0; i<workArr.length; i++)
				{
					if(workArr[i]!=-1)
					{
						if(i==0)
						{
							freeWorks = workArr[i];
						}
						else
						{
							freeWorks = freeWorks +","+workArr[i];
						}
					}
					else
					{
						var oTbl = document.getElementById(tblId);
						setStatusByWorkIds(freeWorks,oTbl)
						//alert(oTbl.innerHTML);
						return freeWorks;
					}
				}
			}
			catch(e)
			{
				//alert(e.name+":"+e.message);
			}
		}
		
		function setStatusByWorkIds(freeWorks,oTbl)
		{
			if(freeWorks!=0)
			{
				freeWorks = freeWorks + '';
				var oTBody = oTbl.tBodies[0];
    			var colDataRows = oTBody.rows;
    			for(var i=0; i<colDataRows.length; i++)
    			{
    				var cells = colDataRows[i].cells;
    				if(cells[0])
    				{
    				
    					if(freeWorks.indexOf(cells[0].innerText)!=-1)
    					{
    						cells[3].innerHTML = "����";
    						cells[5].innerHTML = "1";
    						//colDataRows[i].className = "lightTr";
    					}
    					else
    					{
    						//colDataRows[i].className = "commonTr";
    					}
    				}
    			}
				//sortTable('tblSort', 5)
			}
		}
		
		function my_sortTable(tblId, col)
		{
			
		}
		
		var circleTime;
		function runLoad()
		{
			//txtCurTime
			var cycle = 5000;
			var circleTime=setInterval("updateComponect()",cycle); 
			
		}
		
		function updateComponect()
		{
			//getFreeWorkids('tblSort')
			updateAgentState();
			updateAgentStateTbl();
			
		}
		//������ϯ״̬ table
		function updateAgentStateTbl()
		{
			var oTbl = document.getElementById('tblSort');
			var oTBody = oTbl.tBodies[0];
    		var colDataRows = oTBody.rows;
    		var num = opener.getHasLoginAgentNum();
    		if(num>0)
    		{
    			for(var i=0; i<num; i++)
    			{
    				try
    				{
	   
	    				var workid = opener.getAgentWorkIdMonitorByNum(i);
	    				var state =  opener.getAgentStateMonitorByNum(i);
	    				//printInfo(state+":i is:"+i+"workid is:"+workid);
	    				var obj = document.getElementById('agentState'+workid);
	    				if(obj)
	    				{
	    					obj.innerText = state;
	    				}
	    				else
	    				{
	    				}
	    				
    				}
    				catch(e)
    				{
    					printInfo('updateAgentStateTbl()err');
    					printInfo(e.name+":"+e.message);
    				}
    			}
    		}
		}
		//������ϯ״̬ �ı����
		function updateAgentState()
		{
			try
			{
				var status = opener.queryAgentStateNum();
				//���Բ�ѯ������=1��½��ϯ����������=2������ϯ����������=3ͨ����ϯ����������=4��ϯ��ϯ����������=6�����Ŷ�����������=5��������
				//alert(status);
				if(status)
				{
			
					var arrs = status.split(",");
					if(arrs.length>0)
					{
						var logins = arrs[0];
						if(logins<0)
						{
							logins = '';
						}
						document.getElementById('loginWorker').value = logins;
						var frees = arrs[1];
						if(frees<0)
						{
							frees = '';
						}
						document.getElementById('freeWorker').value = frees;
						var talkings = arrs[2];
						if(talkings<0)
						{
							talkings = '';
						}
						document.getElementById('talkingWorker').value = talkings;
						var leavings = arrs[3];
						if(leavings<0)
						{
							leavings = '';
						}
		
						document.getElementById('leavingWorker').value = leavings;
						//�������ϯenableOutCallWorker = ������ϯ δ������ϯunreadyWorker = agentNum - ��¼��ϯ ������ϯworkingWorker=������ϯ
						try
						{
							var agentNum = getSumAgentNum();
							document.getElementById('enableOutCallWorker').value = frees;
							if(logins=='')
							{
								logins = 0;
							}
							document.getElementById('unreadyWorker').value = arrs[3];
							document.getElementById('workingWorker').value = talkings;
						}
						catch(e)
						{
							printInfo('updateAgentState()err');
							printInfo(e.name+":"+e.message);
						}
						//�����Ŷ���
						document.getElementById('outLineQueue').value = arrs[4];
						//�������� outLineSum
						document.getElementById('outLineSum').value = arrs[5];
					}
				}
			}
			catch(e)
			{
				//alert(e.name+":"+e.message);
			}
		}
		
		function getSumAgentNum()
		{
			var oTbl = document.getElementById('tblSort');
			if(oTbl)
			{
				var oTBody = oTbl.tBodies[0];
				var colDataRows = oTBody.rows;
				return colDataRows.length;
			}
			return -1;
		}
		//rowClickStl
		function setStyle2tr(obj)
		{
			var oTbl = document.getElementById('tblSort');
			if(oTbl)
			{
				var oTBody = oTbl.tBodies[0];
				var colDataRows = oTBody.rows;
				for(var i=0; i<colDataRows.length; i++)
				{
					if(colDataRows[i].srcClass)
					colDataRows[i].className=colDataRows[i].srcClass;
				}
				obj.className="rowClickStl";
				selectWork = obj.cells[0].innerText;
			}
		}
		
		
		
		
		function checkWorkId()
		{
			if(selectWork==-1)
				return false;
			return true;
		}
		
		//��ʼ����
		function beginMonitor()
		{
			if(checkWorkId())
			{
				var i = opener.m_tlaListen(selectWork);
				if(i!=0)
				{
					printInfo('����'+selectWork+'ʧ�� ���ú���tlaListen('+selectWork+') ����ֵ:'+i+';');
				}
				else
				{
					document.getElementById('endMonitor').style.display="block";
					document.getElementById('beginMonitor').style.display="none";
				}
			}
		}
		function endMonitor()
		{
			if(checkWorkId())
			{
				var i = opener.m_tlaStoplisten();
				if(i!=0)
				{
					printInfo('ֹͣ����'+selectWork+'ʧ�� ���ú���tlaStoplisten() ����ֵ:'+i+';');
				}
				else
				{
					document.getElementById('endMonitor').style.display="none";
					document.getElementById('beginMonitor').style.display="block";
				}
			}
		}
		//������ϯ
		function blockAgent()
		{
			if(checkWorkId())
			{
				
			}
		}
		//ǿ����ϯ
		function insertAgent()
		{
			if(checkWorkId())
			{
				//m_tlaIntrude(wokrid)
				var i = opener.m_tlaIntrude(selectWork);
				if(i!=0)
				{
					printInfo('������ǿ����ϯ����'+selectWork+'ʧ�� ���ú���tlaIntrude('+selectWork+') ����ֵ:'+i+';');
				}
				else
				{
					//document.getElementById('endMonitor').style.display="block";
					//document.getElementById('beginMonitor').style.display="none";
					document.getElementById('insertBtn').style.display="none";
					document.getElementById('insertBtn').style.display="block";
				}
			}
		}
		function cutAgent()
		{
			if(checkWorkId())
			{
				//m_tlaIntrude(wokrid)
				var i = opener.m_tlaCut(selectWork);
				if(i!=0)
				{
					printInfo('������ǿ����ϯ����'+selectWork+'ʧ�� ���ú���tlaCut('+selectWork+') ����ֵ:'+i+';');
				}
				else
				{
					document.getElementById('insertBtn').style.display="block";
					document.getElementById('insertBtn').style.display="none";
				}
			}
		}
		//ע����ϯ
		function logoutAgent()
		{
			try
			{
				if(checkWorkId())
				{
					//m_tlaKill(mode,para)
					var i = opener.m_tlaKill(2,selectWork);
					if(i!=0)
					{
						printInfo('ע��'+selectWork+'ʧ�� ���ú���tlaKill(2,'+selectWork+') ����ֵ:'+i+';');
					}
					else
					{
						//document.getElementById('endMonitor').style.display="block";
						//document.getElementById('beginMonitor').style.display="none";
					}
				}
			}
			catch(e)
			{
				printInfo('ע����ϯ�����쳣'+e.name+":"+e.message);
			}
		}
		//��������
		function callHoldup()
		{
			if(checkWorkId())
			{
				//m_tlaGetcall(workid)
				var i = opener.m_tlaGetcall(selectWork);
				if(i!=0)
				{
					printInfo('��������'+selectWork+'ʧ�� ���ú���tlaGetcall('+selectWork+') ����ֵ:'+i+';');
				}
				else
				{
					//document.getElementById('endMonitor').style.display="block";
					//document.getElementById('beginMonitor').style.display="none";
				}
			}
		}
		//���в��
		function callBackout()
		{
			if(checkWorkId())
			{
				//m_tlaCut(workid)
				var i = opener.m_tlaCut(selectWork);
				if(i!=0)
				{
					printInfo('���в��'+selectWork+'ʧ�� ���ú���tlaCut('+selectWork+') ����ֵ:'+i+';');
				}
				else
				{
					//document.getElementById('endMonitor').style.display="block";
					//document.getElementById('beginMonitor').style.display="none";
				}
			}
		}
	</script>
  </head>
  
  <body onload="runLoad()">
  <div id="container">
     <div style="float:left;width:450px;height:338px;overflow:auto;">
     <table border="0" id="tblSort" style="width:450px;">
            <thead>
                <tr>
                    <th style="cursor:pointer"><div>��ϯ����</div></th>
                    <th style="cursor:pointer" style="display:none;"><div>��ϯ��</div></th>
                    <th style="cursor:pointer"><div>��ϯ��ɫ</div></th>
                    <th style="cursor:pointer"><div>��ϯ״̬</div></th>
                    <th style="cursor:pointer" style="display:none;"><div>���ڷ���</div></th>
                    <th id="showHide"  style="cursor:pointer" style="display:none;"><div>sortState</div></th>                    
                </tr>
            </thead>
            <tbody>
            <% 
            	UserService us = (UserService)SpringRunningContainer.getInstance().getBean("UserService");
            	List l = us.getUserList();
            	pageContext.setAttribute("l",l);
            %>
            <logic:iterate id="c" name="l" indexId="i">
				<%
					String style = "Wonderful";
					if(i%2==0)
						style="Partner";
				%>
                <tr class="<%=style %>" srcClass="<%=style %>" onclick="setStyle2tr(this)">
                    <td style="cursor:pointer"><bean:write name="c" property="worknum" filter="true"/></td>
                    <td style="cursor:pointer;display:none;" ><bean:write name="c" property="workname" filter="true"/></td>
                    <td style="cursor:pointer"><bean:write name="c" property="impower" filter="true"/></td>
                    <td style="cursor:pointer" id="agentState<bean:write name="c" property="worknum" filter="true"/>"><bean:write name="c" property="state" filter="true"/></td>
                    <td style="cursor:pointer;display:none;"><bean:write name="c" property="worknum" filter="true"/></td>
                    <td style="cursor:pointer" style="display:none;">-1</td>
                </tr>
             </logic:iterate>
            </tbody>
        </table> 
        </div>
         <div style="float:right;"><table class="Background">
	        	<tr>
		        	<td colspan="2"><span class="Statistics" style="display:block">��ϯ�������ͳ��</span></td>
	        	</tr>
	        	<tr>
	        	  <td width="100" class="Navigation">��¼��ϯ</td>
        	      <td width="152"><input name="text" type="text" class="Text" id="loginWorker"/></td>
        	  </tr>
        	    <tr>
		        	<td class="Navigation">��ϯ��ϯ</td><!-- δ��¼��ϯ -->
		        	<td><input type="text" class="Text" id="unreadyWorker"/></td>
		        </tr>
		        <tr>
		        	<td class="Navigation">������ϯ</td>
		        	<td><input type="text" class="Text" id="freeWorker"/></td>
		        </tr>
		        <tr>
		        	<td class="Navigation">ͨ����ϯ</td>
		        	<td><input type="text" class="Text" id="talkingWorker"/></td>
		        </tr>
		       <tr>
		        	<td class="Navigation">�����Ŷ�</td>
		        	<td><input type="text" class="Text" id="outLineQueue"/></td>
		        </tr>
		        <tr>
		        	<td class="Navigation">��������</td>
		        	<td><input type="text" class="Text" id="outLineSum"/></td>
		        </tr>
		    
		        <tr style="display:none;">
		        	<td class="Navigation">��ϯ��ϯ����</td><!-- ��ϯ��¼����ϯ -->
		        	<td><input type="text" class="Text" id="leavingWorker"/></td>
		        </tr>
		        <tr style="display:none;">
		        	<td class="Navigation">�������ϯ</td>
		        	<td><input type="text" class="Text" id="enableOutCallWorker"/></td>
		        </tr>
		      
		        <tr style="display:none;">
		        	<td class="Navigation" >������ϯ</td>
		        	<td><input type="text" class="Text" id="workingWorker"/></td>
		        </tr>
		       
		        <tr>
		          <td colspan="2" class="Statistics">�೤��ϯ����<br></td>
	          </tr>
		        <tr>
		          <td colspan="2" align="center"><table width="224">
                    <tr>
                      <td width="72">
                      	<input type="button" id="beginMonitor" value="��ʼ����" onclick="beginMonitor()" /><input type="button" id="endMonitor" value="ֹͣ����" onclick="endMonitor()" style="display:none"/>
                      </td>
                      <td width="75">
                      	<input type="button" value="������ϯ" onclick="blockAgent()" style="display:none"/>
                      </td>
                      <td width="70">
                      	<input type="button" id="insertBtn" value="ǿ����ϯ" onclick="insertAgent()" />
                      	<input type="button" id="cutBtn" value="ǿ����ϯ" onclick="cutAgent()" style="display:none;" />
                      </td>
                    </tr>
                    <tr>
                      <td><input type="button" value="ע����ϯ" onclick="logoutAgent()" /></td>
                      <td><input type="button" value="���غ���" onclick="callHoldup()" /></td>
                      <td><input type="button" value="�������" onclick="callBackout()" /></td>
                    </tr>
                  </table></td>
	          </tr>
  </table>
  </div>
   </div>
   <textarea cols=50 rows=20 id="txtLog"></textarea>
  </body>
</html>
