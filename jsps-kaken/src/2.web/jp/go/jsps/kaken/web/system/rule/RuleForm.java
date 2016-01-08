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
package jp.go.jsps.kaken.web.system.rule;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: RuleForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:45 $"
 */
public class RuleForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 対象者IDリスト */
	private List taishoIdList = new ArrayList();

	/** 文字数リスト */
	private List mojisuChkList = new ArrayList();

	/** 大文字・小文字の混在リスト */
	private List charChk1List = new ArrayList();

	/** 大文字・小文字の混在ラジオリスト */
	private List radioCharChk1List = new ArrayList();

	/** アルファベットと数字の混在リスト */
	private List charChk2List = new ArrayList();

	/** アルファベットと数字の混在ラジオリスト */
	private List radioCharChk2List = new ArrayList();

	/** 予備1リスト */
	private List charChk3List = new ArrayList();

	/** 予備1ラジオリスト */
	private List radioCharChk3List = new ArrayList();

	/** 予備2リスト */
	private List charChk4List = new ArrayList();

	/** 予備2ラジオリスト */
	private List radioCharChk4List = new ArrayList();

	/** 予備3リスト */
	private List charChk5List = new ArrayList();

	/** 予備3ラジオリスト */
	private List radioCharChk5List = new ArrayList();

	/** 備考リスト*/
	private List bikoList = new ArrayList();

	/** 有効期限(年)リスト */
	private List yukoDateYearList = new ArrayList();

	/** 有効期限(月)リスト */
	private List yukoDateMonthList = new ArrayList();

	/** 有効期限(日)リスト */
	private List yukoDateDayList = new ArrayList();

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public RuleForm() {
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
	 * @return
	 */
	public List getBikoList() {
		return bikoList;
	}

	/**
	 * @return
	 */
	public List getCharChk1List() {
		return charChk1List;
	}

	/**
	 * @return
	 */
	public List getCharChk2List() {
		return charChk2List;
	}

	/**
	 * @return
	 */
	public List getCharChk3List() {
		return charChk3List;
	}

	/**
	 * @return
	 */
	public List getCharChk4List() {
		return charChk4List;
	}

	/**
	 * @return
	 */
	public List getCharChk5List() {
		return charChk5List;
	}

	/**
	 * @return
	 */
	public List getMojisuChkList() {
		return mojisuChkList;
	}

	/**
	 * @return
	 */
	public List getRadioCharChk1List() {
		return radioCharChk1List;
	}

	/**
	 * @return
	 */
	public List getRadioCharChk2List() {
		return radioCharChk2List;
	}

	/**
	 * @return
	 */
	public List getRadioCharChk3List() {
		return radioCharChk3List;
	}

	/**
	 * @return
	 */
	public List getRadioCharChk4List() {
		return radioCharChk4List;
	}

	/**
	 * @return
	 */
	public List getRadioCharChk5List() {
		return radioCharChk5List;
	}

	/**
	 * @return
	 */
	public List getTaishoIdList() {
		return taishoIdList;
	}

	/**
	 * @return
	 */
	public List getYukoDateDayList() {
		return yukoDateDayList;
	}

	/**
	 * @return
	 */
	public List getYukoDateMonthList() {
		return yukoDateMonthList;
	}

	/**
	 * @return
	 */
	public List getYukoDateYearList() {
		return yukoDateYearList;
	}

	/**
	 * @param list
	 */
	public void setBikoList(List list) {
		bikoList = list;
	}

	/**
	 * @param list
	 */
	public void setCharChk1List(List list) {
		charChk1List = list;
	}

	/**
	 * @param list
	 */
	public void setCharChk2List(List list) {
		charChk2List = list;
	}

	/**
	 * @param list
	 */
	public void setCharChk3List(List list) {
		charChk3List = list;
	}

	/**
	 * @param list
	 */
	public void setCharChk4List(List list) {
		charChk4List = list;
	}

	/**
	 * @param list
	 */
	public void setCharChk5List(List list) {
		charChk5List = list;
	}

	/**
	 * @param list
	 */
	public void setMojisuChkList(List list) {
		mojisuChkList = list;
	}

	/**
	 * @param list
	 */
	public void setRadioCharChk1List(List list) {
		radioCharChk1List = list;
	}

	/**
	 * @param list
	 */
	public void setRadioCharChk2List(List list) {
		radioCharChk2List = list;
	}

	/**
	 * @param list
	 */
	public void setRadioCharChk3List(List list) {
		radioCharChk3List = list;
	}

	/**
	 * @param list
	 */
	public void setRadioCharChk4List(List list) {
		radioCharChk4List = list;
	}

	/**
	 * @param list
	 */
	public void setRadioCharChk5List(List list) {
		radioCharChk5List = list;
	}

	/**
	 * @param list
	 */
	public void setTaishoIdList(List list) {
		taishoIdList = list;
	}

	/**
	 * @param list
	 */
	public void setYukoDateDayList(List list) {
		yukoDateDayList = list;
	}

	/**
	 * @param list
	 */
	public void setYukoDateMonthList(List list) {
		yukoDateMonthList = list;
	}

	/**
	 * @param list
	 */
	public void setYukoDateYearList(List list) {
		yukoDateYearList = list;
	}

}
