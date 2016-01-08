/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.web.webwork;

import java.util.List;

import com.zyf.framework.transfer.meta.FileType;
import com.zyf.framework.transfer.meta.TransferMetaData;

/**
 * ʹ�õ�������ʱ action ��Ҫʵ�ֵĽӿ�, ʵ�ִ˽ӿڵ� action ��ֱ��ʹ�� {@link com.zyf.framework.web.webwork.dispatcher.TransferExportResult} ������ļ����ͻ���
 * @see com.zyf.framework.web.webwork.dispatcher.TransferExportResult
 * @since 2005-9-28
 * @author ����
 * @version $Id: TransferExportable.java,v 1.1 2007/12/08 08:17:12 lanxg Exp $
 */
public interface TransferExportable {
	
	/**
	 * �õ���������
	 * @return the list fill with {@link com.zyf.framework.transfer.TransferExportRow}
	 */
	List getTransferExportRows();
	
	/**
	 * �õ�����������Ҫ��Ԫ��Ϣ
	 * @return the transferMetaData
	 */
	TransferMetaData getTransferMetaData();
	
	/**
	 * �õ��ļ�����, �˲���Ӧ���� {@link com.zyf.framework.web.webwork.converter.TransferFileTypeConverter} ת���õ�
	 * @see FileType
	 * @see com.zyf.framework.web.webwork.converter.TransferFileTypeConverter
	 * @return the FileType
	 */
	FileType getFileType();
	
}
