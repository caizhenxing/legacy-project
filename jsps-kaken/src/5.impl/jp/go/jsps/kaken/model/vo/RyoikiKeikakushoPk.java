/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : RyoikiKeikakushoPk.java
 *    Description : 領域計画書（概要）情報管理テーブル主キー
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.dyh        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

/**
 * 領域計画書（概要）情報管理テーブル主キー
 * ID RCSfile="$RCSfile: RyoikiKeikakushoPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class RyoikiKeikakushoPk extends ValueObject{

    //---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------
    private static final long serialVersionUID = -4155370705945945554L;

    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------

    /* システム受付番号 */
    private String ryoikiSystemNo;

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------
    /**
     * コンストラクタ。
     */
    public RyoikiKeikakushoPk() {
        super();
    }

    /**
     * コンストラクタ。
     * @param ryoikiSystemNo システム受付番号
     */
    public RyoikiKeikakushoPk(String ryoikiSystemNo){
        super();
        this.ryoikiSystemNo = ryoikiSystemNo;
    }
    
    //--------------------------------------------------------------------
    // Properties
    //---------------------------------------------------------------------

    /**
     * システム受付番号を取得
     * @return String システム受付番号
     */
    public String getRyoikiSystemNo() {
        return ryoikiSystemNo;
    }

    /**
     * システム受付番号を設定
     * @param ryoikiSystemNo システム受付番号
     */
    public void setRyoikiSystemNo(String ryoikiSystemNo) {
        this.ryoikiSystemNo = ryoikiSystemNo;
    }
}