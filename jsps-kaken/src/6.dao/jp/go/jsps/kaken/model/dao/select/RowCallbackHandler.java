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

import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;

/** 
 * ResultSetを処理するためのハンドラインターフェース。
 *
 * ID RCSfile="$RCSfile: RowCallbackHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 */
public interface RowCallbackHandler {

	/**
	 * ResultSetを処理するためのメソッド。
	 * resultSet.next()の間呼び出される。
	 * @param rs				ResultSet
	 * @throws SQLException		
	 */
	public void processRow(ResultSet rs) throws SQLException,NoDataFoundException;

}
