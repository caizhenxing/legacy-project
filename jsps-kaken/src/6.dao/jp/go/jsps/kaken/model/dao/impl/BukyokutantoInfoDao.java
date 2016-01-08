/*
 * �쐬��: 2005/03/25
 *
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoPk;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.EscapeUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���ǒS���ҏ��f�[�^�A�N�Z�X�N���X�B
 * 
 * @author yoshikawa_h
 *
 */
public class BukyokutantoInfoDao {
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	/** ���O */
	protected static final Log log = LogFactory.getLog(BukyokutantoInfoDao.class);

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
	public BukyokutantoInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}	
	
	/**
	 * �L�[�Ɉ�v���镔�ǒS���ҏ����擾����B
	 * @param connection			�R�l�N�V����
	 * @param pkInfo				��L�[���
	 * @return						���ǒS���ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public BukyokutantoInfo selectBukyokutantoInfo(
		Connection connection,
		BukyokutantoPk pkInfo)
		throws DataAccessException, NoDataFoundException {
			String query =
				"SELECT "
					+ " BUKYOKU.BUKYOKUTANTO_ID"		//���ǒS����ID
					+ ",BUKYOKU.PASSWORD"				//�p�X���[�h
					+ ",BUKYOKU.TANTO_NAME_SEI"			//�S���Җ��i���j
					+ ",BUKYOKU.TANTO_NAME_MEI"			//�S���Җ��i���j
					+ ",BUKYOKU.BUKA_NAME"				//�S���ҕ��ۖ�	
					//2005/04/06�@�ǉ��@��������--------------------------
					//���R�@�S���ҌW�����ǉ����ꂽ����
					+ ",BUKYOKU.KAKARI_NAME"
					//�ǉ��@�����܂�--------------------------------------
					+ ",BUKYOKU.SHOZOKU_CD"				//�����@�փR�[�h
					+ ",BUKYOKU.BUKYOKU_TEL"			//�d�b�ԍ�
					+ ",BUKYOKU.BUKYOKU_FAX"			//FAX�ԍ�
					+ ",BUKYOKU.BUKYOKU_EMAIL"			//Email
					+ ",BUKYOKU.BUKYOKU_CD"				//���ǃR�[�h
					+ ",BUKYOKU.DEFAULT_PASSWORD"		//�f�t�H���g�p�X���[�h
					+ ",BUKYOKU.REGIST_FLG"				//�o�^�ς݃t���O
					+ ",BUKYOKU.DEL_FLG"				//�폜�t���O
//					 2005/04/05 �ǉ� ��������---------------------------------
//					 ���R ���O�C�����A�L�������`�F�b�N�̂���
					+ ",SHOZOKU.YUKO_DATE"			//�L������
//					 �ǉ� �����܂�--------------------------------------------
//					 2005/04/21 �ǉ� ��������---------------------------------
//					 ���R ���O�C�����A�����@�֒S���҂̍폜�t���O���`�F�b�N
					+ ",SHOZOKU.DEL_FLG AS DEL_FLG_SHOZOKU"			//�����@�֒S���҂̍폜�t���O
//					 �ǉ� �����܂�--------------------------------------------
					+ " FROM BUKYOKUTANTOINFO BUKYOKU"
						+ " INNER JOIN SHOZOKUTANTOINFO SHOZOKU"
							+ " ON BUKYOKU.SHOZOKU_CD = SHOZOKU.SHOZOKU_CD"
					+ " WHERE BUKYOKU.BUKYOKUTANTO_ID = ?";
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			try {
				BukyokutantoInfo result = new BukyokutantoInfo();
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				preparedStatement.setString(i++, pkInfo.getBukyokutantoId());
				recordSet = preparedStatement.executeQuery();
				if (recordSet.next()) {
					result.setBukyokutantoId(recordSet.getString("BUKYOKUTANTO_ID"));
					result.setPassword(recordSet.getString("PASSWORD"));
					result.setTantoNameSei(recordSet.getString("TANTO_NAME_SEI"));
					//2005/04/06 �ύX�@��������
					//���R�@�S���Җ��i���j�ɒS���Җ��i���j���ݒ肳��Ă�������
					//result.setTantoNameMei(recordSet.getString("TANTO_NAME_SEI"));
					result.setTantoNameMei(recordSet.getString("TANTO_NAME_MEI"));
					//�ύX�@�����܂�
					result.setBukaName(recordSet.getString("BUKA_NAME"));
					//2005/04/06�@�ǉ��@��������
					//���R�@�S���ҌW�����ǉ����ꂽ����
					result.setKakariName(recordSet.getString("KAKARI_NAME"));
					//�ǉ��@�����܂�
					result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
					result.setBukyokuTel(recordSet.getString("BUKYOKU_TEL"));
					result.setBukyokuFax(recordSet.getString("BUKYOKU_FAX"));
					result.setBukyokuEmail(recordSet.getString("BUKYOKU_EMAIL"));
					result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));
					result.setRegistFlg(recordSet.getString("REGIST_FLG"));
					result.setDelFlg(recordSet.getString("DEL_FLG"));
					result.setYukoDate(recordSet.getDate("YUKO_DATE"));
					result.setDelFlgShozoku(recordSet.getString("DEL_FLG_SHOZOKU"));
					return result;
				} else {
					throw new NoDataFoundException(
						"���ǒS���ҏ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F���ǒS����ID'"
							+ pkInfo.getBukyokutantoId()
							+ "'");
				}
			} catch (SQLException ex) {
				throw new DataAccessException("���ǒS���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}
	
	
	/**
	 * �L�[�Ɉ�v����S�����ǂ��擾����B
	 * @param connection			�R�l�N�V����
	 * @param pkInfo				��L�[���
	 * @return						���ǒS���ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public BukyokutantoInfo[] selectTantoBukyokuInfo(
		Connection connection,
		BukyokutantoPk pkInfo)
		throws DataAccessException, NoDataFoundException {
			String query =
				"SELECT "
					+ " BUKYOKUTANTO_ID"		//���ǒS����ID
					+ ",BUKYOKU_CD"				//���ǃR�[�h
					+ ",SHOZOKU_CD"			//�����@�փR�[�h
					+ ",BIKO"			//���l
					+ " FROM TANTOBUKYOKUKANRI"
					+ " WHERE BUKYOKUTANTO_ID = ?";
					
			if(pkInfo.getBukyokuCd() != null  && pkInfo.getBukyokuCd().endsWith("")){
				query += " AND BUKYOKU_CD = ?";
			}
			
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			
			BukyokutantoInfo[] tantoInfos = null;
			try {
				BukyokutantoInfo result = new BukyokutantoInfo();
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				preparedStatement.setString(i++, pkInfo.getBukyokutantoId());
				
				if(pkInfo.getBukyokuCd() != null  && pkInfo.getBukyokuCd().endsWith("")){
					preparedStatement.setString(i++, pkInfo.getBukyokuCd());
				}
				recordSet = preparedStatement.executeQuery();
				
				List resultList = new ArrayList();
				while (recordSet.next()) {
					result.setBukyokutantoId(recordSet.getString("BUKYOKUTANTO_ID"));
					result.setTantoBukyokuCd(recordSet.getString("BUKYOKU_CD"));
					result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
					result.setBiko(recordSet.getString("BIKO"));
					resultList.add(result);	
					
				}
				
				//�߂�l
				tantoInfos = (BukyokutantoInfo[])resultList.toArray(new BukyokutantoInfo[0]);
				
			} catch (SQLException ex) {
				throw new DataAccessException("���ǒS���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
			
			return tantoInfos;
	}
	
	/**
	 * �p�X���[�h�̕ύX����B 
	 * @param connection			�R�l�N�V����
	 * @param pkInfo				��L�[���
	 * @param newPassword			�V�����p�X���[�h
	 * @return              		�p�X���[�h�̕ύX�ɐ��������ꍇ true �ȊO false
	 * @throws DataAccessException	�ύX���ɗ�O�����������ꍇ
	 */
	public boolean changePasswordBukyokutantoInfo(
		Connection connection,
		BukyokutantoPk pkInfo,
		String newPassword)
		throws DataAccessException {

			String query = "UPDATE BUKYOKUTANTOINFO "
						 + "SET "
						 + "PASSWORD = ? "
						 + "WHERE "
						 + "BUKYOKUTANTO_ID = ? "
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
				DatabaseUtil.setParameter(ps,i++, pkInfo.getBukyokutantoId());	//���ǒS����ID
				DatabaseUtil.executeUpdate(ps);
			} catch (SQLException ex) {
				throw new DataAccessException("�p�X���[�h�ύX���ɗ�O���������܂����B ", ex);

			} finally {
				DatabaseUtil.closeResource(null, ps);
			}
			return true;
	}

	
	// 2005/04/07 �ǉ���������---------------------------------------------
	// ���R ���ǒS���ҏ��̓o�^�A�X�V�A�폜�A�p�X���[�h�ύX�����ǉ�����
	
	
	/** ���ǒS���ҏ����X�V����B
	 * ���ǒS���ҏ��e�[�u���̃f�[�^�X�V�ƒS�����ǊǗ��e�[�u���̃f�[�^�̒ǉ����s���B
	 * 
	 * @param		connection			�R�l�N�V����
	 * @param		info				���ǒS���ҏ��	
	 * @exception	DataAccessException	
	 * @exception	ApplicationException 	
	 */
	public void updateBukyokuData(Connection connection, 
				BukyokutantoInfo info)
				throws DataAccessException, ApplicationException{

		//���ǒS���ҏ����X�V����
		String query = "UPDATE BUKYOKUTANTOINFO "
							 + "SET "
							 + " TANTO_NAME_SEI = ? "
							 + ", TANTO_NAME_MEI = ? "
							 + ", BUKA_NAME = ? "
							 + ", KAKARI_NAME = ? "
							 + ", BUKYOKU_TEL = ? "
							 + ", BUKYOKU_FAX = ? "
							 + ", BUKYOKU_EMAIL = ? "
							 + ", REGIST_FLG = 1 ";
		//20005/06/01 �폜 ��������----------------------------------------
		//���R �p�X���[�h�̕ύX���s��Ȃ����ߍ폜
		
		//if(info.getAction() != null && info.getAction().equals("add")){
		//	query += 			", PASSWORD = ? ";
		//}
		//�폜 �����܂�----------------------------------------------------					 
		query =	query  	     + "WHERE "
							 + "BUKYOKUTANTO_ID = ? "
							 + "AND DEL_FLG = 0";			//�폜�t���O
		PreparedStatement ps = null;
			
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		try {
			//�X�V
			ps = connection.prepareStatement(query);
			int i = 1;	
			DatabaseUtil.setParameter(ps,i++, info.getTantoNameSei());	//���ǒS���Ґ�
			DatabaseUtil.setParameter(ps,i++, info.getTantoNameMei());	//���ǒS���Җ�
			DatabaseUtil.setParameter(ps,i++, info.getBukaName());		//���ۖ�
			DatabaseUtil.setParameter(ps,i++, info.getKakariName());	//�W��
			DatabaseUtil.setParameter(ps,i++, info.getBukyokuTel());	//�d�b�ԍ�
			DatabaseUtil.setParameter(ps,i++, info.getBukyokuFax());	//FAX
			DatabaseUtil.setParameter(ps,i++, info.getBukyokuEmail());	//mail
			//20005/06/01 �폜 ��������----------------------------------------
			//���R �p�X���[�h�̕ύX���s��Ȃ����ߍ폜
		
			//if(info.getAction() != null && info.getAction().equals("add")){
			//	DatabaseUtil.setParameter(ps,i++, newPassword);			//�p�X���[�h
			//}	
			//�폜 �����܂�----------------------------------------------------	
			DatabaseUtil.setParameter(ps,i++, info.getBukyokutantoId());//���ǒS����ID
			DatabaseUtil.executeUpdate(ps);
		}catch (SQLException ex) {
			try {
					DatabaseUtil.rollback(connection);
				} catch (TransactionException tex) {
					throw new ApplicationException(
						"���ǒS���ҏ��X�V����DB�G���[���������܂���",
						new ErrorInfo("errors.4002"),
						tex);
				} finally {
					DatabaseUtil.closeResource(null, ps);
				}
			throw new DataAccessException("���ǒS���ҏ��X�V���ɗ�O���������܂����B ", ex);

		}
		
		//�S�����ǊǗ��e�[�u���Ƀf�[�^��o�^����
		String insertQuery = null;

		PreparedStatement statement = null;
		boolean success = false;
		
		try {
			//���ǃR�[�h�̓o�^		
			int i = 1;
			//���ǃR�[�h�̌����������J��Ԃ�
			for(int j=0; j < info.getBukyokuList().size(); j++){
				Object bukyokuCd = info.getBukyokuList().get(j);
				if(bukyokuCd != null && !bukyokuCd.equals("")){
					insertQuery = "INSERT INTO TANTOBUKYOKUKANRI(" 
									+ "BUKYOKUTANTO_ID, " 
									+ "SHOZOKU_CD, " 
									+ "BUKYOKU_CD) "
									+ "VALUES(?, ?, ?)";
					statement = connection.prepareStatement(insertQuery);
					DatabaseUtil.setParameter(statement,i++, info.getBukyokutantoId());	//���ǒS���Ґ�
					DatabaseUtil.setParameter(statement,i++, info.getShozokuCd());		//�����R�[�h
					DatabaseUtil.setParameter(statement,i++, (String)bukyokuCd);		//���ǃR�[�h
					statement.executeQuery();
					i = 1;
				}
			}
			success = true;
		}catch (SQLException ex) {
				throw new DataAccessException("�S�����ǊǗ��o�^���ɗ�O���������܂����B ", ex);
		
		} finally {		
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�p�X���[�h�ύX����DB�G���[���������܂���",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeResource(null, statement);
			}
		}						
	}
	
	
	/** �폜�������s���B 
	 * 	�S�����ǊǗ��e�[�u�����珊���R�[�h�̈�v��������폜����B
	 * 
	 * @param		connection			�R�l�N�V����
	 * @param		info				���ǒS���ҏ��
	 * @exception	DataAccessException
	 * @exception	ApplicationException
	 */
	public void deleteBukyokuCd(Connection connection, BukyokutantoInfo info)
		throws DataAccessException, ApplicationException {

		boolean success = false;
		//�폜����
		String deleteQuery =
			"DELETE FROM TANTOBUKYOKUKANRI "
				+ "WHERE BUKYOKUTANTO_ID = ? "
				+ "AND SHOZOKU_CD = ? ";
		
		if(log.isDebugEnabled()){
			log.debug("query:" + deleteQuery);
		}
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = connection.prepareStatement(deleteQuery);
			int i = 1;
			DatabaseUtil.setParameter(prepareStatement,	i++, info.getBukyokutantoId());
			DatabaseUtil.setParameter(prepareStatement,	i++, info.getShozokuCd());
			prepareStatement.executeQuery();
			success = true;
		} catch (SQLException e) {
			throw new ApplicationException(
				"�S�����ǊǗ����폜����DB�G�����������܂���", new ErrorInfo("errors.4002"), e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�����ǊǗ����폜�m�蒆��DB�G���[���������܂���", new ErrorInfo("errors.4002"), e);
			} finally {
				DatabaseUtil.closeResource(null, prepareStatement);
			}
		}
	}
	
	
	/** 
	 * ���ǃR�[�h�̃`�F�b�N���s���B
	 * 
	 * @param		connection			�R�l�N�V����
	 * @param 		array				���ǃR�[�h�̔z��
	 * @exception	DataAccessException
	 * @exception	NoDataFoundException
	 */
	public int CheckBukyokuCd(Connection connection, HashSet set)
		throws DataAccessException, NoDataFoundException {

			String query = "SELECT COUNT(*) COUNT "
						 + "FROM MASTER_BUKYOKU ";			
			PreparedStatement ps = null;
			ResultSet recordSet = null;
			
			int count = 0;
			try {
				Iterator iter = set.iterator();
				if(set.size() == 1){
					query= query + "WHERE BUKYOKU_CD = "+EscapeUtil.toSqlString(iter.next().toString());
				}
				else{
					query = query + "WHERE BUKYOKU_CD IN(";
					while(iter.hasNext()){
						Object bukyokuCd = iter.next();
						if(bukyokuCd != null && !bukyokuCd.equals("")){
							query = query + "'" + EscapeUtil.toSqlString(bukyokuCd.toString()) + "',";
						}
						
					}
					query = query.substring(0, query.length()-1) + ")";
				}
				
				if(log.isDebugEnabled()){
					log.debug("query:" + query);
				}
				
				ps = connection.prepareStatement(query);
				recordSet = ps.executeQuery();
				if(recordSet.next()){
					count = recordSet.getInt("COUNT");
				}else{
					throw new NoDataFoundException(
						"�S�����ǊǗ����e�[�u���ɊY������f�[�^��������܂���B" +
							"�����L�[�F����CD'" + set + "'");
				}
						
			} catch (SQLException ex) {
				throw new DataAccessException("���ǃR�[�h�m�F���ɗ�O���������܂����B ", ex);

			} finally {
				DatabaseUtil.closeResource(null, ps);
			}
			return count;
	}
	
	
	/** 
	 * �����R�[�h�̔z����擾����B
	 *  
	 * @param		connection			�R�l�N�V����
	 * @param 		info				���ǒS���ҏ��
	 * @exception	DataAccessExcepiton
	 */
	public ArrayList selectTantoBukyokuKanri(Connection connection, BukyokutantoInfo info)
	throws DataAccessException{
	
		ArrayList list = new ArrayList();
		PreparedStatement ps = null;
		ResultSet recordSet = null;
		
		String query = "SELECT BUKYOKU_CD FROM TANTOBUKYOKUKANRI WHERE BUKYOKUTANTO_ID = ? ";	
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		try {
			//�X�V
			ps = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(ps,i++, info.getBukyokutantoId());
			recordSet = ps.executeQuery();
			for(int j = 0; j < 30; j++){
				if(recordSet.next()) {
					list.add(recordSet.getString("BUKYOKU_CD"));
				}else{
					list.add("");
				}				
			}					
			return list;
					
		} catch (SQLException ex) {
			throw new DataAccessException("�����R�[�h�擾���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, ps);
		}
	}
	
	
	/** 
	 * �폜�����B
	 * ���ǒS���ҏ��e�[�u�����畔�ǒS��ID����v������̍폜�t���O��1(�폜�ς�)�ɂ���B
	 * 
	 * @param		connection			�R�l�N�V����
	 * @param 		info				���ǒS���ҏ��
	 * @exception	DataAccessException
	 * @exception	ApplicationException
	 */
	public void deleteBukyokuData(Connection connection, BukyokutantoInfo info)
		throws DataAccessException, ApplicationException {
		
		PreparedStatement ps = null;
		ResultSet recordSet = null;
			
		String query = "UPDATE BUKYOKUTANTOINFO " 
					 + "SET DEL_FLG = 1 "
					 + "WHERE BUKYOKUTANTO_ID = ? "
					 + "AND DEL_FLG = 0";
			
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		try {
			//�폜
			ps = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(ps, i++, info.getBukyokutantoId());//���ǒS����ID
			DatabaseUtil.executeUpdate(ps);
	
		} catch (SQLException ex) {
			throw new DataAccessException(
				"���ǒS���҃e�[�u�����폜���ɗ�O���������܂����B ",
				ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, ps);
		}
	}
	
	
	/**
	 * �p�X���[�h���f�t�H���g�p�X���[�h�ɖ߂��B 
	 * 
	 * @param connection			�R�l�N�V����
	 * @param info					���ǒS���ҏ��
	 * @param newPassword			�V�����p�X���[�h
	 * @return              		�p�X���[�h�̕ύX�ɐ��������ꍇ true �ȊO false
	 * @throws DataAccessException	�ύX���ɗ�O�����������ꍇ
	 */
	public boolean originPassword(
		Connection connection,
		BukyokutantoInfo info)
		throws DataAccessException {

			String query = "UPDATE BUKYOKUTANTOINFO"
						 + " SET"
			//2005/04/13�@�C�� ��������------------------------------------------------------
			//���R �p�X���[�h�Ƀf�t�H���g�p�X���[�h��ݒ肷�邽��
						 + " PASSWORD = DEFAULT_PASSWORD"
			//�C�� �����܂�------------------------------------------------------------------
						 + " WHERE"
						 + " BUKYOKUTANTO_ID = ?"
						 + " AND DEL_FLG = 0";									//�폜�t���O

			PreparedStatement preparedStatement = null;
			
			if(log.isDebugEnabled()){
				log.debug("query:" + query);
			}
		
			try {
				//�o�^
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,info.getBukyokutantoId());	//���ǒS����ID
				DatabaseUtil.executeUpdate(preparedStatement);
			} catch (SQLException ex) {
				throw new DataAccessException("�p�X���[�h�ύX���ɗ�O���������܂����B ", ex);

			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
			return true;
	}
	
	
	/** 
	 * �����Ǘ������擾����B
	 * 
	 * @param 		connection			�R�l�N�V����
	 * @param 		info				���ǒS���ҏ��
	 * @return		info				���ǒS���ҏ��(�����Ǘ����Z�b�g��)
	 * @exception	DataAccessException	
	 * @exception	NoDataFoundException
	 */
	public BukyokutantoInfo selectShozokuData(Connection connection,
		BukyokutantoInfo info)
		throws DataAccessException, NoDataFoundException {

		String query = "SELECT"
//					 + " M.SHOZOKU_CD,"
					 + " SHOZOKU.SHOZOKU_CD,"
//					 + " M.SHOZOKU_NAME_KANJI,"
					 + " SHOZOKU.SHOZOKU_NAME_KANJI,"
				//2005/04/28�@�ύX ��������------------------------
				//���R �����@�֖��̉p���͋@�փ}�X�^�ɓo�^����Ȃ�����
				//	 + " M.SHOZOKU_NAME_EIGO "
					 + " SHOZOKU.SHOZOKU_NAME_EIGO"
				//�ύX �����܂�------------------------------------
					 + " FROM BUKYOKUTANTOINFO B"
//					 +	 " INNER JOIN MASTER_KIKAN M"
//					 +	 " ON B.SHOZOKU_CD = M.SHOZOKU_CD "
			//2005/04/28�@�ǉ� ��������----------------------------
			//���R �����@�֖��̉p���������@�֒S���҂���擾���邽��
					 +   " INNER JOIN SHOZOKUTANTOINFO SHOZOKU"
					 +   " ON SHOZOKU.SHOZOKU_CD = B.SHOZOKU_CD"
			//�ǉ� �����܂�----------------------------------------
					 + " WHERE B.BUKYOKUTANTO_ID = ?";

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
			
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,info.getBukyokutantoId());	//���ǒS����ID
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				info.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
				info.setShozokuName(recordSet.getString("SHOZOKU_NAME_KANJI"));
				info.setShozokuNameEigo(recordSet.getString("SHOZOKU_NAME_EIGO"));						
				return info;
			} else {
				throw new NoDataFoundException(
					"���ǒS���ҏ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F���ǒS����ID'"
						+ info.getBukyokutantoId()
						+ "'");
			}

		} catch (SQLException ex) {
			throw new DataAccessException("�����Ǘ����擾���ɗ�O���������܂����B ", ex);

		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	// �ǉ� �����܂�--------------------------------------------------------
	
	//	2005/04/08 �ǉ� ��������--------------------------------------------
	//	���R ���O�C������ID�E�p�X���[�h�`�F�b�N�̂���
	/**
	 * ���[�UID�A�p�X���[�h�̔F�؂��s���B
	 * @param connection		�R�l�N�V����
	 * @param userid			���[�UID
	 * @param password			�p�X���[�h
	 * @return					�F�؂ɐ��������ꍇ true �ȊO false
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 */
	public boolean authenticateBukyokuInfo(
		Connection connection, String userid, String password)
		throws DataAccessException {
		String query =
			"SELECT count(*)"
				+ " FROM BUKYOKUTANTOINFO"
				+ " WHERE DEL_FLG = 0"
				+ " AND BUKYOKUTANTO_ID = ?"
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
			throw new DataAccessException("���ǒS���ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	// �ǉ� �����܂�--------------------------------------------------------
	
	
	// 2005/04/21 �ǉ� ��������------------------------------------------------------------------------------
	// ���R ���ǒS���ҏ��̒ǉ�����
	
	/** ���ǒS���ҏ���ǉ�����B
	 * ���ǒS���ҏ��e�[�u���̃f�[�^�ǉ����s���B
	 * 
	 * @param		connection			�R�l�N�V����
	 * @param		info				���ǒS���ҏ��	
	 * @exception	DataAccessException	
	 * @exception	ApplicationException 	
	 */
	public void insertBukyokuData(Connection connection,BukyokutantoInfo info)
				throws DataAccessException, ApplicationException{
		
		//���ǒS���ҏ���ǉ�����
		String query = 
			 "INSERT INTO BUKYOKUTANTOINFO ("
				+ " BUKYOKUTANTO_ID"
			//2005/06/01 �ǉ� ��������-------------------------
			//���R �p�X���[�h�𕔋ǒS���ҏ��ǉ����ɓo�^���邽��(VALUES��?���ǉ�)
				+ " ,PASSWORD"
			//�ǉ� �����܂�------------------------------------
				+ " ,SHOZOKU_CD"
				+ " ,DEFAULT_PASSWORD"
				+ " ,REGIST_FLG"
				+ " ,DEL_FLG)"
			+ " VALUES( ?,?,?,?,?,? )";
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++, info.getBukyokutantoId());	//���ǒS����ID
			//2005/06/01 �ǉ� ��������---------------------------------------------
			//���R �p�X���[�h�𕔋ǒS���ҏ��ǉ����ɓo�^���邽��
			DatabaseUtil.setParameter(preparedStatement,i++, info.getDefaultPassword());	//�p�X���[�h
			//�ǉ� �����܂�--------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++, info.getShozokuCd());	//�����@�փR�[�h
			DatabaseUtil.setParameter(preparedStatement,i++, info.getDefaultPassword());	//�f�t�H���g�p�X���[�h
			DatabaseUtil.setParameter(preparedStatement,i++, "0");						//�o�^�ς݃t���O
			DatabaseUtil.setParameter(preparedStatement,i++, "0");						//�폜�t���O
			DatabaseUtil.executeUpdate(preparedStatement);
		}catch (SQLException ex) {
			try {
					DatabaseUtil.rollback(connection);
				} catch (TransactionException tex) {
					throw new ApplicationException(
						"���ǒS���ҏ��o�^����DB�G���[���������܂���",
						new ErrorInfo("errors.4002"),
						tex);
				} finally {
					DatabaseUtil.closeResource(null, preparedStatement);
				}
			throw new DataAccessException("���ǒS���ҏ��o�^���ɗ�O���������܂����B ", ex);

		}						
	}
	// �ǉ� �����܂�---------------------------------------------------------------------------------------------
	
	// 2005/04/21 �ǉ� ��������------------------------------------------------------------------------------
	// ���R ���ǒS���ҘA�Ԏ擾�p
	
    /**
     *�@�����@�֖��̘A�Ԃ��擾����B
     * @param connection           �R�l�N�V����
     * @param shozokuCd            �����@�փR�[�h
     * @return                     �A��(2��)
     * @throws DataAccessException�@�f�[�^�x�[�X�A�N�Z�X���̗�O
     */
    public String getSequenceNo(Connection connection,String shozokuCd) throws DataAccessException {
        String query =
            "SELECT TO_CHAR(MAX(SUBSTR(BUKYOKUTANTO_ID,6,2)) + 1,'FM00') COUNT"
                + " FROM BUKYOKUTANTOINFO"
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
				ret= recordSet.getString(1);
            }
			//���ǘA�Ԃ�10����Ƃ��� 20055/07/14
			if(ret == null){
				//ret = "01";
				ret = "10";
			}
            return ret;
            
        } catch (SQLException ex) {
            throw new DataAccessException("���ǒS���ҏ��e�[�u���������s���ɗ�O���������܂����B", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    
	// �ǉ� �����܂�---------------------------------------------------------------------------------------------
    
    // 2005/04/22 �ǉ� ��������----------------------------------------------------------------------------------
    // ���R �����@�֒S���ҍ폜���A�������@�ւɑ����镔�ǒS���҂������폜���邽��
	/** 
	 * �������@�ւɑ����镔�ǒS���҂��ꊇ�폜�i�_���폜�j����B
	 * ���ǒS���ҏ��e�[�u�����珊���@�փR�[�h����v������̍폜�t���O��1(�폜�ς�)�ɂ���B
	 * 
	 * @param		connection			�R�l�N�V����
	 * @param 		shozokuCd			�����@�փR�[�h
	 * @exception	DataAccessException
	 * @exception	ApplicationException
	 */
	public void deleteBukyokuDataAll(Connection connection, String shozokuCd)
		throws DataAccessException, ApplicationException {
		
		PreparedStatement ps = null;
		ResultSet recordSet = null;
			
		String query = "UPDATE BUKYOKUTANTOINFO " 
					 + "SET DEL_FLG = 1 "
					 + "WHERE SHOZOKU_CD = ? "
					 + "AND DEL_FLG = 0";
			
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		try {
			//�폜
			ps = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(ps, i++, shozokuCd);//�����@�փR�[�h
			ps.executeUpdate();
	
		} catch (SQLException ex) {
			throw new DataAccessException(
				"���ǒS���҃e�[�u�����폜���ɗ�O���������܂����B ",
				ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, ps);
		}
	}
	// �ǉ� �����܂�---------------------------------------------------------------------------------------------
    
    // 2005/04/22 �ǉ� ��������----------------------------------------------------------------------------------
    // ���R �����@�֒S���ҍ폜���A�������@�ւɑ����镔�ǒS���ҏ����폜���邽��
    /**
     * �������@�ւɑ����镔�ǒS���҂̒S�����Ǐ����폜����B�i�����폜�j
     * 
     * @param connection           �R�l�N�V����
     * @param shozokuCd            �����@�փR�[�h
     * @throws DataAccessException�@�f�[�^�x�[�X�A�N�Z�X���̗�O
     */
	public void deleteBukyokuCdAll(Connection connection, String shozokuCd)
		throws DataAccessException, ApplicationException {

		boolean success = false;
		//�폜����
		String deleteQuery =
			"DELETE FROM TANTOBUKYOKUKANRI "
				+ "WHERE SHOZOKU_CD = ? ";
		
		if(log.isDebugEnabled()){
			log.debug("query:" + deleteQuery);
		}
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = connection.prepareStatement(deleteQuery);
			int i = 1;
			DatabaseUtil.setParameter(prepareStatement,	i++, shozokuCd);
			prepareStatement.executeQuery();
			success = true;
		} catch (SQLException e) {
			throw new ApplicationException(
				"�S�����ǊǗ����폜����DB�G�����������܂���", new ErrorInfo("errors.4002"), e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�����ǊǗ����폜�m�蒆��DB�G���[���������܂���", new ErrorInfo("errors.4002"), e);
			} finally {
				DatabaseUtil.closeResource(null, prepareStatement);
			}
		}
	}
	// �ǉ� �����܂�---------------------------------------------------------------------------------------------
	
	//2005/06/01 �ǉ� ��������----------------------------------------------
	//�ؖ������s�pCSV�̕��ǒS���҃f�[�^�擾�̂���
	/**
	 *�@�ؖ������s�pCSV�̃f�[�^���擾����.<br>
	 *
	 * @param connection		�R�l�N�V����
	 * @param info				�����@�֏��
	 * @param list				�����@��CSV�f�[�^	
	 * @return  list           CSV�f�[�^���X�g    
	 * @throws DataAccessException�@�f�[�^�x�[�X�A�N�Z�X���̗�O
	 */
	public List getShomeiCsvData(Connection connection, ShozokuInfo info, List list) 
		throws DataAccessException {
		String query =
			"SELECT SUBSTR(BUKYOKUTANTO_ID,0,7) CODE, PASSWORD " +
			"FROM BUKYOKUTANTOINFO " +
			"WHERE SHOZOKU_CD = ? AND DEL_FLG = 0";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		List line = new ArrayList();
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, info.getShozokuCd());
			recordSet = preparedStatement.executeQuery();
			
			//�ݒ�t�@�C������CSV�Œ�f�[�^���擾
			String profileName = ApplicationSettings.getString(ISettingKeys.PROFILE_NAME);			//profile name
			String subjectDn = ApplicationSettings.getString(ISettingKeys.SUBJECT_DN);				//subject DN
			String subjectAltName = ApplicationSettings.getString(ISettingKeys.SUBJECT_ALT_NAME);	//subjectAltName
			String pubkeyAlgo = ApplicationSettings.getString(ISettingKeys.PUBKEY_ALGO);			//pubkey algo
			String keyLength = ApplicationSettings.getString(ISettingKeys.KEY_LENGTH);				//key length
			String p12Flag = ApplicationSettings.getString(ISettingKeys.P12_FLAG);		
				
			String shozoku = null;
			String renban = null;
			String password = null;
			//�����@�֏������X�g�Ɋi�[
			line.add(list);
			
			while (recordSet.next()) {
				List innerLine = new ArrayList();
				shozoku = recordSet.getString("CODE");
				if(shozoku != null && shozoku.length() != 0){
					renban = shozoku.substring(0,7);
				}
				password = recordSet.getString("PASSWORD");
				//-------CSV�f�[�^�����X�g�Ɋi�[
				innerLine.add("\"" +profileName + "\"");				//profile name
				innerLine.add(shozoku);				//���ǒS����ID�̏�7��(�����@�փR�[�h+�A��(2��))
				innerLine.add("\"" + subjectDn + info.getShozokuNameEigo() + renban + "\"");	//subject DN, �����@�֖�(�p��) + �A��(ID�̘A��)
				innerLine.add("\"" + subjectAltName + "\"");			//subjectAltName
				innerLine.add(pubkeyAlgo);							//pubkey algo
				innerLine.add(keyLength);							//key length	
				innerLine.add(p12Flag);								//p12 flag
				innerLine.add("\"" + password+ "\"");	//�p�X���[�h
				
				line.add(innerLine);
			}	
            
		} catch (SQLException ex) {
			throw new DataAccessException("���ǒS���ҏ��e�[�u���������s���ɗ�O���������܂����B", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return line;
	}
	//�ǉ� �����܂�---------------------------------------------------------


	/**
	 * ���ǒS���҂̃p�X���[�h���擾����B
	 * @param connection
	 * @param info
	 * @throws DataAccessException
	 * @throws ApplicationException
	 */
	public String getTantoPassword(Connection connection, 
			BukyokutantoInfo info)
			throws DataAccessException, ApplicationException{
		
		//���ǒS���҃p�X���[�h�����X�V����
		String query = "SELECT PASSWORD"
					 + "  FROM BUKYOKUTANTOINFO "
					 + " WHERE BUKYOKUTANTO_ID = ? "
					 + " AND DEL_FLG = 0";			//�폜�t���O

		PreparedStatement ps = null;
		ResultSet recordSet = null;
		String password = "";

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		try {
			//�X�V
			ps = connection.prepareStatement(query);
			int i = 1;	
			ps.setString(i++, info.getBukyokutantoId());//���ǒS����ID
			recordSet = ps.executeQuery();

			if (recordSet.next()) {
				password = recordSet.getString("PASSWORD");
			}	
			
		}catch (SQLException ex) {
			throw new DataAccessException("���ǒS���ҏ��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, ps);
		}
		return password;
	}

}
