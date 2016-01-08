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
 * �������������X�g�Ŏ擾����n���h���N���X�B�iCount(*)���Ŏg�p����j
 *
 * ID RCSfile="$RCSfile: CountFieldCallbackHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 */
public class CountFieldCallbackHandler implements RowCallbackHandler {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** �s�� */
	private int rowCount;
	
	/** ����*/
	private int count = 0;

	/** �������ʃ��X�g */
	private Map result = new HashMap();

	//---------------------------------------------------------------------
	// Implementation of RowCallbackHandler interface
	//---------------------------------------------------------------------
	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.dao.select.RowCallbackHandler#processRow(java.sql.ResultSet)
	 */
	public void processRow(ResultSet rs) throws SQLException {
		//���ڏ�񓙂̎擾
		if (rowCount == 0) {
			count = rs.getInt(1);
		}
	}
	
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * �Ώی����̎擾
	 * @return	��������
	 */
	public int getCount() {
		return count;
	}

}
