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

import java.sql.*;

import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;

import org.apache.commons.logging.*;

/**
 * �����@�֏��f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: ShozokuInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class ShozokuInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** �����@�֏��Ǘ��V�[�P���X�� */
	public static final String SEQ_SHOZOKUINFO = "SEQ_SHOZOKUINFO";
	
	/** �����@�֏��Ǘ��V�[�P���X�̎擾�����i�A�ԗp�j */
	public static final int SEQ_FIGURE = 2;

	/** ���O */
	protected static final Log log = LogFactory.getLog(ShozokuInfoDao.class);

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
	public ShozokuInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * �����@�֏����擾����B�폜�t���O���u0�v�̏ꍇ�̂݌�������B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						�����@�֏��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public ShozokuInfo selectShozokuInfo(
		Connection connection,
		ShozokuPk primaryKeys)
		throws DataAccessException, NoDataFoundException {
		String query =
			"SELECT "
				+ " A.SHOZOKUTANTO_ID"						//�����@�֒S����ID
				+ ",A.SHOZOKU_CD"							//�����@�֖��i�R�[�h�j
				+ ",A.SHOZOKU_NAME_KANJI"					//�����@�֖��i���{��j
				+ ",A.SHOZOKU_RYAKUSHO"						//�����@�֖��i���́j
				+ ",A.SHOZOKU_NAME_EIGO"					//�����@�֖��i�p���j
				+ ",A.SHUBETU_CD"							//�@�֎��
				+ ",A.PASSWORD"								//�p�X���[�h
				+ ",A.SEKININSHA_NAME_SEI"					//�ӔC�Ҏ����i���j
				+ ",A.SEKININSHA_NAME_MEI"					//�ӔC�Ҏ����i���j
				+ ",A.SEKININSHA_YAKU"						//�ӔC�Җ�E
				+ ",A.BUKYOKU_NAME"							//�S�����ۖ�
				+ ",A.KAKARI_NAME"							//�S���W��
				+ ",A.TANTO_NAME_SEI"						//�S���Җ��i���j
				+ ",A.TANTO_NAME_MEI"						//�S���Җ��i���j
				+ ",A.TANTO_TEL"							//�S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
				+ ",A.TANTO_FAX"							//�S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
				+ ",A.TANTO_EMAIL"							//�S���ҕ��Ǐ��ݒn�iEmail�j
				+ ",A.TANTO_EMAIL2"							//�S���ҕ��Ǐ��ݒn�iEmail2�j
				+ ",A.TANTO_ZIP"							//�S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
				+ ",A.TANTO_ADDRESS"						//�S���ҕ��Ǐ��ݒn�i�Z���j
				+ ",A.NINSHOKEY_FLG"						//�F�؃L�[���s�t���O
				+ ",A.BIKO"									//���l
				+ ",A.YUKO_DATE"							//�L������
				//2005.08.10 iso ID���s���t�̒ǉ�
				+ ",A.ID_DATE"								//ID���s���t
// 2004/04/20 �ǉ� ��������----------------------------------------------------
// ���R �u���ǒS���Ґl���v�擾
				+ ",A.BUKYOKU_NUM"							//���ǒS���Ґl��
// �ǉ� �����܂�---------------------------------------------------------------
				+ ",A.DEL_FLG"								//�폜�t���O
				+ " FROM SHOZOKUTANTOINFO A"
				+ " WHERE SHOZOKUTANTO_ID = ?"
				+ " AND DEL_FLG = 0";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShozokuInfo result = new ShozokuInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getShozokuTantoId());				//�����@�֒S����ID
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setShozokuTantoId(recordSet.getString("SHOZOKUTANTO_ID"));			//�����@�֒S����ID
				result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));						//�����@�֖��i�R�[�h�j
				result.setShozokuName(recordSet.getString("SHOZOKU_NAME_KANJI"));			//�����@�֖��i���{��j
				result.setShozokuRyakusho(recordSet.getString("SHOZOKU_RYAKUSHO"));			//�����@�֖��i���́j
				result.setShozokuNameEigo(recordSet.getString("SHOZOKU_NAME_EIGO"));		//�����@�֖��i�p���j			
				result.setShubetuCd(recordSet.getString("SHUBETU_CD"));						//�@�֎��
				result.setPassword(recordSet.getString("PASSWORD"));						//�p�X���[�h				
				result.setSekininshaNameSei(recordSet.getString("SEKININSHA_NAME_SEI"));	//�ӔC�Ҏ����i���j
				result.setSekininshaNameMei(recordSet.getString("SEKININSHA_NAME_MEI"));	//�ӔC�Ҏ����i���j
				result.setSekininshaYaku(recordSet.getString("SEKININSHA_YAKU"));			//�ӔC�Җ�E
				result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));					//�S�����ۖ�
				result.setKakariName(recordSet.getString("KAKARI_NAME"));					//�S���W��
				result.setTantoNameSei(recordSet.getString("TANTO_NAME_SEI"));				//�S���Җ��i���j
				result.setTantoNameMei(recordSet.getString("TANTO_NAME_MEI"));				//�S���Җ��i���j
				result.setTantoTel(recordSet.getString("TANTO_TEL"));						//�S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
				result.setTantoFax(recordSet.getString("TANTO_FAX"));						//�S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
				result.setTantoEmail(recordSet.getString("TANTO_EMAIL"));					//�S���ҕ��Ǐ��ݒn�iEmail�j
				result.setTantoEmail2(recordSet.getString("TANTO_EMAIL2"));					//�S���ҕ��Ǐ��ݒn�iEmail2�j
				result.setTantoZip(recordSet.getString("TANTO_ZIP"));						//�S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
				result.setTantoAddress(recordSet.getString("TANTO_ADDRESS"));				//�S���ҕ��Ǐ��ݒn�i�Z���j
				result.setNinshokeyFlg(recordSet.getString("NINSHOKEY_FLG"));				//�F�؃L�[���s�t���O
				result.setBiko(recordSet.getString("BIKO"));								//���l
				result.setYukoDate(recordSet.getDate("YUKO_DATE"));							//�L������
				//2005.08.10 iso ID���s���t�̒ǉ�
				result.setIdDate(recordSet.getDate("ID_DATE"));								//ID���s���t
// 2004/04/20 �ǉ� ��������----------------------------------------------------
// ���R �u���ǒS���Ґl���v�擾
				result.setBukyokuNum(recordSet.getString("BUKYOKU_NUM"));					//���ǒS���Ґl��
// �ǉ� �����܂�---------------------------------------------------------------
				result.setDelFlg(recordSet.getString("DEL_FLG"));							//�폜�t���O
				return result;
			} else {
				throw new NoDataFoundException(
					"�����@�֏��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�����@�֒S����ID'"
						+ primaryKeys.getShozokuTantoId()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�����@�֏��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	/**
	 * �����@�֏��̐����擾����B�폜�t���O���u0�v�̏ꍇ�̂݌�������B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						�����@�֏��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 */
	public int countShozokuInfo(
		Connection connection,
		String shozokuCd)
		throws DataAccessException {
		String query =
			"SELECT COUNT(*)"
				+ " FROM SHOZOKUTANTOINFO"
				+ " WHERE SHOZOKU_CD = ?"
				+ " AND DEL_FLG = 0";//�폜�t���O
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShozokuInfo result = new ShozokuInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, shozokuCd);
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("�����@�֏��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * �����@�֏���o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^���鏊���@�֏��
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertShozokuInfo(
		Connection connection,
		ShozokuInfo addInfo)
		throws DataAccessException, DuplicateKeyException {
			
		//�d���`�F�b�N
		try {
			selectShozokuInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO SHOZOKUTANTOINFO "
				+ "(SHOZOKUTANTO_ID"		//�����@�֒S����ID
				+ ",SHOZOKU_CD"				//�����@�֖��i�R�[�h�j
				+ ",SHOZOKU_NAME_KANJI"		//�����@�֖��i���{��j
				+ ",SHOZOKU_RYAKUSHO"		//�����@�֖��i���́j
				+ ",SHOZOKU_NAME_EIGO"		//�����@�֖��i�p���j
				+ ",SHUBETU_CD"				//�@�֎��				
				+ ",PASSWORD"				//�p�X���[�h
				+ ",SEKININSHA_NAME_SEI"	//�ӔC�Ҏ����i���j
				+ ",SEKININSHA_NAME_MEI"	//�ӔC�Ҏ����i���j
				+ ",SEKININSHA_YAKU"		//�ӔC�Җ�E
				+ ",BUKYOKU_NAME"			//�S�����ۖ�
				+ ",KAKARI_NAME"			//�S���W��
				+ ",TANTO_NAME_SEI"			//�S���Җ��i���j
				+ ",TANTO_NAME_MEI"			//�S���Җ��i���j
				+ ",TANTO_TEL"				//�S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
				+ ",TANTO_FAX"				//�S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
				+ ",TANTO_EMAIL"			//�S���ҕ��Ǐ��ݒn�iEmail�j
				+ ",TANTO_EMAIL2"			//�S���ҕ��Ǐ��ݒn�iEmail2�j
				+ ",TANTO_ZIP"				//�S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
				+ ",TANTO_ADDRESS"			//�S���ҕ��Ǐ��ݒn�i�Z���j		
				+ ",NINSHOKEY_FLG"			//�F�؃L�[���s�t���O
				+ ",BIKO"					//���l
				+ ",YUKO_DATE"				//�L������
				//2005.08.10 iso ID���s���t�̒ǉ�
				+ ",ID_DATE"				//ID���s���t
//				2005/04/20 �ǉ� ��������-------------------------------------
//				���R �u���ǒS���Ґl���v���ڒǉ�
				+ ",BUKYOKU_NUM"			//���ǒS���Ґl��
//				�ǉ� �����܂�------------------------------------------------
				+ ",DEL_FLG) "				//�폜�t���O
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuTantoId());		//�����@�֒S����ID
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuCd());			//�����@�֖��i�R�[�h�j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());			//�����@�֖��i���{��j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuRyakusho());		//�����@�֖��i���́j			
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuNameEigo());		//�����@�֖��i�p���j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShubetuCd());			//�@�֎��
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getPassword());				//�p�X���[�h
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSekininshaNameSei());	//�ӔC�Ҏ����i���j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSekininshaNameMei());	//�ӔC�Ҏ����i���j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSekininshaYaku());		//�ӔC�Җ�E
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuName());			//�S�����ۖ�
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKakariName());			//�S���W��
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoNameSei());			//�S���Җ��i���j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoNameMei());			//�S���Җ��i���j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoTel());				//�S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoFax());				//�S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoEmail());			//�S���ҕ��Ǐ��ݒn�iEmail�j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoEmail2());			//�S���ҕ��Ǐ��ݒn�iEmail2�j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoZip());				//�S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoAddress());			//�S���ҕ��Ǐ��ݒn�i�Z���j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNinshokeyFlg());			//�F�؃L�[���s�t���O
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());					//���l
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYukoDate());				//�L������
			//2005.08.10 iso ID���s���t�̒ǉ�
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getIdDate());				//ID���s���t
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuNum());			//���ǒS���Ґl��
			DatabaseUtil.setParameter(preparedStatement, i++, 0);								//�폜�t���O
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("�����@�֏��o�^���ɗ�O���������܂����B ", ex);
			throw new DataAccessException("�����@�֏��o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * �����@�֏����X�V����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�X�V���鏊���@�֏��
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException		�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateShozokuInfo(
		Connection connection,
		ShozokuInfo updateInfo)
		throws DataAccessException, NoDataFoundException {

		//����
		selectShozokuInfo(connection, updateInfo);

		String query =
			"UPDATE SHOZOKUTANTOINFO"
				+ " SET"	
				+ " SHOZOKU_CD = ?"				//�����@�֖��i�R�[�h�j
				+ ",SHOZOKU_NAME_KANJI = ?"		//�����@�֖��i���{��j
				+ ",SHOZOKU_RYAKUSHO = ?"		//�����@�֖��i���́j
				+ ",SHOZOKU_NAME_EIGO = ?"		//�����@�֖��i�p���j
				+ ",SHUBETU_CD = ?"				//�@�֎��
				+ ",PASSWORD = ?"				//�p�X���[�h
				+ ",SEKININSHA_NAME_SEI = ?"	//�ӔC�Ҏ����i���j
				+ ",SEKININSHA_NAME_MEI = ?"	//�ӔC�Ҏ����i���j
				+ ",SEKININSHA_YAKU = ?"		//�ӔC�Җ�E
				+ ",BUKYOKU_NAME = ?"			//�S�����ۖ�
				+ ",KAKARI_NAME = ?"			//�S���W��
				+ ",TANTO_NAME_SEI = ?"			//�S���Җ��i���j
				+ ",TANTO_NAME_MEI = ?"			//�S���Җ��i���j
				+ ",TANTO_TEL = ?"				//�S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
				+ ",TANTO_FAX = ?"				//�S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
				+ ",TANTO_EMAIL = ?"			//�S���ҕ��Ǐ��ݒn�iEmail�j
				+ ",TANTO_EMAIL2 = ?"			//�S���ҕ��Ǐ��ݒn�iEmail2�j
				+ ",TANTO_ZIP = ?"				//�S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
				+ ",TANTO_ADDRESS = ?"			//�S���ҕ��Ǐ��ݒn�i�Z���j
				+ ",NINSHOKEY_FLG = ?"			//�F�؃L�[���s�t���O
				+ ",BIKO = ?"					//���l
				+ ",YUKO_DATE = ?"				//�L������
				//2005.08.10 iso ID���s���t�̒ǉ�
				+ ",ID_DATE = ?"				//ID���s���t
//				2005/04/20 �ǉ� ��������-------------------------------------
//				���R �u���ǒS���Ґl���v���ڒǉ�
				+ ",BUKYOKU_NUM = ?"			//���ǒS���Ґl��
//				�ǉ� �����܂�------------------------------------------------
				+ ",DEL_FLG = ?"				//�폜�t���O				
				+ " WHERE"
				+ " SHOZOKUTANTO_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());			//�����@�֖��i�R�[�h�j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());		//�����@�֖��i���{��j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuRyakusho());	//�����@�֖��i���́j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuNameEigo());	//�����@�֖��i�p���j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShubetuCd());			//�@�֎��
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getPassword());			//�p�X���[�h
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSekininshaNameSei());	//�ӔC�Ҏ����i���j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSekininshaNameMei());	//�ӔC�Ҏ����i���j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSekininshaYaku());	//�ӔC�Җ�E
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());		//�S�����ۖ�
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKakariName());		//�S���W��
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoNameSei());		//�S���Җ��i���j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoNameMei());		//�S���Җ��i���j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoTel());			//�S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoFax());			//�S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoEmail());		//�S���ҕ��Ǐ��ݒn�iEmail�j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoEmail2());		//�S���ҕ��Ǐ��ݒn�iEmail2�j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoZip());			//�S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoAddress());		//�S���ҕ��Ǐ��ݒn�i�Z���j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNinshokeyFlg());		//�F�؃L�[���s�t���O
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());				//���l
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getYukoDate());			//�L������
			//2005.08.10 iso ID���s���t�̒ǉ�
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getIdDate());			//ID���s���t
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuNum());		//���ǒS���Ґl��
			DatabaseUtil.setParameter(preparedStatement, i++, updateInfo.getDelFlg());			//�폜�t���O
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuTantoId());	//�����@�֒S����ID
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("�����@�֏��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * �����@�֏����폜����B(�폜�t���O) 
	 * @param connection			�R�l�N�V����
	 * @param addInfo				�폜���鏊���@�֎�L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteFlgShozokuInfo(
		Connection connection,
		ShozokuPk deleteInfo)
		throws DataAccessException, NoDataFoundException {

		//����
		selectShozokuInfo(connection, deleteInfo);
		
		String query =
			"UPDATE SHOZOKUTANTOINFO"
				+ " SET"
				+ " DEL_FLG = 1"//�폜�t���O				
				+ " WHERE"
				+ " SHOZOKUTANTO_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getShozokuTantoId());//�����@�֒S����ID
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {

			throw new DataAccessException("�����@�֏��폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * ���������B
	 * �����@�֏����폜����B(�����폜) 
	 * @param connection			�R�l�N�V����
	 * @param addInfo				�폜���鏊���@�֎�L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteShozokuInfo(
		Connection connection,
		ShozokuPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
	}
	
	
	
	/**
	 * ���[�UID�A�p�X���[�h�̔F�؂��s���B
	 * @param connection		�R�l�N�V����
	 * @param userid			���[�UID
	 * @param password			�p�X���[�h
	 * @return					�F�؂ɐ��������ꍇ true �ȊO false
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 */
	public boolean authenticateShozokuInfo(
		Connection connection, String userid, String password)
		throws DataAccessException {
		String query =
			"SELECT count(*)"
				+ " FROM SHOZOKUTANTOINFO"
				+ " WHERE DEL_FLG = 0"
				+ " AND SHOZOKUTANTO_ID = ?"
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
			if(count > 0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�����@�֏��e�[�u���������s���ɗ�O���������܂����B ", ex);
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
	public boolean changePasswordShozokuInfo(
		Connection connection,
		ShozokuPk pkInfo,
		String newPassword)
		throws DataAccessException {

		String query =
			"UPDATE SHOZOKUTANTOINFO"
				+ " SET"
				+ " PASSWORD = ?"
				+ " WHERE"
				+ " SHOZOKUTANTO_ID = ?"
				+ " AND DEL_FLG = 0";//�폜�t���O

		PreparedStatement preparedStatement = null;
			
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++, newPassword);//�V�����p�X���[�h
			DatabaseUtil.setParameter(preparedStatement,i++, pkInfo.getShozokuTantoId());//�����@�֒S����ID
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("�p�X���[�h�ύX���ɗ�O���������܂����B ", ex);

		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
		return true;
	}
	
	
	
    /**
	 * �����@�֖��̏��Ԃ��擾����B
	 * 
	 * @param connection         	�R�l�N�V����
	 * @param shozokuCd          	�����@�փR�[�h
	 * @return 					����(2��)
	 * @throws DataAccessException �f�[�^�x�[�X�A�N�Z�X���̗�O
	 */
	public String getSequenceNo(Connection connection, String shozokuCd)
			throws DataAccessException, ApplicationException
	{
		String query =
			 "SELECT TO_CHAR(MAX(SUBSTR(SHOZOKUTANTO_ID,6,2)) + 1,'FM00') COUNT"
				+ " FROM SHOZOKUTANTOINFO"
				+ " WHERE SHOZOKU_CD = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, shozokuCd);
			recordSet = preparedStatement.executeQuery();

			String ret = null;
			if (recordSet.next()) {
				ret = recordSet.getString(1);
				if (ret == null) {
					ret = "01";
				}
			}

			//2005/07/14 �A�Ԃ�10�ȏ㎞�A�G���[�Ƃ���
			if (Integer.parseInt(ret) > 9){
				throw new ApplicationException("�����@�֒S����ID�̘A�Ԃ�09�𒴂��܂����B", 
								new ErrorInfo("errors.4001")
						);
			}

			return ret;

		} catch (SQLException ex) {
			throw new DataAccessException("�����@�֏��e�[�u���������s���ɗ�O���������܂����B", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
     
     
     
	/**
	 * �����S���ҏ����X�V����B
	 * �X�V������͈ȉ��̒ʂ�B <br>
	 * <li>�����@�֖��i�a���j</li>
	 * <li>�����@�֖��i���́j</li>
	 * �i�������@�֖��i�p���j�ɂ��Ă͍X�V���Ȃ��B�j <br>
	 * 
	 * @param connection             �R�l�N�V����
	 * @param updateInfo             �����@�֏��
	 * @throws DataAccessException   �X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException  �Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateShozokuInfo(
		Connection connection,
		KikanInfo updateInfo)
		throws DataAccessException, NoDataFoundException {
	
		String query =
			"UPDATE SHOZOKUTANTOINFO"
				+ " SET"
				+ " SHOZOKU_NAME_KANJI = ?"
				+ ",SHOZOKU_RYAKUSHO = ?"
				+ " WHERE"
				+ "  SHOZOKU_CD = ?"			//�@�փR�[�h��������...
				+ " AND ( "						//���A
				+ "  SHOZOKU_NAME_KANJI <> ?"	//�@�֖��i�a���j���Ⴄ��
				+ "  OR "						//�܂���
				+ "  SHOZOKU_RYAKUSHO <> ?"		//�@�֖��i���́j���Ⴄ����
				+ " ) "
				;

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuNameKanji());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuRyakusho());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuNameKanji());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuRyakusho());
			preparedStatement.executeUpdate();		//�X�V�����́A0���`�������ł��邽��

		} catch (SQLException ex) {
			throw new DataAccessException("�����S���ҏ��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * �����@�֏�����������B
	 * 
	 * �����@�֒S���҂͍폜�t���O���u0�v�̃f�[�^�A���ǒS���҂͑S�Ă�ΏۂƂ���B
	 * 
	 * @param connection
	 * @param primaryKeys
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 */
	public Page selectShozokuAndBukyokuTanto(UserInfo userInfo, ShozokuSearchInfo searchInfo)
		throws ApplicationException {
		
		String sql=
			"SELECT"
			+ " INFO.SHOZOKU_CD"						//�����@�֖��i�R�[�h�j
			+ " ,INFO.SHOZOKU_RYAKUSHO"					//�����@�֖��i���́j
			+ " ,INFO.TANTO_ID"							//�S����ID�i�����@�ց^���ǁj
			+ " ,INFO.TANTO_NAME_SEI"					//�S���Җ��i���j
			+ " ,INFO.TANTO_NAME_MEI"					//�S���Җ��i���j
			+ " ,INFO.DEL_FLG"							//�폜�t���O
			+ " ,INFO.TANTO_FLG"						//�S���t���O�i0�F�����@�ց@1�F���ǁj
			+ " FROM("
				+ " SELECT"
					+ " S.SHOZOKU_CD"
					+ " ,S.SHOZOKU_RYAKUSHO"
					+ " ,S.SHOZOKUTANTO_ID AS TANTO_ID"
					+ " ,S.TANTO_NAME_SEI"
					+ " ,S.TANTO_NAME_MEI"
					+ " ,S.DEL_FLG"
					+ " ,0 AS TANTO_FLG"				//�S���t���O�u0�v
					+ " ,S.SHUBETU_CD"					//�@�֎�ʁi�����p�j
					+ " ,S.SHOZOKU_NAME_KANJI"			//�����@�֖��i�����p�j
				+ " FROM SHOZOKUTANTOINFO S"			//�����@�֒S���ҏ��e�[�u��
				+ " WHERE DEL_FLG = 0"
				+ " UNION"
				+ " SELECT"
					+ " B.SHOZOKU_CD"
					+ " ,S2.SHOZOKU_RYAKUSHO"
					+ " ,B.BUKYOKUTANTO_ID AS TANTO_ID"
					+ " ,B.TANTO_NAME_SEI"
					+ " ,B.TANTO_NAME_MEI"
					+ " ,B.DEL_FLG"
					+ " ,1 AS TANTO_FLG"				//�S���t���O�u1�v
					+ " ,S2.SHUBETU_CD"					//�@�֎�ʁi�����p�j
					+ " ,S2.SHOZOKU_NAME_KANJI"			//�����@�֖��i�����p�j
				+ " FROM BUKYOKUTANTOINFO B"			//���ǒS���ҏ��e�[�u��
					+ " INNER JOIN SHOZOKUTANTOINFO S2"		//�����@�փ}�X�^
					+ " ON B.SHOZOKU_CD = S2.SHOZOKU_CD"
					+ " AND S2.DEL_FLG = 0"
				+ " WHERE REGIST_FLG = 1"
			+ " ) INFO"
			+ " WHERE SHOZOKU_CD IS NOT NULL";
		
		StringBuffer query = new StringBuffer(sql);
		
		if(searchInfo.getShubetuCd() != null && !searchInfo.getShubetuCd().equals("")){			//�@�֎�ʃR�[�h�i���S��v�j
			query.append(" AND SHUBETU_CD = " + EscapeUtil.toSqlString(searchInfo.getShubetuCd()) );
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){			//�����@�֖��i�R�[�h�j�i���S��v�j
			query.append(" AND SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){		//�����@�֖��i���{��j�i������v�j
			query.append(" AND SHOZOKU_NAME_KANJI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");			
		}
		if(searchInfo.getShozokuTantoId() != null && !searchInfo.getShozokuTantoId().equals("")){	//�S����ID�i���S��v�j
			query.append(" AND TANTO_ID = '" + EscapeUtil.toSqlString(searchInfo.getShozokuTantoId()) + "'");
		}
		if(searchInfo.getTantoNameSei() != null && !(searchInfo.getTantoNameSei()).equals("")){	//�S���Җ��i���j�i������v�j
			query.append(" AND TANTO_NAME_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameSei()) + "%'");			
		}
		if(searchInfo.getTantoNameMei() != null && !(searchInfo.getTantoNameMei()).equals("")){	//�S���Җ��i���j�i������v�j
			query.append(" AND TANTO_NAME_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameMei()) + "%'");			
		}
		if(searchInfo.getBukyokuTantoId() != null && !(searchInfo.getBukyokuTantoId()).equals("")){	//���ǒS����ID�i���S��v�j
			query.append(" AND TANTO_ID = '" + EscapeUtil.toSqlString(searchInfo.getBukyokuTantoId()) + "'");
		}
		if(searchInfo.getBukyokuSearchFlg() != null && searchInfo.getBukyokuSearchFlg().equals("1")){
			query.append(" AND DEL_FLG = 1");
		}

		//2005/04/26 �ǉ� -------------------------------------------------------------------------------��������
		//���R �����@�֒S���Җ����̏����@�֒S���ҏ��Ɋւ���i���݂̍ۂɁA���ǒS���ҏ�񂪌�������Ȃ��悤�ɏC��
		//���ǒS����ID�����������ɃZ�b�g�����ꍇ�͕��ǒS���҂̏��̂ݎ擾
		if(searchInfo.getBukyokuTantoId() != null && !(searchInfo.getBukyokuTantoId()).equals("")){
			query.append(" AND TANTO_FLG = 1");
		}
		//�����@�֒S����ID�A�S���Җ������������ɃZ�b�g�����ꍇ�͏����@�֒S���҂̏��̂ݎ擾
		if((searchInfo.getShozokuTantoId() != null && !searchInfo.getShozokuTantoId().equals(""))
				||(searchInfo.getTantoNameSei() != null && !(searchInfo.getTantoNameSei()).equals(""))
				||(searchInfo.getTantoNameMei() != null && !(searchInfo.getTantoNameMei()).equals(""))){
			query.append(" AND TANTO_FLG = 0");
		}
		//2005/04/26 �ǉ� -------------------------------------------------------------------------------�����܂�
		
		
		//�����@�փR�[�h�A�S���t���O�A�S����ID�Ń\�[�g
		query.append(" ORDER BY SHOZOKU_CD,TANTO_FLG,TANTO_ID");		
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// �y�[�W�擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection, searchInfo, query.toString());
		} catch (DataAccessException e) {
			log.error("�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	   
}
