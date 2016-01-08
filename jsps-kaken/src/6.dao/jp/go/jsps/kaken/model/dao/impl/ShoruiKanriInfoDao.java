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
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
import jp.go.jsps.kaken.model.vo.ShoruiKanriPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 書類管理情報データアクセスクラス。
 * ID RCSfile="$RCSfile: ShoruiKanriInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class ShoruiKanriInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(ShoruiKanriInfoDao.class);

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
	public ShoruiKanriInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * 書類管理情報を取得する。削除フラグが「0」の場合のみ検索する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						書類管理情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public ShoruiKanriInfo selectShoruiKanriInfo(
		Connection connection,
		ShoruiKanriPk primaryKeys)
		throws DataAccessException, NoDataFoundException {
		String query =
			"SELECT "
				+ " A.JIGYO_ID"				//事業ID
				+ ",A.TAISHO_ID"			//対象
				+ ",A.SYSTEM_NO"			//システム受付番号
				+ ",A.SHORUI_FILE"			//格納先ディレクトリ
				+ ",A.SHORUI_NAME"			//書類名
				+ ",A.DEL_FLG"				//削除フラグ
				+ " FROM SHORUIKANRI A"
				+ " WHERE DEL_FLG = 0"; 
		StringBuffer buffer = new StringBuffer(query);
			
		if(primaryKeys.getJigyoId() != null && primaryKeys.getJigyoId().length() != 0){	//事業ID
			buffer.append("AND JIGYO_ID = ?");					
		}
		if(primaryKeys.getSystemNo() != null && primaryKeys.getSystemNo().length() != 0){	//システム番号
			buffer.append("AND SYSTEM_NO = ?");							
		}

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShoruiKanriInfo result = new ShoruiKanriInfo();
			preparedStatement = connection.prepareStatement(buffer.toString());
			int i = 1;
			if(primaryKeys.getJigyoId() != null && primaryKeys.getJigyoId().length() != 0){	//事業ID
				preparedStatement.setString(i++, primaryKeys.getJigyoId());					
			}
			if(primaryKeys.getSystemNo() != null && primaryKeys.getSystemNo().length() != 0){	//システム受付番号
				preparedStatement.setString(i++, primaryKeys.getSystemNo());				
			}
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setJigyoId(recordSet.getString("JIGYO_ID"));			//事業ID
				result.setTaishoId(recordSet.getString("TAISHO_ID"));		//対象
				result.setSystemNo(recordSet.getString("SYSTEM_NO"));		//システム受付番号
				result.setShoruiFile(recordSet.getString("SHORUI_FILE"));	//格納先ディレクトリ
				result.setShoruiName(recordSet.getString("SHORUI_NAME"));	//書類名
				result.setDelFlg(recordSet.getString("DEL_FLG"));			//削除フラグ
				return result;
			} else {
				throw new NoDataFoundException(
					"書類管理情報テーブルに該当するデータが見つかりません。検索キー：事業ID'"
						+ primaryKeys.getJigyoId()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("書類管理情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}




	/**
	 * 書類管理情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する書類管理情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertShoruiKanriInfo(
		Connection connection,
		ShoruiKanriInfo addInfo)
		throws DataAccessException, DuplicateKeyException {
			
		//重複チェック
		try {
			selectShoruiKanriInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO SHORUIKANRI "
				+ "(JIGYO_ID"		//事業ID
				+ ",TAISHO_ID"		//対象
				+ ",SYSTEM_NO"		//システム受付番号
				+ ",SHORUI_FILE"	//格納先ディレクトリ
				+ ",SHORUI_NAME"	//書類名
				+ ",DEL_FLG) "		//削除フラグ
				+ "VALUES "
				+ "(?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoId());		//事業ID
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTaishoId());		//対象
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSystemNo());		//システム受付番号
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShoruiFile());	//格納先ディレクトリ
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShoruiName());	//書類名	
			DatabaseUtil.setParameter(preparedStatement, i++, 0);						//削除フラグ
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("書類管理情報登録中に例外が発生しました。 ", ex);
			throw new DataAccessException("書類管理情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * 書類管理情報を削除する。(削除フラグ) 
	 * @param connection			コネクション
	 * @param addInfo				削除する書類管理情報主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteFlgShoruiKanriInfo(
		Connection connection,
		ShoruiKanriPk deleteInfo)
		throws DataAccessException, NoDataFoundException {

		//検索
		selectShoruiKanriInfo(connection, deleteInfo);
		
		String query =
			"UPDATE SHORUIKANRI"
				+ " SET"
				+ " DEL_FLG = 1";//削除フラグ				
				
		StringBuffer buffer = new StringBuffer(query);
				
		if(deleteInfo.getJigyoId() != null && deleteInfo.getJigyoId().length() != 0){			//事業ID
			buffer.append("WHERE JIGYO_ID = ?");		
		}else if(deleteInfo.getSystemNo() != null && deleteInfo.getSystemNo().length() != 0){	//システム番号	
			buffer.append("WHERE SYSTEM_NO = ?");	
		}

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(buffer.toString());
			int i = 1;
			if(deleteInfo.getJigyoId() != null && deleteInfo.getJigyoId().length() != 0){
				preparedStatement.setString(i++, deleteInfo.getJigyoId());		//事業ID
				int count = preparedStatement.executeUpdate();

				if(log.isDebugEnabled()){
					log.debug(count + "件の書類管理情報の削除に成功しました。");
				}

			}else if(deleteInfo.getSystemNo() != null && deleteInfo.getSystemNo().length() != 0){
				DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getSystemNo());		//システム番号				
				DatabaseUtil.executeUpdate(preparedStatement);
			}
		} catch (SQLException ex) {

			throw new DataAccessException("書類管理情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	
	/**
	 * 書類管理情報を削除する。(物理削除) 
	 * @param connection			コネクション
	 * @param addInfo				削除する書類管理情報主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteJigyoKanriInfo(
		Connection connection,
		ShoruiKanriPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
	}
	




}
