package et.bo.markanainfo.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;





import et.bo.focusPursue.service.FocusPursueService;
import et.bo.markanainfo.service.MarkanainfoService;
import et.bo.priceinfo.service.PriceinfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class MarkanainfoAction extends BaseAction {

	private ClassTreeService cts=null;
	private MarkanainfoService markinfo = null;
	
		/**
		 * @describe �����г�������ҳ��
		 */
		public ActionForward toMarkanainfoMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			UserBean ub = (UserBean)request.getSession().getAttribute(SysStaticParameter.USERBEAN_IN_SESSION);
			String str_state=request.getParameter("state");
			markinfo.clearMessage(ub.getUserId(), str_state);
			request.setAttribute("state", str_state);			
			return map.findForward("toMarkanainfoMain");
	    }
		/**
		 * @describe �г�������ѯҳ
		 */
		public ActionForward toMarkanainfoQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			request.setAttribute("state", request.getParameter("state"));
			TreeControlNodeService node = cts.getNodeByNickName("variety");
			Iterator it = node.getChildren().iterator();
			List<LabelValueBean> markanainfolist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();				
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(child.getLabel());
				lvb.setValue(child.getBaseTreeNodeService().getLabel().toString());
				markanainfolist.add(lvb);
			}
			request.setAttribute("list", markanainfolist);
			return map.findForward("toMarkanainfoQuery");
	    }
		
		/**
		   * @describe �г�������ͳ������ѡ��ҳ
		   */
		public ActionForward toPopStatistic(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			return map.findForward("tomarkanainfoStatisticQuery");
		}

		/**
		   * @describe �г�������ͳ��������תAction
		   */
		public ActionForward toStatisticForwardAction(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			String type = request.getParameter("statisticType").toString();
			if(type!=null&&!"".equals(type)){
				if("ByEditor".equals(type)) return new ActionForward("/stat/markanaInfoByEditor.do?method=toMain");
				if("ByType".equals(type)) return new ActionForward("/stat/markanaInfoByType.do?method=toMain");
				if("OneEditor".equals(type)) return new ActionForward("/stat/markanaInfoByProduct.do?method=toMain");
				if("AllEditor".equals(type)) return new ActionForward("/stat/markanaInfoByProductType.do?method=toMain");
				if("ByEditorType".equals(type)) return new ActionForward("/stat/markanaInfoByEditor.do?method=toUniteMain");
			}
			return null;
		}
		
		/**
		 * @describe ҳ��Load
		 */
		public ActionForward toMarkanainfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;
			
			request.getSession().removeAttribute("oldUploadFile");
			request.getSession().removeAttribute("uploadfile");

			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	       
	        List varietyList = cts.getLabelVaList("variety");   
	        request.setAttribute("varietyList", varietyList);
	        
	        TreeControlNodeService node = cts.getNodeByNickName("variety");
			Iterator it = node.getChildren().iterator();
			List<LabelValueBean> markanainfolist = new ArrayList<LabelValueBean>();
			while(it.hasNext()){
				TreeControlNodeService child = (TreeControlNodeService)it.next();				
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(child.getLabel());
				lvb.setValue(child.getBaseTreeNodeService().getLabel().toString());
				markanainfolist.add(lvb);
			}
			request.setAttribute("list", markanainfolist);
			
	        if(type.equals("insert")){
	        	IBaseDTO dto=(IBaseDTO)form;
	        	dto.set("createTime", TimeUtil.getNowTimeSr());
				request.setAttribute(map.getName(), dto);
	        	
	        	return map.findForward("toMarkanainfoLoad");
	        }
	        if(type.equals("update")){
	        	String id = request.getParameter("id");
	        	
	        	IBaseDTO dto = markinfo.getMarkanainfo(id);
	        	request.getSession().setAttribute("oldUploadFile", dto.get("uploadfile"));//���ڱȽ�ԭ���Ƿ����ϴ��ļ���
	        	request.setAttribute(map.getName(),dto);
	        	
	        	
	        	request.setAttribute("id", id);
	        	return map.findForward("toMarkanainfoLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = markinfo.getMarkanainfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	
	        	request.setAttribute("id", id);
	        	
	        	return map.findForward("toMarkanainfoLoad");
	        }
	        if(type.equals("delete")){
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = markinfo.getMarkanainfo(id);
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	return map.findForward("toMarkanainfoLoad");
	        }
			return map.findForward("toMarkanainfoLoad");
	    }
		/**
		 * @describe �г������б�ҳ
		 */
		public ActionForward toMarkanainfoList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			List list = null;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("focusPursuepageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(19);

	        String str_state = request.getParameter("state");
	        if(str_state!=null&&str_state.length()>1){
	        	dto.set("state", markinfo.changeState(str_state));
	        }
	        
	        try {
	        	list = markinfo.markanainfoQuery(dto,pageInfo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	       
	        int size = markinfo.getMarkanainfoSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/cc_agro_sy/",map,request);
	        request.getSession().setAttribute("focusPursuepageTurning",pt);       
			return map.findForward("toMarkanainfoList");
	    }
		/**
		 * @describe �г��������,�޸�,ɾ��ҳ
		 */
		public ActionForward toMarkanainfo(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	        
	        List varietyList = cts.getLabelVaList("variety");   
	        request.setAttribute("varietyList", varietyList);
	        
	        
			if (type.equals("insert")) {
				try {
					Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
					String userId = (String)infoMap.get("userId");
					dto.set("subid", userId);//�ύ��id
					
					String uploadfile = (String)request.getSession().getAttribute("uploadfile");
					if(uploadfile !=null && !uploadfile.equals("")){
						dto.set("uploadfile", uploadfile);
						request.getSession().removeAttribute("uploadfile");
					}//����һ��Ҫ��dto����֮ǰ�ӣ������ܱ��浽���ݿ�
					markinfo.addMarkanainfo(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try {
					String uploadfile = (String)request.getSession().getAttribute("uploadfile");
					String oldUploadFile = (String)request.getSession().getAttribute("oldUploadFile");//��Դ��ҳ��ļ�¼ԭ�����ݿ�����ϴ��ļ�					
					if(uploadfile !=null && !uploadfile.equals("")){			//������ϴ���ϳ��ַ���
						if(oldUploadFile !=null && !oldUploadFile.equals("")){	//���ԭ�����ϴ�����ϳɴ�
							uploadfile = oldUploadFile + "," + uploadfile;
							
						}														//���ԭ��û�ϴ�����ֱ�����´�
						dto.set("uploadfile", uploadfile);
					}else{														//���û�ϴ����ԭ�����ַ���������
						dto.set("uploadfile", oldUploadFile);
					}
					request.getSession().removeAttribute("oldUploadFile");		//������session����
					request.getSession().removeAttribute("uploadfile");
					
					Map infoMap = (Map)request.getSession().getAttribute(SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
					String userId = (String)infoMap.get("userId");
					dto.set("subid", userId);//�ύ��id
					dto.set("accid", "admin");//������id
					
					boolean b=markinfo.updateMarkanainfo(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						//**********��
						List varietyList2 = cts.getLabelVaList("variety");   
				        request.setAttribute("varietyList", varietyList2);
				        TreeControlNodeService node = cts.getNodeByNickName("variety");
						Iterator it = node.getChildren().iterator();
						List<LabelValueBean> markanainfolist = new ArrayList<LabelValueBean>();
						while(it.hasNext()){
							TreeControlNodeService child = (TreeControlNodeService)it.next();				
							LabelValueBean lvb = new LabelValueBean();
							lvb.setLabel(child.getLabel());
							lvb.setValue(child.getBaseTreeNodeService().getLabel().toString());
							markanainfolist.add(lvb);
						}
						request.setAttribute("list", markanainfolist);
				        //**********
						return map.findForward("toMarkanainfoLoad");
					}
					//**********��
					List varietyList2 = cts.getLabelVaList("variety");   
			        request.setAttribute("varietyList", varietyList2);
			        TreeControlNodeService node = cts.getNodeByNickName("variety");
					Iterator it = node.getChildren().iterator();
					List<LabelValueBean> markanainfolist = new ArrayList<LabelValueBean>();
					while(it.hasNext()){
						TreeControlNodeService child = (TreeControlNodeService)it.next();				
						LabelValueBean lvb = new LabelValueBean();
						lvb.setLabel(child.getLabel());
						lvb.setValue(child.getBaseTreeNodeService().getLabel().toString());
						markanainfolist.add(lvb);
					}
					request.setAttribute("list", markanainfolist);
			        //**********
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toMarkanainfoLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					markinfo.delMarkanainfo((String)dto.get("markanaId"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toMarkanainfoLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("toMarkanainfoLoad");
	    }
		
		
		
		public ActionForward popIntersave(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			return map.findForward("popIntersave");
		}
		

		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}
		public MarkanainfoService getMarkinfo() {
			return markinfo;
		}
		public void setMarkinfo(MarkanainfoService markinfo) {
			this.markinfo = markinfo;
		}  
}
