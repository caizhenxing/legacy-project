/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.common;

/**
 * �A�v���P�[�V�����ݒ�t�@�C���L�[���`����B
 * 
 * ID RCSfile="$RCSfile: ISettingKeys.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public interface ISettingKeys {
	
	/** ���ʐݒ�t�@�C���̃��\�[�X�o���h�����@*/
	public static final String BUNDLE_NAME = "ApplicationSettings";
	
	/** �ϐ��p�����[�^�� */
	public static final String VARIABLE_PARAM = "VARIABLE_PARAM";
	
	/** �ϐ��̒l */
	public static final String VARIABLE_VALUE = "VARIABLE_VALUE";
	
	//------------
	
	/** PDF�I�[�g�R���o�[�^ IN�t�H���_(�ϊ��������t�@�C�������̃t�H���_�Ɋi�[)�B�@*/
	public static final String PDF_IN_FOLDER = "PDF_IN_FOLDER";

	/** PDF�I�[�g�R���o�[�^ OUT�t�H���_(�ϊ������t�@�C�������̃t�H���_�Ɏ����ŏo��)�B�@*/
	public static final String PDF_OUT_FOLDER = "PDF_OUT_FOLDER";

	/** PDF�I�[�g�R���o�[�^ STATUS�t�H���_(�ϊ������X�e�[�^�X�����̃t�H���_�Ɏ����ŏo��)�B */
	public static final String PDF_STATUS_FOLDER = "PDF_STATUS_FOLDER";

	/** PDF�I�[�g�R���o�[�^ ERR�t�H���_(�ϊ��Ɏ��s�����ꍇ�A�ϊ����t�@�C�������̃t�H���_�ɕۑ�)�B�@*/
	public static final String PDF_ERR_FOLDER = "PDF_ERR_FOLDER";

	/** PDF�I�[�g�R���o�[�^ PDF�ϊ��t�@�C���Ď��Ԋu(s) */
	public static final String PDF_REFRESH_SECONDS = "PDF_REFRESH_SECONDS";

	/** PDF�o��(WEBDOC)�ݒ���t�@�C��(���Ƌ敪�ƒ��[�e���v���[�g�̊֘A�t�@�C��) */
	public static final String PDF_REPORT_SETTING_FILE_PATH = "PDF_REPORT_SETTING_FILE_PATH";

	/** PDF�o��(WEBDOC)��ƃt�H���_ */
	public static final String PDF_WORK_FOLDER = "PDF_WORK_FOLDER";

	/** 
	 * WORD�t�@�C����PDF�ϊ��^�C���A�E�g(�b) 
	 * 0�̏ꍇ�̓^�C���A�E�g���Ȃ��B
	 */
	public static final String PDF_TIMEOUT = "PDF_TIMEOUT";
	
	/** PDF�ϊ��T�[�u���b�gURL */	
	public static final String PDF_CONV_SERVLET_URL = "PDF_CONV_SERVLET_URL";

	//2006.07.03 iso PDF�ϊ��T�[�o�U�蕪�������̂��ߒǉ�
	/** PDF�ϊ��T�[�u���b�gURL�̏d�ݕt�� */	
	public static final String PDF_CONV_SERVLET_WEIGHTS = "PDF_CONV_SERVLET_WEIGHTS";

	
	//2006.07.03 iso �Y�t�t�@�C���ϊ��T�[�o�U�蕪�������̂��ߒǉ�
	public static final String ANNEX_CONV_SERVLET_URL = "ANNEX_CONV_SERVLET_URL";

	public static final String ANNEX_CONV_SERVLET_WEIGHTS = "ANNEX_CONV_SERVLET_WEIGHTS";
	
	
	/** �\����XML�t�@�C���i�[�t�H���_(�{�t�H���_�z���Ɂu����ID\�V�X�e����t�ԍ�\xml�v�Ƒ���) */
	public static final String SHINSEI_XML_FOLDER = "SHINSEI_XML_FOLDER";
    
    /** XML�e���v���[�g�t�@�C��(Shift_JIS) */
    public static final String SHINSEI_XML_TEMPLATE = "SHINSEI_XML_TEMPLATE";

	/** �\����PDF�t�@�C���i�[�t�H���_(�{�t�H���_�z���Ɂu����ID\�V�X�e����t�ԍ�\pdf�v�Ƒ���) */
	public static final String SHINSEI_PDF_FOLDER = "SHINSEI_PDF_FOLDER";

    //2006.09.25 iso iso �^�C�g���Ɂu�T�v�v������PDF�쐬�̂���
	/** �̈�v�揑PDF�t�@�C��(�T�v��)�i�[�t�H���_(�{�t�H���_�z���Ɂu����ID�v�Ƒ���) */
	public static final String RG_PDF_FOLDER = "RG_PDF_FOLDER";

// 2006/06/27 dyh add start
    /** �̈�v�揑�\��PDF�t�@�C���i�[�t�H���_(�{�t�H���_�z���Ɂu����ID\���̈�ԍ�\pdf�v�Ƒ���) */
    public static final String PDF_DOMAINCOVER = "PDF_DOMAINCOVER";
// 2006/06/27 dyh add end

	/** �\�����Y�t�t�@�C���i�[�t�H���_(�{�t�H���_�z���Ɂu����ID\�V�X�e����t�ԍ�\word�v�Ƒ���) */
	public static final String SHINSEI_ANNEX_FOLDER = "SHINSEI_ANNEX_FOLDER";

	/** �\�����Y�tPDF�t�@�C���i�[�t�H���_(�{�t�H���_�z���Ɂu����ID\�V�X�e����t�ԍ�\word�v�Ƒ���) */
	public static final String SHINSEI_ANNEX_PDF_FOLDER = "SHINSEI_ANNEX_PDF_FOLDER";

	/** �\�����Y�t�G���[PDF�t�@�C���i�[�t�H���_(�����s�\�̃G���[PDF�����̃t�H���_�Ɋi�[����) */
	public static final String SHINSEI_ANNEX_ERR_FOLDER = "SHINSEI_ANNEX_ERR_FOLDER";
	
	/** �\�������E���t�@�C���i�[�t�H���_�i{0}=����ID,{1}=�V�X�e����t�ԍ��j */
	public static final String SHINSEI_SUISEN_FOLDER = "SHINSEI_SUISEN_FOLDER";

	/** �\����PDF�t�@�C���i�p�X���[�h���b�N�����Łj�i�[�t�H���_�i{0}=����ID,{1}=���Ƃ��Ƃ̖����K���j*/
	public static final String SHINSEI_PDF_NO_PASSWORD = "SHINSEI_PDF_NO_PASSWORD";

	/** �\�����e���͗p�t�@�C���iWindows�p�j(�{�t�H���_�z���Ɂu����ID\win\����ID.doc�v�Ƒ���) */
	public static final String SHINSEI_TENPUWIN_FOLDER = "SHINSEI_TENPUWIN_FOLDER";

	/** �\�����e���͗p�t�@�C���iMac�p�j(�{�t�H���_�z���Ɂu����ID\win\����ID.doc�v�Ƒ���) */
	public static final String SHINSEI_TENPUMAC_FOLDER = "SHINSEI_TENPUMAC_FOLDER";

	/** �]���p�t�@�C��(�{�t�H���_�z���Ɂu����ID\\����ID.doc�v�Ƒ���) */
	public static final String SHINSEI_HYOKA_FOLDER = "SHINSEI_HYOKA_FOLDER";
	
	/** ���ރt�H���_(�{�t�H���_�z���Ɂu����ID\\�Ώ۔ԍ�\\�V�X�e����t�ԍ�.pdf�v�Ƒ���) */
	public static final String SHINSEI_SHORUI_FOLDER = "SHINSEI_SHORUI_FOLDER";
	
	/** �R�����ʃt�@�C���i����ID\\�V�X�e����t�ԍ�\\shinsa\\�R�����ԍ�.doc�j*/
	public static final String SHINSEI_KEKKA_FOLDER = "SHINSEI_KEKKA_FOLDER";
	
	//2005/04/13 �ǉ� ��������-------------------------------------------------------------------
	//PDF�\���t�@�C���p�X�擾�̂���
	
	/** PDF�\���t�@�C�� */
	public static final String PDF_COVER = "PDF_COVER";
	
	//�ǉ� �����܂�------------------------------------------------------------------------------
	
	//--------- <���[���֌W�ݒ���> ---------------
	/** ���[���T�[�o�A�h���X */
	public static final String SMTP_SERVER_ADDRESS = "SMTP_SERVER_ADDRESS"; 
	/** ���o�l�i���ꂵ�ĂP�j */
	public static final String FROM_ADDRESS = "FROM_ADDRESS";
	/** �A���[�g�ʒm�p���[���A�h���X */
	public static final String TO_ADDRESS_FOR_ALERT = "TO_ADDRESS_FOR_ALERT";
	
	/** ���[�����e�i�\���҂��\�����m�F�����������Ƃ��j�u�����v */
	public static final String SUBJECT_SHINSEISHO_KAKUNIN_KANRYO = "SUBJECT_SHINSEISHO_KAKUNIN_KANRYO";
	/** ���[�����e�i�\���҂��\�����m�F�����������Ƃ��j�u�{���v */
	public static final String CONTENT_SHINSEISHO_KAKUNIN_KANRYO = "CONTENT_SHINSEISHO_KAKUNIN_KANRYO";
    
//2006/06/27 �ǉ��@���`�؁@��������    
    /** ���[�����e�i����҂����̈�ԍ����s����o�^�����Ƃ��j�u�����v */
    public static final String SUBJECT_KARIRYOIKINO_KAKUNIN_IRAI = "SUBJECT_KARIRYOIKINO_KAKUNIN_IRAI";
    /** ���[�����e�i����҂����̈�ԍ����s����o�^�����Ƃ��j�u�{���v */
    public static final String CONTENT_KARIRYOIKINO_KAKUNIN_IRAI = "CONTENT_KARIRYOIKINO_KAKUNIN_IRAI";
//2006/06/27 �ǉ��@���`�؁@�����܂�
 
//2006/06/29  �ǉ��@�������@��������  
    /** ���[�����e�i�̈��\�҂������v�撲�����p�������Ƃ��j�u�����v */
    public static final String SUBJECT_RYOIKIDAIHYOSHA_KYAKKA = "SUBJECT_RYOIKIDAIHYOSHA_KYAKKA";
    /** ���[�����e�i�̈��\�҂������v�撲�����p�������Ƃ��j�u�{���v */
    public static final String CONTENT_RYOIKIDAIHYOSHA_KYAKKA = "CONTENT_RYOIKIDAIHYOSHA_KYAKKA";
    
    /** �m�F�\�������ߐ؂�����܂ł̓��t */
    public static final String DATE_BY_KAKUNIN_TOKUSOKU = "DATE_BY_KAKUNIN_TOKUSOKU";
    /**���[�����e�i�����@�֒S���҂ւ̊m�F�ʒm�j�u�����v */
    public static final String SUBJECT_SHINSEISHO_KAKUNIN_TOKUSOKU = "SUBJECT_SHINSEISHO_KAKUNIN_TOKUSOKU";
    /**���[�����e�i�����@�֒S���҂ւ̊m�F�ʒm�j�u�{���v */
    public static final String CONTENT_SHINSEISHO_KAKUNIN_TOKUSOKU = "CONTENT_SHINSEISHO_KAKUNIN_TOKUSOKU";
    
    /** ���F�\�������ߐ؂�����܂ł̓��t */
    public static final String DATE_BY_SHONIN_TOKUSOKU = "DATE_BY_KAKUNIN_TOKUSOKU";
    /**���[�����e�i�����@�֒S���҂ւ̏��F�ʒm�j�u�����v */
    public static final String SUBJECT_RYOIKIGAIYO_SHONIN_TOKUSOKU = "SUBJECT_RYOIKIGAIYO_SHONIN_TOKUSOKU";
    /**���[�����e�i�����@�֒S���҂ւ̏��F�ʒm�j�u�{���v */
    public static final String CONTENT_RYOIKIGAIYO_SHONIN_TOKUSOKU = "CONTENT_RYOIKIGAIYO_SHONIN_TOKUSOKU";
//2006/06/29  �ǉ��@�������@�����܂�   
    
	/** ���[�����e�i�����@�ւ��\���������F�����Ƃ��j�u�����v */
	public static final String SUBJECT_SHINSEISHO_SHOZOKUKIKAN_SHONIN = "SUBJECT_SHINSEISHO_SHOZOKUKIKAN_SHONIN";
	/** ���[�����e�i�����@�ւ��\���������F�����Ƃ��j�u�{���v */
	public static final String CONTENT_SHINSEISHO_SHOZOKUKIKAN_SHONIN = "CONTENT_SHINSEISHO_SHOZOKUKIKAN_SHONIN";
	/** ���[�����e�i�����@�ւ��`�F�b�N���X�g���m�肵���Ƃ��j�u�����v */
	public static final String SUBJECT_CHECKLIST_KAKUTEI = "SUBJECT_CHECKLIST_KAKUTEI";
	/** ���[�����e�i�����@�ւ��`�F�b�N���X�g���m�肵���Ƃ��j�u�{���v */
	public static final String CONTENT_CHECKLIST_KAKUTEI = "CONTENT_CHECKLIST_KAKUTEI";

	/** �R���Ñ������܂ł̓��t */
	public static final String DATE_BY_SHINSA_KIGEN = "DATE_BY_SHINSA_KIGEN";
	/**���[�����e�i�R�����ւ̐R���Ñ��j�u�����v */
	public static final String SUBJECT_SHINSEISHO_SHINSA_SAISOKU = "SUBJECT_SHINSEISHO_SHINSA_SAISOKU";
	/**���[�����e�i�R�����ւ̐R���Ñ��j�u�{���v */
	public static final String CONTENT_SHINSEISHO_SHINSA_SAISOKU = "CONTENT_SHINSEISHO_SHINSA_SAISOKU";

	/** �����F�\�������ߐ؂�����܂ł̓��t */
	public static final String DATE_BY_SHONIN_KIGEN = "DATE_BY_SHONIN_KIGEN";
	/**���[�����e�i�����@�֒S���҂ւ̖����F�m�F�ʒm�j�u�����v */
	public static final String SUBJECT_SHINSEISHO_SHONIN_TSUCHI = "SUBJECT_SHINSEISHO_SHONIN_TSUCHI";
	/**���[�����e�i�����@�֒S���҂ւ̖����F�m�F�ʒm�j�u�{���v */
	public static final String CONTENT_SHINSEISHO_SHONIN_TSUCHI = "CONTENT_SHINSEISHO_SHONIN_TSUCHI";
	
	/** ���[�����e�i�R�������R�����������Ƃ��j�u�����v */
	public static final String SUBJECT_SHINSAKEKKA_JURI_TSUCHI = "SUBJECT_SHINSAKEKKA_JURI_TSUCHI";
	/** ���[�����e�i�R�������R�����������Ƃ��j�u�{���v */
	public static final String CONTENT_SHINSAKEKKA_JURI_TSUCHI = "CONTENT_SHINSAKEKKA_JURI_TSUCHI";
	
	
	//--------- <�w�U�₢���킹����> ---------------	
	/** �w�U�₢���킹��X�֔ԍ� */
	public static final String GAKUSHIN_TOIAWASE_YUBIN = "GAKUSHIN_TOIAWASE_YUBIN";
	/** �w�U�₢���킹��Z�� */	
	public static final String GAKUSHIN_TOIAWASE_JUSHO = "GAKUSHIN_TOIAWASE_JUSHO";

	//----------------------------------------------
	/** �f�[�^�\�[�X�^�C�v */
	public static final String DB_DATA_SOURCE_TYPE = "DB_DATA_SOURCE_TYPE";
	/** �f�[�^�x�[�XURL */
	public static final String DB_URL = "DB_URL";
	/** �f�[�^�x�[�X���[�U�� */
	public static final String DB_USER = "DB_USER";
	/** �f�[�^�x�[�X�p�X���[�h */
	public static final String DB_PASSWORD = "DB_PASSWORD";
	/** MIN�R�l�N�V�������̐ݒ� */
	public static final String DB_MIN_LIMIT = "DB_MIN_LIMIT";
	/** MAX�R�l�N�V������ */
	public static final String DB_MAX_LIMIT = "DB_MAX_LIMIT";
	/** �����R���e�L�X�g�t�@�N�g�� */
	public static final String DB_INITIAL_CONTEXT_FACTORY = "DB_INITIAL_CONTEXT_FACTORY";
	/** �f�[�^�\�[�X�T�[�r�X�v���o�C�_ */
	public static final String DB_PROVIDER_URL = "DB_PROVIDER_URL";
	/** �f�[�^�\�[�X�� */
	public static final String DB_DATA_SOURCE_NAME = "DB_DATA_SOURCE_NAME";
	
	
	//-----------------------------------------------
	/** �Ɩ��T�[�oURL */
	public static final String GYOMU_SERVLET_URL = "GYOMU_SERVLET_URL";
	
	
	//--------- <�\������> ---------------
	/** �V�X�e����t�ԍ����g���C�� */		
	public static final String SYSTEM_NO_MAX_RETRY_COUNT = "SYSTEM_NO_MAX_RETRY_COUNT";
	/** �d���\���`�F�b�N�t���O */
	public static final String CHECK_DUPLICACATION_FLAG = "CHECK_DUPLICACATION_FLAG";
	/** �Y�t�t�@�C�����M�����E�\���o�^�������������s�񐔁i1�b�Ԋu�j*/
	public static final String TRY_COUNT_SYNCHRONIZE    = "TRY_COUNT_SYNCHRONIZE";
	//2005/04/18 �ǉ� ��������-------------------------------------------------------------------
	//�`�F�b�N�f�W�b�g�`�F�b�N���t���O�Ő��䂷�邽��
	/** �`�F�b�N�f�W�b�g�t���O */
	public static final String CHECK_DIGIT_FLAG = "CHECK_DIGIT_FLAG";
	//�ǉ� �����܂�------------------------------------------------------------------------------
	
	//--------- <�R���˗��ʒm���o�͏���> ---------------
	/** �R���˗��ʒm���t�@�C���i�[�t�H���_ */		
	public static final String IRAI_WORK_FOLDER = "IRAI_WORK_FOLDER";
	/** �R���˗����t�H�[�}�b�g�p�X */
	public static final String IRAI_FORMAT_PATH = "IRAI_FORMAT_PATH";
	/** �R���˗����t�H�[�}�b�g�� */
	public static final String IRAI_FORMAT_FILE_NAME = "IRAI_FORMAT_FILE_NAME";
	//--------- <�R���˗��ʒm���i�R�����Ǘ��p�j�o�͏���> ---------------
	/** �R���˗��ʒm���i�R�����Ǘ��p�j�t�@�C���i�[�t�H���_ */		
	public static final String IRAI_WORK_FOLDER2 = "IRAI_WORK_FOLDER2";
	/** �R���˗����i�R�����Ǘ��p�j�t�H�[�}�b�g�p�X */
	public static final String IRAI_FORMAT_PATH2 = "IRAI_FORMAT_PATH2";
	/** �R���˗����i�R�����Ǘ��p�j�t�H�[�}�b�g�� */
	public static final String IRAI_FORMAT_FILE_NAME2 = "IRAI_FORMAT_FILE_NAME2";
	//--------- <ID�p�X���[�h�ʒm���o�͏���> ---------------
	/** ID�p�X���[�h�ʒm���t�@�C���i�[�t�H���_ */		
	public static final String SHINSEISHA_WORK_FOLDER = "SHINSEISHA_WORK_FOLDER";
	/** ID�p�X���[�h�ʒm���t�H�[�}�b�g�p�X */
	public static final String SHINSEISHA_FORMAT_PATH = "SHINSEISHA_FORMAT_PATH";
	/** ID�p�X���[�h�ʒm���t�H�[�}�b�g�� */
	public static final String SHINSEISHA_FORMAT_FILE_NAME = "SHINSEISHA_FORMAT_FILE_NAME";
	

	//--------- <�]�����ʏo�͏���> ---------------
	/** �]�����ʃt�@�C���i�[�t�H���_ */		
	public static final String HYOKA_WORK_FOLDER = "HYOKA_WORK_FOLDER";
	/** �]�����ʃt�H�[�}�b�g�p�X */
	public static final String HYOKA_FORMAT_PATH = "HYOKA_FORMAT_PATH";
	/** �]�����ʃt�H�[�}�b�g�� */
	public static final String HYOKA_FORMAT_FILE_NAME = "HYOKA_FORMAT_FILE_NAME";
	
	
	//--------- <����U��`�F�b�N�˗��ʒm���o�͏���> ---------------
	/** �`�F�b�N�˗��ʒm���t�@�C���i�[�t�H���_ */		
	public static final String CHECKIRAI_WORK_FOLDER = "CHECKIRAI_WORK_FOLDER";
	/** �`�F�b�N�˗����t�H�[�}�b�g�p�X */
	public static final String CHECKIRAI_FORMAT_PATH = "CHECKIRAI_FORMAT_PATH";
	/** �`�F�b�N�˗����t�H�[�}�b�g�� */
	public static final String CHECKIRAI_FORMAT_FILE_NAME = "CHECKIRAI_FORMAT_FILE_NAME";
	
	//--------- <���发�ނ̒�o���o�͐ݒ菈��> ---------------
	/** ���发�ނ̒�o���t�@�C���i�[�t�H���_ */		
	public static final String OUBO_WORK_FOLDER = "OUBO_WORK_FOLDER";
	/** ���发�ނ̒�o���t�H�[�}�b�g�p�X */
	public static final String OUBO_FORMAT_PATH = "OUBO_FORMAT_PATH";
	/** ���发�ނ̒�o���t�H�[�}�b�g�� */
	public static final String OUBO_FORMAT_FILE_NAME_KIBAN = "OUBO_FORMAT_FILE_NAME_KIBAN";
	public static final String OUBO_FORMAT_FILE_NAME_TOKUTEI = "OUBO_FORMAT_FILE_NAME_TOKUTEI";
    public static final String OUBO_FORMAT_FILE_NAME_TOKUTEI_SHINKI = "OUBO_FORMAT_FILE_NAME_TOKUTEI_SHINKI";
	public static final String OUBO_FORMAT_FILE_NAME_WAKATESTART = "OUBO_FORMAT_FILE_NAME_WAKATESTART";
	public static final String OUBO_FORMAT_FILE_NAME_SHOKUSHINHI = "OUBO_FORMAT_FILE_NAME_SHOKUSHINHI";
	
	//--------- <�}�X�^�捞> ---------------
	/** DB�G�N�X�|�[�g�R�}���h */		
	public static final String EXPORT_COMMAND = "EXPORT_COMMAND";
	/** CSV��荞�ݐ� */
	public static final String CSV_TORIKOMI_LOCATION = "CSV_TORIKOMI_LOCATION";	
	
	//---------<�p���`�f�[�^>---------------
	/** �p����f�[�^�ۊǐ� */
	public static final String PUNCHDATA_HOKAN_LOCATION = "PUNCHDATA_HOKAN_LOCATION";
	
	//--------- <�f�[�^�ۊǐݒ�> ---------------
	/** �f�[�^�ۊǃT�[�oDB�����N�� */
	public static final String HOKAN_SERVER_DB_LINK = "HOKAN_SERVER_DB_LINK";
	/** �f�[�^�ۊǃT�[�oUNC */
	public static final String HOKAN_SERVER_UNC = "HOKAN_SERVER_UNC";
	/** UNC�ɕϊ�����h���C�u���^�[ */
	public static final String DRIVE_LETTER_CONVERTED_TO_UNC = "DRIVE_LETTER_CONVERTED_TO_UNC";
	/** �f�[�^�ۊǃT�[�o�ɃR�s�[����f�B���N�g�� */
	public static final String HOKAN_TARGET_DIRECTORY = "HOKAN_TARGET_DIRECTORY";
	
	//--------- <CertWorker CSV�t�H�[�}�b�g�ݒ�> ---------------
	/** profile name */
	public static final String PROFILE_NAME = "PROFILE_NAME";
	/** subject DN */
	public static final String SUBJECT_DN = "SUBJECT_DN";	
	/** subjectAltName */
	public static final String SUBJECT_ALT_NAME = "SUBJECT_ALT_NAME";
	/** pubkey algo */
	public static final String PUBKEY_ALGO = "PUBKEY_ALGO";
	/** key length */
	public static final String KEY_LENGTH = "KEY_LENGTH";
	/** p12 flag */
	public static final String P12_FLAG = "P12_FLAG";	
		
    //--------- <Velocity�ݒ�> ---------------
    /** Velocity�\�����ɗ�O�����������Ƃ��ɕ\������y�[�WURL(�R���e�L�X�g���[�g����̑��΃p�X) */
    public static final String VM_ERROR_PAGE = "VM_ERROR_PAGE";   
    
	//--------- <�ꗗ�ݒ�> ---------------
	/** �ꗗ��ʂɂ�����y�[�W�T�C�Y�� */
	public static final String PAGE_SIZE = "PAGE_SIZE";   
	/** #�Y��������MAX�l */
	public static final String MAX_RECORD_COUNT = "MAX_RECORD_COUNT";   	
	
	
	//--------- <�������`�F�b�N�ݒ�> ---------------
	/** �g�p�������`�F�b�N�䗦�i�p�[�Z���e�[�W�j*/
	public static final String MAX_MEMORY_USED_RATE = "MAX_MEMORY_USED_RATE";
	/**Sorry�y�[�W�Ƃ��ĕ\��������(�R���e�L�X�g���[�g����̑��΃p�X)*/
	public static final String MAX_MEMORY_ERROR_PAGE = "MAX_MEMORY_ERROR_PAGE";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
