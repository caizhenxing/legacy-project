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
import jp.go.jsps.kaken.model.vo.RuleInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.UserInfo;
/**
 * 各ID・パスワードの発行ルール管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IRuleMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IRuleMaintenance {

	/**
	 * 発行ルール情報を新規作成する。
	 * 発行ルールに基づきパスワードを設定する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param addInfo				作成する発行ルール情報。
	 * @return						新規登録した発行ルール情報
	 * @throws ApplicationException	
	 */
	public RuleInfo insert(UserInfo userInfo,RuleInfo addInfo) throws ApplicationException;

	/**
	 * 発行ルール情報を更新する。
	 * @param userInfo				実行するユーザ情報。
	 * @param updateInfo			更新する発行ルール情報。
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,RuleInfo updateInfo) throws ApplicationException;

	/**
	 * 発行ルール情報を更新する。
	 * @param userInfo				実行するユーザ情報。
	 * @param updateList			更新する発行ルール情報。
	 * @throws ApplicationException
	 */
	public void updateAll(UserInfo userInfo,List updateList) throws ApplicationException;

	/**
	 * 発行ルール情報を削除する。
	 * @param userInfo				実行するユーザ情報。
	 * @param deleteInfo			削除する発行ルール情報。
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,RuleInfo deleteInfo) throws ApplicationException;

	/**
	 * 発行ルール情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				検索する発行ルール情報。
	 * @return						取得した発行ルール情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public RuleInfo select(UserInfo userInfo,RulePk pkInfo) throws ApplicationException;

	/**
	 * 発行ルール情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @return						取得した発行ルール情報。
	 * @throws ApplicationException
	 */
	public List selectList(UserInfo userInfo) throws ApplicationException;

	/**
	 * 登録または更新する発行ルール情報を形式チェックする。
	 * @param userInfo					実行するユーザ情報
	 * @param insertOrUpdateInfo		登録または新規作成する発行ルール情報
	 * @return							形式チェック後の発行ルール情報
	 * @throws ApplicationException	
	 * @throws ValidationException		検証にエラーがあった場合。
	 */
	public RuleInfo validate(UserInfo userInfo,RuleInfo insertOrUpdateInfo)
		throws ApplicationException, ValidationException;

}
