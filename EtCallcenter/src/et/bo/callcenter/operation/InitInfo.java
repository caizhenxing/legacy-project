/**
 * 	@(#)InitInfo.java   2007-1-16 ����10:43:11
 *	 �� 
 *	 
 */
package et.bo.callcenter.operation;

 /**
 * @author zhaoyifei
 * @version 2007-1-16
 * @see
 */
public class InitInfo {

	private int lineBuf=1024*8*3;//ͨ���ڴ�
	private int recordBuf=1024*8;//¼��������
	private int busyPa=700;//æ��
	private int packRate=0;//¼��ѹ����
	private int ringback1=1000;//��������ʱ��
	private int ringback2=4000;//������ͣʱ��
	private int busylen=350;//������Ƶ��
	private int ringtimes=10;//����������
	/**
	 * @return the busylen
	 */
	public int getBusylen() {
		return busylen;
	}
	/**
	 * @param busylen the busylen to set
	 */
	public void setBusylen(int busylen) {
		this.busylen = busylen;
	}
	/**
	 * @return the busyPa
	 */
	public int getBusyPa() {
		return busyPa;
	}
	/**
	 * @param busyPa the busyPa to set
	 */
	public void setBusyPa(int busyPa) {
		this.busyPa = busyPa;
	}
	/**
	 * @return the lineBuf
	 */
	public int getLineBuf() {
		return lineBuf;
	}
	/**
	 * @param lineBuf the lineBuf to set
	 */
	public void setLineBuf(int lineBuf) {
		this.lineBuf = lineBuf;
	}
	/**
	 * @return the packRate
	 */
	public int getPackRate() {
		return packRate;
	}
	/**
	 * @param packRate the packRate to set
	 */
	public void setPackRate(int packRate) {
		this.packRate = packRate;
	}
	/**
	 * @return the recordBuf
	 */
	public int getRecordBuf() {
		return recordBuf;
	}
	/**
	 * @param recordBuf the recordBuf to set
	 */
	public void setRecordBuf(int recordBuf) {
		this.recordBuf = recordBuf;
	}
	/**
	 * @return the ringback1
	 */
	public int getRingback1() {
		return ringback1;
	}
	/**
	 * @param ringback1 the ringback1 to set
	 */
	public void setRingback1(int ringback1) {
		this.ringback1 = ringback1;
	}
	/**
	 * @return the ringback2
	 */
	public int getRingback2() {
		return ringback2;
	}
	/**
	 * @param ringback2 the ringback2 to set
	 */
	public void setRingback2(int ringback2) {
		this.ringback2 = ringback2;
	}
	/**
	 * @return the ringtimes
	 */
	public int getRingtimes() {
		return ringtimes;
	}
	/**
	 * @param ringtimes the ringtimes to set
	 */
	public void setRingtimes(int ringtimes) {
		this.ringtimes = ringtimes;
	}
}
