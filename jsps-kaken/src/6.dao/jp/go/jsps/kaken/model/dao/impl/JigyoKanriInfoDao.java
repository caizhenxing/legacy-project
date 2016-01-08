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
import java.util.Date;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * 事業管理情報データアクセスクラス。
 * ID RCSfile="$RCSfile: JigyoKanriInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class JigyoKanriInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(JigyoKanriInfoDao.class);

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
	public JigyoKanriInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * コンストラクタ。
	 * @param userInfo	実行するユーザ情報
	 * @param dbLink   DBリンク名
	 */
	public JigyoKanriInfoDao(UserInfo userInfo, String dbLink) {
		this.userInfo = userInfo;
		this.dbLink   = dbLink;
	}
	
	/**
	 * SEQテーブルを作成する。
	 * @param connection			コネクション
	 * @param jigyoId				事業ID
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 */
	public void createSEQ(
		Connection connection,
		String jigyoId)
		throws DataAccessException {
		
		String query =
			"CREATE SEQUENCE SEQ_" + EscapeUtil.toSqlString(jigyoId)
			+ " INCREMENT BY 1"
			+ " START WITH 1"
			+ " MAXVALUE 999999"
			+ " NOMINVALUE"
			+ " CYCLE" 
			+ " CACHE 20"
			+ " NOORDER";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			recordSet = preparedStatement.executeQuery();
		} catch (SQLException ex) {
			throw new DataAccessException("SEQテーブル作成中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * 事業管理情報を取得する。削除フラグが「0」の場合のみ検索する。
	 * @param connection			コネクション
	 * @param primaryKeys			主キー情報
	 * @return						事業管理情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public JigyoKanriInfo selectJigyoKanriInfo(
		Connection connection,
		JigyoKanriPk primaryKeys)
		throws DataAccessException, NoDataFoundException {
		String query =
			"SELECT "
				+ " A.JIGYO_ID"				//事業ID
				+ ",A.NENDO"				//年度
				+ ",A.KAISU"				//回数
				+ ",A.JIGYO_NAME"			//事業名
				+ ",A.JIGYO_KUBUN"			//事業区分
				+ ",A.SHINSA_KUBUN"			//審査区分
				+ ",A.TANTOKA_NAME"			//業務担当課
				+ ",A.TANTOKAKARI"			//業務担当係名
				+ ",A.TOIAWASE_NAME"		//問い合わせ先担当者名
				+ ",A.TOIAWASE_TEL"			//問い合わせ先電話番号
				+ ",A.TOIAWASE_EMAIL"		//問い合わせ先E-mail
				+ ",A.UKETUKEKIKAN_START"	//学振受付期間（開始）
				+ ",A.UKETUKEKIKAN_END"		//学振受付期間（終了）
				//2005/04/25 追加 ここから----------------------------------------------------
				//理由 URLの追加のため
				+ ",A.URL_TITLE"			//URL(タイトル)
				+ ",A.URL_ADDRESS"			//URL(アドレス)
				+ ",A.DL_URL"				//ダウンロードURL
				//追加 ここまで---------------------------------------------------------------
				+ ",A.SHINSAKIGEN"			//審査期限
				+ ",A.TENPU_NAME"			//添付文書名
				+ ",A.TENPU_WIN"			//添付ファイル格納フォルダ（Win）
				+ ",A.TENPU_MAC"			//添付ファイル格納フォルダ（Mac）
//2007/02/03 苗　追加ここから
                + ",A.PAGE_FROM"            //応募内容ファイルページ数(下限)
                + ",A.PAGE_TO"              //応募内容ファイルページ数(上限)
//2007/02/03　苗　追加ここまで                
				+ ",A.HYOKA_FILE_FLG"		//評価用ファイル有無
				+ ",A.HYOKA_FILE"			//評価用ファイル格納フォルダ
				//2006/02/08 追加　苗　ここから----------------------------------------------------
                //理由　研究者名簿締切日の追加のため
				+ ",A.MEIBO_DATE"           //研究者名簿締切日
                //追加 ここまで---------------------------------------------------------------
//				2006/06/14 追加　劉洋　ここから----------------------------------------------------
                //理由　仮領域番号発行締切日の追加のため
				+ ",A.KARIRYOIKINO_UKETUKEKIKAN_END "           //仮領域番号発行締切日
                //追加 ここまで---------------------------------------------------------------
//              2006/07/10 追加　李義華　ここから----------------------------------------------------
                //理由　領域代表者確定締切日の追加のため
                + ",A.RYOIKI_KAKUTEIKIKAN_END　 "                //領域代表者確定締切日                
                //追加 ここまで---------------------------------------------------------------                
				+ ",A.KOKAI_FLG"			//公開フラグ
				+ ",A.KESSAI_NO"			//公開決裁番号
				+ ",A.KOKAI_ID"				//公開確定者ID
				+ ",A.HOKAN_DATE"			//データ保管日
				+ ",A.YUKO_DATE"			//保管有効期限
				+ ",A.BIKO"					//備考
				+ ",A.DEL_FLG"				//削除フラグ
//				 2006/10/24 易旭 追加 ここから
				+ ",A.RIGAI_KIKAN_END"		//利害関係入力締切日
//				 2006/10/24 易旭 追加 ここまで
				+ " FROM JIGYOKANRI"+dbLink+" A"
				+ " WHERE JIGYO_ID = ?"
//2004/10/26update　システム管理者向け申請情報検索への対応
//				+ " AND DEL_FLG = 0"//削除フラグ
				;
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			JigyoKanriInfo result = new JigyoKanriInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getJigyoId());//事業ID
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setJigyoId(recordSet.getString("JIGYO_ID"));							//事業ID
				result.setNendo(recordSet.getString("NENDO"));								//年度
				result.setKaisu(recordSet.getString("KAISU"));								//回数
				result.setJigyoName(recordSet.getString("JIGYO_NAME"));						//事業名
				result.setJigyoKubun(recordSet.getString("JIGYO_KUBUN"));					//事業区分
				result.setShinsaKubun(recordSet.getString("SHINSA_KUBUN"));					//審査区分
				result.setTantokaName(recordSet.getString("TANTOKA_NAME"));					//業務担当課
				result.setTantoKakari(recordSet.getString("TANTOKAKARI"));					//業務担当係名
				result.setToiawaseName(recordSet.getString("TOIAWASE_NAME"));				//問い合わせ先担当者名
				result.setToiawaseTel(recordSet.getString("TOIAWASE_TEL"));					//問い合わせ先電話番号
				result.setToiawaseEmail(recordSet.getString("TOIAWASE_EMAIL"));				//問い合わせ先E-mail
				result.setUketukekikanStart(recordSet.getDate("UKETUKEKIKAN_START"));		//学振受付期間（開始）
				result.setUketukekikanEnd(recordSet.getDate("UKETUKEKIKAN_END"));			//学振受付期間（終了）
				result.setUrlTitle(recordSet.getString("URL_TITLE"));						//URLタイトル
				result.setUrlAddress(recordSet.getString("URL_ADDRESS"));					//URLアドレス
				result.setDlUrl(recordSet.getString("DL_URL"));								//ダウンロードURL
				result.setShinsaKigen(recordSet.getDate("SHINSAKIGEN"));					//審査期限
				result.setTenpuName(recordSet.getString("TENPU_NAME"));						//添付文書名
				result.setTenpuWin(recordSet.getString("TENPU_WIN"));						//添付ファイル格納フォルダ（Win）
				result.setTenpuMac(recordSet.getString("TENPU_MAC"));						//添付ファイル格納フォルダ（Mac）
//2007/02/03 苗　追加ここから
                result.setPageFrom(recordSet.getString("PAGE_FROM"));                          //応募内容ファイルページ数(下限)
                result.setPageTo(recordSet.getString("PAGE_TO"));                              //応募内容ファイルページ数(上限)
//2007/02/03　苗　追加ここまで                
				result.setHyokaFileFlg(recordSet.getString("HYOKA_FILE_FLG"));				//評価用ファイル有無
				result.setHyokaFile(recordSet.getString("HYOKA_FILE"));						//評価用ファイル格納フォルダ
//2006/02/08 追加　苗　ここから----------------------------------------------------
//理由　研究者名簿締切日の追加のため
				result.setMeiboDate(recordSet.getDate("MEIBO_DATE"));                       //研究者名簿締切日
//追加 ここまで---------------------------------------------------------------
//				2006/02/08 追加　劉洋　ここから----------------------------------------------------
//				理由　仮領域番号発行締切日の追加のため
				result.setKariryoikiNoEndDate(recordSet.getDate("KARIRYOIKINO_UKETUKEKIKAN_END"));    //仮領域番号発行締切日
//				追加 ここまで---------------------------------------------------------------
//              2006/07/10 追加　李義華　ここから----------------------------------------------------
//              理由　領域代表者確定締切日の追加のため
                result.setRyoikiEndDate(recordSet.getDate("RYOIKI_KAKUTEIKIKAN_END"));                //領域代表者確定締切日
//              追加 ここまで---------------------------------------------------------------                
				result.setKokaiFlg(recordSet.getString("KOKAI_FLG"));						//公開フラグ
				result.setKessaiNo(recordSet.getString("KESSAI_NO"));						//公開決済番号	
				result.setKokaiID(recordSet.getString("KOKAI_ID"));							//公開確定者ID
				result.setHokanDate(recordSet.getDate("HOKAN_DATE"));						//データ保管日
				result.setYukoDate(recordSet.getDate("YUKO_DATE"));							//保管有効期限
				result.setBiko(recordSet.getString("BIKO"));								//備考			
				result.setDelFlg(recordSet.getString("DEL_FLG"));							//削除フラグ
				
//				 2006/10/24 易旭 追加 ここから
				result.setRigaiEndDate(recordSet.getDate("RIGAI_KIKAN_END"));				//利害関係入力締切日
//				 2006/10/24 易旭 追加 ここまで
				
				//2006.06.08 iso 審査担当事業一覧での事業名表示方式修正
				result.setJigyoCd(recordSet.getString("JIGYO_ID").substring(2, 7));			//事業ID
				
				return result;
			} else {
				throw new NoDataFoundException(
					"事業管理情報テーブルに該当するデータが見つかりません。検索キー：事業ID'"
						+ primaryKeys.getJigyoId()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("事業管理情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	
	
	/**
	 * 事業管理情報を返す。
	 * 事業コード、年度、回数を元に事業管理情報を返す。
	 * @param connection
	 * @param jigyoCd
	 * @param nendo
	 * @param kaisu
	 * @return
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 */
	public JigyoKanriInfo selectJigyoKanriInfo(
            Connection connection,
            String     jigyoCd,
            String     nendo,
            String     kaisu)
		    throws DataAccessException, NoDataFoundException {

		//和暦年度を西暦年度（下２ケタ）に変換する
		nendo = DateUtil.changeWareki2Seireki(nendo);
			
		//----------事業IDを作成
		String jigyoId = nendo + jigyoCd + kaisu;
		JigyoKanriPk primaryKeys = new JigyoKanriPk(jigyoId);
		
		return selectJigyoKanriInfo(connection, primaryKeys);
		
	}

	/**
	 * 事業管理情報の数を取得する。削除フラグに関係なく検索する。
	 * @param connection			コネクション
	 * @param countInfo			    主キー情報
	 * @return						事業管理情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 */
	public int countJigyoKanriInfo(
		Connection connection,
		JigyoKanriInfo countInfo)
		throws DataAccessException {
		String query =
			"SELECT COUNT(*)"
				+ " FROM JIGYOKANRI"+dbLink
				+ " WHERE JIGYO_NAME = ?"	//事業名
				+ " AND NENDO = ?"			//年度				
				+ " AND KAISU = ?";			//回数
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,countInfo.getJigyoName());
			DatabaseUtil.setParameter(preparedStatement,i++,countInfo.getNendo());
			DatabaseUtil.setParameter(preparedStatement,i++,countInfo.getKaisu());
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("事業管理情報テーブル検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * 事業管理情報を登録する。
	 * @param connection				コネクション
	 * @param addInfo					登録する事業管理情報
	 * @throws DataAccessException		登録中に例外が発生した場合。	
	 * @throws DuplicateKeyException	キーに一致するデータが既に存在する場合。
	 */
	public void insertJigyoKanriInfo(
		Connection connection,
		JigyoKanriInfo addInfo)
		throws DataAccessException, DuplicateKeyException {
			
		//重複チェック
		try {
			selectJigyoKanriInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'は既に登録されています。");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO JIGYOKANRI"+dbLink+" A "
				+ "("
				+ " A.JIGYO_ID"				//事業ID
				+ ",A.NENDO"				//年度
				+ ",A.KAISU"				//回数
				+ ",A.JIGYO_NAME"			//事業名
				+ ",A.JIGYO_KUBUN"			//事業区分
				+ ",A.SHINSA_KUBUN"			//審査区分
				+ ",A.TANTOKA_NAME"			//業務担当課
				+ ",A.TANTOKAKARI"			//業務担当係名
				+ ",A.TOIAWASE_NAME"		//問い合わせ先担当者名
				+ ",A.TOIAWASE_TEL"			//問い合わせ先電話番号
				+ ",A.TOIAWASE_EMAIL"		//問い合わせ先E-mail
				+ ",A.UKETUKEKIKAN_START"	//学振受付期間（開始）
				+ ",A.UKETUKEKIKAN_END"		//学振受付期間（終了）
                //追加 ここまで---------------------------------------------------------------
                // 2006/06/14 追加 ここから 劉洋----------------------------------
                + ",A.KARIRYOIKINO_UKETUKEKIKAN_END " //仮領域番号発行締切日
                + ",A.RYOIKI_KAKUTEIKIKAN_END " //領域代表者確定締切日
                //2006/06/14 劉洋 追加 ここまで-----------------------------------
				//2005/04/21 追加 ここから----------------------------------------------------
				//理由 URLの追加のため
				+ ",A.URL_TITLE"			//URL(タイトル)
				+ ",A.URL_ADDRESS"			//URL(アドレス)
				+ ",A.DL_URL"				//ダウンロードURL
				//2005/04/21 追加 ここまで---------------------------------------------------------------
				//2006/02/10 追加 ここから----------------------------------------------------
				+ ",A.MEIBO_DATE"			//研究者名簿登録最終締切日
				+ ",A.SHINSAKIGEN"			//審査期限
				+ ",A.TENPU_NAME"			//添付文書名
				+ ",A.TENPU_WIN"			//添付ファイル格納フォルダ（Win）
				+ ",A.TENPU_MAC"			//添付ファイル格納フォルダ（Mac）
//2007/02/03 苗　追加ここから
                + ",A.PAGE_FROM"            //応募内容ファイルページ数(下限)
                + ",A.PAGE_TO"              //応募内容ファイルページ数(上限)
//2007/02/03　苗　追加ここまで                
				+ ",A.HYOKA_FILE_FLG"		//評価用ファイル有無
				+ ",A.HYOKA_FILE"			//評価用ファイル格納フォルダ
				+ ",A.KOKAI_FLG"			//公開フラグ
				+ ",A.KESSAI_NO"			//公開決裁番号
				+ ",A.KOKAI_ID"				//公開確定者ID
				+ ",A.HOKAN_DATE"			//データ保管日
				+ ",A.YUKO_DATE"			//保管有効期限
				+ ",A.BIKO"					//備考
				+ ",A.DEL_FLG"				//削除フラグ
//				 2006/10/24 易旭 追加 ここから
				+ ",A.RIGAI_KIKAN_END"		//利害関係入力締切日
//				 2006/10/24 易旭 追加 ここまで
				+ ") "
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoId());			//事業ID
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNendo());			//年度
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKaisu());			//回数
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoName());		//事業名
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoKubun());		//事業区分
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsaKubun());		//審査区分
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantokaName());		//業務担当課
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoKakari());		//業務担当係名
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getToiawaseName());		//問い合わせ先担当者名
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getToiawaseTel());		//問い合わせ先電話番号
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getToiawaseEmail());	//問い合わせ先E-mail
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUketukekikanStart());//学振受付期間（開始）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUketukekikanEnd());	//学振受付期間（終了）
            // 2006/06/14 劉洋 追加 ここから ----------------------------------------------------
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKariryoikiNoEndDate()); //仮領域番号発行締切日
            // 2006/06/14 劉洋 追加 ここまで-----------------------------------------------------
//          2006/07/10 追加 ここから 李義華----------------------------------------------------
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRyoikiEndDate());        //領域代表者確定締切日
//          2006/07/10 追加 ここまで 李義華---------------------------------------------------------------
            //2005/04/21 追加 ここから----------------------------------------------------
			//理由 URLの追加のため
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUrlTitle());			//URL(タイトル)
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUrlAddress());		//URL(アドレス)
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getDlUrl());			//ダウンロードURL
			//追加 ここまで---------------------------------------------------------------	
			//2006/02/10 追加 ここから----------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getMeiboDate());		//研究者名簿登録最終締切日
			//追加 ここまで---------------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsaKigen());		//審査期限
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTenpuName());		//添付文書名
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTenpuWin());			//添付ファイル格納フォルダ（Win）
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTenpuMac());			//添付ファイル格納フォルダ（Mac）
//2007/02/03 苗　追加ここから
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getPageFrom());         //応募内容ファイルページ数(下限)
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getPageTo());           //応募内容ファイルページ数(上限)
//2007/02/03　苗　追加ここまで            
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHyokaFileFlg());		//評価用ファイル有無
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHyokaFile());		//評価用ファイル格納フォルダ
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKokaiFlg());			//公開フラグ
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKessaiNo());			//公開決裁番号
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKokaiID());			//公開確定者ID
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHokanDate());		//データ保管日
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYukoDate());			//保管有効期限
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());				//備考			
			DatabaseUtil.setParameter(preparedStatement, i++, 0);							//削除フラグ
//			 2006/10/24 易旭 追加 ここから
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRigaiEndDate());		//利害関係入力締切日						//削除フラグ
//			 2006/10/24 易旭 追加 ここまで
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("事業管理情報登録中に例外が発生しました。 ", ex);
			throw new DataAccessException("事業管理情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * 事業管理情報を更新する。
	 * @param connection                コネクション
	 * @param updateInfo                更新する事業管理情報
	 * @throws DataAccessException      更新中に例外が発生した場合
	 * @throws NoDataFoundException     対象データが見つからない場合
	 */
	public void updateJigyoKanriInfo(
		Connection connection,
		JigyoKanriInfo updateInfo)
		throws DataAccessException, NoDataFoundException {

		//検索
		selectJigyoKanriInfo(connection, updateInfo);

		String query =
			"UPDATE JIGYOKANRI"+dbLink+" A "
				+ " SET"	
				+ " A.JIGYO_ID = ? "			//事業ID
				+ ",A.NENDO = ? "				//年度
				+ ",A.KAISU = ? "				//回数
				+ ",A.JIGYO_NAME = ? "			//事業名
				+ ",A.JIGYO_KUBUN = ? "			//事業区分
				+ ",A.SHINSA_KUBUN = ? "		//審査区分
				+ ",A.TANTOKA_NAME = ? "		//業務担当課
				+ ",A.TANTOKAKARI = ? "			//業務担当係名
				+ ",A.TOIAWASE_NAME = ? "		//問い合わせ先担当者名
				+ ",A.TOIAWASE_TEL = ? "		//問い合わせ先電話番号
				+ ",A.TOIAWASE_EMAIL = ? "		//問い合わせ先E-mail
				+ ",A.UKETUKEKIKAN_START = ? "	//学振受付期間（開始）
				+ ",A.UKETUKEKIKAN_END = ? "	//学振受付期間（終了）
                // 2006/06/14 劉洋 追加 ここから---------------------------------------------------
                + ",A.KARIRYOIKINO_UKETUKEKIKAN_END = ?" //仮領域番号発行締切日
                + ",A.RYOIKI_KAKUTEIKIKAN_END = ?" //仮領域番号発行締切日
                // 2006/06/14 劉洋 追加 ここまで----------------------------------------------------
				//2005/04/24 追加 ここから----------------------------------------------------
				//理由 URLの追加のため
				+ ",A.URL_TITLE = ?"			//URL(タイトル)
				+ ",A.URL_ADDRESS = ?"			//URL(アドレス)
				+ ",A.DL_URL = ?"				//ダウンロードURL
				//追加 ここまで---------------------------------------------------------------
				//2006/02/10 追加 ここから----------------------------------------------------
				+ ",A.MEIBO_DATE=?"			    //研究者名簿登録最終締切日
				//追加 ここまで---------------------------------------------------------------
				
				+ ",A.SHINSAKIGEN = ? "			//審査期限
				+ ",A.TENPU_NAME = ? "			//添付文書名
				+ ",A.TENPU_WIN = ? "			//添付ファイル格納フォルダ（Win）
				+ ",A.TENPU_MAC = ? "			//添付ファイル格納フォルダ（Mac）
//2007/02/03 苗　追加ここから
                + ",A.PAGE_FROM = ?"            //応募内容ファイルページ数(下限)
                + ",A.PAGE_TO = ?"              //応募内容ファイルページ数(上限)
//2007/02/03 苗　追加ここまで                
				+ ",A.HYOKA_FILE_FLG = ? "		//評価用ファイル有無
				+ ",A.HYOKA_FILE = ? "			//評価用ファイル格納フォルダ
				+ ",A.KOKAI_FLG = ? "			//公開フラグ
				+ ",A.KESSAI_NO = ? "			//公開決裁番号
				+ ",A.KOKAI_ID = ? "			//公開確定者ID
				+ ",A.HOKAN_DATE = ? "			//データ保管日
				+ ",A.YUKO_DATE = ? "			//保管有効期限
				+ ",A.BIKO = ? "				//備考
				+ ",A.DEL_FLG = ? "				//削除フラグ
//				 2006/10/24 易旭 追加 ここから
				+ ",A.RIGAI_KIKAN_END = ? "		//利害関係入力締切日
//				 2006/10/24 易旭 追加 ここまで
				+ " WHERE"
				+ " JIGYO_ID = ?";//事業ID

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoId());			//事業ID
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNendo());				//年度
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKaisu());				//回数
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoName());			//事業名
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());		//事業区分
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsaKubun());		//審査区分
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantokaName());		//業務担当課
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoKakari());		//業務担当係名
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getToiawaseName());		//問い合わせ先担当者名
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getToiawaseTel());		//問い合わせ先電話番号
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getToiawaseEmail());		//問い合わせ先E-mail
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getUketukekikanStart());//学振受付期間（開始）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getUketukekikanEnd());	//学振受付期間（終了）
            // 2006/06/14 劉洋 追加 ここから --------------------------------------------------------------
            DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKariryoikiNoEndDate()); //仮領域番号発行締切日
            DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getRyoikiEndDate()); //領域代表者確定締切日
            // 2006/06/14 劉洋 追加 ここまで---------------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getUrlTitle());			//URLタイトル
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getUrlAddress());		//URLアドレス
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDlUrl());				//ダウンロードURL		
			//2006/02/10 追加 ここから----------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getMeiboDate());		    //研究者名簿登録最終締切日
			//追加 ここまで---------------------------------------------------------------	
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsaKigen());		//審査期限
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTenpuName());			//添付文書名
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTenpuWin());			//添付ファイル格納フォルダ（Win）
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTenpuMac());			//添付ファイル格納フォルダ（Mac）
//2007/02/03 苗　追加ここから
            DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getPageFrom());          //応募内容ファイルページ数(下限)
            DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getPageTo());            //応募内容ファイルページ数(上限)
//2007/02/03　苗　追加ここまで            
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHyokaFileFlg());		//評価用ファイル有無
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHyokaFile());			//評価用ファイル格納フォルダ
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKokaiFlg());			//公開フラグ
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKessaiNo());			//公開決裁番号
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKokaiID());			//公開確定者ID
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHokanDate());			//データ保管日
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getYukoDate());			//保管有効期限
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());				//備考			
			DatabaseUtil.setParameter(preparedStatement, i++, 0);								//削除フラグ
//			 2006/10/24 易旭 追加 ここから
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getRigaiEndDate());		//利害関係入力締切日
//			 2006/10/24 易旭 追加 ここまで
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoId());			//事業ID
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("事業管理情報更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * 事業管理情報の公開確定情報を更新する。
	 * 公開確定情報を更新する場合は、パスワードのチェックを行う。
	 * @param connection          コネクション
     * @param jigyoPks            事業管理テーブル主キーリスト
     * @param kesssaiNo           決済番号
	 * @param gyomutantoId        業務担当ＩＤ
     * @throws DataAccessException
     * @throws NoDataFoundException
	 */
	public void updateKokaiKakutei(Connection connection, JigyoKanriPk[] jigyoPks,
            String kesssaiNo, String gyomutantoId) 
		throws DataAccessException, NoDataFoundException {

		String query =
			"UPDATE JIGYOKANRI"+dbLink
				+ " SET"	
				+ " KOKAI_FLG = ?"//公開フラグ
				+ ",KESSAI_NO = ?"//公開決裁番号
				+ ",KOKAI_ID = ?";//公開確定者ID		
				
		StringBuffer buffer = new StringBuffer(query);
		
		//更新キーをセット						
		String aSeparate = "";
		buffer.append(" WHERE JIGYO_ID IN(");	//事業ID
		for (int i = 0; i < jigyoPks.length; i++) {
			buffer.append(aSeparate);
			buffer.append("?");
			aSeparate = ",";
		}
		buffer.append(")");


		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(buffer.toString());
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++, "1");							//公開フラグ
			DatabaseUtil.setParameter(preparedStatement,i++, kesssaiNo);					//公開決裁番号		
			DatabaseUtil.setParameter(preparedStatement,i++, gyomutantoId);					//公開確定者ID	
			for (int n = 0; n < jigyoPks.length; n++) {					
				DatabaseUtil.setParameter(preparedStatement,i++, jigyoPks[n].getJigyoId());	//事業ID
			}
			int count = preparedStatement.executeUpdate();
			
			if(log.isDebugEnabled()){
				log.debug(count + "件の公開確定情報の更新に成功しました。");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("事業管理情報（公開確定）更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * 事業管理情報を削除する。(削除フラグ) 
	 * @param connection			コネクション
	 * @param deleteInfo            削除する所属機関主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteFlgJigyoKanriInfo(
		Connection connection,
		JigyoKanriPk deleteInfo)
		throws DataAccessException, NoDataFoundException {

		//検索
		selectJigyoKanriInfo(connection, deleteInfo);
		
		String query =
			"UPDATE JIGYOKANRI"+dbLink
				+ " SET"
				+ " DEL_FLG = 1"//削除フラグ				
				+ " WHERE"
				+ " JIGYO_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getJigyoId());//事業ID
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {

			throw new DataAccessException("事業管理情報削除中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * 事業管理情報を削除する。(物理削除) 
	 * @param connection			コネクション
	 * @param deleteInfo			削除する所属機関主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public void deleteJigyoKanriInfo(
		Connection connection,
		JigyoKanriPk deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		deleteJigyoKanriInfo(connection, deleteInfo, true);
	}
	
	
	
	/**
	 * 事業管理情報を削除する。(物理削除) 
	 * 該当データが存在しなかった場合は何も処理しない。
	 * @param connection			コネクション
	 * @param deleteInfo			削除する所属機関主キー情報
	 * @throws DataAccessException	削除中に例外が発生した場合
	 */
	public void deleteJigyoKanriInfoNoCheck(
		Connection connection,
		JigyoKanriPk deleteInfo)
		throws DataAccessException
	{
		try{
			deleteJigyoKanriInfo(connection, deleteInfo, false);
		}catch(NoDataFoundException e){
			//第3引数をfalseにした場合、本来この例外が発生することは無い
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 事業管理情報を削除する。(物理削除) 
	 * @param connection			コネクション
	 * @param deleteInfo			削除する所属機関主キー情報
	 * @param existsCheck          レコードの存在チェックをする場合[true]
	 * @throws DataAccessException	削除中に例外が発生した場合
	 * @throws NoDataFoundException	対象データが見つからない場合(existsCheckがtrueのときのみ）
	 */
	private void deleteJigyoKanriInfo(
		Connection connection,
		JigyoKanriPk deleteInfo,
		boolean existsCheck)
		throws DataAccessException, NoDataFoundException
	{
		//検索（削除対象データが存在しなかった場合は例外発生）
		if(existsCheck){
			selectJigyoKanriInfo(connection, deleteInfo);
		}
		
		String query =
		"DELETE FROM JIGYOKANRI"+dbLink
			+ " WHERE"
			+ " JIGYO_ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement,index++,deleteInfo.getJigyoId());	//検索条件（事業ID）
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("事業管理情報削除中（物理削除）に例外が発生しました。 ", ex);
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
			"INSERT INTO JIGYOKANRI"+dbLink
				+ " SELECT * FROM JIGYOKANRI WHERE JIGYO_ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, jigyoId);		//検索条件（事業ID）
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("事業管理情報保管中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * 事業情報（一部）の一覧を取得する。（削除されていないもののみ）
	 * @param	connection			コネクション
	 * @return						事業情報（一部）
	 * @throws ApplicationException
	 */
	public static List selectJigyoKanriList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " JIGYO_ID"			
			+ ",NENDO"				
			+ ",KAISU"				
			+ ",JIGYO_NAME"			
			+ ",JIGYO_KUBUN"		
			+ ",SHINSA_KUBUN"		
			+ " FROM JIGYOKANRI A"
			+ " WHERE A.DEL_FLG = '0'"
			+ " ORDER BY A.JIGYO_ID";								
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
				"事業情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		}
	}		
	
	
	
	/**
	 * 当該事業区分の事業情報（一部）の一覧を取得する。（削除されていないもののみ）
	 * @param	connection			コネクション
	 * @param  jigyoKubun          事業区分
	 * @return						事業情報（一部）
	 * @throws ApplicationException
	 */
	public static List selectJigyoKanriList(Connection connection, String jigyoKubun)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " JIGYO_ID"			
			+ ",NENDO"				
			+ ",KAISU"				
			+ ",JIGYO_NAME"			
			+ ",JIGYO_KUBUN"		
			+ ",SHINSA_KUBUN"		
			+ " FROM JIGYOKANRI A"
			+ " WHERE A.DEL_FLG = '0'"
			+ " AND A.JIGYO_KUBUN = ?"
			+ " ORDER BY A.JIGYO_ID";								
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
				"事業情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		}
	}		
	
	/**
	 * 承認確認メール送信事業管理情報を取得する。学振受付締切日のn日前の事業情報のみ
	 * 削除フラグが「0」の場合のみ検索する。
	 * @param connection			コネクション
	 * @param days					締切日までの日数
	 * @return						事業管理情報
	 * @throws DataAccessException	データ取得中に例外が発生した場合。
	 * @throws NoDataFoundException	対象データが見つからない場合
	 */
	public List selectShoninTsuchiJigyoInfo(Connection connection, int days)
		throws DataAccessException, NoDataFoundException
	{
		
		String query =
					  "SELECT DISTINCT A.JIGYO_ID, A.NENDO, A.KAISU, A.JIGYO_NAME,"
				          + " B.SHOZOKU_CD, C.TANTO_EMAIL"
					+ "  FROM JIGYOKANRI A, SHINSEIDATAKANRI B, SHOZOKUTANTOINFO C, CHCKLISTINFO D"
					+ " WHERE A.UKETUKEKIKAN_END = TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD') + " + EscapeUtil.toSqlString(Integer.toString(days)) 
					//+ "   AND A.JIGYO_KUBUN IN ('1','2','3')"
					//2005.11.16 iso 基盤・特定でチェックリスト確定には出さない。
//					+ "   AND B.JOKYO_ID IN ('01','02','03')"
					+ "   AND ("
//2006/05/09 追加ここから                    
//					+ "    (B.JOKYO_ID IN ('01','02','03') AND B.JIGYO_KUBUN IN ('1','2','3'))"
                    + "    (B.JOKYO_ID IN ('01','02','03') AND"
//                    + "     B.JIGYO_KUBUN IN ("
//                    + StringUtil.changeIterator2CSV(JigyoKubunFilter.getShoninTaishoJigyoKubun().iterator(),true)
//  2006/06/29 ZJP 追加ここから 
                    + " SUBSTR(B.JIGYO_ID,3,5) IN ("
                    + StringUtil.changeIterator2CSV(JigyoKubunFilter.getShoninTaishoJigyoCd().iterator(),true)
                    + "))"
// 　2006/06/29 ZJP 追加ここまで 
//苗　追加ここまで                    
					//2005.12.16 iso 学創でメールが送信されないバグを修正
					//学創の場合は、チェックリスト条件がいらない。
//					+ "    OR (D.JOKYO_ID = '03' AND B.JIGYO_KUBUN IN ('4','5'))"
//					+ "   )"
//					+ "   AND (B.JIGYO_ID = D.JIGYO_ID AND B.SHOZOKU_CD = D.SHOZOKU_CD)"
//2006/05/09 追加ここから                      
//					+ "    OR (D.JOKYO_ID = '03' AND B.JIGYO_KUBUN IN ('4','5') AND (B.JIGYO_ID = D.JIGYO_ID AND B.SHOZOKU_CD = D.SHOZOKU_CD))"
                    + "    OR (D.JOKYO_ID = '03' " 
//                    + "    AND B.JIGYO_KUBUN IN ("
//                    +      StringUtil.changeIterator2CSV(JigyoKubunFilter.getCheckListTaishoJigyoKubun().iterator(),true)
//                    + "    )"
//　2006/06/29 ZJP 追加ここから 
                    + "    AND SUBSTR(B.JIGYO_ID,3,5) IN ("
                    +      StringUtil.changeIterator2CSV(JigyoKubunFilter.getCheckListTaishoJigyoCd().iterator(),true)
                    + "    )"
// 2006/06/29 ZJP 追加ここまで                    
                    + "    AND (B.JIGYO_ID = D.JIGYO_ID AND B.SHOZOKU_CD = D.SHOZOKU_CD))"
//苗　追加ここまで                       
					+ "   )"
					
					+ "   AND A.DEL_FLG = 0"
					+ "   AND A.JIGYO_ID = B.JIGYO_ID"
					+ "   AND A.NENDO = B.NENDO"
					+ "   AND A.KAISU = B.KAISU"
					+ "   AND B.DEL_FLG = 0"
					
					//2006.05.18 iso 削除された所属研究機関担当者へメールが送信されるバグを修正
					+ "   AND C.DEL_FLG = 0"
					
					+ "   AND B.SHOZOKU_CD = C.SHOZOKU_CD"
					+ " ORDER BY SHOZOKU_CD, JIGYO_ID, NENDO, KAISU";

		if (log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		return SelectUtil.select(connection, query);

	}
	
//2006/04/26 追加ここから
    /**
     * 基盤などの事業情報の一覧を取得する。（削除されていないもののみかつ事業区分が4,6,7のもの）
     * @param   connection          コネクション
     * @return                      事業情報（基盤系の事業情報）
     * @throws ApplicationException
     */
    public static List selectKibanJigyoKubun(Connection connection)
        throws ApplicationException {

        //-----------------------
        // SQL文の作成
        //-----------------------
        String select =
            "SELECT"
            + " JIGYO_ID"           
            + ",NENDO"              
            + ",KAISU"              
            + ",JIGYO_NAME"         
            + ",JIGYO_KUBUN"        
            + ",SHINSA_KUBUN"       
            + " FROM JIGYOKANRI A"
            + " WHERE A.DEL_FLG = '0'"
            + " AND A.JIGYO_KUBUN IN(" 
            + IJigyoKubun.JIGYO_KUBUN_KIBAN
            + ","
            + IJigyoKubun.JIGYO_KUBUN_WAKATESTART
            + ","
            + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI
            + ")"
            + " ORDER BY A.JIGYO_ID";  
        
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
                "事業情報検索中にDBエラーが発生しました。",
                new ErrorInfo("errors.4004"),
                e);
        }
    }
//苗　追加ここまで  
    
//2006/06/20 lwj add begin
    /**
     * 事業管理情報を取得する。削除フラグが「0」の場合のみ検索する。
     * @param connection            コネクション
     * @param primaryKeys           主キー情報
     * @return                      事業管理情報
     * @throws DataAccessException  データ取得中に例外が発生した場合。
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public JigyoKanriInfo selectJigyoKanriInfos(
            Connection connection,
            JigyoKanriPk primaryKeys)
            throws DataAccessException, NoDataFoundException{
        
        String query =
            "SELECT "
                + " A.NENDO"                //年度
                + ",A.KAISU"                //回数
                + ",A.JIGYO_NAME"           //事業名
                + ",B.KIBOUBUMON_CD"        //審査希望部門（系等）コード
                + " FROM JIGYOKANRI A"
                + " INNER JOIN RYOIKIKEIKAKUSHOINFO B"
                + " ON A.JIGYO_ID = B.JIGYO_ID "
                + " WHERE A.JIGYO_ID = ?";
        
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try{
            JigyoKanriInfo result = new JigyoKanriInfo();
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, primaryKeys.getJigyoId());//事業ID
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()){
                result.setNendo(recordSet.getString("NENDO"));
                result.setKaisu(recordSet.getString("KAISU"));
                result.setJigyoName(recordSet.getString("JIGYO_NAME")); 
                result.setKiboubumonCd(recordSet.getString("KIBOUBUMON_CD"));
                
                return result;
            }else {
                throw new NoDataFoundException(
                        "該当するデータが見つかりません。検索キー：事業ID'"
                            + primaryKeys.getJigyoId()
                            + "'");
                }
        }catch (SQLException ex) {
            throw new DataAccessException("テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2006/06/20 lwj add end

//2007/5/25 by xiang start
    /**
     * 保管日付と保管期限を更新する
     * @param connection
     * @param jigyoPk　事業ID
     * @param yukoKigen 保管期限
     */
	public void updateHokanInfo(Connection connection, JigyoKanriPk jigyoPk, Date yukoKigen) 
		throws DataAccessException, NoDataFoundException {

		String query =
					"UPDATE JIGYOKANRI"+dbLink
						+ " SET"	
						+ " HOKAN_DATE = SYSDATE"	//保管日付
						+ ",YUKO_DATE = ?"			//保管期限
						+ " WHERE JIGYO_ID = ?"
						;
		
		PreparedStatement preparedStatement = null;
		try {
			//登録
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++, yukoKigen);			//保管期限	
			DatabaseUtil.setParameter(preparedStatement,i++, jigyoPk.getJigyoId());	//事業ID	

			int count = preparedStatement.executeUpdate();
			
			if(count != 1 && log.isDebugEnabled()){
				log.debug(count + "件の事業管理情報の更新に成功しました。");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("事業管理情報（保管日付）更新中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
//2007/5/25 by xiang end

}