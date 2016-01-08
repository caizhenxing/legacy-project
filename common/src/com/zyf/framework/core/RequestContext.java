/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.core;

import com.zyf.framework.authentication.entity.Function;
import com.zyf.framework.codename.UserCodeName;
import com.zyf.persistent.filter.ConditionInfo;

/**
 * <p>ϵͳ����ӿ�, ͨ��<code>AOP</code>��ʵ��, �ṩ�����ĵ�ͨ����Ϣ, ��ǰ��¼�û�, ������
 * ����, �κ�һ��������<code>spring bean factory</code>��<code>ServiceBase</code>����
 * ������<code>cast</code>����������Ի�ȡ������Ϣ</p>
 * <p>����ڷ�����ʹ������Щ��Ϣ, ��ô��<code>junit</code>ʱ�����Եķ���ʵ��Ҫ��ʵ������ӿ�
 * ͬʱ<code>mock</code>��ʹ�õ���Ϣ</p>
 * @author zhangli
 * @version $Id: RequestContext.java,v 1.1 2007/11/05 03:16:27 yushn Exp $
 * @since 2007-6-11
 */
public interface RequestContext {
    
    /**
     * ������ǰ��¼���û�
     * @return ��ǰ��¼���û�
     */
    UserCodeName getCurrentUser();
    
    /**
     * ������ǰ�����е�ִ������
     * @return ��ǰ�����е�ִ������
     */
    ConditionInfo getConditionInfo();
    
    /**
     * ������ǰ����Ĺ���
     * @return ��ǰ����Ĺ���
     */
    Function getCurrentFunction();
}
