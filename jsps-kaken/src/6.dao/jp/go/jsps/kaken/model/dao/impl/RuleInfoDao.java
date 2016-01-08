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
import jp.go.jsps.kaken.model.vo.RuleInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.RandomPwd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ＩＤパスワード発行ルール情報データアクセスクラス。
 * ID RCSfile="$RCSfile: RuleInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class RuleInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(RuleInfoDao.class);

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
	public RuleInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * パスワード発行ルール情報を取得する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						パスワード発行ルール情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public RuleInfo selectRuleInfo(
		Connection connection,
		RulePk primaryKeys)
		throws DataAccessException, NoDataFoundException {
		String query =
			"SELECT "
				+ " * "
				+ " FROM RULEINFO A"
				+ " WHERE TAISHO_ID = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			RuleInfo result = new RuleInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getTaishoId());
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setTaishoId(recordSet.getString("TAISHO_ID"));
				result.setMojisuChk(recordSet.getString("MOJISU_CHK"));
				result.setCharChk1(recordSet.getString("CHAR_CHK1"));
				result.setCharChk2(recordSet.getString("CHAR_CHK2"));
				result.setCharChk3(recordSet.getString("CHAR_CHK3"));
				result.setCharChk4(recordSet.getString("CHAR_CHK4"));
				result.setCharChk5(recordSet.getString("CHAR_CHK5"));
				result.setYukoDate(recordSet.getDate("YUKO_DATE"));
				result.setBiko(recordSet.getString("BIKO"));
				return result;
			} else {
				throw new NoDataFoundException(
					"パスワード発行ルールテーブルに該当するデータが見つかりません。検索キー：対象者ID'"
						+ primaryKeys
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("パスワード発行ルールテーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * パスワード発行ルール情報を登録する。
	 * @param connection			コネクション
	 * @param addInfo				登録するパスワード発行ルール情報
	 * @return						登録したパスワード発行ルール情報
	 * @throws DataAccessException	登録に例外が発生した場合。
	 */
	public void insertRuleInfo(Connection connection, RuleInfo addInfo)
		throws DataAccessException,DuplicateKeyException {
	}
	
	/**
	 * パスワード発行ルール情報を更新する。
	 * @param connection			コネクション
	 * @param updateInfo			更新するスワード発行ルール情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateRuleInfo(Connection connection, RuleInfo updateInfo)
		throws DataAccessException, NoDataFoundException {

			//検索
			selectRuleInfo(connection, updateInfo);

			String query =
				"UPDATE RULEINFO"
					+ " SET"
					+ " TAISHO_ID = ?"
					+ ",MOJISU_CHK = ?"
					+ ",CHAR_CHK1 = ?"
					+ ",CHAR_CHK2 = ?"
					+ ",CHAR_CHK3 = ?"
					+ ",CHAR_CHK4 = ?"
					+ ",CHAR_CHK5 = ?"
					+ ",YUKO_DATE = ?"
					+ ",BIKO = ?"
					+ " WHERE"
					+ " TAISHO_ID = ?";

			PreparedStatement preparedStatement = null;
			try {
				//登録
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTaishoId());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getMojisuChk());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getCharChk1());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getCharChk2());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getCharChk3());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getCharChk4());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getCharChk5());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getYukoDate());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTaishoId());
				DatabaseUtil.executeUpdate(preparedStatement);

			} catch (SQLException ex) {
				throw new DataAccessException("発行ルール設定中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}

	/**
	 * パスワード発行ルール情報を削除する。 
	 * @param connection			コネクション
	 * @param userInfo				実行するスワード発行ルール情報
	 * @param deleteInfo			削除する申請者情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合 
	 */
	public void deleteRuleInfo(Connection connection, RuleInfo deleteInfo)
		throws DataAccessException {
	}

	/**
	 * パスワード発行ルールに基づき、パスワードを作成する。
	 * @param connection			コネクション
	 * @param primaryKeys				主キー情報
	 * @return							申請者用パスワード
	 * @throws DataAccessException		データアクセス中に例外が発生した場合。
	 */
	public String getPassword(Connection connection, RulePk primaryKeys)
		throws NoDataFoundException, DataAccessException{
			boolean isUpperCase = true;	//true:大文字を含む
			boolean isLowerCase = true;	//true:小文字を含む
			boolean isDigit = true;		//true:数字を含む
			int length = 6;				//パスワードの長さ

			RuleInfo ruleInfo = new RuleInfo();
			ruleInfo = selectRuleInfo(connection, primaryKeys);
			if(ruleInfo.getCharChk1() != null && ruleInfo.getCharChk1().equals("1")) {
				isUpperCase = false;
			}
			if(ruleInfo.getCharChk2() != null && ruleInfo.getCharChk2().equals("1")) {
				isDigit = false;
			}
			length = Integer.parseInt(ruleInfo.getMojisuChk());
			if(length > 10 || length < 6){
				length = 6;
			}
			return RandomPwd.generate(isUpperCase, isLowerCase, isDigit, length);
	}

//	/**
//	 * パスワード発行ルールに基づき、申請者用パスワードを作成する。
//	 * @return							申請者用パスワード
//	 * @throws DataAccessException		データアクセス中に例外が発生した場合。
//	 */
//	public String getShinseiPassword()
//		throws NoDataFoundException, DataAccessException{
//		return "shinsei";
//	}
//	
//	/**
//	 * パスワード発行ルールに基づき、所属機関担当者用パスワードを作成する。
//	 * @return							所属機関担当者用パスワード
//	 * @throws DataAccessException		データアクセス中に例外が発生した場合。
//	 */
//	public String getShozokuPassword()
//		throws DataAccessException{
//		return "kikan";
//	}
//
//	/**
//	 * パスワード発行ルールに基づき、業務担当者用パスワードを作成する。
//	 * @return							業務担当者用パスワード
//	 * @throws DataAccessException		データアクセス中に例外が発生した場合。
//	 */
//	public String getGyomutantoPassword()
//		throws DataAccessException{
//		return "gyomutanto";
//	}
//
//	/**
//	 * パスワード発行ルールに基づき、:審査員用パスワードを作成する。
//	 * @return							所属機関担当者用パスワード
//	 * @throws DataAccessException		データアクセス中に例外が発生した場合。
//	 */
//	public String getShinsaPassword()
//		throws DataAccessException{
//		return "shinsa";
//	}
	
}
