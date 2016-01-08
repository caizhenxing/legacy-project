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
 * �\���ҏ���ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinseishaInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class ShinseishaInfo extends ShinseishaPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �\����ID */
	private String shinseishaId;

	/** �����@�փR�[�h */
	private String shozokuCd;
	
	/** �����@�֖��i�a���j */
	private String shozokuName;

	/** �����@�֖��i�p���j */
	private String shozokuNameEigo;

	/** �����@�֖��i���́j */
	private String shozokuNameRyaku;

	/** �p�X���[�h */
	private String password;
	
	/** �\���Ҏ����i����-���j */
	private String nameKanjiSei;

	/** �\���Ҏ����i����-���j */
	private String nameKanjiMei;

	/** �\���Ҏ����i�t���K�i-���j */
	private String nameKanaSei;

	/** �\���Ҏ����i�t���K�i-���j */
	private String nameKanaMei;
	
	/** �\���Ҏ����i���[�}��-���j */
	private String nameRoSei;

	/** �\���Ҏ����i���[�}��-���j */
	private String nameRoMei;

	/** ���ǃR�[�h */
	private String bukyokuCd;
	
	/** ���ǖ� */
	private String bukyokuName;

	/** ���ǖ��i���́j */
	private String bukyokuNameRyaku;
	
	/** ���ǎ�� */
	private String bukyokuShubetuCd;
	
	/** ���ǎ�ʖ� */
	private String bukyokuShubetuName;

	/** �E���R�[�h */
	private String shokushuCd;

	/** �E���i�a���j */
	private String shokushuNameKanji;

	/** �E���i���́j */
	private String shokushuNameRyaku;

	/** �Ȍ������Ҕԍ� */
	private String kenkyuNo;

	/** ����剞��t���O */
	private String hikoboFlg;
	
	/** ���l */
	private String biko;
	
	/** �폜�t���O*/
	private String delFlg;
	
	/** �L������ */
	private Date yukoDate; 
	
//	2005/03/28 �ǉ� ��������---------------------------------
//	���R ���N������\�����邽��
	/** ���N���� */
	private Date birthday;
//  �ǉ� �����܂�--------------------------------------------
	
//	2005/03/31 �ǉ� ��������---------------------------------
//	���R �u���s��ID�v�u���s���v���ڒǉ�
	/** ���s��ID */
	private String hakkoshaId;
	
	/** ���s�� */
	private Date hakkoDate;
//	2006/02/09  �ǉ� 
	private String ouboshikaku;
//  �ǉ� �����܂�--------------------------------------------
    
    //�{�@2006/06/16 ��������
    private String nenrei;
    //�{�@�����܂�
    
//  2006/06/20 �c�@�ǉ���������
    /** �̈�v�揑�i�T�v�j�t���O */
    private boolean ryoikiGaiyoFlg;
//2006/06/20�@�c�@�ǉ������܂�       
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

    /**
	 * �R���X�g���N�^�B
	 */
	public ShinseishaInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------


	/**
	 * @return
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * @return
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}

	/**
	 * @return
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
	 * @return
	 */
	public String getBukyokuShubetuCd() {
		return bukyokuShubetuCd;
	}

	/**
	 * @return
	 */
	public String getBukyokuShubetuName() {
		return bukyokuShubetuName;
	}

	/**
	 * @return
	 */
	public String getDelFlg() {
		return delFlg;
	}

	/**
	 * @return
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}

	/**
	 * @return
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

	/**
	 * @return
	 */
	public String getNameRoMei() {
		return nameRoMei;
	}

	/**
	 * @return
	 */
	public String getNameRoSei() {
		return nameRoSei;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public String getShinseishaId() {
		return shinseishaId;
	}

	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @return
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}

	/**
	 * @return
	 */
	public Date getYukoDate() {
		return yukoDate;
	}

	/**
	 * @param string
	 */
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuCd(String string) {
		bukyokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuName(String string) {
		bukyokuName = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuShubetuCd(String string) {
		bukyokuShubetuCd = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuShubetuName(String string) {
		bukyokuShubetuName = string;
	}

	/**
	 * @param string
	 */
	public void setDelFlg(String string) {
		delFlg = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiMei(String string) {
		nameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiSei(String string) {
		nameKanjiSei = string;
	}

	/**
	 * @param string
	 */
	public void setNameRoMei(String string) {
		nameRoMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameRoSei(String string) {
		nameRoSei = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaId(String string) {
		shinseishaId = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameEigo(String string) {
		shozokuNameEigo = string;
	}

	/**
	 * @param date
	 */
	public void setYukoDate(Date date) {
		yukoDate = date;
	}

	/**
	 * @return
	 */
	public String getShozokuNameRyaku() {
		return shozokuNameRyaku;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameRyaku(String string) {
		shozokuNameRyaku = string;
	}

	/**
	 * @return
	 */
	public String getBukyokuNameRyaku() {
		return bukyokuNameRyaku;
	}

	/**
	 * @return
	 */
	public String getHikoboFlg() {
		return hikoboFlg;
	}

	/**
	 * @return
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}

	/**
	 * @return
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}

	/**
	 * @return
	 */
	public String getShokushuCd() {
		return shokushuCd;
	}

	/**
	 * @return
	 */
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
	}

	/**
	 * @return
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
	}

	/**
	 * @param string
	 */
	public void setBukyokuNameRyaku(String string) {
		bukyokuNameRyaku = string;
	}

	/**
	 * @param string
	 */
	public void setHikoboFlg(String string) {
		hikoboFlg = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanaMei(String string) {
		nameKanaMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanaSei(String string) {
		nameKanaSei = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuCd(String string) {
		shokushuCd = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameKanji(String string) {
		shokushuNameKanji = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameRyaku(String string) {
		shokushuNameRyaku = string;
	}

//	2005/03/28 �ǉ� ��������---------------------------------
//	���R ���N������\�����邽��
	/**
	 * @return
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param date
	 */
	public void setBirthday(Date date) {
		birthday = date;
	}
//	�ǉ� �����܂�--------------------------------------------
	
//	2005/03/31 �ǉ� ��������---------------------------------
//	���R �u���s��ID�v�u���s���v�ǉ�
	/**
	 * @return hakkoDate ��߂��܂��B
	 */
	public Date getHakkoDate() {
		return hakkoDate;
	}
	
	/**
	 * @param hakkoDate hakkoDate ��ݒ�B
	 */
	public void setHakkoDate(Date hakkoDate) {
		this.hakkoDate = hakkoDate;
	}
	/**
	 * @return hakkoshaID ��߂��܂��B
	 */
	public String getHakkoshaId() {
		return hakkoshaId;
	}
	/**
	 * @param hakkoshaID hakkoshaID ��ݒ�B
	 */
	public void setHakkoshaId(String hakkoshaID) {
		this.hakkoshaId = hakkoshaID;
	}
//�ǉ� �����܂�--------------------------------------------

	public String getOuboshikaku() {
		return ouboshikaku;
	}

	public void setOuboshikaku(String ouboshikaku) {
		this.ouboshikaku = ouboshikaku;
	}
    
    /**
     * @return nenrei ��߂��܂��B
     */
    public String getNenrei( )
    {
        return nenrei;
    }

    /**
     * @param nenrei �ݒ肷�� nenrei�B
     */
    public void setNenrei(String nenrei)
    {
        this.nenrei = nenrei;
    }

    /**
     * @return ryoikiGaiyoFlg��߂��܂�
     */
    public boolean isRyoikiGaiyoFlg() {
        return ryoikiGaiyoFlg;
    }

    /**
     * @param ryoikiGaiyoFlg��ݒ肷��
     */
    public void setRyoikiGaiyoFlg(boolean ryoikiGaiyoFlg) {
        this.ryoikiGaiyoFlg = ryoikiGaiyoFlg;
    }

}
