/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : JuriCheckListForm.java
 *    Description : �`�F�b�N���X�g�p�t�H�[���B
 *
 *    Author      : Admin
 *    Date        : 2005/04/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/07/15    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import jp.go.jsps.kaken.util.Page;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * �`�F�b�N���X�g�p�t�H�[��
 * 
 * @author masuo_t
 */
public class JuriCheckListForm extends BaseValidatorForm{

// 20050715
    /** �y�[�W��� */
	private Page checkListPage;

    /** ����ID */
	private String jigyoID;

    /** ����CD */
	private String jigyoCD;

    /** ����CD */
	private String shozokuCD;

    /** ���l */
	private String juriBiko;

    /** �󗝌��� */
	private String juriKekka;

    /** ��ID */
	private String jokyoID;

    /** �� */
	private String kaisu;

    /** �󗝌��ʏ��I�����X�g */
	private List juriFujuri = new ArrayList();

// 2006/07/21 dyh delete start ���R�F�g�p���Ȃ�
//    /** �󗝌��ʃ��X�g�\�������� */
//	private String listValue;
// 2006/07/21 dyh delete end

    /** �󗝃t���O:�� */
	static final int Juri = 0;

    /** �󗝃t���O:�s�� */
	static final int Fujuri = 1;
// Horikoshi

/************************************************************************************/

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public JuriCheckListForm() {
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
		jigyoID		=	"";							/** ����ID */
		jigyoCD		=	"";							/** ����CD */
		shozokuCD	=	"";							/** ����CD */
		juriBiko	=	"";							/** ���l */
		juriKekka	=	"";							/** �󗝌��� */
		jokyoID		=	"";
		kaisu		=	"";
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

// 20050715

	/** �y�[�W��� **/
    /**
     * �y�[�W�����擾
     * @return �y�[�W���
     */
	public Page getCheckListPage() {
		return checkListPage;
	}

    /**
     * �y�[�W����ݒ�
     * @param checkListPage �y�[�W���
     */
	public void setCheckListPage(Page checkListPage) {
		this.checkListPage = checkListPage;
	}

	/** �󗝕s�� **/
    /**
     * �󗝌��ʏ��I�����X�g���擾
     * @return �󗝌��ʏ��I�����X�g
     */
	public List getJuriFujuri() {
		return juriFujuri;
	}

    /**
     * �󗝌��ʏ��I�����X�g��ݒ�
     * @param JuriFujuri �󗝌��ʏ��I�����X�g
     */
	public void setJuriFujuri(List JuriFujuri) {
		juriFujuri = JuriFujuri;
	}

	/** �󗝔��l **/
    /**
     * ���l���擾
     * @return ���l
     */
	public String getJuriBiko() {
		return juriBiko;
	}
    /**
     * ���l��ݒ�
     * @param JuriBiko ���l
     */
	public void setJuriBiko(String JuriBiko) {
		juriBiko = JuriBiko;
	}

	/** �󗝌��� **/
    /**
     * �󗝌��ʂ��擾
     * @return �󗝌���
     */
	public String getJuriKekka() {
		return juriKekka;
	}
    /**
     * �󗝌��ʂ�ݒ�
     * @param JuriKekka �󗝌���
     */
	public void setJuriKekka(String JuriKekka) {
		juriKekka = JuriKekka;
	}

// Horikoshi

	/** ����ID **/
    /**
     * ����ID���擾
     * @return ����ID
     */
	public String getJigyoID() {
		return jigyoID;
	}
    /**
     * ����ID��ݒ�
     * @param JigyoID ����ID
     */
	public void setJigyoID(String JigyoID) {
		jigyoID = JigyoID;
	}

	/** ����CD **/
    /**
     * ����CD���擾
     * @return ����CD
     */
	public String getShozokuCD() {
		return shozokuCD;
	}
    /**
     * ����CD��ݒ�
     * @param ShozokuCD ����CD
     */
	public void setShozokuCD(String ShozokuCD) {
		shozokuCD = ShozokuCD;
	}

	/** ����CD **/
    /**
     * ����CD���擾
     * @return ����CD
     */
	public String getJigyoCD() {
		return jigyoCD;
	}
    /**
     * ����CD��ݒ�
     * @param JigyoCD ����CD
     */
	public void setJigyoCD(String JigyoCD) {
		jigyoCD = JigyoCD;
	}

	/** �� **/
    /**
     * �񐔂��擾
     * @return ��
     */
	public String getKaisu() {
		return kaisu;
	}
    /**
     * �񐔂�ݒ�
     * @param Kaisu ��
     */
	public void setKaisu(String Kaisu) {
		kaisu = Kaisu;
	}
	
	/** ��ID **/
    /**
     * ��ID���擾
     * @return ��ID
     */
	public String getJokyoID() {
		return jokyoID;
	}
    /**
     * ��ID��ݒ�
     * @param JokyoID ��ID
     */
	public void setJokyoID(String JokyoID) {
		jokyoID = JokyoID;
	}
}