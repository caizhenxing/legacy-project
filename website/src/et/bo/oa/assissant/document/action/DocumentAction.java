package et.bo.oa.assissant.document.action;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import et.bo.oa.assissant.document.service.DocumentService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class DocumentAction extends BaseAction {
	
	private DocumentService documentService = null;
	
	private ClassTreeService cts=null;
	
	public ClassTreeService getCts() {
		return cts;
	}
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	public DocumentService getDocumentService() {
		return documentService;
	}
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
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
	public ActionForward toDocMain4(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
		return map.findForward("main4");
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
	public ActionForward toDocMain3(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
		return map.findForward("main3");
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
	public ActionForward toDocMain2(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
		return map.findForward("main2");
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
	public ActionForward toDocMain(ActionMapping map, ActionForm form,
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

    
    public ActionForward toDocQuery4(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List l=cts.getLabelVaList("documentType");
    	request.setAttribute("tl",l);
        return map.findForward("query4");
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

    
    public ActionForward toDocQuery3(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List l=cts.getLabelVaList("documentType");
    	request.setAttribute("tl",l);
        return map.findForward("query3");
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

    
    public ActionForward toDocQuery2(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List l=cts.getLabelVaList("documentType");
    	request.setAttribute("tl",l);
        return map.findForward("query2");
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

    
    public ActionForward toDocQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List l=cts.getLabelVaList("documentType");
    	request.setAttribute("tl",l);
        return map.findForward("query");
    }
    /**
     * <p>跳转到角色管理主查询</p>
     *
     * @param info:跳转到审批主查询
     * 
     * @return:
     * 
     * @throws
     */

    
    public ActionForward toDocShenPi(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        return map.findForward("shenPi");
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

    public ActionForward toDocLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {
        	request.getSession().removeAttribute("uploadList");
        	List l=cts.getLabelVaList("documentType");
        	request.setAttribute("tl",l);
            return map.findForward("load");
        }
        if (type.equals("insertConferenceDoc")) {
        	
        	//TODO test 
//        	String did =request.getParameter("did");
//        	
//        	request.getSession().setAttribute("docid",did);
        	//TODO
        	request.getSession().removeAttribute("uploadList");
            return map.findForward("conferenceDocLoad");
        }
        if (type.equals("detail")) {
            String id = request.getParameter("id");
            IBaseDTO dto=documentService.getDocInfo(id);
            request.setAttribute(map.getName(),dto);
            
            request.setAttribute("eid",id);
            return map.findForward("detail");
        }
        if (type.equals("shenpi")) {
            String id = request.getParameter("id");
            IBaseDTO dto=documentService.getDocInfo(id);
            request.setAttribute(map.getName(),dto);
            
            return map.findForward("shenpi");
        }
        if (type.equals("shenpi4")) {
            String id = request.getParameter("id");
            IBaseDTO dto=documentService.getDocInfo(id);
            request.setAttribute(map.getName(),dto);
            
            return map.findForward("shenpi4");
        }
        if (type.equals("update")) {
        	request.getSession().removeAttribute("uploadList");
            String id = request.getParameter("id");
            IBaseDTO dto=documentService.getDocInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("update");
        }
        if (type.equals("delete")) {
        	request.getSession().removeAttribute("uploadList");
            String id = request.getParameter("id");
            IBaseDTO dto=documentService.getDocInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("delete");
        }
        if (type.equals("delete4")) {
        	request.getSession().removeAttribute("uploadList");
            String id = request.getParameter("id");
            IBaseDTO dto=documentService.getDocInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("delete4");
        }
        //
        //return map.findForward("load");
        return null;
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
    public ActionForward toDocList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String folderType = cts.getvaluebyId(formdto.get("folderType").toString());
    	formdto.set("folderType", folderType);
        
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
        pageInfo.setPageSize(5);
        List l = documentService.findDocInfo(formdto,pageInfo);
        int size = documentService.getDocSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("agropageTurning",pt);       
        return map.findForward("list");
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
    public ActionForward toDocList2(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String folderType = cts.getvaluebyId(formdto.get("folderType").toString());
    	formdto.set("folderType", folderType);
        
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
        pageInfo.setPageSize(5);
        List l = documentService.findDocInfo2(formdto,pageInfo);
        int size = documentService.getDocSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("agropageTurning",pt);       
        return map.findForward("list2");
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
    public ActionForward toDocList3(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String folderType = cts.getvaluebyId(formdto.get("folderType").toString());
    	formdto.set("folderType", folderType);
        
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
        pageInfo.setPageSize(5);
        List l = documentService.findDocInfo(formdto,pageInfo);
        int size = documentService.getDocSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("agropageTurning",pt);       
        return map.findForward("list3");
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
    public ActionForward toDocList4(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String folderType = cts.getvaluebyId(formdto.get("folderType").toString());
    	formdto.set("folderType", folderType);
        
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
        pageInfo.setPageSize(5);
        List l = documentService.findDocInfo4(formdto,pageInfo);
        int size = documentService.getDocSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("agropageTurning",pt);       
        return map.findForward("list4");
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

    public ActionForward operDoc(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {
        	String path = "";
        	if(request.getSession().getAttribute("uploadList")==null||request.getSession().getAttribute("uploadList")=="")
        	{
        		request.setAttribute("idus_state","sys.updatefail.upload");
        		List lt=cts.getLabelVaList("documentType");
            	request.setAttribute("tl",lt);
        		return map.findForward("load");
        	}
        	String folderType = cts.getvaluebyId(formdto.get("folderType").toString());
        	formdto.set("folderType", folderType);
        	List l = (ArrayList)request.getSession().getAttribute("uploadList");
        	request.getSession().removeAttribute("uploadList");
        	Iterator iter = l.iterator();
        	while(iter.hasNext()){
        		path =(String)iter.next();
        		formdto.set("folderId",path);
        	}
        	
            boolean flag = documentService.addDocInfo(formdto);
            if(flag==false){
            	request.setAttribute("idus_state","sys.addfail.numberexists");
            	List lt=cts.getLabelVaList("documentType");
            	request.setAttribute("tl",lt);
            	return map.findForward("load");
            }
            List lt=cts.getLabelVaList("documentType");
        	request.setAttribute("tl",lt);
            request.setAttribute("idus_state","sys.addsuccess");
            return map.findForward("load");
        }
        if (type.equals("insertConferenceDoc")) {
        	String path = "";
        	if(request.getSession().getAttribute("uploadList")==null||request.getSession().getAttribute("uploadList")=="")
        	{
        		request.setAttribute("idus_state","sys.updatefail.upload");
        		List lt=cts.getLabelVaList("documentType");
             	request.setAttribute("tl",lt);
        		return map.findForward("load");
        	}
        	List l = (ArrayList)request.getSession().getAttribute("uploadList");
        	request.getSession().removeAttribute("uploadList");
        	Iterator iter = l.iterator();
        	while(iter.hasNext()){
        		path =(String)iter.next();
        		formdto.set("folderId",path);
        	}
        	
            boolean flag = documentService.addConfenceDocInfo(formdto);
            if(flag==false){
            	request.setAttribute("idus_state","sys.addfail.numberexists");
            	List lt=cts.getLabelVaList("documentType");
             	request.setAttribute("tl",lt);
            	return map.findForward("load");
            }
            request.setAttribute("docPath",path);
            request.setAttribute("idus_state","sys.addsuccess");
            //TODO test with did
//            String did =(String)request.getSession().getAttribute("docid");
//            
//            String forwardPath ="/oa/assissant/conference/conferOper.do?method=toLoad&type=end&did="+did;
            String forwardPath ="/oa/assissant/conference/conferOper.do?method=toLoad&type=end";
//            return map.findForward("load");
            List lt=cts.getLabelVaList("documentType");
        	request.setAttribute("tl",lt);
            return new ActionForward(forwardPath);
            
        }
        if (type.equals("update")) {
        	String path = "";
        	if(request.getSession().getAttribute("uploadList")==null||request.getSession().getAttribute("uploadList")=="")
        	{
        		request.setAttribute("idus_state","sys.updatefail.upload");
        		List lt=cts.getLabelVaList("documentType");
             	request.setAttribute("tl",lt);
        		return map.findForward("load");
        	}
        	List l = (ArrayList)request.getSession().getAttribute("uploadList");
        	request.getSession().removeAttribute("uploadList");
        	Iterator iter = l.iterator();
        	while(iter.hasNext()){
        		path =(String)iter.next();
        		formdto.set("folderId",path);
        	}
        	
            documentService.updateDocInfo(formdto);
            List lt=cts.getLabelVaList("documentType");
        	request.setAttribute("tl",lt);
            request.setAttribute("idus_state","sys.updatesuccess");
            return map.findForward("load");
        }
        if (type.equals("shenpi")) {
            documentService.shenpiDocInfoSign(formdto);
            request.setAttribute("idus_state","sys.approvesuccess");
            List lt=cts.getLabelVaList("documentType");
        	request.setAttribute("tl",lt);
        	//
            return map.findForward("shenpi");
        }
        if (type.equals("shenpi4")) {
            documentService.shenpiDoc(formdto);
            request.setAttribute("idus_state","sys.approvesuccess");
            List lt=cts.getLabelVaList("documentType");
        	request.setAttribute("tl",lt);
            return map.findForward("load");
        }
        if (type.equals("delete")) {
            documentService.deleteDocInfo(formdto);
            request.setAttribute("idus_state","sys.delsuccess");
            List lt=cts.getLabelVaList("documentType");
        	request.setAttribute("tl",lt);
            return map.findForward("load");
        }
        if (type.equals("delete4")) {
            documentService.deleteDocInfo4(formdto);
            request.setAttribute("idus_state","sys.delsuccess");
            List lt=cts.getLabelVaList("documentType");
        	request.setAttribute("tl",lt);
            return map.findForward("load");
        }
        return map.findForward("load");
    }
}
