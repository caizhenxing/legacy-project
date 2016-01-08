/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common.systemcode.spi;

import java.util.List;

import com.zyf.common.systemcode.SystemCodeService;
import com.zyf.web.view.ComboSupportList;

/**
 * @since 2006-9-20
 * @author java2enterprise
 * @version $Id: AbstractSystemCodeService.java,v 1.3 2007/12/17 11:02:39 lanxg Exp $
 */
public abstract class AbstractSystemCodeService implements SystemCodeService {

	/**
	 * @see com.zyf.common.systemcode.SystemCodeService#getCodeList(java.lang.String, java.lang.String)
	 */
	public ComboSupportList getCodeList(String systemModuleName, String parentCode) {	
		List list = findByParentCode(systemModuleName, parentCode);
		ComboSupportList comboSupportList = new ComboSupportList("code", "name");
		comboSupportList.addAll(list);
		return comboSupportList;
	}

}
