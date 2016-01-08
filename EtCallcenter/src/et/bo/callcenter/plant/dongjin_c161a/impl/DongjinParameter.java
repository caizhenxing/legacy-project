/**
 * 	@(#)DongjinParameter.java   2007-1-6 下午12:01:47
 *	 。 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

 /**
 * @author zhaoyifei
 * @version 2007-1-6
 * @see
 */
public class DongjinParameter {

	public static int  SIG_STOP=0;//       停止播放信号音
	public static int SIG_DIALTONE=1 ;// 拨号音
	public static int SIG_BUSY1=2;//      忙音一
	public static int SIG_BUSY2=3;//      忙音二
	public static int SIG_RINGBACK=4;//  回铃音
	public static int SIG_STOP_NEW=10;
	
	
	public static int CHTYPE_USER=0;
	public static int CHTYPE_TRUNK=1;
	public static int CHTYPE_EMPTY=2;
	public static int CHTYPE_RECORD=3;

	public static int RECORD_CHECK=01;
	public static int PLAY_CHECK=02;
	public static int SEND_CHECK=03;
	public static int SEND_READY_CHECK=04;

	public static int R_BUSY=0x21;
	public static int R_OTHER=0x20;

	public static int S_NODIALTONE=0x0F;
	public static int S_NORESULT=0x10;
	public static int S_BUSY=0x11;
	public static int S_NOBODY=0x13;
	public static int S_CONNECT=0x14;
	public static int S_NOSIGNAL=0x15;

	public static int S_DIALSIG=0x30;
	
	public static int SIG_CADENCE_BUSY =1;	//忙音
	public static int SIG_CADENCE_RINGBACK=2 ;  //回铃音
	
	
	public static int HANG_UP_FLAG_FALSE=0;//没有挂机
	public static int HANG_UP_FLAG_TRUE=1;//已经挂机
	public static int HANG_UP_FLAG_START=2;//开始挂机
	public static int HANG_UP_FLAG_PRESS_R=3;//拍叉簧
	
	public static int 	VOL_ADJUST_RECORD=0;
	public static int VOL_ADJUST_PLAY=1;
}
