/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : IShinseiMaintenance.java
 *    Description : �\�����̊Ǘ����s���C���^�[�t�F�[�X
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/12/08    V1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemBusyException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.HyokaSearchInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.KeizokuInfo;
import jp.go.jsps.kaken.model.vo.KeizokuPk;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.ShinsaKekka2ndInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * �\�����̊Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IShinseiMaintenance.java,v $"
 * Revision="$Revision: 1.7 $"
 * Date="$Date: 2007/07/26 07:37:38 $"
 */
public interface IShinseiMaintenance {
    
    //---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------    
    /** �߂�lMap�L�[�l�F�\����� */
    public static final String KEY_SHINSEIDATA_INFO = "key_shinseidata_info";
    
    /** �߂�lMap�L�[�l�F�n���̋敪���X�g�i�\�����͗p�j */
    public static final String KEY_KEI_KUBUN_LIST   = "key_kei_kubun_list";
    
    /** �߂�lMap�L�[�l�F���E�̊ϓ_���X�g�i�\�����͗p�j */
    public static final String KEY_SUISEN_LIST      = "key_suisen_list";
    
    /** �߂�lMap�L�[�l�F�E�탊�X�g�i�\�����͗p�j */
    public static final String KEY_SHOKUSHU_LIST    = "key_shokushu_list";
    
// 20050527 Start
    /** �߂�lMap�L�[�l�F�̈惊�X�g�i�\�����͗p�j */
    public static final String KEY_RYOUIKI_LIST     = "key_ryouiki_list";
// Horikoshi End
    
//2007/02/08 �c�@�ǉ���������
    /** �߂�lMap�L�[�l�F�̈惊�X�g�i�\�����͗p�j */
    public static final String KEY_KIBOUBUMON_WAKA_LIST     = "key_kiboubumon_waka_list";
//2007/02/08 �c�@�ǉ������܂�    
    
//2006/06/22 �c�@�ǉ���������
    /** �߂�lMap�L�[�l�F�̈�v�揑�i�T�v�j��� */
    public static final String KEY_RYOIKIKEIKAKUSHO_INFO = "key_ryoikikeikakusho_info";
    
    /** �߂�lMap�L�[�l�F�R����]���僊�X�g�i�̈�v�揑���͗p�j */
    public static final String KEY_KIBOUBUMON_LIST  = "key_kiboubumon_list";
    
    /** �߂�lMap�L�[�l�F���O�������X�g�i�̈�v�揑���͗p�j */
    public static final String KEY_JIZENCHOUSA_LIST  = "key_jizenchousa_list";
    
    /** �߂�lMap�L�[�l�F�����̕K�v�����X�g�i�̈�v�揑���͗p�j */
    public static final String KEY_KENKYUHITSUYOUSEI_LIST  = "key_kenkyuHitsuyousei_list";
    
    /** �߂�lMap�L�[�l�F15���ރ��X�g�i�̈�v�揑���͗p�j */
    public static final String KEY_KANRENBUNYABUNRUI_LIST  = "key_kanrenbunyaBunrui_list";
    
    /** �߂�lMap�L�[�l�F�Y�t�t�@�[���t���O���X�g */
    public static final String KEY_RYOIKITENPUFLAG_LIST = "key_ryoikiTenpuFlg_list";
    
    /** �̈�v�揑�m��t���O�i�ʏ�j */
    public static final String FLAG_RYOIKIKEIKAKUSHO_NOT_KAKUTEI = "0";
    
    /** �̈�v�揑�m��t���O�i�̈�v�揑�m��ρj */
    public static final String FLAG_RYOIKIKEIKAKUSHO_KAKUTEI = "1";
    
    /** �̈�v�揑�����t���O�i�ʏ�j */
    public static final String FLAG_RYOIKIKEIKAKUSHO_NOT_CANCEL = "0";
    
    /** �̈�v�揑�����t���O�i���F�����ρj */
    public static final String FLAG_RYOIKIKEIKAKUSHO_CANCEL = "1";
    
    /** �Q�l��������t���O */    
    public static final String FLAG_TENPU_ARI = "1";
    
    /** �Q�l�����Ȃ��t���O */ 
    public static final String FLAG_TENPU_NASI = "0";
    
//2006/06/22 �c�@�ǉ������܂�        

    /** �\�����폜�t���O�i�ʏ�j */
    public static final String FLAG_APPLICATION_NOT_DELETE = "0";
    
    /** �\�����폜�t���O�i�폜�ς݁j */
    public static final String FLAG_APPLICATION_DELETE     = "1";
    
/** 20050621 �萔 */

    // �`�F�b�N���
    public static final String TOKUTEI_HENKOU        =    "1";        // ����̈�(�啝�ȕύX)
    public static final String TOKUTEI_CHOUSEI       =    "1";        // ����̈�(������)
    public static final String CHECK_OFF             =    "0";
    public static final String CHECK_ON              =    "1";        // �`�F�b�N�{�b�N�X��ԊǗ��iON�j
                                                                      // �������ǂ̃t���O�ɂ��g�p
                                                                      // ���폜�̃t���O�ɂ��g�p
    //�����敪
    public static final String NAME_KEIKAKU          =    "�v�挤��";
    public static final String NAME_KOUBO            =    "���匤��";
//20050822 �I����������I�������̈�ɕύX
    public static final String NAME_SHUURYOU         =    "�I�������̈�";
    public static final String KUBUN_KEIKAKU         =    "1";        // �v�挤���敪
    public static final String KUBUN_KOUBO           =    "2";        // ���匤���敪
    public static final String KUBUN_SHUURYOU        =    "3";        // �I�������敪
    public static final String RYOUIKI_KEIKAKU       =    "000";      // �̈�ԍ�(�v�挤��)
    public static final String RYOUIKI_KOUBO         =    "001";      // �̈�ԍ�(���匤��)
    public static final String RYOUIKI_SHUURYOU      =    "999";      // �̈�ԍ�(�I������)
    public static final String SHUURYOU_NAME         =    "���ʎ��܂Ƃ�";
    // �\���敪
    public static final String SHINSEI_NEW           =    "1";        // �V�K
    public static final String SHINSEI_CONTINUE      =    "2";        // �p��

    // �ǋ敪
    public static final String HAN_SOUKATU           =    "X00";      // ������
    public static final String HAN_SHIEN             =    "Y00";      // �x����
    public static final String HAN_CHAR              =    "X";        // �I�������̍ۂɎ����Z�b�g����l(������)
    public static final String HAN_NO                =    "00";       // �I�������̍ۂɎ����Z�b�g����l(�ԍ�)

    // �`�F�b�N���x��
    public static final int    CHECK_ZERO            =    0;
    public static final int    CHECK_ONE             =    1;
    public static final int    CHECK_TWO             =    2;
    public static final int    CHECK_THREE           =    3;
    public static final int    KOUMOKU_CHECK_NUM     =    0;          //�`�F�b�N���镶����C���f�b�N�X
    // ���ږ�
    public static final String KENKYUU_KUBUN         =    "�����敪";
    public static final String SHINSEI_KUBUN         =    "�V�K�E�p���敪";
    public static final String CHANGE_FLG            =    "�啝�ȕύX�𔺂������ۑ�";
    public static final String KADAI_NUM             =    "�p���̏ꍇ�̌����ۑ�ԍ�";
    public static final String RYOUIKI_NUM           =    "�̈�ԍ�";
    public static final String RYOUIKI_NAME          =    "�̈旪�̖�";
    public static final String KENKYUU_NUM           =    "�������ڔԍ�";
    public static final String CHOUSEI_FLG           =    "�v�挤���̂���������";
    public static final String KENKYUU_KEIHI         =    "�����o��";
    // ������

    public static final int    NENSU = 5;                      // ����������N�����͂��邩 
//2006/07/03 �c�@�ǉ���������
    public static final int    NENSU_TOKUTEI_SINNKI  = 6;    //�@����������N�����͂��邩�i����̈�V�K�p�j
//2006/07/03�@�c�@�ǉ������܂�    
    public static final int    MAX_KENKYUKEIHI       =    9999999;    // �����o��V�X�e��MAX�l
    public static final int    MAX_KENKYUKEIHI_GOKEI =    9999999;    // �����o��v�ō����z(�~)
    public static final int    MIN_KENKYUKEIHI       =    100;        // �e�N�x�̌����o��(���~)
    // �������S��
    public static final String KENKYUU_BUNTAN        =    "2";        // �������S��
    public static final String KENKYUU_ERROR_NO      =    "99999999"; // �G���[�ƂȂ錤���Ҕԍ�   

    // ��茤���̔N���
    public static final int    WAKATE_LIMIT			=    37;
    public static final String STR_COUNT				=    "GET_COUNT";

    //��茤��S�̔N���
    public static final int    WAKATE_S_LIMIT			=    42;
    
    // �J����]�̗L��
    public static final String KAIJI_FLG_SET         =    "0";        // �v�挤���̏ꍇ�ɃZ�b�g����t���O�l
// Horikoshi

    // �G�t�H�[�g
    public static final String EFFORT_MIN            =    "1";
    public static final String EFFORT_MAX            =    "100";
//2006/08/17 �c�@�ǉ���������
    public static final String EFFORT_MIN_SINNKI     =    "0";
//2006/08/17�@�c�@�ǉ������܂�    
    // �����t���זڔԍ��łȂ��זڔԍ��̍ő�l
    public static final int    MAX_SAIMOKU_NOT_JIGEN =    8999;

    //2005.10.27 iso �����v��ŏI�N�x�O�N�x����̗L���t���O��ǉ�
    public static final String ZENNEN_ON             =    "1";     //���傷��
    public static final String ZENNEN_OFF            =    "2";     //���債�Ȃ�

    //---------------------------------------------------------------------
    // Methods 
    //---------------------------------------------------------------------    
    /**
     * �\��������������B
     * @param userInfo ���s���郆�[�U���
     * @param searchInfo �\�����������
     * @return Page �擾�����\�����y�[�W�I�u�W�F�N�g
     * @throws ApplicationException
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
     */
    public Page searchApplication(UserInfo userInfo, ShinseiSearchInfo searchInfo) 
        throws NoDataFoundException, ApplicationException;

    /**
     * �ȈՐ\���������擾����B
     * �\�������𒊏o����B
     * @param userInfo ���s���郆�[�U���
     * @param pkInfo �\���f�[�^���i�L�[�j
     * @return SimpleShinseiDataInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public SimpleShinseiDataInfo selectSimpleShinseiDataInfo(
            UserInfo userInfo,
            ShinseiDataPk pkInfo)
        throws NoDataFoundException, ApplicationException;

    /**
     * �ȈՐ\���������擾����B
     * @param userInfo ���s���郆�[�U���
     * @param pkInfo �\���f�[�^���i�L�[�j�z��
     * @return SimpleShinseiDataInfo[]
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public SimpleShinseiDataInfo[] selectSimpleShinseiDataInfos(
            UserInfo userInfo,
            ShinseiDataPk[] pkInfo)
        throws NoDataFoundException, ApplicationException;

    /**
     * �\�������𒊏o����B
     * @param userInfo ���s���郆�[�U���
     * @param pkInfo �\���f�[�^���i�L�[�j
     * @return ShinseiDataInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public ShinseiDataInfo selectShinseiDataInfo(UserInfo userInfo,
                                                 ShinseiDataPk pkInfo)
        throws NoDataFoundException, ApplicationException;
 
    /**
     * �V�K���͗p�̐\�����𒊏o����B
     * �\�����͂ɕK�v�Ȏ��Ə����Z�b�g�����\������Ԃ���B
     * @param userInfo              ���s���郆�[�U���
     * @param pkInfo                ���ƊǗ�����L�[
     * @return Map                  �V�K���͗p�\�����ƌn�񃊃X�g��Map
     * @throws ApplicationException    
     * @throws NoDataFoundException �Ώۃf�[�^�i���Ə��j��������Ȃ��ꍇ�̗�O
     */
    public Map selectShinseiDataForInput(UserInfo userInfo,
                                         JigyoKanriPk pkInfo) 
        throws NoDataFoundException, ApplicationException;

    /**
     * �����f�[�^�X�V�p�̐\�����𒊏o����B
     * @param userInfo              ���s���郆�[�U���
     * @param pkInfo                �\���f�[�^��L�[
     * @return Map                  �����\�����ƌn�񃊃X�g��Map
     * @throws ApplicationException    
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ�̗�O
     */
    public Map selectShinseiDataForInput(UserInfo userInfo, 
                                         ShinseiDataPk pkInfo) 
        throws NoDataFoundException, ApplicationException;

    /**
     * �\���������ۑ�����B�i�V�K�j
     * @param userInfo              ���s���郆�[�U���
     * @param addInfo               �쐬����\�����
     * @param fileRes               �Y�t�t�@�C���i���݂��Ȃ��ꍇ��null�j
     * @return ShinseiDataInfo      �V�K�o�^�����\�����
     * @throws ValidationException    
     * @throws ApplicationException    
     */
    public ShinseiDataInfo transientSaveNew(UserInfo userInfo, 
                                            ShinseiDataInfo addInfo, 
                                            FileResource fileRes) 
        throws ValidationException, ApplicationException;    

    /**
     * �\���������ۑ�����B�i�X�V�j
     * @param userInfo              ���s���郆�[�U���
     * @param addInfo               �쐬����\�����
     * @param fileRes               �Y�t�t�@�C���i���݂��Ȃ��ꍇ��null�j
     * @throws ValidationException    
     * @throws NoDataFoundException
     * @throws ApplicationException    
     */
    public void transientSaveUpdate(UserInfo userInfo, 
                                    ShinseiDataInfo addInfo, 
                                    FileResource fileRes) 
        throws ValidationException, NoDataFoundException, ApplicationException;    

    /**
     * �\������o�^����B�i�V�K�j
     * @param userInfo              ���s���郆�[�U���
     * @param updateInfo            �X�V����\�����
     * @param fileRes               �Y�t�t�@�C��
     * @return                      �V�K�o�^�����\�����
     * @throws ValidationException    
     * @throws ApplicationException
     */
    public ShinseiDataInfo registApplicationNew(UserInfo userInfo,
                                                ShinseiDataInfo updateInfo,
                                                FileResource fileRes) 
        throws ValidationException, ApplicationException;

    /**
     * �\������o�^����B�i�X�V�j
     * @param userInfo              ���s���郆�[�U���
     * @param updateInfo            �X�V����\�����
     * @param fileRes               �Y�t�t�@�C���i���݂��Ȃ��ꍇ��null�j
     * @return ShinseiDataInfo      �\���f�[�^���
     * @throws ValidationException    
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public ShinseiDataInfo registApplicationUpdate(UserInfo userInfo,
            ShinseiDataInfo updateInfo,FileResource fileRes) 
        throws ValidationException, NoDataFoundException, ApplicationException;

    /**
     * �\���f�[�^��XML�ϊ��APDF�ϊ�����B
     * ������A�\���󋵂��u�\�����m�F�v�ɍX�V����B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void convertApplication(UserInfo userInfo,
                                   ShinseiDataPk shinseiDataPk)
        throws NoDataFoundException, ApplicationException;

    /**
     * �\�������폜���܂��B�i�\���ҁj
     * @param userInfo              ���s���郆�[�U���
     * @param shinseiDataPk         �\���f�[�^���i�L�[�j
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void deleteApplication(UserInfo userInfo,
                                  ShinseiDataPk shinseiDataPk) 
        throws NoDataFoundException, ApplicationException;

    /**
     * PDF�ϊ���̃t�@�C�����擾����B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @return FileResource
     * @throws ApplicationException
     */
    public FileResource getPdfFileRes(UserInfo userInfo,
                                      ShinseiDataPk shinseiDataPk)
        throws ApplicationException;

    /**
     * PDF�ϊ���O�̓Y�t�t�@�C�����擾����B
     * �Y�t�t�@�C�����������݂���ꍇ�A�ŏ��̃t�@�C�����\�[�X���擾����B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @return FileResource
     * @throws ApplicationException
     */
    public FileResource getTenpuFileRes(UserInfo userInfo,
                                        ShinseiDataPk shinseiDataPk)
        throws ApplicationException;

    /**
     * PDF�ϊ���O�̓Y�t�t�@�C�����擾����B
     * �Y�t�t�@�C�����������݂���ꍇ�A���ׂẴt�@�C�����\�[�X���擾����B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @return FileResource[]
     * @throws ApplicationException
     */
    public FileResource[] getAllTenpuFileRes(UserInfo userInfo,
                                             ShinseiDataPk shinseiDataPk)
        throws ApplicationException;

    /**
     * �\�����̊m�F����������B�i�\���ҁj
     * ������A�\���󋵂��u�������Ԏ�t���v�ɂ���B
     * �����@�֒S���҈��ĂɁu�\�����m�F�������[���v�𑗐M����B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void confirmComplete(UserInfo userInfo,
                                ShinseiDataPk shinseiDataPk)
         throws NoDataFoundException,ApplicationException;

    /**
     * �\���������F����B�i�����@�֒S���ҁj
     * ������A�\���󋵂��u�w�U�������v�ɂ���B
     * �����@�֒S���҈��ĂɁu�\�����w�U���B���[���v�𑗐M����B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void recognizeApplication(UserInfo userInfo,
                                       ShinseiDataPk shinseiDataPk)
        throws NoDataFoundException, ApplicationException;

    /**
     * �\�������p������B�i�����@�֒S���ҁj
     * ������A�\���󋵂��u�����@�֋p���v�ɂ���B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void rejectApplication(UserInfo userInfo,
                                     ShinseiDataPk shinseiDataPk)
        throws NoDataFoundException, ApplicationException;

//    /**
//     * �]���R�����g������������B
//     * @param userInfo              ���s���郆�[�U���
//     * @param searchInfo            �]���R�����g�������
//     * @return                      �擾�����]���R�����g���y�[�W�I�u�W�F�N�g
//     * @throws ApplicationException    
//     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
//     */
//    public Page searchCommentList(UserInfo userInfo, HyokaSearchInfo searchInfo) 
//        throws NoDataFoundException, ApplicationException;

    /**
     * �]��������������B
     * @param userInfo              ���s���郆�[�U���
     * @param searchInfo            �]���R�����g�������
     * @return Page                 �擾�����]���R�����g���y�[�W�I�u�W�F�N�g
     * @throws ApplicationException    
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
     */
    public Page searchHyokaList(UserInfo userInfo, HyokaSearchInfo searchInfo) 
        throws NoDataFoundException, ApplicationException;

    /**
     * 1���R�����ʂɂ�����Ɩ��S���җp���l��o�^����B
     * @param userInfo ���s���郆�[�U���
     * @param shinsaKekkaRefInfo 1���R�����ʏ��
     * @throws NoClassDefFoundError
     * @throws ApplicationException
     */
    public void regist1stShinsaKekkaBiko(UserInfo userInfo,
            ShinsaKekkaReferenceInfo shinsaKekkaRefInfo)
        throws NoClassDefFoundError, ApplicationException;

    /**
     * 2���R�����ʂ�o�^����B
     * @param userInfo ���s���郆�[�U���
     * @param shinsaKekka2nd 2���R�����ʏ��
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void regist2ndShinsaKekka(UserInfo userInfo,
                                     ShinsaKekka2ndInfo shinsaKekka2nd)
        throws NoDataFoundException, ApplicationException;    

    /**
     * �\�������󗝂���B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @param jigyoCd ���ƃR�[�h
     * @param comment
     * @param seiriNo �����ԍ�
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registShinseiJuri(UserInfo userInfo,
                                    ShinseiDataPk shinseiDataPk,
                                    String jigyoCd,
                                    String comment,
                                    String seiriNo
                                    )
        throws NoDataFoundException, ApplicationException;

    /**
     * �\������s�󗝂���B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @param jigyoCd ���ƃR�[�h
     * @param comment
     * @param seiriNo �����ԍ�
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registShinseiFujuri(UserInfo userInfo,
                                    ShinseiDataPk shinseiDataPk,
                                    String jigyoCd, 
                                    String comment,
                                    String seiriNo)
        throws NoDataFoundException, ApplicationException;

    /**
     * �\�������C���˗�����B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @param jigyoCd ���ƃR�[�h
     * @param comment
     * @param seiriNo �����ԍ�
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registShinseiShuseiIrai(UserInfo userInfo,
                                        ShinseiDataPk shinseiDataPk,
                                        String jigyoCd, 
                                        String comment,
                                        String seiriNo)
        throws NoDataFoundException, ApplicationException;

//add start dyh 2006/06/02
    /**
     * �ꊇ��.<br><br>
     * �ꊇ�󗝃f�[�^��\���B�i�w�n�A�����A��ՂŎg�p�j
     * 
     * @param userInfo ���s���郆�[�U���
     * @param searchInfo �\�����������
     * @param systemNos �L�[�z��
     * @return Page�@�ꊇ�󗝃f�[�^���
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public Page getShinseiJuriAll(UserInfo userInfo,
                                  ShinseiSearchInfo searchInfo,
                                  String[] systemNos)
        throws NoDataFoundException, ApplicationException;
//add end dyh 2006/06/02

    //2005/04/22 �ǉ� ��������-----------------------------------------
    /**
     * �ꊇ�󗝂��s���B
     * @param userInfo ���s���郆�[�U���
     * @param pks �L�[���X�g
     * @return List �ꊇ�󗝃f�[�^���
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
//    public void registShinseiJuriAll(UserInfo userInfo,List pks)
    public List registShinseiJuriAll(UserInfo userInfo,List pks)
        throws NoDataFoundException, ApplicationException;
    // �ǉ� �����܂�----------------------------------------------------

    /**
     * �R���˗����s�ʒm���̃X�e�[�^�X�X�V���\�b�h�B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void updateStatusForShinsaIraiIssue(UserInfo userInfo,
                                               ShinseiDataPk[] shinseiDataPk)
        throws NoDataFoundException, ApplicationException;

    /**
     * CSV�o�͗p�̐\���ҏ�����������i�Ɩ��p�j�B
     * 
     * @param userInfo ���s���郆�[�U���
     * @param searchInfo �\�����������
     * @return List CSV�o�͗p�̐\���ҏ��
     * @throws ApplicationException
     */
    public List searchCsvData(UserInfo userInfo, ShinseiSearchInfo searchInfo) 
        throws ApplicationException;

    /**
     * CSV�o�͗p�̐\���ҏ�����������(�����p)�B
     * 
     * @param userInfo ���s���郆�[�U���
     * @param searchInfo �\�����������
     * @return List CSV�o�͗p�̐\���ҏ��
     * @throws ApplicationException
     */
    public List searchShozokuCsvData(UserInfo userInfo,
                                     ShinseiSearchInfo searchInfo) 
        throws ApplicationException;

    /**
     * �����g�DCSV�o�͗p�̐\���ҏ�����������B
     * 
     * @param userInfo ���s���郆�[�U���
     * @param searchInfo �\�����������
     * @return List �����g�DCSV�o�͗p�̐\���ҏ��
     * @throws ApplicationException
     */
    public List searchKenkyuSoshikiCsvData(UserInfo userInfo,
                                           ShinseiSearchInfo searchInfo) 
        throws ApplicationException;

    /**
     * ���E���t�@�C�����i�[���A�\���f�[�^�e�[�u���Ƀp�X����o�^����B
     * �o�^�ς݂̏ꍇ�͏㏑������B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @param fileRes �t�@�C���̑���M�Ŏg�p���郊�\�[�X
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registSuisenFile(
            UserInfo userInfo,
            ShinseiDataPk shinseiDataPk,
            FileResource fileRes)
        throws NoDataFoundException, ApplicationException;

    /**
     * �w�肳�ꂽ�\���f�[�^�̐��E���t�@�C����Ԃ��B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @return FileResource �\���f�[�^�̐��E���t�@�C��
     * @throws NoDataFoundException
     * @throws ApplicationException
     */    
    public FileResource getSuisenFileRes(
            UserInfo userInfo,
            ShinseiDataPk shinseiDataPk)
        throws NoDataFoundException, ApplicationException;

    /**
     * �w�肳�ꂽ�\���f�[�^�̐��E���t�@�C���p�X�����폜����B
     * ���ۂ̃t�@�C���͏����Ȃ��B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void deleteSuisenFile(
            UserInfo userInfo,
            ShinseiDataPk shinseiDataPk)
        throws NoDataFoundException, ApplicationException;

    /**
     * �]������CSV�o�͗p�̕]��������������B
     * @param userInfo              ���s���郆�[�U���
     * @param searchInfo            �]���R�����g�������
     * @return FileResource         �擾�����]���R�����g���t�@�C��
     * @throws ApplicationException    
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
     */
    public FileResource searchCsvHyokaList(
            UserInfo userInfo,
            HyokaSearchInfo searchInfo) 
        throws NoDataFoundException, ApplicationException;

    /**
     * CSV�o�͗p�̕]��������������B
     * @param userInfo              ���s���郆�[�U���B
     * @param searchInfo            �������
     * @return List                 �擾��������U����
     * @throws ApplicationException    
     */
    public List searchCsvData(UserInfo userInfo, HyokaSearchInfo searchInfo) 
        throws ApplicationException;

    // 20060605 Wang Xiancheng add start
    /**
     * �����\���������F����B�i�����@�֒S���ҁj
     * ������A�\���󋵂��u�w�U�������v�ɂ���B
     * �����@�֒S���҈��ĂɁu�\�����w�U���B���[���v�𑗐M����B
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPks �\���f�[�^���i�L�[�j�z��
     * @param checkKbn �m�F�Ə��F�̋敪
     * @throws ValidationException
     * @throws ApplicationException
     */    
    public void recognizeMultiApplication(
            UserInfo userInfo,
            ShinseiDataPk[] shinseiDataPks,
            String checkKbn)
        throws ValidationException, ApplicationException;
    // 20060605 Wang Xiancheng add end

    // 2006/06/14 dyh add start �����v�撲���ꗗ�p
    /**
     * �����v�撲���ꗗ�f�[�^���擾
     * 
     * @param userInfo ���s���郆�[�U���
     * @param searchInfo �\�����������
     * @return List �����v�撲���ꗗ�f�[�^
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List getKeikakuTyosyoList(
            UserInfo userInfo,
            ShinseiSearchInfo searchInfo)
        throws NoDataFoundException, ApplicationException;
    // 2006/06/14 dyh add end

    // 2006/06/16 Wang Xiancheng add start
    /**
     * �m�F�E�p���Ώۉ�����ꗗ�f�[�^���擾
     * 
     * @param userInfo ���s���郆�[�U���
     * @param searchInfo �\�����������
     * @return List �ꗗ�f�[�^
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public Page searchConfirmInfo(
            UserInfo userInfo,
            ShinseiSearchInfo searchInfo)
        throws NoDataFoundException, ApplicationException;
    // 2006/06/16 Wang Xiancheng add end

    // 2006/06/16 �c �ǉ���������
    /**
     * ����󋵁i�����v�撲���i����̈�V�K�j�j�̋p��.<br>
     * <br>
     * 
     * @param userInfo ���s���郆�[�U���
     * @param shinseiDataPk �\���f�[�^���i�L�[�j
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.IShinseiMaintenance#rejectApplication(jp.go.jsps.kaken.model.vo.UserInfo,
     *      jp.go.jsps.kaken.model.vo.ShinseiDataPk)
     */
    public void rejectApplicationForTokuteiSinnki(
            UserInfo userInfo,
            ShinseiDataPk shinseiDataPk)
        throws NoDataFoundException, ApplicationException;
    // 2006/06/16 �c �ǉ������܂�

    // 2006/06/20 ���� �ǉ���������
    /**
     * �����o��\��������
     * 
     * @param userInfo ���s���郆�[�U���
     * @param dataInfo ������\�ҋy�ѕ��S�ҁi�����g�D�\�j���
     * @return List �ꗗ�f�[�^
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.IShinseiMaintenance#rejectApplication(jp.go.jsps.kaken.model.vo.UserInfo,
     *      jp.go.jsps.kaken.model.vo.KenkyuSoshikiKenkyushaInfo)
     */
    public List searchKenkyuKeihi(
            UserInfo userInfo,
            KenkyuSoshikiKenkyushaInfo dataInfo)
        throws NoDataFoundException, ApplicationException;
    // 2006/06/20 ���� �ǉ������܂�

	// 2006/06/16 �ǉ��@���`�؁@��������
	/**
	 * �����g�D�\��������
	 * 
	 * @param userInfo ���s���郆�[�U���
	 * @param dataInfo KenkyuSoshikiKenkyushaInfo
	 * @return List �ꗗ�f�[�^			
	 * @throws ApplicationException	
	 * @throws DataAccessException
     * @throws NoDataFoundException 
	 */
	public List searchKenkyuSosiki(
			UserInfo userInfo,
			KenkyuSoshikiKenkyushaInfo dataInfo) 
	    throws ApplicationException, DataAccessException,NoDataFoundException;
    // 2006/06/16 �ǉ��@���`�؁@�����܂�
//<!-- ADD�@START 2007/07/10 BIS ���� -->	
	/**
	 * �����g�D�\����update
	 * 
	 * @param userInfo ���s���郆�[�U���
	 * @param dataInfo KenkyuSoshikiKenkyushaInfo	
	 * @throws ApplicationException	
	 * @throws ValidationException
	 * @throws DataAccessException
	 */
	public void updateHyojijun(
			UserInfo userInfo,
			KenkyuSoshikiKenkyushaInfo dataInfo) 
	    throws ApplicationException, ValidationException,DataAccessException;
//<!-- ADD�@END�@ 2007/07/10 BIS ���� -->	
//  2006/06/21 �c�@�ǉ���������    
    /**
     * �V�K�̈�v�揑�i�T�v�j���͗p�����f�[�^��ԋp.<br><br>
     * 
     * @param userInfo ���O�I���������[�U���
     * @param pkInfo ���ƊǗ��e�[�u����L�[
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @return �V�K���͗p�����f�[�^(Map)
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public Map selectRyoikiKeikakushoInfoForInput(
            UserInfo userInfo,
            JigyoKanriPk pkInfo,
            String ryoikiSystemNo)
        throws NoDataFoundException, ApplicationException;

    /**
     * �̈�v�揑���̓o�^.<br><br>
     * 
     * �̈�v�揑�A�Y�t�t�@�C���̓o�^���s���B<br>
     * �r���ŗ�O������������A���[���o�b�N����B<br><br>
     * 
     * @param userInfo  ���O�I���������[�U���
     * @param dataInfo  RyoikiKeikakushoInfo
     * @param fileRes   FileResource
     * @return �̈�v�揑(RyoikikeikakushoInfo)
     * @throws ValidationException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo registGaiyoApplicationNew(
            UserInfo userInfo,
            RyoikiKeikakushoInfo dataInfo,
            FileResource fileRes)
        throws ValidationException, ApplicationException;

    /**
     * �̈�v�揑���̍X�V.<br><br>
     * 
     * �̈�v�揑�A�Y�t�t�@�C���̓o�^���s���B<br>
     * �r���ŗ�O������������A���[���o�b�N����B<br><br>
     * 
     * @param userInfo  ���O�I���������[�U���
     * @param ryoikikeikakushoInfo �̈�v�揑�i�T�v�j���
     * @param fileRes   FileResource
     * @return �̈�v�揑(RyoikikeikakushoInfo)
     * @throws ValidationException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo registGaiyoApplicationUpdate(
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo,
            FileResource fileRes)
        throws ValidationException, ApplicationException;

    /**
     * �̈�v�揑���̈ꎞ�ۑ�.<br><br>
     * 
     * �̈�v�揑�A�Y�t�t�@�C���̓o�^���s���B<br>
     * �r���ŗ�O������������A���[���o�b�N����B<br><br>
     * 
     * @param userInfo  ���O�I���������[�U���
     * @param ryoikikeikakushoInfo �̈�v�揑�i�T�v�j���
     * @param fileRes   FileResource
     * @return �̈�v�揑(RyoikikeikakushoInfo)
     * @throws ValidationException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo transientGaiyoApplicationNew (
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo,
            FileResource fileRes)
        throws ValidationException, ApplicationException;
//2006/06/21�@�c�@�ǉ������܂�     

//  �{�@2006/06/29�@��������
    /**
     * �̈�v�揑�m�F�����m�F
     * @param userInfo  ���O�I���������[�U���
     * @param ryoikikeikakushoPk �̈�v�揑�i�T�v�j�L�[���
     * @throws NoDataFoundException
     * @throws DataAccessException
     * @throws ApplicationException
     */
    public void confirmGaiyoComplete(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikikeikakushoPk)
        throws NoDataFoundException, ApplicationException;
    //�{�@2006/06/29�@�����܂�

    //2006.07.03 zhangt add start
    /**
     * �̈�v�揑PDF���̕ϊ�
     * @param userInfo ���O�I���������[�U���
     * @param ryoikiKeikakushoPk �̈�v�揑�i�T�v�j�L�[���
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void convertGaiyoApplication(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiKeikakushoPk)
        throws NoDataFoundException, ApplicationException ;
    //2006.07.03 zhangt add end

    /**
     * �̈�v�揑���̈ꎞ�ۑ�.(�X�V)<br><br>
     * 
     * �̈�v�揑�A�Y�t�t�@�C���̓o�^���s���B<br>
     * �r���ŗ�O������������A���[���o�b�N����B<br><br>
     * 
     * @param userInfo ���O�I���������[�U���
     * @param ryoikikeikakushoInfo �̈�v�揑�i�T�v�j���
     * @param fileRes   �t�@�C���̑���M�Ŏg�p���郊�\�[�X
     * @throws ValidationException
     * @throws ApplicationException
     */
    public void transientGaiyoApplicationUpdate (
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo,
            FileResource fileRes)
        throws ValidationException, ApplicationException;

//2006/07/21 �c�@�ǉ���������
    /**
     * �\�������擾.<br><br>
     * 
     * @param userInfo  ���O�I���������[�U���
     * @param connection  �R�l�N�V����
     * @param pkInfo  �\���f�[�^���i�L�[�j
     * @return ShinseiDataInfo �\�����
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public ShinseiDataInfo selectShinseiDataInfoForConfirm(
            UserInfo userInfo,
            Connection connection,
            ShinseiDataPk pkInfo)
        throws NoDataFoundException, ApplicationException;

    /**
     * �ȈՐ\�������擾.�i�̈��\�җp�j<br>
     * <br>
     * �ȈՐ\�������擾����B<br>
     * <br>
     * ���폜��ʂŎg�p<br>
     * <br>
     * ���N���X��selectSimpleShinseiDataInfos(UserInfo,ShinseiDataPk[])���\�b�h���ĂԁB<br>
     * �����ɁA������userInfo�Ƒ�����pkInfo���i�[�����z��(ShinseiDataPk)��n���B<br>
     * <br>
     * �擾����SimpleShinseiDataInfo��ԋp����B<br>
     * <br>
     * 
     * @param userInfo ���O�I���������[�U���
     * @param pkInfo �\���f�[�^���i�L�[�j
     * @return SimpleShinseiDataInfo �ȈՐ\�����
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.IShinseiMaintenance#selectSimpleShinseiDataInfo(jp.go.jsps.kaken.model.vo.UserInfo,
     *      jp.go.jsps.kaken.model.vo.ShinseiDataPk)
     */
    public SimpleShinseiDataInfo selectSimpleShinseiDataInfoForGaiyo(
            UserInfo userInfo,
            ShinseiDataPk pkInfo)
        throws NoDataFoundException, ApplicationException;
//2006/07/21�@�c�@�ǉ������܂�    
    

    //2006.09.25 iso iso �^�C�g���Ɂu�T�v�v������PDF�쐬�̂���
    /**
     * �̈�v�揑�T�vPDF�쐬
     * @param userInfo  UserInfo
     * @return �Ȃ�
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void GaiyoPdfConvert(UserInfo userInfo) throws NoDataFoundException,ApplicationException;
    
    //<!-- ADD�@START 2007/07/20 BIS ���� -->
    /**
     * �̈�������v�撲���̏��N�x�����o��`�F�b�N
     * @param userInfo  UserInfo
     * @param ryouikiNo  ���̈�ԍ�
     * @return �Ȃ�
     * @throws ValidationException
     * @throws ApplicationException
     */
    public void CheckKenkyuKeihiSoukeiInfo(UserInfo userInfo,String ryouikiNo) throws ValidationException,ApplicationException ;
    //<!-- ADD�@START 2007/07/20 BIS ���� -->
	// ADD START 2007-07-10 BIS ���u��
	/**
	 * �p���ۑ���擾
	 * @param jigyoId
	 * @param kadaiNo
	 * @return
	 */
    public KeizokuInfo getKenkyukadaiInfo(UserInfo userInfo, String kenkyuNo, String kadaiNo)
	    throws SystemBusyException, NoDataFoundException,
		DataAccessException;
	//�@ADD END 2007-07-10 BIS ���u��
}