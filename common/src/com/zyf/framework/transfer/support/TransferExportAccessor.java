/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.support;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.zyf.framework.transfer.meta.TransferMetaData;

/**
 * @since 2005-9-28
 * @author ÍõÕþ
 * @version $Id: TransferExportAccessor.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public abstract class TransferExportAccessor implements InitializingBean {
	
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



	public void afterPropertiesSet() {
		Assert.notNull(getTransferMetaData(), " transferMetaData must be specified. ");
	}

}
