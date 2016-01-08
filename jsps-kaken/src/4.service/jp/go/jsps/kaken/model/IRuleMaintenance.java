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
import jp.go.jsps.kaken.model.vo.RuleInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.UserInfo;
/**
 * �eID�E�p�X���[�h�̔��s���[���Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IRuleMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IRuleMaintenance {

	/**
	 * ���s���[������V�K�쐬����B
	 * ���s���[���Ɋ�Â��p�X���[�h��ݒ肷��B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param addInfo				�쐬���锭�s���[�����B
	 * @return						�V�K�o�^�������s���[�����
	 * @throws ApplicationException	
	 */
	public RuleInfo insert(UserInfo userInfo,RuleInfo addInfo) throws ApplicationException;

	/**
	 * ���s���[�������X�V����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param updateInfo			�X�V���锭�s���[�����B
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,RuleInfo updateInfo) throws ApplicationException;

	/**
	 * ���s���[�������X�V����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param updateList			�X�V���锭�s���[�����B
	 * @throws ApplicationException
	 */
	public void updateAll(UserInfo userInfo,List updateList) throws ApplicationException;

	/**
	 * ���s���[�������폜����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param deleteInfo			�폜���锭�s���[�����B
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,RuleInfo deleteInfo) throws ApplicationException;

	/**
	 * ���s���[��������������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�������锭�s���[�����B
	 * @return						�擾�������s���[�����
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public RuleInfo select(UserInfo userInfo,RulePk pkInfo) throws ApplicationException;

	/**
	 * ���s���[��������������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @return						�擾�������s���[�����B
	 * @throws ApplicationException
	 */
	public List selectList(UserInfo userInfo) throws ApplicationException;

	/**
	 * �o�^�܂��͍X�V���锭�s���[�������`���`�F�b�N����B
	 * @param userInfo					���s���郆�[�U���
	 * @param insertOrUpdateInfo		�o�^�܂��͐V�K�쐬���锭�s���[�����
	 * @return							�`���`�F�b�N��̔��s���[�����
	 * @throws ApplicationException	
	 * @throws ValidationException		���؂ɃG���[���������ꍇ�B
	 */
	public RuleInfo validate(UserInfo userInfo,RuleInfo insertOrUpdateInfo)
		throws ApplicationException, ValidationException;

}
