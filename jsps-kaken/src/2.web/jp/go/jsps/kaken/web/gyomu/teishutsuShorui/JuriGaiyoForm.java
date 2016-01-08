/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : JuriGaivoForm.java
 *    Description : ��o���ފǗ��i����̈�i�V�K�j�j�p�t�H�[��
 *
 *    Author      : DIS.jzx
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.jzx        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ��o���ފǗ��i����̈�i�V�K�j�j�p�t�H�[��
 * ID RCSfile="$RCSfile: JuriGaiyoForm.java,v $" 
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class JuriGaiyoForm extends BaseValidatorForm {

    // ---------------------------------------------------------------------
    // Instance data
    // ---------------------------------------------------------------------

    /** �V�X�e����t�ԍ� */
    private String systemNo;

    /** �󗝌��� */
    private String juriKekka;

    /** �󗝌��ʏ��I�����X�g */
    private List juriKekkaList = new ArrayList();

    /** �N�x */
    private String nendo;

    /** �� */
    private String kaisu;

    /** ���Ɩ� */
    private String jigyoName;
    
    /** �����R�[�h */
    private String shozokuCd;  

    /** �����ԍ� */
    private String uketukeNo;
    
    /** �����ԍ� */
    private String ryoikiJokyoId;
    
    /** ����Id */
    private String jigyoId;  
    
    /** ���ƃR�[�h */
    private String jigyoCd;

    /** ��Id */
    private String jokyoId;

    /** �󗝌��ʃ��X�g�\�������� */
    private List juriFujuri = new ArrayList();

    /** ���匏�� */
    private String count;
    // �E�E�E�E�E�E�E�E�E�E

    // ---------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------

    /**
     * �R���X�g���N�^�B
     */
    public JuriGaiyoForm() {
        super();
        init();
    }

    // ---------------------------------------------------------------------
    // Public methods
    // ---------------------------------------------------------------------

    /**
     * �����������B 
     * 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        // �E�E�E
    }

    /**
     * ����������
     */
    public void init() {
//      2006/06/21 by jzx start
        count="";
        systemNo = "";
        juriKekka = "";
        nendo="";
        kaisu="";
        jigyoName="";
        uketukeNo="";
        ryoikiJokyoId="";
//      2006/06/21 by jzx end       
        shozokuCd="";
        jigyoId="";
        jigyoCd="";
        jokyoId=""; 
    }

    /*
     * ���̓`�F�b�N�B (�� Javadoc)
     * 
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        // ��^����-----
        ActionErrors errors = super.validate(mapping, request);

        // ---------------------------------------------
        // ��{�I�ȃ`�F�b�N(�K�{�A�`�����j��Validator���g�p����B
        // ---------------------------------------------

        // ��{���̓`�F�b�N�����܂�
        if (!errors.isEmpty()) {
            return errors;
        }

        // ��^����-----

        // �ǉ�����-----
        // ---------------------------------------------
        // �g�ݍ��킹�`�F�b�N
        // ---------------------------------------------

        return errors;
    }

    // ---------------------------------------------------------------------
    // Properties
    // ---------------------------------------------------------------------

    /**
     * @return
     */
    public String getSystemNo() {
        return systemNo;
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
     * @return
     */
    public String getJigyoName() {
        return jigyoName;
    }

    /**
     * @param jigyoName
     */
    public void setJigyoName(String jigyoName) {
        this.jigyoName = jigyoName;
    }

    /**
     * @return
     */
    public String getKaisu() {
        return kaisu;
    }

    /**
     * @param kaisu
     */
    public void setKaisu(String kaisu) {
        this.kaisu = kaisu;
    }

    /**
     * @return
     */
    public String getNendo() {
        return nendo;
    }

    /**
     * @param nendo
     */
    public void setNendo(String nendo) {
        this.nendo = nendo;
    }

    /**
     * @return
     */
    public String getUketukeNo() {
        return uketukeNo;
    }

    /**
     * @param uketukeNo
     */
    public void setUketukeNo(String uketukeNo) {
        this.uketukeNo = uketukeNo;
    }

    /**
     * @return Returns the ryoikiJokyoId.
     */
    public String getRyoikiJokyoId() {
        return ryoikiJokyoId;
    }

    /**
     * @param ryoikiJokyoId The ryoikiJokyoId to set.
     */
    public void setRyoikiJokyoId(String ryoikiJokyoId) {
        this.ryoikiJokyoId = ryoikiJokyoId;
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
     * @return Returns the jigyoId.
     */
    public String getJigyoId() {
        return jigyoId;
    }

    /**
     * @param jigyoId The jigyoId to set.
     */
    public void setJigyoId(String jigyoId) {
        this.jigyoId = jigyoId;
    }

    /**
     * @return Returns the jokyoId.
     */
    public String getJokyoId() {
        return jokyoId;
    }

    /**
     * @param jokyoId The jokyoId to set.
     */
    public void setJokyoId(String jokyoId) {
        this.jokyoId = jokyoId;
    }

    /**
     * @return Returns the juriFujuri.
     */
    public List getJuriFujuri() {
        return juriFujuri;
    }

    /**
     * @param juriFujuri The juriFujuri to set.
     */
    public void setJuriFujuri(List juriFujuri) {
        this.juriFujuri = juriFujuri;
    }

    /**
     * @return Returns the shozokuCd.
     */
    public String getShozokuCd() {
        return shozokuCd;
    }

    /**
     * @param shozokuCd The shozokuCd to set.
     */
    public void setShozokuCd(String shozokuCd) {
        this.shozokuCd = shozokuCd;
    }

    /**
     * @return Returns the count.
     */
    public String getCount() {
        return count;
    }

    /**
     * @param count The count to set.
     */
    public void setCount(String count) {
        this.count = count;
    }
}