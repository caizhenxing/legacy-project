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
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;

import org.apache.commons.logging.*;

/**
 * 海外分野マスタデータアクセスクラス。
 * ID RCSfile="$RCSfile: MasterKaigaibunyaInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public class MasterKaigaibunyaInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(MasterKaigaibunyaInfoDao.class);

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
	public MasterKaigaibunyaInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * 海外分野情報の一覧を取得する。
	 * @param	connection			コネクション
	 * @return						海外分野情報
	 * @throws ApplicationException
	 */
	public static List selectKaigaibunyaList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.KAIGAIBUNYA_CD"
			+ ",A.KAIGAIBUNYA_NAME"
			+ ",A.KAIGAIBUNYA_NAME_RYAKU"
			+ ",A.BIKO"					//備考
			+ " FROM MASTER_KAIGAIBUNYA A"
			+ " ORDER BY A.KAIGAIBUNYA_CD";								
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
				"海外分野マスタ情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"海外分野マスタに1件もデータがありません。",
				e);
		}
	}
	
	
	
	/**
	 * 海外分野マスタの１レコードをMap形式で返す。
	 * 引数には主キー値を渡す。
	 * @param connection
	 * @param pk
	 * @param value
	 * @return
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static Map selectRecord(Connection connection, String pk)
		throws NoDataFoundException, DataAccessException
	{
		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.KAIGAIBUNYA_CD"
			+ ",A.KAIGAIBUNYA_NAME"
			+ ",A.KAIGAIBUNYA_NAME_RYAKU"
			+ ",A.BIKO"					//備考
			+ " FROM MASTER_KAIGAIBUNYA A"
			+ " WHERE KAIGAIBUNYA_CD = ? "
			+ " ORDER BY A.KAIGAIBUNYA_CD"
			;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + select);
		}
		
		//-----------------------
		// レコード取得
		//-----------------------
		List result = SelectUtil.select(connection,
										select, 
										new String[]{pk});
		if(result.isEmpty()){
			throw new NoDataFoundException(
				"当該レコードは存在しません。海外分野コード="+pk);
		}
		return (Map)result.get(0);
		
	}
	
	
	/**
	 * 海外分野マスタのレコードを取得する。
	 * @param connection
	 * @param pk
	 * @return
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static KaigaiBunyaInfo getKaigaiBunyaInfo(Connection connection, String pk)	 throws NoDataFoundException, DataAccessException{
		Map record=selectRecord(connection,pk);
		if(record==null||record.isEmpty()){
			return null;
		}
		KaigaiBunyaInfo kaigaiBunyaInfo=new KaigaiBunyaInfo();
		kaigaiBunyaInfo.setKaigaibunyaCD((String)record.get("KAIGAIBUNYA_CD"));
		kaigaiBunyaInfo.setKaigaibunyaName((String)record.get("KAIGAIBUNYA_NAME"));
		kaigaiBunyaInfo.setKaigaibunyaNameRyaku((String)record.get("KAIGAIBUNYA_NAME_RYAKU"));
		kaigaiBunyaInfo.setBiko((String)record.get("BIKO"));
		
		return kaigaiBunyaInfo;
	}
	

}
