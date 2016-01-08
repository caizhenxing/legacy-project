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

import java.util.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

/**
 * �֘A��������Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IKanrenBunyaMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:49 $"
 */
public interface IKanrenBunyaMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	

	
	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
	
	/**
	 * �֘A���������������B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo, ShinseiSearchInfo searchInfo) throws ApplicationException;
	
	
	
	
	/**
	 * �֘A������CSV���X�g��Ԃ��B
	 * @param userInfo
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public List kanrenSearchCsvData(UserInfo userInfo,ShinseiSearchInfo searchInfo )
		throws ApplicationException;	
	
	
	
	
	
	
	
	
	

}