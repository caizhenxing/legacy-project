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

import jp.go.jsps.kaken.model.vo.ErrorInfo;

/**
 * �T�[�o�ڑ��Ɏ��s�����Ƃ��ɔ��������O�N���X�B
 * 
 * ID RCSfile="$RCSfile: ConnectException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:41 $"
 */
public class ConnectException extends ApplicationException {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -6952541947424927379L;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 */
	public ConnectException(String message) {
		super(message,new ErrorInfo("errors.5004"));
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param errorInfo		�G���[���
	 */
	public ConnectException(String message, ErrorInfo errorInfo) {
		super(message, errorInfo);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param errorInfo		�G���[���
	 * @param cause			�����ƂȂ�����O
	 */
	public ConnectException(
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
	public ConnectException(String message, Throwable cause) {
		super(message, new ErrorInfo("errors.5004"), cause);
	}
}
