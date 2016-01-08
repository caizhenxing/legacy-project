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

import java.util.ArrayList;
import java.util.List;

/**
 * ����U�茋�ʌ���������ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinsaJokyoSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class ShinsaJokyoSearchInfo extends SearchInfo{

	/**
     * <code>serialVersionUID</code> �̃R�����g
     */
    private static final long serialVersionUID = -1427338328937100345L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �R�����ԍ� */
	private String shinsainNo;

	/** �R�������i����-���j */
	private String nameKanjiSei;

	/** �R�������i����-���j */
	private String nameKanjiMei;

// 2006/07/03 dyh update start ���R�F��ʂŐR�������������@�֖���ǉ�
	/** �R�������������@�֖� */
	private String shozokuName;
// 2006/07/03 dyh update end

//	/** ���Ɩ� */
//	private String jigyoName;

	/** �N�x */
	private String nendo;

	/** �� */
	private String kaisu;

	/** �P���R�����͕��� */
	private String shinsaType;

	/** ���ƑI��l���X�g */
	private List values = new ArrayList();

	/** �R�������i�t���K�i-���j */
	private String nameKanaSei;

	/** �R�������i�t���K�i-���j */
	private String nameKanaMei;

	/** �����@�փR�[�h */
	private String shozokuCd;

	/** �n���̋敪 */
	private String keiName;

	/** �R���� */
	private String shinsaJokyo;

	/** ����ID */
	private String jigyoId;

	/** ���Ƌ敪 */
	private String jigyoKubun;
//�ŏI���O�C������ǉ�
	/** �ŏI���O�C���� */
	private String loginDate;
	
	/** �ŏI���O�C�����X�g */
	private List loginDateList = new ArrayList();

//���Q�֌W�҂�ǉ�
	/** ���Q�֌W�҃��X�g */
	private List rigaiKankeishaList = new ArrayList();	
	
	/** ���Q�֌W�� */
	private String rigaiKankeisha;

//	�\��������ǉ�
	/** �\������ */
	private String hyojiHoshikiShinsaJokyo;
	
//�����ԍ���ǉ�
	/** �����ԍ��i�w�n�p�j */
	private String seiriNo;

	/** ���Q�֌W���͊����� */
	private String rigaiJokyo;

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsaJokyoSearchInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

//	/**
//	 * @return
//	 */
//	public String getJigyoName() {
//		return jigyoName;
//	}

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
     * �R�������i����-���j���擾
	 * @return �R�������i����-���j
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

    /**
     * �R�������i����-���j��ݒ�
     * @param string �R�������i����-���j
     */
    public void setNameKanjiMei(String string) {
        nameKanjiMei = string;
    }

	/**
     * �R�������i����-���j���擾
	 * @return �R�������i����-���j
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

    /**
     * �R�������i����-���j��ݒ�
     * @param string �R�������i����-���j
     */
    public void setNameKanjiSei(String string) {
        nameKanjiSei = string;
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

// 2006/07/03 dyh update start ���R�F��ʂŐR�������������@�֖���ǉ�
	/**
     * �R�������������@�֖����擾
	 * @return �R�������������@�֖�
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
// 2006/07/03 dyh update end

//	/**
//	 * @param string
//	 */
//	public void setJigyoName(String string) {
//		jigyoName = string;
//	}

	/**
     * �P���R�����͕������擾
	 * @return �P���R�����͕���
	 */
	public String getShinsaType() {
		return shinsaType;
	}

	/**
     * �P���R�����͕�����ݒ�
	 * @param string �P���R�����͕���
	 */
	public void setShinsaType(String string) {
		shinsaType = string;
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
     * �R�������i�t���K�i-���j���擾
	 * @return �R�������i�t���K�i-���j
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}

    /**
     * �R�������i�t���K�i-���j��ݒ�
     * @param string �R�������i�t���K�i-���j
     */
    public void setNameKanaMei(String string) {
        nameKanaMei = string;
    }

	/**
     * �R�������i�t���K�i-���j���擾
	 * @return �R�������i�t���K�i-���j
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}

    /**
     * �R�������i�t���K�i-���j��ݒ�
     * @param string �R�������i�t���K�i-���j
     */
    public void setNameKanaSei(String string) {
        nameKanaSei = string;
    }

	/**
     * �R���󋵂��擾
	 * @return �R����
	 */
	public String getShinsaJokyo() {
		return shinsaJokyo;
	}

    /**
     * �R���󋵂�ݒ�
     * @param string �R����
     */
    public void setShinsaJokyo(String string) {
        shinsaJokyo = string;
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
     * ���ƑI��l���X�g���擾
	 * @return ���ƑI��l���X�g
	 */
	public List getValues() {
		return values;
	}

	/**
     * ���ƑI��l���X�g��ݒ�
	 * @param list ���ƑI��l���X�g
	 */
	public void setValues(List list) {
		values = list;
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

//�ŏI���O�C�����ǉ��@2005/10/24
	/**
     * �ŏI���O�C�������擾
     * @return �ŏI���O�C����
     */
    public String getLoginDate() {
        return loginDate;
    }

	/**
     * �ŏI���O�C������ݒ�
	 * @param string �ŏI���O�C����
	 */
	public void setLoginDate(String string) {
		loginDate = string;
	}
//	�ŏI���O�C�����ǉ�	2005/10/25

	/**
     * �ŏI���O�C�����X�g���擾
     * @return �ŏI���O�C�����X�g
     */
	public List getLoginDateList() {
		return loginDateList;
	}

	/**
     * �ŏI���O�C�����X�g��ݒ�
	 * @param list �ŏI���O�C�����X�g
	 */
	public void setLoginDateList(List list) {
		loginDateList = list;
	}
	
//	���Q�֌W�Ғǉ�		2005/10/25
	/**
     * ���Q�֌W�҃��X�g���擾
	 * @return ���Q�֌W�҃��X�g
	 */
	public List getRigaiKankeishaList() {
		return rigaiKankeishaList;
	}
	
	/**
     * ���Q�֌W�҃��X�g��ݒ�
	 * @param list ���Q�֌W�҃��X�g
	 */
	public void setRigaiKankeishaList(List list) {
		rigaiKankeishaList = list;
	}

	/**
     * ���Q�֌W�҂��擾
	 * @return ���Q�֌W��
	 */
	public String getRigaiKankeisha() {
		return rigaiKankeisha;
	}

	/**
     * ���Q�֌W�҂�ݒ�
	 * @param string ���Q�֌W��
	 */
	public void setRigaiKankeisha(String string) {
		rigaiKankeisha = string;
	}

//�����ԍ��i�w�n�p�j�ǉ�	2005/11/2
	/**
     * �����ԍ��i�w�n�p�j���擾
	 * @return �����ԍ��i�w�n�p�j
	 */
	public String getSeiriNo() {
		return seiriNo;
	}

	/**
     * �����ԍ��i�w�n�p�j��ݒ�
	 * @param string �����ԍ��i�w�n�p�j
	 */
	public void setSeiriNo(String string) {
		seiriNo = string;
	}

	/**
     * �\���������擾
	 * @return �\������
	 */
	public String getHyojiHoshikiShinsaJokyo() {
		return hyojiHoshikiShinsaJokyo;
	}
	
	/**
     * �\��������ݒ�
	 * @param string �\������
	 */
	public void setHyojiHoshikiShinsaJokyo(String string) {
		hyojiHoshikiShinsaJokyo = string;
	}

	/**
     * rigaiJokyo���擾���܂��B
     * 
     * @return rigaiJokyo
     */
    
    public String getRigaiJokyo() {
    	return rigaiJokyo;
    }

	/**
     * rigaiJokyo��ݒ肵�܂��B
     * 
     * @param rigaiJokyo rigaiJokyo
     */
    
    public void setRigaiJokyo(String rigaiJokyo) {
    	this.rigaiJokyo = rigaiJokyo;
    }
}