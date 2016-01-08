/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
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
 * �\���ҏ����Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IShinsainMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IShinsainMaintenance {

	/**
	 * �\���ҏ���V�K�쐬����B
	 * ���s���[���Ɋ�Â��p�X���[�h��ݒ肷��B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param addInfo				�쐬���郆�[�U���B
	 * @return						�V�K�o�^�������[�U���
	 * @throws ApplicationException	
	 */
	public ShinsainInfo insert(UserInfo userInfo, ShinsainInfo addInfo) throws ApplicationException;

	/**
	 * �\���ҏ����X�V����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param updateInfo			�X�V���郆�[�U���B
	 * @throws ApplicationException
	 */
	public ShinsainInfo update(UserInfo userInfo, ShinsainInfo updateInfo) throws ApplicationException;

	/**
	 * �\���ҏ����폜����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param deleteInfo			�폜���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo, ShinsainInfo deleteInfo) throws ApplicationException, ValidationException;

	/**
	 * �\���ҏ�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�������郆�[�U���B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public ShinsainInfo select(UserInfo userInfo, ShinsainPk pkInfo) throws ApplicationException;


	/**
	 * �\���ҏ�����������B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo, ShinsainSearchInfo searchInfo) throws ApplicationException;


//	/**
//	 * �R�������(���ȍזڕ�)����������B
//	 * @param userInfo				���s���郆�[�U���B
//	 * @param searchInfo			�������
//	 * @return						�������ʃy�[�W���
//	 * @throws ApplicationException	
//	 */
//	public Page searchRank(UserInfo userInfo, ShinsainSearchInfo searchInfo) throws ApplicationException;


//	/**
//	 * ���ȍזږ���t������B
//	 * @param userInfo				���s���郆�[�U���B
//	 * @param shinsainInfo			�R�������
//	 * @return						�R�������
//	 * @throws ApplicationException	
//	 */
//	public ShinsainInfo addSaimoku(UserInfo userInfo, ShinsainInfo shinsainInfo) throws ApplicationException;


	/**
	 * CSV�o�͗p�̐\���ҏ�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������郆�[�U���B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public List searchCsvData(UserInfo userInfo, ShinsainSearchInfo searchInfo) throws ApplicationException;


	/**
	 * �o�^�܂��͍X�V����\���ҏ����`���`�F�b�N����B
	 * @param userInfo					���s���郆�[�U���
	 * @param insertOrUpdateInfo		�o�^�܂��͐V�K�쐬����\���ҏ��
	 * @param mode						�����e�i���X���[�h
	 * @return							�`���`�F�b�N��̃��[�U���
	 * @throws ApplicationException	
	 * @throws ValidationException		���؂ɃG���[���������ꍇ�B
	 */
	public ShinsainInfo validate(UserInfo userInfo, ShinsainInfo info, String mode)
		throws ApplicationException, ValidationException;

	/**
	 * �p�X���[�h��ύX����B
	 * @param userInfo					���s���郆�[�U���
	 * @param pkInfo					�p�X���[�h���X�V���錟�����郆�[�U��L�[���B
	 * @param oldPassword				�X�V�O�p�X���[�h
	 * @param newPassword				�V�����p�X���[�h
	 * @return							�p�X���[�h�̕ύX�ɐ��������ꍇ true �ȊO false
	 * @throws ApplicationException	
	 * @throws ValidationException		�X�V�O�p�X���[�h����v���Ȃ��ꍇ���A���؂ɃG���[���������ꍇ�B
	 */
	public boolean changePassword(UserInfo userInfo, ShinsainPk pkInfo, String oldPassword, String newPassword)
		throws ApplicationException,ValidationException;		


	/**
	 * �R�����̃p�X���[�h���Đݒ肷��B
	 * ���s���[���Ɋ�Â��p�X���[�h��ݒ肷��B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�p�X���[�h���Đݒ肷�錟�����郆�[�U��L�[���B
	 * @return						�p�X���[�h���Đݒ肵�����[�U���
	 * @throws ApplicationException	
	 */
	public ShinsainInfo reconfigurePassword(UserInfo userInfo,ShinsainPk pkInfo) throws ApplicationException;
	
	
	
	/**
	 * �R���˗����𔭍s����B�i�R�����Ǘ��p�j
	 * @param userInfo				���s���郆�[�U���
	 * @param searchInfo			��������
	 * @return						�R���˗������k�t�@�C����FileResource
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public FileResource createIraisho(UserInfo userInfo, ShinsainSearchInfo searchInfo) throws ApplicationException;	
	
	
	
}