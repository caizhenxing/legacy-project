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

package jp.go.jsps.kaken.model.vo.shinsei;

import jp.go.jsps.kaken.model.vo.ShinseiDataPk;

/**
 * 研究代表者及び分担者（研究組織表）の主キー情報保持するクラス。
 * 
 * ID RCSfile="$RCSfile: KenkyuSoshikiKenkyushaPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public class KenkyuSoshikiKenkyushaPk extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** シーケンス番号 */
	private String seqNo;
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KenkyuSoshikiKenkyushaPk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @param string
	 */
	public void setSeqNo(String string) {
		seqNo = string;
	}

}
