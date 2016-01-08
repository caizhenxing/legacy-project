/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.exceptions;

/**
 * @since 2005-9-28
 * @author ÍõÕþ
 * @version $Id: ErrorWhenWriteOutputStreamException.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public class ErrorWhenWriteOutputStreamException extends TransferException {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 8546376819879243494L;

	/**
	 * @param message
	 */
	public ErrorWhenWriteOutputStreamException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public ErrorWhenWriteOutputStreamException(String message, Throwable cause) {
		super(message, cause);
	}



	
	
}
