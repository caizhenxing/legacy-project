/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common.ui.combo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @since 2006-7-13
 * @author java2enterprise
 * @version $Id: DefaultComboProviderService.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 */
public class DefaultComboProviderService implements ComboProviderService {

	public Map getComboData(String category) {
		Map map = new LinkedHashMap();
		map.put("1", "AAA");
		map.put("2", "BBB");
		map.put("3", "CCC");
		return map;
	}

}
