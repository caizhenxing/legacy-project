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

import jp.go.jsps.kaken.model.vo.ValueObject;

/**
 * カテゴリ情報（主キー）を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: KategoriPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class KategoriPk extends ValueObject {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 部科カテゴリ */
	private String bukaKategori;

	//...など

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KategoriPk() {
		super();
	}
	
	/**
	 * コンストラクタ。
	 * @param bunkaSaimokuCd
	 */
	public KategoriPk(String bukaKategori){
		this.bukaKategori = bukaKategori;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
	 * @return
	 */
	public String getBukaKategori() {
		return bukaKategori;
	}

	/**
	 * @param string
	 */
	public void setBukaKategori(String string) {
		bukaKategori = string;
	}

}
