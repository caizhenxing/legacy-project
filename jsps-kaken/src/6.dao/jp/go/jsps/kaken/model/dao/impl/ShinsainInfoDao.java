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

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * 審査員情報データアクセスクラス。
 * ID RCSfile="$RCSfile: ShinsainInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class ShinsainInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** 審査員情報管理シーケンス名 */
	public static final String SEQ_SHINSAININFO = "SEQ_SHINSAININFO";

	/** ログ */
	protected static final Log log = LogFactory.getLog(ShinsainInfoDao.class);

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
	public ShinsainInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * キーに一致する審査員情報を取得する。
	 * @param connection			コネクション
	 * @param pkInfo				主キー情報
	 * @return						審査員情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public ShinsainInfo selectShinsainInfo(
			Connection connection,
			ShinsainPk pkInfo)
			throws DataAccessException, NoDataFoundException {
 
//2006/04/23 追加ここから
        //申請書の事業区分
        String jigyoKubun = pkInfo.getJigyoKubun();
        //審査員の事業区分のSet
        Set shinsainSet = JigyoKubunFilter.Convert2ShinsainJigyoKubun(jigyoKubun);
        //審査員の事業区分
        String shinsainJigyoKubun = StringUtil.changeIterator2CSV(shinsainSet.iterator(),true);
//苗　追加ここまで
        
		String query = "SELECT SI.SHINSAIN_ID SHINSAIN_ID"
			+ ", MS.SHINSAIN_NO SHINSAIN_NO"
			+ ", MS.JIGYO_KUBUN JIGYO_KUBUN"
			+ ", MS.NAME_KANJI_SEI NAME_KANJI_SEI"
			+ ", MS.NAME_KANJI_MEI NAME_KANJI_MEI"
			+ ", MS.NAME_KANA_SEI NAME_KANA_SEI"
			+ ", MS.NAME_KANA_MEI NAME_KANA_MEI"
			+ ", MS.SHOZOKU_CD SHOZOKU_CD"
			+ ", MS.SHOZOKU_NAME SHOZOKU_NAME"
//			+ ", MS.BUKYOKU_CD BUKYOKU_CD"
			+ ", MS.BUKYOKU_NAME BUKYOKU_NAME"
//			+ ", MS.SHOKUSHU_CD SHOKUSHU_CD"
			+ ", MS.SHOKUSHU_NAME SHOKUSHU_NAME"
//			+ ", MS.KEI_CD KEI_CD"
//			+ ", MS.SHINSA_KAHI SHINSA_KAHI"
			+ ", MS.SOFU_ZIP SOFU_ZIP"
			+ ", MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS"
			+ ", MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL"
//			+ ", MS.SOFU_ZIPEMAIL2 SOFU_ZIPEMAIL2"
			+ ", MS.SHOZOKU_TEL SHOZOKU_TEL"
			+ ", MS.SHOZOKU_FAX SHOZOKU_FAX"
//			+ ", MS.JITAKU_TEL JITAKU_TEL"
//			+ ", MS.SINKI_KEIZOKU_FLG SINKI_KEIZOKU_FLG"
//			+ ", MS.KIZOKU_START KIZOKU_START"
//			+ ", MS.KIZOKU_END KIZOKU_END"
//			+ ", MS.SHAKIN SHAKIN"
			+ ", MS.URL URL"
//			+ ", MS.LEVEL_A1 LEVEL_A1"
//			+ ", MS.LEVEL_B1_1 LEVEL_B1_1"
//			+ ", MS.LEVEL_B1_2 LEVEL_B1_2"
//			+ ", MS.LEVEL_B1_3 LEVEL_B1_3"
//			+ ", MS.LEVEL_B2_1 LEVEL_B2_1"
//			+ ", MS.LEVEL_B2_2 LEVEL_B2_2"
//			+ ", MS.LEVEL_B2_3 LEVEL_B2_3"
//			+ ", MS.KEY1 KEY1"
//			+ ", MS.KEY2 KEY2"
//			+ ", MS.KEY3 KEY3"
//			+ ", MS.KEY4 KEY4"
//			+ ", MS.KEY5 KEY5"
//			+ ", MS.KEY6 KEY6"
//			+ ", MS.KEY7 KEY7"
			+ ", MS.SENMON SENMON"
			+ ", MS.KOSHIN_DATE KOSHIN_DATE"
			+ ", MS.BIKO BIKO"
			
			+ ", SI.PASSWORD PASSWORD"
			+ ", SI.YUKO_DATE YUKO_DATE"
			+ ", SI.LOGIN_DATE"				//2005/10/20 最終ログイン日追加
			+ ", SI.MAIL_FLG"				//2005/10/26 メールフラグ追加
			+ ", SI.DEL_FLG DEL_FLG"
//			2006/10/24 易旭 追加ここから
			+ ", SI.DOWNLOAD_FLG"
//			2006/10/24 易旭 追加ここまで 
			+ " FROM MASTER_SHINSAIN MS"
			+ ", SHINSAININFO SI"
			+ " WHERE SI.DEL_FLG = 0"
			+ " AND MS.SHINSAIN_NO = ?"
//			+ " AND MS.JIGYO_KUBUN = ?"
//2006/04/23 追加ここから
            + " AND MS.JIGYO_KUBUN IN ("
            + shinsainJigyoKubun
            + ")"
//苗　追加ここまで    
			+ " AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)"
			+ " AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)"; 

		PreparedStatement ps = null;
		ResultSet rset = null;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		try {
			ShinsainInfo result = new ShinsainInfo();
			ps = connection.prepareStatement(query);
			int i = 1;
			ps.setString(i++, pkInfo.getShinsainNo());
//			ps.setString(i++, pkInfo.getJigyoKubun());
//2006/04/23 苗　削除ここから            
//			if (IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(pkInfo.getJigyoKubun())){
//				ps.setString(i++, IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
//			}else{
//				ps.setString(i++, IJigyoKubun.JIGYO_KUBUN_KIBAN);
//			}
//削除ここまで       
            
			rset = ps.executeQuery();

			if (rset.next()) {
				result.setShinsainId(rset.getString("SHINSAIN_ID"));
				result.setShinsainNo(rset.getString("SHINSAIN_NO"));
				result.setJigyoKubun(rset.getString("JIGYO_KUBUN"));
				result.setNameKanjiSei(rset.getString("NAME_KANJI_SEI"));
				result.setNameKanjiMei(rset.getString("NAME_KANJI_MEI"));
				result.setNameKanaSei(rset.getString("NAME_KANA_SEI"));
				result.setNameKanaMei(rset.getString("NAME_KANA_MEI"));
				result.setShozokuCd(rset.getString("SHOZOKU_CD"));
				result.setShozokuName(rset.getString("SHOZOKU_NAME"));
//				result.setBukyokuCd(rset.getString("BUKYOKU_CD"));
				result.setBukyokuName(rset.getString("BUKYOKU_NAME"));
//				result.setShokushuCd(rset.getString("SHOKUSHU_CD"));
				result.setShokushuName(rset.getString("SHOKUSHU_NAME"));
//				result.setKeiCd(rset.getString("KEI_CD"));
//				result.setShinsaKahi(rset.getString("SHINSA_KAHI"));
				result.setSofuZip(rset.getString("SOFU_ZIP"));
				result.setSofuZipaddress(rset.getString("SOFU_ZIPADDRESS"));
				result.setSofuZipemail(rset.getString("SOFU_ZIPEMAIL"));
//				result.setSofuZipemail2(rset.getString("SOFU_ZIPEMAIL2"));
				result.setShozokuTel(rset.getString("SHOZOKU_TEL"));
				result.setShozokuFax(rset.getString("SHOZOKU_FAX"));
//				result.setJitakuTel(rset.getString("JITAKU_TEL"));
//				result.setSinkiKeizokuFlg(rset.getString("SINKI_KEIZOKU_FLG"));
//				result.setKizokuStart(rset.getDate("KIZOKU_START"));
//				result.setKizokuEnd(rset.getDate("KIZOKU_END"));
//				result.setShakin(rset.getString("SHAKIN"));
				result.setUrl(rset.getString("URL"));
//				result.setLevelA1(rset.getString("LEVEL_A1"));
//				result.setLevelB11(rset.getString("LEVEL_B1_1"));
//				result.setLevelB12(rset.getString("LEVEL_B1_2"));
//				result.setLevelB13(rset.getString("LEVEL_B1_3"));
//				result.setLevelB21(rset.getString("LEVEL_B2_1"));
//				result.setLevelB22(rset.getString("LEVEL_B2_2"));
//				result.setLevelB23(rset.getString("LEVEL_B2_3"));
//				result.setKey1(rset.getString("KEY1"));
//				result.setKey2(rset.getString("KEY2"));
//				result.setKey3(rset.getString("KEY3"));
//				result.setKey4(rset.getString("KEY4"));
//				result.setKey5(rset.getString("KEY5"));
//				result.setKey6(rset.getString("KEY6"));
//				result.setKey7(rset.getString("KEY7"));
				result.setSenmon(rset.getString("SENMON"));
				result.setKoshinDate(rset.getDate("KOSHIN_DATE"));
				result.setBiko(rset.getString("BIKO"));
				result.setPassword(rset.getString("PASSWORD"));
				result.setYukoDate(rset.getDate("YUKO_DATE"));
				result.setLoginDate(rset.getDate("LOGIN_DATE"));
				result.setMailFlg(rset.getString("MAIL_FLG"));
				result.setDelFlg(rset.getString("DEL_FLG"));
//				2006/10/24 易旭 追加ここから
				result.setDownloadFlag(rset.getString("DOWNLOAD_FLG"));
//				2006/10/24 易旭 追加ここまで 
				return result;
			} else {
				throw new NoDataFoundException(
					"審査員情報テーブルに該当するデータが見つかりません。検索キー：審査員番号・事業区分'"
						+ pkInfo.getShinsainNo()
						+ "事業区分"
						+ pkInfo.getJigyoKubun()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("審査員情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(rset, ps);
		}
	}
	
	

	/**
	 * 審査員情報を登録する。
	 * 審査員マスタテーブルと審査員情報テーブルに登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する審査員情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertShinsainInfo(Connection connection, ShinsainInfo addInfo)
		throws DataAccessException, DuplicateKeyException {
		insertShinsainInfo(connection, addInfo, true);
	}
	
	/**
	 * 審査員情報を登録する。
	 * 審査員マスタテーブルのみに登録する。（審査員情報テーブルには登録しない。）
	 * @param connection				コネクション
	 * @param addInfo					登録する審査員情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertShinsainInfoOnlyMaster(Connection connection, ShinsainInfo addInfo)
		throws DataAccessException, DuplicateKeyException {
		insertShinsainInfo(connection, addInfo, false);
	}
	
	/**
	 * 審査員情報を登録する。
	 * 第三引数がtrueの場合、審査員マスタと審査員情報テーブルの両方に登録する。
	 * 第三引数がfalseの場合、審査員マスタテーブルのみに登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する審査員情報
	 * @param bothTable                true：審査員マスタテーブルと審査員情報テーブルに登録する。
	 * 									false：審査員マスタテーブルのみ登録する。
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	private void insertShinsainInfo(Connection connection, 
									  ShinsainInfo addInfo, 
									  boolean bothTable)
		throws DataAccessException, DuplicateKeyException
	{
		//重複チェック
		try {
			selectShinsainInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}

		String query1 =
			"INSERT INTO MASTER_SHINSAIN"
				+ " (SHINSAIN_NO"
				+ ", JIGYO_KUBUN"
				+ ", NAME_KANJI_SEI"
				+ ", NAME_KANJI_MEI"
				+ ", NAME_KANA_SEI"
				+ ", NAME_KANA_MEI"
				+ ", SHOZOKU_CD"
				+ ", SHOZOKU_NAME"
				+ ", BUKYOKU_NAME"
				+ ", SHOKUSHU_NAME"
				+ ", SOFU_ZIP"
				+ ", SOFU_ZIPADDRESS"
				+ ", SOFU_ZIPEMAIL"
				+ ", SHOZOKU_TEL"
				+ ", SHOZOKU_FAX"
				+ ", URL"
				+ ", SENMON"
				+ ", KOSHIN_DATE"
				+ ", BIKO)"
				+ " VALUES"
				+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		String query2 =
			"INSERT INTO SHINSAININFO"
				+ " (SHINSAIN_ID"
				+ ", PASSWORD"
				+ ", YUKO_DATE"
				+ ", LOGIN_DATE"			//2005/10/20　最終ログイン日追加
				+ ", MAIL_FLG"				//2005/10/26  メールフラグ追加
				+ ", DEL_FLG)"
				+ " VALUES"
				+ " (?,?,?,?,?,?)";

		PreparedStatement preparedStatement  = null;
		PreparedStatement preparedStatement2 = null;
		try {
			//審査員マスタ登録
			preparedStatement = connection.prepareStatement(query1);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSofuZip());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSofuZipaddress());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSofuZipemail());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuTel());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuFax());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUrl());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSenmon());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKoshinDate());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			DatabaseUtil.executeUpdate(preparedStatement);

			//フラグがtrueの場合、審査員情報テーブルにも登録する
			if(bothTable){
				i = 1;
				//審査員情報テーブル登録
				preparedStatement2 = connection.prepareStatement(query2);
				DatabaseUtil.setParameter(preparedStatement2,i++,addInfo.getShinsainId());
				DatabaseUtil.setParameter(preparedStatement2,i++,addInfo.getPassword());
				DatabaseUtil.setParameter(preparedStatement2,i++,addInfo.getYukoDate());
				DatabaseUtil.setParameter(preparedStatement2,i++,addInfo.getLoginDate());
				DatabaseUtil.setParameter(preparedStatement2,i++,addInfo.getMailFlg());
				DatabaseUtil.setParameter(preparedStatement2,i++, 0);
				DatabaseUtil.executeUpdate(preparedStatement2);
			}
		
		} catch (SQLException ex) {
			throw new DataAccessException("審査員情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
			DatabaseUtil.closeResource(null, preparedStatement2);
		}
	}
	
	
	
	/**
	 * 審査員情報を更新する。
	 * @param connection			コネクション
	 * @param updateInfo			更新する審査員情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateShinsainInfo(
			Connection connection,
			ShinsainInfo updateInfo)
			throws DataAccessException, NoDataFoundException {
		
		//検索
		selectShinsainInfo(connection, updateInfo);

//2006/05/22　追加ここから        
        Set shinsaiSet = JigyoKubunFilter.Convert2ShinsainJigyoKubun(updateInfo.getJigyoKubun()) ; //審査員の事業区分のSet
        String jigyoKubun= (String)shinsaiSet.iterator().next();
//苗　追加ここまで        

		//審査員マスタTBL更新SQL
		String query1 = "UPDATE MASTER_SHINSAIN"
				+ " SET"
				+ " NAME_KANJI_SEI = ?"
				+ ", NAME_KANJI_MEI = ?"
				+ ", NAME_KANA_SEI = ?"
				+ ", NAME_KANA_MEI = ?"
				+ ", SHOZOKU_CD = ?"
				+ ", SHOZOKU_NAME = ?"
//				+ ", BUKYOKU_CD = ?"
				+ ", BUKYOKU_NAME = ?"
//				+ ", SHOKUSHU_CD = ?"
				+ ", SHOKUSHU_NAME = ?"
//				+ ", KEI_CD = ?"
//				+ ", SHINSA_KAHI = ?"
				+ ", SOFU_ZIP = ?"
				+ ", SOFU_ZIPADDRESS = ?"
				+ ", SOFU_ZIPEMAIL = ?"
				+ ", SHOZOKU_TEL = ?"
				+ ", SHOZOKU_FAX = ?"
//				+ ", JITAKU_TEL = ?"
//				+ ", SINKI_KEIZOKU_FLG = ?"
//				+ ", KIZOKU_START = ?"
//				+ ", KIZOKU_END = ?"
//				+ ", SHAKIN = ?"
				+ ", URL = ?"
//				+ ", LEVEL_A1 = ?"
//				+ ", LEVEL_B1_1 = ?"
//				+ ", LEVEL_B1_2 = ?"
//				+ ", LEVEL_B1_3 = ?"
//				+ ", LEVEL_B2_1 = ?"
//				+ ", LEVEL_B2_2 = ?"
//				+ ", LEVEL_B2_3 = ?"
//				+ ", KEY1 = ?"
//				+ ", KEY2 = ?"
//				+ ", KEY3 = ?"
//				+ ", KEY4 = ?"
//				+ ", KEY5 = ?"
//				+ ", KEY6 = ?"
//				+ ", KEY7 = ?"
				+ ", SENMON = ?"
				+ ", KOSHIN_DATE = ?"
				+ ", BIKO = ?"
				+ " WHERE"
//2006/05/22　追加ここから
//                + " JIGYO_KUBUN = ? "
				+ " JIGYO_KUBUN IN("
                + jigyoKubun
                + ")"
//苗　追加ここまで                
				+ " AND SHINSAIN_NO = ?";

		//審査員情報TBL更新SQL
		String query2 = "UPDATE SHINSAININFO"
				+ " SET"
				+ " PASSWORD = ?"
				+ " ,YUKO_DATE = ?"
				+ " ,LOGIN_DATE = ?"		//2005/10/20 最終ログイン日追加
				+ " ,MAIL_FLG = ?"			//2005/10/26 メールフラグ追加
				+ " ,DOWNLOAD_FLG = ?"		//2006/10/24 研究計画調書ダウンロード  易旭 追加
				+ " WHERE"
				+ " SHINSAIN_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//審査員マスタ更新
			preparedStatement = connection.prepareStatement(query1);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuName());
//				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKeiCd());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsaKahi());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSofuZip());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSofuZipaddress());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSofuZipemail());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuTel());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuFax());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJitakuTel());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSinkiKeizokuFlg());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKizokuStart());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKizokuEnd());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShakin());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getUrl());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelA1());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelB11());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelB12());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelB13());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelB21());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelB22());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelB23());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey1());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey2());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey3());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey4());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey5());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey6());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey7());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSenmon());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKoshinDate());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
//2006/05/22 削除ここから            
//		　　　 DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());
//苗　削除ここまで            
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNo());
			DatabaseUtil.executeUpdate(preparedStatement);

			//初期化
			preparedStatement = null;
			i = 1;

			//審査員マスタ更新
			preparedStatement = connection.prepareStatement(query2);

			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getPassword());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getYukoDate());	
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLoginDate());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getMailFlg());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDownloadFlag());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainId());
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("審査員情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * 審査員情報を削除する。(削除フラグ) 
	 * @param connection			コネクション
	 * @param deleteInfo			削除する審査員主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteFlgShinsainInfo(
			Connection connection,
			ShinsainPk deleteInfo)
			throws DataAccessException, NoDataFoundException {
			
		//検索
		ShinsainInfo shinsainInfo = selectShinsainInfo(connection, deleteInfo);

		String query = "UPDATE SHINSAININFO"
				+ " SET"
				+ " DEL_FLG = 1"
				+ " WHERE"
				+ " SHINSAIN_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//更新(フラグによる削除)
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainInfo.getShinsainId());
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("審査員情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * 審査員情報を削除する。(物理削除) 
	 * @param connection			コネクション
	 * @param deleteInfo				削除する審査員主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteShinsainInfo(
			Connection connection,
			ShinsainPk deleteInfo)
			throws DataAccessException, NoDataFoundException {
			
		//検索
		ShinsainInfo shinsainInfo = selectShinsainInfo(connection, deleteInfo);

		//審査員情報テーブルの削除
		String query1 = "DELETE FROM SHINSAININFO"
				+ " WHERE"
				+ " SHINSAIN_ID = ?";

		//審査員マスタの削除
		String query2 = "DELETE FROM MASTER_SHINSAIN"
				+ " WHERE"
				+ " SHINSAIN_NO = ?"
				+ " AND JIGYO_KUBUN = ?";

		PreparedStatement preparedStatement = null;
		try {
			//審査員情報テーブルの削除
			preparedStatement = connection.prepareStatement(query1);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainInfo.getShinsainId());
			DatabaseUtil.executeUpdate(preparedStatement);

			//初期化
			preparedStatement = null;
			i = 1;
			//審査員マスタの削除
			preparedStatement = connection.prepareStatement(query2);
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainInfo.getJigyoKubun());
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("審査員情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * ユーザID、パスワードの認証を行う。
	 * @param connection			コネクション
	 * @param userid				ユーザID
	 * @param password				パスワード
	 * @return						認証に成功した場合 true 以外 false
	 * @throws DataAccessException	データベースアクセス中の例外
	 */
	public boolean authenticateShinsainInfo(
		Connection connection,
		String userid,
		String password)
		throws DataAccessException {

			String query = "SELECT count(*) "
						 + "FROM SHINSAININFO "
						 + "WHERE DEL_FLG = 0 "
						 + "AND SHINSAIN_ID = ? "
						 + "AND PASSWORD = ?";

			PreparedStatement ps = null;
			ResultSet rset = null;
			int count = 0;

			try {
				ps = connection.prepareStatement(query);
				int i = 1;
				ps.setString(i++, userid);
				ps.setString(i++, password);
				rset = ps.executeQuery();

				if (rset.next()) {
					count = rset.getInt(1);
				}

				//一致するデータが存在する場合は、true返す
				if (count > 0) {
					return true;
				} else {
					return false;
				}

			} catch (SQLException ex) {
				throw new DataAccessException("審査員情報テーブル検索実行中に例外が発生しました。 ", ex);
			} finally {
				DatabaseUtil.closeResource(rset, ps);
			}
	}
	
	
	
	/**
	 * パスワードを変更する。 
	 * @param connection
	 * @param pkInfo				主キー情報
	 * @param newPassword			新しいパスワード
	 * @return              		パスワードの変更に成功した場合 true 以外 false
	 * @throws DataAccessException	変更中に例外が発生した場合
	 */
	public boolean changePasswordShinsainInfo(
		Connection connection,
		ShinsainPk pkInfo,
		String newPassword)
		throws DataAccessException {

			String query = "UPDATE SHINSAININFO"
						 + " SET"
						 + " PASSWORD = ?"
						 + " WHERE"
						 + " SUBSTR(SHINSAIN_ID,3,8) = ?"						//事業区分+審査員番号(7桁)
						 + " AND DEL_FLG = 0";									//削除フラグ

			PreparedStatement ps = null;
			
			if(log.isDebugEnabled()){
				log.debug("query:" + query);
			}
		
			try {
				//登録
				ps = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(ps,i++, newPassword);										//新しいパスワード
				DatabaseUtil.setParameter(ps,i++, pkInfo.getJigyoKubun()+pkInfo.getShinsainNo());	//審査員番号
				DatabaseUtil.executeUpdate(ps);
			} catch (SQLException ex) {
				throw new DataAccessException("パスワード変更中に例外が発生しました。 ", ex);

			} finally {
				DatabaseUtil.closeResource(null, ps);
			}
			return true;
	}
	
	
	
	/**
	 * 審査員情報の数を取得する。削除フラグが「0」の場合のみ検索する。
	 * @param connection		コネクション
	 * @param searchInfo		審査員情報
	 * @return					審査員数
	 */
	public int countShinsainInfo(Connection connection, ShinsainInfo searchInfo)
			throws DataAccessException {

		String query = "SELECT COUNT(*)"
				+ " FROM MASTER_SHINSAIN MS"
				+ ", SHINSAININFO SI"
				+ " WHERE MS.SHINSAIN_NO = ?"
				+ " AND MS.JIGYO_KUBUN = ?"
				+ " AND SI.DEL_FLG = 0"				//削除フラグ
				+ " AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)"
				+ " AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)";

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;

		try {
			ShinsainInfo result = new ShinsainInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getJigyoKubun());
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("審査員情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	
	/**
	 * 審査員情報テーブルに指定した審査員番号＋区分のレコードがあるかを判断する。
	 * 削除フラグが「1（削除済み」のデータも対象となる。
	 * @param	connection			コネクション
	 * @param　shinsainNo			審査員番号
	 * @param  kubun               区分
	 * @return						レコード有無（boolean）
	 * @throws ApplicationException
	 */
	public int checkShinsainInfo(Connection connection, 
								  String     shinsainNo,
								  String     kubun)
		throws DataAccessException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select = "SELECT COUNT(*) FROM SHINSAININFO "
					  + " WHERE"
					  + "  SUBSTR(SHINSAIN_ID,4,7) = ?"		//審査員番号(7桁)
					  + " AND"
					  + "  SUBSTR(SHINSAIN_ID,3,1) = ?"		//事業区分(1桁)
					  ;

		PreparedStatement preparedStatement = null;
		ResultSet         rset              = null;
		try {
			//取得
			preparedStatement = connection.prepareStatement(select);
			int i=1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainNo);			
			DatabaseUtil.setParameter(preparedStatement,i++,kubun);
			rset = preparedStatement.executeQuery();
			int count = 0;
			if (rset.next()) {
				count = rset.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("審査員情報レコード検索中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(rset, preparedStatement);
		}
	}	
	
	
	
	/**
	 * 審査員マスタ情報（一部）の一覧を取得する。
	 * @param	connection			コネクション
	 * @return						審査員情報（一部）
	 * @throws ApplicationException
	 */
	public static List selectShinsainInfoList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " SHINSAIN_NO"
			+ ",JIGYO_KUBUN"
			+ ",NAME_KANJI_SEI"
			+ ",NAME_KANJI_MEI"
			+ ",NAME_KANA_SEI"
			+ ",NAME_KANA_MEI"
			+ ",SHOZOKU_CD"
			+ ",SHOZOKU_NAME"
			+ ",BUKYOKU_NAME"
			+ ",SHOKUSHU_NAME"
			+ " FROM MASTER_SHINSAIN A"
			+ " ORDER BY A.SHINSAIN_NO";								
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
				"審査員マスタ情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"審査員マスタに1件もデータがありません。",
				e);
		}
	}	
	
	
	
	/**
	 * 当該事業区分の審査員マスタ情報（一部）の一覧を取得する。
	 * @param	connection			コネクション
	 * @param  jigyoKubun          事業区分
	 * @return						審査員情報（一部）
	 * @throws ApplicationException
	 */
	public static List selectShinsainInfoList(Connection connection, String jigyoKubun)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " SHINSAIN_NO"
			+ ",JIGYO_KUBUN"
			+ ",NAME_KANJI_SEI"
			+ ",NAME_KANJI_MEI"
			+ ",NAME_KANA_SEI"
			+ ",NAME_KANA_MEI"
			+ ",SHOZOKU_CD"
			+ ",SHOZOKU_NAME"
			+ ",BUKYOKU_NAME"
			+ ",SHOKUSHU_NAME"
			+ " FROM MASTER_SHINSAIN A"
			+ " WHERE A.JIGYO_KUBUN = ?"
			+ " ORDER BY A.SHINSAIN_NO";								
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString(), new String[]{jigyoKubun});
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"審査員マスタ情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		}
	}	
	
	
	
	/**
	 * 審査依頼書用CSVデータをDBより取得する。（審査員管理用）
	 * @param connection
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public List createCSV4Iraisho(Connection connection, ShinsainSearchInfo searchInfo)
		throws ApplicationException
	{
		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		String select = "SELECT " 
						+ "to_char(substr(MS.shinsain_no,1,4), '9990') || "				//審査員番号
						+ 	"substr(MS.shinsain_no,5,1) || '－' || "					//頭５桁（最初の３桁のゼロは省略）と
						+ 		"substr(MS.shinsain_no,6,2)		\"審査員番号\""			//後２桁をハイフンで結合
						+ ",MS.NAME_KANJI_SEI                   \"審査員名（漢字-姓）\""	//審査員名（漢字-姓）
						+ ",MS.NAME_KANJI_MEI                   \"審査員名（漢字-名）\""	//審査員名（漢字-名）
						+ ",MS.SHOZOKU_NAME                     \"審査員所属機関名\""	//審査員所属機関名
						+ ",MS.BUKYOKU_NAME                     \"審査員部局名\""		//審査員部局名
						+ ",MS.SHOKUSHU_NAME                    \"審査員職名\""			//審査員職名
						+ ",MS.SOFU_ZIP                         \"送付先（郵便番号）\""	//送付先（郵便番号）
						+ ",MS.SOFU_ZIPADDRESS                  \"送付先（住所）\""		//送付先（住所）
						+ ",MS.SOFU_ZIPEMAIL                    \"送付先（Email）\""	//送付先（Email）
						+ ",MS.SHOZOKU_TEL                      \"電話番号\""			//電話番号
						+ ",MS.SHOZOKU_FAX                      \"FAX番号\""			//FAX番号
						+ ",MS.BIKO                             \"備考\""				//備考
						+ ",SI.SHINSAIN_ID                      \"審査員ID\""			//審査員ID
						+ ",SI.PASSWORD                         \"パスワード\""			//パスワード
						+ ",TO_CHAR(SI.YUKO_DATE,'YYYY/MM/DD')  \"有効期限\""			//有効期限
						+ " FROM"
						+ "  MASTER_SHINSAIN MS, SHINSAININFO SI"
						+ " WHERE"
						+ "  SI.DEL_FLG = 0"
						;

		StringBuffer query = new StringBuffer(select);
		
		//審査員番号
		if(searchInfo.getShinsainNo() != null && !searchInfo.getShinsainNo().equals("")){			
			query.append(" AND MS.SHINSAIN_NO = '" + EscapeUtil.toSqlString(searchInfo.getShinsainNo()) + "'");
		}
		//申請者氏名（漢字-姓）
		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){
			query.append(" AND MS.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		//申請者氏名（漢字-名）
		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){				
			query.append(" AND MS.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		//所属機関コード
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){					
			query.append(" AND MS.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		//細目番号		
		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){			//細目番号
			query.append(" AND (")
				.append("((SUBSTR(MS.SHINSAIN_NO, 1, 4) = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "')")
					.append(" AND (SUBSTR(MS.SHINSAIN_NO, 5, 1) IN ('A', 'B')))")
				.append(" OR ((SUBSTR(MS.SHINSAIN_NO, 1, 1) = '0')")
					.append(" AND (SUBSTR(MS.SHINSAIN_NO, 2, 4) = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "'))")
			.append(")");
		}
		//事業区分		
		if(searchInfo.getJigyoKubun() != null && !searchInfo.getJigyoKubun().equals("")){			
			query.append(" AND MS.JIGYO_KUBUN = '" + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + "'");
		}

		//結合条件＋ソート条件（審査員番号順）
		query.append(" AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)")	//7桁
			 .append(" AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)")
			 .append(" ORDER BY MS.SHINSAIN_NO");
		
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
	 * 審査員情報を更新する。（メールフラグのみ） 
	 * @param connection			コネクション
	 * @param updateInfo			更新する審査員情報
	 * @throws DataAccessException	更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateMailFlgShinsainInfo(
			Connection connection,
			ShinsainInfo updateInfo)
			throws DataAccessException, NoDataFoundException {
			
		//検索
		ShinsainInfo shinsainInfo = selectShinsainInfo(connection, updateInfo);

		String query = "UPDATE SHINSAININFO"
				+ " SET"
				+ " MAIL_FLG = ?"
				+ " WHERE"
				+ " SHINSAIN_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//更新(メールフラグのみ)
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getMailFlg());
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainInfo.getShinsainId());
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("審査員情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	
	
	
	
}
