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
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * 
 */
public class JigyoKanriForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ����ID */
	private String jigyoId;

	/** �N�x */
	private String nendo;

	/** �� */
	private String kaisu;
	
	/** ���ƃR�[�h */
	private String jigyoCd;

	/** �Ɩ��S���� */
	private String tantokaName;

	/** �Ɩ��S���W�� */
	private String tantoKakari;

	/** �₢���킹��S���Җ� */
	private String toiawaseName;

	/** �₢���킹��d�b�ԍ� */
	private String toiawaseTel;

	/** �₢���킹��E-mail */
	private String toiawaseEmail;

	/** �w�U��t���ԁi�J�n�j(�N) */
	private String uketukekikanStartYear;

	/** �w�U��t���ԁi�J�n�j(��) */
	private String uketukekikanStartMonth;

	/** �w�U��t���ԁi�J�n�j(��) */
	private String uketukekikanStartDay;

	/** �w�U��t���ԁi�I���j(�N) */
	private String uketukekikanEndYear;

	/** �w�U��t���ԁi�I���j(��) */
	private String uketukekikanEndMonth;

	/** �w�U��t���ԁi�I���j(��) */
	private String uketukekikanEndDay;

	/** �����Җ���o�^�ŏI���ؓ�(�N) */
	private String meiboDateYear;

	/** �����Җ���o�^�ŏI���ؓ�(��) */
	private String meiboDateMonth;

	/** �����Җ���o�^�ŏI���ؓ�(��) */
	private String meiboDateDay;

	// 2006/06/14 �ǉ� ���`�� ��������
	/** ���̈�ԍ����s���ؓ�(�N) */
	private String kariryoikiNoEndDateYear;

	/** ���̈�ԍ����s���ؓ�(��) */
	private String kariryoikiNoEndDateMonth;

	/** ���̈�ԍ����s���ؓ�(��) */
	private String kariryoikiNoEndDateDay;

	// 2006/06/14 �ǉ� ���`�� �����܂�

	// 2006/07/10 �ǉ� ���`�� ��������
	/** �̈��\�Ҋm����ؓ�(�N) */
	private String ryoikiEndDateYear;

	/** �̈��\�Ҋm����ؓ�(��) */
	private String ryoikiEndDateMonth;

	/** �̈��\�Ҋm����ؓ�(��) */
	private String ryoikiEndDateDay;
	// 2006/07/10 �ǉ� ���`�� �����܂�

	// 2006/10/23 �ǉ� �Ո� ��������
	/** ���Q�֌W���͒��ؓ�(�N) */
	private String rigaiEndDateYear;

	/** ���Q�֌W���͒��ؓ�(��) */
	private String rigaiEndDateMonth;

	/** ���Q�֌W���͒��ؓ�(��) */
	private String rigaiEndDateDay;
	// 2006/10/23 �ǉ� �Ո� �����܂�

	/** �R������(�N) */
	private String shinsaKigenYear;

	/** �R������(��) */
	private String shinsaKigenMonth;

	/** �R������(��) */
	private String shinsaKigenDay;

	/** �Y�t������ */
	private String tenpuName;

	/** �Y�t�t�@�C��(Win) */
	private String tenpuWin;

	/** �Y�t�t�@�C��(Mac) */
	private String tenpuMac;

	/** �]���p�t�@�C���L�� */
	private String hyokaFileFlg;

	/** �]���p�t�@�C�� */
	private String hyokaFile;

	/** ���J�t���O */
	private String kokaiFlg;

	/** ���J���ٔԍ� */
	private String kessaiNo;

	/** ���J�m���ID */
	private String kokaiID;

	/** ���l */
	private String biko;

	/** �폜�t���O */
	private String delFlag;
    
// 2007/02/07 ���u�j�@�ǉ���������
    /** ������e�t�@�C���y�[�W���͈�(����) */
    private String pageFrom;
    
    /** ������e�t�@�C���y�[�W���͈�(���) */
    private String pageTo;

// 2007/02/07 ���u�j�@�ǉ������܂�
	//-----
	
	/** ���Ɩ��I�����X�g */
	private List jigyoNameList = new ArrayList();

	/** �]���p�t�@�C���L���i�Ȃ��^����j���X�g */
	private List flgList = new ArrayList();
		
	/** �Y�t�t�@�C��(Win)(�A�b�v���[�h�t�@�C��) */
	private FormFile tenpuWinUploadFile;

	/** �Y�t�t�@�C��(Mac)(�A�b�v���[�h�t�@�C��) */
	private FormFile tenpuMacUploadFile;

	/** �]���p�t�@�C��(�A�b�v���[�h�t�@�C��) */
	private FormFile hyokaUploadFile;
	
	/** �_�E�����[�h�t�@�C���t���O */
	private String downloadFileFlg;	

	//�E�E�E�E�E�E�E�E�E�E

	//2005/04/19 �ǉ� ��������----------------------------------------------
	//���R URL�̒ǉ��̂���
	
	/** URL�^�C�g�� */
	private String urlTitle;
	
	/** URL�A�h���X */
	private String urlAddress;

	/**�@�_�E�����[�hURL */
	private String dlUrl;

	//---------------------------------------------------------------------
	// Constructors
	// ---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public JigyoKanriForm() {
		super();
		init();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/*
	 * �����������B (�� Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//�E�E�E
	}

	/**
	 * ����������
	 */
	public void init() {
		jigyoId = "";
		nendo = "";
		kaisu = "";
		jigyoCd = "";
		tantokaName = "";
		tantoKakari = "";
		toiawaseName = "";
		toiawaseTel = "";
		toiawaseEmail = "";
		uketukekikanStartYear = "";
		uketukekikanStartMonth = "";
		uketukekikanStartDay = "";
		uketukekikanEndYear = "";
		uketukekikanEndMonth = "";
		uketukekikanEndDay = "";
		shinsaKigenYear = "";
		shinsaKigenMonth = "";
		shinsaKigenDay = "";
		meiboDateYear = "";
		meiboDateMonth = "";
		meiboDateDay = "";

		// 2006/06/14 �ǉ� ���`�� ��������
		kariryoikiNoEndDateYear = "";
		kariryoikiNoEndDateMonth = "";
		kariryoikiNoEndDateDay = "";
		// 2006/06/14 �ǉ� ���`�� �����܂�
		// 2006/07/10�ǉ� ���`�� ��������
		ryoikiEndDateYear = "";
		ryoikiEndDateMonth = "";
		ryoikiEndDateDay = "";
		// 2006/07/10 �ǉ� ���`�� �����܂�
		// 2006/10/23 �ǉ� �Ո� ��������
		rigaiEndDateYear = "";
		rigaiEndDateMonth = "";
		rigaiEndDateDay = "";
		// 2006/10/23 �ǉ� �Ո� �����܂�
		
		tenpuName = "";
		tenpuWin = "";
		tenpuMac = "";
		hyokaFileFlg = "0";//�����\���u�Ȃ��v
		hyokaFile = "";
		kokaiFlg = "";
		kessaiNo = "";
		kokaiID = "";
		biko = "";
		delFlag = "";
		downloadFileFlg = "0";// �\�����e�t�@�C���iWin�j
// 2007/02/07 ���u�j�@�ǉ���������       
        pageFrom = "";
        pageTo = "";    
// 2007/02/07 ���u�j�@�ǉ������܂�
	}

	/*
	 * ���̓`�F�b�N�B (�� Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		//��^����----- 
		ActionErrors errors = super.validate(mapping, request);

		//---------------------------------------------
		// ��{�I�ȃ`�F�b�N(�K�{�A�`�����j��Validator���g�p����B
		// ---------------------------------------------
// 2007/02/07 ���u�j�@�ǉ���������
        // ������e�t�@�C���y�[�W��������������e�t�@�C���y�[�W������̏ꍇ�̓G���[�Ƃ���    
        this.setPageFrom(pageFrom.trim());
        this.setPageTo(pageTo.trim());
        if(errors.isEmpty()){            
            if (!StringUtil.isBlank(pageFrom) && !StringUtil.isBlank(pageTo)){  
                
                if (Integer.parseInt(pageFrom) > Integer.parseInt(pageTo)){                  
                     errors.add(ActionErrors.GLOBAL_ERROR,
                            new ActionError("errors.9032"));
                }
            }      
        }         
// 2007/02/07�@���u�j�@�ǉ������܂�        
		// ��{���̓`�F�b�N�����܂�
		if (!errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.2009"));
		}
		// ��^����-----

		// �ǉ�����-----
		// ---------------------------------------------
		// �g�ݍ��킹�`�F�b�N
		// ---------------------------------------------

		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
     * ���l���擾
     * @return ���l
	 */
	public String getBiko() {
		return biko;
	}

    /**
     * ���l��ݒ�
     * @param string ���l
     */
    public void setBiko(String string) {
        biko = string;
    }

    /**
     * �폜�t���O���擾
     * @return �폜�t���O
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * �폜�t���O��ݒ�
     * @param string �폜�t���O
     */
    public void setDelFlag(String string) {
        delFlag = string;
    }

    /**
     * �_�E�����[�h�t�@�C���t���O���擾
     * @return �_�E�����[�h�t�@�C���t���O
     */
    public String getDownloadFileFlg() {
        return downloadFileFlg;
    }

    /**
     * �_�E�����[�h�t�@�C���t���O��ݒ�
     * @param string �_�E�����[�h�t�@�C���t���O
     */
    public void setDownloadFileFlg(String string) {
        downloadFileFlg = string;
    }

    /**
     * �]���p�t�@�C�����擾
     * @return �]���p�t�@�C��
     */
    public String getHyokaFile() {
        return hyokaFile;
    }

    /**
     * �]���p�t�@�C����ݒ�
     * @param string �]���p�t�@�C��
     */
    public void setHyokaFile(String string) {
        hyokaFile = string;
    }

    /**
     * �]���p�t�@�C���L�����擾
     * @return �]���p�t�@�C���L��
     */
    public String getHyokaFileFlg() {
        return hyokaFileFlg;
    }

    /**
     * �]���p�t�@�C���L����ݒ�
     * @param string �]���p�t�@�C���L��
     */
    public void setHyokaFileFlg(String string) {
        hyokaFileFlg = string;
    }

    /**
     * �]���p�t�@�C�����擾
     * @return �]���p�t�@�C��
     */
    public FormFile getHyokaUploadFile() {
        return hyokaUploadFile;
    }

    /**
     * �]���p�t�@�C����ݒ�
     * @param file �]���p�t�@�C��
     */
    public void setHyokaUploadFile(FormFile file) {
        hyokaUploadFile = file;
    }

    /**
     * ���ƃR�[�h���擾
     * @return ���ƃR�[�h
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * ���ƃR�[�h��ݒ�
     * @param string ���ƃR�[�h
     */
    public void setJigyoCd(String string) {
        jigyoCd = string;
    }

    /**
     * ����ID���擾
     * @return ����ID
     */
    public String getJigyoId() {
        return jigyoId;
    }

    /**
     * ����ID��ݒ�
     * @param string ����ID
     */
    public void setJigyoId(String string) {
        jigyoId = string;
    }

    /**
     * ���Ɩ��I�����X�g���擾
     * @return ���Ɩ��I�����X�g
     */
    public List getJigyoNameList() {
        return jigyoNameList;
    }

    /**
     * �񐔂��擾
     * @return ��
     */
    public String getKaisu() {
        return kaisu;
    }

    /**
     * �񐔂�ݒ�
     * @param string ��
     */
    public void setKaisu(String string) {
        kaisu = string;
    }

    /**
     * ���J���ٔԍ����擾
     * @return ���J���ٔԍ�
     */
    public String getKessaiNo() {
        return kessaiNo;
    }

    /**
     * ���J���ٔԍ���ݒ�
     * @param string ���J���ٔԍ�
     */
    public void setKessaiNo(String string) {
        kessaiNo = string;
    }

    /**
     * ���J�t���O���擾
     * @return ���J�t���O
     */
    public String getKokaiFlg() {
        return kokaiFlg;
    }

    /**
     * ���J�t���O��ݒ�
     * @param string ���J�t���O
     */
    public void setKokaiFlg(String string) {
        kokaiFlg = string;
    }

    /**
     * ���J�m���ID���擾
     * @return ���J�m���ID
     */
    public String getKokaiID() {
        return kokaiID;
    }

    /**
     * ���J�m���ID��ݒ�
     * @param string ���J�m���ID
     */
    public void setKokaiID(String string) {
        kokaiID = string;
    }

    /**
     * �N�x���擾
     * @return �N�x
     */
    public String getNendo() {
        return nendo;
    }

    /**
     * �N�x��ݒ�
     * @param string �N�x
     */
    public void setNendo(String string) {
        nendo = string;
    }

    /**
     * �R������(��)���擾
     * @return �R������(��)
     */
    public String getShinsaKigenDay() {
        return shinsaKigenDay;
    }

    /**
     * �R������(��)��ݒ�
     * @param string �R������(��)
     */
    public void setShinsaKigenDay(String string) {
        shinsaKigenDay = string;
    }

    /**
     * �R������(��)���擾
     * @return �R������(��)
     */
    public String getShinsaKigenMonth() {
        return shinsaKigenMonth;
    }

    /**
     * �R������(��)��ݒ�
     * @param string �R������(��)
     */
    public void setShinsaKigenMonth(String string) {
        shinsaKigenMonth = string;
    }

    /**
     * �R������(�N)���擾
     * @return �R������(�N)
     */
    public String getShinsaKigenYear() {
        return shinsaKigenYear;
    }

    /**
     * �R������(�N)��ݒ�
     * @param string �R������(�N)
     */
    public void setShinsaKigenYear(String string) {
        shinsaKigenYear = string;
    }

    /**
     * �Ɩ��S���W�����擾
     * @return �Ɩ��S���W��
     */
    public String getTantoKakari() {
        return tantoKakari;
    }

    /**
     * �Ɩ��S���W����ݒ�
     * @param string �Ɩ��S���W��
     */
    public void setTantoKakari(String string) {
        tantoKakari = string;
    }

    /**
     * �Ɩ��S���ۂ��擾
     * @return �Ɩ��S����
     */
    public String getTantokaName() {
        return tantokaName;
    }

    /**
     * �Ɩ��S���ۂ�ݒ�
     * @param string �Ɩ��S����
     */
    public void setTantokaName(String string) {
        tantokaName = string;
    }

    /**
     * �Y�t�t�@�C��(Mac)���擾
     * @return �Y�t�t�@�C��(Mac)
     */
    public String getTenpuMac() {
        return tenpuMac;
    }

    /**
     * �Y�t�t�@�C��(Mac)��ݒ�
     * @param string �Y�t�t�@�C��(Mac)
     */
    public void setTenpuMac(String string) {
        tenpuMac = string;
    }

    /**
     * �Y�t�t�@�C��(Mac)���擾
     * @return �Y�t�t�@�C��(Mac)
     */
    public FormFile getTenpuMacUploadFile() {
        return tenpuMacUploadFile;
    }

    /**
     * �Y�t�t�@�C��(Mac)��ݒ�
     * @param file �Y�t�t�@�C��(Mac)
     */
    public void setTenpuMacUploadFile(FormFile file) {
        tenpuMacUploadFile = file;
    }

    /**
     * �Y�t���������擾
     * @return �Y�t������
     */
    public String getTenpuName() {
        return tenpuName;
    }

    /**
     * �Y�t��������ݒ�
     * @param string �Y�t������
     */
    public void setTenpuName(String string) {
        tenpuName = string;
    }

    /**
     * �Y�t�t�@�C��(Win)���擾
     * @return �Y�t�t�@�C��(Win)
     */
    public String getTenpuWin() {
        return tenpuWin;
    }

    /**
     * �Y�t�t�@�C��(Win)��ݒ�
     * @param string �Y�t�t�@�C��(Win)
     */
    public void setTenpuWin(String string) {
        tenpuWin = string;
    }

    /**
     * �Y�t�t�@�C��(Win)���擾
     * @return �Y�t�t�@�C��(Win)
     */
    public FormFile getTenpuWinUploadFile() {
        return tenpuWinUploadFile;
    }

    /**
     * �Y�t�t�@�C��(Win)��ݒ�
     * @param file �Y�t�t�@�C��(Win)
     */
    public void setTenpuWinUploadFile(FormFile file) {
        tenpuWinUploadFile = file;
    }

    /**
     * �₢���킹��E-mail���擾
     * @return �₢���킹��E-mail
     */
    public String getToiawaseEmail() {
        return toiawaseEmail;
    }

    /**
     * �₢���킹��E-mail��ݒ�
     * @param string �₢���킹��E-mail
     */
    public void setToiawaseEmail(String string) {
        toiawaseEmail = string;
    }

    /**
     * �₢���킹��S���Җ����擾
     * @return �₢���킹��S���Җ�
     */
    public String getToiawaseName() {
        return toiawaseName;
    }

    /**
     * �₢���킹��S���Җ���ݒ�
     * @param string �₢���킹��S���Җ�
     */
    public void setToiawaseName(String string) {
        toiawaseName = string;
    }

    /**
     * �₢���킹��d�b�ԍ����擾
     * @return �₢���킹��d�b�ԍ�
     */
    public String getToiawaseTel() {
        return toiawaseTel;
    }

    /**
     * �₢���킹��d�b�ԍ���ݒ�
     * @param string �₢���킹��d�b�ԍ�
     */
    public void setToiawaseTel(String string) {
        toiawaseTel = string;
    }

    /**
     * �w�U��t���ԁi�I���j(��)���擾
     * @return �w�U��t���ԁi�I���j(��)
     */
    public String getUketukekikanEndDay() {
        return uketukekikanEndDay;
    }

    /**
     * �w�U��t���ԁi�I���j(��)��ݒ�
     * @param string �w�U��t���ԁi�I���j(��)
     */
    public void setUketukekikanEndDay(String string) {
        uketukekikanEndDay = string;
    }

    /**
     * �w�U��t���ԁi�I���j(��)���擾
     * @return �w�U��t���ԁi�I���j(��)
     */
    public String getUketukekikanEndMonth() {
        return uketukekikanEndMonth;
    }

    /**
     * �w�U��t���ԁi�I���j(��)��ݒ�
     * @param string �w�U��t���ԁi�I���j(��)
     */
    public void setUketukekikanEndMonth(String string) {
        uketukekikanEndMonth = string;
    }

    /**
     * �w�U��t���ԁi�I���j(�N)���擾
     * @return �w�U��t���ԁi�I���j(�N)
     */
    public String getUketukekikanEndYear() {
        return uketukekikanEndYear;
    }

    /**
     * �w�U��t���ԁi�I���j(�N)��ݒ�
     * @param string �w�U��t���ԁi�I���j(�N)
     */
    public void setUketukekikanEndYear(String string) {
        uketukekikanEndYear = string;
    }

    /**
     * �w�U��t���ԁi�J�n�j(��)���擾
     * @return �w�U��t���ԁi�J�n�j(��)
     */
    public String getUketukekikanStartDay() {
        return uketukekikanStartDay;
    }

    /**
     * �w�U��t���ԁi�J�n�j(��)��ݒ�
     * @param string �w�U��t���ԁi�J�n�j(��)
     */
    public void setUketukekikanStartDay(String string) {
        uketukekikanStartDay = string;
    }

    /**
     * �w�U��t���ԁi�J�n�j(��)���擾
     * @return �w�U��t���ԁi�J�n�j(��)
     */
    public String getUketukekikanStartMonth() {
        return uketukekikanStartMonth;
    }

    /**
     * �w�U��t���ԁi�J�n�j(��)��ݒ�
     * @param string �w�U��t���ԁi�J�n�j(��)
     */
    public void setUketukekikanStartMonth(String string) {
        uketukekikanStartMonth = string;
    }

    /**
     * �w�U��t���ԁi�J�n�j(�N)���擾
     * @return �w�U��t���ԁi�J�n�j(�N)
     */
    public String getUketukekikanStartYear() {
        return uketukekikanStartYear;
    }

    /**
     * �w�U��t���ԁi�J�n�j(�N)��ݒ�
     * @param string �w�U��t���ԁi�J�n�j(�N)
     */
    public void setUketukekikanStartYear(String string) {
        uketukekikanStartYear = string;
    }

    /**
     * ���Ɩ��I�����X�g��ݒ�
     * @param list ���Ɩ��I�����X�g
     */
    public void setJigyoNameList(List list) {
        jigyoNameList = list;
    }

    /**
     * �]���p�t�@�C���L���i�Ȃ��^����j���X�g���擾
     * @return �]���p�t�@�C���L���i�Ȃ��^����j���X�g
     */
    public List getFlgList() {
        return flgList;
    }

    /**
     * �]���p�t�@�C���L���i�Ȃ��^����j���X�g��ݒ�
     * @param list �]���p�t�@�C���L���i�Ȃ��^����j���X�g
     */
    public void setFlgList(List list) {
        flgList = list;
    }

    /**
     * �_�E�����[�hURL���擾
     * @return �_�E�����[�hURL
     */
    public String getDlUrl() {
        return dlUrl;
    }

    /**
     * �_�E�����[�hURL��ݒ�
     * @param string �_�E�����[�hURL
     */
    public void setDlUrl(String string) {
        dlUrl = string;
    }

    /**
     * URL�A�h���X���擾
     * @return URL�A�h���X
     */
    public String getUrlAddress() {
        return urlAddress;
    }

    /**
     * URL�A�h���X��ݒ�
     * @param string URL�A�h���X
     */
    public void setUrlAddress(String string) {
        urlAddress = string;
    }

    /**
     * URL�^�C�g�����擾
     * @return URL�^�C�g��
     */
    public String getUrlTitle() {
        return urlTitle;
    }

    /**
     * URL�^�C�g����ݒ�
     * @param string URL�^�C�g��
     */
    public void setUrlTitle(String string) {
        urlTitle = string;
    }

    /**
     * �����Җ���o�^�ŏI���ؓ�(�N)���擾
     * @return �����Җ���o�^�ŏI���ؓ�(�N)
     */
    public String getMeiboDateYear() {
        return meiboDateYear;
    }

    /**
     * �����Җ���o�^�ŏI���ؓ�(�N)��ݒ�
     * @param meiboDateYear �����Җ���o�^�ŏI���ؓ�(�N)
     */
    public void setMeiboDateYear(String meiboDateYear) {
        this.meiboDateYear = meiboDateYear;
    }

    /**
     * �����Җ���o�^�ŏI���ؓ�(��)���擾
     * @return �����Җ���o�^�ŏI���ؓ�(��)
     */
    public String getMeiboDateMonth() {
        return meiboDateMonth;
    }

    /**
     * �����Җ���o�^�ŏI���ؓ�(��)��ݒ�
     * @param meiboDateMonth �����Җ���o�^�ŏI���ؓ�(��)
     */
    public void setMeiboDateMonth(String meiboDateMonth) {
        this.meiboDateMonth = meiboDateMonth;
    }

    /**
     * �����Җ���o�^�ŏI���ؓ�(��)���擾
     * @return �����Җ���o�^�ŏI���ؓ�(��)
     */
    public String getMeiboDateDay() {
        return meiboDateDay;
    }

    /**
     * �����Җ���o�^�ŏI���ؓ�(��)��ݒ�
     * @param meiboDateDay �����Җ���o�^�ŏI���ؓ�(��)
     */
    public void setMeiboDateDay(String meiboDateDay) {
        this.meiboDateDay = meiboDateDay;
    }

    // 2006/06/14 �ǉ� ���`�� ��������
    /**
     * ���̈�ԍ����s���ؓ�(��)���擾
     * @return ���̈�ԍ����s���ؓ�(��)
     */
    public String getKariryoikiNoEndDateDay() {
        return kariryoikiNoEndDateDay;
    }

    /**
     * ���̈�ԍ����s���ؓ�(��)��ݒ�
     * @param kariryoikiNoEndDateDay ���̈�ԍ����s���ؓ�(��)
     */
    public void setKariryoikiNoEndDateDay(String kariryoikiNoEndDateDay) {
        this.kariryoikiNoEndDateDay = kariryoikiNoEndDateDay;
    }

    /**
     * ���̈�ԍ����s���ؓ�(��)���擾
     * @return ���̈�ԍ����s���ؓ�(��)
     */
    public String getKariryoikiNoEndDateMonth() {
        return kariryoikiNoEndDateMonth;
    }

    /**
     * ���̈�ԍ����s���ؓ�(��)��ݒ�
     * @param kariryoikiNoEndDateMonth ���̈�ԍ����s���ؓ�(��)
     */
    public void setKariryoikiNoEndDateMonth(String kariryoikiNoEndDateMonth) {
        this.kariryoikiNoEndDateMonth = kariryoikiNoEndDateMonth;
    }

    /**
     * ���̈�ԍ����s���ؓ�(�N)���擾
     * @return ���̈�ԍ����s���ؓ�(�N)
     */
    public String getKariryoikiNoEndDateYear() {
        return kariryoikiNoEndDateYear;
    }

    /**
     * ���̈�ԍ����s���ؓ�(�N)��ݒ�
     * @param kariryoikiNoEndDateYear ���̈�ԍ����s���ؓ�(�N)
     */
    public void setKariryoikiNoEndDateYear(String kariryoikiNoEndDateYear) {
        this.kariryoikiNoEndDateYear = kariryoikiNoEndDateYear;
    }

    // 2006/06/14 �ǉ� ���`�� �����܂�
    // 2006/07/10 �ǉ� ���`�� ��������
    /**
     * �̈��\�Ҋm����ؓ�(�N)���擾
     * @return �̈��\�Ҋm����ؓ�(�N)
     */
    public String getRyoikiEndDateYear() {
        return ryoikiEndDateYear;
    }

    /**
     * �̈��\�Ҋm����ؓ�(�N)��ݒ�
     * @param ryoikiEndDateYear �̈��\�Ҋm����ؓ�(�N)
     */
    public void setRyoikiEndDateYear(String ryoikiEndDateYear) {
        this.ryoikiEndDateYear = ryoikiEndDateYear;
    }

    /**
     * �̈��\�Ҋm����ؓ�(��)���擾
     * @return �̈��\�Ҋm����ؓ�(��)
     */
    public String getRyoikiEndDateMonth() {
        return ryoikiEndDateMonth;
    }

    /**
     * �̈��\�Ҋm����ؓ�(��)��ݒ�
     * @param ryoikiEndDateMonth �̈��\�Ҋm����ؓ�(��)
     */
    public void setRyoikiEndDateMonth(String ryoikiEndDateMonth) {
        this.ryoikiEndDateMonth = ryoikiEndDateMonth;
    }

    /**
     * �̈��\�Ҋm����ؓ�(��)���擾
     * @return �̈��\�Ҋm����ؓ�(��)
     */
    public String getRyoikiEndDateDay() {
        return ryoikiEndDateDay;
    }

    /**
     * �̈��\�Ҋm����ؓ�(��)��ݒ�
     * @param ryoikiEndDateDay �̈��\�Ҋm����ؓ�(��)
     */
    public void setRyoikiEndDateDay(String ryoikiEndDateDay) {
        this.ryoikiEndDateDay = ryoikiEndDateDay;
    }
    // 2006/07/10 �ǉ� ���`�� �����܂�

// 2006/10/23 �ǉ� �Ո� ��������
    /**
     * ���Q�֌W���͒��ؓ�(�N)���擾
     * @return ���Q�֌W���͒��ؓ�(�N)
     */
    public String getRigaiEndDateYear() {
        return rigaiEndDateYear;
    }

    /**
     * ���Q�֌W���͒��ؓ�(�N)��ݒ�
     * @param rigaiEndDateYear ���Q�֌W���͒��ؓ�(�N)
     */
    public void setRigaiEndDateYear(String rigaiEndDateYear) {
        this.rigaiEndDateYear = rigaiEndDateYear;
    }

    /**
     * ���Q�֌W���͒��ؓ�(��)���擾
     * @return ���Q�֌W���͒��ؓ�(��)
     */
    public String getRigaiEndDateMonth() {
        return rigaiEndDateMonth;
    }

    /**
     * ���Q�֌W���͒��ؓ�(��)��ݒ�
     * @param rigaiEndDateMonth ���Q�֌W���͒��ؓ�(��)
     */
    public void setRigaiEndDateMonth(String rigaiEndDateMonth) {
        this.rigaiEndDateMonth = rigaiEndDateMonth;
    }

    /**
     * ���Q�֌W���͒��ؓ�(��)���擾
     * @return ���Q�֌W���͒��ؓ�(��)
     */
    public String getRigaiEndDateDay() {
        return rigaiEndDateDay;
    }

    /**
     * ���Q�֌W���͒��ؓ�(��)��ݒ�
     * @param rigaiEndDateDay ���Q�֌W���͒��ؓ�(��)
     */
    public void setRigaiEndDateDay(String rigaiEndDateDay) {
        this.rigaiEndDateDay = rigaiEndDateDay;
    }
// 2006/10/23 �ǉ� �Ո� �����܂�
    
// 2007/02/07 ���u�j�@�ǉ���������
   
    /**
     * ������e�t�@�C���y�[�W���͈�(����)���擾
     * @return pageFrom ������e�t�@�C���y�[�W���͈�(����)
     */
    public String getPageFrom() {
        return pageFrom;
    }

    /**
     * ������e�t�@�C���y�[�W���͈�(����)��ݒ�
     * @param string pageFrom ������e�t�@�C���y�[�W���͈�(����)
     */
    public void setPageFrom(String pageFrom) {
        this.pageFrom = pageFrom;
    }
    
    
    /**
     * ������e�t�@�C���y�[�W���͈�(���)
     * @return pageTo ������e�t�@�C���y�[�W���͈�(���)
     */
    public String getPageTo() {
        return pageTo;
    }

    /**
     * ������e�t�@�C���y�[�W���͈�(���)��ݒ�
     * @param string pageTo ������e�t�@�C���y�[�W���͈�(���)
     */
    public void setPageTo(String pageTo) {
        this.pageTo = pageTo;
    } 
// 2007/02/07 ���u�j�@�ǉ������܂�
}
