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

import java.sql.*;
import java.util.*;

import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * キーワードマスタデータアクセスクラス。
 */
public class MasterKeywordInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(MasterKeywordInfoDao.class);

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
	public MasterKeywordInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * キーワード情報の一覧を取得する。
	 * @param	connection			コネクション
	 * @return						キーワード情報
	 * @throws ApplicationException
	 */
	public static List selectKeywordInfoList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUNKASAIMOKU_CD"		//分科細目コード
			+ ",A.SAIMOKU_NAME"			//細目名
			+ ",A.BUNKA_CD"				//分科コード
			+ ",A.BUNKA_NAME"			//分科名
			+ ",A.BUNYA_CD"				//分野コード
			+ ",A.BUNYA_NAME"			//分野名
			+ ",A.KEI"					//系
			+ ",A.KEYWORD_CD"			//記号
			+ ",A.KEYWORD"				//キーワード
			+ " FROM MASTER_KEYWORD A"
			+ " ORDER BY BUNKASAIMOKU_CD";								
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
				"キーワード情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"キーワード情報マスタに1件もデータがありません。",	e);
		}
	}

	//TODO コード表
	/**
	 * キーワード情報の一覧を取得する。
	 * @param	connection			コネクション
	 * @param	bunkaCd				分科コード
	 * @return						キーワード情報
	 * @throws ApplicationException
	 */
	public static List selectKeywordInfoList(Connection connection, String bunkaCd)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		StringBuffer sbSql = new StringBuffer(512);

		sbSql.append("SELECT B.BUNKA_CD, B.BUNKA_CNT,\n");
		sbSql.append("       C.BUNKASAIMOKU_CD, C.SAIMOKU_NAME, TO_CHAR(C.SAIMOKU_CNT) SAIMOKU_CNT, \n");
		sbSql.append("       D.BUNKATSU_NO, TO_CHAR(D.BUNKATSU_CNT) BUNKATSU_CNT, \n");
		sbSql.append("       E.KEYWORD_CD, E.KEYWORD\n");
		sbSql.append("  FROM \n");
		sbSql.append("      (SELECT BUNKA_CD, COUNT(*) BUNKA_CNT\n");
		sbSql.append("         FROM MASTER_KEYWORD\n");
		sbSql.append("        WHERE BUNKA_CD = ?\n");
		sbSql.append("        GROUP BY BUNKA_CD) B,\n");
		sbSql.append("      (SELECT BUNKA_CD, BUNKASAIMOKU_CD, \n");
		sbSql.append("              MIN(SAIMOKU_NAME) SAIMOKU_NAME, COUNT(*) SAIMOKU_CNT\n");
		sbSql.append("         FROM MASTER_KEYWORD\n");
		sbSql.append("        WHERE BUNKA_CD = ?\n");
		sbSql.append("        GROUP BY BUNKA_CD, BUNKASAIMOKU_CD) C,\n");
		sbSql.append("      (SELECT BUNKA_CD, BUNKASAIMOKU_CD, BUNKATSU_NO, COUNT(*) BUNKATSU_CNT\n");
		sbSql.append("         FROM MASTER_KEYWORD\n");
		sbSql.append("        WHERE BUNKA_CD = ?\n");
		sbSql.append("        GROUP BY BUNKA_CD, BUNKASAIMOKU_CD, BUNKATSU_NO) D,\n");
		sbSql.append("      (SELECT BUNKA_CD, BUNKASAIMOKU_CD, BUNKATSU_NO,\n");
		sbSql.append("              KEYWORD_CD, MIN(KEYWORD) KEYWORD\n");
		sbSql.append("         FROM MASTER_KEYWORD\n");
		sbSql.append("        WHERE BUNKA_CD = ?\n");
		sbSql.append("        GROUP BY BUNKA_CD, BUNKASAIMOKU_CD, BUNKATSU_NO, KEYWORD_CD) E\n");
		sbSql.append(" WHERE B.BUNKA_CD = C.BUNKA_CD\n");
		sbSql.append("   AND C.BUNKA_CD = D.BUNKA_CD\n");
		sbSql.append("   AND C.BUNKASAIMOKU_CD = D.BUNKASAIMOKU_CD\n");
		sbSql.append("   AND D.BUNKA_CD = E.BUNKA_CD\n");
		sbSql.append("   AND D.BUNKASAIMOKU_CD = E.BUNKASAIMOKU_CD\n");
		sbSql.append("   AND D.BUNKATSU_NO = E.BUNKATSU_NO\n");
		sbSql.append(" ORDER BY BUNKA_CD, BUNKASAIMOKU_CD, BUNKATSU_NO, KEYWORD_CD\n");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + sbSql);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,sbSql.toString(),new String[]{bunkaCd,bunkaCd,bunkaCd,bunkaCd});
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"キーワード情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"キーワード情報マスタに1件もデータがありません。",
				e);
		}
	}


	//TODO コード表
	/**
	 * 分科名の一覧を取得する。
	 * @param	connection			コネクション
	 * @return	bunyaCd				分野コード
	 * @throws ApplicationException
	 */
	public static List selectBunkaNameList(Connection connection, String bunyaCd)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUNKA_CD,"			//分科コード
			+ " A.BUNKA_NAME"			//分科名
			+ " FROM MASTER_KEYWORD A"
			+ " WHERE A.BUNYA_CD = ?"	//分野コード
			+ " GROUP BY A.BUNKA_CD,BUNKA_NAME,SUBSTR(BUNKASAIMOKU_CD,0,2)"
			+ " ORDER BY SUBSTR(BUNKASAIMOKU_CD,0,2)";			//分科細目コードの先頭から2番目の昇順							
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString(),new String[]{bunyaCd});
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"キーワード情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"キーワード情報マスタに1件もデータがありません。",
				e);
		}
	}

	
	//TODO コード表
	/**
	 * コード一覧作成用メソッド。<br>
	 * 分野コードと分野名の一覧を取得する。
	 * 分野コード順でソートする。
	 * @param	connection			コネクション
	 * @return	
	 * @throws ApplicationException
	 */
	public static List selectBunyaNameList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUNYA_CD"			//分野コード
			+ ",A.BUNYA_NAME"		//分野名
			+ " FROM MASTER_KEYWORD A"
			+ " GROUP BY BUNYA_CD,BUNYA_NAME"
			+ " ORDER BY BUNYA_CD";	//分野コード
			
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
				"キーワード情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"キーワード情報マスタに1件もデータがありません。",
				e);
		}
	}
	
	
	
	/**
	 * キーワード情報を取得する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						キーワード
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public KeywordInfo selectKeywordInfo(
		Connection connection,
		KeywordPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		StringBuffer sbSql = new StringBuffer(512);

		sbSql.append("SELECT")
			 .append(" BUNKASAIMOKU_CD")	//分科細目コード
			 .append(",SAIMOKU_NAME")		//細目名
			 .append(",BUNKA_CD")			//分科コード
			 .append(",BUNKA_NAME")			//分科名
			 .append(",BUNYA_CD")			//分野コード
			 .append(",BUNYA_NAME")			//分野名
			 .append(",KEI")				//系
			 .append(",BUNKATSU_NO")		//分割番号
			 .append(",KEYWORD_CD")			//記号
			 .append(",KEYWORD")			//キーワード
			 .append(" FROM MASTER_KEYWORD")
			 .append(" WHERE BUNKASAIMOKU_CD = ?")
			 .append(" AND BUNKATSU_NO = ?")
			 .append(" AND KEYWORD_CD = ?")
			 ;

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			KeywordInfo result = new KeywordInfo();
			preparedStatement = connection.prepareStatement(sbSql.toString());
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getBunkaSaimokuCd());
			preparedStatement.setString(i++, primaryKeys.getBunkatsuNo());
			preparedStatement.setString(i++, primaryKeys.getKeywordCd());
			
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
				result.setSaimokuName(recordSet.getString("SAIMOKU_NAME"));
				result.setBunkaCd(recordSet.getString("BUNKA_CD"));
				result.setBunkaName(recordSet.getString("BUNKA_NAME"));
				result.setBunyaCd(recordSet.getString("BUNYA_CD"));
				result.setBunyaName(recordSet.getString("BUNYA_NAME"));
				result.setKei(recordSet.getString("KEI"));
				result.setBunkatsuNo(recordSet.getString("BUNKATSU_NO"));
				result.setKeywordCd(recordSet.getString("KEYWORD_CD"));
				result.setKeyword(recordSet.getString("KEYWORD"));
				
			} else {
				throw new NoDataFoundException(
					"キーワード情報テーブルに該当するデータが見つかりません。検索キー：分科細目CD'"
						+ primaryKeys.getBunkaSaimokuCd()
						+ "' 分割番号'"
						+ primaryKeys.getBunkatsuNo()
						+ "'");
			}
			
			return result;
		} catch (SQLException ex) {
			throw new DataAccessException("キーワード情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}




	/**
	 * キーワード情報を取得する。
	 * 細目番号、分割番号、キーワードコードをそれぞれ条件指定する。
	 * 上記引数がnullまたは空文字だった場合は、検索条件から除外する。
	 * @param connection			コネクション
	 * @param saimokuCd			細目番号
	 * @param bunkatsuCd			分割番号
	 * @param keywordCd			キーワードコード
	 * @return						キーワード情報リスト
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public KeywordInfo[] selectKeywordInfo(
		Connection connection,
		String saimokuCd,
		String bunkatsuCd,
		String keywordCd)
		throws DataAccessException, NoDataFoundException
	{
		StringBuffer sbSql = new StringBuffer(512);

		sbSql.append("SELECT")
			 .append(" BUNKASAIMOKU_CD")	//分科細目コード
			 .append(",SAIMOKU_NAME")		//細目名
			 .append(",BUNKA_CD")			//分科コード
			 .append(",BUNKA_NAME")			//分科名
			 .append(",BUNYA_CD")			//分野コード
			 .append(",BUNYA_NAME")			//分野名
			 .append(",KEI")				//系
			 .append(",BUNKATSU_NO")		//分割番号
			 .append(",KEYWORD_CD")			//記号
			 .append(",KEYWORD")			//キーワード
			 .append(" FROM MASTER_KEYWORD")
			 .append(" WHERE ")
			 .append("  1=1")				//ダミー条件
			 ;
			 
		
		//細目番号が条件として指定されていた場合
		if(!StringUtil.isBlank(saimokuCd)){ 
			sbSql.append(" AND BUNKASAIMOKU_CD = '"+EscapeUtil.toSqlString(saimokuCd)+"' ");
		}
		//分割番号が条件として指定されていた場合
		if(!StringUtil.isBlank(bunkatsuCd)){
			sbSql.append(" AND BUNKATSU_NO = '"+EscapeUtil.toSqlString(bunkatsuCd)+"' ");
		}
		//2005/8/30 修正
		else{
////<!-- UPDATE　START 2007/07/24 BIS 張楠 -->      
//			分割番号（BUNKATSU_NO）が「1」「2」「3」「4」「5」の場合     			
			//sbSql.append(" AND BUNKATSU_NO IN ('-','1','2')");
			sbSql.append(" AND BUNKATSU_NO IN ('-','1','2','3','4','5')");
////<!-- UPDATE　START 2007/07/24 BIS 張楠 -->
		}
		//キーワードコードが条件として指定されていた場合
		if(!StringUtil.isBlank(keywordCd)){
			sbSql.append(" AND KEYWORD_CD = '"+EscapeUtil.toSqlString(keywordCd)+"' ");
		}
		
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(sbSql.toString());
			int i = 1;
			recordSet = preparedStatement.executeQuery();
			
			List resultArray = new ArrayList();
			while(recordSet.next()) {
				KeywordInfo result = new KeywordInfo();
				result.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
				result.setSaimokuName(recordSet.getString("SAIMOKU_NAME"));
				result.setBunkaCd(recordSet.getString("BUNKA_CD"));
				result.setBunkaName(recordSet.getString("BUNKA_NAME"));
				result.setBunyaCd(recordSet.getString("BUNYA_CD"));
				result.setBunyaName(recordSet.getString("BUNYA_NAME"));
				result.setKei(recordSet.getString("KEI"));
				result.setBunkatsuNo(recordSet.getString("BUNKATSU_NO"));
				result.setKeywordCd(recordSet.getString("KEYWORD_CD"));
				result.setKeyword(recordSet.getString("KEYWORD"));
				resultArray.add(result);
			}
			
			if(resultArray.isEmpty()){
				throw new NoDataFoundException(
					"キーワード情報テーブルに該当するデータが見つかりません。検索キー：分科細目CD'"
						+ saimokuCd
						+ "' 分割番号'"
						+ bunkatsuCd
						+ "' キーワードコード'"
						+ keywordCd
						+ "'");
			}
			
			return (KeywordInfo[])resultArray.toArray(new KeywordInfo[0]);
			
		} catch (SQLException ex) {
			throw new DataAccessException("キーワード情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}






















	/**
	 * キーワード情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録するキーワード情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertKeywordInfo(
		Connection connection,
		KeywordInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//重複チェック
		try {
			selectKeywordInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO MASTER_KEYWORD "
				+ "("
				+ " BUNKASAIMOKU_CD"		//分科細目コード
				+ ",SAIMOKU_NAME"			//細目名
				+ ",BUNKA_CD"				//分科コード
				+ ",BUNKA_NAME"				//分科名
				+ ",BUNYA_CD"				//分野コード
				+ ",BUNYA_NAME"				//分野名
				+ ",KEI"					//系
				+ ",BUNKATSU_NO"			//分割番号
				+ ",KEYWORD_CD"				//記号
				+ ",KEYWORD"				//キーワード
				+ ")"
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunkaSaimokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSaimokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunkaCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunkaName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunyaCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunyaName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunkatsuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKeywordCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKeyword());

			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("キーワード情報登録中に例外が発生しました。 ", ex);
			throw new DataAccessException("キーワード情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * キーワード情報を更新する。
	 * @param connection				コネクション
	 * @param addInfo					更新するキーワード情報
	 * @throws DataAccessException		更新中に例外が発生した場合
	 * @throws NoDataFoundException		対象データが見つからない場合
	 */
	public void updateKeywordInfo(
		Connection connection,
		KeywordInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
		//検索（対象データが存在しなかった場合は例外発生）
		selectKeywordInfo(connection, updateInfo);

		String query =
			"UPDATE MASTER_KEYWORD"
				+ " SET"	
				+ " BUNKASAIMOKU_CD = ?"		//分科細目コード
				+ ",SAIMOKU_NAME = ?"			//細目名
				+ ",BUNKA_CD = ?"				//分科コード
				+ ",BUNKA_NAME = ?"				//分科名
				+ ",BUNYA_CD = ?"				//分野コード
				+ ",BUNYA_NAME = ?"				//分野名
				+ ",KEI = ?"					//系
				+ " WHERE"
				+ " BUNKASAIMOKU_CD = ?";

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunkaSaimokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSaimokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunkaCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunkaName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunyaCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunyaName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunkaSaimokuCd());	//分科細目コード
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("キーワード情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	
	/**
	 * キーワード情報を削除する。(物理削除) 
	 * @param connection			コネクション
	 * @param addInfo				削除するキーワード主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteKeywordInfo(
		Connection connection,
		KeywordPk deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//検索（対象データが存在しなかった場合は例外発生）
		selectKeywordInfo(connection, deleteInfo);

		String query =
			"DELETE FROM MASTER_KEYWORD"
				+ " WHERE"
				+ " BUNKASAIMOKU_CD = ?";

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getBunkaSaimokuCd());	//分科細目コード
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("キーワード情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
}
