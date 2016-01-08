/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.meta;

import com.zyf.framework.transfer.TransferRow;

/**
 * @since 2005-11-21
 * @author ÍõÕþ
 * @version $Id: TransferMetaDataFactory.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public abstract class TransferMetaDataFactory {

	public static TransferMetaData getDefaultTransferMetaData() {
		DefaultTransferMetaData metaData = new DefaultTransferMetaData();
		metaData.setDateFormat(TransferRow.DEFAULT_DATE_FORMAT);
		metaData.setTxtSeparator(TransferRow.DEFAULT_TXT_SEPARATOR);
		return metaData;
	}
	
}
