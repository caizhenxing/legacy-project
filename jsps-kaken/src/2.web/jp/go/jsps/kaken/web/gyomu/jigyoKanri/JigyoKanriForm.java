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
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * 
 */
public class JigyoKanriForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 事業ID */
	private String jigyoId;

	/** 年度 */
	private String nendo;

	/** 回数 */
	private String kaisu;
	
	/** 事業コード */
	private String jigyoCd;

	/** 業務担当課 */
	private String tantokaName;

	/** 業務担当係名 */
	private String tantoKakari;

	/** 問い合わせ先担当者名 */
	private String toiawaseName;

	/** 問い合わせ先電話番号 */
	private String toiawaseTel;

	/** 問い合わせ先E-mail */
	private String toiawaseEmail;

	/** 学振受付期間（開始）(年) */
	private String uketukekikanStartYear;

	/** 学振受付期間（開始）(月) */
	private String uketukekikanStartMonth;

	/** 学振受付期間（開始）(日) */
	private String uketukekikanStartDay;

	/** 学振受付期間（終了）(年) */
	private String uketukekikanEndYear;

	/** 学振受付期間（終了）(月) */
	private String uketukekikanEndMonth;

	/** 学振受付期間（終了）(日) */
	private String uketukekikanEndDay;

	/** 研究者名簿登録最終締切日(年) */
	private String meiboDateYear;

	/** 研究者名簿登録最終締切日(月) */
	private String meiboDateMonth;

	/** 研究者名簿登録最終締切日(日) */
	private String meiboDateDay;

	// 2006/06/14 追加 李義華 ここから
	/** 仮領域番号発行締切日(年) */
	private String kariryoikiNoEndDateYear;

	/** 仮領域番号発行締切日(月) */
	private String kariryoikiNoEndDateMonth;

	/** 仮領域番号発行締切日(日) */
	private String kariryoikiNoEndDateDay;

	// 2006/06/14 追加 李義華 ここまで

	// 2006/07/10 追加 李義華 ここから
	/** 領域代表者確定締切日(年) */
	private String ryoikiEndDateYear;

	/** 領域代表者確定締切日(月) */
	private String ryoikiEndDateMonth;

	/** 領域代表者確定締切日(日) */
	private String ryoikiEndDateDay;
	// 2006/07/10 追加 李義華 ここまで

	// 2006/10/23 追加 易旭 ここから
	/** 利害関係入力締切日(年) */
	private String rigaiEndDateYear;

	/** 利害関係入力締切日(月) */
	private String rigaiEndDateMonth;

	/** 利害関係入力締切日(日) */
	private String rigaiEndDateDay;
	// 2006/10/23 追加 易旭 ここまで

	/** 審査期限(年) */
	private String shinsaKigenYear;

	/** 審査期限(月) */
	private String shinsaKigenMonth;

	/** 審査期限(日) */
	private String shinsaKigenDay;

	/** 添付文書名 */
	private String tenpuName;

	/** 添付ファイル(Win) */
	private String tenpuWin;

	/** 添付ファイル(Mac) */
	private String tenpuMac;

	/** 評価用ファイル有無 */
	private String hyokaFileFlg;

	/** 評価用ファイル */
	private String hyokaFile;

	/** 公開フラグ */
	private String kokaiFlg;

	/** 公開決裁番号 */
	private String kessaiNo;

	/** 公開確定者ID */
	private String kokaiID;

	/** 備考 */
	private String biko;

	/** 削除フラグ */
	private String delFlag;
    
// 2007/02/07 張志男　追加ここから
    /** 応募内容ファイルページ数範囲(下限) */
    private String pageFrom;
    
    /** 応募内容ファイルページ数範囲(上限) */
    private String pageTo;

// 2007/02/07 張志男　追加ここまで
	//-----
	
	/** 事業名選択リスト */
	private List jigyoNameList = new ArrayList();

	/** 評価用ファイル有無（なし／あり）リスト */
	private List flgList = new ArrayList();
		
	/** 添付ファイル(Win)(アップロードファイル) */
	private FormFile tenpuWinUploadFile;

	/** 添付ファイル(Mac)(アップロードファイル) */
	private FormFile tenpuMacUploadFile;

	/** 評価用ファイル(アップロードファイル) */
	private FormFile hyokaUploadFile;
	
	/** ダウンロードファイルフラグ */
	private String downloadFileFlg;	

	//・・・・・・・・・・

	//2005/04/19 追加 ここから----------------------------------------------
	//理由 URLの追加のため
	
	/** URLタイトル */
	private String urlTitle;
	
	/** URLアドレス */
	private String urlAddress;

	/**　ダウンロードURL */
	private String dlUrl;

	//---------------------------------------------------------------------
	// Constructors
	// ---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public JigyoKanriForm() {
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
		jigyoId = "";
		nendo = "";
		kaisu = "";
		jigyoCd = "";
		tantokaName = "";
		tantoKakari = "";
		toiawaseName = "";
		toiawaseTel = "";
		toiawaseEmail = "";
		uketukekikanStartYear = "";
		uketukekikanStartMonth = "";
		uketukekikanStartDay = "";
		uketukekikanEndYear = "";
		uketukekikanEndMonth = "";
		uketukekikanEndDay = "";
		shinsaKigenYear = "";
		shinsaKigenMonth = "";
		shinsaKigenDay = "";
		meiboDateYear = "";
		meiboDateMonth = "";
		meiboDateDay = "";

		// 2006/06/14 追加 李義華 ここから
		kariryoikiNoEndDateYear = "";
		kariryoikiNoEndDateMonth = "";
		kariryoikiNoEndDateDay = "";
		// 2006/06/14 追加 李義華 ここまで
		// 2006/07/10追加 李義華 ここから
		ryoikiEndDateYear = "";
		ryoikiEndDateMonth = "";
		ryoikiEndDateDay = "";
		// 2006/07/10 追加 李義華 ここまで
		// 2006/10/23 追加 易旭 ここから
		rigaiEndDateYear = "";
		rigaiEndDateMonth = "";
		rigaiEndDateDay = "";
		// 2006/10/23 追加 易旭 ここまで
		
		tenpuName = "";
		tenpuWin = "";
		tenpuMac = "";
		hyokaFileFlg = "0";//初期表示「なし」
		hyokaFile = "";
		kokaiFlg = "";
		kessaiNo = "";
		kokaiID = "";
		biko = "";
		delFlag = "";
		downloadFileFlg = "0";// 申請内容ファイル（Win）
// 2007/02/07 張志男　追加ここから       
        pageFrom = "";
        pageTo = "";    
// 2007/02/07 張志男　追加ここまで
	}

	/*
	 * 入力チェック。 (非 Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		//定型処理----- 
		ActionErrors errors = super.validate(mapping, request);

		//---------------------------------------------
		// 基本的なチェック(必須、形式等）はValidatorを使用する。
		// ---------------------------------------------
// 2007/02/07 張志男　追加ここから
        // 応募内容ファイルページ数下限＞応募内容ファイルページ数上限の場合はエラーとする    
        this.setPageFrom(pageFrom.trim());
        this.setPageTo(pageTo.trim());
        if(errors.isEmpty()){            
            if (!StringUtil.isBlank(pageFrom) && !StringUtil.isBlank(pageTo)){  
                
                if (Integer.parseInt(pageFrom) > Integer.parseInt(pageTo)){                  
                     errors.add(ActionErrors.GLOBAL_ERROR,
                            new ActionError("errors.9032"));
                }
            }      
        }         
// 2007/02/07　張志男　追加ここまで        
		// 基本入力チェックここまで
		if (!errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.2009"));
		}
		// 定型処理-----

		// 追加処理-----
		// ---------------------------------------------
		// 組み合わせチェック
		// ---------------------------------------------

		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
     * 備考を取得
     * @return 備考
	 */
	public String getBiko() {
		return biko;
	}

    /**
     * 備考を設定
     * @param string 備考
     */
    public void setBiko(String string) {
        biko = string;
    }

    /**
     * 削除フラグを取得
     * @return 削除フラグ
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 削除フラグを設定
     * @param string 削除フラグ
     */
    public void setDelFlag(String string) {
        delFlag = string;
    }

    /**
     * ダウンロードファイルフラグを取得
     * @return ダウンロードファイルフラグ
     */
    public String getDownloadFileFlg() {
        return downloadFileFlg;
    }

    /**
     * ダウンロードファイルフラグを設定
     * @param string ダウンロードファイルフラグ
     */
    public void setDownloadFileFlg(String string) {
        downloadFileFlg = string;
    }

    /**
     * 評価用ファイルを取得
     * @return 評価用ファイル
     */
    public String getHyokaFile() {
        return hyokaFile;
    }

    /**
     * 評価用ファイルを設定
     * @param string 評価用ファイル
     */
    public void setHyokaFile(String string) {
        hyokaFile = string;
    }

    /**
     * 評価用ファイル有無を取得
     * @return 評価用ファイル有無
     */
    public String getHyokaFileFlg() {
        return hyokaFileFlg;
    }

    /**
     * 評価用ファイル有無を設定
     * @param string 評価用ファイル有無
     */
    public void setHyokaFileFlg(String string) {
        hyokaFileFlg = string;
    }

    /**
     * 評価用ファイルを取得
     * @return 評価用ファイル
     */
    public FormFile getHyokaUploadFile() {
        return hyokaUploadFile;
    }

    /**
     * 評価用ファイルを設定
     * @param file 評価用ファイル
     */
    public void setHyokaUploadFile(FormFile file) {
        hyokaUploadFile = file;
    }

    /**
     * 事業コードを取得
     * @return 事業コード
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * 事業コードを設定
     * @param string 事業コード
     */
    public void setJigyoCd(String string) {
        jigyoCd = string;
    }

    /**
     * 事業IDを取得
     * @return 事業ID
     */
    public String getJigyoId() {
        return jigyoId;
    }

    /**
     * 事業IDを設定
     * @param string 事業ID
     */
    public void setJigyoId(String string) {
        jigyoId = string;
    }

    /**
     * 事業名選択リストを取得
     * @return 事業名選択リスト
     */
    public List getJigyoNameList() {
        return jigyoNameList;
    }

    /**
     * 回数を取得
     * @return 回数
     */
    public String getKaisu() {
        return kaisu;
    }

    /**
     * 回数を設定
     * @param string 回数
     */
    public void setKaisu(String string) {
        kaisu = string;
    }

    /**
     * 公開決裁番号を取得
     * @return 公開決裁番号
     */
    public String getKessaiNo() {
        return kessaiNo;
    }

    /**
     * 公開決裁番号を設定
     * @param string 公開決裁番号
     */
    public void setKessaiNo(String string) {
        kessaiNo = string;
    }

    /**
     * 公開フラグを取得
     * @return 公開フラグ
     */
    public String getKokaiFlg() {
        return kokaiFlg;
    }

    /**
     * 公開フラグを設定
     * @param string 公開フラグ
     */
    public void setKokaiFlg(String string) {
        kokaiFlg = string;
    }

    /**
     * 公開確定者IDを取得
     * @return 公開確定者ID
     */
    public String getKokaiID() {
        return kokaiID;
    }

    /**
     * 公開確定者IDを設定
     * @param string 公開確定者ID
     */
    public void setKokaiID(String string) {
        kokaiID = string;
    }

    /**
     * 年度を取得
     * @return 年度
     */
    public String getNendo() {
        return nendo;
    }

    /**
     * 年度を設定
     * @param string 年度
     */
    public void setNendo(String string) {
        nendo = string;
    }

    /**
     * 審査期限(日)を取得
     * @return 審査期限(日)
     */
    public String getShinsaKigenDay() {
        return shinsaKigenDay;
    }

    /**
     * 審査期限(日)を設定
     * @param string 審査期限(日)
     */
    public void setShinsaKigenDay(String string) {
        shinsaKigenDay = string;
    }

    /**
     * 審査期限(月)を取得
     * @return 審査期限(月)
     */
    public String getShinsaKigenMonth() {
        return shinsaKigenMonth;
    }

    /**
     * 審査期限(月)を設定
     * @param string 審査期限(月)
     */
    public void setShinsaKigenMonth(String string) {
        shinsaKigenMonth = string;
    }

    /**
     * 審査期限(年)を取得
     * @return 審査期限(年)
     */
    public String getShinsaKigenYear() {
        return shinsaKigenYear;
    }

    /**
     * 審査期限(年)を設定
     * @param string 審査期限(年)
     */
    public void setShinsaKigenYear(String string) {
        shinsaKigenYear = string;
    }

    /**
     * 業務担当係名を取得
     * @return 業務担当係名
     */
    public String getTantoKakari() {
        return tantoKakari;
    }

    /**
     * 業務担当係名を設定
     * @param string 業務担当係名
     */
    public void setTantoKakari(String string) {
        tantoKakari = string;
    }

    /**
     * 業務担当課を取得
     * @return 業務担当課
     */
    public String getTantokaName() {
        return tantokaName;
    }

    /**
     * 業務担当課を設定
     * @param string 業務担当課
     */
    public void setTantokaName(String string) {
        tantokaName = string;
    }

    /**
     * 添付ファイル(Mac)を取得
     * @return 添付ファイル(Mac)
     */
    public String getTenpuMac() {
        return tenpuMac;
    }

    /**
     * 添付ファイル(Mac)を設定
     * @param string 添付ファイル(Mac)
     */
    public void setTenpuMac(String string) {
        tenpuMac = string;
    }

    /**
     * 添付ファイル(Mac)を取得
     * @return 添付ファイル(Mac)
     */
    public FormFile getTenpuMacUploadFile() {
        return tenpuMacUploadFile;
    }

    /**
     * 添付ファイル(Mac)を設定
     * @param file 添付ファイル(Mac)
     */
    public void setTenpuMacUploadFile(FormFile file) {
        tenpuMacUploadFile = file;
    }

    /**
     * 添付文書名を取得
     * @return 添付文書名
     */
    public String getTenpuName() {
        return tenpuName;
    }

    /**
     * 添付文書名を設定
     * @param string 添付文書名
     */
    public void setTenpuName(String string) {
        tenpuName = string;
    }

    /**
     * 添付ファイル(Win)を取得
     * @return 添付ファイル(Win)
     */
    public String getTenpuWin() {
        return tenpuWin;
    }

    /**
     * 添付ファイル(Win)を設定
     * @param string 添付ファイル(Win)
     */
    public void setTenpuWin(String string) {
        tenpuWin = string;
    }

    /**
     * 添付ファイル(Win)を取得
     * @return 添付ファイル(Win)
     */
    public FormFile getTenpuWinUploadFile() {
        return tenpuWinUploadFile;
    }

    /**
     * 添付ファイル(Win)を設定
     * @param file 添付ファイル(Win)
     */
    public void setTenpuWinUploadFile(FormFile file) {
        tenpuWinUploadFile = file;
    }

    /**
     * 問い合わせ先E-mailを取得
     * @return 問い合わせ先E-mail
     */
    public String getToiawaseEmail() {
        return toiawaseEmail;
    }

    /**
     * 問い合わせ先E-mailを設定
     * @param string 問い合わせ先E-mail
     */
    public void setToiawaseEmail(String string) {
        toiawaseEmail = string;
    }

    /**
     * 問い合わせ先担当者名を取得
     * @return 問い合わせ先担当者名
     */
    public String getToiawaseName() {
        return toiawaseName;
    }

    /**
     * 問い合わせ先担当者名を設定
     * @param string 問い合わせ先担当者名
     */
    public void setToiawaseName(String string) {
        toiawaseName = string;
    }

    /**
     * 問い合わせ先電話番号を取得
     * @return 問い合わせ先電話番号
     */
    public String getToiawaseTel() {
        return toiawaseTel;
    }

    /**
     * 問い合わせ先電話番号を設定
     * @param string 問い合わせ先電話番号
     */
    public void setToiawaseTel(String string) {
        toiawaseTel = string;
    }

    /**
     * 学振受付期間（終了）(日)を取得
     * @return 学振受付期間（終了）(日)
     */
    public String getUketukekikanEndDay() {
        return uketukekikanEndDay;
    }

    /**
     * 学振受付期間（終了）(日)を設定
     * @param string 学振受付期間（終了）(日)
     */
    public void setUketukekikanEndDay(String string) {
        uketukekikanEndDay = string;
    }

    /**
     * 学振受付期間（終了）(月)を取得
     * @return 学振受付期間（終了）(月)
     */
    public String getUketukekikanEndMonth() {
        return uketukekikanEndMonth;
    }

    /**
     * 学振受付期間（終了）(月)を設定
     * @param string 学振受付期間（終了）(月)
     */
    public void setUketukekikanEndMonth(String string) {
        uketukekikanEndMonth = string;
    }

    /**
     * 学振受付期間（終了）(年)を取得
     * @return 学振受付期間（終了）(年)
     */
    public String getUketukekikanEndYear() {
        return uketukekikanEndYear;
    }

    /**
     * 学振受付期間（終了）(年)を設定
     * @param string 学振受付期間（終了）(年)
     */
    public void setUketukekikanEndYear(String string) {
        uketukekikanEndYear = string;
    }

    /**
     * 学振受付期間（開始）(日)を取得
     * @return 学振受付期間（開始）(日)
     */
    public String getUketukekikanStartDay() {
        return uketukekikanStartDay;
    }

    /**
     * 学振受付期間（開始）(日)を設定
     * @param string 学振受付期間（開始）(日)
     */
    public void setUketukekikanStartDay(String string) {
        uketukekikanStartDay = string;
    }

    /**
     * 学振受付期間（開始）(月)を取得
     * @return 学振受付期間（開始）(月)
     */
    public String getUketukekikanStartMonth() {
        return uketukekikanStartMonth;
    }

    /**
     * 学振受付期間（開始）(月)を設定
     * @param string 学振受付期間（開始）(月)
     */
    public void setUketukekikanStartMonth(String string) {
        uketukekikanStartMonth = string;
    }

    /**
     * 学振受付期間（開始）(年)を取得
     * @return 学振受付期間（開始）(年)
     */
    public String getUketukekikanStartYear() {
        return uketukekikanStartYear;
    }

    /**
     * 学振受付期間（開始）(年)を設定
     * @param string 学振受付期間（開始）(年)
     */
    public void setUketukekikanStartYear(String string) {
        uketukekikanStartYear = string;
    }

    /**
     * 事業名選択リストを設定
     * @param list 事業名選択リスト
     */
    public void setJigyoNameList(List list) {
        jigyoNameList = list;
    }

    /**
     * 評価用ファイル有無（なし／あり）リストを取得
     * @return 評価用ファイル有無（なし／あり）リスト
     */
    public List getFlgList() {
        return flgList;
    }

    /**
     * 評価用ファイル有無（なし／あり）リストを設定
     * @param list 評価用ファイル有無（なし／あり）リスト
     */
    public void setFlgList(List list) {
        flgList = list;
    }

    /**
     * ダウンロードURLを取得
     * @return ダウンロードURL
     */
    public String getDlUrl() {
        return dlUrl;
    }

    /**
     * ダウンロードURLを設定
     * @param string ダウンロードURL
     */
    public void setDlUrl(String string) {
        dlUrl = string;
    }

    /**
     * URLアドレスを取得
     * @return URLアドレス
     */
    public String getUrlAddress() {
        return urlAddress;
    }

    /**
     * URLアドレスを設定
     * @param string URLアドレス
     */
    public void setUrlAddress(String string) {
        urlAddress = string;
    }

    /**
     * URLタイトルを取得
     * @return URLタイトル
     */
    public String getUrlTitle() {
        return urlTitle;
    }

    /**
     * URLタイトルを設定
     * @param string URLタイトル
     */
    public void setUrlTitle(String string) {
        urlTitle = string;
    }

    /**
     * 研究者名簿登録最終締切日(年)を取得
     * @return 研究者名簿登録最終締切日(年)
     */
    public String getMeiboDateYear() {
        return meiboDateYear;
    }

    /**
     * 研究者名簿登録最終締切日(年)を設定
     * @param meiboDateYear 研究者名簿登録最終締切日(年)
     */
    public void setMeiboDateYear(String meiboDateYear) {
        this.meiboDateYear = meiboDateYear;
    }

    /**
     * 研究者名簿登録最終締切日(月)を取得
     * @return 研究者名簿登録最終締切日(月)
     */
    public String getMeiboDateMonth() {
        return meiboDateMonth;
    }

    /**
     * 研究者名簿登録最終締切日(月)を設定
     * @param meiboDateMonth 研究者名簿登録最終締切日(月)
     */
    public void setMeiboDateMonth(String meiboDateMonth) {
        this.meiboDateMonth = meiboDateMonth;
    }

    /**
     * 研究者名簿登録最終締切日(日)を取得
     * @return 研究者名簿登録最終締切日(日)
     */
    public String getMeiboDateDay() {
        return meiboDateDay;
    }

    /**
     * 研究者名簿登録最終締切日(日)を設定
     * @param meiboDateDay 研究者名簿登録最終締切日(日)
     */
    public void setMeiboDateDay(String meiboDateDay) {
        this.meiboDateDay = meiboDateDay;
    }

    // 2006/06/14 追加 李義華 ここから
    /**
     * 仮領域番号発行締切日(日)を取得
     * @return 仮領域番号発行締切日(日)
     */
    public String getKariryoikiNoEndDateDay() {
        return kariryoikiNoEndDateDay;
    }

    /**
     * 仮領域番号発行締切日(日)を設定
     * @param kariryoikiNoEndDateDay 仮領域番号発行締切日(日)
     */
    public void setKariryoikiNoEndDateDay(String kariryoikiNoEndDateDay) {
        this.kariryoikiNoEndDateDay = kariryoikiNoEndDateDay;
    }

    /**
     * 仮領域番号発行締切日(月)を取得
     * @return 仮領域番号発行締切日(月)
     */
    public String getKariryoikiNoEndDateMonth() {
        return kariryoikiNoEndDateMonth;
    }

    /**
     * 仮領域番号発行締切日(月)を設定
     * @param kariryoikiNoEndDateMonth 仮領域番号発行締切日(月)
     */
    public void setKariryoikiNoEndDateMonth(String kariryoikiNoEndDateMonth) {
        this.kariryoikiNoEndDateMonth = kariryoikiNoEndDateMonth;
    }

    /**
     * 仮領域番号発行締切日(年)を取得
     * @return 仮領域番号発行締切日(年)
     */
    public String getKariryoikiNoEndDateYear() {
        return kariryoikiNoEndDateYear;
    }

    /**
     * 仮領域番号発行締切日(年)を設定
     * @param kariryoikiNoEndDateYear 仮領域番号発行締切日(年)
     */
    public void setKariryoikiNoEndDateYear(String kariryoikiNoEndDateYear) {
        this.kariryoikiNoEndDateYear = kariryoikiNoEndDateYear;
    }

    // 2006/06/14 追加 李義華 ここまで
    // 2006/07/10 追加 李義華 ここから
    /**
     * 領域代表者確定締切日(年)を取得
     * @return 領域代表者確定締切日(年)
     */
    public String getRyoikiEndDateYear() {
        return ryoikiEndDateYear;
    }

    /**
     * 領域代表者確定締切日(年)を設定
     * @param ryoikiEndDateYear 領域代表者確定締切日(年)
     */
    public void setRyoikiEndDateYear(String ryoikiEndDateYear) {
        this.ryoikiEndDateYear = ryoikiEndDateYear;
    }

    /**
     * 領域代表者確定締切日(月)を取得
     * @return 領域代表者確定締切日(月)
     */
    public String getRyoikiEndDateMonth() {
        return ryoikiEndDateMonth;
    }

    /**
     * 領域代表者確定締切日(月)を設定
     * @param ryoikiEndDateMonth 領域代表者確定締切日(月)
     */
    public void setRyoikiEndDateMonth(String ryoikiEndDateMonth) {
        this.ryoikiEndDateMonth = ryoikiEndDateMonth;
    }

    /**
     * 領域代表者確定締切日(日)を取得
     * @return 領域代表者確定締切日(日)
     */
    public String getRyoikiEndDateDay() {
        return ryoikiEndDateDay;
    }

    /**
     * 領域代表者確定締切日(日)を設定
     * @param ryoikiEndDateDay 領域代表者確定締切日(日)
     */
    public void setRyoikiEndDateDay(String ryoikiEndDateDay) {
        this.ryoikiEndDateDay = ryoikiEndDateDay;
    }
    // 2006/07/10 追加 李義華 ここまで

// 2006/10/23 追加 易旭 ここから
    /**
     * 利害関係入力締切日(年)を取得
     * @return 利害関係入力締切日(年)
     */
    public String getRigaiEndDateYear() {
        return rigaiEndDateYear;
    }

    /**
     * 利害関係入力締切日(年)を設定
     * @param rigaiEndDateYear 利害関係入力締切日(年)
     */
    public void setRigaiEndDateYear(String rigaiEndDateYear) {
        this.rigaiEndDateYear = rigaiEndDateYear;
    }

    /**
     * 利害関係入力締切日(月)を取得
     * @return 利害関係入力締切日(月)
     */
    public String getRigaiEndDateMonth() {
        return rigaiEndDateMonth;
    }

    /**
     * 利害関係入力締切日(月)を設定
     * @param rigaiEndDateMonth 利害関係入力締切日(月)
     */
    public void setRigaiEndDateMonth(String rigaiEndDateMonth) {
        this.rigaiEndDateMonth = rigaiEndDateMonth;
    }

    /**
     * 利害関係入力締切日(日)を取得
     * @return 利害関係入力締切日(日)
     */
    public String getRigaiEndDateDay() {
        return rigaiEndDateDay;
    }

    /**
     * 利害関係入力締切日(日)を設定
     * @param rigaiEndDateDay 利害関係入力締切日(日)
     */
    public void setRigaiEndDateDay(String rigaiEndDateDay) {
        this.rigaiEndDateDay = rigaiEndDateDay;
    }
// 2006/10/23 追加 易旭 ここまで
    
// 2007/02/07 張志男　追加ここから
   
    /**
     * 応募内容ファイルページ数範囲(下限)を取得
     * @return pageFrom 応募内容ファイルページ数範囲(下限)
     */
    public String getPageFrom() {
        return pageFrom;
    }

    /**
     * 応募内容ファイルページ数範囲(下限)を設定
     * @param string pageFrom 応募内容ファイルページ数範囲(下限)
     */
    public void setPageFrom(String pageFrom) {
        this.pageFrom = pageFrom;
    }
    
    
    /**
     * 応募内容ファイルページ数範囲(上限)
     * @return pageTo 応募内容ファイルページ数範囲(上限)
     */
    public String getPageTo() {
        return pageTo;
    }

    /**
     * 応募内容ファイルページ数範囲(上限)を設定
     * @param string pageTo 応募内容ファイルページ数範囲(上限)
     */
    public void setPageTo(String pageTo) {
        this.pageTo = pageTo;
    } 
// 2007/02/07 張志男　追加ここまで
}
