/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/10
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
 package jp.go.jsps.kaken.model.vo;

/**
 * サービス情報クラス。
 * ID RCSfile="$RCSfile: ServiceInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:08:04 $"
 */
public class ServiceInfo {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** サービス名情報。 */
	private String name;
	
	/** サービス実装クラス。*/
	private String type;


	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ServiceInfo() {
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
	public String getType() {
		return type;
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
	public void setType(String string) {
		type = string;
	}

}
