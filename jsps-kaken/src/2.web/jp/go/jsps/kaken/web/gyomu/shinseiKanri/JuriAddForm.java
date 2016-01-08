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
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: JuriAddForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class JuriAddForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �V�X�e����t�ԍ� */
	private String systemNo;
    
    /** ���ƃR�[�h */
    private String jigyoCd;

	/** ���l */
	private String juriBiko;

	/** �󗝌��� */
	private String juriKekka;
	
	/** �󗝌��ʏ��I�����X�g */
	private List juriKekkaList = new ArrayList();	
	
	/** �󗝐����ԍ� */
	private String juriSeiriNo;

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public JuriAddForm() {
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
		systemNo = "";
		juriBiko = "";
		juriKekka = "";
		juriSeiriNo = "";
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
	 * @return
	 */
	public String getJuriBiko() {
		return juriBiko;
	}

	/**
	 * @return
	 */
	public String getSystemNo() {
		return systemNo;
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
	public void setSystemNo(String string) {
		systemNo = string;
	}

	/**
	 * @return
	 */
	public String getJuriKekka() {
		return juriKekka;
	}

	/**
	 * @return
	 */
	public List getJuriKekkaList() {
		return juriKekkaList;
	}

	/**
	 * @param string
	 */
	public void setJuriKekka(String string) {
		juriKekka = string;
	}

	/**
	 * @param list
	 */
	public void setJuriKekkaList(List list) {
		juriKekkaList = list;
	}

	/**
	 * @return �����ԍ���Ԃ�
	 */
	public String getJuriSeiriNo(){
		return juriSeiriNo;
	}
	
	/**
	 * @param str �����ԍ����Z�b�g����
	 */
	public void setJuriSeiriNo(String str){
		juriSeiriNo = str;
	}

    /**
     * @return Returns the jigyoCd.
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * @param jigyoCd The jigyoCd to set.
     */
    public void setJigyoCd(String jigyoCd) {
        this.jigyoCd = jigyoCd;
    }

}
