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
package jp.go.jsps.kaken.web.gyomu.datahokan;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * データ保管フォーム。
 * 
 * ID RCSfile="$RCSfile: DataHokanForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:24 $"
 */
public class DataHokanForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** 事業リスト */
	private List jigyoList;
	
	/** 事業CD */
	private String jigyoCd;
	
	/** 事業ID */
	private String jigyoId;
	
	/** 事業名 */
	private String jigyoName;
	
	/** 年度 */
	private String nendo;
	
	/** 回数 */
	private String kaisu;
	
	/** 有効期限（年） */
	private String yukoKigenYear;
	
	/** 有効期限（月） */
	private String yukoKigenMonth;
	
	/** 有効期限（日） */
	private String yukoKigenDate;
	
	/** 申請者名（姓） */
	private String shinseishaNameKanjiSei;
	
	/** 申請者名（名） */
	private String shinseishaNameKanjiMei;
	
	/** 申請者氏名（フリガナ-姓） */
	private String shinseishaNameKanaSei;
	
	/** 申請者氏名（フリガナ-名）*/
	private String shinseishaNameKanaMei;
	
	/** 申請者名（ローマ字：姓） */
	private String shinseishaNameRoSei;
	
	/** 申請者名（ローマ字：名） */
	private String shinseishaNameRoMei;
		
	/** 所属機関コード */
	private String shozokuCd;

	/** 申請者研究者番号 */
	private String kenkyuNo;
	
	/** 申請番号 */
	private String uketukeNo;
	
	/** 細目番号 */
	private String bunkaSaimokuCd;	
	
	/** 保管処理件数 */
	private int shoriKensu;
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public DataHokanForm() {
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
	public String getJigyoCd() {
		return jigyoCd;
	}

	/**
	 * @return
	 */
	public List getJigyoList() {
		return jigyoList;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @return
	 */
	public String getNendo() {
		return nendo;
	}

	/**
	 * @return
	 */
	public String getShinseishaNameKanjiMei() {
		return shinseishaNameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getShinseishaNameKanjiSei() {
		return shinseishaNameKanjiSei;
	}

	/**
	 * @return
	 */
	public String getShinseishaNameRoMei() {
		return shinseishaNameRoMei;
	}

	/**
	 * @return
	 */
	public String getShinseishaNameRoSei() {
		return shinseishaNameRoSei;
	}

	/**
	 * @return
	 */
	public int getShoriKensu() {
		return shoriKensu;
	}

	/**
	 * @return
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

	/**
	 * @return
	 */
	public String getYukoKigenDate() {
		return yukoKigenDate;
	}

	/**
	 * @return
	 */
	public String getYukoKigenMonth() {
		return yukoKigenMonth;
	}

	/**
	 * @return
	 */
	public String getYukoKigenYear() {
		return yukoKigenYear;
	}

	/**
	 * @param string
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
	}

	/**
	 * @param list
	 */
	public void setJigyoList(List list) {
		jigyoList = list;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	/**
	 * @param string
	 */
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameKanjiMei(String string) {
		shinseishaNameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameKanjiSei(String string) {
		shinseishaNameKanjiSei = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameRoMei(String string) {
		shinseishaNameRoMei = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameRoSei(String string) {
		shinseishaNameRoSei = string;
	}

	/**
	 * @param string
	 */
	public void setUketukeNo(String string) {
		uketukeNo = string;
	}

	/**
	 * @param string
	 */
	public void setYukoKigenDate(String string) {
		yukoKigenDate = string;
	}

	/**
	 * @param string
	 */
	public void setYukoKigenMonth(String string) {
		yukoKigenMonth = string;
	}

	/**
	 * @param string
	 */
	public void setYukoKigenYear(String string) {
		yukoKigenYear = string;
	}

	/**
	 * @return
	 */
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @param string
	 */
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param i
	 */
	public void setShoriKensu(int i) {
		shoriKensu = i;
	}

	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @return
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

	/**
	 * @return
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}

	/**
	 * @return
	 */
	public String getShinseishaNameKanaMei() {
		return shinseishaNameKanaMei;
	}

	/**
	 * @return
	 */
	public String getShinseishaNameKanaSei() {
		return shinseishaNameKanaSei;
	}

	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @param string
	 */
	public void setBunkaSaimokuCd(String string) {
		bunkaSaimokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameKanaMei(String string) {
		shinseishaNameKanaMei = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameKanaSei(String string) {
		shinseishaNameKanaSei = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

}
