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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import jp.go.jsps.kaken.model.IGyomutantoMaintenance;
import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.AccessKanriDao;
import jp.go.jsps.kaken.model.dao.impl.GyomutantoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterJigyoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.RuleInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.AccessKanriInfo;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 業務担当者情報管理を行うクラス.<br /><br />
 * 
 * 使用しているテーブル<br /><br />
 * 　・業務担当者情報テーブル:GYOMUTANTOINFO<br />
 * 　　　業務（原課）担当者の基本情報を管理する。<br /><br />
 * 　・アクセス制御テーブル:ACCESSKANRI<br />
 * 　　　業務担当者のアクセス制御情報を管理する。<br /><br />
 * 　・事業マスタ:MASTER_JIGYO<br />
 * 　　　事業名称、事業ごとの系名、事業区分等の事業に関する基本情報を管理する。<br /><br />
 */
public class GyomutantoMaintenance implements IGyomutantoMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(GyomutantoMaintenance.class);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public GyomutantoMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IGyomutantoMaintenance
	//---------------------------------------------------------------------

	/**
	 * 業務担当者情報の登録.<br /><br />
	 * 業務担当者情報の登録、アクセス制御情報の登録を行い、その値を格納したGyomutantoInfoを返却する。<br /><br />
	 * <b>1.パスワードの発行</b><br />
	 * 対象者IDから、パスワードを発行する。<br /><br />
	 * 　(1)以下のSQL文を発行してパスワード発行ルールを取得する。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		*
	 * FROM
	 * 		RULEINFO A
	 * WHERE
	 * 		TAISHO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>対象者ID</td><td>定数GYOMUTANTOを使用する。(具体的な値は"3")</td></tr>
	 * </table><br />
	 * 　(2)取得したパスワード発行ルールよりパスワードを発行し、addInfoにセットする。<br /><br />
	 * 
	 * <b>2.業務担当者情報の追加</b><br />
	 * 業務担当者情報テーブルに値を追加する。<br /><br />
	 * 　(1)二重登録にならないか、重複チェックを行う。<br />
	 * 　　以下のSQL文を発行し、業務担当者情報を取得する。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * 	SELECT
	 * 			GYOMUTANTO_ID
	 * 			,ADMIN_FLG
	 * 			,PASSWORD
	 * 			,NAME_KANJI_SEI
	 * 			,NAME_KANJI_MEI
	 * 			,NAME_KANA_SEI
	 * 			,NAME_KANA_MEI
	 * 			,BUKA_NAME
	 * 			,KAKARI_NAME
	 * 			,BIKO
	 * 			,YUKO_DATE
	 * 			,DEL_FLG
	 * 	FROM
	 * 			GYOMUTANTOINFO
	 * 	WHERE
	 * 			GYOMUTANTO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数addInfoの変数GyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * 　　該当する値があった場合には、例外をthrowする。<br /><br />
	 * 　(2)登録を行う。<br />
	 * 　　以下のSQL文を発行し、業務担当者情報を登録する。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * INSERT INTO
	 * 		GYOMUTANTOINFO
	 * 				(GYOMUTANTO_ID
	 * 				,ADMIN_FLG
	 * 				,PASSWORD
	 * 				,NAME_KANJI_SEI
	 * 				,NAME_KANJI_MEI
	 * 				,NAME_KANA_SEI
	 * 				,NAME_KANA_MEI
	 * 				,BUKA_NAME
	 * 				,KAKARI_NAME
	 * 				,BIKO
	 * 				,YUKO_DATE
	 * 				,DEL_FLG)
	 * 		VALUES
	 * 				(?,?,?,?,?,?,?,?,?,?,?,?)</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数addInfoの変数GyomutantoIdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>管理者フラグ</td><td>0</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>パスワード</td><td>第二引数addInfoの変数Passwordを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（漢字－姓）</td><td>第二引数addInfoの変数NameKanjiSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（漢字－名）</td><td>第二引数addInfoの変数NameKanjiMeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ－姓）</td><td>第二引数addInfoの変数NameKanaSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ－名）</td><td>第二引数addInfoの変数NameKanaMeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部課名</td><td>第二引数addInfoの変数BukaNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>係名</td><td>第二引数addInfoの変数KakariNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>第二引数addInfoの変数Bikoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>有効期限</td><td>第二引数addInfoの変数YukoDateを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>0</td></tr>
	 * </table><br />
	 * 
	 * <b>3.担当事業情報の追加</b><br />
	 * 
	 * 
	 * 　(1)テーブルの削除<br />
	 * 　　登録データがDBに存在するとエラーとなるため、念のため物理削除を行う。<br />
	 * 　　発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * DELETE
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数addInfoの変数GyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 　(2)事業区分の取得<br />
	 * 　　事業区分を取得する。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		NVL(A.JIGYO_KUBUN,0) JIGYO_KUBUN
	 * FROM
	 * 		MASTER_JIGYO A
	 * WHERE
	 * 		JIGYO_CD=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>第二引数addInfoの変数JigyoValues内のjigyoCdを使用する。</td></tr>
	 * </table><br />
	 * 　　取得した事業区分と事業コードを、AccessKanriInfoに格納する。<br /><br />
	 * 
	 * 
	 * 　(3)担当事業情報の登録<br />
	 * 　　二重登録にならないか重複チェックを行い、重複でなければ登録を行う。<br />
	 * 　　以下のSQL文を発行し、業務担当者情報を取得する。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		GYOMUTANTO_ID
	 * 		,JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * 		,BIKO
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID=?
	 * 		AND JIGYO_CD=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>AccessKanriInfoの変数GyomutantoIdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>AccessKanriInfoの変数JigyoCdを使用する。</td></tr>
	 * </table><br />
	 * 　　該当する値が合った場合は、例外をthrowする。<br />
	 * 　　重複するものがなければ、登録を行う。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * INSERT INTO ACCESSKANRI
	 * 		(
	 * 		GYOMUTANTO_ID
	 * 		,JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * 		,BIKO
	 * 		)
	 * VALUES
	 * 		(?,?,?,?)</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>AccessKanriInfoの変数GyomutantoIdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>AccessKanriInfoの変数JigyoCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>AccessKanriInfoの変数JigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>AccessKanriInfoの変数JigyoBikoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 　(2),(3)はaddInfoのList<b>"getJigyoValues"</b>の件数だけ繰り返す。<br /><br />
	 * 
	 * 　(4)登録データの取得<br />
	 * 　　業務担当者の登録データを取得する。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		GYOMUTANTO_ID
	 * 		,ADMIN_FLG
	 * 		,PASSWORD
	 * 		,NAME_KANJI_SEI
	 * 		,NAME_KANJI_MEI
	 * 		,NAME_KANA_SEI
	 * 		,NAME_KANA_MEI
	 * 		,BUKA_NAME
	 * 		,KAKARI_NAME
	 * 		,BIKO
	 * 		,YUKO_DATE
	 * 		,DEL_FLG
	 * FROM
	 * 		GYOMUTANTOINFO
	 * WHERE
	 * 		GYOMUTANTO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数addInfoの変数GyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 　　取得した値は、GyomutantoInfoに格納する。<br /><br />
	 * 
	 * 　(5)アクセス制御情報の取得<br />
	 * 　　該当業務担当者のアクセス制御情報を取得する。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID=?
	 * ORDER BY
	 * 		GYOMUTANTO_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数addInfoの変数GyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * 　　取得した値をそれぞれのHashSetに格納し、その二つのHashSetをMapに格納する。<br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>値</td><td>HashSetの名前</td><td>Map格納時のキー名</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>tantoJigyoCd</td><td>tantoJigyoCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>tantoJigyoKubun</td><td>tantoJigyoKubun</td></tr>
	 * </table><br />
	 * 　　取得したMap内からHashSet"<b>tantoJigyoCd</b>"を取り出し、ArrayListに格納する。
	 * このListをGyomutantoInfoに加えて返却する。
	 * @param userInfo UserInfo
	 * @param addInfo GyomutantoInfo
	 * @return 業務担当者情報が格納されたGyomutantoInfo
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.GyomutantoInfo)
	 */
	public GyomutantoInfo insert(UserInfo userInfo, GyomutantoInfo addInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//RULEINFOテーブルよりルール取得準備
			RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.GYOMUTANTO);
			
			//---------------------------------------
			//パスワード情報の作成
			//---------------------------------------
			String newPassword = rureInfoDao.getPassword(connection, rulePk);
			addInfo.setPassword(newPassword);

			//---------------------------------------
			//業務担当者情報の追加
			//---------------------------------------
			GyomutantoInfoDao gyomutantoInfoDao = new GyomutantoInfoDao(userInfo);
			gyomutantoInfoDao.insertGyomutantoInfo(connection,addInfo);		

			//---------------------------------------
			//担当事業情報の追加
			//---------------------------------------
			AccessKanriInfo accessKanriInfo = new AccessKanriInfo();
			accessKanriInfo.setGyomutantoId(addInfo.getGyomutantoId());
			AccessKanriDao accessKanriDao = new AccessKanriDao(userInfo);
			//登録データがDBに存在するとエラーとなるため、念のため削除処理を実行しておく
			accessKanriDao.deleteAccessKanri(connection, addInfo);
			for(int i = 0; i < addInfo.getJigyoValues().size(); i++) {
				String jigyoKubun = MasterJigyoInfoDao.selectJigyoKubun(connection, addInfo.getJigyoValues().get(i).toString());
				accessKanriInfo.setJigyoKubun(jigyoKubun);
				accessKanriInfo.setJigyoCd(addInfo.getJigyoValues().get(i).toString());
				accessKanriDao.insertAccessKanriInfo(connection, accessKanriInfo);
			}

			//---------------------------------------
			//登録データの取得
			//---------------------------------------
			GyomutantoInfo result = gyomutantoInfoDao.selectGyomutantoInfo(connection, addInfo);

			//アクセス制御テーブルから担当事業情報を設定
			HashMap accessKanriMap = (HashMap)accessKanriDao.selectAccessKanri(connection, addInfo);
			HashSet tantoJigyoCdSet = (HashSet)accessKanriMap.get("tantoJigyoCd");
			ArrayList tantoJigyoCdList = new ArrayList(tantoJigyoCdSet);
			result.setJigyoValues(tantoJigyoCdList);

			//---------------------------------------
			//登録正常終了
			//---------------------------------------
			success = true;			
			
			return result;
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"業務担当者管理データ登録中にDBエラーが発生しました。",
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
					"業務担当者管理データ登録中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 業務担当者情報の更新.<br /><br />
	 * 業務担当者情報、アクセス管理情報の更新を行う。<br /><br />
	 * 
	 * <b>1.業務担当者情報の更新</b><br /><br />
	 * 業務担当者情報の更新を行う。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 		GYOMUTANTOINFO
	 * SET
	 * 		ADMIN_FLG=?
	 * 		,PASSWORD=?
	 * 		,NAME_KANJI_SEI=?
	 * 		,NAME_KANJI_MEI=?
	 * 		,NAME_KANA_SEI=?
	 * 		,NAME_KANA_MEI=?
	 * 		,BUKA_NAME=?
	 * 		,KAKARI_NAME=?
	 * 		,BIKO=?
	 * 		,YUKO_DATE=?
	 * 		,DEL_FLG=?
	 * WHERE
	 * 		GYOMUTANTO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>管理者フラグ</td><td>第二引数updateInfoの変数AdminFlgを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>パスワード</td><td>第二引数updateInfoの変数Passwordを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（漢字－姓）</td><td>第二引数updateInfoの変数NameKanjiSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（漢字－名）</td><td>第二引数updateInfoの変数NameKanjiMeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ－姓）</td><td>第二引数updateInfoの変数NameKanaSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ－名）</td><td>第二引数updateInfoの変数NameKanaMeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部課名</td><td>第二引数updateInfoの変数BukaNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>係名</td><td>第二引数updateInfoの変数KakariNameを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>第二引数updateInfoの変数Bikoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>有効期限</td><td>第二引数updateInfoの変数YukoDateを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>第二引数updateInfoの変数DelFlgを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数updateInfoの変数GyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * <b>2.アクセス管理情報の更新</b><br /><br />
	 * アクセス管理情報の更新を行う。更新は、一度情報を物理削除した後に新しい情報を挿入することで行われる。<br /><br />
	 * 
	 * 
	 * 　(1)アクセス管理情報の物理削除<br /><br />
	 * 　　該当する担当者のアクセス管理情報の物理削除を行う。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * DELETE
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数updateInfoの変数GyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 　(2)アクセス管理情報の登録<br /><br />
	 * 　　事業区分を取得する。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		NVL(A.JIGYO_KUBUN,0) JIGYO_KUBUN
	 * FROM
	 * 		MASTER_JIGYO A
	 * WHERE
	 * 		JIGYO_CD=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>第二引数updateInfoの変数JigyoValues内のjigyoCdを使用する。</td></tr>
	 * </table><br />
	 * 　　取得した事業区分と事業コードを、AccessKanriInfoに格納する。<br /><br />
	 * 
	 * 
	 * 　(3)担当事業情報の登録<br />
	 * 　　二重登録にならないか重複チェックを行い、重複でなければ登録を行う。<br />
	 * 　　以下のSQL文を発行し、業務担当者情報を取得する。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		GYOMUTANTO_ID
	 * 		,JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * 		,BIKO
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID=?
	 * 		AND JIGYO_CD=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>AccessKanriInfoの変数GyomutantoIdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>AccessKanriInfoの変数JigyoCdを使用する。</td></tr>
	 * </table><br />
	 * 　　該当する値が合った場合は、例外をthrowする。<br />
	 * 　　重複するものがなければ、登録を行う。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * INSERT INTO ACCESSKANRI
	 * 		(
	 * 		GYOMUTANTO_ID
	 * 		,JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * 		,BIKO
	 * 		)
	 * VALUES
	 * 		(?,?,?,?)</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>AccessKanriInfoの変数GyomutantoIdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>AccessKanriInfoの変数JigyoCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>AccessKanriInfoの変数JigyoKubunを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>AccessKanriInfoの変数JigyoBikoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 　(2),(3)はaddInfoのList<b>"getJigyoValues"</b>の件数だけ繰り返す。<br /><br />
	 * 
	 * 　更新作業中に例外が発生したら、例外をthrowする。<br /><br />
	 * @param userInfo UserInfo
	 * @param updateInfo GyomutantoInfo
	 * @return
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.GyomutantoInfo)
	 */
	public void update(UserInfo userInfo, GyomutantoInfo updateInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//業務担当者情報の更新
			//---------------------------------------
			GyomutantoInfoDao dao = new GyomutantoInfoDao(userInfo);
			dao.updateGyomutantoInfo(connection, updateInfo);
			
			//アクセス管理情報の更新(削除→登録)
			AccessKanriInfo accessKanriInfo = new AccessKanriInfo();
			accessKanriInfo.setGyomutantoId(updateInfo.getGyomutantoId());
			AccessKanriDao accessKanriDao = new AccessKanriDao(userInfo);
			accessKanriDao.deleteAccessKanri(connection, updateInfo);
			for(int i = 0; i < updateInfo.getJigyoValues().size(); i++) {
				String jigyoKubun = MasterJigyoInfoDao.selectJigyoKubun(connection, updateInfo.getJigyoValues().get(i).toString());
				accessKanriInfo.setJigyoKubun(jigyoKubun);
				accessKanriInfo.setJigyoCd(updateInfo.getJigyoValues().get(i).toString());
				accessKanriDao.insertAccessKanriInfo(connection, accessKanriInfo);
			}
			
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"業務担当者管理データ更新中にDBエラーが発生しました。",
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
					"業務担当者管理データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 業務担当者情報の削除.<br /><br />
	 * 
	 * 業務担当者情報の論理削除を行う。<br /><br />
	 * 
	 * <b>1.業務担当者情報の検索</b><br /><br />
	 * 
	 * 　以下のSQL文を発行し、業務担当者情報を検索する。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * 	SELECT
	 * 			GYOMUTANTO_ID
	 * 			,ADMIN_FLG
	 * 			,PASSWORD
	 * 			,NAME_KANJI_SEI
	 * 			,NAME_KANJI_MEI
	 * 			,NAME_KANA_SEI
	 * 			,NAME_KANA_MEI
	 * 			,BUKA_NAME
	 * 			,KAKARI_NAME
	 * 			,BIKO
	 * 			,YUKO_DATE
	 * 			,DEL_FLG
	 * 	FROM
	 * 			GYOMUTANTOINFO
	 * 	WHERE
	 * 			GYOMUTANTO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数addInfoの変数GyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * <b>2.業務担当者情報の論理削除</b><br /><br />
	 * 　以下のSQL文を発行し、業務担当者情報の論理削除を行う。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 		GYOMUTANTOINFO
	 * SET
	 * 		DEL_FLG = 1
	 * WHERE
	 * 		GYOMUTANTO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数deleteInfoの変数GyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * @param userInfo UserInfo
	 * @param deleteInfo GyomutantoInfo
	 * @return
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.GyomutantoInfo)
	 */
	public void delete(UserInfo userInfo, GyomutantoInfo deleteInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//業務担当者情報の更新
			//---------------------------------------
			GyomutantoInfoDao dao = new GyomutantoInfoDao(userInfo);
			dao.deleteFlgGyomutantoInfo(connection, deleteInfo);
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"業務担当者管理データ削除中にDBエラーが発生しました。",
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
					"業務担当者管理データ削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 業務担当者情報の取得.<br /><br />
	 * 業務担当者情報を取得し、アクセス制御情報を加えて返却する。<br /><br />
	 * 
	 * <b>1.登録データの取得</b><br /><br />
	 * 業務担当者の登録データを取得する。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		GYOMUTANTO_ID
	 * 		,ADMIN_FLG
	 * 		,PASSWORD
	 * 		,NAME_KANJI_SEI
	 * 		,NAME_KANJI_MEI
	 * 		,NAME_KANA_SEI
	 * 		,NAME_KANA_MEI
	 * 		,BUKA_NAME
	 * 		,KAKARI_NAME
	 * 		,BIKO
	 * 		,YUKO_DATE
	 * 		,DEL_FLG
	 * FROM
	 * 		GYOMUTANTOINFO
	 * WHERE
	 * 		GYOMUTANTO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数addInfoの変数GyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値は、GyomutantoInfoに格納する。<br /><br />
	 * 
	 * <b>2.アクセス管理情報の設定</b><br /><br />
	 * 該当業務担当者のアクセス制御情報を取得する。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID=?
	 * ORDER BY
	 * 		GYOMUTANTO_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数addInfoの変数GyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * 取得した値をそれぞれのHashSetに格納し、その二つのHashSetをMapに格納する。<br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>値</td><td>HashSetの名前</td><td>Map格納時のキー名</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>tantoJigyoCd</td><td>tantoJigyoCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>tantoJigyoKubun</td><td>tantoJigyoKubun</td></tr>
	 * </table><br />
	 * 取得したMap内からHashSet"<b>tantoJigyoCd</b>"を取り出し、ArrayListに格納する。
	 * このListをGyomutantoInfoに加えて返却する。
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo GyomutantoPk
	 * @return 業務担当者情報が格納されたGyomutantoInfo
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.GyomutantoPk)
	 */
	public GyomutantoInfo select(UserInfo userInfo, GyomutantoPk pkInfo)
			throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			GyomutantoInfoDao dao = new GyomutantoInfoDao(userInfo);
			GyomutantoInfo gyomutantoInfo = dao.selectGyomutantoInfo(connection, pkInfo);
			
			//アクセス管理情報の設定
			AccessKanriInfo accessKanriInfo = new AccessKanriInfo();
			accessKanriInfo.setGyomutantoId(pkInfo.getGyomutantoId());
			AccessKanriDao accessKanriDao = new AccessKanriDao(userInfo);
			HashMap accessKanriMap = (HashMap)accessKanriDao.selectAccessKanri(connection, pkInfo);
			HashSet tantoJigyoCdSet = (HashSet)accessKanriMap.get("tantoJigyoCd");
			ArrayList tantoJigyoCdList = new ArrayList(tantoJigyoCdSet);
			gyomutantoInfo.setJigyoValues(tantoJigyoCdList);
			
			return gyomutantoInfo;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"業務担当者管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * 業務担当者情報を持つPageを返す.<br /><br />
	 * 
	 * <b>1.業務担当者のデータの取得</b><br /><br />
	 * 以下のSQL文を発行して、業務担当者のデータを取得する。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * SELECT
	 * 		*
	 * FROM
	 * 		GYOMUTANTOINFO
	 * WHERE
	 * 		DEL_FLG = 0
	 * 		AND ADMIN_FLG = 0
	 * ORDER BY
	 * 		GYOMUTANTO_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * 取得した値は、列名をキーにしてMapに格納される。それをまとめてListに格納し、そのListがPageに格納される。<br /><br />
	 * 
	 * <b>2.アクセス管理情報の取得</b><br /><br />
	 * 取得したPage内のListからMapを取り出して、該当業務担当者のアクセス制御情報を格納していく。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID=?
	 * ORDER BY
	 * 		GYOMUTANTO_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>Mapに格納されたGYOMUTANTO_IDの値を使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得したMapに格納されている"事業コード"の値の入ったHashSetを取り出し、ArrayListに格納する。
	 * そのArrayListを、キー名"VALUES"で、はじめに取り出したMapに再度格納する。
	 * この処理を、Mapの数だけ繰り返す。<br /><br />
	 * 
	 * 
	 * <b>3.Pageの返却</b><br /><br />
	 * すべてのMapにアクセス制御情報が格納されたら、Listを再度Pageに格納し、返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @return 業務担当者情報を持つPage
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public Page search(UserInfo userInfo)
			throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------

		String select = "SELECT * FROM GYOMUTANTOINFO"
			+ " WHERE DEL_FLG = 0"
			+ " AND ADMIN_FLG = 0"
			+ " ORDER BY GYOMUTANTO_ID";
			
		if(log.isDebugEnabled()){
			log.debug("select:" + select);
		}
		
		//-----------------------
		// ページ取得
		//-----------------------
		SearchInfo searchInfo = new SearchInfo();
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			Page page = SelectUtil.selectPageInfo(connection, searchInfo, select);
			
			//アクセス管理情報の設定
			AccessKanriDao accessKanriDao = new AccessKanriDao(userInfo);
			List list = page.getList();
			for(int i = 0; i < list.size(); i++) {
				HashMap hashMap = (HashMap)list.get(i);
				GyomutantoPk pkInfo = new GyomutantoPk();
				pkInfo.setGyomutantoId(hashMap.get("GYOMUTANTO_ID").toString());
				HashMap accessKanriMap = (HashMap)accessKanriDao.selectAccessKanri(connection, pkInfo);
				HashSet tantoJigyoCdSet = (HashSet)accessKanriMap.get("tantoJigyoCd");
				ArrayList tantoJigyoCdList = new ArrayList(tantoJigyoCdSet);
				hashMap.put("VALUES", tantoJigyoCdList);
				list.set(i, hashMap);
			}
			page.setList(list);
			return page;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"業務担当者管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * 2重登録のチェック.<br /><br />
	 * 業務担当者情報テーブルに同じ業務担当者が登録されていないかどうか確認する。<br /><br />
	 * 
	 * <b>1.2重登録のチェックの実行</b><br /><br />
	 * 第三引数のString"mode"が、"<b>add_mode</b>"であるときに、重複チェックを実行する。
	 * 発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		COUNT(*)"
	 * FROM
	 * 		GYOMUTANTOINFO"
	 * WHERE
	 * 		GYOMUTANTO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数infoの変数gyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 該当する業務担当者のIDが存在している場合には、例外をthorwする。<br /><br />
	 * 
	 * <b>2.値の返却</b><br /><br />
	 * 第三引数のString"mode"が、"<b>add_mode</b>"でないとき、
	 * 又は登録が重複していないときには、第二引数のinfoをそのまま返却する。<br /><br />
	 * @param userInfo UserInfo
	 * @param info GyomutantoInfo
	 * @param mode String
	 * @return GyomutantoInfo
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.GyomutantoInfo)
	 */
	public GyomutantoInfo validate(UserInfo userInfo, GyomutantoInfo info, String mode)
			throws ApplicationException, ValidationException {
		if(mode.equals(IMaintenanceName.ADD_MODE)) {
			//2重登録チェック
			//業務担当者情報テーブルにすでにIDが同じ業務担当者が登録されていないかどうかを確認
			Connection connection = null;
			GyomutantoInfoDao dao = new GyomutantoInfoDao(userInfo);
			try {
				connection = DatabaseUtil.getConnection();
				int count = dao.countGyomutantoInfo(connection, info.getGyomutantoId());
				//すでに登録されている場合
				if(count > 0){
					String[] error = {"業務担当者"};
					throw new ApplicationException("すでに業務担当者が登録されています。", 	new ErrorInfo("errors.4007", error));			
				}
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"業務担当者データチェック中にDBエラーが発生しました。",
					new ErrorInfo("errors.4005"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		return info;
	}

	/**
	 * パスワードの更新.<br /><br />
	 * <b>1.登録データの取得</b><br /><br />
	 * 業務担当者の登録データを取得する。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		GYOMUTANTO_ID
	 * 		,ADMIN_FLG
	 * 		,PASSWORD
	 * 		,NAME_KANJI_SEI
	 * 		,NAME_KANJI_MEI
	 * 		,NAME_KANA_SEI
	 * 		,NAME_KANA_MEI
	 * 		,BUKA_NAME
	 * 		,KAKARI_NAME
	 * 		,BIKO
	 * 		,YUKO_DATE
	 * 		,DEL_FLG
	 * FROM
	 * 		GYOMUTANTOINFO
	 * WHERE
	 * 		GYOMUTANTO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数pkInfoの変数GyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した値は、GyomutantoInfoに格納する。<br /><br />
	 * 
	 * <b>2.パスワードの更新</b><br /><br />
	 * 
	 * 第三引数oldPasswordが、取得した値のパスワードと異なる場合には、例外をthrowする。<br />
	 * 等しい場合には、パスワードを第四引数newPasswordに書き換える。発行されるSQL文は以下の通りである。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 		GYOMUTANTOINFO
	 * SET
	 * 		PASSWORD = ?
	 * WHERE
	 * 		GYOMUTANTO_ID = ?
	 * 		AND DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第四引数newPasswordを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当ID</td><td>第二引数pkInfoの変数GyomutantoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 更新が終了したら、"true"を返却する。途中で例外が発生したら、例外をthrowする。<br /><br />
	 * @param userInfo UserInfo
	 * @param pkInfo GyomutantoPk
	 * @param oldPassword String
	 * @param newPassword String
	 * @return boolean
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#changePassword(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.GyomutantoPk, java.lang.String, java.lang.String)
	 */
	public boolean changePassword(
			UserInfo userInfo,
			GyomutantoPk pkInfo,
			String oldPassword,
			String newPassword)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//業務担当者情報の取得
			//---------------------------------------
			GyomutantoInfoDao dao = new GyomutantoInfoDao(userInfo);
			GyomutantoInfo info = dao.selectGyomutantoInfo(connection, pkInfo);

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
			if(dao.changePasswordGyomutantoInfo(connection,pkInfo,newPassword)){
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
}
