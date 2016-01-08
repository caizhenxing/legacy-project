/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common.persistent.hibernate3.spi;

import com.zyf.common.CommonConstants;
import com.zyf.common.CommonServiceLocator;
import com.zyf.common.systemcode.SystemCode;
import com.zyf.common.systemcode.SystemCodeService;
import com.zyf.persistent.hibernate3.entity.CodeWrapper;
import com.zyf.persistent.hibernate3.usertype.CodeWrapperType.CodeWrapperCallbackHanler;

/**
 * This is a spi class
 * @since 2006-9-20
 * @author java2enterprise
 * @version $Id: ReformCodeWrapperCallbackHandler.java,v 1.3 2007/12/17 11:02:38 lanxg Exp $
 */
public class ReformCodeWrapperCallbackHandler implements CodeWrapperCallbackHanler {

	public static final String SERVICE_NAME = CommonConstants.MODULE_NAME + ".persistent.reformCodeWrapperCallbackHandler";
	
	public void afterNullSafeGet(CodeWrapper codeWrapperLoaded)
		throws Throwable {
		
		SystemCodeService systemCodeService = CommonServiceLocator.getSystemCodeService();
		String[] moduleNames = systemCodeService.getRegisteredSystemModuleNames();
		
		for (int i = 0; i < moduleNames.length; i++) {
			SystemCode systemCode = systemCodeService.load(moduleNames[i], codeWrapperLoaded.getCode());
			if (systemCode == null) {
				continue;
			}
			codeWrapperLoaded.setName(systemCode.getName());
			codeWrapperLoaded.setDescription(systemCode.getDescription());
			break;
		}		
	}

}
