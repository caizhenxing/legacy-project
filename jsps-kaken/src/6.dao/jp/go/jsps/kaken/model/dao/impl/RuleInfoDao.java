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
import jp.go.jsps.kaken.model.vo.RuleInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.RandomPwd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �h�c�p�X���[�h���s���[�����f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: RuleInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class RuleInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(RuleInfoDao.class);

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
	public RuleInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * �p�X���[�h���s���[�������擾����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						�p�X���[�h���s���[�����
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public RuleInfo selectRuleInfo(
		Connection connection,
		RulePk primaryKeys)
		throws DataAccessException, NoDataFoundException {
		String query =
			"SELECT "
				+ " * "
				+ " FROM RULEINFO A"
				+ " WHERE TAISHO_ID = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			RuleInfo result = new RuleInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getTaishoId());
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setTaishoId(recordSet.getString("TAISHO_ID"));
				result.setMojisuChk(recordSet.getString("MOJISU_CHK"));
				result.setCharChk1(recordSet.getString("CHAR_CHK1"));
				result.setCharChk2(recordSet.getString("CHAR_CHK2"));
				result.setCharChk3(recordSet.getString("CHAR_CHK3"));
				result.setCharChk4(recordSet.getString("CHAR_CHK4"));
				result.setCharChk5(recordSet.getString("CHAR_CHK5"));
				result.setYukoDate(recordSet.getDate("YUKO_DATE"));
				result.setBiko(recordSet.getString("BIKO"));
				return result;
			} else {
				throw new NoDataFoundException(
					"�p�X���[�h���s���[���e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�Ώێ�ID'"
						+ primaryKeys
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�p�X���[�h���s���[���e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * �p�X���[�h���s���[������o�^����B
	 * @param connection			�R�l�N�V����
	 * @param addInfo				�o�^����p�X���[�h���s���[�����
	 * @return						�o�^�����p�X���[�h���s���[�����
	 * @throws DataAccessException	�o�^�ɗ�O�����������ꍇ�B
	 */
	public void insertRuleInfo(Connection connection, RuleInfo addInfo)
		throws DataAccessException,DuplicateKeyException {
	}
	
	/**
	 * �p�X���[�h���s���[�������X�V����B
	 * @param connection			�R�l�N�V����
	 * @param updateInfo			�X�V����X���[�h���s���[�����
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateRuleInfo(Connection connection, RuleInfo updateInfo)
		throws DataAccessException, NoDataFoundException {

			//����
			selectRuleInfo(connection, updateInfo);

			String query =
				"UPDATE RULEINFO"
					+ " SET"
					+ " TAISHO_ID = ?"
					+ ",MOJISU_CHK = ?"
					+ ",CHAR_CHK1 = ?"
					+ ",CHAR_CHK2 = ?"
					+ ",CHAR_CHK3 = ?"
					+ ",CHAR_CHK4 = ?"
					+ ",CHAR_CHK5 = ?"
					+ ",YUKO_DATE = ?"
					+ ",BIKO = ?"
					+ " WHERE"
					+ " TAISHO_ID = ?";

			PreparedStatement preparedStatement = null;
			try {
				//�o�^
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTaishoId());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getMojisuChk());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getCharChk1());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getCharChk2());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getCharChk3());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getCharChk4());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getCharChk5());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getYukoDate());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTaishoId());
				DatabaseUtil.executeUpdate(preparedStatement);

			} catch (SQLException ex) {
				throw new DataAccessException("���s���[���ݒ蒆�ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}

	/**
	 * �p�X���[�h���s���[�������폜����B 
	 * @param connection			�R�l�N�V����
	 * @param userInfo				���s����X���[�h���s���[�����
	 * @param deleteInfo			�폜����\���ҏ��
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ 
	 */
	public void deleteRuleInfo(Connection connection, RuleInfo deleteInfo)
		throws DataAccessException {
	}

	/**
	 * �p�X���[�h���s���[���Ɋ�Â��A�p�X���[�h���쐬����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys				��L�[���
	 * @return							�\���җp�p�X���[�h
	 * @throws DataAccessException		�f�[�^�A�N�Z�X���ɗ�O�����������ꍇ�B
	 */
	public String getPassword(Connection connection, RulePk primaryKeys)
		throws NoDataFoundException, DataAccessException{
			boolean isUpperCase = true;	//true:�啶�����܂�
			boolean isLowerCase = true;	//true:���������܂�
			boolean isDigit = true;		//true:�������܂�
			int length = 6;				//�p�X���[�h�̒���

			RuleInfo ruleInfo = new RuleInfo();
			ruleInfo = selectRuleInfo(connection, primaryKeys);
			if(ruleInfo.getCharChk1() != null && ruleInfo.getCharChk1().equals("1")) {
				isUpperCase = false;
			}
			if(ruleInfo.getCharChk2() != null && ruleInfo.getCharChk2().equals("1")) {
				isDigit = false;
			}
			length = Integer.parseInt(ruleInfo.getMojisuChk());
			if(length > 10 || length < 6){
				length = 6;
			}
			return RandomPwd.generate(isUpperCase, isLowerCase, isDigit, length);
	}

//	/**
//	 * �p�X���[�h���s���[���Ɋ�Â��A�\���җp�p�X���[�h���쐬����B
//	 * @return							�\���җp�p�X���[�h
//	 * @throws DataAccessException		�f�[�^�A�N�Z�X���ɗ�O�����������ꍇ�B
//	 */
//	public String getShinseiPassword()
//		throws NoDataFoundException, DataAccessException{
//		return "shinsei";
//	}
//	
//	/**
//	 * �p�X���[�h���s���[���Ɋ�Â��A�����@�֒S���җp�p�X���[�h���쐬����B
//	 * @return							�����@�֒S���җp�p�X���[�h
//	 * @throws DataAccessException		�f�[�^�A�N�Z�X���ɗ�O�����������ꍇ�B
//	 */
//	public String getShozokuPassword()
//		throws DataAccessException{
//		return "kikan";
//	}
//
//	/**
//	 * �p�X���[�h���s���[���Ɋ�Â��A�Ɩ��S���җp�p�X���[�h���쐬����B
//	 * @return							�Ɩ��S���җp�p�X���[�h
//	 * @throws DataAccessException		�f�[�^�A�N�Z�X���ɗ�O�����������ꍇ�B
//	 */
//	public String getGyomutantoPassword()
//		throws DataAccessException{
//		return "gyomutanto";
//	}
//
//	/**
//	 * �p�X���[�h���s���[���Ɋ�Â��A:�R�����p�p�X���[�h���쐬����B
//	 * @return							�����@�֒S���җp�p�X���[�h
//	 * @throws DataAccessException		�f�[�^�A�N�Z�X���ɗ�O�����������ꍇ�B
//	 */
//	public String getShinsaPassword()
//		throws DataAccessException{
//		return "shinsa";
//	}
	
}
