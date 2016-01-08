package et.bo.pcc.cclog.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.pcc.cclog.service.CclogService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class CclogAction extends BaseAction {
    static Logger log = Logger.getLogger(CclogAction.class.getName());
    
    private CclogService cclogService = null;
    
    private ClassTreeService cts = null;
    
    private ClassTreeService depTree=null;
   
	/**
	 * @describe 来电防火墙主页面
	 */
	public ActionForward toCclogMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("main");
    }
	/**
	 * @describe 查询页
	 */
	public ActionForward toCclogQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		List dl =depTree.getLabelVaList("1");
//		List department = cts.getLabelList("department_type");
		request.setAttribute("dl", dl);
		List l = cts.getLabelVaList("errorinhale");
        request.setAttribute("errorphone", l);
        List l1 = cts.getLabelVaList("question_type");
        request.setAttribute("ctreelist", l1);
		return map.findForward("query");
    }
	/**
	 * @describe 页面Load
	 */
	public ActionForward toCclogLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if(type.equals("detail")){
        	String id = request.getParameter("id");
        	request.setAttribute(map.getName(), cclogService.listFuzzInfo(id));
        	//IBaseDTO dto = cclogService.getCclogInfo(id);
        	//request.setAttribute("", cclogService.listPhoneInfo(id));
        	//System.out.println("map "+map.getName());
        	request.setAttribute("list", cclogService.listPhoneInfo(id));
        	//request.setAttribute(map.getName(), dto);
        	return map.findForward("load");
        }
		return map.findForward("load");
    }
	/**
	 * @describe 防火墙规则列表页
	 */
	public ActionForward toCclogList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		IBaseDTO dto = (IBaseDTO)form;
		String pageState = null;
        PageInfo pageInfo = null;
        pageInfo=PageInfo.operPara(request,"cclogpageTurning", 10, dto);
		//String type=(String)dto.get("type");
		if(pageInfo.getQl()!=null)
			dto=(IBaseDTO)pageInfo.getQl();
        List list = new ArrayList();
        try {
			list = cclogService.queryCclog(dto, pageInfo);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
        int size = cclogService.getCclogSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list", list);
        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
        request.getSession().setAttribute("cclogpageTurning",pt);       
		return map.findForward("list");
    }
	
	/**
	 * @describe cclog登陆后列表页信息
	 */
	public ActionForward toCclogSList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("cclogpageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        String begintime = (String)request.getSession().getAttribute(SysStaticParameter.LOGIN_TIME_FOR_SELECT);
        UserInfo ui = (UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
        String operatornum = ui.getUserName();
        List list = cclogService.queryCcLogInfo(dto, pageInfo,begintime,operatornum);
        int size = cclogService.getCcLogInfoSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list", list);
        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
        request.getSession().setAttribute("cclogpageTurning",pt);       
		return map.findForward("slist");
    }
	
	
	/**
	 * @describe 部门main页
	 */
	public ActionForward toDepMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("depmain");
    }
	
	/**
	 * @describe 部门query页
	 */
	public ActionForward toDepQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("depquery");
    }
	
	/**
	 * 得到部门信息列表
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toDepList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		request.setAttribute("deplist", cclogService.getDepInfo(dto));
		return map.findForward("deplist");
	}
	
    
    public CclogService getcclogService() {
		return cclogService;
	}
	public void setcclogService(CclogService cclogService) {
		this.cclogService = cclogService;
	}
	public ClassTreeService getCts() {
		return cts;
	}
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	public ClassTreeService getDepTree() {
		return depTree;
	}
	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}
}
