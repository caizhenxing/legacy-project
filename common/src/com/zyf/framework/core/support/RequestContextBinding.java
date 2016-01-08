/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.core.support;

import com.zyf.framework.core.RequestContext;

/**
 * ��/���{@link RequestContext}����ǰִ���߳�
 * @author zhangli
 * @version $Id: RequestContextBinding.java,v 1.1 2007/11/05 03:16:05 yushn Exp $
 * @since 2007-5-4
 */
public interface RequestContextBinding {
    
    /**
     * ������������ԭ�������ʵ�ֱַ�����Ӧ�ô������{@link RequestContext}�ͼܹ�����
     * ��/���{@link RequestContext}, Ϊ�˷���Ӧ�ô������������Ӧ�õ�����
     */
    String SERVICE_NAME = "system.requestContext";
    
    boolean hasRequestContext();
    
    void bindRequestContext(RequestContext requestContext);
    
    void unbindRequestContext();

}
