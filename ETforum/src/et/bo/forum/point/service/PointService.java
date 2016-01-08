/**
 * 	@(#)PointService.java   2006-11-22 ����03:58:33
 *	 �� 
 *	 
 */
package et.bo.forum.point.service;

/**
 * @describe ��̳���ֲ����ӿ�
 * @author Ҷ����
 * @version 2006-11-22
 * @see
 */
public interface PointService {
	/**
	 * @describe �����ӷ�
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void addSendPoint(String userId);
	/**
	 * @describe �����ӷ�
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void addAnswerPoint(String userId);
	/**
	 * @describe ����Ա�ӷ�
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void addManagerPoint(String userId, int point);
	/**
	 * @describe ���뾫���ӷ�
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void joinPrimePoint(String userId);
	/**
	 * @describe �ö��ӷ� 
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void putTopPoint(String userId);
	/**
	 * @describe ɾ���۷�
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void cutDeletePostPoint(String userId);
	/**
	 * @describe ����Ա�۷�
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public void cutManagerPoint(String userId, int point);
	/**
	 * @describe �����û����ֵõ��ȼ�
	 * @param
	 * @version 2006-11-29
	 * @return
	 */
	public String getUserLevel(int point);
	/**
	 * @describe ȡ���û�����
	 * @param
	 * @version 2006-12-19
	 * @return  String
	 */
	public String getUserPiont(String userId);
}
