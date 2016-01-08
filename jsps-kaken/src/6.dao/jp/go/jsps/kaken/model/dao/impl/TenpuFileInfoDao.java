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
import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.TenpuFileInfo;
import jp.go.jsps.kaken.model.vo.TenpuFilePk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 添付ファイル管理テーブルアクセスクラス。
 * ID RCSfile="$RCSfile: TenpuFileInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class TenpuFileInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** 添付ファイル管理テーブルシーケンス名（SEQ_事業ID */
	private static final String SEQ_TENPUFILEINFO = "SEQ_TENPU";
	
	/** 添付ファイル管理テーブルシーケンスの取得桁数（連番用） */
	private static final int SEQ_FIGURE = 1;

	/** ログ */
	protected static final Log log = LogFactory.getLog(TenpuFileInfoDao.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** 実行するユーザ情報 */
	private UserInfo userInfo = null;
	
	/** DBリンク名 */
	private String   dbLink   = "";
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	
	/**
	 * コンストラクタ。
	 * @param userInfo	実行するユーザ情報
	 */
	public TenpuFileInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	
	/**
	 * コンストラクタ。
	 * @param userInfo	実行するユーザ情報
	 * @param dbLink   DBリンク名
	 */
	public TenpuFileInfoDao(UserInfo userInfo, String dbLink) {
		this.userInfo = userInfo;
		this.dbLink   = dbLink;
	}
	
	
	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * 添付ファイル情報を取得する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報（システム受付番号＋シーケンス番号）
	 * @return						所属機関情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public TenpuFileInfo selectTenpuFileInfo(
		Connection connection,
		TenpuFilePk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		String query =
			"SELECT "
				+ " A.SYSTEM_NO,"					//システム受付番号
				+ " A.SEQ_TENPU,"					//シーケンス番号
				+ " A.JIGYO_ID,"					//事業ID
				+ " A.TENPU_PATH,"					//格納パス
				+ " A.PDF_PATH"						//変換ファイル格納パス
				+ " FROM TENPUFILEINFO"+dbLink+" A"
				+ " WHERE"
				+ " SYSTEM_NO = ?"
				+ " AND"
				+ " SEQ_TENPU = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getSystemNo());	//検索条件（システム受付番号）
			DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getSeqTenpu());	//検索条件（シーケンス番号）
			recordSet = preparedStatement.executeQuery();
			
			TenpuFileInfo result = new TenpuFileInfo();
			if (recordSet.next()) {
				result.setSystemNo(recordSet.getString("SYSTEM_NO"));
				result.setSeqTenpu(recordSet.getString("SEQ_TENPU"));
				result.setJigyoId(recordSet.getString("JIGYO_ID"));
				result.setTenpuPath(recordSet.getString("TENPU_PATH"));
				result.setPdfPath(recordSet.getString("PDF_PATH"));
			}else{
				throw new NoDataFoundException(
					"添付ファイル管理テーブルに該当するデータが見つかりません。検索キー：システム受付番号'"
						+ primaryKeys.getSystemNo()
						+ "' 検索キー：シーケンス番号'"
						+ primaryKeys.getSeqTenpu()
						+ "'");
			}
			
			return result;
			
		} catch (SQLException ex) {
			throw new DataAccessException("添付ファイル管理テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}



	/**
	 * 添付ファイル情報を取得する。
	 * システム受付番号に紐づく添付情報を全て返す。
	 * @param connection			コネクション
	 * @param primaryKeys			キー情報（システム受付番号）
	 * @return						所属機関情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public TenpuFileInfo[] selectTenpuFileInfos(
		Connection connection,
		ShinseiDataPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		String query =
			"SELECT "
				+ " A.SYSTEM_NO,"					//システム受付番号
				+ " A.SEQ_TENPU,"					//シーケンス番号
				+ " A.JIGYO_ID,"					//事業ID
				+ " A.TENPU_PATH,"					//格納パス
				+ " A.PDF_PATH"						//変換ファイル格納パス
				+ " FROM TENPUFILEINFO"+dbLink+" A"
				+ " WHERE SYSTEM_NO = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getSystemNo());	//検索条件（システム受付番号）
			recordSet = preparedStatement.executeQuery();
			
			//該当レコードをすべて取得
			List list = new ArrayList();
			while(recordSet.next()){
				TenpuFileInfo result = new TenpuFileInfo();
				result.setSystemNo(recordSet.getString("SYSTEM_NO"));
				result.setSeqTenpu(recordSet.getString("SEQ_TENPU"));
				result.setJigyoId(recordSet.getString("JIGYO_ID"));
				result.setTenpuPath(recordSet.getString("TENPU_PATH"));
				result.setPdfPath(recordSet.getString("PDF_PATH"));
				list.add(result);
			}
			
			//コレクションから配列へ変換
			TenpuFileInfo[] resultArray = (TenpuFileInfo[])list.toArray(new TenpuFileInfo[0]);
			
			//該当レコードが存在しなかった場合
			if(resultArray.length == 0){
				throw new NoDataFoundException(
					"添付ファイル管理テーブルに該当するデータが見つかりません。検索キー：システム受付番号'"
						+ primaryKeys.getSystemNo()
						+ "'");
			}
			
			return resultArray;
			
		} catch (SQLException ex) {
			throw new DataAccessException("添付ファイル管理テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	


	/**
	 * 添付ファイル情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する添付ファイルデータ
	 * @throws DataAccessException		登録中に例外が発生した場合
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合
	 */
	public void insertTenpuFileInfo(
		Connection connection,
		TenpuFileInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//重複チェック
		try {
			selectTenpuFileInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}
		
		String query =
			"INSERT INTO TENPUFILEINFO"+dbLink
				+ " ("
				+ "  SYSTEM_NO,"				//システム受付番号
				+ "  SEQ_TENPU,"				//シーケンス番号
				+ "  JIGYO_ID,"					//事業ID
				+ "  TENPU_PATH,"				//格納パス
				+ "  PDF_PATH"					//PDFパス
				+ " ) "
				+ "VALUES "
				+ "("
				+ "?,?,?,?,?"
				+ ")";		
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement,index++, addInfo.getSystemNo());
			DatabaseUtil.setParameter(preparedStatement,index++, addInfo.getSeqTenpu());
			DatabaseUtil.setParameter(preparedStatement,index++, addInfo.getJigyoId());
			DatabaseUtil.setParameter(preparedStatement,index++, addInfo.getTenpuPath());
			DatabaseUtil.setParameter(preparedStatement,index++, addInfo.getPdfPath());			
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("添付ファイル情報登録中に例外が発生しました。 ", ex);
			throw new DataAccessException("添付ファイル情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}



	/**
	 * 添付ファイル情報を更新する。
	 * @param connection				コネクション
	 * @param updateInfo				更新する添付ファイルデータ
	 * @throws DataAccessException		更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateTenpuFileInfo(
		Connection connection,
		TenpuFileInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
		//検索（更新対象データが存在しなかった場合は例外発生）
		selectTenpuFileInfo(connection, updateInfo);

		String query =
			"UPDATE TENPUFILEINFO"+dbLink
				+ " SET"
				+ " SYSTEM_NO = ?,"				//システム受付番号
				+ " SEQ_TENPU = ?,"				//シーケンス番号
				+ " JIGYO_ID = ?,"				//事業ID
				+ " TENPU_PATH = ?,"			//格納パス	
				+ " PDF_PATH = ?"				//PDF格納パス	
				+ " WHERE"
				+ " SYSTEM_NO = ?"
				+ " AND"
				+ " SEQ_TENPU = ?";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getSystemNo());
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getSeqTenpu());
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getJigyoId());
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getTenpuPath());			
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getPdfPath());			
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getSystemNo());	//検索条件（システム受付番号）
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getSeqTenpu());	//検索条件（シーケンス番号）		
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("添付ファイル情報更新中に例外が発生しました。 ", ex);
			throw new DataAccessException("添付ファイル情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}



	/**
	 * 添付ファイル情報を削除する。
	 * @param connection				コネクション
	 * @param deleteInfo				主キー情報（システム受付番号＋シーケンス番号）
	 * @throws DataAccessException		削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteTenpuFileInfo(
		Connection connection,
		TenpuFilePk deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//検索（削除対象データが存在しなかった場合は例外発生）
		selectTenpuFileInfo(connection, deleteInfo);
		
		String query =
			"DELETE FROM TENPUFILEINFO"+dbLink
				+ " WHERE"
				+ " SYSTEM_NO = ?"
				+ " AND"
				+ " SEQ_TENPU = ?";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, deleteInfo.getSystemNo());	//検索条件（システム受付番号）
			DatabaseUtil.setParameter(preparedStatement, index++, deleteInfo.getSeqTenpu());	//検索条件（シーケンス番号）
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("添付ファイル情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

    
	/**
	 * 添付ファイル情報を削除する。 
	 * システム受付番号に紐づく添付情報を全て削除する。
	 * @param connection				コネクション
	 * @param deleteInfo				キー情報（システム受付番号）
	 * @throws DataAccessException		削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが１件も見つからない場合
	 */
	public void deleteTenpuFileInfos(
		Connection connection,
		ShinseiDataPk deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//検索（削除対象データが存在しなかった場合は例外発生）
		selectTenpuFileInfos(connection, deleteInfo);
		
		String query =
			"DELETE FROM TENPUFILEINFO"+dbLink
				+ " WHERE"
				+ " SYSTEM_NO = ?";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, deleteInfo.getSystemNo());	//検索条件（システム受付番号）
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("添付ファイル情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * 添付ファイル情報を削除する。 
	 * 事業IDに紐づく添付情報を全て削除する。
	 * 該当データが存在しなかった場合は何も処理しない。
	 * @param connection				コネクション
	 * @param jigyoId				　　検索条件（事業ID）
	 * @throws DataAccessException		削除中に例外が発生した場合
	 */
	public void deleteTenpuFileInfos(
		Connection connection,
		String     jigyoId)
		throws DataAccessException
	{
		String query =
			"DELETE FROM TENPUFILEINFO"+dbLink
				+ " WHERE"
				+ " JIGYO_ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, jigyoId);		//検索条件（事業ID）
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("添付ファイル情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * ローカルに存在する該当レコードの内容をDBLink先のテーブルに挿入する。
	 * DBLink先に同じレコードがある場合は、予め削除しておくこと。
	 * DBLinkが設定されていない場合はエラーとなる。
	 * @param connection
	 * @param jigyoId
	 * @throws DataAccessException
	 */
	public void copy2HokanDB(
		Connection connection,
		String     jigyoId)
		throws DataAccessException
	{
		//DBLink名がセットされていない場合
		if(dbLink == null || dbLink.length() == 0){
			throw new DataAccessException("DBリンク名が設定されていません。DBLink="+dbLink);
		}
		
		String query =
			"INSERT INTO TENPUFILEINFO"+dbLink
				+ " SELECT * FROM TENPUFILEINFO WHERE JIGYO_ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, jigyoId);		//検索条件（事業ID）
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("添付ファイル情報保管中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
//2006/06/27 苗　追加ここから
    /**
     * 添付ファイル情報を取得する。
     * システム受付番号に紐づく添付情報を全て返す。
     * @param connection            コネクション
     * @param primaryKeys           キー情報（システム受付番号）
     * @return                      所属機関情報
     * @throws DataAccessException  データ取得中に例外が発生した場合。
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public TenpuFileInfo[] selectTenpuFileInfosForGaiyo(
        Connection connection,
        RyoikiKeikakushoPk primaryKeys)
        throws DataAccessException, NoDataFoundException
    {
        String query =
            "SELECT "
                + " A.SYSTEM_NO,"                   //システム受付番号
                + " A.SEQ_TENPU,"                   //シーケンス番号
                + " A.JIGYO_ID,"                    //事業ID
                + " A.TENPU_PATH,"                  //格納パス
                + " A.PDF_PATH"                     //変換ファイル格納パス
                + " FROM TENPUFILEINFO"+dbLink+" A"
                + " WHERE SYSTEM_NO = ?";
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getRyoikiSystemNo());   //検索条件（システム受付番号）
            recordSet = preparedStatement.executeQuery();
            
            //該当レコードをすべて取得
            List list = new ArrayList();
            while(recordSet.next()){
                TenpuFileInfo result = new TenpuFileInfo();
                result.setSystemNo(recordSet.getString("SYSTEM_NO"));
                result.setSeqTenpu(recordSet.getString("SEQ_TENPU"));
                result.setJigyoId(recordSet.getString("JIGYO_ID"));
                result.setTenpuPath(recordSet.getString("TENPU_PATH"));
                result.setPdfPath(recordSet.getString("PDF_PATH"));
                list.add(result);
            }
            
            //コレクションから配列へ変換
            TenpuFileInfo[] resultArray = (TenpuFileInfo[])list.toArray(new TenpuFileInfo[0]);
            
            //該当レコードが存在しなかった場合
            if(resultArray.length == 0){
                throw new NoDataFoundException(
                    "添付ファイル管理テーブルに該当するデータが見つかりません。検索キー：システム受付番号'"
                        + primaryKeys.getRyoikiSystemNo()
                        + "'");
            }
            
            return resultArray;
            
        } catch (SQLException ex) {
            throw new DataAccessException("添付ファイル管理テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    
    
    /**
     * 添付ファイル情報を削除する。 
     * システム受付番号に紐づく添付情報を全て削除する。
     * @param connection                コネクション
     * @param deleteInfo                キー情報（システム受付番号）
     * @throws DataAccessException      削除中に例外が発生した場合
     * @throws NoDataFoundException 対象データが１件も見つからない場合
     */
    public void deleteTenpuFileInfosForGaiyo(
        Connection connection,
        RyoikiKeikakushoPk deleteInfo)
        throws DataAccessException, NoDataFoundException
    {
        //検索（削除対象データが存在しなかった場合は例外発生）
        selectTenpuFileInfosForGaiyo(connection, deleteInfo);
        
        String query =
            "DELETE FROM TENPUFILEINFO"+dbLink
                + " WHERE"
                + " SYSTEM_NO = ?";
        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, deleteInfo.getRyoikiSystemNo());    //検索条件（システム受付番号）
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("添付ファイル情報削除中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
//2006/06/27　苗　追加ここまで 
//2006/07/14　zhangt　追加ここから   
    /**
     * 添付ファイル情報を取得する。
     * システム受付番号に紐づく添付情報を全て返す。
     * @param connection            コネクション
     * @param primaryKeys           キー情報（システム受付番号）
     * @return                      所属機関情報
     * @throws DataAccessException  データ取得中に例外が発生した場合。
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public TenpuFileInfo[] selectTenpuFiles(
        Connection connection,
        RyoikiKeikakushoPk primaryKeys)
        throws DataAccessException, NoDataFoundException
    {
        String query =
            "SELECT "
                + " A.SYSTEM_NO,"                   //システム受付番号
                + " A.SEQ_TENPU,"                   //シーケンス番号
                + " A.JIGYO_ID,"                    //事業ID
                + " A.TENPU_PATH,"                  //格納パス
                + " A.PDF_PATH"                     //変換ファイル格納パス
                + " FROM TENPUFILEINFO"+dbLink+" A"
                + " WHERE SYSTEM_NO = ?";
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getRyoikiSystemNo());   //検索条件（システム受付番号）
            recordSet = preparedStatement.executeQuery();
            
            //該当レコードをすべて取得
            List list = new ArrayList();
            while(recordSet.next()){
                TenpuFileInfo result = new TenpuFileInfo();
                result.setSystemNo(recordSet.getString("SYSTEM_NO"));
                result.setSeqTenpu(recordSet.getString("SEQ_TENPU"));
                result.setJigyoId(recordSet.getString("JIGYO_ID"));
                result.setTenpuPath(recordSet.getString("TENPU_PATH"));
                result.setPdfPath(recordSet.getString("PDF_PATH"));
                list.add(result);
            }
            
            //コレクションから配列へ変換
            TenpuFileInfo[] resultArray = (TenpuFileInfo[])list.toArray(new TenpuFileInfo[0]);
            
            //該当レコードが存在しなかった場合
            if(resultArray.length == 0){
                throw new NoDataFoundException(
                    "添付ファイル管理テーブルに該当するデータが見つかりません。検索キー：システム受付番号'"
                        + primaryKeys.getRyoikiSystemNo()
                        + "'");
            }
            
            return resultArray;
            
        } catch (SQLException ex) {
            throw new DataAccessException("添付ファイル管理テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2006/07/14　zhangt　追加ここまで
}
