/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : teisyutuForm.java
 *    Description : ���̈�ԍ����s�A���发�ޏ��F�E�p���p�t�H�[��
 *
 *    Author      : DIS.liuYi
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.liuYi      �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

/**
 * ���̈�ԍ����s�A���发�ޏ��F�E�p���p�t�H�[��
 * 
 * ID RCSfile="$RCSfile: TeisyutuForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class TeisyutuForm extends BaseSearchForm {
    //  ---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------

    /** �V�X�e����t�ԍ� */
    private String ryoikiSystemNo;

    /** ���̈�ԍ� */
    private String kariryoikiNo;

    /** ���ƃR�[�h */
    private String jigyoCd;
    
    /** �J�ډ�ʃt���O */
    private String screenFlg;

    /**
     * �R���X�g���N�^�B
     */
    public TeisyutuForm() {
        super();
        init();
    }

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
        ryoikiSystemNo = "";
        screenFlg = "";
        jigyoCd   ="";
        kariryoikiNo ="";
    }

    /* 
     * ���̓`�F�b�N�B
     * (�� Javadoc)
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(ActionMapping mapping,
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

        return errors;
    }

    /**
     * @return Returns the ryoikiSystemNo.
     */
    public String getRyoikiSystemNo() {
        return ryoikiSystemNo;
    }

    /**
     * @param ryoikiSystemNo The ryoikiSystemNo to set.
     */
    public void setRyoikiSystemNo(String ryoikiSystemNo) {
        this.ryoikiSystemNo = ryoikiSystemNo;
    }

    /**
     * @return Returns the screenFlg.
     */
    public String getScreenFlg() {
        return screenFlg;
    }

    /**
     * @param screenFlg The screenFlg to set.
     */
    public void setScreenFlg(String screenFlg) {
        this.screenFlg = screenFlg;
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

    /**
     * @return Returns the kariryoikiNo.
     */
    public String getKariryoikiNo() {
        return kariryoikiNo;
    }

    /**
     * @param kariryoikiNo The kariryoikiNo to set.
     */
    public void setKariryoikiNo(String kariryoikiNo) {
        this.kariryoikiNo = kariryoikiNo;
    }
}