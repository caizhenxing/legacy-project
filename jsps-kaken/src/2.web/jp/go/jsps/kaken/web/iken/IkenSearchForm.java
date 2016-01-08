/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム(JSPS)
 *    Source name : IkenSearchForm.java
 *    Description : 意見情報検索条件入力フォームクラス。
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/23    1.0         Xiang Emin     新規
 *
 *====================================================================== 
 */package jp.go.jsps.kaken.web.iken;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

/**
 * ご意見情報検索条件入力フォームクラス。
 * ID RCSfile="$RCSfile: IkenSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:18 $"
 */
public class IkenSearchForm extends BaseSearchForm {

	/** 申請者フラグ */
	private String shinseisya ;
	
	/** 所属機関担当者 */
	private String syozoku ;
	
	/** 部局担当者 */
	private String bukyoku ;
	
	/** 審査員　*/
	private String shinsyain ;
	
	/** 作成日(開始)(年) */
	private String     sakuseiDateFromYear;
	
	/** 作成日(開始)(月) */
	private String 	sakuseiDateFromMonth;

	/** 作成日(開始)(日) */
	private String 	sakuseiDateFromDay;
	
	/** 作成日(終了)(年) */
	private String     sakuseiDateToYear;
	
	/** 作成日(終了)(月) */
	private String 	sakuseiDateToMonth;

	/** 作成日(終了)(日) */
	private String 	sakuseiDateToDay;

	/** 表示方式 */
	private String dispmode ;
	
	/**
	 * コンストラクタ。
	 */
	public IkenSearchForm() {
		super();
		init();
	}

	/**
	 * 初期化処理
	 */
	public void init() {
		shinseisya = "";
		syozoku = "";
		bukyoku = "";
		shinsyain = "";
		sakuseiDateFromYear= "";
		sakuseiDateFromMonth= "";
		sakuseiDateFromDay= "";
		sakuseiDateToYear= "";
		sakuseiDateToMonth= "";
		sakuseiDateToDay= "";
		dispmode = "";
	}

	/**
	 * 申請者フラグの取得
	 * @return
	 */
	public String getShinseisya(){
		return shinseisya;
	}
	
	/**
	 * 申請者フラグの設定
	 * @param n
	 */
	public void setShinseisya(String n){
		shinseisya = n;
	}

	/**
	 * 所属機関担当者フラグの取得
	 * @return
	 */
	public String getSyozoku(){
		return syozoku;
	}
	
	/**
	 * 所属機関担当者フラグの設定
	 * @param n
	 */
	public void setSyozoku(String n){
		syozoku = n;
	}
	
	/**
	 * 部局担当者フラグの取得
	 * @return
	 */
	public String getBukyoku(){
		return bukyoku;
	}
	
	/**
	 * 部局担当者フラグの設定
	 * @param n
	 */
	public void setBukyoku(String n){
		bukyoku = n;
	}

	/**
	 * 申請者フラグの取得
	 * @return
	 */
	public String getShinsyain(){
		return shinsyain;
	}
	
	/**
	 * 申請者フラグの設定
	 * @param n
	 */
	public void setShinsyain(String n){
		shinsyain = n;
	}
	
	/**
	 * 投稿日開始年の取得
	 * @return
	 */
	public String getSakuseiDateFromYear() {
		return sakuseiDateFromYear;
	}
	
	/**
	 * 投稿日開始年の設定
	 * @param str
	 */
	public void setSakuseiDateFromYear(String str) {
		sakuseiDateFromYear = str;
	}

	/**
	 * 投稿日開始月の取得
	 * @return
	 */
	public String getSakuseiDateFromMonth() {
		return sakuseiDateFromMonth;
	}

	/**
	 * 投稿日開始月の設定
	 * @param str
	 */
	public void setSakuseiDateFromMonth(String str) {
		sakuseiDateFromMonth = str;
	}
	
	/**
	 * 投稿日開始日の取得
	 * @return
	 */
	public String getSakuseiDateFromDay() {
		return sakuseiDateFromDay;
	}

	/**
	 * 投稿日開始日の設定
	 * @param str
	 */
	public void setSakuseiDateFromDay(String str) {
		sakuseiDateFromDay = str;
	}
	
	/**
	 * 投稿日終了年の取得
	 * @return
	 */
	public String getSakuseiDateToYear() {
		return sakuseiDateToYear;
	}

	/**
	 * 投稿日終了年の設定
	 * @param str
	 */
	public void setSakuseiDateToYear(String str) {
		sakuseiDateToYear = str;
	}

	/**
	 * 投稿日終了月の取得
	 * @return
	 */
	public String getSakuseiDateToMonth() {
		return sakuseiDateToMonth;
	}

	/**
	 * 投稿日終了月の設定
	 * @param str
	 */
	public void setSakuseiDateToMonth(String str) {
		sakuseiDateToMonth = str;
	}

	/**
	 * 投稿日終了日の取得
	 * @return
	 */
	public String getSakuseiDateToDay() {
		return sakuseiDateToDay;
	}

	/**
	 * 投稿日終了日の設定
	 * @param str
	 */
	public void setSakuseiDateToDay(String str) {
		sakuseiDateToDay = str;
	}

	/**
	 * 申請者フラグの取得
	 * @return
	 */
	public String getDispmode(){
		return dispmode;
	}
	
	/**
	 * 申請者フラグの設定
	 * @param n
	 */
	public void setDispmode(String n){
		dispmode = n;
	}
}
