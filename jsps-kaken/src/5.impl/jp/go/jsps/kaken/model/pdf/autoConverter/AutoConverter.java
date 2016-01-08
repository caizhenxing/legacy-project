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
package jp.go.jsps.kaken.model.pdf.autoConverter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.go.jsps.kaken.model.exceptions.ConvertException;
import jp.go.jsps.kaken.model.pdf.webdoc.WebdocUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �Y�t�t�@�C���ϊ��N���X�B
 * ID RCSfile="$RCSfile: AutoConverter.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:55 $"
 */
public class AutoConverter {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ���O�N���X�B 
	 */
	private static final Log log = LogFactory.getLog(AutoConverter.class);

	/** 
	 * �󂯓n���N���X�B 
	 */
	private static final AutoConverter Converter;

	/**
	 * ������
	 */
	static {
		Converter = new AutoConverter();
		//���[�J�[�X���b�h�̎��s
		new FileWatcher(Converter).start();
	}

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * �ϊ��Ώۃt�@�C������ێ�����}�b�v�B
	 */
	private final Map fileInfo = Collections.synchronizedMap(new HashMap());

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 *	�R���X�g���N�^�B
	 */
	private AutoConverter() {
		super();
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * �Y�t�t�@�C���ϊ��N���X���擾����B
	 * @return	�ϊ��N���X
	 */
	public static AutoConverter getConverter() {
		return Converter;
	}

	/**
	 * �ϊ��T�[�r�X�����s����B
	 * @param attachedResource		�ϊ��Ώۃt�@�C�����
	 * @return						�ϊ��Ώی��ʃI�u�W�F�N�g
	 * @throws ConvertException	�ϊ��ΏۓY�t�t�@�C�����w��IN�t�H���_�ւ̃R�s�[����Ƃ��Ɏ��s�����Ƃ��B
	 */
	public synchronized ConvertResult setFileResource(FileResource attachedResource)
		throws ConvertException {

		ConvertResult result = new ConvertResult(this);
		//�ύX���邽�߂̈�ӂ̖��O
		File attachedFile =
			new File(
				FileWatcher.getInFolder(),
				WebdocUtil.getTempName() + "_" + attachedResource.getName());
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			boolean bWrite =
				FileUtil.writeFile(
					attachedFile,
					attachedResource.getBinary());
			if (!bWrite) {
				throw new ConvertException("'" + attachedFile + "'�ϊ��Ώۃt�@�C����ϊ��f�B���N�g���ɏ������߂܂���ł����B");
			}
		} catch (IOException e) {
			throw new ConvertException(
				"�ϊ��Ώۃt�@�C����ϊ��f�B���N�g���ɃR�s�[��" + attachedFile + "�ɗ�O���������܂����B",e);
		}

		//�ϊ��ΏۂɃZ�b�g����B		
		putFileInfo(attachedFile, result);

		return result;
	}

	/**
	 * �ϊ��Ώۃt�@�C����ǉ�����B
	 * @param attachedResource	�ϊ��Ώۃt�@�C�����B
	 * @param result			�ϊ����ʃI�u�W�F�N�g�B
	 */
	private synchronized void putFileInfo(
		File attachedFile,
		ConvertResult result) {
		if (fileInfo.isEmpty()) {
			notifyAll();
		}
		fileInfo.put(attachedFile.getName(), result);
		if (log.isDebugEnabled()) {
			log.debug("�ϊ��Ώۃt�@�C�����Z�b�g���܂����B�ҋ@���t�@�C�����X�g::" + fileInfo.keySet());
		}
	}

	/**
	 * �ϊ��Ώۃt�@�C�������擾����B
	 * @param attachedResource	�ϊ��Ώۃt�@�C�����B
	 * @param result			�ϊ����ʃI�u�W�F�N�g�B
	 */
	public synchronized Map getFileInfo() {
		if (fileInfo.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				log.warn("�ϊ��Ώۃt�@�C�����ҋ@���Ɋ������������܂����B");
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("�ϊ��Ώۃt�@�C�������擾���܂����B�ҋ@���t�@�C�����X�g::" + fileInfo.keySet());
		}
		return fileInfo;
	}

	/**
	 * �^�C���A�E�g�����A�ϊ��Ώۃt�@�C�������폜����B
	 * @param result	�ϊ��Ώی��ʃI�u�W�F�N�g
	 */
	public synchronized void removeFileInfo(ConvertResult result) {
		if (fileInfo.containsValue(result)) {
			log.debug("�ϊ��Ώۃt�@�C�����t�@�C�������폜���܂��B");
			for (Iterator iter = fileInfo.keySet().iterator();
				iter.hasNext();
				) {
				String attachedFileName = (String) iter.next();
				if (fileInfo.get(attachedFileName) == result) {
					fileInfo.remove(attachedFileName);
					log.debug(
						"�ϊ��Ώۃt�@�C�����t�@�C�����" + attachedFileName + "���폜���܂����B");
					break;
				}
			}
		}
	}
}
