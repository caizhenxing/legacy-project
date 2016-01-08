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
package jp.go.jsps.kaken.model.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ConnectException;
import jp.go.jsps.kaken.model.exceptions.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * �T�[�u���b�g�̃��\�b�h���Ăяo�����߂̏������s�n���h���N���X�B
 * 
 * ID RCSfile="$RCSfile: CallServletHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:51 $"
 */
public class CallServletHandler implements InvocationHandler{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ���O�N���X�B 
	 */
	private static final Log log = LogFactory.getLog(CallServletHandler.class);

	/**
	 * �T�[�u���b�gURL 
	 */
	private String strURL;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * �T�[�r�X�� 
	 */
	private String serviceName =null;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 * @param serviceName	�T�[�r�X������
	 */
	public CallServletHandler(String serviceName) {
		super();
		this.serviceName = serviceName;
		this.strURL = ApplicationSettings.getString(ISettingKeys.GYOMU_SERVLET_URL);
	}

	/**
	 * �R���X�g���N�^�B
	 * @param serviceName	�T�[�r�X������
	 * @param serverUrl		�T�[�oURI
	 * 
	 */
	public CallServletHandler(String serviceName,String serverUrl) {
		super();
		this.serviceName = serviceName;
		this.strURL = serverUrl;
	}
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* (�� Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (args == null) {
			args = new Object[0];
		}
		return request(serviceName, method.getName(),method.getParameterTypes(), args);
	}


	/**
	 * �T�[�o�ɐڑ��������擾����B
	 * @param serviceName	�Ăяo���T�[�r�X���B
	 * @param method		�Ăяo�����\�b�h���B
	 * @param paramTypes	�Ăяo�����\�b�h�̃p�����[�^�^�z��B
	 * @param params		�Ăяo�����\�b�h�̃p�����[�^�z��B
	 * @return				�������ʃI�u�W�F�N�g
	 * @throws Exception	
	 */
	private Object request(
		final String serviceName,
		final String method,
		final Class[] paramTypes,
		final Object[] params)
		throws ApplicationException {

		ObjectOutputStream Out = null;
		ObjectInputStream In = null;
		Object objReturn = new Object();
		try {
			URL aURL;
			try {
				aURL = new URL(strURL);
			} catch (MalformedURLException e) {
				throw new SystemException("�T�[�u���b�g�A�h���X '" + strURL + "'�������ł��B",e);
			}

			//�T�[�u���b�g�ɐڑ�����
			URLConnection uCon = aURL.openConnection();
			uCon.setRequestProperty(
				"Content-type",
				"application/octet-stream");
			uCon.setUseCaches(false);
			//�T�[�o����ւ̏������݂�������
			uCon.setDoOutput(true);
			//�T�[�o����̓ǂݍ��݂�������
			uCon.setDoInput(true);

			try {
				//Servlet�ւ̏o�̓X�g���[�����쐬����
				Out = new ObjectOutputStream( new BufferedOutputStream(uCon.getOutputStream()));
				//�T�[�u���b�g�ɃT�[�r�X���A���\�b�h���A�p�����[�^�𑗂�
				Out.writeUTF(serviceName);
				Out.writeUTF(method);
				Out.writeObject(paramTypes);
				Out.writeObject(params);
				Out.flush();
			} finally {
				if (Out != null)
					Out.close();
			}
			
			try {
				//Servlet����̓��̓X�g���[�����쐬����
				In = new ObjectInputStream(	new BufferedInputStream(uCon.getInputStream()));
				//�߂�l���擾����
				objReturn = In.readObject();
			} finally {
				if (In != null)
					In.close();
			}
			
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("�T�[�o�[�ڑ��������ɗ�O���������܂����B", e);
			}
			throw new ConnectException("�T�[�o�[�ڑ��������ɗ�O���������܂����B", e);
		}

		//�T�[�u���b�g�����O���߂��Ă��Ă��Ȃ����`�F�b�N����
		if (objReturn instanceof ApplicationException) {
			throw (ApplicationException) objReturn;
		} else if (objReturn instanceof SystemException) {
			throw (SystemException) objReturn;
		} else if (objReturn instanceof IOException) {
			//�T�[�o�[�ڑ��������ɗ�O���������܂����B
			throw new ConnectException("�T�[�o�[��������IO��O���������܂����B",(IOException) objReturn);
		} else if (objReturn instanceof Throwable) {
			if (log.isErrorEnabled()) {
				log.error("�T�[�o�����ŗ\�����ʗ�O���������܂����B", (Throwable) objReturn);
			}
			throw new SystemException("�T�[�o�����ŗ\�����ʗ�O���������܂����B",(Throwable) objReturn);
		}
		return objReturn;
	}
}
