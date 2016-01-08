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
import jp.go.jsps.kaken.model.dao.impl.*;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseishaInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.InvalidLogonException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.DateUtil;

/**
 * �\���҂̃��O�I���F�؂���������N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinseishaAuthentication.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:47 $"
 */
public class ShinseishaAuthentication implements IAuthentication {

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinseishaAuthentication() {
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
			ShinseishaInfoDao dao = new ShinseishaInfoDao(UserInfo.SYSTEM_USER);
            
// 2007/02/03 ���u�j�@�ǉ���������
            /** ���O�C���i�F�ؑO�j */
            loginLog.info( " ���O�C���i�J�n�j, ���[�U��� : " + UserRole.SHINSEISHA + " , ���O�C��ID : " + userid);
// 2007/02/03�@���u�j�@�ǉ������܂�
			
            if (!dao.authenticateShinseishaInfo(connection, userid, password)) {
                logErrors= true ;                
				throw new InvalidLogonException(
					"���[�UID�܂��́A�p�X���[�h���Ⴂ�܂��B�\���ҏ��F���[�UID '"
						+ userid
						+ "' �p�X���[�h'"
						+ password
						+ "'");
			}
			//���O�C�����̎擾
			ShinseishaPk pkInfo = new ShinseishaPk();
			pkInfo.setShinseishaId(userid);
			ShinseishaInfo info = dao.selectShinseishaInfo(connection, pkInfo);
			
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
						"���[�UID�̗L���������߂��Ă��܂��B�\���ҏ��F���[�UID '"
							+ userid
							+ "' �p�X���[�h'"
							+ password
							+ "'"
							, new ErrorInfo("errors.5013"));
				}
			}

			//���O�C���҂��������Ă���@�ւ̑��݂��`�F�b�N����i���݂��Ȃ��ꍇ�̓��O�C���s�j
			//�_�~�[�R�[�h�̏ꍇ�́A�@�փ}�X�^�ɑ��݂��Ȃ��Ă����O�C����������B
			if(!info.getShozokuCd().equals(IShozokuMaintenance.OTHER_KIKAN_CODE)) {
				MasterKikanInfoDao masterKikanInfoDao = new MasterKikanInfoDao(UserInfo.SYSTEM_USER);
				int kikanCount = masterKikanInfoDao.countShozokuInfo(connection,info.getShozokuCd());
			
				if(kikanCount < 1){
                    logErrors= true ;
					//�����@�ւ����݂��Ȃ��ꍇ
					throw new InvalidLogonException(
						"���[�U�̏����@�ւ����݂��܂���B�\���ҏ��F���[�UID '"
							+ userid
							+ "' �p�X���[�h'"
							+ password
							+ "'"
							, new ErrorInfo("errors.5024"));
				}
			}

		    //2005/08/09 takano �����҃}�X�^�̑��݃`�F�b�N���s�Ȃ��B�������� -----
			MasterKenkyushaInfoDao kenkyushaDao = new MasterKenkyushaInfoDao(UserInfo.SYSTEM_USER);
			KenkyushaInfo kenkyushaInfo = new KenkyushaInfo();
			kenkyushaInfo.setKenkyuNo(info.getKenkyuNo());
			kenkyushaInfo.setShozokuCd(info.getShozokuCd());
			int kenkyuCount = kenkyushaDao.countKenkyushaInfo(connection,
															  kenkyushaInfo,
															  false);	//�폜�t���O[1]�͏����B
			if(kenkyuCount < 1){
                logErrors= true ;
				//�����҃}�X�^�ɑ��݂��Ȃ��ꍇ
				throw new InvalidLogonException(
					"���[�U�͌����҃}�X�^�ɑ��݂��܂���B�\���ҏ��F���[�UID '"
						+ userid
						+ "' �p�X���[�h'"
						+ password
						+ "'"
						, new ErrorInfo("errors.5003"));
			}
		    //2005/08/09 takano �����҃}�X�^�̑��݃`�F�b�N���s�Ȃ��B�����܂� ----   
            
// 2007/02/03 ���u�j�@�ǉ���������
            /** ���O�C���i�F�ؐ����j */
            loginLog.info( " ���O�C���i�I���j, ���[�U��� : " + UserRole.SHINSEISHA + " , ���O�C��ID : " + userid);
// 2007/02/03�@���u�j�@�ǉ������܂�
            
			// ���O�C�������\���ҏ������[�U���ɃZ�b�g
			UserInfo userInfo = new UserInfo();
			userInfo.setShinseishaInfo(info);
			userInfo.setRole(UserRole.SHINSEISHA);

			return userInfo;

		} catch (DataAccessException e) {
            logErrors= true ;
			throw new ApplicationException(
				"�\���ҔF�ؒ���DB�G���[���������܂����B",
				new ErrorInfo("errors.4006"),
				e);
		} catch (NoDataFoundException e) {
            logErrors= true ;
			throw new ApplicationException(
				"�\���ҔF�،�A�\���ҏ��̎擾�Ɏ��s���܂����B",
				new ErrorInfo("errors.4006"),
				e);
		} finally {
            if(logErrors)
            {
// 2007/02/03 ���u�j�@�ǉ���������
                /** ���O�C���i�F�؎��s��j */
                loginLog.info( " ���O�C���i���s�j, ���[�U��� : " + UserRole.SHINSEISHA + " , ���O�C��ID : " + userid + " , �p�X���[�h : " + password);
// 2007/02/03�@���u�j�@�ǉ������܂�
            }
			DatabaseUtil.closeConnection(connection);
		}
	}
}