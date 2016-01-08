/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : teisyutuForm.java
 *    Description : 仮領域番号発行、応募書類承認・却下用フォーム
 *
 *    Author      : DIS.liuYi
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.liuYi      新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

/**
 * 仮領域番号発行、応募書類承認・却下用フォーム
 * 
 * ID RCSfile="$RCSfile: TeisyutuForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:45 $"
 */
public class TeisyutuForm extends BaseSearchForm {
    //  ---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------

    /** システム受付番号 */
    private String ryoikiSystemNo;

    /** 仮領域番号 */
    private String kariryoikiNo;

    /** 事業コード */
    private String jigyoCd;
    
    /** 遷移画面フラグ */
    private String screenFlg;

    /**
     * コンストラクタ。
     */
    public TeisyutuForm() {
        super();
        init();
    }

    /* 
     * 初期化処理。
     * (非 Javadoc)
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        //・・・
    }

    /**
     * 初期化処理
     */
    public void init() {
        ryoikiSystemNo = "";
        screenFlg = "";
        jigyoCd   ="";
        kariryoikiNo ="";
    }

    /* 
     * 入力チェック。
     * (非 Javadoc)
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        //定型処理----- 
        ActionErrors errors = super.validate(mapping, request);
        //---------------------------------------------
        // 基本的なチェック(必須、形式等）はValidatorを使用する。
        //---------------------------------------------

        //基本入力チェックここまで
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