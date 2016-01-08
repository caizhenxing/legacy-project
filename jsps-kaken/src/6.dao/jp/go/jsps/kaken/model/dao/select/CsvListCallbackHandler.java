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
import java.util.List;


/**
 * CSV出力するリストを取得するハンドラクラス。
 *
 * ID RCSfile="$RCSfile: CsvListCallbackHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 */
public class CsvListCallbackHandler extends BaseCallbackHandler {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 処理結果リスト */
	private List result = new ArrayList();

	/** ヘッダー情報を含むかどうか*/
	private boolean includeHeader = false;

	//---------------------------------------------------------------------
	// Implementation of RowCallbackHandler interface
	//---------------------------------------------------------------------


	/* (非 Javadoc)
	 * @see dao.BaseCallbackHandler#processRow(java.sql.ResultSet, int)
	 */
	protected void processRow(ResultSet rs, int rowNum) throws SQLException {

		//ヘッダー情報の取得---------------
		if (rowNum == 0 && includeHeader) {
			List aHeader = new ArrayList(getColumnNames().length);
			for (int i = 0; i < getColumnNames().length; i++) {
				String columnName = getColumnNames()[i];
				aHeader.add(columnName);
			}
			result.add(aHeader);
		}
		
		//データの取得--------------------
		List aRecord = new ArrayList(getColumnNames().length);
		for (int i = 0; i < getColumnNames().length; i++) {
			String columnName = getColumnNames()[i];
			String columnValue = rs.getString(columnName);
			if (columnValue != null) {
				aRecord.add(columnValue);
			}else{
				aRecord.add("");
			}
		}
		result.add(aRecord);
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

	/**
	 * CSVリストにヘッダー行を含めるかどうかを取得する。
	 * @return	ヘッダー行を含める場合 true
	 */
	public boolean isIncludeHeader() {
		return includeHeader;
	}

	/**
	 * CSVリストにヘッダー行を含めるかどうかを設定する。
	 * @param b	ヘッダー行を含める場合 true
	 */
	public void setIncludeHeader(boolean b) {
		includeHeader = b;
	}

}
