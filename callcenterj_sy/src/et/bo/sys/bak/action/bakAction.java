package et.bo.sys.bak.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.sys.bak.service.BakService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class bakAction extends BaseAction {

	private BakService bs=null;

		/**
		 * @describe 进入网站收藏主页面
		 */
		public ActionForward tobakSaveMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			return map.findForward("main");
	    }
		/**
		 * @describe 查询页
		 */
		public ActionForward tobakSaveQuery(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception{
			return map.findForward("query");
	    }
		
		/**
		 * @describe 用户列表页
		 */
		public ActionForward tobakList(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;

			String pageState = null;
	        PageInfo pageInfo = null;
	        pageState = (String)request.getParameter("pagestate");
	        if (pageState==null) {
	            pageInfo = new PageInfo();
	        }else{
	            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("bakpageTurning");
	            pageInfo = pageTurning.getPage();
	            pageInfo.setState(pageState);
	            dto = (DynaActionFormDTO)pageInfo.getQl();
	        }
	        pageInfo.setPageSize(5);
	        List list = bs.bakQuery(dto,pageInfo);
	        int size = bs.getBakSize();
	        pageInfo.setRowCount(size);
	        pageInfo.setQl(dto);
	        request.setAttribute("list", list);
	        PageTurning pt = new PageTurning(pageInfo,"/Sopho/",map,request);
	        request.getSession().setAttribute("bakpageTurning",pt);       
			return map.findForward("list");
	    }
	
		public ActionForward tobakPopQuery(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			return map.findForward("pop");
		}
		
		public ActionForward tobakImmediate(ActionMapping map, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			bs.backupSet();
			bs.startBakupImmediate();
			return new ActionForward("/bak.do?method=tobakList");
		}
		
		
		
	    public ActionForward dbtypeChange(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
	        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
	        String type = request.getParameter("type");
	        

	        
	        if(request.getParameter("btntype")==null)
	        {
	        	 request.setAttribute("opertype",type);
	        	 request.getSession().setAttribute("sessionType", type);
	        }
	        else
	        {
	        	type = request.getSession().getAttribute("sessionType").toString();
	        	 request.setAttribute("opertype", type);
	        	 request.getSession().setAttribute("sessionType", type);
	        }
	       
	        if(formdto.get("dbtype").toString().equals("day"))
	        {
	        	request.setAttribute("btntype","day");
	        }

	        if (type.equals("insert")) {

	            return map.findForward("load");
	        }
	        if (type.equals("update")) {
	            String id = request.getParameter("id");
	            IBaseDTO dto=bs.getBakInfo(id);

	            dto.set("dbtype", formdto.get("dbtype").toString());
	            
	            request.setAttribute(map.getName(),dto);
	            return map.findForward("load");
	        }
	        if (type.equals("delete")) {
	            String id = request.getParameter("id");
	            IBaseDTO dto=bs.getBakInfo(id);
	            request.setAttribute(map.getName(),dto);
	            dto.set("dbtype", formdto.get("dbtype").toString());
	            return map.findForward("load");
	        }
	        if (type.equals("detail")) {
	            String id = request.getParameter("id");
	            IBaseDTO dto=bs.getBakInfo(id);
	            request.setAttribute(map.getName(),dto);
	            dto.set("dbtype", formdto.get("dbtype").toString());
	            return map.findForward("load");
	        }
	        return map.findForward("load");
	    }
		
	    /**
	     * bak信息load
	     *
	     * @param info:角色管理信息load
	     * 
	     * @return:
	     * 
	     * @throws
	     */

	    public ActionForward tobakLoad(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
	        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
	        String type = request.getParameter("type");
	        if(request.getParameter("btntype")==null)
	        {
	        	 request.setAttribute("opertype",type);
	        	 request.getSession().setAttribute("sessionType", type);
	        }
	        else
	        {
	        	type = request.getSession().getAttribute("sessionType").toString();
	        	 request.setAttribute("opertype", type);
	        	 request.getSession().setAttribute("sessionType", type);
	        }
	       
	        if(formdto.get("dbtype").toString().equals("day"))
	        {
	        	request.setAttribute("btntype","day");
	        }
	   
	        if (type.equals("insert")) {

	            return map.findForward("load");
	        }
	        if (type.equals("update")) {
	            String id = request.getParameter("id");
	            IBaseDTO dto=bs.getBakInfo(id);

	           
	            
	            request.setAttribute(map.getName(),dto);
	            return map.findForward("load");
	        }
	        if (type.equals("delete")) {
	            String id = request.getParameter("id");
	            IBaseDTO dto=bs.getBakInfo(id);
	            request.setAttribute(map.getName(),dto);
	            return map.findForward("load");
	        }
	        if (type.equals("detail")) {
	            String id = request.getParameter("id");
	            IBaseDTO dto=bs.getBakInfo(id);
	            request.setAttribute(map.getName(),dto);
	            return map.findForward("load");
	        }
	        return map.findForward("load");
	    }
	    
		/**
		 * @describe 用户添加,修改,删除页
		 */
	    
		public ActionForward tobakOper(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO)form;
			String type = request.getParameter("type");
	        request.setAttribute("opertype",type);
	        String id = request.getParameter("id").toString();	        

//	        System.out.println(dto.get("dbtype").toString()+"sssssssssssssssssssssssss");
	        
			if (type.equals("insert")) {
				try {
					bs.addBak(dto);
					request.setAttribute("operSign", "sys.common.operSuccess");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					return map.findForward("error");
				}
			}        
			if (type.equals("update")){
				try { 
					boolean b=bs.updateBak(dto);
					if(b==true){
						request.setAttribute("operSign", "et.pcc.portCompare.samePortOrIp");
						return map.findForward("load");
					}
					request.setAttribute("operSign", "sys.common.operSuccess");
//					return map.findForward("load");
				} catch (RuntimeException e) {
//					log.error("PortCompareAction : update ERROR");
					e.printStackTrace();
					return map.findForward("error");
				}
			}
			if (type.equals("delete")){
				try {
					bs.delBak((String)dto.get("id"));
					request.setAttribute("operSign", "sys.common.operSuccess");
					return map.findForward("load");
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return map.findForward("error");
				}
			}			
			return map.findForward("load");
	    }
		
		public BakService getBs() {
			return bs;
		}
		public void setBs(BakService bs) {
			this.bs = bs;
		}
	    
}
