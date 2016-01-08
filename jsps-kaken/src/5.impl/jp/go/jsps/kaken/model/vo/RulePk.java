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
 * 各ID・パスワードの発行ルール情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: RulePk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class RulePk extends ValueObject{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -5519567648071771748L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 対象者ID */
	private String taishoId;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public RulePk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getTaishoId() {
		return taishoId;
	}

	/**
	 * @param string
	 */
	public void setTaishoId(String string) {
		taishoId = string;
	}

}
