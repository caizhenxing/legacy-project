/*======================================================================
 *    SYSTEM      : 
 *    Source name : IkenInfo.java
 *    Description : 意見・要望情報をＤＢアクセスクラス
 *
 *    Author      : Admin
 *    Date        : 2005/05/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *　　2005/05/20    1.0         Xiang Emin     新規作成
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
//import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.IkenSearchInfo;
import jp.go.jsps.kaken.model.vo.SearchInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.go.jsps.kaken.model.vo.IkenInfo;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;

/**
 * @author user1
 *
 */
public class IkenInfoDao {

	/** ログ */
	protected static final Log log = LogFactory.getLog(IkenInfoDao.class);
	
	/** 意見内容  */
	private IkenInfo iken = null;
	
	
	/**
	 * コンストラクタ。
	 */
	public IkenInfoDao( ) {
	}

	
	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	
	/**
	 * 意見内容登録
	 * @param connection
	 * @param addInfo
	 * @throws DataAccessException
	 * @throws DuplicateKeyException
	 */
	public void insertIkenInfo(
			Connection connection,
			IkenInfo addInfo)
			throws DataAccessException, DuplicateKeyException {
		
		String query =
			"INSERT INTO IKEN_INFO ("
				+ "  SYSTEM_NO"
				+ ", SAKUSEI_DATE"
				+ ", TAISHO_ID"
				+ ", IKEN"
				+ ", BIKO"
				+ ") VALUES ("
				+ " TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSFF3')"
				+ ", SYSDATE, ?, ?, ? )";
				/*+ ", SYSDATE"
				+ "," + addInfo.getTaisho_id() 
				+ ", '" + addInfo.getIken() + "'"
				+ ", null )";*/
		
		PreparedStatement preparedStatement = null;
		try {
			if (log.isDebugEnabled()){
				log.debug("ご意見サイズ：" + addInfo.getIken().length());
			}
			
			//登録
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTaisho_id());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getIken());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("ご意見・ご要望情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * 指定検索条件に該当する意見データを取得する。
	 * @param connection
	 * @param searchInfo
	 * @return
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 * @throws ApplicationException  申請者情報がセットされていなかった場合
	 */
	public Page searchIkenInfo(
		Connection connection,
		IkenSearchInfo searchInfo)
		throws DataAccessException , NoDataFoundException, ApplicationException
	{

		String select =	"SELECT SYSTEM_NO, TAISHO_ID"
						+ ", DECODE(TAISHO_ID,1,'応募者', 2,'所属研究機関担当者', 4,'審査員', 6,'部局担当者') TAISHO_NM"
						+ ", TO_CHAR(SAKUSEI_DATE,'YYYY\"年\"MM\"月\"DD\"日\"') SAKUSEI_DATE"
						//+ ", IKEN"
						+ " FROM IKEN_INFO";
		String where = "";
		
		//投稿日開始
		String tmpDate = searchInfo.getSakuseiDateFrom();
		if ( tmpDate != null && !"".equals( tmpDate )){
			where = " WHERE SAKUSEI_DATE >= TO_DATE('" + EscapeUtil.toSqlString(searchInfo.getSakuseiDateFrom()) + "','YYYY/MM/DD')";
		}
		
		//投稿日終了
		tmpDate = searchInfo.getSakuseiDateTo();
		if ( tmpDate != null && !"".equals(tmpDate)){
			if ("".equals(where)){
				where = " WHERE";
			}else{
				where = where + " AND";
			}
			where = where + " SAKUSEI_DATE < TO_DATE('" + EscapeUtil.toSqlString(searchInfo.getSakuseiDateTo()) + "','YYYY/MM/DD')+1";
		}
		
		//対象者ＩＤ
		if (!"".equals(searchInfo.getShinseisya()) || !"".equals(searchInfo.getSyozoku()) ||
			!"".equals(searchInfo.getBukyoku()) || !"".equals(searchInfo.getShinsyain()) ){
			if ("".equals(where)){
				where = " WHERE TAISHO_ID IN (";
			}else{
				where = where + " AND TAISHO_ID IN (";
			}

			int flg = 0;
			if (!"".equals(searchInfo.getShinseisya())){
				where = where + "1";
				flg = 1;
			}
			
			if (!"".equals(searchInfo.getSyozoku())){
				if (flg != 0){
					where = where + ",2";
				}else{
					where = where + "2";
					flg = 1;
				}
			}

			if (!"".equals(searchInfo.getBukyoku())){
				if (flg != 0){
					where = where + ",6";
				}else{
					where = where + "6";
					flg = 1;
				}
			}

			if (!"".equals(searchInfo.getShinsyain())){
				if (flg != 0){
					where = where + ",4";
				}else{
					where = where + "4";
					flg = 1;
				}
			}
			where = where + ")";
		}
		
		
		
		//検索条件を元にSQL文を生成する。
		String query = select + where;
		
		if ( "1".equals(searchInfo.getDispmode()) ){
			query = query + " ORDER BY TAISHO_ID, SAKUSEI_DATE";
		}else{
			query = query + " ORDER BY SAKUSEI_DATE, TAISHO_ID";
		}
		
		//for debug
		if(log.isDebugEnabled()){
			log.debug("意見情報query:" + query);
		}
		
		// ページ取得
		return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query);
	}

	/**
	 * 指定検索条件に該当する意見データを取得する。
	 * @param connection
	 * @param searchInfo
	 * @return
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 * @throws ApplicationException  申請者情報がセットされていなかった場合
	 */
	public IkenInfo getIkenInfo(
		Connection connection,
		String system_no,
		String taisho_id)
		throws DataAccessException , NoDataFoundException, ApplicationException
	{

		String select =	" SELECT TO_CHAR(SAKUSEI_DATE,'YYYY\"年\"MM\"月\"DD\"日\"') SAKUSEI_DATE"
						+ "    , DECODE(TAISHO_ID,1,'応募者', 2,'所属研究機関担当者', 4,'審査員', 6,'部局担当者') TAISHO_NM"
						+ "    , IKEN"
						+ " FROM IKEN_INFO "
						+ "WHERE SYSTEM_NO = '" + EscapeUtil.toSqlString(system_no) + "'"
						+ "  AND TAISHO_ID = " + EscapeUtil.toSqlString(taisho_id) ;
		
		//for debug
		if(log.isDebugEnabled()){
			log.debug("意見情報query:" + select);
		}

		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			IkenInfo result = new IkenInfo();
			preparedStatement = connection.prepareStatement(select);
			int index = 1;
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				//---基本情報（前半）
				result.setSakusei_date(recordSet.getString("SAKUSEI_DATE"));
				result.setTaisho_nm(recordSet.getString("TAISHO_NM"));
				result.setIken(recordSet.getString("IKEN"));
				result.setSystem_no(system_no);

				return result;
				
			} else {
				throw new NoDataFoundException(
					"意見内容テーブルに該当するデータが見つかりません。検索キー：システム受付番号'"
						+ system_no 
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("意見内容テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		
	}	
}
