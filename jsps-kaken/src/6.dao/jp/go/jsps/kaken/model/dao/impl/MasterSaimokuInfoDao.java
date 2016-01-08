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
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.SaimokuInfo;
import jp.go.jsps.kaken.model.vo.SaimokuPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 分科細目マスタデータアクセスクラス。
 */
public class MasterSaimokuInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(MasterSaimokuInfoDao.class);

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
	public MasterSaimokuInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * 分科細目情報の一覧を取得する。
	 * @param	connection			コネクション
	 * @return						分科細目情報
	 * @throws ApplicationException
	 */
	public static List selectSaimokuInfoList(Connection connection)
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
			+ " FROM MASTER_SAIMOKU A"
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
				"分科細目情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"分科細目情報マスタに1件もデータがありません。",
				e);
		}
	}

//	/**
//	 * 分科細目情報の一覧を取得する。
//	 * @param	connection			コネクション
//	 * @param	bunkaName			分科名
//	 * @return						分科細目情報
//	 * @throws ApplicationException
//	 */
//	public static List selectSaimokuInfoList(Connection connection, String bunkaName)
//		throws ApplicationException {
//
//		//-----------------------
//		// SQL文の作成
//		//-----------------------
//		String select =
//			"SELECT"
//			+ " A.BUNKASAIMOKU_CD"				//分科細目コード
//			+ ",A.BUNKA_NAME"					//分科名
//			+ " FROM MASTER_SAIMOKU A"
//			+ " WHERE A.BUNKA_NAME = ? "		//分科名
//			+ " ORDER BY A.BUNKASAIMOKU_CD";	//分科細目コードの昇順							
//			StringBuffer query = new StringBuffer(select);
//
//		if(log.isDebugEnabled()){
//			log.debug("query:" + query);
//		}
//		
//		//-----------------------
//		// リスト取得
//		//-----------------------
//		try {
//			return SelectUtil.select(connection,query.toString(),new String[]{bunkaName});
//		} catch (DataAccessException e) {
//			throw new ApplicationException(
//				"分科細目情報検索中にDBエラーが発生しました。",
//				new ErrorInfo("errors.4004"),
//				e);
//		} catch (NoDataFoundException e) {
//			throw new SystemException(
//				"分科細目情報マスタに1件もデータがありません。",
//				e);
//		}
//	}

	//TODO コード表
	/**
	 * 分科細目情報の一覧を取得する。
	 * @param	connection			コネクション
	 * @param	bunkaCd				分科コード
	 * @return						分科細目情報
	 * @throws ApplicationException
	 */
	public static List selectSaimokuInfoList(Connection connection, String bunkaCd)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUNKASAIMOKU_CD"				//分科細目コード
			+ ",A.SAIMOKU_NAME"					//細目名
// 2005/04/15 追加 ここから-----------------------------
// 理由 「分割番号」追加
			+ ",A.BUNKATSU_NO"			//分割番号
// 追加 ここまで----------------------------------------
			+ " FROM MASTER_SAIMOKU A"
			;
			//TODO 分科コード空の暫定対応。
			if(bunkaCd != null && !bunkaCd.equals("")) {
				select += " WHERE A.BUNKA_CD = ? ";			//分科コード
			} else {
				select += " WHERE A.BUNKA_CD IS NULL ";
			}
			select += " ORDER BY A.BUNKASAIMOKU_CD,A.BUNKATSU_NO";	//分科細目コードの昇順							
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			//TODO 分科コード空の暫定対応。
			if(bunkaCd != null && !bunkaCd.equals("")) {
				return SelectUtil.select(connection,query.toString(),new String[]{bunkaCd});
			} else {
				return SelectUtil.select(connection,query.toString());
			}
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"分科細目情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"分科細目情報マスタに1件もデータがありません。",
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
			+ " FROM MASTER_SAIMOKU A"
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
				"分科細目情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"分科細目情報マスタに1件もデータがありません。",
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
			+ " FROM MASTER_SAIMOKU A"
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
				"分科細目情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"分科細目情報マスタに1件もデータがありません。",
				e);
		}
	}
	
	
	
	/**
	 * 分科細目情報を取得する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						分科細目
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public SaimokuInfo selectSaimokuInfo(
		Connection connection,
		SaimokuPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		
//		2005/04/12 変更 ここから----------
//		理由:分割番号追加のため
		String query = "";
		//分割番号が無い場合は分割番号を指定しないためフラグにfalseを指定
		boolean bunkatsuFlg = true;

// 20050707 分割番号が"-"だった場合にエラーとする条件を追加
//		if(primaryKeys.getBunkatsuNo() == null || primaryKeys.getBunkatsuNo().length() == 0){
//			bunkatsuFlg = false;
//		}
		if(primaryKeys.getBunkatsuNo() == null || 
			primaryKeys.getBunkatsuNo().length() == 0 ||
			primaryKeys.getBunkatsuNo() == "-"){
			bunkatsuFlg = false;
		}
// Horikoshi

		if(bunkatsuFlg){
			query =
				"SELECT "
					+ " A.BUNKASAIMOKU_CD"		//分科細目コード
					+ ",A.SAIMOKU_NAME"			//細目名
					+ ",A.BUNKA_CD"				//分科コード
					+ ",A.BUNKA_NAME"			//分科名
					+ ",A.BUNYA_CD"				//分野コード
					+ ",A.BUNYA_NAME"			//分野名
					+ ",A.KEI"					//系
					+ ",A.BUNKATSU_NO"			//分割番号
					+ " FROM MASTER_SAIMOKU A"
					+ " WHERE BUNKASAIMOKU_CD = ?"
					+ " AND"
					+ " BUNKATSU_NO = ?";
		}else{
				query =
					"SELECT "
					+ " A.BUNKASAIMOKU_CD"		//分科細目コード
					+ ",A.SAIMOKU_NAME"			//細目名
					+ ",A.BUNKA_CD"				//分科コード
					+ ",A.BUNKA_NAME"			//分科名
					+ ",A.BUNYA_CD"				//分野コード
					+ ",A.BUNYA_NAME"			//分野名
					+ ",A.KEI"					//系
					+ " FROM MASTER_SAIMOKU A"
					+ " WHERE BUNKASAIMOKU_CD = ?";
		}
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			SaimokuInfo result = new SaimokuInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getBunkaSaimokuCd());
			
			//分割番号が無い場合は分割番号を指定しない
			if(bunkatsuFlg){
				preparedStatement.setString(i++, primaryKeys.getBunkatsuNo());
			}
			
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
				result.setSaimokuName(recordSet.getString("SAIMOKU_NAME"));
				result.setBunkaCd(recordSet.getString("BUNKA_CD"));
				result.setBunkaName(recordSet.getString("BUNKA_NAME"));
				result.setBunyaCd(recordSet.getString("BUNYA_CD"));
				result.setBunyaName(recordSet.getString("BUNYA_NAME"));
				result.setKei(recordSet.getString("KEI"));
				
				//分割番号が無い場合は分割番号を指定しない
				if(bunkatsuFlg){
					result.setBunkatsuNo(recordSet.getString("BUNKATSU_NO"));
				}
				
			} else {
				throw new NoDataFoundException(
					"分科細目情報テーブルに該当するデータが見つかりません。検索キー：分科細目CD'"
						+ primaryKeys.getBunkaSaimokuCd()
						+ "' 分割番号'"
						+ primaryKeys.getBunkatsuNo()
						+ "'");
			}
			
			return result;
//			2005/04/12 修正 ここまで----------
		} catch (SQLException ex) {
			throw new DataAccessException("分科細目情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}


	/**
	 * 分科細目情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する分科細目情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertSaimokuInfo(
		Connection connection,
		SaimokuInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//重複チェック
		try {
			checkSaimokuInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO MASTER_SAIMOKU "
				+ "("
				+ " BUNKASAIMOKU_CD"		//分科細目コード
				+ ",SAIMOKU_NAME"			//細目名
				+ ",BUNKA_CD"				//分科コード
				+ ",BUNKA_NAME"				//分科名
				+ ",BUNYA_CD"				//分野コード
				+ ",BUNYA_NAME"				//分野名
				+ ",KEI"					//系
				//2005/04/22 追加 ここから-------------------------------------------------
				//分割番号を追加 (VALUESにも'?'を一つ追加)
				+ ",BUNKATSU_NO"
				//追加 ここまで------------------------------------------------------------
				+ ")"
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?)";
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
			//2005/04/22 追加 ここから-------------------------------------------------
			//分割番号を追加
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunkatsuNo());
			//追加 ここまで------------------------------------------------------------
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("分科細目情報登録中に例外が発生しました。 ", ex);
			throw new DataAccessException("分科細目情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * 分科細目情報を更新する。
	 * @param connection				コネクション
	 * @param addInfo					更新する分科細目情報
	 * @throws DataAccessException		更新中に例外が発生した場合
	 * @throws NoDataFoundException		対象データが見つからない場合
	 */
	public void updateSaimokuInfo(
		Connection connection,
		SaimokuInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
		//検索（対象データが存在しなかった場合は例外発生）
		selectSaimokuInfo(connection, updateInfo);

		String query =
			"UPDATE MASTER_SAIMOKU"
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
			throw new DataAccessException("分科細目情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	
	/**
	 * 分科細目情報を削除する。(物理削除) 
	 * @param connection			コネクション
	 * @param addInfo				削除する分科細目主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteSaimokuInfo(
		Connection connection,
		SaimokuPk deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//検索（対象データが存在しなかった場合は例外発生）
		selectSaimokuInfo(connection, deleteInfo);

		String query =
			"DELETE FROM MASTER_SAIMOKU"
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
			throw new DataAccessException("分科細目情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * 分科細目情報存在をチェックする。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						分科細目
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public SaimokuInfo checkSaimokuInfo(
		Connection connection,
		SaimokuPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		
		String query = "";

		query = "SELECT "
					+ " A.BUNKASAIMOKU_CD"		//分科細目コード
					+ ",A.SAIMOKU_NAME"			//細目名
					+ ",A.BUNKA_CD"				//分科コード
					+ ",A.BUNKA_NAME"			//分科名
					+ ",A.BUNYA_CD"				//分野コード
					+ ",A.BUNYA_NAME"			//分野名
					+ ",A.KEI"					//系
					+ ",A.BUNKATSU_NO"			//分割番号
					+ " FROM MASTER_SAIMOKU A"
					+ " WHERE BUNKASAIMOKU_CD = ?"
					+ " AND"
					+ " BUNKATSU_NO = ?";

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			SaimokuInfo result = new SaimokuInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getBunkaSaimokuCd());
			preparedStatement.setString(i++, primaryKeys.getBunkatsuNo());
			
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
				
			} else {
				throw new NoDataFoundException(
					"分科細目情報テーブルに該当するデータが見つかりません。検索キー：分科細目CD'"
						+ primaryKeys.getBunkaSaimokuCd()
						+ "' 分割番号'"
						+ primaryKeys.getBunkatsuNo()
						+ "'");
			}
			
			return result;

		} catch (SQLException ex) {
			throw new DataAccessException("分科細目情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
    
//2006/06/27 苗　追加ここから
    /**
     * 細目情報存在をチェックする。
     * @param connection            コネクション
     * @param primaryKeys           主キー情報
     * @return SaimokuInfo          分科細目
     * @throws DataAccessException  データ取得中に例外が発生した場合。
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public SaimokuInfo checkSaimokuInfoForGaiyo(
        Connection connection,
        SaimokuPk primaryKeys)
        throws DataAccessException, NoDataFoundException {
        
        StringBuffer query = new StringBuffer();

        query.append("SELECT ");
        query.append(" A.BUNKASAIMOKU_CD"); // 分科細目コード
        query.append(",A.SAIMOKU_NAME"); // 細目名
        query.append(",A.BUNKA_NAME"); // 分科名
        query.append(",A.BUNYA_NAME"); // 分野名
        query.append(" FROM MASTER_SAIMOKU A");
        query.append(" WHERE A.BUNKASAIMOKU_CD = ?"); 

        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            SaimokuInfo result = new SaimokuInfo();
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            preparedStatement.setString(i++, primaryKeys.getBunkaSaimokuCd());
            
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                result.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
                result.setSaimokuName(recordSet.getString("SAIMOKU_NAME"));
                result.setBunkaName(recordSet.getString("BUNKA_NAME"));
                result.setBunyaName(recordSet.getString("BUNYA_NAME"));
                
            } else {
                throw new NoDataFoundException(
                    "分科細目情報テーブルに該当するデータが見つかりません。検索キー：分科細目CD'"
                        + primaryKeys.getBunkaSaimokuCd()
                        + "'");
            }
            
            return result;

        } catch (SQLException ex) {
            throw new DataAccessException("分科細目情報テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2006/06/27　苗　追加ここまで
    
//2007/03/01 苗　追加ここから
    /**
     * 分科細目情報を取得する。
     * @param connection            コネクション
     * @param primaryKeys           主キー情報
     * @return                      分科細目
     * @throws DataAccessException  データ取得中に例外が発生した場合。
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public SaimokuInfo selectSaimokuInfoForCheck(
        Connection connection,
        SaimokuPk primaryKeys)
        throws DataAccessException, NoDataFoundException
    {
        String query = "SELECT "
                    + " A.BUNKASAIMOKU_CD"      //分科細目コード
                    + ",A.SAIMOKU_NAME"         //細目名
                    + ",A.BUNKA_CD"             //分科コード
                    + ",A.BUNKA_NAME"           //分科名
                    + ",A.BUNYA_CD"             //分野コード
                    + ",A.BUNYA_NAME"           //分野名
                    + ",A.KEI"                  //系
                    + ",A.BUNKATSU_NO"          //分割番号
                    + " FROM MASTER_SAIMOKU A"
                    + " WHERE BUNKASAIMOKU_CD = ?";
        
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            SaimokuInfo result = new SaimokuInfo();
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, primaryKeys.getBunkaSaimokuCd());
            
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
            }
            return result;
        } catch (SQLException ex) {
            throw new DataAccessException("分科細目情報テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2007/03/01　苗　追加ここまで    
}
