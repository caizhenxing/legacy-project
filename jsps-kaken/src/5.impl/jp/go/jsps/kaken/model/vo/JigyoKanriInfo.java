/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : JigyoKanriInfo.java
 *    Description : 事業管理情報を保持するクラス
 *
 *    Author      : DIS.miaom & DIS.dyh
 *    Date        : 2006/06/19
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Date;

import jp.go.jsps.kaken.util.FileResource;

/**
 * 事業管理情報を保持するクラス。
 */
public class JigyoKanriInfo extends JigyoKanriPk{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	//static final long serialVersionUID = -4533191159575948040L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 年度 */
	private String    nendo;
    
    /** 年度（西暦） */
    private String    nendoSeireki;
	
	/** 回数 */
	private String    kaisu;
	
	/** 事業名 */
	private String    jigyoName;
	
	/** 事業区分 */
	private String    jigyoKubun;
	
	/** 審査区分 */
	private String    shinsaKubun;
	
	/** 業務担当課 */
	private String    tantokaName;
	
	/** 業務担当係名 */
	private String    tantoKakari;
	
	/** 問い合わせ先担当者名 */
	private String    toiawaseName;
	
	/** 問い合わせ先電話番号 */
	private String    toiawaseTel;	
	
	/** 問い合わせ先E-mail */
	private String    toiawaseEmail;
	
	/** 学振受付期間（開始） */
	private Date      uketukekikanStart;
	
	/** 学振受付期間（終了） */
	private Date      uketukekikanEnd;

    /** 領域代表者確定締切日 */
    private Date      ryoikiKakuteikikanEnd;

	/** 審査期限 */
	private Date      shinsaKigen;
	
	/** 添付文書名 */
	private String    tenpuName;
	
	/** 添付ファイル格納フォルダ(Win) */
	private String    tenpuWin;	
	
	/** 添付ファイル格納フォルダ(Mac) */
	private String    tenpuMac;	
 
//2007/02/03 苗　追加ここから
    /** 応募内容ファイルページ数(下限) */
    private String pageFrom;

    /** 応募内容ファイルページ数(上限) */
    private String pageTo;
//2007/02/03　苗　追加ここまで    
	
	/** 評価用ファイル有無 */
	private String    hyokaFileFlg;
	
	/** 評価用ファイル名 */
	private String    hyokaName;
	
	/** 評価用ファイル格納フォルダ */
	private String    hyokaFile;
	
	/** 公開フラグ */
	private String    kokaiFlg;
	
	/** 公開決裁番号 */
	private String    kessaiNo;
	
	/** 公開確定者ID */
	private String    kokaiID;

	/** データ保管日 */
	private Date      hokanDate;
	
	/** 保管有効期限 */
	private Date      YukoDate;
	
	/** 備考 */
	private String    biko;
	
	/** 削除フラグ */
	private String    delFlg;
    
    //2006/06/20 lwj add begin
    /** 審査希望部門（系等）コード */
    private String    kiboubumonCd;
    //2006/06/20 lwj add end
	
	//------業務担当者向け機能・事業管理
	/** 事業コード（事業マスタ） */
	private String    jigyoCd;

	/** 添付ファイル（Win） */
	private FileResource    tenpuWinFileRes;
	
	/** 添付ファイル（Mac） */
	private FileResource    tenpuMacFileRes;

	/** 評価用ファイル */
	private FileResource    hyokaFileRes;
	
	//------審査担当者向け機能
	/** 学振問い合わせ先郵便番号 */
	private String    toiawaseZip;	

	/** 学振問い合わせ先住所 */
	private String    toiawaseJusho;	
	
	//	2005/04/19 追加 ここから----------------------------------------------
	//理由 URLの追加のため
	
	/** URLタイトル */
	private String urlTitle;
	
	/** URLアドレス */
	private String urlAddress;

	/**　ダウンロードURL */
	private String dlUrl;

	// 2005/04/24 追加 ここから----------------------------------------------
	// 理由 更新時のファイル削除処理追加のため
	/** Winファイルの削除フラグ */
	private boolean delWinFileFlg = false;
	
	/** Macファイルの削除フラグ */
	private boolean delMacFileFlg = false;
	
	/** 評価ファイルの削除フラグ */
	private boolean delHyokaFileFlg = false;
	// 追加 ここから----------------------------------------------
	
	// 2006/02/08 追加  苗　ここから----------------------------------------------
	// 理由 研究者名簿締切日追加のため
	private Date meiboDate;
	// ここまで
	
	// 2006/06/13 追加  李義華　ここから
	// 理由 仮領域番号発行締切日追加のため
    /** 仮領域番号発行締切日 */
	private Date kariryoikiNoEndDate;
    
    // 理由 領域代表者確定締切日追加のため
    /** 領域代表者確定締切日 */
    private Date ryoikiEndDate;
    // 2006/06/13 追加  李義華　ここまで
	
    // 2006/06/23 追加 易旭 ここから
    // 理由 利害関係入力締切日追加のため
    private Date rigaiEndDate;
    // 2006/06/23 追加 易旭 ここまで
	  //---------------------------------------------------------------------
	  // Constructors
	  //---------------------------------------------------------------------	
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

    /**
	 * コンストラクタ。
	 */
	public JigyoKanriInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

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
     * 年度（西暦）を取得
     * @return 年度（西暦）
     */
    public String getNendoSeireki() {
        return nendoSeireki;
    }

    /**
     * 年度（西暦）を設定
     * @param nendoSeireki 年度（西暦）
     */
    public void setNendoSeireki(String nendoSeireki) {
        this.nendoSeireki = nendoSeireki;
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
     * 事業名を取得
     * @return 事業名
     */
    public String getJigyoName() {
        return jigyoName;
    }

    /**
     * 事業名を設定
     * @param string 事業名
     */
    public void setJigyoName(String string) {
        jigyoName = string;
    }

    /**
     * 事業区分を取得
     * @return 事業区分
     */
    public String getJigyoKubun() {
        return jigyoKubun;
    }

    /**
     * 事業区分を設定
     * @param string 事業区分
     */
    public void setJigyoKubun(String string) {
        jigyoKubun = string;
    }

    /**
     * 審査区分を取得
     * @return 審査区分
     */
    public String getShinsaKubun() {
        return shinsaKubun;
    }

    /**
     * 審査区分を設定
     * @param string 審査区分
     */
    public void setShinsaKubun(String string) {
        shinsaKubun = string;
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
     * 学振受付期間（開始）を取得
     * @return 学振受付期間（開始）
     */
    public Date getUketukekikanStart() {
        return uketukekikanStart;
    }

    /**
     * 学振受付期間（開始）を設定
     * @param date 学振受付期間（開始）
     */
    public void setUketukekikanStart(Date date) {
        uketukekikanStart = date;
    }

    /**
     * 学振受付期間（終了）を取得
     * @return 学振受付期間（終了）
     */
    public Date getUketukekikanEnd() {
        return uketukekikanEnd;
    }

    /**
     * 学振受付期間（終了）を設定
     * @param date 学振受付期間（終了）
     */
    public void setUketukekikanEnd(Date date) {
        uketukekikanEnd = date;
    }

    /**
     * 領域代表者確定締切日を取得
     * @return 領域代表者確定締切日
     */
    public Date getRyoikiKakuteikikanEnd() {
        return ryoikiKakuteikikanEnd;
    }

    /**
     * 領域代表者確定締切日を設定
     * @param ryoikiKakuteikikanEnd 領域代表者確定締切日
     */
    public void setRyoikiKakuteikikanEnd(Date ryoikiKakuteikikanEnd) {
        this.ryoikiKakuteikikanEnd = ryoikiKakuteikikanEnd;
    }

    /**
     * 審査期限を取得
     * @return 審査期限
     */
    public Date getShinsaKigen() {
        return shinsaKigen;
    }

    /**
     * 審査期限を設定
     * @param date 審査期限
     */
    public void setShinsaKigen(Date date) {
        shinsaKigen = date;
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
     * 添付ファイル格納フォルダ(Win)を取得
     * @return 添付ファイル格納フォルダ(Win)
     */
    public String getTenpuWin() {
        return tenpuWin;
    }

    /**
     * 添付ファイル格納フォルダ(Win)を設定
     * @param string 添付ファイル格納フォルダ(Win)
     */
    public void setTenpuWin(String string) {
        tenpuWin = string;
    }

    /**
     * 添付ファイル格納フォルダ(Mac)を取得
     * @return 添付ファイル格納フォルダ(Mac)
     */
    public String getTenpuMac() {
        return tenpuMac;
    }

    /**
     * 添付ファイル格納フォルダ(Mac)を設定
     * @param string 添付ファイル格納フォルダ(Mac)
     */
    public void setTenpuMac(String string) {
        tenpuMac = string;
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
     * 評価用ファイル名を取得
     * @return 評価用ファイル名
     */
    public String getHyokaName() {
        return hyokaName;
    }

    /**
     * 評価用ファイル名を設定
     * @param string 評価用ファイル名
     */
    public void setHyokaName(String string) {
        hyokaName = string;
    }

    /**
     * 評価用ファイル格納フォルダを取得
     * @return 評価用ファイル格納フォルダ
     */
    public String getHyokaFile() {
        return hyokaFile;
    }

    /**
     * 評価用ファイル格納フォルダを設定
     * @param string 評価用ファイル格納フォルダ
     */
    public void setHyokaFile(String string) {
        hyokaFile = string;
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
     * データ保管日を取得
     * @return データ保管日
     */
    public Date getHokanDate() {
        return hokanDate;
    }

    /**
     * データ保管日を設定
     * @param date データ保管日
     */
    public void setHokanDate(Date date) {
        hokanDate = date;
    }

    /**
     * 保管有効期限を取得
     * @return 保管有効期限
     */
    public Date getYukoDate() {
        return YukoDate;
    }

    /**
     * 保管有効期限を設定
     * @param date 保管有効期限
     */
    public void setYukoDate(Date date) {
        YukoDate = date;
    }

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
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * 削除フラグを設定
     * @param string 削除フラグ
     */
    public void setDelFlg(String string) {
        delFlg = string;
    }

    /**
     * 審査希望部門（系等）コードを取得
     * @return 審査希望部門（系等）コード
     */
    public String getKiboubumonCd() {
        return kiboubumonCd;
    }

    /**
     * 審査希望部門（系等）コードを設定
     * @param kiboubumonCd 審査希望部門（系等）コード
     */
    public void setKiboubumonCd(String kiboubumonCd) {
        this.kiboubumonCd = kiboubumonCd;
    }

    /**
     * 事業コード（事業マスタ）を取得
     * @return 事業コード（事業マスタ）
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * 事業コード（事業マスタ）を設定
     * @param string 事業コード（事業マスタ）
     */
    public void setJigyoCd(String string) {
        jigyoCd = string;
    }

    /**
     * 添付ファイル（Win）を取得
     * @return 添付ファイル（Win）
     */
    public FileResource getTenpuWinFileRes() {
        return tenpuWinFileRes;
    }

    /**
     * 添付ファイル（Win）を設定
     * @param resource 添付ファイル（Win）
     */
    public void setTenpuWinFileRes(FileResource resource) {
        tenpuWinFileRes = resource;
    }

    /**
     * 添付ファイル（Mac）を取得
     * @return 添付ファイル（Mac）
     */
    public FileResource getTenpuMacFileRes() {
        return tenpuMacFileRes;
    }

    /**
     * 添付ファイル（Mac）を設定
     * @param resource 添付ファイル（Mac）
     */
    public void setTenpuMacFileRes(FileResource resource) {
        tenpuMacFileRes = resource;
    }

    /**
     * 評価用ファイルを取得
     * @return 評価用ファイル
     */
	public FileResource getHyokaFileRes() {
		return hyokaFileRes;
	}

    /**
     * 評価用ファイルを設定
     * @param resource 評価用ファイル
     */
    public void setHyokaFileRes(FileResource resource) {
        hyokaFileRes = resource;
    }

    /**
     * 学振問い合わせ先郵便番号を取得
     * @return 学振問い合わせ先郵便番号
     */
    public String getToiawaseZip() {
        return toiawaseZip;
    }

    /**
     * 学振問い合わせ先郵便番号を設定
     * @param string 学振問い合わせ先郵便番号
     */
    public void setToiawaseZip(String string) {
        toiawaseZip = string;
    }

    /**
     * 学振問い合わせ先住所を取得
     * @return 学振問い合わせ先住所
     */
	public String getToiawaseJusho() {
		return toiawaseJusho;
	}

    /**
     * 学振問い合わせ先住所を設定
     * @param string 学振問い合わせ先住所
     */
	public void setToiawaseJusho(String string) {
		toiawaseJusho = string;
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
     * Winファイルの削除フラグを取得
     * @return Winファイルの削除フラグ
     */
    public boolean isDelWinFileFlg() {
        return delWinFileFlg;
    }

    /**
     * Winファイルの削除フラグを設定
     * @param delWinFileFlg Winファイルの削除フラグ
     */
    public void setDelWinFileFlg(boolean delWinFileFlg) {
        this.delWinFileFlg = delWinFileFlg;
    }

    /**
     * Macファイルの削除フラグを取得
     * @return Macファイルの削除フラグ
     */
    public boolean isDelMacFileFlg() {
        return delMacFileFlg;
    }

    /**
     * Macファイルの削除フラグを設定
     * @param delMacFileFlg Macファイルの削除フラグ
     */
    public void setDelMacFileFlg(boolean delMacFileFlg) {
        this.delMacFileFlg = delMacFileFlg;
    }

    /**
     * 評価ファイルの削除フラグを取得
     * @return 評価ファイルの削除フラグ
     */
	public boolean isDelHyokaFileFlg() {
		return delHyokaFileFlg;
	}

    /**
     * 評価ファイルの削除フラグを設定
     * @param delHyokaFileFlg 評価ファイルの削除フラグ
     */
    public void setDelHyokaFileFlg(boolean delHyokaFileFlg) {
        this.delHyokaFileFlg = delHyokaFileFlg;
    }

    /**
     * 研究者名簿締切日を取得
     * @return 研究者名簿締切日
     */
    public Date getMeiboDate() {
        return meiboDate;
    }

    /**
     * 研究者名簿締切日を設定
     * @param meiboDate 研究者名簿締切日
     */
    public void setMeiboDate(Date meiboDate) {
        this.meiboDate = meiboDate;
    }

	// 2006/06/14 追加 李義華 ここから
    /**
     * 仮領域番号発行締切日を取得
     * @return 仮領域番号発行締切日
     */
    public Date getKariryoikiNoEndDate() {
        return kariryoikiNoEndDate;
    }

    /**
     * 仮領域番号発行締切日を設定
     * @param kariryoikiNoEndDate 仮領域番号発行締切日
     */
    public void setKariryoikiNoEndDate(Date kariryoikiNoEndDate) {
        this.kariryoikiNoEndDate = kariryoikiNoEndDate;
    }

    /**
     * 領域代表者確定締切日を取得
     * @return 領域代表者確定締切日
     */
    public Date getRyoikiEndDate() {
        return ryoikiEndDate;
    }

    /**
     * 領域代表者確定締切日を設定
     * @param ryoikiEndDate 領域代表者確定締切日
     */
    public void setRyoikiEndDate(Date ryoikiEndDate) {
        this.ryoikiEndDate = ryoikiEndDate;
    }
    // 2006/07/10 追加 李義華 ここまで

    // 2006/06/23 追加 易旭 ここから
    /**
     * 利害関係入力締切日を取得
     * @return 利害関係入力締切日
     */
	public Date getRigaiEndDate() {
		return rigaiEndDate;
	}

    /**
     * 利害関係入力締切日を設定
     * @param rigaiEndDate 利害関係入力締切日
     */
	public void setRigaiEndDate(Date rigaiEndDate) {
		this.rigaiEndDate = rigaiEndDate;
	}
    // 2006/06/23 追加 易旭 ここまで
    

    /**
     * 応募内容ファイルページ数(下限)設定
     * @param  pageFrom 設定値
     */
    public void setPageFrom(String pageFrom) {
        this.pageFrom = pageFrom;
    }
    
    /**
     * 応募内容ファイルページ数(下限)取得
     * @return 応募内容ファイルページ数(下限)の値
     */
    public String getPageFrom() {
        return pageFrom;
    }
    
    /**
     * 応募内容ファイルページ数(上限)設定
     * @param  pageTo 設定値
     */
    public void setPageTo(String pageTo) {
        this.pageTo = pageTo;
    }
    
    /**
     * 応募内容ファイルページ数(上限)取得
     * @return 応募内容ファイルページ数(上限)の値
     */
    public String getPageTo() {
        return pageTo;
    }
}