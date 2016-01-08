/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/07/26
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.AccessKanriInfo;
import jp.go.jsps.kaken.model.vo.AccessKanriPk;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * アクセス制御テーブルアクセスクラス。
 * ID RCSfile="$RCSfile: AccessKanriDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class AccessKanriDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(AccessKanriDao.class);

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
	public AccessKanriDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * キーに一致するアクセス制御情報を取得する。
	 * @param connection			    コネクション
	 * @param pkInfo				    主キー情報
	 * @return						    アクセス制御情報
	 * @throws DataAccessException	    データ取得中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public AccessKanriInfo selectAccessKanriInfo(
		Connection connection,
		AccessKanriPk pkInfo)
		throws DataAccessException, NoDataFoundException
	{
			String query =
				"SELECT "
					+ " GYOMUTANTO_ID"
					+ ",JIGYO_CD"
					+ ",JIGYO_KUBUN"
					+ ",BIKO"
					+ " FROM ACCESSKANRI"
					+ " WHERE"
					+ "   GYOMUTANTO_ID = ?"
					+ " AND"
					+ "   JIGYO_CD = ?"
					;
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			try {
				AccessKanriInfo result = new AccessKanriInfo();
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement, i++, pkInfo.getGyomutantoId());
				DatabaseUtil.setParameter(preparedStatement, i++, pkInfo.getJigyoCd());				
				recordSet = preparedStatement.executeQuery();
				if (recordSet.next()) {
					result.setGyomutantoId(recordSet.getString("GYOMUTANTO_ID"));
					result.setJigyoCd(recordSet.getString("JIGYO_CD"));
					result.setJigyoKubun(recordSet.getString("JIGYO_KUBUN"));
					result.setBiko(recordSet.getString("BIKO"));
					return result;
				} else {
					throw new NoDataFoundException(
						"アクセス制御テーブルに該当するデータが見つかりません。検索キー：業務担当者ID'"
							+ pkInfo.getGyomutantoId()
							+ "'、事業コード'"
							+ pkInfo.getJigyoCd()
							+ "'");
				}
			} catch (SQLException ex) {
				throw new DataAccessException("アクセス制御テーブル検索実行中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}	
	
	
	
	/**
	 * アクセス制御情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録するアクセス制御情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertAccessKanriInfo(
		Connection connection,
		AccessKanriInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
			//重複チェック
			try {
				selectAccessKanriInfo(connection, addInfo);
				//NG
				throw new DuplicateKeyException(
					"'" + addInfo + "'は既に登録されています。");
			} catch (NoDataFoundException e) {
				//OK
			}
		
			String query =
				"INSERT INTO ACCESSKANRI "
					+ "("
					+ " GYOMUTANTO_ID"
					+ ",JIGYO_CD"
					+ ",JIGYO_KUBUN"
					+ ",BIKO"
					+ ") "
					+ "VALUES "
					+ "(?,?,?,?)"
					;
					
			PreparedStatement preparedStatement = null;
			try {
				//登録
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getGyomutantoId());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoCd());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoKubun());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
				DatabaseUtil.executeUpdate(preparedStatement);
			} catch (SQLException ex) {
				throw new DataAccessException("アクセス制御情報登録中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	
	
	
	/**
	 * アクセス制御情報を更新する。
	 * @param connection				コネクション
	 * @param updateInfo				更新する業務担当者情報
	 * @throws DataAccessException		更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateGyomutantoInfo(
		Connection connection,
		AccessKanriInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
			//検索
			selectAccessKanriInfo(connection, updateInfo);
	
			String query =
				"UPDATE ACCESSKANRI"
					+ " SET"
					+ " GYOMUTANTO_ID = ?"
					+ ",JIGYO_CD = ?"
					+ ",JIGYO_KUBUN = ?"
					+ ",BIKO = ?"
					+ " WHERE"
					+ "   GYOMUTANTO_ID = ?"
					+ " AND"
					+ "   JIGYO_CD = ?"
					;

			PreparedStatement preparedStatement = null;
			try {
				//登録
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getGyomutantoId());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoCd());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getGyomutantoId());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoCd());				
				DatabaseUtil.executeUpdate(preparedStatement);				
			} catch (SQLException ex) {
				throw new DataAccessException("アクセス制御情報更新中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	
	
	
	/**
	 * アクセス管理情報を削除する。(物理削除)
	 * @param connection			    コネクション
	 * @param pkInfo				    削除する業務担当者情報
	 * @throws DataAccessException     削除中に例外が発生した場合
	 * @throws NoDataFoundException    対象データが見つからない場合
	 */
	public void deleteAccessKanriInfo(
		Connection connection,
		AccessKanriInfo deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//検索（削除対象データが存在しなかった場合は例外発生）
		selectAccessKanriInfo(connection, deleteInfo);
		
		String query =
			"DELETE FROM ACCESSKANRI"
				+ " WHERE"
				+ "   GYOMUTANTO_ID = ?"
				+ " AND"
				+ "   JIGYO_CD = ?"
				;
		
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getGyomutantoId());
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getJigyoCd());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("アクセス制御情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}	
	
	
	
	/**
	 * 当該業務担当者のアクセス制御情報を全て返す。<!--.-->
	 * 対象データが存在しない場合は、空のHashSetが格納されている。<br>
	 * 戻り値の型が、updateAccessKanri()の引数とは違うので注意。<br>
	 * 	 * <table border="1">
	 * 	<tr>
	 * 		<td>No</td>
	 * 		<td>キー名</td>
	 * 		<td>型</td>
	 * 		<td>値</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>1</td>
	 * 		<td>tantoJigyoCd</td>
	 * 		<td>HashSet</td>
	 * 		<td>担当事業コード</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>2</td>
	 * 		<td>tantoJigyoKubun</td>
	 * 		<td>HashSet</td>
	 * 		<td>担当事業区分</td>
	 * 	</tr>
	 * </table><br>
	 * @param connection             コネクション
	 * @param gyomuTantoPk           業務担当者情報主キー
	 * @return                       当該業務担当者のアクセス制御情報
	 * @throws DataAccessException   データ取得中に例外が発生した場合
	 */
	public Map selectAccessKanri(
		Connection   connection,
		GyomutantoPk gyomuTantoPk)
		throws DataAccessException
	{
		String query =
			"SELECT "
				+ " JIGYO_CD"
				+ ",JIGYO_KUBUN"
				+ " FROM ACCESSKANRI"
				+ " WHERE"
				+ "   GYOMUTANTO_ID = ?"
				+ " ORDER BY GYOMUTANTO_ID"
				;
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			Set tantoJigyoCd     = new HashSet();
			Set tantoJigyoKubun  = new HashSet();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, gyomuTantoPk.getGyomutantoId());
			//複数件の削除なのでDatabaseUtilを通さない。
			recordSet = preparedStatement.executeQuery();
			while(recordSet.next()) {
				tantoJigyoCd.add(recordSet.getString("JIGYO_CD"));
				tantoJigyoKubun.add(recordSet.getString("JIGYO_KUBUN"));
			} 

			//促進費（基盤C）を担当した場合、促進費（基盤AB、若手AB）も該当する 2007/3/23修正
			if (tantoJigyoCd.contains("00154")){
				tantoJigyoCd.add("00152");
				tantoJigyoCd.add("00153");
				tantoJigyoCd.add("00155");
				tantoJigyoCd.add("00156");
			}
			
			Map result = new HashMap();
			result.put("tantoJigyoCd"    , tantoJigyoCd);
			result.put("tantoJigyoKubun" , tantoJigyoKubun);
			return result;

		} catch (SQLException ex) {
			throw new DataAccessException("アクセス制御テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}		
		
	}
	
	
	
	/**
	 * 当該業務担当者のアクセス管理情報を削除する。(物理削除)
	 * @param connection			    コネクション
	 * @param gyomuTantoPk             業務担当者情報主キー
	 * @throws DataAccessException     削除中に例外が発生した場合
	 */
	public void deleteAccessKanri(
		Connection connection,
		GyomutantoPk gyomuTantoPk)
		throws DataAccessException
	{
		String query =
			"DELETE FROM ACCESSKANRI"
				+ " WHERE"
				+ "   GYOMUTANTO_ID = ?"
				;
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,gyomuTantoPk.getGyomutantoId());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("アクセス制御情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}	
	
	
	
	/**
	 * 当該業務担当者のアクセス制御情報を更新する。<!--.-->
	 * 当該業務担当者のアクセス制御情報を全て削除して、新規で登録する。<br>
	 * 第三引数の型が、selectAccessKanri()の戻り値とは違うので注意。<br>
	 * <table border="1">
	 * 	<tr>
	 * 		<td>No</td>
	 * 		<td>キー名</td>
	 * 		<td>型</td>
	 * 		<td>値</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>1</td>
	 * 		<td>tantoJigyoCd</td>
	 * 		<td>ArrayList</td>
	 * 		<td>担当事業コード</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>2</td>
	 * 		<td>tantoJigyoKubun</td>
	 * 		<td>ArrayList</td>
	 * 		<td>担当事業区分</td>
	 * 	</tr>
	 * </table><br>
	 * 本メソッドでは、項目の「備考」については考慮しない。<br>
	 * 
	 * @param connection            コネクション
	 * @param gyomuTantoPk          業務担当者情報主キー
	 * @param updateMap             担当事業情報（事業コード、事業区分）
	 * @return int                  登録件数
	 * @throws DataAccessException  DBアクセス時にエラーとなった場合
	 */
	public int updateAccessKanri(
		Connection   connection,
		GyomutantoPk gyomuTantoPk,
		Map          updateMap)
		throws DataAccessException
	{	
		//削除
		deleteAccessKanri(connection, gyomuTantoPk);
		
		//担当事業情報を取得する
		List tantoJigyoCdList    = (List)updateMap.get("tantoJigyoCd");
		List tantoJigyoKubunList = (List)updateMap.get("tantoJigyoKubun");				
		
		AccessKanriInfo insertInfo = new AccessKanriInfo();
		insertInfo.setGyomutantoId(gyomuTantoPk.getGyomutantoId());
		
		int count = 0;
		for(int i=0; i<tantoJigyoCdList.size(); i++){
			insertInfo.setJigyoCd((String)tantoJigyoCdList.get(i));
			insertInfo.setJigyoKubun((String)tantoJigyoKubunList.get(i));
			//新規登録
			insertAccessKanriInfo(connection, insertInfo);
			count++;
		}
		
		return count;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
