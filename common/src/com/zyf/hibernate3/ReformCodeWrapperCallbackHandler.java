/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.hibernate3;

import com.zyf.common.dict.service.BasicCodeService;
import com.zyf.container.ServiceProvider;
import com.zyf.hibernate3.CodeWrapperType.CodeWrapperCallbackHanler;
import com.zyf.persistent.hibernate3.entity.CodeWrapper;


/**
 * This is a spi class
 * @since 2006-9-20
 * @author java2enterprise
 * @version $Id: ReformCodeWrapperCallbackHandler.java,v 1.1 2007/10/08 02:44:36 zhaoyf Exp $
 */
public class ReformCodeWrapperCallbackHandler implements CodeWrapperCallbackHanler  {

	
	
	public ReformCodeWrapperCallbackHandler() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void afterNullSafeGet(CodeWrapper codeWrapperLoaded)
		throws Throwable {
			
		codeWrapperLoaded.setName(service().getNameByCode(codeWrapperLoaded.getCode()));
	}
	static BasicCodeService service() {
		return (BasicCodeService) ServiceProvider.getService(BasicCodeService.SERVICE_NAME);
	}
}
