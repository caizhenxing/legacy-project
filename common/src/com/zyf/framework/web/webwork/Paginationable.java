/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 * Project cardAssistant
 */
package com.zyf.framework.web.webwork;

import com.zyf.framework.dao.PaginationSupport;

/**
 * ��ҳ�ӿ�, ������Ҫ��ҳ���ܵ� action ����ʵ�ִ˽ӿ�
 * 
 * @see com.zyf.framework.dao.PaginationSupport
 * @since 2005-9-7
 * @author ����
 * @version $Id: Paginationable.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public interface Paginationable {
	
	/**
	 * �õ���ҳʵ����, ������ /common/pager.jsp ����ʾ
	 * @return ��ҳʵ����
	 */
	PaginationSupport getPaginationSupport();
	
}
