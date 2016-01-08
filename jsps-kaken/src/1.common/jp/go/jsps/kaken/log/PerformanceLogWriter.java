/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/12/10
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
 
package jp.go.jsps.kaken.log;
import java.text.*;

import org.apache.commons.logging.*;


/**
 * パフォーマンスログ出力クラス。
 * ID RCSfile="$RCSfile: PerformanceLogWriter.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:50 $"
 */
public class PerformanceLogWriter {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** パフォーマンスログクラス */
	protected static final Log log = LogFactory.getLog("performance");
	
	/** メモリ使用率書式 */
	private static final DecimalFormat df = new DecimalFormat("#0.00");
	
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** 処理開始時間 */
	private long startTime  = 0L;
	
	/** メモリ使用状況出力フラグ（デフォルトtrue） */
	private boolean memFlag = true;
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ
	 * インスタンス生成時の時間を処理開始時間とする。
	 * このインスタンスを使用してログを出力した場合、
	 * 出力内容には処理時間（ログ出力メソッドの呼び出し時間－処理開始時間）と
	 * メモリ使用状況（空き／トータル／使用サイズ／使用率）が出力される。
	 */
	public PerformanceLogWriter(){
		startTime = System.currentTimeMillis();
	}
	
	
	/**
	 * コンストラクタ
	 * @param startTime  処理開始時間
	 * @param memFlag    メモリ使用状況出力フラグ（falseの場合は表示されない）
	 */
	public PerformanceLogWriter(long startTime, boolean memFlag){
		this.startTime = startTime;
		this.memFlag   = memFlag;
	}
	
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
	/**
	 * 処理開始時間を現在時刻にリセットする。
	 */
	public void resetTime(){
		this.startTime = System.currentTimeMillis();
	}
	
	
	/**
	 * ログ出力メソッド。
	 * コンストラクタ呼び出し時の時間（または前回resetTimeメソッド呼び出し時間）を開始時間、
	 * 当該メソッドを呼び出した時間を終了時間として、その差を「処理時間」として出力する。
	 * @param moji			出力文字列（基本的に処理名）
	 */
	public void out(String moji){
		long entTime = System.currentTimeMillis();
		out(moji, this.startTime, entTime, this.memFlag);
	}
	
	
	/**
	 * ログ出力メソッド。
	 * コンストラクタ呼び出し時の時間（または前回resetTimeメソッド呼び出し時間）を開始時間、
	 * 当該メソッドを呼び出した時間を終了時間として、その差を「処理時間」として出力する。
	 * ログ出力後、処理開始時間を現在時刻にリセットする。
	 * @param moji			出力文字列（基本的に処理名）
	 */
	public void outWithRstTime(String moji){
		long entTime = System.currentTimeMillis();
		out(moji, this.startTime, entTime, this.memFlag);
		resetTime();
	}
	
	
	/**
	 * ログ出力メソッド。
	 * 処理実行終了時間と処理実行開始時間との差を「処理時間」として出力する。
	 * @param moji			出力文字列（基本的に処理名）
	 * @param startTime    処理実行開始時間
	 * @param endTime      処理実行終了時間
	 * @param memFlag		メモリ使用状況出力フラグ
	 */
	public static void out(String moji,
							 long startTime, 
							 long endTime, 
							 boolean memFlag)
	{
		//出力文字列
		StringBuffer buffer = new StringBuffer();
		buffer.append(moji);
		
		//処理時間の算出(ms)
		long processingTime = endTime - startTime;
		buffer.append(",処理時間(ms)：").append(processingTime);
		
		//メモリ使用状況の算出(KB)
		if(memFlag){
			Runtime rt       = Runtime.getRuntime();
			long   freeMem   = rt.freeMemory()  / 1024;
			long   totalMem  = rt.totalMemory() / 1024;
			long   usedMem   = totalMem - freeMem;
			double usageRate = ((double)usedMem  / (double)totalMem) * 100;
			buffer.append(",空きメモリサイズ(KB)：")
				  .append(freeMem)
				  .append(",総合メモリサイズ(KB)：")
				  .append(totalMem)
				  .append(",使用メモリサイズ(KB)：")
				  .append(usedMem)
				  .append(",メモリ使用率(%)：")
				  .append(df.format(usageRate))
				  ;
		}
		
		//ログ出力
		log.debug(buffer);
		
	}
	
	
	
	/**
	 * メモリ使用率（パーセンテージ）を取得する。
	 * ※使用率は整数に丸められる。
	 * @return メモリ使用率
	 */
	public static int checkUsedMemRate(){
		Runtime rt       = Runtime.getRuntime();
		long   freeMem   = rt.freeMemory();
		long   totalMem  = rt.totalMemory();
		long   usedMem   = totalMem - freeMem;
		double usageRate = ((double)usedMem  / (double)totalMem) * 100;
		return (int)usageRate;
	}
	
	
	
}


