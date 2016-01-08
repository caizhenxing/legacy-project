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
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: ShinsaKekka2ndForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class ShinsaKekka2ndForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** システム番号 */
	private String    systemNo;
	
	/** 2次審査結果 */
	private String    kekka2;
	
	/** 総経費 */
	private String    souKehi;
	
	/** 初年度経費 */
	private String    shonenKehi;
	
	/** 業務担当者記入欄 */
	private String    shinsa2Biko;
	
	/** 2次審査結果情報選択リスト */
	private List kekka2List = new ArrayList();

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsaKekka2ndForm() {
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
		//・・・
	}

	/**
	 * 初期化処理
	 */
	public void init() {
		systemNo = "";
		kekka2 = Integer.toString(StatusCode.KEKKA2_SAITAKU);	//初期値設定。2次審査結果：「採択」
		souKehi = "";
		shonenKehi = "";
		shinsa2Biko = "";
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
				
		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getKekka2() {
		return kekka2;
	}

	/**
	 * @return
	 */
	public String getShinsa2Biko() {
		return shinsa2Biko;
	}

	/**
	 * @return
	 */
	public String getShonenKehi() {
		return shonenKehi;
	}

	/**
	 * @return
	 */
	public String getSouKehi() {
		return souKehi;
	}

	/**
	 * @param string
	 */
	public void setKekka2(String string) {
		kekka2 = string;
	}

	/**
	 * @param string
	 */
	public void setShinsa2Biko(String string) {
		shinsa2Biko = string;
	}

	/**
	 * @param string
	 */
	public void setShonenKehi(String string) {
		shonenKehi = string;
	}

	/**
	 * @param string
	 */
	public void setSouKehi(String string) {
		souKehi = string;
	}

	/**
	 * @return
	 */
	public String getSystemNo() {
		return systemNo;
	}

	/**
	 * @param string
	 */
	public void setSystemNo(String string) {
		systemNo = string;
	}

	/**
	 * @return
	 */
	public List getKekka2List() {
		return kekka2List;
	}

	/**
	 * @param list
	 */
	public void setKekka2List(List list) {
		kekka2List = list;
	}

}
