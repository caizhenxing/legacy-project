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
 * �V�X�e���r�W�[��O�N���X�B
 * 
 * ID RCSfile="$RCSfile: SystemBusyException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:41 $"
 * 
 */
public class SystemBusyException extends ApplicationException {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -6495039124887634495L;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 */
	public SystemBusyException(String message) {
		super(message,new ErrorInfo("errors.busy"));
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param errorInfo		�G���[���
	 */
	public SystemBusyException(String message, ErrorInfo errorInfo) {
		super(message, errorInfo);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param errorInfo		�G���[���
	 * @param cause			�����ƂȂ�����O
	 */
	public SystemBusyException(
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
	public SystemBusyException(String message, Throwable cause) {
		super(message, new ErrorInfo("errors.busy"), cause);
	}
}
