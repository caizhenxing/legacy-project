/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.core.support;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.zyf.framework.transfer.meta.TransferMetaData;

/**
 * Convenient super class for Transfer export data access objects.
 * Requires a TransferMetaData to be set, providing a
 * TransferExportTemplate based on it to subclasses.
 * 
 * @since 2005-9-28
 * @author ÍõÕþ
 * @version $Id: TransferExportSupport.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public abstract class TransferExportSupport implements InitializingBean {
	
	private TransferMetaData transferMetaData;
	
	/**
	 * @return Returns the transferMetaData.
	 */
	public final TransferMetaData getTransferMetaData() {
		return transferMetaData;
	}


	/**
	 * @param transferMetaData The transferMetaData to set.
	 */
	public final void setTransferMetaData(TransferMetaData transferMetaData) {
		this.transferMetaData = transferMetaData;
	}
	

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(getTransferMetaData(), " transferMetaData or transferExportTemplate is required. ");
	}
	
}
