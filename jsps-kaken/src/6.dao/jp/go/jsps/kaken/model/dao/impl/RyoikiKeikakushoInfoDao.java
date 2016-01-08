/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : RyoikikeikakushoInfoDao.java
 *    Description : 領域計画書（概要）情報管理データアクセスクラス
 *
 *    Author      : DIS
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS            新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.select.BaseCallbackHandler;
import jp.go.jsps.kaken.model.dao.select.JDBCReading;
import jp.go.jsps.kaken.model.dao.select.PreparedStatementCreator;
import jp.go.jsps.kaken.model.dao.select.RowCallbackHandler;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.UserAuthorityException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 領域計画書（概要）情報管理データアクセスクラス。
 * ID RCSfile="$RCSfile: RyoikiKeikakushoInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class RyoikiKeikakushoInfoDao {

    /** ログ */
    protected static final Log log = LogFactory.getLog(RyoikiKeikakushoInfoDao.class);

    // ---------------------------------------------------------------------
    // Instance data
    // ---------------------------------------------------------------------
//  2006/06/20 by jzx add start
    /** DBリンク名 */
    private String dbLink = "";
    
    /** 実行するユーザ情報 */
    private UserInfo userInfo = null;

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------
    
    /**
     * コンストラクタ。
     * @param userInfo  実行するユーザ情報
     */
    public RyoikiKeikakushoInfoDao(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * コンストラクタ。
     * @param userInfo  実行するユーザ情報
     * @param dbLink   DBリンク名
     */
    public RyoikiKeikakushoInfoDao(UserInfo userInfo, String dbLink) {
        this.userInfo = userInfo;
        this.dbLink   = dbLink;
    }

    //---------------------------------------------------------------------
    // Public Methods
    //---------------------------------------------------------------------
    /**
     * 指定されたシステム受付番号の領域計画書（概要）情報を取得する。
     * 
     * @param  connection コネクション
     * @param  pkInfo キー情報
     * @return RyoikikeikakushoInfo 領域計画書（概要）情報
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @author DIS.jzx
     */
    public RyoikiKeikakushoInfo selectRyoikiKeikakushoInfo(
            Connection connection, RyoikiKeikakushoPk pkInfo)
            throws DataAccessException, NoDataFoundException {

        // システム受付番号が指定されていない場合はnullを返す
        if (pkInfo == null) {
            throw new NoDataFoundException(
                    "領域計画書（概要）情報管理テーブルに該当するデータが見つかりません。"
                    + "検索キー：領域計画書（概要）情報管理");
        }
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");
        query.append(" A.SHOZOKU_CD,");
        query.append(" A.SHOZOKU_NAME,");
        query.append(" A.KARIRYOIKI_NO,");
        query.append(" A.RYOIKI_NAME,");
        query.append(" A.BUKYOKU_NAME,");
        query.append(" A.SHOKUSHU_NAME_KANJI,");
        query.append(" A.NAME_KANA_SEI,");
        query.append(" A.NAME_KANA_MEI,");
        query.append(" A.EDITION,");
        query.append(" A.RYOIKI_JOKYO_ID,");
        query.append(" B.NENDO,");
        query.append(" B.KAISU,");
        query.append(" C.JIGYO_CD,");
        query.append(" C.JIGYO_NAME,");
        //add start ly 2006/06/15
        query.append(" A.NENDO AS RYOIKI_NENDO,");
        query.append(" A.KAISU AS RYOIKI_KAISU,");
        query.append(" A.SHINSEISHA_ID,");
        query.append(" A.KENKYU_NO,");
        query.append(" A.NAME_KANJI_SEI,");
        query.append(" A.NAME_KANJI_MEI,");
        query.append(" A.JIGYO_NAME");
        //add end ly 2006/06/15
//      query.append(" COUNT(*) COUNT ")
        query.append(" FROM RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" A");
        query.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" B");
        query.append(" ON A.JIGYO_ID = B.JIGYO_ID ");
        query.append(" INNER JOIN MASTER_JIGYO").append(dbLink).append(" C");
        query.append(" ON C.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) ");
// 2006/07/25 dyh update start
//        query.append(" WHERE A.RYOIKI_SYSTEM_NO ='");
//        query.append(EscapeUtil.toSqlString(pkInfo.getRyoikiSystemNo()));
//        query.append("'");
        query.append(" WHERE A.RYOIKI_SYSTEM_NO = ?");
// 2006/07/25 dyh update end
        
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        // -----------------------
        // リスト取得
        // -----------------------
        PreparedStatement ps = null;
        ResultSet resutlt = null;

        try {
            ps = connection.prepareStatement(query.toString());
// 2006/07/25 dyh add start
            int i = 1;
            DatabaseUtil.setParameter(ps, i++, pkInfo.getRyoikiSystemNo());
// 2006/07/25 dyh add end
            resutlt = ps.executeQuery();
            RyoikiKeikakushoInfo info = new RyoikiKeikakushoInfo();
            if (resutlt.next()) {
                info.setShozokuCd(resutlt.getString("SHOZOKU_CD"));
                info.setShozokuName(resutlt.getString("SHOZOKU_NAME"));
                info.setKariryoikiNo(resutlt.getString("KARIRYOIKI_NO"));
                info.setRyoikiName(resutlt.getString("RYOIKI_NAME"));
                info.setBukyokuName(resutlt.getString("BUKYOKU_NAME"));
                info.setShokushuNameKanji(resutlt.getString("SHOKUSHU_NAME_KANJI"));
                info.setNameKanaSei(resutlt.getString("NAME_KANA_SEI"));
                info.setNameKanaMei(resutlt.getString("NAME_KANA_MEI"));
                info.setEdition(resutlt.getString("EDITION"));
                info.setRyoikiJokyoId(resutlt.getString("RYOIKI_JOKYO_ID"));
                info.setNendo(resutlt.getString("NENDO"));
                info.setKaisu(resutlt.getString("KAISU"));
                info.setJigyoName(resutlt.getString("JIGYO_NAME"));
                //add start ly 2006/06/15
                info.setShinseishaId(resutlt.getString("SHINSEISHA_ID"));
                info.setKenkyuNo(resutlt.getString("KENKYU_NO"));
                info.setNameKanjiSei(resutlt.getString("NAME_KANJI_SEI"));
                info.setNameKanjiMei(resutlt.getString("NAME_KANJI_MEI"));
                info.setRyoikiSystemNo(resutlt.getString("RYOIKI_SYSTEM_NO"));
                //add end ly 2006/06/15
            }
            else {
                throw new NoDataFoundException(
                        "領域計画書（概要）情報管理テーブルに該当するデータが見つかりません。"
                        + "検索キー：領域計画書（概要）情報管理"
                        + pkInfo.getRyoikiSystemNo());
            }
            return info;
        }catch (SQLException ex) {
            throw new DataAccessException("領域計画書（概要）情報管理テーブル検索実行中に例外が発生しました。 ", ex);
        }
        finally {
            DatabaseUtil.closeResource(resutlt, ps);
        }
    }

    /**
     * 受理登録画面形式チェックを更新する。
     * @param  connection コネクション
     * @param  pkInfo キー情報
     * @throws DataAccessException 更新中に例外が発生した場合
     * @throws NoDataFoundException
     * @author DIS.jzx
     */
    public void checkKenkyuNoInfo(Connection connection,
            RyoikiKeikakushoPk pkInfo) throws DataAccessException,
            NoDataFoundException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" D.KENKYU_NO");
        query.append(" FROM RYOIKIKEIKAKUSHOINFO ").append(dbLink).append(" A");
        query.append(" INNER JOIN SHINSEIDATAKANRI").append(dbLink).append(" B"); 
        query.append(" ON A.KARIRYOIKI_NO = B.RYOIKI_NO ");
        query.append(" INNER JOIN KENKYUSOSHIKIKANRI").append(dbLink).append(" C");
        query.append(" ON B.SYSTEM_NO = C.SYSTEM_NO ");
        query.append(" INNER JOIN MASTER_KENKYUSHA").append(dbLink).append(" D");
        query.append(" ON C.KENKYU_NO = D.KENKYU_NO ");
// 2006/07/25 dyh update start
//        query.append(" WHERE A.RYOIKI_SYSTEM_NO ='");
//        query.append(EscapeUtil.toSqlString(pkInfo.getRyoikiSystemNo()));
//        query.append("'");
        query.append(" WHERE A.RYOIKI_SYSTEM_NO = ? ");
// 2006/07/25 dyh update end
        query.append(" AND A.DEL_FLG = 0");
        query.append(" AND B.DEL_FLG = 0");
        query.append(" AND D.DEL_FLG = 0");
        query.append( "GROUP BY D.KENKYU_NO");
        
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
      try{
          preparedStatement = connection.prepareStatement(query.toString());
// 2006/07/25 dyh add start
          int i = 1;
          DatabaseUtil.setParameter(preparedStatement, i++, pkInfo.getRyoikiSystemNo());
// 2006/07/25 dyh add end
          recordSet = preparedStatement.executeQuery();
          if(!(recordSet.next())){
              throw new NoDataFoundException("該当研究者マスタに以下の研究者が存在しません",
                      new ErrorInfo("errors.5064"));
          }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//  2006/06/21 jzx add end
    
// 2006/06/21 dyh add start
    /**
     * 指定された申請者IDの領域計画書（概要）情報管理データを取得する。
     * 
     * @param  connection コネクション
     * @param  shinseishaId 申請者ID
     * @return RyoikikeikakushoInfo
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List selectRyoikiKeikakushoInfo(
            Connection connection,String shinseishaId) 
            throws DataAccessException, NoDataFoundException {

        // 申請者ID
        StringBuffer addQuery = new StringBuffer();
        if (!StringUtil.isBlank(shinseishaId)) {
            addQuery.append(" AND ");
            addQuery.append(" R.SHINSEISHA_ID = '");
            addQuery.append(EscapeUtil.toSqlString(shinseishaId));
            addQuery.append("' ");
        }
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");

        query.append(" J.NENDO,");                  // 年度
        query.append(" J.KAISU,");                  // 回数
        query.append(" J.JIGYO_NAME,");             // 研究種目名
        query.append(" R.RYOIKI_SYSTEM_NO, ");      // システム受付番号
        query.append(" R.KARIRYOIKI_NO,");          // 仮領域番号
        query.append(" R.RYOIKI_NAME,");            // 応募領域名
        query.append(" R.JIGYO_ID,");               // 事業ID
        query.append(" R.NAME_KANJI_SEI,");         // 領域代表者氏名（漢字等-姓）
        query.append(" R.NAME_KANJI_MEI,");         // 領域代表者氏名（漢字等-名）
        query.append(" J.UKETUKEKIKAN_END,");       // 学振受付期間（終了）
        query.append(" CASE WHEN TRUNC(sysdate, 'DD') < J.UKETUKEKIKAN_START ");
        query.append("           THEN 'FALSE' ");
        query.append("      WHEN TRUNC(sysdate, 'DD') > J.UKETUKEKIKAN_END ");
        query.append("           THEN 'FALSE' ");
        query.append("      ELSE 'TRUE' ");
        query.append(" END UKETUKE_END_FLAG,");     // 学振受付期間フラグ
        query.append(" J.RYOIKI_KAKUTEIKIKAN_END,");// 領域代表者確定締切日
        query.append(" CASE WHEN TRUNC(sysdate, 'DD') > J.RYOIKI_KAKUTEIKIKAN_END ");
        query.append("           THEN 'OVER' ");
        query.append("      ELSE 'UNDER' ");
        query.append(" END RYOIKI_END_FLAG,");      // 領域代表者確定締切日以降フラグ
        query.append(" R.SAKUSEI_DATE,");           // 領域計画書概要作成日
        query.append(" R.SHONIN_DATE,");            // 所属機関承認日
        query.append(" R.RYOIKI_JOKYO_ID,");        // 領域計画書（概要）申請状況ID
        query.append(" DECODE(R.RYOIKIKEIKAKUSHO_KAKUTEI_FLG,");
        query.append("        NULL,0,R.RYOIKIKEIKAKUSHO_KAKUTEI_FLG)");
        query.append("        RYOIKIKEIKAKUSHO_KAKUTEI_FLG,");// 確定フラグ
        query.append(" M.RYOIKIDAIHYOU_HYOJI, "); // 領域計画書（概要）申請状況ID
        query.append(" M.JOKYO_NAME "); // 領域計画書（概要）申請状況名称
  
        query.append(" FROM ");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");// 領域計画書（概要）情報管理テーブル
        query.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" J ");// 事業情報管理テーブル
        query.append(" ON R.JIGYO_ID = J.JIGYO_ID ");
        query.append(" INNER JOIN MASTER_RYOIKI_STATUS");// 領域計画書概要状況マスタテーブル
        query.append(dbLink).append(" M ");
        query.append(" ON R.RYOIKI_JOKYO_ID = M.JOKYO_ID ");
        query.append(" AND R.CANCEL_FLG = M.KAIJYO_FLG ");

        query.append(" WHERE ");
        query.append(" R.DEL_FLG = 0 ");
        query.append(addQuery);
        query.append(" ORDER BY R.JIGYO_ID ASC,R.KARIRYOIKI_NO ASC");
        
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        // -----------------------
        // リスト取得
        // -----------------------
        List resultList = SelectUtil.select(connection,query.toString());
        return resultList;
    }
// 2006/06/21 dyh add end

//  2006/06/28 mcj add start
    /**
     * 指定されたシステム受付番号の領域計画書（概要）情報管理データを取得する。
     * 
     * @param  connection
     * @param  ryoikiSystemNo
     * @return resultList
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public RyoikiKeikakushoInfo selectInfo(
            Connection connection,
            String ryoikiSystemNo) 
            throws DataAccessException, NoDataFoundException {

        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" J.NENDO,");           // 年度
        select.append(" J.KAISU,");           // 回数
        select.append(" J.JIGYO_NAME,");      // 研究種目名
        select.append(" R.RYOIKI_SYSTEM_NO,");// システム受付番号
        select.append(" R.KARIRYOIKI_NO,");   // 仮領域番号
        select.append(" R.RYOIKI_NAME,");     // 応募領域名
        select.append(" R.NAME_KANJI_SEI,");  // 領域代表者氏名（漢字等-姓）
        select.append(" R.NAME_KANJI_MEI,");  // 領域代表者氏名（漢字等-名）
        select.append(" J.UKETUKEKIKAN_END,");// 学振受付期間（終了）
        select.append(" R.SAKUSEI_DATE,");    // 領域計画書概要作成日
        select.append(" R.SHONIN_DATE,");     // 所属機関承認日
        select.append(" R.RYOIKI_JOKYO_ID,"); // 領域計画書（概要）申請状況
//        select.append(" M.RYOIKIDAIHYOU_HYOJI,");
        select.append(" R.CANCEL_FLG ");
        select.append(" FROM ");
        select.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");// 領域計画書（概要）情報管理テーブル
        select.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" J ");// 事業情報管理テーブル
        select.append(" ON R.JIGYO_ID = J.JIGYO_ID ");
//        select.append(" INNER JOIN MASTER_RYOIKI_STATUS").append(dbLink).append(" M ");// 事業情報管理テーブル
//        select.append(" ON R.RYOIKI_JOKYO_ID = M.JOKYO_ID ");
//        select.append(" AND R.CANCEL_FLG = M.KAIJYO_FLG ");
        select.append(" AND ");
// 2006/07/25 dyh update start
//        select.append(" R.RYOIKI_SYSTEM_NO = '");
//        select.append(ryoikiSystemNo);            
//        select.append("' ");
        select.append(" R.RYOIKI_SYSTEM_NO = ?");
// 2006/07/25 dyh update end
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + select.toString());
        }
        // -----------------------
        // リスト取得
        // -----------------------
        PreparedStatement preparedStatement = null;
        ResultSet resutlt = null;
        try {
            preparedStatement = connection.prepareStatement(select.toString());
// 2006/07/25 dyh add start
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, ryoikiSystemNo);
// 2006/07/25 dyh add end
            resutlt = preparedStatement.executeQuery();
            RyoikiKeikakushoInfo info = new RyoikiKeikakushoInfo();
            if (resutlt.next()) {
                info.setRyoikiSystemNo(resutlt.getString("RYOIKI_SYSTEM_NO"));
                info.setNendo(resutlt.getString("NENDO"));
                info.setKaisu(resutlt.getString("KAISU"));
                info.setJigyoName(resutlt.getString("JIGYO_NAME"));
                info.setRyoikiSystemNo(resutlt.getString("RYOIKI_SYSTEM_NO"));// システム受付番号
                info.setKariryoikiNo(resutlt.getString("KARIRYOIKI_NO"));                
                info.setRyoikiName(resutlt.getString("RYOIKI_NAME"));
                info.setNameKanjiSei(resutlt.getString("NAME_KANJI_SEI"));
                info.setNameKanjiMei(resutlt.getString("NAME_KANJI_MEI"));
                info.setUketukeNo(resutlt.getString("UKETUKEKIKAN_END"));
                info.setSakuseiDate(resutlt.getDate("SAKUSEI_DATE"));
                info.setShoninDate(resutlt.getDate("SHONIN_DATE"));                
                info.setRyoikiJokyoId(resutlt.getString("RYOIKI_JOKYO_ID"));               
                info.setCancelFlg(resutlt.getString("CANCEL_FLG"));
            }
            else {
                throw new NoDataFoundException(
                        "領域計画書（概要）情報管理テーブルに該当するデータが見つかりません。検索キー：領域計画書（概要）情報管理"
                                + info.getRyoikiSystemNo() + "'");
            }
            return info;
        }
        catch (SQLException ex) {
            throw new DataAccessException("領域計画書（概要）情報管理テーブル検索実行中に例外が発生しました。 ", ex);
        }
        finally {
            DatabaseUtil.closeResource(resutlt, preparedStatement);
        }        
    }

    /**
     * 指定されたシステム受付番号の領域計画書（概要）情報管理データを取得する。
     * 
     * @param  connection
     * @param  ryoikikeikakushoInfo
     * @return resultList
     * @throws DataAccessException
     * @throws DuplicateKeyException
     */
    public void existCount(
            Connection connection,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo) 
            throws DataAccessException, DuplicateKeyException{

        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" A.RYOIKI_NO ");
        select.append("FROM");
        select.append(" SHINSEIDATAKANRI A,");
        select.append(" RYOIKIKEIKAKUSHOINFO B ");
        select.append("WHERE");
        select.append(" A.RYOIKI_NO = B.KARIRYOIKI_NO");
        select.append(" AND");
        select.append(" A.JOKYO_ID IN (04,06,07,08,09,10,11,12,21,23,24)");
        select.append(" AND A.DEL_FLG = 0");
        select.append(" AND B.DEL_FLG = 0");
// 2006/07/26 dyh update start
//        select.append(" AND B.RYOIKI_SYSTEM_NO = '");
//        select.append(ryoikikeikakushoInfo.getRyoikiSystemNo());
//        select.append("' ");
        select.append(" AND B.RYOIKI_SYSTEM_NO = ?");
// 2006/07/26 dyh update end
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + select.toString());
        }
        // -----------------------
        // リスト取得
        // -----------------------
        PreparedStatement preparedStatement = null;
        ResultSet resutlt = null;
        try {
            preparedStatement = connection.prepareStatement(select.toString());
// 2006/07/26 dyh add start
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, ryoikikeikakushoInfo.getRyoikiSystemNo());
// 2006/07/26 dyh add start
            resutlt = preparedStatement.executeQuery();
            if (resutlt.next()) {
                throw new DuplicateKeyException("確認中の研究計画調書があります。全ての研究計画調書を却下してから、削除してください。");
            }           
        }
        catch (SQLException ex) {
            throw new DataAccessException("領域計画書（概要）情報管理テーブル検索実行中に例外が発生しました。 ", ex);
        }
        finally {
            DatabaseUtil.closeResource(resutlt, preparedStatement);
        }        
    }

//  2006/06/15 宮　ここから
    /**
     * 指定された条件の提出確認（特定領域研究（新規領域）)情報を取得する。
     * 
     * @param connection コネクション
     * @param ryoikiInfo
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List searchRyoikiInfoList(Connection connection,
            RyoikiKeikakushoInfo ryoikiInfo) throws DataAccessException,
            NoDataFoundException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");   // システム受付番号
        query.append(" A.KARIRYOIKI_NO,");      // 仮領域番号
        query.append(" A.UKETUKE_NO,");         // 受付番号（整理番号）
        query.append(" A.JIGYO_ID,");           // 事業ID
        query.append(" A.NENDO,");              // 年度
        query.append(" A.KAISU,");              // 回数
        query.append(" A.JIGYO_NAME,");         // 事業名
        query.append(" A.SHINSEISHA_ID,");      // 申請者ID
        query.append(" A.RYOIKI_NAME_RYAKU,");  // 領域略称名
        query.append(" A.BUKYOKU_NAME_RYAKU,"); // 部局名（略称）
        query.append(" A.SHOKUSHU_NAME_RYAKU,");// 職名（略称）
        query.append(" A.SHOZOKU_CD,");         // 所属機関コード
        query.append(" A.NAME_KANJI_SEI,");     // 事務担当者氏名
        query.append(" A.NAME_KANJI_MEI,");     // 事務担当者氏名
        query.append(" A.EDITION,");            // 版
        query.append(" A.PDF_PATH,");           // 領域計画書概要PDFの格納パス
        query.append(" A.SAKUSEI_DATE,");       // 領域計画書概要作成日
        query.append(" A.SHONIN_DATE,");        // 所属機関承認日
        query.append(" A.RYOIKI_JOKYO_ID,");    // 領域計画書（概要）申請状況ID
        query.append(" A.KENKYU_NO,");          // 申請者研究者番号
        query.append(" B.UKETUKEKIKAN_END,");   // 学振受付期間（終了）
        //2006/07/19 ADD START
        query.append(" B.KARIRYOIKINO_UKETUKEKIKAN_END,");// 仮領域番号受付期間（終了）
        //2006/07/19 ADD END
        query.append(" A.CANCEL_FLG,");         // 解除フラグ

        // UPDADE START LY 2006/06/21
        query.append(" (SELECT");
        query.append("   COUNT(*) ");
        query.append("  FROM");
        query.append("   SHINSEIDATAKANRI C");
        query.append("  WHERE");
        query.append("   A.KARIRYOIKI_NO = C.RYOIKI_NO ");
        query.append("   AND C.DEL_FLG = 0 ");
        query.append("   AND A.DEL_FLG = 0 ");

        // 申請状況
        if (!StringUtil.isBlank(ryoikiInfo.getJokyoIds())) {
            query.append(" AND C.JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(ryoikiInfo.getJokyoIds(), true));
            query.append(")");
        }
        query.append(" ) AS COUNT ");

        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" A");
        query.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" B");
        query.append(" ON A.JIGYO_ID = B.JIGYO_ID ");
        query.append("WHERE");
        query.append(" A.DEL_FLG = 0 ");
        // UPDATE LY END 2006/06/21

        // 所属機関コード
        if (!StringUtil.isBlank(ryoikiInfo.getShozokuCd())) {
            query.append(" AND A.SHOZOKU_CD = '");
            query.append(EscapeUtil.toSqlString(ryoikiInfo.getShozokuCd()));
            query.append("'");
        }
        // 事業コード
        if (!StringUtil.isBlank(ryoikiInfo.getJigyoCd())) {
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '");
            query.append(EscapeUtil.toSqlString(ryoikiInfo.getJigyoCd()));
            query.append("'");
        }

        // ソート順:事業ＩＤの昇順、仮領域番号の昇順
        query.append(" ORDER BY A.JIGYO_ID ASC,A.KARIRYOIKI_NO ASC");

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        return SelectUtil.select(connection, query.toString());
    }
    // 2006/06/15 宮 ここまで
      
//    /**
//     * 申請検索条件オブジェクトから検索条件を取得しSQLの問い合わせ部分を生成する。
//     * 生成した問い合わせ部分は第一引数の文字列の後ろに結合される。
//     * @param select      
//     * @param searchInfo
//     * @return
//     */
//    protected static String getQueryString(String select,
//            RyoikiKeikakushoInfo searchInfo) {
//
//        //-----検索条件オブジェクトの内容をSQLに結合していく-----
//        StringBuffer query = new StringBuffer(select);
//
//        if (!StringUtil.isBlank(searchInfo.getRyoikiSystemNo())) {
//            query.append(" AND A.RYOIKI_SYSTEM_NO = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getRyoikiSystemNo()))
//                    .append("'");
//        }
//        //申請番号
//        if (!StringUtil.isBlank(searchInfo.getUketukeNo())) {
//            query.append(" AND A.UKETUKE_NO = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getUketukeNo())).append(
//                    "'");
//        }
//        //事業ID
//        if (!StringUtil.isBlank(searchInfo.getJigyoId())) {
//            query.append(" AND A.JIGYO_ID = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getJigyoId()))
//                    .append("'");
//        }
//        //事業CD（事業IDの3文字目から5文字分）
//        if (!StringUtil.isBlank(searchInfo.getJigyoCd())) {
//            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getJigyoCd()))
//                    .append("'");
//        }
//        //      事業名
//        if (!StringUtil.isBlank(searchInfo.getJigyoName())) {
//            query.append(" AND A.JIGYO_NAME = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getJigyoName())).append(
//                    "'");
//        }
//        //年度
//        if (!StringUtil.isBlank(searchInfo.getNendo())) {
//            query.append(" AND A.NENDO = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getNendo())).append("'");
//        }
//        //回数
//        if (!StringUtil.isBlank(searchInfo.getKaisu())) {
//            query.append(" AND A.KAISU = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getKaisu())).append("'");
//        }
////        // 事業区分
////        if (searchInfo.getJigyoKubun() != null
////                && searchInfo.getJigyoKubun().size() != 0) {
////            query.append(" AND A.JIGYO_KUBUN IN (").append(
////                    changeIterator2CSV(searchInfo.getJigyoKubun().iterator()))
////                    .append(")");
////        }
//        // 所属機関コード
//        if (!StringUtil.isBlank(searchInfo.getShozokuCd())) {
//            query.append(" AND A.SHOZOKU_CD = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getShozokuCd())).append(
//                    "'");
//        }
//        //所属研究機関名(名称)
//        if (!StringUtil.isBlank(searchInfo.getShozokuName())) {
//            query.append("AND A.SHOZOKU_NAME_RYAKU like '%").append(
//                    EscapeUtil.toSqlString(searchInfo.getShozokuName()))
//                    .append("%' ");
//        }
//        //申請状況
//        if (searchInfo.getJokyoIds() != null
//                && searchInfo.getJokyoIds().length != 0) {
//            query.append(" AND C.JOKYO_ID IN (").append(
//                    StringUtil.changeArray2CSV(searchInfo.getJokyoIds(), true))
//                    .append(")");
//        }
//        //領域計画書（概要）申請状況ID
//        if (searchInfo.getRyoikiJokyoIds() != null
//                && searchInfo.getRyoikiJokyoIds().length != 0) {
//            query.append(" AND A.RYOIKI_JOKYO_ID IN (").append(
//                    StringUtil.changeArray2CSV(searchInfo.getJokyoIds(), true))
//                    .append(")");
//        }
//        query.append(" ORDER BY A.JIGYO_ID ASC,KARIRYOIKI_NO ASC");
//        return query.toString();
//    }
    
//  2006/06/15 lwj ここから */
//    /**
//     * 指定された事業コードの提出書類一覧（特定領域研究（新規領域）)情報を取得する。
//     * @param connection コネクション
//     * @param searchInfo
//     * @return List
//     * @throws DataAccessException
//     * @throws NoDataFoundException
//     */
//    public List selectAllTeishutuShorui(Connection connection,
//            TeishutsuShoruiSearchInfo searchInfo)
//            throws DataAccessException, NoDataFoundException {
//
//        String select = "SELECT " 
//                + " A.RYOIKI_SYSTEM_NO,"                   // システム受付番号
//                + " A.KARIRYOIKI_NO,"                      // 仮領域番号
//                + " A.UKETUKE_NO,"                         // 受付番号（整理番号）
//                + " A.JIGYO_ID,"                           // 事業ID
//                + " A.CANCEL_FLG,"                         // フラグ
//                + " B.NENDO,"                              // 年度
//                + " B.KAISU,"                              // 回数
//                + " D.JIGYO_CD,"                           // 事業コード
//                + " D.JIGYO_NAME,"                         // 事業名
//                + " A.SHINSEISHA_ID,"                      // 申請者ID
//                + " A.DEL_FLG,"                            // 削除フラグ
//                + " A.RYOIKI_NAME_RYAKU,"                  // 領域略称名
//                + " A.BUKYOKU_NAME_RYAKU,"                 // 部局名（略称）
//                + " A.SHOKUSHU_NAME_RYAKU,"                // 職名（略称）
//                + " A.SHOZOKU_CD,"                         // 所属機関コード
//                + " A.SHOZOKU_NAME,"                       // 所属機関名
//                + " A.NAME_KANJI_SEI,"                     // 事務担当者氏名（フリガナ-姓）
//                + " A.NAME_KANJI_MEI,"                     // 事務担当者氏名（フリガナ-名）
//                + " CASE WHEN TRUNC(sysdate, 'DD') < B.UKETUKEKIKAN_START "
//                + "            THEN 'FALSE' "
//                + "      WHEN TRUNC(sysdate, 'DD') > B.UKETUKEKIKAN_END "
//                + "            THEN 'FALSE' "
//                + "      ELSE 'TRUE' "
//                + " END UKETUKE_END_FLAG,"                 // 学振受付期間フラグ
//                + " A.EDITION,"                            // 版
//                + " A.PDF_PATH,"                           // 領域計画書概要PDFの格納パス
//                + " A.SAKUSEI_DATE,"                       // 領域計画書概要作成日
//                + " A.JYURI_DATE,"                         // 受理日
//                + " A.RYOIKI_JOKYO_ID,"                    // 領域計画書（概要）申請状況ID
//                + " A.KENKYU_NO,"                          // 申請者研究者番号
//                + " SYSDATE,"
//                + " (SELECT COUNT(*) FROM SHINSEIDATAKANRI C"
//                + " WHERE A.KARIRYOIKI_NO = C.RYOIKI_NO " + " AND C.DEL_FLG = 0 ";
//        
//        if(searchInfo.getDelFlg().equals("0")){
//            select = select + " AND A.DEL_FLG = " + searchInfo.getDelFlg() + "";
//        }
//        //申請状況
//        if (searchInfo.getSearchJokyoId() != null && searchInfo.getSearchJokyoId().length != 0) {
//            select = select + " AND C.JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId(), true) + ")";
//        }
//        select = select
//                + " )AS COUNT FROM RYOIKIKEIKAKUSHOINFO "
//                + dbLink
//                + " A"
//                + " INNER JOIN JIGYOKANRI"
//                + dbLink
//                + " B"
//                + " ON A.JIGYO_ID = B.JIGYO_ID "
//                + " INNER JOIN MASTER_JIGYO"
//                + dbLink
//                + " D"
//                + " ON D.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) "
//                + " WHERE 2>1";
//
//        if (searchInfo.getDelFlg().equals("0")) {
//            select = select + " AND A.DEL_FLG = " + searchInfo.getDelFlg() + "";
//        }
//        if (!StringUtil.isBlank(searchInfo.getJigyoCd())) {
//            select = select + " AND D.JIGYO_CD = '"
//                    + EscapeUtil.toSqlString(searchInfo.getJigyoCd()) + "'";
//        }
//        if (!StringUtil.isBlank(searchInfo.getShozokuCd())) {
//            select = select + " AND A.SHOZOKU_CD = '"
//                    + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'";
//        }
//        if (!StringUtil.isBlank(searchInfo.getShozokuName())) {
//            select = select + " AND A.SHOZOKU_NAME like '%"
//                    + EscapeUtil.toSqlString(searchInfo.getShozokuName())
//                    + "%' ";
//        }
//        if ((searchInfo.getSearchJokyoId1() != null
//                && searchInfo.getSearchJokyoId1().length != 0)
//                && (searchInfo.getSearchJokyoId2() == null
//                        || searchInfo.getSearchJokyoId2().length == 0)) {
//            select = select + " AND A.RYOIKI_JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId1(), true) + ")";
//        }
//        if ((searchInfo.getSearchJokyoId2() != null
//                && searchInfo.getSearchJokyoId2().length != 0)
//                && (searchInfo.getSearchJokyoId1() == null
//                        || searchInfo.getSearchJokyoId1().length == 0)) {
//            select = select + " AND (A.RYOIKI_JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId2(), true) + ")"
//                    + " AND A.CANCEL_FLG =1 )";
//        }
//        if ((searchInfo.getSearchJokyoId1() != null
//                && searchInfo.getSearchJokyoId1().length != 0)
//                && !(searchInfo.getSearchJokyoId2() == null
//                        || searchInfo.getSearchJokyoId2().length == 0)) {
//            select = select + " AND (A.RYOIKI_JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId1(), true) + ")"
//                    + " OR (A.RYOIKI_JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId2(), true)
//                    + ") AND A.CANCEL_FLG =1 ))";
//        }
//        if((searchInfo.getRyoikiJokyoId() != null
//                && searchInfo.getRyoikiJokyoId().length != 0)
//                && searchInfo.getDelFlg().equals("0")) {
//            select = select
//                    + "AND A.RYOIKI_JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getRyoikiJokyoId(),
//                            true) + ")";
//        }
//        if((searchInfo.getRyoikiJokyoId() != null
//                && searchInfo.getRyoikiJokyoId().length != 0)
//                && searchInfo.getDelFlg().equals("1")) {
//            select = select
//                    + "AND (A.RYOIKI_JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getRyoikiJokyoId(),
//                            true)
//                    + ") OR"
//                    + " A.DEL_FLG = "
//                    + searchInfo.getDelFlg()
//                    + ") AND A.UKETUKE_NO IS NOT NULL ORDER BY A.JIGYO_ID ASC,A.UKETUKE_NO ASC";
//        } else {
//            select = select + " ORDER BY A.SHOZOKU_CD ASC,A.UKETUKE_NO ASC";
//        }
//
//        // for debug
//        if (log.isDebugEnabled()) {
//            log.debug("query:" + select);
//        }
//        
//        return SelectUtil.select(connection, select);
//    }
    /**
     * 指定された事業コードの提出書類一覧（特定領域研究（新規領域）)情報を取得する。
     * @param connection コネクション
     * @param searchInfo
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List selectAllTeishutuShorui(Connection connection,
            TeishutsuShoruiSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");                   // システム受付番号
        query.append(" A.KARIRYOIKI_NO,");                      // 仮領域番号
        query.append(" A.UKETUKE_NO,");                         // 受付番号（整理番号）
        query.append(" A.JIGYO_ID,");                           // 事業ID
        query.append(" A.CANCEL_FLG,");                         // フラグ
        query.append(" B.NENDO,");                              // 年度
        query.append(" B.KAISU,");                              // 回数
        query.append(" D.JIGYO_CD,");                           // 事業コード
        query.append(" D.JIGYO_NAME,");                         // 事業名
        query.append(" A.SHINSEISHA_ID,");                      // 申請者ID
        query.append(" A.DEL_FLG,");                            // 削除フラグ
        query.append(" A.RYOIKI_NAME_RYAKU,");                  // 領域略称名
        query.append(" A.BUKYOKU_NAME_RYAKU,");                 // 部局名（略称）
        query.append(" A.SHOKUSHU_NAME_RYAKU,");                // 職名（略称）
        query.append(" A.SHOZOKU_CD,");                         // 所属機関コード
        query.append(" A.SHOZOKU_NAME,");                       // 所属機関名
        query.append(" A.NAME_KANJI_SEI,");                     // 事務担当者氏名（フリガナ-姓）
        query.append(" A.NAME_KANJI_MEI,");                     // 事務担当者氏名（フリガナ-名）
        query.append(" CASE WHEN TRUNC(sysdate, 'DD') < B.UKETUKEKIKAN_START ");
        query.append("            THEN 'FALSE' ");
        query.append("      WHEN TRUNC(sysdate, 'DD') > B.UKETUKEKIKAN_END ");
        query.append("            THEN 'FALSE' ");
        query.append("      ELSE 'TRUE' ");
        query.append(" END UKETUKE_END_FLAG,");                 // 学振受付期間フラグ
        query.append(" A.EDITION,");                            // 版
        query.append(" A.PDF_PATH,");                           // 領域計画書概要PDFの格納パス
        query.append(" A.SAKUSEI_DATE,");                       // 領域計画書概要作成日
        query.append(" A.JYURI_DATE,");                         // 受理日
        query.append(" A.RYOIKI_JOKYO_ID,");                    // 領域計画書（概要）申請状況ID
        query.append(" A.KENKYU_NO,");                          // 申請者研究者番号
        query.append(" SYSDATE,");

        // 件数検索 ここから---------------------
        query.append(" (SELECT COUNT(*) ");
        query.append("  FROM");
        query.append("    SHINSEIDATAKANRI").append(dbLink).append(" C");
        query.append("  WHERE A.KARIRYOIKI_NO = C.RYOIKI_NO ");
        query.append("    AND C.DEL_FLG = 0 ");
        // 削除フラグ
        if(searchInfo.getDelFlg().equals("0")){
            query.append(" AND A.DEL_FLG = ");
            query.append(searchInfo.getDelFlg());
        }
        //申請状況
        if (searchInfo.getSearchJokyoId() != null && searchInfo.getSearchJokyoId().length != 0) {
            query.append(" AND C.JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId(), true));
            query.append(")");
        }
        query.append(" ) AS COUNT ");
        // 件数検索 ここまで---------------------

        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" A");
        query.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" B");
        query.append(" ON A.JIGYO_ID = B.JIGYO_ID ");
        query.append(" INNER JOIN MASTER_JIGYO").append(dbLink).append(" D");
        query.append(" ON D.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) ");
        query.append("WHERE");
        query.append(" 2 > 1");

        if (searchInfo.getDelFlg().equals("0")) {
            query.append(" AND A.DEL_FLG = ");
            query.append(searchInfo.getDelFlg());
        }
        if (!StringUtil.isBlank(searchInfo.getJigyoCd())) {
            query.append(" AND D.JIGYO_CD = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getJigyoCd()));
            query.append("'");
        }
        if (!StringUtil.isBlank(searchInfo.getShozokuCd())) {
            query.append(" AND A.SHOZOKU_CD = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()));
            query.append("'");
        }
        if (!StringUtil.isBlank(searchInfo.getShozokuName())) {
            query.append(" AND A.SHOZOKU_NAME like '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getShozokuName()));
            query.append("%' ");
        }
        if ((searchInfo.getSearchJokyoId1() != null
                && searchInfo.getSearchJokyoId1().length != 0)
                && (searchInfo.getSearchJokyoId2() == null
                        || searchInfo.getSearchJokyoId2().length == 0)) {
            query.append(" AND A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId1(), true));
            query.append(")");
        }
        if ((searchInfo.getSearchJokyoId2() != null
                && searchInfo.getSearchJokyoId2().length != 0)
                && (searchInfo.getSearchJokyoId1() == null
                        || searchInfo.getSearchJokyoId1().length == 0)) {
            query.append(" AND (A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId2(), true));
            query.append(") AND A.CANCEL_FLG = 1)");
        }
        if ((searchInfo.getSearchJokyoId1() != null
                && searchInfo.getSearchJokyoId1().length != 0)
                && !(searchInfo.getSearchJokyoId2() == null
                        || searchInfo.getSearchJokyoId2().length == 0)) {
            query.append(" AND (A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId1(), true));
            query.append(")");
            query.append(" OR (A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId2(), true));
            query.append(") AND A.CANCEL_FLG =1 ))");
        }
        if((searchInfo.getRyoikiJokyoId() != null
                && searchInfo.getRyoikiJokyoId().length != 0)
                && searchInfo.getDelFlg().equals("0")) {
            query.append("AND A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getRyoikiJokyoId(), true));
            query.append(")");
        }
        if((searchInfo.getRyoikiJokyoId() != null
                && searchInfo.getRyoikiJokyoId().length != 0)
                && searchInfo.getDelFlg().equals("1")) {
            query.append("AND (A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getRyoikiJokyoId(), true));
            query.append(") OR A.DEL_FLG = ");
            query.append(searchInfo.getDelFlg());
            query.append(")");
            query.append(" AND A.UKETUKE_NO IS NOT NULL ");
            //2006/08/01 zhangt add start
            query.append(" AND A.JIGYO_ID IN(");
            query.append(StringUtil.changeArray2CSV(searchInfo.getJigyoId(), true));
            query.append(")");
            //2006/08/01 zhangt add end
            query.append(" ORDER BY A.JIGYO_ID ASC, A.UKETUKE_NO ASC");
        } else {
            query.append(" ORDER BY A.SHOZOKU_CD ASC, A.UKETUKE_NO ASC");
        }

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        
        return SelectUtil.select(connection, query.toString());
    }
//   2006/06/15 lwj ここまで 
    
//  2006/06/15 苗　追加ここから
    /**
     * 領域計画書概要テーブル（当該年度の削除フラグ=0）に仮領域番号が存在するかチェック
     * @param connection コネクション
     * @param strSQL
     * @return strCount
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String selectRyoikiNoCount(Connection connection, String strSQL)
            throws DataAccessException {

        String              strCount            =   null;
        ResultSet           recordSet           =   null;
        PreparedStatement   preparedStatement   =   null;

        try{
            preparedStatement = connection.prepareStatement(strSQL);
            recordSet = preparedStatement.executeQuery();
            if(recordSet.next()){
                strCount =recordSet.getString(IShinseiMaintenance.STR_COUNT);
            }

        }catch (SQLException ex) {
            throw new DataAccessException("領域計画書概要テーブルの検索中に例外が発生しました。", ex);
        }

        return strCount;
    }
// 2006/06/15　苗　追加ここまで   

// 2006/06/17 ly add start
    /**
     * 応募書類の提出書出力用CSVデータをDBより取得する。（所属機関管理用）
     * @param connection コネクション
     * @param ryoikiInfo 所属機関管理番号
     * @return
     * @throws ApplicationException
     */
    public List createTokuteiShinki(Connection connection,
            RyoikiKeikakushoInfo ryoikiInfo) throws ApplicationException {

        //-----------------------
        // 検索条件よりSQL文の作成
        //-----------------------
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" R.SHOZOKU_CD    \"機関番号\" ");   // 所属機関コード//差込印刷Wordとのリンクが切れるため「所属機関」のまま
        query.append(",R.SHOZOKU_NAME  \"所属機関名\" "); // 所属機関名     //差込印刷Wordとのリンクが切れるため「所属機関」のまま
        query.append(",COUNT(*)        \"領域計画書概要件数\" ");
        query.append("FROM (");
        query.append(  "SELECT ");
        query.append(  " A.SHOZOKU_CD     ");   // 所属機関コード
        query.append(  ",A.SHOZOKU_NAME   ");   // 所属機関名
        query.append(  ",A.KARIRYOIKI_NO  ");
        query.append(  " FROM ");
        query.append(  " RYOIKIKEIKAKUSHOINFO A");
        query.append(  " INNER JOIN JIGYOKANRI B");
        query.append(  " ON A.JIGYO_ID = B.JIGYO_ID ");
        query.append(  " INNER JOIN SHINSEIDATAKANRI C");
        query.append(  " ON A.KARIRYOIKI_NO = C.RYOIKI_NO");
        query.append(  " WHERE");
        query.append(  " C.DEL_FLG = 0 AND A.DEL_FLG = 0 ");

        // 申請状況
        if (ryoikiInfo.getJokyoIds() != null
                && ryoikiInfo.getJokyoIds().length > 0) {
            query.append(" AND C.JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(ryoikiInfo.getJokyoIds(),true));
            query.append(")");
        }

        // 領域計画書（概要）申請状況ID
        if (ryoikiInfo.getRyoikiJokyoIds() != null
                && ryoikiInfo.getRyoikiJokyoIds().length != 0) {
            query.append(" AND A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(ryoikiInfo
                    .getRyoikiJokyoIds(), true));
            query.append(")");
        }
        query.append(" GROUP BY A.SHOZOKU_CD, A.SHOZOKU_NAME, A.KARIRYOIKI_NO");
        query.append(" ) R ");

        //所属機関コード
        if (!StringUtil.isBlank(ryoikiInfo.getShozokuCd())) {
            query.append(" WHERE R.SHOZOKU_CD = '");
            query.append(EscapeUtil.toSqlString(ryoikiInfo.getShozokuCd()));
            query.append("'");
        }
        query.append(" GROUP BY R.SHOZOKU_CD, R.SHOZOKU_NAME");

        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        
        // -----DBレコード取得-----
        try {
            return SelectUtil.selectCsvList(connection, query.toString(), true);
        }
        catch (DataAccessException e) {
            throw new ApplicationException(
                    "応募書類の提出書出力データ検索中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4008"), e);
        }
    }
// 2006/06/17 ly add end

//  2006/06/15 zjp ここから
    /**
     * 指定されたシステム受付番号の応募書類情報を取得する。
     * 
     * @param connection コネクション
     * @param pkInfo
     * @param ryoikikeikakushoInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @author DIS.zjp 2006/06/15
     */
    public RyoikiKeikakushoInfo searchSyoninInfo(Connection connection,
            RyoikiKeikakushoPk pkInfo,RyoikiKeikakushoInfo ryoikikeikakushoInfo) 
            throws NoDataFoundException,DataAccessException {

        // システム受付番号が指定されていない場合はnullを返す
        if (pkInfo == null) {
            return null;
        }
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");                 //システム受付番号
        query.append(" A.KARIRYOIKI_NO,");                    //仮領域番号
        query.append(" A.UKETUKE_NO,");                       //受付番号（整理番号）
        query.append(" A.JIGYO_ID,");                         //事業ID
        query.append(" A.NENDO,");                            //年度
        query.append(" A.KAISU," );                            //回数
        query.append(" A.JIGYO_NAME,");                       //事業名
        query.append(" A.SHINSEISHA_ID,");                    //申請者ID
        query.append(" A.RYOIKI_NAME,");                      //領域名
        query.append(" A.BUKYOKU_NAME,");                     //部局名
        query.append(" A.SHOKUSHU_NAME_KANJI,");              //職名
        query.append(" A.SHOZOKU_CD,");                       //所属機関コード
        query.append(" A.NAME_KANJI_SEI,");                   //事務担当者氏名（姓）
        query.append(" A.NAME_KANJI_MEI,");                   //事務担当者氏名（名）
        query.append(" A.EDITION,");                          //版
        query.append(" A.PDF_PATH,");                         //領域計画書概要PDFの格納パス
        query.append(" A.SAKUSEI_DATE,");                     //領域計画書概要作成日
        query.append(" A.SHONIN_DATE,");                      //所属機関承認日
        query.append(" A.RYOIKI_JOKYO_ID,");                  //領域計画書（概要）申請状況ID
        query.append(" B.UKETUKEKIKAN_END,");                 //領域計画書（概要）申請状況ID
        query.append(" (SELECT COUNT(*) FROM SHINSEIDATAKANRI C ");                     
        query.append(" WHERE A.KARIRYOIKI_NO = C.RYOIKI_NO ");                     
        query.append(" AND C.DEL_FLG = 0 ");                     
        query.append(" AND A.DEL_FLG = 0 ");
        query.append(" AND C.JOKYO_ID IN (");
        query.append(StringUtil.changeArray2CSV(ryoikikeikakushoInfo.getJokyoIds(), true));                       
        query.append(")");
        query.append(" ) AS COUNT " );                         //応募件数    
        query.append(" FROM RYOIKIKEIKAKUSHOINFO ").append(dbLink).append(" A" );
        query.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" B" );
        query.append(" ON A.JIGYO_ID = B.JIGYO_ID ");    
        query.append(" WHERE A.DEL_FLG = 0 " );
// 2006/07/26 update start
//        query.append(" AND A.RYOIKI_SYSTEM_NO = '");
//        query.append(pkInfo.getRyoikiSystemNo());
//        query.append("' ");
        query.append(" AND A.RYOIKI_SYSTEM_NO = ?");
// 2006/07/26 update end

        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        // -----------------------
        // リスト取得
        // -----------------------
        PreparedStatement preparedStatement = null;
        ResultSet resutlt = null;

        try {
            preparedStatement = connection.prepareStatement(query.toString());
// 2006/07/26 add start
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, pkInfo.getRyoikiSystemNo());
// 2006/07/26 add end
            resutlt = preparedStatement.executeQuery();
            RyoikiKeikakushoInfo info = new RyoikiKeikakushoInfo();
            if (resutlt.next()) {
                info.setRyoikiSystemNo(resutlt.getString("RYOIKI_SYSTEM_NO"));
                info.setUketukeNo(resutlt.getString("UKETUKE_NO"));
                info.setJigyoId(resutlt.getString("JIGYO_ID"));
                info.setNendo(resutlt.getString("NENDO"));
                info.setKaisu(resutlt.getString("KAISU"));
                info.setJigyoName(resutlt.getString("JIGYO_NAME"));
                info.setShinseishaId(resutlt.getString("SHINSEISHA_ID"));
                info.setKariryoikiNo(resutlt.getString("KARIRYOIKI_NO"));
                info.setShozokuCd(resutlt.getString("SHOZOKU_CD"));
                info.setRyoikiName(resutlt.getString("RYOIKI_NAME"));
                info.setBukyokuName(resutlt.getString("BUKYOKU_NAME"));
                info.setPdfPath(resutlt.getString("PDF_PATH"));
                info.setShokushuNameKanji(resutlt.getString("SHOKUSHU_NAME_KANJI"));
                info.setNameKanjiSei(resutlt.getString("NAME_KANJI_SEI"));
                info.setNameKanjiMei(resutlt.getString("NAME_KANJI_MEI"));
                info.setCount(resutlt.getString("COUNT"));
                info.setEdition(resutlt.getString("EDITION"));
                info.setSakuseiDate(resutlt.getDate("SAKUSEI_DATE"));
                info.setShoninDate(resutlt.getDate("SHONIN_DATE"));
            }
            else {
                throw new NoDataFoundException(
                        "領域計画書（概要）情報管理テーブルに該当するデータが見つかりません。検索キー：領域計画書（概要）情報管理"
                                + info.getRyoikiSystemNo() + "'");
            }
            return info;
        }
        catch (SQLException ex) {
            throw new DataAccessException("領域計画書（概要）情報管理テーブル検索実行中に例外が発生しました。 ", ex);
        }
        finally {
            DatabaseUtil.closeResource(resutlt, preparedStatement);
        }
    }
    
    /**
     * 指定された事業コードの提出確認（特定領域研究（新規領域）)情報を取得する。
     * 
     * @param connection コネクション
     * @param kariryoikiNo
     * @param ryoikikeikakushoInfo
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @author DIS.zjp 2006/06/15
     */
    public List searchSystemNoList(Connection connection, String kariryoikiNo,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo)
            throws DataAccessException, NoDataFoundException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" S.SYSTEM_NO " );
        query.append(" FROM SHINSEIDATAKANRI").append(dbLink).append(" S" );
        query.append(" INNER JOIN RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R" );
        query.append(" ON R.KARIRYOIKI_NO = S.RYOIKI_NO " );
        query.append(" WHERE R.DEL_FLG = 0 " );
        query.append(" AND S.DEL_FLG = 0 " );
        query.append(" AND S.JOKYO_ID IN (");
        query.append(StringUtil.changeArray2CSV(ryoikikeikakushoInfo.getJokyoIds(), true));
        query.append(")");
        query.append(" AND S.RYOIKI_NO = '");
        query.append(EscapeUtil.toSqlString(kariryoikiNo));
        query.append("' ");
        query.append(" GROUP BY S.SYSTEM_NO ");

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        try {
            return SelectUtil.select(connection, query.toString());
        }
        catch (NoDataFoundException e) {
            throw new NoDataFoundException(e.getMessage(), new ErrorInfo(
                    "errors.5002"), e);
        }
        catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }
    
    /**
     * 承認確認メール送信事業管理情報を取得する。学振受付締切日のn日前の事業情報のみ
     * 削除フラグが「0」の場合のみ検索する。
     * @param connection            コネクション
     * @param days                  締切日までの日数
     * @return                      事業管理情報
     * @throws DataAccessException  データ取得中に例外が発生した場合。
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public List selectKakuninTokusokuInfo(Connection connection, int days)
            throws DataAccessException, NoDataFoundException {
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT DISTINCT");
        query.append(" A.JIGYO_ID,");
        query.append(" A.NENDO,");
        query.append(" A.KAISU,");
        query.append(" A.JIGYO_NAME,");
        query.append(" B.SHOZOKU_CD,");
        query.append(" C.TANTO_EMAIL ");
        query.append("FROM");
        query.append(" JIGYOKANRI A,");
        query.append(" SHINSEIDATAKANRI B,");
        query.append(" SHOZOKUTANTOINFO C,");
        query.append(" RYOIKIKEIKAKUSHOINFO D ");
        query.append("WHERE");
        query.append(" A.RYOIKI_KAKUTEIKIKAN_END = TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD') + ");
        query.append(EscapeUtil.toSqlString(Integer.toString(days)));
        query.append(" AND B.JOKYO_ID IN ('01','02','03')");
        query.append(" AND A.DEL_FLG = 0");
        query.append(" AND A.JIGYO_ID = B.JIGYO_ID");
        query.append(" AND A.NENDO = B.NENDO");
        query.append(" AND A.KAISU = B.KAISU");
        query.append(" AND B.DEL_FLG = 0");
        query.append(" AND C.DEL_FLG = 0");
        query.append(" AND B.SHOZOKU_CD = C.SHOZOKU_CD");
        query.append(" AND B.RYOIKI_NO = D.KARIRYOIKI_NO");
        query.append(" AND D.RYOIKIKEIKAKUSHO_KAKUTEI_FLG <> 1");
        query.append(" AND SUBSTR(A.JIGYO_ID,3,5) = '00022' ");
        query.append("ORDER BY SHOZOKU_CD, JIGYO_ID, NENDO, KAISU");

        if (log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        return SelectUtil.select(connection, query.toString());
    }
    
    /**
     * 承認督促メール送信事業管理情報を取得する。学振受付締切日のn日前の事業情報のみ
     * 削除フラグが「0」の場合のみ検索する。
     * 特定領域研究事業のみ
     * @param connection            コネクション
     * @param days                  締切日までの日数
     * @return                      事業管理情報
     * @throws DataAccessException  データ取得中に例外が発生した場合。
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public List selectShoninTokusokuInfo(Connection connection, int days)
            throws DataAccessException, NoDataFoundException {
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT DISTINCT");
        query.append(" A.JIGYO_ID,");
        query.append(" A.NENDO,");
        query.append(" A.KAISU,");
        query.append(" A.JIGYO_NAME,");
        query.append(" B.SHOZOKU_CD,");
        query.append(" C.TANTO_EMAIL ");
        query.append("FROM");
        query.append(" JIGYOKANRI A,");
        query.append(" RYOIKIKEIKAKUSHOINFO B,");
        query.append(" SHOZOKUTANTOINFO C ");
        query.append("WHERE");
        query.append(" A.UKETUKEKIKAN_END = TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD') + ");
        query.append(EscapeUtil.toSqlString(Integer.toString(days)));
        query.append(" AND B.RYOIKI_JOKYO_ID IN ('01','02','03','31','32','33')");
        query.append(" AND A.DEL_FLG = 0");
        query.append(" AND A.JIGYO_ID = B.JIGYO_ID");
        query.append(" AND A.NENDO = B.NENDO");
        query.append(" AND A.KAISU = B.KAISU");
        query.append(" AND B.DEL_FLG = 0");
        query.append(" AND C.DEL_FLG = 0");
        query.append(" AND B.SHOZOKU_CD = C.SHOZOKU_CD");
        query.append(" AND SUBSTR(A.JIGYO_ID,3,5) = '00022' ");
        query.append("ORDER BY SHOZOKU_CD, JIGYO_ID, NENDO, KAISU");

        if (log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        return SelectUtil.select(connection, query.toString());
    }
//  2006/06/15 zjp ここまで
    
    
//2006/06/20 苗　追加ここから
    /**
     * 領域計画書概要テーブル（削除フラグ=0）に申請者IDが存在するかチェック
     * @param connection コネクション
     * @param shinseishaInfo
     * @return strCount
     * @throws DataAccessException
     */
    public int selectCountByShinseishaId(Connection connection,
            ShinseishaInfo shinseishaInfo)
            throws DataAccessException {
        
        //応募者ID
        String shinseishaId = shinseishaInfo.getShinseishaId();

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" COUNT(SHINSEISHA_ID)");
        query.append(" GET_COUNT ");
        query.append("FROM ");
        query.append(" RYOIKIKEIKAKUSHOINFO R ");
        query.append("WHERE");
        query.append(" R.DEL_FLG = 0 ");
// 2006/07/26 dyh update start
//        query.append(" AND R.SHINSEISHA_ID = '");
//        query.append(EscapeUtil.toSqlString(shinseishaId));
//        query.append("'");
        query.append(" AND R.SHINSEISHA_ID = ?");
// 2006/07/26 dyh update end
        if (log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try{
            preparedStatement = connection.prepareStatement(query.toString());
// 2006/07/26 dyh add start
            int index =1;
            DatabaseUtil.setParameter(preparedStatement, index++, shinseishaId);
// 2006/07/26 dyh add end
            recordSet = preparedStatement.executeQuery();
            recordSet.next();
            int infoCount = recordSet.getInt(IShinseiMaintenance.STR_COUNT);
            
            return infoCount;
        } catch (SQLException ex) {
            throw new DataAccessException("領域計画書概要テーブルの検索中に例外が発生しました。", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2006/06/20　苗　追加ここまで   
    
    
//  宮　2006/06/20　ここから
    /**
     * 仮領域番号発行情報登録画面の形式チェック
     * @param connection コネクション
     * @param shinseishaInfo 
     * @param pkInfo 
     * @return count
     * @throws DataAccessException データベースアクセス中の例外
     * @throws DuplicateKeyException
     */
    public void isExistRyoikiInfo(Connection connection,
            ShinseishaInfo shinseishaInfo,JigyoKanriPk pkInfo)
            throws DataAccessException, DuplicateKeyException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT COUNT(*)");
        query.append(" FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO R");
        query.append(" WHERE R.DEL_FLG = 0");
        query.append(" AND R.KENKYU_NO = ?");
        query.append(" AND R.JIGYO_ID = ?");

        if (log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            preparedStatement.setString(i++, shinseishaInfo.getKenkyuNo());
            preparedStatement.setString(i++, pkInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
            if (count > 0) {
                throw new DuplicateKeyException("該当する情報は既に登録されています。");
            }
        }catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    //宮　ここまで
    
//  宮　2006/06/21　ここから
    /**
     * 応募情報又は研究計画調書確認完了確認画面の形式チェック
     * @param connection コネクション
     * @param existInfo                
     * @return count
     * @throws DataAccessException データベースアクセス中の例外
     * @throws ApplicationException
     */
    public void existRyoikiInfoCount(Connection connection,
            ShinseiDataInfo existInfo) throws DataAccessException,
            ApplicationException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT COUNT(*)");
        query.append(" FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        query.append(" WHERE R.DEL_FLG = 0");
        query.append(" AND R.KARIRYOIKI_NO = ?");

        if (log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            preparedStatement.setString(i++, existInfo.getRyouikiNo());
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
            if (count == 0) {
                throw new ApplicationException("該当データはありません。",
                        new ErrorInfo("errors.5002"));
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
        finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    // 宮 ここまで

    /**
     * 表紙PDF格納パスを取得する。
     * 
     * @param connection コネクション
     * @param ryoikiSystemNo システム受付番号
     * @return String PDFファイルパス
     * @throws DataAccessException
     * @author DIS.dyh
     */
    public String selectHyoshiPdfPath(Connection connection,
            String ryoikiSystemNo)
            throws DataAccessException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" R.HYOSHI_PDF_PATH ");
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        query.append("WHERE");
        query.append(" R.DEL_FLG = 0 ");
        query.append(" AND R.RYOIKI_SYSTEM_NO = ?");

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        String path = "";
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, ryoikiSystemNo);
            recordSet = preparedStatement.executeQuery();

            if (recordSet.next()) {
                path = recordSet.getString("HYOSHI_PDF_PATH");
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("対象データが存在しません。 ", ex);
        }
        finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
        return path;
    }
  //add end ly 2006/06/22

//  2006/06/27 dyh add start
    /**
     * 領域計画書概要PDFの格納パスを取得する。
     * 
     * @param connection コネクション
     * @param ryoikiSystemNo システム受付番号
     * @return String PDFファイルパス
     * @throws DataAccessException
     * @author DIS.dyh
     */
    public String selectPdfPath(Connection connection,
            String ryoikiSystemNo)
            throws DataAccessException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" R.PDF_PATH ");
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        query.append("WHERE");
        query.append(" R.DEL_FLG = 0 ");
        query.append(" AND R.RYOIKI_SYSTEM_NO = ?");

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        String path = "";
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, ryoikiSystemNo);
            recordSet = preparedStatement.executeQuery();

            if (recordSet.next()) {
                path = recordSet.getString("PDF_PATH");
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("対象データが存在しません。 ", ex);
        }
        finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
        return path;
    }

    /**
     * 領域計画書表紙PDFファイルデータを検索する。
     * 
     * @param connection コネクション
     * @param ryoikiSystemNo システム受付番号
     * @return RyoikiKeikakushoInfo 領域計画書表紙PDFファイルデータ
     * @throws DataAccessException
     * @author DIS.dyh
     */
    public RyoikiKeikakushoInfo selectGaiyoHyoshiPdfData(Connection connection,
            String ryoikiSystemNo)
            throws DataAccessException {

        // 検索条件：RYOIKI_SYSTEM_NO,DEL_FLG?0
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" R.NENDO, ");              // 年度
        query.append(" R.KARIRYOIKI_NO, ");      // 仮領域番号
        query.append(" R.UKETUKE_NO, ");         // 受付番号（整理番号）
        query.append(" R.JIGYO_ID, ");           // 事業ID
        query.append(" R.RYOIKI_NAME, ");        // 応募領域名
        query.append(" R.NAME_KANA_SEI, ");      // 領域代表者氏名（漢字等-姓）
        query.append(" R.NAME_KANA_MEI, ");      // 領域代表者氏名（漢字等-名）
        query.append(" R.NAME_KANJI_SEI, ");     // 領域代表者氏名（フリガナ-姓）
        query.append(" R.NAME_KANJI_MEI, ");     // 領域代表者氏名（フリガナ-名）
        query.append(" R.SHOZOKU_NAME, ");       // 所属研究機関名
        query.append(" R.BUKYOKU_NAME, ");       // 部局名
        query.append(" R.SHOKUSHU_NAME_KANJI, ");// 職名
        query.append(" R.SAKUSEI_DATE ");        // 作成日（年）
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");// 領域計画書（概要）情報管理テーブル
        query.append("WHERE");
        query.append(" R.DEL_FLG = 0 AND R.RYOIKI_SYSTEM_NO = ? ");
        query.append("ORDER BY R.JIGYO_ID");

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, ryoikiSystemNo);
            recordSet = preparedStatement.executeQuery();

            RyoikiKeikakushoInfo info = new RyoikiKeikakushoInfo();
            if(recordSet.next()){
                info.setNendo(recordSet.getString("NENDO"));                // 年度
                info.setKariryoikiNo(recordSet.getString("KARIRYOIKI_NO")); // 応募領域名
                info.setUketukeNo(recordSet.getString("UKETUKE_NO"));       // 受付番号（整理番号）
                info.setJigyoId(recordSet.getString("JIGYO_ID"));           // 応募領域名
                info.setRyoikiName(recordSet.getString("RYOIKI_NAME"));     // 応募領域名
                info.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));  // 研究者氏名（フリガナ）（姓）
                info.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));  // 研究者氏名（フリガナ）（名）
                info.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));// 研究者氏名（漢字等）（姓）
                info.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));// 研究者氏名（漢字等）（名）
                info.setShozokuName(recordSet.getString("SHOZOKU_NAME"));   // 所属研究機関名
                info.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));   // 部局名
                info.setShokushuNameKanji(recordSet.getString("SHOKUSHU_NAME_KANJI"));// 職名
                info.setSakuseiDate(recordSet.getDate("SAKUSEI_DATE"));     // 作成日（年）
            }
            return info;
        }catch (SQLException ex) {
               throw new DataAccessException("対象データが存在しません。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * 領域計画書概要PDFの格納パスを更新する。
     * @param connection            コネクション
     * @param ryoikiSystemNo        主キー値
     * @param iodFile               PDFファイル
     * @throws DataAccessException  更新処理中の例外。
     * @throws NoDataFoundException　処理対象データがみつからないとき。
     * @author DIS.dyh
     */
    public void updatePdfPath(
            Connection connection,
            final String ryoikiSystemNo,
            File iodFile)
            throws DataAccessException {

        StringBuffer uQuery = new StringBuffer();
        uQuery.append("UPDATE");
        uQuery.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        uQuery.append("SET ");
        uQuery.append(" R.PDF_PATH = ? ");
        uQuery.append("WHERE");
        uQuery.append(" R.DEL_FLG = 0 AND R.RYOIKI_SYSTEM_NO = ?");

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + uQuery.toString());
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(uQuery.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++,
                    EscapeUtil.toSqlString(iodFile.getAbsolutePath()));
            DatabaseUtil.setParameter(preparedStatement, index++,
                    EscapeUtil.toSqlString(ryoikiSystemNo));
            DatabaseUtil.executeUpdate(preparedStatement);

        } catch (SQLException ex) {
            throw new DataAccessException(
                "領域計画書概要PDFの格納パス更新中に例外が発生しました。 ：システム受付番号'"
                    + ryoikiSystemNo + "'", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * 表紙PDF格納パスを更新する。
     * @param connection            コネクション
     * @param ryoikiSystemNo        主キー値
     * @param iodFile               PDFファイル
     * @throws DataAccessException  更新処理中の例外。
     * @throws NoDataFoundException　処理対象データがみつからないとき。
     * @author DIS.dyh
     */
    public void updateHyoshiPdfPath(
            Connection connection,
            final String ryoikiSystemNo,
            File iodFile)
            throws DataAccessException {

//        //参照可能領域計画書表紙データかチェック
//        boolean isExist = isExist(connection, ryoikiSystemNo);
//        if(count == 0){
//            throw new UserAuthorityException("参照可能な領域計画書表紙データではありません。");
//        }
        
        StringBuffer uQuery = new StringBuffer();
        uQuery.append("UPDATE");
        uQuery.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        uQuery.append("SET ");
        uQuery.append(" R.HYOSHI_PDF_PATH = ? ");
        uQuery.append("WHERE");
        uQuery.append(" R.DEL_FLG = 0 AND R.RYOIKI_SYSTEM_NO = ?");

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + uQuery.toString());
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(uQuery.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++,
                    EscapeUtil.toSqlString(iodFile.getAbsolutePath()));
            DatabaseUtil.setParameter(preparedStatement, index++,
                    EscapeUtil.toSqlString(ryoikiSystemNo));
            DatabaseUtil.executeUpdate(preparedStatement);

        } catch (SQLException ex) {
            throw new DataAccessException(
                "表紙PDF格納パス更新中に例外が発生しました。 ：システム受付番号'"
                    + ryoikiSystemNo + "'", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
// 2006/06/27 dyh add end

//2006/06/27 苗　追加ここから

    //2006.08.14 iso
    //確定解除で作成中の時、審査希望分野を変更して一時保存した場合、
    //受付番号が採番されないバグを修正。
    //引数を変更
//    /**
//     * 整理番号を返す。
//     * @param connection コネクション
//     * @param ryoikikeikakushoInfo
//     * @return String
//     * @throws DataAccessException
//     */
//    public String getUketukeNo(Connection connection,
//            RyoikiKeikakushoInfo ryoikikeikakushoInfo)
//            throws DataAccessException {
//        
//        StringBuffer query = new StringBuffer();
//        query.append("SELECT");
//        query.append(" TO_CHAR(count(*) + 1, 'FM00') SEQ_NUM ");
//        query.append("FROM");
//        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
//        query.append("WHERE");
//        query.append(" R.KIBOUBUMON_CD = ? ");
//        query.append(" AND R.JIGYO_ID = ? ");
//        
//        //2006.08.14 iso 一時保存データがカウントされ正しく採番されないバグ修正
//        query.append(" AND R.UKETUKE_NO IS NOT NULL ");
//        
//        //for debug
//        if(log.isDebugEnabled()){
//            log.debug("query:" + query.toString());
//        }
//
//        //DB接続
//        PreparedStatement preparedStatement = null;
//        ResultSet recordSet = null;
//        try {
//            preparedStatement = connection.prepareStatement(query.toString());
//            int i = 1;
//            preparedStatement.setString(i++, ryoikikeikakushoInfo.getKiboubumonCd());
//            preparedStatement.setString(i++, ryoikikeikakushoInfo.getJigyoId());
//            
//            recordSet = preparedStatement.executeQuery();
//            String seqNumber = null;
//            if (recordSet.next()) {
//                seqNumber= recordSet.getString(1);
//                if(seqNumber == null){
//                    seqNumber = "01";
//                }
//            }
//
//            return StringUtils.right(seqNumber,2);
//             
//        } catch (SQLException ex) {
//            throw new DataAccessException("領域計画書（概要）情報管理テーブル検索実行中に例外が発生しました。", ex);
//        } finally {
//            DatabaseUtil.closeResource(recordSet, preparedStatement);
//        }
//    }
    /**
     * 整理番号を返す。
     * @param connection コネクション
     * @param ryoikikeikakushoInfo
     * @param kiboubumonRyaku 審査希望部門略称
     * @return String
     * @throws DataAccessException
     */
    public String getUketukeNo(Connection connection,
    		RyoikiKeikakushoInfo ryoikikeikakushoInfo,
            String kiboubumonRyaku)
            throws DataAccessException {

        //受付番号「人XX」、希望分野コード05(生物)のようなデータが生まれる可能性があるため、
        //順番の取得は、受付番号の頭文字で行う。
        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" TO_CHAR(MAX(SUBSTR(UKETUKE_NO,2,2)) + 1, 'FM00') SEQ_NUM ");
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        query.append("WHERE");
        query.append(" R.JIGYO_ID = ? ");
        query.append(" AND SUBSTR(R.UKETUKE_NO, 1, 1) = ? ");
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        //DB接続
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            preparedStatement.setString(i++, ryoikikeikakushoInfo.getJigyoId());
            preparedStatement.setString(i++, kiboubumonRyaku);
            
            recordSet = preparedStatement.executeQuery();
            String seqNumber = null;
            if (recordSet.next()) {
                seqNumber= recordSet.getString(1);
                if(seqNumber == null){
                    seqNumber = "01";
                }
            }

            return StringUtils.right(seqNumber,2);
             
        } catch (SQLException ex) {
            throw new DataAccessException("領域計画書（概要）情報管理テーブル検索実行中に例外が発生しました。", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    
    /**
     * 当該領域計画書を参照することができるかチェックする。
     * 参照可能に該当しない場合は NoDataAccessExceptioin を発生させる。
     * 指定数と該当データ数が一致しない場合も NoDataAccessExceptioin を発生させる。
     * 
     * @param connection コネクション
     * @param primaryKey
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void checkOwnRyoikiKakusyoData(Connection connection,
            RyoikiKeikakushoPk primaryKey)
            throws DataAccessException, NoDataFoundException {
        
        StringBuffer query = new StringBuffer();
        String table = " RYOIKIKEIKAKUSHOINFO"+dbLink+" R";
        
        //ログインユーザによって条件式を分岐する
        //---申請者
        if(userInfo.getRole().equals(UserRole.SHINSEISHA)){
            query.append(" AND");
            query.append(" R.SHINSEISHA_ID = '");
            query.append(EscapeUtil.toSqlString(userInfo.getShinseishaInfo().getShinseishaId()));
            query.append("'");
                    
        //---所属機関担当者
        }else if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
            query.append(" AND");
            query.append(" R.SHOZOKU_CD = '");
            query.append(EscapeUtil.toSqlString(userInfo.getShozokuInfo().getShozokuCd()));
            query.append("'");
                    
        // ---審査員
        }else if(userInfo.getRole().equals(UserRole.SHINSAIN)){
            table = " RYOIKIKEIKAKUSHOINFO"+dbLink+" R,";
            query.append("(SELECT SYSTEM_NO FROM SHINSAKEKKA");
            query.append(dbLink);
            query.append("  WHERE");
            query.append("    SHINSAIN_NO = '");
            query.append(EscapeUtil.toSqlString(userInfo.getShinsainInfo().getShinsainNo()));
            query.append("') B");

            query.append(" AND");
            query.append(" A.SYSTEM_NO = B.SYSTEM_NO"); 
        
        // ---業務担当者
        }else if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
            
            table = table+ " inner join  (SELECT JIGYO_CD FROM ACCESSKANRI";
            table = table+ "  WHERE";
            table = table+ "    GYOMUTANTO_ID = '";
            table = table+ EscapeUtil.toSqlString(userInfo.getGyomutantoInfo().getGyomutantoId());
            table = table+ "') B";

            table = table+ " on";
            table = table+ " B.JIGYO_CD = SUBSTR(R.JIGYO_ID,3,5)"; // 事業コード
            query.append("") ;
        // ---それ以外
        }else{

        }

        // SQLの作成
        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" COUNT(R.RYOIKI_SYSTEM_NO) ");
        select.append("FROM ");
        select.append(table);
        select.append(" WHERE ");
        select.append(" R.RYOIKI_SYSTEM_NO = ?");
        select.append(query);

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + select.toString());
        }
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        int count = 0;
        try {
            //登録
            preparedStatement = connection.prepareStatement(select.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, primaryKey.getRyoikiSystemNo());
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("領域計画書（概要）情報管理テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }       
        
        //該当件数のチェック
        if(count != 1){
            throw new UserAuthorityException("参照可能な領域計画データではありません。");
        }        
    }

    /**
     * 領域計画書情報を削除する（論理）。
     * @param connection                コネクション
     * @param ryoikiSystemNo            システム受付番号
     * @throws DataAccessException      更新中に例外が発生した場合
     * @throws NoDataFoundException     対象データが見つからない場合
     */
    public void deleteFlagRyoikiKeikakushoInfo(Connection connection, String ryoikiSystemNo)
            throws DataAccessException {

        StringBuffer query = new StringBuffer();
        query.append("UPDATE");
        query.append(" RYOIKIKEIKAKUSHOINFO R ");
        query.append("SET");
        query.append(" R.DEL_FLG = 1 ");
        query.append("WHERE");
        query.append(" R.RYOIKI_SYSTEM_NO = ?");//システム受付番号

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++, ryoikiSystemNo); //システム受付番号                  
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            log.error("領域計画書情報更新中に例外が発生しました。 ", ex);
            throw new DataAccessException("領域計画書情報更新中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * 領域計画書情報を更新する。
     * @param connection                コネクション
     * @param updateInfo                更新する領域計画書情報
     * @throws DataAccessException      更新中に例外が発生した場合
     * @throws NoDataFoundException     対象データが見つからない場合
     */
    public void updateRyoikiKeikakushoInfo(
        Connection connection,
        RyoikiKeikakushoInfo updateInfo)
        throws DataAccessException, NoDataFoundException {
        
        //参照可能申請データかチェック
        checkOwnRyoikiKakusyoData(connection, updateInfo);

        StringBuffer query = new StringBuffer();
        query.append("UPDATE");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");    
        query.append("SET");
        query.append("  R.RYOIKI_SYSTEM_NO              = ? ");// システム受付番号
        query.append(" ,R.KARIRYOIKI_NO                 = ? ");// 仮領域番号
        query.append(" ,R.UKETUKE_NO                    = ? ");// 受付番号（整理番号）
        query.append(" ,R.JIGYO_ID                      = ? ");// 事業ID
        query.append(" ,R.NENDO                         = ? ");// 年度
        query.append(" ,R.KAISU                         = ? ");// 回数
        query.append(" ,R.JIGYO_NAME                    = ? ");// 事業名
        query.append(" ,R.SHINSEISHA_ID                 = ? ");// 申請者ID
        query.append(" ,R.KAKUTEI_DATE                  = ? ");// 領域計画書確定日
        query.append(" ,R.SAKUSEI_DATE                  = ? ");// 領域計画書概要作成日
        query.append(" ,R.SHONIN_DATE                   = ? ");// 所属機関承認日
        query.append(" ,R.JYURI_DATE                    = ? ");// 学振受理日
        query.append(" ,R.NAME_KANJI_SEI                = ? ");// 領域代表者氏名（漢字等-姓）
        query.append(" ,R.NAME_KANJI_MEI                = ? ");// 領域代表者氏名（漢字等-名）
        query.append(" ,R.NAME_KANA_SEI                 = ? ");// 領域代表者氏名（フリガナ-姓）
        query.append(" ,R.NAME_KANA_MEI                 = ? ");// 領域代表者氏名（フリガナ-名）
        query.append(" ,R.NENREI                        = ? ");// 年齢
        query.append(" ,R.KENKYU_NO                     = ? ");// 申請者研究者番号
        query.append(" ,R.SHOZOKU_CD                    = ? ");// 所属機関コード
        query.append(" ,R.SHOZOKU_NAME                  = ? ");// 所属機関名
        query.append(" ,R.SHOZOKU_NAME_RYAKU            = ? ");// 所属機関名（略称）
        query.append(" ,R.BUKYOKU_CD                    = ? ");// 部局コード
        query.append(" ,R.BUKYOKU_NAME                  = ? ");// 部局名
        query.append(" ,R.BUKYOKU_NAME_RYAKU            = ? ");// 部局名（略称）
        query.append(" ,R.SHOKUSHU_CD                   = ? ");// 職名コード
        query.append(" ,R.SHOKUSHU_NAME_KANJI           = ? ");// 職名（和文）
        query.append(" ,R.SHOKUSHU_NAME_RYAKU           = ? ");// 職名（略称）
        query.append(" ,R.KIBOUBUMON_CD                 = ? ");// 審査希望部門（系等）コード
        query.append(" ,R.KIBOUBUMON_NAME               = ? ");// 審査希望部門（系等）名称
        query.append(" ,R.RYOIKI_NAME                   = ? ");// 応募領域名
        query.append(" ,R.RYOIKI_NAME_EIGO              = ? ");// 英訳名
        query.append(" ,R.RYOIKI_NAME_RYAKU             = ? ");// 領域略称名
        query.append(" ,R.KENKYU_GAIYOU                 = ? ");// 研究概要
        query.append(" ,R.JIZENCHOUSA_FLG               = ? ");// 事前調査の状況
        query.append(" ,R.JIZENCHOUSA_SONOTA            = ? ");// 事前調査の状況（その他）
        query.append(" ,R.KAKO_OUBOJYOUKYOU             = ? ");// 過去の応募状況
        query.append(" ,R.ZENNENDO_OUBO_FLG             = ? ");// 最終年度前年度の応募（該当の有無）
        query.append(" ,R.ZENNENDO_OUBO_NO              = ? ");// 最終年度前年度の研究領域番号
        query.append(" ,R.ZENNENDO_OUBO_RYOIKI_RYAKU    = ? ");// 最終年度前年度の領域略称名
        query.append(" ,R.ZENNENDO_OUBO_SETTEI          = ? ");// 最終年度前年度の設定期間
        query.append(" ,R.BUNKASAIMOKU_CD1              = ? ");// 関連分野（細目番号）1
        query.append(" ,R.BUNYA_NAME1                   = ? ");// 関連分野（分野）1
        query.append(" ,R.BUNKA_NAME1                   = ? ");// 関連分野（分科）1
        query.append(" ,R.SAIMOKU_NAME1                 = ? ");// 関連分野（細目）1
        query.append(" ,R.BUNKASAIMOKU_CD2              = ? ");// 関連分野（細目番号）2
        query.append(" ,R.BUNYA_NAME2                   = ? ");// 関連分野（分野）2
        query.append(" ,R.BUNKA_NAME2                   = ? ");// 関連分野（分科）2
        query.append(" ,R.SAIMOKU_NAME2                 = ? ");// 関連分野（細目）2
        query.append(" ,R.KANRENBUNYA_BUNRUI_NO         = ? ");// 関連分野15分類（番号）
        query.append(" ,R.KANRENBUNYA_BUNRUI_NAME       = ? ");// 関連分野15分類（分類名）
        query.append(" ,R.KENKYU_HITSUYOUSEI_1          = ? ");// 研究の必要性1
        query.append(" ,R.KENKYU_HITSUYOUSEI_2          = ? ");// 研究の必要性2
        query.append(" ,R.KENKYU_HITSUYOUSEI_3          = ? ");// 研究の必要性3
        query.append(" ,R.KENKYU_HITSUYOUSEI_4          = ? ");// 研究の必要性4
        query.append(" ,R.KENKYU_HITSUYOUSEI_5          = ? ");// 研究の必要性5
        query.append(" ,R.KENKYU_SYOKEI_1               = ? ");// 研究経費（1年目)-小計
        query.append(" ,R.KENKYU_UTIWAKE_1              = ? ");// 研究経費（1年目)-内訳
        query.append(" ,R.KENKYU_SYOKEI_2               = ? ");// 研究経費（2年目)-小計
        query.append(" ,R.KENKYU_UTIWAKE_2              = ? ");// 研究経費（2年目)-内訳
        query.append(" ,R.KENKYU_SYOKEI_3               = ? ");// 研究経費（3年目)-小計
        query.append(" ,R.KENKYU_UTIWAKE_3              = ? ");// 研究経費（3年目)-内訳
        query.append(" ,R.KENKYU_SYOKEI_4               = ? ");// 研究経費（4年目)-小計
        query.append(" ,R.KENKYU_UTIWAKE_4              = ? ");// 研究経費（4年目)-内訳
        query.append(" ,R.KENKYU_SYOKEI_5               = ? ");// 研究経費（5年目)-小計
        query.append(" ,R.KENKYU_UTIWAKE_5              = ? ");// 研究経費（5年目)-内訳
        query.append(" ,R.KENKYU_SYOKEI_6               = ? ");// 研究経費（6年目)-小計
        query.append(" ,R.KENKYU_UTIWAKE_6              = ? ");// 研究経費（6年目)-内訳
        query.append(" ,R.DAIHYOU_ZIP                   = ? ");// 領域代表者（郵便番号）
        query.append(" ,R.DAIHYOU_ADDRESS               = ? ");// 領域代表者（住所）
        query.append(" ,R.DAIHYOU_TEL                   = ? ");// 領域代表者（電話）
        query.append(" ,R.DAIHYOU_FAX                   = ? ");// 領域代表者（FAX）
        query.append(" ,R.DAIHYOU_EMAIL                 = ? ");// 領域代表者（メールアドレス）
        query.append(" ,R.JIMUTANTO_NAME_KANJI_SEI      = ? ");// 事務担当者氏名（漢字等-姓）
        query.append(" ,R.JIMUTANTO_NAME_KANJI_MEI      = ? ");// 事務担当者氏名（漢字等-名）
        query.append(" ,R.JIMUTANTO_NAME_KANA_SEI       = ? ");// 事務担当者氏名（フリガナ-姓）
        query.append(" ,R.JIMUTANTO_NAME_KANA_MEI       = ? ");// 事務担当者氏名（フリガナ-名）
        query.append(" ,R.JIMUTANTO_SHOZOKU_CD          = ? ");// 事務担当者研究機関番号
        query.append(" ,R.JIMUTANTO_SHOZOKU_NAME        = ? ");// 事務担当者研究機関名
        query.append(" ,R.JIMUTANTO_BUKYOKU_CD          = ? ");// 事務担当者部局番号
        query.append(" ,R.JIMUTANTO_BUKYOKU_NAME        = ? ");// 事務担当者部局名
        query.append(" ,R.JIMUTANTO_SHOKUSHU_CD         = ? ");// 事務担当者職名番号
        query.append(" ,R.JIMUTANTO_SHOKUSHU_NAME_KANJI = ? ");// 事務担当者職名（和文）
        query.append(" ,R.JIMUTANTO_ZIP                 = ? ");// 事務担当者（郵便番号）
        query.append(" ,R.JIMUTANTO_ADDRESS             = ? ");// 事務担当者（住所）
        query.append(" ,R.JIMUTANTO_TEL                 = ? ");// 事務担当者（電話）
        query.append(" ,R.JIMUTANTO_FAX                 = ? ");// 事務担当者（FAX）
        query.append(" ,R.JIMUTANTO_EMAIL               = ? ");// 事務担当者（メールアドレス）
        query.append(" ,R.KANREN_SHIMEI1                = ? ");// 関連分野の研究者-氏名1
        query.append(" ,R.KANREN_KIKAN1                 = ? ");// 関連分野の研究者-所属研究機関1
        query.append(" ,R.KANREN_BUKYOKU1               = ? ");// 関連分野の研究者-部局1
        query.append(" ,R.KANREN_SHOKU1                 = ? ");// 関連分野の研究者-職名1
        query.append(" ,R.KANREN_SENMON1                = ? ");// 関連分野の研究者-専門分野1
        query.append(" ,R.KANREN_TEL1                   = ? ");// 関連分野の研究者-勤務先電話番号1
        query.append(" ,R.KANREN_JITAKUTEL1             = ? ");// 関連分野の研究者-自宅電話番号1
        query.append(" ,R.KANREN_SHIMEI2                = ? ");// 関連分野の研究者-氏名2
        query.append(" ,R.KANREN_KIKAN2                 = ? ");// 関連分野の研究者-所属研究機関2
        query.append(" ,R.KANREN_BUKYOKU2               = ? ");// 関連分野の研究者-部局2
        query.append(" ,R.KANREN_SHOKU2                 = ? ");// 関連分野の研究者-職名2
        query.append(" ,R.KANREN_SENMON2                = ? ");// 関連分野の研究者-専門分野2
        query.append(" ,R.KANREN_TEL2                   = ? ");// 関連分野の研究者-勤務先電話番号2
        query.append(" ,R.KANREN_JITAKUTEL2             = ? ");// 関連分野の研究者-自宅電話番号2
        query.append(" ,R.KANREN_SHIMEI3                = ? ");// 関連分野の研究者-氏名3
        query.append(" ,R.KANREN_KIKAN3                 = ? ");// 関連分野の研究者-所属研究機関3
        query.append(" ,R.KANREN_BUKYOKU3               = ? ");// 関連分野の研究者-部局3
        query.append(" ,R.KANREN_SHOKU3                 = ? ");// 関連分野の研究者-職名3
        query.append(" ,R.KANREN_SENMON3                = ? ");// 関連分野の研究者-専門分野3
        query.append(" ,R.KANREN_TEL3                   = ? ");// 関連分野の研究者-勤務先電話番号3
        query.append(" ,R.KANREN_JITAKUTEL3             = ? ");// 関連分野の研究者-自宅電話番号3
        query.append(" ,R.PDF_PATH                      = ? ");// 領域計画書概要PDFの格納パス
        query.append(" ,R.HYOSHI_PDF_PATH               = ? ");// 表紙PDF格納パス
        query.append(" ,R.JURI_KEKKA                    = ? ");// 受理結果
        query.append(" ,R.JURI_BIKO                     = ? ");// 受理結果備考
        query.append(" ,R.RYOIKI_JOKYO_ID               = ? ");// 領域計画書（概要）申請状況ID
        query.append(" ,R.EDITION                       = ? ");// 版
        query.append(" ,R.RYOIKIKEIKAKUSHO_KAKUTEI_FLG  = ? ");// 確定フラグ
        query.append(" ,R.CANCEL_FLG                    = ? ");// 解除フラグ
        query.append(" ,R.DEL_FLG                       = ? ");// 削除フラグ
        query.append("WHERE");
        query.append(" R.RYOIKI_SYSTEM_NO = ?");//システム受付番号

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query.toString());
            int index = this.setAllParameter(preparedStatement, updateInfo);
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getRyoikiSystemNo()); //システム受付番号                  
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            log.error("領域計画書情報更新中に例外が発生しました。 ", ex);
            throw new DataAccessException("領域計画書情報更新中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * 指定PreparedStatementに申請データすべてのパラメータをセットする。
     * SQLステートメントには全てのパラメータを指定可能にしておくこと。
     * @param preparedStatement
     * @param ryoikikeikakushoInfo
     * @return index          次のパラメータインデックス
     * @throws SQLException
     */
    private int setAllParameter(PreparedStatement preparedStatement,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo) throws SQLException {
        int index = 1;
        //---基本情報
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getRyoikiSystemNo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKariryoikiNo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getUketukeNo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJigyoId());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getNendo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKaisu());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJigyoName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShinseishaId());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKakuteiDate());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getSakuseiDate());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShoninDate());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJyuriDate());

        //---申請者（領域代表者）
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getNameKanjiSei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getNameKanjiMei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getNameKanaSei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getNameKanaMei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getNenrei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuNo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShozokuCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShozokuName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShozokuNameRyaku());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBukyokuCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBukyokuName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBukyokuNameRyaku());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShokushuCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShokushuNameKanji());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShokushuNameRyaku());

        //---領域情報
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKiboubumonCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKiboubumonName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getRyoikiName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getRyoikiNameEigo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getRyoikiNameRyaku());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuGaiyou());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJizenchousaFlg());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJizenchousaSonota());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKakoOubojyoukyou());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getZennendoOuboFlg());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getZennendoOuboNo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getZennendoOuboRyoikiRyaku());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getZennendoOuboSettei());
        
        //---関連分野
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBunkasaimokuCd1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBunyaName1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBunkaName1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getSaimokuName1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBunkasaimokuCd2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBunyaName2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBunkaName2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getSaimokuName2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenbunyaBunruiNo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenbunyaBunruiName());

        //---研究の必要性
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuHitsuyousei1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuHitsuyousei2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuHitsuyousei3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuHitsuyousei4());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuHitsuyousei5());
        
        //---研究経費
// 2006/08/25 dyh update start 原因：仕様変更
//        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuSyokei1());
//        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuUtiwake1());
        DatabaseUtil.setParameter(preparedStatement,index++, "");
        DatabaseUtil.setParameter(preparedStatement,index++, "");
// 2006/08/25 dyh update end
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuSyokei2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuUtiwake2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuSyokei3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuUtiwake3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuSyokei4());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuUtiwake4());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuSyokei5());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuUtiwake5());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuSyokei6());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuUtiwake6());

        //---領域代表者
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getDaihyouZip());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getDaihyouAddress());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getDaihyouTel());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getDaihyouFax());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getDaihyouEmail());
        
        //---事務担当者
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoNameKanjiSei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoNameKanjiMei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoNameKanaSei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoNameKanaMei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoShozokuCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoShozokuName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoBukyokuCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoBukyokuName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoShokushuCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoShokushuNameKanji());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoZip());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoAddress());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoTel());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoFax());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoEmail());
        
        
        //---関連分野の研究者
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenShimei1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenKikan1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenBukyoku1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenShoku1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenSenmon1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenTel1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenJitakutel1());
        
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenShimei2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenKikan2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenBukyoku2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenShoku2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenSenmon2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenTel2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenJitakutel2());
        
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenShimei3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenKikan3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenBukyoku3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenShoku3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenSenmon3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenTel3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenJitakutel3());
        
        //---基本情報（後半）
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getPdfPath());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getHyoshiPdfPath());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJuriKekka());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJuriBiko());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getRyoikiJokyoId());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getEdition());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getRyoikikeikakushoKakuteiFlg());        
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getCancelFlg());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getDelFlg());

        return index;
    }
    
    /**
     * 当該領域計画書を参照することができるかチェックする。 参照可能に該当しない場合は NoDataAccessExceptioin を発生させる。
     * <p>
     * 判断基準は以下の通り。
     * <li>申請者の場合･･･自分の申請者IDのものかどうか
     * </p>
     * 
     * @param connection コネクション
     * @param pkInfo
     * @param lock
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    private RyoikiKeikakushoInfo selectRyoikiInfo(Connection connection,
            RyoikiKeikakushoPk pkInfo, boolean lock)
            throws DataAccessException, NoDataFoundException {
        
        //参照可能申請データかチェック
        checkOwnRyoikiKakusyoData(connection, pkInfo);
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");             // システム受付番号
        query.append(" A.KARIRYOIKI_NO,");                // 仮領域番号            
        query.append(" A.UKETUKE_NO,");                   // 受付番号（整理番号）
        query.append(" A.JIGYO_ID,");                     // 事業ID
        query.append(" A.NENDO,");                        // 年度
        query.append(" A.KAISU,");                        // 回数
        query.append(" A.JIGYO_NAME,");                   // 事業名
        query.append(" A.SHINSEISHA_ID,");                // 申請者ID
        query.append(" A.KAKUTEI_DATE,");                 // 領域計画書確定日                 
        query.append(" A.SAKUSEI_DATE,");                 // 領域計画書概要作成日
        query.append(" A.SHONIN_DATE,");                  // 所属機関承認日
        query.append(" A.JYURI_DATE,");                   // 学振受理日
        query.append(" A.NAME_KANJI_SEI,");               // 領域代表者氏名（漢字等-姓）
        query.append(" A.NAME_KANJI_MEI,");               // 領域代表者氏名（漢字等-名）
        query.append(" A.NAME_KANA_SEI,");                // 領域代表者氏名（フリガナ-姓）
        query.append(" A.NAME_KANA_MEI,");                // 領域代表者氏名（フリガナ-名）
        query.append(" A.NENREI,");                       // 年齢
        query.append(" A.KENKYU_NO,");                    // 申請者研究者番号
        query.append(" A.SHOZOKU_CD,");                   // 所属機関コード
        query.append(" A.SHOZOKU_NAME,");                 // 所属機関名
        query.append(" A.SHOZOKU_NAME_RYAKU,");           // 所属機関名（略称）
        query.append(" A.BUKYOKU_CD,");                   // 部局コード
        query.append(" A.BUKYOKU_NAME,");                 // 部局名
        query.append(" A.BUKYOKU_NAME_RYAKU,");           // 部局名（略称）
        query.append(" A.SHOKUSHU_CD,");                  // 職名コード
        query.append(" A.SHOKUSHU_NAME_KANJI,");          // 職名（和文）
        query.append(" A.SHOKUSHU_NAME_RYAKU,");          // 職名（略称）
        query.append(" A.KIBOUBUMON_CD,");                // 審査希望部門（系等）コード
        query.append(" A.KIBOUBUMON_NAME,");              // 審査希望部門（系等）名称
        query.append(" A.RYOIKI_NAME,");                  // 応募領域名
        query.append(" A.RYOIKI_NAME_EIGO,");             // 英訳名
        query.append(" A.RYOIKI_NAME_RYAKU,");            // 領域略称名
        query.append(" A.KENKYU_GAIYOU,");                // 研究概要
        query.append(" A.JIZENCHOUSA_FLG,");              // 事前調査の状況
        query.append(" A.JIZENCHOUSA_SONOTA,");           // 事前調査の状況（その他）
        query.append(" A.KAKO_OUBOJYOUKYOU,");            // 過去の応募状況
        query.append(" A.ZENNENDO_OUBO_FLG,");            // 最終年度前年度の応募（該当の有無）
        query.append(" A.ZENNENDO_OUBO_NO,");             // 最終年度前年度の研究領域番号
        query.append(" A.ZENNENDO_OUBO_RYOIKI_RYAKU,");   // 最終年度前年度の領域略称名
        query.append(" A.ZENNENDO_OUBO_SETTEI,");         // 最終年度前年度の設定期間
        query.append(" A.BUNKASAIMOKU_CD1,");             // 関連分野（細目番号）1
        query.append(" A.BUNYA_NAME1,");                  // 関連分野（分野）1
        query.append(" A.BUNKA_NAME1,");                  // 関連分野（分科）1
        query.append(" A.SAIMOKU_NAME1,");                // 関連分野（細目）1
        query.append(" A.BUNKASAIMOKU_CD2,");             // 関連分野（細目番号）2
        query.append(" A.BUNYA_NAME2,");                  // 関連分野（分野）2
        query.append(" A.BUNKA_NAME2,");                  // 関連分野（分科）2
        query.append(" A.SAIMOKU_NAME2,");                // 関連分野（細目）2
        query.append(" A.KANRENBUNYA_BUNRUI_NO,");        // 関連分野15分類（番号）
        query.append(" A.KANRENBUNYA_BUNRUI_NAME,");      // 関連分野15分類（分類名）
        query.append(" A.KENKYU_HITSUYOUSEI_1,");         // 研究の必要性1
        query.append(" A.KENKYU_HITSUYOUSEI_2,");         // 研究の必要性2
        query.append(" A.KENKYU_HITSUYOUSEI_3,");         // 研究の必要性3
        query.append(" A.KENKYU_HITSUYOUSEI_4,");         // 研究の必要性4
        query.append(" A.KENKYU_HITSUYOUSEI_5,");         // 研究の必要性5
        query.append(" A.KENKYU_SYOKEI_1,");              // 研究経費（1年目)-小計
        query.append(" A.KENKYU_UTIWAKE_1,");             // 研究経費（1年目)-内訳
        query.append(" A.KENKYU_SYOKEI_2,");              // 研究経費（2年目)-小計
        query.append(" A.KENKYU_UTIWAKE_2,");             // 研究経費（2年目)-内訳
        query.append(" A.KENKYU_SYOKEI_3,");              // 研究経費（3年目)-小計
        query.append(" A.KENKYU_UTIWAKE_3,");             // 研究経費（3年目)-内訳
        query.append(" A.KENKYU_SYOKEI_4,");              // 研究経費（4年目)-小計
        query.append(" A.KENKYU_UTIWAKE_4,");             // 研究経費（4年目)-内訳
        query.append(" A.KENKYU_SYOKEI_5,");              // 研究経費（5年目)-小計
        query.append(" A.KENKYU_UTIWAKE_5,");             // 研究経費（5年目)-内訳
        query.append(" A.KENKYU_SYOKEI_6,");              // 研究経費（6年目)-小計
        query.append(" A.KENKYU_UTIWAKE_6,");             // 研究経費（6年目)-内訳
        query.append(" A.DAIHYOU_ZIP,");                  // 領域代表者（郵便番号）
        query.append(" A.DAIHYOU_ADDRESS,");              // 領域代表者（住所）
        query.append(" A.DAIHYOU_TEL,");                  // 領域代表者（電話）
        query.append(" A.DAIHYOU_FAX,");                  // 領域代表者（FAX）
        query.append(" A.DAIHYOU_EMAIL,");                // 領域代表者（メールアドレス）
        query.append(" A.JIMUTANTO_NAME_KANJI_SEI,");     // 事務担当者氏名（漢字等-姓）
        query.append(" A.JIMUTANTO_NAME_KANJI_MEI,");     // 事務担当者氏名（漢字等-名）
        query.append(" A.JIMUTANTO_NAME_KANA_SEI,");      // 事務担当者氏名（フリガナ-姓）
        query.append(" A.JIMUTANTO_NAME_KANA_MEI,");      // 事務担当者氏名（フリガナ-名）
        query.append(" A.JIMUTANTO_SHOZOKU_CD,");         // 事務担当者研究機関番号
        query.append(" A.JIMUTANTO_SHOZOKU_NAME,");       // 事務担当者研究機関名
        query.append(" A.JIMUTANTO_BUKYOKU_CD,");         // 事務担当者部局番号
        query.append(" A.JIMUTANTO_BUKYOKU_NAME,");       // 事務担当者部局名
        query.append(" A.JIMUTANTO_SHOKUSHU_CD,");        // 事務担当者職名番号
        query.append(" A.JIMUTANTO_SHOKUSHU_NAME_KANJI,");// 事務担当者職名（和文）
        query.append(" A.JIMUTANTO_ZIP,");                // 事務担当者（郵便番号）
        query.append(" A.JIMUTANTO_ADDRESS,");            // 事務担当者（住所）
        query.append(" A.JIMUTANTO_TEL,");                // 事務担当者（電話）
        query.append(" A.JIMUTANTO_FAX,");                // 事務担当者（FAX）
        query.append(" A.JIMUTANTO_EMAIL,");              // 事務担当者（メールアドレス）
        query.append(" A.KANREN_SHIMEI1,");               // 関連分野の研究者-氏名1
        query.append(" A.KANREN_KIKAN1,");                // 関連分野の研究者-所属研究機関1
        query.append(" A.KANREN_BUKYOKU1,");              // 関連分野の研究者-部局1
        query.append(" A.KANREN_SHOKU1,");                // 関連分野の研究者-職名1
        query.append(" A.KANREN_SENMON1,");               // 関連分野の研究者-専門分野1
        query.append(" A.KANREN_TEL1,");                  // 関連分野の研究者-勤務先電話番号1
        query.append(" A.KANREN_JITAKUTEL1,");            // 関連分野の研究者-自宅電話番号1
        query.append(" A.KANREN_SHIMEI2,");               // 関連分野の研究者-氏名2
        query.append(" A.KANREN_KIKAN2,");                // 関連分野の研究者-所属研究機関2
        query.append(" A.KANREN_BUKYOKU2,");              // 関連分野の研究者-部局2                             
        query.append(" A.KANREN_SHOKU2,");                // 関連分野の研究者-職名2
        query.append(" A.KANREN_SENMON2,");               // 関連分野の研究者-専門分野2
        query.append(" A.KANREN_TEL2,");                  // 関連分野の研究者-勤務先電話番号2
        query.append(" A.KANREN_JITAKUTEL2,");            // 関連分野の研究者-自宅電話番号2
        query.append(" A.KANREN_SHIMEI3,");               // 関連分野の研究者-氏名3
        query.append(" A.KANREN_KIKAN3,");                // 関連分野の研究者-所属研究機関3
        query.append(" A.KANREN_BUKYOKU3,");              // 関連分野の研究者-部局3                             
        query.append(" A.KANREN_SHOKU3,");                // 関連分野の研究者-職名3
        query.append(" A.KANREN_SENMON3,");               // 関連分野の研究者-専門分野3
        query.append(" A.KANREN_TEL3,");                  // 関連分野の研究者-勤務先電話番号3
        query.append(" A.KANREN_JITAKUTEL3,");            // 関連分野の研究者-自宅電話番号3
        query.append(" A.PDF_PATH,");                     // 領域計画書概要PDFの格納パス
        query.append(" A.HYOSHI_PDF_PATH,");              // 表紙PDF格納パス
        query.append(" A.JURI_KEKKA,");                   // 受理結果
        query.append(" A.JURI_BIKO,");                    // 受理結果備考
        query.append(" A.RYOIKI_JOKYO_ID,");              // 領域計画書（概要）申請状況ID                    
        query.append(" A.EDITION,");                      // 版
        query.append(" A.RYOIKIKEIKAKUSHO_KAKUTEI_FLG,"); // 確定フラグ
        query.append(" A.CANCEL_FLG,");                   // 解除フラグ
        query.append(" A.DEL_FLG ");                      // 削除フラグ
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" A");
// 2006/07/26 dyh update start
//        query.append(" WHERE A.RYOIKI_SYSTEM_NO ='");
//        query.append(pkInfo.getRyoikiSystemNo());
//        query.append("'");
        query.append(" WHERE A.RYOIKI_SYSTEM_NO = ?");
// 2006/07/26 dyh update end

        //排他制御を行う場合
        if(lock){
            query.append(" FOR UPDATE");
        }

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        
        // -----------------------
        // リスト取得
        // -----------------------
        PreparedStatement ps = null;
        ResultSet resutlt = null;

        try {
            ps = connection.prepareStatement(query.toString());
            int index = 1;
            DatabaseUtil.setParameter(ps,index++, pkInfo.getRyoikiSystemNo());
            resutlt = ps.executeQuery();

            RyoikiKeikakushoInfo info = new RyoikiKeikakushoInfo();
            if (resutlt.next()) {
                info.setRyoikiSystemNo(resutlt.getString("RYOIKI_SYSTEM_NO"));
                info.setKariryoikiNo(resutlt.getString("KARIRYOIKI_NO"));
                info.setUketukeNo(resutlt.getString("UKETUKE_NO"));
                info.setJigyoId(resutlt.getString("JIGYO_ID"));
                info.setJigyoCd(resutlt.getString("JIGYO_ID").substring(2,7));
                info.setNendo(resutlt.getString("NENDO"));
                info.setKaisu(resutlt.getString("KAISU"));
                info.setJigyoName(resutlt.getString("JIGYO_NAME"));
                info.setShinseishaId(resutlt.getString("SHINSEISHA_ID"));
                info.setKakuteiDate(resutlt.getDate("KAKUTEI_DATE"));
                info.setSakuseiDate(resutlt.getDate("SAKUSEI_DATE"));
                info.setShoninDate(resutlt.getDate("SHONIN_DATE"));
                info.setJyuriDate(resutlt.getDate("JYURI_DATE"));
                info.setNameKanjiSei(resutlt.getString("NAME_KANJI_SEI"));
                info.setNameKanjiMei(resutlt.getString("NAME_KANJI_MEI"));
                info.setNameKanaSei(resutlt.getString("NAME_KANA_SEI"));
                info.setNameKanaMei(resutlt.getString("NAME_KANA_MEI"));
                info.setNenrei(resutlt.getString("NENREI"));
                info.setKenkyuNo(resutlt.getString("KENKYU_NO"));
                info.setShozokuCd(resutlt.getString("SHOZOKU_CD"));
                info.setShozokuName(resutlt.getString("SHOZOKU_NAME"));
                info.setShozokuNameRyaku(resutlt.getString("SHOZOKU_NAME_RYAKU"));
                info.setBukyokuCd(resutlt.getString("BUKYOKU_CD"));
                info.setBukyokuName(resutlt.getString("BUKYOKU_NAME"));
                info.setBukyokuNameRyaku(resutlt.getString("BUKYOKU_NAME_RYAKU"));
                info.setShokushuCd(resutlt.getString("SHOKUSHU_CD"));
                info.setShokushuNameKanji(resutlt.getString("SHOKUSHU_NAME_KANJI"));
                info.setShokushuNameRyaku(resutlt.getString("SHOKUSHU_NAME_RYAKU"));
                info.setKiboubumonCd(resutlt.getString("KIBOUBUMON_CD"));
                info.setKiboubumonName(resutlt.getString("KIBOUBUMON_NAME"));
                info.setRyoikiName(resutlt.getString("RYOIKI_NAME"));
                info.setRyoikiNameEigo(resutlt.getString("RYOIKI_NAME_EIGO"));
                info.setRyoikiNameRyaku(resutlt.getString("RYOIKI_NAME_RYAKU"));
                info.setKenkyuGaiyou(resutlt.getString("KENKYU_GAIYOU"));
                info.setJizenchousaFlg(resutlt.getString("JIZENCHOUSA_FLG"));
                info.setJizenchousaSonota(resutlt.getString("JIZENCHOUSA_SONOTA"));
                info.setKakoOubojyoukyou(resutlt.getString("KAKO_OUBOJYOUKYOU"));
                info.setZennendoOuboFlg(resutlt.getString("ZENNENDO_OUBO_FLG"));
                info.setZennendoOuboNo(resutlt.getString("ZENNENDO_OUBO_NO"));
                info.setZennendoOuboRyoikiRyaku(resutlt.getString("ZENNENDO_OUBO_RYOIKI_RYAKU"));
                info.setZennendoOuboSettei(resutlt.getString("ZENNENDO_OUBO_SETTEI"));
                info.setBunkasaimokuCd1(resutlt.getString("BUNKASAIMOKU_CD1"));
                info.setBunyaName1(resutlt.getString("BUNYA_NAME1"));
                info.setBunkaName1(resutlt.getString("BUNKA_NAME1"));
                info.setSaimokuName1(resutlt.getString("SAIMOKU_NAME1"));
                info.setBunkasaimokuCd2(resutlt.getString("BUNKASAIMOKU_CD2"));
                info.setBunyaName2(resutlt.getString("BUNYA_NAME2"));
                info.setBunkaName2(resutlt.getString("BUNKA_NAME2"));
                info.setSaimokuName2(resutlt.getString("SAIMOKU_NAME2"));
                info.setKanrenbunyaBunruiNo(resutlt.getString("KANRENBUNYA_BUNRUI_NO"));
                info.setKanrenbunyaBunruiName(resutlt.getString("KANRENBUNYA_BUNRUI_NAME"));
                info.setKenkyuHitsuyousei1(resutlt.getString("KENKYU_HITSUYOUSEI_1"));
                info.setKenkyuHitsuyousei2(resutlt.getString("KENKYU_HITSUYOUSEI_2"));
                info.setKenkyuHitsuyousei3(resutlt.getString("KENKYU_HITSUYOUSEI_3"));
                info.setKenkyuHitsuyousei4(resutlt.getString("KENKYU_HITSUYOUSEI_4"));
                info.setKenkyuHitsuyousei5(resutlt.getString("KENKYU_HITSUYOUSEI_5"));
// 2006/08/25 dyh delete start 原因：仕様変更
                //info.setKenkyuSyokei1(resutlt.getString("KENKYU_SYOKEI_1"));
                //info.setKenkyuUtiwake1(resutlt.getString("KENKYU_UTIWAKE_1"));
// 2006/08/25 dyh delete end
                info.setKenkyuSyokei2(resutlt.getString("KENKYU_SYOKEI_2"));
                info.setKenkyuUtiwake2(resutlt.getString("KENKYU_UTIWAKE_2"));
                info.setKenkyuSyokei3(resutlt.getString("KENKYU_SYOKEI_3"));
                info.setKenkyuUtiwake3(resutlt.getString("KENKYU_UTIWAKE_3"));
                info.setKenkyuSyokei4(resutlt.getString("KENKYU_SYOKEI_4"));
                info.setKenkyuUtiwake4(resutlt.getString("KENKYU_UTIWAKE_4"));
                info.setKenkyuSyokei5(resutlt.getString("KENKYU_SYOKEI_5"));
                info.setKenkyuUtiwake5(resutlt.getString("KENKYU_UTIWAKE_5"));
                info.setKenkyuSyokei6(resutlt.getString("KENKYU_SYOKEI_6"));
                info.setKenkyuUtiwake6(resutlt.getString("KENKYU_UTIWAKE_6"));
                info.setDaihyouZip(resutlt.getString("DAIHYOU_ZIP"));
                info.setDaihyouAddress(resutlt.getString("DAIHYOU_ADDRESS"));
                info.setDaihyouTel(resutlt.getString("DAIHYOU_TEL"));
                info.setDaihyouFax(resutlt.getString("DAIHYOU_FAX"));
                info.setDaihyouEmail(resutlt.getString("DAIHYOU_EMAIL"));
                info.setJimutantoNameKanjiSei(resutlt.getString("JIMUTANTO_NAME_KANJI_SEI"));
                info.setJimutantoNameKanjiMei(resutlt.getString("JIMUTANTO_NAME_KANJI_MEI"));
                info.setJimutantoNameKanaSei(resutlt.getString("JIMUTANTO_NAME_KANA_SEI"));
                info.setJimutantoNameKanaMei(resutlt.getString("JIMUTANTO_NAME_KANA_MEI"));
                info.setJimutantoShozokuCd(resutlt.getString("JIMUTANTO_SHOZOKU_CD"));
                info.setJimutantoShozokuName(resutlt.getString("JIMUTANTO_SHOZOKU_NAME"));
                info.setJimutantoBukyokuCd(resutlt.getString("JIMUTANTO_BUKYOKU_CD"));
                info.setJimutantoBukyokuName(resutlt.getString("JIMUTANTO_BUKYOKU_NAME"));
                info.setJimutantoShokushuCd(resutlt.getString("JIMUTANTO_SHOKUSHU_CD"));
                info.setJimutantoShokushuNameKanji(resutlt.getString("JIMUTANTO_SHOKUSHU_NAME_KANJI"));
                info.setJimutantoZip(resutlt.getString("JIMUTANTO_ZIP"));
                info.setJimutantoAddress(resutlt.getString("JIMUTANTO_ADDRESS"));
                info.setJimutantoTel(resutlt.getString("JIMUTANTO_TEL"));
                info.setJimutantoFax(resutlt.getString("JIMUTANTO_FAX"));
                info.setJimutantoEmail(resutlt.getString("JIMUTANTO_EMAIL"));
                info.setKanrenShimei1(resutlt.getString("KANREN_SHIMEI1"));
                info.setKanrenKikan1(resutlt.getString("KANREN_KIKAN1"));
                info.setKanrenBukyoku1(resutlt.getString("KANREN_BUKYOKU1"));
                info.setKanrenShoku1(resutlt.getString("KANREN_SHOKU1"));
                info.setKanrenSenmon1(resutlt.getString("KANREN_SENMON1"));
                info.setKanrenTel1(resutlt.getString("KANREN_TEL1"));
                info.setKanrenJitakutel1(resutlt.getString("KANREN_JITAKUTEL1"));
                info.setKanrenShimei2(resutlt.getString("KANREN_SHIMEI2"));
                info.setKanrenKikan2(resutlt.getString("KANREN_KIKAN2"));
                info.setKanrenBukyoku2(resutlt.getString("KANREN_BUKYOKU2"));
                info.setKanrenShoku2(resutlt.getString("KANREN_SHOKU2"));
                info.setKanrenSenmon2(resutlt.getString("KANREN_SENMON2"));
                info.setKanrenTel2(resutlt.getString("KANREN_TEL2"));
                info.setKanrenJitakutel2(resutlt.getString("KANREN_JITAKUTEL2"));
                info.setKanrenShimei3(resutlt.getString("KANREN_SHIMEI3"));
                info.setKanrenKikan3(resutlt.getString("KANREN_KIKAN3"));
                info.setKanrenBukyoku3(resutlt.getString("KANREN_BUKYOKU3"));
                info.setKanrenShoku3(resutlt.getString("KANREN_SHOKU3"));
                info.setKanrenSenmon3(resutlt.getString("KANREN_SENMON3"));
                info.setKanrenTel3(resutlt.getString("KANREN_TEL3"));
                info.setKanrenJitakutel3(resutlt.getString("KANREN_JITAKUTEL3"));
                info.setPdfPath(resutlt.getString("PDF_PATH"));
                info.setHyoshiPdfPath(resutlt.getString("HYOSHI_PDF_PATH"));
                info.setJuriKekka(resutlt.getString("JURI_KEKKA"));
                info.setJuriBiko(resutlt.getString("JURI_BIKO"));
                info.setRyoikiJokyoId(resutlt.getString("RYOIKI_JOKYO_ID"));
                info.setEdition(resutlt.getString("EDITION"));
                info.setRyoikikeikakushoKakuteiFlg(resutlt.getString("RYOIKIKEIKAKUSHO_KAKUTEI_FLG"));
                info.setCancelFlg(resutlt.getString("CANCEL_FLG"));
                info.setDelFlg(resutlt.getString("DEL_FLG"));
            } else {
                throw new NoDataFoundException(
                        "領域計画書（概要）情報管理テーブルに該当するデータが見つかりません。検索キー：システム受付番号'"
                        + pkInfo.getRyoikiSystemNo()
                        + "'");
            }
            return info;
        }
        catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        finally {
            DatabaseUtil.closeResource(resutlt, ps);
        }
    }
    

    //2006.09.25 iso iso タイトルに「概要」をつけたPDF作成のため
    /**
     * 領域計画書（概要）申請状況IDが「受理」の領域計画書情報リストを返す。
     * 
     * @param connection コネクション
     * @param lock
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    private RyoikiKeikakushoInfo[] selectRyoikiInfos(Connection connection, boolean lock)
            throws DataAccessException, NoDataFoundException {
        
    	//個別のselectRyoikiInfoと同じだが、2006年度しか使わない機能になりそうなので、独立させない。
    	//今後このメソッドを使用するなら、SQL分や結果のセット部分は独立させるべき。
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");             // システム受付番号
        query.append(" A.KARIRYOIKI_NO,");                // 仮領域番号            
        query.append(" A.UKETUKE_NO,");                   // 受付番号（整理番号）
        query.append(" A.JIGYO_ID,");                     // 事業ID
        query.append(" A.NENDO,");                        // 年度
        query.append(" A.KAISU,");                        // 回数
        query.append(" A.JIGYO_NAME,");                   // 事業名
        query.append(" A.SHINSEISHA_ID,");                // 申請者ID
        query.append(" A.KAKUTEI_DATE,");                 // 領域計画書確定日                 
        query.append(" A.SAKUSEI_DATE,");                 // 領域計画書概要作成日
        query.append(" A.SHONIN_DATE,");                  // 所属機関承認日
        query.append(" A.JYURI_DATE,");                   // 学振受理日
        query.append(" A.NAME_KANJI_SEI,");               // 領域代表者氏名（漢字等-姓）
        query.append(" A.NAME_KANJI_MEI,");               // 領域代表者氏名（漢字等-名）
        query.append(" A.NAME_KANA_SEI,");                // 領域代表者氏名（フリガナ-姓）
        query.append(" A.NAME_KANA_MEI,");                // 領域代表者氏名（フリガナ-名）
        query.append(" A.NENREI,");                       // 年齢
        query.append(" A.KENKYU_NO,");                    // 申請者研究者番号
        query.append(" A.SHOZOKU_CD,");                   // 所属機関コード
        query.append(" A.SHOZOKU_NAME,");                 // 所属機関名
        query.append(" A.SHOZOKU_NAME_RYAKU,");           // 所属機関名（略称）
        query.append(" A.BUKYOKU_CD,");                   // 部局コード
        query.append(" A.BUKYOKU_NAME,");                 // 部局名
        query.append(" A.BUKYOKU_NAME_RYAKU,");           // 部局名（略称）
        query.append(" A.SHOKUSHU_CD,");                  // 職名コード
        query.append(" A.SHOKUSHU_NAME_KANJI,");          // 職名（和文）
        query.append(" A.SHOKUSHU_NAME_RYAKU,");          // 職名（略称）
        query.append(" A.KIBOUBUMON_CD,");                // 審査希望部門（系等）コード
        query.append(" A.KIBOUBUMON_NAME,");              // 審査希望部門（系等）名称
        query.append(" A.RYOIKI_NAME,");                  // 応募領域名
        query.append(" A.RYOIKI_NAME_EIGO,");             // 英訳名
        query.append(" A.RYOIKI_NAME_RYAKU,");            // 領域略称名
        query.append(" A.KENKYU_GAIYOU,");                // 研究概要
        query.append(" A.JIZENCHOUSA_FLG,");              // 事前調査の状況
        query.append(" A.JIZENCHOUSA_SONOTA,");           // 事前調査の状況（その他）
        query.append(" A.KAKO_OUBOJYOUKYOU,");            // 過去の応募状況
        query.append(" A.ZENNENDO_OUBO_FLG,");            // 最終年度前年度の応募（該当の有無）
        query.append(" A.ZENNENDO_OUBO_NO,");             // 最終年度前年度の研究領域番号
        query.append(" A.ZENNENDO_OUBO_RYOIKI_RYAKU,");   // 最終年度前年度の領域略称名
        query.append(" A.ZENNENDO_OUBO_SETTEI,");         // 最終年度前年度の設定期間
        query.append(" A.BUNKASAIMOKU_CD1,");             // 関連分野（細目番号）1
        query.append(" A.BUNYA_NAME1,");                  // 関連分野（分野）1
        query.append(" A.BUNKA_NAME1,");                  // 関連分野（分科）1
        query.append(" A.SAIMOKU_NAME1,");                // 関連分野（細目）1
        query.append(" A.BUNKASAIMOKU_CD2,");             // 関連分野（細目番号）2
        query.append(" A.BUNYA_NAME2,");                  // 関連分野（分野）2
        query.append(" A.BUNKA_NAME2,");                  // 関連分野（分科）2
        query.append(" A.SAIMOKU_NAME2,");                // 関連分野（細目）2
        query.append(" A.KANRENBUNYA_BUNRUI_NO,");        // 関連分野15分類（番号）
        query.append(" A.KANRENBUNYA_BUNRUI_NAME,");      // 関連分野15分類（分類名）
        query.append(" A.KENKYU_HITSUYOUSEI_1,");         // 研究の必要性1
        query.append(" A.KENKYU_HITSUYOUSEI_2,");         // 研究の必要性2
        query.append(" A.KENKYU_HITSUYOUSEI_3,");         // 研究の必要性3
        query.append(" A.KENKYU_HITSUYOUSEI_4,");         // 研究の必要性4
        query.append(" A.KENKYU_HITSUYOUSEI_5,");         // 研究の必要性5
        query.append(" A.KENKYU_SYOKEI_1,");              // 研究経費（1年目)-小計
        query.append(" A.KENKYU_UTIWAKE_1,");             // 研究経費（1年目)-内訳
        query.append(" A.KENKYU_SYOKEI_2,");              // 研究経費（2年目)-小計
        query.append(" A.KENKYU_UTIWAKE_2,");             // 研究経費（2年目)-内訳
        query.append(" A.KENKYU_SYOKEI_3,");              // 研究経費（3年目)-小計
        query.append(" A.KENKYU_UTIWAKE_3,");             // 研究経費（3年目)-内訳
        query.append(" A.KENKYU_SYOKEI_4,");              // 研究経費（4年目)-小計
        query.append(" A.KENKYU_UTIWAKE_4,");             // 研究経費（4年目)-内訳
        query.append(" A.KENKYU_SYOKEI_5,");              // 研究経費（5年目)-小計
        query.append(" A.KENKYU_UTIWAKE_5,");             // 研究経費（5年目)-内訳
        query.append(" A.KENKYU_SYOKEI_6,");              // 研究経費（6年目)-小計
        query.append(" A.KENKYU_UTIWAKE_6,");             // 研究経費（6年目)-内訳
        query.append(" A.DAIHYOU_ZIP,");                  // 領域代表者（郵便番号）
        query.append(" A.DAIHYOU_ADDRESS,");              // 領域代表者（住所）
        query.append(" A.DAIHYOU_TEL,");                  // 領域代表者（電話）
        query.append(" A.DAIHYOU_FAX,");                  // 領域代表者（FAX）
        query.append(" A.DAIHYOU_EMAIL,");                // 領域代表者（メールアドレス）
        query.append(" A.JIMUTANTO_NAME_KANJI_SEI,");     // 事務担当者氏名（漢字等-姓）
        query.append(" A.JIMUTANTO_NAME_KANJI_MEI,");     // 事務担当者氏名（漢字等-名）
        query.append(" A.JIMUTANTO_NAME_KANA_SEI,");      // 事務担当者氏名（フリガナ-姓）
        query.append(" A.JIMUTANTO_NAME_KANA_MEI,");      // 事務担当者氏名（フリガナ-名）
        query.append(" A.JIMUTANTO_SHOZOKU_CD,");         // 事務担当者研究機関番号
        query.append(" A.JIMUTANTO_SHOZOKU_NAME,");       // 事務担当者研究機関名
        query.append(" A.JIMUTANTO_BUKYOKU_CD,");         // 事務担当者部局番号
        query.append(" A.JIMUTANTO_BUKYOKU_NAME,");       // 事務担当者部局名
        query.append(" A.JIMUTANTO_SHOKUSHU_CD,");        // 事務担当者職名番号
        query.append(" A.JIMUTANTO_SHOKUSHU_NAME_KANJI,");// 事務担当者職名（和文）
        query.append(" A.JIMUTANTO_ZIP,");                // 事務担当者（郵便番号）
        query.append(" A.JIMUTANTO_ADDRESS,");            // 事務担当者（住所）
        query.append(" A.JIMUTANTO_TEL,");                // 事務担当者（電話）
        query.append(" A.JIMUTANTO_FAX,");                // 事務担当者（FAX）
        query.append(" A.JIMUTANTO_EMAIL,");              // 事務担当者（メールアドレス）
        query.append(" A.KANREN_SHIMEI1,");               // 関連分野の研究者-氏名1
        query.append(" A.KANREN_KIKAN1,");                // 関連分野の研究者-所属研究機関1
        query.append(" A.KANREN_BUKYOKU1,");              // 関連分野の研究者-部局1
        query.append(" A.KANREN_SHOKU1,");                // 関連分野の研究者-職名1
        query.append(" A.KANREN_SENMON1,");               // 関連分野の研究者-専門分野1
        query.append(" A.KANREN_TEL1,");                  // 関連分野の研究者-勤務先電話番号1
        query.append(" A.KANREN_JITAKUTEL1,");            // 関連分野の研究者-自宅電話番号1
        query.append(" A.KANREN_SHIMEI2,");               // 関連分野の研究者-氏名2
        query.append(" A.KANREN_KIKAN2,");                // 関連分野の研究者-所属研究機関2
        query.append(" A.KANREN_BUKYOKU2,");              // 関連分野の研究者-部局2                             
        query.append(" A.KANREN_SHOKU2,");                // 関連分野の研究者-職名2
        query.append(" A.KANREN_SENMON2,");               // 関連分野の研究者-専門分野2
        query.append(" A.KANREN_TEL2,");                  // 関連分野の研究者-勤務先電話番号2
        query.append(" A.KANREN_JITAKUTEL2,");            // 関連分野の研究者-自宅電話番号2
        query.append(" A.KANREN_SHIMEI3,");               // 関連分野の研究者-氏名3
        query.append(" A.KANREN_KIKAN3,");                // 関連分野の研究者-所属研究機関3
        query.append(" A.KANREN_BUKYOKU3,");              // 関連分野の研究者-部局3                             
        query.append(" A.KANREN_SHOKU3,");                // 関連分野の研究者-職名3
        query.append(" A.KANREN_SENMON3,");               // 関連分野の研究者-専門分野3
        query.append(" A.KANREN_TEL3,");                  // 関連分野の研究者-勤務先電話番号3
        query.append(" A.KANREN_JITAKUTEL3,");            // 関連分野の研究者-自宅電話番号3
        query.append(" A.PDF_PATH,");                     // 領域計画書概要PDFの格納パス
        query.append(" A.HYOSHI_PDF_PATH,");              // 表紙PDF格納パス
        query.append(" A.JURI_KEKKA,");                   // 受理結果
        query.append(" A.JURI_BIKO,");                    // 受理結果備考
        query.append(" A.RYOIKI_JOKYO_ID,");              // 領域計画書（概要）申請状況ID                    
        query.append(" A.EDITION,");                      // 版
        query.append(" A.RYOIKIKEIKAKUSHO_KAKUTEI_FLG,"); // 確定フラグ
        query.append(" A.CANCEL_FLG,");                   // 解除フラグ
        query.append(" A.DEL_FLG ");                      // 削除フラグ
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" A");
        query.append(" WHERE A.RYOIKI_JOKYO_ID IN ('" + StatusCode.STATUS_GAKUSIN_JYURI + "')");		//応募状況：受理	2006年度のみ使う機能なのでべた書き
        query.append(" AND DEL_FLG = 0");

        //排他制御を行う場合
        if(lock){
            query.append(" FOR UPDATE");
        }

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        
        // -----------------------
        // リスト取得
        // -----------------------
        PreparedStatement ps = null;
        ResultSet result = null;

        RyoikiKeikakushoInfo[] ryoikiKeikakushoInfos = null;
        try {
            ps = connection.prepareStatement(query.toString());
            result = ps.executeQuery();

            List resultList = new ArrayList();
            while(result.next()) {
                RyoikiKeikakushoInfo info = new RyoikiKeikakushoInfo();
                info.setRyoikiSystemNo(result.getString("RYOIKI_SYSTEM_NO"));
                info.setKariryoikiNo(result.getString("KARIRYOIKI_NO"));
                info.setUketukeNo(result.getString("UKETUKE_NO").trim());		//空白がついてくるのでtrimをかける
                info.setJigyoId(result.getString("JIGYO_ID"));
                info.setJigyoCd(result.getString("JIGYO_ID").substring(2,7));
                info.setNendo(result.getString("NENDO"));
                info.setKaisu(result.getString("KAISU"));
                info.setJigyoName(result.getString("JIGYO_NAME"));
                info.setShinseishaId(result.getString("SHINSEISHA_ID"));
                info.setKakuteiDate(result.getDate("KAKUTEI_DATE"));
                info.setSakuseiDate(result.getDate("SAKUSEI_DATE"));
                info.setShoninDate(result.getDate("SHONIN_DATE"));
                info.setJyuriDate(result.getDate("JYURI_DATE"));
                info.setNameKanjiSei(result.getString("NAME_KANJI_SEI"));
                info.setNameKanjiMei(result.getString("NAME_KANJI_MEI"));
                info.setNameKanaSei(result.getString("NAME_KANA_SEI"));
                info.setNameKanaMei(result.getString("NAME_KANA_MEI"));
                info.setNenrei(result.getString("NENREI"));
                info.setKenkyuNo(result.getString("KENKYU_NO"));
                info.setShozokuCd(result.getString("SHOZOKU_CD"));
                info.setShozokuName(result.getString("SHOZOKU_NAME"));
                info.setShozokuNameRyaku(result.getString("SHOZOKU_NAME_RYAKU"));
                info.setBukyokuCd(result.getString("BUKYOKU_CD"));
                info.setBukyokuName(result.getString("BUKYOKU_NAME"));
                info.setBukyokuNameRyaku(result.getString("BUKYOKU_NAME_RYAKU"));
                info.setShokushuCd(result.getString("SHOKUSHU_CD"));
                info.setShokushuNameKanji(result.getString("SHOKUSHU_NAME_KANJI"));
                info.setShokushuNameRyaku(result.getString("SHOKUSHU_NAME_RYAKU"));
                info.setKiboubumonCd(result.getString("KIBOUBUMON_CD"));
                info.setKiboubumonName(result.getString("KIBOUBUMON_NAME"));
                info.setRyoikiName(result.getString("RYOIKI_NAME"));
                info.setRyoikiNameEigo(result.getString("RYOIKI_NAME_EIGO"));
                info.setRyoikiNameRyaku(result.getString("RYOIKI_NAME_RYAKU"));
                info.setKenkyuGaiyou(result.getString("KENKYU_GAIYOU"));
                info.setJizenchousaFlg(result.getString("JIZENCHOUSA_FLG"));
                info.setJizenchousaSonota(result.getString("JIZENCHOUSA_SONOTA"));
                info.setKakoOubojyoukyou(result.getString("KAKO_OUBOJYOUKYOU"));
                info.setZennendoOuboFlg(result.getString("ZENNENDO_OUBO_FLG"));
                info.setZennendoOuboNo(result.getString("ZENNENDO_OUBO_NO"));
                info.setZennendoOuboRyoikiRyaku(result.getString("ZENNENDO_OUBO_RYOIKI_RYAKU"));
                info.setZennendoOuboSettei(result.getString("ZENNENDO_OUBO_SETTEI"));
                info.setBunkasaimokuCd1(result.getString("BUNKASAIMOKU_CD1"));
                info.setBunyaName1(result.getString("BUNYA_NAME1"));
                info.setBunkaName1(result.getString("BUNKA_NAME1"));
                info.setSaimokuName1(result.getString("SAIMOKU_NAME1"));
                info.setBunkasaimokuCd2(result.getString("BUNKASAIMOKU_CD2"));
                info.setBunyaName2(result.getString("BUNYA_NAME2"));
                info.setBunkaName2(result.getString("BUNKA_NAME2"));
                info.setSaimokuName2(result.getString("SAIMOKU_NAME2"));
                info.setKanrenbunyaBunruiNo(result.getString("KANRENBUNYA_BUNRUI_NO"));
                info.setKanrenbunyaBunruiName(result.getString("KANRENBUNYA_BUNRUI_NAME"));
                info.setKenkyuHitsuyousei1(result.getString("KENKYU_HITSUYOUSEI_1"));
                info.setKenkyuHitsuyousei2(result.getString("KENKYU_HITSUYOUSEI_2"));
                info.setKenkyuHitsuyousei3(result.getString("KENKYU_HITSUYOUSEI_3"));
                info.setKenkyuHitsuyousei4(result.getString("KENKYU_HITSUYOUSEI_4"));
                info.setKenkyuHitsuyousei5(result.getString("KENKYU_HITSUYOUSEI_5"));
                info.setKenkyuSyokei2(result.getString("KENKYU_SYOKEI_2"));
                info.setKenkyuUtiwake2(result.getString("KENKYU_UTIWAKE_2"));
                info.setKenkyuSyokei3(result.getString("KENKYU_SYOKEI_3"));
                info.setKenkyuUtiwake3(result.getString("KENKYU_UTIWAKE_3"));
                info.setKenkyuSyokei4(result.getString("KENKYU_SYOKEI_4"));
                info.setKenkyuUtiwake4(result.getString("KENKYU_UTIWAKE_4"));
                info.setKenkyuSyokei5(result.getString("KENKYU_SYOKEI_5"));
                info.setKenkyuUtiwake5(result.getString("KENKYU_UTIWAKE_5"));
                info.setKenkyuSyokei6(result.getString("KENKYU_SYOKEI_6"));
                info.setKenkyuUtiwake6(result.getString("KENKYU_UTIWAKE_6"));
                info.setDaihyouZip(result.getString("DAIHYOU_ZIP"));
                info.setDaihyouAddress(result.getString("DAIHYOU_ADDRESS"));
                info.setDaihyouTel(result.getString("DAIHYOU_TEL"));
                info.setDaihyouFax(result.getString("DAIHYOU_FAX"));
                info.setDaihyouEmail(result.getString("DAIHYOU_EMAIL"));
                info.setJimutantoNameKanjiSei(result.getString("JIMUTANTO_NAME_KANJI_SEI"));
                info.setJimutantoNameKanjiMei(result.getString("JIMUTANTO_NAME_KANJI_MEI"));
                info.setJimutantoNameKanaSei(result.getString("JIMUTANTO_NAME_KANA_SEI"));
                info.setJimutantoNameKanaMei(result.getString("JIMUTANTO_NAME_KANA_MEI"));
                info.setJimutantoShozokuCd(result.getString("JIMUTANTO_SHOZOKU_CD"));
                info.setJimutantoShozokuName(result.getString("JIMUTANTO_SHOZOKU_NAME"));
                info.setJimutantoBukyokuCd(result.getString("JIMUTANTO_BUKYOKU_CD"));
                info.setJimutantoBukyokuName(result.getString("JIMUTANTO_BUKYOKU_NAME"));
                info.setJimutantoShokushuCd(result.getString("JIMUTANTO_SHOKUSHU_CD"));
                info.setJimutantoShokushuNameKanji(result.getString("JIMUTANTO_SHOKUSHU_NAME_KANJI"));
                info.setJimutantoZip(result.getString("JIMUTANTO_ZIP"));
                info.setJimutantoAddress(result.getString("JIMUTANTO_ADDRESS"));
                info.setJimutantoTel(result.getString("JIMUTANTO_TEL"));
                info.setJimutantoFax(result.getString("JIMUTANTO_FAX"));
                info.setJimutantoEmail(result.getString("JIMUTANTO_EMAIL"));
                info.setKanrenShimei1(result.getString("KANREN_SHIMEI1"));
                info.setKanrenKikan1(result.getString("KANREN_KIKAN1"));
                info.setKanrenBukyoku1(result.getString("KANREN_BUKYOKU1"));
                info.setKanrenShoku1(result.getString("KANREN_SHOKU1"));
                info.setKanrenSenmon1(result.getString("KANREN_SENMON1"));
                info.setKanrenTel1(result.getString("KANREN_TEL1"));
                info.setKanrenJitakutel1(result.getString("KANREN_JITAKUTEL1"));
                info.setKanrenShimei2(result.getString("KANREN_SHIMEI2"));
                info.setKanrenKikan2(result.getString("KANREN_KIKAN2"));
                info.setKanrenBukyoku2(result.getString("KANREN_BUKYOKU2"));
                info.setKanrenShoku2(result.getString("KANREN_SHOKU2"));
                info.setKanrenSenmon2(result.getString("KANREN_SENMON2"));
                info.setKanrenTel2(result.getString("KANREN_TEL2"));
                info.setKanrenJitakutel2(result.getString("KANREN_JITAKUTEL2"));
                info.setKanrenShimei3(result.getString("KANREN_SHIMEI3"));
                info.setKanrenKikan3(result.getString("KANREN_KIKAN3"));
                info.setKanrenBukyoku3(result.getString("KANREN_BUKYOKU3"));
                info.setKanrenShoku3(result.getString("KANREN_SHOKU3"));
                info.setKanrenSenmon3(result.getString("KANREN_SENMON3"));
                info.setKanrenTel3(result.getString("KANREN_TEL3"));
                info.setKanrenJitakutel3(result.getString("KANREN_JITAKUTEL3"));
                info.setPdfPath(result.getString("PDF_PATH"));
                info.setHyoshiPdfPath(result.getString("HYOSHI_PDF_PATH"));
                info.setJuriKekka(result.getString("JURI_KEKKA"));
                info.setJuriBiko(result.getString("JURI_BIKO"));
                info.setRyoikiJokyoId(result.getString("RYOIKI_JOKYO_ID"));
                info.setEdition(result.getString("EDITION"));
                info.setRyoikikeikakushoKakuteiFlg(result.getString("RYOIKIKEIKAKUSHO_KAKUTEI_FLG"));
                info.setCancelFlg(result.getString("CANCEL_FLG"));
                info.setDelFlg(result.getString("DEL_FLG"));
                resultList.add(info);
            }
            //戻り値
            ryoikiKeikakushoInfos = (RyoikiKeikakushoInfo[])resultList.toArray(new RyoikiKeikakushoInfo[0]);
            if(ryoikiKeikakushoInfos.length == 0){
                throw new NoDataFoundException(
                    "領域計画書管理テーブルに該当するデータが見つかりません。応募状況：学振受理'"
                        + Arrays.asList(ryoikiKeikakushoInfos).toString()
                        + "'");
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        finally {
            DatabaseUtil.closeResource(result, ps);
        }
        return ryoikiKeikakushoInfos;
    }
    
    
    /**
     * 領域計画書（概要）情報を取得する。
     * 削除フラグが「0」の場合（削除されていない場合）のみ検索する。
     * 取得したレコードをロックする。（comitが行われるまで。）
     * @param connection            コネクション
     * @param primaryKey            主キー情報
     * @return                      所属機関情報
     * @throws DataAccessException  データ取得中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public RyoikiKeikakushoInfo selectRyoikiKeikakushoInfoForLock(
        Connection connection,
        RyoikiKeikakushoPk primaryKey)
        throws DataAccessException, NoDataFoundException {
        
        return selectRyoikiInfo(connection, primaryKey, true);
    }
    
    /**
     * 領域計画書（概要）情報を取得する。
     * 削除フラグが「0」の場合（削除されていない場合）のみ検索する。
     * @param connection            コネクション
     * @param primaryKey            主キー情報
     * @return                      所属機関情報
     * @throws DataAccessException  データ取得中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public RyoikiKeikakushoInfo selectRyoikiKeikakushoDataInfo(
        Connection connection,
        RyoikiKeikakushoPk primaryKey)
        throws DataAccessException, NoDataFoundException {
        
        return selectRyoikiInfo(connection, primaryKey, false);
    }

    //2006.09.25 iso iso タイトルに「概要」をつけたPDF作成のため
    //※ロック無しは未実装
    /**
     * 領域計画書情報リストを取得する。
     * 応募情報が「受理」の場合のみ検索する。
     * @param connection            コネクション
     * @return                      領域計画書情報リスト
     * @throws DataAccessException  データ取得中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public RyoikiKeikakushoInfo[] selectRyoikiKeikakushoDataInfosForLock(Connection connection)
        	throws DataAccessException, NoDataFoundException {
        
        return selectRyoikiInfos(connection, false);
    }
    
    /**
     * 領域計画書（概要）情報を登録する。
     * @param connection                コネクション
     * @param addInfo                   登録する申請情報
     * @throws DataAccessException      登録中に例外が発生した場合。
     * @throws DuplicateKeyException    キーに一致するデータが既に存在する場合
     */
    public void insertRyoikiKeikakushoInfo(Connection connection,
            RyoikiKeikakushoInfo addInfo)
            throws DataAccessException, DuplicateKeyException {

        //キー重複チェック
        try {
            selectRyoikiKeikakushoDataInfo(connection, addInfo);
            //NG
            throw new DuplicateKeyException(
                "'" + addInfo + "'は既に登録されています。");
        } catch (NoDataFoundException e) {
            //OK
        }
        
        StringBuffer query = new StringBuffer();
        query.append("INSERT INTO");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink);
        query.append(" (");
        query.append(" RYOIKI_SYSTEM_NO   ");// システム受付番号
        query.append(",KARIRYOIKI_NO      ");// 仮領域番号
        query.append(",UKETUKE_NO         ");// 受付番号（整理番号）
        query.append(",JIGYO_ID           ");// 事業ID
        query.append(",NENDO              ");// 年度
        query.append(",KAISU              ");// 回数
        query.append(",JIGYO_NAME         ");// 事業名
        query.append(",SHINSEISHA_ID      ");// 申請者ID
        query.append(",KAKUTEI_DATE       ");// 領域計画書確定日
        query.append(",SAKUSEI_DATE       ");// 領域計画書概要作成日
        query.append(",SHONIN_DATE        ");// 所属機関承認日
        query.append(",JYURI_DATE         ");// 学振受理日
        query.append(",NAME_KANJI_SEI     ");// 領域代表者氏名（漢字等-姓）
        query.append(",NAME_KANJI_MEI     ");// 領域代表者氏名（漢字等-名）
        query.append(",NAME_KANA_SEI      ");// 領域代表者氏名（フリガナ-姓）
        query.append(",NAME_KANA_MEI      ");// 領域代表者氏名（フリガナ-名）
        query.append(",NENREI             ");// 年齢
        query.append(",KENKYU_NO          ");// 申請者研究者番号
        query.append(",SHOZOKU_CD         ");// 所属機関コード
        query.append(",SHOZOKU_NAME       ");// 所属機関名
        query.append(",SHOZOKU_NAME_RYAKU ");// 所属機関名（略称）
        query.append(",BUKYOKU_CD         ");// 部局コード
        query.append(",BUKYOKU_NAME       ");// 部局名
        query.append(",BUKYOKU_NAME_RYAKU ");// 部局名（略称）
        query.append(",SHOKUSHU_CD        ");// 職名コード
        query.append(",SHOKUSHU_NAME_KANJI");// 職名（和文）
        query.append(",SHOKUSHU_NAME_RYAKU");// 職名（略称）
        query.append(",KIBOUBUMON_CD      ");// 審査希望部門（系等）コード
        query.append(",KIBOUBUMON_NAME    ");// 審査希望部門（系等）名称
        query.append(",RYOIKI_NAME        ");// 応募領域名
        query.append(",RYOIKI_NAME_EIGO   ");// 英訳名
        query.append(",RYOIKI_NAME_RYAKU  ");// 領域略称名
        query.append(",KENKYU_GAIYOU      ");// 研究概要
        query.append(",JIZENCHOUSA_FLG    ");// 事前調査の状況
        query.append(",JIZENCHOUSA_SONOTA ");// 事前調査の状況（その他）
        query.append(",KAKO_OUBOJYOUKYOU  ");// 過去の応募状況
        query.append(",ZENNENDO_OUBO_FLG  ");// 最終年度前年度の応募（該当の有無）
        query.append(",ZENNENDO_OUBO_NO          ");// 最終年度前年度の研究領域番号
        query.append(",ZENNENDO_OUBO_RYOIKI_RYAKU");// 最終年度前年度の領域略称名
        query.append(",ZENNENDO_OUBO_SETTEI      ");// 最終年度前年度の設定期間
        query.append(",BUNKASAIMOKU_CD1          ");// 関連分野（細目番号）1
        query.append(",BUNYA_NAME1               ");// 関連分野（分野）1
        query.append(",BUNKA_NAME1               ");// 関連分野（分科）1
        query.append(",SAIMOKU_NAME1             ");// 関連分野（細目）1
        query.append(",BUNKASAIMOKU_CD2          ");// 関連分野（細目番号）2
        query.append(",BUNYA_NAME2               ");// 関連分野（分野）2
        query.append(",BUNKA_NAME2               ");// 関連分野（分科）2
        query.append(",SAIMOKU_NAME2             ");// 関連分野（細目）2
        query.append(",KANRENBUNYA_BUNRUI_NO     ");// 関連分野15分類（番号）
        query.append(",KANRENBUNYA_BUNRUI_NAME   ");// 関連分野15分類（分類名）
        query.append(",KENKYU_HITSUYOUSEI_1      ");// 研究の必要性1
        query.append(",KENKYU_HITSUYOUSEI_2      ");// 研究の必要性2
        query.append(",KENKYU_HITSUYOUSEI_3      ");// 研究の必要性3
        query.append(",KENKYU_HITSUYOUSEI_4      ");// 研究の必要性4
        query.append(",KENKYU_HITSUYOUSEI_5      ");// 研究の必要性5
        query.append(",KENKYU_SYOKEI_1           ");// 研究経費（1年目)-小計
        query.append(",KENKYU_UTIWAKE_1          ");// 研究経費（1年目)-内訳
        query.append(",KENKYU_SYOKEI_2           ");// 研究経費（2年目)-小計
        query.append(",KENKYU_UTIWAKE_2          ");// 研究経費（2年目)-内訳
        query.append(",KENKYU_SYOKEI_3           ");// 研究経費（3年目)-小計
        query.append(",KENKYU_UTIWAKE_3          ");// 研究経費（3年目)-内訳
        query.append(",KENKYU_SYOKEI_4           ");// 研究経費（4年目)-小計
        query.append(",KENKYU_UTIWAKE_4          ");// 研究経費（4年目)-内訳
        query.append(",KENKYU_SYOKEI_5           ");// 研究経費（5年目)-小計
        query.append(",KENKYU_UTIWAKE_5          ");// 研究経費（5年目)-内訳
        query.append(",KENKYU_SYOKEI_6           ");// 研究経費（6年目)-小計
        query.append(",KENKYU_UTIWAKE_6          ");// 研究経費（6年目)-内訳
        query.append(",DAIHYOU_ZIP                  ");// 領域代表者（郵便番号）
        query.append(",DAIHYOU_ADDRESS              ");// 領域代表者（住所）
        query.append(",DAIHYOU_TEL                  ");// 領域代表者（電話）
        query.append(",DAIHYOU_FAX                  ");// 領域代表者（FAX）
        query.append(",DAIHYOU_EMAIL                ");// 領域代表者（メールアドレス）
        query.append(",JIMUTANTO_NAME_KANJI_SEI     ");// 事務担当者氏名（漢字等-姓）
        query.append(",JIMUTANTO_NAME_KANJI_MEI     ");// 事務担当者氏名（漢字等-名）
        query.append(",JIMUTANTO_NAME_KANA_SEI      ");// 事務担当者氏名（フリガナ-姓）
        query.append(",JIMUTANTO_NAME_KANA_MEI      ");// 事務担当者氏名（フリガナ-名）
        query.append(",JIMUTANTO_SHOZOKU_CD         ");// 事務担当者研究機関番号
        query.append(",JIMUTANTO_SHOZOKU_NAME       ");// 事務担当者研究機関名
        query.append(",JIMUTANTO_BUKYOKU_CD         ");// 事務担当者部局番号
        query.append(",JIMUTANTO_BUKYOKU_NAME       ");// 事務担当者部局名
        query.append(",JIMUTANTO_SHOKUSHU_CD        ");// 事務担当者職名番号
        query.append(",JIMUTANTO_SHOKUSHU_NAME_KANJI");// 事務担当者職名（和文）
        query.append(",JIMUTANTO_ZIP                ");// 事務担当者（郵便番号）
        query.append(",JIMUTANTO_ADDRESS            ");// 事務担当者（住所）
        query.append(",JIMUTANTO_TEL                ");// 事務担当者（電話）
        query.append(",JIMUTANTO_FAX                ");// 事務担当者（FAX）
        query.append(",JIMUTANTO_EMAIL              ");// 事務担当者（メールアドレス）
        query.append(",KANREN_SHIMEI1               ");// 関連分野の研究者-氏名1
        query.append(",KANREN_KIKAN1                ");// 関連分野の研究者-所属研究機関1
        query.append(",KANREN_BUKYOKU1              ");// 関連分野の研究者-部局1
        query.append(",KANREN_SHOKU1                ");// 関連分野の研究者-職名1
        query.append(",KANREN_SENMON1               ");// 関連分野の研究者-専門分野1
        query.append(",KANREN_TEL1                  ");// 関連分野の研究者-勤務先電話番号1
        query.append(",KANREN_JITAKUTEL1            ");// 関連分野の研究者-自宅電話番号1
        query.append(",KANREN_SHIMEI2               ");// 関連分野の研究者-氏名2
        query.append(",KANREN_KIKAN2                ");// 関連分野の研究者-所属研究機関2
        query.append(",KANREN_BUKYOKU2              ");// 関連分野の研究者-部局2
        query.append(",KANREN_SHOKU2                ");// 関連分野の研究者-職名2
        query.append(",KANREN_SENMON2               ");// 関連分野の研究者-専門分野2
        query.append(",KANREN_TEL2                  ");// 関連分野の研究者-勤務先電話番号2
        query.append(",KANREN_JITAKUTEL2            ");// 関連分野の研究者-自宅電話番号2
        query.append(",KANREN_SHIMEI3               ");// 関連分野の研究者-氏名3
        query.append(",KANREN_KIKAN3                ");// 関連分野の研究者-所属研究機関3
        query.append(",KANREN_BUKYOKU3              ");// 関連分野の研究者-部局3
        query.append(",KANREN_SHOKU3                ");// 関連分野の研究者-職名3
        query.append(",KANREN_SENMON3               ");// 関連分野の研究者-専門分野3
        query.append(",KANREN_TEL3                  ");// 関連分野の研究者-勤務先電話番号3
        query.append(",KANREN_JITAKUTEL3            ");// 関連分野の研究者-自宅電話番号3
        query.append(",PDF_PATH                     ");// 領域計画書概要PDFの格納パス
        query.append(",HYOSHI_PDF_PATH              ");// 表紙PDF格納パス
        query.append(",JURI_KEKKA                   ");// 受理結果
        query.append(",JURI_BIKO                    ");// 受理結果備考
        query.append(",RYOIKI_JOKYO_ID              ");// 領域計画書（概要）申請状況ID
        query.append(",EDITION                      ");// 版
        query.append(",RYOIKIKEIKAKUSHO_KAKUTEI_FLG ");// 確定フラグ
        query.append(",CANCEL_FLG                   ");// 解除フラグ
        query.append(",DEL_FLG                      ");// 削除フラグ
        query.append(" ) ");
        query.append("VALUES");
        query.append(" (");
        query.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"); // 25個
        query.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"); // 50個
        query.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"); // 75個
        query.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"); // 100個
        query.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"); // 125個
        query.append(" )");

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query.toString());
            this.setAllParameter(preparedStatement, addInfo);           
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            log.error("領域計画書（概要）情報登録中に例外が発生しました。 ", ex);
            log.info(addInfo);
            throw new DataAccessException("領域計画書（概要）情報登録中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
//2006/06/27 苗　追加ここまで 

//2006/07/17 苗　追加ここから
    /**
     * 領域計画書（概要）キー情報より対象となるIODファイルを取得する。
     * @param connection            コネクション
     * @param pkInfo                領域計画書（概要）キー情報
     * @return                      変換するIOFファイルリスト
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List getIodFilesToMerge(Connection connection, final RyoikiKeikakushoPk pkInfo)
        throws NoDataFoundException, DataAccessException {
    
        //1.ファイル取得用SQL分
        final StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" 0 SEQ_NUM,0 SEQ_TENPU,D.PDF_PATH IOD_FILE_PATH ");
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" D ");
        query.append("WHERE D.RYOIKI_SYSTEM_NO = ? ");
        query.append("UNION ALL ");
        query.append("SELECT");
        query.append(" 1 SEQ_NUM,A.SEQ_TENPU SEQ_TENPU,A.PDF_PATH IOD_FILE_PATH ");
        query.append("FROM");
        query.append(" TENPUFILEINFO").append(dbLink).append(" A ");
        query.append("WHERE A.SYSTEM_NO = ? ");
        query.append("ORDER BY SEQ_NUM,SEQ_TENPU");

        if(log.isDebugEnabled()){
            log.debug("領域計画書（概要）キー情報 '" + pkInfo);
        }
        
        //2.検索用SQL作成オブジェクトを生成する。        
        PreparedStatementCreator creator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn)
                throws SQLException {
                PreparedStatement statement = conn.prepareStatement(query.toString());
                int i = 1;
                DatabaseUtil.setParameter(statement,i++,pkInfo.getRyoikiSystemNo());
                DatabaseUtil.setParameter(statement,i++,pkInfo.getRyoikiSystemNo());
                //システム番号
                return statement;
            }
        };
        //3.申請データ中間ファイル・添付ファイル中間ファイルを取得する。  
        final List iodFile = new ArrayList();
        
        //4.検索を実行する。
        RowCallbackHandler handler = new BaseCallbackHandler() {
            protected void processRow(ResultSet rs, int rowNum)
                throws SQLException, NoDataFoundException {
                String iodFilePath = rs.getString("IOD_FILE_PATH");
                if (iodFilePath == null || "".equals(iodFilePath)) {
                    throw new NoDataFoundException("領域計画書（概要）IODファイル情報が見つかりませんでした。検索キー：システム受付番号'"
                    + pkInfo.getRyoikiSystemNo()
                    + "'");
                } else {
                    iodFile.add(new File(iodFilePath));
                }
            }
        };
        new JDBCReading().query(connection,creator, handler);
        
        if (log.isDebugEnabled()) {
            for (Iterator iter = iodFile.iterator(); iter.hasNext();) {
                log.debug("結合対象ファイル:" + iter.next());
            }
        }
        
        //5.結合ファイルをチェックする。
        if(iodFile.isEmpty()){
            throw new NoDataFoundException("結合するファイルが見つかりませんでした。");
        }
        return iodFile;
    }
//2006/07/17　苗　追加ここまで   
    
    //2006.09.29 iso 「概要」つきPDF作成のため添付ファイルのみ取得する必要がでたので独立
    /**
     * 領域計画書（概要）キー情報より対象となるIODファイルを取得する。
     * @param connection            コネクション
     * @param pkInfo                領域計画書（概要）キー情報
     * @return                      変換するIOFファイルリスト
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List getTenpuFilesToMerge(Connection connection, final RyoikiKeikakushoPk pkInfo)
    		throws NoDataFoundException, DataAccessException {
    
        //1.ファイル取得用SQL分
        final StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" 1 SEQ_NUM,A.SEQ_TENPU SEQ_TENPU,A.PDF_PATH IOD_FILE_PATH ");
        query.append("FROM");
        query.append(" TENPUFILEINFO").append(dbLink).append(" A ");
        query.append("WHERE A.SYSTEM_NO = ? ");
        query.append("ORDER BY SEQ_NUM,SEQ_TENPU");

        if(log.isDebugEnabled()){
            log.debug("領域計画書（概要）キー情報 '" + pkInfo);
        }
        
        //2.検索用SQL作成オブジェクトを生成する。        
        PreparedStatementCreator creator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn)
                throws SQLException {
                PreparedStatement statement = conn.prepareStatement(query.toString());
                int i = 1;
                DatabaseUtil.setParameter(statement,i++,pkInfo.getRyoikiSystemNo());
                //システム番号
                return statement;
            }
        };
        //3.申請データ中間ファイル・添付ファイル中間ファイルを取得する。  
        final List iodFile = new ArrayList();
        
        //4.検索を実行する。
        RowCallbackHandler handler = new BaseCallbackHandler() {
            protected void processRow(ResultSet rs, int rowNum)
                throws SQLException, NoDataFoundException {
                String iodFilePath = rs.getString("IOD_FILE_PATH");
                if (iodFilePath == null || "".equals(iodFilePath)) {
                    throw new NoDataFoundException("領域計画書（概要）IODファイル情報が見つかりませんでした。検索キー：システム受付番号'"
                    + pkInfo.getRyoikiSystemNo()
                    + "'");
                } else {
                    iodFile.add(new File(iodFilePath));
                }
            }
        };
        new JDBCReading().query(connection,creator, handler);
        
        if (log.isDebugEnabled()) {
            for (Iterator iter = iodFile.iterator(); iter.hasNext();) {
                log.debug("結合対象ファイル:" + iter.next());
            }
        }
        
        //5.結合ファイルをチェックする。
        if(iodFile.isEmpty()){
            throw new NoDataFoundException("結合するファイルが見つかりませんでした。");
        }
        return iodFile;
    }
    
    //add start ly 2006/07/31
    /**
     * 発行した仮領域番号が既に存在するかチェックする。
     * @param connection コネクション
     * @param ryoikikeikakushoInfo
     * @return int
     * @throws DataAccessException
     */
    public int KariryoikiNoCount(Connection connection,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo)
            throws DataAccessException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" COUNT(*) ");
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO R ");
        query.append("WHERE");
        query.append(" R.KARIRYOIKI_NO = ? ");
        
        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        // DB接続
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            preparedStatement.setString(i++, ryoikikeikakushoInfo
                    .getKariryoikiNo());
            recordSet = preparedStatement.executeQuery();
            int count = 0;
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
            return count;
        }
        catch (SQLException ex) {
            throw new DataAccessException(
                    "発行した仮領域番号が既に存在するかチェック検索実行中に例外が発生しました。", ex);
        }
        finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    //add end ly 2006/07/31
    
    // 2006/10/24 add by liucy start
    /**
     * 領域計画書情報を削除する。(物理削除) 事業IDに紐づく申請データを全て削除する。 該当データが存在しなかった場合は何も処理しない。
     * 
     * @param connection コネクション
     * @param jigyoId 検索条件（事業ID）
     * @throws DataAccessException 削除中に例外が発生した場合
     */
    public void deleteRyoikiKeikakushoInfo(Connection connection, String jigyoId)
            throws DataAccessException {

        String query = "DELETE FROM RYOIKIKEIKAKUSHOINFO" + dbLink + " WHERE"
                + " JIGYO_ID = ?";

        PreparedStatement preparedStatement = null;
        try {
            // 登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, jigyoId); // 検索条件（事業ID）
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("領域計画書情報削除中（物理削除）に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * ローカルに存在する該当レコードの内容をDBLink先のテーブルに挿入する。 DBLink先に同じレコードがある場合は、予め削除しておくこと。
     * DBLinkが設定されていない場合はエラーとなる。
     * 
     * @param connection
     * @param jigyoId
     * @throws DataAccessException
     */
    public void copy2HokanDB(Connection connection, String jigyoId)
            throws DataAccessException {

        // DBLink名がセットされていない場合
        if (dbLink == null || dbLink.length() == 0) {
            throw new DataAccessException("DBリンク名が設定されていません。DBLink=" + dbLink);
        }

        String query = "INSERT INTO RYOIKIKEIKAKUSHOINFO" + dbLink
                + " SELECT * FROM RYOIKIKEIKAKUSHOINFO WHERE JIGYO_ID = ?";
        PreparedStatement preparedStatement = null;
        try {
            // 登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, jigyoId); // 検索条件（事業ID）
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("領域計画書データ管理テーブル保管中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
    // add end 2006/10/24
    
//2007/02/06 苗　追加ここから
    /**
     * 整理番号のカウントを返す。
     * @param connection コネクション
     * @param ryoikikeikakushoInfo
     * @return int
     * @throws DataAccessException
     */
    public int countUketukeNo(Connection connection,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo)
            throws DataAccessException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" COUNT(UKETUKE_NO)");
        query.append(" FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        query.append("WHERE");
        query.append(" R.JIGYO_ID = ? ");
        query.append(" AND R.UKETUKE_NO = ? ");
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        //DB接続
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, ryoikikeikakushoInfo.getJigyoId());
            DatabaseUtil.setParameter(preparedStatement, i++, ryoikikeikakushoInfo.getUketukeNo()); 
            
            recordSet = preparedStatement.executeQuery();
            int count = 0;
            if (recordSet.next()){
                count =  recordSet.getInt(1);
            }
            
            return count;
             
        } catch (SQLException ex) {
            throw new DataAccessException("領域計画書（概要）情報管理テーブル検索実行中に例外が発生しました。", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2007/02/06　苗　追加ここまで    
}