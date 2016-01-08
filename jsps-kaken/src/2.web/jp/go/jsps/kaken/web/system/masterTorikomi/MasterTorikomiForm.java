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
package jp.go.jsps.kaken.web.system.masterTorikomi;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.BaseSearchForm;

//import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping; 
import org.apache.struts.upload.FormFile;

/**
 * 申請情報システム番号フォーム
 * 
 * ID RCSfile="$RCSfile: MasterTorikomiForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
 */
public class MasterTorikomiForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** マスタ種別 */
	private String   masterShubetu    = "";

	/** 新規更新フラグ */
	//2005.10.18 iso 初期値を更新に変更
//	private String   shinkiKoshinFlg  = "0";
	private String   shinkiKoshinFlg  = "1";
	
	/** アップロードファイル */
	private FormFile uploadCsv        = null;

	/** マスタ管理一覧リスト */
	private List     masterKanriList  = new ArrayList();

	/** マスタ種別コンボ */
	private List     shubetuComboList = new ArrayList();

	/** 新規更新フラグリスト */
	private List     shinkiKoshinList = new ArrayList();

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public MasterTorikomiForm() {
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
	 * @param string
	 */
	public void setMasterShubetu(String string) {
		masterShubetu = string;
	}

	/**
	 * @param string
	 */
	public void setShinkiKoshinFlg(String string) {
		shinkiKoshinFlg = string;
	}

	/**
	 * @param file
	 */
	public void setUploadCsv(FormFile file) {
		uploadCsv = file;
	}

	/**
	 * @param list
	 */
	public void setMasterKanriList(List list) {
		masterKanriList = list;
	}

	/**
	 * @param list
	 */
	public void setShubetuComboList(List list) {
		shubetuComboList = list;
	}

	/**
	 * @param list
	 */
	public void setShinkiKoshinList(List list) {
		shinkiKoshinList = list;
	}


	/**
	 * @return
	 */
	public String getMasterShubetu() {
		return masterShubetu;
	}

	/**
	 * @return
	 */
	public String getShinkiKoshinFlg() {
		return shinkiKoshinFlg;
	}

	/**
	 * @return
	 */
	public FormFile getUploadCsv() {
		return uploadCsv;
	}

	/**
	 * @return
	 */
	public List getMasterKanriList() {
		return masterKanriList;
	}

	/**
	 * @return
	 */
	public List getShubetuComboList() {
		return shubetuComboList;
	}

	/**
	 * @return
	 */
	public List getShinkiKoshinList() {
		return shinkiKoshinList;
	}

}
