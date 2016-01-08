/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common;

//import com.zyf.common.addressbook.service.CommonBookPersonService;
//import com.coheg.common.ui.organization.service.CommonOrganizationService;
//import com.coheg.common.ui.organization.service.ExtensionalOrganizationService;
//import com.coheg.common.ui.user.service.ExtensionalUserService;
import com.zyf.common.message.service.CommonMessageService;
import com.zyf.common.systemcode.SystemCodeService;
import com.zyf.common.systemcode.spi.SystemCodeCRUDService;
import com.zyf.container.ServiceProvider;

/**
 * @since 2006-9-20
 * @author java2enterprise
 * @version $Id: CommonServiceLocator.java,v 1.3 2007/12/17 11:02:39 lanxg Exp $
 */
public abstract class CommonServiceLocator {
	
	public static SystemCodeService getSystemCodeService() {
		return (SystemCodeService) ServiceProvider.getService(SystemCodeCRUDService.SERVICE_NAME);
	}
	
	public static SystemCodeCRUDService getSystemCodeCRUDService() {
		return (SystemCodeCRUDService) ServiceProvider.getService(SystemCodeCRUDService.SERVICE_NAME);
	}
	
//	public static CommonOrganizationService getOrganizationService() {
//		return (CommonOrganizationService) ServiceProvider.getService(CommonOrganizationService.SERVICE_NAME);
//	}
//	
//	public static ExtensionalOrganizationService getExtensionalOrganizationService() {
//		return (ExtensionalOrganizationService) ServiceProvider.getService(ExtensionalOrganizationService.SERVICE_NAME);
//	}
//	
//	public static ExtensionalUserService getExtensionalUserService() {
//		return (ExtensionalUserService) ServiceProvider.getService(ExtensionalUserService.SERVICE_NAME);
//	}
	
	public static CommonMessageService getMessageService() {
		return (CommonMessageService) ServiceProvider.getService(CommonMessageService.SERVICE_NAME);
	}
	
//	public static CommonBookPersonService getBookService() {
//		return (CommonBookPersonService) ServiceProvider.getService(CommonBookPersonService.SERVICE_NAME);
//	}
	
}
