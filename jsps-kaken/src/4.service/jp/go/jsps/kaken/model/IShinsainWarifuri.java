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

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.model.vo.WarifuriInfo;
import jp.go.jsps.kaken.model.vo.WarifuriPk;
import jp.go.jsps.kaken.model.vo.WarifuriSearchInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * 割り振り結果情報を管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IShinsainWarifuri.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:49 $"
 */
public interface IShinsainWarifuri {
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** 審査員人数（基盤） */
	public static final int SHINSAIN_NINZU_KIBAN   = 12;
	
	/** 審査員人数（学創） */
	public static final int SHINSAIN_NINZU_GAKUSOU = 6;
		
	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	

	/**
	 * 割り振り結果情報を新規作成する。
	 * 発行ルールに基づきパスワードを設定する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param addInfo				作成するユーザ情報。
	 * @return						新規登録したユーザ情報
	 * @throws ApplicationException	
	 */
	public WarifuriInfo insert(UserInfo userInfo,WarifuriInfo addInfo) throws ApplicationException;

	/**
	 * 割り振り結果情報を更新する。
	 * @param userInfo				実行するユーザ情報。
	 * @param shinsaKekkaPk		更新するユーザ情報。
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,WarifuriPk WarifuriPk) throws ApplicationException;

	/**
	 * 割り振り結果情報を削除する。
	 * @param userInfo				実行するユーザ情報。
	 * @param shinsaKekkaPk		削除するユーザ情報。
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,ShinsaKekkaPk shinsaKekkaPk) throws ApplicationException;

	/**
	 * 割り振り結果情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				検索するユーザ情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public WarifuriInfo select(UserInfo userInfo, ShinsaKekkaPk infoPk) throws ApplicationException;


	/**
	 * 割り振り結果情報を検索する。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo,WarifuriSearchInfo searchInfo) throws ApplicationException;


	/**
	 * 登録または更新する割り振り結果情報を形式チェックする。
	 * @param userInfo					実行するユーザ情報
	 * @param insertOrUpdateInfo		登録または新規作成する割り振り結果情報
	 * @param mode						メンテナンスモード
	 * @return							形式チェック後のユーザ情報
	 * @throws ApplicationException	
	 * @throws ValidationException		検証にエラーがあった場合。
	 */
//	public WarifuriInfo validate(UserInfo userInfo,WarifuriInfo insertOrUpdateInfo, String mode)
//		throws ApplicationException, ValidationException;


	/**
	 * CSV出力用の申請者情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索するユーザ情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public FileResource createIraisho(UserInfo userInfo, WarifuriSearchInfo searchInfo) throws ApplicationException;


	/**
	 * 事業区分を取得する。
	 * 
	 * @param userInfo				実行するユーザ情報
	 * @param jigyoCd				事業コード
	 * @return						事業区分
	 * @throws ApplicationException
	 */
	public String selectJigyoKubun(UserInfo userInfo, String jigyoCd) throws ApplicationException;
	

}