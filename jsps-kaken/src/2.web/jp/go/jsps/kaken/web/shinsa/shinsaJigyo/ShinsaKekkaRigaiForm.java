/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : 苗苗
 *    Date        : 2006/10/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/10/28    v1.0        苗苗                        新規
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.IShinsaKekkaMaintenance;
import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * 利害関係フォーム
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaRigaiForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:58 $"
 */
public class ShinsaKekkaRigaiForm extends BaseValidatorForm {
    
    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------

    /**
     * <code>serialVersionUID</code> のコメント
     */
    private static final long serialVersionUID = -3846050228857955697L;

	/** システム番号 */
    private String systemNo;
    
    /** コメント */
    private String comments;
    
    /** 利害関係 */
    private String rigai;
    
    /** 利害関係リスト */
    private List rigaiList = new ArrayList();

	//審査意見文字数チェックの為 2007/5/8
    /** 事業コード */
	private String jigyoCd;

	//---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------

    /**
     * コンストラクタ。
     */
    public ShinsaKekkaRigaiForm() {
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
        systemNo = "";         // システム番号
        comments = "";
        rigai = "";
        jigyoCd = "";
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
        
        if (IShinsaKekkaMaintenance.RIGAI_OFF.equals(rigai)){
            if(comments != null && comments.length() != 0 ){
                errors.add(ActionErrors.GLOBAL_ERROR,
                           new ActionError("errors.5070"));  
            }
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
     * システム番号を取得
     * @return システム番号
     */
    public String getSystemNo() {
        return systemNo;
    }
    
    /**
     * システム番号を設定
     * @param string システム番号
     */
    public void setSystemNo(String string) {
        systemNo = string;
    }

    /**
     * 利害関係リストを取得
     * @return 利害関係リスト
     */
    public List getRigaiList() {
        return rigaiList;
    }

    /**
     * 利害関係リストを設定
     * @param list 利害関係リスト
     */
    public void setRigaiList(List list) {
        rigaiList = list;
    }
    
    /**
     * コメントを取得
     * @return コメント
     */
    public String getComments() {
        return comments;
    }

    /**
     * コメントを設定
     * @param string コメント
     */
    public void setComments(String string) {
        comments = string;
    }

    /**
     * 利害関係を取得
     * @return 利害関係
     */
    public String getRigai() {
        return rigai;
    }

    /**
     * 利害関係を設定
     * @param string 利害関係
     */
    public void setRigai(String string) {
        rigai = string;
    }

    /**
     * 事業コードを取得
     * @return 事業コード
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * 事業コードを設定
     * @param string 事業コード
     */
    public void setJigyoCd(String string) {
        jigyoCd = string;
    }
}