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
package jp.go.jsps.kaken.model.vo;

import java.io.Serializable;

/**
 * �G���[���ێ��N���X�B
 * 
 * ID RCSfile="$RCSfile: ErrorInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:38 $"
 */
public class ErrorInfo implements Serializable{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -3441380396156858703L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 
	 * �G���[�R�[�h���B
	 */
	private String errorCode;

	/** 
	 * �G���[�������B
	 */
	private Object[] errorArgs;
	
	
	/**
	 * �G���[�v���p�e�B
	 */
	private String property = "GLOBAL_ERROR";
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 * @param errorCode		�G���[�R�[�h���
	 */
	public ErrorInfo(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	/**
	 * �R���X�g���N�^�B
	 * @param errorCode		�G���[�R�[�h���
	 * @param errorArgs		�G���[�������
	 */
	public ErrorInfo(String errorCode,Object[] errorArgs) {
		super();
		this.errorCode = errorCode;
		this.errorArgs = errorArgs;
	}

	
	/**
	 * �R���X�g���N�^�B
	 * @param errorCode       �G���[�R�[�h���
	 * @param errorArgs       �G���O�������
	 * @param property 	   �G���[�v���p�e�B
	 */
	public ErrorInfo(String errorCode, Object[] errorArgs, String property){
		super();
		this.errorCode = errorCode;
		this.errorArgs = errorArgs;
		this.property  = property;
	}
	

	//---------------------------------------------------------------------
	// Proerties
	//---------------------------------------------------------------------

	/**
	 * �G���[���������擾����B
	 * @return	�G���[�������
	 */
	public Object[] getErrorArgs() {
		return errorArgs;
	}

	/**
	 * �G���[�R�[�h�����擾����B
	 * @return	�G���[�R�[�h���
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * �G���[���������Z�b�g����B
	 * @param objects	�G���[�������
	 */
	public void setErrorArgs(Object[] objects) {
		errorArgs = objects;
	}

	/**
	 * �G���[�R�[�h�����Z�b�g����B
	 * @param string	�G���[�R�[�h���
	 */
	public void setErrorCode(String string) {
		errorCode = string;
	}

	/**
	 * @return
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param string
	 */
	public void setProperty(String string) {
		property = string;
	}

}
