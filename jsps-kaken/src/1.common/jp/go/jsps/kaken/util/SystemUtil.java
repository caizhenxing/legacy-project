/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/10/15
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

/**
 * �V�X�e���v���p�e�B���[�e�B���e�B�N���X<!--�B-->
 * jakarta-commons �� SystemUtils�N���X���g���������́B
 * ID RCSfile="$RCSfile: SystemUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class SystemUtil extends org.apache.commons.lang.SystemUtils {
	
	/** ���[�h�o�����T�[�X�C�b�`���O����pCookie�� */
	public static final String LB_COOKIE_NAME = System.getProperty("lb.cookie.name");
	
	/** ���[�h�o�����T�[�X�C�b�`���O����pCookie�l */
	public static final String LB_COOKIE_VALUE = System.getProperty("lb.cookie.value");
	
	
}
