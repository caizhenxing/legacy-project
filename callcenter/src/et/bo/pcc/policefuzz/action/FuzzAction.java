/**
 * 	@(#)FuzzAction.java   Oct 9, 2006 2:19:07 PM
 *	 。 
 *	 
 */
package et.bo.pcc.policefuzz.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.pcc.policefuzz.PoliceFuzzService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhang
 * @version Oct 9, 2006
 * @see
 */
public class FuzzAction extends BaseAction{
	
	private ClassTreeService ctree = null;
	
	private PoliceFuzzService fuzz = null;
	
	private ClassTreeService depTree = null;

	public ClassTreeService getDepTree() {
		return depTree;
	}

	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}

	public PoliceFuzzService getFuzz() {
		return fuzz;
	}

	public void setFuzz(PoliceFuzzService fuzz) {
		this.fuzz = fuzz;
	}
	
	/**
	 * @describe 跳转到main页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toFuzzMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("main");
	}

	/**
	 * @describe 跳转到query页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toFuzzQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("query");
	}

	/**
	 * @describe 跳转到load页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toFuzzLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);
        List l = ctree.getLabelVaList("person_type");
        request.setAttribute("ctreelist", l);
        List ltype = depTree.getLabelVaList("1");
        request.setAttribute("dtreelist", ltype);
        //增
		if (type.equals("insert")) {
			return map.findForward("load");
		}
		//改
		if (type.equals("update")) {
			String id = request.getParameter("id");
			IBaseDTO dto = fuzz.getFuzzInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		//删
		if (type.equals("delete")) {
			String id = request.getParameter("id");
			IBaseDTO dto = fuzz.getFuzzInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("load");
		}
		//详细
		if (type.equals("see")) {
			String id = request.getParameter("id");
			IBaseDTO dto = fuzz.getFuzzInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("see");
		}
		return map.findForward("load");
	}

	/**
	 * @describe 跳转到list页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toFuzzList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("fuzzpageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List l = fuzz.fuzzIndex(formdto, pageInfo);
        int size = fuzz.getFuzzSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
        request.getSession().setAttribute("fuzzpageTurning",pt);
        return map.findForward("list");
	}
	
	/**
	 * @describe 招行添删改操作
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
    public ActionForward operFuzz(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        List l = ctree.getLabelVaList("person_type");
        request.setAttribute("ctreelist", l);
        List ltype = depTree.getLabelVaList("1");
        request.setAttribute("dtreelist", ltype);
        if (type.equals("insert")) {
        	String policeNum = formdto.get("fuzzNo").toString();
        	if (fuzz.checkPoliceNum(policeNum)) {
				request.setAttribute("errors", "et.pcc.fuzz.fuzzload.fuzzexists");
				return map.findForward("fail");
			}else{

			}
        	fuzz.addFuzzInfo(formdto);
            request.setAttribute("idus_state","sys.addsuccess");
            return map.findForward("load");
        }
        if (type.equals("update")) {
        	fuzz.updateFuzzInfo(formdto);
            request.setAttribute("idus_state","sys.updatesuccess");
            return map.findForward("load");
        }
        if (type.equals("delete")) {
        	fuzz.delFuzzInfo(formdto);
            request.setAttribute("idus_state","sys.delsuccess");
            return map.findForward("load");
        }
        return map.findForward("load");
    }
    
	/**
	 * @describe 检查警号是否存在
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
    public ActionForward toCheckPoliceNum(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return map.findForward("topolicenum");
    }
    
    
	/**
	 * @describe 检查警号是否存在
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
    public ActionForward checkPoliceNum(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	DynaActionFormDTO formdto = (DynaActionFormDTO)form;
    	String policeNum = formdto.get("fuzzNo").toString();
    	if (fuzz.checkPoliceNum(policeNum)) {
    		
    		request.setAttribute("fuzzno", fuzz.getPoliceInfo(policeNum));
    		request.getSession().setAttribute("policeNum", policeNum);
			return map.findForward("checksuccess");
		}else{
			request.setAttribute("errors", "et.pcc.fuzz.updatePoliceNum.passworderror");
			return map.findForward("fail");
		}
    }
    
	/**
	 * @describe 修改密码
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
    public ActionForward updatePassword(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	DynaActionFormDTO formdto = (DynaActionFormDTO)form;
    	String policeNum = request.getSession().getAttribute("policeNum").toString();
    	formdto.set("fuzzNo", policeNum);
    	if (fuzz.updatePoliceNum(formdto)) {
			return map.findForward("updatesuccess");
		}else{
			request.setAttribute("errors", "et.pcc.fuzz.updatePoliceNum.error");
			return map.findForward("fail");
		}
    }
    
    
	/**
	 * @describe main
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
    public ActionForward toCountMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return map.findForward("countmain");
    }
    
	/**
	 * @describe 查询信息查询页
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
    public ActionForward toCountQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List ltype = depTree.getLabelVaList("1");
        request.setAttribute("dtreelist", ltype);
    	return map.findForward("countquery");
    }
    
	/**
	 * @describe 跳转到list页面
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toCountList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("countpageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List l = fuzz.countIndex(formdto, pageInfo);
        int size = fuzz.getCountSize();
        request.setAttribute("countsize", size);
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
        request.getSession().setAttribute("countpageTurning",pt);
        return map.findForward("countlist");
	}
	
	
	/**
	 * @describe main
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
    public ActionForward outtolocal(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	DynaActionFormDTO formdto = (DynaActionFormDTO)form;
    	String filename = fuzz.getRemoateFile(formdto);
    	request.setAttribute("filename", filename);
    	return map.findForward("download");
    }

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}
}
