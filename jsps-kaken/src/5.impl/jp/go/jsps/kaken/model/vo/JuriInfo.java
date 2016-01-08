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
 * 受理情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: JuriInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:10 $"
 */
public class JuriInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 1465093784967589186L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 受理結果 */
	private String juriKekka;

	/** 受理結果備考 */
	private String juriBiko;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public JuriInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------


	/**
	 * @return
	 */
	public String getJuriBiko() {
		return juriBiko;
	}

	/**
	 * @return
	 */
	public String getJuriKekka() {
		return juriKekka;
	}

	/**
	 * @param string
	 */
	public void setJuriBiko(String string) {
		juriBiko = string;
	}

	/**
	 * @param string
	 */
	public void setJuriKekka(String string) {
		juriKekka = string;
	}

}
