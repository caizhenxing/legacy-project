/*
 * �쐬��: 2005/03/25
 *
 */
package jp.go.jsps.kaken.model.impl;

import java.sql.Connection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.go.jsps.kaken.model.IBukyokutantoMaintenance;
import jp.go.jsps.kaken.model.IShozokuMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.BukyokutantoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.InvalidLogonException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoPk;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.StringUtil;

/**
 * ���ǒS���҂̃��O�I���F�؂���������N���X�B
 * 
 * @author yoshikawa_h
 *
 */
public class BukyokutantoAuthentication {
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public BukyokutantoAuthentication() {
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
				BukyokutantoInfoDao dao = new BukyokutantoInfoDao(UserInfo.SYSTEM_USER);
				
// 2007/02/03 ���u�j�@�ǉ���������
                /** ���O�C���i�F�ؑO�j */
                loginLog.info( " ���O�C���i�J�n�j, ���[�U��� : " + UserRole.BUKYOKUTANTO + " , ���O�C��ID : " + userid);
// 2007/02/03�@���u�j�@�ǉ������܂�
                
				// 2005/04/08 �ǉ� ��������---------------------------------------------------
				// ���R ���[�UID�ƃp�X���[�h�̊m�F�̂���
				if (!dao.authenticateBukyokuInfo(connection, userid, password)) {
                    logErrors= true ;                    
					throw new InvalidLogonException(
						"���[�UID�܂��́A�p�X���[�h���Ⴂ�܂��B���ǒS���ҏ��F���[�UID '"
							+ userid
							+ "' �p�X���[�h'"
							+ password
							+ "'");
				}
				// �ǉ� �����܂�--------------------------------------------------------------
				
				//���O�C�����̎擾
				BukyokutantoPk pkInfo = new BukyokutantoPk();
				pkInfo.setBukyokutantoId(userid);
				BukyokutantoInfo info = dao.selectBukyokutantoInfo(connection, pkInfo);
				
//				//�����@�֒S���҂��폜����Ă���Ƃ��G���[
//				if(info.getDelFlgShozoku().equals("1")){
//					throw new InvalidLogonException(
//							"�����@�ւ̒S���҂��폜����Ă��܂��B�����@�փR�[�h'"
//								+ info.getShozokuCd() + "'"
//								, new ErrorInfo("errors.5035"));
//				}
				
				//2005.08.16 iso �����@�֓o�^���ɏ����@�֒S���҂̃p�X���[�h������悤�ɂȂ�A
				//�����@�ւœo�^���Ă��Ȃ����ǒS���҂����O�C���\�ƂȂ��Ă��܂����̂ŁA
				//�����œo�^�t���O�����ă��O�C�����䂷��悤�ɕύX
				if(StringUtil.isBlank(info.getRegistFlg())) {
                    logErrors= true ;
					throw new ApplicationException(
						"���ǒS���ҔF�،�A�o�^�ς݃t���O�̎擾�Ɏ��s���܂����B",
						new ErrorInfo("errors.4006")
						);
				} else if(IBukyokutantoMaintenance.REGIST_FLG_YET.equals(info.getRegistFlg())) {
                    logErrors= true ;
					throw new InvalidLogonException(
											"�����@�ւŖ��o�^�̕��ǒS���҂ł��B���ǒS���ҏ��F���[�UID '"
												+ userid
												+ "'");
				}
				
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
							"���[�UID�̗L���������߂��Ă��܂��B���ǒS���ҏ��F���[�UID '"
								+ userid
								+ "' �p�X���[�h'"
								+ password
								+ "'"
								, new ErrorInfo("errors.5013"));
					}
				}
				
				//�S�����Ǐ��
				BukyokutantoInfo[] tanto = dao.selectTantoBukyokuInfo(connection,pkInfo);
				if(tanto.length != 0){
					info.setTantoFlg(true);
				}else{
					info.setTantoFlg(false);
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
							"���[�U�̏����@�ւ����݂��܂���B���ǒS���ҏ��F���[�UID '"
								+ userid
								+ "' �p�X���[�h'"
								+ password
								+ "'"
								, new ErrorInfo("errors.5024"));
					}
				}
                
// 2007/02/03 ���u�j�@�ǉ���������
                /** ���O�C���i�F�ؐ����j */
                loginLog.info( " ���O�C���i�I���j, ���[�U��� : " + UserRole.BUKYOKUTANTO + " , ���O�C��ID : " + userid);
// 2007/02/03�@���u�j�@�ǉ������܂�
                
				//���O�C���������ǒS���ҏ������[�U���ɃZ�b�g
				UserInfo userInfo = new UserInfo();
				userInfo.setBukyokutantoInfo(info);
				userInfo.setRole(UserRole.BUKYOKUTANTO);

				return userInfo;

			} catch (DataAccessException e) {
                logErrors= true ;
				throw new ApplicationException(
					"���ǒS���ҔF�ؒ���DB�G���[���������܂����B",
					new ErrorInfo("errors.4006"),
					e);
			} catch (NoDataFoundException e) {
                logErrors= true ;
				throw new ApplicationException(
					"���ǒS���ҔF�،�A���ǒS���ҏ��̎擾�Ɏ��s���܂����B",
					new ErrorInfo("errors.4006"),
					e);
			} finally {
                if(logErrors)
                {
// 2007/02/03 ���u�j�@�ǉ���������
                    /** ���O�C���i�F�؎��s��j */
                    loginLog.info( " ���O�C���i���s�j, ���[�U��� : " + UserRole.BUKYOKUTANTO + " , ���O�C��ID : " + userid + " , �p�X���[�h : " + password);
// 2007/02/03�@���u�j�@�ǉ������܂�
                }
				DatabaseUtil.closeConnection(connection);
			}
		}
}
