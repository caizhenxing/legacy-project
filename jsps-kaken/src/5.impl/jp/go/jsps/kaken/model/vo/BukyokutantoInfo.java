/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/03/24
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Date;
import java.util.List;


/**
 * 部局担当者情報を保持するクラス。
 * 
 */
public class BukyokutantoInfo extends BukyokutantoPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** 部局担当者ID */
	private String bukyokutantoId;
	
	/** パスワード */
	private String password;

	/** 部局コード */
	private String bukyokuCd;

	/** 所属機関コード */
	private String shozokuCd;
	
	/** 担当者名（姓） */
	private String tantoNameSei;

	/** 担当者名（名） */
	private String tantoNameMei;
	
	/** 電話番号 */
	private String bukyokuTel;

	/** FAX番号 */
	private String bukyokuFax;
	
	/** Email */
	private String bukyokuEmail;

	/** 部課名 */
	private String bukaName;
	
	/** デフォルトパスワード */
	private String defaultPassword;

	/** 登録済みフラグ */
	private String registFlg;

	/** 削除フラグ */
	private String delFlg;
	
	/** 担当部局フラグ */
	private boolean tantoFlg;
	
	/** 有効期限 */
	private Date yukoDate;
	
	/** 担当部局コード */
	private String tantoBukyokuCd;
	
	/** 備考 */
	private String biko;
	
	// 2005/04/07 追加ここから---------------------------------------------
	// 理由 部局担当者情報の登録、更新、削除、パスワード変更処理追加のため
	
	/** 部局コード */
	private List bukyokuList;
	
	/** 係名 */
	private String kakariName;
	
	/** 所属機関名 */
	private String shozokuName;
	
	/** 所属機関名(英文) */
	private String shozokuNameEigo;
	
	/** アクション */
	private String action;
	
	// 追加 ここまで--------------------------------------------------------
	
	// 2005/04/21 追加ここから---------------------------------------------
	// 理由 所属機関担当者の削除フラグ（ログイン時にチェック）
	/** 所属機関担当者削除フラグ */
	private String delFlgShozoku;
	// 追加 ここまで--------------------------------------------------------
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public BukyokutantoInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return bukaName を戻します。
	 */
	public String getBukaName() {
		return bukaName;
	}
	/**
	 * @param bukaName bukaName を設定。
	 */
	public void setBukaName(String bukaName) {
		this.bukaName = bukaName;
	}
	/**
	 * @return bukyokuCd を戻します。
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}
	/**
	 * @param bukyokuCd bukyokuCd を設定。
	 */
	public void setBukyokuCd(String bukyokuCd) {
		this.bukyokuCd = bukyokuCd;
	}
	/**
	 * @return bukyokuEmail を戻します。
	 */
	public String getBukyokuEmail() {
		return bukyokuEmail;
	}
	/**
	 * @param bukyokuEmail bukyokuEmail を設定。
	 */
	public void setBukyokuEmail(String bukyokuEmail) {
		this.bukyokuEmail = bukyokuEmail;
	}
	/**
	 * @return bukyokuFax を戻します。
	 */
	public String getBukyokuFax() {
		return bukyokuFax;
	}
	/**
	 * @param bukyokuFax bukyokuFax を設定。
	 */
	public void setBukyokuFax(String bukyokuFax) {
		this.bukyokuFax = bukyokuFax;
	}
	/**
	 * @return bukyokutantoId を戻します。
	 */
	public String getBukyokutantoId() {
		return bukyokutantoId;
	}
	/**
	 * @param bukyokutantoId bukyokutantoId を設定。
	 */
	public void setBukyokutantoId(String bukyokutantoId) {
		this.bukyokutantoId = bukyokutantoId;
	}
	/**
	 * @return bukyokuTel を戻します。
	 */
	public String getBukyokuTel() {
		return bukyokuTel;
	}
	/**
	 * @param bukyokuTel bukyokuTel を設定。
	 */
	public void setBukyokuTel(String bukyokuTel) {
		this.bukyokuTel = bukyokuTel;
	}
	/**
	 * @return defaultPassword を戻します。
	 */
	public String getDefaultPassword() {
		return defaultPassword;
	}
	/**
	 * @param defaultPassword defaultPassword を設定。
	 */
	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}
	/**
	 * @return delFlg を戻します。
	 */
	public String getDelFlg() {
		return delFlg;
	}
	/**
	 * @param delFlg delFlg を設定。
	 */
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	/**
	 * @return password を戻します。
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password password を設定。
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return registFlg を戻します。
	 */
	public String getRegistFlg() {
		return registFlg;
	}
	/**
	 * @param registFlg registFlg を設定。
	 */
	public void setRegistFlg(String registFlg) {
		this.registFlg = registFlg;
	}
	/**
	 * @return shozokuCd を戻します。
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}
	/**
	 * @param shozokuCd shozokuCd を設定。
	 */
	public void setShozokuCd(String shozokuCd) {
		this.shozokuCd = shozokuCd;
	}
	/**
	 * @return tantoNameMei を戻します。
	 */
	public String getTantoNameMei() {
		return tantoNameMei;
	}
	/**
	 * @param tantoNameMei tantoNameMei を設定。
	 */
	public void setTantoNameMei(String tantoNameMei) {
		this.tantoNameMei = tantoNameMei;
	}
	/**
	 * @return tantoNameSei を戻します。
	 */
	public String getTantoNameSei() {
		return tantoNameSei;
	}
	/**
	 * @param tantoNameSei tantoNameSei を設定。
	 */
	public void setTantoNameSei(String tantoNameSei) {
		this.tantoNameSei = tantoNameSei;
	}
	/**
	 * @return tantoFlg を戻します。
	 */
	public boolean getTantoFlg() {
		return tantoFlg;
	}
	/**
	 * @param tantoFlg tantoFlg を設定。
	 */
	public void setTantoFlg(boolean tantoFlg) {
		this.tantoFlg = tantoFlg;
	}
	/**
	 * @return yukoDate を戻します。
	 */
	public Date getYukoDate() {
		return yukoDate;
	}
	/**
	 * @param yukoDate yukoDate を設定。
	 */
	public void setYukoDate(Date yukoDate) {
		this.yukoDate = yukoDate;
	}
	/**
	 * @return tantoBukyokuCd を戻します。
	 */
	public String getTantoBukyokuCd() {
		return tantoBukyokuCd;
	}
	/**
	 * @param tantoBukyokuCd tantoBukyokuCd を設定。
	 */
	public void setTantoBukyokuCd(String tantoBukyokuCd) {
		this.tantoBukyokuCd = tantoBukyokuCd;
	}
	/**
	 * @return biko を戻します。
	 */
	public String getBiko() {
		return biko;
	}
	/**
	 * @param biko biko を設定。
	 */
	public void setBiko(String biko) {
		this.biko = biko;
	}
	
	// 2005/04/07 追加ここから---------------------------------------------
	// 理由 部局担当者情報の登録、更新、削除、パスワード変更処理追加のため
	
	/**
	 * @return bukyokuListを戻します。
	 */
	public List getBukyokuList() {
		return bukyokuList;
	}

	/**
	 * @param bukyokuList bukoykuListを設定。
	 */
	public void setBukyokuList(List bukyokuList) {
		this.bukyokuList = bukyokuList;
	}

	/**
	 * @return kakariNameを戻します。
	 */
	public String getKakariName() {
		return kakariName;
	}

	/**
	 * @param kakariName kakariNameを設定。
	 */
	public void setKakariName(String kakariName) {
		this.kakariName = kakariName;
	}

	/**
	 * @return shozokuNameを戻します。
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @return shozokuNameEigoを戻します。
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}

	/**
	 * @param shozokuName shozokuNameを設定。
	 */
	public void setShozokuName(String shozokuName) {
		this.shozokuName = shozokuName;
	}

	/**
	 * @param shozokuNameEigo shozokuNameEigoを設定。
	 */
	public void setShozokuNameEigo(String shozokuNameEigo) {
		this.shozokuNameEigo = shozokuNameEigo;
	}

	/**
	 * @return actionを戻します。
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action actionを設定
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
	// 追加 ここまで--------------------------------------------------------

	/**
	 * @return delFlgShozoku を戻します。
	 */
	public String getDelFlgShozoku() {
		return delFlgShozoku;
	}
	/**
	 * @param delFlgShozoku delFlgShozoku を設定。
	 */
	public void setDelFlgShozoku(String delFlgShozoku) {
		this.delFlgShozoku = delFlgShozoku;
	}
}