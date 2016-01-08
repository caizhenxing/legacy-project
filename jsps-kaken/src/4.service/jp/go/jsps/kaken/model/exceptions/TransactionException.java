/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.exceptions;

import jp.go.jsps.kaken.model.vo.ErrorInfo;

/**
 * トランザクション処理中の例外クラス。
 * 
 * ID RCSfile="$RCSfile: TransactionException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:41 $"
 */
public class TransactionException extends ApplicationException {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 6740614899973101726L;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 */
	public TransactionException(String message) {
		super(message,new ErrorInfo("errors.4000"));
	}

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 * @param errorInfo		エラー情報
	 */
	public TransactionException(String message, ErrorInfo errorInfo) {
		super(message, errorInfo);
	}

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 * @param errorInfo		エラー情報
	 * @param cause			原因となった例外
	 */
	public TransactionException(
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
	public TransactionException(String message, Throwable cause) {
		super(message, new ErrorInfo("errors.4000"), cause);
	}
}