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
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
import jp.go.jsps.kaken.model.vo.ShoruiKanriPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���ފǗ����f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: ShoruiKanriInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class ShoruiKanriInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(ShoruiKanriInfoDao.class);

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
	public ShoruiKanriInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * ���ފǗ������擾����B�폜�t���O���u0�v�̏ꍇ�̂݌�������B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						���ފǗ����
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public ShoruiKanriInfo selectShoruiKanriInfo(
		Connection connection,
		ShoruiKanriPk primaryKeys)
		throws DataAccessException, NoDataFoundException {
		String query =
			"SELECT "
				+ " A.JIGYO_ID"				//����ID
				+ ",A.TAISHO_ID"			//�Ώ�
				+ ",A.SYSTEM_NO"			//�V�X�e����t�ԍ�
				+ ",A.SHORUI_FILE"			//�i�[��f�B���N�g��
				+ ",A.SHORUI_NAME"			//���ޖ�
				+ ",A.DEL_FLG"				//�폜�t���O
				+ " FROM SHORUIKANRI A"
				+ " WHERE DEL_FLG = 0"; 
		StringBuffer buffer = new StringBuffer(query);
			
		if(primaryKeys.getJigyoId() != null && primaryKeys.getJigyoId().length() != 0){	//����ID
			buffer.append("AND JIGYO_ID = ?");					
		}
		if(primaryKeys.getSystemNo() != null && primaryKeys.getSystemNo().length() != 0){	//�V�X�e���ԍ�
			buffer.append("AND SYSTEM_NO = ?");							
		}

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShoruiKanriInfo result = new ShoruiKanriInfo();
			preparedStatement = connection.prepareStatement(buffer.toString());
			int i = 1;
			if(primaryKeys.getJigyoId() != null && primaryKeys.getJigyoId().length() != 0){	//����ID
				preparedStatement.setString(i++, primaryKeys.getJigyoId());					
			}
			if(primaryKeys.getSystemNo() != null && primaryKeys.getSystemNo().length() != 0){	//�V�X�e����t�ԍ�
				preparedStatement.setString(i++, primaryKeys.getSystemNo());				
			}
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setJigyoId(recordSet.getString("JIGYO_ID"));			//����ID
				result.setTaishoId(recordSet.getString("TAISHO_ID"));		//�Ώ�
				result.setSystemNo(recordSet.getString("SYSTEM_NO"));		//�V�X�e����t�ԍ�
				result.setShoruiFile(recordSet.getString("SHORUI_FILE"));	//�i�[��f�B���N�g��
				result.setShoruiName(recordSet.getString("SHORUI_NAME"));	//���ޖ�
				result.setDelFlg(recordSet.getString("DEL_FLG"));			//�폜�t���O
				return result;
			} else {
				throw new NoDataFoundException(
					"���ފǗ����e�[�u���ɊY������f�[�^��������܂���B�����L�[�F����ID'"
						+ primaryKeys.getJigyoId()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("���ފǗ����e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}




	/**
	 * ���ފǗ�����o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^���鏑�ފǗ����
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertShoruiKanriInfo(
		Connection connection,
		ShoruiKanriInfo addInfo)
		throws DataAccessException, DuplicateKeyException {
			
		//�d���`�F�b�N
		try {
			selectShoruiKanriInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO SHORUIKANRI "
				+ "(JIGYO_ID"		//����ID
				+ ",TAISHO_ID"		//�Ώ�
				+ ",SYSTEM_NO"		//�V�X�e����t�ԍ�
				+ ",SHORUI_FILE"	//�i�[��f�B���N�g��
				+ ",SHORUI_NAME"	//���ޖ�
				+ ",DEL_FLG) "		//�폜�t���O
				+ "VALUES "
				+ "(?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoId());		//����ID
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTaishoId());		//�Ώ�
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSystemNo());		//�V�X�e����t�ԍ�
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShoruiFile());	//�i�[��f�B���N�g��
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShoruiName());	//���ޖ�	
			DatabaseUtil.setParameter(preparedStatement, i++, 0);						//�폜�t���O
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("���ފǗ����o�^���ɗ�O���������܂����B ", ex);
			throw new DataAccessException("���ފǗ����o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * ���ފǗ������폜����B(�폜�t���O) 
	 * @param connection			�R�l�N�V����
	 * @param addInfo				�폜���鏑�ފǗ�����L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteFlgShoruiKanriInfo(
		Connection connection,
		ShoruiKanriPk deleteInfo)
		throws DataAccessException, NoDataFoundException {

		//����
		selectShoruiKanriInfo(connection, deleteInfo);
		
		String query =
			"UPDATE SHORUIKANRI"
				+ " SET"
				+ " DEL_FLG = 1";//�폜�t���O				
				
		StringBuffer buffer = new StringBuffer(query);
				
		if(deleteInfo.getJigyoId() != null && deleteInfo.getJigyoId().length() != 0){			//����ID
			buffer.append("WHERE JIGYO_ID = ?");		
		}else if(deleteInfo.getSystemNo() != null && deleteInfo.getSystemNo().length() != 0){	//�V�X�e���ԍ�	
			buffer.append("WHERE SYSTEM_NO = ?");	
		}

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(buffer.toString());
			int i = 1;
			if(deleteInfo.getJigyoId() != null && deleteInfo.getJigyoId().length() != 0){
				preparedStatement.setString(i++, deleteInfo.getJigyoId());		//����ID
				int count = preparedStatement.executeUpdate();

				if(log.isDebugEnabled()){
					log.debug(count + "���̏��ފǗ����̍폜�ɐ������܂����B");
				}

			}else if(deleteInfo.getSystemNo() != null && deleteInfo.getSystemNo().length() != 0){
				DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getSystemNo());		//�V�X�e���ԍ�				
				DatabaseUtil.executeUpdate(preparedStatement);
			}
		} catch (SQLException ex) {

			throw new DataAccessException("���ފǗ����폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	
	/**
	 * ���ފǗ������폜����B(�����폜) 
	 * @param connection			�R�l�N�V����
	 * @param addInfo				�폜���鏑�ފǗ�����L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteJigyoKanriInfo(
		Connection connection,
		ShoruiKanriPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
	}
	




}
