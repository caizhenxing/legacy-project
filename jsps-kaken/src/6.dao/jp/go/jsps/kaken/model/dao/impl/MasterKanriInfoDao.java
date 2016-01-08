/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/16
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
 *　マスタ管理マスタデータアクセスクラス。
 * ID RCSfile="$RCSfile: MasterKanriInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class MasterKanriInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(MasterKanriInfoDao.class);

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
	public MasterKanriInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * マスタ管理情報を取得する。
	 * @param	connection				コネクション
	 * @param  masterShubetu       	マスタ種別（主キー情報）
	 * @return							マスタ管理情報
	 * @throws NoDataFoundException	データが見つからなかった場合
	 * @throws DataAccessException		データベースアクセス中にエラーが発生した場合
	 */
	public MasterKanriInfo selectMasterKanriInfo(
		Connection connection,
		String     masterShubetu)
		throws NoDataFoundException, DataAccessException
	{
		//-----------------------
		// SQL文の作成
		//-----------------------
		String query = "SELECT " 
					  + "A.MASTER_SHUBETU, "				//マスタ種別
					  + "A.MASTER_NAME, "					//マスタ名称
					  + "A.IMPORT_DATE, "					//取り込み日時
					  + "A.KENSU, "							//件数
					  + "A.IMPORT_TABLE, "					//取り込みテーブル名
					  + "A.IMPORT_FLG, "					//新規・更新フラグ
					  + "A.IMPORT_MSG, "					//処理状況
					  + "A.CSV_PATH "						//CSVファイルパス
					  + "FROM MASTER_INFO A"
					  + " WHERE MASTER_SHUBETU = ?";						  								

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// マスタ管理情報取得
		//-----------------------
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			MasterKanriInfo result = new MasterKanriInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, masterShubetu);
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setMasterShubetu(recordSet.getString("MASTER_SHUBETU"));
				result.setMasterName(recordSet.getString("MASTER_NAME"));
				result.setImportDate(recordSet.getDate("IMPORT_DATE"));
				result.setKensu(recordSet.getString("KENSU"));
				result.setImportTable(recordSet.getString("IMPORT_TABLE"));
				result.setImportFlg(recordSet.getString("IMPORT_FLG"));
				result.setImportMsg(recordSet.getString("IMPORT_MSG"));
				result.setCsvPath(recordSet.getString("CSV_PATH"));
				return result;
			} else {
				throw new NoDataFoundException(
					"マスタ管理情報テーブルに該当するデータが見つかりません。検索キー：マスタ種別：'"
						+ masterShubetu
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("マスタ管理情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	/**
	 * マスタ管理情報の一覧を取得する。
	 * @param	connection			コネクション
	 * @return						マスタ管理情報
	 * @throws ApplicationException
	 */
	public static List selectList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select = "SELECT A.MASTER_SHUBETU, "										//マスタ種別
					  + "A.MASTER_NAME, "												//マスタ名称
					  + "TO_CHAR(A.IMPORT_DATE, 'YYYY/MM/DD HH24:MI:SS') IMPORT_DATE, "	//取り込み日時
					  + "A.KENSU, "														//件数
					  + "A.IMPORT_TABLE, "												//取り込みテーブル名
					  + "A.IMPORT_FLG, "												//新規・更新フラグ
					  + "A.IMPORT_MSG, "												//処理状況
					  + "A.CSV_PATH "													//CSVファイルパス
					  + "FROM MASTER_INFO A"
					  + " ORDER BY TO_NUMBER(MASTER_SHUBETU)";						  								

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
				"マスタ管理情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"マスタ管理マスタに1件もデータがありません。",
				e);
		}
	}



	/**
	 * マスタ管理テーブルの情報を更新する。
	 * IMPORT_DATEについては、データベースのシステム日時で更新する。
	 * @param connection			コネクション
	 * @param dataInfo				更新するマスタ管理情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void update(
		Connection connection,
		MasterKanriInfo dataInfo)
		throws DataAccessException, NoDataFoundException {
	
			String query = "UPDATE MASTER_INFO"
						 + " SET"
						 + " MASTER_SHUBETU = ?"
						 + ",MASTER_NAME = ?"
						 + ",IMPORT_DATE = SYSDATE"
						 + ",KENSU = ?"
						 + ",IMPORT_TABLE = ?"
						 + ",IMPORT_FLG = ?"
						 + ",IMPORT_MSG = ?"
						 + ",CSV_PATH = ?"
						 + " WHERE"
						 + " MASTER_SHUBETU = ? "
						 ;
			
			if(log.isDebugEnabled()){
				log.debug("query:" + query);
			}
			
			PreparedStatement preparedStatement = null;
			try {
				//マスタ管理テーブル更新
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getMasterShubetu());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getMasterName());
				//DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getImportDate());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getKensu());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getImportTable());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getImportFlg());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getImportMsg());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getCsvPath());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getMasterShubetu());
				DatabaseUtil.executeUpdate(preparedStatement);

			} catch (SQLException ex) {
				throw new DataAccessException("マスタ管理テーブル更新中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}

	
	
	/**
	 * 指定テーブルの全レコードを物理削除する。
	 * @param connection			コネクション
	 * @param table_name			削除する申請者情報主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteMaster(
		Connection connection,
		String table_name)
		throws DataAccessException, NoDataFoundException {
	
			String query = "DELETE FROM " + table_name;
			
			if(log.isDebugEnabled()){
				log.debug("query:" + query);
			}
						
			PreparedStatement preparedStatement = null;
			try {
				//削除
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.executeUpdate();

			} catch (SQLException ex) {
				throw new DataAccessException(table_name+"レコード削除中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	
	
	
	/**
	 * 指定テーブルの全レコードを物理削除する。
	 * @param connection			コネクション
	 * @param table_name			削除する申請者情報主キー情報
	 * @param _query               問い合わせ条件
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteMaster(
		Connection connection,
		String table_name,
		String _query)
		throws DataAccessException, NoDataFoundException {
	
			String query = "DELETE FROM " + table_name + " " + _query;
			
			if(log.isDebugEnabled()){
				log.debug("query:" + query);
			}
			
			PreparedStatement preparedStatement = null;
			try {
				//削除
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.executeUpdate();

			} catch (SQLException ex) {
				throw new DataAccessException(table_name+"レコード削除中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	
	/**
	 * 研究者名簿マスタ更新日を取得する
	 * @param connection
	 * @return 「YYYYMMDD」形式の更新日
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public String selectMeiboUpdateDate(Connection connection)
			throws NoDataFoundException, DataAccessException
	{
		
		//-----------------------
		// SQL文の作成
		//-----------------------
		String query = "SELECT" 
					  + " TO_CHAR(MASTER_DATE, 'YYYYMMDD') MASTER_DATE"		//マスタ更新日
					  + " FROM MASTER_INFO"
					  + " WHERE MASTER_SHUBETU = '7'";						  								

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// マスタ管理情報取得
		//-----------------------
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				return recordSet.getString("MASTER_DATE");
			} else {
				throw new NoDataFoundException(
					"マスタ管理情報テーブルに研究者マスタのデータが見つかりません。");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("マスタ管理情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
}