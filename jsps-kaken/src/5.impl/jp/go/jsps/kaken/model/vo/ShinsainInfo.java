/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Date;

/**
 * �R��������ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinsainInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class ShinsainInfo extends ShinsainPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �R����ID */
	private String shinsainId;

//	/** �R�����ԍ� */
//	private String shinsainNo;

	/** �R���������i����-���j */
	private String nameKanjiSei;

	/** �R���������i����-���j */
	private String nameKanjiMei;

	/** �R���������i�J�i-���j */
	private String nameKanaSei;

	/** �R���������i�J�i-���j */
	private String nameKanaMei;

	/** �����@�֖��i�R�[�h�j */
	private String shozokuCd;

	/** �����@�֖��i�a���j */
	private String shozokuName;

//	/** ���ǖ��i�R�[�h�j*/
//	private String bukyokuCd;

	/** ���ǖ��i�a���j */
	private String bukyokuName;

//	/** �E��R�[�h */
//	private String shokushuCd;

	/** �E�햼�� */
	private String shokushuName;

//	/** �n�� */
//	private String keiCd;
//
//	/** �R���s�� */
//	private String shinsaKahi;

	/** ���t��i�X�֔ԍ��j */
	private String sofuZip;

	/** ���t��i�Z���j */
	private String sofuZipaddress;

	/** ���t��iEmail�j */
	private String sofuZipemail;

//	/** ���t��iEmail2�j */
//	private String sofuZipemail2;

	/** �d�b�ԍ� */
	private String shozokuTel;

//	/** ����d�b�ԍ� */
//	private String jitakuTel;

//	/** �V�K�E�p�� */
//	private String sinkiKeizokuFlg;
//
//	/** �V�K�E�p��(�\���p) */
//	private String sinkiKeizokuHyoji;
//
//	/** �Ϗ��J�n�� */
//	private Date kizokuStart;
//
//	/** �Ϗ��I���� */
//	private Date kizokuEnd;
//
//	/** �Ӌ� */
//	private String shakin;
//
//	/** �Ӌ�(�\���p) */
//	private String shakinHyoji;

	/** URL */
	private String url;

//	/** ���ȍזڃR�[�h�iA�j */
//	private String levelA1;
//
//	/** ���ȍזڃR�[�h�iB1-1�j */
//	private String levelB11;
//
//	/** ���ȍזڃR�[�h�iB1-2�j */
//	private String levelB12;
//
//	/** ���ȍזڃR�[�h�iB1-3�j */
//	private String levelB13;
//
//	/** ���ȍזڃR�[�h�iB2-1�j */
//	private String levelB21;
//
//	/** ���ȍזڃR�[�h�iB2-2�j */
//	private String levelB22;
//
//	/** ���ȍזڃR�[�h�iB2-3�j */
//	private String levelB23;
//
//	/** ��啪��̃L�[���[�h(1) */
//	private String key1;
//
//	/** ��啪��̃L�[���[�h(2) */
//	private String key2;
//
//	/** ��啪��̃L�[���[�h(3) */
//	private String key3;
//
//	/** ��啪��̃L�[���[�h(4) */
//	private String key4;
//
//	/** ��啪��̃L�[���[�h(5) */
//	private String key5;
//
//	/** ��啪��̃L�[���[�h(6) */
//	private String key6;
//
//	/** ��啪��̃L�[���[�h(7) */
//	private String key7;

	/** �p�X���[�h */
	private String password;

	/** ���l */
	private String biko;

	/** �L������ */
	private Date yukoDate;

	/** �폜�t���O */
	private String delFlg;

//	/** �S�����Ƌ敪 */
//	private String jigyoKubun;

	/** FAX�ԍ� */
	private String shozokuFax;

	/** ��� */
	private String senmon;

	/** �X�V��(�N) */
	private Date koshinDate;
	
//�ŏI���O�C������ǉ�
	/** �ŏI���O�C���� */
	private Date loginDate; 

//���[���t���O��ǉ�
	/** ���[���t���O */
	private String mailFlg;
	
//	2006/10/24 �Ո� �ǉ���������
    /** �����v�撲���_�E�����[�h�t���O*/
	private String downloadFlag;
//	2006/10/24 �Ո� �ǉ������܂�

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsainInfo() {
		super();
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
     * ���ǖ��i�a���j���擾
     * @return ���ǖ��i�a���j
     */
    public String getBukyokuName() {
        return bukyokuName;
    }

    /**
     * ���ǖ��i�a���j��ݒ�
     * @param string ���ǖ��i�a���j
     */
    public void setBukyokuName(String string) {
        bukyokuName = string;
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
     * �X�V��(�N)���擾
     * @return �X�V��(�N)
     */
    public Date getKoshinDate() {
        return koshinDate;
    }

    /**
     * �X�V��(�N)��ݒ�
     * @param date �X�V��(�N)
     */
    public void setKoshinDate(Date date) {
        koshinDate = date;
    }

    /**
     * �R���������i�J�i-���j���擾
     * @return �R���������i�J�i-���j
     */
    public String getNameKanaMei() {
        return nameKanaMei;
    }

    /**
     * �R���������i�J�i-���j��ݒ�
     * @param string �R���������i�J�i-���j
     */
    public void setNameKanaMei(String string) {
        nameKanaMei = string;
    }

    /**
     * �R���������i�J�i-���j���擾
     * @return �R���������i�J�i-���j
     */
    public String getNameKanaSei() {
        return nameKanaSei;
    }

    /**
     * �R���������i�J�i-���j��ݒ�
     * @param string �R���������i�J�i-���j
     */
    public void setNameKanaSei(String string) {
        nameKanaSei = string;
    }

    /**
     * �R���������i����-���j���擾
     * @return �R���������i����-���j
     */
    public String getNameKanjiMei() {
        return nameKanjiMei;
    }

    /**
     * �R���������i����-���j��ݒ�
     * @param string �R���������i����-���j
     */
    public void setNameKanjiMei(String string) {
        nameKanjiMei = string;
    }

    /**
     * �R���������i����-���j���擾
     * @return �R���������i����-���j
     */
    public String getNameKanjiSei() {
        return nameKanjiSei;
    }

    /**
     * �R���������i����-���j��ݒ�
     * @param string �R���������i����-���j
     */
    public void setNameKanjiSei(String string) {
        nameKanjiSei = string;
    }

    /**
     * �p�X���[�h���擾
     * @return �p�X���[�h
     */
    public String getPassword() {
        return password;
    }

    /**
     * �p�X���[�h��ݒ�
     * @param string �p�X���[�h
     */
    public void setPassword(String string) {
        password = string;
    }

    /**
     * �����擾
     * @return ���
     */
    public String getSenmon() {
        return senmon;
    }

    /**
     * ����ݒ�
     * @param string ���
     */
    public void setSenmon(String string) {
        senmon = string;
    }

    /**
     * �R����ID���擾
     * @return �R����ID
     */
    public String getShinsainId() {
        return shinsainId;
    }

    /**
     * �R����ID��ݒ�
     * @param string �R����ID
     */
    public void setShinsainId(String string) {
        shinsainId = string;
    }

//    /**
//     * �R�����ԍ����擾
//     * @return �R�����ԍ�
//     */
//    public String getShinsainNo() {
//        return shinsainNo;
//    }
//
//    /**
//     * �R�����ԍ���ݒ�
//     * @param string �R�����ԍ�
//     */
//    public void setShinsainNo(String string) {
//        shinsainNo = string;
//    }

    /**
     * �E�햼�̂��擾
     * @return �E�햼��
     */
    public String getShokushuName() {
        return shokushuName;
    }

    /**
     * �E�햼�̂�ݒ�
     * @param string �E�햼��
     */
    public void setShokushuName(String string) {
        shokushuName = string;
    }

    /**
     * �����@�֖��i�R�[�h�j���擾
     * @return �����@�֖��i�R�[�h�j
     */
    public String getShozokuCd() {
        return shozokuCd;
    }

    /**
     * �����@�֖��i�R�[�h�j��ݒ�
     * @param string �����@�֖��i�R�[�h�j
     */
    public void setShozokuCd(String string) {
        shozokuCd = string;
    }

    /**
     * FAX�ԍ����擾
     * @return FAX�ԍ�
     */
    public String getShozokuFax() {
        return shozokuFax;
    }

    /**
     * FAX�ԍ���ݒ�
     * @param string FAX�ԍ�
     */
    public void setShozokuFax(String string) {
        shozokuFax = string;
    }

    /**
     * �����@�֖��i�a���j���擾
     * @return �����@�֖��i�a���j
     */
    public String getShozokuName() {
        return shozokuName;
    }

    /**
     * �����@�֖��i�a���j��ݒ�
     * @param string �����@�֖��i�a���j
     */
    public void setShozokuName(String string) {
        shozokuName = string;
    }

    /**
     * �d�b�ԍ����擾
     * @return �d�b�ԍ�
     */
    public String getShozokuTel() {
        return shozokuTel;
    }

    /**
     * �d�b�ԍ���ݒ�
     * @param string �d�b�ԍ�
     */
    public void setShozokuTel(String string) {
        shozokuTel = string;
    }

    /**
     * ���t��i�X�֔ԍ��j���擾
     * @return ���t��i�X�֔ԍ��j
     */
    public String getSofuZip() {
        return sofuZip;
    }

    /**
     * ���t��i�X�֔ԍ��j��ݒ�
     * @param string ���t��i�X�֔ԍ��j
     */
    public void setSofuZip(String string) {
        sofuZip = string;
    }

    /**
     * ���t��i�Z���j���擾
     * @return ���t��i�Z���j
     */
    public String getSofuZipaddress() {
        return sofuZipaddress;
    }

    /**
     * ���t��i�Z���j��ݒ�
     * @param string ���t��i�Z���j
     */
    public void setSofuZipaddress(String string) {
        sofuZipaddress = string;
    }

    /**
     * ���t��iEmail�j���擾
     * @return ���t��iEmail�j
     */
    public String getSofuZipemail() {
        return sofuZipemail;
    }

    /**
     * ���t��iEmail�j��ݒ�
     * @param string ���t��iEmail�j
     */
    public void setSofuZipemail(String string) {
        sofuZipemail = string;
    }

    /**
     * URL���擾
     * @return URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * URL��ݒ�
     * @param string URL
     */
    public void setUrl(String string) {
        url = string;
    }

//    /**
//     * �S�����Ƌ敪���擾
//     * @return �S�����Ƌ敪
//     */
//    public String getJigyoKubun() {
//        return jigyoKubun;
//    }
//
//    /**
//     * �S�����Ƌ敪��ݒ�
//     * @param string �S�����Ƌ敪
//     */
//    public void setJigyoKubun(String string) {
//        jigyoKubun = string;
//    }

    /**
     * �L���������擾
     * @return �L������
     */
    public Date getYukoDate() {
        return yukoDate;
    }

    /**
     * �L��������ݒ�
     * @param date �L������
     */
    public void setYukoDate(Date date) {
        yukoDate = date;
    }

    /**
     * �ŏI���O�C�������擾
     * @return �ŏI���O�C����
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * �ŏI���O�C������ݒ�
     * @param string �ŏI���O�C����
     */
    public void setLoginDate(Date date) {
        loginDate = date;
    }

    /**
     * ���[���t���O���擾
     * @return ���[���t���O
     */
    public String getMailFlg() {
        return mailFlg;
    }

    /**
     * ���[���t���O��ݒ�
     * @param string ���[���t���O
     */
    public void setMailFlg(String string) {
        mailFlg = string;
    }

// 2006/10/24 �Ո� �ǉ���������
    /**
     * �����v�撲���_�E�����[�h�t���O���擾
     * @return �����v�撲���_�E�����[�h�t���O
     */
    public String getDownloadFlag() {
        return downloadFlag;
    }

    /**
     * �����v�撲���_�E�����[�h�t���O��ݒ�
     * @param downloadFlag �����v�撲���_�E�����[�h�t���O
     */
    public void setDownloadFlag(String downloadFlag) {
        this.downloadFlag = downloadFlag;
    }
//	2006/10/24 �Ո� �ǉ������܂�
}
