/**
 * 	@(#)IDongjinOutHook.java   2006-12-21 ����04:54:45
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-21
 * @see
 */
public interface IDongjinOutHook {

	/**
	 * 
	 * ���弰ժ�һ�����
	 * 
	 * 
	 * 
	 */
	
	/**
	 * ��������Ƿ��������źŻ������Ƿ������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return true ����������
	 * @return false �������һ�
	 * 
	 */
	public boolean ringDetect(int wChnlNo);
	
	/**
	 * ����������������ߣ���������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public void offHook(int wChnlNo);
	
	/**
	 * ���߹һ����������ߣ���������
	 * @param wChnlNo ͨ����
	 * @version 2006-11-21
	 * @return
	 */
	public void hangUp(int wChnlNo);
	
}
