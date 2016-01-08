/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.*;
import java.util.*;

import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;

import org.apache.commons.logging.*;

/**
 *�@�}�X�^�Ǘ��}�X�^�f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: MasterKanriInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class MasterKanriInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(MasterKanriInfoDao.class);

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
	public MasterKanriInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * �}�X�^�Ǘ������擾����B
	 * @param	connection				�R�l�N�V����
	 * @param  masterShubetu       	�}�X�^��ʁi��L�[���j
	 * @return							�}�X�^�Ǘ����
	 * @throws NoDataFoundException	�f�[�^��������Ȃ������ꍇ
	 * @throws DataAccessException		�f�[�^�x�[�X�A�N�Z�X���ɃG���[�����������ꍇ
	 */
	public MasterKanriInfo selectMasterKanriInfo(
		Connection connection,
		String     masterShubetu)
		throws NoDataFoundException, DataAccessException
	{
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String query = "SELECT " 
					  + "A.MASTER_SHUBETU, "				//�}�X�^���
					  + "A.MASTER_NAME, "					//�}�X�^����
					  + "A.IMPORT_DATE, "					//��荞�ݓ���
					  + "A.KENSU, "							//����
					  + "A.IMPORT_TABLE, "					//��荞�݃e�[�u����
					  + "A.IMPORT_FLG, "					//�V�K�E�X�V�t���O
					  + "A.IMPORT_MSG, "					//������
					  + "A.CSV_PATH "						//CSV�t�@�C���p�X
					  + "FROM MASTER_INFO A"
					  + " WHERE MASTER_SHUBETU = ?";						  								

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// �}�X�^�Ǘ����擾
		//-----------------------
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			MasterKanriInfo result = new MasterKanriInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, masterShubetu);
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setMasterShubetu(recordSet.getString("MASTER_SHUBETU"));
				result.setMasterName(recordSet.getString("MASTER_NAME"));
				result.setImportDate(recordSet.getDate("IMPORT_DATE"));
				result.setKensu(recordSet.getString("KENSU"));
				result.setImportTable(recordSet.getString("IMPORT_TABLE"));
				result.setImportFlg(recordSet.getString("IMPORT_FLG"));
				result.setImportMsg(recordSet.getString("IMPORT_MSG"));
				result.setCsvPath(recordSet.getString("CSV_PATH"));
				return result;
			} else {
				throw new NoDataFoundException(
					"�}�X�^�Ǘ����e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�}�X�^��ʁF'"
						+ masterShubetu
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�}�X�^�Ǘ����e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	/**
	 * �}�X�^�Ǘ����̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @return						�}�X�^�Ǘ����
	 * @throws ApplicationException
	 */
	public static List selectList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select = "SELECT A.MASTER_SHUBETU, "										//�}�X�^���
					  + "A.MASTER_NAME, "												//�}�X�^����
					  + "TO_CHAR(A.IMPORT_DATE, 'YYYY/MM/DD HH24:MI:SS') IMPORT_DATE, "	//��荞�ݓ���
					  + "A.KENSU, "														//����
					  + "A.IMPORT_TABLE, "												//��荞�݃e�[�u����
					  + "A.IMPORT_FLG, "												//�V�K�E�X�V�t���O
					  + "A.IMPORT_MSG, "												//������
					  + "A.CSV_PATH "													//CSV�t�@�C���p�X
					  + "FROM MASTER_INFO A"
					  + " ORDER BY TO_NUMBER(MASTER_SHUBETU)";						  								

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
				"�}�X�^�Ǘ���񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�}�X�^�Ǘ��}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}



	/**
	 * �}�X�^�Ǘ��e�[�u���̏����X�V����B
	 * IMPORT_DATE�ɂ��ẮA�f�[�^�x�[�X�̃V�X�e�������ōX�V����B
	 * @param connection			�R�l�N�V����
	 * @param dataInfo				�X�V����}�X�^�Ǘ����
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void update(
		Connection connection,
		MasterKanriInfo dataInfo)
		throws DataAccessException, NoDataFoundException {
	
			String query = "UPDATE MASTER_INFO"
						 + " SET"
						 + " MASTER_SHUBETU = ?"
						 + ",MASTER_NAME = ?"
						 + ",IMPORT_DATE = SYSDATE"
						 + ",KENSU = ?"
						 + ",IMPORT_TABLE = ?"
						 + ",IMPORT_FLG = ?"
						 + ",IMPORT_MSG = ?"
						 + ",CSV_PATH = ?"
						 + " WHERE"
						 + " MASTER_SHUBETU = ? "
						 ;
			
			if(log.isDebugEnabled()){
				log.debug("query:" + query);
			}
			
			PreparedStatement preparedStatement = null;
			try {
				//�}�X�^�Ǘ��e�[�u���X�V
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getMasterShubetu());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getMasterName());
				//DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getImportDate());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getKensu());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getImportTable());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getImportFlg());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getImportMsg());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getCsvPath());
				DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getMasterShubetu());
				DatabaseUtil.executeUpdate(preparedStatement);

			} catch (SQLException ex) {
				throw new DataAccessException("�}�X�^�Ǘ��e�[�u���X�V���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}

	
	
	/**
	 * �w��e�[�u���̑S���R�[�h�𕨗��폜����B
	 * @param connection			�R�l�N�V����
	 * @param table_name			�폜����\���ҏ���L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteMaster(
		Connection connection,
		String table_name)
		throws DataAccessException, NoDataFoundException {
	
			String query = "DELETE FROM " + table_name;
			
			if(log.isDebugEnabled()){
				log.debug("query:" + query);
			}
						
			PreparedStatement preparedStatement = null;
			try {
				//�폜
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.executeUpdate();

			} catch (SQLException ex) {
				throw new DataAccessException(table_name+"���R�[�h�폜���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	
	
	
	/**
	 * �w��e�[�u���̑S���R�[�h�𕨗��폜����B
	 * @param connection			�R�l�N�V����
	 * @param table_name			�폜����\���ҏ���L�[���
	 * @param _query               �₢���킹����
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteMaster(
		Connection connection,
		String table_name,
		String _query)
		throws DataAccessException, NoDataFoundException {
	
			String query = "DELETE FROM " + table_name + " " + _query;
			
			if(log.isDebugEnabled()){
				log.debug("query:" + query);
			}
			
			PreparedStatement preparedStatement = null;
			try {
				//�폜
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.executeUpdate();

			} catch (SQLException ex) {
				throw new DataAccessException(table_name+"���R�[�h�폜���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	
	/**
	 * �����Җ���}�X�^�X�V�����擾����
	 * @param connection
	 * @return �uYYYYMMDD�v�`���̍X�V��
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public String selectMeiboUpdateDate(Connection connection)
			throws NoDataFoundException, DataAccessException
	{
		
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String query = "SELECT" 
					  + " TO_CHAR(MASTER_DATE, 'YYYYMMDD') MASTER_DATE"		//�}�X�^�X�V��
					  + " FROM MASTER_INFO"
					  + " WHERE MASTER_SHUBETU = '7'";						  								

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// �}�X�^�Ǘ����擾
		//-----------------------
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				return recordSet.getString("MASTER_DATE");
			} else {
				throw new NoDataFoundException(
					"�}�X�^�Ǘ����e�[�u���Ɍ����҃}�X�^�̃f�[�^��������܂���B");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�}�X�^�Ǘ����e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
}