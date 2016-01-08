/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.strategy;

import org.springframework.util.Assert;

import com.zyf.framework.transfer.meta.FileType;

/**
 * <class>ProcessTransferRowContext</class> �� {@link com.zyf.framework.transfer.strategy.ProcessTransferRowStrategy} �� Factory
 * @see com.zyf.framework.transfer.strategy.ProcessTransferRowStrategy
 * @since 2005-9-27
 * @author ����
 * @version $Id: ProcessTransferRowContext.java,v 1.1 2007/12/08 08:17:12 lanxg Exp $
 */
public abstract class ProcessTransferRowContext {
	
	/**
	 * �����ļ����͵õ���Ӧ�Ĵ������
	 * @param fileType the filType
	 * @return the strategy
	 * @throws InternalError if the fileType object is an illegal object
	 * @see FileType
	 */
	public static ProcessTransferRowStrategy getStrategy(FileType fileType) throws InternalError {
		Assert.notNull(fileType, " fileType is required ");
		if (FileType.XLS.equals(fileType)) {
			return new XlsStrategy();
		} else if (FileType.TXT.equals(fileType)) {
			return new TxtStrategy();
		}
		throw new InternalError(fileType + " is an illegal object ! ");
	}
	
}
