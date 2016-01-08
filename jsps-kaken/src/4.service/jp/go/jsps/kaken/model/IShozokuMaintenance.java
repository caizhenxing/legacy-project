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

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.KikanInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.model.vo.ShozokuSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * 所属機関情報を管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IShozokuMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:49 $"
 */
public interface IShozokuMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** その他の所属機関コード */
	public static final String OTHER_KIKAN_CODE = "99999";

	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	

	/**
	 * 所属機関情報を新規作成する。
	 * 発行ルールに基づきパスワードを設定する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param addInfo				作成するユーザ情報。
	 * @return						新規登録したユーザ情報
	 * @throws ApplicationException	
	 */
	public ShozokuInfo insert(UserInfo userInfo,ShozokuInfo addInfo) throws ApplicationException;

	/**
	 * 所属機関情報を更新する。
	 * @param userInfo				実行するユーザ情報。
	 * @param updateInfo			更新するユーザ情報。
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,ShozokuInfo updateInfo) throws ApplicationException;

	/**
	 * 所属機関情報を削除する。
	 * @param userInfo				実行するユーザ情報。
	 * @param deleteInfo			削除するユーザ情報。
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,ShozokuInfo deleteInfo) throws ApplicationException;

	/**
	 * 所属機関情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param keyInfo				検索するユーザ情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public ShozokuInfo select(UserInfo userInfo,ShozokuPk pkInfo) throws ApplicationException;
	
	/**
	 * 所属機関情報を所属機関マスタから検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param kikanInfo			検索情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public KikanInfo select(UserInfo userInfo, KikanInfo kikanInfo) throws ApplicationException;
	
	/**
	 * 所属機関コードをキーとして所属機関担当者を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param shozokuCd			検索情報。所属機関コード。
	 * @return						所属機関担当者の数。
	 * @throws ApplicationException
	 */
	public int select(UserInfo userInfo, String shozokuCd) throws ApplicationException;

	/**
	 * 所属機関コードをキーとして所属機関担当者情報を検索し、所属機関担当者IDの昇順のリストを返す。
	 * 所属機関コードがnullまたは""の場合は、全件検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param shozokuCd			検索情報。所属機関コード。
	 * @return						所属機関情報。
	 * @throws ApplicationException
	 */
	public List searchShozokuInfo(UserInfo userInfo, String shozokuCd) throws ApplicationException;

	/**
	 * 所属機関情報を検索する。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo,ShozokuSearchInfo searchInfo) throws ApplicationException;

	/**
	 * CSV出力用の所属機関情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索するユーザ情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public List searchCsvData(UserInfo userInfo,ShozokuSearchInfo searchInfo) throws ApplicationException;

	/**
	 * CSV出力用の所属機関情報を検索する。(システム管理者用)
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索するユーザ情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public List searchCsvDataForSysMng(UserInfo userInfo,ShozokuSearchInfo searchInfo) throws ApplicationException;

	/**
	 * 登録または更新する所属機関情報を形式チェックする。
	 * 
	 * @param userInfo					実行するユーザ情報
	 * @param insertOrUpdateInfo		登録または新規作成する所属機関情報
	 * @return							形式チェック後の所属機関情報
	 * @throws ApplicationException	
	 * @throws ValidationException		検証にエラーがあった場合。
	 */
	public ShozokuInfo validate(UserInfo userInfo,ShozokuInfo insertOrUpdateInfo)
		throws ApplicationException, ValidationException;


	/**
	 * パスワードを変更する。
	 * 
	 * @param userInfo					実行するユーザ情報
	 * @param pkInfo					パスワードを更新する検索するユーザ主キー情報。
	 * @param oldPassword				更新前パスワード
	 * @param newPassword				新しいパスワード
	 * @return							パスワードの変更に成功した場合 true 以外 false
	 * @throws ApplicationException	
	 * @throws ValidationException		更新前パスワードが一致しない場合等、検証にエラーがあった場合。
	 */
	public boolean changePassword(UserInfo userInfo, ShozokuPk pkInfo,String oldPassword ,String newPassword)
		throws ApplicationException,ValidationException;
	
//	2005/04/20 追加 ここから----------------------------------------
//	理由 システム管理者向け機能の所属機関情報検索用
	/**
	 * 所属機関情報を検索する。
	 * 
	 * @param userInfo					実行するユーザ情報
	 * @param searchInfo				検索条件
	 * @return							検索結果ページ情報
	 * @throws ApplicationException
	 */
	public Page searchShozokuTantoList(UserInfo userInfo, ShozokuSearchInfo searchInfo)
		throws ApplicationException;
//	追加 ここまで-----------------------------------------------------

//	2005/04/21 追加 ここから----------------------------------------
//	理由 パスワード再設定処理。
	/**
	 * 
	 * @param userInfo					実行するユーザ情報
	 * @param pkInfo					キー情報
	 * @return							検索結果ページ情報
	 * @throws ApplicationException
	 */
	public ShozokuInfo reconfigurePassword(UserInfo userInfo, ShozokuPk pkInfo)
		throws ApplicationException;
//	追加 ここまで-----------------------------------------------------

	/**
	 * 承認確認メールを送信する。
	 * 学振締切日の３日前の0:00に申請期限になる事業に対して、
	 * 申請者が所属する所属担当者に向けてメールを送信する。
	 * 当該申請期限の事業が存在しない場合は何も処理しない。
	 * @param userInfo
	 * @throws ApplicationException
	 */
	public void sendMailShoninTsuchi(UserInfo userInfo)
		throws ApplicationException;

}