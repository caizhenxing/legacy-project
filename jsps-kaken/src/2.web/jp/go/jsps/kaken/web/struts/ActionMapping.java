/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/17
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.struts;

/**
 * ActionMappingにログインチェックの有無のプロパティを追加する。
 * 
 * ID RCSfile="$RCSfile: ActionMapping.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public class ActionMapping extends org.apache.struts.action.ActionMapping {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ログインチェックを行うかどうかの属性。*/
	protected boolean logonCheck = true;
	
	/** エラー時に閉じるボタンを表示するかどうかの属性。*/
	protected boolean errorClose = false;
	
	/** 空きメモリチェックを行うかどうかの属性。*/
	protected boolean memoryCheck = false;
	
    /** アクション説明分*/
    protected String description = "";
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * 	コンストラクタ。
	 */
	public ActionMapping() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * ログインチェックを行うかどうかを取得する。
	 * @return	ログインチェックを行う場合 true 以外 false
	 */
	public boolean isLogonCheck() {
		return logonCheck;
	}

	/**
	 * ログインチェックを行うかどうかを設定する。
	 * @param b　ログインチェックを行う場合 true 以外 false
	 */
	public void setLogonCheck(boolean b) {
		logonCheck = b;
	}

	/**
	 * エラー画面で閉じるボタンを表示するかを取得する。
	 * @return	閉じるボタンを表示する場合　true 以外 false
	 */
	public boolean isErrorClose() {
		return errorClose;
	}

	/**
 	 * エラー画面で閉じるボタンを表示するかを設定する。
	 * @param b 閉じるボタンを表示する場合　true 以外 false
	 */
	public void setErrorClose(boolean b) {
		errorClose = b;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * @return
	 */
	public boolean isMemoryCheck() {
		return memoryCheck;
	}

	/**
	 * @param b
	 */
	public void setMemoryCheck(boolean b) {
		memoryCheck = b;
	}

}
