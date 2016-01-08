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
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.KategoriInfo;
import jp.go.jsps.kaken.model.vo.KategoriPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * カテゴリマスタデータアクセスクラス。
 * ID RCSfile="$RCSfile: MasterKategoriInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class MasterKategoriInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(MasterKategoriInfoDao.class);

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
	public MasterKategoriInfoDao(UserInfo userInfo) {
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
	public static List selectKategoriList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUKA_KATEGORI"		//カテゴリ
			+ ",A.KATEGORI_NAME"		//カテゴリ名称
			+ ",A.BIKO"					//備考
			+ " FROM MASTER_KATEGORI A"
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
				"カテゴリ情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"カテゴリマスタに1件もデータがありません。",
				e);
		}
	}

	/**
	 * カテゴリ情報を取得する。
	 * @param connection				コネクション
	 * @param primaryKeys				主キー情報
	 * @return							カテゴリ情報
	 * @throws DataAccessException		データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public KategoriInfo selectKategoriInfo(
		Connection connection,
		KategoriPk primaryKeys)
		throws DataAccessException, NoDataFoundException {

		String query =
			"SELECT "
				+ " A.BUKA_KATEGORI"			//カテゴリ
				+ ",A.KATEGORI_NAME"			//カテゴリ名称
				+ ",A.BIKO"						//備考
				+ " FROM MASTER_KATEGORI A"
				+ " WHERE BUKA_KATEGORI = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			KategoriInfo result = new KategoriInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getBukaKategori());
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setBukaKategori(recordSet.getString("BUKA_KATEGORI"));
				result.setKategoriName(recordSet.getString("KATEGORI_NAME"));
				result.setBiko(recordSet.getString("BIKO"));
				return result;
			} else {
				throw new NoDataFoundException(
					"カテゴリ情報テーブルに該当するデータが見つかりません。検索キー：カテゴリコード'"
						+ primaryKeys.getBukaKategori()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("カテゴリ情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
}
