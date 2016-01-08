/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.strategy;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.zyf.framework.transfer.TransferImportRow;
import com.zyf.framework.transfer.exceptions.TransferException;
import com.zyf.framework.transfer.meta.ImportMetaData;
import com.zyf.framework.transfer.meta.TransferMetaData;

/**
 * <class>ProcessTransferRowStrategy</class> 是导入导出文件的策略接口, Use Gof Strategy Pattern.
 * @since 2005-9-27
 * @author 王政
 * @version $Id: ProcessTransferRowStrategy.java,v 1.1 2007/12/08 08:17:12 lanxg Exp $
 */
public interface ProcessTransferRowStrategy {
	
	/**
	 * 根据 InputStream 和 ImportMetaData 得到 TransferRows
	 * @param in the InputStream
	 * @param importMetaData the ImportMetaData
	 * @return list fill with {@link TransferImportRow}
	 * @throws TransferException if any exception throws
	 */
	List populateTransferRows(InputStream in, ImportMetaData importMetaData) throws TransferException;
	
	/**
	 * 将 TransferRows 写到 OutputStream 中
	 * @param list fill with {@link com.zyf.framework.transfer.TransferExportRow}
	 * @param transferMetaData the transferMetaData
	 * @throws TransferException if any exception throws
	 */
	void writeTransferRows2OutputStream(List exportRows, TransferMetaData transferMetaData, OutputStream out) throws TransferException;
	
}
