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
import jp.go.jsps.kaken.model.vo.BukyokuInfo;
import jp.go.jsps.kaken.model.vo.BukyokuPk;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *�@���ǃ}�X�^�f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: MasterBukyokuInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class MasterBukyokuInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(MasterBukyokuInfoDao.class);

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
	public MasterBukyokuInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	
	/**
	 * ���Ǐ��̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @return						���Ǐ��
	 * @throws ApplicationException
	 */
	public static List selectBukyokuInfoList(Connection connection)
		throws ApplicationException
	{
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUKYOKU_CD"		//���ǃR�[�h
			+ ",A.BUKA_NAME"		//���Ȗ���
			+ ",A.BUKA_RYAKUSHO"	//���ȗ���
			+ ",A.BUKA_KATEGORI"	//�J�e�S��
			+ ",A.SORT_NO"			//�\�[�g�ԍ�
			+ ",A.BIKO"				//���l
			+ " FROM MASTER_BUKYOKU A"
			+ " ORDER BY SORT_NO";	//�\�[�g�ԍ�						
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
				"���Ǐ�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"���ǃ}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}
	
	
	
	//TODO �R�[�h�\
	/**
	 * ���Ǐ��̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @return						���Ǐ��
	 * @throws ApplicationException
	 */
	public static List selectBukyokuInfoList(Connection connection, String bukaKategori)
		throws ApplicationException {
		
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUKYOKU_CD"		//���ǃR�[�h
			+ ",A.BUKA_NAME"		//���Ȗ���
			+ ",A.BUKA_RYAKUSHO"	//���ȗ���
			+ ",A.BUKA_KATEGORI"	//�J�e�S��
			+ ",A.SORT_NO"			//�\�[�g�ԍ�
			+ ",A.BIKO"				//���l
			+ " FROM MASTER_BUKYOKU A"
			+ " WHERE BUKA_KATEGORI = ?"
			+ " ORDER BY SORT_NO";	//�\�[�g�ԍ�						
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ���X�g�擾
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString(), new String[]{bukaKategori});
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���Ǐ�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"���ǃ}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}

	//TODO �R�[�h�\
	/**
	 * �R�[�h�ꗗ�쐬�p���\�b�h�B<br>
	 * �J�e�S���ƃJ�e�S�����̂̈ꗗ���擾����B
	 * �J�e�S�����Ƀ\�[�g����B
	 * @param	connection			�R�l�N�V����
	 * @return
	 * @throws ApplicationException
	 */
	public static List selectKategoriInfoList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUKA_KATEGORI,"			//�J�e�S��
			+ " B.KATEGORI_NAME"			//�J�e�S������
			+ " FROM MASTER_BUKYOKU A, MASTER_KATEGORI B"
			+ " WHERE A.BUKA_KATEGORI = B.BUKA_KATEGORI"
			+ " GROUP BY A.BUKA_KATEGORI, B.KATEGORI_NAME"
			+ " ORDER BY A.BUKA_KATEGORI";								
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
				"���Ǐ�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"���ǃ}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}
	
	
	
	

	/**
	 * �L�[�Ɉ�v���镔�Ǐ����擾����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						���Ǐ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public BukyokuInfo selectBukyokuInfo(Connection connection,BukyokuPk pkInfo)
		throws DataAccessException, NoDataFoundException {

		BukyokuInfo result = new BukyokuInfo();
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement =
				connection.prepareStatement(
					"SELECT * FROM MASTER_BUKYOKU WHERE BUKYOKU_CD = ? ");
			int i = 1;
			//preparedStatement.setInt(i++, Integer.parseInt(pkInfo.getBukyokuCd()));
			DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getBukyokuCd());
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));
				result.setBukaName(recordSet.getString("BUKA_NAME"));
				result.setBukaRyakusyo(recordSet.getString("BUKA_RYAKUSHO"));
				result.setBukaKategori(recordSet.getString("BUKA_KATEGORI"));
				result.setSortNo(recordSet.getString("SORT_NO"));
				result.setBiko(recordSet.getString("BIKO"));
			} else {
				throw new NoDataFoundException(
					"���ǃ}�X�^�ɊY������f�[�^��������܂���B�����L�[�F���ǃR�[�h'"
						+ pkInfo.getBukyokuCd()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("���ǃ}�X�^�������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return result;
	}



	/**
	 * ���Ǐ���o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^���镔�Ǐ��
	 * @throws DataAccessException		�o�^�ɗ�O�����������ꍇ�B
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertBukyokuInfo(Connection connection,BukyokuInfo addInfo)
		throws DataAccessException,DuplicateKeyException {

		//�d���`�F�b�N
		try {
			selectBukyokuInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}

		String query = "INSERT INTO MASTER_BUKYOKU "
					 + "("
					 + " BUKYOKU_CD"
					 + ",BUKA_NAME"
					 + ",BUKA_RYAKUSHO"
					 + ",BUKA_KATEGORI"
					 + ",SORT_NO"
					 + ",BIKO"
					 + ") "
					 + "VALUES "
					 + "(?,?,?,?,?,?)"
					 ;

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukaName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukaRyakusyo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukaKategori());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSortNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("���ǃ}�X�^�o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * ���Ǐ����X�V����B
	 * @param connection			�R�l�N�V����
	 * @param updateInfo			�X�V���镔�Ǐ��
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateBukyokuInfo(Connection connection,BukyokuInfo updateInfo)
		throws DataAccessException, NoDataFoundException {
		
		//�����i�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectBukyokuInfo(connection, updateInfo);
		
		String query = "UPDATE MASTER_BUKYOKU "
					 + " SET"
					 + " BUKYOKU_CD = ?"
					 + ",BUKA_NAME = ?"
					 + ",BUKA_RYAKUSHO = ?"
					 + ",BUKA_KATEGORI = ?"
					 + ",SORT_NO = ?"
					 + ",BIKO = ?"
					 + " WHERE "
					 + " BUKYOKU_CD = ?"
					 ;

		PreparedStatement preparedStatement = null;
		try {
			//�X�V
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukaName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukaRyakusyo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukaKategori());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSortNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuCd());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("���ǃ}�X�^�o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	
	
	/**
	 * ���Ǐ����폜����B
	 * @param connection			�R�l�N�V����
	 * @param deleteInfo			�폜���镔�ǎ�L�[���
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ 
	 */
	public void deleteBukyokuInfo(Connection connection,BukyokuPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
		
		//�����i�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectBukyokuInfo(connection, deleteInfo);

		String query =
			"DELETE FROM MASTER_BUKYOKU"
				+ " WHERE"
				+ " BUKYOKU_CD = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�폜
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getBukyokuCd());
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("���ǃ}�X�^�폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

}
