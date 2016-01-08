/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */

package jp.go.jsps.kaken.model.vo;

import java.util.Date;

/**
 * 簡易申請データ情報クラス。
 * 
 * ID RCSfile="$RCSfile: SimpleShinseiDataInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class SimpleShinseiDataInfo extends ShinseiDataPk{

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
	
	/** 研究課題名 */
	private String kadaiName;
	
	/** 学振受付期限（終了） */
	private Date uketukeKikanEnd;
    
    //add start ly 2006/07/19
    /** 領域代表者確定締切日（終了） */
    private Date ryoikiKakuteikikanEnd;
    //add end ly 2006/07/19
	
	/** 申請書作成日時 */
	private Date sakuseiDate;
	
	/** 所属機関承認日 */
	private Date shoninDate;
	
	/** 受理結果 */
	private String juriKekka;
	
	/** 受理結果備考 */
	private String juriBiko;
	
	/** 受理整理番号 */
	private String seiriNo;

	/** 1次審査結果(ABC) */
	private String kekka1Abc;
	
	/** 1次審査結果(点数) */
	private String kekka1Ten;
	
	/** １次審査結果（点数順） */
	private String kekka1TenSorted;	
	
	/** 2次審査結果 */
	private String kekka2;
	
	/** 申請状況（コード） */
	private String jokyoId;
	
	/** 申請状況（文字列） */
	private String jokyoName;
	
	/** 再申請フラグ */
	private String saishinseiFlg;
	
	/** 申請者ID */
	private String shinseishaId;
	
	/** 申請者名（姓） */
	private String shinseishaNameSei;
	
	/** 申請者名（名）*/
	private String shinseishaNameMei;
	
	/** 申請者研究者番号 */
	private String kenkyuNo;
	
	/** 所属機関コード */
	private String shozokuCd;
	
	/** 所属機関名 */
	private String shozokuName;
	
	/** 所属機関名（略称） */
	private String shozokuNameRyaku;
	
	/** 部局名 */
	private String bukyokuName;
	
	/** 部局名（略称） */
	private String bukyokuNameRyaku;
	
	/** 職名 */
	private String shokushuNameKanji;
	
	/** 職名（略称） */
	private String shokushuNameRyaku;
	
	/** 推薦書パス */
	private String suisenshoPath;

// 20050707
	/** 2次審査備考 */
	private String shinsa2Biko;
	/** 備考(受理結果備考と二次審査備考兼用) */
	private String biko;
// Horikoshi

//2006/06/16 苗　追加ここから
    /** 研究項目番号 */
    private String komokuNo;
    
    /** 調整班 */
    private String choseihan;
    
    /** 版 */
    private String edition;
//2006/06/16　苗　追加ここまで    
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public SimpleShinseiDataInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	
	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
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
	public String getJokyoId() {
		return jokyoId;
	}

	/**
	 * @return
	 */
	public String getJokyoName() {
		return jokyoName;
	}

	/**
	 * @return
	 */
	public String getKadaiName() {
		return kadaiName;
	}

	/**
	 * @return
	 */
	public Date getSakuseiDate() {
		return sakuseiDate;
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
	public String getShinseishaNameMei() {
		return shinseishaNameMei;
	}

	/**
	 * @return
	 */
	public String getShinseishaNameSei() {
		return shinseishaNameSei;
	}

	/**
	 * @return
	 */
	public Date getShoninDate() {
		return shoninDate;
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
	public String getShozokuNameRyaku() {
		return shozokuNameRyaku;
	}

	/**
	 * @return
	 */
	public Date getUketukeKikanEnd() {
		return uketukeKikanEnd;
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
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoName(String string) {
		jigyoName = string;
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
	public void setJokyoName(String string) {
		jokyoName = string;
	}

	/**
	 * @param string
	 */
	public void setKadaiName(String string) {
		kadaiName = string;
	}

	/**
	 * @param date
	 */
	public void setSakuseiDate(Date date) {
		sakuseiDate = date;
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
	public void setShinseishaNameMei(String string) {
		shinseishaNameMei = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameSei(String string) {
		shinseishaNameSei = string;
	}

	/**
	 * @param date
	 */
	public void setShoninDate(Date date) {
		shoninDate = date;
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
	public void setShozokuNameRyaku(String string) {
		shozokuNameRyaku = string;
	}

	/**
	 * @param date
	 */
	public void setUketukeKikanEnd(Date date) {
		uketukeKikanEnd = date;
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
	public String getKekka1Abc() {
		return kekka1Abc;
	}

	/**
	 * @return
	 */
	public String getKekka1Ten() {
		return kekka1Ten;
	}

	/**
	 * @return
	 */
	public String getKekka2() {
		return kekka2;
	}

	/**
	 * @param string
	 */
	public void setKekka1Abc(String string) {
		kekka1Abc = string;
	}

	/**
	 * @param string
	 */
	public void setKekka1Ten(String string) {
		kekka1Ten = string;
	}

	/**
	 * @param string
	 */
	public void setKekka2(String string) {
		kekka2 = string;
	}

	/**
	 * @return
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
	 * @param string
	 */
	public void setBukyokuName(String string) {
		bukyokuName = string;
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
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
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
	public void setShokushuNameKanji(String string) {
		shokushuNameKanji = string;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	/**
	 * @return
	 */
	public String getJuriBiko() {
		return juriBiko;
	}

	/**
	 * @return
	 */
	public String getJuriKekka() {
		return juriKekka;
	}

	/**
	 * @param string
	 */
	public void setJuriBiko(String string) {
		juriBiko = string;
	}

	/**
	 * @param string
	 */
	public void setJuriKekka(String string) {
		juriKekka = string;
	}

	/**
	 * @return
	 */
	public String getSaishinseiFlg() {
		return saishinseiFlg;
	}

	/**
	 * @param string
	 */
	public void setSaishinseiFlg(String string) {
		saishinseiFlg = string;
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
	public String getKenkyuNo() {
		return kenkyuNo;
	}

	/**
	 * @return
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
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
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameRyaku(String string) {
		shokushuNameRyaku = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @return
	 */
	public String getSuisenshoPath() {
		return suisenshoPath;
	}

	/**
	 * @param string
	 */
	public void setSuisenshoPath(String string) {
		suisenshoPath = string;
	}

	/**
	 * @return
	 */
	public String getKekka1TenSorted() {
		return kekka1TenSorted;
	}

	/**
	 * @param string
	 */
	public void setKekka1TenSorted(String string) {
		kekka1TenSorted = string;
	}

// 20050707
	/**
	 * @return
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * @param string
	 */
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @return
	 */
	public String getShinsa2Biko() {
		return shinsa2Biko;
	}

	/**
	 * @param string
	 */
	public void setShinsa2Biko(String string) {
		shinsa2Biko = string;
	}
// Horikoshi
	
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
     * 研究項目番号を取得する
     * 
     * @return 研究項目番号を返す
     */
    public String getKomokuNo( )
    {
        return komokuNo;
    }

    /**
     * 研究項目番号を設定する
     * 
     * @param　研究項目番号をセットする
     */
    public void setKomokuNo(String komokuNo)
    {
        this.komokuNo = komokuNo;
    }

    /**
     * 調整班を取得する
     * 
     * @return 調整班を返す
     */
    public String getChoseihan( )
    {
        return choseihan;
    }

    /**
     * 調整班を設定する
     * 
     * @param 調整班をセットする
     */
    public void setChoseihan(String choseihan)
    {
        this.choseihan = choseihan;
    }

    /**
     * 版を取得する
     * 
     * @return 版を返す
     */
    public String getEdition( )
    {
        return edition;
    }

    /**
     * 版を設定する
     * 
     * @param 版をセットする
     */
    public void setEdition(String edition)
    {
        this.edition = edition;
    }

    /**
     * @return Returns the ryoikiKakuteikikanEnd.
     */
    public Date getRyoikiKakuteikikanEnd() {
        return ryoikiKakuteikikanEnd;
    }

    /**
     * @param ryoikiKakuteikikanEnd The ryoikiKakuteikikanEnd to set.
     */
    public void setRyoikiKakuteikikanEnd(Date ryoikiKakuteikikanEnd) {
        this.ryoikiKakuteikikanEnd = ryoikiKakuteikikanEnd;
    }
    
    
}
