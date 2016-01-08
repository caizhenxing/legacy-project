/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.sql.Connection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.go.jsps.kaken.model.IAuthentication;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.ShinsainInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.InvalidLogonException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinsainPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.model.exceptions.*;
/**
 * 審査員のログオン認証を実装するクラス。
 * 
 * ID RCSfile="$RCSfile: ShinsainAuthentication.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:47 $"
 */
public class ShinsainAuthentication implements IAuthentication {

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsainAuthentication() {
		super();
	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.IAuthentication#authenticate(java.lang.String, java.lang.String)
	 */
	public UserInfo authenticate(String userid, String password)
		throws InvalidLogonException, ApplicationException {
        
// 2007/02/03 張志男　追加ここから
        /** ログ（ログイン）*/
        Log loginLog = LogFactory.getLog("login");
        boolean logErrors = false;
// 2006/12/07　張志男　追加ここまで
        
		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//認証
			ShinsainInfoDao dao = new ShinsainInfoDao(UserInfo.SYSTEM_USER);
            
// 2007/02/03 張志男　追加ここから
            /** ログイン（認証前） */
            loginLog.info( " ログイン（開始）, ユーザ種別 : " + UserRole.SHINSAIN + " , ログインID : " + userid);
// 2007/02/03　張志男　追加ここまで
            
			if (!dao.authenticateShinsainInfo(connection, userid, password)) {
                logErrors= true ;                
				throw new InvalidLogonException(
					"ユーザIDまたは、パスワードが違います。審査員情報：ユーザID '"
						+ userid
						+ "' パスワード'"
						+ password
						+ "'");
			}
			//ログイン情報の取得
			ShinsainPk pkInfo = new ShinsainPk();
			pkInfo.setShinsainNo(userid.substring(3,10));	//審査員番号(7桁)
			pkInfo.setJigyoKubun(userid.substring(2,3));	//事業区分
			ShinsainInfo info = dao.selectShinsainInfo(connection, pkInfo);
			
			//IDの有効期限をチェックする
			Date date = info.getYukoDate();
			if(date != null){
				DateUtil yukoDate = new DateUtil(date);
				DateUtil now      = new DateUtil();
				//現在日付と有効期限日を比較
				int hi = now.getElapse(yukoDate);
				if(hi < 0){
                    logErrors= true ;
					//有効期限日を過ぎている場合
					throw new InvalidLogonException(
						"ユーザIDの有効期限が過ぎています。審査員情報：ユーザID '"
							+ userid
							+ "' パスワード'"
							+ password
							+ "'"
							, new ErrorInfo("errors.5013"));
				}
			}
            
// 2007/02/03 張志男　追加ここから
            /** ログイン（認証成功） */
            loginLog.info( " ログイン（終了）, ユーザ種別 : " + UserRole.SHINSAIN + " , ログインID : " + userid);
// 2007/02/03　張志男　追加ここまで
            
			//ログインした審査員情報をユーザ情報にセット
			UserInfo userInfo = new UserInfo();
			userInfo.setShinsainInfo(info);
			userInfo.setRole(UserRole.SHINSAIN);
			
            //2005/10/20最終ログイン日追加
			info.setLoginDate(new Date());		//データ保管日
			dao.updateShinsainInfo(connection, info);

			success = true;
			return userInfo;

		} catch (DataAccessException e) {
            logErrors= true ;
			throw new ApplicationException(
				"審査員認証中にDBエラーが発生しました。",
				new ErrorInfo("errors.4006"),
				e);
		} catch (NoDataFoundException e) {
            logErrors= true ;
			throw new ApplicationException(
				"審査員認証後、審査員情報の取得に失敗しました。",
				new ErrorInfo("errors.4006"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
                logErrors= true ;
				throw new ApplicationException(
				"審査員認証注にDBエラーが発生しました。",
				new ErrorInfo("errors.4002"),
				e);
			}finally {
                if(logErrors)
                {
// 2007/02/03 張志男　追加ここから
                    /** ログイン（認証失敗後） */
                    loginLog.info( " ログイン（失敗）, ユーザ種別 : " + UserRole.SHINSAIN + " , ログインID : " + userid + " , パスワード : " + password);
// 2007/02/03　張志男　追加ここまで
                }
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
}
