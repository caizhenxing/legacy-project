/**
 * 
 * 制作时间：2007-8-16下午03:34:27
 * 工程名：comoon
 * 文件名：com.zyf.common.dict.webHrBasiccodeAction.java
 * 制作者：zhaoyifei
 * 
 */
package com.zyf.common.dict.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zyf.common.dict.domain.CommonBasicCode;
import com.zyf.common.dict.service.CommonBasicCodeService;
import com.zyf.common.domain.tree.FlatTreeUtils;
import com.zyf.container.ServiceProvider;
import com.zyf.framework.exception.persistent.PrimaryKeyConstraintException;
import com.zyf.struts.DispatchActionPlus;
import com.zyf.tools.MessageInfo;
import com.zyf.web.MessageUtils;



/**
 * @author zhaoyifei
 *
 */
public class CommonBasicCodeAction extends DispatchActionPlus {
	public static CommonBasicCodeService service() {
		return (CommonBasicCodeService) ServiceProvider.getService(CommonBasicCodeService.SERVICE_NAME);
	}

	public ActionForward entry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonBasicCodeForm theForm = (CommonBasicCodeForm)form;
		String parentCode = request.getParameter("parentCode");
		if (StringUtils.isBlank(parentCode)) {
			parentCode = CommonBasicCode.ROOT_NODE_CODE;
		}
		CommonBasicCode hr = service().load(parentCode);
		if(theForm.getRoot()==null)
		{
			theForm.setRoot(parentCode);
			theForm.setBean(hr);
		}
		return mapping.findForward(theForm.getStep());
	}
	
	public ActionForward tree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonBasicCodeForm theForm = (CommonBasicCodeForm)form;
		String parentCode = theForm.getParentCode();
		if (StringUtils.isBlank(parentCode)) {
			parentCode = theForm.getRoot();
		}
		CommonBasicCode hr = service().load(parentCode);
		
		List list = service().list(hr);
		response.setContentType("text/plain; charset=GBK");
		
		response.getWriter().print(FlatTreeUtils.serialize(list, false));
		return null;
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonBasicCodeForm theForm = (CommonBasicCodeForm)form;
		String parentCode = theForm.getParentCode();
		if (StringUtils.isBlank(parentCode)) {
			parentCode = CommonBasicCode.ROOT_NODE_CODE;
		}
		CommonBasicCode hr = service().load(parentCode);
		List list = service().list(hr);
//		if(list.size()==0)
//			list=service().list(hr.getParent());
		/* 用于显示所属节点信息 */
		theForm.setBean(hr);
		theForm.setList(list);
		return mapping.findForward(theForm.getStep());
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonBasicCodeForm theForm = (CommonBasicCodeForm)form;
		String id=request.getParameter("oid");
		String type=request.getParameter("type");
		if(StringUtils.isBlank(id))
			id=theForm.getOid();
		if(!"new".equals(type)&&StringUtils.isBlank(id))
		{
			id=theForm.getParent();
		if(StringUtils.isBlank(id))
			id=theForm.getParentCode();
		}
		CommonBasicCode hr;
		if(!StringUtils.isBlank(id))
		{
			if("new".equals(type))
				hr=service().newInstance(id);
			else
				hr=service().get(id);
			if(hr==null)
				hr=service().newInstance(theForm.getParent());
			
		}
		else{
			if(StringUtils.isBlank(theForm.getParent()))
				hr=service().newInstance(theForm.getParentCode());
			else
				hr=service().newInstance(theForm.getParent());
		}
		if(hr==null)
		{
			MessageUtils.addErrorMessage(request, "系统编码，不能新增");
			return mapping.findForward("globalMessage");
		}
		theForm.setBean(hr);
		return mapping.findForward(theForm.getStep());
	}

	public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonBasicCodeForm theForm = (CommonBasicCodeForm)form;
		return mapping.findForward(theForm.getStep());
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommonBasicCodeForm theForm = (CommonBasicCodeForm)form;
		String id=request.getParameter("oid");
		CommonBasicCode hr = service().load(id);
		if("1".equals(hr.getDeleteState()))
		{
		service().delete(hr);
		theForm.setStep("list");
		MessageUtils.addMessage(request, MessageInfo.factory().getMessage("HR_I043_R_0"));
		}
		else
		{
			MessageUtils.addMessage(request, "不能删除");
		}
		return list(mapping, form, request, response);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

        boolean isCreate = false;
		CommonBasicCodeForm theForm = (CommonBasicCodeForm)form;
		theForm.getBean().setParent(service().load(theForm.getParent()));
		
		if(theForm.getBean().getVersion()==null)
		{
            isCreate = true;
    		theForm.getBean().setDeleteState("1");
    		theForm.getBean().setLayerNum(new Integer(theForm.getBean().getParent().getLayerNum().intValue()+1));
		}
		if("0".equals(theForm.getBean().getDeleteState()))
		{
			MessageUtils.addMessage(request,"不允许修改此项记录");
			theForm.setStep("edit");
			return edit(mapping, form, request, response);
		}
		try {
			service().saveOrUpdate(theForm.getBean());
		} catch (PrimaryKeyConstraintException e) {
			MessageUtils.addMessage(request, MessageInfo.factory().getMessage("HR_I047_C_0"));//HR_I047_C_0 
			theForm.setStep("edit");
			return edit(mapping, form, request, response);
		}
		if (isCreate) {
            MessageUtils.addMessage(request, MessageUtils.DEFAULT_ADD_SUCCESS_MESSAGE);
        } else {
            MessageUtils.addMessage(request, MessageUtils.DEFAULT_EDIT_SUCCESS_MESSAGE);
        }
		theForm.setStep("edit");
		
		return edit(mapping, form, request, response);
	}

}
