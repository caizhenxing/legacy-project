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
package jp.go.jsps.kaken.model.dao.exceptions;

/**
 * �f�[�^�x�[�X�֘A�̗�O�B
 * 
 * ID RCSfile="$RCSfile: DataAccessException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:57 $"
 * 
 */
public class DataAccessException extends Exception {

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public DataAccessException() {
		super();
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message	�ڍ׃��b�Z�[�W
	 */
	public DataAccessException(String message) {
		super(message);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message	�ڍ׃��b�Z�[�W
	 * @param cause		�����ƂȂ�����O
	 */
	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param cause		�����ƂȂ�����O�B
	 */
	public DataAccessException(Throwable cause) {
		super(cause);
	}

}

