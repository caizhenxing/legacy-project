/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ITeishutuShoruiMaintenance.java
 *    Description : ���̈�ԍ����s�A���发�ޏ��F�E�p�����s���C���^�[�t�F�[�X�B
 *
 *    Author      : DIS
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS            �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoSystemInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;

/**
 * ���̈�ԍ����s�A���发�ޏ��F�E�p�����s���C���^�[�t�F�[�X�B
 * ID RCSfile="$RCSfile: ITeishutuShoruiMaintenance.java,v $"
 * Revision="$Revision: 1.8 $"
 * Date="$Date: 2007/07/25 07:56:21 $"
 */
public interface ITeishutuShoruiMaintenance {

//2006/06/16 by jzx add start
    /**
     * �󗝓o�^���͎󗝉����̏����擾����B
     * 
     * @param userInfo ���O�C���ҏ��
     * @param pkInfo
     * @return RyoikikeikakushoInfo
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo selectRyoikikeikakushoInfo(
            UserInfo userInfo,
            RyoikiKeikakushoPk pkInfo)
            throws DataAccessException,
                   NoDataFoundException,
                   ApplicationException;

    /**
     * �󗝓o�^�i��o���ށj�B
     * 
     * @param  userInfo ���O�C���ҏ��
     * @param  ryoikiInfo �L�[���
     * @param  juriKekka �󗝌���
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registTeisyutusyoJuri(UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo, String juriKekka)
            throws  NoDataFoundException,
            ApplicationException;

    /**
     * �󗝉����i��o���ށj�B
     * 
     * @param  userInfo ���O�C���ҏ��
     * @param  ryoikiInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void cancelTeisyutusyoJuri(UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo) throws NoDataFoundException,
			ApplicationException;
    
    /**
     * �̈�������v�撲���m��B
     * @param userInfo ���O�C���ҏ��
     * @param shinseiDataInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void kakuteiRyoikiGaiyo(UserInfo userInfo,
            ShinseiDataInfo shinseiDataInfo) throws NoDataFoundException,
            ApplicationException;

    /**
	 * �̈�������v�撲���m������B
     * 
	 * @param userInfo ���O�C���ҏ��
	 * @param shinseiDataInfo
	 * @param pkInfo
	 * @return void
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
    public void cancelKakuteiRyoikiGaiyo(UserInfo userInfo,
            ShinseiDataInfo shinseiDataInfo) throws NoDataFoundException,
			ApplicationException;
    // 2006/06/16 by jzx add end
    
    // 2006/06/20 ����@��������
    /**
     * ��єԍ����X�g�p�̃f�[�^���擾����B
     * 
     * @param  userInfo ���O�C���ҏ��
     * @param  teishutsuShoruiSearchInfo �����������
     * @return List
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List selectTeisyutusyoTobiSinkiList(UserInfo userInfo,
            TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo)
            throws NoDataFoundException,ApplicationException;
    // 2006/06/20 ����@�����܂�

    // 2006/06/15 lwj add start
    /**
     * ��o���ވꗗ�\���p�̃f�[�^���擾����B
     * 
     * @param userInfo ���O�C���ҏ��
     * @param teishutsuShoruiSearchInfo �����������
     * @return List
     * @throws NoDataFoundException
     * @throws DataAccessException
     * @throws ApplicationException
     */
    public List selectTeishutuShoruiList(UserInfo userInfo,
            TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo) 
            throws NoDataFoundException,
            ApplicationException;

    // 2006/06/15 lwj add end

    // 2006/06/15 �{ ��������
    /**
     * ��o�m�F�i����̈挤��(�V�K�̈�)�j�ꗗ�����擾����B
     * 
     * @param userInfo ���O�C���ҏ��
     * @param ryoikiInfo
     * @return List
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List searchTeisyutuKakuninList(UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException;
    // 2006/06/15 �{ �����܂�

    // add start ly 2006/06/15
    /**
     * �ȈՉ��̈�ԍ����s�m�F�����擾����B
     * 
     * @param userInfo ���O�C���ҏ��
     * @param ryoikiPk
     * @return
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo selectRyoikiInfo(UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk)
            throws NoDataFoundException, ApplicationException;

    /**
     * ���̈�ԍ����s�m�F�����s�B�i�����@�֒S���ҁj
     * ������ARYOIKI_JOKYO_ID=33�ɍX�V�����B
     * 
     * @param userInfo ���O�C���ҏ��
     * @param ryoikiPk
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void confirmKariBangoHakko(UserInfo userInfo, RyoikiKeikakushoPk ryoikiPk)
            throws NoDataFoundException, ApplicationException;

    /**
     * ���̈�ԍ����s�p���m�F����B�i�����@�֒S���ҁj
     * ������ARYOIKI_JOKYO_ID=32�ɍX�V�����B
     * 
     * @param userInfo ���O�C���ҏ��
     * @param ryoikiPk
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void rejectKariBangoHakko(UserInfo userInfo, RyoikiKeikakushoPk ryoikiPk)
            throws NoDataFoundException, ApplicationException;

    /**
     * ���发�ނ̒�o���o�́i��Ռ������A����̈挤���j
     * 
     * @param userInfo ���O�C���ҏ��
     * @param ryoikiInfo �`�F�b�N���X�g��������
     * @return�@FileResource�@�o�͏��CSV�t�@�E��
     * @throws ApplicationException
     */
    public FileResource createOuboTeishutusho(
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws ApplicationException;
    // add end ly 2006/06/15
    
    //add start zjp 2006/06/15
    /**
     * ���发�ޏ����擾����B
     * 
     * @param userInfo ���O�C���ҏ��
     * @param ryoikiPk
     * @param ryoikiInfo
     * @return RyoikikeikakushoInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo searchOuboSyoruiInfo(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException;
    
    /**
     * ���发�ނ����F
     * 
     * @param userInfo ���O�C���ҏ��
     * @param ryoikiPk
     * @param ryoikiInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void approveOuboSyorui(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException;
    
    /**
     * ���发�ނ��p��
     * 
     * @param userInfo ���O�C���ҏ��
     * @param ryoikiPk
     * @param ryoikiInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void rejectOuboSyorui(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException;
    
    /**
     * �m�F���[���𑗐M����B
     * �w�U���ؓ���?���O��0:02�ɐ\�������ɂȂ鎖�Ƃɑ΂��āA
     * �\���҂��������鏊���S���҂Ɍ����ă��[���𑗐M����B
     * ���Y�\�������̎��Ƃ����݂��Ȃ��ꍇ�͉����������Ȃ��B
     * @param userInfo
     * @throws ApplicationException
     */
    public void sendMailKakuninTokusoku(UserInfo userInfo)
        throws ApplicationException;
    
    /**
     * ���F���[���𑗐M����B
     * �w�U���ؓ���1���O��0:02�ɐ\�������ɂȂ鎖�Ƃɑ΂��āA
     * �\���҂��������鏊���S���҂Ɍ����ă��[���𑗐M����B
     * ���Y�\�������̎��Ƃ����݂��Ȃ��ꍇ�͉����������Ȃ��B
     * @param userInfo
     * @throws ApplicationException
     */
    public void sendMailShoninTokusoku(UserInfo userInfo)
        throws ApplicationException;
    //add end zjp 2006/06/15
    
//  �{�@2006/06/19 ��������
    /**    
     * ���̈�ԍ����s����o�^����
     * @param userInfo ���[�U���
     * @param pkInfo JigyoKanriPk
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @author DIS.gongXB
     */
    public void registKariBangoHakkoInfo(
            UserInfo userInfo,
            JigyoKanriPk pkInfo)
            throws NoDataFoundException,ApplicationException;
// �{ �����܂�
    
//  mcj�@2006/06/29 ��������
    /**
     * ���F����(��o����)
     * 
     * @param userInfo
     * @param ryoikikeikakushoPk
     * @param ryoikiInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void cancelTeisyutusyoSyonin(UserInfo userInfo,
            RyoikiKeikakushoPk ryoikikeikakushoPk,
            RyoikiKeikakushoInfo ryoikiInfo) 
            throws DataAccessException, NoDataFoundException, ApplicationException;

    /**
     * �ꊇ��(��o���ވꗗ)�����s
     * 
     * @param userInfo
     * @param searchInfo
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void executeIkkatuJuri(
            UserInfo userInfo,
            TeishutsuShoruiSearchInfo searchInfo)
            throws DataAccessException,NoDataFoundException, ApplicationException;

    /**
     * �̈�v�揑�폜�����擾����B
     * 
     * @param userInfo
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @return RyoikiKeikakushoInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo getRyoikiGaiyoDeleteInfo(
            UserInfo userInfo, 
            String ryoikiSystemNo)
            throws NoDataFoundException, ApplicationException;

    /**
     * �̈�v�揑�폜�m�F�̃f�[�^���폜����B
     * 
     * @param userInfo
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void deleteFlagRyoikiGaiyo(
            UserInfo userInfo,
            String ryoikiSystemNo)
            throws NoDataFoundException, ApplicationException;
// mcj 2006/06/29 �����܂�

//�@2006/06/21 dyh add start
    /**    
     * �̈�v�揑�ƌ����v�撲���̈ꗗ���擾����B
     * 
     * @param userInfo ���[�U���
     * @param kariryoikiNo ���̈�ԍ�
     * @return List �̈�v�揑�ƌ����v�撲���̈ꗗ
     * @throws ApplicationException
     */
    public List getRyoikiAndKenkyuList(UserInfo userInfo, String kariryoikiNo)
            throws ApplicationException;

    /**
     * �̈�v�揑�\��PDF�t�@�C�����擾�B
     * 
     * @param userInfo ���O�C���ҏ��
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @return FileResource PDF�t�@�C��
     * @throws ApplicationException
     */
    public FileResource getGaiyoCoverPdfFile(UserInfo userInfo, String ryoikiSystemNo)
            throws ApplicationException;

    /**
     * �̈�v�揑�m�FPDF�t�@�C�����擾�B
     * 
     * @param userInfo ���O�C���ҏ��
     * @param pkInfo �V�X�e����t�ԍ�
     * @return FileResource PDF�t�@�C��
     * @throws ApplicationException
     */
    public FileResource getRyoikiGaiyoPdfFile(UserInfo userInfo, RyoikiKeikakushoPk pkInfo)
            throws ApplicationException;
// 2006/06/21 dyh add end

// 2006/07/21 dyh add start
    /**
     * �̈�v�揑�i�T�v�j���Ǘ��e�[�u���ɁA�f�[�^�̑��݃`�F�b�N
     * ���O�C���҂̐\����ID�ƈ�v���ADEL_FLG�i�폜�t���O�j��[0]
     * @param userInfo
     * @return boolean
     * @throws ApplicationException
     */
    public boolean isExistRyoikiGaiyoInfo(UserInfo userInfo)
            throws ApplicationException;
// 2006/07/21 dyh add end
    
//ADD�@START 2007/07/02 BIS ���� -->
    /**    
     * �̈�v�揑���̈ꗗ���擾����B
     * 
     * @param searchInfo �̈�v�揑���
     * @return List �̈�v�揑�̏��
     * @throws ApplicationException
     */
    public List getRyoikiResult(RyoikiKeikakushoSystemInfo searchInfo)
            throws ApplicationException;    
//ADD�@END�@ 2007/07/02 BIS ���� -->    

}