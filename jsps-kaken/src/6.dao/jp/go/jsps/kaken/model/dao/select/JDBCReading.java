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
package jp.go.jsps.kaken.model.dao.select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *	データベース操作のテンプレートクラス。
 *
 * ID RCSfile="$RCSfile: JDBCReading.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 * 
 */
public class JDBCReading {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** ログ */
	private static Log log = LogFactory.getLog(JDBCReading.class);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public JDBCReading() {
		super();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * 検索処理を実行する。
	 * @param  connection			コネクション
	 * @param  psc					プリコンパイルされたSQL文。
	 * @param  rcbh		処理ハンドラ。
	 * @throws DataAccessException	データベース関連の例外。
	 *         NoDataFoundException　対象データが見つからない場合。  			
	 */
	public void query(Connection connection,PreparedStatementCreator psc, RowCallbackHandler rcbh)
		throws DataAccessException,NoDataFoundException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = psc.createPreparedStatement(connection);
			rs = pstmt.executeQuery();
			//データ取得チェックフラグ
			boolean isDataFound = false;
			while (rs.next()) {
				isDataFound = true;
				rcbh.processRow(rs);
			}
			//データ取得チェック
			if (isDataFound == false) {
				throw new NoDataFoundException("対象データが見つかりません。");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("検索処理に失敗しました。", ex);
		} finally {
			DatabaseUtil.closeResource(rs,pstmt);
		}
	}

	/**
	 * 検索処理を実行する。
	 * @param psc					プリコンパイルされたSQL文。
	 * @param rcbh					処理ハンドラ。
	 * @param startPosition			取得開始行番号
	 * @param howManyRows			取得件数(0のときはすべてのデータを取得します)			
	 * @throws DataAccessException	DB関連のエラーが発生した場合。
	 *         NoDataFoundException　対象データが見つからない場合。  			
	 */
	public void query(
		Connection connection,
		PreparedStatementCreator psc,
		RowCallbackHandler rcbh,
		int startPosition,
		int howManyRows)
		throws DataAccessException, NoDataFoundException {

		//check
		if (startPosition < 0)
			throw new IllegalArgumentException("startPosition < 0");

		//ページサイズが0のときはすべてのデータを表示する。
		if (howManyRows <= 0) {
			this.query(connection,psc, rcbh);
			return;
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = psc.createPreparedStatement(connection);
			rs = pstmt.executeQuery();

			//指定位置にカーソル移動
			setStartPosition(startPosition + 1, rs);
			do{
				rcbh.processRow(rs);
			}while (rs.next() && (--howManyRows > 0));
		} catch (SQLException ex) {
			throw new DataAccessException("検索処理に失敗しました。", ex);
		} finally {
			DatabaseUtil.closeResource(rs,pstmt);
		}
	}
	
	/**
	 * 指定位置にカーソルを移動する。
	 * @param startAtRow		
	 * @param resultSet
	 * @throws SQLException
	 */
	private void setStartPosition(
			int startAtRow, ResultSet resultSet) 
			throws SQLException,NoDataFoundException{
		if (startAtRow > 0) {
			if (resultSet.getType() !=
					ResultSet.TYPE_FORWARD_ONLY) {
				// Move the cursor using JDBC 2.0 API
				if (!resultSet.absolute(startAtRow)) {
					throw new NoDataFoundException("指定位置にカーソルを移動できません。");
				}
			} else {
				//ループして移動する。
				for (int i=0; i< startAtRow; i++) {
					if (!resultSet.next()) {
						throw new NoDataFoundException("指定位置にカーソルを移動できません。");
					}
				}
			}
		}
	}
}
