/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム(JSPS)
 *    Source name : IkenForm.java
 *    Description : ご意見ご要望情報入力フォームクラス。
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/23    1.0         Xiang Emin     新規
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.iken;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * ご意見ご要望情報入力フォームクラス。
 * ID RCSfile="$RCSfile: IkenForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:18 $"
 */
public class IkenForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * ご意見ご要望。
	 */
	private String ikenInfo;
	
	/**
	 * 対象者ID
	 * 1:申請者、2:所属機関担当者、4:審査員、5:国際センター研究員、6:部局担当者
	 */
	private String taishoID;

	/** システム受付番号 */
	private String system_no;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public IkenForm() {
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
		
		this.ikenInfo = "";
		this.taishoID = "";
		this.system_no = "";
	}

	/* 
	 * 入力チェック。
	 * (非 Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		//スーパークラスの処理を呼び出す。 
		ActionErrors errors = super.validate(mapping, request);
		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getIkenInfo() {
		return ikenInfo;
	}

	/**
	 * @param string
	 */
	public void setIkenInfo(String string) {
		ikenInfo = string;
	}

	/**
	 * 対象者IDを取得する
	 * @return　対象者ID
	 */
	public String getTaishoID(){
		return taishoID;
	}
	
	/**
	 * 対象者IDを設定する
	 * @param s
	 */
	public void setTaishoID(String s){
		taishoID = s;
	}

	/**
	 * システム受付番号の取得
	 * @return
	 */
	public String getSystem_no(){
		return system_no;
	}
	
	/**
	 * システム受付番号の設定
	 * @param str
	 */
	public void setSystem_no(String str){
		system_no = str;
	}
}
