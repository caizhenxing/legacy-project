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

import org.apache.commons.logging.*;

/**
 *　所属機関マスタデータアクセスクラス。
 * ID RCSfile="$RCSfile: MasterKikanInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class MasterKikanInfoDao {

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
	public MasterKikanInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * 所属機関情報の一覧を取得する。
	 * @param	connection			コネクション
	 * @return						所属機関情報
	 * @throws ApplicationException
	 */
	public static List selectKikanList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.SHUBETU_CD"			//機関種別コード
			+ ",A.KIKAN_KUBUN"			//機関区分
			+ ",A.SHOZOKU_CD"			//機関コード
			+ ",A.SHOZOKU_NAME_KANJI"	//機関名称（日本語）
			+ ",A.SHOZOKU_RYAKUSHO"		//機関略称
			+ ",A.SHOZOKU_NAME_EIGO"	//機関名称（英語）
			+ ",A.SHOZOKU_ZIP"			//郵便番号
			+ ",A.SHOZOKU_ADDRESS1"		//住所１
			+ ",A.SHOZOKU_ADDRESS2"		//住所２
			+ ",A.SHOZOKU_TEL"			//電話番号
			+ ",A.SHOZOKU_FAX"			//FAX番号
			+ ",A.SHOZOKU_DAIHYO_NAME"	//代表者
			+ ",A.BIKO"					//備考
			+ " FROM MASTER_KIKAN A"
			+ " ORDER BY SHOZOKU_CD"
			;								
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
				"所属機関情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"所属機関情報に1件もデータがありません。",
				e);
		}
	}

	/**
	 * キーに一致する所属機関情報を取得する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						所属機関情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public KikanInfo selectKikanInfo(Connection connection,KikanPk pkInfo)
		throws DataAccessException, NoDataFoundException {

		KikanInfo result = new KikanInfo();
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement =
				connection.prepareStatement(
					"SELECT * FROM MASTER_KIKAN WHERE SHOZOKU_CD = ?");
			int i = 1;
			preparedStatement.setInt(i++, Integer.parseInt(pkInfo.getShozokuCd()));
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setShubetuCd(recordSet.getString("SHUBETU_CD"));					//機関種別コード
				result.setKikanKubun(recordSet.getString("KIKAN_KUBUN"));				//機関区分
				result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));					//機関コード
				result.setShozokuNameKanji(recordSet.getString("SHOZOKU_NAME_KANJI"));	//機関名称（日本語）
				result.setShozokuRyakusho(recordSet.getString("SHOZOKU_RYAKUSHO"));		//機関名称（略称）
				result.setShozokuNameEigo(recordSet.getString("SHOZOKU_NAME_EIGO"));	//機関名称（英語）
				result.setShozokuZip(recordSet.getString("SHOZOKU_ZIP"));				//郵便番号
				result.setShozokuAddress1(recordSet.getString("SHOZOKU_ADDRESS1"));		//住所1
				result.setShozokuAddress2(recordSet.getString("SHOZOKU_ADDRESS2"));		//住所2
				result.setShozokuTel(recordSet.getString("SHOZOKU_TEL"));				//電話番号
				result.setShozokuFax(recordSet.getString("SHOZOKU_FAX"));				//FAX番号
				result.setShozokuDaihyoName(recordSet.getString("SHOZOKU_DAIHYO_NAME"));//代表者名
				result.setBiko(recordSet.getString("BIKO"));							//備考
				//...
			} else {
				throw new NoDataFoundException(
					"所属機関マスタに該当するデータが見つかりません。検索キー：' 機関コード'"
						+ pkInfo.getShozokuCd()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("所属機関マスタ検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return result;
	}

	
	//TODO コード表（未使用）
	/**
	 * コード一覧作成用メソッド。<br>
	 * 機関コードと機関名称（日本語）の一覧を返す。
     * 機関コード順にソートする。
	 * @param	connection			コネクション
	 * @param	shubetuCd			機関種別コード
	 * @return						所属機関情報
	 * @throws ApplicationException
	 */
	public static List selectKikanList(Connection connection, String shubetuCd)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.SHOZOKU_CD"			//機関コード
			+ ",A.SHOZOKU_NAME_KANJI"	//機関名称（日本語）
			+ " FROM MASTER_KIKAN A"
			+ " WHERE SHUBETU_CD = ?"
			+ " ORDER BY SHOZOKU_CD"	//機関コード順
			;								
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString(), new String[]{shubetuCd});
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"所属機関情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"所属機関マスタに1件もデータがありません。",
				e);
		}
	}


	//TODO コード表（未使用）
	/**
	 * コード一覧作成用メソッド。<br>
	 * 機関種別コードと種別名称の一覧を返す。
	 * 所属機関種別コード順にソートする。
	 * @param	connection			コネクション
	 * @return						所属機関種別情報
	 * @throws ApplicationException
	 */
	public static List selectKikanShubetuList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.SHUBETU_CD"									//機関種別コード
			+ ",TO_NUMBER(A.SHUBETU_CD) SHUBETU_CD_NUM"			//機関種別コード（数値）
			+ ",B.SHUBETU_NAME"									//機関名称（日本語）
			+ " FROM MASTER_KIKAN A INNER JOIN MASTER_KIKANSHUBETU B ON A.SHUBETU_CD = B.SHUBETU_CD"
			+ " GROUP BY A.SHUBETU_CD,B.SHUBETU_NAME"
			+ " ORDER BY SHUBETU_CD_NUM"
			;								
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
				"所属機関情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"所属機関マスタに1件もデータがありません。",
				e);
		}
	}
	
	//TODO コード表（2004/11/17）
	/**
	 * 機関コード表作成用メソッド。<br>
	 * インデックスを返す。
	 * 大分類コード、中分類コード、小分類コード、所属機関コードの昇順でソートする。
	 * @param	connection			コネクション
	 * @return						機関コード情報
	 * @throws ApplicationException
	 */
	public static List selectKikanCodeIndex(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " B.DAIBUNRUI_CD,"	//大分類コード
			+ " B.DAIBUNRUI_NAME,"	//大分類名
			+ " B.CHUBUNRUI_CD,"	//中分類コード
			+ " B.CHUBUNRUI_NAME"	//中分類名
//2005/08/01 小分類が不要となる為、コメントする
//			+ " B.SHOBUNRUI_CD,"	//小分類コード
//			+ " B.SHOBUNRUI_NAME"	//小分類名
			+ " FROM MASTER_KIKAN A,MASTER_KOKAIKUBUN B"
			+ " WHERE"
			+ " A.SHUBETU_CD = B.SHUBETU_CD"	//機関種別コード
			+ " AND"
			+ " A.KIKAN_KUBUN = B.KIKAN_KUBUN"	//機関区分
			+ " GROUP BY"
			+ " B.DAIBUNRUI_CD,"	//大分類コード
			+ " B.DAIBUNRUI_NAME,"	//大分類名
			+ " B.CHUBUNRUI_CD,"	//中分類コード
			+ " B.CHUBUNRUI_NAME"	//中分類名
//			+ " B.SHOBUNRUI_CD,"	//小分類コード
//			+ " B.SHOBUNRUI_NAME"	//小分類名
			+ " ORDER BY"
			+ " B.DAIBUNRUI_CD,"	//大分類コード
			+ " B.DAIBUNRUI_NAME,"	//大分類名
			+ " B.CHUBUNRUI_CD,"	//中分類コード
			+ " B.CHUBUNRUI_NAME"	//中分類名
//			+ " B.SHOBUNRUI_CD,"	//小分類コード
//			+ " B.SHOBUNRUI_NAME"	//小分類名
			;								
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
				"所属機関情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"所属機関マスタに1件もデータがありません。",
				e);
		}
	}

	//TODO コード表（2004/11/17）
	/**
	 * 機関コード表作成用メソッド。<br>
	 * 機関マスタと公開区分マスタの一覧を返す。
	 * 大分類コード、中分類コード、小分類コード、所属機関コードの昇順でソートする。
	 * @param	connection			コネクション
	 * @return						機関コード情報
	 * @throws ApplicationException
	 */
	public static List selectKikanCodeList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.SHUBETU_CD,"			//種別機関コード
			+ " A.KIKAN_KUBUN,"			//機関区分
			+ " A.SHOZOKU_CD,"			//所属機関コード
			+ " A.SHOZOKU_NAME_KANJI,"	//所属機関名
			+ " B.DAIBUNRUI_CD,"		//大分類コード
			+ " B.DAIBUNRUI_NAME,"		//大分類名
			+ " B.CHUBUNRUI_CD,"		//中分類コード
			+ " B.CHUBUNRUI_NAME"		//中分類名
//2005/08/01 小分類が不要となる為、コメントする
//			+ " B.SHOBUNRUI_CD,"		//小分類コード
//			+ " B.SHOBUNRUI_NAME"		//小分類名
			+ " FROM MASTER_KIKAN A,MASTER_KOKAIKUBUN B"
			+ " WHERE"
			+ " A.SHUBETU_CD = B.SHUBETU_CD"	//機関種別コード
			+ " AND"
			+ " A.KIKAN_KUBUN = B.KIKAN_KUBUN"	//機関区分
//			+ " ORDER BY B.DAIBUNRUI_CD, B.CHUBUNRUI_CD, B.SHOBUNRUI_CD, A.SHOZOKU_CD"	//大分類、中分類、小分類、所属機関コード
			+ " ORDER BY B.DAIBUNRUI_CD, B.CHUBUNRUI_CD, A.SHOZOKU_CD"	//大分類、中分類、小分類、所属機関コード
			;								
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
				"所属機関情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"所属機関マスタに1件もデータがありません。",
				e);
		}
	}
	
	/**
	 * 所属機関マスタ情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する所属機関マスタ情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertMasterKikan(
		Connection connection,
		KikanInfo addInfo)
		throws DataAccessException
	{
		//重複チェック
		try {
			selectKikanInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}
					
		String query = "INSERT INTO MASTER_KIKAN "
					 + "("
					 + " SHUBETU_CD"
					 + ",KIKAN_KUBUN"
					 + ",SHOZOKU_CD"
					 + ",SHOZOKU_NAME_KANJI"
					 + ",SHOZOKU_RYAKUSHO"
					 + ",SHOZOKU_NAME_EIGO"
					 + ",SHOZOKU_ZIP"
					 + ",SHOZOKU_ADDRESS1"
					 + ",SHOZOKU_ADDRESS2"
					 + ",SHOZOKU_TEL"
					 + ",SHOZOKU_FAX"
					 + ",SHOZOKU_DAIHYO_NAME"
					 + ",BIKO"
					 + ") "
					 + "VALUES "
					 + "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShubetuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKikanKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuNameKanji());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuRyakusho());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuNameEigo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuZip());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuAddress1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuAddress2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuTel());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuFax());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuDaihyoName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("所属機関マスタ登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * 所属機関情報を一括登録する。（※未実装）
	 * @param connection				コネクション
	 * @param addInfos					登録する所属機関情報リスト
	 * @throws DataAccessException		登録に例外が発生した場合。
	 */
	public void insertKikanInfos(Connection connection, List addInfos)
		throws DataAccessException {
		return;
	}

	//2005.08.08 iso 所属機関担当者ログイン時に機関マスタ存在チェックをするため追加
	/**
	 * 機関情報の数を取得する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						機関数
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 */
	public int countShozokuInfo(
			Connection connection,
			String shozokuCd)
			throws DataAccessException {
		
		String query =
			"SELECT COUNT(*)"
				+ " FROM MASTER_KIKAN"
				+ " WHERE SHOZOKU_CD = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShozokuInfo result = new ShozokuInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, shozokuCd);
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("所属機関マスタテーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	/**
	 * 公開区分マスタに存在チェックを行う
	 * @param connection
	 * @param kikanShubetu	機関種別
	 * @param kikanKubun	機関区分
	 * @return　件数
	 * @throws DataAccessException
	 */
	public int checkKokaiKubun(
			Connection connection,
			String kikanShubetu,
			String kikanKubun)
			throws DataAccessException {
		
		String query =
			"SELECT COUNT(*)"
				+ " FROM MASTER_KOKAIKUBUN"
				+ " WHERE SHUBETU_CD = ?"
				+ " AND KIKAN_KUBUN = ?"
				;

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, kikanShubetu);
			preparedStatement.setString(i++, kikanKubun);
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("公開区分マスタテーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
}
