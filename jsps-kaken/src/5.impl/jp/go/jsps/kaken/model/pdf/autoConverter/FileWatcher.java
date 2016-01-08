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
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * �ϊ��t�@�C���Ď��N���X�B
 * ID RCSfile="$RCSfile: FileWatcher.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:55 $"
 */
public class FileWatcher extends Thread {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O�N���X�B */
	private static final Log log = LogFactory.getLog(FileWatcher.class);

	/** �o�̓t�@�C���g���q�B */
	//2005.07.15 iso PDF�Y�t�@�\�̎���
//	private static String OUT_FILE_SUFFIX = ".iod";
	private static String OUT_FILE_SUFFIX = ".pdf";

	/**  �Ď����ԊԊu(�b) */
	private static int refreshSeconds = ApplicationSettings.getInteger(ISettingKeys.PDF_REFRESH_SECONDS).intValue();

	/** �ϊ��Ώ�IN�t�H���_�B */
	private static File IN_FOLDER = ApplicationSettings.getFile(ISettingKeys.PDF_IN_FOLDER);

	/** �ϊ�����OUT�t�H���_�B*/
	private static File OUT_FOLDER = ApplicationSettings.getFile(ISettingKeys.PDF_OUT_FOLDER);

	/** �ϊ��X�e�[�^�X�t�@�C���o�͐�t�H���_�B */
	private static File STATUS_FOLDER = ApplicationSettings.getFile(ISettingKeys.PDF_STATUS_FOLDER);

	/** �ϊ����ʃG���[���i�[�t�H���_�B */
	private static File ERR_FOLDER = ApplicationSettings.getFile(ISettingKeys.PDF_ERR_FOLDER);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �Ď����ԊԊu�B */
	private final long delay;

	/** �ϊ����N�G�X�g�󂯓n���N���X�B*/
	private final AutoConverter converter;

	//---------------------------------------------------------------------
	// constructor
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 * @param converter	�Ď��Ώەϊ��N���X�B
	 */
	public FileWatcher(AutoConverter converter) {
		super();
		this.converter = converter;
		setDaemon(true);
		delay = refreshSeconds * 1000;
	}

	/**
	 * �ϊ��Ώ�IN�t�H���_���擾����B
	 * @return	�ϊ��Ώ�IN�t�H���_�B
	 */
	public static File getInFolder() {
		return IN_FOLDER;
	}

	/**
	 * �t�@�C���ύX���`�F�b�N����X���b�h�B
	 */
	public void run() {
		while (true) {
			//�ϊ��Ώۓ��̓t�@�C���`�F�b�N�����B
			if (log.isDebugEnabled()) {
				log.debug("--------- �ϊ��t�@�C���m�F�J�n ---------");
			}
			checkFiles(converter.getFileInfo());
			try {
				sleep(delay);
			} catch (InterruptedException ie) {
				log.warn("�t�@�C���Ď��X���b�h�ҋ@���Ɋ������������܂����B");
			}
		}
	}

	/**
	 * �����Ώۃt�@�C�����ŃX�e�[�^�X�o�͐�t�H���_���Ď�����B
	 * @param inputFiles	�����Ώۃt�@�C�����
	 */
	public void checkFiles(Map inputFiles) {
		//�X�e�[�^�X�t�H���_�̃t�@�C�����ꗗ���擾����B
		if(!STATUS_FOLDER.exists()){
			throw new SystemException("�ϊ��X�e�[�^�X�t�@�C���o�͐�t�H���_�B'" + STATUS_FOLDER + "'��������܂���B");
		}
		File[] statusFileList = STATUS_FOLDER.listFiles();
		
		for (int i = 0; i < statusFileList.length; i++) {
			File statusFile = statusFileList[i];
			if (log.isDebugEnabled()) {
				log.debug("�`�F�b�N�ΏۃX�e�[�^�X�t�@�C���� ::" + statusFile);
			}
			synchronized (inputFiles) {
				//�ϊ��Ώۂ̃t�@�C���ꗗ���擾����B
				for (Iterator iter = inputFiles.keySet().iterator();iter.hasNext();) {
					//�ϊ��Ώۓ��̓t�@�C���B	
					String inputFile = (String) iter.next();
					//�������ʃI�u�W�F�N�g
					ConvertResult result =(ConvertResult) inputFiles.get(inputFile);
					//�X�e�[�^�X�t�@�C���`�F�b�N
					String status = checkStatusFile(statusFile, inputFile);

					if (STATUS_NONE.equals(status)) {
						//�Y���t�@�C���ȊO
						continue;
					}
					//�������ʔ��ʗp�t���O
					try {
						if (STATUS_OK.equals(status)) {
							//OK START-----------------
							processOk(inputFile, result);
							return;
							//OK END  -----------------
						} else {
							//NG START-----------------
							processNg(statusFile, result);
							return;
							//NG END  -----------------
						}
					} finally {
						//�I���W�i���̃X�e�[�^�X�t�@�C�����폜����B
						if (log.isDebugEnabled()) {
							log.debug("�X�e�[�^�X�t�@�C��'" + statusFile + "'�̍폜���܂��B");
						}
						FileUtil.delete(statusFile);
					}
				}
			}
		}
	}

	/**
	 * �ϊ��Ɏ��s�����ꍇ�̏���
	 * �X�e�[�^�X�t�@�C�����G���[�t�H���_�Ɉړ�����B
	 * @param statusFile	
	 * @param result
	 */
	private void processNg(File statusFile, ConvertResult result) {
		try {
			//�X�e�[�^�X�t�@�C�����G���[�t�H���_�ֈړ�����B
			FileResource statusResource = FileUtil.readFile(statusFile.getParentFile(),statusFile.getName());
			if (!FileUtil.writeFile(ERR_FOLDER, statusResource)) {
				log.warn("�X�e�[�^�X�t�@�C��'" + statusFile + "'���G���[�t�H���_�ւ̈ړ��Ɏ��s���܂����B");
			}
		} catch (IOException e) {
			log.warn(
				"�X�e�[�^�X�t�@�C��'" + statusFile + "'�̃G���[�t�H���_�ւ̈ړ��Ɏ��s���܂����B", e);
		}
		
		result.setRealData(new SystemException("�t�@�C���̕ϊ��Ɏ��s���܂����B�ϊ��G���[�t�@�C��'"	+ new File(ERR_FOLDER,statusFile.getName())+ "'"));
	}

	/**
	 * ����ɕϊ����ꂽ�ꍇ�̏����B
	 * �ϊ������t�@�C�����擾���A�������ʃI�u�W�F�N�g�ɃZ�b�g����B
	 * 
	 * @param inputFile
	 * @param result
	 */
	private void processOk(String inputFile, ConvertResult result) {
		try {
			result.setRealData(getResult(inputFile));
		} catch (IOException e) {
			result.setRealData(
				new SystemException(
					"�ϊ�����I���B���ʃt�@�C��'" + inputFile + "'�̎擾�Ɏ��s���܂��� �B ",
					e));
		}
	}
	

	/**
	 * �ϊ������t�@�C�����擾����B
	 * @param inputFile	���̓t�@�C����
	 * @return	�ϊ���t�@�C�����\�[�X
	 * @throws IOException	�t�@�C���̎擾����IO��O
	 */
	private FileResource getResult(String inputFile) throws IOException {
		//���ʃt�@�C�����擾����B
		File resultFile = new File(OUT_FOLDER, inputFile + OUT_FILE_SUFFIX);
		FileResource resource =	FileUtil.readFile(resultFile);
		//�o�̓t�@�C�����폜����B
		if (!FileUtil.delete(resultFile)) {
			log.warn("�o�̓t�@�C���̍폜�Ɏ��s���܂����B:" + resultFile);
		}
		return resource;
	}

	/**
	 * �X�e�[�^�X�t�@�C�������̓t�@�C���̏������ʃt�@�C���ł��邩���`�F�b�N����B
	 * �������ʂ��`�F�b�N����B
	 * @param statusFile	�X�e�[�^�X�t�@�C��
	 * @param inputFile		���t�@�C��
	 * @return				����I���� true �������ʂ��擾�ł��Ȃ��Ƃ� false
	 * @throws ConvertException		�������ʃt�@�C����NG�܂���ERR�̂Ƃ�
	 */
	private String checkStatusFile(File statusFile, String inputFile) {
		
		//�X�e�[�^�X�t�@�C���`���̃`�F�b�N �y�`#OK.���t�@�C�����z	
		Matcher matcher = pattern.matcher(statusFile.getName());
		if (matcher.find()) {
			if (log.isDebugEnabled()) {
				log.debug("�X�e�[�^�X '" + matcher.group(STATUS_GROUPID) + "'");
				log.debug("�t�@�C���� '" + matcher.group(ORG_FILE_NAME_GROUPID) + "'");
			}
			//�X�e�[�^�X�t�@�C���������t�@�C�����ł��邩�ǂ����B
			if (inputFile.equals(matcher.group(ORG_FILE_NAME_GROUPID))) {
				//���t�@�C��������̓t�@�C�������폜����B
				converter.getFileInfo().remove(inputFile);
				//2005.01.24 iso �啶������������ʂ��Ȃ��悤�ݒ�
//				if (STATUS_OK.equals(matcher.group(STATUS_GROUPID))) {
				if (STATUS_OK.equals(matcher.group(STATUS_GROUPID).toLowerCase())) {
					//����I����
					return STATUS_OK;
				} else {
					//�ȊO
					return STATUS_ERR;
				}
			}
		}
		return STATUS_NONE;
	}

	/** �X�e�[�^�X�t�@�C�����f�p���K�\���p�^�[���B*/
	private Pattern pattern =
		Pattern.compile(
			"^~\\d+?.("
				+ STATUS_OK
				+ "|"
				+ STATUS_NG
				+ "|"
				+ STATUS_ERR
				+ ").(\\S*)$"
				,Pattern.CASE_INSENSITIVE);		//2005.01.24 iso �啶������������ʂ��Ȃ��悤�ݒ�
	
	/** �X�e�[�^�X���O���[�v�B*/
	private int STATUS_GROUPID = 1;
	/** �t�@�C�����O���[�v�B */
	private int ORG_FILE_NAME_GROUPID = 2;
	/** �ϊ��X�e�[�^�XOK */
	private static final String STATUS_OK = "ok";
	/** �ϊ��X�e�[�^�XNG�B */
	private static final String STATUS_NG = "ng";
	/** �ϊ��X�e�[�^�XERR�B */
	private static final String STATUS_ERR = "err";
	/** �Ώۃt�@�C���ȊO�̂Ƃ��B */
	private static final String STATUS_NONE = "";

}
