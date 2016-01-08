/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : JuriCheckListForm.java
 *    Description : チェックリスト用フォーム。
 *
 *    Author      : Admin
 *    Date        : 2005/04/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/07/15    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import jp.go.jsps.kaken.util.Page;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * チェックリスト用フォーム
 * 
 * @author masuo_t
 */
public class JuriCheckListForm extends BaseValidatorForm{

// 20050715
    /** ページ情報 */
	private Page checkListPage;

    /** 事業ID */
	private String jigyoID;

    /** 事業CD */
	private String jigyoCD;

    /** 所属CD */
	private String shozokuCD;

    /** 備考 */
	private String juriBiko;

    /** 受理結果 */
	private String juriKekka;

    /** 状況ID */
	private String jokyoID;

    /** 回数 */
	private String kaisu;

    /** 受理結果情報選択リスト */
	private List juriFujuri = new ArrayList();

// 2006/07/21 dyh delete start 理由：使用しない
//    /** 受理結果リスト表示文字列 */
//	private String listValue;
// 2006/07/21 dyh delete end

    /** 受理フラグ:受理 */
	static final int Juri = 0;

    /** 受理フラグ:不受理 */
	static final int Fujuri = 1;
// Horikoshi

/************************************************************************************/

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public JuriCheckListForm() {
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
		jigyoID		=	"";							/** 事業ID */
		jigyoCD		=	"";							/** 事業CD */
		shozokuCD	=	"";							/** 所属CD */
		juriBiko	=	"";							/** 備考 */
		juriKekka	=	"";							/** 受理結果 */
		jokyoID		=	"";
		kaisu		=	"";
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

// 20050715

	/** ページ情報 **/
    /**
     * ページ情報を取得
     * @return ページ情報
     */
	public Page getCheckListPage() {
		return checkListPage;
	}

    /**
     * ページ情報を設定
     * @param checkListPage ページ情報
     */
	public void setCheckListPage(Page checkListPage) {
		this.checkListPage = checkListPage;
	}

	/** 受理不受理 **/
    /**
     * 受理結果情報選択リストを取得
     * @return 受理結果情報選択リスト
     */
	public List getJuriFujuri() {
		return juriFujuri;
	}

    /**
     * 受理結果情報選択リストを設定
     * @param JuriFujuri 受理結果情報選択リスト
     */
	public void setJuriFujuri(List JuriFujuri) {
		juriFujuri = JuriFujuri;
	}

	/** 受理備考 **/
    /**
     * 備考を取得
     * @return 備考
     */
	public String getJuriBiko() {
		return juriBiko;
	}
    /**
     * 備考を設定
     * @param JuriBiko 備考
     */
	public void setJuriBiko(String JuriBiko) {
		juriBiko = JuriBiko;
	}

	/** 受理結果 **/
    /**
     * 受理結果を取得
     * @return 受理結果
     */
	public String getJuriKekka() {
		return juriKekka;
	}
    /**
     * 受理結果を設定
     * @param JuriKekka 受理結果
     */
	public void setJuriKekka(String JuriKekka) {
		juriKekka = JuriKekka;
	}

// Horikoshi

	/** 事業ID **/
    /**
     * 事業IDを取得
     * @return 事業ID
     */
	public String getJigyoID() {
		return jigyoID;
	}
    /**
     * 事業IDを設定
     * @param JigyoID 事業ID
     */
	public void setJigyoID(String JigyoID) {
		jigyoID = JigyoID;
	}

	/** 所属CD **/
    /**
     * 所属CDを取得
     * @return 所属CD
     */
	public String getShozokuCD() {
		return shozokuCD;
	}
    /**
     * 所属CDを設定
     * @param ShozokuCD 所属CD
     */
	public void setShozokuCD(String ShozokuCD) {
		shozokuCD = ShozokuCD;
	}

	/** 事業CD **/
    /**
     * 事業CDを取得
     * @return 事業CD
     */
	public String getJigyoCD() {
		return jigyoCD;
	}
    /**
     * 事業CDを設定
     * @param JigyoCD 事業CD
     */
	public void setJigyoCD(String JigyoCD) {
		jigyoCD = JigyoCD;
	}

	/** 回数 **/
    /**
     * 回数を取得
     * @return 回数
     */
	public String getKaisu() {
		return kaisu;
	}
    /**
     * 回数を設定
     * @param Kaisu 回数
     */
	public void setKaisu(String Kaisu) {
		kaisu = Kaisu;
	}
	
	/** 状況ID **/
    /**
     * 状況IDを取得
     * @return 状況ID
     */
	public String getJokyoID() {
		return jokyoID;
	}
    /**
     * 状況IDを設定
     * @param JokyoID 状況ID
     */
	public void setJokyoID(String JokyoID) {
		jokyoID = JokyoID;
	}
}