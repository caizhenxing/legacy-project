/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/07/26
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.AccessKanriInfo;
import jp.go.jsps.kaken.model.vo.AccessKanriPk;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �A�N�Z�X����e�[�u���A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: AccessKanriDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class AccessKanriDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(AccessKanriDao.class);

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
	public AccessKanriDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * �L�[�Ɉ�v����A�N�Z�X��������擾����B
	 * @param connection			    �R�l�N�V����
	 * @param pkInfo				    ��L�[���
	 * @return						    �A�N�Z�X������
	 * @throws DataAccessException	    �f�[�^�擾���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public AccessKanriInfo selectAccessKanriInfo(
		Connection connection,
		AccessKanriPk pkInfo)
		throws DataAccessException, NoDataFoundException
	{
			String query =
				"SELECT "
					+ " GYOMUTANTO_ID"
					+ ",JIGYO_CD"
					+ ",JIGYO_KUBUN"
					+ ",BIKO"
					+ " FROM ACCESSKANRI"
					+ " WHERE"
					+ "   GYOMUTANTO_ID = ?"
					+ " AND"
					+ "   JIGYO_CD = ?"
					;
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			try {
				AccessKanriInfo result = new AccessKanriInfo();
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement, i++, pkInfo.getGyomutantoId());
				DatabaseUtil.setParameter(preparedStatement, i++, pkInfo.getJigyoCd());				
				recordSet = preparedStatement.executeQuery();
				if (recordSet.next()) {
					result.setGyomutantoId(recordSet.getString("GYOMUTANTO_ID"));
					result.setJigyoCd(recordSet.getString("JIGYO_CD"));
					result.setJigyoKubun(recordSet.getString("JIGYO_KUBUN"));
					result.setBiko(recordSet.getString("BIKO"));
					return result;
				} else {
					throw new NoDataFoundException(
						"�A�N�Z�X����e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�Ɩ��S����ID'"
							+ pkInfo.getGyomutantoId()
							+ "'�A���ƃR�[�h'"
							+ pkInfo.getJigyoCd()
							+ "'");
				}
			} catch (SQLException ex) {
				throw new DataAccessException("�A�N�Z�X����e�[�u���������s���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}	
	
	
	
	/**
	 * �A�N�Z�X�������o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^����A�N�Z�X������
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertAccessKanriInfo(
		Connection connection,
		AccessKanriInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
			//�d���`�F�b�N
			try {
				selectAccessKanriInfo(connection, addInfo);
				//NG
				throw new DuplicateKeyException(
					"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
			} catch (NoDataFoundException e) {
				//OK
			}
		
			String query =
				"INSERT INTO ACCESSKANRI "
					+ "("
					+ " GYOMUTANTO_ID"
					+ ",JIGYO_CD"
					+ ",JIGYO_KUBUN"
					+ ",BIKO"
					+ ") "
					+ "VALUES "
					+ "(?,?,?,?)"
					;
					
			PreparedStatement preparedStatement = null;
			try {
				//�o�^
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getGyomutantoId());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoCd());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoKubun());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
				DatabaseUtil.executeUpdate(preparedStatement);
			} catch (SQLException ex) {
				throw new DataAccessException("�A�N�Z�X������o�^���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	
	
	
	/**
	 * �A�N�Z�X��������X�V����B
	 * @param connection				�R�l�N�V����
	 * @param updateInfo				�X�V����Ɩ��S���ҏ��
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateGyomutantoInfo(
		Connection connection,
		AccessKanriInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
			//����
			selectAccessKanriInfo(connection, updateInfo);
	
			String query =
				"UPDATE ACCESSKANRI"
					+ " SET"
					+ " GYOMUTANTO_ID = ?"
					+ ",JIGYO_CD = ?"
					+ ",JIGYO_KUBUN = ?"
					+ ",BIKO = ?"
					+ " WHERE"
					+ "   GYOMUTANTO_ID = ?"
					+ " AND"
					+ "   JIGYO_CD = ?"
					;

			PreparedStatement preparedStatement = null;
			try {
				//�o�^
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getGyomutantoId());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoCd());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getGyomutantoId());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoCd());				
				DatabaseUtil.executeUpdate(preparedStatement);				
			} catch (SQLException ex) {
				throw new DataAccessException("�A�N�Z�X������X�V���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	
	
	
	/**
	 * �A�N�Z�X�Ǘ������폜����B(�����폜)
	 * @param connection			    �R�l�N�V����
	 * @param pkInfo				    �폜����Ɩ��S���ҏ��
	 * @throws DataAccessException     �폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException    �Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteAccessKanriInfo(
		Connection connection,
		AccessKanriInfo deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�폜�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectAccessKanriInfo(connection, deleteInfo);
		
		String query =
			"DELETE FROM ACCESSKANRI"
				+ " WHERE"
				+ "   GYOMUTANTO_ID = ?"
				+ " AND"
				+ "   JIGYO_CD = ?"
				;
		
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getGyomutantoId());
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getJigyoCd());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("�A�N�Z�X������폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}	
	
	
	
	/**
	 * ���Y�Ɩ��S���҂̃A�N�Z�X�������S�ĕԂ��B<!--.-->
	 * �Ώۃf�[�^�����݂��Ȃ��ꍇ�́A���HashSet���i�[����Ă���B<br>
	 * �߂�l�̌^���AupdateAccessKanri()�̈����Ƃ͈Ⴄ�̂Œ��ӁB<br>
	 * 	 * <table border="1">
	 * 	<tr>
	 * 		<td>No</td>
	 * 		<td>�L�[��</td>
	 * 		<td>�^</td>
	 * 		<td>�l</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>1</td>
	 * 		<td>tantoJigyoCd</td>
	 * 		<td>HashSet</td>
	 * 		<td>�S�����ƃR�[�h</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>2</td>
	 * 		<td>tantoJigyoKubun</td>
	 * 		<td>HashSet</td>
	 * 		<td>�S�����Ƌ敪</td>
	 * 	</tr>
	 * </table><br>
	 * @param connection             �R�l�N�V����
	 * @param gyomuTantoPk           �Ɩ��S���ҏ���L�[
	 * @return                       ���Y�Ɩ��S���҂̃A�N�Z�X������
	 * @throws DataAccessException   �f�[�^�擾���ɗ�O�����������ꍇ
	 */
	public Map selectAccessKanri(
		Connection   connection,
		GyomutantoPk gyomuTantoPk)
		throws DataAccessException
	{
		String query =
			"SELECT "
				+ " JIGYO_CD"
				+ ",JIGYO_KUBUN"
				+ " FROM ACCESSKANRI"
				+ " WHERE"
				+ "   GYOMUTANTO_ID = ?"
				+ " ORDER BY GYOMUTANTO_ID"
				;
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			Set tantoJigyoCd     = new HashSet();
			Set tantoJigyoKubun  = new HashSet();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, gyomuTantoPk.getGyomutantoId());
			//�������̍폜�Ȃ̂�DatabaseUtil��ʂ��Ȃ��B
			recordSet = preparedStatement.executeQuery();
			while(recordSet.next()) {
				tantoJigyoCd.add(recordSet.getString("JIGYO_CD"));
				tantoJigyoKubun.add(recordSet.getString("JIGYO_KUBUN"));
			} 

			//���i��i���C�j��S�������ꍇ�A���i��i���AB�A���AB�j���Y������ 2007/3/23�C��
			if (tantoJigyoCd.contains("00154")){
				tantoJigyoCd.add("00152");
				tantoJigyoCd.add("00153");
				tantoJigyoCd.add("00155");
				tantoJigyoCd.add("00156");
			}
			
			Map result = new HashMap();
			result.put("tantoJigyoCd"    , tantoJigyoCd);
			result.put("tantoJigyoKubun" , tantoJigyoKubun);
			return result;

		} catch (SQLException ex) {
			throw new DataAccessException("�A�N�Z�X����e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}		
		
	}
	
	
	
	/**
	 * ���Y�Ɩ��S���҂̃A�N�Z�X�Ǘ������폜����B(�����폜)
	 * @param connection			    �R�l�N�V����
	 * @param gyomuTantoPk             �Ɩ��S���ҏ���L�[
	 * @throws DataAccessException     �폜���ɗ�O�����������ꍇ
	 */
	public void deleteAccessKanri(
		Connection connection,
		GyomutantoPk gyomuTantoPk)
		throws DataAccessException
	{
		String query =
			"DELETE FROM ACCESSKANRI"
				+ " WHERE"
				+ "   GYOMUTANTO_ID = ?"
				;
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,gyomuTantoPk.getGyomutantoId());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("�A�N�Z�X������폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}	
	
	
	
	/**
	 * ���Y�Ɩ��S���҂̃A�N�Z�X��������X�V����B<!--.-->
	 * ���Y�Ɩ��S���҂̃A�N�Z�X�������S�č폜���āA�V�K�œo�^����B<br>
	 * ��O�����̌^���AselectAccessKanri()�̖߂�l�Ƃ͈Ⴄ�̂Œ��ӁB<br>
	 * <table border="1">
	 * 	<tr>
	 * 		<td>No</td>
	 * 		<td>�L�[��</td>
	 * 		<td>�^</td>
	 * 		<td>�l</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>1</td>
	 * 		<td>tantoJigyoCd</td>
	 * 		<td>ArrayList</td>
	 * 		<td>�S�����ƃR�[�h</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>2</td>
	 * 		<td>tantoJigyoKubun</td>
	 * 		<td>ArrayList</td>
	 * 		<td>�S�����Ƌ敪</td>
	 * 	</tr>
	 * </table><br>
	 * �{���\�b�h�ł́A���ڂ́u���l�v�ɂ��Ă͍l�����Ȃ��B<br>
	 * 
	 * @param connection            �R�l�N�V����
	 * @param gyomuTantoPk          �Ɩ��S���ҏ���L�[
	 * @param updateMap             �S�����Ə��i���ƃR�[�h�A���Ƌ敪�j
	 * @return int                  �o�^����
	 * @throws DataAccessException  DB�A�N�Z�X���ɃG���[�ƂȂ����ꍇ
	 */
	public int updateAccessKanri(
		Connection   connection,
		GyomutantoPk gyomuTantoPk,
		Map          updateMap)
		throws DataAccessException
	{	
		//�폜
		deleteAccessKanri(connection, gyomuTantoPk);
		
		//�S�����Ə����擾����
		List tantoJigyoCdList    = (List)updateMap.get("tantoJigyoCd");
		List tantoJigyoKubunList = (List)updateMap.get("tantoJigyoKubun");				
		
		AccessKanriInfo insertInfo = new AccessKanriInfo();
		insertInfo.setGyomutantoId(gyomuTantoPk.getGyomutantoId());
		
		int count = 0;
		for(int i=0; i<tantoJigyoCdList.size(); i++){
			insertInfo.setJigyoCd((String)tantoJigyoCdList.get(i));
			insertInfo.setJigyoKubun((String)tantoJigyoKubunList.get(i));
			//�V�K�o�^
			insertAccessKanriInfo(connection, insertInfo);
			count++;
		}
		
		return count;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
