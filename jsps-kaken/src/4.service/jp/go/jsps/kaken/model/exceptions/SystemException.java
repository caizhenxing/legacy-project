/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.exceptions;

/**
 * �V�X�e���֘A�̗�O�N���X�B
 * 
 * ID RCSfile="$RCSfile: SystemException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:41 $"
 * 
 */
public class SystemException extends RuntimeException {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 73683311051810516L;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public SystemException() {
		super();
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message	�ڍ׃��b�Z�[�W
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message	�ڍ׃��b�Z�[�W
	 * @param cause		�����ƂȂ�����O
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param cause		�����ƂȂ�����O�B
	 */
	public SystemException(Throwable cause) {
		super(cause);
	}

}
