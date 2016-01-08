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

/**
 * 分科細目情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: RyouikiInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class RyouikiInfo extends RyouikiInfoPk {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 領域略称名 */
	private String ryoikiName;

	/** 備考 */
	private String biko;
	
	/** 公募フラグ　*/
	private String kobou;
	
	/** 計画研究フラグ　*/
	private String keikaku;
    
    //add start liuyi 206/06/30
    /** 前年度応募フラグ　*/
    private String zennendoOuboFlg;
    
    /** 設定期間（開始年度）　*/
    private String settelKikanKaishi;
    
    /** 設定期間（終了年度）　*/
    private String settelKikanShuryo;
    
    /** 設定期間　*/
    private String settelKikan;
    
    //add end liuyi 2006/06/30

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public RyouikiInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return 領域略称名
	 */
	public String getRyoikiName() {
		return ryoikiName;
	}

	/**
	 * @return 備考
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * @param string 領域略称名
	 */
	public void setRyoikiName(String string) {
		ryoikiName = string;
	}

	/**
	 * @param string 備考
	 */
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @return 公募フラグ
	 */
	public String getKobou(){
		return kobou;
	}
	
	/**
	 * @param str　公募フラグ
	 */
	public void setKobou(String str){
		kobou = str;
	}
	/**
	 * @return keikaku 計画研究フラグ
	 */
	public String getKeikaku()
	{
		return keikaku;
	}
	/**
	 * @param keikaku 計画研究フラグを設定します。
	 */
	public void setKeikaku(String keikaku)
	{
		this.keikaku = keikaku;
	}

    /**
     * @return Returns the settelKikanKaishi.
     */
    public String getSettelKikanKaishi() {
        return settelKikanKaishi;
    }

    /**
     * @param settelKikanKaishi The settelKikanKaishi to set.
     */
    public void setSettelKikanKaishi(String settelKikanKaishi) {
        this.settelKikanKaishi = settelKikanKaishi;
    }

    /**
     * @return Returns the settelKikanShuryo.
     */
    public String getSettelKikanShuryo() {
        return settelKikanShuryo;
    }

    /**
     * @param settelKikanShuryo The settelKikanShuryo to set.
     */
    public void setSettelKikanShuryo(String settelKikanShuryo) {
        this.settelKikanShuryo = settelKikanShuryo;
    }

    /**
     * @return Returns the zennendoOuboFlg.
     */
    public String getZennendoOuboFlg() {
        return zennendoOuboFlg;
    }

    /**
     * @param zennendoOuboFlg The zennendoOuboFlg to set.
     */
    public void setZennendoOuboFlg(String zennendoOuboFlg) {
        this.zennendoOuboFlg = zennendoOuboFlg;
    }

    /**
     * @return Returns the settelKikan.
     */
    public String getSettelKikan() {
        return settelKikan;
    }

    /**
     * @param settelKikan The settelKikan to set.
     */
    public void setSettelKikan(String settelKikan) {
        this.settelKikan = settelKikan;
    }
}
