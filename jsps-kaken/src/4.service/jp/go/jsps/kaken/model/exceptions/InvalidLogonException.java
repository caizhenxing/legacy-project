/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.exceptions;

import jp.go.jsps.kaken.model.vo.ErrorInfo;


/**
 * ログオン認証に失敗したときに発生する例外クラス。
 * 
 * ID RCSfile="$RCSfile: InvalidLogonException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:42 $"
 */
public class InvalidLogonException extends ApplicationException {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 4018344322010111204L;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 */
	public InvalidLogonException(String message) {
		super(message,new ErrorInfo("errors.5003"));
	}

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 * @param errorInfo		エラー情報
	 */
	public InvalidLogonException(String message, ErrorInfo errorInfo) {
		super(message, errorInfo);
	}

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 * @param errorInfo		エラー情報
	 * @param cause			原因となった例外
	 */
	public InvalidLogonException(
		String message,
		ErrorInfo errorInfo,
		Throwable cause) {
		super(message, errorInfo, cause);
	}

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 * @param cause			原因となった例外
	 */
	public InvalidLogonException(String message, Throwable cause) {
		super(message, new ErrorInfo("errors.5003"), cause);
	}
}
