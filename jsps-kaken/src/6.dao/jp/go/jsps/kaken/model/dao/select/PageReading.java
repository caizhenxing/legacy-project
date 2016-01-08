/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.select;

import java.sql.*;

import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * ページ情報に基づき、データを取得する。
 * 
 * ID RCSfile="$RCSfile: PageReading.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class PageReading{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	/** ログ */
	protected static Log log = LogFactory.getLog(PageReading.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 検索条件オブジェクト */
	private SearchInfo info;

	/** 件数取得用検索SQLステートメント*/
	private PreparedStatementCreator countCerater;

	/** 検索用SQLステートメント*/
	private PreparedStatementCreator dataCreator;
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ
	 * 該当件数MAX値を超えたら例外発生。
	 * @param info		検索条件オブジェクト
	 * @param countCerater		
	 * @param dataCreator		
	 */
	public PageReading(SearchInfo info,PreparedStatementCreator countCerater, PreparedStatementCreator dataCreator) {
		super();
		this.info = info;
		this.countCerater = countCerater;
		this.dataCreator = dataCreator;
	}

	
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
	/**
	 * 検索処理を実行する。
	 * @return	検索結果ページ情報。
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 * @throws RecordCountOutOfBoundsException
	 */
	public final Page search(Connection connection) 
		throws DataAccessException,NoDataFoundException, RecordCountOutOfBoundsException{

		//検索テンプレート
		JDBCReading searchUtil = new JDBCReading();
		try {
			CountFieldCallbackHandler handler = new CountFieldCallbackHandler();
			searchUtil.query(connection, countCerater, handler);
			//総件数の取得
			int totalCount = handler.getCount();
			//データ件数チェック
			if(totalCount == 0){
				throw new NoDataFoundException("条件に一致するデータが見つかりませんでした。");
			}else if(info.getMaxSize()>0 && totalCount>info.getMaxSize()){
				String msg = "該当件数のMAX値を超えました。totalCount="+totalCount+", maxSize="+info.getMaxSize();
				throw new RecordCountOutOfBoundsException(msg);
			}else if(totalCount <= info.getStartPosition()){
				//該当するページが存在しないので、前のページを表示。（開始行デフォルトは0）
				setBeforeStartPosition(totalCount, info.getStartPosition());
			}
			
			//ページの取得
			ListCallbackHandler pageHandler = new ListCallbackHandler();
			searchUtil.query(
				connection,
				dataCreator,
				pageHandler,
				info.getStartPosition(),
				info.getPageSize());

			//ページ情報の設定
			Page pageInfo =
				new Page(
					pageHandler.getResult(),
					info.getStartPosition(),
					info.getPageSize(),
					(int)totalCount);
			return pageInfo;

		} catch (DataAccessException dae) {
			if (log.isDebugEnabled()) {
				log.debug("検索コマンド実行中に例外が発生しました。", dae);
			}
			throw dae;
		}
	}
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
	/**
	 * 検索条件オブジェクトに前のページの開始行を設定する。
	 * 前のページ
	 * @throws NoDataFoundException
	 */
	private void setBeforeStartPosition(int totalCount ,int startPosition)
		 throws NoDataFoundException {
		
		//総件数が開始行より大きくなるまで、繰り返す。
		while(totalCount <= startPosition){
			if(startPosition >= 0){
				this.info.setStartPosition(startPosition - info.getPageSize());
				startPosition = this.info.getStartPosition();
			}else{
				throw new NoDataFoundException("開始行が不正な値です。開始行'" + startPosition+ "'");
			}
		}
	}	
}
