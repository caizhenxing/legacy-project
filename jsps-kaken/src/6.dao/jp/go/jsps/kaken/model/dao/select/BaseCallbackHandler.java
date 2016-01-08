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
 * ResultSet���������邽�߂̃n���h���N���X�B
 *
 * ID RCSfile="$RCSfile: BaseCallbackHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 */
public class BaseCallbackHandler implements RowCallbackHandler {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �s�� */
	private int rowCount;

	/** �� */
	private int columnCount;

	/** ��^�C�v */
	private int[] columnTypes;
	
	/** �� */
	private String[] columnNames;

	//---------------------------------------------------------------------
	// Implementation of RowCallbackHandler interface
	//---------------------------------------------------------------------

	/* (�� Javadoc)
	 * @see dao.RowCallbackHandler#processRow(java.sql.ResultSet)
	 */
	public final void processRow(ResultSet rs) throws SQLException,NoDataFoundException {
		//���ڏ�񓙂̎擾
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
	 * �擾����ResultSet���ɌĂ΂��
	 * �T�u�N���X�ŃI�[�o���C�g����B
	 * @param rs			ResultSet
	 * @param rowNum		�s�ԍ�
	 * @throws SQLException
	 */
	protected void processRow(ResultSet rs, int rowNum) throws SQLException,NoDataFoundException {
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * SQL �^���擾����B
	 * @return	SQL�^���擾
	 */
	public final int[] getColumnTypes() {
		return columnTypes;
	}
	
	/**
	 * ���ږ����擾����B
	 * @return	���ږ�
	 */
	public final String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * �s�����擾����B
	 * @return	�s��
	 */
	public final int getRowCount() {
		return rowCount;
	}


	/**
	 * �񐔂��擾����B
	 * @return	�񐔁B
	 */
	public final int getColumnCount() {
		return columnCount;
	}

}
