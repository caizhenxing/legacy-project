/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
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
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * 審査員情報管理を行うクラス.<br /><br />
 * 
 * 
 * 
 * 使用しているテーブル<br /><br />
 * 　・審査員マスタ:MASTER_SHINSAIN<br />
 * 　　　審査員の情報を管理する。<br /><br />
 * 　・審査員情報テーブル:SHINSAININFO<br />
 * 　　　審査員のID・パスワードを管理する。<br /><br />
 * 　・審査結果テーブル:SHINSAKEKKA<br />
 * 　　　審査員割り振り結果情報と申請書の審査結果を管理する。<br /><br />
 * 　・申請データ管理テーブル:SHINSEIDATAKANRI<br />
 * 　　　申請データの情報を管理する。<br /><br />
 * 　・ＩＤパスワード発行ルールテーブル:RULEINFO<br />
 * 　　　各ID・パスワードの発行ルールを管理する。<br /><br />
 */
public class ShinsainMaintenance implements IShinsainMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ログ
	 */
	protected static Log log = LogFactory.getLog(ShinsainMaintenance.class);
	
	/**
	 * 審査依頼通知書ファイル格納フォルダ .<br /><br />
	 * 
	 * 具体的な値は、"<b>${shinsei_path}/work/irai2/</b>"<br />
	 */
	private static String IRAI_WORK_FOLDER2      = ApplicationSettings.getString(ISettingKeys.IRAI_WORK_FOLDER2);		

	/**
	 * 審査依頼書Wordファイル格納フォルダ.<br /><br />
	 * 
	 * 具体的な値は、"<b>${shinsei_path}/settings/irai2/</b>"<br />
	 */
	private static String IRAI_FORMAT_PATH2      = ApplicationSettings.getString(ISettingKeys.IRAI_FORMAT_PATH2);		

	/**
	 * 審査依頼書Wordファイル名.<br /><br />
	 * 
	 * 具体的な値は、"<b>shinsairai2.doc</b>"<br />
	 */
	private static String IRAI_FORMAT_FILE_NAME2 = ApplicationSettings.getString(ISettingKeys.IRAI_FORMAT_FILE_NAME2);		

	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public ShinsainMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IShinseishaMaintenance
	//---------------------------------------------------------------------

	/**
	 * 審査員情報の挿入.<br /><br />
	 * 
	 * 第二引数等のデータを使用し、審査員マスタ・審査員情報テーブルにデータを挿入する。
	 * その後、挿入したデータを持つShinsainInfoを返却する。<br /><br />
	 * 
	 * <b>1.審査員情報を追加</b><br /><br />
	 * 以下のデータを、第二引数のaddInfoに追加する。<br /><br />
	 * 
	 * 　(1)審査員IDの追加<br />
	 * 　　"<b>西暦年度(2桁)＋事業区分(1桁)+審査員番号(6桁)＋チェックデジット記号(1桁)</b>"のStringを取得する。<br /><br />
	 * 
	 * 　　　・西暦年度　　　　　　　…現在の年度を西暦で取得。<br />
	 * 　　　・事業区分　　　　　　　　　…第二引数addInfoから取得。<br />
	 * 　　　・審査員番号　　　　　　…第二引数addInfoから取得。<br />
	 * 　　　・チェックデジット記号　…メソッド"<b>getCheckDigit(key</b>(※1), <b>CheckDiditUtil.FORM_ALP</b>(※2)<b>)</b>"より取得。<br />
	 * 　　　　(※1)西暦年度(2桁)＋事業区分(1桁)+審査員番号(6桁)のString。<br />
	 * 　　　　(※2)String "form_alp"<br /><br />
	 * 
	 * 　　求めた文字列を、審査員IDとしてaddInfoに追加する。<br /><br />
	 * 
	 * 　(2)パスワードの追加<br />
	 * 　　以下のSQL文を発行して、パスワード作成ルールを取得する。(バインド変数は、SQL文の下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	RULEINFO A
	 * WHERE
	 * 	TAISHO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>対象者ID</td><td>4(ITaishoId.SHINSAIN)を使用する。</td></tr>
	 * </table><br />
	 * 
	 * 　　取得したルールより、パスワードの文字列を作成し、addInfoに追加する。<br /><br />
	 * 
	 * 　(3)データ更新日の追加<br />
	 * 
	 * 　　現在の日付をWASより取得し、addInfoに追加する。<br /><br />
	 * 
	 * <b>2.審査員情報の挿入</b><br /><br />
	 * 
	 * 　(1)データ挿入の前に、以下のSQL文を発行し、重複チェックを行う。<br />
	 * 　　(バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数addInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数addInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 　(2)重複がない場合は、以下のSQL文を発行し、審査員マスタと審査員情報テーブルにデータを挿入する。<br />
	 * 　　(バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * INSERT INTO MASTER_SHINSAIN
	 * 	(SHINSAIN_NO
	 * 	, JIGYO_KUBUN
	 * 	, NAME_KANJI_SEI
	 * 	, NAME_KANJI_MEI
	 * 	, NAME_KANA_SEI
	 * 	, NAME_KANA_MEI
	 * 	, SHOZOKU_CD
	 * 	, SHOZOKU_NAME
	 * 	, BUKYOKU_NAME
	 * 	, SHOKUSHU_NAME
	 * 	, SOFU_ZIP
	 * 	, SOFU_ZIPADDRESS
	 * 	, SOFU_ZIPEMAIL
	 * 	, SHOZOKU_TEL
	 * 	, SHOZOKU_FAX
	 * 	, URL
	 * 	, SENMON
	 * 	, KOSHIN_DATE
	 * 	, BIKO)
	 * VALUES
	 * 	(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数addInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数addInfoの変数JigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(漢字等-姓)</td><td>第二引数addInfoの変数NameKanjiSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(漢字等-名)</td><td>第二引数addInfoの変数NameKanjiMeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(フリガナ-姓)</td><td>第二引数addInfoの変数NameKanaSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(フリガナ-名)</td><td>第二引数addInfoの変数NameKanaMeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名(コード)</td><td>第二引数addInfoの変数ShozokuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名(和文)</td><td>第二引数addInfoの変数ShozokuNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名(和文)</td><td>第二引数addInfoの変数BukyokuNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職種名称</td><td>第二引数addInfoの変数ShokushuNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>送付先(郵便番号)</td><td>第二引数addInfoの変数SofuZipを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>送付先(住所)</td><td>第二引数addInfoの変数SofuZipaddressを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>送付先(Email)</td><td>第二引数addInfoの変数SofuZipemailを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>電話番号</td><td>第二引数addInfoの変数ShozokuTelを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>FAX番号</td><td>第二引数addInfoの変数ShozokuFaxを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>URL</td><td>第二引数addInfoの変数Urlを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>専門分野</td><td>第二引数addInfoの変数Senmonを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>データ更新日</td><td>第二引数addInfoの変数KoshinDateを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>第二引数addInfoの変数Bikoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * INSERT INTO SHINSAININFO
	 * 	(SHINSAIN_ID
	 * 	, PASSWORD
	 * 	, YUKO_DATE
	 * 	, DEL_FLG)
	 * VALUES
	 * 	(?,?,?,?)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員ID</td><td>第二引数addInfoの変数ShinsainIdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>パスワード</td><td>第二引数addInfoの変数Passwordを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>第二引数addInfoの変数YukoDateを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>0</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 
	 * <b>3.値の取得</b><br /><br />
	 * 
	 * 　審査員マスタと、審査員情報テーブルから、今挿入した値を取得する。発行するSQL文は以下の通り。<br />
	 * 　(バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数addInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数addInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値を、ShinsainInfoに格納して、返却する。
	 * 
	 * @param userInfo UserInfo
	 * @param addInfo ShinsainInfo
	 * @return 登録したデータを持つShinsainInfo
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainInfo)
	 */
	public ShinsainInfo insert(UserInfo userInfo, ShinsainInfo addInfo) throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//---------------------------------------
			//審査員情報の登録
			//---------------------------------------
			ShinsainInfoDao dao = new ShinsainInfoDao(userInfo);

			//---------------------------------------
			//キー情報の（審査員ID）を作成
			//西暦年度(2桁)＋区分（1桁）+審査員番号(6桁) ＋チェックデジット記号（1桁）
			//---------------------------------------
			String wareki = new DateUtil().getNendo();
			String key = DateUtil.changeWareki2Seireki(wareki)
						+ addInfo.getJigyoKubun()
						+ addInfo.getShinsainNo();
			
			//審査員IDを取得					
			String shinsainId = key +  CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
			addInfo.setShinsainId(shinsainId);

			//RULEINFOテーブルよりルール取得準備
			RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHINSAIN);
			
			//---------------------------------------
			//パスワード情報の作成
			//---------------------------------------
			String newPassword = rureInfoDao.getPassword(connection, rulePk);
			addInfo.setPassword(newPassword);

			//---------------------------------------
			//有効日付情報情報の作成(担当者用)
			//---------------------------------------
//			Date yukoDate = rureInfoDao.selectRuleInfo(connection, rulePk).getYukoDate();
//			addInfo.setYukoDate(yukoDate);

			//更新日付の設定		
			DateUtil dateUtil = new DateUtil();
			addInfo.setKoshinDate(dateUtil.getCal().getTime());

			dao.insertShinsainInfo(connection, addInfo);

			//---------------------------------------
			//登録データの取得
			//---------------------------------------
			ShinsainInfo result = dao.selectShinsainInfo(connection, addInfo);
			
			//---------------------------------------
			//登録正常終了
			//---------------------------------------
			success = true;

			return result;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"審査員管理データ登録中にDBエラーが発生しました。",
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
					"審査員管理データ登録中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 審査員情報の更新.<br /><br />
	 * 
	 * 審査員マスタ・審査員情報テーブルの更新を行う。
	 * また、すでに割り振られている審査員の場合には、審査結果テーブルも更新を行う。<br /><br />
	 * 
	 * <b>1.審査員情報の更新</b><br /><br />
	 * 　以下のSQL文を発行して、審査員マスタ・審査員情報テーブルの更新を行う。<br />
	 * 　(バインド変数は、SQL文の下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	MASTER_SHINSAIN
	 * SET
	 * 	NAME_KANJI_SEI = ?
	 * 	, NAME_KANJI_MEI = ?
	 * 	, NAME_KANA_SEI = ?
	 * 	, NAME_KANA_MEI = ?
	 * 	, SHOZOKU_CD = ?
	 * 	, SHOZOKU_NAME = ?
	 * 	, BUKYOKU_NAME = ?
	 * 	, SHOKUSHU_NAME = ?
	 * 	, SOFU_ZIP = ?
	 * 	, SOFU_ZIPADDRESS = ?
	 * 	, SOFU_ZIPEMAIL = ?
	 * 	, SHOZOKU_TEL = ?
	 * 	, SHOZOKU_FAX = ?
	 * 	, URL = ?
	 * 	, SENMON = ?
	 * 	, KOSHIN_DATE = ?
	 * 	, BIKO = ?
	 * WHERE
	 * 	JIGYO_KUBUN = ?
	 * 	AND SHINSAIN_NO = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(漢字等-姓)</td><td>第二引数updateInfoの変数NameKanjiSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(漢字等-名)</td><td>第二引数updateInfoの変数NameKanjiMeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(フリガナ-姓)</td><td>第二引数updateInfoの変数NameKanaSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(フリガナ-名)</td><td>第二引数updateInfoの変数NameKanaMeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名(コード)</td><td>第二引数updateInfoの変数ShozokuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名(和文)</td><td>第二引数updateInfoの変数ShozokuNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名(和文)</td><td>第二引数updateInfoの変数BukyokuNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職種名称</td><td>第二引数updateInfoの変数ShokushuNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>送付先(郵便番号)</td><td>第二引数updateInfoの変数SofuZipを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>送付先(住所)</td><td>第二引数updateInfoの変数SofuZipaddressを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>送付先(Email)</td><td>第二引数updateInfoの変数SofuZipemailを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>電話番号</td><td>第二引数updateInfoの変数ShozokuTelを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>FAX番号</td><td>第二引数updateInfoの変数ShozokuFaxを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>URL</td><td>第二引数updateInfoの変数Urlを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>専門分野</td><td>第二引数updateInfoの変数Senmonを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>データ更新日</td><td>第二引数updateInfoの変数KoshinDateを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>第二引数updateInfoの変数Bikoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数updateInfoの変数JigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数updateInfoの変数ShinsainNoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAININFO
	 * SET
	 * 	PASSWORD = ?
	 * 	,YUKO_DATE = ?
	 * WHERE
	 * 	SHINSAIN_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>パスワード</td><td>第二引数updateInfoの変数Passwordを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>第二引数updateInfoの変数YukoDateを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員ID</td><td>第二引数updateInfoの変数ShinsainIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 
	 * <b>2.審査結果テーブルの審査員情報の更新</b><br /><br />
	 * 割り振られていれば、審査結果テーブルの審査員情報も更新する。<br /><br />
	 * 
	 * 　(1)割り振られているかを確認<br /><br />
	 * 　　以下のSQL文を発行して、審査未完了(SHINSA_JOKYO = '0')で削除されていない(DEL_FLG = '0')担当審査結果情報の数を確認する。<br />
	 * 　　(バインド変数は、SQL文の下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	COUNT(*)
	 * FROM
	 * 	SHINSAKEKKA
	 * 	, SHINSEIDATAKANRI
	 * WHERE
	 * 	A.SHINSAIN_NO = ?"
	 * 	AND A.JIGYO_KUBUN = ?
	 * 	AND A.SHINSA_JOKYO = '0'
	 * 	AND A.SYSTEM_NO = B.SYSTEM_NO
	 * 	AND B.DEL_FLG = '0'</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数updateInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数updateInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 
	 * 　(2)割り振られていたら更新を行う<br /><br />
	 * 　　結果が1件以上あった場合、以下のSQL文を発行して審査結果テーブルの更新を行う。<br />
	 * 　　(バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAKEKKA
	 * SET
	 * 	SHINSAIN_NO = ?			--審査員番号
	 * 	, JIGYO_KUBUN = ?			--事業区分
	 * 	, SHINSAIN_NAME_KANJI_SEI = ?	--審査員名（漢字－姓）
	 * 	, SHINSAIN_NAME_KANJI_MEI = ?	--審査員名（漢字－名）
	 * 	, NAME_KANA_SEI = ?		--審査員名（フリガナ－姓）
	 * 	, NAME_KANA_MEI = ?		--審査員名（フリガナ－名）
	 * 	, SHOZOKU_NAME = ?			--審査員所属機関名
	 * 	, BUKYOKU_NAME = ?			--審査員部局名
	 * 	, SHOKUSHU_NAME = ?		--審査員職名
	 * WHERE
	 * 	SHINSAIN_NO = ?
	 * 	AND JIGYO_KUBUN = ?
	 * 	AND SHINSA_JOKYO = '0'
	 * 	AND EXISTS(
	 * 			SELECT
	 * 				*
	 * 			FROM
	 * 				SHINSEIDATAKANRI A
	 * 				, SHINSAKEKKA B
	 * 			WHERE
	 * 				A.DEL_FLG = 0
	 * 				AND A.SYSTEM_NO = B.SYSTEM_NO
	 * 		)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数updateInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数updateInfoの変数JigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（漢字－姓）</td><td>第二引数updateInfoの変数JigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（漢字－名）</td><td>第二引数updateInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－姓）</td><td>第二引数updateInfoの変数JigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－名）</td><td>第二引数updateInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員所属機関名</td><td>第二引数updateInfoの変数JigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員部局名</td><td>第二引数updateInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員職名</td><td>第二引数updateInfoの変数JigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数updateInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数updateInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo
	 * @param updateInfo ShinsainInfo
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainInfo)
	 */
	public ShinsainInfo update(UserInfo userInfo, ShinsainInfo updateInfo) throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//---------------------------------------
			//審査員情報の更新
			//---------------------------------------
			ShinsainInfoDao shinsainInfoDao = new ShinsainInfoDao(userInfo);
			shinsainInfoDao.updateShinsainInfo(connection, updateInfo);
			
			//---------------------------------------
			//審査結果テーブルの審査員情報の更新
			//---------------------------------------
			ShinsaKekkaInfoDao shinsaKekkaInfoDao = new ShinsaKekkaInfoDao(userInfo);
			int tantoShinsaKekka
				= shinsaKekkaInfoDao.countTantoShinsaKekkaInfo(connection, updateInfo.getShinsainNo(), updateInfo.getJigyoKubun());
			//割り振られていれば、審査結果の審査員情報も更新
			if(tantoShinsaKekka > 0) {
				shinsaKekkaInfoDao.updateShinsainInfo(connection, updateInfo, null, null);
			}
						
			//---------------------------------------
			//更新データの取得
			//---------------------------------------
			ShinsainInfo result = shinsainInfoDao.selectShinsainInfo(connection, updateInfo);
			
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;
			
			return result;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"審査員管理データ更新中にDBエラーが発生しました。",
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
					"審査員管理データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
	}

	/**
	 * 審査員情報の削除.<br /><br />
	 * 
	 * 審査結果テーブルの審査員情報の削除</b><br /><br />
	 * 
	 * <b>1.割り振られているかを確認</b><br /><br />
	 * 　割り振られていて、審査未完了(SHINSA_JOKYO = '0')のものがある場合は削除できないので、
	 * 以下のSQL文を発行して確認する。<br />
	 * 　(バインド変数は、SQL文の下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	COUNT(*)
	 * FROM
	 * 	SHINSAKEKKA
	 * 	, SHINSEIDATAKANRI
	 * WHERE
	 * 	A.SHINSAIN_NO = ?
	 * 	AND A.JIGYO_KUBUN = ?
	 * 	AND A.SHINSA_JOKYO = '0'
	 * 	AND A.SYSTEM_NO = B.SYSTEM_NO
	 * 	AND B.DEL_FLG = '0'</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数updateInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数updateInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 　審査未完了で削除されていない担当審査結果情報が存在する場合には例外をthrowする。<br /><br />
	 * 
	 * <b>2.審査員情報の削除</b><br /><br />
	 * 審査員情報の物理削除を行う。<br /><br />
	 * 
	 * 　(1)審査員情報の検索<br /><br />
	 * 　　該当する審査員情報を検索する。発行するSQL文は以下の通り。<br />
	 * 　　(バインド変数は、SQL文の下の表を参照)<br />
	 * 　　該当するものがなかった場合には例外をthrowする。<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数addInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数addInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 　　結果が存在する場合には、値をShinsainInfoに格納する。<br /><br />
	 * 
	 * 　(2)審査員情報の物理削除<br /><br />
	 * 　　審査員情報テーブル・審査員マスタから情報の物理削除を行う。発行するSQL文は以下の通り。<br />
	 * 　　(バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * DELETE FROM
	 * 	SHINSAININFO
	 * WHERE
	 * 	SHINSAIN_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員ID</td><td>ShinsainInfoの変数ShinsainIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * DELETE FROM
	 * 	MASTER_SHINSAIN
	 * WHERE
	 * 	SHINSAIN_NO = ?
	 * 	AND JIGYO_KUBUN = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>ShinsainInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>ShinsainInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param deleteInfo ShinsainInfo
	 * @throws ApplicationException
	 * @throws ValidationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainInfo)
	 */
	public void delete(UserInfo userInfo, ShinsainInfo deleteInfo) throws ApplicationException, ValidationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//エラー情報保持用リスト
			List errors = new ArrayList();

			//審査を割り振られている場合は、削除できないのでチェック
			ShinsaKekkaInfoDao shinsaKekkaInfoDao = new ShinsaKekkaInfoDao(userInfo);
			int tantoShinsaKekka
				= shinsaKekkaInfoDao.countTantoShinsaKekkaInfo(connection, deleteInfo.getShinsainNo(), deleteInfo.getJigyoKubun());
			if(tantoShinsaKekka> 0) {
				errors.add(new ErrorInfo("errors.5029"));
				throw new ValidationException(
					"審査未完了で削除されていない申請データが見つかりました。",
					errors);
			}

			//---------------------------------------
			//審査員情報の削除(審査員の登録機能があるので、審査員番号がダブらないよう物理削除)
			//---------------------------------------
			ShinsainInfoDao shinsainInfoDao = new ShinsainInfoDao(userInfo);
			shinsainInfoDao.deleteShinsainInfo(connection, deleteInfo);
			//---------------------------------------
			//削除正常終了
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"審査員管理データ削除中にDBエラーが発生しました。",
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
					"審査員管理データ削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
	}

	/**
	 * 審査員情報の取得.<br /><br />
	 * 
	 * 審査員情報を取得し、結果をShinsainInfoに格納して返却する。<br /><br />
	 * 
	 * <b>1.審査員情報の取得</b><br /><br />
	 * 以下のSQL文を発行して、審査員情報の取得を行う。<br />
	 * (バインド変数は、SQLの下の表を参照) <br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数pkInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数pkInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * <b>2.結果の返却</b><br /><br />
	 * 
	 * 取得した値をShinsainInfoに格納し、返却する。<br />
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo ShinsainPk
	 * @return 審査員情報を格納したShinsainInfo
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainPk)
	 */
	public ShinsainInfo select(UserInfo userInfo, ShinsainPk pkInfo) throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			ShinsainInfoDao dao = new ShinsainInfoDao(userInfo);
			return dao.selectShinsainInfo(connection, pkInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"審査員管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * 審査員情報を持つPageの取得.<br /><br />
	 * 
	 * 以下のSQL文を発行し、審査員情報を取得する。<br />
	 * SQL文のwhere句には、第二引数の各値がnull又は空文字列でない場合に条件文が加えられていく。<br />
	 * (加えられるSQL文と、条件になる変数はSQL文の下の表を参照)
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	MS.SHINSAIN_NO		--審査員番号
	 * 	, MS.NAME_KANJI_SEI	--審査員氏名（漢字-姓）
	 * 	, MS.NAME_KANJI_MEI	--審査員氏名（漢字-名）
	 * 	, MS.SHOZOKU_NAME		--所属機関名
	 * 	, MS.SOFU_ZIPEMAIL		--送付先(Email)
	 * 	, MS.JIGYO_KUBUN"		--事業区分
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 
	 *				<b><span style="color:#002288">－－動的検索条件－－</span></b>
	 *
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)
	 * ORDER BY
	 * 	MS.SHINSAIN_NO</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>審査員番号</td><td>ShinsainNo</td><td>AND MS.SHINSAIN_NO = '審査員番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>審査員名(姓-漢字等)</td><td>NameKanjiSei</td><td>AND MS.NAME_KANJI_SEI LIKE '%審査員名(姓-漢字等)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>審査員名(名-漢字等)</td><td>NameKanjiMei</td><td>AND MS.NAME_KANJI_SEI LIKE '%審査員名(名-漢字等)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属コード</td><td>ShozokuCd</td><td>AND MS.SHOZOKU_CD = '所属コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>細目コード</td><td>BunkaSaimokuCd</td><td>AND (((SUBSTR(MS.SHINSAIN_NO, 1, 4) = '細目コード')AND (SUBSTR(MS.SHINSAIN_NO, 5, 1) IN ('A', 'B')))OR ((SUBSTR(MS.SHINSAIN_NO, 1, 1) = '0')AND (SUBSTR(MS.SHINSAIN_NO, 2, 4) = '細目コード'))</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>JigyoKubun</td><td>AND MS.JIGYO_KUBUN = '事業区分'</td></tr>
	 * </table><br/>
	 * 
	 * 
	 * 取得した値は、列名をキーにしてMapに格納される。
	 * それをまとめてListに格納し、そのListがPageに格納される。
	 * 審査員情報を持つこのPageが、返却される。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsainSearchInfo
	 * @return 審査員情報を持つPage
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainSearchInfo)
	 */
	public Page search(UserInfo userInfo, ShinsainSearchInfo searchInfo) throws ApplicationException {

		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------

		String select = "SELECT MS.SHINSAIN_NO"			//審査員番号
						+ ", MS.NAME_KANJI_SEI"			//審査員氏名（漢字-姓）
						+ ", MS.NAME_KANJI_MEI"			//審査員氏名（漢字-名）
						+ ", MS.SHOZOKU_NAME"			//所属機関名
						+ ", MS.SOFU_ZIPEMAIL"			//送付先(Email)
						+ ", MS.JIGYO_KUBUN"			//事業区分
						+ " FROM MASTER_SHINSAIN MS"
						+ ", SHINSAININFO SI"
						+ " WHERE SI.DEL_FLG = 0";

		StringBuffer query = new StringBuffer(select);
	
		if(searchInfo.getShinsainNo() != null && !searchInfo.getShinsainNo().equals("")){			//審査員番号
			query.append(" AND MS.SHINSAIN_NO = '" + EscapeUtil.toSqlString(searchInfo.getShinsainNo()) + "'");
		}
//		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){	//分科細目コード
//			ArrayList arrBscd = this.separateString(searchInfo.getBunkaSaimokuCd());
//
//			int cnt = arrBscd.size();
//			for(int i=0; i<cnt; i++){
//				String condi = (String)arrBscd.get(i);
//				
//				if(i == 0){
//					query.append("AND (MS.LEVEL_A1 = '" + condi + "' ");
//				}else{
//					query.append(" OR MS.LEVEL_A1 = '" + condi + "' ");
//				}
//				query.append("OR MS.LEVEL_B1_1 = '" + condi + "' ");
//				query.append("OR MS.LEVEL_B1_2 = '" + condi + "' ");
//				query.append("OR MS.LEVEL_B1_3 = '" + condi + "' ");
//				query.append("OR MS.LEVEL_B2_1 = '" + condi + "' ");
//				query.append("OR MS.LEVEL_B2_2 = '" + condi + "' ");
//				query.append("OR MS.LEVEL_B2_3 = '" + condi + "'");
//			}
//			if(cnt != 0){
//				query.append(") ");
//			}
//		}
		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){				//申請者氏名（漢字-姓）
			query.append(" AND MS.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){				//申請者氏名（漢字-名）
			query.append(" AND MS.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){					//所属機関コード
			query.append(" AND MS.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){			//細目番号
			query.append(" AND (")
				.append("((SUBSTR(MS.SHINSAIN_NO, 1, 4) = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "')")
					.append(" AND (SUBSTR(MS.SHINSAIN_NO, 5, 1) IN ('A', 'B')))")
				.append(" OR ((SUBSTR(MS.SHINSAIN_NO, 1, 1) = '0')")
					.append(" AND (SUBSTR(MS.SHINSAIN_NO, 2, 4) = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "'))")
			.append(")");
		}
//		if(searchInfo.getKeyword() != null && !searchInfo.getKeyword().equals("")){				//キーワード
//			ArrayList arrBscd = this.separateString(searchInfo.getKeyword());
//
//			int cnt = arrBscd.size();
//			for(int i=0; i<cnt; i++){
//				String condi = EscapeUtil.toSqlString((String)arrBscd.get(i));
//				
//				if(i == 0){
//					query.append("AND (MS.KEY1 LIKE '%" + condi + "%' ");
//				}else{
//					query.append(" OR MS.KEY1 LIKE '%" + condi + "%' ");
//				}
//				query.append("OR MS.KEY2 LIKE '%" + condi + "%' ");
//				query.append("OR MS.KEY3 LIKE '%" + condi + "%' ");
//				query.append("OR MS.KEY4 LIKE '%" + condi + "%' ");
//				query.append("OR MS.KEY5 LIKE '%" + condi + "%' ");
//				query.append("OR MS.KEY6 LIKE '%" + condi + "%' ");
//				query.append("OR MS.KEY7 LIKE '%" + condi + "%'");				
//			}
//			if(cnt != 0){
//				query.append(") ");
//			}
//		}
		if(searchInfo.getJigyoKubun() != null && !searchInfo.getJigyoKubun().equals("")){			//事業区分
			query.append(" AND MS.JIGYO_KUBUN = '" + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + "'");
		}

		//結合条件＋ソート条件（審査員番号順）
		query.append(" AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)")	//7桁
			 .append(" AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)")
			 .append(" ORDER BY MS.SHINSAIN_NO");
		
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
				"審査員管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

	}

	/**
	 * 審査員情報CSVデータの作成.<br /><br />
	 * 
	 * 以下のSQL文を発行し、審査員情報を取得する。<br />
	 * SQL文のwhere句には、第二引数の各値がnull又は空文字列でない場合に条件文が加えられていく。<br />
	 * (加えられるSQL文と、条件になる変数はSQL文の下の表を参照)
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * SELECT
	 * 	MS.SHINSAIN_NO			"審査員番号"
	 * 	, REPLACE(REPLACE(MS.JIGYO_KUBUN,
	 * 			'1',
	 * 			'学術創成')
	 * 		'4',
	 * 		'基盤研究等')		"事業区分"
	 * 	, MS.NAME_KANJI_SEI		"氏名(漢字等－姓)"
	 * 	, MS.NAME_KANJI_MEI		"氏名(漢字等－名)"
	 * 	, MS.NAME_KANA_SEI			"氏名(フリガナ－姓)"
	 * 	, MS.NAME_KANA_MEI			"氏名(フリガナ－名)"
	 * 	, MS.SHOZOKU_CD			"所属機関名(コード)"
	 * 	, MS.SHOZOKU_NAME			"所属機関名(和文)"
	 * 	, MS.BUKYOKU_NAME			"部局名(和文)"
	 * 	, MS.SHOKUSHU_NAME			"職種名称"
	 * 	, MS.SOFU_ZIP			"送付先(郵便番号)"
	 * 	, MS.SOFU_ZIPADDRESS		"送付先(住所)"
	 * 	, MS.SOFU_ZIPEMAIL			"送付先(Email)"
	 * 	, MS.SHOZOKU_TEL			"電話番号"
	 * 	, MS.SHOZOKU_FAX			"FAX番号"
	 * 	, MS.URL				"URL"
	 * 	, MS.SENMON			"専門分野"
	 * 	, TO_CHAR(MS.KOSHIN_DATE,
	 * 		'YYYY/MM/DD')		"データ更新日"
	 * 	, MS.BIKO				"備考"
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 
	 *	<b><span style="color:#002288">－－動的検索条件－－</span></b>
	 *
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)
	 * ORDER BY
	 * 	MS.SHINSAIN_NO</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>審査員番号</td><td>ShinsainNo</td><td>AND MS.SHINSAIN_NO = '審査員番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>審査員名(姓-漢字等)</td><td>NameKanjiSei</td><td>AND MS.NAME_KANJI_SEI LIKE '%審査員名(姓-漢字等)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>審査員名(名-漢字等)</td><td>NameKanjiMei</td><td>AND MS.NAME_KANJI_SEI LIKE '%審査員名(名-漢字等)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属コード</td><td>ShozokuCd</td><td>AND MS.SHOZOKU_CD = '所属コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>細目コード</td><td>BunkaSaimokuCd</td><td>AND (((SUBSTR(MS.SHINSAIN_NO, 1, 4) = '細目コード')AND (SUBSTR(MS.SHINSAIN_NO, 5, 1) IN ('A', 'B')))OR ((SUBSTR(MS.SHINSAIN_NO, 1, 1) = '0')AND (SUBSTR(MS.SHINSAIN_NO, 2, 4) = '細目コード'))</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>JigyoKubun</td><td>AND MS.JIGYO_KUBUN = '事業区分'</td></tr>
	 * </table><br />
	 * 
	 * Listの最初の要素にカラム名を挿入する。こうして得たCSVのListを返却する。<br /><br />
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsainSearchInfo
	 * @return 審査員情報のCSV出力データのList
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#searchCsvData(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainSearchInfo)
	 */
	public List searchCsvData(UserInfo userInfo, ShinsainSearchInfo searchInfo) throws ApplicationException {
	
		//-----------------------
		// SQL文の作成
		//-----------------------
		String select = "SELECT MS.SHINSAIN_NO \"審査員番号\""			//審査員番号
						+ ", REPLACE(REPLACE(MS.JIGYO_KUBUN, '1', '学術創成'), '4', '基盤研究等') \"事業区分\""				//事業区分
						+ ", MS.NAME_KANJI_SEI \"氏名(漢字等－姓)\""		//氏名(漢字等－姓)
						+ ", MS.NAME_KANJI_MEI \"氏名(漢字等－名)\""		//氏名(漢字等－名)
						+ ", MS.NAME_KANA_SEI \"氏名(フリガナ－姓)\""		//氏名(フリガナ－姓)
						+ ", MS.NAME_KANA_MEI \"氏名(フリガナ－名)\""		//氏名(フリガナ－名)
						+ ", MS.SHOZOKU_CD \"所属研究機関名(番号)\""		//所属機関名(コード)
						+ ", MS.SHOZOKU_NAME \"所属機研究関名(和文)\""	//所属機関名(和文)
//						+ ", MS.BUKYOKU_CD \"部局名(コード)\""			//部局名(コード)
						+ ", MS.BUKYOKU_NAME \"部局名(和文)\""			//部局名(和文)
//						+ ", MS.SHOKUSHU_CD \"職種コード\""				//職種コード
						+ ", MS.SHOKUSHU_NAME \"職種名称\""				//職種名称
//						+ ", MS.KEI_CD \"系別\""							//系別
//						+ ", MS.SHINSA_KAHI \"審査可不可\""				//審査可不可
						+ ", MS.SOFU_ZIP \"送付先(郵便番号)\""			//送付先(郵便番号)
						+ ", MS.SOFU_ZIPADDRESS \"送付先(住所)\""		//送付先(住所)
						+ ", MS.SOFU_ZIPEMAIL \"送付先(Email)\""			//送付先(Email)
						+ ", MS.SHOZOKU_TEL \"電話番号\""				//電話番号
						+ ", MS.SHOZOKU_FAX \"FAX番号\""					//電話番号
//						+ ", MS.SOFU_ZIPEMAIL2 \"送付先(Email2)\""		//送付先(Email2)
//						+ ", MS.SHOZOKU_TEL \"機関電話番号\""			//機関電話番号
//						+ ", MS.JITAKU_TEL \"自宅電話番号\""				//自宅電話番号
//						+ ", MS.SINKI_KEIZOKU_FLG \"新規・継続\""		//新規・継続
//						+ ", MS.KIZOKU_START \"委嘱開始日\""				//委嘱開始日
//						+ ", MS.KIZOKU_END \"委嘱終了日\""				//委嘱終了日
//						+ ", MS.SHAKIN \"謝金\""							//謝金
						+ ", MS.URL URL"								//URL
						+ ", MS.SENMON \"専門分野\""						//専門分野
						+ ", TO_CHAR(MS.KOSHIN_DATE, 'YYYY/MM/DD') \"データ更新日\""			//データ更新日
//						+ ", MS.LEVEL_A1 \"分科細目コード(A)\""			//分科細目コード(A)
//						+ ", MS.LEVEL_B1_1 \"分科細目コード(B1-1)\""		//分科細目コード(B1-1)
//						+ ", MS.LEVEL_B1_2 \"分科細目コード(B1-2)\""		//分科細目コード(B1-2)
//						+ ", MS.LEVEL_B1_3 \"分科細目コード(B1-3)\""		//分科細目コード(B1-3)
//						+ ", MS.LEVEL_B2_1 \"分科細目コード(B2-1)\""		//分科細目コード(B2-1)
//						+ ", MS.LEVEL_B2_2 \"分科細目コード(B2-2)\""		//分科細目コード(B2-2)
//						+ ", MS.LEVEL_B2_3 \"分科細目コード(B2-3)\""		//分科細目コード(B2-3)
//						+ ", MS.KEY1 \"専門分野のキーワード(1)\""		//専門分野のキーワード(1)
//						+ ", MS.KEY2 \"専門分野のキーワード(2)\""		//専門分野のキーワード(2)
//						+ ", MS.KEY3 \"専門分野のキーワード(3)\""		//専門分野のキーワード(3)
//						+ ", MS.KEY4 \"専門分野のキーワード(4)\""		//専門分野のキーワード(4)
//						+ ", MS.KEY5 \"専門分野のキーワード(5)\""		//専門分野のキーワード(5)
//						+ ", MS.KEY6 \"専門分野のキーワード(6)\""		//専門分野のキーワード(6)
//						+ ", MS.KEY7 \"専門分野のキーワード(7)\""		//専門分野のキーワード(7)
						+ ", MS.BIKO \"備考\""							//備考
						+ " FROM MASTER_SHINSAIN MS"
						+ ", SHINSAININFO SI"
						+ " WHERE SI.DEL_FLG = 0";
		
		StringBuffer query = new StringBuffer(select);
			
		if(searchInfo.getShinsainNo() != null && !searchInfo.getShinsainNo().equals("")){			//審査員番号
			query.append(" AND MS.SHINSAIN_NO = '" + EscapeUtil.toSqlString(searchInfo.getShinsainNo()) + "'");
		}
//		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){	//分科細目コード
//			ArrayList arrBscd = this.separateString(searchInfo.getBunkaSaimokuCd());
//		
//			int cnt = arrBscd.size();
//			for(int i=0; i<cnt; i++){
//				String condi = (String)arrBscd.get(i);
//						
//				if(i == 0){
//					query.append(" AND (MS.LEVEL_A1 = '" + condi + "'");
//				}else{
//					query.append(" OR MS.LEVEL_A1 = '" + condi + "'");
//				}
//				query.append(" OR MS.LEVEL_B1_1 = '" + condi + "'");
//				query.append(" OR MS.LEVEL_B1_2 = '" + condi + "'");
//				query.append(" OR MS.LEVEL_B1_3 = '" + condi + "'");
//				query.append(" OR MS.LEVEL_B2_1 = '" + condi + "'");
//				query.append(" OR MS.LEVEL_B2_2 = '" + condi + "'");
//				query.append(" OR MS.LEVEL_B2_3 = '" + condi + "'");
//			}
//			if(cnt != 0){
//				query.append(") ");
//			}
//		}
		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){				//申請者氏名（漢字-姓）
			query.append(" AND MS.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){				//申請者氏名（漢字-名）
			query.append(" AND MS.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){					//所属機関コード
			query.append(" AND MS.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){			//細目番号
			query.append(" AND (")
				.append("((SUBSTR(MS.SHINSAIN_NO, 1, 4) = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "')")
					.append(" AND (SUBSTR(MS.SHINSAIN_NO, 5, 1) IN ('A', 'B')))")
				.append(" OR ((SUBSTR(MS.SHINSAIN_NO, 1, 1) = '0')")
					.append(" AND (SUBSTR(MS.SHINSAIN_NO, 2, 4) = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "'))")
			.append(")");
		}
//		if(searchInfo.getKeyword() != null && !searchInfo.getKeyword().equals("")){				//キーワード
//			ArrayList arrBscd = this.separateString(searchInfo.getKeyword());
//		
//			int cnt = arrBscd.size();
//			for(int i=0; i<cnt; i++){
//				String condi = EscapeUtil.toSqlString((String)arrBscd.get(i));
//						
//				if(i == 0){
//					query.append(" AND (MS.KEY1 LIKE '%" + condi + "%'");
//				}else{
//					query.append(" OR MS.KEY1 LIKE '%" + condi + "%'");
//				}
//				query.append(" OR MS.KEY2 LIKE '%" + condi + "%'");
//				query.append(" OR MS.KEY3 LIKE '%" + condi + "%'");
//				query.append(" OR MS.KEY4 LIKE '%" + condi + "%'");
//				query.append(" OR MS.KEY5 LIKE '%" + condi + "%'");
//				query.append(" OR MS.KEY6 LIKE '%" + condi + "%'");
//				query.append(" OR MS.KEY7 LIKE '%" + condi + "%'");				
//			}
//			if(cnt != 0){
//				query.append(")");
//			}
//		}
		if(searchInfo.getJigyoKubun() != null && !searchInfo.getJigyoKubun().equals("")){			//事業区分
			query.append(" AND MS.JIGYO_KUBUN = '" + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + "'");
		}
		
		//結合条件＋ソート条件（審査員番号順）
		query.append(" AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)")	//7桁
			 .append(" AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)")
			 .append(" ORDER BY MS.SHINSAIN_NO");
		
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

	/**
	 * 重複のチェック.<br /><br />
	 * 
	 * <b>1.機関情報の取得</b><br /><br />
	 * 
	 * 所属機関コードから、所属機関名を取得する。<br /><br />
	 * 
	 * 
	 * 第二引数infoの変数ShozokuCdの値が"99999"(その他)だった場合には、kikanInfoに以下の値を与える。<br /><br />
	 * 
	 * 　・ShozokuNameKanji	…第二引数infoの変数ShozokuName<br />
	 * 　・ShozokuNameEigo	…null<br />
	 * 　・ShozokuRyakusho	…"その他"<br /><br />
	 * 
	 * それ以外の場合には、以下のSQL文を発行して、キーに一致する所属機関情報を取得する。<br />
	 * （バインド変数はSQLの下の表を参照）<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	MASTER_KIKAN
	 * WHERE
	 * 	SHOZOKU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数infoの変数ShozokuCdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値の中から、所属機関名を第二引数のinfoに格納する。<br /><br />
	 * 
	 * <b>2.重複チェック</b><br /><br />
	 * 以下のSQL文を発行して、重複チェックを行う。<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	COUNT(*)
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND SI.DEL_FLG = 0		--削除フラグ
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数infoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数infoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 検索結果が1件以上あった場合には例外をthrowし、
	 * 重複がなかった場合には、infoを返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param info ShinsainInfo
	 * @param mode String
	 * @return ShinsainInfo
	 * @throws ApplicationException
	 * @throws ValidationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainInfo, java.lang.String)
	 */
	public ShinsainInfo validate(UserInfo userInfo, ShinsainInfo info, String mode) throws ApplicationException, ValidationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//エラー情報保持用リスト
			List errors = new ArrayList();

			//各コードから名称を取得するため、ShinseishaMaintenanceを使用する
			ShinseishaMaintenance shinseishaMaintenance = new ShinseishaMaintenance();
			
			//---------------------------
			//所属機関コード→所属機関名のセット
			//---------------------------
			KikanInfo kikanInfo
				= shinseishaMaintenance.getKikanCodeValue(userInfo, info.getShozokuCd(), info.getShozokuName(), null);
			info.setShozokuName(kikanInfo.getShozokuNameKanji());

//			KikanInfo kikanInfo = new KikanInfo();
//			kikanInfo.setShubetuCd("0"); //とりあえず０固定
//			kikanInfo.setShozokuCd(info.getShozokuCd());
//			
//			if(info.getShozokuCd() != null && !info.getShozokuCd().equals("") && !info.getShozokuCd().equals("9999")) {
//				try {
//					kikanInfo = new MasterKikanInfoDao(userInfo).selectKikanInfo(connection,kikanInfo);
//					info.setShozokuName(kikanInfo.getShozokuNameKanji());
//				} catch (NoDataFoundException e) {
//					//見つからなかったとき。
//					errors.add(
//						new ErrorInfo("errors.2001", new String[] { "所属機関コード" }));
//				}
//			}

//			//---------------------------
//			//部局コード→部局名
//			//---------------------------
//			BukyokuInfo bukyokuInfo = new BukyokuInfo();
//			bukyokuInfo.setBukyokuCd(info.getBukyokuCd());
//
//			if(info.getBukyokuCd() != null && !info.getBukyokuCd().equals("") && !info.getBukyokuCd().equals("999")) {
//				try {
//					bukyokuInfo =
//						new MasterBukyokuInfoDao(userInfo).selectBukyokuInfo(connection,bukyokuInfo);
//					info.setBukyokuName(bukyokuInfo.getBukaName());
//				} catch (NoDataFoundException e) {
//					//見つからなかったとき。
//					errors.add(
//						new ErrorInfo("errors.2001", new String[] { "部局コード" }));
//				}
//			}
//
//			//---------------------------
//			//職種コード→職種名
//			//---------------------------
//			ShokushuInfo shokushuInfo = new ShokushuInfo();
//			shokushuInfo.setShokushuCd(info.getShokushuCd());
//
//			if(info.getShokushuCd() != null && !info.getShokushuCd().equals("") && !info.getShokushuCd().equals("999")) {
//				try {
//					shokushuInfo =
//						new MasterShokushuInfoDao(userInfo).selectShokushuInfo(connection,shokushuInfo);
//					info.setShokushuName(shokushuInfo.getShokushuName());
//				} catch (NoDataFoundException e) {
//					//見つからなかったとき。
//					errors.add(
//						new ErrorInfo("errors.2001", new String[] { "職種コード" }));
//				}
//			}

			//2重登録チェック
			//審査員番号・事業区分が同じ場合をチェック
			if(mode.equals(IMaintenanceName.ADD_MODE)) {
				ShinsainInfoDao dao = new ShinsainInfoDao(userInfo);
				int count = dao.countShinsainInfo(connection, info);
				//すでに登録されている場合
				if(count > 0){
					String[] error = {"審査員"};
					throw new ApplicationException("すでに審査員が登録されています。", 	new ErrorInfo("errors.4007", error));			
				}
			}

			//-----入力エラーがあった場合は例外をなげる-----
			if (!errors.isEmpty()) {
				throw new ValidationException(
					"審査員管理データチェック中にエラーが見つかりました。",
					errors);
			}

			return info;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"審査員管理データチェック中にDBエラーが発生しました。",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

	}

	/**
	 * パスワードの更新.<br /><br />
	 * 
	 * <b>1.審査員情報の取得</b><br /><br />
	 * 以下のSQL文を発行して、審査員情報を取得する。<br />
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数addInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数addInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 
	 * <b>2.パスワードのチェック</b><br /><br />
	 * 
	 * 取得した審査員情報のパスワードの値が、第三引数のoldPasswordと等しいかチェックする。
	 * 異なる場合には、例外をthrowする。<br /><br />
	 * 
	 * 
	 * <b>3.パスワードの更新</b><br /><br />
	 * 
	 * 以下のSQL文を発行して、パスワードの更新を行う。<br />
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAININFO
	 * SET
	 * 	PASSWORD = ?
	 * WHERE
	 * 	SUBSTR(SHINSAIN_ID,3,8) = ?		--事業区分+審査員番号(7桁)
	 * 	AND DEL_FLG = 0			--削除フラグ</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>新パスワード</td><td>第四引数newPasswordを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数pkInfoの変数JigyoKubunとShinsainNoを連結したものを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 正常にパスワードの更新が行われた場合には、trueを返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo ShinsainPk
	 * @param oldPassword String
	 * @param newPassword String
	 * @return 更新結果のboolean
	 * @throws ApplicationException
	 * @throws ValidationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#changePassword(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainPk, java.lang.String, java.lang.String)
	 */
	public boolean changePassword(UserInfo userInfo, ShinsainPk pkInfo, String oldPassword, String newPassword) throws ApplicationException, ValidationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//---------------------------------------
			//審査員の取得
			//---------------------------------------
			ShinsainInfoDao dao = new ShinsainInfoDao(userInfo);
			ShinsainInfo info = dao.selectShinsainInfo(connection, pkInfo);

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
			if(dao.changePasswordShinsainInfo(connection,pkInfo,newPassword)){
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

//	/* (非 Javadoc)
//	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#separateString(java.lang.String)
//	 */
//	private ArrayList separateString(String str) throws ApplicationException {
//		str = str.trim();
//		ArrayList arrayl = new ArrayList();
//			
//		while(true){
//			int idx_low = str.indexOf(" ");										//半角スペースのインデックス
//			int idx_up = str.indexOf("　");										//全角スペースのインデックス
//				
//			//半角スペース、全角スペースともに該当なし
//			if(idx_low == -1 && idx_up == -1){
//				if(!str.equals("")){
//					arrayl.add(str);
//				}
//				break;
//			}
//			//全角スペースが該当なし
//			else if(idx_up == -1){
//				String condi = str.substring(0, idx_low);
//				if(!condi.equals("")){
//					arrayl.add(condi);
//				}
//				str = str.substring(idx_low+1);
//			}
//			//半角スペースが該当なし
//			else if(idx_low == -1){
//				String condi = str.substring(0, idx_up);
//				if(!condi.equals("")){
//					arrayl.add(condi);
//				}
//				str = str.substring(idx_up+1);
//			}
//			//半角スペース、全角スペースとも該当あり
//			else{
//				//半角スペースが先に該当する
//				if(idx_low < idx_up){
//					String condi = str.substring(0, idx_low);
//					if(!condi.equals("")){
//						arrayl.add(condi);
//					}
//					str = str.substring(idx_low+1);
//				}
//				//全角スペースが先に該当する
//				else{
//					String condi = str.substring(0, idx_up);
//					if(!condi.equals("")){
//						arrayl.add(condi);
//					}
//					str = str.substring(idx_up+1);
//				}
//			}
//		}
//			
//		return arrayl;
//	}


	/**
	 * パスワードの更新.<br /><br />
	 * 
	 * <b>1.審査員情報の取得</b><br /><br />
	 * 以下のSQL文を発行して、審査員情報を取得する。<br />
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数addInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数addInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 
	 * <b>2.パスワードの再設定</b><br /><br />
	 * 以下のSQL文よりパスワード発行ルールを取得し、パスワードの再発行を行う。<br />
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	RULEINFO A
	 * WHERE
	 * 	TAISHO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数primaryKeysの変数TaishoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 以下のSQL文より、再発行したパスワードを登録する。
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAININFO
	 * SET
	 * 	PASSWORD = ?
	 * WHERE
	 * 	SUBSTR(SHINSAIN_ID,3,8) = ?		--事業区分+審査員番号(7桁)
	 * 	AND DEL_FLG = 0			--削除フラグ</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>新パスワード</td><td>再発行したnewPasswordを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数pkInfoの変数JigyoKubunとShinsainNoを連結したものを使用する。</td></tr>
	 * </table><br />
	 * 
	 * <b>3.審査員情報の取得</b><br /><br />
	 * 以下のSQL文を発行して、再度審査員情報を取得し、返却する。<br />
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数addInfoの変数ShinsainNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数addInfoの変数JigyoKubunを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値をShinsainInfoに格納して返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo ShinsainPk
	 * @return ShinsainInfo
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#reconfigurePassword(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainPk)
	 */
	public ShinsainInfo reconfigurePassword(UserInfo userInfo, ShinsainPk pkInfo) throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//審査員情報の取得
			//---------------------------------------
			ShinsainInfoDao dao = new ShinsainInfoDao(userInfo);
			ShinsainInfo info = dao.selectShinsainInfo(connection, pkInfo);

			//RULEINFOテーブルよりルール取得準備
			RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHINSAIN);

			//パスワードを再設定する
			String newPassword = rureInfoDao.getPassword(connection, rulePk);
			success = dao.changePasswordShinsainInfo(connection,pkInfo,newPassword);

			//---------------------------------------
			//審査員データの取得
			//---------------------------------------
			ShinsainInfo result = dao.selectShinsainInfo(connection, pkInfo);
			
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

	/**
	 * 依頼書の作成.<br /><br />
	 * 
	 * 
	 * <b>1.CSVデータのListの取得</b><br /><br />
	 * 
	 * 以下のSQL文を発行して、CSVデータのListを取得する。<br />
	 * SQL文のwhere句には、第二引数の各値がnull又は空文字列でない場合に条件文が加えられていく。<br />
	 * (加えられるSQL文と、条件になる変数はSQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	to_char(substr(MS.shinsain_no,1,4), '9990') ||	--審査員番号
	 * 		substr(MS.shinsain_no,5,1) || '－' ||
	 * 		substr(MS.shinsain_no,6,2)		"審査員番号"
	 * 		--頭５桁(最初の３桁のゼロは省略)と後２桁をハイフンで結合
	 * 
	 * 	,MS.NAME_KANJI_SEI				"審査員名(漢字-姓)"
	 * 	,MS.NAME_KANJI_MEI				"審査員名(漢字-名)"
	 * 	,MS.SHOZOKU_NAME				"審査員所属機関名"
	 * 	,MS.BUKYOKU_NAME				"審査員部局名"
	 * 	,MS.SHOKUSHU_NAME				"審査員職名"
	 * 	,MS.SOFU_ZIP				"送付先(郵便番号)"
	 * 	,MS.SOFU_ZIPADDRESS			"送付先(住所)"
	 * 	,MS.SOFU_ZIPEMAIL				"送付先(Email)"
	 * 	,MS.SHOZOKU_TEL				"電話番号"
	 * 	,MS.SHOZOKU_FAX				"FAX番号"
	 * 	,MS.BIKO					"備考"
	 * 	,SI.SHINSAIN_ID				"審査員ID"
	 * 	,SI.PASSWORD				"パスワード"
	 * 	,TO_CHAR(SI.YUKO_DATE,'YYYY/MM/DD')		"有効期限"
	 * FROM
	 * 	MASTER_SHINSAIN MS, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 
	 *				<b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 *
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)
	 * ORDER BY
	 * 	MS.SHINSAIN_NO</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>審査員番号</td><td>ShinsainNo</td><td>AND MS.SHINSAIN_NO = '審査員番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>審査員名(姓-漢字等)</td><td>NameKanjiSei</td><td>AND MS.NAME_KANJI_SEI LIKE '%審査員名(姓-漢字等)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>審査員名(名-漢字等)</td><td>NameKanjiMei</td><td>AND MS.NAME_KANJI_SEI LIKE '%審査員名(名-漢字等)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属コード</td><td>ShozokuCd</td><td>AND MS.SHOZOKU_CD = '所属コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>細目コード</td><td>BunkaSaimokuCd</td><td>AND (((SUBSTR(MS.SHINSAIN_NO, 1, 4) = '細目コード')AND (SUBSTR(MS.SHINSAIN_NO, 5, 1) IN ('A', 'B')))OR ((SUBSTR(MS.SHINSAIN_NO, 1, 1) = '0')AND (SUBSTR(MS.SHINSAIN_NO, 2, 4) = '細目コード'))</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>JigyoKubun</td><td>AND MS.JIGYO_KUBUN = '事業区分'</td></tr>
	 * </table><br/>
	 * 
	 * 
	 * <b>2.CSVファイルの出力</b><br /><br />
	 * ファイル名、ファイルパスを指定して、CSVファイルの出力を行う。<br /><br />
	 * 　・ファイル名　　…"<b>SHINSAIRAI2</b>"<br />
	 * 　・ファイルパス　…"<b>${shinsei_path}/work/irai2/本日の日付/</b>"<br />
	 * 　　　※${shinsei_path}の値はApplicationSettings.propertiesに設定<br><br>
	 * 
	 * <b>3.ファイルのコピー</b><br /><br />
	 * 
	 * 以下のパスに、ファイルのコピーを行う。<br /><br />
	 * 　・${shinsei_path}/settings/irai2/shinsairai2.doc　→　${shinsei_path}/work/irai2/本日の日付/shinsairai2.doc<br />
	 * 　・${shinsei_path}/settings/irai2/$　　　　　　　　→　${shinsei_path}/work/irai2/本日の日付/$<br />
	 * 　　　※${shinsei_path}の値はApplicationSettings.propertiesに設定<br><br>
	 * 
	 * 
	 * <b>4.ファイルの圧縮</b><br /><br />
	 * 
	 * ファイルパス"${shinsei_path}/work/irai2/本日の日付/"のものを圧縮する。
	 * 圧縮先も同じパスで、ファイル名は"<b>SHINSAIRAI2_本日の日付</b>"となる。<br /><br />
	 * 
	 * 
	 * <b>5.ファイルの返却</b><br /><br />
	 * 作成した圧縮ファイルを読み込み、返却する。<br /><br />
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsainSearchInfo
	 * @return FileResource
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#createIraisho(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainSearchInfo)
	 */
	public FileResource createIraisho(
		UserInfo userInfo,
		ShinsainSearchInfo searchInfo)
		throws ApplicationException
	{
		//DBレコード取得
		List csv_data = null;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			csv_data = new ShinsainInfoDao(userInfo).createCSV4Iraisho(connection, searchInfo);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		//-----------------------
		// CSVファイル出力
		//-----------------------
		String csvFileName = "SHINSAIRAI2";
		//2005/09/09 takano フォルダ名をミリ秒単位に変更。念のため同時に同期処理も組み込み。
		String filepath = null;
		synchronized(log){
			filepath = IRAI_WORK_FOLDER2 + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";	
		}
		CsvUtil.output(csv_data, filepath, csvFileName);
		
		//-----------------------
		// 依頼書ファイルのコピー
		//-----------------------
		FileUtil.fileCopy(new File(IRAI_FORMAT_PATH2 + IRAI_FORMAT_FILE_NAME2), new File(filepath + IRAI_FORMAT_FILE_NAME2));
		FileUtil.fileCopy(new File(IRAI_FORMAT_PATH2 + "$"), new File(filepath + "$"));
		
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

}
