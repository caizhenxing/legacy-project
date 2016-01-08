/*
 * @(#)AddressMainService.java	 2009-03-13
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.constKeyValue;

import et.bo.constKeyValue.service.ConstKeyValueService;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>ʡ���ص�ַ��Ϣά��</p>
 * 
 * @version 2009-03-13
 * @author wangwenquan
 */
public class ConstKeyValueDwr {
	public ConstKeyValueDwr()
	{
		service = (ConstKeyValueService)SpringRunningContainer.getInstance().getBean("ConstKeyValueService");
	}
	ConstKeyValueService service;
	/**
	 * ���ӹ���
	 * @param type
	 * @param key
	 * @param value
	 */
	public void addConstKeyValue(String type,String key, String value)
	{
		service.addConstKeyValue(type, key, value);
	}
	/**
	 * ���ӻ��޸Ĺ���
	 * @param type
	 * @param key
	 * @param value
	 * @return  int 0�ɹ� 1ʧ��
	 */
	public int addOrUpdateConstKeyValue(String type,String key, String value)
	{
		return service.addOrUpdateConstKeyValue(type, key, value);
	}
	/**
	 * ��ѯ����
	 * @param type
	 * @param key
	 * @return String
	 */
	public String getConstValueByTypeKey(String type,String key)
	{
		return service.getConstValueByTypeKey(type, key);
	}
}
