/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �V�[�P���X���f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: SequenceUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class SequenceUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(SequenceUtil.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���s���郆�[�U��� */
	private UserInfo userInfo = null;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	
	/**
	 * �R���X�g���N�^�B
	 * @param userInfo	���s���郆�[�U���
	 */
	public SequenceUtil(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * �V�[�P���X���擾����B
	 * @param SequenceName			�擾����V�[�P���X��
	 * @param figer 				����
	 * @return						�V�[�P���X�l
	 * @throws DataAccessException	�f�[�^�x�[�X�A�N�Z�X���̗�O
	 */
	public String select(Connection connection ,String sequenceName, int figure)
		throws DataAccessException, NoDataFoundException {
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			StringBuffer keta = new StringBuffer("FM");
			for(int i=0; i<figure; i++){
				keta.append("0");
			}
			preparedStatement =
				connection.prepareStatement(
					"SELECT TO_CHAR(" + sequenceName + ".NEXTVAL,'" + keta.toString() + "') SEQ FROM DUAL");				

			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				return recordSet.getString("SEQ");
			} else {
				throw new NoDataFoundException(
					"�V�[�P���X�̎擾�Ɏ��s���܂����B�V�[�P���X��'"
						+ sequenceName
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�V�[�P���X�̎擾���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
}
