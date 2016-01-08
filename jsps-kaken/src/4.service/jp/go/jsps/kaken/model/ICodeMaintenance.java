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

import java.util.Map;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.UserInfo;

/**
 * コード表情報の管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: ICodeMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface ICodeMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** 戻り値Mapキー値：索引用リスト */
	public static final String KEY_INDEX_LIST = "key_index_list";
		
	/** 戻り値Mapキー値：検索用リスト */
	public static final String KEY_SEARCH_LIST = "key_search_list";	
	
	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
	
	/**
	 * 所属機関マスタから所属機関種別コードごとの所属機関情報の一覧を取得する。
	 * 
	 * @param userInfo				実行するユーザ情報
	 * @param shubetuCd			所属機関種別コード
	 * @return						取得したユーザ情報
	 * @throws ApplicationException
	 */
//	public List getKikanInfoList(UserInfo userInfo, String shubetuCd) throws ApplicationException;

	/**
	 * 所属機関マスタから所属機関情報の一覧を取得する。
	 * すべての所属機関種別コードの所属機関情報をリストで返す。
	 * 
	 * @param userInfo				実行するユーザ情報
	 * @param searchInfo			検索するユーザ情報
	 * @return						取得したユーザ情報
	 * @throws ApplicationException
	 */
	public Map getKikanInfoList(UserInfo userInfo) throws ApplicationException;
	
	/**
	 * 所属機関種別マスタから所属機関種別情報の一覧を取得する。
	 *
	 * @return						取得したユーザ情報
	 * @throws ApplicationException
	 */
//	public List getKikanShubetuInfoList(UserInfo userInfo) throws ApplicationException;

	/**
	 * 分科細目マスタから部名の一覧を取得する。
	 * 
	 * @param userInfo				実行するユーザ情報
	 * @return						取得したユーザ情報
	 * @throws ApplicationException
	 */
//	public List getBuNameList(UserInfo userInfo) throws ApplicationException;

	/**
	 * 分科細目マスタから分科名の一覧を取得する。
	 * 
	 * @param userInfo				実行するユーザ情報
	 * @return						取得したユーザ情報
	 * @throws ApplicationException
	 */
//	public List getBunkaNameList(UserInfo userInfo, String buName) throws ApplicationException;
	
	/**
	 * 分科細目マスタから分科名ごとの分科細目情報の一覧を取得する。
	 * 
	 * @param userInfo				実行するユーザ情報
	 * @param bunkaName			分科名
	 * @return						取得したユーザ情報
	 * @throws ApplicationException
	 */
//	public List getSaimokuInfoList(UserInfo userInfo, String bunkaName) throws ApplicationException;

	/**
	 * 分科細目マスタから分科細目情報の一覧を取得する。
	 * すべての所属機関種別コードの所属機関情報をリストで返す。
	 * 
	 * @param userInfo				実行するユーザ情報
	 * @param searchInfo			検索するユーザ情報
	 * @return						取得したユーザ情報
	 * @throws ApplicationException
	 */
	public Map getSaimokuInfoList(UserInfo userInfo) throws ApplicationException;

	
	/**
	 * 索引ごとの部局情報の一覧を取得する。
	 * @param userInfo				実行するユーザ情報
	 * @param index				索引
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
//	public List getBukyokuInfoList(UserInfo userInfo,String index) throws ApplicationException;
	
	/**
	 * 部局マスタから部局情報の一覧を取得する。
	 * すべての部局コードの部局情報をリストで返す。
	 * 
	 * @param userInfo				実行するユーザ情報
	 * @param searchInfo			検索するユーザ情報
	 * @return						取得したユーザ情報
	 * @throws ApplicationException
	 */
	public Map getBukyokuInfoList(UserInfo userInfo) throws ApplicationException;

	/**
	 * 部局マスタから索引の一覧を取得する。
	 *
	 * @return						取得したユーザ情報
	 * @throws ApplicationException
	 */
//	public List getSakuinList(UserInfo userInfo) throws ApplicationException;

	/**
	 * キーワード一覧表示情報を取得する
	 * @param userInfo
	 * @return　キーワード一覧表示情報
	 * @throws ApplicationException
	 */
	public Map getKeywordInfoList(UserInfo userInfo) throws ApplicationException ;

	/**
	 * 領域マスタ一覧情報取得
	 * @param userInfo
	 * @return
	 * @throws ApplicationException
	 */
	public Map getRyoikiInfoList(UserInfo userInfo) throws ApplicationException;
    
//2006/07/24　苗　追加ここから
    /**
     * 領域マスタ（新規領域）一覧情報取得
     * @param userInfo
     * @return
     * @throws ApplicationException
     */
    public Map getRyoikiSinnkiInfoList(UserInfo userInfo) throws ApplicationException;
//2006/07/24　苗　追加ここまで    
	
}