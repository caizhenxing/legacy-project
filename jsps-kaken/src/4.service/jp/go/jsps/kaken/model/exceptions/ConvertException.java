/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.exceptions;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;


/**
 * IOD�EPDF���t�@�C���ϊ���O�N���X�B
 * 
 * ID RCSfile="$RCSfile: ConvertException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:41 $"
 */
public class ConvertException extends ApplicationException {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 6168424123683572303L;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 */
	public ConvertException(String message) {
		super(message,new ErrorInfo("errors.8000"));
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param errorInfo		�G���[���
	 */
	public ConvertException(String message, ErrorInfo errorInfo) {
		super(message, errorInfo);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param errorInfo		�G���[���
	 * @param cause			�����ƂȂ�����O
	 */
	public ConvertException(
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
	public ConvertException(String message, Throwable cause) {
		super(message, new ErrorInfo("errors.8000"), cause);
	}
}
