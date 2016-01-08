/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : TokuteiSearchForm.java
 *    Description : ����̈挤�������t�H�[��
 *
 *    Author      : �c�c
 *    Date        : 2006/06/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/21    v1.0        �c�c                        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

/**
 * ����̈挤�������t�H�[��
 */
public class TokuteiSearchForm extends BaseSearchForm{

    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------
    
    /** ���ƃR�[�h(�������A�J���}��؂�) */
    private String jigyoCds;
    
    //�E�E�E�E�E�E�E�E�E�E

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------

    /**
     * �R���X�g���N�^�B
     */
    public TokuteiSearchForm() {
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
        jigyoCds = "";
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
     * ���ƃR�[�h���擾
     * @return String ���ƃR�[�h
     */
    public String getJigyoCds() {
        return jigyoCds;
    }

    /**
     * ���ƃR�[�h��ݒ�
     * @param jigyoCds ���ƃR�[�h
     */
    public void setJigyoCds(String jigyoCds) {
        this.jigyoCds = jigyoCds;
    }
}