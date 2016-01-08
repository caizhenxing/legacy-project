/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ����ʱ�䣺2007-9-6
 * �ļ�����CommonBasicCodeInitService.java
 * �����ߣ����
 * 
 */
package com.zyf.common.basiccode.service;

import java.util.List;

import com.zyf.core.ServiceBase;

public interface CommonBasicCodeInitService extends ServiceBase {

	static final String SERVICE_NAME = "common.CommonBasicCodeInitService";
	/**
	 * ����ָ������Ľڵ�
	 * 
	 * @param id
	 *            Ҫ���صĽڵ����
	 * @return �ڵ���Ϣ
	 */
	public List load(String id);

	/**
	 * ��ʼ��ѡ�е���ϵͳ
	 * 
	 * @param subID
	 *            ��ϵͳid
	 * @return ������
	 */
	public String reset(String subID);

	/**
	 * ����ID���Ҽ�¼
	 * 
	 * @param id
	 *            Ҫ�ҵ�ID
	 * @return �ҵ��ļ�¼
	 */
	public List find(String id);

}
