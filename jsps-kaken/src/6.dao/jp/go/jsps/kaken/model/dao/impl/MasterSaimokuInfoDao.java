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
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.SaimokuInfo;
import jp.go.jsps.kaken.model.vo.SaimokuPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���ȍזڃ}�X�^�f�[�^�A�N�Z�X�N���X�B
 */
public class MasterSaimokuInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(MasterSaimokuInfoDao.class);

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
	public MasterSaimokuInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * ���ȍזڏ��̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @return						���ȍזڏ��
	 * @throws ApplicationException
	 */
	public static List selectSaimokuInfoList(Connection connection)
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
			+ " FROM MASTER_SAIMOKU A"
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
				"���ȍזڏ�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"���ȍזڏ��}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}

//	/**
//	 * ���ȍזڏ��̈ꗗ���擾����B
//	 * @param	connection			�R�l�N�V����
//	 * @param	bunkaName			���Ȗ�
//	 * @return						���ȍזڏ��
//	 * @throws ApplicationException
//	 */
//	public static List selectSaimokuInfoList(Connection connection, String bunkaName)
//		throws ApplicationException {
//
//		//-----------------------
//		// SQL���̍쐬
//		//-----------------------
//		String select =
//			"SELECT"
//			+ " A.BUNKASAIMOKU_CD"				//���ȍזڃR�[�h
//			+ ",A.BUNKA_NAME"					//���Ȗ�
//			+ " FROM MASTER_SAIMOKU A"
//			+ " WHERE A.BUNKA_NAME = ? "		//���Ȗ�
//			+ " ORDER BY A.BUNKASAIMOKU_CD";	//���ȍזڃR�[�h�̏���							
//			StringBuffer query = new StringBuffer(select);
//
//		if(log.isDebugEnabled()){
//			log.debug("query:" + query);
//		}
//		
//		//-----------------------
//		// ���X�g�擾
//		//-----------------------
//		try {
//			return SelectUtil.select(connection,query.toString(),new String[]{bunkaName});
//		} catch (DataAccessException e) {
//			throw new ApplicationException(
//				"���ȍזڏ�񌟍�����DB�G���[���������܂����B",
//				new ErrorInfo("errors.4004"),
//				e);
//		} catch (NoDataFoundException e) {
//			throw new SystemException(
//				"���ȍזڏ��}�X�^��1�����f�[�^������܂���B",
//				e);
//		}
//	}

	//TODO �R�[�h�\
	/**
	 * ���ȍזڏ��̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @param	bunkaCd				���ȃR�[�h
	 * @return						���ȍזڏ��
	 * @throws ApplicationException
	 */
	public static List selectSaimokuInfoList(Connection connection, String bunkaCd)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.BUNKASAIMOKU_CD"				//���ȍזڃR�[�h
			+ ",A.SAIMOKU_NAME"					//�זږ�
// 2005/04/15 �ǉ� ��������-----------------------------
// ���R �u�����ԍ��v�ǉ�
			+ ",A.BUNKATSU_NO"			//�����ԍ�
// �ǉ� �����܂�----------------------------------------
			+ " FROM MASTER_SAIMOKU A"
			;
			//TODO ���ȃR�[�h��̎b��Ή��B
			if(bunkaCd != null && !bunkaCd.equals("")) {
				select += " WHERE A.BUNKA_CD = ? ";			//���ȃR�[�h
			} else {
				select += " WHERE A.BUNKA_CD IS NULL ";
			}
			select += " ORDER BY A.BUNKASAIMOKU_CD,A.BUNKATSU_NO";	//���ȍזڃR�[�h�̏���							
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ���X�g�擾
		//-----------------------
		try {
			//TODO ���ȃR�[�h��̎b��Ή��B
			if(bunkaCd != null && !bunkaCd.equals("")) {
				return SelectUtil.select(connection,query.toString(),new String[]{bunkaCd});
			} else {
				return SelectUtil.select(connection,query.toString());
			}
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���ȍזڏ�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"���ȍזڏ��}�X�^��1�����f�[�^������܂���B",
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
			+ " FROM MASTER_SAIMOKU A"
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
				"���ȍזڏ�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"���ȍזڏ��}�X�^��1�����f�[�^������܂���B",
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
			+ " FROM MASTER_SAIMOKU A"
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
				"���ȍזڏ�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"���ȍזڏ��}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}
	
	
	
	/**
	 * ���ȍזڏ����擾����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						���ȍז�
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public SaimokuInfo selectSaimokuInfo(
		Connection connection,
		SaimokuPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		
//		2005/04/12 �ύX ��������----------
//		���R:�����ԍ��ǉ��̂���
		String query = "";
		//�����ԍ��������ꍇ�͕����ԍ����w�肵�Ȃ����߃t���O��false���w��
		boolean bunkatsuFlg = true;

// 20050707 �����ԍ���"-"�������ꍇ�ɃG���[�Ƃ��������ǉ�
//		if(primaryKeys.getBunkatsuNo() == null || primaryKeys.getBunkatsuNo().length() == 0){
//			bunkatsuFlg = false;
//		}
		if(primaryKeys.getBunkatsuNo() == null || 
			primaryKeys.getBunkatsuNo().length() == 0 ||
			primaryKeys.getBunkatsuNo() == "-"){
			bunkatsuFlg = false;
		}
// Horikoshi

		if(bunkatsuFlg){
			query =
				"SELECT "
					+ " A.BUNKASAIMOKU_CD"		//���ȍזڃR�[�h
					+ ",A.SAIMOKU_NAME"			//�זږ�
					+ ",A.BUNKA_CD"				//���ȃR�[�h
					+ ",A.BUNKA_NAME"			//���Ȗ�
					+ ",A.BUNYA_CD"				//����R�[�h
					+ ",A.BUNYA_NAME"			//���얼
					+ ",A.KEI"					//�n
					+ ",A.BUNKATSU_NO"			//�����ԍ�
					+ " FROM MASTER_SAIMOKU A"
					+ " WHERE BUNKASAIMOKU_CD = ?"
					+ " AND"
					+ " BUNKATSU_NO = ?";
		}else{
				query =
					"SELECT "
					+ " A.BUNKASAIMOKU_CD"		//���ȍזڃR�[�h
					+ ",A.SAIMOKU_NAME"			//�זږ�
					+ ",A.BUNKA_CD"				//���ȃR�[�h
					+ ",A.BUNKA_NAME"			//���Ȗ�
					+ ",A.BUNYA_CD"				//����R�[�h
					+ ",A.BUNYA_NAME"			//���얼
					+ ",A.KEI"					//�n
					+ " FROM MASTER_SAIMOKU A"
					+ " WHERE BUNKASAIMOKU_CD = ?";
		}
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			SaimokuInfo result = new SaimokuInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getBunkaSaimokuCd());
			
			//�����ԍ��������ꍇ�͕����ԍ����w�肵�Ȃ�
			if(bunkatsuFlg){
				preparedStatement.setString(i++, primaryKeys.getBunkatsuNo());
			}
			
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
				result.setSaimokuName(recordSet.getString("SAIMOKU_NAME"));
				result.setBunkaCd(recordSet.getString("BUNKA_CD"));
				result.setBunkaName(recordSet.getString("BUNKA_NAME"));
				result.setBunyaCd(recordSet.getString("BUNYA_CD"));
				result.setBunyaName(recordSet.getString("BUNYA_NAME"));
				result.setKei(recordSet.getString("KEI"));
				
				//�����ԍ��������ꍇ�͕����ԍ����w�肵�Ȃ�
				if(bunkatsuFlg){
					result.setBunkatsuNo(recordSet.getString("BUNKATSU_NO"));
				}
				
			} else {
				throw new NoDataFoundException(
					"���ȍזڏ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F���ȍז�CD'"
						+ primaryKeys.getBunkaSaimokuCd()
						+ "' �����ԍ�'"
						+ primaryKeys.getBunkatsuNo()
						+ "'");
			}
			
			return result;
//			2005/04/12 �C�� �����܂�----------
		} catch (SQLException ex) {
			throw new DataAccessException("���ȍזڏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}


	/**
	 * ���ȍזڏ���o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^���镪�ȍזڏ��
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertSaimokuInfo(
		Connection connection,
		SaimokuInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//�d���`�F�b�N
		try {
			checkSaimokuInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO MASTER_SAIMOKU "
				+ "("
				+ " BUNKASAIMOKU_CD"		//���ȍזڃR�[�h
				+ ",SAIMOKU_NAME"			//�זږ�
				+ ",BUNKA_CD"				//���ȃR�[�h
				+ ",BUNKA_NAME"				//���Ȗ�
				+ ",BUNYA_CD"				//����R�[�h
				+ ",BUNYA_NAME"				//���얼
				+ ",KEI"					//�n
				//2005/04/22 �ǉ� ��������-------------------------------------------------
				//�����ԍ���ǉ� (VALUES�ɂ�'?'����ǉ�)
				+ ",BUNKATSU_NO"
				//�ǉ� �����܂�------------------------------------------------------------
				+ ")"
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?)";
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
			//2005/04/22 �ǉ� ��������-------------------------------------------------
			//�����ԍ���ǉ�
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunkatsuNo());
			//�ǉ� �����܂�------------------------------------------------------------
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("���ȍזڏ��o�^���ɗ�O���������܂����B ", ex);
			throw new DataAccessException("���ȍזڏ��o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * ���ȍזڏ����X�V����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�X�V���镪�ȍזڏ��
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException		�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateSaimokuInfo(
		Connection connection,
		SaimokuInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectSaimokuInfo(connection, updateInfo);

		String query =
			"UPDATE MASTER_SAIMOKU"
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
			throw new DataAccessException("���ȍזڏ��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	
	/**
	 * ���ȍזڏ����폜����B(�����폜) 
	 * @param connection			�R�l�N�V����
	 * @param addInfo				�폜���镪�ȍזڎ�L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteSaimokuInfo(
		Connection connection,
		SaimokuPk deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectSaimokuInfo(connection, deleteInfo);

		String query =
			"DELETE FROM MASTER_SAIMOKU"
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
			throw new DataAccessException("���ȍזڏ��폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * ���ȍזڏ�񑶍݂��`�F�b�N����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						���ȍז�
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public SaimokuInfo checkSaimokuInfo(
		Connection connection,
		SaimokuPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		
		String query = "";

		query = "SELECT "
					+ " A.BUNKASAIMOKU_CD"		//���ȍזڃR�[�h
					+ ",A.SAIMOKU_NAME"			//�זږ�
					+ ",A.BUNKA_CD"				//���ȃR�[�h
					+ ",A.BUNKA_NAME"			//���Ȗ�
					+ ",A.BUNYA_CD"				//����R�[�h
					+ ",A.BUNYA_NAME"			//���얼
					+ ",A.KEI"					//�n
					+ ",A.BUNKATSU_NO"			//�����ԍ�
					+ " FROM MASTER_SAIMOKU A"
					+ " WHERE BUNKASAIMOKU_CD = ?"
					+ " AND"
					+ " BUNKATSU_NO = ?";

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			SaimokuInfo result = new SaimokuInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getBunkaSaimokuCd());
			preparedStatement.setString(i++, primaryKeys.getBunkatsuNo());
			
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
				
			} else {
				throw new NoDataFoundException(
					"���ȍזڏ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F���ȍז�CD'"
						+ primaryKeys.getBunkaSaimokuCd()
						+ "' �����ԍ�'"
						+ primaryKeys.getBunkatsuNo()
						+ "'");
			}
			
			return result;

		} catch (SQLException ex) {
			throw new DataAccessException("���ȍזڏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
    
//2006/06/27 �c�@�ǉ���������
    /**
     * �זڏ�񑶍݂��`�F�b�N����B
     * @param connection            �R�l�N�V����
     * @param primaryKeys           ��L�[���
     * @return SaimokuInfo          ���ȍז�
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ�B
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public SaimokuInfo checkSaimokuInfoForGaiyo(
        Connection connection,
        SaimokuPk primaryKeys)
        throws DataAccessException, NoDataFoundException {
        
        StringBuffer query = new StringBuffer();

        query.append("SELECT ");
        query.append(" A.BUNKASAIMOKU_CD"); // ���ȍזڃR�[�h
        query.append(",A.SAIMOKU_NAME"); // �זږ�
        query.append(",A.BUNKA_NAME"); // ���Ȗ�
        query.append(",A.BUNYA_NAME"); // ���얼
        query.append(" FROM MASTER_SAIMOKU A");
        query.append(" WHERE A.BUNKASAIMOKU_CD = ?"); 

        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            SaimokuInfo result = new SaimokuInfo();
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            preparedStatement.setString(i++, primaryKeys.getBunkaSaimokuCd());
            
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                result.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
                result.setSaimokuName(recordSet.getString("SAIMOKU_NAME"));
                result.setBunkaName(recordSet.getString("BUNKA_NAME"));
                result.setBunyaName(recordSet.getString("BUNYA_NAME"));
                
            } else {
                throw new NoDataFoundException(
                    "���ȍזڏ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F���ȍז�CD'"
                        + primaryKeys.getBunkaSaimokuCd()
                        + "'");
            }
            
            return result;

        } catch (SQLException ex) {
            throw new DataAccessException("���ȍזڏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2006/06/27�@�c�@�ǉ������܂�
    
//2007/03/01 �c�@�ǉ���������
    /**
     * ���ȍזڏ����擾����B
     * @param connection            �R�l�N�V����
     * @param primaryKeys           ��L�[���
     * @return                      ���ȍז�
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ�B
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public SaimokuInfo selectSaimokuInfoForCheck(
        Connection connection,
        SaimokuPk primaryKeys)
        throws DataAccessException, NoDataFoundException
    {
        String query = "SELECT "
                    + " A.BUNKASAIMOKU_CD"      //���ȍזڃR�[�h
                    + ",A.SAIMOKU_NAME"         //�זږ�
                    + ",A.BUNKA_CD"             //���ȃR�[�h
                    + ",A.BUNKA_NAME"           //���Ȗ�
                    + ",A.BUNYA_CD"             //����R�[�h
                    + ",A.BUNYA_NAME"           //���얼
                    + ",A.KEI"                  //�n
                    + ",A.BUNKATSU_NO"          //�����ԍ�
                    + " FROM MASTER_SAIMOKU A"
                    + " WHERE BUNKASAIMOKU_CD = ?";
        
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            SaimokuInfo result = new SaimokuInfo();
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, primaryKeys.getBunkaSaimokuCd());
            
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
            }
            return result;
        } catch (SQLException ex) {
            throw new DataAccessException("���ȍזڏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2007/03/01�@�c�@�ǉ������܂�    
}
