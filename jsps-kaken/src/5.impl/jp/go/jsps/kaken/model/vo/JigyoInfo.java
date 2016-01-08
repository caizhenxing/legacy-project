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
 * ���ƃ}�X�^����ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: JigyoInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class JigyoInfo extends JigyoPk{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -5842956445453861622L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���Ɩ��� */
	private String    jigyoName;
	
	/** ���� */
	private String    kuniName;
	
	/** �n�� */
	private String[]  keiNames = new String[10];
	
	/** ���Ƌ敪 */
	private String    jigyoKubun;
	
	/** ���ʁE�Ή����ԃR�[�h�� */
	private String    kuniTaioKahi;
	
	/** ���l */
	private String    biko;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public JigyoInfo() {
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
	 * @return
	 */
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @return
	 */
	public String[] getKeiNames() {
		return keiNames;
	}

	/**
	 * @return
	 */
	public String getKuniName() {
		return kuniName;
	}

	/**
	 * @return
	 */
	public String getKuniTaioKahi() {
		return kuniTaioKahi;
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

	/**
	 * @param string
	 */
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param strings
	 */
	public void setKeiNames(String[] strings) {
		keiNames = strings;
	}

	/**
	 * @param string
	 */
	public void setKuniName(String string) {
		kuniName = string;
	}

	/**
	 * @param string
	 */
	public void setKuniTaioKahi(String string) {
		kuniTaioKahi = string;
	}

}
