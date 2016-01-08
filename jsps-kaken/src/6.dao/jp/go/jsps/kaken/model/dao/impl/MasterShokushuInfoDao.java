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
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShokushuInfo;
import jp.go.jsps.kaken.model.vo.ShokushuPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *�@�E��}�X�^�f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: MasterShokushuInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class MasterShokushuInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(MasterShokushuInfoDao.class);

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
	public MasterShokushuInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * �E����̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @return						�E����
	 * @throws ApplicationException
	 */
	public static List selectShokushuList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.SHOKUSHU_CD"			//�E��R�[�h
			+ ",A.SHOKUSHU_NAME"		//�E����
			+ ",A.SHOKUSHU_NAME_RYAKU"	//�E��(����)
			+ ",A.BIKO"					//���l
			+ " FROM MASTER_SHOKUSHU A"
			+ " ORDER BY A.SHOKUSHU_CD";								
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ���X�g�擾
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�E���񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�E��}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}

	/**
	 * �E������擾����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						�E����
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public ShokushuInfo selectShokushuInfo(
		Connection connection,
		ShokushuPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		String query =
			"SELECT "
				+ " A.SHOKUSHU_CD"				//�E��R�[�h
				+ ",A.SHOKUSHU_NAME"			//�E����
				+ ",A.SHOKUSHU_NAME_RYAKU"		//�E��(����)
				+ ",A.BIKO"						//���l
				+ " FROM MASTER_SHOKUSHU A"
				+ " WHERE SHOKUSHU_CD = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShokushuInfo result = new ShokushuInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getShokushuCd());
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));
				result.setShokushuName(recordSet.getString("SHOKUSHU_NAME"));
				result.setShokushuNameRyaku(recordSet.getString("SHOKUSHU_NAME_RYAKU"));
				result.setBiko(recordSet.getString("BIKO"));
				return result;
			} else {
				throw new NoDataFoundException(
					"�E����e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�E��R�[�h'"
						+ primaryKeys.getShokushuCd()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�E����e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * �E�����o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^����E����
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertShokushuInfo(
		Connection connection,
		ShokushuInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//�d���`�F�b�N
		try {
			selectShokushuInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO MASTER_SHOKUSHU "
				+ "("
				+ " SHOKUSHU_CD"			//�E��R�[�h
				+ ",SHOKUSHU_NAME"			//�E��
				+ ",SHOKUSHU_NAME_RYAKU"	//�E��(����)
				+ ",BIKO"					//���l
				+ ")"
				+ "VALUES "
				+ "(?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuNameRyaku());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("�E����o�^���ɗ�O���������܂����B ", ex);
			throw new DataAccessException("�E����o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * �E������X�V����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�X�V����E����
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException		�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateShokushuInfo(
		Connection connection,
		ShokushuInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectShokushuInfo(connection, updateInfo);

		String query =
			"UPDATE MASTER_SHOKUSHU"
				+ " SET"	
				+ " SHOKUSHU_CD = ?"			//�E��R�[�h
				+ ",SHOKUSHU_NAME = ?"			//�E����
				+ ",SHOKUSHU_NAME_RYAKU = ?"	//�E��(����)
				+ ",BIKO = ?"					//���l
				+ " WHERE"
				+ " SHOKUSHU_CD = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuNameRyaku());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuName());	//�E��R�[�h
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("�E����X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	
	/**
	 * �E������폜����B(�����폜) 
	 * @param connection			�R�l�N�V����
	 * @param addInfo				�폜����E���L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteShokushuInfo(
		Connection connection,
		ShokushuPk deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectShokushuInfo(connection, deleteInfo);

		String query =
			"DELETE FROM MASTER_SHOKUSHU"
				+ " WHERE"
				+ " SHOKUSHU_CD = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getShokushuCd());	//�E��R�[�h
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("�E����폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

}
