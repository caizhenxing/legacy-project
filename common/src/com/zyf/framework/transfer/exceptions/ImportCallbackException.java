/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.exceptions;


/**
 * @since 2005-9-25
 * @author ÍõÕþ
 * @version $Id: ImportCallbackException.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public class ImportCallbackException extends TransferException {

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = 2846647139648519083L;

	private int rowNumber;
	
	public ImportCallbackException(String message, Throwable cause, int errorRowNumber) {
		super(message, cause);
		this.rowNumber = errorRowNumber;
	}

	/**
	 * @return Returns the rowNumber.
	 */
	public int getRowNumber() {
		return rowNumber;
	}
		
	
}
