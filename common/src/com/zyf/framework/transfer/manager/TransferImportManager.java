/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.manager;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.zyf.framework.transfer.TransferImportResult;
import com.zyf.framework.transfer.TransferImportable;
import com.zyf.framework.transfer.callbacks.TransferImportCallback;
import com.zyf.framework.transfer.callbacks.TransferImportProcessDataCallback;
import com.zyf.framework.transfer.exceptions.ImportProcessDataCallbackException;
import com.zyf.framework.transfer.exceptions.TransferException;
import com.zyf.framework.transfer.meta.FileType;
import com.zyf.framework.transfer.meta.ImportMetaData;

/**
 * <class>TransferImportManager</class> �ṩ�˵�������ʱ��Ҫ�ķ���, ʵ����Ӧ���� {@link com.zyf.framework.transfer.core.support.TransferImportDaoSupport} ������, 
 * ���е� {@link com.zyf.framework.transfer.core.TransferImportTemplate} �� {@link TransferImportManager} �еķ����ṩ�˾������֧��, ͨ���������ֻ��Ҫ�򵥵�ʵ�ֶ�
 * ������ı������ɾ�Ĳ�������
 * @see com.zyf.framework.transfer.core.support.TransferImportDaoSupport
 * @see com.zyf.framework.transfer.core.TransferImportOperations
 * @see com.zyf.framework.transfer.core.TransferImportTemplate
 * @since 2005-9-26
 * @author ����
 * @version $Id: TransferImportManager.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public interface TransferImportManager {
	
	/**
	 * �������ݷ���, �� spring �����ļ���Ӧ������������ : <p>
	 * 
	 *    &lt;prop key="importData"&gt;PROPAGATION_REQUIRES_NEW, +com.zyf.framework.transfer.exceptions.TransferException&lt;/prop&gt;
	 * 
	 * <p>
	 * ʵ����ֻ��Ҫ���� {@link com.zyf.framework.transfer.core.TransferImportOperations#importData(InputStream, FileType, Date, TransferImportCallback)}, 
	 * �ټ򵥵�ʵ���������ݹ��ܼ���
	 * 
	 * @param in the input stream
	 * @param fileType the fileType
	 * @param importDate ��������, ������ҳ��
	 * @return the TransferImportResult
	 * @throws TransferException ע��, �׳����쳣ʱ����Ӧ�� commit !!!
	 * @see com.zyf.framework.transfer.core.TransferImportOperations#importData(InputStream, FileType, Date, TransferImportCallback)
	 * @see com.zyf.framework.transfer.callbacks.TransferImportCallback
	 * @see TransferImportResult
	 * @see com.zyf.framework.transfer.TransferImportable
	 */
	TransferImportResult importData(final InputStream in, final FileType fileType, final Date importDate) throws TransferException;
	
	/**
	 * ɾ����������,  �� spring �����ļ���Ӧ������������ : <p>
	 *   
	 *   &lt;prop key="removeWrongData"&gt;PROPAGATION_REQUIRES_NEW, +com.zyf.framework.transfer.exceptions.ImportProcessDataCallbackException&lt;/prop&gt;
	 *  
	 * <p>
	 * ʵ����ֻ��Ҫ���� {@link com.zyf.framework.transfer.core.TransferImportOperations#processDataAfterImported(Serializable[], TransferImportProcessDataCallback)}, 
	 * �ټ򵥵�ʵ��ɾ�����ݹ��ܼ���
	 *   
	 * @param errorIds �� ui ��ȡ�õ�ʵ�� id ����
	 * @return ����Ķ���, ʵ�������������ҵ����Ҫ����Ϣ
	 * @throws ImportProcessDataCallbackException ע��,���׳����쳣ʱ����Ӧ�� commit !!!
	 * @see com.zyf.framework.transfer.core.TransferImportOperations#processDataAfterImported(Serializable[], TransferImportProcessDataCallback)
	 * 
	 */
	Object removeWrongData(Serializable[] errorIds) throws ImportProcessDataCallbackException;
	
	/**
	 * ȷ������,  �� spring �����ļ���Ӧ������������ : <p>
	 *   
	 *   &lt;prop key="confirmCorrectData"&gt;PROPAGATION_REQUIRES_NEW, +com.zyf.framework.transfer.exceptions.ImportProcessDataCallbackException&lt;/prop&gt;
	 *  
	 * <p>
	 * ʵ����ֻ��Ҫ���� {@link com.zyf.framework.transfer.core.TransferImportOperations#processDataAfterImported(Serializable[], TransferImportProcessDataCallback)}, 
	 * �ټ򵥵�ʵ���޸����ݹ��ܼ���
	 * @param errorIds �� ui ��ȡ�õ�ʵ�� id ����
	 *   
	 * @return ����Ķ���, ʵ�������������ҵ����Ҫ����Ϣ
	 * @throws ImportProcessDataCallbackException ע��,���׳����쳣ʱ����Ӧ�� commit !!!
	 * @see com.zyf.framework.transfer.core.TransferImportOperations#processDataAfterImported(Serializable[], TransferImportProcessDataCallback)
	 * 
	 */
	Object confirmCorrectData(Serializable[] correctIds) throws ImportProcessDataCallbackException;
	
	/**
	 * �õ���Ҫ���������, һ���Ǳ��� confirmed �ֶ�Ϊ null ������, ע�� List �е�Ԫ������Ӧ��Ϊ {@link TransferImportable} !!!
	 * @return list fill with {@link TransferImportable}
	 */
	List getRequireToBeProcessedData();
		
	/**
	 * �õ�������������Ҫ��Ԫ��Ϣ, �������ڸ�ʽ��, �ı��������Ҫ�����ʵ�����Լ����������� properteis
	 * @return the ImportMetaData
	 * @see ImportMetaData
	 */
	ImportMetaData getImportMetaData();
	
}
