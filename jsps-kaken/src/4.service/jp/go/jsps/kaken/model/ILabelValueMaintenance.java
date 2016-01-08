/*======================================================================
 *    SYSTEM      : �d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ILabelValueMaintenance
 *    Description : ���x���Ǘ����s���C���^�[�t�F�[�X
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

import java.util.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;

/**
 * ���x���Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: ILabelValueMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface ILabelValueMaintenance {
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	

	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
    
// 20050526 Start
    /**
	 * �����̈�敪���擾����B
	 * @return	�����̈�敪���X�g
     * @throws ApplicationException
	 */
	public List getKenkyuKubunList() throws ApplicationException;
// Horikoshi End

	/**
	 * �����@�֎�ʂ��擾����B
	 * @return	�����@�֎�ʃ��X�g
     * @throws ApplicationException
	 */
	public List getKikanShubetuCdList() throws ApplicationException;
	
	/**
	 * ���Ɩ����擾����B
	 * @return	���Ɩ����X�g
     * @throws ApplicationException
	 */
	public List getJigyoNameList() throws ApplicationException;

	/**
	 * ���Ɩ����擾����B
	 * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A�����̒S�����鎖�Ƌ敪�݂̂�
	 * ���Ɩ����X�g���Ԃ�B
     * @param userInfo ���[�U���
	 * @return	���Ɩ����X�g
     * @throws ApplicationException
	 */
	public List getJigyoNameList(UserInfo userInfo) throws ApplicationException;

	/**
	 * �w�肳�ꂽ���Ƌ敪�̎��Ɩ����擾����B
     * @param userInfo ���[�U���
	 * @param	jigyoKubun	���Ƌ敪
	 * @return	���Ɩ����X�g
     * @throws ApplicationException
	 */
	public List getJigyoNameList(UserInfo userInfo, String jigyoKubun) throws ApplicationException;

	/**
	 * ���Ɩ����擾����B
	 * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
	 * �����̒S�����鎖�ƁE�R���S�����鎖�Ƌ敪�i1�܂���4�̏ꍇ�j�݂̂̎��Ɩ����X�g���Ԃ�
     * @param userInfo ���[�U���
	 * @return	���Ɩ����X�g
     * @throws ApplicationException
	 */
	public List getShinsaTaishoJigyoNameList(UserInfo userInfo) throws ApplicationException;
		
	/**
	 * �E�탊�X�g���擾����B
	 * @return	�E�탊�X�g
     * @throws ApplicationException
	 */
	public List getShokushuList() throws ApplicationException;

	/**
	 * �J�e�S�����X�g���擾����B
	 * @return	�J�e�S�����X�g
     * @throws ApplicationException
	 */
	public List getKategoriCdList() throws ApplicationException;

	/**
	 * ���x�������擾����B�I�����X�g�ɕ\������f�[�^�̂ݎ擾����B
	 * ���Y�u���x���敪�v�̃f�[�^�����݂��Ȃ������ꍇ�ANoDataFoundException ��
	 * �X���[����B
	 * @param	labelKubun	���x���敪
	 * @return				���x�������X�g
     * @throws ApplicationException
	 */
	public List getLabelList(String labelKubun) throws ApplicationException;
	
	/**
	 * ���x�������擾����B���x���敪�Ɉ�v���邷�ׂẴf�[�^���擾����B
	 * ���Y�u���x���敪�v�̃f�[�^�����݂��Ȃ������ꍇ�ANoDataFoundException ��
	 * �X���[����B
	 * @param	labelKubun	���x���敪
	 * @return				���x�������X�g
     * @throws ApplicationException
	 */
	public List getAllLabelList(String labelKubun) throws ApplicationException;
	
	/**
	 * ���x�������擾����B
	 * LabelValueBean��List���AList�̌`���Ŗ߂�B
	 * ���Y�u���x���敪�v�̃f�[�^�����݂��Ȃ������ꍇ�A�߂�l��List���ɂ� NULL ��
	 * �i�[�����B
	 * @param	labelKubun	���x���敪
	 * @return				���x�������X�g
     * @throws ApplicationException
	 */
	public List getLabelList(String[] labelKubun) throws ApplicationException;	
	

	//2005.10.25 iso ���x�����X�g���ꊇ�擾���邽�ߒǉ�
	/**
	 * ���x�������擾����B
	 * LabelValueBean��List���AMap�̌`���Ŗ߂�B
	 * @param	labelKubun	���x���敪
	 * @return				���x���}�b�v
     * @throws ApplicationException
	 */
	public Map getLabelMap(String[] labelKubun) throws ApplicationException;
	
	/**
	 * ���x�������擾����B���x���敪�Ɉ�v���邷�ׂẴf�[�^���擾����B
	 * LabelValueBean��List���AList�̌`���Ŗ߂�B
	 * ���Y�u���x���敪�v�̃f�[�^�����݂��Ȃ������ꍇ�A�߂�l��List���ɂ� NULL ��
	 * �i�[�����B
	 * @param	labelKubun	���x���敪
	 * @return				���x�������X�g
     * @throws ApplicationException
	 */
	public List getAllLabelList(String[] labelKubun) throws ApplicationException;

	/**
	 * ���x���}�X�^�̂P���R�[�h��Map�`���ŕԂ��B
	 * ���Y�f�[�^�����݂��Ȃ������ꍇ�ANoDataFoundException ��
	 * �X���[����B
	 * @param	labelKubun	���x���敪
	 * @param	value		�l
	 * @return				���x����
     * @throws ApplicationException
	 */
	public Map selectRecord(String labelKubun, String value) throws ApplicationException;	
	
//	2005/04/18 �ǉ� ��������----------
//	���R:�C�O����ǉ��̂���

	/**
	 * �C�O���샊�X�g���擾����B
	 * @return	�E�탊�X�g
     * @throws ApplicationException
	 */
	public List getKaigaiBunyaList() throws ApplicationException;
	
//	2005/04/12 �ǉ� �����܂�----------

//2007/02/08 �c�@�폜��������@�g�p���Ȃ�    
//2006/02/15 �ǉ� ��������----------
//	/**
//	 * ���샊�X�g���擾����B
//	 * @return	�E�탊�X�g
//     * @throws ApplicationException
//	 */
//	public List getKiboRyoikiList() throws ApplicationException;
// syuu �ǉ� �����܂� ----------
//2007/02/08�@�c�@�폜�����܂�    


	//2005/04/27 �ǉ� ��������--------------
	//�����̒S�����鎖�ƃR�[�h�ōi�荞�񂾎��Ɩ���\�����邽��
	/**
	 * ���Ɩ����擾����B
	 * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
	 * �����̒S�����鎖��CD�݂̂̎��Ɩ����X�g���Ԃ�
     * @param userInfo ���[�U���
	 * @return	���Ɩ����X�g
     * @throws ApplicationException
	 */
	public List getJigyoNameListByJigyoCds(UserInfo userInfo) throws ApplicationException;
	//�ǉ� �����܂�-------------------------
	

	/**
	 * ���Ɩ����擾����B
	 * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
	 * �����̒S�����鎖��CD�E���Ƌ敪�i1�܂���4�̏ꍇ�j�݂̂̎��Ɩ����X�g���Ԃ�
     * @param userInfo ���[�U���
     * @param jigyoKubun ���Ƌ敪
	 * @return	���Ɩ����X�g
     * @throws ApplicationException
	 */
	public List getJigyoNameListByJigyoCds(UserInfo userInfo, String jigyoKubun)
		throws ApplicationException;
	

	/**
	 * ���Ɩ����擾����B
	 * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
	 * �����̒S�����鎖�ƁE�R���S�����鎖�Ƌ敪�i1�܂���4�̏ꍇ�j�݂̂̎��Ɩ����X�g���Ԃ�
     * @param userInfo ���[�U���
	 * @return	���Ɩ����X�g
     * @throws ApplicationException
	 */
	public List getShinsaTaishoListByJigyoCds(UserInfo userInfo) throws ApplicationException;
    
    
//  2006/06/02 �c �ǉ���������
    /**
     * ���Ɩ��i���Ɓi���S,A,B�j�̍폜�j���擾����B
     * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
     * �����̒S�����鎖�ƁE�R���S�����鎖�Ƌ敪�i1�܂���4�̏ꍇ�j�݂̂̎��Ɩ����X�g���Ԃ�
     * @param userInfo ���[�U���
     * @param jigyoCds ���ƃR�[�h���X�g
     * @param jigyoKubun ���Ƌ敪
     * @return  ���Ɩ����X�g
     * @throws ApplicationException
     */
     public List getJigyoNameListByJigyoCds(UserInfo userInfo, String[] jigyoCds, String jigyoKubun) throws ApplicationException; 
//2006/06/02 �c �ǉ������܂�    
    
//    //2006/06/06 jzx�@add start
//    //�����̒S�����鎖�ƃR�[�h�ōi�荞�񂾎��Ɩ���\�����邽��
//    /**
//     * ���Ɩ����擾����B
//     * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
//     * �����̒S�����鎖��CD�݂̂̎��Ɩ����X�g���Ԃ�
//     * @param userInfo ���[�U���
//     * @return  ���Ɩ����X�g
//     * @throws ApplicationException
//     */
//     public List getJigyoNameListByJigyoCds2(UserInfo userInfo) throws ApplicationException;
//    //2006/06/06 jzx�@add end
//     
//    //2006/06/06 lwj�@add start
//     /**
//      * ���Ɩ����擾����B
//      * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
//      * �����̒S�����鎖��CD�݂̂̎��Ɩ����X�g���Ԃ�
//      * @param userInfo ���[�U���
//      * @param jigyoCd
//      * @return  ���Ɩ����X�g
//      * @throws ApplicationException
//      */
//     public List getJigyoNameListByJigyoCds3(UserInfo userInfo, String jigyoCd)throws ApplicationException;
//    //2006/06/06 lwj�@add end
}