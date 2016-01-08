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

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: ShozokuForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:02 $"
 */
public class ShozokuForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** ID */
	private String shozokuTantoId;
	
	/** 所属機関名（コード） */
	private String shozokuCd;
	
	/** 所属機関名（英文） */
	private String shozokuNameEigo;
	
	/** パスワード */
	private String password;

	/** 責任者（姓） */
	private String sekininshaNameSei;

	/** 責任者（名） */
	private String sekininshaNameMei;

	/** 責任者役職 */
	private String sekininshaYaku;

	/** 担当部課名 */
	private String bukyokuName;

	/** 担当係名 */
	private String kakariName;

	/** 担当者氏名（姓） */
	private String tantoNameSei;

	/** 担当者氏名（名） */
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

	/** 備考 */
	private String biko;

	/** 有効期限(年) */
	private String yukoDateYear;

	/** 有効期限(月) */
	private String yukoDateMonth;

	/** 有効期限(日) */
	private String yukoDateDay;
	
//	2005/04/20 追加 ここから---------------------------------
//	理由 「部局担当者人数」項目追加
	/** 部局担当者人数 */
	private String bukyokuNum;
//	追加 ここまで--------------------------------------------

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShozokuForm() {
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
		shozokuTantoId = "";
		shozokuCd = "";
		shozokuNameEigo = "";
		password = "";
		sekininshaNameSei = "";
		sekininshaNameMei = "";
		sekininshaYaku = "";
		bukyokuName = "";
		kakariName = "";
		tantoNameSei = "";
		tantoNameMei = "";
		tantoTel = "";
		tantoFax = "";
		tantoEmail = "";
		tantoEmail2 ="";
		tantoZip = "";
		tantoAddress = "";
		biko = "";
		yukoDateYear = "";
		yukoDateMonth = "";
		yukoDateDay = "";
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
	public String getTantoEmail() {
		return tantoEmail;
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
	public String getPassword() {
		return password;
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
	public String getTantoAddress() {
		return tantoAddress;
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
	public String getTantoTel() {
		return tantoTel;
	}

	/**
	 * @return
	 */
	public String getYukoDateDay() {
		return yukoDateDay;
	}

	/**
	 * @return
	 */
	public String getYukoDateMonth() {
		return yukoDateMonth;
	}

	/**
	 * @return
	 */
	public String getYukoDateYear() {
		return yukoDateYear;
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
	public void setTantoEmail(String string) {
		tantoEmail = string;
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
	public void setPassword(String string) {
		password = string;
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
	public void setTantoAddress(String string) {
		tantoAddress = string;
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
	public void setTantoTel(String string) {
		tantoTel = string;
	}

	/**
	 * @param string
	 */
	public void setYukoDateDay(String string) {
		yukoDateDay = string;
	}

	/**
	 * @param string
	 */
	public void setYukoDateMonth(String string) {
		yukoDateMonth = string;
	}

	/**
	 * @param string
	 */
	public void setYukoDateYear(String string) {
		yukoDateYear = string;
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
	public String getTantoNameMei() {
		return tantoNameMei;
	}

	/**
	 * @return
	 */
	public String getTantoZip() {
		return tantoZip;
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
	public void setTantoNameMei(String string) {
		tantoNameMei = string;
	}

	/**
	 * @param string
	 */
	public void setTantoZip(String string) {
		tantoZip = string;
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
	public String getTantoEmail2() {
		return tantoEmail2;
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
	public void setTantoEmail2(String string) {
		tantoEmail2 = string;
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
}
