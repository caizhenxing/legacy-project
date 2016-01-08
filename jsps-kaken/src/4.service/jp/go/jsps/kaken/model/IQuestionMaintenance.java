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
 * アンケート情報の管理を行うインターフェース。
 * 
 */
public interface IQuestionMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** 戻り値Mapキー値：アンケート */
	public static final String KEY_QUESTION = "key_question";

	/** 警告プロパティ名 */
	public static final String WARNING = "WARNING";

	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	

	/**
	 * アンケートを登録する。
	 * @param userInfo
	 * @param questionInfo
	 * @throws ApplicationException
	 */
	public void insert(UserInfo userInfo, QuestionInfo questionInfo)
		throws ApplicationException;		
	
}