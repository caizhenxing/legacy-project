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
package jp.go.jsps.kaken.model.exceptions;

import jp.go.jsps.kaken.model.vo.ErrorInfo;

/**
 * �f�[�^�Q�Ƃ̌����������ꍇ�̗�O�N���X�B
 * 
 * ID RCSfile="$RCSfile: UserAuthorityException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:42 $"
 */
public class UserAuthorityException extends NoDataFoundException {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 */
	public UserAuthorityException(String message) {
		super(message,new ErrorInfo("errors.authority"));
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param errorInfo		�G���[���
	 */
	public UserAuthorityException(String message, ErrorInfo errorInfo) {
		super(message, errorInfo);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param errorInfo		�G���[���
	 * @param cause			�����ƂȂ�����O
	 */
	public UserAuthorityException(
		String message,
		ErrorInfo errorInfo,
		Throwable cause) {
		super(message, errorInfo, cause);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param cause			�����ƂȂ�����O
	 */
	public UserAuthorityException(String message, Throwable cause) {
		super(message, new ErrorInfo("errors.authority"), cause);
	}
}