/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.meta;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @since 2005-9-26
 * @author ÍõÕþ
 * @version $Id: DefaultImportMetaData.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public class DefaultImportMetaData extends DefaultTransferMetaData implements ImportMetaData, InitializingBean {
	
	private Map entityMap = new LinkedHashMap();
	
	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.isTrue(! getEntityMap().isEmpty(), " entityMap must be specified ");
	}

	/**
	 * @return Returns the entityProperties.
	 */
	public Map getEntityMap() {
		return entityMap;
	}

	/**
	 * @param entityMap The entityMap to set.
	 */
	public void setEntityMap(Map entityMap) {
		this.entityMap = entityMap;
	}

	



}
