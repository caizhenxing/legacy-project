/*======================================================================
 *    SYSTEM      : 
 *    Source name : IIkeninfoMaintenance.java
 *    Description : 意見・要望情報に更新処理実装クラス
 *
 *    Author      : Admin
 *    Date        : 2005/05/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *　　2005/05/20    1.0         Xiang Emin     新規作成
 *
 *====================================================================== 
 */package jp.go.jsps.kaken.model.impl;

import java.sql.Connection;
import java.util.List;
//import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.go.jsps.kaken.model.IIkeninfoMaintenance;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.impl.IkenInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.IkenInfo;
import jp.go.jsps.kaken.model.vo.IkenSearchInfo;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;

/**
 * @author user1
 * 
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。 ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞
 * コード・テンプレート
 */
public class IkeninfoMaintenance implements IIkeninfoMaintenance {
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(ShinseiMaintenance.class);

	/** システム受付番号取得リトライ回数 */
	protected static final int SYSTEM_NO_MAX_RETRY_COUNT = 
					ApplicationSettings.getInt(ISettingKeys.SYSTEM_NO_MAX_RETRY_COUNT);

	/**
	 *  
	 */
	public IkeninfoMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------

	/*
	 * ご意見データ新規登録処理
	 * 
	 * @see jp.go.jsps.kaken.model.IIkeninfoMaintenance#insert(jp.go.jsps.kaken.model.vo.IkenInfo)
	 */
	public void insert(IkenInfo addInfo) throws ApplicationException
	{

		Connection connection = null;
		boolean success = false;

		if ( log.isDebugEnabled() ){
			log.debug("ご意見情報登録開始");
		}
		
		//--------------------
		// ご意見・要望データ登録
		//--------------------
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();

			IkenInfoDao dao = new IkenInfoDao();
			//-- 登録時にキーが重なった場合はリトライをかける --
			int count = 0;
			while (true) {
				try {
					dao.insertIkenInfo(connection, addInfo);
					success = true;
					break;
				} catch (DuplicateKeyException e) {
					count++;
					if (count < SYSTEM_NO_MAX_RETRY_COUNT) {
						if ( log.isDebugEnabled() ){
							log.debug("ご意見情報登録に第" + count + "回失敗しました。");
						}
						//dataInfo.setSystemNo(getSystemNumber());
						// //システム受付番号を再取得
						continue;
					} else {
						throw e;
					}
				}
			}
		}
		catch (DataAccessException e) {

			throw new ApplicationException("ご意見・要望情報登録中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"), e);
		}
		finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("ご意見・要望情報登録中にDBエラーが発生しました。",
						new ErrorInfo("errors.4001"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}

	}

	/**
	 * 意見情報を検索する。
	 * @param searchInfo           意見検索条件情報
	 * @return						取得した申請情報ページオブジェクト
	 * @throws ApplicationException	
	 * @throws NoDataFoundException		対象データが見つからない場合の例外。
	 */
	public Page searchIken(IkenSearchInfo searchInfo) 
		throws NoDataFoundException, ApplicationException
	{
		//DBコネクションの取得
		Connection connection = null;	
		try{
			connection = DatabaseUtil.getConnection();
			
			//---申請書一覧ページ情報
			Page pageInfo = null;
			try {
				IkenInfoDao dao = new IkenInfoDao();
				pageInfo = dao.searchIkenInfo(connection, searchInfo);	//該当レコードを全件取得
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"ご意見情報検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
		
			return pageInfo;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
	}
	
	/**
	 * 意見情報を抽出する。
	 * @param system_no システム受付番号
	 * @param taisho_id 対象者ＩＤ
	 * @return IkenInfo 意見情報
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public IkenInfo selectIkenDataInfo(String system_no, String taisho_id)
    	throws NoDataFoundException, ApplicationException
	{

		//DBコネクションの取得
		Connection connection = DatabaseUtil.getConnection();
		
		try{
			//---意見内容情報
			IkenInfo ikenDataInfo = null;

			try {
				IkenInfoDao dao = new IkenInfoDao();
				ikenDataInfo = dao.getIkenInfo(connection, system_no, taisho_id);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"意見内容情報検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
	
			return ikenDataInfo;
			
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
	}

	/**
	 * CSV出力用の意見情報を検索する。
	 * 
	 * @param searchInfo			検索する条件情報。
	 * @return						取得した意見情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public List searchCsvData(IkenSearchInfo searchInfo)
			throws ApplicationException
	{

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			 "SELECT "
				+ "  SYSTEM_NO \"システム受付番号\""
				+ ", TO_CHAR(SAKUSEI_DATE,'YYYY/MM/DD') \"投稿日\""
				//+ ", TAISHO_ID \"対象者\"" 2005/6/29修正
				+ ", DECODE(TAISHO_ID,1,'応募者', 2,'所属研究機関担当者', 4,'審査員', 6,'部局担当者') \"対象者\""
				+ ", IKEN \"ご意見内容\""
				+ ", BIKO \"備考\""
			+ " FROM IKEN_INFO"
			+ " WHERE 1 = 1"
			;

		StringBuffer query = new StringBuffer(select);

		//検索条件を元にSQL文を生成する。
		//投稿日開始
		String tmpDate = searchInfo.getSakuseiDateFrom();
		if ( tmpDate != null && !"".equals( tmpDate )){
			query.append(" AND SAKUSEI_DATE >= TO_DATE('" + EscapeUtil.toSqlString(searchInfo.getSakuseiDateFrom()) + "','YYYY/MM/DD')");
		}
		
		//投稿日終了
		tmpDate = searchInfo.getSakuseiDateTo();
		if ( tmpDate != null && !"".equals(tmpDate)){
			query.append(" AND SAKUSEI_DATE < TO_DATE('" + EscapeUtil.toSqlString(searchInfo.getSakuseiDateTo()) + "','YYYY/MM/DD')+1");
		}
		
		//対象者ＩＤ
		if (!"".equals(searchInfo.getShinseisya()) || !"".equals(searchInfo.getSyozoku()) ||
			!"".equals(searchInfo.getBukyoku()) || !"".equals(searchInfo.getShinsyain()) )
		{

			query.append(" AND TAISHO_ID IN (");

			int flg = 0;
			if (!"".equals(searchInfo.getShinseisya())){
				query.append( "1" );
				flg = 1;
			}
			
			if (!"".equals(searchInfo.getSyozoku())){
				if (flg != 0){
					query.append( ",2" );
				}else{
					query.append( "2" );
					flg = 1;
				}
			}

			if (!"".equals(searchInfo.getBukyoku())){
				if (flg != 0){
					query.append( ",6" );
				}else{
					query.append( "6" );
					flg = 1;
				}
			}

			if (!"".equals(searchInfo.getShinsyain())){
				if (flg != 0){
					query.append( ",4" );
				}else{
					query.append( "4" );
					flg = 1;
				}
			}
			query.append( ")" );
		}
		
		
		//ソート順を生成する。
		if ( "1".equals(searchInfo.getDispmode()) ){
			query.append( " ORDER BY TAISHO_ID, SAKUSEI_DATE" );
		}else{
			query.append( " ORDER BY SAKUSEI_DATE, TAISHO_ID" );
		}
	
		if(log.isDebugEnabled()){
			log.debug("意見query:" + query);
		}

		//-----------------------
		// リスト取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectCsvList(connection, query.toString(), true);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"意見情報CSV出力データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4008"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
}