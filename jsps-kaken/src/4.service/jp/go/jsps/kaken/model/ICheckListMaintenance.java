/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.sql.Connection;
import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * �`�F�b�N���X�g�����̊Ǘ����s���C���^�[�t�F�[�X�B
 * 
 */
public interface ICheckListMaintenance {

// 20050715
	/** �߂�lMap�L�[�l�F�󗝕s�󗝃��X�g */
	public static final String KEY_JURIFUJURI_LIST		= "key_jurifujuri_list";
// Horikoshi

	/**
	 * �`�F�b�N���X�g�ꗗ�\���p�̃f�[�^���擾����B
	 * @param userInfo ���s���郆�[�U���
	 * @param info �`�F�b�N���X�g�e�[�u������p�f�[�^���X�g
	 * @return �y�[�W���
	 * @throws ApplicationException		
	 */
	public Page selectCheckList(UserInfo userInfo, CheckListSearchInfo info)
            throws ApplicationException;
// 20050627
    /**
     * �`�F�b�N���X�g�ꗗ�\���p�̃f�[�^���擾����B
     * @param userInfo ���s���郆�[�U���
     * @param info �`�F�b�N���X�g�e�[�u������p�f�[�^���X�g
     * @param blnFlg
     * @return �y�[�W���
     * @throws ApplicationException     
     */
	public Page selectCheckList(UserInfo userInfo, CheckListSearchInfo info, boolean blnFlg)
            throws ApplicationException;
// Horikoshi
		
	/**
	 * �`�F�b�N���X�g�\���p�̃f�[�^���擾����B
	 * @param userInfo		���s���郆�[�U���
	 * @param info			�`�F�b�N���X�g�e�[�u������p�f�[�^���X�g
	 * @return						�y�[�W���
	 * @throws ApplicationException
	 */
	public Page selectListData(UserInfo userInfo, CheckListSearchInfo info) throws ApplicationException;
		
	/**
	 * �`�F�b�N���X�g�̏�ID���擾����B
	 * @param userInfo		���s���郆�[�U���
	 * @param info			�`�F�b�N���X�g�e�[�u������p�f�[�^���X�g
	 * @return						��ID
	 * @throws ApplicationException
	 */
	public String checkJokyoId(UserInfo userInfo, CheckListSearchInfo info) throws ApplicationException;
	
	/**
	 * �`�F�b�N���X�g�̊m�菈�����s���B
	 * @param userInfo		���s���郆�[�U���
	 * @param info			�`�F�b�N���X�g�e�[�u������p�f�[�^���X�g
	 * @param isVersionUp	�m�肩�m��������𔻕�
	 * @throws ApplicationException
	 * @throws ValidationException
	 */
	public void checkListUpdate(UserInfo userInfo, CheckListSearchInfo info, boolean isVersionUp)
            throws ApplicationException, ValidationException;
	
	/**
	 * �`�F�b�N���X�g�̔�єԍ��\���p�̃f�[�^���擾����B
	 * @param userInfo		���s���郆�[�U���
	 * @param info			�`�F�b�N���X�g�e�[�u������p�f�[�^���X�g
	 * @return				�y�[�W���
	 * @throws ApplicationException
	 */
	public Page selectTobiList(UserInfo userInfo, CheckListSearchInfo info)
            throws ApplicationException;
	
	/**
	 * �`�F�b�N���X�g��񂪊w�U��t���������m�F����B
	 * @param userInfo ���s���郆�[�U���
	 * @param searchInfo �`�F�b�N���X�g�e�[�u������p�f�[�^���X�g
	 * @return ���������ǂ����̊m�F�t���O true���������/false��������O
	 * @throws ApplicationException
	 */
	public boolean checkLimitDate(UserInfo userInfo, CheckListSearchInfo searchInfo)
            throws ApplicationException;
	
	/**
	 * �ꊇ�󗝓o�^���s���B
	 * @param userInfo 	UserInfo	���[�U���
	 * @param shozokuArray	List		�����@��CD�̔z��
	 * @param jigyoArray	List		����ID�̔z��
	 * @param systemArray	List		�V�X�e����t�ԍ��̔z��
     * @param comment       String
	 * @throws ApplicationException  
	 */
// 20050721
//	public void juriAll(UserInfo userInfo, List shozokuArray, List jigyoArray, List systemArray) throws ApplicationException;
	public void juriAll(UserInfo userInfo, List shozokuArray, List jigyoArray,
            List systemArray, String comment)
            throws ApplicationException;
// Horikoshi

	/**
	 * �`�F�b�N���X�gCSV�o�͗p��List�쐬
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @return						�擾�������[�U���B
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public List searchCsvData(UserInfo userInfo, CheckListSearchInfo searchInfo) throws ApplicationException;

	/**
	 * �`�F�b�N���X�g�ꗗCSV�o�͗p��List�쐬
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @return						�擾�������[�U���B
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public List searchCsvDataIchiran(UserInfo userInfo, CheckListSearchInfo searchInfo)
            throws ApplicationException;

	
	//2005/05/19 �ǉ� ��������----------------------------------------------
	//���R�@�`�F�b�N���X�g��ʁA��єԍ����X�g��ʂ̃^�C�g�����擾�̂���
	
	/**
	 * �`�F�b�N���X�g�^�C�g�����擾
	 * 
	 * @param searchInfo		�������
	 * @return					�y�[�W���
	 * @throws ApplicationException
	 */
	public Page selectTitle(CheckListSearchInfo searchInfo)throws ApplicationException;
	//�ǉ� �����܂�---------------------------------------------------------
	
//	//2005/05/25 �ǉ� ��������----------------------------------------------
//	//���R PDF�t�@�C���p�X�擾�̂���
//	/**
//	 * �\��PDF�t�@�C���p�X�擾
//	 * 
//	 * @param userInfo	UserInfo
//	 * @param searchInfo	CheckListSearchInfo
//	 * @return	�\��PDF�t�@�C���p�X
//	 * @exception ApplicationException
//	 */
//	public String getPdfFilePath(UserInfo userInfo, CheckListSearchInfo searchInfo) throws ApplicationException;
//	
//	//�ǉ� �����܂�---------------------------------------------------------

	/**
	 * �\���pPDF���_�E�����[�h����B
	 * @param userInfo ���O�C���ҏ��
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public FileResource getPdfFile(UserInfo userInfo, CheckListSearchInfo searchInfo)
            throws ApplicationException;
	
	
	
// 20050714
//	/**
//	 * �`�F�b�N���X�g�ɊY������S�Ă̌����҂��擾
//	 * @param userInfo ���O�C���ҏ��
//	 * @param searchInfo					�������
//	 * @return								�����ҏ��
//	 * @throws ApplicationException
//	 */
//	public List getKenkyushaList(UserInfo userInfo, CheckListSearchInfo searchInfo) throws ApplicationException;

	/**
	 * List�Ɋi�[���ꂽ�����҂̑��݃`�F�b�N
     * @param userInfo ���O�C���ҏ��
     * @param searchInfo
	 * @param connection
	 * @return List ��������(True�F�����҂��S�đ��݁AFalse�F���݂��Ȃ������҂���)
	 * @throws ApplicationException
	 */
	public List chkKenkyushaExist(
					UserInfo userInfo,
					CheckListSearchInfo searchInfo,
					Connection connection
					) throws ApplicationException;

    /**
     * �����ҏ�񃊃X�g�̃`�F�b�N�i�����Ҕԍ��Ƌ@�փR�[�h�̑��݃`�F�b�N�j
     * @param userInfo ���O�C���ҏ��
     * @param searchInfo
     * @param shozokuCdArray
     * @param jigyoIdArray
     * @return List ��������(True�F�����҂��S�đ��݁AFalse�F���݂��Ȃ������҂���)
     * @throws ApplicationException
     */
	public List IkkatuKenkyushaExist(
					UserInfo userInfo,
					CheckListSearchInfo searchInfo,
					List shozokuCdArray,
					List jigyoIdArray
					) throws ApplicationException;

// Horikoshi

	/**
	 * ���发�ނ̒�o���o�́i��Ռ������A����̈挤���j
	 * @param userInfo ���O�C���ҏ��
	 * @param checkInfo �`�F�b�N���X�g��������
	 * @return�@FileResource�@�o�͏��CSV�t�@�E��
     * @throws ApplicationException
	 */
	public FileResource createOuboTeishutusho(
			UserInfo userInfo,
			CheckListSearchInfo checkInfo)
			throws ApplicationException;

	/**
	 * �󗝁A�s�󗝏��������s����
	 * @param userInfo ���O�C���ҏ��
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 * @throws NoDataFoundException
	 */
	public List CheckListAcceptUnacceptable(UserInfo userInfo, CheckListSearchInfo searchInfo)
            throws ApplicationException, NoDataFoundException;

	/**
	 * �󗝂����s
	 * @param checkInfo
	 * @param userInfo ���O�C���ҏ��
	 * @param connection
	 * @param shozokuArray
	 * @param jigyoArray
	 * @param systemArray
	 * @return
	 * @throws ApplicationException
	 */
//	public boolean CheckListJuri(CheckListSearchInfo checkInfo, UserInfo userInfo, Connection connection, List shozokuArray, List jigyoArray, List systemArray) throws ApplicationException;
	public List CheckListJuri(CheckListSearchInfo checkInfo, UserInfo userInfo,
            Connection connection, List shozokuArray, List jigyoArray, List systemArray)
            throws ApplicationException;

	/**
	 * �s�󗝂����s
	 * @param userInfo ���O�C���ҏ��
	 * @param connection
	 * @param shinseiDataPk
	 * @param comment
	 * @param seiriNo
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean EntryFujuriInfo(UserInfo userInfo, Connection connection,
            ShinseiDataPk shinseiDataPk, String comment, String seiriNo)
            throws NoDataFoundException, ApplicationException;

}