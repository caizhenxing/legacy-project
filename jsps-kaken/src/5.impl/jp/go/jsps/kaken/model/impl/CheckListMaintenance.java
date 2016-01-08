/*
 * 作成日: 2005/03/31
 *
 */
package jp.go.jsps.kaken.model.impl;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.impl.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.status.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * チェックリスト情報管理クラス.<br><br>
 *
 * <b>概要:</b><br>
 * チェックリスト情報を管理する。<br><br>
 *
 * 使用テーブル<br>
 * <table>
 * <tr><td>チェックリストテーブル</td><td>：チェックリスト情報を管理</td></tr>
 * </table>
 *
 */
public class CheckListMaintenance implements ICheckListMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(CheckListMaintenance.class);
    
// 2007/02/06 張志男　追加ここから
    /** ログ（ステイタス）*/
    protected static Log statusLog = LogFactory.getLog("status");
// 2007/02/06　張志男　追加ここまで

    //ADD　START 2007/07/20 BIS 趙一非
    //制度改正カスタマイズ
    //一括受理ログ出力
    protected static Log juriLog = LogFactory.getLog("juri");
    //ADD　END 2007/07/20 BIS 趙一非
    
	/** メールサーバアドレス */
	private static final String SMTP_SERVER_ADDRESS =
								ApplicationSettings.getString(ISettingKeys.SMTP_SERVER_ADDRESS);

	/** 差出人（統一して１つ） */
	private static final String FROM_ADDRESS =
								ApplicationSettings.getString(ISettingKeys.FROM_ADDRESS);

	/** メール内容（申請者が申請書確認を完了したとき）「件名」 */
	private static final String SUBJECT_CHECKLIST_KAKUTEI =
								ApplicationSettings.getString(ISettingKeys.SUBJECT_CHECKLIST_KAKUTEI);

	/** メール内容（申請者が申請書確認を完了したとき）「本文」 */
	private static final String CONTENT_CHECKLIST_KAKUTEI =
								ApplicationSettings.getString(ISettingKeys.CONTENT_CHECKLIST_KAKUTEI);

	/**
	 * 応募書類の提出書ファイル格納フォルダ .<br /><br />
	 *
	 * 具体的な値は、"<b>${shinsei_path}/work/oubo/</b>"<br />
	 */
	private static String OUBO_WORK_FOLDER = ApplicationSettings.getString(ISettingKeys.OUBO_WORK_FOLDER);

	/**
	 * 応募書類の提出書Wordファイル格納フォルダ.<br /><br />
	 *
	 * 具体的な値は、"<b>${shinsei_path}/settings/oubo/</b>"<br />
	 */
	private static String OUBO_FORMAT_PATH = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_PATH);

	/**
	 * 応募書類の提出書Wordファイル名.<br /><br />
	 *
	 * 具体的な値(基盤)は、"<b>kiban.doc</b>"<br />
	 * 具体的な値(特定領域)は、"<b>tokutei.doc</b>"<br />
	 * 具体的な値(若手スタートアップ)は、"<b>wakate.doc</b>"<br />
	 * 具体的な値(特別研究促進費)は、"<b>shokushinhi.doc</b>"<br />
	 */
	private static String OUBO_FORMAT_FILE_NAME_KIBAN = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_FILE_NAME_KIBAN);
	private static String OUBO_FORMAT_FILE_NAME_TOKUTEI = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_FILE_NAME_TOKUTEI);
	private static String OUBO_FORMAT_FILE_NAME_WAKATESTART = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_FILE_NAME_WAKATESTART);
	private static String OUBO_FORMAT_FILE_NAME_SHOKUSHINHI = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_FILE_NAME_SHOKUSHINHI);

	/** 申請受理フラグ：受理 */
	public static final String FLAG_JURI_KEKKA_JURI 	 = "0";

	/** 申請受理フラグ：不受理 */
	public static final String FLAG_JURI_KEKKA_FUJURI	 = "1";

	/** 申請受理フラグ：修正依頼 */
	public static final String FLAG_JURI_KEKKA_SHUSEIIRAI	 = "2";

	/** 申請書削除フラグ（通常） */
	public static final String FLAG_APPLICATION_NOT_DELETE = "0";

	/** 申請書削除フラグ（削除済み） */
	public static final String FLAG_APPLICATION_DELETE	   = "1";

	/** 重複申請チェックフラグ */
	protected static final boolean CHECK_DUPLICACATION_FLAG =
										ApplicationSettings.getBoolean(ISettingKeys.CHECK_DUPLICACATION_FLAG);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public CheckListMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement ICheckListMaintenance
	//---------------------------------------------------------------------

	/**
	 * チェックリスト一覧表示用のデータを取得する.<br/><br/>
	 *
	 * チェックリストテーブル、申請者情報テーブル、業者担当情報テーブルから、
	 * 引数で指定されるレコード情報を取得する。<br/><br/>
	 *
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		CASE
	 * 		  WHEN sysdate < A.UKETUKEKIKAN_START THEN 'FALSE'
	 * 		  WHEN sysdate > A.UKETUKEKIKAN_END THEN 'FALSE'
	 * 		  ELSE 'TRUE'
	 * 		END DATE_FLAG,									   -- 受付期間判別フラグ
	 * 		A.NENDO,										   -- 年度
	 * 		A.KAISU,										   -- 回数
	 * 		MASTER_JIGYO.JIGYO_NAME JIGYO_NAME, 			   -- 事業名
	 * 		A.UKETUKEKIKAN_END, 							   -- 受付期間終了日
	 * 		COUNT(*) COUNT, 								   -- 件数
	 * 		C.KAKUTEI_DATE, 								   -- 確定日
	 * 		C.EDITION,										   -- 版
	 * 		A.JIGYO_ID, 									   -- 事業ID
	 * 		C.JOKYO_ID, 									   -- 状況ID
	 * 		B.SHOZOKU_CD,									   -- 所属コード
	 * 		MASTER_KIKAN.SHOZOKU_NAME_KANJI SHOZOKU_NAME,	   -- 所属名
	 * 		B.JYURI_DATE,									   -- 受理日
	 * 		C.CANCEL_FLG									   -- 受理解除フラグ
	 * FROM
	 * 		SHINSEIDATAKANRI B								   -- 申請データ管理テーブル
	 * INNER JOIN
	 * 		CHCKLISTINFO C									   -- チェックリスト情報テーブル
	 * INNER JOIN
	 * 		JIGYOKANRI A									   -- 事業管理テーブル
	 * ON
	 * 		A.JIGYO_ID = C.JIGYO_ID
	 * INNER JOIN
	 * 		MASTER_JIGYO MASTER_JIGYO						   -- 事業マスタテーブル
	 * ON
	 * 		MASTER_JIGYO.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5)
	 * ON
	 * 		C.JIGYO_ID = B.JIGYO_ID
	 * 		AND C.SHOZOKU_CD = B.SHOZOKU_CD
	 * 		AND C.JOKYO_ID = B.JOKYO_ID
	 * INNER JOIN
	 * 		MASTER_KIKAN MASTER_KIKAN						   -- 機関マスタテーブル
	 * ON
	 * 		MASTER_KIKAN.SHOZOKU_CD = B.SHOZOKU_CD
	 * ------------------------ 部局担当者で担当部局を持つ場合に追加---------------------------
	 * INNER JOIN
	 * 		TANTOBUKYOKUKANRI TANTO 						   -- 担当部局管理テーブル
	 * ON
	 * 		B.SHOZOKU_CD = TANTO.SHOZOKU_CD
	 * 		AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD
	 * 		AND TANTO.BUKYOKUTANTO_ID = ?
	 * -------------------------------------------------------------------------------------
	 * -------------業務担当者の場合に権限の無い情報を表示しないようにするため追加---------------
	 * INNER JOIN
	 * 		ACCESSKANRI AC									   -- アクセス管理テーブル
	 * ON
	 * 		AC.JIGYO_CD = SUBSTR(B.JIGYO_ID,3,5)
	 * 		AND AC.GYOMUTANTO_ID = ?
	 * -------------------------------------------------------------------------------------
	 * WHERE
	 * 		B.DEL_FLG = 0
	 * 		AND A.DEL_FLG = 0
	 * 		AND B.JIGYO_KUBUN = 4
	 * <b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 * GROUP BY
	 * 		A.NENDO,
	 * 		A.KAISU,
	 * 		MASTER_JIGYO.JIGYO_NAME,
	 * 		A.UKETUKEKIKAN_START,
	 * 		A.UKETUKEKIKAN_END,
	 * 		C.KAKUTEI_DATE,
	 * 		C.EDITION,
	 * 		A.JIGYO_ID,
	 * 		C.JOKYO_ID,
	 * 		B.SHOZOKU_CD,
	 * 		MASTER_KIKAN.SHOZOKU_NAME_KANJI,
	 * 		B.JYURI_DATE,
	 * 		C.CANCEL_FLG
	 * ORDER BY
	 * 		A.JIGYO_ID,
	 * 		B.SHOZOKU_CD
	 * </pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>TANTO.BUKYOKUTANTO_ID</td><td>第一引数userInfoの部局担当者情報bukyokutantoInfoの変数bukyokutantoId</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>AC.GYOMUTANTO_ID</td><td>第一引数userInfoの業務担当者情報gyomutantoInfoの変数gyomutantoId</td></tr>
	 * </table><br/><br/>
	 *
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>状況ID</td><td>jokyoList</td><td>AND C.JOKYO_ID IN(状況IDの配列)</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>所属CD</td><td>shozokuCd</td><td>AND C.SHOZOKU_CD = '所属CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>事業CD</td><td>jigyoCd</td><td>AND SUBSTR(A.JIGYO_ID, 3, 5) = '事業CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>回数</td><td>kaisu</td><td>AND A.KAISU = '回数'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>所属機関名</td><td>shozokuName</td><td>AND B.SHOZOKU_NAME like '%所属機関名%'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>受理状況(全て)</td><td>cancellationFlag="0"</td><td>AND (C.CANCEL_FLG = '1' OR C.JOKYO_ID <> '03') </td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>受理状況(確定解除)</td><td>cancellationFlag="1"</td><td>AND C.CANCEL_FLG = '1'</td></tr>
	 * </table><br />
	 *
	 * @param userInfo		UserInfo
	 * @param searchInfo 	CheckListSearchInfo
	 * @return page	ページ情報
	 * @see jp.go.jsps.kaken.model.ICheckListMaintenance#selectCheckList(UserInfo userInfo, CheckListSearchInfo info)
	 */
	public Page selectCheckList(
		UserInfo userInfo,
		CheckListSearchInfo searchInfo)
		throws NoDataFoundException,ApplicationException {

		// 特定領域研究の受理にも使用されていたため変更
		// ※特定領域研究に未対応であった
		// 特定領域研究の受理登録から呼び出される時のみ事業CDでなく事業IDを抽出条件にする

		//-----------------------
		// ページ情報取得
		//-----------------------
		Connection connection = null;
		try {
			return selectCheckList(userInfo, searchInfo, false);
		}
		catch (NoDataFoundException e) {
			throw new NoDataFoundException("該当する情報が存在しませんでした。",
                    new ErrorInfo("errors.4004"),e);
        }
		catch (ApplicationException e) {
			throw new ApplicationException("データ検索中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"),e);
        }
		finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

    /**
     * チェックリスト一覧表示用のデータを取得する
     * @param userInfo ユーザ情報
     * @param searchInfo 検索条件情報
     * @param blnJuriflg 
     * @return Page ページ情報
     * @throws ApplicationException
     */
	public Page selectCheckList(
			UserInfo userInfo,
			CheckListSearchInfo searchInfo,
			boolean blnJuriflg)
			throws ApplicationException {

        //-----------------------
        // 検索条件よりSQL文の作成
        //-----------------------
        String select =
			//2005.12.09 iso 締め切り当日に確定解除ボタンが表示されないバグを修正
//			  "SELECT CASE WHEN TRUNC(sysdate < A.UKETUKEKIKAN_START THEN 'FALSE' "
//                 + "WHEN TRUNC(sysdate > A.UKETUKEKIKAN_END THEN 'FALSE' "
            "SELECT CASE WHEN TRUNC(sysdate, 'DD') < A.UKETUKEKIKAN_START THEN 'FALSE' "
                + "WHEN TRUNC(sysdate, 'DD') > A.UKETUKEKIKAN_END THEN 'FALSE' "
                + "ELSE 'TRUE' "
                + "END DATE_FLAG,"
                + " A.NENDO, "
                + "A.KAISU, "
                + "MASTER_JIGYO.JIGYO_NAME JIGYO_NAME, "
                + "A.UKETUKEKIKAN_END, "
                + "COUNT(*) COUNT, "
                + "C.KAKUTEI_DATE, "
                + "C.EDITION, "
                + "A.JIGYO_ID, "
                + "C.JOKYO_ID, "
                + "B.SHOZOKU_CD, "
//add start dyh 2006/3/7 原因：受理登録画面で初期値を表示しない
                + "B.JURI_BIKO,"//受理備考
//add end dyh 2006/3/7
                ;
        // 2005.08.10 iso 機関マスタに存在しないサンプル期間「99999」対応
        if (userInfo.getRole().equals(UserRole.GYOMUTANTO)) {
            select += "MASTER_KIKAN.SHOZOKU_NAME_KANJI SHOZOKU_NAME,";
        }
        select += "B.JYURI_DATE, "
                + "C.CANCEL_FLG "
                + "FROM SHINSEIDATAKANRI B "
                + "INNER JOIN  CHCKLISTINFO C "
                + "INNER JOIN JIGYOKANRI A "
                + "ON A.JIGYO_ID = C.JIGYO_ID "
                + "INNER JOIN MASTER_JIGYO MASTER_JIGYO "
                + "ON MASTER_JIGYO.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) "
                + "ON C.JIGYO_ID = B.JIGYO_ID "
                + "AND C.SHOZOKU_CD = B.SHOZOKU_CD "
                //2005.12.16 iso チェックリスト件数の不具合修正
//	            + "AND C.JOKYO_ID = B.JOKYO_ID "
                ;
        //2005.08.10 iso 機関マスタに存在しないサンプル期間「99999」対応
        if(userInfo.getRole().equals(UserRole.GYOMUTANTO)) {
            select += "INNER JOIN MASTER_KIKAN MASTER_KIKAN "
                    + " ON MASTER_KIKAN.SHOZOKU_CD = B.SHOZOKU_CD ";
        }
        //理由 担当部局管理テーブルにデータが存在する場合は担当部局管理の部局コードを条件に追加
        if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
            select = select + "INNER JOIN TANTOBUKYOKUKANRI TANTO "
                   + "ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD "
                   + "AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD "
                   + "AND TANTO.BUKYOKUTANTO_ID = '"
                   + EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())
                   + "' ";
        //理由 業務担当者にてチェックリスト一覧を表示すると、権限で表示されない情報も表示されてしまう。
        } else if(UserRole.GYOMUTANTO.equals(userInfo.getRole())){
            select += "INNER JOIN ACCESSKANRI AC "
                    + "ON AC.JIGYO_CD = SUBSTR(B.JIGYO_ID,3,5) "
                    + "AND AC.GYOMUTANTO_ID = '"
                    + EscapeUtil.toSqlString(userInfo.getGyomutantoInfo().getGyomutantoId())
                    + "' ";
        }
        select = select + "WHERE B.DEL_FLG = 0 "
               + "AND A.DEL_FLG = 0 ";

// 20050606 検索条件に事業区分を追加　※特定領域研究が追加されたため検索条件となる事業区分が必要になった
        //事業区分："1"基盤研究、"5"特定領域研究、"6"若手スタートアップ、"7"特別研究促進費
//update ly 2006/06/01
        if(! StringUtil.isBlank(searchInfo.getJigyoKubun())){
        //if(searchInfo.getJigyoKubun() != null){
//update ly 2006/06/01
            select = select + " AND B.JIGYO_KUBUN = "
                   + EscapeUtil.toSqlString(searchInfo.getJigyoKubun())
                   + " ";
        }
        else{
            //事業区分が存在しなかった場合には基盤研究をセット　※特定領域のチェックリストから遷移する場合にはかならず事業区分がセットされているため
            select = select + " AND B.JIGYO_KUBUN = "
                   + IJigyoKubun.JIGYO_KUBUN_KIBAN + " ";
        }

        StringBuffer query = new StringBuffer(select);
        
//        if (searchInfo.getShozokuCd() != null
//                && !searchInfo.getShozokuCd().equals("")) { //所属機関コード
        if (!StringUtil.isBlank(searchInfo.getShozokuCd())) { //所属機関コード
            query.append(" AND C.SHOZOKU_CD = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()));
            query.append("'");
        }
        String[] jokyoList = searchInfo.getSearchJokyoId();
        if(jokyoList != null && jokyoList.length != 0){
            //2005.12.16 iso チェックリスト件数の不具合修正
//	          query.append(" AND C.JOKYO_ID IN(")
            query.append(" AND B.JOKYO_ID IN(")
                 .append(StringUtil.changeArray2CSV(jokyoList, true))
                 .append(")");
        }

// 20050829 基盤研究においても年度を考慮する必要があった → blnJuriflgの必要がなくなった(？)のでselectCheckListメソッドを分ける必要がなくなった？
//        if(!blnJuriflg){
//	          String jigyoCd = searchInfo.getJigyoCd();
//	          if(jigyoCd != null && jigyoCd.length() != 0){
//	              //事業CD（事業IDの3文字目から5文字分）
//	              query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '")
//    		           .append(EscapeUtil.toSqlString(jigyoCd))
//	                   .append("'");
//	          }
//    	  }
//	      else{
//        	  String jigyoId = searchInfo.getJigyoId();
//        	  if(jigyoId != null && jigyoId.length() != 0){
//                //事業ID
//	              query.append(" AND JIGYO_ID = '")
//	                   .append(EscapeUtil.toSqlString(jigyoId))
//	                   .append("'");
//	          }
//    	  }
        String jigyoCd = searchInfo.getJigyoCd();
//update start dyh 2006/06/02
//        if(jigyoCd != null && jigyoCd.length() != 0){
//            //事業CD（事業IDの3文字目から5文字分）
//            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '")
//                 .append(EscapeUtil.toSqlString(jigyoCd))
//                 .append("'");
//        }
        if(jigyoCd == null || jigyoCd.length() == 0){
            // 【チェックリスト管理（基盤研究（C）・萌芽研究・若手研究）】のリンクから遷移
            if (IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(searchInfo.getJigyoKubun())) {
                // 完全電子化基盤の事業CDの格納（基盤S,A,Bなし）
                String[] jigyoCdsKikan = {IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN,  //基盤研究(C)(一般)
                                          IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU, //基盤研究(C)(企画調査)
                                          //DEL START 2007/07/02 BIS 趙一非
                                          //H19完全電子化対応
                                          //表示される種目を修正
                                          //IJigyoCd.JIGYO_CD_KIBAN_HOGA,     //萌芽研究
                                          //DEL END 2007/07/02  BIS 趙一非
                                          IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A, //若手研究(A)
                                          IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B};//若手研究(B)
                    
                query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (");
                query.append(StringUtil.changeArray2CSV(jigyoCdsKikan,true));  
                query.append(")");
            }
        }else{
            //事業CD（事業IDの3文字目から5文字分）
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '")
                 .append(EscapeUtil.toSqlString(jigyoCd))
                 .append("'");
        }
//update end dyh 2006/06/02
//add start ly 2006/06/01 
        //担当事業CD（事業IDの3文字目から5文字分）
        if(searchInfo.getTantoJigyoCd() != null && searchInfo.getTantoJigyoCd().size() != 0){
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
                 .append(StringUtil.changeIterator2CSV(searchInfo.getTantoJigyoCd().iterator(), true))
                 .append(")");
        }
//add end ly 2006/06/01
        //2005/09/01 業務担当者のときは担当事業を条件に追加する
        if(UserRole.GYOMUTANTO.equals(userInfo.getRole())){
            Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
            String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
                 .append(tantoJigyoCd)
                 .append(")")
                 .toString();
        }
            
        String jigyoId = searchInfo.getJigyoId();
        if(jigyoId != null && jigyoId.length() != 0){
            //事業ID
            query.append(" AND JIGYO_ID = '")
                 .append(EscapeUtil.toSqlString(jigyoId))
                 .append("'");
        }

        //理由 事業担当者の場合の回数の条件を追加
        String kaisu = searchInfo.getKaisu();
        if(kaisu != null && kaisu.length() != 0){
            query.append(" AND A.KAISU = '")
                 .append(EscapeUtil.toSqlString(kaisu))
                 .append("'");
        }

        String shozokuName = searchInfo.getShozokuName();
        if (shozokuName != null && shozokuName.length() != 0) {
            query.append(" AND B.SHOZOKU_NAME like '%")
                 .append(EscapeUtil.toSqlString(shozokuName))
                 .append("%'");
        }

        String cancellationFlag = searchInfo.getCancellationFlag();
        //受理状況が全ての場合
        if(cancellationFlag != null && cancellationFlag.equals("0")){
            query.append(" AND (C.CANCEL_FLG = '1' OR C.JOKYO_ID <> '03') ");
        }
        //受理状況が確定解除の場合
        else if(cancellationFlag != null && cancellationFlag.equals("1")){
            query.append(" AND C.CANCEL_FLG = '")
                 .append(EscapeUtil.toSqlString(cancellationFlag))
                 .append("'");
        }

        query.append(" GROUP BY A.NENDO, ");
        query.append("A.KAISU, ");
        query.append("MASTER_JIGYO.JIGYO_NAME, ");
        query.append("A.UKETUKEKIKAN_END, ");
        query.append("C.KAKUTEI_DATE, ");
        query.append("C.EDITION, ");
        query.append("A.JIGYO_ID, ");
        query.append("C.JOKYO_ID, ");
        query.append("B.SHOZOKU_CD, ");
//add start dyh 2006/3/7 原因：受理登録画面で初期値を表示しない
        query.append("B.JURI_BIKO,");//受理備考
//add end dyh 2006/3/7

        //2005.08.10 iso 機関マスタに存在しないサンプル期間「99999」対応
        if(userInfo.getRole().equals(UserRole.GYOMUTANTO)) {
            query.append("MASTER_KIKAN.SHOZOKU_NAME_KANJI, ");
        }
        query.append("B.JYURI_DATE, ");
        query.append("A.UKETUKEKIKAN_START, ");
        query.append("C.CANCEL_FLG ");
        query.append("ORDER BY A.JIGYO_ID, B.SHOZOKU_CD");

        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        //-----------------------
        // ページ情報取得
        //-----------------------
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
// 20050707 ステータス(状況)を取得
            Page page = SelectUtil.selectPageInfo(connection, searchInfo, query.toString());
            new StatusManager(userInfo).setJokyoName(connection, page);
            return page;
// Horikoshi
        }
        catch(NoDataFoundException e){
            throw new NoDataFoundException("該当する情報が存在しませんでした。",
                    new ErrorInfo("errors.4004"),e);
        }
        catch (DataAccessException e) {
            throw new ApplicationException("データ検索中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"),e);
        }
        catch(RecordCountOutOfBoundsException e){
            throw new ApplicationException("該当件数のMAX値を超えました。",
                    new ErrorInfo("errors.4004"),e);
        }
        finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    /**
     * チェックリストの確定処理を行う.<br/><br/>
     *
     * チェックリストテーブル、申請者情報テーブル、業者担当情報テーブルから、
     * 引数で指定されるレコード情報を取得する。<br/><br/>
     *
     * 1.以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
     * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
     * <tr bgcolor="#FFFFFF"><td>
     * <pre>
     * SELECT
     *     	A.JOKYO_ID		                -- 事業ID
     * FROM
     * 	    CHCKLISTINFO A	                -- チェックリスト情報テーブル
     * INNER JOIN
     * 		SHINSEIDATAKANRI B				-- 申請データ管理テーブル
	 * ON
	 * 		A.JOKYO_ID = B.JOKYO_ID
	 * 		AND A.SHOZOKU_CD = B.SYOZOKU_CD
	 * WHERE
	 * 		A.SHOZOKU_CD = ?
	 * 		AND A.JIGYO_ID = ?
	 * 		AND B.JIGYO_KUBUN = 4
	 * FOR UPDATE
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数searchInfoの変数syozokuCd</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数searchInfoの変数jigyoId</td></tr>
	 * </table><br/><br/>
	 *
	 * 2.取得した状況IDをチェックして既に変更されていないか確認する。<BR><BR>
	 *
	 * 3.以下のSQLを実行して、学振有効期限をチェックする。<BR>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		COUNT(*) COUNT							   -- 有効期限内データ件数
	 * FROM
	 * 		SHINSEIDATAKANRI DATA					   -- 申請データ管理テーブル
	 * INNER JOIN
	 * 		CHCKLISTINFO CHECKLIST					   -- チェックリスト情報テーブル
	 * INNER JOIN
	 * 		JIGYOKANRI JIGYO						   -- 事業管理テーブル
	 * ON
	 * 		JIGYO.JIGYO_ID = CHECKLIST.JIGYO_ID
	 * ON
	 * 		CHECKLIST.JIGYO_ID = DATA.JIGYO_ID
	 * 		AND	CHECKLIST.SHOZOKU_CD = DATA.SHOZOKU_CD
	 * WHERE
	 * 		DATA.JOKYO_ID = CHECKLIST.JOKYO_ID
	 * 		AND DATA.DEL_FLG = 0
	 * 		AND JIGYO.DEL_FLG = 0
	 * 		AND DATA.JIGYO_KUBUN = 4
	 * 		AND SYSDATE BETWEEN JIGYO.UKETUKEKIKAN_START AND JIGYO.UKETUKEKIKAN_END
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * SQL実行結果が0件で、状況IDが06(学振受理)ではない場合は、有効期限外としてValidationExceptionを返す。
	 * <BR><BR>
	 *
	 * 4.DBの更新を行う。<BR>
	 * チェックリストの更新用に以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 * 		CHCKLISTINFO			  -- チェックリスト情報テーブル
	 * SET
	 * 		JOKYO_ID = ?,
	 * 		KAKUTEI_DATE = sysdate,
	 * 		EDITION = ?,
	 * 		CANCEL_FLG = 0
	 * WHERE
	 * 		SHOZOKU_CD = ?
	 * 		AND JIGYO_ID = ?
	 * 		AND JOKYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br/>
	 * 変更値
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JOKYO_ID</td><td>第二引数searchInfoの変数jokyoId</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>EDITION</td><td>第二引数searchInfoの変数edition</td></tr>
	 * </table><br/>
	 * 絞込み条件
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数searchInfoの変数jokyoId</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数searchInfoの変数jigyoId</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JOKYO_ID</td><td>第二引数searchInfoの変数jokyoId</td></tr>
	 * </table><br/><br/>
	 * 申請情報管理の更新用に以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 * 		SHINSEIDATAKANRI			-- 申請データ管理テーブル
	 * SET
	 * 		JOKYO_ID = ?
	 * WHERE
	 * 		JIGYO_ID = ?
	 * 		AND JOKYO_ID = ?
	 * 		AND SHOZOKU_CD = ?
	 * 		AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 変更値
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JOKYO_ID</td><td>第二引数searchInfoの変数jokyoId</td></tr>
	 * </table><br/>
	 * 絞込み条件
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数searchInfoの変数jigyoId</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JOKYO_ID</td><td>第二引数searchInfoの変数jokyoId</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数searchInfoの変数shozokuCd</td></tr>
	 * </table><br/><br/>
	 *
	 * 5.行っている処理がチェックリストの確定処理の場合は、表紙PDFを作成する。<BR><BR>
	 * pdfConvertのconvertHyoshiData(Connection, UserInfo, CheckListSearchInfo)メソッドを呼び出して、
	 * 表紙PDFを作成する。<BR>
	 * 作成したPDFはCHCKLISTINFOテーブルのPDF_PATHに記述されたパス上に格納されている｡<BR>
	 *
	 * @param userInfo		UserInfo
	 * @param searchInfo 		CheckListSearchInfo
	 * @param isVersionUp	boolean
	 * @exception	ApplicationException
	 * @exception	ValidationException
	 */
	public void checkListUpdate(UserInfo userInfo,
		CheckListSearchInfo searchInfo,
		boolean isVersionUp)
		throws ApplicationException,ValidationException {

		boolean success = false;
// 2007/02/06 張志男　追加ここから
        String jokyoId= "";
        String afterJokyoId= searchInfo.getChangeJokyoId();
// 2007/02/06　張志男　追加ここまで
        
		//更新
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			CheckListInfoDao dao = new CheckListInfoDao(userInfo);
//  2007/02/06 張志男　修正ここから
            //String jokyoId = dao.checkJokyoId(connection, searchInfo, true);
            jokyoId = dao.checkJokyoId(connection, searchInfo, true);
//  2007/02/06　張志男　修正ここまで			
			//状況IDチェック
			if(jokyoId != null && jokyoId.equals(searchInfo.getJokyoId())){

				//2005/04/12 追加 ここから--------------------------------------------------
				//有効期限外の場合にValidationExceptionを返すように修正
				int checkDate = dao.checkDate(connection, searchInfo);
				//有効期限チェック
// 2006/06/20 dyh update start 原因：CheckListSearchInfo定数を削除
//                if(checkDate == 0 && !jokyoId.equals(CheckListSearchInfo.GAKUSIN_JYURI)){
				if(checkDate == 0 && !StatusCode.STATUS_GAKUSIN_JYURI.equals(jokyoId)){
// 2006/06/20 dyh update end
					throw new ValidationException(
						"学振有効期限外のチェックリストです", new ArrayList());
				}
				//追加 ここまで-------------------------------------------------------------

// 2007/02/06 張志男　追加ここから
                /** ログ 更新前  */
                if (jokyoId.equalsIgnoreCase("03")){
                    statusLog.info( " 応募情報確定前 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                            + " , 事業ID : " + searchInfo.getJigyoId() + " , 機関コード : " + searchInfo.getShozokuCd() + " , 更新前申請状況ID : " + jokyoId );    
                }
                else if (jokyoId.equalsIgnoreCase("04")){
                    statusLog.info( " チェックリスト確定解除前 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                            + " , 事業ID : " + searchInfo.getJigyoId() + " , 所属機関コード : " + searchInfo.getShozokuCd() + " , 更新前申請状況ID : " + jokyoId );
                }
                else if (jokyoId.equalsIgnoreCase("06")){
                    statusLog.info( " チェックリスト受理解除前 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                            + " , 事業ID : " + searchInfo.getJigyoId() + " , 所属機関コード : " + searchInfo.getShozokuCd() + " , 更新前申請状況ID : " + jokyoId );
                }
// 2007/02/06　張志男　追加ここまで 
//              <!-- UPDATE　START 2007/07/18 BIS 張楠 -->
                //審査員割振りデータ取り込み時のチック
                String jigyoCd = searchInfo.getJigyoId().substring(2,7);
    			if (IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyoCd) || IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyoCd) || 
    					IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyoCd) || IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyoCd)){
    				//チェックリストの更新
    				dao.updateCheckListInfo(connection, searchInfo, isVersionUp);
    			}
//    			<!-- UPDATE　END　 2007/07/18 BIS 張楠 -->
				//申請情報管理の更新
				dao.updateShinseiData(connection, searchInfo);

				//2005/05/25 追加 ここから-------------------------------------------------
				//理由 表紙PDF作成のため
// 2006/06/20 dyh update start 原因：CheckListSearchInfo定数を変更
//                if(isVersionUp && jokyoId.equals(CheckListSearchInfo.SHOZOKU_UKETUKE)){
				if(isVersionUp && jokyoId.equals(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU)){
// 2006/06/20 dyh update end
					IPdfConvert pdfConvert = new PdfConvert();
					try{
						pdfConvert.convertHyoshiData(connection, userInfo, searchInfo);
					}catch(Exception e){
						throw new ApplicationException("表紙PDFの作成でエラーが発生しました",e);
					}
				}
				//追加 ここまで------------------------------------------------------------

			}else{
				throw new ApplicationException(
					"既に確定されたチェックリストです",
					new ErrorInfo("errors.4002"));
			}

			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"状況ID更新中にDBエラーが発生しました",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
// 2007/02/06 張志男　追加ここから
                    /** ログ 更新後 */
                    if (jokyoId.equalsIgnoreCase("03")){
                        statusLog.info( " 応募情報確定後 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                                + " , 事業ID : " + searchInfo.getJigyoId() + " , 機関コード : " + searchInfo.getShozokuCd() + " , 更新後申請状況ID : " + afterJokyoId); 
                    } 
                    else if (jokyoId.equalsIgnoreCase("04")){
                        statusLog.info( " チェックリスト確定解除後 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                                + " , 事業ID : " + searchInfo.getJigyoId() + " , 所属機関コード : " + searchInfo.getShozokuCd() + " , 更新後申請状況ID : " + afterJokyoId);
                    }
                    else if (jokyoId.equalsIgnoreCase("06")){
                        statusLog.info( " チェックリスト受理解除後 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                                + " , 事業ID : " + searchInfo.getJigyoId() + " , 所属機関コード : " + searchInfo.getShozokuCd() + " , 更新後申請状況ID : " + afterJokyoId);
                    }
// 2007/02/06　張志男　追加ここまで                    
				} else {
					DatabaseUtil.rollback(connection);
// 2007/02/06 張志男　追加ここから
                    /** ログ 更新失敗  */
                     if (jokyoId.equalsIgnoreCase("03")){
                         statusLog.info( " 応募情報確定失敗 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                                 + " , 事業ID : " + searchInfo.getJigyoId() + " , 機関コード : " + searchInfo.getShozokuCd());
                     }
                     else if (jokyoId.equalsIgnoreCase("04")){
                         statusLog.info( " チェックリスト確定解除失敗 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                                 + " , 事業ID : " + searchInfo.getJigyoId() + " , 所属機関コード : " + searchInfo.getShozokuCd());
                     }
                     else if (jokyoId.equalsIgnoreCase("06")){
                         statusLog.info( " チェックリスト受理解除失敗 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                                 + " , 事業ID : " + searchInfo.getJigyoId() + " , 所属機関コード : " + searchInfo.getShozokuCd());
                     }
// 2007/02/06　張志男　追加ここまで                    
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"状況ID更新中にDBエラーが発生しました",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}

		//---------------
		// メール送信（所属機関担当者のみ） 2005/07/14追加
		//---------------

		if (userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
            // 2006/08/21 dyh add start
            // [基盤研究等（基盤S,A,Bは除く）・特定領域（継続領域）・若手研究スタートアップ・特別研究促進費]以外の所属研究機関がチェックリストを確定したとき、送信しない
            String tempJigyoCd = "";
            if(searchInfo.getJigyoId() != null && searchInfo.getJigyoId().length() == 8){
                tempJigyoCd = searchInfo.getJigyoId().substring(2,7);
            }
            if(!JigyoKubunFilter.getCheckListKakuteiJigyoCd().contains(tempJigyoCd)){
                return;
            }
            // 2006/08/21 dyh add end

			//当該所属機関担当者のメールアドレス情報を取得する
			String to = userInfo.getShozokuInfo().getTantoEmail();

			//-----メール本文ファイルの読み込み
			String content = null;
			try{
				File contentFile = new File(CONTENT_CHECKLIST_KAKUTEI);
				FileResource fileRes = FileUtil.readFile(contentFile);
				content = new String(fileRes.getBinary());
			}catch(FileNotFoundException e){
				throw new ApplicationException(
						"メール本文ファイルが見つかりませんでした。",
// 2006/08/21 dyh update start
						//new ErrorInfo("errors.4002"),
                        new ErrorInfo("errors.7004"),
// 2006/08/21 dyh update end
						e);
			}catch(IOException e){
				throw new ApplicationException(
						"メール本文ファイル読み込み時にエラーが発生しました。",
// 2006/08/21 dyh update start
						//new ErrorInfo("errors.4002"),
                        new ErrorInfo("errors.8003"),
// 2006/08/21 dyh update end
						e);
			}

			//関連情報取得
			//Page titleResult = null;
			try{
				List titleResult = this.selectTitleInfo(searchInfo);

				Map map = (Map)titleResult.get(0);

				//研究種目名
				String jigyoName = "平成" + map.get("NENDO") + "年度 ";

				//回数チェック
				String kaishu = map.get("KAISU").toString();
				if (kaishu != null && Integer.parseInt(kaishu) > 1){
					jigyoName = jigyoName + "第" + kaishu + "回 ";
				}

				jigyoName = jigyoName + map.get("JIGYO_NAME");

				//確定日の取得
				String kakuteiDate = "";
				Date dte = (Date)map.get("KAKUTEI_DATE");
				if (dte != null){
					kakuteiDate = new SimpleDateFormat("yyyy年MM月dd日").format(dte);
				}

				//版数
//				<!-- UPDATE　START 2007/07/18 BIS 王志安 -->
				String edition = map.get("ALLEDITION") == null ? "" : map.get("ALLEDITION").toString();
				//<!-- 古いコード --> String edition = map.get("ALLEDITION").toString();
//				<!-- UPDATE　END 2007/07/18 BIS 王志安 -->
				//件数
				int kensu = selectListDataCount(userInfo, searchInfo);

//				log.debug("JIGYO_NAME:" + map.get("JIGYO_NAME").toString());
//				log.debug("ALLEDITION:" + map.get("ALLEDITION").toString());
//				log.debug("KENSHU:" + kensu);
//				log.debug("KAKUTEI_DATE:" + kakuteiDate);

				//-----メール本文ファイルの動的項目変更
				String[] param = new String[]{
						jigyoName, 						//事業名
						kakuteiDate,					//確定日
						edition,						//版数
						Integer.toString(kensu) 		//応募件数
				};
				content = MessageFormat.format(content, param);

				if (log.isDebugEnabled()) {
					log.debug("content:" + content);
                }
// 2006/08/21 dyh update start
//            }catch (Exception e){
//                throw new ApplicationException(
//                        "チェックリスト確定で関連情報取得に失敗しました。",
//                        new ErrorInfo("errors.4002"),
//                        e);
//            }
			}catch (NoDataFoundException e){
                throw new ApplicationException(
                        "チェックリスト確定で関連情報取得に失敗しました。",
                        new ErrorInfo("errors.5002"),
                        e);
            }catch (Exception e){
				throw new ApplicationException(
						"チェックリスト確定で関連情報取得に失敗しました。",
						new ErrorInfo("errors.8003"),
						e);
			}
// 2006/08/21 dyh update end

			//-----メール送信
			try{
				SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
				mailer.sendMail(FROM_ADDRESS,							//差出人
								to,										//to
								null,									//cc
								null,									//bcc
								SUBJECT_CHECKLIST_KAKUTEI,				//件名
								content);								//本文
			}catch(Exception e){
				//2005.08.03 iso メール送信失敗を警告表示のみに変更
//				throw new ApplicationException(
//						"チェックリスト確定でメール送信に失敗しました。",
//						new ErrorInfo("errors.4002"),
//						e);
				log.warn("メール送信に失敗しました。",e);
			}
		}
	}


	/**
	 * チェックリストの状況IDを取得する.<br/><br/>
	 *
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		A.JOKYO_ID				 -- 状況ID
	 * FROM
	 * 		CHCKLISTINFO A			 -- チェックリスト情報テーブル
	 * INNER JOIN
	 * 		SHINSEIDATAKANRI B		 -- 申請データ管理テーブル
	 * ON
	 * 		A.JOKYO_ID = B.JOKYO_ID
	 * WHERE
	 * 		A.SHOZOKU_CD = ?
	 * 		AND A.JIGYO_ID = ?
	 * 		AND A.JIGYO_KUBUN = 4
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数searchInfoの変数syozokuCd</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数searchInfoの変数jigyoId</td></tr>
	 * </table><br/><br/>
	 *
	 * @param userInfo		UserInfo
	 * @param searchInfo CheckListSearchInfo
	 * @return String	状況ID
	 * @see jp.go.jsps.kaken.model.ICheckListMaintenance#checkJokyoId(UserInfo userInfo, CheckListSearchInfo info)
	 */
	public String checkJokyoId(UserInfo userInfo,
		CheckListSearchInfo searchInfo)
		throws ApplicationException {

		String jokyoId = null;
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			CheckListInfoDao dao = new CheckListInfoDao(userInfo);

			jokyoId = dao.checkJokyoId(connection, searchInfo, false);
		} catch (DataAccessException e) {

			throw new ApplicationException(
				"チェックリスト状況取得中にDBエラーが発生しました",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
				DatabaseUtil.closeConnection(connection);
		}

		return jokyoId;
	}


	/**
	 * チェックリスト表示用のデータを取得する.<br/><br/>
	 *
	 * 　チェックリストテーブル、申請者情報テーブル、業者担当情報テーブルから、
	 * 　引数で指定されるレコード情報を取得する。<br/><br/>
	 *
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		A.NENDO,			   -- 年度
	 * 		A.KAISU,			   -- 回数
	 * 		A.JIGYO_NAME,		   -- 事業名
	 * 		C.KAKUTEI_DATE, 	   -- 確定日
	 * 		C.EDITION ALLEDITION,  -- チェックリストの版
	 * 		B.BUNKASAIMOKU_CD,	   -- 細目番号
	 * 		B.UKETUKE_NO,		   -- 申請番号
	 * 		B.EDITION EDITION,	   -- 申請情報の版
	 * 		B.BUKYOKU_NAME, 	   -- 部局名
	 * 		B.SHOKUSHU_NAME_KANJI, -- 職種名(漢字)
	 * 		B.NAME_KANJI_SEI,	   -- 申請者氏名（漢字等-姓）
	 * 		B.NAME_KANJI_MEI,	   -- 申請者氏名（漢字等-名）
	 * 		B.KADAI_NAME_KANJI,    -- 研究課題名(和文）
	 * 		B.SHINSEISHA_ID,	   -- 申請者ID
	 * 		B.SAKUSEI_DATE, 	   -- 申請書作成日
	 * 		B.SYSTEM_NO,		   -- システム受付番号
	 * 		B.SHOZOKU_CD,		   -- 所属機関コード
	 * 		A.JIGYO_ID			   -- 事業ID
	 * FROM
	 * 		SHINSEIDATAKANRI B	   -- 申請データ管理テーブル
	 * INNER JOIN
	 * 		CHCKLISTINFO C		   -- チェックリスト情報テーブル
	 * INNER JOIN
	 * 		JIGYOKANRI A		   -- 事業管理テーブル
	 * ON
	 * 		A.JIGYO_ID = C.JIGYO_ID
	 * ON
	 * 		C.JIGYO_ID = B.JIGYO_ID
	 * 		AND C.SHOZOKU_CD = B.SHOZOKU_CD
	 *
	 * ------担当部局管理テーブルにデータが存在する場合に追加 --------
	 * INNER JOIN
	 * 		TANTOBUKYOKUKANRI TANTO -- 担当部局管理テーブル
	 * ON
	 * 		B.SHOZOKU_CD = TANTO.SHOZOKU_CD
	 * 		AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD
	 * 		AND TANTO.BUKYOKUTANTO_ID = ?
	 * -----------------------------------------------------------
	 *
	 * WHERE
	 * 		B.JOKYO_ID = C.JOKYO_ID
	 * 		AND B.DEL_FLG = 0
	 * 		AND A.DEL_FLG = 0
	 * 		AND B.JIGYO_KUBUN = 4
	 * <b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 * ORDER BY
	 * 		B.BUNKASAIMOKU_CD
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数searchInfoの変数syozokuCd</td></tr>
	 * </table><br/>
	 *
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>所属CD</td><td>shozokuCd</td><td>AND C.SHOZOKU_CD = '所属CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>事業ID</td><td>jigyoCd</td><td>AND B.JIGYO_ID = '事業ID'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>状況ID</td><td>jokyoList</td><td>AND C.JOKYO_ID IN(状況IDの配列)</td></tr>
	 * </table><br />
	 *
	 * @param userInfo		UserInfo
	 * @param searchInfo 	CheckListSearchInfo
	 * @return page	ページ情報
	 * @see jp.go.jsps.kaken.model.ICheckListMaintenance#selectCheckList(UserInfo userInfo, CheckListSearchInfo info)
	 */
	public Page selectListData(
		UserInfo userInfo,
		CheckListSearchInfo searchInfo)
		throws ApplicationException {

		String select =
			"SELECT A.NENDO, "
				+ "A.KAISU, "
				+ "A.JIGYO_NAME, "
				+ "C.KAKUTEI_DATE, "
				+ "C.EDITION ALLEDITION, "
				+ "B.BUNKASAIMOKU_CD, "
// 20050712 ソート、表示の可能性があるため分割番号を追加
				+ "B.BUNKATSU_NO, "
				+ "B.JOKYO_ID, "
// Horikoshi
				+ "B.UKETUKE_NO, "
				+ "B.EDITION EDITION, "
			//2005/04/12 削除 ここから--------------------------------------------------
			//理由 件数の表示にCOUNTを用いていないため
			//	+ "SUM(DECODE(C.JOKYO_ID, B.JOKYO_ID, 1, 0)) COUNT, "
			//削除 ここまで-------------------------------------------------------------
				+ "B.BUKYOKU_NAME, "
				+ "B.BUKYOKU_NAME_RYAKU, "
				+ "B.SHOKUSHU_NAME_KANJI, "
				+ "B.SHOKUSHU_NAME_RYAKU, "
				+ "B.NAME_KANJI_SEI, "
				+ "B.NAME_KANJI_MEI, "
				+ "B.KADAI_NAME_KANJI, "
				+ "B.SHINSEISHA_ID, "
				+ "B.SAKUSEI_DATE, "
			//2005/04/013 追加 ここから-------------------------------------------------
			//理由 申請書表示用に必要なため
				+ "B.SYSTEM_NO, "
				+ "B.SHOZOKU_CD, "
				+ "A.JIGYO_ID "
			//追加 ここまで-------------------------------------------------------------

// 20050721
				+ ",B.RYOIKI_NO," +
				" B.KOMOKU_NO," +
				" B.SHINSEI_KUBUN," +
				" B.KENKYU_KUBUN "
// Horikoshi

			+ "FROM SHINSEIDATAKANRI B "
			//2005/04/12 追加 ここから--------------------------------------------------
			//理由 INNER JOINの順番変更と条件追加
			//(JIGYOKANRI->CHECKLISTINFO を CHECKLISTINFO->JIGYOKANRIに変更して、所属CDの条件追加)
				+ "INNER JOIN CHCKLISTINFO C "
					+ "INNER JOIN JIGYOKANRI A "
					+ "ON A.JIGYO_ID = C.JIGYO_ID "
				+ "ON C.JIGYO_ID = B.JIGYO_ID "
				+ "AND B.SHOZOKU_CD = C.SHOZOKU_CD ";
			//追加 ここまで-------------------------------------------------------------
			//2005/04/11 追加 ここから--------------------------------------------------
			//理由 担当部局管理テーブルにデータが存在する場合は担当部局管理の部局コードを条件に追加
			if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
				select = select + "INNER JOIN TANTOBUKYOKUKANRI TANTO "
								+ "ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD "
								+ "AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD "
								//2005/04/20　追加 ここから------------------------------------------
								//理由 条件不足のため
								+ "AND TANTO.BUKYOKUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())+"' ";
								//追加 ここまで------------------------------------------------------
			}
			//追加 ここまで-------------------------------------------------------------

			//2005.12.19 iso 学振受付中以降を全て出力するよう変更
//			select = select + "WHERE B.JOKYO_ID = C.JOKYO_ID "
//				+ "AND B.DEL_FLG = 0 "
			select = select + "WHERE B.DEL_FLG = 0 "
// 20050606 Start 検索条件に事業区分を追加　※特定領域研究が追加になったため検索条件に事業区分が必要になった
//				+ "AND A.DEL_FLG = 0 " + "AND B.JIGYO_KUBUN = 4 ";
				+ "AND A.DEL_FLG = 0 ";
				if(searchInfo.getJigyoKubun() != null){
					select = select + " AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + " ";
				}
				else{
					//TODO:事業区分が存在しなかった場合にはエラーとする？　→　暫定的に基盤を指定する
					select = select + " AND B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN + " ";
				}
// Horikoshi End
		StringBuffer query = new StringBuffer(select);
		if (searchInfo.getShozokuCd() != null
			&& !searchInfo.getShozokuCd().equals("")) { //所属機関コード
			query.append(
				" AND C.SHOZOKU_CD = '"
					+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
					+ "'");
		}
		//2005.11.18 iso 所属機関が抜けていたので追加
		if(!StringUtil.isBlank(searchInfo.getShozokuName())){
			query.append(" AND B.SHOZOKU_NAME like '%")
				.append(EscapeUtil.toSqlString(searchInfo.getShozokuName()))
				.append("%'");
		}

		if(searchInfo.getJigyoId() != null
			&& !searchInfo.getJigyoId().equals("")) { //事業ID
			query.append(
				" AND B.JIGYO_ID = '"
				+ EscapeUtil.toSqlString(searchInfo.getJigyoId())
				+ "'");
		}
		if(searchInfo.getJigyoCd() != null
			&& !searchInfo.getJigyoCd().equals("")) { //事業コード
			query.append(
				" AND SUBSTR(B.JIGYO_ID,3,5) = '"
				+ EscapeUtil.toSqlString(searchInfo.getJigyoCd())
				+ "'");
		}
		//2005/09/01 業務担当者のときは担当事業を条件に追加する
		if(UserRole.GYOMUTANTO.equals(userInfo.getRole())){
			
			//UPDATE START 2007/07/23 BIS 趙一非
			//Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
			
			List jigyoCdsKikanList;
			List tantoJigyoCdList=new ArrayList();
			String[] jigyoCdsKikan = {IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN,  //基盤研究(C)(一般)
                    IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU, //基盤研究(C)(企画調査)
                    IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A, //若手研究(A)
                    IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B};//若手研究(B)
			jigyoCdsKikanList=Arrays.asList(jigyoCdsKikan);
			Iterator i=jigyoCdsKikanList.iterator();
			while(i.hasNext())
			{
				String s=(String)i.next();
				if( userInfo.getGyomutantoInfo().getTantoJigyoCd().contains(s))
				{
					tantoJigyoCdList.add(s);
				}
			}
			Iterator ite =tantoJigyoCdList.iterator();
			//UPDATE END 2007/07/23 BIS 趙一非
			String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
			query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
				 .append(tantoJigyoCd)
				 .append(")")
				 .toString();
		}
		
		//2005/04/14 追加 ここから--------------------------------------------------
		//理由 受理情報取得のため追加
		String[] jokyoList = searchInfo.getSearchJokyoId();
		if(jokyoList != null && jokyoList.length != 0){
			//2005.12.19 iso 学振受付中以降を全て出力するよう変更
//			query.append(" AND C.JOKYO_ID IN(")
			query.append(" AND B.JOKYO_ID IN(")
				.append(StringUtil.changeArray2CSV(jokyoList, true))
				.append(")");
		}
		//追加 ここまで-------------------------------------------------------------

// 20050721 ソート順の変更
//		query.append(
//				//2005/04/12 削除 ここから--------------------------------------------------
//				//理由 不要なため削除
//			/*	" GROUP BY A.NENDO, "
//				+ "A.KAISU, "
//				+ "A.JIGYO_NAME, "
//				+ "C.KAKUTEI_DATE, "
//				+ "C.EDITION, "
//				+ "B.BUNKASAIMOKU_CD, "
//				+ "B.UKETUKE_NO, "
//				+ "B.EDITION, "
//				+ "B.BUKYOKU_NAME, "
//				+ "B.SHOKUSHU_NAME_KANJI, "
//				+ "B.NAME_KANJI_SEI, "
//				+ "B.NAME_KANJI_MEI, "
//				+ "B.KADAI_NAME_KANJI, "
//				+ "B.SHINSEISHA_ID, "
//				+ "B.SAKUSEI_DATE, "
//				//2005/04/013 追加 ここから-------------------------------------------------
//				//理由 申請書表示用にSYSTEM_NOが必要なため
//				+ "B.SYSTEM_NO "*/
//				//追加 ここまで-------------------------------------------------------------
//				 "ORDER BY B.BUNKASAIMOKU_CD");

		if(searchInfo.getJigyoKubun() != null &&
			searchInfo.getJigyoKubun() != "" &&
			searchInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI)){

			//事業区分が特定領域の場合
			query.append("ORDER BY")
//					.append(" B.SHINSEI_KUBUN DESC")
//					.append(", B.KENKYU_KUBUN ASC")
//					.append(", B.RYOIKI_NO ASC")
//					.append(", B.CHOSEIHAN DESC")
//					.append(", B.KOMOKU_NO ASC")
//					.append(", B.KADAI_NO_KEIZOKU ASC ")
					.append(" B.SHINSEI_KUBUN DESC")
					.append(", B.KENKYU_KUBUN ASC")
					.append(", B.RYOIKI_NO ASC")
					.append(", B.KOMOKU_NO ASC")
					.append(", B.CHOSEIHAN DESC")
					.append(", B.UKETUKE_NO ASC ")
					;
		}
		else{
			//その他(基盤事業)の場合
			query.append("ORDER BY")
					.append(" B.SHINSEI_KUBUN ASC")
					.append(", B.BUNKASAIMOKU_CD ASC")
// 20050826 分割番号を追加
					.append(", B.BUNKATSU_NO ASC")
					.append(", B.UKETUKE_NO ASC ")
					;
			}
//		else{
//			query.append("ORDER BY B.BUNKASAIMOKU_CD");
//		}
// Horikoshi

		//ソート順（申請者IDの昇順）
		//query.append(" ORDER BY A.KENKYU_NO");

		if (log.isDebugEnabled()) {
			log.debug("query:" + query);
		}

		//-----------------------
		// ページ情報取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(
				connection,
				searchInfo,
				query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"申請書情報取得中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}


	/**
	 * 飛び番号表示用のデータを取得する.<br/><br/>
	 *
	 * 　チェックリストテーブル、申請者情報テーブル、業者担当情報テーブルから、
	 * 　引数で指定されるレコード情報を取得する。<br/><br/>
	 *
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		A.NENDO,				  -- 年度
	 * 		A.KAISU,				  -- 回数
	 * 		A.JIGYO_NAME,			  -- 事業名
	 * 		C.KAKUTEI_DATE, 		  -- 確定日
	 * 		C.EDITION,				  -- 版
	 * 		B.UKETUKE_NO,			  -- 申請番号
	 * 		B.JOKYO_ID, 			  -- 状況ID
	 * 		B.DEL_FLG,				  -- 削除フラグ
	 * 		B.BUNKASAIMOKU_CD		  -- 細目番号
	 * FROM
	 * 		SHINSEIDATAKANRI B		  -- 申請データ管理テーブル
	 * INNER JOIN
	 * 		CHCKLISTINFO C			  -- チェックリスト情報テーブル
	 * INNER JOIN
	 * 		JIGYOKANRI A			  -- 事業管理テーブル
	 * ON
	 * 		A.JIGYO_ID = C.JIGYO_ID
	 * ON
	 * 		C.JIGYO_ID = B.JIGYO_ID
	 * 		AND C.SHOZOKU_CD = B.SHOZOKU_CD
	 * ------部局担当者で担当部局を持つ場合に追加--------
	 * INNER JOIN
	 * 		TANTOBUKYOKUKANRI TANTO   -- 担当部局管理テーブル
	 * ON
	 * 		B.SHOZOKU_CD = TANTO.SHOZOKU_CD
	 * 		AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD
	 * 		AND TANTO.BUKYOKUTANTO_ID = ?
	 * -----------------------------------------------
	 * WHERE
	 * 		(B.DEL_FLG = 1 OR B.JOKYO_ID = '05')
	 * 		AND A.DEL_FLG = 0
	 * 		AND B.JIGYO_KUBUN = 4
	 * 		AND C.SHOZOKU_CD = ?
	 * 		AND B.JIGYO_ID = ?
	 * GROUP BY
	 * 		A.NENDO,
	 * 		A.KAISU,
	 * 		A.JIGYO_NAME,
	 * 		C.KAKUTEI_DATE,
	 * 		C.EDITION,
	 * 		B.UKETUKE_NO,
	 * 		B.JOKYO_ID,
	 * 		B.DEL_FLG,
	 * 		B.BUNKASAIMOKU_CD
	 * ORDER BY
	 * 		B.UKETUKE_NO
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>TANTO.BUKYOKUTANTO_ID</td><td>第一引数userInfoの部局担当者情報bukyokutantoInfoの変数bukyokutantoId</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>C.SHOZOKU_CD</td><td>第二引数searchInfoの変数syozokuCd</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>B.JIGYO_ID</td><td>第二引数searchInfoの変数jigyoId</td></tr>
	 * </table><br/><br/>
	 *
	 * @param userInfo		UserInfo
	 * @param searchInfo 	CheckListSearchInfo
	 * @return page	Page
	 * @see jp.go.jsps.kaken.model.ICheckListMaintenance#selectTobiList(UserInfo userInfo, CheckListSearchInfo info)
	 */
	public Page selectTobiList(UserInfo userInfo,
	CheckListSearchInfo searchInfo)
	throws ApplicationException {

	String select =
		"SELECT A.NENDO, "
			+ "A.KAISU, "
			+ "A.JIGYO_NAME, "
			+ "C.KAKUTEI_DATE, "
			+ "C.EDITION, "
		//2005/04/12 削除 ここから--------------------------------------------------
		//理由 件数の表示にCOUNTを用いていないため
		//	+ "SUM(DECODE(C.JOKYO_ID, B.JOKYO_ID, 1, 0)) COUNT, "
		//削除 ここまで-------------------------------------------------------------
			+ "B.UKETUKE_NO, "
			+ "B.JOKYO_ID, "
			+ "B.DEL_FLG, "
		//2005/04/21 追加 ここから--------------------------------------------------
		//理由 細目番号取得のため追加
			+ "B.BUNKASAIMOKU_CD "
		//追加 ここまで-------------------------------------------------------------

			// ソート順を追加したため
			+ ", B.SHINSEISHA_ID,"
			+ " B.RYOIKI_NO,"
			+ " B.KOMOKU_NO, "
			+ " B.BUNKATSU_NO, "
			+ " B.NAME_KANJI_SEI, "
			+ " B.NAME_KANJI_MEI,"
			+ " B.SHINSEI_KUBUN,"
			+ " B.KENKYU_KUBUN "

			+ "FROM SHINSEIDATAKANRI B "
		//2005/04/12 修正 ここから--------------------------------------------------
		//理由 INNER JOINの順番変更と条件追加
		//(JIGYOKANRI->CHECKLISTINFO を CHECKLISTINFO->JIGYOKANRIに変更して、所属CDの条件追加)
			+ "INNER JOIN CHCKLISTINFO C "
				+ "INNER JOIN JIGYOKANRI A "
				+ "ON A.JIGYO_ID = C.JIGYO_ID "
			+ "ON C.JIGYO_ID = B.JIGYO_ID "
			+ "AND C.SHOZOKU_CD = B.SHOZOKU_CD ";
		//修正 ここまで-------------------------------------------------------------
		//2005/04/11 追加 ここから--------------------------------------------------
		//理由 担当部局管理テーブルにデータが存在する場合は担当部局管理の部局コードを条件に追加
		if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
			select = select + "INNER JOIN TANTOBUKYOKUKANRI TANTO "
							+ "ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD "
							+ "AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD "
							//2005/04/20　追加 ここから------------------------------------------
							//理由 条件不足のため
							+ "AND TANTO.BUKYOKUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())+"' ";
							//追加 ここまで------------------------------------------------------
		}
		//追加 ここまで-------------------------------------------------------------
		
		//2005.10.18 iso PDF変換でエラーとなったケースを表示させるよう変更
		//状況は「01」だが、受け付けNOが存在するケース
//// 20050811 間違いのため対象ステータスを修正 03→02
////		select = select + "WHERE (B.DEL_FLG = 1 OR B.JOKYO_ID = '05') "
//		select = select + "WHERE (B.DEL_FLG = 1 OR B.JOKYO_ID = '02' OR B.JOKYO_ID = '05') "
//// Horikoshi
//		+" AND A.DEL_FLG = 0 "
//
//// 20050606 検索条件に事業区分を追加　※特定領域研究の追加のため条件に事業区分をセット
//		+ "AND B.JIGYO_KUBUN = " + searchInfo.getJigyoKubun() + "  "
//		//飛び番号にならないので受付番号がないものは対象としない
//		+  "AND B.UKETUKE_NO IS NOT NULL ";
//// Horikoshi
		select = select
				+ "WHERE (B.DEL_FLG = 1 OR B.JOKYO_ID = '01' OR B.JOKYO_ID = '02' OR B.JOKYO_ID = '05') "
				+" AND A.DEL_FLG = 0 "
				+ "AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + "  "
				+ "AND B.UKETUKE_NO IS NOT NULL ";
		
	StringBuffer query = new StringBuffer(select);
	if (searchInfo.getShozokuCd() != null
		&& !searchInfo.getShozokuCd().equals("")) { //所属機関コード
		query.append(
			" AND C.SHOZOKU_CD = '"
				+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
				+ "'");
	}
	if(searchInfo.getJigyoId() != null
		&& !searchInfo.getJigyoId().equals("")){	//事業ID
		query.append(
			" AND B.JIGYO_ID = '"
			+ EscapeUtil.toSqlString(searchInfo.getJigyoId())
			+ "'");
	}
	query.append(
			" GROUP BY A.NENDO, "
			+ "A.KAISU, "
			+ "A.JIGYO_NAME, "
			+ "C.KAKUTEI_DATE, "
			+ "C.EDITION, "
			+ "B.UKETUKE_NO, "
			+ "B.JOKYO_ID, "
			+ "B.DEL_FLG, "
// ソート順追加のため
			+ "B.SHINSEISHA_ID, "
			+ "B.SHINSEI_KUBUN, "
			+ "B.KENKYU_KUBUN, "
			+ "B.RYOIKI_NO, "
			+ "B.KOMOKU_NO, "
			+ "B.CHOSEIHAN, "
			+ "B.BUNKATSU_NO, "
			+ "B.NAME_KANJI_SEI, "
			+ "B.NAME_KANJI_MEI, "

			+ "B.BUNKASAIMOKU_CD "
			);

// 20050826 ソート順を追加
	if(searchInfo.getJigyoKubun() != null &&
			searchInfo.getJigyoKubun() != "" &&
			searchInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI)){
			//事業区分が特定領域の場合
			query.append("ORDER BY")
					.append(" B.SHINSEI_KUBUN DESC")
					.append(", B.KENKYU_KUBUN ASC")
					.append(", B.RYOIKI_NO ASC")
					.append(", B.KOMOKU_NO ASC")
					.append(", B.CHOSEIHAN DESC")
					.append(", B.UKETUKE_NO ASC ")
					;
		}
		else{
			//その他(基盤事業)の場合
			query.append("ORDER BY")
					.append(" B.SHINSEI_KUBUN ASC")
					.append(", B.BUNKASAIMOKU_CD ASC")
// 20050826 分割番号を追加
					.append(", B.BUNKATSU_NO ASC")
					.append(", B.UKETUKE_NO ASC ")
					;
		}

	    //ソート順（申請者IDの昇順）
	    //query.append(" ORDER BY A.KENKYU_NO");

	    if (log.isDebugEnabled()) {
		    log.debug("query:" + query);
	    }

		//-----------------------
		// ページ情報取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(
				connection,
				searchInfo,
				query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"申請書情報取得中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}


	//2005/04/11 追加 ここから--------------------------------------------------
	//有効期限チェックのため追加
	/**
	 * チェックリストの情報が学振受付期限以内であるかを確認する.<br/><br/>
	 *
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		COUNT(*) COUNT				-- 有効期限内データ件数
	 * FROM
	 * 		SHINSEIDATAKANRI DATA		-- 申請データ管理テーブル
	 * INNER JOIN
	 * 		CHCKLISTINFO CHECKLIST		-- チェックリスト情報テーブル
	 * INNER JOIN
	 * 		JIGYOKANRI JIGYO			-- 事業管理テーブル
	 * ON
	 * 		JIGYO.JIGYO_ID = CHECKLIST.JIGYO_ID
	 * ON
	 * 		CHECKLIST.JIGYO_ID = DATA.JIGYO_ID
	 * 		AND CHECKLIST.SHOZOKU_CD = DATA.SHOZOKU_CD
	 * WHERE
	 * 		DATA.JOKYO_ID = CHECKLIST.JOKYO_ID
	 * 		AND DATA.DEL_FLG = 0
	 * 		AND JIGYO.DEL_FLG = 0
	 * 		AND DATA.JIGYO_KUBUN = 4
	 * 		AND SYSDATE
	 * 		  BETWEEN JIGYO.UKETUKEKIKAN_START
	 * 		  AND JIGYO.UKETUKEKIKAN_END
	 *		AND CHECKLIST.SHOZOKU_CD = ?
	 * 		AND DATA.JIGYO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数searchInfoの変数syozokuCd</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数searchInfoの変数jigyoId</td></tr>
	 * </table><br/><br/>
	 *
	 * SQLの結果の件数が0件の場合は有効期限内の値がないとしてfalseを返す。<BR>
	 * 件数が1件以上ある場合はtrueを返す。
	 *
	 * @param userInfo		UserInfo
	 * @param searchInfo CheckListSearchInfo
	 * @return boolean		dateFlag
	 */
	public boolean checkLimitDate(UserInfo userInfo,
		CheckListSearchInfo searchInfo)
		throws ApplicationException {

		int dateCheck = 0;
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			CheckListInfoDao dao = new CheckListInfoDao(userInfo);

			dateCheck = dao.checkDate(connection, searchInfo);

			if(dateCheck != 0){
				return true;
			}
		} catch (DataAccessException e) {

			throw new ApplicationException(
				"学振受付期間確認中にDBエラーが発生しました",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
				DatabaseUtil.closeConnection(connection);
		}

		return false;
	}

	//追加 ここまで-------------------------------------------------------------


	//2005/04/14 追加ここから---------------------------------------------------
	//一括受理メソッドを追加
	/**
	 * 一括受理登録を行う.<br>
	 *
	 * 以下の1,2の処理を配列に格納された所属CDの個数分行う。<BR><br>
	 *
	 * <b>1.チェックリストテーブルの更新</b><br/>
	 * 事業IDと所属CDを結合した文字列を生成し、その文字列がHashSetに格納されていない場合のみ処理を行う。<BR>
	 * HashSetに文字列が格納されていない場合に、HashSetに生成した文字列を格納する｡<BR>
	 * 以下のSQLを実行し、チェックリストの状況IDの更新を行う。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 * 		CHCKLISTINFO	   -- チェックリスト情報テーブル
	 * SET
	 * 		JOKYO_ID = ?
	 * WHERE
	 * 		SHOZOKU_CD = ?
	 * 		AND JIGYO_ID = ?
	 * 		AND JOKYO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 変更値
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JOKYO_ID</td><td>06(学振受理)</td></tr>
	 * </table><br/>
	 * 絞込み条件
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数shozokuArrayのi番目の値(iは繰り返し回数)</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第三引数jigyoArrayのi番目の値(iは繰り返し回数)</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JOKYO_ID</td><td>04(学振受付中)</td></tr>
	 * </table><br/><br/>
	 *
	 * <b>2.申請書テーブルの受理</b><br/>
	 * ShinseiMaintenanceクラスのregistShinseiJuriメソッドを呼び出して、
	 * 申請書の受理処理と審査結果テーブルへのデータ追加を行う。<BR>
	 *
	 * @param userInfo 	UserInfo	ユーザ情報
	 * @param shozokuArray	List		所属機関CDの配列
	 * @param jigyoArray	List		事業IDの配列
	 * @param systemArray	List		システム受付番号の配列
	 * @throws ApplicationException
	 */
// 20050721
//	public void juriAll(UserInfo userInfo, List shozokuArray, List jigyoArray, List systemArray)
	public void juriAll(UserInfo userInfo, List shozokuArray, List jigyoArray, List systemArray, String comment)
// Horikoshi
		throws ApplicationException {

		boolean success = false;
		ShinseiMaintenance shinsei = new ShinseiMaintenance();
		ShinseiDataPk shinseiPk = new ShinseiDataPk();
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		Connection connection = null;
		CheckListInfoDao dao = new CheckListInfoDao(userInfo);
		HashSet set = new HashSet();
		String data = null;
// 2007/02/08 張志男　追加ここから 
        //ログの出力用
        StringBuffer logBuffer = new StringBuffer();
// 2007/02/08　張志男　追加ここまで
        
		//更新用状況IDをセット
// 2006/06/20 dyh update start 原因：CheckListSearchInfo定数を変更
//		checkInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU);
//		checkInfo.setChangeJokyoId(StatusCode.STATUS_GAKUSIN_JYURI);
        checkInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU);
        checkInfo.setChangeJokyoId(StatusCode.STATUS_GAKUSIN_JYURI);
// 2006/06/20 dyh update end

		try{
// 2007/02/08 張志男　追加ここから            
            /** ログ 更新前  (事業ID、機関コードの順で，事業ID毎に改行を追加して出力する)*/
            TreeSet treeSet = new TreeSet();
            for(int i = 0; i < shozokuArray.size(); i++){
                //事業IDと所属CD(CHCKLISTINFOテーブルの主キー)を文字列にする
                data = (String)jigyoArray.get(i) + (String)shozokuArray.get(i);
                //既に主キー文字列がHashSetに格納されている場合はチェックリストの更新は行わない
                if(!treeSet.contains(data)){
                    //HashSetに主キー文字列を格納
                    treeSet.add(data);
                }
            }
            Object[] tempArray = treeSet.toArray();           
 
//2007/03/13 苗　削除ここから            
//            int x, y;
//            String temp = "";
//            for (x = tempArray.length - 1; x > 0; x--) {
//                for (y = 0; y < x; y++) {
//                    if (Long.parseLong(tempArray[y].toString()) > Long
//                            .parseLong(tempArray[y + 1].toString())) {
//                        temp = tempArray[y].toString();
//                        tempArray[y] = tempArray[y + 1];
//                        tempArray[y + 1] = temp;
//                    }
//                }
//            }
//2007/03/13 苗　削除ここまで
            //ログの編集
            for (int n = 0; n < tempArray.length; n++) {
                String jigyoId = tempArray[n].toString().substring(0, 7);
                String beforeJigyoId = "";
                if (n > 0) {
                    beforeJigyoId = tempArray[n - 1].toString().substring(0, 7);
                }

                if (!jigyoId.equals(beforeJigyoId)) {
                    if (!StringUtil.isBlank(beforeJigyoId)) {
                        logBuffer.append(")");
                        logBuffer.append(" \n ");
                    }
                    logBuffer.append(" 事業ID :" + jigyoId);
                    logBuffer.append(" , 機関コード(");
                    logBuffer.append(tempArray[n].toString().substring(8, 13));
                } else {
                    logBuffer.append(",");
                    logBuffer.append(tempArray[n].toString().substring(8, 13));
                }
                
                if (n == tempArray.length -1){
                    logBuffer.append(")"); 
                }
            }
            //ログの出力
          statusLog.info( " チェックリスト一括受理前 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
          + " ,  " + logBuffer.toString() );
            //ADD START 2007/07/20 BIS 趙一非
             juriLog.info( "チェックリスト一括受理を開始しました , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                    + " ,  " + logBuffer.toString() );   
            //ADD END 2007/07/20 BIS 趙一非
            data = "";
// 2007/02/08　張志男　追加ここまで         
			connection = DatabaseUtil.getConnection();
			//データの個数分受理登録を行う
			for(int i = 0; i < shozokuArray.size(); i++){                
               	if(i % 1000 == 0) {
					log.info("jigyoId=" + jigyoArray.get(i) + " 処理状況::" + i + "件");
               	}
					
					//ADD START 2007/07/20 BIS 趙一非
	            	//制度改正カスタマイズ
	            	//・1,000件単位で途中経過のログを出力する。
					//システム時間, "チェックリスト一括受理を処理中（1000件）です", ユーザ種別, ログインID, ＜事業IDn, 機関コードn＞（※）
               	if(0==(i%1000)&0!=i)
            	{	
               		juriLog.info(" チェックリスト一括受理を処理中（"+i+"件）です , ユーザ種別 : " + userInfo.getRole()
                            + " , ログインID : " + userInfo.getId()+ " ,  " + logBuffer.toString() );
            	}
					//ADD END 2007/07/20 BIS 趙一非
					
					
				
				
				//事業IDと所属CD(CHCKLISTINFOテーブルの主キー)を文字列にする
				data = (String)jigyoArray.get(i) + (String)shozokuArray.get(i);
				//既に主キー文字列がHashSetに格納されている場合はチェックリストの更新は行わない
				if(!set.contains(data)){
					//HashSetに主キー文字列を格納
					set.add(data);
					//状況ID更新
					checkInfo.setJigyoId((String)jigyoArray.get(i));
					checkInfo.setShozokuCd((String)shozokuArray.get(i));
					//チェックリストの更新
					dao.updateCheckListInfo(connection, checkInfo, true);
				}
				//申請書の受理処理
				shinseiPk.setSystemNo((String)systemArray.get(i));
				shinsei.registShinseiJuri(userInfo, shinseiPk, null, comment, null);
			}
			log.info("jigyoId=" + jigyoArray.get(shozokuArray.size()-1) + "～"  + jigyoArray.get(shozokuArray.size()-1) + " 処理状況::完了");
            success = true;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"状況ID更新中にDBエラーが発生しました",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
// 2007/02/08 張志男　追加ここから
                     /** ログ 更新後 */  
                  statusLog.info( " チェックリスト一括受理後 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                  + " ,  " + logBuffer.toString() );
                  //ADD START 2007/07/20 BIS 趙一非
                  juriLog.info( " チェックリスト一括受理が正常に終了しました, ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                            + " ,  " + logBuffer.toString() ); 
                  //ADD END 2007/07/20 BIS 趙一非
// 2007/02/08　張志男　追加ここまで 
				} else {
					DatabaseUtil.rollback(connection);
//2007/02/08 張志男　追加ここから
                    /** ログ 更新失敗 */
                  statusLog.info( " チェックリスト一括受理失敗 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                  + " ,  " + logBuffer.toString() ); 
                  //ADD START 2007/07/20 BIS 趙一非
                   juriLog.info( " チェックリスト一括受理に失敗しました , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                           + " ,  " + logBuffer.toString() ); 
                   //ADD START 2007/07/20 BIS 趙一非
//2007/02/08　張志男　追加ここまで 
                    
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"状況ID更新中にDBエラーが発生しました",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}


	//追加ここまで---------------------------------------------------------

	//	2005/04/24 追加 ここから----------
	//	理由:チェックリストCSV出力追加のため

	/**
	 * チェックリストCSV出力用のList作成.<br />
	 *
	 * SelectUtilクラスのselectCsvListメソッドを呼び出して以下のSQLを実行し、CSVデータリストを生成する。（バインド変数はSQLの下の表を参照）<BR>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *		B.JIGYO_ID \"事業ID\",							-- 事業ID
	 *		B.NENDO \"年度\",								-- 年度
	 *		B.KAISU \"回数\",								-- 回数
	 *		B.JIGYO_NAME \"事業名\",						-- 事業名
	 *		B.BUNKASAIMOKU_CD \"細目番号\", 				-- 分割番号
	 *		B.SHOZOKU_CD \"機関番号\",						-- 機関番号
	 *		SUBSTR(B.UKETUKE_NO,7) \"整理番号\",			-- 整理番号
	 *		REPLACE(B.EDITION,'0','-') \"版\",				-- 版
	 *		B.BUKYOKU_CD \"部局コード\",					-- 部局コード
	 *		B.BUKYOKU_NAME \"部局名\",						-- 部局名
	 *		B.SHOKUSHU_CD \"職コード\", 					-- 職コード
	 *		B.SHOKUSHU_NAME_KANJI \"職名\", 				-- 職名
	 *		B.NAME_KANJI_SEI \"氏名（漢字等-姓）\", 		 -- 氏名（漢字等-姓）
	 *		B.NAME_KANJI_MEI \"氏名（漢字等-名）\", 		 -- 氏名（漢字等-名）
	 *		B.NAME_KANA_SEI \"氏名（フリガナ-姓）\",		 -- 氏名（フリガナ-姓）
	 *		B.NAME_KANA_MEI \"氏名（フリガナ-名）\" 		 -- 氏名（フリガナ-名）
	 * FROM
	 * 		SHINSEIDATAKANRI B
	 * INNER JOIN
	 * 		CHCKLISTINFO C
	 * INNER JOIN
	 * 		JIGYOKANRI A
	 * ON
	 * 		A.JIGYO_ID = C.JIGYO_ID
	 * ON
	 * 		C.JIGYO_ID = B.JIGYO_ID "
	 * 		AND B.SHOZOKU_CD = C.SHOZOKU_CD
	 * INNER JOIN TANTOBUKYOKUKANRI TANTO
	 * ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD
	 * 		AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD
	 * 		AND TANTO.BUKYOKUTANTO_ID = ?
	 * WHERE B.JOKYO_ID = C.JOKYO_ID
	 * 		AND B.DEL_FLG = 0
	 * 		AND A.DEL_FLG = 0
	 * 		AND B.JIGYO_KUBUN = 4
	 * <b><span style="color:#002288">-- 動的検索条件 --</span></b></pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>所属CD</td><td>shozokuCd</td><td>AND C.SHOZOKU_CD = '所属CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>事業ID</td><td>jigyoCd</td><td>AND B.JIGYO_ID = '事業ID'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>状況ID</td><td>jokyoList</td><td>AND C.JOKYO_ID IN(状況IDの配列)</td></tr>
	 * </table><br />
	 *
	 * @param userInfo		ユーザ情報
	 * @param searchInfo	検索条件
	 * @return				CSV出力用LIST
	 * @throws ApplicationException
	 */
	public List searchCsvData(UserInfo userInfo, CheckListSearchInfo searchInfo)
	throws ApplicationException {
		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------

		String select =
			"SELECT "
			+ "ROWNUM \"チェックリスト表示順\", "
			+ "JIGYO_NAME \"事業名\", "
			+ "SHINSEI_KUBUN \"新規・継続区分\", "
			+ "KADAI_NO_KEIZOKU \"課題番号\", "
			//DEL START 2007/07/04 BIS 趙一非
			//H19完全電子化及び制度改正
			//チェックリストから特定領域用項目を削除
			//+ "KENKYU_KUBUN \"計画・公募・終了区分\", "
			//DEL END 2007/07/04 BIS 趙一非
			+ "SHOZOKU_CD \"機関番号\", "
			+ "SUBSTR(JIGYO_ID,5,2) \"研究種目番号\", "
			+ "SHINSA_KUBUN \"審査区分番号\", "
			+ "BUNKASAIMOKU_CD \"細目番号\", "
			+ "BUNKATSU_NO \"分割番号\", "
			
			
			//DEL START 2007/07/04 BIS 趙一非
			//H19完全電子化及び制度改正
			//チェックリストから特定領域用項目を削除
			//+ "RYOIKI_NO \"領域番号\", "
			//+ "KOMOKU_NO \"研究項目番号\", "
			//+ "OHABAHENKO \"大幅な変更を伴う研究課題\", "
			//+ "CHOSEIHAN \"調整班\", "
			//+ "RYOIKI_RYAKU \"領域略称名\", "
			//DEL END 2007/07/04 BIS 趙一非
			+ "SUBSTR(UKETUKE_NO,7) \"整理番号\", "
			+ "EDITION \"版\", "
			+ "BUKYOKU_CD \"部局番号\", "
			+ "BUKYOKU_NAME \"部局名\", "
			+ "SHOKUSHU_CD \"職番号\", "

			+ "SHOKUSHU_NAME_KANJI \"職名\", "
			+ "NAME_KANA_SEI \"氏名（フリガナ‐姓）\", "
			+ "NAME_KANA_MEI \"氏名（フリガナ‐名）\", "
			+ "NAME_KANJI_SEI \"氏名（漢字等‐姓）\", "
			+ "NAME_KANJI_MEI \"氏名（漢字等‐名）\", "
			+ "NENREI \"年齢\", "
			+ "KENKYU_NO \"応募者研究者番号\", "
			+ "SHOZOKU_NAME \"所属研究機関名\", "
			+ "ZIP \"郵便番号\", "

			+ "ADDRESS \"住所\", "
			+ "TEL \"TEL\", "
			+ "FAX \"FAX\", "
			+ "EMAIL \"Email\", "
			+ "KADAI_NAME_KANJI \"研究課題名\", "
			+ "BUNYA_NAME \"分野\", "
			+ "BUNKA_NAME \"分科\", "
			+ "SAIMOKU_NAME \"細目\", "
			+ "SAIMOKU_KEYWORD \"細目表付表キーワード\", "
			+ "OTHER_KEYWORD \"細目表以外のキーワード\", "

			+ "KENKYU_TAISHO \"類型\", "
			+ "KEIHI1 \"1年目研究経費\", "
			+ "BIHINHI1 \"1年目設備備品費\", "
			+ "SHOMOHINHI1 \"1年目消耗品費\", "
			+ "RYOHI1 \"1年目旅費\", "
			+ "SHAKIN1 \"1年目謝金等\", "
			+ "SONOTA1 \"1年目その他\", "
			+ "KEIHI2 \"2年目研究経費\", "
			+ "BIHINHI2 \"2年目設備備品費\", "
			+ "SHOMOHINHI2 \"2年目消耗品費\", "

			+ "RYOHI2 \"2年目旅費\", "
			+ "SHAKIN2 \"2年目謝金等\", "
			+ "SONOTA2 \"2年目その他\", "
			+ "KEIHI3 \"3年目研究経費\", "
			+ "BIHINHI3 \"3年目設備備品費\", "
			+ "SHOMOHINHI3 \"3年目消耗品費\", "
			+ "RYOHI3 \"3年目旅費\", "
			+ "SHAKIN3 \"3年目謝金等\", "
			+ "SONOTA3 \"3年目その他\", "
			+ "KEIHI4 \"4年目研究経費\", "

			+ "BIHINHI4 \"4年目設備備品費\", "
			+ "SHOMOHINHI4 \"4年目消耗品費\", "
			+ "RYOHI4 \"4年目旅費\", "
			+ "SHAKIN4 \"4年目謝金等\", "
			+ "SONOTA4 \"4年目その他\", "
			+ "KEIHI5 \"5年目研究経費\", "
			+ "BIHINHI5 \"5年目設備備品費\", "
			+ "SHOMOHINHI5 \"5年目消耗品費\", "
			+ "RYOHI5 \"5年目旅費\", "
			+ "SHAKIN5 \"5年目謝金等\", "

			+ "SONOTA5 \"5年目その他\", "
			+ "KEIHI_TOTAL \"総計研究経費\", "
			+ "BIHINHI_TOTAL \"総計設備備品費\", "
			+ "SHOMOHINHI_TOTAL \"総計消耗品費\", "
			+ "RYOHI_TOTAL \"総計旅費\", "
			+ "SHAKIN_TOTAL \"総計謝金等\", "
			+ "SONOTA_TOTAL \"総計その他\", "
			+ "BUNTANKIN_FLG \"分担金の配分\", "
			+ "KAIJIKIBO_FLG \"開示希望の有無\", "
			+ "KENKYU_NINZU \"研究者数\", "

// 2007/03/02  張志男　修正 ここから
            //+ "TAKIKAN_NINZU \"他研究機関の分担者数\", "
            + "TAKIKAN_NINZU \"他機関の分担者数\", "
// 2007/03/02  張志男　修正 ここまで
			+ "SHINSEI_FLG_NO \"研究計画最終年度前年度の応募\", "
			+ "KADAI_NO_SAISYU \"最終年度課題番号\", "
// 2007/02/14  張志男　削除ここから
//          //ADD START DYH 2006/2/15
//			+ "TO_CHAR(SAIYO_DATE, 'YYYY/MM/DD') \"採用年月日\","
//			+ "KINMU_HOUR \"勤務時間数\","
//			+ "NAIYAKUGAKU \"特別研究員奨励費内約額\","
//			+ "OUBO_SHIKAKU \"応募資格\","
//			+ "TO_CHAR(SIKAKU_DATE, 'YYYY/MM/DD') \"資格取得年月日\","
//			+ "SYUTOKUMAE_KIKAN \"資格取得前機関名\","
//			+ "TO_CHAR(IKUKYU_START_DATE, 'YYYY/MM/DD') \"育休等開始日\","
//			+ "TO_CHAR(IKUKYU_END_DATE, 'YYYY/MM/DD') \"育休等終了日\","
//           //ADD END DYH 2006/2/15
// 2007/02/14　張志男　削除ここまで
			+ "TO_CHAR(SAKUSEI_DATE, 'YYYY/MM/DD') \"作成日\" "



			+ "FROM (SELECT "
			+ "B.JIGYO_NAME, "
			+ "B.SHINSEI_KUBUN, "
			+ "B.KADAI_NO_KEIZOKU, "
			+ "B.KENKYU_KUBUN, "
			+ "B.JIGYO_ID, "
			+ "B.SHINSA_KUBUN, "
			+ "B.BUNKASAIMOKU_CD, "
			+ "B.BUNKATSU_NO, "

			+ "B.RYOIKI_NO, "
			+ "B.KOMOKU_NO, "
			+ "B.OHABAHENKO, "
			+ "B.CHOSEIHAN, "
			+ "B.RYOIKI_RYAKU, "
			+ "B.UKETUKE_NO, "
			+ "B.EDITION, "
			+ "B.BUKYOKU_CD, "
			+ "B.BUKYOKU_NAME, "
			+ "B.SHOKUSHU_CD, "

			+ "B.SHOKUSHU_NAME_KANJI, "
			+ "B.NAME_KANJI_SEI, "
			+ "B.NAME_KANJI_MEI, "
			+ "B.NAME_KANA_SEI, "
			+ "B.NAME_KANA_MEI, "
			+ "B.NENREI, "
			+ "B.KENKYU_NO, "
			+ "B.SHOZOKU_CD, "
			+ "B.SHOZOKU_NAME, "
			+ "B.ZIP, "

			+ "B.ADDRESS, "
			+ "B.TEL, "
			+ "B.FAX, "
			+ "B.EMAIL, "
			+ "B.KADAI_NAME_KANJI, "
			+ "B.BUNYA_NAME, "
			+ "B.BUNKA_NAME, "
			+ "B.SAIMOKU_NAME, "
			+ "B.SAIMOKU_KEYWORD, "
			+ "B.OTHER_KEYWORD, "

			+ "B.KENKYU_TAISHO, "
			+ "B.KEIHI1, "
			+ "B.BIHINHI1, "
			+ "B.SHOMOHINHI1, "
			+ "B.RYOHI1, "
			+ "B.SHAKIN1, "
			+ "B.SONOTA1, "
			+ "B.KEIHI2, "
			+ "B.BIHINHI2, "
			+ "B.SHOMOHINHI2, "

			+ "B.RYOHI2, "
			+ "B.SHAKIN2, "
			+ "B.SONOTA2, "
			+ "B.KEIHI3, "
			+ "B.BIHINHI3, "
			+ "B.SHOMOHINHI3, "
			+ "B.RYOHI3, "
			+ "B.SHAKIN3, "
			+ "B.SONOTA3, "
			+ "B.KEIHI4, "

			+ "B.BIHINHI4, "
			+ "B.SHOMOHINHI4, "
			+ "B.RYOHI4, "
			+ "B.SHAKIN4, "
			+ "B.SONOTA4, "
			+ "B.KEIHI5, "
			+ "B.BIHINHI5, "
			+ "B.SHOMOHINHI5, "
			+ "B.RYOHI5, "
			+ "B.SHAKIN5, "

			+ "B.SONOTA5, "
			+ "B.KEIHI_TOTAL, "
			+ "B.BIHINHI_TOTAL, "
			+ "B.SHOMOHINHI_TOTAL, "
			+ "B.RYOHI_TOTAL, "
			+ "B.SHAKIN_TOTAL, "
			+ "B.SONOTA_TOTAL, "
			+ "B.BUNTANKIN_FLG, "
			+ "B.KAIJIKIBO_FLG, "
			+ "B.KENKYU_NINZU, "

			+ "B.TAKIKAN_NINZU, "
			+ "B.SHINSEI_FLG_NO, "
			+ "B.KADAI_NO_SAISYU, "
//2007/02/14  張志男　削除ここから
////add start dyh 2006/2/15
//			+ "B.SAIYO_DATE,"
//			+ "B.KINMU_HOUR,"
//			+ "B.NAIYAKUGAKU,"
////2006/05/12 追加ここから            
////			+ "B.OUBO_SHIKAKU,"
//            + "CASE WHEN B.JIGYO_KUBUN = "
//            + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI
//            + "THEN B.OUBO_SHIKAKU ELSE ' ' END OUBO_SHIKAKU,"
////苗　追加ここまで            
//			+ "B.SIKAKU_DATE,"
//			+ "B.SYUTOKUMAE_KIKAN,"
//			+ "B.IKUKYU_START_DATE,"
//			+ "B.IKUKYU_END_DATE,"
////add end dyh 2006/2/15
//2007/02/14　張志男　削除ここまで
			+ "B.SAKUSEI_DATE "

				+ "FROM SHINSEIDATAKANRI B "
				//2005/04/12 追加 ここから--------------------------------------------------
				//理由 INNER JOINの順番変更と条件追加
				//(JIGYOKANRI->CHECKLISTINFO を CHECKLISTINFO->JIGYOKANRIに変更して、所属CDの条件追加)
					+ "INNER JOIN CHCKLISTINFO C "
						+ "INNER JOIN JIGYOKANRI A "
						+ "ON A.JIGYO_ID = C.JIGYO_ID "
					+ "ON C.JIGYO_ID = B.JIGYO_ID "
					+ "AND B.SHOZOKU_CD = C.SHOZOKU_CD ";
				//追加 ここまで-------------------------------------------------------------
				//2005/04/11 追加 ここから--------------------------------------------------
				//理由 担当部局管理テーブルにデータが存在する場合は担当部局管理の部局コードを条件に追加
				if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
					select = select + "INNER JOIN TANTOBUKYOKUKANRI TANTO "
									+ "ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD "
									+ "AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD "
									//2005/04/20　追加 ここから------------------------------------------
									//理由 条件不足のため
									+ "AND TANTO.BUKYOKUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())+"' ";
									//追加 ここまで------------------------------------------------------
				}
				//追加 ここまで-------------------------------------------------------------

				//2005.12.19 iso 学振受付中以降を全て出力するよう変更
//				select = select + "WHERE B.JOKYO_ID = C.JOKYO_ID "
//				+ "AND B.DEL_FLG = 0 "
				select = select + "WHERE B.DEL_FLG = 0 "
					
					+ "AND A.DEL_FLG = 0 "

// 20050606 Start 検索条件に事業区分を追加　※特定領域研究の追加のため条件に事業区分をセット
					+ "AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + "  ";
// Horikoshi End

			StringBuffer query = new StringBuffer(select);
			if (searchInfo.getShozokuCd() != null
				&& !searchInfo.getShozokuCd().equals("")) { //所属機関コード
				query.append(
					" AND C.SHOZOKU_CD = '"
						+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
						+ "'");
			}
			if(searchInfo.getJigyoId() != null
				&& !searchInfo.getJigyoId().equals("")) { //事業コード
				query.append(
					" AND B.JIGYO_ID = '"
					+ EscapeUtil.toSqlString(searchInfo.getJigyoId())
					+ "'");
			}
			//2005/04/14 追加 ここから--------------------------------------------------
			//理由 受理情報取得のため追加
			String[] jokyoList = searchInfo.getSearchJokyoId();
			if(jokyoList != null && jokyoList.length != 0){
				//2005.12.19 iso 学振受付中以降を全て出力するよう変更
//				query.append(" AND C.JOKYO_ID IN(")
				query.append(" AND B.JOKYO_ID IN(")
					.append(StringUtil.changeArray2CSV(jokyoList, true))
					.append(")");
			}
			//追加 ここまで-------------------------------------------------------------

// ソート機能を追加
			if(searchInfo.getJigyoKubun() != null &&
				searchInfo.getJigyoKubun() != "" &&
				searchInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI)){
				//事業区分が特定領域の場合
				query.append(" ORDER BY")
						.append(" B.SHINSEI_KUBUN DESC")
						.append(", B.KENKYU_KUBUN ASC")
						.append(", B.RYOIKI_NO ASC")
						.append(", B.KOMOKU_NO ASC")
						.append(", B.CHOSEIHAN DESC")
						.append(", B.UKETUKE_NO ASC ")
						;
			}
			else{
				//その他(基盤事業)の場合
				query.append("ORDER BY")
						.append(" B.SHINSEI_KUBUN ASC")
						.append(", B.BUNKASAIMOKU_CD ASC")
						.append(", B.BUNKATSU_NO ASC")
						.append(", B.UKETUKE_NO ASC ")
						;
			}
			query.append(")");
// ここまで

		if (log.isDebugEnabled()) {
			log.debug("query:" + query);
		}

		//-----------------------
		// リスト取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectCsvList(connection,query.toString(), true);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"CSV出力データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4008"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}


	/**
	 * チェックリストCSV出力用のList作成.<br />
	 *
	 * SelectUtilクラスのselectCsvListメソッドを呼び出して以下のSQLを実行し、CSVデータリストを生成する。（バインド変数はSQLの下の表を参照）<BR>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		C.SHOZOKU_CD \"機関番号\",						-- 機関番号
	 * 		B.SHOZOKU_NAME \"所属機関名\",					-- 所属機関名
	 * 		C.JIGYO_ID \"事業ID\",							-- 事業ID
	 * 		A.NENDO \"年度\",								-- 年度
	 * 		A.KAISU \"回数\",								-- 回数
	 * 		B.JIGYO_NAME \"事業名\",						-- 事業名
	 * 		COUNT(*) \"申請件数\",							-- 申請件数
	 * 		C.EDITION \"版\",								-- 版
	 * 		C.JOKYO_ID \"受理状況\",						-- 受理状況
	 * 		TO_CHAR(B.JYURI_DATE, 'YYYY/MM/DD') \"受理日\"	-- 受理日
	 * FROM
	 * 		SHINSEIDATAKANRI B
	 * INNER JOIN
	 * 		CHCKLISTINFO C
	 * INNER JOIN
	 * 		MASTER_JIGYO MASTER_JIGYO
	 * ON
	 * 		MASTER_JIGYO.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5)
	 * INNER JOIN
	 * 		JIGYOKANRI A
	 * ON
	 * 		A.JIGYO_ID = C.JIGYO_ID
	 * ON
	 * 		C.JIGYO_ID = B.JIGYO_ID
	 * 		AND C.SHOZOKU_CD = B.SHOZOKU_CD
	 * 		AND C.JOKYO_ID = B.JOKYO_ID
	 *
	 * ------------------------ 部局担当者で担当部局を持つ場合に追加---------------------------
	 * INNER JOIN
	 * 		TANTOBUKYOKUKANRI TANTO 						   -- 担当部局管理テーブル
	 * ON
	 * 		B.SHOZOKU_CD = TANTO.SHOZOKU_CD
	 * 		AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD
	 * 		AND TANTO.BUKYOKUTANTO_ID = ?
	 * -------------------------------------------------------------------------------------
	 *
	 * -------------業務担当者の場合に権限の無い情報を表示しないようにするため追加---------------
	 * INNER JOIN
	 * 		ACCESSKANRI AC									   -- アクセス管理テーブル
	 * ON
	 * 		AC.JIGYO_CD = SUBSTR(B.JIGYO_ID,3,5)
	 * 		AND AC.GYOMUTANTO_ID = ?
	 * -------------------------------------------------------------------------------------
	 *
	 * <b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 *
	 * GROUP BY
	 * 		A.NENDO,
	 * 		A.KAISU,
	 * 		MASTER_JIGYO.JIGYO_NAME,
	 * 		C.EDITION,
	 * 		C.JIGYO_ID,
	 * 		C.JOKYO_ID,
	 * 		C.SHOZOKU_CD,
	 * 		MASTER_KIKAN.SHOZOKU_NAME_KANJI,
	 * 		B.JYURI_DATE
	 * ORDER BY
	 * 		C.JIGYO_ID, C.SHOZOKU_CD</pre>
	 * </td></tr>
	 * </table><br/>
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>TANTO.BUKYOKUTANTO_ID</td><td>第一引数userInfoの部局担当者情報bukyokutantoInfoの変数bukyokutantoId</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>AC.GYOMUTANTO_ID</td><td>第一引数userInfoの業務担当者情報gyomutantoInfoの変数gyomutantoId</td></tr>
	 * </table><br/><br/>
	 *
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>状況ID</td><td>jokyoList</td><td>AND C.JOKYO_ID IN(状況IDの配列)</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>所属CD</td><td>shozokuCd</td><td>AND C.SHOZOKU_CD = '所属CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>事業CD</td><td>jigyoCd</td><td>AND SUBSTR(A.JIGYO_ID, 3, 5) = '事業CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>回数</td><td>kaisu</td><td>AND A.KAISU = '回数'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>所属機関名</td><td>shozokuName</td><td>AND B.SHOZOKU_NAME like '%所属機関名%'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>受理状況(全て)</td><td>cancellationFlag="0"</td><td>AND (C.CANCEL_FLG = '1' OR C.JOKYO_ID <> '03') </td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>受理状況(確定解除)</td><td>cancellationFlag="1"</td><td>AND C.CANCEL_FLG = '1'</td></tr>
	 * </table><br />
	 *
	 * @param userInfo		ユーザ情報
	 * @param searchInfo	検索条件
	 * @return				CSV出力用LIST
	 * @throws ApplicationException
	 */
	public List searchCsvDataIchiran(UserInfo userInfo, CheckListSearchInfo searchInfo)
	throws ApplicationException {
		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------

		//2005.10.04 iso 申請→応募、所属機関→所属研究機関、コード→番号、申請書→研究計画調書
		String select =
				"SELECT "
   					+ "C.SHOZOKU_CD \"機関番号\", "							//機関番号
				//2005/06/01 変更 ここから--------------------------------------------------
				//理由 所属機関をマスタから取得するため
				//	+ "B.SHOZOKU_NAME \"所属機関名\", "						//所属機関名
					+ "MASTER_KIKAN.SHOZOKU_NAME_KANJI \"所属研究機関名\", "
				//変更 ここまで-------------------------------------------------------------
					+ "C.JIGYO_ID \"事業ID\", "								//事業ID
					+ "A.NENDO \"年度\", "									//年度
					+ "A.KAISU \"回数\", "									//回数
				//2005/06/01 変更 ここから--------------------------------------------------
				//理由 事業名をマスタから取得するため
				//	+ "B.JIGYO_NAME \"事業名\", "							//事業名
					+ "MASTER_JIGYO.JIGYO_NAME \"事業名\", "
				//変更 ここまで-------------------------------------------------------------
					+ "COUNT(*) \"応募件数\", "								//応募件数
					+ "C.EDITION \"版\", "									//版
					+ "C.JOKYO_ID \"受理状況\", "							//受理状況
					+ "TO_CHAR(B.JYURI_DATE, 'YYYY/MM/DD') \"受理日\" "		//受理日

				+ "FROM SHINSEIDATAKANRI B "
					+ "INNER JOIN  CHCKLISTINFO C "
						+ "INNER JOIN JIGYOKANRI A "
						+ "ON A.JIGYO_ID = C.JIGYO_ID "
					//2005/06/01　追加 ここから--------------------------------------
					//理由 事業名をマスタから取得するため
						+ "INNER JOIN MASTER_JIGYO MASTER_JIGYO "
						+ "ON MASTER_JIGYO.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) "
					//追加　ここまで-------------------------------------------------
					+ "ON C.JIGYO_ID = B.JIGYO_ID "
					+ "AND C.SHOZOKU_CD = B.SHOZOKU_CD "
//2006/05/25 削除ここから       原因：画面 の件数とCSVの件数の不具合修正          
//					+ "AND C.JOKYO_ID = B.JOKYO_ID "
//苗　削除ここまで  
				//2005/06/01　追加 ここから------------------------------------------
				//理由 所属機関をマスタから取得するため
					+ "INNER JOIN MASTER_KIKAN MASTER_KIKAN "
					+ " ON MASTER_KIKAN.SHOZOKU_CD = B.SHOZOKU_CD ";
				//追加 ここまで------------------------------------------------------
		 if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
				select = select + "INNER JOIN TANTOBUKYOKUKANRI TANTO "
					+ "ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD "
					+ "AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD "
					+ "AND TANTO.BUKYOKUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())+"' ";
		//	}
		//2005/06/01　追加 ここから--------------------------------------------------
		//理由 業務担当者にてチェックリスト一覧を表示すると、権限で表示されない情報も表示されてしまうため追加
		} else if(UserRole.GYOMUTANTO.equals(userInfo.getRole())){
			select += "INNER JOIN ACCESSKANRI AC "
				+ "ON AC.JIGYO_CD = SUBSTR(B.JIGYO_ID,3,5) "
				+ "AND AC.GYOMUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getGyomutantoInfo().getGyomutantoId())+"' ";
		}
		//2005/06/01  追加 ここまで--------------------------------------------------
			select = select	+ "WHERE B.DEL_FLG = 0 "
					+ "AND A.DEL_FLG = 0 "

// 20050606 Start 検索条件に事業区分を追加　※特定領域研究の追加のため条件に事業区分をセット
//					+ "AND B.JIGYO_KUBUN = 4 ";
					+ "AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + "  ";
// Horikoshi End

			StringBuffer query = new StringBuffer(select);

			if (searchInfo.getShozokuCd() != null
				&& !searchInfo.getShozokuCd().equals("")) { //所属機関コード
				query.append(
					" AND C.SHOZOKU_CD = '"
						+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
						+ "'");
			}
			String[] jokyoList = searchInfo.getSearchJokyoId();
			if(jokyoList != null && jokyoList.length != 0){
				//query.append(" AND C.JOKYO_ID IN(")
				query.append(" AND B.JOKYO_ID IN(")				
					.append(StringUtil.changeArray2CSV(jokyoList, true))
					.append(")");
			}
			
			String jigyoCd = searchInfo.getJigyoCd();
			//2006.11.14 iso 完全電子化対応が抜けていたので追加
//			if(jigyoCd != null && jigyoCd.length() != 0){
//				//事業CD（事業IDの3文字目から5文字分）
//				query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '")
//					 .append(EscapeUtil.toSqlString(jigyoCd))
//					 .append("'");
//			}
	        if(jigyoCd == null || jigyoCd.length() == 0){
	            // 【チェックリスト管理（基盤研究（C）・萌芽研究・若手研究）】のリンクから遷移
	            if (IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(searchInfo.getJigyoKubun())) {
	                // 完全電子化基盤の事業CDの格納（基盤S,A,Bなし）
	                String[] jigyoCdsKikan = {IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN,  //基盤研究(C)(一般)
	                                          IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU, //基盤研究(C)(企画調査)
	                                         //DEL START 2007/07/04 BIS 趙一非
	                                          //H19完全電子化
	                                          //表示種目変更（萌芽研究を削除）
	                                          //IJigyoCd.JIGYO_CD_KIBAN_HOGA,     //萌芽研究
	                                          //DEL END 2007/07/04 BIS 趙一非
	                                          IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A, //若手研究(A)
	                                          IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B};//若手研究(B)
	                    
	                query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (");
	                query.append(StringUtil.changeArray2CSV(jigyoCdsKikan,true));  
	                query.append(")");
	            }
	        }else{
	            //事業CD（事業IDの3文字目から5文字分）
	            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '")
	                 .append(EscapeUtil.toSqlString(jigyoCd))
	                 .append("'");
	        }
			
	        //2006.11.14 iso 担当事業に関する条件が抜けていたので追加
	        //担当事業CD（事業IDの3文字目から5文字分）
	        if(searchInfo.getTantoJigyoCd() != null && searchInfo.getTantoJigyoCd().size() != 0){
	            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
	                 .append(StringUtil.changeIterator2CSV(searchInfo.getTantoJigyoCd().iterator(), true))
	                 .append(")");
	        }
	        //業務担当者のときは担当事業を条件に追加する
	        if(UserRole.GYOMUTANTO.equals(userInfo.getRole())){
	            Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
	            String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
	            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
	                 .append(tantoJigyoCd)
	                 .append(")")
	                 .toString();
	        }
	        
			String kaisu = searchInfo.getKaisu();
			if(kaisu != null && kaisu.length() != 0){
				query.append(" AND A.KAISU = '")
					 .append(EscapeUtil.toSqlString(kaisu))
					 .append("'");
			}
			String shozokuName = searchInfo.getShozokuName();
			if(shozokuName != null && shozokuName.length() != 0){
				query.append(" AND B.SHOZOKU_NAME like '%")
					.append(EscapeUtil.toSqlString(shozokuName))
					.append("%'");
			}
			String cancellationFlag = searchInfo.getCancellationFlag();
			//受理状況が全ての場合
			if(cancellationFlag != null && cancellationFlag.equals("0")){
				query.append(" AND (C.CANCEL_FLG = '1' OR C.JOKYO_ID <> '03') ");
			}
			//受理状況が確定解除の場合
			else if(cancellationFlag != null && cancellationFlag.equals("1")){
				query.append(" AND C.CANCEL_FLG = '")
					.append(EscapeUtil.toSqlString(cancellationFlag))
					.append("'");
			}

			query.append(
				" GROUP BY A.NENDO, "
					+ "A.KAISU, "
				//2005/06/01 変更 ここから--------------------------------------------------
				//理由 事業名をマスタから取得するため
				//	+ "B.JIGYO_NAME, "
					+ "MASTER_JIGYO.JIGYO_NAME, "
				//変更 ここまで-------------------------------------------------------------
					+ "C.EDITION, "
					+ "C.JIGYO_ID, "
					+ "C.JOKYO_ID, "
					+ "C.SHOZOKU_CD, "
				//2005/06/01 変更 ここから--------------------------------------------------
				//理由 所属機関をマスタから取得するため
				//	+ "B.SHOZOKU_NAME, "
					+ "MASTER_KIKAN.SHOZOKU_NAME_KANJI, "
				//変更 ここまで-------------------------------------------------------------
					+ "B.JYURI_DATE "
					+ "ORDER BY C.JIGYO_ID, C.SHOZOKU_CD");

		if (log.isDebugEnabled()) {
			log.debug("query:" + query);
		}

		//-----------------------
		// リスト取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectCsvList(connection,query.toString(), true);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"CSV出力データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4008"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	//	2005/04/23 追加 ここまで----------


	//2005/05/19 追加 ここから----------------------------------------------
	//理由　チェックリスト画面、飛び番号リスト画面のタイトル情報取得のため
	/**
	 * チェックリストタイトル情報取得.<BR><BR>
	 *
	 * 下のSQLを実行し、CSVデータリストを生成する。（バインド変数はSQLの下の表を参照）<BR>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *	   JIGYO.NENDO
	 *	   , JIGYO.KAISU
	 *	   , JIGYO.JIGYO_NAME
	 *	   , C.KAKUTEI_DATE
	 *	   , C.EDITION ALLEDITION
	 *	   , C.JIGYO_ID
	 *	   , C.SHOZOKU_CD
	 * FROM
	 *	   CHCKLISTINFO C
	 * INNER JOIN
	 *	   JIGYOKANRI JIGYO
	 * ON
	 *	   A.JIGYO_ID = C.JIGYO_ID
	 * WHERE
	 *	   JIGYO.JIGYO_KUBUN =4
	 *
	 * <b><span style="color:#002288">-- 動的検索条件 --</span></b></pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>所属CD</td><td>shozokuCd</td><td>AND C.SHOZOKU_CD = '所属CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>事業ID</td><td>jigyoId</td><td>AND C.JIGYO_ID = '事業ID'</td></tr>
	 * </table><br />
	 *
	 * 取得した値は、列名をキーにMapにセットされ、Listにセットされる。
	 * そのListを格納したPageを返却する。<br /><br />
	 *
	 * @param searchInfo	CheckListSearchInfo
	 * @return	Page	ページ情報
	 * @exception ApplicationException
	 */
	public Page selectTitle(CheckListSearchInfo searchInfo)
		throws ApplicationException {

		String select =
			"SELECT JIGYO.NENDO" +
				", JIGYO.KAISU" +
				", JIGYO.JIGYO_NAME" +
				", C.KAKUTEI_DATE" +
				", C.EDITION ALLEDITION " +
				", C.JIGYO_ID " +
				", C.SHOZOKU_CD " +
			"FROM CHCKLISTINFO C " +
			"INNER JOIN JIGYOKANRI JIGYO " +
				"ON JIGYO.JIGYO_ID = C.JIGYO_ID " +

// 20050606 Start 検索条件に事業区分を追加　※特定領域研究の追加のため条件に事業区分をセット
//			"WHERE JIGYO.JIGYO_KUBUN =4 ";
				"WHERE JIGYO.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + " ";
// Horikoshi End

		StringBuffer query = new StringBuffer(select);
		if (searchInfo.getShozokuCd() != null
			&& !searchInfo.getShozokuCd().equals("")) { //所属機関コード
			query.append(
				" AND C.SHOZOKU_CD = '"
					+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
					+ "'");
		}
		if(searchInfo.getJigyoId() != null
			&& !searchInfo.getJigyoId().equals("")){	//事業ID
			query.append(
				" AND C.JIGYO_ID = '"
				+ EscapeUtil.toSqlString(searchInfo.getJigyoId())
				+ "'");
		}
		if (log.isDebugEnabled()) {
			log.debug("query:" + query);
		}

		//-----------------------
		// ページ情報取得
		//-----------------------
		Connection connection = null;
		Page page = null;
		try {
			connection = DatabaseUtil.getConnection();
			page = SelectUtil.selectPageInfo(
				connection,
				searchInfo,
				query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"申請書情報取得中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return page;
	}
	//追加 ここまで------------------------------------------------------


//	//2005/05/25 追加 ここから-------------------------------------------
//	//理由　表紙PDFファイル取得のため
//
//	/**
//	 * 表紙PDFファイルパス取得.<BR><BR>
//	 *
//	 * 下のSQLを実行し、表紙PDFファイルパスを取得する。（バインド変数はSQLの下の表を参照）<BR>
//	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
//	 * <tr bgcolor="#FFFFFF"><td>
//	 * <pre>
//	 *
//	 * SELECT
//	 *	   PDF_PATH 		  -- PDFの格納パス
//	 * FROM
//	 *	   CHCKLISTINFO 	  -- チェックリスト情報テーブル
//	 * WHERE
//	 *	   SHOZOKU_CD = ?
//	 *	   AND JIGYO_ID = ?</pre>
//	 * </td></tr>
//	 * </table><br/>
//	 *
//	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
//	 *	   <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
//	 * 	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数searchInfoの変数shozokuCd</td></tr>
//	 * 	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数searchInfoの変数jigyoId</td></tr>
//	 * </table><br />
//	 *
//	 * 取得したファイルパスを返す。<br /><br />
//	 *
//	 * @param userInfo	UserInfo
//	 * @param searchInfo	CheckListSearchInfo
//	 * @return	表紙PDFファイルパス
//	 * @exception ApplicationException
//	 */
//	public String getPdfFilePath(UserInfo userInfo,
//		CheckListSearchInfo searchInfo)
//		throws ApplicationException {
//
//		String pdfPath = null;
//		Connection connection = null;
//		try{
//			connection = DatabaseUtil.getConnection();
//			CheckListInfoDao dao = new CheckListInfoDao(userInfo);
//			//PDFファイルパスの取得
//			pdfPath = dao.getPath(connection, searchInfo);
//
//		} catch (DataAccessException e) {
//
//			throw new ApplicationException(
//				"PDFファイルパス取得中にDBエラーが発生しました",
//				new ErrorInfo("errors.4002"),
//				e);
//		} finally {
//				DatabaseUtil.closeConnection(connection);
//		}
//		return pdfPath;
//	}
//	//追加 ここまで------------------------------------------------------


	//2005.08.11 iso 表紙ダウンロードはファイルリソースを直接返すよう変更。
	//パスを返すやり方では、Webサーバと業務サーバが分かれると対応できない。
	/**
	 * 表紙用PDFをダウンロードする。
	 * @param userInfo
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public FileResource getPdfFile(UserInfo userInfo,
			CheckListSearchInfo searchInfo)
			throws ApplicationException {

		String pdfPath = null;
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			CheckListInfoDao dao = new CheckListInfoDao(userInfo);
			//PDFファイルパスの取得
			pdfPath = dao.getPath(connection, searchInfo);

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"PDFファイルパス取得中にDBエラーが発生しました",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
				DatabaseUtil.closeConnection(connection);
		}

		FileResource fileRes = null;
		File file = new File(pdfPath);
		try{
			fileRes = FileUtil.readFile(file);
		}catch(IOException e){
			throw new FileIOException(
				"ファイルの入力中にエラーが発生しました。",
				e);
		}

		return fileRes;
	}

	/**
	 * 研究者情報リストのチェック（研究者番号と機関コードの存在チェック）
	 * @param userInfo ログオンしたユーザ情報
	 * @param searchInfo チェックリスト検索情報
	 * @param shozokuCdArray 研究者番号リスト
	 * @param jigyoIdArray 機関コードリスト
	 * @return List
	 * @throws ApplicationException
	 */
	public List IkkatuKenkyushaExist(
			UserInfo userInfo,
			CheckListSearchInfo searchInfo,
			List shozokuCdArray,
			List jigyoIdArray
			) throws
			ApplicationException{

		List lstErrors = new ArrayList();
		List lstResult = new ArrayList();
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			for(int count=0;count<shozokuCdArray.size();count++){
				searchInfo.setShozokuCd(shozokuCdArray.get(count).toString());
				searchInfo.setJigyoId(jigyoIdArray.get(count).toString());
				lstErrors = chkKenkyushaExist(userInfo, searchInfo, connection);
				for(int i=0; i<lstErrors.size(); i++){
					if(!lstResult.contains(lstErrors.get(i))){
						lstResult.add(lstErrors.get(i).toString());
					}
				}
			}
		}catch(ApplicationException ex){
			throw new ApplicationException("研究者の検索中にDBエラーが発生しました。",new ErrorInfo("errors.4004"),ex);
		}finally{
			DatabaseUtil.closeConnection(connection);
		}
		return lstResult;
	}

	/**
	 * 研究者情報のチェックメソッド（研究者番号と機関コードでチェックする）
	 */
	public List chkKenkyushaExist(
			UserInfo userInfo,
			CheckListSearchInfo searchInfo,
			Connection connection
			) throws
			ApplicationException{

		boolean connectFlg		= true;								//コネクションが渡されたのか判別　True：渡された　False：Nullだった
		List lstErrors			= new ArrayList();					//エラーメッセージ配列
		String chSQL			= new String();						//SQL文字列
		List lstKenkyusha		= new ArrayList();					//研究者NO配列
		List lstSystemNo		= new ArrayList();					//システムNO配列
//delete start dyh 2006/2/10 原因：使用しないの変数
//		List lstResult			= new ArrayList();					//
//delete end dyh 2006/2/10
		CheckListInfoDao dao	= new CheckListInfoDao(userInfo);

		try{
			if(connection == null){
				//コネクションが渡されなかった場合には生成
				connection = DatabaseUtil.getConnection();
				connectFlg = false;
			}
			searchInfo.setJokyoId(dao.checkJokyoId(connection, searchInfo, false));

			//システムNO取得SQL作成＆システムNO取得
			chSQL = "Select" +
					" SYSTEM_NO," +
					" BUNKASAIMOKU_CD," +
					" BUNKATSU_NO," +
					" RYOIKI_NO," +
					" KOMOKU_NO," +
					" UKETUKE_NO," +
					" KENKYU_NO," +
					" NAME_KANJI_SEI," +
					" NAME_KANJI_MEI," +
					" NAME_KANA_SEI," +
					" NAME_KANA_MEI " +
					"From" +
					" SHINSEIDATAKANRI " +
					"WHERE" +
					" JIGYO_ID = '" + EscapeUtil.toSqlString(searchInfo.getJigyoId()) + "' " +		//クライアントから飛んでくる検索条件
					"AND" +
					" JOKYO_ID = '" + EscapeUtil.toSqlString(searchInfo.getJokyoId()) + "' " +		//現在のチェックリストの状況ID
					"AND" +
					" SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "' " +	//クライアントから飛んでくる検索条件
					"AND" +
					" DEL_FLG = " + "0" + ""
					;
			if (log.isDebugEnabled()) {log.debug("query:" + chSQL);}
			String chItems[] = new String[]{
					"SYSTEM_NO","BUNKASAIMOKU_CD","BUNKATSU_NO","RYOIKI_NO","KOMOKU_NO","UKETUKE_NO","KENKYU_NO","NAME_KANJI_SEI","NAME_KANJI_MEI","NAME_KANA_SEI","NAME_KANA_MEI"
					};
			lstSystemNo = dao.select(connection, chSQL, chItems);
			if(lstSystemNo.size() <= 0){									//研究情報が存在しない場合
				throw new ApplicationException("研究者の検索中にDBエラーが発生しました。",new ErrorInfo("errors.4004"));
			}

			//研究者情報取得SQL(基本部分)
			chSQL = "Select" +
					" KENKYU_NO," +
					" SHOZOKU_CD," +
					" NAME_KANJI_SEI," +
					" NAME_KANJI_MEI," +
					" NAME_KANA_SEI," +
					" NAME_KANA_MEI " +
					"From" +
					" KENKYUSOSHIKIKANRI " +
					"Where" +
					" SEQ_NO = '1'" +	//2005/08/31 暫定的に研究代表者のみをチェックするように修正。（承認、受理、応募登録とあわせる）
					" AND" +
					" SYSTEM_NO = '"
					;
			for(int i = 0; i<lstSystemNo.size(); i++){
				Object objInfo[] = (Object[])lstSystemNo.get(i);
				String chSysSQL = chSQL + String.valueOf(objInfo[0]) + "'";
				if (log.isDebugEnabled()) {log.debug("query:" + chSysSQL);}

				String chKenkyuSearch[] = new String[]{"KENKYU_NO",
														"SHOZOKU_CD",
														"NAME_KANJI_SEI",
														"NAME_KANJI_MEI",
														"NAME_KANA_SEI",
														"NAME_KANA_MEI"
														};
				lstKenkyusha = dao.select(connection,chSysSQL,chKenkyuSearch);
				if(lstKenkyusha == null || lstKenkyusha.isEmpty()){				//研究者情報が存在しない場合
					throw new ApplicationException("研究者の検索中にDBエラーが発生しました。",new ErrorInfo("errors.4004"));
				}

				//研究者マスタに研究者が存在するかチェック
				String chQuery = "Select" +
						" COUNT(KENKYU_NO) GET_COUNT " +
						"From" +
						" MASTER_KENKYUSHA " +
						"Where "
						;
				for(int n = 0;n<lstKenkyusha.size(); n++){
					Object objKenkyuInfo[] =(Object[])lstKenkyusha.get(n);
					//一括受理、チェックリスト確定（承認）時は研究者番号と機関コードで存在チェック
					String Query =
							" KENKYU_NO = '" + EscapeUtil.toSqlString(String.valueOf(objKenkyuInfo[0])) + "' " +
							"AND" +
							" SHOZOKU_CD = '" + EscapeUtil.toSqlString(String.valueOf(objKenkyuInfo[1])) + "' " +
							"AND" +
							" DEL_FLG = 0 "
							;

					if (log.isDebugEnabled()) {log.debug("query:" + chQuery + Query);}
					String chCount[] = new String[]{"GET_COUNT"};
					List lstRes = dao.select(connection, chQuery + Query, chCount);
					Object objCount[] =(Object[])lstRes.get(0);

					if("0".equals(String.valueOf(objCount[0]))){
						String chMessage = null;
						if(searchInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
//							chMessage = "研究者マスタに以下の研究者が存在しません。：(" +
//										StringUtil.defaultString(objInfo[1]) + "-" +
//										StringUtil.defaultString(objInfo[2]) + "-" +
//										StringUtil.defaultString(objInfo[5]) + ")[" +
//										StringUtil.defaultString(objInfo[7]) + "_" +
//										StringUtil.defaultString(objInfo[8]) + "]" +
//										StringUtil.defaultString(objInfo[6]) + ""
							chMessage = "研究者マスタに以下の研究者が存在しません。：(" +
										StringUtil.defaultString(objInfo[1]) + "-" +
										StringUtil.defaultString(objInfo[2]) + "-" +
										StringUtil.defaultString(objInfo[5]) + ")[" +
										StringUtil.defaultString(objKenkyuInfo[2]) + "_" +
										StringUtil.defaultString(objKenkyuInfo[3]) + "]" +
										StringUtil.defaultString(objKenkyuInfo[0]) + ""
							;
						}
						else if(searchInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI)){
//							chMessage = "研究者マスタに以下の研究者が存在しません。：(" +
//										StringUtil.defaultString(objInfo[3]) + "-" +
//										StringUtil.defaultString(objInfo[4]) + "-" +
//										StringUtil.defaultString(objInfo[5]) + ")[" +
//										StringUtil.defaultString(objInfo[7]) + "_" +
//										StringUtil.defaultString(objInfo[8]) + "]" +
//										StringUtil.defaultString(objInfo[6]) + ""
							chMessage = "研究者マスタに以下の研究者が存在しません。：(" +
										StringUtil.defaultString(objInfo[3]) + "-" +
										StringUtil.defaultString(objInfo[4]) + "-" +
										StringUtil.defaultString(objInfo[5]) + ")[" +
										StringUtil.defaultString(objKenkyuInfo[2]) + "_" +
										StringUtil.defaultString(objKenkyuInfo[3]) + "]" +
										StringUtil.defaultString(objKenkyuInfo[0]) + ""
							;
						}
						else{
							chMessage = "研究者が存在しませんでした。";
						}
						lstErrors.add(chMessage);
					}
					else if(!"1".equals(String.valueOf(objCount[0]))){
						break;
					}
				}
			}
		}
		catch(DataAccessException ex){
			throw new ApplicationException("研究者の検索中にDBエラーが発生しました。",new ErrorInfo("errors.4004"),ex);
//		}catch(NoDataFoundException ex){
//			throw new NoDataFoundException("研究者が存在しませんでした。",new ErrorInfo("errors.5048"),ex);
//			throw new NoDataFoundException(ex.getMessage());
		}catch(ApplicationException ex){
			throw new ApplicationException("研究者の検索中にDBエラーが発生しました。",new ErrorInfo("errors.4004"),ex);
		}finally{
			if(!connectFlg){
				//コネクションが渡されなかった(Nullだった)場合にはメソッド内でコネクションをクローズ
				DatabaseUtil.closeConnection(connection);
			}
		}
		return lstErrors;
	}

	/**
	 * チェックリスト確定後、メール送信用情報を取得する
	 * @param searchInfo
	 * @return List
     * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	private List selectTitleInfo(CheckListSearchInfo searchInfo)
			throws NoDataFoundException, ApplicationException {

		String select = "SELECT JIGYO.NENDO"
                      + ", JIGYO.KAISU"
                      + ", JIGYO.JIGYO_NAME"
                      + ", C.KAKUTEI_DATE"
                      + ", C.EDITION ALLEDITION "
                      + ", C.JIGYO_ID "
                      + ", C.SHOZOKU_CD "
                      + "FROM CHCKLISTINFO C "
                      + "INNER JOIN JIGYOKANRI JIGYO "
                      + "ON JIGYO.JIGYO_ID = C.JIGYO_ID "

					//検索条件に事業区分を追加　※特定領域研究の追加のため条件に事業区分をセット
                      + "WHERE JIGYO.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun());
// 2006/08/21 dyh delete start
//// 2006/06/30 zjp 追加ここから
//                        select = select + " AND SUBSTR(JIGYO.JIGYO_ID,3,5) IN ("
//                        + StringUtil.changeIterator2CSV(JigyoKubunFilter.getCheckListKakuteiJigyoCd().iterator(),true)
//                        + ") ";
////  zjp　追加ここまで
// 2006/08/21 dyh delete end
		StringBuffer query = new StringBuffer(select);
		if (searchInfo.getShozokuCd() != null
			&& !searchInfo.getShozokuCd().equals("")) { //所属機関コード
			query.append(
				" AND C.SHOZOKU_CD = '"
					+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
					+ "'");
		}
		if(searchInfo.getJigyoId() != null
			&& !searchInfo.getJigyoId().equals("")){	//事業ID
			query.append(
				" AND C.JIGYO_ID = '"
				+ EscapeUtil.toSqlString(searchInfo.getJigyoId())
				+ "'");
		}
		if (log.isDebugEnabled()) {
			log.debug("query:" + query);
		}

		//-----------------------
		// ページ情報取得
		//-----------------------
		Connection connection = null;
//delete start dyh 2006/2/10 原因：使用しないの変数
//		Page page = null;
//delete end dyh 2006/2/10
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.select(connection, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"チェックリストタイトル情報取得中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * チェックリスト明細データの件数を取得する
	 * @param userInfo
	 * @param searchInfo
	 * @return 明細件数
	 * @throws ApplicationException
	 */
	private int selectListDataCount(UserInfo userInfo, CheckListSearchInfo searchInfo)
			throws ApplicationException
	{

		String select = "SELECT count(*) CNT "
						+ "FROM SHINSEIDATAKANRI B INNER JOIN CHCKLISTINFO C "
						+ "INNER JOIN JIGYOKANRI A "
						+ "ON A.JIGYO_ID = C.JIGYO_ID "
						+ "ON C.JIGYO_ID = B.JIGYO_ID "
						+ "AND B.SHOZOKU_CD = C.SHOZOKU_CD ";

//		if (userInfo.getBukyokutantoInfo() != null
//				&& userInfo.getBukyokutantoInfo().getTantoFlg()) {
//			select = select + "INNER JOIN TANTOBUKYOKUKANRI TANTO "
//					+ "ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD "
//					+ "AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD "
//					+ "AND TANTO.BUKYOKUTANTO_ID = '"
//					+ userInfo.getBukyokutantoInfo().getBukyokutantoId() + "' ";
//		}

		select = select + "WHERE B.JOKYO_ID = C.JOKYO_ID "
						  + "AND B.DEL_FLG = 0 "
						  + "AND A.DEL_FLG = 0 ";

		if (searchInfo.getJigyoKubun() != null) {
			select = select + " AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun());
		} else {
			select = select + " AND B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN;
		}

		StringBuffer query = new StringBuffer(select);
		if (searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")) { //所属機関コード
			query.append(" AND C.SHOZOKU_CD = '"
					+ EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if (searchInfo.getJigyoId() != null && !searchInfo.getJigyoId().equals("")) { //事業コード
			query.append(" AND B.JIGYO_ID = '"
					+ EscapeUtil.toSqlString(searchInfo.getJigyoId()) + "'");
		}

		String[] jokyoList = searchInfo.getSearchJokyoId();
		if (jokyoList != null && jokyoList.length != 0) {
			query.append(" AND C.JOKYO_ID IN(").append(
					StringUtil.changeArray2CSV(jokyoList, true)).append(")");
		}

		if (log.isDebugEnabled()) {
			log.debug("チェックリスト明細件数query:" + query);
		}

		//-----------------------
		// ページ情報取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			List rstList = SelectUtil.select(connection, query.toString());
			return Integer.parseInt(((Map) rstList.get(0)).get("CNT").toString());
		} catch (DataAccessException e) {
			throw new ApplicationException("チェックリスト明細件数取得中にDBエラーが発生しました。",
						new ErrorInfo("errors.4004"), e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * 応募書類の提出書出力（基盤研究等、特定領域研究）
	 * @param UserInfo userInfo ログイン者情報
	 * @param checkInfo チェックリスト検索条件
	 * @return　FileResource　出力情報CSVファウル
	 */
	public FileResource createOuboTeishutusho(
			UserInfo userInfo,
			CheckListSearchInfo checkInfo)
			throws ApplicationException
	{
		//DBレコード取得
		List csv_data = null;
		try {
			csv_data = selectOuboTeishutushoInfo(checkInfo);
		} finally {
		}

//		if (csv_data.size() < 2){
//			throw new ApplicationException(
//					"応募情報がありません。",
//					new ErrorInfo("errors.5002"));
//		}

		//-----------------------
		// CSVファイル出力
		//-----------------------
		String csvFileName = "";
		if (checkInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
			csvFileName = "KIBANDATA";//基盤
		}else if(checkInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI)){
			csvFileName = "TOKUTEIDATA";//特定領域
		}
// 2007/02/12  張志男　削除ここから
//        else if(checkInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)){
//			csvFileName = "WAKATE";//若手スタートアップ
//		}else{
//			csvFileName = "SHOKUSHINHI";//特別研究促進費
//		}
// 2007/02/12　張志男　削除ここまで
        
		//2005/09/09 takano フォルダ名をミリ秒単位に変更。念のため同時に同期処理も組み込み。
		String filepath = null;
		synchronized(log){
			//2005/9/27 ロック時間が短くて同じ現象が再現した為、ログイン者IDも組み込む
			//filepath = OUBO_WORK_FOLDER + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";
			filepath = OUBO_WORK_FOLDER + userInfo.getShozokuInfo().getShozokuTantoId() + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";	
		}
		CsvUtil.output(csv_data, filepath, csvFileName);

		//-----------------------
		// 依頼書ファイルのコピー
		//-----------------------
//update start dyh 2006/2/8
        //基盤
		if (checkInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + OUBO_FORMAT_FILE_NAME_KIBAN),
							  new File(filepath + OUBO_FORMAT_FILE_NAME_KIBAN));
			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + "$1"), new File(filepath + "$"));
        //特定領域
		}else if (checkInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI)){
			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + OUBO_FORMAT_FILE_NAME_TOKUTEI),
							  new File(filepath + OUBO_FORMAT_FILE_NAME_TOKUTEI));
			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + "$2"), new File(filepath + "$"));
		
		}
// 2007/02/12  張志男　削除ここから
//        //若手スタートアップ
//        else if (checkInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)){
//			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + OUBO_FORMAT_FILE_NAME_WAKATESTART),
//							  new File(filepath + OUBO_FORMAT_FILE_NAME_WAKATESTART));
//			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + "$3"), new File(filepath + "$"));
//		//特別研究促進費
//		}else{
//			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + OUBO_FORMAT_FILE_NAME_SHOKUSHINHI),
//					  new File(filepath + OUBO_FORMAT_FILE_NAME_SHOKUSHINHI));
//			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + "$4"), new File(filepath + "$"));
//		}
// 2007/02/12　張志男　削除ここまで
//update end dyh 2006/2/8

		//-----------------------
		// ファイルの圧縮
		//-----------------------
		String comp_file_name = csvFileName + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
		FileUtil.fileCompress(filepath, filepath, comp_file_name);

		//-------------------------------------
		//作成したファイルを読み込む。
		//-------------------------------------
		File exe_file = new File(filepath + comp_file_name + ".EXE");
		FileResource compFileRes = null;
		try {
			compFileRes = FileUtil.readFile(exe_file);
		} catch (IOException e) {
			throw new ApplicationException(
				"作成ファイル'" + comp_file_name + ".EXE'情報の取得に失敗しました。",
				new ErrorInfo("errors.8005"),
				e);
		}finally{
			//作業ファイルの削除
			FileUtil.delete(exe_file.getParentFile());
		}

		//自己解凍型圧縮ファイルをリターン
		return compFileRes;
	}

	/**
	 * 応募書類の提出書出力情報の取得
	 * 取得条件：　2005/8/30　変更
	 * 基盤の場合：各種目の件数は該当種目新規継続区分＝１の件数
	 * 　　　　　　継続課題件数は各種目新規継続区分＝２の合計件数
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	private List selectOuboTeishutushoInfo(CheckListSearchInfo searchInfo)
			throws ApplicationException {

		StringBuffer sbSelecct = new StringBuffer(1024);
		StringBuffer sbSelecct2 = new StringBuffer(1024);
		StringBuffer sbFrom = new StringBuffer(512);
		StringBuffer sbWhere = new StringBuffer(512);

		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
        // 2006/08/17 dyh add start
        String jigyoKubun = searchInfo.getJigyoKubun();
        // 事業区分が存在しなかった場合には基盤研究をセット
        if(StringUtil.isBlank(jigyoKubun)){
            jigyoKubun = IJigyoKubun.JIGYO_KUBUN_KIBAN;
        }
        // 2006/08/17 add end

		//基盤の場合
		if (IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKubun)){
			sbSelecct.append("SELECT  ")
					.append(" SHOZOKU_CD                                \"機関番号\", \n")
					.append(" SHOZOKU_NAME                              \"所属機関名\", \n")	//差込印刷Wordとのリンクが切れるため「所属機関」のまま
// 2006/08/17 dyh delete start 原因：Word様式から削除したため
					//.append(" DECODE(KIBAN_S_CNT,  0, '', KIBAN_S_CNT ) \"基盤Ｓ件数\", \n")
					//.append(" DECODE(KIBAN_A1_CNT, 0, '', KIBAN_A1_CNT) \"基盤Ａ一般件数\", \n")
					//.append(" DECODE(KIBAN_A2_CNT, 0, '', KIBAN_A2_CNT) \"基盤Ａ調査件数\", \n")
					//.append(" DECODE(KIBAN_B1_CNT, 0, '', KIBAN_B1_CNT) \"基盤Ｂ一般件数\", \n")
					//.append(" DECODE(KIBAN_B2_CNT, 0, '', KIBAN_B2_CNT) \"基盤Ｂ調査件数\", \n")
					.append(" DECODE(KIBAN_C1_CNT, 0, '', KIBAN_C1_CNT) \"基盤Ｃ一般件数\", \n")
//2006/08/18 削除を復活
					.append(" DECODE(KIBAN_C2_CNT, 0, '', KIBAN_C2_CNT) \"基盤Ｃ調査件数\", \n")
// 2006/08/17 dyh delete end
					.append(" DECODE(HOUGA_CNT,    0, '', HOUGA_CNT)    \"萌芽研究件数\", \n")
					.append(" DECODE(WAKATE_A_CNT, 0, '', WAKATE_A_CNT) \"若手Ａ件数\", \n")
					.append(" DECODE(WAKATE_B_CNT, 0, '', WAKATE_B_CNT) \"若手Ｂ件数\", \n")
					.append(" DECODE(CONTU_CNT,    0, '', CONTU_CNT)    \"継続課題件数\", \n")
					.append(" TOTAL_CNT                                 \"総件数\" \n");

			sbSelecct.append(" FROM ( \n");

			sbSelecct.append(" SELECT  ")
					.append(" MIN(B.SHOZOKU_CD)   SHOZOKU_CD, \n")
					.append(" MIN(B.SHOZOKU_NAME) SHOZOKU_NAME, \n")
// 2006/08/17 dyh delete start 原因：Word様式から削除したため
					//.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00031' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_S_CNT, \n")//基盤Ｓ
					//.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00041' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_A1_CNT, \n")//基盤Ａ一般
					//.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00043' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_A2_CNT, \n")//基盤Ａ調査
					//.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00051' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_B1_CNT, \n")//基盤Ｂ一般
					//.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00053' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_B2_CNT, \n")//基盤Ｂ調査
					.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00061' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_C1_CNT, \n")//基盤Ｃ一般
//2006/08/18 削除を復活
					.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00062' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_C2_CNT, \n")//基盤Ｃ調査
// 2006/08/17 dyh delete end
					.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00111' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) HOUGA_CNT, \n")//萌芽研究
					.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00121' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) WAKATE_A_CNT, \n")//若手Ａ
					.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00131' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) WAKATE_B_CNT, \n")//若手Ｂ
					.append(" SUM(DECODE(B.SHINSEI_KUBUN,'2',1,0)) CONTU_CNT, \n")
					.append(" COUNT(*)     TOTAL_CNT \n");
        //特定領域の場合
		}else if(IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(jigyoKubun)){
			sbSelecct.append("SELECT")
					.append(" DECODE(T1.SHOZOKU_CD,NULL,T2.SHOZOKU_CD,T1.SHOZOKU_CD) \"機関番号\", \n")
					.append(" DECODE(T1.SHOZOKU_NAME,NULL,T2.SHOZOKU_NAME,T1.SHOZOKU_NAME) \"所属機関名\", \n")
					.append(" DECODE(T1.KOUBO1_CNT,    0, '', T1.KOUBO1_CNT)    \"公募計画件数\", \n")
					.append(" DECODE(T1.KOUBO2_CNT,    0, '', T1.KOUBO2_CNT)    \"公募研究件数\", \n")
					.append(" DECODE(T1.NO_KOUBO_CNT,  0, '', T1.NO_KOUBO_CNT)  \"非公募件数\", \n")
					.append(" DECODE(T2.KIKAN_END_CNT, 0, '', T2.KIKAN_END_CNT) \"研究終了件数\", \n")
					.append(" DECODE(T2.TOTAL_CNT,NULL,T1.TOTAL_CNT,T2.TOTAL_CNT) \"総件数\" \n");

			sbSelecct.append(" FROM ( \n");
			
			sbSelecct.append("SELECT")
					.append(" MIN(B.SHOZOKU_CD)   SHOZOKU_CD, \n")
					.append(" MIN(B.SHOZOKU_NAME) SHOZOKU_NAME, \n")
					.append(" SUM(CASE WHEN B.KENKYU_KUBUN = '1' AND D.KOUBO_FLG = '1'\n")
					.append(" THEN 1 ELSE 0 END) KOUBO1_CNT, \n")
					.append(" SUM(DECODE(B.KENKYU_KUBUN, 2,1,0)) KOUBO2_CNT, \n")
					.append(" SUM(CASE WHEN B.KENKYU_KUBUN = '1' AND NVL(D.KOUBO_FLG,'0') <> '1'\n")
					.append(" THEN 1 ELSE 0 END) NO_KOUBO_CNT, \n")
//					.append(" SUM(DECODE(B.KENKYU_KUBUN, 3,1,0)) KIKAN_END_CNT, \n")
					.append(" COUNT(*)     TOTAL_CNT \n");

			sbSelecct2.append("(SELECT")
					.append(" MIN(B.SHOZOKU_CD)   SHOZOKU_CD, \n")
					.append(" MIN(B.SHOZOKU_NAME) SHOZOKU_NAME, \n")
					.append(" SUM(DECODE(B.KENKYU_KUBUN, 3,1,0)) KIKAN_END_CNT, \n")
					.append(" COUNT(*)     TOTAL_CNT \n");
		
		}
// 2007/02/12  張志男　削除ここから
//        //若手スタートアップの場合
//        else if(IJigyoKubun.JIGYO_KUBUN_WAKATESTART.equals(jigyoKubun)){
//			sbSelecct.append("SELECT ")
//			        .append(" SHOZOKU_CD                            \"機関番号\", \n")
//			        .append(" SHOZOKU_NAME                          \"所属機関名\", \n")
//			        .append(" DECODE(WAKATE_CNT, 0, '', WAKATE_CNT) \"スタートアップ件数\" \n");
////delete start dyh 2006/2/22 原因：仕様変更
//			        //.append(" TOTAL_CNT                             \"総件数\" \n");
////delete end dyh 2006/2/22 原因：仕様変更
//
//	        sbSelecct.append(" FROM ( \n");
//
//	        sbSelecct.append(" SELECT ")
//			        .append(" MIN(B.SHOZOKU_CD)   SHOZOKU_CD, \n")
//			        .append(" MIN(B.SHOZOKU_NAME) SHOZOKU_NAME, \n")
//			        .append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00141' THEN 1 ELSE 0 END) WAKATE_CNT \n");
////delete start dyh 2006/2/22 原因：仕様変更
////			        .append(" COUNT(*)     TOTAL_CNT \n");
////delete end dyh 2006/2/22 原因：仕様変更
//
//		//特別研究促進費の場合
//		}else{
//			sbSelecct.append("SELECT ")
//	                .append(" SHOZOKU_CD                                \"機関番号\", \n")
//	                .append(" SHOZOKU_NAME                              \"所属機関名\", \n")
//	                .append(" DECODE(KIBAN_A_CNT, 0, '', KIBAN_A_CNT)   \"基盤Ａ相当件数\", \n")
//	                .append(" DECODE(KIBAN_B_CNT, 0, '', KIBAN_B_CNT)   \"基盤Ｂ相当件数\", \n")
//	                .append(" DECODE(KIBAN_C_CNT, 0, '', KIBAN_C_CNT)   \"基盤Ｃ相当件数\", \n")
//	                .append(" DECODE(WAKATE_A_CNT, 0, '', WAKATE_A_CNT) \"若手Ａ相当件数\", \n")
//	                .append(" DECODE(WAKATE_B_CNT, 0, '', WAKATE_B_CNT) \"若手Ｂ相当件数\", \n")
//	                .append(" TOTAL_CNT                                 \"総件数\" \n");
//
//	        sbSelecct.append(" FROM ( \n");
//
//	        sbSelecct.append(" SELECT ")
//	                .append(" MIN(B.SHOZOKU_CD)   SHOZOKU_CD, \n")
//	                .append(" MIN(B.SHOZOKU_NAME) SHOZOKU_NAME, \n")
//	                .append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00152' THEN 1 ELSE 0 END) KIBAN_A_CNT, \n")
//	                .append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00153' THEN 1 ELSE 0 END) KIBAN_B_CNT, \n")
//	                .append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00154' THEN 1 ELSE 0 END) KIBAN_C_CNT, \n")
//	                .append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00155' THEN 1 ELSE 0 END) WAKATE_A_CNT, \n")
//	                .append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00156' THEN 1 ELSE 0 END) WAKATE_B_CNT, \n")
//	                .append(" COUNT(*)     TOTAL_CNT \n");
//		}
// 2007/02/12　張志男　削除ここまで
        
		sbFrom.append(" FROM SHINSEIDATAKANRI B \n")
				.append( "INNER JOIN  CHCKLISTINFO C \n")
				.append( "INNER JOIN JIGYOKANRI A \n")
				.append("ON A.JIGYO_ID = C.JIGYO_ID \n")
				//.append("INNER JOIN MASTER_JIGYO MASTER_JIGYO ")
				//.append("ON MASTER_JIGYO.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) ")
				.append("ON C.JIGYO_ID = B.JIGYO_ID \n")
				.append("AND C.SHOZOKU_CD = B.SHOZOKU_CD \n")
			// 2006/08/18 saiこの条件は削除する	
				//.append("AND C.JOKYO_ID = B.JOKYO_ID \n")
			// 2006/08/18 sai	
				//.append("INNER JOIN MASTER_KIKAN MASTER_KIKAN ")
				//.append("ON MASTER_KIKAN.SHOZOKU_CD = B.SHOZOKU_CD")
				;

		//検索条件を作成する
        //削除フラグを設定
		sbWhere.append(" WHERE B.DEL_FLG = 0 \n")
				.append("AND A.DEL_FLG = 0 \n");

// 2006/08/17 dyh update start 原因：上で事業区分の判断が追加している。
        //事業区分を設定
        sbWhere.append( " AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(jigyoKubun));
//		if(searchInfo.getJigyoKubun() != null){
//			sbWhere.append( " AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()));
//		}
//		else{
//			//事業区分が存在しなかった場合には基盤研究をセット
//			//※特定領域のチェックリストから遷移する場合にはかならず事業区分がセットされているため
//			sbWhere.append(" AND B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN );
//		}

        // 事業コードを設定(基盤の場合のみ)
        if(IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKubun)){
            sbWhere.append(" AND SUBSTR(B.JIGYO_ID,3,5) IN (");
//          2006/08/18 事業コード'00062'を追加
            sbWhere.append("'00061','00062','00111','00121','00131')");
        }
// 2006/08/17 dyh update end

        //2006.11.07 iso 応募書類の件数に特定新規分が含まれて件数が合わないバグ修正
        // 事業コードを設定(特定領域の場合、継続のみをカウントする)
        if(IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(jigyoKubun)){
            sbWhere.append(" AND SUBSTR(B.JIGYO_ID,3,5) IN (");
            sbWhere.append("'00021')");
        }
        
		if (searchInfo.getShozokuCd() != null
			&& !searchInfo.getShozokuCd().equals("")) { //所属機関コード
			sbWhere.append(" AND C.SHOZOKU_CD = '"
					+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
					+ "'");
		}

		//状況IDが03,04以外の場合に対応するため
		String[] jokyoList = searchInfo.getSearchJokyoId();
		if(jokyoList != null && jokyoList.length != 0){
//		 2006/08/18 sai B.JOKYO_IDに修正
			//sbWhere.append(" AND C.JOKYO_ID IN(")
			sbWhere.append(" AND B.JOKYO_ID IN(")	
//		 2006/08/18 sai			
				.append(StringUtil.changeArray2CSV(jokyoList, true))
				.append(")");
		}

		String cancellationFlag = searchInfo.getCancellationFlag();
		//受理状況が全ての場合
		if(cancellationFlag != null && cancellationFlag.equals("0")){
			sbWhere.append(" AND (C.CANCEL_FLG = '1' OR C.JOKYO_ID <> '03') ");
		}
		//受理状況が確定解除の場合
		else if(cancellationFlag != null && cancellationFlag.equals("1")){
			sbWhere.append(" AND C.CANCEL_FLG = '")
				.append(EscapeUtil.toSqlString(cancellationFlag))
				.append("'");
		}
		sbWhere.append(" )");

		//特定研究領域の場合
		if (IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(jigyoKubun)){
			//計画研究、公募研究
			sbSelecct.append(sbFrom.toString())
					.append("INNER JOIN MASTER_RYOIKI D \n")
					.append("ON B.RYOIKI_NO = D.RYOIKI_NO \n")
					.append("AND B.KOMOKU_NO = D.KOMOKU_NO \n");

			sbSelecct.append(sbWhere.toString())
					.append(" T1,")
					.append(sbSelecct2.toString())
					.append(sbFrom.toString())
					.append(sbWhere.toString())
					.append(" T2");

		}
		//基盤の場合
		else{
			sbSelecct.append(sbFrom.toString()).append(sbWhere.toString());
		}

		if (log.isDebugEnabled()) {
			log.debug("query:" + sbSelecct);
		}

		//-----------------------
		// ページ情報取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			return SelectUtil.selectCsvList(connection, sbSelecct.toString(), true);

		}
		catch(NoDataFoundException e){
			throw new NoDataFoundException("該当する情報が存在しませんでした。",new ErrorInfo("errors.4004"),e);}
		catch (DataAccessException e) {
			throw new ApplicationException("データ検索中にDBエラーが発生しました。",new ErrorInfo("errors.4004"),e);}
		finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICheckListMaintenance#blnCheckListAcceptUnacceptable(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.CheckListSearchInfo)
	 */
	public List CheckListAcceptUnacceptable(
						UserInfo userInfo,
						CheckListSearchInfo searchInfo
						)
			throws
			NoDataFoundException,
			ApplicationException
	{

/** ページ情報の検索を実行して受理、不受理の対象となる情報を取得 **/
		Page result = null;
		try{
			result = selectListData(userInfo, searchInfo);
			if (result == null) {
				//発生した場合には例外をスロー
				throw new ApplicationException("受理情報の取得処理でエラーが発生しました。",new ErrorInfo("errors.5002"));
			}
		}catch(NoDataFoundException ex){
			throw new NoDataFoundException("受理情報の取得処理でエラーが発生しました。",new ErrorInfo("errors.5002"),ex);
		}catch(ApplicationException ex){
			throw new ApplicationException("受理情報の取得処理でエラーが発生しました。",new ErrorInfo("errors.4001"),ex);
		}finally{
		}

		//------申請者ID項目名：SHINSEISHA_ID
		List lstErrors				= new ArrayList();
		String shozokuCd			= ShinseiSearchInfo.ORDER_BY_SHOZOKU_CD;
		String jigyoId				= ShinseiSearchInfo.ORDER_BY_JIGYO_ID;
		String systemNo				= ShinseiSearchInfo.ORDER_BY_SYSTEM_NO;
		ArrayList shozokuCdArray	= new ArrayList();					//------所属CDの配列
		ArrayList jigyoIdArray		= new ArrayList();					//------事業IDの配列
		ArrayList systemNoArray		= new ArrayList();					//------SYSTEM_NOの配列
        
// 2007/02/06 張志男　追加ここから
        /** ログ : 更新後申請状況IDを取得  */                   
         String afterJokyoId= searchInfo.getChangeJokyoId();                                      
//2007/02/06　張志男　追加ここまで 
            
		//------申請者の数分繰り返し
		for(int i = 0; i < result.getSize(); i++){
			//------各申請者の情報を取得
			HashMap juriDataMap		= (HashMap) result.getList().get(i);
			Object shozokuData		= juriDataMap.get(shozokuCd);
			Object jigyoData		= juriDataMap.get(jigyoId);
			Object systemData		= juriDataMap.get(systemNo);
			//------申請者IDにデータがある場合は配列に格納
			if (shozokuData != null && !shozokuData.equals("")
				&& jigyoData != null && !jigyoData.equals("")
				&& systemData!= null && !systemData.equals("")) {
					shozokuCdArray.add(shozokuData);
					jigyoIdArray.add(jigyoData);
					systemNoArray.add(systemData);
			}
		}
		//状況IDを取得
		HashMap dataMap = (HashMap)result.getList().get(0);
		Object jokyoID = dataMap.get("JOKYO_ID");
		searchInfo.setJokyoId(jokyoID.toString());

/** 受理、不受理の処理を判別して実行 **/
		Connection connection = null;
// 2007/02/06 張志男　追加ここから
        /** ログ 更新前 */                   
            statusLog.info( " チェックリスト受理登録前 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                    + " , 事業ID : " + (String)jigyoIdArray.get(0) + " , 所属機関コード : " + (String)shozokuCdArray.get(0) + " , 更新前申請状況ID : " + jokyoID);                                     
//2007/02/06　張志男　追加ここまで
		try{
			connection = DatabaseUtil.getConnection();			//コネクションの取得
// 2006/06/20 dyh update start 原因：CheckListSearchInfo定数を変更
//            if(searchInfo.getChangeJokyoId().equals(StatusCode.STATUS_GAKUSIN_JYURI)){
			if(searchInfo.getChangeJokyoId().equals(StatusCode.STATUS_GAKUSIN_JYURI)){
// 2006/06/20 dyh update end
				// 受理
				lstErrors = CheckListJuri(
						searchInfo, userInfo, connection,
						shozokuCdArray, jigyoIdArray, systemNoArray
						);
			}
// 2006/06/20 dyh update start 原因：CheckListSearchInfo定数を変更
//            else if(searchInfo.getChangeJokyoId().equals(StatusCode.STATUS_GAKUSIN_FUJYURI)){
			else if(searchInfo.getChangeJokyoId().equals(StatusCode.STATUS_GAKUSIN_FUJYURI)){
// 2006/06/20 dyh update end
				// 不受理
				for (int i = 0; i < result.getSize(); i++) {
					ShinseiDataPk addPk = new ShinseiDataPk(systemNoArray.get(i).toString());
					EntryFujuriInfo(
							userInfo, connection, addPk,
							searchInfo.getJuriComment(),
							null							//整理番号(受理)
							);
				}  
				//CHECKLISTINFOの更新
				CheckListInfoDao dao = new CheckListInfoDao(userInfo);
				dao.updateCheckListInfo(connection, searchInfo, true);
			}
		}catch(SystemBusyException ex){
			throw new ApplicationException("受理情報の取得処理でエラーが発生しました。",new ErrorInfo("errors.4001"),ex);
		}catch(DataAccessException ex){
			throw new ApplicationException("受理情報の取得処理でエラーが発生しました。",new ErrorInfo("errors.4001"),ex);
//		}catch(NoDataFoundException ex){
//			throw new NoDataFoundException(ex.getMessage());
		}catch(ApplicationException ex){
			throw new ApplicationException("受理情報の取得処理でエラーが発生しました。",new ErrorInfo("errors.4001"),ex);
		}finally{
			try{
				//コミット＆ロールバック
				if(lstErrors.isEmpty()){
					DatabaseUtil.commit(connection);
// 2007/02/06 張志男　追加ここから
                    /** ログ 更新後 */                   
                        statusLog.info( " チェックリスト受理登録後 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                                + " , 事業ID : " + searchInfo.getJigyoId() + " , 所属機関コード : " + searchInfo.getShozokuCd() + " , 更新後申請状況ID : " + afterJokyoId);                                     
// 2007/02/06　張志男　追加ここまで  
				}else{
					DatabaseUtil.rollback(connection);
// 2007/02/06 張志男　追加ここから
                    /** ログ 更新失敗 */                   
                        statusLog.info( " チェックリスト受理登録失敗 , ユーザ種別 : " + userInfo.getRole() + " , ログインID : " + userInfo.getId() 
                                + " , 事業ID : " + searchInfo.getJigyoId() + " , 所属機関コード : " + searchInfo.getShozokuCd());                                     
// 2007/02/06　張志男　追加ここまで  
				}
			}catch(TransactionException e){
				throw new ApplicationException("状況ID更新中にDBエラーが発生しました", new ErrorInfo("errors.4002"), e);
			}finally{
			}
			DatabaseUtil.closeConnection(connection);
		}
		return lstErrors;
	}


	/**
	 * チェックリストでの受理処理を実行する（基盤、特定）
	 * @param		checkInfo
	 * @param		userInfo
	 * @param		connection
	 * @param		shozokuArray
	 * @param		jigyoArray
	 * @param		systemArray
	 * @throws		ApplicationException
	 */
	public List CheckListJuri(
			CheckListSearchInfo checkInfo,
			UserInfo userInfo,
			Connection connection,
			List shozokuArray,
			List jigyoArray,
			List systemArray
			)
			throws ApplicationException {

		List lstErrors				= new ArrayList();
		String data					= null;
		HashSet set					= new HashSet();
		ShinseiDataPk shinseiPk		= new ShinseiDataPk();
		CheckListInfoDao dao		= new CheckListInfoDao(userInfo);

		//受理が可能なのは「学振受付中(04)」「学振不受理(07)」のみ
// 2006/06/20 dyh update start 原因：CheckListSearchInfo定数を変更
//        if(checkInfo.getJokyoId() == null ||
//                (!checkInfo.getJokyoId().equals(StatusCode.STATUS_GAKUSIN_SHORITYU) &&
//                !checkInfo.getJokyoId().equals(StatusCode.STATUS_GAKUSIN_FUJYURI))
//                ){
		if(checkInfo.getJokyoId() == null ||
			(!checkInfo.getJokyoId().equals(StatusCode.STATUS_GAKUSIN_SHORITYU) &&
			!checkInfo.getJokyoId().equals(StatusCode.STATUS_GAKUSIN_FUJYURI))
			){
// 2006/06/20 dyh update end
			throw new ApplicationException(
					"受理可能な状態の応募ではありません。",
					new ErrorInfo("errors.9015")
					);
		}

		//研究者の存在チェック
		try{
			lstErrors = chkKenkyushaExist(userInfo, checkInfo, null);
			if(!lstErrors.isEmpty()){
				return lstErrors;
			}
			for(int i = 0; i < shozokuArray.size(); i++){							//データの個数分受理登録を行う
				data = (String)jigyoArray.get(i) + (String)shozokuArray.get(i);		//事業IDと所属CD(CHCKLISTINFOテーブルの主キー)を文字列にする
				if(!set.contains(data)){											//既に主キー文字列がHashSetに格納されている場合はチェックリストの更新は行わない
					set.add(data);													//HashSetに主キー文字列を格納
					checkInfo.setJigyoId((String)jigyoArray.get(i));				//状況ID更新
					checkInfo.setShozokuCd((String)shozokuArray.get(i));			//
					dao.updateCheckListInfo(connection, checkInfo, true);			//チェックリストの更新
				}
				shinseiPk.setSystemNo((String)systemArray.get(i));					//申請書の受理処理
				EntryJuriInfo(
						userInfo, shinseiPk, connection, checkInfo.getJuriComment(),
						null														//受理整理番号
						);
			}
		}catch(DataAccessException e){
			throw new ApplicationException("状況ID更新中にDBエラーが発生しました", new ErrorInfo("errors.4002"), e);
		}catch(ApplicationException ex){
			throw new ApplicationException("研究者の検索中にDBエラーが発生しました。",new ErrorInfo("errors.4004"),ex);
		}
		return lstErrors;
	}


	/**
	 * 受理情報の登録を実行する
	 * @param userInfo
	 * @param shinseiDataPk
	 * @param connection
	 * @param comment
	 * @param seiriNo
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public synchronized void EntryJuriInfo(
			UserInfo userInfo,
			ShinseiDataPk shinseiDataPk,
			Connection connection,
			String comment,
			String seiriNo)
		throws
			NoDataFoundException,
			ApplicationException
		{
//delete start dyh 2006/2/10 原因：使用しないの変数
//		boolean success		= false;
//delete end dyh 2006/2/10
		ShinseiDataInfoDao dao	= new ShinseiDataInfoDao(userInfo);		//申請データ管理DAO
		try {
			//排他制御のため既存データを取得する
			ShinseiDataInfo existInfo = null;
			try{
				existInfo = dao.selectShinseiDataInfoForLock(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException("申請書管理データ排他取得中にDBエラーが発生しました。", new ErrorInfo("errors.4001"), e);
			}

			//---申請データ削除フラグチェック---
			String delFlag = existInfo.getDelFlg();
			if(FLAG_APPLICATION_DELETE.equals(delFlag))
			{
				throw new ApplicationException(
					"当該申請データは削除されています。SystemNo=" + shinseiDataPk.getSystemNo(),
					new ErrorInfo("errors.9001"));
			}

			//---申請データステータスチェック---
			String jyokyoId = existInfo.getJokyoId();
// 2006/06/20 dyh update start 原因：StatusCode定数を変更
//            if( !(StatusCode.STATUS_GAKUSIN_SHORITYU.equals(jyokyoId)) &&
//                     !(StatusCode.STATUS_GAKUSIN_JYURI.equals(jyokyoId)) &&
//                      !(StatusCode.STATUS_GAKUSIN_FUJYURI.equals(jyokyoId))
//                      ){
			if( !(StatusCode.STATUS_GAKUSIN_SHORITYU.equals(jyokyoId)) &&
				 !(StatusCode.STATUS_GAKUSIN_JYURI.equals(jyokyoId)) &&
				  !(StatusCode.STATUS_GAKUSIN_FUJYURI.equals(jyokyoId))
				  ){
// 2006/06/20 dyh update end
				//---学振処理中、学振受理、学振不受理以外の場合はエラー
				throw new ApplicationException(
					"当該申請データは受理可能なステータスではありません。SystemNo="
					+ shinseiDataPk.getSystemNo(),
					new ErrorInfo("errors.9015"));
			}

			//---受理登録時の重複申請チェック---
			if(CHECK_DUPLICACATION_FLAG){
				int count = 0;
				try{
					count = dao.countDuplicateApplicationForJuri(connection, existInfo);
				}catch(DataAccessException e){
					throw new ApplicationException("申請書管理データ検索中にDBエラーが発生しました。", new ErrorInfo("errors.4004"), e);
				}
				//重複チェック結果
				if(count != 0){
					throw new ApplicationException(
							"申請受理登録時に重複申請が見つかりました。SystemNo="
							+ shinseiDataPk.getSystemNo(),
							new ErrorInfo("errors.9017"));
				}
			}

			//---DB更新---
			try {
				try{
					ShinsaKekkaInfoDao shinsaKekkaDao = new ShinsaKekkaInfoDao(userInfo);	//---審査結果テーブルを作成---
					shinsaKekkaDao.deleteShinsaKekkaInfo(connection, shinseiDataPk);		//現行データを削除

					//新規データを作成
					ShinsaKekkaInfo shinsaKekkaInfo = new ShinsaKekkaInfo();
					shinsaKekkaInfo.setSystemNo(existInfo.getSystemNo());								//システム番号
					shinsaKekkaInfo.setUketukeNo(existInfo.getUketukeNo());								//申請番号
					shinsaKekkaInfo.setJigyoKubun(existInfo.getKadaiInfo().getJigyoKubun());			//事業区分
					shinsaKekkaInfo.setShinsaKubun(existInfo.getKadaiInfo().getShinsaKubun());			//審査区分
					shinsaKekkaInfo.setJigyoId(existInfo.getJigyoId());									//事業ID
					shinsaKekkaInfo.setJigyoName(existInfo.getJigyoName());								//事業名
					shinsaKekkaInfo.setBunkaSaimokuCd(existInfo.getKadaiInfo().getBunkaSaimokuCd());	//細目番号
					shinsaKekkaInfo.setShinsaJokyo("0");												//審査状況
					
					//TODO 2段階審査　ダミーデータを基盤の場合は12個、それ以外の場合は3個作成する。
					int dummyCnt = 0;
//2006/04/27 追加ここから                    
//					if(IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(shinsaKekkaInfo.getJigyoKubun())){
					if(IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(shinsaKekkaInfo.getJigyoKubun())
                            || IJigyoKubun.JIGYO_KUBUN_WAKATESTART.equals(shinsaKekkaInfo.getJigyoKubun())
                            || IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI.equals(shinsaKekkaInfo.getJigyoKubun())){
//苗　追加ここまで                        
						dummyCnt = IShinsainWarifuri.SHINSAIN_NINZU_KIBAN;
					}else{
						dummyCnt = IShinsainWarifuri.SHINSAIN_NINZU_GAKUSOU;
					}
					//審査員番号、シーケンス番号が異なるレコードを作成
					for(int i = 0 ; i < dummyCnt ; i++){
						//桁数を合わせる
						if(i < 9){
							shinsaKekkaInfo.setShinsainNo("@00000"+ new Integer(i+1).toString());	//審査員番号(7桁)
						}else{
							shinsaKekkaInfo.setShinsainNo("@0000"+ new Integer(i+1).toString());	//審査員番号(7桁)
						}
						shinsaKekkaInfo.setSeqNo(new Integer(i+1).toString());					//シーケンス番号
						shinsaKekkaDao.insertShinsaKekkaInfo(connection, shinsaKekkaInfo);
					}
				}catch(DataAccessException e){
					throw new ApplicationException( "審査結果情報登録中にDBエラーが発生しました。", new ErrorInfo("errors.4001"), e);
				}

				try{
					//更新データをセットする
// 2006/06/20 dyh update start 原因：StatusCode定数を変更
//                    if((StatusCode.SAISHINSEI_FLG_SAISHINSEITYU).equals(existInfo.getSaishinseiFlg())){
//                        //申請フラグが「1（再申請中）」の場合は「2（再申請済み）」に変更
//                        existInfo.setSaishinseiFlg(StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI);
//                    }
//                    existInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_JYURI); //申請状況「06：学振受理」
					if(StatusCode.SAISHINSEI_FLG_SAISHINSEITYU.equals(existInfo.getSaishinseiFlg())){
						//申請フラグが「1（再申請中）」の場合は「2（再申請済み）」に変更
						existInfo.setSaishinseiFlg(StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI);
					}
					existInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_JYURI);	//申請状況「06：学振受理」
//2006/06/20 dyh update end
					existInfo.setJuriKekka(FLAG_JURI_KEKKA_JURI);				//受理結果
					existInfo.setJuriBiko(comment);								//受理結果備考
					existInfo.setSeiriNo(seiriNo);								//整理番号
					existInfo.setJyuriDate(new Date());							//学振受理日
					dao.updateShinseiDataInfo(connection, existInfo, true);
				}catch(DataAccessException e){
					throw new ApplicationException( "申請情報更新中にDBエラーが発生しました。", new ErrorInfo("errors.4001"), e);
				}
//delete start dyh 2006/2/10 原因：使用しないの変数
//				success = true;
//delete end dyh 2006/2/10
			}catch (ApplicationException e){
				throw new ApplicationException("申請情報更新中にDBエラーが発生しました。", new ErrorInfo("errors.4001"), e);
			}
		}finally{
			//コミット＆ロールバックは上位側で実行
			//コネクションの切断は上位側で実行
		}
	}


	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICheckListMaintenance#EntryFujuriInfo(jp.go.jsps.kaken.model.vo.UserInfo, java.sql.Connection, jp.go.jsps.kaken.model.vo.ShinseiDataPk, java.lang.String, java.lang.String)
	 */
	public boolean EntryFujuriInfo(
			UserInfo userInfo,
			Connection connection,
			ShinseiDataPk shinseiDataPk,
			String comment,
			String seiriNo)
            throws NoDataFoundException, ApplicationException {
        boolean success = false;
        try {
            ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo); // 申請データ管理DAO

            // 排他制御のため既存データを取得する
            ShinseiDataInfo existInfo = null;
            try {
                existInfo = dao.selectShinseiDataInfoForLock(connection,
                        shinseiDataPk, true);
            }
            catch (NoDataFoundException e) {
                throw e;
            }
            catch (DataAccessException e) {
                throw new ApplicationException(
                        "申請書管理データ排他取得中にDBエラーが発生しました。",
                        new ErrorInfo("errors.4001"), e);
            }

            // ---申請データ削除フラグチェック---
            String delFlag = existInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException(
                        "当該申請データは削除されています。SystemNo="
                        + shinseiDataPk.getSystemNo(),
                        new ErrorInfo("errors.9001"));
            }

			//---申請データステータスチェック---
			String jyokyoId = existInfo.getJokyoId();
// 2006/06/20 dyh update start 原因：StatusCode定数を変更
//            if(!(StatusCode.STATUS_GAKUSIN_SHORITYU.equals(jyokyoId)) &&
//                     !(StatusCode.STATUS_GAKUSIN_JYURI.equals(jyokyoId)) &&
//                      !(StatusCode.STATUS_GAKUSIN_FUJYURI.equals(jyokyoId))){
			if(!(StatusCode.STATUS_GAKUSIN_SHORITYU.equals(jyokyoId)) &&
				!(StatusCode.STATUS_GAKUSIN_JYURI.equals(jyokyoId)) &&
				!(StatusCode.STATUS_GAKUSIN_FUJYURI.equals(jyokyoId))){
// 2006/06/20 dyh update end
					//---学振処理中、学振受理、学振不受理以外の場合はエラー
				throw new ApplicationException(
                        "当該申請データは不受理可能なステータスではありません。SystemNo="
						+ shinseiDataPk.getSystemNo(),
						new ErrorInfo("errors.9016"));
			}

			//---DB更新---
			try{
				try{
					ShinsaKekkaInfoDao shinsaKekkaDao = new ShinsaKekkaInfoDao(userInfo);		//審査結果テーブルを作成
					shinsaKekkaDao.deleteShinsaKekkaInfo(connection, shinseiDataPk);			//現行データを削除
				}catch(DataAccessException e){
					throw new ApplicationException("審査結果情報削除中にDBエラーが発生しました。", new ErrorInfo("errors.4001"), e);
				}

				try{
					//更新データをセットする
// 2006/06/20 dyh update start 原因：StatusCode定数を変更
//                    if((StatusCode.SAISHINSEI_FLG_SAISHINSEITYU).equals(existInfo.getSaishinseiFlg())){
//                        //申請フラグが「1（再申請中）」の場合は「2（再申請済み）」に変更
//                        existInfo.setSaishinseiFlg(StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI);
//                    }
//                    existInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_FUJYURI);       //申請状況「07：学振不受理」
					if((StatusCode.SAISHINSEI_FLG_SAISHINSEITYU).equals(existInfo.getSaishinseiFlg())){
						//申請フラグが「1（再申請中）」の場合は「2（再申請済み）」に変更
						existInfo.setSaishinseiFlg(StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI);
					}
					existInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_FUJYURI);		//申請状況「07：学振不受理」
// 2006/06/20 dyh update end
					existInfo.setJuriKekka(FLAG_JURI_KEKKA_FUJURI);						//受理結果
					existInfo.setJuriBiko(comment);										//受理結果備考
					existInfo.setSeiriNo(seiriNo);										//整理番号
					existInfo.setJyuriDate(new Date());									//学振受理日
					dao.updateShinseiDataInfo(connection, existInfo, true);					//応募情報の更新
					success = true;
				}catch(DataAccessException e){
					throw new ApplicationException("申請情報更新中にDBエラーが発生しました。", new ErrorInfo("errors.4001"), e);
				}
			}catch(ApplicationException e){
				throw new ApplicationException("申請情報更新中にDBエラーが発生しました。", new ErrorInfo("errors.4001"), e);
			}
		}
		finally{
			//コミット＆ロールバックは上位側で実行
			//コネクションの切断は上位側で実行
		}
		return success;
	}
}