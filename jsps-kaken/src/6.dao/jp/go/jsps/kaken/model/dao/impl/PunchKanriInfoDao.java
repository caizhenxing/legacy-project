/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（JSPS)
 *    Source name : PunchKanriInfoDao.java
 *    Description : パンチデータ管理の為、DBアクセスのクラス
 *
 *    Author      : Admin
 *    Date        : 2004/11/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/11/02    V1.0                       新規作成
 *    2005/05/31    V1.1        向　　　　　　　特定領域パンチデータ出力追加
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.PunchDataKanriInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.CSVLine;
import jp.go.jsps.kaken.util.HanZenConverter;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * パンチ管理データアクセスクラス。 ID RCSfile="$RCSfile: PunchKanriInfoDao.java,v $"
 * Revision="$Revision: 1.6 $" Date="$Date: 2007/07/25 10:18:04 $"
 */
public class PunchKanriInfoDao {

    //---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------

    /** ログ */
    protected static final Log log = LogFactory.getLog(PunchKanriInfoDao.class);

    private static final String LINE_SHIFT = "\r\n";

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
     * 
     * @param userInfo 実行するユーザ情報
     */
    public PunchKanriInfoDao(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    //---------------------------------------------------------------------
    // Public Methods
    //---------------------------------------------------------------------
    /**
     * パンチデータ情報の一覧を取得する。
     * 
     * @param connection コネクション
     * @param userInfo
     * @return パンチデータ情報
     * @throws ApplicationException
     */
    public List selectList(Connection connection, UserInfo userInfo)
            throws ApplicationException {

        //検索条件
        String addQuery = null;

        //業務担当者の場合は自分が担当する事業区分のみ。
        if (userInfo.getRole().equals(UserRole.GYOMUTANTO)) {
            Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoKubun().iterator();
            String tantoJigyoKubun = StringUtil.changeIterator2CSV(ite, true);
            addQuery = new StringBuffer(" WHERE A.JIGYO_KUBUN IN (")
            							.append(tantoJigyoKubun)
            							.append(")").toString();
        } else {
            addQuery = "";
        }

        //-----------------------
        // SQL文の作成
        //-----------------------
        String query = "SELECT "
                + " A.PUNCH_SHUBETU " //パンチデータ種別
                + ",A.PUNCH_NAME " //パンチデータ名称
                + ",A.JIGYO_KUBUN " //事業区分
                + ",TO_CHAR(A.SAKUSEI_DATE,'YYYY/MM/DD HH24:MI:SS') SAKUSEI_DATE" //作成日時
                + ",A.PUNCH_PATH " //パンチデータファイルパス
                //ソート順の不具合を修正する 2006/10/3
                + ",TO_CHAR(A.PUNCH_SHUBETU,'00') SORTNO"
                //+ "FROM PUNCHKANRI A" + addQuery + " ORDER BY A.PUNCH_SHUBETU";
                + " FROM PUNCHKANRI A" + addQuery + " ORDER BY SORTNO";

        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        //-----------------------
        // リスト取得
        //-----------------------
        try {
            return SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException("パンチデータ管理テーブル検索中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("パンチデータ管理テーブルに1件もデータがありません。", e);
        }
    }

    /**
     * パンチ管理情報を取得する。
     * 
     * @param connection コネクション
     * @param punchShubetu パンチ種別（主キー情報）
     * @return パンチデータ管理情報
     * @throws NoDataFoundException データが見つからなかった場合
     * @throws DataAccessException データベースアクセス中にエラーが発生した場合
     */
    public PunchDataKanriInfo selectPunchKanriInfo(Connection connection,
            String punchShubetu) throws NoDataFoundException,
            DataAccessException {

        //-----------------------
        // SQL文の作成
        //-----------------------
        String query = "SELECT " + " A.PUNCH_SHUBETU " //パンチデータ種別
                + ",A.PUNCH_NAME " //パンチデータ名称
                + ",A.JIGYO_KUBUN " //事業区分
                + ",A.SAKUSEI_DATE " //作成日時
                + ",A.PUNCH_PATH " //パンチデータファイルパス
                + "FROM PUNCHKANRI A" + " WHERE PUNCH_SHUBETU = ?";

        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        //-----------------------
        // パンチデータ情報取得
        //-----------------------
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            PunchDataKanriInfo result = new PunchDataKanriInfo();
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, punchShubetu);
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                result.setPunchShubetu(recordSet.getString("PUNCH_SHUBETU"));
                result.setPunchName(recordSet.getString("PUNCH_NAME"));
                result.setJigyoKubun(recordSet.getString("JIGYO_KUBUN"));
                result.setSakuseiDate(recordSet.getDate("SAKUSEI_DATE"));
                result.setPunchPath(recordSet.getString("PUNCH_PATH"));
                return result;
            } else {
                throw new NoDataFoundException("パンチデータ管理テーブルに該当するデータが見つかりません"
                        + punchShubetu + "'");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("パンチデータ管理テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

    /**
     * パンチ管理テーブルの情報を更新する。 SAKUSEI_DATEについては、データベースのシステム日時で更新する。
     * 
     * @param connection コネクション
     * @param dataInfo 更新するパンチデータ管理情報
     * @throws DataAccessException 更新中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public void update(Connection connection, PunchDataKanriInfo dataInfo)
            throws DataAccessException, NoDataFoundException {

        String query = "UPDATE PUNCHKANRI"
        	           + " SET PUNCH_SHUBETU = ?"
        	           	   + ",PUNCH_NAME = ?" 
        			       + ",JIGYO_KUBUN = ?"
        			       + ",SAKUSEI_DATE = SYSDATE" 
        			       + ",PUNCH_PATH = ?" 
        		     + " WHERE PUNCH_SHUBETU = ? ";

        PreparedStatement preparedStatement = null;
        try {
            //パンチ管理テーブル更新
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getPunchShubetu());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getPunchName());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getJigyoKubun());
            //DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getSakuseiDate());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getPunchPath());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getPunchShubetu());
            DatabaseUtil.executeUpdate(preparedStatement);

        } catch (SQLException ex) {
            throw new DataAccessException("パンチデータ管理テーブル更新中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * 特別推進研究応募情報パンチデータを作成する
     * 
     * @param connection
     * @return パンチデータ情報(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataTokushi(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

        String query = "SELECT "
        			//+ ",LPAD('01',2,'0') KENKYUSHUMOKU" 					//研究種目番号(2バイト)
            		+ " NVL(A.SHINSEI_KUBUN,' ') SHINSEIKUBUN " 			//新規継続区分(1バイト)
	                + ",A.SHOZOKU_CD SHOZOKU_CD_DAIHYO" 					//所属機関コード(5バイト)
	                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER" 	//整理番号(4バイト)申請番号のハイフン以下
//UPDATE　START 2007/07/25 BIS 金京浩   //出力ファイル仕様20070720.xlsについて                 
	                /*
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.KEI_NAME_NO,' ') END KEINAMENO" 					//系等の区分番号(1バイト)
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //分担金の有無
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" 	//1年目研究経費(7バイト)
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" 	//2年目研究経費(7バイト)
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" 	//3年目研究経費(7バイト)
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" 	//4年目研究経費(7バイト)
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" 	//5年目研究経費(7バイト)
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_KEIZOKU,'        ') END KADAINOKEIZOKU" //継続分の研究課題番号（8バイト）
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD('　',80,'　') ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI" //研究課題名（和文）（80バイト）
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,'0') END KENKYUNINZU" 	//研究者数（2バイト）
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(TO_CHAR(A.SHINSEI_FLG_NO),' ') END SHINSEIFLGNO" //研究計画最終年度前年度の応募（1バイト）
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD(' ',8) ELSE LPAD(NVL(A.KADAI_NO_SAISYU,' '),8) END KADAINOSAISYU" //最終年度課題番号（8バイト）
	                */
	                
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.KEI_NAME_NO,' ') END KEINAMENO" 					//系等の区分番号(1バイト)
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //分担金の有無
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" 	//1年目研究経費(7バイト)
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" 	//2年目研究経費(7バイト)
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" 	//3年目研究経費(7バイト)
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" 	//4年目研究経費(7バイト)
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" 	//5年目研究経費(7バイト)
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_KEIZOKU,'        ') END KADAINOKEIZOKU" //継続分の研究課題番号（8バイト）
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD('　',80,'　') ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI" //研究課題名（和文）（80バイト）
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,'0') END KENKYUNINZU" 	//研究者数（2バイト）
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(TO_CHAR(A.SHINSEI_FLG_NO),' ') END SHINSEIFLGNO" //研究計画最終年度前年度の応募（1バイト）
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD(' ',8) ELSE LPAD(NVL(A.KADAI_NO_SAISYU,' '),8) END KADAINOSAISYU" //最終年度課題番号（8バイト）
	                	                
//UPDATE　END　 2007/07/25 BIS 金京浩	                
	                + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG" 		//代表者分担者別(1バイト)
					//2005.10.17 kainuma追加
					+ ",RPAD(B.NAME_KANA_SEI,32,'　') NAMEKANASEI"	//氏名（フリガナー姓）(32バイト)

	                //2005.12.22 iso NULLだった時のバグ修正
//					+ ",RPAD(B.NAME_KANA_MEI,32,'　') NAMEKANAMEI"	//氏名（フリガナー名）(32バイト)
					+ ",RPAD(NVL(B.NAME_KANA_MEI, '　'),32,'　') NAMEKANAMEI"	//氏名（フリガナー名）(32バイト)
					+ ",RPAD(B.NAME_KANJI_SEI,32,'　') NAMEKANJISEI"	//氏名（漢字等ー姓）(32バイト)
	                //2005.12.22 iso NULLだった時のバグ修正
//					+ ",RPAD(B.NAME_KANJI_MEI,32,'　') NAMEKANJIMEI"	//氏名（漢字等ー名）(32バイト)
					+ ",RPAD(NVL(B.NAME_KANJI_MEI, '　'),32,'　') NAMEKANJIMEI"	//氏名（漢字等ー名）(32バイト)
					
					//追加以上
	                + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD" 	//所属機関名(コード)研究組織表管理テーブルより(5バイト)
	                + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD " 	//部局名(コード)(3バイト)
	                + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD"	//職名コード(2バイト)
	                + ",LPAD(B.KENKYU_NO, 8,' ') KENKYUNO" 		//研究者番号(8バイト)
	                + ",LPAD(B.KEIHI, 7,'0') KEIHI" 			//研究経費(7バイト)
	                + ",LPAD(B.EFFORT, 3,'0') EFFORT" 			//エフォート(3バイト)
	                + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
	                + " WHERE A.DEL_FLG = 0"
	                + " AND A.SYSTEM_NO = B.SYSTEM_NO"
	                + " AND A.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_TOKUSUI
	                + " AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)"
	                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.UKETUKE_NO, SEIRINUMBER, BUNTAN_FLG, B.SEQ_NO"; //事業ＩＤ順、機関ー整理番号順、代表者分担者フラグ順、シーケンス番号順
        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = new ArrayList();
//        List finalList = new ArrayList();

        //-----------------------
        // リスト取得
        //-----------------------
        try {
            list = SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException("データベースアクセス中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4000"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("申請データテーブルに1件もデータがありません。", e);
        }

        //最終的にreturnするStringを宣言する
//        String finalResult = new String();
        StringBuffer finalResult = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {

            //DBから取得したList型listをString型にして、そのStringのListを作る
            Map recordMap = (Map) list.get(i);
            String kenkyuShumoku = "01";		//研究種目番号(2バイト)
            String shinseiKubun = (String) recordMap.get("SHINSEIKUBUN");
            String syozokuCdDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");
            String keiNameNo = (String) recordMap.get("KEINAMENO");
            String buntankinFlg = (String) recordMap.get("BUNTANKINFLG");
            String keihi1 = (String) recordMap.get("KEIHI1");
            String keihi2 = (String) recordMap.get("KEIHI2");
            String keihi3 = (String) recordMap.get("KEIHI3");
            String keihi4 = (String) recordMap.get("KEIHI4");
            String keihi5 = (String) recordMap.get("KEIHI5");
            String kadaiNoKeizoku = (String) recordMap.get("KADAINOKEIZOKU");
            String kadaiNameKanji = (String) recordMap.get("KADAINAMEKANJI");
            String kenkyuNinzu = (String) recordMap.get("KENKYUNINZU");
            String shinseiFlgNo = (String) recordMap.get("SHINSEIFLGNO");
            String kadaiNoSaisyu = (String) recordMap.get("KADAINOSAISYU");
            String buntanFlg = (String) recordMap.get("BUNTANFLG");
			//2005.10.17　kainuma追加
			String nameKanaSei = (String)recordMap.get("NAMEKANASEI");
			String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");
			String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");
			String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");
			//追加以上
            String shozokuCd = (String) recordMap.get("SHOZOKUCD");
            String bukyokuNo = (String) recordMap.get("BUKYOKUCD");
            String shokusyuCd = (String) recordMap.get("SHOKUSHUCD");
            String kenkyuNo = (String) recordMap.get("KENKYUNO");
            String keihi = (String) recordMap.get("KEIHI");
            String effort = (String) recordMap.get("EFFORT");

            //DBより取得したレコードをGCの対象にする（大量データに対応するため）
            recordMap = null;
            list.set(i, null);
            
            finalResult.append(kenkyuShumoku)
					   .append(shinseiKubun)
					   .append(syozokuCdDaihyo)
	                   .append(seiriNumber)
	                   .append(keiNameNo)
	                   .append(buntankinFlg)
	                   .append(keihi1)
	                   .append(keihi2)
	                   .append(keihi3)
	                   .append(keihi4)
	                   .append(keihi5)
	                   .append(kadaiNoKeizoku)
	                   .append(kadaiNameKanji)
	                   .append(kenkyuNinzu)
	                   .append(shinseiFlgNo)
	                   .append(kadaiNoSaisyu)
					   .append(buntanFlg)
						//2005.10.17 kainuma追加
						.append(nameKanaSei)
						.append(nameKanaMei)
						.append(nameKanjiSei)
						.append(nameKanjiMei)
						//追加以上
	                   .append(shozokuCd)
					   .append(bukyokuNo)
	                   .append(shokusyuCd)
					   .append(kenkyuNo)
	                   .append(keihi)
	                   .append(effort)
	                   .append(LINE_SHIFT);

//            finalList.add(record);
//            finalResult = finalResult + (String) finalList.get(i);
        }
        return finalResult.toString();
    }
    
    /**
     * 学術創成研究費パンチデータを作成する
     * 
     * @param connection
     * @return パンチデータ情報(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataGakuso(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

        String query = "SELECT "
                //+ " '52' SHUMOKUNO" 									//研究種目番号（2バイト）
                + " A.SHOZOKU_CD SHOZOKU_CD_DAIHYO" 					//所属機関コード(5バイト)
                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER"	//整理番号(4バイト)申請番号のハイフン以下
//UPDATE　START 2007/07/25 BIS 金京浩   //出力ファイル仕様20070720.xlsについて                 
                /*
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" 	//1年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" 	//2年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" 	//3年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" 	//4年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" 	//5年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE A.KEI_NAME_NO END KEINAMENO" 			//系等の区分番号（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '    ' ELSE A.BUNKASAIMOKU_CD END BUNKASAIMOKUCD" //細目コード（4バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD('　',80,'　') ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI" //研究課題名（和文）（80バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,'0') END KENKYUNINZU" //研究者数（2バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //分担金の有無
                */
                
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" 	//1年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" 	//2年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" 	//3年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" 	//4年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" 	//5年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE A.KEI_NAME_NO END KEINAMENO" 			//系等の区分番号（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '    ' ELSE A.BUNKASAIMOKU_CD END BUNKASAIMOKUCD" //細目コード（4バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD('　',80,'　') ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI" //研究課題名（和文）（80バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,'0') END KENKYUNINZU" //研究者数（2バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //分担金の有無
                
//UPDATE　END　 2007/07/25 BIS 金京浩                 
                + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG" 		//代表者分担者別(1バイト)
                //2005.10.17 kainuma追加
                + ",RPAD(B.NAME_KANA_SEI,32,'　') NAMEKANASEI"	//氏名（フリガナー姓）(32バイト)
                
                //2005.12.22 iso NULLだった時のバグ修正
//				+ ",RPAD(B.NAME_KANA_MEI,32,'　') NAMEKANAMEI"	//氏名（フリガナー名）(32バイト)
				+ ",RPAD(NVL(B.NAME_KANA_MEI, '　'),32,'　') NAMEKANAMEI"	//氏名（フリガナー名）(32バイト)
				+ ",RPAD(B.NAME_KANJI_SEI,32,'　') NAMEKANJISEI"	//氏名（漢字等ー姓）(32バイト)
                //2005.12.22 iso NULLだった時のバグ修正
//				+ ",RPAD(B.NAME_KANJI_MEI,32,'　') NAMEKANJIMEI"	//氏名（漢字等ー名）(32バイト)
				+ ",RPAD(NVL(B.NAME_KANJI_MEI, '　'),32,'　') NAMEKANJIMEI"	//氏名（漢字等ー名）(32バイト)
				
				//追加以上
                + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD" 	//所属機関名(コード)研究組織表管理テーブルより(5バイト)
                + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD " 	//部局名(コード)(3バイト)
                + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD"	//職名コード(2バイト)
                + ",LPAD(B.KENKYU_NO, 8,' ') KENKYUNO" 		//研究者番号(8バイト)
                + ",LPAD(B.KEIHI, 7,'0') KEIHI" 			//研究経費(7バイト)
                + ",LPAD(B.EFFORT, 3,'0') EFFORT" 			//エフォート(3バイト)
                + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
                + " WHERE A.DEL_FLG = 0"
                + " AND A.SYSTEM_NO = B.SYSTEM_NO"
                + " AND A.JIGYO_KUBUN IN (" + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO + "," + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO + ")"
                + " AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, SEIRINUMBER, BUNTAN_FLG, B.SEQ_NO"; //事業ID,機関ー整理番号順、代表者分担者フラグ順、シーケンス番号順
        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = new ArrayList();
//        List finalList = new ArrayList();

        //-----------------------
        // リスト取得
        //-----------------------
        try {
            list = SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException("データベースアクセス中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("申請データテーブルに1件もデータがありません。", e);
        }

        //最終的にreturnするStringを宣言する
//        String finalResult = new String();
        StringBuffer finalResult = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {

            //DBから取得したList型listをString型にして、そのStringのListを作る
            Map recordMap = (Map) list.get(i);
            String jigyoId = "52"; 			//研究種目番号
            String syozokuCdDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");
            String keihi1 = (String) recordMap.get("KEIHI1");
            String keihi2 = (String) recordMap.get("KEIHI2");
            String keihi3 = (String) recordMap.get("KEIHI3");
            String keihi4 = (String) recordMap.get("KEIHI4");
            String keihi5 = (String) recordMap.get("KEIHI5");
            String keinameno = (String) recordMap.get("KEINAMENO");
            String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKUCD");
            String kadaiNameKanji = (String) recordMap.get("KADAINAMEKANJI");
            String kenkyuNinzu = (String) recordMap.get("KENKYUNINZU");
            String buntanKinFlg = (String) recordMap.get("BUNTANKINFLG");
            String buntanFlg = (String) recordMap.get("BUNTANFLG");
            //2005.10.17　kainuma追加
            String nameKanaSei = (String)recordMap.get("NAMEKANASEI");
			String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");
			String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");
			String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");
			//追加以上
            String syozokuCd = (String) recordMap.get("SHOZOKUCD");
            String bukyokuCd = (String) recordMap.get("BUKYOKUCD");
            String syokusyuCd = (String) recordMap.get("SHOKUSHUCD");
            String kenkyuNo = (String) recordMap.get("KENKYUNO");
            String keihi = (String) recordMap.get("KEIHI");
            String effort = (String) recordMap.get("EFFORT");

            //DBより取得したレコードをGCの対象にする（大量データに対応するため）
            recordMap = null;
            list.set(i, null);
            
            finalResult.append(jigyoId)
					   .append(syozokuCdDaihyo)
					   .append(seiriNumber)
					   .append(keihi1)
					   .append(keihi2)
					   .append(keihi3)
					   .append(keihi4)
					   .append(keihi5)
					   .append(keinameno)
					   .append(bunkaSaimokuCd)
					   .append(kadaiNameKanji)
					   .append(kenkyuNinzu)
					   .append(buntanKinFlg)
					   .append(buntanFlg)
					   //2005.10.17 kainuma追加
					   .append(nameKanaSei)
					   .append(nameKanaMei)
					   .append(nameKanjiSei)
					   .append(nameKanjiMei)
					   //追加以上
					   .append(syozokuCd)
					   .append(bukyokuCd)
					   .append(syokusyuCd)
					   .append(kenkyuNo)
					   .append(keihi)
					   .append(effort)
					   .append(LINE_SHIFT);

//            finalList.add(record);
//            finalResult = finalResult + (String) finalList.get(i);

        }
        return finalResult.toString();
    }

    /**
     * 評定表パンチデータを作成する
     * 
     * @param connection
     * @return パンチデータ情報(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataHyotei(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

        String query = "SELECT "
				+ "SUBSTR(A.JIGYO_ID,5,2) KENKYUSHUMOKU"					//研究種目番号（2バイト)
				//2007/5/10 若手Sを追加した為修正
				//+ ",NVL(A.SHINSA_KUBUN,'1') SHINSA_KUBUN" 				//審査区分(1バイト)  
				+ ",SUBSTR(A.JIGYO_ID,7,1) SHINSA_KUBUN" 					//審査区分(1バイト)
				//2007/5/10 end
                + ",NVL(SUBSTR(B.UKETUKE_NO,1,5),'     ') SHOZOKUCDDAIHYO"	//所属機関コード(5バイト)
				+ ",NVL(A.BUNKASAIMOKU_CD,'    ') BUNKASAIMOKUCD" 			//細目コード（4バイト）
				+ ",CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
				+ "      WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"	//分割番号（1バイト）
//UPDATE　START 2007/07/25 BIS 金京浩   //出力ファイル仕様20070720.xlsについて         				
				/*
				+ ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
				+ "      WHEN A.BUNKATSU_NO = '2' THEN '2' ELSE ' ' END BUNKATSUNO_12"	//分割番号（1バイト）
				*/
				
				+ ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
				+ " 	 WHEN A.BUNKATSU_NO = '2' THEN '2' "
				+ " 	 WHEN A.BUNKATSU_NO = '3' THEN '3' "
				+ " 	 WHEN A.BUNKATSU_NO = '4' THEN '4' "
				+ "      WHEN A.BUNKATSU_NO = '5' THEN '5' ELSE ' ' END BUNKATSUNO_12"	//分割番号（1バイト）
				
////UPDATE　END　 2007/07/25 BIS 金京浩
				+ ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER" 					//整理番号(4バイト)申請番号のハイフン以下
                + ",NVL(SUBSTR(B.SHINSAIN_NO,6,2), '  ') SHINSAINNO" 					//審査員枝番(審査員番号の下２桁）（2バイト）
				+ ",CASE WHEN B.RIGAI = '1' THEN '1' ELSE ' '  END RIGAIKANKEI" 		//利害関係
				+ ",NVL(B.JUYOSEI, ' ') JUYOSEI" 										//研究課題の学術的重要性
				+ ",NVL(B.KENKYUKEIKAKU, ' ') KENKYUKEIKAKU"							//研究計画・方法の妥当性
				+ ",NVL(B.DOKUSOSEI, ' ') DOKUSOSEI"									//独創性及び革新性
				+ ",NVL(B.HAKYUKOKA, ' ') HAKYUKOKA" 									//波及効果
				+ ",NVL(B.SUIKONORYOKU, ' ') SUIKONORYOKU" 								//遂行能力
				+ ",CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '111') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_HOGA"	//適切性・萌芽
				+ ",CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '043')OR(SUBSTR(A.JIGYO_ID,5,3) = '053') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_KAIGAI"	//適切性・萌芽
				//2007/5/10 追加
				+ ",NVL(B.TEKISETSU_WAKATES, ' ') WAKATES"								//若手Sとしての妥当性
				//2007/5/10 追加完了
				+ ",CASE WHEN B.KEKKA_TEN = '-' THEN ' ' ELSE NVL(B.KEKKA_TEN, ' ') END KEKKATEN"	//総合評価
                + ",CASE WHEN B.JINKEN = '3' THEN '3' ELSE ' ' END JINKEN" 							//人権
                + ",CASE WHEN B.BUNTANKIN = '3' THEN '3' ELSE ' ' END BUNTANKIN" 					//分担金
                + ",CASE WHEN B.DATO = '0' THEN ' ' ELSE NVL(B.DATO, ' ') END DATO" 				//経費の妥当性
                + ",CASE WHEN B.COMMENTS IS NULL THEN '　' ELSE TO_MULTI_BYTE(B.COMMENTS) END COMMENTS" //審査意見
				+ ",CASE WHEN B.OTHER_COMMENT IS NULL THEN '　' ELSE TO_MULTI_BYTE(B.OTHER_COMMENT) END OTHER_COMMENT" //コメント
				+ ",NVL(B.SHINSA_JOKYO, '0') SHINSAJOKYO" 											//審査状況
                + " FROM SHINSEIDATAKANRI A, SHINSAKEKKA B"
                + " WHERE B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN
                + "   AND NOT B.SHINSAIN_NO LIKE '@%'"	//ダミー審査員を除外する(ダミー審査員番号が＠付いてる)
                + "   AND A.SYSTEM_NO = B.SYSTEM_NO" 
                + "   AND A.JIGYO_KUBUN = B.JIGYO_KUBUN "
				+ "	  AND A.JIGYO_KUBUN IN (" + IJigyoKubun.JIGYO_KUBUN_KIBAN +  ")"
	//			
                + "   AND A.DEL_FLG = 0"
                + "   AND TO_NUMBER(A.JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY KENKYUSHUMOKU, SHINSA_KUBUN, SHOZOKUCDDAIHYO, BUNKASAIMOKUCD, A.BUNKATSU_NO, SHINSAINNO, SEIRINUMBER"; //所属機関コード、,整理番号、審査員枝番

        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = new ArrayList();
        //List finalList = new ArrayList();

        //-----------------------
        // リスト取得
        //-----------------------
        try {
            list = SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException("データベースアクセス中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("データベースに1件もデータがありません。", e);
        }

        //最終的にreturnするStringを宣言する
        StringBuffer finalResult = new StringBuffer();
        char charToBeAdded = '　'; //全角スペース
        final int commentLength = 1000;	//150; //コメントの表示文字数
			   int otherCommentLength = 50; //コメントの表示文字数
        final char HOSHI = '★';

        //半角全角コンバータ
        HanZenConverter hanZenConverter = new HanZenConverter();

        for (int i = 0; i < list.size(); i++) {

            //long start = System.currentTimeMillis();

            //DBから取得したList型listをString型にして、そのStringのListを作る
            Map recordMap = (Map) list.get(i);
            String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //研究種目番号
			String shinsaKbn = (String) recordMap.get("SHINSA_KUBUN");
            String shozokuCd = (String) recordMap.get("SHOZOKUCDDAIHYO");
			String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKUCD");
			String bunkatsNoAB = (String) recordMap.get("BUNKATSUNO_AB");
			String bunkatsNo12 = (String) recordMap.get("BUNKATSUNO_12");
			String seiriNumber = (String) recordMap.get("SEIRINUMBER");
			String shinsainNo = (String) recordMap.get("SHINSAINNO");
			String rigai = (String) recordMap.get("RIGAIKANKEI");
			String juyosei = (String) recordMap.get("JUYOSEI");
			String kenkyuKeikaku = (String) recordMap.get("KENKYUKEIKAKU");
			String dokusosei = (String) recordMap.get("DOKUSOSEI");
			String hakyukoka = (String) recordMap.get("HAKYUKOKA");
			String suikonoryoku = (String) recordMap.get("SUIKONORYOKU");
			String tekisetsuHoga = (String) recordMap.get("TEKISETU_HOGA");
			String tekisetsuKaigai = (String) recordMap.get("TEKISETU_KAIGAI");
			String wakates = (String) recordMap.get("WAKATES");	//2007/5/10 追加
            String kekkaTen = (String) recordMap.get("KEKKATEN");            
            String jinken = (String) recordMap.get("JINKEN");
            String buntankin = (String) recordMap.get("BUNTANKIN");
            String dato = (String) recordMap.get("DATO");
            String recordComments = (String) recordMap.get("COMMENTS");
			String recordOtherComment = (String) recordMap.get("OTHER_COMMENT");
            String shinsaJokyo = (String) recordMap.get("SHINSAJOKYO");

            //DBより取得したレコードをGCの対象にする（大量データに対応するため）
            recordMap = null;
            list.set(i, null);

            //---意見を半角から全角にする
            //charの配列に変換
            String ZenkakuComments = hanZenConverter.convert(recordComments);
            char[] cArray = ZenkakuComments.toCharArray();
            for (int j = 0; j < cArray.length; j++) {
                if (StringUtil.isHankaku(cArray[j])) {
                    cArray[j] = HOSHI; //半角の場合は置き換え
                }

            }
            //文字列に戻す
            String checkedZenkakuComments = new String(cArray);
            if (checkedZenkakuComments.length() > commentLength){
            	checkedZenkakuComments = checkedZenkakuComments.substring(0, commentLength);
            }

            //意見を150文字の文字の固定長にする
            while (checkedZenkakuComments.length() < commentLength) {
                checkedZenkakuComments += charToBeAdded;
            }
			//意見ここまで


			//---コメントを半角から全角にする
			//charの配列に変換
			String ZenOtherComment = hanZenConverter.convert(recordOtherComment);
			char[] ocArray = ZenOtherComment.toCharArray();
			for (int j = 0; j < ocArray.length; j++) {
				if (StringUtil.isHankaku(ocArray[j])) {
					ocArray[j] = HOSHI; //半角の場合は置き換え
				}

			}
			//文字列に戻す
			String checkedZenOtherComment = new String(ocArray);
			if (checkedZenOtherComment.length() > otherCommentLength){
				checkedZenOtherComment = checkedZenOtherComment.substring(0, otherCommentLength);
			}

			//コメントを50文字の文字の固定長にする
			while (checkedZenOtherComment.length() < otherCommentLength) {
				checkedZenOtherComment += charToBeAdded;
			}

            //最終結果にレコード（本行）を文字列結合
            finalResult.append(jigyoId)
					   .append(shinsaKbn)
            		   .append(shozokuCd)
					   .append(bunkaSaimokuCd)
					   .append(bunkatsNoAB)
					   .append(bunkatsNo12)
					   .append(seiriNumber)
            		   .append(shinsainNo)
					   .append(rigai)
				  	   .append(juyosei)
					   .append(kenkyuKeikaku)
					   .append(dokusosei)
					   .append(hakyukoka)
					   .append(suikonoryoku)
					   .append(tekisetsuHoga)
					   .append(tekisetsuKaigai)
					   .append(wakates)
            		   .append(kekkaTen)
            		   .append(jinken)
            		   .append(buntankin)
            		   .append(dato)
            		   .append(checkedZenkakuComments)
					   .append(checkedZenOtherComment)
            		   .append(shinsaJokyo)
            		   .append(LINE_SHIFT);

            //long stop = System.currentTimeMillis() - start;
            //System.out.println(i+"行の処理時間。" + stop);
            }
        return finalResult.toString();
    }
    
    /**
     * 基盤研究等応募情報パンチデータを作成する
     * 
     * @param connection
     * @return パンチデータ情報(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataKibanKenkyu(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

    	log.info("パンチデータ処理開始。");
    	
        String query = "SELECT "
        		
                + " SUBSTR(A.JIGYO_ID,5,2) KENKYUSHUMOKU"	//研究種目番号（2バイト）
                //2007/02/08 苗　修正ここから
//                + ",NVL(A.SHINSA_KUBUN,'1') SHINSA_KUBUN" 	//審査区分(1バイト)
                + ",CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00120' THEN '0' ELSE NVL(A.SHINSA_KUBUN,'1') END SHINSA_KUBUN"  //審査区分(1バイト)
                //2007/02/08 苗　修正ここまで
                + ",NVL(A.SHINSEI_KUBUN,'1') SHINSEI_KUBUN" //新規継続区分(1バイト)
                + ",A.SHOZOKU_CD SHOZOKU_CD_DAIHYO" 		//所属機関コード(5バイト)
                + ",A.BUNKASAIMOKU_CD" 						//細目コード（4バイト）
                + ",CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
                + "      WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"	//分割番号（1バイト）
//UPDATE　START 2007/07/25 BIS 金京浩   //出力ファイル仕様20070720.xlsについて             
                //+ ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
                //+ "      WHEN A.BUNKATSU_NO = '2' THEN '2' ELSE ' ' END BUNKATSUNO_12"	//分割番号（1バイト）
                + ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
                + " 	 WHEN A.BUNKATSU_NO = '2' THEN '2' "
                + "		 WHEN A.BUNKATSU_NO = '3' THEN '3' "
                + "		 WHEN A.BUNKATSU_NO = '4' THEN '4' "
                + "      WHEN A.BUNKATSU_NO = '5' THEN '5' ELSE ' ' END BUNKATSUNO_12"	//分割番号（1バイト）                
//UPDATE　END　 2007/07/25 BIS 金京浩                 
                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER" 					//整理番号(4バイト)申請番号のハイフン以下
//UPDATE　START 2007/07/25 BIS 金京浩   //出力ファイル仕様20070720.xlsについて                
             /*  
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.KENKYU_TAISHO,' ') END KENKYU_TAISHO" //研究対象の類型（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE NVL(A.KAIGAIBUNYA_CD,'  ') END KAIGAIBUNYA_CD" //海外分野（2バイト）
                //2005.10.17kainuma 追加
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"		//版数
                //追加以上
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" //5年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_KEIZOKU,'        ') END KADAI_NO_KEIZOKU" //継続分の研究課題番号（8バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //分担金の有無
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD('　',80,'　') ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI" //研究課題名（和文）（80バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' " 
                + "      WHEN A.KENKYU_NINZU IS NULL THEN '01' ELSE LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') END KENKYUNINZU" //研究者数（2バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBO_FLG" //開示希望の有無（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(TO_CHAR(A.SHINSEI_FLG_NO),' ') END SHINSEI_FLG_NO" //研究計画最終年度前年度の応募（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_SAISYU,'        ') END KADAI_NO_SAISYU" //最終年度課題番号（8バイト）
             */   
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.KENKYU_TAISHO,' ') END KENKYU_TAISHO" //研究対象の類型（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE NVL(A.KAIGAIBUNYA_CD,'  ') END KAIGAIBUNYA_CD" //海外分野（2バイト）
                //2005.10.17kainuma 追加
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"		//版数
                //追加以上
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" //5年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_KEIZOKU,'        ') END KADAI_NO_KEIZOKU" //継続分の研究課題番号（8バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //分担金の有無
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD('　',80,'　') ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI" //研究課題名（和文）（80バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' " 
                + "      WHEN A.KENKYU_NINZU IS NULL THEN '01' ELSE LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') END KENKYUNINZU" //研究者数（2バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBO_FLG" //開示希望の有無（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(TO_CHAR(A.SHINSEI_FLG_NO),' ') END SHINSEI_FLG_NO" //研究計画最終年度前年度の応募（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_SAISYU,'        ') END KADAI_NO_SAISYU" //最終年度課題番号（8バイト）
               
//UPDATE　END　 2007/07/25 BIS 金京浩                
                + ",NVL(A.KEYWORD_CD,' ') KEYWORD_CD" 		//細目表キーワード（1バイト）                 
               // + ",DECODE(A.OTHER_KEYWORD,NULL,RPAD('　',50),RPAD(SUBSTRB(A.OTHER_KEYWORD,1,50),50)) OTHER_KEYWORD" //細目表以外のキーワード（50バイト）
				+ ",CASE WHEN A.OTHER_KEYWORD IS NULL THEN '　' ELSE TO_MULTI_BYTE(A.OTHER_KEYWORD) END OTHER_KEYWORD" //細目表以外のキーワード（50バイト）
                
                + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG" 		//代表者分担者別(1バイト)
                //2005.10.17kainuma　追加
				+ ",RPAD(B.NAME_KANA_SEI,32,'　') NAMEKANASEI"	//氏名（フリガナー姓）(32バイト)

                //2005.12.22 iso NULLだった時のバグ修正
//				+ ",RPAD(B.NAME_KANA_MEI,32,'　') NAMEKANAMEI"	//氏名（フリガナー名）(32バイト)
				+ ",RPAD(NVL(B.NAME_KANA_MEI, '　'),32,'　') NAMEKANAMEI"	//氏名（フリガナー名）(32バイト)
				+ ",RPAD(B.NAME_KANJI_SEI,32,'　') NAMEKANJISEI"	//氏名（漢字等ー姓）(32バイト)
                //2005.12.22 iso NULLだった時のバグ修正
//				+ ",RPAD(B.NAME_KANJI_MEI,32,'　') NAMEKANJIMEI"	//氏名（漢字等ー名）(32バイト)
				+ ",RPAD(NVL(B.NAME_KANJI_MEI, '　'),32,'　') NAMEKANJIMEI"	//氏名（漢字等ー名）(32バイト)
				
				//追加以上
                + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD" 	//所属機関名(コード)研究組織表管理テーブルより(5バイト)
                + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD " 	//部局名(コード)(3バイト)
                + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD" 	//職名コード(2バイト)
                + ",LPAD(B.KENKYU_NO, 8,' ')  KENKYUNO" 	//研究者番号(8バイト)
                + ",LPAD(NVL(B.KEIHI,0), 7,'0') KEIHI" 	    //研究経費(7バイト)
                + ",LPAD(B.EFFORT, 3,'0') EFFORT" 			//エフォート(3バイト)

                + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
                + " WHERE DEL_FLG = 0" 
                + "   AND A.SYSTEM_NO = B.SYSTEM_NO"
                + "   AND JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN
                + "   AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.BUNKASAIMOKU_CD, A.BUNKATSU_NO, SEIRINUMBER, BUNTANFLG, B.SEQ_NO"; //事業ID,機関ー細目番号ー分割番号ー整理番号、代表者分担者フラグ順、シーケンス番号順

        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = null; //new ArrayList();
        //List finalList = new ArrayList();

        //-----------------------
        // リスト取得
        //-----------------------
        try {
            list = SelectUtil.select(connection, query);
        } catch (DataAccessException e) {
            throw new ApplicationException("データベースアクセス中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("申請データテーブルに1件もデータがありません。", e);
        }

        //最終的にreturnするStringを宣言する
        //String finalResult = new String();
        StringBuffer finalResult = new StringBuffer();
		final char charToBeAdded = '　'; //全角スペース
		final int otherKeywordLength = 25; //細目表意外のキーワードの表示文字数
		final char HOSHI = '★';
		
		//半角全角コンバータ
		HanZenConverter hanZenConverter = new HanZenConverter();

        for (int i = 0; i < list.size(); i++) {
        	
			if(i % 1000 == 0) {
				log.info("パンチデータ処理数::" + i + "件");
			}
        	
        	
            //DBから取得したList型listをString型にして、そのStringのListを作る
            Map recordMap = (Map) list.get(i);
            String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //研究種目番号
        	String shinsaKbn = (String) recordMap.get("SHINSA_KUBUN");
        	String shinseiKbn = (String) recordMap.get("SHINSEI_KUBUN");
            String syozokuCdDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");
            String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKU_CD");
            String bunkatsNoAB = (String) recordMap.get("BUNKATSUNO_AB");
            String bunkatsNo12 = (String) recordMap.get("BUNKATSUNO_12");
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");
            String kenkyuTaisho = (String) recordMap.get("KENKYU_TAISHO");
            String kaigaiBunya = (String) recordMap.get("KAIGAIBUNYA_CD");
            //2005.10.17 kainuma追加
            String edition = (String)recordMap.get("EDITION");
            //追加以上
        	String keihi1 = (String) recordMap.get("KEIHI1");
            String keihi2 = (String) recordMap.get("KEIHI2");
            String keihi3 = (String) recordMap.get("KEIHI3");
            String keihi4 = (String) recordMap.get("KEIHI4");
            String keihi5 = (String) recordMap.get("KEIHI5");
            String kadaiNoKeizoku = (String) recordMap.get("KADAI_NO_KEIZOKU");
            String buntanKinFlg = (String) recordMap.get("BUNTANKINFLG");
            String kadaiNameKanji = (String) recordMap.get("KADAINAMEKANJI");
            String kenkyuNinzu = (String) recordMap.get("KENKYUNINZU");
        	String kaijikibo = (String) recordMap.get("KAIJIKIBO_FLG");
            String shinseiFlg = (String) recordMap.get("SHINSEI_FLG_NO");
            String kadaiNoSaisyu = (String) recordMap.get("KADAI_NO_SAISYU");
            String keywordCd = (String) recordMap.get("KEYWORD_CD");
            String otherKeyword = (String) recordMap.get("OTHER_KEYWORD");
            String buntanFlg = (String) recordMap.get("BUNTANFLG");
			//2005.10.17　kainuma追加
			String nameKanaSei = (String)recordMap.get("NAMEKANASEI");
			String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");
			String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");
			String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");
			//追加以上
            String syozokuCd = (String) recordMap.get("SHOZOKUCD");
            String bukyokuCd = (String) recordMap.get("BUKYOKUCD");
            String syokusyuCd = (String) recordMap.get("SHOKUSHUCD");
            String kenkyuNo = (String) recordMap.get("KENKYUNO");
        	String keihi = (String) recordMap.get("KEIHI");
            String effort = (String) recordMap.get("EFFORT");
            
            
            //DBより取得したレコードをGCの対象にする（大量データに対応するため）
            recordMap = null;
            list.set(i, null);
            
            
			//細目以外のキーワードを半角から全角にする
			//charの配列に変換
            //TODO
			String ZenkakuOtherKeyword = hanZenConverter.convert(otherKeyword);
			char[] cArray = ZenkakuOtherKeyword.toCharArray();
			for (int j = 0; j < cArray.length; j++) {
				if (StringUtil.isHankaku(cArray[j])) {
					cArray[j] = HOSHI; //半角の場合は置き換え
				  }

			  }
			  //文字列に戻す
			  String checkedOtherKeyword = new String(cArray);
			  if (checkedOtherKeyword.length() > otherKeywordLength){
				checkedOtherKeyword = checkedOtherKeyword.substring(0, otherKeywordLength);
			  }

			  //細目以外のキーワードを25文字の文字の固定長にする
			  while (checkedOtherKeyword.length() < otherKeywordLength) {
				checkedOtherKeyword += charToBeAdded;
			  }


			finalResult.append(jigyoId)
            		   .append(shinsaKbn)
            		   .append(shinseiKbn)
            		   .append(syozokuCdDaihyo)
           			   .append(bunkaSaimokuCd)
            		   .append(bunkatsNoAB)
            		   .append(bunkatsNo12)
            		   .append(seiriNumber)
            		   .append(kenkyuTaisho)
            		   .append(kaigaiBunya)
            		   .append(edition)
            		   .append(keihi1)
            		   .append(keihi2)
            		   .append(keihi3)
            		   .append(keihi4)
            		   .append(keihi5)
            		   .append(kadaiNoKeizoku)
            		   .append(buntanKinFlg)
            		   .append(kadaiNameKanji)
            		   .append(kenkyuNinzu)
            		   .append(kaijikibo)
            		   .append(shinseiFlg)
            		   .append(kadaiNoSaisyu)
            		   .append(keywordCd)
            		   .append(checkedOtherKeyword)
            		   .append(buntanFlg)
					   //2005.10.17 kainuma追加
					   .append(nameKanaSei)
					   .append(nameKanaMei)
					   .append(nameKanjiSei)
					   .append(nameKanjiMei)
					   //追加以上
            		   .append(syozokuCd)
            		   .append(bukyokuCd)
            		   .append(syokusyuCd)
            		   .append(kenkyuNo)
            		   .append(keihi)
            		   .append(effort)
            		   .append(LINE_SHIFT).toString();

            //finalList.add(record);
            //finalResult = finalResult + (String) finalList.get(i);
        }
        log.info("パンチデータ処理終了。");
        return finalResult.toString();
    }

    /**
     * 特定領域研究(新規領域)領域計画書概要のパンチデータを作成する
     * 
     * @param connection
     * @return パンチデータ情報(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataTokuteiSinnkiGaiyo(Connection connection)
        throws ApplicationException,
            DataAccessException,
            NoDataFoundException
    {

        String query = "SELECT "
                //update liuyi start 2006/06/29
                + " SUBSTR(A.JIGYO_ID,5,2) KENKYUSHUMOKU" //研究種目番号                           
                + ",NVL(A.UKETUKE_NO,'    ') UKETUKE_NO" // 整理番号                           
                + ",LPAD(NVL(A.KIBOUBUMON_CD,' '),2,' ') KIBOUBUMON_CD" // 審査希望部門（系等）               
                + ",NVL(A.KARIRYOIKI_NO,'     ') KARIRYOIKI_NO" // 仮領域番号                         
                + ",RPAD(NVL(A.RYOIKI_NAME,' '),80,'　')  RYOIKI_NAME" // 応募領域名（和文）                 
                + ",RPAD(NVL(A.RYOIKI_NAME_EIGO,' '),200,'　')  RYOIKI_NAME_EIGO" // 応募領域名（英文）                 
                + ",RPAD(NVL(A.RYOIKI_NAME_RYAKU,' '),16,'　')  RYOIKI_NAME_RYAKU" // 応募領域名（略称）                 
                + ",RPAD(A.NAME_KANA_SEI,32,'　') NAME_KANA_SEI" // 領域代表者「フリガナ」（姓）       
                + ",RPAD(NVL(A.NAME_KANA_MEI, '　'),32,'　') NAME_KANA_MEI" // 領域代表者「フリガナ」（名）       
                + ",RPAD(A.NAME_KANJI_SEI,32,'　') NAME_KANJI_SEI" // 領域代表者「漢字等」（姓）         
                + ",RPAD(NVL(A.NAME_KANJI_MEI, '　'),32,'　') NAME_KANJI_MEI" // 領域代表者「漢字等」（名）         
                + ",A.SHOZOKU_CD  SHOZOKU_CD" // 所属研究機関コード                 
                + ",A.BUKYOKU_CD BUKYOKU_CD " // 部局コード                         
                + ",RPAD(A.BUKYOKU_NAME,80,'　') BUKYOKU_NAME" // 部局名                             
                + ",LPAD(A.SHOKUSHU_CD,2,' ') SHOKUSHU_CD" // 職名コード                         
                + ",RPAD(A.SHOKUSHU_NAME_KANJI,100,' ') SHOKUSHU_NAME_KANJI " // 職名                               
                + ",RPAD(NVL(A.KENKYU_GAIYOU,' '),40,' ') KENKYU_GAIYOU " // 応募領域の研究概要                 
                + ",LPAD(A.JIZENCHOUSA_FLG,1,'0') JIZENCHOUSA_FLG " // 準備研究・事前調査の状況コード     
                + ",RPAD(NVL(A.JIZENCHOUSA_SONOTA,' '),40,' ') JIZENCHOUSA_SONOTA " // 準備研究・事前調査の状況名称       
                + ",LPAD(A.ZENNENDO_OUBO_FLG,1,'0') ZENNENDO_OUBO_FLG " // 前年度の応募該当フラグ             
                + ",LPAD(NVL(A.ZENNENDO_OUBO_NO,' '),3,' ') ZENNENDO_OUBO_NO " // 前年度の応募領域コード             
                + ",LPAD(NVL(A.BUNKASAIMOKU_CD1,' '),4,' ') BUNKASAIMOKU_CD1 " // 関連分野の細目番号1                
                + ",LPAD(NVL(A.BUNKASAIMOKU_CD2,' '),4,' ') BUNKASAIMOKU_CD2 " // 関連分野の細目番号2                
                + ",LPAD(NVL(A.KANRENBUNYA_BUNRUI_NO,''),2,' ') KANRENBUNYA_BUNRUI_NO " // 関連分野15分類コード               
                + ",LPAD(A.KENKYU_HITSUYOUSEI_1,1,'0') KENKYU_HITSUYOUSEI_1 " // 研究の必要性1   
                + ",LPAD(A.KENKYU_HITSUYOUSEI_2,1,'0') KENKYU_HITSUYOUSEI_2 " // 研究の必要性2                      
                + ",LPAD(A.KENKYU_HITSUYOUSEI_3,1,'0') KENKYU_HITSUYOUSEI_3 " // 研究の必要性3                      
                + ",LPAD(A.KENKYU_HITSUYOUSEI_4,1,'0') KENKYU_HITSUYOUSEI_4 " // 研究の必要性4                      
                + ",LPAD(A.KENKYU_HITSUYOUSEI_5,1,'0') KENKYU_HITSUYOUSEI_5 " // 研究の必要性5                      
                + ",LPAD(NVL(A.KENKYU_SYOKEI_1,'0'),7,'0') KENKYU_SYOKEI_1 " // 経費：公募研究の小計1年目          
                + ",RPAD(NVL(A.KENKYU_UTIWAKE_1,' '),30,' ') KENKYU_UTIWAKE_1 " // 経費：公募研究の内訳1年目          
                + ",LPAD(NVL(A.KENKYU_SYOKEI_2,'0'),7,'0') KENKYU_SYOKEI_2 " // 経費：公募研究の小計2年目          
                + ",RPAD(NVL(A.KENKYU_UTIWAKE_2,' '),30,' ') KENKYU_UTIWAKE_2 " // 経費：公募研究の内訳2年目          
                + ",LPAD(NVL(A.KENKYU_SYOKEI_3,'0'),7,'0') KENKYU_SYOKEI_3 " // 経費：公募研究の小計3年目          
                + ",RPAD(NVL(A.KENKYU_UTIWAKE_3,' '),30,' ') KENKYU_UTIWAKE_3 " // 経費：公募研究の内訳3年目          
                + ",LPAD(NVL(A.KENKYU_SYOKEI_4,'0'),7,'0') KENKYU_SYOKEI_4 " // 経費：公募研究の小計4年目          
                + ",RPAD(NVL(A.KENKYU_UTIWAKE_4,' '),30,' ') KENKYU_UTIWAKE_4 " // 経費：公募研究の内訳4年目          
                + ",LPAD(NVL(A.KENKYU_SYOKEI_5,'0'),7,'0') KENKYU_SYOKEI_5 " // 経費：公募研究の小計5年目          
                + ",RPAD(NVL(A.KENKYU_UTIWAKE_5,' '),30,' ') KENKYU_UTIWAKE_5 " // 経費：公募研究の内訳5年目          
                + ",LPAD(NVL(A.KENKYU_SYOKEI_6,'0'),7,'0') KENKYU_SYOKEI_6 " // 経費：公募研究の小計6年目          
                + ",RPAD(NVL(A.KENKYU_UTIWAKE_6,' '),30,' ') KENKYU_UTIWAKE_6 " // 経費：公募研究の内訳6年目          
                + ",NVL(A.DAIHYOU_ZIP,'        ') DAIHYOU_ZIP " // 領域代表者：郵便番号               
                + ",RPAD(NVL(A.DAIHYOU_ADDRESS,' '),100,' ') DAIHYOU_ADDRESS " // 領域代表者：住所                   
                + ",RPAD(NVL(A.DAIHYOU_TEL,' '),50,' ') DAIHYOU_TEL " // 領域代表者：電話番号               
                + ",RPAD(NVL(A.DAIHYOU_FAX,' '),50,' ') DAIHYOU_FAX " // 領域代表者：FAX番号                
                + ",RPAD(NVL(A.DAIHYOU_EMAIL,' '),50,' ') DAIHYOU_EMAIL " // 領域代表者：Email                  
                + ",RPAD(NVL(A.JIMUTANTO_NAME_KANJI_SEI, ' '),32,'　') JIMUTANTO_NAME_KANJI_SEI" // 事務担当者「フリガナ」（姓）       
                + ",RPAD(NVL(A.JIMUTANTO_NAME_KANJI_MEI, ' '),32,'　') JIMUTANTO_NAME_KANJI_MEI" // 事務担当者「フリガナ」（名）       
                + ",RPAD(NVL(A.JIMUTANTO_NAME_KANA_SEI, ' '),32,'　') JIMUTANTO_NAME_KANA_SEI" // 事務担当者「漢字等」（姓）         
                + ",RPAD(NVL(A.JIMUTANTO_NAME_KANA_MEI, ' '),32,'　') JIMUTANTO_NAME_KANA_MEI" // 事務担当者「漢字等」（名）         
                + ",LPAD(NVL(A.JIMUTANTO_SHOZOKU_CD, ' '),5,'　') JIMUTANTO_SHOZOKU_CD" // 事務担当者：所属研究機関コード     
                + ",LPAD(NVL(A.JIMUTANTO_BUKYOKU_CD, ' '),3,'　') JIMUTANTO_BUKYOKU_CD" // 事務担当者：部局コード             
                + ",RPAD(NVL(A.JIMUTANTO_BUKYOKU_NAME, ' '),100,'　') JIMUTANTO_BUKYOKU_NAME" // 事務担当者：部局名                 
                + ",LPAD(NVL(A.JIMUTANTO_SHOKUSHU_CD, '　'),2,'　') JIMUTANTO_SHOKUSHU_CD" // 事務担当者：職名コード             
                + ",RPAD(NVL(A.JIMUTANTO_SHOKUSHU_NAME_KANJI, '　'),40,'　') JIMUTANTO_SHOKUSHU_NAME_KANJI"// 事務担当者：職名                               
                + ",NVL(A.JIMUTANTO_ZIP, '        ') JIMUTANTO_ZIP" // 事務担当者：郵便番号               
                + ",RPAD(NVL(A.JIMUTANTO_ADDRESS, '　'),100,'　') JIMUTANTO_ADDRESS" // 事務担当者：住所                   
                + ",RPAD(NVL(A.JIMUTANTO_TEL, '　'),50,'　') JIMUTANTO_TEL" // 事務担当者：電話番号               
                + ",RPAD(NVL(A.JIMUTANTO_FAX, '　'),50,'　') JIMUTANTO_FAX" // 事務担当者：FAX番号                
                + ",RPAD(NVL(A.JIMUTANTO_EMAIL, '　'),50,'　') JIMUTANTO_EMAIL" // 事務担当者：Email                  
                + ",RPAD(NVL(A.KANREN_SHIMEI1, '　'),32,'　') KANREN_SHIMEI1" // 関連研究分野研究者1：氏名          
                + ",RPAD(NVL(A.KANREN_KIKAN1, '　'),80,'　') KANREN_KIKAN1" // 関連研究分野研究者1：所属機関名    
                + ",RPAD(NVL(A.KANREN_BUKYOKU1, '　'),100,'　') KANREN_BUKYOKU1" // 関連研究分野研究者1：部局          
                + ",RPAD(NVL(A.KANREN_SHOKU1, '　'),40,'　') KANREN_SHOKU1" // 関連研究分野研究者1：職            
                + ",RPAD(NVL(A.KANREN_SENMON1, '　'),40,'　') KANREN_SENMON1" // 関連研究分野研究者1：現在の専門    
                + ",RPAD(NVL(A.KANREN_TEL1, '　'),50,'　') KANREN_TEL1" // 関連研究分野研究者1：勤務先電話番号
                + ",RPAD(NVL(A.KANREN_JITAKUTEL1, '　'),50,'　') KANREN_JITAKUTEL1" // 関連研究分野研究者1：自宅電話番号  
                + ",RPAD(NVL(A.KANREN_SHIMEI2, '　'),32,'　') KANREN_SHIMEI2" // 関連研究分野研究者2：氏名          
                + ",RPAD(NVL(A.KANREN_KIKAN2, '　'),80,'　') KANREN_KIKAN2" // 関連研究分野研究者2：所属機関名    
                + ",RPAD(NVL(A.KANREN_BUKYOKU2, '　'),100,'　') KANREN_BUKYOKU2" // 関連研究分野研究者2：部局          
                + ",RPAD(NVL(A.KANREN_SHOKU2, '　'),40,'　') KANREN_SHOKU2" // 関連研究分野研究者2：職            
                + ",RPAD(NVL(A.KANREN_SENMON2, '　'),40,'　') KANREN_SENMON2" // 関連研究分野研究者2：現在の専門    
                + ",RPAD(NVL(A.KANREN_TEL2, '　'),50,'　') KANREN_TEL2" // 関連研究分野研究者2：勤務先電話番号
                + ",RPAD(NVL(A.KANREN_JITAKUTEL2, '　'),50,'　')KANREN_JITAKUTEL2" // 関連研究分野研究者2：自宅電話番号  
                + ",RPAD(NVL(A.KANREN_SHIMEI3, '　'),32,'　') KANREN_SHIMEI3" // 関連研究分野研究者3：氏名          
                + ",RPAD(NVL(A.KANREN_KIKAN3, '　'),80,'　') KANREN_KIKAN3" // 関連研究分野研究者3：所属機関名    
                + ",RPAD(NVL(A.KANREN_BUKYOKU3, '　'),100,'　') KANREN_BUKYOKU3" // 関連研究分野研究者3：部局          
                + ",RPAD(NVL(A.KANREN_SHOKU3, '　'),40,'　') KANREN_SHOKU3" // 関連研究分野研究者3：職            
                + ",RPAD(NVL(A.KANREN_SENMON3, '　'),40,'　') KANREN_SENMON3" // 関連研究分野研究者3：現在の専門    
                + ",RPAD(NVL(A.KANREN_TEL3, '　'),50,'　') KANREN_TEL3" // 関連研究分野研究者3：勤務先電話番号
                + ",RPAD(NVL(A.KANREN_JITAKUTEL3, '　'),50,'　') KANREN_JITAKUTEL3" // 関連研究分野研究者3：自宅電話番号 
                + " FROM RYOIKIKEIKAKUSHOINFO A"
                + " WHERE DEL_FLG = 0"
                + "   AND SUBSTR(A.JIGYO_ID,3,5) ='00022' "
                + "   AND TO_NUMBER(RYOIKI_JOKYO_ID) IN (6,8,9,10,11,12,21,22,23,24)"
                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.KARIRYOIKI_NO,  KARIRYOIKI_NO"; //事業ID順、機関-仮領域番号-項目番号-整理番号順

        //for debug
        if (log.isDebugEnabled())
        {
            log.debug("query:" + query);
        }

        List list = new ArrayList();

        //-----------------------
        // リスト取得
        //-----------------------
        try
        {
            list = SelectUtil.select(connection, query.toString());
        }
        catch (DataAccessException e)
        {
            throw new ApplicationException("データベースアクセス中にDBエラーが発生しました。",
                                           new ErrorInfo("errors.4004"), e);
        }
        catch (NoDataFoundException e)
        {
            throw new NoDataFoundException("データベースに1件もデータがありません。", e);
        }

        //最終的にreturnするStringを宣言する
        StringBuffer finalResult = new StringBuffer();

        for (int i = 0; i < list.size(); i++)
        {

            //	long start = System.currentTimeMillis();

            //DBから取得したList型listをString型にして、そのStringのListを作る
            Map recordMap = (Map)list.get(i);
            String KENKYUSHUMOKU = "02"; //研究種目番号                       
            String UKETUKE_NO = (String)recordMap.get("UKETUKE_NO"); //整理番号                           
            String KIBOUBUMON_CD = (String)recordMap.get("KIBOUBUMON_CD"); //審査希望部門（系等）               
            String KARIRYOIKI_NO = (String)recordMap.get("KARIRYOIKI_NO"); //仮領域番号                         
            String RYOIKI_NAME = (String)recordMap.get("RYOIKI_NAME"); //応募領域名（和文）                 
            String RYOIKI_NAME_EIGO = (String)recordMap.get("RYOIKI_NAME_EIGO"); //応募領域名（英文）                 
            String RYOIKI_NAME_RYAKU = (String)recordMap.get("RYOIKI_NAME_RYAKU");
            ; //応募領域名（略称）NAMEKANASEI                 
            String NAME_KANJI_SEI = (String)recordMap.get("NAME_KANJI_SEI"); //領域代表者「フリガナ」（姓）       
            String NAME_KANJI_MEI = (String)recordMap.get("NAME_KANJI_MEI"); //領域代表者「フリガナ」（名）       
            String NAME_KANA_SEI = (String)recordMap.get("NAME_KANA_SEI"); //領域代表者「漢字等」（姓）         
            String NAME_KANA_MEI = (String)recordMap.get("NAME_KANA_MEI"); //領域代表者「漢字等」（名）         
            String SHOZOKU_CD = (String)recordMap.get("SHOZOKU_CD"); //所属研究機関コード                 
            String BUKYOKU_CD = (String)recordMap.get("BUKYOKU_CD"); //部局コード                         
            String BUKYOKU_NAME = (String)recordMap.get("BUKYOKU_NAME");
            ; //部局名                             
            String SHOKUSHU_CD = (String)recordMap.get("SHOKUSHU_CD");
            ; //職名コード                         
            String SHOKUSHU_NAME_KANJI = (String)recordMap.get("SHOKUSHU_NAME_KANJI"); //職名                               
            String KENKYU_GAIYOU = (String)recordMap.get("KENKYU_GAIYOU"); //応募領域の研究概要                 
            String JIZENCHOUSA_FLG = (String)recordMap.get("JIZENCHOUSA_FLG"); //準備研究・事前調査の状況コード     
            String JIZENCHOUSA_SONOTA = (String)recordMap.get("JIZENCHOUSA_SONOTA"); //準備研究・事前調査の状況名称       
            String ZENNENDO_OUBO_FLG = (String)recordMap.get("ZENNENDO_OUBO_FLG"); //前年度の応募該当フラグ             
            String ZENNENDO_OUBO_NO = (String)recordMap.get("ZENNENDO_OUBO_NO"); //前年度の応募領域コード             
            String BUNKASAIMOKU_CD1 = (String)recordMap.get("BUNKASAIMOKU_CD1"); //関連分野の細目番号1                
            String BUNKASAIMOKU_CD2 = (String)recordMap.get("BUNKASAIMOKU_CD2"); //関連分野の細目番号2                
            String KANRENBUNYA_BUNRUI_NO = (String)recordMap.get("KANRENBUNYA_BUNRUI_NO"); //関連分野15分類コード               
            String KENKYU_HITSUYOUSEI_1 = (String)recordMap.get("KENKYU_HITSUYOUSEI_1"); //研究の必要性1                      
            String KENKYU_HITSUYOUSEI_2 = (String)recordMap.get("KENKYU_HITSUYOUSEI_2"); //研究の必要性2                      
            String KENKYU_HITSUYOUSEI_3 = (String)recordMap.get("KENKYU_HITSUYOUSEI_3"); //研究の必要性3                      
            String KENKYU_HITSUYOUSEI_4 = (String)recordMap.get("KENKYU_HITSUYOUSEI_4"); //研究の必要性4                      
            String KENKYU_HITSUYOUSEI_5 = (String)recordMap.get("KENKYU_HITSUYOUSEI_5"); //研究の必要性5                      
            String KENKYU_SYOKEI_1 = (String)recordMap.get("KENKYU_SYOKEI_1"); //経費：公募研究の小計1年目          
            String KENKYU_UTIWAKE_1 = (String)recordMap.get("KENKYU_UTIWAKE_1"); //経費：公募研究の内訳1年目          
            String KENKYU_SYOKEI_2 = (String)recordMap.get("KENKYU_SYOKEI_2"); //経費：公募研究の小計2年目          
            String KENKYU_UTIWAKE_2 = (String)recordMap.get("KENKYU_UTIWAKE_2"); //経費：公募研究の内訳2年目          
            String KENKYU_SYOKEI_3 = (String)recordMap.get("KENKYU_SYOKEI_3"); //経費：公募研究の小計3年目          
            String KENKYU_UTIWAKE_3 = (String)recordMap.get("KENKYU_UTIWAKE_3"); //経費：公募研究の内訳3年目          
            String KENKYU_SYOKEI_4 = (String)recordMap.get("KENKYU_SYOKEI_4"); //経費：公募研究の小計4年目          
            String KENKYU_UTIWAKE_4 = (String)recordMap.get("KENKYU_UTIWAKE_4"); //経費：公募研究の内訳4年目          
            String KENKYU_SYOKEI_5 = (String)recordMap.get("KENKYU_SYOKEI_5"); //経費：公募研究の小計5年目          
            String KENKYU_UTIWAKE_5 = (String)recordMap.get("KENKYU_UTIWAKE_5"); //経費：公募研究の内訳5年目          
            String KENKYU_SYOKEI_6 = (String)recordMap.get("KENKYU_SYOKEI_6"); //経費：公募研究の小計6年目          
            String KENKYU_UTIWAKE_6 = (String)recordMap.get("KENKYU_UTIWAKE_6"); //経費：公募研究の内訳6年目          
            String DAIHYOU_ZIP = (String)recordMap.get("DAIHYOU_ZIP"); //領域代表者：郵便番号               
            String DAIHYOU_ADDRESS = (String)recordMap.get("DAIHYOU_ADDRESS"); //領域代表者：住所                   
            String DAIHYOU_TEL = (String)recordMap.get("DAIHYOU_TEL"); //領域代表者：電話番号               
            String DAIHYOU_FAX = (String)recordMap.get("DAIHYOU_FAX"); //領域代表者：FAX番号                
            String DAIHYOU_EMAIL = (String)recordMap.get("DAIHYOU_EMAIL"); //領域代表者：Email                  
            String JIMUTANTO_NAME_KANJI_SEI = (String)recordMap.get("JIMUTANTO_NAME_KANJI_SEI"); //事務担当者「フリガナ」（姓）       
            String JIMUTANTO_NAME_KANJI_MEI = (String)recordMap.get("JIMUTANTO_NAME_KANJI_MEI"); //事務担当者「フリガナ」（名）       
            String JIMUTANTO_NAME_KANA_SEI = (String)recordMap.get("JIMUTANTO_NAME_KANA_SEI"); //事務担当者「漢字等」（姓）         
            String JIMUTANTO_NAME_KANA_MEI = (String)recordMap.get("JIMUTANTO_NAME_KANA_MEI"); //事務担当者「漢字等」（名）         
            String JIMUTANTO_SHOZOKU_CD = (String)recordMap.get("JIMUTANTO_SHOZOKU_CD"); //事務担当者：所属研究機関コード     
            String JIMUTANTO_BUKYOKU_CD = (String)recordMap.get("JIMUTANTO_BUKYOKU_CD"); //事務担当者：部局コード             
            String JIMUTANTO_BUKYOKU_NAME = (String)recordMap.get("JIMUTANTO_BUKYOKU_NAME"); //事務担当者：部局名                 
            String JIMUTANTO_SHOKUSHU_CD = (String)recordMap.get("JIMUTANTO_SHOKUSHU_CD"); //事務担当者：職名コード             
            String JIMUTANTO_S_N_KANJI = (String)recordMap.get("JIMUTANTO_SHOKUSHU_NAME_KANJI"); //事務担当者：職名                   
            String JIMUTANTO_ZIP = (String)recordMap.get("JIMUTANTO_ZIP"); //事務担当者：郵便番号               
            String JIMUTANTO_ADDRESS = (String)recordMap.get("JIMUTANTO_ADDRESS"); //事務担当者：住所                   
            String JIMUTANTO_TEL = (String)recordMap.get("JIMUTANTO_TEL"); //事務担当者：電話番号               
            String JIMUTANTO_FAX = (String)recordMap.get("JIMUTANTO_FAX"); //事務担当者：FAX番号                
            String JIMUTANTO_EMAIL = (String)recordMap.get("JIMUTANTO_EMAIL"); //事務担当者：Email                  
            String KANREN_SHIMEI1 = (String)recordMap.get("KANREN_SHIMEI1"); //関連研究分野研究者1：氏名          
            String KANREN_KIKAN1 = (String)recordMap.get("KANREN_KIKAN1"); //関連研究分野研究者1：所属機関名    
            String KANREN_BUKYOKU1 = (String)recordMap.get("KANREN_BUKYOKU1"); //関連研究分野研究者1：部局          
            String KANREN_SHOKU1 = (String)recordMap.get("KANREN_SHOKU1"); //関連研究分野研究者1：職            
            String KANREN_SENMON1 = (String)recordMap.get("KANREN_SENMON1"); //関連研究分野研究者1：現在の専門    
            String KANREN_TEL1 = (String)recordMap.get("KANREN_TEL1"); //関連研究分野研究者1：勤務先電話番号
            String KANREN_JITAKUTEL1 = (String)recordMap.get("KANREN_JITAKUTEL1"); //関連研究分野研究者1：自宅電話番号  
            String KANREN_SHIMEI2 = (String)recordMap.get("KANREN_SHIMEI2"); //関連研究分野研究者2：氏名          
            String KANREN_KIKAN2 = (String)recordMap.get("KANREN_KIKAN2"); //関連研究分野研究者2：所属機関名    
            String KANREN_BUKYOKU2 = (String)recordMap.get("KANREN_BUKYOKU2"); //関連研究分野研究者2：部局          
            String KANREN_SHOKU2 = (String)recordMap.get("KANREN_SHOKU2"); //関連研究分野研究者2：職            
            String KANREN_SENMON2 = (String)recordMap.get("KANREN_SENMON2"); //関連研究分野研究者2：現在の専門    
            String KANREN_TEL2 = (String)recordMap.get("KANREN_TEL2"); //関連研究分野研究者2：勤務先電話番号
            String KANREN_JITAKUTEL2 = (String)recordMap.get("KANREN_JITAKUTEL2"); //関連研究分野研究者2：自宅電話番号  
            String KANREN_SHIMEI3 = (String)recordMap.get("KANREN_SHIMEI3"); //関連研究分野研究者3：氏名          
            String KANREN_KIKAN3 = (String)recordMap.get("KANREN_KIKAN3"); //関連研究分野研究者3：所属機関名    
            String KANREN_BUKYOKU3 = (String)recordMap.get("KANREN_BUKYOKU3"); //関連研究分野研究者3：部局          
            String KANREN_SHOKU3 = (String)recordMap.get("KANREN_SHOKU3"); //関連研究分野研究者3：職            
            String KANREN_SENMON3 = (String)recordMap.get("KANREN_SENMON3"); //関連研究分野研究者3：現在の専門    
            String KANREN_TEL3 = (String)recordMap.get("KANREN_TEL3"); //関連研究分野研究者3：勤務先電話番号
            String KANREN_JITAKUTEL3 = (String)recordMap.get("KANREN_JITAKUTEL3"); //関連研究分野研究者3：自宅電話番号 

            //DBより取得したレコードをGCの対象にする（大量データに対応するため）
            recordMap = null;
            list.set(i, null);

            //最終結果にレコード（本行）を文字列結合
            finalResult.append(KENKYUSHUMOKU) //研究種目番号                       
                    .append(UKETUKE_NO) //整理番号                           
                    .append(KIBOUBUMON_CD) //審査希望部門（系等）               
                    .append(KARIRYOIKI_NO) //仮領域番号                         
                    .append(RYOIKI_NAME) //応募領域名（和文）                 
                    .append(RYOIKI_NAME_EIGO) //応募領域名（英文）                 
                    .append(RYOIKI_NAME_RYAKU) //応募領域名（略称）                 
                    .append(NAME_KANJI_SEI) //領域代表者「フリガナ」（姓）       
                    .append(NAME_KANJI_MEI) //領域代表者「フリガナ」（名）       
                    .append(NAME_KANA_SEI) //領域代表者「漢字等」（姓）         
                    .append(NAME_KANA_MEI) //領域代表者「漢字等」（名）         
                    .append(SHOZOKU_CD) //所属研究機関コード                 
                    .append(BUKYOKU_CD) //部局コード                         
                    .append(BUKYOKU_NAME) //部局名                             
                    .append(SHOKUSHU_CD) //職名コード                         
                    .append(SHOKUSHU_NAME_KANJI) //職名                               
                    .append(KENKYU_GAIYOU) //応募領域の研究概要                 
                    .append(JIZENCHOUSA_FLG) //準備研究・事前調査の状況コード     
                    .append(JIZENCHOUSA_SONOTA) //準備研究・事前調査の状況名称       
                    .append(ZENNENDO_OUBO_FLG) //前年度の応募該当フラグ             
                    .append(ZENNENDO_OUBO_NO) //前年度の応募領域コード             
                    .append(BUNKASAIMOKU_CD1) //関連分野の細目番号1                
                    .append(BUNKASAIMOKU_CD2) //関連分野の細目番号2                
                    .append(KANRENBUNYA_BUNRUI_NO) //関連分野15分類コード               
                    .append(KENKYU_HITSUYOUSEI_1) //研究の必要性1                      
                    .append(KENKYU_HITSUYOUSEI_2) //研究の必要性2                      
                    .append(KENKYU_HITSUYOUSEI_3) //研究の必要性3                      
                    .append(KENKYU_HITSUYOUSEI_4) //研究の必要性4                      
                    .append(KENKYU_HITSUYOUSEI_5) //研究の必要性5                      
                    .append(KENKYU_SYOKEI_1) //経費：公募研究の小計1年目          
                    .append(KENKYU_UTIWAKE_1) //経費：公募研究の内訳1年目          
                    .append(KENKYU_SYOKEI_2) //経費：公募研究の小計2年目          
                    .append(KENKYU_UTIWAKE_2) //経費：公募研究の内訳2年目          
                    .append(KENKYU_SYOKEI_3) //経費：公募研究の小計3年目          
                    .append(KENKYU_UTIWAKE_3) //経費：公募研究の内訳3年目          
                    .append(KENKYU_SYOKEI_4) //経費：公募研究の小計4年目          
                    .append(KENKYU_UTIWAKE_4) //経費：公募研究の内訳4年目          
                    .append(KENKYU_SYOKEI_5) //経費：公募研究の小計5年目          
                    .append(KENKYU_UTIWAKE_5) //経費：公募研究の内訳5年目          
                    .append(KENKYU_SYOKEI_6) //経費：公募研究の小計6年目          
                    .append(KENKYU_UTIWAKE_6) //経費：公募研究の内訳6年目          
                    .append(DAIHYOU_ZIP) //領域代表者：郵便番号               
                    .append(DAIHYOU_ADDRESS) //領域代表者：住所                   
                    .append(DAIHYOU_TEL) //領域代表者：電話番号               
                    .append(DAIHYOU_FAX) //領域代表者：FAX番号                
                    .append(DAIHYOU_EMAIL) //領域代表者：Email                  
                    .append(JIMUTANTO_NAME_KANJI_SEI) //事務担当者「フリガナ」（姓）       
                    .append(JIMUTANTO_NAME_KANJI_MEI) //事務担当者「フリガナ」（名）       
                    .append(JIMUTANTO_NAME_KANA_SEI) //事務担当者「漢字等」（姓）         
                    .append(JIMUTANTO_NAME_KANA_MEI) //事務担当者「漢字等」（名）         
                    .append(JIMUTANTO_SHOZOKU_CD) //事務担当者：所属研究機関コード     
                    .append(JIMUTANTO_BUKYOKU_CD) //事務担当者：部局コード             
                    .append(JIMUTANTO_BUKYOKU_NAME) //事務担当者：部局名                 
                    .append(JIMUTANTO_SHOKUSHU_CD) //事務担当者：職名コード             
                    .append(JIMUTANTO_S_N_KANJI) //事務担当者：職名                   
                    .append(JIMUTANTO_ZIP) //事務担当者：郵便番号               
                    .append(JIMUTANTO_ADDRESS) //事務担当者：住所                   
                    .append(JIMUTANTO_TEL) //事務担当者：電話番号               
                    .append(JIMUTANTO_FAX) //事務担当者：FAX番号                
                    .append(JIMUTANTO_EMAIL) //事務担当者：Email                  
                    .append(KANREN_SHIMEI1) //関連研究分野研究者1：氏名          
                    .append(KANREN_KIKAN1) //関連研究分野研究者1：所属機関名    
                    .append(KANREN_BUKYOKU1) //関連研究分野研究者1：部局          
                    .append(KANREN_SHOKU1) //関連研究分野研究者1：職            
                    .append(KANREN_SENMON1) //関連研究分野研究者1：現在の専門    
                    .append(KANREN_TEL1) //関連研究分野研究者1：勤務先電話番号
                    .append(KANREN_JITAKUTEL1) //関連研究分野研究者1：自宅電話番号  
                    .append(KANREN_SHIMEI2) //関連研究分野研究者2：氏名          
                    .append(KANREN_KIKAN2) //関連研究分野研究者2：所属機関名    
                    .append(KANREN_BUKYOKU2) //関連研究分野研究者2：部局          
                    .append(KANREN_SHOKU2) //関連研究分野研究者2：職            
                    .append(KANREN_SENMON2) //関連研究分野研究者2：現在の専門    
                    .append(KANREN_TEL2) //関連研究分野研究者2：勤務先電話番号
                    .append(KANREN_JITAKUTEL2) //関連研究分野研究者2：自宅電話番号  
                    .append(KANREN_SHIMEI3) //関連研究分野研究者3：氏名          
                    .append(KANREN_KIKAN3) //関連研究分野研究者3：所属機関名    
                    .append(KANREN_BUKYOKU3) //関連研究分野研究者3：部局          
                    .append(KANREN_SHOKU3) //関連研究分野研究者3：職            
                    .append(KANREN_SENMON3) //関連研究分野研究者3：現在の専門    
                    .append(KANREN_TEL3) //関連研究分野研究者3：勤務先電話番号
                    .append(KANREN_JITAKUTEL3) //関連研究分野研究者3：自宅電話番号 
                    .append(LINE_SHIFT).toString();

        }

        return finalResult.toString();
    }

//Add By Sai 2006.09.19
    /**
     * 特定領域研究(新規領域)領域計画書概要のパンチデータ(Csv)を作成する
     * 
     * @param connection
     * @return パンチデータ情報(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataTokuteiSinnkiGaiyoCsv(Connection connection)
        throws ApplicationException,
            DataAccessException,
            NoDataFoundException
    {

    	List list = new ArrayList();
    	String query = "SELECT "
                + " '02' \"研究種目番号\"" //研究種目番号                           
                + ",TRIM(A.UKETUKE_NO)" // 整理番号                           
                + ",A.KIBOUBUMON_CD" // 審査希望部門（系等）               
                + ",A.KARIRYOIKI_NO" // 仮領域番号                         
                + ",A.RYOIKI_NAME" // 応募領域名（和文）                 
                + ",A.RYOIKI_NAME_EIGO" // 応募領域名（英文）                 
                + ",A.RYOIKI_NAME_RYAKU" // 応募領域名（略称）                 
                + ",A.NAME_KANJI_SEI" // 領域代表者「漢字等」（姓）         
                + ",A.NAME_KANJI_MEI" // 領域代表者「漢字等」（名）         
                + ",A.NAME_KANA_SEI" // 領域代表者「フリガナ」（姓）       
                + ",A.NAME_KANA_MEI" // 領域代表者「フリガナ」（名）       
                + ",A.SHOZOKU_CD " // 所属研究機関コード                 
                + ",A.BUKYOKU_CD  " // 部局コード                         
                + ",A.BUKYOKU_NAME" // 部局名                             
                + ",A.SHOKUSHU_CD" // 職名コード                         
                + ",A.SHOKUSHU_NAME_KANJI" // 職名                               
                + ",A.KENKYU_GAIYOU" // 応募領域の研究概要                 
                + ",A.JIZENCHOUSA_FLG" // 準備研究・事前調査の状況コード     
                + ",A.JIZENCHOUSA_SONOTA" // 準備研究・事前調査の状況名称       
                + ",A.ZENNENDO_OUBO_FLG" // 前年度の応募該当フラグ             
                + ",A.ZENNENDO_OUBO_NO" // 前年度の応募領域コード             
                + ",A.BUNKASAIMOKU_CD1" // 関連分野の細目番号1                
                + ",A.BUNKASAIMOKU_CD2" // 関連分野の細目番号2                
                + ",A.KANRENBUNYA_BUNRUI_NO" // 関連分野15分類コード               
                + ",A.KENKYU_HITSUYOUSEI_1" // 研究の必要性1   
                + ",A.KENKYU_HITSUYOUSEI_2" // 研究の必要性2                      
                + ",A.KENKYU_HITSUYOUSEI_3" // 研究の必要性3                      
                + ",A.KENKYU_HITSUYOUSEI_4" // 研究の必要性4                      
                + ",A.KENKYU_HITSUYOUSEI_5" // 研究の必要性5                      
                + ",A.KENKYU_SYOKEI_1" // 経費：公募研究の小計1年目          
                + ",A.KENKYU_UTIWAKE_1" // 経費：公募研究の内訳1年目          
                + ",A.KENKYU_SYOKEI_2" // 経費：公募研究の小計2年目          
                + ",A.KENKYU_UTIWAKE_2" // 経費：公募研究の内訳2年目          
                + ",A.KENKYU_SYOKEI_3" // 経費：公募研究の小計3年目          
                + ",A.KENKYU_UTIWAKE_3" // 経費：公募研究の内訳3年目          
                + ",A.KENKYU_SYOKEI_4" // 経費：公募研究の小計4年目          
                + ",A.KENKYU_UTIWAKE_4" // 経費：公募研究の内訳4年目          
                + ",A.KENKYU_SYOKEI_5" // 経費：公募研究の小計5年目          
                + ",A.KENKYU_UTIWAKE_5" // 経費：公募研究の内訳5年目          
                + ",A.KENKYU_SYOKEI_6" // 経費：公募研究の小計6年目          
                + ",A.KENKYU_UTIWAKE_6" // 経費：公募研究の内訳6年目  
                //ADD START 2007/07/04 BIS 趙一非
                //H19完全電子化及び制度改正
                //特定新規（領域計画書概要）応募情報パンチデータの応募金額の合計に計算方法 
                
                
                + ",A.KENKYU_SYOKEI_1+KEIHI1" // 経費：応募金額の合計1年目          
                      
                + ",A.KENKYU_SYOKEI_2+KEIHI2"  // 経費：応募金額の合計2年目          
                      
                + ",A.KENKYU_SYOKEI_3+KEIHI3"  // 経費：応募金額の合計3年目          
                         
                + ",A.KENKYU_SYOKEI_4+KEIHI4" // 経費：応募金額の合計4年目          
                        
                + ",A.KENKYU_SYOKEI_5+KEIHI5"  // 経費：応募金額の合計5年目          
                         
                + ",A.KENKYU_SYOKEI_6+KEIHI6"  // 経費：応募金額の合計6年目          
                
                
                
                
                //ADD END 2007/07/04 BIS 趙一非
                + ",A.DAIHYOU_ZIP" // 領域代表者：郵便番号               
                + ",A.DAIHYOU_ADDRESS" // 領域代表者：住所                   
                + ",A.DAIHYOU_TEL" // 領域代表者：電話番号               
                + ",A.DAIHYOU_FAX" // 領域代表者：FAX番号                
                + ",A.DAIHYOU_EMAIL" // 領域代表者：Email                  
                + ",A.JIMUTANTO_NAME_KANJI_SEI" // 事務担当者「漢字等」（姓）        
                + ",A.JIMUTANTO_NAME_KANJI_MEI" // 事務担当者「漢字等」（名）       
                + ",A.JIMUTANTO_NAME_KANA_SEI" //  事務担当者「フリガナ」（姓）    
                + ",A.JIMUTANTO_NAME_KANA_MEI" // 事務担当者「フリガナ」（名）         
                + ",A.JIMUTANTO_SHOZOKU_CD" // 事務担当者：所属研究機関コード     
                + ",A.JIMUTANTO_BUKYOKU_CD" // 事務担当者：部局コード             
                + ",A.JIMUTANTO_BUKYOKU_NAME" // 事務担当者：部局名                 
                + ",A.JIMUTANTO_SHOKUSHU_CD" // 事務担当者：職名コード             
                + ",A.JIMUTANTO_SHOKUSHU_NAME_KANJI"// 事務担当者：職名                               
                + ",A.JIMUTANTO_ZIP" // 事務担当者：郵便番号               
                + ",A.JIMUTANTO_ADDRESS" // 事務担当者：住所                   
                + ",A.JIMUTANTO_TEL" // 事務担当者：電話番号               
                + ",A.JIMUTANTO_FAX" // 事務担当者：FAX番号                
                + ",A.JIMUTANTO_EMAIL" // 事務担当者：Email 
                + ",A.KANREN_SHIMEI1" // 関連研究分野研究者1：氏名          
                + ",A.KANREN_KIKAN1" // 関連研究分野研究者1：所属機関名    
                + ",A.KANREN_BUKYOKU1" // 関連研究分野研究者1：部局          
                + ",A.KANREN_SHOKU1" // 関連研究分野研究者1：職            
                + ",A.KANREN_SENMON1" // 関連研究分野研究者1：現在の専門    
                + ",A.KANREN_TEL1" // 関連研究分野研究者1：勤務先電話番号
                + ",A.KANREN_JITAKUTEL1" // 関連研究分野研究者1：自宅電話番号  
                + ",A.KANREN_SHIMEI2" // 関連研究分野研究者2：氏名          
                + ",A.KANREN_KIKAN2" // 関連研究分野研究者2：所属機関名    
                + ",A.KANREN_BUKYOKU2" // 関連研究分野研究者2：部局          
                + ",A.KANREN_SHOKU2" // 関連研究分野研究者2：職            
                + ",A.KANREN_SENMON2" // 関連研究分野研究者2：現在の専門    
                + ",A.KANREN_TEL2" // 関連研究分野研究者2：勤務先電話番号
                + ",A.KANREN_JITAKUTEL2" // 関連研究分野研究者2：自宅電話番号  
                + ",A.KANREN_SHIMEI3" // 関連研究分野研究者3：氏名          
                + ",A.KANREN_KIKAN3" // 関連研究分野研究者3：所属機関名    
                + ",A.KANREN_BUKYOKU3" // 関連研究分野研究者3：部局          
                + ",A.KANREN_SHOKU3" // 関連研究分野研究者3：職            
                + ",A.KANREN_SENMON3" // 関連研究分野研究者3：現在の専門    
                + ",A.KANREN_TEL3" // 関連研究分野研究者3：勤務先電話番号
                + ",A.KANREN_JITAKUTEL3" // 関連研究分野研究者3：自宅電話番号 
                
//            	UPDATE START 2007/07/05 BIS 趙一非
                
                
                //H19完全電子化及び制度改正
                //特定新規（領域計画書概要）応募情報パンチデータの応募金額の合計に計算方法 
                
                
//                + " FROM RYOIKIKEIKAKUSHOINFO A"
//                + " WHERE DEL_FLG = 0"
//                + "   AND SUBSTR(A.JIGYO_ID,3,5) ='00022' "             
//                //2006.10.30 iso 「受理済み」のみ（領域計画書で、8以降の状態はない）
////                + "   AND TO_NUMBER(RYOIKI_JOKYO_ID) IN (6,8,9,10,11,12,21,22,23,24)"
//                + "   AND TO_NUMBER(RYOIKI_JOKYO_ID) IN (6)"
//
//                
//                
//                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.KARIRYOIKI_NO,  KARIRYOIKI_NO"; //事業ID順、機関-仮領域番号-項目番号-整理番号順
 
              + " FROM RYOIKIKEIKAKUSHOINFO A"
              + ",(Select SUM(B.KEIHI1) KEIHI1 , SUM(B.KEIHI2) KEIHI2,SUM(B.KEIHI3) KEIHI3,SUM(B.KEIHI4) KEIHI4,SUM(B.KEIHI5) KEIHI5,SUM(B.KEIHI6) KEIHI6,B.RYOIKI_NO From SHINSEIDATAKANRI B  Where JOKYO_ID='06' AND B.DEL_FLG = 0 AND SUBSTR(JIGYO_ID,3,5) ='00022' Group By B.RYOIKI_NO) C"
              + " WHERE A.DEL_FLG = 0"
              + "   AND SUBSTR(A.JIGYO_ID,3,5) ='00022' "             
                
                
              //2006.10.30 iso 「受理済み」のみ（領域計画書で、8以降の状態はない）
//            + "   AND TO_NUMBER(RYOIKI_JOKYO_ID) IN (6,8,9,10,11,12,21,22,23,24)"
            + "   AND TO_NUMBER(A.RYOIKI_JOKYO_ID) IN (6)"
    		+" AND A.KARIRYOIKI_NO=C.RYOIKI_NO"
            
            
            + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.KARIRYOIKI_NO"; //事業ID順、機関-仮領域番号-項目番号-整理番号順

//            	UPDATE END 2007/07/05 BIS 趙一非
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        //-----------------------
        // リスト取得
        //-----------------------
        try
        {
            list = SelectUtil.selectCsvList(connection, query,true);
        }
        catch (DataAccessException e)
        {
            throw new ApplicationException("データベースアクセス中にDBエラーが発生しました。",
                                           new ErrorInfo("errors.4004"), e);
        }
        catch (NoDataFoundException e)
        {
            throw new NoDataFoundException("データベースに1件もデータがありません。", e);
        }

        String[] columnArray = {
        		"研究種目番号"
        		,"整理番号"
        		,"審査希望部門（系等）"
        		,"仮領域番号"
        		,"応募領域名（和文）"
        		,"応募領域名（英文）"
        		,"応募領域名（略称）"
        		,"領域代表者「漢字等」（姓）"
        		,"領域代表者「漢字等」（名）"
        		,"領域代表者「フリガナ」（姓）"
        		,"領域代表者「フリガナ」（名）"
        		,"所属研究機関コード"
        		,"部局コード"
        		,"部局名"
        		,"職名コード"
        		,"職名"
        		,"応募領域の研究概要"
        		,"準備研究・事前調査の状況コード"
        		,"準備研究・事前調査の状況名称"
        		,"前年度の応募該当フラグ"
        		,"前年度の応募領域コード"
        		,"関連分野の細目番号1"
        		,"関連分野の細目番号2"
        		,"関連分野15分類コード"
        		,"研究の必要性1"
        		,"研究の必要性2"
        		,"研究の必要性3"
        		,"研究の必要性4"
        		,"研究の必要性5"
        		,"経費：公募研究の小計1年目"
        		,"経費：公募研究の内訳1年目"
        		,"経費：公募研究の小計2年目"
        		,"経費：公募研究の内訳2年目"
        		,"経費：公募研究の小計3年目"
        		,"経費：公募研究の内訳3年目"
        		,"経費：公募研究の小計4年目"
        		,"経費：公募研究の内訳4年目"
        		,"経費：公募研究の小計5年目"
        		,"経費：公募研究の内訳5年目"
        		,"経費：公募研究の小計6年目"
        		,"経費：公募研究の内訳6年目"
        		
        		
        		//ADD START 2007/07/04 BIS 趙一非
                
                ,"経費：応募金額の合計1年目"     
                      
                ,"経費：応募金額の合計2年目"          
                      
                ,"経費：応募金額の合計3年目"         
                         
                ,"経費：応募金額の合計4年目"          
                        
                ,"経費：応募金額の合計5年目"          
                         
                ,"経費：応募金額の合計6年目"          
                
                
                
                
                //ADD END 2007/07/04 BIS 趙一非
        		
        		
        		,"領域代表者：郵便番号"
        		,"領域代表者：住所"
        		,"領域代表者：電話番号"
        		,"領域代表者：FAX番号"
        		,"領域代表者：Email"
        		,"事務担当者「漢字等」（姓）"
        		,"事務担当者「漢字等」（名）"
        		,"事務担当者「フリガナ」（姓）"
        		,"事務担当者「フリガナ」（名）"
        		,"事務担当者：所属研究機関コード"
        		,"事務担当者：部局コード"
        		,"事務担当者：部局名"
        		,"事務担当者：職名コード"
        		,"事務担当者：職名"
        		,"事務担当者：郵便番号"
        		,"事務担当者：住所"
        		,"事務担当者：電話番号"
        		,"事務担当者：FAX番号"
        		,"事務担当者：Email"
        		,"関連研究分野研究者1：氏名"
        		,"関連研究分野研究者1：所属機関名"
        		,"関連研究分野研究者1：部局"
        		,"関連研究分野研究者1：職"
        		,"関連研究分野研究者1：現在の専門"
        		,"関連研究分野研究者1：勤務先電話番号"
        		,"関連研究分野研究者1：自宅電話番号"
        		,"関連研究分野研究者2：氏名"
        		,"関連研究分野研究者2：所属機関名"
        		,"関連研究分野研究者2：部局"
        		,"関連研究分野研究者2：職"
        		,"関連研究分野研究者2：現在の専門"
        		,"関連研究分野研究者2：勤務先電話番号"
        		,"関連研究分野研究者2：自宅電話番号"
        		,"関連研究分野研究者3：氏名"
        		,"関連研究分野研究者3：所属機関名"
        		,"関連研究分野研究者3：部局"
        		,"関連研究分野研究者3：職"
        		,"関連研究分野研究者3：現在の専門"
        		,"関連研究分野研究者3：勤務先電話番号"
        		,"関連研究分野研究者3：自宅電話番号"
            };

		list.set(0, Arrays.asList(columnArray));

        StringBuffer finalResult = new StringBuffer();
		//CSVデータを生成する
		for(int i = 0;i < list.size(); i++){
			List line = (List)list.get(i);
			Iterator iterator = line.iterator();

			//1行分のCSVデータを作成する
			CSVLine csvline = new CSVLine();

			//1行分のデータ項目数くりかえす			
			while(iterator.hasNext()){
				String col = (String)iterator.next();
				csvline.addItem(col);
			}

			finalResult.append(csvline.getLine().toString()); //関連研究分野研究者3：自宅電話番号 
            finalResult.append(LINE_SHIFT).toString();		
		}
		return finalResult.toString();

    }
       
//2006/04/11　追加ここから
    /**
     * 若手研究（スタートアップ）応募情報パンチデータを作成する
     * 
     * @param connection
     * @return パンチデータ情報(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataWakastartKenkyu(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

    	log.info("パンチデータ処理開始。");
    	
        String query = "SELECT "
        		
                + " SUBSTR(A.JIGYO_ID,5,2) KENKYUSHUMOKU"	//研究種目番号（2バイト）
                + ",NVL(A.SHINSA_KUBUN,'1') SHINSA_KUBUN" 	//審査区分(1バイト)
                + ",NVL(A.SHINSEI_KUBUN,'1') SHINSEI_KUBUN" //新規継続区分(1バイト)
                + ",A.SHOZOKU_CD SHOZOKU_CD_DAIHYO" 		//所属機関コード(5バイト)
                + ",A.BUNKASAIMOKU_CD" 						//細目コード（4バイト）
                + ",CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
                + "      WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"	//分割番号（1バイト）
                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER" 					//整理番号(4バイト)申請番号のハイフン以下
                + ",CASE WHEN A.SHINSARYOIKI_CD IS NULL THEN '  ' "
                + "      ELSE LPAD(TRIM(A.SHINSARYOIKI_CD),2,'0') END SHINSARYOIKI_CD" //分野（2バイト）
                + ",LPAD(A.EDITION,2,'0') EDITION"		//版数
                + ",LPAD(A.KEIHI1,7,'0') KEIHI1" //1年目研究経費(7バイト)
                + ",LPAD(A.KEIHI2,7,'0') KEIHI2" //2年目研究経費(7バイト)
                + ",NVL(A.BUNTANKIN_FLG,' ') BUNTANKINFLG" //分担金の有無
                + ",RPAD(A.KADAI_NAME_KANJI,80,'　') KADAINAMEKANJI" //研究課題名（和文）（80バイト）
                + ",LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') KENKYUNINZU" //研究者数（2バイト）
                + ",NVL(A.KAIJIKIBO_FLG_NO,' ') KAIJIKIBO_FLG" //開示希望の有無（1バイト）
                + ",NVL(TO_CHAR(A.SHINSEI_FLG_NO),' ') SHINSEI_FLG_NO" //研究計画最終年度前年度の応募（1バイト）
//                + ",CASE WHEN A.KEYWORD_CD IS NULL THEN ' ' ELSE ' ' END KEYWORD_CD" //細目表キーワード（1バイト）
                + ",' ' KEYWORD_CD" //細目表キーワード（1バイト）
                + ",CASE WHEN A.OTHER_KEYWORD IS NULL THEN '　' ELSE TO_MULTI_BYTE(A.OTHER_KEYWORD) END OTHER_KEYWORD" //細目表以外のキーワード（50バイト）
                + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG" 		//代表者分担者別(1バイト)
				+ ",RPAD(B.NAME_KANA_SEI,32,'　') NAMEKANASEI"	//氏名（フリガナー姓）(32バイト)
				+ ",RPAD(NVL(B.NAME_KANA_MEI, '　'),32,'　') NAMEKANAMEI"	//氏名（フリガナー名）(32バイト)
				+ ",RPAD(B.NAME_KANJI_SEI,32,'　') NAMEKANJISEI"	//氏名（漢字等ー姓）(32バイト)
				+ ",RPAD(NVL(B.NAME_KANJI_MEI, '　'),32,'　') NAMEKANJIMEI"	//氏名（漢字等ー名）(32バイト)
                + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD" 	//所属機関名(コード)研究組織表管理テーブルより(5バイト)
                + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD " 	//部局名(コード)(3バイト)
                + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD" 	//職名コード(2バイト)
                + ",LPAD(B.KENKYU_NO, 8,' ')  KENKYUNO" 	//研究者番号(8バイト)
                + ",LPAD(NVL(B.KEIHI,0), 7,'0') KEIHI" 		//研究経費(7バイト)
                + ",LPAD(B.EFFORT, 3,'0') EFFORT" 			//エフォート(3バイト)
                + ",LPAD(NVL(TO_CHAR(A.SAIYO_DATE,'YYYYMMDD'),' '),8,' ') SAIYO_DATE" //所属研究機関採用年月日(8バイト)
                + ",LPAD(A.KINMU_HOUR, 2,'0') KINMU_HOUR" 	//１週間あたりの勤務時間数(2バイト)
                + ",LPAD(A.NAIYAKUGAKU, 4,'0') NAIYAKUGAKU" //特別研究員奨励費内約額(4バイト)
                //2007/02/08 苗　追加ここから
                + ",NVL(A.SHOREIHI_NO_NENDO, '  ') SHOREIHI_NO_NENDO" //特別研究員奨励費課題番号-年度(2バイト)
                + ",NVL(A.SHOREIHI_NO_SEIRI, '     ') SHOREIHI_NO_SEIRI" //特別研究員奨励費課題番号-整理番号(5バイト)
                //2007/02/08　苗　追加ここまで

                + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
                + " WHERE DEL_FLG = 0" 
                + "   AND A.SYSTEM_NO = B.SYSTEM_NO"
                + "   AND JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_WAKATESTART
                + "   AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.BUNKASAIMOKU_CD, A.BUNKATSU_NO, SEIRINUMBER"; //事業ID,機関ー細目番号ー分割番号ー整理番号

        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = null; //new ArrayList();
        //List finalList = new ArrayList();

        //-----------------------
        // リスト取得
        //-----------------------
        try {
            list = SelectUtil.select(connection, query);
        } catch (DataAccessException e) {
            throw new ApplicationException("データベースアクセス中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("申請データテーブルに1件もデータがありません。", e);
        }

        //最終的にreturnするStringを宣言する
        //String finalResult = new String();
        StringBuffer finalResult = new StringBuffer();
//2006/04/21 追加ここから        
        final char charToBeAdded = '　'; //全角スペース
        final int otherKeywordLength = 25; //細目表意外のキーワードの表示文字数
        final char HOSHI = '★';

        //半角全角コンバータ
        HanZenConverter hanZenConverter = new HanZenConverter();
//苗　追加ここまで
        for (int i = 0; i < list.size(); i++) {
        	
			if(i % 1000 == 0) {
				log.info("パンチデータ処理数::" + i + "件");
			}
        	
        	
            //DBから取得したList型listをString型にして、そのStringのListを作る
            Map recordMap = (Map) list.get(i);
            String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //研究種目番号
        	String shinsaKbn = "1";//審査区分
        	String shinseiKbn = "1";//新規継続区分
            String syozokuCdDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");//所属機関コード
            String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKU_CD");//細目コード
            String bunkatsNoAB = (String) recordMap.get("BUNKATSUNO_AB");//分割番号
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");//整理番号
            String kaigaiBunya = (String) recordMap.get("SHINSARYOIKI_CD");//分野
            String edition = (String)recordMap.get("EDITION");//版数
        	String keihi1 = (String) recordMap.get("KEIHI1");//1年目研究経費
            String keihi2 = (String) recordMap.get("KEIHI2");//2年目研究経費
            String buntanKinFlg = "2";//分担金の有無
            String kadaiNameKanji = (String) recordMap.get("KADAINAMEKANJI");//研究課題名（和文）
            String kenkyuNinzu = "01";//研究者数
        	String kaijikibo = (String) recordMap.get("KAIJIKIBO_FLG");//開示希望の有無
            String shinseiFlg = "2";//研究計画最終年度前年度の応募
            String keywordCd = (String) recordMap.get("KEYWORD_CD");;//細目表キーワード
            String otherKeyword = (String) recordMap.get("OTHER_KEYWORD");;//細目表以外のキーワード
            String buntanFlg = "1";//代表者分担者別
			String nameKanaSei = (String)recordMap.get("NAMEKANASEI");//氏名（フリガナー姓）
			String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");//氏名（フリガナー名）
			String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");//氏名（漢字等ー姓）
			String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");//氏名（漢字等ー名）
            String syozokuCd = (String) recordMap.get("SHOZOKUCD");//所属機関名(コード)研究組織表管理テーブルより
            String bukyokuCd = (String) recordMap.get("BUKYOKUCD");//部局名(コード)
            String syokusyuCd = (String) recordMap.get("SHOKUSHUCD");//職名コード
            String kenkyuNo = (String) recordMap.get("KENKYUNO");//研究者番号
        	String keihi = (String) recordMap.get("KEIHI");//研究経費
            String effort = (String) recordMap.get("EFFORT");//エフォート
            String saiyoDate = (String) recordMap.get("SAIYO_DATE");//所属研究機関採用年月日
            String kinmuHour = (String)recordMap.get("KINMU_HOUR");//１週間あたりの勤務時間数
            String naigakugaiku = (String) recordMap.get("NAIYAKUGAKU");//特別研究員奨励費内約額
//2007/02/08 苗　追加ここから
            String shoreihiNoNendo = (String) recordMap.get("SHOREIHI_NO_NENDO");//特別研究員奨励費課題番号-年度
            String shoreihiNoSeiri = (String) recordMap.get("SHOREIHI_NO_SEIRI");//特別研究員奨励費課題番号-整理番号
//2007/02/08　苗　追加ここまで            
            
            
            //DBより取得したレコードをGCの対象にする（大量データに対応するため）
            recordMap = null;
            list.set(i, null);
            
//2006/04/21 追加ここから            
            String ZenkakuOtherKeyword = hanZenConverter.convert(otherKeyword);
            char[] cArray = ZenkakuOtherKeyword.toCharArray();
            for (int j = 0; j < cArray.length; j++) {
                if (StringUtil.isHankaku(cArray[j])) {
                    cArray[j] = HOSHI; //半角の場合は置き換え
                  }

              }
              //文字列に戻す
              String checkedOtherKeyword = new String(cArray);
              if (checkedOtherKeyword.length() > otherKeywordLength){
                checkedOtherKeyword = checkedOtherKeyword.substring(0, otherKeywordLength);
              }

              //細目以外のキーワードを25文字の文字の固定長にする
              while (checkedOtherKeyword.length() < otherKeywordLength) {
                checkedOtherKeyword += charToBeAdded;
              }
//苗　ここまで
            
			finalResult.append(jigyoId)
            		   .append(shinsaKbn)
            		   .append(shinseiKbn)
            		   .append(syozokuCdDaihyo)
           			   .append(bunkaSaimokuCd)
            		   .append(bunkatsNoAB)
            		   .append(seiriNumber)
            		   .append(kaigaiBunya)
            		   .append(edition)
            		   .append(keihi1)
            		   .append(keihi2)
            		   .append(buntanKinFlg)
            		   .append(kadaiNameKanji)
            		   .append(kenkyuNinzu)
            		   .append(kaijikibo)
            		   .append(shinseiFlg)
            		   .append(keywordCd)
            		   .append(checkedOtherKeyword)
            		   .append(buntanFlg)
					   .append(nameKanaSei)
					   .append(nameKanaMei)
					   .append(nameKanjiSei)
					   .append(nameKanjiMei)
            		   .append(syozokuCd)
            		   .append(bukyokuCd)
            		   .append(syokusyuCd)
            		   .append(kenkyuNo)
            		   .append(keihi)
            		   .append(effort)
            		   .append(saiyoDate)
            		   .append(kinmuHour)
            		   .append(naigakugaiku)
                       //2007/02/08 苗　追加ここから
                       .append(shoreihiNoNendo)
                       .append(shoreihiNoSeiri)
                       //2007/02/08　苗　追加ここまで
            		   .append(LINE_SHIFT).toString();

            //finalList.add(record);
            //finalResult = finalResult + (String) finalList.get(i);
        }
        log.info("パンチデータ処理終了。");
        return finalResult.toString();
    }
    
    /**
     * 特別研究促進費応募情報パンチデータを作成する
     * 
     * @param connection
     * @return パンチデータ情報(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataSokushinKenkyu(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

    	log.info("パンチデータ処理開始。");
    	
        String query = "SELECT "
        		
                + " SUBSTR(A.JIGYO_ID,5,2) KENKYUSHUMOKU"	//研究種目番号（2バイト）
                + ",NVL(A.SHINSA_KUBUN,' ') SHINSA_KUBUN" 	//審査区分(1バイト)
                + ",NVL(A.SHINSEI_KUBUN,'1') SHINSEI_KUBUN" //新規継続区分(1バイト)
                + ",A.SHOZOKU_CD SHOZOKU_CD_DAIHYO" 		//所属機関コード(5バイト)
                + ",A.BUNKASAIMOKU_CD" 						//細目コード（4バイト）
                + ",CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
                + "      WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"	//分割番号（1バイト）
//UPDATE　START 2007/07/25 BIS 金京浩   //出力ファイル仕様20070720.xlsについて  
                /*
                + ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
                + "      WHEN A.BUNKATSU_NO = '2' THEN '2' ELSE ' ' END BUNKATSUNO_12"	//分割番号（1バイト）
                */
                
                + ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
                + " 	 WHEN A.BUNKATSU_NO = '2' THEN '2' "
                + "		 WHEN A.BUNKATSU_NO = '3' THEN '3' "
                + "		 WHEN A.BUNKATSU_NO = '4' THEN '4' "
                + "      WHEN A.BUNKATSU_NO = '5' THEN '5' ELSE ' ' END BUNKATSUNO_12"	//分割番号（1バイト）                
//UPDATE　END　 2007/07/25 BIS 金京浩                
                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER" 					//整理番号(4バイト)申請番号のハイフン以下
                //2007/02/14 苗　追加ここから
                + ",NVL(A.SHINSARYOIKI_CD,'  ') SHINSARYOIKI_CD"                        //審査希望分野コード（2バイト）
                //2007/02/14　苗　追加ここまで
//UPDATE　START 2007/07/25 BIS 金京浩   //出力ファイル仕様20070720.xlsについて                  
                /*
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.KENKYU_TAISHO,' ') END KENKYU_TAISHO" //研究対象の類型（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE NVL(A.KAIGAIBUNYA_CD,'  ') END KAIGAIBUNYA_CD" //海外分野（2バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"		//版数
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE '0000000' END KEIHI5" //5年目研究経費(7バイト)
                + ",CASE WHEN A.OTHER_KEYWORD IS NULL THEN '        ' ELSE '        '  END KADAI_NO_KEIZOKU" //継続分の研究課題番号（8バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //分担金の有無
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD('　',80,'　') ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI" //研究課題名（和文）（80バイト）
                */
                
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.KENKYU_TAISHO,' ') END KENKYU_TAISHO" //研究対象の類型（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE NVL(A.KAIGAIBUNYA_CD,'  ') END KAIGAIBUNYA_CD" //海外分野（2バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"		//版数
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4年目研究経費(7バイト)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE '0000000' END KEIHI5" //5年目研究経費(7バイト)
                + ",CASE WHEN A.OTHER_KEYWORD IS NULL THEN '        ' ELSE '        '  END KADAI_NO_KEIZOKU" //継続分の研究課題番号（8バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //分担金の有無
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD('　',80,'　') ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI" //研究課題名（和文）（80バイト）
               
// UPDATE　END　 2007/07/25 BIS 金京浩                 
//2006/05/15 追加ここから                
//                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') END KENKYUNINZU" //研究者数（2バイト）
                + " ,CASE WHEN A.KENKYU_NINZU IS NULL THEN "
                + "     (CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00155' THEN LPAD('1',2,'0') ELSE "
                + "         (CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00156' THEN  LPAD('1',2,'0') ELSE '  ' END )" 
                + "     END ) "
                + " ELSE "
                + "     (CASE WHEN  B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') END )"
                + " END KENKYUNINZU" //研究者数（2バイト）
//苗　追加ここまで
//UPDATE　START 2007/07/25 BIS 金京浩   //出力ファイル仕様20070720.xlsについて   
                /*
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBO_FLG" //開示希望の有無（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE '2' END SHINSEI_FLG_NO" //研究計画最終年度前年度の応募（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_SAISYU,'        ') END KADAI_NO_SAISYU" //最終年度課題番号（8バイト）
                */
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBO_FLG" //開示希望の有無（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE '2' END SHINSEI_FLG_NO" //研究計画最終年度前年度の応募（1バイト）
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_SAISYU,'        ') END KADAI_NO_SAISYU" //最終年度課題番号（8バイト）
//UPDATE　END　 2007/07/25 BIS 金京浩                
                + ",NVL(A.KEYWORD_CD,' ') KEYWORD_CD" 		//細目表キーワード（1バイト）
                + ",CASE WHEN A.OTHER_KEYWORD IS NULL THEN '　' ELSE TO_MULTI_BYTE(A.OTHER_KEYWORD) END OTHER_KEYWORD" //細目表以外のキーワード（50バイト）
                + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG" 		//代表者分担者別(1バイト)
				+ ",RPAD(B.NAME_KANA_SEI,32,'　') NAMEKANASEI"	//氏名（フリガナー姓）(32バイト)
				+ ",RPAD(NVL(B.NAME_KANA_MEI, '　'),32,'　') NAMEKANAMEI"	//氏名（フリガナー名）(32バイト)
				+ ",RPAD(B.NAME_KANJI_SEI,32,'　') NAMEKANJISEI"	//氏名（漢字等ー姓）(32バイト)
				+ ",RPAD(NVL(B.NAME_KANJI_MEI, '　'),32,'　') NAMEKANJIMEI"	//氏名（漢字等ー名）(32バイト)				
                + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD" 	//所属機関名(コード)研究組織表管理テーブルより(5バイト)
                + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD " 	//部局名(コード)(3バイト)
                + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD" 	//職名コード(2バイト)
                + ",LPAD(B.KENKYU_NO, 8,' ')  KENKYUNO" 	//研究者番号(8バイト)
                + ",LPAD(NVL(B.KEIHI,0), 7,'0') KEIHI" 		//研究経費(7バイト)
                + ",LPAD(B.EFFORT, 3,'0') EFFORT" 			//エフォート(3バイト)
                + ",NVL(A.OUBO_SHIKAKU,' ') OUBO_SHIKAKU"   //応募資格                
                + ",CASE WHEN A.OUBO_SHIKAKU = '1' THEN LPAD(NVL(TO_CHAR(A.SIKAKU_DATE,'YYYYMMDD'),' '),8,' ') ELSE '        ' END SIKAKU_DATE_SIN" //新たに科研費の応募資格を得た年月日(8バイト)
                + ",CASE WHEN A.OUBO_SHIKAKU = '2' THEN LPAD(NVL(TO_CHAR(A.SIKAKU_DATE,'YYYYMMDD'),' '),8,' ') ELSE '        ' END SIKAKU_DATE_SAYI" //再び科研費の応募資格を得た年月日(8バイト)
//2006/04/15 追加ここから     
                + ",CASE WHEN A.OUBO_SHIKAKU = '2' THEN TO_MULTI_BYTE(NVL(A.SYUTOKUMAE_KIKAN,' ')) ELSE TO_MULTI_BYTE(' ') END SYUTOKUMAE_KIKAN" //応募資格を得る前の所属研究機関名(8バイト)
//苗　ここまで                
                + ",CASE WHEN A.OUBO_SHIKAKU = '3' THEN LPAD(NVL(TO_CHAR(A.IKUKYU_START_DATE,'YYYYMMDD'),' '),8,' ') ELSE '        ' END IKUKYU_START_DATE" //育休等の取得期間（開始年月日）(8バイト)
                + ",CASE WHEN A.OUBO_SHIKAKU = '3' THEN LPAD(NVL(TO_CHAR(A.IKUKYU_END_DATE,'YYYYMMDD'),' '),8,' ') ELSE '        ' END IKUKYU_END_DATE" //育休等の取得期間（終了年月日）(8バイト)
                
                + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
                + " WHERE DEL_FLG = 0" 
                + "   AND A.SYSTEM_NO = B.SYSTEM_NO"
                + "   AND JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI
                + "   AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.BUNKASAIMOKU_CD, A.BUNKATSU_NO, SEIRINUMBER, BUNTANFLG, B.SEQ_NO"; //事業ID,機関ー細目番号ー分割番号ー整理番号、代表者分担者フラグ順、シーケンス番号順

        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = null; //new ArrayList();
        //List finalList = new ArrayList();

        //-----------------------
        // リスト取得
        //-----------------------
        try {
            list = SelectUtil.select(connection, query);
        } catch (DataAccessException e) {
            throw new ApplicationException("データベースアクセス中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("申請データテーブルに1件もデータがありません。", e);
        }

        //最終的にreturnするStringを宣言する
        //String finalResult = new String();
        StringBuffer finalResult = new StringBuffer();

//2006/04/15 追加ここから    
		final char charToBeAdded = '　'; //全角スペース
		final int syutokumaeKikanLength = 40; //応募資格を得る前の所属研究機関名の表示文字数
        final int otherKeywordLength = 25; //細目表意外のキーワードの表示文字数
		final char HOSHI = '★';

		//半角全角コンバータ
		HanZenConverter hanZenConverter = new HanZenConverter();
//苗　ここまで  
		
        for (int i = 0; i < list.size(); i++) {
        	
			if(i % 1000 == 0) {
				log.info("パンチデータ処理数::" + i + "件");
			}
        	
        	
            //DBから取得したList型listをString型にして、そのStringのListを作る
            Map recordMap = (Map) list.get(i);
            String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //研究種目番号
        	String shinsaKbn = (String) recordMap.get("SHINSA_KUBUN");//審査区分
        	String shinseiKbn = "1";//新規継続区分
            String syozokuCdDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");//所属機関コード
            String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKU_CD");//細目コード
            String bunkatsNoAB = (String) recordMap.get("BUNKATSUNO_AB");//分割番号
            String bunkatsNo12 = (String) recordMap.get("BUNKATSUNO_12");//分割番号
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");//整理番号
            //2007/02/14 苗　追加ここから
            String shinsaRyoikiCd = (String) recordMap.get("SHINSARYOIKI_CD");//審査希望分野コード
            //2007/02/14　苗　追加ここまで
            String kenkyuTaisho = " ";//研究対象の類型
            String kaigaiBunya = "  ";//海外分野
            String edition = (String)recordMap.get("EDITION");//版数
        	String keihi1 = (String) recordMap.get("KEIHI1");//1年目研究経費
            String keihi2 = (String) recordMap.get("KEIHI2");//2年目研究経費
            String keihi3 = (String) recordMap.get("KEIHI3");//3年目研究経費
            String keihi4 = (String) recordMap.get("KEIHI4");//4年目研究経費
            String keihi5 = (String) recordMap.get("KEIHI5");;//5年目研究経費
            String kadaiNoKeizoku = (String) recordMap.get("KADAI_NO_KEIZOKU");//継続分の研究課題番号
            String buntanKinFlg = (String) recordMap.get("BUNTANKINFLG");//分担金の有無
            String kadaiNameKanji = (String) recordMap.get("KADAINAMEKANJI");//研究課題名（和文）
            String kenkyuNinzu = (String) recordMap.get("KENKYUNINZU");//研究者数
        	String kaijikibo = (String) recordMap.get("KAIJIKIBO_FLG");//開示希望の有無
            String shinseiFlg = (String) recordMap.get("SHINSEI_FLG_NO");//研究計画最終年度前年度の応募
            String kadaiNoSaisyu = (String) recordMap.get("KADAI_NO_KEIZOKU");//最終年度課題番号
            String keywordCd = (String) recordMap.get("KEYWORD_CD");//細目表キーワード
            String otherKeyword = (String) recordMap.get("OTHER_KEYWORD");//細目表以外のキーワード
            String buntanFlg = (String) recordMap.get("BUNTANFLG");//代表者分担者別
			String nameKanaSei = (String)recordMap.get("NAMEKANASEI");//氏名（フリガナー姓）
			String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");//氏名（フリガナー名）
			String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");//氏名（漢字等ー姓）
			String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");//氏名（漢字等ー名）
            String syozokuCd = (String) recordMap.get("SHOZOKUCD");//所属機関名(コード)研究組織表管理テーブルより
            String bukyokuCd = (String) recordMap.get("BUKYOKUCD");//部局名(コード)
            String syokusyuCd = (String) recordMap.get("SHOKUSHUCD");//職名コード
            String kenkyuNo = (String) recordMap.get("KENKYUNO");//研究者番号
        	String keihi = (String) recordMap.get("KEIHI");//研究経費
            String effort = (String) recordMap.get("EFFORT");//エフォート
            String ouboShikaka = (String) recordMap.get("OUBO_SHIKAKU");//応募資格
            String sikakuDateSin = (String) recordMap.get("SIKAKU_DATE_SIN");//新たに科研費の応募資格を得た年月日
            String sikakuDateSayi = (String) recordMap.get("SIKAKU_DATE_SAYI");//再び科研費の応募資格を得た年月日
//2006/04/15 追加ここから              
            String syutokumaeKikan = (String) recordMap.get("SYUTOKUMAE_KIKAN");//応募資格を得る前の所属研究機関名
//苗　ここまで              
            String ikukyuStartDate = (String) recordMap.get("IKUKYU_START_DATE");//育休等の取得期間（開始年月日）
            String ikukyuEndDate = (String) recordMap.get("IKUKYU_END_DATE");////育休等の取得期間（終了年月日）

            
            //DBより取得したレコードをGCの対象にする（大量データに対応するため）
            recordMap = null;
            list.set(i, null);

//2006/04/15 追加ここから              
			//---応募資格を得る前の所属研究機関名を半角から全角にする
			//charの配列に変換
            //TODO
			String syutokumaeKikanZen = hanZenConverter.convert(syutokumaeKikan);
			char[] ocArray = syutokumaeKikanZen.toCharArray();
			for (int j = 0; j < ocArray.length; j++) {
				if (StringUtil.isHankaku(ocArray[j])) {
					ocArray[j] = HOSHI; //半角の場合は置き換え
				}
			}
            
			//文字列に戻す
			String checkedSyutokumaeKikanZen = new String(ocArray);
			if (checkedSyutokumaeKikanZen.length() > syutokumaeKikanLength){
				checkedSyutokumaeKikanZen = checkedSyutokumaeKikanZen.substring(0, syutokumaeKikanLength);
			}

			//応募資格を得る前の所属研究機関名を40文字の文字の固定長にする
			while (checkedSyutokumaeKikanZen.length() < syutokumaeKikanLength) {
				checkedSyutokumaeKikanZen += charToBeAdded;
			}
//苗　ここまで              

//2006/04/21 追加ここから            
            String ZenkakuOtherKeyword = hanZenConverter.convert(otherKeyword);
            char[] cArray = ZenkakuOtherKeyword.toCharArray();
            for (int j = 0; j < cArray.length; j++) {
                if (StringUtil.isHankaku(cArray[j])) {
                    cArray[j] = HOSHI; //半角の場合は置き換え
                  }

              }
              //文字列に戻す
              String checkedOtherKeyword = new String(cArray);
              if (checkedOtherKeyword.length() > otherKeywordLength){
                checkedOtherKeyword = checkedOtherKeyword.substring(0, otherKeywordLength);
              }

              //細目以外のキーワードを25文字の文字の固定長にする
              while (checkedOtherKeyword.length() < otherKeywordLength) {
                checkedOtherKeyword += charToBeAdded;
              }
//苗　ここまで
			
			finalResult.append(jigyoId)
            		   .append(shinsaKbn)
            		   .append(shinseiKbn)
            		   .append(syozokuCdDaihyo)
           			   .append(bunkaSaimokuCd)
            		   .append(bunkatsNoAB)
            		   .append(bunkatsNo12)
            		   .append(seiriNumber)
                       //2007/02/14 苗　追加ここから
                       .append(shinsaRyoikiCd)//審査希望分野コード
                       //2007/02/14　苗　追加ここまで
            		   .append(kenkyuTaisho)
            		   .append(kaigaiBunya)
            		   .append(edition)
            		   .append(keihi1)
            		   .append(keihi2)
            		   .append(keihi3)
            		   .append(keihi4)
            		   .append(keihi5)
            		   .append(kadaiNoKeizoku)
            		   .append(buntanKinFlg)
            		   .append(kadaiNameKanji)
            		   .append(kenkyuNinzu)
            		   .append(kaijikibo)
            		   .append(shinseiFlg)
            		   .append(kadaiNoSaisyu)
            		   .append(keywordCd)
            		   .append(checkedOtherKeyword)
            		   .append(buntanFlg)
					   .append(nameKanaSei)
					   .append(nameKanaMei)
					   .append(nameKanjiSei)
					   .append(nameKanjiMei)
            		   .append(syozokuCd)
            		   .append(bukyokuCd)
            		   .append(syokusyuCd)
            		   .append(kenkyuNo)
            		   .append(keihi)
            		   .append(effort)
            		   .append(ouboShikaka)
            		   .append(sikakuDateSin)
            		   .append(sikakuDateSayi)
//2006/04/15 追加ここから              		   
            		   .append(checkedSyutokumaeKikanZen)
//苗　ここまで              		   
            		   .append(ikukyuStartDate)
            		   .append(ikukyuEndDate)
            		   .append(LINE_SHIFT).toString();

            //finalList.add(record);
            //finalResult = finalResult + (String) finalList.get(i);
        }
        log.info("パンチデータ処理終了。");
        return finalResult.toString();
    }

//苗　追加ここまで  
    
//2006/05/25 追加ここから
    /**
     * 若手研究（スタートアップ）評定表パンチデータを作成する
     * 
     * @param connection
     * @return パンチデータ情報(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataWakateHyotei(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

        String query = "SELECT "
                + "'14' KENKYUSHUMOKU"    //研究種目番号（2バイト)  
                + ",'1' SHINSA_KUBUN"   //審査区分(1バイト)  
                + ",NVL(SUBSTR(B.UKETUKE_NO,1,5),'     ') SHOZOKUCDDAIHYO" //所属機関コード(5バイト)
                + ",NVL(A.BUNKASAIMOKU_CD,'    ') BUNKASAIMOKUCD"                       //細目コード（4バイト）
                + ",CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
                + "      WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"  //分割番号（1バイト）
                + ",' ' BUNKATSUNO_12"  //分割番号（1バイト）
                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER"                   //整理番号(4バイト)申請番号のハイフン以下
                + ",NVL(SUBSTR(B.SHINSAIN_NO,6,2), '  ') SHINSAINNO" //審査員枝番(審査員番号の下２桁）（2バイト）
                + ",CASE WHEN B.RIGAI = '1' THEN '1' ELSE ' '  END RIGAIKANKEI" //利害関係
                + ",NVL(B.JUYOSEI, ' ') JUYOSEI" //研究課題の学術的重要性
                + ",NVL(B.KENKYUKEIKAKU, ' ') KENKYUKEIKAKU" //研究計画・方法の妥当性
                + ",NVL(B.DOKUSOSEI, ' ') DOKUSOSEI" //独創性及び革新性
                + ",NVL(B.HAKYUKOKA, ' ') HAKYUKOKA" //波及効果
                + ",NVL(B.SUIKONORYOKU, ' ') SUIKONORYOKU" //遂行能力
                + ",CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '111') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_HOGA" //適切性・萌芽
                + ",CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '043')OR(SUBSTR(A.JIGYO_ID,5,3) = '053') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_KAIGAI" //適切性・萌芽
                + ",CASE WHEN B.KEKKA_TEN = '-' THEN ' ' ELSE NVL(B.KEKKA_TEN, ' ') END KEKKATEN" //総合評価
                + ",CASE WHEN B.JINKEN = '3' THEN '3' ELSE ' ' END JINKEN" //人権
                + ",CASE WHEN B.BUNTANKIN = '3' THEN '3' ELSE ' ' END BUNTANKIN" //分担金
                + ",CASE WHEN B.DATO = '0' THEN ' ' ELSE NVL(B.DATO, ' ') END DATO" //経費の妥当性
                + ",CASE WHEN B.COMMENTS IS NULL THEN '　' ELSE TO_MULTI_BYTE(B.COMMENTS) END COMMENTS" //審査意見
                + ",CASE WHEN B.OTHER_COMMENT IS NULL THEN '　' ELSE TO_MULTI_BYTE(B.OTHER_COMMENT) END OTHER_COMMENT" //コメント
                + ",NVL(B.SHINSA_JOKYO, '0') SHINSAJOKYO" //審査状況
                + " FROM SHINSEIDATAKANRI A, SHINSAKEKKA B"
                + " WHERE B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_WAKATESTART
                + "   AND NOT B.SHINSAIN_NO LIKE '@%'"  //ダミー審査員を除外する(ダミー審査員番号が＠付いてる)
                + "   AND A.SYSTEM_NO = B.SYSTEM_NO" 
                + "   AND A.JIGYO_KUBUN = B.JIGYO_KUBUN "
                + "   AND A.JIGYO_KUBUN IN (" + IJigyoKubun.JIGYO_KUBUN_WAKATESTART +  ")"
    //          
                + "   AND A.DEL_FLG = 0"
                + "   AND TO_NUMBER(A.JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY KENKYUSHUMOKU, SHINSA_KUBUN, SHOZOKUCDDAIHYO, BUNKASAIMOKUCD, A.BUNKATSU_NO, SHINSAINNO, SEIRINUMBER"; //所属機関コード、,整理番号、審査員枝番

        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = new ArrayList();
        //List finalList = new ArrayList();

        //-----------------------
        // リスト取得
        //-----------------------
        try {
            list = SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException("データベースアクセス中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("データベースに1件もデータがありません。", e);
        }

        //最終的にreturnするStringを宣言する
        StringBuffer finalResult = new StringBuffer();
        char charToBeAdded = '　'; //全角スペース
        final int commentLength = 150; //コメントの表示文字数
               int otherCommentLength = 50; //コメントの表示文字数
        final char HOSHI = '★';

        //半角全角コンバータ
        HanZenConverter hanZenConverter = new HanZenConverter();

        for (int i = 0; i < list.size(); i++) {

            //long start = System.currentTimeMillis();

            //DBから取得したList型listをString型にして、そのStringのListを作る
            Map recordMap = (Map) list.get(i);
            String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //研究種目番号
            String shinsaKbn = (String) recordMap.get("SHINSA_KUBUN");
            String shozokuCd = (String) recordMap.get("SHOZOKUCDDAIHYO");
            String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKUCD");
            String bunkatsNoAB = (String) recordMap.get("BUNKATSUNO_AB");
            String bunkatsNo12 = (String) recordMap.get("BUNKATSUNO_12");
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");
            String shinsainNo = (String) recordMap.get("SHINSAINNO");
            String rigai = (String) recordMap.get("RIGAIKANKEI");
            String juyosei = (String) recordMap.get("JUYOSEI");
            String kenkyuKeikaku = (String) recordMap.get("KENKYUKEIKAKU");
            String dokusosei = (String) recordMap.get("DOKUSOSEI");
            String hakyukoka = (String) recordMap.get("HAKYUKOKA");
            String suikonoryoku = (String) recordMap.get("SUIKONORYOKU");
            String tekisetsuHoga = (String) recordMap.get("TEKISETU_HOGA");
            String tekisetsuKaigai = (String) recordMap.get("TEKISETU_KAIGAI");
            String kekkaTen = (String) recordMap.get("KEKKATEN");            
            String jinken = (String) recordMap.get("JINKEN");
            String buntankin = (String) recordMap.get("BUNTANKIN");
            String dato = (String) recordMap.get("DATO");
            String recordComments = (String) recordMap.get("COMMENTS");
            String recordOtherComment = (String) recordMap.get("OTHER_COMMENT");
            String shinsaJokyo = (String) recordMap.get("SHINSAJOKYO");

            //DBより取得したレコードをGCの対象にする（大量データに対応するため）
            recordMap = null;
            list.set(i, null);

            //---意見を半角から全角にする
            //charの配列に変換
            String ZenkakuComments = hanZenConverter.convert(recordComments);
            char[] cArray = ZenkakuComments.toCharArray();
            for (int j = 0; j < cArray.length; j++) {
                if (StringUtil.isHankaku(cArray[j])) {
                    cArray[j] = HOSHI; //半角の場合は置き換え
                }

            }
            //文字列に戻す
            String checkedZenkakuComments = new String(cArray);
            if (checkedZenkakuComments.length() > commentLength){
                checkedZenkakuComments = checkedZenkakuComments.substring(0, commentLength);
            }

            //意見を150文字の文字の固定長にする
            while (checkedZenkakuComments.length() < commentLength) {
                checkedZenkakuComments += charToBeAdded;
            }
            //意見ここまで


            //---コメントを半角から全角にする
            //charの配列に変換
            String ZenOtherComment = hanZenConverter.convert(recordOtherComment);
            char[] ocArray = ZenOtherComment.toCharArray();
            for (int j = 0; j < ocArray.length; j++) {
                if (StringUtil.isHankaku(ocArray[j])) {
                    ocArray[j] = HOSHI; //半角の場合は置き換え
                }

            }
            //文字列に戻す
            String checkedZenOtherComment = new String(ocArray);
            if (checkedZenOtherComment.length() > otherCommentLength){
                checkedZenOtherComment = checkedZenOtherComment.substring(0, otherCommentLength);
            }

            //コメントを50文字の文字の固定長にする
            while (checkedZenOtherComment.length() < otherCommentLength) {
                checkedZenOtherComment += charToBeAdded;
            }

            //最終結果にレコード（本行）を文字列結合
            finalResult.append(jigyoId)
                       .append(shinsaKbn)
                       .append(shozokuCd)
                       .append(bunkaSaimokuCd)
                       .append(bunkatsNoAB)
                       .append(bunkatsNo12)
                       .append(seiriNumber)
                       .append(shinsainNo)
                       .append(rigai)
                       .append(juyosei)
                       .append(kenkyuKeikaku)
                       .append(dokusosei)
                       .append(hakyukoka)
                       .append(suikonoryoku)
                       .append(tekisetsuHoga)
                       .append(tekisetsuKaigai)
                       .append(kekkaTen)
                       .append(jinken)
                       .append(buntankin)
                       .append(dato)
                       .append(checkedZenkakuComments)
                       .append(checkedZenOtherComment)
                       .append(shinsaJokyo)
                       .append(LINE_SHIFT);

            //long stop = System.currentTimeMillis() - start;
            //System.out.println(i+"行の処理時間。" + stop);
            }
        return finalResult.toString();
    }
    
    /**
     * 若手研究（スタートアップ）評定表パンチデータを作成する
     * 
     * @param connection
     * @return パンチデータ情報(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataSokushinHyotei(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

        String query = "SELECT "
                + "'15' KENKYUSHUMOKU"    //研究種目番号（2バイト)  
                + ",CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00152' THEN '2'"
                + "      WHEN SUBSTR(A.JIGYO_ID,3,5) = '00153' THEN '3' " 
                + "      WHEN SUBSTR(A.JIGYO_ID,3,5) = '00154' THEN '4' " 
                + "      WHEN SUBSTR(A.JIGYO_ID,3,5) = '00155' THEN '5' " 
                + "      WHEN SUBSTR(A.JIGYO_ID,3,5) = '00156' THEN '6' " 
                + "      ELSE ' ' END SHINSA_KUBUN"  //審査区分（1バイト）
                + ",NVL(SUBSTR(B.UKETUKE_NO,1,5),'     ') SHOZOKUCDDAIHYO" //所属機関コード(5バイト)
                + ",NVL(A.BUNKASAIMOKU_CD,'    ') BUNKASAIMOKUCD"                       //細目コード（4バイト）
                + ",CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
                + "      WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"  //分割番号（1バイト）
//UPDATE　START 2007/07/25 BIS 金京浩   //出力ファイル仕様20070720.xlsについて                 
                /*
                + ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
                + "      WHEN A.BUNKATSU_NO = '2' THEN '2' ELSE ' ' END BUNKATSUNO_12"  //分割番号（1バイト）
                */
                
                + ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
                + "		 WHEN A.BUNKATSU_NO = '2' THEN '2' "
                + "		 WHEN A.BUNKATSU_NO = '3' THEN '3' "
                + "		 WHEN A.BUNKATSU_NO = '4' THEN '4' "
                + "      WHEN A.BUNKATSU_NO = '5' THEN '5' ELSE ' ' END BUNKATSUNO_12"  //分割番号（1バイト）
                 
                
//UPDATE  END　 2007/07/25 BIS 金京浩                 
                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER"                   //整理番号(4バイト)申請番号のハイフン以下
                + ",NVL(SUBSTR(B.SHINSAIN_NO,6,2), '  ') SHINSAINNO" //審査員枝番(審査員番号の下２桁）（2バイト）
                + ",CASE WHEN B.RIGAI = '1' THEN '1' ELSE ' '  END RIGAIKANKEI" //利害関係
                + ",NVL(B.JUYOSEI, ' ') JUYOSEI" //研究課題の学術的重要性
                + ",NVL(B.KENKYUKEIKAKU, ' ') KENKYUKEIKAKU" //研究計画・方法の妥当性
                + ",NVL(B.DOKUSOSEI, ' ') DOKUSOSEI" //独創性及び革新性
                + ",NVL(B.HAKYUKOKA, ' ') HAKYUKOKA" //波及効果
                + ",NVL(B.SUIKONORYOKU, ' ') SUIKONORYOKU" //遂行能力
                + ",CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '111') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_HOGA" //適切性・萌芽
                + ",CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '043')OR(SUBSTR(A.JIGYO_ID,5,3) = '053') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_KAIGAI" //適切性・萌芽
                + ",CASE WHEN B.KEKKA_TEN = '-' THEN ' ' ELSE NVL(B.KEKKA_TEN, ' ') END KEKKATEN" //総合評価
                + ",CASE WHEN B.JINKEN = '3' THEN '3' ELSE ' ' END JINKEN" //人権
                + ",CASE WHEN B.BUNTANKIN = '3' THEN '3' ELSE ' ' END BUNTANKIN" //分担金
                + ",CASE WHEN B.DATO = '0' THEN ' ' ELSE NVL(B.DATO, ' ') END DATO" //経費の妥当性
                + ",CASE WHEN B.COMMENTS IS NULL THEN '　' ELSE TO_MULTI_BYTE(B.COMMENTS) END COMMENTS" //審査意見
                + ",CASE WHEN B.OTHER_COMMENT IS NULL THEN '　' ELSE TO_MULTI_BYTE(B.OTHER_COMMENT) END OTHER_COMMENT" //コメント
                + ",NVL(B.SHINSA_JOKYO, '0') SHINSAJOKYO" //審査状況
                + " FROM SHINSEIDATAKANRI A, SHINSAKEKKA B"
                + " WHERE B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI
                + "   AND NOT B.SHINSAIN_NO LIKE '@%'"  //ダミー審査員を除外する(ダミー審査員番号が＠付いてる)
                + "   AND A.SYSTEM_NO = B.SYSTEM_NO" 
                + "   AND A.JIGYO_KUBUN = B.JIGYO_KUBUN "
                + "   AND A.JIGYO_KUBUN IN (" + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI +  ")"
    //          
                + "   AND A.DEL_FLG = 0"
                + "   AND TO_NUMBER(A.JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY KENKYUSHUMOKU, SHINSA_KUBUN, SHOZOKUCDDAIHYO, BUNKASAIMOKUCD, A.BUNKATSU_NO, SHINSAINNO, SEIRINUMBER"; //所属機関コード、,整理番号、審査員枝番

        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = new ArrayList();
        //List finalList = new ArrayList();

        //-----------------------
        // リスト取得
        //-----------------------
        try {
            list = SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException("データベースアクセス中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("データベースに1件もデータがありません。", e);
        }

        //最終的にreturnするStringを宣言する
        StringBuffer finalResult = new StringBuffer();
        char charToBeAdded = '　'; //全角スペース
        final int commentLength = 150; //コメントの表示文字数
               int otherCommentLength = 50; //コメントの表示文字数
        final char HOSHI = '★';

        //半角全角コンバータ
        HanZenConverter hanZenConverter = new HanZenConverter();

        for (int i = 0; i < list.size(); i++) {

            //long start = System.currentTimeMillis();

            //DBから取得したList型listをString型にして、そのStringのListを作る
            Map recordMap = (Map) list.get(i);
            String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //研究種目番号
            String shinsaKbn = (String) recordMap.get("SHINSA_KUBUN");
            String shozokuCd = (String) recordMap.get("SHOZOKUCDDAIHYO");
            String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKUCD");
            String bunkatsNoAB = (String) recordMap.get("BUNKATSUNO_AB");
            String bunkatsNo12 = (String) recordMap.get("BUNKATSUNO_12");
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");
            String shinsainNo = (String) recordMap.get("SHINSAINNO");
            String rigai = (String) recordMap.get("RIGAIKANKEI");
            String juyosei = (String) recordMap.get("JUYOSEI");
            String kenkyuKeikaku = (String) recordMap.get("KENKYUKEIKAKU");
            String dokusosei = (String) recordMap.get("DOKUSOSEI");
            String hakyukoka = (String) recordMap.get("HAKYUKOKA");
            String suikonoryoku = (String) recordMap.get("SUIKONORYOKU");
            String tekisetsuHoga = (String) recordMap.get("TEKISETU_HOGA");
            String tekisetsuKaigai = (String) recordMap.get("TEKISETU_KAIGAI");
            String kekkaTen = (String) recordMap.get("KEKKATEN");            
            String jinken = (String) recordMap.get("JINKEN");
            String buntankin = (String) recordMap.get("BUNTANKIN");
            String dato = (String) recordMap.get("DATO");
            String recordComments = (String) recordMap.get("COMMENTS");
            String recordOtherComment = (String) recordMap.get("OTHER_COMMENT");
            String shinsaJokyo = (String) recordMap.get("SHINSAJOKYO");

            //DBより取得したレコードをGCの対象にする（大量データに対応するため）
            recordMap = null;
            list.set(i, null);

            //---意見を半角から全角にする
            //charの配列に変換
            String ZenkakuComments = hanZenConverter.convert(recordComments);
            char[] cArray = ZenkakuComments.toCharArray();
            for (int j = 0; j < cArray.length; j++) {
                if (StringUtil.isHankaku(cArray[j])) {
                    cArray[j] = HOSHI; //半角の場合は置き換え
                }

            }
            //文字列に戻す
            String checkedZenkakuComments = new String(cArray);
            if (checkedZenkakuComments.length() > commentLength){
                checkedZenkakuComments = checkedZenkakuComments.substring(0, commentLength);
            }

            //意見を150文字の文字の固定長にする
            while (checkedZenkakuComments.length() < commentLength) {
                checkedZenkakuComments += charToBeAdded;
            }
            //意見ここまで


            //---コメントを半角から全角にする
            //charの配列に変換
            String ZenOtherComment = hanZenConverter.convert(recordOtherComment);
            char[] ocArray = ZenOtherComment.toCharArray();
            for (int j = 0; j < ocArray.length; j++) {
                if (StringUtil.isHankaku(ocArray[j])) {
                    ocArray[j] = HOSHI; //半角の場合は置き換え
                }

            }
            //文字列に戻す
            String checkedZenOtherComment = new String(ocArray);
            if (checkedZenOtherComment.length() > otherCommentLength){
                checkedZenOtherComment = checkedZenOtherComment.substring(0, otherCommentLength);
            }

            //コメントを50文字の文字の固定長にする
            while (checkedZenOtherComment.length() < otherCommentLength) {
                checkedZenOtherComment += charToBeAdded;
            }

            //最終結果にレコード（本行）を文字列結合
            finalResult.append(jigyoId)
                       .append(shinsaKbn)
                       .append(shozokuCd)
                       .append(bunkaSaimokuCd)
                       .append(bunkatsNoAB)
                       .append(bunkatsNo12)
                       .append(seiriNumber)
                       .append(shinsainNo)
                       .append(rigai)
                       .append(juyosei)
                       .append(kenkyuKeikaku)
                       .append(dokusosei)
                       .append(hakyukoka)
                       .append(suikonoryoku)
                       .append(tekisetsuHoga)
                       .append(tekisetsuKaigai)
                       .append(kekkaTen)
                       .append(jinken)
                       .append(buntankin)
                       .append(dato)
                       .append(checkedZenkakuComments)
                       .append(checkedZenOtherComment)
                       .append(shinsaJokyo)
                       .append(LINE_SHIFT);

            //long stop = System.currentTimeMillis() - start;
            //System.out.println(i+"行の処理時間。" + stop);
            }
        return finalResult.toString();
    }
//苗　追加ここまで    
    
//2006/06/20 苗　追加ここから
//TODO　2006/6/20　苗    
    /**
     * 特定領域研究(新規領域)研究計画調書のパンチデータを作成する
     * 
     * @param connection
     * @return パンチデータ情報(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataTokuteiSinnkiChosho(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {
    	//修正By　Sai　2006.09.15
    	//SQL文から研究種目番号（2バイト）,計画研究・公募研究の別,開示希望の有無（1バイト）を削除する
        String query = "SELECT "
            //update liuyi start 2006/06/29
//            + " SUBSTR(A.JIGYO_ID,5,2) KENKYUSHUMOKU"   //研究種目番号（2バイト）
//            + ",A.SHOZOKU_CD SHOZOKU_CD_DAIHYO"         //所属機関コード(5バイト)
            + " A.SHOZOKU_CD SHOZOKU_CD_DAIHYO"         //所属機関コード(5バイト)
        	+ ",A.RYOIKI_NO RYOIKI_DAIHYO"          //仮領域番号(5バイト)
            + ",A.KOMOKU_NO KOMOKU_NO_DAIHYO"           //研究項目番号(3バイト)
            + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER" //整理番号(4バイト)申請番号のハイフン以下
//            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE ( CASE WHEN A.KENKYU_KUBUN = '1' THEN '6'"
//            + "  ELSE ' ' END ) END KENKYU_KUBUN"  //計画研究・公募研究の別
//            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE (CASE WHEN A.KENKYU_KUBUN = '1' AND A.KOMOKU_NO ='X00' THEN '1'"
//            + " ELSE(case WHEN A.KENKYU_KUBUN = '1' AND A.KOMOKU_NO ='Y00' THEN '2'"
//            + " ELSE '3' END)END) END KOMOKU_NO_NM"  //総括・支援・調整班の別

            + ",CASE WHEN A.KOMOKU_NO = 'X00' AND B.BUNTAN_FLG = '1' THEN '1'"
            + "      WHEN A.KOMOKU_NO = 'Y00' AND B.BUNTAN_FLG = '1' THEN '2'"
            + "      WHEN A.CHOSEIHAN = '1'   AND B.BUNTAN_FLG = '1' THEN '3'"
            + "      ELSE ' ' END KOMOKU_NO_NM" 			////総括・支援・調整班の別
            
//UPDATE　START 2007/07/25 BIS 金京浩   //出力ファイル仕様20070720.xlsについて                      
            /*
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"      //版数
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //分担金の有無
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1年目研究経費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI1,7,'0') END BIHINHI1" //1年目設備備品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI1,7,'0') END SHOMOHINHI1" //1年目消耗品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI1,7,'0') END RYOHI1" //1年目旅費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN1,7,'0') END SHAKIN1" //1年目謝金等(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA1,7,'0') END SONOTA1" //1年目その他(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2年目研究経費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI2,7,'0') END BIHINHI2" //2年目設備備品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI2,7,'0') END SHOMOHINHI2" //2年目消耗品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI2,7,'0') END RYOHI2" //2年目旅費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN2,7,'0') END SHAKIN2" //2年目謝金等(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA2,7,'0') END SONOTA2" //2年目その他(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3年目研究経費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI3,7,'0') END BIHINHI3" //3年目設備備品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI3,7,'0') END SHOMOHINHI3" //3年目消耗品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI3,7,'0') END RYOHI3" //3年目旅費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN3,7,'0') END SHAKIN3" //3年目謝金等(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA3,7,'0') END SONOTA3" //3年目その他(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4年目研究経費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI4,7,'0') END BIHINHI4" //4年目設備備品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI4,7,'0') END SHOMOHINHI4" //4年目消耗品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI4,7,'0') END RYOHI4" //4年目旅費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN4,7,'0') END SHAKIN4" //4年目謝金等(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA4,7,'0') END SONOTA4" //4年目その他(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" //5年目研究経費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI5,7,'0') END BIHINHI5" //5年目設備備品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI5,7,'0') END SHOMOHINHI5" //5年目消耗品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI5,7,'0') END RYOHI5" //5年目旅費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN5,7,'0') END SHAKIN5" //5年目謝金等(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA5,7,'0') END SONOTA5" //5年目その他(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI6,7,'0') END KEIHI6" //6年目研究経費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI6,7,'0') END BIHINHI6" //6年目設備備品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI6,7,'0') END SHOMOHINHI6" //6年目消耗品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI6,7,'0') END RYOHI6" //6年目旅費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN6,7,'0') END SHAKIN6" //6年目謝金等(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA6,7,'0') END SONOTA6" //6年目その他(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD('　',80,'　') ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI" //研究課題名（和文）（80バイト）
            + " ,CASE WHEN A.KENKYU_NINZU IS NULL THEN "
            + "     (CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00155' THEN LPAD('1',2,'0') ELSE "
            + "         (CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00156' THEN  LPAD('1',2,'0') ELSE '  ' END )" 
            + "     END ) "
            + " ELSE "
            + "     (CASE WHEN  B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') END )"
            + " END KENKYUNINZU" //研究者数（2バイト）
            */
            
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"      //版数
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //分担金の有無
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1年目研究経費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI1,7,'0') END BIHINHI1" //1年目設備備品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI1,7,'0') END SHOMOHINHI1" //1年目消耗品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI1,7,'0') END RYOHI1" //1年目旅費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN1,7,'0') END SHAKIN1" //1年目謝金等(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA1,7,'0') END SONOTA1" //1年目その他(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2年目研究経費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI2,7,'0') END BIHINHI2" //2年目設備備品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI2,7,'0') END SHOMOHINHI2" //2年目消耗品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI2,7,'0') END RYOHI2" //2年目旅費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN2,7,'0') END SHAKIN2" //2年目謝金等(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA2,7,'0') END SONOTA2" //2年目その他(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3年目研究経費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI3,7,'0') END BIHINHI3" //3年目設備備品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI3,7,'0') END SHOMOHINHI3" //3年目消耗品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI3,7,'0') END RYOHI3" //3年目旅費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN3,7,'0') END SHAKIN3" //3年目謝金等(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA3,7,'0') END SONOTA3" //3年目その他(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4年目研究経費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI4,7,'0') END BIHINHI4" //4年目設備備品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI4,7,'0') END SHOMOHINHI4" //4年目消耗品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI4,7,'0') END RYOHI4" //4年目旅費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN4,7,'0') END SHAKIN4" //4年目謝金等(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA4,7,'0') END SONOTA4" //4年目その他(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" //5年目研究経費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI5,7,'0') END BIHINHI5" //5年目設備備品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI5,7,'0') END SHOMOHINHI5" //5年目消耗品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI5,7,'0') END RYOHI5" //5年目旅費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN5,7,'0') END SHAKIN5" //5年目謝金等(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA5,7,'0') END SONOTA5" //5年目その他(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI6,7,'0') END KEIHI6" //6年目研究経費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI6,7,'0') END BIHINHI6" //6年目設備備品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI6,7,'0') END SHOMOHINHI6" //6年目消耗品費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI6,7,'0') END RYOHI6" //6年目旅費(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN6,7,'0') END SHAKIN6" //6年目謝金等(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA6,7,'0') END SONOTA6" //6年目その他(7バイト)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD('　',80,'　') ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI" //研究課題名（和文）（80バイト）
            + " ,CASE WHEN A.KENKYU_NINZU IS NULL THEN "
            + "     (CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00155' THEN LPAD('1',2,'0') ELSE "
            + "         (CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00156' THEN  LPAD('1',2,'0') ELSE '  ' END )" 
            + "     END ) "
            + " ELSE "
            + "     (CASE WHEN  B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') END )"
            + " END KENKYUNINZU" //研究者数（2バイト）            
//UPDATE  END　 2007/07/25 BIS 金京浩
//            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBO_FLG" //開示希望の有無（1バイト）
            + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG"        //代表者分担者別(1バイト)
            
            //2006.11.20 iso 漢字とフリガナの順を入れ替え
            //ここは入れ替えなくてもいいが合わせて修正
//            + ",RPAD(B.NAME_KANJI_SEI,32,'　') NAMEKANJISEI" //氏名（漢字等ー姓）(32バイト)
//            + ",RPAD(NVL(B.NAME_KANJI_MEI, '　'),32,'　') NAMEKANJIMEI"   //氏名（漢字等ー名）(32バイト)        
            + ",RPAD(B.NAME_KANA_SEI,32,'　') NAMEKANASEI"   //氏名（フリガナー姓）(32バイト)
            + ",RPAD(NVL(B.NAME_KANA_MEI, '　'),32,'　') NAMEKANAMEI" //氏名（フリガナー名）(32バイト)
            + ",RPAD(B.NAME_KANJI_SEI,32,'　') NAMEKANJISEI" //氏名（漢字等ー姓）(32バイト)
            + ",RPAD(NVL(B.NAME_KANJI_MEI, '　'),32,'　') NAMEKANJIMEI"   //氏名（漢字等ー名）(32バイト)    
            
            + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD"     //所属機関名(コード)研究組織表管理テーブルより(5バイト)
            + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD "    //部局名(コード)(3バイト)
            + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD"   //職名コード(2バイト)
            + ",LPAD(B.KENKYU_NO, 8,' ')  KENKYUNO"     //研究者番号(8バイト)
            + ",LPAD(NVL(B.KEIHI,0), 7,'0') KEIHI"      //研究経費(7バイト)
            + ",LPAD(B.EFFORT, 3,'0') EFFORT"           //エフォート(3バイト)
            + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
            + " WHERE DEL_FLG = 0" 
            + "   AND A.SYSTEM_NO = B.SYSTEM_NO"
            + "   AND SUBSTR(A.JIGYO_ID,3,5) ='00022' " 
            
            //2006.10.30 iso 領域代表者関連のステータス21～24は出力対象ではないので削除。
//            + "   AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12,21,22,23,24)"
            + "   AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)"
            
            + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.RYOIKI_NO, A.KOMOKU_NO,SEIRINUMBER,BUNTANFLG,B.SEQ_NO"; //事業ID,機関ー細目番号ー分割番号ー整理番号、代表者分担者フラグ順、シーケンス番号順


//      for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = null; //new ArrayList();
         //-----------------------
        // リスト取得
        //-----------------------
        try {
            list = SelectUtil.select(connection, query);
        } catch (DataAccessException e) {
            throw new ApplicationException("データベースアクセス中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("申請データテーブルに1件もデータがありません。", e);
        }

        //最終的にreturnするStringを宣言する
        //String finalResult = new String();
        StringBuffer finalResult = new StringBuffer();

//2006/04/15 追加ここから    
//        final char charToBeAdded = '　'; //全角スペース                                                                       //TODO
//        final int syutokumaeKikanLength = 40; //応募資格を得る前の所属研究機関名の表示文字数
//        final int otherKeywordLength = 25; //細目表意外のキーワードの表示文字数
//        final char HOSHI = '★';

        //半角全角コンバータ
 //       HanZenConverter hanZenConverter = new HanZenConverter();
//苗　ここまで  
        
        for (int i = 0; i < list.size(); i++) {
            
            if(i % 1000 == 0) {
                log.info("パンチデータ処理数::" + i + "件");
            }
            
            
            //DBから取得したList型listをString型にして、そのStringのListを作る
            Map recordMap = (Map) list.get(i);
            //String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //研究種目番号
            String syozokuCdDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");//所属機関コード
            String ryoikiDaihyo = (String) recordMap.get("RYOIKI_DAIHYO");//仮領域番号
            String komokuNoDaihyo = (String) recordMap.get("KOMOKU_NO_DAIHYO");//研究項目番号
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");//整理番号
            //String kenkyuKbn = (String) recordMap.get("KENKYU_KUBUN");//計画研究・公募研究の別
            String komokuNoNm =(String) recordMap.get("KOMOKU_NO_NM");//総括・支援・調整班の別
            String edition = (String)recordMap.get("EDITION");//版数
            String buntanKinFlg = (String) recordMap.get("BUNTANKINFLG");//分担金の有無
            String keihi1 = (String) recordMap.get("KEIHI1");//1年目研究経費
            String bihinhi1 = (String) recordMap.get("BIHINHI1");//1年目設備備品費
            String shomohinhi1 = (String) recordMap.get("SHOMOHINHI1");//1年目消耗品費
            String ryohi1 = (String) recordMap.get("RYOHI1");//1年目旅費
            String shakin1 = (String) recordMap.get("SHAKIN1");//1年目謝金等
            String sonota1 = (String) recordMap.get("SONOTA1");//1年目その他
            String keihi2 = (String) recordMap.get("KEIHI2");//2年目研究経費
            String bihinhi2 = (String) recordMap.get("BIHINHI2");//2年目設備備品費
            String shomohinhi2 = (String) recordMap.get("SHOMOHINHI2");//2年目消耗品費
            String ryohi2 = (String) recordMap.get("RYOHI2");//2年目旅費
            String shakin2 = (String) recordMap.get("SHAKIN2");//2年目謝金等
            String sonota2 = (String) recordMap.get("SONOTA2");//2年目その他
            String keihi3 = (String) recordMap.get("KEIHI3");//3年目研究経費
            String bihinhi3 = (String) recordMap.get("BIHINHI3");//3年目設備備品費
            String shomohinhi3 = (String) recordMap.get("SHOMOHINHI3");//3年目消耗品費
            String ryohi3 = (String) recordMap.get("RYOHI3");//3年目旅費
            String shakin3 = (String) recordMap.get("SHAKIN3");//3年目謝金等
            String sonota3 = (String) recordMap.get("SONOTA3");//3年目その他
            String keihi4 = (String) recordMap.get("KEIHI4");//4年目研究経費
            String bihinhi4 = (String) recordMap.get("BIHINHI4");//1年目設備備品費
            String shomohinhi4 = (String) recordMap.get("SHOMOHINHI4");//4年目消耗品費
            String ryohi4 = (String) recordMap.get("RYOHI4");//4年目旅費
            String shakin4 = (String) recordMap.get("SHAKIN4");//4年目謝金等
            String sonota4 = (String) recordMap.get("SONOTA4");//4年目その他
            String keihi5 = (String) recordMap.get("KEIHI5");;//5年目研究経費
            String bihinhi5 = (String) recordMap.get("BIHINHI5");//5年目設備備品費
            String shomohinhi5 = (String) recordMap.get("SHOMOHINHI5");//5年目消耗品費
            String ryohi5 = (String) recordMap.get("RYOHI5");//5年目旅費
            String shakin5 = (String) recordMap.get("SHAKIN5");//5年目謝金等
            String sonota5 = (String) recordMap.get("SONOTA5");//5年目その他
            String keihi6 = (String) recordMap.get("KEIHI6");;//6年目研究経費
            String bihinhi6 = (String) recordMap.get("BIHINHI6");//6年目設備備品費
            String shomohinhi6 = (String) recordMap.get("SHOMOHINHI6");//6年目消耗品費
            String ryohi6 = (String) recordMap.get("RYOHI6");//6年目旅費
            String shakin6 = (String) recordMap.get("SHAKIN6");//6年目謝金等
            String sonota6 = (String) recordMap.get("SONOTA6");//6年目その他
            String kadaiNameKanji = (String) recordMap.get("KADAINAMEKANJI");//研究課題名（和文）
            String kenkyuNinzu = (String) recordMap.get("KENKYUNINZU");//研究者数
            //String kaijikibo = (String) recordMap.get("KAIJIKIBO_FLG");//開示希望の有無
            String buntanFlg = (String) recordMap.get("BUNTANFLG");//代表者分担者別

            //2006.11.20 iso 漢字とフリガナの順を入れ替え
            //ここは入れ替えなくてもいいが合わせて修正
//            String nameKanaSei = (String)recordMap.get("NAMEKANASEI");//氏名（フリガナー姓）
//            String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");//氏名（フリガナー名）
            String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");//氏名（漢字等ー姓）
            String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");//氏名（漢字等ー名）
            String nameKanaSei = (String)recordMap.get("NAMEKANASEI");//氏名（フリガナー姓）
            String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");//氏名（フリガナー名）
            
            String syozokuCd = (String) recordMap.get("SHOZOKUCD");//所属機関名(コード)研究組織表管理テーブルより
            String bukyokuCd = (String) recordMap.get("BUKYOKUCD");//部局名(コード)
            String syokusyuCd = (String) recordMap.get("SHOKUSHUCD");//職名コード
            String kenkyuNo = (String) recordMap.get("KENKYUNO");//研究者番号
            String keihi = (String) recordMap.get("KEIHI");//研究経費
            String effort = (String) recordMap.get("EFFORT");//エフォート
            
            

            
            //DBより取得したレコードをGCの対象にする（大量データに対応するため）
            recordMap = null;
            list.set(i, null);
            
            
//             finalResult.append(jigyoId)
            finalResult.append(syozokuCdDaihyo) 
                        .append(ryoikiDaihyo) 
                        .append(komokuNoDaihyo) 
                        .append(seiriNumber)
                        //.append(kenkyuKbn) 
                        .append(komokuNoNm) 
                        .append(edition )
                        .append(buntanKinFlg)
                        .append(keihi1 )
                        .append(bihinhi1) 
                        .append(shomohinhi1) 
                        .append(ryohi1) 
                        .append(shakin1) 
                        .append(sonota1) 
                        .append(keihi2) 
                        .append(bihinhi2) 
                        .append(shomohinhi2) 
                        .append(ryohi2) 
                        .append(shakin2)
                        .append(sonota2) 
                        .append(keihi3) 
                        .append(bihinhi3) 
                        .append(shomohinhi3) 
                        .append(ryohi3) 
                        .append(shakin3) 
                        .append(sonota3) 
                        .append(keihi4) 
                        .append(bihinhi4) 
                        .append(shomohinhi4)
                        .append(ryohi4) 
                        .append(shakin4)
                        .append(sonota4) 
                        .append(keihi5) 
                        .append(bihinhi5) 
                        .append(shomohinhi5)
                        .append(ryohi5) 
                        .append(shakin5) 
                        .append(sonota5) 
                        .append(keihi6) 
                        .append(bihinhi6) 
                        .append(shomohinhi6) 
                        .append(ryohi6) 
                        .append(shakin6) 
                        .append(sonota6) 
                        .append(kadaiNameKanji) 
                        .append(kenkyuNinzu) 
                        //.append(kaijikibo) 
                        .append(buntanFlg)
                        
                        //2006.11.20 iso 漢字とフリガナの順を入れ替え
//                        .append(nameKanjiSei) 
//                        .append(nameKanjiMei) 
                        .append(nameKanaSei) 
                        .append(nameKanaMei) 
                        .append(nameKanjiSei) 
                        .append(nameKanjiMei) 
                        
                        .append(syozokuCd) 
                        .append(bukyokuCd) 
                        .append(syokusyuCd) 
                        .append(kenkyuNo) 
                        .append(keihi) 
                        .append(effort) 
                        .append(LINE_SHIFT).toString();
            //update end liuyi 2006/06/29
            //finalList.add(record);
            //finalResult = finalResult + (String) finalList.get(i);
        }
        log.info("パンチデータ処理終了。");
        return finalResult.toString();
    }
//2006/06/20　苗　追加ここまで 
    
//  TODO　2006/6/20　苗    
    /**
     * 特定領域研究(継続)研究計画調書パンチデータを作成する
     * 
     * @param connection
     * @return パンチデータ情報(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataTokuteiKeiZokuChosho(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {
        String query = "SELECT " 
			//+ " '2' DATAKBN" //データ区分(1バイト)
			+ " NVL(A.SHINSEI_KUBUN,' ') SHINSEI_KUBUN" //新規継続区分(1バイト)
            + ",A.SHOZOKU_CD SHOZOKU_CD_DAIHYO" 		//所属機関コード(5バイト)
            + ",NVL(A.RYOIKI_NO, '   ') RYOIKI_NO" 		//領域番号(3バイト)
            + ",NVL(A.KOMOKU_NO, '   ') KOMOKU_NO" 		//研究項目番号(3バイト)
            + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SHERINO" //整理番号(4バイト)
            + ",CASE WHEN A.KENKYU_KUBUN = '1' AND B.BUNTAN_FLG = '1' THEN '6'"
            + "      WHEN A.KENKYU_KUBUN = '2' AND B.BUNTAN_FLG = '1' THEN '7'"
            + "      ELSE ' ' END KENKYU_KUBUN" 		//計画研究・公募研究
            + ",CASE WHEN A.KOMOKU_NO = 'X00' AND B.BUNTAN_FLG = '1' THEN '1'"
            + "      WHEN A.KOMOKU_NO = 'Y00' AND B.BUNTAN_FLG = '1' THEN '2'"
            + "      WHEN A.CHOSEIHAN = '1'   AND B.BUNTAN_FLG = '1' THEN '3'"
            + "      ELSE ' ' END CHOSEIHAN" 			//調整班
			//2005.10.17kainuma 追加
//UPDATE　START 2007/07/25 BIS 金京浩   //出力ファイル仕様20070720.xlsについて                 
			/*
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"		//版数
			//追加以上
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE LPAD(A.BUNTANKIN_FLG,1,' ') END BUNTANKIN_FLG" //分担金の有無
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1年目研究経費(7バイト)
			//2005.10.17kainuma 追加
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI1,7,'0') END BIHINHI1" //1年目設備備品費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI1,7,'0') END SHOMOHINHI1" //1年目消耗品費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI1,7,'0') END RYOHI1" //1年目旅費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN1,7,'0') END SHAKIN1" //	1年目謝金等(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA1,7,'0') END SONOTA1" //1年目その他(7バイト)
			//追加以上
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2年目研究経費(7バイト)
			//2005.10.17kainuma 追加
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI2,7,'0') END BIHINHI2" //2年目設備備品費(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI2,7,'0') END SHOMOHINHI2" //2年目消耗品費(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI2,7,'0') END RYOHI2" //2年目旅費(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN2,7,'0') END SHAKIN2" //2年目謝金等(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA2,7,'0') END SONOTA2" //2年目その他(7バイト)
		    //追加以上
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3年目研究経費(7バイト)
			//2005.10.17kainuma 追加
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI3,7,'0') END BIHINHI3" //3年目設備備品費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI3,7,'0') END SHOMOHINHI3" //3年目消耗品費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI3,7,'0') END RYOHI3" //3年目旅費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN3,7,'0') END SHAKIN3" //3年目謝金等(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA3,7,'0') END SONOTA3" //3年目その他(7バイト)
		    //追加以上
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4年目研究経費(7バイト)
			//2005.10.17kainuma 追加
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI4,7,'0') END BIHINHI4" //4年目設備備品費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI4,7,'0') END SHOMOHINHI4" //4年目消耗品費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI4,7,'0') END RYOHI4" //4年目旅費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN4,7,'0') END SHAKIN4" //4年目謝金等(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA4,7,'0') END SONOTA4" //4年目その他(7バイト)
			//追加以上
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" //5年目研究経費(7バイト)
			//2005.10.17kainuma 追加
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI5,7,'0') END BIHINHI5" //5年目設備備品費(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI5,7,'0') END SHOMOHINHI5" //5年目消耗品費(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI5,7,'0') END RYOHI5" //5年目旅費(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN5,7,'0') END SHAKIN5" //5年目謝金等(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA5,7,'0') END SONOTA5" //5年目その他(7バイト)
		    //追加以上
            
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_KEIZOKU,'        ') END KADAI_NO_KEIZOKU" //継続分の研究課題番号（8バイト）
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD('　',80,'　') ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI" //研究課題名（和文）（80バイト）
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,'0') END KENKYUNINZU" //研究者数（2バイト）
            //2005.10.24kainuma　追加
            +",CASE WHEN  B.BUNTAN_FLG = '2' THEN ' '"
            +"		WHEN  B.BUNTAN_FLG = '1' AND A.KAIJIKIBO_FLG_NO = '0' THEN ' '"
            +"      ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBOFLGNO" //開示希望の有無（1バイト）"
            //追加以上
            */
            
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"		//版数
			//追加以上
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE LPAD(A.BUNTANKIN_FLG,1,' ') END BUNTANKIN_FLG" //分担金の有無
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1年目研究経費(7バイト)
			//2005.10.17kainuma 追加
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI1,7,'0') END BIHINHI1" //1年目設備備品費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI1,7,'0') END SHOMOHINHI1" //1年目消耗品費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI1,7,'0') END RYOHI1" //1年目旅費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN1,7,'0') END SHAKIN1" //	1年目謝金等(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA1,7,'0') END SONOTA1" //1年目その他(7バイト)
			//追加以上
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2年目研究経費(7バイト)
			//2005.10.17kainuma 追加
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI2,7,'0') END BIHINHI2" //2年目設備備品費(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI2,7,'0') END SHOMOHINHI2" //2年目消耗品費(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI2,7,'0') END RYOHI2" //2年目旅費(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN2,7,'0') END SHAKIN2" //2年目謝金等(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA2,7,'0') END SONOTA2" //2年目その他(7バイト)
		    //追加以上
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3年目研究経費(7バイト)
			//2005.10.17kainuma 追加
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI3,7,'0') END BIHINHI3" //3年目設備備品費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI3,7,'0') END SHOMOHINHI3" //3年目消耗品費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI3,7,'0') END RYOHI3" //3年目旅費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN3,7,'0') END SHAKIN3" //3年目謝金等(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA3,7,'0') END SONOTA3" //3年目その他(7バイト)
		    //追加以上
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4年目研究経費(7バイト)
			//2005.10.17kainuma 追加
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI4,7,'0') END BIHINHI4" //4年目設備備品費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI4,7,'0') END SHOMOHINHI4" //4年目消耗品費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI4,7,'0') END RYOHI4" //4年目旅費(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN4,7,'0') END SHAKIN4" //4年目謝金等(7バイト)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA4,7,'0') END SONOTA4" //4年目その他(7バイト)
			//追加以上
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" //5年目研究経費(7バイト)
			//2005.10.17kainuma 追加
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI5,7,'0') END BIHINHI5" //5年目設備備品費(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI5,7,'0') END SHOMOHINHI5" //5年目消耗品費(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI5,7,'0') END RYOHI5" //5年目旅費(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN5,7,'0') END SHAKIN5" //5年目謝金等(7バイト)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA5,7,'0') END SONOTA5" //5年目その他(7バイト)
		    //追加以上
            
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_KEIZOKU,'        ') END KADAI_NO_KEIZOKU" //継続分の研究課題番号（8バイト）
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD('　',80,'　') ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI" //研究課題名（和文）（80バイト）
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,'0') END KENKYUNINZU" //研究者数（2バイト）
            //2005.10.24kainuma　追加
            +",CASE WHEN  B.BUNTAN_FLG <> '1' THEN ' '"
            +"		WHEN  B.BUNTAN_FLG = '1' AND A.KAIJIKIBO_FLG_NO = '0' THEN ' '"
            +"      ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBOFLGNO" //開示希望の有無（1バイト）"
            //追加以上
            
//UPDATE　END　 2007/07/25 BIS 金京浩            
            
            + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG" 		//代表者分担者別(1バイト)
			//2005.10.17kainuma　追加
		    + ",RPAD(B.NAME_KANA_SEI,32,'　') NAMEKANASEI"	//氏名（フリガナー姓）(32バイト)

            //2005.12.22 iso NULLだった時のバグ修正
//			+ ",RPAD(B.NAME_KANA_MEI,32,'　') NAMEKANAMEI"	//氏名（フリガナー名）(32バイト)
			+ ",RPAD(NVL(B.NAME_KANA_MEI, '　'),32,'　') NAMEKANAMEI"	//氏名（フリガナー名）(32バイト)
			+ ",RPAD(B.NAME_KANJI_SEI,32,'　') NAMEKANJISEI"	//氏名（漢字等ー姓）(32バイト)
            //2005.12.22 iso NULLだった時のバグ修正
//			+ ",RPAD(B.NAME_KANJI_MEI,32,'　') NAMEKANJIMEI"	//氏名（漢字等ー名）(32バイト)
			+ ",RPAD(NVL(B.NAME_KANJI_MEI, '　'),32,'　') NAMEKANJIMEI"	//氏名（漢字等ー名）(32バイト)
			
		    //追加以上
            
            + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD" 	//所属機関名(コード)研究組織表管理テーブルより(5バイト)
            + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD " 	//部局名(コード)(3バイト)
            + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD" 	//職名コード(2バイト)
            + ",LPAD(B.KENKYU_NO, 8,' ')  KENKYUNO" 	//研究者番号(8バイト)
            + ",LPAD(B.KEIHI, 7,'0') KEIHI" 			//研究経費(7バイト)
            + ",LPAD(B.EFFORT, 3,'0') EFFORT" 			//エフォート(3バイト)

            + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
            + " WHERE A.DEL_FLG = 0"
//Modify By Sai 2006.09.15 Start             
//            + "   AND A.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_TOKUTEI
            + "   AND SUBSTR(A.JIGYO_ID,3,5) ='00021' " 
//          Modify By Sai 2006.09.15 End             
            + "   AND TO_NUMBER(A.JOKYO_ID) IN (6,8,9,10,11,12)"
            + "   AND A.SYSTEM_NO = B.SYSTEM_NO"
            + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.RYOIKI_NO, A.KOMOKU_NO, SHERINO, BUNTANFLG, B.SEQ_NO"; //事業ID、機関ー領域番号ー項目番号ー整理番号順、代表者分担者フラグ順、シーケンス番号順

		//for debug
		if (log.isDebugEnabled()) {
		log.debug("query:" + query);
		}
		
		List list = new ArrayList();
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
		list = SelectUtil.select(connection, query.toString());
		} catch (DataAccessException e) {
		throw new ApplicationException("データベースアクセス中にDBエラーが発生しました。",
		        new ErrorInfo("errors.4004"), e);
		} catch (NoDataFoundException e) {
		throw new NoDataFoundException("データベースに1件もデータがありません。", e);
		}
		
		//最終的にreturnするStringを宣言する
		StringBuffer finalResult = new StringBuffer();
		
		for (int i = 0; i < list.size(); i++) {
		
		//	long start = System.currentTimeMillis();
		
		//DBから取得したList型listをString型にして、そのStringのListを作る
		Map recordMap = (Map) list.get(i);
		String strDataKbn = "02";
		
		
		String strShinseiKbn = (String) recordMap.get("SHINSEI_KUBUN");
		String strShozokuDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");
		String strRyoikiNo = (String) recordMap.get("RYOIKI_NO");
		String strKomokuNo = (String) recordMap.get("KOMOKU_NO");
		String strSheriNo = (String) recordMap.get("SHERINO");
		String strKENKYU_KUBUN = (String) recordMap.get("KENKYU_KUBUN");
		String strCHOSEIHAN = (String) recordMap.get("CHOSEIHAN");
		//2005.10.17 kainuma追加
		String edition = (String)recordMap.get("EDITION");
		//追加以上
		String strBUNTANKIN_FLG = (String) recordMap.get("BUNTANKIN_FLG");
		String strKEIHI1 = (String) recordMap.get("KEIHI1");
		
		//2005.10.17 kainuma追加
		String strBIHINHI1 = (String)recordMap.get("BIHINHI1");
		String strSHOMOHINHI1 = (String)recordMap.get("SHOMOHINHI1");
		String strRYOHI1 = (String)recordMap.get("RYOHI1");
		String strSHAKIN1 = (String)recordMap.get("SHAKIN1");
		String strSONOTA1 = (String)recordMap.get("SONOTA1");
		//追加以上
		
		
		String strKEIHI2 = (String) recordMap.get("KEIHI2");
		//2005.10.17 kainuma追加
		String strBIHINHI2 = (String)recordMap.get("BIHINHI2");
		String strSHOMOHINHI2 = (String)recordMap.get("SHOMOHINHI2");
		String strRYOHI2 = (String)recordMap.get("RYOHI2");
		String strSHAKIN2 = (String)recordMap.get("SHAKIN2");
		String strSONOTA2 = (String)recordMap.get("SONOTA2");
		//追加以上
		
		String strKEIHI3 = (String) recordMap.get("KEIHI3");
		//2005.10.17 kainuma追加
		String strBIHINHI3 = (String)recordMap.get("BIHINHI3");
		String strSHOMOHINHI3 = (String)recordMap.get("SHOMOHINHI3");
		String strRYOHI3 = (String)recordMap.get("RYOHI3");
		String strSHAKIN3 = (String)recordMap.get("SHAKIN3");
		String strSONOTA3 = (String)recordMap.get("SONOTA3");
		//追加以上
		
		String strKEIHI4 = (String) recordMap.get("KEIHI4");
		//2005.10.17 kainuma追加
		String strBIHINHI4 = (String)recordMap.get("BIHINHI4");
		String strSHOMOHINHI4 = (String)recordMap.get("SHOMOHINHI4");
		String strRYOHI4 = (String)recordMap.get("RYOHI4");
		String strSHAKIN4 = (String)recordMap.get("SHAKIN4");
		String strSONOTA4 = (String)recordMap.get("SONOTA4");
		//追加以上
		            
		String strKEIHI5 = (String) recordMap.get("KEIHI5");
		//2005.10.17 kainuma追加
		String strBIHINHI5 = (String)recordMap.get("BIHINHI5");
		String strSHOMOHINHI5 = (String)recordMap.get("SHOMOHINHI5");
		String strRYOHI5 = (String)recordMap.get("RYOHI5");
		String strSHAKIN5 = (String)recordMap.get("SHAKIN5");
		String strSONOTA5 = (String)recordMap.get("SONOTA5");
		//追加以上
		
		String strKADAI_NO_KEIZOKU = (String) recordMap.get("KADAI_NO_KEIZOKU");
		String strKadaiName = (String) recordMap.get("KADAINAMEKANJI");
		String strKENKYU_NINZU = (String) recordMap.get("KENKYUNINZU");
		
		//2005.10.24 kainuma追加
		String strKAIJIKIBO_FLG_NO = (String)recordMap.get("KAIJIKIBOFLGNO");
		//追加以上
		String strBuntanFlg = (String) recordMap.get("BUNTANFLG");
		
		//2005.10.17　kainuma追加
		String nameKanaSei = (String)recordMap.get("NAMEKANASEI");
		String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");
		String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");
		String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");
		//追加以上
				  
		String strSHOZOKU_CD = (String) recordMap.get("SHOZOKUCD");
		String strBUKYOKU_CD = (String) recordMap.get("BUKYOKUCD");
		String strSHOKUSHU_CD = (String) recordMap.get("SHOKUSHUCD");
		String strKENKYU_NO = (String) recordMap.get("KENKYUNO");
		String strKeihi = (String) recordMap.get("KEIHI");
		String strEffort = (String) recordMap.get("EFFORT");
		
		//DBより取得したレコードをGCの対象にする（大量データに対応するため）
		recordMap = null;
		list.set(i, null);
		
		//最終結果にレコード（本行）を文字列結合
		finalResult.append(strDataKbn)
				   .append(strShinseiKbn)
				   .append(strShozokuDaihyo)
				   .append(strRyoikiNo)
				   .append(strKomokuNo)
				   .append(strSheriNo)
				   .append(strKENKYU_KUBUN)
				   .append(strCHOSEIHAN)
				   .append(edition)
				   .append(strBUNTANKIN_FLG)
				   
				   .append(strKEIHI1)
				   //2005.10.17 kainuma追加
				   .append(strBIHINHI1)
				   .append(strSHOMOHINHI1)
				   .append(strRYOHI1)
				   .append(strSHAKIN1)
				   .append(strSONOTA1)
				   //追加以上
				   
				  
				   .append(strKEIHI2)
					//2005.10.17 kainuma追加
				   .append(strBIHINHI2)
				   .append(strSHOMOHINHI2)
				   .append(strRYOHI2)
				   .append(strSHAKIN2)
				   .append(strSONOTA2)
					//追加以上
							 
				   .append(strKEIHI3)
				   //2005.10.17 kainuma追加
				   .append(strBIHINHI3)
				   .append(strSHOMOHINHI3)
				   .append(strRYOHI3)
				   .append(strSHAKIN3)
				   .append(strSONOTA3)
				   //追加以上
				   
				  .append(strKEIHI4)
				   //2005.10.17 kainuma追加
				  .append(strBIHINHI4)
				  .append(strSHOMOHINHI4)
				  .append(strRYOHI4)
				  .append(strSHAKIN4)
				  .append(strSONOTA4)
				   //追加以上
				   
				   .append(strKEIHI5)
					//2005.10.17 kainuma追加
			   	   .append(strBIHINHI5)
				   .append(strSHOMOHINHI5)
				   .append(strRYOHI5)
				   .append(strSHAKIN5)
				   .append(strSONOTA5)
					//追加以上
				   
				   .append(strKADAI_NO_KEIZOKU)
				   .append(strKadaiName)
				   .append(strKENKYU_NINZU)
					//2005.10.17 kainuma追加
				   .append(strKAIJIKIBO_FLG_NO)
				    //追加以上
				   .append(strBuntanFlg)
					//2005.10.17 kainuma追加
				   .append(nameKanaSei)
				   .append(nameKanaMei)
				   .append(nameKanjiSei)
				   .append(nameKanjiMei)
				    //追加以上
				   .append(strSHOZOKU_CD)
				   .append(strBUKYOKU_CD)
				   .append(strSHOKUSHU_CD)
				   .append(strKENKYU_NO)
				   .append(strKeihi)
				   .append(strEffort)
				   .append(LINE_SHIFT);
		
		}
		
		return finalResult.toString();
		}
    
}