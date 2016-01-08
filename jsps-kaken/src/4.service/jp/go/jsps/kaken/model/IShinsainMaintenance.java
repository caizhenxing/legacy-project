/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
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
 * 申請者情報を管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IShinsainMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IShinsainMaintenance {

	/**
	 * 申請者情報を新規作成する。
	 * 発行ルールに基づきパスワードを設定する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param addInfo				作成するユーザ情報。
	 * @return						新規登録したユーザ情報
	 * @throws ApplicationException	
	 */
	public ShinsainInfo insert(UserInfo userInfo, ShinsainInfo addInfo) throws ApplicationException;

	/**
	 * 申請者情報を更新する。
	 * @param userInfo				実行するユーザ情報。
	 * @param updateInfo			更新するユーザ情報。
	 * @throws ApplicationException
	 */
	public ShinsainInfo update(UserInfo userInfo, ShinsainInfo updateInfo) throws ApplicationException;

	/**
	 * 申請者情報を削除する。
	 * @param userInfo				実行するユーザ情報。
	 * @param deleteInfo			削除するユーザ情報。
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo, ShinsainInfo deleteInfo) throws ApplicationException, ValidationException;

	/**
	 * 申請者情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				検索するユーザ情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public ShinsainInfo select(UserInfo userInfo, ShinsainPk pkInfo) throws ApplicationException;


	/**
	 * 申請者情報を検索する。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo, ShinsainSearchInfo searchInfo) throws ApplicationException;


//	/**
//	 * 審査員情報(分科細目別)を検索する。
//	 * @param userInfo				実行するユーザ情報。
//	 * @param searchInfo			検索情報
//	 * @return						検索結果ページ情報
//	 * @throws ApplicationException	
//	 */
//	public Page searchRank(UserInfo userInfo, ShinsainSearchInfo searchInfo) throws ApplicationException;


//	/**
//	 * 分科細目名を付加する。
//	 * @param userInfo				実行するユーザ情報。
//	 * @param shinsainInfo			審査員情報
//	 * @return						審査員情報
//	 * @throws ApplicationException	
//	 */
//	public ShinsainInfo addSaimoku(UserInfo userInfo, ShinsainInfo shinsainInfo) throws ApplicationException;


	/**
	 * CSV出力用の申請者情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索するユーザ情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public List searchCsvData(UserInfo userInfo, ShinsainSearchInfo searchInfo) throws ApplicationException;


	/**
	 * 登録または更新する申請者情報を形式チェックする。
	 * @param userInfo					実行するユーザ情報
	 * @param insertOrUpdateInfo		登録または新規作成する申請者情報
	 * @param mode						メンテナンスモード
	 * @return							形式チェック後のユーザ情報
	 * @throws ApplicationException	
	 * @throws ValidationException		検証にエラーがあった場合。
	 */
	public ShinsainInfo validate(UserInfo userInfo, ShinsainInfo info, String mode)
		throws ApplicationException, ValidationException;

	/**
	 * パスワードを変更する。
	 * @param userInfo					実行するユーザ情報
	 * @param pkInfo					パスワードを更新する検索するユーザ主キー情報。
	 * @param oldPassword				更新前パスワード
	 * @param newPassword				新しいパスワード
	 * @return							パスワードの変更に成功した場合 true 以外 false
	 * @throws ApplicationException	
	 * @throws ValidationException		更新前パスワードが一致しない場合等、検証にエラーがあった場合。
	 */
	public boolean changePassword(UserInfo userInfo, ShinsainPk pkInfo, String oldPassword, String newPassword)
		throws ApplicationException,ValidationException;		


	/**
	 * 審査員のパスワードを再設定する。
	 * 発行ルールに基づきパスワードを設定する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				パスワードを再設定する検索するユーザ主キー情報。
	 * @return						パスワードを再設定したユーザ情報
	 * @throws ApplicationException	
	 */
	public ShinsainInfo reconfigurePassword(UserInfo userInfo,ShinsainPk pkInfo) throws ApplicationException;
	
	
	
	/**
	 * 審査依頼書を発行する。（審査員管理用）
	 * @param userInfo				実行するユーザ情報
	 * @param searchInfo			検索条件
	 * @return						審査依頼書圧縮ファイルのFileResource
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public FileResource createIraisho(UserInfo userInfo, ShinsainSearchInfo searchInfo) throws ApplicationException;	
	
	
	
}