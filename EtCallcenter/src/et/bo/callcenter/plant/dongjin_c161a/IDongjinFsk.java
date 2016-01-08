/**
 * 	@(#)IDongjinFsk.java   2006-12-22 ����10:42:00
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a;

 /**
 * @author zhaoyifei
 * @version 2006-12-22
 * @see
 */
public interface IDongjinFsk {

	/**
	 * 
	 * �����к�����йغ���
	 * 
	 * ��������FSK
	 * ��Ҫ�õ�
	 * ResetCallerIDBuffer
	 * GetCallerIDRawStr
	 * �����������ɣ�������������˵����
	 */
	
	/**
	 * ��ʼ��ĳ·Caller-ID������
	 * @param  wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void resetCallerIDBuffer(int wChnlNo);
	
	/**
	 * ���Call-ID�����ݣ�IDStr���û����䣬128�ֽھ��԰�ȫ��������3��4ʱ���յ�����һ��ʽ����ϸ�ʽ�����ܽ��ա�
	 * ���������󣬵���ResetCallerIDBuffer��������������3��4ʱ��ժ������Ҫ��ʱ������ʱ�ղ���FSKժ��
	 * @param wChnlNo ͨ����
	 * @version 2006-11-22
	 * @return IDRawStr ���յ����к�����Ϣ
	 * @return 0 δ�յ���Ϣ
	 * @return 1 ���ڽ���ͷ��Ϣ
	 * @return 2 ���ڽ���id����
	 * @return 3 ������ϣ�У����ȷ
	 * @return 4 ������ϣ�У�����
	 */
	public abstract String getCallerIDRawStr(int wChnlNo);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract String getCallerIDStr(int wChnlNo);
	
	
	
	/**
	 * 
	 * 
	 * �շ�FSK
	 * 
	 */
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int dJFskInitForFsk(int mode); 
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void dJFskResetFskBuffer(int trunkID,int mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int dJFskCheckSendFSKEnd(int vocID,int mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int dJFskSendFSK(int trunkID,byte[] pInfo,int wSize,int mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int dJFskGetFSK(int trunkID,byte[] pInfo,int mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void dJFskStopSend(int trunkID,int mode);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract void dJFskRelease();
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int dJFskSendFSKA(int trunkID,byte[] pInfo,int wSize,int mode,int markNum);
	
	/**
	 * 
	 * @param
	 * @version 2006-11-22
	 * @return
	 */
	public abstract int dJFskSendFSKBit(int trunkID,byte[] pInfo,int wSize,int mode);
	
}
