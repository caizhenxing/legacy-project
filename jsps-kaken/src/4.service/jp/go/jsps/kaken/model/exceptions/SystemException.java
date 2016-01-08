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

/**
 * システム関連の例外クラス。
 * 
 * ID RCSfile="$RCSfile: SystemException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:41 $"
 * 
 */
public class SystemException extends RuntimeException {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 73683311051810516L;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public SystemException() {
		super();
	}

	/**
	 * コンストラクタ。
	 * @param message	詳細メッセージ
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ。
	 * @param message	詳細メッセージ
	 * @param cause		原因となった例外
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * コンストラクタ。
	 * @param cause		原因となった例外。
	 */
	public SystemException(Throwable cause) {
		super(cause);
	}

}
