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
 * ���J�m�����ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: KokaiKakuteiInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class KokaiKakuteiInfo extends ValueObject{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 7085881573896453074L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �m�肷�鎖�Ə�� */
	private JigyoKanriPk[] jigyoKanriPks;

	/** �p�X���[�h */
	private String passWord;

	/** ���J���ٔԍ� */
	private String kessaiNo;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public KokaiKakuteiInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public JigyoKanriPk[] getJigyoKanriPks() {
		return jigyoKanriPks;
	}

	/**
	 * @return
	 */
	public String getKessaiNo() {
		return kessaiNo;
	}

	/**
	 * @return
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * @param pks
	 */
	public void setJigyoKanriPks(JigyoKanriPk[] pks) {
		jigyoKanriPks = pks;
	}

	/**
	 * @param string
	 */
	public void setKessaiNo(String string) {
		kessaiNo = string;
	}

	/**
	 * @param string
	 */
	public void setPassWord(String string) {
		passWord = string;
	}

}
