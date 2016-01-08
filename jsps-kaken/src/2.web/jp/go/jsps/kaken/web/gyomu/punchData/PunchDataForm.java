/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.punchData;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * 申請情報システム番号フォーム
 * 
 * ID RCSfile="$RCSfile: PunchDataForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class PunchDataForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** パンチ種別 */
	private String   punchShubetu    = "";

	/** パンチデータ一覧リスト */
	private List     punchKanriList  = new ArrayList();


	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public PunchDataForm() {
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
	 * パンチデータ一覧リストを戻る
	 * @return パンチデータ一覧リスト
	 */
	public List getPunchKanriList() {
		return punchKanriList;
	}

	/**
	 * パンチ種別を戻る
	 * @return パンチ種別
	 */
	public String getPunchShubetu() {
		return punchShubetu;
	}

	/**
	 * パンチデータ一覧リストを設定
	 * @param list パンチデータ一覧リスト
	 */
	public void setPunchKanriList(List list) {
		punchKanriList = list;
	}

	/**
	 * パンチ種別を設定
	 * @param string パンチ種別
	 */
	public void setPunchShubetu(String string) {
		punchShubetu = string;
	}
}