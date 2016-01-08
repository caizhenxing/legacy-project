/*
 * Created on 2005/04/18
 *
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.KeizokuInfo;
import jp.go.jsps.kaken.model.vo.KeizokuPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

/**
 * @author masuo_t
 *
 */
public class MasterKeizokuInfoDao {

	
	/** ���s���郆�[�U��� */
	private UserInfo userInfo = null;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	
	
	/**
	 * �R���X�g���N�^�B
	 * @param userInfo	���s���郆�[�U���
	 */
	public MasterKeizokuInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	/**
	 * �p���ۑ�}�X�^����Y������f�[�^���擾����B
	 * 
	 * @param connection
	 * @param pk
	 * @return	resultList	
	 * @throws DataAccessException
	 */
	public List select(Connection connection, KeizokuPk pk)
		throws DataAccessException, NoDataFoundException{
		
		List resultList = new ArrayList();
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		
		try {	
			String select = 
				"SELECT "
				+ " JIGYO_ID"				//����ID
				+ ", KADAI_NO"				//�ۑ�ԍ�
				+ ", ZENNENDO_KUBUN"		//�O�N�x����ۋ敪
				+ ", BIKO"					//���l
				+ " FROM MASTER_KEIZOKU"	//�p���ۑ�}�X�^	
				+ " WHERE JIGYO_ID = ?"
				+ " AND KADAI_NO = ?";		//��L�[�F����ID�A�ۑ�ԍ�
			
			StringBuffer query = new StringBuffer(select);
			
			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			KeizokuInfo result = new KeizokuInfo();
			preparedStatement.setString(i++, pk.getJigyoId());
			preparedStatement.setString(i++, pk.getKadaiNo());
			recordSet = preparedStatement.executeQuery();
			//�f�[�^�̌����������J��Ԃ�
			if(recordSet.next()){
				result.setJigyoId(recordSet.getString("JIGYO_ID"));
				result.setKadaiNo(recordSet.getString("KADAI_NO"));
				result.setZennendoKubun(recordSet.getString("ZENNENDO_KUBUN"));
				result.setBiko(recordSet.getString("BIKO"));
				//List��KeizokuInfo���i�[
				resultList.add(result);	
			
			}else{
				throw new NoDataFoundException(
					"�p���ۑ�}�X�^�e�[�u���ɊY������f�[�^��������܂���B�����L�[�F����CD'"
						+ pk.getJigyoId()
						+"', �ۑ�ԍ�'"
						+ pk.getKadaiNo()
						+ "'");
			}			
		} catch (SQLException ex) {
			throw new DataAccessException("�p���ۑ�}�X�^�������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return resultList;
	}
	

	//2005/04/22 �ǉ� ��������----------------------------------------------------
	//�p���ۑ�}�X�^��荞�ݗp��INSERT�����̒ǉ�
	/**
	 * �p���ۑ�}�X�^�ɒl��o�^����B
	 * 
	 * @param connection
	 * @param info
	 * @throws DataAccessException
	 */
	public void insertKeizokuInfo(Connection connection, KeizokuInfo info)
		throws DataAccessException{

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		
		try {	
			String select = 
					"INSERT INTO MASTER_KEIZOKU("
					+ " JIGYO_ID"				//����ID
					+ ", KADAI_NO"				//�ۑ�ԍ�
					+ ", ZENNENDO_KUBUN"		//�O�N�x����ۋ敪
					+ ", BIKO" 					//���l
//<!-- UPDATE�@START 2007/07/11 BIS ���� -->					
					+ ", KENKYU_NO" 			//�����Ҕԍ�
					+ ", KADAI_NAME_KANJI" 		//�����ۑ薼
					+ ", NAIYAKUGAKU1" 			//1�N�ړ���z
					+ ", NAIYAKUGAKU2" 			//2�N�ړ���z
					+ ", NAIYAKUGAKU3" 			//3�N�ړ���z
					+ ", NAIYAKUGAKU4" 			//4�N�ړ���z
					+ ", NAIYAKUGAKU5" 			//5�N�ړ���z
					+")"					
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
//<!-- UPDATE�@END 2007/07/11 BIS ���� -->			
			StringBuffer query = new StringBuffer(select);
			
			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,info.getJigyoId());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getKadaiNo());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getZennendoKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getBiko());
//			<!-- UPDATE�@START 2007/07/11 BIS ���� -->							
			DatabaseUtil.setParameter(preparedStatement,i++,info.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getKadaiNameKanji());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getNaiyakugaku1());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getNaiyakugaku2());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getNaiyakugaku3());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getNaiyakugaku4());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getNaiyakugaku5());
//			<!-- UPDATE�@END 2007/07/11 BIS ���� -->					
			DatabaseUtil.executeUpdate(preparedStatement);
		
		} catch (SQLException ex) {
			throw new DataAccessException("�p���ۑ�}�X�^�o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}


// 20050826 �ۑ�ԍ��݂̂Ōp���ۑ�}�X�^��������擾����
	/**
	 * �p���ۑ�}�X�^����ۑ�ԍ��݂̂ŊY������f�[�^���擾����B
	 * 
	 * @param connection
	 * @param kadaiNo
	 * @return	resultList
	 * @throws DataAccessException
	 */
	public List select(Connection connection, String kadaiNo)
		throws DataAccessException, NoDataFoundException{

		List resultList = new ArrayList();
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;

		try {
			String select = 
				"SELECT"
				+ " JIGYO_ID"			//����ID
				+ ", KADAI_NO"			//�ۑ�ԍ�
				+ ", ZENNENDO_KUBUN"	//�O�N�x����ۋ敪
				+ ", BIKO "				//���l
				+ "FROM"
				+ " MASTER_KEIZOKU "	//�p���ۑ�}�X�^	
				+ "WHERE"
				+ " KADAI_NO = ? ";		//�ۑ�ԍ�
			
			StringBuffer query = new StringBuffer(select);

			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			KeizokuInfo result = new KeizokuInfo();
			preparedStatement.setString(i++, kadaiNo);
			recordSet = preparedStatement.executeQuery();
			//�f�[�^�̌����������J��Ԃ�
			if(recordSet.next()){
				result.setJigyoId(recordSet.getString("JIGYO_ID"));
				result.setKadaiNo(recordSet.getString("KADAI_NO"));
				result.setZennendoKubun(recordSet.getString("ZENNENDO_KUBUN"));
				result.setBiko(recordSet.getString("BIKO"));
				//List��KeizokuInfo���i�[
				resultList.add(result);	
			
			}else{
				throw new NoDataFoundException(
					"�p���ۑ�}�X�^�e�[�u���ɊY������f�[�^��������܂���B�����L�[�F"
						+" �ۑ�ԍ�'"
						+ kadaiNo
						+ "'");
			}			
		} catch (SQLException ex) {
			throw new DataAccessException("�p���ۑ�}�X�^�������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return resultList;
	}

// ADD�@START 2007-07-26 BIS ���u��
	/**
	 * �p���ۑ�}�X�^����Y������f�[�^���擾����B
	 * 
	 * @param connection
	 * @param pk
	 * @return	resultList	
	 * @throws DataAccessException
	 */
	public List select(Connection connection, String kenkyuNo, String kadaiNo)
		throws DataAccessException, NoDataFoundException{
		
		List resultList = new ArrayList();
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		
		try {	
			String select = 
				"SELECT KADAI_NAME_KANJI" 		//�����ۑ薼
				+ ", NAIYAKUGAKU1" 			//1�N�ړ���z
				+ ", NAIYAKUGAKU2" 			//2�N�ړ���z
				+ ", NAIYAKUGAKU3" 			//3�N�ړ���z
				+ ", NAIYAKUGAKU4" 			//4�N�ړ���z
				+ ", NAIYAKUGAKU5" 			//5�N�ړ���z
				+ " FROM MASTER_KEIZOKU"	//�p���ۑ�}�X�^	
				+ " WHERE KENKYU_NO = ?"
				+ " AND KADAI_NO = ?";		//�ۑ�ԍ�
			
			StringBuffer query = new StringBuffer(select);
			
			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			KeizokuInfo result = new KeizokuInfo();
			preparedStatement.setString(i++, kenkyuNo);
			preparedStatement.setString(i++, kadaiNo);
			recordSet = preparedStatement.executeQuery();
			//�f�[�^�̌����������J��Ԃ�
			if(recordSet.next()){
				result.setKadaiNameKanji(recordSet.getString("KADAI_NAME_KANJI"));
				result.setNaiyakugaku1(recordSet.getString("NAIYAKUGAKU1"));
				result.setNaiyakugaku2(recordSet.getString("NAIYAKUGAKU2"));
				result.setNaiyakugaku3(recordSet.getString("NAIYAKUGAKU3"));
				result.setNaiyakugaku4(recordSet.getString("NAIYAKUGAKU4"));
				result.setNaiyakugaku5(recordSet.getString("NAIYAKUGAKU5"));
				//List��KeizokuInfo���i�[
				resultList.add(result);	
			
			}else{
				throw new NoDataFoundException(
					"�p���ۑ�}�X�^�e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�����Ҕԍ�'"
						+ kenkyuNo
						+"', �ۑ�ԍ�'"
						+ kadaiNo
						+ "'");
			}			
		} catch (SQLException ex) {
			throw new DataAccessException("�p���ۑ�}�X�^�������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return resultList;
	}
// ADD�@END 2007-07-26 BIS ���u��
}
