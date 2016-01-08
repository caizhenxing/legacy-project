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
 * データベースのデータが既に更新されていた場合の例外。
 * 
 * ID RCSfile="$RCSfile: AlreadyModifiedException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:57 $"
 */

public class AlreadyModifiedException extends DataAccessException {

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
    
	/**
	 * コンストラクタ。
	 * @param message	詳細メッセージ
	 */
	public AlreadyModifiedException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ。
	 * @param message	詳細メッセージ
	 * @param cause		原因となった例外
	 */
	public AlreadyModifiedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * コンストラクタ。
	 * @param cause		原因となった例外。
	 */
	public AlreadyModifiedException(Throwable cause) {
		super(cause);
	}}
