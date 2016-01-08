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
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * 申請者情報データアクセスクラス。
 * ID RCSfile="$RCSfile: ShinseishaInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class ShinseishaInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** 申請者情報管理シーケンス名 */
	public static final String SEQ_SHINSEISHAINFO = "SEQ_SHINSEISHAINFO";
	
	/** 所属機関情報管理シーケンスの取得桁数（連番用） */
	public static final int SEQ_FIGURE = 4;

	/** ログ */
	protected static final Log log = LogFactory.getLog(ShinseishaInfoDao.class);

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
	public ShinseishaInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * キーに一致する申請者情報を取得する。
	 * 研究者マスタに存在しない応募者は除外する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						申請者情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public ShinseishaInfo selectShinseishaInfo(
		Connection connection,
		ShinseishaPk primaryKeys)
		throws DataAccessException, NoDataFoundException {
		//研究者マスタに存在しない応募者は除外する。
		return selectShinseishaInfo(connection, primaryKeys, true);
	}


	/**
	 * キーに一致する申請者情報を取得する。
	 * 
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @param masterCheck			研究者マスタに登録されているもののみ[true]
	 * 								研究者マスタに関係無く[false]
	 * @return						申請者情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public ShinseishaInfo selectShinseishaInfo(
		Connection connection,
		ShinseishaPk primaryKeys,
		boolean masterCheck)
		throws DataAccessException, NoDataFoundException {
		
		//2005/09/12 研究者マスタとの結合条件
		String innerMaster = "";
//add start dyh 2006/2/14
		String innerColumnNm = "";
//add end dyh 2006/2/14
		if(masterCheck){
			innerMaster = " INNER JOIN MASTER_KENKYUSHA MK "
						 +" ON  A.KENKYU_NO  = MK.KENKYU_NO "
						 +" AND A.SHOZOKU_CD = MK.SHOZOKU_CD "
						 +" AND MK.DEL_FLG = 0 "
						 ;
//add start dyh 2006/2/14
			innerColumnNm = ",MK.OUBO_SHIKAKU";
//add end dyh 2006/2/14
		}
		
		String query =
			"SELECT "
				+ " A.SHINSEISHA_ID"
				+ " ,A.SHOZOKU_CD"
				+ " ,NVL(A.SHOZOKU_NAME,'') SHOZOKU_NAME"
				+ " ,NVL(A.SHOZOKU_NAME_EIGO,'') SHOZOKU_NAME_EIGO"
				+ " ,NVL(A.SHOZOKU_NAME_RYAKU,'') SHOZOKU_NAME_RYAKU"
				+ " ,A.PASSWORD"
				+ " ,NVL(A.NAME_KANJI_SEI,'') NAME_KANJI_SEI"
				+ " ,NVL(A.NAME_KANJI_MEI,'') NAME_KANJI_MEI"
				+ " ,NVL(A.NAME_KANA_SEI,'') NAME_KANA_SEI"
				+ " ,NVL(A.NAME_KANA_MEI,'') NAME_KANA_MEI"
				+ " ,NVL(A.NAME_RO_SEI,'') NAME_RO_SEI"
				+ " ,NVL(A.NAME_RO_MEI,'') NAME_RO_MEI"
				+ " ,A.BUKYOKU_CD"
				+ " ,NVL(A.BUKYOKU_NAME,'') BUKYOKU_NAME"
				+ " ,NVL(A.BUKYOKU_NAME_RYAKU,'') BUKYOKU_NAME_RYAKU"
//				+ " ,NVL(A.SHUBETU_CD,'') SHUBETU_CD"
//				+ " ,NVL(A.OTHER_BUKYOKU,'') OTHER_BUKYOKU"
				+ " ,A.KENKYU_NO"
				+ " ,A.SHOKUSHU_CD"
				+ " ,NVL(A.SHOKUSHU_NAME_KANJI,'') SHOKUSHU_NAME_KANJI"
				+ " ,NVL(A.SHOKUSHU_NAME_RYAKU,'') SHOKUSHU_NAME_RYAKU"
				+ " ,A.HIKOBO_FLG"
				+ " ,NVL(A.BIKO,'') BIKO"
				+ " ,A.YUKO_DATE"
				+ " ,A.DEL_FLG"
//add start dyh 2006/02/14
				+ innerColumnNm
//add end dyh 2006/02/14
				//	2005/03/28 追加 ここから---------------------------------
				//	理由 生年月日を表示するため
				+ " ,A.BIRTHDAY"
				//	追加 ここまで--------------------------------------------
				
				//2005.10.06 iso 発行者ID・発行者日付を追加
				+ ", A.HAKKOSHA_ID"
				+ " ,A.HAKKO_DATE"
				
				+ " FROM SHINSEISHAINFO A"
				+ innerMaster
				+ " WHERE SHINSEISHA_ID = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShinseishaInfo result = new ShinseishaInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,primaryKeys.getShinseishaId());
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setShinseishaId(recordSet.getString("SHINSEISHA_ID"));
				result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
				result.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
				result.setShozokuNameEigo(recordSet.getString("SHOZOKU_NAME_EIGO"));
				result.setShozokuNameRyaku(recordSet.getString("SHOZOKU_NAME_RYAKU"));
				result.setPassword(recordSet.getString("PASSWORD"));
				result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));
				result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));
				result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
				result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
				result.setNameRoSei(recordSet.getString("NAME_RO_SEI"));
				result.setNameRoMei(recordSet.getString("NAME_RO_MEI"));
				result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));
				result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
				result.setBukyokuNameRyaku(recordSet.getString("BUKYOKU_NAME_RYAKU"));
//				result.setBukyokuShubetuCd(recordSet.getString("SHUBETU_CD"));
//				result.setBukyokuShubetuName(recordSet.getString("OTHER_BUKYOKU"));
				result.setKenkyuNo(recordSet.getString("KENKYU_NO"));
				result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));
				result.setShokushuNameKanji(recordSet.getString("SHOKUSHU_NAME_KANJI"));
				result.setShokushuNameRyaku(recordSet.getString("SHOKUSHU_NAME_RYAKU"));
				result.setHikoboFlg(recordSet.getString("HIKOBO_FLG"));
				result.setBiko(recordSet.getString("BIKO"));
				result.setDelFlg(recordSet.getString("DEL_FLG"));
				result.setYukoDate(recordSet.getDate("YUKO_DATE"));
				//	2005/03/28 追加 ここから---------------------------------
				//	理由 生年月日を表示するため
				result.setBirthday(recordSet.getDate("BIRTHDAY"));				
				//	追加 ここまで--------------------------------------------	
				
				//2005.10.06 iso 発行者ID・発行者日付を追加		
				result.setHakkoshaId(recordSet.getString("HAKKOSHA_ID"));	
				result.setHakkoDate(recordSet.getDate("HAKKO_DATE"));	
//				2006/02/09 追加 ここから---------------------------------
				result.setOuboshikaku(recordSet.getString("OUBO_SHIKAKU"));
					
				return result;
			} else {
				throw new NoDataFoundException(
					"申請者情報テーブルに該当するデータが見つかりません。検索キー：申請者ID'"
						+ primaryKeys.getShinseishaId()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("申請者情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * 申請者情報の数を取得する。削除フラグが「0」の場合のみ検索する。
	 * @param connection			コネクション
	 * @param searchInfo			申請者情報
	 * @return						申請者情報数
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 */
	public int countShinseishaInfo(
		Connection connection,
		ShinseishaInfo searchInfo)
		throws DataAccessException {
		String query =
			"SELECT COUNT(*)"
				+ " FROM SHINSEISHAINFO"
				+ " WHERE SHINSEISHA_ID <> ?"
				+ " AND SHOZOKU_CD = ?"
				+ " AND KENKYU_NO = ?"
//				+ " AND SHOZOKU_NAME = ?"
//				+ " AND NAME_KANJI_SEI = ?"
//				+ " AND NAME_KANJI_MEI = ?"
//				+ " AND BUKYOKU_CD = ?"
//				+ " AND SHUBETU_CD = ?"
//				+ " AND SHOKUSHU_CD = ?"
				+ " AND DEL_FLG = 0";		//削除フラグ
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShinseishaInfo result = new ShinseishaInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			if(searchInfo.getShinseishaId() != null && !searchInfo.getShinseishaId().equals("")) {
				DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShinseishaId());
			} else {
				DatabaseUtil.setParameter(preparedStatement,i++,"0");
			}
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getKenkyuNo());
//			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShozokuName());
//			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getNameKanjiSei());
//			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getNameKanjiMei());
//			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getBukyokuCd());
//			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getBukyokuShubetuCd());
//			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShokushuCd());
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("申請者情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * 申請者情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する申請者情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertShinseishaInfo(
		Connection connection,
		ShinseishaInfo addInfo)
		throws DataAccessException, DuplicateKeyException {
			
		//重複チェック
		try {
			//研究者マスタに関係無く重複レコードをチェックする
			selectShinseishaInfo(connection, addInfo,false);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}
		
		String query =
			"INSERT INTO SHINSEISHAINFO"
				+ " (SHINSEISHA_ID"
				+ ", SHOZOKU_CD"
				+ ", SHOZOKU_NAME"
				+ ", SHOZOKU_NAME_EIGO"
				+ ", SHOZOKU_NAME_RYAKU"
				+ ", PASSWORD"
				+ ", NAME_KANJI_SEI"
				+ ", NAME_KANJI_MEI"
				+ ", NAME_KANA_SEI"
				+ ", NAME_KANA_MEI"
				+ ", NAME_RO_SEI"
				+ ", NAME_RO_MEI"
				+ ", BUKYOKU_CD"
				+ ", BUKYOKU_NAME"
				+ ", BUKYOKU_NAME_RYAKU"
//				+ ", SHUBETU_CD"
//				+ ", OTHER_BUKYOKU"
				+ ", KENKYU_NO"
				+ ", SHOKUSHU_CD"
				+ ", SHOKUSHU_NAME_KANJI"
				+ ", SHOKUSHU_NAME_RYAKU"
				+ ", HIKOBO_FLG"
				+ ", BIKO"
				+ ", YUKO_DATE"
// 2005/03/31 追加 ここから----------------------------------------
// 理由 DB項目追加による
				+ ", BIRTHDAY"			//生年月日
				+ ", HAKKOSHA_ID"		//発行者ID
				+ ", HAKKO_DATE"		//発行日
// 追加 ここまで---------------------------------------------------
				+ ", DEL_FLG)"
				+ " VALUES"
				+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?)";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinseishaId());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuNameEigo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuNameRyaku());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getPassword());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameRoSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameRoMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuNameRyaku());
//			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuShubetuCd());
//			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuShubetuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuNameKanji());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuNameRyaku());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHikoboFlg());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYukoDate());
			
//			 2005/03/31 追加 ここから----------------------------------------
//			 理由 DB項目追加による「生年月日」「発行者ID」　（「発行日」はSYSDATE　）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBirthday());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHakkoshaId());
//			 追加 ここまで---------------------------------------------------
			
			DatabaseUtil.setParameter(preparedStatement, i++, 0);
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("申請者情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * 申請者情報を更新する。
	 * @param connection			コネクション
	 * @param addInfo				更新する申請者情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateShinseishaInfo(
		Connection connection,
		ShinseishaInfo updateInfo)
		throws DataAccessException, NoDataFoundException {
			
		//検索
		selectShinseishaInfo(connection, updateInfo);
	
		String query =
			"UPDATE SHINSEISHAINFO"
				+ " SET"
				+ " SHOZOKU_CD = ?"
				+ ", SHOZOKU_NAME = ?"
				+ ", SHOZOKU_NAME_EIGO = ?"
				+ ", SHOZOKU_NAME_RYAKU = ?"
				+ ", PASSWORD = ?"
				+ ", NAME_KANJI_SEI = ?"
				+ ", NAME_KANJI_MEI = ?"
				+ ", NAME_KANA_SEI = ?"
				+ ", NAME_KANA_MEI = ?"
				+ ", NAME_RO_SEI = ?"
				+ ", NAME_RO_MEI = ?"
				+ ", BUKYOKU_CD = ?"
				+ ", BUKYOKU_NAME= ?"
				+ ", BUKYOKU_NAME_RYAKU= ?"
//				+ ", SHUBETU_CD = ?"
//				+ ", OTHER_BUKYOKU = ?"
				+ ", KENKYU_NO = ?"
				+ ", SHOKUSHU_CD = ?"
				+ ", SHOKUSHU_NAME_KANJI = ?"
				+ ", SHOKUSHU_NAME_RYAKU = ?"
				+ ", HIKOBO_FLG = ?"
				+ ", BIKO = ?"
				+ ", YUKO_DATE = ?"
				+ ", DEL_FLG = ?";
		//2005/04/18 追加 ここから----------------------------------------------------------
		//理由　研究者情報修正･削除時の対応のため
		if(updateInfo.getBirthday() != null){
				query += ", BIRTHDAY = ?";
		}
		if(updateInfo.getHakkoshaId() != null){
				query += ", HAKKOSHA_ID = ?";
		}
		if(updateInfo.getHakkoDate() != null){
    			query += ", HAKKO_DATE = ?";
		}
		//追加 ここまで---------------------------------------------------------------------
		
		query = query
					+ " WHERE"
					+ " SHINSEISHA_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuNameEigo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuNameRyaku());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getPassword());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameRoSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameRoMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuNameRyaku());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuShubetuCd());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuShubetuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuNameKanji());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuNameRyaku());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHikoboFlg());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getYukoDate());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDelFlg());
			//2005/04/18 追加 ここから----------------------------------------------------------
			//理由　研究者情報修正･削除時の対応のため
			if(updateInfo.getBirthday() != null){
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBirthday());
			}
			if(updateInfo.getHakkoshaId() != null){
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHakkoshaId());
			}
			if(updateInfo.getHakkoDate() != null){
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHakkoDate());
			}
			//追加 ここまで---------------------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinseishaId());
			
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("申請者情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * 申請者情報を更新する。申請者情報テーブルに含まれる所属機関情報を更新する。<br>
	 * 削除フラグに関係なく、所属機関コードが一致するレコードをすべて更新。
	 * @param connection			コネクション
	 * @param updateInfo			所属機関担当者情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateShinseishaInfo(
		Connection connection,
		ShozokuInfo updateInfo)
		throws DataAccessException, NoDataFoundException {
	
		String query =
			"UPDATE SHINSEISHAINFO"
				+ " SET"
				+ " SHOZOKU_NAME = ?"
				+ ",SHOZOKU_NAME_EIGO = ?"
				+ ",SHOZOKU_NAME_RYAKU = ?"
				+ " WHERE"
				+ " SHOZOKU_CD = ?"
				;

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuNameEigo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuRyakusho());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
			preparedStatement.executeUpdate();		//更新件数は、0件～複数件であるため

		} catch (SQLException ex) {
			throw new DataAccessException("申請者情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * 申請者情報を更新する。申請者情報テーブルに含まれる所属機関情報を更新する。<br>
	 * 更新する情報は以下の通り。<br>
	 * <li>所属機関名（和文）</li>
	 * <li>所属機関名（略称）</li>
	 * （※所属機関名（英文）については更新しない。）<br>
	 * @param connection			コネクション
	 * @param updateInfo			所属機関情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateShinseishaInfo(
		Connection connection,
		KikanInfo updateInfo)
		throws DataAccessException, NoDataFoundException {
	
		String query =
			"UPDATE SHINSEISHAINFO"
				+ " SET"
				+ " SHOZOKU_NAME = ?"
				+ ",SHOZOKU_NAME_RYAKU = ?"
				+ " WHERE"
				+ "  SHOZOKU_CD = ?"			//機関コードが同じで...
				+ " AND ( "						//かつ、
				+ "  SHOZOKU_NAME <> ?"			//機関名（和文）が違うか
				+ "  OR "						//または
				+ "  SHOZOKU_NAME_RYAKU <> ?"	//機関名（略称）が違うもの
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
			throw new DataAccessException("申請者情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * 申請者情報を削除する。(削除フラグ) 
	 * @param connection			コネクション
	 * @param addInfo				削除する申請者情報主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteFlgShinseishaInfo(
		Connection connection,
		ShinseishaPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
			
			//検索
			selectShinseishaInfo(connection, deleteInfo);
	
			String query =
				"UPDATE SHINSEISHAINFO"
					+ " SET"
					+ " HAKKOSHA_ID = ? "							//発行者ID
					+ ",HAKKO_DATE = sysdate"						//発行日
					+ ",DEL_FLG = 1"								//削除フラグ
					+ " WHERE"
					+ " SHINSEISHA_ID = ?";

			PreparedStatement preparedStatement = null;
			try {
				//登録
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,userInfo.getId());
				DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getShinseishaId());
				DatabaseUtil.executeUpdate(preparedStatement);

			} catch (SQLException ex) {
				throw new DataAccessException("申請者情報削除中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	

	/**
	 * 申請者情報を削除する。(物理削除) 
	 * @param connection			コネクション
	 * @param addInfo				削除する申請者情報主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteShinseishaInfo(
		Connection connection,
		ShinseishaPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
	}


	/**
	 * ユーザID、パスワードの認証を行う。
	 * @param connection			コネクション
	 * @param userid				ユーザID
	 * @param password				パスワード
	 * @return						認証に成功した場合 true 以外 false
	 * @throws DataAccessException	データベースアクセス中の例外
	 */
	public boolean authenticateShinseishaInfo(
		Connection connection,
		String userid,
		String password)
		throws DataAccessException {
		String query =
			"SELECT count(*)"
				+ " FROM SHINSEISHAINFO SHINSEI"
			//2005/04/26 追加 ここから-------------------------------------		
			//理由 研究者マスタと所属機関マスタの条件追加のため			
				+ " INNER JOIN MASTER_KENKYUSHA KENKYU"
				//2005.08.08 iso その他コード(99999)をはじく為コメント化
//				+ " INNER JOIN MASTER_KIKAN KIKAN"
//				+ " ON KIKAN.SHOZOKU_CD = KENKYU.SHOZOKU_CD"
				+ " ON KENKYU.SHOZOKU_CD = SHINSEI.SHOZOKU_CD"
				+ " AND KENKYU.KENKYU_NO = SHINSEI.KENKYU_NO"
			//追加 ここまで------------------------------------------------
				+ " WHERE SHINSEI.DEL_FLG = 0"
				+ " AND SHINSEI.SHINSEISHA_ID = ?"
				+ " AND SHINSEI.PASSWORD = ?"
			//2005/04/26 追加 ここから-------------------------------------		
			//理由 研究者マスタと所属機関マスタの条件追加のため
				+ " AND KENKYU.DEL_FLG = 0";
			//追加 ここまで------------------------------------------------
			
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		int count = 0;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,userid);
			DatabaseUtil.setParameter(preparedStatement,i++,password);
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			//一致するデータが存在する場合は、true返す
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			throw new DataAccessException("申請者情報テーブル検索実行中に例外が発生しました。 ", ex);
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
	public boolean changePasswordShinseishaInfo(
			Connection connection,
			ShinseishaPk pkInfo,
			String newPassword)
			throws DataAccessException {

		//2005.10.06 iso 申請者自身がパスワード変更した場合、発行者ID・発効日を変更しないよう修正
//		String query = "UPDATE SHINSEISHAINFO"
//						 + " SET"
//						 + " PASSWORD = ? "
//						 + ",HAKKOSHA_ID = ? "							//発行者ID
//						 + ",HAKKO_DATE = sysdate"						//発行日
//						 + " WHERE"
//						 + " SHINSEISHA_ID = ?"
//						 + " AND DEL_FLG = 0";							//削除フラグ

		String addQuery = "";
		if(!UserRole.SHINSEISHA.equals(userInfo.getRole())) {
			addQuery = ",HAKKOSHA_ID = ? "							//発行者ID
					 + ",HAKKO_DATE = sysdate"						//発行日
					 ;
			
		}

		String query = "UPDATE SHINSEISHAINFO"
						 + " SET"
						 + " PASSWORD = ? "
						 + addQuery
						 + " WHERE"
						 + " SHINSEISHA_ID = ?"
						 + " AND DEL_FLG = 0";							//削除フラグ

		PreparedStatement preparedStatement = null;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
	
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,newPassword);				//新しいパスワード
			
			//2005.10.06 iso 申請者自身がパスワード変更した場合、発行者ID・発効日を変更しないよう修正
			if(!UserRole.SHINSEISHA.equals(userInfo.getRole())) {
				DatabaseUtil.setParameter(preparedStatement, i++, userInfo.getId());		//発行者ID
			}
			
			DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getShinseishaId());	//申請者ID
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("パスワード変更中に例外が発生しました。 ", ex);

		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
		return true;
	}

	/**
     *　所属機関毎の順番を取得する。
	 * @param connection           コネクション
	 * @param shozokuCd            所属機関コード
	 * @return                     順番(5桁)
	 * @throws DataAccessException　データベースアクセス中の例外
	 */
	public String getSequenceNo(Connection connection,String shozokuCd) throws DataAccessException {
		String query =
			"SELECT TO_CHAR(MAX(SUBSTR(SHINSEISHA_ID,8,5)) + 1,'FM00000') COUNT"
				+ " FROM SHINSEISHAINFO"
				+ " WHERE SHOZOKU_CD = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shozokuCd);
			recordSet = preparedStatement.executeQuery();
			String ret = null;
			 if (recordSet.next()) {
				ret= recordSet.getString(1);
				if(ret == null){
					ret = "00001";
				}
			 }
			 return ret;
		} catch (SQLException ex) {
			throw new DataAccessException("申請者情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}


	/**
	 * 非公募応募可フラグを外す。
	 * @param connection			コネクション
	 * @param searchInfo			申請者検索情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 */
	public void deleteHikoboFlgInfo(
			Connection connection,
			ShinseishaSearchInfo searchInfo)
			throws DataAccessException {

		String update = "UPDATE SHINSEISHAINFO S SET S.HIKOBO_FLG = 0 WHERE ";			
		
		StringBuffer query = new StringBuffer(update);

		if(!"".equals(searchInfo.getShinseishaId())) {
			query.append("S.SHINSEISHA_ID = '");
			query.append(EscapeUtil.toSqlString(searchInfo.getShinseishaId()));
			query.append("' AND ");
		}
		if(!"".equals(searchInfo.getNameKanjiSei())) {
			query.append("S.NAME_KANJI_SEI LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()));
			query.append("%' AND ");
		}
		if(!"".equals(searchInfo.getNameKanjiMei())) {
			query.append("S.NAME_KANJI_MEI LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()));
			query.append("%' AND ");
		}
		if(!"".equals(searchInfo.getNameKanaSei())) {
			query.append("S.NAME_KANA_SEI LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()));
			query.append("%' AND ");
		}
		if(!"".equals(searchInfo.getNameKanaMei())) {
			query.append("S.NAME_KANA_MEI LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()));
			query.append("%' AND ");
		}
		if(!"".equals(searchInfo.getNameRoSei())) {
			query.append("UPPER(S.NAME_RO_SEI) LIKE '%");						//双方を大文字化して検索条件としないとエラーとなるので注意
			query.append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()));
			query.append("%' AND ");
		}
		if(!"".equals(searchInfo.getNameRoMei())) {
			query.append("UPPER(S.NAME_RO_MEI) LIKE '%");						//双方を大文字化して検索条件としないとエラーとなるので注意
			query.append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()));
			query.append("%' AND ");
		}
		if(!"".equals(searchInfo.getShozokuCd())) {
			query.append("S.SHOZOKU_CD = '");
			query.append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()));
			query.append("' AND ");
		}
		if(!"".equals(searchInfo.getShozokuName())) {
			query.append("(S.SHOZOKU_NAME LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getShozokuName()));
			query.append("%' OR S.SHOZOKU_NAME_RYAKU LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getShozokuName()));
			query.append("%') AND ");
		}
		query.append("S.DEL_FLG = 0");
		
		//2005/09/12 研究者マスタに存在しない場合は除外する
		query.append(" AND S.KENKYU_NO IN ")
 			 .append(" (SELECT MK.KENKYU_NO FROM MASTER_KENKYUSHA MK ") 
			 .append("  WHERE MK.KENKYU_NO  = S.KENKYU_NO ")
			 .append("  AND   MK.SHOZOKU_CD = S.SHOZOKU_CD ")
			 .append("  AND   MK.DEL_FLG = 0) ")
			 ;

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query.toString());
			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			throw new DataAccessException("非公募フラグ削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	//2005/04/18 追加 ここから------------------------------------------------------------
	//理由 研究者の更新・削除時に申請者情報を更新するため
	
	public String selectKenkyusha(Connection connection, KenkyushaPk pk)
		throws DataAccessException{
			
		String ret = null;
		String query =
			"SELECT SHINSEISHA_ID "
				+ " FROM SHINSEISHAINFO"
				+ " WHERE KENKYU_NO = ?" 
				+ " AND SHOZOKU_CD = ?";
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		String kenkyuNo = pk.getKenkyuNo();
		String shozokuCd = pk.getShozokuCd();
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,kenkyuNo);
			DatabaseUtil.setParameter(preparedStatement,i++,shozokuCd);
			recordSet = preparedStatement.executeQuery();
			
			 if (recordSet.next()) {
				ret= recordSet.getString(1);
			 }
		} catch (SQLException ex) {
			throw new DataAccessException("申請者情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return ret;
	}
	
	/**
	 * IDパスワード通知書用CSVデータをDBより取得する。（研究者管理用）
	 * @param connection
	 * @param kenkyuNo 登録した研究員番号
	 * @param sozokuCd 所属機関コード
	 * @return
	 * @throws ApplicationException
	 */
	public List createCSV4Tsuchisho(Connection connection, String[] kenkyuNo, String sozokuCd)
		throws ApplicationException
	{
		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		String select = "SELECT " 
						+ "SI.NAME_KANJI_SEI            \"氏名-姓\""			//氏名-姓
						+ ",SI.NAME_KANJI_MEI           \"氏名-名\""			//氏名-名
						+ ",SI.NAME_KANA_SEI            \"フリガナ-姓\""		//フリガナ-姓
						+ ",SI.NAME_KANA_MEI            \"フリガナ-名\""		//フリガナ-名
						+ ",SI.KENKYU_NO	            \"研究者番号\""		//研究者番号
						+ ",SI.SHOZOKU_CD               \"所属機関コード\""	//所属機関コード	//差込印刷Wordとのリンクが切れるため「所属機関」のまま
						+ ",SI.SHOZOKU_NAME		        \"所属機関名\""		//所属機関名	//差込印刷Wordとのリンクが切れるため「所属機関」のまま
						+ ",SI.BUKYOKU_CD	            \"部局コード\""		//部局コード
						+ ",SI.BUKYOKU_NAME             \"部局名\""			//部局名
						+ ",SI.SHUBETU_CD	            \"部局種別\""		//部局種別
						+ ",SI.SHOKUSHU_NAME_KANJI      \"職名\""			//職名
						+ ",SI.SHINSEISHA_ID            \"ID\""				//ID
						+ ",SI.PASSWORD                 \"パスワード\""		//パスワード
						+ ",ST.BUKYOKU_NAME				\"担当部課名\""		//担当部課名
						+ ",ST.KAKARI_NAME              \"担当係名\""		//担当係名
						//+ ",ST.TANTO_NAME_SEI           \"担当者氏名\""		//担当者氏名
						+ ",ST.TANTO_NAME_SEI || '　' || TANTO_NAME_MEI \"担当者氏名\""
						+ ",ST.TANTO_EMAIL     	        \"Emailアドレス\""	//Emailアドレス
						+ ",ST.TANTO_TEL                \"担当者電話番号\""	//担当者電話番号
						+ ",ST.TANTO_FAX                \"担当者FAX番号\""	//担当者FAX番号
						+ ",ST.TANTO_ZIP                \"担当者郵便番号\""	//担当者郵便番号
						+ ",ST.TANTO_ADDRESS            \"担当者住所\""		//担当者住所
						+ " FROM"
						+ "  SHINSEISHAINFO SI, SHOZOKUTANTOINFO ST"
						+ " WHERE"
						+ "  SI.SHOZOKU_CD = ST.SHOZOKU_CD"
				//2005/04/30 追加 -------------------------------------------ここから
				//理由 削除フラグの条件追加
						+ "  AND SI.DEL_FLG = '0' AND ST.DEL_FLG = '0' "
				//2005/04/30 追加 -------------------------------------------ここまで
						+ " AND SI.SHOZOKU_CD = '" + EscapeUtil.toSqlString(sozokuCd) + "'"	//2005/07/13
						;

		StringBuffer query = new StringBuffer(select);
		

		//対象研究番号
		if(kenkyuNo != null && !kenkyuNo.equals("")){
			//2005/09/01 1000件以上になる場合があるのでINを使わない。
			//query.append(" AND SI.KENKYU_NO IN (")
			//.append(changeArray2CSV(kenkyuNo))
			//.append(")");
			query.append(" AND (");
			for(int i=0; i<kenkyuNo.length; i++){
				query.append(" SI.KENKYU_NO = '")
					 .append(EscapeUtil.toSqlString(kenkyuNo[i]))
					 .append("' ");
				if(i!=kenkyuNo.length-1){
					query.append(" OR ");
				}
			}
			query.append(")");
			
			
		}

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----DBレコード取得-----
		try{
			return SelectUtil.selectCsvList(connection, query.toString(), true);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"CSV出力データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4008"),
				e);
		}
		
	}
	
	/**
	 * 
	 * @param array
	 * @return
	 */
	private static String changeArray2CSV(String[] array){
		return StringUtil.changeArray2CSV(array, true);
	}
	

	//2005.09.26 iso 多重登録防止のため追加
	/**
	 * 同一「研究者番号・所属機関コード」の申請者情報数を取得する。
	 * 削除フラグが「0」の場合のみ検索する。
	 * @param connection			コネクション
	 * @param searchInfo			申請者情報
	 * @return						申請者情報数
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 */
	public int countShinseishaInfoPreInsert(
			Connection connection,
			ShinseishaInfo searchInfo)
			throws DataAccessException {
		String query =
			"SELECT COUNT(*)"
				+ " FROM SHINSEISHAINFO"
				+ " WHERE SHOZOKU_CD = ?"
				+ " AND KENKYU_NO = ?"
				+ " AND DEL_FLG = 0";		//削除フラグ
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShinseishaInfo result = new ShinseishaInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getKenkyuNo());
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("申請者情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

}
