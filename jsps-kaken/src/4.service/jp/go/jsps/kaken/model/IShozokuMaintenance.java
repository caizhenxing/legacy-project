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

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.KikanInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.model.vo.ShozokuSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * �����@�֏����Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IShozokuMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:49 $"
 */
public interface IShozokuMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** ���̑��̏����@�փR�[�h */
	public static final String OTHER_KIKAN_CODE = "99999";

	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	

	/**
	 * �����@�֏���V�K�쐬����B
	 * ���s���[���Ɋ�Â��p�X���[�h��ݒ肷��B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param addInfo				�쐬���郆�[�U���B
	 * @return						�V�K�o�^�������[�U���
	 * @throws ApplicationException	
	 */
	public ShozokuInfo insert(UserInfo userInfo,ShozokuInfo addInfo) throws ApplicationException;

	/**
	 * �����@�֏����X�V����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param updateInfo			�X�V���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,ShozokuInfo updateInfo) throws ApplicationException;

	/**
	 * �����@�֏����폜����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param deleteInfo			�폜���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,ShozokuInfo deleteInfo) throws ApplicationException;

	/**
	 * �����@�֏�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param keyInfo				�������郆�[�U���B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public ShozokuInfo select(UserInfo userInfo,ShozokuPk pkInfo) throws ApplicationException;
	
	/**
	 * �����@�֏��������@�փ}�X�^���猟������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param kikanInfo			�������B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public KikanInfo select(UserInfo userInfo, KikanInfo kikanInfo) throws ApplicationException;
	
	/**
	 * �����@�փR�[�h���L�[�Ƃ��ď����@�֒S���҂���������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param shozokuCd			�������B�����@�փR�[�h�B
	 * @return						�����@�֒S���҂̐��B
	 * @throws ApplicationException
	 */
	public int select(UserInfo userInfo, String shozokuCd) throws ApplicationException;

	/**
	 * �����@�փR�[�h���L�[�Ƃ��ď����@�֒S���ҏ����������A�����@�֒S����ID�̏����̃��X�g��Ԃ��B
	 * �����@�փR�[�h��null�܂���""�̏ꍇ�́A�S����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param shozokuCd			�������B�����@�փR�[�h�B
	 * @return						�����@�֏��B
	 * @throws ApplicationException
	 */
	public List searchShozokuInfo(UserInfo userInfo, String shozokuCd) throws ApplicationException;

	/**
	 * �����@�֏�����������B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo,ShozokuSearchInfo searchInfo) throws ApplicationException;

	/**
	 * CSV�o�͗p�̏����@�֏�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������郆�[�U���B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public List searchCsvData(UserInfo userInfo,ShozokuSearchInfo searchInfo) throws ApplicationException;

	/**
	 * CSV�o�͗p�̏����@�֏�����������B(�V�X�e���Ǘ��җp)
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������郆�[�U���B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public List searchCsvDataForSysMng(UserInfo userInfo,ShozokuSearchInfo searchInfo) throws ApplicationException;

	/**
	 * �o�^�܂��͍X�V���鏊���@�֏����`���`�F�b�N����B
	 * 
	 * @param userInfo					���s���郆�[�U���
	 * @param insertOrUpdateInfo		�o�^�܂��͐V�K�쐬���鏊���@�֏��
	 * @return							�`���`�F�b�N��̏����@�֏��
	 * @throws ApplicationException	
	 * @throws ValidationException		���؂ɃG���[���������ꍇ�B
	 */
	public ShozokuInfo validate(UserInfo userInfo,ShozokuInfo insertOrUpdateInfo)
		throws ApplicationException, ValidationException;


	/**
	 * �p�X���[�h��ύX����B
	 * 
	 * @param userInfo					���s���郆�[�U���
	 * @param pkInfo					�p�X���[�h���X�V���錟�����郆�[�U��L�[���B
	 * @param oldPassword				�X�V�O�p�X���[�h
	 * @param newPassword				�V�����p�X���[�h
	 * @return							�p�X���[�h�̕ύX�ɐ��������ꍇ true �ȊO false
	 * @throws ApplicationException	
	 * @throws ValidationException		�X�V�O�p�X���[�h����v���Ȃ��ꍇ���A���؂ɃG���[���������ꍇ�B
	 */
	public boolean changePassword(UserInfo userInfo, ShozokuPk pkInfo,String oldPassword ,String newPassword)
		throws ApplicationException,ValidationException;
	
//	2005/04/20 �ǉ� ��������----------------------------------------
//	���R �V�X�e���Ǘ��Ҍ����@�\�̏����@�֏�񌟍��p
	/**
	 * �����@�֏�����������B
	 * 
	 * @param userInfo					���s���郆�[�U���
	 * @param searchInfo				��������
	 * @return							�������ʃy�[�W���
	 * @throws ApplicationException
	 */
	public Page searchShozokuTantoList(UserInfo userInfo, ShozokuSearchInfo searchInfo)
		throws ApplicationException;
//	�ǉ� �����܂�-----------------------------------------------------

//	2005/04/21 �ǉ� ��������----------------------------------------
//	���R �p�X���[�h�Đݒ菈���B
	/**
	 * 
	 * @param userInfo					���s���郆�[�U���
	 * @param pkInfo					�L�[���
	 * @return							�������ʃy�[�W���
	 * @throws ApplicationException
	 */
	public ShozokuInfo reconfigurePassword(UserInfo userInfo, ShozokuPk pkInfo)
		throws ApplicationException;
//	�ǉ� �����܂�-----------------------------------------------------

	/**
	 * ���F�m�F���[���𑗐M����B
	 * �w�U���ؓ��̂R���O��0:00�ɐ\�������ɂȂ鎖�Ƃɑ΂��āA
	 * �\���҂��������鏊���S���҂Ɍ����ă��[���𑗐M����B
	 * ���Y�\�������̎��Ƃ����݂��Ȃ��ꍇ�͉����������Ȃ��B
	 * @param userInfo
	 * @throws ApplicationException
	 */
	public void sendMailShoninTsuchi(UserInfo userInfo)
		throws ApplicationException;

}