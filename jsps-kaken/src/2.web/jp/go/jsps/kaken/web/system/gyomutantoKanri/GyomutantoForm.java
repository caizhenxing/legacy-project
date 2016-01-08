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
package jp.go.jsps.kaken.web.system.gyomutantoKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: GyomutantoForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
 */
public class GyomutantoForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 業務担当者ID */
	private String gyomutantoId;

	/** パスワード */
	private String password;
	
	/** 業務担当者氏名（漢字-姓） */
	private String nameKanjiSei;

	/** 業務担当者氏名（漢字-名） */
	private String nameKanjiMei;

	/** 業務担当者氏名（フリガナ-姓） */
	private String nameKanaSei;

	/** 業務担当者氏名（フリガナ-名） */
	private String nameKanaMei;

	/** 部課名 */
	private String bukaName;
	
	/** 係名 */
	private String kakariName;

	/** 備考 */
	private String biko;

	/** 削除フラグ*/
	private String delFlg;

	/** 有効期限(年) */
	private String yukoDateYear;

	/** 有効期限(月) */
	private String yukoDateMonth;

	/** 有効期限(日) */
	private String yukoDateDay;

	/** 事業名 */
	private String jigyoName;

	/** 事業名選択リスト */
	private List jigyoNameList = new ArrayList();

	/** 事業選択値 */
	private List values = new ArrayList();

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public GyomutantoForm() {
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
		gyomutantoId = "";
		password = "";
		nameKanjiSei = "";
		nameKanjiMei = "";
		nameKanaSei = "";
		nameKanaMei = "";
		bukaName = "";
		kakariName = "";
		biko = "";
		yukoDateYear = "";
		yukoDateMonth = "";
		yukoDateDay = "";
		jigyoName = "";
		values = new ArrayList();
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

		/* この処理はvalidation-system.xmlで行う
		//日付妥当性チェック
		if (!StringUtil
			.isDate(
				getYukoDateYear()
					+ "/"
					+ getYukoDateMonth()
					+ "/"
					+ getYukoDateDay())) {

			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2003", "有効期限"));
		}
		*/

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
	public String getBukaName() {
		return bukaName;
	}

	/**
	 * @return
	 */
	public String getDelFlg() {
		return delFlg;
	}

	/**
	 * @return
	 */
	public String getGyomutantoId() {
		return gyomutantoId;
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
	public String getNameKanaMei() {
		return nameKanaMei;
	}

	/**
	 * @return
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
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
	public String getPassword() {
		return password;
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
	public void setBukaName(String string) {
		bukaName = string;
	}

	/**
	 * @param string
	 */
	public void setDelFlg(String string) {
		delFlg = string;
	}

	/**
	 * @param string
	 */
	public void setGyomutantoId(String string) {
		gyomutantoId = string;
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
	public void setNameKanaMei(String string) {
		nameKanaMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanaSei(String string) {
		nameKanaSei = string;
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
	public void setPassword(String string) {
		password = string;
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
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @return
	 */
	public List getJigyoNameList() {
		return jigyoNameList;
	}

	/**
	 * @return
	 */
	public List getValueList() {
		return values;
	}

	/**
	 * @param string
	 */
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param list
	 */
	public void setJigyoNameList(List list) {
		jigyoNameList = list;
	}

	/**
	 * @param list
	 */
	public void setValueList(List list) {
		values = list;
	}


	/**
	 * @return
	 */
	public Object getValues(int key) {
		return values.get(key);
	}

	/**
	 * @param string
	 */
	public void setValues(int key, Object value) {
		if(!values.contains(value)){
			values.add(value);
		}
	}
}
