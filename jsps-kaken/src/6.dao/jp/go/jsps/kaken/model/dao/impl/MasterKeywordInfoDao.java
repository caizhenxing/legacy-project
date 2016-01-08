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
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * �L�[���[�h�}�X�^�f�[�^�A�N�Z�X�N���X�B
 */
public class MasterKeywordInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(MasterKeywordInfoDao.class);

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
	public MasterKeywordInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * �L�[���[�h���̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @return						�L�[���[�h���
	 * @throws ApplicationException
	 */
	public static List selectKeywordInfoList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUNKASAIMOKU_CD"		//���ȍזڃR�[�h
			+ ",A.SAIMOKU_NAME"			//�זږ�
			+ ",A.BUNKA_CD"				//���ȃR�[�h
			+ ",A.BUNKA_NAME"			//���Ȗ�
			+ ",A.BUNYA_CD"				//����R�[�h
			+ ",A.BUNYA_NAME"			//���얼
			+ ",A.KEI"					//�n
			+ ",A.KEYWORD_CD"			//�L��
			+ ",A.KEYWORD"				//�L�[���[�h
			+ " FROM MASTER_KEYWORD A"
			+ " ORDER BY BUNKASAIMOKU_CD";								
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
				"�L�[���[�h��񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�L�[���[�h���}�X�^��1�����f�[�^������܂���B",	e);
		}
	}

	//TODO �R�[�h�\
	/**
	 * �L�[���[�h���̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @param	bunkaCd				���ȃR�[�h
	 * @return						�L�[���[�h���
	 * @throws ApplicationException
	 */
	public static List selectKeywordInfoList(Connection connection, String bunkaCd)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		StringBuffer sbSql = new StringBuffer(512);

		sbSql.append("SELECT B.BUNKA_CD, B.BUNKA_CNT,\n");
		sbSql.append("       C.BUNKASAIMOKU_CD, C.SAIMOKU_NAME, TO_CHAR(C.SAIMOKU_CNT) SAIMOKU_CNT, \n");
		sbSql.append("       D.BUNKATSU_NO, TO_CHAR(D.BUNKATSU_CNT) BUNKATSU_CNT, \n");
		sbSql.append("       E.KEYWORD_CD, E.KEYWORD\n");
		sbSql.append("  FROM \n");
		sbSql.append("      (SELECT BUNKA_CD, COUNT(*) BUNKA_CNT\n");
		sbSql.append("         FROM MASTER_KEYWORD\n");
		sbSql.append("        WHERE BUNKA_CD = ?\n");
		sbSql.append("        GROUP BY BUNKA_CD) B,\n");
		sbSql.append("      (SELECT BUNKA_CD, BUNKASAIMOKU_CD, \n");
		sbSql.append("              MIN(SAIMOKU_NAME) SAIMOKU_NAME, COUNT(*) SAIMOKU_CNT\n");
		sbSql.append("         FROM MASTER_KEYWORD\n");
		sbSql.append("        WHERE BUNKA_CD = ?\n");
		sbSql.append("        GROUP BY BUNKA_CD, BUNKASAIMOKU_CD) C,\n");
		sbSql.append("      (SELECT BUNKA_CD, BUNKASAIMOKU_CD, BUNKATSU_NO, COUNT(*) BUNKATSU_CNT\n");
		sbSql.append("         FROM MASTER_KEYWORD\n");
		sbSql.append("        WHERE BUNKA_CD = ?\n");
		sbSql.append("        GROUP BY BUNKA_CD, BUNKASAIMOKU_CD, BUNKATSU_NO) D,\n");
		sbSql.append("      (SELECT BUNKA_CD, BUNKASAIMOKU_CD, BUNKATSU_NO,\n");
		sbSql.append("              KEYWORD_CD, MIN(KEYWORD) KEYWORD\n");
		sbSql.append("         FROM MASTER_KEYWORD\n");
		sbSql.append("        WHERE BUNKA_CD = ?\n");
		sbSql.append("        GROUP BY BUNKA_CD, BUNKASAIMOKU_CD, BUNKATSU_NO, KEYWORD_CD) E\n");
		sbSql.append(" WHERE B.BUNKA_CD = C.BUNKA_CD\n");
		sbSql.append("   AND C.BUNKA_CD = D.BUNKA_CD\n");
		sbSql.append("   AND C.BUNKASAIMOKU_CD = D.BUNKASAIMOKU_CD\n");
		sbSql.append("   AND D.BUNKA_CD = E.BUNKA_CD\n");
		sbSql.append("   AND D.BUNKASAIMOKU_CD = E.BUNKASAIMOKU_CD\n");
		sbSql.append("   AND D.BUNKATSU_NO = E.BUNKATSU_NO\n");
		sbSql.append(" ORDER BY BUNKA_CD, BUNKASAIMOKU_CD, BUNKATSU_NO, KEYWORD_CD\n");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + sbSql);
		}
		
		//-----------------------
		// ���X�g�擾
		//-----------------------
		try {
			return SelectUtil.select(connection,sbSql.toString(),new String[]{bunkaCd,bunkaCd,bunkaCd,bunkaCd});
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�L�[���[�h��񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�L�[���[�h���}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}


	//TODO �R�[�h�\
	/**
	 * ���Ȗ��̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @return	bunyaCd				����R�[�h
	 * @throws ApplicationException
	 */
	public static List selectBunkaNameList(Connection connection, String bunyaCd)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUNKA_CD,"			//���ȃR�[�h
			+ " A.BUNKA_NAME"			//���Ȗ�
			+ " FROM MASTER_KEYWORD A"
			+ " WHERE A.BUNYA_CD = ?"	//����R�[�h
			+ " GROUP BY A.BUNKA_CD,BUNKA_NAME,SUBSTR(BUNKASAIMOKU_CD,0,2)"
			+ " ORDER BY SUBSTR(BUNKASAIMOKU_CD,0,2)";			//���ȍזڃR�[�h�̐擪����2�Ԗڂ̏���							
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ���X�g�擾
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString(),new String[]{bunyaCd});
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�L�[���[�h��񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�L�[���[�h���}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}

	
	//TODO �R�[�h�\
	/**
	 * �R�[�h�ꗗ�쐬�p���\�b�h�B<br>
	 * ����R�[�h�ƕ��얼�̈ꗗ���擾����B
	 * ����R�[�h���Ń\�[�g����B
	 * @param	connection			�R�l�N�V����
	 * @return	
	 * @throws ApplicationException
	 */
	public static List selectBunyaNameList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUNYA_CD"			//����R�[�h
			+ ",A.BUNYA_NAME"		//���얼
			+ " FROM MASTER_KEYWORD A"
			+ " GROUP BY BUNYA_CD,BUNYA_NAME"
			+ " ORDER BY BUNYA_CD";	//����R�[�h
			
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
				"�L�[���[�h��񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�L�[���[�h���}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}
	
	
	
	/**
	 * �L�[���[�h�����擾����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						�L�[���[�h
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public KeywordInfo selectKeywordInfo(
		Connection connection,
		KeywordPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		StringBuffer sbSql = new StringBuffer(512);

		sbSql.append("SELECT")
			 .append(" BUNKASAIMOKU_CD")	//���ȍזڃR�[�h
			 .append(",SAIMOKU_NAME")		//�זږ�
			 .append(",BUNKA_CD")			//���ȃR�[�h
			 .append(",BUNKA_NAME")			//���Ȗ�
			 .append(",BUNYA_CD")			//����R�[�h
			 .append(",BUNYA_NAME")			//���얼
			 .append(",KEI")				//�n
			 .append(",BUNKATSU_NO")		//�����ԍ�
			 .append(",KEYWORD_CD")			//�L��
			 .append(",KEYWORD")			//�L�[���[�h
			 .append(" FROM MASTER_KEYWORD")
			 .append(" WHERE BUNKASAIMOKU_CD = ?")
			 .append(" AND BUNKATSU_NO = ?")
			 .append(" AND KEYWORD_CD = ?")
			 ;

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			KeywordInfo result = new KeywordInfo();
			preparedStatement = connection.prepareStatement(sbSql.toString());
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getBunkaSaimokuCd());
			preparedStatement.setString(i++, primaryKeys.getBunkatsuNo());
			preparedStatement.setString(i++, primaryKeys.getKeywordCd());
			
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
				result.setSaimokuName(recordSet.getString("SAIMOKU_NAME"));
				result.setBunkaCd(recordSet.getString("BUNKA_CD"));
				result.setBunkaName(recordSet.getString("BUNKA_NAME"));
				result.setBunyaCd(recordSet.getString("BUNYA_CD"));
				result.setBunyaName(recordSet.getString("BUNYA_NAME"));
				result.setKei(recordSet.getString("KEI"));
				result.setBunkatsuNo(recordSet.getString("BUNKATSU_NO"));
				result.setKeywordCd(recordSet.getString("KEYWORD_CD"));
				result.setKeyword(recordSet.getString("KEYWORD"));
				
			} else {
				throw new NoDataFoundException(
					"�L�[���[�h���e�[�u���ɊY������f�[�^��������܂���B�����L�[�F���ȍז�CD'"
						+ primaryKeys.getBunkaSaimokuCd()
						+ "' �����ԍ�'"
						+ primaryKeys.getBunkatsuNo()
						+ "'");
			}
			
			return result;
		} catch (SQLException ex) {
			throw new DataAccessException("�L�[���[�h���e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}




	/**
	 * �L�[���[�h�����擾����B
	 * �זڔԍ��A�����ԍ��A�L�[���[�h�R�[�h�����ꂼ������w�肷��B
	 * ��L������null�܂��͋󕶎��������ꍇ�́A�����������珜�O����B
	 * @param connection			�R�l�N�V����
	 * @param saimokuCd			�זڔԍ�
	 * @param bunkatsuCd			�����ԍ�
	 * @param keywordCd			�L�[���[�h�R�[�h
	 * @return						�L�[���[�h��񃊃X�g
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public KeywordInfo[] selectKeywordInfo(
		Connection connection,
		String saimokuCd,
		String bunkatsuCd,
		String keywordCd)
		throws DataAccessException, NoDataFoundException
	{
		StringBuffer sbSql = new StringBuffer(512);

		sbSql.append("SELECT")
			 .append(" BUNKASAIMOKU_CD")	//���ȍזڃR�[�h
			 .append(",SAIMOKU_NAME")		//�זږ�
			 .append(",BUNKA_CD")			//���ȃR�[�h
			 .append(",BUNKA_NAME")			//���Ȗ�
			 .append(",BUNYA_CD")			//����R�[�h
			 .append(",BUNYA_NAME")			//���얼
			 .append(",KEI")				//�n
			 .append(",BUNKATSU_NO")		//�����ԍ�
			 .append(",KEYWORD_CD")			//�L��
			 .append(",KEYWORD")			//�L�[���[�h
			 .append(" FROM MASTER_KEYWORD")
			 .append(" WHERE ")
			 .append("  1=1")				//�_�~�[����
			 ;
			 
		
		//�זڔԍ��������Ƃ��Ďw�肳��Ă����ꍇ
		if(!StringUtil.isBlank(saimokuCd)){ 
			sbSql.append(" AND BUNKASAIMOKU_CD = '"+EscapeUtil.toSqlString(saimokuCd)+"' ");
		}
		//�����ԍ��������Ƃ��Ďw�肳��Ă����ꍇ
		if(!StringUtil.isBlank(bunkatsuCd)){
			sbSql.append(" AND BUNKATSU_NO = '"+EscapeUtil.toSqlString(bunkatsuCd)+"' ");
		}
		//2005/8/30 �C��
		else{
////<!-- UPDATE�@START 2007/07/24 BIS ���� -->      
//			�����ԍ��iBUNKATSU_NO�j���u1�v�u2�v�u3�v�u4�v�u5�v�̏ꍇ     			
			//sbSql.append(" AND BUNKATSU_NO IN ('-','1','2')");
			sbSql.append(" AND BUNKATSU_NO IN ('-','1','2','3','4','5')");
////<!-- UPDATE�@START 2007/07/24 BIS ���� -->
		}
		//�L�[���[�h�R�[�h�������Ƃ��Ďw�肳��Ă����ꍇ
		if(!StringUtil.isBlank(keywordCd)){
			sbSql.append(" AND KEYWORD_CD = '"+EscapeUtil.toSqlString(keywordCd)+"' ");
		}
		
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(sbSql.toString());
			int i = 1;
			recordSet = preparedStatement.executeQuery();
			
			List resultArray = new ArrayList();
			while(recordSet.next()) {
				KeywordInfo result = new KeywordInfo();
				result.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
				result.setSaimokuName(recordSet.getString("SAIMOKU_NAME"));
				result.setBunkaCd(recordSet.getString("BUNKA_CD"));
				result.setBunkaName(recordSet.getString("BUNKA_NAME"));
				result.setBunyaCd(recordSet.getString("BUNYA_CD"));
				result.setBunyaName(recordSet.getString("BUNYA_NAME"));
				result.setKei(recordSet.getString("KEI"));
				result.setBunkatsuNo(recordSet.getString("BUNKATSU_NO"));
				result.setKeywordCd(recordSet.getString("KEYWORD_CD"));
				result.setKeyword(recordSet.getString("KEYWORD"));
				resultArray.add(result);
			}
			
			if(resultArray.isEmpty()){
				throw new NoDataFoundException(
					"�L�[���[�h���e�[�u���ɊY������f�[�^��������܂���B�����L�[�F���ȍז�CD'"
						+ saimokuCd
						+ "' �����ԍ�'"
						+ bunkatsuCd
						+ "' �L�[���[�h�R�[�h'"
						+ keywordCd
						+ "'");
			}
			
			return (KeywordInfo[])resultArray.toArray(new KeywordInfo[0]);
			
		} catch (SQLException ex) {
			throw new DataAccessException("�L�[���[�h���e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}






















	/**
	 * �L�[���[�h����o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^����L�[���[�h���
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertKeywordInfo(
		Connection connection,
		KeywordInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//�d���`�F�b�N
		try {
			selectKeywordInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO MASTER_KEYWORD "
				+ "("
				+ " BUNKASAIMOKU_CD"		//���ȍזڃR�[�h
				+ ",SAIMOKU_NAME"			//�זږ�
				+ ",BUNKA_CD"				//���ȃR�[�h
				+ ",BUNKA_NAME"				//���Ȗ�
				+ ",BUNYA_CD"				//����R�[�h
				+ ",BUNYA_NAME"				//���얼
				+ ",KEI"					//�n
				+ ",BUNKATSU_NO"			//�����ԍ�
				+ ",KEYWORD_CD"				//�L��
				+ ",KEYWORD"				//�L�[���[�h
				+ ")"
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunkaSaimokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSaimokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunkaCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunkaName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunyaCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunyaName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunkatsuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKeywordCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKeyword());

			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("�L�[���[�h���o�^���ɗ�O���������܂����B ", ex);
			throw new DataAccessException("�L�[���[�h���o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * �L�[���[�h�����X�V����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�X�V����L�[���[�h���
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException		�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateKeywordInfo(
		Connection connection,
		KeywordInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectKeywordInfo(connection, updateInfo);

		String query =
			"UPDATE MASTER_KEYWORD"
				+ " SET"	
				+ " BUNKASAIMOKU_CD = ?"		//���ȍזڃR�[�h
				+ ",SAIMOKU_NAME = ?"			//�זږ�
				+ ",BUNKA_CD = ?"				//���ȃR�[�h
				+ ",BUNKA_NAME = ?"				//���Ȗ�
				+ ",BUNYA_CD = ?"				//����R�[�h
				+ ",BUNYA_NAME = ?"				//���얼
				+ ",KEI = ?"					//�n
				+ " WHERE"
				+ " BUNKASAIMOKU_CD = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunkaSaimokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSaimokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunkaCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunkaName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunyaCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunyaName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunkaSaimokuCd());	//���ȍזڃR�[�h
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("�L�[���[�h���X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	
	/**
	 * �L�[���[�h�����폜����B(�����폜) 
	 * @param connection			�R�l�N�V����
	 * @param addInfo				�폜����L�[���[�h��L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteKeywordInfo(
		Connection connection,
		KeywordPk deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectKeywordInfo(connection, deleteInfo);

		String query =
			"DELETE FROM MASTER_KEYWORD"
				+ " WHERE"
				+ " BUNKASAIMOKU_CD = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getBunkaSaimokuCd());	//���ȍזڃR�[�h
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("�L�[���[�h���폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
}
