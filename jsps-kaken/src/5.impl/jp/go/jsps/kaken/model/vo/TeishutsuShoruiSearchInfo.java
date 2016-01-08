/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : TeishutsuShoruiSearchInfo.java
 *    Description : ��o�m�F�i����̈挤���i�V�K�̈�j�j����������ێ�����N���X�B
 *
 *    Author      : �����R
 *    Date        : 2006/06/15
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.liWJ       �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * ��o�m�F�i����̈挤���i�V�K�̈�j�j����������ێ�����N���X�B
 * ID RCSfile="$RCSfile: TeishutsuShoruiSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class TeishutsuShoruiSearchInfo extends SearchInfo{

    /** �����R�[�h */
    private String shozokuCd;
    
    /** �󗝉����t���O */
    private String cancelFlag;
    
    /** �����@�֖� */
    private String shozokuName;
    
    /** ������ږ����X�g */
    private List JigyoList = new ArrayList();
    
    /** ���ƃR�[�h */
    private String jigyoCd;

    /** ������ږ� */
    private String JigyoName;
    
    /** �����p��ID */
    private String[] searchJokyoId;
    
    /** �����p��ID1 */
    private String[] searchJokyoId1;
    
    /** �����p��ID2 */
    private String[] searchJokyoId2;
    
    //2006/06/21 ����@��������
    /** �̈�v�揑�i�T�v�j�\����ID */
    private String[] ryoikiJokyoId;

    /** �폜�t���O */
    private String delFlg ;
    //2006/06/21 ����@�����܂�
    
    //2006/06/21 mcj�@��������
    /** �����敪 */
    private String jokyoKubun;
    //2006/06/21 mcj�@�����܂�
    //2006/08/01 ����@��������
    /** ����ID */
    private String[] jigyoId;
    //2006/08/01 ����@�����܂�
    

    /**
     * �󗝉����t���O���擾
     * @return String �󗝉����t���O
     */
    public String getCancelFlag() {
        return cancelFlag;
    }

    /**
     * �󗝉����t���O��ݒ�
     * @param cancelFlag �󗝉����t���O
     */
    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag;
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
     * ������ږ����X�g���擾
     * @return List ������ږ����X�g
     */
    public List getJigyoList() {
        return JigyoList;
    }

    /**
     * ������ږ����X�g��ݒ�
     * @param jigyoList ������ږ����X�g
     */
    public void setJigyoList(List jigyoList) {
        JigyoList = jigyoList;
    }

    /**
     * ���擾
     * @return String[] the searchJokyoId1.
     */
    public String[] getSearchJokyoId1() {
        return searchJokyoId1;
    }

    /**
     * ��ݒ�
     * @param searchJokyoId1 The searchJokyoId1 to set.
     */
    public void setSearchJokyoId1(String[] searchJokyoId1) {
        this.searchJokyoId1 = searchJokyoId1;
    }

    /**
     * ���擾
     * @return String[] the searchJokyoId2.
     */
    public String[] getSearchJokyoId2() {
        return searchJokyoId2;
    }

    /**
     * ��ݒ�
     * @param searchJokyoId2 The searchJokyoId2 to set.
     */
    public void setSearchJokyoId2(String[] searchJokyoId2) {
        this.searchJokyoId2 = searchJokyoId2;
    }

    /**
     * ���擾
     * @return String[] the searchJokyoId.
     */
    public String[] getSearchJokyoId() {
        return searchJokyoId;
    }

    /**
     * ��ݒ�
     * @param searchJokyoId The searchJokyoId to set.
     */
    public void setSearchJokyoId(String[] searchJokyoId) {
        this.searchJokyoId = searchJokyoId;
    }

    /**
     * ������ږ����擾
     * @return String ������ږ�
     */
    public String getJigyoName() {
        return JigyoName;
    }

    /**
     * ������ږ���ݒ�
     * @param jigyoName ������ږ�
     */
    public void setJigyoName(String jigyoName) {
        JigyoName = jigyoName;
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
    //  2006/06/21 ����@��������
    /**
     * ���擾
     * @return String[] the ryoikiJokyoId.
     */
    public String[] getRyoikiJokyoId() {
        return ryoikiJokyoId;
    }

    /**
     * ��ݒ�
     * @param ryoikiJokyoId The ryoikiJokyoId to set.
     */
    public void setRyoikiJokyoId(String[] ryoikiJokyoId) {
        this.ryoikiJokyoId = ryoikiJokyoId;
    }

    /**
     * �폜�t���O���擾
     * @return String �폜�t���O
     */
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * �폜�t���O��ݒ�
     * @param delFlg �폜�t���O
     */
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }
    //  2006/06/21 ����@�����܂�

    /**
     * �����敪���擾
     * @return String �����敪
     */
    public String getJokyoKubun() {
        return jokyoKubun;
    }

    /**
     * �����敪��ݒ�
     * @param jokyoKubun �����敪
     */
    public void setJokyoKubun(String jokyoKubun) {
        this.jokyoKubun = jokyoKubun;
    }

    /**
     * @return Returns the jigyoId.
     */
    public String[] getJigyoId() {
        return jigyoId;
    }

    /**
     * @param jigyoId The jigyoId to set.
     */
    public void setJigyoId(String[] jigyoId) {
        this.jigyoId = jigyoId;
    }
}