/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.codename.impl;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import com.zyf.exception.UnexpectedException;
import com.zyf.framework.codename.AbstractModuleCodeName;
import com.zyf.framework.codename.CodeName;

/**
 * <p>����Ϊ������{@link CodeName}�еĵ�һ�ִ�������ǰ׺, ������ʱΪ��������"ģ����"ǰ׺
 * , ���Ѵ����������ʱȥ��ǰ׺. �Ա��ⲻͬ��ģ��֮������ؿ���ʱ������ɵĴ����ظ�</p>
 * <p>���޸�{@link AbstractModuleCodeNameService}ʱһ��Ҫͬʱע�������, �����������
 * ����ķ����Ĳ����ͷ���ֵ</p>
 * @author zhangli
 * @version $Id: ModuleCodeNamePrefixInterceptor.java,v 1.1 2007/11/05 03:16:26 yushn Exp $
 * @since 2007-3-30
 * @see AbstractModuleCodeNameService
 * @see AbstractModuleCodeName
 */
public class ModuleCodeNamePrefixInterceptor implements MethodInterceptor {

    /**
     * ������, ����{@link AbstractModuleCodeName}ʱ, Ϊ��������<code>module name prefix</code>,
     * ���Ҵӷ��صĽ���е�<code>code</code>ȥ��<code>module name prefix</code>
     */
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object[] arguments = methodInvocation.getArguments();
        if (arguments != null) {
            if (arguments.length == 1 && AbstractModuleCodeName.class.isInstance(arguments[0])) {
                /* save, update */
                String code = ((AbstractModuleCodeName) arguments[0]).getCode();
                ((AbstractModuleCodeName) arguments[0]).setCode(prefixCodeWithModuleName(arguments[0].getClass(), code));
            } else if (arguments.length == 2 
                && arguments[0].getClass() == Class.class 
                && arguments[1].getClass() == String.class) {
                /* list, load */
                arguments[1] = prefixCodeWithModuleName((Class) arguments[0], (String) arguments[1]); 
            }
        }
        Object ret = methodInvocation.proceed();
        if (ret != null) {
            if (AbstractModuleCodeName.class.isInstance(ret)) {
                AbstractModuleCodeName cn = (AbstractModuleCodeName) ret;
                ret = unprefixCodeWithModuleName(cn);
            } else if (List.class.isInstance(ret)) {
                CollectionUtils.transform((List) ret, unprefixTransformer);
            }
        }
        return ret;
    }
    
    /**
     * ����һ��<code>CodeName</code>, ��<code>persistent</code>ת��Ϊ<code>detached</code>
     * @param cn Ҫ���Ƶ�<code>persistent</code>����
     * @return һ��������ȫ��ͬ��ʵ��
     */
    private AbstractModuleCodeName unprefixCodeWithModuleName(AbstractModuleCodeName cn) {
        AbstractModuleCodeName ret = (AbstractModuleCodeName) cn.clone();
        String moduleName = ret.getModuleName();
        if (ret.getCode().startsWith(moduleName)) {
            ret.setCode(cn.getCode().substring(moduleName.length() + 1));
        }
        return ret;
    }
    
    /**
     * ���ݲ���<code>clazz's getModuleName()</code>Ϊ���������Ĵ��������ǰ׺
     * @param clazz {@link AbstractModuleCodeName}������
     * @param code �������<code>code</code>��<code>CodeName</code>
     * @return ������ģ������ǰ׺�Ĵ���
     */
    private String prefixCodeWithModuleName(Class clazz, String code) {
        AbstractModuleCodeName cn = null;
        try {
            cn = (AbstractModuleCodeName) clazz.newInstance();
        } catch (Exception e) {
            throw new UnexpectedException("����ʵ����" + clazz.getName(), e);
        }
        return new StringBuffer(cn.getModuleName()).append(".").append(code).toString();
    }
    
    private Transformer unprefixTransformer = new UnprefixTransformer();
    
    private class UnprefixTransformer implements Transformer {
        public Object transform(Object element) {
            AbstractModuleCodeName cn = (AbstractModuleCodeName) element;
            return unprefixCodeWithModuleName(cn);
        }
    }
}
