/*
 * @(#)confManager.java	 2008-03-09
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.confManager;
/**
 * <p>�������</p>
 * 
 * @version 2008-03-09
 * @author wangwenquan
 */
public interface ConfManagerService {
	/**
	 * ��������״̬
	 * @param id ����
	 * @param value ���ֵ 1 2
	 */
	public void setAllowFlag(String id,String value);
	/**
	 * ������״̬
	 * @param id ����
	 * @param value ���ֵ 1 2 3
	 */
	public void setNewState(String id, String value);
	/**
	 * ���õ�ǰ״̬
	 * @param id ����
	 * @param value ���ֵ 1 2 3
	 */
	public void setCurState(String id, String value);
	/**
	 * �������� ��״̬��ػ��˳����� ���Ϊ�����
	 */
	public void meetExit();
}
