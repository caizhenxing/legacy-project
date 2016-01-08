/*
 * �쐬��: 2005/03/24
 *
 */
package jp.go.jsps.kaken.model;

import java.util.List;
import java.util.Set;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.BukyokuSearchInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoPk;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * @author yoshikawa_h
 *
 */
public interface IBukyokutantoMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** �o�^�ς݃t���O�F���o�^�l */
	public static final String REGIST_FLG_YET = "0";
	
	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
	
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
	public boolean changePassword(UserInfo userInfo, BukyokutantoPk pkInfo,String oldPassword ,String newPassword)
		throws ApplicationException,ValidationException;
	
	public BukyokutantoInfo[] select(UserInfo userInfo, BukyokutantoPk pkInfo)
		throws ApplicationException;

	// 2005/04/07 �ǉ���������---------------------------------------------
	// ���R ���ǒS���ҏ��̓o�^�A�X�V�A�폜�A�p�X���[�h�ύX�����ǉ��̂���	
	
	/** 
 	 * ���ǒS���҈ꗗ���̎擾�B
 	 * 
	 * @param		userInfo	���[�U���
	 * @param		info		��������
	 * @return		Page		�y�[�W���
	 * @exception	ApplicationExcepiton
	 */
	public Page searchBukyokuList(UserInfo userInfo, BukyokuSearchInfo info)
		throws ApplicationException;	
	
	/** 
�@	 * ���ǒS���ҏ��̓o�^�B
	 * 
	 * @param		userInfo	���[�U���
	 * @param		info		���ǒS���ҏ��
	 * @return		info		�o�^�f�[�^���i�[�������ǒS���ҏ��
	 * @exception	ApplicationException
	 */		
	public BukyokutantoInfo setBukyokuData(UserInfo userInfo, BukyokutantoInfo info)
		throws ApplicationException;
	
	/** 
	 * ���͂��ꂽ���ǃR�[�h�����ǃ}�X�^�Ɋ܂܂�邩�ǂ������m�F����B
	 *   
	 * @param		userInfo	���[�U���
	 * @param		array		���ǃR�[�h�z��	
	 * @exception	ApplicationException
	 */
	public void CheckBukyokuCd(UserInfo userInfo, Set set)
		throws ApplicationException;
	
	/** 
	 * ���ǒS���ҏ����擾����B
	 * 
	 * @param		userInfo	���[�U���
	 * @param		info		���ǒS���R�[�h�̊i�[���ꂽ���ǒS���ҏ��
	 * @return		info		�o�^�f�[�^���i�[�������ǒS���ҏ��
	 * @exception	ApplicationException
	 */		
	public BukyokutantoInfo selectBukyokuData(UserInfo userInfo, BukyokutantoInfo info)
			throws ApplicationException;
    
    /** 
 	 * ���ǒS���ҏ��폜�B
 	 * 
  	 * @param		userInfo	���[�U���
	 * @param		info		���ǒS���ҏ��
	 * @exception	ApplicationException
 	 */
	public void delete(UserInfo userInfo, BukyokutantoInfo info)
			throws ApplicationException;

    // 2005/04/22 �ǉ� ��������---------------------------------------
    // ���R �����@�֒S���ҍ폜���A�������@�ւɑ����镔�ǒS���ҏ����폜����
    /** 
 	 * ���ǒS���ҏ��폜�B
 	 * 
  	 * @param		userInfo	���[�U���
	 * @param		shozokuCd		���ǒS���ҏ��
	 * @exception	ApplicationException
 	 */
	public void deleteAll(UserInfo userInfo, String shozokuCd)
			throws ApplicationException;
	// �ǉ� �����܂�---------------------------------------------------
	
	/** 
	 * �����S���҂����ǒS���҂̃p�X���[�h��ύX����B
	 * 
	 * @param		userInfo	���[�U���
	 * @param		info		���ǒS���ҏ��
	 * @return		info		�ύX�p�X���[�h���i�[�������ǒS���ҏ��
	 * @exception	ApplicationException
	 */		
	public BukyokutantoInfo changeBukyokuPassword(UserInfo userInfo, BukyokutantoInfo info)
		throws ApplicationException;

	/** 
	 * �����@�֒S���҂̘A����⏊���@�֖������擾����B
	 * 
	 * @param		userInfo	���[�U���
	 * @param		info		�����R�[�h���i�[���ꂽ���ǒS���ҏ��
	 * @return		info		���ǒS���ҏ��
	 * @exception	ApplicationException
	 */
	public BukyokutantoInfo selectShozokuData(UserInfo userInfo, BukyokutantoInfo info)
		throws ApplicationException;

	// �ǉ� �����܂�--------------------------------------------------------
	
	//2005/06/01 �ǉ� ��������----------------------------------------------
	//���R �ؖ������s�pCSV�ɕ��ǒS���ҏ����o�͂��邽��
	
	/**
	 * �ؖ������s�pCSV�f�[�^���擾����.<BR><BR>
	 * 
	 * @param userInfo		UserInfo
	 * @param info			ShozokuInfo
	 * @param list			List
	 * @return	�ؖ������s�pCSV�f�[�^
	 * @throws ApplicationException
	 */
	public List getShomeiCsvData(UserInfo userInfo, ShozokuInfo info, List list)
		throws ApplicationException;
		
	//�ǉ� �����܂�---------------------------------------------------------
}
