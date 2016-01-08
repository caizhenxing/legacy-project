/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 * Project cardAssistant
 */
package com.zyf.framework.web.webwork;

import com.zyf.framework.dao.PaginationSupport;

/**
 * 翻页接口, 所有需要翻页功能的 action 必须实现此接口
 * 
 * @see com.zyf.framework.dao.PaginationSupport
 * @since 2005-9-7
 * @author 王政
 * @version $Id: Paginationable.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public interface Paginationable {
	
	/**
	 * 得到翻页实体类, 用于在 /common/pager.jsp 中显示
	 * @return 翻页实体类
	 */
	PaginationSupport getPaginationSupport();
	
}
