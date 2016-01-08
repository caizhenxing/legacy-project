package et.bo.oa.resource.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.resource.service.ResourceServiceI;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p> ��Դ���� Action </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-12
 * 
 */
public class ResourceAction extends BaseAction {

	private Log logger = LogFactory.getLog(ResourceAction.class);
	
	private ResourceServiceI resourceServiceImpl = null;
	
//	 ��״�ṹ��ע��
	private ClassTreeService ctree = null;
	
	public ResourceAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p> ��Դʹ����� ��ѯ��ҳ </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("mainPage");
	}
	
	/**
	 * <p> ��Դʹ����� ��ѯҳ </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List resourceTypes = ctree.getLabelVaList("resourceType");
		request.setAttribute("resourceType", resourceTypes);
		return mapping.findForward("resourceQuery");
	}
	
	/**
	 * <p> ��Դʹ����� ��ѯ </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toResourceList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("resourceList");
	}
	
	/**
	 * <p> ��Դ������Ϣ �Ǽ� </p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addResourceInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		resourceServiceImpl.addResource(formDto);
		return mapping.findForward("success");
	}

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}

	public ResourceServiceI getResourceServiceImpl() {
		return resourceServiceImpl;
	}

	public void setResourceServiceImpl(ResourceServiceI resourceServiceImpl) {
		this.resourceServiceImpl = resourceServiceImpl;
	}
}
