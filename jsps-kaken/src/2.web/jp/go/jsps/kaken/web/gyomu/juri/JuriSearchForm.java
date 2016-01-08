/*======================================================================
 *    SYSTEM      : 電子申請システム（科学研究費補助金）
 *    Source name : JuriSearchForm
 *    Description : 受理登録対象応募情報検索用フォーム
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
 * 受理登録対象応募情報検索用フォーム
 */
public class JuriSearchForm extends BaseSearchForm{

    /** VersionUID */
    private static final long serialVersionUID = -2147403507609272092L;

    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------
    /** 事業CD */
    private String jigyoCd;

    /** 研究種目名 */
    private String jigyoNm;

    /** 研究種目名リスト */
    private List jigyoNmList = new ArrayList();

    /** 所属コード */
    private String shozokuCd;

    /** 所属研究機関名 */
    private String shozokuNm;

    /**
     * 事業CDを取得
     * @return String 事業CD
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * 事業CDを設定
     * @param jigyoCd 事業CD
     */
    public void setJigyoCd(String jigyoCd) {
        this.jigyoCd = jigyoCd;
    }

    /**
     * 研究種目名を取得
     * @return String 研究種目名
     */
    public String getJigyoNm() {
        return jigyoNm;
    }

    /**
     * 研究種目名を設定
     * @param jigyoNm 研究種目名
     */
    public void setJigyoNm(String jigyoNm) {
        this.jigyoNm = jigyoNm;
    }

    /**
     * 研究種目名リストを取得
     * @return String 研究種目名リスト
     */
    public List getJigyoNmList() {
        return jigyoNmList;
    }

    /**
     * 研究種目名リストを設定
     * @param jigyoNmList 研究種目名リスト
     */
    public void setJigyoNmList(List jigyoNmList) {
        this.jigyoNmList = jigyoNmList;
    }

    /**
     * 所属コードを取得
     * @return String 所属コード
     */
    public String getShozokuCd() {
        return shozokuCd;
    }

    /**
     * 所属コードを設定
     * @param shozokuCd 所属コード
     */
    public void setShozokuCd(String shozokuCd) {
        this.shozokuCd = shozokuCd;
    }

    /**
     * 所属研究機関名を取得
     * @return String 所属研究機関名
     */
    public String getShozokuNm() {
        return shozokuNm;
    }

    /**
     * 所属研究機関名を設定
     * @param shozokuNm 所属研究機関名
     */
    public void setShozokuNm(String shozokuNm) {
        this.shozokuNm = shozokuNm;
    }
}