/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.common;

/**
 * 定数
 * 
 */
public interface IConstants {

	/** 成功時のページ遷移キー */
	public static final String SUCCESS_KEY = "success";

	/** 失敗時のページ遷移キー */
	public static final String FAILURE_KEY = "failure";

	/** CANCEL時のページ遷移キー */
	public static final String CANCEL_KEY = "cancel";
	
	/** トランザクショントークンエラーページ遷移キー*/
	public static final String TOKEN_ERROR_KEY = "token";
	
	/** ユーザ情報 セッションキー */
	public static final String USER_CONTAINER_KEY = "userContainer";

	/** 業務担当者ログインサーバビス */
	public static final String GYOMUTANTO_LOGIN_SERVICE = "GYOMUTANTO_LOGIN_SERVICE";
	
	/** 更新処理結果等の情報を保持するキー値 */
	public static final String RESULT_INFO = "result";

	/** 申請者登録時の連絡先担当者情報を保持するキー値 */
	public static final String RESULT_TANTO = "resultTanto";
	
	/** コード表の索引情報を保持するキー値 */
	public static final String INDEX_INFO = "index";
	
	/** コード表の検索情報を保持するキー値 */
	public static final String SEARCH_INFO = "search";

	//2005/04/13　追加 ここから-----------------------------------------------------------
	//理由 飛び番号リストのタイトル情報取得のため
	
	/** 画面のタイトル情報を保持するキー値 */
	public static final String TITLE_INFO = "title";
	
	//追加 ここまで-----------------------------------------------------------------------
	
}
