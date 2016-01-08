/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : �c�c
 *    Date        : 2006/10/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/10/28    v1.0        �c�c                        �V�K
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.IShinsaKekkaMaintenance;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * ���Q�֌W�t�H�[��
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaRigaiForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:58 $"
 */
public class ShinsaKekkaRigaiForm extends BaseValidatorForm {
    
    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------

    /**
     * <code>serialVersionUID</code> �̃R�����g
     */
    private static final long serialVersionUID = -3846050228857955697L;

	/** �V�X�e���ԍ� */
    private String systemNo;
    
    /** �R�����g */
    private String comments;
    
    /** ���Q�֌W */
    private String rigai;
    
    /** ���Q�֌W���X�g */
    private List rigaiList = new ArrayList();

	//�R���ӌ��������`�F�b�N�̈� 2007/5/8
    /** ���ƃR�[�h */
	private String jigyoCd;

	//---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------

    /**
     * �R���X�g���N�^�B
     */
    public ShinsaKekkaRigaiForm() {
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
        systemNo = "";         // �V�X�e���ԍ�
        comments = "";
        rigai = "";
        jigyoCd = "";
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
        
        if (IShinsaKekkaMaintenance.RIGAI_OFF.equals(rigai)){
            if(comments != null && comments.length() != 0 ){
                errors.add(ActionErrors.GLOBAL_ERROR,
                           new ActionError("errors.5070"));  
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
     * ���Q�֌W���X�g���擾
     * @return ���Q�֌W���X�g
     */
    public List getRigaiList() {
        return rigaiList;
    }

    /**
     * ���Q�֌W���X�g��ݒ�
     * @param list ���Q�֌W���X�g
     */
    public void setRigaiList(List list) {
        rigaiList = list;
    }
    
    /**
     * �R�����g���擾
     * @return �R�����g
     */
    public String getComments() {
        return comments;
    }

    /**
     * �R�����g��ݒ�
     * @param string �R�����g
     */
    public void setComments(String string) {
        comments = string;
    }

    /**
     * ���Q�֌W���擾
     * @return ���Q�֌W
     */
    public String getRigai() {
        return rigai;
    }

    /**
     * ���Q�֌W��ݒ�
     * @param string ���Q�֌W
     */
    public void setRigai(String string) {
        rigai = string;
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
}