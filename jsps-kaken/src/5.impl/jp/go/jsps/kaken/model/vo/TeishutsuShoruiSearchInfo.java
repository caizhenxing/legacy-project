/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : TeishutsuShoruiSearchInfo.java
 *    Description : 提出確認（特定領域研究（新規領域））検索条件を保持するクラス。
 *
 *    Author      : 李万軍
 *    Date        : 2006/06/15
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.liWJ       新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 提出確認（特定領域研究（新規領域））検索条件を保持するクラス。
 * ID RCSfile="$RCSfile: TeishutsuShoruiSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class TeishutsuShoruiSearchInfo extends SearchInfo{

    /** 所属コード */
    private String shozokuCd;
    
    /** 受理解除フラグ */
    private String cancelFlag;
    
    /** 所属機関名 */
    private String shozokuName;
    
    /** 研究種目名リスト */
    private List JigyoList = new ArrayList();
    
    /** 事業コード */
    private String jigyoCd;

    /** 研究種目名 */
    private String JigyoName;
    
    /** 検索用状況ID */
    private String[] searchJokyoId;
    
    /** 検索用状況ID1 */
    private String[] searchJokyoId1;
    
    /** 検索用状況ID2 */
    private String[] searchJokyoId2;
    
    //2006/06/21 張拓　ここから
    /** 領域計画書（概要）申請状況ID */
    private String[] ryoikiJokyoId;

    /** 削除フラグ */
    private String delFlg ;
    //2006/06/21 張拓　ここまで
    
    //2006/06/21 mcj　ここから
    /** 研究区分 */
    private String jokyoKubun;
    //2006/06/21 mcj　ここまで
    //2006/08/01 張拓　ここから
    /** 事業ID */
    private String[] jigyoId;
    //2006/08/01 張拓　ここまで
    

    /**
     * 受理解除フラグを取得
     * @return String 受理解除フラグ
     */
    public String getCancelFlag() {
        return cancelFlag;
    }

    /**
     * 受理解除フラグを設定
     * @param cancelFlag 受理解除フラグ
     */
    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag;
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
     * 研究種目名リストを取得
     * @return List 研究種目名リスト
     */
    public List getJigyoList() {
        return JigyoList;
    }

    /**
     * 研究種目名リストを設定
     * @param jigyoList 研究種目名リスト
     */
    public void setJigyoList(List jigyoList) {
        JigyoList = jigyoList;
    }

    /**
     * を取得
     * @return String[] the searchJokyoId1.
     */
    public String[] getSearchJokyoId1() {
        return searchJokyoId1;
    }

    /**
     * を設定
     * @param searchJokyoId1 The searchJokyoId1 to set.
     */
    public void setSearchJokyoId1(String[] searchJokyoId1) {
        this.searchJokyoId1 = searchJokyoId1;
    }

    /**
     * を取得
     * @return String[] the searchJokyoId2.
     */
    public String[] getSearchJokyoId2() {
        return searchJokyoId2;
    }

    /**
     * を設定
     * @param searchJokyoId2 The searchJokyoId2 to set.
     */
    public void setSearchJokyoId2(String[] searchJokyoId2) {
        this.searchJokyoId2 = searchJokyoId2;
    }

    /**
     * を取得
     * @return String[] the searchJokyoId.
     */
    public String[] getSearchJokyoId() {
        return searchJokyoId;
    }

    /**
     * を設定
     * @param searchJokyoId The searchJokyoId to set.
     */
    public void setSearchJokyoId(String[] searchJokyoId) {
        this.searchJokyoId = searchJokyoId;
    }

    /**
     * 研究種目名を取得
     * @return String 研究種目名
     */
    public String getJigyoName() {
        return JigyoName;
    }

    /**
     * 研究種目名を設定
     * @param jigyoName 研究種目名
     */
    public void setJigyoName(String jigyoName) {
        JigyoName = jigyoName;
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
    //  2006/06/21 張拓　ここから
    /**
     * を取得
     * @return String[] the ryoikiJokyoId.
     */
    public String[] getRyoikiJokyoId() {
        return ryoikiJokyoId;
    }

    /**
     * を設定
     * @param ryoikiJokyoId The ryoikiJokyoId to set.
     */
    public void setRyoikiJokyoId(String[] ryoikiJokyoId) {
        this.ryoikiJokyoId = ryoikiJokyoId;
    }

    /**
     * 削除フラグを取得
     * @return String 削除フラグ
     */
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * 削除フラグを設定
     * @param delFlg 削除フラグ
     */
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }
    //  2006/06/21 張拓　ここまで

    /**
     * 研究区分を取得
     * @return String 研究区分
     */
    public String getJokyoKubun() {
        return jokyoKubun;
    }

    /**
     * 研究区分を設定
     * @param jokyoKubun 研究区分
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