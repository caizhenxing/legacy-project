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
package jp.go.jsps.kaken.web.system.shozokuKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * 所属機関情報検索フォーム
 * 
 * ID RCSfile="$RCSfile: ShozokuSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:02 $"
 */
public class ShozokuSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 部局種別選択リスト */
	private List shubetuCdList = new ArrayList();

	/** 機関種別コード */
	private String shubetuCd;
	
	/** 担当者ID */
	private String shozokuTantoId;

	/** 担当者名（姓） */
	private String tantoNameSei;
	
	/** 担当者名（名） */
	private String tantoNameMei;

	/** 所属機関コード */
	private String shozokuCd;

	/** 所属機関名 */
	private String shozokuName;
	
//	 2005/04/20 追加 ここから----------------------------------
//	 理由 「部局担当者ID」「部局担当者検索条件」項目追加
		/**	部局担当者ID */
		private String bukyokuTantoId;
		
		/**	部局担当者検索条件 */
		private String bukyokuSearchFlg;
		
		/** 部局担当者検索条件フラグ選択リスト */
		private List bukyokuSearchFlgList = new ArrayList();
//	 追加 ここまで---------------------------------------------
		
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShozokuSearchForm() {
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
		shubetuCd = "";
		shozokuTantoId = "";
		tantoNameSei = "";
		tantoNameMei = "";
		shozokuCd = "";
		shozokuName = "";
		bukyokuTantoId = "";
		bukyokuSearchFlg = "0";
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
	public String getShubetuCd() {
		return shubetuCd;
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
	public void setShubetuCd(String string) {
		shubetuCd = string;
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
	public List getShubetuCdList() {
		return shubetuCdList;
	}

	/**
	 * @param list
	 */
	public void setShubetuCdList(List list) {
		shubetuCdList = list;
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
	/**
	 * @return bukyokuSearchFlgList を戻します。
	 */
	public List getBukyokuSearchFlgList() {
		return bukyokuSearchFlgList;
	}
	/**
	 * @param bukyokuSearchFlgList bukyokuSearchFlgList を設定。
	 */
	public void setBukyokuSearchFlgList(List bukyokuSearchFlgList) {
		this.bukyokuSearchFlgList = bukyokuSearchFlgList;
	}
}
