package et.bo.sms.sendAndReceive.action;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sms.sendAndReceive.service.sendAndReceiveService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;



public class sendAndReceiveAction extends BaseAction {
		private sendAndReceiveService sars = null;
		private ClassTreeService cts = null;
		/**
		 * 短信收发统计查询query
		 * @param map
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward toQueryQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			return map.findForward("toQueryQuery");
	    }	
		/**
		 * 短信收发统计查询main
		 * @param map
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward toQueryMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			return map.findForward("toQueryMain");
	    }	
		/**
		 * @describe 已发送信息框架页面
		 */
		public ActionForward toSendMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("toSendMain");
	    }
		/**
		 * @describe 已发送信息查询页
		 */
		public ActionForward toSendQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			String type=request.getParameter("type");
			if(type==null||type.equals("")||type.equals("already")){
				request.setAttribute("type", "already");
			}
			if(type.equals("receive")){
				request.setAttribute("type", "receive");
			}
			if(type.equals("notSend")){
				request.setAttribute("type", "notSend");
			}
			TreeControlNodeService node = cts.getNodeByNickName("smsType");
			List list = node.getChildren();
			Iterator it = list.iterator();
			List<LabelValueBean> ivrlist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();
				Iterator itr=child.getChildren().iterator();
				while(itr.hasNext()){
					TreeControlNodeService ch=(TreeControlNodeService)itr.next();
					LabelValueBean lvb = new LabelValueBean();
					lvb.setLabel(ch.getLabel());
					lvb.setValue(ch.getBaseTreeNodeService().getNickName());
					ivrlist.add(lvb);
				}				
			}
			request.setAttribute("list", ivrlist);
			return map.findForward("toSendQuery");
	    }	
		/**
		 * @describe 未发送信息框架页面
		 */
		public ActionForward toSendNotMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			return map.findForward("toSendNotMain");
	    }
		/**
		 * @describe 未发送信息查询页
		 */
		public ActionForward toSendNotQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			return map.findForward("toSendNotQuery");
	    }	
		/**
		 * @describe 已发送信息Load页面
		 */
		public ActionForward toSendLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {			
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;
			String type = request.getParameter("type");			
	        request.setAttribute("opertype",type);	        
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = sars.getSendInfo(id);
	        	request.setAttribute(map.getName(), dto);	        	
	        	request.setAttribute("id", id);	        	
	        	return map.findForward("toSendLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");	        	
	        	String delType = request.getParameter("delType").toString();//从页面得到删除的类型 有两种类型:一种为删除类型,一种为永久删除类型	        	
	        	IBaseDTO dto = sars.getSendInfo(id);
	        	request.setAttribute(map.getName(), dto);	        	
	        	request.getSession().setAttribute("delTypeSend",delType);	        	
	        	request.setAttribute("id", id);
	        	return map.findForward("toSendLoad");
	        }
			return map.findForward("toSendLoad");
	    }	
		/**
		 * @describe 未发送信息Load页面
		 */
		public ActionForward toSendNotLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {				
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;
			String type = request.getParameter("type");			
	        request.setAttribute("opertype",type);	        
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = sars.getSendNotInfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);	        	
	        	return map.findForward("toSendNotLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");
	        	String delType = request.getParameter("delType").toString();//从页面得到删除的类型 有两种类型:一种为删除类型,一种为永久删除类型
	        	IBaseDTO dto = sars.getSendNotInfo(id);
	        	request.setAttribute(map.getName(), dto);	        	
	        	request.getSession().setAttribute("delTypeSendNot",delType);        	
	        	request.setAttribute("id", id);
	        	return map.findForward("toSendNotLoad");
	        }
			return map.findForward("toSendNotLoad");
	    }	
		/**
		 * @describe 已发送信息列表页
		 */
		public ActionForward toSendList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			List list = null;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("sendpageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(14);
	        try {
	        	list = sars.sendQuery(dto,pageInfo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        int size = sars.getSendSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/cc_agro_sy/",map,request);
	        request.getSession().setAttribute("sendpageTurning",pt);       
			return map.findForward("toSendList");
	    }
		/**
		 * @describe 未发送信息列表页
		 */
		public ActionForward toSendNotList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			List list = null;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("sendNotpageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(14);
	        try {
	        	list = sars.sendNotQuery(dto,pageInfo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}       
	        int size = sars.getSendNotSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/cc_agro_sy/",map,request);
	        request.getSession().setAttribute("sendNotpageTurning",pt);       
			return map.findForward("toSendNotList");
	    }
		/**
		 * @describe 已发送信息操作删除
		 */
		public ActionForward toSendOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
	        request.setAttribute("opertype",type);			
	        String delType = request.getSession().getAttribute("delTypeSend").toString();       
			if (type.equals("delete")){
				try {
					sars.delSend((String)dto.get("id"),delType);
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toSendLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toSendLoad");
	    }
		/**
		 * @describe 未发送信息操作删除
		 */
		public ActionForward toSendNotOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
	        request.setAttribute("opertype",type);			
	        String delType = request.getSession().getAttribute("delTypeSendNot").toString();	        
			if (type.equals("delete")){
				try {
					sars.delSendNot((String)dto.get("id"),delType);
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toSendNotLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toSendNotLoad");
	    }
			
//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊读取信息开始＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
		/**
		 * @describe 收信息框架页面
		 */
		public ActionForward toReceiveMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("toReceiveMain");
	    }
		/**
		 * @describe 收信息查询页
		 */
		public ActionForward toReceiveQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			IBaseDTO dbd = (DynaActionFormDTO)form;	
			return map.findForward("toReceiveQuery");
	    }
		/**
		 * @describe 收信息Load页面
		 */
		public ActionForward toReceiveLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {				
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;
			String type = request.getParameter("type");			
	        request.setAttribute("opertype",type);	        
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = sars.receiveOneQuery(id);
	        	request.setAttribute(map.getName(), dto);
	        	return map.findForward("toReceiveLoad");
	        }  
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = sars.receiveOneQuery(id);
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	return map.findForward("toReceiveLoad");
	        }
			return map.findForward("toReceiveLoad");
	    }		
		/**
		 * @describe 短信删除
		 */
		public ActionForward operReceive(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {	
			String id = request.getParameter("id");
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
				try {
					sars.delMessage(dto.getString("id").toString());
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toReceiveLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
	    }
		/**
		 * @describe 收信息列表页
		 */
		public ActionForward toReceiveList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			List list = null;
			String pageState = null;
	        PageInfo pageInfo = null;        
	        String num = "0";//request.getParameter("num").toString();//传入的参数	        
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("receivePageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(13);
	        try {
	        	list = sars.receiveQuery(dto,pageInfo, num);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        int size = sars.getReceivetSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/cc_agro_sy/",map,request);
	        request.getSession().setAttribute("receivePageTurning",pt);       
			return map.findForward("toReceiveList");
	    }
//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊读取信息结束＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
		
		
		public sendAndReceiveService getSars() {
			return sars;
		}
		public void setSars(sendAndReceiveService sars) {
			this.sars = sars;
		}
		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}    
}
