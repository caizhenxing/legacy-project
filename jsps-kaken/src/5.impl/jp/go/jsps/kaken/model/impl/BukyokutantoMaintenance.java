/*
 * 作成日: 2005/03/24
 *
 */
package jp.go.jsps.kaken.model.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.go.jsps.kaken.model.IBukyokutantoMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.BukyokutantoInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.BukyokuSearchInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoPk;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 部局担当者情報管理クラス.<br><br>
 * 
 * <b>概要:</b><br>
 * 部局担当者情報を管理する。<br><br>
 * 
 * 使用テーブル<br>
 * <table>
 * <tr><td>部局担当者情報テーブル</td><td>：部局担当者の基本情報を管理</td></tr>
 * </table>
 */
public class BukyokutantoMaintenance implements IBukyokutantoMaintenance{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(BukyokutantoMaintenance.class);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public BukyokutantoMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IBukyokutantoMaintenance
	//---------------------------------------------------------------------
	
	/**
	 * パスワードを変更する.<br/><br/>
	 * 
	 * <b>1.部局担当者情報の取得</b><br/>
	 * 　部局担当者テーブルから、引数で指定されるレコード情報を取得する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     BUKYOKU.BUKYOKUTANTO_ID              -- 部局担当者ID
	 *     ,BUKYOKU.PASSWORD                    -- パスワード
	 *     ,BUKYOKU.TANTO_NAME_SEI              -- 担当者名（姓）
	 *     ,BUKYOKU.TANTO_NAME_MEI              -- 担当者名（名）
	 *     ,BUKYOKU.BUKA_NAME                   -- 担当者部課名
	 *     ,BUKYOKU.KAKARI_NAME                 -- 担当者係名
	 *     ,BUKYOKU.SHOZOKU_CD                  -- 所属機関コード
	 *     ,BUKYOKU.BUKYOKU_TEL                 -- 電話番号
	 *     ,BUKYOKU.BUKYOKU_FAX	                -- FAX番号
	 *     ,BUKYOKU.BUKYOKU_EMAIL               -- Email
	 *     ,BUKYOKU.BUKYOKU_CD                  -- 部局コード
	 *     ,BUKYOKU.DEFAULT_PASSWORD            -- デフォルトパスワード
	 *     ,BUKYOKU.REGIST_FLG                  -- 登録済みフラグ
	 *     ,BUKYOKU.DEL_FLG                     -- 削除フラグ
	 *     ,SHOZOKU.YUKO_DATE                   -- 有効期限
	 *     ,SHOZOKU.DEL_FLG AS DEL_FLG_SHOZOKU  -- 所属担当者削除フラグ
	 * FROM
	 *     BUKYOKUTANTOINFO BUKYOKU             -- 部局担当者情報テーブル
	 * INNER JOIN 
	 *     SHOZOKUTANTOINFO SHOZOKU             -- 所属担当者情報テーブル
	 * ON 
	 *     BUKYOKU.SHOZOKU_CD = SHOZOKU.SHOZOKU_CD
	 * WHERE
	 *     BUKYOKUTANTO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数pkInfoの変数bukyokuTantoId</td></tr>
	 * </table><br/><br/> 
	 * 
	 * <b>2.現在のパスワードをチェック</b><br/>
	 * 　1.で取得した部局担当者情報の現在のパスワードと、第三引数oldPasswordを比較する。<br/>
	 * 　一致しないとき、例外をthrowする。<br/><br/>
	 * 
	 * <b>3.現在のパスワードを更新</b><br/>
	 * 　パスワードを新しいパスワードへ変更するために、部局担当者テーブルを更新する。<br/><br/>
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE 
	 *     BUKYOKUTANTOINFO 
	 * SET
	 *     PASSWORD = ?
	 * WHERE
	 *     BUKYOKUTANTO_ID = ?
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
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数pkInfoの変数bukyokuTantoId</td></tr>
	 * </table><br/><br/> 
	 * 
	 * @param userInfo		UserInfo
	 * @param pkInfo 		パスワードを変更するレコードのPK（BukyokutantoPk）
	 * @param oldPassword 旧パスワード
	 * @param newPassword 新パスワード
	 * @return true
	 * @see jp.go.jsps.kaken.model.IBukyokutantoMaintenance#changePassword(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.BukyokutantoPk, java.lang.String, java.lang.String)
	 */
	public boolean changePassword(UserInfo userInfo, BukyokutantoPk pkInfo, String oldPassword, String newPassword) throws ApplicationException, ValidationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//部局担当者情報の取得
			//---------------------------------------
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			BukyokutantoInfo info = dao.selectBukyokutantoInfo(connection, pkInfo);

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
			if(dao.changePasswordBukyokutantoInfo(connection,pkInfo,newPassword)){
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
	 * 担当部局の取得.<br><br>
	 * 
	 * 部局担当者の担当する部局を取得する。
	 * 以下のSQLを実行し取得した結果を返却する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT 
	 *     BUKYOKUTANTO_ID     -- 部局担当者ID
	 *     ,BUKYOKU_CD         -- 部局コード
	 *     ,SHOZOKU_CD         -- 所属機関コード
	 *     ,BIKO               -- 備考
	 * FROM 
	 *     TANTOBUKYOKUKANRI   -- 担当部局管理
	 * WHERE 
	 *     BUKYOKUTANTO_ID = ?
	 * AND 
	 *     BUKYOKU_CD = ?      -- 第二引数pkInfoの変数bukyokuCdに値がセットされている場合に条件を追加
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数pkInfoの変数bukyokuTantoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKU_CD</td><td>第二引数pkInfoの変数bukyokuCd</td></tr>
	 * </table><br/><br/> 
	 * 
	 * @param userInfo		UserInfo
	 * @param pkInfo		担当部局を取得するレコードのPK（BukyokutantoPk）
	 * @return BukyokutantoInfo[]	部局担当者情報
	 * @throws ApplicationException
	 */
	public BukyokutantoInfo[] select(UserInfo userInfo, BukyokutantoPk pkInfo)
			throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			return dao.selectTantoBukyokuInfo(connection, pkInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"所属機関管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	

	// 2005/04/07 追加ここから---------------------------------------------
	// 理由 部局担当者情報の登録、更新、削除、パスワード変更処理追加のため

	/** 
	 * 部局担当者一覧情報の取得.<br/><br/>
	 * 
	 * <b>1.部局担当者情報の取得。</b><br/>
	 * 　部局担当者テーブルから、引数で指定されるレコード情報を取得する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT 
	 *      A.BUKYOKUTANTO_ID,      -- 部局担当者ID
	 *      A.TANTO_NAME_SEI,       -- 担当者名(姓)
	 *      A.TANTO_NAME_MEI,       -- 担当者名(名)
	 *      A.BUKA_NAME,            -- 担当部課名
	 *      A.REGIST_FLG            -- 登録済みフラグ
	 * FROM 
	 * 	    BUKYOKUTANTOINFO A      -- 部局担当者情報テーブル
	 * INNER JOIN                   -- 担当部局フラグがtrueの場合に追加
	 * 	    TANTOBUKYOKUKANRI B 	
	 * ON 
	 * 	    A.BUKYOKUTANTO_ID = B.BUKYOKUTANTO_ID
	 * WHERE 
	 * 	    A.DEL_FLG = 0
	 *      AND A.SHOZOKU_CD = ?
	 * ORDER BY 
	 *      A.REGIST_FLG DESC,
	 *      A.BUKYOKUTANTO_ID
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数infoの変数shozokuCd</td></tr>
	 * </table><br/><br/> 
	 * 
	 * <b>2.ページ情報の取得</b><br/>
	 * 　1.で取得した部局担当者情報からページ情報を取得する。<br/>
	 * <br/>
	 * 
	 * @param		userInfo	ユーザ情報
	 * @param		info		検索条件
	 * @return		Page		ページ情報
	 * @exception	ApplicationExcepiton
	 */
	public Page searchBukyokuList(UserInfo userInfo, BukyokuSearchInfo info)
		throws ApplicationException {
		
		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		
		String select = 
			"SELECT A.BUKYOKUTANTO_ID, " +
					"A.TANTO_NAME_SEI, " +
					"A.TANTO_NAME_MEI, " +
					"A.BUKA_NAME, " +
					"A.REGIST_FLG " +
			"FROM BUKYOKUTANTOINFO A ";	
					
		StringBuffer query = new StringBuffer(select);
		
		//担当部局情報を持つ場合は担当部局管理テーブルをINNER JOINして検索する　
		if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){	
			query.append(" INNER JOIN TANTOBUKYOKUKANRI B " +
					 " ON A.BUKYOKUTANTO_ID = B.BUKYOKUTANTO_ID ");
		}
		//削除フラグ
		query.append("WHERE A.DEL_FLG = 0");
		
		if(info.getShozokuCd() != null && !info.getShozokuCd().equals("")){	
			//所属機関名（コード）（完全一致）
			query.append(" AND A.SHOZOKU_CD = '" + EscapeUtil.toSqlString(info.getShozokuCd()) + "'");
		}
				
		//ソート順（所属機関名（コード）の昇順）
		query.append(" ORDER BY A.REGIST_FLG DESC, A.BUKYOKUTANTO_ID");		
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ページ取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection, info, query.toString());
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
	 * 部局コードのチェック.<br/>
	 * 
	 * 入力された部局コードが部局マスタに含まれるかどうかを確認する。<br/><br/>	
	 * 
	 * <b>1.部局コードの個数の取得。</b><br/>
	 * 　部局マスタテーブルから、引数で指定されるレコード情報の個数を取得する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT 
	 * 			COUNT(*) COUNT 
	 * FROM 
	 * 			MASTER_BUKYOKU 
	 * WHERE 
	 * 			BUKYOKU_CD = ?
	 * 
	 * ※引数の部局コードが複数ある場合はWHERE句に以下のINを使用する。
	 * 
	 * WHERE 
	 * 			BUKYOKU_CD IN(?, ?, ･･･)
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKU_CD</td><td>第二引数setの各変数</td></tr>
	 * </table><br/><br/> 
	 *
	 * <b>2.部局コードの確認</b><br/>
	 * 　1.で取得した個数と引数の配列の長さを比較して一致するかどうかか確認する。<br/>
	 * 　一致しない場合はValidateExceptionを返す。<br/>
	 * <br/>
	 *  
	 * @param		userInfo	ユーザ情報
	 * @param		set			部局コードSet	
	 * @exception	ApplicationException
	 * 
	 */
	public void CheckBukyokuCd(UserInfo userInfo, Set set)
		throws ApplicationException {
		
		Connection connection = null;
		
		int count = 0;
		try{
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			count = dao.CheckBukyokuCd(connection, (HashSet)set);
			if(set.size() != count){
				List errors = new ArrayList();
				errors.add(new ErrorInfo("errors.2001", new String[] {"部局コード"}));
				throw new ValidationException("部局コードが間違っています。", errors);	
			}
			
		} catch (DataAccessException e) {
			log.error("部局コード確認中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"部局コード確認中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}	
	
	
	/** 
	 * 部局担当者情報の登録.<BR>
	 * 
	 * 部局担当者情報登録、部局担当者情報修正時に呼ばれる。<br/><br/>	
	 * 
	 * <b>1.部局の確認</b><br/>
	 * 　同クラスのselectメソッドを呼び出して 部局担当者の担当する部局があるか確認する。<br/>
	 * <br/> 
	 * 
	 * <b>2.部局の削除。</b><br/>
	 * 　1.で部局がある場合に実行される。
	 *   担当部局管理テーブルから、引数で指定されるレコード情報を削除する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * DELETE FROM 
	 * 			TANTOBUKYOKUKANRI 
	 * WHERE 
	 * 			BUKYOKUTANTO_ID = ? 
	 * 			AND SHOZOKU_CD = ? 
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数infoの変数bukyokutantoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数infoの変数shozokuCd</td></tr>
	 * </table><br/><br/> 
	 *
	 * <b>3.パスワードの取得</b><br/>
	 * 　新規登録時のみ実行される。<br/>
	 * 　RULEINFOテーブルよりルールを取得し、パスワードを生成する。<br/>
	 * <br/> 
	 * 
	 * <b>4.部局担当者情報データの更新</b><br/>
	 *   部局担当者情報の登録・更新に、部局担当者情報テーブルを更新する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>	
	 * UPDATE 
	 * 			BUKYOKUTANTOINFO 
	 * SET 
	 * 			TANTO_NAME_SEI = ? ,
	 * 			TANTO_NAME_MEI = ? ,
	 * 			BUKA_NAME = ? , 
	 * 			KAKARI_NAME = ? , 
	 * 			BUKYOKU_TEL = ? , 
	 * 			BUKYOKU_FAX = ? , 
	 * 			BUKYOKU_EMAIL = ? , 	
	 * 			REGIST_FLG = 1 ", 
	 * 			PASSWORD = ?  --------------------新規登録時のみ
	 * WHERE 
	 * 			BUKYOKUTANTO_ID = ? 
	 * 			AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 変更値
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TANTO_NAME_SEI</td><td>第二引数infoの変数tantoNameSei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TANTO_NAME_MEI</td><td>第二引数infoの変数tantoNameMei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKA_NAME</td><td>第二引数infoの変数bukaName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KAKARI_NAME</td><td>第二引数infoの変数kakariName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKU_TEL</td><td>第二引数infoの変数bukyokuTel</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKU_FAX</td><td>第二引数infoの変数bukyokuFax</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKU_EMAIL</td><td>第二引数infoの変数bukyokuEmail</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>PASSWORD</td><td>第二引数infoの変数password(新規登録時のみ)</td></tr>
	 * </table><br/>
	 * 絞込み条件
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数pkInfoの変数bukyokuTantoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * <b>5.部局コードの更新</b><br/>
	 *   部局担当者情報テーブルに部局コードを登録する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * 	INSERT INTO 
	 * 			TANTOBUKYOKUKANRI(
	 *                          BUKYOKUTANTO_ID,  
	 *                          SHOZOKU_CD, 
	 *                          BUKYOKU_CD) 
	 * 	VALUES(?, ?, ?)
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数infoの変数bukyokutantoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数infoの変数shozokuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKU_CD</td><td>第二引数infoの変数bukyokuCd</td></tr>
	 * </table><br/><br/>   
	 * 
	 * @param		userInfo	ユーザ情報
	 * @param		info		部局担当者情報
	 * @return		info		登録データを格納した部局担当者情報
	 * @exception	ApplicationException
	 * 
	 */
	public BukyokutantoInfo setBukyokuData(UserInfo userInfo, BukyokutantoInfo info)
		throws ApplicationException {
		Connection connection = null;
	
		BukyokutantoPk pk = new BukyokutantoPk();
		pk.setBukyokutantoId(info.getBukyokutantoId());		
		//selectメソッドを用いて担当部局管理テーブルにデータがあるか確認
		BukyokutantoInfo[] tanto = select(userInfo, pk);

		try{
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			
			if(tanto.length != 0){		
				//担当部局管理テーブルにデータがある場合はデータの削除を行う
				dao.deleteBukyokuCd(connection, info);
			}
			
			//2005/06/01 削除 ここから--------------------------------------
			//理由 パスワードはシステム管理者の所属機関登録時に登録するため
			//部局担当者情報の登録では行わない(Daoの変数newPasswordも削除）
			/*
			//新パスワード
			String newPassword = null;
			
			//新規登録時のみパスワードを設定する
			if(info.getAction() != null && info.getAction().equals("add")){
			
				//RULEINFOテーブルよりルール取得準備
				RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
				RulePk rulePk = new RulePk();
				rulePk.setTaishoId(ITaishoId.BUKYOKUTANTO);
				newPassword = rureInfoDao.getPassword(connection, rulePk);
				//パスワードを部局担当情報保持クラスに設定する
				info.setPassword(newPassword);
			}
			*/
			
			//データを更新する
			//dao.updateBukyokuData(connection, info, newPassword);
			dao.updateBukyokuData(connection, info);

			//2005/06/30追加
			//新規登録時、パスワードを表示する為、パスワードを取得する
			if(info.getAction() != null && info.getAction().equals("add")){
				String pwd = dao.getTantoPassword(connection, info);
				info.setPassword(pwd);
			}
			//2005/06/30追加完了
			
			
		} catch (DataAccessException e) {
			log.error("部局担当者データ登録中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"部局担当者データ登録中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return info;
	}
	
	
	/** 
	 * 部局担当者情報を取得する.<BR><BR>
	 * 
	 * <b>1.部局担当者情報の取得</b><br/>
	 * 　部局担当者テーブルから、引数で指定されるレコード情報を取得する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     BUKYOKU.BUKYOKUTANTO_ID              -- 部局担当者ID
	 *     ,BUKYOKU.PASSWORD                    -- パスワード
	 *     ,BUKYOKU.TANTO_NAME_SEI              -- 担当者名（姓）
	 *     ,BUKYOKU.TANTO_NAME_MEI              -- 担当者名（名）
	 *     ,BUKYOKU.BUKA_NAME                   -- 担当者部課名
	 *     ,BUKYOKU.KAKARI_NAME                 -- 担当者係名
	 *     ,BUKYOKU.SHOZOKU_CD                  -- 所属機関コード
	 *     ,BUKYOKU.BUKYOKU_TEL                 -- 電話番号
	 *     ,BUKYOKU.BUKYOKU_FAX                 -- FAX番号
	 *     ,BUKYOKU.BUKYOKU_EMAIL               -- Email
	 *     ,BUKYOKU.BUKYOKU_CD                  -- 部局コード
	 *     ,BUKYOKU.DEFAULT_PASSWORD            -- デフォルトパスワード
	 *     ,BUKYOKU.REGIST_FLG                  -- 登録済みフラグ
	 *     ,BUKYOKU.DEL_FLG                     -- 削除フラグ
	 *     ,BUKYOKU.SHOZOKU.YUKO_DATE           -- 有効期限
	 *     ,SHOZOKU.DEL_FLG AS DEL_FLG_SHOZOKU  -- 所属機関担当者の削除フラグ
	 * FROM
	 *     BUKYOKUTANTOINFO BUKYOKU             -- 部局担当者情報テーブル
	 * INNER JOIN 
	 *     SHOZOKUTANTOINFO SHOZOKU             -- 所属担当者情報テーブル
	 * ON 
	 *     BUKYOKU.SHOZOKU_CD = SHOZOKU.SHOZOKU_CD
	 * WHERE
	 *     BUKYOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数pkInfoの変数bukyokuTantoId</td></tr>
	 * </table><br/><br/> 
	 * 
	 * <b>2.部局コードの取得</b><br/>
	 *   担当部局管理テーブルから、引数で指定されるレコード情報を取得する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * 	SELECT 
	 * 			BUKYOKU_CD          -- 部局コード 
	 * 	FROM 
	 * 			TANTOBUKYOKUKANRI   -- 担当部局管理テーブル
	 * 	WHERE 
	 * 			BUKYOKUTANTO_ID = ? 
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数infoの変数bukyokutantoId</td></tr>
	 * </table><br/><br/> 
	 *
	 * <b>3.所属担当者情報の取得</b><br/>
	 * 　同クラスのselectShozokuDataメソッドを呼び出して所属担当者情報を取得する。<br/>
	 * <br/> 
	 * 
	 * @param		userInfo	ユーザ情報
	 * @param		info		部局担当コードの格納された部局担当者情報
	 * @return		info		登録データを格納した部局担当者情報
	 * @exception	ApplicationException
	 * 
	 */
	public BukyokutantoInfo selectBukyokuData(UserInfo userInfo, BukyokutantoInfo info)
			throws ApplicationException {
		
		Connection connection = null;
		ArrayList bukyokuList = new ArrayList();
		
		try{
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			BukyokutantoPk pk = new BukyokutantoPk();
			pk.setBukyokutantoId(info.getBukyokutantoId());
			//部局担当者情報の取得
			info = dao.selectBukyokutantoInfo(connection, pk);
			//部局コードの取得
			bukyokuList = dao.selectTantoBukyokuKanri(connection, info);
			info.setBukyokuList(bukyokuList);
			//所属担当者情報の取得
			info = selectShozokuData(userInfo, info);	
		}catch(DataAccessException e) {
			log.error("所属機関管理データ検索中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"所属機関管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return info;		
	}
	
	
	/** 
	 * 部局担当者情報削除.<BR><BR>
	 * 
	 * <b>1.部局担当者情報の削除</b><br/>
	 * 　部局担当者テーブルで、引数で指定されるレコード情報の削除フラグに１を設定する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE 
	 * 			BUKYOKUTANTOINFO 
	 * SET 
	 * 			DEL_FLG = 1 
	 * WHERE 
	 * 			BUKYOKUTANTO_ID = ?
	 * 			AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数infoの変数bukyokuTantoId</td></tr>
	 * </table><br/><br/>  
	 * 
	 * <b>2.部局コードの削除。</b><br/>
	 * 　1.で部局がある場合に実行される。
	 *   担当部局管理テーブルから、引数で指定されるレコード情報を削除する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * DELETE FROM 
	 * 			TANTOBUKYOKUKANRI 
	 * WHERE 
	 * 			BUKYOKUTANTO_ID = ? 
	 * 			AND SHOZOKU_CD = ? 
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数infoの変数bukyokutantoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数infoの変数shozokuCd</td></tr>
	 * </table><br/><br/> 
	 * 
	 * @param		userInfo	ユーザ情報
	 * @param		info		部局担当者情報
	 * @exception	ApplicationException
	 * 
	 */
	public void delete(UserInfo userInfo, BukyokutantoInfo info)
		throws ApplicationException {
			
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			//部局担当者情報の削除
			dao.deleteBukyokuData(connection, info);
			//部局コードの削除
			dao.deleteBukyokuCd(connection, info);
			
		}catch(DataAccessException e) {
			log.error("部局担当者情報削除中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"部局担当者情報削除中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}				
	}
	
    // 2005/04/22 追加 ここから------------------------------------------------------
    // 理由 所属機関担当者削除時、同所属機関に属する部局担当者情報も削除するため
	/**
	 * 部局担当者情報削除.<br><br>
	 * 
	 * 同所属機関に属する部局担当者情報を一括で削除する。<BR>
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre> 
	 * UPDATE 
	 * 		BUKYOKUTANTOINFO
	 * SET 
	 * 		DEL_FLG = 1 
	 * WHERE 
	 * 		SHOZOKU_CD = ? 
	 * 		AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数shozokuCd</td></tr>
	 * </table><br/><br/>  
	 * 
	 * @param userInfo		ユーザ情報
	 * @param shozokuCd	削除対象所属CD
	 * @throws ApplicationException
	 */
	public void deleteAll(UserInfo userInfo, String shozokuCd)
		throws ApplicationException {
		
		boolean success = false;
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			//部局担当者情報の削除
			dao.deleteBukyokuDataAll(connection, shozokuCd);
			//担当部局コードの削除
			// 2005/04/22 現状では、担当部局情報は削除しない。
			//dao.deleteBukyokuCdAll(connection, shozokuCd);
			
			success = true;
		}catch(DataAccessException e) {
			log.error("部局担当者情報削除中にDBエラーが発生しました。", e);
			throw new ApplicationException(
				"部局担当者情報削除中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
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
						"部局担当者情報削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
			DatabaseUtil.closeConnection(connection);
		}				
	}
	// 追加 ここまで------------------------------------------------------------------
	
	/** 
	 * 所属担当者で部局担当者のパスワードを変更する.<BR><BR>
	 * パスワードはDEFAULT_PASSWORDに戻される。<BR><BR>
	 * 
	 * <b>1.パスワードの更新</b><br/>
	 *   パスワードの更新に、部局担当者情報テーブルを更新する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>	
	 * UPDATE 
	 * 		BUKYOKUTANTOINFO
	 * SET
	 * 		PASSWORD = DEFAULT_PASSWORD
	 * WHERE
	 * 		BUKYOKUTANTO_ID = ?
	 * 		AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数infoの変数bukyokutantoId</td></tr>
	 * </table><br/><br/>   
	 * 
	 * 
	 * <b>2.所属担当者情報の取得</b><br/>
	 * 　所属機関担当者情報を取得する。
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>	
	 * SELECT 
	 * 		M.SHOZOKU_CD                    -- 所属機関コード
	 * 		,M.SHOZOKU_NAME_KANJI           -- 機関名称（日本語）
	 * 		,SHOZOKU.SHOZOKU_NAME_EIGO      -- 機関名称（英語）
	 * FROM 
	 * 		BUKYOKUTANTOINFO B              -- 部局担当者情報テーブル
	 * INNER JOIN
	 * 		MASTER_KIKAN M                  -- 機関マスタ
	 * ON 
	 * 		B.SHOZOKU_CD = M.SHOZOKU_CD 
	 * INNER JOIN 
	 * 		SHOZOKUTANTOINFO SHOZOKU        -- 所属機関担当者情報テーブル
	 * ON 
	 * 		SHOZOKU.SHOZOKU_CD = B.SHOZOKU_CD
	 * WHERE 
	 * 		B.BUKYOKUTANTO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数infoの変数bukyokutantoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * <b>3.新パスワードの取得</b><br/>
	 * 　デフォルトパスワードに戻したパスワードを取得する。
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>	
	 * SELECT
	 *     BUKYOKU.BUKYOKUTANTO_ID              -- 部局担当者ID
	 *     ,BUKYOKU.PASSWORD                    -- パスワード
	 *     ,BUKYOKU.TANTO_NAME_SEI              -- 担当者名（姓）
	 *     ,BUKYOKU.TANTO_NAME_MEI              -- 担当者名（名）
	 *     ,BUKYOKU.BUKA_NAME                   -- 担当者部課名
	 *     ,BUKYOKU.KAKARI_NAME                 -- 担当者係名
	 *     ,BUKYOKU.SHOZOKU_CD                  -- 所属機関コード
	 *     ,BUKYOKU.BUKYOKU_TEL                 -- 電話番号
	 *     ,BUKYOKU.BUKYOKU_FAX                 -- FAX番号
	 *     ,BUKYOKU.BUKYOKU_EMAIL               -- Email
	 *     ,BUKYOKU.BUKYOKU_CD                  -- 部局コード
	 *     ,BUKYOKU.DEFAULT_PASSWORD            -- デフォルトパスワード
	 *     ,BUKYOKU.REGIST_FLG                  -- 登録済みフラグ
	 *     ,BUKYOKU.DEL_FLG                     -- 削除フラグ
	 *     ,SHOZOKU.YUKO_DATE                   -- 有効期限
	 *     ,SHOZOKU.DEL_FLG AS DEL_FLG_SHOZOKU  -- 所属担当者削除フラグ
	 * FROM
	 *     BUKYOKUTANTOINFO BUKYOKU             -- 部局担当者情報テーブル
	 * INNER JOIN 
	 *     SHOZOKUTANTOINFO SHOZOKU             -- 所属担当者情報テーブル
	 * ON 
	 *     BUKYOKU.SHOZOKU_CD = SHOZOKU.SHOZOKU_CD
	 * WHERE
	 *     BUKYOKUTANTO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数pkInfoの変数bukyokuTantoId</td></tr>
	 * </table><br/><br/> 
	 * 
	 * @param		userInfo	ユーザ情報
	 * @param		info		部局担当者情報
	 * @return		info		変更パスワードを格納した部局担当者情報
	 * @exception	ApplicationException
	 */
	public BukyokutantoInfo changeBukyokuPassword(
		UserInfo userInfo,
		BukyokutantoInfo info)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//申請者情報の取得
			//---------------------------------------
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			
			//2005/04/13 削除 ここから---------------------------------------------------
			//理由 パスワード再設定はデフォルトパスワードに戻すのでパスワードの取得は不要なため
			
			//String newPassword = null;
			//RULEINFOテーブルよりルール取得準備
			//RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			//RulePk rulePk = new RulePk();
			//rulePk.setTaishoId(ITaishoId.SHINSEISHA);

			//パスワードを再設定する
			//	newPassword = rureInfoDao.getPassword(connection, rulePk);
			//削除 ここまで--------------------------------------------------------------
			
			success = dao.originPassword(connection, info);
			//所属担当者情報を取得する
			info = dao.selectShozokuData(connection, info);
			
			//2005/04/13 追加 ここから---------------------------------------------------
			//理由 パスワード再取得のため
			//パスワードを再取得する
			info.setPassword(dao.selectBukyokutantoInfo(connection, info).getPassword());
			//追加 ここまで--------------------------------------------------------------
			success = true;
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
		return info;
	}
	
	
	/** 
	 * 所属機関情報を取得する.<BR><BR>
	 * 
	 * 所属機関担当者の連絡先や所属機関名等を取得する。<BR><BR>
	 * 
	 * <b>1.所属担当者情報の取得</b><br/>
	 *   機関マスタテーブルから、引数で指定されるレコード情報を取得する。<br/><br/>
	 * 
	 * 　以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>	
	 * SELECT 
	 * 		M.SHOZOKU_CD                    -- 所属機関コード
	 * 		,M.SHOZOKU_NAME_KANJI           -- 機関名称（日本語）
	 * 		,SHOZOKU.SHOZOKU_NAME_EIGO      -- 機関名称（英語）
	 * FROM 
	 * 		BUKYOKUTANTOINFO B              -- 部局担当者情報テーブル
	 * INNER JOIN
	 * 		MASTER_KIKAN M                  -- 機関マスタ
	 * ON 
	 * 		B.SHOZOKU_CD = M.SHOZOKU_CD 
	 * INNER JOIN 
	 * 		SHOZOKUTANTOINFO SHOZOKU        -- 所属機関担当者情報テーブル
	 * ON 
	 * 		SHOZOKU.SHOZOKU_CD = B.SHOZOKU_CD
	 * WHERE 
	 * 		B.BUKYOKUTANTO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>第二引数infoの変数bukyokutantoId</td></tr>
	 * </table><br/><br/>   
	 * 
	 * @param		userInfo	ユーザ情報
	 * @param		info		所属コードが格納された部局担当者情報
	 * @return		info		部局担当者情報
	 * @exception	ApplicationException
	 * 
	 */
	public BukyokutantoInfo selectShozokuData(UserInfo userInfo, BukyokutantoInfo info)
		throws ApplicationException {
	
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			
			//所属担当者情報を取得する
			info = dao.selectShozokuData(connection, info);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"所属担当者情報取得中にDBエラーが発生しました",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return info;
	}
	

	// 追加 ここまで--------------------------------------------------------
	
	//2005/06/01 追加 ここから----------------------------------------------
	//証明書発行用CSVの部局担当者データ取得のため
	
	/**
	 * 証明書発行用CSVデータを取得する.<BR><BR>
	 * 
	 * 第三引数listの値（所属機関のcsvデータ)をArrayListに格納する。<BR><BR>
	 *  
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT 
	 * 		SUBSTR(BUKYOKUTANTO_ID,0,7) CODE
	 * 		, PASSWORD 
	 * FROM 
	 * 		BUKYOKUTANTOINFO 
	 * WHERE 
	 * 		SHOZOKU_CD = ? 
	 * 		AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">列名</td><td>値</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>第二引数infoの変数shozokuCd</td></tr>
	 * </table><br/><br/>   
	 * 
	 * 以下の1.2の処理をSQLの結果個数分繰り返す。<br>
	 * 1.設定ファイルからprofileName, subjectDn, subjectAltName, pubkeyAlgo, keyLength, p12Flagを取得し、
	 * SQLの各実行結果と共にListに格納する。<br>
	 * 2.格納したListをArrayListに格納する。<br><br>
	 * 
	 * 所属機関情報と部局担当者情報を全て格納したArrayListを返す。<br>
	 * 
	 * @param userInfo		UserInfo
	 * @param info			ShozokuInfo
	 * @param list			List
	 * @return	証明書発行用CSVデータ
	 * @throws ApplicationException
	 */
	public List getShomeiCsvData(UserInfo userInfo, ShozokuInfo info, List list)
		throws ApplicationException {	
	
		Connection connection = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			
			//所属担当者情報を取得する
			list = dao.getShomeiCsvData(connection, info, list);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"所属担当者情報取得中にDBエラーが発生しました",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return list;
	}
	//追加 ここまで---------------------------------------------------------
}
