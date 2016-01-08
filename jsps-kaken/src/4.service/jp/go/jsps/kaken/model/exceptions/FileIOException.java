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
 * ファイル入出力関連の例外クラス。
 * 
 * ID RCSfile="$RCSfile: FileIOException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:41 $"
 * 
 */
public class FileIOException extends ApplicationException{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 3122108023900077367L;

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
	public FileIOException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 * @param errorInfo		エラー情報
	 */
	public FileIOException(String message, ErrorInfo errorInfo) {
		super(message, errorInfo);
	}

	/**
	 * コンストラクタ。
	 * @param message		詳細メッセージ
	 * @param errorInfo		エラー情報
	 * @param cause			原因となった例外
	 */
	public FileIOException(
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
	public FileIOException(String message, Throwable cause) {
		super(message, cause);
	}
}
