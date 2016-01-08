/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : MasterKanrenBunyaBunruiInfoDao.java
 *    Description : 関連分野15分類マスタマスタデータアクセスクラス
 *
 *    Author      : 苗苗
 *    Date        : 2006/06/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/21    v1.0        苗苗                        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MasterKanrenBunyaBunruiInfoDao {

/**
 * 関連分野15分類マスタマスタデータアクセスクラス。
 * 
 */
//  ---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------

    /** ログ */
    protected static final Log log = LogFactory.getLog(MasterKanrenBunyaBunruiInfoDao.class);

    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------

    /** 実行するユーザ情報 */
    private UserInfo userInfo = null;

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------
    
    /**
     * コンストラクタ。
     * @param userInfo      実行するユーザ情報
     */
    public MasterKanrenBunyaBunruiInfoDao(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    //---------------------------------------------------------------------
    // Public Methods
    //---------------------------------------------------------------------
    
    /**
     * 関連分野15分類マスタ情報の一覧を取得する。（プルダウン）
     * @param   connection          コネクション
     * @return                      関連分野15分類マスタ情報
     * @throws ApplicationException
     */
    public static List selectKanrenBunyaBunruiInfoList(Connection connection)
            throws ApplicationException {
        
        //-----------------------
        // SQL文の作成
        //-----------------------
        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" A.KANRENBUNYA_BUNRUI_NO KANRENBUNYA_BUNRUI_NO");       //関連分野15分類（番号）
        query.append(",A.KANRENBUNYA_BUNRUI_NAME KANRENBUNYA_BUNRUI_NAME");     //関連分野15分類（分類名）
        query.append(" FROM MASTER_KANRENBUNYABUNRUI A");
        query.append(" ORDER BY TO_NUMBER(A.KANRENBUNYA_BUNRUI_NO) ASC");                   
            

        if(log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }
        
        //-----------------------
        // リスト取得
        //-----------------------
        try {
            return SelectUtil.select(connection,query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException(
                "関連分野15分類マスタ情報検索中にDBエラーが発生しました。",
                new ErrorInfo("errors.4004"),
                e);
        } catch (NoDataFoundException e) {
            throw new SystemException(
                "関連分野15分類マスタに1件もデータがありません。",
                e);
        }
    }
}