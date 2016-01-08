/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺Oct 25, 200711:34:00 AM
 * �ļ�����DispatchActionPlus.java
 * �����ߣ�zhaoyf
 * 
 */
package com.zyf.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.util.RequestUtils;

import com.zyf.container.ServiceProvider;
import com.zyf.exception.ExceptionInfo;
import com.zyf.web.BaseDispatchAction;
import com.zyf.web.FileuploadExceededException;
import com.zyf.web.MessageUtils;

/**
 * @author zhaoyf
 *
 */
public class DispatchActionPlus extends BaseDispatchAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request, HttpServletResponse response)
	        throws Exception {
		// TODO Auto-generated method stub
		 ActionForward forward = null;
	        String name = getName(mapping, form, request, response);
	        try {
	            validate(mapping, form, request, response);
	            forward = dispatchMethod(mapping, form, request, response, name);
	        } catch (Exception e) {
	        	/**
	        	 * ���ڵ��Խ׶Σ���ʽ����֮ǰһ��Ҫע�͵�
	        	 */
	        	e.printStackTrace();
	            ExceptionInfo info = ServiceProvider.getExceptionHandler().handle(e, this.getClass(), name);
	            String msg = "���÷���[" + name + "]��������";
	            if (logger.isInfoEnabled()) {
	                logger.info(msg, info.getThrowable());
	            }
	            /*
	             * ���� struts' action �������� input ����ʱֱ�ӷ��ص��������������ҳ��, ���û������ֱ���׳��쳣
	             */
	            MessageUtils.addErrorMessage(request, info.getErrorInformation());
	            request.setAttribute("application.exception", info.getThrowable());
	            String forwardName = getExceptionForwardName(mapping, form, request, response);
	            if (mapping.getInputForward() != null) {
	                forward = mapping.getInputForward();
	            } else if (mapping.findForward(forwardName) != null) {
	                forward = mapping.findForward(forwardName);
	            } else {
	                throw new Exception(info.getThrowable());
	            }
	            return mapping.findForward("globalMessage");
	        } finally {
	            if (form != null && form.getMultipartRequestHandler() != null) {
	                form.getMultipartRequestHandler().rollback();
	            }
	        }

	        return forward;
	}
	/**
     * <P>��֤�ڽ���<code>action</code>֮ǰ�����Ĵ���, ��Ҫ����֤������<code>ActionForm's validation</code>
     * �еĶ��ƴ���(��������<code>ActionForm's validation</code>����<code>null</code>����
     * ��<code>struts action config</code>��<code>validate=false</code>), ��������ļ���
     * �������Ĵ���ֱ���׳�</P>
     * <P>���ﲻ����ʹ�����ֻ�����֤�������<code>action config's validate=false</code>
     * �����</P>
     */
    private void validate(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        if (form == null) {
            /* ȷʵ���ڲ�����form-bean���÷�, ���ܲ��ᳫ, �������ﲻ����Ϊ�쳣���� */
            if (logger.isDebugEnabled()) {
                logger.debug("form-bean��null,����û��Ϊ[" + mapping.getPath() + "]����name����");
            }
            return;
        }
        
        BaseFormPlus theForm = (BaseFormPlus) form;
        ModuleConfig moduleConfig = RequestUtils.getModuleConfig(request, getServlet().getServletContext());

        if (theForm.getException() != null) {
            throw theForm.getException();
        } else {
            Boolean maxLengthExceeded =
                (Boolean) request.getAttribute(
                    MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
            if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue())) {
                throw new FileuploadExceededException("�ϴ��ļ���С���ܳ���[" 
                    + moduleConfig.getControllerConfig().getMaxFileSize() + "]");
            }
        }
    }
}
