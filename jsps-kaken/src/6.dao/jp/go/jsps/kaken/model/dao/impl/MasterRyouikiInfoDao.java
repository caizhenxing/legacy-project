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

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.EscapeUtil;

import org.apache.commons.logging.*;

/**
 * 領域マスタデータアクセスクラス。
 * ID RCSfile="$RCSfile: MasterRyouikiInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public class MasterRyouikiInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(MasterRyouikiInfoDao.class);

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
	public MasterRyouikiInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * 領域の一覧(コンポボックス用)を取得する。
	 * @param	connection			コネクション
	 * @return						事業情報
	 * @throws ApplicationException
	 */
	public static List selectRyouikiKubunInfoList(Connection connection)
		throws ApplicationException,NoDataFoundException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.RYOIKI_NO"
			+ ",A.RYOIKI_RYAKU"
			+ " FROM MASTER_RYOIKI A"
			+ " ORDER BY RYOIKI_NO";		
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
				"領域情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new NoDataFoundException(
				"領域マスタに1件もデータがありません。",
				e);
		}
	}
	

	/**
	 * 領域マスタの１レコードをMap形式で返す。
	 * 引数には主キー値を渡す。
	 * @param connection
	 * @param labelKubun
	 * @param value
	 * @return
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static Map selectRecord(Connection connection, String ryouikiNo)
		throws NoDataFoundException, DataAccessException
	{
		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.RYOIKI_NO"
			+ ",A.RYOIKI_RYAKU"
			+ ",A.KOMOKU_NO"
//2006/06/26 苗　修正ここから
            + ",A.SETTEI_KIKAN"  //設定期間
            + ",A.SETTEI_KIKAN_KAISHI" //設定期間（開始年度）
            + ",A.SETTEI_KIKAN_SHURYO" //設定期間（終了年度）
//2006/06/26 苗　修正ここまで            
			+ ",A.BIKO"
			+ " FROM MASTER_RYOIKI A"
			+ " WHERE RYOIKI_NO = ? "
			;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + select);
		}
		
		//-----------------------
		// レコード取得
		//-----------------------
		List result = SelectUtil.select(connection,
										select, 
										new String[]{ryouikiNo});
		if(result.isEmpty()){
			throw new NoDataFoundException(
				"当該レコードは存在しません。領域No="+ryouikiNo);
		}
		return (Map)result.get(0);
		
	}

	/**
	 * 領域マスタの１レコードをMap形式で返す。
	 * 引数には主キー値を渡す。
	 * @param connection
	 * @param labelKubun
	 * @param value
	 * @return
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static Map selectRecord(Connection connection, RyouikiInfoPk pkInfo)
		throws NoDataFoundException, DataAccessException
	{
		return selectRecord(connection, pkInfo, "0");
	}
	
	/**
	 * 領域マスタの１レコードをMap形式で返す。
	 * 引数には主キー値を渡す。
	 * @param connection
	 * @param labelKubun
	 * @param value
	 * @return
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static Map selectRecord(Connection connection, RyouikiInfoPk pkInfo, String ryoikiKbn)
		throws NoDataFoundException, DataAccessException
	{
		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.RYOIKI_NO"
			+ ",A.RYOIKI_RYAKU"
			+ ",A.KOMOKU_NO"
//2006/07/04 苗　修正ここから
            + ",A.SETTEI_KIKAN"  //設定期間
            + ",A.SETTEI_KIKAN_KAISHI" //設定期間（開始年度）
            + ",A.SETTEI_KIKAN_SHURYO" //設定期間（終了年度）
//2006/07/04 苗　修正ここまで              
			+ " FROM MASTER_RYOIKI A"
			+ " WHERE RYOIKI_NO = ? "
			+ " AND KOMOKU_NO = ? "
			;

		//計画研究の場合
		if ("1".equals(ryoikiKbn)){
			select = select + " AND KEIKAKU_FLG = '1'";
		}
		//公募研究の場合
		else if ("2".equals(ryoikiKbn)){
			select = select + " AND KOUBO_FLG = '1'";
		}
		
		
		if(log.isDebugEnabled()){
			log.debug("query:" + select);
		}
		
		//-----------------------
		// レコード取得
		//-----------------------
		List result = SelectUtil.select(connection,
										select, 
										new String[]{pkInfo.getRyoikiNo(), pkInfo.getKomokuNo() });
		if(result.isEmpty()){
			throw new NoDataFoundException("当該レコードは存在しません。");
		}
		return (Map)result.get(0);
		
	}
	
	/**
	 * 領域情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録するキーワード情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertRyoikiInfo(
		Connection connection,
		RyouikiInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//重複チェック
		try {
			selectRecord(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO MASTER_RYOIKI "
				+ "("
				+ " RYOIKI_NO"			//領域番号
				+ ",RYOIKI_RYAKU"		//領域略称名
				+ ",KOMOKU_NO"			//研究項目番号
				+ ",KOUBO_FLG"			//公募フラグ
				+ ",KEIKAKU_FLG"		//計画研究フラグ
                //add start liuyi 2006/06/30
                + ",ZENNENDO_OUBO_FLG"  //前年度応募フラグ
                + ",SETTEI_KIKAN_KAISHI"//設定期間（開始年度）
                + ",SETTEI_KIKAN_SHURYO"//設定期間（終了年度）
                + ",SETTEI_KIKAN"       //設定期間
                //add end liuyi 2006/06/30
				+ ",BIKO"				//備考
				+ ")"
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRyoikiNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRyoikiName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKomokuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKobou());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKeikaku());
            //add start liuyi 2006/06/30
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getZennendoOuboFlg());
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSettelKikanKaishi());
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSettelKikanShuryo());
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSettelKikan());
            //add end liuyi 2006/06/30
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());

			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("領域マスタ情報登録中に例外が発生しました。 ", ex);
			throw new DataAccessException("領域マスタ情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * コード一覧作成用メソッド。<br>
	 * 領域番号と領域名称の一覧を取得する。
	 * 領域番号順にソートする。
	 * @param	connection			コネクション
	 * @return
	 * @throws ApplicationException
	 */
	public static List selectRyoikiInfoList(Connection connection, String kubun)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =	"SELECT"
						+ " RYOIKI_NO,"				//領域番号
						+ " RYOIKI_RYAKU"			//領域名称
						+ " FROM MASTER_RYOIKI";

		if ("1".equals(kubun)){
			select = select + " WHERE KEIKAKU_FLG = '1'";
		}else{
			select = select + " WHERE KOUBO_FLG = '1'";
		}
		select = select	+ " GROUP BY RYOIKI_NO, RYOIKI_RYAKU"
						+ " ORDER BY RYOIKI_NO";								

		if(log.isDebugEnabled()){
			log.debug("query:" + select);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection, select);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"領域情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"領域マスタに1件もデータがありません。",
				e);
		}
	}
//2006/06/26 苗　追加ここから
    /**
     * 領域番号の件数を取得する。
     * 領域番号順にソートする。
     * @param   connection    コネクション
     * @param   ryoikoNo      領域番号      
     * @return
     * @throws ApplicationException
     * @throws DataAccessException 
     */
    public String selectRyoikiNoCount(Connection connection, String ryoikoNo)
        throws ApplicationException, DataAccessException {
        
        String              strCount            =   null;
        ResultSet           recordSet           =   null;
        PreparedStatement   preparedStatement   =   null;
        
        //-----------------------
        // SQL文の作成
        //-----------------------
        
        StringBuffer select =  new StringBuffer();
        select.append("SELECT COUNT(RYOIKI_NO) ");
        select.append(ISystemServise.STR_COUNT);
        select.append(" FROM");
        select.append(" (SELECT MR.RYOIKI_NO FROM MASTER_RYOIKI MR WHERE MR.ZENNENDO_OUBO_FLG = '1' ");
        select.append("  AND MR.RYOIKI_NO = '");
        select.append(EscapeUtil.toSqlString(ryoikoNo));
        select.append("')");                           

        if(log.isDebugEnabled()){
            log.debug("query:" + select.toString());
        }
        try{
            preparedStatement = connection.prepareStatement(select.toString());
            recordSet = preparedStatement.executeQuery();
            
            if(recordSet.next()){
                strCount =recordSet.getString(ISystemServise.STR_COUNT);
            }else{
                throw new NoDataFoundException("領域マスタデータテーブルに該当するデータが見つかりません。");
            }
        }catch (SQLException ex) {
            throw new DataAccessException("領域マスタデータテーブルの検索中に例外が発生しました。",ex);
        } catch(NoDataFoundException ex){
            throw new NoDataFoundException("該当する領域番号が存在しません。",     ex);
        }   
 
        return strCount;    
    }
//2006/06/26　苗　追加ここまで    
//2006/07/24　苗　追加ここから
    /**
     * コード一覧（新規領域）作成用メソッド。<br>
     * 領域番号と領域名称の一覧を取得する。
     * 領域番号順にソートする。
     * @param   connection          コネクション
     * @return　　List
     * @throws ApplicationException
     */
    public static List selectRyoikiSinnkiInfoList(Connection connection)
        throws ApplicationException {

        //-----------------------
        // SQL文の作成
        //-----------------------
        StringBuffer select = new StringBuffer();
            
        select.append("SELECT DISTINCT");
        select.append(" RYOIKI_NO,");//領域番号
        select.append(" RYOIKI_RYAKU,");//領域名称
        select.append(" SETTEI_KIKAN");//設定期間
        select.append(" FROM MASTER_RYOIKI");
        select.append(" WHERE ZENNENDO_OUBO_FLG = '1'");
        select.append(" ORDER BY RYOIKI_NO");

        if(log.isDebugEnabled()){
            log.debug("query:" + select);
        }
        
        //-----------------------
        // リスト取得
        //-----------------------
        try {
            return SelectUtil.select(connection, select.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException(
                "領域情報検索中にDBエラーが発生しました。",
                new ErrorInfo("errors.4004"),
                e);
        } catch (NoDataFoundException e) {
            throw new SystemException(
                "領域マスタに1件もデータがありません。",
                e);
        }
    }
//2006/07/24　苗　追加ここまで    
}
