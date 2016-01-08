/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.meta;

import java.text.DateFormat;

/**
 * ����������Ҫ��Ԫ��Ϣ, �������ڸ�ʽ���ı������, Ĭ��ʵ���� {@link com.zyf.framework.transfer.meta.DefaultTransferMetaData}
 * @since 2005-9-28
 * @author ����
 * @version $Id: TransferMetaData.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public interface TransferMetaData {

	/**
	 * �õ����ڸ�ʽ, ���δ����, ����ȡ {@link com.zyf.framework.transfer.TransferRow#DEFAULT_DATE_FORMAT}
	 * @return the DateFormat
	 */
	DateFormat getDateFormat();
	
	/**
	 * �õ��ı�������ֵ��ļ������, ���δ����, ����ȡ {@link com.zyf.framework.transfer.TransferRow#DEFAULT_TXT_SEPARATOR}
	 * @return the separator
	 */
	String getTxtSeparator();
	
}