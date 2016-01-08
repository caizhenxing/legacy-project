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

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �Ɩ��S���ҏ��f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: GyomutantoInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class GyomutantoInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** �Ɩ��S���ҏ��Ǘ��V�[�P���X�� */
	public static final String SEQ_GYOMUTANTOINFO = "SEQ_GYOMUTANTOINFO";

	/** ���O */
	protected static final Log log = LogFactory.getLog(GyomutantoInfoDao.class);

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
	public GyomutantoInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * �L�[�Ɉ�v����Ɩ��S���ҏ����擾����B
	 * @param connection			�R�l�N�V����
	 * @param pkInfo				��L�[���
	 * @return						�Ɩ��S���ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public GyomutantoInfo selectGyomutantoInfo(
		Connection connection,
		GyomutantoPk pkInfo)
		throws DataAccessException, NoDataFoundException {
			String query =
				"SELECT "
					+ " GYOMUTANTO_ID"
					+ ",ADMIN_FLG"
					+ ",PASSWORD"
					+ ",NAME_KANJI_SEI"
					+ ",NAME_KANJI_MEI"
					+ ",NAME_KANA_SEI"
					+ ",NAME_KANA_MEI"
					+ ",BUKA_NAME"
					+ ",KAKARI_NAME"
					+ ",BIKO"
					+ ",YUKO_DATE"
					+ ",DEL_FLG"
					+ " FROM GYOMUTANTOINFO"
					+ " WHERE GYOMUTANTO_ID = ?";
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			try {
				GyomutantoInfo result = new GyomutantoInfo();
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				preparedStatement.setString(i++, pkInfo.getGyomutantoId());
				recordSet = preparedStatement.executeQuery();
				if (recordSet.next()) {
					result.setGyomutantoId(recordSet.getString("GYOMUTANTO_ID"));
					result.setAdminFlg(recordSet.getString("ADMIN_FLG"));
					result.setPassword(recordSet.getString("PASSWORD"));
					result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));
					result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));
					result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
					result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
					result.setBukaName(recordSet.getString("BUKA_NAME"));
					result.setKakariName(recordSet.getString("KAKARI_NAME"));
					result.setBiko(recordSet.getString("BIKO"));
					result.setDelFlg(recordSet.getString("DEL_FLG"));
					result.setYukoDate(recordSet.getDate("YUKO_DATE"));
					return result;
				} else {
					throw new NoDataFoundException(
						"�Ɩ��S���ҏ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�Ɩ��S����ID'"
							+ pkInfo.getGyomutantoId()
							+ "'");
				}
			} catch (SQLException ex) {
				throw new DataAccessException("�Ɩ��S���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}

	/**
	 * �Ɩ��S���ҏ��̐����擾����B�폜�t���O���u0�v�̏ꍇ�̂݌�������B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						�Ɩ��S���ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 */
	public int countGyomutantoInfo(
		Connection connection,
		String gyomutantoId)
		throws DataAccessException {
		String query =
			"SELECT COUNT(*)"
				+ " FROM GYOMUTANTOINFO"
				+ " WHERE GYOMUTANTO_ID = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			GyomutantoInfo result = new GyomutantoInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, gyomutantoId);
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("�Ɩ��S���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * �Ɩ��S���ҏ���o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^����Ɩ��S���ҏ��
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertGyomutantoInfo(
		Connection connection,
		GyomutantoInfo addInfo)
		throws DataAccessException, DuplicateKeyException {

			//�d���`�F�b�N
			try {
				selectGyomutantoInfo(connection, addInfo);
				//NG
				throw new DuplicateKeyException(
					"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
			} catch (NoDataFoundException e) {
				//OK
			}
		
			String query =
				"INSERT INTO GYOMUTANTOINFO "
					+ "(GYOMUTANTO_ID"
					+ ",ADMIN_FLG"
					+ ",PASSWORD"
					+ ",NAME_KANJI_SEI"
					+ ",NAME_KANJI_MEI"
					+ ",NAME_KANA_SEI"
					+ ",NAME_KANA_MEI"
					+ ",BUKA_NAME"
					+ ",KAKARI_NAME"
					+ ",BIKO"
					+ ",YUKO_DATE"
					+ ",DEL_FLG) "
					+ "VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = null;
			try {
				//�o�^
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getGyomutantoId());
				DatabaseUtil.setParameter(preparedStatement, i++, 0);
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getPassword());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiSei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiMei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaSei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaMei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukaName());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKakariName());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYukoDate());
				DatabaseUtil.setParameter(preparedStatement, i++, 0);
				DatabaseUtil.executeUpdate(preparedStatement);
			} catch (SQLException ex) {
				throw new DataAccessException("�Ɩ��S���ҏ��o�^���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}

	/**
	 * �Ɩ��S���ҏ����X�V����B
	 * @param connection				�R�l�N�V����
	 * @param updateInfo				�X�V����Ɩ��S���ҏ��
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException		�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateGyomutantoInfo(
		Connection connection,
		GyomutantoInfo updateInfo)
		throws DataAccessException, NoDataFoundException {
			//����
			selectGyomutantoInfo(connection, updateInfo);
	
			String query =
				"UPDATE GYOMUTANTOINFO"
					+ " SET"
					+ " ADMIN_FLG = ?"
					+ ",PASSWORD = ?"
					+ ",NAME_KANJI_SEI = ?"
					+ ",NAME_KANJI_MEI = ?"
					+ ",NAME_KANA_SEI = ?"
					+ ",NAME_KANA_MEI = ?"
					+ ",BUKA_NAME = ?"
					+ ",KAKARI_NAME = ?"
					+ ",BIKO = ?"
					+ ",YUKO_DATE = ?"
					+ ",DEL_FLG = ?"
					+ " WHERE"
					+ " GYOMUTANTO_ID = ?";

			PreparedStatement preparedStatement = null;
			try {
				//�o�^
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getAdminFlg());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getPassword());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukaName());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKakariName());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getYukoDate());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDelFlg());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getGyomutantoId());
				DatabaseUtil.executeUpdate(preparedStatement);

			} catch (SQLException ex) {
				throw new DataAccessException("�Ɩ��S���ҏ��X�V���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}

	/**
	 * �Ɩ��S���ҏ����폜����B(�폜�t���O) 
	 * @param connection			�R�l�N�V����
	 * @param pkInfo				�폜����Ɩ��S���ҏ��
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteFlgGyomutantoInfo(
		Connection connection,
		GyomutantoPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
			//����
			selectGyomutantoInfo(connection, deleteInfo);
	
			String query =
				"UPDATE GYOMUTANTOINFO"
					+ " SET"
					+ " DEL_FLG = 1"
					+ " WHERE"
					+ " GYOMUTANTO_ID = ?";

			PreparedStatement preparedStatement = null;
			try {
				//�o�^
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getGyomutantoId());
				DatabaseUtil.executeUpdate(preparedStatement);

			} catch (SQLException ex) {
				throw new DataAccessException("�Ɩ��S���ҏ��폜���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}

	/**
	 * �Ɩ��S���ҏ����폜����B(�����폜)
	 * @param connection			�R�l�N�V����
	 * @param pkInfo				�폜����Ɩ��S���ҏ��
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteGyomutantoInfo(
		Connection connection,
		GyomutantoPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
			//TODO �������̏��
	}

	/**
	 * ���[�UID�A�p�X���[�h�̔F�؂��s���B
	 * @param connection			�R�l�N�V����
	 * @param userid				���[�UID
	 * @param password				�p�X���[�h
	 * @return						�F�؂ɐ��������ꍇ true �ȊO false
	 * @throws DataAccessException	�f�[�^�x�[�X�A�N�Z�X���̗�O
	 */
	public boolean authenticateGyomutantoInfo(
		Connection connection,
		String userid,
		String password)
		throws DataAccessException {
			String query =
				"SELECT count(*)"
					+ " FROM GYOMUTANTOINFO"
					+ " WHERE DEL_FLG = 0"
					+ " AND ADMIN_FLG = 0"
					+ " AND GYOMUTANTO_ID = ?"
					+ " AND PASSWORD = ?";
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			int count = 0;
			try {
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				preparedStatement.setString(i++, userid);
				preparedStatement.setString(i++, password);
				recordSet = preparedStatement.executeQuery();
				if (recordSet.next()) {
					count = recordSet.getInt(1);
				}
				//��v����f�[�^�����݂���ꍇ�́Atrue�Ԃ�
				if (count > 0) {
					return true;
				} else {
					return false;
				}
			} catch (SQLException ex) {
				throw new DataAccessException("�Ɩ��S���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}

	/**
	 * ���[�UID�A�p�X���[�h�̔F�؂��s���B�i�V�X�e���Ǘ��җp�j
	 * @param connection			�R�l�N�V����
	 * @param userid				���[�UID
	 * @param password				�p�X���[�h
	 * @return						�F�؂ɐ��������ꍇ true �ȊO false
	 * @throws DataAccessException	�f�[�^�x�[�X�A�N�Z�X���̗�O
	 */
	public boolean authenticateSystemKanriInfo(
		Connection connection,
		String userid,
		String password)
		throws DataAccessException {
			String query =
				"SELECT count(*)"
					+ " FROM GYOMUTANTOINFO"
					+ " WHERE DEL_FLG = 0"
					+ " AND ADMIN_FLG = 1"
					+ " AND GYOMUTANTO_ID = ?"
					+ " AND PASSWORD = ?";
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			int count = 0;
			try {
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				preparedStatement.setString(i++, userid);
				preparedStatement.setString(i++, password);
				recordSet = preparedStatement.executeQuery();
				if (recordSet.next()) {
					count = recordSet.getInt(1);
				}
				//��v����f�[�^�����݂���ꍇ�́Atrue�Ԃ�
				if (count > 0) {
					return true;
				} else {
					return false;
				}
			} catch (SQLException ex) {
				throw new DataAccessException("�Ɩ��S���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}
	
	/**
	 * �p�X���[�h�̕ύX����B 
	 * @param connection			�R�l�N�V����
	 * @param pkInfo				��L�[���
	 * @param newPassword			�V�����p�X���[�h
	 * @return              		�p�X���[�h�̕ύX�ɐ��������ꍇ true �ȊO false
	 * @throws DataAccessException	�ύX���ɗ�O�����������ꍇ
	 */
	public boolean changePasswordGyomutantoInfo(
		Connection connection,
		GyomutantoPk pkInfo,
		String newPassword)
		throws DataAccessException {

			String query = "UPDATE GYOMUTANTOINFO "
						 + "SET "
						 + "PASSWORD = ? "
						 + "WHERE "
						 + "GYOMUTANTO_ID = ? "
						 + "AND DEL_FLG = 0";									//�폜�t���O

			PreparedStatement ps = null;
			
			if(log.isDebugEnabled()){
				log.debug("query:" + query);
			}
		
			try {
				//�o�^
				ps = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(ps,i++, newPassword);					//�V�����p�X���[�h
				DatabaseUtil.setParameter(ps,i++, pkInfo.getGyomutantoId());	//�Ɩ��S����ID
				DatabaseUtil.executeUpdate(ps);
			} catch (SQLException ex) {
				throw new DataAccessException("�p�X���[�h�ύX���ɗ�O���������܂����B ", ex);

			} finally {
				DatabaseUtil.closeResource(null, ps);
			}
			return true;
	}

}
