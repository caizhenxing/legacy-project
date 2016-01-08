/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/25
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.pdf.webdoc;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.ConvertException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yss.iothe.iowebdoc.iodoc;
import yss.iothe.iowebdoc.iodtopdf;
import yss.iothe.iowebdoc.webdocmem;
import yss.pdfmakeup.pdfcombine;
import yss.pdfmakeup.pmudst;
import yss.pdfmakeup.pmuobjwatermark;
import yss.pdfmakeup.pmusrc;

/**
 * IOWEBDOC��API���Ăяo�����߂̃��[�e�B���e�B�N���X�B
 * 
 * ID RCSfile="$RCSfile: WebdocUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:58 $"
 */
public class WebdocUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ���O�N���X�B 
	 */
	private static final Log log = LogFactory.getLog(WebdocUtil.class);

	/** ���O�iPDF MakeUp�j*/
	protected static Log pmLog = LogFactory.getLog("pdfmakeup");
	
	//---------------------------------------------------------------------
	// static data
	//---------------------------------------------------------------------

	/** 
	 * ���[�U�p��ƃt�H���_�e�f�B���N�g���B
	 */
	private static File WORK_PARENT_FOLDER;

	/** 
	 * �o�͐�PDF�t�@�C�����B
	 */
	public static final String PDF = ".pdf";

	/** 
	 * �o�͐�PDF�t�@�C�����B
	 */
	public static final String IOD = ".iod";

	/** 
	 * PDF�ϊ����̃��O�t�@�C�����B
	 */
	public static final String LOG = ".log";

	//---------------------------------------------------------------------
	// static initializer  
	//---------------------------------------------------------------------

	static {
		WORK_PARENT_FOLDER =
			ApplicationSettings.getFile(ISettingKeys.PDF_WORK_FOLDER);
		WORK_PARENT_FOLDER.mkdirs();
	}

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public WebdocUtil() {
		super();
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * �ݒ�����pdf�t�@�C�����쐬����B
	 * @param 	iodSettingInfo			�ݒ���
	 * @return	pdf�t�@�C��				�쐬����PDF�t�@�C��
	 */
	public static File iodFileCreation(List iodSettingInfo) {

		//-------------------------------------
		// �p�����[�^�`�F�b�N
		//-------------------------------------
		if (iodSettingInfo == null || iodSettingInfo.isEmpty())
			throw new IllegalArgumentException(
				//2005.07.14 iso PDF�ϊ��ɕύX
//				WebdocUtil.class +"::iod���[�쐬�ݒ��񂪖����ł��B");
				WebdocUtil.class +"::pdf���[�쐬�ݒ��񂪖����ł��B");

		//-------------------------------------
		// �ϐ�������
		//-------------------------------------
		//1�ł߂̐ݒ�����擾
		PageInfo pageInfo = (PageInfo) iodSettingInfo.get(0);
		String tempName = getTempName();
		File workFolder = getWorkFolder(tempName);

		//2005.06.14 iso PDF�ϊ��ɕύX
//		File outIodFile = new File(workFolder, tempName + IOD);
		File outIodFile = new File(workFolder, tempName + PDF);
		File outIodLogFile = new File(workFolder, outIodFile.getName() + LOG);

		//-------------------------------------
		// �쐬
		//-------------------------------------
		/* IOWEBDOC�p���O�t�@�C���B	 */
		final String IoDocLogFilePath = outIodLogFile.getAbsolutePath();

		/* �@�C���X�^���X�쐬 */
		webdocmem webdocmem = new webdocmem();

		try {
			/* ���O�t�@�C�������w�肵�܂��B */
			if (webdocmem.setlog(IoDocLogFilePath) < 0) {
				log.error(
					"webdocmem#setlog ���O�t�@�C���̐ݒ�Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ webdocmem.getmess());
				throw new SystemException(webdocmem.getmess());
			}

			//�f�o�b�O���o��
			if (log.isDebugEnabled()) {
				log.debug("webdocmem info :: ���O�t�@�C����" + webdocmem.getlog());
			}

			/* �A���k�̎�ނ��w�肵�܂� */
			if (webdocmem.setcompress(iodoc.WEBDOC_COMPRESS_DEFAULT) < 0) {
				log.error(
					"webdocmem#setcompress ���k��ނ̐ݒ�Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ webdocmem.getmess());
				throw new SystemException(webdocmem.getmess());
			}

			/* �BIOD�����[�h���� */
			if (webdocmem.loadiod(pageInfo.getTemplateFile().getAbsolutePath())
				< 0) {
				log.error(
					"webdocmem#loadiod ���C�A�E�g(.iod)�t�@�C���̃��[�h�Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ webdocmem.getmess());
				throw new SystemException(webdocmem.getmess());
			}

			//�f�o�b�O���o��
			if (log.isDebugEnabled()) {
				log.debug(
					"webdocmem info :: iod�t�@�C����" + webdocmem.getloadiod());
			}

			/* �C�o�͂���IOD�̃t�@�C������ݒ� */
			//2005.07.14 iso PDF�ϊ��ɕύX
//			if (webdocmem.setoutiod(outIodFile.getAbsolutePath()) < 0) {
//				log.error(
//					"webdocmem#setoutiod �o��(.iod)�t�@�C���̎w��Ɏ��s���܂����B�G���[���b�Z�[�W��"
//						+ webdocmem.getmess());
//				throw new SystemException(webdocmem.getmess());
//			}
//
//			//�f�o�b�O���o��
//			if (log.isDebugEnabled()) {
//				log.debug(
//					"webdocmem info :: �o��(.iod)�t�@�C���� " + webdocmem.getoutiod());
//			}
			if (webdocmem.setoutpdf(outIodFile.getAbsolutePath()) < 0) {
				log.error(
					"webdocmem#setoutiod �o��(.pdf)�t�@�C���̎w��Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ webdocmem.getmess());
				throw new SystemException(webdocmem.getmess());
			}
			//�f�o�b�O���o��
			if (log.isDebugEnabled()) {
				log.debug(
					"webdocmem info :: �o��(.pdf)�t�@�C���� " + webdocmem.getoutpdf());
			}

			//-------------------------------------
			// ���[�h����Ă���IOD�Ƀf�[�^��ݒ肷��B
			//-------------------------------------
			for (Iterator iter = iodSettingInfo.iterator(); iter.hasNext();) {
				PageInfo element = (PageInfo) iter.next();
				if (element != pageInfo) {
					webdocmem.setiddata(
						"iod:",
						element.getTemplateFile().getAbsolutePath());
				}

				/* �E�P�ŕ��̃f�[�^��ݒ肷�� */
				for (Iterator iterator = element.getFields().iterator();
					iterator.hasNext();
					) {
					FieldInfo field = (FieldInfo) iterator.next();
					String id = field.getName();
					String data = field.getValue();
					if (webdocmem.setiddata(id, data) < 0) {
						log.error(
							"webdocmem#setiddata �f�[�^�̎w��Ɏ��s���܂����B�G���[���b�Z�[�W��"
								+ webdocmem.getmess());
						throw new SystemException(webdocmem.getmess());
					}
				}

				/* �D���y�[�W */
				if (webdocmem.outpage() < 0) {
					log.error(
						"webdocmem#outpage ���y�[�W�Ɏ��s���܂����B�G���[���b�Z�[�W��"
							+ webdocmem.getmess());
					throw new SystemException(webdocmem.getmess());
				}

			}

			/* �f�[�^�ݒ�̏I��(iod �̃N���[�Y)  */
			if (webdocmem.outend() < 0) {
				log.error(
					"webdoc#outend IOD�̃t�@�C���̃N���[�Y�Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ webdocmem.getmess());
				throw new SystemException(webdocmem.getmess());
			}

			if (log.isDebugEnabled()) {
				log.debug(outIodFile + "���쐬���܂����B");
			}
		} finally {
			/* �C���X�^���X�̊J�� */
			webdocmem.release();
		}

		return outIodFile;
	}

	/**
	 * iod�t�@�C�����\�[�XOR pdf�t�@�C�����\�[�X���������A�p�X���[�h�tPDF���\�[�X���擾����B
	 * @param iodFileResources			��������t�@�C�����\�[�X�B
	 * @param password					PDF���J�����߂̃p�X���[�h�B	
	 * @return							��������PDF�t�@�C�����\�[�X
	 * @throws ConvertException			�t�@�C���̕ۑ��Ɏ��s�����Ƃ��ȂǁB
	 */
	public static File iodToPdf(List iodFileResources, String password)
		throws ConvertException {

		//-------------------------------------
		// �p�����[�^�`�F�b�N
		//-------------------------------------
		if (iodFileResources == null || iodFileResources.isEmpty()) {
			throw new IllegalArgumentException("PDF�ϊ�����iod�t�@�C�����w�肳��Ă��܂���B");
		}

		//-------------------------------------
		//�ϐ�������
		//-------------------------------------
		String tempName = getTempName();

		//��ƃt�H���_�̍쐬
		File workFolder = getWorkFolder(tempName);
		File outPdfFile = new File(workFolder, tempName + PDF);
		File logPdfFile = new File(workFolder, outPdfFile.getName() + LOG);

		//��������t�@�C���B
		File[] iodFiles = new File[iodFileResources.size()];

		//2005.07.14 iso PDF�Y�t�@�\�̎����ɂ��APDF������IOD��PDF�쐬��2��ނ��쐬�B
		//���f�[�^��IOD�Ŏc���Ă��邽�߁AIOD��PDF�쐬�@�\���c���B
//		//iod�t�@�C������������
//		int i = 0;
//		for (Iterator iter = iodFileResources.iterator(); iter.hasNext();) {
//			FileResource element = (FileResource) iter.next();
//			iodFiles[i] =
//				new File(workFolder, getTempName() + "-" + element.getName());
//			try {
//				if (!FileUtil.writeFile(iodFiles[i], element.getBinary())) {
//					throw new ConvertException(
//						"PDF�ϊ�����iod�t�@�C��'" + element.getPath() + "'�̏����ݎ��s���܂����B",new ErrorInfo("errors.8003"));
//				}
//			} catch (IOException e) {
//				throw new ConvertException(
//					"PDF�ϊ�����iod�t�@�C��'" + element.getPath() + "'�̏����ݎ��s���܂����B",new ErrorInfo("errors.8003"), e);
//			}
//			//�f�[�^�̊J���B
//			element.setBinary(null);
//			i++;
//		}
//
//		//�ϊ�	
//		iodToPdf(iodFiles, outPdfFile, logPdfFile, password);

		//iod�t�@�C������������
		int i = 0;
		String fileType = PDF;
		for (Iterator iter = iodFileResources.iterator(); iter.hasNext();) {
			FileResource element = (FileResource) iter.next();
			//�Y�t�t�@�C����IOD�ɕϊ����Ă���ꍇ�́AIOD��PDF�ϊ����s���B
			if(element.getName() != null && element.getName().endsWith(IOD)) {
				fileType = IOD;
			}
			iodFiles[i] =
				new File(workFolder, getTempName() + "-" + element.getName());
			try {
				if (!FileUtil.writeFile(iodFiles[i], element.getBinary())) {
					throw new ConvertException(
						"PDF�ϊ�����iod�t�@�C��'" + element.getPath() + "'�̏����ݎ��s���܂����B",new ErrorInfo("errors.8003"));
				}
			} catch (IOException e) {
				throw new ConvertException(
					"PDF�ϊ�����iod�t�@�C��'" + element.getPath() + "'�̏����ݎ��s���܂����B",new ErrorInfo("errors.8003"), e);
			}
			//�f�[�^�̊J���B
			element.setBinary(null);
			i++;
		}

		//�ϊ�
		if(fileType.equals(PDF)) {
			combPdf(iodFiles, outPdfFile, password);
		} else {
			iodToPdf(iodFiles, outPdfFile, logPdfFile, password);
		}

		//�쐬����pdf���擾
		return outPdfFile;

	}

	/**
	 * ��ƃt�H���_���擾����B
	 * @param tempName	
	 * @return			��ƃt�H���_
	 */
	private static File getWorkFolder(String tempName) {
		File workFolder =
			new File(
				WORK_PARENT_FOLDER
					+ File.separator
					+ tempName
					+ File.separator);
		workFolder.mkdirs();
		return workFolder;
	}

	/**
	 * IOCSVDOC�EIOCSVCELA�ō쐬���������̂h�n�c�t�@�C�����܂Ƃ߂Ăo�c�e�t�@�C���ɕϊ�����B
	 * @param iodFiles					��������t�@�C���B
	 * @param outPdfFile				�o��PDF�t�@�C��
	 * @param outPdfLogFile				�o��PDF���O�t�@�C��
	 * @param password					�p�X���[�h
	 */
	private static void iodToPdf(
		File[] iodFiles,
		File outPdfFile,
		File outPdfLogFile,
		String password) {

		/* IOWEBDOC�p���O�t�@�C���B	 */
		final String IoDocLogFilePath = outPdfLogFile.getAbsolutePath();

		iodtopdf iodtopdf = new iodtopdf();

		/* ���O�t�@�C���ݒ� */
		iodtopdf.setlog(IoDocLogFilePath);

		/* �o�c�e�o�͎��̈��k���@��ݒ� */
		iodtopdf.setcompress(iodoc.IODTOPDF_COMPRESS_DEFAULT); /*�f�t�H���g���k*/

		//2006.12.13 iso �p�X���[�h�Ȃ�PDF�̓Z�L�����e�B�����Z�b�g���Ȃ��悤�ύX
//		/* �Z�L�����e�B����ݒ� */
//		//����E�ҏW�E�]�ځE���ߒǉ�
//		if (iodtopdf
//		.setpdfsecurity(password, password,
//			 false	//����s��
//			,true	//�ҏW�s��
//		  	,true	//�]�ڕs��
//		    ,false)	//���ߕs��
//			< 0) {
//			if (log.isErrorEnabled()) {
//				log.error(
//					"iodtopdf#setpdfsecurity �Z�L�����e�B���̐ݒ�Ɏ��s���܂����B�G���[���b�Z�[�W��"
//						+ iodtopdf.getmess());
//			}
//			throw new SystemException(iodtopdf.getmess());
//		}
		if(!StringUtil.isBlank(password)) {
			/* �Z�L�����e�B����ݒ� */
			//����E�ҏW�E�]�ځE���ߒǉ�
			if (iodtopdf.setpdfsecurity(password, password,
				 false	//����s��
				,true	//�ҏW�s��
			  	,true	//�]�ڕs��
			    ,false)	//���ߕs��
				< 0) {
				if (log.isErrorEnabled()) {
					log.error(
						"iodtopdf#setpdfsecurity �Z�L�����e�B���̐ݒ�Ɏ��s���܂����B�G���[���b�Z�[�W��"
							+ iodtopdf.getmess());
				}
				throw new SystemException(iodtopdf.getmess());
			}
		}
		
		/* ��������ݒ� */
		if (iodtopdf.setpdfdocinf("�\����", "", "JSPS", "JSPS�d�q�\���V�X�e��")
			< 0) {
			if (log.isErrorEnabled()) {
				log.error(
					"iodtopdf#setpdfdocinf �o�c�e�̕������̐ݒ�Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ iodtopdf.getmess());
			}
			throw new SystemException(iodtopdf.getmess());
		}

		/* �o�c�e�t�@�C�����J�� */
		int status = iodtopdf.pdfopen(outPdfFile.getAbsolutePath());
		try {
			if (status < 0) {
				if (log.isErrorEnabled()) {
					log.error(
						"iodtopdf#pdfopen �o�͗p�ɂo�c�e���I�[�v���Ɏ��s���܂����B�G���[���b�Z�[�W��"
							+ iodtopdf.getmess()
							+ " log�t�@�C����"
							+ IoDocLogFilePath);
				}
				throw new SystemException(iodtopdf.getmess());
			}

			for (int i = 0; i < iodFiles.length; i++) {
				/* �w�肵���h�n�c��ϊ����āA�I�[�v���ς̂o�c�e�ɒǉ����܂��B*/
				if (iodtopdf.topdf(iodFiles[i].getAbsolutePath(), null) < 0) {
					if (log.isErrorEnabled()) {
						log.error(
							"iodtopdf#topdf "
								+ iodFiles[i]
								+ "�t�@�C���̂o�c�e�ϊ��o�͂Ɏ��s���܂����B�G���[���b�Z�[�W��"
								+ iodtopdf.getmess()
								+ " log�t�@�C����"
								+ IoDocLogFilePath);
					}
					throw new SystemException(iodtopdf.getmess());
				}
			}

			/* �I�[�v���ς̂o�c�e���N���[�Y���܂��B */
			if (iodtopdf.pdfclose() < 0) {
				log.error(
					"iodtopdf#pdfclose �I�[�v���ς̂o�c�e�̃N���[�Y�Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ iodtopdf.getmess()
						+ " log�t�@�C����"
						+ IoDocLogFilePath);
				throw new SystemException(iodtopdf.getmess());
			}

		} finally {
			/* �����ŕێ����Ă���n���h�����J�����܂��B */
			iodtopdf.release();
		}
	}

	/**
	 * PDF�t�@�C�����܂Ƃ߂�1�̃t�@�C���Ɍ�������B
	 * @param pdfFiles					��������t�@�C���B
	 * @param outPdfFile				�o��PDF�t�@�C��
	 * @param password					�p�X���[�h
	 */
	//2005.01.10 iso Tomcat��������̂ŋً}���
//	private static void combPdf(
	private static synchronized void combPdf(
			File[] iodFiles,
			File outPdfFile,
			String password)
			throws ConvertException {

		pdfcombine comb = new pdfcombine();

		/* �A���N���X�̏����� */
		if(comb.init() < 0) {
			if(log.isErrorEnabled()) {
				log.error("combPdf#init PDF�����ݒ�̏������Ŏ��s���܂����B�G���[���b�Z�[�W��"
					+ comb.geterror());
			}
			pmLog.error(comb.geterror());
			throw new ConvertException(comb.geterror());
		}
		
		/* Web�֍œK�� */
		if(comb.setfastwebview(true) < 0) {
			if(log.isErrorEnabled()) {
				log.error("combPdf#setfastwebview Web�œK���̐ݒ�Ŏ��s���܂����B�G���[���b�Z�[�W��"
					+ comb.geterror());
			}
			pmLog.error(comb.geterror());
			throw new ConvertException(comb.geterror());
		}
		
		/* PDF���͏��̐ݒ� */
		if(comb.setdocinfo("�\����", "", "JSPS", "JSPS�d�q�\���V�X�e��", "") < 0) {
			if(log.isErrorEnabled()) {
				log.error("combPdf#setdocinfo PDF���͏��̐ݒ�Ŏ��s���܂����B�G���[���b�Z�[�W��"
					+ comb.geterror());
			}
			pmLog.error(comb.geterror());
			throw new ConvertException(comb.geterror());
		}
		
		//2006.12.13 iso �p�X���[�h�Ȃ�PDF�̓Z�L�����e�B�����Z�b�g���Ȃ��悤�ύX
//		/* �Z�L�����e�B����ݒ� */
//		if(comb.setsecurity(
//				false	//�A�����̐擪�t�@�C���̐ݒ�͎g�p���Ȃ�
//				,password
//				,password
//				,false	//����s��
//				,true	//�ҏW�s��
//				,true	//�]�ڕs��
//				,false)	//���ߕs��
//				< 0) {
//			if(log.isErrorEnabled()) {
//				log.error("combPdf#setsecurity �Z�L�����e�B���̐ݒ�Ɏ��s���܂����B�G���[���b�Z�[�W��"
//					+ comb.geterror());
//			}
//			pmLog.error(comb.geterror());
//			throw new ConvertException(comb.geterror());
//		}
		/* �Z�L�����e�B����ݒ� */
		if(!StringUtil.isBlank(password)) {
			if(comb.setsecurity(
					false	//�A�����̐擪�t�@�C���̐ݒ�͎g�p���Ȃ�
					,password
					,password
					,false	//����s��
					,true	//�ҏW�s��
					,true	//�]�ڕs��
					,false)	//���ߕs��
					< 0) {
				if(log.isErrorEnabled()) {
					log.error("combPdf#setsecurity �Z�L�����e�B���̐ݒ�Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ comb.geterror());
				}
				pmLog.error(comb.geterror());
				throw new ConvertException(comb.geterror());
			}
		}
		

		/* PDF�t�@�C�����J�� */
		int status = comb.open(outPdfFile.getAbsolutePath());
		try {
			if(status < 0) {
				if(log.isErrorEnabled()) {
					log.error("combPdf#open �o�͗pPDF�̃I�[�v���Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ comb.geterror());
				}
				pmLog.error(comb.geterror());
				throw new ConvertException(comb.geterror());
			}

			for(int i = 0; i < iodFiles.length; i++) {
				/* �w�肵��PDF���I�[�v���ς�PDF�ɒǉ����܂��B*/
				if(comb.combine(iodFiles[i].getAbsolutePath()) < 0) {
					if(log.isErrorEnabled()) {
						log.error("combPdf#combine "
							+ iodFiles[i]
							+ "�A��PDF�t�@�C���̒ǉ������Ɏ��s���܂����B�G���[���b�Z�[�W��"
							+ comb.geterror());
					}
					pmLog.error(comb.geterror());
					throw new ConvertException(comb.geterror());
				}
			}

			/* PDF��A���B�y�јA����o��PDF���N���[�Y */
			if (comb.close() < 0) {
				if(log.isErrorEnabled()) {
					log.error("combPdf#close PDF�̘A���A�y�јA����o��PDF�̃N���[�Y�Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ comb.geterror());
				}
				pmLog.error(comb.geterror());
				throw new ConvertException(comb.geterror());
			}

		} finally {
			/* �����ŕێ����Ă���n���h�����J�����܂��B */
			comb.release();
		}
	}


	//2005.07.15 iso PDF�Y�t�@�\�ǉ�
	/**
	 * PDF�t�@�C�����L�����𒲂ׂ�B
	 * �����ȏꍇ�́A�G���[��Ԃ��B
	 * @param FileResource		��������t�@�C���B
	 * @return				�L���F0�@�����F�G���[�R�[�h
	 */
	//2005.01.10 iso Tomcat��������̂ŋً}���
//	public static int checkPdf(FileResource fileRes) throws ConvertException {
	public static synchronized int checkPdf(FileResource fileRes) throws ConvertException {

		pdfcombine comb = new pdfcombine();
		
		//�_�~�[�t�@�C��
		String tmpfile = WORK_PARENT_FOLDER + File.separator + "tmp.pdf";
		
		String filePath = WORK_PARENT_FOLDER + File.separator + getTempName() + PDF;
		File checkfile = new File(filePath);
		try {
			FileUtil.writeFile(checkfile, fileRes.getBinary());
		} catch (IOException e) {
			throw new ConvertException(
				"�`�F�b�N����t�@�C��'" + fileRes.getPath() + "'�̏����ݎ��s���܂����B",new ErrorInfo("errors.8003"), e);
		}

		/* �A���N���X�̏����� */
		if(comb.init() < 0) {
			if(log.isErrorEnabled()) {
				log.error("checkPdf#init PDF�����ݒ�̏������Ŏ��s���܂����B�G���[���b�Z�[�W��"
					+ comb.geterror());
			}
			pmLog.error(comb.geterror());
//			return comb.geterrorno();
			return getErrorNo(comb.geterror());
		}

		/* PDF�t�@�C�����J�� */
		int status = comb.open(tmpfile);
		try {
			if(status < 0) {
				if(log.isErrorEnabled()) {
					log.error("checkPdf#open �o�͗pPDF�̃I�[�v���Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ comb.geterror());
				}
				pmLog.error(comb.geterror());
//				return comb.geterrorno();
				return getErrorNo(comb.geterror());
			}
			
			if(comb.combine(filePath) < 0) {
				if(log.isErrorEnabled()) {
					log.error("checkPdf#combine "
						+ filePath
						+ "�A��PDF�t�@�C���̒ǉ������Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ comb.geterror());
				}
				pmLog.error(comb.geterror());
//				return comb.geterrorno();
				return getErrorNo(comb.geterror());
			}
				
			/* PDF��A���B�y�јA����o��PDF���N���[�Y */
			if (comb.close() < 0) {
				if(log.isErrorEnabled()) {
					log.error("checkPdfclose PDF�̘A���A�y�јA����o��PDF�̃N���[�Y�Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ comb.geterror());
				}
				pmLog.error(comb.geterror());
//				return comb.geterrorno();
				return getErrorNo(comb.geterror());
			}

		} finally {
			/* �����ŕێ����Ă���n���h�����J�����܂��B */
			comb.release();
			//�ꎞ�t�@�C���̍폜
			FileUtil.delete(checkfile);
		}
		return 0;
	}

	/**
	 * PDF Makeup�̃G���[���b�Z�[�W����A�G���[�R�[�h��Ԃ��B
	 * ��geterrorno()���\�b�h�Ƀo�O������̂ŁA�C�������܂ňꎞ�I�Ɏg�p����B
	 * @param errMsg		�G���[���b�Z�[�W�B
	 * @return	���ʃR�[�h
	 */
	private static int getErrorNo(String errMsg) {
		try {
			return Integer.parseInt(errMsg.substring(errMsg.indexOf("[")+1, errMsg.indexOf("]")));
		} catch(NumberFormatException e) {
			if(log.isErrorEnabled()) {
				log.error("PDF�ϊ��G���[�R�[�h�̎擾�Ɏ��s���܂����B");
			}
			return -1;
		}
	}


	/** �e���|�����[�t�@�C���h�c*/
	private static int tempId = 0;

	/** �e���|�����[�t�@�C�����b�N�I�u�W�F�N�g */
	private static Object lockTempName = new Object();

	/**
	 * �e���|�����[�t�@�C�������擾���܂�
	 * @return	�e���|�����[�t�@�C����
	 */
	public static String getTempName() {
		synchronized (lockTempName) {
			StringBuffer sb = new StringBuffer();
			SimpleDateFormat df =
				new SimpleDateFormat("yyyy-MM-dd,HH-mm-ss,SSS");
			sb.append(df.format(new Date()));

			//�R����ID�ɂ���B	
			if (tempId > 999)
				tempId = 0;
			String cntStr = "00" + Integer.toString(tempId++);
			sb.append("-" + cntStr.substring(cntStr.length() - 3));
			return sb.toString();
		}
	}
	
	
	/**
	 * PDF�̃y�[�W���`�F�b�N
	 */
	public static int checkPageNum(String filePath, String password) {
		
		pmusrc pmu = new pmusrc();
		pmu.init();
		pmu.openpdf(filePath, password);
		
		return  pmu.getpagecount();
		
	}
	
	/**
	 * �E�H�[�^�[�}�[�N�ǋL(PDF�̔w�i�ɕ������ǉ�)
	 * @param pdfFile�@�o�c�e�t�@�C��
	 * @param password�@�o�c�e�t�@�C���̃p�X���[�h
	 * @param outText�@�w�i������
	 * @return File    Null�̎��G���[�ANull�ȊO���V����PDF�t�@�C��
	 */
	public static File addWaterMark(File pdfFile, String password, String outText) 	{
		int sts;
		pmuobjwatermark objwater;
		int layer;
		int pos;
		int pagetype;
		File tempFile = null;

		try {
			// �o�͐�C���X�^���X�쐬 
			pmudst dstPdf = new pmudst();

			//������
			dstPdf.init();
			
			//����PDF�̑S�Ẵy�[�W���o�͂���
			sts = dstPdf.addsrcfile(pdfFile.getPath(), password);
			if (sts < 0 && log.isErrorEnabled()) {
				log.error("addWaterMark �o��PDF�t�@�C�����쐬�ł��Ȃ��B�G���[���b�Z�[�W��"
							+ dstPdf.geterror());
				
				//�������~
				throw new SystemException(dstPdf.geterror());
			}
			
			if(!StringUtil.isBlank(password)) {
				// �Z�L�����e�B����ݒ�. ����E�ҏW�E�]�ځE���ߒǉ�
				sts = dstPdf.setsecurity( password, password
										,false		//����s��
										,true		//�ҏW�s��
									  	,true		//�]�ڕs��
									    ,false)	;	//���ߕs��
					
				if (sts < 0 && log.isErrorEnabled()) {
					log.error("addWaterMark �Z�L�����e�B���̐ݒ�Ɏ��s���܂����B�G���[���b�Z�[�W��"
								+ dstPdf.geterror());
					
					//�������~
					throw new SystemException(dstPdf.geterror());
				}
			}
			
			//�E�H�[�^�[�}�[�N�𐶐�
			objwater = dstPdf.createobjwatermark();
	
			// pos = pmuobjwatermark.POS_XY ;/* �w�x���g�p */
			// pos = pmuobjwatermark.POS_LT ;/* ���� */
			// pos = pmuobjwatermark.POS_LM ;/* �����i */
			// pos = pmuobjwatermark.POS_LB ;/* ���� */
			// pos = pmuobjwatermark.POS_CT ;/* ������ */
			pos = pmuobjwatermark.POS_CM;/* �������i */
			// pos = pmuobjwatermark.POS_CB ;/* ������ */
			// pos = pmuobjwatermark.POS_RT ;/* �E�� */
			// pos = pmuobjwatermark.POS_RM ;/* �E���i */
			// pos = pmuobjwatermark.POS_RB ;/* �E�� */
			sts = objwater.setbasepos(pos);
	
			/* �ʒu����(��Βl) */
			sts = objwater.setpos(0, 0);
	
			/* �ʒu����(�ړ�) */
			sts = objwater.movepos(0, 0);
	
			/* �z�u(�O��) */
			//layer = pmuobjwatermark.LAYER_FRONT;
			layer = pmuobjwatermark.LAYER_BACK;
			sts = objwater.setlayer(layer);
	
			/* �y�[�W�ԍ��w�� */
			pagetype = pmuobjwatermark.PAGETYPE_ALL;
			//pagetype = pmuobjwatermark.PAGETYPE_PAGE;
			//pagetype = pmuobjwatermark.PAGETYPE_FROMTO;
			//pagetype = pmuobjwatermark.PAGETYPE_FROM;
			//pagetype = pmuobjwatermark.PAGETYPE_TO;
			if (pagetype == pmuobjwatermark.PAGETYPE_ALL) { /* �S�Ẵy�[�W */
				sts = objwater.settargetpage(pagetype, 0, 0);
			} else if (pagetype == pmuobjwatermark.PAGETYPE_PAGE) { /* �P��y�[�W */
				sts = objwater.settargetpage(pagetype, 1, 0);
				sts = objwater.settargetpage(pagetype, 2, 0);
			} else if (pagetype == pmuobjwatermark.PAGETYPE_FROMTO) { /* �y�[�W�͈� */
				sts = objwater.settargetpage(pagetype, 1, 2);
			} else if (pagetype == pmuobjwatermark.PAGETYPE_FROM) { /* ���̃y�[�W�ȍ~ */
				sts = objwater.settargetpage(pagetype, 3, 0);
			} else if (pagetype == pmuobjwatermark.PAGETYPE_TO) { /* ���̃y�[�W�� */
				sts = objwater.settargetpage(pagetype, 10, 0);
			}
	
			sts = objwater.setfontscale(120);
	
			sts = objwater.setfontrotate(45.0);
	
			/* �t�H���g */
			sts = objwater.setfont("�l�r ����");
	
			/* �����T�C�Y */
			sts = objwater.setfontsize(96.0);
	
			/* �����̐F */
			sts = objwater.setfontcolor(200, 200, 200);
	
			/* �������� */
			sts = objwater.setfontbold(true);
	
			/* �Α̕��� */
			sts = objwater.setfontitalic(true);
	
			sts = objwater.setstring(outText);

			//�ꎞ�t�@�C�������쐬
			String tempName = pdfFile.getParent() + File.separator + getTempName() + PDF;
			
			/* �ҏW��̂o�c�e���o�͂��� */
			sts = dstPdf.outputpdf(tempName);
			if (sts < 0){
				//�o�c�e�t�@�C���̏������݂͎��s����
				log.error("addWaterMark PDF�t�@�C���̏������݂Ɏ��s���܂����B�G���[���b�Z�[�W��"
						+ dstPdf.geterror());
			}else{
				//�V���Ȃo�c�e�t�@�C���𐶐�����
				tempFile = new File(tempName);
			}
			
			/* �o�͐�C���X�^���X�J�� */
			dstPdf.release();
		}catch (Exception e){
			//�������Ȃ�
		}

		return  tempFile;
	}
}
