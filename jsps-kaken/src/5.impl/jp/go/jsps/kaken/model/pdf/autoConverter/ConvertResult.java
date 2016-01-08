/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.pdf.autoConverter;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.ConvertException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.util.FileResource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 変換処理結果を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ConvertResult.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:55 $"
 */
public class ConvertResult {
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ログクラス。 
	 */
	private static final Log log = LogFactory.getLog(ConvertResult.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * 変換されたファイル、または例外。
	 */
	private Object realData = null;

	/**
	 * 処理結果の準備ができたかどうかのフラグ。
	 */
	private boolean ready = false;

	/**
	 * 変換対象ファイル情報保持マップ。
	 */
	private AutoConverter converter;


	/** タイムアウト値（秒） */
	private final int timeOut;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * 	コンストラクタ。
	 * @param converter		変換クラス。
	 */
	public ConvertResult(AutoConverter converter) {	
		super();
		this.converter = converter;
		this.timeOut	= ApplicationSettings.getInteger(ISettingKeys.PDF_TIMEOUT).intValue() * 1000;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * 実際に変換された処理結果ファイルをセットする。
	 * @param realdata
	 */
	public synchronized void setRealData(Object realData) {
		if (ready) {
			return;
		}
		this.realData = realData;
		this.ready = true;
		//通知
		notifyAll();
	}


	/**
	 * 変換結果を取得する。
	 * 変化終了まで待機する。
	 * @return	処理結果ファイル。
	 * @throws ConvertException	変換中に例外が発生した場合。
	 */
	public synchronized FileResource getResult() throws ConvertException {

		//変換結果がセットされるまで待機する。
		long start = System.currentTimeMillis();

		while(!ready){
			//タイムアウトしない。
			if(timeOut == 0){
				try{
					wait();
				}catch(InterruptedException e){
					if(log.isDebugEnabled()){
						log.debug(e);
					}
				}
			//ｘｘ秒後にタイムアウトする。	
			}else{
				long now 	= System.currentTimeMillis();
				long rest	= (timeOut == 0) ? 0 : timeOut  - (now - start);	//待ち時間
				if(timeOut !=0 && rest <= 0){
					log.debug("タイムアウトが発生しました。now - start = " + ( now - start ) + " >= timeout = " + timeOut );
					converter.removeFileInfo(this);
					throw new ConvertException("変換タイムアウトが発生しました。",new ErrorInfo("errors.8001"));
				}
				try{
					wait(rest);
				}catch(InterruptedException e){
					if(log.isDebugEnabled()){
						log.debug(e);
					}
				}
			}
		}
		
		//例外が発生していた場合は、例外をスローする。
		if (realData instanceof SystemException) {
			throw (SystemException) realData;
		} else {
			return (FileResource) realData;
		}
	}
}
