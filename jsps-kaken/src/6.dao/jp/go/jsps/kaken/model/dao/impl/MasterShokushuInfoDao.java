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
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShokushuInfo;
import jp.go.jsps.kaken.model.vo.ShokushuPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *　職種マスタデータアクセスクラス。
 * ID RCSfile="$RCSfile: MasterShokushuInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class MasterShokushuInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(MasterShokushuInfoDao.class);

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
	public MasterShokushuInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * 職種情報の一覧を取得する。
	 * @param	connection			コネクション
	 * @return						職種情報
	 * @throws ApplicationException
	 */
	public static List selectShokushuList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.SHOKUSHU_CD"			//職種コード
			+ ",A.SHOKUSHU_NAME"		//職名称
			+ ",A.SHOKUSHU_NAME_RYAKU"	//職名(略称)
			+ ",A.BIKO"					//備考
			+ " FROM MASTER_SHOKUSHU A"
			+ " ORDER BY A.SHOKUSHU_CD";								
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
				"職種情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"職種マスタに1件もデータがありません。",
				e);
		}
	}

	/**
	 * 職種情報を取得する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						職種情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public ShokushuInfo selectShokushuInfo(
		Connection connection,
		ShokushuPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		String query =
			"SELECT "
				+ " A.SHOKUSHU_CD"				//職種コード
				+ ",A.SHOKUSHU_NAME"			//職名称
				+ ",A.SHOKUSHU_NAME_RYAKU"		//職名(略称)
				+ ",A.BIKO"						//備考
				+ " FROM MASTER_SHOKUSHU A"
				+ " WHERE SHOKUSHU_CD = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShokushuInfo result = new ShokushuInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getShokushuCd());
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));
				result.setShokushuName(recordSet.getString("SHOKUSHU_NAME"));
				result.setShokushuNameRyaku(recordSet.getString("SHOKUSHU_NAME_RYAKU"));
				result.setBiko(recordSet.getString("BIKO"));
				return result;
			} else {
				throw new NoDataFoundException(
					"職種情報テーブルに該当するデータが見つかりません。検索キー：職種コード'"
						+ primaryKeys.getShokushuCd()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("職種情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * 職種情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する職種情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertShokushuInfo(
		Connection connection,
		ShokushuInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//重複チェック
		try {
			selectShokushuInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO MASTER_SHOKUSHU "
				+ "("
				+ " SHOKUSHU_CD"			//職種コード
				+ ",SHOKUSHU_NAME"			//職名
				+ ",SHOKUSHU_NAME_RYAKU"	//職名(略称)
				+ ",BIKO"					//備考
				+ ")"
				+ "VALUES "
				+ "(?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuNameRyaku());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("職種情報登録中に例外が発生しました。 ", ex);
			throw new DataAccessException("職種情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * 職種情報を更新する。
	 * @param connection				コネクション
	 * @param addInfo					更新する職種情報
	 * @throws DataAccessException		更新中に例外が発生した場合
	 * @throws NoDataFoundException		対象データが見つからない場合
	 */
	public void updateShokushuInfo(
		Connection connection,
		ShokushuInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
		//検索（対象データが存在しなかった場合は例外発生）
		selectShokushuInfo(connection, updateInfo);

		String query =
			"UPDATE MASTER_SHOKUSHU"
				+ " SET"	
				+ " SHOKUSHU_CD = ?"			//職種コード
				+ ",SHOKUSHU_NAME = ?"			//職名称
				+ ",SHOKUSHU_NAME_RYAKU = ?"	//職名(略称)
				+ ",BIKO = ?"					//備考
				+ " WHERE"
				+ " SHOKUSHU_CD = ?";

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuNameRyaku());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuName());	//職種コード
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("職種情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	
	/**
	 * 職種情報を削除する。(物理削除) 
	 * @param connection			コネクション
	 * @param addInfo				削除する職種主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteShokushuInfo(
		Connection connection,
		ShokushuPk deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//検索（対象データが存在しなかった場合は例外発生）
		selectShokushuInfo(connection, deleteInfo);

		String query =
			"DELETE FROM MASTER_SHOKUSHU"
				+ " WHERE"
				+ " SHOKUSHU_CD = ?";

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getShokushuCd());	//職種コード
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("職種情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

}
