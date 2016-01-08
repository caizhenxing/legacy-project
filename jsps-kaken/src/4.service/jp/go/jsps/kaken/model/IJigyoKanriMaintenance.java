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

import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.JigyoKanriSearchInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
import jp.go.jsps.kaken.model.vo.ShoruiKanriPk;
import jp.go.jsps.kaken.model.vo.ShoruiKanriSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * ���ƊǗ������Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IJigyoKanriMaintenance.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/07/24 03:00:59 $"
 */
public interface IJigyoKanriMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** �߂�lMap�L�[�l�F���ƊǗ���� */
	public static final String KEY_JIGYOKANRI_INFO = "key_jigyokanri_info";
    
	/** �߂�lMap�L�[�l�F���ފǗ����X�g*/
	public static final String KEY_SHORUIKANRI_LIST   = "key_shoruikanri_list";
		
	/** �_�E�����[�h�t�@�C���t���O�B�Y�t�t�@�C���iWin�j */
	public static String FILE_FLG_TENPU_WIN = "0";
	
	/** �_�E�����[�h�t�@�C���t���O�B�Y�t�t�@�C���iMac�j */
	public static String FILE_FLG_TENPU_MAC = "1";
	
	/** �_�E�����[�h�t�@�C���t���O�B�]���p�t�@�C�� */
	public static String FILE_FLG_HYOKA = "2";


	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
	/**
	 * ���ƊǗ�����V�K�쐬����B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param addInfo				�쐬���郆�[�U���B
	 * @return						�V�K�o�^�������[�U���
	 * @throws ApplicationException	
	 */
	public JigyoKanriInfo insert(UserInfo userInfo,JigyoKanriInfo addInfo) throws ApplicationException;

	/**
	 * ���ފǗ�����V�K�쐬����B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param addInfo				�쐬���郆�[�U���B
	 * @return						�V�K�o�^�������[�U���
	 * @throws ApplicationException	
	 */
	public List insert(UserInfo userInfo,ShoruiKanriInfo addInfo) throws ApplicationException;

	/**
	 * ���ƊǗ������X�V����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param updateInfo			�X�V���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,JigyoKanriInfo updateInfo) throws ApplicationException;

	/**
	 * ���ƊǗ����̌��J�m������X�V����B
	 * ���J�m������X�V����ꍇ�́A�p�X���[�h�̃`�F�b�N���s���B
	 * @param userInfo				���s���郆�[�U���B
	 * @param updateInfo			�X�V���郆�[�U���B
	 * @param password				�p�X���[�h���B
	 * @throws ApplicationException
	 */
	public void updateKokaiKakutei(UserInfo userInfo,JigyoKanriPk[] jigyoPks, String kessaiNo, String password) throws ApplicationException;

	/**
	 * ���ƊǗ������폜����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param deleteInfo			�폜���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,JigyoKanriInfo deleteInfo) throws ApplicationException;

	/**
	 * ���ފǗ������폜����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param deleteInfo			�폜���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,ShoruiKanriPk pkInfo) throws ApplicationException;
	
	/**
	 * ���ފǗ������폜����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param deleteInfo			�폜���郆�[�U���B
	 * @throws ApplicationException
	 */
	public List delete(UserInfo userInfo,ShoruiKanriInfo deleteInfo) throws ApplicationException;

	/**
	 * ���ƊǗ�������������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�������郆�[�U���B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public JigyoKanriInfo select(UserInfo userInfo,JigyoKanriPk pkInfo) throws ApplicationException;
	
	/**
	 * ���ޏ�����������B
	 * ���ƊǗ����Ə��ފǗ�����Map�`���ŕԂ��B
	 * Map�̃L�[��[KEY_JIGYOKANRI_INFO], [KEY_SHORUIKANRI_LIST]�ƂȂ�B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�������B
	 * @return						�擾�������[�U���B
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public Map select(UserInfo userInfo, ShoruiKanriPk pkInfo) throws ApplicationException;

	/**
	 * ���ޏ�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�������B
	 * @return						�擾�������[�U���B
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public ShoruiKanriInfo selectShoruiInfo(UserInfo userInfo, ShoruiKanriPk pkInfo) throws ApplicationException;
	
	/**
	 * ���ƊǗ�������������B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException;

	/**
	 * ���ƊǗ�������������B
	 * �Ɩ��S���҈ȊO�́AselectJigyoInfoList(Connection connection) ��
	 * �������ʂ��Ԃ�B
	 * <li>�Ɩ��S���ҥ������������S�����鎖�Ƌ敪�ɊY�����鎖�ƃ��X�g</li>
	 * <li>�Ɩ��S���҈ȊO����S��</li>
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo, JigyoKanriSearchInfo searchInfo) throws ApplicationException;
	
	/**
	 * ���ފǗ�������������B
	 * �\�[�g���́A�V�X�e����t�ԍ��̏����Ƃ���B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public List search(UserInfo userInfo, ShoruiKanriSearchInfo searchInfo) throws ApplicationException;

	/**
	 * ��t���̎��ƊǗ�������������B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public Page searchUketukeJigyo(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException;

	/**
	 * �o�^�܂��͍X�V���鎖�ƊǗ������`���`�F�b�N����B
	 * @param userInfo					���s���郆�[�U���
	 * @param insertOrUpdateInfo		�o�^�܂��͐V�K�쐬���鎖�ƊǗ����
	 * @param mode						���[�h
	 * @return							�`���`�F�b�N��̎��ƊǗ����
	 * @throws ApplicationException	
	 * @throws ValidationException		���؂ɃG���[���������ꍇ�B
	 */
	public JigyoKanriInfo validate(UserInfo userInfo,JigyoKanriInfo insertOrUpdateInfo, String mode)
		throws ApplicationException, ValidationException;

	/**
	 * ���ƊǗ����̃t�@�C�����\�[�X���擾����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @param fileFlag				�_�E�����[�h����t�@�C���̎�ނ�\���B
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public FileResource getJigyoKanriFileRes(UserInfo userInfo,JigyoKanriPk jigyoPk, String fileFlg) throws ApplicationException;

	/**
	 * ���ފǗ����̃t�@�C�����\�[�X���擾����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public FileResource getShoruiKanriFileRes(UserInfo userInfo,ShoruiKanriPk getInfo) throws ApplicationException;

	/**
	 * ���ވꗗ���擾����B
	 * ���ƊǗ����e�[�u���Ə��ފǗ����e�[�u���̃f�[�^���擾����B
	 * �\�[�g���́A����ID,�V�X�e����t�ԍ��̏����Ƃ���B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������郆�[�U���B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException
	 */
	public Page searchShoruiList(UserInfo userInfo, ShoruiKanriSearchInfo searchInfo) throws ApplicationException;

	/**
	 * ����U��Ώێ��ƊǗ�������������B
	 * ����U�菈���͐\���󋵂��u06(�w�i��)�v�̐\���f�[�^�̑��݂��鎖�ƂƂȂ�B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public Page searchWarifuriJigyo(UserInfo userInfo,SearchInfo searchInfo) throws ApplicationException;
	
	
	/**
	 * ���Ə����擾����B
	 * ���ƃR�[�h�A�N�x�i�a��j�A�񐔂����Ɏ��Ə����擾����B
	 * @param userInfo
	 * @param jigyoCd
	 * @param nendo
	 * @param kaisu
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public JigyoKanriInfo getJigyoKanriInfo(UserInfo userInfo,
											 String   jigyoCd,
											 String   nendo,
											 String   kaisu)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 *  ���̈�ԍ����s���o�^
	 *  �����̈�ŏI�N�x�O�N�x�̉���
	 * @param userInfo
	 * @param ryoikiKeikakushoInfo
	 * @return
	 * @throws ApplicationException
	 */
	public RyoikiKeikakushoInfo ryoikiKeikakushoInfo(UserInfo userInfo,
			RyoikiKeikakushoInfo ryoikiKeikakushoInfo) throws ApplicationException;
}






