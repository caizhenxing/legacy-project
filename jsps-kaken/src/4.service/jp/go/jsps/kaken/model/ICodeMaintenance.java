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
import jp.go.jsps.kaken.model.vo.UserInfo;

/**
 * �R�[�h�\���̊Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: ICodeMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface ICodeMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** �߂�lMap�L�[�l�F�����p���X�g */
	public static final String KEY_INDEX_LIST = "key_index_list";
		
	/** �߂�lMap�L�[�l�F�����p���X�g */
	public static final String KEY_SEARCH_LIST = "key_search_list";	
	
	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
	
	/**
	 * �����@�փ}�X�^���珊���@�֎�ʃR�[�h���Ƃ̏����@�֏��̈ꗗ���擾����B
	 * 
	 * @param userInfo				���s���郆�[�U���
	 * @param shubetuCd			�����@�֎�ʃR�[�h
	 * @return						�擾�������[�U���
	 * @throws ApplicationException
	 */
//	public List getKikanInfoList(UserInfo userInfo, String shubetuCd) throws ApplicationException;

	/**
	 * �����@�փ}�X�^���珊���@�֏��̈ꗗ���擾����B
	 * ���ׂĂ̏����@�֎�ʃR�[�h�̏����@�֏������X�g�ŕԂ��B
	 * 
	 * @param userInfo				���s���郆�[�U���
	 * @param searchInfo			�������郆�[�U���
	 * @return						�擾�������[�U���
	 * @throws ApplicationException
	 */
	public Map getKikanInfoList(UserInfo userInfo) throws ApplicationException;
	
	/**
	 * �����@�֎�ʃ}�X�^���珊���@�֎�ʏ��̈ꗗ���擾����B
	 *
	 * @return						�擾�������[�U���
	 * @throws ApplicationException
	 */
//	public List getKikanShubetuInfoList(UserInfo userInfo) throws ApplicationException;

	/**
	 * ���ȍזڃ}�X�^���畔���̈ꗗ���擾����B
	 * 
	 * @param userInfo				���s���郆�[�U���
	 * @return						�擾�������[�U���
	 * @throws ApplicationException
	 */
//	public List getBuNameList(UserInfo userInfo) throws ApplicationException;

	/**
	 * ���ȍזڃ}�X�^���番�Ȗ��̈ꗗ���擾����B
	 * 
	 * @param userInfo				���s���郆�[�U���
	 * @return						�擾�������[�U���
	 * @throws ApplicationException
	 */
//	public List getBunkaNameList(UserInfo userInfo, String buName) throws ApplicationException;
	
	/**
	 * ���ȍזڃ}�X�^���番�Ȗ����Ƃ̕��ȍזڏ��̈ꗗ���擾����B
	 * 
	 * @param userInfo				���s���郆�[�U���
	 * @param bunkaName			���Ȗ�
	 * @return						�擾�������[�U���
	 * @throws ApplicationException
	 */
//	public List getSaimokuInfoList(UserInfo userInfo, String bunkaName) throws ApplicationException;

	/**
	 * ���ȍזڃ}�X�^���番�ȍזڏ��̈ꗗ���擾����B
	 * ���ׂĂ̏����@�֎�ʃR�[�h�̏����@�֏������X�g�ŕԂ��B
	 * 
	 * @param userInfo				���s���郆�[�U���
	 * @param searchInfo			�������郆�[�U���
	 * @return						�擾�������[�U���
	 * @throws ApplicationException
	 */
	public Map getSaimokuInfoList(UserInfo userInfo) throws ApplicationException;

	
	/**
	 * �������Ƃ̕��Ǐ��̈ꗗ���擾����B
	 * @param userInfo				���s���郆�[�U���
	 * @param index				����
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
//	public List getBukyokuInfoList(UserInfo userInfo,String index) throws ApplicationException;
	
	/**
	 * ���ǃ}�X�^���畔�Ǐ��̈ꗗ���擾����B
	 * ���ׂĂ̕��ǃR�[�h�̕��Ǐ������X�g�ŕԂ��B
	 * 
	 * @param userInfo				���s���郆�[�U���
	 * @param searchInfo			�������郆�[�U���
	 * @return						�擾�������[�U���
	 * @throws ApplicationException
	 */
	public Map getBukyokuInfoList(UserInfo userInfo) throws ApplicationException;

	/**
	 * ���ǃ}�X�^��������̈ꗗ���擾����B
	 *
	 * @return						�擾�������[�U���
	 * @throws ApplicationException
	 */
//	public List getSakuinList(UserInfo userInfo) throws ApplicationException;

	/**
	 * �L�[���[�h�ꗗ�\�������擾����
	 * @param userInfo
	 * @return�@�L�[���[�h�ꗗ�\�����
	 * @throws ApplicationException
	 */
	public Map getKeywordInfoList(UserInfo userInfo) throws ApplicationException ;

	/**
	 * �̈�}�X�^�ꗗ���擾
	 * @param userInfo
	 * @return
	 * @throws ApplicationException
	 */
	public Map getRyoikiInfoList(UserInfo userInfo) throws ApplicationException;
    
//2006/07/24�@�c�@�ǉ���������
    /**
     * �̈�}�X�^�i�V�K�̈�j�ꗗ���擾
     * @param userInfo
     * @return
     * @throws ApplicationException
     */
    public Map getRyoikiSinnkiInfoList(UserInfo userInfo) throws ApplicationException;
//2006/07/24�@�c�@�ǉ������܂�    
	
}