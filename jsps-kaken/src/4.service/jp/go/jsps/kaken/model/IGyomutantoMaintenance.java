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
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * 業務担当者情報を管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IGyomutantoMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:49 $"
 */
public interface IGyomutantoMaintenance {

	/**
	 * 業務担当者情報を新規作成する。
	 * 発行ルールに基づきパスワードを設定する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param addInfo				作成するユーザ情報。
	 * @return						新規登録したユーザ情報
	 * @throws ApplicationException	
	 */
	public GyomutantoInfo insert(UserInfo userInfo,GyomutantoInfo addInfo) throws ApplicationException;

	/**
	 * 業務担当者情報を更新する。
	 * @param userInfo				実行するユーザ情報。
	 * @param updateInfo			更新するユーザ情報。
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,GyomutantoInfo updateInfo) throws ApplicationException;

	/**
	 * 業務担当者情報を削除する。
	 * @param userInfo				実行するユーザ情報。
	 * @param deleteInfo			削除するユーザ情報。
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,GyomutantoInfo deleteInfo) throws ApplicationException;

	/**
	 * 業務担当者情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				検索するユーザ情報。
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public GyomutantoInfo select(UserInfo userInfo,GyomutantoPk pkInfo) throws ApplicationException;


	/**
	 * 業務担当者情報を検索する。
	 * @param userInfo				実行するユーザ情報。
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo) throws ApplicationException;


	/**
	 * 登録または更新する業務担当者情報を形式チェックする。
	 * @param userInfo					実行するユーザ情報
	 * @param insertOrUpdateInfo		登録または新規作成する申請者情報
	 * @param mode						メンテナンスモード
	 * @return							形式チェック後のユーザ情報
	 * @throws ApplicationException	
	 * @throws ValidationException		検証にエラーがあった場合。
	 */
	public GyomutantoInfo validate(UserInfo userInfo,GyomutantoInfo insertOrUpdateInfo, String mode)
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
	public boolean changePassword(UserInfo userInfo, GyomutantoPk pkInfo,String oldPassword ,String newPassword)
		throws ApplicationException,ValidationException;		


}