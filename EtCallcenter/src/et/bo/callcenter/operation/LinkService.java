/**
 * 	@(#)LinkService.java   2007-1-15 ����04:33:51
 *	 �� 
 *	 
 */
package et.bo.callcenter.operation;


 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public interface LinkService {

	/**
	 * ��·�绰����
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void setLink(LineService ls1,LineService ls2);
	/**
	 * ��·�绰����
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract LinkInfo clearLink(LineService ls);
	/**
	 * ������ͨ
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void setListen(LineService ls1,LineService ls2);
	
	/**
	 * ����ͨ��
	 * @param
	 * @version 2007-1-16
	 * @return
	 */
	public abstract void setlinkThree(LineService ls1,LineService ls2,LineService ls3);
	
	/**
	 * ͨ��¼��
	 * @param
	 * @version 2007-1-22
	 * @return
	 */
	public abstract void setRecord(LineService ls);
	
	
	public abstract boolean contain(LineService ls);
}
