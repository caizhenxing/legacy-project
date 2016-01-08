/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.IJigyoKanriMaintenance;
import jp.go.jsps.kaken.model.IShinsaKekkaMaintenance;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.ILabelKubun;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.JigyoKanriInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterLabelInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterSogoHyokaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinsaKekkaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseiDataInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.FileIOException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekka2ndInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.ShoruiKanriSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.DateUtil;
//import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.go.jsps.kaken.web.common.LabelValueBean;


/**
 * 審査結果情報管理を行うクラス.<br /><br />
 * 
 * 使用しているテーブル<br /><br />
 * 　・審査結果テーブル:SHINSAKEKKA<br />
 * 　　　審査員割り振り結果情報と申請書の審査結果を管理する。<br /><br />
 * 　・申請データ管理テーブル:SHINSEIDATAKANRI<br />
 * 　　　申請データの情報を管理する。<br /><br />
 * 　・事業情報管理テーブル:JIGYOKNARI<br />
 * 　　　事業の基本情報を管理<br /><br />
 * 　・書類管理テーブル:SHORUIKANRI<br />
 * 　　　事業毎の書類を管理する。<br /><br />
 * 　・ラベルマスタ:MASTER_LABEL<br />
 * 　　　プルダウン等の名称、略称を管理する。<br /><br />
 * 　・総合評価マスタ:MASTER_SOGOHYOKA<br />
 * 　　　総合評価の点数配分を管理する。<br /><br />
 */
public class ShinsaKekkaMaintenance implements IShinsaKekkaMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/**
	 * 審査結果ファイル格納フォルダ.<br /><br />
	 * 
	 * 値は"${shinsei_path}/data/{0}/{1}/shinsa/{2}.{3}"
	 */
	private static final String SHINSEI_KEKKA_FOLDER = 
								ApplicationSettings.getString(ISettingKeys.SHINSEI_KEKKA_FOLDER);	
	
	/**
	 * 申請書削除フラグ（通常）.<br /><br />
	 * 
	 * 値は"0"
	 */
	private static final String FLAG_APPLICATION_NOT_DELETE = IShinseiMaintenance.FLAG_APPLICATION_NOT_DELETE;
	
	/**
	 * 申請書削除フラグ（削除済み）.<br /><br />
	 * 
	 * 値は"1"
	 */
	private static final String FLAG_APPLICATION_DELETE     = IShinseiMaintenance.FLAG_APPLICATION_DELETE;
	

	/**
	 * メールサーバアドレス.<br /><br />
	 * 
	 * 値は"localhost"
	 */
	private static final String SMTP_SERVER_ADDRESS = 
								ApplicationSettings.getString(ISettingKeys.SMTP_SERVER_ADDRESS); 
	/**
	 * 差出人（統一して１つ）.<br /><br />
	 * 
	 * 値はApplicationSettings.propertiesに設定
	 */
	private static final String FROM_ADDRESS = 
								ApplicationSettings.getString(ISettingKeys.FROM_ADDRESS);
	/**
	 * 審査催促期限までの日付.<br /><br />
	 * 
	 * 値は"3"
	 */
	private static final int DATE_BY_SHINSA_KIGEN = 
								ApplicationSettings.getInt(ISettingKeys.DATE_BY_SHINSA_KIGEN);
	/**
	 * メール内容（審査員への審査催促）「件名」.<br /><br />
	 */
	private static final String SUBJECT_SHINSEISHO_SHINSA_SAISOKU = 
								ApplicationSettings.getString(ISettingKeys.SUBJECT_SHINSEISHO_SHINSA_SAISOKU);
	/**
	 * メール内容（審査員への審査催促）「本文」.<br /><br />
	 * 
	 * 値は"${shinsei_path}/settings/mail/shinseisho_shinsa_saisoku.txt"
	 */
	private static final String CONTENT_SHINSEISHO_SHINSA_SAISOKU = 
								ApplicationSettings.getString(ISettingKeys.CONTENT_SHINSEISHO_SHINSA_SAISOKU);
	
	/**
	 * メール内容（審査員が審査完了したとき）「件名」.<br /><br />
	 */
	private static final String SUBJECT_SHINSAKEKKA_JURI_TSUCHI = 
								ApplicationSettings.getString(ISettingKeys.SUBJECT_SHINSAKEKKA_JURI_TSUCHI);

	/**
	 * メール内容（審査員が審査完了したとき）「本文」.<br /><br />
	 * 
	 * 値は"${shinsei_path}/settings/mail/shinsakekka_juri_tsuchi.txt"
	 */
	private static final String CONTENT_SHINSAKEKKA_JURI_TSUCHI = 
								ApplicationSettings.getString(ISettingKeys.CONTENT_SHINSAKEKKA_JURI_TSUCHI);
	
	/**
	 * 審査状況（未完了）.<br /><br />
	 */
	public static final String SHINSAJOKYO_COMPLETE_YET = "0";	

	/**
	 * 審査状況（完了）.<br /><br />
	 */
	public static final String SHINSAJOKYO_COMPLETE = "1";
    
    /**
     * 利害関係該当課題.<br /><br />
     */
    public static final String RIEKISOHAN_FLAG = "riekiSohan";
    
    /**
     * 審査担当分応募課.<br /><br />
     */
    public static final String SINNSA_FLAG = "sinnsa";
    
	
//2006-10-26 張志男 追加ここから
	/**
	 * 利害関係入力完了（完了）.<br /><br />
	 */
	public static final String NYURYOKUJOKYO_COMPLETE = "1";	
	
    /**
     * 利害関係入力完了（未完了）.<br /><br />
     */
    public static final String NYURYOKUJOKYO_COMPLETE_YET = "0";    
//2006-10-26 張志男 追加 ここまで
	

//2006/05/12 追加ここから    
    /**
     * 総合評点(未入力).<br /><br />
     */
    public static final String SHINSAKEKKA_KEKKA_TEN_MI = "-1"; 
    
    /**
     * 総合評点(完了).<br /><br />
     */
    public static final String SHINSAKEKKA_KEKKA_TEN_KANRYOU = "0";   								
//苗　追加ここまで
    
	/**
	 * ログ.<br /><br />
	 */
	protected static Log log = LogFactory.getLog(ShozokuMaintenance.class);
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public ShinsaKekkaMaintenance() {
		super();
	}
	
	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------	
	/**
	 * 審査期限のチェック.<br /><br />
	 * 
	 * 現在の日付が当該事業の審査期限を過ぎていないかチェックする。<br />
	 * 審査期限がNULLの場合は、TRUEを返す。<br />
	 * 現在日付は、WASから取得。<br /><br />
	 * 
	 * 以下のSQLを実行し、当該事業の事業情報を取得する。<br />
	 * （バインド変数はSQLの下の表を参照）<br /><br />
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID			-- 事業ID
	 *     ,A.NENDO			-- 年度
	 *     ,A.KAISU			-- 回数
	 *     ,A.JIGYO_NAME			-- 事業名
	 *     ,A.JIGYO_KUBUN		-- 事業区分
	 *     ,A.SHINSA_KUBUN		-- 審査区分
	 *     ,A.TANTOKA_NAME		-- 業務担当課
	 *     ,A.TANTOKAKARI		-- 業務担当係名
	 *     ,A.TOIAWASE_NAME		-- 問い合わせ先担当者名
	 *     ,A.TOIAWASE_TEL		-- 問い合わせ先電話番号
	 *     ,A.TOIAWASE_EMAIL		-- 問い合わせ先E-mail
	 *     ,A.UKETUKEKIKAN_START		-- 学振受付期間（開始）
	 *     ,A.UKETUKEKIKAN_END		-- 学振受付期間（終了）
	 *     ,A.SHINSAKIGEN		-- 審査期限
	 *     ,A.TENPU_NAME			-- 添付文書名
	 *     ,A.TENPU_WIN			-- 添付ファイル格納フォルダ（Win）
	 *     ,A.TENPU_MAC			-- 添付ファイル格納フォルダ（Mac）
	 *     ,A.HYOKA_FILE_FLG		-- 評価用ファイル有無
	 *     ,A.HYOKA_FILE			-- 評価用ファイル格納フォルダ
	 *     ,A.KOKAI_FLG			-- 公開フラグ
	 *     ,A.KESSAI_NO			-- 公開決裁番号
	 *     ,A.KOKAI_ID			-- 公開確定者ID
	 *     ,A.HOKAN_DATE			-- データ保管日
	 *     ,A.YUKO_DATE			-- 保管有効期限
	 *     ,A.BIKO			-- 備考
	 *     ,A.DEL_FLG			-- 削除フラグ
	 * FROM
	 *     JIGYOKANRI A
	 * WHERE
	 *     JIGYO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数shinseiDataInfoの変数jigyoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * 取得した事業情報の審査期限がnullだった場合、trueを返却して完了となる。<br /><br />
	 * 
	 * 審査期限がnullではなかった場合、現在の日付と審査期限を比べる。<br />
	 * 期限を過ぎていたらfalseを、過ぎていなければtrueを返却する。<br /><br />
	 * 
	 * @param userInfo
	 * @param shinseiDataInfo
	 * @return [false]審査期限を過ぎている場合 [true]審査期限がnull又は審査期限を過ぎていない場合
	 * @throws ApplicationException
	 */
	private boolean checkShinsaKigen(
			UserInfo userInfo, 
			ShinseiDataInfo shinseiDataInfo)
		throws ApplicationException
	{
		//-----当該事業の事業情報を取得する
		JigyoKanriPk pkInfo = new JigyoKanriPk(shinseiDataInfo.getJigyoId());
		JigyoKanriInfo jigyoKanriInfo = null;
		try{
			IJigyoKanriMaintenance jigyoMainte = new JigyoKanriMaintenance();
			jigyoKanriInfo = jigyoMainte.select(userInfo, pkInfo);
		} catch (ApplicationException e) {
			throw new ApplicationException(
				"事業情報管理管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		}
		
		//審査期限の取得
		if(jigyoKanriInfo.getShinsaKigen() == null){
			//審査期限がnullだったらtrue
			return true;
		}
		
		DateUtil shinsaEnd = new DateUtil(jigyoKanriInfo.getShinsaKigen());
		DateUtil now = new DateUtil();
		
		//現在日付と審査期限を比較
		boolean bFlag = true;
		int hi = now.getElapse(shinsaEnd);
		if(hi < 0){
			//審査期限を過ぎている場合
			bFlag = false;
		}
				
		return bFlag;		
		
	}
	
	//---------------------------------------------------------------------
	// implement IShinsaKekkaMaintenance
	//---------------------------------------------------------------------
	/**
	 * 審査対象事業情報を持つPageの取得.<br /><br />
	 * 
	 * 以下のSQL文を発行する。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.JIGYO_ID,
	 * 	A.SHINSAIN_NO,
	 * 	A.JIGYO_NAME,
	 * 	B.NENDO,
	 * 	B.KAISU,
	 * 	C.SHINSAKIGEN,
	 * 	C.SHINSAKIGEN_FLAG,
	 * 	COUNT(A.JIGYO_ID) COUNT,
	 * 	SUM(DECODE(A.SHINSA_JOKYO,1,1,0))
	 * 	SHINSA_JYOKYO_COUNT
	 * FROM(
	 * 	SELECT
	 * 		*
	 * 	FROM
	 * 		SHINSAKEKKA
	 * 	WHERE
	 * 		SHINSAIN_NO = ?				--審査員番号
	 * 		AND JIGYO_KUBUN = ?			--事業区分
	 * ) A
	 * INNER JOIN(
	 * 	SELECT
	 * 		*
	 * 	FROM
	 * 		SHINSEIDATAKANRI
	 * 	WHERE
	 * 		DEL_FLG=0
	 * 		AND JOKYO_ID IN('10','11')			--審査状況が10又は11のもの
	 * ) B
	 * ON A.SYSTEM_NO = B.SYSTEM_NO
	 * INNER JOIN(
	 * 	SELECT
	 * 		JIGYO_ID,
	 * 		JIGYO_NAME,
	 * 		NENDO,
	 * 		KAISU,
	 * 		SHINSAKIGEN,
	 * 		DECODE(
	 * 			SIGN(TO_DATE(TO_CHAR(SHINSAKIGEN,'YYYY/MM/DD'),'YYYY/MM/DD')
	 * 			- TO_DATE(TO_CHAR(SYSDATE, 'YYYY/MM/DD'),'YYYY/MM/DD') )
	 * 			,0 ,'TRUE'			--現在時刻と同じ場合
	 * 			,1 ,'TRUE'			--現在時刻の方が審査期限より前
	 * 			,-1,'FALSE'			--現在時刻の方が審査期限より後
	 * 		)
	 * 		SHINSAKIGEN_FLAG				--審査期限到達フラグ
	 * 	FROM
	 * 		JIGYOKANRI
	 * ) C
	 * ON A.JIGYO_ID = C.JIGYO_ID
	 * 	WHERE
	 * 		A.SHINSA_JOKYO = '0'			--審査状況が「0：未完了」の場合
	 * 		OR(					--または
	 * 			(C.SHINSAKIGEN_FLAG = 'TRUE'
	 * 			OR C.SHINSAKIGEN_FLAG IS NULL)	--審査期限内または審査期限なしで
	 * 			AND				--かつ
	 * 			A.SHINSA_JOKYO = '1'		--審査状況が「1：完了」の場合
	 * 		)
	 * 	GROUP BY
	 * 		A.JIGYO_ID
	 * 		,A.SHINSAIN_NO
	 * 		,A.JIGYO_NAME
	 * 		,B.NENDO
	 * 		,B.KAISU
	 * 		,C.SHINSAKIGEN
	 * 	ORDER BY
	 * 		A.JIGYO_ID				--事業IDの昇順</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数userInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数userInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 　・審査状況10…一次審査中<br/>
	 * 　・審査状況11…一次審査完了<br/><br/>
	 * 
	 * 取得した審査対象事業情報を、列名をキーにMapに格納し、Listにまとめた上で、Pageにセットして返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo SearchInfo
	 * @return 審査対象事業情報を持つPage
	 * @throws ApplicationException
	 */
	public Page searchShinsaJigyo(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException {
		
        String shinsainNo = userInfo.getShinsainInfo().getShinsainNo();     //審査員番号         
        String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun();		//事業区分
//2006/04/25 追加ここから
        Set shinsaiSet = JigyoKubunFilter.Convert2ShinsainJigyoKubun(jigyoKubun) ; //審査員の事業区分のSet
        String jigyoKubunTemp = (String)shinsaiSet.iterator().next();
        
        Set shinseiSet = JigyoKubunFilter.Convert2ShinseishoJigyoKubun(jigyoKubunTemp) ; //申請の事業区分のSet
        //CSVを取得
        String shinseiJigyoKubun =  StringUtil.changeIterator2CSV(shinseiSet.iterator(),true);
//苗　追加ここまで        
		
		//-----------------------
		// SQL文の作成
		//-----------------------
		String query = 
			"SELECT" 
			+ " A.JIGYO_ID,"
			+ " A.SHINSAIN_NO,"
			+ " A.JIGYO_NAME,"
//2006/04/18 追加ここから
			+ " A.JIGYO_KUBUN,"
//苗　追加ここまで	

//			2006/10/24 易旭 追加ここから
//			+ " A.NYURYOKU_JOKYO,"
			+ " SUM( DECODE( NVL(A.NYURYOKU_JOKYO, '0') , 1 , 1 , 0 ) ) NYURYOKU_JOKYO_COUNT ," //利害関係用
//			2006/10/24 易旭 追加ここまで
			
			+ " B.NENDO,"
			+ " B.KAISU,"
			+ " C.SHINSAKIGEN,"
//          2006/10/24 易旭 追加ここから
			+ " C.RIGAI_KIKAN_END,"             //利害関係入力期限
//          2006/10/24 易旭 追加ここまで
			+ " C.SHINSAKIGEN_FLAG,"
			+ " COUNT(A.JIGYO_ID) COUNT,"
			+ " SUM( DECODE( A.SHINSA_JOKYO , 1 , 1 , 0 ) ) SHINSA_JYOKYO_COUNT "
			+ " FROM "
			+ "  (SELECT * FROM SHINSAKEKKA"
			+ "   WHERE SHINSAIN_NO = ?"		//審査員番号
//			+ "   AND JIGYO_KUBUN = ?"			//事業区分
//2006/04/24 追加ここから
            + "   AND JIGYO_KUBUN IN ("
            + shinseiJigyoKubun
            + "   )"  
//苗　追加ここまで              
//2006/04/23 削除ここから （2006/04/18　苗　追加しました）
//			if (jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)) {
//				query = query + "   AND JIGYO_KUBUN = ?"; //事業区分
//			} else {
//				String jigyoKbns = StringUtil.changeArray2CSV(IJigyoKubun.SHINSA_KANOU_JIGYO_KUBUN, true);
//				query = query + "   AND JIGYO_KUBUN IN (" + jigyoKbns + ")"; //事業区分
//			}
//            query = query
//苗　削除ここまで             
			+ "  ) A"
			+ " INNER JOIN "
			+ "  (SELECT * FROM SHINSEIDATAKANRI"
			+ "   WHERE DEL_FLG=0"
			+ "   AND JOKYO_ID IN('" 
			+ 	  StatusCode.STATUS_1st_SHINSATYU								//申請状況が[10]	
			+ "', '" + StatusCode.STATUS_1st_SHINSA_KANRYO +  "')"			//申請状況が[11]
			+ "   ) B"
			+ " ON A.SYSTEM_NO = B.SYSTEM_NO"
			+ " INNER JOIN" 
			+ "  (SELECT"
			+ "   JIGYO_ID,"
			+ "   JIGYO_NAME,"
			+ "   NENDO,"
			+ "   KAISU,"
			+ "   SHINSAKIGEN,"
			+ "   RIGAI_KIKAN_END,"
			+ "   DECODE"
			+ "   ("
			+ "    SIGN( TO_DATE(TO_CHAR(SHINSAKIGEN, 'YYYY/MM/DD'),'YYYY/MM/DD')  - TO_DATE(TO_CHAR(SYSDATE, 'YYYY/MM/DD'),'YYYY/MM/DD') )"
			+ "    ,0 , 'TRUE'"								//現在時刻と同じ場合
			+ "    ,1 , 'TRUE'"								//現在時刻の方が審査期限より前
			+ "    ,-1, 'FALSE'"							//現在時刻の方が審査期限より後
			+ "   ) SHINSAKIGEN_FLAG "						//審査期限到達フラグ
			+ "   FROM JIGYOKANRI"
			+ "   ) C"
			+ " ON A.JIGYO_ID = C.JIGYO_ID"
			+ " WHERE"
//			+ " NOT(C.SHINSAKIGEN_FLAG = 'FALSE'"	//審査期限過ぎてて←審査期限がNULLのやつがFALSE以外にヒットしてくれない
//			+ " AND"								//かつ
//			+ " A.SHINSA_JOKYO = '1')"				//審査状況が「1：完了」ではない場合
			+ " A.SHINSA_JOKYO = '0'"				//審査状況が「0：未完了」の場合
			+ " OR"										//または
			+ " ("
			+ " (C.SHINSAKIGEN_FLAG = 'TRUE' OR C.SHINSAKIGEN_FLAG IS NULL)"		//審査期限内または審査期限なしで
			+ " AND"									//かつ
			+ " A.SHINSA_JOKYO = '1')"			//審査状況が「1：完了」の場合
			+ " GROUP BY A.JIGYO_ID,A.SHINSAIN_NO,A.JIGYO_NAME,B.NENDO,B.KAISU,C.SHINSAKIGEN,A.JIGYO_KUBUN,C.RIGAI_KIKAN_END "
			+ " ORDER BY A.JIGYO_ID";	//事業IDの昇順		
			
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		//------------------------
		
		//-----------------------
		// ページ取得
		//-----------------------
		Connection connection = null;
		try {
            
            connection = DatabaseUtil.getConnection();
            
            return SelectUtil.selectPageInfo(connection, searchInfo, query, new String[]{ shinsainNo });
            
//2006/04/24 削除ここから（2006/04/18　追加しました）            
//            if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
//            	return SelectUtil.selectPageInfo(connection, searchInfo, query, new String[]{shinsainNo, jigyoKubun});
//            } else {  
//            	return SelectUtil.selectPageInfo(connection, searchInfo, query, new String[]{ shinsainNo });
//            }
//苗　削除ここまで			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"審査対象事業一覧データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * 書類情報の取得.<br /><br />
	 * 
	 * 自クラスのメソッドSearchShinsaJigyo(UserInfo userInfo, SearchInfo searchInfo)を呼び出して、
	 * Pageを取得する。その後、以下のSQL文を発行して、書類情報のListを取得する。<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	SHORUIKANRI A
	 * WHERE
	 * 	DEL_FLG = 0
	 * 
	 * 		<b><span style="color:#002288">－－動的検索条件－－</span></b>
	 * 
	 * ORDER BY
	 * 	SYSTEM_NO</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業ID</td><td>JigyoId</td><td>AND A.JIGYO_ID ='事業ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>対象者ID</td><td>TaishoId</td><td>4(審査員をあらわす)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>SystemNo</td><td>AND A.SYSYEM_NO ='システム受付番号'</td></tr>
	 * </table><br/>
	 * 
	 * 取得したListをMapに格納し、それをArrayListに加える。<br/><br/>
	 * 
	 * このSQLを発行する処理を、取得したPageの要素がなくなるまで繰り返す。
	 * 最後に、このArrayListをPageに格納して、返却する。<br/><br/>
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo SearchInfo
	 * @return 書類情報をもつPage
	 * @throws ApplicationException
	 */
	public Page getShinsaJigyo(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException {

		Page page = searchShinsaJigyo(userInfo, searchInfo);
		
		List newList = new ArrayList();
		
		Iterator iterator = (page.getList()).iterator();
		while(iterator.hasNext()){
			Map map = (Map)iterator.next();
			String jigyoid = (String) map.get("JIGYO_ID");
			JigyoKanriMaintenance maintenance = new JigyoKanriMaintenance();
			ShoruiKanriSearchInfo shoruiInfo = new ShoruiKanriSearchInfo();
			shoruiInfo.setJigyoId(jigyoid);
			shoruiInfo.setTaishoId(ITaishoId.SHINSAIN);
			List list = maintenance.search(userInfo, shoruiInfo);
			map.put("SHORUI_INFO", list);
			newList.add(map);
		}
		
		page.setList(newList);
		
		return page;
	}

	/**
	 * 添付ファイル(評価ファイル)の取得.<br /><br />
	 * 
	 * 以下のSQL文より添付ファイルパスを取得し、ファイルを読み込んでファイルリソースを返却する。<br />
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SYSTEM_NO			--システム受付番号
	 * 	,A.UKETUKE_NO			--申請番号
	 * 	,A.SHINSAIN_NO			--審査員番号
	 * 	,A.JIGYO_KUBUN			--事業区分
	 * 	,A.SEQ_NO				--シーケンス番号
	 * 	,A.SHINSA_KUBUN			--審査区分
	 * 	,A.SHINSAIN_NAME_KANJI_SEI		--審査員名（漢字－姓）
	 * 	,A.SHINSAIN_NAME_KANJI_MEI		--審査員名（漢字－名）
	 * 	,A.NAME_KANA_SEI			--審査員名（フリガナ－姓）
	 * 	,A.NAME_KANA_MEI			--審査員名（フリガナ－名）
	 * 	,A.SHOZOKU_NAME			--審査員所属機関名
	 * 	,A.BUKYOKU_NAME			--審査員部局名
	 * 	,A.SHOKUSHU_NAME			--審査員職名
	 * 	,A.JIGYO_ID			--事業ID
	 * 	,A.JIGYO_NAME			--事業名
	 * 	,A.BUNKASAIMOKU_CD			--細目番号
	 * 	,A.EDA_NO				--枝番				
	 * 	,A.CHECKDIGIT			--チェックデジット
	 * 	,A.KEKKA_ABC			--総合評価（ABC）
	 * 	,A.KEKKA_TEN			--総合評価（点数）
	 * 	,A.COMMENT1			--コメント1			
	 * 	,A.COMMENT2			--コメント2
	 * 	,A.COMMENT3			--コメント3
	 * 	,A.COMMENT4			--コメント4
	 * 	,A.COMMENT5			--コメント5
	 * 	,A.COMMENT6			--コメント6
	 * 	,A.KENKYUNAIYO			--研究内容
	 * 	,A.KENKYUKEIKAKU			--研究計画		
	 * 	,A.TEKISETSU_KAIGAI		--適切性-海外
	 * 	,A.TEKISETSU_KENKYU1		--適切性-研究（1）
	 * 	,A.TEKISETSU			--適切性			
	 * 	,A.DATO				--妥当性
	 * 	,A.SHINSEISHA			--研究代表者
	 * 	,A.KENKYUBUNTANSHA			--研究分担者
	 * 	,A.HITOGENOMU			--ヒトゲノム
	 * 	,A.TOKUTEI			--特定胚
	 * 	,A.HITOES				--ヒトES細胞
	 * 	,A.KUMIKAE			--遺伝子組換え実験
	 * 	,A.CHIRYO				--遺伝子治療臨床研究			
	 * 	,A.EKIGAKU			--疫学研究
	 * 	,A.COMMENTS			--コメント
	 * 	,A.TENPU_PATH			--添付ファイル格納パス			
	 * 	,DECODE(
	 * 		NVL(A.TENPU_PATH,'null') 
	 * 		,'null','FALSE'		--添付ファイル格納パスがNULL
	 * 		,      'TRUE'		--添付ファイル格納パスがNULL以外
	 * 	)TENPU_FLG			--添付ファイル格納フラグ
	 * 	,A.SHINSA_JOKYO			--審査状況
	 * 	,A.BIKO				--備考
	 * FROM
	 * 	SHINSAKEKKA A
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * 	AND SHINSAIN_NO = ?
	 * 	AND JIGYO_KUBUN = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>第二引数downloadPkの変数SystemNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数downloadPkの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数downloadPkの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値から、添付ファイル格納パスを取り出す。
	 * この値がnull又は空文字列だった場合には例外をthrowする。<br /><br />
	 * 
	 * パスの値のファイルを読み込み、ファイルリソースを返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param downloadPk
	 * @return FileResource
	 * @throws ApplicationException
	 */
	public FileResource getHyokaFileRes(UserInfo userInfo, ShinsaKekkaPk downloadPk) throws ApplicationException {

		FileResource fileRes = null;
			
				
		//DBコネクションの取得
		Connection connection = null;		
		try{
			
			ShinsaKekkaInfo shinsaKekkaInfo = null;
			try{
				connection = DatabaseUtil.getConnection();
				ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);
				shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfo(connection, downloadPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}

			String filePath = shinsaKekkaInfo.getTenpuPath();
			if(filePath == null || filePath.equals("")){
				throw new FileIOException(
					"ファイルパスが不正です。ファイルパス'" + filePath + "'");			
			}
			try{
				File file = new File(filePath);
				fileRes = FileUtil.readFile(file);
			}catch(FileNotFoundException e){
				throw new FileIOException(
					"ファイルが見つかりませんでした。",			
					e);
			}catch(IOException e){
				throw new FileIOException(
					"ファイルの入出力中にエラーが発生しました。",
					e);
			}
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		return fileRes;
	}

	/**
	 * Mapの取得.<br /><br />
	 * 
	 * Mapを取得する自クラスのメソッド、<br />
	 * selectShinsaKekkaTantoList(userInfo,jigyoId,null,searchInfo)<br />
	 * を呼び出し、取得したMapをそのまま返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param jigyoId String
	 * @param searchInfo SearchInfo
	 * @return Map
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Map selectShinsaKekkaTantoList(UserInfo userInfo, String jigyoId, SearchInfo searchInfo) throws NoDataFoundException, ApplicationException {

		return selectShinsaKekkaTantoList(userInfo,
											jigyoId,
											null,
                                            null,//2006/10/27　苗　追加　このメッソドは呼び出さないから、nullを追加しました
                                            null,//2006/10/27　苗　追加　このメッソドは呼び出さないから、nullを追加しました
											searchInfo);
	}

	/**
	 * Mapの取得.<br /><br />
	 * 
	 * 1.以下のSQL文を発行して、審査担当申請一覧ページ情報を取得する。<br />
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SYSTEM_NO,				--システム受付番号
	 * 	A.UKETUKE_NO,				--申請番号
	 * 	A.SHINSAIN_NO,				--審査員番号
	 * 	A.JIGYO_ID,				--事業ID
	 * 	SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD,	--事業コード
	 * 	A.JIGYO_NAME,				--事業名
	 * 	A.BUNKASAIMOKU_CD,			--分科細目コード
	 * 	A.KEKKA_ABC,				--総合評価（ABC）
	 * 	A.KEKKA_TEN,				--総合評価（点数）
	 * 	A.KENKYUNAIYO,				--研究内容
	 * 	A.KENKYUKEIKAKU,			--研究計画
	 * 	A.TEKISETSU_KAIGAI,			--適切性-海外
	 * 	A.TEKISETSU_KENKYU1,		--適切性-研究(1)
	 * 	A.TEKISETSU,				--適切性
	 * 	A.DATO,					--妥当性
	 * 	A.COMMENTS,				--コメント
	 * 	A.SHINSA_JOKYO,				--審査状況
	 * 	B.SHINSEISHA_ID,			--申請者ID
	 * 	B.NAME_KANJI_SEI,			--申請者氏名（漢字等-姓）
	 * 	B.NAME_KANJI_MEI,			--申請者氏名（漢字等-名）
	 * 	B.SHOZOKU_CD,				--所属機関コード
	 * 	B.SHOZOKU_NAME_RYAKU,		--所属機関名（略称）
	 * 	B.BUKYOKU_NAME_RYAKU,		--部局名（略称）
	 * 	B.SHOKUSHU_NAME_RYAKU,		--職名（略称）
	 * 	B.KADAI_NAME_KANJI,		--研究課題名又はセミナー名(和文）
	 * 	B.SAIMOKU_NAME,			--細目名
	 * 	DECODE(
	 * 		NVL(B.SUISENSHO_PATH,'null') 
	 * 		,'null','FALSE'			--推薦書パスがNULLのとき
	 * 		,      'TRUE'			--推薦書パスがNULL以外のとき
	 * 	) SUISENSHO_FLG, 			--推薦書登録フラグ
	 * 	B.BUNKATSU_NO,				--分割番号
	 * 	B.BUNTANKIN_FLG,			--分担金の有無
	 * 	B.KAIGAIBUNYA_CD,			--海外分野コード
	 * 	B.KAIGAIBUNYA_NAME_RYAKU,	--海外分野名（略称）
	 * 	B.SHINSEI_FLG_NO,		--研究計画最終年度前年度の応募
	 * 	B.JOKYO_ID,				--申請状況ID
	 * 	C.SHINSAKIGEN,			--審査期限
	 * 	C.NENDO,				--年度
	 * 	C.KAISU,				--回数
	 * 	DECODE(
	 * 		SIGN( TO_DATE(TO_CHAR(C.SHINSAKIGEN,'YYYY/MM/DD'),
	 * 			'YYYY/MM/DD')
	 * 			- TO_DATE(TO_CHAR(SYSDATE,'YYYY/MM/DD'),
	 * 			'YYYY/MM/DD')
	 * 		)
	 * 		,0 ,'TRUE'			--現在時刻と同じ場合
	 * 		,1 ,'TRUE'			--現在時刻の方が審査期限より前
	 * 		,-1,'FALSE'			--現在時刻の方が審査期限より後
	 * 	) SHINSAKIGEN_FLAG				--審査期限到達フラグ
	 * FROM(
	 * 	SELECT
	 * 		*
	 * 	FROM
	 * 		SHINSAKEKKA
	 * 	WHERE 
	 * 	SHINSAIN_NO = ?				--当該審査員番号
	 * 	AND JIGYO_KUBUN = ?			--当該事業区分
	 * 	AND JIGYO_ID = ?			--当該事業ID
	 * 	) A,
	 * 	(
	 * 	SELECT
	 * 		*
	 * 	FROM
	 * 		SHINSEIDATAKANRI
	 * 	WHERE
	 * 		(JOKYO_ID = '10' OR JOKYO_ID = '11')
	 * 			--当該事業IDが10または11のもの
	 * 		AND DEL_FLG = '0'			--削除されてないもの
	 * 	) B,
	 * 	JIGYOKANRI C
	 * WHERE
	 * 	A.SYSTEM_NO = B.SYSTEM_NO
	 * 	AND A.JIGYO_ID = C.JIGYO_ID
	 * 
	 * 	<b><span style="color:#002288">－－動的検索条件1－－</span></b>
	 * 
	 * ORDER BY
	 * 
	 * 	<b><span style="color:#002288">－－動的検索条件2－－</span></b>
	 * 
	 * 	A.UKETUKE_NO</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第一引数userInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第一引数userInfoの変数JigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数jigyoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 引数searchInfoの値によって検索条件が動的に変化する。<br/><br/>
	 * 
	 * 
	 * <b><span style="color:#002288">動的検索条件1</span></b><br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>条件</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>総合評価（点数）</td><td>kekkaTen</td><td>-1(※1)</td><td>AND A.KEKKA_TEN IS NULL</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>総合評価（点数）</td><td>kekkaTen</td><td>5～-</td><td>AND A.KEKKA_TEN = ?</td></tr>
	 * </table><br />
	 * 
	 * 
	 * <b><span style="color:#002288">動的検索条件2</span></b><br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>条件</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>jigyoKubun</td><td>4(※2)</td><td>A.BUNKASAIMOKU_CD,</td></tr>
	 * </table><br />
	 * 
	 * 　・(※1)未入力を示す<br />
	 * 　・(※2)基盤を示す<br /><br />
	 * 
	 * 
	 * 
	 * 
	 * 2.以下のSQL文を発行して、プルダウン用のラベル名を持つListを取得する。取得するのは、<br />
	 * 　・適切性-海外　　　(TEKISETSU_KAIGAI)<br />
	 * 　・適切性-研究 (1)　(TEKISETSU_KENKYU1)<br />
	 * 　・適切性　　　　　　(TEKISETSU)<br />
	 * 　・妥当性　　　　　　(DATO)<br />
	 * の４種類なので、SQL文は４回発行する。()の値は、それぞれのラベル区分の値。<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.ATAI
	 * 	, A.NAME
	 * 	, A.RYAKU
	 * 	, A.SORT
	 * 	, A.BIKO
	 * FROM
	 * 	MASTER_LABEL A
	 * WHERE
	 * 	A.LABEL_KUBUN = ?
	 * 	AND A.SORT != 0
	 * ORDER BY
	 * 	SORT</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>ラベル区分</td><td>前述したラベル区分の値を使用する。</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 3.(1)1で取得したPageからListを取り出す。<br /><br />
	 * 
	 * 　(2)List内のMapからそれぞれ４つの値<br />
	 * 　　　・適切性-海外<br />
	 * 　　　・適切性-研究 (1)<br />
	 * 　　　・適切性<br />
	 * 　　　・妥当性<br />
	 * 　　を取得する。<br /><br />
	 * 
	 * 　(3)この値を用いて、2で取得したListからラベル名を、自クラスのメソッドgetLabelName()を呼び出して取り出す。<br /><br />
	 * 
	 * 　(4)このラベル名の値を、先ほどのMapに格納する。<br /><br />
	 * 
	 * 　(5)(1)で取り出したListから、(2)で取り出したMapを削除し、(4)のMapをそこに格納する。<br />
	 * 　　(ラベル名を持つMapで上書きする)<br /><br />
	 * 
	 * 　(6)(2)～(5)を、Mapの数だけ繰り返す。<br /><br /><br />
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 4.申請状況の確認<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	COUNT(*)		--申請番号
	 * FROM
	 * 	SHINSAKEKKA A,
	 * 	(
	 * 	SELECT
	 * 		SYSTEM_NO
	 * 	FROM
	 * 		SHINSEIDATAKANRI
	 * 	WHERE
	 * 		DEL_FLG=0
	 * 		AND (JOKYO_ID = '10' OR JOKYO_ID = '11')
	 * 
	 * 	<b><span style="color:#002288">－－動的検索条件1－－</span></b>
	 * 
	 * 	) B
	 * WHERE
	 * 	A.SYSTEM_NO=B.SYSTEM_NO
	 * 
	 * 	<b><span style="color:#002288">－－動的検索条件2－－</span></b>
	 * 
	 * </pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * 引数searchInfoの値によって検索条件が動的に変化する。<br/><br/>
	 * 
	 * 
	 * 
	 * <b><span style="color:#002288">動的検索条件1</span></b><br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>システム番号</td><td>systemNo</td><td>AND SYSTEM_NO= ?</td></tr>
	 * </table><br/>
	 * 
	 * (※)システム番号には、nullを与えている。 <br/><br/>
	 * 
	 * 
	 * <b><span style="color:#002288">動的検索条件2</span></b><br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>審査員番号</td><td>shinsainNo</td><td>AND A.SHINSAIN_NO ='審査員番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>jigyoKubun</td><td>AND A.JIGYO_KUBUN = '事業区分'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業ID</td><td>jigyoId</td><td>AND A.JIGYO_ID = '事業ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>審査状況</td><td>shinsaJokyo</td><td>AND A.SHINSA_JOKYO= '審査状況'</td></tr>
	 * </table><br/>
	 * 
	 * (※)審査状況には、0を与えている。 <br/><br/>
	 * 
	 * 審査状況が「0:未完了」のデータがなかったら、
	 * 審査完了フラグのString"<b>shinsaCompleteFlg</b>"に"TRUE"を持たせる。
	 * データがあれば"FALSE"を持たせる。<br /><br /><br />
	 * 
	 * 
	 * 
	 * 5.Mapに値を格納していく。<br /><br />
	 * 
	 * HashMapのオブジェクト"map"を作成し、以下の値を格納する。<br /><br />
	 * 　・3で更新したPage　　…キーは"key_shinsatanto_list"<br />
	 * 　・4で取得したString"<b>shinsaCompleteFlg</b>"　　　…キーは"key_shinsacomplete_flg"<br /><br />
	 * 
	 * この"map"を返却して完了となるが、
	 * 事業区分が「4:基盤」である場合のみ、
	 * 総合評点のListも"map"に格納する。<br /><br />
	 * 
	 * 
	 * まず、以下のSQL文を発行して、総合評点リストを取得する。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.KEKKA_TEN			--総合評価（点数）
	 * 	,COUNT(*) COUNT			--件数
	 * FROM
	 * 	SHINSAKEKKA A,
	 * 	(
	 * 	SELECT
	 * 		*
	 * 	FROM
	 * 		SHINSEIDATAKANRI
	 * 	WHERE(
	 * 		JOKYO_ID = '10'	OR JOKYO_ID = '11')
	 * 		AND DEL_FLG = '0'		--削除されてないもの
	 * 	) B
	 * WHERE
	 * 	A.SYSTEM_NO = B.SYSTEM_NO
	 * 	AND A.SHINSAIN_NO = ?
	 * 	AND A.JIGYO_KUBUN = ?
	 * 	AND A.JIGYO_ID = ?
	 * GROUP BY
	 * 	A.KEKKA_TEN
	 * ORDER BY
	 * 	NVL(REPLACE(A.KEKKA_TEN, '-', '0'), '-1') DESC</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第一引数userInfoの変数shinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第一引数userInfoの変数JigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数jigyoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得したList内には、Mapに、各キーでkekkaTenとcountを持っている。<br />
	 * これを別のMap"hyotenMap"にキー…kekkaTen、値…countで格納しなおす。<br />
	 * この作業を、List内のMapの数だけ繰り返す。<br /><br />
	 * 
	 * 最後に、すべての件数をキーを"0"で"hyotenMap"に格納する。<br />
	 * この"hyotenMap"をキー"key_sogohyoten_list"にて"map"に格納し、返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param jigyoId String
	 * @param kekkaTen String
     * @param countKbn String
     * @param rigai String
	 * @param searchInfo SearchInfo
	 * @return Map
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Map selectShinsaKekkaTantoList(
			UserInfo userInfo,
			String jigyoId,
			String kekkaTen,
            String countKbn,
            String rigai,
			SearchInfo searchInfo)
		throws NoDataFoundException, ApplicationException {

		String shinsainNo = userInfo.getShinsainInfo().getShinsainNo();		//審査員番号
		String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun();		//事業区分
		
		//DBコネクションの取得
		Connection connection = null;	
		try{
			connection = DatabaseUtil.getConnection();
			ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);			
			//---審査担当申請一覧ページ情報
			Page pageInfo = null;
			try {
				pageInfo = dao.selectShinsaKekkaTantoList(
											connection, 
											shinsainNo,
											jigyoKubun,
											jigyoId,
											kekkaTen,
                                            rigai,
											searchInfo);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"審査結果データ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			} catch (NoDataFoundException e){
				//0件のページオブジェクトを生成
				pageInfo = Page.EMPTY_PAGE;			
			}

			//ラベル名
			List tekisetsuKaigaiList = null;		//適切性-海外
			List tekisetsuKenkyu1List = null;		//適切性-研究（1）
			List tekisetsuList = null;				//適切性			
			List datoList = null;					//妥当性
			List jinkenList = null;				    //人権の保護・法令等の遵守	
			List buntankinList = null;				//分担金配分
			
			
			try{
				String[] labelKubun = new String[]{ILabelKubun.TEKISETSU_KAIGAI,
													ILabelKubun.TEKISETSU_KENKYU1,
													ILabelKubun.TEKISETSU,
													ILabelKubun.DATO,
													ILabelKubun.JINKEN,
													ILabelKubun.BUNTANKIN};
				List bothList = new LabelValueMaintenance().getLabelList(labelKubun);	//4つのラベルリスト
				tekisetsuKaigaiList = (List)bothList.get(0);		
				tekisetsuKenkyu1List = (List)bothList.get(1);		
				tekisetsuList = (List)bothList.get(2);
				datoList = (List)bothList.get(3);				
				jinkenList = (List)bothList.get(4);
				buntankinList = (List)bothList.get(5);	
							
			}catch(ApplicationException e){
				throw new ApplicationException(
					"ラベルマスタ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
									
			List list = pageInfo.getList();
			for(int i = 0;i< list.size();i ++){
				Map lineMap = (Map)list.get(i);
				//値を取得
				String tekisetsuKaigaiValue = (String) lineMap.get("TEKISETSU_KAIGAI");		//適切性-海外
				String tekisetsuKenkyu1Value = (String) lineMap.get("TEKISETSU_KENKYU1");	//適切性-研究（1）
				String tekisetsuValue = (String) lineMap.get("TEKISETSU");					//適切性
				String datoValue = (String) lineMap.get("DATO");							//妥当性
				String jinkenValue = (String) lineMap.get("JINKEN");						//人権の保護・法令等の遵守	
				String buntankinValue = (String) lineMap.get("BUNTANKIN");					//分担金配分
				
				//ラベル名を取得
				String tekisetsuKaigaiLabel = getLabelName(tekisetsuKaigaiList, tekisetsuKaigaiValue);
				String tekisetsuKenkyu1Label = getLabelName(tekisetsuKenkyu1List, tekisetsuKenkyu1Value);
				String tekisetsuLabel =  getLabelName(tekisetsuList, tekisetsuValue);
				String datoLabel = getLabelName(datoList, datoValue);
				String jinkenLabel = getLabelName(jinkenList, jinkenValue);
				String buntankinLabel = getLabelName(buntankinList, buntankinValue);
				
				//ラベル名をセット
				lineMap.put("TEKISETSU_KAIGAI_LABEL", tekisetsuKaigaiLabel);
				lineMap.put("TEKISETSU_KENKYU1_LABEL", tekisetsuKenkyu1Label);
				lineMap.put("TEKISETSU_LABEL", tekisetsuLabel);
				lineMap.put("DATO_LABEL", datoLabel);
				lineMap.put("JINKEN_LABEL",jinkenLabel);
				lineMap.put("BUNTANKIN_LABEL", buntankinLabel);
				
				//リストから削除して追加しなおす
				list.remove(i);
				list.add(i, lineMap);
				
			}
			
//2006/10/27 苗　修正ここから            
//			//---審査状況が「0:未完了」のデータがあるかどうかを確認
//			int count = 0;
//			try {
//				count = dao.countShinsaKekkaInfo(
//										connection,
//										shinsainNo,
//										jigyoKubun,
//										null,
//										jigyoId,
//										ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET									,null
//										);
//			} catch (DataAccessException e) {
//				throw new ApplicationException(
//					"審査結果データ検索中にDBエラーが発生しました。",
//					new ErrorInfo("errors.4004"),
//					e);
//			}
//						
//			//審査状況が「0:未完了」のデータがなかったらTRUEを返す
//			String shinsaCompleteFlg = "FALSE";
//			if(count == 0){
//				shinsaCompleteFlg = "TRUE";	
//			}

			int count = 0;
			try {
                //---審査状況が「0:未完了」のデータがあるかどうかを確認
                if (SINNSA_FLAG.equals(countKbn)) {
                    count = dao.countShinsaKekkaInfo(connection, shinsainNo,
                            jigyoKubun, null, jigyoId,
                            ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET,
                            null);
                //---入力状況が「0:未完了」のデータがあるかどうかを確認    
                } else if (RIEKISOHAN_FLAG.equals(countKbn)) {
                    count = dao.countShinsaKekkaInfo(connection, shinsainNo,
                            jigyoKubun, null, jigyoId, null,
                            ShinsaKekkaMaintenance.NYURYOKUJOKYO_COMPLETE_YET);
                }
            } catch (DataAccessException e) {
                throw new ApplicationException("審査結果データ検索中にDBエラーが発生しました。",
                        new ErrorInfo("errors.4004"), e);
            }
                        
            String shinsaCompleteFlg = "FALSE";
            String nyuryokuCompleteFlg = "FALSE";
            if (SINNSA_FLAG.equals(countKbn)) {
                //審査状況が「0:未完了」のデータがなかったらTRUEを返す
                if(count == 0){
                    shinsaCompleteFlg = "TRUE"; 
                }
            } else if (RIEKISOHAN_FLAG.equals(countKbn)) {
                //入力状況が「0:未完了」のデータがなかったらTRUEを返す
                if(count == 0){
                    nyuryokuCompleteFlg = "TRUE"; 
                }               
            }

			// 戻り値マップ
			Map map = new HashMap();	
			// 審査担当分一覧（一覧データ）
			map.put(KEY_SHINSATANTO_LIST, pageInfo);
			// 審査担当分一覧（審査完了フラグ）
			map.put(KEY_SHINSACOMPLETE_FLG, shinsaCompleteFlg);
            // 利害関係入力状況一覧（入力完了フラグ）
            map.put(KEY_NYURYOKUCOMPLETE_FLG, nyuryokuCompleteFlg);
//2007/10/27　苗　修正ここまで            
			
			// 事業区分が「4:基盤」だったら
			if(IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKubun)
                    || IJigyoKubun.JIGYO_KUBUN_WAKATESTART.equals(jigyoKubun)
                    || IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI.equals(jigyoKubun)){
				
				//---総合評点リストを取得
				List hyotenList = null;
				try {
					hyotenList = dao.getSogoHyokaList(
												connection, 
												shinsainNo,
												jigyoKubun,
												jigyoId);
				} catch (DataAccessException e) {
					throw new ApplicationException(
						"審査結果データ検索中にDBエラーが発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}

				//マップに入れなおす
				int allcount = 0;
				Map hyotenMap = new HashMap();
				for(int i=0; i<hyotenList.size(); i++){
					Map recordMap = (Map)hyotenList.get(i);	//１レコード
	
					String kekkaTen_ = (String)recordMap.get("KEKKA_TEN");
					String count_ = ((Number)recordMap.get("COUNT")).toString();
					allcount = allcount + Integer.parseInt(count_);
	
					hyotenMap.put(kekkaTen_, count_);
				}
				//「すべて」（総件数）のキーは「0」でセット
				hyotenMap.put("0", Integer.toString(allcount));				

				//戻り値マップに追加
				//審査担当分一覧（総合評点リスト）
				map.put(KEY_SOGOHYOTEN_LIST, hyotenMap);
			}

			//2006.06.08 iso 審査担当事業一覧での事業名表示方式修正
			JigyoKanriPk jigyoKanriPk = new JigyoKanriPk(jigyoId);
			JigyoKanriInfoDao jigyoKanriInfoDao = new JigyoKanriInfoDao(userInfo);
			JigyoKanriInfo jigyoKanriInfo;
			try {
				jigyoKanriInfo = jigyoKanriInfoDao.selectJigyoKanriInfo(connection, jigyoKanriPk);
			} catch(DataAccessException e) {
				throw new ApplicationException(
					"事業データ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			map.put("JIGYO_INFO", jigyoKanriInfo);
			return map;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * 審査結果情報の取得.<br /><br />
	 * 
	 * 審査結果情報を取得して、それを格納したShinsaKekkaInputInfoを返却する。<br /><br />
	 * 
	 * 1.以下のSQL文を発行して、該当する申請データを取得する。<br />
	 * (バインド引数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SYSTEM_NO			--システム受付番号
	 * 	,A.UKETUKE_NO			--申請番号
	 * 	,A.JIGYO_ID			--事業ID
	 * 	,A.NENDO				--年度
	 * 	,A.KAISU				--回数
	 * 	,A.JIGYO_NAME			--事業名
	 * 	,A.SHINSEISHA_ID			--申請者ID
	 * 	,A.SAKUSEI_DATE			--申請書作成日
	 * 	,A.SHONIN_DATE			--所属機関承認日
	 * 	,A.JYURI_DATE			--学振受理日
	 * 	,A.NAME_KANJI_SEI			--申請者氏名（漢字等-姓）
	 * 	,A.NAME_KANJI_MEI			--申請者氏名（漢字等-名）
	 * 	,A.NAME_KANA_SEI			--申請者氏名（フリガナ-姓）
	 * 	,A.NAME_KANA_MEI			--申請者氏名（フリガナ-名）
	 * 	,A.NAME_RO_SEI			--申請者氏名（ローマ字-姓）
	 * 	,A.NAME_RO_MEI			--申請者氏名（ローマ字-名）
	 * 	,A.NENREI				--年齢
	 * 	,A.KENKYU_NO			--申請者研究者番号
	 * 	,A.SHOZOKU_CD			--所属機関コード
	 * 	,A.SHOZOKU_NAME			--所属機関名
	 * 	,A.SHOZOKU_NAME_RYAKU		--所属機関名（略称）
	 * 	,A.BUKYOKU_CD			--部局コード
	 * 	,A.BUKYOKU_NAME			--部局名
	 * 	,A.BUKYOKU_NAME_RYAKU		--部局名（略称）
	 * 	,A.SHOKUSHU_CD			--職名コード
	 * 	,A.SHOKUSHU_NAME_KANJI		--職名（和文）
	 * 	,A.SHOKUSHU_NAME_RYAKU		--職名（略称）
	 * 	,A.ZIP				--郵便番号
	 * 	,A.ADDRESS			--住所
	 * 	,A.TEL				--TEL
	 * 	,A.FAX				--FAX
	 * 	,A.EMAIL				--E-Mail
	 * 	,A.SENMON				--現在の専門
	 * 	,A.GAKUI				--学位
	 * 	,A.BUNTAN				--役割分担
	 * 	,A.KADAI_NAME_KANJI		--研究課題名(和文）
	 * 	,A.KADAI_NAME_EIGO			--研究課題名(英文）
	 * 	,A.JIGYO_KUBUN			--事業区分
	 * 	,A.SHINSA_KUBUN			--審査区分
	 * 	,A.SHINSA_KUBUN_MEISHO		--審査区分名称
	 * 	,A.BUNKATSU_NO			--分割番号
	 * 	,A.BUNKATSU_NO_MEISHO		--分割番号名称
	 * 	,A.KENKYU_TAISHO			--研究対象の類型
	 * 	,A.KEI_NAME_NO			--系等の区分番号
	 * 	,A.KEI_NAME			--系等の区分
	 * 	,A.KEI_NAME_RYAKU			--系等の区分略称
	 * 	,A.BUNKASAIMOKU_CD			--細目番号
	 * 	,A.BUNYA_NAME			--分野
	 * 	,A.BUNKA_NAME			--分科
	 * 	,A.SAIMOKU_NAME			--細目
	 * 	,A.BUNKASAIMOKU_CD2		--細目番号2
	 * 	,A.BUNYA_NAME2			--分野2
	 * 	,A.BUNKA_NAME2			--分科2
	 * 	,A.SAIMOKU_NAME2			--細目2
	 * 	,A.KANTEN_NO			--推薦の観点番号
	 * 	,A.KANTEN				--推薦の観点
	 * 	,A.KANTEN_RYAKU			--推薦の観点略称
	 * 	,A.KEIHI1				--1年目研究経費
	 * 	,A.BIHINHI1			--1年目設備備品費
	 * 	,A.SHOMOHINHI1			--1年目消耗品費
	 * 	,A.KOKUNAIRYOHI1			--1年目国内旅費
	 * 	,A.GAIKOKURYOHI1			--1年目外国旅費
	 * 	,A.RYOHI1				--1年目旅費
	 * 	,A.SHAKIN1			--1年目謝金等
	 * 	,A.SONOTA1			--1年目その他
	 * 	,A.KEIHI2				--2年目研究経費
	 * 	,A.BIHINHI2			--2年目設備備品費
	 * 	,A.SHOMOHINHI2			--2年目消耗品費
	 * 	,A.KOKUNAIRYOHI2			--2年目国内旅費
	 * 	,A.GAIKOKURYOHI2			--2年目外国旅費
	 * 	,A.RYOHI2				--2年目旅費
	 * 	,A.SHAKIN2			--2年目謝金等
	 * 	,A.SONOTA2			--2年目その他
	 * 	,A.KEIHI3				--3年目研究経費
	 * 	,A.BIHINHI3			--3年目設備備品費
	 * 	,A.SHOMOHINHI3			--3年目消耗品費
	 * 	,A.KOKUNAIRYOHI3			--3年目国内旅費
	 * 	,A.GAIKOKURYOHI3			--3年目外国旅費
	 * 	,A.RYOHI3				--3年目旅費
	 * 	,A.SHAKIN3			--3年目謝金等
	 * 	,A.SONOTA3			--3年目その他
	 * 	,A.KEIHI4				--4年目研究経費
	 * 	,A.BIHINHI4			--4年目設備備品費
	 * 	,A.SHOMOHINHI4			--4年目消耗品費
	 * 	,A.KOKUNAIRYOHI4			--4年目国内旅費
	 * 	,A.GAIKOKURYOHI4			--4年目外国旅費
	 * 	,A.RYOHI4				--4年目旅費
	 * 	,A.SHAKIN4			--4年目謝金等
	 * 	,A.SONOTA4			--4年目その他
	 * 	,A.KEIHI5				--5年目研究経費
	 * 	,A.BIHINHI5			--5年目設備備品費
	 * 	,A.SHOMOHINHI5			--5年目消耗品費
	 * 	,A.KOKUNAIRYOHI5			--5年目国内旅費
	 * 	,A.GAIKOKURYOHI5			--5年目外国旅費
	 * 	,A.RYOHI5				--5年目旅費
	 * 	,A.SHAKIN5			--5年目謝金等
	 * 	,A.SONOTA5			--5年目その他
	 * 	,A.KEIHI_TOTAL			--総計-研究経費
	 * 	,A.BIHINHI_TOTAL			--総計-設備備品費
	 * 	,A.SHOMOHINHI_TOTAL		--総計-消耗品費
	 * 	,A.KOKUNAIRYOHI_TOTAL		--総計-国内旅費
	 * 	,A.GAIKOKURYOHI_TOTAL		--総計-外国旅費
	 * 	,A.RYOHI_TOTAL			--総計-旅費
	 * 	,A.SHAKIN_TOTAL			--総計-謝金等
	 * 	,A.SONOTA_TOTAL			--総計-その他
	 * 	,A.SOSHIKI_KEITAI_NO		--研究組織の形態番号
	 * 	,A.SOSHIKI_KEITAI			--研究組織の形態
	 * 	,A.BUNTANKIN_FLG			--分担金の有無
	 * 	,A.KOYOHI				--研究支援者雇用経費
	 * 	,A.KENKYU_NINZU			--研究者数
	 * 	,A.TAKIKAN_NINZU			--他機関の分担者数
	 * 	,A.SHINSEI_KUBUN			--新規継続区分
	 * 	,A.KADAI_NO_KEIZOKU		--継続分の研究課題番号
	 * 	,A.SHINSEI_FLG_NO			--申請の有無番号
	 * 	,A.SHINSEI_FLG			--申請の有無
	 * 	,A.KADAI_NO_SAISYU			--最終年度課題番号
	 * 	,A.KAIJIKIBO_FLG_NO		--開示希望の有無番号
	 * 	,A.KAIJIKIBO_FLG			--開示希望の有無
	 * 	,A.KAIGAIBUNYA_CD			--海外分野コード
	 * 	,A.KAIGAIBUNYA_NAME		--海外分野名称
	 * 	,A.KAIGAIBUNYA_NAME_RYAKU		--海外分野略称
	 * 	,A.KANREN_SHIMEI1			--関連分野の研究者-氏名1
	 * 	,A.KANREN_KIKAN1			--関連分野の研究者-所属機関1
	 * 	,A.KANREN_BUKYOKU1			--関連分野の研究者-所属部局1
	 * 	,A.KANREN_SHOKU1			--関連分野の研究者-職名1
	 * 	,A.KANREN_SENMON1			--関連分野の研究者-専門分野1
	 * 	,A.KANREN_TEL1			--関連分野の研究者-勤務先電話番号1
	 * 	,A.KANREN_JITAKUTEL1		--関連分野の研究者-自宅電話番号1
	 * 	,A.KANREN_MAIL1			--関連分野の研究者-E-mail1
	 * 	,A.KANREN_SHIMEI2			--関連分野の研究者-氏名2
	 * 	,A.KANREN_KIKAN2			--関連分野の研究者-所属機関2
	 * 	,A.KANREN_BUKYOKU2			--関連分野の研究者-所属部局2
	 * 	,A.KANREN_SHOKU2			--関連分野の研究者-職名2
	 * 	,A.KANREN_SENMON2			--関連分野の研究者-専門分野2
	 * 	,A.KANREN_TEL2			--関連分野の研究者-勤務先電話番号2
	 * 	,A.KANREN_JITAKUTEL2		--関連分野の研究者-自宅電話番号2
	 * 	,A.KANREN_MAIL2			--関連分野の研究者-E-mail2
	 * 	,A.KANREN_SHIMEI3			--関連分野の研究者-氏名3
	 * 	,A.KANREN_KIKAN3			--関連分野の研究者-所属機関3
	 * 	,A.KANREN_BUKYOKU3			--関連分野の研究者-所属部局3
	 * 	,A.KANREN_SHOKU3			--関連分野の研究者-職名3
	 * 	,A.KANREN_SENMON3			--関連分野の研究者-専門分野3
	 * 	,A.KANREN_TEL3			--関連分野の研究者-勤務先電話番号3
	 * 	,A.KANREN_JITAKUTEL3		--関連分野の研究者-自宅電話番号3
	 * 	,A.KANREN_MAIL3			--関連分野の研究者-E-mail3
	 * 	,A.XML_PATH			--XMLの格納パス
	 * 	,A.PDF_PATH			--PDFの格納パス
	 * 	,A.JURI_KEKKA			--受理結果
	 * 	,A.JURI_BIKO			--受理結果備考
	 * 	,A.SUISENSHO_PATH			--推薦書の格納パス
	 * 	,A.KEKKA1_ABC			--１次審査結果(ABC)
	 * 	,A.KEKKA1_TEN			--１次審査結果(点数)
	 * 	,A.KEKKA1_TEN_SORTED		--１次審査結果(点数順)
	 * 	,A.SHINSA1_BIKO			--１次審査備考
	 * 	,A.KEKKA2				--２次審査結果
	 * 	,A.SOU_KEHI			--総経費（学振入力）
	 * 	,A.SHONEN_KEHI			--初年度経費（学振入力）
	 * 	,A.SHINSA2_BIKO			--業務担当者記入欄
	 * 	,A.JOKYO_ID			--申請状況ID
	 * 	,A.SAISHINSEI_FLG			--再申請フラグ
	 * 	,A.DEL_FLG			--削除フラグ
	 * FROM
	 * 	SHINSEIDATAKANRI A
	 * WHERE
	 * 	SYSTEM_NO = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム番号</td><td>第二引数shinsaKekkaPkの変数SystemNoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値は、shinseiDataInfoに格納する。<br /><br /><br />
	 * 
	 * 
	 * 
	 * 2.以下のSQL文を発行して、該当する審査結果データを取得する。<br />
	 * (バインド引数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SYSTEM_NO			--システム受付番号
	 * 	,A.UKETUKE_NO			--申請番号
	 * 	,A.SHINSAIN_NO			--審査員番号
	 * 	,A.JIGYO_KUBUN			--事業区分
	 * 	,A.SEQ_NO				--シーケンス番号
	 * 	,A.SHINSA_KUBUN			--審査区分
	 * 	,A.SHINSAIN_NAME_KANJI_SEI		--審査員名（漢字－姓）
	 * 	,A.SHINSAIN_NAME_KANJI_MEI		--審査員名（漢字－名）
	 * 	,A.NAME_KANA_SEI			--審査員名（フリガナ－姓）
	 * 	,A.NAME_KANA_MEI			--審査員名（フリガナ－名）
	 * 	,A.SHOZOKU_NAME			--審査員所属機関名
	 * 	,A.BUKYOKU_NAME			--審査員部局名
	 * 	,A.SHOKUSHU_NAME			--審査員職名
	 * 	,A.JIGYO_ID			--事業ID
	 * 	,A.JIGYO_NAME			--事業名
	 * 	,A.BUNKASAIMOKU_CD			--細目番号
	 * 	,A.EDA_NO				--枝番
	 * 	,A.CHECKDIGIT			--チェックデジット
	 * 	,A.KEKKA_ABC			--総合評価（ABC）
	 * 	,A.KEKKA_TEN			--総合評価（点数）
	 * 	,A.COMMENT1			--コメント1
	 * 	,A.COMMENT2			--コメント2
	 * 	,A.COMMENT3			--コメント3
	 * 	,A.COMMENT4			--コメント4
	 * 	,A.COMMENT5			--コメント5
	 * 	,A.COMMENT6			--コメント6
	 * 	,A.KENKYUNAIYO			--研究内容
	 * 	,A.KENKYUKEIKAKU			--研究計画
	 * 	,A.TEKISETSU_KAIGAI		--適切性-海外
	 * 	,A.TEKISETSU_KENKYU1		--適切性-研究（1）
	 * 	,A.TEKISETSU			--適切性
	 * 	,A.DATO				--妥当性
	 * 	,A.SHINSEISHA			--研究代表者
	 * 	,A.KENKYUBUNTANSHA			--研究分担者
	 * 	,A.HITOGENOMU			--ヒトゲノム
	 * 	,A.TOKUTEI			--特定胚
	 * 	,A.HITOES				--ヒトES細胞
	 * 	,A.KUMIKAE			--遺伝子組換え実験
	 * 	,A.CHIRYO				--遺伝子治療臨床研究
	 * 	,A.EKIGAKU			--疫学研究
	 * 	,A.COMMENTS			--コメント
	 * 	,A.TENPU_PATH			--添付ファイル格納パス
	 * 	,DECODE(
	 * 		NVL(A.TENPU_PATH,'null') 
	 * 		,'null','FALSE'		--添付ファイル格納パスがNULLのとき
	 * 		,      'TRUE'		--添付ファイル格納パスがNULL以外のとき
	 * 	) TENPU_FLG			--添付ファイル格納フラグ
	 * 	,A.SHINSA_JOKYO			--審査状況
	 * 	,A.BIKO				--備考
	 * FROM
	 * 	SHINSAKEKKA A
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * 	AND SHINSAIN_NO = ?
	 * 	AND JIGYO_KUBUN = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム番号</td><td>第二引数shinsaKekkaPkの変数SystemNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数shinsaKekkaPkの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数shinsaKekkaPkの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値は、shinsaKekkaInfoに格納する。<br /><br /><br />
	 * 
	 * 
	 * 
	 * 3.以下のSQL文を発行して、該当する事業管理データを取得する。<br />
	 * (バインド引数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.JIGYO_ID		--事業ID
	 * 	,A.NENDO			--年度
	 * 	,A.KAISU			--回数
	 * 	,A.JIGYO_NAME		--事業名
	 * 	,A.JIGYO_KUBUN		--事業区分
	 * 	,A.SHINSA_KUBUN		--審査区分
	 * 	,A.TANTOKA_NAME		--業務担当課
	 * 	,A.TANTOKAKARI		--業務担当係名
	 * 	,A.TOIAWASE_NAME		--問い合わせ先担当者名
	 * 	,A.TOIAWASE_TEL		--問い合わせ先電話番号
	 * 	,A.TOIAWASE_EMAIL		--問い合わせ先E-mail
	 * 	,A.UKETUKEKIKAN_START	--学振受付期間（開始）
	 * 	,A.UKETUKEKIKAN_END	--学振受付期間（終了）
	 * 	,A.SHINSAKIGEN		--審査期限
	 * 	,A.TENPU_NAME		--添付文書名
	 * 	,A.TENPU_WIN		--添付ファイル格納フォルダ（Win）
	 * 	,A.TENPU_MAC		--添付ファイル格納フォルダ（Mac）
	 * 	,A.HYOKA_FILE_FLG		--評価用ファイル有無
	 * 	,A.HYOKA_FILE		--評価用ファイル格納フォルダ
	 * 	,A.KOKAI_FLG		--公開フラグ
	 * 	,A.KESSAI_NO		--公開決裁番号
	 * 	,A.KOKAI_ID		--公開確定者ID
	 * 	,A.HOKAN_DATE		--データ保管日
	 * 	,A.YUKO_DATE		--保管有効期限
	 * 	,A.BIKO			--備考
	 * 	,A.DEL_FLG		--削除フラグ
	 * FROM
	 * 	JIGYOKANRI A
	 * WHERE
	 * 	JIGYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>取得したshinseiDataInfoの変数JigyoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値は、jigyoKanriInfoに格納する。<br /><br /><br />
	 * 
	 * 
	 * 
	 * 4.ShinsaKekkaInputInfoオブジェクトに値を格納する。<br /><br />
	 * 
	 * 　(1)1～3で取得した値を格納していく。格納する値は、以下の表を参照。<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>名前(和名)</td><td>名前</td><td>値を持つInfoオブジェクト</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>SystemNo</td><td>shinsaKekkaPk</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>getShinsainNo</td><td>shinsaKekkaPk</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>JigyoKubun</td><td>shinsaKekkaPk</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>年度</td><td>Nendo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>回数</td><td>Kaisu</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>海外研究分野(基盤)</td><td>KaigaibunyaName <span style="color:red">??</span></td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>細目番号(基盤)</td><td>BunkaSaimokuCd</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>細目名</td><td>SaimokuName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>JigyoId</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究種目名</td><td>JigyoName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請番号</td><td>UketukeNo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究課題名</td><td>KadaiNameKanji</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者名-姓</td><td>NameKanjiSei</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者名-名</td><td>NameKanjiMei</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名</td><td>ShozokuName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名</td><td>BukyokuName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名</td><td>ShokushuName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>推薦の観点</td><td>Kanten</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>系等の区分</td><td>KeiName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究組織の形態番号</td><td>SoshikiKeitaiNo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究計画最終年度前年度の応募</td><td>ShinseiFlgNo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>分担金の有無</td><td>BuntankinFlg</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>分割番号</td><td>BunkatsuNo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>海外分野コード</td><td>KaigaibunyaCd</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>海外分野名</td><td>KaigaibunyaName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総合評点(ABC)</td><td>KekkaAbc</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総合評点(点数)</td><td>KekkaTen</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント1</td><td>Comment1</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント2</td><td>Comment2</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント3</td><td>Comment3</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント4</td><td>Comment4</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント5</td><td>Comment5</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント6</td><td>Comment6</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究内容(基盤)</td><td>KenkyuNaiyo</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究計画(基盤)</td><td>KenkyuKeikaku</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>適切性-海外(基盤)</td><td>TekisetsuKaigai</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>適切性-研究(1)</td><td>TekisetsuKenkyu1</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>適切性(基盤)</td><td>Tekisetsu</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>妥当性(基盤)</td><td>Dato</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究代表者(基盤)</td><td>Shinseisha</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究分担者(基盤)</td><td>KenkyuBuntansha</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>ヒトゲノム(基盤)</td><td>Hitogenomu</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>特定胚(基盤)</td><td>Tokutei</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>ヒトES細胞(基盤)</td><td>HitoEs</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>遺伝子組換え実験(基盤)</td><td>Kumikae</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>遺伝子治療臨床研究(基盤)</td><td>Chiryo</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>疫学研究(基盤)</td><td>Ekigaku</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント(基盤)</td><td>Comments</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>添付ファイル格納フラグ</td><td>TenpuFlg</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>評価用ファイル有無</td><td>HyokaFileFlg</td><td>jigyoKanriInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>JigyoCd</td><td>shinsaKekkaInfo</td></tr>
	 * </table><br /><br />
	 * 
	 * 　(2)添付ファイル名の格納<br /><br />
	 * 　　shinsaKekkaInfo内のtenpuPathの値があるときは、パスからファイル名を取得し、格納する。<br /><br /><br />
	 * 
	 * 　(3)表示ラベル名の格納<br /><br />
	 * 　　以下のSQL文を発行して、ラベルマスタからレコードを持つMapを取得する。<br />
	 * 　　取得したMapの"表示ラベル名"をそれぞれ格納する。<br />
	 * 　　取得するラベル名は、以下の値のものとする。<br />
	 * 　　この、取得するラベル名の数だけ処理を行う。<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>取得するラベル名</td><td>ラベル区分</td><td>使用する値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>総合評価（ABC）</td><td>KEKKA_ABC</td><td>KekkaAbc</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総合評価（点数）</td><td>KEKKA_TEN</td><td>KekkaTen</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究内容</td><td>KENKYUNAIYO</td><td>KenkyuNaiyo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究計画</td><td>KENKYUKEIKAKU</td><td>KenkyuKeikaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>適切性-海外</td><td>TEKISETSU_KAIGAI</td><td>TekisetsuKaigai</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>適切性-研究(1)</td><td>TEKISETSU_KENKYU1</td><td>TekisetsuKenkyu1</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>適切性</td><td>TEKISETSU</td><td>Tekisetsu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>妥当性</td><td>DATO</td><td>Dato</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究代表者</td><td>SHINSEISHA</td><td>Shinseisha</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究分担者</td><td>KENKYUBUNTANSHA</td><td>KenkyuBuntansha</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>ヒトゲノム</td><td>HITOGENOMU</td><td>Hitogenomu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>特定胚</td><td>TOKUTEI</td><td>Tokutei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>ヒトＥＳ細胞</td><td>HITOES</td><td>HitoEs</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>遺伝子組換え実験</td><td>KUMIKAE</td><td>Kumikae</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>遺伝子治療臨床研究</td><td>CHIRYO</td><td>Chiryo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>疫学研究</td><td>EKIGAKU</td><td>Ekigaku</td></tr>
	 * </table><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.ATAI
	 * 	, A.NAME
	 * 	, A.RYAKU
	 * 	, A.SORT
	 * 	, A.BIKO
	 * FROM
	 * 	MASTER_LABEL A
	 * WHERE
	 * 	A.LABEL_KUBUN = ?
	 * 	AND A.ATAI = ?
	 * ORDER BY
	 * 	SORT</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>ラベル区分</td><td>上記の表の『ラベル区分』を使用</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>値</td><td>上記の表の『使用する値』を使用</td></tr>
	 * </table><br /><br />
	 * 
	 * 5.値の格納が終了したら、ShinsaKekkaInputInfoオブジェクトを返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param shinsaKekkaPk ShinsaKekkaPk
	 * @return 審査結果情報を持つShinsaKekkaInputInfo
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public ShinsaKekkaInputInfo select1stShinsaKekka(
		UserInfo userInfo,
		ShinsaKekkaPk shinsaKekkaPk)
		throws NoDataFoundException, ApplicationException
	{
		Connection   connection  = null;
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			
			//申請データ管理DAO
			ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo);
			//該当申請データを取得する
			ShinseiDataPk shinseiDataPk = new ShinseiDataPk(shinsaKekkaPk.getSystemNo());
			ShinseiDataInfo shinseiDataInfo = null;
			try{
				shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"申請書管理データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
		
			//審査結果DAO
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);
			//該当審査結果データを取得する
			ShinsaKekkaInfo shinsaKekkaInfo = null;
			try{
				shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfo(connection, shinsaKekkaPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//事業管理DAO
			JigyoKanriInfoDao jigyoDao = new JigyoKanriInfoDao(userInfo);
			//該当事業管理データを取得する
			JigyoKanriPk jigyoKanriPk = new JigyoKanriPk(shinseiDataInfo.getJigyoId());
			
			JigyoKanriInfo jigyoKanriInfo = null;
			try{
				jigyoKanriInfo = jigyoDao.selectJigyoKanriInfo(connection, jigyoKanriPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"事業管理データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//---審査結果入力オブジェクトの生成
			ShinsaKekkaInputInfo info = new ShinsaKekkaInputInfo();
			info.setSystemNo(shinsaKekkaPk.getSystemNo());									//システム受付番号
			info.setShinsainNo(shinsaKekkaPk.getShinsainNo());								//審査員番号
			info.setJigyoKubun(shinsaKekkaPk.getJigyoKubun());								//事業区分
			info.setNendo(shinseiDataInfo.getNendo());										//年度
			info.setKaisu(shinseiDataInfo.getKaisu());										//回数
			
			
			// 2005/03/03
			// 事業ID  ??  海外研究分野名  ??
			info.setJigyoId(shinseiDataInfo.getJigyoId());									//海外研究分野名（基盤）
			
			info.setBunkaSaimokuCd(shinseiDataInfo.getKadaiInfo().getBunkaSaimokuCd());		//細目番号（基盤）
			info.setSaimokuName(shinseiDataInfo.getKadaiInfo().getSaimokuName());			//細目名（基盤）
			info.setJigyoId(shinseiDataInfo.getJigyoId());									//事業ID			
			info.setJigyoName(shinseiDataInfo.getJigyoName());								//研究種目名
			info.setUketukeNo(shinseiDataInfo.getUketukeNo());								//申請番号
			info.setKadaiNameKanji(shinseiDataInfo.getKadaiInfo().getKadaiNameKanji());		//研究課題名
			info.setNameKanjiSei(shinseiDataInfo.getDaihyouInfo().getNameKanjiSei());		//申請者名-姓
			info.setNameKanjiMei(shinseiDataInfo.getDaihyouInfo().getNameKanjiMei());		//申請者名-名
			info.setShozokuName(shinseiDataInfo.getDaihyouInfo().getShozokuName());			//所属機関名
			info.setBukyokuName(shinseiDataInfo.getDaihyouInfo().getBukyokuName());			//部局名
			info.setShokushuName(shinseiDataInfo.getDaihyouInfo().getShokushuNameKanji());	//職名
			info.setKanten(shinseiDataInfo.getKadaiInfo().getKanten());						//推薦の観点
			info.setKeiName(shinseiDataInfo.getKadaiInfo().getKeiName());					//系等の区分
			info.setSoshikiKeitaiNo(shinseiDataInfo.getSoshikiKeitaiNo());					//研究組織の形態番号
			info.setShinseiFlgNo(shinseiDataInfo.getShinseiFlgNo());						//研究計画最終年度前年度の応募
			info.setBuntankinFlg(shinseiDataInfo.getBuntankinFlg());						//分担金の有無
			info.setBunkatsuNo(shinseiDataInfo.getKadaiInfo().getBunkatsuNo());				//分割番号
			info.setKaigaibunyaCd(shinseiDataInfo.getKaigaibunyaCd());						//海外分野コード
			info.setKaigaibunyaName(shinseiDataInfo.getKaigaibunyaName());					//海外分野名
			info.setKekkaAbc(shinsaKekkaInfo.getKekkaAbc());								//総合評点（ABC）
			info.setKekkaTen(shinsaKekkaInfo.getKekkaTen());								//総合評点（点数）
			info.setComment1(shinsaKekkaInfo.getComment1());								//コメント1
			info.setComment2(shinsaKekkaInfo.getComment2());								//コメント2
			info.setComment3(shinsaKekkaInfo.getComment3());								//コメント3
			info.setComment4(shinsaKekkaInfo.getComment4());								//コメント4
			info.setComment5(shinsaKekkaInfo.getComment5());								//コメント5
			info.setComment6(shinsaKekkaInfo.getComment6());								//コメント6
			info.setKenkyuNaiyo(shinsaKekkaInfo.getKenkyuNaiyo());							//研究内容（基盤）
			info.setKenkyuKeikaku(shinsaKekkaInfo.getKenkyuKeikaku());						//研究計画（基盤）
			info.setTekisetsuKaigai(shinsaKekkaInfo.getTekisetsuKaigai());					//適切性-海外（基盤）
			info.setTekisetsuKenkyu1(shinsaKekkaInfo.getTekisetsuKenkyu1());				//適切性-研究(1)（基盤）
			info.setTekisetsu(shinsaKekkaInfo.getTekisetsu());								//適切性（基盤）
			info.setDato(shinsaKekkaInfo.getDato());										//妥当性（基盤）
			info.setShinseisha(shinsaKekkaInfo.getShinseisha());							//研究代表者（基盤）
			info.setKenkyuBuntansha(shinsaKekkaInfo.getKenkyuBuntansha());					//研究分担者（基盤）	
			info.setHitogenomu(shinsaKekkaInfo.getHitogenomu());							//ヒトゲノム（基盤）
			info.setTokutei(shinsaKekkaInfo.getTokutei());									//特定胚（基盤）
			info.setHitoEs(shinsaKekkaInfo.getHitoEs());									//ヒトES細胞（基盤）
			info.setKumikae(shinsaKekkaInfo.getKumikae());									//遺伝子組換え実験（基盤）
			info.setChiryo(shinsaKekkaInfo.getChiryo());									//遺伝子治療臨床研究（基盤）
			info.setEkigaku(shinsaKekkaInfo.getEkigaku());									//疫学研究（基盤）							
			info.setComments(shinsaKekkaInfo.getComments());								//コメント（基盤）
			//2005.10.26 kainuma
			info.setRigai(shinsaKekkaInfo.getRigai());										//利害関係
			info.setWakates(shinsaKekkaInfo.getWakates());									//若手S　2007/5/8
			info.setJuyosei(shinsaKekkaInfo.getJuyosei());									//学術的重要性・妥当性
			info.setDokusosei(shinsaKekkaInfo.getDokusosei());								//独創性・革新性
			info.setHakyukoka(shinsaKekkaInfo.getHakyukoka());								//波及効果・普遍性
			info.setSuikonoryoku(shinsaKekkaInfo.getSuikonoryoku());						//遂行能力・環境の適切性
			info.setJinken(shinsaKekkaInfo.getJinken());									//人権の保護・法令等の遵守
			info.setBuntankin(shinsaKekkaInfo.getBuntankin());								//分担金配分
			info.setOtherComment(shinsaKekkaInfo.getOtherComment());						//その他コメント

			info.setTenpuFlg(shinsaKekkaInfo.getTenpuFlg());								//添付ファイル格納フラグ
			info.setHyokaFileFlg(jigyoKanriInfo.getHyokaFileFlg());							//評価用ファイル有無	
			info.setJigyoCd(shinsaKekkaInfo.getJigyoId().substring(2,7));					//事業コード
//2006/04/10 追加ここから
			info.setShinsaryoikiCd(shinseiDataInfo.getShinsaRyoikiCd());                    //分野番号
			info.setShinsaryoikiName(shinseiDataInfo.getShinsaRyoikiName());                //分野名
//苗　追加ここまで			

			
			//添付ファイル名をセット
			String tenpuPath = shinsaKekkaInfo.getTenpuPath();
			if(tenpuPath != null && tenpuPath.length() != 0){
				info.setTenpuName(new File(tenpuPath).getName());
			}
						
			//総合評価（ABC）の表示ラベル名をセット
			if(info.getKekkaAbc() != null && info.getKekkaAbc().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KEKKA_ABC,
																	info.getKekkaAbc());		
					info.setKekkaAbcLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、総合評価（ABC）をセット
					info.setKekkaAbcLabel(info.getKekkaAbc());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//総合評価（点数）の表示ラベル名をセット
			if(info.getKekkaTen() != null && info.getKekkaTen().length() != 0){
				try{				
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KEKKA_TEN,
																	info.getKekkaTen());		
					info.setKekkaTenLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、総合評価（点数）又は（萌芽）をセット
					info.setKekkaTenLabel(info.getKekkaTen());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			
			//総合評価（萌芽）の表示ラベル名をセット
			if(info.getKekkaTen() != null && info.getKekkaTen().length() != 0){
				 try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	  ILabelKubun.KEKKA_TEN_HOGA,
																	  info.getKekkaTen());		
					info.setKekkaTenHogaLabel((String)resultMap.get("NAME"));
						  
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、総合評価（萌芽）をセット
					info.setKekkaTenHogaLabel(info.getKekkaTen());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			
			//研究内容の表示ラベル名をセット
			if(info.getKenkyuNaiyo() != null && info.getKenkyuNaiyo().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KENKYUNAIYO,
																	info.getKenkyuNaiyo());		
					info.setKenkyuNaiyoLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、研究内容をセット
					info.setKenkyuNaiyoLabel(info.getKenkyuNaiyo());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//研究計画の表示ラベル名をセット
			if(info.getKenkyuKeikaku() != null && info.getKenkyuKeikaku().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KENKYUKEIKAKU,
																	info.getKenkyuKeikaku());		
					info.setKenkyuKeikakuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、研究計画をセット
					info.setKenkyuKeikakuLabel(info.getKenkyuKeikaku());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//適切性-海外の表示ラベル名をセット
			if(info.getTekisetsuKaigai() != null && info.getTekisetsuKaigai().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.TEKISETSU_KAIGAI,
																	info.getTekisetsuKaigai());		
					info.setTekisetsuKaigaiLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、適切性-海外をセット
					info.setTekisetsuKaigaiLabel(info.getTekisetsuKaigai());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//適切性-研究(1)の表示ラベル名をセット
			if(info.getTekisetsuKenkyu1() != null && info.getTekisetsuKenkyu1().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.TEKISETSU_KENKYU1,
																	info.getTekisetsuKenkyu1());		
					info.setTekisetsuKenkyu1Label((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、適切性-研究(1)をセット
					info.setTekisetsuKenkyu1Label(info.getTekisetsuKenkyu1());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//適切性の表示ラベル名をセット
			if(info.getTekisetsu() != null && info.getTekisetsu().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.TEKISETSU,
																	info.getTekisetsu());		
					info.setTekisetsuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、適切性をセット
					info.setTekisetsuLabel(info.getTekisetsu());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//妥当性の表示ラベル名をセット
			if(info.getDato() != null && info.getDato().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.DATO,
																	info.getDato());		
					info.setDatoLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、妥当性をセット
					info.setDatoLabel(info.getDato());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//研究代表者の表示ラベル名をセット
			if(info.getShinseisha() != null && info.getShinseisha().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.SHINSEISHA,
																	info.getShinseisha());		
					info.setShinseishaLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、研究代表者をセット
					info.setShinseishaLabel(info.getShinseisha());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//研究分担者の表示ラベル名をセット
			if(info.getKenkyuBuntansha() != null && info.getKenkyuBuntansha().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KENKYUBUNTANSHA,
																	info.getKenkyuBuntansha());		
					info.setKenkyuBuntanshaLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、研究分担者をセット
					info.setKenkyuBuntanshaLabel(info.getKenkyuBuntansha());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//ヒトゲノムの表示ラベル名をセット
			if(info.getHitogenomu() != null && info.getHitogenomu().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.HITOGENOMU,
																	info.getHitogenomu());		
					info.setHitogenomuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、ヒトゲノムをセット
					info.setHitogenomuLabel(info.getHitogenomu());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//特定胚の表示ラベル名をセット
			if(info.getTokutei() != null && info.getTokutei().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.TOKUTEI,
																	info.getTokutei());		
					info.setTokuteiLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、特定胚をセット
					info.setTokuteiLabel(info.getTokutei());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//ヒトＥＳ細胞の表示ラベル名をセット
			if(info.getHitoEs() != null && info.getHitoEs().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.HITOES,
																	info.getHitoEs());		
					info.setHitoEsLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、ヒトＥＳ細胞をセット
					info.setHitoEsLabel(info.getHitoEs());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//遺伝子組換え実験の表示ラベル名をセット
			if(info.getKumikae() != null && info.getKumikae().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KUMIKAE,
																	info.getKumikae());		
					info.setKumikaeLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、遺伝子組換え実験をセット
					info.setKumikaeLabel(info.getKumikae());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//遺伝子治療臨床研究の表示ラベル名をセット
			if(info.getChiryo() != null && info.getChiryo().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.CHIRYO,
																	info.getChiryo());		
					info.setChiryoLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、遺伝子治療臨床研究をセット
					info.setChiryoLabel(info.getChiryo());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//疫学研究の表示ラベル名をセット
			if(info.getEkigaku() != null && info.getEkigaku().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.EKIGAKU,
																	info.getEkigaku());		
					info.setEkigakuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、疫学研究をセット
					info.setEkigakuLabel(info.getEkigaku());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			
			
			//利害関係の表示ラベル名をセット
		  	if (info.getRigai() != null && info.getRigai().length() != 0) {
                try {
                    Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.RIGAI, info.getRigai());
                    info.setRigaiLabel((String) resultMap.get("NAME"));
                } catch (NoDataFoundException e) {
                    //例外NoDataFoundExceptionが発生したときは、利害関係をセット
                    info.setRigaiLabel(info.getRigai());
                } catch (DataAccessException e) {
                    throw new ApplicationException("ラベルマスタ情報取得中に例外が発生しました。",
                            new ErrorInfo("errors.4004"), e);
                }
            }

			//若手Sとしての妥当性の表示ラベル名をセット　2007/5/9追加
			if (info.getWakates() != null && info.getWakates().length() != 0) {
                try {
                    Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.JUYOSEI, info.getWakates());
					info.setWakatesLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、若手Sをセット
				  info.setWakatesLabel(info.getWakates());
				}catch (DataAccessException e) {
                    throw new ApplicationException("ラベルマスタ情報取得中に例外が発生しました。",
                            new ErrorInfo("errors.4004"), e);
                }
		    }			  
		  	
			//学術的重要性・妥当性の表示ラベル名をセット
			if (info.getJuyosei() != null && info.getJuyosei().length() != 0) {
                try {
                    Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.JUYOSEI, info.getJuyosei());
					info.setJuyoseiLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、該当の値をセットする
				  info.setJuyoseiLabel(info.getJuyosei());
				}catch (DataAccessException e) {
                    throw new ApplicationException("ラベルマスタ情報取得中に例外が発生しました。",
                            new ErrorInfo("errors.4004"), e);
                }
		    }			  
	
			//独創性・革新性の表示ラベル名をセット
			if(info.getDokusosei() != null && info.getDokusosei().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.DOKUSOSEI, info.getDokusosei());
					info.setDokusoseiLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、該当の値をセットする
					info.setDokusoseiLabel(info.getDokusosei());
				}catch (DataAccessException e) {
                    throw new ApplicationException("ラベルマスタ情報取得中に例外が発生しました。",
                            new ErrorInfo("errors.4004"), e);
                }
			}
			
			//波及効果・普遍性の表示ラベル名をセット
			if(info.getHakyukoka() != null && info.getHakyukoka().length() != 0){
				try {
                    Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.HAKYUKOKA, info.getHakyukoka());
					info.setHakyukokaLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、該当の値をセットする
				  info.setHakyukokaLabel(info.getHakyukoka());
				}catch (DataAccessException e) {
                    throw new ApplicationException("ラベルマスタ情報取得中に例外が発生しました。",
                            new ErrorInfo("errors.4004"), e);
			  }		
		  }
			
			//遂行能力・環境の適切性の表示ラベル名をセット
			if(info.getSuikonoryoku() != null && info.getSuikonoryoku().length() != 0){
				try {
				  Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.SUIKONORYOKU, info.getSuikonoryoku());
					info.setSuikonoryokuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、該当の値をセットする
				  info.setSuikonoryokuLabel(info.getRigai());
				}catch (DataAccessException e) {
                    throw new ApplicationException("ラベルマスタ情報取得中に例外が発生しました。",
                            new ErrorInfo("errors.4004"), e);
                }		
		    }
		
            // 人権の保護・法令等の遵守の表示ラベル名をセット
            if (info.getJinken() != null && info.getJinken().length() != 0) {
                try {
                    Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.JINKEN, info.getJinken());
                    info.setJinkenLabel((String) resultMap.get("NAME"));
                } catch (NoDataFoundException e) {
					//例外NoDataFoundExceptionが発生したときは、該当の値をセットする
                    info.setJinkenLabel(info.getJinken());
                } catch (DataAccessException e) {
                    throw new ApplicationException("ラベルマスタ情報取得中に例外が発生しました。",
                            new ErrorInfo("errors.4004"), e);
                }
            }
			
		    //分担金配分の表示ラベル名をセット
		    if(info.getBuntankin() != null && info.getBuntankin().length() != 0){
			    try{
				    Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																//2005.01.17 iso 分担金の表示バグを修正
//																ILabelKubun.SUIKONORYOKU,
																ILabelKubun.BUNTANKIN,
																info.getBuntankin());		
				    info.setBuntankinLabel((String)resultMap.get("NAME"));
			    }catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、該当の値をセットする
				    info.setBuntankinLabel(info.getRigai());
			    }catch (DataAccessException e) {
					throw new ApplicationException(
					"ラベルマスタ情報取得中に例外が発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}		
		}			  						  
			return info;
			
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	
	}
		
	/**
	 * 審査結果の登録.<br /><br />
	 * 
	 * 1.審査結果の登録<br /><br />
	 * 
	 * (1)以下のSQL文を発行して、該当する審査結果データを取得する。<br />
	 * (バインド引数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SYSTEM_NO			--システム受付番号
	 * 	,A.UKETUKE_NO			--申請番号
	 * 	,A.SHINSAIN_NO			--審査員番号
	 * 	,A.JIGYO_KUBUN			--事業区分
	 * 	,A.SEQ_NO				--シーケンス番号
	 * 	,A.SHINSA_KUBUN			--審査区分
	 * 	,A.SHINSAIN_NAME_KANJI_SEI		--審査員名（漢字－姓）
	 * 	,A.SHINSAIN_NAME_KANJI_MEI		--審査員名（漢字－名）
	 * 	,A.NAME_KANA_SEI			--審査員名（フリガナ－姓）
	 * 	,A.NAME_KANA_MEI			--審査員名（フリガナ－名）
	 * 	,A.SHOZOKU_NAME			--審査員所属機関名
	 * 	,A.BUKYOKU_NAME			--審査員部局名
	 * 	,A.SHOKUSHU_NAME			--審査員職名
	 * 	,A.JIGYO_ID			--事業ID
	 * 	,A.JIGYO_NAME			--事業名
	 * 	,A.BUNKASAIMOKU_CD			--細目番号
	 * 	,A.EDA_NO				--枝番
	 * 	,A.CHECKDIGIT			--チェックデジット
	 * 	,A.KEKKA_ABC			--総合評価（ABC）
	 * 	,A.KEKKA_TEN			--総合評価（点数）
	 * 	,A.COMMENT1			--コメント1
	 * 	,A.COMMENT2			--コメント2
	 * 	,A.COMMENT3			--コメント3
	 * 	,A.COMMENT4			--コメント4
	 * 	,A.COMMENT5			--コメント5
	 * 	,A.COMMENT6			--コメント6
	 * 	,A.KENKYUNAIYO			--研究内容
	 * 	,A.KENKYUKEIKAKU			--研究計画
	 * 	,A.TEKISETSU_KAIGAI		--適切性-海外
	 * 	,A.TEKISETSU_KENKYU1		--適切性-研究（1）
	 * 	,A.TEKISETSU			--適切性
	 * 	,A.DATO				--妥当性
	 * 	,A.SHINSEISHA			--研究代表者
	 * 	,A.KENKYUBUNTANSHA			--研究分担者
	 * 	,A.HITOGENOMU			--ヒトゲノム
	 * 	,A.TOKUTEI			--特定胚
	 * 	,A.HITOES				--ヒトES細胞
	 * 	,A.KUMIKAE			--遺伝子組換え実験
	 * 	,A.CHIRYO				--遺伝子治療臨床研究
	 * 	,A.EKIGAKU			--疫学研究
	 * 	,A.COMMENTS			--コメント
	 * 	,A.TENPU_PATH			--添付ファイル格納パス
	 * 	,DECODE(
	 * 		NVL(A.TENPU_PATH,'null') 
	 * 		,'null','FALSE'		--添付ファイル格納パスがNULLのとき
	 * 		,      'TRUE'		--添付ファイル格納パスがNULL以外のとき
	 * 	) TENPU_FLG			--添付ファイル格納フラグ
	 * 	,A.SHINSA_JOKYO			--審査状況
	 * 	,A.BIKO				--備考
	 * FROM
	 * 	SHINSAKEKKA A
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * 	AND SHINSAIN_NO = ?
	 * 	AND JIGYO_KUBUN = ?
	 * FOR UPDATE</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム番号</td><td>第二引数shinsaKekkaInputInfoの変数SystemNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数shinsaKekkaInputInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数shinsaKekkaInputInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値はShinsaKekkaInfoに格納する。<br /><br />
	 * 
	 * 第二引数のShinsaKekkaInputInfoの中から、HyokaFileResを取得する。<br />
	 * この値がnullでなければ、以下の出力先のパスにHyokaFileResを書き込む。<br /><br />
	 * 
	 * <b>D:/shinsa-kaken/data/</b>事業ID<b>/</b>システム番号<b>/shinsa/</b>審査員番号<b>.</b>拡張子<br /><br />
	 * 
	 * ・事業ID　　　…第二引数shinsaKekkaInputInfoの変数JigyoIdを使用する。<br />
	 * ・システム番号…第二引数shinsaKekkaInputInfoの変数SystemNoを使用する。<br />
	 * ・審査員番号　…第二引数shinsaKekkaInputInfoの変数ShinsainNoを使用する。<br />
	 * ・拡張子　　　…HyokaFileResの拡張子と同じものを使用する。<br /><br />
	 * 
	 * このパスの値を、SQL文で取得したShinsaKekkaInfoの"添付ファイル名"に加える。<br /><br />
	 * 
	 * (2)第一引数userInfoが持つShinsainInfo、第二引数shinsaKekkaInputInfoから、以下の値をShinsaKekkaInfoに加える。<br /><br />
	 * 
	 * ・ShinsainInfoから取得する値(審査員情報)<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>値(和名)</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（漢字-姓）</td><td>ShinsainNameKanjiSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（漢字-名）</td><td>ShinsainNameKanjiMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－姓）</td><td>NameKanaSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－名）</td><td>NameKanaMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員所属機関名</td><td>ShozokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員部局名</td><td>BukyokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員職名</td><td>ShokushuName</td></tr>
	 * </table><br />
	 * 
	 * ・shinsaKekkaInputInfoから取得する値(更新データ)<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>値(和名)</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>総合評価（ABC）</td><td>KekkaAbc</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総合評価（点数）</td><td>KekkaTen</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント1</td><td>Comment1</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント2</td><td>Comment2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント3</td><td>Comment3</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント4</td><td>Comment4</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント5</td><td>Comment5</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント6</td><td>Comment6</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究内容</td><td>KenkyuNaiyo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究計画</td><td>KenkyuKeikaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>適切性-海外</td><td>TekisetsuKaigai</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>適切性-研究(1)</td><td>TekisetsuKenkyu1</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>適切性</td><td>Tekisetsu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>妥当性</td><td>Dato</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究代表者</td><td>Shinseisha</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究分担者</td><td>KenkyuBuntansha</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>ヒトゲノム</td><td>Hitogenomu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>特定胚</td><td>Tokutei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>ヒトES細胞</td><td>HitoEs</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>遺伝子組換え実験</td><td>Kumikae</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>遺伝子治療臨床研究</td><td>Chiryo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>疫学研究</td><td>Ekigaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント</td><td>Comments</td></tr>
	 * </table><br />
	 * 
	 * (3)取得したshinsaKekkaInputInfoの値を使って、審査結果テーブルの更新を行う。
	 * 発行するSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAKEKKA
	 * SET	
	 * 	SYSTEM_NO = ?					--システム受付番号
	 * 	,UKETUKE_NO = ?				--申請番号
	 * 	,SHINSAIN_NO = ?				--審査員番号
	 * 	,JIGYO_KUBUN = ?				--事業区分
	 * 	,SEQ_NO = ?					--シーケンス番号
	 * 	,SHINSA_KUBUN = ?				--審査区分
	 * 	,SHINSAIN_NAME_KANJI_SEI = ?	--審査員名（漢字－姓）
	 * 	,SHINSAIN_NAME_KANJI_MEI = ?	--審査員名（漢字－名）
	 * 	,NAME_KANA_SEI = ?				--審査員名（フリガナ－姓）
	 * 	,NAME_KANA_MEI = ?				--審査員名（フリガナ－名）
	 * 	,SHOZOKU_NAME = ?				--審査員所属機関名
	 * 	,BUKYOKU_NAME = ?				--審査員部局名
	 * 	,SHOKUSHU_NAME = ?				--審査員職名
	 * 	,JIGYO_ID = ?					--事業ID
	 * 	,JIGYO_NAME = ?				--事業名
	 * 	,BUNKASAIMOKU_CD = ?			--細目番号
	 * 	,EDA_NO = ?					--枝番
	 * 	,CHECKDIGIT = ?				--チェックデジット
	 * 	,KEKKA_ABC = ?					--総合評価（ABC）
	 * 	,KEKKA_TEN = ?					--総合評価（点数）
	 * 	,COMMENT1 = ?					--コメント1
	 * 	,COMMENT2 = ?					--コメント2
	 * 	,COMMENT3 = ?					--コメント3
	 * 	,COMMENT4 = ?					--コメント4
	 * 	,COMMENT5 = ?					--コメント5
	 * 	,COMMENT6 = ?					--コメント6
	 * 	,KENKYUNAIYO = ?				--研究内容
	 * 	,KENKYUKEIKAKU = ?				--研究計画
	 * 	,TEKISETSU_KAIGAI = ?			--適切性-海外
	 * 	,TEKISETSU_KENKYU1 = ?			--適切性-研究（1）
	 * 	,TEKISETSU = ?					--適切性
	 * 	,DATO = ?						--妥当性
	 * 	,SHINSEISHA = ?				--研究代表者
	 * 	,KENKYUBUNTANSHA = ?			--研究分担者
	 * 	,HITOGENOMU = ?				--ヒトゲノム
	 * 	,TOKUTEI = ?					--特定胚
	 * 	,HITOES = ?					--ヒトES細胞
	 * 	,KUMIKAE = ?					--遺伝子組換え実験
	 * 	,CHIRYO = ?					--遺伝子治療臨床研究
	 * 	,EKIGAKU = ?					--疫学研究
	 * 	,COMMENTS = ?					--コメント
	 * 	,TENPU_PATH = ?				--添付ファイル格納パス
	 * 	,SHINSA_JOKYO = ?				--審査状況
	 * 	,BIKO = ?						--備考
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * 	AND SHINSAIN_NO = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * バインド変数の値には、すべてshinsaKekkaInputInfoの持つ値を使用する。
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>SystemNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請番号</td><td>UketukeNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>ShinsainNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>JigyoKubun</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>シーケンス番号</td><td>SeqNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査区分</td><td>ShinsaKubun</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（漢字－姓）</td><td>ShinsainNameKanjiSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（漢字－名）</td><td>ShinsainNameKanjiMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－姓）</td><td>NameKanaSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－名）</td><td>NameKanaMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員所属機関名</td><td>ShozokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員部局名</td><td>BukyokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員職名</td><td>ShokushuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>JigyoId</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業名</td><td>JigyoName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>細目番号</td><td>BunkaSaimokuCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>枝番</td><td>EdaNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>チェックデジット</td><td>CheckDigit</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総合評価（ABC）</td><td>KekkaAbc</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総合評価（点数）</td><td>KekkaTen</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント1</td><td>Comment1</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント2</td><td>Comment2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント3</td><td>Comment3</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント4</td><td>Comment4</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント5</td><td>Comment5</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント6</td><td>Comment6</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究内容</td><td>KenkyuNaiyo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究計画</td><td>KenkyuKeikaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>適切性-海外</td><td>TekisetsuKaigai</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>適切性-研究（1）</td><td>TekisetsuKenkyu1</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>適切性</td><td>Tekisetsu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>妥当性</td><td>Dato</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究代表者</td><td>Shinseisha</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究分担者</td><td>KenkyuBuntansha</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>ヒトゲノム</td><td>Hitogenomu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>特定胚</td><td>Tokutei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>ヒトES細胞</td><td>HitoEs</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>遺伝子組換え実験</td><td>Kumikae</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>遺伝子治療臨床研究</td><td>Chiryo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>疫学研究</td><td>Ekigaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>コメント</td><td>Comments</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>添付ファイル格納パス</td><td>TenpuPath</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査状況</td><td>ShinsaJokyo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>Biko</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>SystemNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>ShinsainNo</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 2.申請データ更新<br /><br />
	 * 
	 * (1)以下のSQL文を発行して、該当する申請データを取得する。<br />
	 * (バインド引数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SYSTEM_NO			--システム受付番号
	 * 	,A.UKETUKE_NO			--申請番号
	 * 	,A.JIGYO_ID			--事業ID
	 * 	,A.NENDO				--年度
	 * 	,A.KAISU				--回数
	 * 	,A.JIGYO_NAME			--事業名
	 * 	,A.SHINSEISHA_ID			--申請者ID
	 * 	,A.SAKUSEI_DATE			--申請書作成日
	 * 	,A.SHONIN_DATE			--所属機関承認日
	 * 	,A.JYURI_DATE			--学振受理日
	 * 	,A.NAME_KANJI_SEI			--申請者氏名（漢字等-姓）
	 * 	,A.NAME_KANJI_MEI			--申請者氏名（漢字等-名）
	 * 	,A.NAME_KANA_SEI			--申請者氏名（フリガナ-姓）
	 * 	,A.NAME_KANA_MEI			--申請者氏名（フリガナ-名）
	 * 	,A.NAME_RO_SEI			--申請者氏名（ローマ字-姓）
	 * 	,A.NAME_RO_MEI			--申請者氏名（ローマ字-名）
	 * 	,A.NENREI				--年齢
	 * 	,A.KENKYU_NO			--申請者研究者番号
	 * 	,A.SHOZOKU_CD			--所属機関コード
	 * 	,A.SHOZOKU_NAME			--所属機関名
	 * 	,A.SHOZOKU_NAME_RYAKU		--所属機関名（略称）
	 * 	,A.BUKYOKU_CD			--部局コード
	 * 	,A.BUKYOKU_NAME			--部局名
	 * 	,A.BUKYOKU_NAME_RYAKU		--部局名（略称）
	 * 	,A.SHOKUSHU_CD			--職名コード
	 * 	,A.SHOKUSHU_NAME_KANJI		--職名（和文）
	 * 	,A.SHOKUSHU_NAME_RYAKU		--職名（略称）
	 * 	,A.ZIP				--郵便番号
	 * 	,A.ADDRESS			--住所
	 * 	,A.TEL				--TEL
	 * 	,A.FAX				--FAX
	 * 	,A.EMAIL				--E-Mail
	 * 	,A.SENMON				--現在の専門
	 * 	,A.GAKUI				--学位
	 * 	,A.BUNTAN				--役割分担
	 * 	,A.KADAI_NAME_KANJI		--研究課題名(和文）
	 * 	,A.KADAI_NAME_EIGO			--研究課題名(英文）
	 * 	,A.JIGYO_KUBUN			--事業区分
	 * 	,A.SHINSA_KUBUN			--審査区分
	 * 	,A.SHINSA_KUBUN_MEISHO		--審査区分名称
	 * 	,A.BUNKATSU_NO			--分割番号
	 * 	,A.BUNKATSU_NO_MEISHO		--分割番号名称
	 * 	,A.KENKYU_TAISHO			--研究対象の類型
	 * 	,A.KEI_NAME_NO			--系等の区分番号
	 * 	,A.KEI_NAME			--系等の区分
	 * 	,A.KEI_NAME_RYAKU			--系等の区分略称
	 * 	,A.BUNKASAIMOKU_CD			--細目番号
	 * 	,A.BUNYA_NAME			--分野
	 * 	,A.BUNKA_NAME			--分科
	 * 	,A.SAIMOKU_NAME			--細目
	 * 	,A.BUNKASAIMOKU_CD2		--細目番号2
	 * 	,A.BUNYA_NAME2			--分野2
	 * 	,A.BUNKA_NAME2			--分科2
	 * 	,A.SAIMOKU_NAME2			--細目2
	 * 	,A.KANTEN_NO			--推薦の観点番号
	 * 	,A.KANTEN				--推薦の観点
	 * 	,A.KANTEN_RYAKU			--推薦の観点略称
	 * 	,A.KEIHI1				--1年目研究経費
	 * 	,A.BIHINHI1			--1年目設備備品費
	 * 	,A.SHOMOHINHI1			--1年目消耗品費
	 * 	,A.KOKUNAIRYOHI1			--1年目国内旅費
	 * 	,A.GAIKOKURYOHI1			--1年目外国旅費
	 * 	,A.RYOHI1				--1年目旅費
	 * 	,A.SHAKIN1			--1年目謝金等
	 * 	,A.SONOTA1			--1年目その他
	 * 	,A.KEIHI2				--2年目研究経費
	 * 	,A.BIHINHI2			--2年目設備備品費
	 * 	,A.SHOMOHINHI2			--2年目消耗品費
	 * 	,A.KOKUNAIRYOHI2			--2年目国内旅費
	 * 	,A.GAIKOKURYOHI2			--2年目外国旅費
	 * 	,A.RYOHI2				--2年目旅費
	 * 	,A.SHAKIN2			--2年目謝金等
	 * 	,A.SONOTA2			--2年目その他
	 * 	,A.KEIHI3				--3年目研究経費
	 * 	,A.BIHINHI3			--3年目設備備品費
	 * 	,A.SHOMOHINHI3			--3年目消耗品費
	 * 	,A.KOKUNAIRYOHI3			--3年目国内旅費
	 * 	,A.GAIKOKURYOHI3			--3年目外国旅費
	 * 	,A.RYOHI3				--3年目旅費
	 * 	,A.SHAKIN3			--3年目謝金等
	 * 	,A.SONOTA3			--3年目その他
	 * 	,A.KEIHI4				--4年目研究経費
	 * 	,A.BIHINHI4			--4年目設備備品費
	 * 	,A.SHOMOHINHI4			--4年目消耗品費
	 * 	,A.KOKUNAIRYOHI4			--4年目国内旅費
	 * 	,A.GAIKOKURYOHI4			--4年目外国旅費
	 * 	,A.RYOHI4				--4年目旅費
	 * 	,A.SHAKIN4			--4年目謝金等
	 * 	,A.SONOTA4			--4年目その他
	 * 	,A.KEIHI5				--5年目研究経費
	 * 	,A.BIHINHI5			--5年目設備備品費
	 * 	,A.SHOMOHINHI5			--5年目消耗品費
	 * 	,A.KOKUNAIRYOHI5			--5年目国内旅費
	 * 	,A.GAIKOKURYOHI5			--5年目外国旅費
	 * 	,A.RYOHI5				--5年目旅費
	 * 	,A.SHAKIN5			--5年目謝金等
	 * 	,A.SONOTA5			--5年目その他
	 * 	,A.KEIHI_TOTAL			--総計-研究経費
	 * 	,A.BIHINHI_TOTAL			--総計-設備備品費
	 * 	,A.SHOMOHINHI_TOTAL		--総計-消耗品費
	 * 	,A.KOKUNAIRYOHI_TOTAL		--総計-国内旅費
	 * 	,A.GAIKOKURYOHI_TOTAL		--総計-外国旅費
	 * 	,A.RYOHI_TOTAL			--総計-旅費
	 * 	,A.SHAKIN_TOTAL			--総計-謝金等
	 * 	,A.SONOTA_TOTAL			--総計-その他
	 * 	,A.SOSHIKI_KEITAI_NO		--研究組織の形態番号
	 * 	,A.SOSHIKI_KEITAI			--研究組織の形態
	 * 	,A.BUNTANKIN_FLG			--分担金の有無
	 * 	,A.KOYOHI				--研究支援者雇用経費
	 * 	,A.KENKYU_NINZU			--研究者数
	 * 	,A.TAKIKAN_NINZU			--他機関の分担者数
	 * 	,A.SHINSEI_KUBUN			--新規継続区分
	 * 	,A.KADAI_NO_KEIZOKU		--継続分の研究課題番号
	 * 	,A.SHINSEI_FLG_NO			--申請の有無番号
	 * 	,A.SHINSEI_FLG			--申請の有無
	 * 	,A.KADAI_NO_SAISYU			--最終年度課題番号
	 * 	,A.KAIJIKIBO_FLG_NO		--開示希望の有無番号
	 * 	,A.KAIJIKIBO_FLG			--開示希望の有無
	 * 	,A.KAIGAIBUNYA_CD			--海外分野コード
	 * 	,A.KAIGAIBUNYA_NAME		--海外分野名称
	 * 	,A.KAIGAIBUNYA_NAME_RYAKU		--海外分野略称
	 * 	,A.KANREN_SHIMEI1			--関連分野の研究者-氏名1
	 * 	,A.KANREN_KIKAN1			--関連分野の研究者-所属機関1
	 * 	,A.KANREN_BUKYOKU1			--関連分野の研究者-所属部局1
	 * 	,A.KANREN_SHOKU1			--関連分野の研究者-職名1
	 * 	,A.KANREN_SENMON1			--関連分野の研究者-専門分野1
	 * 	,A.KANREN_TEL1			--関連分野の研究者-勤務先電話番号1
	 * 	,A.KANREN_JITAKUTEL1		--関連分野の研究者-自宅電話番号1
	 * 	,A.KANREN_MAIL1			--関連分野の研究者-E-mail1
	 * 	,A.KANREN_SHIMEI2			--関連分野の研究者-氏名2
	 * 	,A.KANREN_KIKAN2			--関連分野の研究者-所属機関2
	 * 	,A.KANREN_BUKYOKU2			--関連分野の研究者-所属部局2
	 * 	,A.KANREN_SHOKU2			--関連分野の研究者-職名2
	 * 	,A.KANREN_SENMON2			--関連分野の研究者-専門分野2
	 * 	,A.KANREN_TEL2			--関連分野の研究者-勤務先電話番号2
	 * 	,A.KANREN_JITAKUTEL2		--関連分野の研究者-自宅電話番号2
	 * 	,A.KANREN_MAIL2			--関連分野の研究者-E-mail2
	 * 	,A.KANREN_SHIMEI3			--関連分野の研究者-氏名3
	 * 	,A.KANREN_KIKAN3			--関連分野の研究者-所属機関3
	 * 	,A.KANREN_BUKYOKU3			--関連分野の研究者-所属部局3
	 * 	,A.KANREN_SHOKU3			--関連分野の研究者-職名3
	 * 	,A.KANREN_SENMON3			--関連分野の研究者-専門分野3
	 * 	,A.KANREN_TEL3			--関連分野の研究者-勤務先電話番号3
	 * 	,A.KANREN_JITAKUTEL3		--関連分野の研究者-自宅電話番号3
	 * 	,A.KANREN_MAIL3			--関連分野の研究者-E-mail3
	 * 	,A.XML_PATH			--XMLの格納パス
	 * 	,A.PDF_PATH			--PDFの格納パス
	 * 	,A.JURI_KEKKA			--受理結果
	 * 	,A.JURI_BIKO			--受理結果備考
	 * 	,A.SUISENSHO_PATH			--推薦書の格納パス
	 * 	,A.KEKKA1_ABC			--１次審査結果(ABC)
	 * 	,A.KEKKA1_TEN			--１次審査結果(点数)
	 * 	,A.KEKKA1_TEN_SORTED		--１次審査結果(点数順)
	 * 	,A.SHINSA1_BIKO			--１次審査備考
	 * 	,A.KEKKA2				--２次審査結果
	 * 	,A.SOU_KEHI			--総経費（学振入力）
	 * 	,A.SHONEN_KEHI			--初年度経費（学振入力）
	 * 	,A.SHINSA2_BIKO			--業務担当者記入欄
	 * 	,A.JOKYO_ID			--申請状況ID
	 * 	,A.SAISHINSEI_FLG			--再申請フラグ
	 * 	,A.DEL_FLG			--削除フラグ
	 * FROM
	 * 	SHINSEIDATAKANRI A
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * FOR UPDATE</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム番号</td><td>第二引数shinsaKekkaInputInfoの変数SystemNoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値は、existInfoに格納する。<br /><br />
	 * 
	 * existInfoの、DelFlg(削除フラグ)の値が、"1"(削除済み)であった場合には、例外をthrowする。<br />
	 * existInfoの、JokyoId(審査状況ID)の値が、"8","9","10","11"以外であった場合には、例外をthrowする。<br />
	 * 　・8…審査員割り振り処理後<br />
	 * 　・9…割り振りチェック完了<br />
	 * 　・10…1次審査中<br />
	 * 　・11…1次審査完了<br /><br />
	 * 
	 * 
	 * 
	 * 
	 * 
	 * (2)以下のSQL文を発行して、該当する審査結果を取得する。<br />
	 * (バインド引数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SYSTEM_NO		--システム受付番号
	 * 	,A.UKETUKE_NO		--申請番号
	 * 	,A.SHINSAIN_NO		--審査員番号
	 * 	,A.JIGYO_KUBUN		--事業区分
	 * 	,A.SEQ_NO			--シーケンス番号
	 * 	,A.SHINSA_KUBUN		--審査区分
	 * 	,A.SHINSAIN_NAME_KANJI_SEI	--審査員名（漢字－姓）
	 * 	,A.SHINSAIN_NAME_KANJI_MEI	--審査員名（漢字－名）
	 * 	,A.NAME_KANA_SEI		--審査員名（フリガナ－姓）
	 * 	,A.NAME_KANA_MEI		--審査員名（フリガナ－名）
	 * 	,A.SHOZOKU_NAME		--審査員所属機関名
	 * 	,A.BUKYOKU_NAME		--審査員部局名
	 * 	,A.SHOKUSHU_NAME		--審査員職名
	 * 	,A.JIGYO_ID			--事業ID
	 * 	,A.JIGYO_NAME		--事業名
	 * 	,A.BUNKASAIMOKU_CD		--細目番号
	 * 	,A.EDA_NO			--枝番
	 * 	,A.CHECKDIGIT		--チェックデジット
	 * 	,A.KEKKA_ABC		--総合評価（ABC）
	 * 	,A.KEKKA_TEN		--総合評価（点数）
	 * 	,NVL(
	 * 		REPLACE(
	 * 			A.KEKKA_TEN,
	 * 			'-',
	 * 			'0'
	 * 		),'-1'
	 * 	) SORT_KEKKA_TEN
	 * 		--ソート用。審査結果（点数）の値NULL→'-1'、'-'→'0'に置換）
	 * 	,A.COMMENT1		--コメント1
	 * 	,A.COMMENT2		--コメント2
	 * 	,A.COMMENT3		--コメント3
	 * 	,A.COMMENT4		--コメント4
	 * 	,A.COMMENT5		--コメント5
	 * 	,A.COMMENT6		--コメント6
	 * 	,A.KENKYUNAIYO		--研究内容
	 * 	,A.KENKYUKEIKAKU		--研究計画
	 * 	,A.TEKISETSU_KAIGAI	--適切性-海外
	 * 	,A.TEKISETSU_KENKYU1	--適切性-研究（1）
	 * 	,A.TEKISETSU		--適切性
	 * 	,A.DATO			--妥当性
	 * 	,A.SHINSEISHA		--研究代表者
	 * 	,A.KENKYUBUNTANSHA		--研究分担者
	 * 	,A.HITOGENOMU		--ヒトゲノム
	 * 	,A.TOKUTEI		--特定胚
	 * 	,A.HITOES			--ヒトES細胞
	 * 	,A.KUMIKAE		--遺伝子組換え実験
	 * 	,A.CHIRYO			--遺伝子治療臨床研究
	 * 	,A.EKIGAKU		--疫学研究
	 * 	,A.COMMENTS		--コメント
	 * 	,A.TENPU_PATH		--添付ファイル格納パス
	 * 	,A.SHINSA_JOKYO		--審査状況
	 * 	,A.BIKO			--備考
	 * FROM
	 * 	SHINSAKEKKA A
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * ORDER BY
	 * 	KEKKA_ABC ASC		--総合評価（ABC）の昇順
	 * 	,SORT_KEKKA_TEN DESC	--総合評価（点数）の降順
	 * 	,SHINSAIN_NO ASC		--審査員番号の昇順
	 * 	,JIGYO_KUBUN ASC		--事業区分の昇順</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム番号</td><td>第二引数shinsaKekkaInputInfoの変数SystemNoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値は、ShinsaKekkaInfoの配列に格納する。<br /><br />
	 * 
	 * 
	 * 
	 * 
	 * (3)以下のSQL文を発行して、総合評価マスタから総合評価情報の一覧のListを取得する。<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SOGO_HYOKA		--総合評価
	 * 	,A.JIGYO_KUBUN		--事業区分
	 * 	,A.TENSU			--点数
	 * 	,A.BIKO				--備考
	 * FROM
	 * 	MASTER_SOGOHYOKA A
	 * ORDER BY
	 * 	JIGYO_KUBUN
	 * 	,SOGO_HYOKA</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * 取得したList内のMapから、<br /><br />
	 * 
	 * ・総合評価<br />
	 * ・事業区分<br />
	 * ・点数<br /><br />
	 * 
	 * を取り出す。そして、HashMapオブジェクト"sogoHyokaMap"を生成し、
	 * 事業区分と総合評価を連結させたStringをキーに、点数を格納する。<br />
	 * 
	 * この作業を、List内のMapの数だけ行う。<br /><br />
	 * 
	 * 
	 * 
	 * 
	 * (4)(2)で取得したShinsaKekkaInfoの配列の、総合評価（ABC）と総合評価（点数）の値をチェックする。<br />
	 * 総合評価（ABC）と総合評価（点数）は混在しないので、どちらの値を持つかを見て、場合わけを行う。<br />
	 * この処理を、ShinsaKekkaInfoの配列の数だけ繰り返す。<br /><br />
	 * 
	 * 　(4-1)総合評価（ABC）の値を持つ場合<br /><br />
	 * 
	 * 　String"KekkaAbc"を連結して足し合わせていく。
	 * 　また、ShinsaKekkaInfoの事業区分と総合評価（ABC）の値を用いて、(3)のMapから"点数"の値を取得する。
	 * 　値がない場合には例外をthrowする。<br />
	 * 
	 * 　取得した点数は、intとして足し合わせる。<br /><br />
	 * 
	 * 　(4-2)総合評価（点数）の値を持つ場合<br /><br />
	 * 
	 * 　ShinsaKekkaInfoの事業区分と総合評価（点数）の値を用いて、(3)のMapから"点数"の値を取得する。<br />
	 * 
	 * 　取得した点数は、intとして足し合わせる。(4-1で足し合わせているものに足していく)<br />
	 * 　また、点数をStringとして、間に"　"をはさんで連結していく。<br /><br />
	 * 
	 * 
	 * 
	 * (5)次の値を、(1)で取得したexistInfoに加える。<br /><br />
	 * 
	 * ・kekka1Abc(一次審査結果(ABC))…(4-1)でString"KekkaAbc"を連結して足し合わせたもの。<br />
	 * ・kekka1Ten(一次審査結果(点数))…(4-1)、(4-2)で足し合わせた点数のintを、Stringにしたもの。<br />
	 * ・kekka1TenSorted(一次審査結果(点数順))…(4-2)で点数をStringとして、間に"　"をはさんで連結したもの。<br />
	 * ・jokyoId(審査状況)…"11"(1次審査完了)<br /><br />
	 * 
	 * (6)取得したexistInfoと以下のSQL文を使用して、申請データテーブルの更新を行う。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSEIDATAKANRI
	 * SET			
	 * 	SYSTEM_NO = ?				--システム受付番号
	 * 	,UKETUKE_NO = ?				--申請番号
	 * 	,JIGYO_ID = ?				--事業ID
	 * 	,NENDO = ?				--年度
	 * 	,KAISU = ?				--回数
	 * 	,JIGYO_NAME = ?				--事業名
	 * 	,SHINSEISHA_ID = ?				--申請者ID
	 * 	,SAKUSEI_DATE = ?				--申請書作成日
	 * 	,SHONIN_DATE = ?				--所属機関承認日
	 * 	,JYURI_DATE = ?				--学振受理日
	 * 	,NAME_KANJI_SEI = ?			--申請者氏名（漢字等-姓）
	 * 	,NAME_KANJI_MEI = ?			--申請者氏名（漢字等-名）
	 * 	,NAME_KANA_SEI = ?				--申請者氏名（フリガナ-姓）
	 * 	,NAME_KANA_MEI = ?				--申請者氏名（フリガナ-名）
	 * 	,NAME_RO_SEI = ?				--申請者氏名（ローマ字-姓）
	 * 	,NAME_RO_MEI = ?				--申請者氏名（ローマ字-名）
	 * 	,NENREI = ?				--年齢
	 * 	,KENKYU_NO = ?				--申請者研究者番号
	 * 	,SHOZOKU_CD = ?				--所属機関コード
	 * 	,SHOZOKU_NAME = ?				--所属機関名
	 * 	,SHOZOKU_NAME_RYAKU = ?			--所属機関名（略称）
	 * 	,BUKYOKU_CD = ?				--部局コード
	 * 	,BUKYOKU_NAME = ?				--部局名
	 * 	,BUKYOKU_NAME_RYAKU = ?			--部局名（略称）
	 * 	,SHOKUSHU_CD = ?				--職名コード
	 * 	,SHOKUSHU_NAME_KANJI = ?			--職名（和文）
	 * 	,SHOKUSHU_NAME_RYAKU = ?			--職名（略称）
	 * 	,ZIP = ?					--郵便番号
	 * 	,ADDRESS = ?				--住所
	 * 	,TEL = ?					--TEL
	 * 	,FAX = ?					--FAX
	 * 	,EMAIL = ?				--E-Mail
	 * 	,SENMON = ?				--現在の専門
	 * 	,GAKUI = ?				--学位
	 * 	,BUNTAN = ?				--役割分担
	 * 	,KADAI_NAME_KANJI = ?			--研究課題名(和文）
	 * 	,KADAI_NAME_EIGO = ?			--研究課題名(英文）
	 * 	,JIGYO_KUBUN = ?				--事業区分
	 * 	,SHINSA_KUBUN = ?				--審査区分
	 * 	,SHINSA_KUBUN_MEISHO = ?			--審査区分名称
	 * 	,BUNKATSU_NO = ?				--分割番号
	 * 	,BUNKATSU_NO_MEISHO = ?			--分割番号名称
	 * 	,KENKYU_TAISHO = ?				--研究対象の類型
	 * 	,KEI_NAME_NO = ?				--系等の区分番号
	 * 	,KEI_NAME = ?				--系等の区分
	 * 	,KEI_NAME_RYAKU = ?			--系等の区分略称
	 * 	,BUNKASAIMOKU_CD = ?			--細目番号
	 * 	,BUNYA_NAME = ?				--分野
	 * 	,BUNKA_NAME = ?				--分科
	 * 	,SAIMOKU_NAME = ?				--細目
	 * 	,BUNKASAIMOKU_CD2 = ?			--細目番号2
	 * 	,BUNYA_NAME2 = ?				--分野2
	 * 	,BUNKA_NAME2 = ?				--分科2
	 * 	,SAIMOKU_NAME2 = ?				--細目2
	 * 	,KANTEN_NO = ?				--推薦の観点番号
	 * 	,KANTEN = ?				--推薦の観点
	 * 	,KANTEN_RYAKU = ?		--推薦の観点略称
	 * 	,KEIHI1 = ?				--1年目研究経費
	 * 	,BIHINHI1 = ?			--1年目設備備品費
	 * 	,SHOMOHINHI1 = ?		--1年目消耗品費
	 * 	,KOKUNAIRYOHI1 = ?		--1年目国内旅費
	 * 	,GAIKOKURYOHI1 = ?		--1年目外国旅費
	 * 	,RYOHI1 = ?				--1年目旅費
	 * 	,SHAKIN1 = ?			--1年目謝金等
	 * 	,SONOTA1 = ?			--1年目その他
	 * 	,KEIHI2 = ?				--2年目研究経費
	 * 	,BIHINHI2 = ?			--2年目設備備品費
	 * 	,SHOMOHINHI2 = ?		--2年目消耗品費
	 * 	,KOKUNAIRYOHI2 = ?		--2年目国内旅費
	 * 	,GAIKOKURYOHI2 = ?		--2年目外国旅費
	 * 	,RYOHI2 = ?				--2年目旅費
	 * 	,SHAKIN2 = ?			--2年目謝金等
	 * 	,SONOTA2 = ?			--2年目その他
	 * 	,KEIHI3 = ?				--3年目研究経費
	 * 	,BIHINHI3 = ?			--3年目設備備品費
	 * 	,SHOMOHINHI3 = ?		--3年目消耗品費
	 * 	,KOKUNAIRYOHI3 = ?		--3年目国内旅費
	 * 	,GAIKOKURYOHI3 = ?		--3年目外国旅費
	 * 	,RYOHI3 = ?				--3年目旅費
	 * 	,SHAKIN3 = ?			--3年目謝金等
	 * 	,SONOTA3 = ?			--3年目その他
	 * 	,KEIHI4 = ?				--4年目研究経費
	 * 	,BIHINHI4 = ?			--4年目設備備品費
	 * 	,SHOMOHINHI4 = ?		--4年目消耗品費
	 * 	,KOKUNAIRYOHI4 = ?		--4年目国内旅費
	 * 	,GAIKOKURYOHI4 = ?		--4年目外国旅費
	 * 	,RYOHI4 = ?				--4年目旅費
	 * 	,SHAKIN4 = ?			--4年目謝金等
	 * 	,SONOTA4 = ?			--4年目その他
	 * 	,KEIHI5 = ?				--5年目研究経費
	 * 	,BIHINHI5 = ?			--5年目設備備品費
	 * 	,SHOMOHINHI5 = ?		--5年目消耗品費
	 * 	,KOKUNAIRYOHI5 = ?		--5年目国内旅費
	 * 	,GAIKOKURYOHI5 = ?		--5年目外国旅費
	 * 	,RYOHI5 = ?				--5年目旅費
	 * 	,SHAKIN5 = ?			--5年目謝金等
	 * 	,SONOTA5 = ?			--5年目その他
	 * 	,KEIHI_TOTAL = ?		--総計-研究経費
	 * 	,BIHINHI_TOTAL = ?		--総計-設備備品費
	 * 	,SHOMOHINHI_TOTAL = ?	--総計-消耗品費
	 * 	,KOKUNAIRYOHI_TOTAL = ?	--総計-国内旅費
	 * 	,GAIKOKURYOHI_TOTAL = ?	--総計-外国旅費
	 * 	,RYOHI_TOTAL = ?		--総計-旅費
	 * 	,SHAKIN_TOTAL = ?		--総計-謝金等
	 * 	,SONOTA_TOTAL = ?		--総計-その他
	 * 	,SOSHIKI_KEITAI_NO = ?		--研究組織の形態番号
	 * 	,SOSHIKI_KEITAI = ?			--研究組織の形態
	 * 	,BUNTANKIN_FLG = ?			--分担金の有無
	 * 	,KOYOHI = ?				--研究支援者雇用経費
	 * 	,KENKYU_NINZU = ?			--研究者数
	 * 	,TAKIKAN_NINZU = ?			--他機関の分担者数
	 * 	,SHINSEI_KUBUN = ?			--新規継続区分
	 * 	,KADAI_NO_KEIZOKU = ?		--継続分の研究課題番号
	 * 	,SHINSEI_FLG_NO = ?			--研究計画最終年度前年度の応募
	 * 	,SHINSEI_FLG = ?				--申請の有無
	 * 	,KADAI_NO_SAISYU = ?			--最終年度課題番号
	 * 	,KAIJIKIBO_FLG_NO = ?			--開示希望の有無番号
	 * 	,KAIJIKIBO_FLG = ?				--開示希望の有無
	 * 	,KAIGAIBUNYA_CD = ?			--海外分野コード
	 * 	,KAIGAIBUNYA_NAME = ?			--海外分野名称
	 * 	,KAIGAIBUNYA_NAME_RYAKU = ?			--海外分野略称
	 * 	,KANREN_SHIMEI1 = ?			--関連分野の研究者-氏名1
	 * 	,KANREN_KIKAN1 = ?			--関連分野の研究者-所属機関1
	 * 	,KANREN_BUKYOKU1 = ?		--関連分野の研究者-所属部局1
	 * 	,KANREN_SHOKU1 = ?			--関連分野の研究者-職名1
	 * 	,KANREN_SENMON1 = ?			--関連分野の研究者-専門分野1
	 * 	,KANREN_TEL1 = ?			--関連分野の研究者-勤務先電話番号1
	 * 	,KANREN_JITAKUTEL1 = ?		--関連分野の研究者-自宅電話番号1
	 * 	,KANREN_MAIL1 = ?			--関連分野の研究者-E-mail1
	 * 	,KANREN_SHIMEI2 = ?			--関連分野の研究者-氏名2
	 * 	,KANREN_KIKAN2 = ?			--関連分野の研究者-所属機関2
	 * 	,KANREN_BUKYOKU2 = ?		--関連分野の研究者-所属部局2
	 * 	,KANREN_SHOKU2 = ?			--関連分野の研究者-職名2
	 * 	,KANREN_SENMON2 = ?			--関連分野の研究者-専門分野2
	 * 	,KANREN_TEL2 = ?			--関連分野の研究者-勤務先電話番号2
	 * 	,KANREN_JITAKUTEL2 = ?		--関連分野の研究者-自宅電話番号2
	 * 	,KANREN_MAIL2 = ?			--関連分野の研究者-E-mail2
	 * 	,KANREN_SHIMEI3 = ?			--関連分野の研究者-氏名3
	 * 	,KANREN_KIKAN3 = ?			--関連分野の研究者-所属機関3
	 * 	,KANREN_BUKYOKU3 = ?		--関連分野の研究者-所属部局3
	 * 	,KANREN_SHOKU3 = ?			--関連分野の研究者-職名3
	 * 	,KANREN_SENMON3 = ?			--関連分野の研究者-専門分野3
	 * 	,KANREN_TEL3 = ?			--関連分野の研究者-勤務先電話番号3
	 * 	,KANREN_JITAKUTEL3 = ?		--関連分野の研究者-自宅電話番号3
	 * 	,KANREN_MAIL3 = ?			--関連分野の研究者-E-mail3
	 * 	,XML_PATH = ?				--XMLの格納パス
	 * 	,PDF_PATH = ?				--PDFの格納パス
	 * 	,JURI_KEKKA = ?				--受理結果
	 * 	,JURI_BIKO = ?				--受理結果備考
	 * 	,SUISENSHO_PATH = ?			--推薦書の格納パス
	 * 	,KEKKA1_ABC = ?				--１次審査結果(ABC)
	 * 	,KEKKA1_TEN = ?				--１次審査結果(点数)
	 * 	,KEKKA1_TEN_SORTED = ?			--１次審査結果(点数順)
	 * 	,SHINSA1_BIKO = ?				--１次審査備考
	 * 	,KEKKA2 = ?				--２次審査結果
	 * 	,SOU_KEHI = ?				--総経費（学振入力）
	 * 	,SHONEN_KEHI = ?				--初年度経費（学振入力）
	 * 	,SHINSA2_BIKO = ?				--業務担当者記入欄
	 * 	,JOKYO_ID = ?				--申請状況ID
	 * 	,SAISHINSEI_FLG = ?			--再申請フラグ
	 * 	,DEL_FLG = ?				--削除フラグ
	 * WHERE
	 * 	SYSTEM_NO = ?;				--システム受付番号</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * 値は、ShinseiDataInfoの変数である。<br />
	 * 値に添え字のあるものは、それぞれ、ShinseiDataInfoが持つオブジェクトの値。<br /><br />
	 * 
	 * 　・A:DaihyouInfo<br />
	 * 　・B:KadaiInfo<br />
	 * 　・C:KenkyuKeihiInfo<br />
	 * 　・D:KenkyuKeihiSoukeiInfo<br />
	 * 　・E:KanrenBunyaKenkyushaInfo<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>SystemNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請番号</td><td>uketukeNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>jigyoId</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>年度</td><td>nendo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>回数</td><td>kaisu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業名</td><td>jigyoName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者ID</td><td>ShinseishaId</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請書作成日</td><td>SakuseiDate</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関承認日</td><td>ShoninDate</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>学振受理日</td><td>JyuriDate</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者氏名（漢字等-姓）</td><td>A:NameKanjiSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者氏名（漢字等-名）</td><td>A:NameKanjiMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者氏名（フリガナ-姓）</td><td>A:NameKanaSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者氏名（フリガナ-名）</td><td>A:NameKanaMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者氏名（ローマ字-姓）</td><td>A:NameRoSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者氏名（ローマ字-名）</td><td>A:NameRoMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>年齢</td><td>A:Nenrei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者研究者番号</td><td>A:KenkyuNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>A:ShozokuCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名</td><td>A:ShozokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名（略称）</td><td>A:ShozokuNameRyaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局コード</td><td>A:BukyokuCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名</td><td>A:BukyokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名（略称）</td><td>A:BukyokuNameRyaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名コード</td><td>A:ShokushuCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名（和文）</td><td>A:ShokushuNameKanji</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名（略称）</td><td>A:ShokushuNameRyaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>郵便番号</td><td>A:Zip</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>住所</td><td>A:Address</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>TEL</td><td>A:Tel</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>FAX</td><td>A:Fax</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>E-Mail</td><td>A:Email</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>現在の専門</td><td>A:Senmon</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>学位</td><td>A:Gakui</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>役割分担</td><td>A:Buntan</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究課題名(和文）</td><td>B:KadaiNameKanji</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究課題名(英文）</td><td>B:KadaiNameEigo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>B:JigyoKubun</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査区分</td><td>B:ShinsaKubun</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査区分名称</td><td>B:ShinsaKubunMeisho</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>分割番号</td><td>B:BunkatsuNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>分割番号名称</td><td>B:BunkatsuNoMeisho</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究対象の類型</td><td>B:KenkyuTaisho</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>系等の区分番号</td><td>B:KeiNameNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>系等の区分</td><td>B:KeiName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>系等の区分略称</td><td>B:KeiNameRyaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>細目番号</td><td>B:BunkaSaimokuCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>分野</td><td>B:Bunya</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>分科</td><td>B:Bunka</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>細目</td><td>B:SaimokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>細目番号2</td><td>B:BunkaSaimokuCd2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>分野2</td><td>B:Bunya2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>分科2</td><td>B:Bunka2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>細目2</td><td>B:SaimokuName2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>推薦の観点番号</td><td>B:KantenNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>推薦の観点</td><td>B:Kanten</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>推薦の観点略称</td><td>B:KantenRyaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1年目研究経費</td><td>C:Keihi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1年目設備備品費</td><td>C:Bihinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1年目消耗品費</td><td>C:Shomohinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1年目国内旅費</td><td>C:Kokunairyohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1年目外国旅費</td><td>C:Gaikokuryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1年目旅費</td><td>C:Ryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1年目謝金等</td><td>C:Shakin</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1年目その他</td><td>C:Sonota</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2年目研究経費</td><td>C:Keihi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2年目設備備品費</td><td>C:Bihinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2年目消耗品費</td><td>C:Shomohinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2年目国内旅費</td><td>C:Kokunairyohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2年目外国旅費</td><td>C:Gaikokuryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2年目旅費</td><td>C:Ryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2年目謝金等</td><td>C:Shakin</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2年目その他</td><td>C:Sonota</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3年目研究経費</td><td>C:Keihi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3年目設備備品費</td><td>C:Bihinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3年目消耗品費</td><td>C:Shomohinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3年目国内旅費</td><td>C:Kokunairyohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3年目外国旅費</td><td>C:Gaikokuryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3年目旅費</td><td>C:Ryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3年目謝金等</td><td>C:Shakin</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3年目その他</td><td>C:Sonota</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4年目研究経費</td><td>C:Keihi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4年目設備備品費</td><td>C:Bihinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4年目消耗品費</td><td>C:Shomohinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4年目国内旅費</td><td>C:Kokunairyohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4年目外国旅費</td><td>C:Gaikokuryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4年目旅費</td><td>C:Ryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4年目謝金等</td><td>C:Shakin</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4年目その他</td><td>C:Sonota</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5年目研究経費</td><td>C:Keihi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5年目設備備品費</td><td>C:Bihinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5年目消耗品費</td><td>C:Shomohinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5年目国内旅費</td><td>C:Kokunairyohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5年目外国旅費</td><td>C:Gaikokuryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5年目旅費</td><td>C:Ryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5年目謝金等</td><td>C:Shakin</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5年目その他</td><td>C:Sonota</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総計-研究経費</td><td>D:KeihiTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総計-設備備品費</td><td>D:BihinhiTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総計-消耗品費</td><td>D:ShomohinhiTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総計-国内旅費</td><td>D:KokunairyohiTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総計-外国旅費</td><td>D:GaikokuryohiTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総計-旅費</td><td>D:RyohiTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総計-謝金等</td><td>D:ShakinTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総計-その他</td><td>D:SonotaTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究組織の形態番号</td><td>soshikiKeitaiNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究組織の形態</td><td>soshikiKeitai</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>分担金の有無</td><td>buntankinFlg</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究支援者雇用経費</td><td>koyohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者数</td><td>kenkyuNinzu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>他機関の分担者数</td><td>takikanNinzu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>新規継続区分</td><td>shinseiKubun</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>継続分の研究課題番号</td><td>kadaiNoKeizoku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究計画最終年度前年度の応募</td><td>shinseiFlgNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請の有無</td><td>shinseiFlg</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>最終年度課題番号</td><td>kadaiNoSaisyu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>開示希望の有無番号</td><td>kaijikiboFlgNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>開示希望の有無</td><td>kaijiKiboFlg</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>海外分野コード</td><td>kaigaibunyaCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>海外分野名称</td><td>kaigaibunyaName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>海外分野略称</td><td>kaigaibunyaNameRyaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-氏名1</td><td>E:KanrenShimei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-所属機関1</td><td>E:KanrenKikan</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-所属部局1</td><td>E:KanrenBukyoku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-職名1</td><td>E:KanrenShoku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-専門分野1</td><td>E:KanrenSenmon</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-勤務先電話番号1</td><td>E:KanrenTel</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-自宅電話番号1</td><td>E:KanrenJitakuTel</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-E-mail1</td><td>E:KanrenMail</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-氏名2</td><td>E:KanrenShimei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-所属機関2</td><td>E:KanrenKikan</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-所属部局2</td><td>E:KanrenBukyoku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-職名2</td><td>E:KanrenShoku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-専門分野2</td><td>E:KanrenSenmon</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-勤務先電話番号2</td><td>E:KanrenTel</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-自宅電話番号2</td><td>E:KanrenJitakuTel</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>関連分野の研究者-E-mail2</td><td>E:KanrenMail</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>XMLの格納パス</td><td>XmlPath</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>PDFの格納パス</td><td>PdfPath</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>受理結果</td><td>JuriKekka</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>受理結果備考</td><td>JuriBiko</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>推薦書の格納パス</td><td>SuisenshoPath</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>１次審査結果(ABC)</td><td>Kekka1Abc</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>１次審査結果(点数)</td><td>Kekka1Ten</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>１次審査結果(点数順)</td><td>Kekka1TenSorted</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>１次審査備考</td><td>Shinsa1Biko</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>２次審査結果</td><td>Kekka2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総経費（学振入力）</td><td>SouKehi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>初年度経費（学振入力）</td><td>ShonenKehi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当者記入欄</td><td>Shinsa2Biko</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請状況ID</td><td>JokyoId</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>再申請フラグ</td><td>SaishinseiFlg</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>DelFlg</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>systemNo</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param shinsaKekkaInputInfo ShinsaKekkaInputInfo
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void regist1stShinsaKekka(
		UserInfo userInfo,
		ShinsaKekkaInputInfo shinsaKekkaInputInfo)
		throws NoDataFoundException, ApplicationException
	{
		Connection   connection  = null;
		boolean     success     = false;
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			
			//システム受付番号
			String systemNo = shinsaKekkaInputInfo.getSystemNo();
			
			//--------------------
			// 審査結果登録
			//--------------------						
			//審査結果DAO
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);			
			//既存データを取得する
			ShinsaKekkaPk shinsaKekkaPk = (ShinsaKekkaPk)shinsaKekkaInputInfo;
			ShinsaKekkaInfo shinsaKekkaInfo = null;
			try{
				shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfoForLock(connection, shinsaKekkaPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果情報取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}			

			//---審査結果ファイル格納---
			FileResource kekkaFileRes = shinsaKekkaInputInfo.getHyokaFileRes();
			String outPath = null;
			if(kekkaFileRes != null){
				//---ファイル情報---
				String   jigyoId    = shinsaKekkaInputInfo.getJigyoId();
				String   shinsainNo = shinsaKekkaInputInfo.getShinsainNo();
				String extension = FileUtil.getExtention(kekkaFileRes.getName());		//拡張子
				String[] pathInfo   = new String[]{jigyoId, systemNo, shinsainNo, extension};	
				outPath             = MessageFormat.format(SHINSEI_KEKKA_FOLDER, pathInfo);	//出力先
				File     outFile    = new File(outPath);
			
				//拡張子を除いたファイル名が審査員番号と同じファイルは削除
				File[] list = outFile.getParentFile().listFiles();
				if (list != null && list.length > 0) {
					for (int i = 0; i < list.length; i++) {
						int index = list[i].getName().lastIndexOf(".");
						if(index > -1){
							String str = list[i].getName().substring(0, index);
							if(shinsainNo.equals(str)){
								FileUtil.delete(list[i]);
							}
						}
					}
				}
			
				try{
					FileUtil.writeFile(outFile, kekkaFileRes.getBinary());
				}catch(IOException e){
					throw new ApplicationException(
						"審査結果ファイル格納中にエラーが発生しました。",
						new ErrorInfo("errors.7001"),
						e);
				}
				//-------添付ファイル名をセット
				shinsaKekkaInfo.setTenpuPath(outPath);								//添付ファイルパス
			}

			
			//---DB更新---
			try {
				//更新データをセットする		
				shinsaKekkaInfo.setKekkaAbc(shinsaKekkaInputInfo.getKekkaAbc());				//総合評価（ABC）
				shinsaKekkaInfo.setKekkaTen(shinsaKekkaInputInfo.getKekkaTen());				//総合評価（点数）
				shinsaKekkaInfo.setComment1(shinsaKekkaInputInfo.getComment1());				//コメント1
				shinsaKekkaInfo.setComment2(shinsaKekkaInputInfo.getComment2());				//コメント2
				shinsaKekkaInfo.setComment3(shinsaKekkaInputInfo.getComment3());				//コメント3
				shinsaKekkaInfo.setComment4(shinsaKekkaInputInfo.getComment4());				//コメント4
				shinsaKekkaInfo.setComment5(shinsaKekkaInputInfo.getComment5());				//コメント5
				shinsaKekkaInfo.setComment6(shinsaKekkaInputInfo.getComment6());				//コメント6
				shinsaKekkaInfo.setKenkyuNaiyo(shinsaKekkaInputInfo.getKenkyuNaiyo());			//研究内容
				shinsaKekkaInfo.setKenkyuKeikaku(shinsaKekkaInputInfo.getKenkyuKeikaku());		//研究計画
				shinsaKekkaInfo.setTekisetsuKaigai(shinsaKekkaInputInfo.getTekisetsuKaigai());	//適切性-海外
				shinsaKekkaInfo.setTekisetsuKenkyu1(shinsaKekkaInputInfo.getTekisetsuKenkyu1());//適切性-研究(1)
				shinsaKekkaInfo.setTekisetsu(shinsaKekkaInputInfo.getTekisetsu());				//適切性
				shinsaKekkaInfo.setDato(shinsaKekkaInputInfo.getDato());						//妥当性
				shinsaKekkaInfo.setShinseisha(shinsaKekkaInputInfo.getShinseisha());			//研究代表者
				shinsaKekkaInfo.setKenkyuBuntansha(shinsaKekkaInputInfo.getKenkyuBuntansha());	//研究分担者
				shinsaKekkaInfo.setHitogenomu(shinsaKekkaInputInfo.getHitogenomu());			//ヒトゲノム
				shinsaKekkaInfo.setTokutei(shinsaKekkaInputInfo.getTokutei());					//特定胚
				shinsaKekkaInfo.setHitoEs(shinsaKekkaInputInfo.getHitoEs());					//ヒトES細胞
				shinsaKekkaInfo.setKumikae(shinsaKekkaInputInfo.getKumikae());					//遺伝子組換え実験
				shinsaKekkaInfo.setChiryo(shinsaKekkaInputInfo.getChiryo());					//遺伝子治療臨床研究
				shinsaKekkaInfo.setEkigaku(shinsaKekkaInputInfo.getEkigaku());					//疫学研究
				shinsaKekkaInfo.setComments(shinsaKekkaInputInfo.getComments());				//コメント
				
				//2005.10.26 kainuma
				shinsaKekkaInfo.setRigai(shinsaKekkaInputInfo.getRigai());						//利害関係
				shinsaKekkaInfo.setWakates(shinsaKekkaInputInfo.getWakates());					//若手S　2007/5/9
				shinsaKekkaInfo.setJuyosei(shinsaKekkaInputInfo.getJuyosei());					//学術的重要性・妥当性
				shinsaKekkaInfo.setDokusosei(shinsaKekkaInputInfo.getDokusosei());				//独創性・革新性
				shinsaKekkaInfo.setHakyukoka(shinsaKekkaInputInfo.getHakyukoka());				//波及効果・普遍性
				shinsaKekkaInfo.setSuikonoryoku(shinsaKekkaInputInfo.getSuikonoryoku());		//遂行能力・環境の適切性
				shinsaKekkaInfo.setJinken(shinsaKekkaInputInfo.getJinken());					//人権の保護・法令等の遵守
				shinsaKekkaInfo.setBuntankin(shinsaKekkaInputInfo.getBuntankin());				//分担金配分
				shinsaKekkaInfo.setOtherComment(shinsaKekkaInputInfo.getOtherComment());		//その他コメント
				//
				
				//審査員情報をセットする
				shinsaKekkaInfo.setShinsainNameKanjiSei(userInfo.getShinsainInfo().getNameKanjiSei());	//審査員名（漢字-姓）
				shinsaKekkaInfo.setShinsainNameKanjiMei(userInfo.getShinsainInfo().getNameKanjiMei());	//審査員名（漢字-名）
				shinsaKekkaInfo.setNameKanaSei(userInfo.getShinsainInfo().getNameKanaSei());			//審査員名（フリガナ－姓）
				shinsaKekkaInfo.setNameKanaMei(userInfo.getShinsainInfo().getNameKanaMei());			//審査員名（フリガナ－名）
				shinsaKekkaInfo.setShozokuName(userInfo.getShinsainInfo().getShozokuName());			//審査員所属機関名				
				shinsaKekkaInfo.setBukyokuName(userInfo.getShinsainInfo().getBukyokuName());			//審査員部局名
				shinsaKekkaInfo.setShokushuName(userInfo.getShinsainInfo().getShokushuName());			//審査員職名
								
				shinsaDao.updateShinsaKekkaInfo(connection, shinsaKekkaInfo);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"審査結果情報更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} catch (ApplicationException e) {
				throw new ApplicationException(
					"審査結果情報更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			}			
					
			//--------------------
			// 申請データ更新
			//--------------------			
			//申請データ管理DAO
			ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo);
						
			//排他制御のため既存データを取得する
			ShinseiDataPk shinseiDataPk = new ShinseiDataPk(systemNo);
			ShinseiDataInfo existInfo = null;
			try{
				existInfo = dao.selectShinseiDataInfoForLock(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"申請書管理データ排他取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}

			//---申請データ削除フラグチェック---
			String delFlag = existInfo.getDelFlg(); 
			if(FLAG_APPLICATION_DELETE.equals(delFlag))
			{
				throw new ApplicationException(
					"当該申請データは削除されています。SystemNo=" + systemNo,
					new ErrorInfo("errors.9001"));
			}			
			//---申請データステータスチェック---
			String jyokyoId = existInfo.getJokyoId();
			//---審査員割り振り処理後、割り振りチェック完了、1次審査中、1次審査完了以外の場合はエラー
			if( !(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO.equals(jyokyoId)) &&
				 !(StatusCode.STATUS_WARIFURI_CHECK_KANRYO.equals(jyokyoId)) &&
				 !(StatusCode.STATUS_1st_SHINSATYU.equals(jyokyoId)) &&
				 !(StatusCode.STATUS_1st_SHINSA_KANRYO.equals(jyokyoId)) )
			{
				throw new ApplicationException(
					"当該申請データは１次審査登録可能なステータスではありません。SystemNo="
					+ systemNo,
					new ErrorInfo("errors.9012"));
			}			
			
			//---審査期限チェック---
			//科研費は、審査期限過ぎても入力ＯＫ
//			if(!checkShinsaKigen(userInfo, existInfo)){
//				throw new ApplicationException(
//					"当該事業は審査期限を過ぎています。SystemNo="
//					+ systemNo,
//					new ErrorInfo("errors.9007"));
//			}

			//---審査結果レコード取得（結果ABCの昇順）---
			ShinsaKekkaInfo[] shinsaKekkaInfoArray = null;						
			try{
				shinsaKekkaInfoArray = shinsaDao.selectShinsaKekkaInfo(connection, shinseiDataPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}

			//---総合評価マスタから総合評価情報の一覧を取得する---					
			HashMap sogoHyokaMap = new HashMap();
			List hyokaList = MasterSogoHyokaInfoDao.selectSogoHyokaInfoList(connection);	
			Iterator iterator = hyokaList.iterator();
			while(iterator.hasNext()){
				Map map = (Map)iterator.next();
				String sogoHyoka = (String) map.get("SOGO_HYOKA");		//総合評価
				String jigyoKubun = new Integer(((Number) map.get("JIGYO_KUBUN")).intValue()).toString();	//事業区分
				String tensu =  new Integer(((Number) map.get("TENSU")).intValue()).toString();			//点数
				sogoHyokaMap.put(jigyoKubun + sogoHyoka, tensu);		//キー：事業区分+総合評価、値：点数 
			}	
			
			//---DB更新---
			try {
				String kekkaAbc = "";
				int intKekkaTen = 0;
				String kekkaTenSorted = "";
				boolean kekkaTenFlag = false;
				for(int i=0; i<shinsaKekkaInfoArray.length; i++){
					try{
						//総合評価（ABC）と総合評価（点数）は混在しない
						if(shinsaKekkaInfoArray[i].getKekkaAbc() != null){
							kekkaAbc = kekkaAbc + shinsaKekkaInfoArray[i].getKekkaAbc();
							String tensu = (String) sogoHyokaMap.get(shinsaKekkaInfoArray[i].getJigyoKubun()
														+ shinsaKekkaInfoArray[i].getKekkaAbc());
							if(tensu == null){
								throw new ApplicationException(
									"総合評価マスタに一致するデータが存在しません。検索キー：総合評価'"
									+ shinsaKekkaInfoArray[i].getKekkaAbc() 
									+ "',事業区分：'" +  shinsaKekkaInfoArray[i].getJigyoKubun() + "'",
									new ErrorInfo("errors.4002"));	
							}
							intKekkaTen = intKekkaTen
												+ Integer.parseInt((String) sogoHyokaMap.get(
																shinsaKekkaInfoArray[i].getJigyoKubun()
																	 + shinsaKekkaInfoArray[i].getKekkaAbc()));
							kekkaTenFlag = true;	//1つでも点数が設定されていた場合[true]						
						}else if(shinsaKekkaInfoArray[i].getKekkaTen() != null){
								//１次審査結果(点数)
								intKekkaTen = intKekkaTen
													+ Integer.parseInt((String) sogoHyokaMap.get(
																	shinsaKekkaInfoArray[i].getJigyoKubun()
																		 + shinsaKekkaInfoArray[i].getKekkaTen()));
								
								//１次審査結果(点数順)
								if(kekkaTenSorted.equals("")){
									kekkaTenSorted = kekkaTenSorted + shinsaKekkaInfoArray[i].getKekkaTen();
								}else{
									kekkaTenSorted = kekkaTenSorted + " " + shinsaKekkaInfoArray[i].getKekkaTen();									
								}
								
							kekkaTenFlag = true;	//1つでも点数が設定されていた場合[true]
						}
					}catch(NumberFormatException e){
						//数値として認識できない場合は処理を飛ばす
					}
				}
				
				//数値として認識できる点数が１つでもセットされていた場合は登録する
				String kekkaTen = null;
				if(kekkaTenFlag){
					kekkaTen = new Integer(intKekkaTen).toString();
				}
				
				//更新データをセットする
				existInfo.setKekka1Abc(kekkaAbc);								//１次審査結果(ABC)
				existInfo.setKekka1Ten(kekkaTen);								//１次審査結果(点数)
				existInfo.setKekka1TenSorted(kekkaTenSorted);					//１次審査結果(点数順)				
				existInfo.setJokyoId(StatusCode.STATUS_1st_SHINSA_KANRYO);		//申請状況
				dao.updateShinseiDataInfo(connection, existInfo, true);
				
				success = true;
				
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"申請情報更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} catch (ApplicationException e) {
				throw new ApplicationException(
					"申請情報更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			}
			
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"審査結果情報、申請情報DB更新中にエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {							
				DatabaseUtil.closeConnection(connection);
			}
		}		
	}
		
	/**
	 * 審査結果の取得.<br /><br />
	 * 
	 * 自クラスのメソッド<br />
	 * ・getShinsaKekkaReferenceInfo(userInfo, shinseiDataPk)<br />
	 * ・getShinsaKekka2nd(userInfo, shinseiDataPk)<br />
	 * により取得した値をMapに格納し、返却する。<br /><br />
	 * 
	 * 格納時のキーは、それぞれ<br />
	 * ・key_shinsakekka_1st<br />
	 * ・key_shinsakekka_2nd<br />
	 * である。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return Map
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Map getShinsaKekkaBoth(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException
	{
		Map map = new HashMap();
		
		//1次審査結果（参照用）
		map.put(
			KEY_SHINSAKEKKA_1ST,
			getShinsaKekkaReferenceInfo(userInfo, shinseiDataPk)
		);
		
		//2次審査結果
		map.put(
			KEY_SHINSAKEKKA_2ND,
			getShinsaKekka2nd(userInfo, shinseiDataPk)
		);
		
		return map;
	}
	
	
	
	/**
	 * 一次審査結果の取得.<br /><br />
	 * 
	 * 1.以下のSQL文を発行して、該当する申請データを取得する。<br />
	 * (バインド引数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SYSTEM_NO			--システム受付番号
	 * 	,A.UKETUKE_NO			--申請番号
	 * 	,A.JIGYO_ID			--事業ID
	 * 	,A.NENDO				--年度
	 * 	,A.KAISU				--回数
	 * 	,A.JIGYO_NAME			--事業名
	 * 	,A.SHINSEISHA_ID			--申請者ID
	 * 	,A.SAKUSEI_DATE			--申請書作成日
	 * 	,A.SHONIN_DATE			--所属機関承認日
	 * 	,A.JYURI_DATE			--学振受理日
	 * 	,A.NAME_KANJI_SEI			--申請者氏名（漢字等-姓）
	 * 	,A.NAME_KANJI_MEI			--申請者氏名（漢字等-名）
	 * 	,A.NAME_KANA_SEI			--申請者氏名（フリガナ-姓）
	 * 	,A.NAME_KANA_MEI			--申請者氏名（フリガナ-名）
	 * 	,A.NAME_RO_SEI			--申請者氏名（ローマ字-姓）
	 * 	,A.NAME_RO_MEI			--申請者氏名（ローマ字-名）
	 * 	,A.NENREI				--年齢
	 * 	,A.KENKYU_NO			--申請者研究者番号
	 * 	,A.SHOZOKU_CD			--所属機関コード
	 * 	,A.SHOZOKU_NAME			--所属機関名
	 * 	,A.SHOZOKU_NAME_RYAKU		--所属機関名（略称）
	 * 	,A.BUKYOKU_CD			--部局コード
	 * 	,A.BUKYOKU_NAME			--部局名
	 * 	,A.BUKYOKU_NAME_RYAKU		--部局名（略称）
	 * 	,A.SHOKUSHU_CD			--職名コード
	 * 	,A.SHOKUSHU_NAME_KANJI		--職名（和文）
	 * 	,A.SHOKUSHU_NAME_RYAKU		--職名（略称）
	 * 	,A.ZIP				--郵便番号
	 * 	,A.ADDRESS			--住所
	 * 	,A.TEL				--TEL
	 * 	,A.FAX				--FAX
	 * 	,A.EMAIL				--E-Mail
	 * 	,A.SENMON				--現在の専門
	 * 	,A.GAKUI				--学位
	 * 	,A.BUNTAN				--役割分担
	 * 	,A.KADAI_NAME_KANJI		--研究課題名(和文）
	 * 	,A.KADAI_NAME_EIGO			--研究課題名(英文）
	 * 	,A.JIGYO_KUBUN			--事業区分
	 * 	,A.SHINSA_KUBUN			--審査区分
	 * 	,A.SHINSA_KUBUN_MEISHO		--審査区分名称
	 * 	,A.BUNKATSU_NO			--分割番号
	 * 	,A.BUNKATSU_NO_MEISHO		--分割番号名称
	 * 	,A.KENKYU_TAISHO			--研究対象の類型
	 * 	,A.KEI_NAME_NO			--系等の区分番号
	 * 	,A.KEI_NAME			--系等の区分
	 * 	,A.KEI_NAME_RYAKU			--系等の区分略称
	 * 	,A.BUNKASAIMOKU_CD			--細目番号
	 * 	,A.BUNYA_NAME			--分野
	 * 	,A.BUNKA_NAME			--分科
	 * 	,A.SAIMOKU_NAME			--細目
	 * 	,A.BUNKASAIMOKU_CD2		--細目番号2
	 * 	,A.BUNYA_NAME2			--分野2
	 * 	,A.BUNKA_NAME2			--分科2
	 * 	,A.SAIMOKU_NAME2			--細目2
	 * 	,A.KANTEN_NO			--推薦の観点番号
	 * 	,A.KANTEN				--推薦の観点
	 * 	,A.KANTEN_RYAKU			--推薦の観点略称
	 * 	,A.KEIHI1				--1年目研究経費
	 * 	,A.BIHINHI1			--1年目設備備品費
	 * 	,A.SHOMOHINHI1			--1年目消耗品費
	 * 	,A.KOKUNAIRYOHI1			--1年目国内旅費
	 * 	,A.GAIKOKURYOHI1			--1年目外国旅費
	 * 	,A.RYOHI1				--1年目旅費
	 * 	,A.SHAKIN1			--1年目謝金等
	 * 	,A.SONOTA1			--1年目その他
	 * 	,A.KEIHI2				--2年目研究経費
	 * 	,A.BIHINHI2			--2年目設備備品費
	 * 	,A.SHOMOHINHI2			--2年目消耗品費
	 * 	,A.KOKUNAIRYOHI2			--2年目国内旅費
	 * 	,A.GAIKOKURYOHI2			--2年目外国旅費
	 * 	,A.RYOHI2				--2年目旅費
	 * 	,A.SHAKIN2			--2年目謝金等
	 * 	,A.SONOTA2			--2年目その他
	 * 	,A.KEIHI3				--3年目研究経費
	 * 	,A.BIHINHI3			--3年目設備備品費
	 * 	,A.SHOMOHINHI3			--3年目消耗品費
	 * 	,A.KOKUNAIRYOHI3			--3年目国内旅費
	 * 	,A.GAIKOKURYOHI3			--3年目外国旅費
	 * 	,A.RYOHI3				--3年目旅費
	 * 	,A.SHAKIN3			--3年目謝金等
	 * 	,A.SONOTA3			--3年目その他
	 * 	,A.KEIHI4				--4年目研究経費
	 * 	,A.BIHINHI4			--4年目設備備品費
	 * 	,A.SHOMOHINHI4			--4年目消耗品費
	 * 	,A.KOKUNAIRYOHI4			--4年目国内旅費
	 * 	,A.GAIKOKURYOHI4			--4年目外国旅費
	 * 	,A.RYOHI4				--4年目旅費
	 * 	,A.SHAKIN4			--4年目謝金等
	 * 	,A.SONOTA4			--4年目その他
	 * 	,A.KEIHI5				--5年目研究経費
	 * 	,A.BIHINHI5			--5年目設備備品費
	 * 	,A.SHOMOHINHI5			--5年目消耗品費
	 * 	,A.KOKUNAIRYOHI5			--5年目国内旅費
	 * 	,A.GAIKOKURYOHI5			--5年目外国旅費
	 * 	,A.RYOHI5				--5年目旅費
	 * 	,A.SHAKIN5			--5年目謝金等
	 * 	,A.SONOTA5			--5年目その他
	 * 	,A.KEIHI_TOTAL			--総計-研究経費
	 * 	,A.BIHINHI_TOTAL			--総計-設備備品費
	 * 	,A.SHOMOHINHI_TOTAL		--総計-消耗品費
	 * 	,A.KOKUNAIRYOHI_TOTAL		--総計-国内旅費
	 * 	,A.GAIKOKURYOHI_TOTAL		--総計-外国旅費
	 * 	,A.RYOHI_TOTAL			--総計-旅費
	 * 	,A.SHAKIN_TOTAL			--総計-謝金等
	 * 	,A.SONOTA_TOTAL			--総計-その他
	 * 	,A.SOSHIKI_KEITAI_NO		--研究組織の形態番号
	 * 	,A.SOSHIKI_KEITAI			--研究組織の形態
	 * 	,A.BUNTANKIN_FLG			--分担金の有無
	 * 	,A.KOYOHI				--研究支援者雇用経費
	 * 	,A.KENKYU_NINZU			--研究者数
	 * 	,A.TAKIKAN_NINZU			--他機関の分担者数
	 * 	,A.SHINSEI_KUBUN			--新規継続区分
	 * 	,A.KADAI_NO_KEIZOKU		--継続分の研究課題番号
	 * 	,A.SHINSEI_FLG_NO			--申請の有無番号
	 * 	,A.SHINSEI_FLG			--申請の有無
	 * 	,A.KADAI_NO_SAISYU			--最終年度課題番号
	 * 	,A.KAIJIKIBO_FLG_NO		--開示希望の有無番号
	 * 	,A.KAIJIKIBO_FLG			--開示希望の有無
	 * 	,A.KAIGAIBUNYA_CD			--海外分野コード
	 * 	,A.KAIGAIBUNYA_NAME		--海外分野名称
	 * 	,A.KAIGAIBUNYA_NAME_RYAKU		--海外分野略称
	 * 	,A.KANREN_SHIMEI1			--関連分野の研究者-氏名1
	 * 	,A.KANREN_KIKAN1			--関連分野の研究者-所属機関1
	 * 	,A.KANREN_BUKYOKU1			--関連分野の研究者-所属部局1
	 * 	,A.KANREN_SHOKU1			--関連分野の研究者-職名1
	 * 	,A.KANREN_SENMON1			--関連分野の研究者-専門分野1
	 * 	,A.KANREN_TEL1			--関連分野の研究者-勤務先電話番号1
	 * 	,A.KANREN_JITAKUTEL1		--関連分野の研究者-自宅電話番号1
	 * 	,A.KANREN_MAIL1			--関連分野の研究者-E-mail1
	 * 	,A.KANREN_SHIMEI2			--関連分野の研究者-氏名2
	 * 	,A.KANREN_KIKAN2			--関連分野の研究者-所属機関2
	 * 	,A.KANREN_BUKYOKU2			--関連分野の研究者-所属部局2
	 * 	,A.KANREN_SHOKU2			--関連分野の研究者-職名2
	 * 	,A.KANREN_SENMON2			--関連分野の研究者-専門分野2
	 * 	,A.KANREN_TEL2			--関連分野の研究者-勤務先電話番号2
	 * 	,A.KANREN_JITAKUTEL2		--関連分野の研究者-自宅電話番号2
	 * 	,A.KANREN_MAIL2			--関連分野の研究者-E-mail2
	 * 	,A.KANREN_SHIMEI3			--関連分野の研究者-氏名3
	 * 	,A.KANREN_KIKAN3			--関連分野の研究者-所属機関3
	 * 	,A.KANREN_BUKYOKU3			--関連分野の研究者-所属部局3
	 * 	,A.KANREN_SHOKU3			--関連分野の研究者-職名3
	 * 	,A.KANREN_SENMON3			--関連分野の研究者-専門分野3
	 * 	,A.KANREN_TEL3			--関連分野の研究者-勤務先電話番号3
	 * 	,A.KANREN_JITAKUTEL3		--関連分野の研究者-自宅電話番号3
	 * 	,A.KANREN_MAIL3			--関連分野の研究者-E-mail3
	 * 	,A.XML_PATH			--XMLの格納パス
	 * 	,A.PDF_PATH			--PDFの格納パス
	 * 	,A.JURI_KEKKA			--受理結果
	 * 	,A.JURI_BIKO			--受理結果備考
	 * 	,A.SUISENSHO_PATH			--推薦書の格納パス
	 * 	,A.KEKKA1_ABC			--１次審査結果(ABC)
	 * 	,A.KEKKA1_TEN			--１次審査結果(点数)
	 * 	,A.KEKKA1_TEN_SORTED		--１次審査結果(点数順)
	 * 	,A.SHINSA1_BIKO			--１次審査備考
	 * 	,A.KEKKA2				--２次審査結果
	 * 	,A.SOU_KEHI			--総経費（学振入力）
	 * 	,A.SHONEN_KEHI			--初年度経費（学振入力）
	 * 	,A.SHINSA2_BIKO			--業務担当者記入欄
	 * 	,A.JOKYO_ID			--申請状況ID
	 * 	,A.SAISHINSEI_FLG			--再申請フラグ
	 * 	,A.DEL_FLG			--削除フラグ
	 * FROM
	 * 	SHINSEIDATAKANRI A
	 * WHERE
	 * 	SYSTEM_NO = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム番号</td><td>第二引数shinseiDataPkの変数SystemNoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値を、shinseiDataInfoに格納する。<br /><br />
	 * 
	 * 2.以下のSQL文を発行して、該当する審査結果データを取得する。<br />
	 * (バインド引数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SYSTEM_NO		--システム受付番号
	 * 	,A.UKETUKE_NO		--申請番号
	 * 	,A.SHINSAIN_NO		--審査員番号
	 * 	,A.JIGYO_KUBUN		--事業区分
	 * 	,A.SEQ_NO			--シーケンス番号
	 * 	,A.SHINSA_KUBUN		--審査区分
	 * 	,A.SHINSAIN_NAME_KANJI_SEI	--審査員名（漢字－姓）
	 * 	,A.SHINSAIN_NAME_KANJI_MEI	--審査員名（漢字－名）
	 * 	,A.NAME_KANA_SEI		--審査員名（フリガナ－姓）
	 * 	,A.NAME_KANA_MEI		--審査員名（フリガナ－名）
	 * 	,A.SHOZOKU_NAME		--審査員所属機関名
	 * 	,A.BUKYOKU_NAME		--審査員部局名
	 * 	,A.SHOKUSHU_NAME		--審査員職名
	 * 	,A.JIGYO_ID			--事業ID
	 * 	,A.JIGYO_NAME		--事業名
	 * 	,A.BUNKASAIMOKU_CD		--細目番号
	 * 	,A.EDA_NO			--枝番
	 * 	,A.CHECKDIGIT		--チェックデジット
	 * 	,A.KEKKA_ABC		--総合評価（ABC）
	 * 	,A.KEKKA_TEN		--総合評価（点数）
	 * 	,NVL(
	 * 		REPLACE(
	 * 			A.KEKKA_TEN,
	 * 			'-',
	 * 			'0'
	 * 		),'-1'
	 * 	) SORT_KEKKA_TEN
	 * 		--ソート用。審査結果（点数）の値NULL→'-1'、'-'→'0'に置換）
	 * 	,A.COMMENT1		--コメント1
	 * 	,A.COMMENT2		--コメント2
	 * 	,A.COMMENT3		--コメント3
	 * 	,A.COMMENT4		--コメント4
	 * 	,A.COMMENT5		--コメント5
	 * 	,A.COMMENT6		--コメント6
	 * 	,A.KENKYUNAIYO		--研究内容
	 * 	,A.KENKYUKEIKAKU		--研究計画
	 * 	,A.TEKISETSU_KAIGAI	--適切性-海外
	 * 	,A.TEKISETSU_KENKYU1	--適切性-研究（1）
	 * 	,A.TEKISETSU		--適切性
	 * 	,A.DATO			--妥当性
	 * 	,A.SHINSEISHA		--研究代表者
	 * 	,A.KENKYUBUNTANSHA		--研究分担者
	 * 	,A.HITOGENOMU		--ヒトゲノム
	 * 	,A.TOKUTEI		--特定胚
	 * 	,A.HITOES			--ヒトES細胞
	 * 	,A.KUMIKAE		--遺伝子組換え実験
	 * 	,A.CHIRYO			--遺伝子治療臨床研究
	 * 	,A.EKIGAKU		--疫学研究
	 * 	,A.COMMENTS		--コメント
	 * 	,A.TENPU_PATH		--添付ファイル格納パス
	 * 	,A.SHINSA_JOKYO		--審査状況
	 * 	,A.BIKO			--備考
	 * FROM
	 * 	SHINSAKEKKA A
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * ORDER BY
	 * 	KEKKA_ABC ASC		--総合評価（ABC）の昇順
	 * 	,SORT_KEKKA_TEN DESC	--総合評価（点数）の降順
	 * 	,SHINSAIN_NO ASC		--審査員番号の昇順
	 * 	,JIGYO_KUBUN ASC		--事業区分の昇順</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム番号</td><td>第二引数shinseiDataPkの変数SystemNoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値を、shinsaKekkaInfoの配列に格納する。<br /><br />
	 * 
	 * 
	 * 3.以下のSQL文を発行して、プルダウン用のラベル名を持つListを取得する。取得するのは、<br />
	 * ・総合評価（ABC）　(KEKKA_ABC)<br />
	 * ・総合評価（点数）　(KEKKA_TEN)<br />
	 * の2種類なので、SQL文は2回発行する。()の値は、それぞれのラベル区分の値。<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.ATAI
	 * 	, A.NAME
	 * 	, A.RYAKU
	 * 	, A.SORT
	 * 	, A.BIKO
	 * FROM
	 * 	MASTER_LABEL A
	 * WHERE
	 * 	A.LABEL_KUBUN = ?
	 * 	AND A.SORT != 0
	 * ORDER BY
	 * 	SORT</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>ラベル区分</td><td>前述したラベル区分の値を使用する。</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 4.2で取得したshinsaKekkaInfoの配列の各々に、以下の値を加えていく。
	 * この処理は、配列の数だけ繰り返す。<br /><br />
	 * 
	 * (1)shinsaKekkaInfoの添付ファイルパスの値から添付ファイル名を取り出して、shinsaKekkaInfoのTenpuNameに加える。<br /><br />
	 * 
	 * (2)3で取得した表示ラベル名のListから、総合評価（ABC）の表示ラベル名を
	 * 自クラスのメソッドgetLabelName()を使用して取り出し、shinsaKekkaInfoのKekkaAbcLabelに加える。<br /><br />
	 * 
	 * (3)3で取得した表示ラベル名のListから、総合評価（点数）の表示ラベル名を
	 * 自クラスのメソッドgetLabelName()を使用して取り出し、shinsaKekkaInfoのKekkaTenLabelに加える。<br /><br /><br />
	 * 
	 * 
	 * 
	 * 5.ShinsaKekkaReferenceInfoのオブジェクトを生成し、以下の値を格納して返却する。<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>値(和文)</td><td>値</td><td>値をもつオブジェクト</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>SystemNo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請番号</td><td>UketukeNo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>年度</td><td>Nendo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>回数</td><td>Kaisu</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>JigyoId</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業名</td><td>JigyoName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究課題名</td><td>KadaiNameKanji</td><td>shinseiDataInfo内のKadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者名（姓）</td><td>NameKanjiSei</td><td>shinseiDataInfo内のKadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者名（名）</td><td>NameKanjiMei</td><td>shinseiDataInfo内のKadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名</td><td>ShozokuName</td><td>shinseiDataInfo内のKadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名</td><td>BukyokuName</td><td>shinseiDataInfo内のKadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名</td><td>ShokushuNameKanji</td><td>shinseiDataInfo内のKadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>KenkyuNo</td><td>shinseiDataInfo内のKadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1次審査結果情報</td><td>ShinsaKekkaInfo</td><td>ShinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当者用備考</td><td>Shinsa1Biko</td><td>shinseiDataInfo</td></tr>
	 * </table><br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return ShinsaKekkaReferenceInfo
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public ShinsaKekkaReferenceInfo getShinsaKekkaReferenceInfo(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException
	{
		Connection   connection  = null;
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			
			//申請データ管理DAO
			ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo);
			//該当申請データを取得する
			ShinseiDataInfo shinseiDataInfo = null;
			try{
				shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"申請書管理データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//審査結果DAO
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);
			//該当審査結果データを取得する
			ShinsaKekkaInfo[] shinsaKekkaInfo = null;
			try{
				shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfo(connection, shinseiDataPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			

			//ラベル名
			List kekkaAbcList = null;		//総合評価（ABC）
			List kekkaTenList = null;		//総合評価（点数）
			List kekkaTenHogaList = null;	//総合評価（萌芽）
			try{
				String[] labelKubun = new String[]{ILabelKubun.KEKKA_ABC,
													ILabelKubun.KEKKA_TEN,
													ILabelKubun.KEKKA_TEN_HOGA};
				List bothList = new LabelValueMaintenance().getLabelList(labelKubun);	//4つのラベルリスト
				kekkaAbcList = (List)bothList.get(0);		
				kekkaTenList = (List)bothList.get(1);
				kekkaTenHogaList = (List)bothList.get(2);		
			}catch(ApplicationException e){
				throw new ApplicationException(
					"ラベルマスタ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}

			for(int i = 0; i < shinsaKekkaInfo.length; i++){
				//添付ファイル名をセット
				String tenpuPath = shinsaKekkaInfo[i].getTenpuPath();
				if(tenpuPath != null && tenpuPath.length() != 0){
					shinsaKekkaInfo[i].setTenpuName(new File(tenpuPath).getName());
				}
				
				//総合評価（ABC）の表示ラベル名をセット
				String kekkaAbcLabel = getLabelName(kekkaAbcList, shinsaKekkaInfo[i].getKekkaAbc());
				shinsaKekkaInfo[i].setKekkaAbcLabel(kekkaAbcLabel);

				//総合評価（点数）の表示ラベル名をセット
				String kekkaTenLabel = getLabelName(kekkaTenList, shinsaKekkaInfo[i].getKekkaTen());
				shinsaKekkaInfo[i].setKekkaTenLabel(kekkaTenLabel);

				//総合評価（萌芽）の表示ラベル名をセット
				String kekkaTenHogaLabel = getLabelName(kekkaTenHogaList, shinsaKekkaInfo[i].getKekkaTen());
				shinsaKekkaInfo[i].setKekkaTenHogaLabel(kekkaTenHogaLabel);


			}				
		
			//1次審査結果（参照用）の生成
			ShinsaKekkaReferenceInfo refInfo = new ShinsaKekkaReferenceInfo();
			refInfo.setSystemNo(shinseiDataInfo.getSystemNo());										//システム受付番号
			refInfo.setUketukeNo(shinseiDataInfo.getUketukeNo());									//申請番号
			refInfo.setNendo(shinseiDataInfo.getNendo());											//年度
			refInfo.setKaisu(shinseiDataInfo.getKaisu());											//回数
			refInfo.setJigyoId(shinseiDataInfo.getJigyoId());										//事業ID
			refInfo.setJigyoName(shinseiDataInfo.getJigyoName());									//事業名
			refInfo.setJigyoCd(shinseiDataInfo.getJigyoCd());										//事業ID
			refInfo.setKadaiNameKanji(shinseiDataInfo.getKadaiInfo().getKadaiNameKanji());			//研究課題名
			refInfo.setNameKanjiSei(shinseiDataInfo.getDaihyouInfo().getNameKanjiSei());			//申請者名（姓）
			refInfo.setNameKanjiMei(shinseiDataInfo.getDaihyouInfo().getNameKanjiMei());			//申請者名（名）
			refInfo.setShozokuName(shinseiDataInfo.getDaihyouInfo().getShozokuName());				//所属機関名
			refInfo.setBukyokuName(shinseiDataInfo.getDaihyouInfo().getBukyokuName());				//部局名
			refInfo.setShokushuNameKanji(shinseiDataInfo.getDaihyouInfo().getShokushuNameKanji());	//職名
			refInfo.setKenkyuNo(shinseiDataInfo.getDaihyouInfo().getKenkyuNo());					//研究者番号			
			refInfo.setShinsaKekkaInfo(shinsaKekkaInfo);											//1次審査結果情報
			refInfo.setShinsa1Biko(shinseiDataInfo.getShinsa1Biko());								//業務担当者用備考

			return refInfo;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
	}
	
	
	
	/**
	 * 二次審査結果(参照用)の取得.<br /><br />
	 * 
	 * 以下のSQL文を発行して、該当する申請データを取得する。<br />
	 * (バインド引数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SYSTEM_NO			--システム受付番号
	 * 	,A.UKETUKE_NO			--申請番号
	 * 	,A.JIGYO_ID			--事業ID
	 * 	,A.NENDO				--年度
	 * 	,A.KAISU				--回数
	 * 	,A.JIGYO_NAME			--事業名
	 * 	,A.SHINSEISHA_ID			--申請者ID
	 * 	,A.SAKUSEI_DATE			--申請書作成日
	 * 	,A.SHONIN_DATE			--所属機関承認日
	 * 	,A.JYURI_DATE			--学振受理日
	 * 	,A.NAME_KANJI_SEI			--申請者氏名（漢字等-姓）
	 * 	,A.NAME_KANJI_MEI			--申請者氏名（漢字等-名）
	 * 	,A.NAME_KANA_SEI			--申請者氏名（フリガナ-姓）
	 * 	,A.NAME_KANA_MEI			--申請者氏名（フリガナ-名）
	 * 	,A.NAME_RO_SEI			--申請者氏名（ローマ字-姓）
	 * 	,A.NAME_RO_MEI			--申請者氏名（ローマ字-名）
	 * 	,A.NENREI				--年齢
	 * 	,A.KENKYU_NO			--申請者研究者番号
	 * 	,A.SHOZOKU_CD			--所属機関コード
	 * 	,A.SHOZOKU_NAME			--所属機関名
	 * 	,A.SHOZOKU_NAME_RYAKU		--所属機関名（略称）
	 * 	,A.BUKYOKU_CD			--部局コード
	 * 	,A.BUKYOKU_NAME			--部局名
	 * 	,A.BUKYOKU_NAME_RYAKU		--部局名（略称）
	 * 	,A.SHOKUSHU_CD			--職名コード
	 * 	,A.SHOKUSHU_NAME_KANJI		--職名（和文）
	 * 	,A.SHOKUSHU_NAME_RYAKU		--職名（略称）
	 * 	,A.ZIP				--郵便番号
	 * 	,A.ADDRESS			--住所
	 * 	,A.TEL				--TEL
	 * 	,A.FAX				--FAX
	 * 	,A.EMAIL				--E-Mail
	 * 	,A.SENMON				--現在の専門
	 * 	,A.GAKUI				--学位
	 * 	,A.BUNTAN				--役割分担
	 * 	,A.KADAI_NAME_KANJI		--研究課題名(和文）
	 * 	,A.KADAI_NAME_EIGO			--研究課題名(英文）
	 * 	,A.JIGYO_KUBUN			--事業区分
	 * 	,A.SHINSA_KUBUN			--審査区分
	 * 	,A.SHINSA_KUBUN_MEISHO		--審査区分名称
	 * 	,A.BUNKATSU_NO			--分割番号
	 * 	,A.BUNKATSU_NO_MEISHO		--分割番号名称
	 * 	,A.KENKYU_TAISHO			--研究対象の類型
	 * 	,A.KEI_NAME_NO			--系等の区分番号
	 * 	,A.KEI_NAME			--系等の区分
	 * 	,A.KEI_NAME_RYAKU			--系等の区分略称
	 * 	,A.BUNKASAIMOKU_CD			--細目番号
	 * 	,A.BUNYA_NAME			--分野
	 * 	,A.BUNKA_NAME			--分科
	 * 	,A.SAIMOKU_NAME			--細目
	 * 	,A.BUNKASAIMOKU_CD2		--細目番号2
	 * 	,A.BUNYA_NAME2			--分野2
	 * 	,A.BUNKA_NAME2			--分科2
	 * 	,A.SAIMOKU_NAME2			--細目2
	 * 	,A.KANTEN_NO			--推薦の観点番号
	 * 	,A.KANTEN				--推薦の観点
	 * 	,A.KANTEN_RYAKU			--推薦の観点略称
	 * 	,A.KEIHI1				--1年目研究経費
	 * 	,A.BIHINHI1			--1年目設備備品費
	 * 	,A.SHOMOHINHI1			--1年目消耗品費
	 * 	,A.KOKUNAIRYOHI1			--1年目国内旅費
	 * 	,A.GAIKOKURYOHI1			--1年目外国旅費
	 * 	,A.RYOHI1				--1年目旅費
	 * 	,A.SHAKIN1			--1年目謝金等
	 * 	,A.SONOTA1			--1年目その他
	 * 	,A.KEIHI2				--2年目研究経費
	 * 	,A.BIHINHI2			--2年目設備備品費
	 * 	,A.SHOMOHINHI2			--2年目消耗品費
	 * 	,A.KOKUNAIRYOHI2			--2年目国内旅費
	 * 	,A.GAIKOKURYOHI2			--2年目外国旅費
	 * 	,A.RYOHI2				--2年目旅費
	 * 	,A.SHAKIN2			--2年目謝金等
	 * 	,A.SONOTA2			--2年目その他
	 * 	,A.KEIHI3				--3年目研究経費
	 * 	,A.BIHINHI3			--3年目設備備品費
	 * 	,A.SHOMOHINHI3			--3年目消耗品費
	 * 	,A.KOKUNAIRYOHI3			--3年目国内旅費
	 * 	,A.GAIKOKURYOHI3			--3年目外国旅費
	 * 	,A.RYOHI3				--3年目旅費
	 * 	,A.SHAKIN3			--3年目謝金等
	 * 	,A.SONOTA3			--3年目その他
	 * 	,A.KEIHI4				--4年目研究経費
	 * 	,A.BIHINHI4			--4年目設備備品費
	 * 	,A.SHOMOHINHI4			--4年目消耗品費
	 * 	,A.KOKUNAIRYOHI4			--4年目国内旅費
	 * 	,A.GAIKOKURYOHI4			--4年目外国旅費
	 * 	,A.RYOHI4				--4年目旅費
	 * 	,A.SHAKIN4			--4年目謝金等
	 * 	,A.SONOTA4			--4年目その他
	 * 	,A.KEIHI5				--5年目研究経費
	 * 	,A.BIHINHI5			--5年目設備備品費
	 * 	,A.SHOMOHINHI5			--5年目消耗品費
	 * 	,A.KOKUNAIRYOHI5			--5年目国内旅費
	 * 	,A.GAIKOKURYOHI5			--5年目外国旅費
	 * 	,A.RYOHI5				--5年目旅費
	 * 	,A.SHAKIN5			--5年目謝金等
	 * 	,A.SONOTA5			--5年目その他
	 * 	,A.KEIHI_TOTAL			--総計-研究経費
	 * 	,A.BIHINHI_TOTAL			--総計-設備備品費
	 * 	,A.SHOMOHINHI_TOTAL		--総計-消耗品費
	 * 	,A.KOKUNAIRYOHI_TOTAL		--総計-国内旅費
	 * 	,A.GAIKOKURYOHI_TOTAL		--総計-外国旅費
	 * 	,A.RYOHI_TOTAL			--総計-旅費
	 * 	,A.SHAKIN_TOTAL			--総計-謝金等
	 * 	,A.SONOTA_TOTAL			--総計-その他
	 * 	,A.SOSHIKI_KEITAI_NO		--研究組織の形態番号
	 * 	,A.SOSHIKI_KEITAI			--研究組織の形態
	 * 	,A.BUNTANKIN_FLG			--分担金の有無
	 * 	,A.KOYOHI				--研究支援者雇用経費
	 * 	,A.KENKYU_NINZU			--研究者数
	 * 	,A.TAKIKAN_NINZU			--他機関の分担者数
	 * 	,A.SHINSEI_KUBUN			--新規継続区分
	 * 	,A.KADAI_NO_KEIZOKU		--継続分の研究課題番号
	 * 	,A.SHINSEI_FLG_NO			--申請の有無番号
	 * 	,A.SHINSEI_FLG			--申請の有無
	 * 	,A.KADAI_NO_SAISYU			--最終年度課題番号
	 * 	,A.KAIJIKIBO_FLG_NO		--開示希望の有無番号
	 * 	,A.KAIJIKIBO_FLG			--開示希望の有無
	 * 	,A.KAIGAIBUNYA_CD			--海外分野コード
	 * 	,A.KAIGAIBUNYA_NAME		--海外分野名称
	 * 	,A.KAIGAIBUNYA_NAME_RYAKU		--海外分野略称
	 * 	,A.KANREN_SHIMEI1			--関連分野の研究者-氏名1
	 * 	,A.KANREN_KIKAN1			--関連分野の研究者-所属機関1
	 * 	,A.KANREN_BUKYOKU1			--関連分野の研究者-所属部局1
	 * 	,A.KANREN_SHOKU1			--関連分野の研究者-職名1
	 * 	,A.KANREN_SENMON1			--関連分野の研究者-専門分野1
	 * 	,A.KANREN_TEL1			--関連分野の研究者-勤務先電話番号1
	 * 	,A.KANREN_JITAKUTEL1		--関連分野の研究者-自宅電話番号1
	 * 	,A.KANREN_MAIL1			--関連分野の研究者-E-mail1
	 * 	,A.KANREN_SHIMEI2			--関連分野の研究者-氏名2
	 * 	,A.KANREN_KIKAN2			--関連分野の研究者-所属機関2
	 * 	,A.KANREN_BUKYOKU2			--関連分野の研究者-所属部局2
	 * 	,A.KANREN_SHOKU2			--関連分野の研究者-職名2
	 * 	,A.KANREN_SENMON2			--関連分野の研究者-専門分野2
	 * 	,A.KANREN_TEL2			--関連分野の研究者-勤務先電話番号2
	 * 	,A.KANREN_JITAKUTEL2		--関連分野の研究者-自宅電話番号2
	 * 	,A.KANREN_MAIL2			--関連分野の研究者-E-mail2
	 * 	,A.KANREN_SHIMEI3			--関連分野の研究者-氏名3
	 * 	,A.KANREN_KIKAN3			--関連分野の研究者-所属機関3
	 * 	,A.KANREN_BUKYOKU3			--関連分野の研究者-所属部局3
	 * 	,A.KANREN_SHOKU3			--関連分野の研究者-職名3
	 * 	,A.KANREN_SENMON3			--関連分野の研究者-専門分野3
	 * 	,A.KANREN_TEL3			--関連分野の研究者-勤務先電話番号3
	 * 	,A.KANREN_JITAKUTEL3		--関連分野の研究者-自宅電話番号3
	 * 	,A.KANREN_MAIL3			--関連分野の研究者-E-mail3
	 * 	,A.XML_PATH			--XMLの格納パス
	 * 	,A.PDF_PATH			--PDFの格納パス
	 * 	,A.JURI_KEKKA			--受理結果
	 * 	,A.JURI_BIKO			--受理結果備考
	 * 	,A.SUISENSHO_PATH			--推薦書の格納パス
	 * 	,A.KEKKA1_ABC			--１次審査結果(ABC)
	 * 	,A.KEKKA1_TEN			--１次審査結果(点数)
	 * 	,A.KEKKA1_TEN_SORTED		--１次審査結果(点数順)
	 * 	,A.SHINSA1_BIKO			--１次審査備考
	 * 	,A.KEKKA2				--２次審査結果
	 * 	,A.SOU_KEHI			--総経費（学振入力）
	 * 	,A.SHONEN_KEHI			--初年度経費（学振入力）
	 * 	,A.SHINSA2_BIKO			--業務担当者記入欄
	 * 	,A.JOKYO_ID			--申請状況ID
	 * 	,A.SAISHINSEI_FLG			--再申請フラグ
	 * 	,A.DEL_FLG			--削除フラグ
	 * FROM
	 * 	SHINSEIDATAKANRI A
	 * WHERE
	 * 	SYSTEM_NO = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム番号</td><td>第二引数shinseiDataPkの変数SystemNoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値をshinseiDataInfoに格納し、ここからShinsaKekka2ndInfoへ値を与える。<br />
	 * 与える値は、以下の表を参照。<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>与える値の名前(和文)</td><td>値の名前</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>SystemNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2次審査結果</td><td>Kekka2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>総経費</td><td>SouKehi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>初年度経費</td><td>ShonenKehi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当者記入欄</td><td>Shinsa2Biko</td></tr>
	 * </table><br />
	 * 
	 * 値を受け取ったShinsaKekka2ndInfoを返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return ShinsaKekka2ndInfo
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public ShinsaKekka2ndInfo getShinsaKekka2nd(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException
	{
		Connection   connection  = null;
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			
			//申請データ管理DAO
			ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo);
			//該当申請データを取得する
			ShinseiDataInfo shinseiDataInfo = null;
			try{
				shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"申請書管理データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//2次審査結果（参照用）の生成
			ShinsaKekka2ndInfo kekka2ndInfo = new ShinsaKekka2ndInfo();
			kekka2ndInfo.setSystemNo(shinseiDataInfo.getSystemNo());		//システム受付番号
			kekka2ndInfo.setKekka2(shinseiDataInfo.getKekka2());			//2次審査結果
			kekka2ndInfo.setSouKehi(shinseiDataInfo.getSouKehi());			//総経費
			kekka2ndInfo.setShonenKehi(shinseiDataInfo.getShonenKehi());	//初年度経費
			kekka2ndInfo.setShinsa2Biko(shinseiDataInfo.getShinsa2Biko());	//業務担当者記入欄
			
			return kekka2ndInfo;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
	}
	
	/**
	 * 催促のメール送信.<br /><br />
	 * 
	 * 1.審査員情報・事業情報の取得<br /><br />
	 * 
	 * 以下のSQL文を発行して、<br />
	 * ・未審査の申請書を持つ審査員情報（審査員No、メールアドレス）<br />
	 * ・3日後が審査期限である事業情報（事業ID、年度、事業名）<br />
	 * をもつListを取得する。<br />
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT DISTINCT
	 * 	S.SHINSAIN_NO,
	 * 	J.JIGYO_ID,
	 * 	J.NENDO,
	 * 	J.JIGYO_NAME,
	 * 	M.SOFU_ZIPEMAIL
	 * FROM(
	 * 		SELECT
	 * 			SHINSAIN_NO,
	 * 			JIGYO_ID
	 * 		FROM
	 * 			SHINSAKEKKA
	 * 		WHERE
	 * 			SHINSA_JOKYO = '0' OR SHINSA_JOKYO IS NULL
	 * 	) S,	//審査未完了のもの
	 * 	(
	 * 		SELECT
	 * 			JIGYO_ID,
	 * 			NENDO,
	 * 			JIGYO_NAME
	 * 		FROM
	 * 			JIGYOKANRI
	 * 		WHERE
	 * 			_CHAR(SHINSAKIGEN,'YYYY/MM/DD') = ?
	 * 	) J,
	 * 	MASTER_SHINSAIN M
	 * WHERE
	 * 	S.JIGYO_ID=J.JIGYO_ID
	 * 	AND S.SHINSAIN_NO=M.SHINSAIN_NO
	 * ORDER BY
	 * 	S.SHINSAIN_NO,
	 * 	J.JIGYO_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査期限</td><td>本日から3日後の日付を"yyyy/MM/dd"で使用する。</td></tr>
	 * </table><br />
	 * 
	 * 該当する審査員が存在しなかった場合は、ここで処理が終了となる。<br /><br /><br />
	 * 
	 * 2.データ構造の変換<br /><br />
	 * 
	 * 該当する審査員が存在する場合は、
	 * 取得したList(重複データを含む単一リスト)
	 * を審査員番号ごとのマップへデータ構造を変換する。<br /><br />
	 * 
	 * (1)取得したList内のMapから、<br /><br />
	 * 
	 * 　・審査員番号<br />
	 * 　・メールアドレス<br />
	 * 　・年度<br />
	 * 　・事業名<br /><br />
	 * 
	 * 　をString型で取り出し、事業名を<br /><br />
	 * 
	 * 　　"<b>平成{1}年度{2}</b>"<br />
	 * 　　{1}…年度の値<br />
	 * 　　{2}…事業名<br /><br />
	 * 
	 * 　の形にする。<br /><br />
	 * 
	 * (2)メールアドレスと変換した事業名をList"dataList"にセットし、<br />
	 * 　このListを、審査員番号をキーにして、Map"saisokuMap"に格納する。<br /><br />
	 * 
	 * (3)(1)、(2)の処理を、List内のMapの数だけ繰り返すが、<br />
	 * 　その過程で同じ審査員の情報があった場合には、<br />
	 * 　同じdataListに、変換した事業名だけをセットする。<br /><br /><br />
	 * 
	 * 
	 * 3.メールの送信<br /><br />
	 * ここでは、dataListの数だけ、処理を繰り返す。<br /><br />
	 * 
	 * (1)メールの本文の読み込み<br /><br />
	 * 　
	 * 　本文のあるファイルを読み込む。読み込むのは、<br />
	 * 　　"<b>D:/shinsei-kaken/settings/mail/shinseisho_shinsa_saisoku.txt</b>"<br />
	 *　 のファイル。<br />
	 * 　その後、ファイルの本文をString型にして受け取る。<br />
	 * 　読み込みできなかった場合には例外をthrowする。<br /><br />
	 * 
	 * 
	 * (2)動的項目の作成<br /><br />
	 * 
	 * 　2で作成したsaisokuMapからdataListを取り出し、メール本文の動的項目を作成する。<br />
	 * 　なお、dataList内のメールアドレスが設定されていない場合には処理を飛ばし、次のdataListの処理を行う。<br /><br />
	 * 
	 * 　(2-1)変換された事業名から、<br /><br />
	 * 
	 * 　　　　"<b>　【研究種目名】</b>{1}　<b>\n</b>"<br />
	 * 　　　　　　{1}…変換された事業名<br /><br />
	 * 
	 * 　　というStringを作成する。dataListの事業の数だけ作成し、連結していく。<br /><br />
	 * 
	 * 　(2-2)審査期限日を、"M月d日"の形でStringにする。<br /><br />
	 * 
	 * 　　この二つのStringを配列で持ち、取得した本文とともに<br /><br />
	 * 
	 * 　　　　<b>MessageFormat.format(</b>本文<b>,</b>Stringの配列<b>)</b><br /><br />
	 * 
	 * 　　に与え、本文を完成させる。<br /><br /><br />
	 * 
	 * 
	 * (3)送信<br /><br />
	 * 
	 * 　メールアドレス、作成した本文に加え、件名、差出人を与えて、メールを送信する。<br /><br />
	 * 
	 * 　・件名　…SUBJECT_SHINSEISHO_SHINSA_SAISOKUの値を使用。<br />
	 * 　・差出人…ApplicationSettings.propertiesに設定<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @throws ApplicationException
	 */
	public void sendMailShinsaSaisoku(UserInfo userInfo)
		throws ApplicationException 
	{
		List       shinsainList = null;
		Connection connection   = null;
		
		//審査期限日時の設定
		DateUtil du = new DateUtil();
		du.addDate(DATE_BY_SHINSA_KIGEN);	//指定日付を加算する
		Date date = du.getCal().getTime();
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();		
			//審査結果DAO
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);
			try{
				shinsainList = shinsaDao.selectShinsainWithNonExamined(connection, date);
			}catch(NoDataFoundException e){
				//何も処理しない
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}					
		} finally {
			DatabaseUtil.closeConnection(connection);
		}		
		connection = null;		
		
		//該当する審査員が存在しなかった場合
		if(shinsainList == null || shinsainList.size() == 0){
			String strDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
			String msg = "審査期限["+strDate+"]の事業で、未審査の申請書を持つ審査員は存在しません。";
			log.info(msg);
			return;
		}
		
		
		//-----データ構造を変換（重複データを含む単一リストから審査員番号ごとのマップへ変換する）
		//全体Map
		Map saisokuMap = new HashMap();
		for(int i=0; i<shinsainList.size(); i++){
			
			//1レコード
			Map recordMap = (Map)shinsainList.get(i);
			String shinsain_no   = (String)recordMap.get("SHINSAIN_NO");
			String sofu_zipemail = (String)recordMap.get("SOFU_ZIPEMAIL");			
			String nendo         = (String)recordMap.get("NENDO");
			String jigyo_name    = (String)recordMap.get("JIGYO_NAME");
			
				//事業名を「年度＋事業名」へ変換
				jigyo_name = new StringBuffer("平成")
							 .append(nendo)
							 .append("年度 ")
							 .append(jigyo_name)
							 .toString();
			
			//全体Mapに当該審査員データが存在していた場合
			if(saisokuMap.containsKey(shinsain_no)){
				List dataList = (List)saisokuMap.get(shinsain_no);
				dataList.add(jigyo_name);				//次レコードに年度+事業名を追加
			//初の審査員データの場合	
			}else{
				List dataList = new ArrayList();
				dataList.add(sofu_zipemail);			//1レコード目にメールアドレス
				dataList.add(jigyo_name);				//2レコード目に年度+事業名
				saisokuMap.put(shinsain_no, dataList);
			}
			
		}				
		
		
		
		//---------------
		// メール送信
		//---------------
		//-----メール本文ファイルの読み込み
		String content = null;
		try{
			File contentFile = new File(CONTENT_SHINSEISHO_SHINSA_SAISOKU);
			FileResource fileRes = FileUtil.readFile(contentFile);
			content = new String(fileRes.getBinary());
		}catch(FileNotFoundException e){
			log.warn("メール本文ファイルが見つかりませんでした。", e);
			return;
		}catch(IOException e){
			log.warn("メール本文ファイル読み込み時にエラーが発生しました。",e);
			return;
		}
		
		//-----メール送信（１人ずつ送信）
		String kigenDate = new SimpleDateFormat("M月d日").format(date);
		for(Iterator iter = saisokuMap.keySet().iterator(); iter.hasNext();){
			
			//審査員ごとのデータリストを取得する
			String shinsainNo = (String)iter.next();
			List   dataList   = (List)saisokuMap.get(shinsainNo);
			
			//メールアドレスが設定されていない場合は処理を飛ばす
			String to = (String)dataList.get(0);
			if(to == null || to.length() == 0){
				continue;
			}
			
			//-----メール本文ファイルの動的項目変更
			StringBuffer jigyoNameList = new StringBuffer("\n");
			for(int i=1; i<dataList.size(); i++){
				jigyoNameList.append("  【研究種目名】")
							 .append(dataList.get(i))
							 .append("\n")
							 ;
			}
			String[] param = new String[]{
								jigyoNameList.toString(),	//事業名リスト
								kigenDate					//審査期限日付
							 };
			String   body  = MessageFormat.format(content, param);			
			
			
			try{
				SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
				mailer.sendMail(FROM_ADDRESS,						//差出人
								to,									//to
								null,								//cc
								null,								//bcc
								SUBJECT_SHINSEISHO_SHINSA_SAISOKU,	//件名
								body);							    //本文
			}catch(Exception e){
				log.warn("メール送信に失敗しました。",e);
				continue;
			}
		}		
		
	}


	/**
	 * 審査状況の更新.<br /><br />
	 * 
	 * 審査状況を更新する自クラスのメソッド<br />
	 * updateJigyoShinsaComplete(userInfo, jigyoId, ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE)<br />
	 * を呼び出し、審査状況を『審査完了』に更新する。<br />
	 * メソッドに与える第三引数(審査状況を表すString)は"1"(審査完了を表す)<br /><br />
	 * 
	 * 正常に更新が行われたら、trueを返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param jigyoId String
	 * @return 更新結果のboolean
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean updateJigyoShinsaComplete(UserInfo userInfo, 
												String jigyoId) 
										throws NoDataFoundException, ApplicationException {
	
		return updateJigyoShinsaComplete(userInfo, jigyoId, ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE);

//		String shinsainNo = userInfo.getShinsainInfo().getShinsainNo();		//審査員番号
//		String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun();		//事業区分
//		
//		Connection   connection  = null;
//		boolean     success     = false;
//		try {
//			connection = DatabaseUtil.getConnection();
//	
//			//審査結果管理DAO
//			ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);
//					
//			//総合評価がNULLのデータがあるかどうかを確認
//			int count = 0;
//			try{
//				count = dao.countShinsaKekkaInfo(
//									connection,
//									shinsainNo,
//									jigyoKubun,
//									jigyoId);
//			}catch(DataAccessException e){
//				throw new ApplicationException(
//					"審査結果データ取得中にDBエラーが発生しました。",
//					new ErrorInfo("errors.4004"),
//					e);
//			}		
//			
//			//総合評価がNULLのデータがあったらエラーを返す
//			if(count != 0){
//				return false;
//			}
//			
//			//審査状況を更新
//			try{
//				dao.updateShinsaKekkaInfo(
//								connection, 
//								shinsainNo, 
//								jigyoKubun, 
//								null,
//								jigyoId,
//								ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE);
//			}catch(NoDataFoundException e){
//				throw e;
//			}catch(DataAccessException e){
//				throw new ApplicationException(
//					"審査結果データ更新中にDBエラーが発生しました。",
//					new ErrorInfo("errors.4002"),
//					e);
//			}
//			success = true;
//		} finally {
//			try {
//				if (success) {
//					DatabaseUtil.commit(connection);
//				} else {
//					DatabaseUtil.rollback(connection);
//				}
//			} catch (TransactionException e) {
//				throw new ApplicationException(
//					"審査結果情報DB更新中にエラーが発生しました。",
//					new ErrorInfo("errors.4002"),
//					e);
//			} finally {							
//				DatabaseUtil.closeConnection(connection);
//			}
//		}		
//		return true;
	}



	/**
	 * 審査状況の更新.<br /><br />
	 * 
	 * 審査状況を更新する自クラスのメソッド<br />
	 * updateJigyoShinsaComplete(userInfo, jigyoId, ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET)<br />
	 * を呼び出し、審査状況を『審査未完了』に更新する。<br />
	 * メソッドに与える第三引数(審査状況を表すString)は"0"(審査未完了を表す)<br /><br />
	 * 
	 * 正常に更新が行われたら、trueを返却する。<br /><br />
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param jigyoId String
	 * @return boolean
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean updateJigyoShinsaCompleteYet(UserInfo userInfo, 
												String jigyoId) 
										throws NoDataFoundException, ApplicationException {
	
		return updateJigyoShinsaComplete(userInfo, jigyoId, ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET);
	
	}
	
	

	/**
	 * 審査状況の更新.<br /><br />
	 * 
	 * 与えられたshinsaJokyoの値に従い、審査状況を更新する。<br /><br />
	 * 
	 * 1.第三引数のshinsaJokyoの値がnullであった場合には、
	 * shinsaJokyoに"1"(審査完了)をセットする。<br /><br />
	 * 
	 * 2.以下のSQL文を発行して、総合評価がNULLのデータがあるかどうかを確認する。
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	COUNT(*)
	 * FROM
	 * 	SHINSAKEKKA A,
	 * 		(
	 * 		SELECT
	 * 			*
	 * 		FROM
	 * 			SHINSEIDATAKANRI
	 * 		WHERE
	 * 			(JOKYO_ID = '10'
	 * 			OR JOKYO_ID = '11')
	 * 			AND DEL_FLG = '0'	--削除されてないもの
	 * 		) B
	 * WHERE
	 * 	A.SYSTEM_NO = B.SYSTEM_NO
	 * 	AND A.KEKKA_ABC IS NULL		--総合評価（ABC）
	 * 	AND A.KEKKA_TEN IS NULL		--総合評価（点数）
	 * 	AND A.SHINSAIN_NO = ?
	 * 	AND A.JIGYO_KUBUN = ?
	 * 	AND A.JIGYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第一引数userInfoの変数shinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第一引数userInfoの変数JigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数JigyoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 総合評価がNULLのデータがあった場合には、falseを返却して完了となる。<br /><br />
	 * 
	 * 3.NULLのデータがない場合には、以下のSQL文を発行して審査状況を更新する。
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAKEKKA A
	 * SET
	 * 	A.SHINSA_JOKYO = ?
	 * WHERE
	 * 	A.SYSTEM_NO IN(
	 * 		SELECT
	 * 			B.SYSTEM_NO
	 * 		FROM
	 * 			SHINSEIDATAKANRI B
	 * 			, SHINSAKEKKA C
	 * 		WHERE
	 * 			B.SYSTEM_NO=C.SYSTEM_NO
	 * 			AND B.DEL_FLG=0
	 * 			AND (B.JOKYO_ID = '10' OR B.JOKYO_ID = '11')
	 * 	)
	 * 	AND A.SHINSAIN_NO = ?"
	 * 	AND A.JIGYO_KUBUN = ?
	 * 	AND A.JIGYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査状況</td><td>第三引数shinsaJokyoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第一引数userInfoの変数shinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第一引数userInfoの変数jigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数のJigyoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 4.更新が正常に終了したら、trueを返却する。
	 * 正常に終了しなかった場合には、例外をthrowする。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param jigyoId String
	 * @param shinsaJokyo String
	 * @return boolean
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean updateJigyoShinsaComplete(UserInfo userInfo, 
												String jigyoId,
												String shinsaJokyo) 
										throws NoDataFoundException, ApplicationException {
	

		String shinsainNo = userInfo.getShinsainInfo().getShinsainNo();		//審査員番号
		String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun();		//事業区分
		
		//審査状況がnullなら審査完了へ
		if(shinsaJokyo == null) {
			shinsaJokyo = ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE;
		}
		
		Connection   connection  = null;
		boolean     success     = false;
		try {
			connection = DatabaseUtil.getConnection();
	
			//審査結果管理DAO
			ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);
					
			//総合評価がNULLのデータがあるかどうかを確認
			int count = 0;
			try{
				count = dao.countShinsaKekkaInfo(
									connection,
									shinsainNo,
									jigyoKubun,
									jigyoId);
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}		
			
			//総合評価がNULLのデータがあったらエラーを返す
			if(count != 0){
				return false;
			}
			
			//審査状況を更新
			try{
				dao.updateShinsaKekkaInfo(
								connection, 
								shinsainNo, 
								jigyoKubun, 
								null,
								jigyoId,
								shinsaJokyo);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			}
			
			//メールアドレス登録画面で「登録しない」を選択し、メールアドレスが登録されている場合のみメールを送信する
			if(!"1".equals(userInfo.getShinsainInfo().getMailFlg()) 
				&& !StringUtil.isBlank(userInfo.getShinsainInfo().getSofuZipemail())){
				
				//-----メール本文ファイルの読み込み
				String content = null;
				try{
					File contentFile = new File(CONTENT_SHINSAKEKKA_JURI_TSUCHI);
					FileResource fileRes = FileUtil.readFile(contentFile);
					content = new String(fileRes.getBinary());
				}catch(FileNotFoundException e){
					log.warn("メール本文ファイルが見つかりませんでした。", e);
					return success;
				}catch(IOException e){
					log.warn("メール本文ファイル読み込み時にエラーが発生しました。",e);
					return success;
				}
				
				//事業情報を取得
				JigyoKanriInfoDao jigyoKanriDao = new JigyoKanriInfoDao(userInfo);
				JigyoKanriPk jigyoKanriPk = new JigyoKanriPk();
				JigyoKanriInfo jigyoKanriInfo = null;
				
				jigyoKanriPk.setJigyoId(jigyoId);
				
				try {
					jigyoKanriInfo = jigyoKanriDao.selectJigyoKanriInfo(connection, jigyoKanriPk);
					
				}catch(NoDataFoundException e){
					throw e;
				}catch(DataAccessException e){
					throw new ApplicationException(
						"審査結果データ取得中にDBエラーが発生しました。",
						new ErrorInfo("errors.4002"),
						e);
				}
				
				//研究種目名を作成
				String shumokuName = null;
				String nendo       = jigyoKanriInfo.getNendo();
				String jigyoName   = jigyoKanriInfo.getJigyoName();
				String kaisu       = jigyoKanriInfo.getKaisu();
				
				//回数は複数回のときのみ表示する
				if(Integer.parseInt(kaisu) > 1){
					shumokuName = "平成"+ nendo + "年度 第"+ kaisu +"回 "+ jigyoName; 
				}else{
					shumokuName = "平成"+ nendo + "年度 "+ jigyoName;
				}
				
				
				//---総合評点リストを取得
				List hyotenList = null;
				try {
					hyotenList = dao.getSogoHyokaList(
												connection, 
												shinsainNo,
												jigyoKubun,
												jigyoId);
				} catch (DataAccessException e) {
					throw new ApplicationException(
						"審査結果データ検索中にDBエラーが発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}

				//マップに入れなおす
				int allcount = 0;
				Map hyotenMap = new HashMap();
				for(int i=0; i<hyotenList.size(); i++){
					Map recordMap = (Map)hyotenList.get(i);	//１レコード
	
					String kekkaTen_ = (String)recordMap.get("KEKKA_TEN");
					String count_ = ((Number)recordMap.get("COUNT")).toString();
					allcount = allcount + Integer.parseInt(count_);
	
					hyotenMap.put(kekkaTen_, count_);
				}
				//「すべて」（総件数）のキーは「0」でセット
				hyotenMap.put("0", Integer.toString(allcount));	
				
				
				//-----メール送信
				SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
			
				
				//メール本文変換データ
				String[] param = new String[]{
											  shumokuName,							//研究種目名
											  Integer.toString(allcount)			//審査件数
											  };
				
				//メール本文テンプレートを変換							  
				String   body  = MessageFormat.format(content, param);			
			
				try{
					mailer.sendMail(FROM_ADDRESS,									//差出人
									userInfo.getShinsainInfo().getSofuZipemail(),	//to
									null,											//cc
									null,											//bcc
									SUBJECT_SHINSAKEKKA_JURI_TSUCHI,				//件名
									body);											//本文
				
				}catch(Exception e){
					log.warn("メール送信に失敗しました。",e);
					//return success;		2005/11/14　tanabe メール送信に失敗しても処理は続行させる。
				}
			}
			success = true;
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"審査結果情報DB更新中にエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {							
				DatabaseUtil.closeConnection(connection);
			}
		}		
		return true;
	}


	/**
	 * 審査状況の更新.<br /><br />
	 * 
	 * 以下のSQL文を発行して、審査状況を未完了にする。
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAKEKKA A
	 * SET
	 * 	A.SHINSA_JOKYO = ?
	 * WHERE
	 * 	A.SYSTEM_NO IN(
	 * 		SELECT
	 * 			B.SYSTEM_NO
	 * 		FROM
	 * 			SHINSEIDATAKANRI B
	 * 			, SHINSAKEKKA C
	 * 		WHERE
	 * 			B.SYSTEM_NO=C.SYSTEM_NO
	 * 			AND B.DEL_FLG=0
	 * 			AND (B.JOKYO_ID = '10' OR B.JOKYO_ID = '11')
	 * 
	 * 	<b><span style="color:#002288">－－動的検索条件－－</span></b>
	 * 
	 * 	)
	 * 	AND A.SHINSAIN_NO = ?"
	 * 	AND A.JIGYO_KUBUN = ?
	 * 	AND A.JIGYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>システム番号</td><td>systemNo</td><td>AND B.SYSTEM_NO= ?</td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査状況</td><td>0(未完了)</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>システム番号</td><td>第二引数systemNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第一引数userInfoの変数shinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第一引数userInfoの変数jigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>null</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param systemNo String
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void updateShinseiShinsaComplete(UserInfo userInfo, String systemNo) throws NoDataFoundException, ApplicationException {

		String shinsainNo = userInfo.getShinsainInfo().getShinsainNo();		//審査員番号
		String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun();		//事業区分

		Connection   connection  = null;
		boolean     success     = false;
		try {
			connection = DatabaseUtil.getConnection();
	
			//審査結果管理DAO
			ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);
	
			
			//審査状況を更新
			try{
				dao.updateShinsaKekkaInfo(connection, shinsainNo, jigyoKubun, systemNo, null, ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			}
			success = true;
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"審査結果情報DB更新中にエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {							
				DatabaseUtil.closeConnection(connection);
			}
		}		
	}

	/**
	 * ラベル名の取得.<br /><br />
	 * 
	 * 第一引数のListが持つLabelValueBeanの中のラベル名から、
	 * 指定された値に該当するラベル名を返す。<br /><br />
	 * 
	 * 指定された値に該当するラベル名が存在しない場合は、
	 * 値そのものを返す。<br /><br />
	 * 
	 * @param list LabelValueBeanを持つList
	 * @param value String
	 * @return ラベル名のString
	 */
	private String getLabelName(List list, String value){
		String labelName = value;  //初期値として値をセットする
		for(int i=0; i<list.size(); i++){
			LabelValueBean bean = (LabelValueBean)list.get(i);
			if(bean.getValue().equals(value)){
				labelName = bean.getLabel();
				break;
			}
		}
		return labelName;
	}

	//使用しない。
	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.IShinsaKekkaMaintenance#selectShinsaKekkaTantoListKiban(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String, jp.go.jsps.kaken.model.vo.SearchInfo)
	 */
//	public Map selectShinsaKekkaTantoListKiban(UserInfo userInfo,
//												String jigyoId,
//												String kekkaTen,
//												SearchInfo searchInfo) 
//			throws NoDataFoundException, ApplicationException  
//	{
//
//		String shinsainNo = userInfo.getShinsainInfo().getShinsainNo();		//審査員番号
//		String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun();		//事業区分
//					
//		//DBコネクションの取得
//		Connection connection = null;	
//		try{
//			connection = DatabaseUtil.getConnection();
//			ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);			
//			//---審査担当申請一覧ページ情報
//			Page pageInfo = null;
//			try {
//				pageInfo = dao.selectShinsaKekkaTantoList(
//											connection, 
//											shinsainNo,
//											jigyoKubun,
//											jigyoId,
//											kekkaTen,
//											searchInfo);
//			} catch (DataAccessException e) {
//				throw new ApplicationException(
//					"審査結果データ検索中にDBエラーが発生しました。",
//					new ErrorInfo("errors.4004"),
//					e);
//			} catch (NoDataFoundException e){
//				//0件のページオブジェクトを生成（点数ごとに再検索した場合、0件の場合も一覧に表示するため）
//				pageInfo = Page.EMPTY_PAGE;			
//			}
//
//			//ラベル名をラベルマスタから取得してセット
//			List tekisetsuKaigaiList = null;		//適切性-海外
//			List tekisetsuKenkyu1List = null;		//適切性-研究（1）
//			List tekisetsuList = null;				//適切性			
//			List datoList = null;					//妥当性
//			try{
//				String[] labelKubun = new String[]{ILabelKubun.TEKISETSU_KAIGAI,
//													ILabelKubun.TEKISETSU_KENKYU1,
//													ILabelKubun.TEKISETSU,
//													ILabelKubun.DATO};
//				List bothList = new LabelValueMaintenance().getLabelList(labelKubun);	//4つのラベルリスト
//				tekisetsuKaigaiList = (List)bothList.get(0);		
//				tekisetsuKenkyu1List = (List)bothList.get(1);		
//				tekisetsuList = (List)bothList.get(2);
//				datoList = (List)bothList.get(3);				
//			}catch(ApplicationException e){
//				throw new ApplicationException(
//					"ラベルマスタ検索中にDBエラーが発生しました。",
//					new ErrorInfo("errors.4004"),
//					e);
//			}
//						
//			List list = pageInfo.getList();
//			for(int i = 0;i< list.size();i ++){
//				Map lineMap = (Map)list.get(i);
//				//値を取得
//				String tekisetsuKaigaiValue = (String) lineMap.get("TEKISETSU_KAIGAI");		//適切性-海外
//				String tekisetsuKenkyu1Value = (String) lineMap.get("TEKISETSU_KENKYU1");	//適切性-研究（1）
//				String tekisetsuValue = (String) lineMap.get("TEKISETSU");					//適切性
//				String datoValue = (String) lineMap.get("DATO");							//妥当性
//	
//				//ラベル名を取得
//				String tekisetsuKaigaiLabel = getLabelName(tekisetsuKaigaiList, tekisetsuKaigaiValue);
//				String tekisetsuKenkyu1Label = getLabelName(tekisetsuKenkyu1List, tekisetsuKenkyu1Value);
//				String tekisetsuLabel =  getLabelName(tekisetsuList, tekisetsuValue);
//				String datoLabel = getLabelName(datoList, datoValue);
//	
//				//ラベル名をセット
//				lineMap.put("TEKISETSU_KAIGAI_LABEL", tekisetsuKaigaiLabel);
//				lineMap.put("TEKISETSU_KENKYU1_LABEL", tekisetsuKenkyu1Label);
//				lineMap.put("TEKISETSU_LABEL", tekisetsuLabel);
//				lineMap.put("DATO_LABEL", datoLabel);
//	
//				//リストから削除して追加しなおす
//				list.remove(i);
//				list.add(i, lineMap);
//			}	
//
//			//---審査状況が「0:未完了」のデータがあるかどうかを確認
//			int count = 0;
//			try {
//				count = dao.countShinsaKekkaInfo(
//										connection,
//										shinsainNo,
//										jigyoKubun,
//										null,
//										jigyoId,
//										ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET);
//			} catch (DataAccessException e) {
//				throw new ApplicationException(
//					"審査結果データ検索中にDBエラーが発生しました。",
//					new ErrorInfo("errors.4004"),
//					e);
//			}
//			
//			//審査状況が「0:未完了」のデータがなかったらTRUEを返す
//			String shinsaCompleteFlg = "FALSE";
//			if(count == 0){
//				shinsaCompleteFlg = "TRUE";	
//			}
//			
//			//---総合評点リストを取得
//			List hyotenList = null;
//			try {
//				hyotenList = dao.getSogoHyokaList(
//											connection, 
//											shinsainNo,
//											jigyoKubun,
//											jigyoId);
//			} catch (DataAccessException e) {
//				throw new ApplicationException(
//					"審査結果データ検索中にDBエラーが発生しました。",
//					new ErrorInfo("errors.4004"),
//					e);
//			}
//
//			//マップに入れなおす
//			int allcount = 0;
//			Map hyotenMap = new HashMap();
//			for(int i=0; i<hyotenList.size(); i++){
//				Map recordMap = (Map)hyotenList.get(i);	//１レコード
//	
//				String kekkaTen_ = (String)recordMap.get("KEKKA_TEN");
//				String count_ = ((Number)recordMap.get("COUNT")).toString();
//				allcount = allcount + Integer.parseInt(count_);
//	
//				hyotenMap.put(kekkaTen_, count_);
//			}
//			//「すべて」（総件数）のキーは「0」でセット
//			hyotenMap.put("0", Integer.toString(allcount));
//
//			//戻り値Mapを作成
//			Map map = new HashMap();
//			//審査担当分一覧（一覧データ）
//			map.put(KEY_SHINSATANTO_LIST, pageInfo);
//			//審査担当分一覧（審査完了フラグ）
//			map.put(KEY_SHINSACOMPLETE_FLG,	shinsaCompleteFlg);
//			//審査担当分一覧（総合評点リスト）
//			map.put(KEY_SOGOHYOTEN_LIST, hyotenMap);
//
//			return map;
//
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//
////		try{
////			connection = DatabaseUtil.getConnection();
////			ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);			
////			//---審査担当申請一覧ページ情報
////			List hyotenList = null;
////			try {
////				hyotenList = dao.getSogoHyotenList(
////											connection, 
////											shinsainNo,
////											jigyoKubun,
////											jigyoId);
////			} catch (DataAccessException e) {
////				throw new ApplicationException(
////					"審査結果データ検索中にDBエラーが発生しました。",
////					new ErrorInfo("errors.4004"),
////					e);
////			}
////
////			int allcount = 0;
////			Map hyotenMap = new HashMap();
////			for(int i=0; i<hyotenList.size(); i++){
////				Map recordMap = (Map)hyotenList.get(i);	//１レコード
////				
////				String kekkaTen_ = (String)recordMap.get("KEKKA_TEN");
////				String count = ((Number)recordMap.get("COUNT")).toString();
////				allcount = allcount + Integer.parseInt(count);
////				
////				hyotenMap.put(kekkaTen_, count);
////			}
////			hyotenMap.put("0", Integer.toString(allcount));
////
////			map.put("hyotenList", hyotenMap);
//
////-----別の方法
////		Page pageInfo = (Page)map.get(IShinsaKekkaMaintenance.KEY_SHINSATANTO_LIST);
////		List recordList = pageInfo.getList();
//		
////		Map listMap = new HashMap();
////		Map countMap = new HashMap();
////		
////		listMap.put("0", recordList);
////		countMap.put("0", Integer.toString(recordList.size()));
////				
////		for(int i=0; i<recordList.size(); i++){
////			Map recordMap = (Map)recordList.get(i);	//１レコード
////				
////			//リスト
////			List indexValueList = null;
////			if(listMap.containsKey(recordMap.get("KEKKA_TEN"))){
////				indexValueList = (List)listMap.get(recordMap.get("KEKKA_TEN"));
////			}else{
////				indexValueList = new ArrayList();
////				listMap.put(recordMap.get("KEKKA_TEN"), indexValueList);
////			}
////			indexValueList.add(recordMap);
////
////			//総件数
////			int count = 0;
////			if(countMap.containsKey(recordMap.get("KEKKA_TEN"))){
////				count = Integer.parseInt((String)recordMap.get("KEKKA_TEN"));
////				countMap.put((String)recordMap.get("KEKKA_TEN"), Integer.toString(count++));
////			}else{
////				countMap.put((String)recordMap.get("KEKKA_TEN"), "1");
////			}
////		}
////			map.put("list", listMap);
////			map.put("count", countMap);
//
////			return map;
////		} finally {
////			DatabaseUtil.closeConnection(connection);
////		}
//	}
    
// 2006-10-25 張志男 利害関係入力完了 追加 ここから
    /**
     * 入力状況の更新.
     * 正常に更新が行われたら、trueを返却する。
     * 
     * @param userInfo UserInfo
     * @param jigyoId String
     * @return void
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void updateRiekiSohanComplete(UserInfo userInfo, String jigyoId)
            throws NoDataFoundException, ApplicationException {

        String shinsainNo = userInfo.getShinsainInfo().getShinsainNo(); // 審査員番号
        String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun(); // 事業区分
        Connection connection = null;
        boolean success = false;

        try {
            connection = DatabaseUtil.getConnection();

            // 審査結果管理DAO
            ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);

            // 入力状況を更新
            try {
                dao.updateNyuryokuJokyo(connection, shinsainNo, jigyoKubun,
                        jigyoId, ShinsaKekkaMaintenance.NYURYOKUJOKYO_COMPLETE);
            } catch (NoDataFoundException e) {
                throw e;
            } catch (DataAccessException e) {
                throw new ApplicationException("審査結果情報DB更新中にエラーが発生しました。",
                        new ErrorInfo("errors.4002"), e);
            }
            success = true;
        } finally {
            try {
                if (success) {
                    DatabaseUtil.commit(connection);
                } else {
                    DatabaseUtil.rollback(connection);
                }
            } catch (TransactionException e) {
                throw new ApplicationException("審査結果情報DB更新中にエラーが発生しました。",
                        new ErrorInfo("errors.4002"), e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
// 2006-10-25 張志男 利害関係入力完了 追加 ここまで
  
//2006/10/27　苗　追加ここから
    /**
     * 利害関係意見の登録
     * @param userInfo UserInfo
     * @param shinsaKekkaInputInfo ShinsaKekkaInputInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registRiekiSohan(
            UserInfo userInfo,
            ShinsaKekkaInputInfo shinsaKekkaInputInfo)
        throws NoDataFoundException, ApplicationException {
        
        Connection   connection  = null;
        boolean     success     = false;
        try {
            //DBコネクションの取得
            connection = DatabaseUtil.getConnection();

			//システム受付番号
			String systemNo = shinsaKekkaInputInfo.getSystemNo();
            
            //--------------------
            // 利害関係意見登録
            //--------------------                      
            //審査結果DAO
            ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);            
            //既存データを取得する
            ShinsaKekkaPk shinsaKekkaPk = (ShinsaKekkaPk)shinsaKekkaInputInfo;
            ShinsaKekkaInfo shinsaKekkaInfo = null;
            try{
                shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfoForLock(connection, shinsaKekkaPk);
            }catch(NoDataFoundException e){
                throw e;
            }catch(DataAccessException e){
                throw new ApplicationException(
                    "審査結果情報取得中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"),
                    e);
            }           

            //---DB更新---
            try {
                //更新データをセットする       
                shinsaKekkaInfo.setComments(shinsaKekkaInputInfo.getComments());                //コメント
                shinsaKekkaInfo.setRigai(shinsaKekkaInputInfo.getRigai());                      //利害関係
                                              
                shinsaKekkaInfo.setKekkaAbc(shinsaKekkaInputInfo.getKekkaAbc());                //総合評価（ABC）
                shinsaKekkaInfo.setKekkaTen(shinsaKekkaInputInfo.getKekkaTen());                //総合評価（点数）
                shinsaKekkaInfo.setKenkyuNaiyo(shinsaKekkaInputInfo.getKenkyuNaiyo());          //研究内容
                shinsaKekkaInfo.setKenkyuKeikaku(shinsaKekkaInputInfo.getKenkyuKeikaku());      //研究計画
                shinsaKekkaInfo.setTekisetsuKaigai(shinsaKekkaInputInfo.getTekisetsuKaigai());  //適切性-海外
                shinsaKekkaInfo.setTekisetsuKenkyu1(shinsaKekkaInputInfo.getTekisetsuKenkyu1());//適切性-研究(1)
                shinsaKekkaInfo.setTekisetsu(shinsaKekkaInputInfo.getTekisetsu());              //適切性
                shinsaKekkaInfo.setDato(shinsaKekkaInputInfo.getDato());                        //妥当性
                shinsaKekkaInfo.setWakates(shinsaKekkaInputInfo.getWakates());					//若手S　2007/5/11追加
                shinsaKekkaInfo.setJuyosei(shinsaKekkaInputInfo.getJuyosei());                  //学術的重要性・妥当性
                shinsaKekkaInfo.setDokusosei(shinsaKekkaInputInfo.getDokusosei());              //独創性・革新性
                shinsaKekkaInfo.setHakyukoka(shinsaKekkaInputInfo.getHakyukoka());              //波及効果・普遍性
                shinsaKekkaInfo.setSuikonoryoku(shinsaKekkaInputInfo.getSuikonoryoku());        //遂行能力・環境の適切性
                shinsaKekkaInfo.setJinken(shinsaKekkaInputInfo.getJinken());                    //人権の保護・法令等の遵守
                shinsaKekkaInfo.setBuntankin(shinsaKekkaInputInfo.getBuntankin());              //分担金配分
                shinsaKekkaInfo.setOtherComment(shinsaKekkaInputInfo.getOtherComment());        //その他コメント

                //審査員情報をセットする
                shinsaKekkaInfo.setShinsainNameKanjiSei(userInfo.getShinsainInfo().getNameKanjiSei());  //審査員名（漢字-姓）
                shinsaKekkaInfo.setShinsainNameKanjiMei(userInfo.getShinsainInfo().getNameKanjiMei());  //審査員名（漢字-名）
                shinsaKekkaInfo.setNameKanaSei(userInfo.getShinsainInfo().getNameKanaSei());            //審査員名（フリガナ－姓）
                shinsaKekkaInfo.setNameKanaMei(userInfo.getShinsainInfo().getNameKanaMei());            //審査員名（フリガナ－名）
                shinsaKekkaInfo.setShozokuName(userInfo.getShinsainInfo().getShozokuName());            //審査員所属機関名              
                shinsaKekkaInfo.setBukyokuName(userInfo.getShinsainInfo().getBukyokuName());            //審査員部局名
                shinsaKekkaInfo.setShokushuName(userInfo.getShinsainInfo().getShokushuName());          //審査員職名
                shinsaDao.updateShinsaKekkaInfo(connection, shinsaKekkaInfo);
                
                success = true;              
            } catch (DataAccessException e) {
                throw new ApplicationException(
                    "審査結果情報更新中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4002"),
                    e);
            } catch (ApplicationException e) {
                throw new ApplicationException(
                    "審査結果情報更新中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4002"),
                    e);
            }
            
			//--------------------
			// 申請データ更新
			//--------------------			
			//申請データ管理DAO
			ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo);
						
			//排他制御のため既存データを取得する
			ShinseiDataPk shinseiDataPk = new ShinseiDataPk(systemNo);
			ShinseiDataInfo existInfo = null;
			try{
				existInfo = dao.selectShinseiDataInfoForLock(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"申請書管理データ排他取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}

			//---申請データ削除フラグチェック---
			String delFlag = existInfo.getDelFlg(); 
			if(FLAG_APPLICATION_DELETE.equals(delFlag))
			{
				throw new ApplicationException(
					"当該申請データは削除されています。SystemNo=" + systemNo,
					new ErrorInfo("errors.9001"));
			}			
			//---申請データステータスチェック---
			String jyokyoId = existInfo.getJokyoId();
			//---審査員割り振り処理後、割り振りチェック完了、1次審査中、1次審査完了以外の場合はエラー
			if( !(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO.equals(jyokyoId)) &&
				 !(StatusCode.STATUS_WARIFURI_CHECK_KANRYO.equals(jyokyoId)) &&
				 !(StatusCode.STATUS_1st_SHINSATYU.equals(jyokyoId)) &&
				 !(StatusCode.STATUS_1st_SHINSA_KANRYO.equals(jyokyoId)) )
			{
				throw new ApplicationException(
					"当該申請データは１次審査登録可能なステータスではありません。SystemNo="
					+ systemNo,
					new ErrorInfo("errors.9012"));
			}			
			
			//---審査結果レコード取得（結果ABCの昇順）---
			ShinsaKekkaInfo[] shinsaKekkaInfoArray = null;						
			try{
				shinsaKekkaInfoArray = shinsaDao.selectShinsaKekkaInfo(connection, shinseiDataPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}

			//---総合評価マスタから総合評価情報の一覧を取得する---					
			HashMap sogoHyokaMap = new HashMap();
			List hyokaList = MasterSogoHyokaInfoDao.selectSogoHyokaInfoList(connection);	
			Iterator iterator = hyokaList.iterator();
			while(iterator.hasNext()){
				Map map = (Map)iterator.next();
				String sogoHyoka = (String) map.get("SOGO_HYOKA");		//総合評価
				String jigyoKubun = new Integer(((Number) map.get("JIGYO_KUBUN")).intValue()).toString();	//事業区分
				String tensu =  new Integer(((Number) map.get("TENSU")).intValue()).toString();			//点数
				sogoHyokaMap.put(jigyoKubun + sogoHyoka, tensu);		//キー：事業区分+総合評価、値：点数 
			}	
			
			//---DB更新---
			try {
				String kekkaAbc = "";
				int intKekkaTen = 0;
				String kekkaTenSorted = "";
				boolean kekkaTenFlag = false;
				for(int i=0; i<shinsaKekkaInfoArray.length; i++){
					try{
//						//総合評価（ABC）と総合評価（点数）は混在しない
//						if(shinsaKekkaInfoArray[i].getKekkaAbc() != null){
//							kekkaAbc = kekkaAbc + shinsaKekkaInfoArray[i].getKekkaAbc();
//							String tensu = (String) sogoHyokaMap.get(shinsaKekkaInfoArray[i].getJigyoKubun()
//														+ shinsaKekkaInfoArray[i].getKekkaAbc());
//							if(tensu == null){
//								throw new ApplicationException(
//									"総合評価マスタに一致するデータが存在しません。検索キー：総合評価'"
//									+ shinsaKekkaInfoArray[i].getKekkaAbc() 
//									+ "',事業区分：'" +  shinsaKekkaInfoArray[i].getJigyoKubun() + "'",
//									new ErrorInfo("errors.4002"));	
//							}
//							intKekkaTen = intKekkaTen
//												+ Integer.parseInt((String) sogoHyokaMap.get(
//																shinsaKekkaInfoArray[i].getJigyoKubun()
//																	 + shinsaKekkaInfoArray[i].getKekkaAbc()));
//							kekkaTenFlag = true;	//1つでも点数が設定されていた場合[true]						
//						}else 
						if(shinsaKekkaInfoArray[i].getKekkaTen() != null){
								//１次審査結果(点数)
								intKekkaTen = intKekkaTen
													+ Integer.parseInt((String) sogoHyokaMap.get(
																	shinsaKekkaInfoArray[i].getJigyoKubun()
																		 + shinsaKekkaInfoArray[i].getKekkaTen()));
								
								//１次審査結果(点数順)
								if(kekkaTenSorted.equals("")){
									kekkaTenSorted = kekkaTenSorted + shinsaKekkaInfoArray[i].getKekkaTen();
								}else{
									kekkaTenSorted = kekkaTenSorted + " " + shinsaKekkaInfoArray[i].getKekkaTen();									
								}
								
							kekkaTenFlag = true;	//1つでも点数が設定されていた場合[true]
						}
					}catch(NumberFormatException e){
						//数値として認識できない場合は処理を飛ばす
					}
				}
				
				//数値として認識できる点数が１つでもセットされていた場合は登録する
				String kekkaTen = null;
				if(kekkaTenFlag){
					kekkaTen = new Integer(intKekkaTen).toString();
				}
				
				//更新データをセットする
				existInfo.setKekka1Abc(kekkaAbc);								//１次審査結果(ABC)
				existInfo.setKekka1Ten(kekkaTen);								//１次審査結果(点数)
				existInfo.setKekka1TenSorted(kekkaTenSorted);					//１次審査結果(点数順)				
				existInfo.setJokyoId(StatusCode.STATUS_1st_SHINSA_KANRYO);		//申請状況
				dao.updateShinseiDataInfo(connection, existInfo, true);
				
				success = true;
				
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"申請情報更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} catch (ApplicationException e) {
				throw new ApplicationException(
					"申請情報更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			}
            
            
       } finally {
            try {
                if (success) {
                    DatabaseUtil.commit(connection);
                } else {
                    DatabaseUtil.rollback(connection);
                }
            } catch (TransactionException e) {
                throw new ApplicationException(
                    "審査結果情報、申請情報DB更新中にエラーが発生しました。",
                    new ErrorInfo("errors.4002"),
                    e);
            } finally {                         
                DatabaseUtil.closeConnection(connection);
            }
        }     
    }
    
    /**
     * 審査結果情報の取得(利害関係用)
     * @param userInfo UserInfo
     * @param shinsaKekkaPk ShinsaKekkaPk
     * @return 審査結果情報を持つShinsaKekkaInputInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public ShinsaKekkaInputInfo selectShinsaKekkaForRiekiSohan(
        UserInfo userInfo,
        ShinsaKekkaPk shinsaKekkaPk)
        throws NoDataFoundException, ApplicationException {
        
        Connection connection = null;
        try {
            //DBコネクションの取得
            connection = DatabaseUtil.getConnection();

            //申請データ管理DAO
            ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo);
            //該当申請データを取得する
            ShinseiDataPk shinseiDataPk = new ShinseiDataPk(shinsaKekkaPk
                    .getSystemNo());
            ShinseiDataInfo shinseiDataInfo = null;
            try {
                shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection,
                        shinseiDataPk, true);
            } catch (NoDataFoundException e) {
                throw e;
            } catch (DataAccessException e) {
                throw new ApplicationException("申請書管理データ取得中にDBエラーが発生しました。",
                        new ErrorInfo("errors.4004"), e);
            }

            //審査結果DAO
            ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);
            //該当審査結果データを取得する
            ShinsaKekkaInfo shinsaKekkaInfo = null;
            try {
                shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfo(connection,
                        shinsaKekkaPk);
            } catch (NoDataFoundException e) {
                throw e;
            } catch (DataAccessException e) {
                throw new ApplicationException("審査結果データ取得中にDBエラーが発生しました。",
                        new ErrorInfo("errors.4004"), e);
            }

            //---審査結果入力オブジェクトの生成
            ShinsaKekkaInputInfo info = new ShinsaKekkaInputInfo();
            info.setSystemNo(shinsaKekkaPk.getSystemNo()); //システム受付番号
            info.setShinsainNo(shinsaKekkaPk.getShinsainNo()); //審査員番号
            info.setJigyoKubun(shinsaKekkaPk.getJigyoKubun()); //事業区分
            info.setNendo(shinseiDataInfo.getNendo()); //年度
            info.setKaisu(shinseiDataInfo.getKaisu()); //回数

            info.setBunkaSaimokuCd(shinseiDataInfo.getKadaiInfo().getBunkaSaimokuCd()); //細目番号
            info.setSaimokuName(shinseiDataInfo.getKadaiInfo().getSaimokuName()); //細目名
            info.setKaigaibunyaCd(shinseiDataInfo.getKaigaibunyaCd());//海外分野コード
            info.setKaigaibunyaName(shinseiDataInfo.getKaigaibunyaName());//海外分野名
            info.setJigyoId(shinseiDataInfo.getJigyoId()); //事業ID          
            info.setJigyoName(shinseiDataInfo.getJigyoName()); //研究種目名
            info.setUketukeNo(shinseiDataInfo.getUketukeNo()); //申請番号
            info.setKadaiNameKanji(shinseiDataInfo.getKadaiInfo().getKadaiNameKanji()); //研究課題名
            info.setNameKanjiSei(shinseiDataInfo.getDaihyouInfo().getNameKanjiSei()); //申請者名-姓
            info.setNameKanjiMei(shinseiDataInfo.getDaihyouInfo().getNameKanjiMei()); //申請者名-名
            info.setShozokuName(shinseiDataInfo.getDaihyouInfo().getShozokuName()); //所属機関名
            info.setBukyokuName(shinseiDataInfo.getDaihyouInfo().getBukyokuName()); //部局名
            info.setShokushuName(shinseiDataInfo.getDaihyouInfo().getShokushuNameKanji()); //職名
            info.setShinseiFlgNo(shinseiDataInfo.getShinseiFlgNo()); //研究計画最終年度前年度の応募
            info.setBuntankinFlg(shinseiDataInfo.getBuntankinFlg()); //分担金の有無
            info.setRigai(shinsaKekkaInfo.getRigai()); //利害関係
            info.setJigyoCd(shinsaKekkaInfo.getJigyoId().substring(2, 7)); //事業コード
            info.setComments(shinsaKekkaInfo.getComments());

            return info;
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }
//2006/10/27　苗　追加ここまで    
}