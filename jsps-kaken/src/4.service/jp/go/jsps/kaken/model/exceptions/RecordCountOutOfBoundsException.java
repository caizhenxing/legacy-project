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
 * �ꗗ�擾�ɂ����āA�Y���������ő�l�𒴂����ꍇ�̗�O�N���X�B
 * 
 * ID RCSfile="$RCSfile: RecordCountOutOfBoundsException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:42 $"
 */
public class RecordCountOutOfBoundsException extends ApplicationException {

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
	public RecordCountOutOfBoundsException(String message) {
		super(message,new ErrorInfo("errors.maxcount"));
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param errorInfo		�G���[���
	 */
	public RecordCountOutOfBoundsException(String message, ErrorInfo errorInfo) {
		super(message, errorInfo);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param errorInfo		�G���[���
	 * @param cause			�����ƂȂ�����O
	 */
	public RecordCountOutOfBoundsException(
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
	public RecordCountOutOfBoundsException(String message, Throwable cause) {
		super(message, new ErrorInfo("errors.maxcount"), cause);
	}
	
	
}