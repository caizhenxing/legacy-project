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

import jp.go.jsps.kaken.model.vo.ValueObject;
import java.util.Date;

/**
 * �R��������ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinsainPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class ShinsainPk extends ValueObject{
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �R�����ԍ� */
	private String shinsainNo;

	/** �S�����Ƌ敪 */
	private String jigyoKubun;

	/** �ŏI���O�C�����@*/
	private Date loginDate;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsainPk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

	/**
	 * @param string
	 */
	public void setShinsainNo(String string) {
		shinsainNo = string;
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
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

//�ŏI���O�C������ǉ�

	/**
	 * @return
	 */
	public Date getLoginDate() {
		return loginDate;
	}
	/**
	 * @param date
	 */
	public void setLoginDate(Date date) {
		loginDate = date;

	}

}
