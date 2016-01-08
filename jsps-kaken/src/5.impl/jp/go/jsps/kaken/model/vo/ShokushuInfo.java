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
 * 職種情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShokushuInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class ShokushuInfo extends ShokushuPk {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -5297234819852786759L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 職名称 */
	private String shokushuName;

	/** 職名(略称) */
	private String shokushuNameRyaku;
	
	/** 備考 */
	private String biko;
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShokushuInfo() {
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
	public String getShokushuName() {
		return shokushuName;
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
	public void setShokushuName(String string) {
		shokushuName = string;
	}

	/**
	 * @return
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameRyaku(String string) {
		shokushuNameRyaku = string;
	}

}
