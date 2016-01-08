/*
 * 作成日: 2005/03/25
 *
 */
package jp.go.jsps.kaken.model.impl;

import java.sql.Connection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.go.jsps.kaken.model.IBukyokutantoMaintenance;
import jp.go.jsps.kaken.model.IShozokuMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.BukyokutantoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.InvalidLogonException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoPk;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.StringUtil;

/**
 * 部局担当者のログオン認証を実装するクラス。
 * 
 * @author yoshikawa_h
 *
 */
public class BukyokutantoAuthentication {
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public BukyokutantoAuthentication() {
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
				BukyokutantoInfoDao dao = new BukyokutantoInfoDao(UserInfo.SYSTEM_USER);
				
// 2007/02/03 張志男　追加ここから
                /** ログイン（認証前） */
                loginLog.info( " ログイン（開始）, ユーザ種別 : " + UserRole.BUKYOKUTANTO + " , ログインID : " + userid);
// 2007/02/03　張志男　追加ここまで
                
				// 2005/04/08 追加 ここから---------------------------------------------------
				// 理由 ユーザIDとパスワードの確認のため
				if (!dao.authenticateBukyokuInfo(connection, userid, password)) {
                    logErrors= true ;                    
					throw new InvalidLogonException(
						"ユーザIDまたは、パスワードが違います。部局担当者情報：ユーザID '"
							+ userid
							+ "' パスワード'"
							+ password
							+ "'");
				}
				// 追加 ここまで--------------------------------------------------------------
				
				//ログイン情報の取得
				BukyokutantoPk pkInfo = new BukyokutantoPk();
				pkInfo.setBukyokutantoId(userid);
				BukyokutantoInfo info = dao.selectBukyokutantoInfo(connection, pkInfo);
				
//				//所属機関担当者が削除されているときエラー
//				if(info.getDelFlgShozoku().equals("1")){
//					throw new InvalidLogonException(
//							"所属機関の担当者が削除されています。所属機関コード'"
//								+ info.getShozokuCd() + "'"
//								, new ErrorInfo("errors.5035"));
//				}
				
				//2005.08.16 iso 所属機関登録時に所属機関担当者のパスワードが入るようになり、
				//所属機関で登録していない部局担当者もログイン可能となってしまったので、
				//ここで登録フラグを見てログイン制御するように変更
				if(StringUtil.isBlank(info.getRegistFlg())) {
                    logErrors= true ;
					throw new ApplicationException(
						"部局担当者認証後、登録済みフラグの取得に失敗しました。",
						new ErrorInfo("errors.4006")
						);
				} else if(IBukyokutantoMaintenance.REGIST_FLG_YET.equals(info.getRegistFlg())) {
                    logErrors= true ;
					throw new InvalidLogonException(
											"所属機関で未登録の部局担当者です。部局担当者情報：ユーザID '"
												+ userid
												+ "'");
				}
				
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
							"ユーザIDの有効期限が過ぎています。部局担当者情報：ユーザID '"
								+ userid
								+ "' パスワード'"
								+ password
								+ "'"
								, new ErrorInfo("errors.5013"));
					}
				}
				
				//担当部局情報
				BukyokutantoInfo[] tanto = dao.selectTantoBukyokuInfo(connection,pkInfo);
				if(tanto.length != 0){
					info.setTantoFlg(true);
				}else{
					info.setTantoFlg(false);
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
							"ユーザの所属機関が存在しません。部局担当者情報：ユーザID '"
								+ userid
								+ "' パスワード'"
								+ password
								+ "'"
								, new ErrorInfo("errors.5024"));
					}
				}
                
// 2007/02/03 張志男　追加ここから
                /** ログイン（認証成功） */
                loginLog.info( " ログイン（終了）, ユーザ種別 : " + UserRole.BUKYOKUTANTO + " , ログインID : " + userid);
// 2007/02/03　張志男　追加ここまで
                
				//ログインした部局担当者情報をユーザ情報にセット
				UserInfo userInfo = new UserInfo();
				userInfo.setBukyokutantoInfo(info);
				userInfo.setRole(UserRole.BUKYOKUTANTO);

				return userInfo;

			} catch (DataAccessException e) {
                logErrors= true ;
				throw new ApplicationException(
					"部局担当者認証中にDBエラーが発生しました。",
					new ErrorInfo("errors.4006"),
					e);
			} catch (NoDataFoundException e) {
                logErrors= true ;
				throw new ApplicationException(
					"部局担当者認証後、部局担当者情報の取得に失敗しました。",
					new ErrorInfo("errors.4006"),
					e);
			} finally {
                if(logErrors)
                {
// 2007/02/03 張志男　追加ここから
                    /** ログイン（認証失敗後） */
                    loginLog.info( " ログイン（失敗）, ユーザ種別 : " + UserRole.BUKYOKUTANTO + " , ログインID : " + userid + " , パスワード : " + password);
// 2007/02/03　張志男　追加ここまで
                }
				DatabaseUtil.closeConnection(connection);
			}
		}
}
