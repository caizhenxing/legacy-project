/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/07/04
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.impl.LabelValueMaintenance;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ラベルマスタマスタデータアクセスクラス。
 * ID RCSfile="$RCSfile: MasterLabelInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class MasterLabelInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(MasterLabelInfoDao.class);

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
	 * @param userInfo		実行するユーザ情報
	 */
	public MasterLabelInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	
	/**
	 * ラベルマスタの１レコードをMap形式で返す。
	 * 引数には主キー値を渡す。
	 * @param connection
	 * @param labelKubun
	 * @param value
	 * @return
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static Map selectRecord(Connection connection, String labelKubun, String value)
		throws NoDataFoundException, DataAccessException
	{
		//-----------------------
		// SQL文の作成
		//-----------------------
		StringBuffer query = new StringBuffer("SELECT");
		query.append("  A.ATAI");
		query.append(", A.NAME");
		query.append(", A.RYAKU");
		query.append(", A.SORT");
		query.append(", A.BIKO");
		query.append(" FROM MASTER_LABEL A");
		query.append(" WHERE A.LABEL_KUBUN = ? AND A.ATAI = ?");
		query.append(" ORDER BY SORT");

		//2005.10.26 iso たいした情報が出ないのでコメント化
//		if(log.isDebugEnabled()){
//			log.debug("query:" + query);
//		}
		
		//-----------------------
		// レコード取得
		//-----------------------
		List result = SelectUtil.select(connection,
										query.toString(), 
										new String[]{labelKubun,value});
		if(result.isEmpty()){
			String msg = "当該レコードは存在しません。ラベル区分="+labelKubun+", 値="+ value;
			throw new NoDataFoundException(msg);
		}
		return (Map)result.get(0);
		
	}
	
	/**
	 * ラベル情報の一覧を取得する。（プルダウン）
	 * @param	connection			コネクション
	 * @param	labelKubun			ラベル区分情報
	 * @return						ラベル情報
	 * @throws ApplicationException
	 */
	public static List selectLabelInfoList(Connection connection, String labelKubun)
		throws ApplicationException {
		return selectLabelInfoList(connection, labelKubun, LabelValueMaintenance.FLAG_KENSAKU_LIST);
	}


	/**
	 * ラベル情報の一覧を取得する。（全部）
	 * @param	connection			コネクション
	 * @param	labelKubun			ラベル区分情報
	 * @return						ラベル情報
	 * @throws ApplicationException
	 */
	public static List selectAllLabelInfoList(Connection connection, String labelKubun)
		throws ApplicationException {
		return selectLabelInfoList(connection, labelKubun, LabelValueMaintenance.FLAG_KENSAKU_ALL);
	}

	
	/**
	 * ラベル情報の一覧を取得する。
	 * @param	connection			コネクション
	 * @param	labelKubun			ラベル区分情報
	 * @param	kensakuFlg			検索フラグ。
	 * @return						ラベル情報
	 * @throws ApplicationException
	 */
	public static List selectLabelInfoList(Connection connection, String labelKubun, String kensakuFlg)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		StringBuffer query = new StringBuffer("SELECT");
		query.append("  A.ATAI");
		query.append(", A.NAME");
		query.append(", A.RYAKU");
		query.append(", A.SORT");
		query.append(", A.BIKO");
		query.append(" FROM MASTER_LABEL A");
		query.append(" WHERE A.LABEL_KUBUN = ?");
		if((LabelValueMaintenance.FLAG_KENSAKU_LIST).equals(kensakuFlg)){
			query.append(" AND A.SORT != 0");	
		}
		query.append(" ORDER BY SORT");

		//2005.10.26 iso たいした情報が出ないのでコメント化
//		if(log.isDebugEnabled()){
//			log.debug("query:" + query);
//		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString(), new String[]{labelKubun});
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"ラベル情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw e;
		}
	}

}
