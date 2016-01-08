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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.go.jsps.kaken.model.IAuthentication;
import jp.go.jsps.kaken.model.IShozokuMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShozokuInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.InvalidLogonException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.DateUtil;

/**
 * �����@�֒S���Ҏ҂̃��O�I���F�؂���������N���X�B
 * 
 * ID RCSfile="$RCSfile: ShozokutantoAuthentication.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:47 $"
 */
public class ShozokutantoAuthentication implements IAuthentication {

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShozokutantoAuthentication() {
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
			ShozokuInfoDao dao = new ShozokuInfoDao(UserInfo.SYSTEM_USER);
            
// 2007/02/03 ���u�j�@�ǉ���������
            /** ���O�C���i�F�ؑO�j */
            loginLog.info( " ���O�C���i�J�n�j, ���[�U��� : " + UserRole.SHOZOKUTANTO + " , ���O�C��ID : " + userid);
// 2007/02/03�@���u�j�@�ǉ������܂�
            
			if (!dao.authenticateShozokuInfo(connection, userid, password)) {
                logErrors= true ;                
				throw new InvalidLogonException(
					"���[�UID�܂��́A�p�X���[�h���Ⴂ�܂��B�����@�֒S���ҏ��F���[�UID '"
						+ userid
						+ "' �p�X���[�h'"
						+ password
						+ "'");
			}
			//���O�C�����̎擾
			ShozokuPk pkInfo = new ShozokuPk();
			pkInfo.setShozokuTantoId(userid);
			ShozokuInfo info = dao.selectShozokuInfo(connection, pkInfo);
		
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
						"���[�UID�̗L���������߂��Ă��܂��B�����@�֒S���ҏ��F���[�UID '"
							+ userid
							+ "' �p�X���[�h'"
							+ password
							+ "'"
							, new ErrorInfo("errors.5013"));
				}
			}

			//2005.08.08 iso ���O�C���҂��������Ă���@�ւ̑��݂��`�F�b�N����i���݂��Ȃ��ꍇ�̓��O�C���s�j
			//�_�~�[�R�[�h�̏ꍇ�́A�@�փ}�X�^�ɑ��݂��Ȃ��Ă����O�C����������B
			if(!info.getShozokuCd().equals(IShozokuMaintenance.OTHER_KIKAN_CODE)) {
				MasterKikanInfoDao masterKikanInfoDao = new MasterKikanInfoDao(UserInfo.SYSTEM_USER);
				int kikanCount = masterKikanInfoDao.countShozokuInfo(connection,info.getShozokuCd());
				
				if(kikanCount < 1){
                    logErrors= true ;
					//�����@�ւ����݂��Ȃ��ꍇ
					throw new InvalidLogonException(
						"���[�U�̏����@�ւ����݂��܂���B�����S���ҏ��F���[�UID '"
							+ userid
							+ "' �p�X���[�h'"
							+ password
							+ "'"
							, new ErrorInfo("errors.5024"));
				}
			}
			
// 2007/02/03 ���u�j�@�ǉ���������
            /** ���O�C���i�F�ؐ����j */
            loginLog.info( " ���O�C���i�I���j, ���[�U��� : " + UserRole.SHOZOKUTANTO + " , ���O�C��ID : " + userid);
// 2007/02/03�@���u�j�@�ǉ������܂�
            
			//���O�C�����������@�֒S���ҏ������[�U���ɃZ�b�g
			UserInfo userInfo = new UserInfo();
			userInfo.setShozokuInfo(info);
			userInfo.setRole(UserRole.SHOZOKUTANTO);

			return userInfo;

		} catch (DataAccessException e) {
            logErrors= true ;
			throw new ApplicationException(
				"�����@�֒S���ҔF�ؒ���DB�G���[���������܂����B",
				new ErrorInfo("errors.4006"),
				e);
		} catch (NoDataFoundException e) {
            logErrors= true ;
			throw new ApplicationException(
				"�����@�֒S���ҔF�،�A�����@�֒S���ҏ��̎擾�Ɏ��s���܂����B",
				new ErrorInfo("errors.4006"),
				e);
		} finally {
            if(logErrors)
            {
// 2007/02/03 ���u�j�@�ǉ���������
                /** ���O�C���i�F�؎��s��j */
                loginLog.info( " ���O�C���i���s�j, ���[�U��� : " + UserRole.SHOZOKUTANTO + " , ���O�C��ID : " + userid + " , �p�X���[�h : " + password);
// 2007/02/03�@���u�j�@�ǉ������܂�
            }
			DatabaseUtil.closeConnection(connection);
		}
	}
}
