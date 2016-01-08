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
 * 書類管理情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShoruiKanriPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:10 $"
 */
public class ShoruiKanriPk extends ValueObject {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	//static final long serialVersionUID = -3298939536992232366L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 事業ID */
	private String jigyoId;
	
	/** 対象 */
	private String taishoId;
	
	/** システム受付番号 */
	private String systemNo;

	//...など

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShoruiKanriPk() {
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
	public String getSystemNo() {
		return systemNo;
	}

	/**
	 * @return
	 */
	public String getTaishoId() {
		return taishoId;
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
	public void setSystemNo(String string) {
		systemNo = string;
	}

	/**
	 * @param string
	 */
	public void setTaishoId(String string) {
		taishoId = string;
	}

}
