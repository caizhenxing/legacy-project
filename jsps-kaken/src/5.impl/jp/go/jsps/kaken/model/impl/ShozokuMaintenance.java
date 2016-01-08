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
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.IShozokuMaintenance;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.BukyokutantoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.impl.RuleInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseishaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShozokuInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.KikanInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.model.vo.ShozokuSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.CheckDiditUtil;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.dao.impl.JigyoKanriInfoDao;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.util.SendMailer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 所属機関情報管理クラス</br>
 * </br>
 * <b>概要</b></br>
 * 所属機関情報を管理します。
 * 所属機関担当者が更新された際は、その所属機関に所属する申請者情報に関しても更新します。
 * （所属機関の名称等を申請者情報として保持しているため）
 * 
 */
public class ShozokuMaintenance implements IShozokuMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(ShozokuMaintenance.class);

	/** メールサーバアドレス */
	private static final String SMTP_SERVER_ADDRESS = ApplicationSettings.getString(ISettingKeys.SMTP_SERVER_ADDRESS); 

	/** 差出人（統一して１つ） */
	private static final String FROM_ADDRESS = ApplicationSettings.getString(ISettingKeys.FROM_ADDRESS);
	
	/** 未承認申請書締め切り期限までの日付 */
	protected static final int DATE_BY_SHONIN_KIGEN = ApplicationSettings.getInt(ISettingKeys.DATE_BY_SHONIN_KIGEN);
	
	/** メール内容（所属機関担当者への未承認確認通知）「件名」 */
	protected static final String SUBJECT_SHINSEISHO_SHONIN_TSUCHI = ApplicationSettings.getString(ISettingKeys.SUBJECT_SHINSEISHO_SHONIN_TSUCHI);
		
	/** メール内容（所属機関担当者への未承認確認通知）「本文」 */
	protected static final String CONTENT_SHINSEISHO_SHONIN_TSUCHI = ApplicationSettings.getString(ISettingKeys.CONTENT_SHINSEISHO_SHONIN_TSUCHI);
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public ShozokuMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IShozokuMaintenance
	//---------------------------------------------------------------------

	/**
	 * 所属機関担当者情報を新規作成する。<br/><br/>
	 * 登録データを検索し、検索結果を返却する。<br/><br/>
	 * 
	 * <b>1.所属機関担当者ID作成</b><br/>
	 * 　(1)所属機関毎の順番を取得 <br/><br/>
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     TO_CHAR(MAX(SUBSTR(SHOZOKUTANTO_ID,6,2)) + 1,'FM00') COUNT
     * FROM
     *     SHOZOKUTANTOINFO
     * WHERE
     *     SHOZOKU_CD = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数addInfoの変数shozokuCd</td></tr>
	 * </table><br/><br/>
	 * 
	 * 　(2)所属機関担当者IDを作成<br/>
	 * 所属機関コード（第二引数addInfoに格納されているchozokuCd）と(1)で取得したコードをkeyにしてチェックデジットを取得する。
	 * チェックデジットのkeyに、取得したチェックデジットを付加したものを所属機関担当者IDとする。
	 * なお、チェックデジットはCheckDiditUtilクラスのgetCheckDigit()にて取得する。<br/><br/>
	 * 
	 * <b>2.パスワード情報作成</b><br/>
	 * パスワード発行ルールに基づき、パスワードを作成する。<br/><br/>
	 * 　(1)パスワード発行ルール取得<br/><br/>
	 * 以下のSQLを実行し、パスワード発行ルールを取得する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     TAISHO_ID
	 *     ,MOJISU_CHK
	 *     ,CHAR_CHK1
	 *     ,CHAR_CHK2
	 *     ,CHAR_CHK3
	 *     ,CHAR_CHK4
	 *     ,CHAR_CHK5
	 *     ,YUKO_DATE
	 *     ,BIKO
	 * FROM
	 *     RULEINFO A
	 * WHERE
	 *     TAISHO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/><br/>
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TAISHO_ID</td><td>対象者ID '2'　[所属機関担当者]</td></tr>
	 * </table><br/><br/>
	 * 　(2)パスワード作成<br/><br/>
	 * RandomPwdクラスのgenerate()を使用してパスワードを作成する。
	 * なお、パスワード生成に関する詳細は割愛する。<br/><br/>
	 * 
	 * <b>3.所属機関担当者情報の登録</b><br/>
	 * 　(1)重複チェック<br/><br/>
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * 検索した結果、レコードが選択された場合は、すでにレコード登録済みなので、例外をthrowする。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- 所属機関担当者ID
	 *     ,A.SHOZOKU_CD				-- 所属機関名（コード）
	 *     ,A.SHOZOKU_NAME_KANJI			-- 所属機関名（日本語）
	 *     ,A.SHOZOKU_RYAKUSHO			-- 所属機関名（略称）
	 *     ,A.SHOZOKU_NAME_EIGO			-- 所属機関名（英文）
	 *     ,A.SHUBETU_CD				-- 機関種別
	 *     ,A.PASSWORD				-- パスワード
	 *     ,A.SEKININSHA_NAME_SEI			-- 責任者氏名（姓）
	 *     ,A.SEKININSHA_NAME_MEI			-- 責任者氏名（名）
	 *     ,A.SEKININSHA_YAKU			-- 責任者役職
	 *     ,A.BUKYOKU_NAME				-- 担当部課名
	 *     ,A.KAKARI_NAME				-- 担当係名
	 *     ,A.TANTO_NAME_SEI			-- 担当者名（姓）
	 *     ,A.TANTO_NAME_MEI			-- 担当者名（名）
	 *     ,A.TANTO_TEL				-- 担当者部局所在地（電話番号）
	 *     ,A.TANTO_FAX				-- 担当者部局所在地（FAX番号）
	 *     ,A.TANTO_EMAIL				-- 担当者部局所在地（Email）
	 *     ,A.TANTO_EMAIL2				-- 担当者部局所在地（Email2）
	 *     ,A.TANTO_ZIP				-- 担当者部局所在地（郵便番号）
	 *     ,A.TANTO_ADDRESS				-- 担当者部局所在地（住所）
	 *     ,A.NINSHOKEY_FLG				-- 認証キー発行フラグ
	 *     ,A.BIKO				-- 備考
	 *     ,A.YUKO_DATE				-- 有効期限
	 *     ,A.ID_DATE				-- ID発行日付
	 *     ,A.DEL_FLG				-- 削除フラグ
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * <tr bgcolor="#FFFFFF"><td width="40%">SHOZOKUTANTO_ID</td><td>第二引数addInfoの変数shozokuTantoId</td></tr>
	 * </table><br/><br/>
	 * 　(2)所属機関担当者テーブルへ登録<br/>
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * INSERT INTO SHOZOKUTANTO INFO(
	 *     SHOZOKUTANTO_ID				-- 所属機関担当者ID
	 *     ,SHOZOKU_CD				-- 所属機関名（コード）
	 *     ,SHOZOKU_NAME_KANJI			-- 所属機関名（日本語）
	 *     ,SHOZOKU_RYAKUSHO			-- 所属機関名（略称）
	 *     ,SHOZOKU_NAME_EIGO			-- 所属機関名（英文）
	 *     ,SHUBETU_CD				-- 機関種別
	 *     ,PASSWORD				-- パスワード
	 *     ,SEKININSHA_NAME_SEI			-- 責任者氏名（姓）
	 *     ,SEKININSHA_NAME_MEI			-- 責任者氏名（名）
	 *     ,SEKININSHA_YAKU				-- 責任者役職
	 *     ,BUKYOKU_NAME				-- 担当部課名
	 *     ,KAKARI_NAME				-- 担当係名
	 *     ,TANTO_NAME_SEI				-- 担当者名（姓）
	 *     ,TANTO_NAME_MEI				-- 担当者名（名）
	 *     ,TANTO_TEL				-- 担当者部局所在地（電話番号）
	 *     ,TANTO_FAX				-- 担当者部局所在地（FAX番号）
	 *     ,TANTO_EMAIL				-- 担当者部局所在地（Email）
	 *     ,TANTO_EMAIL2				-- 担当者部局所在地（Email2）
	 *     ,TANTO_ZIP				-- 担当者部局所在地（郵便番号）
	 *     ,TANTO_ADDRESS				-- 担当者部局所在地（住所）
	 *     ,NINSHOKEY_FLG				-- 認証キー発行フラグ
	 *     ,BIKO					-- 備考
	 *     ,YUKO_DATE				-- 有効期限
	 *     ,A.ID_DATE				-- ID発行日付
	 *     ,DEL_FLG)				-- 削除フラグ
	 * VALUES(
	 *     ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?
	 * )
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関担当者ID</td><td>第二引数addInfoの変数shozokuTantoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（コード）</td><td>第二引数addInfoの変数shozokuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（日本語）</td><td>第二引数addInfoの変数shozokuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（略称）</td><td>第二引数addInfoの変数shozokuRyakusho</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（英文）</td><td>第二引数addInfoの変数shozokuNameEigo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>機関種別</td><td>第二引数addInfoの変数shubetuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>パスワード</td><td>第二引数addInfoの変数password</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>責任者氏名（姓）</td><td>第二引数addInfoの変数sekininshaNameSei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>責任者氏名（名）</td><td>第二引数addInfoの変数sekininshaNameMei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>責任者役職</td><td>第二引数addInfoの変数sekininshaYaku</td></tr>
 	 *     <tr bgcolor="#FFFFFF"><td>担当部課名</td><td>第二引数addInfoの変数bukyokuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当係名</td><td>第二引数addInfoの変数kakariName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者名（姓）</td><td>第二引数addInfoの変数tantoNameSei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者名（名）</td><td>第二引数addInfoの変数tantoNameMei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者部局所在地（電話番号）</td><td>第二引数addInfoの変数tantoTel</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者部局所在地（FAX番号）</td><td>第二引数addInfoの変数tantoFax</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者部局所在地（Email）</td><td>第二引数addInfoの変数tantoEmail</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者部局所在地（Email2）</td><td>第二引数addInfoの変数tantoEmail2</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者部局所在地（郵便番号）</td><td>第二引数addInfoの変数tantoZip</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者部局所在地（住所）</td><td>第二引数addInfoの変数tantoAddress</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>認証キー発行フラグ</td><td>第二引数addInfoの変数ninshokeyFlg</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>備考</td><td>第二引数addInfoの変数biko</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>有効期限</td><td>第二引数addInfoの変数yukoDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>ID発行日付</td><td>第二引数addInfoの変数idDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>0</td></tr>
	 * </table><br/><br/>
	 * 
	 * 　(3)登録データの取得<br/>
	 * 登録データを取得する。<br/><br/>
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- 所属機関担当者ID
	 *     ,A.SHOZOKU_CD				-- 所属機関名（コード）
	 *     ,A.SHOZOKU_NAME_KANJI			-- 所属機関名（日本語）
	 *     ,A.SHOZOKU_RYAKUSHO			-- 所属機関名（略称）
	 *     ,A.SHOZOKU_NAME_EIGO			-- 所属機関名（英文）
	 *     ,A.SHUBETU_CD				-- 機関種別
	 *     ,A.PASSWORD				-- パスワード
	 *     ,A.SEKININSHA_NAME_SEI			-- 責任者氏名（姓）
	 *     ,A.SEKININSHA_NAME_MEI			-- 責任者氏名（名）
	 *     ,A.SEKININSHA_YAKU			-- 責任者役職
	 *     ,A.BUKYOKU_NAME				-- 担当部課名
	 *     ,A.KAKARI_NAME				-- 担当係名
	 *     ,A.TANTO_NAME_SEI			-- 担当者名（姓）
	 *     ,A.TANTO_NAME_MEI			-- 担当者名（名）
	 *     ,A.TANTO_TEL				-- 担当者部局所在地（電話番号）
	 *     ,A.TANTO_FAX				-- 担当者部局所在地（FAX番号）
	 *     ,A.TANTO_EMAIL				-- 担当者部局所在地（Email）
	 *     ,A.TANTO_EMAIL2				-- 担当者部局所在地（Email2）
	 *     ,A.TANTO_ZIP				-- 担当者部局所在地（郵便番号）
	 *     ,A.TANTO_ADDRESS				-- 担当者部局所在地（住所）
	 *     ,A.NINSHOKEY_FLG				-- 認証キー発行フラグ
	 *     ,A.BIKO				-- 備考
	 *     ,A.YUKO_DATE				-- 有効期限
	 *     ,A.ID_DATE				-- ID発行日付
	 *     ,A.DEL_FLG				-- 削除フラグ
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * <tr bgcolor="#FFFFFF"><td>SHOZOKUTANTO_ID</td><td>第二引数addInfoの変数shozokuTantoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * <b>4.申請者情報の更新</b><br/>
	 * 申請者情報テーブルを更新する。<br/>
	 * （所属機関名等が更新されている場合があるため、申請者情報も更新処理を行う。）<br/><br/>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * 対象データが見つからないとき、例外をthrowする。<br/><br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE SHINSEISHAINFO SET
	 *     SHOZOKU_NAME = ?
	 *     ,SHOZOKU_NAME_EIGO = ?
	 *     ,SHOZOKU_NAME_RYAKU = ?
	 * WHERE
	 *     SHOZOKU_CD = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME</td><td>第二引数addInfoの変数shozokuName</td></tr>
	 * <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_EIGO</td><td>第二引数addInfoの変数shozokuNameEigo</td></tr>
	 * <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_RYAKU</td><td>第二引数addInfoの変数shozokuRyakusho</td></tr>
	 * </table><br/><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数addInfoの変数shozokuCd</td></tr>
	 * </table><br/><br/>
	 * <b>注)所属機関名等の修正は申請者情報のみの更新とし、審査結果等のテーブルは更新対象としない.</b>
	 * 
	 * @param userInfo	UserInfo
	 * @param addInfo	insertするShozokuInfo
	 * @return 第二引数と同じ情報
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuInfo)
	 */
	public synchronized ShozokuInfo insert(UserInfo userInfo, ShozokuInfo addInfo)
		throws ApplicationException {
			
		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();				

            //---------------------------------------
            //所属機関担当者情報データアクセスオブジェクト
            //---------------------------------------
            ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);

			//---------------------------------------
			//キー情報の（所属機関担当者ID）を作成
			//所属機関コード（5桁）+連番（2桁）+チェックデジット（1桁）（モジュラス10）
			//---------------------------------------
			try{
				//所属機関コード（5桁）+連番（2桁）を作成
				String key = addInfo.getShozokuCd() + dao.getSequenceNo(connection, addInfo.getShozokuCd());
				
				//所属機関担当者IDを取得（チェックデジットはアルファベット形式）				
				String shozokuTantoId = key +  CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
				addInfo.setShozokuTantoId(shozokuTantoId);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"シーケンス番号取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} catch (ApplicationException e) {
				//所属機関担当者IDの連番は09を超えた場合
				throw e;
			}
						
			//---------------------------------------
			//パスワード情報の作成
			//---------------------------------------
			try{
				//RULEINFOテーブルよりルール取得準備
				RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
				RulePk rulePk = new RulePk();
				rulePk.setTaishoId(ITaishoId.SHOZOKUTANTO);

				//パスワード取得
				String newPassword = rureInfoDao.getPassword(connection, rulePk);
				addInfo.setPassword(newPassword);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"所属機関担当者パスワード作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			}

			//---------------------------------------
			//ID登録日付の作成
			//---------------------------------------
			Date idDate = new Date();
			addInfo.setIdDate(idDate);
			
			//---------------------------------------
			//所属機関担当者情報の登録
			//---------------------------------------
			ShozokuInfo result = null;
			try{
				//登録
				dao.insertShozokuInfo(connection,addInfo);		
				//登録データの取得
				result = dao.selectShozokuInfo(connection, addInfo);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"所属機関管理データ登録中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			}
			
			//---------------------------------------
			//申請者情報の更新
			//---------------------------------------
			try{
				ShinseishaInfoDao shinseishaDao = new ShinseishaInfoDao(userInfo);
				//更新
				shinseishaDao.updateShinseishaInfo(connection, addInfo);
			}catch(DataAccessException e){
				throw new ApplicationException(
					"申請者情報データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			}
			
//			2005/04/21 追加 ここから------------------------------------------
			
			//---------------------------------------
			//部局担当者情報の追加
			//---------------------------------------
			
			//部局担当者人数が空でないとき、部局担当者情報を追加
			if(addInfo.getBukyokuNum() != null && ! addInfo.getBukyokuNum().equals("")){
				
				BukyokutantoInfoDao bukyokutantoDao = new BukyokutantoInfoDao(userInfo);
				BukyokutantoInfo bukyokuInfo;
				
				int seqNo = 0;
				try {
					//連番取得
					seqNo = Integer.parseInt(bukyokutantoDao.getSequenceNo(connection, addInfo.getShozokuCd()));
				} catch (DataAccessException e) {
					throw new ApplicationException(
							"シーケンス番号取得中にDBエラーが発生しました。",
							new ErrorInfo("errors.4001"),
							e);
				}
				
				//連番を2桁にするため
				DecimalFormat df = new DecimalFormat("00");
				
				//入力された人数分、繰り返す
				for(int i=0; i<Integer.parseInt(addInfo.getBukyokuNum()); i++){
					bukyokuInfo = new BukyokutantoInfo();
					
					//---------------------------------------
					//キー情報の（部局担当者ID）を作成
					//所属機関コード（5桁）+連番（2桁）+区分（1桁）（「1」固定）+チェックデジット（1桁）（モジュラス10）
					//---------------------------------------
					
					//所属機関コード（5桁）+連番（2桁）を作成
					//2005/07/14 区分はBを固定とする
					//String key = addInfo.getShozokuCd() + df.format(seqNo+i) + "1";
					String key = addInfo.getShozokuCd() + df.format(seqNo+i) + "B";
					
					//部局担当者IDを取得（チェックデジットはアルファベット形式）				
					String bukyokuTantoId = key +  CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
					bukyokuInfo.setBukyokutantoId(bukyokuTantoId);
								
					//---------------------------------------
					//パスワード情報の作成（デフォルトパスワード）
					//---------------------------------------
					try{
						//RULEINFOテーブルよりルール取得準備
						RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
						RulePk rulePk = new RulePk();
						rulePk.setTaishoId(ITaishoId.SHOZOKUTANTO);

						//パスワード取得
						String defaultPassword = rureInfoDao.getPassword(connection, rulePk);

						//ディフォルトパスワード設定
						bukyokuInfo.setDefaultPassword(defaultPassword);

					} catch (DataAccessException e) {
						throw new ApplicationException(
							"部局担当者のパスワード作成中にDBエラーが発生しました。",
							new ErrorInfo("errors.4001"),
							e);
					}
					
					//所属機関コードのセット
					bukyokuInfo.setShozokuCd(addInfo.getShozokuCd());
					
					try{
						//登録
						bukyokutantoDao.insertBukyokuData(connection,bukyokuInfo);
						
					} catch (DataAccessException e) {
						throw new ApplicationException(
							"部局担当者データ登録中にDBエラーが発生しました。",
							new ErrorInfo("errors.4001"),
							e);
					}
				}
			}
			
//			追加 ここまで-----------------------------------------------------
			
			//---------------------------------------
			//登録正常終了
			//---------------------------------------
			success = true;			
			
			return result;	

		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"所属機関管理データ登録中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 所属機関担当者情報を更新する。<br/><br/>
	 * 
	 * <b>1.所属機関担当者情報の更新</b><br/>
	 * 所属機関担当者テーブルを更新する。<br/>
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE SHOZOKUTANTOINFO SET
	 *     SHOZOKU_CD = ?				-- 所属機関名（コード）
	 *     ,SHOZOKU_NAME_KANJI = ?			-- 所属機関名（日本語）
	 *     ,SHOZOKU_RYAKUSHO = ?			-- 所属機関名（略称）
	 *     ,SHOZOKU_NAME_EIGO = ?			-- 所属機関名（英文）
	 *     ,SHUBETU_CD = ?				-- 機関種別
	 *     ,PASSWORD = ?				-- パスワード
	 *     ,SEKININSHA_NAME_SEI = ?			-- 責任者氏名（姓）
	 *     ,SEKININSHA_NAME_MEI = ?			-- 責任者氏名（名）
	 *     ,SEKININSHA_YAKU = ?			-- 責任者役職
	 *     ,BUKYOKU_NAME = ?			-- 担当部課名
	 *     ,KAKARI_NAME = ?				-- 担当係名
	 *     ,TANTO_NAME_SEI = ?			-- 担当者名（姓）
	 *     ,TANTO_NAME_MEI = ?			-- 担当者名（名）
	 *     ,TANTO_TEL = ?				-- 担当者部局所在地（電話番号）
	 *     ,TANTO_FAX = ?				-- 担当者部局所在地（FAX番号）
	 *     ,TANTO_EMAIL = ?				-- 担当者部局所在地（Email）
	 *     ,TANTO_EMAIL2 = ?			-- 担当者部局所在地（Email2）
	 *     ,TANTO_ZIP = ?				-- 担当者部局所在地（郵便番号）
	 *     ,TANTO_ADDRESS = ?			-- 担当者部局所在地（住所）
	 *     ,NINSHOKEY_FLG = ?			-- 認証キー発行フラグ
	 *     ,BIKO = ?				-- 備考
	 *     ,YUKO_DATE = ?				-- 有効期限
	 *     ,A.ID_DATE = ?				-- ID発行日付
	 *     ,DEL_FLG = ?				-- 削除フラグ
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 更新値
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（コード）</td><td>第二引数updateInfoの変数shozokuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（日本語）</td><td>第二引数updateInfoの変数shozokuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（略称）</td><td>第二引数updateInfoの変数shozokuRyakusho</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（英文）</td><td>第二引数updateInfoの変数shozokuNameEigo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>機関種別</td><td>第二引数updateInfoの変数shubetuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>パスワード</td><td>第二引数updateInfoの変数password</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>責任者氏名（姓）</td><td>第二引数updateInfoの変数sekininshaNameSei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>責任者氏名（名）</td><td>第二引数updateInfoの変数sekininshaNameMei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>責任者役職</td><td>第二引数updateInfoの変数sekininshaYaku</td></tr>
 	 *     <tr bgcolor="#FFFFFF"><td>担当部課名</td><td>第二引数updateInfoの変数bukyokuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当係名</td><td>第二引数updateInfoの変数kakariName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者名（姓）</td><td>第二引数updateInfoの変数tantoNameSei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者名（名）</td><td>第二引数updateInfoの変数tantoNameMei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者部局所在地（電話番号）</td><td>第二引数updateInfoの変数tantoTel</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者部局所在地（FAX番号）</td><td>第二引数updateInfoの変数tantoFax</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者部局所在地（Email）</td><td>第二引数updateInfoの変数tantoEmail</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者部局所在地（Email2）</td><td>第二引数updateInfoの変数tantoEmail2</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者部局所在地（郵便番号）</td><td>第二引数updateInfoの変数tantoZip</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者部局所在地（住所）</td><td>第二引数updateInfoの変数tantoAddress</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>認証キー発行フラグ</td><td>第二引数updateInfoの変数ninshokeyFlg</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>備考</td><td>第二引数updateInfoの変数biko</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>有効期限</td><td>第二引数updateInfoの変数yukoDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>ID発行日付</td><td>第二引数updateInfoの変数idDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>0</td></tr>
	 * </table><br/><br/>
	 * 絞込み条件
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関担当者ID</td><td>第二引数updateInfoの変数shozokuTantoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * 
	 * 
	 * <b>2.申請者情報の更新</b><br/>
	 * 申込者情報テーブルを更新する。<br/>
	 * （所属機関名等が更新されている場合があるため、申請者情報も更新処理を行う。）<br/><br/>
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE SHINSEISHAINFO SET
	 *     SHOZOKU_NAME = ?
	 *     ,SHOZOKU_NAME_EIGO = ?
	 *     ,SHOZOKU_NAME_RYAKU = ?
	 * WHERE
	 *     SHOZOKU_CD = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 更新値
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME</td><td>第二引数updateInfoの変数shozokuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_EIGO</td><td>第二引数addInfoの変数shozokuNameEigo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_RYAKU</td><td>第二引数updateInfoの変数shozokuRyakusho</td></tr>
	 * </table><br/><br/>
	 * 絞込み条件
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数updateInfoの変数shozokuCd</td></tr>
	 * </table><br/><br/>
	 * <b>注)所属機関名等の修正は申請者情報のみの更新とし、審査結果等のテーブルは更新対象としない.</b>
	 * 
	 * 
	 * @param userInfo		UserInfo
	 * @param updateInfo	更新情報(ShozokuInfo)
	 * @return なし
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuInfo)
	 */
	public void update(UserInfo userInfo, ShozokuInfo updateInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//---------------------------------------
			//所属機関担当者情報の更新
			//---------------------------------------
			try{
				ShozokuInfoDao shozokuDao = new ShozokuInfoDao(userInfo);
				shozokuDao.updateShozokuInfo(connection, updateInfo);
			}catch(DataAccessException e){
				throw new ApplicationException(
					"所属機関情報データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			}

			//---------------------------------------
			//申請者情報の更新
			//---------------------------------------
			try{
				ShinseishaInfoDao shinseishaDao = new ShinseishaInfoDao(userInfo);
				shinseishaDao.updateShinseishaInfo(connection, updateInfo);
			}catch(DataAccessException e){
				throw new ApplicationException(
					"申請者情報データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			}
			
//			2005/04/21 追加 ここから------------------------------------------

			//---------------------------------------
			//部局担当者情報の追加登録
			//---------------------------------------
			if(updateInfo.getAddBukyokuNum() > 0){
				BukyokutantoInfoDao bukyokutantoDao = new BukyokutantoInfoDao(userInfo);
				BukyokutantoInfo bukyokuInfo;
				
				int seqNo = 0;
				try {
					//連番取得
					seqNo = Integer.parseInt(bukyokutantoDao.getSequenceNo(connection, updateInfo.getShozokuCd()));
				} catch (DataAccessException e) {
					throw new ApplicationException(
							"シーケンス番号取得中にDBエラーが発生しました。",
							new ErrorInfo("errors.4001"),
							e);
				}
				
				//連番を2桁にするため
				DecimalFormat df = new DecimalFormat("00");
				
				//部局担当者人数の増分だけ、繰り返す
				for(int i=0; i<updateInfo.getAddBukyokuNum(); i++){
					bukyokuInfo = new BukyokutantoInfo();
					
					//---------------------------------------
					//キー情報の（部局担当者ID）を作成
					//所属機関コード（5桁）+連番（2桁）+区分（1桁）（「1」固定）+チェックデジット（1桁）（モジュラス10）
					//---------------------------------------
					
					//連番は99を超えたらエラーとする 2005/07/14
					if (seqNo+i > 99){
						throw new ApplicationException(
								"部局担当者IDの連番は99を超えました。",
								new ErrorInfo("errors.4001"));
					}
					
					//所属機関コード（5桁）+連番（2桁）を作成
					//区分はBを固定とする 2005/07/14
					//String key = updateInfo.getShozokuCd() + df.format(seqNo+i) + "1";
					String key = updateInfo.getShozokuCd() + df.format(seqNo+i) + "B";
					
					//部局担当者IDを取得（チェックデジットはアルファベット形式）				
					String bukyokuTantoId = key +  CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
					bukyokuInfo.setBukyokutantoId(bukyokuTantoId);
								
					//---------------------------------------
					//パスワード情報の作成（デフォルトパスワード）
					//---------------------------------------
					try{
						//RULEINFOテーブルよりルール取得準備
						RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
						RulePk rulePk = new RulePk();
						rulePk.setTaishoId(ITaishoId.SHOZOKUTANTO);
						//パスワード取得
						String defaultPassword = rureInfoDao.getPassword(connection, rulePk);
						bukyokuInfo.setDefaultPassword(defaultPassword);
					} catch (DataAccessException e) {
						throw new ApplicationException(
							"部局担当者のパスワード作成中にDBエラーが発生しました。",
							new ErrorInfo("errors.4001"),
							e);
					}
						
					//所属機関コードのセット
					bukyokuInfo.setShozokuCd(updateInfo.getShozokuCd());
					
					try{
						//登録
						bukyokutantoDao.insertBukyokuData(connection,bukyokuInfo);
					
						} catch (DataAccessException e) {
						throw new ApplicationException(
							"部局担当者データ登録中にDBエラーが発生しました。",
							new ErrorInfo("errors.4001"),
							e);
					}
				}
			}
			
//			追加 ここまで-----------------------------------------------------
			
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
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
					"所属機関管理データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 所属機関担当者情報を削除する。<br/><br/>
	 *  
	 * 以下のSQLを実行し、SHOZOKUTANTOINFOから、対象となるレコードを論理削除する。（バインド変数はSQLの下の表を参照）
	 * 
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE SHOZOKUTANTOINFO SET
	 *     DEL_FLG = 1			-- 削除フラグ
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>deleteInfo</td><td>第二引数addInfoの変数shozokuTantoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param deleteInfo	削除情報(ShozokuInfo)
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuInfo)
	 */
	public void delete(UserInfo userInfo, ShozokuInfo deleteInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();			
			//---------------------------------------
			//所属機関担当者情報の更新
			//---------------------------------------
			ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);
			dao.deleteFlgShozokuInfo(connection, deleteInfo);
			
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"所属機関管理データ削除中にDBエラーが発生しました。",
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
					"所属機関管理データ削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 所属機関担当者情報を取得する。<br/><br/>
	 * 
	 * 所属機関担当者テーブルから、該当レコードを取得する。<br/><br/>
	 * 以下のSQLを実行し、取得した情報を返却する。（バインド変数はSQLの下の表を参照）
	 * ただし、該当するレコードがない場合は例外をthrowする。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- 所属機関担当者ID
	 *     ,A.SHOZOKU_CD"				-- 所属機関名（コード）
	 *     ,A.SHOZOKU_NAME_KANJI			-- 所属機関名（日本語）
	 *     ,A.SHOZOKU_RYAKUSHO			-- 所属機関名（略称）
	 *     ,A.SHOZOKU_NAME_EIGO			-- 所属機関名（英文）
	 *     ,A.SHUBETU_CD				-- 機関種別
	 *     ,A.PASSWORD				-- パスワード
	 *     ,A.SEKININSHA_NAME_SEI			-- 責任者氏名（姓）
	 *     ,A.SEKININSHA_NAME_MEI			-- 責任者氏名（名）
	 *     ,A.SEKININSHA_YAKU			-- 責任者役職
	 *     ,A.BUKYOKU_NAME				-- 担当部課名
	 *     ,A.KAKARI_NAME				-- 担当係名
	 *     ,A.TANTO_NAME_SEI			-- 担当者名（姓）
	 *     ,A.TANTO_NAME_MEI			-- 担当者名（名）
	 *     ,A.TANTO_TEL				-- 担当者部局所在地（電話番号）
	 *     ,A.TANTO_FAX				-- 担当者部局所在地（FAX番号）
	 *     ,A.TANTO_EMAIL				-- 担当者部局所在地（Email）
	 *     ,A.TANTO_EMAIL2				-- 担当者部局所在地（Email2）
	 *     ,A.TANTO_ZIP				-- 担当者部局所在地（郵便番号）
	 *     ,A.TANTO_ADDRESS				-- 担当者部局所在地（住所）
	 *     ,A.NINSHOKEY_FLG				-- 認証キー発行フラグ
	 *     ,A.BIKO				-- 備考
	 *     ,A.YUKO_DATE				-- 有効期限
	 *     ,A.ID_DATE				-- ID発行日付
	 *     ,A.DEL_FLG				-- 削除フラグ
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKUTANTO_ID</td><td>第二引数pkInfoの変数shozokuTantoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo	UserInfo
	 * @param pkInfo	検索を行う所属機関情報のPK情報（ShozokuPk）
	 * @return 所属機関担当者情報
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuInfo)
	 */
	public ShozokuInfo select(UserInfo userInfo, ShozokuPk pkInfo)
		throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);
			return dao.selectShozokuInfo(connection, pkInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"所属機関管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * 所属機関マスタ情報を取得する。<br/><br/>
	 * 
	 * 所属機関マスタテーブルから、該当レコードを取得しKikanInfoへ格納後返却する。<br/><br/>
	 * 以下のSQLを実行し、取得した情報を返却する。（バインド変数はSQLの下の表を参照）
	 * ただし、レコードの取得が出来なかった場合は例外をthrowする。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     *
	 * FROM
	 *     MASTER_KIKAN
	 * WHERE
	 *     SHOZOKU_CD = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数pkInfoの変数shozokuCd</td></tr>
	 * </table><br/>
	 * 取得する情報
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>KikanInfoの格納先変数名</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHUBETU_CD</td><td>shubetuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KIKAN_KUBUN</td><td>kikanKubun</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>shozokuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_KANJI</td><td>shozokuNameKanji</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_RYAKUSHO</td><td>shozokuRyakusho</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_EIGO</td><td>shozokuNameEigo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_ZIP</td><td>shozokuZip</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_ADDRESS1</td><td>shozokuAddress1</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_ADDRESS2</td><td>shozokuAddress2</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_TEL</td><td>shozokuTel</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_FAX</td><td>shozokuFax</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_DAIHYO_NAME</td><td>shozokuDaihyoName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BIKO</td><td>biko</td></tr>
	 * </table><br/>
	 * 
	 * <br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param kikanInfo		情報の取得をおこなう所属機関情報（KikanInfo）
	 * @return 所属機関マスタ情報
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#selectMaster(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShozokuPk)
	 */
	public KikanInfo select(UserInfo userInfo, KikanInfo kikanInfo)
		 throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return new MasterKikanInfoDao(userInfo).selectKikanInfo(connection,kikanInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"所属機関管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			 DatabaseUtil.closeConnection(connection);
	 	}
	}

	/**
	 * ページ情報を取得する。<br/><br/>
	 * 
	 * 第二引数で渡された検索条件に基づき、所属機関担当者テーブルを検索する。<br/><br/>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * 検索結果をPageオブジェクトに格納し、返却する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- 所属機関担当者ID
	 *     ,A.SHOZOKU_CD"				-- 所属機関名（コード）
	 *     ,A.SHOZOKU_NAME_KANJI			-- 所属機関名（日本語）
	 *     ,A.SHOZOKU_RYAKUSHO			-- 所属機関名（略称）
	 *     ,A.SHOZOKU_NAME_EIGO			-- 所属機関名（英文）
	 *     ,A.SHUBETU_CD				-- 機関種別
	 *     ,A.PASSWORD				-- パスワード
	 *     ,A.SEKININSHA_NAME_SEI			-- 責任者氏名（姓）
	 *     ,A.SEKININSHA_NAME_MEI			-- 責任者氏名（名）
	 *     ,A.SEKININSHA_YAKU			-- 責任者役職
	 *     ,A.BUKYOKU_NAME				-- 担当部課名
	 *     ,A.KAKARI_NAME				-- 担当係名
	 *     ,A.TANTO_NAME_SEI			-- 担当者名（姓）
	 *     ,A.TANTO_NAME_MEI			-- 担当者名（名）
	 *     ,A.TANTO_TEL				-- 担当者部局所在地（電話番号）
	 *     ,A.TANTO_FAX				-- 担当者部局所在地（FAX番号）
	 *     ,A.TANTO_EMAIL				-- 担当者部局所在地（Email）
	 *     ,A.TANTO_EMAIL2				-- 担当者部局所在地（Email2）
	 *     ,A.TANTO_ZIP				-- 担当者部局所在地（郵便番号）
	 *     ,A.TANTO_ADDRESS				-- 担当者部局所在地（住所）
	 *     ,A.NINSHOKEY_FLG				-- 認証キー発行フラグ
	 *     ,A.BIKO				-- 備考
	 *     ,A.YUKO_DATE				-- 有効期限
	 *     ,A.ID_DATE				-- ID発行日付
	 *     ,BUKYOKU_NUM				-- 部局担当者人数
	 *     ,A.DEL_FLG				-- 削除フラグ
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     DEL_FLG = 0
	 *	
	 *				<b><span style="color:#002288">－－動的検索条件－－</span></b>
	 * 
	 * ORDER BY SHOZOKU_CD
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>機関種別コード</td><td>ShubetuCd</td><td>AND SHUBETU_CD = '機関種別コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（コード）</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '所属機関名（コード）'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（日本語）</td><td>ShozokuNameKanji</td><td>AND SHOZOKU_NAME_KANJI LIKE '%所属機関名（日本語）'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者ID</td><td>ShozokuTantoId</td><td>AND SHOZOKUTANTO_ID = '担当者ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者名（姓）</td><td>TantoNameSei</td><td>AND TANTO_NAME_SEI LIKE '%担当者名（姓）'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者名（名）</td><td>TantoNameMei</td><td>AND TANTO_NAME_MEI LIKE '%担当者名（名）'</td></tr>
	 * </table><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	検索条件（ShozokuSearchInfo）
	 * @return 所属機関担当者情報を格納したPageオブジェクト
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuSearchInfo)
	 */
	public Page search(UserInfo userInfo, ShozokuSearchInfo searchInfo)
		throws ApplicationException {

		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		
		String select = 
			"SELECT *  FROM SHOZOKUTANTOINFO A WHERE DEL_FLG = 0";	
			
		StringBuffer query = new StringBuffer(select);
		
		if(searchInfo.getShubetuCd() != null && !searchInfo.getShubetuCd().equals("")){			//機関種別コード（完全一致）
			query.append(" AND SHUBETU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShubetuCd()) + "'");
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){			//所属機関名（コード）（完全一致）
			query.append(" AND SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){		//所属機関名（日本語）（部分一致）
			query.append(" AND SHOZOKU_NAME_KANJI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");			
		}
		if(searchInfo.getShozokuTantoId() != null && !searchInfo.getShozokuTantoId().equals("")){	//担当者ID（完全一致）
			query.append(" AND SHOZOKUTANTO_ID = '" + EscapeUtil.toSqlString(searchInfo.getShozokuTantoId()) + "'");			
		}
		if(searchInfo.getTantoNameSei() != null && !(searchInfo.getTantoNameSei()).equals("")){	//担当者名（姓）（部分一致）
			query.append(" AND TANTO_NAME_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameSei()) + "%'");			
		}
		if(searchInfo.getTantoNameMei() != null && !(searchInfo.getTantoNameMei()).equals("")){	//担当者名（名）（部分一致）
			query.append(" AND TANTO_NAME_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameMei()) + "%'");			
		}	
		//ソート順（所属機関名（コード）の昇順）
		query.append(" ORDER BY SHOZOKU_CD");		
		
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
			log.error("所属機関管理データ検索中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"所属機関管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

	}
	
	/**
	 * CSV出力データ作成.<br />
	 * 
	 * 所属機関担当者情報をCSV出力するために、検索条件に該当するレコードをListへ格納し、呼び出し元へ返却する。
	 * その際、各レコード情報は列ごとにListへ格納されたうえで返却するListへ格納される。(Listによる2次元配列構造)
	 * なお、返却するListの一つ目の要素は、ヘッダー情報が格納されている。
	 * <br />
	 * <br />
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID "所属機関担当者ID"			-- 所属機関担当者ID
	 *     ,A.SHOZOKU_CD "所属機関名（コード）"			-- 所属機関名（コード）
	 *     ,A.SHUBETU_CD "機関種別コード"			-- 機関種別コード
	 *     ,A.SHOZOKU_NAME_KANJI "所属機関名（和文）"		-- 所属機関名（和文）
	 *     ,A.SHOZOKU_RYAKUSHO "所属機関名（略称）"		-- 所属機関名（略称）
	 *     ,A.SHOZOKU_NAME_EIGO "所属機関名（英文）"		-- 所属機関名（英文）
	 *     ,A.SEKININSHA_NAME_SEI "責任者名（姓）"		-- 責任者名（姓）
	 *     ,A.SEKININSHA_NAME_MEI "責任者名（名）"		-- 責任者名（名）
	 *     ,A.SEKININSHA_YAKU "責任者役職"			-- 責任者役職
	 *     ,A.BUKYOKU_NAME "担当部課名"			-- 担当部課名
	 *     ,A.KAKARI_NAME "担当者係名"			-- 担当者係名
	 *     ,A.TANTO_NAME_SEI "担当者名（姓）"			-- 担当者名（姓）
	 *     ,A.TANTO_NAME_MEI "担当者名（名）"			-- 担当者名（名）
	 *     ,A.TANTO_TEL "担当者連絡先（電話番号）"		-- 担当者部局所在地（電話番号）
	 *     ,A.TANTO_FAX "担当者連絡先（FAX番号）"		-- 担当者部局所在地（FAX番号）
	 *     ,A.TANTO_EMAIL "担当者連絡先（Email）"		-- 担当者部局所在地（Email）
	 *     ,A.TANTO_EMAIL2 "担当者連絡先（Email2）"		-- 担当者部局所在地（Email2）
	 *     ,A.TANTO_ZIP "担当者連絡先（郵便番号）"		-- 担当者部局所在地（郵便番号）
	 *     ,A.TANTO_ADDRESS "担当者連絡先（住所）"		-- 担当者部局所在地（住所）
	 *     ,A.BIKO "備考"					-- 備考
	 *     ,TO_CHAR(A.YUKO_DATE,'YYYY/MM/DD') "有効期限"		-- 有効期限
	 *     ,TO_CHAR(A.ID_DATE,'YYYY/MM/DD') "ID発行日付"		-- ID発行日付
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * 
	 * WHERE
	 *     DEL_FLG = 0
	 *	
	 *				<b><span style="color:#002288">－－動的検索条件－－</span></b>
	 * 
	 * ORDER BY SHOZOKU_CD
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>機関種別コード</td><td>ShubetuCd</td><td>AND SHUBETU_CD = '機関種別コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（コード）</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '所属機関名（コード）'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（日本語）</td><td>ShozokuNameKanji</td><td>AND SHOZOKU_NAME_KANJI LIKE '%所属機関名（日本語）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者ID</td><td>ShozokuTantoId</td><td>AND SHOZOKUTANTO_ID = '担当者ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者名（姓）</td><td>TantoNameSei</td><td>AND TANTO_NAME_SEI LIKE '%担当者名（姓）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者名（名）</td><td>TantoNameMei</td><td>AND TANTO_NAME_MEI LIKE '%担当者名（名）%'</td></tr>
	 * </table><br/>
	 * 
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#search(jp.go.jsps.kaken.mode
	 * l.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuSearchInfo)
	 */
	public List searchCsvData(UserInfo userInfo, ShozokuSearchInfo searchInfo)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		//2005.10.04 iso 申請→応募、所属機関→所属研究機関、コード→番号、申請書→研究計画調書
		String select =
			"SELECT "
				+ " A.SHOZOKUTANTO_ID \"所属研究機関担当者ID\""			//所属機関担当者ID
				+ ",A.SHOZOKU_CD \"所属研究機関名（番号）\""				//所属機関名（コード）
				+ ",A.SHUBETU_CD \"所属研究機関種別番号\""				//機関種別コード
				+ ",A.SHOZOKU_NAME_KANJI \"所属研究機関名（和文）\""		//所属機関名（和文）
				+ ",A.SHOZOKU_RYAKUSHO \"所属研究機関名（略称）\""		//所属機関名（略称）
				+ ",A.SHOZOKU_NAME_EIGO \"所属研究機関名（英文）\""		//所属機関名（英文）
				+ ",A.SEKININSHA_NAME_SEI \"責任者名（姓）\""			//責任者名（姓）
				+ ",A.SEKININSHA_NAME_MEI \"責任者名（名）\""			//責任者名（名）
				+ ",A.SEKININSHA_YAKU \"責任者役職\""					//責任者役職
				+ ",A.BUKYOKU_NAME \"担当部課名\""						//担当部課名
				+ ",A.KAKARI_NAME \"担当者係名\""						//担当者係名
				+ ",A.TANTO_NAME_SEI \"担当者名（姓）\""					//担当者名（姓）
				+ ",A.TANTO_NAME_MEI \"担当者名（名）\""					//担当者名（名）
				+ ",A.TANTO_TEL \"担当者連絡先（電話番号）\""				//担当者部局所在地（電話番号）
				+ ",A.TANTO_FAX \"担当者連絡先（FAX番号）\""				//担当者部局所在地（FAX番号）
				+ ",A.TANTO_EMAIL \"担当者連絡先（Email）\""				//担当者部局所在地（Email）
				+ ",A.TANTO_EMAIL2 \"担当者連絡先（Email2）\""			//担当者部局所在地（Email2）
				+ ",A.TANTO_ZIP \"担当者連絡先（郵便番号）\""				//担当者部局所在地（郵便番号）
				+ ",A.TANTO_ADDRESS \"担当者連絡先（住所）\""				//担当者部局所在地（住所）
				+ ",A.BIKO \"備考\""										//備考
//				2005/04/22 追加 ここから----------
//				理由:部局担当者人数追加のため
				+ ",A.BUKYOKU_NUM \"部局担当者人数\""					//部局担当者人数
//				2005/04/22 追加 ここまで----------
				+ ",TO_CHAR(A.YUKO_DATE,'YYYY/MM/DD') \"有効期限\""		//有効期限
				//2005.08.10 iso ID発行日付の追加
				+ ",TO_CHAR(A.ID_DATE,'YYYY/MM/DD') \"ID発行日付\""		//ID発行日付
				+ " FROM SHOZOKUTANTOINFO A"
				+ " WHERE DEL_FLG = 0";																		

		StringBuffer query = new StringBuffer(select);

		if(searchInfo.getShubetuCd() != null && !searchInfo.getShubetuCd().equals("")){			//機関種別コード（完全一致）
			query.append(" AND SHUBETU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShubetuCd()) + "'");
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){			//所属機関名（コード）（完全一致）
			query.append(" AND SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){		//所属機関名（日本語）（部分一致）
			query.append(" AND SHOZOKU_NAME_KANJI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");			
		}
		if(searchInfo.getShozokuTantoId() != null && !searchInfo.getShozokuTantoId().equals("")){	//担当者ID（完全一致）
			query.append(" AND SHOZOKUTANTO_ID = '" + EscapeUtil.toSqlString(searchInfo.getShozokuTantoId()) + "'");		
		}
		if(searchInfo.getTantoNameSei() != null && !(searchInfo.getTantoNameSei()).equals("")){	//担当者名（姓）（部分一致）
			query.append(" AND TANTO_NAME_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameSei()) + "%'");			
		}
		if(searchInfo.getTantoNameMei() != null && !(searchInfo.getTantoNameMei()).equals("")){	//担当者名（名）（部分一致）
			query.append(" AND TANTO_NAME_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameMei()) + "%'");			
		}

		//ソート順（所属機関名（コード）の昇順）
		query.append(" ORDER BY SHOZOKU_CD");

		if(log.isDebugEnabled()){
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
	
	//2005/04/26 追加 -------------------------------------------------------------------------------------------ここから
	//理由 システム管理者の所属機関担当者一覧は部局担当者の情報も同時に出力する必要があるため
	/**
	 * CSV出力データ作成.<br />
	 * 
	 * 所属機関担当者情報をCSV出力するために、検索条件に該当するレコードをListへ格納し、呼び出し元へ返却する。
	 * その際、各レコード情報は列ごとにListへ格納されたうえで返却するListへ格納される。(Listによる2次元配列構造)
	 * なお、返却するListの一つ目の要素は、ヘッダー情報が格納されている。
	 * <br />
	 * <br />
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID "所属機関担当者ID"			-- 所属機関担当者ID
	 *     ,A.SHOZOKU_CD "所属機関名（コード）"			-- 所属機関名（コード）
	 *     ,A.SHUBETU_CD "機関種別コード"			-- 機関種別コード
	 *     ,A.SHOZOKU_NAME_KANJI "所属機関名（和文）"		-- 所属機関名（和文）
	 *     ,A.SHOZOKU_RYAKUSHO "所属機関名（略称）"		-- 所属機関名（略称）
	 *     ,A.SHOZOKU_NAME_EIGO "所属機関名（英文）"		-- 所属機関名（英文）
	 *     ,A.SEKININSHA_NAME_SEI "責任者名（姓）"		-- 責任者名（姓）
	 *     ,A.SEKININSHA_NAME_MEI "責任者名（名）"		-- 責任者名（名）
	 *     ,A.SEKININSHA_YAKU "責任者役職"			-- 責任者役職
	 *     ,A.BUKYOKU_NAME "担当部課名"			-- 担当部課名
	 *     ,A.KAKARI_NAME "担当者係名"			-- 担当者係名
	 *     ,A.TANTO_NAME_SEI "担当者名（姓）"			-- 担当者名（姓）
	 *     ,A.TANTO_NAME_MEI "担当者名（名）"			-- 担当者名（名）
	 *     ,A.TANTO_TEL "担当者連絡先（電話番号）"		-- 担当者部局所在地（電話番号）
	 *     ,A.TANTO_FAX "担当者連絡先（FAX番号）"		-- 担当者部局所在地（FAX番号）
	 *     ,A.TANTO_EMAIL "担当者連絡先（Email）"		-- 担当者部局所在地（Email）
	 *     ,A.TANTO_EMAIL2 "担当者連絡先（Email2）"		-- 担当者部局所在地（Email2）
	 *     ,A.TANTO_ZIP "担当者連絡先（郵便番号）"		-- 担当者部局所在地（郵便番号）
	 *     ,A.TANTO_ADDRESS "担当者連絡先（住所）"		-- 担当者部局所在地（住所）
	 *     ,A.BIKO "備考"					-- 備考
	 *     ,TO_CHAR(A.YUKO_DATE,'YYYY/MM/DD') "有効期限"		-- 有効期限
	 *     ,TO_CHAR(A.ID_DATE,'YYYY/MM/DD') "ID発行日付"		-- ID発行日付
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * 
	 * WHERE
	 *     DEL_FLG = 0
	 *	
	 *				<b><span style="color:#002288">－－動的検索条件－－</span></b>
	 * 
	 * ORDER BY SHOZOKU_CD
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>機関種別コード</td><td>ShubetuCd</td><td>AND SHUBETU_CD = '機関種別コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（コード）</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '所属機関名（コード）'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（日本語）</td><td>ShozokuNameKanji</td><td>AND SHOZOKU_NAME_KANJI LIKE '%所属機関名（日本語）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者ID</td><td>ShozokuTantoId</td><td>AND SHOZOKUTANTO_ID = '担当者ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者名（姓）</td><td>TantoNameSei</td><td>AND TANTO_NAME_SEI LIKE '%担当者名（姓）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者名（名）</td><td>TantoNameMei</td><td>AND TANTO_NAME_MEI LIKE '%担当者名（名）%'</td></tr>
	 * </table><br/>
	 * 
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#search(jp.go.jsps.kaken.mode
	 * l.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuSearchInfo)
	 */
	public List searchCsvDataForSysMng(UserInfo userInfo, ShozokuSearchInfo searchInfo)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT "
			+ "	 INFO.SHOZOKUTANTO_ID \"所属研究機関担当者ID\" "			//所属機関担当者ID
			+ "	,INFO.SHOZOKU_CD \"所属研究機関名（番号）\" "				//所属機関名（コード）
			+ "	,INFO.SHUBETU_CD \"所属研究機関種別コード\" "				//機関種別コード
			+ "	,INFO.SHOZOKU_NAME_KANJI \"所属研究機関名（和文）\" "		//所属機関名（和文）
			+ "	,INFO.SHOZOKU_RYAKUSHO \"所属研究機関名（略称）\" "		//所属機関名（略称）
			+ "	,INFO.SHOZOKU_NAME_EIGO \"所属研究機関名（英文）\" "		//所属機関名（英文）
			+ "	,INFO.SEKININSHA_NAME_SEI \"責任者名（姓）\" "			//責任者名（姓）
			+ "	,INFO.SEKININSHA_NAME_MEI \"責任者名（名）\" "			//責任者名（名）
			+ "	,INFO.SEKININSHA_YAKU \"責任者役職\" "					//責任者役職
			+ "	,INFO.BUKYOKU_NAME \"担当部課名\" "						//担当部課名
			+ "	,INFO.KAKARI_NAME \"担当者係名\" "						//担当者係名
			+ "	,INFO.TANTO_NAME_SEI \"担当者名（姓）\" "				//担当者名（姓）
			+ "	,INFO.TANTO_NAME_MEI \"担当者名（名）\" "				//担当者名（名）
			+ "	,INFO.TANTO_TEL \"担当者連絡先（電話番号）\" "			//担当者部局所在地（電話番号）
			+ "	,INFO.TANTO_FAX \"担当者連絡先（FAX番号）\" "				//担当者部局所在地（FAX番号）
			+ "	,INFO.TANTO_EMAIL \"担当者連絡先（Email）\" "				//担当者部局所在地（Email）
			+ "	,INFO.TANTO_EMAIL2 \"担当者連絡先（Email2）\" "			//担当者部局所在地（Email2）
			+ "	,INFO.TANTO_ZIP \"担当者連絡先（郵便番号）\" "			//担当者部局所在地（郵便番号）
			+ "	,INFO.TANTO_ADDRESS \"担当者連絡先（住所）\" "			//担当者部局所在地（住所）
			+ "	,INFO.BIKO \"備考\" "									//備考
			+ "	,INFO.BUKYOKU_NUM \"部局担当者人数\" "					//部局担当者人数
			+ "	,INFO.YUKO_DATE \"有効期限\" "							//有効期限
			+ "	,INFO.ID_DATE \"ID発行日付\" "							//ID発行日付
			+ " ,INFO.DEL_FLG \"削除フラグ\" "							//削除フラグ
			+ "FROM( "
			+ "	SELECT "
			+ "		 S.SHOZOKUTANTO_ID "
			+ "		,S.SHOZOKU_CD "
			+ "		,S.SHUBETU_CD "
			+ "		,S.SHOZOKU_NAME_KANJI "
			+ "		,S.SHOZOKU_RYAKUSHO "
			+ "		,S.SHOZOKU_NAME_EIGO "
			+ "		,S.SEKININSHA_NAME_SEI "
			+ "		,S.SEKININSHA_NAME_MEI "
			+ "		,S.SEKININSHA_YAKU "
			+ "		,S.BUKYOKU_NAME "
			+ "		,S.KAKARI_NAME "
			+ "		,S.TANTO_NAME_SEI "
			+ "		,S.TANTO_NAME_MEI "
			+ "		,S.TANTO_TEL "
			+ "		,S.TANTO_FAX "
			+ "		,S.TANTO_EMAIL "
			+ "		,S.TANTO_EMAIL2 "
			+ "		,S.TANTO_ZIP "
			+ "		,S.TANTO_ADDRESS "
			+ "		,S.BIKO "
			+ "		,S.BUKYOKU_NUM "
			+ "		,TO_CHAR(S.YUKO_DATE,'YYYY/MM/DD') YUKO_DATE "
			//2005.08.10 iso ID発行日付の追加
			+ "		,TO_CHAR(S.ID_DATE,'YYYY/MM/DD') ID_DATE "
			+ "		,S.DEL_FLG "
			+ "	FROM "
			+ "		SHOZOKUTANTOINFO S "
			+ "	WHERE "
			+ "		S.DEL_FLG = 0 ";

		StringBuffer query = new StringBuffer(select);

		if(searchInfo.getShubetuCd() != null && !searchInfo.getShubetuCd().equals("")){			//機関種別コード（完全一致）
			query.append(" AND S.SHUBETU_CD = " + EscapeUtil.toSqlString(searchInfo.getShubetuCd()) );
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){			//所属機関名（コード）（完全一致）
			query.append(" AND S.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){		//所属機関名（日本語）（部分一致）
			query.append(" AND S.SHOZOKU_NAME_KANJI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");			
		}
		if(searchInfo.getTantoNameSei() != null && !(searchInfo.getTantoNameSei()).equals("")){	//担当者名（姓）（部分一致）
			query.append(" AND S.TANTO_NAME_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameSei()) + "%'");			
		}
		if(searchInfo.getTantoNameMei() != null && !(searchInfo.getTantoNameMei()).equals("")){	//担当者名（名）（部分一致）
			query.append(" AND S.TANTO_NAME_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameMei()) + "%'");			
		}

		select =
			"	UNION "
			+ "	SELECT "
			+ "		 B.BUKYOKUTANTO_ID "
			+ "		,B.SHOZOKU_CD "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,B.BUKA_NAME "
			+ "		,B.KAKARI_NAME "
			+ "		,B.TANTO_NAME_SEI "
			+ "		,B.TANTO_NAME_MEI "
			+ "		,B.BUKYOKU_TEL "
			+ "		,B.BUKYOKU_FAX "
			+ "		,B.BUKYOKU_EMAIL "
			+ "		,NULL "
			+ "		,B.BUKYOKU_ZIP "
			+ "		,B.BUKYOKU_ADDRESS "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,NULL "
			//2005.08.10 iso ID発行日付の追加
			+ "		,NULL "
			+ "		,B.DEL_FLG "
			+ "	FROM "
			+ "		BUKYOKUTANTOINFO B "
			+ "			INNER JOIN SHOZOKUTANTOINFO S "
			+ "			ON S.SHOZOKU_CD = B.SHOZOKU_CD "
			+ "			AND S.DEL_FLG = 0 "
			+ "	WHERE "
			+ "		B.REGIST_FLG = 1 ";
		
		query.append(select);
		
		if(searchInfo.getShubetuCd() != null && !searchInfo.getShubetuCd().equals("")){			//機関種別コード（完全一致）
			query.append(" AND S.SHUBETU_CD = " + EscapeUtil.toSqlString(searchInfo.getShubetuCd()) );
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){			//所属機関名（コード）（完全一致）
			query.append(" AND S.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){		//所属機関名（日本語）（部分一致）
			query.append(" AND S.SHOZOKU_NAME_KANJI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");			
		}
		if((searchInfo.getTantoNameSei() != null && !(searchInfo.getTantoNameSei()).equals(""))
			||(searchInfo.getTantoNameMei() != null && !(searchInfo.getTantoNameMei()).equals(""))){	//担当者名（姓）、担当者名（名）
			//部局担当者の情報が検索によって選択されないようにする
			query.append(" AND B.BUKYOKUTANTO_ID = NULL ");
		}
		
		select =
			"  ) INFO "
			+ "WHERE "
			+ "	INFO.SHOZOKU_CD IS NOT NULL ";

		query.append(select);
		
		if(searchInfo.getBukyokuSearchFlg() != null && searchInfo.getBukyokuSearchFlg().equals("1")){
			query.append(" AND INFO.DEL_FLG = 1");
		}
		if(searchInfo.getBukyokuTantoId() != null && !(searchInfo.getBukyokuTantoId()).equals("")){	//部局担当者ID（完全一致）
			query.append(" AND INFO.SHOZOKUTANTO_ID = '" + EscapeUtil.toSqlString(searchInfo.getBukyokuTantoId()) + "'");
		}
		if(searchInfo.getShozokuTantoId() != null && !searchInfo.getShozokuTantoId().equals("")){	//担当者ID（完全一致）
			query.append(" AND INFO.SHOZOKUTANTO_ID = '" + EscapeUtil.toSqlString(searchInfo.getShozokuTantoId()) + "'");			
		}

		//ソート順（所属機関名（コード）の昇順）
		query.append(" ORDER BY INFO.SHOZOKU_CD,INFO.SHUBETU_CD,INFO.SHOZOKUTANTO_ID");

		if(log.isDebugEnabled()){
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
	//2005/04/26 追加 -------------------------------------------------------------------------------------------ここまで

	/**
	 * 所属機関担当者情報の数を取得する。<br/><br/>
	 * 
	 * 所属機関担当者テーブルから、指定された所属機関コードのレコード件数を取得する。<br/><br/>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     COUNT(*)
	 * FROM
	 *     SHOZOKUTANTOINFO
	 * WHERE
	 *     SHOZOKU_CD = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数shozokuCd</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param shozokuCd		所属機関コード
	 * @return レコード件数
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo,  java.lang.String)
	 */
	public int select(UserInfo userInfo, String shozokuCd) throws ApplicationException {
		

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);
			return dao.countShozokuInfo(connection, shozokuCd);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"所属機関管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * パスワードを変更する。<br/><br/>
	 * 
	 * <b>1.所属機関担当者情報の取得</b><br/>
	 * 　所属機関担当者テーブルから、引数で指定されるレコード情報を取得する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- 所属機関担当者ID
	 *     ,A.SHOZOKU_CD"				-- 所属機関名（コード）
	 *     ,A.SHOZOKU_NAME_KANJI			-- 所属機関名（日本語）
	 *     ,A.SHOZOKU_RYAKUSHO			-- 所属機関名（略称）
	 *     ,A.SHOZOKU_NAME_EIGO			-- 所属機関名（英文）
	 *     ,A.SHUBETU_CD				-- 機関種別
	 *     ,A.PASSWORD				-- パスワード
	 *     ,A.SEKININSHA_NAME_SEI			-- 責任者氏名（姓）
	 *     ,A.SEKININSHA_NAME_MEI			-- 責任者氏名（名）
	 *     ,A.SEKININSHA_YAKU			-- 責任者役職
	 *     ,A.BUKYOKU_NAME				-- 担当部課名
	 *     ,A.KAKARI_NAME				-- 担当係名
	 *     ,A.TANTO_NAME_SEI			-- 担当者名（姓）
	 *     ,A.TANTO_NAME_MEI			-- 担当者名（名）
	 *     ,A.TANTO_TEL				-- 担当者部局所在地（電話番号）
	 *     ,A.TANTO_FAX				-- 担当者部局所在地（FAX番号）
	 *     ,A.TANTO_EMAIL				-- 担当者部局所在地（Email）
	 *     ,A.TANTO_EMAIL2				-- 担当者部局所在地（Email2）
	 *     ,A.TANTO_ZIP				-- 担当者部局所在地（郵便番号）
	 *     ,A.TANTO_ADDRESS				-- 担当者部局所在地（住所）
	 *     ,A.NINSHOKEY_FLG				-- 認証キー発行フラグ
	 *     ,A.BIKO				-- 備考
	 *     ,A.YUKO_DATE				-- 有効期限
	 *     ,A.ID_DATE				-- ID発行日付
	 *     ,A.DEL_FLG				-- 削除フラグ
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKUTANTO_ID</td><td>第二引数pkInfoの変数shozokuTantoId</td></tr>
	 * </table><br/><br/> 
	 * 
	 * <b>2.現在のパスワードをチェック</b><br/>
	 * 　1.で取得した所属機関担当者情報の現在のパスワードと、第三引数oldPasswordを比較する。<br/>
	 * 　一致しないとき、例外をthrowする。<br/><br/>
	 * 
	 * <b>3.現在のパスワードを更新</b><br/>
	 * 　パスワードを新しいパスワードへ変更するために、所属機関担当者テーブルを更新する。<br/><br/>
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE SHOZOKUTANTOINFO SET
	 *     PASSWORD = ?
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 変更値
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>PASSWORD</td><td>第四引数newPassword</td></tr>
	 * </table><br/>
	 * 絞込み条件
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKUTANTO_ID</td><td>第二引数pkInfoの変数shozokuTantoId</td></tr>
	 * </table><br/><br/> 
	 * 
	 * @param userInfo		UserInfo
	 * @param pkInfo 		パスワードを変更するレコードのPK（ShozokuPk）
	 * @param oldPassword 旧パスワード
	 * @param newPassword 新パスワード
	 * @return true
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#changePassword(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShozokuPk, java.lang.String, java.lang.String)
	 */
	public boolean changePassword(UserInfo userInfo, ShozokuPk pkInfo, String oldPassword, String newPassword) throws ApplicationException, ValidationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//所属機関の取得
			//---------------------------------------
			ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);
			ShozokuInfo info = dao.selectShozokuInfo(connection, pkInfo);

			//---------------------------------------
			//現在のパスワードをチェックする。
			//---------------------------------------
			if (!info.getPassword().equals(oldPassword)) {
				//エラー情報保持用リスト
				List errors = new ArrayList();
				errors.add(new ErrorInfo("errors.2001", new String[] { "現在のパスワード" }));
				throw new ValidationException(
						"パスワード変更データチェック中にエラーが見つかりました。",
						errors);
			}

			//---------------------------------------
			//現在のパスワードを更新する。
			//---------------------------------------
			if(dao.changePasswordShozokuInfo(connection,pkInfo,newPassword)){
				//更新正常終了
				success = true;
			}

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"パスワード変更中にDBエラーが発生しました",
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
					"パスワード変更中にDBエラーが発生しました",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		return success;
	}

	/**
	 * validateメソッド.<br />
	 * <br />
	 * 空実装でnullを返却する。
	 * 
	 * @param userInfo				UserInfo
	 * @param insertOrUpdateInfo	ShozokuInfo
	 * @return null
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShozokuInfo)
	 */
	public ShozokuInfo validate(UserInfo userInfo, ShozokuInfo insertOrUpdateInfo) throws ApplicationException, ValidationException {
		return null;
	}

	/**
	 * 所属機関担当者情報のデータリストを取得する.<br />
	 * <br />
	 * 所属機関担当者情報を取得し、Listへ格納後に呼び出し元へ返却する。<br/><br/>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- 所属機関担当者ID
	 *     ,A.SHOZOKU_CD"				-- 所属機関名（コード）
	 *     ,A.SHOZOKU_NAME_KANJI			-- 所属機関名（日本語）
	 *     ,A.SHOZOKU_RYAKUSHO			-- 所属機関名（略称）
	 *     ,A.SHOZOKU_NAME_EIGO			-- 所属機関名（英文）
	 *     ,A.SHUBETU_CD				-- 機関種別
	 *     ,A.PASSWORD				-- パスワード
	 *     ,A.SEKININSHA_NAME_SEI			-- 責任者氏名（姓）
	 *     ,A.SEKININSHA_NAME_MEI			-- 責任者氏名（名）
	 *     ,A.SEKININSHA_YAKU			-- 責任者役職
	 *     ,A.BUKYOKU_NAME				-- 担当部課名
	 *     ,A.KAKARI_NAME				-- 担当係名
	 *     ,A.TANTO_NAME_SEI			-- 担当者名（姓）
	 *     ,A.TANTO_NAME_MEI			-- 担当者名（名）
	 *     ,A.TANTO_TEL				-- 担当者部局所在地（電話番号）
	 *     ,A.TANTO_FAX				-- 担当者部局所在地（FAX番号）
	 *     ,A.TANTO_EMAIL				-- 担当者部局所在地（Email）
	 *     ,A.TANTO_EMAIL2				-- 担当者部局所在地（Email2）
	 *     ,A.TANTO_ZIP				-- 担当者部局所在地（郵便番号）
	 *     ,A.TANTO_ADDRESS				-- 担当者部局所在地（住所）
	 *     ,A.NINSHOKEY_FLG				-- 認証キー発行フラグ
	 *     ,A.BIKO				-- 備考
	 *     ,A.YUKO_DATE				-- 有効期限
	 *     ,A.ID_DATE				-- ID発行日付
	 *     ,BUKYOKU_NUM				-- 部局担当者人数
	 *     ,A.DEL_FLG				-- 削除フラグ
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     DEL_FLG = 0
	 *     <b>AND SHOZOKU_CD = '所属機関コード'(※)</b>
	 * ORDER BY SHOZOKUTANTO_ID		-- 所属機関担当者ID
	 * </pre>
	 * </td></tr>
	 * </table>
	 *	<div style="font-size:8pt">（※）の検索条件にかんしては、引数のshozokuCdがnullでないときのみ加えられる。
	 *	なお、'<b>所属機関コード</b>'は第二引数のshozokuCdがセットされる。</div><br />
	 * 
	 * @param userInfo		UserInfo
	 * @param shozokuCd 	所属機関コード
	 * @return 所属機関担当者情報のリスト
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShozokuInfo)
	 */
	public List searchShozokuInfo(UserInfo userInfo, String shozokuCd) throws ApplicationException {

		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		
		String select = 
			"SELECT *  FROM SHOZOKUTANTOINFO A WHERE DEL_FLG = 0";	
			
		StringBuffer query = new StringBuffer(select);
		if(shozokuCd != null && !shozokuCd.equals("")){		
			query.append(" AND SHOZOKU_CD = " + EscapeUtil.toSqlString(shozokuCd));
		}
		
		//ソート順（所属機関担当者IDの昇順）
		query.append(" ORDER BY SHOZOKUTANTO_ID");			

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
			return SelectUtil.select(connection, query.toString());
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
	
//	2005/04/20 追加 ここから----------------------------------------
//	理由 システム管理者向け機能の所属機関情報検索用
	/**
	 * 所属機関情報の検索.<br><br>
	 * 
	 * 所属機関担当者と部局担当者情報を取得する。
	 * 
	 * @param userInfo
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public Page searchShozokuTantoList(UserInfo userInfo, ShozokuSearchInfo searchInfo)
		throws ApplicationException {
		
		ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);
		return dao.selectShozokuAndBukyokuTanto(userInfo,searchInfo);
	}
//	追加 ここまで-----------------------------------------------------

//	2005/04/21 追加 ここから----------------------------------------
//	理由 パスワード再設定処理追加
	
	/**
	 * パスワード再設定.<br><br>
	 * 
	 * @param userInfo
	 * @param pkInfo
	 * @return
	 * @throws ApplicationException
	 */
	public ShozokuInfo reconfigurePassword(UserInfo userInfo, ShozokuPk pkInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//所属機関担当者情報の取得
			//---------------------------------------
			ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);
			ShozokuInfo info = dao.selectShozokuInfo(connection, pkInfo);

			//RULEINFOテーブルよりルール取得準備
			RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHOZOKUTANTO);

			//パスワードを再設定する
			String newPassword = rureInfoDao.getPassword(connection, rulePk);
			success = dao.changePasswordShozokuInfo(connection,pkInfo,newPassword);

			//---------------------------------------
			//所属機関担当者データの取得
			//---------------------------------------
			ShozokuInfo result = dao.selectShozokuInfo(connection, pkInfo);
			
			return result;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"パスワード再設定中にDBエラーが発生しました",
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
					"パスワード再設定中にDBエラーが発生しました",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
//	追加 ここまで-----------------------------------------------------

	/**
	 * 承認確認メール自動通知送信実行
	 * @param userInfo
	 * @throws ApplicationException
	 */
	public void sendMailShoninTsuchi(UserInfo userInfo) throws ApplicationException
	{
		List jigyoList = null;
		Connection connection = null;

		//承認締め切り日時の設定
		DateUtil du = new DateUtil();
		du.addDate(DATE_BY_SHONIN_KIGEN);	//指定日付を加算する
		Date date = du.getCal().getTime();

		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			//事業管理DAO
			JigyoKanriInfoDao jigyoDao = new JigyoKanriInfoDao(userInfo);
			try {
				jigyoList = jigyoDao.selectShoninTsuchiJigyoInfo(connection, DATE_BY_SHONIN_KIGEN);
			} catch (NoDataFoundException e) {
				//何も処理しない
			} catch (DataAccessException e) {
				throw new ApplicationException("承認確認メール自動通知送信事業データ取得中にDBエラーが発生しました。",
						new ErrorInfo("errors.4004"), e);
			}
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		connection = null;

		//該当する事業が存在しなかった場合
		if (jigyoList == null || jigyoList.size() == 0) {
			String strDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
			String msg = "募集締め切りが[" + strDate + "]の事業で、承認確認の申請書を持つ事業は存在しません。";
			log.info(msg);
			return;
		}

		//-----データ構造を変換（重複データを含む単一リストから所属機関担当者ごとのマップへ変換する）
		//全体Map
		Map saisokuMap = new HashMap();
		for (int i = 0; i < jigyoList.size(); i++) {

			//1レコード
			Map recordMap = (Map) jigyoList.get(i);
			String nendo = (String) recordMap.get("NENDO");
			BigDecimal kaisu = (BigDecimal) recordMap.get("KAISU");
			String jigyo_name = (String) recordMap.get("JIGYO_NAME");
			String tanto_email = (String) recordMap.get("TANTO_EMAIL");

			String kaisu_hyoji = "";
			if (kaisu.intValue() > 1) {
				kaisu_hyoji = "第" + kaisu + "回 "; //回数が1回以上の場合は表示する
			}

			//事業名を「年度＋事業名」へ変換し、問い合わせ先を（改行＋全角空白）で結合。
// UPDATE START 2007-07-09 BIS 王志安
//			String jigyo_info = new StringBuffer("【研究種目名】平成")
//											.append(nendo)
//											.append("年度 ")
//											.append(kaisu_hyoji)
//											.append(jigyo_name)
//											.toString();
			String jigyo_info = new StringBuffer("    平成")
											.append(nendo)
											.append("年度 ")
											.append(kaisu_hyoji)
											.append(jigyo_name)
											.toString();
// UPDATE END 2007-07-09 BIS 王志安
			//全体Mapに当該所属機関担当者データが存在していた場合
			if (saisokuMap.containsKey(tanto_email)) {
				List dataList = (List) saisokuMap.get(tanto_email);
				dataList.add(jigyo_info); //次レコードに事業情報
			} else {
				//初の事業の場合	
				List dataList = new ArrayList();
				dataList.add(tanto_email); //1レコード目にメールアドレス
				dataList.add(jigyo_info); //2レコード目に事業情報
				saisokuMap.put(tanto_email, dataList);
			}

		}

		//---------------
		// メール送信
		//---------------
		//-----メール本文ファイルの読み込み
		String content = null;
		try {
			File contentFile = new File(CONTENT_SHINSEISHO_SHONIN_TSUCHI);
			FileResource fileRes = FileUtil.readFile(contentFile);
			content = new String(fileRes.getBinary());
		} catch (FileNotFoundException e) {
			log.warn("メール本文ファイルが見つかりませんでした。", e);
			return;
		} catch (IOException e) {
			log.warn("メール本文ファイル読み込み時にエラーが発生しました。", e);
			return;
		}

		//承認締め切り日付フォーマットを変更する
		String kigenDate = new StringBuffer("平成")
								.append(new DateUtil(date).getNendo())
								.append("年")
								.append(new SimpleDateFormat("M月d日").format(date))
								.toString();

		//-----メール送信（１人ずつ送信）
		for (Iterator iter = saisokuMap.keySet().iterator(); iter.hasNext();) {

			//所属機関担当者ごとのデータリストを取得する
			String tantoEmail = (String) iter.next();
			List dataList = (List) saisokuMap.get(tantoEmail);

			//メールアドレスが設定されていない場合は処理を飛ばす
			String to = (String) dataList.get(0);
			if (to == null || to.length() == 0) {
				continue;
			}

			//-----メール本文ファイルの動的項目変更
// UPDATE START 2007-07-24 BIS 王志安
//			StringBuffer jigyoNameList = new StringBuffer("\n");
//			for (int i = 1; i < dataList.size(); i++) {
//				jigyoNameList.append(dataList.get(i)).append("\n");
//			}
			StringBuffer jigyoNameList = new StringBuffer("");
			for (int i = 1; i < dataList.size(); i++) {
				jigyoNameList.append("\n");
				jigyoNameList.append(dataList.get(i));
			}
// UPDATE END 2007-07-24 BIS 王志安
			String[] param = new String[] { kigenDate, //承認締め切り日付
									jigyoNameList.toString(), //事業名リスト
								};
			String body = MessageFormat.format(content, param);

			if (log.isDebugEnabled()){
				log.debug("送信情報：********************\n" + body);
			}
			try {
				SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
				mailer.sendMail(FROM_ADDRESS, //差出人
						to, //to
						null, //cc
						null, //bcc
						SUBJECT_SHINSEISHO_SHONIN_TSUCHI, //件名
						body); //本文
			} catch (Exception e) {
				log.warn("メール送信に失敗しました。", e);
				continue;
			}
		}

	}




}

