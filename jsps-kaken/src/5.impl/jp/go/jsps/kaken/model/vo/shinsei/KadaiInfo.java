/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : KadaiInfo.java
 *    Description : �����ۑ����ێ�����N���X�B
 *
 *    Author      : Admin
 *    Date        : 2003/01/10
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    v1.0        Admin          �V�K�쐬
 *    2006/07/20    v1.1        DIS.dyh        �ύX
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo.shinsei;

import jp.go.jsps.kaken.model.vo.ShinseiDataPk;

/**
 * �����ۑ����ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: KadaiInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public class KadaiInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** �����ۑ薼(�a���j */
	private String kadaiNameKanji;
	
	/** �����ۑ薼(�p���j */
	private String kadaiNameEigo;
	
	/** ���Ƌ敪 */
	private String jigyoKubun;
	
	/** �R���敪 */
	private String shinsaKubun;
	
	/** �R���敪���� */
	private String shinsaKubunMeisho;
	
	/** �����ԍ� */
	private String bunkatsuNo;
	
	/** �����ԍ����� */
	private String bunkatsuNoMeisho;
	
	/** �����Ώۂ̗ތ^ */
	private String kenkyuTaisho;
	
	/** �n���̋敪�ԍ� */
	private String keiNameNo;
	
	/** �n���̋敪 */
	private String keiName;
	
	/** �n���̋敪�i���́j */
	private String keiNameRyaku;
	
	/** �זڔԍ� */
	private String bunkaSaimokuCd;
	
	/** ���� */
	private String bunya;
	
	/** ���� */
	private String bunka;
	
	/** �ז� */
	private String saimokuName;
	
	/** �זڔԍ�2 */
	private String bunkaSaimokuCd2;
	
	/** ����2 */
	private String bunya2;
	
	/** ����2 */
	private String bunka2;
	
	/** �ז�2 */
	private String saimokuName2;
	
	/** ���E�̊ϓ_�ԍ� */
	private String kantenNo;
	
	/** ���E�̊ϓ_ */
	private String kanten;
	
	/** ���E�̊ϓ_���� */
	private String kantenRyaku;
	
	/** �� */
	private int edition;
	
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public KadaiInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

    /**
     * �����ۑ薼(�a���j���擾
     * @return �����ۑ薼(�a���j
     */
    public String getKadaiNameKanji() {
        return kadaiNameKanji;
    }

    /**
     * �����ۑ薼(�a���j��ݒ�
     * @param string �����ۑ薼(�a���j
     */
    public void setKadaiNameKanji(String string) {
        kadaiNameKanji = string;
    }

    /**
     * �����ۑ薼(�p���j���擾
     * @return �����ۑ薼(�p���j
     */
    public String getKadaiNameEigo() {
        return kadaiNameEigo;
    }

    /**
     * �����ۑ薼(�p���j��ݒ�
     * @param string �����ۑ薼(�p���j
     */
    public void setKadaiNameEigo(String string) {
        kadaiNameEigo = string;
    }

    /**
     * ���Ƌ敪��ݒ�
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
     * �R���敪���̂��擾
     * @return �R���敪����
     */
    public String getShinsaKubunMeisho() {
        return shinsaKubunMeisho;
    }

    /**
     * �R���敪���̂�ݒ�
     * @param string �R���敪����
     */
    public void setShinsaKubunMeisho(String string) {
        shinsaKubunMeisho = string;
    }

    /**
     * �����ԍ����擾
     * @return �����ԍ�
     */
    public String getBunkatsuNo() {
        return bunkatsuNo;
    }

    /**
     * �����ԍ���ݒ�
     * @param string �����ԍ�
     */
    public void setBunkatsuNo(String string) {
        bunkatsuNo = string;
    }

    /**
     * �����ԍ����̂��擾
     * @return �����ԍ�����
     */
    public String getBunkatsuNoMeisho() {
        return bunkatsuNoMeisho;
    }

    /**
     * �����ԍ����̂�ݒ�
     * @param string �����ԍ�����
     */
    public void setBunkatsuNoMeisho(String string) {
        bunkatsuNoMeisho = string;
    }

    /**
     * �����Ώۂ̗ތ^���擾
     * @return �����Ώۂ̗ތ^
     */
    public String getKenkyuTaisho() {
        return kenkyuTaisho;
    }

    /**
     * �����Ώۂ̗ތ^��ݒ�
     * @param string �����Ώۂ̗ތ^
     */
    public void setKenkyuTaisho(String string) {
        kenkyuTaisho = string;
    }

    /**
     * �n���̋敪�ԍ����擾
     * @return �n���̋敪�ԍ�
     */
    public String getKeiNameNo() {
        return keiNameNo;
    }

    /**
     * �n���̋敪�ԍ���ݒ�
     * @param string �n���̋敪�ԍ�
     */
    public void setKeiNameNo(String string) {
        keiNameNo = string;
    }

    /**
     * �n���̋敪���擾
     * @return �n���̋敪
     */
    public String getKeiName() {
        return keiName;
    }

    /**
     * �n���̋敪��ݒ�
     * @param string �n���̋敪
     */
    public void setKeiName(String string) {
        keiName = string;
    }

    /**
     * �n���̋敪�i���́j���擾
     * @return �n���̋敪�i���́j
     */
    public String getKeiNameRyaku() {
        return keiNameRyaku;
    }

    /**
     * �n���̋敪�i���́j��ݒ�
     * @param string �n���̋敪�i���́j
     */
    public void setKeiNameRyaku(String string) {
        keiNameRyaku = string;
    }

    /**
     * �זڔԍ����擾
     * @return �זڔԍ�
     */
    public String getBunkaSaimokuCd() {
        return bunkaSaimokuCd;
    }

    /**
     * �זڔԍ���ݒ�
     * @param string �זڔԍ�
     */
    public void setBunkaSaimokuCd(String string) {
        bunkaSaimokuCd = string;
    }

    /**
     * ������擾
     * @return ����
     */
    public String getBunya() {
        return bunya;
    }

    /**
     * �����ݒ�
     * @param string ����
     */
    public void setBunya(String string) {
        bunya = string;
    }

	/**
     * ���Ȃ��擾
	 * @return String ����
	 */
	public String getBunka() {
		return bunka;
	}

    /**
     * ���Ȃ�ݒ�
     * @param string ����
     */
    public void setBunka(String string) {
        bunka = string;
    }

    /**
     * �זڂ��擾
     * @return �ז�
     */
    public String getSaimokuName() {
        return saimokuName;
    }

    /**
     * �זڂ�ݒ�
     * @param string �ז�
     */
    public void setSaimokuName(String string) {
        saimokuName = string;
    }

    /**
     * �זڔԍ�2���擾
     * @return �זڔԍ�2
     */
    public String getBunkaSaimokuCd2() {
        return bunkaSaimokuCd2;
    }

    /**
     * �זڔԍ�2��ݒ�
     * @param string �זڔԍ�2
     */
    public void setBunkaSaimokuCd2(String string) {
        bunkaSaimokuCd2 = string;
    }

    /**
     * ����2���擾
     * @return ����2
     */
    public String getBunya2() {
        return bunya2;
    }

    /**
     * ����2��ݒ�
     * @param string ����2
     */
    public void setBunya2(String string) {
        bunya2 = string;
    }

	/**
     * ����2���擾
	 * @return ����2
	 */
	public String getBunka2() {
		return bunka2;
	}

    /**
     * ����2��ݒ�
     * @param string ����2
     */
    public void setBunka2(String string) {
        bunka2 = string;
    }

    /**
     * �ז�2���擾
     * @return �ז�2
     */
    public String getSaimokuName2() {
        return saimokuName2;
    }

    /**
     * �ז�2��ݒ�
     * @param string �ז�2
     */
    public void setSaimokuName2(String string) {
        saimokuName2 = string;
    }

    /**
     * ���E�̊ϓ_�ԍ����擾
     * @return ���E�̊ϓ_�ԍ�
     */
    public String getKantenNo() {
        return kantenNo;
    }

    /**
     * ���E�̊ϓ_�ԍ���ݒ�
     * @param string ���E�̊ϓ_�ԍ�
     */
    public void setKantenNo(String string) {
        kantenNo = string;
    }

	/**
     * ���E�̊ϓ_���擾
	 * @return ���E�̊ϓ_
	 */
	public String getKanten() {
		return kanten;
	}

    /**
     * ���E�̊ϓ_��ݒ�
     * @param string ���E�̊ϓ_
     */
    public void setKanten(String string) {
        kanten = string;
    }

	/**
     * ���E�̊ϓ_���̂��擾
	 * @return ���E�̊ϓ_����
	 */
	public String getKantenRyaku() {
		return kantenRyaku;
	}

    /**
     * ���E�̊ϓ_���̂�ݒ�
     * @param string ���E�̊ϓ_����
     */
    public void setKantenRyaku(String string) {
        kantenRyaku = string;
    }

	/**
     * �ł�ݒ�
	 * @return int ��
	 */
	public int getEdition() {
		return edition;
	}

	/**
     * �ł�ݒ�
	 * @param edition ��
	 */
	public void setEdition(int edition) {
		this.edition = edition;
	}
}
