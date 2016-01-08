/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: RegistMailAddrForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class RegistMailAddrForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** メールアドレス */
	private String mailAddress;

	/** メールフラグ */
	private String mailFlg;

	/** メールフラグリスト */
	private List mailFlgList = new ArrayList();

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	
	/**
	 * コンストラクタ。
	 */
	public RegistMailAddrForm() {
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
		this.mailAddress = "";
		this.mailFlg ="";
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
		
		//「登録する」を選択し、メールアドレスが未入力の場合
		if(getMailFlg().equals("0") && StringUtil.isBlank(getMailAddress())){
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2002", new String[]{"Emailアドレス"}));	
		}
				
		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @return
	 */
	public String getMailFlg() {
		return mailFlg;
	}

	/**
	 * @return
	 */
	public List getMailFlgList() {
		return mailFlgList;
	}

	/**
	 * @param string
	 */
	public void setMailAddress(String string) {
		mailAddress = string;
	}

	/**
	 * @param string
	 */
	public void setMailFlg(String string) {
		mailFlg = string;
	}

	/**
	 * @param list
	 */
	public void setMailFlgList(List list) {
		mailFlgList = list;
	}

}
