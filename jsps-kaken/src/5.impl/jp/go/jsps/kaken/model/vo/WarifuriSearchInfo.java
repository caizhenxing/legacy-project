/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : WarifuriSearchInfo.java
 *    Description : ����U�茋�ʌ���������ێ�����N���X
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    V1.0        Admin          �V�K�쐬
 *    2006/07/03    V1.1        DIS.dyh        �ύX
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * ����U�茋�ʌ���������ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: WarifuriSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class WarifuriSearchInfo extends SearchInfo{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** �V�X�e���ԍ� */
	private String systemNo;

	/** �\���ԍ� */
	private String uketukeNo;

	/** ���Ƌ敪 */
	private String jigyoKubun;
	
	/** ���ƃR�[�h */
	private String jigyoCd;

	/** �N�x */
	private String nendo;

	/** �� */
	private String kaisu;

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

	/** �����@�փR�[�h */
	private String shozokuCd;	
	
	/** �זڔԍ� */
	private String bunkaSaimokuCd;
	
	/** �n���̋敪 */
	private String keiName;

// 2006/07/03 dyh add start ���R�F��ʂŐR�������������@�֖���ǉ�
    /** �R�������������@�֖� */
    private String shozokuName;
// 2006/07/03 dyh add end

//2005/10/17�ǉ�
	/** �����ԍ� */
	private String seiriNo;
	
	/** ���ƑI��l */
	private List jigyoCdValueList = new ArrayList();

	/** �S�����Ƌ敪�i�����j */
	private Set tantoJigyoKubun;

	/** �R�����ԍ� */
	private String shinsainNo;

//	2005/11/8 �ǉ�
	/** ���Q�֌W�� */
	private String rigai;
	
	/** ���U�� */
	private String warifuriFlg;
//	2005/11/8 �ǉ�����
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public WarifuriSearchInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
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
     * ���ƑI��l���擾
	 * @return ���ƑI��l
	 */
	public List getJigyoCdValueList() {
		return jigyoCdValueList;
	}

    /**
     * ���ƑI��l��ݒ�
     * @param list ���ƑI��l
     */
    public void setJigyoCdValueList(List list) {
        jigyoCdValueList = list;
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

// 2006/07/03 dyh add start ���R�F��ʂŐR�������������@�֖���ǉ�
    /**
     * �R�������������@�֖����擾
     * @return String �R�������������@�֖�
     */
    public String getShozokuName() {
        return shozokuName;
    }

    /**
     * �R�������������@�֖���ݒ�
     * @param string �R�������������@�֖�
     */
    public void setShozokuName(String string) {
        shozokuName = string;
    }
// 2006/07/03 dyh add end

	/**
     * �\���Ҏ����i�t���K�i-���j���擾
	 * @return �\���Ҏ����i�t���K�i-���j
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}

    /**
     * �\���Ҏ����i�t���K�i-���j��ݒ�
     * @param string �\���Ҏ����i�t���K�i-���j
     */
    public void setNameKanaMei(String string) {
        nameKanaMei = string;
    }

	/**
     * �\���Ҏ����i�t���K�i-���j���擾
	 * @return �\���Ҏ����i�t���K�i-���j
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}

    /**
     * �\���Ҏ����i�t���K�i-���j��ݒ�
     * @param string �\���Ҏ����i�t���K�i-���j
     */
    public void setNameKanaSei(String string) {
        nameKanaSei = string;
    }

	/**
     * �\���Ҏ����i����-���j���擾
	 * @return �\���Ҏ����i����-���j
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

    /**
     * �\���Ҏ����i����-���j��ݒ�
     * @param string �\���Ҏ����i����-���j
     */
    public void setNameKanjiMei(String string) {
        nameKanjiMei = string;
    }

	/**
     * �\���Ҏ����i����-���j���擾
	 * @return �\���Ҏ����i����-���j
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

    /**
     * �\���Ҏ����i����-���j��ݒ�
     * @param string �\���Ҏ����i����-���j
     */
    public void setNameKanjiSei(String string) {
        nameKanjiSei = string;
    }

	/**
     * �\���Ҏ����i���[�}��-���j���擾
	 * @return �\���Ҏ����i���[�}��-���j
	 */
	public String getNameRoMei() {
		return nameRoMei;
	}

    /**
     * �\���Ҏ����i���[�}��-���j��ݒ�
     * @param string �\���Ҏ����i���[�}��-���j
     */
    public void setNameRoMei(String string) {
        nameRoMei = string;
    }

	/**
     * �\���Ҏ����i���[�}��-���j���擾
	 * @return �\���Ҏ����i���[�}��-���j
	 */
	public String getNameRoSei() {
		return nameRoSei;
	}

    /**
     * �\���Ҏ����i���[�}��-���j��ݒ�
     * @param string �\���Ҏ����i���[�}��-���j
     */
    public void setNameRoSei(String string) {
        nameRoSei = string;
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
     * �R�����ԍ����擾
	 * @return �R�����ԍ�
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

    /**
     * �R�����ԍ���ݒ�
     * @param string �R�����ԍ�
     */
    public void setShinsainNo(String string) {
        shinsainNo = string;
    }

	/**
     * �����@�փR�[�h���擾
	 * @return �����@�փR�[�h
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

    /**
     * �����@�փR�[�h��ݒ�
     * @param string �����@�փR�[�h
     */
    public void setShozokuCd(String string) {
        shozokuCd = string;
    }

	/**
     * �V�X�e���ԍ����擾
	 * @return �V�X�e���ԍ�
	 */
	public String getSystemNo() {
		return systemNo;
	}

    /**
     * �V�X�e���ԍ���ݒ�
     * @param string �V�X�e���ԍ�
     */
    public void setSystemNo(String string) {
        systemNo = string;
    }

	/**
     * �S�����Ƌ敪�i�����j���擾
	 * @return �S�����Ƌ敪�i�����j
	 */
	public Set getTantoJigyoKubun() {
		return tantoJigyoKubun;
	}

	/**
     * �\���ԍ����擾
	 * @return �\���ԍ�
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

    /**
     * �\���ԍ���ݒ�
     * @param string �\���ԍ�
     */
    public void setUketukeNo(String string) {
        uketukeNo = string;
    }

	/**
     * �S�����Ƌ敪�i�����j��ݒ�
	 * @param set �S�����Ƌ敪�i�����j
	 */
	public void setTantoJigyoKubun(Set set) {
		tantoJigyoKubun = set;
	}

//2005/10/17�@�����ԍ��ǉ�
	/**
     * �����ԍ����擾
	 * @return �����ԍ�
	 */
	public String getSeiriNo() {
		return seiriNo;
	}

	/**
     * �����ԍ���ݒ�
	 * @param string �����ԍ�
	 */
	public void setSeiriNo(String string) {
		seiriNo = string;
	}

	/**
	 * ���Q�֌W�҂��擾���܂��B
	 * @return ���Q�֌W��
	 */
	public String getRigai() {
		return rigai;
	}

	/**
	 * ���Q�֌W�҂�ݒ肵�܂��B
	 * @param rigai ���Q�֌W��
	 */
	public void setRigai(String rigai) {
		this.rigai = rigai;
	}

	/**
	 * ���U����擾���܂��B
	 * @return ���U��
	 */
	public String getWarifuriFlg() {
		return warifuriFlg;
	}

	/**
	 * ���U���ݒ肵�܂��B
	 * @param warifuriFlg ���U��
	 */
	public void setWarifuriFlg(String warifuriFlg) {
		this.warifuriFlg = warifuriFlg;
	}
}