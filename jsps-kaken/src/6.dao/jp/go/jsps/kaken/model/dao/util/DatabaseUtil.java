/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.util;

import java.io.StringReader;
import java.sql.*;
import java.util.Date;

import javax.sql.*;

import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.util.*;
import oracle.jdbc.pool.*;

import org.apache.commons.logging.*;

/**
 * データベース関連のユーティリティクラス。
 * 
 * ID RCSfile="$RCSfile: DatabaseUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:08:01 $"
 * 
 */
public class DatabaseUtil {

	/**  ログ  */
	private static Log log = LogFactory.getLog(DatabaseUtil.class);

	/**
	 * 指定されたパラメータに文字列をセットする。
	 * @param statement		パラメータをセットするステートメント
	 * @param i				最初のパラメータは 1、2 番目のパラメータは 2、などとする
	 * @param value			パラメータ値
	 * @throws SQLException	データベースアクセスエラーが発生した場合
	 */
	public static void setParameter(
		PreparedStatement statement,
		int i,
		String value)
		throws SQLException {

		if (value == null) {
			statement.setString(i, null);
		//2005.11.09 iso 666文字を超えてた場合は、setCharacterStreamを使う
		} else if(value.length() > 666) {
			setParameter666Over(statement, i,value);
		} else {
			value = value.trim().replaceAll("[\r\n]", "");	//空白、改行コードを削除する
			statement.setString(i, value);
		}
	}

	//2005.11.09 iso 666文字超えに対応
	/**
	 * 指定されたパラメータに文字列をセットする。
	 * 666文字を超えてる場合に対応。
	 * @param statement		パラメータをセットするステートメント
	 * @param i				最初のパラメータは 1、2 番目のパラメータは 2、などとする
	 * @param value			パラメータ値
	 * @throws SQLException	データベースアクセスエラーが発生した場合
	 */
	public static void setParameter666Over(
			PreparedStatement statement,
			int i,
			String value)
			throws SQLException {

		if (value == null) {
			statement.setString(i, null);
		} else {
			value = value.trim().replaceAll("[\r\n]", "");	//空白、改行コードを削除する
			StringReader sReader = new StringReader(value);
			statement.setCharacterStream(i, sReader, value.length());
		}
	}

	/**
	 * 指定されたパラメータにint値をセットする。
	 * @param statement		パラメータをセットするステートメント
	 * @param i				最初のパラメータは 1、2 番目のパラメータは 2、などとする
	 * @param value			パラメータ値
	 * @throws SQLException	データベースアクセスエラーが発生した場合
	 */
	public static void setParameter(
		PreparedStatement statement,
		int i,
		int value)
		throws SQLException {
		statement.setInt(i, value);
	}

	/**
	 * 指定されたパラメータにint値をセットする。
	 * @param statement		パラメータをセットするステートメント
	 * @param i				最初のパラメータは 1、2 番目のパラメータは 2、などとする
	 * @param value			パラメータ値
	 * @throws SQLException	データベースアクセスエラーが発生した場合
	 */
	public static void setParameter(
		PreparedStatement statement,
		int i,
		Integer value)
		throws SQLException {
		statement.setInt(i, value.intValue());
	}

	/**
	 * 指定されたパラメータにlong値をセットする。
	 * @param statement		パラメータをセットするステートメント
	 * @param i				最初のパラメータは 1、2 番目のパラメータは 2、などとする
	 * @param value			パラメータ値
	 * @throws SQLException	データベースアクセスエラーが発生した場合
	 */
	public static void setParameter(
		PreparedStatement statement,
		int i,
		Long value)
		throws SQLException {
		statement.setLong(i, value.longValue());
	}

	/**
	 * 指定されたパラメータに日付値をセットする。
	 * @param statement		パラメータをセットするステートメント
	 * @param i				最初のパラメータは 1、2 番目のパラメータは 2、などとする
	 * @param value			パラメータ値
	 * @throws SQLException	データベースアクセスエラーが発生した場合
	 */
	public static void setParameter(
		PreparedStatement statement,
		int i,
		Date value)
		throws SQLException {

//JDBCドライバによっては、きちんとDate型を渡さないとエラーとなる。(Oracle→OK, Weblogic→NG）
//			if (value == null) {
//				statement.setString(i, null);
//			} else {
//				statement.setString(
//					i,
//					new SimpleDateFormat("yyyy/MM/dd").format(value));
//			}
			if(value == null){
				statement.setDate(i,null);
			}else{
				Date utilDate = new DateUtil(value).getDateYYYYMMDD();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				statement.setDate(i,sqlDate);
			}
	}

	/**
	 * データベースコネクションをクローズします 
	 * 
	 * @param connection データベースコネクション
	 */
	public static void closeConnection(Connection connection) {
		try {
			if (connection == null) {
				return;
			}
			connection.close();
		} catch (SQLException e) {
			log.error("コネクションのクローズに失敗しました。", e);
			throw new SystemException("コネクションのクローズに失敗しました。", e);
		}
	}

	/**
	 * 資源をクローズする。
	 *
	 * @param preparedStatement DMLオブジェクト
	 * @param resultSet 結果オブジェクト
	 */
	public static void closeResource(
		ResultSet resultSet,
		PreparedStatement preparedStatement)
	{
		Exception ex = null;
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			log.error("結果オブジェクトのクローズに失敗しました。", e);
			ex = e;
			//throw new SystemException("結果オブジェクトのクローズに失敗しました。", e);
		}
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			log.error("ステートメントのクローズに失敗しました。", e);
			ex = e;
			//throw new SystemException("ステートメントのクローズに失敗しました。", e);
		}
		
		if(ex != null){
			String msg = "結果オブジェクトまたはステートメントのクローズに失敗しました。";
			throw new SystemException(msg, ex);
		}
		
	}

	/**
	 * コネクションを取得する。
	 * @return	コネクション。
	 */
	public static Connection getConnection() throws SystemBusyException {
		try {
			DataSource dataSource = DataSourceFactory.getDataSouce();
			if(log.isDebugEnabled()){
				if(dataSource instanceof OracleConnectionCacheImpl){
					log.debug("Active size : " + ((OracleConnectionCacheImpl)dataSource).getActiveSize());
					log.debug("Cache Size is " + ((OracleConnectionCacheImpl)dataSource).getCacheSize());
				}	
			}
			Connection connection = null;
			try{
				connection = dataSource.getConnection();
				if(connection == null){
					throw new SystemBusyException("コネクションの取得に失敗しました。");
				}
			}catch(SQLException e){
				//weblogicの場合はコネクション数を超えたときに例外が発生する
				throw new SystemBusyException("コネクションの取得に失敗しました。", e);
			}
			checkForWarning(connection.getWarnings());
			connection.setAutoCommit(false);
			return connection;
		} catch (SQLException e) {
			log.error("コネクションの取得に失敗しました。", e);
			throw new SystemException("コネクションの取得に失敗しました。", e);
		}
	}
	
	/**
	 * コネクションの状況を取得する。
	 * @throws SystemBusyException
	 */
	public static void checkStatus() throws SystemBusyException {
		DataSource dataSource = DataSourceFactory.getDataSouce();
		if(log.isDebugEnabled()){
			log.debug("Active size : " + ((OracleConnectionCacheImpl)dataSource).getActiveSize());
			log.debug("Cache Size is " + ((OracleConnectionCacheImpl)dataSource).getCacheSize());
		}
	}	

	/**
	 * コミットする。
	 * @param connection				コミット対象のコネクション
	 * @throws TransactionException		コミットに失敗した場合。
	 */
	public static void commit(Connection connection)
		throws TransactionException {
		try {
			if (connection != null) {
				//コミット
				connection.commit();
			}
		} catch (SQLException ex) {
			log.error("コミットに失敗しました。", ex);
			throw new TransactionException("コミットに失敗しました。", ex);
		}
	}
	

	/**
	 * ロールバックする。
	 * @param connection				ロールバック対象のコネクション
	 * @throws TransactionException		ロールバックに失敗した場合。
	 */
	public static void rollback(Connection connection)
		throws TransactionException {
		try {
			if (connection != null) {
				connection.rollback();
			}
		} catch (SQLException ex) {
			log.error("ロールバックに失敗しました。", ex);
			throw new TransactionException("ロールバックに失敗しました。", ex);
		}
	}

	// Format and print any warnings from the connection
	private static void checkForWarning(SQLWarning warn) throws SQLException {
		// If a SQLWarning object was given, display the
		// warning messages.  Note that there could be
		// multiple warnings chained together

		if (warn != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("*** Warning ***\n");
			while (warn != null) {
				buffer.append("SQLState: " + warn.getSQLState());
				buffer.append("Message:  " + warn.getMessage());
				buffer.append("Vendor:   " + warn.getErrorCode());
				buffer.append("");
				warn = warn.getNextWarning();
			}
			log.info(buffer);
		}
	}
	
	/**
	 * Updateを実行する。更新件数をチェックする。
	 * @param statement
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	public static void executeUpdate(PreparedStatement statement)
		throws SQLException, DataAccessException {
		int resultCount = statement.executeUpdate();
		if (resultCount != 1) {
			if (log.isErrorEnabled()) {
				log.error("更新件数が不正です。更新件数'" + resultCount + "'件。");
			}
			throw new DataAccessException(
				"更新に失敗しました。更新件数'" + resultCount + "'件。");
		}
	}
	
}
