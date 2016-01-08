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

package jp.go.jsps.kaken.model.pdf.webdoc;

import jp.go.jsps.kaken.model.vo.ValueObject;

/**
 * iodファイルの項目と出力プロパティ名情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: FieldInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:57 $"
 */
public class FieldInfo extends ValueObject{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 351806220948002289L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** iodに設定している値 */
	private String name;
	
	/** 設定するオブジェクトのプロパティ名 */
	private String value;
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public FieldInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setValue(String string) {
		value = string;
	}

}
