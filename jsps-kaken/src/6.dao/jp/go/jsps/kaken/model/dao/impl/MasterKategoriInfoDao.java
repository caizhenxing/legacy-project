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
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.KategoriInfo;
import jp.go.jsps.kaken.model.vo.KategoriPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �J�e�S���}�X�^�f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: MasterKategoriInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class MasterKategoriInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(MasterKategoriInfoDao.class);

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
	public MasterKategoriInfoDao(UserInfo userInfo) {
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
	public static List selectKategoriList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUKA_KATEGORI"		//�J�e�S��
			+ ",A.KATEGORI_NAME"		//�J�e�S������
			+ ",A.BIKO"					//���l
			+ " FROM MASTER_KATEGORI A"
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
				"�J�e�S����񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�J�e�S���}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}

	/**
	 * �J�e�S�������擾����B
	 * @param connection				�R�l�N�V����
	 * @param primaryKeys				��L�[���
	 * @return							�J�e�S�����
	 * @throws DataAccessException		�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public KategoriInfo selectKategoriInfo(
		Connection connection,
		KategoriPk primaryKeys)
		throws DataAccessException, NoDataFoundException {

		String query =
			"SELECT "
				+ " A.BUKA_KATEGORI"			//�J�e�S��
				+ ",A.KATEGORI_NAME"			//�J�e�S������
				+ ",A.BIKO"						//���l
				+ " FROM MASTER_KATEGORI A"
				+ " WHERE BUKA_KATEGORI = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			KategoriInfo result = new KategoriInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getBukaKategori());
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setBukaKategori(recordSet.getString("BUKA_KATEGORI"));
				result.setKategoriName(recordSet.getString("KATEGORI_NAME"));
				result.setBiko(recordSet.getString("BIKO"));
				return result;
			} else {
				throw new NoDataFoundException(
					"�J�e�S�����e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�J�e�S���R�[�h'"
						+ primaryKeys.getBukaKategori()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�J�e�S�����e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
}
