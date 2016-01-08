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
import jp.go.jsps.kaken.model.vo.ShinsaJokyoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * 審査状況情報を管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IShinsaJokyoKakunin.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:49 $"
 */
public interface IShinsaJokyoKakunin {

	/**
	 * 審査状況情報を新規作成する。
	 * 発行ルールに基づきパスワードを設定する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param addInfo				作成するユーザ情報。
	 * @return						新規登録したユーザ情報。
	 * @throws ApplicationException	
	 */
	public ShinsaJokyoInfo insert(UserInfo userInfo,ShinsaJokyoInfo addInfo) throws ApplicationException;

	/**
	 * 審査状況情報を更新する。
	 * @param userInfo				実行するユーザ情報。
	 * @param updateInfo			更新するユーザ情報。
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,ShinsaKekkaInfo updateInfo) throws ApplicationException;

	/**
	 * 審査状況情報を削除する。
	 * @param userInfo				実行するユーザ情報。
	 * @param deleteInfo			削除するユーザ情報。
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,ShinsaJokyoInfo deleteInfo) throws ApplicationException;


	/**
	 * 審査状況情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @return						取得したユーザ情報。
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public List searchCsvData(UserInfo userInfo,ShinsaJokyoSearchInfo searchInfo) throws ApplicationException;

	/**
	 * 審査状況情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				検索するユーザ情報。
	 * @return						取得したユーザ情報。
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public ShinsaJokyoInfo select(UserInfo userInfo,ShinsaJokyoSearchInfo searchInfo) throws ApplicationException;


	/**
	 * 審査状況情報を検索する。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @return						検索結果ページ情報。
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo,ShinsaJokyoSearchInfo searchInfo) throws ApplicationException;


	/**
	 * 選択情報の審査状況を未完了とし、審査状況情報を検索する。
	 * @param userInfo				実行するユーザ情報。
	 * @param shinsaJokyoInfo		審査状況情報。
	 * @throws ApplicationException	
	 * @throws ValidationException	
	 */
	public void saishinsa(UserInfo userInfo, ShinsaJokyoInfo shinsaJokyoInfo) throws ApplicationException, ValidationException;

//2006/10/25 jinbaogang add start
	/**
	 * 利益相反再入力完了。
	 * @param userInfo				実行するユーザ情報。
	 * @param shinsaJokyoInfo		利益相反再入力情報。
	 * @throws ApplicationException	
	 * @throws ValidationException	
	 */
	public void updateSaiNyuryoku(UserInfo userInfo, ShinsaJokyoInfo shinsaJokyoInfo) throws ApplicationException, ValidationException;
//  2006/10/25 jinbaogang add end
    
	/**
	 * 登録または更新する審査状況情報を形式チェックする。
	 * @param userInfo					実行するユーザ情報。
	 * @param insertOrUpdateInfo		登録または新規作成する審査状況情報。
	 * @param mode						メンテナンスモード。
	 * @return							形式チェック後のユーザ情報。
	 * @throws ApplicationException	
	 * @throws ValidationException		検証にエラーがあった場合。
	 */
	public ShinsaJokyoInfo validate(UserInfo userInfo,ShinsaJokyoInfo insertOrUpdateInfo, String mode)
		throws ApplicationException, ValidationException;


	/**
	 * 当該審査員が担当する当該事業IDの審査結果をクリアする。
	 * 当該審査員が存在していなくてもクリアする。ただし、[審査未完了]のものだけ。
	 * @param userInfo
	 * @param shinsaJokyoInfo
	 * @throws ApplicationException
	 */
	public void clearShinsaKekka(UserInfo userInfo, ShinsaJokyoInfo shinsaJokyoInfo)
			throws ApplicationException;
	
	
	
	
	
	
	
	
}