/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer.callbacks;

import java.io.Serializable;

/**
 * <class>TransferImportProcessDataCallback</class> �����ڵ������ݳɹ�����һЩ��������, ����ɾ���������ݺ�ȷ������
 * @since 2005-9-26
 * @author ����
 * @version $Id: TransferImportProcessDataCallback.java,v 1.1 2007/12/08 08:17:13 lanxg Exp $
 */
public interface TransferImportProcessDataCallback {
	
	/**
	 * ��������, ���ܻ���ɾ���������ݻ�ȷ������, ע������Ĳ�����һ�� id ���� id ����, ԭ�����ڴ�������ʱ���ܻ��׳��쳣, 
	 * �� {@link com.zyf.framework.transfer.core.TransferImportTemplate#processDataAfterImported(Serializable[], TransferImportProcessDataCallback)}
	 * ��Ҫ�ڴ���ÿ������ʱ catch ���쳣������Ӧ����
	 * @param id �� ui ��ȡ�õ�ʵ�� id 
	 * @throws Exception if any error happens
	 */
	void processData(Serializable id) throws Exception; 
	
}