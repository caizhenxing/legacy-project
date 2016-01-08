/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsainKanri;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: ShinsainForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:40 $"
 */
public class ShinsainForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 審査員ID */
	private String shinsainId;

	/** 審査員番号 */
	private String shinsainNo;

	/** 審査員氏名（漢字-姓） */
	private String nameKanjiSei;

	/** 審査員氏名（漢字-名） */
	private String nameKanjiMei;

	/** 審査員氏名（カナ-姓） */
	private String nameKanaSei;

	/** 審査員氏名（カナ-名）*/
	private String nameKanaMei;

	/** 所属機関名（コード）*/
	private String shozokuCd;

	/** 所属機関名（和文）*/
	private String shozokuName;

//	/** 部局名（コード）*/
//	private String bukyokuCd;

	/** 部局名（和文）*/
	private String bukyokuName;

//	/** 職種コード */
//	private String shokushuCd;

	/** 職種名称 */
	private String shokushuName;

//	/** 系別 */
//	private String keiCd;
//
//	/** 審査可不可 */
//	private String shinsaKahi;

	/** 送付先（郵便番号） */
	private String sofuZip;

	/** 送付先（住所） */
	private String sofuZipaddress;

	/** 送付先（Email） */
	private String sofuZipemail;

//	/** 送付先（Email2） */
//	private String sofuZipemail2;

	/** 電話番号 */
	private String shozokuTel;

//	/** 自宅電話番号 */
//	private String jitakuTel;

//	/** 新規・継続 */
//	private String sinkiKeizokuFlg;
//
//	/** 新規・継続(表示用) */
//	private String sinkiKeizokuHyoji;
//
//	/** 委嘱開始日(年) */
//	private String kizokuStartYear;
//
//	/** 委嘱開始日(月) */
//	private String kizokuStartMonth;
//
//	/** 委嘱開始日(日) */
//	private String kizokuStartDay;
//
//	/** 委嘱終了日(年) */
//	private String kizokuEndYear;
//
//	/** 委嘱終了日(月) */
//	private String kizokuEndMonth;
//
//	/** 委嘱終了日(日) */
//	private String kizokuEndDay;
//
//	/** 謝金 */
//	private String shakin;
//
//	/** 謝金(表示用) */
//	private String shakinHyoji;

	/** URL */
	private String url;

//	/** 分科細目コード（A） */
//	private String levelA1;
//
//	/** 分科細目コード（B1-1） */
//	private String levelB11;
//
//	/** 分科細目コード（B1-2） */
//	private String levelB12;
//
//	/** 分科細目コード（B1-3） */
//	private String levelB13;
//
//	/** 分科細目コード（B2-1） */
//	private String levelB21;
//
//	/** 分科細目コード（B2-2） */
//	private String levelB22;
//
//	/** 分科細目コード（B2-3） */
//	private String levelB23;
//
//	/** 専門分野のキーワード(1) */
//	private String key1;
//
//	/** 専門分野のキーワード(2) */
//	private String key2;
//
//	/** 専門分野のキーワード(3) */
//	private String key3;
//
//	/** 専門分野のキーワード(4) */
//	private String key4;
//
//	/** 専門分野のキーワード(5) */
//	private String key5;
//
//	/** 専門分野のキーワード(6) */
//	private String key6;
//
//	/** 専門分野のキーワード(7) */
//	private String key7;

	/** パスワード */
	private String password;

	/** 備考 */
	private String biko;

	/** 有効期限(年) */
	private String yukoDateYear;

	/** 有効期限(月) */
	private String yukoDateMonth;

	/** 有効期限(日) */
	private String yukoDateDay;

	/** 削除フラグ*/
	private String delFlg;

//	/** 新規・継続選択リスト */
//	private List sinkiKeizokuFlgList = new ArrayList();
//
//	/** 謝金選択リスト */
//	private List shakinList = new ArrayList();

	/** FAX番号 */
	private String shozokuFax;

	/** 担当事業区分 */
	private String jigyoKubun;

	/** 専門 */
	private String senmon;

	/** 更新日(年) */
	private String koshinDateYear;

	/** 更新日(月) */
	private String koshinDateMonth;

	/** 更新日(日) */
	private String koshinDateDay;

	// 2006/10/24 易旭 追加ここから
	// 研究計画調書ダウンロードフラグ
	private String downloadFlag = "0";

	// 2006/10/24 易旭 追加ここまで

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsainForm() {
		super();
		init();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * 初期化処理。 (非 Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//・・・
	}

	/**
	 * 初期化処理
	 */
	public void init() {
		shinsainId = "";
		shinsainNo = "";
		nameKanjiSei = "";
		nameKanjiMei = "";
		nameKanaSei = "";
		nameKanaMei = "";
		shozokuCd = "";
		shozokuName = "";
//		bukyokuCd = "";
		bukyokuName = "";
//		shokushuCd = "";
		shokushuName = "";
//		keiCd = "";
//		shinsaKahi = "";
		sofuZip = "";
		sofuZipaddress = "";
		sofuZipemail = "";
//		sofuZipemail2 = "";
		shozokuTel = "";
//		jitakuTel = "";
//		sinkiKeizokuFlg = "";
//		sinkiKeizokuHyoji = "";
//		kizokuStartYear = "";
//		kizokuStartMonth = "";
//		kizokuStartDay = "";
//		kizokuEndYear = "";
//		kizokuEndMonth = "";
//		kizokuEndDay = "";
//		shakin = "";
//		shakinHyoji = "";
		url = "";
//		levelA1 = "";
//		levelB11 = "";
//		levelB12 = "";
//		levelB13 = "";
//		levelB21 = "";
//		levelB22 = "";
//		levelB23 = "";
//		key1 = "";
//		key2 = "";
//		key3 = "";
//		key4 = "";
//		key5 = "";
//		key6 = "";
//		key7 = "";
		password = "";
		biko = "";
		yukoDateYear = "";
		yukoDateMonth = "";
		yukoDateDay = "";
		delFlg = "";
		shozokuFax = "";
		jigyoKubun = "";
		senmon = "";
		koshinDateYear = "";
		koshinDateMonth = "";
		koshinDateDay = "";
	}

	/* 
	 * 入力チェック。 (非 Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
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
	public String getBukyokuName() {
		return bukyokuName;
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
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @return
	 */
	public String getKoshinDateDay() {
		return koshinDateDay;
	}

	/**
	 * @return
	 */
	public String getKoshinDateMonth() {
		return koshinDateMonth;
	}

	/**
	 * @return
	 */
	public String getKoshinDateYear() {
		return koshinDateYear;
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
	public String getSenmon() {
		return senmon;
	}

	/**
	 * @return
	 */
	public String getShinsainId() {
		return shinsainId;
	}

	/**
	 * @return
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

	/**
	 * @return
	 */
	public String getShokushuName() {
		return shokushuName;
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
	public String getShozokuFax() {
		return shozokuFax;
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
	public String getShozokuTel() {
		return shozokuTel;
	}

	/**
	 * @return
	 */
	public String getSofuZip() {
		return sofuZip;
	}

	/**
	 * @return
	 */
	public String getSofuZipaddress() {
		return sofuZipaddress;
	}

	/**
	 * @return
	 */
	public String getSofuZipemail() {
		return sofuZipemail;
	}

	/**
	 * @return
	 */
	public String getUrl() {
		return url;
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
	public void setDelFlg(String string) {
		delFlg = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

	/**
	 * @param string
	 */
	public void setKoshinDateDay(String string) {
		koshinDateDay = string;
	}

	/**
	 * @param string
	 */
	public void setKoshinDateMonth(String string) {
		koshinDateMonth = string;
	}

	/**
	 * @param string
	 */
	public void setKoshinDateYear(String string) {
		koshinDateYear = string;
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
	public void setSenmon(String string) {
		senmon = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainId(String string) {
		shinsainId = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainNo(String string) {
		shinsainNo = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuName(String string) {
		shokushuName = string;
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
	public void setShozokuFax(String string) {
		shozokuFax = string;
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
	public void setShozokuTel(String string) {
		shozokuTel = string;
	}

	/**
	 * @param string
	 */
	public void setSofuZip(String string) {
		sofuZip = string;
	}

	/**
	 * @param string
	 */
	public void setSofuZipaddress(String string) {
		sofuZipaddress = string;
	}

	/**
	 * @param string
	 */
	public void setSofuZipemail(String string) {
		sofuZipemail = string;
	}

	/**
	 * @param string
	 */
	public void setUrl(String string) {
		url = string;
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

	public String getDownloadFlag() {
		return downloadFlag;
	}

	public void setDownloadFlag(String downloadFlag) {
		this.downloadFlag = downloadFlag;
	}
}
