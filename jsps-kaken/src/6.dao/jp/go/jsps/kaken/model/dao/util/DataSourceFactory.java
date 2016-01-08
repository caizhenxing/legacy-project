/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.util;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import oracle.jdbc.pool.*;

import org.apache.commons.logging.*;

/**
 * データソース取得用ファクトリクラス。
 * 
 * ID RCSfile="$RCSfile: DataSourceFactory.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:08:00 $"
 */
public class DataSourceFactory {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ログクラス。 
	 */
	private static final Log log = LogFactory.getLog(DataSourceFactory.class);

	/** 
	 * データソース 
	 */
	private static DataSource dataSource = 
								lookupDataSource(ApplicationSettings.getString(ISettingKeys.DB_DATA_SOURCE_TYPE));

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public DataSourceFactory() {
		super();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * データソースを取得する。
	 * @return	データソース。
	 */
	public static DataSource getDataSouce() {
		return dataSource;
	}
	
	
	/**
	 * データソースを取得する。
	 * dataSourceTypeが「WebLogic」の場合、WebLogic用のデータソースを返す。
	 * @param dataSourceType
	 * @return
	 */
	private static DataSource lookupDataSource(String dataSourceType){
		//WebLogic用
		if("WEBLOGIC".equals(dataSourceType.toUpperCase())){
			return settingDataSource4WebLogic();
		//それ以外
		}else{
			return settingDataSource();
		}
	}
	
	
	
	
	
	/**
	 * 接続文字列のデータソースを取得する。
	 * @param connectURI
	 * @return				データソース
	 */
	private static DataSource settingDataSource() {
		try {
			// Create a OracleConnectionPoolDataSource as an factory
			// of PooledConnections for the Cache to create.
			OracleConnectionPoolDataSource ocpds =
				new OracleConnectionPoolDataSource();
			ocpds.setURL(ApplicationSettings.getString(ISettingKeys.DB_URL));
			ocpds.setUser(ApplicationSettings.getString(ISettingKeys.DB_USER));
			ocpds.setPassword(ApplicationSettings.getString(ISettingKeys.DB_PASSWORD));
			// Associate it with the Cache
			OracleConnectionCacheImpl ods =	new OracleConnectionCacheImpl(ocpds);
			// MINコネクション数の設定
			ods.setMinLimit(ApplicationSettings.getInteger(ISettingKeys.DB_MIN_LIMIT).intValue());
			// MAXコネクション数の設定
			ods.setMaxLimit(ApplicationSettings.getInteger(ISettingKeys.DB_MAX_LIMIT).intValue());
			//　確認間隔			
			ods.setThreadWakeUpInterval(10);
			//未使用の物理接続が保持される最大期間
			//ods.setCacheInactivityTimeout(30);
			ods.setCacheInactivityTimeout(0);	//タイムアウト無し
			// コネクション取得方式の設定
			ods.setCacheScheme(OracleConnectionCacheImpl.FIXED_RETURN_NULL_SCHEME);

			return ods;

		} catch (SQLException e) {
			throw new SystemException("データソースの取得に失敗しました", e);
		}
	}
	
	
	
	/**
	 * 接続文字列のデータソースを取得する。（WebLogic用）
	 * @param connectURI
	 * @return				データソース
	 */
	private static DataSource settingDataSource4WebLogic() {
		try {
			//初期コンテキスト環境セット
			Hashtable ht = new Hashtable();
			ht.put(Context.INITIAL_CONTEXT_FACTORY,
				   ApplicationSettings.getString(ISettingKeys.DB_INITIAL_CONTEXT_FACTORY));
			ht.put(Context.PROVIDER_URL, 
			       ApplicationSettings.getString(ISettingKeys.DB_PROVIDER_URL));
			
			//DataSourceの取得
			Context context = new InitialContext(ht);
			return (DataSource)context.lookup(ApplicationSettings.getString(ISettingKeys.DB_DATA_SOURCE_NAME));

		} catch (Exception e) {
			throw new SystemException("データソースの取得に失敗しました", e);
		}
	}
	
	
	
}
