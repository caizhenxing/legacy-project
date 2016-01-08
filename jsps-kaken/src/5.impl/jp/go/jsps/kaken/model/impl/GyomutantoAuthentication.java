/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.go.jsps.kaken.model.IAuthentication;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.AccessKanriDao;
import jp.go.jsps.kaken.model.dao.impl.GyomutantoInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.InvalidLogonException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.DateUtil;

/**
 * �Ɩ��S���҂̃��O�I���F�؂���������N���X�B
 * 
 * ID RCSfile="$RCSfile: GyomutantoAuthentication.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:48 $"
 */
public class GyomutantoAuthentication implements IAuthentication {

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public GyomutantoAuthentication() {
		super();
	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.IAuthentication#authenticate(java.lang.String, java.lang.String)
	 */
	public UserInfo authenticate(String userid, String password)
		throws InvalidLogonException, ApplicationException {
// 2007/02/03 ���u�j�@�ǉ���������
        /** ���O�i���O�C���j*/
        Log loginLog = LogFactory.getLog("login");
        boolean logErrors = false;
// 2007/02/03�@���u�j�@�ǉ������܂�
        
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//�F��
			GyomutantoInfoDao dao = new GyomutantoInfoDao(UserInfo.SYSTEM_USER);
            
// 2007/02/03 ���u�j�@�ǉ���������
            /** ���O�C���i�F�ؑO�j */
            loginLog.info( " ���O�C���i�J�n�j, ���[�U��� : " + UserRole.GYOMUTANTO + " , ���O�C��ID : " + userid);
// 2007/02/03�@���u�j�@�ǉ������܂�
            
			if (!dao.authenticateGyomutantoInfo(connection, userid, password)) {
                logErrors= true ;                
				throw new InvalidLogonException(
					"���[�UID�܂��́A�p�X���[�h���Ⴂ�܂��B�Ɩ��S���ҏ��F���[�UID '"
						+ userid
						+ "' �p�X���[�h'"
						+ password
						+ "'");
			}
			//���O�C�����̎擾
			GyomutantoPk pkInfo = new GyomutantoPk();
			pkInfo.setGyomutantoId(userid);
			GyomutantoInfo info = dao.selectGyomutantoInfo(connection, pkInfo);
			
			//ID�̗L���������`�F�b�N����
			Date date = info.getYukoDate();
			if(date != null){
				DateUtil yukoDate = new DateUtil(date);
				DateUtil now      = new DateUtil();
				//���ݓ��t�ƗL�����������r
				int hi = now.getElapse(yukoDate);
				if(hi < 0){
                    logErrors= true ;
					//�L�����������߂��Ă���ꍇ
					throw new InvalidLogonException(
						"���[�UID�̗L���������߂��Ă��܂��B�Ɩ��S���ҏ��F���[�UID '"
							+ userid
							+ "' �p�X���[�h'"
							+ password
							+ "'"
							, new ErrorInfo("errors.5013"));
				}
			}
            
// 2007/02/03 ���u�j�@�ǉ���������
            /** ���O�C���i�F�ؐ����j */
            loginLog.info( " ���O�C���i�I���j, ���[�U��� : " + UserRole.GYOMUTANTO + " , ���O�C��ID : " + userid);
// 2007/02/03�@���u�j�@�ǉ������܂�
            
			//�F�؂����������ꍇ�A�A�N�Z�X��������擾����
			AccessKanriDao accessDao = new AccessKanriDao(UserInfo.SYSTEM_USER);
			Map accessMap = accessDao.selectAccessKanri(connection, pkInfo);
			info.setTantoJigyoCd((Set)accessMap.get("tantoJigyoCd"));
			info.setTantoJigyoKubun((Set)accessMap.get("tantoJigyoKubun"));
			
			//���O�C�������Ɩ��S���ҏ������[�U���ɃZ�b�g
			UserInfo userInfo = new UserInfo();
			userInfo.setGyomutantoInfo(info);
			userInfo.setRole(UserRole.GYOMUTANTO);
			return userInfo;

		} catch (DataAccessException e) {
            logErrors= true ;
			throw new ApplicationException(
				"�Ɩ��S���ҔF�ؒ���DB�G���[���������܂����B",
				new ErrorInfo("errors.4006"),
				e);
		}catch (NoDataFoundException e) {
            logErrors= true ;
			throw new ApplicationException(
				"�Ɩ��S���ҔF�،�A�Ɩ��S���ҏ��̎擾�Ɏ��s���܂����B",
				new ErrorInfo("errors.4006"),
				e);
		} finally {
            if(logErrors)
            {
// 2007/02/03 ���u�j�@�ǉ���������
                /** ���O�C���i�F�؎��s��j */
                loginLog.info( " ���O�C���i���s�j, ���[�U��� : " + UserRole.GYOMUTANTO + " , ���O�C��ID : " + userid + " , �p�X���[�h : " + password);
// 2007/02/03�@���u�j�@�ǉ������܂�
            }
			DatabaseUtil.closeConnection(connection);
		}
	}
}