/*
 * �㽭��ѧ�������ŵ�����ҵ����Ȩ����(2006-2007),POWER BY COHEG TEAM 
 */
package com.zyf.framework.core.support.aop;

import java.util.HashSet;
import java.util.Set;

import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.beans.factory.InitializingBean;

import com.zyf.framework.authentication.service.AuthenticationService;
import com.zyf.framework.core.RequestContext;

/**
 * ʵ�ֶ�̬����{@link RequestContext}��֧����
 * @author zhangli
 * @version $Id: RequestContextIntroductionAdvisor.java,v 1.1 2007/11/05 03:16:24 yushn Exp $
 * @since 2007-6-12
 */
public class RequestContextIntroductionAdvisor extends DefaultIntroductionAdvisor implements InitializingBean {
    
    /**
     * ��������ʱ��Ӧ��<code>crosscut</code>�Ľӿڻ���
     */
    private Set eliminatedClasses;
    
    public void setEliminatedClasses(Set classes) {
        this.eliminatedClasses = classes;
    }

    public RequestContextIntroductionAdvisor() {
        super(new RequestContextIntroductionInterceptor(), RequestContext.class);
    }

    public boolean matches(Class clazz) {
        return super.matches(clazz) && !this.eliminatedClasses.contains(clazz);
    }

    public void afterPropertiesSet() throws Exception {
        this.eliminatedClasses = new HashSet(1);
        this.eliminatedClasses.add(AuthenticationService.class);
    }
}
