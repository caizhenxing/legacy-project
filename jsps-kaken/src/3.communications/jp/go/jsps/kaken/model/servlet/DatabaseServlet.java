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
package jp.go.jsps.kaken.model.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;

import jp.go.jsps.kaken.model.vo.ServiceInfo;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.SystemException;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

/**
 * �ݒ�����擾����T�[�u���b�g�B
 * 
 * ID RCSfile="$RCSfile: DatabaseServlet.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:54 $"
 */
public final class DatabaseServlet extends HttpServlet {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ���O�N���X�B 
	 */
	private static final Log log = LogFactory.getLog(DatabaseServlet.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * �R�}���h��ێ�����}�b�v�B
	 */
	private Map commands = new HashMap();

	/**
	 * �ݒ���XML
	 */
	private String pathname ="/WEB-INF/service-setting.xml";

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * Gracefully shut down this database servlet, releasing any resources
	 * that were allocated at initialization.
	 */
	public void destroy() {
		getServletContext().removeAttribute(IServiceName.APPLICATION_SERVICE);
	}

	/**
	 * �������������s���B
	 * �ݒ�t�@�C����Ǎ��ށB
	 */
	public void init() throws ServletException {

		try {
			load();
		} catch (Exception e) {
			log.error(pathname + "(�ݒ�t�@�C��):�Ǎ��Ɏ��s���܂����B", e);
			throw new UnavailableException(pathname + "(�ݒ�t�@�C��):�Ǎ��Ɏ��s���܂����B");
		}

		//�A�v���P�[�V�����ɓo�^
		getServletContext().setAttribute(IServiceName.APPLICATION_SERVICE,commands);

		//for debug
		if (log.isDebugEnabled()) {
			for (Iterator iter = commands.keySet().iterator();
				iter.hasNext();
				) {
				Object serviceName = iter.next();
				Object serviceClass = commands.get(serviceName);
				log.debug(
					"�T�[�r�X��:'"
						+ serviceName
						+ "' \t �����N���X��:'"
						+ serviceClass.getClass().getName());
			}
		}
	}



	/**
	 * �T�[�r�X��ǉ�����B
	 * �T�[�r�X�N���X�̃C���X�^���X���쐬����B
	 * @param info	�T�[�r�X���I�u�W�F�N�g
	 */
	public void addService(ServiceInfo info) {
		try {
			Object aInstance = Class.forName(info.getType()).newInstance();
			commands.put(info.getName(), aInstance);
		} catch (Exception e) {
			throw new SystemException(
				"�T�[�r�X'"+info.getName()+ "'�̃N���X'" + info.getType() + "'�̃C���X�^���X�̍쐬�Ɏ��s���܂����B",e);
		 }
	}
	

	/**
	 *	�ݒ���t�@�C����ǂݍ��ށB
	 */
	private synchronized void load() throws Exception {

		//������
		InputStream is = getServletContext().getResourceAsStream(pathname);
		if (is == null) {
			log.error(pathname + "(�ݒ���t�@�C��):������܂���B");
			return;
		}

		BufferedInputStream bis = new BufferedInputStream(is);

		// Construct a digester to use for parsing
		Digester digester = new Digester();
		digester.push(this);
		digester.setValidating(false);

		digester.addObjectCreate(
			"service-settings/service",
			"jp.go.jsps.kaken.model.vo.ServiceInfo");
		digester.addSetProperties("service-settings/service");
		digester.addSetNext("service-settings/service", "addService","jp.go.jsps.kaken.model.vo.ServiceInfo");

		// Parse the input stream to initialize our database
		try {
			digester.parse(bis);
		} catch (IOException e) {
			log.error(pathname + "(�ݒ���t�@�C��):�Ǎ��Ɏ��s���܂����B", e);
			throw e;
		} catch (SAXException e) {
			log.error(pathname + "(�ݒ���t�@�C��):��͂Ɏ��s���܂����B", e);
			throw e;
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException ioe) {
					log.error(
						pathname + "(�ݒ���t�@�C��):�t�@�C��IO�G���[�ł��B",
						ioe);
				}
			}
		}
	}
}
