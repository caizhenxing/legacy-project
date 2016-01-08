/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.UserAuthorityException;
import jp.go.jsps.kaken.model.vo.CheckListInfo;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.EscapeUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * チェックリストテーブルアクセスクラス。
 */
public class CheckListInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(CheckListInfoDao.class);

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
	 * @param userInfo	実行するユーザ情報
	 */
	public CheckListInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	

	/**
	 * チェックリスト情報の数を取得する
	 * @param connection				コネクション
	 * @param primaryKeys				主キー情報
	 * @return
	 * @throws DataAccessException		取得中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public int countCheckListInfo(
		Connection connection,
		ShinseiDataInfo primaryKeys)
		throws DataAccessException, NoDataFoundException
		{
			String query =
				"SELECT "
					+ " count(*) COUNT"				//取得件数
					+ " FROM CHCKLISTINFO A"
					+ " WHERE A.SHOZOKU_CD = ?"
					+ " AND"
					+ " A.JIGYO_ID = ?"
					;
			
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			int retInt = 0;
			try {
				preparedStatement = connection.prepareStatement(query);
				int index = 1;
				DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getDaihyouInfo().getShozokuCd());	//所属機関コード
				DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getJigyoId());	//事業ID
				recordSet = preparedStatement.executeQuery();
				if (recordSet.next()) {
					retInt = recordSet.getInt("COUNT");
				} else {
					throw new NoDataFoundException(
						"申請データ管理テーブルに該当するデータが見つかりません。検索キー：所属コード'"
							+ primaryKeys.getDaihyouInfo().getShozokuCd()
							+ "'"
							+ " 事業ID'"
							+ primaryKeys.getJigyoId()
							+ "'"
							);
				}
				
			} catch (SQLException ex) {
				throw new DataAccessException("申請データ管理テーブル検索実行中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
				
			}
			return retInt;
		}
	
	/**
	 * チェックリスト情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録するチェックリスト情報
	 * @throws DataAccessException		登録中に例外が発生した場合。
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合
	 */
	public void insertCheckListInfo(
		Connection connection,
		ShinseiDataInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//キー重複チェック
		try {
			int count = countCheckListInfo(connection, addInfo);
			//NG
			if(count != 0){
				throw new DuplicateKeyException(
						"'" + addInfo + "'は既に登録されています。");
			}
		} catch (NoDataFoundException e) {
			//OK
		}
		
		String query =
			"INSERT INTO CHCKLISTINFO"
				+ "("
				+ "  SHOZOKU_CD"			//所属機関コード
				+ " ,JIGYO_ID"				//事業ID
				+ " ,KAKUTEI_DATE"			//チェックリスト確定日
				+ " ,EDITION"				//版
				+ " ,JOKYO_ID"				//申請状況ID
			//2005/04/21 追加 ここから---------------------------------------
			//理由 解除フラグの追加のため
				+ " ,CANCEL_FLG"
			//追加 ここまで--------------------------------------------------
			//2005/05/24 追加 ここから---------------------------------------
			//理由 PDFの格納パスの追加のため(VALUESの?も追加)
				+ " ,PDF_PATH"
			//追加 ここまで--------------------------------------------------
				+ ") "
				+ "  VALUES "
				+ "("
				+ "  ?,?,?,?,?,?,?"
				+ ")";
		
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement,index++,addInfo.getDaihyouInfo().getShozokuCd());	//所属機関コード
			DatabaseUtil.setParameter(preparedStatement,index++,addInfo.getJigyoId());						//事業ID
			DatabaseUtil.setParameter(preparedStatement,index++,"");										//チェックリスト確定日
			DatabaseUtil.setParameter(preparedStatement,index++,"");										//版
			
			//2005/04/15 削除 ここから--------------------------------------------------------
			//チェックリストの状況IDは作成時に03となるため
			//DatabaseUtil.setParameter(preparedStatement,index++,addInfo.getJokyoId());						//申請状況ID
			//削除 ここまで-------------------------------------------------------------------
			
			//2005/04/15 追加 ここから--------------------------------------------------------
			//チェックリストの状況IDは作成時に03となるため	
			DatabaseUtil.setParameter(preparedStatement,index++,"03");				
			//追加 ここまで-------------------------------------------------------------------
			
			//2005/04/21 追加 ここから--------------------------------------------------------
			//理由 解除フラグの追加のため
			DatabaseUtil.setParameter(preparedStatement,index++,"0");
			//追加 ここまで-------------------------------------------------------------------
			
			//2005/05/24 追加 ここから--------------------------------------------------------
			//理由 PDFの格納パスの追加のため
			DatabaseUtil.setParameter(preparedStatement,index++,"");
			//追加 ここまで-------------------------------------------------------------------
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("チェックリスト登録中に例外が発生しました。 ", ex);
			//エラーになったらもう一度チャレンジする
			try{
				int count = countCheckListInfo(connection, addInfo);
				if(count == 0){
					throw new DataAccessException("チェックリスト登録中に例外が発生しました。 ", ex);
				}			
			}catch (NoDataFoundException e){
				throw new DataAccessException("チェックリスト登録中に例外が発生しました。 ", ex);
			}
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
		
	
	/**
	 * チェックリスト情報を更新する。
	 * @param connection				コネクション
	 * @param checkInfo				チェックリスト検索情報
	 * @throws DataAccessException		更新中に例外が発生した場合
	 */
	public void updateCheckListInfo(
		Connection connection,
		CheckListSearchInfo checkInfo,
		boolean isVersionUp)
		throws DataAccessException
	{
		String query =
			"UPDATE CHCKLISTINFO"
				+ " SET"
				+ " JOKYO_ID = ?";
		//変更前の状況IDを取得
		String jokyoId = checkInfo.getJokyoId();
		if(jokyoId == null || jokyoId.equals("")){
			throw new DataAccessException("状況IDがセットされていません。");
		}
		//確定時は版を上げる
		// 2004/04/14 修正 状況IDの条件を追加--------------------------------------
		
		// 2005/04/12 変更 ここから-----------------------------------------------
		// 確定解除時に確定日を削除するため
		if(isVersionUp && jokyoId.equals(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU)){
			query = query + ", KAKUTEI_DATE = sysdate, EDITION = ?";
			//2005/04/21 追加 ここから--------------------------------------------
			//解除フラグ追加のため (確定時は解除フラグを0にする)
			query += ", CANCEL_FLG = 0 ";
			//追加 ここまで-------------------------------------------------------
			
		//確定解除時は確定日を削除する
		}else if(!isVersionUp && jokyoId.equals(StatusCode.STATUS_GAKUSIN_SHORITYU)){
			query = query + ", KAKUTEI_DATE = null";
			//2005/04/21 追加 ここから--------------------------------------------
			//解除フラグ追加のため (確定解除時は解除フラグを1にする)
			query += ", CANCEL_FLG = 1 ";
			//追加 ここまで-------------------------------------------------------
			
		}
		// 変更ここまで-----------------------------------------------------------
		
		query = query
				+ " WHERE"
				+ 	" SHOZOKU_CD = ?"
				+ " AND"
				+ 	" JIGYO_ID = ?"
				+ " AND"
				+ 	" JOKYO_ID = ?"
				;
		if (log.isDebugEnabled()){
			log.debug("チェックリスト更新：" + query);
		}
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getChangeJokyoId());
			if(isVersionUp && jokyoId.equals(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU)){
				DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getEdition());
			}
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getJigyoId());	
			DatabaseUtil.setParameter(preparedStatement,i++,jokyoId);
			DatabaseUtil.executeUpdate(preparedStatement);				
		} catch (SQLException ex) {
			throw new DataAccessException("チェックリスト情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	/**
	 * 申請情報管理を更新する。
	 * @param connection				コネクション
	 * @param checkInfo				チェックリスト検索情報
	 * @throws DataAccessException		更新中に例外が発生した場合
	 */
	public void updateShinseiData(
		Connection connection,
		CheckListSearchInfo checkInfo)
		throws DataAccessException
	{
		String query =
			"UPDATE SHINSEIDATAKANRI"
				+ " SET"
				+ "   JOKYO_ID = ?"
		//2006.12.13 iso 受理解除時に承認日にシステム日付がセットされるバグ修正
//				+ " , SHONIN_DATE =  SYSDATE "	//2005/08/26 takano 承認日をセット
				;

		//2006.12.13 iso 受理解除時に承認日にシステム日付がセットされるバグ修正
		//現状況IDが所属機関受付中なら、承認動作なので、承認日をセットする。
		if(checkInfo.getJokyoId() != null
				&& StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU.equals(checkInfo.getJokyoId())) {
			query += " , SHONIN_DATE =  SYSDATE "	//2005/08/26 takano 承認日をセット
				;
		}
				
			//2005/04/12 追加 ここから--------------------------------------------
			//理由 受理解除のため
				if(checkInfo.getJokyoId() != null && checkInfo.getJokyoId().equals(StatusCode.STATUS_GAKUSIN_JYURI)){
					query += " , JYURI_DATE = null ";
				}
			//追加 ここまで-------------------------------------------------------	
		query = query + " WHERE"
				+ "   JIGYO_ID = ?"
				+ " AND"
				+ "   JOKYO_ID = ?"
			//2005/04/12 追加 ここから--------------------------------------------
			//理由 条件不足のため
				+ " AND"
				+ "   SHOZOKU_CD = ?"
			//追加 ここまで-------------------------------------------------------
				+ " AND"
				+ "   DEL_FLG = 0";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getChangeJokyoId());
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getJigyoId());			
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getJokyoId());
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getShozokuCd());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("申請情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * チェックリストの状況IDを取得する。<!--.-->
	 * 
	 * @param connection             コネクション
	 * @param checkInfo           チェックリスト検索情報
	 * @return String   チェックリストの状況ID
	 * @throws DataAccessException   データ取得中に例外が発生した場合
	 */
	public String checkJokyoId(
	Connection connection,
	CheckListSearchInfo checkInfo, 
	boolean updateFlag)
		throws DataAccessException
	{

		String query =
			"SELECT "
				+ " A.JOKYO_ID"
				+ " FROM CHCKLISTINFO A"
				+ 	" INNER JOIN JIGYOKANRI B"
				+ 	" ON A.JIGYO_ID = B.JIGYO_ID"
				+ " WHERE"
				+ "   A.SHOZOKU_CD = ?"
				+ " AND A.JIGYO_ID = ?";
// 20050606 Start 条件に事業区分を追加
				if(checkInfo.getJigyoKubun() != null && 
					checkInfo.getJigyoKubun().length() > 0
					){
					query = query + " AND B.JIGYO_KUBUN = '" + EscapeUtil.toSqlString(checkInfo.getJigyoKubun()) + "' ";
				}
				else{
					//TODO:チェック情報に事業区分がセットされていない場合にはデフォルトの基盤事業区分をセット
					query = query + " AND B.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "' ";
				}
// Horikoshi End

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		if(updateFlag){
			query = query + " FOR UPDATE ";
		}

		//for debug
		if(log.isDebugEnabled()){log.debug("query:" + query);}

		String jokyoId = null;

		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, checkInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement, i++, checkInfo.getJigyoId());
			recordSet = preparedStatement.executeQuery();
			if(recordSet.next()){
				jokyoId =recordSet.getString("JOKYO_ID"); 
			}

		} catch (SQLException ex) {
			throw new DataAccessException("対象データが存在しません。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}

		return jokyoId;
	}



	//2005/04/11 追加 ここから--------------------------------------------------
	//理由 学振有効期限確認のため
	/**
	 * 有効期限を確認する。
	 * 
	 * @param connection             コネクション
	 * @param checkInfo           チェックリスト検索情報
	 * @return int					有効期限内のデータの個数
	 * @throws DataAccessException   データ取得中に例外が発生した場合
	 */
	public int checkDate(
		Connection connection,
		CheckListSearchInfo checkInfo)
		throws DataAccessException
	{
				
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		
		String query ="SELECT COUNT(*) COUNT "
			+ "FROM SHINSEIDATAKANRI DATA "
			//2005/04/12 修正 ここから--------------------------------------------
			//理由 INNER JOINの接続方式が不正なため
				+ "INNER JOIN CHCKLISTINFO CHECKLIST "
					+ "INNER JOIN JIGYOKANRI JIGYO "
					+ "ON JIGYO.JIGYO_ID = CHECKLIST.JIGYO_ID "
				+ "ON CHECKLIST.JIGYO_ID = DATA.JIGYO_ID " 
				+ "AND CHECKLIST.SHOZOKU_CD = DATA.SHOZOKU_CD "
			//修正 ここまで-------------------------------------------------------		
			+"WHERE DATA.JOKYO_ID = CHECKLIST.JOKYO_ID " 
				+ "AND DATA.DEL_FLG = 0 "
// 20050606 Start 検索条件に事業区分を追加
//				+ "AND JIGYO.DEL_FLG = 0 "
//				+ "AND DATA.JIGYO_KUBUN = 4 "
				+ "AND JIGYO.DEL_FLG = 0 ";
				if (checkInfo.getJigyoKubun() != null && 
					checkInfo.getJigyoKubun().length() > 0
					){
					query = query + " AND DATA.JIGYO_KUBUN = " + EscapeUtil.toSqlString(checkInfo.getJigyoKubun()) + " ";
				}
				else{
					//TODO:事業区分が不正な場合にはデフォルトの基盤事業区分をセット
					query = query + " AND DATA.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN + " ";
				}
				//2005.11.17 iso 締切日当日が表示されないので、日付までで丸め込むよう修正
//				query = query + " AND SYSDATE BETWEEN JIGYO.UKETUKEKIKAN_START AND JIGYO.UKETUKEKIKAN_END";
				query = query + " AND TRUNC(SYSDATE, 'DD') BETWEEN JIGYO.UKETUKEKIKAN_START AND JIGYO.UKETUKEKIKAN_END";
//				+ "AND SYSDATE BETWEEN JIGYO.UKETUKEKIKAN_START AND JIGYO.UKETUKEKIKAN_END";
// Horikoshi End
		
		if (checkInfo.getShozokuCd() != null
			&& !checkInfo.getShozokuCd().equals("")) { //所属機関コード
			query = query +
				" AND CHECKLIST.SHOZOKU_CD = '"
					+ EscapeUtil.toSqlString(checkInfo.getShozokuCd())
					+ "'";
		}
		if(checkInfo.getJigyoId() != null
			&& !checkInfo.getJigyoId().equals("")) { //事業ID
			query = query +
				" AND DATA.JIGYO_ID = '"
				+ EscapeUtil.toSqlString(checkInfo.getJigyoId())
				+ "'";
		}
		try {
			preparedStatement = connection.prepareStatement(query);
			int count = 0;
			recordSet = preparedStatement.executeQuery();
			recordSet.next();
			count = recordSet.getInt("COUNT");						
				
			//戻り値
			return count;

		} catch (SQLException ex) {
			throw new DataAccessException("対象データが存在しません。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}		
	}
	//	追加 ここまで-------------------------------------------------------------
	
	
	
	//2005/05/24　追加　ここから--------------------------------------------------
	//理由　表紙PDFファイル処理のため
	/**
	 * チェックリストの表紙PDFファイルパスを更新する。
	 * @param connection            コネクション
	 * @param pkInfo                主キー情報
	 * @param iodFile               PDFファイル
	 * @throws DataAccessException  更新処理中の例外。
	 * @throws NoDataFoundException　処理対象データがみつからないとき。
	 */
	public void updateFilePath(
		Connection connection,
		final CheckListSearchInfo pkInfo,
		File iodFile)
		throws DataAccessException, NoDataFoundException 
	{
		
		ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();
		shinseiInfo.setJigyoId(pkInfo.getJigyoId());
		shinseiInfo.getDaihyouInfo().setShozokuCd(pkInfo.getShozokuCd());
		//参照可能チェックリストデータかチェック
		int count = countCheckListInfo(connection, shinseiInfo);
		if(count == 0){
			throw new UserAuthorityException("参照可能なチェックリストデータではありません。");
		}
		
		String updateQuery =
				"UPDATE CHCKLISTINFO"
					+ " SET"
					+ " PDF_PATH = ?"
					+ " WHERE SHOZOKU_CD = ?"
					+ " AND JIGYO_ID = ? ";
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(updateQuery);
			int i = 1;
			//PDFファイルパス
			DatabaseUtil.setParameter(preparedStatement,i++,iodFile.getAbsolutePath());
			//所属コードと事業ID
			DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getJigyoId());
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException(
				"チェックリスト表紙PDFファイルパス更新中に例外が発生しました。 ：所属CD'"
					+ pkInfo.getShozokuCd()
					+ "', 事業ID'"
					+ pkInfo.getJigyoId()
					+ "'",
				ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}	
	
	
	/**
	 * 表紙PDFファイルパスを取得する。
	 * 
	 * @param connection	Connection
	 * @param info	CheckListSearchInfo
	 * @return	PDFファイルパス
	 * @throws DataAccessException	
	 */
	public String getPath(Connection connection, CheckListSearchInfo info)
		throws DataAccessException{
		
		String path = null;
		
		String query = "SELECT PDF_PATH FROM CHCKLISTINFO WHERE SHOZOKU_CD = ? AND JIGYO_ID = ?";
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			//所属コードと事業ID
			DatabaseUtil.setParameter(preparedStatement,i++,info.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getJigyoId());
			recordSet = preparedStatement.executeQuery();
		
			if(recordSet.next()){
				path = recordSet.getString("PDF_PATH"); 
			}
		}catch (SQLException ex) {
			   throw new DataAccessException("対象データが存在しません。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
		return path;
	}	
	
	
	/**
	 * 表紙PDFデータを取得する。
	 * 
	 * @param connection	Connection
	 * @param searchInfo	CheckListSearchInfo
	 * @return	表紙PDFデータを格納したCheckListInfo
	 */
	public CheckListInfo selectPdfData(Connection connection, CheckListSearchInfo searchInfo)
		throws DataAccessException{

		CheckListInfo resultInfo = new CheckListInfo();
		
		String query = 
			"SELECT " 
				+ "MASTER_JIGYO.JIGYO_NAME JIGYO_NAME" 
				+ ", COUNT(*) COUNT" 

				//新規・継続の件数 2005/07/25
				//2005/8/18 基盤C(企画)の場合、新規・継続区分がnullを登録されている為
				//新規・継続区分がnullである時も新規とする
			    + ", SUM(DECODE(NVL(B.SHINSEI_KUBUN,'1'),'1',1,0)) SHINKI_CNT"
			    + ", SUM(DECODE(B.SHINSEI_KUBUN,'2',1,0)) KEIZOKU_CNT"
				+ ", MIN(B.JIGYO_KUBUN) JIGYO_KUBUN"
				
				+ ", C.SHOZOKU_CD SHOZOKU_CD" 
				+ ", MASTER_KIKAN.SHOZOKU_NAME_KANJI SHOZOKU_NAME "
			+ "FROM SHINSEIDATAKANRI B "
				+ "INNER JOIN CHCKLISTINFO C "
					+ "INNER JOIN JIGYOKANRI A " 
					+ "ON A.JIGYO_ID = C.JIGYO_ID " 
					+ "INNER JOIN MASTER_JIGYO MASTER_JIGYO "
					+ "ON MASTER_JIGYO.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) " 
				+ "ON C.JIGYO_ID = B.JIGYO_ID "
				+ "AND C.SHOZOKU_CD = B.SHOZOKU_CD "
				+ "AND C.JOKYO_ID = B.JOKYO_ID "
				+ "INNER JOIN MASTER_KIKAN MASTER_KIKAN " 
				+ "ON MASTER_KIKAN.SHOZOKU_CD = B.SHOZOKU_CD "
				+ "WHERE B.DEL_FLG = 0 " 
// 20050606 Start 検索情報に事業区分を追加
//				+ "AND A.DEL_FLG = 0 " 
//				+ "AND B.JIGYO_KUBUN = 4 "
				+ "AND A.DEL_FLG = 0 ";
				if (searchInfo.getJigyoKubun() != null && 
					searchInfo.getJigyoKubun().length() > 0
					){
					query = query + " AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + " ";
				}
				else{
					//TODO：事業区分が不正の場合にはデフォルトの基盤事業区分をセット
					query = query + " AND B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN + " ";
				}
				query = query
// Horikoshi End
				+ "AND C.SHOZOKU_CD = ? " 
				+ "AND C.JIGYO_ID = ? "
			+ "GROUP BY " 
				+ "MASTER_JIGYO.JIGYO_NAME" 
				+ ", A.JIGYO_ID" 
				+ ", C.SHOZOKU_CD" 
				+ ", MASTER_KIKAN.SHOZOKU_NAME_KANJI ";
		if (log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			//所属コードと事業ID
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getJigyoId());
			recordSet = preparedStatement.executeQuery();
		
			if(recordSet.next()){			
				resultInfo.setJigyoName(recordSet.getString("JIGYO_NAME"));//事業名
				resultInfo.setShozokuCd(recordSet.getString("SHOZOKU_CD"));//所属CD
				resultInfo.setShozokuName(recordSet.getString("SHOZOKU_NAME"));//所属機関名
				resultInfo.setKaisu(recordSet.getString("COUNT"));//件数
				
				//2005/07/25新規・継続表紙分離対応
				resultInfo.setShinkiCount(recordSet.getInt("SHINKI_CNT"));//新規件数
				resultInfo.setKeizokuCount(recordSet.getInt("KEIZOKU_CNT"));//継続件数
				resultInfo.setJigyoKbn(recordSet.getString("JIGYO_KUBUN"));//事業区分
			}
		}catch (SQLException ex) {
			   throw new DataAccessException("対象データが存在しません。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
		return resultInfo;
	}		
	//追加　ここまで------------------------------------------------------------



// 20050722
	public List select(
			Connection connection,
			String chSQL,
			final String[] chItems
			)
		throws
			DataAccessException
		{

		int nCount							= 0;
		int nNum							= 0;
		List lstResult						= new ArrayList();
		PreparedStatement preparedStatement	= null;
		ResultSet recordSet					= null;

		try {
			preparedStatement = connection.prepareStatement(chSQL);
			recordSet = preparedStatement.executeQuery();
			while(recordSet.next()){
				String[] chValues				= new String[chItems.length];
				for(nNum=0; chItems.length > nNum; nNum++){
					chValues[nNum] = recordSet.getString(chItems[nNum]);
				}
				lstResult.add(nCount, chValues);
				nCount++;
			}
		}
		catch (SQLException ex) {
			   throw new DataAccessException("対象データが存在しません。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
		return lstResult;
	}
// Horikoshi
//  2007/03/02 苗　追加ここから
    /**
     * 申請機関確定済みの情報を返す。
     * @param connection
     * @param kikanCd
     * @param jigyoID
     * @return boolean
     * @throws DataAccessException
     */
    public boolean checkKakuteibi(Connection connection, String kikanCd,
            String jigyoID) throws DataAccessException {
        Date kakuteibi = null;
        String query = "SELECT KAKUTEI_DATE" // 解除フラグ
                + " FROM CHCKLISTINFO" + " WHERE" + " SHOZOKU_CD = ?" + " AND"
                + " JIGYO_ID = ?";
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, kikanCd); // 検索条件（申請機関）
            DatabaseUtil.setParameter(preparedStatement, i++, jigyoID); // 検索条件（事業ID）
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                kakuteibi = recordSet.getDate("KAKUTEI_DATE"); // 確定日
            }
        } catch (SQLException ex) {
            throw new DataAccessException("チェックリストテーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
        return kakuteibi != null;
    }
//2007/03/02　苗　追加ここまで   
}
