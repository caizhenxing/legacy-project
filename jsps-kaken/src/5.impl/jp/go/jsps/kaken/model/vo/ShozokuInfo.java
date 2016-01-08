/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Date;

/**
 * 所属機関情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShozokuInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class ShozokuInfo extends ShozokuPk{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** 所属機関コード */
	private String shozokuCd;
	
	/** 機関種別コード */
	private String shubetuCd;

	/** 所属機関名(和文) */
	private String shozokuName;
	
	/** 所属機関名(略称) */
	private String shozokuRyakusho;

	/** 所属機関名(英文) */
	private String shozokuNameEigo;

	/** パスワード */
	private String password;

	/** 責任者（姓） */
	private String sekininshaNameSei;

	/** 責任者（名） */
	private String sekininshaNameMei;

	/** 責任者役職 */
	private String sekininshaYaku;

	/** 責任者電話番号 */
	private String sekininshaTel;

	/** 担当部局名 */
	private String bukyokuName;

	/** 担当課名 */
	private String kaName;

	/** 担当係名 */
	private String kakariName;

	/** 担当者名（姓） */
	private String tantoNameSei;

	/** 担当者名（名） */
	private String tantoNameMei;

	/** 担当者部局所在地（電話番号） */
	private String tantoTel;

	/** 担当者部局所在地（FAX番号） */
	private String tantoFax;
	
	/** 担当者部局所在地（Email） */
	private String tantoEmail;
	
	/** 担当者部局所在地（Email2） */
	private String tantoEmail2;

	/** 担当者部局所在地（郵便番号） */
	private String tantoZip;

	/** 担当者部局所在地（住所） */
	private String tantoAddress;

	/** 認証キー発行フラグ */
	private String ninshokeyFlg;
	
	/** 備考 */
	private String biko;

	/** 有効期限 */
	private Date yukoDate;

	//2005.08.10 iso ID発行日付を追加
	/** 有効期限 */
	private Date idDate;
	
// 2005/04/20 追加 ここから-------------------------------
// 理由 「部局担当者人数」追加
	/** 部局担当者人数 */
	private String bukyokuNum;
// 追加 ここまで------------------------------------------
	
	/** 削除フラグ */
	private String delFlg;
	
//	 2005/04/20 追加 ここから-------------------------------
//	 理由 「担当フラグ」追加
	/** 担当フラグ
	 * システム管理者機能の所属機関情報検索で使用。
	 * 所属機関：0　部局：1
	 */
	private String tantoFlg;
//	 追加 ここまで------------------------------------------
	
//	 2005/04/21 追加 ここから-------------------------------
//	 理由 更新処理用、追加部局担当者人数
		/** 追加部局担当者人数 */
		private int addBukyokuNum;
//	 追加 ここまで------------------------------------------
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShozokuInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * @return
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
	 * @return
	 */
	public String getKakariName() {
		return kakariName;
	}

	/**
	 * @return
	 */
	public String getKaName() {
		return kaName;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public String getSekininshaNameMei() {
		return sekininshaNameMei;
	}

	/**
	 * @return
	 */
	public String getSekininshaNameSei() {
		return sekininshaNameSei;
	}

	/**
	 * @return
	 */
	public String getSekininshaTel() {
		return sekininshaTel;
	}

	/**
	 * @return
	 */
	public String getSekininshaYaku() {
		return sekininshaYaku;
	}

	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @return
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}

	/**
	 * @return
	 */
	public String getTantoAddress() {
		return tantoAddress;
	}

	/**
	 * @return
	 */
	public String getTantoEmail() {
		return tantoEmail;
	}

	/**
	 * @return
	 */
	public String getTantoFax() {
		return tantoFax;
	}

	/**
	 * @return
	 */
	public String getTantoNameMei() {
		return tantoNameMei;
	}

	/**
	 * @return
	 */
	public String getTantoNameSei() {
		return tantoNameSei;
	}

	/**
	 * @return
	 */
	public String getTantoTel() {
		return tantoTel;
	}

	/**
	 * @return
	 */
	public String getTantoZip() {
		return tantoZip;
	}

	/**
	 * @return
	 */
	public Date getYukoDate() {
		return yukoDate;
	}

	/**
	 * @param string
	 */
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuName(String string) {
		bukyokuName = string;
	}

	/**
	 * @param string
	 */
	public void setKakariName(String string) {
		kakariName = string;
	}

	/**
	 * @param string
	 */
	public void setKaName(String string) {
		kaName = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @param string
	 */
	public void setSekininshaNameMei(String string) {
		sekininshaNameMei = string;
	}

	/**
	 * @param string
	 */
	public void setSekininshaNameSei(String string) {
		sekininshaNameSei = string;
	}

	/**
	 * @param string
	 */
	public void setSekininshaTel(String string) {
		sekininshaTel = string;
	}

	/**
	 * @param string
	 */
	public void setSekininshaYaku(String string) {
		sekininshaYaku = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameEigo(String string) {
		shozokuNameEigo = string;
	}

	/**
	 * @param string
	 */
	public void setTantoAddress(String string) {
		tantoAddress = string;
	}

	/**
	 * @param string
	 */
	public void setTantoEmail(String string) {
		tantoEmail = string;
	}

	/**
	 * @param string
	 */
	public void setTantoFax(String string) {
		tantoFax = string;
	}

	/**
	 * @param string
	 */
	public void setTantoNameMei(String string) {
		tantoNameMei = string;
	}

	/**
	 * @param string
	 */
	public void setTantoNameSei(String string) {
		tantoNameSei = string;
	}

	/**
	 * @param string
	 */
	public void setTantoTel(String string) {
		tantoTel = string;
	}

	/**
	 * @param string
	 */
	public void setTantoZip(String string) {
		tantoZip = string;
	}

	/**
	 * @param string
	 */
	public void setYukoDate(Date date) {
		yukoDate = date;
	}

	/**
	 * @return
	 */
	public String getDelFlg() {
		return delFlg;
	}

	/**
	 * @param date
	 */
	public void setDelFlg(String string) {
		delFlg = string;
	}


	/**
	 * @return
	 */
	public String getShubetuCd() {
		return shubetuCd;
	}

	/**
	 * @param string
	 */
	public void setShubetuCd(String string) {
		shubetuCd = string;
	}

	/**
	 * @return
	 */
	public String getNinshokeyFlg() {
		return ninshokeyFlg;
	}

	/**
	 * @return
	 */
	public String getTantoEmail2() {
		return tantoEmail2;
	}

	/**
	 * @param string
	 */
	public void setNinshokeyFlg(String string) {
		ninshokeyFlg = string;
	}

	/**
	 * @param string
	 */
	public void setTantoEmail2(String string) {
		tantoEmail2 = string;
	}

	/**
	 * @return
	 */
	public String getShozokuRyakusho() {
		return shozokuRyakusho;
	}

	/**
	 * @param string
	 */
	public void setShozokuRyakusho(String string) {
		shozokuRyakusho = string;
	}
	
	/**
	 * @return bukyokuNum を戻します。
	 */
	public String getBukyokuNum() {
		return bukyokuNum;
	}
	/**
	 * @param bukyokuNum bukyokuNum を設定。
	 */
	public void setBukyokuNum(String bukyokuNum) {
		this.bukyokuNum = bukyokuNum;
	}
	
	/**
	 * @return shubetsuNo を戻します。
	 */
	public String getTantoFlg() {
		return tantoFlg;
	}
	/**
	 * @param shubetsuNo shubetsuNo を設定。
	 */
	public void setTantoFlg(String shubetsuNo) {
		this.tantoFlg = shubetsuNo;
	}
	
	/**
	 * @return addBukyokuNum を戻します。
	 */
	public int getAddBukyokuNum() {
		return addBukyokuNum;
	}
	/**
	 * @param addBukyokuNum addBukyokuNum を設定。
	 */
	public void setAddBukyokuNum(int addBukyokuNum) {
		this.addBukyokuNum = addBukyokuNum;
	}
	/**
	 * @return
	 */
	public Date getIdDate() {
		return idDate;
	}

	/**
	 * @param date
	 */
	public void setIdDate(Date date) {
		idDate = date;
	}

}
