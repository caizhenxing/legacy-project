/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/05
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
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.util.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 検索用データアクセスクラス。
 * 
 * ID RCSfile="$RCSfile: SelectUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class SelectUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(SelectUtil.class);

	//---------------------------------------------------------------------
	// Public Method
	//---------------------------------------------------------------------
	/**
	 * 検索条件とSQLに基づき、ページ情報を取得する。
	 * @param connection			コネクション
	 * @param searchInfo			検索情報
	 * @param query					SQL文
	 * @return						ページデータ
	 * 								対象データが見つからない場合は、Pageオブジェクトのリストが空となる。
	 * @throws DataAccessException	データベースアクセス中の例外
	 * @throws NoDataFoundException	
	 * @throws RecordCountOutOfBoundsException	
	 */
	public static Page selectPageInfo(Connection connection,SearchInfo searchInfo, final String query)
		throws DataAccessException , NoDataFoundException, RecordCountOutOfBoundsException {
		//検索用SQL作成オブジェクトを生成する。		
		PreparedStatementCreator dataCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
				return conn.prepareStatement(
					query.toString(),
					//--------------------------------------------------
					// 2004/12/08 takano 
					// TYPE_SCROLL_INSENSITIVEの場合、
					// カーソル移動の結果をメモリ上に展開してしまうため
					// 大量件数（1万件以上？）には向かない。
					// TYPE_FORWARD_ONLYに変更する。
					// この先の処理では順次移動しかしていないため問題無し。
					//--------------------------------------------------
					//ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			}
		};
		//件数取得用
		PreparedStatementCreator countCerater = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
				return conn.prepareStatement(
					"SELECT COUNT(*) AS COUNT FROM (" + query.toString() + ")");
			}
		};

// 20050629
		try{
			//検索を実行する。
			return new PageReading(searchInfo,countCerater,dataCreator).search(connection);
		}
		catch(NoDataFoundException e){
			//throw new NoDataFoundException("該当する情報が存在しませんでした。", new ErrorInfo("errors.4004"), e);}
			throw new NoDataFoundException("該当する情報が存在しませんでした。", new ErrorInfo("errors.5002"), e);}
		catch(DataAccessException e){
			throw new DataAccessException("データ検索中にDBエラーが発生しました。");}
		catch(RecordCountOutOfBoundsException e){
			//throw new RecordCountOutOfBoundsException("該当する情報が存在しませんでした。",new ErrorInfo("errors.4004"),e);}
			throw new RecordCountOutOfBoundsException("該当する情報が存在しませんでした。",new ErrorInfo("errors.maxcount"),e);}
		finally{
			//TODO:
		}
// Horikoshi
	}

	/**
	 * 検索条件とSQLに基づき、ページ情報を取得する。
	 * @param connection			コネクション
	 * @param searchInfo			検索情報
	 * @param param                PreparedStatementに渡す動的パラメータ
	 * @param query					SQL文
	 * @return						ページデータ
	 * 								対象データが見つからない場合は、Pageオブジェクトのリストが空となる。
	 * @throws DataAccessException	データベースアクセス中の例外
	 * @throws NoDataFoundException	
	 * @throws RecordCountOutOfBoundsException	
	 */
	public static Page selectPageInfo(Connection connection,SearchInfo searchInfo, final String query, final String[] param)
		throws DataAccessException , NoDataFoundException, RecordCountOutOfBoundsException {
		//検索用SQL作成オブジェクトを生成する。		
		PreparedStatementCreator dataCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
					PreparedStatement pre = conn.prepareStatement(
										query.toString(),
										//ResultSet.TYPE_SCROLL_INSENSITIVE,	//コメント理由は上のメソッド参照。
										ResultSet.TYPE_FORWARD_ONLY,
										ResultSet.CONCUR_READ_ONLY);
					for(int i=1; i<=param.length; i++){
						pre.setString(i, param[i-1]);
					}
				return pre;
			}
		};
		//件数取得用
		PreparedStatementCreator countCerater = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
					PreparedStatement pre = conn.prepareStatement(
						"SELECT COUNT(*) AS COUNT FROM (" + query.toString() + ")");
					for(int i=1; i<=param.length; i++){
						pre.setString(i, param[i-1]);
					}				
				return pre;
			}
		};
		//検索を実行する。
		return new PageReading(searchInfo,countCerater,dataCreator).search(connection);
	}

	/**
	 * 検索条件とSQLに基づき、データリストを取得する。
	 * @param searchInfo			検索情報
	 * @param query				SQL文
	 * @param param                PreparedStatementに渡す動的パラメータ
	 * @return						リスト
	 * @throws DataAccessException	データベースアクセス中の例外
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public static List select(Connection connection,final String query, final String[] param)
		throws DataAccessException , NoDataFoundException{
		//検索用SQL作成オブジェクトを生成する。		
		PreparedStatementCreator dataCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
					PreparedStatement pre = conn.prepareStatement(query.toString());
					for(int i=1; i<=param.length; i++){
						pre.setString(i, param[i-1]);
					}
				return pre;
			}
		};
		//検索を実行する。
		ListCallbackHandler callbackHandler = new ListCallbackHandler();
		new JDBCReading().query(connection,dataCreator,callbackHandler);
		return callbackHandler.getResult();
	}
	
	/**
	 * 検索条件とSQLに基づき、データリストを取得する。
	 * @param connection			コネクション
	 * @param searchInfo			検索情報
	 * @param query					SQL文
	 * @return						リスト
	 * @throws DataAccessException	データベースアクセス中の例外
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public static List select(Connection connection, final String query)
		throws DataAccessException , NoDataFoundException{
		//検索用SQL作成オブジェクトを生成する。		
		PreparedStatementCreator dataCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
				return conn.prepareStatement(
					query.toString());
			}
		};
		//検索を実行する。
		ListCallbackHandler callbackHandler = new ListCallbackHandler();
		new JDBCReading().query(connection,dataCreator,callbackHandler);
		return callbackHandler.getResult();
	}

	/**
	 * 検索条件とSQLに基づき、CSVデータリストを取得する。
	 * @param connection			コネクション
	 * @param query					SQL文
	 * @param includeHeader			ヘッダー情報を含める場合true以外false
	 * @return						リスト
	 * @throws DataAccessException	データベースアクセス中の例外
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public static List selectCsvList(Connection connection,final String query,boolean includeHeader)
		throws DataAccessException , NoDataFoundException{
		//検索用SQL作成オブジェクトを生成する。		
		PreparedStatementCreator dataCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
				return conn.prepareStatement(
					query.toString());
			}
		};
		//検索を実行する。
		CsvListCallbackHandler callbackHandler = new CsvListCallbackHandler();
		//ヘッダー情報を含むかどうか
		callbackHandler.setIncludeHeader(includeHeader);
		new JDBCReading().query(connection,dataCreator,callbackHandler);
		return callbackHandler.getResult();
	}

}
