/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.select;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;


/**
 * 処理結果をリストで取得するハンドラクラス。
 *
 * ID RCSfile="$RCSfile: ListCallbackHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 */
public class ListCallbackHandler extends BaseCallbackHandler {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 処理結果リスト */
	private List result = new ArrayList();

	//---------------------------------------------------------------------
	// Implementation of RowCallbackHandler interface
	//---------------------------------------------------------------------


	/* (非 Javadoc)
	 * @see dao.BaseCallbackHandler#processRow(java.sql.ResultSet, int)
	 */
	protected void processRow(ResultSet rs, int rowNum) throws SQLException,NoDataFoundException {
		HashMap map = new HashMap();
		for (int i = 0; i < getColumnNames().length; i++) {
			String columnName = getColumnNames()[i];
			Object obj = rs.getObject(columnName);
			if (obj != null) {
				map.put(columnName, obj);
			}
		}
		result.add(map);
	}
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	
	/**
	 * 行情報のマップを保持する処理結果リストを取得する。
	 * @return	処理結果リスト
	 */
	public List getResult() {
		return result;
	}

}
