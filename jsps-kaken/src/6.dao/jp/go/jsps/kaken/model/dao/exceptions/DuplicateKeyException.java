/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.exceptions;

/**
 * �f�[�^�ǉ��̍ۂɎ�L�[���d������f�[�^�����ɑ��݂���ꍇ�̗�O�B
 * 
 * ID RCSfile="$RCSfile: DuplicateKeyException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:57 $"
 */
public class DuplicateKeyException extends DataAccessException {

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public DuplicateKeyException() {
		super();
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message	�ڍ׃��b�Z�[�W
	 */
	public DuplicateKeyException(String message) {
		super(message);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message	�ڍ׃��b�Z�[�W
	 * @param cause		�����ƂȂ�����O
	 */
	public DuplicateKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param cause		�����ƂȂ�����O�B
	 */
	public DuplicateKeyException(Throwable cause) {
		super(cause);
	}

}
