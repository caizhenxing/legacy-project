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
import java.util.List;

import jp.go.jsps.kaken.model.IRuleMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.RuleInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.RuleInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 各ID・パスワードの発行ルール管理クラス.<br><br>
 * 
 * 概要：<br>
 * ＩＤパスワード発行ルールテーブル：各ID・パスワードの発行ルールを管理
 */
public class RuleMaintenance implements IRuleMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ. */
	protected static Log log = LogFactory.getLog(RuleMaintenance.class);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ.
	 */
	public RuleMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IRuleMaintenance
	//---------------------------------------------------------------------

	/**
	 * 発行ルールの新規作成.<br><br>
	 * 
	 * 第二引数addInfoをそのまま返却する。
	 * 
	 * @param userInfo	UserInfo
	 * @param addInfo	RuleInfo
	 * @return 第二引数addInfo(RuleInfo)
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.RuleInfo)
	 */
	public RuleInfo insert(UserInfo userInfo, RuleInfo addInfo)
		throws ApplicationException {
			return addInfo;
	}

	/**
	 * 発行ルールの更新.<br><br>
	 * 
	 * ID・パスワードの発行ルールを更新する。<br><br>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 *     RULEINFO				-- ID・パスワード発行ルールテーブル
	 * SET
	 *     TAISHO_ID = ?		-- 対象者ID
	 *     ,MOJISU_CHK = ?		-- 文字数
	 *     ,CHAR_CHK1 = ?		-- 大文字・小文字の混在
	 *     ,CHAR_CHK2 = ?		-- アルファベットと数字の混在
	 *     ,CHAR_CHK3 = ?		-- 予備1
	 *     ,CHAR_CHK4 = ?		-- 予備2
	 *     ,CHAR_CHK5 = ?		-- 予備3
	 *     ,YUKO_DATE = ?		-- 有効期限
	 *     ,BIKO = ?			-- 備考
	 * WHERE
	 *     TAISHO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TAISHO_ID</td><td>第二引数updateInfoの変数taishoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>MOJISU_CHK</td><td>第二引数updateInfoの変数mojisuChk</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>CHAR_CHK1</td><td>第二引数updateInfoの変数charChk1</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>CHAR_CHK2</td><td>第二引数updateInfoの変数charChk2</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>CHAR_CHK3</td><td>第二引数updateInfoの変数charChk3</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>CHAR_CHK4</td><td>第二引数updateInfoの変数charChk4</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>CHAR_CHK5</td><td>第二引数updateInfoの変数charChk5</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>YUKO_DATE</td><td>第二引数updateInfoの変数yukoDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BIKO</td><td>第二引数updateInfoの変数biko</td></tr>
	 * </table><br>
	 * 
	 * 条件文のバインド変数
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TAISHO_ID</td><td>第二引数updateInfoの変数taishoId</td></tr>
	 * </table><br>
	 * 
	 * @param userInfo		UserInfo
	 * @param updateInfo	更新情報(RuleInfo)
	 * @return なし
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.RuleInfo)
	 */
	public void update(UserInfo userInfo, RuleInfo updateInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//発行ルール情報の更新
			//---------------------------------------
			RuleInfoDao dao = new RuleInfoDao(userInfo);
			dao.updateRuleInfo(connection, updateInfo);
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"発行ルール更新中にDBエラーが発生しました。",
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
					"発行ルール更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 発行ルールの更新.<br><br>
	 * 
	 * 複数件のID・パスワードの発行ルールを更新する。<br><br>
	 * 
	 * 自クラスのupdate(UserInfo, RuleInfo)メソッドを呼び、発行ルールを更新する。<br>
	 * 引数に、第一引数userInfoと第二引数updateListの要素(RuleInfo)を渡す。<br>
	 * 第二引数updateListのsizeだけ繰り返す。
	 * 
	 * @param userInfo		UserInfo
	 * @param updateList	更新情報リスト(List)
	 * @return なし
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#update(jp.go.jsps.kaken.model.vo.UserInfo, java.util.List)
	 */
	public void updateAll(UserInfo userInfo, List updateList)
		throws ApplicationException {

		for(int i = 0; i < updateList.size(); i++) {
			update(userInfo, (RuleInfo)updateList.get(i));
		}
	}

	/**
	 * 発行ルールの削除.<br><br>
	 * 
	 * 空メソッド。
	 * 
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.RuleInfo)
	 */
	public void delete(UserInfo userInfo, RuleInfo deleteInfo)
		throws ApplicationException {
	}
	
	/**
	 * 発行ルールの取得.<br><br>
	 * 
	 * ID・パスワードの発行ルールを取得する。<br><br>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     TAISHO_ID		-- 対象者ID
	 *     ,MOJISU_CHK		-- 文字数
	 *     ,CHAR_CHK1		-- 大文字・小文字の混在
	 *     ,CHAR_CHK2		-- アルファベットと数字の混在
	 *     ,CHAR_CHK3		-- 予備1
	 *     ,CHAR_CHK4		-- 予備2
	 *     ,CHAR_CHK5		-- 予備3
	 *     ,YUKO_DATE		-- 有効期限
	 *     ,BIKO		-- 備考
	 * FROM
	 *     RULEINFO A		-- ID・パスワード発行ルールテーブル
	 * WHERE
	 *     TAISHO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TAISHO_ID</td><td>第二引数updateInfoの変数taishoId</td></tr>
	 * </table><br>
	 * 
	 * @param userInfo	UserInfo
	 * @param pkInfo	発行ルールPK情報(RulePk)
	 * @return 発行ルール情報(RuleInfo)
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.RulePk)
	 */
	public RuleInfo select(UserInfo userInfo, RulePk pkInfo)
		throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			RuleInfoDao dao = new RuleInfoDao(userInfo);
			return dao.selectRuleInfo(connection, pkInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"発行ルール管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * 発行ルール一覧の取得.<br><br>
	 * 
	 * ID・パスワードの発行ルール情報リストを取得する。<br>
	 * 返却されたリストの要素に、Mapで行情報を持つ。<br><br>
	 * 
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     TAISHO_ID		-- 対象者ID
	 *     ,MOJISU_CHK		-- 文字数
	 *     ,CHAR_CHK1		-- 大文字・小文字の混在
	 *     ,CHAR_CHK2		-- アルファベットと数字の混在
	 *     ,CHAR_CHK3		-- 予備1
	 *     ,CHAR_CHK4		-- 予備2
	 *     ,CHAR_CHK5		-- 予備3
	 *     ,YUKO_DATE		-- 有効期限
	 *     ,BIKO		-- 備考
	 * FROM
	 *     RULEINFO A		-- ID・パスワード発行ルールテーブル
	 * 
	 * ORDER BY TAISHO_ID
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * @param userInfo	UserInfo
	 * @return 発行ルール情報リスト
	 * 
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public List selectList(UserInfo userInfo) throws ApplicationException {
	
		//-----------------------
		// SQL文の作成
		//-----------------------
		String select = "SELECT "
						+ " * "
						+ " FROM RULEINFO A";						
		StringBuffer query = new StringBuffer(select);

		//ソート順（対照IDの昇順）
		query.append(" ORDER BY TAISHO_ID");

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		//-----------------------
		// リスト取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.select(connection, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"ルール設定データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * validateメソッド.<br><br>
	 * 
	 * 入力エラーがあったとき、例外をthrowする。<br><br>
	 * 
	 * 入力エラーがなければ、第二引数infoをそのまま返却する。
	 * 
	 * @param userInfo	UserInfo
	 * @param info		RuleInfo
	 * @return 第二引数info(RuleInfo)
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.RuleInfo)
	 */
	public RuleInfo validate(UserInfo userInfo, RuleInfo info)
		throws ApplicationException, ValidationException {

			//エラー情報保持用リスト
			List errors = new ArrayList();

			//-----入力エラーがあった場合は例外をなげる-----
			if (!errors.isEmpty()) {
				throw new ValidationException(
					"発行ルール管理データチェック中にエラーが見つかりました。",
					errors);
			}
			return info;
	}
}
