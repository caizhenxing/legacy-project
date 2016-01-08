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

import java.util.Map;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekka2ndInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * �R�����ʏ����Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IShinsaKekkaMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IShinsaKekkaMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** �߂�lMap�L�[�l�F1���R�����ʁi�Q�Ɨp�j */
	public static final String KEY_SHINSAKEKKA_1ST = "key_shinsakekka_1st";
	
	/** �߂�lMap�L�[�l�F2���R������ */
	public static final String KEY_SHINSAKEKKA_2ND = "key_shinsakekka_2nd";

	/** �߂�lMap�L�[�l�F�R���S�����ꗗ�p�i�ꗗ�f�[�^�j */
	public static final String KEY_SHINSATANTO_LIST = "key_shinsatanto_list";
	
	/** �߂�lMap�L�[�l�F�R���S�����ꗗ�p�i�R�������t���O�j */
	public static final String KEY_SHINSACOMPLETE_FLG = "key_shinsacomplete_flg";
    
//2006/10/27 �c�@�ǉ���������
    /** �߂�lMap�L�[�l�F���Q�֌W���͏󋵈ꗗ�p�i���͊����t���O�j */
    public static final String KEY_NYURYOKUCOMPLETE_FLG = "key_nyuryokucomplete_flg";
//2006/10/27�@�c�@�ǉ������܂�

	/** �߂�lMap�L�[�l�F�R���S�����ꗗ�p�i�����]�_���X�g�j */
	public static final String KEY_SOGOHYOTEN_LIST = "key_sogohyoten_list";
		
//�R������
	public static final String	RIGAI_ON			=	"1";	//���Q�֌W�L��
	public static final String	RIGAI_OFF			=	"0";	//���Q�֌W�Ȃ�

//���̑��̕]�����ځE�K�ؐ�	
	public static final String	TEKISETU_JINKEN			=	"3";	//�K�ؐ��E�l���u�~�v�̂Ƃ�
	public static final String	TEKISETU_BUNTANKIN		=	"3";	//�K�ؐ��E���S���u�~�v�̂Ƃ�
	public static final String	TEKISETU_KEIHI			=	"3";	//�K�ؐ��E�����o��u�~�v�̂Ƃ�
	
//�����]�_		
	public static final String	SOGO_HYOTEN				=	"-";	//�����]�_�u-�v�̂Ƃ�
		
	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
	
	/**
	 * �R���������̏��ފǗ������܂߂��R���Ώێ��ƈꗗ��Ԃ��B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @return						�擾�������[�U���B
	 * @throws ApplicationException
	 */
	public Page getShinsaJigyo(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException;


	/**
	 * �R���������̐R���Ώێ��ƈꗗ��Ԃ��B
	 * ���ފǗ����͏����B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @return						�擾�������[�U���B
	 * @throws ApplicationException
	 */
	public Page searchShinsaJigyo(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException;

	/**
	 * �R�����ʓ��͏��̃t�@�C�����\�[�X���擾����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�������B
	 * @return						�������ʃy�[�W���B
	 * @throws ApplicationException	
	 */
	public FileResource getHyokaFileRes(UserInfo userInfo, ShinsaKekkaPk pkInfo) throws ApplicationException;
	
	/**
	 * �R���S�����\���ꗗ��Ԃ��B
	 * @param userInfo
	 * @param jigyoId
	 * @param searchInfo
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Map selectShinsaKekkaTantoList(UserInfo   userInfo,
											String     jigyoId,
											SearchInfo searchInfo)
		throws NoDataFoundException, ApplicationException;	
	
	/**
	 * �R���S�����\���ꗗ��Ԃ��B
	 * @param userInfo
	 * @param jigyoId
	 * @param kekkaTen
	 * @param searchInfo
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Map selectShinsaKekkaTantoList(UserInfo userInfo,
											String jigyoId,
											String kekkaTen,
                                            String countKbn,
                                            String rigai,
											SearchInfo searchInfo)
		throws NoDataFoundException, ApplicationException;
		
	/**
	 * 1���R�����ʓ��͏���Ԃ��B
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
	 * 1���R�����ʂ�o�^����B
	 * �R�����ʃe�[�u���̓��Y���R�[�h���X�V������A�\���f�[�^���X�V����B
	 * @param userInfo
	 * @param shinsaKekkaInputInfo
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void regist1stShinsaKekka(UserInfo userInfo,
										ShinsaKekkaInputInfo shinsaKekkaInputInfo)
		throws NoDataFoundException, ApplicationException;	
	
	
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
	 * �R���Ñ����[���𑗐M����B
	 * �ݒ�t�@�C���Ɏw�肳�ꂽ������ɐR�������ɂȂ鎖�Ƃɑ΂��āA
	 * ���R���̐\���������݂���R�����Ɍ����ă��[���𑗐M����B
	 * ���Y�R�������̎��Ƃ����݂��Ȃ��ꍇ�͉����������Ȃ��B
	 * @param userInfo
	 * @throws ApplicationException
	 */
	public void sendMailShinsaSaisoku(UserInfo userInfo)
		throws ApplicationException;
	
	/**
	 * �R�����ʂ̐R���󋵂�R�������ɍX�V����B
	 * @param userInfo
	 * @param jigyoId
	 * @return �����]����NULL�̃f�[�^������ꍇ�́Afalse��Ԃ��B�R���󋵂̍X�V�ɐ��������ꍇ�́Atrue��Ԃ��B
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean updateJigyoShinsaComplete(UserInfo userInfo,
												String jigyoId)
		throws NoDataFoundException, ApplicationException;

	/**
	 * �R�����ʂ̐R���󋵂�R���������ɍX�V����B
	 * @param userInfo
	 * @param jigyoId
	 * @return �����]����NULL�̃f�[�^������ꍇ�́Afalse��Ԃ��B�R���󋵂̍X�V�ɐ��������ꍇ�́Atrue��Ԃ��B
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean updateJigyoShinsaCompleteYet(UserInfo userInfo,
												String jigyoId)
		throws NoDataFoundException, ApplicationException;

	/**
	 * �R�����ʂ̐R���󋵂��w�肵���󋵂ɍX�V����B
	 * @param userInfo
	 * @param jigyoId
	 * @param shinsaJokyo
	 * @return �����]����NULL�̃f�[�^������ꍇ�́Afalse��Ԃ��B�R���󋵂̍X�V�ɐ��������ꍇ�́Atrue��Ԃ��B
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean updateJigyoShinsaComplete(UserInfo userInfo,
												String jigyoId,
												String shinsaJokyo)
		throws NoDataFoundException, ApplicationException;

	/**
	 * �R�����ʂ̐R���󋵂��X�V����B
	 * @param userInfo
	 * @param systemNo
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void updateShinseiShinsaComplete(UserInfo userInfo,
												String systemNo)
		throws NoDataFoundException, ApplicationException;
	
// 206-10-25 ���u�j ���Q�֌W���͊��� �ǉ� ��������
    /**
     * ���Q�֌W���͂̓��͏󋵂𗘊Q�֌W���͊����ɍX�V����B     
     * @param userInfo
     * @param jigyoId
     * @return ���͏󋵂̍X�V�ɐ��������ꍇ�́Atrue��Ԃ��B
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void updateRiekiSohanComplete(UserInfo userInfo, String jigyoId)
            throws NoDataFoundException, ApplicationException;
// 206-10-25 ���u�j ���Q�֌W���͊��� �ǉ� �����܂�
    
// 2006/10/27 �c�@�ǉ���������
    /**
     * ���Q�֌W�ӌ��̓o�^
     * @param userInfo UserInfo
     * @param shinsaKekkaInputInfo ShinsaKekkaInputInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registRiekiSohan(
        UserInfo userInfo,
        ShinsaKekkaInputInfo shinsaKekkaInputInfo)
        throws NoDataFoundException, ApplicationException;
    
    /**
     * �R�����ʏ��̎擾(���Q�֌W�p)
     * @param userInfo UserInfo
     * @param shinsaKekkaPk ShinsaKekkaPk
     * @return �R�����ʏ�������ShinsaKekkaInputInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public ShinsaKekkaInputInfo selectShinsaKekkaForRiekiSohan(
        UserInfo userInfo,
        ShinsaKekkaPk shinsaKekkaPk)
        throws NoDataFoundException, ApplicationException;
//�@2006/10/27�@�c�@�ǉ������܂�    
}