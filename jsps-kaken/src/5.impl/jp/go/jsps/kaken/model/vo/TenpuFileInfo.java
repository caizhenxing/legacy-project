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
 * 添付ファイル情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: TenpuFileInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class TenpuFileInfo extends TenpuFilePk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** 事業ID */
	private String jigyoId;
	
	/** 格納パス */
	private String tenpuPath;
	
	/** PDFファイル格納パス */
	private String pdfPath;
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public TenpuFileInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getTenpuPath() {
		return tenpuPath;
	}

	/**
	 * @param string
	 */
	public void setTenpuPath(String string) {
		tenpuPath = string;
	}

	/**
	 * @return
	 */
	public String getPdfPath() {
		return pdfPath;
	}

	/**
	 * @param string
	 */
	public void setPdfPath(String string) {
		pdfPath = string;
	}

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

}
