/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : WarifuriSearchForm.java
 *    Description : ����U�茋�ʌ����t�H�[��
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
package jp.go.jsps.kaken.web.gyomu.warifuri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *  ����U�茋�ʌ����t�H�[��
 * 
 * ID RCSfile="$RCSfile: WarifuriSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class WarifuriSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �\���ԍ� */
	private String uketukeNo;

	/** ���ƃR�[�h */
	private String jigyoCd;

	/** �N�x */
	private String nendo;

	/** �� */
	private String kaisu;

	/** �\���Ҏ����i������-���j */
	private String nameKanjiSei;
	
	/** �\���Ҏ����i������-���j */
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

	/** �����ԍ� */
	private String seiriNo;	
	
//	2005/11/8 �ǉ�
	/** ���Q�֌W�� */
	private String rigai;
	
	/** ���U�� */
	private String warifuriFlg;
//	2005/11/8 �ǉ�����
	
	/** ���Ɩ��I�����X�g */
	private List jigyoNameList = new ArrayList();
	
	/** ���ƑI��l */
	//�`�F�b�N�{�b�N�X�̃v���p�e�B����values�œ��ꂳ��Ă�̂ŁA�ύX����ƒl���Z�b�g����Ȃ��̂Œ���
	private List values = new ArrayList();
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public WarifuriSearchForm() {
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
		uketukeNo = "";          // �\���ԍ�
		jigyoCd = "";            // ���ƃR�[�h
		nendo = "";              // �N�x
		kaisu = "";              // ��
	    nameKanjiSei = "";       // �\���Ҏ����i������-���j
	    nameKanjiMei = "";       // �\���Ҏ����i������-���j
	    nameKanaSei = "";        // �\���Ҏ����i�t���K�i-���j
	    nameKanaMei = "";        // �\���Ҏ����i�t���K�i-���j
	    nameRoSei = "";          // �\���Ҏ����i���[�}��-���j
	    nameRoMei = "";          // �\���Ҏ����i���[�}��-���j
	    shozokuCd = "";          // �����@�փR�[�h
	    bunkaSaimokuCd = "";     // �זڔԍ�
	    keiName = "";            // �n���̋敪
        shozokuName = "";        // �R�������������@�֖�
	    seiriNo = "";            // �����ԍ�
	    rigai = "";              // ���Q�֌W��
	    warifuriFlg = "";        // ���U��
		values = new ArrayList();// ���ƑI��l
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
		
		//�w�p�n���p�Ɗ�Ռ������p�̗���ڂ��`�F�b�N�{�b�N�X�őI�����Ă����ꍇ�̓G���[�i�`�F�b�N�{�b�N�X�̑I���͕K�{�j
		List list = getValueList();
		
		if(list.size() == 0){
			errors.add(
				ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.2002", new String[]{"������ږ�"}));
		}
		
		for(int i = 0; i < list.size(); i++){
			if(list.size() >= 2 && IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO.equals((String)list.get(i))){
				errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.5062", new String[]{"������ږ�"}));	
			}
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
     * ���Ɩ��I�����X�g���擾
	 * @return ���Ɩ��I�����X�g
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
     * �\���Ҏ����i������-���j���擾
	 * @return �\���Ҏ����i������-���j
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

    /**
     * �\���Ҏ����i������-���j��ݒ�
     * @param string �\���Ҏ����i������-���j
     */
    public void setNameKanjiMei(String string) {
        nameKanjiMei = string;
    }

	/**
     * �\���Ҏ����i������-���j���擾
	 * @return �\���Ҏ����i������-���j
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

    /**
     * �\���Ҏ����i������-���j��ݒ�
     * @param string �\���Ҏ����i������-���j
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
     * �\���ԍ����擾
	 * @return String �\���ԍ�
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
     * �����ԍ����擾
	 * @return �����ԍ�
	 */
	public String getSeiriNo(){
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
     * ���ƑI��l���擾
	 * @return ���ƑI��l
	 */
	public List getValueList() {
		return values;
	}

	/**
     * ���ƑI��l��ݒ�
	 * @param list ���ƑI��l
	 */
	public void setValueList(List list) {
		values = list;
	}
	
	/**
     * ���ƑI��l���擾
     * @param key
	 * @return ���ƑI��l
	 */
	public Object getValues(int key) {
		return values.get(key);
	}

	/**
     * ���ƑI��l��ݒ�
     * @param key
     * @param value
	 * @param string ���ƑI��l
	 */
	public void setValues(int key, Object value) {
		if(!values.contains(value)){
			values.add(value);
		}
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