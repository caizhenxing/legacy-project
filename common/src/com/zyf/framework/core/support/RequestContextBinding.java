/* 
 * 浙江大学快威集团版权所有(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.core.support;

import com.zyf.framework.core.RequestContext;

/**
 * 绑定/解除{@link RequestContext}到当前执行线程
 * @author zhangli
 * @version $Id: RequestContextBinding.java,v 1.1 2007/11/05 03:16:05 yushn Exp $
 * @since 2007-5-4
 */
public interface RequestContextBinding {
    
    /**
     * 这个命名特殊的原因是这个实现分别用于应用代码检索{@link RequestContext}和架构代码
     * 绑定/解除{@link RequestContext}, 为了方便应用代码采用了倾向应用的名称
     */
    String SERVICE_NAME = "system.requestContext";
    
    boolean hasRequestContext();
    
    void bindRequestContext(RequestContext requestContext);
    
    void unbindRequestContext();

}
