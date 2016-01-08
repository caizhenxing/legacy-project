/**
 * 
 * ����ʱ�䣺2007-8-16����02:51:55
 * ��������comoon
 * �ļ�����com.zyf.common.dict.service.implHrBasicCodeService.java
 * �����ߣ�Administrator
 * 
 */
package com.zyf.common.dict.service;

import java.util.List;

import com.zyf.common.dict.domain.CommonBasicCode;
import com.zyf.core.ServiceBase;



/**
 * @author zhaoyifei
 *
 */
public interface CommonBasicCodeService extends ServiceBase{

	static final String SERVICE_NAME = "common.CommonBasicCodeService";
	
	List list(CommonBasicCode config);

	/**
	 * ����򴴽�һ��������Ϣ
	 * @param config Ҫ����򴴽���������Ϣ
	 */
	void saveOrUpdate(CommonBasicCode config);
	
	/**
	 * ɾ��ָ����������Ϣ, �������ڵ������ӽڵ�������ɾ��
	 * @param config Ҫɾ���Ľڵ�
	 */
	void delete(CommonBasicCode config);
	
	/**
	 * ����ָ������Ľڵ�
	 * @param id Ҫ���صĽڵ����
	 * @return �ڵ���Ϣ
	 */
	CommonBasicCode load(String id);
	
	/**
	 * ����ָ������Ľڵ�
	 * @param id Ҫ���صĽڵ����
	 * @return �ڵ���Ϣ
	 */
	CommonBasicCode get(String id);
	
	/**
	 * ��ָ���Ľڵ��´����µĽڵ���Ϣ
	 * @param parentId ָ���ڵ��<code>code</code>
	 * @return �´����Ľڵ���Ϣ
	 */
	CommonBasicCode newInstance(String parentId);
}
