package com.zyf.web;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RequestProcessor;

import com.zyf.core.ContextInfo;
import com.zyf.framework.core.RequestContext;

/**
 * ��<code>struts's RequestProcessor</code>����չ, ���ڴ�<code>out-of-box</code>����
 * ����Ĺ���
 * @author scott
 * @since 2006-5-7
 * @version $Id: CustomRequestProcessorTemplate.java,v 1.1 2007/11/05 03:16:05 yushn Exp $
 *
 */
public class CustomRequestProcessorTemplate extends RequestProcessor {
    protected static Set executors = new LinkedHashSet(10);
    
    protected void processPopulate(
        HttpServletRequest request,
        HttpServletResponse response,
        ActionForm form,
        ActionMapping mapping) throws ServletException {
        
        passRequest2Form(form, request);
        /* Ϊ�˷�ֹ��Ѫģ�͵ĸ���, ��ʱ��������. ����Ѫģ���л�û������ʹ�������Ĳ�ѯִ��, ��Щ���ԵĲ�ѯ���Ѿ���ʼִ���� */
        ContextInfo.concealQuery();

        try {
        	super.processPopulate(request, response, form, mapping);
            
            for (Iterator it = executors.iterator(); it.hasNext(); ) {
                ((ProcessPopulateExecutor) it.next()).execute(request, response, form, mapping);
            }
        } catch (Exception e) {
        	if (BaseActionForm.class.isInstance(form)) {
                /* ���쳣�����ӳٵ� dispatch action ��, �Է���ui�쳣���� */
        		((BaseActionForm) form).setException(e);
        	} else {
        		if (ServletException.class.isInstance(e)) {
        			throw (ServletException) e;
        		}
        		throw new ServletException(e);
        	}
        }
    }
    
    /**
     * <p>��{@link RequestContext}��Ϣ����<code>request</code>, ����������, ����
     * û�о���<code>ActionServlet</code>�Ĳ�������������, ����ֱ�ӵ�<code>jsp</code>
     * ��<code>servlet</code></p>
     * <p>��ΪĿǰ��ȫʵ�ֲ�ȷ��, ����û����<code>filter</code>�н����������ǡ���Ĺ���,
     * ͬʱ����<code>RequestContext</code>ʱ����������߳�, ������ָ���ͬһ���̵߳����
     * �򵥵ظ���ͬ����Դ</p>
     */
    protected HttpServletRequest processMultipart(HttpServletRequest request) {
        HttpServletRequest req = super.processMultipart(request);
        /*
        RequestContext rc = (RequestContext) ServiceProvider.getService(RequestContextFactoryBean.SERVICE_NAME);
        req.setAttribute(RequestContextFactoryBean.SERVICE_NAME, rc);
        */
        return req;
    }

    public interface ProcessPopulateExecutor {
        /* ע��: form == null ����� */
        void execute(
            HttpServletRequest request,
            HttpServletResponse response,
            ActionForm form,
            ActionMapping mapping) throws ServletException;
    }
    
    /**
     * ע����<code>RequestProcessor's processPopulate</code>����ִ�к�Ҫִ�еĶ���, ���
     * ����Ӧ����ϵͳ����ʱ����
     * @param executor ��<code>RequestProcessor's processPopulate</code>����ִ�к�Ҫִ�еĶ���
     */
    public static void registry(ProcessPopulateExecutor executor) {
        if (executor != null) {
            executors.add(executor);
        }
    }

    private void passRequest2Form(ActionForm form, HttpServletRequest request) {
        if (form instanceof BaseActionForm) {
            ((BaseActionForm) form).request = request;
        }
    }
    
    public static void deregistry() {
        executors = null;
    }
}
