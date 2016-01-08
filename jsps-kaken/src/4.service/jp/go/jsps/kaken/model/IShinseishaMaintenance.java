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

import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaPk;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * �\���ҏ����Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IShinseishaMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IShinseishaMaintenance {

	/**
	 * �\���ҏ���V�K�쐬����B
	 * ���s���[���Ɋ�Â��p�X���[�h��ݒ肷��B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param addInfo				�쐬���郆�[�U���B
	 * @return						�V�K�o�^�������[�U���
	 * @throws ApplicationException	
	 */
	public ShinseishaInfo insert(UserInfo userInfo,ShinseishaInfo addInfo) throws ApplicationException;

	/**
	 * �\���ҏ����X�V����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param updateInfo			�X�V���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,ShinseishaInfo updateInfo) throws ApplicationException;

	/**
	 * �\���ҏ����폜����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param deleteInfo			�폜���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,ShinseishaInfo deleteInfo) throws ApplicationException;

	/**
	 * �\���ҏ�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�������郆�[�U���B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public ShinseishaInfo select(UserInfo userInfo,ShinseishaPk pkInfo) throws ApplicationException;


	/**
	 * �\���ҏ�����������B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo,ShinseishaSearchInfo searchInfo) throws ApplicationException;
	
	/**
	 * CSV�o�͗p�̐\���ҏ�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������郆�[�U���B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public List searchCsvData(UserInfo userInfo,ShinseishaSearchInfo searchInfo) throws ApplicationException;


	/**
	 * �o�^�܂��͍X�V����\���ҏ����`���`�F�b�N����B
	 * @param userInfo					���s���郆�[�U���
	 * @param insertOrUpdateInfo		�o�^�܂��͐V�K�쐬����\���ҏ��
	 * @param mode						�����e�i���X���[�h
	 * @return							�`���`�F�b�N��̃��[�U���
	 * @throws ApplicationException	
	 * @throws ValidationException		���؂ɃG���[���������ꍇ�B
	 */
	public ShinseishaInfo validate(UserInfo userInfo,ShinseishaInfo insertOrUpdateInfo, String mode)
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
	public boolean changePassword(UserInfo userInfo, ShinseishaPk pkInfo,String oldPassword ,String newPassword)
		throws ApplicationException,ValidationException;		

	/**
	 * �\���҂̃p�X���[�h���Đݒ肷��B
	 * ���s���[���Ɋ�Â��p�X���[�h��ݒ肷��B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�p�X���[�h���Đݒ肷�錟�����郆�[�U��L�[���B
	 * @return						�p�X���[�h���Đݒ肵�����[�U���
	 * @throws ApplicationException	
	 */
	public ShinseishaInfo reconfigurePassword(UserInfo userInfo,ShinseishaPk pkInfo) throws ApplicationException;
	

	/**
	 * ����剞��t���O���O���B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�񉞕�\���t���O���O�����[�U�̏��B
	 * @throws ApplicationException	
	 */
	public void deleteHikoboFlgInfo(UserInfo userInfo, ShinseishaSearchInfo searchInfo) throws ApplicationException;
	
	//2005/04/06�@�ǉ���������@�p�X���[�h�ꊇ�Đݒ胁�\�b�h�ǉ��̂���
	
	/**
	 * �p�X���[�h���ꊇ�Đݒ肷��B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�p�X���[�h���Đݒ肷�錟�����郆�[�U��L�[���B
	 * @param array				�\����ID�̔z��						
	 * @throws ApplicationException	
	 */
	public void reconfigurePasswordAll(UserInfo userInfo, ShinseishaPk pkInfo, ArrayList array) throws ApplicationException;
	
	// �ǉ������܂�
}