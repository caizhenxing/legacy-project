package et.bo.oa.assissant.hr.action;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import et.bo.oa.assissant.hr.service.HRService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.Constants;
import excellence.common.util.file.FileUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;


public class HRAction extends BaseAction {
	
	private HRService hrService = null;
	
//	 树状结构，注入
	private ClassTreeService ctree = null;
	
	private ClassTreeService departTree = null;
	
	public ClassTreeService getCtree() {
		return ctree;
	}
	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}
	public ClassTreeService getDepartTree() {
		return departTree;
	}
	public void setDepartTree(ClassTreeService departTree) {
		this.departTree = departTree;
	}
	/**
     * <p>跳转到员工管理主柜架页面</p>
     *
     * @param info:跳转到角色管理主柜架页面
     * 
     * @return:
     * 
     * @throws
     */
	public ActionForward toHrMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
		return map.findForward("main");
    }
	/**
     * <p>跳转到角色管理主查询</p>
     *
     * @param info:跳转到员工管理主查询
     * 
     * @return:
     * 
     * @throws
     */

    
    public ActionForward toHrQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	List departLists = departTree.getLabelList("1");
		request.setAttribute("departLists",departLists);
        return map.findForward("query");
    }
    /**
     * <p>上传照片</p>
     */
    public ActionForward toDocUpload(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        return map.findForward("docUpload");
    }
    /**
     * <p>员工管理信息load</p>
     *
     * @param info:员工管理信息load
     * 
     * @return:
     * 
     * @throws
     */
    

    public ActionForward toHrLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        if(type==null)
        	type=(String)request.getAttribute("type");
        request.setAttribute("opertype",type);
        List l1=ctree.getLabelVaList("provinceType");
        request.setAttribute("provinceType",l1);
        List l2=ctree.getLabelVaList("nation");
        request.setAttribute("nationlist",l2);
        List l3=ctree.getLabelVaList("degree");
        request.setAttribute("degreelist",l3);
        List l4=ctree.getLabelVaList("polity");
        request.setAttribute("politylist",l4);
        List departLists = departTree.getLabelList("1");
		request.setAttribute("departLists",departLists);
        if (type.equals("insert")) {
        	
            return map.findForward("info");
        }
        if (type.equals("detail")) {
            String id = request.getParameter("id");
            IBaseDTO dto=hrService.getHrInfo(id);
            request.setAttribute(map.getName(),dto);
            
            request.setAttribute("eid",id);
            return map.findForward("detail");
        }
        if (type.equals("companyAddressListDetail")) {
            String id = request.getParameter("id");
            IBaseDTO dto=hrService.getHrInfo(id);
            request.setAttribute(map.getName(),dto);
            
            request.setAttribute("eid",id);
            return map.findForward("companyAddressListDetail");
        }
        if (type.equals("update")) {
            String id = request.getParameter("id");
           if(id==null)
        	   id=(String)request.getAttribute("id");
            IBaseDTO dto=hrService.getHrInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("info");
        }
        if (type.equals("delete")) {
            String id = request.getParameter("id");
            
            IBaseDTO dto=hrService.getHrInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("info");
        }
        return map.findForward("info");
    }
    /**
     * <p>员工管理信息显示</p>
     *
     * @param info:员工管理信息显示
     * 
     * @return:
     * 
     * @throws
     */
    public ActionForward toHrList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("agropageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List l = hrService.findHrInfo(formdto,pageInfo);
        int size = hrService.getHrSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("agropageTurning",pt);       
        return map.findForward("list");
    }
    
    /**
     * <p>添加员工基本信息</p>
     *
     * @param info:
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward operHr(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {
//        	
        	
        	try
        	{
            boolean flag = hrService.addHrInfo(formdto);
            request.setAttribute("idus_state","sys.addsuccess");
        	}catch(Exception e)
        	{
        		e.printStackTrace();
            	request.setAttribute("idus_state","sys.addfail.nameexists");
            	
            }
        	request.setAttribute("type", "insert");
            return map.findForward("load");
        }
        if (type.equals("update")) {
        	
        	try
        	{
        		hrService.updateHrInfo(formdto);
            request.setAttribute("idus_state","sys.updatesuccess");
        	}catch(Exception e)
        	{
            	request.setAttribute("idus_state","sys.updatefail.nameexists");
            	e.printStackTrace();
            }
            request.setAttribute("type", "update");
            return map.findForward("load");
        }
        if (type.equals("delete")) {
            
           
            try
        	{
            	 hrService.deleteHrInfo(formdto);
            	 request.setAttribute("idus_state","sys.delsuccess");
        	}catch(Exception e)
        	{
            	e.printStackTrace();
        		request.setAttribute("idus_state","sys.deletefail.nameexists");
            	
            }
            return map.findForward("load");
        }
        return map.findForward("load");
    }
    /**
     * 上传照片
     * @param
     * @version 2006-9-16
     * @return
     */
    public ActionForward upload(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
            {
    			String type=(String)request.getParameter("type");
    			
    			if(type.equals("page"))
    			return map.findForward("upload");
    			IBaseDTO dto=(IBaseDTO)form;
    			FormFile ff=(FormFile)dto.get("photo");
    			FileUtil fileUtil = new FileUtil();

    			String filename = ff.getFileName();
    			//

    			InputStream is = ff.getInputStream();
    			String id=(String)dto.get("id");
    			fileUtil.newFile(Constants.getProperty("employee_photo_realpath"), filename, is);
    			hrService.updatePhoto(id, Constants.getProperty("employee_photo_webpath")+filename);
    			//request.setAttribute("id",id);
    			request.setAttribute("path",Constants.getProperty("employee_photo_webpath")+filename);
    			return map.findForward("upload");
            }
	public HRService getHrService() {
		return hrService;
	}
	public void setHrService(HRService hrService) {
		this.hrService = hrService;
	}
}
