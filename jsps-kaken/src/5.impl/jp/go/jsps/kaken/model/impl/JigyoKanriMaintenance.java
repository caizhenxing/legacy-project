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

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

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
 * 事業情報管理クラス.<br><br>
 * 
 * <b>概要</b><br>
 * ・使用テーブル<br>
 * 事業情報管理テーブル	：事業の基本情報を管理<br>
 * 書類管理テーブル		：事業ごとの書類を管理<br>
 */
public class JigyoKanriMaintenance implements IJigyoKanriMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(ShozokuMaintenance.class);
	
	/** システム番号フォーマット */
	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	/** 添付ファイル（Win） */
	private static String SHINSEI_TENPUWIN_FOLDER = ApplicationSettings.getString(ISettingKeys.SHINSEI_TENPUWIN_FOLDER);

	/** 添付ファイル（Mac） */
	private static String SHINSEI_TENPUMAC_FOLDER = ApplicationSettings.getString(ISettingKeys.SHINSEI_TENPUMAC_FOLDER);
	
	/** 評価用ファイル */
	private static String SHINSEI_HYOKA_FOLDER = ApplicationSettings.getString(ISettingKeys.SHINSEI_HYOKA_FOLDER);

	/** 書類ファイル */
	private static String SHINSEI_SHORUI_FOLDER = ApplicationSettings.getString(ISettingKeys.SHINSEI_SHORUI_FOLDER);
	
	/** 非公募申請の事業区分 */
	private static String[] HIKOBO_JIGYO_KUBUN = new String[]{IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO};
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ.
	 */
	public JigyoKanriMaintenance() {
		super();
	}
	
	
	
	//---------------------------------------------------------------------
	// Private Methods
	//---------------------------------------------------------------------
	
	/**
	 * システム番号の生成.
	 * 
	 * @return システム番号
	 */
	private static synchronized String getSystemNumber(){
		//現在時刻をシステム受付番号のフォーマットに変換する
		Date now = new Date();
		String systemNo = sdf.format(now);
		return systemNo;
	}
	

	
	//---------------------------------------------------------------------
	// implement IJigyoKanriMaintenance
	//---------------------------------------------------------------------
	
	/**
	 * 事業情報の新規作成.<br/><br/>
	 * 
	 * 事業情報を新規作成する。<br/><br/>
	 * 
	 * <b>1.事業IDの作成</b><br/>
	 * (1)DateUtilクラスのchangeWareki2Seireki()メソッドにて、
	 * 第二引数addInfoの変数nendoを和暦から西暦に変換する。<br/><br/>
	 * 
	 * (2)事業IDを作成する。<br/>
	 * 　(1)で取得した年度 + 第二引数addInfoの変数jigyoCd + 第二引数addInfoの変数kaisu を事業IDとする。<br/><br/>
	 * 
	 * <b>2.アップロードファイルの書き込み</b><br>
	 * 　Win申請ファイル、Mac申請ファイル、評価ファイルについてそれぞれ処理を行う。<br><br>
	 * 
	 * (1)FileUtilクラスのgetExtention()メソッドで、ファイルの拡張子を取得する。<br><br>
	 * 
	 * (2)ファイルパスを生成する。<br>
	 * 　Win申請ファイル－変数SHINSEI_TENPUWIN_FOLDER<br>
	 * 　Mac申請ファイル－変数SHINSEI_TENPUMAC_FOLDER<br>
	 * 　評価ファイル－変数SHINSEI_HYOKA_FOLDER<br><br>
	 * 
	 * 　配列pathInfoに、1.で作成した事業ID、(1)で取得したファイル拡張子を格納する。<br/><br/>
	 * 
	 * 　変数のパターンに従い、配列pathInfoをMessageFormatクラスのformat()メソッドを用いてフォーマットする。<br/>
	 * 　生成したファイルパスで、ファイルを生成する。<br/><br/>
	 * 
	 * 　(例)Win申請ファイルパターン：${shinsei_path}/data/{0}/tenpufile/win/{0}.{1}<br/>
	 * 　　　申請ファイル拡張子：doc　　事業ID：04000011　のとき<br>
	 * 　　　ファイルパス：${shinsei_path}/data/04000011/tenpufile/win/04000011.doc<br>
	 * 　　　　※${shinsei_path}の値はApplicationSettings.propertiesに設定<br><br>
	 * 
	 * (3)ファイルを書き込む。<br>
	 * 　自クラスのwriteFile()メソッドを呼び、ファイルを書き込む。<br><br>
	 * 
	 * <b>3.事業マスタ情報より必要情報の取得（事業区分、審査区分）</b><br>
	 * (1)事業マスタ情報の取得
	 * 　事業マスタテーブルを検索し、結果レコードをMap形式で返す。<br><br>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_CD				-- 事業コード
	 *     ,A.JIGYO_NAME			-- 事業名称
	 *     ,A.KEI_KUBUN				-- 系名区分
	 *     ,A.JIGYO_KUBUN			-- 事業区分
	 *     ,A.SHINSA_KUBUN			-- 審査区分
	 *     ,A.BIKO					-- 備考
	 * FROM
	 *     MASTER_JIGYO A
	 * WHERE
	 *     JIGYO_CD = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数addInfoの変数jigyoCd</td></tr>
	 * </table><br/><br/>
	 * 
	 * (2)事業区分、審査区分のセット<br/>
	 * 　(1)で取得したMapから事業区分、審査区分を取得し、
	 * StringUtilクラスのdefaultString()メソッドで文字列をチェックする。<br/>
	 * 　addInfoの変数jigyoKubun,変数shinsaKubunをセットする。<br/><br/>
	 * 
	 * <b>4.事業情報の追加</b><br/>
	 * 　重複チェックし、データが存在しないとき登録処理を行う。<br/>
	 * 　データが存在する場合、例外をthrowする。<br/><br/>
	 * 
	 * (1)重複チェック<br/>
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- 事業ID
	 *     ,A.NENDO					-- 年度
	 *     ,A.KAISU					-- 回数
	 *     ,A.JIGYO_NAME			-- 事業名
	 *     ,A.JIGYO_KUBUN			-- 事業区分
	 *     ,A.SHINSA_KUBUN			-- 審査区分
	 *     ,A.TANTOKA_NAME			-- 業務担当課
	 *     ,A.TANTOKAKARI			-- 業務担当係名
	 *     ,A.TOIAWASE_NAME			-- 問い合わせ先担当者名
	 *     ,A.TOIAWASE_TEL			-- 問い合わせ先電話番号
	 *     ,A.TOIAWASE_EMAIL		-- 問い合わせ先E-mail
	 *     ,A.UKETUKEKIKAN_START	-- 学振受付期間（開始）
	 *     ,A.UKETUKEKIKAN_END		-- 学振受付期間（終了）
	 *     ,A.SHINSAKIGEN			-- 審査期限
	 *     ,A.TENPU_NAME			-- 添付文書名
	 *     ,A.TENPU_WIN				-- 添付ファイル格納フォルダ（Win）
	 *     ,A.TENPU_MAC				-- 添付ファイル格納フォルダ（Mac）
	 *     ,A.HYOKA_FILE_FLG		-- 評価用ファイル有無
	 *     ,A.HYOKA_FILE			-- 評価用ファイル格納フォルダ
	 *     ,A.KOKAI_FLG				-- 公開フラグ
	 *     ,A.KESSAI_NO				-- 公開決裁番号
	 *     ,A.KOKAI_ID				-- 公開確定者ID
	 *     ,A.HOKAN_DATE			-- データ保管日
	 *     ,A.YUKO_DATE				-- 保管有効期限
	 *     ,A.BIKO				-- 備考
	 *     ,A.DEL_FLG				-- 削除フラグ
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
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数addInfoの変数jigyoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * (2)事業情報の登録<br/>
	 * 　事業情報管理テーブルに登録する。<br/>
	 * 　(1)の結果、該当データが存在しないとき登録処理を行う。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * INSERT INTO JIGYOKANRI A(
	 *     A.JIGYO_ID				-- 事業ID
	 *     ,A.NENDO					-- 年度
	 *     ,A.KAISU					-- 回数
	 *     ,A.JIGYO_NAME			-- 事業名
	 *     ,A.JIGYO_KUBUN			-- 事業区分
	 *     ,A.SHINSA_KUBUN			-- 審査区分
	 *     ,A.TANTOKA_NAME			-- 業務担当課
	 *     ,A.TANTOKAKARI			-- 業務担当係名
	 *     ,A.TOIAWASE_NAME			-- 問い合わせ先担当者名
	 *     ,A.TOIAWASE_TEL			-- 問い合わせ先電話番号
	 *     ,A.TOIAWASE_EMAIL		-- 問い合わせ先E-mail
	 *     ,A.UKETUKEKIKAN_START	-- 学振受付期間（開始）
	 *     ,A.UKETUKEKIKAN_END		-- 学振受付期間（終了）
	 *     ,A.SHINSAKIGEN			-- 審査期限
	 *     ,A.TENPU_NAME			-- 添付文書名
	 *     ,A.TENPU_WIN				-- 添付ファイル格納フォルダ（Win）
	 *     ,A.TENPU_MAC				-- 添付ファイル格納フォルダ（Mac）
	 *     ,A.HYOKA_FILE_FLG		-- 評価用ファイル有無
	 *     ,A.HYOKA_FILE			-- 評価用ファイル格納フォルダ
	 *     ,A.KOKAI_FLG				-- 公開フラグ
	 *     ,A.KESSAI_NO				-- 公開決裁番号
	 *     ,A.KOKAI_ID				-- 公開確定者ID
	 *     ,A.HOKAN_DATE			-- データ保管日
	 *     ,A.YUKO_DATE				-- 保管有効期限
	 *     ,A.BIKO				-- 備考
	 *     ,A.DEL_FLG				-- 削除フラグ
	 * )
	 * VALUES
	 *     (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数addInfoの変数jigyoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>NENDO</td><td>第二引数addInfoの変数nendo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KAISU</td><td>第二引数addInfoの変数kaisu</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_NAME</td><td>第二引数addInfoの変数jigyoName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_KUBUN</td><td>第二引数addInfoの変数jigyoKubun</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHINSA_KUBUN</td><td>第二引数addInfoの変数shinsaKubun</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TANTOKA_NAME</td><td>第二引数addInfoの変数tantokaName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TANTOKAKARI</td><td>第二引数addInfoの変数tantoKakari</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TOIAWASE_NAME</td><td>第二引数addInfoの変数toiawaseName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TOIAWASE_TEL</td><td>第二引数addInfoの変数toiawaseTel</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TOIAWASE_EMAIL</td><td>第二引数addInfoの変数toiawaseEmail</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>UKETUKEKIKAN_START</td><td>第二引数addInfoの変数uketukekikanStart</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>UKETUKEKIKAN_END</td><td>第二引数addInfoの変数uketukekikanEnd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHINSAKIGEN</td><td>第二引数addInfoの変数shinsaKigen</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TENPU_NAME</td><td>第二引数addInfoの変数tenpuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TENPU_WIN</td><td>第二引数addInfoの変数tenpuWin</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TENPU_MAC</td><td>第二引数addInfoの変数tenpuMac</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>HYOKA_FILE_FLG</td><td>第二引数addInfoの変数hyokaFileFlg</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>HYOKA_FILE</td><td>第二引数addInfoの変数hyokaFile</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KOKAI_FLG</td><td>第二引数addInfoの変数kokaiFlg</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KESSAI_NO</td><td>第二引数addInfoの変数kessaiNo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KOKAI_ID</td><td>第二引数addInfoの変数kokaiID</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>HOKAN_DATE</td><td>第二引数addInfoの変数hokanDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>YUKO_DATE</td><td>第二引数addInfoの変数YukoDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BIKO</td><td>第二引数addInfoの変数Biko</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>DEL_FLG</td><td>0　(通常)</td></tr>
	 * </table><br/><br/>
	 * 
	 * <b>5.登録データの取得</b><br/>
	 * 　検索結果が0件のとき、例外をthrowする。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- 事業ID
	 *     ,A.NENDO					-- 年度
	 *     ,A.KAISU					-- 回数
	 *     ,A.JIGYO_NAME			-- 事業名
	 *     ,A.JIGYO_KUBUN			-- 事業区分
	 *     ,A.SHINSA_KUBUN			-- 審査区分
	 *     ,A.TANTOKA_NAME			-- 業務担当課
	 *     ,A.TANTOKAKARI			-- 業務担当係名
	 *     ,A.TOIAWASE_NAME			-- 問い合わせ先担当者名
	 *     ,A.TOIAWASE_TEL			-- 問い合わせ先電話番号
	 *     ,A.TOIAWASE_EMAIL		-- 問い合わせ先E-mail
	 *     ,A.UKETUKEKIKAN_START	-- 学振受付期間（開始）
	 *     ,A.UKETUKEKIKAN_END		-- 学振受付期間（終了）
	 *     ,A.SHINSAKIGEN			-- 審査期限
	 *     ,A.TENPU_NAME			-- 添付文書名
	 *     ,A.TENPU_WIN				-- 添付ファイル格納フォルダ（Win）
	 *     ,A.TENPU_MAC				-- 添付ファイル格納フォルダ（Mac）
	 *     ,A.HYOKA_FILE_FLG		-- 評価用ファイル有無
	 *     ,A.HYOKA_FILE			-- 評価用ファイル格納フォルダ
	 *     ,A.KOKAI_FLG				-- 公開フラグ
	 *     ,A.KESSAI_NO				-- 公開決裁番号
	 *     ,A.KOKAI_ID				-- 公開確定者ID
	 *     ,A.HOKAN_DATE			-- データ保管日
	 *     ,A.YUKO_DATE				-- 保管有効期限
	 *     ,A.BIKO				-- 備考
	 *     ,A.DEL_FLG				-- 削除フラグ
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
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数addInfoの変数jigyoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo	UserInfo
	 * @param addInfo	登録情報(JigyoKanriInfo)
	 * @return 事業情報
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuInfo)
	 */
	public JigyoKanriInfo insert(UserInfo userInfo, JigyoKanriInfo addInfo)
		throws ApplicationException {
			
		boolean success = false;
		
		
		//---------------------------------------
		//キー情報の（事業ID）を作成
		//西暦年度（2桁）+事業CD（5桁）+回数（1桁）
		//---------------------------------------
		String jigyoId = null;
		//----------年度を西暦年度（2桁）に変換
		String nendo = addInfo.getNendo();
		
		try{
			nendo = DateUtil.changeWareki2Seireki(nendo);
			//----------事業IDを作成
			jigyoId = nendo + addInfo.getJigyoCd() + addInfo.getKaisu();
			addInfo.setJigyoId(jigyoId);
		}catch(Exception e){
			throw new ApplicationException(
				"事業IDを作成中にエラーが発生しました。",
				new ErrorInfo("errors.4001"),
				e);			
		}

		Connection connection = null;
		try {	
			connection = DatabaseUtil.getConnection();
			//--------アップロードファイルの書き込み
			//---添付ファイルフォルダ（Win）
			FileResource tenpuWinFileRes = addInfo.getTenpuWinFileRes();	
			if(tenpuWinFileRes != null){
				//拡張子の取得
				String extension = FileUtil.getExtention(tenpuWinFileRes.getName());
				//ファイルパスを生成
				String[] pathInfo = new String[]{jigyoId, extension};
				String   tenpuWinPath  = MessageFormat.format(SHINSEI_TENPUWIN_FOLDER, pathInfo);
				File     tenpuWinFile   = new File(tenpuWinPath);
				//ファイルの書き込み
				writeFile(tenpuWinFile, tenpuWinFileRes, true);
				//ファイルパスをセット
				addInfo.setTenpuWin(tenpuWinPath);
				//添付ファイル名をセット
				addInfo.setTenpuName(tenpuWinFile.getName());				
			}
			
			//---添付ファイルフォルダ（Mac）				
			FileResource tenpuMacFileRes = addInfo.getTenpuMacFileRes();	
			if(tenpuMacFileRes != null){
				//拡張子の取得
				String extension = FileUtil.getExtention(tenpuMacFileRes.getName());
				//ファイルパスを生成
				String[] pathInfo = new String[]{jigyoId, extension};
				String   tenpuMacPath  = MessageFormat.format(SHINSEI_TENPUMAC_FOLDER, pathInfo);
				File     tenpuMacFile   = new File(tenpuMacPath);	
				//ファイルの書き込み
				writeFile(tenpuMacFile, tenpuMacFileRes, true);
				//ファイルパスをセット
				addInfo.setTenpuMac(tenpuMacPath);
				//添付ファイル名をセット
				addInfo.setTenpuName(tenpuMacFile.getName());				
			}
			
			//---評価用ファイルフォルダ			
			FileResource hyokaFileRes = addInfo.getHyokaFileRes();	
			if(hyokaFileRes != null){
				//拡張子の取得
				String extension = FileUtil.getExtention(hyokaFileRes.getName());
				//ファイルパスを生成
				String[] pathInfo = new String[]{jigyoId, extension};
				String   hyokaPath  = MessageFormat.format(SHINSEI_HYOKA_FOLDER, pathInfo);
				File     hyokaFile   = new File(hyokaPath);			
				//ファイルの書き込み
				writeFile(hyokaFile, hyokaFileRes, true);
				//ファイルパスをセット
				addInfo.setHyokaFile(hyokaPath);
			}
			
			
			//---------------------------------------
			//事業マスタ情報より必要情報の取得（事業区分、審査区分）
			//---------------------------------------
			Map masterInfo = MasterJigyoInfoDao.selectRecord(connection, addInfo.getJigyoCd());
			addInfo.setJigyoKubun(StringUtil.defaultString(masterInfo.get("JIGYO_KUBUN"),null));
			addInfo.setShinsaKubun(StringUtil.defaultString(masterInfo.get("SHINSA_KUBUN"),null));
			
			//---------------------------------------
			//事業管理情報の追加
			//---------------------------------------
			JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			dao.insertJigyoKanriInfo(connection,addInfo);		

			//---------------------------------------
			//登録データの取得
			//---------------------------------------
			JigyoKanriInfo result = dao.selectJigyoKanriInfo(connection, addInfo);

			//---------------------------------------
			//SEQの登録
			//---------------------------------------
			//科研では使用しない。
			//dao.createSEQ(connection, jigyoId);	

			//---------------------------------------
			//登録正常終了
			//---------------------------------------
			success = true;			
			return result;
	
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"事業管理データ登録中にDBエラーが発生しました。",
				new ErrorInfo("errors.4001"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"事業管理データ登録中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	/**
	 * 事業情報の更新.<br><br>
	 * 
	 * 事業情報を更新する。<br><br>
	 * 
	 * <b>1.アップロードファイルの書き込み</b><br/>
	 * (1)FileUtilクラスのgetExtention()メソッドにて、添付ファイルの拡張子を取得する。<br/><br/>
	 * 
	 * <b>2.アップロードファイルの書き込み</b><br>
	 * 　Win申請ファイル、Mac申請ファイル、評価ファイルについてそれぞれ処理を行う。<br><br>
	 * 
	 * (1)FileUtilクラスのgetExtention()メソッドで、ファイルの拡張子を取得する。<br><br>
	 * 
	 * (2)ファイルパスを生成する。<br>
	 * 　Win申請ファイル－変数SHINSEI_TENPUWIN_FOLDER<br>
	 * 　Mac申請ファイル－変数SHINSEI_TENPUMAC_FOLDER<br>
	 * 　評価ファイル－変数SHINSEI_HYOKA_FOLDER<br><br>
	 * 
	 * 　配列pathInfoに、1.で作成した事業ID、(1)で取得したファイル拡張子を格納する。<br/><br/>
	 * 
	 * 　変数のパターンに従い、配列pathInfoをMessageFormatクラスのformat()メソッドを用いてフォーマットする。<br/>
	 * 　生成したファイルパスで、ファイルを生成する。<br/><br/>
	 * 　　(例)Win申請ファイルパターン：${shinsei_path}/data/{0}/tenpufile/win/{0}.{1}<br/>
	 * 　　　　申請ファイル拡張子：doc　　事業ID：04000011　のとき<br>
	 * 　　　　ファイルパス：${shinsei_path}/data/04000011/tenpufile/win/04000011.doc<br>
	 * 　　　　　※${shinsei_path}の値はApplicationSettings.propertiesに設定<br><br>
	 * 
	 * (3)ファイルを書き込む。<br>
	 * 　　自クラスのwriteFile()メソッドを呼び、ファイルを書き込む。<br><br>
	 * 
	 * <b>2.事業情報の更新</b><br/>
	 * 　事業情報管理テーブルを更新する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 *     JIGYOKANRI A
	 * SET
	 *     A.JIGYO_ID = ?			-- 事業ID
	 *     ,A.NENDO = ?				-- 年度
	 *     ,A.KAISU = ?				-- 回数
	 *     ,A.JIGYO_NAME = ?		-- 事業名
	 *     ,A.JIGYO_KUBUN = ?		-- 事業区分
	 *     ,A.SHINSA_KUBUN = ?		-- 審査区分
	 *     ,A.TANTOKA_NAME = ?		-- 業務担当課
	 *     ,A.TANTOKAKARI = ?		-- 業務担当係名
	 *     ,A.TOIAWASE_NAME = ?		-- 問い合わせ先担当者名
	 *     ,A.TOIAWASE_TEL = ?		-- 問い合わせ先電話番号
	 *     ,A.TOIAWASE_EMAIL = ?	-- 問い合わせ先E-mail
	 *     ,A.UKETUKEKIKAN_START = ?	-- 学振受付期間（開始）
	 *     ,A.UKETUKEKIKAN_END = ?		-- 学振受付期間（終了）
	 *     ,A.SHINSAKIGEN = ?		-- 審査期限
	 *     ,A.TENPU_NAME = ?		-- 添付文書名
	 *     ,A.TENPU_WIN = ?			-- 添付ファイル格納フォルダ（Win）
	 *     ,A.TENPU_MAC = ?			-- 添付ファイル格納フォルダ（Mac）
	 *     ,A.HYOKA_FILE_FLG = ?	-- 評価用ファイル有無
	 *     ,A.HYOKA_FILE = ?		-- 評価用ファイル格納フォルダ
	 *     ,A.KOKAI_FLG = ?			-- 公開フラグ
	 *     ,A.KESSAI_NO = ?			-- 公開決裁番号
	 *     ,A.KOKAI_ID = ?			-- 公開確定者ID
	 *     ,A.HOKAN_DATE = ?		-- データ保管日
	 *     ,A.YUKO_DATE = ?			-- 保管有効期限
	 *     ,A.BIKO = ?				-- 備考
	 *     ,A.DEL_FLG = ?			-- 削除フラグ
	 *  WHERE
	 *     JIGYO_ID = ?				-- 事業ID
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数addInfoの変数jigyoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>NENDO</td><td>第二引数addInfoの変数nendo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KAISU</td><td>第二引数addInfoの変数kaisu</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_NAME</td><td>第二引数addInfoの変数jigyoName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_KUBUN</td><td>第二引数addInfoの変数jigyoKubun</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHINSA_KUBUN</td><td>第二引数addInfoの変数shinsaKubun</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TANTOKA_NAME</td><td>第二引数addInfoの変数tantokaName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TANTOKAKARI</td><td>第二引数addInfoの変数tantoKakari</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TOIAWASE_NAME</td><td>第二引数addInfoの変数toiawaseName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TOIAWASE_TEL</td><td>第二引数addInfoの変数toiawaseTel</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TOIAWASE_EMAIL</td><td>第二引数addInfoの変数toiawaseEmail</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>UKETUKEKIKAN_START</td><td>第二引数addInfoの変数uketukekikanStart</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>UKETUKEKIKAN_END</td><td>第二引数addInfoの変数uketukekikanEnd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHINSAKIGEN</td><td>第二引数addInfoの変数shinsaKigen</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TENPU_NAME</td><td>第二引数addInfoの変数tenpuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TENPU_WIN</td><td>第二引数addInfoの変数tenpuWin</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TENPU_MAC</td><td>第二引数addInfoの変数tenpuMac</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>HYOKA_FILE_FLG</td><td>第二引数addInfoの変数hyokaFileFlg</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>HYOKA_FILE</td><td>第二引数addInfoの変数hyokaFile</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KOKAI_FLG</td><td>第二引数addInfoの変数kokaiFlg</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KESSAI_NO</td><td>第二引数addInfoの変数kessaiNo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KOKAI_ID</td><td>第二引数addInfoの変数kokaiID</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>HOKAN_DATE</td><td>第二引数addInfoの変数hokanDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>YUKO_DATE</td><td>第二引数addInfoの変数YukoDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BIKO</td><td>第二引数addInfoの変数Biko</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>DEL_FLG</td><td>0　(通常)</td></tr>
	 * </table><br/>
	 * 
	 * 条件文 バインド変数
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数addInfoの変数jigyoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param updateInfo	更新情報(JigyokKanriInfo)
	 * @return なし
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.JigyoKanriInfo)
	 */
	public void update(UserInfo userInfo, JigyoKanriInfo updateInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();			
			//-----アップロードファイルの書き込み
			String jigyoId = updateInfo.getJigyoId();
			
			// 2005/04/24 追加 ここから ----------------------------------------
			// 理由 更新時、ファイル削除のため
			
// 2005/04/25 修正 ファイル自体の削除は不要
			//Winファイル削除
			if(updateInfo.isDelWinFileFlg()){
//				String path = updateInfo.getTenpuWin();
//				File winFile = new File(path);
//				FileUtil.delete(winFile);
				updateInfo.setTenpuWin(null);
			}
			//Macファイル削除
			if(updateInfo.isDelMacFileFlg()){
//				String path = updateInfo.getTenpuMac();
//				File macFile = new File(path);
//				FileUtil.delete(macFile);
				updateInfo.setTenpuMac(null);
			}
			
			//Winファイル・Macファイルともに空のとき、添付文書名を空にする
			if(updateInfo.getTenpuWin() == null && updateInfo.getTenpuMac() == null){
				updateInfo.setTenpuName(null);
			}
			
			// 追加 ここから ----------------------------------------
			
			//---添付ファイルフォルダ（Win）
			FileResource tenpuWinFileRes = updateInfo.getTenpuWinFileRes();	
			if(tenpuWinFileRes != null){
				//拡張子の取得
				String extension = FileUtil.getExtention(tenpuWinFileRes.getName());
				//ファイルパスを生成
				String[] pathInfo = new String[]{jigyoId, extension};
				String   tenpuWinPath  = MessageFormat.format(SHINSEI_TENPUWIN_FOLDER, pathInfo);
				File     tenpuWinFile   = new File(tenpuWinPath);			
				//ファイルの書き込み
				writeFile(tenpuWinFile, tenpuWinFileRes, true);
				//ファイルパスをセット
				updateInfo.setTenpuWin(tenpuWinPath);
				//-------添付ファイル名をセット
				updateInfo.setTenpuName(tenpuWinFile.getName());				
			}
			
			//---添付ファイルフォルダ（Mac）				
			FileResource tenpuMacFileRes = updateInfo.getTenpuMacFileRes();	
			if(tenpuMacFileRes != null){
				//拡張子の取得
				String extension = FileUtil.getExtention(tenpuMacFileRes.getName());
				//ファイルパスを生成
				String[] pathInfo = new String[]{jigyoId, extension};
				String   tenpuMacPath  = MessageFormat.format(SHINSEI_TENPUMAC_FOLDER, pathInfo);
				File     tenpuMacFile   = new File(tenpuMacPath);			
				//ファイルの書き込み
				writeFile(tenpuMacFile, tenpuMacFileRes, true);
				//ファイルパスをセット
				updateInfo.setTenpuMac(tenpuMacPath);
				//-------添付ファイル名をセット
				updateInfo.setTenpuName(tenpuMacFile.getName());				
			}
			
			//---評価用ファイルフォルダ			
			FileResource hyokaFileRes = updateInfo.getHyokaFileRes();	
			if(hyokaFileRes != null){
				//拡張子の取得
				String extension = FileUtil.getExtention(hyokaFileRes.getName());
				//ファイルパスを生成
				String[] pathInfo = new String[]{jigyoId, extension};
				String   hyokaPath  = MessageFormat.format(SHINSEI_HYOKA_FOLDER, pathInfo);
				File     hyokaFile   = new File(hyokaPath);			
				//ファイルの書き込み
				writeFile(hyokaFile, hyokaFileRes, true);
				//ファイルパスをセット
				updateInfo.setHyokaFile(hyokaPath);
			}
	
			//---------------------------------------
			//事業管理情報の更新
			//---------------------------------------
			JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			dao.updateJigyoKanriInfo(connection, updateInfo);
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;	
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"事業管理データ更新中にDBエラーが発生しました。",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"事業管理データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	/**
	 * 事業情報の公開情報の更新.<br/><br/>
	 * 
	 * 複数の事業情報について、公開確定情報を更新する。<br/><br/>
	 * 
	 * <b>1.パスワード確認</b><br/><br/>
	 * 第一引数userInfoから、業務担当者情報のパスワードを取得する。<br/>
	 * 第四引数passwordと比較する。<br/>
	 * 　パスワードが一致しないとき、例外をthrowする。<br/>
	 * 　パスワードが一致したとき、更新処理を行う。<br/><br/>
	 * 
	 * <b>2.事業情報の公開確定情報を更新</b><br/>
	 * 　1.でパスワードが一致したとき、事業情報管理テーブルを更新する。<br/><br/>
	 * 　該当レコードの公開フラグを1にする。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 *     JIGYOKANRI
	 * SET
	 *     KOKAI_FLG = ?	-- 公開フラグ　(1：公開 0：非公開(規定値))
	 *     ,KESSAI_NO = ?	-- 公開決裁番号
	 *     ,KOKAI_ID = ?	-- 公開確定者ID	
	 * WHERE
	 *     JIGYO_ID IN(	
	 *         ?		-- 事業ID　第二引数jigyoPksのlengthだけ繰り返し
	 *     )
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KOKAI_FLG</td><td>1　(公開)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KESSAI_NO</td><td>第三引数kessaiNo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KOKAI_ID</td><td>第一引数userInfoの変数gyomutantoId</td></tr>
	 * </table><br/>
	 * 
	 * 条件文 バインド変数
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数jigyoPksの変数jigyoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo	UserInfo
	 * @param jigyoPks	事業ID配列(JigyoKanriPk[])
	 * @param kessaiNo	公開決裁番号
	 * @param password	パスワード
	 * @return なし
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#updateKokaiKakutei(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.JigyoKanriInfo, java.lang.String)
	 */
	public void updateKokaiKakutei(UserInfo userInfo, JigyoKanriPk[] jigyoPks, String kessaiNo, String password) throws ApplicationException {
		
		//パスワードが正しいかどうかを確認
		String dbpassword = userInfo.getGyomutantoInfo().getPassword();
		
		if(!password.equals(dbpassword)){
			throw new ApplicationException(
				"パスワードが間違っています。",
				new ErrorInfo("errors.2001", new String[]{"パスワード"}) );
		}
		
		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//事業管理情報の更新
			//---------------------------------------
			JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			dao.updateKokaiKakutei(connection, jigyoPks, kessaiNo, userInfo.getGyomutantoInfo().getGyomutantoId());
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;	
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"事業管理データ（公開確定）更新中にDBエラーが発生しました。",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"事業管理データ（公開確定）更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}		
	}
	
	/**
	 * 事業情報の削除.<br><br>
	 * 
	 * 事業情報を論理削除する。<br><br>
	 * 
	 * 以下のSQLを実行し、事業情報管理テーブル[JIGYOKANRI]から対象レコードを論理削除する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 *     JIGYOKANRI
	 * SET
	 *     DEL_FLG = 1		-- 削除フラグ (1：削除　0：通常(規定値))
	 * WHERE
	 *     JIGYO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数deleteInfoの変数jigyoId</td></tr>
	 * </table><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param deleteInfo	削除情報(JigyoKanriInfo)
	 * @return なし
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.JigyoKanriInfo)
	 */
	public void delete(UserInfo userInfo, JigyoKanriInfo deleteInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();			
			//---------------------------------------
			//事業管理情報の削除
			//---------------------------------------
			JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			dao.deleteFlgJigyoKanriInfo(connection, deleteInfo);
			
			//---------------------------------------
			//削除正常終了
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"事業管理データ削除中にDBエラーが発生しました。",
				new ErrorInfo("errors.4003"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"事業管理データ削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
		//---------------------------------------
		//書類管理情報の削除
		//---------------------------------------
		ShoruiKanriPk shoruiPk = new ShoruiKanriPk();
		shoruiPk.setJigyoId(deleteInfo.getJigyoId());
		try{
			delete(userInfo, shoruiPk);
		}catch(NoDataFoundException e){
			//何も処理しない
		}
		
	}
	
	/**
	 * 事業情報の取得.<br><br>
	 * 
	 * １件の事業情報を取得する。<br><br>
	 * 
	 * <b>1.事業情報の取得</b><br/>
	 * 事業情報管理テーブルを検索し、該当事業情報をinfoにセットする。<br/><br/>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- 事業ID
	 *     ,A.NENDO					-- 年度
	 *     ,A.KAISU					-- 回数
	 *     ,A.JIGYO_NAME			-- 事業名
	 *     ,A.JIGYO_KUBUN			-- 事業区分
	 *     ,A.SHINSA_KUBUN			-- 審査区分
	 *     ,A.TANTOKA_NAME			-- 業務担当課
	 *     ,A.TANTOKAKARI			-- 業務担当係名
	 *     ,A.TOIAWASE_NAME			-- 問い合わせ先担当者名
	 *     ,A.TOIAWASE_TEL			-- 問い合わせ先電話番号
	 *     ,A.TOIAWASE_EMAIL		-- 問い合わせ先E-mail
	 *     ,A.UKETUKEKIKAN_START	-- 学振受付期間（開始）
	 *     ,A.UKETUKEKIKAN_END		-- 学振受付期間（終了）
	 *     ,A.SHINSAKIGEN			-- 審査期限
	 *     ,A.TENPU_NAME			-- 添付文書名
	 *     ,A.TENPU_WIN				-- 添付ファイル格納フォルダ（Win）
	 *     ,A.TENPU_MAC				-- 添付ファイル格納フォルダ（Mac）
	 *     ,A.HYOKA_FILE_FLG		-- 評価用ファイル有無
	 *     ,A.HYOKA_FILE			-- 評価用ファイル格納フォルダ
	 *     ,A.KOKAI_FLG				-- 公開フラグ
	 *     ,A.KESSAI_NO				-- 公開決裁番号
	 *     ,A.KOKAI_ID				-- 公開確定者ID
	 *     ,A.HOKAN_DATE			-- データ保管日
	 *     ,A.YUKO_DATE				-- 保管有効期限
	 *     ,A.BIKO				-- 備考
	 *     ,A.DEL_FLG				-- 削除フラグ
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
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数addInfoの変数jigyoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * <b>2.ファイル名のセット</b><br/>
	 * FileUtilクラスのgetFileName()メソッド呼び出し。<br/>
	 * getFileName()メソッドで、パスの区切り文字'\'を'/'に置換、ファイルパスからファイル名を取り出した文字列を返却。<br/>
	 * 返却値をinfoの変数hyokaNameにセットする。<br/><br/>
	 * 
	 * <b>3.info返却</b><br/>
	 * infoを返却する。
	 * 
	 * @param userInfo	UserInfo
	 * @param pkInfo	検索する事業情報のPK情報(JigyoKanriPk)
	 * @return 事業情報
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.JigyoKanriInfo)
	 */
	public JigyoKanriInfo select(UserInfo userInfo, JigyoKanriPk pkInfo)
		throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			JigyoKanriInfo info = dao.selectJigyoKanriInfo(connection, pkInfo);
			info.setHyokaName(FileUtil.getFileName(info.getHyokaFile()));	//ファイル名を抽出してセットする
			return info;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"所属機関管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	
	//ADD START 2007/07/23 BIS 趙一非
	public RyoikiKeikakushoInfo ryoikiKeikakushoInfo(UserInfo userInfo,RyoikiKeikakushoInfo ryoikiKeikakushoInfo) throws ApplicationException
	{
		
		RyoikiKeikakushoInfo info =new RyoikiKeikakushoInfo();
		List errors=new ArrayList();
		if(null!=ryoikiKeikakushoInfo.getZennendoOuboNo()&&!"".equals(ryoikiKeikakushoInfo.getZennendoOuboNo().trim()))
		{
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			MasterRyouikiInfoDao masterRyoikidao = new MasterRyouikiInfoDao(userInfo);
			//JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			//RyoikiKeikakushoInfo info = dao.selectRyoikiKeikakushoInfo(connection, zennendoOuboNo);
			if(!checkAndSetRyoiki(connection,userInfo,ryoikiKeikakushoInfo,errors)){
				
		        throw new DataAccessException();
			}
//			領域情報をセット
	        Map ryoikiMap = new HashMap();
			info.setZennendoOuboFlg(ryoikiKeikakushoInfo.getZennendoOuboFlg());
			info.setZennendoOuboNo(ryoikiKeikakushoInfo.getZennendoOuboNo());
			info.setZennendoOuboRyoikiRyaku(ryoikiKeikakushoInfo.getZennendoOuboRyoikiRyaku());
			info.setZennendoOuboSettei(ryoikiKeikakushoInfo.getZennendoOuboSettei());
			return info;
		} catch (DataAccessException e) {
			String msg = "最終年度前年度にあたる領域番号";
	        String property = "ryoikikeikakushoInfo.zennendoOuboNo";
			
			throw new ApplicationException(
				"仮領域番号発行情報登録検索中にDBエラーが発生しました。",
				(ErrorInfo)errors.get(0),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		}
		else
		{
			RyoikiKeikakushoInfo result=new RyoikiKeikakushoInfo();
			result.setZennendoOuboNo("");
			result.setZennendoOuboRyoikiRyaku("");
			result.setZennendoOuboSettei("");
			result.setZennendoOuboFlg(ryoikiKeikakushoInfo.getZennendoOuboFlg());
			return result;
		}
		
	}
	/**
     * 研究領域について<br><br>
     * 
     * @param connection        Connection
     * @param userInfo          UserInfo
     * @param shinseiDataInfo   shinseiDataInfo
     * @return [true]研究領域情報のセット完了
     *         [false]領域コードが指定されていない/領域コードが領域マスタテーブルに存在しない
     * 
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    private boolean checkAndSetRyoiki(Connection connection, UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo, List errors) throws NoDataFoundException,
            ApplicationException {
        
        // 領域番号の取得
        String ryoikiNo = ryoikikeikakushoInfo.getZennendoOuboNo();
        
        if(StringUtil.isBlank(ryoikiNo)){
            //コードが空の場合は初期化する
            ryoikikeikakushoInfo.setZennendoOuboRyoikiRyaku(null);
            ryoikikeikakushoInfo.setZennendoOuboSettei(null);
            return false;
        }
        
        //領域マスタDaoの生成
        MasterRyouikiInfoDao masterRyoikidao = new MasterRyouikiInfoDao(userInfo);
        
        String msg = "最終年度前年度にあたる領域番号";
        String property = "ryoikikeikakushoInfo.zennendoOuboNo";
        
        try{
            if("0".equals(masterRyoikidao.selectRyoikiNoCount(connection, ryoikiNo))){
                errors.add(new ErrorInfo("errors.2001", new String[]{msg}, property));
                return false;
            }
        }catch(DataAccessException ex){
            throw new ApplicationException("最終年度前年度にあたる領域番号の検索中にエラーが発生しました。",new ErrorInfo("errors.4004"));
        }catch(NoDataFoundException ex){
            errors.add(new ErrorInfo("errors.2001", new String[]{msg}, property));
        }
        
        //領域情報をセット
        Map ryoikiMap = new HashMap();
        
        try{
            ryoikiMap = MasterRyouikiInfoDao.selectRecord(connection, ryoikiNo);
        }catch(DataAccessException ex){
            throw new ApplicationException("領域情報検索中にDBエラーが発生しました。",new ErrorInfo("errors.4004"));
        }catch(NoDataFoundException ex){
            throw new NoDataFoundException("該当する情報が存在しません。", new ErrorInfo("errors.5002"), ex);
        }
        
        ryoikikeikakushoInfo.setZennendoOuboRyoikiRyaku((String)ryoikiMap.get("RYOIKI_RYAKU"));
        ryoikikeikakushoInfo.setZennendoOuboSettei((String)ryoikiMap.get("SETTEI_KIKAN"));
    
        return true;
    }
	//	ADD END 2007/07/23 BIS 趙一非
    
	/**
	 * 事業情報ページ情報の取得.<br><br>
	 * 
	 * すべての事業情報のページ情報を取得する。<br><br>
	 * 
	 * 第二引数で渡された検索条件に基づき、事業情報管理テーブルを検索する。<br/><br/>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）<br/>
	 * 検索結果をPageオブジェクトに格納する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- 事業ID
	 *     ,A.NENDO					-- 年度
	 *     ,A.KAISU					-- 回数
	 *     ,A.JIGYO_NAME			-- 事業名
	 *     ,A.JIGYO_KUBUN			-- 事業区分
	 *     ,A.SHINSA_KUBUN			-- 審査区分
	 *     ,A.TANTOKA_NAME			-- 業務担当課
	 *     ,A.TANTOKAKARI			-- 業務担当係名
	 *     ,A.TOIAWASE_NAME			-- 問い合わせ先担当者名
	 *     ,A.TOIAWASE_TEL			-- 問い合わせ先電話番号
	 *     ,A.TOIAWASE_EMAIL		-- 問い合わせ先E-mail
	 *     ,A.UKETUKEKIKAN_START	-- 学振受付期間（開始）
	 *     ,A.UKETUKEKIKAN_END		-- 学振受付期間（終了）
	 *     ,A.SHINSAKIGEN			-- 審査期限
	 *     ,A.TENPU_NAME			-- 添付文書名
	 *     ,A.TENPU_WIN				-- 添付ファイル格納フォルダ（Win）
	 *     ,A.TENPU_MAC				-- 添付ファイル格納フォルダ（Mac）
	 *     ,A.HYOKA_FILE_FLG		-- 評価用ファイル有無
	 *     ,A.HYOKA_FILE			-- 評価用ファイル格納フォルダ
	 *     ,A.KOKAI_FLG				-- 公開フラグ
	 *     ,A.KESSAI_NO				-- 公開決裁番号
	 *     ,A.KOKAI_ID				-- 公開確定者ID
	 *     ,A.HOKAN_DATE			-- データ保管日
	 *     ,A.YUKO_DATE				-- 保管有効期限
	 *     ,A.BIKO				-- 備考
	 *     ,A.DEL_FLG				-- 削除フラグ
	 * FROM
	 *     JIGYOKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 * 
	 * ORDER BY JIGYO_ID
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	検索条件(SearchInfo)
	 * @return 事業情報を格納したPageオブジェクト
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuSearchInfo)
	 */
	public Page search(UserInfo userInfo, SearchInfo searchInfo)
		throws ApplicationException {

		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		String select = 
			"SELECT *  FROM JIGYOKANRI A WHERE DEL_FLG = 0";
			
		StringBuffer query = new StringBuffer(select);
		
		//ソート順（事業IDの昇順）
		query.append(" ORDER BY JIGYO_ID");		
		
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
			return SelectUtil.selectPageInfo(connection, searchInfo, query.toString());
		} catch (DataAccessException e) {
			log.error("事業管理データ検索中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"事業管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);

		} finally {
			DatabaseUtil.closeConnection(connection);
		}

	}
	
	
	/**
	 * 事業情報の検索.<br><br>
	 * 
	 * 事業情報を検索する。<br><br>
	 * 
	 * 業務担当者以外は、selectJigyoInfoList(Connection connection) と
	 * 同じ結果が返る。
	 * <li>業務担当者･･････自分が担当する事業区分に該当する事業リスト</li>
	 * <li>業務担当者以外･･･全部</li><br/><br/>
	 * 
	 * <b>1.検索条件を指定</b><br/>
	 * ユーザが業務担当者のとき、担当区分を指定する。<br/>
	 * StringUtilクラスのchangeIterator2CSV()メソッドで、文字列反復子をCSV形式で返す。
	 * 返却値を変数tantoJigyoKubunに格納。<br><br>
	 * 
	 * <b>2.事業情報を検索</b><br/>
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- 事業ID
	 *     ,A.NENDO"				-- 年度
	 *     ,A.KAISU					-- 回数
	 *     ,A.JIGYO_NAME			-- 事業名
	 *     ,A.JIGYO_KUBUN			-- 事業区分
	 *     ,A.SHINSA_KUBUN			-- 審査区分
	 *     ,A.TANTOKA_NAME			-- 業務担当課
	 *     ,A.TANTOKAKARI			-- 業務担当係名
	 *     ,A.TOIAWASE_NAME			-- 問い合わせ先担当者名
	 *     ,A.TOIAWASE_TEL			-- 問い合わせ先電話番号
	 *     ,A.TOIAWASE_EMAIL		-- 問い合わせ先E-mail
	 *     ,A.UKETUKEKIKAN_START	-- 学振受付期間（開始）
	 *     ,A.UKETUKEKIKAN_END		-- 学振受付期間（終了）
	 *     ,A.SHINSAKIGEN			-- 審査期限
	 *     ,A.TENPU_NAME			-- 添付文書名
	 *     ,A.TENPU_WIN				-- 添付ファイル格納フォルダ（Win）
	 *     ,A.TENPU_MAC				-- 添付ファイル格納フォルダ（Mac）
	 *     ,A.HYOKA_FILE_FLG		-- 評価用ファイル有無
	 *     ,A.HYOKA_FILE			-- 評価用ファイル格納フォルダ
	 *     ,A.KOKAI_FLG				-- 公開フラグ
	 *     ,A.KESSAI_NO				-- 公開決裁番号
	 *     ,A.KOKAI_ID				-- 公開確定者ID
	 *     ,A.HOKAN_DATE			-- データ保管日
	 *     ,A.YUKO_DATE				-- 保管有効期限
	 *     ,A.BIKO			-- 備考
	 *     ,A.DEL_FLG				-- 削除フラグ
	 *     ,DECODE (
	 *         SIGN(TO_DATE(TO_CHAR(A.UKETUKEKIKAN_START, 'YYYY/MM/DD')
	 *              ,'YYYY/MM/DD') - TO_DATE(TO_CHAR(SYSDATE, 'YYYY/MM/DD'),'YYYY/MM/DD') )
	 *         ,0 , 'FALSE'		-- 現在時刻と同じ場合
	 *         ,1 , 'TRUE'		-- 現在時刻の方が学振受付期間（開始）より前
	 *         ,-1, 'FALSE'		-- 現在時刻の方が学振受付期間（開始）より後
	 *     ) DELETE_BUTTON_FLAG	-- 学振受付期間（開始）到達フラグ
	 * FROM
	 *     JIGYOKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 * 
	 * 		---動的検索条件1---
	 * 
	 * 		---動的検索条件2---
	 * 
	 * ORDER BY JIGYO_ID
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">動的検索条件1</span></b><br/>
	 * ユーザによって検索条件が動的に変化する。<br/>
	 * 業務担当者のとき、検索条件追加。<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>tantoJigyoKubun</td><td>AND A.JIGYO_KUBUN IN  ('事業区分1', '事業区分', …)</td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">動的検索条件2</span></b><br/>
	 * 第二引数の配列jigyoPksの値数によって、検索条件のパラメタ数が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業ID</td><td>jigyoID</td><td>AND JIGYO_ID IN ('事業ID1', '事業ID2', …)</td></tr>
	 * </table><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	検索条件(JigyoKanriSearchInfo)
	 * @return	事業情報を格納したPageオブジェクト
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo, JigyoKanriSearchInfo searchInfo)
		throws ApplicationException {

		//検索条件
		String addQuery = "";
		//業務担当者の場合は自分が担当する事業区分のみ。-> 基盤等が細分化されたため事業コードに変更
		if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
			//Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoKubun().iterator();
			//String tantoJigyoKubun = StringUtil.changeIterator2CSV(ite,true);
			//addQuery = new StringBuffer(" AND A.JIGYO_KUBUN IN (")
			//			 .append(tantoJigyoKubun)
			//			 .append(")")
			//			 .toString();
			Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
			String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
			addQuery = new StringBuffer(" AND SUBSTR(A.JIGYO_ID,3,5) IN (")
						 .append(tantoJigyoCd)
						 .append(")")
						 .toString();
		}

		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		String select = 		
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
				+ ",A.HYOKA_FILE_FLG"		//評価用ファイル有無
				+ ",A.HYOKA_FILE"			//評価用ファイル格納フォルダ
				+ ",A.KOKAI_FLG"			//公開フラグ
				+ ",A.KESSAI_NO"			//公開決裁番号
				+ ",A.KOKAI_ID"				//公開確定者ID
				+ ",A.HOKAN_DATE"			//データ保管日
				+ ",A.YUKO_DATE"			//保管有効期限
				+ ",A.BIKO"					//備考
				+ ",A.DEL_FLG"				//削除フラグ
				+ ",DECODE"
				+ " ("
				+ "  SIGN(TO_DATE(TO_CHAR(A.UKETUKEKIKAN_START, 'YYYY/MM/DD'),'YYYY/MM/DD') - TO_DATE(TO_CHAR(SYSDATE, 'YYYY/MM/DD'),'YYYY/MM/DD') )"
				+ "  ,0 , 'FALSE'"							//現在時刻と同じ場合
				+ "  ,1 , 'TRUE'"							//現在時刻の方が学振受付期間（開始）より前
				+ "  ,-1, 'FALSE'"							//現在時刻の方が学振受付期間（開始）より後
				+ " ) DELETE_BUTTON_FLAG"					//学振受付期間（開始）到達フラグ
				+ " FROM JIGYOKANRI A"
				+ " WHERE"
				+ "  DEL_FLG = 0 "
				+ addQuery
				;
			
		StringBuffer query = new StringBuffer(select);
		
		if(searchInfo.getJigyoPks() != null && searchInfo.getJigyoPks().length != 0){
			//事業IDをセット
			JigyoKanriPk[] jigyoPks = searchInfo.getJigyoPks();		
			query.append(" AND JIGYO_ID IN (");
			String aSeparate = "";
			for (int i = 0; i < jigyoPks.length; i++) {
				query.append(aSeparate);
				query.append(EscapeUtil.toSqlString(jigyoPks[i].getJigyoId()));
				aSeparate = ",";
			}
			query.append(")");								
		}
		//ソート順（事業IDの昇順）
		query.append(" ORDER BY JIGYO_ID");		
		
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
			return SelectUtil.selectPageInfo(connection, searchInfo, query.toString());
		} catch (DataAccessException e) {
			log.error("事業管理データ検索中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"事業管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);

		} finally {
			DatabaseUtil.closeConnection(connection);
		}

	}

	/**
	 * validateメソッド.<br><br>
	 * 
	 * <b>二重登録チェック</b><br>
	 * 取得件数が0でないとき、例外をthrowする。<br><br>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     COUNT(*)
	 * FROM
	 *     JIGYOKANRI
	 * WHERE
	 *     JIGYO_NAME = ?		-- 事業名
	 *     AND NENDO = ?"		-- 年度
	 *     AND KAISU = ?		-- 回数
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_NAME</td><td>第二引数insertOrUpdateInfoの変数jigyoName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>NENDO</td><td>第二引数insertOrUpdateInfoの変数nendo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KAISU</td><td>第二引数insertOrUpdateInfoの変数kaisu</td></tr>
	 * </table><br><br>
	 * 
	 * @param userInfo				UserInfo
	 * @param insertOrUpdateInfo	JigyoKanriInfo
	 * @return 事業情報
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.JigyoKanriInfo)
	 */
	public JigyoKanriInfo validate(UserInfo userInfo, JigyoKanriInfo insertOrUpdateInfo, String mode) throws ApplicationException, ValidationException {
		
		if(mode.equals(IMaintenanceName.ADD_MODE)){
			
			//-----------------------
			// 2重登録チェック
			//-----------------------
			//事業管理情報テーブルにすでに同じ「事業名+年度+回数」が登録されていないかどうかを確認
			JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			int count = 0;
			Connection connection = null;
			try {
				connection = DatabaseUtil.getConnection();
				//エラー情報保持用リスト
				List errors = new ArrayList();				
				count = dao.countJigyoKanriInfo(connection, insertOrUpdateInfo);
	
				//すでに登録されている場合
				if(count > 0){				
					errors.add(
						new ErrorInfo("errors.4007", new String[] {"事業"}));
					throw new ValidationException(
						"すでに事業が登録されています。検索キー：" +
						"事業名'"+ insertOrUpdateInfo.getJigyoName() + "'"
						+ "年度'"+ insertOrUpdateInfo.getNendo() + "'"
						+ "回数'"+ insertOrUpdateInfo.getKaisu() + "'",
						errors);
				}
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"事業管理データ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
			
//2004/08/30 takano 科研では以下の重複チェックは行わないため、コメントアウト。
//			//-----------------------
//			// 事業マスタ情報取得
//			//-----------------------
//			//事業マスタから事業コードをキーとして国名、対応機関名を取得
//			String select = 
//				"SELECT" +	" A.KUNI_NAME," 
//						  +	" A.TAIO_NAME " 
//						  +	" FROM MASTER_JIGYO A" ;
//			
//			StringBuffer query = new StringBuffer(select);
//			//事業コードをセット		
//			query.append(" WHERE JIGYO_CD = '" + insertOrUpdateInfo.getJigyoCd() + "'");				
//		
//			if(log.isDebugEnabled()){
//				log.debug("query:" + query);
//			}
//
//			//------------------------
//		
//			connection = null;
//			try {
//				connection = DatabaseUtil.getConnection();
//				List list = SelectUtil.select(connection, query.toString());
//				Map datas = (Map)list.get(0);
//				insertOrUpdateInfo.setKuniName((String)datas.get("KUNI_NAME"));//国名
//				insertOrUpdateInfo.setTaioName((String)datas.get("TAIO_NAME"));//対応機関名
//			} catch (NoDataFoundException e) {
//				throw new SystemException(
//					"事業マスタに該当するデータがありません。",
//					e);				
//			} catch (DataAccessException e) {
//				log.error("事業マスタデータ検索中にDBエラーが発生しました。", e);
//				throw new ApplicationException(
//					"事業マスタデータ検索中にDBエラーが発生しました。",
//					new ErrorInfo("errors.4004"),
//					e);
//
//			} finally {
//				DatabaseUtil.closeConnection(connection);
//			}

			
		}else if(mode.equals(IMaintenanceName.EDIT_MODE)){			
			//なにもしない
		}
		
		return insertOrUpdateInfo;
	}
	
	
	/**
	 * 書類情報の取得.<br><br>
	 * 
	 * 書類情報を取得する。<br><br>
	 * 
	 * 第二引数pkInfoに基づき、事業情報管理テーブル、書類管理テーブルを検索する。<br/>
	 * 検索処理は以下の通り。<br/><br/>
	 * 
	 * <b>1.書類管理情報セット</b><br/>
	 * searchInfoに、第二引数pkInfoの変数jigyoIdをセットする。<br><br>
	 * 自クラスのselect(UserInfo, JigyoKanriPk)メソッドを呼び、事業情報を取得する。<br/>
	 * 引数は、第一引数userInfoとsearchInfoを渡す。<br/><br/>
	 * searchInfoに取得した事業情報を格納する。<br/><br/>
	 * 
	 * shoruiInfoへ、searchInfoに格納された事業ID、事業名、年度、回数をセットする。<br/><br/>
	 * 
	 * <b>2.書類リスト取得</b><br/>
	 * shoruiSearchInfoに、searchInfoに格納された事業IDをセットする。<br/><br/>
	 * 
	 * 自クラスのsearch(UserInfo, ShoruiKanriSearchInfo)メソッドを呼び、書類情報を取得する。<br/>
	 * 引数は、第一引数userInfoとshoruiSearchInfoを渡す。<br/><br/>
	 * shoruiKanriListに、取得した書類情報を格納する。<br/><br/>
	 * 
	 * <b>3.取得情報の返却</b><br/>
	 * MapにshoruiInfo,shoruiKanriListを格納して返却する。
	 * 
	 * @param userInfo	UserInfo
	 * @param pkInfo	検索する書類情報のPK情報(ShoruiKanriPk)
	 * @return 書類情報Map
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriPk)
	 */
	public Map select(UserInfo userInfo, ShoruiKanriPk pkInfo) throws ApplicationException {
		
		//-----------------------
		// 事業管理情報取得
		//-----------------------
		JigyoKanriInfo searchInfo = new JigyoKanriInfo();
		searchInfo.setJigyoId(pkInfo.getJigyoId());
		searchInfo = select(userInfo, searchInfo);
		
		//書類管理情報に検索結果をセット
		ShoruiKanriInfo shoruiInfo = new ShoruiKanriInfo();
		shoruiInfo.setJigyoId(searchInfo.getJigyoId());//事業ID
		shoruiInfo.setJigyoName(searchInfo.getJigyoName());//事業名
		shoruiInfo.setNendo(searchInfo.getNendo());//年度	
		shoruiInfo.setKaisu(searchInfo.getKaisu());//回数
		
		//-----------------------
		// 書類リスト取得
		//-----------------------
		ShoruiKanriSearchInfo shoruiSearchInfo = new ShoruiKanriSearchInfo();
		shoruiSearchInfo.setJigyoId(searchInfo.getJigyoId());
		List shoruiKanriList = search(userInfo, shoruiSearchInfo);
		
		//事業管理情報リスト、書類管理情報リストをMapに格納して返す
		Map map = new HashMap();
		map.put(KEY_JIGYOKANRI_INFO, shoruiInfo);//事業管理情報
		map.put(KEY_SHORUIKANRI_LIST, shoruiKanriList);	//書類管理情報リスト
		
		return map;
	}
	
	/**
	 * 書類情報の登録.<br><br>
	 * 
	 * 書類情報を登録する。<br><br>
	 * 
	 * <b>1.システム番号の発行</b><br/>
	 * 自クラスのgetSystemNumber()メソッドを呼ぶ。<br/>
	 * 第二引数addInfoにセットする。<br/><br/>
	 * 
	 * <b>2.アップロードファイルの書き込み</b><br/>
	 * (1)FileUtilクラスのgetExtention()メソッドで、書類ファイルの拡張子を取得する。<br/><br/>
	 * 
	 * (2)ファイルパスを生成する。<br/>
	 * 　配列pathInfoに、addInfoから取得した事業ID、対象、システム番号、(1)で取得したファイル拡張子を格納する。<br/><br/>
	 * 　変数SHINSEI_SHORUI_FOLDERのパターンに従い、配列pathInfoをMessageFormatクラスのformatメソッドを用いてフォーマットする。<br/>
	 * 　生成したファイルパスで、ファイルを生成する。<br/><br/>
	 * 
	 * 　(例)書類ファイルパターン：${shinsei_path}/data/{0}/shorui/{1}/{2}.{3}<br/>
	 * 　　　申請ファイル拡張子：doc　　事業ID：04000011　　対象：2　　システム番号：20041122135506001　のとき<br>
	 * 　　　ファイルパス：${shinsei_path}/data/04000011/shorui/2/20041122135506001.doc<br>
	 * 　　　　※${shinsei_path}の値はApplicationSettings.propertiesに設定<br><br>
	 * 
	 * (3)ファイルを書き込む。<br/>
	 * 　自クラスのwriteFile()メソッドを呼び、ファイルを書き込む。<br/><br/>
	 * 
	 * <b>3.書類情報の追加</b><br/>
	 * 重複チェックし、データが存在しないとき登録処理を行う。<br/>
	 * データが存在する場合、例外をthrowする。<br/><br/>
	 * 
	 * (1)重複チェック<br/>
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID			-- 事業ID
	 *     ,A.TAISHO_ID			-- 対象
	 *     ,A.SYSTEM_NO			-- システム受付番号
	 *     ,A.SHORUI_FILE		-- 格納先ディレクトリ
	 *     ,A.SHORUI_NAME		-- 書類名
	 *     ,A.DEL_FLG			-- 削除フラグ
	 * FROM
	 *     SHORUIKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 *     AND JIGYO_ID = ?
	 *     AND SYSTEM_NO = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数addInfoの変数jigyoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SYSTEM_NO</td><td>1.で取得したシステム番号</td></tr>
	 * </table><br/><br/>
	 * 
	 * (2)書類情報の登録<br/>
	 * 　書類管理テーブルに登録する。<br/>
	 * 　(1)の結果、該当データが存在しないとき登録処理を行う。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * INSERT INTO SHORUIKANRI (
	 *     JIGYO_ID			-- 事業ID
	 *     ,TAISHO_ID		-- 対象
	 *     ,SYSTEM_NO		-- システム受付番号
	 *     ,SHORUI_FILE		-- 格納先ディレクトリ
	 *     ,SHORUI_NAME		-- 書類名
	 *     ,DEL_FLG)		-- 削除フラグ
	 * VALUES
	 *     (?,?,?,?,?,?)
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>第二引数addInfoの変数jigyoCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TAISHO_ID</td><td>第二引数addInfoの変数taishoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SYSTEM_NO</td><td>第二引数addInfoの変数systemNo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHORUI_FILE</td><td>第二引数addInfoの変数shoruiFile</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHORUI_NAME</td><td>第二引数addInfoの変数shoruiName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>DEL_FLG</td><td>0</td></tr>
	 * </table><br/><br/>
	 * 
	 * <b>4.書類リスト取得</b><br/>
	 * searchInfoにaddInfoの変数jigyoIdをセットする。<br><br>
	 * 自クラスのsearch(UserInfo, ShoruiKanriSearchInfo)メソッドを呼び、書類情報リストを取得する。<br>
	 * 引数は、第一引数userInfoとsearchInfoを渡す。
	 * 
	 * @param userInfo	UserInfo
	 * @param addInfo	登録情報(ShoruiKanriInfo)
	 * @return 書類情報リスト
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriInfo)
	 */
	public List insert(UserInfo userInfo, ShoruiKanriInfo addInfo) throws ApplicationException {
		
		boolean success = false;
		Connection connection = null;
		try {				
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//システム番号の発行
			//---------------------------------------
			addInfo.setSystemNo(getSystemNumber());
			String jigyoId = addInfo.getJigyoId();//事業ID	
			String taishoId = addInfo.getTaishoId();//対象
			String systemNo = addInfo.getSystemNo();//システム受付番号
							
			//-----アップロードファイルの書き込み
			//---書類ファイル
			FileResource shoruiFileRes = addInfo.getShoruiFileRes();	
			if(shoruiFileRes != null){
				//拡張子の取得
				String extension = FileUtil.getExtention(shoruiFileRes.getName());
				//ファイルパスを生成
				String[] pathInfo = new String[]{jigyoId, taishoId, systemNo, extension};
				String   shoruiPath  = MessageFormat.format(SHINSEI_SHORUI_FOLDER, pathInfo);
				File     shoruiFile   = new File(shoruiPath);			
				//ファイルの書き込み
				writeFile(shoruiFile, shoruiFileRes, false);
				//ファイルパスをセット
				addInfo.setShoruiFile(shoruiPath);
			}	
			
			//---------------------------------------
			//書類情報の追加
			//---------------------------------------
			ShoruiKanriInfoDao dao = new ShoruiKanriInfoDao(userInfo);
			dao.insertShoruiKanriInfo(connection,addInfo);		

			//---------------------------------------
			//登録正常終了
			//---------------------------------------
			success = true;
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"書類管理データ登録中にDBエラーが発生しました。",
				new ErrorInfo("errors.4001"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"書類管理データ登録中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
		
		//-----------------------
		// 書類リスト取得
		//-----------------------
		String jigyoId = addInfo.getJigyoId();
		//検索用キーの事業IDを再セット	
		ShoruiKanriSearchInfo searchInfo = new ShoruiKanriSearchInfo();
		searchInfo.setJigyoId(jigyoId);
		List shoruiKanriList = search(userInfo, searchInfo);

		return shoruiKanriList;
	}
	
	/**
	 * 書類情報の取得.<br><br>
	 * 
	 * 書類情報を取得する。<br><br>
	 * 
	 * <b>1.書類情報の取得</b><br>
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID, 			-- 事業ID
	 *     A.TAISHO_ID,			-- 対象
	 *     A.SYSTEM_NO,			-- システム受付番号
	 *     A.SHORUI_FILE, 			-- 格納先ディレクトリ
	 *     A.SHORUI_NAME,			-- 書類名
	 *     A.DEL_FLG			-- 削除フラグ
	 * FROM
	 *     SHORUIKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 * 
	 * 	--- 動的検索条件1 ---
	 * 
	 * ORDER BY SYSTEM_NO
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <b><span style="color:#002288">動的検索条件1</span></b><br/>
	 * ユーザによって検索条件が動的に変化する。<br/>
	 * 業務担当者のとき、検索条件追加。<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業ID</td><td>jigyoId</td><td>AND A.JIGYO_ID ='事業ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>対象</td><td>taishoId</td><td>AND A.TAISHO_ID ='対象'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>システム番号</td><td>systemNo</td><td>AND A.SYSYEM_NO ='システム番号'</td></tr>
 	 * </table><br>
 	 * 
 	 * shoruiKanriListに取得情報を格納<br><br>
 	 * 
	 * <b>2.書類ファイル名のセット</b><br>
	 * 結果リストをIterator型の変数iteratorに格納する。<br><br>
	 * 
	 * FileUtilクラスのgetFileName()メソッド呼び出し。<br>
	 * getFileName()メソッドで、パスの区切り文字'\'を'/'に置換、ファイルパスからファイル名を取り出した文字列を返却。<br>
	 * 返却された値をMapにセットし、MapをList変数newListにaddする。<br>
	 * iteratorの要素がなくなるまで繰り返す。<br><br>
	 * 
	 * shoruiKanriListにnewListを代入する。<br><br>
 	 * 
 	 * <b>3.書類情報リストの返却</b><br>
 	 * shoruiKanriListを返却する。
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	検索条件(ShoruiKanriSearchInfo)
	 * @return 書類情報リスト
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriSearchInfo)
	 */
	public List search(UserInfo userInfo, ShoruiKanriSearchInfo searchInfo) throws ApplicationException {
		
		String select = 
			"SELECT *  FROM SHORUIKANRI A WHERE DEL_FLG = 0";	
			
		StringBuffer  query = new StringBuffer(select);

		if(searchInfo.getJigyoId() != null && searchInfo.getJigyoId().length() != 0){
			query.append(" AND A.JIGYO_ID ='" + EscapeUtil.toSqlString(searchInfo.getJigyoId()) + "'");		//事業ID
		}
		if(searchInfo.getTaishoId() != null && searchInfo.getTaishoId().length() != 0){
			query.append(" AND A.TAISHO_ID ='" + EscapeUtil.toSqlString(searchInfo.getTaishoId()) + "'");		//対象
		}
		if(searchInfo.getSystemNo() != null && searchInfo.getSystemNo().length() != 0){
			query.append(" AND A.SYSYEM_NO ='" + EscapeUtil.toSqlString(searchInfo.getSystemNo()) + "'");		//システム受付番号
		}
				
		//ソート順（システム番号の昇順）
		query.append(" ORDER BY SYSTEM_NO");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		//------------------------
		List shoruiKanriList = null;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			shoruiKanriList = SelectUtil.select(connection, query.toString());
			
			//書類ファイルからファイル名を取得してマップにセット
			if(shoruiKanriList != null){
				List newList = new ArrayList();
				Iterator iterator = shoruiKanriList.iterator();
				while(iterator.hasNext()){
					Map shoruiMap = (Map)iterator.next();
					String shoruiFile = (String) shoruiMap.get("SHORUI_FILE");
					String filename = FileUtil.getFileName(shoruiFile);
					shoruiMap.put("FILE_NAME", filename);
					newList.add(shoruiMap);
				}
				shoruiKanriList = newList;
			}

		} catch (NoDataFoundException e) {
			//なにも処理しない
		} catch (DataAccessException e) {
			log.error("書類管理データ検索中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"書類管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}	
		return shoruiKanriList;
	}
	

	/**
	 * 事業情報のページ情報の取得.<br><br>
	 * 
	 * 受付期間内の事業情報のページ情報を取得する。<br><br>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID,				-- 事業ID
	 *     A.JIGYO_NAME,			-- 事業名
	 *     A.NENDO,					-- 年度
	 *     TO_CHAR(A.KAISU) KAISU,	-- 回数
	 *     A.UKETUKEKIKAN_END,		-- 事業受付期間（終了日）
	 *     A.TENPU_WIN,				-- 事業受付期間（終了日）
	 *     A.TENPU_MAC				-- 事業受付期間（終了日）
	 * FROM
	 *     JIGYOKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 *     AND TO_DATE( TO_CHAR(SYSDATE,'YYYY/MM/DD')
	 *              , 'YYYY/MM/DD' ) >= UKETUKEKIKAN_START	-- 受付期間内であることを確認
	 *          AND TO_DATE( TO_CHAR(SYSDATE,'YYYY/MM/DD')
	 *              , 'YYYY/MM/DD' ) <= UKETUKEKIKAN_END
	 * 
	 * --- <b><span style="color:#002288">動的検索条件1</span></b> ---
	 * 
	 * --- <b><span style="color:#008822">動的検索条件2</span></b> ---
	 * 
	 * ORDER BY JIGYO_ID
     * </pre>
     * </td></tr>
	 * </table>
	 * <br/>
	 * <b><span style="color:#002288">動的検索条件1</span></b><br/>
	 * ユーザによって検索条件が動的に変化する。<br/>
	 * 申請者かつ非公募権限がないとき、検索条件追加。<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>HIKOBO_JIGYO_KUBUN</td><td>AND JIGYO_KUBUN NOT IN  ('事業区分')</td></tr>
	 * </table>
	 * <br/>
	 * <b><span style="color:#008822">動的検索条件2</span></b><br/>
	 * 第二引数がJigyoKanriSearchInfoでかつ、jigyoKubunがnullもしくは空のではない場合に下記絞込条件を追加する。<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>jigyoKubun</td><td>AND JIGYO_KUBUN IN  ('事業区分1','事業区分2'…)</td></tr>
	 * </table>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	検索条件(SearchInfo)
	 * @return 事業情報を格納したPageオブジェクト
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.JigyoKanriInfo)
	 */
	public Page searchUketukeJigyo(UserInfo userInfo, 
									SearchInfo searchInfo) 
		throws ApplicationException
	{
		//追加検索条件
		String hikoboQuery = "";
		
//		2005/04/22 追加 ここから----------
//		理由:下記追加文を使用するため
		
//		//申請者の場合は、非公募権限があるかどうかチェックする。
//		if(userInfo.getRole().equals(UserRole.SHINSEISHA)){
//			//申請者の非公募権限が無い場合 → 検索条件を追加（非公募事業区分を除く）
//			ShinseishaInfo shinseishaInfo = userInfo.getShinseishaInfo();
//			if(!"1".equals(shinseishaInfo.getHikoboFlg())){
//				hikoboQuery = new StringBuffer()
//							  .append(" AND JIGYO_KUBUN NOT IN (")
//							  .append(StringUtil.changeArray2CSV(HIKOBO_JIGYO_KUBUN, true))
//							  .append(")")
//							  .toString();
//			}
//		}
		
//		2005/04/22 追加 ここまで----------
		
		String[] jigyoKubun = null;
		//2005/03/24 追加 ------------------------------------------------ここから
		//理由 基盤事業とその他の一覧を別々の画面として表示するため
		if( searchInfo!=null && searchInfo instanceof JigyoKanriSearchInfo){
			jigyoKubun=((JigyoKanriSearchInfo)searchInfo).getJigyoKubun();
			if(jigyoKubun!=null&&jigyoKubun.length>0){
				hikoboQuery = new StringBuffer()
				  .append(" AND JIGYO_KUBUN IN (")
				  .append(StringUtil.changeArray2CSV(jigyoKubun, true))
				  .append(")")
				  .toString();
			}
		}
        
//2006/06/21 苗　追加ここから
        //理由 2006/06/21の応募者メニューの変更すること
        if (searchInfo != null && searchInfo instanceof JigyoKanriSearchInfo) {
            String jigyoCds = ((JigyoKanriSearchInfo) searchInfo).getJigyoCds();
            if(!StringUtil.isBlank(jigyoCds)){
//2006/07/09 劉佳　変更ここから
                hikoboQuery = new StringBuffer()
                  .append(" AND JIGYO_KUBUN IN (")
                  .append(StringUtil.changeArray2CSV(jigyoKubun, true))
                  .append(")")
                  .append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (")
                  .append(StringUtil.changeArray2CSV(jigyoCds.split(","),true))
                  .append(")")
                  .toString();
//2006/07/09 劉佳　変更ここまで
            }
        }
//2006/06/21 苗　追加ここまで        
		//2005/03/24 追加 ------------------------------------------------ここまで
		//       2006/06/15 劉佳　追加ここから
        //      理由 追加項目「KARIRYOIKINO_UKETUKEKIKAN_END」のため
		String select = 
		"SELECT A.JIGYO_ID," 				//事業ID
			 + " A.JIGYO_NAME,"				//事業名
			 + " A.NENDO,"					//年度
			 + " TO_CHAR(A.KAISU) KAISU,"	//回数
			 + " A.UKETUKEKIKAN_END,"		//事業受付期間（終了日）
			 + " A.TENPU_WIN,"				//応募内容ファイル（Win)
			 + " A.TENPU_MAC,"				//応募内容ファイル（Mac)
			 + " A.URL_TITLE,"				//URLタイトル
			 + " A.URL_ADDRESS,"			//URLアドレス
			 + " A.DL_URL,"				    //資料ダウンロードURL
             + " DECODE (SIGN(TO_DATE"
             + "( TO_CHAR(A.KARIRYOIKINO_UKETUKEKIKAN_END,"
             + "'YYYY/MM/DD'), 'YYYY/MM/DD' )- "
             + "TO_DATE( TO_CHAR(SYSDATE ,'YYYY/MM/DD'), 'YYYY/MM/DD' )),0 ,"
             + "'TRUE',1,'TRUE',-1,'FALSE') KARIRYOIKINO_FLAG ,"
             + " A.KARIRYOIKINO_UKETUKEKIKAN_END," //仮領域番号受付期間（終了）
             + " DECODE (SIGN(TO_DATE"
             + "( TO_CHAR(A.RYOIKI_KAKUTEIKIKAN_END,"
             + "'YYYY/MM/DD'), 'YYYY/MM/DD' )- "
             + "TO_DATE( TO_CHAR(SYSDATE ,'YYYY/MM/DD'), 'YYYY/MM/DD' )),0 ,"
             + "'TRUE',1,'TRUE',-1,'FALSE') RYOIKINO_FLAG ,"
             + " A.RYOIKI_KAKUTEIKIKAN_END"; //領域代表者確定締切日   
// 2006/06/15 劉佳　追加ここまで
			//2005/04/26 追加 ここから-----------------------------------
			//受付中研究種目一覧で「所属機関受付締切」と表示するため
// 20050708 特定領域を追加
//			if(jigyoKubun != null && jigyoKubun.length>0 && jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
//				select += ", CHCK.JOKYO_ID";
//			}	 
		if(jigyoKubun != null && 
			jigyoKubun.length>0 && 
			(jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_KIBAN) || jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI) ||
//2006/03/07 追加ここから					
			 jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART) || jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)
//苗　追加ここまで
			)){
			select += ", CHCK.JOKYO_ID";
		}	 
// Horikoshi
			//追加 ここまで----------------------------------------------
			
		select += " FROM JIGYOKANRI A";
			 
// 20050708 特定領域の追加
//			 //2005/04/26 追加 ここから-----------------------------------
//			 //受付中研究種目一覧で「所属機関受付締切」と表示するため
//			 if(jigyoKubun != null && jigyoKubun.length>0 && jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
//			 	select = select + " LEFT JOIN CHCKLISTINFO CHCK "
//			 					+ " ON A.JIGYO_ID = CHCK.JIGYO_ID"
//			 					+ " AND CHCK.SHOZOKU_CD = "+ userInfo.getShinseishaInfo().getShozokuCd();
//			 }
//			 //追加 ここまで----------------------------------------------
		 if(jigyoKubun != null && 
		 	jigyoKubun.length>0 && 
			(jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_KIBAN) || jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI) ||
//2006/03/07 追加ここから					
			 jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART) || jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)
//苗　追加ここまで			
		     )){
		 	select = select + " LEFT JOIN CHCKLISTINFO CHCK "
		 					+ " ON A.JIGYO_ID = CHCK.JIGYO_ID"
		 					+ " AND CHCK.SHOZOKU_CD = "+ EscapeUtil.toSqlString(userInfo.getShinseishaInfo().getShozokuCd());
		 }
// Horikoshi

			 select = select + " WHERE DEL_FLG = 0"
			 + " AND "						//受付期間内であることを確認
			 + "  TO_DATE( TO_CHAR(SYSDATE,'YYYY/MM/DD'), 'YYYY/MM/DD' ) >= UKETUKEKIKAN_START"		
			 + " AND "
			 + "  TO_DATE( TO_CHAR(SYSDATE,'YYYY/MM/DD'), 'YYYY/MM/DD' ) <= UKETUKEKIKAN_END"		
			 + hikoboQuery
			 ;
		StringBuffer  query = new StringBuffer(select);
		
		//ソート順（事業IDの昇順）
		query.append(" ORDER BY JIGYO_ID");
		
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

			return SelectUtil.selectPageInfo(connection, searchInfo, query.toString());

		} catch (DataAccessException e) {
			log.error("事業管理データ検索中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"事業管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);

		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * 書類情報の削除.<br><br>
	 * 
	 * 書類情報を削除する。<br><br>
	 * 
	 * <b>1.書類情報の取得</b><br>
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID			-- 事業ID
	 *     ,A.TAISHO_ID			-- 対象
	 *     ,A.SYSTEM_NO			-- システム受付番号
	 *     ,A.SHORUI_FILE		-- 格納先ディレクトリ
	 *     ,A.SHORUI_NAME		-- 書類名
	 *     ,A.DEL_FLG			-- 削除フラグ
	 * FROM
	 *     SHORUIKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 * 
	 * 	--- 動的検索条件1 ---
	 * 
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <b><span style="color:#002288">動的検索条件1</span></b><br/>
	 * pkInfoによって検索条件が動的に変化する。<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>jigyoId</td><td>AND JIGYO_ID = '事業ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>システム番号</td><td>systemNo</td><td>AND SYSTEM_NO = 'システム番号'</td></tr>
	 * </table><br><br>
	 * 
	 * <b>2.書類情報の削除</b><br>
	 * 
	 * 以下のSQLを実行し、書類管理テーブル[SHORUIKANRI]から対象レコードを論理削除する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * 
	 * UPDATE
	 *     SHORUIKANRI
	 * SET
	 *     DEL_FLG = 1		-- 削除フラグ
	 * 
	 * 	--- 動的検索条件1 ---
	 * 
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <b><span style="color:#002288">動的検索条件1</span></b><br/>
	 * pkInfoによって検索条件が動的に変化する。<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>jigyoId</td><td>WHERE JIGYO_ID = '事業ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>システム番号</td><td>systemNo</td><td>WHERE SYSTEM_NO = 'システム番号'</td></tr>
	 * </table><br><br>
	 * 
	 * <b>3.書類ファイル名の変更</b><br>
	 * 1.で取得した事業情報の書類ファイルパスがnullでないとき、
	 * FileクラスのrenameTo()メソッドでファイル名を"_delete"を付加した名前に変更する。<br>
	 * (例)20041122135506001.xls　→　20041122135506001_delete.xls
	 * 
	 * @param userInfo	UserInfo
	 * @param pkIfo		削除する書類情報のPK情報(ShoruiKanriPk)
	 * @return なし
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriPk)
	 */
	public void delete(UserInfo userInfo, ShoruiKanriPk pkInfo) throws ApplicationException {
		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();			
			//---------------------------------------
			//書類管理情報の更新
			//---------------------------------------
			ShoruiKanriInfoDao dao = new ShoruiKanriInfoDao(userInfo);
			ShoruiKanriInfo selectInfo = dao.selectShoruiKanriInfo(connection, pkInfo);
			String shoruiPath = selectInfo.getShoruiFile();
			dao.deleteFlgShoruiKanriInfo(connection, pkInfo);
			
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;

			//---------------------------------------
			//書類ファイルのファイル名を変更
			//---------------------------------------
			if(shoruiPath != null){
				if(new File(shoruiPath).exists()){
					new File(shoruiPath).renameTo(new File(shoruiPath + "_delete"));
				}
			}

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"書類管理データ削除中にDBエラーが発生しました。",
				new ErrorInfo("errors.4003"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"書類管理データ削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}	
		
	}
	
	/**
	 * 書類情報の削除.<br><br>
	 * 
	 * システム番号をキーに書類情報を論理削除する。<br><br>
	 * 
	 * <b>1.書類情報を削除</b><br>
	 * shoruiKanriPkに引数deleteInfoの変数systemNoの値をセットする。<br>
	 * 第一引数userInfoとshoruiPkを引数に自クラスのdelete(UserInfo, ShoruiKanriPk)メソッドを呼び、書類情報を論理削除する。<br><br>
	 * 
	 * <b>2.書類リスト取得</b><br>
	 * searchInfoに引数deleteInfoの変数jigyoIdの値をセットする。<br>
	 * 第一引数userInfoとsearchInfoを引数に自クラスのsearch(UserInfo, ShoruiKanriSearchInfo)を呼び、書類リストを取得する。<br><br>
	 * 
	 * @param userInfo		UserInfo
	 * @param deleteInfo	ShoruiKanriInfo
	 * @return 書類情報のリスト
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriInfo)
	 */
	public List delete(UserInfo userInfo, ShoruiKanriInfo deleteInfo) throws ApplicationException {
		
		//-----------------------
		// 書類管理情報削除
		//-----------------------
		ShoruiKanriPk shoruiPk = new ShoruiKanriPk();
		shoruiPk.setSystemNo(deleteInfo.getSystemNo());
		delete(userInfo, shoruiPk);
		
		//-----------------------
		// 書類リスト取得
		//-----------------------
		String jigyoId = deleteInfo.getJigyoId();
		//検索用キーの事業IDを再セット	
		ShoruiKanriSearchInfo searchInfo = new ShoruiKanriSearchInfo();
		searchInfo.setJigyoId(jigyoId);
		List shoruiKanriList = search(userInfo, searchInfo);
		
		return shoruiKanriList;
	}
	
	/**
	 * 書類情報の取得.<br><br>
	 * 
	 * 書類情報を取得する。<br><br>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID			-- 事業ID
	 *     ,A.TAISHO_ID			-- 対象
	 *     ,A.SYSTEM_NO			-- システム受付番号
	 *     ,A.SHORUI_FILE		-- 格納先ディレクトリ
	 *     ,A.SHORUI_NAME		-- 書類名
	 *     ,A.DEL_FLG			-- 削除フラグ
	 * FROM
	 *     SHORUIKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 * 
	 * 	--- 動的検索条件1 ---
	 * 
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <b><span style="color:#002288">動的検索条件1</span></b><br/>
	 * pkInfoによって検索条件が動的に変化する。<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>jigyoId</td><td>AND JIGYO_ID = '事業ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>システム番号</td><td>systemNo</td><td>AND SYSTEM_NO = 'システム番号'</td></tr>
	 * </table>
	 * 
	 * @param userInfo	UserInfo
	 * @param pkInfo	ShoruiKanriPk
	 * @return 書類情報
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#selectShoruiInfo(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriPk)
	 */
	public ShoruiKanriInfo selectShoruiInfo(UserInfo userInfo, ShoruiKanriPk pkInfo) throws ApplicationException {
		
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			ShoruiKanriInfoDao dao = new ShoruiKanriInfoDao(userInfo);
			return dao.selectShoruiKanriInfo(connection, pkInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"書類管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * 事業情報ファイルリソースの取得.<br><br>
	 * 
	 * 事業情報ファイルリソースを取得する。<br><br>
	 * 
	 * <b>1.事業情報を取得</b><br>
	 * 第二引数jigyoPkの変数jigyoIdがセットされているとき、事業情報を検索する。<br>
	 * 第一引数userInfoと第二引数jigyoPkを引数に自クラスのselect(UserInfo, JigyoKanriPk)を呼び、
	 * 事業情報を取得する。<br><br>
	 * 
	 * <b>2.ファイルパスを取得</b><br>
	 * 第三引数fileFlgをもとにファイルパスを取得する。<br>
	 * fileFlgの値によって、jigyoInfoのtenpuWin、tenpuMac、hyokaFileを取得しfilePathに格納する。<br><br>
	 * 
	 * <b>3.ファイルリソースを取得</b><br>
	 * filePathがnullまたは空白のとき、例外をthrowする。<br><br>
	 * 
	 * FileUtilクラスのreadFile()にて、ファイルリソースを取得する。
	 * 
	 * @param userInfo	UserInfo
	 * @param jigyoPk	JigyoKanriPk
	 * @param fileFlg	ファイルフラグ(String)
	 * @return 事業情報ファイルリソース
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#getJigyoKanriFileRes(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.JigyoKanriPk)
	 */
	public FileResource getJigyoKanriFileRes(UserInfo userInfo, JigyoKanriPk jigyoPk, String fileFlg) throws ApplicationException {

		FileResource fileRes = null;
		JigyoKanriInfo jigyoInfo = null;
		
		//idがセットされている場合は、事業管理情報を検索する
		if(jigyoPk.getJigyoId() != null && !jigyoPk.equals("")){
			try{
				jigyoInfo = select(userInfo, jigyoPk);
			}catch(ApplicationException e){
				throw new FileIOException(
				"事業管理データ検索中にDBエラーが発生しました。");				
			}			
		}
	
		//ファイルフラグをもとにファイルパスを取得
		String filePath = null;
		if(fileFlg.equals(FILE_FLG_TENPU_WIN)){
			filePath = jigyoInfo.getTenpuWin();		//添付ファイル（Win）
		}else if(fileFlg.equals(FILE_FLG_TENPU_MAC)){
			filePath = jigyoInfo.getTenpuMac();		//添付ファイル（Mac）			
		}else if(fileFlg.equals(FILE_FLG_HYOKA)){
			filePath = jigyoInfo.getHyokaFile();	//評価用ファイル					
		}
		
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
		return fileRes;

	}
	
	/**
	 * 書類情報ファイルリソースの取得.<br><br>
	 * 
	 * 書類情報ファイルリソースを取得する。<br><br>
	 * 
	 * <b>1.書類情報を取得</b><br>
	 * 第一引数userInfoと第二引数shoruiPkを引数に自クラスのselectShoruiInfo(UserInfo, shoruiKanriPk)を呼び、
	 * 書類情報を取得する。<br><br>
	 * 
	 * <b>2.ファイルリソースを取得</b><br>
	 * filePathにshoruiInfoの変数shoruiFileを格納する。<br>
	 * filePathがnullまたは空白のとき、例外をthrowする。<br><br>
	 * 
	 * FileUtilクラスのreadFile()にて、ファイルリソースを取得する。
	 * 
	 * @param userInfo	UserInfo
	 * @param shoruiPk	ShoruiKanriPk
	 * @return 書類情報ファイルリソース
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#getShoruiKanriFileRes(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriPk)
	 */
	public FileResource getShoruiKanriFileRes(UserInfo userInfo, ShoruiKanriPk shoruiPk) throws ApplicationException {

			FileResource fileRes = null;
			ShoruiKanriInfo shoruiInfo = null;
		
			JigyoKanriMaintenance mantenance = new JigyoKanriMaintenance();
			try{
				shoruiInfo = mantenance.selectShoruiInfo(userInfo, shoruiPk);
			}catch(ApplicationException e){
				throw new FileIOException(
				"書類管理データ検索中にDBエラーが発生しました。");				
			}
		
			String filePath = shoruiInfo.getShoruiFile();
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
			return fileRes;
	}
	
	/**
	 * 書類一覧ページ情報の取得.<br><br>
	 * 
	 * 書類一覧のページ情報を取得する。<br><br>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID, 			-- 事業ID
	 *     B.TAISHO_ID,			-- 対象
	 *     B.SHORUI_NAME,		-- 書類名
	 *     B.SYSTEM_NO,			-- システム受付番号
	 *     A.JIGYO_NAME,		-- 事業名
	 *     A.NENDO,			-- 年度
	 *     TO_CHAR(A.KAISU) KAISU,	-- 回数
	 *     A.UKETUKEKIKAN_START,	-- 事業受付期間（開始日）
	 *     A.UKETUKEKIKAN_END		-- 事業受付期間（終了日）
	 * FROM
	 *     JIGYOKANRI A, SHORUIKANRI B
	 * WHERE
	 *     A.JIGYO_ID = B.JIGYO_ID
	 *     AND A.DEL_FLG = 0
	 *     AND B.DEL_FLG = 0
	 * 
	 * 	--- 動的検索条件1 ---
	 * 
	 * ORDER BY A.JIGYO_ID,SYSTEM_NO
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <b><span style="color:#002288">動的検索条件1</span></b><br/>
	 * searchInfoによって検索条件が動的に変化する。<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>jigyoId</td><td>AND B.JIGYO_ID = '事業ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>対象</td><td>taishoId</td><td>AND B.TAISHO_ID = '対象'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>システム番号</td><td>systemNo</td><td>AND B.SYSYEM_NO = 'システム番号'</td></tr>
	 * </table>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	ShoruiKanriSearchInfo
	 * @return 書類情報リスト
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#searchShoruiList(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriSearchInfo)
	 */
	public Page searchShoruiList(UserInfo userInfo, ShoruiKanriSearchInfo searchInfo) throws ApplicationException {
		//-----------------------
		// SQL文の作成
		//-----------------------
		String select = 
			"SELECT A.JIGYO_ID," 		//事業ID
			+ " B.TAISHO_ID,"			//対象
			+ " B.SHORUI_NAME,"			//書類名
			+ " B.SYSTEM_NO,"			//システム受付番号
			+ " A.JIGYO_NAME,"			//事業名
			+ " A.NENDO,"				//年度
			+ " TO_CHAR(A.KAISU) KAISU,"//回数
			+ " A.UKETUKEKIKAN_START,"	//事業受付期間（開始日）
			+ " A.UKETUKEKIKAN_END"		//事業受付期間（終了日）
			+ " FROM JIGYOKANRI A, SHORUIKANRI B"
			+ " WHERE A.JIGYO_ID = B.JIGYO_ID"
			+ " AND A.DEL_FLG = 0"
			+ " AND B.DEL_FLG = 0";
			

		StringBuffer query = new StringBuffer(select);
		if(searchInfo.getJigyoId() != null && searchInfo.getJigyoId().length() != 0){
			query.append(" AND B.JIGYO_ID ='" + EscapeUtil.toSqlString(searchInfo.getJigyoId()) + "'");		//事業ID
		}
		if(searchInfo.getTaishoId() != null && searchInfo.getTaishoId().length() != 0){
			query.append(" AND B.TAISHO_ID ='" + EscapeUtil.toSqlString(searchInfo.getTaishoId()) + "'");		//対象
		}
		if(searchInfo.getSystemNo() != null && searchInfo.getSystemNo().length() != 0){
			query.append(" AND B.SYSYEM_NO ='" + EscapeUtil.toSqlString(searchInfo.getSystemNo()) + "'");		//システム受付番号
		}
		query.append(" ORDER BY A.JIGYO_ID,SYSTEM_NO");	//事業ID,システム受付番号の昇順
				
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
			return SelectUtil.selectPageInfo(connection, searchInfo, query.toString());

		} catch (DataAccessException e) {
			log.error("書類一覧データ検索中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"書類一覧データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * ページ情報の取得.<br><br>
	 * 
	 * ページ情報を取得する。<br><br>
	 * 
	 * 第二引数で渡された検索条件に基づき、事業情報管理テーブルを検索する。<br/>
	 * 相関副問い合わせで申請状況IDが学振受理のレコードを取得する。<br/><br/>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）<br/>
	 * 検索結果をPageオブジェクトに格納し、返却する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- 事業ID
	 *     ,A.NENDO					-- 年度
	 *     ,A.KAISU					-- 回数
	 *     ,A.JIGYO_NAME			-- 事業名
	 *     ,A.JIGYO_KUBUN			-- 事業区分
	 *     ,A.SHINSA_KUBUN			-- 審査区分
	 *     ,A.TANTOKA_NAME			-- 業務担当課
	 *     ,A.TANTOKAKARI			-- 業務担当係名
	 *     ,A.TOIAWASE_NAME			-- 問い合わせ先担当者名
	 *     ,A.TOIAWASE_TEL			-- 問い合わせ先電話番号
	 *     ,A.TOIAWASE_EMAIL		-- 問い合わせ先E-mail
	 *     ,A.UKETUKEKIKAN_START	-- 学振受付期間（開始）
	 *     ,A.UKETUKEKIKAN_END		-- 学振受付期間（終了）
	 *     ,A.SHINSAKIGEN			-- 審査期限
	 *     ,A.TENPU_NAME			-- 添付文書名
	 *     ,A.TENPU_WIN				-- 添付ファイル格納フォルダ（Win）
	 *     ,A.TENPU_MAC				-- 添付ファイル格納フォルダ（Mac）
	 *     ,A.HYOKA_FILE_FLG		-- 評価用ファイル有無
	 *     ,A.HYOKA_FILE			-- 評価用ファイル格納フォルダ
	 *     ,A.KOKAI_FLG				-- 公開フラグ
	 *     ,A.KESSAI_NO				-- 公開決裁番号
	 *     ,A.KOKAI_ID				-- 公開確定者ID
	 *     ,A.HOKAN_DATE			-- データ保管日
	 *     ,A.YUKO_DATE				-- 保管有効期限
	 *     ,A.BIKO				-- 備考
	 *     ,A.DEL_FLG				-- 削除フラグ
	 * FROM
	 *     JIGYOKANRI A					-- 事業情報管理テーブル
	 * WHERE
	 *     A.DEL_FLG = '0'
	 *     AND EXISTS(
	 *         SELECT
	 *             *
	 *         FROM
	 *             SHINSEIDATAKANRI B	-- 申請データ管理テーブル
	 *         WHERE
	 *             B.JIGYO_ID = A.JIGYO_ID
	 *             AND B.DEL_FLG = '0'
	 *             AND B.JOKYO_ID= '06'	-- ステータスが学振受理
	 *         )
	 * 
	 * ORDER BY A.JIGYO_ID
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>B.JOKYO_ID</td><td>'06'  [StatusCode.STATUS_GAKUSIN_JYURI]</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	検索条件(SearchInfo)
	 * @return 事業情報を格納したPageオブジェクト
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#searchWarifuriJigyo(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.SearchInfo)
	 */
	public Page searchWarifuriJigyo(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException {

		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		StringBuffer query= new StringBuffer();
		query.append("SELECT * FROM JIGYOKANRI A ");
        query.append("WHERE A.DEL_FLG = '0' AND EXISTS(SELECT * FROM SHINSEIDATAKANRI B WHERE B.JIGYO_ID = A.JIGYO_ID AND B.DEL_FLG = '0' AND B.JOKYO_ID='"
                + StatusCode.STATUS_GAKUSIN_JYURI //ステータスが学振受理のもの
                + "') ");
		//ソート順（事業IDの昇順）
		query.append("ORDER BY A.JIGYO_ID");		
		
		if(log.isDebugEnabled()){
			log.debug("query:'" + query.toString() + "'");
		}
		
		//-----------------------
		// ページ取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection, searchInfo, query.toString());
		} catch (DataAccessException e) {
			log.error("事業管理データ検索中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"事業管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * ファイル書き込み.<br><br>
	 * 
	 * ファイルにバイトの配列を書き込む。<br><br>
	 * 
	 * 第一引数fileから拡張子を除いたファイル名を取得し、変数jigyoIdに代入。<br/><br/>
	 * 
	 * 第一引数fileの親ディレクトリのリストを取得する。<br/><br/>
	 * 
	 * 変数jigyoIdと一致するリスト中のファイル名を削除する。<br/>
	 * FileUtilクラスのdelete()メソッドにて、ファイルを削除する。<br/><br/>
	 * 
	 * FileUtilクラスのwriteFile()メソッドにて、ファイルを格納する。<br/><br/>
	 * 
	 * @param	file	ファイル
	 * @param	fileRes	書き込むリソース
	 * @return	なし
	 * @throws	ApplicationException
	 */
	public void writeFile(File file, FileResource fileRes, boolean deleteFlg)
									 throws ApplicationException{
		try{
			if(deleteFlg){			
				//拡張子を除いたファイル名が同じファイルは削除
				int index = file.getName().lastIndexOf(".");
				String jigyoId = "";
				if(index > -1){
					jigyoId = file.getName().substring(0, index);
				}
				File[] list = file.getParentFile().listFiles();
				if (list != null && list.length > 0) {
					for (int i = 0; i < list.length; i++) {
						int index1 = list[i].getName().lastIndexOf(".");
						if(index1 > -1){
							String str = list[i].getName().substring(0, index1);
							if(jigyoId.equals(str)){
								FileUtil.delete(list[i]);
							}
						}
					}
				}
			}
		
			//ファイル格納			
			FileUtil.writeFile(file, fileRes.getBinary());	
		}catch(IOException e){
			throw new ApplicationException(
				"ファイル格納中にエラーが発生しました。ファイル'" + file.getName() + "'",
				new ErrorInfo("errors.7001"),
				e);				
		}
	}

	/**
	 * 事業情報の取得.<br><br>
	 * 
	 * 事業情報を取得する。<br><br>
	 * 
	 * 事業コード、年度、回数を元に事業情報管理テーブルから検索する。<br/><br/>
	 * 
	 * <b>1.事業IDの作成</b><br/>
	 * (1) DateUtilクラスのchangeWareki2Seireki()メソッドにて、
	 * 第三引数nendoを和暦から西暦に変換する。<br/><br/>
	 * 
	 * (2) 事業IDを作成する。<br/>
	 * 　(1)で取得した年度 + 第二引数jigyoCd + 第四引数kaisu を事業IDとする。<br/><br/>
	 * 
	 * <b>2.事業情報を取得</b><br>
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- 事業ID
	 *     ,A.NENDO					-- 年度
	 *     ,A.KAISU					-- 回数
	 *     ,A.JIGYO_NAME			-- 事業名
	 *     ,A.JIGYO_KUBUN			-- 事業区分
	 *     ,A.SHINSA_KUBUN			-- 審査区分
	 *     ,A.TANTOKA_NAME			-- 業務担当課
	 *     ,A.TANTOKAKARI			-- 業務担当係名
	 *     ,A.TOIAWASE_NAME			-- 問い合わせ先担当者名
	 *     ,A.TOIAWASE_TEL			-- 問い合わせ先電話番号
	 *     ,A.TOIAWASE_EMAIL		-- 問い合わせ先E-mail
	 *     ,A.UKETUKEKIKAN_START	-- 学振受付期間（開始）
	 *     ,A.UKETUKEKIKAN_END		-- 学振受付期間（終了）
	 *     ,A.SHINSAKIGEN			-- 審査期限
	 *     ,A.TENPU_NAME			-- 添付文書名
	 *     ,A.TENPU_WIN				-- 添付ファイル格納フォルダ（Win）
	 *     ,A.TENPU_MAC				-- 添付ファイル格納フォルダ（Mac）
	 *     ,A.HYOKA_FILE_FLG		-- 評価用ファイル有無
	 *     ,A.HYOKA_FILE			-- 評価用ファイル格納フォルダ
	 *     ,A.KOKAI_FLG				-- 公開フラグ
	 *     ,A.KESSAI_NO				-- 公開決裁番号
	 *     ,A.KOKAI_ID				-- 公開確定者ID
	 *     ,A.HOKAN_DATE			-- データ保管日
	 *     ,A.YUKO_DATE				-- 保管有効期限
	 *     ,A.BIKO				-- 備考
	 *     ,A.DEL_FLG				-- 削除フラグ
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
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>1.で作成した事業ID</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo	UserInfo
	 * @param jigyoCd	事業コード
	 * @param nendo		年度
	 * @param kaisu		回数
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#getJigyoKanriInfo(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String, java.lang.String, java.lang.String)
	 */
	public JigyoKanriInfo getJigyoKanriInfo(
		UserInfo userInfo,
		String jigyoCd,
		String nendo,
		String kaisu)
		throws NoDataFoundException, ApplicationException
	{
		//DBコネクションの取得
		Connection connection = null;	
		try{
			connection = DatabaseUtil.getConnection();
			
			//---事業情報を取得する
			JigyoKanriInfo info = null;
			try {
				JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
				info = dao.selectJigyoKanriInfo(connection, jigyoCd, nendo, kaisu);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"事業管理情報データ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			return info;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}	
}