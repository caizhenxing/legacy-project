/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.model.IRuleMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.RuleInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.RuleInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �eID�E�p�X���[�h�̔��s���[���Ǘ��N���X.<br><br>
 * 
 * �T�v�F<br>
 * �h�c�p�X���[�h���s���[���e�[�u���F�eID�E�p�X���[�h�̔��s���[�����Ǘ�
 */
public class RuleMaintenance implements IRuleMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O. */
	protected static Log log = LogFactory.getLog(RuleMaintenance.class);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^.
	 */
	public RuleMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IRuleMaintenance
	//---------------------------------------------------------------------

	/**
	 * ���s���[���̐V�K�쐬.<br><br>
	 * 
	 * ������addInfo�����̂܂ܕԋp����B
	 * 
	 * @param userInfo	UserInfo
	 * @param addInfo	RuleInfo
	 * @return ������addInfo(RuleInfo)
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.RuleInfo)
	 */
	public RuleInfo insert(UserInfo userInfo, RuleInfo addInfo)
		throws ApplicationException {
			return addInfo;
	}

	/**
	 * ���s���[���̍X�V.<br><br>
	 * 
	 * ID�E�p�X���[�h�̔��s���[�����X�V����B<br><br>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 *     RULEINFO				-- ID�E�p�X���[�h���s���[���e�[�u��
	 * SET
	 *     TAISHO_ID = ?		-- �Ώێ�ID
	 *     ,MOJISU_CHK = ?		-- ������
	 *     ,CHAR_CHK1 = ?		-- �啶���E�������̍���
	 *     ,CHAR_CHK2 = ?		-- �A���t�@�x�b�g�Ɛ����̍���
	 *     ,CHAR_CHK3 = ?		-- �\��1
	 *     ,CHAR_CHK4 = ?		-- �\��2
	 *     ,CHAR_CHK5 = ?		-- �\��3
	 *     ,YUKO_DATE = ?		-- �L������
	 *     ,BIKO = ?			-- ���l
	 * WHERE
	 *     TAISHO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TAISHO_ID</td><td>������updateInfo�̕ϐ�taishoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>MOJISU_CHK</td><td>������updateInfo�̕ϐ�mojisuChk</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>CHAR_CHK1</td><td>������updateInfo�̕ϐ�charChk1</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>CHAR_CHK2</td><td>������updateInfo�̕ϐ�charChk2</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>CHAR_CHK3</td><td>������updateInfo�̕ϐ�charChk3</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>CHAR_CHK4</td><td>������updateInfo�̕ϐ�charChk4</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>CHAR_CHK5</td><td>������updateInfo�̕ϐ�charChk5</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>YUKO_DATE</td><td>������updateInfo�̕ϐ�yukoDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BIKO</td><td>������updateInfo�̕ϐ�biko</td></tr>
	 * </table><br>
	 * 
	 * �������̃o�C���h�ϐ�
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TAISHO_ID</td><td>������updateInfo�̕ϐ�taishoId</td></tr>
	 * </table><br>
	 * 
	 * @param userInfo		UserInfo
	 * @param updateInfo	�X�V���(RuleInfo)
	 * @return �Ȃ�
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.RuleInfo)
	 */
	public void update(UserInfo userInfo, RuleInfo updateInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//���s���[�����̍X�V
			//---------------------------------------
			RuleInfoDao dao = new RuleInfoDao(userInfo);
			dao.updateRuleInfo(connection, updateInfo);
			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���s���[���X�V����DB�G���[���������܂����B",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"���s���[���X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * ���s���[���̍X�V.<br><br>
	 * 
	 * ��������ID�E�p�X���[�h�̔��s���[�����X�V����B<br><br>
	 * 
	 * ���N���X��update(UserInfo, RuleInfo)���\�b�h���ĂсA���s���[�����X�V����B<br>
	 * �����ɁA������userInfo�Ƒ�����updateList�̗v�f(RuleInfo)��n���B<br>
	 * ������updateList��size�����J��Ԃ��B
	 * 
	 * @param userInfo		UserInfo
	 * @param updateList	�X�V��񃊃X�g(List)
	 * @return �Ȃ�
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#update(jp.go.jsps.kaken.model.vo.UserInfo, java.util.List)
	 */
	public void updateAll(UserInfo userInfo, List updateList)
		throws ApplicationException {

		for(int i = 0; i < updateList.size(); i++) {
			update(userInfo, (RuleInfo)updateList.get(i));
		}
	}

	/**
	 * ���s���[���̍폜.<br><br>
	 * 
	 * �󃁃\�b�h�B
	 * 
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.RuleInfo)
	 */
	public void delete(UserInfo userInfo, RuleInfo deleteInfo)
		throws ApplicationException {
	}
	
	/**
	 * ���s���[���̎擾.<br><br>
	 * 
	 * ID�E�p�X���[�h�̔��s���[�����擾����B<br><br>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     TAISHO_ID		-- �Ώێ�ID
	 *     ,MOJISU_CHK		-- ������
	 *     ,CHAR_CHK1		-- �啶���E�������̍���
	 *     ,CHAR_CHK2		-- �A���t�@�x�b�g�Ɛ����̍���
	 *     ,CHAR_CHK3		-- �\��1
	 *     ,CHAR_CHK4		-- �\��2
	 *     ,CHAR_CHK5		-- �\��3
	 *     ,YUKO_DATE		-- �L������
	 *     ,BIKO		-- ���l
	 * FROM
	 *     RULEINFO A		-- ID�E�p�X���[�h���s���[���e�[�u��
	 * WHERE
	 *     TAISHO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TAISHO_ID</td><td>������updateInfo�̕ϐ�taishoId</td></tr>
	 * </table><br>
	 * 
	 * @param userInfo	UserInfo
	 * @param pkInfo	���s���[��PK���(RulePk)
	 * @return ���s���[�����(RuleInfo)
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.RulePk)
	 */
	public RuleInfo select(UserInfo userInfo, RulePk pkInfo)
		throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			RuleInfoDao dao = new RuleInfoDao(userInfo);
			return dao.selectRuleInfo(connection, pkInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���s���[���Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * ���s���[���ꗗ�̎擾.<br><br>
	 * 
	 * ID�E�p�X���[�h�̔��s���[����񃊃X�g���擾����B<br>
	 * �ԋp���ꂽ���X�g�̗v�f�ɁAMap�ōs�������B<br><br>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     TAISHO_ID		-- �Ώێ�ID
	 *     ,MOJISU_CHK		-- ������
	 *     ,CHAR_CHK1		-- �啶���E�������̍���
	 *     ,CHAR_CHK2		-- �A���t�@�x�b�g�Ɛ����̍���
	 *     ,CHAR_CHK3		-- �\��1
	 *     ,CHAR_CHK4		-- �\��2
	 *     ,CHAR_CHK5		-- �\��3
	 *     ,YUKO_DATE		-- �L������
	 *     ,BIKO		-- ���l
	 * FROM
	 *     RULEINFO A		-- ID�E�p�X���[�h���s���[���e�[�u��
	 * 
	 * ORDER BY TAISHO_ID
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * @param userInfo	UserInfo
	 * @return ���s���[����񃊃X�g
	 * 
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public List selectList(UserInfo userInfo) throws ApplicationException {
	
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select = "SELECT "
						+ " * "
						+ " FROM RULEINFO A";						
		StringBuffer query = new StringBuffer(select);

		//�\�[�g���i�Ώ�ID�̏����j
		query.append(" ORDER BY TAISHO_ID");

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		//-----------------------
		// ���X�g�擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.select(connection, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���[���ݒ�f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * validate���\�b�h.<br><br>
	 * 
	 * ���̓G���[���������Ƃ��A��O��throw����B<br><br>
	 * 
	 * ���̓G���[���Ȃ���΁A������info�����̂܂ܕԋp����B
	 * 
	 * @param userInfo	UserInfo
	 * @param info		RuleInfo
	 * @return ������info(RuleInfo)
	 * @see jp.go.jsps.kaken.model.IRuleMaintenance#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.RuleInfo)
	 */
	public RuleInfo validate(UserInfo userInfo, RuleInfo info)
		throws ApplicationException, ValidationException {

			//�G���[���ێ��p���X�g
			List errors = new ArrayList();

			//-----���̓G���[���������ꍇ�͗�O���Ȃ���-----
			if (!errors.isEmpty()) {
				throw new ValidationException(
					"���s���[���Ǘ��f�[�^�`�F�b�N���ɃG���[��������܂����B",
					errors);
			}
			return info;
	}
}
