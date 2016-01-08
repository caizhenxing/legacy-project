/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseiJohoKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * 申請情報検索フォーム
 * 
 */
public class ShinseiSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 事業CD */
	private String jigyoCd;

	/** 年度 */
	private String nendo;

	/** 回数 */
	private String kaisu;

	/** 申請者氏名（氏・漢字） */
	private String nameKanjiSei;

	/** 申請者氏名（名・漢字） */
	private String nameKanjiMei;

	/** 申請者氏名（氏・ローマ字） */
	private String nameRoSei;

	/** 申請者氏名（名・ローマ字） */
	private String nameRoMei;

	/** 申請状況 */
	private String jokyoId;

	/** 作成日（From・年） */
	private String sakuseiDateFromYear;

	/** 作成日（From・月） */
	private String sakuseiDateFromMonth;

	/** 作成日（From・日） */
	private String sakuseiDateFromDay;

	/** 作成日（To・年） */
	private String sakuseiDateToYear;

	/** 作成日（To・月） */
	private String sakuseiDateToMonth;

	/** 作成日（To・日） */
	private String sakuseiDateToDay;

	/** 所属機関承認日（From・年） */
	private String shoninDateFromYear;

	/** 所属機関承認日（From・月） */
	private String shoninDateFromMonth;

	/** 所属機関承認日（From・日） */
	private String shoninDateFromDay;

	/** 所属機関承認日（To・年） */
	private String shoninDateToYear;

	/** 所属機関承認日（To・月） */
	private String shoninDateToMonth;

	/** 所属機関承認日（To・日） */
	private String shoninDateToDay;

	/** 表示方式 */
	private String hyojiHoshiki;

	/** 事業名リスト */
	private List jigyoList = new ArrayList();

	/** 申請状況リスト */
	private List jokyoList = new ArrayList();

	/** 申請者氏名（氏・フリガナ） */
	private String nameKanaSei;

	/** 申請者氏名（名・フリガナ） */
	private String nameKanaMei;

	/** 研究者番号 */
	private String kenkyuNo;

	/** 部局コード */
	private String bukyokuCd;

	/** 表示方式リスト */
	private List hyojiHoshikiList = new ArrayList();

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinseiSearchForm() {
		super();
		init();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * 初期化処理。
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//・・・
	}

	/**
	 * 初期化処理
	 */
	public void init() {
		jigyoCd              = "";
		nendo                = "";
		kaisu                = "";
		nameKanjiSei         = "";
		nameKanjiMei         = "";
		nameRoSei            = "";
		nameRoMei            = "";
		jokyoId              = "";
		sakuseiDateFromYear  = "";
		sakuseiDateFromMonth = "";
		sakuseiDateFromDay   = "";
		sakuseiDateToYear    = "";
		sakuseiDateToMonth   = "";
		sakuseiDateToDay     = "";
		shoninDateFromYear   = "";
		shoninDateFromMonth  = "";
		shoninDateFromDay    = "";
		shoninDateToYear     = "";
		shoninDateToMonth    = "";
		shoninDateToDay      = "";
		hyojiHoshiki         = "";
		nameKanaSei     = "";
		nameKanaMei    = "";
		kenkyuNo      = "";
		bukyokuCd         = "";
	}

	/* 
	 * 入力チェック。
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
	public String getJigyoCd() {
		return jigyoCd;
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
	public String getKaisu() {
		return kaisu;
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
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getNameRoSei() {
		return nameRoSei;
	}

	/**
	 * @return
	 */
	public String getNameRoMei() {
		return nameRoMei;
	}

	/**
	 * @return
	 */
	public String getJokyoId() {
		return jokyoId;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateFromYear() {
		return sakuseiDateFromYear;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateFromMonth() {
		return sakuseiDateFromMonth;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateFromDay() {
		return sakuseiDateFromDay;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateToYear() {
		return sakuseiDateToYear;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateToMonth() {
		return sakuseiDateToMonth;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateToDay() {
		return sakuseiDateToDay;
	}

	/**
	 * @return
	 */
	public String getShoninDateFromYear() {
		return shoninDateFromYear;
	}

	/**
	 * @return
	 */
	public String getShoninDateFromMonth() {
		return shoninDateFromMonth;
	}

	/**
	 * @return
	 */
	public String getShoninDateFromDay() {
		return shoninDateFromDay;
	}

	/**
	 * @return
	 */
	public String getShoninDateToYear() {
		return shoninDateToYear;
	}

	/**
	 * @return
	 */
	public String getShoninDateToMonth() {
		return shoninDateToMonth;
	}

	/**
	 * @return
	 */
	public String getShoninDateToDay() {
		return shoninDateToDay;
	}

	/**
	 * @return
	 */
	public String getHyojiHoshiki() {
		return hyojiHoshiki;
	}

	/**
	 * @return
	 */
	public List getJigyoList() {
		return jigyoList;
	}

	/**
	 * @return
	 */
	public List getJokyoList() {
		return jokyoList;
	}


	/**
	 * @param string
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
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
	public void setKaisu(String string) {
		kaisu = string;
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
	public void setNameKanjiMei(String string) {
		nameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameRoSei(String string) {
		nameRoSei = string;
	}

	/**
	 * @param string
	 */
	public void setNameRoMei(String string) {
		nameRoMei = string;
	}

	/**
	 * @param string
	 */
	public void setJokyoId(String string) {
		jokyoId = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateFromYear(String string) {
		sakuseiDateFromYear = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateFromMonth(String string) {
		sakuseiDateFromMonth = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateFromDay(String string) {
		sakuseiDateFromDay = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateToYear(String string) {
		sakuseiDateToYear = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateToMonth(String string) {
		sakuseiDateToMonth = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateToDay(String string) {
		sakuseiDateToDay = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateFromYear(String string) {
		shoninDateFromYear = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateFromMonth(String string) {
		shoninDateFromMonth = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateFromDay(String string) {
		shoninDateFromDay = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateToYear(String string) {
		shoninDateToYear = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateToMonth(String string) {
		shoninDateToMonth = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateToDay(String string) {
		shoninDateToDay = string;
	}

	/**
	 * @param string
	 */
	public void setHyojiHoshiki(String string) {
		hyojiHoshiki = string;
	}

	/**
	 * @param list
	 */
	public void setJigyoList(List list) {
		jigyoList = list;
	}

	/**
	 * @param list
	 */
	public void setJokyoList(List list) {
		jokyoList = list;
	}

	/**
	 * @return
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}

	/**
	 * @return
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}

	/**
	 * @return
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}

	/**
	 * @return
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}

	/**
	 * @param string
	 */
	public void setBukyokuCd(String string) {
		bukyokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanaMei(String string) {
		nameKanaMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanaSei(String string) {
		nameKanaSei = string;
	}

	/**
	 * @return
	 */
	public List getHyojiHoshikiList() {
		return hyojiHoshikiList;
	}

	/**
	 * @param list
	 */
	public void setHyojiHoshikiList(List list) {
		hyojiHoshikiList = list;
	}

}
