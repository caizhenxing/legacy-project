/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.MasterKanriInfo;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * 所属機関情報を管理を行うインターフェース。
 * 
 */
public interface ISystemMaintenance {
	
	/** マスタ種別：分科細目マスタ */
	public static final String MASTER_SAIMOKU           = "1";		
	
	/** マスタ種別：所属機関マスタ */
	public static final String MASTER_KIKAN             = "2";		
	
	/** マスタ種別：職種マスタ */
	//public static final String MASTER_SHOKUSHU          = "3";		
	
	/** マスタ種別：部局マスタ */
	public static final String MASTER_BUKYOKU           = "4";		
	
	/** マスタ種別：審査員マスタ（学術創成） */
	public static final String MASTER_SHINSAIN_GAKUJUTU = "5";		
	
	/** マスタ種別：審査員マスタ（基盤研究） */
	public static final String MASTER_SHINSAIN_KIBAN    = "6";
	
	//2005/04/22　追加 ここから-------------------------------------
	//理由　マスタ取り込み用に研究者マスタと継続課題マスタの種別を追加
	
	/** マスタ種別：研究者マスタ */
	public static final String MASTER_KENKYUSHA = "7";
	
	/** マスタ種別：継続課題マスタ */
	public static final String MASTER_KEIZOKUKADAI = "8";

	//追加 ここまで-------------------------------------------------

	/** マスタ種別：割り振り結果情報 */
	public static final String MASTER_WARIFURIKEKKA     = "9";	

	/** マスタ種別：領域マスタ */
	public static final String MASTER_RYOIKI	 = "10";	

	/** マスタ種別：キーワードマスタ */
	public static final String MASTER_KEYWORD	 = "11";	
	
	/** マスタ取り込み種別：新規 */
	public static final String MASTER_TORIKOMI_SHINKI   = "0";
	
	/** マスタ取り込み種別：更新 */
	public static final String MASTER_TORIKOMI_KOSHIN   = "1";
	
	

	/**
	 * マスタ管理情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @return						取得したマスタ管理情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public List selectList(UserInfo userInfo) 
		throws ApplicationException;
	
	
	/**
	 * CSVファイルを取得する
	 * @param userInfo ユーザ情報
	 * @param masterShubetu　ユーザが選択したマスタファイル番号
	 * @return　fileResource　csvファイル
	 * @throws ApplicationException　アプリケーションエラーが発生した場合
	 * @throws DataAccessException データベースアクセス中にエラーが発生した場合
	 */
	public FileResource getCsvFileResource(UserInfo userInfo, String masterShubetu) 
		throws ApplicationException;
		
	
	
	/**
	 * マスタを取り込む。
	 * 取り込むマスタの種類は、第三引数で指定する。
	 * 指定できるマスタは本インタフェースで定義している「MASTER_XXXX」のみ。
	 * それ以外の場合は例外を発生する。
	 * @param userInfo
	 * @param fileRes
	 * @param masterShubetu
	 * @param shinkiKoshinFlg
	 * @return
	 * @throws ApplicationException
	 */
	public MasterKanriInfo torikomimaster(UserInfo userInfo,
										   FileResource fileRes,
										   final String masterShubetu,
										   final String shinkiKoshinFlg)
		throws ApplicationException;
	
	
	/**
	 * 削除されていない事業情報リストを取得する。
	 * @param userInfo
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public Page selectJigyoList(UserInfo userInfo, SearchInfo searchInfo) 
		throws ApplicationException;
		
	
	/**
	 * 指定事業データを削除する。
	 * 当該事業の事業情報管理、申請データ情報の削除フラグをオンにする。
	 * @param userInfo
	 * @param jigyoPk
	 * @return
	 * @throws ApplicationException
	 */
	public JigyoKanriInfo deleteJigyo(UserInfo userInfo, JigyoKanriPk jigyoPk) 
		throws ApplicationException;
		
	

	
	
	
	
}