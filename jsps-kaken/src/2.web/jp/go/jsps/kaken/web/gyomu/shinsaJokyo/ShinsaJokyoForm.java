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
package jp.go.jsps.kaken.web.gyomu.shinsaJokyo;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *  審査状況フォーム
 * 
 * ID RCSfile="$RCSfile: ShinsaJokyoForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:15 $"
 */
public class ShinsaJokyoForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 審査員番号 */
	private String shinsainNo;

	/** 審査員名（漢字-氏） */
	private String nameKanjiSei;

	/** 審査員名（漢字-名） */
	private String nameKanjiMei;

	/** 事業名 */
	private String jigyoName;

	/** 年度 */
	private String nendo;

	/** 回数 */
	private String kaisu;

	/** 事業区分 */
	private String jigyoKubun;

	/** 事業ID */
	private String jigyoId;
	
//最終ログイン日を追加
	/** 最終ログイン日 */
	private String loginDate;
	
//整理番号（学創用）を追加
  /** 整理番号（学創用） */
	  private String seiriNo;

//	表示方式を追加
	/** 表示方式 */
	private String hyojiHoshikiShinsaJokyo;

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsaJokyoForm() {
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
		shinsainNo = "";
		nameKanjiSei = "";
		nameKanjiMei = "";
		jigyoName = "";
		nendo = "";
		kaisu = "";
		jigyoKubun = "";
		jigyoId = "";
		loginDate = "";
		seiriNo = "";
		hyojiHoshikiShinsaJokyo = "";
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
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @return
	 */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @return
	 */
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @return
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

	/**
	 * @return
	 */
	public String getNendo() {
		return nendo;
	}

	/**
	 * @return
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiMei(String string) {
		nameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiSei(String string) {
		nameKanjiSei = string;
	}

	/**
	 * @param string
	 */
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainNo(String string) {
		shinsainNo = string;
	}
//最終ログイン日を追加
	/**
	 * @return
	 */
	public String getLoginDate() {
		return loginDate;
	  }
	/**
	 * @param string
	 */
	public void setLoginDate(String string) {
		loginDate = string;
	}
//整理番号（学創用）を追加	2005/11/2
//整理番号を追加
	/**
	 * @return
	 */
	public String getSeiriNo() {
		return seiriNo;
	}
	/**
	 * @param string
	 */
	public void setSeiriNo(String string) {
		seiriNo = string;
	}
	
	/**
	 * @return
	 */
	public String getHyojiHoshikiShinsaJokyo() {
		return hyojiHoshikiShinsaJokyo;
	}
	
	/**
	 * @param string
	 */
	public void setHyojiHoshikiShinsaJokyo(String string) {
		hyojiHoshikiShinsaJokyo = string;
	}	
}
