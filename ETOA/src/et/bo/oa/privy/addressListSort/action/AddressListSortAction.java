/**
 * @(#)EmailService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.oa.privy.addressListSort.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.privy.addressListSort.service.AddressListSortService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
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
public class AddressListSortAction extends BaseAction {
    static Logger log = Logger.getLogger(AddressListSortAction.class.getName());
    private AddressListSortService addressListSortService = null;
    
    /**
	 * @describe  测试
	 * @param
	 * @return
	 * 
	 */
    public ActionForward toTest(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("ok");
    }
    /**
	 * @describe 类别主页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toAddressListSortMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("main");
    }
	/**
	 * @describe 个人类别主页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toPersonalAddressListSortMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("personalMain");
    }
	/**
	 * @describe 公共类别主页面
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toCommonAddressListSortMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("commonMain");
    }
	/**
	 * @describe 查询页
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toAddressListSortQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sortSet = request.getParameter("sortSet");
		request.setAttribute("sortSet",sortSet);
		return map.findForward("query");
    }
	/**
	 * @describe 类别信息管理Load
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toAddressListSortLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        String addressListSign = request.getParameter("addressListSign");
        request.setAttribute("addressListSign", addressListSign);
		if (type.equals("insert")) {
            return map.findForward("load");
        }
		if (type.equals("detail")) {
            String id = request.getParameter("id");
            IBaseDTO dto=addressListSortService.getAddressListSortInfo(id);   
            request.setAttribute(map.getName(),dto);
            return map.findForward("detail");
        }
		if (type.equals("update")) {
            String id = request.getParameter("id");
            IBaseDTO dto=addressListSortService.getAddressListSortInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
		if (type.equals("delete")) {
            String id = request.getParameter("id");
            IBaseDTO dto=addressListSortService.getAddressListSortInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
		return map.findForward("load");
    }
	/**
	 * @describe 类别信息显示
	 * @param
	 * @return
	 * 
	 */
	public ActionForward toAddressListSortList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String sortSet = request.getParameter("sortSet");
		request.setAttribute("sortSet",sortSet);
		if(formdto.get("sortMark").toString().equals("")){
			if(sortSet.equals("company")){
				formdto.set("sortMark", "0");
			}else if(sortSet.equals("personal")){
				formdto.set("sortMark", "1");
			}else if(sortSet.equals("common")){
				formdto.set("sortMark", "2");
			}
		}
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
        List l = addressListSortService.findAddressListSortInfo(formdto, pageInfo);
        int size = addressListSortService.getAddressListSortSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list", l);
        PageTurning pt = new PageTurning(pageInfo, "/ETOA/", map, request);
        request.getSession().setAttribute("agropageTurning", pt);
        return map.findForward("list");
    }
	/**
	 * @describe 类别信息添加
	 * @param
	 * @return
	 * 
	 */
	public ActionForward operAddressListSort(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO)form;
		String type = request.getParameter("type");
		request.setAttribute("opertype",type);
		String addressListSign = request.getParameter("addressListSign");
        request.setAttribute("addressListSign", addressListSign);
		UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		String userkey = ui.getUserName();
		if (type.equals("insert")) {
        	formdto.set("userId", userkey);
        	if(addressListSortService.isHaveSameName(formdto)==true){
        		request.setAttribute("idus_state","sys.addfail.nameexists");
            	return map.findForward("load");
        	}
        	boolean flag = addressListSortService.addAddressListSortInfo(formdto);           
            request.setAttribute("idus_state","sys.addsuccess");
            if(formdto.get("sortMark").equals("0")){
            	return map.findForward("main");
            }else if(formdto.get("sortMark").equals("1")){
            	return map.findForward("personalMain");
            }else if(formdto.get("sortMark").equals("2")){
            	return map.findForward("commonMain");
            }            
        }
		if (type.equals("update")) {
			formdto.set("userId", userkey);
            try {
				addressListSortService.updateAddressListSortInfo(formdto);
				request.setAttribute("idus_state", "sys.updatesuccess");
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if(formdto.get("sortMark").equals("0")){
            	return map.findForward("main");
            }else if(formdto.get("sortMark").equals("1")){
            	return map.findForward("personalMain");
            }else if(formdto.get("sortMark").equals("2")){
            	return map.findForward("commonMain");
            }           
        }
		if (type.equals("delete")) {
			try {
				boolean flag=addressListSortService.deleteAddressListSortInfo(formdto);
				if(flag==false){
					request.setAttribute("idus_state", "et.oa.privy.addressListSort.deleteFail");
				}else{
					request.setAttribute("idus_state", "sys.delsuccess");
				}
				
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				log.info("删除出错");
				e.printStackTrace();
			}
            if(formdto.get("sortMark").equals("0")){
            	return map.findForward("main");
            }else if(formdto.get("sortMark").equals("1")){
            	return map.findForward("personalMain");
            }else if(formdto.get("sortMark").equals("2")){
            	return map.findForward("commonMain");
            }           
        }
		return map.findForward("load");
    }
	public AddressListSortService getAddressListSortService() {
		return addressListSortService;
	}
	public void setAddressListSortService(
			AddressListSortService addressListSortService) {
		this.addressListSortService = addressListSortService;
	}
	
}
