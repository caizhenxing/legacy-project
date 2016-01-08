/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.JigyoKanriSearchInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
import jp.go.jsps.kaken.model.vo.ShoruiKanriPk;
import jp.go.jsps.kaken.model.vo.ShoruiKanriSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * 事業管理情報を管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IJigyoKanriMaintenance.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/07/24 03:00:59 $"
 */
public interface IJigyoKanriMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** 戻り値Mapキー値：事業管理情報 */
	public static final String KEY_JIGYOKANRI_INFO = "key_jigyokanri_info";
    
	/** 戻り値Mapキー値：書類管理リスト*/
	public static final String KEY_SHORUIKANRI_LIST   = "key_shoruikanri_list";
		
	/** ダウンロードファイルフラグ。添付ファイル（Win） */
	public static String FILE_FLG_TENPU_WIN = "0";
	
	/** ダウンロードファイルフラグ。添付ファイル（Mac） */
	public static String FILE_FLG_TENPU_MAC = "1";
	
	/** ダウンロードファイルフラグ。評価用ファイル */
	public static String FILE_FLG_HYOKA = "2";


	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
	/**
	 * 事業管理情報を新規作成する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param addInfo				作成するユーザ情報。
	 * @return						新規登録したユーザ情報
	 * @throws ApplicationException	
	 */
	public JigyoKanriInfo insert(UserInfo userInfo,JigyoKanriInfo addInfo) throws ApplicationException;

	/**
	 * 書類管理情報を新規作成する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param addInfo				作成するユーザ情報。
	 * @return						新規登録したユーザ情報
	 * @throws ApplicationException	
	 */
	public List insert(UserInfo userInfo,ShoruiKanriInfo addInfo) throws ApplicationException;

	/**
	 * 事業管理情報を更新する。
	 * @param userInfo				実行するユーザ情報。
	 * @param updateInfo			更新するユーザ情報。
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,JigyoKanriInfo updateInfo) throws ApplicationException;

	/**
	 * 事業管理情報の公開確定情報を更新する。
	 * 公開確定情報を更新する場合は、パスワードのチェックを行う。
	 * @param userInfo				実行するユーザ情報。
	 * @param updateInfo			更新するユーザ情報。
	 * @param password				パスワード情報。
	 * @throws ApplicationException
	 */
	public void updateKokaiKakutei(UserInfo userInfo,JigyoKanriPk[] jigyoPks, String kessaiNo, String password) throws ApplicationException;

	/**
	 * 事業管理情報を削除する。
	 * @param userInfo				実行するユーザ情報。
	 * @param deleteInfo			削除するユーザ情報。
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,JigyoKanriInfo deleteInfo) throws ApplicationException;

	/**
	 * 書類管理情報を削除する。
	 * @param userInfo				実行するユーザ情報。
	 * @param deleteInfo			削除するユーザ情報。
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,ShoruiKanriPk pkInfo) throws ApplicationException;
	
	/**
	 * 書類管理情報を削除する。
	 * @param userInfo				実行するユーザ情報。
	 * @param deleteInfo			削除するユーザ情報。
	 * @throws ApplicationException
	 */
	public List delete(UserInfo userInfo,ShoruiKanriInfo deleteInfo) throws ApplicationException;

	/**
	 * 事業管理情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				検索するユーザ情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public JigyoKanriInfo select(UserInfo userInfo,JigyoKanriPk pkInfo) throws ApplicationException;
	
	/**
	 * 書類情報を検索する。
	 * 事業管理情報と書類管理情報をMap形式で返す。
	 * Mapのキーは[KEY_JIGYOKANRI_INFO], [KEY_SHORUIKANRI_LIST]となる。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				検索情報。
	 * @return						取得したユーザ情報。
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public Map select(UserInfo userInfo, ShoruiKanriPk pkInfo) throws ApplicationException;

	/**
	 * 書類情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				検索情報。
	 * @return						取得したユーザ情報。
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public ShoruiKanriInfo selectShoruiInfo(UserInfo userInfo, ShoruiKanriPk pkInfo) throws ApplicationException;
	
	/**
	 * 事業管理情報を検索する。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException;

	/**
	 * 事業管理情報を検索する。
	 * 業務担当者以外は、selectJigyoInfoList(Connection connection) と
	 * 同じ結果が返る。
	 * <li>業務担当者･･････自分が担当する事業区分に該当する事業リスト</li>
	 * <li>業務担当者以外･･･全部</li>
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo, JigyoKanriSearchInfo searchInfo) throws ApplicationException;
	
	/**
	 * 書類管理情報を検索する。
	 * ソート順は、システム受付番号の昇順とする。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public List search(UserInfo userInfo, ShoruiKanriSearchInfo searchInfo) throws ApplicationException;

	/**
	 * 受付中の事業管理情報を検索する。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public Page searchUketukeJigyo(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException;

	/**
	 * 登録または更新する事業管理情報を形式チェックする。
	 * @param userInfo					実行するユーザ情報
	 * @param insertOrUpdateInfo		登録または新規作成する事業管理情報
	 * @param mode						モード
	 * @return							形式チェック後の事業管理情報
	 * @throws ApplicationException	
	 * @throws ValidationException		検証にエラーがあった場合。
	 */
	public JigyoKanriInfo validate(UserInfo userInfo,JigyoKanriInfo insertOrUpdateInfo, String mode)
		throws ApplicationException, ValidationException;

	/**
	 * 事業管理情報のファイルリソースを取得する。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @param fileFlag				ダウンロードするファイルの種類を表す。
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public FileResource getJigyoKanriFileRes(UserInfo userInfo,JigyoKanriPk jigyoPk, String fileFlg) throws ApplicationException;

	/**
	 * 書類管理情報のファイルリソースを取得する。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public FileResource getShoruiKanriFileRes(UserInfo userInfo,ShoruiKanriPk getInfo) throws ApplicationException;

	/**
	 * 書類一覧を取得する。
	 * 事業管理情報テーブルと書類管理情報テーブルのデータを取得する。
	 * ソート順は、事業ID,システム受付番号の昇順とする。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索するユーザ情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException
	 */
	public Page searchShoruiList(UserInfo userInfo, ShoruiKanriSearchInfo searchInfo) throws ApplicationException;

	/**
	 * 割り振り対象事業管理情報を検索する。
	 * 割り振り処理は申請状況が「06(学進受理)」の申請データの存在する事業となる。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public Page searchWarifuriJigyo(UserInfo userInfo,SearchInfo searchInfo) throws ApplicationException;
	
	
	/**
	 * 事業情報を取得する。
	 * 事業コード、年度（和暦）、回数を元に事業情報を取得する。
	 * @param userInfo
	 * @param jigyoCd
	 * @param nendo
	 * @param kaisu
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public JigyoKanriInfo getJigyoKanriInfo(UserInfo userInfo,
											 String   jigyoCd,
											 String   nendo,
											 String   kaisu)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 *  仮領域番号発行情報登録
	 *  研究領域最終年度前年度の応募
	 * @param userInfo
	 * @param ryoikiKeikakushoInfo
	 * @return
	 * @throws ApplicationException
	 */
	public RyoikiKeikakushoInfo ryoikiKeikakushoInfo(UserInfo userInfo,
			RyoikiKeikakushoInfo ryoikiKeikakushoInfo) throws ApplicationException;
}






