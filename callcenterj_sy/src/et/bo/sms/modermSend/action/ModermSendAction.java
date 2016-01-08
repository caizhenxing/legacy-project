package et.bo.sms.modermSend.action;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.custinfo.service.CustinfoService;
import et.bo.fileBean.FileUtil;
import et.bo.sad.form.SadForm;
import et.bo.sad.service.SadService;
import et.bo.sms.modermSend.service.SmsSendNewService;
import et.bo.sms.modermSend.service.impl.SMSContent;
import et.bo.sys.common.SysStaticParameter;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;


import java.io.InputStream;
import org.apache.struts.upload.FormFile;

import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.util.Constants;



public class ModermSendAction extends BaseAction {

	private SmsSendNewService ssns = null;
	
	private ClassTreeService cts = null;
    
	public ClassTreeService getCts() {
		return cts;
	}
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
		/**
		 * @describe 显示发送信息页
		 */
		public ActionForward toModermSendMessage(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{

			return map.findForward("modermSendMessage");
	    }
		/**
		 * @describe 执行发送信息
		 */
		public ActionForward operModerSendMessage(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;		
			SMSContent smscontent = new SMSContent();//实例化Bean
			String time = null;
			String tel  = null;
			String type = request.getParameter("type").toString();			
			String sendNum = dbd.getString("sendNum").toString();
			String receiveNum = dbd.getString("receiveNum").toString();
			String receiveManName = dbd.getString("receiveManName").toString();
			String schedularTime = dbd.getString("schedularTime").toString();
			String begintime = dbd.getString("begintime").toString();
			String content = dbd.getString("content").toString();		
			tel = ReceiveTelSum(receiveNum,receiveManName);//调用私有方法返回合成后的电话字符串
			time = schedularTime +""+begintime;//合成时间			
			smscontent.setSendNum(sendNum);
			smscontent.setReceiveNum(tel);
			smscontent.setSchedularTime(time);
			smscontent.setContent(content);			
			String judgeInFunction = judgeFunction(dbd,type);//调用私有方法返回短信发送类型			
			if(judgeInFunction.equals("sendGroup"))
			{
				smscontent.setSendState("sendGroup");
				ssns.sendToGroup(smscontent);
				return map.findForward("modermSendMessage");
			}
			else if(judgeInFunction.equals("sendOne"))
			{
				smscontent.setSendState("sendOne");
				ssns.sendToOne(smscontent);
				return map.findForward("modermSendMessage");
			}
			else
			{
				if(judgeInFunction.equals("time"))
				{
					smscontent.setSendState("time");
				}
				else
				{
					smscontent.setSendState("draft");
				}
				ssns.saveDraft(smscontent, dbd);
				return map.findForward("modermSendMessage");
			}
	    }		
		//判断短信的发送类型
		private String judgeFunction(DynaActionFormDTO dbd,String type)
		{
			String returnStr = null;//声明变量返回字符串，具体调用service里面的哪个方法
			SMSContent smscontent = new SMSContent();//实例化Bean
			String sendNum = dbd.getString("sendNum").toString();
			String receiveNum = dbd.getString("receiveNum").toString();
			String receiveManName = dbd.getString("receiveManName").toString();
			String schedularTime = dbd.getString("schedularTime").toString();
			String begintime = dbd.getString("begintime").toString();
			String sendType = dbd.getString("sendType").toString();
			String content = dbd.getString("content").toString();			
			if(sendType.equals("on"))//如果点击里定时按钮则把returnStr赋值为time
			{
				
				returnStr = "time";
			}
			else
			{
				if(receiveNum.equals("") && !receiveManName.equals(""))//接收号码为空，接收人不为空
				{	
					if(receiveManName.split(",").length>1)
					{
						returnStr = "sendGroup";
						
						if(type.equals("save"))
						{						//判断如果type类型为save则　bean的发送类型将被设置为草稿类型
							returnStr = "draft";
						}
					}
					else
					{
						returnStr = "sendOne";
						
						if(type.equals("save"))
						{
							returnStr = "draft";
						}
					}
				}
				else if(!receiveNum.equals("") && receiveManName.equals(""))//接收号码不为空，接收人为空
				{
					if(receiveNum.split(",").length>1)
					{
						returnStr = "sendGroup";
						
						if(type.equals("save"))//判断如果type类型为save则　bean的发送类型将被设置为草稿类型
						{
							returnStr = "draft";
						}
					}
					else
					{
						returnStr = "sendOne";
						if(type.equals("save"))//判断如果type类型为save则　bean的发送类型将被设置为草稿类型
						{
							returnStr = "draft";
						}
					}
				}
				else if(!receiveNum.equals("") || !receiveManName.equals(""))//接收人和号码都不为空
				{
					returnStr = "sendGroup";
					if(type.equals("save"))//判断如果type类型为save则　bean的发送类型将被设置为草稿类型
					{
						returnStr = "draft";
					}
				}
			}	
			return returnStr;
		}
		
		
		
		//合成电话号码
		private String ReceiveTelSum(String receiveNum , String receiveManName)
		{
			String TelSum = null;//作为方法的返回值
			String receivenum = null;//声明接收号码的变量
			String receivemanname = null;//声明接收人的变量
			String expression = null;//声明名为符号的变量
			String expression1 = null;//声明名为符号的变量
			
			if(!receiveNum.equals("") && receiveManName.equals(""))//接收号码为空，接收人不为空
			{	
				String strLast = null;							   //声明变量做接收字符用
				if(receiveNum.split(",").length>0)//判断传入的号码是否包含＂，＂符号
				{
					strLast = receiveNum.substring(receiveNum.length()-1, receiveNum.length());//如果包含，在得出这个字符串的最后一位是什么
					if(strLast.equals(","))//比较上面得出的字符，如果是＂，＂将进入循环
					{
						TelSum= receiveNum.substring(0, receiveNum.length()-1);//判断字符等于＂，＂　然后将这个字符串的最后一个逗号截去
					}
				}
				else
				{
						TelSum = receiveNum;//
				}
			}
			else if(receiveNum.equals("") && !receiveManName.equals(""))//接收号码不为空，接收人为空
			{
				receivemanname = ssns.telByLinkMan(receiveManName);//调用接口，将人名传入后台，返回来的是电话号码
				if(receivemanname.split(",").length>0)//判断字符串里面是否有符号＂，＂
				{
					expression = receivemanname.substring(receivemanname.length()-1, receivemanname.length());//判断取得的字符串的最后一位是不是符号什么
					if(expression.equals(","))//判断最后一位是是否与符号"，"相等．
					{
						TelSum = receivemanname.substring(0, receivemanname.length()-1);//执行了相等操作，那么这个字符串的最后一位的　＂，＂　，将被截取掉
					}
					else
					{
						TelSum = receivemanname;//否则正常赋值给变量　TelSum　作为返回值
					}
				}
			}
			else if(!receiveNum.equals("") && !receiveManName.equals(""))//接收人和接收号码都不为空
			{
				receivenum = receiveNum;//将接受号码
					if(receiveManName.split(",").length>0)//判断字符串里面是否包含＂，＂
					{
						receivemanname = ssns.telByLinkMan(receiveManName);//调用接口返回该人的电话号码
					}
						expression = receivenum + receivemanname;//将接收号码和接收人的号码合成一个字符串
						expression1 = expression.substring(expression.length()-1, expression.length());//取得最后一个字符是什么
					if(expression1.equals(","))
					{
						TelSum = expression.substring(0, expression.length()-1);
					}
					else
					{
						TelSum = expression;
					}
			}
			return TelSum;
		}
		/**
		 * 根据URL参数执行 toAllList 方法，返回要forward页面。
		 * @param ActionMapping
		 * @param ActionForm
		 * @param HttpServletRequest
		 * @param HttpServletResponse
		 * @return ActionForward 返回列表页面
		 */
		public ActionForward toAllList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response){
	        List list = ssns.custinfoAllQuery();
	        request.setAttribute("list",list);

			return map.findForward("alllist");
		}	
		public SmsSendNewService getSsns() {
			return ssns;
		}
		public void setSsns(SmsSendNewService ssns) {
			this.ssns = ssns;
		}
		
		public ActionForward getUserList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String userType = dto.get("userType").toString();
			
			List<LabelValueBean> userList = ssns.getUserList(userType);
			
			System.out.println("userList.size() is "+userList.size());
			List<LabelValueBean> userList2 = new ArrayList<LabelValueBean>();
			
			request.getSession().setAttribute("userList", userList);
			request.getSession().setAttribute("userList2", userList2);
			return map.findForward("select");
		}
		
		
		public ActionForward toUserQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			TreeControlNodeService node = cts.getNodeByNickName("telNoteType");
			List<LabelValueBean> userList = new ArrayList<LabelValueBean>();
			List<LabelValueBean> userList2 = new ArrayList<LabelValueBean>();
			
			List list = node.getChildren();
			Iterator it = list.iterator();
			List<LabelValueBean> usertypelist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(child.getLabel());
				System.out.println("label is "+child.getLabel());
				lvb.setValue(child.getId());
				System.out.println("value is "+child.getId());
				usertypelist.add(lvb);
			}
			request.setAttribute("list", usertypelist);
			request.getSession().setAttribute("userList", userList);
			request.getSession().setAttribute("userList2", userList2);
			return map.findForward("query");
		}
		
		public ActionForward select(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
//			List groupList = sss.linkGroupQuery();
//			List<LabelValueBean> userList = om.getUserList("");
//			List<LabelValueBean> userList2 = new ArrayList<LabelValueBean>();
////			request.getSession().setAttribute("groupList", groupList);
//			request.getSession().setAttribute("userList", userList);
//			request.getSession().setAttribute("userList2", userList2);
			return map.findForward("selectFrame");
		}

}
