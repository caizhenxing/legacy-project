/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common;

import java.util.List;

import com.zyf.core.ContextInfo;

/**
 * �йذ�ȫ�Ĺ����࣬��Ҫ���뵽����С�
 * @since 2007-4-27
 * @author pillarliu 
 * @version $Id: SecurityUtils.java,v 1.1 2007/12/17 11:02:39 lanxg Exp $
 */
public class SecurityUtils {

	/**�Ƚϵ�ǰ�û��Ƿ��� role �Ľ�ɫ*/
	public static boolean isContains(String role){
		boolean flag = false;
		List roles = ContextInfo.getCurrentUser().getRoles();		
		if(roles.contains(role)){
			flag = true;
		}
		return flag;
	}
}
