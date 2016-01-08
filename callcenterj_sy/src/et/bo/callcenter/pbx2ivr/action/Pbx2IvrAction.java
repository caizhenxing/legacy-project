/**
 * 沈阳卓越科技有限公司
 * 2008-5-5
 */
package et.bo.callcenter.pbx2ivr.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.callcenter.pbx2ivr.service.Pbx2IvrService;
import et.bo.common.ConstantsCommonI;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * 交换机端口号与IVR端口的对应的Action实现类 接受交换机与IVR端口对应的查询、显示、加载等页面请求
 * 
 * @author 梁云锋
 * 
 */
public class Pbx2IvrAction extends BaseAction {
	private Pbx2IvrService service;

	public Pbx2IvrService getService() {
		return service;
	}

	public void setService(Pbx2IvrService service) {
		this.service = service;
	}

	/**
	 * 跳转到交换机与IVR端口映射模块主框架页面/callcenter/pbx2ivr/pbx2ivrMain.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("main");
	}

	/**
	 * 调转到交换机与IVR端口映射模块的查询页面/callcenter/pbx2ivr/pbx2ivrQuery.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("query");
	}

	/**
	 * 查询所有已经存在的交换机到IVR的端口映射
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List<DynaBeanDTO> list = service.query();
		request.setAttribute("list", list);
		return mapping.findForward("list");
	}

	/**
	 * 加载指定端口映射信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toLoad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String operation = request.getParameter("type");
		request.setAttribute("opertype", operation);
		// 如果不是添加请求则加载选择的记录信息
		if (!ConstantsCommonI.INSERT_OPER.equals(operation)) {
			String id = request.getParameter("id");
			IBaseDTO dto = service.getPortMapInfo(id);
			request.setAttribute(mapping.getName(), dto);
		}
		return mapping.findForward("load");
	}

	/**
	 * 保存更新的端口映射信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String operation = request.getParameter("type");
		request.setAttribute("opertype", operation);
		// 添加保存操作 执行service的add方法
		if (ConstantsCommonI.INSERT_OPER.equals(operation)) {
			service.add(dto);
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		// 修改保存操作 执行service的update方法
		else if (ConstantsCommonI.UPDATE_OPER.equals(operation)) {
			service.update(dto);
			request.setAttribute(mapping.getName(), service.getPortMapInfo(dto
					.get("id").toString()));
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		// 删除保存操作 执行service的delete方法
		else if (ConstantsCommonI.DELETE_OPER.equals(operation)) {
			service.delete(dto);
			request.setAttribute("operSign", "sys.common.operSuccess");
		}
		return mapping.findForward("load");
	}
}
