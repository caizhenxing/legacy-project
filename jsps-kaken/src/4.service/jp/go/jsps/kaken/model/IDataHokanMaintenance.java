/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.util.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

/**
 * �f�[�^�ۊǂ��s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IDataHokanMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IDataHokanMaintenance {
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** �߂�lMap�L�[�l�F1���R�����ʁi�Q�Ɨp�j */
	public static final String KEY_SHINSAKEKKA_1ST = IShinsaKekkaMaintenance.KEY_SHINSAKEKKA_1ST;
	
	/** �߂�lMap�L�[�l�F2���R������ */
	public static final String KEY_SHINSAKEKKA_2ND = IShinsaKekkaMaintenance.KEY_SHINSAKEKKA_2ND;	
	
	
	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
	/**
	 * �f�[�^�ۊǏ��������s����B
	 * @param userInfo
	 * @param jigyoKanriPk
	 * @param yukoKigen
	 * @return �ۊǌ����i�\���f�[�^�̃��R�[�h���j
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public int dataHokanInvoke(UserInfo userInfo, JigyoKanriPk jigyoKanriPk, Date yukoKigen)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * �\��������������B
	 * @param userInfo				���s���郆�[�U���
	 * @param searchInfo           �\�����������
	 * @return						�擾�����\�����y�[�W�I�u�W�F�N�g
	 * @throws ApplicationException	
	 * @throws NoDataFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public Page searchApplication(UserInfo userInfo, ShinseiSearchInfo searchInfo) 
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * PDF�ϊ���̃t�@�C�����擾����B
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws ApplicationException
	 */
	public FileResource getPdfFileRes(UserInfo userInfo,
	                                   ShinseiDataPk shinseiDataPk)
		throws ApplicationException;
	
	
	/**
	 * CSV�o�͗p�̐\���ҏ�����������B
	 * 
	 * @param userInfo				
	 * @param searchInfo			
	 * @return						
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public List searchCsvData(UserInfo userInfo, ShinseiSearchInfo searchInfo) 
		throws ApplicationException;
	
	
	/**
	 * �����g�DCSV�o�͗p�̐\���ҏ�����������B
	 * 
	 * @param userInfo				
	 * @param searchInfo			
	 * @return						
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public List searchKenkyuSoshikiCsvData(UserInfo userInfo, ShinseiSearchInfo searchInfo) 
		throws ApplicationException;
	
	
	/**
	 * �R�����ʂ�Ԃ��B
	 * 1���R�����ʁi�Q�Ɨp�j��2���R�����ʂ�Map�`���ŕԂ��B
	 * Map�̃L�[��[KEY_SHINSAKEKKA_1ST], [KEY_SHINSAKEKKA_2ND]�ƂȂ�B
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Map getShinsaKekkaBoth(UserInfo userInfo,
								   ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * 1���R�����ʁi�Q�Ɨp�j��Ԃ��B
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public ShinsaKekkaReferenceInfo getShinsaKekkaReferenceInfo(UserInfo userInfo,
																 ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * 2���R�����ʂ�Ԃ��B
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public ShinsaKekka2ndInfo getShinsaKekka2nd(UserInfo userInfo,
												 ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * 1���R�����ʓ��͏��i�ڍׁj��Ԃ��B
	 * @param userInfo
	 * @param shinsaKekkaPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public ShinsaKekkaInputInfo select1stShinsaKekka(UserInfo userInfo,
														ShinsaKekkaPk shinsaKekkaPk)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * �R�����ʓ��͏��̃t�@�C�����\�[�X���擾����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�R�����ʃe�[�u����L�[�B
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public FileResource getHyokaFileRes(UserInfo userInfo,
										 ShinsaKekkaPk pkInfo) 
		throws ApplicationException;
	
	
	/**
	 * �w�肳�ꂽ�\���f�[�^�̐��E���t�@�C����Ԃ��B
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */	
	public FileResource getSuisenFileRes(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException;	
	
}