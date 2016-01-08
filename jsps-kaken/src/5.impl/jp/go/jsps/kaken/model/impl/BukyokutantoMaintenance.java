/*
 * �쐬��: 2005/03/24
 *
 */
package jp.go.jsps.kaken.model.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.go.jsps.kaken.model.IBukyokutantoMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.BukyokutantoInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.BukyokuSearchInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoPk;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���ǒS���ҏ��Ǘ��N���X.<br><br>
 * 
 * <b>�T�v:</b><br>
 * ���ǒS���ҏ����Ǘ�����B<br><br>
 * 
 * �g�p�e�[�u��<br>
 * <table>
 * <tr><td>���ǒS���ҏ��e�[�u��</td><td>�F���ǒS���҂̊�{�����Ǘ�</td></tr>
 * </table>
 */
public class BukyokutantoMaintenance implements IBukyokutantoMaintenance{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(BukyokutantoMaintenance.class);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public BukyokutantoMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IBukyokutantoMaintenance
	//---------------------------------------------------------------------
	
	/**
	 * �p�X���[�h��ύX����.<br/><br/>
	 * 
	 * <b>1.���ǒS���ҏ��̎擾</b><br/>
	 * �@���ǒS���҃e�[�u������A�����Ŏw�肳��郌�R�[�h�����擾����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     BUKYOKU.BUKYOKUTANTO_ID              -- ���ǒS����ID
	 *     ,BUKYOKU.PASSWORD                    -- �p�X���[�h
	 *     ,BUKYOKU.TANTO_NAME_SEI              -- �S���Җ��i���j
	 *     ,BUKYOKU.TANTO_NAME_MEI              -- �S���Җ��i���j
	 *     ,BUKYOKU.BUKA_NAME                   -- �S���ҕ��ۖ�
	 *     ,BUKYOKU.KAKARI_NAME                 -- �S���ҌW��
	 *     ,BUKYOKU.SHOZOKU_CD                  -- �����@�փR�[�h
	 *     ,BUKYOKU.BUKYOKU_TEL                 -- �d�b�ԍ�
	 *     ,BUKYOKU.BUKYOKU_FAX	                -- FAX�ԍ�
	 *     ,BUKYOKU.BUKYOKU_EMAIL               -- Email
	 *     ,BUKYOKU.BUKYOKU_CD                  -- ���ǃR�[�h
	 *     ,BUKYOKU.DEFAULT_PASSWORD            -- �f�t�H���g�p�X���[�h
	 *     ,BUKYOKU.REGIST_FLG                  -- �o�^�ς݃t���O
	 *     ,BUKYOKU.DEL_FLG                     -- �폜�t���O
	 *     ,SHOZOKU.YUKO_DATE                   -- �L������
	 *     ,SHOZOKU.DEL_FLG AS DEL_FLG_SHOZOKU  -- �����S���ҍ폜�t���O
	 * FROM
	 *     BUKYOKUTANTOINFO BUKYOKU             -- ���ǒS���ҏ��e�[�u��
	 * INNER JOIN 
	 *     SHOZOKUTANTOINFO SHOZOKU             -- �����S���ҏ��e�[�u��
	 * ON 
	 *     BUKYOKU.SHOZOKU_CD = SHOZOKU.SHOZOKU_CD
	 * WHERE
	 *     BUKYOKUTANTO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������pkInfo�̕ϐ�bukyokuTantoId</td></tr>
	 * </table><br/><br/> 
	 * 
	 * <b>2.���݂̃p�X���[�h���`�F�b�N</b><br/>
	 * �@1.�Ŏ擾�������ǒS���ҏ��̌��݂̃p�X���[�h�ƁA��O����oldPassword���r����B<br/>
	 * �@��v���Ȃ��Ƃ��A��O��throw����B<br/><br/>
	 * 
	 * <b>3.���݂̃p�X���[�h���X�V</b><br/>
	 * �@�p�X���[�h��V�����p�X���[�h�֕ύX���邽�߂ɁA���ǒS���҃e�[�u�����X�V����B<br/><br/>
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE 
	 *     BUKYOKUTANTOINFO 
	 * SET
	 *     PASSWORD = ?
	 * WHERE
	 *     BUKYOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * �ύX�l
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>PASSWORD</td><td>��l����newPassword</td></tr>
	 * </table><br/>
	 * �i���ݏ���
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������pkInfo�̕ϐ�bukyokuTantoId</td></tr>
	 * </table><br/><br/> 
	 * 
	 * @param userInfo		UserInfo
	 * @param pkInfo 		�p�X���[�h��ύX���郌�R�[�h��PK�iBukyokutantoPk�j
	 * @param oldPassword ���p�X���[�h
	 * @param newPassword �V�p�X���[�h
	 * @return true
	 * @see jp.go.jsps.kaken.model.IBukyokutantoMaintenance#changePassword(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.BukyokutantoPk, java.lang.String, java.lang.String)
	 */
	public boolean changePassword(UserInfo userInfo, BukyokutantoPk pkInfo, String oldPassword, String newPassword) throws ApplicationException, ValidationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//���ǒS���ҏ��̎擾
			//---------------------------------------
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			BukyokutantoInfo info = dao.selectBukyokutantoInfo(connection, pkInfo);

			//---------------------------------------
			//���݂̃p�X���[�h���`�F�b�N����B
			//---------------------------------------
			if (!info.getPassword().equals(oldPassword)) {
				//�G���[���ێ��p���X�g
				List errors = new ArrayList();
				errors.add(new ErrorInfo("errors.2001", new String[] { "���݂̃p�X���[�h" }));
				throw new ValidationException(
						"�p�X���[�h�ύX�f�[�^�`�F�b�N���ɃG���[��������܂����B",
						errors);
			}

			//---------------------------------------
			//���݂̃p�X���[�h���X�V����B
			//---------------------------------------
			if(dao.changePasswordBukyokutantoInfo(connection,pkInfo,newPassword)){
				//�X�V����I��
				success = true;
			}

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�p�X���[�h�ύX����DB�G���[���������܂���",
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
					"�p�X���[�h�ύX����DB�G���[���������܂���",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		return success;
	}
	
	
	/**
	 * �S�����ǂ̎擾.<br><br>
	 * 
	 * ���ǒS���҂̒S�����镔�ǂ��擾����B
	 * �ȉ���SQL�����s���擾�������ʂ�ԋp����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT 
	 *     BUKYOKUTANTO_ID     -- ���ǒS����ID
	 *     ,BUKYOKU_CD         -- ���ǃR�[�h
	 *     ,SHOZOKU_CD         -- �����@�փR�[�h
	 *     ,BIKO               -- ���l
	 * FROM 
	 *     TANTOBUKYOKUKANRI   -- �S�����ǊǗ�
	 * WHERE 
	 *     BUKYOKUTANTO_ID = ?
	 * AND 
	 *     BUKYOKU_CD = ?      -- ������pkInfo�̕ϐ�bukyokuCd�ɒl���Z�b�g����Ă���ꍇ�ɏ�����ǉ�
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������pkInfo�̕ϐ�bukyokuTantoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKU_CD</td><td>������pkInfo�̕ϐ�bukyokuCd</td></tr>
	 * </table><br/><br/> 
	 * 
	 * @param userInfo		UserInfo
	 * @param pkInfo		�S�����ǂ��擾���郌�R�[�h��PK�iBukyokutantoPk�j
	 * @return BukyokutantoInfo[]	���ǒS���ҏ��
	 * @throws ApplicationException
	 */
	public BukyokutantoInfo[] select(UserInfo userInfo, BukyokutantoPk pkInfo)
			throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			return dao.selectTantoBukyokuInfo(connection, pkInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	

	// 2005/04/07 �ǉ���������---------------------------------------------
	// ���R ���ǒS���ҏ��̓o�^�A�X�V�A�폜�A�p�X���[�h�ύX�����ǉ��̂���

	/** 
	 * ���ǒS���҈ꗗ���̎擾.<br/><br/>
	 * 
	 * <b>1.���ǒS���ҏ��̎擾�B</b><br/>
	 * �@���ǒS���҃e�[�u������A�����Ŏw�肳��郌�R�[�h�����擾����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT 
	 *      A.BUKYOKUTANTO_ID,      -- ���ǒS����ID
	 *      A.TANTO_NAME_SEI,       -- �S���Җ�(��)
	 *      A.TANTO_NAME_MEI,       -- �S���Җ�(��)
	 *      A.BUKA_NAME,            -- �S�����ۖ�
	 *      A.REGIST_FLG            -- �o�^�ς݃t���O
	 * FROM 
	 * 	    BUKYOKUTANTOINFO A      -- ���ǒS���ҏ��e�[�u��
	 * INNER JOIN                   -- �S�����ǃt���O��true�̏ꍇ�ɒǉ�
	 * 	    TANTOBUKYOKUKANRI B 	
	 * ON 
	 * 	    A.BUKYOKUTANTO_ID = B.BUKYOKUTANTO_ID
	 * WHERE 
	 * 	    A.DEL_FLG = 0
	 *      AND A.SHOZOKU_CD = ?
	 * ORDER BY 
	 *      A.REGIST_FLG DESC,
	 *      A.BUKYOKUTANTO_ID
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������info�̕ϐ�shozokuCd</td></tr>
	 * </table><br/><br/> 
	 * 
	 * <b>2.�y�[�W���̎擾</b><br/>
	 * �@1.�Ŏ擾�������ǒS���ҏ�񂩂�y�[�W�����擾����B<br/>
	 * <br/>
	 * 
	 * @param		userInfo	���[�U���
	 * @param		info		��������
	 * @return		Page		�y�[�W���
	 * @exception	ApplicationExcepiton
	 */
	public Page searchBukyokuList(UserInfo userInfo, BukyokuSearchInfo info)
		throws ApplicationException {
		
		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		
		String select = 
			"SELECT A.BUKYOKUTANTO_ID, " +
					"A.TANTO_NAME_SEI, " +
					"A.TANTO_NAME_MEI, " +
					"A.BUKA_NAME, " +
					"A.REGIST_FLG " +
			"FROM BUKYOKUTANTOINFO A ";	
					
		StringBuffer query = new StringBuffer(select);
		
		//�S�����Ǐ������ꍇ�͒S�����ǊǗ��e�[�u����INNER JOIN���Č�������@
		if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){	
			query.append(" INNER JOIN TANTOBUKYOKUKANRI B " +
					 " ON A.BUKYOKUTANTO_ID = B.BUKYOKUTANTO_ID ");
		}
		//�폜�t���O
		query.append("WHERE A.DEL_FLG = 0");
		
		if(info.getShozokuCd() != null && !info.getShozokuCd().equals("")){	
			//�����@�֖��i�R�[�h�j�i���S��v�j
			query.append(" AND A.SHOZOKU_CD = '" + EscapeUtil.toSqlString(info.getShozokuCd()) + "'");
		}
				
		//�\�[�g���i�����@�֖��i�R�[�h�j�̏����j
		query.append(" ORDER BY A.REGIST_FLG DESC, A.BUKYOKUTANTO_ID");		
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// �y�[�W�擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection, info, query.toString());
		} catch (DataAccessException e) {
			log.error("�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
		
	
	/** 
	 * ���ǃR�[�h�̃`�F�b�N.<br/>
	 * 
	 * ���͂��ꂽ���ǃR�[�h�����ǃ}�X�^�Ɋ܂܂�邩�ǂ������m�F����B<br/><br/>	
	 * 
	 * <b>1.���ǃR�[�h�̌��̎擾�B</b><br/>
	 * �@���ǃ}�X�^�e�[�u������A�����Ŏw�肳��郌�R�[�h���̌����擾����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT 
	 * 			COUNT(*) COUNT 
	 * FROM 
	 * 			MASTER_BUKYOKU 
	 * WHERE 
	 * 			BUKYOKU_CD = ?
	 * 
	 * �������̕��ǃR�[�h����������ꍇ��WHERE��Ɉȉ���IN���g�p����B
	 * 
	 * WHERE 
	 * 			BUKYOKU_CD IN(?, ?, ���)
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKU_CD</td><td>������set�̊e�ϐ�</td></tr>
	 * </table><br/><br/> 
	 *
	 * <b>2.���ǃR�[�h�̊m�F</b><br/>
	 * �@1.�Ŏ擾�������ƈ����̔z��̒������r���Ĉ�v���邩�ǂ������m�F����B<br/>
	 * �@��v���Ȃ��ꍇ��ValidateException��Ԃ��B<br/>
	 * <br/>
	 *  
	 * @param		userInfo	���[�U���
	 * @param		set			���ǃR�[�hSet	
	 * @exception	ApplicationException
	 * 
	 */
	public void CheckBukyokuCd(UserInfo userInfo, Set set)
		throws ApplicationException {
		
		Connection connection = null;
		
		int count = 0;
		try{
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			count = dao.CheckBukyokuCd(connection, (HashSet)set);
			if(set.size() != count){
				List errors = new ArrayList();
				errors.add(new ErrorInfo("errors.2001", new String[] {"���ǃR�[�h"}));
				throw new ValidationException("���ǃR�[�h���Ԉ���Ă��܂��B", errors);	
			}
			
		} catch (DataAccessException e) {
			log.error("���ǃR�[�h�m�F����DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"���ǃR�[�h�m�F����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}	
	
	
	/** 
	 * ���ǒS���ҏ��̓o�^.<BR>
	 * 
	 * ���ǒS���ҏ��o�^�A���ǒS���ҏ��C�����ɌĂ΂��B<br/><br/>	
	 * 
	 * <b>1.���ǂ̊m�F</b><br/>
	 * �@���N���X��select���\�b�h���Ăяo���� ���ǒS���҂̒S�����镔�ǂ����邩�m�F����B<br/>
	 * <br/> 
	 * 
	 * <b>2.���ǂ̍폜�B</b><br/>
	 * �@1.�ŕ��ǂ�����ꍇ�Ɏ��s�����B
	 *   �S�����ǊǗ��e�[�u������A�����Ŏw�肳��郌�R�[�h�����폜����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * DELETE FROM 
	 * 			TANTOBUKYOKUKANRI 
	 * WHERE 
	 * 			BUKYOKUTANTO_ID = ? 
	 * 			AND SHOZOKU_CD = ? 
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������info�̕ϐ�bukyokutantoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������info�̕ϐ�shozokuCd</td></tr>
	 * </table><br/><br/> 
	 *
	 * <b>3.�p�X���[�h�̎擾</b><br/>
	 * �@�V�K�o�^���̂ݎ��s�����B<br/>
	 * �@RULEINFO�e�[�u����胋�[�����擾���A�p�X���[�h�𐶐�����B<br/>
	 * <br/> 
	 * 
	 * <b>4.���ǒS���ҏ��f�[�^�̍X�V</b><br/>
	 *   ���ǒS���ҏ��̓o�^�E�X�V�ɁA���ǒS���ҏ��e�[�u�����X�V����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>	
	 * UPDATE 
	 * 			BUKYOKUTANTOINFO 
	 * SET 
	 * 			TANTO_NAME_SEI = ? ,
	 * 			TANTO_NAME_MEI = ? ,
	 * 			BUKA_NAME = ? , 
	 * 			KAKARI_NAME = ? , 
	 * 			BUKYOKU_TEL = ? , 
	 * 			BUKYOKU_FAX = ? , 
	 * 			BUKYOKU_EMAIL = ? , 	
	 * 			REGIST_FLG = 1 ", 
	 * 			PASSWORD = ?  --------------------�V�K�o�^���̂�
	 * WHERE 
	 * 			BUKYOKUTANTO_ID = ? 
	 * 			AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * �ύX�l
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TANTO_NAME_SEI</td><td>������info�̕ϐ�tantoNameSei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TANTO_NAME_MEI</td><td>������info�̕ϐ�tantoNameMei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKA_NAME</td><td>������info�̕ϐ�bukaName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KAKARI_NAME</td><td>������info�̕ϐ�kakariName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKU_TEL</td><td>������info�̕ϐ�bukyokuTel</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKU_FAX</td><td>������info�̕ϐ�bukyokuFax</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKU_EMAIL</td><td>������info�̕ϐ�bukyokuEmail</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>PASSWORD</td><td>������info�̕ϐ�password(�V�K�o�^���̂�)</td></tr>
	 * </table><br/>
	 * �i���ݏ���
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������pkInfo�̕ϐ�bukyokuTantoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * <b>5.���ǃR�[�h�̍X�V</b><br/>
	 *   ���ǒS���ҏ��e�[�u���ɕ��ǃR�[�h��o�^����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * 	INSERT INTO 
	 * 			TANTOBUKYOKUKANRI(
	 *                          BUKYOKUTANTO_ID,  
	 *                          SHOZOKU_CD, 
	 *                          BUKYOKU_CD) 
	 * 	VALUES(?, ?, ?)
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������info�̕ϐ�bukyokutantoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������info�̕ϐ�shozokuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKU_CD</td><td>������info�̕ϐ�bukyokuCd</td></tr>
	 * </table><br/><br/>   
	 * 
	 * @param		userInfo	���[�U���
	 * @param		info		���ǒS���ҏ��
	 * @return		info		�o�^�f�[�^���i�[�������ǒS���ҏ��
	 * @exception	ApplicationException
	 * 
	 */
	public BukyokutantoInfo setBukyokuData(UserInfo userInfo, BukyokutantoInfo info)
		throws ApplicationException {
		Connection connection = null;
	
		BukyokutantoPk pk = new BukyokutantoPk();
		pk.setBukyokutantoId(info.getBukyokutantoId());		
		//select���\�b�h��p���ĒS�����ǊǗ��e�[�u���Ƀf�[�^�����邩�m�F
		BukyokutantoInfo[] tanto = select(userInfo, pk);

		try{
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			
			if(tanto.length != 0){		
				//�S�����ǊǗ��e�[�u���Ƀf�[�^������ꍇ�̓f�[�^�̍폜���s��
				dao.deleteBukyokuCd(connection, info);
			}
			
			//2005/06/01 �폜 ��������--------------------------------------
			//���R �p�X���[�h�̓V�X�e���Ǘ��҂̏����@�֓o�^���ɓo�^���邽��
			//���ǒS���ҏ��̓o�^�ł͍s��Ȃ�(Dao�̕ϐ�newPassword���폜�j
			/*
			//�V�p�X���[�h
			String newPassword = null;
			
			//�V�K�o�^���̂݃p�X���[�h��ݒ肷��
			if(info.getAction() != null && info.getAction().equals("add")){
			
				//RULEINFO�e�[�u����胋�[���擾����
				RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
				RulePk rulePk = new RulePk();
				rulePk.setTaishoId(ITaishoId.BUKYOKUTANTO);
				newPassword = rureInfoDao.getPassword(connection, rulePk);
				//�p�X���[�h�𕔋ǒS�����ێ��N���X�ɐݒ肷��
				info.setPassword(newPassword);
			}
			*/
			
			//�f�[�^���X�V����
			//dao.updateBukyokuData(connection, info, newPassword);
			dao.updateBukyokuData(connection, info);

			//2005/06/30�ǉ�
			//�V�K�o�^���A�p�X���[�h��\������ׁA�p�X���[�h���擾����
			if(info.getAction() != null && info.getAction().equals("add")){
				String pwd = dao.getTantoPassword(connection, info);
				info.setPassword(pwd);
			}
			//2005/06/30�ǉ�����
			
			
		} catch (DataAccessException e) {
			log.error("���ǒS���҃f�[�^�o�^����DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"���ǒS���҃f�[�^�o�^����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return info;
	}
	
	
	/** 
	 * ���ǒS���ҏ����擾����.<BR><BR>
	 * 
	 * <b>1.���ǒS���ҏ��̎擾</b><br/>
	 * �@���ǒS���҃e�[�u������A�����Ŏw�肳��郌�R�[�h�����擾����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     BUKYOKU.BUKYOKUTANTO_ID              -- ���ǒS����ID
	 *     ,BUKYOKU.PASSWORD                    -- �p�X���[�h
	 *     ,BUKYOKU.TANTO_NAME_SEI              -- �S���Җ��i���j
	 *     ,BUKYOKU.TANTO_NAME_MEI              -- �S���Җ��i���j
	 *     ,BUKYOKU.BUKA_NAME                   -- �S���ҕ��ۖ�
	 *     ,BUKYOKU.KAKARI_NAME                 -- �S���ҌW��
	 *     ,BUKYOKU.SHOZOKU_CD                  -- �����@�փR�[�h
	 *     ,BUKYOKU.BUKYOKU_TEL                 -- �d�b�ԍ�
	 *     ,BUKYOKU.BUKYOKU_FAX                 -- FAX�ԍ�
	 *     ,BUKYOKU.BUKYOKU_EMAIL               -- Email
	 *     ,BUKYOKU.BUKYOKU_CD                  -- ���ǃR�[�h
	 *     ,BUKYOKU.DEFAULT_PASSWORD            -- �f�t�H���g�p�X���[�h
	 *     ,BUKYOKU.REGIST_FLG                  -- �o�^�ς݃t���O
	 *     ,BUKYOKU.DEL_FLG                     -- �폜�t���O
	 *     ,BUKYOKU.SHOZOKU.YUKO_DATE           -- �L������
	 *     ,SHOZOKU.DEL_FLG AS DEL_FLG_SHOZOKU  -- �����@�֒S���҂̍폜�t���O
	 * FROM
	 *     BUKYOKUTANTOINFO BUKYOKU             -- ���ǒS���ҏ��e�[�u��
	 * INNER JOIN 
	 *     SHOZOKUTANTOINFO SHOZOKU             -- �����S���ҏ��e�[�u��
	 * ON 
	 *     BUKYOKU.SHOZOKU_CD = SHOZOKU.SHOZOKU_CD
	 * WHERE
	 *     BUKYOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������pkInfo�̕ϐ�bukyokuTantoId</td></tr>
	 * </table><br/><br/> 
	 * 
	 * <b>2.���ǃR�[�h�̎擾</b><br/>
	 *   �S�����ǊǗ��e�[�u������A�����Ŏw�肳��郌�R�[�h�����擾����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * 	SELECT 
	 * 			BUKYOKU_CD          -- ���ǃR�[�h 
	 * 	FROM 
	 * 			TANTOBUKYOKUKANRI   -- �S�����ǊǗ��e�[�u��
	 * 	WHERE 
	 * 			BUKYOKUTANTO_ID = ? 
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������info�̕ϐ�bukyokutantoId</td></tr>
	 * </table><br/><br/> 
	 *
	 * <b>3.�����S���ҏ��̎擾</b><br/>
	 * �@���N���X��selectShozokuData���\�b�h���Ăяo���ď����S���ҏ����擾����B<br/>
	 * <br/> 
	 * 
	 * @param		userInfo	���[�U���
	 * @param		info		���ǒS���R�[�h�̊i�[���ꂽ���ǒS���ҏ��
	 * @return		info		�o�^�f�[�^���i�[�������ǒS���ҏ��
	 * @exception	ApplicationException
	 * 
	 */
	public BukyokutantoInfo selectBukyokuData(UserInfo userInfo, BukyokutantoInfo info)
			throws ApplicationException {
		
		Connection connection = null;
		ArrayList bukyokuList = new ArrayList();
		
		try{
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			BukyokutantoPk pk = new BukyokutantoPk();
			pk.setBukyokutantoId(info.getBukyokutantoId());
			//���ǒS���ҏ��̎擾
			info = dao.selectBukyokutantoInfo(connection, pk);
			//���ǃR�[�h�̎擾
			bukyokuList = dao.selectTantoBukyokuKanri(connection, info);
			info.setBukyokuList(bukyokuList);
			//�����S���ҏ��̎擾
			info = selectShozokuData(userInfo, info);	
		}catch(DataAccessException e) {
			log.error("�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return info;		
	}
	
	
	/** 
	 * ���ǒS���ҏ��폜.<BR><BR>
	 * 
	 * <b>1.���ǒS���ҏ��̍폜</b><br/>
	 * �@���ǒS���҃e�[�u���ŁA�����Ŏw�肳��郌�R�[�h���̍폜�t���O�ɂP��ݒ肷��B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE 
	 * 			BUKYOKUTANTOINFO 
	 * SET 
	 * 			DEL_FLG = 1 
	 * WHERE 
	 * 			BUKYOKUTANTO_ID = ?
	 * 			AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������info�̕ϐ�bukyokuTantoId</td></tr>
	 * </table><br/><br/>  
	 * 
	 * <b>2.���ǃR�[�h�̍폜�B</b><br/>
	 * �@1.�ŕ��ǂ�����ꍇ�Ɏ��s�����B
	 *   �S�����ǊǗ��e�[�u������A�����Ŏw�肳��郌�R�[�h�����폜����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * DELETE FROM 
	 * 			TANTOBUKYOKUKANRI 
	 * WHERE 
	 * 			BUKYOKUTANTO_ID = ? 
	 * 			AND SHOZOKU_CD = ? 
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������info�̕ϐ�bukyokutantoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������info�̕ϐ�shozokuCd</td></tr>
	 * </table><br/><br/> 
	 * 
	 * @param		userInfo	���[�U���
	 * @param		info		���ǒS���ҏ��
	 * @exception	ApplicationException
	 * 
	 */
	public void delete(UserInfo userInfo, BukyokutantoInfo info)
		throws ApplicationException {
			
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			//���ǒS���ҏ��̍폜
			dao.deleteBukyokuData(connection, info);
			//���ǃR�[�h�̍폜
			dao.deleteBukyokuCd(connection, info);
			
		}catch(DataAccessException e) {
			log.error("���ǒS���ҏ��폜����DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"���ǒS���ҏ��폜����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}				
	}
	
    // 2005/04/22 �ǉ� ��������------------------------------------------------------
    // ���R �����@�֒S���ҍ폜���A�������@�ւɑ����镔�ǒS���ҏ����폜���邽��
	/**
	 * ���ǒS���ҏ��폜.<br><br>
	 * 
	 * �������@�ւɑ����镔�ǒS���ҏ����ꊇ�ō폜����B<BR>
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre> 
	 * UPDATE 
	 * 		BUKYOKUTANTOINFO
	 * SET 
	 * 		DEL_FLG = 1 
	 * WHERE 
	 * 		SHOZOKU_CD = ? 
	 * 		AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������shozokuCd</td></tr>
	 * </table><br/><br/>  
	 * 
	 * @param userInfo		���[�U���
	 * @param shozokuCd	�폜�Ώۏ���CD
	 * @throws ApplicationException
	 */
	public void deleteAll(UserInfo userInfo, String shozokuCd)
		throws ApplicationException {
		
		boolean success = false;
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			//���ǒS���ҏ��̍폜
			dao.deleteBukyokuDataAll(connection, shozokuCd);
			//�S�����ǃR�[�h�̍폜
			// 2005/04/22 ����ł́A�S�����Ǐ��͍폜���Ȃ��B
			//dao.deleteBukyokuCdAll(connection, shozokuCd);
			
			success = true;
		}catch(DataAccessException e) {
			log.error("���ǒS���ҏ��폜����DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"���ǒS���ҏ��폜����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
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
						"���ǒS���ҏ��폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
			DatabaseUtil.closeConnection(connection);
		}				
	}
	// �ǉ� �����܂�------------------------------------------------------------------
	
	/** 
	 * �����S���҂ŕ��ǒS���҂̃p�X���[�h��ύX����.<BR><BR>
	 * �p�X���[�h��DEFAULT_PASSWORD�ɖ߂����B<BR><BR>
	 * 
	 * <b>1.�p�X���[�h�̍X�V</b><br/>
	 *   �p�X���[�h�̍X�V�ɁA���ǒS���ҏ��e�[�u�����X�V����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>	
	 * UPDATE 
	 * 		BUKYOKUTANTOINFO
	 * SET
	 * 		PASSWORD = DEFAULT_PASSWORD
	 * WHERE
	 * 		BUKYOKUTANTO_ID = ?
	 * 		AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������info�̕ϐ�bukyokutantoId</td></tr>
	 * </table><br/><br/>   
	 * 
	 * 
	 * <b>2.�����S���ҏ��̎擾</b><br/>
	 * �@�����@�֒S���ҏ����擾����B
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>	
	 * SELECT 
	 * 		M.SHOZOKU_CD                    -- �����@�փR�[�h
	 * 		,M.SHOZOKU_NAME_KANJI           -- �@�֖��́i���{��j
	 * 		,SHOZOKU.SHOZOKU_NAME_EIGO      -- �@�֖��́i�p��j
	 * FROM 
	 * 		BUKYOKUTANTOINFO B              -- ���ǒS���ҏ��e�[�u��
	 * INNER JOIN
	 * 		MASTER_KIKAN M                  -- �@�փ}�X�^
	 * ON 
	 * 		B.SHOZOKU_CD = M.SHOZOKU_CD 
	 * INNER JOIN 
	 * 		SHOZOKUTANTOINFO SHOZOKU        -- �����@�֒S���ҏ��e�[�u��
	 * ON 
	 * 		SHOZOKU.SHOZOKU_CD = B.SHOZOKU_CD
	 * WHERE 
	 * 		B.BUKYOKUTANTO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������info�̕ϐ�bukyokutantoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * <b>3.�V�p�X���[�h�̎擾</b><br/>
	 * �@�f�t�H���g�p�X���[�h�ɖ߂����p�X���[�h���擾����B
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>	
	 * SELECT
	 *     BUKYOKU.BUKYOKUTANTO_ID              -- ���ǒS����ID
	 *     ,BUKYOKU.PASSWORD                    -- �p�X���[�h
	 *     ,BUKYOKU.TANTO_NAME_SEI              -- �S���Җ��i���j
	 *     ,BUKYOKU.TANTO_NAME_MEI              -- �S���Җ��i���j
	 *     ,BUKYOKU.BUKA_NAME                   -- �S���ҕ��ۖ�
	 *     ,BUKYOKU.KAKARI_NAME                 -- �S���ҌW��
	 *     ,BUKYOKU.SHOZOKU_CD                  -- �����@�փR�[�h
	 *     ,BUKYOKU.BUKYOKU_TEL                 -- �d�b�ԍ�
	 *     ,BUKYOKU.BUKYOKU_FAX                 -- FAX�ԍ�
	 *     ,BUKYOKU.BUKYOKU_EMAIL               -- Email
	 *     ,BUKYOKU.BUKYOKU_CD                  -- ���ǃR�[�h
	 *     ,BUKYOKU.DEFAULT_PASSWORD            -- �f�t�H���g�p�X���[�h
	 *     ,BUKYOKU.REGIST_FLG                  -- �o�^�ς݃t���O
	 *     ,BUKYOKU.DEL_FLG                     -- �폜�t���O
	 *     ,SHOZOKU.YUKO_DATE                   -- �L������
	 *     ,SHOZOKU.DEL_FLG AS DEL_FLG_SHOZOKU  -- �����S���ҍ폜�t���O
	 * FROM
	 *     BUKYOKUTANTOINFO BUKYOKU             -- ���ǒS���ҏ��e�[�u��
	 * INNER JOIN 
	 *     SHOZOKUTANTOINFO SHOZOKU             -- �����S���ҏ��e�[�u��
	 * ON 
	 *     BUKYOKU.SHOZOKU_CD = SHOZOKU.SHOZOKU_CD
	 * WHERE
	 *     BUKYOKUTANTO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������pkInfo�̕ϐ�bukyokuTantoId</td></tr>
	 * </table><br/><br/> 
	 * 
	 * @param		userInfo	���[�U���
	 * @param		info		���ǒS���ҏ��
	 * @return		info		�ύX�p�X���[�h���i�[�������ǒS���ҏ��
	 * @exception	ApplicationException
	 */
	public BukyokutantoInfo changeBukyokuPassword(
		UserInfo userInfo,
		BukyokutantoInfo info)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//�\���ҏ��̎擾
			//---------------------------------------
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			
			//2005/04/13 �폜 ��������---------------------------------------------------
			//���R �p�X���[�h�Đݒ�̓f�t�H���g�p�X���[�h�ɖ߂��̂Ńp�X���[�h�̎擾�͕s�v�Ȃ���
			
			//String newPassword = null;
			//RULEINFO�e�[�u����胋�[���擾����
			//RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			//RulePk rulePk = new RulePk();
			//rulePk.setTaishoId(ITaishoId.SHINSEISHA);

			//�p�X���[�h���Đݒ肷��
			//	newPassword = rureInfoDao.getPassword(connection, rulePk);
			//�폜 �����܂�--------------------------------------------------------------
			
			success = dao.originPassword(connection, info);
			//�����S���ҏ����擾����
			info = dao.selectShozokuData(connection, info);
			
			//2005/04/13 �ǉ� ��������---------------------------------------------------
			//���R �p�X���[�h�Ď擾�̂���
			//�p�X���[�h���Ď擾����
			info.setPassword(dao.selectBukyokutantoInfo(connection, info).getPassword());
			//�ǉ� �����܂�--------------------------------------------------------------
			success = true;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�p�X���[�h�Đݒ蒆��DB�G���[���������܂���",
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
					"�p�X���[�h�Đݒ蒆��DB�G���[���������܂���",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		return info;
	}
	
	
	/** 
	 * �����@�֏����擾����.<BR><BR>
	 * 
	 * �����@�֒S���҂̘A����⏊���@�֖������擾����B<BR><BR>
	 * 
	 * <b>1.�����S���ҏ��̎擾</b><br/>
	 *   �@�փ}�X�^�e�[�u������A�����Ŏw�肳��郌�R�[�h�����擾����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>	
	 * SELECT 
	 * 		M.SHOZOKU_CD                    -- �����@�փR�[�h
	 * 		,M.SHOZOKU_NAME_KANJI           -- �@�֖��́i���{��j
	 * 		,SHOZOKU.SHOZOKU_NAME_EIGO      -- �@�֖��́i�p��j
	 * FROM 
	 * 		BUKYOKUTANTOINFO B              -- ���ǒS���ҏ��e�[�u��
	 * INNER JOIN
	 * 		MASTER_KIKAN M                  -- �@�փ}�X�^
	 * ON 
	 * 		B.SHOZOKU_CD = M.SHOZOKU_CD 
	 * INNER JOIN 
	 * 		SHOZOKUTANTOINFO SHOZOKU        -- �����@�֒S���ҏ��e�[�u��
	 * ON 
	 * 		SHOZOKU.SHOZOKU_CD = B.SHOZOKU_CD
	 * WHERE 
	 * 		B.BUKYOKUTANTO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>BUKYOKUTANTO_ID</td><td>������info�̕ϐ�bukyokutantoId</td></tr>
	 * </table><br/><br/>   
	 * 
	 * @param		userInfo	���[�U���
	 * @param		info		�����R�[�h���i�[���ꂽ���ǒS���ҏ��
	 * @return		info		���ǒS���ҏ��
	 * @exception	ApplicationException
	 * 
	 */
	public BukyokutantoInfo selectShozokuData(UserInfo userInfo, BukyokutantoInfo info)
		throws ApplicationException {
	
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			
			//�����S���ҏ����擾����
			info = dao.selectShozokuData(connection, info);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����S���ҏ��擾����DB�G���[���������܂���",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return info;
	}
	

	// �ǉ� �����܂�--------------------------------------------------------
	
	//2005/06/01 �ǉ� ��������----------------------------------------------
	//�ؖ������s�pCSV�̕��ǒS���҃f�[�^�擾�̂���
	
	/**
	 * �ؖ������s�pCSV�f�[�^���擾����.<BR><BR>
	 * 
	 * ��O����list�̒l�i�����@�ւ�csv�f�[�^)��ArrayList�Ɋi�[����B<BR><BR>
	 *  
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT 
	 * 		SUBSTR(BUKYOKUTANTO_ID,0,7) CODE
	 * 		, PASSWORD 
	 * FROM 
	 * 		BUKYOKUTANTOINFO 
	 * WHERE 
	 * 		SHOZOKU_CD = ? 
	 * 		AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������info�̕ϐ�shozokuCd</td></tr>
	 * </table><br/><br/>   
	 * 
	 * �ȉ���1.2�̏�����SQL�̌��ʌ����J��Ԃ��B<br>
	 * 1.�ݒ�t�@�C������profileName, subjectDn, subjectAltName, pubkeyAlgo, keyLength, p12Flag���擾���A
	 * SQL�̊e���s���ʂƋ���List�Ɋi�[����B<br>
	 * 2.�i�[����List��ArrayList�Ɋi�[����B<br><br>
	 * 
	 * �����@�֏��ƕ��ǒS���ҏ���S�Ċi�[����ArrayList��Ԃ��B<br>
	 * 
	 * @param userInfo		UserInfo
	 * @param info			ShozokuInfo
	 * @param list			List
	 * @return	�ؖ������s�pCSV�f�[�^
	 * @throws ApplicationException
	 */
	public List getShomeiCsvData(UserInfo userInfo, ShozokuInfo info, List list)
		throws ApplicationException {	
	
		Connection connection = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			BukyokutantoInfoDao dao = new BukyokutantoInfoDao(userInfo);
			
			//�����S���ҏ����擾����
			list = dao.getShomeiCsvData(connection, info, list);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����S���ҏ��擾����DB�G���[���������܂���",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return list;
	}
	//�ǉ� �����܂�---------------------------------------------------------
}
