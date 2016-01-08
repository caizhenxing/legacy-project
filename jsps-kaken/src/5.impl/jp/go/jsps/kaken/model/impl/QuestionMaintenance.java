/*======================================================================
 *    SYSTEM      : 
 *    Source name : QuestonMaintenance.java
 *    Description : アンケート情報に更新処理実装クラス
 *
 *    Author      : Admin
 *    Date        : 2005/10/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *　　2005/10/27    1.0         Amemiya    新規作成
 *
 *====================================================================== 
 */package jp.go.jsps.kaken.model.impl;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.go.jsps.kaken.model.IQuestionMaintenance;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.impl.QuestionInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.QuestionInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;

/**
 * @author user1
 * 
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。 ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞
 * コード・テンプレート
 */
public class QuestionMaintenance implements IQuestionMaintenance {
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(QuestionMaintenance.class);

	/** システム受付番号取得リトライ回数 */
	protected static final int SYSTEM_NO_MAX_RETRY_COUNT = 
					ApplicationSettings.getInt(ISettingKeys.SYSTEM_NO_MAX_RETRY_COUNT);

	/**
	 *  コンストラクタ
	 */
	public QuestionMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------
		
	/**
	 * システム受付番号の生成.<br><br>
	 * 
	 * WASのシステム日付をフォーマットし、受付番号を取得する。<br>
	 * フォーマットパターン－"yyyyMMddHHmmssSSS"
	 * 
	 * @return システム受付番号
	 */
	//2004/11/25 private → pubic
	public synchronized static String getUketukeNo()
	{
		//念のため1ミリ秒スリープさせて確実に別番号を返す。
		try{
			Thread.sleep(1);
		}catch(InterruptedException e){
			e.printStackTrace();	//特に何も処理しない
		}
		//現在時刻をシステム受付番号のフォーマットに変換する
		Date now = new Date();
		String systemNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(now);
		return systemNo;
		
	}
	
//	public synchronized static String getKinyuDate()
//	{
//		//現在時刻をシステム受付番号のフォーマットに変換する
//		Date now = new Date();
//		String systemNo1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);
//		return systemNo1;
//	}


	/*
	 * アンケート情報データ新規登録処理
	 * 
	 * @see jp.go.jsps.kaken.model.IQuestionMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.QuestionInfo)
	 */
	public void insert(UserInfo userInfo, QuestionInfo addInfo) throws ApplicationException
	{

		Connection connection = null;
		boolean success = false;

		if ( log.isDebugEnabled() ){
			log.debug("アンケート情報登録開始");
		}
		
		//--------------------
		// アンケートデータ登録
		//--------------------
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			addInfo.setUketukeNo(getUketukeNo());
//			addInfo.setKinyuDate(getKinyuDate());
			QuestionInfoDao dao = new QuestionInfoDao();
			//-- 登録時にキーが重なった場合はリトライをかける --
			int count = 0;
			while (true) {
				try {
					//2005.11.04 iso ユーザによってDaoを選択
					if(userInfo.getRole() != null) {
						if(userInfo.getRole().equals(UserRole.QUESTION_SHINSEISHA)) {
							dao.insertQuestionShinseishaInfo(connection, addInfo);
						} else if(userInfo.getRole().equals(UserRole.QUESTION_SHOZOKUTANTO)) {
							dao.insertQuestionShozokuInfo(connection, addInfo);
						} else if(userInfo.getRole().equals(UserRole.QUESTION_BUKYOKUTANTO)) {
							dao.insertQuestionBukyokuInfo(connection, addInfo);
						} else if(userInfo.getRole().equals(UserRole.QUESTION_SHINSAIN)) {
							dao.insertQuestionShinsainInfo(connection, addInfo);
						} else {
							throw new ApplicationException(
									"ユーザのロール情報が想定外です。",
									new ErrorInfo("errors.system"));
						}
					} else {
						throw new ApplicationException(
								"ユーザのロール情報が設定されていません。",
								new ErrorInfo("errors.system"));
					}
					success = true;
					break;
				} catch (DuplicateKeyException e) {
					count++;
					if (count < SYSTEM_NO_MAX_RETRY_COUNT) {
						if ( log.isDebugEnabled() ){
							log.debug("アンケート情報登録に第" + count + "回失敗しました。");
						}
						addInfo.setUketukeNo(getUketukeNo());
						 //システム受付番号を再取得
						continue;
					} else {
						throw e;
					}
				}
			}
		}
		catch (DataAccessException e) {

			throw new ApplicationException("アンケート情報登録中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"), e);
		}
		finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("アンケート情報登録中にDBエラーが発生しました。",
						new ErrorInfo("errors.4001"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

}