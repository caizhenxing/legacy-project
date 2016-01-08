/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : JuriGaivoForm.java
 *    Description : 提出書類管理（特定領域（新規））用フォーム
 *
 *    Author      : DIS.jzx
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.jzx        新規作成
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
 * 提出書類管理（特定領域（新規））用フォーム
 * ID RCSfile="$RCSfile: JuriGaiyoForm.java,v $" 
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class JuriGaiyoForm extends BaseValidatorForm {

    // ---------------------------------------------------------------------
    // Instance data
    // ---------------------------------------------------------------------

    /** システム受付番号 */
    private String systemNo;

    /** 受理結果 */
    private String juriKekka;

    /** 受理結果情報選択リスト */
    private List juriKekkaList = new ArrayList();

    /** 年度 */
    private String nendo;

    /** 回数 */
    private String kaisu;

    /** 事業名 */
    private String jigyoName;
    
    /** 所属コード */
    private String shozokuCd;  

    /** 整理番号 */
    private String uketukeNo;
    
    /** 整理番号 */
    private String ryoikiJokyoId;
    
    /** 事業Id */
    private String jigyoId;  
    
    /** 事業コード */
    private String jigyoCd;

    /** 状況Id */
    private String jokyoId;

    /** 受理結果リスト表示文字列 */
    private List juriFujuri = new ArrayList();

    /** 応募件数 */
    private String count;
    // ・・・・・・・・・・

    // ---------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------

    /**
     * コンストラクタ。
     */
    public JuriGaiyoForm() {
        super();
        init();
    }

    // ---------------------------------------------------------------------
    // Public methods
    // ---------------------------------------------------------------------

    /**
     * 初期化処理。 
     * 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        // ・・・
    }

    /**
     * 初期化処理
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
     * 入力チェック。 (非 Javadoc)
     * 
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        // 定型処理-----
        ActionErrors errors = super.validate(mapping, request);

        // ---------------------------------------------
        // 基本的なチェック(必須、形式等）はValidatorを使用する。
        // ---------------------------------------------

        // 基本入力チェックここまで
        if (!errors.isEmpty()) {
            return errors;
        }

        // 定型処理-----

        // 追加処理-----
        // ---------------------------------------------
        // 組み合わせチェック
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