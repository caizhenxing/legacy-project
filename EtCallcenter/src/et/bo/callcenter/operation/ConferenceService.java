/**
 * 	@(#)ConferenceService.java   2007-1-15 ����04:32:30
 *	 �� 
 *	 
 */
package et.bo.callcenter.operation;


 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public interface ConferenceService {

	/**
	 * ���ӻ����Ա
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void addLine(int i,LineService ls);
	/**
	 * ���ٻ����Ա
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void subLine(int i,LineService ls);
	/**
	 * ���ӻ�������
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void addListen(int i,LineService ls);
	/**
	 * ���ٻ�������
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void subListen(int i,LineService ls);
	/**
	 * ���ӻ���¼��
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void addRecord(int i,LineService ls,String file);
	/**
	 * ȥ������¼��
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void subRecord(int i,LineService ls);
}
