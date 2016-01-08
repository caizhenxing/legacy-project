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

import com.zyf.framework.transfer.meta.ImportMetaData;

/**
 * @since 2005-9-25
 * @author ÍõÕþ
 * @version $Id: TransferImportAccessor.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public abstract class TransferImportAccessor implements InitializingBean {
	
	private ImportMetaData importMetaData;
	
	/**
	 * @return Returns the importMetaData.
	 */
	public final ImportMetaData getImportMetaData() {
		return importMetaData;
	}

	/**
	 * @param importMetaData The importMetaData to set.
	 */
	public final void setImportMetaData(ImportMetaData tableMetaData) {
		this.importMetaData = tableMetaData;
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(getImportMetaData(), " importMetaData must be specified ");
	}
	
	
	
	
}
