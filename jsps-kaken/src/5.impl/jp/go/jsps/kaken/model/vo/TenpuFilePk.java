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
 * 添付ファイル情報（キー）を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: TenpuFilePk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class TenpuFilePk extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** シーケンス番号 */
	private String seqTenpu;
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public TenpuFilePk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getSeqTenpu() {
		return seqTenpu;
	}

	/**
	 * @param string
	 */
	public void setSeqTenpu(String string) {
		seqTenpu = string;
	}


}
