/*
 * 
 */
package et.bo.callcenter.callOutSet.action;

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

import et.bo.callcenter.callOutSet.service.CallOutSetService;
import et.bo.sys.common.SysStaticParameter;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
/**
 * 
 * @author chen gang
 *
 */
@SuppressWarnings("unchecked")
public class CallOutSetAction extends BaseAction {
	    static Logger log = Logger.getLogger(CallOutSetAction.class.getName());
	    
	    private CallOutSetService om = null;
	    
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
		public ActionForward toAppraiseMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("main");
	    }
		/**
		 * @describe 查询页
		 */
		public ActionForward toAppraiseQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			//System.out.println("jin ru cha xun");
			return map.findForward("query");
	    }
		/**
		 * @describe 页面Load
		 */
		public ActionForward toOrderMenuLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
//			IVRMultiVoice
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
			request.setAttribute("ivrlist", ivrlist);
			return map.findForward("load");
	    }
		/**
		 * @describe 用户评价列表页
		 */
		public ActionForward toOrderMenuList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
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
	        pageInfo.setPageSize(10);
	        String telNum = dto.get("telNum").toString();
	        List list = om.orderMenuSearch(telNum, pageInfo);
	        int size = om.getOrderMenuSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
	        request.getSession().setAttribute("firewallpageTurning",pt);       
			return map.findForward("list");
	    }
		/**
		 * @describe 信息添加,修改,删除页
		 */
		public ActionForward toOrderMenuOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			request.setAttribute("ivrlist", new ArrayList());
			om.addOrderMenu(dto);
			
			request.setAttribute("operSign", "sys.common.operSuccess");

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
			request.setAttribute("ivrlist", ivrlist);
			
			dto.set("telNum", "");
			dto.set("beginTime", "");
			dto.set("beginDate", "");
			dto.set("ivrInfo", "");
			request.setAttribute(map.getName(), dto);
			
			return map.findForward("load");
	    }
		
		public ActionForward toDelOrderMenu(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			String id = request.getParameter("id");
			request.setAttribute("list", new ArrayList());
			boolean flag = om.delOrderMenu(id);
			
			if(!flag) {
				request.setAttribute("operSign", "tuidingshibai");
				return map.findForward("load");
			}
			
			request.setAttribute("operSign", "sys.common.operSuccess");
			return map.findForward("list");
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
		
		public ActionForward getUserList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String userType = dto.get("userType").toString();
			
			List<LabelValueBean> userList = om.getUserList(userType);
			
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
		
		/**
		 * 以下几个方法为添加联系人时用到的方法
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public ActionForward add(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			// String receivers = request.getParameter("value");
			String select = request.getParameter("select");

			List l = (List) request.getSession().getAttribute("userList2");
			List<LabelValueBean> ul = (List) request.getSession().getAttribute(
					"userList");
			System.out.println("userList size is "+ul.size());
			for (int i = 0, size = ul.size(); i < size; i++) {
				LabelValueBean lvb = (LabelValueBean)ul.get(i);
				if (lvb.getValue().equals(select)) {
					l.add(lvb);
					ul.remove(lvb);
					break;
				}
			}
			request.getSession().setAttribute("userList", ul);
			request.getSession().setAttribute("userList2", l);
			return mapping.findForward("select");

		}

		public ActionForward addall(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			// String receivers = request.getParameter("value");
			List l = (List) request.getSession().getAttribute("userList2");
			List ul = (List) request.getSession().getAttribute("userList");
			l.addAll(ul);
			ul.clear();
			request.getSession().setAttribute("userList", ul);
			request.getSession().setAttribute("userList2", l);
			return mapping.findForward("select");
		}

		public ActionForward suball(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			// String receivers = request.getParameter("value");
			List l = (List) request.getSession().getAttribute("userList2");
			List ul = (List) request.getSession().getAttribute("userList");
			ul.addAll(l);
			l.clear();
			request.getSession().setAttribute("userList", ul);
			request.getSession().setAttribute("userList2", l);
			return mapping.findForward("select");
		}

		/**
		 * Sub.
		 * 
		 * @param mapping the mapping
		 * @param form the form
		 * @param request the request
		 * @param response the response
		 * 
		 * @return the action forward
		 * 
		 * @throws Exception the exception
		 */
		public ActionForward sub(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String select = request.getParameter("select");

			List<LabelValueBean> l = (List<LabelValueBean>) request.getSession().getAttribute(
					"userList2");
			List ul = (List) request.getSession().getAttribute("userList");
			for (int i = 0, size = l.size(); i < size; i++) {
				LabelValueBean lvb = l.get(i);
				if (lvb.getValue().equals(select)) {
					ul.add(lvb);
					l.remove(lvb);
					break;
				}
			}
			request.getSession().setAttribute("userList", ul);
			request.getSession().setAttribute("userList2", l);
			return mapping.findForward("select");

		}
		
		/**
		 * 跳转到由文本内容群呼添加页面
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward textSet(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
			return mapping.findForward("text");
		}
		
		/**
		 * 执行由文本内容群呼添加操作
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward textSetOper(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
			String userId = (String)infoMap.get("userId");
			dto.set("subid", userId);//提交人id
			
			boolean flag = om.addGroupCallBack(dto);
			
			if(flag)
				request.setAttribute("operSign", "sys.common.operSuccess");
			
			dto.set("telNum", "");
			dto.set("beginTime", "");
			dto.set("beginDate", "");
			dto.set("textContent", "");
			request.setAttribute(mapping.getName(), dto);
			return mapping.findForward("text");
		}
		
		public CallOutSetService getOm() {
			return om;
		}
		public void setOm(CallOutSetService om) {
			this.om = om;
		}
	    
}
