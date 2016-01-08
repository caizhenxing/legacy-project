/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : TokuteiSearchForm.java
 *    Description : 特定領域研究検索フォーム
 *
 *    Author      : 苗苗
 *    Date        : 2006/06/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/21    v1.0        苗苗                        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

/**
 * 特定領域研究検索フォーム
 */
public class TokuteiSearchForm extends BaseSearchForm{

    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------
    
    /** 事業コード(複数件可、カンマ区切り) */
    private String jigyoCds;
    
    //・・・・・・・・・・

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------

    /**
     * コンストラクタ。
     */
    public TokuteiSearchForm() {
        super();
        init();
    }

    //---------------------------------------------------------------------
    // Public methods
    //---------------------------------------------------------------------

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
        jigyoCds = "";
    }

    /* 
     * 入力チェック。
     * (非 Javadoc)
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(
        ActionMapping mapping,
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

        //定型処理----- 

        //追加処理----- 

        //---------------------------------------------
        //組み合わせチェック 
        //---------------------------------------------
        return errors;
    }

    //---------------------------------------------------------------------
    // Properties
    //---------------------------------------------------------------------
    
    /**
     * 事業コードを取得
     * @return String 事業コード
     */
    public String getJigyoCds() {
        return jigyoCds;
    }

    /**
     * 事業コードを設定
     * @param jigyoCds 事業コード
     */
    public void setJigyoCds(String jigyoCds) {
        this.jigyoCds = jigyoCds;
    }
}