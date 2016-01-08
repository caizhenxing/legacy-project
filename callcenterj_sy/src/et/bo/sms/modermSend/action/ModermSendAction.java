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
		 * @describe ��ʾ������Ϣҳ
		 */
		public ActionForward toModermSendMessage(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{

			return map.findForward("modermSendMessage");
	    }
		/**
		 * @describe ִ�з�����Ϣ
		 */
		public ActionForward operModerSendMessage(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;		
			SMSContent smscontent = new SMSContent();//ʵ����Bean
			String time = null;
			String tel  = null;
			String type = request.getParameter("type").toString();			
			String sendNum = dbd.getString("sendNum").toString();
			String receiveNum = dbd.getString("receiveNum").toString();
			String receiveManName = dbd.getString("receiveManName").toString();
			String schedularTime = dbd.getString("schedularTime").toString();
			String begintime = dbd.getString("begintime").toString();
			String content = dbd.getString("content").toString();		
			tel = ReceiveTelSum(receiveNum,receiveManName);//����˽�з������غϳɺ�ĵ绰�ַ���
			time = schedularTime +""+begintime;//�ϳ�ʱ��			
			smscontent.setSendNum(sendNum);
			smscontent.setReceiveNum(tel);
			smscontent.setSchedularTime(time);
			smscontent.setContent(content);			
			String judgeInFunction = judgeFunction(dbd,type);//����˽�з������ض��ŷ�������			
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
		//�ж϶��ŵķ�������
		private String judgeFunction(DynaActionFormDTO dbd,String type)
		{
			String returnStr = null;//�������������ַ������������service������ĸ�����
			SMSContent smscontent = new SMSContent();//ʵ����Bean
			String sendNum = dbd.getString("sendNum").toString();
			String receiveNum = dbd.getString("receiveNum").toString();
			String receiveManName = dbd.getString("receiveManName").toString();
			String schedularTime = dbd.getString("schedularTime").toString();
			String begintime = dbd.getString("begintime").toString();
			String sendType = dbd.getString("sendType").toString();
			String content = dbd.getString("content").toString();			
			if(sendType.equals("on"))//�������ﶨʱ��ť���returnStr��ֵΪtime
			{
				
				returnStr = "time";
			}
			else
			{
				if(receiveNum.equals("") && !receiveManName.equals(""))//���պ���Ϊ�գ������˲�Ϊ��
				{	
					if(receiveManName.split(",").length>1)
					{
						returnStr = "sendGroup";
						
						if(type.equals("save"))
						{						//�ж����type����Ϊsave��bean�ķ������ͽ�������Ϊ�ݸ�����
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
				else if(!receiveNum.equals("") && receiveManName.equals(""))//���պ��벻Ϊ�գ�������Ϊ��
				{
					if(receiveNum.split(",").length>1)
					{
						returnStr = "sendGroup";
						
						if(type.equals("save"))//�ж����type����Ϊsave��bean�ķ������ͽ�������Ϊ�ݸ�����
						{
							returnStr = "draft";
						}
					}
					else
					{
						returnStr = "sendOne";
						if(type.equals("save"))//�ж����type����Ϊsave��bean�ķ������ͽ�������Ϊ�ݸ�����
						{
							returnStr = "draft";
						}
					}
				}
				else if(!receiveNum.equals("") || !receiveManName.equals(""))//�����˺ͺ��붼��Ϊ��
				{
					returnStr = "sendGroup";
					if(type.equals("save"))//�ж����type����Ϊsave��bean�ķ������ͽ�������Ϊ�ݸ�����
					{
						returnStr = "draft";
					}
				}
			}	
			return returnStr;
		}
		
		
		
		//�ϳɵ绰����
		private String ReceiveTelSum(String receiveNum , String receiveManName)
		{
			String TelSum = null;//��Ϊ�����ķ���ֵ
			String receivenum = null;//�������պ���ı���
			String receivemanname = null;//���������˵ı���
			String expression = null;//������Ϊ���ŵı���
			String expression1 = null;//������Ϊ���ŵı���
			
			if(!receiveNum.equals("") && receiveManName.equals(""))//���պ���Ϊ�գ������˲�Ϊ��
			{	
				String strLast = null;							   //���������������ַ���
				if(receiveNum.split(",").length>0)//�жϴ���ĺ����Ƿ��������������
				{
					strLast = receiveNum.substring(receiveNum.length()-1, receiveNum.length());//����������ڵó�����ַ��������һλ��ʲô
					if(strLast.equals(","))//�Ƚ�����ó����ַ�������ǣ�����������ѭ��
					{
						TelSum= receiveNum.substring(0, receiveNum.length()-1);//�ж��ַ����ڣ�������Ȼ������ַ��������һ�����Ž�ȥ
					}
				}
				else
				{
						TelSum = receiveNum;//
				}
			}
			else if(receiveNum.equals("") && !receiveManName.equals(""))//���պ��벻Ϊ�գ�������Ϊ��
			{
				receivemanname = ssns.telByLinkMan(receiveManName);//���ýӿڣ������������̨�����������ǵ绰����
				if(receivemanname.split(",").length>0)//�ж��ַ��������Ƿ��з��ţ�����
				{
					expression = receivemanname.substring(receivemanname.length()-1, receivemanname.length());//�ж�ȡ�õ��ַ��������һλ�ǲ��Ƿ���ʲô
					if(expression.equals(","))//�ж����һλ���Ƿ������"��"��ȣ�
					{
						TelSum = receivemanname.substring(0, receivemanname.length()-1);//ִ������Ȳ�������ô����ַ��������һλ�ġ�����������������ȡ��
					}
					else
					{
						TelSum = receivemanname;//����������ֵ��������TelSum����Ϊ����ֵ
					}
				}
			}
			else if(!receiveNum.equals("") && !receiveManName.equals(""))//�����˺ͽ��պ��붼��Ϊ��
			{
				receivenum = receiveNum;//�����ܺ���
					if(receiveManName.split(",").length>0)//�ж��ַ��������Ƿ����������
					{
						receivemanname = ssns.telByLinkMan(receiveManName);//���ýӿڷ��ظ��˵ĵ绰����
					}
						expression = receivenum + receivemanname;//�����պ���ͽ����˵ĺ���ϳ�һ���ַ���
						expression1 = expression.substring(expression.length()-1, expression.length());//ȡ�����һ���ַ���ʲô
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
		 * ����URL����ִ�� toAllList ����������Ҫforwardҳ�档
		 * @param ActionMapping
		 * @param ActionForm
		 * @param HttpServletRequest
		 * @param HttpServletResponse
		 * @return ActionForward �����б�ҳ��
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
