/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.UserAuthorityException;
import jp.go.jsps.kaken.model.vo.CheckListInfo;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.EscapeUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * �`�F�b�N���X�g�e�[�u���A�N�Z�X�N���X�B
 */
public class CheckListInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(CheckListInfoDao.class);

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
	public CheckListInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	

	/**
	 * �`�F�b�N���X�g���̐����擾����
	 * @param connection				�R�l�N�V����
	 * @param primaryKeys				��L�[���
	 * @return
	 * @throws DataAccessException		�擾���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public int countCheckListInfo(
		Connection connection,
		ShinseiDataInfo primaryKeys)
		throws DataAccessException, NoDataFoundException
		{
			String query =
				"SELECT "
					+ " count(*) COUNT"				//�擾����
					+ " FROM CHCKLISTINFO A"
					+ " WHERE A.SHOZOKU_CD = ?"
					+ " AND"
					+ " A.JIGYO_ID = ?"
					;
			
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			int retInt = 0;
			try {
				preparedStatement = connection.prepareStatement(query);
				int index = 1;
				DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getDaihyouInfo().getShozokuCd());	//�����@�փR�[�h
				DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getJigyoId());	//����ID
				recordSet = preparedStatement.executeQuery();
				if (recordSet.next()) {
					retInt = recordSet.getInt("COUNT");
				} else {
					throw new NoDataFoundException(
						"�\���f�[�^�Ǘ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�����R�[�h'"
							+ primaryKeys.getDaihyouInfo().getShozokuCd()
							+ "'"
							+ " ����ID'"
							+ primaryKeys.getJigyoId()
							+ "'"
							);
				}
				
			} catch (SQLException ex) {
				throw new DataAccessException("�\���f�[�^�Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
				
			}
			return retInt;
		}
	
	/**
	 * �`�F�b�N���X�g����o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^����`�F�b�N���X�g���
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ
	 */
	public void insertCheckListInfo(
		Connection connection,
		ShinseiDataInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//�L�[�d���`�F�b�N
		try {
			int count = countCheckListInfo(connection, addInfo);
			//NG
			if(count != 0){
				throw new DuplicateKeyException(
						"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
			}
		} catch (NoDataFoundException e) {
			//OK
		}
		
		String query =
			"INSERT INTO CHCKLISTINFO"
				+ "("
				+ "  SHOZOKU_CD"			//�����@�փR�[�h
				+ " ,JIGYO_ID"				//����ID
				+ " ,KAKUTEI_DATE"			//�`�F�b�N���X�g�m���
				+ " ,EDITION"				//��
				+ " ,JOKYO_ID"				//�\����ID
			//2005/04/21 �ǉ� ��������---------------------------------------
			//���R �����t���O�̒ǉ��̂���
				+ " ,CANCEL_FLG"
			//�ǉ� �����܂�--------------------------------------------------
			//2005/05/24 �ǉ� ��������---------------------------------------
			//���R PDF�̊i�[�p�X�̒ǉ��̂���(VALUES��?���ǉ�)
				+ " ,PDF_PATH"
			//�ǉ� �����܂�--------------------------------------------------
				+ ") "
				+ "  VALUES "
				+ "("
				+ "  ?,?,?,?,?,?,?"
				+ ")";
		
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement,index++,addInfo.getDaihyouInfo().getShozokuCd());	//�����@�փR�[�h
			DatabaseUtil.setParameter(preparedStatement,index++,addInfo.getJigyoId());						//����ID
			DatabaseUtil.setParameter(preparedStatement,index++,"");										//�`�F�b�N���X�g�m���
			DatabaseUtil.setParameter(preparedStatement,index++,"");										//��
			
			//2005/04/15 �폜 ��������--------------------------------------------------------
			//�`�F�b�N���X�g�̏�ID�͍쐬����03�ƂȂ邽��
			//DatabaseUtil.setParameter(preparedStatement,index++,addInfo.getJokyoId());						//�\����ID
			//�폜 �����܂�-------------------------------------------------------------------
			
			//2005/04/15 �ǉ� ��������--------------------------------------------------------
			//�`�F�b�N���X�g�̏�ID�͍쐬����03�ƂȂ邽��	
			DatabaseUtil.setParameter(preparedStatement,index++,"03");				
			//�ǉ� �����܂�-------------------------------------------------------------------
			
			//2005/04/21 �ǉ� ��������--------------------------------------------------------
			//���R �����t���O�̒ǉ��̂���
			DatabaseUtil.setParameter(preparedStatement,index++,"0");
			//�ǉ� �����܂�-------------------------------------------------------------------
			
			//2005/05/24 �ǉ� ��������--------------------------------------------------------
			//���R PDF�̊i�[�p�X�̒ǉ��̂���
			DatabaseUtil.setParameter(preparedStatement,index++,"");
			//�ǉ� �����܂�-------------------------------------------------------------------
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("�`�F�b�N���X�g�o�^���ɗ�O���������܂����B ", ex);
			//�G���[�ɂȂ����������x�`�������W����
			try{
				int count = countCheckListInfo(connection, addInfo);
				if(count == 0){
					throw new DataAccessException("�`�F�b�N���X�g�o�^���ɗ�O���������܂����B ", ex);
				}			
			}catch (NoDataFoundException e){
				throw new DataAccessException("�`�F�b�N���X�g�o�^���ɗ�O���������܂����B ", ex);
			}
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
		
	
	/**
	 * �`�F�b�N���X�g�����X�V����B
	 * @param connection				�R�l�N�V����
	 * @param checkInfo				�`�F�b�N���X�g�������
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 */
	public void updateCheckListInfo(
		Connection connection,
		CheckListSearchInfo checkInfo,
		boolean isVersionUp)
		throws DataAccessException
	{
		String query =
			"UPDATE CHCKLISTINFO"
				+ " SET"
				+ " JOKYO_ID = ?";
		//�ύX�O�̏�ID���擾
		String jokyoId = checkInfo.getJokyoId();
		if(jokyoId == null || jokyoId.equals("")){
			throw new DataAccessException("��ID���Z�b�g����Ă��܂���B");
		}
		//�m�莞�͔ł��グ��
		// 2004/04/14 �C�� ��ID�̏�����ǉ�--------------------------------------
		
		// 2005/04/12 �ύX ��������-----------------------------------------------
		// �m��������Ɋm������폜���邽��
		if(isVersionUp && jokyoId.equals(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU)){
			query = query + ", KAKUTEI_DATE = sysdate, EDITION = ?";
			//2005/04/21 �ǉ� ��������--------------------------------------------
			//�����t���O�ǉ��̂��� (�m�莞�͉����t���O��0�ɂ���)
			query += ", CANCEL_FLG = 0 ";
			//�ǉ� �����܂�-------------------------------------------------------
			
		//�m��������͊m������폜����
		}else if(!isVersionUp && jokyoId.equals(StatusCode.STATUS_GAKUSIN_SHORITYU)){
			query = query + ", KAKUTEI_DATE = null";
			//2005/04/21 �ǉ� ��������--------------------------------------------
			//�����t���O�ǉ��̂��� (�m��������͉����t���O��1�ɂ���)
			query += ", CANCEL_FLG = 1 ";
			//�ǉ� �����܂�-------------------------------------------------------
			
		}
		// �ύX�����܂�-----------------------------------------------------------
		
		query = query
				+ " WHERE"
				+ 	" SHOZOKU_CD = ?"
				+ " AND"
				+ 	" JIGYO_ID = ?"
				+ " AND"
				+ 	" JOKYO_ID = ?"
				;
		if (log.isDebugEnabled()){
			log.debug("�`�F�b�N���X�g�X�V�F" + query);
		}
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getChangeJokyoId());
			if(isVersionUp && jokyoId.equals(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU)){
				DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getEdition());
			}
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getJigyoId());	
			DatabaseUtil.setParameter(preparedStatement,i++,jokyoId);
			DatabaseUtil.executeUpdate(preparedStatement);				
		} catch (SQLException ex) {
			throw new DataAccessException("�`�F�b�N���X�g���X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	/**
	 * �\�����Ǘ����X�V����B
	 * @param connection				�R�l�N�V����
	 * @param checkInfo				�`�F�b�N���X�g�������
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 */
	public void updateShinseiData(
		Connection connection,
		CheckListSearchInfo checkInfo)
		throws DataAccessException
	{
		String query =
			"UPDATE SHINSEIDATAKANRI"
				+ " SET"
				+ "   JOKYO_ID = ?"
		//2006.12.13 iso �󗝉������ɏ��F���ɃV�X�e�����t���Z�b�g�����o�O�C��
//				+ " , SHONIN_DATE =  SYSDATE "	//2005/08/26 takano ���F�����Z�b�g
				;

		//2006.12.13 iso �󗝉������ɏ��F���ɃV�X�e�����t���Z�b�g�����o�O�C��
		//����ID�������@�֎�t���Ȃ�A���F����Ȃ̂ŁA���F�����Z�b�g����B
		if(checkInfo.getJokyoId() != null
				&& StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU.equals(checkInfo.getJokyoId())) {
			query += " , SHONIN_DATE =  SYSDATE "	//2005/08/26 takano ���F�����Z�b�g
				;
		}
				
			//2005/04/12 �ǉ� ��������--------------------------------------------
			//���R �󗝉����̂���
				if(checkInfo.getJokyoId() != null && checkInfo.getJokyoId().equals(StatusCode.STATUS_GAKUSIN_JYURI)){
					query += " , JYURI_DATE = null ";
				}
			//�ǉ� �����܂�-------------------------------------------------------	
		query = query + " WHERE"
				+ "   JIGYO_ID = ?"
				+ " AND"
				+ "   JOKYO_ID = ?"
			//2005/04/12 �ǉ� ��������--------------------------------------------
			//���R �����s���̂���
				+ " AND"
				+ "   SHOZOKU_CD = ?"
			//�ǉ� �����܂�-------------------------------------------------------
				+ " AND"
				+ "   DEL_FLG = 0";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getChangeJokyoId());
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getJigyoId());			
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getJokyoId());
			DatabaseUtil.setParameter(preparedStatement,i++,checkInfo.getShozokuCd());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("�\�����X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * �`�F�b�N���X�g�̏�ID���擾����B<!--.-->
	 * 
	 * @param connection             �R�l�N�V����
	 * @param checkInfo           �`�F�b�N���X�g�������
	 * @return String   �`�F�b�N���X�g�̏�ID
	 * @throws DataAccessException   �f�[�^�擾���ɗ�O�����������ꍇ
	 */
	public String checkJokyoId(
	Connection connection,
	CheckListSearchInfo checkInfo, 
	boolean updateFlag)
		throws DataAccessException
	{

		String query =
			"SELECT "
				+ " A.JOKYO_ID"
				+ " FROM CHCKLISTINFO A"
				+ 	" INNER JOIN JIGYOKANRI B"
				+ 	" ON A.JIGYO_ID = B.JIGYO_ID"
				+ " WHERE"
				+ "   A.SHOZOKU_CD = ?"
				+ " AND A.JIGYO_ID = ?";
// 20050606 Start �����Ɏ��Ƌ敪��ǉ�
				if(checkInfo.getJigyoKubun() != null && 
					checkInfo.getJigyoKubun().length() > 0
					){
					query = query + " AND B.JIGYO_KUBUN = '" + EscapeUtil.toSqlString(checkInfo.getJigyoKubun()) + "' ";
				}
				else{
					//TODO:�`�F�b�N���Ɏ��Ƌ敪���Z�b�g����Ă��Ȃ��ꍇ�ɂ̓f�t�H���g�̊�Վ��Ƌ敪���Z�b�g
					query = query + " AND B.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "' ";
				}
// Horikoshi End

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		if(updateFlag){
			query = query + " FOR UPDATE ";
		}

		//for debug
		if(log.isDebugEnabled()){log.debug("query:" + query);}

		String jokyoId = null;

		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, checkInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement, i++, checkInfo.getJigyoId());
			recordSet = preparedStatement.executeQuery();
			if(recordSet.next()){
				jokyoId =recordSet.getString("JOKYO_ID"); 
			}

		} catch (SQLException ex) {
			throw new DataAccessException("�Ώۃf�[�^�����݂��܂���B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}

		return jokyoId;
	}



	//2005/04/11 �ǉ� ��������--------------------------------------------------
	//���R �w�U�L�������m�F�̂���
	/**
	 * �L���������m�F����B
	 * 
	 * @param connection             �R�l�N�V����
	 * @param checkInfo           �`�F�b�N���X�g�������
	 * @return int					�L���������̃f�[�^�̌�
	 * @throws DataAccessException   �f�[�^�擾���ɗ�O�����������ꍇ
	 */
	public int checkDate(
		Connection connection,
		CheckListSearchInfo checkInfo)
		throws DataAccessException
	{
				
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		
		String query ="SELECT COUNT(*) COUNT "
			+ "FROM SHINSEIDATAKANRI DATA "
			//2005/04/12 �C�� ��������--------------------------------------------
			//���R INNER JOIN�̐ڑ��������s���Ȃ���
				+ "INNER JOIN CHCKLISTINFO CHECKLIST "
					+ "INNER JOIN JIGYOKANRI JIGYO "
					+ "ON JIGYO.JIGYO_ID = CHECKLIST.JIGYO_ID "
				+ "ON CHECKLIST.JIGYO_ID = DATA.JIGYO_ID " 
				+ "AND CHECKLIST.SHOZOKU_CD = DATA.SHOZOKU_CD "
			//�C�� �����܂�-------------------------------------------------------		
			+"WHERE DATA.JOKYO_ID = CHECKLIST.JOKYO_ID " 
				+ "AND DATA.DEL_FLG = 0 "
// 20050606 Start ���������Ɏ��Ƌ敪��ǉ�
//				+ "AND JIGYO.DEL_FLG = 0 "
//				+ "AND DATA.JIGYO_KUBUN = 4 "
				+ "AND JIGYO.DEL_FLG = 0 ";
				if (checkInfo.getJigyoKubun() != null && 
					checkInfo.getJigyoKubun().length() > 0
					){
					query = query + " AND DATA.JIGYO_KUBUN = " + EscapeUtil.toSqlString(checkInfo.getJigyoKubun()) + " ";
				}
				else{
					//TODO:���Ƌ敪���s���ȏꍇ�ɂ̓f�t�H���g�̊�Վ��Ƌ敪���Z�b�g
					query = query + " AND DATA.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN + " ";
				}
				//2005.11.17 iso ���ؓ��������\������Ȃ��̂ŁA���t�܂łŊۂߍ��ނ悤�C��
//				query = query + " AND SYSDATE BETWEEN JIGYO.UKETUKEKIKAN_START AND JIGYO.UKETUKEKIKAN_END";
				query = query + " AND TRUNC(SYSDATE, 'DD') BETWEEN JIGYO.UKETUKEKIKAN_START AND JIGYO.UKETUKEKIKAN_END";
//				+ "AND SYSDATE BETWEEN JIGYO.UKETUKEKIKAN_START AND JIGYO.UKETUKEKIKAN_END";
// Horikoshi End
		
		if (checkInfo.getShozokuCd() != null
			&& !checkInfo.getShozokuCd().equals("")) { //�����@�փR�[�h
			query = query +
				" AND CHECKLIST.SHOZOKU_CD = '"
					+ EscapeUtil.toSqlString(checkInfo.getShozokuCd())
					+ "'";
		}
		if(checkInfo.getJigyoId() != null
			&& !checkInfo.getJigyoId().equals("")) { //����ID
			query = query +
				" AND DATA.JIGYO_ID = '"
				+ EscapeUtil.toSqlString(checkInfo.getJigyoId())
				+ "'";
		}
		try {
			preparedStatement = connection.prepareStatement(query);
			int count = 0;
			recordSet = preparedStatement.executeQuery();
			recordSet.next();
			count = recordSet.getInt("COUNT");						
				
			//�߂�l
			return count;

		} catch (SQLException ex) {
			throw new DataAccessException("�Ώۃf�[�^�����݂��܂���B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}		
	}
	//	�ǉ� �����܂�-------------------------------------------------------------
	
	
	
	//2005/05/24�@�ǉ��@��������--------------------------------------------------
	//���R�@�\��PDF�t�@�C�������̂���
	/**
	 * �`�F�b�N���X�g�̕\��PDF�t�@�C���p�X���X�V����B
	 * @param connection            �R�l�N�V����
	 * @param pkInfo                ��L�[���
	 * @param iodFile               PDF�t�@�C��
	 * @throws DataAccessException  �X�V�������̗�O�B
	 * @throws NoDataFoundException�@�����Ώۃf�[�^���݂���Ȃ��Ƃ��B
	 */
	public void updateFilePath(
		Connection connection,
		final CheckListSearchInfo pkInfo,
		File iodFile)
		throws DataAccessException, NoDataFoundException 
	{
		
		ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();
		shinseiInfo.setJigyoId(pkInfo.getJigyoId());
		shinseiInfo.getDaihyouInfo().setShozokuCd(pkInfo.getShozokuCd());
		//�Q�Ɖ\�`�F�b�N���X�g�f�[�^���`�F�b�N
		int count = countCheckListInfo(connection, shinseiInfo);
		if(count == 0){
			throw new UserAuthorityException("�Q�Ɖ\�ȃ`�F�b�N���X�g�f�[�^�ł͂���܂���B");
		}
		
		String updateQuery =
				"UPDATE CHCKLISTINFO"
					+ " SET"
					+ " PDF_PATH = ?"
					+ " WHERE SHOZOKU_CD = ?"
					+ " AND JIGYO_ID = ? ";
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(updateQuery);
			int i = 1;
			//PDF�t�@�C���p�X
			DatabaseUtil.setParameter(preparedStatement,i++,iodFile.getAbsolutePath());
			//�����R�[�h�Ǝ���ID
			DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getJigyoId());
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException(
				"�`�F�b�N���X�g�\��PDF�t�@�C���p�X�X�V���ɗ�O���������܂����B �F����CD'"
					+ pkInfo.getShozokuCd()
					+ "', ����ID'"
					+ pkInfo.getJigyoId()
					+ "'",
				ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}	
	
	
	/**
	 * �\��PDF�t�@�C���p�X���擾����B
	 * 
	 * @param connection	Connection
	 * @param info	CheckListSearchInfo
	 * @return	PDF�t�@�C���p�X
	 * @throws DataAccessException	
	 */
	public String getPath(Connection connection, CheckListSearchInfo info)
		throws DataAccessException{
		
		String path = null;
		
		String query = "SELECT PDF_PATH FROM CHCKLISTINFO WHERE SHOZOKU_CD = ? AND JIGYO_ID = ?";
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			//�����R�[�h�Ǝ���ID
			DatabaseUtil.setParameter(preparedStatement,i++,info.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getJigyoId());
			recordSet = preparedStatement.executeQuery();
		
			if(recordSet.next()){
				path = recordSet.getString("PDF_PATH"); 
			}
		}catch (SQLException ex) {
			   throw new DataAccessException("�Ώۃf�[�^�����݂��܂���B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
		return path;
	}	
	
	
	/**
	 * �\��PDF�f�[�^���擾����B
	 * 
	 * @param connection	Connection
	 * @param searchInfo	CheckListSearchInfo
	 * @return	�\��PDF�f�[�^���i�[����CheckListInfo
	 */
	public CheckListInfo selectPdfData(Connection connection, CheckListSearchInfo searchInfo)
		throws DataAccessException{

		CheckListInfo resultInfo = new CheckListInfo();
		
		String query = 
			"SELECT " 
				+ "MASTER_JIGYO.JIGYO_NAME JIGYO_NAME" 
				+ ", COUNT(*) COUNT" 

				//�V�K�E�p���̌��� 2005/07/25
				//2005/8/18 ���C(���)�̏ꍇ�A�V�K�E�p���敪��null��o�^����Ă����
				//�V�K�E�p���敪��null�ł��鎞���V�K�Ƃ���
			    + ", SUM(DECODE(NVL(B.SHINSEI_KUBUN,'1'),'1',1,0)) SHINKI_CNT"
			    + ", SUM(DECODE(B.SHINSEI_KUBUN,'2',1,0)) KEIZOKU_CNT"
				+ ", MIN(B.JIGYO_KUBUN) JIGYO_KUBUN"
				
				+ ", C.SHOZOKU_CD SHOZOKU_CD" 
				+ ", MASTER_KIKAN.SHOZOKU_NAME_KANJI SHOZOKU_NAME "
			+ "FROM SHINSEIDATAKANRI B "
				+ "INNER JOIN CHCKLISTINFO C "
					+ "INNER JOIN JIGYOKANRI A " 
					+ "ON A.JIGYO_ID = C.JIGYO_ID " 
					+ "INNER JOIN MASTER_JIGYO MASTER_JIGYO "
					+ "ON MASTER_JIGYO.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) " 
				+ "ON C.JIGYO_ID = B.JIGYO_ID "
				+ "AND C.SHOZOKU_CD = B.SHOZOKU_CD "
				+ "AND C.JOKYO_ID = B.JOKYO_ID "
				+ "INNER JOIN MASTER_KIKAN MASTER_KIKAN " 
				+ "ON MASTER_KIKAN.SHOZOKU_CD = B.SHOZOKU_CD "
				+ "WHERE B.DEL_FLG = 0 " 
// 20050606 Start �������Ɏ��Ƌ敪��ǉ�
//				+ "AND A.DEL_FLG = 0 " 
//				+ "AND B.JIGYO_KUBUN = 4 "
				+ "AND A.DEL_FLG = 0 ";
				if (searchInfo.getJigyoKubun() != null && 
					searchInfo.getJigyoKubun().length() > 0
					){
					query = query + " AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + " ";
				}
				else{
					//TODO�F���Ƌ敪���s���̏ꍇ�ɂ̓f�t�H���g�̊�Վ��Ƌ敪���Z�b�g
					query = query + " AND B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN + " ";
				}
				query = query
// Horikoshi End
				+ "AND C.SHOZOKU_CD = ? " 
				+ "AND C.JIGYO_ID = ? "
			+ "GROUP BY " 
				+ "MASTER_JIGYO.JIGYO_NAME" 
				+ ", A.JIGYO_ID" 
				+ ", C.SHOZOKU_CD" 
				+ ", MASTER_KIKAN.SHOZOKU_NAME_KANJI ";
		if (log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			//�����R�[�h�Ǝ���ID
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getJigyoId());
			recordSet = preparedStatement.executeQuery();
		
			if(recordSet.next()){			
				resultInfo.setJigyoName(recordSet.getString("JIGYO_NAME"));//���Ɩ�
				resultInfo.setShozokuCd(recordSet.getString("SHOZOKU_CD"));//����CD
				resultInfo.setShozokuName(recordSet.getString("SHOZOKU_NAME"));//�����@�֖�
				resultInfo.setKaisu(recordSet.getString("COUNT"));//����
				
				//2005/07/25�V�K�E�p���\�������Ή�
				resultInfo.setShinkiCount(recordSet.getInt("SHINKI_CNT"));//�V�K����
				resultInfo.setKeizokuCount(recordSet.getInt("KEIZOKU_CNT"));//�p������
				resultInfo.setJigyoKbn(recordSet.getString("JIGYO_KUBUN"));//���Ƌ敪
			}
		}catch (SQLException ex) {
			   throw new DataAccessException("�Ώۃf�[�^�����݂��܂���B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
		return resultInfo;
	}		
	//�ǉ��@�����܂�------------------------------------------------------------



// 20050722
	public List select(
			Connection connection,
			String chSQL,
			final String[] chItems
			)
		throws
			DataAccessException
		{

		int nCount							= 0;
		int nNum							= 0;
		List lstResult						= new ArrayList();
		PreparedStatement preparedStatement	= null;
		ResultSet recordSet					= null;

		try {
			preparedStatement = connection.prepareStatement(chSQL);
			recordSet = preparedStatement.executeQuery();
			while(recordSet.next()){
				String[] chValues				= new String[chItems.length];
				for(nNum=0; chItems.length > nNum; nNum++){
					chValues[nNum] = recordSet.getString(chItems[nNum]);
				}
				lstResult.add(nCount, chValues);
				nCount++;
			}
		}
		catch (SQLException ex) {
			   throw new DataAccessException("�Ώۃf�[�^�����݂��܂���B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
		return lstResult;
	}
// Horikoshi
//  2007/03/02 �c�@�ǉ���������
    /**
     * �\���@�֊m��ς݂̏���Ԃ��B
     * @param connection
     * @param kikanCd
     * @param jigyoID
     * @return boolean
     * @throws DataAccessException
     */
    public boolean checkKakuteibi(Connection connection, String kikanCd,
            String jigyoID) throws DataAccessException {
        Date kakuteibi = null;
        String query = "SELECT KAKUTEI_DATE" // �����t���O
                + " FROM CHCKLISTINFO" + " WHERE" + " SHOZOKU_CD = ?" + " AND"
                + " JIGYO_ID = ?";
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, kikanCd); // ���������i�\���@�ցj
            DatabaseUtil.setParameter(preparedStatement, i++, jigyoID); // ���������i����ID�j
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                kakuteibi = recordSet.getDate("KAKUTEI_DATE"); // �m���
            }
        } catch (SQLException ex) {
            throw new DataAccessException("�`�F�b�N���X�g�e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
        return kakuteibi != null;
    }
//2007/03/02�@�c�@�ǉ������܂�   
}
