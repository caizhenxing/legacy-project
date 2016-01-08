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
package jp.go.jsps.kaken.web.gyomu.warifuri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * 審査員情報検索フォーム
 * 
 * ID RCSfile="$RCSfile: ShinsainSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class ShinsainSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** 事業区分 */
	private String	jigyoKubun;
	
	/** 審査員番号 */
	private String shinsainNo;
	
	/** 審査員氏名（漢字-姓） */
	private String nameKanjiSei;

	/** 審査員氏名（漢字-名） */
	private String nameKanjiMei;

	/** 所属機関コード*/
	private String shozokuCd;
	
	/** 細目番号*/
	private String bunkaSaimokuCd;
	
	/** 事業名選択リスト */
	private List jigyoKubunList = new ArrayList();

	/** 審査員番号(審査員情報表示用) */
	private String shinsainNo4View;

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsainSearchForm() {
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
		jigyoKubun = "";
		nameKanjiSei = "";
		nameKanjiMei = "";
		bunkaSaimokuCd = "";
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
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @return
	 */
	public List getJigyoKubunList() {
		return jigyoKubunList;
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
	public String getShinsainNo() {
		return shinsainNo;
	}

	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

	/**
	 * @param list
	 */
	public void setJigyoKubunList(List list) {
		jigyoKubunList = list;
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
	public void setShinsainNo(String string) {
		shinsainNo = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
	 * @return
	 */
	public String getShinsainNo4View() {
		return shinsainNo4View;
	}

	/**
	 * @param string
	 */
	public void setShinsainNo4View(String string) {
		shinsainNo4View = string;
	}

	/**
	 * @return
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

	/**
	 * @param string
	 */
	public void setBunkaSaimokuCd(String string) {
		bunkaSaimokuCd = string;
	}

}
