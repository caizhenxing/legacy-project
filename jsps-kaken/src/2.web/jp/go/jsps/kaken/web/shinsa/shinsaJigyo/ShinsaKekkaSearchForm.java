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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * �R�����ʌ����t�H�[��
 * ID RCSfile="$RCSfile: ShinsaKekkaSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class ShinsaKekkaSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ����ID */
	private String jigyoId;
	
	/** �R�����ʁi�_���j */
	private String kekkaTen;	

	//2006.06.08 iso �R���S�����ƈꗗ�ł̎��Ɩ��\�������C��
//	//2006/04/13 add start ly 
//	/** ���Ɩ� */
//	private String jigyoName;
	
//2006/04/18 �c �ǉ���������
	/**�@���Ƌ敪 */
	private String jigyoKubun;
//2006/04/18 �c �ǉ������܂�
    
//2006/05/12 �c �ǉ���������
    /**�@�R���� */
    private String shinsaJokyo;
//2006/05/12 �c �ǉ������܂� 
   
//2006/10/27 �c�@�ǉ���������
    /** ���Q�֌W */
    private String rigai;
//2006/10/27�@�c�@�ǉ������܂�        
    //�E�E�E�E�E�E�E�E�E�E
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsaKekkaSearchForm() {
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
		jigyoId = "";  // ����ID
		kekkaTen = ""; // �R�����ʁi�_���j
//		jigyoName="";
		jigyoKubun=""; // ���Ƌ敪
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
     * �R�����ʁi�_���j���擾
     * @return �R�����ʁi�_���j
     */
	public String getKekkaTen() {
		return kekkaTen;
	}

    /**
     * �R�����ʁi�_���j��ݒ�
     * @param string �R�����ʁi�_���j
     */
	public void setKekkaTen(String string) {
		kekkaTen = string;
	}

//	public String getJigyoName() {
//		return jigyoName;
//	}
//
//	public void setJigyoName(String jigyoName) {
//		this.jigyoName = jigyoName;
//	}

    /**
     * ���Ƌ敪���擾
     * @return ���Ƌ敪
     */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

    /**
     * ���Ƌ敪��ݒ�
     * @param jigyoKubun ���Ƌ敪
     */
	public void setJigyoKubun(String jigyoKubun) {
		this.jigyoKubun = jigyoKubun;
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
     * @param shinsaJyokyo �R����
     */
    public void setShinsaJokyo(String shinsaJyokyo) {
        this.shinsaJokyo = shinsaJyokyo;
	}

    /**
     * @return Returns the rigai.
     */
    public String getRigai() {
        return rigai;
    }

    /**
     * @param rigai The rigai to set.
     */
    public void setRigai(String rigai) {
        this.rigai = rigai;
    }
}
