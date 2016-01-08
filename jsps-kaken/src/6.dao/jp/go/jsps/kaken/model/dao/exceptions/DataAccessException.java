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
package jp.go.jsps.kaken.model.dao.exceptions;

/**
 * データベース関連の例外。
 * 
 * ID RCSfile="$RCSfile: DataAccessException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:57 $"
 * 
 */
public class DataAccessException extends Exception {

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public DataAccessException() {
		super();
	}

	/**
	 * コンストラクタ。
	 * @param message	詳細メッセージ
	 */
	public DataAccessException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ。
	 * @param message	詳細メッセージ
	 * @param cause		原因となった例外
	 */
	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * コンストラクタ。
	 * @param cause		原因となった例外。
	 */
	public DataAccessException(Throwable cause) {
		super(cause);
	}

}

