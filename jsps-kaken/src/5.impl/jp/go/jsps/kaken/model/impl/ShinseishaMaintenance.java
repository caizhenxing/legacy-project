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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.IBukyokutantoMaintenance;
import jp.go.jsps.kaken.model.IShinseishaMaintenance;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.MasterBukyokuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterShokushuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.RuleInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseishaInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.BukyokuInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoPk;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.KikanInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaPk;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.model.vo.ShokushuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.CheckDiditUtil;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 申請者情報管理を行うクラス。
 * 
 * 使用しているテーブル<br /><br />
 * 　・申請者情報テーブル:SHINSEISHAINFO<br />
 * 　　　申請者の基本情報を管理する。<br /><br />
 * 　・所属機関マスタ:MASTER_KIKAN<br />
 * 　　　所属機関の情報を管理する。<br /><br />
 * 　・所属機関担当者情報テーブル:SHOZOKUTANTOINFO<br />
 * 　　　所属機関担当者の基本情報を管理する。<br /><br />
 * 　・部局マスタ:MASTER_BUKYOKU<br />
 * 　　　部局の情報を管理する。<br /><br />
 * 　・職種マスタ:MASTER_SHOKUSHU<br />
 * 　　　職種を管理する。<br /><br />
 * 　・ＩＤパスワード発行ルールテーブル:RULEINFO<br />
 * 　　　各ID・パスワードの発行ルールを管理する。<br /><br />
 */
public class ShinseishaMaintenance implements IShinseishaMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(ShinseishaMaintenance.class);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public ShinseishaMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IShinseishaMaintenance
	//---------------------------------------------------------------------

	/**
	 * 申請者情報の追加.<br /><br />
	 * 申請者情報テーブルに新規でデータを加え、そのデータを返却する。<br /><br />
	 * 
	 * <b>1.情報の追加</b><br /><br />
	 * 第二引数のaddInfoに、次の値を加えていく。<br /><br />
	 * 　(1)申請者ID<br /><br />
	 * 　　"<b>西暦年度(2桁)＋所属機関コード(5桁)+連番(5桁)＋チェックデジット記号(1桁)</b>"のStringを取得し、addInfoに加える。<br /><br />
	 * 
	 * 　　　・西暦年度　　　　　　　…現在の年度を西暦で取得。<br />
	 * 　　　・所属機関コード　　　　…第二引数addInfoから取得。<br />
	 * 　　　・連番　　　　　　　　　…以下のSQL文から取得。<br />
	 * 　　　・チェックデジット記号　…メソッド"<b>getCheckDigit(key,CheckDiditUtil.FORM_ALP)</b>"より取得。<br />
	 * 　　　　　　key…西暦+所属機関コード+連番<br />
	 * 　　　　　　CheckDiditUtil.FORM_ALP…"form_alp"<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	TO_CHAR(MAX(SUBSTR(SHINSEISHA_ID,8,5)) + 1,'FM00000') COUNT
	 * FROM
	 * 	SHINSEISHAINFO
	 * WHERE
	 * 	SHOZOKU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数addInfoの変数ShozokuCdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 　(2)パスワード<br /><br />
	 * 　　以下のSQL文からルールを取得し、パスワードの作成を行う。<br />
	 * 　　(バインド変数は、SQL文の下の表を参照)<br /><br />
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
	 * 		<tr bgcolor="#FFFFFF"><td>対象者ID</td><td>1(ITaishoId.SHINSEISHA)を使用する。</td></tr>
	 * </table><br />
	 * 
	 * 　　取得したパスワードをaddInfoに加える。<br /><br />
	 * 
	 * 　(3)有効日付<br /><br />
	 * 　　addInfoの有効日付の値がnullだった場合のみ、
	 * 上記のSQL文を発行して、取得した値から有効日付をaddInfoに格納する。<br /><br />
	 * 
	 * 
	 * 　(4)非公募フラグ<br /><br />
	 * 　　addInfoの非公募フラグの値がnull又は空文字列だった場合のみ、"0"(非公募申請不可)を格納する。<br /><br />
	 * 
	 * 
	 * <b>2.申請者情報の挿入</b><br /><br />
	 * 
	 * 以下のSQL文を発行して、申請者情報テーブルにデータを挿入する。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * INSERT INTO SHINSEISHAINFO
	 * 	(SHINSEISHA_ID
	 * 	, SHOZOKU_CD
	 * 	, SHOZOKU_NAME
	 * 	, SHOZOKU_NAME_EIGO
	 * 	, SHOZOKU_NAME_RYAKU
	 * 	, PASSWORD
	 * 	, NAME_KANJI_SEI
	 * 	, NAME_KANJI_MEI
	 * 	, NAME_KANA_SEI
	 * 	, NAME_KANA_MEI
	 * 	, NAME_RO_SEI
	 * 	, NAME_RO_MEI
	 * 	, BUKYOKU_CD
	 * 	, BUKYOKU_NAME
	 * 	, BUKYOKU_NAME_RYAKU
	 * 	, KENKYU_NO
	 * 	, SHOKUSHU_CD
	 * 	, SHOKUSHU_NAME_KANJI
	 * 	, SHOKUSHU_NAME_RYAKU
	 * 	, HIKOBO_FLG
	 * 	, BIKO
	 * 	, YUKO_DATE
	 * 	, DEL_FLG)
	 * VALUES
	 * 	(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者ID</td><td>第二引数addInfoの変数ShinseishaIdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数addInfoの変数ShozokuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名(和文)</td><td>第二引数addInfoの変数ShozokuNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名(英文)</td><td>第二引数addInfoの変数ShozokuNameEigoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名(略称)</td><td>第二引数addInfoの変数ShozokuNameRyakuを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>パスワード</td><td>第二引数addInfoの変数Passwordを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(漢字等-姓)</td><td>第二引数addInfoの変数NameKanjiSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(漢字等-名)</td><td>第二引数addInfoの変数NameKanjiSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(フリガナ-姓)</td><td>第二引数addInfoの変数NameKanaSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(フリガナ-名)</td><td>第二引数addInfoの変数NameKanaSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(ローマ字-姓)</td><td>第二引数addInfoの変数NameRoSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(ローマ字-名)</td><td>第二引数addInfoの変数NameRoSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名(コード)</td><td>第二引数addInfoの変数BukyokuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名(和文)</td><td>第二引数addInfoの変数BukyokuNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名(略称)</td><td>第二引数addInfoの変数BukyokuNameRyakuを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数addInfoの変数KenkyuNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名コード</td><td>第二引数addInfoの変数ShokushuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名(和文)</td><td>第二引数addInfoの変数ShokushuNameKanjiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名(略称)</td><td>第二引数addInfoの変数ShokushuNameRyakuを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>非公募フラグ</td><td>第二引数addInfoの変数HikoboFlgを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>第二引数addInfoの変数Bikoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>有効日付</td><td>第二引数addInfoの変数YukoDateを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>0</td></tr>
	 * </table><br />
	 * 
	 * <b>3.申請者情報の取得</b><br /><br />
	 * 
	 * 以下のSQL文を発行して、申請者情報テーブルからデータを取得する。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SHINSEISHA_ID
	 * 	,A.SHOZOKU_CD
	 * 	,NVL(A.SHOZOKU_NAME,'') SHOZOKU_NAME
	 * 	,NVL(A.SHOZOKU_NAME_EIGO,'') SHOZOKU_NAME_EIGO
	 * 	,NVL(A.SHOZOKU_NAME_RYAKU,'') SHOZOKU_NAME_RYAKU
	 * 	,A.PASSWORD
	 * 	,NVL(A.NAME_KANJI_SEI,'') NAME_KANJI_SEI
	 * 	,NVL(A.NAME_KANJI_MEI,'') NAME_KANJI_MEI
	 * 	,NVL(A.NAME_KANA_SEI,'') NAME_KANA_SEI
	 * 	,NVL(A.NAME_KANA_MEI,'') NAME_KANA_MEI
	 * 	,NVL(A.NAME_RO_SEI,'') NAME_RO_SEI
	 * 	,NVL(A.NAME_RO_MEI,'') NAME_RO_MEI
	 * 	,A.BUKYOKU_CD
	 * 	,NVL(A.BUKYOKU_NAME,'') BUKYOKU_NAME
	 * 	,NVL(A.BUKYOKU_NAME_RYAKU,'') BUKYOKU_NAME_RYAKU
	 * 	,A.KENKYU_NO
	 * 	,A.SHOKUSHU_CD
	 * 	,NVL(A.SHOKUSHU_NAME_KANJI,'') SHOKUSHU_NAME_KANJI
	 * 	,NVL(A.SHOKUSHU_NAME_RYAKU,'') SHOKUSHU_NAME_RYAKU
	 * 	,A.HIKOBO_FLG
	 * 	,NVL(A.BIKO,'') BIKO
	 * 	,A.YUKO_DATE
	 * 	,A.DEL_FLG
	 * FROM
	 * 	SHINSEISHAINFO A
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者ID</td><td>第二引数addInfoの変数ShinseishaIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値をShinseishaInfoに格納し、返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param addInfo ShinseishaInfo
	 * @return 登録した申請者情報を持つShinseishaInfo
	 * @throws ApplicationException
	 */
	public synchronized ShinseishaInfo insert(UserInfo userInfo, ShinseishaInfo addInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

            //---------------------------------------
            //申請者情報データアクセスオブジェクト
            //---------------------------------------
            ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);

			//---------------------------------------
			//キー情報の（申請者ID）を作成
			//西暦年度（2桁）+ 所属機関コード（5桁）+ 連番（5桁）+ チェックデジット（１桁）
			//---------------------------------------
			String wareki = new DateUtil().getNendo();
			String key = DateUtil.changeWareki2Seireki(wareki)
						+ addInfo.getShozokuCd()
						+ dao.getSequenceNo(connection,addInfo.getShozokuCd());
			
            //申請者IDを取得					
			String shinseishaId = key +  CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
			addInfo.setShinseishaId(shinseishaId);

			//RULEINFOテーブルよりルール取得準備
			RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHINSEISHA);
			
			//---------------------------------------
			//パスワード情報の作成
			//---------------------------------------
			String newPassword = rureInfoDao.getPassword(connection, rulePk);
			addInfo.setPassword(newPassword);

			//---------------------------------------
			//有効日付情報情報の作成(担当者用)
			//---------------------------------------
			if(addInfo.getYukoDate() == null) {
				Date newYukoDate = rureInfoDao.selectRuleInfo(connection, rulePk).getYukoDate();
				addInfo.setYukoDate(newYukoDate);
			}

			//---------------------------------------
			//非公募応募可フラグ(担当者用)
			//---------------------------------------
			if(addInfo.getHikoboFlg() == null || addInfo.getHikoboFlg().equals("")) {
				addInfo.setHikoboFlg("0");
			}

			//---------------------------------------
			//申請者情報の追加
			//---------------------------------------
			dao.insertShinseishaInfo(connection,addInfo);		

			//---------------------------------------
			//登録データの取得
			//---------------------------------------
			ShinseishaInfo result = dao.selectShinseishaInfo(connection, addInfo);

			//---------------------------------------
			//登録正常終了
			//---------------------------------------
			success = true;			
			
			return result;
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"申請者管理データ登録中にDBエラーが発生しました。",
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
					"申請者管理データ登録中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 申請者情報の更新.<br /><br />
	 * 
	 * 以下のSQL文を発行して、申請者情報テーブルの更新を行う。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSEISHAINFO
	 * SET
	 * 	SHOZOKU_CD = ?
	 * 	, SHOZOKU_NAME = ?
	 * 	, SHOZOKU_NAME_EIGO = ?
	 * 	, SHOZOKU_NAME_RYAKU = ?
	 * 	, PASSWORD = ?
	 * 	, NAME_KANJI_SEI = ?
	 * 	, NAME_KANJI_MEI = ?
	 * 	, NAME_KANA_SEI = ?
	 * 	, NAME_KANA_MEI = ?
	 * 	, NAME_RO_SEI = ?
	 * 	, NAME_RO_MEI = ?
	 * 	, BUKYOKU_CD = ?
	 * 	, BUKYOKU_NAME= ?
	 * 	, BUKYOKU_NAME_RYAKU= ?
	 * 	, KENKYU_NO = ?
	 * 	, SHOKUSHU_CD = ?
	 * 	, SHOKUSHU_NAME_KANJI = ?
	 * 	, SHOKUSHU_NAME_RYAKU = ?
	 * 	, HIKOBO_FLG = ?
	 * 	, BIKO = ?
	 * 	, YUKO_DATE = ?
	 * 	, DEL_FLG = ?
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数updateInfoの変数ShozokuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名(和文)</td><td>第二引数updateInfoの変数ShozokuNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名(英文)</td><td>第二引数updateInfoの変数ShozokuNameEigoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名(略称)</td><td>第二引数updateInfoの変数ShozokuNameRyakuを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>パスワード</td><td>第二引数updateInfoの変数Passwordを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(漢字等-姓)</td><td>第二引数updateInfoの変数NameKanjiSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(漢字等-名)</td><td>第二引数updateInfoの変数NameKanjiSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(フリガナ-姓)</td><td>第二引数updateInfoの変数NameKanaSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(フリガナ-名)</td><td>第二引数updateInfoの変数NameKanaSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(ローマ字-姓)</td><td>第二引数updateInfoの変数NameRoSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(ローマ字-名)</td><td>第二引数updateInfoの変数NameRoSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名(コード)</td><td>第二引数updateInfoの変数BukyokuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名(和文)</td><td>第二引数updateInfoの変数BukyokuNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名(略称)</td><td>第二引数updateInfoの変数BukyokuNameRyakuを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数updateInfoの変数KenkyuNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名コード</td><td>第二引数updateInfoの変数ShokushuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名(和文)</td><td>第二引数updateInfoの変数ShokushuNameKanjiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名(略称)</td><td>第二引数updateInfoの変数ShokushuNameRyakuを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>非公募フラグ</td><td>第二引数updateInfoの変数HikoboFlgを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>第二引数updateInfoの変数Bikoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>有効日付</td><td>第二引数updateInfoの変数YukoDateを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>0</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者ID</td><td>第二引数updateInfoの変数ShinseishaIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param updateInfo ShinseishaInfo
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo, ShinseishaInfo updateInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//申請者情報の更新
			//---------------------------------------
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			dao.updateShinseishaInfo(connection, updateInfo);
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"申請者管理データ更新中にDBエラーが発生しました。",
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
					"申請者管理データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 申請者情報の削除.<br /><br />
	 * 
	 * 以下のSQL文を発行して、申請者情報テーブルの論理削除を行う。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSEISHAINFO
	 * SET
	 * 	DEL_FLG = 1
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者ID</td><td>第二引数deleteInfoの変数ShinseishaIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param deleteInfo ShinseishaInfo
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo, ShinseishaInfo deleteInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//申請者情報の更新
			//---------------------------------------
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			dao.deleteFlgShinseishaInfo(connection, deleteInfo);
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"申請者管理データ削除中にDBエラーが発生しました。",
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
					"申請者管理データ削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 申請者情報の取得.<br /><br />
	 * 
	 * 以下のSQL文を発行して、申請者情報テーブルからデータを取得する。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SHINSEISHA_ID
	 * 	,A.SHOZOKU_CD
	 * 	,NVL(A.SHOZOKU_NAME,'') SHOZOKU_NAME
	 * 	,NVL(A.SHOZOKU_NAME_EIGO,'') SHOZOKU_NAME_EIGO
	 * 	,NVL(A.SHOZOKU_NAME_RYAKU,'') SHOZOKU_NAME_RYAKU
	 * 	,A.PASSWORD
	 * 	,NVL(A.NAME_KANJI_SEI,'') NAME_KANJI_SEI
	 * 	,NVL(A.NAME_KANJI_MEI,'') NAME_KANJI_MEI
	 * 	,NVL(A.NAME_KANA_SEI,'') NAME_KANA_SEI
	 * 	,NVL(A.NAME_KANA_MEI,'') NAME_KANA_MEI
	 * 	,NVL(A.NAME_RO_SEI,'') NAME_RO_SEI
	 * 	,NVL(A.NAME_RO_MEI,'') NAME_RO_MEI
	 * 	,A.BUKYOKU_CD
	 * 	,NVL(A.BUKYOKU_NAME,'') BUKYOKU_NAME
	 * 	,NVL(A.BUKYOKU_NAME_RYAKU,'') BUKYOKU_NAME_RYAKU
	 * 	,A.KENKYU_NO
	 * 	,A.SHOKUSHU_CD
	 * 	,NVL(A.SHOKUSHU_NAME_KANJI,'') SHOKUSHU_NAME_KANJI
	 * 	,NVL(A.SHOKUSHU_NAME_RYAKU,'') SHOKUSHU_NAME_RYAKU
	 * 	,A.HIKOBO_FLG
	 * 	,NVL(A.BIKO,'') BIKO
	 * 	,A.YUKO_DATE
	 * 	,A.DEL_FLG
	 * FROM
	 * 	SHINSEISHAINFO A
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者ID</td><td>第二引数ShinseishaPkの変数ShinseishaIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値をShinseishaInfoに格納し、返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo ShinseishaPk
	 * @return ShinseishaInfo
	 * @throws ApplicationException
	 */
	public ShinseishaInfo select(UserInfo userInfo, ShinseishaPk pkInfo)
			throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			return dao.selectShinseishaInfo(connection, pkInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"申請者管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * CSVデータListの取得.<br /><br />
	 * 
	 * 以下のSQL文を発行して、CSV出力データの取得を行い、返却する。<br />
	 * Listの初めの要素には、カラム名が入る。<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SHINSEISHA_ID		"申請者ID"			--ID
	 * 	, A.SHOZOKU_CD		"所属機関名(コード)"	-所属機関名(コード)
	 * 	, A.SHOZOKU_NAME		"所属機関名(和文)"	--所属機関名(和文)
	 * 	, A.SHOZOKU_NAME_EIGO	"所属機関名(英文)"	--所属機関名(英文)
	 * 	, A.SHOZOKU_NAME_RYAKU	"所属機関名(略称)"	--所属機関名(略称)
	 * 	, A.NAME_KANJI_SEI		"氏名(漢字等-姓)"	--氏名(漢字等-姓)
	 * 	, A.NAME_KANJI_MEI		"氏名(漢字等-名)"	--氏名(漢字等-名)
	 * 	, A.NAME_KANA_SEI		"氏名(フリガナ-姓)"	--氏名(フリガナ-姓)
	 * 	, A.NAME_KANA_MEI		"氏名(フリガナ-名)"	--氏名(フリガナ-名)
	 * 	, A.NAME_RO_SEI		"氏名(ローマ字-姓)"	--氏名(ローマ字-姓)
	 * 	, A.NAME_RO_MEI		"氏名(ローマ字-名)"	--氏名(ローマ字-名)
	 * 	, A.KENKYU_NO		"研究者番号"		--研究者番号
	 * 	, A.BUKYOKU_CD		"部局名(コード)"	--部局名(コード)
	 * 	, A.BUKYOKU_NAME		"部局名(和文)"	--部局名(和文)
	 * 	, A.BUKYOKU_NAME_RYAKU	"部局名(略称)"	--部局名(略称)
	 * 	, A.SHOKUSHU_CD		"職名(コード)"		--職名(コード)
	 * 	, A.SHOKUSHU_NAME_KANJI	"職名(和文)"	--職名(和文)
	 * 	, A.SHOKUSHU_NAME_RYAKU	"職名(略称)"	--職名(略称)
	 * 	, A.HIKOBO_FLG		"非公募申請可フラグ"--非公募申請課フラグ
	 * 	, A.BIKO			"備考"			--備考
	 * 	, TO_CHAR(A.YUKO_DATE,
	 * 		'YYYY/MM/DD')	"有効期限"		--有効期限
	 * FROM
	 * 	SHINSEISHAINFO A
	 * WHERE
	 * 	DEL_FLG = 0				--削除フラグ
	 * 
	 * 	<b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 * 
	 * ORDER BY
	 * 	SHINSEISHA_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者ID</td><td>ShinseishaId</td><td>AND SHINSEISHA_ID = '申請者ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名(コード)</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '所属機関名(コード)'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名</td><td>ShozokuName</td><td>AND (SHOZOKU_NAME LIKE '%所属機関名%' OR SHOZOKU_NAME_RYAKU LIKE '%所属機関名%')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(漢字-姓)</td><td>NameKanjiSei</td><td>AND NAME_KANJI_SEI LIKE '%申請者氏名(漢字-姓)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(漢字-名)</td><td>NameKanjiMei</td><td>AND NAME_KANJI_MEI LIKE '%申請者氏名(漢字-名)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(フリガナ-姓)</td><td>NameKanaSei</td><td>AND NAME_KANA_SEI LIKE '%申請者氏名(フリガナ-姓)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(フリガナ-名)</td><td>NameKanaMei</td><td>AND NAME_KANA_MEI LIKE '%申請者氏名(フリガナ-名)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(ローマ字-姓)</td><td>NameRoSei</td><td>AND UPPER(NAME_RO_SEI) LIKE '%申請者氏名(ローマ字-姓)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(ローマ字-名)</td><td>NameRoMei</td><td>AND UPPER(NAME_RO_SEI) LIKE '%申請者氏名(ローマ字-姓)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>部局コード</td><td>BukyokuCd</td><td>AND BUKYOKU_CD = '部局コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>研究者番号</td><td>KenkyuNo</td><td>AND KENKYU_NO = '研究者番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>非公募フラグ</td><td>HikoboFlg</td><td>AND HIKOBO_FLG = '非公募フラグ'</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseishaSearchInfo
	 * @return CSV出力データのList
	 * @throws ApplicationException
	 */
	public List searchCsvData(UserInfo userInfo, ShinseishaSearchInfo searchInfo)
			throws ApplicationException {
	
		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT "
				+ " A.SHINSEISHA_ID \"応募者ID\""						//ID
				+ ", A.SHOZOKU_CD \"所属研究機関名（番号）\""				//所属機関名（コード）
				+ ", A.SHOZOKU_NAME \"所属研究機関名（和文）\""			//所属機関名（和文）
				+ ", A.SHOZOKU_NAME_EIGO \"所属研究機関名（英文）\""		//所属機関名（英文）
				+ ", A.SHOZOKU_NAME_RYAKU \"所属研究機関名（略称）\""		//所属機関名（略称）
//				+ ", A.PASSWORD \"パスワード\""							//パスワード
				+ ", A.NAME_KANJI_SEI \"氏名（漢字等-姓）\""				//氏名（漢字等-姓）
				+ ", A.NAME_KANJI_MEI \"氏名（漢字等-名）\""				//氏名（漢字等-名）
				+ ", A.NAME_KANA_SEI \"氏名（フリガナ-姓）\""				//氏名（フリガナ-姓）
				+ ", A.NAME_KANA_MEI \"氏名（フリガナ-名）\""				//氏名（フリガナ-名）
				//2005/04/23 修正 -----------------------------ここから
				//理由 csv出力仕様変更のため
//				+ ", A.NAME_RO_SEI \"氏名（ローマ字-姓）\""				//氏名（ローマ字-姓）
//				+ ", A.NAME_RO_MEI \"氏名（ローマ字-名）\""				//氏名（ローマ字-名）
				//2005/04/23 修正 -----------------------------ここまで
				+ ", A.KENKYU_NO \"研究者番号\""							//研究者番号
				+ ", A.BUKYOKU_CD \"部局名（番号）\""					//部局名（コード）
				+ ", A.BUKYOKU_NAME \"部局名（和文）\""					//部局名（和文）
				+ ", A.BUKYOKU_NAME_RYAKU \"部局名（略称）\""			//部局名（略称）
//				+ ", A.SHUBETU_CD \"部局種別（コード）\""					//部局種別（コード）
//				+ ", A.OTHER_BUKYOKU \"部局種別\""						//部局種別
				+ ", A.SHOKUSHU_CD \"職名（番号）\""						//職名（コード）
				+ ", A.SHOKUSHU_NAME_KANJI \"職名（和文）\""				//職名（和文）
				+ ", A.SHOKUSHU_NAME_RYAKU \"職名（略称）\""				//職名（略称）
				+ ", A.HIKOBO_FLG \"非公募応募可フラグ\""					//非公募応募可フラグ
				+ ", A.BIKO \"備考\""									//備考
				+ ", TO_CHAR(A.YUKO_DATE, 'YYYY/MM/DD') \"有効期限\""	//有効期限
				//2005/04/23 追加 -----------------------------ここから
				//理由 csv出力仕様変更のため
				+ ", TO_CHAR(A.BIRTHDAY, 'YYYY/MM/DD') \"生年月日\""	//生年月日
				+ ", A.HAKKOSHA_ID \"発行者ID\""					//発行者ID
				+ ", TO_CHAR(A.HAKKO_DATE, 'YYYY/MM/DD') \"発行日\""	//発行日
				+ ", B.SEIBETSU \"性別\""					//性別
				+ ", B.GAKUI \"学位\""					//学位
				//2006/02/10 追加
				+",  B.OUBO_SHIKAKU \"応募資格\""    //応募資格
				+ ", TO_CHAR(B.KOSHIN_DATE, 'YYYY/MM/DD') \"データ更新日\""					//データ更新日
				//2005/04/23 追加 -----------------------------ここまで
//				+ ", A.DEL_FLG \"削除フラグ\""							//削除フラグ
				//2005.07.27 iso 異なる所属機関で同じ研究者番号が複数いると出力データが多重出力されるバグを修正
//				+ " FROM SHINSEISHAINFO A LEFT JOIN MASTER_KENKYUSHA B ON A.KENKYU_NO = B.KENKYU_NO"
				//+ " FROM SHINSEISHAINFO A LEFT JOIN MASTER_KENKYUSHA B ON A.KENKYU_NO = B.KENKYU_NO AND A.SHOZOKU_CD = B.SHOZOKU_CD"
				//2005/09/12 研究者マスタに存在しない場合は応募者一覧に表示しない
				+ " FROM SHINSEISHAINFO A "
				+ "    INNER JOIN MASTER_KENKYUSHA B "
				+ "      ON  A.KENKYU_NO  = B.KENKYU_NO "
				+ "      AND A.SHOZOKU_CD = B.SHOZOKU_CD "
				+ "      AND B.DEL_FLG    = 0 "
				+ " WHERE A.DEL_FLG = 0"									//削除フラグ
				;
				StringBuffer query = new StringBuffer(select);

			if(searchInfo.getShinseishaId() != null && !searchInfo.getShinseishaId().equals("")){	//申請者ID
				query.append(" AND A.SHINSEISHA_ID = '" + EscapeUtil.toSqlString(searchInfo.getShinseishaId()) +"'");
			}
			if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){		//所属機関コード
				query.append(" AND A.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) +"'");
			}
			//所属機関名は和文と略称の両方を検索する
			if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){	//所属機関名
				query.append(" AND (A.SHOZOKU_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName())
					+ "%' OR A.SHOZOKU_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%')");
			}
			if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	//申請者氏名（漢字-姓）
				query.append(" AND A.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
			}
			if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	//申請者氏名（漢字-名）
				query.append(" AND A.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
			}
			if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	//申請者氏名（フリガナ-姓）
				query.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
			}
			if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){	//申請者氏名（フリガナ-名）
				query.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
			}
			//2005/04/27 削除 ここから-----------------------------------------------
			//不要条件のため
			/*
			if(searchInfo.getNameRoSei() != null && !searchInfo.getNameRoSei().equals("")){		//申請者氏名（ローマ字-姓）
				query.append(" AND UPPER(A.NAME_RO_SEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()) + "%'");
			}
			if(searchInfo.getNameRoMei() != null && !searchInfo.getNameRoMei().equals("")){		//申請者氏名（ローマ字-名）
				query.append(" AND UPPER(A.NAME_RO_MEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()) + "%'");
			}
			*/
			//削除 ここまで---------------------------------------------------------
			if(searchInfo.getBukyokuCd() != null && !searchInfo.getBukyokuCd().equals("")){		//部局コード
				query.append(" AND A.BUKYOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getBukyokuCd()) +"'");
			}
			if(searchInfo.getKenkyuNo() != null && !searchInfo.getKenkyuNo().equals("")){			//研究者番号
				query.append(" AND A.KENKYU_NO = '" + EscapeUtil.toSqlString(searchInfo.getKenkyuNo()) +"'");
			}
			if(searchInfo.getHikoboFlg() != null && !searchInfo.getHikoboFlg().equals("")){		//非公募申請課フラグ
				query.append(" AND A.HIKOBO_FLG = '" + EscapeUtil.toSqlString(searchInfo.getHikoboFlg()) +"'");
			}

			//ソート順（申請者IDの昇順）
			query.append(" ORDER BY A.SHINSEISHA_ID");

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
	 * 申請者のPage情報の取得.<br /><br />
	 * 
	 * 以下のSQL文を発行して、申請者のPage情報を取得する。
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	SHINSEISHAINFO A
	 * 
	 * INNER JOIN --------------------------------担当部局管理テーブルにデータがある場合に追加 
	 * 	TANTOBUKYOKUKANRI TANTO 
	 * ON 
	 * 	TANTO.SHOZOKU_CD = SHINSEI.SHOZOKU_CD 
	 * AND 
	 * TANTO.BUKYOKU_CD = SHINSEI.BUKYOKU_CD
	 * 
	 * 
	 * WHERE
	 * 	DEL_FLG = 0
	 * 
	 * 	<b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 * 
	 * ORDER BY
	 * 	SHINSEISHA_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者ID</td><td>ShinseishaId</td><td>AND SHINSEISHA_ID = '申請者ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名(コード)</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '所属機関名(コード)'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名</td><td>ShozokuName</td><td>AND (SHOZOKU_NAME LIKE '%所属機関名%' OR SHOZOKU_NAME_RYAKU LIKE '%所属機関名%')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(漢字-姓)</td><td>NameKanjiSei</td><td>AND NAME_KANJI_SEI LIKE '%申請者氏名(漢字-姓)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(漢字-名)</td><td>NameKanjiMei</td><td>AND NAME_KANJI_MEI LIKE '%申請者氏名(漢字-名)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(フリガナ-姓)</td><td>NameKanaSei</td><td>AND NAME_KANA_SEI LIKE '%申請者氏名(フリガナ-姓)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(フリガナ-名)</td><td>NameKanaMei</td><td>AND NAME_KANA_MEI LIKE '%申請者氏名(フリガナ-名)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(ローマ字-姓)</td><td>NameRoSei</td><td>AND UPPER(NAME_RO_SEI) LIKE '%申請者氏名(ローマ字-姓)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(ローマ字-名)</td><td>NameRoMei</td><td>AND UPPER(NAME_RO_SEI) LIKE '%申請者氏名(ローマ字-姓)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>部局コード</td><td>BukyokuCd</td><td>AND BUKYOKU_CD = '部局コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>研究者番号</td><td>KenkyuNo</td><td>AND KENKYU_NO = '研究者番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>非公募フラグ</td><td>HikoboFlg</td><td>AND HIKOBO_FLG = '非公募フラグ'</td></tr>
	 * </table><br />
	 * 
	 * 取得した値は、列名をキーにMapにセットされ、Listにセットされる。
	 * そのListを格納したPageを返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseishaSearchInfo
	 * @return 申請者情報のPage
	 */
	public Page search(UserInfo userInfo, ShinseishaSearchInfo searchInfo)
			throws ApplicationException {
		
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return search(userInfo, searchInfo, connection);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
//ADD　START 2007/07/25 BIS 金京浩  
	public Page getKenkyushaInfoByKenkyuNo(UserInfo userInfo, ShinseishaSearchInfo searchInfo)
			throws ApplicationException {
		
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return getKenkyushaInfoByKenkyuNo(userInfo, searchInfo, connection);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/** 取得した値は、列名をキーにMapにセットされ、Listにセットされる。	 
	 * そのListを格納したPageを返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseishaSearchInfo
	 * @return 研究者情報のPage
	 */
	protected Page getKenkyushaInfoByKenkyuNo(UserInfo userInfo, ShinseishaSearchInfo searchInfo, Connection connection)
	throws ApplicationException {
	
		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		String select = 
			"SELECT * FROM (MASTER_KENKYUSHA KENKYU) "
			;
			select += "WHERE ";
		
		StringBuffer query = new StringBuffer(select);
		
		query.append(" KENKYU.KENKYU_NO = '" + EscapeUtil.toSqlString(searchInfo.getKenkyuNo()) +"'");//研究者番号
				
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ページ取得
		//-----------------------
		try {
			return SelectUtil.selectPageInfo(connection,searchInfo, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"研究者管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		}
	}	
	
//ADD　END　 2007/07/25 BIS 金京浩	
	/**
	 * 申請者のPage情報の取得.<br /><br />
	 * 同一トランザクションで実施したい場合用。
	 * @param userInfo
	 * @param searchInfo
	 * @param connection
	 * @return
	 * @throws ApplicationException
	 */
	protected Page search(UserInfo userInfo, ShinseishaSearchInfo searchInfo, Connection connection)
			throws ApplicationException {
		
		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		
		//2005/04/20 追加 ここから----------------------------------------------
		//部局担当時に許可された部局かどうかの条件を追加
		if(userInfo.getRole().equals(UserRole.BUKYOKUTANTO)){
			//部局担当者のとき、検索条件の部局コードが自分の担当かチェックする
			BukyokutantoInfo info = userInfo.getBukyokutantoInfo();
			
			if(info.getTantoFlg()){
				if(searchInfo.getBukyokuCd() != null && !searchInfo.getBukyokuCd().equals("")){
					IBukyokutantoMaintenance bukyokutantoMaintenance = new BukyokutantoMaintenance();
					
					//キーのセット
					BukyokutantoPk pkInfo = new BukyokutantoPk();
					pkInfo.setBukyokutantoId(info.getBukyokutantoId());
					pkInfo.setBukyokuCd(searchInfo.getBukyokuCd());
		            
					BukyokutantoInfo[] tanto = bukyokutantoMaintenance.select(userInfo,pkInfo);
		            
					if(tanto.length == 0){
						throw new NoDataFoundException(
								"ログインユーザの担当する部局ではありません。"
									+ "検索キー：部局担当者ID'" + pkInfo.getBukyokutantoId() + "'"
									+ " 担当部局コード'" + pkInfo.getBukyokuCd()
									+ "'", new ErrorInfo("errors.authority"));
					}
				}
			}
		}
		//追加 ここまで---------------------------------------------------------		
		
		// 2005/04/08 削除　ここから--------------------------------------------
		// 理由 部局担当者の申請者情報取得のため
		// String select= "SELECT * FROM SHINSEISHAINFO SHINSEI WHERE DEL_FLG = 0";
		// 削除 ここまで--------------------------------------------------------
		
		// 2005/04/08 追加　ここから-------------------------------------------
		// 理由 部局担当者の申請者情報取得のため
		String select = 
		//2005/09/12 研究者マスタに存在しない場合は応募者一覧に表示しない
		//"SELECT * FROM SHINSEISHAINFO SHINSEI ";
		"SELECT * FROM (SHINSEISHAINFO SHINSEI "
						+" INNER JOIN MASTER_KENKYUSHA MK "
						+" ON  SHINSEI.KENKYU_NO  = MK.KENKYU_NO "
						+" AND SHINSEI.SHOZOKU_CD = MK.SHOZOKU_CD "
						+" AND MK.DEL_FLG = 0 ) "
						;
		if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
			select = select 
						+ "INNER JOIN TANTOBUKYOKUKANRI TANTO " 
						+ "ON TANTO.SHOZOKU_CD = SHINSEI.SHOZOKU_CD "
						+ "AND TANTO.BUKYOKU_CD = SHINSEI.BUKYOKU_CD "
						//2005/04/20　追加 ここから------------------------------------------
						//理由 条件不足のため 
						+ "AND TANTO.BUKYOKUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())+"' ";
						//追加 ここまで------------------------------------------------------
		}
			select += "WHERE SHINSEI.DEL_FLG = 0";
		//	追加 ここまで--------------------------------------------------------
			
		StringBuffer query = new StringBuffer(select);
	
		if(searchInfo.getShinseishaId() != null && !searchInfo.getShinseishaId().equals("")){	//申請者ID
			query.append(" AND SHINSEI.SHINSEISHA_ID = '" + EscapeUtil.toSqlString(searchInfo.getShinseishaId()) +"'");
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){		//所属機関コード
			query.append(" AND SHINSEI.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) +"'");
		}
		//所属機関名は和文と略称の両方を検索する
		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){	//所属機関名
			query.append(" AND (SHINSEI.SHOZOKU_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName())
				+ "%' OR SHINSEI.SHOZOKU_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%')");
		}
		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	//申請者氏名（漢字-姓）
			query.append(" AND SHINSEI.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	//申請者氏名（漢字-名）
			query.append(" AND SHINSEI.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	//申請者氏名（フリガナ-姓）
			query.append(" AND SHINSEI.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
		}
		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){	//申請者氏名（フリガナ-名）
			query.append(" AND SHINSEI.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
		}
		//2005/04/27 削除 ここから-----------------------------------------------
		//不要条件のため
		/*
		if(searchInfo.getNameRoSei() != null && !searchInfo.getNameRoSei().equals("")){		//申請者氏名（ローマ字-姓）
			query.append(" AND SHINSEI.UPPER(NAME_RO_SEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()) + "%'");
		}
		if(searchInfo.getNameRoMei() != null && !searchInfo.getNameRoMei().equals("")){		//申請者氏名（ローマ字-名）
			query.append(" AND SHINSEI.UPPER(NAME_RO_MEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()) + "%'");
		}
		*/
		//削除 ここまで----------------------------------------------
		if(searchInfo.getBukyokuCd() != null && !searchInfo.getBukyokuCd().equals("")){		//部局コード
			query.append(" AND SHINSEI.BUKYOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getBukyokuCd()) +"'");
		}
		if(searchInfo.getKenkyuNo() != null && !searchInfo.getKenkyuNo().equals("")){			//研究者番号
			query.append(" AND SHINSEI.KENKYU_NO = '" + EscapeUtil.toSqlString(searchInfo.getKenkyuNo()) +"'");
		}
		if(searchInfo.getHikoboFlg() != null && !searchInfo.getHikoboFlg().equals("")){		//非公募申請課フラグ
			query.append(" AND SHINSEI.HIKOBO_FLG = '" + EscapeUtil.toSqlString(searchInfo.getHikoboFlg()) +"'");
		}

		//ソート順（申請者IDの昇順）
		query.append(" ORDER BY SHINSEI.SHINSEISHA_ID");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ページ取得
		//-----------------------
		try {
			return SelectUtil.selectPageInfo(connection,searchInfo, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"申請者管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		}
	}
	
	/**
	 * 重複チェック.<br /><br />
	 * 
	 * <b>1.所属機関情報の取得</b><br /><br />
	 * 
	 * 第二引数infoのコードの値を使用して、名前を取得する。
	 * 取得は、自メソッドを使用して行う。使用するコード、メソッド、取得する名前は以下の表を参照。<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>使用するコード</td><td>取得する名前</td><td>使用する自メソッド</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>所属機関名</td><td>getKikanCodeValue4ShinseishaRegist()</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局コード</td><td>部局名、部局名(略称)</td><td>getBukyokuCodeMap()</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職種コード</td><td>職名</td><td>getShokushuMap()</td></tr>
	 * </table><br />
	 * 
	 * 取得した値は、infoに格納する。<br /><br />
	 * 
	 * 
	 * <b>2.重複チェック</b><br /><br />
	 * 
	 * 以下のSQL文を発行し、研究者番号・所属機関コードの
	 * 同じ申請者が登録されていないかどうかを確認する。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	COUNT(*)
	 * FROM
	 * 	SHINSEISHAINFO
	 * WHERE
	 * 	SHINSEISHA_ID <> ?
	 * 	AND SHOZOKU_CD = ?
	 * 	AND KENKYU_NO = ?
	 * 	AND DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者ID</td><td>第二引数infoの変数ShinseishaIdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数infoの変数ShozokuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数infoの変数KenkyuNoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 検索結果が存在する場合には例外をthrowし、存在しない場合にはinfoを返却する。<br /><br />
	 * <b><span style="color:#FF0000">去年のレコードがある場合には例外がthrowされる。</span></b><br/>
	 * 申請者ID…西暦年度(2桁)+所属機関コード(5桁)+連番(5桁)+チェックデジット(1桁)
	 * @param userInfo UserInfo
	 * @param info ShinseishaInfo
	 * @param mode String
	 * @return 申請者情報のShinseishaInfo
	 * @throws ApplicationException
	 * @throws ValidationException
	 */
	public ShinseishaInfo validate(UserInfo userInfo, ShinseishaInfo info, String mode)
			throws ApplicationException, ValidationException {

		Connection connection = null;	
		try {
			connection = DatabaseUtil.getConnection();
			//エラー情報保持用リスト
			List errors = new ArrayList();

			//削除 ここから-----------------------------------------------
			//ローマ字処理を削除
			//---------------------------
			//申請者氏名（ローマ字-姓）を大文字に変換
			//---------------------------
			//info.setNameRoSei(info.getNameRoSei().toUpperCase());

			//---------------------------
			//申請者氏名（ローマ字-名）の1文字目を大文字に以降を小文字に変換
			//---------------------------
			//if(info.getNameRoMei() != null && info.getNameRoMei().length() > 0) {
			//	info.setNameRoMei(info.getNameRoMei().substring(0,1).toUpperCase()
			//		+info.getNameRoMei().substring(1).toLowerCase());
			//}
			//削除 ここまで----------------------------------------------
			
			//---------------------------
			//所属機関コード→所属機関名のセット
			//---------------------------
			KikanInfo kikanInfo = new KikanInfo();
			kikanInfo = getKikanCodeValue4ShinseishaRegist(userInfo, info.getShozokuCd(), info.getShozokuName(), info.getShozokuNameEigo());
			info.setShozokuName(kikanInfo.getShozokuNameKanji());
			info.setShozokuNameEigo(kikanInfo.getShozokuNameEigo());
			info.setShozokuNameRyaku(kikanInfo.getShozokuRyakusho());
			
			//---------------------------
			//部局コード→部局名、部局名(略称)
			//---------------------------
			info.setBukyokuName((String)getBukyokuCodeMap(userInfo, info.getBukyokuCd(), info.getBukyokuName()).get("BUKA_NAME"));
			info.setBukyokuNameRyaku((String)getBukyokuCodeMap(userInfo, info.getBukyokuCd(), info.getBukyokuName()).get("BUKA_RYAKUSHO"));

//			//---------------------------
//			//部局種別コード→部局種別名
//			//---------------------------
//			info.setBukyokuShubetuName(getBukyokuShubetuValue(userInfo, info.getBukyokuShubetuCd(), info.getBukyokuShubetuName()));

			//---------------------------
			//職種コード→職名
			//---------------------------
			info.setShokushuNameKanji((String)getShokushuMap(userInfo, info.getShokushuCd(), info.getShokushuNameKanji()).get("SHOKUSHU_NAME"));
			info.setShokushuNameRyaku((String)getShokushuMap(userInfo, info.getShokushuCd(), info.getShokushuNameKanji()).get("SHOKUSHU_NAME_RYAKU"));

			//研究者番号のチェックデジットチェック
			if(!checkKenkyuNo(info.getKenkyuNo())) {
				throw new ApplicationException("研究者番号が空です。", 	new ErrorInfo("errors.required", new String[] {"研究者番号"}));	
			}
//			String kenkyuNo = info.getKenkyuNo();
//			int checkDigit = (Integer.parseInt(kenkyuNo.substring(1, 2)) * 1
//								+ Integer.parseInt(kenkyuNo.substring(2, 3)) * 2
//								+ Integer.parseInt(kenkyuNo.substring(3, 4)) * 1
//								+ Integer.parseInt(kenkyuNo.substring(4, 5)) * 2
//								+ Integer.parseInt(kenkyuNo.substring(5, 6)) * 1
//								+ Integer.parseInt(kenkyuNo.substring(6, 7)) * 2
//								+ Integer.parseInt(kenkyuNo.substring(7, 8)) * 1)
//							% 10;
//			if(Integer.parseInt(kenkyuNo.substring(0, 1)) != checkDigit) {
//				throw new ApplicationException("研究者番号が間違っています。", 	new ErrorInfo("errors.5018"));	
//			}

			//2重登録チェック
			//申請者情報テーブルにすでに研究者番号、所属部局コードが同じ申請者が登録されていないかどうかを確認
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			int count = dao.countShinseishaInfo(connection, info);
			//すでに登録されている場合
			if(count > 0){
				String[] error = {"申請者"};
				throw new ApplicationException("すでに申請者が登録されています。", 	new ErrorInfo("errors.4007", error));			
			}

			//-----入力エラーがあった場合は例外をなげる-----
			if (!errors.isEmpty()) {
				throw new ValidationException(
					"申請者管理データチェック中にエラーが見つかりました。",
					errors);
			}
			return info;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"申請者管理データチェック中にDBエラーが発生しました。",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * パスワードの更新.<br /><br />
	 * 
	 * <b>1.申請者情報の取得</b><br /><br />
	 * 以下のSQL文を発行して、申請者情報を取得する。<br />
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SHINSEISHA_ID
	 * 	,A.SHOZOKU_CD
	 * 	,NVL(A.SHOZOKU_NAME,'') SHOZOKU_NAME
	 * 	,NVL(A.SHOZOKU_NAME_EIGO,'') SHOZOKU_NAME_EIGO
	 * 	,NVL(A.SHOZOKU_NAME_RYAKU,'') SHOZOKU_NAME_RYAKU
	 * 	,A.PASSWORD
	 * 	,NVL(A.NAME_KANJI_SEI,'') NAME_KANJI_SEI
	 * 	,NVL(A.NAME_KANJI_MEI,'') NAME_KANJI_MEI
	 * 	,NVL(A.NAME_KANA_SEI,'') NAME_KANA_SEI
	 * 	,NVL(A.NAME_KANA_MEI,'') NAME_KANA_MEI
	 * 	,NVL(A.NAME_RO_SEI,'') NAME_RO_SEI
	 * 	,NVL(A.NAME_RO_MEI,'') NAME_RO_MEI
	 * 	,A.BUKYOKU_CD
	 * 	,NVL(A.BUKYOKU_NAME,'') BUKYOKU_NAME
	 * 	,NVL(A.BUKYOKU_NAME_RYAKU,'') BUKYOKU_NAME_RYAKU
	 * 	,A.KENKYU_NO
	 * 	,A.SHOKUSHU_CD
	 * 	,NVL(A.SHOKUSHU_NAME_KANJI,'') SHOKUSHU_NAME_KANJI
	 * 	,NVL(A.SHOKUSHU_NAME_RYAKU,'') SHOKUSHU_NAME_RYAKU
	 * 	,A.HIKOBO_FLG
	 * 	,NVL(A.BIKO,'') BIKO
	 * 	,A.YUKO_DATE
	 * 	,A.DEL_FLG
	 * FROM
	 * 	SHINSEISHAINFO A
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者ID</td><td>第二引数pkInfoの変数ShinseishaIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 
	 * <b>2.パスワードのチェック</b><br /><br />
	 * 
	 * 取得した申請者情報のパスワードの値が、第三引数のoldPasswordと等しいかチェックする。
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
	 * 	SHINSEISHAINFO
	 * SET
	 * 	PASSWORD = ?
	 * WHERE
	 * 	SHINSEISHA_ID = ?
	 * 	AND DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>新パスワード</td><td>第四引数newPasswordを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数pkInfoの変数ShinseishaIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 正常にパスワードの更新が行われた場合には、trueを返却する。<br /><br />
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo ShinseishaPk
	 * @param oldPassword
	 * @param newPassword
	 * @return 更新結果のboolean
	 * @throws ApplicationException
	 */
	public boolean changePassword(
			UserInfo userInfo,
			ShinseishaPk pkInfo,
			String oldPassword,
			String newPassword)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//申請者情報の取得
			//---------------------------------------
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			ShinseishaInfo info = dao.selectShinseishaInfo(connection, pkInfo);

			//---------------------------------------
			//現在のパスワードをチェックする。
			//---------------------------------------
			if (!info.getPassword().equals(oldPassword)) {
				//エラー情報保持用リスト
				List errors = new ArrayList();
				errors.add(new ErrorInfo("errors.2001", new String[] { "パスワード" }));
				throw new ValidationException(
						"パスワード変更データチェック中にエラーが見つかりました。",
						errors);
			}

			//---------------------------------------
			//現在のパスワードを更新する。
			//---------------------------------------
			if(dao.changePasswordShinseishaInfo(connection,pkInfo,newPassword)){
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
	 * パスワードの更新.<br /><br />
	 * 
	 * <b>1.申請者情報の取得</b><br /><br />
	 * 以下のSQL文を発行して、申請者情報を取得する。<br />
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SHINSEISHA_ID
	 * 	,A.SHOZOKU_CD
	 * 	,NVL(A.SHOZOKU_NAME,'') SHOZOKU_NAME
	 * 	,NVL(A.SHOZOKU_NAME_EIGO,'') SHOZOKU_NAME_EIGO
	 * 	,NVL(A.SHOZOKU_NAME_RYAKU,'') SHOZOKU_NAME_RYAKU
	 * 	,A.PASSWORD
	 * 	,NVL(A.NAME_KANJI_SEI,'') NAME_KANJI_SEI
	 * 	,NVL(A.NAME_KANJI_MEI,'') NAME_KANJI_MEI
	 * 	,NVL(A.NAME_KANA_SEI,'') NAME_KANA_SEI
	 * 	,NVL(A.NAME_KANA_MEI,'') NAME_KANA_MEI
	 * 	,NVL(A.NAME_RO_SEI,'') NAME_RO_SEI
	 * 	,NVL(A.NAME_RO_MEI,'') NAME_RO_MEI
	 * 	,A.BUKYOKU_CD
	 * 	,NVL(A.BUKYOKU_NAME,'') BUKYOKU_NAME
	 * 	,NVL(A.BUKYOKU_NAME_RYAKU,'') BUKYOKU_NAME_RYAKU
	 * 	,A.KENKYU_NO
	 * 	,A.SHOKUSHU_CD
	 * 	,NVL(A.SHOKUSHU_NAME_KANJI,'') SHOKUSHU_NAME_KANJI
	 * 	,NVL(A.SHOKUSHU_NAME_RYAKU,'') SHOKUSHU_NAME_RYAKU
	 * 	,A.HIKOBO_FLG
	 * 	,NVL(A.BIKO,'') BIKO
	 * 	,A.YUKO_DATE
	 * 	,A.DEL_FLG
	 * FROM
	 * 	SHINSEISHAINFO A
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者ID</td><td>第二引数pkInfoの変数ShinseishaIdを使用する。</td></tr>
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
	 * 		<tr bgcolor="#FFFFFF"><td>対象者ID</td><td>1(1は申請者をあらわす)</td></tr>
	 * </table><br />
	 * 
	 * 以下のSQL文より、再発行したパスワードを登録する。
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSEISHAINFO
	 * SET
	 * 	PASSWORD = ?
	 * WHERE
	 * 	SHINSEISHA_ID = ?
	 * 	AND DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>新パスワード</td><td>再発行したnewPasswordを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者ID</td><td>第二引数pkInfoの変数ShinseishaIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * <b>3.申請者情報の取得</b><br /><br />
	 * 以下のSQL文を発行して、再度申請者情報を取得し、返却する。<br />
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SHINSEISHA_ID
	 * 	,A.SHOZOKU_CD
	 * 	,NVL(A.SHOZOKU_NAME,'') SHOZOKU_NAME
	 * 	,NVL(A.SHOZOKU_NAME_EIGO,'') SHOZOKU_NAME_EIGO
	 * 	,NVL(A.SHOZOKU_NAME_RYAKU,'') SHOZOKU_NAME_RYAKU
	 * 	,A.PASSWORD
	 * 	,NVL(A.NAME_KANJI_SEI,'') NAME_KANJI_SEI
	 * 	,NVL(A.NAME_KANJI_MEI,'') NAME_KANJI_MEI
	 * 	,NVL(A.NAME_KANA_SEI,'') NAME_KANA_SEI
	 * 	,NVL(A.NAME_KANA_MEI,'') NAME_KANA_MEI
	 * 	,NVL(A.NAME_RO_SEI,'') NAME_RO_SEI
	 * 	,NVL(A.NAME_RO_MEI,'') NAME_RO_MEI
	 * 	,A.BUKYOKU_CD
	 * 	,NVL(A.BUKYOKU_NAME,'') BUKYOKU_NAME
	 * 	,NVL(A.BUKYOKU_NAME_RYAKU,'') BUKYOKU_NAME_RYAKU
	 * 	,A.KENKYU_NO
	 * 	,A.SHOKUSHU_CD
	 * 	,NVL(A.SHOKUSHU_NAME_KANJI,'') SHOKUSHU_NAME_KANJI
	 * 	,NVL(A.SHOKUSHU_NAME_RYAKU,'') SHOKUSHU_NAME_RYAKU
	 * 	,A.HIKOBO_FLG
	 * 	,NVL(A.BIKO,'') BIKO
	 * 	,A.YUKO_DATE
	 * 	,A.DEL_FLG
	 * FROM
	 * 	SHINSEISHAINFO A
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者ID</td><td>第二引数pkInfoの変数ShinseishaIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値をShinseishaInfoに格納して返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo ShinseishaPk
	 * @return パスワード更新後のShinseishaInfo
	 * @throws ApplicationException
	 */
	public ShinseishaInfo reconfigurePassword(UserInfo userInfo, ShinseishaPk pkInfo) throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//申請者情報の取得
			//---------------------------------------
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			ShinseishaInfo info = dao.selectShinseishaInfo(connection, pkInfo);

			//RULEINFOテーブルよりルール取得準備
			RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHINSEISHA);

			//パスワードを再設定する
			String newPassword = rureInfoDao.getPassword(connection, rulePk);
			success = dao.changePasswordShinseishaInfo(connection,pkInfo,newPassword);

			//---------------------------------------
			//申請者データの取得
			//---------------------------------------
			ShinseishaInfo result = dao.selectShinseishaInfo(connection, pkInfo);
			
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
	 * 機関情報の返却（研究組織用）.<br /><br />
	 * 
	 * 所属機関コードから所属機関名を取得する自メソッド<br />
	 * getKikanCodeValueAction(userInfo, kikanCd, nameKanji, nameEigo, false, false)<br />
	 * を呼び出す。<br /><br />
	 * 
	 * メソッドに与える第六引数が"false"であるため、第二引数の値が"99999"(その他)は許されない。<br /><br />
	 * 
	 * @param userInfo			実行するユーザ情報
	 * @param kikanCd          機関コード
	 * @param nameKanji		所属機関名(漢字)
	 * @param nameEigo			所属機関名(英語)
	 * @return KikanInfo
	 * @throws ApplicationException
	 */
	public KikanInfo getKikanCodeValue4KenkyuSoshiki(UserInfo userInfo,
													  String kikanCd, 
													  String nameKanji, 
													  String nameEigo)
			throws ApplicationException {
				
		return getKikanCodeValueAction(userInfo, kikanCd, nameKanji, nameEigo, false, false);
	}

	/**
	 * 機関情報の返却（申請者登録用）.<br /><br />
	 * 
	 * 所属機関コードから所属機関名を取得する自メソッド<br />
	 * getKikanCodeValueAction(userInfo, kikanCd, nameKanji, nameEigo, true, true)<br />
	 * を呼び出す。<br /><br />
	 * 
	 * コードがその他の場合は入力文字列をセットして返す。
	 * 所属機関担当者テーブルに当該コードのレコードが存在した場合、
	 * そちらの情報を優先的にセットして返す。<br /><br />
	 * 
	 * @param  userInfo          	 実行するユーザ情報
	 * @param  kikanCd          	 機関コード
	 * @param  nameKanji          	 所属機関名(漢字)
	 * @param  nameEigo          	 所属機関名(英語)
	 * @return                      機関情報
	 * @throws ApplicationException 
	 */
	public KikanInfo getKikanCodeValue4ShinseishaRegist(UserInfo userInfo, 
														 String kikanCd, 
														 String nameKanji, 
														 String nameEigo)
			throws ApplicationException {
				
		return getKikanCodeValueAction(userInfo, kikanCd, nameKanji, nameEigo, true, true);
	}

	/**
	 * 機関情報の返却.<br /><br />
	 * 
	 * 所属機関コードから所属機関名を取得する自メソッド<br />
	 * getKikanCodeValueAction(userInfo, kikanCd, nameKanji, nameEigo, false, true)<br />
	 * を呼び出す。<br /><br />
	 * 
	 * コードがその他の場合は入力文字列をセットして返す。<br /><br />
	 * 
	 * @param  userInfo          	 実行するユーザ情報
	 * @param  kikanCd          	 機関コード
	 * @param  nameKanji          	 所属機関名(漢字)
	 * @param  nameEigo          	 所属機関名(英語)
	 * @return KikanInfo
	 * @throws ApplicationException
	 */
	public KikanInfo getKikanCodeValue(UserInfo userInfo,
										String kikanCd, 
										String nameKanji, 
										String nameEigo)
			throws ApplicationException{
		return getKikanCodeValueAction(userInfo, kikanCd, nameKanji, nameEigo, false, true);
	}

	/**
	 * 機関情報の返却.<br /><br />
	 * 
	 * 所属機関コードから所属機関名を取得する。<br />
	 * 渡される引数によって、処理が大幅に変わる。<br /><br />
	 * 
	 * まず、第二引数のString"<b>kikanCd</b>"がnullだった場合には、例外をthrowする。<br /><br />
	 * 
	 * 次に、第六引数のboolean"<b>otherFlg</b>"がtrueで、
	 * かつ第二引数のString"<b>kikanCd</b>"が"99999"(その他)だった場合(※)には、
	 * KikanInfoに以下の値を格納する。<br /><br />
	 * 
	 * 　・ShozokuNameKanji…第三引数のString<br />
	 * 　・ShozokuNameEigo…第四引数のString<br />
	 * 　・ShozokuRyakusho…"その他"<br /><br />
	 * 
	 * これで、値を格納したKikanInfoを返却して終了となる。<br /><br /><br />
	 * 
	 * 
	 * 
	 * (※)の条件が当てはまらない場合には、以下のSQL文を発行する。<br /><br />
	 * 
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
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数のStringを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した結果をKikanInfoに格納する。値がない場合には例外をthrowする。<br />
	 * "その他"のID『99999』を使用した場合には、値がないために例外をthrowすることになる。<br /><br />
	 * 
	 * 続いて、第五引数のboolean"<b>priorityFlg</b>"がfalseの場合には、
	 * 今取得したKikanInfoを返却して終了となる。<br />
	 * trueの場合には、以下のSQL文を発行して、所属機関担当者情報を検索する。<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
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
	 *     ,BUKYOKU_NUM				-- 部局担当者人数
	 *     ,A.DEL_FLG				-- 削除フラグ
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     DEL_FLG = 0
	 *	
	 * 　　　<b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 * 
	 * ORDER BY SHOZOKU_CD</pre>
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
	 * 取得した値から、所属機関名(漢字、英語)をKikanInfoに格納し、返却する。<br/><br/>
	 * 
	 * @param  userInfo          	 実行するユーザ情報
	 * @param  kikanCd          	 機関コード
	 * @param  nameKanji          	 所属機関名(漢字)
	 * @param  nameEigo          	 所属機関名(英語)
	 * @param  priorityFlg			 所属機関テーブル優先フラグ　true:所属機関テーブルを優先して返す　false:機関マスタの値を返す
	 * @param  otherFlg			 その他フラグ　true:その他コードあり　false:その他コードなし（その他コードもエラーとなる）
	 * @return                      機関情報
	 * @throws IllegalArgumentException  機関コードがnullの場合
	 * @throws ApplicationException 
	 */
	private KikanInfo getKikanCodeValueAction(UserInfo userInfo, 
											   String kikanCd, 
											   String nameKanji, 
											   String nameEigo, 
											   boolean priorityFlg,
											   boolean otherFlg)
			throws IllegalArgumentException, ApplicationException {

		//引数チェック
		if(kikanCd == null){
			throw new IllegalArgumentException("kikanCdがnullです。");
		}

		KikanInfo kikanInfo = new KikanInfo();
		kikanInfo.setShozokuCd(kikanCd);
		Connection connection = null;	
		try {
			connection = DatabaseUtil.getConnection();
			//エラー情報保持用リスト
			List errors = new ArrayList();

	
			//---otherFlgがtrueで、かつ、その他コードのときはそのまま格納。
			//---略称は「その他」とする。
			if(otherFlg && kikanCd.equals("99999")){
				kikanInfo.setShozokuNameKanji(nameKanji);
				kikanInfo.setShozokuNameEigo(nameEigo);
				kikanInfo.setShozokuRyakusho("その他");
	
			//---データベースを検索する
			} else {
				try {
					kikanInfo = new MasterKikanInfoDao(userInfo).selectKikanInfo(connection,kikanInfo);
				} catch(NoDataFoundException noDataShozokuInfo) {
					//見つからなかったとき。
					errors.add(
						new ErrorInfo("errors.2001", new String[] { "所属機関コード" }));
				}
		
				//優先フラグがtrueの場合は所属機関テーブルを検索する
				if(priorityFlg) {
					//所属機関が内容を変更している場合があるので、所属機関テーブルの情報を優先的に取得する
					ShozokuSearchInfo shozokuSearchInfo = new ShozokuSearchInfo();
					shozokuSearchInfo.setShozokuCd(kikanCd);
					try {
						Page page = new ShozokuMaintenance().search(userInfo, shozokuSearchInfo);
						if(page.getTotalSize() > 0) {
							List list = page.getList();
							HashMap hashMap = (HashMap)list.get(0);

							if(hashMap.get("SHOZOKU_NAME_KANJI") != null) {
								kikanInfo.setShozokuNameKanji(hashMap.get("SHOZOKU_NAME_KANJI").toString());
							}
							if(hashMap.get("SHOZOKU_NAME_EIGO") != null) {
								kikanInfo.setShozokuNameEigo(hashMap.get("SHOZOKU_NAME_EIGO").toString());
							}
//							if(hashMap.get("SHOZOKU_RYAKUSHO") != null) {
//								kikanInfo.setShozokuRyakusho(hashMap.get("SHOZOKU_RYAKUSHO").toString());
//							}
						}
					} catch(ApplicationException e) {
						//見つからなかったとき。
						//→所属機関テーブルに無い場合は、機関マスタのデータを見るのでエラーとしない。
					}
				}

			}
	
			//見つからなかった場合
			if(!errors.isEmpty()){
				String msg = "所属機関マスタに当該データがありません。kikanCD=" + kikanCd;
				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
			}
	
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"所属機関データチェック中にDBエラーが発生しました。",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return kikanInfo;
	}

	/**
	 * 部科名称の返却.<br /><br />
	 * 
	 * 部局コードから部科名称・部科略称のMapを取得する自メソッド<br />
	 * getBukyokuCodeMap(userInfo, bukyokuCd, value)<br />
	 * を呼び出し、そこから部科名称を取り出して返却する。<br /><br />
	 * 
	 * @param  userInfo              実行するユーザ情報
	 * @param  bukyokuCd             部局コード
	 * @param  value                 部科名称
	 * @return String                部科名称
	 * @throws ApplicationException 
	 */
	public String getBukyokuCodeValue(UserInfo userInfo, String bukyokuCd, String value)
			throws ApplicationException {
		return (String)getBukyokuCodeMap(userInfo, bukyokuCd, value).get("BUKA_NAME");
	}

	/**
	 * 部科名称・部科略称のMapの返却.<br /><br />
	 * 
	 * 以下のSQL文を発行して、部局情報を取得する。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	MASTER_BUKYOKU
	 * WHERE
	 * 	BUKYOKU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>部局コード</td><td>第二引数bukyokuCdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した部局情報の"部科名称"、"部科略称"をMapに格納する。<br />
	 * 値がなかった場合には例外をthrowする。<br /><br />
	 * 
	 * 第二引数のbukyokuCdの値が"その他"を表すもの(※)だった場合には、
	 * 第三引数のvalueを部科名称としてMapに格納する。<br />
	 * (※)その他…"709"、"913"、"899"、"875"、"903"、"999"<br /><br />
	 * <b><span style="color:#FF0000">ハードコーディングでいいのか？？</span></b><br/><br />
	 * 作成したMapを返却する。<br /><br />
	 * 
	 * @param  userInfo              実行するユーザ情報
	 * @param  bukyokuCd             部局コード
	 * @param  value                 部局名
	 * @return Map                   部科名称・部科略称のMap
	 * @throws ApplicationException 
	 */
	public Map getBukyokuCodeMap(UserInfo userInfo, String bukyokuCd, String value)
			throws ApplicationException {

		HashMap hashMap = new HashMap();
		
		Connection connection = null;	
		try {
			connection = DatabaseUtil.getConnection();
			//エラー情報保持用リスト
			List errors = new ArrayList();

			try {
				BukyokuInfo bukyokuInfo = new BukyokuInfo();
				bukyokuInfo.setBukyokuCd(bukyokuCd);
					
				bukyokuInfo = new MasterBukyokuInfoDao(userInfo).selectBukyokuInfo(connection, bukyokuInfo);
				hashMap.put("BUKA_NAME", bukyokuInfo.getBukaName());
				hashMap.put("BUKA_RYAKUSHO", bukyokuInfo.getBukaRyakusyo());	//略称はマスタの値を使用する
			} catch(NoDataFoundException e) {
				//見つからなかったとき。
				errors.add(
					new ErrorInfo("errors.2001", new String[] { "部局コード" }));
			}

			//その他の時は入力値valueを格納する。
			//2005/8/27 その他の時は入力値value！=NULLの場合、入力値valueを格納する。
			//if(bukyokuCd != null
			//2005/8/29 部局コードがその他の場合、部局名に全角と半角空白を除去する
			//if(value != null && value.length() > 0
//2006/06/30　苗　修正ここから　   理由：部局コードのその他に、「901」を追加
//			if(!StringUtil.isSpaceString(value)
//					&& (bukyokuCd.equals("709") || bukyokuCd.equals("913") || bukyokuCd.equals("899")
//					|| bukyokuCd.equals("875") || bukyokuCd.equals("903") || bukyokuCd.equals("999"))) {
            if (!StringUtil.isSpaceString(value)
                    && (bukyokuCd.equals("709") || bukyokuCd.equals("901") || 
                        bukyokuCd.equals("913") || bukyokuCd.equals("899") || 
                        bukyokuCd.equals("875") || bukyokuCd.equals("903") || 
                        bukyokuCd.equals("999"))) {
//2006/06/30　苗　修正ここまで            
				hashMap.put("BUKA_NAME", value);
			}
			//見つからなかった場合
			if(!errors.isEmpty()){
				String msg = "部局マスタに当該データがありません。kikanCD=" + bukyokuCd;
				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
			}
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"部局マスタデータチェック中にDBエラーが発生しました。",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return hashMap;
	}

	/**
	 * 職種名の返却.<br /><br />
	 * 
	 * 職種コードから職種名・職種名(略称)のMapを取得する自メソッド<br />
	 * getShokushuMap(userInfo, shokushuCd, value)<br />
	 * を呼び出し、そこから職種名を取り出して返却する。<br /><br />
	 * 
	 * @param  userInfo          	 実行するユーザ情報
	 * @param  shokushuCd         	 職種名コード
	 * @param  value	          	 職種名
	 * @return                      職種名
	 * @throws ApplicationException 
	 */
	public String getShokushuCodeValue(UserInfo userInfo, String shokushuCd, String value)
			throws ApplicationException {
		return (String)getShokushuMap(userInfo, shokushuCd, value).get("SHOKUSHU_NAME");
	}

	/**
	 * 職種名・職種名(略称)のMapの返却.<br /><br />
	 * 
	 * 以下のSQL文を発行して、職種情報を取得する。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SHOKUSHU_CD				--職種コード
	 * 	,A.SHOKUSHU_NAME			--職名称
	 * 	,A.SHOKUSHU_NAME_RYAKU		--職名(略称)
	 * 	,A.BIKO						--備考
	 * FROM
	 * 	MASTER_SHOKUSHU A
	 * WHERE
	 * 	SHOKUSHU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>職種コード</td><td>第二引数shokushuCdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した職種情報の"職種名"、"職種名(略称)"をMapに格納する。<br />
	 * 値がなかった場合には例外をthrowする。<br /><br />
	 * 
	 * 第二引数shokushuCdが"25"(その他)の場合には、
	 * 第三引数のvalueを職種名としてMapに格納する。<br /><br />
	 * 
	 * 
	 * 第二引数shokushuCdが"24"(研究員)で、かつ第三引数のvalueが空文字列でない場合には、
	 * 第三引数のvalueを職種名としてMapに格納する。<br /><br />
	 * 
	 * <b><span style="color:#FF0000">ハードコーディングでいいのか？？</span></b><br/><br />
	 * 
	 * 作成したMapを返却する。<br /><br />
	 * 
	 * @param  userInfo          	 実行するユーザ情報
	 * @param  shokushuCd         	 職種名コード
	 * @param  value	          	 職種名
	 * @return                      職種名・職種名(略称)のMap
	 * @throws ApplicationException 
	 */
	public Map getShokushuMap(UserInfo userInfo, String shokushuCd, String value)
			throws ApplicationException {

//		String shokushuName = null;
		HashMap hashMap = new HashMap();
		
		Connection connection = null;	
		try {
			connection = DatabaseUtil.getConnection();
			//エラー情報保持用リスト
			List errors = new ArrayList();

			try {
				ShokushuInfo shokushuInfo = new ShokushuInfo();
				shokushuInfo.setShokushuCd(shokushuCd);
					
				shokushuInfo = new MasterShokushuInfoDao(userInfo).selectShokushuInfo(connection, shokushuInfo);
//				shokushuName = shokushuInfo.getShokushuName();
				hashMap.put("SHOKUSHU_NAME", shokushuInfo.getShokushuName());
				hashMap.put("SHOKUSHU_NAME_RYAKU", shokushuInfo.getShokushuNameRyaku());	//略称はマスタの値を使用する
			} catch(NoDataFoundException e) {
				//見つからなかったとき。
				errors.add(
					new ErrorInfo("errors.2001", new String[] { "職種コード" }));
			}

			//その他(25)の時入力値valueを格納する。
			if(shokushuCd != null && shokushuCd .equals("25")) {
				hashMap.put("SHOKUSHU_NAME", value);
			}
			//職種コードが研究員(24)で入力値valueが空でない場合、入力値valueを優先する。
			//2005/8/29 全角と半角空白を除去する
			//else if(value != null && !value.equals("")
			else if( !StringUtil.isSpaceString(value)
					&& shokushuCd != null && shokushuCd.equals("24")) {
						hashMap.put("SHOKUSHU_NAME", value);
			}

			//見つからなかった場合
			if(!errors.isEmpty()){
				String msg = "職種マスタに当該データがありません。kikanCD=" + shokushuCd;
				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
			}
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"職種マスタデータチェック中にDBエラーが発生しました。",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return hashMap;
	}

//	/**
//	 * カテゴリ名称を返す。
//	 * コードがその他(05)の場合は入力文字列を返す。
//	 * @param  userInfo          	 実行するユーザ情報
//	 * @param  bukaKategori       	 カテゴリ
//	 * @param  value	          	 カテゴリ名称
//	 * @return                      カテゴリ名称
//	 * @throws ApplicationException 
//	 */
//	public String getKategoriCodeValue(UserInfo userInfo, String bukaKategori, String value)
//			throws ApplicationException {
//
//		String kategoriName = null;
//
//		Connection connection = null;	
//		try {
//			connection = DatabaseUtil.getConnection();
//			//エラー情報保持用リスト
//			List errors = new ArrayList();
//
//			if(bukaKategori != null && !bukaKategori.equals("05")) {
//
//				try {
//					KategoriInfo kategoriInfo = new KategoriInfo();
//					kategoriInfo.setBukaKategori(bukaKategori);
//			
//					kategoriInfo = new MasterKategoriInfoDao(userInfo).selectKategoriInfo(connection, kategoriInfo);
//					kategoriName = kategoriInfo.getKategoriName();
//				} catch(NoDataFoundException e) {
//					//見つからなかったとき。
//					errors.add(
//						new ErrorInfo("errors.2001", new String[] { "カテゴリ" }));
//				}
//			} else {
//				kategoriName = value;
//			}
//			//見つからなかった場合
//			if(!errors.isEmpty()){
//				String msg = "カテゴリマスタに当該データがありません。kikanCD=" + bukaKategori;
//				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
//			}
//		} catch (DataAccessException e) {
//			throw new ApplicationException(
//				"カテゴリマスタデータチェック中にDBエラーが発生しました。",
//				new ErrorInfo("errors.4005"),
//				e);
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//		return kategoriName;
//	}
//
//
//
//	/**
//	 * 部局種別名称を返す。
//	 * コードがその他(05)の場合は入力文字列を返す。
//	 * @param  userInfo          	 実行するユーザ情報
//	 * @param  shubetuCd       	 部局種別
//	 * @param  value	          	 部局種別名称
//	 * @return                      部局種別名称
//	 * @throws ApplicationException 
//	 */
//	public String getBukyokuShubetuValue(UserInfo userInfo, String shubetuCd, String value)
//			throws ApplicationException {
//
//		String shubetuName = null;
//
//		Connection connection = null;	
//		try {
//			connection = DatabaseUtil.getConnection();
//			//エラー情報保持用リスト
//			List errors = new ArrayList();
//
//			if(shubetuCd != null && !shubetuCd.equals("9")) {
//
//				try {
//					HashMap hashmap
//						= (HashMap)MasterLabelInfoDao.selectRecord(connection, ILabelKubun.BUKYOKU_SHUBETU, shubetuCd);
//					
//					shubetuName = hashmap.get("NAME").toString();
//					
//				} catch(NoDataFoundException e) {
//					//見つからなかったとき。
//					errors.add(
//						new ErrorInfo("errors.2001", new String[] { "部局種別" }));
//				}
//			} else {
//				shubetuName = value;
//			}
//			//見つからなかった場合
//			if(!errors.isEmpty()){
//				String msg = "ラベルマスタに当該データがありません。shubetuCd=" + shubetuCd;
//				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
//			}
//		} catch (DataAccessException e) {
//			throw new ApplicationException(
//				"ラベルマスタデータチェック中にDBエラーが発生しました。",
//				new ErrorInfo("errors.4005"),
//				e);
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//		return shubetuName;
//	}

	/**
	 * 非公募申請可フラグの解除.<br /><br />
	 * 
	 * 以下のSQL文を発行して、非公募申請可フラグの値を"0"(非公募申請不可)に変更する。<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSEISHAINFO
	 * SET
	 * 	HIKOBO_FLG = 0
	 * WHERE
	 * 
	 * 		<b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 * 
	 * 	DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者ID</td><td>ShinseishaId</td><td>SHINSEISHA_ID = '申請者ID' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(漢字-姓)</td><td>NameKanjiSei</td><td>NAME_KANJI_SEI LIKE '%申請者氏名(漢字-姓)%' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(漢字-名)</td><td>NameKanjiMei</td><td>NAME_KANJI_MEI LIKE '%申請者氏名(漢字-名)%' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(フリガナ-姓)</td><td>NameKanaSei</td><td>NAME_KANA_SEI LIKE '%申請者氏名(フリガナ-姓)%' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(フリガナ-名)</td><td>NameKanaMei</td><td>NAME_KANA_MEI LIKE '%申請者氏名(フリガナ-名)%' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(ローマ字-姓)</td><td>NameRoSei</td><td>UPPER(NAME_RO_SEI) LIKE '%申請者氏名(ローマ字-姓)%' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(ローマ字-名)</td><td>NameRoMei</td><td>UPPER(NAME_RO_SEI) LIKE '%申請者氏名(ローマ字-姓)%' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名(コード)</td><td>ShozokuCd</td><td>SHOZOKU_CD = '所属機関名(コード)' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名</td><td>ShozokuName</td><td>(SHOZOKU_NAME LIKE '%所属機関名%' OR SHOZOKU_NAME_RYAKU LIKE '%所属機関名%') AND</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseishaSearchInfo
	 * @throws ApplicationException
	 */
	public void deleteHikoboFlgInfo(UserInfo userInfo, ShinseishaSearchInfo searchInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//非公募応募可フラグ
			//---------------------------------------
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			dao.deleteHikoboFlgInfo(connection, searchInfo);
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"非公募応募可フラグ削除中にDBエラーが発生しました。",
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
					"非公募応募可フラグ削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 研究者番号が正しいか判定.<br /><br />
	 * 
	 * 与えられた研究者番号に対して、以下のチェックを挙げた順に行う。<br /><br />
	 * 
	 * 　1.研究者番号の値がnullだった場合には、falseを返却する。<br />
	 * 
	 * 　2.研究者番号の値の桁が8桁以外の場合には、例外をthrowする。<br />
	 * 
	 * 　3.研究者番号の値の8桁目の値(一番左の桁)が、チェックデジットの値と異なる場合には、例外をthrowする。<br /><br />
	 * 
	 * 以上のチェックに通った場合には、正しい研究者番号ということで、trueを返却する。<br /><br />
	 * 
	 * @param  kenkyuNo				研究者番号
	 * @return							true:研究者番号が正しい場合　false:研究者番号がnullの場合
	 * @throws ApplicationException	研究者番号が間違っている場合
	 */
	public boolean checkKenkyuNo(String kenkyuNo) throws ApplicationException {

		List errors = new ArrayList();

		if(kenkyuNo == null) {
			return false;
		} else {
			if(kenkyuNo.length() != 8) {
				errors.add(new ErrorInfo("errors.length", new String[] {"研究者番号", "8"}));
				throw new ValidationException("研究者番号が8文字ではありません。", errors);
			}

// 20050628 チェックデジットによるチェックを削除
//			try {
//				//2005/04/18 追加 ここから-------------------------------------------------
//				//CHECK_DIGIT_FLAGのチェックを追加
//				if(!ApplicationSettings.getBoolean(ISettingKeys.CHECK_DIGIT_FLAG)){
//					return true;
//				}
//				//追加 ここまで------------------------------------------------------------
//				int checkDigit = (Integer.parseInt(kenkyuNo.substring(1, 2)) * 1
//									+ Integer.parseInt(kenkyuNo.substring(2, 3)) * 2
//									+ Integer.parseInt(kenkyuNo.substring(3, 4)) * 1
//									+ Integer.parseInt(kenkyuNo.substring(4, 5)) * 2
//									+ Integer.parseInt(kenkyuNo.substring(5, 6)) * 1
//									+ Integer.parseInt(kenkyuNo.substring(6, 7)) * 2
//									+ Integer.parseInt(kenkyuNo.substring(7, 8)) * 1)
//								% 10;
//				if(Integer.parseInt(kenkyuNo.substring(0, 1)) == checkDigit) {
//					return true;
//			
//			//2005/05/13 削除 ここから------------------------------------
//			//チェックデジットの確認方法は上記の方法を使うため削除
//			//2005/04/28 追加 ここから-----------------------------------------------
//			//チェックデジットの確認方法を変更
//			//String checkDiditString = CheckDiditUtil.getCheckDigit(kenkyuNo.substring(0, 7));
//			//String checkDidit = CheckDiditUtil.convertCheckDigit(new Integer(checkDiditString).intValue());
//			//	if(checkDidit != null && checkDidit.equals(kenkyuNo.substring(7))){
//			//		return true;
//			//追加 ここまで-----------------------------------------------
//			//削除 ここまで-----------------------------------------------
//				} else {
//					errors.add(new ErrorInfo("errors.5018", new String[] {"研究者番号"}));
//					throw new ValidationException("研究者番号が間違っています。", errors);	
//				}
//			} catch (NumberFormatException e) {
//			errors.add(new ErrorInfo("errors.mask_roma", new String[] {"研究者番号"}));
//			throw new ValidationException("チェックデジットを除いた研究者番号が数値ではありません。", errors);
//		}
			return true;
// Horikoshi

		}
	}

	//2005/04/06 追加ここから　パスワード一括再設定処理用のメソッドを追加
	/**
	 * パスワードの一括再設定
	 * 
	 * 以下の処理を配列に格納された申請者IDの個数分行う。
	 * 
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
	 * 		<tr bgcolor="#FFFFFF"><td>対象者ID</td><td>1(1は申請者をあらわす)</td></tr>
	 * </table><br />
	 * 
	 * 以下のSQL文より、再発行したパスワードを登録する。
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSEISHAINFO
	 * SET
	 * 	PASSWORD = ?
	 * WHERE
	 * 	SHINSEISHA_ID = ?
	 * 	AND DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>新パスワード</td><td>再発行したnewPasswordを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>申請者ID</td><td>第二引数pkInfoの変数ShinseishaIdを使用する。</td></tr>
	 * </table><br />
	 *	 
 	 * @param userInfo UserInfo
	 * @param pkInfo ShinseishaPk
	 * @param array 申請者IDの配列
	 * @throws ApplicationException  
	 */
	public void reconfigurePasswordAll(UserInfo userInfo, ShinseishaPk pkInfo, ArrayList array) throws ApplicationException {

			boolean success = false;
			Connection connection = null;
			try {
				connection = DatabaseUtil.getConnection();
				//---------------------------------------
				//申請者情報の取得
				//---------------------------------------
				ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
				String newPassword = null;
				
				//RULEINFOテーブルよりルール取得準備
				RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
				RulePk rulePk = new RulePk();
				rulePk.setTaishoId(ITaishoId.SHINSEISHA);
				
				//パスワードを再設定する
				for(int i = 0; i < array.size(); i++){
					pkInfo.setShinseishaId((String)array.get(i));
					newPassword = rureInfoDao.getPassword(connection, rulePk);
					success = dao.changePasswordShinseishaInfo(connection,pkInfo,newPassword);
				}
				//---------------------------------------
				//申請者データの取得
				//---------------------------------------

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
	//追加ここまで	
}