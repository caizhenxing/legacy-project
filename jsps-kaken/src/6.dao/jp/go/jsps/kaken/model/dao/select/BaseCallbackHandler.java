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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;

/**
 * ResultSetを処理するためのハンドラクラス。
 *
 * ID RCSfile="$RCSfile: BaseCallbackHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 */
public class BaseCallbackHandler implements RowCallbackHandler {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 行数 */
	private int rowCount;

	/** 列数 */
	private int columnCount;

	/** 列タイプ */
	private int[] columnTypes;
	
	/** 列名 */
	private String[] columnNames;

	//---------------------------------------------------------------------
	// Implementation of RowCallbackHandler interface
	//---------------------------------------------------------------------

	/* (非 Javadoc)
	 * @see dao.RowCallbackHandler#processRow(java.sql.ResultSet)
	 */
	public final void processRow(ResultSet rs) throws SQLException,NoDataFoundException {
		//項目情報等の取得
		if (rowCount == 0) {
			ResultSetMetaData rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();
			columnTypes = new int[columnCount];
			columnNames = new String[columnCount];
			for (int i = 1, j = 0; j < columnCount; i++, j++) {
				columnTypes[j] = rsmd.getColumnType(i);
				columnNames[j] = rsmd.getColumnName(i);
			}
		}
		processRow(rs, rowCount++);
	}

	/**
	 * 取得したResultSet毎に呼ばれる
	 * サブクラスでオーバライトする。
	 * @param rs			ResultSet
	 * @param rowNum		行番号
	 * @throws SQLException
	 */
	protected void processRow(ResultSet rs, int rowNum) throws SQLException,NoDataFoundException {
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * SQL 型を取得する。
	 * @return	SQL型を取得
	 */
	public final int[] getColumnTypes() {
		return columnTypes;
	}
	
	/**
	 * 項目名を取得する。
	 * @return	項目名
	 */
	public final String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * 行数を取得する。
	 * @return	行数
	 */
	public final int getRowCount() {
		return rowCount;
	}


	/**
	 * 列数を取得する。
	 * @return	列数。
	 */
	public final int getColumnCount() {
		return columnCount;
	}

}
