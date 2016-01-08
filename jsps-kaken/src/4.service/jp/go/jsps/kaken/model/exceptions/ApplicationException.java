/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.exceptions;

import jp.go.jsps.kaken.model.vo.ErrorInfo;

/**
 * アプリケーション関連の例外クラス。
 * 
 * ID RCSfile="$RCSfile: ApplicationException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:41 $"
 * 
 */
public class ApplicationException extends Exception{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 4264138343671550592L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 
	 * エラー情報。
	 */
	private ErrorInfo errorInfo;


	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 * @param errorInfo		エラー情報
	 */
	public ApplicationException(String message, ErrorInfo errorInfo) {
		super(message);
		this.errorInfo = errorInfo;
	}


	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 * @param cause			原因となった例外
	 */
	public ApplicationException(
		String message,
		Throwable cause) {
		super(message,cause);
	}
	
	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 * @param errorInfo		エラー情報
	 * @param cause			原因となった例外
	 */
	public ApplicationException(
		String message,
		ErrorInfo errorInfo,
		Throwable cause) {
		super(message,cause);
		this.errorInfo = errorInfo;
	}


	//---------------------------------------------------------------------
	// Implementation of ErrorCoded interface
	//---------------------------------------------------------------------

	/**
	 * エラー情報がセットされているかを取得する。
	 * @return	エラー情報がセットされているとき true 以外 false
	 */
	public boolean isErrorInfo(){
		if(errorInfo == null){
			return false;
		}
		return true;
	}

	/**
	 * エラーコードを取得する。
	 * @return	エラーコード
	 */
	public String getErrorCode() {
		return errorInfo.getErrorCode();
	}

	/**
	 * エラー引数を取得する。
	 * @return	エラー引数
	 */
	public Object[] getErrorArgs() {
		return errorInfo.getErrorArgs();
	}

}
