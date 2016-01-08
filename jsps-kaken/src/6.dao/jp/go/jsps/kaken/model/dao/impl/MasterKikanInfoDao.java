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

import org.apache.commons.logging.*;

/**
 *�@�����@�փ}�X�^�f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: MasterKikanInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class MasterKikanInfoDao {

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
	public MasterKikanInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * �����@�֏��̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @return						�����@�֏��
	 * @throws ApplicationException
	 */
	public static List selectKikanList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.SHUBETU_CD"			//�@�֎�ʃR�[�h
			+ ",A.KIKAN_KUBUN"			//�@�֋敪
			+ ",A.SHOZOKU_CD"			//�@�փR�[�h
			+ ",A.SHOZOKU_NAME_KANJI"	//�@�֖��́i���{��j
			+ ",A.SHOZOKU_RYAKUSHO"		//�@�֗���
			+ ",A.SHOZOKU_NAME_EIGO"	//�@�֖��́i�p��j
			+ ",A.SHOZOKU_ZIP"			//�X�֔ԍ�
			+ ",A.SHOZOKU_ADDRESS1"		//�Z���P
			+ ",A.SHOZOKU_ADDRESS2"		//�Z���Q
			+ ",A.SHOZOKU_TEL"			//�d�b�ԍ�
			+ ",A.SHOZOKU_FAX"			//FAX�ԍ�
			+ ",A.SHOZOKU_DAIHYO_NAME"	//��\��
			+ ",A.BIKO"					//���l
			+ " FROM MASTER_KIKAN A"
			+ " ORDER BY SHOZOKU_CD"
			;								
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
				"�����@�֏�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�����@�֏���1�����f�[�^������܂���B",
				e);
		}
	}

	/**
	 * �L�[�Ɉ�v���鏊���@�֏����擾����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						�����@�֏��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public KikanInfo selectKikanInfo(Connection connection,KikanPk pkInfo)
		throws DataAccessException, NoDataFoundException {

		KikanInfo result = new KikanInfo();
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement =
				connection.prepareStatement(
					"SELECT * FROM MASTER_KIKAN WHERE SHOZOKU_CD = ?");
			int i = 1;
			preparedStatement.setInt(i++, Integer.parseInt(pkInfo.getShozokuCd()));
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setShubetuCd(recordSet.getString("SHUBETU_CD"));					//�@�֎�ʃR�[�h
				result.setKikanKubun(recordSet.getString("KIKAN_KUBUN"));				//�@�֋敪
				result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));					//�@�փR�[�h
				result.setShozokuNameKanji(recordSet.getString("SHOZOKU_NAME_KANJI"));	//�@�֖��́i���{��j
				result.setShozokuRyakusho(recordSet.getString("SHOZOKU_RYAKUSHO"));		//�@�֖��́i���́j
				result.setShozokuNameEigo(recordSet.getString("SHOZOKU_NAME_EIGO"));	//�@�֖��́i�p��j
				result.setShozokuZip(recordSet.getString("SHOZOKU_ZIP"));				//�X�֔ԍ�
				result.setShozokuAddress1(recordSet.getString("SHOZOKU_ADDRESS1"));		//�Z��1
				result.setShozokuAddress2(recordSet.getString("SHOZOKU_ADDRESS2"));		//�Z��2
				result.setShozokuTel(recordSet.getString("SHOZOKU_TEL"));				//�d�b�ԍ�
				result.setShozokuFax(recordSet.getString("SHOZOKU_FAX"));				//FAX�ԍ�
				result.setShozokuDaihyoName(recordSet.getString("SHOZOKU_DAIHYO_NAME"));//��\�Җ�
				result.setBiko(recordSet.getString("BIKO"));							//���l
				//...
			} else {
				throw new NoDataFoundException(
					"�����@�փ}�X�^�ɊY������f�[�^��������܂���B�����L�[�F' �@�փR�[�h'"
						+ pkInfo.getShozokuCd()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�����@�փ}�X�^�������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return result;
	}

	
	//TODO �R�[�h�\�i���g�p�j
	/**
	 * �R�[�h�ꗗ�쐬�p���\�b�h�B<br>
	 * �@�փR�[�h�Ƌ@�֖��́i���{��j�̈ꗗ��Ԃ��B
     * �@�փR�[�h���Ƀ\�[�g����B
	 * @param	connection			�R�l�N�V����
	 * @param	shubetuCd			�@�֎�ʃR�[�h
	 * @return						�����@�֏��
	 * @throws ApplicationException
	 */
	public static List selectKikanList(Connection connection, String shubetuCd)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.SHOZOKU_CD"			//�@�փR�[�h
			+ ",A.SHOZOKU_NAME_KANJI"	//�@�֖��́i���{��j
			+ " FROM MASTER_KIKAN A"
			+ " WHERE SHUBETU_CD = ?"
			+ " ORDER BY SHOZOKU_CD"	//�@�փR�[�h��
			;								
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ���X�g�擾
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString(), new String[]{shubetuCd});
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����@�֏�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�����@�փ}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}


	//TODO �R�[�h�\�i���g�p�j
	/**
	 * �R�[�h�ꗗ�쐬�p���\�b�h�B<br>
	 * �@�֎�ʃR�[�h�Ǝ�ʖ��̂̈ꗗ��Ԃ��B
	 * �����@�֎�ʃR�[�h���Ƀ\�[�g����B
	 * @param	connection			�R�l�N�V����
	 * @return						�����@�֎�ʏ��
	 * @throws ApplicationException
	 */
	public static List selectKikanShubetuList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.SHUBETU_CD"									//�@�֎�ʃR�[�h
			+ ",TO_NUMBER(A.SHUBETU_CD) SHUBETU_CD_NUM"			//�@�֎�ʃR�[�h�i���l�j
			+ ",B.SHUBETU_NAME"									//�@�֖��́i���{��j
			+ " FROM MASTER_KIKAN A INNER JOIN MASTER_KIKANSHUBETU B ON A.SHUBETU_CD = B.SHUBETU_CD"
			+ " GROUP BY A.SHUBETU_CD,B.SHUBETU_NAME"
			+ " ORDER BY SHUBETU_CD_NUM"
			;								
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
				"�����@�֏�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�����@�փ}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}
	
	//TODO �R�[�h�\�i2004/11/17�j
	/**
	 * �@�փR�[�h�\�쐬�p���\�b�h�B<br>
	 * �C���f�b�N�X��Ԃ��B
	 * �啪�ރR�[�h�A�����ރR�[�h�A�����ރR�[�h�A�����@�փR�[�h�̏����Ń\�[�g����B
	 * @param	connection			�R�l�N�V����
	 * @return						�@�փR�[�h���
	 * @throws ApplicationException
	 */
	public static List selectKikanCodeIndex(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " B.DAIBUNRUI_CD,"	//�啪�ރR�[�h
			+ " B.DAIBUNRUI_NAME,"	//�啪�ޖ�
			+ " B.CHUBUNRUI_CD,"	//�����ރR�[�h
			+ " B.CHUBUNRUI_NAME"	//�����ޖ�
//2005/08/01 �����ނ��s�v�ƂȂ�ׁA�R�����g����
//			+ " B.SHOBUNRUI_CD,"	//�����ރR�[�h
//			+ " B.SHOBUNRUI_NAME"	//�����ޖ�
			+ " FROM MASTER_KIKAN A,MASTER_KOKAIKUBUN B"
			+ " WHERE"
			+ " A.SHUBETU_CD = B.SHUBETU_CD"	//�@�֎�ʃR�[�h
			+ " AND"
			+ " A.KIKAN_KUBUN = B.KIKAN_KUBUN"	//�@�֋敪
			+ " GROUP BY"
			+ " B.DAIBUNRUI_CD,"	//�啪�ރR�[�h
			+ " B.DAIBUNRUI_NAME,"	//�啪�ޖ�
			+ " B.CHUBUNRUI_CD,"	//�����ރR�[�h
			+ " B.CHUBUNRUI_NAME"	//�����ޖ�
//			+ " B.SHOBUNRUI_CD,"	//�����ރR�[�h
//			+ " B.SHOBUNRUI_NAME"	//�����ޖ�
			+ " ORDER BY"
			+ " B.DAIBUNRUI_CD,"	//�啪�ރR�[�h
			+ " B.DAIBUNRUI_NAME,"	//�啪�ޖ�
			+ " B.CHUBUNRUI_CD,"	//�����ރR�[�h
			+ " B.CHUBUNRUI_NAME"	//�����ޖ�
//			+ " B.SHOBUNRUI_CD,"	//�����ރR�[�h
//			+ " B.SHOBUNRUI_NAME"	//�����ޖ�
			;								
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
				"�����@�֏�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�����@�փ}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}

	//TODO �R�[�h�\�i2004/11/17�j
	/**
	 * �@�փR�[�h�\�쐬�p���\�b�h�B<br>
	 * �@�փ}�X�^�ƌ��J�敪�}�X�^�̈ꗗ��Ԃ��B
	 * �啪�ރR�[�h�A�����ރR�[�h�A�����ރR�[�h�A�����@�փR�[�h�̏����Ń\�[�g����B
	 * @param	connection			�R�l�N�V����
	 * @return						�@�փR�[�h���
	 * @throws ApplicationException
	 */
	public static List selectKikanCodeList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.SHUBETU_CD,"			//��ʋ@�փR�[�h
			+ " A.KIKAN_KUBUN,"			//�@�֋敪
			+ " A.SHOZOKU_CD,"			//�����@�փR�[�h
			+ " A.SHOZOKU_NAME_KANJI,"	//�����@�֖�
			+ " B.DAIBUNRUI_CD,"		//�啪�ރR�[�h
			+ " B.DAIBUNRUI_NAME,"		//�啪�ޖ�
			+ " B.CHUBUNRUI_CD,"		//�����ރR�[�h
			+ " B.CHUBUNRUI_NAME"		//�����ޖ�
//2005/08/01 �����ނ��s�v�ƂȂ�ׁA�R�����g����
//			+ " B.SHOBUNRUI_CD,"		//�����ރR�[�h
//			+ " B.SHOBUNRUI_NAME"		//�����ޖ�
			+ " FROM MASTER_KIKAN A,MASTER_KOKAIKUBUN B"
			+ " WHERE"
			+ " A.SHUBETU_CD = B.SHUBETU_CD"	//�@�֎�ʃR�[�h
			+ " AND"
			+ " A.KIKAN_KUBUN = B.KIKAN_KUBUN"	//�@�֋敪
//			+ " ORDER BY B.DAIBUNRUI_CD, B.CHUBUNRUI_CD, B.SHOBUNRUI_CD, A.SHOZOKU_CD"	//�啪�ށA�����ށA�����ށA�����@�փR�[�h
			+ " ORDER BY B.DAIBUNRUI_CD, B.CHUBUNRUI_CD, A.SHOZOKU_CD"	//�啪�ށA�����ށA�����ށA�����@�փR�[�h
			;								
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
				"�����@�֏�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�����@�փ}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}
	
	/**
	 * �����@�փ}�X�^����o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^���鏊���@�փ}�X�^���
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertMasterKikan(
		Connection connection,
		KikanInfo addInfo)
		throws DataAccessException
	{
		//�d���`�F�b�N
		try {
			selectKikanInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}
					
		String query = "INSERT INTO MASTER_KIKAN "
					 + "("
					 + " SHUBETU_CD"
					 + ",KIKAN_KUBUN"
					 + ",SHOZOKU_CD"
					 + ",SHOZOKU_NAME_KANJI"
					 + ",SHOZOKU_RYAKUSHO"
					 + ",SHOZOKU_NAME_EIGO"
					 + ",SHOZOKU_ZIP"
					 + ",SHOZOKU_ADDRESS1"
					 + ",SHOZOKU_ADDRESS2"
					 + ",SHOZOKU_TEL"
					 + ",SHOZOKU_FAX"
					 + ",SHOZOKU_DAIHYO_NAME"
					 + ",BIKO"
					 + ") "
					 + "VALUES "
					 + "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShubetuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKikanKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuNameKanji());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuRyakusho());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuNameEigo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuZip());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuAddress1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuAddress2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuTel());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuFax());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuDaihyoName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("�����@�փ}�X�^�o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * �����@�֏����ꊇ�o�^����B�i���������j
	 * @param connection				�R�l�N�V����
	 * @param addInfos					�o�^���鏊���@�֏�񃊃X�g
	 * @throws DataAccessException		�o�^�ɗ�O�����������ꍇ�B
	 */
	public void insertKikanInfos(Connection connection, List addInfos)
		throws DataAccessException {
		return;
	}

	//2005.08.08 iso �����@�֒S���҃��O�C�����ɋ@�փ}�X�^���݃`�F�b�N�����邽�ߒǉ�
	/**
	 * �@�֏��̐����擾����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						�@�֐�
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 */
	public int countShozokuInfo(
			Connection connection,
			String shozokuCd)
			throws DataAccessException {
		
		String query =
			"SELECT COUNT(*)"
				+ " FROM MASTER_KIKAN"
				+ " WHERE SHOZOKU_CD = ?";
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
			throw new DataAccessException("�����@�փ}�X�^�e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	/**
	 * ���J�敪�}�X�^�ɑ��݃`�F�b�N���s��
	 * @param connection
	 * @param kikanShubetu	�@�֎��
	 * @param kikanKubun	�@�֋敪
	 * @return�@����
	 * @throws DataAccessException
	 */
	public int checkKokaiKubun(
			Connection connection,
			String kikanShubetu,
			String kikanKubun)
			throws DataAccessException {
		
		String query =
			"SELECT COUNT(*)"
				+ " FROM MASTER_KOKAIKUBUN"
				+ " WHERE SHUBETU_CD = ?"
				+ " AND KIKAN_KUBUN = ?"
				;

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, kikanShubetu);
			preparedStatement.setString(i++, kikanKubun);
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("���J�敪�}�X�^�e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
}
