/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/03
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.passwordChange;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * パスワード変更情報入力フォームクラス。
 * ID RCSfile="$RCSfile: PasswordChangeForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:37 $"
 */
public class PasswordChangeForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * 現在のパスワード。
	 */
	private String password;

	/**
	 * 新しいパスワード。
	 */
	private String newPassword1;
	
	/**
	 * 新しいパスワード（確認用）。
	 */
	private String newPassword2;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public PasswordChangeForm() {
		super();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * 初期化処理。
	 * (非 Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {	
		this.password = "";
		this.newPassword1 ="";
		this.newPassword2 ="";
	}

	/* 
	 * 入力チェック。
	 * (非 Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {


		//定型処理----- 
		ActionErrors errors = super.validate(mapping, request);

		//---------------------------------------------
		// 基本的なチェック(必須、形式等）はValidatorを使用する。
		//---------------------------------------------

		//基本入力チェックここまで
		if (!errors.isEmpty()) {
			return errors;
		}

		//定型処理----- 

		//追加処理----- 

		//---------------------------------------------
		//組み合わせチェック	
		//---------------------------------------------
		//新しいパスワードと確認用の新しいパスワードが一致していることを確認
		if(!getNewPassword1().equals(getNewPassword2())){
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2004"));
				
			if(log.isDebugEnabled()){
				log.debug("新しいパスワードと確認用の新しいパスワードが一致していません。");			
			}
			
			//フォームをリセット
			setNewPassword1("");
			setNewPassword2("");			
		}
		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
	 * @return
	 */
	public String getNewPassword1() {
		return newPassword1;
	}

	/**
	 * @return
	 */
	public String getNewPassword2() {
		return newPassword2;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param string
	 */
	public void setNewPassword1(String string) {
		newPassword1 = string;
	}

	/**
	 * @param string
	 */
	public void setNewPassword2(String string) {
		newPassword2 = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

}
