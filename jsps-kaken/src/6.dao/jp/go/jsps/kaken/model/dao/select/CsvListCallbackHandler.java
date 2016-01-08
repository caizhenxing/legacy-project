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
 * CSV�o�͂��郊�X�g���擾����n���h���N���X�B
 *
 * ID RCSfile="$RCSfile: CsvListCallbackHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 */
public class CsvListCallbackHandler extends BaseCallbackHandler {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �������ʃ��X�g */
	private List result = new ArrayList();

	/** �w�b�_�[�����܂ނ��ǂ���*/
	private boolean includeHeader = false;

	//---------------------------------------------------------------------
	// Implementation of RowCallbackHandler interface
	//---------------------------------------------------------------------


	/* (�� Javadoc)
	 * @see dao.BaseCallbackHandler#processRow(java.sql.ResultSet, int)
	 */
	protected void processRow(ResultSet rs, int rowNum) throws SQLException {

		//�w�b�_�[���̎擾---------------
		if (rowNum == 0 && includeHeader) {
			List aHeader = new ArrayList(getColumnNames().length);
			for (int i = 0; i < getColumnNames().length; i++) {
				String columnName = getColumnNames()[i];
				aHeader.add(columnName);
			}
			result.add(aHeader);
		}
		
		//�f�[�^�̎擾--------------------
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
	 * �s���̃}�b�v��ێ����鏈�����ʃ��X�g���擾����B
	 * @return	�������ʃ��X�g
	 */
	public List getResult() {
		return result;
	}

	/**
	 * CSV���X�g�Ƀw�b�_�[�s���܂߂邩�ǂ������擾����B
	 * @return	�w�b�_�[�s���܂߂�ꍇ true
	 */
	public boolean isIncludeHeader() {
		return includeHeader;
	}

	/**
	 * CSV���X�g�Ƀw�b�_�[�s���܂߂邩�ǂ�����ݒ肷��B
	 * @param b	�w�b�_�[�s���܂߂�ꍇ true
	 */
	public void setIncludeHeader(boolean b) {
		includeHeader = b;
	}

}
