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
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: ShinseiSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class ShinseiSearchForm extends BaseSearchForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4934741393748334190L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** 表示選択 */
	private String     hyojiSentaku;

	/** 事業コード */
	private String     jigyoCd;

	/** 年度 */
	private String     nendo;
	
	/** 回数 */
	private String     kaisu;
	
	/** 申請者氏名（漢字等-姓） */
	private String     nameKanjiSei;
	
	/** 申請者氏名（漢字等-名）*/
	private String     nameKanjiMei;

	/** 申請者氏名（フリガナ-姓） */
	private String     nameKanaSei;
	
	/** 申請者氏名（フリガナ-名）*/
	private String     nameKanaMei;
		
	/** 申請者氏名（ローマ字-姓） */
	private String     nameRoSei;
	
	/** 申請者氏名（ローマ字-名） */
	private String     nameRoMei;

	/** 所属機関コード */
	private String     shozokuCd;

	/** 申請者研究者番号 */
	private String     kenkyuNo;

	/** 系等の区分 */
	private String     keiName;

	/** 推薦の観点番号 */
	private String     kantenNo;
				
	/** 申請状況 */
	private String     jokyoId;
	
	/** 作成日(開始)(年) */
	private String     sakuseiDateFromYear;
	
	/** 作成日(開始)(月) */
	private String 	sakuseiDateFromMonth;

	/** 作成日(開始)(日) */
	private String 	sakuseiDateFromDay;
	
	/** 作成日(終了)(年) */
	private String     sakuseiDateToYear;
	
	/** 作成日(終了)(月) */
	private String 	sakuseiDateToMonth;

	/** 作成日(終了)(日) */
	private String 	sakuseiDateToDay;
	
	/** 所属機関承認日(開始)(年) */
	private String     shoninDateFromYear;
	
	/** 所属機関承認日(開始)(月) */
	private String 	shoninDateFromMonth;

	/** 所属機関承認日(開始)(日) */
	private String 	shoninDateFromDay;
	
	/** 所属機関承認日(終了)(年) */
	private String     shoninDateToYear;
	
	/** 所属機関承認日(終了)(月) */
	private String 	shoninDateToMonth;

	/** 所属機関承認日(終了)(日) */
	private String 	shoninDateToDay;
	
	/** 申請番号 */
	private String     uketukeNo;	

	/** 細目番号 */
	private String     bunkaSaimokuCd;	
	
	/** 表示選択選択リスト */
	private List hyojiSentakuList = new ArrayList();
	
	/** 事業名選択リスト */
	private List jigyoNameList = new ArrayList();

	/** 推薦の観点選択リスト */
	private List kantenList = new ArrayList();
		
	/** 申請状況選択リスト */
	private List jokyoList = new ArrayList();
	
	/** 受理整理番号 */
	private String seiriNo;

	/** 新規・継続区分リスト */
	private List shinkiKeibetuList = new ArrayList();

	/** 研究領域リスト */
	private List kenkyuKubunList = new ArrayList();

	/** 前年度の応募リスト */
	private List zennendoList = new ArrayList();
	
	/** 分担金の有無リスト */
	private List buntankinList = new ArrayList();
	
	/** 開示希望の有無リスト */
	private List kaijiKiboList = new ArrayList();

	/** 調整班のリスト */
	private List chouseiList = new ArrayList();
	
	/** 新規・継続区分 */
	private String shinseiKubun;
	
	/** 分割番号 */
	private String bunkatsuNo;
	
	/** 研究計画最終年度前年度の応募 */
	private String shinseiFlgNo;

	/** 計画研究・公募研究・終了研究領域区分 */
	private String kenkyuKubun;
	
	/** 調整班 */
	private String chouseiFlg;
	
	/** 領域番号 */
	private String ryouikiNo;
	
	/** 研究項目番号 */
	private String ryouikiKoumokuNo;

	/** 分担金の有無 */
	private String buntankinFlg;
	
	/** 開示希望の有無 */
	private String kaijiKiboFlg;
	
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinseiSearchForm() {
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
	}

	/**
	 * 初期化処理
	 */
	public void init() {
		hyojiSentaku= "0";	//デフォルトは、「0:研究種目毎に表示」
		jigyoCd= "";
		nendo= "";
		kaisu= "";
		nameKanjiSei= "";
		nameKanjiMei= "";
		nameKanaSei= "";
		nameKanaMei= "";
		nameRoSei= "";
		nameRoMei= "";
		shozokuCd= "";
		kenkyuNo= "";
		keiName= "";
		kantenNo= "";
		jokyoId= "";
		sakuseiDateFromYear= "";
		sakuseiDateFromMonth= "";
		sakuseiDateFromDay= "";
		sakuseiDateToYear= "";
		sakuseiDateToMonth= "";
		sakuseiDateToDay= "";
		shoninDateFromYear= "";
		shoninDateFromMonth= "";
		shoninDateFromDay= "";
		shoninDateToYear= "";
		shoninDateToMonth= "";
		shoninDateToDay= "";
		uketukeNo= "";	
		bunkaSaimokuCd= "";		
		shinseiKubun="";
		bunkatsuNo="";
		shinseiFlgNo="";
		kenkyuKubun="";
		chouseiFlg="";
		ryouikiNo="";
		buntankinFlg="";
		kaijiKiboFlg="";
		ryouikiKoumokuNo = "";
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
	public String getHyojiSentaku() {
		return hyojiSentaku;
	}

	/**
	 * @return
	 */
	public String getJigyoCd() {
		return jigyoCd;
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
	public String getJokyoId() {
		return jokyoId;
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
	public String getNendo() {
		return nendo;
	}
	
	/**
	 * @return
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

	/**
	 * @param string
	 */
	public void setHyojiSentaku(String string) {
		hyojiSentaku = string;
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
	public void setJigyoNameList(List list) {
		jigyoNameList = list;
	}

	/**
	 * @param string
	 */
	public void setJokyoId(String string) {
		jokyoId = string;
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
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setUketukeNo(String string) {
		uketukeNo = string;
	}

	/**
	 * @return
	 */
	public List getJokyoList() {
		return jokyoList;
	}

	/**
	 * @param list
	 */
	public void setJokyoList(List list) {
		jokyoList = list;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateFromDay() {
		return sakuseiDateFromDay;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateFromMonth() {
		return sakuseiDateFromMonth;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateFromYear() {
		return sakuseiDateFromYear;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateToDay() {
		return sakuseiDateToDay;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateToMonth() {
		return sakuseiDateToMonth;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateToYear() {
		return sakuseiDateToYear;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateFromDay(String string) {
		sakuseiDateFromDay = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateFromMonth(String string) {
		sakuseiDateFromMonth = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateFromYear(String string) {
		sakuseiDateFromYear = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateToDay(String string) {
		sakuseiDateToDay = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateToMonth(String string) {
		sakuseiDateToMonth = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateToYear(String string) {
		sakuseiDateToYear = string;
	}

	/**
	 * @return
	 */
	public String getShoninDateFromDay() {
		return shoninDateFromDay;
	}

	/**
	 * @return
	 */
	public String getShoninDateFromMonth() {
		return shoninDateFromMonth;
	}

	/**
	 * @return
	 */
	public String getShoninDateFromYear() {
		return shoninDateFromYear;
	}

	/**
	 * @return
	 */
	public String getShoninDateToDay() {
		return shoninDateToDay;
	}

	/**
	 * @return
	 */
	public String getShoninDateToMonth() {
		return shoninDateToMonth;
	}

	/**
	 * @return
	 */
	public String getShoninDateToYear() {
		return shoninDateToYear;
	}

	/**
	 * @param string
	 */
	public void setShoninDateFromDay(String string) {
		shoninDateFromDay = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateFromMonth(String string) {
		shoninDateFromMonth = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateFromYear(String string) {
		shoninDateFromYear = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateToDay(String string) {
		shoninDateToDay = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateToMonth(String string) {
		shoninDateToMonth = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateToYear(String string) {
		shoninDateToYear = string;
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
	 * @return
	 */
	public List getHyojiSentakuList() {
		return hyojiSentakuList;
	}

	/**
	 * @param list
	 */
	public void setHyojiSentakuList(List list) {
		hyojiSentakuList = list;
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
	public String getKantenNo() {
		return kantenNo;
	}

	/**
	 * @return
	 */
	public String getKeiName() {
		return keiName;
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
	public void setKantenNo(String string) {
		kantenNo = string;
	}

	/**
	 * @param string
	 */
	public void setKeiName(String string) {
		keiName = string;
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
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
	 * @return
	 */
	public List getKantenList() {
		return kantenList;
	}

	/**
	 * @param list
	 */
	public void setKantenList(List list) {
		kantenList = list;
	}

	/**
	 * @return 整理番号を返す
	 */
	public String getSeiriNo(){
		return seiriNo;
	}
	
	/**
	 * @param str 整理番号をセットする
	 */
	public void setSeiriNo(String str){
		seiriNo = str;
	}

	/**
	 * @return bunkatsuNo を戻します。
	 */
	public String getBunkatsuNo() {
		return bunkatsuNo;
	}

	/**
	 * @param bunkatsuNo 設定する bunkatsuNo。
	 */
	public void setBunkatsuNo(String bunkatsuNo) {
		this.bunkatsuNo = bunkatsuNo;
	}

	/**
	 * @return buntankinFlg を戻します。
	 */
	public String getBuntankinFlg() {
		return buntankinFlg;
	}

	/**
	 * @param buntankinFlg 設定する buntankinFlg。
	 */
	public void setBuntankinFlg(String buntankinFlg) {
		this.buntankinFlg = buntankinFlg;
	}

	/**
	 * @return buntankinList を戻します。
	 */
	public List getBuntankinList() {
		return buntankinList;
	}

	/**
	 * @param buntankinList 設定する buntankinList。
	 */
	public void setBuntankinList(List buntankinList) {
		this.buntankinList = buntankinList;
	}

	/**
	 * @return chouseiFlg を戻します。
	 */
	public String getChouseiFlg() {
		return chouseiFlg;
	}

	/**
	 * @param chouseiFlg 設定する chouseiFlg。
	 */
	public void setChouseiFlg(String chouseiFlg) {
		this.chouseiFlg = chouseiFlg;
	}

	/**
	 * @return kaijiKiboFlg を戻します。
	 */
	public String getKaijiKiboFlg() {
		return kaijiKiboFlg;
	}

	/**
	 * @param kaijiKiboFlg 設定する kaijiKiboFlg。
	 */
	public void setKaijiKiboFlg(String kaijiKiboFlg) {
		this.kaijiKiboFlg = kaijiKiboFlg;
	}

	/**
	 * @return kaijiKiboList を戻します。
	 */
	public List getKaijiKiboList() {
		return kaijiKiboList;
	}

	/**
	 * @param kaijiKiboList 設定する kaijiKiboList。
	 */
	public void setKaijiKiboList(List kaijiKiboList) {
		this.kaijiKiboList = kaijiKiboList;
	}

	/**
	 * @return kenkyuKubun を戻します。
	 */
	public String getKenkyuKubun() {
		return kenkyuKubun;
	}

	/**
	 * @param kenkyuKubun 設定する kenkyuKubun。
	 */
	public void setKenkyuKubun(String kenkyuKubun) {
		this.kenkyuKubun = kenkyuKubun;
	}

	/**
	 * @return kenkyuKubunList を戻します。
	 */
	public List getKenkyuKubunList() {
		return kenkyuKubunList;
	}

	/**
	 * @param kenkyuKubunList 設定する kenkyuKubunList。
	 */
	public void setKenkyuKubunList(List kenkyuKubunList) {
		this.kenkyuKubunList = kenkyuKubunList;
	}

	/**
	 * @return ryouikiNo を戻します。
	 */
	public String getRyouikiNo() {
		return ryouikiNo;
	}

	/**
	 * @param ryouikiNo 設定する ryouikiNo。
	 */
	public void setRyouikiNo(String ryouikiNo) {
		this.ryouikiNo = ryouikiNo;
	}

	/**
	 * @return shinkiKeibetuList を戻します。
	 */
	public List getShinkiKeibetuList() {
		return shinkiKeibetuList;
	}

	/**
	 * @param shinkiKeibetuList 設定する shinkiKeibetuList。
	 */
	public void setShinkiKeibetuList(List shinkiKeibetuList) {
		this.shinkiKeibetuList = shinkiKeibetuList;
	}

	/**
	 * @return shinseiFlgNo を戻します。
	 */
	public String getShinseiFlgNo() {
		return shinseiFlgNo;
	}

	/**
	 * @param shinseiFlgNo 設定する shinseiFlgNo。
	 */
	public void setShinseiFlgNo(String shinseiFlgNo) {
		this.shinseiFlgNo = shinseiFlgNo;
	}

	/**
	 * @return shinseiKubun を戻します。
	 */
	public String getShinseiKubun() {
		return shinseiKubun;
	}

	/**
	 * @param shinseiKubun 設定する shinseiKubun。
	 */
	public void setShinseiKubun(String shinseiKubun) {
		this.shinseiKubun = shinseiKubun;
	}

	/**
	 * @return zennendoList を戻します。
	 */
	public List getZennendoList() {
		return zennendoList;
	}

	/**
	 * @param zennendoList 設定する zennendoList。
	 */
	public void setZennendoList(List zennendoList) {
		this.zennendoList = zennendoList;
	}

	/**
	 * @return ryouikiKoumokuNo を戻します。
	 */
	public String getRyouikiKoumokuNo() {
		return ryouikiKoumokuNo;
	}

	/**
	 * @param ryouikiKoumokuNo 設定する ryouikiKoumokuNo。
	 */
	public void setRyouikiKoumokuNo(String ryouikiKoumokuNo) {
		this.ryouikiKoumokuNo = ryouikiKoumokuNo;
	}

	/**
	 * @return chouseiList を戻します。
	 */
	public List getChouseiList() {
		return chouseiList;
	}

	/**
	 * @param chouseiList 設定する chouseiList。
	 */
	public void setChouseiList(List chouseiList) {
		this.chouseiList = chouseiList;
	}


}