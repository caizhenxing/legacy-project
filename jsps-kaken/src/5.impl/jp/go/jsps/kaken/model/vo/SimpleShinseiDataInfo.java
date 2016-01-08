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
 * �ȈՐ\���f�[�^���N���X�B
 * 
 * ID RCSfile="$RCSfile: SimpleShinseiDataInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class SimpleShinseiDataInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** �\���ԍ� */
	private String uketukeNo;
	
	/** ����ID */
	private String jigyoId;
	
	/** �N�x */
	private String nendo;
	
	/** �� */
	private String kaisu;
	
	/** ���Ɩ� */
	private String jigyoName;
	
	/** �����ۑ薼 */
	private String kadaiName;
	
	/** �w�U��t�����i�I���j */
	private Date uketukeKikanEnd;
    
    //add start ly 2006/07/19
    /** �̈��\�Ҋm����ؓ��i�I���j */
    private Date ryoikiKakuteikikanEnd;
    //add end ly 2006/07/19
	
	/** �\�����쐬���� */
	private Date sakuseiDate;
	
	/** �����@�֏��F�� */
	private Date shoninDate;
	
	/** �󗝌��� */
	private String juriKekka;
	
	/** �󗝌��ʔ��l */
	private String juriBiko;
	
	/** �󗝐����ԍ� */
	private String seiriNo;

	/** 1���R������(ABC) */
	private String kekka1Abc;
	
	/** 1���R������(�_��) */
	private String kekka1Ten;
	
	/** �P���R�����ʁi�_�����j */
	private String kekka1TenSorted;	
	
	/** 2���R������ */
	private String kekka2;
	
	/** �\���󋵁i�R�[�h�j */
	private String jokyoId;
	
	/** �\���󋵁i������j */
	private String jokyoName;
	
	/** �Đ\���t���O */
	private String saishinseiFlg;
	
	/** �\����ID */
	private String shinseishaId;
	
	/** �\���Җ��i���j */
	private String shinseishaNameSei;
	
	/** �\���Җ��i���j*/
	private String shinseishaNameMei;
	
	/** �\���Ҍ����Ҕԍ� */
	private String kenkyuNo;
	
	/** �����@�փR�[�h */
	private String shozokuCd;
	
	/** �����@�֖� */
	private String shozokuName;
	
	/** �����@�֖��i���́j */
	private String shozokuNameRyaku;
	
	/** ���ǖ� */
	private String bukyokuName;
	
	/** ���ǖ��i���́j */
	private String bukyokuNameRyaku;
	
	/** �E�� */
	private String shokushuNameKanji;
	
	/** �E���i���́j */
	private String shokushuNameRyaku;
	
	/** ���E���p�X */
	private String suisenshoPath;

// 20050707
	/** 2���R�����l */
	private String shinsa2Biko;
	/** ���l(�󗝌��ʔ��l�Ɠ񎟐R�����l���p) */
	private String biko;
// Horikoshi

//2006/06/16 �c�@�ǉ���������
    /** �������ڔԍ� */
    private String komokuNo;
    
    /** ������ */
    private String choseihan;
    
    /** �� */
    private String edition;
//2006/06/16�@�c�@�ǉ������܂�    
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public SimpleShinseiDataInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	
	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @return
	 */
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @return
	 */
	public String getJokyoId() {
		return jokyoId;
	}

	/**
	 * @return
	 */
	public String getJokyoName() {
		return jokyoName;
	}

	/**
	 * @return
	 */
	public String getKadaiName() {
		return kadaiName;
	}

	/**
	 * @return
	 */
	public Date getSakuseiDate() {
		return sakuseiDate;
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
	public String getShinseishaNameMei() {
		return shinseishaNameMei;
	}

	/**
	 * @return
	 */
	public String getShinseishaNameSei() {
		return shinseishaNameSei;
	}

	/**
	 * @return
	 */
	public Date getShoninDate() {
		return shoninDate;
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
	public String getShozokuNameRyaku() {
		return shozokuNameRyaku;
	}

	/**
	 * @return
	 */
	public Date getUketukeKikanEnd() {
		return uketukeKikanEnd;
	}

	/**
	 * @return
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param string
	 */
	public void setJokyoId(String string) {
		jokyoId = string;
	}

	/**
	 * @param string
	 */
	public void setJokyoName(String string) {
		jokyoName = string;
	}

	/**
	 * @param string
	 */
	public void setKadaiName(String string) {
		kadaiName = string;
	}

	/**
	 * @param date
	 */
	public void setSakuseiDate(Date date) {
		sakuseiDate = date;
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
	public void setShinseishaNameMei(String string) {
		shinseishaNameMei = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaNameSei(String string) {
		shinseishaNameSei = string;
	}

	/**
	 * @param date
	 */
	public void setShoninDate(Date date) {
		shoninDate = date;
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
	public void setShozokuNameRyaku(String string) {
		shozokuNameRyaku = string;
	}

	/**
	 * @param date
	 */
	public void setUketukeKikanEnd(Date date) {
		uketukeKikanEnd = date;
	}

	/**
	 * @param string
	 */
	public void setUketukeNo(String string) {
		uketukeNo = string;
	}

	/**
	 * @return
	 */
	public String getKekka1Abc() {
		return kekka1Abc;
	}

	/**
	 * @return
	 */
	public String getKekka1Ten() {
		return kekka1Ten;
	}

	/**
	 * @return
	 */
	public String getKekka2() {
		return kekka2;
	}

	/**
	 * @param string
	 */
	public void setKekka1Abc(String string) {
		kekka1Abc = string;
	}

	/**
	 * @param string
	 */
	public void setKekka1Ten(String string) {
		kekka1Ten = string;
	}

	/**
	 * @param string
	 */
	public void setKekka2(String string) {
		kekka2 = string;
	}

	/**
	 * @return
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
	 * @param string
	 */
	public void setBukyokuName(String string) {
		bukyokuName = string;
	}

	/**
	 * @return
	 */
	public String getNendo() {
		return nendo;
	}

	/**
	 * @return
	 */
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
	}

	/**
	 * @param string
	 */
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameKanji(String string) {
		shokushuNameKanji = string;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	/**
	 * @return
	 */
	public String getJuriBiko() {
		return juriBiko;
	}

	/**
	 * @return
	 */
	public String getJuriKekka() {
		return juriKekka;
	}

	/**
	 * @param string
	 */
	public void setJuriBiko(String string) {
		juriBiko = string;
	}

	/**
	 * @param string
	 */
	public void setJuriKekka(String string) {
		juriKekka = string;
	}

	/**
	 * @return
	 */
	public String getSaishinseiFlg() {
		return saishinseiFlg;
	}

	/**
	 * @param string
	 */
	public void setSaishinseiFlg(String string) {
		saishinseiFlg = string;
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
	public String getKenkyuNo() {
		return kenkyuNo;
	}

	/**
	 * @return
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
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
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameRyaku(String string) {
		shokushuNameRyaku = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @return
	 */
	public String getSuisenshoPath() {
		return suisenshoPath;
	}

	/**
	 * @param string
	 */
	public void setSuisenshoPath(String string) {
		suisenshoPath = string;
	}

	/**
	 * @return
	 */
	public String getKekka1TenSorted() {
		return kekka1TenSorted;
	}

	/**
	 * @param string
	 */
	public void setKekka1TenSorted(String string) {
		kekka1TenSorted = string;
	}

// 20050707
	/**
	 * @return
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * @param string
	 */
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @return
	 */
	public String getShinsa2Biko() {
		return shinsa2Biko;
	}

	/**
	 * @param string
	 */
	public void setShinsa2Biko(String string) {
		shinsa2Biko = string;
	}
// Horikoshi
	
	/**
	 * @return �����ԍ���Ԃ�
	 */
	public String getSeiriNo(){
		return seiriNo;
	}
	
	/**
	 * @param str �����ԍ����Z�b�g����
	 */
	public void setSeiriNo(String str){
		seiriNo = str;
	}

    /**
     * �������ڔԍ����擾����
     * 
     * @return �������ڔԍ���Ԃ�
     */
    public String getKomokuNo( )
    {
        return komokuNo;
    }

    /**
     * �������ڔԍ���ݒ肷��
     * 
     * @param�@�������ڔԍ����Z�b�g����
     */
    public void setKomokuNo(String komokuNo)
    {
        this.komokuNo = komokuNo;
    }

    /**
     * �����ǂ��擾����
     * 
     * @return �����ǂ�Ԃ�
     */
    public String getChoseihan( )
    {
        return choseihan;
    }

    /**
     * �����ǂ�ݒ肷��
     * 
     * @param �����ǂ��Z�b�g����
     */
    public void setChoseihan(String choseihan)
    {
        this.choseihan = choseihan;
    }

    /**
     * �ł��擾����
     * 
     * @return �ł�Ԃ�
     */
    public String getEdition( )
    {
        return edition;
    }

    /**
     * �ł�ݒ肷��
     * 
     * @param �ł��Z�b�g����
     */
    public void setEdition(String edition)
    {
        this.edition = edition;
    }

    /**
     * @return Returns the ryoikiKakuteikikanEnd.
     */
    public Date getRyoikiKakuteikikanEnd() {
        return ryoikiKakuteikikanEnd;
    }

    /**
     * @param ryoikiKakuteikikanEnd The ryoikiKakuteikikanEnd to set.
     */
    public void setRyoikiKakuteikikanEnd(Date ryoikiKakuteikikanEnd) {
        this.ryoikiKakuteikikanEnd = ryoikiKakuteikikanEnd;
    }
    
    
}
