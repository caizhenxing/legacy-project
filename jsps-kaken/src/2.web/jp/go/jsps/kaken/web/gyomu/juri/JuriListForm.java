/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : IkkatsuJuriListForm.java
 *    Description : �󗝓o�^�Ώۉ�����ꗗ�p�t�H�[��
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/05/30
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/05/30    V1.0        DIS.dyh        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.juri;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * �󗝓o�^�Ώۉ�����ꗗ�p�t�H�[��
 * @author DIS.dyh
 */
public class JuriListForm extends BaseValidatorForm {
    
    BaseSearchForm base = new BaseSearchForm();

    /** �V�X�e����t�ԍ����X�g */
    //�z��͂P�y�[�W�̍ő�\���������Ƃ�B
    private String[] systemNos = new String[base.getPageSize()];

    /** ���W�I�{�^���̃`�F�b�N���ʗp */
    private String selectRadioBn = new String();

    /**
     * @return Returns the systemNos.
     */
    public String[] getSystemNos() {
        return systemNos;
    }

    /**
     * @param systemNos The systemNos to set.
     */
    public void setSystemNos(String[] systemNos) {
        this.systemNos = systemNos;
    }

    /**
     * @return Returns the selectRadioBn.
     */
    public String getSelectRadioBn() {
        return selectRadioBn;
    }

    /**
     * @param selectRadioBn The selectRadioBn to set.
     */
    public void setSelectRadioBn(String selectRadioBn) {
        this.selectRadioBn = selectRadioBn;
    }
}