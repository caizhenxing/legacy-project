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
 * �Y�t�t�@�C������ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: TenpuFileInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class TenpuFileInfo extends TenpuFilePk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** ����ID */
	private String jigyoId;
	
	/** �i�[�p�X */
	private String tenpuPath;
	
	/** PDF�t�@�C���i�[�p�X */
	private String pdfPath;
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
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
