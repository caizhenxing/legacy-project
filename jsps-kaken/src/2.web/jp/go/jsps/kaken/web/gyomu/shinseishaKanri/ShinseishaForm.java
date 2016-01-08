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
package jp.go.jsps.kaken.web.gyomu.shinseishaKanri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * 申請者フォーム情報
 */
public class ShinseishaForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 申請者ID */
	private String shinseishaId;

	/** 所属機関コード */
	private String shozokuCd;

	/** 所属機関名（和文） */
	private String shozokuName;

	/** 所属機関名（英文） */
	private String shozokuNameEigo;

	/** 所属機関名（略称） */
	private String shozokuNameRyaku;

	/** パスワード */
	private String password;

	/** 申請者氏名（漢字-姓） */
	private String nameKanjiSei;

	/** 申請者氏名（漢字-名） */
	private String nameKanjiMei;

	/** 申請者氏名（フリガナ-姓） */
	private String nameKanaSei;

	/** 申請者氏名（フリガナ-名） */
	private String nameKanaMei;

	/** 申請者氏名（ローマ字-姓） */
	private String nameRoSei;

	/** 申請者氏名（ローマ字-名）*/
	private String nameRoMei;

	/** 部局コード */
	private String bukyokuCd;

	/** 部局名 */
	private String bukyokuName;

	/** 部局名（略称） */
	private String bukyokuNameRyaku;
	
	/** 部局種別選択リスト */
	private List shubetuCdList = new ArrayList();

	/** 部局種別コード */
	private String bukyokuShubetuCd;

	/** 部局種別名 */
	private String bukyokuShubetuName;

	/** 職名コード */
	private String shokushuCd;

	/** 職名（和文） */
	private String shokushuNameKanji;

	/** 職名（略称） */
	private String shokushuNameRyaku;

	/** 職名選択リスト */
	private List shokushuCdList = new ArrayList();

	/** 科研研究者番号 */
	private String kenkyuNo;

	/** 非公募応募可フラグ */
	private String hikoboFlg;

	/** 非公募応募可フラグリスト */
	private List hikoboFlgList = new ArrayList();

	/** 備考 */
	private String biko;
	
	/** 応募資格 */
	private String ouboShikaku;

	/** 有効期限(年) */
	private String yukoDateYear;

	/** 有効期限(月) */
	private String yukoDateMonth;

	/** 有効期限(日) */
	private String yukoDateDay;

	/** 削除フラグ*/
	private String delFlg;
	
	//2005/04/25 追加 ここから----------------------------------------------
	//理由 生年月日取得のため
	
	/** 生年月日 */
	private Date birthday;
	
	//追加 ここまで---------------------------------------------------------

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinseishaForm() {
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
		shinseishaId = "";
		shozokuCd = "";
		shozokuName = "";
		shozokuNameEigo = "";
		shozokuNameRyaku = "";
		password = "";
		nameKanjiSei = "";
		nameKanjiMei = "";
		nameKanaSei = "";
		nameKanaMei = "";
		nameRoSei = "";
		nameRoMei = "";
		bukyokuCd = "";
		bukyokuName = "";
		bukyokuNameRyaku = "";
		bukyokuShubetuCd = "";
		bukyokuShubetuName = "";
		shokushuCd = "";
		shokushuNameKanji = "";
		shokushuNameRyaku = "";
		kenkyuNo = "";
		hikoboFlg = "";
		biko = "";
		yukoDateYear = "";
		yukoDateMonth = "";
		yukoDateDay = "";
		delFlg = "";
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

		/* ここの処理はvalidation-gyomu.xmlで実行
		//所属機関チェック
		if(getShozokuCd() != null && getShozokuCd().equals("9999")) {
			if(StringUtil.isBlank(getShozokuName())) {
				errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2002", "所属機関名(和文)"));
			}
			if(StringUtil.isBlank(getShozokuNameEigo())) {
				errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2002", "所属機関名(英文)"));
			}
		}
		
		//部局名チェック
		if(getBukyokuCd() != null && getBukyokuCd().equals("999")) {
			if(StringUtil.isBlank(getBukyokuName())) {
				errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2002", "部局名(和文)"));
			}
		}

		//部局種別チェック
		if(getBukyokuShubetuCd() != null && getBukyokuShubetuCd().equals("9")) {
			if(StringUtil.isBlank(getBukyokuShubetuName())) {
				errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2002", "部局種別"));
			}
		}

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
	public String getBukyokuCd() {
		return bukyokuCd;
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
	public String getNameKanjiSei() {
		return nameKanjiSei;
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
	public String getNameRoMei() {
		return nameRoMei;
	}

	/**
	 * @return
	 */
	public String getNameRoSei() {
		return nameRoSei;
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
	public String getShinseishaId() {
		return shinseishaId;
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
	public String getBukyokuShubetuCd() {
		return bukyokuShubetuCd;
	}

	/**
	 * @return
	 */
	public String getBukyokuShubetuName() {
		return bukyokuShubetuName;
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
	public void setBukyokuCd(String string) {
		bukyokuCd = string;
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
	public void setNameKanjiSei(String string) {
		nameKanjiSei = string;
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
	public void setNameRoMei(String string) {
		nameRoMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameRoSei(String string) {
		nameRoSei = string;
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
	public void setShinseishaId(String string) {
		shinseishaId = string;
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
	public void setBukyokuShubetuCd(String string) {
		bukyokuShubetuCd = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuShubetuName(String string) {
		bukyokuShubetuName = string;
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
	 * @param list
	 */
	public void setShubetuCdList(List list) {
		shubetuCdList = list;
	}

	/**
	 * @return
	 */
	public List getShubetuCdList() {
		return shubetuCdList;
	}

	/**
	 * @return
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameEigo(String string) {
		shozokuNameEigo = string;
	}

	/**
	 * @return
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}

	/**
	 * @param string
	 */
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

	/**
	 * @return
	 */
	public String getDelFlg() {
		return delFlg;
	}

	/**
	 * @param string
	 */
	public void setDelFlg(String string) {
		delFlg = string;
	}

	/**
	 * @return
	 */
	public String getShozokuNameRyaku() {
		return shozokuNameRyaku;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameRyaku(String string) {
		shozokuNameRyaku = string;
	}

	/**
	 * @return
	 */
	public String getBukyokuNameRyaku() {
		return bukyokuNameRyaku;
	}

	/**
	 * @return
	 */
	public String getHikoboFlg() {
		return hikoboFlg;
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
	public String getShokushuCd() {
		return shokushuCd;
	}

	/**
	 * @return
	 */
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
	}

	/**
	 * @return
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
	}

	/**
	 * @param string
	 */
	public void setBukyokuNameRyaku(String string) {
		bukyokuNameRyaku = string;
	}

	/**
	 * @param string
	 */
	public void setHikoboFlg(String string) {
		hikoboFlg = string;
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
	public void setShokushuCd(String string) {
		shokushuCd = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameKanji(String string) {
		shokushuNameKanji = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameRyaku(String string) {
		shokushuNameRyaku = string;
	}

	/**
	 * @return
	 */
	public List getHikoboFlgList() {
		return hikoboFlgList;
	}

	/**
	 * @param list
	 */
	public void setHikoboFlgList(List list) {
		hikoboFlgList = list;
	}

	/**
	 * @return
	 */
	public List getShokushuCdList() {
		return shokushuCdList;
	}

	/**
	 * @param list
	 */
	public void setShokushuCdList(List list) {
		shokushuCdList = list;
	}

	//2005/04/25 追加 ここから----------------------------------------------
	//理由 生年月日取得のため
	/**
	 * @return
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param date
	 */
	public void setBirthday(Date date) {
		birthday = date;
	}
	//追加 ここまで---------------------------------------------------------

	public String getOuboShikaku() {
		return ouboShikaku;
	}

	public void setOuboShikaku(String ouboShikaku) {
		this.ouboShikaku = ouboShikaku;
	}
}
