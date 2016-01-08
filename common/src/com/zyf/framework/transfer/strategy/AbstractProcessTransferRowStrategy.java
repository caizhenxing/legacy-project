/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.strategy;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.Assert;

import com.zyf.framework.transfer.exceptions.ErrorWhenReadInputStreamException;

/**
 * @since 2005-9-28
 * @author 王政
 * @version $Id: AbstractProcessTransferRowStrategy.java,v 1.1 2007/12/08 08:17:12 lanxg Exp $
 */
public abstract class AbstractProcessTransferRowStrategy {
	
	/**
	 * 在读输入流之前检查可用性
	 * @param inputStream the inputStream
	 * @throws IllegalArgumentException if the input stream is null
	 * @throws ErrorWhenReadInputStreamException when IOException catched
	 */
	protected final void prepareImport(InputStream inputStream) throws ErrorWhenReadInputStreamException {
		Assert.notNull(inputStream, " inputStream must be specified. ");
		try {
			Assert.isTrue(inputStream.available() != 0, " inputStream has no content ");
		} catch (IOException e) {
			throw new ErrorWhenReadInputStreamException(" can't read inputstream ", e);
		}
	}
	
}
