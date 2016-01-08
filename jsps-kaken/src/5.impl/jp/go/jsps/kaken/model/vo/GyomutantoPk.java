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
 * 業務担当者情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: GyomutantoPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class GyomutantoPk extends ValueObject{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 業務担当者ID */
	private String gyomutantoId;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public GyomutantoPk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getGyomutantoId() {
		return gyomutantoId;
	}

	/**
	 * @param string
	 */
	public void setGyomutantoId(String string) {
		gyomutantoId = string;
	}

}
