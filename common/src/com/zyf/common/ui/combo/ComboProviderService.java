/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common.ui.combo;

import java.util.Map;

/**
 * 为前端下拉框提供支持的 Servcie
 * @since 2006-7-13
 * @author java2enterprise
 * @version $Id: ComboProviderService.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 */
public interface ComboProviderService {
	
	String SERVICE_NAME = "common.ui.comboProviderService";
	
	Map getComboData(String category);
	
}
