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
 * ResultSet���������邽�߂̃n���h���C���^�[�t�F�[�X�B
 *
 * ID RCSfile="$RCSfile: RowCallbackHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 */
public interface RowCallbackHandler {

	/**
	 * ResultSet���������邽�߂̃��\�b�h�B
	 * resultSet.next()�̊ԌĂяo�����B
	 * @param rs				ResultSet
	 * @throws SQLException		
	 */
	public void processRow(ResultSet rs) throws SQLException,NoDataFoundException;

}
