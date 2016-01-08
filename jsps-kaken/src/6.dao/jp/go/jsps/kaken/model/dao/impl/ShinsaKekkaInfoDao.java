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
import java.text.*;
import java.util.*;
import java.util.Date;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.impl.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.status.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * 審査結果情報データアクセスクラス。
 */
public class ShinsaKekkaInfoDao {
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(ShinsaKekkaInfoDao.class);

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
	public ShinsaKekkaInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	/**
	 * コンストラクタ。
	 * @param userInfo	実行するユーザ情報
	 * @param dbLink   DBリンク名
	 */
	public ShinsaKekkaInfoDao(UserInfo userInfo, String dbLink) {
		this.userInfo = userInfo;
		this.dbLink   = dbLink;
	}
	

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * 審査結果情報を取得する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						審査結果情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public ShinsaKekkaInfo selectShinsaKekkaInfo(
		Connection connection,
		ShinsaKekkaPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		return selectShinsaKekkaInfo(connection, primaryKeys, false);
	}
	
	/**
	 * 審査結果情報を取得する。
	 * 取得したレコードをロックする。（comitが行われるまで。）
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						審査結果情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public ShinsaKekkaInfo selectShinsaKekkaInfoForLock(
		Connection connection,
		ShinsaKekkaPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		return selectShinsaKekkaInfo(connection, primaryKeys, true);
	}
	
	/**
	 * 審査結果情報を取得する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @param lock                 trueの場合はレコードをロックする
	 * @return						審査結果情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	private ShinsaKekkaInfo selectShinsaKekkaInfo(
		Connection connection,
		ShinsaKekkaPk primaryKeys,
		boolean lock)
		throws DataAccessException, NoDataFoundException
	{
//8/20現在のテーブル	
		String query =
			"SELECT "
				+ " A.SYSTEM_NO"				//システム受付番号
				+ ",A.UKETUKE_NO"				//申請番号
				+ ",A.SHINSAIN_NO"				//審査員番号
				+ ",A.JIGYO_KUBUN"				//事業区分
				+ ",A.SEQ_NO"					//シーケンス番号
				+ ",A.SHINSA_KUBUN"				//審査区分
				+ ",A.SHINSAIN_NAME_KANJI_SEI"	//審査員名（漢字－姓）
				+ ",A.SHINSAIN_NAME_KANJI_MEI"	//審査員名（漢字－名）
				+ ",A.NAME_KANA_SEI"			//審査員名（フリガナ－姓）
				+ ",A.NAME_KANA_MEI"			//審査員名（フリガナ－名）
				+ ",A.SHOZOKU_NAME"				//審査員所属機関名
				+ ",A.BUKYOKU_NAME"				//審査員部局名
				+ ",A.SHOKUSHU_NAME"			//審査員職名
				+ ",A.JIGYO_ID"					//事業ID
				+ ",A.JIGYO_NAME"				//事業名
				+ ",A.BUNKASAIMOKU_CD"			//細目番号
				+ ",A.EDA_NO"					//枝番				
				+ ",A.CHECKDIGIT"				//チェックデジット
				+ ",A.KEKKA_ABC"				//総合評価（ABC）
				+ ",A.KEKKA_TEN"				//総合評価（点数）
				+ ",A.COMMENT1"					//コメント1			
				+ ",A.COMMENT2"					//コメント2
				+ ",A.COMMENT3"					//コメント3
				+ ",A.COMMENT4"					//コメント4
				+ ",A.COMMENT5"					//コメント5
				+ ",A.COMMENT6"					//コメント6
				+ ",A.KENKYUNAIYO"				//研究内容
				+ ",A.KENKYUKEIKAKU"			//研究計画		
				+ ",A.TEKISETSU_KAIGAI"			//適切性-海外
				+ ",A.TEKISETSU_KENKYU1"		//適切性-研究（1）
				+ ",A.TEKISETSU"				//適切性			
				+ ",A.DATO"						//妥当性
				+ ",A.SHINSEISHA"				//研究代表者
				+ ",A.KENKYUBUNTANSHA"			//研究分担者
				+ ",A.HITOGENOMU"				//ヒトゲノム
				+ ",A.TOKUTEI"					//特定胚
				+ ",A.HITOES"					//ヒトES細胞
				+ ",A.KUMIKAE"					//遺伝子組換え実験
				+ ",A.CHIRYO"					//遺伝子治療臨床研究			
				+ ",A.EKIGAKU"					//疫学研究
				+ ",A.COMMENTS"					//コメント
				//2005.10.25 kainuma	
				+ ",A.RIGAI"					//利害関係
				+ ",A.DAIRI"					//代理フラグ			//2005/11/04 追加
				+ ",A.TEKISETSU_WAKATES"		//若手(S)としての妥当性	//2007/5/8 追加
				+ ",A.JUYOSEI"					//学術的重要性・妥当性
				+ ",A.DOKUSOSEI"				//独創性・革新性
				+ ",A.HAKYUKOKA"				//波及効果・普遍性
				+ ",A.SUIKONORYOKU"				//遂行能力・環境の適切性
				+ ",A.JINKEN"					//人権の保護・法令等の遵守
				+ ",A.BUNTANKIN"				//分担金配分			
				+ ",A.OTHER_COMMENT"			//その他コメント
				//2005.10.25 kainuma
				+ ",A.KOSHIN_DATE"				//割り振り更新日		//2005/11/08 追加
				+ ",A.TENPU_PATH"				//添付ファイル格納パス			
				+ ",DECODE"
				+ " ("
				+ "  NVL(A.TENPU_PATH,'null') "
				+ "  ,'null','FALSE'"			//添付ファイル格納パスがNULLのとき
				+ "  ,      'TRUE'"				//添付ファイル格納パスがNULL以外のとき
				+ " ) TENPU_FLG"				//添付ファイル格納フラグ
				+ ",A.SHINSA_JOKYO"				//審査状況
				+ ",A.BIKO"						//備考
				+ " FROM SHINSAKEKKA"+dbLink+" A"
				+ " WHERE"
				+ "  SYSTEM_NO = ?"
				+ " AND"
				+ "  SHINSAIN_NO = ?"
				+ " AND"
				+ "  JIGYO_KUBUN = ?"
				;
				
			//排他制御を行う場合
			if(lock){
				query = query + " FOR UPDATE";
			}				
				
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShinsaKekkaInfo result = new ShinsaKekkaInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, primaryKeys.getSystemNo());		//システム受付番号
			DatabaseUtil.setParameter(preparedStatement, i++, primaryKeys.getShinsainNo());		//審査員番号
			DatabaseUtil.setParameter(preparedStatement, i++, primaryKeys.getJigyoKubun());		//事業区分
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setSystemNo(recordSet.getString("SYSTEM_NO"));
				result.setUketukeNo(recordSet.getString("UKETUKE_NO"));
				result.setShinsainNo(recordSet.getString("SHINSAIN_NO"));		
				result.setJigyoKubun(recordSet.getString("JIGYO_KUBUN"));		
				result.setSeqNo(recordSet.getString("SEQ_NO"));
				result.setShinsaKubun(recordSet.getString("SHINSA_KUBUN"));
				result.setShinsainNameKanjiSei(recordSet.getString("SHINSAIN_NAME_KANJI_SEI"));
				result.setShinsainNameKanjiMei(recordSet.getString("SHINSAIN_NAME_KANJI_MEI"));
				result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
				result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
				result.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
				result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
				result.setShokushuName(recordSet.getString("SHOKUSHU_NAME"));
				result.setJigyoId(recordSet.getString("JIGYO_ID"));
				result.setJigyoName(recordSet.getString("JIGYO_NAME"));
				result.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
				result.setEdaNo(recordSet.getString("EDA_NO"));
				result.setCheckDigit(recordSet.getString("CHECKDIGIT"));		
				result.setKekkaAbc(recordSet.getString("KEKKA_ABC"));		
				result.setKekkaTen(recordSet.getString("KEKKA_TEN"));
				result.setComment1(recordSet.getString("COMMENT1"));
				result.setComment2(recordSet.getString("COMMENT2"));
				result.setComment3(recordSet.getString("COMMENT3"));
				result.setComment4(recordSet.getString("COMMENT4"));
				result.setComment5(recordSet.getString("COMMENT5"));
				result.setComment6(recordSet.getString("COMMENT6"));
				result.setKenkyuNaiyo(recordSet.getString("KENKYUNAIYO"));
				result.setKenkyuKeikaku(recordSet.getString("KENKYUKEIKAKU"));
				result.setTekisetsuKaigai(recordSet.getString("TEKISETSU_KAIGAI"));			
				result.setTekisetsuKenkyu1(recordSet.getString("TEKISETSU_KENKYU1"));
				result.setTekisetsu(recordSet.getString("TEKISETSU"));
				result.setDato(recordSet.getString("DATO"));
				result.setShinseisha(recordSet.getString("SHINSEISHA"));
				result.setKenkyuBuntansha(recordSet.getString("KENKYUBUNTANSHA"));
				result.setHitogenomu(recordSet.getString("HITOGENOMU"));
				result.setTokutei(recordSet.getString("TOKUTEI"));
				result.setHitoEs(recordSet.getString("HITOES"));
				result.setKumikae(recordSet.getString("KUMIKAE"));
				result.setChiryo(recordSet.getString("CHIRYO"));
				result.setEkigaku(recordSet.getString("EKIGAKU"));
				result.setComments(recordSet.getString("COMMENTS"));
				//2005.10.26 kainuma
				result.setRigai(recordSet.getString("RIGAI"));
				result.setDairi(recordSet.getString("DAIRI"));
				result.setWakates(recordSet.getString("TEKISETSU_WAKATES"));	//2007/5/8追加
				result.setJuyosei(recordSet.getString("JUYOSEI"));
				result.setDokusosei(recordSet.getString("DOKUSOSEI"));
				result.setHakyukoka(recordSet.getString("HAKYUKOKA"));
				result.setSuikonoryoku(recordSet.getString("SUIKONORYOKU"));
				result.setJinken(recordSet.getString("JINKEN"));
				result.setBuntankin(recordSet.getString("BUNTANKIN"));
				result.setOtherComment(recordSet.getString("OTHER_COMMENT"));
				//
				result.setKoshinDate(recordSet.getDate("KOSHIN_DATE"));
				result.setTenpuPath(recordSet.getString("TENPU_PATH"));
				result.setTenpuFlg(recordSet.getString("TENPU_FLG"));
				result.setShinsaJokyo(recordSet.getString("SHINSA_JOKYO"));
				result.setBiko(recordSet.getString("BIKO"));
				return result;
			} else {
				throw new NoDataFoundException(
					"審査結果情報テーブルに該当するデータが見つかりません。検索キー：システム受付番号'"
						+ primaryKeys.getSystemNo()
						+ "', 審査員番号'"
				        + primaryKeys.getShinsainNo()
						+ "', 事業区分'"
						+ primaryKeys.getJigyoKubun()
						+ "'");		
			}
		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	/**
	 * 審査結果情報を取得する。
	 * ソート順は総合評価（ABC）の昇順、総合評価（点数）の降順、審査員番号の昇順、事業区分の昇順とする。
	 * @param connection			コネクション
     * @param shinseiDataPk         キー
	 * @return						審査結果情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public ShinsaKekkaInfo[] selectShinsaKekkaInfo(
		Connection connection,
		ShinseiDataPk shinseiDataPk)
		throws DataAccessException, NoDataFoundException
	{
//8/20現在のテーブル			
		String query =
			"SELECT "
				+ " A.SYSTEM_NO"				//システム受付番号
				+ ",A.UKETUKE_NO"				//申請番号
				+ ",A.SHINSAIN_NO"				//審査員番号
				+ ",A.JIGYO_KUBUN"				//事業区分
				+ ",A.SEQ_NO"					//シーケンス番号
				+ ",A.SHINSA_KUBUN"				//審査区分
				+ ",A.SHINSAIN_NAME_KANJI_SEI"	//審査員名（漢字－姓）
				+ ",A.SHINSAIN_NAME_KANJI_MEI"	//審査員名（漢字－名）
				+ ",A.NAME_KANA_SEI"			//審査員名（フリガナ－姓）
				+ ",A.NAME_KANA_MEI"			//審査員名（フリガナ－名）
				+ ",A.SHOZOKU_NAME"				//審査員所属機関名
				+ ",A.BUKYOKU_NAME"				//審査員部局名
				+ ",A.SHOKUSHU_NAME"			//審査員職名
				+ ",A.JIGYO_ID"					//事業ID
				+ ",A.JIGYO_NAME"				//事業名
				+ ",A.BUNKASAIMOKU_CD"			//細目番号
				+ ",A.EDA_NO"					//枝番				
				+ ",A.CHECKDIGIT"				//チェックデジット
				+ ",A.KEKKA_ABC"				//総合評価（ABC）
				+ ",A.KEKKA_TEN"				//総合評価（点数）
				+ ",NVL(REPLACE(A.KEKKA_TEN, '-', '0'), '-1') SORT_KEKKA_TEN"	//ソート用。審査結果（点数）の値NULL→'-1'、'-'→'0'に置換）
				+ ",A.COMMENT1"					//コメント1			
				+ ",A.COMMENT2"					//コメント2
				+ ",A.COMMENT3"					//コメント3
				+ ",A.COMMENT4"					//コメント4
				+ ",A.COMMENT5"					//コメント5
				+ ",A.COMMENT6"					//コメント6
				+ ",A.KENKYUNAIYO"				//研究内容
				+ ",A.KENKYUKEIKAKU"			//研究計画		
				+ ",A.TEKISETSU_KAIGAI"			//適切性-海外
				+ ",A.TEKISETSU_KENKYU1"		//適切性-研究（1）
				+ ",A.TEKISETSU"				//適切性			
				+ ",A.DATO"						//妥当性
				+ ",A.SHINSEISHA"				//研究代表者
				+ ",A.KENKYUBUNTANSHA"			//研究分担者
				+ ",A.HITOGENOMU"				//ヒトゲノム
				+ ",A.TOKUTEI"					//特定胚
				+ ",A.HITOES"					//ヒトES細胞
				+ ",A.KUMIKAE"					//遺伝子組換え実験
				+ ",A.CHIRYO"					//遺伝子治療臨床研究			
				+ ",A.EKIGAKU"					//疫学研究
				+ ",A.COMMENTS"					//コメント
				//2005.10.25 kainuma	
			  	+ ",A.RIGAI"					//利害関係
			  	+ ",A.DAIRI"					//代理フラグ			//2005/11/04 追加
				+ ",A.TEKISETSU_WAKATES"		//若手(S)としての妥当性	//2007/5/8 追加
				+ ",A.JUYOSEI"					//学術的重要性・妥当性
				+ ",A.DOKUSOSEI"				//独創性・革新性
				+ ",A.HAKYUKOKA"				//波及効果・普遍性
			  	+ ",A.SUIKONORYOKU"				//遂行能力・環境の適切性
				+ ",A.JINKEN"					//人権の保護・法令等の遵守
				+ ",A.BUNTANKIN"				//分担金配分			
				+ ",A.OTHER_COMMENT"			//その他コメント
				//2005.10.25 kainuma	
				+ ",A.KOSHIN_DATE"				//割り振り更新日			//2005/11/08 追加
				+ ",A.TENPU_PATH"				//添付ファイル格納パス
				+ ",A.SHINSA_JOKYO"				//審査状況
				+ ",A.BIKO"						//備考
				+ " FROM SHINSAKEKKA"+dbLink+" A"
				+ " WHERE"
				+ "  SYSTEM_NO = ?"
				+ " ORDER BY"
				+ " KEKKA_ABC ASC"						//総合評価（ABC）の昇順
				+ " ,SORT_KEKKA_TEN DESC"				//総合評価（点数）の降順
				+ " ,SHINSAIN_NO ASC"					//審査員番号の昇順
				+ " ,JIGYO_KUBUN ASC"					//事業区分の昇順
				;
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		ShinsaKekkaInfo[] shinseiKekkaInfo = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, shinseiDataPk.getSystemNo());	//システム受付番号
			recordSet = preparedStatement.executeQuery();

			List resultList = new ArrayList();
			while(recordSet.next()){
				ShinsaKekkaInfo result = new ShinsaKekkaInfo();
				result.setSystemNo(recordSet.getString("SYSTEM_NO"));
				result.setUketukeNo(recordSet.getString("UKETUKE_NO"));
				result.setShinsainNo(recordSet.getString("SHINSAIN_NO"));		
				result.setJigyoKubun(recordSet.getString("JIGYO_KUBUN"));
				result.setSeqNo(recordSet.getString("SEQ_NO"));
				result.setShinsaKubun(recordSet.getString("SHINSA_KUBUN"));		
				result.setShinsainNameKanjiSei(recordSet.getString("SHINSAIN_NAME_KANJI_SEI"));
				result.setShinsainNameKanjiMei(recordSet.getString("SHINSAIN_NAME_KANJI_MEI"));
				result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
				result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
				result.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
				result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
				result.setShokushuName(recordSet.getString("SHOKUSHU_NAME"));
				result.setJigyoId(recordSet.getString("JIGYO_ID"));
				result.setJigyoName(recordSet.getString("JIGYO_NAME"));
				result.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
				result.setEdaNo(recordSet.getString("EDA_NO"));
				result.setCheckDigit(recordSet.getString("CHECKDIGIT"));		
				result.setKekkaAbc(recordSet.getString("KEKKA_ABC"));		
				result.setKekkaTen(recordSet.getString("KEKKA_TEN"));	
				result.setComment1(recordSet.getString("COMMENT1"));
				result.setComment2(recordSet.getString("COMMENT2"));
				result.setComment3(recordSet.getString("COMMENT3"));
				result.setComment4(recordSet.getString("COMMENT4"));
				result.setComment5(recordSet.getString("COMMENT5"));
				result.setComment6(recordSet.getString("COMMENT6"));
				result.setKenkyuNaiyo(recordSet.getString("KENKYUNAIYO"));
				result.setKenkyuKeikaku(recordSet.getString("KENKYUKEIKAKU"));
				result.setTekisetsuKaigai(recordSet.getString("TEKISETSU_KAIGAI"));			
				result.setTekisetsuKenkyu1(recordSet.getString("TEKISETSU_KENKYU1"));
				result.setTekisetsu(recordSet.getString("TEKISETSU"));
				result.setDato(recordSet.getString("DATO"));
				result.setShinseisha(recordSet.getString("SHINSEISHA"));
				result.setKenkyuBuntansha(recordSet.getString("KENKYUBUNTANSHA"));
				result.setHitogenomu(recordSet.getString("HITOGENOMU"));
				result.setTokutei(recordSet.getString("TOKUTEI"));
				result.setHitoEs(recordSet.getString("HITOES"));
				result.setKumikae(recordSet.getString("KUMIKAE"));
				result.setChiryo(recordSet.getString("CHIRYO"));
				result.setEkigaku(recordSet.getString("EKIGAKU"));
				result.setComments(recordSet.getString("COMMENTS"));
				//2005.10.26 kainuma
				result.setRigai(recordSet.getString("RIGAI"));
				result.setDairi(recordSet.getString("DAIRI"));
				result.setWakates(recordSet.getString("TEKISETSU_WAKATES"));	//2007/5/8 追加
				result.setJuyosei(recordSet.getString("JUYOSEI"));
				result.setDokusosei(recordSet.getString("DOKUSOSEI"));
				result.setHakyukoka(recordSet.getString("HAKYUKOKA"));
				result.setSuikonoryoku(recordSet.getString("SUIKONORYOKU"));
				result.setJinken(recordSet.getString("JINKEN"));
				result.setBuntankin(recordSet.getString("BUNTANKIN"));
				result.setOtherComment(recordSet.getString("OTHER_COMMENT"));
				//
				result.setKoshinDate(recordSet.getDate("KOSHIN_DATE"));				
				result.setTenpuPath(recordSet.getString("TENPU_PATH"));
				result.setShinsaJokyo(recordSet.getString("SHINSA_JOKYO"));
				result.setBiko(recordSet.getString("BIKO"));			
				resultList.add(result);
			}
			
			//戻り値
			shinseiKekkaInfo = (ShinsaKekkaInfo[])resultList.toArray(new ShinsaKekkaInfo[0]);
			if(shinseiKekkaInfo.length == 0){
				throw new NoDataFoundException(
					"審査結果情報テーブルに該当するデータが見つかりません。検索キー：システム受付番号'"
						+ shinseiDataPk.getSystemNo() + "'");
			}

		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		
		return shinseiKekkaInfo;		
	}	

	/**
	 * 審査結果情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する審査結果情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertShinsaKekkaInfo(
		Connection connection,
		ShinsaKekkaInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//重複チェック
		try {
			selectShinsaKekkaInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}
//8/20現在のテーブル			
		String query =
			"INSERT INTO SHINSAKEKKA"+dbLink
				+ " ("
				+ " SYSTEM_NO"					//システム受付番号
				+ ",UKETUKE_NO"					//申請番号
				+ ",SHINSAIN_NO"				//審査員番号
				+ ",JIGYO_KUBUN"				//事業区分
				+ ",SEQ_NO"						//シーケンス番号
				+ ",SHINSA_KUBUN"				//審査区分
				+ ",SHINSAIN_NAME_KANJI_SEI"	//審査員名（漢字－姓）
				+ ",SHINSAIN_NAME_KANJI_MEI"	//審査員名（漢字－名）
				+ ",NAME_KANA_SEI"				//審査員名（フリガナ－姓）
				+ ",NAME_KANA_MEI"				//審査員名（フリガナ－名）
				+ ",SHOZOKU_NAME"				//審査員所属機関名
				+ ",BUKYOKU_NAME"				//審査員部局名
				+ ",SHOKUSHU_NAME"				//審査員職名
				+ ",JIGYO_ID"					//事業ID
				+ ",JIGYO_NAME"					//事業名
				+ ",BUNKASAIMOKU_CD"			//細目番号
				+ ",EDA_NO"						//枝番				
				+ ",CHECKDIGIT"					//チェックデジット
				+ ",KEKKA_ABC"					//総合評価（ABC）
				+ ",KEKKA_TEN"					//総合評価（点数）
				+ ",COMMENT1"					//コメント1			
				+ ",COMMENT2"					//コメント2
				+ ",COMMENT3"					//コメント3
				+ ",COMMENT4"					//コメント4
				+ ",COMMENT5"					//コメント5
				+ ",COMMENT6"					//コメント6
				+ ",KENKYUNAIYO"				//研究内容
				+ ",KENKYUKEIKAKU"				//研究計画		
				+ ",TEKISETSU_KAIGAI"			//適切性-海外
				+ ",TEKISETSU_KENKYU1"			//適切性-研究（1）
				+ ",TEKISETSU"					//適切性			
				+ ",DATO"						//妥当性
				+ ",SHINSEISHA"					//研究代表者
				+ ",KENKYUBUNTANSHA"			//研究分担者
				+ ",HITOGENOMU"					//ヒトゲノム
				+ ",TOKUTEI"					//特定胚
				+ ",HITOES"						//ヒトES細胞
				+ ",KUMIKAE"					//遺伝子組換え実験
				+ ",CHIRYO"						//遺伝子治療臨床研究			
				+ ",EKIGAKU"					//疫学研究
				+ ",COMMENTS"					//コメント
				//2005.10.25 kainuma	
				+ ",RIGAI"					    //利害関係
				+ ",DAIRI"						//代理フラグ		2005/11/04 追加
				+ ",TEKISETSU_WAKATES"			//若手(S)としての妥当性	//2007/5/8 追加
				+ ",JUYOSEI"					//学術的重要性・妥当性
				+ ",DOKUSOSEI"				    //独創性・革新性
				+ ",HAKYUKOKA"				    //波及効果・普遍性
				+ ",SUIKONORYOKU"				//遂行能力・環境の適切性
				+ ",JINKEN"					    //人権の保護・法令等の遵守
				+ ",BUNTANKIN"				    //分担金配分			
				+ ",OTHER_COMMENT"			    //その他コメント
				//2005.10.25 kainuma
				+ ",KOSHIN_DATE"				//割り振り更新日		2005/11/08 追加
				+ ",TENPU_PATH"					//添付ファイル格納パス
				+ ",SHINSA_JOKYO"				//審査状況
				+ ",BIKO"						//備考
				+ " )"
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"//30個
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//55個
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSystemNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUketukeNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoKubun());			
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSeqNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsaKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsainNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsainNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoId());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunkaSaimokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getEdaNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getCheckDigit());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKekkaAbc());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKekkaTen());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComment1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComment2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComment3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComment4());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComment5());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComment6());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKenkyuNaiyo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKenkyuKeikaku());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTekisetsuKaigai());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTekisetsuKenkyu1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTekisetsu());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getDato());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinseisha());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKenkyuBuntansha());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHitogenomu());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTokutei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHitoEs());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKumikae());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getChiryo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getEkigaku());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComments());
			//2005.10.25 kainuma
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRigai());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getDairi());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getWakates());//2007/5/8 追加
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJuyosei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getDokusosei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHakyukoka());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSuikonoryoku());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJinken());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBuntankin());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherComment());	
			//
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKoshinDate());				
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTenpuPath());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsaJokyo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("審査結果情報登録中に例外が発生しました。 ", ex);
			throw new DataAccessException("審査結果情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * 審査結果情報を更新する。
	 * @param connection				コネクション
	 * @param updateInfo				更新する審査結果情報
	 * @throws DataAccessException		更新中に例外が発生した場合
	 * @throws NoDataFoundException		対象データが見つからない場合
	 */
	public void updateShinsaKekkaInfo(
		Connection connection,
		ShinsaKekkaInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
		//検索（対象データが存在しなかった場合は例外発生）
		selectShinsaKekkaInfo(connection, updateInfo);
//8/20現在のテーブル
		String query =
			"UPDATE SHINSAKEKKA"+dbLink
				+ " SET"	
				+ " SYSTEM_NO = ? "					//システム受付番号
				+ ",UKETUKE_NO = ? "				//申請番号			
				+ ",SHINSAIN_NO = ? "				//審査員番号
				+ ",JIGYO_KUBUN = ? "				//事業区分
				+ ",SEQ_NO = ? "					//シーケンス番号
				+ ",SHINSA_KUBUN = ? "				//審査区分
				+ ",SHINSAIN_NAME_KANJI_SEI = ? "	//審査員名（漢字－姓）
				+ ",SHINSAIN_NAME_KANJI_MEI = ? "	//審査員名（漢字－名）
				+ ",NAME_KANA_SEI = ? "				//審査員名（フリガナ－姓）
				+ ",NAME_KANA_MEI = ? "				//審査員名（フリガナ－名）
				+ ",SHOZOKU_NAME = ? "				//審査員所属機関名
				+ ",BUKYOKU_NAME = ? "				//審査員部局名
				+ ",SHOKUSHU_NAME = ? "				//審査員職名
				+ ",JIGYO_ID = ? "					//事業ID
				+ ",JIGYO_NAME = ? "				//事業名
				+ ",BUNKASAIMOKU_CD = ? "			//細目番号
				+ ",EDA_NO = ? "					//枝番				
				+ ",CHECKDIGIT = ? "				//チェックデジット
				+ ",KEKKA_ABC = ? "					//総合評価（ABC）
				+ ",KEKKA_TEN = ? "					//総合評価（点数）
				+ ",COMMENT1 = ? "					//コメント1			
				+ ",COMMENT2 = ? "					//コメント2
				+ ",COMMENT3 = ? "					//コメント3
				+ ",COMMENT4 = ? "					//コメント4
				+ ",COMMENT5 = ? "					//コメント5
				+ ",COMMENT6 = ? "					//コメント6
				+ ",KENKYUNAIYO = ? "				//研究内容
				+ ",KENKYUKEIKAKU = ? "				//研究計画		
				+ ",TEKISETSU_KAIGAI = ? "			//適切性-海外
				+ ",TEKISETSU_KENKYU1 = ? "			//適切性-研究（1）
				+ ",TEKISETSU = ? "					//適切性			
				+ ",DATO = ? "						//妥当性
				+ ",SHINSEISHA = ? "				//研究代表者
				+ ",KENKYUBUNTANSHA = ? "			//研究分担者
				+ ",HITOGENOMU = ? "				//ヒトゲノム
				+ ",TOKUTEI = ? "					//特定胚
				+ ",HITOES = ? "					//ヒトES細胞
				+ ",KUMIKAE = ? "					//遺伝子組換え実験
				+ ",CHIRYO = ? "					//遺伝子治療臨床研究			
				+ ",EKIGAKU = ? "					//疫学研究
				+ ",COMMENTS = ? "					//コメント
				//2005.10.25 kainuma	
			  	+ ",RIGAI = ? "						//利害関係
			  	+ ",DAIRI = ? "						//代理フラグ 2005/11/04 追加
			 	+ ",TEKISETSU_WAKATES = ? "			//若手Sとしての妥当性 2007/5/8追加
			 	+ ",JUYOSEI = ? "					//学術的重要性・妥当性
			 	+ ",DOKUSOSEI = ? "					//独創性・革新性
				+ ",HAKYUKOKA = ? "					//波及効果・普遍性
			 	+ ",SUIKONORYOKU = ? "				//遂行能力・環境の適切性
				+ ",JINKEN = ? "					//人権の保護・法令等の遵守
			 	+ ",BUNTANKIN = ? "					//分担金配分			
			  	+ ",OTHER_COMMENT = ?"				//その他コメント
			    //2005.10.25 kainuma	
			    + ",KOSHIN_DATE = ?"				//割り振り更新日		//2005/11/08 追加
				+ ",TENPU_PATH = ? "				//添付ファイル格納パス
				+ ",SHINSA_JOKYO = ? "				//審査状況
				+ ",BIKO = ? "						//備考
				+ " WHERE"
				+ "  SYSTEM_NO = ?"
				+ " AND"
				+ "  SHINSAIN_NO = ?";

		PreparedStatement preparedStatement = null;
		try {
			//更新
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSystemNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getUketukeNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());			
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSeqNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsaKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoId());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunkaSaimokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getEdaNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getCheckDigit());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKekkaAbc());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKekkaTen());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComment1());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComment2());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComment3());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComment4());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComment5());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComment6());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuNaiyo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuKeikaku());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTekisetsuKaigai());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTekisetsuKenkyu1());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTekisetsu());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDato());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinseisha());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuBuntansha());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHitogenomu());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTokutei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHitoEs());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKumikae());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getChiryo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getEkigaku());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComments());
			//2005.10.26 kainuma
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getRigai());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDairi());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getWakates());//2007/5/8追加
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJuyosei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDokusosei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHakyukoka());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSuikonoryoku());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJinken());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBuntankin());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherComment());
			//
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKoshinDate());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTenpuPath());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsaJokyo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());			
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSystemNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNo());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * 担当審査結果情報の審査員情報を更新する。
	 * 事業IDが空なら、全ての事業が対象。
	 * 審査状況が空なら、未完了のみ対象。
	 * @param connection				コネクション
	 * @param updateInfo				更新する審査員情報
	 * @param shinsaJokyo				審査状況
	 * @param jigyoId					事業ID
	 * @throws DataAccessException		更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateShinsainInfo(
			Connection connection,
			ShinsainInfo updateInfo,
			String shinsaJokyo,
			String jigyoId)
			throws DataAccessException, NoDataFoundException {

		//審査状況が空なら、未完了のみ対象。
		if(shinsaJokyo == null) {
			shinsaJokyo = ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET;
		}
//2006/11/2 苗　追加ここから
        String jigyoKubun = updateInfo.getJigyoKubun();
        Set shinseishoSet = JigyoKubunFilter.Convert2ShinseishoJigyoKubun(jigyoKubun) ; //審査員の事業区分のSet
        if (shinseishoSet != null && !shinseishoSet.isEmpty()) {
            jigyoKubun = StringUtil.changeIterator2CSV(shinseishoSet.iterator(), true);
        }
//2006/11/2　苗　 追加ここまで       

		String update
			= "UPDATE SHINSAKEKKA"+dbLink
				+ " SET"	
				+ " SHINSAIN_NO = ? "				//審査員番号
//2006/11/03 苗　削除ここから                
//                + ", JIGYO_KUBUN = ? "              //事業区分
//2006/11/03　苗　削除ここまで                
				+ ", SHINSAIN_NAME_KANJI_SEI = ? "	//審査員名（漢字－姓）
				+ ", SHINSAIN_NAME_KANJI_MEI = ? "	//審査員名（漢字－名）
				+ ", NAME_KANA_SEI = ? "			//審査員名（フリガナ－姓）
				+ ", NAME_KANA_MEI = ? "			//審査員名（フリガナ－名）
				+ ", SHOZOKU_NAME = ? "				//審査員所属機関名
				+ ", BUKYOKU_NAME = ? "				//審査員部局名
				+ ", SHOKUSHU_NAME = ? "			//審査員職名
				+ " WHERE SHINSAIN_NO = ?"
//2006/11/02　苗　修正ここから                
//                + " AND JIGYO_KUBUN = ?"
                + " AND JIGYO_KUBUN IN("
                + jigyoKubun
                + ")";
//2006/11/02 苗　修正ここまで              
				;

		StringBuffer query = new StringBuffer(update);
		
		query.append(" AND SHINSA_JOKYO = '" + shinsaJokyo + "'");
		if(jigyoId != null && !jigyoId.equals("")) {
			query.append(" AND JIGYO_ID = '" + EscapeUtil.toSqlString(jigyoId) + "'");
		}
		query.append(" AND EXISTS")
			 .append(" (SELECT * FROM")
			 .append(" SHINSEIDATAKANRI"+dbLink+" A")
			 .append(", SHINSAKEKKA"+dbLink+" B")
			 .append(" WHERE A.DEL_FLG = 0")
			 .append(" AND A.SYSTEM_NO = B.SYSTEM_NO)")
			 ;
		
		PreparedStatement preparedStatement = null;
		try {
			//更新
			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNo());
//2006/11/03　苗　削除ここから            
//            DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());
//2006/11/03　苗　削除ここまで            
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());	
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNo());
//2006/11/02 苗　削除ここから            
//            DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());
//2006/11/02　苗　削除ここまで            
			preparedStatement.executeUpdate();				//更新件数が複数なので
		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	//2004/05/25 追加 ここから-------------------------------------------------------
	//理由　 システム受付番号追加のため
	/**
	 * 担当審査結果情報の審査員情報を更新する。
	 * 事業IDが空なら、全ての事業が対象。
	 * 審査状況が空なら、未完了のみ対象。
	 * @param connection				コネクション
	 * @param updateInfo				更新する審査員情報
	 * @param shinsaJokyo				審査状況
	 * @param jigyoId					事業ID
     * @param systemNo                  システム受付番号
     * @param no                        順番
	 * @throws DataAccessException		更新中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void updateShinsainInfo(
			Connection connection,
			ShinsainInfo updateInfo,
			String shinsaJokyo,
			String jigyoId,
			String systemNo,
			int no)
			throws DataAccessException, NoDataFoundException {

		//審査状況が空なら、未完了のみ対象。
		if(shinsaJokyo == null) {
			shinsaJokyo = ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET;
		}

		String update
			= "UPDATE SHINSAKEKKA"+dbLink
				+ " SET"	
				+ " SHINSAIN_NO = ? "				//審査員番号
				+ ", JIGYO_KUBUN = ? "				//事業区分
				+ ", SHINSAIN_NAME_KANJI_SEI = ? "	//審査員名（漢字－姓）
				+ ", SHINSAIN_NAME_KANJI_MEI = ? "	//審査員名（漢字－名）
				+ ", NAME_KANA_SEI = ? "			//審査員名（フリガナ－姓）
				+ ", NAME_KANA_MEI = ? "			//審査員名（フリガナ－名）
				+ ", SHOZOKU_NAME = ? "				//審査員所属機関名
				+ ", BUKYOKU_NAME = ? "				//審査員部局名
				+ ", SHOKUSHU_NAME = ? "			//審査員職名
				+ " WHERE SHINSAIN_NO = ?"
				+ " AND JIGYO_KUBUN = ?"
				+ " AND SYSTEM_NO = ?"
				;

		StringBuffer query = new StringBuffer(update);
		
		query.append(" AND SHINSA_JOKYO = '" + EscapeUtil.toSqlString(shinsaJokyo) + "'");
		if(jigyoId != null && !jigyoId.equals("")) {
			query.append(" AND JIGYO_ID = '" + EscapeUtil.toSqlString(jigyoId) + "'");
		}
		query.append(" AND EXISTS")
			 .append(" (SELECT * FROM")
			 .append(" SHINSEIDATAKANRI"+dbLink+" A")
			 .append(", SHINSAKEKKA"+dbLink+" B")
			 .append(" WHERE A.DEL_FLG = 0")
			 .append(" AND A.SYSTEM_NO = B.SYSTEM_NO)")
			 ;
		
		PreparedStatement preparedStatement = null;
		try {
			//更新
			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());	
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,"@00000"+no);
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,systemNo);
			preparedStatement.executeUpdate();				//更新件数が複数なので
		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	//追加 ここまで-------------------------------------------------------------------------

	/**
	 * 総合評価（ABC）、総合評価（点数）がNULLの審査結果情報の数を取得する。
	 * @param connection				コネクション
	 * @param shinsainNo
	 * @param jigyoKubun
	 * @param jigyoId
	 * @throws DataAccessException			更新中に例外が発生した場合
	 * @throws NoDataFoundException		対象データが見つからない場合
	 */
	public int countShinsaKekkaInfo(
		Connection connection,
		String shinsainNo,
		String jigyoKubun,
		String jigyoId)
		throws DataAccessException
	{

//8/20現在のテーブル
		String query =
			"SELECT COUNT(*) "
				+ " FROM SHINSAKEKKA"+dbLink+" A,"
				+ " (SELECT * FROM SHINSEIDATAKANRI"+dbLink
				+ "  WHERE "
				+ "   (JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSATYU + "'"		//申請状況が[10]
				+ "    OR "
				+ "    JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSA_KANRYO + "'"	//申請状況が[11]
				+ "   )"
				+ "  AND"
				+ "   DEL_FLG = '0'"							//削除されてないもの
				+ " ) B"
				+ " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
				+ " AND A.KEKKA_ABC IS NULL"	//総合評価（ABC）
				+ " AND A.KEKKA_TEN IS NULL"	//総合評価（点数）
				//2005/11/15 利害関係の時、「-」をセットされる為、不要とする　
				//+ " AND NVL(A.RIGAI,'0') = '0'"		//2005/11/14 利害関係の場合除外	
				+ " AND A.SHINSAIN_NO = ?"
				+ " AND A.JIGYO_KUBUN = ?"
				+ " AND A.JIGYO_ID = ?"
				;
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			//更新
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainNo);
			DatabaseUtil.setParameter(preparedStatement,i++,jigyoKubun);
			DatabaseUtil.setParameter(preparedStatement,i++,jigyoId);
			DatabaseUtil.executeUpdate(preparedStatement);
			int count = 0;			
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}


	/**
	 * 審査未完了で削除されていない担当審査結果情報の数を取得する。
	 * @param connection				コネクション
	 * @param shinsainNo
	 * @param jigyoKubun
     * @return int レコード数
	 * @throws DataAccessException			更新中に例外が発生した場合
	 * @throws NoDataFoundException		対象データが見つからない場合
	 */
	public int countTantoShinsaKekkaInfo(
			Connection connection,
			String shinsainNo,
			String jigyoKubun)
			throws DataAccessException {

		String select =
			"SELECT COUNT(*) "
				+ " FROM SHINSAKEKKA"+dbLink+" A"
				+ ", SHINSEIDATAKANRI" + dbLink + " B"
				+ " WHERE A.SHINSAIN_NO = ?"
				+ " AND A.JIGYO_KUBUN = ?"
				;

		StringBuffer query = new StringBuffer(select);
		query.append(" AND A.SHINSA_JOKYO = '0'")				//未完了のみ
			 .append(" AND A.SYSTEM_NO = B.SYSTEM_NO")
			 .append(" AND B.DEL_FLG = '0'");					//削除されていないもののみ
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainNo);
			DatabaseUtil.setParameter(preparedStatement,i++,jigyoKubun);
			int count = 0;			
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("担当審査結果情報カウント中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

//2006-10-25 張志男  追加 ここから
    /**
     * 入力状況を更新する。
     * @param connection コネクション
     * @param shinsainNo
     * @param jigyoKubun
     * @param jigyoId
     * @param nyuryokuJokyo
     * @throws DataAccessException 更新中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public void updateNyuryokuJokyo(Connection connection, String shinsainNo,
            String jigyoKubun, String jigyoId, String nyuryokuJokyo)
            throws DataAccessException, NoDataFoundException {
        
        String update = "UPDATE SHINSAKEKKA" + dbLink + " A" + " SET"
                + " A.NYURYOKU_JOKYO = ? " // 入力状況
        ;

        ArrayList list = new ArrayList();
        StringBuffer query = new StringBuffer(update);

        // 審査員番号
        query.append(" WHERE A.SHINSAIN_NO = ?");
        list.add(shinsainNo);
        // 事業区分
        query.append(" AND A.JIGYO_KUBUN = ?");
        list.add(jigyoKubun);
        // 事業ID
        query.append(" AND A.JIGYO_ID = ?");
        list.add(jigyoId);

        PreparedStatement preparedStatement = null;
        try {
            // 更新
            preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;
            // 入力状況
            DatabaseUtil.setParameter(preparedStatement, index++, nyuryokuJokyo); 
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                String value = (String) iterator.next();
                DatabaseUtil.setParameter(preparedStatement, index++, value);
            }
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new DataAccessException("審査結果情報更新中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
// 2006-10-25 張志男  追加 ここまで

    /**
     * 審査結果情報の審査状況を更新する。
     * @param connection                コネクション
     * @param shinsainNo
     * @param jigyoKubun
     * @param systemNo
     * @param jigyoId
     * @param shinsaJokyo
     * @throws DataAccessException      更新中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     */
	public void updateShinsaKekkaInfo(
		Connection connection,
		String shinsainNo,
		String jigyoKubun,
		String systemNo,
		String jigyoId,
		String shinsaJokyo
		)
		throws DataAccessException, NoDataFoundException
	{
		//2004/10/13コメントアウト（データが存在しなくても更新処理でExceptionが発生しないため）
		//検索（対象データが存在しなかった場合は例外発生）
//		 int count = countShinsaKekkaInfo(connection, shinsainNo, jigyoKubun, systemNo, jigyoId, null);
//		 if(count == 0){
//			throw new NoDataFoundException(
//				"審査結果情報テーブルに該当するデータが見つかりません。検索キー：審査員番号'"
//					+ shinsainNo
//					+ "'"
//					+",事業区分''"
//					+ jigyoKubun
//					+ "'"					
//					+",システム番号''"
//					+ systemNo
//					+ "'"	
//					+",事業ID''"
//					+ jigyoId
//					+ "'"					
//					);		 	
//		 }
		
		String update =
			"UPDATE SHINSAKEKKA"+dbLink+" A"
				+ " SET"	
				+ " A.SHINSA_JOKYO = ?"				//審査状況
				;

		StringBuffer  query = new StringBuffer(update);
		query.append(" WHERE A.SYSTEM_NO IN");
		query.append(" (SELECT B.SYSTEM_NO FROM SHINSEIDATAKANRI"+dbLink+" B, SHINSAKEKKA"+dbLink+" C");
		query.append(" WHERE B.SYSTEM_NO=C.SYSTEM_NO");
		query.append(" AND B.DEL_FLG=0");
		query.append(" AND (B.JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSATYU + "'");	//申請状況が[10]
		query.append(" OR");
		query.append(" B.JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSA_KANRYO + "')");	//申請状況が[11]
								
		ArrayList list = new ArrayList();
		//システム番号
		if(systemNo != null && systemNo.length() != 0){	
			query.append(" AND B.SYSTEM_NO= ?)");
			list.add(systemNo);
		}else{
			query.append(")");			
		}
		
		
//2005-01-26
//  仮にnullだった場合、想定外のレコードに対して更新してしまうため、
//  nullでも検索条件には追加する。（誤って更新されることを防ぐ。）
//		//審査員番号
//		if(shinsainNo != null && shinsainNo.length() != 0){	
//			query.append(" AND A.SHINSAIN_NO = ?");
//			list.add(shinsainNo);
//		}
//		//事業区分
//		if(jigyoKubun != null && jigyoKubun.length() != 0){
//			query.append(" AND A.JIGYO_KUBUN = ?");
//			list.add(jigyoKubun);
//		}
//		//事業ID
//		if(jigyoId != null && jigyoId.length() != 0){
//			query.append(" AND A.JIGYO_ID = ?");
//			list.add(jigyoId);
//		}
		//審査員番号
		query.append(" AND A.SHINSAIN_NO = ?");
		list.add(shinsainNo);
		//事業区分
		query.append(" AND A.JIGYO_KUBUN = ?");
		list.add(jigyoKubun);
		//事業ID
		query.append(" AND A.JIGYO_ID = ?");
		list.add(jigyoId);
		
		PreparedStatement preparedStatement = null;
		try {
			//更新
			preparedStatement = connection.prepareStatement(query.toString());
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement,index++,shinsaJokyo);		//審査状況
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				String value = (String)iterator.next();
				DatabaseUtil.setParameter(preparedStatement, index++, value);
			}
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**	審査結果情報の数を取得する。
     * @param connection コネクション
     * @param shinsainNo 審査員番号
     * @param jigyoKubun 事業区分
     * @param systemNo システム番号
     * @param jigyoId 事業ＩＤ
     * @param shinsaJokyo 審査状況
     * @param nyuryokuJokyo 入力状況
     * @return int レコード数
     * @throws DataAccessException
	 */
	public int countShinsaKekkaInfo(
				Connection connection, 
				String shinsainNo, 
				String jigyoKubun, 
				String systemNo, 
				String jigyoId,
                String shinsaJokyo,
                String nyuryokuJokyo)
						throws DataAccessException {
//8/17現在のテーブル
		String select =
			"SELECT COUNT(*)"					//申請番号
				+ " FROM SHINSAKEKKA"+dbLink+" A,"
				+ " (SELECT SYSTEM_NO FROM SHINSEIDATAKANRI"+dbLink
				+ " WHERE DEL_FLG=0"
				+ " AND (JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSATYU + "'"	//申請状況が[10]
				+ " OR"
				+ " JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSA_KANRYO + "')"	//申請状況が[11]
				;
	
		StringBuffer  query = new StringBuffer(select);

		ArrayList list = new ArrayList();
		if(systemNo != null && systemNo.length() != 0){	
			query.append(" AND SYSTEM_NO= ?");					//システム番号
			list.add(systemNo);
		}
		query.append(") B");
		query.append(" WHERE A.SYSTEM_NO=B.SYSTEM_NO");	
		if(shinsainNo != null && shinsainNo.length() != 0){	
			query.append(" AND A.SHINSAIN_NO = ?");				//審査員番号
			list.add(shinsainNo);
		}			
		if(jigyoKubun != null && jigyoKubun.length() != 0){
//			if ("1".equals(jigyoKubun)){
//			query.append(" AND A.JIGYO_KUBUN = ?");				//事業区分
//			}else{
//				query.append(" AND (A.JIGYO_KUBUN = ? or A.JIGYO_KUBUN = 6)");//事業区分
//			}
//			list.add(jigyoKubun);
            
//2006/04/25 追加ここから
            Set shinsaiSet = JigyoKubunFilter.Convert2ShinsainJigyoKubun(jigyoKubun) ; //審査員の事業区分のSet
            String jigyoKubunTemp = (String)shinsaiSet.iterator().next();
            
            Set shinseiSet = JigyoKubunFilter.Convert2ShinseishoJigyoKubun(jigyoKubunTemp) ; //申請の事業区分のSet
            //CSVを取得
            String shinseiJigyoKubun =  StringUtil.changeIterator2CSV(shinseiSet.iterator(),true);
            
            select = select 
                + "   AND A.JIGYO_KUBUN IN ("
                + shinseiJigyoKubun
                + "   )";
//  苗　追加ここまで      
		}
		if(jigyoId != null && jigyoId.length() != 0){
			query.append(" AND A.JIGYO_ID = ?");				//事業ID
			list.add(jigyoId);
		}
		if(shinsaJokyo != null && shinsaJokyo.length() != 0){	
            query.append(" AND A.SHINSA_JOKYO = ?");             //審査状況
			list.add(shinsaJokyo);
		}
//2006-10-27 王鵬 追加ここから
        if(nyuryokuJokyo != null && nyuryokuJokyo.length() != 0){   
            query.append(" AND NVL(A.NYURYOKU_JOKYO, 0) = ?");             //入力状況
            list.add(nyuryokuJokyo);
        }
//2006-10-27 王鵬 追加 ここまで        
		if (log.isDebugEnabled()){
			log.debug(query.toString());
		}
				
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;

		try {
//			ShinsaKekkaInfo result = new ShinsaKekkaInfo();
			preparedStatement = connection.prepareStatement(query.toString());
			int index = 1;
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				String value = (String)iterator.next();
				DatabaseUtil.setParameter(preparedStatement, index++, value);
			}
			recordSet = preparedStatement.executeQuery();

			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			} 
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * 審査担当分申請一覧を返す。総合評価（点数）は検索条件としない。（学創/特推用）
     * @param connection コネクション
     * @param shinsainNo 審査員番号
     * @param jigyoKubun 事業区分
     * @param jigyoId 事業ＩＤ
     * @param searchInfo 検索条件
     * @return Page
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Page selectShinsaKekkaTantoList(
				Connection connection,
				String shinsainNo,
				String jigyoKubun,
				String jigyoId,	
				SearchInfo searchInfo)
			throws DataAccessException, NoDataFoundException, ApplicationException
	{
        return selectShinsaKekkaTantoList(connection, shinsainNo, jigyoKubun, jigyoId, null, 
                null, //2006/10/27　苗　追加　このメッソドは呼び出さないから、nullを追加しました
                searchInfo);
	}
	
	/**
	 * 審査担当分申請一覧を返す。
	 * 削除済みの申請書は除外。
	 * 総合評価（点数）が「-1」の場合は、総合評価（点数）が'IS NULL'を検索条件とする。
     * @param connection コネクション
     * @param shinsainNo 審査員番号
     * @param jigyoKubun 事業区分
     * @param jigyoId 事業ＩＤ
     * @param kekkaTen 評価（点数）
     * @param rigai 利害関係
     * @param searchInfo 検索条件
	 * @return
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Page selectShinsaKekkaTantoList(
				Connection connection,
				String shinsainNo,
				String jigyoKubun,
				String jigyoId,
				String kekkaTen,	
                String rigai,
				SearchInfo searchInfo)
			throws DataAccessException, NoDataFoundException, ApplicationException
	{
        String select = "SELECT "
			+ " A.SYSTEM_NO,"							//システム受付番号
			+ " A.UKETUKE_NO,"							//申請番号
			+ " A.SHINSAIN_NO,"							//審査員番号
			+ " A.JIGYO_ID,"							//事業ID
			+ " SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD,"		//事業コード
			+ " A.JIGYO_NAME,"							//事業名		
			+ " A.BUNKASAIMOKU_CD,"						//分科細目コード
			+ " A.KEKKA_ABC,"							//総合評価（ABC）
			+ " A.KEKKA_TEN,"							//総合評価（点数）
			+ " A.KENKYUNAIYO,"							//研究内容
			+ " A.KENKYUKEIKAKU,"						//研究計画
			+ " A.TEKISETSU_KAIGAI,"					//適切性-海外
			+ " A.TEKISETSU_KENKYU1,"					//適切性-研究(1)
			+ " A.TEKISETSU,"							//適切性
			+ " A.DATO,"								//妥当性
			+ " A.COMMENTS,"							//コメント
			//2005.11.03 kainuma
//2006/10/30　苗　修正ここから            
//            + " A.RIGAI,"                               //利害関係
            + " NVL(A.RIGAI,'0') RIGAI,"                //利害関係
//2006/10/30　苗　修正ここまで            
			+ " A.DAIRI,"								//代理フラグ
			+ " A.TEKISETSU_WAKATES,"					//若手(S)としての妥当性	//2007/5/8 追加
			+ " A.JUYOSEI,"								//学術的重要性・妥当性
			+ " A.DOKUSOSEI,"							//独創性・革新性
			+ " A.HAKYUKOKA,"							//波及効果・普遍性
			+ " A.SUIKONORYOKU,"						//遂行能力・環境の適切性
			+ " A.JINKEN,"								//人権の保護
			+ " A.BUNTANKIN,"							//分担金配分
			+ " A.OTHER_COMMENT,"						//その他のコメント
			//
			+ " A.KOSHIN_DATE,"							//割り振り更新日		2005/11/08 追加
			+ " A.SHINSA_JOKYO,"						//審査状況
//2006/10/27 苗　追加ここから
            + " NVL(A.NYURYOKU_JOKYO,'0') NYURYOKU_JOKYO,"//入力状況
//2006/10/27　苗　追加ここまで            
			+ " B.SHINSEISHA_ID,"						//申請者ID
			+ " B.NAME_KANJI_SEI,"						//申請者氏名（漢字等-姓）
			+ " B.NAME_KANJI_MEI,"						//申請者氏名（漢字等-名）
			+ " B.SHOZOKU_CD,"							//所属機関コード
			+ " B.SHOZOKU_NAME_RYAKU,"					//所属機関名（略称）
			+ " B.BUKYOKU_NAME_RYAKU,"					//部局名（略称）
			+ " B.SHOKUSHU_NAME_RYAKU,"					//職名（略称）
			+ " B.KADAI_NAME_KANJI,"					//研究課題名(和文）
			+ " B.SAIMOKU_NAME,"						//細目名
			+ " DECODE"
			+ " ("
			+ "  NVL(B.SUISENSHO_PATH,'null') "
			+ "  ,'null','FALSE'"						//推薦書パスがNULLのとき
			+ "  ,      'TRUE'"							//推薦書パスがNULL以外のとき
			+ " ) SUISENSHO_FLG, "						//推薦書登録フラグ
			+ " B.BUNKATSU_NO,"							//分割番号	
			+ " B.BUNTANKIN_FLG,"						//分担金の有無
			+ " B.KAIGAIBUNYA_CD,"						//海外分野コード
			//add start ly 2006/04/10
			+ " B.KAIGAIBUNYA_NAME,"                    //海外分野名
			+ " B.BUNYA_NAME,"                          //分野名
			//add end ly 2006/04/10
			+ " B.KAIGAIBUNYA_NAME_RYAKU,"				//海外分野名（略称）
			+ " B.SHINSEI_FLG_NO,"						//研究計画最終年度前年度の応募			
			+ " B.JOKYO_ID,"							//申請状況ID
			+ " C.SHINSAKIGEN,"							//審査期限
			+ " C.NENDO,"								//年度
			+ " C.KAISU,"								//回数
			+ " DECODE"
			+ " ("
			+ "  SIGN( TO_DATE(TO_CHAR(C.SHINSAKIGEN, 'YYYY/MM/DD'),'YYYY/MM/DD')  - TO_DATE(TO_CHAR(SYSDATE, 'YYYY/MM/DD'),'YYYY/MM/DD') )"
			+ "  ,0 , 'TRUE'"							//現在時刻と同じ場合
			+ "  ,1 , 'TRUE'"							//現在時刻の方が審査期限より前
			+ "  ,-1, 'FALSE'"							//現在時刻の方が審査期限より後
			+ " ) SHINSAKIGEN_FLAG"						//審査期限到達フラグ
			+ " FROM"
			+ " (SELECT * FROM SHINSAKEKKA"+dbLink
			+ "  WHERE "
			+ "  SHINSAIN_NO = ?"				//当該審査員番号
			;
//		if ("1".equals(jigyoKubun)){
//			select = select	+ "  AND JIGYO_KUBUN = ?";			//当該事業区分
//		}else{
//			select = select	+ "  AND (JIGYO_KUBUN = ? or JIGYO_KUBUN = 6)";			//当該事業区分
//		}
//2006/04/25 追加ここから
        Set shinsaiSet = JigyoKubunFilter.Convert2ShinsainJigyoKubun(jigyoKubun) ; //審査員の事業区分のSet
        String jigyoKubunTemp = (String)shinsaiSet.iterator().next();
        
        Set shinseiSet = JigyoKubunFilter.Convert2ShinseishoJigyoKubun(jigyoKubunTemp) ; //申請の事業区分のSet
        //CSVを取得
        String shinseiJigyoKubun =  StringUtil.changeIterator2CSV(shinseiSet.iterator(),true);
        
        select = select 
            + "   AND JIGYO_KUBUN IN ("
            + shinseiJigyoKubun
            + "   )";
//苗　追加ここまで      
		select = select 
			+ "  AND JIGYO_ID = ?"				//当該事業ID				
			+ " ) A,"
			+ " (SELECT * FROM SHINSEIDATAKANRI"+dbLink
			+ "   WHERE "
			+ "    (JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSATYU + "'"		//申請状況が[10]
			+ "     OR "
			+ "     JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSA_KANRYO + "'"	//申請状況が[11]
			+ "    )"
			+ "    AND DEL_FLG = '0'"		//削除されてないもの				
			+ " ) B,"
//			+ " (SELECT * FROM JIGYOKANRI"+dbLink
//			+ "  WHERE"
//			+ "   JIGYO_ID = ?"				//当該事業ID
//			+ " ) C"
			+ " JIGYOKANRI" +dbLink 
			+ " C"
			+ " WHERE"
			+ " A.SYSTEM_NO = B.SYSTEM_NO"
			+ " AND"
			+ " A.JIGYO_ID = C.JIGYO_ID"			
			;
			
		StringBuffer query = new StringBuffer(select);
		
		//パラメータ情報をセット
		ArrayList list = new ArrayList();
		list.add(shinsainNo);	//審査員番号
//		list.add(jigyoKubun);	//事業区分
		list.add(jigyoId);		//事業ID
		
		//総合評価（点数）
		//null（学創・特推と基盤の初期表示）の場合は、検索条件に加えない。
		if(kekkaTen != null && kekkaTen.length() != 0){	
			if(kekkaTen.equals("-1")){
				//「-1：未入力」を選択された場合は、NULLで検索
				query.append(" AND A.KEKKA_TEN IS NULL");						
			}else{
				//審査結果が「5～-」の場合
				query.append(" AND A.KEKKA_TEN = ?");
				list.add(kekkaTen);		
			}
		}
      
//2006/10/27 苗　追加ここから
        //利害関係
        //nullの場合は、検索条件に加えない。
        if(rigai != null && rigai.length() != 0){ 
            if(rigai.equals("0")){
                //「0：利害関係以外の課題 」を選択された場合は、NULLで検索
                query.append(" AND NVL(A.RIGAI,'0') = 0");                       
            }else{
                query.append(" AND A.RIGAI = ?");
                list.add(rigai);     
            }
        }
//2006/10/27　苗　追加ここまで        

		//ソート順（システム受付番号の昇順）
//		query.append(" ORDER BY A.SYSTEM_NO");
		//ソート順の変更
		query.append(" ORDER BY");
		//基盤の場合、細目番号でソートする。
		
		//2006.07.03 iso 若スタ・基盤相当のソート順に細目番号が反映されないバグを修正
		//学創・特推以外は、条件を加えるよう修正した。
//		if(jigyoKubun != null && jigyoKubun.equals("4")) {
		if(jigyoKubun != null
				&& !jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)
				&& !jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO)
				&& !jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_TOKUSUI)) {
			//2007/5/8 細目コード　→　分割A・B　→　機関・整理番号（機関コード　→　整理番号）の順でソートする。
			query.append(" A.BUNKASAIMOKU_CD,");
			query.append(" B.BUNKATSU_NO,");
		}
		query.append(" A.UKETUKE_NO");
		
		//for debug
		if(log.isDebugEnabled()){
			log.debug("query:" + query.toString());
		}
		
		// ページ取得		
		return SelectUtil.selectPageInfo(connection, searchInfo, query.toString(), (String[])list.toArray(new String[list.size()]));
	}
	
	/**	総合評価（点数）ごとの件数を取得する。
     * @param connection コネクション
     * @param shinsainNo 審査員番号
     * @param jigyoKubun 事業区分
     * @param jigyoId 事業ＩＤ
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
	 */
	public List getSogoHyokaList(
			Connection connection, 
			String shinsainNo, 
			String jigyoKubun,
			String jigyoId)
			throws NoDataFoundException, DataAccessException {

		String select =
			"SELECT "
				+ " A.KEKKA_TEN"					//総合評価（点数）
				+ " ,COUNT(*) COUNT"				//件数
				+ " FROM SHINSAKEKKA"+dbLink+" A,"
				+ " (SELECT * FROM SHINSEIDATAKANRI"+dbLink
				+ "   WHERE "
				+ "    (JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSATYU + "'"		//申請状況が[10]
				+ "     OR "
				+ "     JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSA_KANRYO + "'"	//申請状況が[11]
				+ "    )"
				+ "    AND DEL_FLG = '0'"		//削除されてないもの				
				+ " ) B"
				+ " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
				+ "   AND A.SHINSAIN_NO = ?"
				;

		//+ "   AND A.JIGYO_KUBUN = ?"
//		if ("1".equals(jigyoKubun)){
//			select = select	+ "   AND A.JIGYO_KUBUN = ?";
//		}else{
//			select = select	+ "   AND (A.JIGYO_KUBUN = ? or A.JIGYO_KUBUN = 6)";
//		}
        
//2006/04/25 追加ここから
        Set shinsaiSet = JigyoKubunFilter.Convert2ShinsainJigyoKubun(jigyoKubun) ; //審査員の事業区分のSet
        String jigyoKubunTemp = (String)shinsaiSet.iterator().next();
        
        Set shinseiSet = JigyoKubunFilter.Convert2ShinseishoJigyoKubun(jigyoKubunTemp) ; //申請の事業区分のSet
        //CSVを取得
        String shinseiJigyoKubun =  StringUtil.changeIterator2CSV(shinseiSet.iterator(),true);
        
        select = select 
            + "   AND A.JIGYO_KUBUN IN ("
            + shinseiJigyoKubun
            + "   )";
//苗　追加ここまで         

		select = select	+ "   AND A.JIGYO_ID = ?"
				+ " GROUP BY A.KEKKA_TEN"
				;

		StringBuffer  query = new StringBuffer(select);
		
//2005/11/15 利害関係の場合、件数不正のバグを修正する為 by xiang
		//ソート順（総合評価（点数）の降順）
		//query.append(" ORDER BY NVL(REPLACE(A.KEKKA_TEN, '-', '0'), '-1') DESC");					
		query.append(" ORDER BY NVL(A.KEKKA_TEN,' ') DESC");

		//for debug
		if(log.isDebugEnabled()){
			log.debug("query:" + query.toString());
		}
		
		// リスト取得		
		return SelectUtil.select(connection, query.toString(), new String[]{shinsainNo,jigyoId});
	}

	/**
	 * 審査結果情報を削除する。(物理削除) 
	 * @param connection			コネクション
	 * @param deletePk				削除する審査結果情報主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteShinsaKekkaInfo(
		Connection connection,
		ShinsaKekkaPk deletePk)
		throws DataAccessException, NoDataFoundException
	{
		//検索（対象データが存在しなかった場合は例外発生）
		selectShinsaKekkaInfo(connection, deletePk);

		String query =
			"DELETE FROM SHINSAKEKKA"+dbLink
				+ " WHERE"
				+ "  SYSTEM_NO = ?"
				+ " AND"
				+ "  SHINSAIN_NO = ?"
				+ " AND"
				+ "  JIGYO_KUBUN = ?"
				;

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, deletePk.getSystemNo());		//システム受付番号
			DatabaseUtil.setParameter(preparedStatement, i++, deletePk.getShinsainNo());	//審査員番号
			DatabaseUtil.setParameter(preparedStatement, i++, deletePk.getJigyoKubun());	//事業区分
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * 審査結果情報を削除する。(物理削除) 
	 * @param connection			コネクション
	 * @param deletePk				削除する申請情報主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 */
	public void deleteShinsaKekkaInfo(
		Connection connection,
		ShinseiDataPk deletePk)
        throws DataAccessException
	{

		String query =
			"DELETE FROM SHINSAKEKKA"+dbLink
				+ " WHERE"
				+ "  SYSTEM_NO = ?"
				;

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, deletePk.getSystemNo());		//システム受付番号
			preparedStatement.executeUpdate();		//0件～複数件返す

		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * 審査結果情報を削除する。(物理削除)
	 * 事業IDに紐づく添付情報を全て削除する。
	 * 該当データが存在しなかった場合は何も処理しない。  
	 * @param connection			コネクション
	 * @param jigyoId				検索条件（事業ID）
	 * @throws DataAccessException	削除中に例外が発生した場合
	 */
	public void deleteShinsaKekkaInfo(
		Connection connection,
		String jigyoId)
		throws DataAccessException
	{
		String query =
			"DELETE FROM SHINSAKEKKA"+dbLink
				+ " WHERE"
				+ " JIGYO_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, jigyoId);		//検索条件（事業ID）
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報削除中に例外が発生しました。 ", ex);
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
			"INSERT INTO SHINSAKEKKA"+dbLink
				+ " SELECT * FROM SHINSAKEKKA WHERE JIGYO_ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, jigyoId);		//検索条件（事業ID）
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報保管中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * 未審査の申請書を持つ審査員情報（審査員No、メールアドレス）と
	 * 事業情報（事業ID、年度、事業名）を返す。
	 * 第二引数に指定した日付が審査期限（当日のみ）である事業のみを検索する。
	 * @param connection
	 * @param shinsaKigen
	 * @return
	 * @throws IllegalArgumentException  第二引数がnullの場合
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 */
	public List selectShinsainWithNonExamined(
		Connection connection,
		Date       shinsaKigen)
		throws IllegalArgumentException, DataAccessException, NoDataFoundException
	{	
		if(shinsaKigen == null){
			throw new IllegalArgumentException();
		}
		
		String query =
		"SELECT DISTINCT"
			+ "  S.SHINSAIN_NO,"
			+ "  J.JIGYO_ID,"
			+ "  J.NENDO,"
			+ "  J.JIGYO_NAME,"
			+ "  M.SOFU_ZIPEMAIL"
			+ " FROM"
			+ "  (SELECT"
			+ "    SHINSAIN_NO,"
			+ "    JIGYO_ID"
			+ "   FROM"
			+ "    SHINSAKEKKA"+dbLink
			+ "   WHERE"
			+ "    SHINSA_JOKYO = '0' OR SHINSA_JOKYO IS NULL) S,"	//審査未完了のもの
			+ "  (SELECT"
			+ "    JIGYO_ID,"
			+ "    NENDO,"
			+ "    JIGYO_NAME"
			+ "   FROM"
			+ "    JIGYOKANRI"+dbLink
			+ "   WHERE"
			+ "    TO_CHAR(SHINSAKIGEN,'YYYY/MM/DD') = ?) J,"
			+ "  MASTER_SHINSAIN M"
			+ " WHERE"
			+ "  S.JIGYO_ID=J.JIGYO_ID"
			+ " AND"
			+ "  S.SHINSAIN_NO=M.SHINSAIN_NO"
			+ " ORDER BY"
			+ "  S.SHINSAIN_NO, J.JIGYO_ID"
			;
		
		//for debug
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//リスト取得
		return SelectUtil.select(
					connection, 
					query, 
					new String[]{new SimpleDateFormat("yyyy/MM/dd").format(shinsaKigen)}
				);				

	}
	
	/**
	 * 当該審査員が担当する当該事業IDの審査結果をクリアする。
	 * ただし、[審査未完了]のものだけ。
	 * @param connection
	 * @param shinsaJokyoInfo
	 * @return 更新レコード数
	 * @throws DataAccessException
	 */
	public int clearShinsaKekka(
		Connection connection,
		ShinsaJokyoInfo shinsaJokyoInfo)
		throws DataAccessException
	{
		String query
			= "UPDATE SHINSAKEKKA"+dbLink
				+ " SET"	
				+ "  KEKKA_ABC = NULL "					//総合評価（ABC）
				+ " ,KEKKA_TEN = NULL "					//総合評価（点数）
				+ " ,COMMENT1 = NULL "					//コメント1
				+ " ,COMMENT2 = NULL "					//コメント2
				+ " ,COMMENT3 = NULL "					//コメント3
				+ " ,COMMENT4 = NULL "					//コメント4
				+ " ,COMMENT5 = NULL "					//コメント5
				+ " ,COMMENT6 = NULL "					//コメント6
				+ " ,KENKYUNAIYO = NULL "				//研究内容
				+ " ,KENKYUKEIKAKU = NULL "				//研究計画
				+ " ,TEKISETSU_KAIGAI = NULL "			//適切性-海外
				+ " ,TEKISETSU_KENKYU1 = NULL "			//適切性-研究（1）
				+ " ,TEKISETSU = NULL "					//適切性
				+ " ,DATO = NULL "						//妥当性
				+ " ,SHINSEISHA = NULL "				//研究代表者
				+ " ,KENKYUBUNTANSHA = NULL "			//研究分担者
				+ " ,HITOGENOMU = NULL "				//ヒトゲノム
				+ " ,TOKUTEI = NULL "					//特定胚
				+ " ,HITOES = NULL "					//ヒトES細胞
				+ " ,KUMIKAE = NULL "					//遺伝子組換え実験
				+ " ,CHIRYO = NULL "					//遺伝子治療臨床研究
				+ " ,EKIGAKU = NULL "					//疫学研究
				+ " ,COMMENTS = NULL "					//コメント
				//2005.11.03 kainuma
				+ " ,RIGAI = NULL"						//利害関係
				+ " ,DAIRI = NULL"						//代理フラグ
				+ " ,TEKISETSU_WAKATES = NULL"			//若手(S)としての妥当性	//2007/5/8 追加
				+ " ,JUYOSEI = NULL"					//学術的重要性・妥当性
				+ " ,DOKUSOSEI = NULL"					//独創性・革新性
				+ " ,HAKYUKOKA = NULL"					//波及効果・普遍性
				+ " ,SUIKONORYOKU = NULL"				//遂行能力・環境の適切性
				+ " ,JINKEN = NULL"						//人権の保護
				+ " ,BUNTANKIN = NULL"					//分担金配分
				+ " ,OTHER_COMMENT = NULL"				//その他のコメント
				//
				+ " ,KOSHIN_DATE = NULL"				//割り振り更新日		2005/11/08 追加
				+ " ,TENPU_PATH = NULL "				//添付ファイル格納パス
                //2006/10/23 張志男 追加 ここから
                     /** 入力状況 */
                + " ,NYURYOKU_JOKYO = '0' "     
                //2006/10/23 張志男 追加 ここまで
				+ " WHERE "
				+ "   SHINSAIN_NO = ?"
				+ " AND "
				+ "   JIGYO_ID = ?"
				+ " AND "
				+ "   SHINSA_JOKYO = '" + ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET + "'"
				;
		
		PreparedStatement preparedStatement = null;
		try {
			//更新
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsaJokyoInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,shinsaJokyoInfo.getJigyoId());
			
			return preparedStatement.executeUpdate();	
			
		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報更新中（クリア中）に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
		
	}
	
	

	/**
	 * 当該審査員が担当する当該事業IDの審査結果情報を取得する。
	 * ただし、[審査未完了]のものだけ。
	 * @param connection
	 * @param shinsaJokyoInfo
	 * @return 審査結果情報
	 * @throws DataAccessException
     * @throws NoDataFoundException
	 */
	public ShinsaKekkaInfo[] selectShinsaKekkaInfo(
			Connection connection,
			ShinsaJokyoInfo shinsaJokyoInfo)
			throws DataAccessException, NoDataFoundException{
		
		String query =
			"SELECT "
//テーブル情報は単体selectShinsaKekkaInfoに合わせる
				+ " A.SYSTEM_NO"				//システム受付番号
				+ ",A.UKETUKE_NO"				//申請番号
				+ ",A.SHINSAIN_NO"				//審査員番号
				+ ",A.JIGYO_KUBUN"				//事業区分
				+ ",A.SEQ_NO"					//シーケンス番号
				+ ",A.SHINSA_KUBUN"				//審査区分
				+ ",A.SHINSAIN_NAME_KANJI_SEI"	//審査員名（漢字－姓）
				+ ",A.SHINSAIN_NAME_KANJI_MEI"	//審査員名（漢字－名）
				+ ",A.NAME_KANA_SEI"			//審査員名（フリガナ－姓）
				+ ",A.NAME_KANA_MEI"			//審査員名（フリガナ－名）
				+ ",A.SHOZOKU_NAME"				//審査員所属機関名
				+ ",A.BUKYOKU_NAME"				//審査員部局名
				+ ",A.SHOKUSHU_NAME"			//審査員職名
				+ ",A.JIGYO_ID"					//事業ID
				+ ",A.JIGYO_NAME"				//事業名
				+ ",A.BUNKASAIMOKU_CD"			//細目番号
				+ ",A.EDA_NO"					//枝番				
				+ ",A.CHECKDIGIT"				//チェックデジット
				+ ",A.KEKKA_ABC"				//総合評価（ABC）
				+ ",A.KEKKA_TEN"				//総合評価（点数）
				+ ",A.COMMENT1"					//コメント1			
				+ ",A.COMMENT2"					//コメント2
				+ ",A.COMMENT3"					//コメント3
				+ ",A.COMMENT4"					//コメント4
				+ ",A.COMMENT5"					//コメント5
				+ ",A.COMMENT6"					//コメント6
				+ ",A.KENKYUNAIYO"				//研究内容
				+ ",A.KENKYUKEIKAKU"			//研究計画		
				+ ",A.TEKISETSU_KAIGAI"			//適切性-海外
				+ ",A.TEKISETSU_KENKYU1"		//適切性-研究（1）
				+ ",A.TEKISETSU"				//適切性			
				+ ",A.DATO"						//妥当性
				+ ",A.SHINSEISHA"				//研究代表者
				+ ",A.KENKYUBUNTANSHA"			//研究分担者
				+ ",A.HITOGENOMU"				//ヒトゲノム
				+ ",A.TOKUTEI"					//特定胚
				+ ",A.HITOES"					//ヒトES細胞
				+ ",A.KUMIKAE"					//遺伝子組換え実験
				+ ",A.CHIRYO"					//遺伝子治療臨床研究			
				+ ",A.EKIGAKU"					//疫学研究
				+ ",A.COMMENTS"					//コメント
				//2005.10.25 kainuma	
			  	+ ",A.RIGAI"					//利害関係
			  	+ ",A.DAIRI"					//代理フラグ
				+ ",A.TEKISETSU_WAKATES"		//若手(S)としての妥当性	//2007/5/8 追加
			  	+ ",A.JUYOSEI"					//学術的重要性・妥当性
			  	+ ",A.DOKUSOSEI"				//独創性・革新性
			  	+ ",A.HAKYUKOKA"				//波及効果・普遍性
			  	+ ",A.SUIKONORYOKU"				//遂行能力・環境の適切性
			  	+ ",A.JINKEN"					//人権の保護・法令等の遵守
			  	+ ",A.BUNTANKIN"				//分担金配分			
			  	+ ",A.OTHER_COMMENT"			//その他コメント
			  	//2005.10.25 kainuma
			  	+ ",A.KOSHIN_DATE"				//割り振り更新日		2005/11/08 追加
				+ ",A.TENPU_PATH"				//添付ファイル格納パス			
				+ ",DECODE"
				+ " ("
				+ "  NVL(A.TENPU_PATH,'null') "
				+ "  ,'null','FALSE'"			//添付ファイル格納パスがNULLのとき
				+ "  ,      'TRUE'"				//添付ファイル格納パスがNULL以外のとき
				+ " ) TENPU_FLG"				//添付ファイル格納フラグ
				+ ",A.SHINSA_JOKYO"				//審査状況
				+ ",A.BIKO"						//備考
				+ " FROM SHINSAKEKKA"+dbLink+" A"
				+ " WHERE"
				+ " SHINSAIN_NO = ?"
				+ " AND JIGYO_ID = ?"
				+ " AND SHINSA_JOKYO = '" + ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET + "'"
				;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ShinsaKekkaInfo[] shinseiKekkaInfo = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsaJokyoInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,shinsaJokyoInfo.getJigyoId());
			resultSet = preparedStatement.executeQuery();

			List resultList = new ArrayList();
			while(resultSet.next()){
				ShinsaKekkaInfo result = new ShinsaKekkaInfo();
				result.setSystemNo(resultSet.getString("SYSTEM_NO"));
				result.setUketukeNo(resultSet.getString("UKETUKE_NO"));
				result.setShinsainNo(resultSet.getString("SHINSAIN_NO"));		
				result.setJigyoKubun(resultSet.getString("JIGYO_KUBUN"));		
				result.setSeqNo(resultSet.getString("SEQ_NO"));
				result.setShinsaKubun(resultSet.getString("SHINSA_KUBUN"));
				result.setShinsainNameKanjiSei(resultSet.getString("SHINSAIN_NAME_KANJI_SEI"));
				result.setShinsainNameKanjiMei(resultSet.getString("SHINSAIN_NAME_KANJI_MEI"));
				result.setNameKanaSei(resultSet.getString("NAME_KANA_SEI"));
				result.setNameKanaMei(resultSet.getString("NAME_KANA_MEI"));
				result.setShozokuName(resultSet.getString("SHOZOKU_NAME"));
				result.setBukyokuName(resultSet.getString("BUKYOKU_NAME"));
				result.setShokushuName(resultSet.getString("SHOKUSHU_NAME"));
				result.setJigyoId(resultSet.getString("JIGYO_ID"));
				result.setJigyoName(resultSet.getString("JIGYO_NAME"));
				result.setBunkaSaimokuCd(resultSet.getString("BUNKASAIMOKU_CD"));
				result.setEdaNo(resultSet.getString("EDA_NO"));
				result.setCheckDigit(resultSet.getString("CHECKDIGIT"));		
				result.setKekkaAbc(resultSet.getString("KEKKA_ABC"));		
				result.setKekkaTen(resultSet.getString("KEKKA_TEN"));
				result.setComment1(resultSet.getString("COMMENT1"));
				result.setComment2(resultSet.getString("COMMENT2"));
				result.setComment3(resultSet.getString("COMMENT3"));
				result.setComment4(resultSet.getString("COMMENT4"));
				result.setComment5(resultSet.getString("COMMENT5"));
				result.setComment6(resultSet.getString("COMMENT6"));
				result.setKenkyuNaiyo(resultSet.getString("KENKYUNAIYO"));
				result.setKenkyuKeikaku(resultSet.getString("KENKYUKEIKAKU"));
				result.setTekisetsuKaigai(resultSet.getString("TEKISETSU_KAIGAI"));			
				result.setTekisetsuKenkyu1(resultSet.getString("TEKISETSU_KENKYU1"));
				result.setTekisetsu(resultSet.getString("TEKISETSU"));
				result.setDato(resultSet.getString("DATO"));
				result.setShinseisha(resultSet.getString("SHINSEISHA"));
				result.setKenkyuBuntansha(resultSet.getString("KENKYUBUNTANSHA"));
				result.setHitogenomu(resultSet.getString("HITOGENOMU"));
				result.setTokutei(resultSet.getString("TOKUTEI"));
				result.setHitoEs(resultSet.getString("HITOES"));
				result.setKumikae(resultSet.getString("KUMIKAE"));
				result.setChiryo(resultSet.getString("CHIRYO"));
				result.setEkigaku(resultSet.getString("EKIGAKU"));
				result.setComments(resultSet.getString("COMMENTS"));
				//2005.10.26 kainuma
			  	result.setRigai(resultSet.getString("RIGAI"));
				result.setDairi(resultSet.getString("DAIRI"));
				result.setWakates(resultSet.getString("TEKISETSU_WAKATES"));//2007/5/8 追加
			  	result.setJuyosei(resultSet.getString("JUYOSEI"));
			  	result.setDokusosei(resultSet.getString("DOKUSOSEI"));
			  	result.setHakyukoka(resultSet.getString("HAKYUKOKA"));
			  	result.setSuikonoryoku(resultSet.getString("SUIKONORYOKU"));
			  	result.setJinken(resultSet.getString("JINKEN"));
			  	result.setBuntankin(resultSet.getString("BUNTANKIN"));
			  	result.setOtherComment(resultSet.getString("OTHER_COMMENT"));
			  	//
				result.setKoshinDate(resultSet.getDate("KOSHIN_DATE"));
				result.setTenpuPath(resultSet.getString("TENPU_PATH"));
				result.setTenpuFlg(resultSet.getString("TENPU_FLG"));
				result.setShinsaJokyo(resultSet.getString("SHINSA_JOKYO"));
				result.setBiko(resultSet.getString("BIKO"));			
				resultList.add(result);
			}
			
			//戻り値
			shinseiKekkaInfo = (ShinsaKekkaInfo[])resultList.toArray(new ShinsaKekkaInfo[0]);
			if(shinseiKekkaInfo.length == 0){
				throw new NoDataFoundException(
					"審査結果情報テーブルに該当するデータが見つかりません。検索キー：審査員番号'"
						+ shinsaJokyoInfo.getShinsainNo() + "', 事業ID'" + shinsaJokyoInfo.getJigyoId() + "'");
			}

		} catch (SQLException ex) {
			throw new DataAccessException("審査結果情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(resultSet, preparedStatement);
		}
		
		return shinseiKekkaInfo;		
		
	}



	/*
	 * 
	 * 審査件数一覧の全件数を返す。
	 * @param	connection			コネクション
     * @param select          審査員番号
     * @return int
     * @throws DataAccessException
	 */
	public int countTotalPage(Connection connection, String select)
			throws DataAccessException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		//SQL文は、ShinsajokyoKakuninクラスで作成。
		//本来はここで作成すべきだが、検索条件文等を他で使用しているため、移動できず。

		PreparedStatement preparedStatement = null;
		ResultSet         rset              = null;
		try {
			//取得
			preparedStatement = connection.prepareStatement(select);
			rset = preparedStatement.executeQuery();
			int count = 0;
			if (rset.next()) {
				count = rset.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("審査情報レコード検索中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(rset, preparedStatement);
		}
	}	
}
