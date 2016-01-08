/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.service;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;

import com.zyf.framework.utils.AssertUtils;

/**
 * ������������ʱ�����ǿ�ֵ, �������������{@link ServiceBaseWithNotAllowedNullParamters}
 * , ʡȥ����ÿ����������ʱ�жϲ����Ƿ�Ϊ�յĴ���
 * @author zhangli
 * @version $Id: MethodNullParametersNotAllowedAdvisor.java,v 1.1 2007/11/05 03:16:23 yushn Exp $
 * @since 2007-4-22
 */
public class MethodNullParametersNotAllowedAdvisor extends StaticMethodMatcherPointcutAdvisor
    implements MethodInterceptor, InitializingBean {

    public boolean matches(Method method, Class targetClass) {
        return method.getParameterTypes().length > 0; 
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object[] args = methodInvocation.getArguments();
        String msg = new StringBuffer(methodInvocation.getMethod().getName())
            .append("ִ��ʱ�����ǿ�ֵ").toString();
        for (int i = 0; i < args.length; i++) {
            AssertUtils.notNull(args[0], msg);
        }
        return methodInvocation.proceed();
    }

    public void afterPropertiesSet() throws Exception {
        setAdvice(this);
        setOrder(1);
        setClassFilter(new ClassFilter() {
            public boolean matches(Class clazz) {
                return ServiceBaseWithNotAllowedNullParamters.class.isAssignableFrom(clazz);
            }});
    }
}
