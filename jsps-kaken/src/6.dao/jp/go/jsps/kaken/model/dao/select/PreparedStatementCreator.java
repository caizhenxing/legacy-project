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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * �v���R���p�C�����ꂽ�X�e�[�g�����g���쐬����C���^�[�t�F�[�X�B
 *
 * ID RCSfile="$RCSfile: PreparedStatementCreator.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 */
public interface PreparedStatementCreator{

	/**
	 * �R�l�N�V�������A�X�e�[�g�����g���쐬����B
	 * @param conn			�R�l�N�V����
	 * @return				�X�e�[�g�����g
	 * @throws SQLException	
	 */
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException;

}
