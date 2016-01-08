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
package jp.go.jsps.kaken.model.dao.exceptions;

/**
 * データ追加の際に主キーが重複するデータが既に存在する場合の例外。
 * 
 * ID RCSfile="$RCSfile: DuplicateKeyException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:57 $"
 */
public class DuplicateKeyException extends DataAccessException {

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public DuplicateKeyException() {
		super();
	}

	/**
	 * コンストラクタ。
	 * @param message	詳細メッセージ
	 */
	public DuplicateKeyException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ。
	 * @param message	詳細メッセージ
	 * @param cause		原因となった例外
	 */
	public DuplicateKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * コンストラクタ。
	 * @param cause		原因となった例外。
	 */
	public DuplicateKeyException(Throwable cause) {
		super(cause);
	}

}
