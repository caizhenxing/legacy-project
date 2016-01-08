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
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.model.vo.WarifuriInfo;
import jp.go.jsps.kaken.model.vo.WarifuriPk;
import jp.go.jsps.kaken.model.vo.WarifuriSearchInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * ����U�茋�ʏ����Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IShinsainWarifuri.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:49 $"
 */
public interface IShinsainWarifuri {
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** �R�����l���i��Ձj */
	public static final int SHINSAIN_NINZU_KIBAN   = 12;
	
	/** �R�����l���i�w�n�j */
	public static final int SHINSAIN_NINZU_GAKUSOU = 6;
		
	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	

	/**
	 * ����U�茋�ʏ���V�K�쐬����B
	 * ���s���[���Ɋ�Â��p�X���[�h��ݒ肷��B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param addInfo				�쐬���郆�[�U���B
	 * @return						�V�K�o�^�������[�U���
	 * @throws ApplicationException	
	 */
	public WarifuriInfo insert(UserInfo userInfo,WarifuriInfo addInfo) throws ApplicationException;

	/**
	 * ����U�茋�ʏ����X�V����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param shinsaKekkaPk		�X�V���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo,WarifuriPk WarifuriPk) throws ApplicationException;

	/**
	 * ����U�茋�ʏ����폜����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param shinsaKekkaPk		�폜���郆�[�U���B
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo,ShinsaKekkaPk shinsaKekkaPk) throws ApplicationException;

	/**
	 * ����U�茋�ʏ�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�������郆�[�U���B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public WarifuriInfo select(UserInfo userInfo, ShinsaKekkaPk infoPk) throws ApplicationException;


	/**
	 * ����U�茋�ʏ�����������B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo,WarifuriSearchInfo searchInfo) throws ApplicationException;


	/**
	 * �o�^�܂��͍X�V���銄��U�茋�ʏ����`���`�F�b�N����B
	 * @param userInfo					���s���郆�[�U���
	 * @param insertOrUpdateInfo		�o�^�܂��͐V�K�쐬���銄��U�茋�ʏ��
	 * @param mode						�����e�i���X���[�h
	 * @return							�`���`�F�b�N��̃��[�U���
	 * @throws ApplicationException	
	 * @throws ValidationException		���؂ɃG���[���������ꍇ�B
	 */
//	public WarifuriInfo validate(UserInfo userInfo,WarifuriInfo insertOrUpdateInfo, String mode)
//		throws ApplicationException, ValidationException;


	/**
	 * CSV�o�͗p�̐\���ҏ�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������郆�[�U���B
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public FileResource createIraisho(UserInfo userInfo, WarifuriSearchInfo searchInfo) throws ApplicationException;


	/**
	 * ���Ƌ敪���擾����B
	 * 
	 * @param userInfo				���s���郆�[�U���
	 * @param jigyoCd				���ƃR�[�h
	 * @return						���Ƌ敪
	 * @throws ApplicationException
	 */
	public String selectJigyoKubun(UserInfo userInfo, String jigyoCd) throws ApplicationException;
	

}