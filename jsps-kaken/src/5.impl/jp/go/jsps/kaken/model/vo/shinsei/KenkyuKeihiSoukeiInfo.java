/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : KenkyuKeihiSoukeiInfo.java
 *    Description : 研究経費の総計情報を保持するクラス。
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    V1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo.shinsei;

import jp.go.jsps.kaken.model.vo.ShinseiDataPk;

/**
 * 研究経費の総計情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: KenkyuKeihiSoukeiInfo.java,v $"
 * Revision="$Revision: 1.3 $"
 * Date="$Date: 2007/07/20 01:28:09 $"
 */
public class KenkyuKeihiSoukeiInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
   
	/** 研究経費（5ヵ年分） */
	private KenkyuKeihiInfo[] kenkyuKeihi = makeKenkyuKeihiInfoArray(5);
    
//2006/07/03 苗　追加ここから
    /** 研究経費（6ヵ年分） */
    private KenkyuKeihiInfo[] kenkyuKeihi6 = makeKenkyuKeihiInfoArray(6);
//2006/07/03　苗　追加ここまで    
	
//  ADD START 2007-07-10 BIS 王志安
	/** 内約額 */
	private String naiyakuTotal;
// ADD END 2007-07-10 BIS 王志安
	
    /** 総計-研究経費 */
	private String keihiTotal;
	
	/** 総計-設備備品費 */
	private String bihinhiTotal;
	
	/** 総計-消耗品費 */
	private String shomohinhiTotal;
	
	/** 総計-国内旅費 */
	private String kokunairyohiTotal;
	
	/** 総計-外国旅費 */
	private String gaikokuryohiTotal;
	
	/** 総計-旅費 */
	private String ryohiTotal;
	
	/** 総計-謝金等 */
	private String shakinTotal;
	
	/** 総計-その他 */
	private String sonotaTotal;

// 20050803
    /** 会議費 */
	private String meetingCost;

    /** 印刷費 */
	private String printingCost;
// Horikoshi

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KenkyuKeihiSoukeiInfo() {
		super();
	}
	
	
	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------
	/**
	 * 
	 * @param arrayLength
	 * @return
	 */
	private KenkyuKeihiInfo[] makeKenkyuKeihiInfoArray(int arrayLength){
		KenkyuKeihiInfo[] infoArray = new KenkyuKeihiInfo[arrayLength];
		for(int i=0; i<arrayLength; i++){
			infoArray[i] = new KenkyuKeihiInfo();
		}
		return infoArray;
	}
	
	
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
     * 総計-設備備品費を取得
	 * @return 総計-設備備品費
	 */
	public String getBihinhiTotal() {
		return bihinhiTotal;
	}

	/**
     * 総計-外国旅費を取得
	 * @return 総計-外国旅費
	 */
	public String getGaikokuryohiTotal() {
		return gaikokuryohiTotal;
	}

	/**
     * 総計-研究経費を取得
	 * @return 総計-研究経費
	 */
	public String getKeihiTotal() {
		return keihiTotal;
	}

	/**
     * 研究経費（5ヵ年分）を取得
	 * @return 研究経費（5ヵ年分）
	 */
	public KenkyuKeihiInfo[] getKenkyuKeihi() {
		return kenkyuKeihi;
	}

	/**
     * 総計-国内旅費を取得
	 * @return 総計-国内旅費
	 */
	public String getKokunairyohiTotal() {
		return kokunairyohiTotal;
	}

	/**
     * 総計-謝金等を取得
	 * @return 総計-謝金等
	 */
	public String getShakinTotal() {
		return shakinTotal;
	}

	/**
     * 総計-消耗品費を取得
	 * @return 総計-消耗品費
	 */
	public String getShomohinhiTotal() {
		return shomohinhiTotal;
	}

	/**
     * 総計-その他を取得
	 * @return 総計-その他
	 */
	public String getSonotaTotal() {
		return sonotaTotal;
	}

	/**
     * 総計-設備備品費を設定
	 * @param string 総計-設備備品費
	 */
	public void setBihinhiTotal(String string) {
		bihinhiTotal = string;
	}

	/**
     * 総計-外国旅費を設定
	 * @param string 総計-外国旅費
	 */
	public void setGaikokuryohiTotal(String string) {
		gaikokuryohiTotal = string;
	}

	/**
     * 総計-研究経費を設定
	 * @param string 総計-研究経費
	 */
	public void setKeihiTotal(String string) {
		keihiTotal = string;
	}

	/**
     * 研究経費（5ヵ年分）を設定
	 * @param infos 研究経費（5ヵ年分）
	 */
	public void setKenkyuKeihi(KenkyuKeihiInfo[] infos) {
		kenkyuKeihi = infos;
	}

	/**
     * 総計-国内旅費を設定
	 * @param string 総計-国内旅費
	 */
	public void setKokunairyohiTotal(String string) {
		kokunairyohiTotal = string;
	}

	/**
     * 総計-謝金等を設定
	 * @param string 総計-謝金等
	 */
	public void setShakinTotal(String string) {
		shakinTotal = string;
	}

	/**
     * 総計-消耗品費を設定
	 * @param string 総計-消耗品費
	 */
	public void setShomohinhiTotal(String string) {
		shomohinhiTotal = string;
	}

	/**
     * 総計-その他を設定
	 * @param string 総計-その他
	 */
	public void setSonotaTotal(String string) {
		sonotaTotal = string;
	}

	/**
     * 総計-旅費を取得
	 * @return 総計-旅費
	 */
	public String getRyohiTotal() {
		return ryohiTotal;
	}

	/**
     * 総計-旅費を設定
	 * @param string 総計-旅費
	 */
	public void setRyohiTotal(String string) {
		ryohiTotal = string;
	}

// 20050803
    /**
     * 会議費を取得
     * @return 会議費
     */
	public String getMeetingCost() {
		return meetingCost;
	}

    /**
     * 会議費を設定
     * @param meetingCost 会議費
     */
	public void setMeetingCost(String meetingCost) {
		this.meetingCost = meetingCost;
	}

    /**
     * 印刷費を取得
     * @return 印刷費
     */
	public String getPrintingCost() {
		return printingCost;
	}

    /**
     * 印刷費を設定
     * @param printingCost 印刷費
     */
	public void setPrintingCost(String printingCost) {
		this.printingCost = printingCost;
	}
// Horikoshi

    /**
     * 研究経費（6ヵ年分）を取得
     * @return Returns 研究経費（6ヵ年分）
     */
    public KenkyuKeihiInfo[] getKenkyuKeihi6() {
        return kenkyuKeihi6;
    }

    /**
     * 研究経費（6ヵ年分）を設定
     * @param kenkyuKeihi6 研究経費（6ヵ年分）
     */
    public void setKenkyuKeihi6(KenkyuKeihiInfo[] kenkyuKeihi6) {
        this.kenkyuKeihi6 = kenkyuKeihi6;
    }

//ADD START 2007-07-10 BIS 王志安
	public String getNaiyakuTotal() {
		return naiyakuTotal;
	}


	public void setNaiyakuTotal(String naiyakuTotal) {
		this.naiyakuTotal = naiyakuTotal;
	}
//ADD END 2007-07-10 BIS 王志安
}