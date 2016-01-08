/* 
 * �㽭��ѧ�������Ű�Ȩ����(2006-2007), Power by COHEG TEAM.
 */
package com.zyf.framework.authentication.service.impl;

import com.zyf.framework.authentication.entity.Role;
import com.zyf.framework.authentication.entity.User;
import com.zyf.framework.authentication.service.AuthenticationService;
import com.zyf.framework.codename.UserCodeName;

/**
 * ����һ������<code>BeanShell Script</code>��ʵ��, Ŀ���Ǵӱ��뼶��ȥ���԰�ȫʵ�ֵ���
 * ����, �����������ؽ�ź, ������ʵ���ǰѼܹ������<code>API</code>�������, �;��尲ȫ
 * ��ʵ�ֵ�����һ��<code>glue jar</code>, ͨ��<code>sun jar services specification</code>
 * ����������<code>springframework</code>����ʵ��ͬ����Ŀ��
 * @author zhangli
 * @version $Id: BeanShellAuthenticationService.java,v 1.1 2007/11/05 03:16:13 yushn Exp $
 * @since 2007-4-8
 */
public class BeanShellAuthenticationService extends BeanShellIntegrationService implements AuthenticationService {
    
    protected AuthenticationService getImplementation() {
        return (AuthenticationService) service;
    }

    public Role loadRole(String roleCode) {
        return getImplementation().loadRole(roleCode);
    }

    public User loadUser(String username) {
        return getImplementation().loadUser(username);
    }

    protected Class getServiceClass() {
        return AuthenticationService.class;
    }

    public UserCodeName getContextUser() {
        return getImplementation().getContextUser();
    }

    public com.zyf.core.User legacy() {
        return getImplementation().legacy();
    }
}