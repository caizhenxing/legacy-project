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
 * 所属機関種別マスタ情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: KikanShubetuInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class KikanShubetuInfo extends KikanShubetuPk {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 3313244459473100660L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 機関種別コード*/
	private String shubetuCd;

	/** 種別名称*/
	private String shubetuName;

	/** 備考*/
	private String biko;



	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KikanShubetuInfo() {
		super();
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
	public String getShubetuCd() {
		return shubetuCd;
	}

	/**
	 * @return
	 */
	public String getShubetuName() {
		return shubetuName;
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
	public void setShubetuCd(String string) {
		shubetuCd = string;
	}

	/**
	 * @param string
	 */
	public void setShubetuName(String string) {
		shubetuName = string;
	}

}
