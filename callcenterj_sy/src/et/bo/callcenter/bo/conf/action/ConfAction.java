/**
 * 沈阳卓越科技有限公司
 * 2008-6-10
 */
package et.bo.callcenter.bo.conf.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.callcenter.bo.conf.ConfService;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhang feng
 * 
 */
public class ConfAction extends BaseAction {

	private ConfService cs = null;

	public ConfService getCs() {
		return cs;
	}

	public void setCs(ConfService cs) {
		this.cs = cs;
	}

	/**
	 * @describe 主页面
	 */
	public ActionForward toConfMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("main");
	}

	/**
	 * @describe 查询页
	 */
	public ActionForward toConfQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("query");
	}

	/**
	 * @describe 跳转到会议列表信息页
	 */
	public ActionForward toConfList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List list = cs.confDeployList("1");
		request.setAttribute("list", list);
		List<LabelValueBean> l = cs.getAllConfList();
		request.setAttribute("confroom", list);
		
		return map.findForward("list");
	}

	/**
	 * @describe 跳转到会议列表信息页
	 */
	public ActionForward toOper(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String id = dto.get("id").toString();
		request.setAttribute("operid", id);
		return map.findForward("oper");
	}

	/**
	 * @describe 跳转到会议列表信息页
	 */
	public ActionForward operConf(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		String state = request.getParameter("state");
		
		request.setAttribute("operid", id);

		// 使发音
		if (state.equals("1")) {
			cs.operConf(id, state);
			request.setAttribute("operSign", "sys.common.operSuccess");
			return map.findForward("oper");
		}
		// 使哑
		if (state.equals("2")) {
			cs.operConf(id, state);
			request.setAttribute("operSign", "sys.common.operSuccess");
			return map.findForward("oper");
		}
		// 踢出
		if (state.equals("3")) {
			cs.operConf(id, state);
			request.setAttribute("operSign", "sys.common.operSuccess");
			return map.findForward("oper");
		}
		return map.findForward("oper");
	}
	
	/**
	 * @describe 选择不同的会议室
	 */
	public ActionForward toSelectConf(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String roomno = dto.get("roomInfo").toString();
		List list = cs.confDeployList(roomno);
		request.setAttribute("list", list);
		List<LabelValueBean> l = cs.getAllConfList();
		request.setAttribute("confroom", l);
		return map.findForward("list");
	}
	
	/**
	 * @describe 跳转到会议列表信息页(电话会议组专用)
	 */
	public ActionForward toTelConferenceList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List list = cs.confDeployList("1");
		request.setAttribute("list", list);		
		return map.findForward("TelConferenceList");
	}
}
