/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.sql.Connection;
import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * チェックリスト処理の管理を行うインターフェース。
 * 
 */
public interface ICheckListMaintenance {

// 20050715
	/** 戻り値Mapキー値：受理不受理リスト */
	public static final String KEY_JURIFUJURI_LIST		= "key_jurifujuri_list";
// Horikoshi

	/**
	 * チェックリスト一覧表示用のデータを取得する。
	 * @param userInfo 実行するユーザ情報
	 * @param info チェックリストテーブル操作用データリスト
	 * @return ページ情報
	 * @throws ApplicationException		
	 */
	public Page selectCheckList(UserInfo userInfo, CheckListSearchInfo info)
            throws ApplicationException;
// 20050627
    /**
     * チェックリスト一覧表示用のデータを取得する。
     * @param userInfo 実行するユーザ情報
     * @param info チェックリストテーブル操作用データリスト
     * @param blnFlg
     * @return ページ情報
     * @throws ApplicationException     
     */
	public Page selectCheckList(UserInfo userInfo, CheckListSearchInfo info, boolean blnFlg)
            throws ApplicationException;
// Horikoshi
		
	/**
	 * チェックリスト表示用のデータを取得する。
	 * @param userInfo		実行するユーザ情報
	 * @param info			チェックリストテーブル操作用データリスト
	 * @return						ページ情報
	 * @throws ApplicationException
	 */
	public Page selectListData(UserInfo userInfo, CheckListSearchInfo info) throws ApplicationException;
		
	/**
	 * チェックリストの状況IDを取得する。
	 * @param userInfo		実行するユーザ情報
	 * @param info			チェックリストテーブル操作用データリスト
	 * @return						状況ID
	 * @throws ApplicationException
	 */
	public String checkJokyoId(UserInfo userInfo, CheckListSearchInfo info) throws ApplicationException;
	
	/**
	 * チェックリストの確定処理を行う。
	 * @param userInfo		実行するユーザ情報
	 * @param info			チェックリストテーブル操作用データリスト
	 * @param isVersionUp	確定か確定解除かを判別
	 * @throws ApplicationException
	 * @throws ValidationException
	 */
	public void checkListUpdate(UserInfo userInfo, CheckListSearchInfo info, boolean isVersionUp)
            throws ApplicationException, ValidationException;
	
	/**
	 * チェックリストの飛び番号表示用のデータを取得する。
	 * @param userInfo		実行するユーザ情報
	 * @param info			チェックリストテーブル操作用データリスト
	 * @return				ページ情報
	 * @throws ApplicationException
	 */
	public Page selectTobiList(UserInfo userInfo, CheckListSearchInfo info)
            throws ApplicationException;
	
	/**
	 * チェックリスト情報が学振受付期限内か確認する。
	 * @param userInfo 実行するユーザ情報
	 * @param searchInfo チェックリストテーブル操作用データリスト
	 * @return 期限内かどうかの確認フラグ true･･･期限内/false･･･期限外
	 * @throws ApplicationException
	 */
	public boolean checkLimitDate(UserInfo userInfo, CheckListSearchInfo searchInfo)
            throws ApplicationException;
	
	/**
	 * 一括受理登録を行う。
	 * @param userInfo 	UserInfo	ユーザ情報
	 * @param shozokuArray	List		所属機関CDの配列
	 * @param jigyoArray	List		事業IDの配列
	 * @param systemArray	List		システム受付番号の配列
     * @param comment       String
	 * @throws ApplicationException  
	 */
// 20050721
//	public void juriAll(UserInfo userInfo, List shozokuArray, List jigyoArray, List systemArray) throws ApplicationException;
	public void juriAll(UserInfo userInfo, List shozokuArray, List jigyoArray,
            List systemArray, String comment)
            throws ApplicationException;
// Horikoshi

	/**
	 * チェックリストCSV出力用のList作成
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @return						取得したユーザ情報。
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public List searchCsvData(UserInfo userInfo, CheckListSearchInfo searchInfo) throws ApplicationException;

	/**
	 * チェックリスト一覧CSV出力用のList作成
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @return						取得したユーザ情報。
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public List searchCsvDataIchiran(UserInfo userInfo, CheckListSearchInfo searchInfo)
            throws ApplicationException;

	
	//2005/05/19 追加 ここから----------------------------------------------
	//理由　チェックリスト画面、飛び番号リスト画面のタイトル情報取得のため
	
	/**
	 * チェックリストタイトル情報取得
	 * 
	 * @param searchInfo		検索情報
	 * @return					ページ情報
	 * @throws ApplicationException
	 */
	public Page selectTitle(CheckListSearchInfo searchInfo)throws ApplicationException;
	//追加 ここまで---------------------------------------------------------
	
//	//2005/05/25 追加 ここから----------------------------------------------
//	//理由 PDFファイルパス取得のため
//	/**
//	 * 表紙PDFファイルパス取得
//	 * 
//	 * @param userInfo	UserInfo
//	 * @param searchInfo	CheckListSearchInfo
//	 * @return	表紙PDFファイルパス
//	 * @exception ApplicationException
//	 */
//	public String getPdfFilePath(UserInfo userInfo, CheckListSearchInfo searchInfo) throws ApplicationException;
//	
//	//追加 ここまで---------------------------------------------------------

	/**
	 * 表紙用PDFをダウンロードする。
	 * @param userInfo ログイン者情報
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public FileResource getPdfFile(UserInfo userInfo, CheckListSearchInfo searchInfo)
            throws ApplicationException;
	
	
	
// 20050714
//	/**
//	 * チェックリストに該当する全ての研究者を取得
//	 * @param userInfo ログイン者情報
//	 * @param searchInfo					検索情報
//	 * @return								研究者情報
//	 * @throws ApplicationException
//	 */
//	public List getKenkyushaList(UserInfo userInfo, CheckListSearchInfo searchInfo) throws ApplicationException;

	/**
	 * Listに格納された研究者の存在チェック
     * @param userInfo ログイン者情報
     * @param searchInfo
	 * @param connection
	 * @return List 検索結果(True：研究者が全て存在、False：存在しない研究者あり)
	 * @throws ApplicationException
	 */
	public List chkKenkyushaExist(
					UserInfo userInfo,
					CheckListSearchInfo searchInfo,
					Connection connection
					) throws ApplicationException;

    /**
     * 研究者情報リストのチェック（研究者番号と機関コードの存在チェック）
     * @param userInfo ログイン者情報
     * @param searchInfo
     * @param shozokuCdArray
     * @param jigyoIdArray
     * @return List 検索結果(True：研究者が全て存在、False：存在しない研究者あり)
     * @throws ApplicationException
     */
	public List IkkatuKenkyushaExist(
					UserInfo userInfo,
					CheckListSearchInfo searchInfo,
					List shozokuCdArray,
					List jigyoIdArray
					) throws ApplicationException;

// Horikoshi

	/**
	 * 応募書類の提出書出力（基盤研究等、特定領域研究）
	 * @param userInfo ログイン者情報
	 * @param checkInfo チェックリスト検索条件
	 * @return　FileResource　出力情報CSVファウル
     * @throws ApplicationException
	 */
	public FileResource createOuboTeishutusho(
			UserInfo userInfo,
			CheckListSearchInfo checkInfo)
			throws ApplicationException;

	/**
	 * 受理、不受理処理を実行する
	 * @param userInfo ログイン者情報
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 * @throws NoDataFoundException
	 */
	public List CheckListAcceptUnacceptable(UserInfo userInfo, CheckListSearchInfo searchInfo)
            throws ApplicationException, NoDataFoundException;

	/**
	 * 受理を実行
	 * @param checkInfo
	 * @param userInfo ログイン者情報
	 * @param connection
	 * @param shozokuArray
	 * @param jigyoArray
	 * @param systemArray
	 * @return
	 * @throws ApplicationException
	 */
//	public boolean CheckListJuri(CheckListSearchInfo checkInfo, UserInfo userInfo, Connection connection, List shozokuArray, List jigyoArray, List systemArray) throws ApplicationException;
	public List CheckListJuri(CheckListSearchInfo checkInfo, UserInfo userInfo,
            Connection connection, List shozokuArray, List jigyoArray, List systemArray)
            throws ApplicationException;

	/**
	 * 不受理を実行
	 * @param userInfo ログイン者情報
	 * @param connection
	 * @param shinseiDataPk
	 * @param comment
	 * @param seiriNo
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean EntryFujuriInfo(UserInfo userInfo, Connection connection,
            ShinseiDataPk shinseiDataPk, String comment, String seiriNo)
            throws NoDataFoundException, ApplicationException;

}