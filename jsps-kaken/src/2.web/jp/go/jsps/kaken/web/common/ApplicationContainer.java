/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : 
 *    Date        : 
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.common;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �A�v���P�[�V�����S�̂Ŏg�p��������i�[���邽�߂̃R���e�i�N���X�B
 * 
 * ID RCSfile="$RCSfile: ApplicationContainer.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:49 $"
 */
public class ApplicationContainer {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * �N���X���B 
	 */
	private static final String CLASS_NAME = ApplicationContainer.class.getName();

	/**
	 * ���O�N���X�B 
	 */
	private static final Log log = LogFactory.getLog(CLASS_NAME);


	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * �V�X�e����Locale���
	 */
	private Locale systemLocale = null;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ApplicationContainer() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * �V�X�e����Locale�����擾����B
	 * @return	Locale���
	 */
	public Locale getSystemLocale() {
		return systemLocale;
	}

	/**
	 * �V�X�e����Locale����ݒ肷��B
	 * @param aLocale�@Locale���
	 */
	public void setSystemLocale(Locale aLocale) {
		systemLocale = aLocale;
	}


}
