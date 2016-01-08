/*======================================================================
 *    SYSTEM      : 
 *    Source name : QuestonMaintenance.java
 *    Description : �A���P�[�g���ɍX�V���������N���X
 *
 *    Author      : Admin
 *    Date        : 2005/10/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *�@�@2005/10/27    1.0         Amemiya    �V�K�쐬
 *
 *====================================================================== 
 */package jp.go.jsps.kaken.model.impl;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.go.jsps.kaken.model.IQuestionMaintenance;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.impl.QuestionInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.QuestionInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;

/**
 * @author user1
 * 
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎����Q�ƁB �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� ��
 * �R�[�h�E�e���v���[�g
 */
public class QuestionMaintenance implements IQuestionMaintenance {
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(QuestionMaintenance.class);

	/** �V�X�e����t�ԍ��擾���g���C�� */
	protected static final int SYSTEM_NO_MAX_RETRY_COUNT = 
					ApplicationSettings.getInt(ISettingKeys.SYSTEM_NO_MAX_RETRY_COUNT);

	/**
	 *  �R���X�g���N�^
	 */
	public QuestionMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------
		
	/**
	 * �V�X�e����t�ԍ��̐���.<br><br>
	 * 
	 * WAS�̃V�X�e�����t���t�H�[�}�b�g���A��t�ԍ����擾����B<br>
	 * �t�H�[�}�b�g�p�^�[���|"yyyyMMddHHmmssSSS"
	 * 
	 * @return �V�X�e����t�ԍ�
	 */
	//2004/11/25 private �� pubic
	public synchronized static String getUketukeNo()
	{
		//�O�̂���1�~���b�X���[�v�����Ċm���ɕʔԍ���Ԃ��B
		try{
			Thread.sleep(1);
		}catch(InterruptedException e){
			e.printStackTrace();	//���ɉ����������Ȃ�
		}
		//���ݎ������V�X�e����t�ԍ��̃t�H�[�}�b�g�ɕϊ�����
		Date now = new Date();
		String systemNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(now);
		return systemNo;
		
	}
	
//	public synchronized static String getKinyuDate()
//	{
//		//���ݎ������V�X�e����t�ԍ��̃t�H�[�}�b�g�ɕϊ�����
//		Date now = new Date();
//		String systemNo1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);
//		return systemNo1;
//	}


	/*
	 * �A���P�[�g���f�[�^�V�K�o�^����
	 * 
	 * @see jp.go.jsps.kaken.model.IQuestionMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.QuestionInfo)
	 */
	public void insert(UserInfo userInfo, QuestionInfo addInfo) throws ApplicationException
	{

		Connection connection = null;
		boolean success = false;

		if ( log.isDebugEnabled() ){
			log.debug("�A���P�[�g���o�^�J�n");
		}
		
		//--------------------
		// �A���P�[�g�f�[�^�o�^
		//--------------------
		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
			addInfo.setUketukeNo(getUketukeNo());
//			addInfo.setKinyuDate(getKinyuDate());
			QuestionInfoDao dao = new QuestionInfoDao();
			//-- �o�^���ɃL�[���d�Ȃ����ꍇ�̓��g���C�������� --
			int count = 0;
			while (true) {
				try {
					//2005.11.04 iso ���[�U�ɂ����Dao��I��
					if(userInfo.getRole() != null) {
						if(userInfo.getRole().equals(UserRole.QUESTION_SHINSEISHA)) {
							dao.insertQuestionShinseishaInfo(connection, addInfo);
						} else if(userInfo.getRole().equals(UserRole.QUESTION_SHOZOKUTANTO)) {
							dao.insertQuestionShozokuInfo(connection, addInfo);
						} else if(userInfo.getRole().equals(UserRole.QUESTION_BUKYOKUTANTO)) {
							dao.insertQuestionBukyokuInfo(connection, addInfo);
						} else if(userInfo.getRole().equals(UserRole.QUESTION_SHINSAIN)) {
							dao.insertQuestionShinsainInfo(connection, addInfo);
						} else {
							throw new ApplicationException(
									"���[�U�̃��[����񂪑z��O�ł��B",
									new ErrorInfo("errors.system"));
						}
					} else {
						throw new ApplicationException(
								"���[�U�̃��[����񂪐ݒ肳��Ă��܂���B",
								new ErrorInfo("errors.system"));
					}
					success = true;
					break;
				} catch (DuplicateKeyException e) {
					count++;
					if (count < SYSTEM_NO_MAX_RETRY_COUNT) {
						if ( log.isDebugEnabled() ){
							log.debug("�A���P�[�g���o�^�ɑ�" + count + "�񎸔s���܂����B");
						}
						addInfo.setUketukeNo(getUketukeNo());
						 //�V�X�e����t�ԍ����Ď擾
						continue;
					} else {
						throw e;
					}
				}
			}
		}
		catch (DataAccessException e) {

			throw new ApplicationException("�A���P�[�g���o�^����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"), e);
		}
		finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("�A���P�[�g���o�^����DB�G���[���������܂����B",
						new ErrorInfo("errors.4001"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

}