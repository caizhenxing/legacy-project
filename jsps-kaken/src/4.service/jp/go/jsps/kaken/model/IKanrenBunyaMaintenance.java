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
 * 関連分野情報を管理を行うインターフェース。
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
	 * 関連分野情報を検索する。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo, ShinseiSearchInfo searchInfo) throws ApplicationException;
	
	
	
	
	/**
	 * 関連分野情報CSVリストを返す。
	 * @param userInfo
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public List kanrenSearchCsvData(UserInfo userInfo,ShinseiSearchInfo searchInfo )
		throws ApplicationException;	
	
	
	
	
	
	
	
	
	

}