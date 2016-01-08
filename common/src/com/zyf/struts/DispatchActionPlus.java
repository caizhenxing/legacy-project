/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：Oct 25, 200711:34:00 AM
 * 文件名：DispatchActionPlus.java
 * 制作者：zhaoyf
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
	        	 * 用于调试阶段，正式上线之前一定要注释掉
	        	 */
	        	e.printStackTrace();
	            ExceptionInfo info = ServiceProvider.getExceptionHandler().handle(e, this.getClass(), name);
	            String msg = "调用方法[" + name + "]发生错误";
	            if (logger.isInfoEnabled()) {
	                logger.info(msg, info.getThrowable());
	            }
	            /*
	             * 当在 struts' action 中配置了 input 属性时直接返回到发生错误的输入页面, 如果没有配置直接抛出异常
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
     * <P>验证在进入<code>action</code>之前发生的错误, 主要是验证发生在<code>ActionForm's validation</code>
     * 中的定制错误(这类错误从<code>ActionForm's validation</code>返回<code>null</code>或者
     * 在<code>struts action config</code>中<code>validate=false</code>), 如果是由文件上
     * 传产生的错误将直接抛出</P>
     * <P>这里不处理使用这种机制验证错误而在<code>action config's validate=false</code>
     * 的情况</P>
     */
    private void validate(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        if (form == null) {
            /* 确实存在不定义form-bean的用法, 尽管不提倡, 所以这里不能作为异常处理 */
            if (logger.isDebugEnabled()) {
                logger.debug("form-bean是null,可能没有为[" + mapping.getPath() + "]定义name属性");
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
                throw new FileuploadExceededException("上传文件大小不能超过[" 
                    + moduleConfig.getControllerConfig().getMaxFileSize() + "]");
            }
        }
    }
}
