/*
 * @(#)ConstKeyValueService.java	 2009-03-13
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.constKeyValue.service;
/**
 * <p>������Ϣά��</p>
 * 
 * @version 2009-03-13
 * @author wangwenquan
 */
public interface ConstKeyValueService {
	/**
	 * ���ӹ���
	 * @param type
	 * @param key
	 * @param value
	 */
	public void addConstKeyValue(String type,String key, String value);
	/**
	 * ���ӻ��޸Ĺ���
	 * @param type
	 * @param key
	 * @param value
	 * @return  int 0�ɹ� 1ʧ��
	 */
	public int addOrUpdateConstKeyValue(String type,String key, String value);
	/**
	 * �޸Ĺ���
	 * @param id
	 * @param value
	 */
	public void updateConstKeyValue(String id, String value);
	/**
	 * ��ѯ����
	 * @param type
	 * @param key
	 * @return String
	 */
	public String getConstValueByTypeKey(String type,String key);
}
