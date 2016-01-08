/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/07/26
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
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaPk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 研究組織表管理テーブルアクセスクラス。
 * ID RCSfile="$RCSfile: KenkyuSoshikikanriDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class KenkyuSoshikikanriDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(KenkyuSoshikikanriDao.class);

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
	public KenkyuSoshikikanriDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	/**
	 * コンストラクタ。
	 * @param userInfo 実行するユーザ情報
	 * @param dbLink   DBリンク名
	 */
	public KenkyuSoshikikanriDao(UserInfo userInfo, String dbLink){
		this.userInfo = userInfo;
		this.dbLink   = dbLink;
	}


	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * キーに一致する研究組織情報を取得する。
	 * @param connection			    コネクション
	 * @param pkInfo				    主キー情報
	 * @return						    研究組織情報
	 * @throws DataAccessException	    データ取得中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public KenkyuSoshikiKenkyushaInfo selectKenkyuSoshikiKenkyushaInfo(
		Connection connection,
		KenkyuSoshikiKenkyushaPk pkInfo)
		throws DataAccessException, NoDataFoundException
	{
			String query =
				"SELECT "
					+ " SYSTEM_NO"
					+ ",SEQ_NO"
					+ ",JIGYO_ID"
					+ ",BUNTAN_FLG"
					+ ",KENKYU_NO"
					+ ",NAME_KANJI_SEI"
					+ ",NAME_KANJI_MEI"
					+ ",NAME_KANA_SEI"
					+ ",NAME_KANA_MEI"
					+ ",SHOZOKU_CD"
					+ ",SHOZOKU_NAME"
					+ ",BUKYOKU_CD"
					+ ",BUKYOKU_NAME"
					+ ",SHOKUSHU_CD"
					+ ",SHOKUSHU_NAME_KANJI"
					+ ",SENMON"
					+ ",GAKUI"
					+ ",BUNTAN"
					+ ",KEIHI"
					+ ",EFFORT"
					+ ",NENREI"
					+ " FROM KENKYUSOSHIKIKANRI"+dbLink
					+ " WHERE"
					+ "   SYSTEM_NO = ?"
					+ " AND"
					+ "   SEQ_NO = ?"
					;
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			try {
				KenkyuSoshikiKenkyushaInfo result = new KenkyuSoshikiKenkyushaInfo();
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement, i++, pkInfo.getSystemNo());
				DatabaseUtil.setParameter(preparedStatement, i++, pkInfo.getSeqNo());				
				recordSet = preparedStatement.executeQuery();
				if (recordSet.next()) {
					result.setSystemNo(recordSet.getString("SYSTEM_NO"));
					result.setSeqNo(recordSet.getString("SEQ_NO"));
					result.setJigyoID(recordSet.getString("JIGYO_ID"));
					result.setBuntanFlag(recordSet.getString("BUNTAN_FLG"));
					result.setKenkyuNo(recordSet.getString("KENKYU_NO"));
					result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));
					result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));
					result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
					result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
					result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
					result.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
					result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));
					result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
					result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));
					result.setShokushuNameKanji(recordSet.getString("SHOKUSHU_NAME_KANJI"));
					result.setSenmon(recordSet.getString("SENMON"));
					result.setGakui(recordSet.getString("GAKUI"));
					result.setBuntan(recordSet.getString("BUNTAN"));
					result.setKeihi(recordSet.getString("KEIHI"));
					result.setEffort(recordSet.getString("EFFORT"));
					result.setNenrei(recordSet.getString("NENREI"));
					return result;
				} else {
					throw new NoDataFoundException(
						"研究組織情報テーブルに該当するデータが見つかりません。検索キー：システム受付番号'"
							+ pkInfo.getSystemNo()
							+ "'、シーケンス番号'"
							+ pkInfo.getSeqNo()
							+ "'");
				}
			} catch (SQLException ex) {
				throw new DataAccessException("研究組織情報テーブル検索実行中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}	
	
	

	/**
	 * 申請書のキー情報に一致する研究組織情報を取得する。
	 * KenkyuSoshikiKenkyushaInfo のリストが戻る。
	 * @param connection			    コネクション
	 * @param pkInfo				    申請書のキー情報
	 * @return						    研究組織研究者のリスト
	 * @throws DataAccessException	    データ取得中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public List selectKenkyuSoshikiKenkyushaInfo(
		Connection connection,
	    ShinseiDataPk pkInfo)
		throws DataAccessException, NoDataFoundException
	{
			String query =
				"SELECT "
					+ " SYSTEM_NO"
					+ ",SEQ_NO"
					+ ",JIGYO_ID"
					+ ",BUNTAN_FLG"
					+ ",KENKYU_NO"
					+ ",NAME_KANJI_SEI"
					+ ",NAME_KANJI_MEI"
					+ ",NAME_KANA_SEI"
					+ ",NAME_KANA_MEI"
					+ ",SHOZOKU_CD"
					+ ",SHOZOKU_NAME"
					+ ",BUKYOKU_CD"
					+ ",BUKYOKU_NAME"
					+ ",SHOKUSHU_CD"
					+ ",SHOKUSHU_NAME_KANJI"
					+ ",SENMON"
					+ ",GAKUI"
					+ ",BUNTAN"
					+ ",KEIHI"
					+ ",EFFORT"
					+ ",NENREI"
					+ " FROM KENKYUSOSHIKIKANRI"+dbLink
					+ " WHERE"
					+ "   SYSTEM_NO = ?"
					+ " ORDER BY"
					+ "   SEQ_NO"
					;
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			try {
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement, i++, pkInfo.getSystemNo());
				recordSet = preparedStatement.executeQuery();
				
				//該当レコードをすべて取得
				List resultList = new ArrayList();
				while (recordSet.next()) {
					KenkyuSoshikiKenkyushaInfo result = new KenkyuSoshikiKenkyushaInfo();
					result.setSystemNo(recordSet.getString("SYSTEM_NO"));
					result.setSeqNo(recordSet.getString("SEQ_NO"));
					result.setJigyoID(recordSet.getString("JIGYO_ID"));
					result.setBuntanFlag(recordSet.getString("BUNTAN_FLG"));
					result.setKenkyuNo(recordSet.getString("KENKYU_NO"));
					result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));
					result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));
					result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
					result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
					result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
					result.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
					result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));
					result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
					result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));
					result.setShokushuNameKanji(recordSet.getString("SHOKUSHU_NAME_KANJI"));
					result.setSenmon(recordSet.getString("SENMON"));
					result.setGakui(recordSet.getString("GAKUI"));
					result.setBuntan(recordSet.getString("BUNTAN"));
					result.setKeihi(recordSet.getString("KEIHI"));
					result.setEffort(recordSet.getString("EFFORT"));
					result.setNenrei(recordSet.getString("NENREI"));
					resultList.add(result);	
				}
				
				//該当レコードが存在しなかった場合
				if(resultList.size() == 0){
					throw new NoDataFoundException(
						"研究組織情報テーブルに該当するデータが見つかりません。検索キー：システム受付番号'"
							+ pkInfo.getSystemNo()
							+ "'");
				}
				
				return resultList;
				
			} catch (SQLException ex) {
				throw new DataAccessException("研究組織情報テーブル検索実行中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}	
	
	
	
	/**
	 * 研究組織情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する研究組織情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertKenkyuSoshikiKanriInfo(
		Connection connection,
		KenkyuSoshikiKenkyushaInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
			//重複チェック
			try {
				selectKenkyuSoshikiKenkyushaInfo(connection, addInfo);
				//NG
				throw new DuplicateKeyException(
					"'" + addInfo + "'は既に登録されています。");
			} catch (NoDataFoundException e) {
				//OK
			}
		
			String query =
				"INSERT INTO KENKYUSOSHIKIKANRI"+dbLink
					+ "("
					+ " SYSTEM_NO"
					+ ",SEQ_NO"
					+ ",JIGYO_ID"
					+ ",BUNTAN_FLG"
					+ ",KENKYU_NO"
					+ ",NAME_KANJI_SEI"
					+ ",NAME_KANJI_MEI"
					+ ",NAME_KANA_SEI"
					+ ",NAME_KANA_MEI"
					+ ",SHOZOKU_CD"
					+ ",SHOZOKU_NAME"
					+ ",BUKYOKU_CD"
					+ ",BUKYOKU_NAME"
					+ ",SHOKUSHU_CD"
					+ ",SHOKUSHU_NAME_KANJI"
					+ ",SENMON"
					+ ",GAKUI"
					+ ",BUNTAN"
					+ ",KEIHI"
					+ ",EFFORT"
					+ ",NENREI"
					+ ") "
					+ "VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
					;
					
			PreparedStatement preparedStatement = null;
			try {
				//登録
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSystemNo());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSeqNo());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoID());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBuntanFlag());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKenkyuNo());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiSei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiMei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaSei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaMei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuCd());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuCd());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuName());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuCd());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuNameKanji());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSenmon());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getGakui());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBuntan());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKeihi());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getEffort());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNenrei());
				DatabaseUtil.executeUpdate(preparedStatement);
			} catch (SQLException ex) {
				throw new DataAccessException("研究組織情報登録中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	
	
	
	/**
	 * 研究組織情報を更新する。
	 * @param connection				コネクション
	 * @param updateInfo				更新する研究組織情報
	 * @throws DataAccessException		更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateGyomutantoInfo(
		Connection connection,
		KenkyuSoshikiKenkyushaInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
			//検索
			selectKenkyuSoshikiKenkyushaInfo(connection, updateInfo);
	
			String query =
				"UPDATE KENKYUSOSHIKIKANRI"+dbLink
					+ " SET"
					+ " SYSTEM_NO = ? "
					+ ",SEQ_NO = ? "
					+ ",JIGYO_ID = ? "
					+ ",BUNTAN_FLG = ? "
					+ ",KENKYU_NO = ? "
					+ ",NAME_KANJI_SEI = ? "
					+ ",NAME_KANJI_MEI = ? "
					+ ",NAME_KANA_SEI = ? "
					+ ",NAME_KANA_MEI = ? "
					+ ",SHOZOKU_CD = ? "
					+ ",SHOZOKU_NAME = ? "
					+ ",BUKYOKU_CD = ? "
					+ ",BUKYOKU_NAME = ? "
					+ ",SHOKUSHU_CD = ? "
					+ ",SHOKUSHU_NAME_KANJI = ? "
					+ ",SENMON = ? "
					+ ",GAKUI = ? "
					+ ",BUNTAN = ? "
					+ ",KEIHI = ? "
					+ ",EFFORT = ? "
					+ ",NENREI"					
					+ " WHERE"
					+ "   SYSTEM_NO = ?"
					+ " AND"
					+ "   SEQ_NO = ?"
					;

			PreparedStatement preparedStatement = null;
			try {
				//登録
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSystemNo());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSeqNo());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoID());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBuntanFlag());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuNo());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuCd());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuCd());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuNameKanji());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSenmon());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getGakui());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBuntan());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKeihi());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getEffort());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNenrei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSystemNo());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSeqNo());
				DatabaseUtil.executeUpdate(preparedStatement);
			} catch (SQLException ex) {
				throw new DataAccessException("研究組織情報更新中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	
	
	
	/**
	 * 研究組織情報を削除する。(物理削除)
	 * @param connection			    コネクション
	 * @param pkInfo				    削除する研究組織主キー情報
	 * @throws DataAccessException     削除中に例外が発生した場合
	 * @throws NoDataFoundException    対象データが見つからない場合
	 */
	public void deleteKenkyuSoshikiKanriInfo(
		Connection connection,
		KenkyuSoshikiKenkyushaInfo deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//検索（削除対象データが存在しなかった場合は例外発生）
		selectKenkyuSoshikiKenkyushaInfo(connection, deleteInfo);
				
		String query =
			"DELETE FROM KENKYUSOSHIKIKANRI"+dbLink
				+ " WHERE"
				+ "   SYSTEM_NO = ?"
				+ " AND"
				+ "   SEQ_NO = ?"
				;
		
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getSystemNo());
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getSeqNo());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("研究組織情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}	
	
	
	
	/**
	 * 研究組織情報を削除する。(物理削除)
	 * 当該申請書に紐付く研究組織情報を全て削除する。
	 * @param connection			    コネクション
	 * @param pkInfo				    削除する申請書主キー情報
	 * @throws DataAccessException     削除中に例外が発生した場合
	 * @throws NoDataFoundException    対象データが見つからない場合
	 */
	public void deleteKenkyuSoshikiKanriInfo(
		Connection connection,
		ShinseiDataPk pkInfo)
		throws DataAccessException, NoDataFoundException
	{
				
		String query =
			"DELETE FROM KENKYUSOSHIKIKANRI"+dbLink
				+ " WHERE"
				+ "   SYSTEM_NO = ?"
				;
		
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getSystemNo());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("研究組織情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}	
	
	
		
	/**
	 * 研究組織情報を削除する。(物理削除)
	 * 当該事業に紐付く研究組織情報を全て削除する。
	 * @param connection			    コネクション
	 * @param pkInfo				    削除する事業ID
	 * @throws DataAccessException     削除中に例外が発生した場合
	 * @throws NoDataFoundException    対象データが見つからない場合
	 */
	public void deleteKenkyuSoshikiKanriInfo(
		Connection connection,
		String jigyoId)
		throws DataAccessException, NoDataFoundException
	{
				
		String query =
			"DELETE FROM KENKYUSOSHIKIKANRI"+dbLink
				+ " WHERE"
				+ "   JIGYO_ID = ?"
				;
		
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,jigyoId);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("研究組織情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
			
	/**
	 * CSV出力するリストを返す。
	 * @param connection			    コネクション
	 * @param pkInfo				    削除する申請書主キー情報
	 * @throws DataAccessException     削除中に例外が発生した場合
	 * @throws NoDataFoundException    対象データが見つからない場合
	 */
	public List searchCsvData(
		Connection connection,
		ShinseiSearchInfo searchInfo)
		throws DataAccessException, NoDataFoundException
	{
		//SQLの検索条件部分を作成
		String query = ShinseiDataInfoDao.getQueryString("", searchInfo);
		
		String select =	
		"SELECT "
			+ " C.UKETUKE_NO				\"申請番号\"				"
			+ ",B.SEQ_NO					\"シーケンス番号\"			"
			+ ",B.JIGYO_ID					\"事業ID\"					"
			+ ",B.BUNTAN_FLG				\"代表者分担者別\"			"
			+ ",B.KENKYU_NO					\"研究者番号\"				"
			+ ",B.NAME_KANJI_SEI			\"氏名（漢字－姓）\"			"
			+ ",B.NAME_KANJI_MEI			\"氏名（漢字－名）\"			"
			+ ",B.NAME_KANA_SEI				\"氏名（フリガナ－姓）\"		"
			+ ",B.NAME_KANA_MEI				\"氏名（フリガナ－名）\"		"
			+ ",B.SHOZOKU_CD				\"所属研究機関名（番号）\"	"
			+ ",B.SHOZOKU_NAME				\"所属研究機関名（和文）\"	"
			+ ",B.BUKYOKU_CD				\"部局名（番号）\"			"
			+ ",B.BUKYOKU_NAME				\"部局名（和文）\"		"
			+ ",B.SHOKUSHU_CD				\"職名（番号）\"			"
			+ ",B.SHOKUSHU_NAME_KANJI		\"職名（和文）\"			"
			+ ",B.SENMON					\"現在の専門\"			"
			+ ",B.GAKUI						\"学位\"					"
			+ ",B.BUNTAN					\"役割分担\"				"
			+ ",B.KEIHI						\"研究経費\"				"
			+ ",B.EFFORT					\"エフォート\"			"
			+ ",B.NENREI					\"年齢\"					"
			+ " FROM "
			+ "   KENKYUSOSHIKIKANRI"+dbLink+" B"
			+ "  ,(SELECT  A.SYSTEM_NO"
			+ "           ,A.UKETUKE_NO "
			+ "            FROM SHINSEIDATAKANRI"+dbLink+" A "
			+              query					//問い合わせ条件部分
			+ "   ) C"
			+ " WHERE"
			+ "   B.SYSTEM_NO = C.SYSTEM_NO"
			+ " ORDER BY "
			+ "   C.UKETUKE_NO, B.SEQ_NO"
			;	
	
			//for debug
			if(log.isDebugEnabled()){
				log.debug("query:" + select);
			}
	
		//CSVリスト取得（カラム名をキー項目名とする）
		return SelectUtil.selectCsvList(connection, select, true);
	
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
			"INSERT INTO KENKYUSOSHIKIKANRI"+dbLink
				+ " SELECT * FROM KENKYUSOSHIKIKANRI WHERE JIGYO_ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, jigyoId);		//検索条件（事業ID）
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("研究組織表管理テーブル保管中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
}
