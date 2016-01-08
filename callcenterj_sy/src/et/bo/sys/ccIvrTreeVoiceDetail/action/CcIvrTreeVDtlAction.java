package et.bo.sys.ccIvrTreeVoiceDetail.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.ccIvrTreeVoiceDetail.service.CcIvrTreeVDtlService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.ivr.service.IvrClassTreeService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class CcIvrTreeVDtlAction extends BaseAction {
	private ClassTreeService cts=null;
	private CcIvrTreeVDtlService opis = null;
	
		/**
		 * @describe 进入市场价格主页面
		 */
		public ActionForward toCcIvrTreeinfoMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			String addTreeInfoId = request.getParameter("addTreeInfoId");
			if(addTreeInfoId!=null)
			{
				request.getSession().removeAttribute("addTreeInfoId");
				request.getSession().setAttribute("addTreeInfoId", addTreeInfoId);
			}
			return map.findForward("toCcIvrTreeinfoMain");
	    }
		/**
		 * @describe 市场价格查询页
		 */
		public ActionForward toCcIvrTreeinfoQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			List<LabelValueBean> ivrLVList = opis.getIvrLVList();
			request.setAttribute("IVRLVList", ivrLVList);
			return map.findForward("toCcIvrTreeinfoQuery");
	    }
		/**
		 * @describe 页面Load
		 */
		public ActionForward toCcIvrTreeinfoLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			List<LabelValueBean> ivrLVList = opis.getIvrLVList();
			request.setAttribute("IVRLVList", ivrLVList);
//			for(int i=0; i<ivrLVList.size(); i++)
//			{
//				LabelValueBean lv = ivrLVList.get(i);
//				System.out.println(lv.getValue()+":"+lv.getLabel());
//			}
			DynaActionFormDTO dbd = (DynaActionFormDTO)form;

			String type = request.getParameter("type");
			
	        request.setAttribute("opertype",type);
	       
	        //
	        if(type.equals("inserttext")){
	        	//传过来的树id也是Cc_Ivr_tree_Info主键
	        	IBaseDTO dto=(IBaseDTO)form;
	        	dto.set("treeId", request.getParameter("treeId"));
	        	//System.out.println("***** "+request.getParameter("treeId"));
	        	String id=request.getParameter("treeId");
	        	String name="";
				String ivrtype="";
				if(id!=null&&!"".equals(id)){
					name=cts.getLabelById(id);
		        	ivrtype=cts.getNickNameById(id);
				}
				
	        	dto.set("name", name);
	        	dto.set("ivrtype", ivrtype);
	        	//System.out.println("name: "+name);
	        	//System.out.println("ivrtype: "+ivrtype);
	        	request.setAttribute(map.getName(), dto);
	        	return map.findForward("toCcIvrTreeinfoLoadText");
	        }
	        
	        if(type.equals("insert")){
	        	//传过来的树id也是Cc_Ivr_tree_Info主键
	        	IBaseDTO dto=(IBaseDTO)form;
	        	dto.set("treeId", request.getParameter("treeId"));
	        	//System.out.println("***** "+request.getParameter("treeId"));
	        	String id=request.getParameter("treeId");
	        	String name="";
				String ivrtype="";
				if(id!=null&&!"".equals(id)){
					name=cts.getLabelById(id);
		        	ivrtype=cts.getNickNameById(id);
				}
				
	        	dto.set("name", name);
	        	dto.set("ivrtype", ivrtype);
	        	request.setAttribute(map.getName(), dto);
	        	return map.findForward("toCcIvrTreeinfoLoad");
	        }
	       
	        if(type.equals("update")){
	        	String id = request.getParameter("id");  
	        	IBaseDTO dto = opis.getCcIvrTreevoiceDetail(id);
	        	dto.set("createType", dto.get("voicetype"));
	        	String lid = cts.getIdByNickname(dto.get("ivrtype").toString());
	        	if(lid!=null){
	        		for (int i = 0; i < ivrLVList.size(); i++) {
		        		LabelValueBean lvb = ivrLVList.get(i);
		        		if(lid.equals(lvb.getValue())){
		        			dto.set("treeId", lvb.getValue());
		        		}
					}
	        	}
//	        	dto.set("treeId", request.getParameter("treeId"));
	        	request.setAttribute("id", id);
	        	request.setAttribute(map.getName(),dto);
	        	return map.findForward("toCcIvrTreeinfoLoad");
	        }
	        if(type.equals("detail")){
	        	String id = request.getParameter("id");
	        	try
	        	{
	        	IBaseDTO dto = opis.getCcIvrTreevoiceDetail(id);
	        	dto.set("createType", dto.get("voicetype"));
	        	String lid = cts.getIdByNickname(dto.get("ivrtype").toString());
	        	if(lid!=null){
	        		for (int i = 0; i < ivrLVList.size(); i++) {
		        		LabelValueBean lvb = ivrLVList.get(i);
		        		if(lid.equals(lvb.getValue())){
		        			dto.set("treeId", lvb.getValue());
		        		}
					}
	        	}
//	        	dto.set("treeId", request.getParameter("treeId"));
	        	request.setAttribute("id", id);
	        	request.setAttribute(map.getName(), dto);
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        	return map.findForward("toCcIvrTreeinfoLoad");
	        }
	        if(type.equals("delete")){//先从数据库中删除，在从物理路径下删除
	        	String id = request.getParameter("id");

	        	IBaseDTO dto = opis.getCcIvrTreevoiceDetail(id);
	        	dto.set("treeId", request.getParameter("treeId"));
	        	//System.out.println("map.getName(): "+map.getName());
	        	request.setAttribute(map.getName(), dto);
	        	request.setAttribute("id", id);
	        	return map.findForward("toCcIvrTreeinfoLoad");
	        }
			return map.findForward("toCcIvrTreeinfoLoad");
	    }
		/**
		 * @describe 市场价格列表页
		 */
		public ActionForward toOperPriceinfoList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String id = dto.get("treeId").toString();
			String name="";
			String ivrtype="";
			if(!"".equals(id)){
				name=cts.getLabelById(id);
	        	ivrtype=cts.getNickNameById(id);
			}
			
        	dto.set("name", name);
        	dto.set("ivrtype", ivrtype);
			List list = null;
			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("operpriceinfopageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(15);
	        try {
	        	list = opis.operCcIvrTreeInfoList(dto,pageInfo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        
	       
	        int size = opis.getCcIvrTreeInfoSize();
	        //System.out.println("size: " +size);
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        
	        PageTurning pt = new PageTurning(pageInfo,"/callcenterj_sy/",map,request);
	        request.getSession().setAttribute("operpriceinfopageTurning",pt);       
	
	       
	        return map.findForward("toCcIvrTreeinfoList");
	    }
		/**
		 * @describe 语音详细信息添加,修改,删除页
		 */
		public ActionForward toOperPriceinfo(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
			List<LabelValueBean> ivrLVList = opis.getIvrLVList();
			request.setAttribute("IVRLVList", ivrLVList);
	        request.setAttribute("opertype",type);
	        
	        request.getSession().removeAttribute(SysStaticParameter.IVR_TREE_INSESSION);
	        
	        if (type.equals("inserttext")) {
				try {
					//System.out.println("voicePath: "+dto.get("voicePath").toString());
					opis.addCcIvrTreeInfoText(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return map.findForward("error");
				}
			}     
	        
			if (type.equals("insert")) {
				try {
					opis.addCcIvrTreeInfo(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return map.findForward("error");
				}
			}        
			
			if (type.equals("update")){
				try { 
					boolean b=opis.updateCcIvrTreeInfo(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("toCcIvrTreeinfoLoad");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toCcIvrTreeinfoLoad");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					opis.delCcIvrTreeInfo((String)dto.get("id"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("toCcIvrTreeinfoLoad");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}	
			
			return map.findForward("toCcIvrTreeinfoLoad");
	    }
		
		
		
		public ActionForward popIntersave(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			return map.findForward("popIntersave");
		}
		
		public void setCts(IvrClassTreeService cts) {
			this.cts = cts;
		}
		public ClassTreeService getCts() {
			return cts;
		}
		public void setCts(ClassTreeService cts) {
			this.cts = cts;
		}
		public CcIvrTreeVDtlService getOpis() {
			return opis;
		}
		public void setOpis(CcIvrTreeVDtlService opis) {
			this.opis = opis;
		}
}
