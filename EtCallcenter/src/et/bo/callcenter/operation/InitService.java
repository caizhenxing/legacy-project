/**
 * 	@(#)InitService.java   2007-1-22 ����03:17:42
 *	 �� 
 *	 
 */
package et.bo.callcenter.operation;

 /**
 * @author zhaoyifei
 * @version 2007-1-22
 * @see
 */
public interface InitService {

	/**
	 * ��ʼ��
	 * @param ip ��ʼ������
	 * @version 2007-1-16
	 * @return
	 */
	public abstract boolean init(InitInfo ip);
	/**
	 * ������β
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void free();
	/**
	 * �õ�ϵͳ��Ϣ
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract SysInfo getSysInfo();
	
	/**
	 * ����������ÿ100ms����һ��
	 * @param
	 * @version 2007-1-23
	 * @return
	 */
	public abstract void assist();
}
