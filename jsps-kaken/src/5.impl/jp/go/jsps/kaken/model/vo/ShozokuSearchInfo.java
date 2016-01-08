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

import jp.go.jsps.kaken.model.vo.SearchInfo;

/**
 * 所属機関検索条件を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShozokuSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class ShozokuSearchInfo extends SearchInfo{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 6539727479837879456L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** 機関種別コード */
	private String shubetuCd;
	
	/** 所属機関コード */
	private String shozokuCd;

	/** 所属機関名 */
	private String shozokuName;
	
	/** 担当者ID */
	private String shozokuTantoId;

	/** 担当氏名（姓） */
	private String tantoNameSei;

	/** 担当氏名（名） */
	private String tantoNameMei;
	
//	2005/04/20 追加 ここから-----------------------------------
//	理由 「部局担当者ID」「部局担当者検索条件フラグ」項目追加
	/** 部局担当者ID */
	private String bukyokuTantoId;
	
	/**	部局担当者検索条件フラグ */
	private String bukyokuSearchFlg;
//	追加 ここまで----------------------------------------------


	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShozokuSearchInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getShozokuTantoId() {
		return shozokuTantoId;
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
	 * @param string
	 */
	public void setShozokuTantoId(String string) {
		shozokuTantoId = string;
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
	 * @return
	 */
	public String getTantoNameMei() {
		return tantoNameMei;
	}

	/**
	 * @param string
	 */
	public void setTantoNameMei(String string) {
		tantoNameMei = string;
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
	 * @return bukyokuSearchFlg を戻します。
	 */
	public String getBukyokuSearchFlg() {
		return bukyokuSearchFlg;
	}
	/**
	 * @param bukyokuSearchFlg bukyokuSearchFlg を設定。
	 */
	public void setBukyokuSearchFlg(String bukyokuSearchFlg) {
		this.bukyokuSearchFlg = bukyokuSearchFlg;
	}
	/**
	 * @return bukyokuTantoId を戻します。
	 */
	public String getBukyokuTantoId() {
		return bukyokuTantoId;
	}
	/**
	 * @param bukyokuTantoId bukyokuTantoId を設定。
	 */
	public void setBukyokuTantoId(String bukyokuTantoId) {
		this.bukyokuTantoId = bukyokuTantoId;
	}
}
