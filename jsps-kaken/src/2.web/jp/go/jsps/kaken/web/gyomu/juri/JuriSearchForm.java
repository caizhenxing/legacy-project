/*======================================================================
 *    SYSTEM      : �d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : JuriSearchForm
 *    Description : �󗝓o�^�Ώۉ����񌟍��p�t�H�[��
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/05/30
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.juri;

import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

/**
 * �󗝓o�^�Ώۉ����񌟍��p�t�H�[��
 */
public class JuriSearchForm extends BaseSearchForm{

    /** VersionUID */
    private static final long serialVersionUID = -2147403507609272092L;

    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------
    /** ����CD */
    private String jigyoCd;

    /** ������ږ� */
    private String jigyoNm;

    /** ������ږ����X�g */
    private List jigyoNmList = new ArrayList();

    /** �����R�[�h */
    private String shozokuCd;

    /** ���������@�֖� */
    private String shozokuNm;

    /**
     * ����CD���擾
     * @return String ����CD
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * ����CD��ݒ�
     * @param jigyoCd ����CD
     */
    public void setJigyoCd(String jigyoCd) {
        this.jigyoCd = jigyoCd;
    }

    /**
     * ������ږ����擾
     * @return String ������ږ�
     */
    public String getJigyoNm() {
        return jigyoNm;
    }

    /**
     * ������ږ���ݒ�
     * @param jigyoNm ������ږ�
     */
    public void setJigyoNm(String jigyoNm) {
        this.jigyoNm = jigyoNm;
    }

    /**
     * ������ږ����X�g���擾
     * @return String ������ږ����X�g
     */
    public List getJigyoNmList() {
        return jigyoNmList;
    }

    /**
     * ������ږ����X�g��ݒ�
     * @param jigyoNmList ������ږ����X�g
     */
    public void setJigyoNmList(List jigyoNmList) {
        this.jigyoNmList = jigyoNmList;
    }

    /**
     * �����R�[�h���擾
     * @return String �����R�[�h
     */
    public String getShozokuCd() {
        return shozokuCd;
    }

    /**
     * �����R�[�h��ݒ�
     * @param shozokuCd �����R�[�h
     */
    public void setShozokuCd(String shozokuCd) {
        this.shozokuCd = shozokuCd;
    }

    /**
     * ���������@�֖����擾
     * @return String ���������@�֖�
     */
    public String getShozokuNm() {
        return shozokuNm;
    }

    /**
     * ���������@�֖���ݒ�
     * @param shozokuNm ���������@�֖�
     */
    public void setShozokuNm(String shozokuNm) {
        this.shozokuNm = shozokuNm;
    }
}