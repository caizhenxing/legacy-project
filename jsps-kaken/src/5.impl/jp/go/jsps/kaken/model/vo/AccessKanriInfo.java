/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/07/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
 
package jp.go.jsps.kaken.model.vo;

/**
 * アクセス制御情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: AccessKanriInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class AccessKanriInfo extends AccessKanriPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** 事業区分 */
	private String jigyoKubun  = null;
	
	/** 備考 */
	private String biko        = null;
	
	
	//...

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public AccessKanriInfo() {
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
	public String getJigyoKubun() {
		return jigyoKubun;
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
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}
	
	

}
