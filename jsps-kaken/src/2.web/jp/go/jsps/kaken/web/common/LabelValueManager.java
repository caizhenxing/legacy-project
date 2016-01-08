/*======================================================================
 *    SYSTEM      : �d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : LabelValueManager
 *    Description : ���X�g�{�b�N�X��v���_�E�����j���[�̃��X�g�𐶐����邽�߂̃N���X
 *
 *    Author      : Admin
 *    Date        : 2003/12/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ILabelKubun;
import jp.go.jsps.kaken.model.common.SystemServiceFactory;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.UserInfo;

import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.util.StringUtil;

/**
 * ���X�g�{�b�N�X��v���_�E�����j���[�̃��X�g�𐶐����邽�߂̃N���X�B
 * ID RCSfile="$RCSfile: LabelValueManager.java,v $"
 * Revision="$Revision: 1.6 $"
 * Date="$Date: 2007/07/19 06:54:03 $"
 */
public class LabelValueManager {

	//---------------------------------------------------------------------
	// �ėp���\�b�h
	//---------------------------------------------------------------------
	/**
	 * LavelValueList�𐶐����擾����B
	 * ��1������List�ɂ�Map�`���̃I�u�W�F�N�g���i�[����Ă��邱�ƁB
	 * �i�[����Ă���Map����A��2�������L�[�Ƃ��Ď擾�����l�����x���Ƃ��āA
	 * ��3�������L�[�Ƃ��Ď擾�����l���o�����[�l�Ƃ���LavelValueBean�𐶐����AList�Ƃ��ĕԂ��B
	 * @param recordList  ���R�[�h���X�g
	 * @param labelName   ���x����
	 * @param valueName   �o�����[�l
	 * @return LabelValueBean�̃��X�g
	 */
	public static List getLavelValueList(List recordList, 
										 String labelName, 
										 String valueName) {
		List beanList = new ArrayList();
		for(int i=0; i<recordList.size(); i++){
			Map record = (Map)recordList.get(i);
			beanList.add(new LabelValueBean((String)record.get(labelName),
											 (String)record.get(valueName)));
		}
		return beanList;
	}

	//2005.10.25 iso ���x�����X�g�ꊇ�擾�̂��ߒǉ�
	/**
	 * ���x�������擾����B
	 * ���x�����AMap�̌`���Ŗ߂�B
	 * @param	labelKubun	���x���敪
	 * @return				���x���}�b�v
     * @throws ApplicationException
	 */
	public static Map getLabelMap(String[] labelKubun) throws ApplicationException {

		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelMap(labelKubun);
	}

	//---------------------------------------------------------------------
	// �����@�֎��
	//---------------------------------------------------------------------
	/**
	 * �����@�֎�ʂ��擾����B
	 * @return	�����@�֎�ʃ��X�g
     * @throws ApplicationException
	 */
	public static List getKikanShubetuCdList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List shubetuCdList = servise.getKikanShubetuCdList();
		return shubetuCdList;
	}

	/**
	 * �����@�֎�ʃR�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�����@�֎�ʃR�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getKikanShubetuCdValue(String value) 
		throws ApplicationException {		
		return getLabel(getKikanShubetuCdList(), value);
	}

	//---------------------------------------------------------------------
	// ���ǎ�ʃ��X�g
	//---------------------------------------------------------------------
	/**
	 * ���ǎ�ʂ��擾����B
	 * @return	���ǎ�ʃ��X�g
     * @throws ApplicationException
	 */
	public static List getBukyokuShubetuCdList()
			throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List bukyokuCdList = servise.getLabelList(ILabelKubun.BUKYOKU_SHUBETU);
		return bukyokuCdList;
	}

	/**
	 * ���ǎ�ʃR�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	���ǎ�ʃR�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getBukyokuShubetuCdValue(String value)
			throws ApplicationException {		
		return getLabel(getBukyokuShubetuCdList(), value);
	}

	//---------------------------------------------------------------------
	// �E��i�E���j���X�g
	//---------------------------------------------------------------------
	/**
	 * �E��i�E���j���X�g���擾����B�i�\�������͗p�j
	 * @return	�E��i�E���j���X�g
     * @throws ApplicationException
	 */
	public static List getShokushuCdList()
			throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List shokushuList = servise.getShokushuList();
		return shokushuList;
	}

	/**
	 * �E��R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	 �E��R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getShokushuCdValue(String value) 
			throws ApplicationException {		
		return getLabel(getShokushuCdList(), value);
	}

	//---------------------------------------------------------------------
	// ���Ɩ����X�g
	//---------------------------------------------------------------------
	/**
	 * ���Ɩ����X�g���擾����B
	 * @deprecated getJigyoNameList(UserInfo userinfo)���g�p���邱�ƁB
	 * @see getJigyoNameList(UserInfo userinfo)
	 * @return	���Ɩ����X�g
     * @throws ApplicationException
	 */
	public static List getJigyoNameList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List jigyoList = servise.getJigyoNameList();
		return jigyoList;
	}

	/**
	 * ���Ɩ����X�g���擾����B
	 * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
	 * �����̒S�����鎖�Ƃ݂̂̎��Ɩ����X�g���Ԃ�B
	 * @param userInfo
	 * @return
	 * @throws ApplicationException
	 */
	public static List getJigyoNameList(UserInfo userInfo)
		throws ApplicationException {
		//2005.05.13 iso ���Ɩ����X�g�̎擾�́A���Ƌ敪������CD�ɂȂ����̂ňڍs�B
//		//�T�[�o�T�[�r�X�̌Ăяo��
//		ISystemServise servise = SystemServiceFactory.getSystemService(
//									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
//		List jigyoList = servise.getJigyoNameList(userInfo);
//		return jigyoList;
		return getJigyoNameListByJigyoCds(userInfo);
	}

	/**
	 * �w�肳�ꂽ���Ƌ敪�̎��Ɩ����X�g���擾����B
	 * @param userInfo
	 * @param jigyoKubun
	 * @return
	 * @throws ApplicationException
	 */
	public static List getJigyoNameList(UserInfo userInfo, String jigyoKubun)
		throws ApplicationException {
		//2005.05.13 iso ���Ɩ����X�g�̎擾�́A���Ƌ敪������CD�ɂȂ����̂ňڍs�B
//		//�T�[�o�T�[�r�X�̌Ăяo��
//		ISystemServise servise = SystemServiceFactory.getSystemService(
//									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
//		List jigyoList = servise.getJigyoNameList(userInfo, jigyoKubun);
//		return jigyoList;
		return getJigyoNameListByJigyoCds(userInfo, jigyoKubun);
	}

	/**
	 * ���Ɩ����X�g���擾����B
	 * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A�����̒S�����鎖�Ƌ敪�i1�܂���4�̏ꍇ�j�݂̂�
	 * ���Ɩ����X�g���Ԃ�
	 * @param userInfo
	 * @return
	 * @throws ApplicationException
	 */
	public static List getShinsaTaishoJigyoNameList(UserInfo userInfo)
		throws ApplicationException {
//		2005.05.13 iso ���Ɩ����X�g�̎擾�́A���Ƌ敪������CD�ɂȂ����̂ňڍs�B
//		//�T�[�o�T�[�r�X�̌Ăяo��
//		ISystemServise servise = SystemServiceFactory.getSystemService(
//									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
//		
//		List jigyoList = servise.getShinsaTaishoJigyoNameList(userInfo);		
//		return jigyoList;
		return getShinsaTaishoListByJigyoCds(userInfo);
	}

	/**
	 * ���Ɩ��R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	���Ɩ��R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getJigyoNameList(String value) 
		throws ApplicationException {		
		return getLabel(getJigyoNameList(), value);
	}

	//2005/04/27 �ǉ���������-------------------------------------------
	//���R �����̒S�����鎖�ƃR�[�h�ōi�荞�񂾎��Ɩ����擾���邽��
	/**
	 * ���Ɩ����X�g���擾����B
	 * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
	 * �����̒S�����鎖�Ƃ݂̂̎��Ɩ����X�g���Ԃ�
	 * @param userInfo
	 * @return
	 * @throws ApplicationException
	 */
	public static List getJigyoNameListByJigyoCds(UserInfo userInfo)
		throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		
		List jigyoList = servise.getJigyoNameListByJigyoCds(userInfo);		
		return jigyoList;
	}
	//�ǉ� �����܂�---------------------------------------------------------

	/**
	 * ���Ɩ����X�g���擾����B
	 * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
	 * �����̒S�����鎖��CD�E���Ƌ敪�݂̂̎��Ɩ����X�g���Ԃ�
	 * @param userInfo
	 * @param jigyoKubun
	 * @return
	 * @throws ApplicationException
	 */
	public static List getJigyoNameListByJigyoCds(UserInfo userInfo, String jigyoKubun)
			throws ApplicationException {
		
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		
		List jigyoList = servise.getJigyoNameListByJigyoCds(userInfo, jigyoKubun);		
		return jigyoList;
	}
   
//  2006/06/14 �����R�@�ǉ���������------------------------------------------- 
    /**
     * ���Ɩ����X�g���擾����B
     * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
     * �����̒S�����鎖��CD�E���Ƌ敪�݂̂̎��Ɩ����X�g���Ԃ�
     * @param userInfo
     * @param jigyoCd
     * @return
     * @throws ApplicationException
     */
    public static List getJigyoNameListByJigyoCds2(UserInfo userInfo, String jigyoCd)
            throws ApplicationException {
        
        //�T�[�o�T�[�r�X�̌Ăяo��
        ISystemServise servise = SystemServiceFactory.getSystemService(
                                    IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
        String[] validJigyoCds = new String[]{jigyoCd};
        List jigyoList = servise.getJigyoNameListByJigyoCds(userInfo, validJigyoCds, "");      
        return jigyoList;
    }
//  �ǉ� �����܂�---------------------------------------------------------
    
	/**
	 * ���Ɩ����X�g���擾����B
	 * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
	 * �����̒S�����鎖�ƁE�R���S�����鎖�Ƌ敪�i1�܂���4�̏ꍇ�j�݂̂̎��Ɩ����X�g���Ԃ�
	 * @param userInfo
	 * @return
	 * @throws ApplicationException
	 */
	public static List getShinsaTaishoListByJigyoCds(UserInfo userInfo)
            throws ApplicationException {

        // �T�[�o�T�[�r�X�̌Ăяo��
        ISystemServise servise = SystemServiceFactory
                .getSystemService(IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);

        List jigyoList = servise.getShinsaTaishoListByJigyoCds(userInfo);
        return jigyoList;
    }

	//---------------------------------------------------------------------
	// 1���R�����͕���
	//---------------------------------------------------------------------
	/**
	 * 1���R�����͕������擾����B
	 * @return	�Ή��@�֖����X�g
	 */
	public static List getShinsaTypeList() {
        List shinsaTypeList = new ArrayList();
        shinsaTypeList.add(new LabelValueBean("ABC����", "0"));
        shinsaTypeList.add(new LabelValueBean("�_������", "1"));
        shinsaTypeList.add(new LabelValueBean("����", "2"));

        return shinsaTypeList;
    }

	/**
	 * 1���R�����͕����R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	1���R�����͕����R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getShinsaTypeList(String value) 
		throws ApplicationException {		
		return getLabel(getShinsaTypeList(), value);
	}

	//---------------------------------------------------------------------
	// �]���p�t�@�C���L���^�R�����g���L���i�Ȃ��^����j
	//---------------------------------------------------------------------
	/**
	 * �]���p�t�@�C���L���^�R�����g���L�����擾����B
	 * @return	�]���p�t�@�C���L���^�R�����g���L�����X�g
	 */
	public static List getFlgList() {
        List flgList = new ArrayList();
        flgList.add(new LabelValueBean("�Ȃ�", "0"));
        flgList.add(new LabelValueBean("����", "1"));
        return flgList;
    }

	/**
	 * �]���p�t�@�C���L���^�R�����g���L���R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�]���p�t�@�C���L���^�R�����g���L���R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getFlgList(String value) 
		throws ApplicationException {		
		return getLabel(getFlgList(), value);
	}

	//---------------------------------------------------------------------
	// ���ԁ|�J�n���w��Ȃ��t���O�^���ԁ|�I�����w��Ȃ��t���O�i�w��Ȃ��^�w�肠��j
	//---------------------------------------------------------------------
	/**
	 * ���ԁ|�J�n���w��Ȃ��t���O�^���ԁ|�I�����w��Ȃ��t���O���擾����B
	 * @return	���ԁ|�J�n���w��Ȃ��t���O�^���ԁ|�I�����w��Ȃ��t���O���X�g
	 */
	public static List getShiteiFlgList() {
        List flgList = new ArrayList();
        flgList.add(new LabelValueBean("�w��Ȃ�", "0"));
        flgList.add(new LabelValueBean("", "1"));

        return flgList;
    }

	/**
	 * ���ԁ|�J�n���w��Ȃ��t���O�^���ԁ|�I�����w��Ȃ��t���O�R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	���ԁ|�J�n���w��Ȃ��t���O�^���ԁ|�I�����w��Ȃ��t���O�R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getShiteiFlgList(String value) 
		throws ApplicationException {		
		return getLabel(getShiteiFlgList(), value);
	}

	//---------------------------------------------------------------------
	// ���ԁ|�w��t���O�Ȃ��t���O�i�w��Ȃ��^�w�肠��i�N���j�^�w�肠��i���j�j
	//---------------------------------------------------------------------
	/**
	 * ���ԁ|�w��t���O�Ȃ��t���O���擾����B
	 * @return	���ԁ|�w��t���O�Ȃ��t���O���X�g
	 */
	public static List getKikanFlgList() {
        List flgList = new ArrayList();
        flgList.add(new LabelValueBean("�w��Ȃ�", "0"));
        flgList.add(new LabelValueBean("", "1"));
        flgList.add(new LabelValueBean("", "2"));
        return flgList;
    }

	/**
	 * ���ԁ|�J�n���w��Ȃ��t���O�^���ԁ|�I�����w��Ȃ��t���O�R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	���ԁ|�J�n���w��Ȃ��t���O�^���ԁ|�I�����w��Ȃ��t���O�R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getKikanFlgList(String value) 
		throws ApplicationException {		
		return getLabel(getKikanFlgList(), value);
	}

	//---------------------------------------------------------------------
	// �Ώ�
	//---------------------------------------------------------------------
	/**
	 * �Ώۂ��擾����B
	 * @return	�Ώۃ��X�g
	 */
	public static List getTaishoIdList() {
		List taishoIdList = new ArrayList();
		taishoIdList.add(new LabelValueBean("�����@�֌���"   , "2"));
		taishoIdList.add(new LabelValueBean("�R��������" , "4"));
		//taishoIdList.add(new LabelValueBean("�Z���^�[����������" , "5"));
		
		return taishoIdList;
	}

	/**
	 * �ΏۃR�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�ΏۃR�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getTaishoIdList(String value) 
		throws ApplicationException {		
		return getLabel(getTaishoIdList(), value);
	}

//	2005/04/08 �ǉ� ��������----------------------------
//  ���R�F�����Ώۂ̗ތ^�ǉ��̂���
	//---------------------------------------------------------------------
	// �����Ώۂ̗ތ^
	//---------------------------------------------------------------------
	/**
	 * �����Ώۂ̗ތ^���擾����B
	 * @return	�V�K�E�p�����X�g
	 */
	public static List getKenkyuTaishoList() {
		List kenkyuTaishoList = new ArrayList();
// 20050712 ��撲���̌����Ώۗތ^��ύX
//		kenkyuTaishoList.add(new LabelValueBean("1", "1"));
//		kenkyuTaishoList.add(new LabelValueBean("2", "2"));
//		kenkyuTaishoList.add(new LabelValueBean("3", "3"));
		kenkyuTaishoList.add(new LabelValueBean("1�F�u����̈挤���v�̐V�K���������̈�Ƃ��ĉ��傷�邽�߂̏�������", "1"));
		kenkyuTaishoList.add(new LabelValueBean("2�F�w�p�U����K�v���̍������������i���ۋ����������܂ށB�j���̊��", "2"));
		kenkyuTaishoList.add(new LabelValueBean("3�F���{�ł̊J�Â��\�肳��Ă��鍑�ی����W��̌������e�ʂɊւ����擙�̏���", "3"));
// Horikoshi
		return kenkyuTaishoList;
	}

	/**
	 * �����ΏۃR�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�V�K�E�p���R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getKenkyuTaishoList(String value) 
		throws ApplicationException {		
		return getLabel(getKenkyuTaishoList(), value);
	}
//	�ǉ��@�����܂�--------------------------------------
	
//	2005/04/18 �ǉ� ��������----------------------------
//  ���R�F�C�O����ǉ��̂���
	//---------------------------------------------------------------------
	// �C�O���샊�X�g
	//---------------------------------------------------------------------

	/**
	 * �C�O���샊�X�g���擾����B
	 * @return	�C�O���샊�X�g
	 * @throws ApplicationException
	 */
	public static List getKaigaiBunyaList()
		throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		
		List jigyoList = servise.getKaigaiBunyaList();		
		return jigyoList;
	}

	/**
	 * �C�O����R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	���Ɩ��R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getKaigaiBunyaList(String value) 
		throws ApplicationException {		
		return getLabel(getKaigaiBunyaList(), value);
	}

//	�ǉ��@�����܂�--------------------------------------
	//---------------------------------------------------------------------
	// �V�K�E�p���i�R�������j
	//---------------------------------------------------------------------
	/**
	 * �V�K�E�p�����擾����B
	 * @return	�V�K�E�p�����X�g
	 */
	public static List getSinkiKeizokuFlgList(){
		return getSinkiKeizokuFlgList(false);
	}

    /**
     * �V�K�E�p�����擾����B
     * @param tokuteiFlg
     * @return  �V�K�E�p�����X�g
     */
	public static List getSinkiKeizokuFlgList(boolean tokuteiFlg) {
        List sinkiKeizokuFlgList = new ArrayList();
        sinkiKeizokuFlgList.add(new LabelValueBean("�V�K", "1"));
        sinkiKeizokuFlgList.add(new LabelValueBean("�p��", "2"));
        if (tokuteiFlg) {
            sinkiKeizokuFlgList.add(new LabelValueBean("�啝�ȕύX(����̈挤��)", "3"));
        }

        return sinkiKeizokuFlgList;
    }

	/**
	 * �V�K�E�p���R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�V�K�E�p���R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getSinkiKeizokuFlgList(String value) 
		throws ApplicationException {		
		return getLabel(getSinkiKeizokuFlgList(), value);
	}

	//---------------------------------------------------------------------
	// �Ӌ��i�R�������j
	//---------------------------------------------------------------------
	/**
	 * �Ӌ����擾����B
	 * @return	�Ӌ����X�g
	 */
	public static List getShakinList() {
        List shakinList = new ArrayList();
        shakinList.add(new LabelValueBean("�x����", "1"));
        shakinList.add(new LabelValueBean("����", "2"));
        shakinList.add(new LabelValueBean("�Y���Ȃ�", "3"));

        return shakinList;
    }

	/**
	 * �Ӌ��R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�Ӌ��R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getShakinList(String value) 
		throws ApplicationException {		
		return getLabel(getShakinList(), value);
	}

	//---------------------------------------------------------------------
	// �\���󋵁i�����@�ցE�\����񌟍��j
	//---------------------------------------------------------------------
	/**
	 * �\���󋵂��擾����B
	 * @return	�\����
	 */
	public static List getJokyoList(){
		List shakinList = new ArrayList();
		shakinList.add(new LabelValueBean("�@", "0"));
		//2005.08.19 iso �����C��
//		shakinList.add(new LabelValueBean("�\���Җ��m�F", "1"));
		shakinList.add(new LabelValueBean("����Җ��m�F", "1"));
		shakinList.add(new LabelValueBean("�����@�֎�t��", "2"));
//       2006/7/31 zjp�@�ǉ��@�u11�A12�A13�A14�v ��������----------------------
        shakinList.add(new LabelValueBean("�̈��\�Ҋm�F��", "11"));
        shakinList.add(new LabelValueBean("�̈��\�Ҋm��ς�", "12"));
        shakinList.add(new LabelValueBean("�̈��\�ҋp��", "13"));
        shakinList.add(new LabelValueBean("�̈��\�ҏ��������@�֎�t��", "14"));
//      2006/7/31 zjp�@�ǉ��@�u11�A12�A13�A14�v�@�����܂�----------------------       
		shakinList.add(new LabelValueBean("�w�U��t��", "3"));
		shakinList.add(new LabelValueBean("�w�U��", "4"));
		shakinList.add(new LabelValueBean("�s��", "8"));
//		shakinList.add(new LabelValueBean("�̑�", "4"));
//		shakinList.add(new LabelValueBean("�s�̑�", "5"));
//		shakinList.add(new LabelValueBean("�̑����", "6"));
//		shakinList.add(new LabelValueBean("�⌇", "7"));
		shakinList.add(new LabelValueBean("�p��", "9"));
		shakinList.add(new LabelValueBean("�C���˗�", "10"));
			
		return shakinList;
	}

	/**
	 * �\���󋵃R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�\���󋵃R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getJokyoList(String value) 
		throws ApplicationException {		
		return getLabel(getJokyoList(), value);
	}

	//---------------------------------------------------------------------
	// �\���󋵁i�Ɩ��S���ҁE�\����񌟍��j
	//---------------------------------------------------------------------
	/**
	 * �\���󋵂��擾����B
	 * @return	�\����
	 */
	public static List getGyomuJokyoList() {
        List shakinList = new ArrayList();
        shakinList.add(new LabelValueBean("�@", "0"));
        shakinList.add(new LabelValueBean("�󗝑O", "1"));
        shakinList.add(new LabelValueBean("�󗝍ς�", "2"));
        shakinList.add(new LabelValueBean("�s��", "3"));
        shakinList.add(new LabelValueBean("�C���˗�", "4"));
        shakinList.add(new LabelValueBean("�R����", "5"));
        shakinList.add(new LabelValueBean("�̑�", "6"));
        shakinList.add(new LabelValueBean("�s�̑�", "7"));
        shakinList.add(new LabelValueBean("�̗p���", "8"));
        shakinList.add(new LabelValueBean("�⌇", "9"));
        return shakinList;
    }

	/**
	 * �\���󋵃R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�\���󋵃R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getGyomuJokyoList(String value) 
		throws ApplicationException {		
		return getLabel(getGyomuJokyoList(), value);
	}

	//---------------------------------------------------------------------
	// �\���󋵁i�V�X�e���Ǘ��ҁE�\����񌟍��j
	//---------------------------------------------------------------------
	/**
	 * �\���󋵂��擾����B
	 * @return	�\����
	 */
	public static List getSystemJokyoList(){
		List shakinList = new ArrayList();		
		shakinList.add(new LabelValueBean("�@", "0"));
		shakinList.add(new LabelValueBean("�쐬��", "1"));
		//2005.08.19 iso �����C��
//		shakinList.add(new LabelValueBean("�\���Җ��m�F", "2"));
		shakinList.add(new LabelValueBean("����Җ��m�F", "2"));
		shakinList.add(new LabelValueBean("�����@�֎�t��", "3"));
//       2006/7/31 zjp�@�ǉ��@�u11�A12�A13�A14�v ��������----------------------
        shakinList.add(new LabelValueBean("�̈��\�Ҋm�F��", "11"));
        shakinList.add(new LabelValueBean("�̈��\�Ҋm��ς�", "12"));
        shakinList.add(new LabelValueBean("�̈��\�ҋp��", "13"));
        shakinList.add(new LabelValueBean("�̈��\�ҏ��������@�֎�t��", "14"));
//      2006/7/31 zjp�@�ǉ��@�u11�A12�A13�A14�v�@�����܂�----------------------       
		shakinList.add(new LabelValueBean("�󗝑O", "4"));
		shakinList.add(new LabelValueBean("�����@�֋p��", "5"));
		shakinList.add(new LabelValueBean("�󗝍ς�", "6"));
		shakinList.add(new LabelValueBean("�s��", "7"));
		shakinList.add(new LabelValueBean("�C���˗�", "8"));
		shakinList.add(new LabelValueBean("�P���R����", "9"));
		shakinList.add(new LabelValueBean("�Q���R������", "10"));	
		return shakinList;
	}

	/**
	 * �\���󋵃R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�\���󋵃R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getSystemJokyoList(String value) 
		throws ApplicationException {		
		return getLabel(getSystemJokyoList(), value);
	}

	//---------------------------------------------------------------------
	// 2���R�����ʁi�Ɩ��S���ҁE2���R�����ʓo�^�j
	//---------------------------------------------------------------------
	/**
	 * 2���R�����ʂ��擾����B���X�g�p�f�[�^���擾����B���x���敪�ɊY������\�[�g���u0�v�ȊO�̃f�[�^���擾����B
	 * @return	2���R������
     * @throws ApplicationException
	 */
	public static List getShinsaKekka2ndList()
					throws ApplicationException {

	   //�T�[�o�T�[�r�X�̌Ăяo��
	   ISystemServise servise = SystemServiceFactory.getSystemService(
								   IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
	   List kekka2List = servise.getLabelList(ILabelKubun.KEKKA2);
	   return kekka2List;
	}

	/**
	 * 2���R�����ʂ��擾����B�\�[�g�̒l�Ɋ֌W�Ȃ��A���x���敪�ɊY������f�[�^�����ׂĎ擾����B
	 * @return	2���R������
     * @throws ApplicationException
	 */
	public static List getAllShinsaKekka2ndList()
					throws ApplicationException {

	   //�T�[�o�T�[�r�X�̌Ăяo��
	   ISystemServise servise = SystemServiceFactory.getSystemService(
								   IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
	   List kekka2List = servise.getAllLabelList(ILabelKubun.KEKKA2);
	   return kekka2List;
	}

	/**
	 * 2���R�����ʃR�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	2���R�����ʃR�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getShinsaKekka2ndList(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KEKKA2, value);
		String labelName = (String) recordMap.get("NAME");
		return labelName;
	}

	//---------------------------------------------------------------------
	// �󗝌���
	//---------------------------------------------------------------------
	/**
	 * �󗝌��ʂ��擾����B
	 * @return	�󗝌���
     * @throws ApplicationException
	 */
	public static List getJuriKekkaList() throws ApplicationException {
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List juriKekkaList = servise.getLabelList(ILabelKubun.JURI_KEKKA);
		return juriKekkaList;
	}

	/**
	 * �󗝌��ʃR�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�󗝌��ʃR�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getJuriKekkaList(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.JURI_KEKKA, value);
		String labelName = (String) recordMap.get("NAME");
		return labelName;
	}

	//---------------------------------------------------------------------
	// �\���I���i�Ɩ��S���Ҍ����E�\����񌟍��j
	//---------------------------------------------------------------------
	/**
	 * �\����񌟍��̕\���I�����X�g���擾����B
	 * @return	�\���I�����X�g
	 */
	public static List getShinseiHyojiSentakuList(){
		List hyojiSentakuList = new ArrayList();
		hyojiSentakuList.add(new LabelValueBean("������ږ��ɕ\��", "0"));
		//2005.08.19 iso �����C��
//		hyojiSentakuList.add(new LabelValueBean("�\���Җ��ɕ\��", "1"));	
		hyojiSentakuList.add(new LabelValueBean("����Җ��ɕ\��", "1"));			
		hyojiSentakuList.add(new LabelValueBean("�@�֖��ɕ\��", "2"));
		hyojiSentakuList.add(new LabelValueBean("�n���̋敪���ɕ\��", "3"));
		hyojiSentakuList.add(new LabelValueBean("���E�̊ϓ_���ɕ\��", "4"));
		hyojiSentakuList.add(new LabelValueBean("�����ԍ�(�w�n�p)���ɕ\��", "5"));//2005/07/25 �ǉ�
		return hyojiSentakuList;
	}

	/**
	 * �\���I���Ɉ�v����\�����̂��擾����B
	 * @param value	�\���I��
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getShinseiHyojiSentakuList(String value) 
		throws ApplicationException {		
		return getLabel(getShinseiHyojiSentakuList(), value);
	}

	//---------------------------------------------------------------------
	// ����
	//---------------------------------------------------------------------	
	/**
	 * ���X�g���A�I�����ꂽ�l�ɑ΂���\�����x�����擾����B
	 * form�ɐݒ肳�ꂽ�R�[�h�����A�I�����炽��������擾����B
	 * @param aList	LabelValueBean�̃��X�g
	 * @param value	�I�����ꂽ�l�������x��
	 * @return
	 */
	public static String getLabel(List aList, String value) {
		for (Iterator iter = aList.iterator(); iter.hasNext();) {
			LabelValueBean element = (LabelValueBean) iter.next();
			if (element.getValue().equals(value)) {
				return element.getLabel();
			}
		}
		return "";
	}

	//---------------------------------------------------------------------
	// �\�������i�]���ꗗ�����j
	//---------------------------------------------------------------------
//	/**
//	 * �\���������擾����B
//	 * @return	�\���������X�g
//	 */
//	public static List getHyokaHyojiList()
//	{
//		List hyokaHyojiList = new ArrayList();
//		hyokaHyojiList.add(new LabelValueBean("�]�����ꗗ", "1"));
//		hyokaHyojiList.add(new LabelValueBean("�R�����g�ꗗ", "2"));
//		
//		return hyokaHyojiList;
//	}
//
//	/**
//	 * �\�������R�[�h�Ɉ�v����\�����̂��擾����B
//	 * @param value	�\�������R�[�h
//	 * @return	�\������
//	 */
//	public static String getHyokaHyojiList(String value) 
//		throws ApplicationException {		
//		return getLabel(getHyokaHyojiList(), value);
//	}

	/**
	 * �\���������擾����B
	 * @return	�\���������X�g
	 */
	public static List getHyokaHyojiList() {
        List hyokaHyojiList = new ArrayList();
        hyokaHyojiList.add(new LabelValueBean("�_����", "1"));
        hyokaHyojiList.add(new LabelValueBean("A�̑�����", "2"));

        return hyokaHyojiList;
    }

	/**
	 * �\�������R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�\�������R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getHyokaHyojiList(String value) 
		throws ApplicationException {		
		return getLabel(getHyokaHyojiList(), value);
	}

	//---------------------------------------------------------------------
	// �啶���̍��݁i�p�X���[�h���s���[���ݒ�j
	//---------------------------------------------------------------------
	/**
	 * �啶���̍��݂��擾����B
	 * @return	�啶���̍��݃��X�g
	 */
	public static List getCharChk1List() {
		List charChk1 = new ArrayList();
		charChk1.add(new LabelValueBean("����", "0"));
		charChk1.add(new LabelValueBean("�������̂�", "1"));
			
		return charChk1;
	}

	/**
	 * �啶���̍��݂Ɉ�v����\�����̂��擾����B
	 * @param value	�\�������R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getCharChk1List(String value) 
		throws ApplicationException {		
		return getLabel(getCharChk1List(), value);
	}

	//---------------------------------------------------------------------
	// �����̍��݁i�p�X���[�h���s���[���ݒ�j
	//---------------------------------------------------------------------
	/**
	 * �����̍��݂��擾����B
	 * @return	�����̍��݃��X�g
	 */
	public static List getCharChk2List() {
		List charChk2 = new ArrayList();
		charChk2.add(new LabelValueBean("����", "0"));
		charChk2.add(new LabelValueBean("�A���t�@�x�b�g�̂�", "1"));
			
		return charChk2;
	}

	/**
	 * �����̍��݂Ɉ�v����\�����̂��擾����B
	 * @param value	�\�������R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getCharChk2List(String value) 
		throws ApplicationException {		
		return getLabel(getCharChk2List(), value);
	}

	//---------------------------------------------------------------------
	// �V�K�E�X�V�i�}�X�^�捞�j
	//---------------------------------------------------------------------
	/**
	 * �V�K�E�X�V���擾����B
	 * @return	�V�K�E�X�V���X�g
	 */
	public static List getShinkiKoshinFlgList() {
        List sinkiKoshinFlgList = new ArrayList();
        sinkiKoshinFlgList.add(new LabelValueBean("�V�K", "0"));
        sinkiKoshinFlgList.add(new LabelValueBean("�X�V", "1"));

        return sinkiKoshinFlgList;
    }

	/**
	 * �V�K�E�X�V�R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�V�K�E�X�V�R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getShinkiKoshinFlgList(String value) 
		throws ApplicationException {		
		return getLabel(getShinkiKoshinFlgList(), value);
	}

	//---------------------------------------------------------------------
	// ���E�^�񐄑E�i�\���ғo�^�j
	//---------------------------------------------------------------------
	/**
	 * ���E�^�񐄑E���擾����B
	 * @return	���E�^�񐄑E���X�g
	 */
	public static List getHikoboFlgList() {
		List shuninFlgList = new ArrayList();
		shuninFlgList.add(new LabelValueBean("���E", "1"));
		shuninFlgList.add(new LabelValueBean("�񐄑E", "0"));
		
		return shuninFlgList;
	}

	/**
	 * ���E�^�񐄑E�R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	���E�^�񐄑E�R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getHikoboFlgList(String value) 
			throws ApplicationException {		
		return getLabel(getHikoboFlgList(), value);
	}

	/**
	 * �����v��ŏI�N�x�O�N�x�̉��僊�X�g�B
	 * isAsc:true���A���債�Ȃ��^���傷��BisAsc:false���A���傷��^���債�Ȃ��B
     * @param isAsc true:�����Afalse:�~��
	 * @return List
	 */
	public static List getZennendoOboList(boolean isAsc){
		List list = new ArrayList();
		//2005/8/18 by xiang
//		list.add(new LabelValueBean("�Y�����Ȃ�", "2"));
//		list.add(new LabelValueBean("�Y������", "1"));
// 2006/07/24 update start ���R�F��������͉�ʂƉ����񌟍���ʂ̏����͈Ⴄ�ł��B
        if(isAsc){
            list.add(new LabelValueBean("���傷��", "1"));
            list.add(new LabelValueBean("���債�Ȃ�", "2"));
        }else{
            list.add(new LabelValueBean("���債�Ȃ�", "2"));
            list.add(new LabelValueBean("���傷��", "1"));
        }
// 2006/07/24 dyh update end
		return list;
	}

	/**
	 * �����v��ŏI�N�x�O�N�x�̉��僊�X�g(���ʐ��i����)�B
	 * @return List
	 */
	public static List getZennendoOboListTokusui(){
		List list = new ArrayList();
		list.add(new LabelValueBean("�Y�����Ȃ�", "2"));
		list.add(new LabelValueBean("�Y������", "1"));
		return list;
	}
	
	/**
	 * �����v��ŏI�N�x�O�N�x�̉��僊�X�g�ԍ��Ɉ�v����\�����̂��擾����B
	 * @param �����v��ŏI�N�x�O�N�x�̉��僊�X�g�ԍ�
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getZennendoOboList(String value) 
		throws ApplicationException {		
		return getLabel(getZennendoOboList(true), value);
	}

	/**
	 * ���S���̗L�����X�g�B
	 * �L�^��
	 * @return
	 */
	public static List getBuntankinList() {
        List list = new ArrayList();
        list.add(new LabelValueBean("�L", "1"));
        list.add(new LabelValueBean("��", "2"));
        return list;
    }

	//2005/03/28 �ǉ� ------------------------------��������
	//���R �J����]�̗L���ǉ��̂���
	/**
	 * �J����]�̗L�����X�g�B
	 * �L�^��
	 * @return
	 */
	public static List getKaijiKiboList() {
        List list = new ArrayList();
        list.add(new LabelValueBean("�R�����ʂ̊J������]����", "1"));
        list.add(new LabelValueBean("�R�����ʂ̊J������]���Ȃ�", "2"));
        return list;
    }
	//2005/03/28 �ǉ� ------------------------------�����܂�

	//---------------------------------------------------------------------
	// �R����
	//---------------------------------------------------------------------
	/**
	 * �R���󋵂��擾����B
	 * @return	�R���󋵃��X�g
	 */
	public static List getShinsaJokyoList() {

		List shinsaJokyoList = new ArrayList();
		shinsaJokyoList.add(new LabelValueBean("���ׂ�", "2"));
		shinsaJokyoList.add(new LabelValueBean("����", "1"));
		shinsaJokyoList.add(new LabelValueBean("������", "0"));

		return shinsaJokyoList;
	}

	/**
	 * �R���󋵃R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�R���󋵃R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getShinsaJokyoList(String value) 
			throws ApplicationException {		
		return getLabel(getShinsaJokyoList(), value);
	}

//	//---------------------------------------------------------------------
//	// ����剞��t���O
//	//---------------------------------------------------------------------
//	/**
//	 * ����剞��t���O���擾����B
//	 * @return	����剞��t���O���X�g
//	 */
//	public static List getHikoboFlgList()
//		 throws ApplicationException {
//		//�T�[�o�T�[�r�X�̌Ăяo��
//		ISystemServise servise = SystemServiceFactory.getSystemService(
//									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
//		List hikoboFlgList = servise.getLabelList(ILabelKubun.HIKOBO_FLG);
//		return hikoboFlgList;
//	}
//
//	/**
//	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
//	 * @param value	�l
//	 * @return	�\������
//	 */
//	public static String getHikoboFlgValue(String value) 
//		throws ApplicationException {		
//		return getLabel(getHikoboFlgList(), value);
//	}

	//---------------------------------------------------------------------
	// �����]���iABC�j
	//---------------------------------------------------------------------
	/**
	 * �����]���iABC�j���擾����B
	 * @return	�����]���iABC�j���X�g
     * @throws ApplicationException
	 */
	public static List getKekkaAbcList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List sogoHyokaList = servise.getLabelList(ILabelKubun.KEKKA_ABC);
		return sogoHyokaList;
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getKekkaAbcName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KEKKA_ABC, value);
		String labelName = (String) recordMap.get("NAME");
		return labelName;
	}

	//---------------------------------------------------------------------
	// �����]���i�_���j
	//---------------------------------------------------------------------
	/**
	 * �����]���i�_���j���擾����B
	 * @return	�����]���i�_���j���X�g
     * @throws ApplicationException
	 */
	public static List getKekkaTenList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.KEKKA_TEN);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getKekkaTenName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KEKKA_TEN, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �����]���i�_���j�G��
	//---------------------------------------------------------------------
	/**
	 * �����]���i�_���j�G����擾����B
	 * @return	�����]���i�_���j���X�g
     * @throws ApplicationException
	 */
	public static List getKekkaTenHogaList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.KEKKA_TEN_HOGA);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getKekkaTenHogaName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KEKKA_TEN_HOGA, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �������e
	//---------------------------------------------------------------------
	/**
	 * �������e���擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getKenkyuNaiyoList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.KENKYUNAIYO);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getKenkyuNaiyoName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KENKYUNAIYO, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �����v��
	//---------------------------------------------------------------------
	/**
	 * �����v����擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getKenkyuKeikakuList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.KENKYUKEIKAKU);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getKenkyuKeikakuName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KENKYUKEIKAKU, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �K�ؐ�-�C�O
	//---------------------------------------------------------------------
	/**
	 * �K�ؐ�-�C�O���擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getTekisetsuKaigaiList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.TEKISETSU_KAIGAI);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getTekisetsuKaigaiName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.TEKISETSU_KAIGAI, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �K�ؐ�-�����i1�j
	//---------------------------------------------------------------------
	/**
	 * �K�ؐ�-�����i1�j���擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getTekisetsuKenkyu1List()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.TEKISETSU_KENKYU1);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getTekisetsuKenkyu1Name(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.TEKISETSU_KENKYU1, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �K�ؐ�
	//---------------------------------------------------------------------
	/**
	 * �K�ؐ����擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getTekisetsuList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.TEKISETSU);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getTekisetsuName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.TEKISETSU, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �Ó���
	//---------------------------------------------------------------------
	/**
	 * �Ó������擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getDatoList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.DATO);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getDatoName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.DATO, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// ������\��
	//---------------------------------------------------------------------
	/**
	 * ������\�҂��擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getShinseishaList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.SHINSEISHA);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getShinseishaName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.SHINSEISHA, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �������S��
	//---------------------------------------------------------------------
	/**
	 * �������S�҂��擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getKenkyuBuntanshaList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.KENKYUBUNTANSHA);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getKenkyuBuntanshaName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KENKYUBUNTANSHA, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �q�g�Q�m��
	//---------------------------------------------------------------------
	/**
	 * �q�g�Q�m�����擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getHitogenomuList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.HITOGENOMU);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getHitogenomuName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.HITOGENOMU, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// ������
	//---------------------------------------------------------------------
	/**
	 * ��������擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getTokuteiList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.TOKUTEI);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getTokuteiName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.TOKUTEI, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �q�gES�זE
	//---------------------------------------------------------------------
	/**
	 * �q�gES�זE���擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getHitoEsList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.HITOES);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getHitoEsName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.HITOES, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// ��`�q�g��������
	//---------------------------------------------------------------------
	/**
	 * ��`�q�g�����������擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getKumikaeList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.KUMIKAE);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getKumikaeName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KUMIKAE, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// ��`�q���×Տ�����
	//---------------------------------------------------------------------
	/**
	 * ��`�q���×Տ��������擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getChiryoList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.CHIRYO);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getChiryoName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.CHIRYO, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �u�w����
	//---------------------------------------------------------------------
	/**
	 * �u�w�������擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getEkigakuList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.EKIGAKU);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getEkigakuName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.EKIGAKU, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// ���Q�֌W
	//---------------------------------------------------------------------
	/**
	 * ���Q�֌W���擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getRigaiList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.RIGAI);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getRigaiName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.RIGAI, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �w�p�I�d�v���E�Ó���
	//---------------------------------------------------------------------
	/**
	 * �w�p�I�d�v���E�Ó������擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getJuyoseiList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.JUYOSEI);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getJuyoseiName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.JUYOSEI, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �Ƒn���E�v�V��
	//---------------------------------------------------------------------
	/**
	 * �Ƒn���E�v�V�����擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getDokusoseiList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.DOKUSOSEI);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getDokusoseiName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.DOKUSOSEI, value);
		return (String) recordMap.get("NAME");
	}		

	//---------------------------------------------------------------------
	// �g�y���ʁE���Ր�
	//---------------------------------------------------------------------
	/**
	 * �g�y���ʁE���Ր����擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getHakyukokaList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.HAKYUKOKA);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getHakyukokaName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.HAKYUKOKA, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// ���s�\�́E���̓K�ؐ�
	//---------------------------------------------------------------------
	/**
	 * ���s�\�́E���̓K�ؐ����擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getSuikonoryokuList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.SUIKONORYOKU);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getSuikonoryokuName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.SUIKONORYOKU, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// �l���̕ی�E�@�ߓ��̏���
	//---------------------------------------------------------------------
	/**
	 * �l���̕ی�E�@�ߓ��̏�����擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getJinkenList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.JINKEN);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getJinkenName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.JINKEN, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// ���S���z���̏���
	//---------------------------------------------------------------------
	/**
	 * ���S���z�����擾����B
	 * @return	�������e���X�g
     * @throws ApplicationException
	 */
	public static List getBuntankinhaibunList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.BUNTANKIN);
	}

	/**
	 * ���x���敪�E�l�Ɉ�v����\�����̂��擾����B
	 * @param value	�l
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String  getBuntankinhaibunName(String value) 
		throws ApplicationException {		
		//�T�[�o�T�[�r�X�̌Ăяo��		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.BUNTANKIN, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// ���E�̊ϓ_
	//---------------------------------------------------------------------
	/**
	 * ���E�̊ϓ_���擾����B�i�\����񌟍��j
	 * @return	���E�̊ϓ_���X�g
     * @throws ApplicationException
	 */
	public static List getKantenList()
		 throws ApplicationException {
		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List kantenList = servise.getLabelList(ILabelKubun.SUISEN);
		return kantenList;
	}

	//---------------------------------------------------------------------
	// ���Ƌ敪
	//---------------------------------------------------------------------
	/**
	 * ���Ƌ敪�i�R�����j���擾����B�i�܂��g��Ȃ���by�e���j
     * @param userInfo ���[�U���
	 * @return	���Ƌ敪���X�g
     * @throws ApplicationException
	 */
	public static List getJigyoKubunList(UserInfo userInfo)
		 throws ApplicationException {

		List jigyoKubunList = new ArrayList();

		//�Ɩ��S���҂̒S�����Ƌ敪���擾
		Set set = userInfo.getGyomutantoInfo().getTantoJigyoKubun();
		//�w�p�n���i�����j���܂܂�Ă����
		if(set.contains(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
			jigyoKubunList.add(new LabelValueBean("�w�p�n��������", "1"));			
		}
		//��Ռ������܂܂�Ă����
		if(set.contains(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
			jigyoKubunList.add(new LabelValueBean("��Ռ�����", "4"));			
		}
		
		return jigyoKubunList;
	}

	//---------------------------------------------------------------------
	// �\�������i�����@�֌����^�V�X�e���Ǘ��Ҍ����E�\����񌟍��j
	//---------------------------------------------------------------------
	/**
	 * �\�����̕\���������擾����B
	 * @return	�\���������X�g
	 */
	public static List getShinseishoHyojiList() {
		List shinseishoHyojiList = new ArrayList();
		shinseishoHyojiList.add(new LabelValueBean("������ږ��ɕ\��", "1"));
		//2005.08.19 iso �����C��
//		shinseishoHyojiList.add(new LabelValueBean("�\���Җ��ɕ\��", "2"));
		shinseishoHyojiList.add(new LabelValueBean("����Җ��ɕ\��", "2"));
		
		return shinseishoHyojiList;
	}

	/**
	 * �\�������R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�\�������R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getShinseishoHyojiList(String value) 
			throws ApplicationException {		
		return getLabel(getShinseishoHyojiList(), value);
	}

	//---------------------------------------------------------------------
	// �폜�t���O�i�V�X�e���Ǘ��Ҍ����E�\����񌟍��j
	//---------------------------------------------------------------------
	/**
	 * �폜�t���O���擾����B
	 * @return	�\���������X�g
	 */
	public static List getDelFlgList() {
		List deleteFlgList = new ArrayList();
		deleteFlgList.add(new LabelValueBean("�폜�f�[�^������", "1"));
		deleteFlgList.add(new LabelValueBean("�폜�f�[�^���܂�", "2"));
		
		return deleteFlgList;
	}

//	 2005/04/21 �ǉ� ��������--------------------------------------------------
//	 ���R �����@�֏�񌟍��ŕ��ǒS���҂̏������ڒǉ�
	//---------------------------------------------------------------------
	// �폜�t���O�i�V�X�e���Ǘ��Ҍ����E�����@�֏�񌟍��j
	//---------------------------------------------------------------------
	/**
	 * �폜�t���O���擾����B
	 * @return	���������t���O���X�g
	 */
	public static List getBukyokuSearchFlgList() {
        List flgList = new ArrayList();
        flgList.add(new LabelValueBean("���ׂ�", "0"));
        flgList.add(new LabelValueBean("�폜�f�[�^�̂�", "1"));

        return flgList;
    }
//	 �ǉ� �����܂�---------------------------------------------------------------

	//---------------------------------------------------------------------
	// �\�������i��Ռ����]���ꗗ�����j
	//---------------------------------------------------------------------
	/**
	 * �\���������擾����B
	 * @return	�\���������X�g
	 */
	public static List getHyokaHyojiListKiban() {
		List hyokaHyojiListKiban = new ArrayList();
		hyokaHyojiListKiban.add(new LabelValueBean("�]�����ꗗ", "1"));
		hyokaHyojiListKiban.add(new LabelValueBean("�R�����g�ꗗ", "2"));
		
		return hyokaHyojiListKiban;
	}

	/**
	 * �\�������R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�\�������R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getHyokaHyojiListKiban(String value) 
			throws ApplicationException {		
		return getLabel(getHyokaHyojiListKiban(), value);
	}

	/**
	 * �폜�t���O�R�[�h�Ɉ�v����\�����̂��擾����B
	 * @param value	�\�������R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getDelFlgList(String value) 
			throws ApplicationException {		
		return getLabel(getDelFlgList(), value);
	}

	//2005/04/15 �ǉ� ��������----------------------------------------------
	//���R �����ғo�^�Ŏg�p����w�ʂƐ��ʂ̃��X�g��ǉ�
	//---------------------------------------------------------------------
	// �w�ʃ��X�g
	//---------------------------------------------------------------------
	/** 
	 * �w�ʂ��擾����B
	 * @return	�\���������X�g
	 */
	public static List getGakuiList() {
		List deleteFlgList = new ArrayList();
		deleteFlgList.add(new LabelValueBean("�C�m", "10"));
		deleteFlgList.add(new LabelValueBean("���m", "11"));
			
		return deleteFlgList;
	}

	/**
	 * �w�ʂ��擾����B
	 * @param value	�\�������R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getGakuiList(String value) 
		throws ApplicationException {		
			return getLabel(getGakuiList(), value);
	}

	//---------------------------------------------------------------------
	// ���ʃ��X�g
	//---------------------------------------------------------------------
	/** ���ʂ��擾����B
	 * @return	�\���������X�g
	 */
	public static List getSeibetsuList() {
		List deleteFlgList = new ArrayList();
		deleteFlgList.add(new LabelValueBean("�j", "1"));
		deleteFlgList.add(new LabelValueBean("��", "2"));
			
		return deleteFlgList;
	}

	/**
	 * ���ʂ��擾����B
	 * @param value	�\�������R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getSeibetsuList(String value) 
		throws ApplicationException {		
			return getLabel(getSeibetsuList(), value);
	}
	//�ǉ� �����܂�---------------------------------------------------------
	
	//2005/04/21 �ǉ� ��������----------------------------------------------
	//���R �Ɩ��S���҂̃`�F�b�N���X�g�����Ŏg�p����󗝏󋵂̃��X�g��ǉ�
	/** �󗝏󋵂��擾����B
	 * 
	 * @return	�\���������X�g
	 */
	public static List getJuriJokyoList() {
		List juriList = new ArrayList();
		juriList.add(new LabelValueBean("���ׂ�", "0"));
		juriList.add(new LabelValueBean("�󗝍ς�", "06"));
		juriList.add(new LabelValueBean("�󗝑O", "04"));
		juriList.add(new LabelValueBean("�m�����", "03"));
// 20050719 �s�󗝂�ǉ�
		juriList.add(new LabelValueBean("�s��", "07"));
// Horikoshi
		return juriList;
	}
    
//  2006/06/14 �����R�ǉ� ��������----------------------------------------------
    /** �󗝏󋵂��擾����B
     * 
     * @return  �\���������X�g
     */
    public static List getJuriJokyoList2() {
        List juriList = new ArrayList();
        juriList.add(new LabelValueBean("���ׂ�", "0"));
        juriList.add(new LabelValueBean("�󗝍ς�", "06"));
        juriList.add(new LabelValueBean("�󗝑O", "04"));
        juriList.add(new LabelValueBean("���F����", "03"));
        
        return juriList;
    }
//  2006/06/14 �ǉ� �����܂�----------------------------------------------
    
	/**
	 * �󗝏󋵂��擾����B
	 * @param value	�\�������R�[�h
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getJuriJokyoList(String value)
            throws ApplicationException {		
        return getLabel(getJuriJokyoList(), value);
	}
	//�ǉ� �����܂�---------------------------------------------------------

// 20050526 Start ����̈�̂��߂ɒǉ�
	//�����̈�敪�@��
    /**
     * �����̈�敪(����̈�)�Ɉ�v����\�����̂��擾����B
     * @param value�@    �����̈�敪
     * @return  �\������
     * @throws ApplicationException
     */
	public static List getKenkyuKubunList() throws ApplicationException {

        List kenkyuKubunList = new ArrayList();
        kenkyuKubunList.add(new LabelValueBean(IShinseiMaintenance.NAME_KEIKAKU,
                IShinseiMaintenance.KUBUN_KEIKAKU));
        kenkyuKubunList.add(new LabelValueBean(IShinseiMaintenance.NAME_KOUBO,
                IShinseiMaintenance.KUBUN_KOUBO));
        kenkyuKubunList.add(new LabelValueBean(IShinseiMaintenance.NAME_SHUURYOU,
                IShinseiMaintenance.KUBUN_SHUURYOU));

        return kenkyuKubunList;
    }

	/**
	 * �����̈�敪�Ɉ�v����\�����̂��擾����B
     * @param value �����̈�敪
	 * @return	�\������
     * @throws ApplicationException
	 */
	public static String getKenkyuKubunList(String value) 
            throws ApplicationException {
		return getLabel(getKenkyuKubunList(), value);
	}
// Horikoshi End

// 20050713 �󗝁A�s�󗝋@�\�̂��߂ɒǉ�
    /**
     * �󗝁A�s�󗝋@�\�̂��߂ɒǉ�
     * @return  List
     * @throws ApplicationException
     */
	public static List getJuriFujuriList(){
		List lstJuriFujuri = new ArrayList();
		lstJuriFujuri.add(new LabelValueBean("��",	"06"));
		lstJuriFujuri.add(new LabelValueBean("�s��", "07"));
		return lstJuriFujuri;
	}
// Horikoshi

	/**
	 * �����ǂ̃��X�g�B
	 * �L�^��
	 * @return
	 */
	public static List getChouseiList() {
        List list = new ArrayList();
        list.add(new LabelValueBean("������", "1"));
        return list;
    }

//	�ŏI���O�C������ǉ�
    /**
     * �ŏI���O�C�����̗L�����X�g�B
     * �L�^��
     * 
     * @return
     */
    public static List getLoginDateList() {
        List list = new ArrayList();
        list.add(new LabelValueBean("�L", "1"));
        list.add(new LabelValueBean("��", "2"));
        return list;
    }

//	2005/10/25 �ǉ� ��������--------------------------------------------------
	//---------------------------------------------------------------------
	// ���Q�֌W��
	//---------------------------------------------------------------------
	/**
	 * ���Q�֌W�҂��擾����B
	 * @return	���Q�֌W�҃��X�g
	 */
	public static List getRigaiKankeishaList() {

		List rigaiKankeishaList = new ArrayList();
		rigaiKankeishaList.add(new LabelValueBean("���Q�֌W�҂̂�", "1"));
		rigaiKankeishaList.add(new LabelValueBean("���Q�֌W�҂�����", "0"));

		return rigaiKankeishaList;
	}
	//�ǉ� �����܂�---------------------------------------------------------

//	2005/10/27 �ǉ� ��������--------------------------------------------------
	//---------------------------------------------------------------------
	// ���[���t���O
	//---------------------------------------------------------------------
	/**
	 * ���[���t���O���擾����B
	 * @return	���[���t���O���X�g
	 */
	public static List getMailFlgList() {

		List mailFlgList = new ArrayList();
		mailFlgList.add(new LabelValueBean("�o�^����", "0"));
		mailFlgList.add(new LabelValueBean("�o�^���Ȃ�", "1"));

		return mailFlgList;
	}
	//�ǉ� �����܂�---------------------------------------------------------

//�R���󋵌����u�\�������v��ǉ�	2005/11/1
    //---------------------------------------------------------------------
    // �\�������i�R���󋵈ꗗ�����j
    //---------------------------------------------------------------------
    /**
     * �\���������擾����B
     * 
     * @return �\���������X�g
     */
    public static List getHyokaHyojiListShinsaJokyo() {
        List hyokaHyojiListShinsaJokyo = new ArrayList();
        hyokaHyojiListShinsaJokyo.add(new LabelValueBean("�R���󋵈ꗗ", "1"));
        hyokaHyojiListShinsaJokyo.add(new LabelValueBean("�R�������ꗗ", "2"));

        return hyokaHyojiListShinsaJokyo;
    }

    /**
     * �\�������R�[�h�Ɉ�v����\�����̂��擾����B
     * 
     * @param value �\�������R�[�h
     * @return �\������
     * @throws ApplicationException
     */
    public static String getHyokaHyojiListShinsaJokyo(String value)
            throws ApplicationException {
        return getLabel(getHyokaHyojiListShinsaJokyo(), value);
    }

	/**
	 * �l�Ɉ�v���郉�x�����擾����B
	 * �l�Ɉ�v���郉�x�����Ȃ��ꍇ�A�󕶎���Ԃ��B
	 * @param labelList	���x�����X�g
	 * @param atai	�l
	 * @return	�\�����x��
     * @throws ApplicationException
	 */
	public static String getlabelName(List labelList, String atai)
			throws ApplicationException {
		String labelName = "";
		for(Iterator itaretor = labelList.iterator(); itaretor.hasNext();) {
			LabelValueBean labelValueBean = (LabelValueBean)itaretor.next();
			if(!StringUtil.isBlank(atai) && atai.equals(labelValueBean.getValue())) {
				labelName = labelValueBean.getLabel();
			}
		}
		return labelName;
	}

	/**
	 * �l���X�g�Ɉ�v���郉�x�����擾����B
	 * �l�Ɉ�v���郉�x�����Ȃ��ꍇ�A�󕶎���Ԃ��B
	 * �������s���̏ꍇ�A�󃊃X�g��Ԃ��B
	 * @param labelList	���x�����X�g
	 * @param values	�l���X�g
	 * @return	�\�����x�����X�g
     * @throws ApplicationException
	 */
	public static List getlabelName(List labelList, List values)
			throws ApplicationException {
		return getlabelName(labelList, values, null, null);
	}

	/**
	 * �l���X�g�Ɉ�v���郉�x�����擾����B
	 * �l���X�g�ő�3����(���̑��R�[�h)��v����l������ꍇ�A��4������Ԃ��B
	 * �l�Ɉ�v���郉�x�����Ȃ��ꍇ�A�󕶎���Ԃ��B
	 * �������s���̏ꍇ�A�󃊃X�g��Ԃ��B
	 * @param labelList	���x�����X�g
	 * @param values	�l���X�g
     * @param other 
     * @param otherValue 
	 * @return	�\�����x�����X�g
     * @throws ApplicationException
	 */
	public static List getlabelName(List labelList, List values, String other, String otherValue)
			throws ApplicationException {
		List labelNameList = new ArrayList();
		if(labelList.isEmpty() || values.isEmpty()) {
			return labelNameList;
		}
		for(Iterator iterator = values.iterator(); iterator.hasNext();) {
			String value = iterator.next().toString();
			if(!StringUtil.isBlank(other) && other.equals(value)) {
				labelNameList.add(getlabelName(labelList, value, other, otherValue.replaceAll("�@", "").trim()));
			} else {
				labelNameList.add(getlabelName(labelList, value));
			}
		}
		return labelNameList;
	}

	/**
	 * �l�Ɉ�v���郉�x�����擾����B
	 * ��2��������3����(���̑��R�[�h)�ƈ�v�����ꍇ�́A��4������Ԃ��B
	 * �l�Ɉ�v���郉�x�����Ȃ��ꍇ�A�󕶎���Ԃ��B
	 * @param labelList	���x�����X�g
	 * @param atai	�l
	 * @param other	���̑��R�[�h
     * @param otherValue
	 * @param value	���̑��̎��̕Ԃ�l
	 * @return	�\�����x��
     * @throws ApplicationException
	 */
	public static String getlabelName(List labelList, String atai, String other, String otherValue)
			throws ApplicationException {
		if(!StringUtil.isBlank(atai) && !StringUtil.isBlank(other)
				&& atai.equals(other)) {
			return StringUtil.defaultString(otherValue.replaceAll("�@", "").trim());
		} else {
			return getlabelName(labelList, atai);
		}
	}
//2007/02/08 �c�@�폜��������@�g�p���Ȃ�
//	2006/02/15  Start
//	/**
//	 * �R����]������擾����B
//	 * @return	�R����]����
//     * @throws ApplicationException
//	 */
//	public static List getKiboRyoikiList() throws ApplicationException {
//        // �T�[�o�T�[�r�X�̌Ăяo��
//        ISystemServise servise = SystemServiceFactory
//                .getSystemService(IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
//
//        List jigyoList = servise.getKiboRyoikiList();
//        return jigyoList;
//    }

//    /**
//     * �R����]����R�[�h�Ɉ�v����\�����̂��擾����B
//     * @param value	���Ɩ��R�[�h
//     * @return	�\������
//     * @throws ApplicationException
//     */
//    public static String getKiboRyoikiList(String value) 
//    	throws ApplicationException {		
//    	return getLabel(getKiboRyoikiList(), value);
//    }
// syuu  End
//2007/02/08 �c�@�폜�����܂Ł@�g�p���Ȃ�

//2006/06/02 �c�@�ǉ���������
    /**
     * ���Ɩ����X�g�i���Ɓi���S,A,B�j�̍폜�j���擾����B
     * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
     * �����̒S�����鎖��CD�E���Ƌ敪�݂̂̎��Ɩ����X�g���Ԃ�
     * @param userInfo
     * @param jigyoKubun
     * @return
     * @throws ApplicationException
     */
    public static List getJigyoNameListByJigyoCdsWithoutKikanSAB(UserInfo userInfo, String jigyoKubun)
            throws ApplicationException {
        
        //�T�[�o�T�[�r�X�̌Ăяo��
        ISystemServise servise = SystemServiceFactory.getSystemService(
                                    IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
        // ���S�d�q����Ղ̎���CD�̊i�[�i���S,A,B�Ȃ��j
        String[] jigyoCdsKikan = {
                IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN,
                IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU, 
                //DEL START 2007/07/02 BIS�@����
                //������ږ��Ɋ��S�d�q�����Ɓi����i�p���j�A�G��j��ǉ�
                //H19���S�d�q���J�X�^�}�C�Y�Ή�
                //IJigyoCd.JIGYO_CD_KIBAN_HOGA,//�G�茤��
                //DEL end 2007/07/02 BIS�@����
                IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A,
                IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B};
        List jigyoList = servise.getJigyoNameListByJigyoCds(userInfo, jigyoCdsKikan, jigyoKubun);      
        return jigyoList;
    }

    //2006/06/06 jzx�@add start
//  /**
//   * ���Ɩ����X�g���擾����B
//   * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
//   * �����̒S�����鎖�Ƃ݂̂̎��Ɩ����X�g���Ԃ�B
//   * @param userInfo
//   * @return
//   * @throws ApplicationException
//   */
//  public static List getJigyoNameList2(UserInfo userInfo)
//      throws ApplicationException {
//      return getJigyoNameListByJigyoCds2(userInfo);
//  }

    /**
     * ���Ɩ����X�g���擾����B
     * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
     * �����̒S�����鎖�Ƃ݂̂̎��Ɩ����X�g���Ԃ�
     * @param userInfo
     * @return
     * @throws ApplicationException
     */
    public static List getJigyoNameListByJigyoCds2(UserInfo userInfo)
        throws ApplicationException {
        //�T�[�o�T�[�r�X�̌Ăяo��
        ISystemServise servise = SystemServiceFactory.getSystemService(
                                    IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
        String[] validJigyoCds = new String[]{
                IJigyoCd.JIGYO_CD_TOKUSUI,        //���ʐ��i����
                
               // ADD START 2007/07/02 ����
                //������ږ��Ɋ��S�d�q�����Ɓi����i�p���j�A�G��j��ǉ�
                //H19���S�d�q���J�X�^�}�C�Y�Ή�
                IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU,//����̈挤���i�p���̈�j
                // ADD END 2007/07/02 ����
                IJigyoCd.JIGYO_CD_KIBAN_S,        //��Ռ���(S)
                IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN,  //��Ռ���(A)(���)
                IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI, //��Ռ���(A)(�C�O�w�p����)
                IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN,  //��Ռ���(B)(���)
                IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI, //��Ռ���(B)(�C�O�w�p����)
                // ADD START 2007/07/02 BIS�@����
                //������ږ��Ɋ��S�d�q�����Ɓi����i�p���j�A�G��j��ǉ�
                //H19���S�d�q���J�X�^�}�C�Y�Ή�
                IJigyoCd.JIGYO_CD_KIBAN_HOGA,	  //�G�茤��
                // ADD END 2007/07/02 BIS�@����
//2007/02/07 �c�@�ǉ���������             
                IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S, //��茤��(S)
                IJigyoCd.JIGYO_CD_WAKATESTART,    //��茤���i�X�^�[�g�A�b�v�j
                IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A, //���ʌ������i��i��Ռ���(A)�����j
                IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B, //���ʌ������i��i��Ռ���(B)�����j
                IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C, //���ʌ������i��i��Ռ���(C)�����j
                IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A,//���ʌ������i��i��茤��(A)�����j
                IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B,//���ʌ������i��i��茤��(B)�����j
//2007/02/07�@�c�@�ǉ������܂�
                IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO,//�w�p�n��������i���E���j
                IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO};//�w�p�n��������i��W���j
                
                
                
        List jigyoList = servise.getJigyoNameListByJigyoCds(userInfo,validJigyoCds,"");
        return jigyoList;
    }
    //2006/06/06 jzx�@add end
    
    //2006/06/21 lwj add begin
    /**
     * �����̈�ŏI�N�x�O�N�x�̉���i�Y���̗L���j���擾����B
     * 
     * @return �����̈�ŏI�N�x�O�N�x�̉���i�Y���̗L���j���X�g
     */
    public static List getNenduYomuList() {
        List nenduYomuList = new ArrayList();
        nenduYomuList.add(new LabelValueBean("�L", "1"));
        nenduYomuList.add(new LabelValueBean("��", "2"));

        return nenduYomuList;
    }
    //2006/06/21 lwj add end  
}