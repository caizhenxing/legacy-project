/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : JigyoKanriInfo.java
 *    Description : ���ƊǗ�����ێ�����N���X
 *
 *    Author      : DIS.miaom & DIS.dyh
 *    Date        : 2006/06/19
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Date;

import jp.go.jsps.kaken.util.FileResource;

/**
 * ���ƊǗ�����ێ�����N���X�B
 */
public class JigyoKanriInfo extends JigyoKanriPk{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	//static final long serialVersionUID = -4533191159575948040L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �N�x */
	private String    nendo;
    
    /** �N�x�i����j */
    private String    nendoSeireki;
	
	/** �� */
	private String    kaisu;
	
	/** ���Ɩ� */
	private String    jigyoName;
	
	/** ���Ƌ敪 */
	private String    jigyoKubun;
	
	/** �R���敪 */
	private String    shinsaKubun;
	
	/** �Ɩ��S���� */
	private String    tantokaName;
	
	/** �Ɩ��S���W�� */
	private String    tantoKakari;
	
	/** �₢���킹��S���Җ� */
	private String    toiawaseName;
	
	/** �₢���킹��d�b�ԍ� */
	private String    toiawaseTel;	
	
	/** �₢���킹��E-mail */
	private String    toiawaseEmail;
	
	/** �w�U��t���ԁi�J�n�j */
	private Date      uketukekikanStart;
	
	/** �w�U��t���ԁi�I���j */
	private Date      uketukekikanEnd;

    /** �̈��\�Ҋm����ؓ� */
    private Date      ryoikiKakuteikikanEnd;

	/** �R������ */
	private Date      shinsaKigen;
	
	/** �Y�t������ */
	private String    tenpuName;
	
	/** �Y�t�t�@�C���i�[�t�H���_(Win) */
	private String    tenpuWin;	
	
	/** �Y�t�t�@�C���i�[�t�H���_(Mac) */
	private String    tenpuMac;	
 
//2007/02/03 �c�@�ǉ���������
    /** ������e�t�@�C���y�[�W��(����) */
    private String pageFrom;

    /** ������e�t�@�C���y�[�W��(���) */
    private String pageTo;
//2007/02/03�@�c�@�ǉ������܂�    
	
	/** �]���p�t�@�C���L�� */
	private String    hyokaFileFlg;
	
	/** �]���p�t�@�C���� */
	private String    hyokaName;
	
	/** �]���p�t�@�C���i�[�t�H���_ */
	private String    hyokaFile;
	
	/** ���J�t���O */
	private String    kokaiFlg;
	
	/** ���J���ٔԍ� */
	private String    kessaiNo;
	
	/** ���J�m���ID */
	private String    kokaiID;

	/** �f�[�^�ۊǓ� */
	private Date      hokanDate;
	
	/** �ۊǗL������ */
	private Date      YukoDate;
	
	/** ���l */
	private String    biko;
	
	/** �폜�t���O */
	private String    delFlg;
    
    //2006/06/20 lwj add begin
    /** �R����]����i�n���j�R�[�h */
    private String    kiboubumonCd;
    //2006/06/20 lwj add end
	
	//------�Ɩ��S���Ҍ����@�\�E���ƊǗ�
	/** ���ƃR�[�h�i���ƃ}�X�^�j */
	private String    jigyoCd;

	/** �Y�t�t�@�C���iWin�j */
	private FileResource    tenpuWinFileRes;
	
	/** �Y�t�t�@�C���iMac�j */
	private FileResource    tenpuMacFileRes;

	/** �]���p�t�@�C�� */
	private FileResource    hyokaFileRes;
	
	//------�R���S���Ҍ����@�\
	/** �w�U�₢���킹��X�֔ԍ� */
	private String    toiawaseZip;	

	/** �w�U�₢���킹��Z�� */
	private String    toiawaseJusho;	
	
	//	2005/04/19 �ǉ� ��������----------------------------------------------
	//���R URL�̒ǉ��̂���
	
	/** URL�^�C�g�� */
	private String urlTitle;
	
	/** URL�A�h���X */
	private String urlAddress;

	/**�@�_�E�����[�hURL */
	private String dlUrl;

	// 2005/04/24 �ǉ� ��������----------------------------------------------
	// ���R �X�V���̃t�@�C���폜�����ǉ��̂���
	/** Win�t�@�C���̍폜�t���O */
	private boolean delWinFileFlg = false;
	
	/** Mac�t�@�C���̍폜�t���O */
	private boolean delMacFileFlg = false;
	
	/** �]���t�@�C���̍폜�t���O */
	private boolean delHyokaFileFlg = false;
	// �ǉ� ��������----------------------------------------------
	
	// 2006/02/08 �ǉ�  �c�@��������----------------------------------------------
	// ���R �����Җ�����ؓ��ǉ��̂���
	private Date meiboDate;
	// �����܂�
	
	// 2006/06/13 �ǉ�  ���`�؁@��������
	// ���R ���̈�ԍ����s���ؓ��ǉ��̂���
    /** ���̈�ԍ����s���ؓ� */
	private Date kariryoikiNoEndDate;
    
    // ���R �̈��\�Ҋm����ؓ��ǉ��̂���
    /** �̈��\�Ҋm����ؓ� */
    private Date ryoikiEndDate;
    // 2006/06/13 �ǉ�  ���`�؁@�����܂�
	
    // 2006/06/23 �ǉ� �Ո� ��������
    // ���R ���Q�֌W���͒��ؓ��ǉ��̂���
    private Date rigaiEndDate;
    // 2006/06/23 �ǉ� �Ո� �����܂�
	  //---------------------------------------------------------------------
	  // Constructors
	  //---------------------------------------------------------------------	
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

    /**
	 * �R���X�g���N�^�B
	 */
	public JigyoKanriInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

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
     * �N�x�i����j���擾
     * @return �N�x�i����j
     */
    public String getNendoSeireki() {
        return nendoSeireki;
    }

    /**
     * �N�x�i����j��ݒ�
     * @param nendoSeireki �N�x�i����j
     */
    public void setNendoSeireki(String nendoSeireki) {
        this.nendoSeireki = nendoSeireki;
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
     * ���Ɩ����擾
     * @return ���Ɩ�
     */
    public String getJigyoName() {
        return jigyoName;
    }

    /**
     * ���Ɩ���ݒ�
     * @param string ���Ɩ�
     */
    public void setJigyoName(String string) {
        jigyoName = string;
    }

    /**
     * ���Ƌ敪���擾
     * @return ���Ƌ敪
     */
    public String getJigyoKubun() {
        return jigyoKubun;
    }

    /**
     * ���Ƌ敪��ݒ�
     * @param string ���Ƌ敪
     */
    public void setJigyoKubun(String string) {
        jigyoKubun = string;
    }

    /**
     * �R���敪���擾
     * @return �R���敪
     */
    public String getShinsaKubun() {
        return shinsaKubun;
    }

    /**
     * �R���敪��ݒ�
     * @param string �R���敪
     */
    public void setShinsaKubun(String string) {
        shinsaKubun = string;
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
     * �w�U��t���ԁi�J�n�j���擾
     * @return �w�U��t���ԁi�J�n�j
     */
    public Date getUketukekikanStart() {
        return uketukekikanStart;
    }

    /**
     * �w�U��t���ԁi�J�n�j��ݒ�
     * @param date �w�U��t���ԁi�J�n�j
     */
    public void setUketukekikanStart(Date date) {
        uketukekikanStart = date;
    }

    /**
     * �w�U��t���ԁi�I���j���擾
     * @return �w�U��t���ԁi�I���j
     */
    public Date getUketukekikanEnd() {
        return uketukekikanEnd;
    }

    /**
     * �w�U��t���ԁi�I���j��ݒ�
     * @param date �w�U��t���ԁi�I���j
     */
    public void setUketukekikanEnd(Date date) {
        uketukekikanEnd = date;
    }

    /**
     * �̈��\�Ҋm����ؓ����擾
     * @return �̈��\�Ҋm����ؓ�
     */
    public Date getRyoikiKakuteikikanEnd() {
        return ryoikiKakuteikikanEnd;
    }

    /**
     * �̈��\�Ҋm����ؓ���ݒ�
     * @param ryoikiKakuteikikanEnd �̈��\�Ҋm����ؓ�
     */
    public void setRyoikiKakuteikikanEnd(Date ryoikiKakuteikikanEnd) {
        this.ryoikiKakuteikikanEnd = ryoikiKakuteikikanEnd;
    }

    /**
     * �R���������擾
     * @return �R������
     */
    public Date getShinsaKigen() {
        return shinsaKigen;
    }

    /**
     * �R��������ݒ�
     * @param date �R������
     */
    public void setShinsaKigen(Date date) {
        shinsaKigen = date;
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
     * �Y�t�t�@�C���i�[�t�H���_(Win)���擾
     * @return �Y�t�t�@�C���i�[�t�H���_(Win)
     */
    public String getTenpuWin() {
        return tenpuWin;
    }

    /**
     * �Y�t�t�@�C���i�[�t�H���_(Win)��ݒ�
     * @param string �Y�t�t�@�C���i�[�t�H���_(Win)
     */
    public void setTenpuWin(String string) {
        tenpuWin = string;
    }

    /**
     * �Y�t�t�@�C���i�[�t�H���_(Mac)���擾
     * @return �Y�t�t�@�C���i�[�t�H���_(Mac)
     */
    public String getTenpuMac() {
        return tenpuMac;
    }

    /**
     * �Y�t�t�@�C���i�[�t�H���_(Mac)��ݒ�
     * @param string �Y�t�t�@�C���i�[�t�H���_(Mac)
     */
    public void setTenpuMac(String string) {
        tenpuMac = string;
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
     * �]���p�t�@�C�������擾
     * @return �]���p�t�@�C����
     */
    public String getHyokaName() {
        return hyokaName;
    }

    /**
     * �]���p�t�@�C������ݒ�
     * @param string �]���p�t�@�C����
     */
    public void setHyokaName(String string) {
        hyokaName = string;
    }

    /**
     * �]���p�t�@�C���i�[�t�H���_���擾
     * @return �]���p�t�@�C���i�[�t�H���_
     */
    public String getHyokaFile() {
        return hyokaFile;
    }

    /**
     * �]���p�t�@�C���i�[�t�H���_��ݒ�
     * @param string �]���p�t�@�C���i�[�t�H���_
     */
    public void setHyokaFile(String string) {
        hyokaFile = string;
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
     * �f�[�^�ۊǓ����擾
     * @return �f�[�^�ۊǓ�
     */
    public Date getHokanDate() {
        return hokanDate;
    }

    /**
     * �f�[�^�ۊǓ���ݒ�
     * @param date �f�[�^�ۊǓ�
     */
    public void setHokanDate(Date date) {
        hokanDate = date;
    }

    /**
     * �ۊǗL���������擾
     * @return �ۊǗL������
     */
    public Date getYukoDate() {
        return YukoDate;
    }

    /**
     * �ۊǗL��������ݒ�
     * @param date �ۊǗL������
     */
    public void setYukoDate(Date date) {
        YukoDate = date;
    }

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
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * �폜�t���O��ݒ�
     * @param string �폜�t���O
     */
    public void setDelFlg(String string) {
        delFlg = string;
    }

    /**
     * �R����]����i�n���j�R�[�h���擾
     * @return �R����]����i�n���j�R�[�h
     */
    public String getKiboubumonCd() {
        return kiboubumonCd;
    }

    /**
     * �R����]����i�n���j�R�[�h��ݒ�
     * @param kiboubumonCd �R����]����i�n���j�R�[�h
     */
    public void setKiboubumonCd(String kiboubumonCd) {
        this.kiboubumonCd = kiboubumonCd;
    }

    /**
     * ���ƃR�[�h�i���ƃ}�X�^�j���擾
     * @return ���ƃR�[�h�i���ƃ}�X�^�j
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * ���ƃR�[�h�i���ƃ}�X�^�j��ݒ�
     * @param string ���ƃR�[�h�i���ƃ}�X�^�j
     */
    public void setJigyoCd(String string) {
        jigyoCd = string;
    }

    /**
     * �Y�t�t�@�C���iWin�j���擾
     * @return �Y�t�t�@�C���iWin�j
     */
    public FileResource getTenpuWinFileRes() {
        return tenpuWinFileRes;
    }

    /**
     * �Y�t�t�@�C���iWin�j��ݒ�
     * @param resource �Y�t�t�@�C���iWin�j
     */
    public void setTenpuWinFileRes(FileResource resource) {
        tenpuWinFileRes = resource;
    }

    /**
     * �Y�t�t�@�C���iMac�j���擾
     * @return �Y�t�t�@�C���iMac�j
     */
    public FileResource getTenpuMacFileRes() {
        return tenpuMacFileRes;
    }

    /**
     * �Y�t�t�@�C���iMac�j��ݒ�
     * @param resource �Y�t�t�@�C���iMac�j
     */
    public void setTenpuMacFileRes(FileResource resource) {
        tenpuMacFileRes = resource;
    }

    /**
     * �]���p�t�@�C�����擾
     * @return �]���p�t�@�C��
     */
	public FileResource getHyokaFileRes() {
		return hyokaFileRes;
	}

    /**
     * �]���p�t�@�C����ݒ�
     * @param resource �]���p�t�@�C��
     */
    public void setHyokaFileRes(FileResource resource) {
        hyokaFileRes = resource;
    }

    /**
     * �w�U�₢���킹��X�֔ԍ����擾
     * @return �w�U�₢���킹��X�֔ԍ�
     */
    public String getToiawaseZip() {
        return toiawaseZip;
    }

    /**
     * �w�U�₢���킹��X�֔ԍ���ݒ�
     * @param string �w�U�₢���킹��X�֔ԍ�
     */
    public void setToiawaseZip(String string) {
        toiawaseZip = string;
    }

    /**
     * �w�U�₢���킹��Z�����擾
     * @return �w�U�₢���킹��Z��
     */
	public String getToiawaseJusho() {
		return toiawaseJusho;
	}

    /**
     * �w�U�₢���킹��Z����ݒ�
     * @param string �w�U�₢���킹��Z��
     */
	public void setToiawaseJusho(String string) {
		toiawaseJusho = string;
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
     * Win�t�@�C���̍폜�t���O���擾
     * @return Win�t�@�C���̍폜�t���O
     */
    public boolean isDelWinFileFlg() {
        return delWinFileFlg;
    }

    /**
     * Win�t�@�C���̍폜�t���O��ݒ�
     * @param delWinFileFlg Win�t�@�C���̍폜�t���O
     */
    public void setDelWinFileFlg(boolean delWinFileFlg) {
        this.delWinFileFlg = delWinFileFlg;
    }

    /**
     * Mac�t�@�C���̍폜�t���O���擾
     * @return Mac�t�@�C���̍폜�t���O
     */
    public boolean isDelMacFileFlg() {
        return delMacFileFlg;
    }

    /**
     * Mac�t�@�C���̍폜�t���O��ݒ�
     * @param delMacFileFlg Mac�t�@�C���̍폜�t���O
     */
    public void setDelMacFileFlg(boolean delMacFileFlg) {
        this.delMacFileFlg = delMacFileFlg;
    }

    /**
     * �]���t�@�C���̍폜�t���O���擾
     * @return �]���t�@�C���̍폜�t���O
     */
	public boolean isDelHyokaFileFlg() {
		return delHyokaFileFlg;
	}

    /**
     * �]���t�@�C���̍폜�t���O��ݒ�
     * @param delHyokaFileFlg �]���t�@�C���̍폜�t���O
     */
    public void setDelHyokaFileFlg(boolean delHyokaFileFlg) {
        this.delHyokaFileFlg = delHyokaFileFlg;
    }

    /**
     * �����Җ�����ؓ����擾
     * @return �����Җ�����ؓ�
     */
    public Date getMeiboDate() {
        return meiboDate;
    }

    /**
     * �����Җ�����ؓ���ݒ�
     * @param meiboDate �����Җ�����ؓ�
     */
    public void setMeiboDate(Date meiboDate) {
        this.meiboDate = meiboDate;
    }

	// 2006/06/14 �ǉ� ���`�� ��������
    /**
     * ���̈�ԍ����s���ؓ����擾
     * @return ���̈�ԍ����s���ؓ�
     */
    public Date getKariryoikiNoEndDate() {
        return kariryoikiNoEndDate;
    }

    /**
     * ���̈�ԍ����s���ؓ���ݒ�
     * @param kariryoikiNoEndDate ���̈�ԍ����s���ؓ�
     */
    public void setKariryoikiNoEndDate(Date kariryoikiNoEndDate) {
        this.kariryoikiNoEndDate = kariryoikiNoEndDate;
    }

    /**
     * �̈��\�Ҋm����ؓ����擾
     * @return �̈��\�Ҋm����ؓ�
     */
    public Date getRyoikiEndDate() {
        return ryoikiEndDate;
    }

    /**
     * �̈��\�Ҋm����ؓ���ݒ�
     * @param ryoikiEndDate �̈��\�Ҋm����ؓ�
     */
    public void setRyoikiEndDate(Date ryoikiEndDate) {
        this.ryoikiEndDate = ryoikiEndDate;
    }
    // 2006/07/10 �ǉ� ���`�� �����܂�

    // 2006/06/23 �ǉ� �Ո� ��������
    /**
     * ���Q�֌W���͒��ؓ����擾
     * @return ���Q�֌W���͒��ؓ�
     */
	public Date getRigaiEndDate() {
		return rigaiEndDate;
	}

    /**
     * ���Q�֌W���͒��ؓ���ݒ�
     * @param rigaiEndDate ���Q�֌W���͒��ؓ�
     */
	public void setRigaiEndDate(Date rigaiEndDate) {
		this.rigaiEndDate = rigaiEndDate;
	}
    // 2006/06/23 �ǉ� �Ո� �����܂�
    

    /**
     * ������e�t�@�C���y�[�W��(����)�ݒ�
     * @param  pageFrom �ݒ�l
     */
    public void setPageFrom(String pageFrom) {
        this.pageFrom = pageFrom;
    }
    
    /**
     * ������e�t�@�C���y�[�W��(����)�擾
     * @return ������e�t�@�C���y�[�W��(����)�̒l
     */
    public String getPageFrom() {
        return pageFrom;
    }
    
    /**
     * ������e�t�@�C���y�[�W��(���)�ݒ�
     * @param  pageTo �ݒ�l
     */
    public void setPageTo(String pageTo) {
        this.pageTo = pageTo;
    }
    
    /**
     * ������e�t�@�C���y�[�W��(���)�擾
     * @return ������e�t�@�C���y�[�W��(���)�̒l
     */
    public String getPageTo() {
        return pageTo;
    }
}