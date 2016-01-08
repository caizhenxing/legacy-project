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
 * ���[�U�̃��[�����`����N���X�B
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

	/** �\���� */
	public static UserRole SHINSEISHA = new UserRole("/shinsei");

	/** �����@�֒S���� */
	public static UserRole SHOZOKUTANTO = new UserRole("/shozoku");

	/** �R���� */
	public static UserRole SHINSAIN = new UserRole("/shinsa");

	/** �Ɩ��S���� */
	public static UserRole GYOMUTANTO = new UserRole("/gyomu");

	/** �V�X�e���Ǘ��� */
	public static UserRole SYSTEM = new UserRole("/system");
	
	
/* 2005/03/24 �ǉ� ��������---------------------------
 * ���R �u���ǒS���ҁv�ǉ� */
	
	/** ���ǒS���� */
	public static UserRole BUKYOKUTANTO = new UserRole("/bukyoku");
	
/* �ǉ� �����܂�-------------------------------------- */

	/** �\���� */
	public static UserRole QUESTION_SHINSEISHA = new UserRole("/question/oubosya");
	
	/** �����@�֒S���� */
	public static UserRole QUESTION_SHOZOKUTANTO = new UserRole("/question/kikan");
	
	/** �R���� */
	public static UserRole QUESTION_SHINSAIN = new UserRole("/question/shinsa");
	
	/** ���ǒS���� */
	public static UserRole QUESTION_BUKYOKUTANTO = new UserRole("/question/bukyoku");
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
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
