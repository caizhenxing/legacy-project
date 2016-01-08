/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/04
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.exceptions;

import java.util.List;

import jp.go.jsps.kaken.model.vo.ErrorInfo;

/**
 * サーバ上でのチェック時エラーが発生したときの例外クラス。
 * 
 * ID RCSfile="$RCSfile: ValidationException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:41 $"
 */
public class ValidationException extends ApplicationException {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -4476746061576790851L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ErrorInfoのリスト*/
	private List errors;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ValidationException(String message,List errors) {
		super(
			message,
			new ErrorInfo("errors.5001"));
		this.errors = errors;
	}

	/**
	 * エラー情報を取得する。
	 * @return	エラー情報リスト。
	 */
	public List getErrors() {
		return errors;
	}

}
