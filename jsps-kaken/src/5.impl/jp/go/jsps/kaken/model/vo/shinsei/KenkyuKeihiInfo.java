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

package jp.go.jsps.kaken.model.vo.shinsei;

import jp.go.jsps.kaken.model.vo.ShinseiDataPk;


/**
 * 研究経費情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: KenkyuKeihiInfo.java,v $"
 * Revision="$Revision: 1.3 $"
 * Date="$Date: 2007/07/20 01:27:00 $"
 */
public class KenkyuKeihiInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
// ADD START 2007-07-10 BIS 王志安
	/** 内約額 */
	private String naiyaku;
// ADD END 2007-07-10 BIS 王志安
	
	/** 研究経費 */
	private String keihi;
	
	/** 設備備品費 */
	private String bihinhi;
	
	/** 消耗品費 */
	private String shomohinhi;
	
	/** 国内旅費 */
	private String kokunairyohi;
	
	/** 外国旅費 */
	private String gaikokuryohi;
	
	/** 旅費 */
	private String ryohi;
	
	/** 謝金等 */
	private String shakin;
	
	/** その他 */
	private String sonota;
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KenkyuKeihiInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getBihinhi() {
		return bihinhi;
	}

	/**
	 * @return
	 */
	public String getGaikokuryohi() {
		return gaikokuryohi;
	}

	/**
	 * @return
	 */
	public String getKeihi() {
		return keihi;
	}

	/**
	 * @return
	 */
	public String getKokunairyohi() {
		return kokunairyohi;
	}

	/**
	 * @return
	 */
	public String getShakin() {
		return shakin;
	}

	/**
	 * @return
	 */
	public String getShomohinhi() {
		return shomohinhi;
	}

	/**
	 * @return
	 */
	public String getSonota() {
		return sonota;
	}

	/**
	 * @param string
	 */
	public void setBihinhi(String string) {
		bihinhi = string;
	}

	/**
	 * @param string
	 */
	public void setGaikokuryohi(String string) {
		gaikokuryohi = string;
	}

	/**
	 * @param string
	 */
	public void setKeihi(String string) {
		keihi = string;
	}

	/**
	 * @param string
	 */
	public void setKokunairyohi(String string) {
		kokunairyohi = string;
	}

	/**
	 * @param string
	 */
	public void setShakin(String string) {
		shakin = string;
	}

	/**
	 * @param string
	 */
	public void setShomohinhi(String string) {
		shomohinhi = string;
	}

	/**
	 * @param string
	 */
	public void setSonota(String string) {
		sonota = string;
	}

	/**
	 * @return
	 */
	public String getRyohi() {
		return ryohi;
	}

	/**
	 * @param string
	 */
	public void setRyohi(String string) {
		ryohi = string;
	}
//ADD START 2007-07-10 BIS 王志安
	public String getNaiyaku() {
		return naiyaku;
	}

	public void setNaiyaku(String naiyaku) {
		this.naiyaku = naiyaku;
	}
//ADD END 2007-07-10 BIS 王志安
}
