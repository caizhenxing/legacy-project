package com.zyf.framework.core.support;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.zyf.core.ContextInfo;
import com.zyf.core.ServiceBase;
import com.zyf.framework.authentication.entity.Function;
import com.zyf.framework.authentication.service.AuthenticationService;
import com.zyf.framework.codename.UserCodeName;
import com.zyf.framework.core.RequestContext;
import com.zyf.persistent.filter.ConditionInfo;

/**
 * ��ǰ��¼�û����������ĵ��ṩ��, Ҳ����{@link RequestContext}�Ĺ�����, �����������
 * <code>springframework's container</code>, �Ա���Ӧ�ó�����<code>IoC</code>�ķ�
 * ����{@link RequestContext}ʵ��, ��ҵ���߼�ʵ�ֲ���͵�ʹ�÷�����<pre>
 * public abstract class BusinessServiceImpl implements ServiceBase {
 *     
 *     public List businessMethod() {
 *         ...
 *         User user = getRequestContext().getCurrentUser();
 *         ...
 *     }
 *     
 *     protected abstract RequestContext getRequestContext();
 * } 
 * </pre>ֻҪʵ����{@link ServiceBase}��������ӿڲ����з���ǩ��<pre>
 * protected abstract RequestContext getRequestContext();</pre>������ʱ���ɻ�õ�ǰ
 * ִ�л����±����û�, ������������Ϣ��{@link RequestContext}ʵ��, ��<code>web</code>
 * �㻷���¿���ֱ�Ӵ�<code>HttpServletRequest</code>�л��{@link RequestContext}ʵ��,
 * <pre>
 * RequestContext ctx = (RequestContext) request.getAttribute(RequestContext.SERVICE_NAME);
 * </pre>����Ϊʲô������ʵ�ַ�ʽ����ϸ˵�����ܹ�����ĵ�
 * @author scott
 */
public class RequestContextFactoryBean implements FactoryBean, RequestContextBinding, DisposableBean, InitializingBean {
    
    private Log logger = LogFactory.getLog(RequestContextFactoryBean.class);
    
    private AuthenticationService authenticationService;
    
    private ThreadLocal requestContexts = new ThreadLocal();
    
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public Object getObject() throws Exception {
        return new RequestContextImpl();
    }

    public Class getObjectType() {
        return RequestContext.class;
    }

    public boolean isSingleton() {
        return false;
    }
    
    class RequestContextImpl implements RequestContext {
        private UserCodeName user;
        
        RequestContextImpl() {
            user = authenticationService.getContextUser();
        }

        public Function getCurrentFunction() {
            throw new UnsupportedOperationException("don't ever implementation");
        }

        public UserCodeName getCurrentUser() {
            return this.user;
        }

        public ConditionInfo getConditionInfo() {
            return ContextInfo.getContextCondition();
        }
    }

    public boolean hasRequestContext() {
        Map map = (Map) this.requestContexts.get();
        return map.containsKey(getKey());
    }

    public void bindRequestContext(RequestContext requestContext) {
        Map map = (Map) this.requestContexts.get();
        map.put(getKey(), requestContext);
        if (logger.isDebugEnabled()) {
            logger.debug("���߳�" + getKey() + "��" + requestContext);
        }
    }

    public void unbindRequestContext() {
        Map map = (Map) this.requestContexts.get();
        if (map != null && map.containsKey(getKey())) {
            Object o = map.remove(getKey());
            if (logger.isDebugEnabled()) {
                logger.debug("���߳�" + getKey() + "���" + o);
            }
        }
    }

    public void destroy() throws Exception {
        this.requestContexts.set(null);
        this.requestContexts = null;
    }

    public void afterPropertiesSet() throws Exception {
        this.requestContexts.set(new HashMap(64));
    }
    
    private String getKey() {
        return Thread.currentThread().getName();
    }
}
