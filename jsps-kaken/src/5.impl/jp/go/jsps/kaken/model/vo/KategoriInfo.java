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
 * カテゴリ情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: KategoriInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class KategoriInfo extends KategoriPk {
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** カテゴリ名称 */
	private String kategoriName;
	
	/** 備考 */
	private String biko;
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KategoriInfo() {
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
	public String getKategoriName() {
		return kategoriName;
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
	public void setKategoriName(String string) {
		kategoriName = string;
	}

}
