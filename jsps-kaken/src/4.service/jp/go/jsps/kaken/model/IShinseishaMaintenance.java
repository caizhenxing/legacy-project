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

import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaPk;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * 申請者情報を管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IShinseishaMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IShinseishaMaintenance {

	/**
	 * 申請者情報を新規作成する。
	 * 発行ルールに基づきパスワードを設定する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param addInfo				作成するユーザ情報。
	 * @return						新規登録したユーザ情報
	 * @throws ApplicationException	
	 */
	public ShinseishaInfo insert(UserInfo userInfo,ShinseishaInfo addInfo) throws ApplicationException;

	/**
	 * 申請者情報を更新する。
	 * @param userInfo				実行するユーザ情報。
	 * @param updateInfo			更新するユーザ情報。
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,ShinseishaInfo updateInfo) throws ApplicationException;

	/**
	 * 申請者情報を削除する。
	 * @param userInfo				実行するユーザ情報。
	 * @param deleteInfo			削除するユーザ情報。
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,ShinseishaInfo deleteInfo) throws ApplicationException;

	/**
	 * 申請者情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				検索するユーザ情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public ShinseishaInfo select(UserInfo userInfo,ShinseishaPk pkInfo) throws ApplicationException;


	/**
	 * 申請者情報を検索する。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo,ShinseishaSearchInfo searchInfo) throws ApplicationException;
	
	/**
	 * CSV出力用の申請者情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索するユーザ情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public List searchCsvData(UserInfo userInfo,ShinseishaSearchInfo searchInfo) throws ApplicationException;


	/**
	 * 登録または更新する申請者情報を形式チェックする。
	 * @param userInfo					実行するユーザ情報
	 * @param insertOrUpdateInfo		登録または新規作成する申請者情報
	 * @param mode						メンテナンスモード
	 * @return							形式チェック後のユーザ情報
	 * @throws ApplicationException	
	 * @throws ValidationException		検証にエラーがあった場合。
	 */
	public ShinseishaInfo validate(UserInfo userInfo,ShinseishaInfo insertOrUpdateInfo, String mode)
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
	public boolean changePassword(UserInfo userInfo, ShinseishaPk pkInfo,String oldPassword ,String newPassword)
		throws ApplicationException,ValidationException;		

	/**
	 * 申請者のパスワードを再設定する。
	 * 発行ルールに基づきパスワードを設定する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				パスワードを再設定する検索するユーザ主キー情報。
	 * @return						パスワードを再設定したユーザ情報
	 * @throws ApplicationException	
	 */
	public ShinseishaInfo reconfigurePassword(UserInfo userInfo,ShinseishaPk pkInfo) throws ApplicationException;
	

	/**
	 * 非公募応募可フラグを外す。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			非応募申請可フラグを外すユーザの情報。
	 * @throws ApplicationException	
	 */
	public void deleteHikoboFlgInfo(UserInfo userInfo, ShinseishaSearchInfo searchInfo) throws ApplicationException;
	
	//2005/04/06　追加ここから　パスワード一括再設定メソッド追加のため
	
	/**
	 * パスワードを一括再設定する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				パスワードを再設定する検索するユーザ主キー情報。
	 * @param array				申請者IDの配列						
	 * @throws ApplicationException	
	 */
	public void reconfigurePasswordAll(UserInfo userInfo, ShinseishaPk pkInfo, ArrayList array) throws ApplicationException;
	
	// 追加ここまで
}