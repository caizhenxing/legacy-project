/**
 * @(#)EmailService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.oa.privy.addressList.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.assissant.hr.service.HRService;
import et.bo.oa.privy.addressList.service.AddressListService;
import et.bo.oa.privy.addressListSort.service.AddressListSortService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
/**
 * @describe 通讯录action
 * @author   叶浦亮
 * @version 1.0, //
 * @see
 * @see
 */
public class AddressListAction extends BaseAction {
    static Logger log = Logger.getLogger(AddressListAction.class.getName());
    
    private AddressListService addressListService = null;
    
    private AddressListSortService addressListSortService = null;
    
//  部门结构
	private ClassTreeService departTree = null;
	
	private HRService hrService = null;
	
	private ClassTreeService ctree = null;
    
    /**
	 * @describe  测试
	 * @param
	 * @return
	 * 
	 */
    public ActionForward toAddressListMain2(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("ok");
    }
    /**
	 * @describe 通讯录主页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toAddressListMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("main");
    }
	/**
	 * @describe 公司通讯录主页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toCompanyAddressListMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("compangyMain");
    }
	/**
	 * @describe 个人通讯录主页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toPersonalAddressListMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("personalMain");
    }
	/**
	 * @describe 公共通讯录主页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toCommonAddressListMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("commonMain");
    }
	/**
	 * @describe 查询页
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toAddressListQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		String addressListType = request.getParameter("addressListType");
		request.setAttribute("addressListType",addressListType);
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		String userId = ui.getUserName();
		if(addressListType.equals("company")){
			List list = addressListSortService.getLabelList(userId,"0");
			request.setAttribute("sortList",list);
			List departLists = departTree.getLabelList("1");
			request.setAttribute("departLists",departLists);
			return map.findForward("companyQuery");
		}
		if(addressListType.equals("personal")){
			List list = addressListSortService.getLabelList(userId,"1");
			request.setAttribute("sortList",list);
			return map.findForward("query");
		}
		if(addressListType.equals("common")){
			List list = addressListSortService.getLabelList(userId,"2");
			request.setAttribute("sortList",list);
			return map.findForward("query");
		}
		return map.findForward("query");
    }
	/**
	 * @describe 通讯录信息管理Load
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toAddressListLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        String addressListSign = request.getParameter("addressListSign");
        request.setAttribute("addressListSign", addressListSign);
        UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		String userId = ui.getUserName();
		String s = "flag" ;
		if(addressListSign.equals("company")){
			s = "0" ;
		}else if(addressListSign.equals("personal")){
			s = "1" ;
		}else if(addressListSign.equals("common")){
			s = "2" ;
		}
		List list = addressListSortService.getLabelList(userId,s);
		if (type.equals("insert")) {
    		request.setAttribute("sortList",list);
            return map.findForward("load");
        }
		if (type.equals("detail")) {
            String id = request.getParameter("id");
            IBaseDTO dto=addressListService.getAddressListInfo(id);           
            if(!dto.get("sort").toString().equals("")){                
            	dto.set("sort", addressListSortService.getSortNameById(dto.get("sort").toString()));
            }
            request.setAttribute(map.getName(),dto);
            return map.findForward("detail");
        }
		if (type.equals("update")) {
            String id = request.getParameter("id");
            request.setAttribute("sortList",list);
            IBaseDTO dto=addressListService.getAddressListInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
		if (type.equals("delete")) {
            String id = request.getParameter("id");
            request.setAttribute("sortList",list);
            IBaseDTO dto=addressListService.getAddressListInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
		return map.findForward("load");
    }
	/**
	 * @describe 通讯录信息显示
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toAddressListList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String pageState = null;
        PageInfo pageInfo = null;
        if(!(request.getParameter("addressListType")==null)){
            String addressListType = request.getParameter("addressListType");
		    if(addressListType.equals("company")){
			    formdto.set("sign", "0");
		    }else if(addressListType.equals("personal")){
			    formdto.set("sign", "1");
		    }else if(addressListType.equals("common")){
			    formdto.set("sign", "2");
		    }
        }
		pageState = (String) request.getParameter("pagestate");
        String page = request.getParameter("pagestop");
        UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		String userkey = ui.getUserName();
		System.out.println(userkey);
		formdto.set("userId", userkey);
        if (pageState == null && page == null) {
            pageInfo = new PageInfo();
        } else {
            PageTurning pageTurning = (PageTurning) request.getSession()
                    .getAttribute("agropageTurning");
            pageInfo = pageTurning.getPage();
            if (pageState != null)
                pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO) pageInfo.getQl();
        }
        
        pageInfo.setPageSize(10);
        List l=new ArrayList();
		try {
			l = addressListService.findAddressListInfo(formdto, pageInfo);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int size = addressListService.getAddressListSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list", l);
        PageTurning pt = new PageTurning(pageInfo, "/ETOA/", map, request);
        request.getSession().setAttribute("agropageTurning", pt);
        return map.findForward("list");
    }
	/**
	 * @describe 公司通讯录信息显示 List
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toCompanyAddressListList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String pageState = null;
        PageInfo pageInfo = null;
        log.info("company");
		pageState = (String) request.getParameter("pagestate");
        String page = request.getParameter("pagestop");
        UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		String userkey = ui.getUserName();
		formdto.set("userId", userkey);
        if (pageState == null && page == null) {
            pageInfo = new PageInfo();
        } else {
            PageTurning pageTurning = (PageTurning) request.getSession()
                    .getAttribute("agropageTurning");
            pageInfo = pageTurning.getPage();
            if (pageState != null)
                pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO) pageInfo.getQl();
        }
        
        pageInfo.setPageSize(10);
        List l = addressListService.findCompanyAddressListInfo(formdto, pageInfo);
        int size = addressListService.getAddressListSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list", l);
        PageTurning pt = new PageTurning(pageInfo, "/ETOA/", map, request);
        request.getSession().setAttribute("agropageTurning", pt);
        return map.findForward("list");
    }
	/**
	 * @describe 公司通讯录信息显示 List 没有修改删除
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toCompanyAddressListListNoModify(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String pageState = null;
        PageInfo pageInfo = null;
		pageState = (String) request.getParameter("pagestate");
        String page = request.getParameter("pagestop");
        UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		String userkey = ui.getUserName();
		formdto.set("userId", userkey);
        if (pageState == null && page == null) {
            pageInfo = new PageInfo();
        } else {
            PageTurning pageTurning = (PageTurning) request.getSession()
                    .getAttribute("agropageTurning");
            pageInfo = pageTurning.getPage();
            if (pageState != null)
                pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO) pageInfo.getQl();
        }
        
        pageInfo.setPageSize(10);
        List l = addressListService.findCompanyAddressListInfo(formdto, pageInfo);
        int size = addressListService.getAddressListSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list", l);
        PageTurning pt = new PageTurning(pageInfo, "/ETOA/", map, request);
        request.getSession().setAttribute("agropageTurning", pt);
        return map.findForward("companyList");
    }
	/**
	 * @describe 通讯录信息添加
	 * @param
	 * @return
	 * 
	 */
	public ActionForward operAddressList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO)form;
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
		request.setAttribute("opertype",type);
        String addressListSign = request.getParameter("addressListSign");
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		String userId = ui.getUserName();
		List list = addressListSortService.getLabelList(userId,"1");
		if (type.equals("insert")) {
        	formdto.set("userId", userId);
        	boolean flag = addressListService.addAddressListInfo(formdto);
            if(flag==false){
            	request.setAttribute("idus_state","sys.addfail.nameexists");
            	return map.findForward("load");
            }
            request.setAttribute("idus_state","sys.addsuccess");
            request.setAttribute("sortList",list);
            return map.findForward("load");
        }
		if (type.equals("update")) {
			formdto.set("userId", userId);
            addressListService.updateAddressListInfo(formdto);
            request.setAttribute("idus_state", "sys.updatesuccess");
            request.setAttribute("sortList",list);
            return map.findForward("load");
        }
		if (type.equals("delete")) {
			addressListService.deleteAddressListInfo(formdto);
            request.setAttribute("idus_state", "sys.delsuccess");
            request.setAttribute("sortList",list);
            return map.findForward("load");
        }
		return map.findForward("load");
    }
	
	/**
	 * @describe 公共通讯录主页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toCompanyAddressListDetail(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		    DynaActionFormDTO formdto = (DynaActionFormDTO)form;
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
		    String id = request.getParameter("id");
            IBaseDTO dto=hrService.getHrInfo(id);
            List list = new ArrayList();
            list.add(dto);
            request.setAttribute("companyDetail",list);
            return map.findForward("companyAddressListDetail");
    }
	public AddressListService getAddressListService() {
		return addressListService;
	}
	public void setAddressListService(AddressListService addressListService) {
		this.addressListService = addressListService;
	}
	public AddressListSortService getAddressListSortService() {
		return addressListSortService;
	}
	public void setAddressListSortService(
			AddressListSortService addressListSortService) {
		this.addressListSortService = addressListSortService;
	}
	public ClassTreeService getDepartTree() {
		return departTree;
	}
	public void setDepartTree(ClassTreeService departTree) {
		this.departTree = departTree;
	}
	public HRService getHrService() {
		return hrService;
	}
	public void setHrService(HRService hrService) {
		this.hrService = hrService;
	}
	public ClassTreeService getCtree() {
		return ctree;
	}
	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}
}
