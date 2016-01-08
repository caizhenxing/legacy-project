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
 * �֘A���쌤���ҏ���ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: KanrenBunyaKenkyushaInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public class KanrenBunyaKenkyushaInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** �֘A����̌�����-���� */
	private String kanrenShimei;
	
	/** �֘A����̌�����-�����@�� */
	private String kanrenKikan;
	
	/** �֘A����̌�����-�������� */
	private String kanrenBukyoku;
	
	/** �֘A����̌�����-�E�� */
	private String kanrenShoku;
	
	/** �֘A����̌�����-��啪�� */
	private String kanrenSenmon;
	
	/** �֘A����̌�����-�Ζ���d�b�ԍ� */
	private String kanrenTel;
	
	/** �֘A����̌�����-����d�b�ԍ� */
	private String kanrenJitakuTel;
	
	/** �֘A����̌�����-Email */
	private String kanrenMail;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public KanrenBunyaKenkyushaInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getKanrenBukyoku() {
		return kanrenBukyoku;
	}

	/**
	 * @return
	 */
	public String getKanrenKikan() {
		return kanrenKikan;
	}

	/**
	 * @return
	 */
	public String getKanrenMail() {
		return kanrenMail;
	}

	/**
	 * @return
	 */
	public String getKanrenSenmon() {
		return kanrenSenmon;
	}

	/**
	 * @return
	 */
	public String getKanrenShimei() {
		return kanrenShimei;
	}

	/**
	 * @return
	 */
	public String getKanrenShoku() {
		return kanrenShoku;
	}

	/**
	 * @return
	 */
	public String getKanrenTel() {
		return kanrenTel;
	}

	/**
	 * @param string
	 */
	public void setKanrenBukyoku(String string) {
		kanrenBukyoku = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenKikan(String string) {
		kanrenKikan = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenMail(String string) {
		kanrenMail = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenSenmon(String string) {
		kanrenSenmon = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenShimei(String string) {
		kanrenShimei = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenShoku(String string) {
		kanrenShoku = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenTel(String string) {
		kanrenTel = string;
	}

	/**
	 * @return
	 */
	public String getKanrenJitakuTel() {
		return kanrenJitakuTel;
	}

	/**
	 * @param string
	 */
	public void setKanrenJitakuTel(String string) {
		kanrenJitakuTel = string;
	}

}
