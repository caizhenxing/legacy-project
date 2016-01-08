/*
 * 浙江大学快威集团电力事业部版权所有(2006-2007),POWER BY COHEG TEAM 
 */
package com.zyf.framework.core.support.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

import com.zyf.core.ContextInfo;
import com.zyf.framework.authentication.entity.Function;
import com.zyf.framework.codename.UserCodeName;
import com.zyf.framework.core.RequestContext;
import com.zyf.persistent.filter.ConditionInfo;

/**
 * 动态实现{@link RequestContext}的支持类
 * @author zhangli
 * @version $Id: RequestContextIntroductionInterceptor.java,v 1.1 2007/11/05 03:16:24 yushn Exp $
 * @since 2007-6-12
 */
public class RequestContextIntroductionInterceptor extends DelegatingIntroductionInterceptor implements RequestContext {

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object ret = null;
        if (methodInvocation.getMethod().getDeclaringClass() == RequestContext.class) {
            ret = methodInvocation.getMethod().invoke(this, methodInvocation.getArguments());
        } else {
            ret = methodInvocation.proceed();
        }
        return ret;
    }

    public ConditionInfo getConditionInfo() {
        return ContextInfo.getContextCondition();
    }

    public Function getCurrentFunction() {
        throw new UnsupportedOperationException("don't be implemented");
    }

    public UserCodeName getCurrentUser() {
        return ContextInfo.getContextUser();
    }
}
