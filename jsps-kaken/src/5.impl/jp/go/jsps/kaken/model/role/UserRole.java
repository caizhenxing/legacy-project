/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/24
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.role;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enum.Enum;

/**
 * ユーザのロールを定義するクラス。
 * 
 * ID RCSfile="$RCSfile: UserRole.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:08:03 $"
 */
public class UserRole extends Enum {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	static final long serialVersionUID = -7243511528766358084L;

	/** 申請者 */
	public static UserRole SHINSEISHA = new UserRole("/shinsei");

	/** 所属機関担当者 */
	public static UserRole SHOZOKUTANTO = new UserRole("/shozoku");

	/** 審査員 */
	public static UserRole SHINSAIN = new UserRole("/shinsa");

	/** 業務担当者 */
	public static UserRole GYOMUTANTO = new UserRole("/gyomu");

	/** システム管理者 */
	public static UserRole SYSTEM = new UserRole("/system");
	
	
/* 2005/03/24 追加 ここから---------------------------
 * 理由 「部局担当者」追加 */
	
	/** 部局担当者 */
	public static UserRole BUKYOKUTANTO = new UserRole("/bukyoku");
	
/* 追加 ここまで-------------------------------------- */

	/** 申請者 */
	public static UserRole QUESTION_SHINSEISHA = new UserRole("/question/oubosya");
	
	/** 所属機関担当者 */
	public static UserRole QUESTION_SHOZOKUTANTO = new UserRole("/question/kikan");
	
	/** 審査員 */
	public static UserRole QUESTION_SHINSAIN = new UserRole("/question/shinsa");
	
	/** 部局担当者 */
	public static UserRole QUESTION_BUKYOKUTANTO = new UserRole("/question/bukyoku");
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 * @param arg0
	 */
	private UserRole(String arg0) {
		super(arg0);
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	public static UserRole getEnum(String rolename) {
	  return (UserRole) getEnum(UserRole.class, rolename);
	}
 
	public static Map getEnumMap() {
	  return getEnumMap(UserRole.class);
	}
 
	public static List getEnumList() {
	  return getEnumList(UserRole.class);
	}
 
	public static Iterator iterator() {
	  return iterator(UserRole.class);
	}

}
