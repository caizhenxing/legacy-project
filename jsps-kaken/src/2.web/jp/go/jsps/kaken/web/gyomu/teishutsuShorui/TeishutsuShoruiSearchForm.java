/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : TeishutsuShoruiSearchForm.java
 *    Description : 提出書類検索用フォーム
 *
 *    Author      : DIS.lwj
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.lwj        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

/**
 * 提出書類検索用フォーム
 * ID RCSfile="$RCSfile: TeishutsuShoruiSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class TeishutsuShoruiSearchForm extends BaseSearchForm{
    
    /** 事業ID */
    private String jigyoId;
    
    /** 所属コード */
    private String shozokuCd;
    
    /** 研究種目リスト */
    private List JigyoList = new ArrayList();
    
    /** 事業コード */
    private String jigyoCd;
    
    /** 受理状況 */
    private String juriJokyo;
    
    /** 所属機関名 */
    private String shozokuName;
    
    /** 事業区分 */
    private String jigyoKbn;
    
    /** 受理状況リスト */
    private List juriList = new ArrayList();

    /** 仮領域番号 */
    private String kariryoikiNo;

    /** コンストラクタ */
    public TeishutsuShoruiSearchForm(){
        jigyoId = "";
        shozokuCd = "";
        jigyoCd = "";
        juriJokyo = "0";
        shozokuName = "";
        kariryoikiNo = "";
    }

    /**
     * 事業コードを取得
     * @return String 事業コード
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * 事業コードを設定
     * @param jigyoCd 事業コード
     */
    public void setJigyoCd(String jigyoCd) {
        this.jigyoCd = jigyoCd;
    }

    /**
     * 事業IDを取得
     * @return String 事業ID
     */
    public String getJigyoId() {
        return jigyoId;
    }

    /**
     * 事業IDを設定
     * @param jigyoId 事業ID
     */
    public void setJigyoId(String jigyoId) {
        this.jigyoId = jigyoId;
    }

    /**
     * 事業区分を取得
     * @return String 事業区分
     */
    public String getJigyoKbn() {
        return jigyoKbn;
    }

    /**
     * 事業区分を設定
     * @param jigyoKbn 事業区分
     */
    public void setJigyoKbn(String jigyoKbn) {
        this.jigyoKbn = jigyoKbn;
    }

    /**
     * 受理状況を取得
     * @return String 受理状況
     */
    public String getJuriJokyo() {
        return juriJokyo;
    }

    /**
     * 受理状況を設定
     * @param juriJokyo 受理状況
     */
    public void setJuriJokyo(String juriJokyo) {
        this.juriJokyo = juriJokyo;
    }

    /**
     * 受理状況リストを取得
     * @return List 受理状況リスト
     */
    public List getJuriList() {
        return juriList;
    }

    /**
     * 受理状況リストを設定
     * @param juriList 受理状況リスト
     */
    public void setJuriList(List juriList) {
        this.juriList = juriList;
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
     * 所属機関名を取得
     * @return String 所属機関名
     */
    public String getShozokuName() {
        return shozokuName;
    }

    /**
     * 所属機関名を設定
     * @param shozokuName 所属機関名
     */
    public void setShozokuName(String shozokuName) {
        this.shozokuName = shozokuName;
    }

    /**
     * 研究種目リストを取得
     * @return List 研究種目リスト
     */
    public List getJigyoList() {
        return JigyoList;
    }

    /**
     * 研究種目リストを設定
     * @param jigyoList 研究種目リスト
     */
    public void setJigyoList(List jigyoList) {
        JigyoList = jigyoList;
    }

    /**
     * 仮領域番号を取得
     * @return String 仮領域番号
     */
    public String getKariryoikiNo() {
        return kariryoikiNo;
    }

    /**
     * 仮領域番号を設定
     * @param kariryoikiNo 仮領域番号
     */
    public void setKariryoikiNo(String kariryoikiNo) {
        this.kariryoikiNo = kariryoikiNo;
    }
}