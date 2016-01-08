/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common;

/**
 * @since 2006-7-27
 * @author java2enterprise
 * @version $Id: CommonConstants.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 */
public interface CommonConstants {

	String MODULE_NAME = "common";

	/** ����ģ���Ȩ���� */
	String PERMISS_MANAGE_ADDRESS = "common.addressManager";
	
	/**��Ϣ�����ϴ���·��*/
	String MESSAGE_VFS_ROOT = MODULE_NAME + "/message/";
	/**ͨѶ����ͼƬ*/
	String ADDRESS_VFS_ROOT  = MODULE_NAME + "/addressbook/";
	/**���ŵ�ͼƬ*/
	String DEPART_VFS_ROOT  = MODULE_NAME + "/department/";

	/** �ļ�����ʱ���·������˵Ҫ����ʱ���������������û����ù����ļ���ɾ���� */
	String TMP_VFS_ROOT = "temporary/";
	
	//~ �������
	/** ������λ */
	String CODE_UNIT_TYPE = "unit";
    /** ���� */
	String CODE_ENGINERY_GROUP = "engineryGroup";
	/** רҵ */
	String CODE_SPECIALTY = "specialty";
	
}
