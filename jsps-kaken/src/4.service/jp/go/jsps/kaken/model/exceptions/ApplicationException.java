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
 * �A�v���P�[�V�����֘A�̗�O�N���X�B
 * 
 * ID RCSfile="$RCSfile: ApplicationException.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:41 $"
 * 
 */
public class ApplicationException extends Exception{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 4264138343671550592L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 
	 * �G���[���B
	 */
	private ErrorInfo errorInfo;


	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param errorInfo		�G���[���
	 */
	public ApplicationException(String message, ErrorInfo errorInfo) {
		super(message);
		this.errorInfo = errorInfo;
	}


	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param cause			�����ƂȂ�����O
	 */
	public ApplicationException(
		String message,
		Throwable cause) {
		super(message,cause);
	}
	
	/**
	 * �R���X�g���N�^�B
	 * @param message		�ڍ׃��b�Z�[�W
	 * @param errorInfo		�G���[���
	 * @param cause			�����ƂȂ�����O
	 */
	public ApplicationException(
		String message,
		ErrorInfo errorInfo,
		Throwable cause) {
		super(message,cause);
		this.errorInfo = errorInfo;
	}


	//---------------------------------------------------------------------
	// Implementation of ErrorCoded interface
	//---------------------------------------------------------------------

	/**
	 * �G���[��񂪃Z�b�g����Ă��邩���擾����B
	 * @return	�G���[��񂪃Z�b�g����Ă���Ƃ� true �ȊO false
	 */
	public boolean isErrorInfo(){
		if(errorInfo == null){
			return false;
		}
		return true;
	}

	/**
	 * �G���[�R�[�h���擾����B
	 * @return	�G���[�R�[�h
	 */
	public String getErrorCode() {
		return errorInfo.getErrorCode();
	}

	/**
	 * �G���[�������擾����B
	 * @return	�G���[����
	 */
	public Object[] getErrorArgs() {
		return errorInfo.getErrorArgs();
	}

}
