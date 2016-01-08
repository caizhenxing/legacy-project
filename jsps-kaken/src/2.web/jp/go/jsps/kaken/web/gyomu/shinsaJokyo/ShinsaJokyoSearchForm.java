/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ShinsaJokyoSearchForm.java
 *    Description : �R���󋵌����t�H�[��
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          �V�K�쐬
 *    2006/07/03    V1.1        DIS.dyh        �ύX
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsaJokyo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *  �R���󋵌����t�H�[��
 * 
 * ID RCSfile="$RCSfile: ShinsaJokyoSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:16 $"
 */
public class ShinsaJokyoSearchForm extends BaseSearchForm {

	/**
     * <code>serialVersionUID</code> �̃R�����g
     */
    private static final long serialVersionUID = 5888050503516412637L;

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

	/** ���Ɩ� */
	private String jigyoName;

	/** �N�x */
	private String nendo;

	/** �� */
	private String kaisu;

	/** ���Ɩ��I�����X�g */
	private List jigyoNameList = new ArrayList();

	/** ���Ɩ��I��l���X�g */
	private List values = new ArrayList();

	/** �R�������i�t���K�i-���j */
	private String nameKanaSei;

	/** �R�������i�t���K�i-���j */
	private String nameKanaMei;

	/** ����ҏ��������@�֔ԍ� */
	private String shozokuCd;

	/** �n���̋敪 */
	private String keiName;

	/** �R���󋵃��X�g */
	private List shinsaJokyoList = new ArrayList();

	/** �R���� */
	private String shinsaJokyo;
	
//	�ŏI���O�C������ǉ�
	/** �ŏI���O�C�����X�g */
	private List loginDateList = new ArrayList();

	/** �ŏI���O�C���� */
    private String loginDate;

	/** �V�X�e���ԍ� */
	private String systemNo;

	/** ���Ƌ敪 */
	private String jigyoKubun;

	/** ����ID(�ĐR���p) */
	private String jigyoId;
	
//���Q�֌W�҂�ǉ�
	/** ���Q�֌W�҃��X�g */
	private List rigaiKankeishaList = new ArrayList();
	
	/** ���Q�֌W�� */
	private String rigaiKankeisha;
	
//�\��������ǉ�
	/** �\������ */
	private String hyojiHoshikiShinsaJokyo;

	/** �\���������X�g */
	private List hyojiHoshikiListShinsaJokyo;	
	
//�����ԍ��i�w�n�p�j��ǉ�
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
	public ShinsaJokyoSearchForm() {
		super();
		init();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * �����������B
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//�E�E�E
	}

	/**
	 * ����������
	 */
	public void init() {
		shinsainNo = "";
		nameKanjiSei = "";
		nameKanjiMei = "";
		shozokuName = "";
		jigyoName = "";
		nendo = "";
		kaisu = "";
		values = new ArrayList();
		nameKanaSei = "";
		nameKanaMei = "";
		shozokuCd = "";
		keiName = ""; // �n���̋敪
		shinsaJokyo = "";
		loginDate = "";// �ŏI���O�C����
		systemNo = "";
		jigyoKubun = "";
		jigyoId = "";
		hyojiHoshikiShinsaJokyo = "";
		seiriNo = "";
		rigaiJokyo = "";

	}

	/* 
	 * ���̓`�F�b�N�B
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		//��^����----- 
		ActionErrors errors = super.validate(mapping, request);
		//---------------------------------------------
		// ��{�I�ȃ`�F�b�N(�K�{�A�`�����j��Validator���g�p����B
		//---------------------------------------------

		//��{���̓`�F�b�N�����܂�
		if (!errors.isEmpty()) {
			return errors;
		}

		//��^����----- 

		//�ǉ�����----- 

		//---------------------------------------------
		//�g�ݍ��킹�`�F�b�N	
		//---------------------------------------------
		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
     * ���Ɩ����擾
	 * @return String ���Ɩ�
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
     * ���Ɩ��I�����X�g���擾
	 * @return List ���Ɩ��I�����X�g
	 */
	public List getJigyoNameList() {
		return jigyoNameList;
	}

    /**
     * ���Ɩ��I�����X�g��ݒ�
     * @param list ���Ɩ��I�����X�g
     */
    public void setJigyoNameList(List list) {
        jigyoNameList = list;
    }

	/**
     * �񐔂��擾
	 * @return String ��
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
	 * @return String �R�������i����-���j
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
	 * @return String �R�������i����-���j
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
	 * @return String �N�x
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
	 * @return String �R�����ԍ�
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

    /**
     * �R�����ԍ�
     * @param string �R�����ԍ�
     */
    public void setShinsainNo(String string) {
        shinsainNo = string;
    }

// 2006/07/03 dyh update start ���R�F��ʂŐR�������������@�֖���ǉ�
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
// 2006/07/03 dyh update end

	/**
     * �n���̋敪���擾
	 * @return String �n���̋敪
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
     * ����ҏ��������@�֔ԍ����擾
	 * @return ����ҏ��������@�֔ԍ�
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

    /**
     * ����ҏ��������@�֔ԍ���ݒ�
     * @param string ����ҏ��������@�֔ԍ�
     */
    public void setShozokuCd(String string) {
        shozokuCd = string;
    }

    /**
     * �R�������i�t���K�i-���j���擾
     * @return String �R�������i�t���K�i-���j
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
     * �R���󋵃��X�g���擾
	 * @return List �R���󋵃��X�g
	 */
	public List getShinsaJokyoList() {
		return shinsaJokyoList;
	}

	/**
     * �R���󋵃��X�g��ݒ�
	 * @param list �R���󋵃��X�g
	 */
	public void setShinsaJokyoList(List list) {
		shinsaJokyoList = list;
	}

	/**
     * ���Ɩ��I��l���X�g���擾
	 * @return List ���Ɩ��I��l���X�g
	 */
	public List getValues() {
		return values;
	}

	/**
     * ���Ɩ��I��l���X�g��ݒ�
	 * @param list ���Ɩ��I��l���X�g
	 */
	public void setValues(List list) {
		values = list;
	}

	/**
     * ���Ɩ��I��l���X�g���擾
     * @param key
	 * @return Object ���Ɩ��I��l���X�g
	 */
	public Object getValues(int key) {
		return values.get(key);
	}

	/**
     * ���Ɩ��I��l���X�g��ݒ�
     * @param key
     * @param value
	 * @param string ���Ɩ��I��l���X�g
	 */
	public void setValues(int key, Object value) {
        if (!values.contains(value)) {
            values.add(value);
        }
	}

	/**
     * ���Ƌ敪���擾
	 * @return String ���Ƌ敪
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
     * �V�X�e���ԍ����擾
	 * @return String �V�X�e���ԍ�
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
     * ����ID(�ĐR���p)���擾
	 * @return String ����ID(�ĐR���p)
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
     * ����ID(�ĐR���p)��ݒ�
	 * @param string ����ID(�ĐR���p)
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

//�ŏI���O�C�����ǉ�	2005/10/24
	/**
     * �ŏI���O�C�����X�g���擾
	 * @return List �ŏI���O�C�����X�g
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

	/**
     * �ŏI���O�C�������擾
	 * @return String �ŏI���O�C����
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

//���Q�֌W�Ғǉ�	2005/10/25
	/**
     * ���Q�֌W�҃��X�g���擾
	 * @return List ���Q�֌W�҃��X�g
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
	 * @return String ���Q�֌W��
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

//�\��������ǉ�
	/**
     * �\���������擾
	 * @return String �\������
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
     * �\���������X�g���擾
	 * @return List �\���������X�g
	 */
	public List getHyojiHoshikiListShinsaJokyo() {
		return hyojiHoshikiListShinsaJokyo;
	}

	/**
     * �\���������X�g��ݒ�
	 * @param list �\���������X�g
	 */
	public void setHyojiHoshikiListShinsaJokyo(List list) {
		hyojiHoshikiListShinsaJokyo = list;
	}
		
//�����ԍ���ǉ�
	/**
     * �����ԍ��i�w�n�p�j���擾
	 * @return String �����ԍ��i�w�n�p�j
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