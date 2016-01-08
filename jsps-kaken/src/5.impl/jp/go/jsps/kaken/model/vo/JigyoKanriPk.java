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
 * 事業管理テーブル主キー。
 * 
 * ID RCSfile="$RCSfile: JigyoKanriPk.java,v $"
 * Revision="$Revision: 1.3 $"
 * Date="$Date: 2007/07/24 03:12:17 $"
 */
public class JigyoKanriPk extends ValueObject{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 5417204465495809520L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 事業ID */
	private String jigyoId;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public JigyoKanriPk() {
		super();
	}

	/**
	 * コンストラクタ。
	 * @param　jigyoCd 事業ID
	 */
	public JigyoKanriPk(String jigyoId){
		super();
		this.jigyoId = jigyoId;
	}
	
	//--------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------


	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	
	
	//ADD START 2007/07/23 BIS 趙一非
	private String zennendoOuboFlg;
	private String zennendoOuboNo;
	private String zennendoOuboRyoikiRyaku;
	private String zennendoOuboSettei;
    

	public String getZennendoOuboFlg() {
		return zennendoOuboFlg;
	}

	public void setZennendoOuboFlg(String zennendoOuboFlg) {
		this.zennendoOuboFlg = zennendoOuboFlg;
	}

	public String getZennendoOuboNo() {
		return zennendoOuboNo;
	}

	public void setZennendoOuboNo(String zennendoOuboNo) {
		this.zennendoOuboNo = zennendoOuboNo;
	}

	public String getZennendoOuboRyoikiRyaku() {
		return zennendoOuboRyoikiRyaku;
	}

	public void setZennendoOuboRyoikiRyaku(String zennendoOuboRyoikiRyaku) {
		this.zennendoOuboRyoikiRyaku = zennendoOuboRyoikiRyaku;
	}

	public String getZennendoOuboSettei() {
		return zennendoOuboSettei;
	}

	public void setZennendoOuboSettei(String zennendoOuboSettei) {
		this.zennendoOuboSettei = zennendoOuboSettei;
	}
//	ADD END 2007/07/23 BIS 趙一非
}
