/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.BukyokuInfo;
import jp.go.jsps.kaken.model.vo.BukyokuPk;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *　部局マスタデータアクセスクラス。
 * ID RCSfile="$RCSfile: MasterBukyokuInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class MasterBukyokuInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(MasterBukyokuInfoDao.class);

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
	public MasterBukyokuInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	
	/**
	 * 部局情報の一覧を取得する。
	 * @param	connection			コネクション
	 * @return						部局情報
	 * @throws ApplicationException
	 */
	public static List selectBukyokuInfoList(Connection connection)
		throws ApplicationException
	{
		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUKYOKU_CD"		//部局コード
			+ ",A.BUKA_NAME"		//部科名称
			+ ",A.BUKA_RYAKUSHO"	//部科略称
			+ ",A.BUKA_KATEGORI"	//カテゴリ
			+ ",A.SORT_NO"			//ソート番号
			+ ",A.BIKO"				//備考
			+ " FROM MASTER_BUKYOKU A"
			+ " ORDER BY SORT_NO";	//ソート番号						
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"部局情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"部局マスタに1件もデータがありません。",
				e);
		}
	}
	
	
	
	//TODO コード表
	/**
	 * 部局情報の一覧を取得する。
	 * @param	connection			コネクション
	 * @return						部局情報
	 * @throws ApplicationException
	 */
	public static List selectBukyokuInfoList(Connection connection, String bukaKategori)
		throws ApplicationException {
		
		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUKYOKU_CD"		//部局コード
			+ ",A.BUKA_NAME"		//部科名称
			+ ",A.BUKA_RYAKUSHO"	//部科略称
			+ ",A.BUKA_KATEGORI"	//カテゴリ
			+ ",A.SORT_NO"			//ソート番号
			+ ",A.BIKO"				//備考
			+ " FROM MASTER_BUKYOKU A"
			+ " WHERE BUKA_KATEGORI = ?"
			+ " ORDER BY SORT_NO";	//ソート番号						
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString(), new String[]{bukaKategori});
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"部局情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"部局マスタに1件もデータがありません。",
				e);
		}
	}

	//TODO コード表
	/**
	 * コード一覧作成用メソッド。<br>
	 * カテゴリとカテゴリ名称の一覧を取得する。
	 * カテゴリ順にソートする。
	 * @param	connection			コネクション
	 * @return
	 * @throws ApplicationException
	 */
	public static List selectKategoriInfoList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUKA_KATEGORI,"			//カテゴリ
			+ " B.KATEGORI_NAME"			//カテゴリ名称
			+ " FROM MASTER_BUKYOKU A, MASTER_KATEGORI B"
			+ " WHERE A.BUKA_KATEGORI = B.BUKA_KATEGORI"
			+ " GROUP BY A.BUKA_KATEGORI, B.KATEGORI_NAME"
			+ " ORDER BY A.BUKA_KATEGORI";								
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"部局情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"部局マスタに1件もデータがありません。",
				e);
		}
	}
	
	
	
	

	/**
	 * キーに一致する部局情報を取得する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						部局情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public BukyokuInfo selectBukyokuInfo(Connection connection,BukyokuPk pkInfo)
		throws DataAccessException, NoDataFoundException {

		BukyokuInfo result = new BukyokuInfo();
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement =
				connection.prepareStatement(
					"SELECT * FROM MASTER_BUKYOKU WHERE BUKYOKU_CD = ? ");
			int i = 1;
			//preparedStatement.setInt(i++, Integer.parseInt(pkInfo.getBukyokuCd()));
			DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getBukyokuCd());
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));
				result.setBukaName(recordSet.getString("BUKA_NAME"));
				result.setBukaRyakusyo(recordSet.getString("BUKA_RYAKUSHO"));
				result.setBukaKategori(recordSet.getString("BUKA_KATEGORI"));
				result.setSortNo(recordSet.getString("SORT_NO"));
				result.setBiko(recordSet.getString("BIKO"));
			} else {
				throw new NoDataFoundException(
					"部局マスタに該当するデータが見つかりません。検索キー：部局コード'"
						+ pkInfo.getBukyokuCd()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("部局マスタ検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return result;
	}



	/**
	 * 部局情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する部局情報
	 * @throws DataAccessException		登録に例外が発生した場合。
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertBukyokuInfo(Connection connection,BukyokuInfo addInfo)
		throws DataAccessException,DuplicateKeyException {

		//重複チェック
		try {
			selectBukyokuInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}

		String query = "INSERT INTO MASTER_BUKYOKU "
					 + "("
					 + " BUKYOKU_CD"
					 + ",BUKA_NAME"
					 + ",BUKA_RYAKUSHO"
					 + ",BUKA_KATEGORI"
					 + ",SORT_NO"
					 + ",BIKO"
					 + ") "
					 + "VALUES "
					 + "(?,?,?,?,?,?)"
					 ;

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukaName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukaRyakusyo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukaKategori());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSortNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("部局マスタ登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * 部局情報を更新する。
	 * @param connection			コネクション
	 * @param updateInfo			更新する部局情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateBukyokuInfo(Connection connection,BukyokuInfo updateInfo)
		throws DataAccessException, NoDataFoundException {
		
		//検索（対象データが存在しなかった場合は例外発生）
		selectBukyokuInfo(connection, updateInfo);
		
		String query = "UPDATE MASTER_BUKYOKU "
					 + " SET"
					 + " BUKYOKU_CD = ?"
					 + ",BUKA_NAME = ?"
					 + ",BUKA_RYAKUSHO = ?"
					 + ",BUKA_KATEGORI = ?"
					 + ",SORT_NO = ?"
					 + ",BIKO = ?"
					 + " WHERE "
					 + " BUKYOKU_CD = ?"
					 ;

		PreparedStatement preparedStatement = null;
		try {
			//更新
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukaName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukaRyakusyo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukaKategori());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSortNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuCd());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("部局マスタ登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	
	
	/**
	 * 部局情報を削除する。
	 * @param connection			コネクション
	 * @param deleteInfo			削除する部局主キー情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合 
	 */
	public void deleteBukyokuInfo(Connection connection,BukyokuPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
		
		//検索（対象データが存在しなかった場合は例外発生）
		selectBukyokuInfo(connection, deleteInfo);

		String query =
			"DELETE FROM MASTER_BUKYOKU"
				+ " WHERE"
				+ " BUKYOKU_CD = ?";

		PreparedStatement preparedStatement = null;
		try {
			//削除
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getBukyokuCd());
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("部局マスタ削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

}
