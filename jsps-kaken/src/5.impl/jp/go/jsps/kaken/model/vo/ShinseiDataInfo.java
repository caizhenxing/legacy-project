/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : ShinseiDataInfo.java
 *    Description : 申請データ情報を保持するクラス。
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionErrors;

import jp.go.jsps.kaken.model.vo.shinsei.DaihyouInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KadaiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KanrenBunyaKenkyushaInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;

/**
 * 申請データ情報を保持するクラス。
 * 
 * ID RCSfile=$RCSfile: ShinseiDataInfo.java,v $
 * Revision=$Revision: 1.2 $
 * Date=$Date: 2007/07/09 02:55:34 $
 */
public class ShinseiDataInfo extends ShinseiDataPk {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 申請番号 */
	private String uketukeNo;

	/** 事業ID */
	private String jigyoId;
	
	/** 年度 */
	private String nendo;
	
	/** 回数 */
	private String kaisu;
	
	/** 事業名 */
	private String jigyoName;
	
	/** 申請者ID */
	private String shinseishaId;

	/** 申請書作成日 */
	private Date sakuseiDate;

	/** 所属期間承認日 */
	private Date shoninDate;
    
    //add start ly 2006/06/28
    /** 所属期間承認日(mark) */
    private String shoninDateMark;
    //add end ly 2006/06/28

	/** 学振受理日 */
	private Date jyuriDate;
	
	/** 学振受理日Flg */
	private String jyuriDateFlg;

	//・・・・・・・・・・
	
	/** 研究代表者情報　*/
	private DaihyouInfo daihyouInfo = new DaihyouInfo();

	/** 研究課題情報　*/
	private KadaiInfo kadaiInfo = new KadaiInfo();

	/** 研究経費情報 */
	private KenkyuKeihiSoukeiInfo kenkyuKeihiSoukeiInfo = new KenkyuKeihiSoukeiInfo();

	/** 研究組織の形態番号 */
	private String soshikiKeitaiNo;
	
	/** 研究組織の形態 */
	private String soshikiKeitai;
	
	/** 分担金の有無 */
	private String buntankinFlg;
	
	/** 研究支援者雇用経費 */
	private String koyohi;
	
	/** 研究者数 */
	private String kenkyuNinzu;
	
	//2005/03/30 追加 ----------------------------ここから
	//理由 研究組織表に研究協力者が追加されたため
	/** 研究協力者数 */
	private String kyoryokushaNinzu;

	/** 研究者数(カウント用) */
	private int kenkyuNinzuInt = 1;

	/** 研究協力者数(カウント用) */
	private int kyoryokushaNinzuInt = 0;
	//2005/03/30 追加 ----------------------------ここまで
	
	/** 他機関の分担者数 */
	private String takikanNinzu;
	
	/** 研究組織情報（研究代表者及び分担者リスト） */
	private List kenkyuSoshikiInfoList = new ArrayList();
	
	/** 新規継続区分 */
	private String shinseiKubun;
	
	/** 継続分の研究課題番号 */
	private String kadaiNoKeizoku;
	
	/** 研究計画最終年度前年度の応募 */
	private String shinseiFlgNo;
	
	/** 申請の有無 */
	private String shinseiFlg;
	
	/** 最終年度課題番号 */
	private String kadaiNoSaisyu;
	
	/** 開示希望の有無番号 */
	private String kaijikiboFlgNo;
	
	/** 開示希望の有無 */
	private String kaijiKiboFlg;
	
	/** 海外分野コード */
	private String kaigaibunyaCd;
	
	/** 海外分野名称 */
	private String kaigaibunyaName;
	
	/** 海外分野名称略称 */
	private String kaigaibunyaNameRyaku;
	
	//・・・・・・・・・・
	
	/** 関連分野研究者情報 */
	private KanrenBunyaKenkyushaInfo[] kanrenBunyaKenkyushaInfo = makeKanrenBunyaKenkyushaInfoArray(3);
	
	//・・・・・・・・・・

	/** XMLの格納パス */
	private String xmlPath;

	/** PDFの格納パス */
	private String pdfPath;

	/** 受理結果 */
	private String juriKekka;

	/** 受理結果備考 */
	private String juriBiko;
	
	/** 受理整理番号 */
	private String seiriNo;

	/** 推薦書の格納パス */
	private String suisenshoPath;

	/** １次審査結果(ABC) */
	private String kekka1Abc;
	
	/** １次審査結果(点数) */
	private String kekka1Ten;
	
	/** １次審査結果（点数順） */
	private String kekka1TenSorted;	

	/** １次審査備考 */
	private String shinsa1Biko;

	/** ２次審査結果 */
	private String kekka2;

	/** 総経費（学振入力） */
	private String souKehi;

	/** 初年度経費（学振入力） */
	private String shonenKehi;

	/** 業務担当者記入欄 */
	private String shinsa2Biko;

	/** 削除フラグ */
	private String delFlg;

	//----- 以下は事業管理テーブルより -----
	/** 事業CD */
	private String jigyoCd;
	
	/** 事業年度（西暦下２ケタ）*/
	private String nendoSeireki;
	
// 2006/02/13 追加Start
	/** 学振受付期間（終了） */
	private Date uketukekikanEnd;
// 2006/02/13 Nae
	
	//----- 添付ファイル -----
	/** 添付ファイル情報配列 */
	private TenpuFileInfo[] tenpuFileInfos;
	
	//2005/04/19 追加 --------------------------ここから
	//理由 若手研究の場合、誕生日情報が必要なため
    /** 誕生日 */
	private Date birthDay;
	//2005/04/19 追加 --------------------------ここまで
	
	//2006/02/08 追加 --------------------------ここから
	//理由 特別研究促進費（若手研究相当）の場合、研究者名簿締切日情報が必要なため
    /** 研究者名簿締切日 */
	private Date meiboDate;

    /** 研究者名簿締切日(和暦) */
	private String meiboDateWareki;
    //Nae 追加 --------------------------ここまで
	
    // 2006/02/09 Start
	// 理由は申請資格フラグ、採用年月日、勤務時間数、特別研究員奨励費内約額、資格取得年月日、資格取得前機関名、育休等開始日、育休等終了日を追加する
	/** 申請資格フラグ */
	private String ouboShikaku;
	
	/** 採用年月日 */
	private Date saiyoDate;
	
	/** 勤務時間数 */
	private String kinmuHour;

	/** 特別研究員奨励費内約額 */
	private String naiyakugaku;
	
	/** 資格取得年月日 */
	private Date sikakuDate;
	
	/** 資格取得前機関名 */
	private String syuTokumaeKikan;
	
	/** 育休等開始日 */
	private Date ikukyuStartDate;
	
	/** 育休等終了日 */
	private Date ikukyuEndDate;
	// Nae End

	/** 審査希望領域名 */
	private String shinsaRyoikiName;

	/** 審査希望領域コード */
	private String shinsaRyoikiCd;

	// 20060525 Start 特定領域追加のため項目を追加
	/** 計画研究・公募研究・終了研究領域区分 */
	private String kenkyuKubun;

	/** 大幅な変更を伴う研究課題 */
	private String changeFlg = "0";

	/** 領域番号 */
	private String ryouikiNo;

	/** 領域略称名 */
	private String ryouikiRyakuName;

	/** 研究項目番号 */
	private String ryouikiKoumokuNo;

	/** 調整班 */
	private String chouseiFlg = "0";
	// Horikoshi End

//	 20050725
    /** 記号(コード･･･アルファベットなど) **/
	private String kigou;

    /** キーワード(名称) **/
	private String keyName;

    /** 細目表以外のキーワード(名称) **/
	private String keyOtherName;
// Horikoshi

    /** 申請状況ID */
    private String jokyoId;

    // 2006/06/27 LY add 
    /** 申請状況ID配列 */
    private String[] jokyoIds;
    
    /** 再申請フラグ */
    private String saishinseiFlg;
    
//  2006/06/20 by jzx add start
    /** 領域代表者確定日 */
    private Date ryoikiKakuteiDate;
    
    /** 領域代表者確定日Flg */
    private String ryoikiKakuteiDateFlg;
//  2006/06/20 by jzx add end 
    
//2006/06/30 苗　追加ここから
    /** URL */
    private String url;
//2006/06/30　苗　追加ここまで
    
//2007/02/02 苗　追加ここから
    /** 特別研究員奨励費課題番号-年度 */
    private String shoreihiNoNendo;
    
    /** 特別研究員奨励費課題番号-整理番号 */
    private String shoreihiNoSeiri = "";
//2007/02/02　苗　追加ここまで    
 
//  <!-- ADD　START 2007/07/06 BIS 張楠 -->    
//  H19完全電子化及び制度改正.
//  入力した研究組織にエラーがある場合、「応募情報入力」画面でエラーメッセージを表示し、「研究組織表」画面でエラーになる項目の背景色を変更する。	
    /** エラーメッセジー */
    private HashMap errorsMap;
//	<!-- ADD　END　 2007/07/06 BIS 張楠 -->    
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
    
    /**
	 * コンストラクタ。
	 */
	public ShinseiDataInfo() {
		super();
	}
		
	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------
	/**
	 * 関連分野研究者情報を初期化
	 * @param arrayLength
	 * @return
	 */
	private KanrenBunyaKenkyushaInfo[] makeKanrenBunyaKenkyushaInfoArray(int arrayLength){
		KanrenBunyaKenkyushaInfo[] infoArray = new KanrenBunyaKenkyushaInfo[arrayLength];
		for(int i=0; i<arrayLength; i++){
			infoArray[i] = new KanrenBunyaKenkyushaInfo();
		}
		return infoArray;
	}	

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
     * 分担金の有無を取得
	 * @return 分担金の有無
	 */
	public String getBuntankinFlg() {
		return buntankinFlg;
	}

    /**
     * 分担金の有無を設定
     * @param string 分担金の有無
     */
    public void setBuntankinFlg(String string) {
        buntankinFlg = string;
    }

	/**
     * 研究代表者情報を取得
	 * @return 研究代表者情報
	 */
	public DaihyouInfo getDaihyouInfo() {
		return daihyouInfo;
	}

    /**
     * 研究代表者情報を設定
     * @param info 研究代表者情報
     */
    public void setDaihyouInfo(DaihyouInfo info) {
        daihyouInfo = info;
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
     * 事業CDを取得
	 * @return 事業CD
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

    /**
     * 事業CDを設定
     * @param string 事業CD
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
     * 申請状況IDを取得
	 * @return 申請状況ID
	 */
	public String getJokyoId() {
		return jokyoId;
	}

    /**
     * 申請状況IDを設定
     * @param string 申請状況ID
     */
    public void setJokyoId(String string) {
        jokyoId = string;
    }

	/**
     * 受理結果備考を取得
	 * @return 受理結果備考
	 */
	public String getJuriBiko() {
		return juriBiko;
	}

    /**
     * 受理結果備考を設定
     * @param string 受理結果備考
     */
    public void setJuriBiko(String string) {
        juriBiko = string;
    }

	/**
     * 受理結果を取得
	 * @return 受理結果
	 */
	public String getJuriKekka() {
		return juriKekka;
	}

    /**
     * 受理結果を設定
     * @param string 受理結果
     */
    public void setJuriKekka(String string) {
        juriKekka = string;
    }

	/**
     * 学振受理日を取得
	 * @return 学振受理日
	 */
	public Date getJyuriDate() {
		return jyuriDate;
	}

    /**
     * 学振受理日を設定
     * @param date 学振受理日
     */
    public void setJyuriDate(Date date) {
        jyuriDate = date;
    }

	/**
     * 研究課題情報を取得
	 * @return 研究課題情報
	 */
	public KadaiInfo getKadaiInfo() {
		return kadaiInfo;
	}

    /**
     * 研究課題情報を設定
     * @param info 研究課題情報
     */
    public void setKadaiInfo(KadaiInfo info) {
        kadaiInfo = info;
    }

	/**
     * 継続分の研究課題番号を取得
	 * @return 継続分の研究課題番号
	 */
	public String getKadaiNoKeizoku() {
		return kadaiNoKeizoku;
	}

    /**
     * 継続分の研究課題番号を設定
     * @param string 継続分の研究課題番号
     */
    public void setKadaiNoKeizoku(String string) {
        kadaiNoKeizoku = string;
    }

	/**
     * 最終年度課題番号を取得
	 * @return 最終年度課題番号
	 */
	public String getKadaiNoSaisyu() {
		return kadaiNoSaisyu;
	}

    /**
     * 最終年度課題番号を設定
     * @param string 最終年度課題番号
     */
    public void setKadaiNoSaisyu(String string) {
        kadaiNoSaisyu = string;
    }

	/**
     * 開示希望の有無を取得
	 * @return 開示希望の有無
	 */
	public String getKaijiKiboFlg() {
		return kaijiKiboFlg;
	}

    /**
     * 開示希望の有無を設定
     * @param string 開示希望の有無
     */
    public void setKaijiKiboFlg(String string) {
        kaijiKiboFlg = string;
    }

	/**
     * 開示希望の有無番号を取得
	 * @return 開示希望の有無番号
	 */
	public String getKaijikiboFlgNo() {
		return kaijikiboFlgNo;
	}

    /**
     * 開示希望の有無番号を設定
     * @param string 開示希望の有無番号
     */
    public void setKaijikiboFlgNo(String string) {
        kaijikiboFlgNo = string;
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
     * 関連分野研究者情報を取得
	 * @return 関連分野研究者情報
	 */
	public KanrenBunyaKenkyushaInfo[] getKanrenBunyaKenkyushaInfo() {
		return kanrenBunyaKenkyushaInfo;
	}

    /**
     * 関連分野研究者情報を設定
     * @param infos 関連分野研究者情報
     */
    public void setKanrenBunyaKenkyushaInfo(KanrenBunyaKenkyushaInfo[] infos) {
        kanrenBunyaKenkyushaInfo = infos;
    }

	/**
     * １次審査結果(ABC)を取得
	 * @return １次審査結果(ABC)
	 */
	public String getKekka1Abc() {
		return kekka1Abc;
	}

    /**
     * １次審査結果(ABC)を設定
     * @param string １次審査結果(ABC)
     */
    public void setKekka1Abc(String string) {
        kekka1Abc = string;
    }

	/**
     * １次審査結果(点数)を取得
	 * @return １次審査結果(点数)
	 */
	public String getKekka1Ten() {
		return kekka1Ten;
	}

    /**
     * １次審査結果(点数)を設定
     * @param string １次審査結果(点数)
     */
    public void setKekka1Ten(String string) {
        kekka1Ten = string;
    }

	/**
     * ２次審査結果を取得
	 * @return ２次審査結果
	 */
	public String getKekka2() {
		return kekka2;
	}

    /**
     * ２次審査結果を設定
     * @param string ２次審査結果
     */
    public void setKekka2(String string) {
        kekka2 = string;
    }

	/**
     * 研究経費情報を取得
	 * @return 研究経費情報
	 */
	public KenkyuKeihiSoukeiInfo getKenkyuKeihiSoukeiInfo() {
		return kenkyuKeihiSoukeiInfo;
	}

	/**
     * 研究者数を取得
	 * @return 研究者数
	 */
	public String getKenkyuNinzu() {
		return kenkyuNinzu;
	}

    /**
     * 研究者数を設定
     * @param string 研究者数
     */
    public void setKenkyuNinzu(String string) {
        kenkyuNinzu = string;
    }

	/**
     * 研究支援者雇用経費を取得
	 * @return 研究支援者雇用経費
	 */
	public String getKoyohi() {
		return koyohi;
	}

    /**
     * 研究支援者雇用経費を設定
     * @param string 研究支援者雇用経費
     */
    public void setKoyohi(String string) {
        koyohi = string;
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
     * 事業年度（西暦下２ケタ）を取得
	 * @return 事業年度（西暦下２ケタ）
	 */
	public String getNendoSeireki() {
		return nendoSeireki;
	}

    /**
     * 事業年度（西暦下２ケタ）を設定
     * @param string 事業年度（西暦下２ケタ）
     */
    public void setNendoSeireki(String string) {
        nendoSeireki = string;
    }

	/**
     * PDFの格納パスを取得
	 * @return PDFの格納パス
	 */
	public String getPdfPath() {
		return pdfPath;
	}

    /**
     * PDFの格納パスを設定
     * @param string PDFの格納パス
     */
    public void setPdfPath(String string) {
        pdfPath = string;
    }

	/**
     * 再申請フラグを取得
	 * @return 再申請フラグ
	 */
	public String getSaishinseiFlg() {
		return saishinseiFlg;
	}

    /**
     * 再申請フラグを設定
     * @param string 再申請フラグ
     */
    public void setSaishinseiFlg(String string) {
        saishinseiFlg = string;
    }

	/**
     * 申請書作成日を取得
	 * @return 申請書作成日
	 */
	public Date getSakuseiDate() {
		return sakuseiDate;
	}

    /**
     * 申請書作成日を設定
     * @param date 申請書作成日
     */
    public void setSakuseiDate(Date date) {
        sakuseiDate = date;
    }

	/**
     * １次審査備考を取得
	 * @return １次審査備考
	 */
	public String getShinsa1Biko() {
		return shinsa1Biko;
	}

    /**
     * １次審査備考を設定
     * @param string １次審査備考
     */
    public void setShinsa1Biko(String string) {
        shinsa1Biko = string;
    }

	/**
     * 業務担当者記入欄を取得
	 * @return 業務担当者記入欄
	 */
	public String getShinsa2Biko() {
		return shinsa2Biko;
	}

    /**
     * 業務担当者記入欄を設定
     * @param string 業務担当者記入欄
     */
    public void setShinsa2Biko(String string) {
        shinsa2Biko = string;
    }

	/**
     * 申請の有無を取得
	 * @return 申請の有無
	 */
	public String getShinseiFlg() {
		return shinseiFlg;
	}

    /**
     * 申請の有無を設定
     * @param string 申請の有無
     */
    public void setShinseiFlg(String string) {
        shinseiFlg = string;
    }

	/**
     * 研究計画最終年度前年度の応募を取得
	 * @return 研究計画最終年度前年度の応募
	 */
	public String getShinseiFlgNo() {
		return shinseiFlgNo;
	}

    /**
     * 研究計画最終年度前年度の応募を設定
     * @param string 研究計画最終年度前年度の応募
     */
    public void setShinseiFlgNo(String string) {
        shinseiFlgNo = string;
    }

	/**
     * 新規継続区分を取得
	 * @return 新規継続区分
	 */
	public String getShinseiKubun() {
		return shinseiKubun;
	}

    /**
     * 新規継続区分を設定
     * @param string 新規継続区分
     */
    public void setShinseiKubun(String string) {
        shinseiKubun = string;
    }

	/**
     * 申請者IDを取得
	 * @return 申請者ID
	 */
	public String getShinseishaId() {
		return shinseishaId;
	}

    /**
     * 申請者IDを設定
     * @param string 申請者ID
     */
    public void setShinseishaId(String string) {
        shinseishaId = string;
    }

	/**
     * 初年度経費（学振入力）を取得
	 * @return 初年度経費（学振入力）
	 */
	public String getShonenKehi() {
		return shonenKehi;
	}

    /**
     * 初年度経費（学振入力）を設定
     * @param string 初年度経費（学振入力）
     */
    public void setShonenKehi(String string) {
        shonenKehi = string;
    }

	/**
     * 所属期間承認日を取得
	 * @return 所属期間承認日
	 */
	public Date getShoninDate() {
		return shoninDate;
	}

    /**
     * 所属期間承認日を設定
     * @param date 所属期間承認日
     */
    public void setShoninDate(Date date) {
        shoninDate = date;
    }

	/**
     * 研究組織の形態を取得
	 * @return 研究組織の形態
	 */
	public String getSoshikiKeitai() {
		return soshikiKeitai;
	}

	/**
     * 研究組織の形態番号を取得
	 * @return 研究組織の形態番号
	 */
	public String getSoshikiKeitaiNo() {
		return soshikiKeitaiNo;
	}

    /**
     * 研究組織の形態番号を設定
     * @param string 研究組織の形態番号
     */
    public void setSoshikiKeitaiNo(String string) {
        soshikiKeitaiNo = string;
    }

	/**
     * 総経費（学振入力）を取得
	 * @return 総経費（学振入力）
	 */
	public String getSouKehi() {
		return souKehi;
	}

    /**
     * 総経費（学振入力）を設定
     * @param string 総経費（学振入力）
     */
    public void setSouKehi(String string) {
        souKehi = string;
    }

	/**
     * 推薦書の格納パスを取得
	 * @return 推薦書の格納パス
	 */
	public String getSuisenshoPath() {
		return suisenshoPath;
	}

    /**
     * 推薦書の格納パスを設定
     * @param string 推薦書の格納パス
     */
    public void setSuisenshoPath(String string) {
        suisenshoPath = string;
    }

	/**
     * 他機関の分担者数を取得
	 * @return 他機関の分担者数
	 */
	public String getTakikanNinzu() {
		return takikanNinzu;
	}

    /**
     * 他機関の分担者数を設定
     * @param string 他機関の分担者数
     */
    public void setTakikanNinzu(String string) {
        takikanNinzu = string;
    }

	/**
     * 添付ファイル情報配列を取得
	 * @return 添付ファイル情報配列
	 */
	public TenpuFileInfo[] getTenpuFileInfos() {
		return tenpuFileInfos;
	}

    /**
     * 添付ファイル情報配列を設定
     * @param infos 添付ファイル情報配列
     */
    public void setTenpuFileInfos(TenpuFileInfo[] infos) {
        tenpuFileInfos = infos;
    }

	/**
     * 申請番号を取得
	 * @return 申請番号
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

    /**
     * 申請番号を設定
     * @param string 申請番号
     */
    public void setUketukeNo(String string) {
        uketukeNo = string;
    }

	/**
     * XMLの格納パスを取得
	 * @return XMLの格納パス
	 */
	public String getXmlPath() {
		return xmlPath;
	}

    /**
     * XMLの格納パスを設定
     * @param string XMLの格納パス
     */
    public void setXmlPath(String string) {
        xmlPath = string;
    }

	/**
     * 研究経費情報を設定
	 * @param info 研究経費情報
	 */
	public void setKenkyuKeihiSoukeiInfo(KenkyuKeihiSoukeiInfo info) {
		kenkyuKeihiSoukeiInfo = info;
	}

	/**
     * 研究組織の形態を設定
	 * @param string 研究組織の形態
	 */
	public void setSoshikiKeitai(String string) {
		soshikiKeitai = string;
	}

	/**
     * 研究組織情報（研究代表者及び分担者リスト）を取得
	 * @return 研究組織情報（研究代表者及び分担者リスト）
	 */
	public List getKenkyuSoshikiInfoList() {
		return kenkyuSoshikiInfoList;
	}

	/**
     * 研究組織情報（研究代表者及び分担者リスト）を設定
	 * @param list 研究組織情報（研究代表者及び分担者リスト）
	 */
	public void setKenkyuSoshikiInfoList(List list) {
		kenkyuSoshikiInfoList = list;
	}

	/**
     * １次審査結果（点数順）を取得
	 * @return １次審査結果（点数順）
	 */
	public String getKekka1TenSorted() {
		return kekka1TenSorted;
	}

	/**
     * １次審査結果（点数順）を設定
	 * @param string １次審査結果（点数順）
	 */
	public void setKekka1TenSorted(String string) {
		kekka1TenSorted = string;
	}

	/**
     * 海外分野コードを取得
	 * @return 海外分野コード
	 */
	public String getKaigaibunyaCd() {
		return kaigaibunyaCd;
	}

    /**
     * 海外分野コードを設定
     * @param string 海外分野コード
     */
    public void setKaigaibunyaCd(String string) {
        kaigaibunyaCd = string;
    }

	/**
     * 海外分野名称を取得
	 * @return 海外分野名称
	 */
	public String getKaigaibunyaName() {
		return kaigaibunyaName;
	}

    /**
     * 海外分野名称を設定
     * @param string 海外分野名称
     */
    public void setKaigaibunyaName(String string) {
        kaigaibunyaName = string;
    }

	/**
     * 海外分野名称略称を取得
	 * @return 海外分野名称略称
	 */
	public String getKaigaibunyaNameRyaku() {
		return kaigaibunyaNameRyaku;
	}

	/**
     * 海外分野名称略称を設定
	 * @param string 海外分野名称略称
	 */
	public void setKaigaibunyaNameRyaku(String string) {
		kaigaibunyaNameRyaku = string;
	}

	//2005/03/30 追加 ----------------------------ここから
	//理由 研究組織表に研究協力者が追加されたため
	/**
     * 研究協力者数を取得
	 * @return Returns 研究協力者数
	 */
	public String getKyoryokushaNinzu() {
		return kyoryokushaNinzu;
	}

	/**
     * 研究協力者数を設定
	 * @param kyoryokushaNinzu 研究協力者数
	 */
	public void setKyoryokushaNinzu(String kyoryokushaNinzu) {
		this.kyoryokushaNinzu = kyoryokushaNinzu;
	}

	/**
     * 研究者数(カウント用)を取得
	 * @return Returns 研究者数(カウント用)
	 */
	public int getKenkyuNinzuInt() {
		return kenkyuNinzuInt;
	}

	/**
     * 研究者数(カウント用)を設定
	 * @param kenkyuNinzuInt 研究者数(カウント用)
	 */
	public void setKenkyuNinzuInt(int kenkyuNinzuInt) {
		this.kenkyuNinzuInt = kenkyuNinzuInt;
	}

	/**
     * 研究協力者数(カウント用)を取得
	 * @return Returns 研究協力者数(カウント用)
	 */
	public int getKyoryokushaNinzuInt() {
		return kyoryokushaNinzuInt;
	}

	/**
     * 研究協力者数(カウント用)を設定
	 * @param kyoryokushaNinzuInt 研究協力者数(カウント用)
	 */
	public void setKyoryokushaNinzuInt(int kyoryokushaNinzuInt) {
		this.kyoryokushaNinzuInt = kyoryokushaNinzuInt;
	}
	//2005/03/30 追加 ----------------------------ここまで

	/**
     * 誕生日を取得
	 * @return Returns 誕生日
	 */
	public Date getBirthDay() {
		return birthDay;
	}

	/**
     * 誕生日を設定
	 * @param birthDay 誕生日
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

// 210050526 Start 特定領域用の項目追加のためプロパティを作成

/* 変数 */
    /**
     * 計画研究・公募研究・終了研究領域区分を取得
     * @return　計画研究・公募研究・終了研究領域区分
     */
	public String getKenkyuKubun() {
        return kenkyuKubun;
    }

    /**
     * 計画研究・公募研究・終了研究領域区分を設定
     * @param kenkyuKubun 計画研究・公募研究・終了研究領域区分
     */
	public void setKenkyuKubun(String kenkyuKubun){
        this.kenkyuKubun = kenkyuKubun;
    }

    /**
     * 大幅な変更を伴う研究課題を取得
     * @return　大幅な変更を伴う研究課題
     */
	public String getChangeFlg() {
        return changeFlg;
    }

    /**
     * 大幅な変更を伴う研究課題を設定
     * @param changeFlg 大幅な変更を伴う研究課題
     */
	public void setChangeFlg(String changeFlg){
        this.changeFlg = changeFlg;
    }

    /**
     * 領域番号を取得
     * @return 領域番号
     */
	public String getRyouikiNo(){
        return ryouikiNo;
    }

    /**
     * 領域番号を設定
     * @param string 領域番号
     */
	public void setRyouikiNo(String string) {
        ryouikiNo = string;
    }

    /**
     * 領域略称名を取得
     * @return 領域略称名
     */
	public String getRyouikiRyakuName() {
        return ryouikiRyakuName;
    }

    /**
     * 領域略称名を設定
     * @param string 領域略称名
     */
	public void setRyouikiRyakuName(String string){
        ryouikiRyakuName = string;
    }

    /**
     * 研究項目番号を取得
     * @return 研究項目番号
     */
	public String getRyouikiKoumokuNo() {
        return ryouikiKoumokuNo;
    }

    /**
     * 研究項目番号を設定
     * @param string 研究項目番号
     */
	public void setRyouikiKoumokuNo(String string){
        ryouikiKoumokuNo = string;
    }

    /**
     * 調整班を取得
     * @return 調整班
     */
	public String getChouseiFlg() {
        return chouseiFlg;
    }

    /**
     * 調整班を設定
     * @param string 調整班
     */
	public void setChouseiFlg(String string){
        chouseiFlg = string;
    }
// Horikoshi End

//	 20050725 キーワード追加のため
    /**
     * 記号を取得
     * @return 記号
     */
	public String getKigou() {
		return kigou;
	}

    /**
     * 記号を設定
     * @param kigou 記号
     */
	public void setKigou(String kigou) {
		this.kigou = kigou;
	}

    /**
     * キーワード(名称)を取得
     * @return キーワード(名称)
     */
	public String getKeyName() {
		return keyName;
	}

    /**
     * キーワード(名称)を設定
     * @param keyName キーワード(名称)
     */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

    /**
     * 細目表以外のキーワード(名称)を取得
     * @return 細目表以外のキーワード(名称)
     */
	public String getKeyOtherName() {
		return keyOtherName;
	}

    /**
     * 細目表以外のキーワード(名称)を設定
     * @param keyOtherName 細目表以外のキーワード(名称)
     */
	public void setKeyOtherName(String keyOtherName) {
		this.keyOtherName = keyOtherName;
	}
// Horikoshi

	/**
     * 受理整理番号を取得
	 * @return 整理番号
	 */
	public String getSeiriNo(){
		return seiriNo;
	}
	
	/**
     * 受理整理番号を設定
	 * @param str 整理番号
	 */
	public void setSeiriNo(String str){
		seiriNo = str;
	}

	/**
     * 研究者名簿締切日を取得
	 * @return Returns 研究者名簿締切日
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

	/**
     * 研究者名簿締切日(和暦)を取得
	 * @return Returns 研究者名簿締切日(和暦)
	 */
	public String getMeiboDateWareki() {
		return meiboDateWareki;
	}

	/**
     * 研究者名簿締切日(和暦)を設定
	 * @param meiboDateWareki 研究者名簿締切日(和暦)
	 */
	public void setMeiboDateWareki(String meiboDateWareki) {
		this.meiboDateWareki = meiboDateWareki;
	}

	/**
     * 育休等終了日を取得
	 * @return Returns 育休等終了日
	 */
	public Date getIkukyuEndDate() {
		return ikukyuEndDate;
	}

	/**
     * 育休等終了日を設定
	 * @param ikukyuEndDate 育休等終了日
	 */
	public void setIkukyuEndDate(Date ikukyuEndDate) {
		this.ikukyuEndDate = ikukyuEndDate;
	}

	/**
     * 育休等開始日を取得
	 * @return Returns 育休等開始日
	 */
	public Date getIkukyuStartDate() {
		return ikukyuStartDate;
	}

	/**
     * 育休等開始日を設定
	 * @param ikukyuStartDate 育休等開始日
	 */
	public void setIkukyuStartDate(Date ikukyuStartDate) {
		this.ikukyuStartDate = ikukyuStartDate;
	}

	/**
     * 勤務時間数を取得
	 * @return Returns 勤務時間数
	 */
	public String getKinmuHour() {
		return kinmuHour;
	}

	/**
     * 勤務時間数を設定
	 * @param kinmuHour 勤務時間数
	 */
	public void setKinmuHour(String kinmuHour) {
		this.kinmuHour = kinmuHour;
	}

	/**
     * 特別研究員奨励費内約額を取得
	 * @return Returns 特別研究員奨励費内約額
	 */
	public String getNaiyakugaku() {
		return naiyakugaku;
	}

	/**
     * 特別研究員奨励費内約額を設定
	 * @param naiyakugaku 特別研究員奨励費内約額
	 */
	public void setNaiyakugaku(String naiyakugaku) {
		this.naiyakugaku = naiyakugaku;
	}

	/**
     * 申請資格フラグを取得
	 * @return Returns 申請資格フラグ
	 */
	public String getOuboShikaku() {
		return ouboShikaku;
	}

	/**
     * 申請資格フラグを設定
	 * @param ouboShikaku 申請資格フラグ
	 */
	public void setOuboShikaku(String ouboShikaku) {
		this.ouboShikaku = ouboShikaku;
	}

	/**
     * 採用年月日を取得
	 * @return Returns 採用年月日
	 */
	public Date getSaiyoDate() {
		return saiyoDate;
	}

	/**
     * 採用年月日を設定
	 * @param saiyoDate 採用年月日
	 */
	public void setSaiyoDate(Date saiyoDate) {
		this.saiyoDate = saiyoDate;
	}

	/**
     * 資格取得年月日を取得
	 * @return Returns 資格取得年月日
	 */
	public Date getSikakuDate() {
		return sikakuDate;
	}

	/**
     * 資格取得年月日を設定
	 * @param sikakuDate 資格取得年月日
	 */
	public void setSikakuDate(Date sikakuDate) {
		this.sikakuDate = sikakuDate;
	}

	/**
     * 資格取得前機関名を取得
	 * @return Returns 資格取得前機関名
	 */
	public String getSyuTokumaeKikan() {
		return syuTokumaeKikan;
	}

	/**
     * 資格取得前機関名を設定
	 * @param syuTokumaeKikan 資格取得前機関名
	 */
	public void setSyuTokumaeKikan(String syuTokumaeKikan) {
		this.syuTokumaeKikan = syuTokumaeKikan;
	}

	/**
     * 学振受付期間（終了）を取得
	 * @return Returns 学振受付期間（終了）
	 */
	public Date getUketukekikanEnd() {
		return uketukekikanEnd;
	}

	/**
     * 学振受付期間（終了）を設定
	 * @param uketukekikanEnd 学振受付期間（終了）
	 */
	public void setUketukekikanEnd(Date uketukekikanEnd) {
		this.uketukekikanEnd = uketukekikanEnd;
	}

	/**
     * 審査希望領域コードを取得
	 * @return Returns 審査希望領域コード
	 */
	public String getShinsaRyoikiCd() {
		return shinsaRyoikiCd;
	}

	/**
     * 審査希望領域コードを設定
	 * @param shinsaRyoikiCd 審査希望領域コード
	 */
	public void setShinsaRyoikiCd(String shinsaRyoikiCd) {
		this.shinsaRyoikiCd = shinsaRyoikiCd;
	}

	/**
     * 審査希望領域名を取得
	 * @return Returns 審査希望領域名
	 */
	public String getShinsaRyoikiName() {
		return shinsaRyoikiName;
	}

	/**
     * 審査希望領域名を設定
	 * @param shinsaryoikiName 審査希望領域名
	 */
	public void setShinsaRyoikiName(String shinsaryoikiName) {
		this.shinsaRyoikiName = shinsaryoikiName;
	}

    /**
     * 領域代表者確定日を取得
     * @return Returns 領域代表者確定日
     */
    public Date getRyoikiKakuteiDate() {
        return ryoikiKakuteiDate;
    }

    /**
     * 領域代表者確定日を設定
     * @param ryoikiKakuteiDate 領域代表者確定日
     */
    public void setRyoikiKakuteiDate(Date ryoikiKakuteiDate) {
        this.ryoikiKakuteiDate = ryoikiKakuteiDate;
    }

    /**
     * 申請状況ID配列を取得
     * @return Returns 申請状況ID配列
     */
    public String[] getJokyoIds() {
        return jokyoIds;
    }

    /**
     * 申請状況ID配列を設定
     * @param jokyoIds 申請状況ID配列
     */
    public void setJokyoIds(String[] jokyoIds) {
        this.jokyoIds = jokyoIds;
    }

    /**
     * 所属期間承認日(mark)を取得
     * @return Returns 所属期間承認日(mark)
     */
    public String getShoninDateMark() {
        return shoninDateMark;
    }

    /**
     * 所属期間承認日(mark)を設定
     * @param shoninDateMark 所属期間承認日(mark)
     */
    public void setShoninDateMark(String shoninDateMark) {
        this.shoninDateMark = shoninDateMark;
    }

    /**
     * 学振受理日Flgを取得
     * @return Returns 学振受理日Flg
     */
    public String getJyuriDateFlg() {
        return jyuriDateFlg;
    }

    /**
     * 学振受理日Flgを設定
     * @param jyuriDateFlg 学振受理日Flg
     */
    public void setJyuriDateFlg(String jyuriDateFlg) {
        this.jyuriDateFlg = jyuriDateFlg;
    }

    /**
     * 領域代表者確定日Flgを取得
     * @return Returns 領域代表者確定日Flg
     */
    public String getRyoikiKakuteiDateFlg() {
        return ryoikiKakuteiDateFlg;
    }

    /**
     * 領域代表者確定日Flgを設定
     * @param ryoikiKakuteiDateFlg 領域代表者確定日Flg
     */
    public void setRyoikiKakuteiDateFlg(String ryoikiKakuteiDateFlg) {
        this.ryoikiKakuteiDateFlg = ryoikiKakuteiDateFlg;
    }

    /**
     * URLを取得
     * @return Returns URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * URLを設定
     * @param url URL
     */
    public void setUrl(String url) {
        this.url = url;
    }
    

    /**
     * 特別研究員奨励費課題番号-年度設定
     * @param  shureihiNo 設定値
     */
    public void setShoreihiNoNendo(String shoreihiNoNendo) {
        this.shoreihiNoNendo = shoreihiNoNendo;
    }
    
    /**
     * 特別研究員奨励費課題番号-年度取得
     * @return 特別研究員奨励費課題番号-年度の値
     */
    public String getShoreihiNoNendo() {
        return shoreihiNoNendo;
    }
    

    /**
     * 特別研究員奨励費課題番号-整理番号設定
     * @param  shoreihiNoSeiri 設定値
     */
    public void setShoreihiNoSeiri(String shoreihiNoSeiri) {
        this.shoreihiNoSeiri = shoreihiNoSeiri;
    }
    
    /**
     * 特別研究員奨励費課題番号-整理番号取得
     * @return 特別研究員奨励費課題番号-整理番号の値
     */
    public String getShoreihiNoSeiri() {
        return shoreihiNoSeiri;
    }
//  <!-- ADD　START 2007/07/06 BIS 張楠 -->
//  H19完全電子化及び制度改正.
//  入力した研究組織にエラーがある場合、「応募情報入力」画面でエラーメッセージを表示し、「研究組織表」画面でエラーになる項目の背景色を変更する。	
	/**
	 * @return Returns the errorsMap.
	 */
	public HashMap getErrorsMap() {
		return errorsMap;
	}

	/**
	 * @param errorsMap The errorsMap to set.
	 */
	public void setErrorsMap(HashMap errorsMap) {
		this.errorsMap = errorsMap;
	}
//	<!-- ADD　END　 2007/07/06 BIS 張楠 -->	
}