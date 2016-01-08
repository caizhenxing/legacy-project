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

import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;

import org.apache.commons.logging.*;

/**
 * 所属機関情報データアクセスクラス。
 * ID RCSfile="$RCSfile: ShozokuInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class ShozokuInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** 所属機関情報管理シーケンス名 */
	public static final String SEQ_SHOZOKUINFO = "SEQ_SHOZOKUINFO";
	
	/** 所属機関情報管理シーケンスの取得桁数（連番用） */
	public static final int SEQ_FIGURE = 2;

	/** ログ */
	protected static final Log log = LogFactory.getLog(ShozokuInfoDao.class);

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
	public ShozokuInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * 所属機関情報を取得する。削除フラグが「0」の場合のみ検索する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						所属機関情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public ShozokuInfo selectShozokuInfo(
		Connection connection,
		ShozokuPk primaryKeys)
		throws DataAccessException, NoDataFoundException {
		String query =
			"SELECT "
				+ " A.SHOZOKUTANTO_ID"						//所属機関担当者ID
				+ ",A.SHOZOKU_CD"							//所属機関名（コード）
				+ ",A.SHOZOKU_NAME_KANJI"					//所属機関名（日本語）
				+ ",A.SHOZOKU_RYAKUSHO"						//所属機関名（略称）
				+ ",A.SHOZOKU_NAME_EIGO"					//所属機関名（英文）
				+ ",A.SHUBETU_CD"							//機関種別
				+ ",A.PASSWORD"								//パスワード
				+ ",A.SEKININSHA_NAME_SEI"					//責任者氏名（姓）
				+ ",A.SEKININSHA_NAME_MEI"					//責任者氏名（名）
				+ ",A.SEKININSHA_YAKU"						//責任者役職
				+ ",A.BUKYOKU_NAME"							//担当部課名
				+ ",A.KAKARI_NAME"							//担当係名
				+ ",A.TANTO_NAME_SEI"						//担当者名（姓）
				+ ",A.TANTO_NAME_MEI"						//担当者名（名）
				+ ",A.TANTO_TEL"							//担当者部局所在地（電話番号）
				+ ",A.TANTO_FAX"							//担当者部局所在地（FAX番号）
				+ ",A.TANTO_EMAIL"							//担当者部局所在地（Email）
				+ ",A.TANTO_EMAIL2"							//担当者部局所在地（Email2）
				+ ",A.TANTO_ZIP"							//担当者部局所在地（郵便番号）
				+ ",A.TANTO_ADDRESS"						//担当者部局所在地（住所）
				+ ",A.NINSHOKEY_FLG"						//認証キー発行フラグ
				+ ",A.BIKO"									//備考
				+ ",A.YUKO_DATE"							//有効期限
				//2005.08.10 iso ID発行日付の追加
				+ ",A.ID_DATE"								//ID発行日付
// 2004/04/20 追加 ここから----------------------------------------------------
// 理由 「部局担当者人数」取得
				+ ",A.BUKYOKU_NUM"							//部局担当者人数
// 追加 ここまで---------------------------------------------------------------
				+ ",A.DEL_FLG"								//削除フラグ
				+ " FROM SHOZOKUTANTOINFO A"
				+ " WHERE SHOZOKUTANTO_ID = ?"
				+ " AND DEL_FLG = 0";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShozokuInfo result = new ShozokuInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getShozokuTantoId());				//所属機関担当者ID
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setShozokuTantoId(recordSet.getString("SHOZOKUTANTO_ID"));			//所属機関担当者ID
				result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));						//所属機関名（コード）
				result.setShozokuName(recordSet.getString("SHOZOKU_NAME_KANJI"));			//所属機関名（日本語）
				result.setShozokuRyakusho(recordSet.getString("SHOZOKU_RYAKUSHO"));			//所属機関名（略称）
				result.setShozokuNameEigo(recordSet.getString("SHOZOKU_NAME_EIGO"));		//所属機関名（英文）			
				result.setShubetuCd(recordSet.getString("SHUBETU_CD"));						//機関種別
				result.setPassword(recordSet.getString("PASSWORD"));						//パスワード				
				result.setSekininshaNameSei(recordSet.getString("SEKININSHA_NAME_SEI"));	//責任者氏名（姓）
				result.setSekininshaNameMei(recordSet.getString("SEKININSHA_NAME_MEI"));	//責任者氏名（名）
				result.setSekininshaYaku(recordSet.getString("SEKININSHA_YAKU"));			//責任者役職
				result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));					//担当部課名
				result.setKakariName(recordSet.getString("KAKARI_NAME"));					//担当係名
				result.setTantoNameSei(recordSet.getString("TANTO_NAME_SEI"));				//担当者名（姓）
				result.setTantoNameMei(recordSet.getString("TANTO_NAME_MEI"));				//担当者名（名）
				result.setTantoTel(recordSet.getString("TANTO_TEL"));						//担当者部局所在地（電話番号）
				result.setTantoFax(recordSet.getString("TANTO_FAX"));						//担当者部局所在地（FAX番号）
				result.setTantoEmail(recordSet.getString("TANTO_EMAIL"));					//担当者部局所在地（Email）
				result.setTantoEmail2(recordSet.getString("TANTO_EMAIL2"));					//担当者部局所在地（Email2）
				result.setTantoZip(recordSet.getString("TANTO_ZIP"));						//担当者部局所在地（郵便番号）
				result.setTantoAddress(recordSet.getString("TANTO_ADDRESS"));				//担当者部局所在地（住所）
				result.setNinshokeyFlg(recordSet.getString("NINSHOKEY_FLG"));				//認証キー発行フラグ
				result.setBiko(recordSet.getString("BIKO"));								//備考
				result.setYukoDate(recordSet.getDate("YUKO_DATE"));							//有効期限
				//2005.08.10 iso ID発行日付の追加
				result.setIdDate(recordSet.getDate("ID_DATE"));								//ID発行日付
// 2004/04/20 追加 ここから----------------------------------------------------
// 理由 「部局担当者人数」取得
				result.setBukyokuNum(recordSet.getString("BUKYOKU_NUM"));					//部局担当者人数
// 追加 ここまで---------------------------------------------------------------
				result.setDelFlg(recordSet.getString("DEL_FLG"));							//削除フラグ
				return result;
			} else {
				throw new NoDataFoundException(
					"所属機関情報テーブルに該当するデータが見つかりません。検索キー：所属機関担当者ID'"
						+ primaryKeys.getShozokuTantoId()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("所属機関情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	/**
	 * 所属機関情報の数を取得する。削除フラグが「0」の場合のみ検索する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						所属機関情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 */
	public int countShozokuInfo(
		Connection connection,
		String shozokuCd)
		throws DataAccessException {
		String query =
			"SELECT COUNT(*)"
				+ " FROM SHOZOKUTANTOINFO"
				+ " WHERE SHOZOKU_CD = ?"
				+ " AND DEL_FLG = 0";//削除フラグ
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShozokuInfo result = new ShozokuInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, shozokuCd);
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("所属機関情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * 所属機関情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する所属機関情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertShozokuInfo(
		Connection connection,
		ShozokuInfo addInfo)
		throws DataAccessException, DuplicateKeyException {
			
		//重複チェック
		try {
			selectShozokuInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO SHOZOKUTANTOINFO "
				+ "(SHOZOKUTANTO_ID"		//所属機関担当者ID
				+ ",SHOZOKU_CD"				//所属機関名（コード）
				+ ",SHOZOKU_NAME_KANJI"		//所属機関名（日本語）
				+ ",SHOZOKU_RYAKUSHO"		//所属機関名（略称）
				+ ",SHOZOKU_NAME_EIGO"		//所属機関名（英文）
				+ ",SHUBETU_CD"				//機関種別				
				+ ",PASSWORD"				//パスワード
				+ ",SEKININSHA_NAME_SEI"	//責任者氏名（姓）
				+ ",SEKININSHA_NAME_MEI"	//責任者氏名（名）
				+ ",SEKININSHA_YAKU"		//責任者役職
				+ ",BUKYOKU_NAME"			//担当部課名
				+ ",KAKARI_NAME"			//担当係名
				+ ",TANTO_NAME_SEI"			//担当者名（姓）
				+ ",TANTO_NAME_MEI"			//担当者名（名）
				+ ",TANTO_TEL"				//担当者部局所在地（電話番号）
				+ ",TANTO_FAX"				//担当者部局所在地（FAX番号）
				+ ",TANTO_EMAIL"			//担当者部局所在地（Email）
				+ ",TANTO_EMAIL2"			//担当者部局所在地（Email2）
				+ ",TANTO_ZIP"				//担当者部局所在地（郵便番号）
				+ ",TANTO_ADDRESS"			//担当者部局所在地（住所）		
				+ ",NINSHOKEY_FLG"			//認証キー発行フラグ
				+ ",BIKO"					//備考
				+ ",YUKO_DATE"				//有効期限
				//2005.08.10 iso ID発行日付の追加
				+ ",ID_DATE"				//ID発行日付
//				2005/04/20 追加 ここから-------------------------------------
//				理由 「部局担当者人数」項目追加
				+ ",BUKYOKU_NUM"			//部局担当者人数
//				追加 ここまで------------------------------------------------
				+ ",DEL_FLG) "				//削除フラグ
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuTantoId());		//所属機関担当者ID
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuCd());			//所属機関名（コード）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());			//所属機関名（日本語）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuRyakusho());		//所属機関名（略称）			
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuNameEigo());		//所属機関名（英文）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShubetuCd());			//機関種別
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getPassword());				//パスワード
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSekininshaNameSei());	//責任者氏名（姓）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSekininshaNameMei());	//責任者氏名（名）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSekininshaYaku());		//責任者役職
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuName());			//担当部課名
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKakariName());			//担当係名
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoNameSei());			//担当者名（姓）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoNameMei());			//担当者名（名）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoTel());				//担当者部局所在地（電話番号）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoFax());				//担当者部局所在地（FAX番号）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoEmail());			//担当者部局所在地（Email）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoEmail2());			//担当者部局所在地（Email2）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoZip());				//担当者部局所在地（郵便番号）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoAddress());			//担当者部局所在地（住所）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNinshokeyFlg());			//認証キー発行フラグ
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());					//備考
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYukoDate());				//有効期限
			//2005.08.10 iso ID発行日付の追加
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getIdDate());				//ID発行日付
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuNum());			//部局担当者人数
			DatabaseUtil.setParameter(preparedStatement, i++, 0);								//削除フラグ
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("所属機関情報登録中に例外が発生しました。 ", ex);
			throw new DataAccessException("所属機関情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * 所属機関情報を更新する。
	 * @param connection				コネクション
	 * @param addInfo					更新する所属機関情報
	 * @throws DataAccessException		更新中に例外が発生した場合
	 * @throws NoDataFoundException		対象データが見つからない場合
	 */
	public void updateShozokuInfo(
		Connection connection,
		ShozokuInfo updateInfo)
		throws DataAccessException, NoDataFoundException {

		//検索
		selectShozokuInfo(connection, updateInfo);

		String query =
			"UPDATE SHOZOKUTANTOINFO"
				+ " SET"	
				+ " SHOZOKU_CD = ?"				//所属機関名（コード）
				+ ",SHOZOKU_NAME_KANJI = ?"		//所属機関名（日本語）
				+ ",SHOZOKU_RYAKUSHO = ?"		//所属機関名（略称）
				+ ",SHOZOKU_NAME_EIGO = ?"		//所属機関名（英文）
				+ ",SHUBETU_CD = ?"				//機関種別
				+ ",PASSWORD = ?"				//パスワード
				+ ",SEKININSHA_NAME_SEI = ?"	//責任者氏名（姓）
				+ ",SEKININSHA_NAME_MEI = ?"	//責任者氏名（名）
				+ ",SEKININSHA_YAKU = ?"		//責任者役職
				+ ",BUKYOKU_NAME = ?"			//担当部課名
				+ ",KAKARI_NAME = ?"			//担当係名
				+ ",TANTO_NAME_SEI = ?"			//担当者名（姓）
				+ ",TANTO_NAME_MEI = ?"			//担当者名（名）
				+ ",TANTO_TEL = ?"				//担当者部局所在地（電話番号）
				+ ",TANTO_FAX = ?"				//担当者部局所在地（FAX番号）
				+ ",TANTO_EMAIL = ?"			//担当者部局所在地（Email）
				+ ",TANTO_EMAIL2 = ?"			//担当者部局所在地（Email2）
				+ ",TANTO_ZIP = ?"				//担当者部局所在地（郵便番号）
				+ ",TANTO_ADDRESS = ?"			//担当者部局所在地（住所）
				+ ",NINSHOKEY_FLG = ?"			//認証キー発行フラグ
				+ ",BIKO = ?"					//備考
				+ ",YUKO_DATE = ?"				//有効期限
				//2005.08.10 iso ID発行日付の追加
				+ ",ID_DATE = ?"				//ID発行日付
//				2005/04/20 追加 ここから-------------------------------------
//				理由 「部局担当者人数」項目追加
				+ ",BUKYOKU_NUM = ?"			//部局担当者人数
//				追加 ここまで------------------------------------------------
				+ ",DEL_FLG = ?"				//削除フラグ				
				+ " WHERE"
				+ " SHOZOKUTANTO_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());			//所属機関名（コード）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());		//所属機関名（日本語）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuRyakusho());	//所属機関名（略称）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuNameEigo());	//所属機関名（英文）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShubetuCd());			//機関種別
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getPassword());			//パスワード
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSekininshaNameSei());	//責任者氏名（姓）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSekininshaNameMei());	//責任者氏名（名）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSekininshaYaku());	//責任者役職
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());		//担当部課名
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKakariName());		//担当係名
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoNameSei());		//担当者名（姓）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoNameMei());		//担当者名（名）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoTel());			//担当者部局所在地（電話番号）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoFax());			//担当者部局所在地（FAX番号）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoEmail());		//担当者部局所在地（Email）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoEmail2());		//担当者部局所在地（Email2）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoZip());			//担当者部局所在地（郵便番号）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoAddress());		//担当者部局所在地（住所）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNinshokeyFlg());		//認証キー発行フラグ
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());				//備考
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getYukoDate());			//有効期限
			//2005.08.10 iso ID発行日付の追加
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getIdDate());			//ID発行日付
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuNum());		//部局担当者人数
			DatabaseUtil.setParameter(preparedStatement, i++, updateInfo.getDelFlg());			//削除フラグ
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuTantoId());	//所属機関担当者ID
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("所属機関情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * 所属機関情報を削除する。(削除フラグ) 
	 * @param connection			コネクション
	 * @param addInfo				削除する所属機関主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteFlgShozokuInfo(
		Connection connection,
		ShozokuPk deleteInfo)
		throws DataAccessException, NoDataFoundException {

		//検索
		selectShozokuInfo(connection, deleteInfo);
		
		String query =
			"UPDATE SHOZOKUTANTOINFO"
				+ " SET"
				+ " DEL_FLG = 1"//削除フラグ				
				+ " WHERE"
				+ " SHOZOKUTANTO_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getShozokuTantoId());//所属機関担当者ID
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {

			throw new DataAccessException("所属機関情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * ※未実装。
	 * 所属機関情報を削除する。(物理削除) 
	 * @param connection			コネクション
	 * @param addInfo				削除する所属機関主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteShozokuInfo(
		Connection connection,
		ShozokuPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
	}
	
	
	
	/**
	 * ユーザID、パスワードの認証を行う。
	 * @param connection		コネクション
	 * @param userid			ユーザID
	 * @param password			パスワード
	 * @return					認証に成功した場合 true 以外 false
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 */
	public boolean authenticateShozokuInfo(
		Connection connection, String userid, String password)
		throws DataAccessException {
		String query =
			"SELECT count(*)"
				+ " FROM SHOZOKUTANTOINFO"
				+ " WHERE DEL_FLG = 0"
				+ " AND SHOZOKUTANTO_ID = ?"
				+ " AND PASSWORD = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		int count = 0;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, userid);
			preparedStatement.setString(i++, password);
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			//一致するデータが存在する場合は、true返す
			if(count > 0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException ex) {
			throw new DataAccessException("所属機関情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	
	
	/**
	 * パスワードの変更する。 
	 * @param connection			コネクション
	 * @param pkInfo				主キー情報
	 * @param newPassword			新しいパスワード
	 * @return              		パスワードの変更に成功した場合 true 以外 false
	 * @throws DataAccessException	変更中に例外が発生した場合
	 */
	public boolean changePasswordShozokuInfo(
		Connection connection,
		ShozokuPk pkInfo,
		String newPassword)
		throws DataAccessException {

		String query =
			"UPDATE SHOZOKUTANTOINFO"
				+ " SET"
				+ " PASSWORD = ?"
				+ " WHERE"
				+ " SHOZOKUTANTO_ID = ?"
				+ " AND DEL_FLG = 0";//削除フラグ

		PreparedStatement preparedStatement = null;
			
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++, newPassword);//新しいパスワード
			DatabaseUtil.setParameter(preparedStatement,i++, pkInfo.getShozokuTantoId());//所属機関担当者ID
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("パスワード変更中に例外が発生しました。 ", ex);

		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
		return true;
	}
	
	
	
    /**
	 * 所属機関毎の順番を取得する。
	 * 
	 * @param connection         	コネクション
	 * @param shozokuCd          	所属機関コード
	 * @return 					順番(2桁)
	 * @throws DataAccessException データベースアクセス中の例外
	 */
	public String getSequenceNo(Connection connection, String shozokuCd)
			throws DataAccessException, ApplicationException
	{
		String query =
			 "SELECT TO_CHAR(MAX(SUBSTR(SHOZOKUTANTO_ID,6,2)) + 1,'FM00') COUNT"
				+ " FROM SHOZOKUTANTOINFO"
				+ " WHERE SHOZOKU_CD = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, shozokuCd);
			recordSet = preparedStatement.executeQuery();

			String ret = null;
			if (recordSet.next()) {
				ret = recordSet.getString(1);
				if (ret == null) {
					ret = "01";
				}
			}

			//2005/07/14 連番は10以上時、エラーとする
			if (Integer.parseInt(ret) > 9){
				throw new ApplicationException("所属機関担当者IDの連番は09を超えました。", 
								new ErrorInfo("errors.4001")
						);
			}

			return ret;

		} catch (SQLException ex) {
			throw new DataAccessException("所属機関情報テーブル検索実行中に例外が発生しました。", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
     
     
     
	/**
	 * 所属担当者情報を更新する。
	 * 更新する情報は以下の通り。 <br>
	 * <li>所属機関名（和文）</li>
	 * <li>所属機関名（略称）</li>
	 * （※所属機関名（英文）については更新しない。） <br>
	 * 
	 * @param connection             コネクション
	 * @param updateInfo             所属機関情報
	 * @throws DataAccessException   更新中に例外が発生した場合
	 * @throws NoDataFoundException  対象データが見つからない場合
	 */
	public void updateShozokuInfo(
		Connection connection,
		KikanInfo updateInfo)
		throws DataAccessException, NoDataFoundException {
	
		String query =
			"UPDATE SHOZOKUTANTOINFO"
				+ " SET"
				+ " SHOZOKU_NAME_KANJI = ?"
				+ ",SHOZOKU_RYAKUSHO = ?"
				+ " WHERE"
				+ "  SHOZOKU_CD = ?"			//機関コードが同じで...
				+ " AND ( "						//かつ、
				+ "  SHOZOKU_NAME_KANJI <> ?"	//機関名（和文）が違うか
				+ "  OR "						//または
				+ "  SHOZOKU_RYAKUSHO <> ?"		//機関名（略称）が違うもの
				+ " ) "
				;

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuNameKanji());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuRyakusho());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuNameKanji());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuRyakusho());
			preparedStatement.executeUpdate();		//更新件数は、0件～複数件であるため

		} catch (SQLException ex) {
			throw new DataAccessException("所属担当者情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * 所属機関情報を検索する。
	 * 
	 * 所属機関担当者は削除フラグが「0」のデータ、部局担当者は全てを対象とする。
	 * 
	 * @param connection
	 * @param primaryKeys
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 */
	public Page selectShozokuAndBukyokuTanto(UserInfo userInfo, ShozokuSearchInfo searchInfo)
		throws ApplicationException {
		
		String sql=
			"SELECT"
			+ " INFO.SHOZOKU_CD"						//所属機関名（コード）
			+ " ,INFO.SHOZOKU_RYAKUSHO"					//所属機関名（略称）
			+ " ,INFO.TANTO_ID"							//担当者ID（所属機関／部局）
			+ " ,INFO.TANTO_NAME_SEI"					//担当者名（姓）
			+ " ,INFO.TANTO_NAME_MEI"					//担当者名（名）
			+ " ,INFO.DEL_FLG"							//削除フラグ
			+ " ,INFO.TANTO_FLG"						//担当フラグ（0：所属機関　1：部局）
			+ " FROM("
				+ " SELECT"
					+ " S.SHOZOKU_CD"
					+ " ,S.SHOZOKU_RYAKUSHO"
					+ " ,S.SHOZOKUTANTO_ID AS TANTO_ID"
					+ " ,S.TANTO_NAME_SEI"
					+ " ,S.TANTO_NAME_MEI"
					+ " ,S.DEL_FLG"
					+ " ,0 AS TANTO_FLG"				//担当フラグ「0」
					+ " ,S.SHUBETU_CD"					//機関種別（検索用）
					+ " ,S.SHOZOKU_NAME_KANJI"			//所属機関名（検索用）
				+ " FROM SHOZOKUTANTOINFO S"			//所属機関担当者情報テーブル
				+ " WHERE DEL_FLG = 0"
				+ " UNION"
				+ " SELECT"
					+ " B.SHOZOKU_CD"
					+ " ,S2.SHOZOKU_RYAKUSHO"
					+ " ,B.BUKYOKUTANTO_ID AS TANTO_ID"
					+ " ,B.TANTO_NAME_SEI"
					+ " ,B.TANTO_NAME_MEI"
					+ " ,B.DEL_FLG"
					+ " ,1 AS TANTO_FLG"				//担当フラグ「1」
					+ " ,S2.SHUBETU_CD"					//機関種別（検索用）
					+ " ,S2.SHOZOKU_NAME_KANJI"			//所属機関名（検索用）
				+ " FROM BUKYOKUTANTOINFO B"			//部局担当者情報テーブル
					+ " INNER JOIN SHOZOKUTANTOINFO S2"		//所属機関マスタ
					+ " ON B.SHOZOKU_CD = S2.SHOZOKU_CD"
					+ " AND S2.DEL_FLG = 0"
				+ " WHERE REGIST_FLG = 1"
			+ " ) INFO"
			+ " WHERE SHOZOKU_CD IS NOT NULL";
		
		StringBuffer query = new StringBuffer(sql);
		
		if(searchInfo.getShubetuCd() != null && !searchInfo.getShubetuCd().equals("")){			//機関種別コード（完全一致）
			query.append(" AND SHUBETU_CD = " + EscapeUtil.toSqlString(searchInfo.getShubetuCd()) );
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){			//所属機関名（コード）（完全一致）
			query.append(" AND SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){		//所属機関名（日本語）（部分一致）
			query.append(" AND SHOZOKU_NAME_KANJI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");			
		}
		if(searchInfo.getShozokuTantoId() != null && !searchInfo.getShozokuTantoId().equals("")){	//担当者ID（完全一致）
			query.append(" AND TANTO_ID = '" + EscapeUtil.toSqlString(searchInfo.getShozokuTantoId()) + "'");
		}
		if(searchInfo.getTantoNameSei() != null && !(searchInfo.getTantoNameSei()).equals("")){	//担当者名（姓）（部分一致）
			query.append(" AND TANTO_NAME_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameSei()) + "%'");			
		}
		if(searchInfo.getTantoNameMei() != null && !(searchInfo.getTantoNameMei()).equals("")){	//担当者名（名）（部分一致）
			query.append(" AND TANTO_NAME_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameMei()) + "%'");			
		}
		if(searchInfo.getBukyokuTantoId() != null && !(searchInfo.getBukyokuTantoId()).equals("")){	//部局担当者ID（完全一致）
			query.append(" AND TANTO_ID = '" + EscapeUtil.toSqlString(searchInfo.getBukyokuTantoId()) + "'");
		}
		if(searchInfo.getBukyokuSearchFlg() != null && searchInfo.getBukyokuSearchFlg().equals("1")){
			query.append(" AND DEL_FLG = 1");
		}

		//2005/04/26 追加 -------------------------------------------------------------------------------ここから
		//理由 所属機関担当者名等の所属機関担当者情報に関する絞込みの際に、部局担当者情報が検索されないように修正
		//部局担当者IDを検索条件にセットした場合は部局担当者の情報のみ取得
		if(searchInfo.getBukyokuTantoId() != null && !(searchInfo.getBukyokuTantoId()).equals("")){
			query.append(" AND TANTO_FLG = 1");
		}
		//所属機関担当者ID、担当者名を検索条件にセットした場合は所属機関担当者の情報のみ取得
		if((searchInfo.getShozokuTantoId() != null && !searchInfo.getShozokuTantoId().equals(""))
				||(searchInfo.getTantoNameSei() != null && !(searchInfo.getTantoNameSei()).equals(""))
				||(searchInfo.getTantoNameMei() != null && !(searchInfo.getTantoNameMei()).equals(""))){
			query.append(" AND TANTO_FLG = 0");
		}
		//2005/04/26 追加 -------------------------------------------------------------------------------ここまで
		
		
		//所属機関コード、担当フラグ、担当者IDでソート
		query.append(" ORDER BY SHOZOKU_CD,TANTO_FLG,TANTO_ID");		
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ページ取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection, searchInfo, query.toString());
		} catch (DataAccessException e) {
			log.error("所属機関管理データ検索中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"所属機関管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	   
}
