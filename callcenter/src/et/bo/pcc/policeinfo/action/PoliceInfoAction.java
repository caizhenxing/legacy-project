/**
 * 	@(#)PoliceInfoAction.java   Oct 10, 2006 9:09:27 AM
 *	 。 
 *	 
 */
package et.bo.pcc.policeinfo.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.callcenter.base.ConnectInfo;
import et.bo.callcenter.business.impl.PoliceCallin;
import et.bo.pcc.policeinfo.PoliceInfoService;
import et.bo.pcc.policeinfo.impl.PoliceCallInInfoInMemory;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhang
 * @version Oct 10, 2006
 * @see
 */
public class PoliceInfoAction extends BaseAction {

	private PoliceInfoService info = null;

	private ClassTreeService ctree = null;

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}

	public PoliceInfoService getInfo() {
		return info;
	}

	public void setInfo(PoliceInfoService info) {
		this.info = info;
	}

	/**
	 * @describe 查询警员警号是否存在
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
	public ActionForward searchPNum(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String policeNum = formdto.get("fuzzNo").toString();
		// if (info.checkPoliceNum(policeNum)) {
		// return map.findForward("checknumsuccess");
		// }else{
		// return map.findForward("checknumfail");
		// }
		return null;
	}

	/**
	 * @describe 跳转到main页
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
	public ActionForward toInfoMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		// if (request.getParameter("id") == null) {
		// String operatornum = "";
		// UserInfo ui
		// =(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		// operatornum = ui.getUserName();
		// info.getPoliceIdByOpNum(operatornum);
		// } else {
		// id = request.getParameter("id");
		// }
		// request.getSession().setAttribute("policeinfoid", id);

		request.setAttribute("poid", id);
		return map.findForward("main");
	}
	
	/**
	 * @describe 跳转到load页
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
	public ActionForward toInfoLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("tempid");
		//request.setAttribute("poid", id);
        List l = ctree.getLabelVaList("question_type");
        request.setAttribute("ctreelist", l);
		String type = request.getParameter("type");
		if (type.equals("see")) {
			IBaseDTO dto = info.getQuestionInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("see");
		}
		return map.findForward("see");
	}

	/**
	 * @describe 跳转到query页
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
	public ActionForward toInfoQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String poid = request.getParameter("poid");
		PoliceCallin pc = (PoliceCallin) et.bo.callcenter.business.impl.PoliceCallin
				.getCallinIdMap().get(poid);
		ConnectInfo connectInfo=null;
		Iterator it = ConnectInfo.getCurConn().keySet().iterator();
		while(it.hasNext()){
			String port =(String)it.next();
			ConnectInfo ci=(ConnectInfo)ConnectInfo.getCurConn().get(port);
			if(poid.equals(ci.getId())){
				connectInfo=ci;
				break;
			}
		}
		String phonenum = connectInfo.getPhoneNum();
		if (phonenum.equals("00000000")) {
			phonenum = "没有主叫号码";
		}
		request.setAttribute("phonenum", phonenum);
		List l = ctree.getLabelVaList("question_type");
		request.setAttribute("ctreelist", l);

		formdto.set("pid", poid);
		String fuzznum = pc.getFuzzNum();
		request.setAttribute(map.getName(), formdto);
		request.setAttribute("fuzzno", info.getPoliceInfo(fuzznum));
		return map.findForward("query");
	}

	/**
	 * @describe 添加警务信息
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
	public ActionForward toAddPoliceInfo(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		List l = ctree.getLabelVaList("question_type");
		request.setAttribute("ctreelist", l);
		PoliceCallInInfoInMemory pm = new PoliceCallInInfoInMemory();
		String pcid = (String) formdto.get("pid");
		// request.setAttribute("pid", pcid);
		formdto.set("pid", pcid);
		request.setAttribute(map.getName(), formdto);
		pm.setPcid(pcid);
		pm.setTaginfo((String) formdto.get("taginfo"));
		pm.setQuinfo((String) formdto.get("quinfo"));
		pm.setContent((String) formdto.get("content"));
		pm.setRemark((String) formdto.get("remark"));
		info.addPoliceCallInInfo(pm);
		request.setAttribute("list", info.callInInfoIndex(pcid));
		return map.findForward("list");
	}

	/**
	 * @describe 警务信息列表
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
	public ActionForward policeInfoList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		List l = ctree.getLabelVaList("question_type");
		request.setAttribute("ctreelist", l);
		String pcid = (String) formdto.get("pid");
		// request.setAttribute("pid", pcid);
		formdto.set("pid", pcid);
		request.setAttribute(map.getName(), formdto);
		request.setAttribute("list", info.callInInfoIndex(pcid));
		return map.findForward("list");
	}

	/**
	 * @describe 警务人员操作录入
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
	public ActionForward finishOper(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String pcid = (String) formdto.get("pid");
		// request.setAttribute("pid", pcid);
		formdto.set("pid", pcid);
		request.setAttribute(map.getName(), formdto);
		if (et.bo.callcenter.business.impl.PoliceCallin.getCallinIdMap()
				.containsKey(pcid)) {
			info.upTable(pcid);
			return new ActionForward(
					"/pcc/cclog.do?method=toCclogSList");
		} else {
			if (info.finishOper(pcid)) {
				return new ActionForward(
						"/pcc/cclog.do?method=toCclogSList");
			} else {
				return new ActionForward(
						"/pcc/cclog.do?method=toCclogSList");
			}
		}
	}
	
}
