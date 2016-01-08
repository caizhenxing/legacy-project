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
import jp.go.jsps.kaken.model.IShozokuMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShozokuInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.InvalidLogonException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.DateUtil;

/**
 * 所属機関担当者者のログオン認証を実装するクラス。
 * 
 * ID RCSfile="$RCSfile: ShozokutantoAuthentication.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:47 $"
 */
public class ShozokutantoAuthentication implements IAuthentication {

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShozokutantoAuthentication() {
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
// 2007/02/03　張志男　追加ここまで
        
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//認証
			ShozokuInfoDao dao = new ShozokuInfoDao(UserInfo.SYSTEM_USER);
            
// 2007/02/03 張志男　追加ここから
            /** ログイン（認証前） */
            loginLog.info( " ログイン（開始）, ユーザ種別 : " + UserRole.SHOZOKUTANTO + " , ログインID : " + userid);
// 2007/02/03　張志男　追加ここまで
            
			if (!dao.authenticateShozokuInfo(connection, userid, password)) {
                logErrors= true ;                
				throw new InvalidLogonException(
					"ユーザIDまたは、パスワードが違います。所属機関担当者情報：ユーザID '"
						+ userid
						+ "' パスワード'"
						+ password
						+ "'");
			}
			//ログイン情報の取得
			ShozokuPk pkInfo = new ShozokuPk();
			pkInfo.setShozokuTantoId(userid);
			ShozokuInfo info = dao.selectShozokuInfo(connection, pkInfo);
		
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
						"ユーザIDの有効期限が過ぎています。所属機関担当者情報：ユーザID '"
							+ userid
							+ "' パスワード'"
							+ password
							+ "'"
							, new ErrorInfo("errors.5013"));
				}
			}

			//2005.08.08 iso ログイン者が所属している機関の存在をチェックする（存在しない場合はログイン不可）
			//ダミーコードの場合は、機関マスタに存在しなくてもログインを許可する。
			if(!info.getShozokuCd().equals(IShozokuMaintenance.OTHER_KIKAN_CODE)) {
				MasterKikanInfoDao masterKikanInfoDao = new MasterKikanInfoDao(UserInfo.SYSTEM_USER);
				int kikanCount = masterKikanInfoDao.countShozokuInfo(connection,info.getShozokuCd());
				
				if(kikanCount < 1){
                    logErrors= true ;
					//所属機関が存在しない場合
					throw new InvalidLogonException(
						"ユーザの所属機関が存在しません。所属担当者情報：ユーザID '"
							+ userid
							+ "' パスワード'"
							+ password
							+ "'"
							, new ErrorInfo("errors.5024"));
				}
			}
			
// 2007/02/03 張志男　追加ここから
            /** ログイン（認証成功） */
            loginLog.info( " ログイン（終了）, ユーザ種別 : " + UserRole.SHOZOKUTANTO + " , ログインID : " + userid);
// 2007/02/03　張志男　追加ここまで
            
			//ログインした所属機関担当者情報をユーザ情報にセット
			UserInfo userInfo = new UserInfo();
			userInfo.setShozokuInfo(info);
			userInfo.setRole(UserRole.SHOZOKUTANTO);

			return userInfo;

		} catch (DataAccessException e) {
            logErrors= true ;
			throw new ApplicationException(
				"所属機関担当者認証中にDBエラーが発生しました。",
				new ErrorInfo("errors.4006"),
				e);
		} catch (NoDataFoundException e) {
            logErrors= true ;
			throw new ApplicationException(
				"所属機関担当者認証後、所属機関担当者情報の取得に失敗しました。",
				new ErrorInfo("errors.4006"),
				e);
		} finally {
            if(logErrors)
            {
// 2007/02/03 張志男　追加ここから
                /** ログイン（認証失敗後） */
                loginLog.info( " ログイン（失敗）, ユーザ種別 : " + UserRole.SHOZOKUTANTO + " , ログインID : " + userid + " , パスワード : " + password);
// 2007/02/03　張志男　追加ここまで
            }
			DatabaseUtil.closeConnection(connection);
		}
	}
}
