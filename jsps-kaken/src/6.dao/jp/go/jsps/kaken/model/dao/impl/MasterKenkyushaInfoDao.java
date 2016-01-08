/*
 * 作成日: 2005/03/28
 *
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.*;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * 研究者マスタデータアクセスクラス
 * 
 * @author yoshikawa_h
 *
 */
public class MasterKenkyushaInfoDao {
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(MasterKenkyushaInfoDao.class);

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
	public MasterKenkyushaInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	/**
	 * キーに一致する研究者情報を取得する。
	 * 複数件の研究者情報を研究者マスタから取得する。
	 * 
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @param lock					ロックの有無
	 * @return						研究者情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public KenkyushaInfo selectKenkyushaInfo(Connection connection,KenkyushaPk primaryKey,boolean lock)
		throws DataAccessException, NoDataFoundException {

		KenkyushaInfo result = new KenkyushaInfo();
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			StringBuffer query = new StringBuffer();
			
			query.append(
				"SELECT "
				+ " KENKYU.KENKYU_NO"				//研究者番号
				+ ", KENKYU.NAME_KANJI_SEI"			//氏名（漢字等-姓）
				+ ", KENKYU.NAME_KANJI_MEI"			//氏名（漢字等-名）
				+ ", KENKYU.NAME_KANA_SEI"			//氏名（フリガナ-姓）
				+ ", KENKYU.NAME_KANA_MEI"			//氏名（フリガナ-名）
				+ ", KENKYU.SEIBETSU"				//性別
				+ ", KENKYU.BIRTHDAY"				//生年月日
				+ ", KENKYU.GAKUI"					//学位
				+ ", KENKYU.SHOZOKU_CD"				//所属機関コード
				+ ", KIKAN.SHOZOKU_NAME_KANJI"		//所属機関名（和文）
				+ ", KIKAN.SHOZOKU_NAME_EIGO"		//所属機関名（英文）
				+ ", KIKAN.SHOZOKU_RYAKUSHO"		//所属機関名（略称）
				+ ", KENKYU.BUKYOKU_CD"				//部局コード
				+ ", BUKYOKU.BUKA_NAME"				//部局名
				+ ", BUKYOKU.BUKA_RYAKUSHO"			//部局略称
				+ ", KENKYU.SHOKUSHU_CD"			//職コード
				+ ", SHOKU.SHOKUSHU_NAME"			//職名
				+ ", SHOKU.SHOKUSHU_NAME_RYAKU"		//職略称
				+ ", KENKYU.KOSHIN_DATE"			//更新日時
				+ ", KENKYU.BIKO"					//備考
				//2005/04/21　追加 ここから----------------------------------------
				//研究所マスタに削除フラグ追加のため
				+ ", KENKYU.DEL_FLG"				//削除フラグ
				//追加 ここまで----------------------------------------------------
				+ " FROM MASTER_KENKYUSHA KENKYU"					//研究者マスタ
					+ " INNER JOIN MASTER_KIKAN KIKAN"				//所属機関マスタ
						+ " ON KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD"
					+ " INNER JOIN MASTER_BUKYOKU BUKYOKU"			//部局マスタ
						+ " ON KENKYU.BUKYOKU_CD = BUKYOKU.BUKYOKU_CD"
					+ " INNER JOIN MASTER_SHOKUSHU SHOKU"			//職種マスタ
						+ " ON KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD"
				+ " WHERE KENKYU.SHOZOKU_CD = ?"
				+ " AND KENKYU.KENKYU_NO = ?"		//主キー：所属機関コード、研究者番号
				//2005/04/21　追加 ここから----------------------------------------
				//研究所マスタに削除フラグ追加のため
				+ " AND KENKYU.DEL_FLG = 0"
				//追加 ここまで----------------------------------------------------
			);
			
			//排他制御
			if(lock){
				query.append(" FOR UPDATE");
			}
			
			preparedStatement =
				connection.prepareStatement(query.toString());
			
			int i = 1;
			preparedStatement.setString(i++, primaryKey.getShozokuCd());
			preparedStatement.setString(i++, primaryKey.getKenkyuNo());
			
			recordSet = preparedStatement.executeQuery();
			
			if (recordSet.next()) {
				result.setKenkyuNo(recordSet.getString("KENKYU_NO"));					//研究者番号
				result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));			//氏名（漢字等-姓）
				result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));			//氏名（漢字等-名）
				result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));			//氏名（フリガナ-姓）
				result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));			//氏名（フリガナ-名）
				result.setSeibetsu(recordSet.getString("SEIBETSU"));					//性別
				result.setBirthday(recordSet.getDate("BIRTHDAY"));						//生年月日
				result.setGakui(recordSet.getString("GAKUI"));							//学位
				result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));					//所属機関コード
				result.setShozokuNameKanji(recordSet.getString("SHOZOKU_NAME_KANJI"));	//所属機関名（和文）
				result.setShozokuNameEigo(recordSet.getString("SHOZOKU_NAME_EIGO"));	//所属機関名（英文）
				result.setShozokuRyakusho(recordSet.getString("SHOZOKU_RYAKUSHO"));		//所属機関名（略称）
				result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));					//部局コード
				result.setBukyokuName(recordSet.getString("BUKA_NAME"));				//部局名
				result.setBukyokuNameRyaku(recordSet.getString("BUKA_RYAKUSHO"));		//部局略称
				result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));				//職コード
				result.setShokushuName(recordSet.getString("SHOKUSHU_NAME"));			//職名
				result.setShokushuNameRyaku(recordSet.getString("SHOKUSHU_NAME_RYAKU"));//職名略称
				result.setKoshinDate(recordSet.getDate("KOSHIN_DATE"));					//更新日時
				result.setBiko(recordSet.getString("BIKO"));							//備考
				//...
			}else{
				throw new NoDataFoundException(
						"研究者マスタに該当するデータが見つかりません。検索キー：'研究者番号'"
						+ primaryKey.getKenkyuNo()
						+ " '所属機関コード'" + primaryKey.getShozokuCd()
						+ "");
			}
			
		} catch (SQLException ex) {
			throw new DataAccessException("研究者マスタ検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return result;
	}
	
	/**
	 * 未登録申請者情報を取得する。
	 * 
	 * @param userInfo
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public Page searchUnregist(UserInfo userInfo, ShinseishaSearchInfo searchInfo)
	throws ApplicationException {
		
		//所属機関コード取得
		String shozokuCd = EscapeUtil.toSqlString(searchInfo.getShozokuCd());
		
		String sql = "";
		
		if(userInfo.getRole().equals(UserRole.BUKYOKUTANTO)){
			//部局担当者のとき
			BukyokutantoInfo info = userInfo.getBukyokutantoInfo();
			
			if(info.getTantoFlg()){
				//担当部局の設定があるとき
				sql =
					" TANTOBUKYOKUKANRI TANTO"
						+ " INNER JOIN MASTER_KENKYUSHA KENKYU2"
						+ " ON TANTO.BUKYOKU_CD = KENKYU2.BUKYOKU_CD"
						+ " AND KENKYU2.SHOZOKU_CD = '" + shozokuCd + "'"
						//2005/04/21　追加 ここから----------------------------------------
						//研究所マスタに削除フラグ追加のため
						+ " AND KENKYU2.DEL_FLG = 0"
						//追加 ここまで----------------------------------------------------
						+ " WHERE"
						+ " TANTO.BUKYOKUTANTO_ID = '" + EscapeUtil.toSqlString(info.getBukyokutantoId()) + "'";
			
			//2005/04/19 追加 ここから-------------------------------------------------
			//理由 部局担当者で担当部局の設定がない場合の処理を追加
			}else{
				sql = " MASTER_KENKYUSHA KENKYU2"
					+ " WHERE"
					+ " KENKYU2.DEL_FLG = 0 ";
			}
			//追加 ここまで------------------------------------------------------------
			
		}else{
			sql = " MASTER_KENKYUSHA KENKYU2"
				+ " WHERE"
				+ " KENKYU2.DEL_FLG = 0 ";
		}
		
		
		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		String select =
			"SELECT"
				+ " KENKYU.KENKYU_NO,"
				+ " KENKYU.NAME_KANJI_SEI,"
				+ " KENKYU.NAME_KANJI_MEI,"
				+ " KENKYU.NAME_KANA_SEI,"
				+ " KENKYU.NAME_KANA_MEI,"
				+ " KENKYU.SEIBETSU,"
				+ " KENKYU.BIRTHDAY,"
				+ " KENKYU.GAKUI,"
				+ " KENKYU.SHOZOKU_CD,"
				+ " KENKYU.BUKYOKU_CD,"
				+ " BUKYOKU.BUKA_RYAKUSHO,"
				+ " KENKYU.SHOKUSHU_CD,"
				+ " SHOKU.SHOKUSHU_NAME_RYAKU,"
				+ " KENKYU.KOSHIN_DATE,"
				+ " KENKYU.BIKO"
				+ " FROM"
				+ "("
					+ "SELECT"
						+ " KENKYU2.KENKYU_NO,"
						+ " KENKYU2.NAME_KANA_SEI,"
						+ " KENKYU2.NAME_KANA_MEI,"
						+ " KENKYU2.NAME_KANJI_SEI,"
						+ " KENKYU2.NAME_KANJI_MEI,"
						+ " KENKYU2.SEIBETSU,"
						+ " KENKYU2.BIRTHDAY,"
						+ " KENKYU2.GAKUI,"
						+ " KENKYU2.SHOZOKU_CD,"
						+ " KENKYU2.BUKYOKU_CD,"
						+ " KENKYU2.SHOKUSHU_CD,"
						+ " KENKYU2.KOSHIN_DATE,"
						+ " KENKYU2.BIKO"
					+ " FROM"
					+ sql
					+ " MINUS"
					+ " SELECT"
						+ " MK.KENKYU_NO,"
						+ " MK.NAME_KANA_SEI,"
						+ " MK.NAME_KANA_MEI,"
						+ " MK.NAME_KANJI_SEI,"
						+ " MK.NAME_KANJI_MEI,"
						+ " MK.SEIBETSU,"
						+ " MK.BIRTHDAY,"
						+ " MK.GAKUI,"
						+ " MK.SHOZOKU_CD,"
						+ " MK.BUKYOKU_CD,"
						+ " MK.SHOKUSHU_CD,"
						+ " MK.KOSHIN_DATE,"
						+ " MK.BIKO"
					+ " FROM"
						+ " SHINSEISHAINFO SH"
						+ " INNER JOIN MASTER_KENKYUSHA MK"
						//2005.06.16 iso 違う所属機関に同じ研究者番号の研究者がいると未登録一覧に表示されないバグを修正
//						+ " ON SH.KENKYU_NO = MK.KENKYU_NO"
						+ " ON (SH.KENKYU_NO = MK.KENKYU_NO AND SH.SHOZOKU_CD = MK.SHOZOKU_CD)"
						+ " AND MK.SHOZOKU_CD = '" + shozokuCd + "'"
						//2005/04/21　追加 ここから----------------------------------------
						//研究所マスタに削除フラグ追加のため
						+ " AND MK.DEL_FLG = 0"
						//追加 ここまで----------------------------------------------------
						+ " WHERE"
						+ " SH.DEL_FLG = 0"
				+ " ) KENKYU"
					+ " INNER JOIN MASTER_BUKYOKU BUKYOKU"
						+ " ON KENKYU.BUKYOKU_CD = BUKYOKU.BUKYOKU_CD"
						+ " INNER JOIN MASTER_SHOKUSHU SHOKU"
						+ " ON KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD";
		
		StringBuffer query = new StringBuffer(select);
		
		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	//申請者氏名（漢字-姓）
			query.append(" AND KENKYU.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	//申請者氏名（漢字-名）
			query.append(" AND KENKYU.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	//申請者氏名（フリガナ-姓）
			query.append(" AND KENKYU.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
		}
		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){	//申請者氏名（フリガナ-名）
			query.append(" AND KENKYU.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
		}
		if(searchInfo.getBukyokuCd() != null && !searchInfo.getBukyokuCd().equals("")){		//部局コード
			query.append(" AND KENKYU.BUKYOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getBukyokuCd()) +"'");
		
		}
		if(searchInfo.getKenkyuNo() != null && !searchInfo.getKenkyuNo().equals("")){			//研究者番号
			query.append(" AND KENKYU.KENKYU_NO = '" + EscapeUtil.toSqlString(searchInfo.getKenkyuNo()) +"'");
		}
		//2005/04/19 追加 ここから-------------------------------------------------------------
		//理由 自分の所属する申請者の情報のみを表示するように条件を追加
		if(shozokuCd != null && !shozokuCd.equals("")){
			query.append(" AND KENKYU.SHOZOKU_CD = '" + shozokuCd +"'");
		}
		//追加 ここまで------------------------------------------------------------------------

		//ソート順（申請者IDの昇順）
		query.append(" ORDER BY KENKYU.KENKYU_NO");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		
		//-----------------------
		// ページ取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection,searchInfo, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"未登録申請者データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * 研究者情報の数を取得する。
	 * 
	 * @param connection			コネクション
	 * @param searchInfo			研究者情報
	 * @return						研究者情報数
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 */
	public int countKenkyushaInfo(
		Connection connection,
		KenkyushaInfo searchInfo)
		throws DataAccessException {
		return countKenkyushaInfo(connection, searchInfo, true);
	}
	
	//2005/04/15 追加 ここから---------------------------------------------------------------
	//研究者情報の処理のため
	/**
	 * 研究者情報の数を取得する。
	 * 
	 * @param connection			コネクション
	 * @param searchInfo			研究者情報
	 * @param delFlg				削除フラグチェックするか
	 * @return						研究者情報数
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 */
	public int countKenkyushaInfo(
		Connection connection,
		KenkyushaInfo searchInfo, boolean delFlg)
		throws DataAccessException {
			
		int count = 0;	
		String query =
				"SELECT COUNT(*)"
					+ " FROM MASTER_KENKYUSHA"
					+ " WHERE KENKYU_NO = ?"
					+ " AND SHOZOKU_CD = ?";
					//2005/04/21　追加 ここから----------------------------------------
					//研究所マスタに削除フラグ追加のため
		//2005/8/26 
		if (delFlg){
			query = query + " AND DEL_FLG = 0";
		}
				//追加 ここまで----------------------------------------------------
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			//研究者番号
			DatabaseUtil.setParameter(preparedStatement, i++, searchInfo.getKenkyuNo());
			//所属CD
			DatabaseUtil.setParameter(preparedStatement, i++, searchInfo.getShozokuCd());
			recordSet = preparedStatement.executeQuery();
			
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
		} catch (SQLException ex) {
			throw new DataAccessException("研究者情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return count;
	}
	
	
	/**
	 * 研究者情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する部局情報
	 * @throws DataAccessException		登録に例外が発生した場合。
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertKenkyushaInfo(Connection connection,KenkyushaInfo addInfo)
		throws DataAccessException,DuplicateKeyException {
		
		//重複登録チェック
		int count = countKenkyushaInfo(connection, addInfo);
		if(count > 0){//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		}
		
		String query = "INSERT INTO MASTER_KENKYUSHA "
					+ "("
					+ " KENKYU_NO"
					+ ",NAME_KANA_SEI"
					+ ",NAME_KANA_MEI"
					+ ",NAME_KANJI_SEI"
					+ ",NAME_KANJI_MEI"
					+ ",SEIBETSU"
					+ ",BIRTHDAY"
					+ ",GAKUI"
					+ ",SHOZOKU_CD"
					+ ",BUKYOKU_CD"
					+ ",SHOKUSHU_CD"
					+ ",OUBO_SHIKAKU"
					//2007/4/27　追加
					+ ",OTHER_KIKAN1_FLG"
					+ ",OTHER_KIKAN1_CD"
					+ ",OTHER_KIKAN1_NAME"
					+ ",OTHER_KIKAN2_FLG"
					+ ",OTHER_KIKAN2_CD"
					+ ",OTHER_KIKAN2_NAME"
					+ ",OTHER_KIKAN3_FLG"
					+ ",OTHER_KIKAN3_CD"
					+ ",OTHER_KIKAN3_NAME"
					+ ",OTHER_KIKAN4_FLG"
					+ ",OTHER_KIKAN4_CD"
					+ ",OTHER_KIKAN4_NAME"
					//2007/4/27　追加完了
					+ ",KOSHIN_DATE"
					+ ",BIKO"
					//2005/04/21　追加 ここから----------------------------------------
					//研究所マスタに削除フラグ追加のため
					+ ",DEL_FLG"				//削除フラグ
					//追加 ここまで----------------------------------------------------
					+ ") "
					+ "VALUES "
					+ "(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)"
					;

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSeibetsu());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBirthday());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getGakui());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuCd());
			//2006/02/08　追加　ここから----------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOuboShikaku());

			//2007/4/27 追加
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanFlg1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanCd1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanName1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanFlg2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanCd2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanName2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanFlg3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanCd3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanName3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanFlg4());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanCd4());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanName4());
			//2007/4/27 追加完了
			
			//2005/04/22　追加　ここから----------------------------------------------
			//更新日付をKenkyushaInfoの値で渡すように変更 
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKoshinDate());
			//追加 ここまで-----------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			//2005/04/22　追加　ここから----------------------------------------------
			//削除フラグをKenkyushaInfoの値で渡すように変更 
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getDelFlg());
			//追加 ここまで-----------------------------------------------------------
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("研究者マスタ登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
		
		
	/**
	 * 研究者情報を更新する。
	 * 
	 * @param connection			コネクション
	 * @param addInfo				更新する研究者情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateKenkyushaInfo(
		Connection connection,
		KenkyushaInfo updateInfo)
		throws DataAccessException, ApplicationException{
	
		String query =
			"UPDATE MASTER_KENKYUSHA"
				+ " SET"
					+ " KENKYU_NO = ? "
					+ ",NAME_KANA_SEI = ? "
					+ ",NAME_KANA_MEI = ? "
					+ ",NAME_KANJI_SEI = ? "
					+ ",NAME_KANJI_MEI = ? "
					+ ",SEIBETSU = ? "
					+ ",BIRTHDAY = ? "
					+ ",GAKUI = ? "
					+ ",SHOZOKU_CD = ? "
					+ ",BUKYOKU_CD = ? "
					+ ",SHOKUSHU_CD = ? "
					+ ",OUBO_SHIKAKU = ?"
					//2007/4/27　追加
					+ ",OTHER_KIKAN1_FLG = ?"
					+ ",OTHER_KIKAN1_CD = ?"
					+ ",OTHER_KIKAN1_NAME = ?"
					+ ",OTHER_KIKAN2_FLG = ?"
					+ ",OTHER_KIKAN2_CD = ?"
					+ ",OTHER_KIKAN2_NAME = ?"
					+ ",OTHER_KIKAN3_FLG = ?"
					+ ",OTHER_KIKAN3_CD = ?"
					+ ",OTHER_KIKAN3_NAME = ?"
					+ ",OTHER_KIKAN4_FLG = ?"
					+ ",OTHER_KIKAN4_CD = ?"
					+ ",OTHER_KIKAN4_NAME = ?"
					//2007/4/27　追加完了
					+ ",KOSHIN_DATE = ? "
					+ ",BIKO = ? "
					//2005/04/21　追加 ここから----------------------------------------
					//研究所マスタに削除フラグ追加のため
					+ ",DEL_FLG = ? "				//削除フラグ
					//追加 ここまで----------------------------------------------------
				+ " WHERE"
					+ " KENKYU_NO = ?"
					+ " AND SHOZOKU_CD = ?";

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSeibetsu());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBirthday());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getGakui());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuCd());
			//2006/02/08　追加　ここから----------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOuboShikaku());
			
			//2007/4/27 追加
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanFlg1());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanCd1());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanName1());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanFlg2());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanCd2());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanName2());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanFlg3());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanCd3());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanName3());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanFlg4());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanCd4());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanName4());
			//2007/4/27 追加完了
			
			//2005/04/22　追加　ここから----------------------------------------------
			//更新日付をKenkyushaInfoの値で渡すように変更 
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKoshinDate());
			//追加 ここまで-----------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
			//2005/04/22　追加　ここから----------------------------------------------
			//削除フラグをKenkyushaInfoの値で渡すように変更 
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDelFlg());
			//追加 ここまで-----------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
			DatabaseUtil.executeUpdate(preparedStatement);
			
			//2005/04/22 削除 ここから
			//理由 申請者情報のチェックはMaintenanceクラスで行うように修正
		/*	
			//申請者情報のチェック
			HashMap shinseiMap = new HashMap();
			ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
			searchInfo.setKenkyuNo(updateInfo.getKenkyuNo());
			searchInfo.setShozokuCd(updateInfo.getShozokuCd());
			ShinseishaMaintenance shinsei = new ShinseishaMaintenance();
			boolean existShinseishaInfo = true;
			try{
				Page shinseishaPage = shinsei.search(userInfo, searchInfo);
				shinseiMap = (HashMap)shinseishaPage.getList().get(0);
			}catch(NoDataFoundException e){
				//該当するデータがない場合はそのまま処理を終える
				existShinseishaInfo = false;
			}
			if(existShinseishaInfo
				&& shinseiMap.get("SHINSEISHA_ID") != null 
				&& !shinseiMap.get("SHINSEISHA_ID").equals("")){
				
				ShinseishaInfoDao shinseiDao = new ShinseishaInfoDao(userInfo);
				ShinseishaInfo shinseiInfo = new ShinseishaInfo();
				shinseiInfo.setShinseishaId((String)shinseiMap.get("SHINSEISHA_ID"));
				shinseiInfo.setBirthday(updateInfo.getBirthday());
				shinseiInfo.setBukyokuCd(updateInfo.getBukyokuCd());
				shinseiInfo.setBukyokuName(updateInfo.getBukyokuName());
				shinseiInfo.setBukyokuNameRyaku(updateInfo.getBukyokuNameRyaku());
				shinseiInfo.setKenkyuNo(updateInfo.getKenkyuNo());
				shinseiInfo.setNameKanaMei(updateInfo.getNameKanaMei());
				shinseiInfo.setNameKanaSei(updateInfo.getNameKanaSei());
				shinseiInfo.setNameKanjiMei(updateInfo.getNameKanjiMei());
				shinseiInfo.setNameKanjiSei(updateInfo.getNameKanjiSei());
				shinseiInfo.setShokushuCd(updateInfo.getShokushuCd());
				shinseiInfo.setShokushuNameKanji(updateInfo.getShokushuName());
				shinseiInfo.setShokushuNameRyaku(updateInfo.getShokushuNameRyaku());
				shinseiInfo.setShozokuCd(updateInfo.getShozokuCd());
				shinseiInfo.setShozokuName(updateInfo.getShozokuNameKanji());
				shinseiInfo.setShozokuNameRyaku(updateInfo.getShozokuRyakusho());
				shinseiInfo.setShozokuNameEigo(updateInfo.getShozokuNameEigo());
				shinseiInfo.setBukyokuShubetuName((String)shinseiMap.get("OTHER_BUKYOKU"));
				shinseiInfo.setBukyokuShubetuCd((String)shinseiMap.get("SHUBETU_CD"));
				shinseiInfo.setDelFlg(shinseiMap.get("DEL_FLG").toString());
				shinseiInfo.setHakkoDate((Date)shinseiMap.get("HAKKO_DATE"));
				shinseiInfo.setHakkoshaId((String)shinseiMap.get("HAKKOSHA_ID"));
				shinseiInfo.setHikoboFlg(shinseiMap.get("HIKOBO_FLG").toString());
				shinseiInfo.setNameRoMei((String)shinseiMap.get("NAME_RO_MEI"));
				shinseiInfo.setNameRoSei((String)shinseiMap.get("NAME_RO_SEI"));
				shinseiInfo.setPassword((String)shinseiMap.get("PASSWORD"));
				shinseiInfo.setYukoDate((Date)shinseiMap.get("YUKO_DATE"));
				shinseiDao.updateShinseishaInfo(connection, shinseiInfo);
			}
			*/	
			//削除 ここまで------------------------------------------------------------
			
		} catch (SQLException ex) {
			throw new DataAccessException("研究者情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * 研究者情報を削除する。<BR>
	 * 
	 * 研究者マスタの情報を物理削除した後、申請者情報がある場合は申請者情報のDEL_FLGに1をセットする。
	 * 
	 * @param connection			    コネクション
	 * @param pkInfo				    削除する研究者情報
	 * @throws DataAccessException     削除中に例外が発生した場合
	 */
	public void deleteKenkyushaInfo(
		Connection connection,
		KenkyushaInfo deleteInfo)
		throws DataAccessException, ApplicationException{
		
		String query =
			//2005/04/21　追加 ここから----------------------------------------
			//研究所マスタに削除フラグを追加したため、物理削除から論理削除に変更
			
			//"DELETE FROM MASTER_KENKYUSHA"		
			"UPDATE MASTER_KENKYUSHA SET DEL_FLG = 1"
			
			//追加 ここまで----------------------------------------------------
				+ " WHERE"
				+ "   KENKYU_NO = ?"
				+ " AND"
				+ "   SHOZOKU_CD = ?"
				;
		
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getShozokuCd());
			//研究者マスタのレコード削除
			DatabaseUtil.executeUpdate(preparedStatement);
			
			//2005/08/29 takano 申請者IDは削除しない（ログイン時に研究者マスタの存在チェックをする。）ここから
			////申請者情報の削除処理
			//HashMap shinseiMap = new HashMap();
			//ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
			//searchInfo.setKenkyuNo(deleteInfo.getKenkyuNo());
			//searchInfo.setShozokuCd(deleteInfo.getShozokuCd());
			//ShinseishaMaintenance shinsei = new ShinseishaMaintenance();
			//boolean existShinseishaInfo = true;
			//try{
			//	Page shinseishaPage = shinsei.search(userInfo, searchInfo);
			//	shinseiMap = (HashMap)shinseishaPage.getList().get(0);
			//}catch(NoDataFoundException e){
			//	//該当するデータがない場合はそのまま処理を終える
			//	existShinseishaInfo = false;
			//}
			//if(existShinseishaInfo 
			//	&& shinseiMap.get("SHINSEISHA_ID") != null
			//	&& !shinseiMap.get("SHINSEISHA_ID").equals("")){
			//
			//	ShinseishaInfo shinseishaInfo = new ShinseishaInfo();
			//	ShinseishaInfoDao shinseishaDao = new ShinseishaInfoDao(userInfo);
			//	shinseishaInfo.setShinseishaId((String)shinseiMap.get("SHINSEISHA_ID"));
			//	//申請者情報のDEL_FLGを1にする
			//	shinseishaDao.deleteFlgShinseishaInfo(connection, shinseishaInfo);
			//}
			//2005/08/09 takano 申請者IDは削除しない（ログイン時に研究者マスタの存在チェックをする。）ここまで
			
		} catch (SQLException ex) {
			throw new DataAccessException("研究者情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	
	/**
	 * キーに一致する研究者情報を取得する。
	 * 複数件の研究者情報を研究者マスタから取得する。
	 * 
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @param lock					ロックの有無
	 * @return						研究者情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public KenkyushaInfo getKenkyushaData(Connection connection,KenkyushaPk primaryKey, boolean lock)
		throws DataAccessException, NoDataFoundException {
		
		KenkyushaInfo result = new KenkyushaInfo();
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			StringBuffer query = new StringBuffer();
			
			query.append(
				"SELECT "
				+ " KENKYU.KENKYU_NO"				//研究者番号
				+ ", KENKYU.NAME_KANJI_SEI"			//氏名（漢字等-姓）
				+ ", KENKYU.NAME_KANJI_MEI"			//氏名（漢字等-名）
				+ ", KENKYU.NAME_KANA_SEI"			//氏名（フリガナ-姓）
				+ ", KENKYU.NAME_KANA_MEI"			//氏名（フリガナ-名）
				+ ", KENKYU.SEIBETSU"				//性別
				+ ", KENKYU.BIRTHDAY"				//生年月日
				+ ", KENKYU.GAKUI"					//学位
				+ ", KENKYU.SHOZOKU_CD"				//所属機関コード
				+ ", KENKYU.BUKYOKU_CD"				//部局コード
				+ ", KENKYU.SHOKUSHU_CD"			//職コード
				+ ", KENKYU.KOSHIN_DATE"			//更新日時
				+ ", KENKYU.BIKO"					//備考
				+ ", KIKAN.SHOZOKU_NAME_KANJI"		//所属機関名（和文）
				+ ", KIKAN.SHOZOKU_NAME_EIGO"		//所属機関名（英文）
				+ ", KIKAN.SHOZOKU_RYAKUSHO"		//所属機関名（略称）
				+ ", SHOKU.SHOKUSHU_NAME"			//職名
				+ ", SHOKU.SHOKUSHU_NAME_RYAKU"		//職略称
//ADD Start By Nae 2006/02/27
				+ ", KENKYU.OUBO_SHIKAKU"           //応募資格
//ADD End 				
				+ " FROM MASTER_KENKYUSHA KENKYU"					//研究者マスタ
					+ " INNER JOIN MASTER_KIKAN KIKAN"				//所属機関マスタ
					+ " ON KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD"
					+ " INNER JOIN MASTER_SHOKUSHU SHOKU"			//職種マスタ
					+ " ON KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD"
				+ " WHERE KENKYU.SHOZOKU_CD = ?"
				+ " AND KENKYU.KENKYU_NO = ?"		//主キー：所属機関コード、研究者番号
				//2005/04/21　追加 ここから----------------------------------------
				//研究所マスタに削除フラグ追加のため
				+ " AND KENKYU.DEL_FLG = 0"				//削除フラグ
				//追加 ここまで----------------------------------------------------
			);
			
			//排他制御
			if(lock){
				query.append(" FOR UPDATE");
			}
			
			preparedStatement =
				connection.prepareStatement(query.toString());
			
			int i = 1;
			preparedStatement.setString(i++, primaryKey.getShozokuCd());
			preparedStatement.setString(i++, primaryKey.getKenkyuNo());
			
			recordSet = preparedStatement.executeQuery();
			
			if (recordSet.next()) {
				result.setKenkyuNo(recordSet.getString("KENKYU_NO"));				//研究者番号
				result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));		//氏名（漢字等-姓）
				result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));		//氏名（漢字等-名）
				result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));		//氏名（フリガナ-姓）
				result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));		//氏名（フリガナ-名）
				result.setSeibetsu(recordSet.getString("SEIBETSU"));				//性別
				result.setBirthday(recordSet.getDate("BIRTHDAY"));					//生年月日
				result.setGakui(recordSet.getString("GAKUI"));						//学位
				result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));				//所属機関コード
				result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));				//部局コード
				result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));			//職コード
				result.setKoshinDate(recordSet.getDate("KOSHIN_DATE"));				//更新日時
				result.setBiko(recordSet.getString("BIKO"));						//備考
				result.setShozokuNameKanji(recordSet.getString("SHOZOKU_NAME_KANJI"));		//所属機関名（和文）
				result.setShozokuNameEigo(recordSet.getString("SHOZOKU_NAME_EIGO"));		//所属機関名（英文）
				result.setShozokuRyakusho(recordSet.getString("SHOZOKU_RYAKUSHO"));			//所属機関名（略称）
				result.setShokushuName(recordSet.getString("SHOKUSHU_NAME"));				//職名
				result.setShokushuNameRyaku(recordSet.getString("SHOKUSHU_NAME_RYAKU"));	//職名略称
//ADD Start By Nae 2006/02/27
				result.setOuboShikaku(recordSet.getString("OUBO_SHIKAKU"));          //応募資格
//ADD End 				

			}else{
				throw new NoDataFoundException(
						"研究者マスタに該当するデータが見つかりません。検索キー：'研究者番号'"
						+ primaryKey.getKenkyuNo()
						+ " '所属機関コード'" + primaryKey.getShozokuCd()
						+ "");
			}
			
		} catch (SQLException ex) {
			throw new DataAccessException("研究者マスタ検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return result;
	}
	//追加 ここまで--------------------------------------------------------------------------

	//2005/04/22 追加 ここから-----------------------------------------------------------
	//削除フラグを含めた情報取得のため
	/**
	 * キーに一致する研究者情報を取得する。
	 * 削除フラグを含めた研究者情報を研究者マスタから取得する。
	 * 
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @param lock					ロックの有無
	 * @return						研究者情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public KenkyushaInfo select(Connection connection, KenkyushaPk pk)
			throws DataAccessException, NoDataFoundException {
		
	KenkyushaInfo result = new KenkyushaInfo();
		
	PreparedStatement preparedStatement = null;
	ResultSet recordSet = null;
	try {
		StringBuffer query = new StringBuffer();
			
		query.append(
			"SELECT "
			+ " KENKYU_NO"			//研究者番号
			+ ", NAME_KANJI_SEI"			//氏名（漢字等-姓）
			+ ", NAME_KANJI_MEI"			//氏名（漢字等-名）
			+ ", NAME_KANA_SEI"				//氏名（フリガナ-姓）
			+ ", NAME_KANA_MEI"				//氏名（フリガナ-名）
			+ ", SEIBETSU"					//性別
			+ ", BIRTHDAY"					//生年月日
			+ ", GAKUI"						//学位
			+ ", SHOZOKU_CD"				//所属機関コード
			+ ", BUKYOKU_CD"				//部局コード
			+ ", SHOKUSHU_CD"				//職コード
			+ ", KOSHIN_DATE"				//更新日時
			+ ", BIKO"						//備考
			+ ", DEL_FLG"					//削除フラグ
			+ " FROM MASTER_KENKYUSHA KENKYU"					//研究者マスタ
			+ " WHERE KENKYU.SHOZOKU_CD = ?"
			+ " AND KENKYU.KENKYU_NO = ?"		//主キー：所属機関コード、研究者番号
		);
			
		preparedStatement =
			connection.prepareStatement(query.toString());
			
		int i = 1;
		preparedStatement.setString(i++, pk.getShozokuCd());
		preparedStatement.setString(i++, pk.getKenkyuNo());
			
		recordSet = preparedStatement.executeQuery();
			
		if (recordSet.next()) {
			result.setKenkyuNo(recordSet.getString("KENKYU_NO"));				//研究者番号
			result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));		//氏名（漢字等-姓）
			result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));		//氏名（漢字等-名）
			result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));		//氏名（フリガナ-姓）
			result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));		//氏名（フリガナ-名）
			result.setSeibetsu(recordSet.getString("SEIBETSU"));				//性別
			result.setBirthday(recordSet.getDate("BIRTHDAY"));					//生年月日
			result.setGakui(recordSet.getString("GAKUI"));						//学位
			result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));				//所属機関コード
			result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));				//部局コード
			result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));			//職コード
			result.setKoshinDate(recordSet.getDate("KOSHIN_DATE"));				//更新日時
			result.setBiko(recordSet.getString("BIKO"));						//備考
			result.setDelFlg(recordSet.getString("DEL_FLG"));					//削除フラグ

		}else{
			throw new NoDataFoundException(
					"研究者マスタに該当するデータが見つかりません。検索キー：'研究者番号'"
					+ pk.getKenkyuNo()
					+ " '所属機関コード'" + pk.getShozokuCd()
					+ "");
		}
			
		} catch (SQLException ex) {
			throw new DataAccessException("研究者マスタ検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return result;
	}
	//	追加 ここまで---------------------------------------------------------------------

	/**
	 * 研究者名簿ダウンロードデータを取得する
	 * @return List ログイン者所属機関の研究者名簿データ
	 */
	public List selectKenkyushaMeiboInfo(Connection connection)
	        throws DataAccessException, NoDataFoundException {

		StringBuffer query = new StringBuffer(1024);

		query.append("SELECT")
		     .append(" KENKYU.KENKYU_NO \"研究者番号\"") 										// 研究者番号
		     .append(",KENKYU.NAME_KANJI_SEI ||'　'|| KENKYU.NAME_KANJI_MEI \"氏名（漢字）\"")	// 氏名（漢字）
		     .append(",KENKYU.NAME_KANA_SEI ||'　'|| KENKYU.NAME_KANA_MEI \"氏名（カナ）\"") 	// 氏名（フリガナ-姓）
		     .append(",KENKYU.SEIBETSU \"性別番号\"") 											// 性別番号
		     .append(",DECODE(KENKYU.SEIBETSU,'1','男','2','女','') \"性別\"")					// 性別
		     .append(",KENKYU.SHOZOKU_CD \"機関番号\"") 										// 所属機関コード
		     .append(",KIKAN.SHOZOKU_NAME_KANJI \"機関名\"") 									// 所属機関名（和文）
		     .append(",KENKYU.BUKYOKU_CD \"部局番号\"") 										// 部局コード
		     .append(",BUKYOKU.BUKA_NAME \"部局名\"") 											// 部局名
		     .append(",KENKYU.SHOKUSHU_CD \"職番号\"") 											// 職コード
		     .append(",SHOKU.SHOKUSHU_NAME \"職名\"") 											// 職名
		     .append(",KENKYU.GAKUI \"学位番号\"")												// 学位番号
		     .append(",DECODE(KENKYU.GAKUI,'10','修士','11','博士','') \"学位\"") 				// 学位
		     .append(",TO_CHAR(KENKYU.BIRTHDAY, 'YYYY/MM/DD') \"生年月日\"")					// 生年月日
		     .append(",KENKYU.OTHER_KIKAN1_FLG \"他の機関1（委嘱先マーク）\"")
		     .append(",KENKYU.OTHER_KIKAN1_CD \"他の機関番号1\"")
		     .append(",KENKYU.OTHER_KIKAN1_NAME \"他の機関名1\"")
		     .append(",KENKYU.OTHER_KIKAN2_FLG \"他の機関2（委嘱先マーク）\"")
		     .append(",KENKYU.OTHER_KIKAN2_CD \"他の機関番号2\"")
		     .append(",KENKYU.OTHER_KIKAN2_NAME \"他の機関名2\"")
		     .append(",KENKYU.OTHER_KIKAN3_FLG \"他の機関3（委嘱先マーク）\"")
		     .append(",KENKYU.OTHER_KIKAN3_CD \"他の機関番号3\"")
		     .append(",KENKYU.OTHER_KIKAN3_NAME \"他の機関名3\"")
		     .append(",KENKYU.OTHER_KIKAN4_FLG \"他の機関4（委嘱先マーク）\"")
		     .append(",KENKYU.OTHER_KIKAN4_CD \"他の機関番号4\"")
		     .append(",KENKYU.OTHER_KIKAN4_NAME \"他の機関名4\"")
		     .append(" FROM MASTER_KENKYUSHA KENKYU")					// 研究者マスタ
		     .append(" INNER JOIN MASTER_KIKAN KIKAN") 					// 所属機関マスタ
		     .append(" ON KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD")
		     .append(" INNER JOIN MASTER_BUKYOKU BUKYOKU") 				// 部局マスタ
		     .append(" ON KENKYU.BUKYOKU_CD = BUKYOKU.BUKYOKU_CD")
		     .append(" INNER JOIN MASTER_SHOKUSHU SHOKU") 				// 職種マスタ
		     .append(" ON KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD")
		     .append(" WHERE KENKYU.SHOZOKU_CD = '" + userInfo.getShozokuInfo().getShozokuCd() + "'")// 主キー：所属機関コード
		     .append(" AND KENKYU.DEL_FLG = 0")			        // 研究所マスタに削除フラグ追加のため
		     .append(" ORDER BY KENKYU.KENKYU_NO")				// 研究者番号の昇順（2007-05-25 takano）
		     ;

		if (log.isDebugEnabled()){
			log.debug("query:" + query.toString());
		}

        //CSVリスト取得（カラム名をキー項目とする）
        List csvDataList = SelectUtil.selectCsvList(connection, query.toString(), true);

        return csvDataList;

	}
}
