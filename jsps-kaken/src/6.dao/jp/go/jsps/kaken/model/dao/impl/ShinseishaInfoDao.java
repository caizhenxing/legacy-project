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
import java.util.*;

import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * �\���ҏ��f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: ShinseishaInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class ShinseishaInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** �\���ҏ��Ǘ��V�[�P���X�� */
	public static final String SEQ_SHINSEISHAINFO = "SEQ_SHINSEISHAINFO";
	
	/** �����@�֏��Ǘ��V�[�P���X�̎擾�����i�A�ԗp�j */
	public static final int SEQ_FIGURE = 4;

	/** ���O */
	protected static final Log log = LogFactory.getLog(ShinseishaInfoDao.class);

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
	public ShinseishaInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * �L�[�Ɉ�v����\���ҏ����擾����B
	 * �����҃}�X�^�ɑ��݂��Ȃ�����҂͏��O����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						�\���ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public ShinseishaInfo selectShinseishaInfo(
		Connection connection,
		ShinseishaPk primaryKeys)
		throws DataAccessException, NoDataFoundException {
		//�����҃}�X�^�ɑ��݂��Ȃ�����҂͏��O����B
		return selectShinseishaInfo(connection, primaryKeys, true);
	}


	/**
	 * �L�[�Ɉ�v����\���ҏ����擾����B
	 * 
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @param masterCheck			�����҃}�X�^�ɓo�^����Ă�����̂̂�[true]
	 * 								�����҃}�X�^�Ɋ֌W����[false]
	 * @return						�\���ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public ShinseishaInfo selectShinseishaInfo(
		Connection connection,
		ShinseishaPk primaryKeys,
		boolean masterCheck)
		throws DataAccessException, NoDataFoundException {
		
		//2005/09/12 �����҃}�X�^�Ƃ̌�������
		String innerMaster = "";
//add start dyh 2006/2/14
		String innerColumnNm = "";
//add end dyh 2006/2/14
		if(masterCheck){
			innerMaster = " INNER JOIN MASTER_KENKYUSHA MK "
						 +" ON  A.KENKYU_NO  = MK.KENKYU_NO "
						 +" AND A.SHOZOKU_CD = MK.SHOZOKU_CD "
						 +" AND MK.DEL_FLG = 0 "
						 ;
//add start dyh 2006/2/14
			innerColumnNm = ",MK.OUBO_SHIKAKU";
//add end dyh 2006/2/14
		}
		
		String query =
			"SELECT "
				+ " A.SHINSEISHA_ID"
				+ " ,A.SHOZOKU_CD"
				+ " ,NVL(A.SHOZOKU_NAME,'') SHOZOKU_NAME"
				+ " ,NVL(A.SHOZOKU_NAME_EIGO,'') SHOZOKU_NAME_EIGO"
				+ " ,NVL(A.SHOZOKU_NAME_RYAKU,'') SHOZOKU_NAME_RYAKU"
				+ " ,A.PASSWORD"
				+ " ,NVL(A.NAME_KANJI_SEI,'') NAME_KANJI_SEI"
				+ " ,NVL(A.NAME_KANJI_MEI,'') NAME_KANJI_MEI"
				+ " ,NVL(A.NAME_KANA_SEI,'') NAME_KANA_SEI"
				+ " ,NVL(A.NAME_KANA_MEI,'') NAME_KANA_MEI"
				+ " ,NVL(A.NAME_RO_SEI,'') NAME_RO_SEI"
				+ " ,NVL(A.NAME_RO_MEI,'') NAME_RO_MEI"
				+ " ,A.BUKYOKU_CD"
				+ " ,NVL(A.BUKYOKU_NAME,'') BUKYOKU_NAME"
				+ " ,NVL(A.BUKYOKU_NAME_RYAKU,'') BUKYOKU_NAME_RYAKU"
//				+ " ,NVL(A.SHUBETU_CD,'') SHUBETU_CD"
//				+ " ,NVL(A.OTHER_BUKYOKU,'') OTHER_BUKYOKU"
				+ " ,A.KENKYU_NO"
				+ " ,A.SHOKUSHU_CD"
				+ " ,NVL(A.SHOKUSHU_NAME_KANJI,'') SHOKUSHU_NAME_KANJI"
				+ " ,NVL(A.SHOKUSHU_NAME_RYAKU,'') SHOKUSHU_NAME_RYAKU"
				+ " ,A.HIKOBO_FLG"
				+ " ,NVL(A.BIKO,'') BIKO"
				+ " ,A.YUKO_DATE"
				+ " ,A.DEL_FLG"
//add start dyh 2006/02/14
				+ innerColumnNm
//add end dyh 2006/02/14
				//	2005/03/28 �ǉ� ��������---------------------------------
				//	���R ���N������\�����邽��
				+ " ,A.BIRTHDAY"
				//	�ǉ� �����܂�--------------------------------------------
				
				//2005.10.06 iso ���s��ID�E���s�ғ��t��ǉ�
				+ ", A.HAKKOSHA_ID"
				+ " ,A.HAKKO_DATE"
				
				+ " FROM SHINSEISHAINFO A"
				+ innerMaster
				+ " WHERE SHINSEISHA_ID = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShinseishaInfo result = new ShinseishaInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,primaryKeys.getShinseishaId());
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setShinseishaId(recordSet.getString("SHINSEISHA_ID"));
				result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
				result.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
				result.setShozokuNameEigo(recordSet.getString("SHOZOKU_NAME_EIGO"));
				result.setShozokuNameRyaku(recordSet.getString("SHOZOKU_NAME_RYAKU"));
				result.setPassword(recordSet.getString("PASSWORD"));
				result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));
				result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));
				result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
				result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
				result.setNameRoSei(recordSet.getString("NAME_RO_SEI"));
				result.setNameRoMei(recordSet.getString("NAME_RO_MEI"));
				result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));
				result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
				result.setBukyokuNameRyaku(recordSet.getString("BUKYOKU_NAME_RYAKU"));
//				result.setBukyokuShubetuCd(recordSet.getString("SHUBETU_CD"));
//				result.setBukyokuShubetuName(recordSet.getString("OTHER_BUKYOKU"));
				result.setKenkyuNo(recordSet.getString("KENKYU_NO"));
				result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));
				result.setShokushuNameKanji(recordSet.getString("SHOKUSHU_NAME_KANJI"));
				result.setShokushuNameRyaku(recordSet.getString("SHOKUSHU_NAME_RYAKU"));
				result.setHikoboFlg(recordSet.getString("HIKOBO_FLG"));
				result.setBiko(recordSet.getString("BIKO"));
				result.setDelFlg(recordSet.getString("DEL_FLG"));
				result.setYukoDate(recordSet.getDate("YUKO_DATE"));
				//	2005/03/28 �ǉ� ��������---------------------------------
				//	���R ���N������\�����邽��
				result.setBirthday(recordSet.getDate("BIRTHDAY"));				
				//	�ǉ� �����܂�--------------------------------------------	
				
				//2005.10.06 iso ���s��ID�E���s�ғ��t��ǉ�		
				result.setHakkoshaId(recordSet.getString("HAKKOSHA_ID"));	
				result.setHakkoDate(recordSet.getDate("HAKKO_DATE"));	
//				2006/02/09 �ǉ� ��������---------------------------------
				result.setOuboshikaku(recordSet.getString("OUBO_SHIKAKU"));
					
				return result;
			} else {
				throw new NoDataFoundException(
					"�\���ҏ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�\����ID'"
						+ primaryKeys.getShinseishaId()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�\���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * �\���ҏ��̐����擾����B�폜�t���O���u0�v�̏ꍇ�̂݌�������B
	 * @param connection			�R�l�N�V����
	 * @param searchInfo			�\���ҏ��
	 * @return						�\���ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 */
	public int countShinseishaInfo(
		Connection connection,
		ShinseishaInfo searchInfo)
		throws DataAccessException {
		String query =
			"SELECT COUNT(*)"
				+ " FROM SHINSEISHAINFO"
				+ " WHERE SHINSEISHA_ID <> ?"
				+ " AND SHOZOKU_CD = ?"
				+ " AND KENKYU_NO = ?"
//				+ " AND SHOZOKU_NAME = ?"
//				+ " AND NAME_KANJI_SEI = ?"
//				+ " AND NAME_KANJI_MEI = ?"
//				+ " AND BUKYOKU_CD = ?"
//				+ " AND SHUBETU_CD = ?"
//				+ " AND SHOKUSHU_CD = ?"
				+ " AND DEL_FLG = 0";		//�폜�t���O
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShinseishaInfo result = new ShinseishaInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			if(searchInfo.getShinseishaId() != null && !searchInfo.getShinseishaId().equals("")) {
				DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShinseishaId());
			} else {
				DatabaseUtil.setParameter(preparedStatement,i++,"0");
			}
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getKenkyuNo());
//			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShozokuName());
//			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getNameKanjiSei());
//			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getNameKanjiMei());
//			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getBukyokuCd());
//			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getBukyokuShubetuCd());
//			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShokushuCd());
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("�\���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * �\���ҏ���o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^����\���ҏ��
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertShinseishaInfo(
		Connection connection,
		ShinseishaInfo addInfo)
		throws DataAccessException, DuplicateKeyException {
			
		//�d���`�F�b�N
		try {
			//�����҃}�X�^�Ɋ֌W�����d�����R�[�h���`�F�b�N����
			selectShinseishaInfo(connection, addInfo,false);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}
		
		String query =
			"INSERT INTO SHINSEISHAINFO"
				+ " (SHINSEISHA_ID"
				+ ", SHOZOKU_CD"
				+ ", SHOZOKU_NAME"
				+ ", SHOZOKU_NAME_EIGO"
				+ ", SHOZOKU_NAME_RYAKU"
				+ ", PASSWORD"
				+ ", NAME_KANJI_SEI"
				+ ", NAME_KANJI_MEI"
				+ ", NAME_KANA_SEI"
				+ ", NAME_KANA_MEI"
				+ ", NAME_RO_SEI"
				+ ", NAME_RO_MEI"
				+ ", BUKYOKU_CD"
				+ ", BUKYOKU_NAME"
				+ ", BUKYOKU_NAME_RYAKU"
//				+ ", SHUBETU_CD"
//				+ ", OTHER_BUKYOKU"
				+ ", KENKYU_NO"
				+ ", SHOKUSHU_CD"
				+ ", SHOKUSHU_NAME_KANJI"
				+ ", SHOKUSHU_NAME_RYAKU"
				+ ", HIKOBO_FLG"
				+ ", BIKO"
				+ ", YUKO_DATE"
// 2005/03/31 �ǉ� ��������----------------------------------------
// ���R DB���ڒǉ��ɂ��
				+ ", BIRTHDAY"			//���N����
				+ ", HAKKOSHA_ID"		//���s��ID
				+ ", HAKKO_DATE"		//���s��
// �ǉ� �����܂�---------------------------------------------------
				+ ", DEL_FLG)"
				+ " VALUES"
				+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?)";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinseishaId());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuNameEigo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuNameRyaku());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getPassword());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameRoSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameRoMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuNameRyaku());
//			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuShubetuCd());
//			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuShubetuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuNameKanji());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuNameRyaku());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHikoboFlg());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYukoDate());
			
//			 2005/03/31 �ǉ� ��������----------------------------------------
//			 ���R DB���ڒǉ��ɂ��u���N�����v�u���s��ID�v�@�i�u���s���v��SYSDATE�@�j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBirthday());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHakkoshaId());
//			 �ǉ� �����܂�---------------------------------------------------
			
			DatabaseUtil.setParameter(preparedStatement, i++, 0);
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("�\���ҏ��o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * �\���ҏ����X�V����B
	 * @param connection			�R�l�N�V����
	 * @param addInfo				�X�V����\���ҏ��
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateShinseishaInfo(
		Connection connection,
		ShinseishaInfo updateInfo)
		throws DataAccessException, NoDataFoundException {
			
		//����
		selectShinseishaInfo(connection, updateInfo);
	
		String query =
			"UPDATE SHINSEISHAINFO"
				+ " SET"
				+ " SHOZOKU_CD = ?"
				+ ", SHOZOKU_NAME = ?"
				+ ", SHOZOKU_NAME_EIGO = ?"
				+ ", SHOZOKU_NAME_RYAKU = ?"
				+ ", PASSWORD = ?"
				+ ", NAME_KANJI_SEI = ?"
				+ ", NAME_KANJI_MEI = ?"
				+ ", NAME_KANA_SEI = ?"
				+ ", NAME_KANA_MEI = ?"
				+ ", NAME_RO_SEI = ?"
				+ ", NAME_RO_MEI = ?"
				+ ", BUKYOKU_CD = ?"
				+ ", BUKYOKU_NAME= ?"
				+ ", BUKYOKU_NAME_RYAKU= ?"
//				+ ", SHUBETU_CD = ?"
//				+ ", OTHER_BUKYOKU = ?"
				+ ", KENKYU_NO = ?"
				+ ", SHOKUSHU_CD = ?"
				+ ", SHOKUSHU_NAME_KANJI = ?"
				+ ", SHOKUSHU_NAME_RYAKU = ?"
				+ ", HIKOBO_FLG = ?"
				+ ", BIKO = ?"
				+ ", YUKO_DATE = ?"
				+ ", DEL_FLG = ?";
		//2005/04/18 �ǉ� ��������----------------------------------------------------------
		//���R�@�����ҏ��C����폜���̑Ή��̂���
		if(updateInfo.getBirthday() != null){
				query += ", BIRTHDAY = ?";
		}
		if(updateInfo.getHakkoshaId() != null){
				query += ", HAKKOSHA_ID = ?";
		}
		if(updateInfo.getHakkoDate() != null){
    			query += ", HAKKO_DATE = ?";
		}
		//�ǉ� �����܂�---------------------------------------------------------------------
		
		query = query
					+ " WHERE"
					+ " SHINSEISHA_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuNameEigo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuNameRyaku());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getPassword());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameRoSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameRoMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuNameRyaku());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuShubetuCd());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuShubetuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuNameKanji());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuNameRyaku());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHikoboFlg());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getYukoDate());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDelFlg());
			//2005/04/18 �ǉ� ��������----------------------------------------------------------
			//���R�@�����ҏ��C����폜���̑Ή��̂���
			if(updateInfo.getBirthday() != null){
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBirthday());
			}
			if(updateInfo.getHakkoshaId() != null){
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHakkoshaId());
			}
			if(updateInfo.getHakkoDate() != null){
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHakkoDate());
			}
			//�ǉ� �����܂�---------------------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinseishaId());
			
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("�\���ҏ��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * �\���ҏ����X�V����B�\���ҏ��e�[�u���Ɋ܂܂�鏊���@�֏����X�V����B<br>
	 * �폜�t���O�Ɋ֌W�Ȃ��A�����@�փR�[�h����v���郌�R�[�h�����ׂčX�V�B
	 * @param connection			�R�l�N�V����
	 * @param updateInfo			�����@�֒S���ҏ��
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateShinseishaInfo(
		Connection connection,
		ShozokuInfo updateInfo)
		throws DataAccessException, NoDataFoundException {
	
		String query =
			"UPDATE SHINSEISHAINFO"
				+ " SET"
				+ " SHOZOKU_NAME = ?"
				+ ",SHOZOKU_NAME_EIGO = ?"
				+ ",SHOZOKU_NAME_RYAKU = ?"
				+ " WHERE"
				+ " SHOZOKU_CD = ?"
				;

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuNameEigo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuRyakusho());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
			preparedStatement.executeUpdate();		//�X�V�����́A0���`�������ł��邽��

		} catch (SQLException ex) {
			throw new DataAccessException("�\���ҏ��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * �\���ҏ����X�V����B�\���ҏ��e�[�u���Ɋ܂܂�鏊���@�֏����X�V����B<br>
	 * �X�V������͈ȉ��̒ʂ�B<br>
	 * <li>�����@�֖��i�a���j</li>
	 * <li>�����@�֖��i���́j</li>
	 * �i�������@�֖��i�p���j�ɂ��Ă͍X�V���Ȃ��B�j<br>
	 * @param connection			�R�l�N�V����
	 * @param updateInfo			�����@�֏��
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateShinseishaInfo(
		Connection connection,
		KikanInfo updateInfo)
		throws DataAccessException, NoDataFoundException {
	
		String query =
			"UPDATE SHINSEISHAINFO"
				+ " SET"
				+ " SHOZOKU_NAME = ?"
				+ ",SHOZOKU_NAME_RYAKU = ?"
				+ " WHERE"
				+ "  SHOZOKU_CD = ?"			//�@�փR�[�h��������...
				+ " AND ( "						//���A
				+ "  SHOZOKU_NAME <> ?"			//�@�֖��i�a���j���Ⴄ��
				+ "  OR "						//�܂���
				+ "  SHOZOKU_NAME_RYAKU <> ?"	//�@�֖��i���́j���Ⴄ����
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
			throw new DataAccessException("�\���ҏ��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * �\���ҏ����폜����B(�폜�t���O) 
	 * @param connection			�R�l�N�V����
	 * @param addInfo				�폜����\���ҏ���L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteFlgShinseishaInfo(
		Connection connection,
		ShinseishaPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
			
			//����
			selectShinseishaInfo(connection, deleteInfo);
	
			String query =
				"UPDATE SHINSEISHAINFO"
					+ " SET"
					+ " HAKKOSHA_ID = ? "							//���s��ID
					+ ",HAKKO_DATE = sysdate"						//���s��
					+ ",DEL_FLG = 1"								//�폜�t���O
					+ " WHERE"
					+ " SHINSEISHA_ID = ?";

			PreparedStatement preparedStatement = null;
			try {
				//�o�^
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,userInfo.getId());
				DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getShinseishaId());
				DatabaseUtil.executeUpdate(preparedStatement);

			} catch (SQLException ex) {
				throw new DataAccessException("�\���ҏ��폜���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	

	/**
	 * �\���ҏ����폜����B(�����폜) 
	 * @param connection			�R�l�N�V����
	 * @param addInfo				�폜����\���ҏ���L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteShinseishaInfo(
		Connection connection,
		ShinseishaPk deleteInfo)
		throws DataAccessException, NoDataFoundException {
	}


	/**
	 * ���[�UID�A�p�X���[�h�̔F�؂��s���B
	 * @param connection			�R�l�N�V����
	 * @param userid				���[�UID
	 * @param password				�p�X���[�h
	 * @return						�F�؂ɐ��������ꍇ true �ȊO false
	 * @throws DataAccessException	�f�[�^�x�[�X�A�N�Z�X���̗�O
	 */
	public boolean authenticateShinseishaInfo(
		Connection connection,
		String userid,
		String password)
		throws DataAccessException {
		String query =
			"SELECT count(*)"
				+ " FROM SHINSEISHAINFO SHINSEI"
			//2005/04/26 �ǉ� ��������-------------------------------------		
			//���R �����҃}�X�^�Ə����@�փ}�X�^�̏����ǉ��̂���			
				+ " INNER JOIN MASTER_KENKYUSHA KENKYU"
				//2005.08.08 iso ���̑��R�[�h(99999)���͂����׃R�����g��
//				+ " INNER JOIN MASTER_KIKAN KIKAN"
//				+ " ON KIKAN.SHOZOKU_CD = KENKYU.SHOZOKU_CD"
				+ " ON KENKYU.SHOZOKU_CD = SHINSEI.SHOZOKU_CD"
				+ " AND KENKYU.KENKYU_NO = SHINSEI.KENKYU_NO"
			//�ǉ� �����܂�------------------------------------------------
				+ " WHERE SHINSEI.DEL_FLG = 0"
				+ " AND SHINSEI.SHINSEISHA_ID = ?"
				+ " AND SHINSEI.PASSWORD = ?"
			//2005/04/26 �ǉ� ��������-------------------------------------		
			//���R �����҃}�X�^�Ə����@�փ}�X�^�̏����ǉ��̂���
				+ " AND KENKYU.DEL_FLG = 0";
			//�ǉ� �����܂�------------------------------------------------
			
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		int count = 0;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,userid);
			DatabaseUtil.setParameter(preparedStatement,i++,password);
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
			throw new DataAccessException("�\���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
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
	public boolean changePasswordShinseishaInfo(
			Connection connection,
			ShinseishaPk pkInfo,
			String newPassword)
			throws DataAccessException {

		//2005.10.06 iso �\���Ҏ��g���p�X���[�h�ύX�����ꍇ�A���s��ID�E��������ύX���Ȃ��悤�C��
//		String query = "UPDATE SHINSEISHAINFO"
//						 + " SET"
//						 + " PASSWORD = ? "
//						 + ",HAKKOSHA_ID = ? "							//���s��ID
//						 + ",HAKKO_DATE = sysdate"						//���s��
//						 + " WHERE"
//						 + " SHINSEISHA_ID = ?"
//						 + " AND DEL_FLG = 0";							//�폜�t���O

		String addQuery = "";
		if(!UserRole.SHINSEISHA.equals(userInfo.getRole())) {
			addQuery = ",HAKKOSHA_ID = ? "							//���s��ID
					 + ",HAKKO_DATE = sysdate"						//���s��
					 ;
			
		}

		String query = "UPDATE SHINSEISHAINFO"
						 + " SET"
						 + " PASSWORD = ? "
						 + addQuery
						 + " WHERE"
						 + " SHINSEISHA_ID = ?"
						 + " AND DEL_FLG = 0";							//�폜�t���O

		PreparedStatement preparedStatement = null;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
	
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,newPassword);				//�V�����p�X���[�h
			
			//2005.10.06 iso �\���Ҏ��g���p�X���[�h�ύX�����ꍇ�A���s��ID�E��������ύX���Ȃ��悤�C��
			if(!UserRole.SHINSEISHA.equals(userInfo.getRole())) {
				DatabaseUtil.setParameter(preparedStatement, i++, userInfo.getId());		//���s��ID
			}
			
			DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getShinseishaId());	//�\����ID
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("�p�X���[�h�ύX���ɗ�O���������܂����B ", ex);

		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
		return true;
	}

	/**
     *�@�����@�֖��̏��Ԃ��擾����B
	 * @param connection           �R�l�N�V����
	 * @param shozokuCd            �����@�փR�[�h
	 * @return                     ����(5��)
	 * @throws DataAccessException�@�f�[�^�x�[�X�A�N�Z�X���̗�O
	 */
	public String getSequenceNo(Connection connection,String shozokuCd) throws DataAccessException {
		String query =
			"SELECT TO_CHAR(MAX(SUBSTR(SHINSEISHA_ID,8,5)) + 1,'FM00000') COUNT"
				+ " FROM SHINSEISHAINFO"
				+ " WHERE SHOZOKU_CD = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shozokuCd);
			recordSet = preparedStatement.executeQuery();
			String ret = null;
			 if (recordSet.next()) {
				ret= recordSet.getString(1);
				if(ret == null){
					ret = "00001";
				}
			 }
			 return ret;
		} catch (SQLException ex) {
			throw new DataAccessException("�\���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}


	/**
	 * ����剞��t���O���O���B
	 * @param connection			�R�l�N�V����
	 * @param searchInfo			�\���Ҍ������
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 */
	public void deleteHikoboFlgInfo(
			Connection connection,
			ShinseishaSearchInfo searchInfo)
			throws DataAccessException {

		String update = "UPDATE SHINSEISHAINFO S SET S.HIKOBO_FLG = 0 WHERE ";			
		
		StringBuffer query = new StringBuffer(update);

		if(!"".equals(searchInfo.getShinseishaId())) {
			query.append("S.SHINSEISHA_ID = '");
			query.append(EscapeUtil.toSqlString(searchInfo.getShinseishaId()));
			query.append("' AND ");
		}
		if(!"".equals(searchInfo.getNameKanjiSei())) {
			query.append("S.NAME_KANJI_SEI LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()));
			query.append("%' AND ");
		}
		if(!"".equals(searchInfo.getNameKanjiMei())) {
			query.append("S.NAME_KANJI_MEI LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()));
			query.append("%' AND ");
		}
		if(!"".equals(searchInfo.getNameKanaSei())) {
			query.append("S.NAME_KANA_SEI LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()));
			query.append("%' AND ");
		}
		if(!"".equals(searchInfo.getNameKanaMei())) {
			query.append("S.NAME_KANA_MEI LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()));
			query.append("%' AND ");
		}
		if(!"".equals(searchInfo.getNameRoSei())) {
			query.append("UPPER(S.NAME_RO_SEI) LIKE '%");						//�o����啶�������Č��������Ƃ��Ȃ��ƃG���[�ƂȂ�̂Œ���
			query.append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()));
			query.append("%' AND ");
		}
		if(!"".equals(searchInfo.getNameRoMei())) {
			query.append("UPPER(S.NAME_RO_MEI) LIKE '%");						//�o����啶�������Č��������Ƃ��Ȃ��ƃG���[�ƂȂ�̂Œ���
			query.append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()));
			query.append("%' AND ");
		}
		if(!"".equals(searchInfo.getShozokuCd())) {
			query.append("S.SHOZOKU_CD = '");
			query.append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()));
			query.append("' AND ");
		}
		if(!"".equals(searchInfo.getShozokuName())) {
			query.append("(S.SHOZOKU_NAME LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getShozokuName()));
			query.append("%' OR S.SHOZOKU_NAME_RYAKU LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getShozokuName()));
			query.append("%') AND ");
		}
		query.append("S.DEL_FLG = 0");
		
		//2005/09/12 �����҃}�X�^�ɑ��݂��Ȃ��ꍇ�͏��O����
		query.append(" AND S.KENKYU_NO IN ")
 			 .append(" (SELECT MK.KENKYU_NO FROM MASTER_KENKYUSHA MK ") 
			 .append("  WHERE MK.KENKYU_NO  = S.KENKYU_NO ")
			 .append("  AND   MK.SHOZOKU_CD = S.SHOZOKU_CD ")
			 .append("  AND   MK.DEL_FLG = 0) ")
			 ;

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query.toString());
			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			throw new DataAccessException("�����t���O�폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	//2005/04/18 �ǉ� ��������------------------------------------------------------------
	//���R �����҂̍X�V�E�폜���ɐ\���ҏ����X�V���邽��
	
	public String selectKenkyusha(Connection connection, KenkyushaPk pk)
		throws DataAccessException{
			
		String ret = null;
		String query =
			"SELECT SHINSEISHA_ID "
				+ " FROM SHINSEISHAINFO"
				+ " WHERE KENKYU_NO = ?" 
				+ " AND SHOZOKU_CD = ?";
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		String kenkyuNo = pk.getKenkyuNo();
		String shozokuCd = pk.getShozokuCd();
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,kenkyuNo);
			DatabaseUtil.setParameter(preparedStatement,i++,shozokuCd);
			recordSet = preparedStatement.executeQuery();
			
			 if (recordSet.next()) {
				ret= recordSet.getString(1);
			 }
		} catch (SQLException ex) {
			throw new DataAccessException("�\���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return ret;
	}
	
	/**
	 * ID�p�X���[�h�ʒm���pCSV�f�[�^��DB���擾����B�i�����ҊǗ��p�j
	 * @param connection
	 * @param kenkyuNo �o�^�����������ԍ�
	 * @param sozokuCd �����@�փR�[�h
	 * @return
	 * @throws ApplicationException
	 */
	public List createCSV4Tsuchisho(Connection connection, String[] kenkyuNo, String sozokuCd)
		throws ApplicationException
	{
		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		String select = "SELECT " 
						+ "SI.NAME_KANJI_SEI            \"����-��\""			//����-��
						+ ",SI.NAME_KANJI_MEI           \"����-��\""			//����-��
						+ ",SI.NAME_KANA_SEI            \"�t���K�i-��\""		//�t���K�i-��
						+ ",SI.NAME_KANA_MEI            \"�t���K�i-��\""		//�t���K�i-��
						+ ",SI.KENKYU_NO	            \"�����Ҕԍ�\""		//�����Ҕԍ�
						+ ",SI.SHOZOKU_CD               \"�����@�փR�[�h\""	//�����@�փR�[�h	//�������Word�Ƃ̃����N���؂�邽�߁u�����@�ցv�̂܂�
						+ ",SI.SHOZOKU_NAME		        \"�����@�֖�\""		//�����@�֖�	//�������Word�Ƃ̃����N���؂�邽�߁u�����@�ցv�̂܂�
						+ ",SI.BUKYOKU_CD	            \"���ǃR�[�h\""		//���ǃR�[�h
						+ ",SI.BUKYOKU_NAME             \"���ǖ�\""			//���ǖ�
						+ ",SI.SHUBETU_CD	            \"���ǎ��\""		//���ǎ��
						+ ",SI.SHOKUSHU_NAME_KANJI      \"�E��\""			//�E��
						+ ",SI.SHINSEISHA_ID            \"ID\""				//ID
						+ ",SI.PASSWORD                 \"�p�X���[�h\""		//�p�X���[�h
						+ ",ST.BUKYOKU_NAME				\"�S�����ۖ�\""		//�S�����ۖ�
						+ ",ST.KAKARI_NAME              \"�S���W��\""		//�S���W��
						//+ ",ST.TANTO_NAME_SEI           \"�S���Ҏ���\""		//�S���Ҏ���
						+ ",ST.TANTO_NAME_SEI || '�@' || TANTO_NAME_MEI \"�S���Ҏ���\""
						+ ",ST.TANTO_EMAIL     	        \"Email�A�h���X\""	//Email�A�h���X
						+ ",ST.TANTO_TEL                \"�S���ғd�b�ԍ�\""	//�S���ғd�b�ԍ�
						+ ",ST.TANTO_FAX                \"�S����FAX�ԍ�\""	//�S����FAX�ԍ�
						+ ",ST.TANTO_ZIP                \"�S���җX�֔ԍ�\""	//�S���җX�֔ԍ�
						+ ",ST.TANTO_ADDRESS            \"�S���ҏZ��\""		//�S���ҏZ��
						+ " FROM"
						+ "  SHINSEISHAINFO SI, SHOZOKUTANTOINFO ST"
						+ " WHERE"
						+ "  SI.SHOZOKU_CD = ST.SHOZOKU_CD"
				//2005/04/30 �ǉ� -------------------------------------------��������
				//���R �폜�t���O�̏����ǉ�
						+ "  AND SI.DEL_FLG = '0' AND ST.DEL_FLG = '0' "
				//2005/04/30 �ǉ� -------------------------------------------�����܂�
						+ " AND SI.SHOZOKU_CD = '" + EscapeUtil.toSqlString(sozokuCd) + "'"	//2005/07/13
						;

		StringBuffer query = new StringBuffer(select);
		

		//�Ώی����ԍ�
		if(kenkyuNo != null && !kenkyuNo.equals("")){
			//2005/09/01 1000���ȏ�ɂȂ�ꍇ������̂�IN���g��Ȃ��B
			//query.append(" AND SI.KENKYU_NO IN (")
			//.append(changeArray2CSV(kenkyuNo))
			//.append(")");
			query.append(" AND (");
			for(int i=0; i<kenkyuNo.length; i++){
				query.append(" SI.KENKYU_NO = '")
					 .append(EscapeUtil.toSqlString(kenkyuNo[i]))
					 .append("' ");
				if(i!=kenkyuNo.length-1){
					query.append(" OR ");
				}
			}
			query.append(")");
			
			
		}

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----DB���R�[�h�擾-----
		try{
			return SelectUtil.selectCsvList(connection, query.toString(), true);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"CSV�o�̓f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4008"),
				e);
		}
		
	}
	
	/**
	 * 
	 * @param array
	 * @return
	 */
	private static String changeArray2CSV(String[] array){
		return StringUtil.changeArray2CSV(array, true);
	}
	

	//2005.09.26 iso ���d�o�^�h�~�̂��ߒǉ�
	/**
	 * ����u�����Ҕԍ��E�����@�փR�[�h�v�̐\���ҏ�񐔂��擾����B
	 * �폜�t���O���u0�v�̏ꍇ�̂݌�������B
	 * @param connection			�R�l�N�V����
	 * @param searchInfo			�\���ҏ��
	 * @return						�\���ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 */
	public int countShinseishaInfoPreInsert(
			Connection connection,
			ShinseishaInfo searchInfo)
			throws DataAccessException {
		String query =
			"SELECT COUNT(*)"
				+ " FROM SHINSEISHAINFO"
				+ " WHERE SHOZOKU_CD = ?"
				+ " AND KENKYU_NO = ?"
				+ " AND DEL_FLG = 0";		//�폜�t���O
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShinseishaInfo result = new ShinseishaInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getKenkyuNo());
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("�\���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

}
