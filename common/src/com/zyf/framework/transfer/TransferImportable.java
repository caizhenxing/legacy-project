/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project cardAssistant
 */
package com.zyf.framework.transfer;


/**
 * ��Ҫ���빦�ܵ�ʵ����Ҫʵ�ִ˽ӿ�
 * @since 2005-9-26
 * @author ����
 * @version $Id: TransferImportable.java,v 1.1 2007/12/08 08:17:12 lanxg Exp $
 */
public interface TransferImportable {
		
	/**
	 * �õ�ʵ�� Id 
	 * @return
	 */
	String getEntityIdAsString();
	
	/**
	 * �������ݳɹ����������Ϣ
	 * @return
	 */
	Object getOdditionalInfo();
	
	/**
	 * �Ƿ���ɹ�
	 * @return whether import succeessed
	 */
	boolean isImportSuccessed();
	
	/**
	 * �����Ƿ��ɾ��
	 * @return whether the data can be removed
	 */
	boolean isCanBeRemoved();
	
	/**
	 * 
	 * @param odditionalInfo
	 */
	void setOdditionalInfo(Object odditionalInfo);
	
	/**
	 * 
	 * @param importSuccessed
	 */
	void setImportSuccessed(boolean importSuccessed);
	
	/**
	 * 
	 * @param canBeRemoved
	 */
	void setCanBeRemoved(boolean canBeRemoved);
	
}
