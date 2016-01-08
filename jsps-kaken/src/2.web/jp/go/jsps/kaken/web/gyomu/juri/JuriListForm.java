/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : IkkatsuJuriListForm.java
 *    Description : 受理登録対象応募情報一覧用フォーム
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/05/30
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/05/30    V1.0        DIS.dyh        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.juri;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * 受理登録対象応募情報一覧用フォーム
 * @author DIS.dyh
 */
public class JuriListForm extends BaseValidatorForm {
    
    BaseSearchForm base = new BaseSearchForm();

    /** システム受付番号リスト */
    //配列は１ページの最大表示件数分とる。
    private String[] systemNos = new String[base.getPageSize()];

    /** ラジオボタンのチェック判別用 */
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