/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : TeishutsuShoruiSearchForm.java
 *    Description : ��o���ތ����p�t�H�[��
 *
 *    Author      : DIS.lwj
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.lwj        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

/**
 * ��o���ތ����p�t�H�[��
 * ID RCSfile="$RCSfile: TeishutsuShoruiSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class TeishutsuShoruiSearchForm extends BaseSearchForm{
    
    /** ����ID */
    private String jigyoId;
    
    /** �����R�[�h */
    private String shozokuCd;
    
    /** ������ڃ��X�g */
    private List JigyoList = new ArrayList();
    
    /** ���ƃR�[�h */
    private String jigyoCd;
    
    /** �󗝏� */
    private String juriJokyo;
    
    /** �����@�֖� */
    private String shozokuName;
    
    /** ���Ƌ敪 */
    private String jigyoKbn;
    
    /** �󗝏󋵃��X�g */
    private List juriList = new ArrayList();

    /** ���̈�ԍ� */
    private String kariryoikiNo;

    /** �R���X�g���N�^ */
    public TeishutsuShoruiSearchForm(){
        jigyoId = "";
        shozokuCd = "";
        jigyoCd = "";
        juriJokyo = "0";
        shozokuName = "";
        kariryoikiNo = "";
    }

    /**
     * ���ƃR�[�h���擾
     * @return String ���ƃR�[�h
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * ���ƃR�[�h��ݒ�
     * @param jigyoCd ���ƃR�[�h
     */
    public void setJigyoCd(String jigyoCd) {
        this.jigyoCd = jigyoCd;
    }

    /**
     * ����ID���擾
     * @return String ����ID
     */
    public String getJigyoId() {
        return jigyoId;
    }

    /**
     * ����ID��ݒ�
     * @param jigyoId ����ID
     */
    public void setJigyoId(String jigyoId) {
        this.jigyoId = jigyoId;
    }

    /**
     * ���Ƌ敪���擾
     * @return String ���Ƌ敪
     */
    public String getJigyoKbn() {
        return jigyoKbn;
    }

    /**
     * ���Ƌ敪��ݒ�
     * @param jigyoKbn ���Ƌ敪
     */
    public void setJigyoKbn(String jigyoKbn) {
        this.jigyoKbn = jigyoKbn;
    }

    /**
     * �󗝏󋵂��擾
     * @return String �󗝏�
     */
    public String getJuriJokyo() {
        return juriJokyo;
    }

    /**
     * �󗝏󋵂�ݒ�
     * @param juriJokyo �󗝏�
     */
    public void setJuriJokyo(String juriJokyo) {
        this.juriJokyo = juriJokyo;
    }

    /**
     * �󗝏󋵃��X�g���擾
     * @return List �󗝏󋵃��X�g
     */
    public List getJuriList() {
        return juriList;
    }

    /**
     * �󗝏󋵃��X�g��ݒ�
     * @param juriList �󗝏󋵃��X�g
     */
    public void setJuriList(List juriList) {
        this.juriList = juriList;
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
     * �����@�֖����擾
     * @return String �����@�֖�
     */
    public String getShozokuName() {
        return shozokuName;
    }

    /**
     * �����@�֖���ݒ�
     * @param shozokuName �����@�֖�
     */
    public void setShozokuName(String shozokuName) {
        this.shozokuName = shozokuName;
    }

    /**
     * ������ڃ��X�g���擾
     * @return List ������ڃ��X�g
     */
    public List getJigyoList() {
        return JigyoList;
    }

    /**
     * ������ڃ��X�g��ݒ�
     * @param jigyoList ������ڃ��X�g
     */
    public void setJigyoList(List jigyoList) {
        JigyoList = jigyoList;
    }

    /**
     * ���̈�ԍ����擾
     * @return String ���̈�ԍ�
     */
    public String getKariryoikiNo() {
        return kariryoikiNo;
    }

    /**
     * ���̈�ԍ���ݒ�
     * @param kariryoikiNo ���̈�ԍ�
     */
    public void setKariryoikiNo(String kariryoikiNo) {
        this.kariryoikiNo = kariryoikiNo;
    }
}