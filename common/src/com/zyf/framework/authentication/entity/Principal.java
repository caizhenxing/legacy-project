/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.authentication.entity;

import com.zyf.framework.codename.CodeName;

/**
 * һ�������{@link CodeName}, ����һ��������, �����ǲ���,��ɫ���û�, ʵ������ӿڵ���
 * ͨ������ͳһ��չ��һ��ҵ��ʵ���������, <b>�����ڰ�ȫ����</b>. ����һ���ճ̰��ſ�����
 * һ������������, ����ճ̰��ſ����Ǹ���, ��λ, �����ǲ��ż���
 * @author zhangli
 * @version $Id: Principal.java,v 1.1 2007/11/05 03:16:20 yushn Exp $
 * @since 2007-4-19
 */
public interface Principal extends CodeName {
    
    /**
     * ����һ���û�, ���ʵ���Ƿ���������, �������ͨ�����ɼܹ��������, ͬʱ��֤��������<code>null</code>
     * @param user Ҫ�жϵ��û�
     * @return ���������������<code>true</code>
     */
    boolean pass(User user);

}
