/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.InvalidLogonException;
import jp.go.jsps.kaken.model.vo.UserInfo;

/**
 * ユーザ認証を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IAuthentication.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IAuthentication {

	/**
	 * ユーザ認証を行う。
	 * @param userid		ユーザID
	 * @param password		パスワード
	 * @return								ログインしたユーザ情報
	 * @throws ApplicationException			認証時に認証以外の一般的なエラーが発生したとき。
	 * @throws InvalidLogonException		認証に失敗した場合。
	 */
	public UserInfo authenticate(String userid, String password) throws InvalidLogonException,ApplicationException;
 
}
