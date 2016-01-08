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
 * 部局情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: BukyokuInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class BukyokuInfo extends BukyokuPk {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 6181490072911050763L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

//	/** 索引 */
//	private String indx;

	/** 部科名称 */
	private String bukaName;

	/** 部科略称 */
	private String bukaRyakusyo;

	/** カテゴリ */
	private String bukaKategori;
	
	/** ソート番号 */
	private String sortNo;
	
	/** 備考 */
	private String biko;

	//...など

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public BukyokuInfo() {
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
	public String getBukaName() {
		return bukaName;
	}

	/**
	 * @return
	 */
	public String getBukaRyakusyo() {
		return bukaRyakusyo;
	}

//	/**
//	 * @return
//	 */
//	public String getIndx() {
//		return indx;
//	}

	/**
	 * @param string
	 */
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @param string
	 */
	public void setBukaName(String string) {
		bukaName = string;
	}

	/**
	 * @param string
	 */
	public void setBukaRyakusyo(String string) {
		bukaRyakusyo = string;
	}

//	/**
//	 * @param string
//	 */
//	public void setIndx(String string) {
//		indx = string;
//	}

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

	/**
	 * @return
	 */
	public String getSortNo() {
		return sortNo;
	}

	/**
	 * @param string
	 */
	public void setSortNo(String string) {
		sortNo = string;
	}

}
