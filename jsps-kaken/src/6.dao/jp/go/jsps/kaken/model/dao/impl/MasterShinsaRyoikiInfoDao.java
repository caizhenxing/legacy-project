/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : syuu
 *    Date        : 2006/02/15
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import jp.go.jsps.kaken.model.vo.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *　審査領域マスタデータアクセスクラス。
 * ID RCSfile="$RCSfile: MasterShinsaRyoikiInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class MasterShinsaRyoikiInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(MasterShinsaRyoikiInfoDao.class);

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
	public MasterShinsaRyoikiInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
//2007/02/05 苗　削除ここから    使用しない
//    /**
//	 * 審査領域情報の一覧を取得する。
//	 * @param	connection			コネクション
//	 * @return						分科細目情報
//	 * @throws ApplicationException
//	 */
//	public static List selectShinsaRyoikiInfoList(Connection connection)
//			throws ApplicationException {
//
//		//-----------------------
//		// SQL文の作成
//		//-----------------------
//		String select = "SELECT"
//						+ " A.SHINSARYOIKI_CD"		//領域コード
//						+ ",A.SHINSARYOIKI_NAME"		//領域名称
//						+ " FROM MASTER_SHINSARYOIKI A"
//						+ " ORDER BY SHINSARYOIKI_CD"
//						;								
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
//			return SelectUtil.select(connection,query.toString());
//		} catch (DataAccessException e) {
//			throw new ApplicationException(
//				"審査領域情報検索中にDBエラーが発生しました。",
//				new ErrorInfo("errors.4004"),
//				e);
//		} catch (NoDataFoundException e) {
//			throw new SystemException(
//				"審査領域情報マスタに1件もデータがありません。",
//				e);
//		}
//	}
//
//	/**
//	 * 審査領域マスタから、領域コードをもとに領域名称を取得する。
//	 * @param	connection			コネクション
//	 * @param	ryoikiCd			領域コード
//	 * @return						領域名称
//	 * @throws ApplicationException
//	 */
//	public static String selectRyoikiName(Connection connection, String ryoikiCd)
//			throws ApplicationException {
//
//		//-----------------------
//		// SQL文の作成
//		//-----------------------
//		
//		String select = "SELECT"
//						+ " A.SHINSARYOIKI_NAME"					//領域名称
//						+ " FROM MASTER_SHINSARYOIKI A"
//						+ " WHERE A.SHINSARYOIKI_CD = ?"
//						;
//			StringBuffer query = new StringBuffer(select);
//
//		if(log.isDebugEnabled()){
//			log.debug("query:" + query);
//		}
//
//		PreparedStatement preparedStatement = null;
//		try {
//			//取得
//			preparedStatement = connection.prepareStatement(select);
//			preparedStatement.setString(1, ryoikiCd);
//			ResultSet rset = preparedStatement.executeQuery();
//
//			String ryoikiName = "";
//			if(rset.next()){
//				ryoikiName = rset.getString("SHINSARYOIKI_NAME");
//			}
//			
//			return ryoikiName;
//		} catch (SQLException ex) {
//			throw new ApplicationException("領域名取得中に例外が発生しました。", ex);
//			
//		}
//	}
	

//	/**
//	 * 部名細目マスタから、部名細目CDをもとに部名細目情報を取得する。
//	 * @param	connection			コネクション
//	 * @param	saimokuPk			部名細目マスタのプライマリキー
//	 * @return						部名細目情報
//	 * @throws ApplicationException
//	 */
//	public static HashMap selectSaimoku(Connection connection,String saimokuPk)
//		throws ApplicationException {
//
//		//-----------------------
//		// SQL文の作成
//		//-----------------------
//		
//		//Hashimoto_k[2005/03/16 14:04] 分科細目マスタ変更による修正
//		String select =
//			"SELECT"
//			+ " A.BUNKA_NAME"	//分科名
//			+ ",A.SAIMOKU_NAME"	//細目名
//			+ ",A.BU_CD"		//部コード
//			+ ",A.BU_NAME"		//部名
//			+ ",A.RYOIKI1"		//領域1
//			+ ",A.RYOIKI2"		//領域2
//			+ ",A.RYOIKI3"		//領域3
//			+ ",A.RYOIKI4"		//領域4
//			+ ",A.RYOIKI5"		//領域5
//			+ ",A.RYOIKI6"		//領域6
//			+ ",A.RYOIKI7"		//領域7
//			+ ",A.RYOIKI8"		//領域8
//			+ " FROM MASTER_SAIMOKU A"
//			+ " WHERE A.BUNKASAIMOKU_CD = ?";
//			
//			StringBuffer query = new StringBuffer(select);
//
//		if(log.isDebugEnabled()){
//			log.debug("query:" + query);
//		}
//		
//		PreparedStatement preparedStatement = null;
//		try {
//			//取得
//			preparedStatement = connection.prepareStatement(select);
//			preparedStatement.setString(1, saimokuPk);
//			ResultSet rset = preparedStatement.executeQuery();
//
//			HashMap saimokuMap = new HashMap();
//			if(rset.next()){
//				saimokuMap.put("BUNKA_NAME",rset.getString("BUNKA_NAME"));
//				saimokuMap.put("SAIMOKU_NAME",rset.getString("SAIMOKU_NAME"));
//				saimokuMap.put("BU_CD",rset.getString("BU_CD"));
//				saimokuMap.put("BU_NAME",rset.getString("BU_NAME"));
//				saimokuMap.put("RYOIKI1",rset.getString("RYOIKI1"));
//				saimokuMap.put("RYOIKI2",rset.getString("RYOIKI2"));
//				saimokuMap.put("RYOIKI3",rset.getString("RYOIKI3"));
//				saimokuMap.put("RYOIKI4",rset.getString("RYOIKI4"));
//				saimokuMap.put("RYOIKI5",rset.getString("RYOIKI5"));
//				saimokuMap.put("RYOIKI6",rset.getString("RYOIKI6"));
//				saimokuMap.put("RYOIKI7",rset.getString("RYOIKI7"));
//				saimokuMap.put("RYOIKI8",rset.getString("RYOIKI8"));				
//			}
//			
//			return saimokuMap;
//		} catch (SQLException ex) {
//			throw new ApplicationException("部名細目情報取得中に例外が発生しました。 ", ex);
//			
//		}
//	}
//2007/02/05　苗　削除ここまで    
}
