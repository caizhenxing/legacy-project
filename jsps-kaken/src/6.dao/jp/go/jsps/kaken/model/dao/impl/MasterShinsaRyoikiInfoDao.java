/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : syuu
 *    Date        : 2006/02/15
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import jp.go.jsps.kaken.model.vo.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *�@�R���̈�}�X�^�f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: MasterShinsaRyoikiInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class MasterShinsaRyoikiInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(MasterShinsaRyoikiInfoDao.class);

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
	public MasterShinsaRyoikiInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
//2007/02/05 �c�@�폜��������    �g�p���Ȃ�
//    /**
//	 * �R���̈���̈ꗗ���擾����B
//	 * @param	connection			�R�l�N�V����
//	 * @return						���ȍזڏ��
//	 * @throws ApplicationException
//	 */
//	public static List selectShinsaRyoikiInfoList(Connection connection)
//			throws ApplicationException {
//
//		//-----------------------
//		// SQL���̍쐬
//		//-----------------------
//		String select = "SELECT"
//						+ " A.SHINSARYOIKI_CD"		//�̈�R�[�h
//						+ ",A.SHINSARYOIKI_NAME"		//�̈於��
//						+ " FROM MASTER_SHINSARYOIKI A"
//						+ " ORDER BY SHINSARYOIKI_CD"
//						;								
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
//			return SelectUtil.select(connection,query.toString());
//		} catch (DataAccessException e) {
//			throw new ApplicationException(
//				"�R���̈��񌟍�����DB�G���[���������܂����B",
//				new ErrorInfo("errors.4004"),
//				e);
//		} catch (NoDataFoundException e) {
//			throw new SystemException(
//				"�R���̈���}�X�^��1�����f�[�^������܂���B",
//				e);
//		}
//	}
//
//	/**
//	 * �R���̈�}�X�^����A�̈�R�[�h�����Ƃɗ̈於�̂��擾����B
//	 * @param	connection			�R�l�N�V����
//	 * @param	ryoikiCd			�̈�R�[�h
//	 * @return						�̈於��
//	 * @throws ApplicationException
//	 */
//	public static String selectRyoikiName(Connection connection, String ryoikiCd)
//			throws ApplicationException {
//
//		//-----------------------
//		// SQL���̍쐬
//		//-----------------------
//		
//		String select = "SELECT"
//						+ " A.SHINSARYOIKI_NAME"					//�̈於��
//						+ " FROM MASTER_SHINSARYOIKI A"
//						+ " WHERE A.SHINSARYOIKI_CD = ?"
//						;
//			StringBuffer query = new StringBuffer(select);
//
//		if(log.isDebugEnabled()){
//			log.debug("query:" + query);
//		}
//
//		PreparedStatement preparedStatement = null;
//		try {
//			//�擾
//			preparedStatement = connection.prepareStatement(select);
//			preparedStatement.setString(1, ryoikiCd);
//			ResultSet rset = preparedStatement.executeQuery();
//
//			String ryoikiName = "";
//			if(rset.next()){
//				ryoikiName = rset.getString("SHINSARYOIKI_NAME");
//			}
//			
//			return ryoikiName;
//		} catch (SQLException ex) {
//			throw new ApplicationException("�̈於�擾���ɗ�O���������܂����B", ex);
//			
//		}
//	}
	

//	/**
//	 * �����זڃ}�X�^����A�����ז�CD�����Ƃɕ����זڏ����擾����B
//	 * @param	connection			�R�l�N�V����
//	 * @param	saimokuPk			�����זڃ}�X�^�̃v���C�}���L�[
//	 * @return						�����זڏ��
//	 * @throws ApplicationException
//	 */
//	public static HashMap selectSaimoku(Connection connection,String saimokuPk)
//		throws ApplicationException {
//
//		//-----------------------
//		// SQL���̍쐬
//		//-----------------------
//		
//		//Hashimoto_k[2005/03/16 14:04] ���ȍזڃ}�X�^�ύX�ɂ��C��
//		String select =
//			"SELECT"
//			+ " A.BUNKA_NAME"	//���Ȗ�
//			+ ",A.SAIMOKU_NAME"	//�זږ�
//			+ ",A.BU_CD"		//���R�[�h
//			+ ",A.BU_NAME"		//����
//			+ ",A.RYOIKI1"		//�̈�1
//			+ ",A.RYOIKI2"		//�̈�2
//			+ ",A.RYOIKI3"		//�̈�3
//			+ ",A.RYOIKI4"		//�̈�4
//			+ ",A.RYOIKI5"		//�̈�5
//			+ ",A.RYOIKI6"		//�̈�6
//			+ ",A.RYOIKI7"		//�̈�7
//			+ ",A.RYOIKI8"		//�̈�8
//			+ " FROM MASTER_SAIMOKU A"
//			+ " WHERE A.BUNKASAIMOKU_CD = ?";
//			
//			StringBuffer query = new StringBuffer(select);
//
//		if(log.isDebugEnabled()){
//			log.debug("query:" + query);
//		}
//		
//		PreparedStatement preparedStatement = null;
//		try {
//			//�擾
//			preparedStatement = connection.prepareStatement(select);
//			preparedStatement.setString(1, saimokuPk);
//			ResultSet rset = preparedStatement.executeQuery();
//
//			HashMap saimokuMap = new HashMap();
//			if(rset.next()){
//				saimokuMap.put("BUNKA_NAME",rset.getString("BUNKA_NAME"));
//				saimokuMap.put("SAIMOKU_NAME",rset.getString("SAIMOKU_NAME"));
//				saimokuMap.put("BU_CD",rset.getString("BU_CD"));
//				saimokuMap.put("BU_NAME",rset.getString("BU_NAME"));
//				saimokuMap.put("RYOIKI1",rset.getString("RYOIKI1"));
//				saimokuMap.put("RYOIKI2",rset.getString("RYOIKI2"));
//				saimokuMap.put("RYOIKI3",rset.getString("RYOIKI3"));
//				saimokuMap.put("RYOIKI4",rset.getString("RYOIKI4"));
//				saimokuMap.put("RYOIKI5",rset.getString("RYOIKI5"));
//				saimokuMap.put("RYOIKI6",rset.getString("RYOIKI6"));
//				saimokuMap.put("RYOIKI7",rset.getString("RYOIKI7"));
//				saimokuMap.put("RYOIKI8",rset.getString("RYOIKI8"));				
//			}
//			
//			return saimokuMap;
//		} catch (SQLException ex) {
//			throw new ApplicationException("�����זڏ��擾���ɗ�O���������܂����B ", ex);
//			
//		}
//	}
//2007/02/05�@�c�@�폜�����܂�    
}
