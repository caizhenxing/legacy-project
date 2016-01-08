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
package jp.go.jsps.kaken.model.dao.select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * シーケンス情報データアクセスクラス。
 * ID RCSfile="$RCSfile: SequenceUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class SequenceUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(SequenceUtil.class);

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
	public SequenceUtil(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * シーケンスを取得する。
	 * @param SequenceName			取得するシーケンス名
	 * @param figer 				桁数
	 * @return						シーケンス値
	 * @throws DataAccessException	データベースアクセス中の例外
	 */
	public String select(Connection connection ,String sequenceName, int figure)
		throws DataAccessException, NoDataFoundException {
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			StringBuffer keta = new StringBuffer("FM");
			for(int i=0; i<figure; i++){
				keta.append("0");
			}
			preparedStatement =
				connection.prepareStatement(
					"SELECT TO_CHAR(" + sequenceName + ".NEXTVAL,'" + keta.toString() + "') SEQ FROM DUAL");				

			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				return recordSet.getString("SEQ");
			} else {
				throw new NoDataFoundException(
					"シーケンスの取得に失敗しました。シーケンス名'"
						+ sequenceName
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("シーケンスの取得中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
}
