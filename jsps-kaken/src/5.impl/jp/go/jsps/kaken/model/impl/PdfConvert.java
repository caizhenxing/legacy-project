/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : PdfConvert.java
 *    Description : PDF�ϊ����s���t�@�T�[�h�N���X�B
 *
 *    Author      : Admin
 *    Date        : 2003/01/10
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/01/10    v1.0        Admin          �V�K�쐬
 *    2006/07/19    v1.1        DIS.gongXB     �ύX
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.impl.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.pdf.webdoc.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.util.*;
import jp.go.jsps.kaken.web.util.DateFormat;
import jp.go.jsps.kaken.model.dao.impl.ShinseiDataInfoDao;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;

import org.apache.commons.logging.*;
import org.apache.velocity.*;
import org.apache.velocity.app.*;

/**
 * PDF�ϊ����s���t�@�T�[�h�N���X�B
 */
public class PdfConvert implements IPdfConvert {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	private static Log log = LogFactory.getLog(PdfConvert.class);

	/** ����ID���Ƃ̐ݒ��� */
	private static Map iodSettings = Collections.synchronizedMap(new HashMap());

    /** XML�ϊ��p�e���v���[�g */
    private static String xmlTemplate = null;  

//2006/07/03 iso �ύX ��������---------------------------------------------------------------------
	//2006.07.03 iso PDF�ϊ��T�[�o�U�蕪�������̂��ߒǉ�
//	/** �ϊ��T�[�oURL������ */
//	private static final String SERVER_URL = ApplicationSettings.getString(ISettingKeys.PDF_CONV_SERVLET_URL);

	/** �ϊ��T�[�oURL������ */
	private static final String SERVER_URLS[] = ApplicationSettings.getStrings(ISettingKeys.PDF_CONV_SERVLET_URL);

	/** �ϊ��T�[�oURL������ */
	private static final String SERVER_WEIGHTS[] = ApplicationSettings.getStrings(ISettingKeys.PDF_CONV_SERVLET_WEIGHTS);

	//2006.07.03 iso �Y�t�t�@�C���ϊ��T�[�o�U�蕪�������̂��ߒǉ�
	/** �Y�t�t�@�C���ϊ��T�[�oURL������ */
	private static final String ANNEX_SERVER_URLS[] = ApplicationSettings.getStrings(ISettingKeys.ANNEX_CONV_SERVLET_URL);

	/** �Y�t�t�@�C���ϊ��T�[�oURL������ */
	private static final String ANNEX_SERVER_WEIGHTS[] = ApplicationSettings.getStrings(ISettingKeys.ANNEX_CONV_SERVLET_WEIGHTS);
// 2006/07/03 iso �ύX �����܂�---------------------------------------------------------------------

	/** �ϊ��T�[�oURL������ */
	private static final String REPORT_SETTING_FILE_PATH =ApplicationSettings.getString(ISettingKeys.PDF_REPORT_SETTING_FILE_PATH);

	/** �\����PDF�t�@�C���i�[�t�H���_(�{�t�H���_�z���Ɂu����ID\�V�X�e����t�ԍ�\pdf�v�Ƒ���) */
	private static String SHINSEI_PDF_FOLDER = ApplicationSettings.getString(ISettingKeys.SHINSEI_PDF_FOLDER);		

    /** �\����XML�t�@�C���i�[�t�H���_ */
    private static String SHINSEI_XML_FOLDER = ApplicationSettings.getString(ISettingKeys.SHINSEI_XML_FOLDER);    
    
    /** ���b�N�p�I�u�W�F�N�g */
    private static Object lock = new Object();  

    /** DB�����N�� */
	private String dbLink = "";

    /** �f�[�^�ۊǃT�[�oUNC */
    protected static final String HOKAN_SERVER_UNC = ApplicationSettings.getString(ISettingKeys.HOKAN_SERVER_UNC);
   
    /** UNC�ɕϊ�����h���C�u���^�[ */
    protected static final String DRIVE_LETTER_CONVERTED_TO_UNC = ApplicationSettings.getString(ISettingKeys.DRIVE_LETTER_CONVERTED_TO_UNC);

	//2005/05/25 �ǉ� ��������---------------------------------------------------------------
	//���R�@�\��pdf�t�H���_�w��̂���
   	/** �\��PDF�t�@�C���i�[�t�H���_ */
   	private static String PDF_COVER = ApplicationSettings.getString(ISettingKeys.PDF_COVER);
   	
   	private static final String HYOSHI_PDF_TEMPLATE_NAME="hyoshi";
   	//�ǉ� �����܂�--------------------------------------------------------------------------

// 2006/06/27 dyh add start
    /** �̈�v�揑�\��PDF�t�@�C���i�[�t�H���_(�{�t�H���_�z���Ɂu����ID\���̈�ԍ�\pdf�v�Ƒ���) */
    private static String PDF_DOMAINCOVER = ApplicationSettings.getString(ISettingKeys.PDF_DOMAINCOVER);

    /** �̈�v�揑�\��PDF�t�@�C���̕ϊ��ݒ�e���v���[�g�� */
    private static final String HYOSHIRYOIKI_PDF_TEMPLATE_NAME = "hyoshiRyoiki";
// 2006/06/27 dyh add end

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------

    /**
     * �R���X�g���N�^�B
     */
    public PdfConvert() {
        super();
    }

    /**
     * �R���X�g���N�^�B
     * @param dbLink �f�[�^�x�[�X�����N��
     */
    public PdfConvert(String dbLink) {
        super();
		this.dbLink = dbLink;
    }

	//---------------------------------------------------------------------
	// implements IPdfConvert
	//---------------------------------------------------------------------

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.pdf.IPdfConvert#ShinseiDataConvert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
     * <b>�f�[�^�x�[�X�����N���Ή��B</b>
	 */
	public void shinseiDataConvert(UserInfo userInfo, ShinseiDataPk pkInfo)
		throws ApplicationException {

		Connection connection = null;
		boolean success = false;
		try {
			connection = DatabaseUtil.getConnection();

			//------------------
			//�\���f�[�^XML/PDF�ϊ�
			//------------------
			convertShinseiData(connection, userInfo, pkInfo);

			if (log.isDebugEnabled()) {
				log.debug("-->>�Y�t�t�@�C����ϊ����܂��B");
			}
			//------------------
			//�Y�t�t�@�C��PDF�ϊ�
			//------------------
// 2007/02/08 ���u�j�@�C�� ��������           
            //convertShinseiTenpuFile(connection, userInfo, pkInfo);
              /** �m�F�����ȊO�i[���֐i��]���j*/
            convertShinseiTenpuFile(connection, userInfo, pkInfo , false);
// 2007/02/08�@���u�j�@�C�� �����܂�	

			if (log.isDebugEnabled()) {
				log.debug("--<<�Y�t�t�@�C����ϊ����܂��B");
			}
			//------------------
			//�o�^����I��
			//------------------
			success = true;			
	
		} catch (DataAccessException e) {
			throw new ConvertException("�\���f�[�^���̎擾�Ɏ��s���܂����B",new ErrorInfo("errors.8003"),e);
		} catch (IOException e) {
			throw new ConvertException("�ϊ��t�@�C���̕ۑ��Ɏ��s���܂����B(�t�@�C��IO)",new ErrorInfo("errors.8003"),e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ConvertException("PDF�f�[�^�ϊ�DB�o�^���ɃG���[���������܂����B",e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.pdf.IPdfConvert#getShinseiFileResource(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public FileResource getShinseiFileResource(
		UserInfo userInfo,
		final ShinseiDataPk pkInfo)
		throws ApplicationException, NoDataFoundException, ConvertException {
		return getShinseiFileResource(userInfo, pkInfo, true);
	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.IPdfConvert#getShinseiFileResourceWithoutLock(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public FileResource getShinseiFileResourceWithoutLock(
		UserInfo userInfo,
		ShinseiDataPk pkInfo)
		throws ApplicationException, NoDataFoundException, ConvertException{
			return getShinseiFileResource(userInfo, pkInfo, false);
	}	

	//---------------------------------------------------------------------
	// methods
	//---------------------------------------------------------------------	
	
	/**
	 * �\���f�[�^���PDF�t�@�C�����쐬����B
	 * lockFlag��true�̂Ƃ��A�쐬���ꂽPDF�t�@�C���Ƀp�X���[�h���b�N��������B
	 * @param userInfo
	 * @param pkInfo
	 * @param lockFlag
	 * @return
	 * @throws ApplicationException
	 * @throws NoDataFoundException
	 * @throws ConvertException
	 */
	public FileResource getShinseiFileResource(
		UserInfo userInfo,
		final ShinseiDataPk pkInfo,
		boolean lockFlag)
		throws ApplicationException, NoDataFoundException, ConvertException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//�\���f�[�^�Ǘ�DAO�@2007/6/15 �ǉ�
            ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo, dbLink);
			
			//----------------------------
			//��������t�@�C���̎擾
			//----------------------------
			List iodFiles  = null;
			try{
				//2007/6/15 �C��
				//iodFiles = new ShinseiDataInfoDao(userInfo,dbLink).getIodFilesToMerge(connection,pkInfo);
				iodFiles = shinseiDao.getIodFilesToMerge(connection,pkInfo);
				//2007/6/15�@�C������
			}catch(NoDataFoundException e){
				try{
					//������iod�t�@�C���p�X�����݂��Ȃ������ꍇ�͍ēx�ϊ������������A���̏�ōĎ��s���s���i�P�񂾂��j
					shinseiDataConvert(userInfo, pkInfo);
					//2007/6/15 �C��
					//iodFiles = new ShinseiDataInfoDao(userInfo,dbLink).getIodFilesToMerge(connection,pkInfo);
					iodFiles = shinseiDao.getIodFilesToMerge(connection,pkInfo);
					//2007/6/15�@�C������
				}catch(Exception ex){
					//����ł����s�����ꍇ�͏�ʑ��ɗ�O�𓊂���
					throw e;
				}
			}
			List iodFileResource = new ArrayList();

			//----------------------------
            //DB�����N���l�����ăt�@�C���Ǎ���
			//----------------------------
			for (Iterator iter = iodFiles.iterator(); iter.hasNext();) {
				File element = (File) iter.next();
                File targetFile = null;
                if (dbLink == null || dbLink.length() == 0) {
                    //�ʏ�
                    targetFile = element;
				} else {
					targetFile =
						new File(StringUtil.substrReplace(element.getAbsolutePath(),DRIVE_LETTER_CONVERTED_TO_UNC,HOKAN_SERVER_UNC));
					//�p�X�������UNC�`���ɕϊ�����
                    if(log.isDebugEnabled()){
						log.debug("dbLink�o�R�̂��߁A'"
								+ element
								+ "'��'"
								+ targetFile
								+ "'�t�@�C����ǂݍ��݂܂��B");
                    }
                }
                iodFileResource.add(readFile(targetFile));
 			}

			//----------------------------
			//�p�X���[�h
			//----------------------------
			String password = null;
			if(lockFlag){
				password = getPassword(connection,userInfo);
			}

			if(log.isDebugEnabled()){
				log.debug("--->>PDF���쐬���܂��B");
			}

			//----------------------------
			//�ϊ�
			//----------------------------
			//2006.07.03 iso PDF�ϊ��T�[�o�U�蕪�������̂��ߒǉ�
//			ISystemServise servise =
//				SystemServiceFactory.getSystemService(
//					IServiceName.CONVERT_SERVICE,
//					SERVER_URL);
//			try{
//				return servise.iodToPdf(iodFileResource,password);
//			}finally{
//				if (log.isDebugEnabled()) {
//					log.debug("---<<PDF���쐬���܂����B");
//				}
//			}

// 2006/07/19 dyh update start �����F���ʕ��@���쐬
//			try {
//				String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
//				for(int i=0; i < urls.length; i++) {
//					ISystemServise servise =
//						SystemServiceFactory.getSystemService(
//							IServiceName.CONVERT_SERVICE,
//							urls[i]);
//					try{
//						FileResource fileResource = servise.iodToPdf(iodFileResource,password);
//						if (log.isDebugEnabled()) {
//							log.debug("---<<PDF���쐬���܂����B");
//						}
//						return fileResource;
//					} catch (Exception e) {
//						log.info("PDF�ϊ��T�[�o�ŃG���[���������܂����BURL:" + urls[i], e);
//					}
//				}
//				//�S�T�[�o���̃��[�v���I����Ă�fileResource���A��Ȃ��ꍇ�A
//				//�ڑ��G���[�A�ϊ��G���[�̗������l�����邪�AConvertException�Ƃ���B
//				throw new ConvertException("�S�Ă�PDF�ϊ��T�[�o�ŏ����Ɏ��s���܂����B");
//			} catch(IllegalArgumentException e) {
//				throw new SystemException("PDF�ϊ��T�[�oURL�̎擾�Ɏ��s���܂����B");
//			}
			//2007/6/15 �C��
            //return getPdfFileResource(iodFileResource, password);

			ShinseiDataInfo shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection, pkInfo, false);
            String jokyoId = shinseiDataInfo.getJokyoId();// �\����ID
            String jigyoKbn = shinseiDataInfo.getKadaiInfo().getJigyoKubun();// ����ID
			
            return getPdfFileResource(iodFileResource, password, jokyoId, jigyoKbn);
			//2007/6/15 �C������
            
// 2006/07/19 dyh update start
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"PDF�t�@�C���̎擾�Ɏ��s���܂����B", new ErrorInfo("errors.8003"),e);
		} finally{
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * ���[�U�̃p�X���[�h���擾����B
	 * @param userInfo
	 * @return
	 */
	private String getPassword(Connection connection ,UserInfo userInfo)
            throws NoDataFoundException, DataAccessException {

		if(userInfo.getRole().equals(UserRole.SHINSEISHA)){
			//�\���҂̂Ƃ�
			ShinseishaInfo info = new ShinseishaInfoDao(userInfo)
                .selectShinseishaInfo(connection,userInfo.getShinseishaInfo());
			return info.getPassword();
		}else if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
			//�����@�֒S���҂̂Ƃ�
			ShozokuInfo info = new ShozokuInfoDao(userInfo)
                .selectShozokuInfo(connection,userInfo.getShozokuInfo());	
			return info.getPassword();
		}else if(userInfo.getRole().equals(UserRole.SHINSAIN)){
			//�R�����̂Ƃ�
            ShinsainInfo info = new ShinsainInfoDao(userInfo)
                .selectShinsainInfo(connection,userInfo.getShinsainInfo());    
            return info.getPassword();
		}else if(userInfo.getRole().equals(UserRole.GYOMUTANTO)
                ||userInfo.getRole().equals(UserRole.SYSTEM)){
			//�Ɩ��S���ҁE�V�X�e���Ǘ��҂̂Ƃ�
			GyomutantoInfo info = new GyomutantoInfoDao(userInfo)
                .selectGyomutantoInfo(connection,userInfo.getGyomutantoInfo());	
			return info.getPassword();
        //2005/04/11 �ǉ� ��������------------------------------------------------------------
        //���R ���ǒS���҂̏ꍇ�̃p�X���[�h�擾�̂���
		}else if(userInfo.getRole().equals(UserRole.BUKYOKUTANTO)){
			BukyokutantoInfo info = new BukyokutantoInfoDao(userInfo)
                .selectBukyokutantoInfo(connection, userInfo.getBukyokutantoInfo());
			return info.getPassword();
		//�ǉ� �����܂�
		}else{
			throw new SystemException("���[�U�����ł��܂���B");
		}
	}

	/**
	 * �\���f�[�^�����擾���A�\���f�[�^��PDF�ɕϊ����A�ۑ��t�@�C���p�X��DB�ɏ������ށB
 	 * @param connection
	 * @param userInfo
	 * @param pkInfo
	 * @throws ApplicationException		�ϊ��Ɏ��s�����Ƃ��B
	 * @throws DataAccessException		
	 * @throws IOException
	 */
	private void convertShinseiData(
            Connection connection,
            UserInfo userInfo,
            ShinseiDataPk pkInfo)
            throws ApplicationException, DataAccessException, IOException {
		
		//----------------------------
		//�\���f�[�^���̎擾
		//----------------------------
		ShinseiDataInfo shinseiDataInfo = null;
		try{
			shinseiDataInfo = new ShinseiMaintenance().selectShinseiDataInfo(userInfo, pkInfo);
		}catch(ApplicationException e){
			throw new ConvertException("�\���f�[�^�̎擾�Ɏ��s���܂����B(�f�[�^�A�N�Z�X)",new ErrorInfo("errors.8003"),e);
		}
		
		//2005/04/19 �ǉ� ------------------------------��������
		//���R ��茤���̏ꍇ�́A�a���������擾����K�v������B
//		if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(shinseiDataInfo.getJigyoCd())
//				||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(shinseiDataInfo.getJigyoCd())){
        // 2006/02/14 �ǉ�����
		// ���R�@��茤���A���X�^�[�g�A�b�v�A���ʌ������i��̎�茤���iAB�j�̏ꍇ�́A�a���������擾����K�v����B
		if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_WAKATESTART.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(shinseiDataInfo.getJigyoCd())
//2007/02/03 �c�@�ǉ���������
                ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(shinseiDataInfo.getJigyoCd())
//2007/02/03 �c�@�ǉ������܂�
//2007/03/13 �����F�@�폜�@��������
//                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(shinseiDataInfo.getJigyoCd())
//                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(shinseiDataInfo.getJigyoCd())
//                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(shinseiDataInfo.getJigyoCd())
//2007/03/13 �����F�@�폜�@�����܂�
        ){
			ShinseishaPk pk= new ShinseishaPk();
			pk.setShinseishaId(shinseiDataInfo.getShinseishaId());
			ShinseishaInfo shinseishaInfo=new ShinseishaInfoDao(userInfo).selectShinseishaInfo(connection,pk);
			shinseiDataInfo.setBirthDay(shinseishaInfo.getBirthday());
		}
		//2005/04/19 �ǉ� ------------------------------�����܂�
		
		//----------------------------
		//�ۑ��t�@�C���̍쐬(PDF)
		//----------------------------
		File iodFile = new File(MessageFormat.format(SHINSEI_PDF_FOLDER,
                new Object[] {shinseiDataInfo.getJigyoId(),shinseiDataInfo.getSystemNo()}));
		if(log.isDebugEnabled()){
			log.debug("�\���f�[�^PDF�ϊ��t�@�C����'" + iodFile + "'�ł��B");
		}

        //----------------------------
        //�ۑ��t�@�C���̍쐬(XML)
        //----------------------------
        File xmlFile = new File(MessageFormat.format(SHINSEI_XML_FOLDER,
                new Object[] {shinseiDataInfo.getJigyoId(),shinseiDataInfo.getSystemNo()}));
        if(log.isDebugEnabled()){
            log.debug("�\���f�[�^XML�ϊ��t�@�C����'" + xmlFile + "'�ł��B");
        }
        		
		//----------------------------
		//DB�X�V
		//----------------------------
        new ShinseiDataInfoDao(userInfo).updateFilePath(connection, pkInfo, iodFile ,xmlFile);
		
		//----------------------------
		//�\���f�[�^���̕ϊ�(PDF)
		//----------------------------
// 2006/07/19 dyh update start
//		FileResource iodFileResource = makeIodFormShinseiData(connection ,shinseiDataInfo);
        FileResource iodFileResource = makeIodFormShinseiData(shinseiDataInfo);
// 2006/07/19 dyh update end
		
		//----------------------------
		//PDF�t�@�C������(����X�V���邽�ߏ㏑���̂�)
		//----------------------------
		FileUtil.writeFile(iodFile,iodFileResource.getBinary());
        iodFileResource = null;

		//----------------------------
		//�\���f�[�^����XML�ϊ�����
		//----------------------------
		String xmlString = makeXmlFormShinseiData(shinseiDataInfo);

//		if (log.isDebugEnabled()) {
//			log.debug("XML�̕\��\n" + xmlString);
//		}

		//----------------------------
		//XML�t�@�C��UTF-8�ŏ���(����X�V���邽�ߏ㏑���̂�)
		//----------------------------
		FileUtil.writeFile(xmlFile, xmlString.getBytes("UTF-8"));

	}
// 2007/02/08 ���u�j�@�ǉ���������
    /**
     *�m�F��������shinseiDataConvertForConfirm���\�b�h�ł́A
     *  �uconvertShinseiTenpuFile�v�ł͂Ȃ��A�uconvertShinseiTenpuFileForConfirm�v���Ăяo���悤�ύX�B
     * @param connection
     * @param userInfo
     * @param pkInfo
     * @throws DataAccessException
     * @throws ApplicationException
     * @throws IOException
    */
    private void convertShinseiTenpuFileForConfirm(
            Connection connection, UserInfo userInfo, ShinseiDataPk pkInfo)
            throws DataAccessException,  ApplicationException, IOException {
           convertShinseiTenpuFile(connection, userInfo, pkInfo, true);
          }
// 2007/02/08�@���u�j�@�ǉ������܂�

	/**
	 * �\���f�[�^�����A�Y�t�t�@�C�������擾���A�Y�t�t�@�C����PDF�ɕϊ����A�ۑ��t�@�C���p�X��DB�ɏ������ށB
	 * @param connection
	 * @param userInfo
	 * @param pkInfo
     * @param confirmFlg  false�F�m�F�����ȊO�i[���֐i��]���j true�F�m�F������
	 * @throws DataAccessException
	 * @throws ApplicationException
	 * @throws IOException
	 */
	private void convertShinseiTenpuFile(
		Connection connection,
		UserInfo userInfo,
		ShinseiDataPk pkInfo,
        boolean confirmFlg)
		throws
			DataAccessException,
			ApplicationException,
			IOException {
		try{
			//----------------------------
			//�\���f�[�^�̓Y�t�t�@�C�����̎擾
			//----------------------------
			TenpuFileInfoDao dao = new TenpuFileInfoDao(userInfo);
			TenpuFileInfo[] fileInfos = dao.selectTenpuFileInfos(connection,pkInfo);
			
			for (int i = 0; i < fileInfos.length; i++) {
				//----------------------------
				//�ϊ�����Ă��Ȃ��Ƃ���
				//----------------------------
				if(!isConverted(fileInfos[i])){
					
					//----------------------------
					//�ϊ�����B						
					//----------------------------
					File wordFile = new File(fileInfos[i].getTenpuPath());
					FileResource pdfFileResource = annexFileConvert(readFile(wordFile));
					
					//----------------------------
					//�Y�t�t�H���_�Ƀt�@�C������
					//----------------------------
					File pdfFile = new File(wordFile.getParentFile(),pdfFileResource.getName());
					fileInfos[i].setPdfPath(pdfFile.getAbsolutePath());
					FileUtil.writeFile(pdfFile,pdfFileResource.getBinary());
					
					//----------------------------
					//DB�X�V
					//----------------------------
					dao.updateTenpuFileInfo(connection,fileInfos[i]);
				}

//2007/02/14�@�c�@�ǉ���������
                //�m�F������                
                if (confirmFlg) {
                    //�y�[�W�����擾����
                    int pageNum = WebdocUtil.checkPageNum(fileInfos[i].getPdfPath(), 
                            userInfo.getShinseishaInfo().getPassword());
                    
                    //���Ə��e�[�u���Ƀy�[�W���͈͂��擾
                    JigyoKanriInfo jigyoKanriInfo = new JigyoKanriInfo();                   
                    jigyoKanriInfo.setJigyoId(fileInfos[i].getJigyoId());
                    
                    JigyoKanriInfoDao jigyoKanriInfoDao = new JigyoKanriInfoDao(userInfo);                                   
                    jigyoKanriInfo = jigyoKanriInfoDao.selectJigyoKanriInfo(connection,jigyoKanriInfo);
                    
                    //�G���[���b�Z�[�W
                    String errorMessage = "";
                    //�y�[�W���͈͂��S���œ���
                    if (!StringUtil.isBlank(jigyoKanriInfo.getPageTo())
                            && !StringUtil.isBlank(jigyoKanriInfo.getPageFrom())) {
                        //�y�[�W�̏���Ɖ������ݒ肳��Ă��āA��� == �����̎�
                        if (StringUtil.parseInt(jigyoKanriInfo.getPageFrom()) 
                                == StringUtil.parseInt(jigyoKanriInfo.getPageTo())) {
                            errorMessage = jigyoKanriInfo.getPageFrom() + "�y�[�W";
                        //�y�[�W�̏���Ɖ������ݒ肳��Ă��āA��� != �����̎�    
                        } else {
                            errorMessage = jigyoKanriInfo.getPageFrom() + "�y�[�W�ȏ�A"
                                                    + jigyoKanriInfo.getPageTo() + "�y�[�W�ȉ�";
                        }
                    }
                    //�y�[�W�̉����̂ݐݒ肳��Ă�i����͋�j�̏ꍇ
                    else if (!StringUtil.isBlank(jigyoKanriInfo.getPageFrom())
                            && StringUtil.isBlank(jigyoKanriInfo.getPageTo())) {
                        errorMessage = jigyoKanriInfo.getPageFrom() + "�y�[�W�ȏ�";
                    }
                    //�y�[�W�̏���̂ݐݒ肳��Ă�i�����͋�j�̏ꍇ
                    else if (!StringUtil.isBlank(jigyoKanriInfo.getPageTo())
                            && StringUtil.isBlank(jigyoKanriInfo.getPageFrom())) {
                        errorMessage = jigyoKanriInfo.getPageTo() + "�y�[�W�ȉ�";
                    }

                    //�y�[�W���͈͉��������͂�����APDF�y�[�W�����y�[�W���͈͉����̏ꍇ�̓G���[�Ƃ���
                    if (!StringUtil.isBlank(jigyoKanriInfo.getPageFrom())
                            && pageNum < StringUtil.parseInt(jigyoKanriInfo.getPageFrom())) {
                        throw new ApplicationException(
                                "PDF�y�[�W���`�F�b�N�ɃG���[���������܂����B", new ErrorInfo(
                                        "errors.9031", new String[] { errorMessage }));
                    }
                    //�y�[�W���͈͏�������͂�����APDF�y�[�W�����y�[�W���͈͏���̏ꍇ�̓G���[�Ƃ���
                    if (!StringUtil.isBlank(jigyoKanriInfo.getPageTo())
                            && pageNum > StringUtil.parseInt(jigyoKanriInfo.getPageTo())) {
                        throw new ApplicationException(
                                "PDF�y�[�W���`�F�b�N�ɃG���[���������܂����B", new ErrorInfo(
                                        "errors.9031", new String[] { errorMessage }));
                    }
                }
//2007/02/14�@�c�@�ǉ������܂�
			}
		}catch(NoDataFoundException e){
			if(log.isDebugEnabled()){
				log.debug("�Y�t�t�@�C��������܂���B",e);
			}
		}catch(ConvertException e){
			//PDF�ϊ������̃^�C���A�E�g�̏ꍇ�́A�A���[�g���[���𑗐M����B
			if("errors.8001".equals(e.getErrorCode())){
				//�A���[�g�p�Ȃ̂Ńx�^�����B
				String SMTP_SERVER_ADDRESS = ApplicationSettings.getString(ISettingKeys.SMTP_SERVER_ADDRESS);
				String FROM_ADDRESS = ApplicationSettings.getString(ISettingKeys.FROM_ADDRESS);
				String to = ApplicationSettings.getString(ISettingKeys.TO_ADDRESS_FOR_ALERT);;
				String subject = "�y���{�w�p�U����d�q�\���V�X�e���z�A���[�g�ʒm";
				String content = "PDF�ϊ������Ń^�C���A�E�g���������܂����B\n"
								+"�E���[�UID�F"+userInfo.getId()+"\n"
								+"�E�\�����i�V�X�e����t�ԍ��j�F"+pkInfo.getSystemNo()+"\n"
								+"�E���������F"+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date())+"\n"
								;
				//-----���[�����M
				try{
					SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
					mailer.sendMail(FROM_ADDRESS,			//���o�l
									to,						//to
									null,					//cc
									null,					//bcc
									subject,				//����
									content);				//�{��
				}catch(Exception ex){
					log.warn("���[�����M�Ɏ��s���܂����B",ex);
					return;
				}
			}
			throw e;
		}		
	}
	
	/**
	 * �ϊ�����Ă��邩�`�F�b�N����B
	 * @param fileInfos				�Y�t�t�@�C�����B
	 * @return						true �ϊ�����Ă��� false �ϊ�����Ă��Ȃ��B
	 * @throws DataAccessException	�Y�t�t�@�C�����ݒ肳��Ă��Ȃ��Ƃ��A�܂��́A���݂��Ȃ��Ƃ��B
	 */
	private boolean isConverted(TenpuFileInfo fileInfos) throws ConvertException{
		//�Y�t�t�@�C���̃`�F�b�N

		//2005.07.14 iso PDF�t�@�C���Y�t�@�\
		//PDF�t�@�C�������ړY�t����PDF�p�X��񂪑��݂���ꍇ�́A������true��Ԃ��悤�ύX�B
		if(fileInfos.getPdfPath() != null && !fileInfos.getPdfPath().equals("")) {
			return true;
		} else {
			String wordFilePath = fileInfos.getTenpuPath();
			if (wordFilePath == null || wordFilePath.length() == 0) {
				throw new ConvertException("�Y�t�t�@�C�����ݒ肳��Ă��܂���B"
						+ "�V�X�e����t�ԍ�'"
						+ fileInfos.getSystemNo()
						+ "' �F�V�[�P���X�ԍ�'"
						+ fileInfos.getSeqTenpu()
						+ "'",new ErrorInfo("errors.8003"));
			}
			
			File wordFile = new File(wordFilePath);
			if(!wordFile.exists()){
				throw new ConvertException("�Y�t�t�@�C��'" + wordFile + "'��������܂���B"
						+ "�V�X�e����t�ԍ�'"
						+ fileInfos.getSystemNo()
						+ "' �F�V�[�P���X�ԍ�'"
						+ fileInfos.getSeqTenpu()
						+ "'",new ErrorInfo("errors.8003"));
			}
	
			//�ϊ��t�@�C�������݂��Ȃ��ꍇ�B
			if (fileInfos.getPdfPath() == null
				|| fileInfos.getPdfPath().length() == 0 ) {
				return false;
			}
			return true;
		}
	}

    /**
     * �\���f�[�^�����XML�t�@�C�����쐬����B
     * @param shinseiDataInfo       �\���f�[�^�I�u�W�F�N�g
     * @return                      XML������
     * @throws ApplicationException 
     */
// 2006/07/19 dyh update start
//	private String makeXmlFormShinseiData(ShinseiDataInfo shinseiDataInfo)
//		    throws ApplicationException {
    private String makeXmlFormShinseiData(ShinseiDataInfo shinseiDataInfo){
// 2006/07/19 dyh update end
		synchronized (lock) {
			//�e���v���[�g���̊m�F            
			if (xmlTemplate == null) {
				//�e���v���[�g�̎擾
				File xmlTemplateFile =
					ApplicationSettings.getFile(
						ISettingKeys.SHINSEI_XML_TEMPLATE);
				xmlTemplate = readSettingFile(xmlTemplateFile);
			}
			//�e���v���[�g��\���f�[�^���g�p���ĕϊ�����B
			return merge(xmlTemplate, shinseiDataInfo);
		}
	}
    
	/**
	 * �\���f�[�^�����pdf�t�@�C�����쐬����B
     * @param connection
	 * @param shinseiDataInfo		�\���f�[�^�I�u�W�F�N�g
	 * @return						pdf�t�@�C��
	 * @throws ApplicationException	
	 * @throws ConvertException		pdf�t�@�C���쐬���ɗ�O�����������ꍇ�B
	 */
// 2006/07/19 dyh update start
//	private FileResource makeIodFormShinseiData(Connection connection,ShinseiDataInfo shinseiDataInfo)
    private FileResource makeIodFormShinseiData(ShinseiDataInfo shinseiDataInfo)
// 2006/07/19 dyh update end
		throws ApplicationException {

		//�l�����
		String jigyoKubun = ShinseiFormat.getShinseiShubetu(shinseiDataInfo.getJigyoId());
		
		//���Ƌ�ɊY������ݒ�����擾����
		String template = getSettingTemplate(jigyoKubun);

		//�e���v���[�g��\���f�[�^���g�p���ĕϊ�����B
		String iodSetting = merge(template, shinseiDataInfo);

        //########## DEBUG ##########       
//        if(log.isDebugEnabled()){
//            log.debug("PDF�o�͗pXML '" + iodSetting + "'");
//        }

		//�ݒ�t�@�C���̓Ǎ���
		IodSettings settings = new IodSettings(new StringReader(iodSetting));

		//�ݒ���I�u�W�F�N�g�̍쐬
		List iodSettingInfo = settings.getContents();

        if (log.isDebugEnabled()) {
            log.debug(
            	//2005.07.14 iso PDF�ϊ��ɕύX
//                ">>IOD�t�@�C���쐬�T�[�r�X���Ăяo���܂��B");
				">>IOD�t�@�C���쐬�T�[�r�X���Ăяo���܂��B");
        }

		//PDF�t�@�C���쐬�T�[�r�X�Ăяo��
		//2006.07.03 iso PDF�ϊ��T�[�o�U�蕪�������̂��ߒǉ�
//		ISystemServise servise =
//			SystemServiceFactory.getSystemService(
//				IServiceName.CONVERT_SERVICE,
//				SERVER_URL);
//		FileResource iodFileResource = servise.iodFileCreation(iodSettingInfo);
//
//		//########## DEBUG ##########		
//		if (log.isDebugEnabled()) {
//			log.debug(
//				//2005.07.14 iso PDF�ϊ��ɕύX
////				">>�쐬IOD�t�@�C���� '"
//				">>�쐬IOD�t�@�C���� '"
//					+ iodFileResource.getName()
//					+ " �T�C�Y '"
//					+ iodFileResource.getBinary().length
//					+ "'");
//		}
//      return iodFileResource;

// 2006/07/19 dyh update start------------------------
		try {
			String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
//			for(int i=0; i < urls.length; i++) {
//				ISystemServise servise =
//					SystemServiceFactory.getSystemService(
//						IServiceName.CONVERT_SERVICE,
//						urls[i]);
//				try{
//					FileResource iodFileResource = servise.iodFileCreation(iodSettingInfo);
//					if (log.isDebugEnabled()) {
//						log.debug(
//						">>�쐬IOD�t�@�C���� '"
//							+ iodFileResource.getName()
//							+ " �T�C�Y '"
//							+ iodFileResource.getBinary().length
//							+ "'");
//					}
//					return iodFileResource;
//				} catch (Exception e) {
//					log.info("PDF�ϊ��T�[�o�ŃG���[���������܂����BURL:" + urls[i], e);
//				}
//			}
//			//�S�T�[�o���̃��[�v���I����Ă�iodFileResourcee���擾�ł��Ȃ��ꍇ�A
//			//�ڑ��G���[�A�ϊ��G���[�̗������l�����邪�AConvertException�Ƃ���B
//			throw new ConvertException("�S�Ă�PDF�ϊ��T�[�o�ŏ����Ɏ��s���܂����B");

            return getIodFileResourceShinsei(urls, iodSettingInfo);
		} catch(IllegalArgumentException e) {
			throw new SystemException("PDF�ϊ��T�[�oURL�̎擾�Ɏ��s���܂����B");
		}
// 2006/07/19 dyh update end------------------------
	}

	/**
	 * �Y�t�t�@�C�����pdf�t�@�C�����쐬����B
	 * @param annexFileResource		�Y�t�t�@�C�����\�[�X
	 * @return						pdf�t�@�C�����\�[�X					
	 * @throws ApplicationException
	 * @throws ConvertException
	 */
	private FileResource annexFileConvert(FileResource annexFileResource)
		throws ApplicationException, ConvertException {

		if(log.isDebugEnabled()){
			log.debug("\t--->>>�ϊ��������J�n���܂��B");
		}

		//PDF�t�@�C���쐬�T�[�r�X�Ăяo��
		//2006.07.03 iso PDF�ϊ��T�[�o�U�蕪�������̂��ߒǉ�
//		ISystemServise servise =
//			SystemServiceFactory.getSystemService(
//				IServiceName.CONVERT_SERVICE,
//				SERVER_URL);
//		FileResource iodFileResource =
//			servise.iodFileCreation(annexFileResource);
//
//		//�擾�t�@�C���̃f�o�b�O�\��
//		if (log.isDebugEnabled()) {
//			log.debug(
//				//PDF�ϊ��ɕύX
////				"�ϊ���IOD�t�@�C���� '"
//				"�ϊ���IOD�t�@�C���� '"
//					+ iodFileResource.getName()
//					+ " �T�C�Y '"
//					+ iodFileResource.getBinary().length
//					+ "'");
//		}
//
//		if (log.isDebugEnabled()) {
//			log.debug("\t---<<<�ϊ��������I���܂����B");
//		}
//      return iodFileResource;
		try {
			String[] urls;
			if(ANNEX_SERVER_URLS.length != 0) {
				//�Y�t�t�@�C���ϊ��T�[�o�̂�URL�I����Ɨ����������ꍇ�AANNEX_SERVER_URLS��URL��ݒ肷��B
				urls = getSortedUrls(ANNEX_SERVER_URLS, ANNEX_SERVER_WEIGHTS);
			} else {
				//ANNEX_SERVER_URLS����Ȃ�A�Y�t�t�@�C���ϊ��T�[�o��PDF�ϊ��T�[�o�Ɠ����ɂȂ�B
				urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
			}
			for(int i=0; i < urls.length; i++) {
				ISystemServise servise =
					SystemServiceFactory.getSystemService(
						IServiceName.CONVERT_SERVICE,
						urls[i]);
				try{
					FileResource iodFileResource = servise.iodFileCreation(annexFileResource);
					if (log.isDebugEnabled()) {
						log.debug(
							"�ϊ���IOD�t�@�C���� '"
								+ iodFileResource.getName()
								+ " �T�C�Y '"
								+ iodFileResource.getBinary().length
								+ "'");
						log.debug("\t---<<<�ϊ��������I���܂����B");
					}
					return iodFileResource;
				} catch (Exception e) {
					log.info("PDF�ϊ��T�[�o�ŃG���[���������܂����BURL:" + urls[i], e);
				}
			}
			//�S�T�[�o���̃��[�v���I����Ă�iodFileResourcee���擾�ł��Ȃ��ꍇ�A
			//�ڑ��G���[�A�ϊ��G���[�̗������l�����邪�AConvertException�Ƃ���B
			throw new ConvertException("�S�Ă�PDF�ϊ��T�[�o�ŏ����Ɏ��s���܂����B");
		} catch(IllegalArgumentException e) {
			throw new SystemException("PDF�ϊ��T�[�oURL�̎擾�Ɏ��s���܂����B");
		}
	}

	/**
	 * ���Ƌ敪���APDF�o�͂ɕK�v�Ȑݒ�e���v���[�g�����擾����B
	 * @param jigyoKubun	���Ƌ敪
	 * @return				�ݒ��񕶎���
	 */
	private String getSettingTemplate(String jigyoKubun) {
		synchronized (lock) {
			//���ɓǂݍ��܂�Ă���ꍇ�B
			if (iodSettings.containsKey(jigyoKubun)) {
				return (String) iodSettings.get(jigyoKubun);
			//�ȊO
			} else {
				//2.���Ƌ敪�Ɉ�v����}�b�`���OXML�t�@�C�����擾����(�P��)
				String settingFilePath =
					MessageFormat.format(
						REPORT_SETTING_FILE_PATH,
						new Object[] { jigyoKubun });
				File iodSettingFile = new File(settingFilePath);

				String template = readSettingFile(iodSettingFile);
				iodSettings.put(jigyoKubun, template);
				//3.�ݒ���t�@�C����ǂݍ��ށB
				return template;
			}
		}
	}

	/**
	 * �ϊ��ݒ�e���v���[�g�t�@�C����ǂݍ��ݕ�����ɂ���B
	 * @param settingFile		�ϊ��ݒ�e���v���[�g�t�@�C��
	 * @return					�ϊ��ݒ�e���v���[�g
	 * @throws SystemException	�ϊ��ݒ�e���v���[�g�t�@�C���ǂݍ��ݒ��̗�O
	 */
	private String readSettingFile(File settingFile) throws SystemException {
		Reader is = null;
		try {
			is = new FileReader(settingFile);
			return readerToString(is);
		} catch (IOException e) {
			throw new SystemException(
				"�ϊ��ݒ�e���v���[�g�t�@�C��'" + settingFile + "'���̓ǂݍ��݂Ɏ��s���܂����B",
				e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new SystemException(
						"�ϊ��ݒ�e���v���[�g�t�@�C��'"
							+ settingFile
							+ "'���̓Ǎ�����IO�G���[���������܂����B",
						e);
				}
			}
		}
	}

	/**
	 * �ϊ��ݒ�e���v���[�g�ɐ\���f�[�^�����}�[�W���APDF�o�͗p�ݒ���XML���쐬����B
	 * @param settingInfo			�ϊ��ݒ�e���v���[�g
	 * @param dataInfo				�}�[�W�Ɏg���I�u�W�F�N�g
	 * @return						PDF�o�͗p�ݒ���XML
	 * @throws SystemException		�e���v���[�g�̃}�[�W�Ɏ��s�����ꍇ�B
	 */
	private String merge(String template, Object dataInfo)
		throws SystemException {
		try {

			/* �܂����s���G���W��������������B�f�t�H���g�ł悢�B */
			Velocity.init();

			/* Context ���쐬���āA�f�[�^������ */
			VelocityContext context = new VelocityContext();
			
			//�\���f�[�^���
			context.put("shinseiDataInfo", dataInfo);
			//�c�[���I�u�W�F�N�g
			context.put("escape", new Escape());
			context.put("dateFormat", new DateFormat());
            context.put("shinseiFormat", new ShinseiFormat());
            //2005/04/19 �ǉ� -----------------------------------------��������
            //�a��ϊ��c�[���ǉ�
			context.put("dateUtil", new DateUtil());
            //2005/04/19 �ǉ� -----------------------------------------�����܂�

			/* �e���v���[�g���������� */
			StringWriter writer = new StringWriter();

			/* �������镶������쐬���� */
			Velocity.evaluate(context, writer, "iodSetting", template);
		
			return writer.getBuffer().toString();

		} catch (Exception e) {
			throw new SystemException("�ϊ��ݒ�t�@�C���̃}�[�W���ɃG���[���������܂����B", e);
		}
	}

	/**
	 * �w��t�@�C����ǂݎ��B
	 * @param aFile					�ǂݍ��ރt�@�C��
	 * @return						�ǂݍ��񂾃t�@�C�����\�[�X
	 * @throws ConvertException		�t�@�C���̎擾�Ɏ��s�����Ƃ��B
	 */
	private FileResource readFile(File aFile) throws ConvertException {
		try {
			if (!aFile.exists()) {
				throw new ConvertException("�Ώۃt�@�C��'" + aFile + "'��������܂���B",
                        new ErrorInfo("errors.8003"));
			}
			//�Y�t�t�@�C�����擾����B
			return FileUtil.readFile(aFile);
		} catch (FileNotFoundException e) {
			throw new ConvertException("�Ώۃt�@�C��'" + aFile + "'���̎擾�Ɏ��s���܂����B",
                    new ErrorInfo("errors.8003"),e);
		} catch (IOException e) {
			throw new ConvertException("�Ώۃt�@�C��'" + aFile + "'���̎擾�Ɏ��s���܂����B",
                    new ErrorInfo("errors.8003"),e);
		}
	}

	/** �g�p����u���b�N�T�C�Y */
	private static final int BLKSIZ = 8192;

	/**
	 * Reader�̓��e��String�ɓǂݎ��B
	 * @param is		Reader
	 * @return			�ǂݎ����������B
	 * @throws IOException
	 */
	private static String readerToString(Reader is) throws IOException {
		StringBuffer buffer = new StringBuffer();
		char[] b = new char[BLKSIZ];
		int n;
		while ((n = is.read(b)) > 0) {
			buffer.append(b, 0, n);
		}
		return buffer.toString();
	}

	//2005/05/24 �ǉ��@��������---------------------------------------------------
	//�\��PDF�쐬�̂���
	/**
	 * �\��PDF�f�[�^�����擾���A�\��PDF�f�[�^��IOD�ɕϊ����A
	 * IOD��PDF�ɕϊ����APDF�t�@�C���p�X��DB�ɏ������ށB
	 * 
	 * @param connection	Connection
	 * @param userInfo	UserInfo
	 * @param pkInfo	CheckListSearchInfo
	 * @throws ApplicationException		�ϊ��Ɏ��s�����Ƃ��B
	 * @throws DataAccessException		
	 * @throws IOException
	 */
	public void convertHyoshiData(
            Connection connection,
            UserInfo userInfo,
            CheckListSearchInfo checkInfo)
            throws ApplicationException, DataAccessException, IOException {
	
		//----------------------------
		//�\��PDF�f�[�^���̎擾
		//----------------------------
		CheckListInfo checkListInfo = null;
		try{
			checkListInfo = new CheckListInfoDao(userInfo).selectPdfData(connection, checkInfo);
		}catch(DataAccessException e){
			throw new ConvertException("�\��PDF�f�[�^�̎擾�Ɏ��s���܂����B(�f�[�^�A�N�Z�X)",
                    new ErrorInfo("errors.8003"),e);
		}

		//----------------------------
		//�ۑ��t�@�C���̍쐬(PDF)
		//----------------------------
		File iodFile = new File(MessageFormat.format(PDF_COVER, 
                new Object[] {checkInfo.getJigyoId(),checkInfo.getShozokuCd()}));
		if(log.isDebugEnabled()){
			log.debug("�\��PDF�t�@�C����'" + iodFile + "'�ł��B");
		}
       		
		//----------------------------
		//DB�X�V
		//----------------------------
		new CheckListInfoDao(userInfo).updateFilePath(connection, checkInfo, iodFile);

		//----------------------------
		//�\��PDF�f�[�^���̕ϊ�(PDF)
		//----------------------------
		FileResource pdfFileResource = makeIodFormHyoshiData(userInfo, checkListInfo);
	
		//----------------------------
		//PDF�t�@�C������(����X�V���邽�ߏ㏑���̂�)
		//----------------------------
		FileUtil.writeFile(iodFile, pdfFileResource.getBinary());
		pdfFileResource = null;	
	}
    
//   2006/06/29 dyh add start
    /**
     * �̈�v�揑�\��PDF�f�[�^���PDF�t�@�C�����쐬����B
     * IOD��PDF�ɕϊ����APDF�t�@�C���p�X��DB�ɏ������ށB
     * 
     * @param connection Connection
     * @param userInfo UserInfo
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @throws ApplicationException �ϊ��Ɏ��s�����Ƃ��B
     * @throws ConvertException      
     * @throws IOException
     */
    public void convertGaiyoHyoshiPdf(
            Connection connection,
            UserInfo userInfo,
            String ryoikiSystemNo)
            throws ConvertException, IOException, ApplicationException {
    
        //----------------------------
        //�̈�v�揑�\��PDF�f�[�^���̎擾
        //----------------------------
        RyoikiKeikakushoInfo pdfInfo = null;
        RyoikiKeikakushoInfoDao ryoikiKeikakushoInfoDao =
            new RyoikiKeikakushoInfoDao(userInfo);
        try{
            pdfInfo = ryoikiKeikakushoInfoDao
                    .selectGaiyoHyoshiPdfData(connection, ryoikiSystemNo);
        }catch(DataAccessException e){
            throw new ConvertException(
                    "�̈�v�揑�\��PDF�f�[�^�̎擾�Ɏ��s���܂����B(�f�[�^�A�N�Z�X)",
                    new ErrorInfo("errors.8003"),e);
        }

        //----------------------------
        //�ۑ��t�@�C���̍쐬(PDF)
        //----------------------------
        File iodFile = new File(MessageFormat.format(PDF_DOMAINCOVER,
                new Object[] {pdfInfo.getJigyoId(),pdfInfo.getKariryoikiNo()}));
        if(log.isDebugEnabled()){
            log.debug("�̈�v�揑�\��PDF�t�@�C����'" + iodFile + "'�ł��B");
        }
            
        //----------------------------
        //DB�X�V
        //----------------------------
        try{
            ryoikiKeikakushoInfoDao.updateHyoshiPdfPath(connection,
                    ryoikiSystemNo, iodFile);
        }catch(DataAccessException e){
            throw new ConvertException(
                    "�̈�v�揑�\��PDF�f�[�^�̎擾�Ɏ��s���܂����B(�f�[�^�A�N�Z�X)",
                    new ErrorInfo("errors.4002"),e);
        }

        //----------------------------
        //�̈�v�揑�\��PDF�f�[�^���̕ϊ�(PDF)
        //----------------------------
        FileResource pdfFileResource = makeIodFormGaiyoHyoshi(userInfo, pdfInfo,
                HYOSHIRYOIKI_PDF_TEMPLATE_NAME);
        
        //----------------------------
        //PDF�t�@�C������(����X�V���邽�ߏ㏑���̂�)
        //----------------------------
        FileUtil.writeFile(iodFile, pdfFileResource.getBinary());
        pdfFileResource = null;
    }

    /**
     * �̈�v�揑�\��PDF�f�[�^�����iod�t�@�C�����쐬���A
     * makeIodFormData()���\�b�h���Ăяo���āA
     * getHyoshiFileResource()���\�b�h���Ăяo���āA
     * iod�t�@�C������PDF�t�@�C�����쐬����B
     * 
     * @param UserInfo ���[�U���
     * @param pdfInfo �̈�v�揑�\��PDF�I�u�W�F�N�g
     * @return FileResource PDF�t�@�C��
     * @throws ApplicationException 
     */
    private FileResource makeIodFormGaiyoHyoshi(UserInfo userInfo,
            RyoikiKeikakushoInfo pdfInfo,String pdfTemplateName)
            throws ApplicationException {

        //�Y������ݒ�����擾����
        String template = getSettingTemplate(pdfTemplateName);
        
        //�e���v���[�g��̈�v�揑�\��PDF�f�[�^���g�p���ĕϊ�����B
        String iodSetting = merge(template, pdfInfo);

        //�ݒ�t�@�C���̓Ǎ���
        IodSettings settings = new IodSettings(new StringReader(iodSetting));

        //�ݒ���I�u�W�F�N�g�̍쐬
        List iodSettingInfo = settings.getContents();

        if (log.isDebugEnabled()) {
            log.debug(
                ">>IOD�t�@�C���쐬�T�[�r�X���Ăяo���܂��B");
        }

        FileResource iodFileResource = null;
        try {
            String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
            iodFileResource = getIodFileResourceHyoshi(urls, iodSettingInfo);

            //����iodFileResource�������Ƃ��Ďg�p����̂ŁA������ConvertException�͓����Ȃ��B
            //���for���[�v�̍Ō��catch��ConvertException�𓊂���B
        } catch(IllegalArgumentException e) {
            throw new SystemException("PDF�ϊ��T�[�oURL�̎擾�Ɏ��s���܂����B");
        }

        // PDF�t�@�C���̎擾(�p�X���[�h���w�肵�Ȃ��悤��false��ݒ�)
        FileResource pdfFileResource = getHyoshiFileResource(userInfo, iodFileResource, false);
        return pdfFileResource;
    }

//    /**
//     * PDF�f�[�^�����iod�t�@�C�����쐬��
//     * 
//     * @param pdfInfo PDF�I�u�W�F�N�g
//     * @param pdfTemplateName �ϊ��ݒ�e���v���[�g
//     * @return FileResource PDF�t�@�C��
//     * @throws ApplicationException 
//     */
//    private FileResource makeIodFormData(
//            Object pdfInfo,
//            String pdfTemplateName)
//            throws ApplicationException {
//
//        //�Y������ݒ�����擾����
//        String template = getSettingTemplate(pdfTemplateName);
//        
//        //�e���v���[�g��̈�v�揑�\��PDF�f�[�^���g�p���ĕϊ�����B
//        String iodSetting = merge(template, pdfInfo);
//
//        //�ݒ�t�@�C���̓Ǎ���
//        IodSettings settings = new IodSettings(new StringReader(iodSetting));
//
//        //�ݒ���I�u�W�F�N�g�̍쐬
//        List iodSettingInfo = settings.getContents();
//
//        if (log.isDebugEnabled()) {
//            log.debug(
//                ">>IOD�t�@�C���쐬�T�[�r�X���Ăяo���܂��B");
//        }
//
//        FileResource iodFileResource = getIodFileResource(iodSettingInfo);
//
//        return iodFileResource;
//    }
// 2006/06/29 dyh add end

    //2006/07/03 zhangt add start
    /**
     * �̈�v�揑�T�vPDF�f�[�^���PDF�t�@�C�����쐬����B
     * IOD��PDF�ɕϊ����APDF�t�@�C���p�X��DB�ɏ������ށB
     * 
     * @param connection Connection
     * @param userInfo UserInfo
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @throws ApplicationException �ϊ��Ɏ��s�����Ƃ��B
     * @throws ConvertException      
     * @throws IOException
     */
    public void convertRyoikiGaiyoPdf(UserInfo userInfo, RyoikiKeikakushoPk ryoikiKeikakushoPk)
            throws ApplicationException {
    
        //----------------------------
        //�̈�v�揑�\��PDF�f�[�^���̎擾
        //----------------------------
        Connection connection = null;
        boolean success = false;
        try {
            connection = DatabaseUtil.getConnection();

            //------------------
            //�\���f�[�^XML/PDF�ϊ�
            //------------------
            convertRyoikiKeikakushoData(connection, userInfo, ryoikiKeikakushoPk);

            if (log.isDebugEnabled()) {
                log.debug("-->>�Y�t�t�@�C����ϊ����܂��B");
            }
            //------------------
            //�Y�t�t�@�C��PDF�ϊ�
            //------------------
            convertRyoikiKeikakushoTenpuFile(connection, userInfo, ryoikiKeikakushoPk);

            if (log.isDebugEnabled()) {
                log.debug("--<<�Y�t�t�@�C����ϊ����܂��B");
            }
            //------------------
            //�o�^����I��
            //------------------
            success = true;         
    
        } catch (DataAccessException e) {
            throw new ConvertException("�\���f�[�^���̎擾�Ɏ��s���܂����B",
                    new ErrorInfo("errors.8003"),e);
        } catch (IOException e) {
            throw new ConvertException("�ϊ��t�@�C���̕ۑ��Ɏ��s���܂����B(�t�@�C��IO)",
                    new ErrorInfo("errors.8003"),e);
        } finally {
            try {
                if (success) {
                    DatabaseUtil.commit(connection);
                } else {
                    DatabaseUtil.rollback(connection);
                }
            } catch (TransactionException e) {
                throw new ConvertException("PDF�f�[�^�ϊ�DB�o�^���ɃG���[���������܂����B",e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
 
    /**
     * �\���f�[�^�����擾���A�\���f�[�^��PDF�ɕϊ����A�ۑ��t�@�C���p�X��DB�ɏ������ށB
     * @param connection
     * @param userInfo
     * @param pkInfo
     * @throws ApplicationException     �ϊ��Ɏ��s�����Ƃ��B
     * @throws DataAccessException      
     * @throws IOException
     */
    private void convertRyoikiKeikakushoData(
            Connection connection,
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiKeikakushoPk)
            throws
                ApplicationException,
                DataAccessException,
                IOException {
        
        //----------------------------
        //�\���f�[�^���̎擾
        //----------------------------
        RyoikiKeikakushoInfo ryoikiKeikakushoInfo = null;
        try {
            ryoikiKeikakushoInfo = new ShinseiMaintenance()
                    .selectRyoikiKeikakushoInfo(userInfo, ryoikiKeikakushoPk);
        } catch (ApplicationException e) {
            throw new ConvertException("�\���f�[�^�̎擾�Ɏ��s���܂����B(�f�[�^�A�N�Z�X)",
                    new ErrorInfo("errors.8003"),e);
        }
        //----------------------------
        //�ۑ��t�@�C���̍쐬(PDF)
        //----------------------------
        File iodFile = new File(MessageFormat.format(SHINSEI_PDF_FOLDER,
                new Object[] { ryoikiKeikakushoInfo.getJigyoId() + "_RG",
                        ryoikiKeikakushoInfo.getRyoikiSystemNo() }));
        if (log.isDebugEnabled()) {
            log.debug("�\���f�[�^PDF�ϊ��t�@�C����'" + iodFile + "'�ł��B");
        }

        //----------------------------
        //�ۑ��t�@�C���̍쐬(XML)
        //----------------------------
        File xmlFile = new File(MessageFormat.format(SHINSEI_XML_FOLDER,
                new Object[] { ryoikiKeikakushoInfo.getJigyoId(),
                        ryoikiKeikakushoInfo.getRyoikiSystemNo() }));
        if (log.isDebugEnabled()) {
            log.debug("�\���f�[�^XML�ϊ��t�@�C����'" + xmlFile + "'�ł��B");
        }
                
        //----------------------------
        //DB�X�V
        //----------------------------
        new RyoikiKeikakushoInfoDao(userInfo).updatePdfPath(connection,
                ryoikiKeikakushoInfo.getRyoikiSystemNo(), iodFile);

        //----------------------------
        //�\���f�[�^���̕ϊ�(PDF)
        //----------------------------
        FileResource iodFileResource = makeIodFormRyoikiKeikakushoInfo(ryoikiKeikakushoInfo);
        
        //----------------------------
        //PDF�t�@�C������(����X�V���邽�ߏ㏑���̂�)
        //----------------------------
        FileUtil.writeFile(iodFile,iodFileResource.getBinary());
        iodFileResource = null;

        //----------------------------
        //�\���f�[�^����XML�ϊ�����
        //----------------------------
        String xmlString = makeXmlFormRyoikiKeikakushoInfo(ryoikiKeikakushoInfo);

//      if (log.isDebugEnabled()) {
//          log.debug("XML�̕\��\n" + xmlString);
//      }

        //----------------------------
        //XML�t�@�C��UTF-8�ŏ���(����X�V���邽�ߏ㏑���̂�)
        //----------------------------
        FileUtil.writeFile(xmlFile, xmlString.getBytes("UTF-8"));
    }

    /**
     * �\���f�[�^�����pdf�t�@�C�����쐬����B
     * @param connection
     * @param shinseiDataInfo       �\���f�[�^�I�u�W�F�N�g
     * @return                      pdf�t�@�C��
     * @throws ApplicationException 
     * @throws ConvertException     pdf�t�@�C���쐬���ɗ�O�����������ꍇ�B
     */
    private FileResource makeIodFormRyoikiKeikakushoInfo(RyoikiKeikakushoInfo ryoikiKeikakushoInfo)
        throws ApplicationException {
        
        //�l�����
        String jigyoKubun = "02_RG";
        
        String template = getSettingTemplate(jigyoKubun);
        
        //�e���v���[�g��̈�v�揑�\��PDF�f�[�^���g�p���ĕϊ�����B
        String iodSetting = merge(template, ryoikiKeikakushoInfo);

        //�ݒ�t�@�C���̓Ǎ���
        IodSettings settings = new IodSettings(new StringReader(iodSetting));

        //�ݒ���I�u�W�F�N�g�̍쐬
        List iodSettingInfo = settings.getContents();

        if (log.isDebugEnabled()) {
            log.debug(
                ">>IOD�t�@�C���쐬�T�[�r�X���Ăяo���܂��B");
        }
        try {
            String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
            return getIodFileResourceShinsei(urls, iodSettingInfo);
        } catch(IllegalArgumentException e) {
            throw new SystemException("PDF�ϊ��T�[�oURL�̎擾�Ɏ��s���܂����B");
        }
    }

    /**
     * �\���f�[�^�����XML�t�@�C�����쐬����B
     * @param shinseiDataInfo �\���f�[�^�I�u�W�F�N�g
     * @return String         XML������
     */
    private String makeXmlFormRyoikiKeikakushoInfo(RyoikiKeikakushoInfo ryoikiKeikakushoInfo) {
        synchronized (lock) {
            //�e���v���[�g���̊m�F            
            if (xmlTemplate == null) {
                //�e���v���[�g�̎擾
                File xmlTemplateFile =
                    ApplicationSettings.getFile(
                        ISettingKeys.SHINSEI_XML_TEMPLATE);
                xmlTemplate = readSettingFile(xmlTemplateFile);
            }
            //�e���v���[�g��\���f�[�^���g�p���ĕϊ�����B
            return merge(xmlTemplate, ryoikiKeikakushoInfo);
        }
    }

    /**
     * �̈�v�揑�i�T�v�j�����A�Y�t�t�@�C�������擾���A�Y�t�t�@�C����PDF�ɕϊ����A�ۑ��t�@�C���p�X��DB�ɏ������ށB
     * @param connection
     * @param userInfo
     * @param ryoikiKeikakushoPk
     * @throws DataAccessException
     * @throws ApplicationException
     * @throws IOException
     */
    private void convertRyoikiKeikakushoTenpuFile(
            Connection connection,
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiKeikakushoPk)
            throws DataAccessException, ApplicationException, IOException {

        try{
            //----------------------------
            //�\���f�[�^�̓Y�t�t�@�C�����̎擾
            //----------------------------
            TenpuFileInfoDao dao = new TenpuFileInfoDao(userInfo);
            TenpuFileInfo[] fileInfos = dao.selectTenpuFiles(connection,ryoikiKeikakushoPk);
            
            for (int i = 0; i < fileInfos.length; i++) {
                //----------------------------
                //�ϊ�����Ă��Ȃ��Ƃ���
                //----------------------------
                if(!isConverted(fileInfos[i])){
                    
                    //----------------------------
                    //�ϊ�����B                     
                    //----------------------------
                    File wordFile = new File(fileInfos[i].getTenpuPath());
                    FileResource pdfFileResource = annexFileConvert(readFile(wordFile));
                    
                    //----------------------------
                    //�Y�t�t�H���_�Ƀt�@�C������
                    //----------------------------
                    File pdfFile = new File(wordFile.getParentFile(),pdfFileResource.getName());
                    fileInfos[i].setPdfPath(pdfFile.getAbsolutePath());
                    FileUtil.writeFile(pdfFile,pdfFileResource.getBinary());
                    
                    //----------------------------
                    //DB�X�V
                    //----------------------------
                    dao.updateTenpuFileInfo(connection,fileInfos[i]);
                }
            }
        }catch(NoDataFoundException e){
            if(log.isDebugEnabled()){
                log.debug("�Y�t�t�@�C��������܂���B",e);
            }
        }catch(ConvertException e){
            //PDF�ϊ������̃^�C���A�E�g�̏ꍇ�́A�A���[�g���[���𑗐M����B
            if("errors.8001".equals(e.getErrorCode())){
                //�A���[�g�p�Ȃ̂Ńx�^�����B
                String SMTP_SERVER_ADDRESS = ApplicationSettings.getString(ISettingKeys.SMTP_SERVER_ADDRESS);
                String FROM_ADDRESS = ApplicationSettings.getString(ISettingKeys.FROM_ADDRESS);
                String to = ApplicationSettings.getString(ISettingKeys.TO_ADDRESS_FOR_ALERT);;
                String subject = "�y���{�w�p�U����d�q�\���V�X�e���z�A���[�g�ʒm";
                String content = "PDF�ϊ������Ń^�C���A�E�g���������܂����B\n"
                                +"�E���[�UID�F"+userInfo.getId()+"\n"
                                +"�E�\�����i�V�X�e����t�ԍ��j�F"+ryoikiKeikakushoPk.getRyoikiSystemNo()+"\n"
                                +"�E���������F"+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date())+"\n"
                                ;
                //-----���[�����M
                try{
                    SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
                    mailer.sendMail(FROM_ADDRESS,           //���o�l
                                    to,                     //to
                                    null,                   //cc
                                    null,                   //bcc
                                    subject,                //����
                                    content);               //�{��
                }catch(Exception ex){
                    log.warn("���[�����M�Ɏ��s���܂����B",ex);
                    return;
                }
            }
            throw e;
        }
    }
    //2006/07/03 zhangt add end

	/**
	 * �\��PDF�f�[�^�����iod�t�@�C�����쐬���A
	 * getHyoshiFileResource()���\�b�h���Ăяo���āAiod�t�@�C������PDF�t�@�C�����쐬����B
	 * 
	 * @param UserInfo				���[�U���
	 * @param checkInfo			�\��PDF�I�u�W�F�N�g
	 * @return						PDF�t�@�C��
	 * @throws ApplicationException	
	 */
	private FileResource makeIodFormHyoshiData(UserInfo userInfo, CheckListInfo checkInfo)
		throws ApplicationException {

		//�Y������ݒ�����擾����
		String template = getSettingTemplate(HYOSHI_PDF_TEMPLATE_NAME);
		
		//�e���v���[�g��\��PDF�f�[�^���g�p���ĕϊ�����B
		String iodSetting = merge(template, checkInfo);

		//�ݒ�t�@�C���̓Ǎ���
		IodSettings settings = new IodSettings(new StringReader(iodSetting));

		//�ݒ���I�u�W�F�N�g�̍쐬
		List iodSettingInfo = settings.getContents();

		if (log.isDebugEnabled()) {
			log.debug(
				">>IOD�t�@�C���쐬�T�[�r�X���Ăяo���܂��B");
		}

		//IOD�t�@�C���쐬�T�[�r�X�Ăяo��
		//2006.07.03 iso PDF�ϊ��T�[�o�U�蕪�������̂��ߒǉ�
//		ISystemServise servise =
//			SystemServiceFactory.getSystemService(
//				IServiceName.CONVERT_SERVICE,
//				SERVER_URL);
//
//		//IOD�t�@�C���擾
//		FileResource iodFileResource = servise.iodFileCreation(iodSettingInfo);
//
//		//########## DEBUG ##########		
//		if (log.isDebugEnabled()) {
//			log.debug(
//				">>�쐬IOD�t�@�C���� '"
//					+ iodFileResource.getName()
//					+ " �T�C�Y '"
//					+ iodFileResource.getBinary().length
//					+ "'");
//		}

// 2006/07/19 dyh update start �����F���ʕ��@���쐬
		FileResource iodFileResource = null;
		try {
			String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
//			for(int i=0; i < urls.length; i++) {
//				ISystemServise servise =
//					SystemServiceFactory.getSystemService(
//						IServiceName.CONVERT_SERVICE,
//						urls[i]);
//				try{
//					iodFileResource = servise.iodFileCreation(iodSettingInfo);
//					if (log.isDebugEnabled()) {
//						log.debug(
//								">>�쐬IOD�t�@�C���� '"
//								+ iodFileResource.getName()
//								+ " �T�C�Y '"
//								+ iodFileResource.getBinary().length
//								+ "'");
//					}
//					break;
//				} catch (Exception e) {
//					log.info("PDF�ϊ��T�[�o�ŃG���[���������܂����BURL:" + urls[i], e);
//					if(i == urls.length - 1) {
//						//�S�T�[�o���̃��[�v���I����Ă�pdfFileResource���擾�ł��Ȃ��ꍇ�A
//						//�ڑ��G���[�A�ϊ��G���[�̗������l�����邪�AConvertException�Ƃ���B
//						throw new ConvertException("�S�Ă�PDF�ϊ��T�[�o�ŏ����Ɏ��s���܂����B");
//					}
//				}
//			}
            iodFileResource = getIodFileResourceHyoshi(urls, iodSettingInfo);

			//����iodFileResource�������Ƃ��Ďg�p����̂ŁA������ConvertException�͓����Ȃ��B
			//���for���[�v�̍Ō��catch��ConvertException�𓊂���B
		} catch(IllegalArgumentException e) {
			throw new SystemException("PDF�ϊ��T�[�oURL�̎擾�Ɏ��s���܂����B");
		}
// 2006/07/19 dyh update end

		//PDF�t�@�C���̎擾(�p�X���[�h���w�肵�Ȃ��悤��false��ݒ�)
		FileResource pdfFileResource = getHyoshiFileResource(userInfo, iodFileResource, false);
		
		return pdfFileResource;
	}

	/**
	 * �\��PDF�f�[�^���PDF�t�@�C�����쐬����B
	 * lockFlag��true�̂Ƃ��A�쐬���ꂽPDF�t�@�C���Ƀp�X���[�h���b�N��������B
	 * 
	 * @param userInfo		���[�U���
	 * @param file			iod�t�@�C�����\�[�X
	 * @param lockFlag		�p�X���[�h���b�N�t���O
	 * @return	PDF�t�@�C�����\�[�X
	 * @throws ApplicationException
	 * @throws ConvertException
	 */
	public FileResource getHyoshiFileResource(
		UserInfo userInfo,
		FileResource file, 
		boolean lockFlag)
		throws ApplicationException, ConvertException {

//		FileResource resource = null; 
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			List iodFileResource = new ArrayList();
			iodFileResource.add(file);
	
			//----------------------------
			//�p�X���[�h
			//----------------------------
			String password = null;
			if(lockFlag){
				password = getPassword(connection, userInfo);
			}
			
			if(log.isDebugEnabled()){
				log.debug("--->>PDF���쐬���܂��B");
			}
						
			//----------------------------
			//�ϊ�
			//----------------------------
			//2006.07.03 iso PDF�ϊ��T�[�o�U�蕪�������̂��ߒǉ�
//			ISystemServise servise =
//				SystemServiceFactory.getSystemService(
//					IServiceName.CONVERT_SERVICE,
//					SERVER_URL);
//			
//			try{
//				resource =  servise.iodToPdf(iodFileResource,password);
//			}finally{
//				if (log.isDebugEnabled()) {
//					log.debug("---<<PDF���쐬���܂����B");
//				}
//			}

// 2006/07/19 dyh update start �����F���ʕ��@���쐬
//			try {
//				String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
//				for(int i=0; i < urls.length; i++) {
//					ISystemServise servise =
//						SystemServiceFactory.getSystemService(
//							IServiceName.CONVERT_SERVICE,
//							urls[i]);
//					try{
//						FileResource resource = servise.iodToPdf(iodFileResource,password);
//						if (log.isDebugEnabled()) {
//							log.debug("---<<PDF���쐬���܂����B");
//						}
//						return resource;
//					} catch (Exception e) {
//						log.info("PDF�ϊ��T�[�o�ŃG���[���������܂����BURL:" + urls[i], e);
//					}
//				}
//				//�S�T�[�o���̃��[�v���I����Ă�resource���擾�ł��Ȃ��ꍇ�A
//				//�ڑ��G���[�A�ϊ��G���[�̗������l�����邪�AConvertException�Ƃ���B
//				throw new ConvertException("�S�Ă�PDF�ϊ��T�[�o�ŏ����Ɏ��s���܂����B");
//			} catch(IllegalArgumentException e) {
//				throw new SystemException("PDF�ϊ��T�[�oURL�̎擾�Ɏ��s���܂����B");
//			}
            return getPdfFileResource(iodFileResource,password);
// 2006/07/19 dyh update end

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"PDF�t�@�C���̎擾�Ɏ��s���܂����B", new ErrorInfo("errors.8003"),e);
		} finally{
			DatabaseUtil.closeConnection(connection);
		}
//		return resource;
	}
	//�ǉ��@�����܂�------------------------------------------------------------

	//2005.07.15 iso PDF�Y�t�@�\�ǉ�
	/**
	 * PDF�Y�t�t�@�C�����L�����`�F�b�N����B
	 * �����ȏꍇ�́A�G���[��Ԃ��B
	 * @param fileRes				�Y�t�t�@�C�����\�[�X	
	 */
	public int  checkPdf(FileResource fileRes) throws ApplicationException {
		//2006.07.03 iso PDF�ϊ��T�[�o�U�蕪�������̂��ߒǉ�
//		ISystemServise servise
//			= SystemServiceFactory.getSystemService(IServiceName.CONVERT_SERVICE, SERVER_URL);
//		return servise.checkPdf(fileRes);
		try {
			String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
			for(int i=0; i < urls.length; i++) {
				ISystemServise servise =
					SystemServiceFactory.getSystemService(
						IServiceName.CONVERT_SERVICE,
						urls[i]);
				try{
					return servise.checkPdf(fileRes);
				} catch (Exception e) {
					log.info("PDF�ϊ��T�[�o�ŃG���[���������܂����BURL:" + urls[i], e);
				}
			}
			//�S�T�[�o���̃��[�v���I����Ă��`�F�b�N���ʂ��擾�ł��Ȃ��ꍇ�A
			//�ڑ��G���[�A�ϊ��G���[�̗������l�����邪�AConvertException�Ƃ���B
			throw new ConvertException("�S�Ă�PDF�ϊ��T�[�o�ŏ����Ɏ��s���܂����B");
		} catch(IllegalArgumentException e) {
			throw new SystemException("PDF�ϊ��T�[�oURL�̎擾�Ɏ��s���܂����B");
		}
	}

// 2006/07/20 dyh delete start �����FgetSortedUrls���@�C����A�g�p���Ȃ��B
//	//2006.07.03 iso PDF�ϊ��T�[�o�U�蕪���̂��߂ɒǉ�
//	/**
//	 * �����z���List�^��DEEP�R�s�[����B
//	 * �����z�񂪋�Ȃ�null��Ԃ��B
//	 * @param strings	�����z��
//	 * @return �����I�u�W�F�N�g���X�g
//	 */
//	private List deepCopyArrays2List(String[] strings) {
//		
//		if(strings == null) {
//			return null;
//		}
//		
//		List list = new LinkedList();
//		for(int i = 0; i < strings.length; i++) {
//			list.add(strings[i]);
//		}
//		return list;
//	}
// 2006/07/20 dyh delete end

	//2006.07.03 iso PDF�ϊ��T�[�o�U�蕪���̂��߂ɒǉ�
	/**
	 * URL�����z��������_���Ƀ\�[�g�i�d�ݕt���̉e�����󂯂�j���ĕԂ��B
	 * @param serverUrls	URL�����z��
	 * @param serverUrls	URL�d�ݕt�������z��
	 * @return �\�[�g�ς�URL�����z��
	 * @throws IllegalArgumentException  URL�����z��AURL�d�ݕt�������z�񂪕s���ȏꍇ
	 */
	private String[] getSortedUrls(String[] serverUrls, String[] serverWeights)
            throws IllegalArgumentException {

		//URL�����z��1�̏ꍇ�͏d�ݓ��͊֌W�Ȃ��A�������ɂ��̂܂ܕԂ��B
		if(serverUrls.length == 1) {
			return serverUrls;
		}
		
		//2�̔z��̒�����0���A�قȂ�ꍇ�G���[�Ƃ���B
		if(serverUrls.length == 0 || serverUrls.length != serverWeights.length) {
			throw new IllegalArgumentException("�T�[�oURL�ݒ蕶���񂪕s���ł�");
		}

// 2006/07/20 dyh update start �����FIndexOutOfBoundsException������̂�
//		//�d�݂�0�ȏ��URL�̂݃\�[�g�ΏۂƂ���B
//		List urlList = deepCopyArrays2List(serverUrls);			//�\�[�g�Ώ�URL���X�g�B���̃��X�g����ɂȂ�܂Ń��[�v���J��Ԃ��B
//		List weightList = deepCopyArrays2List(serverWeights);	//�\�[�g�Ώ�URL�d�݃��X�g�B0�ȏ�̐��l�i�ɕϊ��o���镶����j���i�[�B
//		
//		for(int i = 0; i < serverUrls.length; i++) {
//			if(StringUtil.parseInt(serverWeights[i]) <= 0) {
//				//�d�݂�0�ȉ��A���l�ȊO��URL���\�[�g�Ώۂ��珜�O����B
//				urlList.remove(i);
//				weightList.remove(i);
//			}
//		}
        // �d�݂�0�ȏ��URL�̂݃\�[�g�ΏۂƂ���B
        List urlList = new ArrayList(serverUrls.length);         //�\�[�g�Ώ�URL���X�g�B���̃��X�g����ɂȂ�܂Ń��[�v���J��Ԃ��B
        List weightList = new ArrayList(serverWeights.length);   //�\�[�g�Ώ�URL�d�݃��X�g�B0�ȏ�̐��l�i�ɕϊ��o���镶����j���i�[�B
        
        for(int i = 0; i < serverUrls.length; i++) {
            if(StringUtil.parseInt(serverWeights[i]) > 0) {
                //�d�݂�0�ȉ��A���l�ȊO��URL���\�[�g�Ώۂ��珜�O����B
                urlList.add(serverUrls[i]);
                weightList.add(serverWeights[i]);
            }
        }
// 2006/07/20 dyh update start

		int maxListSize = urlList.size();						//�\�[�g�Ώۃ��X�g�T�C�Y�B�d�݂��s���ȏꍇ�������������l�B

		String[] sortedUrls = new String[maxListSize];			//�\�[�g�ςݔz����i�[����
		
		for(int i = 0; i < maxListSize; i++) {
			int totalWeight = 0;								//�\�[�g�Ώۃ��X�g�̑S�d�݂̍��v
			int tmpListSize = urlList.size();					//�\�[�g�Ώۃ��X�g�̃T�C�Y�B���̏����Ń��X�g�̃T�C�Y��0�ɂȂ�܂Ō��葱����B
			double[] ruisekiWeight = new double[tmpListSize];	//�e�d�݂̗ݐ�
			
			for(int j = 0; j < tmpListSize; j++) {
				int weight = Integer.parseInt(weightList.get(j).toString());
				totalWeight += weight;
				ruisekiWeight[j] = totalWeight;
			}

			Random random = new Random();
			int selectNum = random.nextInt(totalWeight);
			
			for(int j = 0; j < tmpListSize; j++) {
				if(ruisekiWeight[j] > selectNum) {
					sortedUrls[i] = urlList.get(j).toString();	//�q�b�g����URL��������\�[�g�ςݔz��Ɋi�[���A
					urlList.remove(j);							//�q�b�g����URL�̓\�[�g�Ώ�URL���X�g����폜����B
					weightList.remove(j);						//�q�b�g����URL�̏d�݂��Ώۃ��X�g����폜����B
					break;
				}
			}
		}
		
		return sortedUrls;
	}

//2006/07/17�@�c�@�ǉ���������------------------------------------------------------------------

    /* (�� Javadoc)
     * @see jp.go.jsps.kaken.model.pdf.IPdfConvert#getShinseiFileResource(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
     */
    public FileResource getGaiyouResource(UserInfo userInfo, final RyoikiKeikakushoPk pkInfo)
            throws ApplicationException, NoDataFoundException, ConvertException {
        return getGaiyouFileResource(userInfo, pkInfo, true);
    }
    
    /**
     * �̈�v�揑�i�T�v�j�����PDF�t�@�C�����쐬����B lockFlag��true�̂Ƃ��A�쐬���ꂽPDF�t�@�C���Ƀp�X���[�h���b�N��������B
     * 
     * @param userInfo
     * @param pkInfo
     * @param lockFlag
     * @return
     * @throws ApplicationException
     * @throws NoDataFoundException
     * @throws ConvertException
     */
    public FileResource getGaiyouFileResource(UserInfo userInfo, final RyoikiKeikakushoPk pkInfo,
            boolean lockFlag) throws ApplicationException, NoDataFoundException, ConvertException {

        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();

            //----------------------------
            //��������t�@�C���̎擾
            //----------------------------
            List iodFiles  = null;
            try{
                iodFiles = new RyoikiKeikakushoInfoDao(userInfo,dbLink).getIodFilesToMerge(connection,pkInfo);
            }catch(NoDataFoundException e){
                try{
                    //������iod�t�@�C���p�X�����݂��Ȃ������ꍇ�͍ēx�ϊ������������A���̏�ōĎ��s���s���i�P�񂾂��j
                    convertRyoikiGaiyoPdf(userInfo, pkInfo);
                    iodFiles = new RyoikiKeikakushoInfoDao(userInfo,dbLink).getIodFilesToMerge(connection,pkInfo);
                }catch(Exception ex){
                    //����ł����s�����ꍇ�͏�ʑ��ɗ�O�𓊂���
                    throw e;
                }
            }
            List iodFileResource = new ArrayList();
            
            //----------------------------
            //DB�����N���l�����ăt�@�C���Ǎ���
            //----------------------------
            for (Iterator iter = iodFiles.iterator(); iter.hasNext();) {
                File element = (File) iter.next();
                File targetFile = null;
                if (dbLink == null || dbLink.length() == 0) {
                    //�ʏ�
                    targetFile = element;
                } else {
                    targetFile =
                        new File(StringUtil.substrReplace(element.getAbsolutePath(),
                                DRIVE_LETTER_CONVERTED_TO_UNC,HOKAN_SERVER_UNC));
                    //�p�X�������UNC�`���ɕϊ�����
                    if(log.isDebugEnabled()){
                        log.debug("dbLink�o�R�̂��߁A'"
                                + element
                                + "'��'"
                                + targetFile
                                + "'�t�@�C����ǂݍ��݂܂��B");
                    }
                }
                iodFileResource.add(readFile(targetFile));
            }
            
            //----------------------------
            //�p�X���[�h
            //----------------------------
            String password = null;
            if(lockFlag){
                password = getPassword(connection,userInfo);
            }
            
            if(log.isDebugEnabled()){
                log.debug("--->>PDF���쐬���܂��B");
            }

            return getPdfFileResource(iodFileResource, password);

        } catch (DataAccessException e) {
            throw new ApplicationException(
                "PDF�t�@�C���̎擾�Ɏ��s���܂����B", new ErrorInfo("errors.8003"),e);
        } finally{
            DatabaseUtil.closeConnection(connection);
        }
    }
//  2006/07/17 �c �ǉ������܂�------------------------------------------------------------------

    //2006.09.29 iso �u�T�v�v��PDF���쐬���A�w��t�H���_�ɏ�������
    /**
     * �t�@�C�����X�g���w��t�H���_�ɏ������ށB
     * 
     * @param connection
     * @param userInfo
     * @param fileList
     * @param outFile
     * @return
     * @throws ApplicationException
     * @throws NoDataFoundException
     * @throws ConvertException
     */
    public void writeGaiyoFileResource(
    		Connection connection, UserInfo userInfo, FileResource fileResource, List fileList, File outFile)
    		throws ApplicationException, NoDataFoundException, ConvertException {

        //�e�X�g�p�B
        //DB�ɒl�������āA���[�J���}�V���Ƀt�@�C�����Ȃ��ꍇ�A�G���[�ɂȂ�B
        //�G���[�ƂȂ���ŁA�G���[�𖳎��������ꍇ�Atrue�Ƃ���B
    	//boolean debugFlg = true;		//2006/10/3 �G���[���O�t�@�C���֏o�͂Ƃ���
    	
        //----------------------------
        //�p�X���[�h
        //----------------------------
        String password = null;

        List fileResourceList = new ArrayList();
        for (Iterator iter = fileList.iterator(); iter.hasNext();) {
            File element = (File) iter.next();
        	try {
                fileResourceList.add(readFile(element));
        	}
        	catch(ConvertException e) {
//2006/10/3
//	            if(debugFlg) {
//	            	log.info("�ȉ��̌��t�@�C����������܂��񂪖������܂��F" + outFile);
//	            } else {
//	    			throw new ApplicationException("�ȉ��̌��t�@�C����������܂���F" + outFile);
//	            }
    			throw new NoDataFoundException("�ȉ��̌��t�@�C����������܂���F" + outFile);
        	}
        }
        fileResourceList.add(0, fileResource);
        
        if(log.isDebugEnabled()){
            log.debug("--->>PDF���쐬���܂��B");
        }
        FileResource writeResource = getPdfFileResource(fileResourceList, password);

        try {
            FileUtil.writeFile(outFile, writeResource.getBinary());
        } catch (IOException e) {
            throw new ConvertException("�t�@�C���̕ۑ��Ɏ��s���܂����B(�t�@�C��IO)",
                    new ErrorInfo("errors.8003"),e);
        } 
    }
    
    /**
     * PDF�ɕϊ������f�[�^���擾����i���̎g�����j
     * @param iodFileResources
     * @param password
     * @return FileResource
     * @throws ConvertException
     */
    private FileResource getPdfFileResource(List iodFileResources, String password)
            throws ConvertException, SystemException {
    	return getPdfFileResource(iodFileResources, password, null, null);
    }
    
    // 2006/07/19 dyh add start
    /**
     * PDF�ɕϊ������f�[�^���擾����i�w�i�o�͂̎g�����j2007/6/15�ǉ�
     * @param iodFileResources
     * @param password
     * @param jokyoId    �\����ID
     * @param jigyoKbn    ����ID
     * @return FileResource
     * @throws ConvertException
     */
    private FileResource getPdfFileResource(
    		List iodFileResources,	String password, 
    		String jokyoId,    		String jigyoKbn)
            throws ConvertException, SystemException {

        try {
            String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
            for(int i=0; i < urls.length; i++) {
                ISystemServise servise =
                    SystemServiceFactory.getSystemService(
                        IServiceName.CONVERT_SERVICE,
                        urls[i]);
                try{
                	//2007/6/15 �����������o��
                    //FileResource fileResource = servise.iodToPdf(iodFileResources,password);
                    FileResource fileResource = servise.iodToPdf(iodFileResources,password, jokyoId, jigyoKbn);
                    if (log.isDebugEnabled()) {
                        log.debug("---<<PDF���쐬���܂����B");
                    }
                    return fileResource;
                } catch (Exception e) {
                    log.info("PDF�ϊ��T�[�o�ŃG���[���������܂����BURL:" + urls[i], e);
                }
            }
            //�S�T�[�o���̃��[�v���I����Ă�fileResource���A��Ȃ��ꍇ�A
            //�ڑ��G���[�A�ϊ��G���[�̗������l�����邪�AConvertException�Ƃ���B
            throw new ConvertException("�S�Ă�PDF�ϊ��T�[�o�ŏ����Ɏ��s���܂����B");
        } catch(IllegalArgumentException e) {
            throw new SystemException("PDF�ϊ��T�[�oURL�̎擾�Ɏ��s���܂����B");
        }
    }

    /**
     * �ݒ���Ɋ�Â�IOD�t�@�C�����擾����
     * @param iodSettingInfo
     * @return
     */
    private FileResource getIodFileResourceHyoshi(String[] urls, List iodSettingInfo)
            throws ConvertException, SystemException {

        FileResource iodFileResource = null;
        for(int i=0; i < urls.length; i++) {
            ISystemServise servise =
                SystemServiceFactory.getSystemService(
                    IServiceName.CONVERT_SERVICE,
                    urls[i]);
            try{
                iodFileResource = servise.iodFileCreation(iodSettingInfo);
                if (log.isDebugEnabled()) {
                    log.debug(
                            ">>�쐬IOD�t�@�C���� '"
                            + iodFileResource.getName()
                            + " �T�C�Y '"
                            + iodFileResource.getBinary().length
                            + "'");
                }
                break;
            } catch (Exception e) {
                log.info("PDF�ϊ��T�[�o�ŃG���[���������܂����BURL:" + urls[i], e);
                if(i == urls.length - 1) {
                    //�S�T�[�o���̃��[�v���I����Ă�pdfFileResource���擾�ł��Ȃ��ꍇ�A
                    //�ڑ��G���[�A�ϊ��G���[�̗������l�����邪�AConvertException�Ƃ���B
                    throw new ConvertException("�S�Ă�PDF�ϊ��T�[�o�ŏ����Ɏ��s���܂����B");
                }
            }
        }
        return iodFileResource;
    }

    /**
     * �ݒ���Ɋ�Â�IOD�t�@�C�����擾����
     * @param iodSettingInfo
     * @return
     */
    private FileResource getIodFileResourceShinsei(String[] urls, List iodSettingInfo)
            throws ConvertException {

        for(int i=0; i < urls.length; i++) {
            ISystemServise servise =
                SystemServiceFactory.getSystemService(
                    IServiceName.CONVERT_SERVICE,
                    urls[i]);
            try{
                FileResource iodFileResource = servise.iodFileCreation(iodSettingInfo);
                if (log.isDebugEnabled()) {
                    log.debug(
                    ">>�쐬IOD�t�@�C���� '"
                        + iodFileResource.getName()
                        + " �T�C�Y '"
                        + iodFileResource.getBinary().length
                        + "'");
                }
                return iodFileResource;
            } catch (Exception e) {
                log.info("PDF�ϊ��T�[�o�ŃG���[���������܂����BURL:" + urls[i], e);
            }
        }

        //�S�T�[�o���̃��[�v���I����Ă�iodFileResourcee���擾�ł��Ȃ��ꍇ�A
        //�ڑ��G���[�A�ϊ��G���[�̗������l�����邪�AConvertException�Ƃ���B
        throw new ConvertException("�S�Ă�PDF�ϊ��T�[�o�ŏ����Ɏ��s���܂����B");
    }
// 2006/07/19 dyh add end

//2006/07/21 �c�@�ǉ���������
    /**
     * �\���f�[�^���A�Y�t�t�@�C�������IOD�t�@�C�����쐬���A�\���f�[�^�Ǘ��ɓo�^����B(�����񖔂͌����v�撲���m�F�p)
     * @param userInfo              ���s���郆�[�U���B
     * @param pkInfo                �\���f�[�^��L�[�B
     * @throws ApplicationException 
     * @throws ConvertException     �ϊ��Ɏ��s�����Ƃ��B
     */
    public void shinseiDataConvertForConfirm(UserInfo userInfo, Connection connection,
            ShinseiDataPk pkInfo, File iodFile, File xmlFile) throws ApplicationException {
        
        try {

        	//2007.03.23 iso �y�[�W�`�F�b�N�G���[�̎��APDF�̔Ő������X�V�����o�O�Ή�
//            //------------------
//            //�\���f�[�^XML/PDF�ϊ�
//            //------------------
//            convertShinseiDataForConfirm(connection, userInfo, pkInfo, iodFile, xmlFile);
//
//            if (log.isDebugEnabled()) {
//                log.debug("-->>�Y�t�t�@�C����ϊ����܂��B");
//            }
            //------------------
            //�Y�t�t�@�C��PDF�ϊ�
            //------------------           
           
// 2007/02/05 ���u�j�@ �C�� ��������
            //convertShinseiTenpuFile(connection, userInfo, pkInfo);
            /** �m�F������  */
            convertShinseiTenpuFileForConfirm(connection, userInfo, pkInfo);
//2007/02/05�@���u�j�@ �C�� �����܂�          
            
            if (log.isDebugEnabled()) {
                log.debug("--<<�Y�t�t�@�C����ϊ����܂��B");
            }       

        	//2007.03.23 iso �y�[�W�`�F�b�N�G���[�̎��APDF�̔Ő������X�V�����o�O�Ή�
            //------------------
            //�\���f�[�^XML/PDF�ϊ�
            //------------------
            convertShinseiDataForConfirm(connection, userInfo, pkInfo, iodFile, xmlFile);

            if (log.isDebugEnabled()) {
                log.debug("-->>�Y�t�t�@�C����ϊ����܂��B");
            }
            
        } catch (DataAccessException e) {
            throw new ConvertException("�\���f�[�^���̎擾�Ɏ��s���܂����B",
                    new ErrorInfo("errors.8003"),e);
        } catch (IOException e) {
            throw new ConvertException("�ϊ��t�@�C���̕ۑ��Ɏ��s���܂����B(�t�@�C��IO)",
                    new ErrorInfo("errors.8003"),e);
        }
    }
    
    /**
     * �\���f�[�^�����擾���A�\���f�[�^��PDF�ɕϊ����A�ۑ��t�@�C���p�X��DB�ɏ������ށB(�����񖔂͌����v�撲���m�F�p)
     * @param connection
     * @param userInfo
     * @param pkInfo
     * @throws ApplicationException     �ϊ��Ɏ��s�����Ƃ��B
     * @throws DataAccessException      
     * @throws IOException
     */
    private void convertShinseiDataForConfirm(
            Connection connection,
            UserInfo userInfo,
            ShinseiDataPk pkInfo,
            File iodFile,
            File xmlFile)
            throws
                ApplicationException,
                DataAccessException,
                IOException {
        
        //----------------------------
        //�\���f�[�^���̎擾
        //----------------------------
        ShinseiDataInfo shinseiDataInfo = null;
        try{
            shinseiDataInfo = new ShinseiMaintenance().selectShinseiDataInfoForConfirm(userInfo,
                    connection, pkInfo);
        }catch(ApplicationException e){
            throw new ConvertException("�\���f�[�^�̎擾�Ɏ��s���܂����B(�f�[�^�A�N�Z�X)",
                    new ErrorInfo("errors.8003"),e);
        }
        
        if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_WAKATESTART.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(shinseiDataInfo.getJigyoCd())
//2007/02/06 �c�@�ǉ���������
                ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(shinseiDataInfo.getJigyoCd())
//2007/02/06�@�c�@�ǉ������܂�
//2007/03/13  �����F�@�폜 ��������
//                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(shinseiDataInfo.getJigyoCd())
//                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(shinseiDataInfo.getJigyoCd())
//                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(shinseiDataInfo.getJigyoCd())
//2007/03/13  �����F�@�폜 �����܂�
        ){
            ShinseishaPk pk= new ShinseishaPk();
            pk.setShinseishaId(shinseiDataInfo.getShinseishaId());
            ShinseishaInfo shinseishaInfo = new ShinseishaInfoDao(userInfo)
                    .selectShinseishaInfo(connection, pk);
            shinseiDataInfo.setBirthDay(shinseishaInfo.getBirthday());
        }
        
        //----------------------------
        //�\���f�[�^���̕ϊ�(PDF)
        //----------------------------
        FileResource iodFileResource = makeIodFormShinseiData(shinseiDataInfo);
        
        //----------------------------
        //PDF�t�@�C������(����X�V���邽�ߏ㏑���̂�)
        //----------------------------
        FileUtil.writeFile(iodFile,iodFileResource.getBinary());
        iodFileResource = null;

        //----------------------------
        //�\���f�[�^����XML�ϊ�����
        //----------------------------
        String xmlString = makeXmlFormShinseiData(shinseiDataInfo);

        //----------------------------
        //XML�t�@�C��UTF-8�ŏ���(����X�V���邽�ߏ㏑���̂�)
        //----------------------------
        FileUtil.writeFile(xmlFile, xmlString.getBytes("UTF-8"));
    }
    
    /**
     * �\���f�[�^�����擾���A�\���f�[�^��PDF�ɕϊ����A�ۑ��t�@�C���p�X��DB�ɏ������ށB(�̈�v�揑�m�F�p)
     * @param connection
     * @param userInfo
     * @param pkInfo
     * @throws ApplicationException     �ϊ��Ɏ��s�����Ƃ��B
     * @throws DataAccessException      
     * @throws IOException
     */
    private void convertRyoikiKeikakushoDataForConfirm(
            Connection connection,
            UserInfo userInfo,
            File iodFile,
            RyoikiKeikakushoPk ryoikiKeikakushoPk)
            throws
                ApplicationException,
                DataAccessException,
                IOException {
        
        //----------------------------
        //�\���f�[�^���̎擾
        //----------------------------
        RyoikiKeikakushoInfo ryoikiKeikakushoInfo = null;
        try {
            ryoikiKeikakushoInfo = new ShinseiMaintenance()
                    .selectRyoikiKeikakushoInfoForConfirm(userInfo, connection, ryoikiKeikakushoPk);
        } catch (ApplicationException e) {
            throw new ConvertException("�\���f�[�^�̎擾�Ɏ��s���܂����B(�f�[�^�A�N�Z�X)",
                    new ErrorInfo("errors.8003"),e);
        }

        //----------------------------
        //�ۑ��t�@�C���̍쐬(XML)
        //----------------------------
        File xmlFile = new File(MessageFormat.format(SHINSEI_XML_FOLDER,
                new Object[] { ryoikiKeikakushoInfo.getJigyoId(),
                        ryoikiKeikakushoInfo.getRyoikiSystemNo() }));
        if (log.isDebugEnabled()) {
            log.debug("�\���f�[�^XML�ϊ��t�@�C����'" + xmlFile + "'�ł��B");
        }
                
        //----------------------------
        //�\���f�[�^���̕ϊ�(PDF)
        //----------------------------
        FileResource iodFileResource = makeIodFormRyoikiKeikakushoInfo(ryoikiKeikakushoInfo);
        
        //----------------------------
        //PDF�t�@�C������(����X�V���邽�ߏ㏑���̂�)
        //----------------------------
        FileUtil.writeFile(iodFile,iodFileResource.getBinary());
        iodFileResource = null;

        //----------------------------
        //�\���f�[�^����XML�ϊ�����
        //----------------------------
        String xmlString = makeXmlFormRyoikiKeikakushoInfo(ryoikiKeikakushoInfo);

        //----------------------------
        //XML�t�@�C��UTF-8�ŏ���(����X�V���邽�ߏ㏑���̂�)
        //----------------------------
        FileUtil.writeFile(xmlFile, xmlString.getBytes("UTF-8"));
    }

    //2006.09.15 iso �^�C�g���Ɂu�T�v�v������PDF�쐬�̂���
    /**
     * �\���f�[�^��PDF�ϊ����A�Y�t�t�@�C����PDF�ƌ�������B
     * @param connection
     * @param userInfo
     * @param ryoikikeikakushoInfo
     * @throws ApplicationException     �ϊ��Ɏ��s�����Ƃ��B
     * @throws DataAccessException      
     * @throws IOException
     */
    public FileResource convertRyoikiKeikakushoGaiyo(
    		Connection connection, UserInfo userInfo, RyoikiKeikakushoInfo ryoikikeikakushoInfo)
    		throws ApplicationException{
        
    	ryoikikeikakushoInfo.setAddTitele("�T�v");	//���ɕύX����邱�Ƃ��Ȃ����낤���A�ׂ������B
        
        //----------------------------
        //�\���f�[�^���̕ϊ�(PDF)
        //----------------------------
        return makeIodFormRyoikiKeikakushoInfo(ryoikikeikakushoInfo);
        
    }
    
    /**
     * �̈�v�揑�T�vPDF�f�[�^���PDF�t�@�C�����쐬����B(�̈�v�揑�m�F�p)
     * IOD��PDF�ɕϊ����APDF�t�@�C���p�X��DB�ɏ������ށB
     * 
     * @param connection Connection
     * @param userInfo UserInfo
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @throws ApplicationException �ϊ��Ɏ��s�����Ƃ��B
     * @throws ConvertException      
     * @throws IOException
     */
    public void convertRyoikiGaiyoPdfForConfirm(Connection connection, UserInfo userInfo, File iodFile,
            RyoikiKeikakushoPk ryoikiKeikakushoPk) throws ApplicationException {
    
        //----------------------------
        //�̈�v�揑�\��PDF�f�[�^���̎擾
        //----------------------------
        try {

            //------------------
            //�\���f�[�^XML/PDF�ϊ�
            //------------------
            convertRyoikiKeikakushoDataForConfirm(connection, userInfo, iodFile, ryoikiKeikakushoPk);

            if (log.isDebugEnabled()) {
                log.debug("-->>�Y�t�t�@�C����ϊ����܂��B");
            }
            //------------------
            //�Y�t�t�@�C��PDF�ϊ�
            //------------------
            convertRyoikiKeikakushoTenpuFile(connection, userInfo, ryoikiKeikakushoPk);

            if (log.isDebugEnabled()) {
                log.debug("--<<�Y�t�t�@�C����ϊ����܂��B");
            }      
    
        } catch (DataAccessException e) {
            throw new ConvertException("�\���f�[�^���̎擾�Ɏ��s���܂����B",
                    new ErrorInfo("errors.8003"),e);
        } catch (IOException e) {
            throw new ConvertException("�ϊ��t�@�C���̕ۑ��Ɏ��s���܂����B(�t�@�C��IO)",
                    new ErrorInfo("errors.8003"),e);
        } 
    }
//2006/07/21�@�c�@�ǉ������܂�        
}