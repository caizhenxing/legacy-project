package et.bo.sms.columnInfo.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sms.columnInfo.service.ColumnInfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import et.po.ColumnInfo;
import et.po.ColumnInfoSendtime;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
/**
 * 
 * @author chen gang
 *
 */
public class ColumnInfoAction extends BaseAction {
	    static Logger log = Logger.getLogger(ColumnInfoAction.class.getName());
	    
	    private ColumnInfoService om = null;
	    
	    private ClassTreeService cts = null;
	    
		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}
		/**
		 * @describe 用户评价主页面
		 */
		public ActionForward toColumnInfoMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("main");
	    }
		/**
		 * @describe 查询页
		 */
		public ActionForward toColumnInfoQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			TreeControlNodeService node = cts.getNodeByNickName("IVRMultiVoice");
			List list = node.getChildren();
			Iterator it = list.iterator();
			List<LabelValueBean> ivrlist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(child.getLabel());
				lvb.setValue(child.getBaseTreeNodeService().getNickName());
				ivrlist.add(lvb);
			}
			request.setAttribute("list", ivrlist);
			return map.findForward("query");
	    }
		
		public ActionForward toColumnInfoOrderProgramme(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			String orderType=request.getParameter("type");
			String id=request.getParameter("id");
			TreeControlNodeService node = cts.getNodeByNickName("IVRMultiVoice");
			List list = node.getChildren();
			Iterator it = list.iterator();
			List<LabelValueBean> ivrlist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(child.getLabel());
				lvb.setValue(child.getBaseTreeNodeService().getNickName());
				ivrlist.add(lvb);
			}
			request.setAttribute("list", ivrlist);
			if(orderType.equals("cancel")){
				ColumnInfo ci=(ColumnInfo)om.loadColunmInfo(id);
				ci.setMenuEndtime(TimeUtil.getNowTime());	
				om.updateColumnInfo(ci);
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("orderLoad");
			}
			if(orderType.equals("delete")){
				ColumnInfo ci=(ColumnInfo)om.loadColunmInfo(id);
				ci.setDeleteMark("delete");
				om.updateColumnInfo(ci);
				request.setAttribute("operSign", "sys.common.operSuccess");
				return map.findForward("orderLoad");
			}
			return map.findForward("orderLoad");
	    }
		
		public ActionForward toColumnInfoCustomize(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			String operType=request.getParameter("type");
			String orderType = request.getParameter("orderType");
			String id=request.getParameter("id");
			TreeControlNodeService node = cts.getNodeByNickName("IVRMultiVoice");
			List list = node.getChildren();
			Iterator it = list.iterator();
			List<LabelValueBean> ivrlist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(child.getLabel());
				lvb.setValue(child.getBaseTreeNodeService().getNickName());
				ivrlist.add(lvb);
			}
			request.setAttribute("list", ivrlist);
			if(operType.equals("cancel")){
				ColumnInfo ci=(ColumnInfo)om.loadColunmInfo(id);
				ci.setMenuEndtime(TimeUtil.getNowTime());	
				om.updateColumnInfo(ci);
				request.setAttribute("operSign", "sys.common.operSuccess");
				request.setAttribute("orderType", orderType);
				return map.findForward("load");
			}
			if(operType.equals("delete")){
				ColumnInfo ci=(ColumnInfo)om.loadColunmInfo(id);
				ci.setDeleteMark("delete");
				om.updateColumnInfo(ci);
				request.setAttribute("operSign", "sys.common.operSuccess");
				request.setAttribute("orderType", orderType);
				return map.findForward("load");
			}
			if(operType.equals("update")){
				ColumnInfo ci=(ColumnInfo)om.loadColunmInfo(id);
				ci.setColInfo(request.getParameter("columnInfo"));
				ci.setMobileNum(request.getParameter("mobileNum"));
				ci.setMenuBegintime(TimeUtil.getTimeByStr(request.getParameter("beginTime")));
				om.updateColumnInfo(ci);
				request.setAttribute("operSign", "sys.common.operSuccess");
				request.setAttribute("orderType", orderType);
				return map.findForward("load");
			}
			return map.findForward("load");
	    }
		
		public ActionForward toMessageColumnInfoQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			TreeControlNodeService node = cts.getNodeByNickName("smsType");
			List list = node.getChildren();
			Iterator it = list.iterator();
			List<LabelValueBean> collist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();
				List listForChild = child.getChildren();
				
				if(listForChild.size() > 0) {
					Iterator it2 = listForChild.iterator();
					while(it2.hasNext()) {
						LabelValueBean lvb = new LabelValueBean();
						
						TreeControlNodeService child2 = (TreeControlNodeService)it2.next();
						lvb.setLabel(child2.getLabel());
						lvb.setValue(child2.getBaseTreeNodeService().getNickName());
						
						collist.add(lvb);
					}
				} else{
					LabelValueBean lvb = new LabelValueBean();
					lvb.setLabel(child.getLabel());
					lvb.setValue(child.getBaseTreeNodeService().getNickName());
						
					collist.add(lvb);
				}
			}
			request.setAttribute("list", collist);
			return map.findForward("message");
	    }
		
		public ActionForward toMessageColumnInfoMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("mainMessage");
	    }
		
		public ActionForward toMessageColumnInfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			om.addColumnInfoMessage(dto);
			return map.findForward("mainMessage");
	    }
		/**
		 * @describe 页面Load
		 */
		public ActionForward toColumnInfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			TreeControlNodeService node = cts.getNodeByNickName("IVRMultiVoice");
			List list = node.getChildren();
			Iterator it = list.iterator();
			List<LabelValueBean> ivrlist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(child.getLabel());
				lvb.setValue(child.getBaseTreeNodeService().getNickName());
				ivrlist.add(lvb);
			}
			request.setAttribute("list", ivrlist);
			DynaActionFormDTO dto=(DynaActionFormDTO)form;
			String orderType=request.getParameter("orderType");
			if(orderType.equals("orderProgramme")){
				String type=request.getParameter("type");
				request.setAttribute("opertype", type);
				request.setAttribute("orderType", orderType);
				String id=request.getParameter("id");
				DynaBeanDTO dbd=om.colunmInfoQuery(id);
				String colinfo=dbd.get("columnInfo").toString();
				request.setAttribute("columnInfo",colinfo);
				request.setAttribute(map.getName(), dbd);
				return map.findForward("orderLoad");
			}
			if(orderType.equals("customize")){
				String type=request.getParameter("type");
				request.setAttribute("opertype", type);
				request.setAttribute("orderType", orderType);
				String id=request.getParameter("id");
				DynaBeanDTO dbd=om.colunmInfoQuery(id);
				String colinfo=dbd.get("columnInfo").toString();
				request.setAttribute("columnInfo",colinfo);
				request.setAttribute(map.getName(), dbd);
				return map.findForward("load");
			}
			return map.findForward("load");
	    }
		/**
		 * @describe 用户评价列表页
		 */
		public ActionForward toColumnInfoList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String orderType = request.getParameter("orderType");
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("firewallpageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(19);
	        List list = om.columnInfoSearch(dto, pageInfo);
	        int size = om.getColumnInfoSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        request.setAttribute("orderType", dto.get("orderType"));
	        if(request.getAttribute("orderType") == null || "".equals(request.getAttribute("orderType")))
	        	request.setAttribute("orderType", orderType);
	        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
	        request.getSession().setAttribute("firewallpageTurning",pt);       
			return map.findForward("list");
	    }
		/**
		 * @describe 信息添加,修改,删除页
		 */
		public ActionForward toColumnInfoOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			
			TreeControlNodeService node = cts.getNodeByNickName("IVRMultiVoice");
			List list = node.getChildren();
			Iterator it = list.iterator();
			List<LabelValueBean> ivrlist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(child.getLabel());
				lvb.setValue(child.getBaseTreeNodeService().getNickName());
				ivrlist.add(lvb);
			}
			request.setAttribute("list", ivrlist);
			
			boolean flag = om.addColumnInfo(dto);
			if(!flag && "customize".equals(dto.get("orderType"))) {
				request.setAttribute("operSign", "dingzhishibai");
				return map.findForward("query");
			} else if(!flag && "orderProgramme".equals(dto.get("orderType"))) {
				request.setAttribute("operSign", "tuidingshibai");
				return map.findForward("query");
			}
			
			request.setAttribute("operSign", "sys.common.operSuccess");
			return map.findForward("query");
	    }
		
		public ActionForward toDelColumnInfo(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String id = request.getParameter("id");
			request.setAttribute("list", new ArrayList());
			boolean flag = om.cancelColInfo(id);
			
			if(!flag) {
				request.setAttribute("operSign", "tuidingshibai");
				return map.findForward("load");
			}
			
			request.setAttribute("operSign", "sys.common.operSuccess");
			return map.findForward("list");
		}
		
		/**
		 * 弹出设定时间页面
		 * @param map
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward toColumnInfoTimeSet(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception  {
			String nickname = request.getParameter("nickname");

			String colInfo = cts.getLabelByNickName(nickname);
			String type=request.getParameter("type");
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			dto.set("columnInfo", colInfo);
			dto.set("nickname", nickname);
			
			request.setAttribute(map.getName(), dto);
			if(type!=null){
				return map.findForward("set");
			}
			return map.findForward("timeSet");
		}
		
		/**
		 * 更新短信栏目
		 * @param map
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward toColumnInfoUpdate(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String colInfo = dto.get("columnInfo").toString();
			String nickname = dto.get("nickname").toString();
			String sendtime = dto.get("sendTime").toString();
			TreeControlNodeService node1 = cts.getNodeByNickName(nickname);
			node1.setLabel(colInfo);
			request.setAttribute("operSign", "sys.common.operSuccess");
			if("".equals(sendtime)) {
				TreeControlNodeService node = cts.getNodeByNickName("IVRMultiVoice");
				List list = node.getChildren();
				Iterator it = list.iterator();
				List<DynaBeanDTO> collist = new ArrayList<DynaBeanDTO>();
				while(it.hasNext()){
					TreeControlNodeService child = (TreeControlNodeService)it.next();
					DynaBeanDTO dbd = new DynaBeanDTO();
					dbd.set("columnInfo", child.getLabel());
					String nick = child.getBaseTreeNodeService().getNickName();
					dbd.set("nickname", nick);
					
					ColumnInfoSendtime cis = om.getCis(nick);
					
					if(cis == null)
						dbd.set("sendTime", "");
					else{
						if(cis.getColumnSendtime() == null)
							dbd.set("sendTime", "");
						else
							dbd.set("sendTime", cis.getColumnSendtime());
					}
						
					collist.add(dbd);
				}
				request.setAttribute("list", collist);
			} else{
//				System.out.println("  .... sendtime is "+sendtime);
				om.addColumnInfoSendtime(nickname, sendtime);
				
				TreeControlNodeService node = cts.getNodeByNickName("IVRMultiVoice");
				List list = node.getChildren();
				Iterator it = list.iterator();
				List<DynaBeanDTO> collist = new ArrayList<DynaBeanDTO>();
				while(it.hasNext()){
					TreeControlNodeService child = (TreeControlNodeService)it.next();
					DynaBeanDTO dbd = new DynaBeanDTO();
					dbd.set("columnInfo", child.getLabel());
					String nick = child.getBaseTreeNodeService().getNickName();
					dbd.set("nickname", nick);
					
					ColumnInfoSendtime cis = om.getCis(nick);
					if(cis == null)
						dbd.set("sendTime", "");
					else{
						if(cis.getColumnSendtime() == null)
							dbd.set("sendTime", "");
						else
							dbd.set("sendTime", cis.getColumnSendtime());
					}
						
					collist.add(dbd);
				}
				request.setAttribute("list", collist);
			}
			return map.findForward("settime");
		}
		
		public ActionForward toColumnInfoSet(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String sendtime = dto.get("sendTime").toString();
			String colInfo = dto.get("columnInfo").toString();
			String nickname = dto.get("nickname").toString();
			if("".equals(sendtime)) {
				TreeControlNodeService node = cts.getNodeByNickName("smsType");
				List list = node.getChildren();
				Iterator it = list.iterator();
				List<DynaBeanDTO> collist = new ArrayList<DynaBeanDTO>();
				while(it.hasNext()){
					TreeControlNodeService child = (TreeControlNodeService)it.next();
					List listForChild = child.getChildren();
					
					if(listForChild.size() > 0) {
						Iterator it2 = listForChild.iterator();
						while(it2.hasNext()) {
							DynaBeanDTO dbd = new DynaBeanDTO();
							TreeControlNodeService child2 = (TreeControlNodeService)it2.next();
							dbd.set("columnInfo", child2.getLabel());
							String nick = child2.getBaseTreeNodeService().getNickName();
							dbd.set("nickname", nick);
							
							ColumnInfoSendtime cis = om.getCis(nick);
							
							if(cis == null)
								dbd.set("sendTime", "");
							else{
								if(cis.getColumnSendtime() == null)
									dbd.set("sendTime", "");
								else
									dbd.set("sendTime", cis.getColumnSendtime());
							}
								
							collist.add(dbd);
						}
					} else{
						DynaBeanDTO dbd = new DynaBeanDTO();
						dbd.set("columnInfo", child.getLabel());
						String nick = child.getBaseTreeNodeService().getNickName();
						dbd.set("nickname", nick);
						
						ColumnInfoSendtime cis = om.getCis(nick);
						
						if(cis == null)
							dbd.set("sendTime", "");
						else{
							if(cis.getColumnSendtime() == null)
								dbd.set("sendTime", "");
							else
								dbd.set("sendTime", cis.getColumnSendtime());
						}
							
						collist.add(dbd);
					}
				}
				request.setAttribute("list", collist);
			} else{
				System.out.println("  .... sendtime is "+sendtime);
				om.addColumnInfoSendtime(nickname, sendtime);
				
				TreeControlNodeService node = cts.getNodeByNickName("smsType");
				List list = node.getChildren();
				Iterator it = list.iterator();
				List<DynaBeanDTO> collist = new ArrayList<DynaBeanDTO>();
				while(it.hasNext()){
					TreeControlNodeService child = (TreeControlNodeService)it.next();
					List listForChild = child.getChildren();
					
					if(listForChild.size() > 0) {
						Iterator it2 = listForChild.iterator();
						while(it2.hasNext()) {
							DynaBeanDTO dbd = new DynaBeanDTO();
							TreeControlNodeService child2 = (TreeControlNodeService)it2.next();
							dbd.set("columnInfo", child2.getLabel());
							String nick = child2.getBaseTreeNodeService().getNickName();
							dbd.set("nickname", nick);
							
							ColumnInfoSendtime cis = om.getCis(nick);
							
							if(cis == null)
								dbd.set("sendTime", "");
							else{
								if(cis.getColumnSendtime() == null)
									dbd.set("sendTime", "");
								else
									dbd.set("sendTime", cis.getColumnSendtime());
							}
								
							collist.add(dbd);
						}
					} else{
						DynaBeanDTO dbd = new DynaBeanDTO();
						dbd.set("columnInfo", child.getLabel());
						String nick = child.getBaseTreeNodeService().getNickName();
						dbd.set("nickname", nick);
						
						ColumnInfoSendtime cis = om.getCis(nick);
						
						if(cis == null)
							dbd.set("sendTime", "");
						else{
							if(cis.getColumnSendtime() == null)
								dbd.set("sendTime", "");
							else
								dbd.set("sendTime", cis.getColumnSendtime());
						}
							
						collist.add(dbd);
					}
				}
				request.setAttribute("list", collist);
			}
			
			return map.findForward("settime");
		}
		
		public ColumnInfoService getOm() {
			return om;
		}
		public void setOm(ColumnInfoService om) {
			this.om = om;
		}
		
		/////////////////////////////////以下为短信维护添删改查方法************************
		
		/**
		 * 发送信息维护主页面
		 */
		public ActionForward toMessageMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("messageMain");
	    }
		
		/**
		 * 发送信息维护主页面
		 */
		public ActionForward toMessageQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			List list = this.getColumnInfo();
			request.setAttribute("list", list);
			return map.findForward("messageQuery");
	    }
		
		/**
		 * 查询发送短信内容列表
		 */
		public ActionForward toMessageList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("userpageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(19);
	       //取得list及条数
	        List list = om.messagesQuery(dto,pageInfo);
	        int size = om.getMessagesSize();
	       
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list",list);
	        PageTurning pt = new PageTurning(pageInfo,"",map,request);
	        request.getSession().setAttribute("userpageTurning",pt);

			
			return map.findForward("messageList");
	    }
		
		/**
		 * 根据URL参数执行 toMessagesLoad 方法，返回要forward页面。
		 * 并且根据URL参数中type的值来判断所执行的操作:CRUD
		 * @param ActionMapping
		 * @param ActionForm
		 * @param HttpServletRequest
		 * @param HttpServletResponse
		 * @return ActionForward 返回列表页面
		 */
		public ActionForward toMessagesLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response){

			String type = request.getParameter("type");
	        request.setAttribute("opertype",type);
//	    	List<LabelValueBean> uList = om.getUserList();
//			request.setAttribute("uList", uList);
	        //根本type参数判断执行所需要的操作
	        
	        List<LabelValueBean> uList = this.getColumnInfo();
	        request.setAttribute("ulist", uList);
	        
	        if(type.equals("insert")){        	
	    		request.setAttribute("opertype", type);
	        	return map.findForward("messageLoad");
	        }
	        
	        if(type.equals("detail")){
	        	
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = om.getMessagesInfo(id);
	        	request.setAttribute(map.getName(),dto);
	        	
	        	return map.findForward("messageLoad");
	        }
	        
	        if(type.equals("update")){
	        	
	        	request.setAttribute("opertype", "update");
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = om.getMessagesInfo(id);
	        	request.setAttribute(map.getName(),dto);
	        	
	        	return map.findForward("messageLoad");
	        }
	        if(type.equals("delete")){
	        	
	        	String id = request.getParameter("id");
	        	IBaseDTO dto = om.getMessagesInfo(id);
	        	request.setAttribute(map.getName(), dto);

	        	return map.findForward("messageLoad");
	        }
	        
			return map.findForward("messageLoad");
		}
		/**
		 * 根据URL参数执行 toMessagesOper 方法，返回要forward页面。
		 * 并且根据URL参数中type的值来判断所执行的操作:CRUD
		 * @param ActionMapping
		 * @param ActionForm
		 * @param HttpServletRequest
		 * @param HttpServletResponse
		 * @return ActionForward 返回列表页面
		 */
		public ActionForward toMessagesOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response){
			
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			
			String type = request.getParameter("type");
			request.setAttribute("opertype",type);
//			List<LabelValueBean> uList = om.getUserList();
//			request.setAttribute("uList", uList);
			
			List<LabelValueBean> uList = this.getColumnInfo();
	        request.setAttribute("ulist", uList);
			
	        if (type.equals("insert")) {
				try {

					om.addMessage(dto);

					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("messageLoad");
					
				} catch (RuntimeException e) {
					return map.findForward("error");
				}
			}     
	        
	        if (type.equals("update")){
				try {
					
					boolean b = om.updateMessage(dto);
					if(b==true){
						request.setAttribute("operSign", "修改成功");
						return map.findForward("messageLoad");
					}else{
						request.setAttribute("operSign","修改失败");
						return map.findForward("messageLoad");
					}
					
				} catch (RuntimeException e) {
					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
	        
	        if (type.equals("delete")){
				try {
					
					om.deleteMessage((String)dto.get("id"));//这句可是真的删除了啊！
					request.setAttribute("operSign", "删除成功");
					return map.findForward("messageLoad");
					
					//下面这块是标记删除
					/*boolean b=messagesService.isDelete((String)dto.get("id"));
					if(b==true){
						request.setAttribute("operSign", "删除成功");
						return map.findForward("load");
					}else{
						request.setAttribute("operSign","删除失败");
						return map.findForward("load");
					}*/
				} catch (RuntimeException e) {
					return map.findForward("error");
				}
			}		
	        
	        
			return map.findForward("messageLoad");
		}
		
		private List getColumnInfo() {
			TreeControlNodeService node = cts.getNodeByNickName("smsType");
			List list = node.getChildren();
			Iterator it = list.iterator();
			List<LabelValueBean> collist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();
				List listForChild = child.getChildren();
				
				if(listForChild.size() > 0) {
					Iterator it2 = listForChild.iterator();
					while(it2.hasNext()) {
						LabelValueBean lvb = new LabelValueBean();
						
						TreeControlNodeService child2 = (TreeControlNodeService)it2.next();
						lvb.setLabel(child2.getLabel());
						lvb.setValue(child2.getBaseTreeNodeService().getNickName());
						
						collist.add(lvb);
					}
				} else{
					LabelValueBean lvb = new LabelValueBean();
					lvb.setLabel(child.getLabel());
					lvb.setValue(child.getBaseTreeNodeService().getNickName());
						
					collist.add(lvb);
				}
			}
			return collist;
		}
	    
}
