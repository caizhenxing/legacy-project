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
    
    <title>座席监控面板</title>
    
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
    						cells[3].innerHTML = "空闲";
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
		//更新座席状态 table
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
		//更新座席状态 文本框的
		function updateAgentState()
		{
			try
			{
				var status = opener.queryAgentStateNum();
				//可以查询：参数=1登陆座席数量，参数=2空闲座席数量，参数=3通话座席数量，参数=4离席座席数量，参数=6外线排队数量，参数=5外线总数
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
						//可外呼座席enableOutCallWorker = 空闲座席 未就绪座席unreadyWorker = agentNum - 登录座席 服务座席workingWorker=工作座席
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
						//外线排队数
						document.getElementById('outLineQueue').value = arrs[4];
						//外线总数 outLineSum
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
		
		//开始监听
		function beginMonitor()
		{
			if(checkWorkId())
			{
				var i = opener.m_tlaListen(selectWork);
				if(i!=0)
				{
					printInfo('监听'+selectWork+'失败 调用函数tlaListen('+selectWork+') 返回值:'+i+';');
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
					printInfo('停止监听'+selectWork+'失败 调用函数tlaStoplisten() 返回值:'+i+';');
				}
				else
				{
					document.getElementById('endMonitor').style.display="none";
					document.getElementById('beginMonitor').style.display="block";
				}
			}
		}
		//阻塞座席
		function blockAgent()
		{
			if(checkWorkId())
			{
				
			}
		}
		//强插座席
		function insertAgent()
		{
			if(checkWorkId())
			{
				//m_tlaIntrude(wokrid)
				var i = opener.m_tlaIntrude(selectWork);
				if(i!=0)
				{
					printInfo('监视者强插座席工号'+selectWork+'失败 调用函数tlaIntrude('+selectWork+') 返回值:'+i+';');
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
					printInfo('监视者强拆座席工号'+selectWork+'失败 调用函数tlaCut('+selectWork+') 返回值:'+i+';');
				}
				else
				{
					document.getElementById('insertBtn').style.display="block";
					document.getElementById('insertBtn').style.display="none";
				}
			}
		}
		//注销座席
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
						printInfo('注销'+selectWork+'失败 调用函数tlaKill(2,'+selectWork+') 返回值:'+i+';');
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
				printInfo('注销座席出现异常'+e.name+":"+e.message);
			}
		}
		//呼叫拦截
		function callHoldup()
		{
			if(checkWorkId())
			{
				//m_tlaGetcall(workid)
				var i = opener.m_tlaGetcall(selectWork);
				if(i!=0)
				{
					printInfo('呼叫拦截'+selectWork+'失败 调用函数tlaGetcall('+selectWork+') 返回值:'+i+';');
				}
				else
				{
					//document.getElementById('endMonitor').style.display="block";
					//document.getElementById('beginMonitor').style.display="none";
				}
			}
		}
		//呼叫拆除
		function callBackout()
		{
			if(checkWorkId())
			{
				//m_tlaCut(workid)
				var i = opener.m_tlaCut(selectWork);
				if(i!=0)
				{
					printInfo('呼叫拆除'+selectWork+'失败 调用函数tlaCut('+selectWork+') 返回值:'+i+';');
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
                    <th style="cursor:pointer"><div>座席工号</div></th>
                    <th style="cursor:pointer" style="display:none;"><div>座席名</div></th>
                    <th style="cursor:pointer"><div>座席角色</div></th>
                    <th style="cursor:pointer"><div>座席状态</div></th>
                    <th style="cursor:pointer" style="display:none;"><div>所在服务</div></th>
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
		        	<td colspan="2"><span class="Statistics" style="display:block">座席服务情况统计</span></td>
	        	</tr>
	        	<tr>
	        	  <td width="100" class="Navigation">登录座席</td>
        	      <td width="152"><input name="text" type="text" class="Text" id="loginWorker"/></td>
        	  </tr>
        	    <tr>
		        	<td class="Navigation">离席座席</td><!-- 未登录座席 -->
		        	<td><input type="text" class="Text" id="unreadyWorker"/></td>
		        </tr>
		        <tr>
		        	<td class="Navigation">空闲座席</td>
		        	<td><input type="text" class="Text" id="freeWorker"/></td>
		        </tr>
		        <tr>
		        	<td class="Navigation">通话座席</td>
		        	<td><input type="text" class="Text" id="talkingWorker"/></td>
		        </tr>
		       <tr>
		        	<td class="Navigation">外线排队</td>
		        	<td><input type="text" class="Text" id="outLineQueue"/></td>
		        </tr>
		        <tr>
		        	<td class="Navigation">外线总数</td>
		        	<td><input type="text" class="Text" id="outLineSum"/></td>
		        </tr>
		    
		        <tr style="display:none;">
		        	<td class="Navigation">离席座席数量</td><!-- 座席登录点离席 -->
		        	<td><input type="text" class="Text" id="leavingWorker"/></td>
		        </tr>
		        <tr style="display:none;">
		        	<td class="Navigation">可外呼座席</td>
		        	<td><input type="text" class="Text" id="enableOutCallWorker"/></td>
		        </tr>
		      
		        <tr style="display:none;">
		        	<td class="Navigation" >服务座席</td>
		        	<td><input type="text" class="Text" id="workingWorker"/></td>
		        </tr>
		       
		        <tr>
		          <td colspan="2" class="Statistics">班长座席功能<br></td>
	          </tr>
		        <tr>
		          <td colspan="2" align="center"><table width="224">
                    <tr>
                      <td width="72">
                      	<input type="button" id="beginMonitor" value="开始监听" onclick="beginMonitor()" /><input type="button" id="endMonitor" value="停止监听" onclick="endMonitor()" style="display:none"/>
                      </td>
                      <td width="75">
                      	<input type="button" value="阻塞座席" onclick="blockAgent()" style="display:none"/>
                      </td>
                      <td width="70">
                      	<input type="button" id="insertBtn" value="强插座席" onclick="insertAgent()" />
                      	<input type="button" id="cutBtn" value="强拆座席" onclick="cutAgent()" style="display:none;" />
                      </td>
                    </tr>
                    <tr>
                      <td><input type="button" value="注销座席" onclick="logoutAgent()" /></td>
                      <td><input type="button" value="拦截呼叫" onclick="callHoldup()" /></td>
                      <td><input type="button" value="拆除呼叫" onclick="callBackout()" /></td>
                    </tr>
                  </table></td>
	          </tr>
  </table>
  </div>
   </div>
   <textarea cols=50 rows=20 id="txtLog"></textarea>
  </body>
</html>
