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
package jp.go.jsps.kaken.model.common;

import java.lang.reflect.Proxy;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.client.CallServletHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �T�[�r�X���擾���邽�߂̃t�@�N�g���N���X�B
 * ID RCSfile="$RCSfile: SystemServiceFactory.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:50 $"
 */
public class SystemServiceFactory {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * �N���X���B 
	 */
	private static final String CLASS_NAME = SystemServiceFactory.class.getName();

	/**
	 * ���O�N���X�B 
	 */
	private static final Log log = LogFactory.getLog(CLASS_NAME);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	private SystemServiceFactory() {
		super();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * �V�X�e���T�[�r�X���擾����B
	 * @param serviceName		�T�[�r�X��
	 * @return					�V�X�e���T�[�r�X�����N���X
	 */
	public static ISystemServise getSystemService(String serviceName) {
		Class[] serviceInterface = new Class[] { ISystemServise.class };
		ISystemServise proxy =
			(ISystemServise) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(),
				serviceInterface,
				new CallServletHandler(serviceName));
		return proxy;
	}
	
	/**
	 * �V�X�e���T�[�r�X���擾����B
	 * @param serviceName		�T�[�r�X��
	 * @param serverUrl			�T�[�oURL
	 * @return					�V�X�e���T�[�r�X�����N���X
	 */
	public static ISystemServise getSystemService(
		String serviceName,
		String serverUrl) {
		Class[] serviceInterface = new Class[] { ISystemServise.class };
		ISystemServise proxy =
			(ISystemServise) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(),
				serviceInterface,
				new CallServletHandler(serviceName,serverUrl));
		return proxy;
	}
}
