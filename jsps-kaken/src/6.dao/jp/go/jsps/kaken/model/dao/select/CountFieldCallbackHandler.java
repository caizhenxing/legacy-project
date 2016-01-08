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
import java.util.HashMap;
import java.util.Map;


/**
 * 処理件数をリストで取得するハンドラクラス。（Count(*)等で使用する）
 *
 * ID RCSfile="$RCSfile: CountFieldCallbackHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 */
public class CountFieldCallbackHandler implements RowCallbackHandler {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** 行数 */
	private int rowCount;
	
	/** 件数*/
	private int count = 0;

	/** 処理結果リスト */
	private Map result = new HashMap();

	//---------------------------------------------------------------------
	// Implementation of RowCallbackHandler interface
	//---------------------------------------------------------------------
	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.dao.select.RowCallbackHandler#processRow(java.sql.ResultSet)
	 */
	public void processRow(ResultSet rs) throws SQLException {
		//項目情報等の取得
		if (rowCount == 0) {
			count = rs.getInt(1);
		}
	}
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * 対象件数の取得
	 * @return	処理件数
	 */
	public int getCount() {
		return count;
	}

}
