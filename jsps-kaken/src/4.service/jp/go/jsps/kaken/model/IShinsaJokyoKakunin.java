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
import jp.go.jsps.kaken.model.vo.ShinsaJokyoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * �R���󋵏����Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IShinsaJokyoKakunin.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:49 $"
 */
public interface IShinsaJokyoKakunin {

	/**
	 * �R���󋵏���V�K�쐬����B
	 * ���s���[���Ɋ�Â��p�X���[�h��ݒ肷��B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param addInfo				�쐬���郆�[�U���B
	 * @return						�V�K�o�^�������[�U���B
	 * @throws ApplicationException	
	 */
	public ShinsaJokyoInfo insert(UserInfo userInfo,ShinsaJokyoInfo addInfo) throws ApplicationException;

	/**
	 * �R���󋵏����X�V����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param updateInfo			�X�V���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,ShinsaKekkaInfo updateInfo) throws ApplicationException;

	/**
	 * �R���󋵏����폜����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param deleteInfo			�폜���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,ShinsaJokyoInfo deleteInfo) throws ApplicationException;


	/**
	 * �R���󋵏�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @return						�擾�������[�U���B
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public List searchCsvData(UserInfo userInfo,ShinsaJokyoSearchInfo searchInfo) throws ApplicationException;

	/**
	 * �R���󋵏�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�������郆�[�U���B
	 * @return						�擾�������[�U���B
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public ShinsaJokyoInfo select(UserInfo userInfo,ShinsaJokyoSearchInfo searchInfo) throws ApplicationException;


	/**
	 * �R���󋵏�����������B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������B
	 * @return						�������ʃy�[�W���B
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo,ShinsaJokyoSearchInfo searchInfo) throws ApplicationException;


	/**
	 * �I�����̐R���󋵂𖢊����Ƃ��A�R���󋵏�����������B
	 * @param userInfo				���s���郆�[�U���B
	 * @param shinsaJokyoInfo		�R���󋵏��B
	 * @throws ApplicationException	
	 * @throws ValidationException	
	 */
	public void saishinsa(UserInfo userInfo, ShinsaJokyoInfo shinsaJokyoInfo) throws ApplicationException, ValidationException;

//2006/10/25 jinbaogang add start
	/**
	 * ���v�����ē��͊����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param shinsaJokyoInfo		���v�����ē��͏��B
	 * @throws ApplicationException	
	 * @throws ValidationException	
	 */
	public void updateSaiNyuryoku(UserInfo userInfo, ShinsaJokyoInfo shinsaJokyoInfo) throws ApplicationException, ValidationException;
//  2006/10/25 jinbaogang add end
    
	/**
	 * �o�^�܂��͍X�V����R���󋵏����`���`�F�b�N����B
	 * @param userInfo					���s���郆�[�U���B
	 * @param insertOrUpdateInfo		�o�^�܂��͐V�K�쐬����R���󋵏��B
	 * @param mode						�����e�i���X���[�h�B
	 * @return							�`���`�F�b�N��̃��[�U���B
	 * @throws ApplicationException	
	 * @throws ValidationException		���؂ɃG���[���������ꍇ�B
	 */
	public ShinsaJokyoInfo validate(UserInfo userInfo,ShinsaJokyoInfo insertOrUpdateInfo, String mode)
		throws ApplicationException, ValidationException;


	/**
	 * ���Y�R�������S�����铖�Y����ID�̐R�����ʂ��N���A����B
	 * ���Y�R���������݂��Ă��Ȃ��Ă��N���A����B�������A[�R��������]�̂��̂����B
	 * @param userInfo
	 * @param shinsaJokyoInfo
	 * @throws ApplicationException
	 */
	public void clearShinsaKekka(UserInfo userInfo, ShinsaJokyoInfo shinsaJokyoInfo)
			throws ApplicationException;
	
	
	
	
	
	
	
	
}