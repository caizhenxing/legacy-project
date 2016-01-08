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
 * ���[�U�F�؂��s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IAuthentication.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IAuthentication {

	/**
	 * ���[�U�F�؂��s���B
	 * @param userid		���[�UID
	 * @param password		�p�X���[�h
	 * @return								���O�C���������[�U���
	 * @throws ApplicationException			�F�؎��ɔF�؈ȊO�̈�ʓI�ȃG���[�����������Ƃ��B
	 * @throws InvalidLogonException		�F�؂Ɏ��s�����ꍇ�B
	 */
	public UserInfo authenticate(String userid, String password) throws InvalidLogonException,ApplicationException;
 
}
