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
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.ShinsainInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.InvalidLogonException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinsainPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.model.exceptions.*;
/**
 * �R�����̃��O�I���F�؂���������N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinsainAuthentication.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:47 $"
 */
public class ShinsainAuthentication implements IAuthentication {

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsainAuthentication() {
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
// 2006/12/07�@���u�j�@�ǉ������܂�
        
		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//�F��
			ShinsainInfoDao dao = new ShinsainInfoDao(UserInfo.SYSTEM_USER);
            
// 2007/02/03 ���u�j�@�ǉ���������
            /** ���O�C���i�F�ؑO�j */
            loginLog.info( " ���O�C���i�J�n�j, ���[�U��� : " + UserRole.SHINSAIN + " , ���O�C��ID : " + userid);
// 2007/02/03�@���u�j�@�ǉ������܂�
            
			if (!dao.authenticateShinsainInfo(connection, userid, password)) {
                logErrors= true ;                
				throw new InvalidLogonException(
					"���[�UID�܂��́A�p�X���[�h���Ⴂ�܂��B�R�������F���[�UID '"
						+ userid
						+ "' �p�X���[�h'"
						+ password
						+ "'");
			}
			//���O�C�����̎擾
			ShinsainPk pkInfo = new ShinsainPk();
			pkInfo.setShinsainNo(userid.substring(3,10));	//�R�����ԍ�(7��)
			pkInfo.setJigyoKubun(userid.substring(2,3));	//���Ƌ敪
			ShinsainInfo info = dao.selectShinsainInfo(connection, pkInfo);
			
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
						"���[�UID�̗L���������߂��Ă��܂��B�R�������F���[�UID '"
							+ userid
							+ "' �p�X���[�h'"
							+ password
							+ "'"
							, new ErrorInfo("errors.5013"));
				}
			}
            
// 2007/02/03 ���u�j�@�ǉ���������
            /** ���O�C���i�F�ؐ����j */
            loginLog.info( " ���O�C���i�I���j, ���[�U��� : " + UserRole.SHINSAIN + " , ���O�C��ID : " + userid);
// 2007/02/03�@���u�j�@�ǉ������܂�
            
			//���O�C�������R�����������[�U���ɃZ�b�g
			UserInfo userInfo = new UserInfo();
			userInfo.setShinsainInfo(info);
			userInfo.setRole(UserRole.SHINSAIN);
			
            //2005/10/20�ŏI���O�C�����ǉ�
			info.setLoginDate(new Date());		//�f�[�^�ۊǓ�
			dao.updateShinsainInfo(connection, info);

			success = true;
			return userInfo;

		} catch (DataAccessException e) {
            logErrors= true ;
			throw new ApplicationException(
				"�R�����F�ؒ���DB�G���[���������܂����B",
				new ErrorInfo("errors.4006"),
				e);
		} catch (NoDataFoundException e) {
            logErrors= true ;
			throw new ApplicationException(
				"�R�����F�،�A�R�������̎擾�Ɏ��s���܂����B",
				new ErrorInfo("errors.4006"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
                logErrors= true ;
				throw new ApplicationException(
				"�R�����F�ؒ���DB�G���[���������܂����B",
				new ErrorInfo("errors.4002"),
				e);
			}finally {
                if(logErrors)
                {
// 2007/02/03 ���u�j�@�ǉ���������
                    /** ���O�C���i�F�؎��s��j */
                    loginLog.info( " ���O�C���i���s�j, ���[�U��� : " + UserRole.SHINSAIN + " , ���O�C��ID : " + userid + " , �p�X���[�h : " + password);
// 2007/02/03�@���u�j�@�ǉ������܂�
                }
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
}
