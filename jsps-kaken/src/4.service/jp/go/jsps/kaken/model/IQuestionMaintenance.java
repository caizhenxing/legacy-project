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
import jp.go.jsps.kaken.model.vo.QuestionInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;



/**
 * �A���P�[�g���̊Ǘ����s���C���^�[�t�F�[�X�B
 * 
 */
public interface IQuestionMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** �߂�lMap�L�[�l�F�A���P�[�g */
	public static final String KEY_QUESTION = "key_question";

	/** �x���v���p�e�B�� */
	public static final String WARNING = "WARNING";

	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	

	/**
	 * �A���P�[�g��o�^����B
	 * @param userInfo
	 * @param questionInfo
	 * @throws ApplicationException
	 */
	public void insert(UserInfo userInfo, QuestionInfo questionInfo)
		throws ApplicationException;		
	
}