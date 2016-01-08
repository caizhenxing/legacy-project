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

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * �Ɩ��S���ҏ����Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IGyomutantoMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:49 $"
 */
public interface IGyomutantoMaintenance {

	/**
	 * �Ɩ��S���ҏ���V�K�쐬����B
	 * ���s���[���Ɋ�Â��p�X���[�h��ݒ肷��B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param addInfo				�쐬���郆�[�U���B
	 * @return						�V�K�o�^�������[�U���
	 * @throws ApplicationException	
	 */
	public GyomutantoInfo insert(UserInfo userInfo,GyomutantoInfo addInfo) throws ApplicationException;

	/**
	 * �Ɩ��S���ҏ����X�V����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param updateInfo			�X�V���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,GyomutantoInfo updateInfo) throws ApplicationException;

	/**
	 * �Ɩ��S���ҏ����폜����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param deleteInfo			�폜���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,GyomutantoInfo deleteInfo) throws ApplicationException;

	/**
	 * �Ɩ��S���ҏ�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�������郆�[�U���B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public GyomutantoInfo select(UserInfo userInfo,GyomutantoPk pkInfo) throws ApplicationException;


	/**
	 * �Ɩ��S���ҏ�����������B
	 * @param userInfo				���s���郆�[�U���B
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo) throws ApplicationException;


	/**
	 * �o�^�܂��͍X�V����Ɩ��S���ҏ����`���`�F�b�N����B
	 * @param userInfo					���s���郆�[�U���
	 * @param insertOrUpdateInfo		�o�^�܂��͐V�K�쐬����\���ҏ��
	 * @param mode						�����e�i���X���[�h
	 * @return							�`���`�F�b�N��̃��[�U���
	 * @throws ApplicationException	
	 * @throws ValidationException		���؂ɃG���[���������ꍇ�B
	 */
	public GyomutantoInfo validate(UserInfo userInfo,GyomutantoInfo insertOrUpdateInfo, String mode)
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
	public boolean changePassword(UserInfo userInfo, GyomutantoPk pkInfo,String oldPassword ,String newPassword)
		throws ApplicationException,ValidationException;		


}