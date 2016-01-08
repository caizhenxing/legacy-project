/**
 * 	@(#)PhoneAction.java   Nov 7, 2006 9:35:58 AM
 *	 �� 
 *	 
 */
package et.bo.pcc.phoneinfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.pcc.cclog.service.CclogService;
import et.bo.pcc.phoneinfo.PhoneInfoService;
import et.bo.pcc.policeinfo.impl.PoliceCallInInfoInMemory;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhang
 * @version Nov 7, 2006
 * @see
 */
public class PhoneAction extends BaseAction{
	
	private PhoneInfoService phoneinfo = null;

	private ClassTreeService ctree = null;
	
	private CclogService cclog = null;
	
	public CclogService getCclog() {
		return cclog;
	}

	public void setCclog(CclogService cclog) {
		this.cclog = cclog;
	}

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}

	public PhoneInfoService getPhoneinfo() {
		return phoneinfo;
	}

	public void setPhoneinfo(PhoneInfoService phoneinfo) {
		this.phoneinfo = phoneinfo;
	}
	
	/**
	 * @describe ��ת����ѯ�������ҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toPNum(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("check");
	}

	/**
	 * @describe ��龯Ա�Ƿ��ܹ�ѯ������
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward searchPNum(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String phonenum = (String)formdto.get("phonenum");
		String fuzznum = (String)formdto.get("fuzzNo");
		String password = (String)formdto.get("password");
		if (phoneinfo.checkPoliceNum(fuzznum, password)) {
			request.setAttribute("phonenum", phonenum);
			request.setAttribute("fuzznum", fuzznum);
			return map.findForward("main");
		}else{
			String check = (String)request.getParameter("checknum");
			if (check==null||check.trim().equals("")) {
				check = "1";
			}else{
				int p = Integer.parseInt(check);
				p = p + 1;
				if (p==3) {
					request.setAttribute("bey_check_num", "et.pcc.phoneinfo.check.beynum");
					return map.findForward("check");
				}
				check = ""+p;
			}
			formdto.set("checknum", check);
			request.setAttribute("checknum", check);
			request.setAttribute(map.getName(), formdto);
			request.setAttribute("error_police","et.pcc.phoneinfo.check.error");
			return map.findForward("check");
		}
	}
	
	
	/**
	 * @describe ��ת��loadҳ
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toInfoLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("tempid");
        List l = ctree.getLabelVaList("question_type");
        request.setAttribute("ctreelist", l);
		String type = request.getParameter("type");
		if (type.equals("see")) {
			IBaseDTO dto = phoneinfo.getQuestionInfo(id);
			request.setAttribute(map.getName(), dto);
			return map.findForward("see");
		}
		return map.findForward("see");
	}
	
	/**
	 * @describe ��ת��queryҳ
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toInfoQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String phonenum = request.getParameter("phonenum");
		if (phonenum.equals("00000000")) {
			phonenum = "û�����к���";
		}
		request.setAttribute("phonenum", phonenum);
		List l = ctree.getLabelVaList("question_type");
		request.setAttribute("ctreelist", l);
		String fuzznum = request.getParameter("fuzznum");
		formdto.set("fuzzNo", fuzznum);
		formdto.set("passvalidnum", "");
		formdto.set("isvalidin", "0");
		UserInfo ui = (UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		formdto.set("operator", ui.getUserName());
		String policeid = phoneinfo.saveCclog(phonenum);
		formdto.set("pid", policeid);
		phoneinfo.addPoliceCallin(formdto);
		request.setAttribute("policeid", policeid);
		request.setAttribute("fuzznum", fuzznum);
		request.setAttribute(map.getName(), formdto);
		request.setAttribute("fuzzno", phoneinfo.getPoliceInfo(fuzznum));
		return map.findForward("query");
	}
	
	
	/**
	 * @describe ��Ӿ�����Ϣ
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toAddPoliceInfo(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		List l = ctree.getLabelVaList("question_type");
		request.setAttribute("ctreelist", l);
		PoliceCallInInfoInMemory pm = new PoliceCallInInfoInMemory();
		String id = request.getParameter("id");
		String fuzznum = request.getParameter("fuzznum");
		request.setAttribute(map.getName(), formdto);
		pm.setPcid(id);
		pm.setTaginfo((String) formdto.get("taginfo"));
		pm.setQuinfo((String) formdto.get("quinfo"));
		pm.setContent((String) formdto.get("content"));
		pm.setRemark((String) formdto.get("remark"));
		phoneinfo.addPoliceCallInInfo(pm);
		request.setAttribute("policeid", id);
		request.setAttribute("fuzznum", fuzznum);
		request.setAttribute("list", phoneinfo.callInInfoIndex(id,fuzznum));
		return map.findForward("list");
	}
	
	
	/**
	 * @describe ������Ϣ�б�
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward policeInfoList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		List l = ctree.getLabelVaList("question_type");
		String id = request.getParameter("id");
		String fuzznum = request.getParameter("fuzznum");
		request.setAttribute("ctreelist", l);
		request.setAttribute(map.getName(), formdto);
		request.setAttribute("policeid", id);
		request.setAttribute("fuzznum", fuzznum);
		request.setAttribute("list", phoneinfo.callInInfoIndex(id,fuzznum));
		return map.findForward("list");
	}
	
	/**
	 * @describe ������Ա����¼��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward finishOper(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		phoneinfo.upCclog(id);
		return new ActionForward(
				"/pcc/cclog.do?method=toCclogSList");
	}
	
	/**
	 * @describe ������־��Ϣ
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward savecclog(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String phonenum = request.getParameter("phonenum");
		phoneinfo.saveCclogEnd(phonenum);
		return map.findForward("check");
	}
	
}
