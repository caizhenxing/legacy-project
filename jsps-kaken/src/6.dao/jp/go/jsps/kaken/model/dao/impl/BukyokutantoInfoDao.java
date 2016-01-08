/*
 * 作成日: 2005/03/25
 *
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoPk;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.EscapeUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 部局担当者情報データアクセスクラス。
 * 
 * @author yoshikawa_h
 *
 */
public class BukyokutantoInfoDao {
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	/** ログ */
	protected static final Log log = LogFactory.getLog(BukyokutantoInfoDao.class);

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
	public BukyokutantoInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}	
	
	/**
	 * キーに一致する部局担当者情報を取得する。
	 * @param connection			コネクション
	 * @param pkInfo				主キー情報
	 * @return						部局担当者情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public BukyokutantoInfo selectBukyokutantoInfo(
		Connection connection,
		BukyokutantoPk pkInfo)
		throws DataAccessException, NoDataFoundException {
			String query =
				"SELECT "
					+ " BUKYOKU.BUKYOKUTANTO_ID"		//部局担当者ID
					+ ",BUKYOKU.PASSWORD"				//パスワード
					+ ",BUKYOKU.TANTO_NAME_SEI"			//担当者名（姓）
					+ ",BUKYOKU.TANTO_NAME_MEI"			//担当者名（名）
					+ ",BUKYOKU.BUKA_NAME"				//担当者部課名	
					//2005/04/06　追加　ここから--------------------------
					//理由　担当者係名が追加されたため
					+ ",BUKYOKU.KAKARI_NAME"
					//追加　ここまで--------------------------------------
					+ ",BUKYOKU.SHOZOKU_CD"				//所属機関コード
					+ ",BUKYOKU.BUKYOKU_TEL"			//電話番号
					+ ",BUKYOKU.BUKYOKU_FAX"			//FAX番号
					+ ",BUKYOKU.BUKYOKU_EMAIL"			//Email
					+ ",BUKYOKU.BUKYOKU_CD"				//部局コード
					+ ",BUKYOKU.DEFAULT_PASSWORD"		//デフォルトパスワード
					+ ",BUKYOKU.REGIST_FLG"				//登録済みフラグ
					+ ",BUKYOKU.DEL_FLG"				//削除フラグ
//					 2005/04/05 追加 ここから---------------------------------
//					 理由 ログイン時、有効期限チェックのため
					+ ",SHOZOKU.YUKO_DATE"			//有効期限
//					 追加 ここまで--------------------------------------------
//					 2005/04/21 追加 ここから---------------------------------
//					 理由 ログイン時、所属機関担当者の削除フラグもチェック
					+ ",SHOZOKU.DEL_FLG AS DEL_FLG_SHOZOKU"			//所属機関担当者の削除フラグ
//					 追加 ここまで--------------------------------------------
					+ " FROM BUKYOKUTANTOINFO BUKYOKU"
						+ " INNER JOIN SHOZOKUTANTOINFO SHOZOKU"
							+ " ON BUKYOKU.SHOZOKU_CD = SHOZOKU.SHOZOKU_CD"
					+ " WHERE BUKYOKU.BUKYOKUTANTO_ID = ?";
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			try {
				BukyokutantoInfo result = new BukyokutantoInfo();
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				preparedStatement.setString(i++, pkInfo.getBukyokutantoId());
				recordSet = preparedStatement.executeQuery();
				if (recordSet.next()) {
					result.setBukyokutantoId(recordSet.getString("BUKYOKUTANTO_ID"));
					result.setPassword(recordSet.getString("PASSWORD"));
					result.setTantoNameSei(recordSet.getString("TANTO_NAME_SEI"));
					//2005/04/06 変更　ここから
					//理由　担当者名（名）に担当者名（姓）が設定されていたため
					//result.setTantoNameMei(recordSet.getString("TANTO_NAME_SEI"));
					result.setTantoNameMei(recordSet.getString("TANTO_NAME_MEI"));
					//変更　ここまで
					result.setBukaName(recordSet.getString("BUKA_NAME"));
					//2005/04/06　追加　ここから
					//理由　担当者係名が追加されたため
					result.setKakariName(recordSet.getString("KAKARI_NAME"));
					//追加　ここまで
					result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
					result.setBukyokuTel(recordSet.getString("BUKYOKU_TEL"));
					result.setBukyokuFax(recordSet.getString("BUKYOKU_FAX"));
					result.setBukyokuEmail(recordSet.getString("BUKYOKU_EMAIL"));
					result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));
					result.setRegistFlg(recordSet.getString("REGIST_FLG"));
					result.setDelFlg(recordSet.getString("DEL_FLG"));
					result.setYukoDate(recordSet.getDate("YUKO_DATE"));
					result.setDelFlgShozoku(recordSet.getString("DEL_FLG_SHOZOKU"));
					return result;
				} else {
					throw new NoDataFoundException(
						"部局担当者情報テーブルに該当するデータが見つかりません。検索キー：部局担当者ID'"
							+ pkInfo.getBukyokutantoId()
							+ "'");
				}
			} catch (SQLException ex) {
				throw new DataAccessException("部局担当者情報テーブル検索実行中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}
	
	
	/**
	 * キーに一致する担当部局を取得する。
	 * @param connection			コネクション
	 * @param pkInfo				主キー情報
	 * @return						部局担当者情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public BukyokutantoInfo[] selectTantoBukyokuInfo(
		Connection connection,
		BukyokutantoPk pkInfo)
		throws DataAccessException, NoDataFoundException {
			String query =
				"SELECT "
					+ " BUKYOKUTANTO_ID"		//部局担当者ID
					+ ",BUKYOKU_CD"				//部局コード
					+ ",SHOZOKU_CD"			//所属機関コード
					+ ",BIKO"			//備考
					+ " FROM TANTOBUKYOKUKANRI"
					+ " WHERE BUKYOKUTANTO_ID = ?";
					
			if(pkInfo.getBukyokuCd() != null  && pkInfo.getBukyokuCd().endsWith("")){
				query += " AND BUKYOKU_CD = ?";
			}
			
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			
			BukyokutantoInfo[] tantoInfos = null;
			try {
				BukyokutantoInfo result = new BukyokutantoInfo();
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				preparedStatement.setString(i++, pkInfo.getBukyokutantoId());
				
				if(pkInfo.getBukyokuCd() != null  && pkInfo.getBukyokuCd().endsWith("")){
					preparedStatement.setString(i++, pkInfo.getBukyokuCd());
				}
				recordSet = preparedStatement.executeQuery();
				
				List resultList = new ArrayList();
				while (recordSet.next()) {
					result.setBukyokutantoId(recordSet.getString("BUKYOKUTANTO_ID"));
					result.setTantoBukyokuCd(recordSet.getString("BUKYOKU_CD"));
					result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
					result.setBiko(recordSet.getString("BIKO"));
					resultList.add(result);	
					
				}
				
				//戻り値
				tantoInfos = (BukyokutantoInfo[])resultList.toArray(new BukyokutantoInfo[0]);
				
			} catch (SQLException ex) {
				throw new DataAccessException("部局担当者情報テーブル検索実行中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
			
			return tantoInfos;
	}
	
	/**
	 * パスワードの変更する。 
	 * @param connection			コネクション
	 * @param pkInfo				主キー情報
	 * @param newPassword			新しいパスワード
	 * @return              		パスワードの変更に成功した場合 true 以外 false
	 * @throws DataAccessException	変更中に例外が発生した場合
	 */
	public boolean changePasswordBukyokutantoInfo(
		Connection connection,
		BukyokutantoPk pkInfo,
		String newPassword)
		throws DataAccessException {

			String query = "UPDATE BUKYOKUTANTOINFO "
						 + "SET "
						 + "PASSWORD = ? "
						 + "WHERE "
						 + "BUKYOKUTANTO_ID = ? "
						 + "AND DEL_FLG = 0";									//削除フラグ

			PreparedStatement ps = null;
			
			if(log.isDebugEnabled()){
				log.debug("query:" + query);
			}
		
			try {
				//登録
				ps = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(ps,i++, newPassword);					//新しいパスワード
				DatabaseUtil.setParameter(ps,i++, pkInfo.getBukyokutantoId());	//部局担当者ID
				DatabaseUtil.executeUpdate(ps);
			} catch (SQLException ex) {
				throw new DataAccessException("パスワード変更中に例外が発生しました。 ", ex);

			} finally {
				DatabaseUtil.closeResource(null, ps);
			}
			return true;
	}

	
	// 2005/04/07 追加ここから---------------------------------------------
	// 理由 部局担当者情報の登録、更新、削除、パスワード変更処理追加ため
	
	
	/** 部局担当者情報を更新する。
	 * 部局担当者情報テーブルのデータ更新と担当部局管理テーブルのデータの追加を行う。
	 * 
	 * @param		connection			コネクション
	 * @param		info				部局担当者情報	
	 * @exception	DataAccessException	
	 * @exception	ApplicationException 	
	 */
	public void updateBukyokuData(Connection connection, 
				BukyokutantoInfo info)
				throws DataAccessException, ApplicationException{

		//部局担当者情報を更新する
		String query = "UPDATE BUKYOKUTANTOINFO "
							 + "SET "
							 + " TANTO_NAME_SEI = ? "
							 + ", TANTO_NAME_MEI = ? "
							 + ", BUKA_NAME = ? "
							 + ", KAKARI_NAME = ? "
							 + ", BUKYOKU_TEL = ? "
							 + ", BUKYOKU_FAX = ? "
							 + ", BUKYOKU_EMAIL = ? "
							 + ", REGIST_FLG = 1 ";
		//20005/06/01 削除 ここから----------------------------------------
		//理由 パスワードの変更を行わないため削除
		
		//if(info.getAction() != null && info.getAction().equals("add")){
		//	query += 			", PASSWORD = ? ";
		//}
		//削除 ここまで----------------------------------------------------					 
		query =	query  	     + "WHERE "
							 + "BUKYOKUTANTO_ID = ? "
							 + "AND DEL_FLG = 0";			//削除フラグ
		PreparedStatement ps = null;
			
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		try {
			//更新
			ps = connection.prepareStatement(query);
			int i = 1;	
			DatabaseUtil.setParameter(ps,i++, info.getTantoNameSei());	//部局担当者姓
			DatabaseUtil.setParameter(ps,i++, info.getTantoNameMei());	//部局担当者名
			DatabaseUtil.setParameter(ps,i++, info.getBukaName());		//部課名
			DatabaseUtil.setParameter(ps,i++, info.getKakariName());	//係名
			DatabaseUtil.setParameter(ps,i++, info.getBukyokuTel());	//電話番号
			DatabaseUtil.setParameter(ps,i++, info.getBukyokuFax());	//FAX
			DatabaseUtil.setParameter(ps,i++, info.getBukyokuEmail());	//mail
			//20005/06/01 削除 ここから----------------------------------------
			//理由 パスワードの変更を行わないため削除
		
			//if(info.getAction() != null && info.getAction().equals("add")){
			//	DatabaseUtil.setParameter(ps,i++, newPassword);			//パスワード
			//}	
			//削除 ここまで----------------------------------------------------	
			DatabaseUtil.setParameter(ps,i++, info.getBukyokutantoId());//部局担当者ID
			DatabaseUtil.executeUpdate(ps);
		}catch (SQLException ex) {
			try {
					DatabaseUtil.rollback(connection);
				} catch (TransactionException tex) {
					throw new ApplicationException(
						"部局担当者情報更新中にDBエラーが発生しました",
						new ErrorInfo("errors.4002"),
						tex);
				} finally {
					DatabaseUtil.closeResource(null, ps);
				}
			throw new DataAccessException("部局担当者情報更新中に例外が発生しました。 ", ex);

		}
		
		//担当部局管理テーブルにデータを登録する
		String insertQuery = null;

		PreparedStatement statement = null;
		boolean success = false;
		
		try {
			//部局コードの登録		
			int i = 1;
			//部局コードの個数分処理を繰り返し
			for(int j=0; j < info.getBukyokuList().size(); j++){
				Object bukyokuCd = info.getBukyokuList().get(j);
				if(bukyokuCd != null && !bukyokuCd.equals("")){
					insertQuery = "INSERT INTO TANTOBUKYOKUKANRI(" 
									+ "BUKYOKUTANTO_ID, " 
									+ "SHOZOKU_CD, " 
									+ "BUKYOKU_CD) "
									+ "VALUES(?, ?, ?)";
					statement = connection.prepareStatement(insertQuery);
					DatabaseUtil.setParameter(statement,i++, info.getBukyokutantoId());	//部局担当者姓
					DatabaseUtil.setParameter(statement,i++, info.getShozokuCd());		//所属コード
					DatabaseUtil.setParameter(statement,i++, (String)bukyokuCd);		//部局コード
					statement.executeQuery();
					i = 1;
				}
			}
			success = true;
		}catch (SQLException ex) {
				throw new DataAccessException("担当部局管理登録中に例外が発生しました。 ", ex);
		
		} finally {		
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"パスワード変更中にDBエラーが発生しました",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeResource(null, statement);
			}
		}						
	}
	
	
	/** 削除処理を行う。 
	 * 	担当部局管理テーブルから所属コードの一致する情報を削除する。
	 * 
	 * @param		connection			コネクション
	 * @param		info				部局担当者情報
	 * @exception	DataAccessException
	 * @exception	ApplicationException
	 */
	public void deleteBukyokuCd(Connection connection, BukyokutantoInfo info)
		throws DataAccessException, ApplicationException {

		boolean success = false;
		//削除処理
		String deleteQuery =
			"DELETE FROM TANTOBUKYOKUKANRI "
				+ "WHERE BUKYOKUTANTO_ID = ? "
				+ "AND SHOZOKU_CD = ? ";
		
		if(log.isDebugEnabled()){
			log.debug("query:" + deleteQuery);
		}
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = connection.prepareStatement(deleteQuery);
			int i = 1;
			DatabaseUtil.setParameter(prepareStatement,	i++, info.getBukyokutantoId());
			DatabaseUtil.setParameter(prepareStatement,	i++, info.getShozokuCd());
			prepareStatement.executeQuery();
			success = true;
		} catch (SQLException e) {
			throw new ApplicationException(
				"担当部局管理情報削除中にDBエラが発生しました", new ErrorInfo("errors.4002"), e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"当部局管理情報削除確定中にDBエラーが発生しました", new ErrorInfo("errors.4002"), e);
			} finally {
				DatabaseUtil.closeResource(null, prepareStatement);
			}
		}
	}
	
	
	/** 
	 * 部局コードのチェックを行う。
	 * 
	 * @param		connection			コネクション
	 * @param 		array				部局コードの配列
	 * @exception	DataAccessException
	 * @exception	NoDataFoundException
	 */
	public int CheckBukyokuCd(Connection connection, HashSet set)
		throws DataAccessException, NoDataFoundException {

			String query = "SELECT COUNT(*) COUNT "
						 + "FROM MASTER_BUKYOKU ";			
			PreparedStatement ps = null;
			ResultSet recordSet = null;
			
			int count = 0;
			try {
				Iterator iter = set.iterator();
				if(set.size() == 1){
					query= query + "WHERE BUKYOKU_CD = "+EscapeUtil.toSqlString(iter.next().toString());
				}
				else{
					query = query + "WHERE BUKYOKU_CD IN(";
					while(iter.hasNext()){
						Object bukyokuCd = iter.next();
						if(bukyokuCd != null && !bukyokuCd.equals("")){
							query = query + "'" + EscapeUtil.toSqlString(bukyokuCd.toString()) + "',";
						}
						
					}
					query = query.substring(0, query.length()-1) + ")";
				}
				
				if(log.isDebugEnabled()){
					log.debug("query:" + query);
				}
				
				ps = connection.prepareStatement(query);
				recordSet = ps.executeQuery();
				if(recordSet.next()){
					count = recordSet.getInt("COUNT");
				}else{
					throw new NoDataFoundException(
						"担当部局管理情報テーブルに該当するデータが見つかりません。" +
							"検索キー：部局CD'" + set + "'");
				}
						
			} catch (SQLException ex) {
				throw new DataAccessException("部局コード確認中に例外が発生しました。 ", ex);

			} finally {
				DatabaseUtil.closeResource(null, ps);
			}
			return count;
	}
	
	
	/** 
	 * 所属コードの配列を取得する。
	 *  
	 * @param		connection			コネクション
	 * @param 		info				部局担当者情報
	 * @exception	DataAccessExcepiton
	 */
	public ArrayList selectTantoBukyokuKanri(Connection connection, BukyokutantoInfo info)
	throws DataAccessException{
	
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet recordSet = null;
		
		String query = "SELECT BUKYOKU_CD FROM TANTOBUKYOKUKANRI WHERE BUKYOKUTANTO_ID = ? ";	
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		try {
			//更新
			ps = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(ps,i++, info.getBukyokutantoId());
			recordSet = ps.executeQuery();
			for(int j = 0; j < 30; j++){
				if(recordSet.next()) {
					list.add(recordSet.getString("BUKYOKU_CD"));
				}else{
					list.add("");
				}				
			}					
			return list;
					
		} catch (SQLException ex) {
			throw new DataAccessException("所属コード取得中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, ps);
		}
	}
	
	
	/** 
	 * 削除処理。
	 * 部局担当者情報テーブルから部局担当IDが一致する情報の削除フラグを1(削除済み)にする。
	 * 
	 * @param		connection			コネクション
	 * @param 		info				部局担当者情報
	 * @exception	DataAccessException
	 * @exception	ApplicationException
	 */
	public void deleteBukyokuData(Connection connection, BukyokutantoInfo info)
		throws DataAccessException, ApplicationException {
		
		PreparedStatement ps = null;
		ResultSet recordSet = null;
			
		String query = "UPDATE BUKYOKUTANTOINFO " 
					 + "SET DEL_FLG = 1 "
					 + "WHERE BUKYOKUTANTO_ID = ? "
					 + "AND DEL_FLG = 0";
			
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		try {
			//削除
			ps = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(ps, i++, info.getBukyokutantoId());//部局担当者ID
			DatabaseUtil.executeUpdate(ps);
	
		} catch (SQLException ex) {
			throw new DataAccessException(
				"部局担当者テーブル情報削除中に例外が発生しました。 ",
				ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, ps);
		}
	}
	
	
	/**
	 * パスワードをデフォルトパスワードに戻す。 
	 * 
	 * @param connection			コネクション
	 * @param info					部局担当者情報
	 * @param newPassword			新しいパスワード
	 * @return              		パスワードの変更に成功した場合 true 以外 false
	 * @throws DataAccessException	変更中に例外が発生した場合
	 */
	public boolean originPassword(
		Connection connection,
		BukyokutantoInfo info)
		throws DataAccessException {

			String query = "UPDATE BUKYOKUTANTOINFO"
						 + " SET"
			//2005/04/13　修正 ここから------------------------------------------------------
			//理由 パスワードにデフォルトパスワードを設定するため
						 + " PASSWORD = DEFAULT_PASSWORD"
			//修正 ここまで------------------------------------------------------------------
						 + " WHERE"
						 + " BUKYOKUTANTO_ID = ?"
						 + " AND DEL_FLG = 0";									//削除フラグ

			PreparedStatement preparedStatement = null;
			
			if(log.isDebugEnabled()){
				log.debug("query:" + query);
			}
		
			try {
				//登録
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,info.getBukyokutantoId());	//部局担当者ID
				DatabaseUtil.executeUpdate(preparedStatement);
			} catch (SQLException ex) {
				throw new DataAccessException("パスワード変更中に例外が発生しました。 ", ex);

			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
			return true;
	}
	
	
	/** 
	 * 所属管理情報を取得する。
	 * 
	 * @param 		connection			コネクション
	 * @param 		info				部局担当者情報
	 * @return		info				部局担当者情報(所属管理情報セット済)
	 * @exception	DataAccessException	
	 * @exception	NoDataFoundException
	 */
	public BukyokutantoInfo selectShozokuData(Connection connection,
		BukyokutantoInfo info)
		throws DataAccessException, NoDataFoundException {

		String query = "SELECT"
//					 + " M.SHOZOKU_CD,"
					 + " SHOZOKU.SHOZOKU_CD,"
//					 + " M.SHOZOKU_NAME_KANJI,"
					 + " SHOZOKU.SHOZOKU_NAME_KANJI,"
				//2005/04/28　変更 ここから------------------------
				//理由 所属機関名の英文は機関マスタに登録されないため
				//	 + " M.SHOZOKU_NAME_EIGO "
					 + " SHOZOKU.SHOZOKU_NAME_EIGO"
				//変更 ここまで------------------------------------
					 + " FROM BUKYOKUTANTOINFO B"
//					 +	 " INNER JOIN MASTER_KIKAN M"
//					 +	 " ON B.SHOZOKU_CD = M.SHOZOKU_CD "
			//2005/04/28　追加 ここから----------------------------
			//理由 所属機関名の英文を所属機関担当者から取得するため
					 +   " INNER JOIN SHOZOKUTANTOINFO SHOZOKU"
					 +   " ON SHOZOKU.SHOZOKU_CD = B.SHOZOKU_CD"
			//追加 ここまで----------------------------------------
					 + " WHERE B.BUKYOKUTANTO_ID = ?";

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
			
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,info.getBukyokutantoId());	//部局担当者ID
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				info.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
				info.setShozokuName(recordSet.getString("SHOZOKU_NAME_KANJI"));
				info.setShozokuNameEigo(recordSet.getString("SHOZOKU_NAME_EIGO"));						
				return info;
			} else {
				throw new NoDataFoundException(
					"部局担当者情報テーブルに該当するデータが見つかりません。検索キー：部局担当者ID'"
						+ info.getBukyokutantoId()
						+ "'");
			}

		} catch (SQLException ex) {
			throw new DataAccessException("所属管理情報取得中に例外が発生しました。 ", ex);

		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	// 追加 ここまで--------------------------------------------------------
	
	//	2005/04/08 追加 ここから--------------------------------------------
	//	理由 ログイン時のID・パスワードチェックのため
	/**
	 * ユーザID、パスワードの認証を行う。
	 * @param connection		コネクション
	 * @param userid			ユーザID
	 * @param password			パスワード
	 * @return					認証に成功した場合 true 以外 false
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 */
	public boolean authenticateBukyokuInfo(
		Connection connection, String userid, String password)
		throws DataAccessException {
		String query =
			"SELECT count(*)"
				+ " FROM BUKYOKUTANTOINFO"
				+ " WHERE DEL_FLG = 0"
				+ " AND BUKYOKUTANTO_ID = ?"
				+ " AND PASSWORD = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		int count = 0;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, userid);
			preparedStatement.setString(i++, password);
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			//一致するデータが存在する場合は、true返す
			if(count > 0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException ex) {
			throw new DataAccessException("部局担当者情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	// 追加 ここまで--------------------------------------------------------
	
	
	// 2005/04/21 追加 ここから------------------------------------------------------------------------------
	// 理由 部局担当者情報の追加処理
	
	/** 部局担当者情報を追加する。
	 * 部局担当者情報テーブルのデータ追加を行う。
	 * 
	 * @param		connection			コネクション
	 * @param		info				部局担当者情報	
	 * @exception	DataAccessException	
	 * @exception	ApplicationException 	
	 */
	public void insertBukyokuData(Connection connection,BukyokutantoInfo info)
				throws DataAccessException, ApplicationException{
		
		//部局担当者情報を追加する
		String query = 
			 "INSERT INTO BUKYOKUTANTOINFO ("
				+ " BUKYOKUTANTO_ID"
			//2005/06/01 追加 ここから-------------------------
			//理由 パスワードを部局担当者情報追加時に登録するため(VALUESの?も追加)
				+ " ,PASSWORD"
			//追加 ここまで------------------------------------
				+ " ,SHOZOKU_CD"
				+ " ,DEFAULT_PASSWORD"
				+ " ,REGIST_FLG"
				+ " ,DEL_FLG)"
			+ " VALUES( ?,?,?,?,?,? )";
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++, info.getBukyokutantoId());	//部局担当者ID
			//2005/06/01 追加 ここから---------------------------------------------
			//理由 パスワードを部局担当者情報追加時に登録するため
			DatabaseUtil.setParameter(preparedStatement,i++, info.getDefaultPassword());	//パスワード
			//追加 ここまで--------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++, info.getShozokuCd());	//所属機関コード
			DatabaseUtil.setParameter(preparedStatement,i++, info.getDefaultPassword());	//デフォルトパスワード
			DatabaseUtil.setParameter(preparedStatement,i++, "0");						//登録済みフラグ
			DatabaseUtil.setParameter(preparedStatement,i++, "0");						//削除フラグ
			DatabaseUtil.executeUpdate(preparedStatement);
		}catch (SQLException ex) {
			try {
					DatabaseUtil.rollback(connection);
				} catch (TransactionException tex) {
					throw new ApplicationException(
						"部局担当者情報登録中にDBエラーが発生しました",
						new ErrorInfo("errors.4002"),
						tex);
				} finally {
					DatabaseUtil.closeResource(null, preparedStatement);
				}
			throw new DataAccessException("部局担当者情報登録中に例外が発生しました。 ", ex);

		}						
	}
	// 追加 ここまで---------------------------------------------------------------------------------------------
	
	// 2005/04/21 追加 ここから------------------------------------------------------------------------------
	// 理由 部局担当者連番取得用
	
    /**
     *　所属機関毎の連番を取得する。
     * @param connection           コネクション
     * @param shozokuCd            所属機関コード
     * @return                     連番(2桁)
     * @throws DataAccessException　データベースアクセス中の例外
     */
    public String getSequenceNo(Connection connection,String shozokuCd) throws DataAccessException {
        String query =
            "SELECT TO_CHAR(MAX(SUBSTR(BUKYOKUTANTO_ID,6,2)) + 1,'FM00') COUNT"
                + " FROM BUKYOKUTANTOINFO"
                + " WHERE SHOZOKU_CD = ?";
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, shozokuCd);
            recordSet = preparedStatement.executeQuery();
            
            String ret = null;
            if (recordSet.next()) {
				ret= recordSet.getString(1);
            }
			//部局連番は10からとする 20055/07/14
			if(ret == null){
				//ret = "01";
				ret = "10";
			}
            return ret;
            
        } catch (SQLException ex) {
            throw new DataAccessException("部局担当者情報テーブル検索実行中に例外が発生しました。", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    
	// 追加 ここまで---------------------------------------------------------------------------------------------
    
    // 2005/04/22 追加 ここから----------------------------------------------------------------------------------
    // 理由 所属機関担当者削除時、同所属機関に属する部局担当者も情報を削除するため
	/** 
	 * 同所属機関に属する部局担当者を一括削除（論理削除）する。
	 * 部局担当者情報テーブルから所属機関コードが一致する情報の削除フラグを1(削除済み)にする。
	 * 
	 * @param		connection			コネクション
	 * @param 		shozokuCd			所属機関コード
	 * @exception	DataAccessException
	 * @exception	ApplicationException
	 */
	public void deleteBukyokuDataAll(Connection connection, String shozokuCd)
		throws DataAccessException, ApplicationException {
		
		PreparedStatement ps = null;
		ResultSet recordSet = null;
			
		String query = "UPDATE BUKYOKUTANTOINFO " 
					 + "SET DEL_FLG = 1 "
					 + "WHERE SHOZOKU_CD = ? "
					 + "AND DEL_FLG = 0";
			
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		try {
			//削除
			ps = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(ps, i++, shozokuCd);//所属機関コード
			ps.executeUpdate();
	
		} catch (SQLException ex) {
			throw new DataAccessException(
				"部局担当者テーブル情報削除中に例外が発生しました。 ",
				ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, ps);
		}
	}
	// 追加 ここまで---------------------------------------------------------------------------------------------
    
    // 2005/04/22 追加 ここから----------------------------------------------------------------------------------
    // 理由 所属機関担当者削除時、同所属機関に属する部局担当者情報も削除するため
    /**
     * 同所属機関に属する部局担当者の担当部局情報を削除する。（物理削除）
     * 
     * @param connection           コネクション
     * @param shozokuCd            所属機関コード
     * @throws DataAccessException　データベースアクセス中の例外
     */
	public void deleteBukyokuCdAll(Connection connection, String shozokuCd)
		throws DataAccessException, ApplicationException {

		boolean success = false;
		//削除処理
		String deleteQuery =
			"DELETE FROM TANTOBUKYOKUKANRI "
				+ "WHERE SHOZOKU_CD = ? ";
		
		if(log.isDebugEnabled()){
			log.debug("query:" + deleteQuery);
		}
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = connection.prepareStatement(deleteQuery);
			int i = 1;
			DatabaseUtil.setParameter(prepareStatement,	i++, shozokuCd);
			prepareStatement.executeQuery();
			success = true;
		} catch (SQLException e) {
			throw new ApplicationException(
				"担当部局管理情報削除中にDBエラが発生しました", new ErrorInfo("errors.4002"), e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"当部局管理情報削除確定中にDBエラーが発生しました", new ErrorInfo("errors.4002"), e);
			} finally {
				DatabaseUtil.closeResource(null, prepareStatement);
			}
		}
	}
	// 追加 ここまで---------------------------------------------------------------------------------------------
	
	//2005/06/01 追加 ここから----------------------------------------------
	//証明書発行用CSVの部局担当者データ取得のため
	/**
	 *　証明書発行用CSVのデータを取得する.<br>
	 *
	 * @param connection		コネクション
	 * @param info				所属機関情報
	 * @param list				所属機関CSVデータ	
	 * @return  list           CSVデータリスト    
	 * @throws DataAccessException　データベースアクセス中の例外
	 */
	public List getShomeiCsvData(Connection connection, ShozokuInfo info, List list) 
		throws DataAccessException {
		String query =
			"SELECT SUBSTR(BUKYOKUTANTO_ID,0,7) CODE, PASSWORD " +
			"FROM BUKYOKUTANTOINFO " +
			"WHERE SHOZOKU_CD = ? AND DEL_FLG = 0";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		List line = new ArrayList();
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, info.getShozokuCd());
			recordSet = preparedStatement.executeQuery();
			
			//設定ファイルからCSV固定データを取得
			String profileName = ApplicationSettings.getString(ISettingKeys.PROFILE_NAME);			//profile name
			String subjectDn = ApplicationSettings.getString(ISettingKeys.SUBJECT_DN);				//subject DN
			String subjectAltName = ApplicationSettings.getString(ISettingKeys.SUBJECT_ALT_NAME);	//subjectAltName
			String pubkeyAlgo = ApplicationSettings.getString(ISettingKeys.PUBKEY_ALGO);			//pubkey algo
			String keyLength = ApplicationSettings.getString(ISettingKeys.KEY_LENGTH);				//key length
			String p12Flag = ApplicationSettings.getString(ISettingKeys.P12_FLAG);		
				
			String shozoku = null;
			String renban = null;
			String password = null;
			//所属機関情報をリストに格納
			line.add(list);
			
			while (recordSet.next()) {
				List innerLine = new ArrayList();
				shozoku = recordSet.getString("CODE");
				if(shozoku != null && shozoku.length() != 0){
					renban = shozoku.substring(0,7);
				}
				password = recordSet.getString("PASSWORD");
				//-------CSVデータをリストに格納
				innerLine.add("\"" +profileName + "\"");				//profile name
				innerLine.add(shozoku);				//部局担当者IDの上7桁(所属機関コード+連番(2桁))
				innerLine.add("\"" + subjectDn + info.getShozokuNameEigo() + renban + "\"");	//subject DN, 所属機関名(英文) + 連番(IDの連番)
				innerLine.add("\"" + subjectAltName + "\"");			//subjectAltName
				innerLine.add(pubkeyAlgo);							//pubkey algo
				innerLine.add(keyLength);							//key length	
				innerLine.add(p12Flag);								//p12 flag
				innerLine.add("\"" + password+ "\"");	//パスワード
				
				line.add(innerLine);
			}	
            
		} catch (SQLException ex) {
			throw new DataAccessException("部局担当者情報テーブル検索実行中に例外が発生しました。", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return line;
	}
	//追加 ここまで---------------------------------------------------------


	/**
	 * 部局担当者のパスワードを取得する。
	 * @param connection
	 * @param info
	 * @throws DataAccessException
	 * @throws ApplicationException
	 */
	public String getTantoPassword(Connection connection, 
			BukyokutantoInfo info)
			throws DataAccessException, ApplicationException{
		
		//部局担当者パスワード情報を更新する
		String query = "SELECT PASSWORD"
					 + "  FROM BUKYOKUTANTOINFO "
					 + " WHERE BUKYOKUTANTO_ID = ? "
					 + " AND DEL_FLG = 0";			//削除フラグ

		PreparedStatement ps = null;
		ResultSet recordSet = null;
		String password = "";

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		try {
			//更新
			ps = connection.prepareStatement(query);
			int i = 1;	
			ps.setString(i++, info.getBukyokutantoId());//部局担当者ID
			recordSet = ps.executeQuery();

			if (recordSet.next()) {
				password = recordSet.getString("PASSWORD");
			}	
			
		}catch (SQLException ex) {
			throw new DataAccessException("部局担当者情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, ps);
		}
		return password;
	}

}
