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
package jp.go.jsps.kaken.web.gyomu.kokaiKakutei;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: KokaiKakuteiForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:49 $"
 */
public class KokaiKakuteiForm extends BaseValidatorForm {
	

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** パスワード */
	private String    password;
	
	/** 公開決裁番号 */
	private String    kessaiNo;
	
	/** 事業ID */
	private List     jigyoIds;
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KokaiKakuteiForm() {
		super();
		init();
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
		super.reset(mapping, request);
		init();
	}

	/**
	 * 初期化処理
	 */
	public void init() {
		password = "";
		kessaiNo = "";
		jigyoIds = new Vector();
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

		if(jigyoIds == null){
			
		}
		//基本入力チェックここまで
		if (!errors.isEmpty()) {
			return errors;
		}
		//定型処理----- 

		//追加処理----- 

		//---------------------------------------------
		//組み合わせチェック	
		//---------------------------------------------

		return errors;
	}
		

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
	 * キーに一致する事業IDを設定する。
	 * @param key		キー
	 * @param value		事業ID
	 */
	public void setJigyoId(int key, Object value) {
		jigyoIds.add(value);
	 }

	/**
	 * キーに一致する事業IDを取得する。
	 * @param key		キー
	 * @return	事業ID
	 */
	public Object getJigyoId(int key) {
		 return jigyoIds.get(key);
	 }

	/**
	 * @return
	 */
	public String getKessaiNo() {
		return kessaiNo;
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
	public void setKessaiNo(String string) {
		kessaiNo = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @return
	 */
	public List getJigyoIds() {
		return jigyoIds;
	}

	/**
	 * @param list
	 */
	public void setJigyoIds(List list) {
		jigyoIds = list;
	}

}