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

import java.util.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

/**
 * データ保管を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IDataHokanMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IDataHokanMaintenance {
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** 戻り値Mapキー値：1次審査結果（参照用） */
	public static final String KEY_SHINSAKEKKA_1ST = IShinsaKekkaMaintenance.KEY_SHINSAKEKKA_1ST;
	
	/** 戻り値Mapキー値：2次審査結果 */
	public static final String KEY_SHINSAKEKKA_2ND = IShinsaKekkaMaintenance.KEY_SHINSAKEKKA_2ND;	
	
	
	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
	/**
	 * データ保管処理を実行する。
	 * @param userInfo
	 * @param jigyoKanriPk
	 * @param yukoKigen
	 * @return 保管件数（申請データのレコード数）
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public int dataHokanInvoke(UserInfo userInfo, JigyoKanriPk jigyoKanriPk, Date yukoKigen)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * 申請情報を検索する。
	 * @param userInfo				実行するユーザ情報
	 * @param searchInfo           申請書検索情報
	 * @return						取得した申請情報ページオブジェクト
	 * @throws ApplicationException	
	 * @throws NoDataFoundException		対象データが見つからない場合の例外。
	 */
	public Page searchApplication(UserInfo userInfo, ShinseiSearchInfo searchInfo) 
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * PDF変換後のファイルを取得する。
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws ApplicationException
	 */
	public FileResource getPdfFileRes(UserInfo userInfo,
	                                   ShinseiDataPk shinseiDataPk)
		throws ApplicationException;
	
	
	/**
	 * CSV出力用の申請者情報を検索する。
	 * 
	 * @param userInfo				
	 * @param searchInfo			
	 * @return						
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public List searchCsvData(UserInfo userInfo, ShinseiSearchInfo searchInfo) 
		throws ApplicationException;
	
	
	/**
	 * 研究組織CSV出力用の申請者情報を検索する。
	 * 
	 * @param userInfo				
	 * @param searchInfo			
	 * @return						
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public List searchKenkyuSoshikiCsvData(UserInfo userInfo, ShinseiSearchInfo searchInfo) 
		throws ApplicationException;
	
	
	/**
	 * 審査結果を返す。
	 * 1次審査結果（参照用）と2次審査結果をMap形式で返す。
	 * Mapのキーは[KEY_SHINSAKEKKA_1ST], [KEY_SHINSAKEKKA_2ND]となる。
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Map getShinsaKekkaBoth(UserInfo userInfo,
								   ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * 1次審査結果（参照用）を返す。
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public ShinsaKekkaReferenceInfo getShinsaKekkaReferenceInfo(UserInfo userInfo,
																 ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * 2次審査結果を返す。
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public ShinsaKekka2ndInfo getShinsaKekka2nd(UserInfo userInfo,
												 ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * 1次審査結果入力情報（詳細）を返す。
	 * @param userInfo
	 * @param shinsaKekkaPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public ShinsaKekkaInputInfo select1stShinsaKekka(UserInfo userInfo,
														ShinsaKekkaPk shinsaKekkaPk)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * 審査結果入力情報のファイルリソースを取得する。
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				審査結果テーブル主キー。
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public FileResource getHyokaFileRes(UserInfo userInfo,
										 ShinsaKekkaPk pkInfo) 
		throws ApplicationException;
	
	
	/**
	 * 指定された申請データの推薦書ファイルを返す。
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */	
	public FileResource getSuisenFileRes(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException;	
	
}