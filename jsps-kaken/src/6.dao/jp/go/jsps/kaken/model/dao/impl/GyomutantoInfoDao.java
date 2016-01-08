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

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 業務担当者情報データアクセスクラス。
 * ID RCSfile="$RCSfile: GyomutantoInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class GyomutantoInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** 業務担当者情報管理シーケンス名 */
	public static final String SEQ_GYOMUTANTOINFO = "SEQ_GYOMUTANTOINFO";

	/** ログ */
	protected static final Log log = LogFactory.getLog(GyomutantoInfoDao.class);

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
	public GyomutantoInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * キーに一致する業務担当者情報を取得する。
	 * @param connection			コネクション
	 * @param pkInfo				主キー情報
	 * @return						業務担当者情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public GyomutantoInfo selectGyomutantoInfo(
		Connection connection,
		GyomutantoPk pkInfo)
		throws DataAccessException, NoDataFoundException {
			String query =
				"SELECT "
					+ " GYOMUTANTO_ID"
					+ ",ADMIN_FLG"
					+ ",PASSWORD"
					+ ",NAME_KANJI_SEI"
					+ ",NAME_KANJI_MEI"
					+ ",NAME_KANA_SEI"
					+ ",NAME_KANA_MEI"
					+ ",BUKA_NAME"
					+ ",KAKARI_NAME"
					+ ",BIKO"
					+ ",YUKO_DATE"
					+ ",DEL_FLG"
					+ " FROM GYOMUTANTOINFO"
					+ " WHERE GYOMUTANTO_ID = ?";
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			try {
				GyomutantoInfo result = new GyomutantoInfo();
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				preparedStatement.setString(i++, pkInfo.getGyomutantoId());
				recordSet = preparedStatement.executeQuery();
				if (recordSet.next()) {
					result.setGyomutantoId(recordSet.getString("GYOMUTANTO_ID"));
					result.setAdminFlg(recordSet.getString("ADMIN_FLG"));
					result.setPassword(recordSet.getString("PASSWORD"));
					result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));
					result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));
					result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
					result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
					result.setBukaName(recordSet.getString("BUKA_NAME"));
					result.setKakariName(recordSet.getString("KAKARI_NAME"));
					result.setBiko(recordSet.getString("BIKO"));
					result.setDelFlg(recordSet.getString("DEL_FLG"));
					result.setYukoDate(recordSet.getDate("YUKO_DATE"));
					return result;
				} else {
					throw new NoDataFoundException(
						"業務担当者情報テーブルに該当するデータが見つかりません。検索キー：業務担当者ID'"
							+ pkInfo.getGyomutantoId()
							+ "'");
				}
			} catch (SQLException ex) {
				throw new DataAccessException("業務担当者情報テーブル検索実行中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}

	/**
	 * 業務担当者情報の数を取得する。削除フラグが「0」の場合のみ検索する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						業務担当者情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 */
	public int countGyomutantoInfo(
		Connection connection,
		String gyomutantoId)
		throws DataAccessException {
		String query =
			"SELECT COUNT(*)"
				+ " FROM GYOMUTANTOINFO"
				+ " WHERE GYOMUTANTO_ID = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			GyomutantoInfo result = new GyomutantoInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, gyomutantoId);
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("業務担当者情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * 業務担当者情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する業務担当者情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertGyomutantoInfo(
		Connection connection,
		GyomutantoInfo addInfo)
		throws DataAccessException, DuplicateKeyException {

			//重複チェック
			try {
				selectGyomutantoInfo(connection, addInfo);
				//NG
				throw new DuplicateKeyException(
					"'" + addInfo + "'は既に登録されています。");
			} catch (NoDataFoundException e) {
				//OK
			}
		
			String query =
				"INSERT INTO GYOMUTANTOINFO "
					+ "(GYOMUTANTO_ID"
					+ ",ADMIN_FLG"
					+ ",PASSWORD"
					+ ",NAME_KANJI_SEI"
					+ ",NAME_KANJI_MEI"
					+ ",NAME_KANA_SEI"
					+ ",NAME_KANA_MEI"
					+ ",BUKA_NAME"
					+ ",KAKARI_NAME"
					+ ",BIKO"
					+ ",YUKO_DATE"
					+ ",DEL_FLG) "
					+ "VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = null;
			try {
				//登録
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getGyomutantoId());
				DatabaseUtil.setParameter(preparedStatement, i++, 0);
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getPassword());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiSei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiMei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaSei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaMei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukaName());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKakariName());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYukoDate());
				DatabaseUtil.setParameter(preparedStatement, i++, 0);
				DatabaseUtil.executeUpdate(preparedStatement);
			} catch (SQLException ex) {
				throw new DataAccessException("業務担当者情報登録中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}

	/**
	 * 業務担当者情報を更新する。
	 * @param connection				コネクション
	 * @param updateInfo				更新する業務担当者情報
	 * @throws DataAccessException		更新中に例外が発生した場合
	 * @throws NoDataFoundException		対象データが見つからない場合
	 */
	public void updateGyomutantoInfo(
		Connection connection,
		GyomutantoInfo updateInfo)
		throws DataAccessException, NoDataFoundException {
			//検索
			selectGyomutantoInfo(connection, updateInfo);
	
			String query =
				"UPDATE GYOMUTANTOINFO"
					+ " SET"
					+ " ADMIN_FLG = ?"
					+ ",PASSWORD = ?"
					+ ",NAME_KANJI_SEI = ?"
					+ ",NAME_KANJI_MEI = ?"
					+ ",NAME_KANA_SEI = ?"
					+ ",NAME_KANA_MEI = ?"
					+ ",BUKA_NAME = ?"
					+ ",KAKARI_NAME = ?"
					+ ",BIKO = ?"
					+ ",YUKO_DATE = ?"
					+ ",DEL_FLG = ?"
					+ " WHERE"
					+ " GYOMUTANTO_ID = ?";

			PreparedStatement preparedStatement = null;
			try {
				//登録
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getAdminFlg());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getPassword());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukaName());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKakariName());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getYukoDate());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDelFlg());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getGyomutantoId());
				DatabaseUtil.executeUpdate(preparedStatement);

			} catch (SQLException ex) {
				throw new DataAccessException("業務担当者情報更新中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}

	/**
	 * 業務担当者情報を削除する。(削除フラグ) 
	 * @param connection			コネクション
	 * @param pkInfo				削除する業務担当者情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteFlgGyomutantoInfo(
		Connection connection,
		GyomutantoPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
			//検索
			selectGyomutantoInfo(connection, deleteInfo);
	
			String query =
				"UPDATE GYOMUTANTOINFO"
					+ " SET"
					+ " DEL_FLG = 1"
					+ " WHERE"
					+ " GYOMUTANTO_ID = ?";

			PreparedStatement preparedStatement = null;
			try {
				//登録
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getGyomutantoId());
				DatabaseUtil.executeUpdate(preparedStatement);

			} catch (SQLException ex) {
				throw new DataAccessException("業務担当者情報削除中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}

	/**
	 * 業務担当者情報を削除する。(物理削除)
	 * @param connection			コネクション
	 * @param pkInfo				削除する業務担当者情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteGyomutantoInfo(
		Connection connection,
		GyomutantoPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
			//TODO 未実装の状態
	}

	/**
	 * ユーザID、パスワードの認証を行う。
	 * @param connection			コネクション
	 * @param userid				ユーザID
	 * @param password				パスワード
	 * @return						認証に成功した場合 true 以外 false
	 * @throws DataAccessException	データベースアクセス中の例外
	 */
	public boolean authenticateGyomutantoInfo(
		Connection connection,
		String userid,
		String password)
		throws DataAccessException {
			String query =
				"SELECT count(*)"
					+ " FROM GYOMUTANTOINFO"
					+ " WHERE DEL_FLG = 0"
					+ " AND ADMIN_FLG = 0"
					+ " AND GYOMUTANTO_ID = ?"
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
				if (count > 0) {
					return true;
				} else {
					return false;
				}
			} catch (SQLException ex) {
				throw new DataAccessException("業務担当者情報テーブル検索実行中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}

	/**
	 * ユーザID、パスワードの認証を行う。（システム管理者用）
	 * @param connection			コネクション
	 * @param userid				ユーザID
	 * @param password				パスワード
	 * @return						認証に成功した場合 true 以外 false
	 * @throws DataAccessException	データベースアクセス中の例外
	 */
	public boolean authenticateSystemKanriInfo(
		Connection connection,
		String userid,
		String password)
		throws DataAccessException {
			String query =
				"SELECT count(*)"
					+ " FROM GYOMUTANTOINFO"
					+ " WHERE DEL_FLG = 0"
					+ " AND ADMIN_FLG = 1"
					+ " AND GYOMUTANTO_ID = ?"
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
				if (count > 0) {
					return true;
				} else {
					return false;
				}
			} catch (SQLException ex) {
				throw new DataAccessException("業務担当者情報テーブル検索実行中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}
	
	/**
	 * パスワードの変更する。 
	 * @param connection			コネクション
	 * @param pkInfo				主キー情報
	 * @param newPassword			新しいパスワード
	 * @return              		パスワードの変更に成功した場合 true 以外 false
	 * @throws DataAccessException	変更中に例外が発生した場合
	 */
	public boolean changePasswordGyomutantoInfo(
		Connection connection,
		GyomutantoPk pkInfo,
		String newPassword)
		throws DataAccessException {

			String query = "UPDATE GYOMUTANTOINFO "
						 + "SET "
						 + "PASSWORD = ? "
						 + "WHERE "
						 + "GYOMUTANTO_ID = ? "
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
				DatabaseUtil.setParameter(ps,i++, pkInfo.getGyomutantoId());	//業務担当者ID
				DatabaseUtil.executeUpdate(ps);
			} catch (SQLException ex) {
				throw new DataAccessException("パスワード変更中に例外が発生しました。 ", ex);

			} finally {
				DatabaseUtil.closeResource(null, ps);
			}
			return true;
	}

}
