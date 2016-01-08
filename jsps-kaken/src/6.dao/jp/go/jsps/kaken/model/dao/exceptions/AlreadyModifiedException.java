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
 * �f�[�^�x�[�X�̃f�[�^�����ɍX�V����Ă����ꍇ�̗�O�B
 * 
 * ID RCSfile="$RCSfile: AlreadyModifiedException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:57 $"
 */

public class AlreadyModifiedException extends DataAccessException {

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
    
	/**
	 * �R���X�g���N�^�B
	 * @param message	�ڍ׃��b�Z�[�W
	 */
	public AlreadyModifiedException(String message) {
		super(message);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message	�ڍ׃��b�Z�[�W
	 * @param cause		�����ƂȂ�����O
	 */
	public AlreadyModifiedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param cause		�����ƂȂ�����O�B
	 */
	public AlreadyModifiedException(Throwable cause) {
		super(cause);
	}}
