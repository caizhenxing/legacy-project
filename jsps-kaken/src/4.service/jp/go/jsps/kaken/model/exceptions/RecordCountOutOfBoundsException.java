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
 * 一覧取得において、該当件数が最大値を超えた場合の例外クラス。
 * 
 * ID RCSfile="$RCSfile: RecordCountOutOfBoundsException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:42 $"
 */
public class RecordCountOutOfBoundsException extends ApplicationException {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 */
	public RecordCountOutOfBoundsException(String message) {
		super(message,new ErrorInfo("errors.maxcount"));
	}

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 * @param errorInfo		エラー情報
	 */
	public RecordCountOutOfBoundsException(String message, ErrorInfo errorInfo) {
		super(message, errorInfo);
	}

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 * @param errorInfo		エラー情報
	 * @param cause			原因となった例外
	 */
	public RecordCountOutOfBoundsException(
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
	public RecordCountOutOfBoundsException(String message, Throwable cause) {
		super(message, new ErrorInfo("errors.maxcount"), cause);
	}
	
	
}