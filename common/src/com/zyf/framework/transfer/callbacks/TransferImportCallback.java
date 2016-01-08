/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.callbacks;

import com.zyf.framework.transfer.TransferImportRow;
import com.zyf.framework.transfer.TransferImportable;


/**
 * 
 * @since 2005-9-25
 * @author ÍõÕþ
 * @version $Id: TransferImportCallback.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public interface TransferImportCallback {
	
	/**
	 * 
	 * @param transferRow
	 * @return
	 * @throws Exception
	 */
	TransferImportable doImport(TransferImportRow transferRow) throws Exception;
	
}
