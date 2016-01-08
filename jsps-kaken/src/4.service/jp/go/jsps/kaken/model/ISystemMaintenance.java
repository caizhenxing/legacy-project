/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.MasterKanriInfo;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * �����@�֏����Ǘ����s���C���^�[�t�F�[�X�B
 * 
 */
public interface ISystemMaintenance {
	
	/** �}�X�^��ʁF���ȍזڃ}�X�^ */
	public static final String MASTER_SAIMOKU           = "1";		
	
	/** �}�X�^��ʁF�����@�փ}�X�^ */
	public static final String MASTER_KIKAN             = "2";		
	
	/** �}�X�^��ʁF�E��}�X�^ */
	//public static final String MASTER_SHOKUSHU          = "3";		
	
	/** �}�X�^��ʁF���ǃ}�X�^ */
	public static final String MASTER_BUKYOKU           = "4";		
	
	/** �}�X�^��ʁF�R�����}�X�^�i�w�p�n���j */
	public static final String MASTER_SHINSAIN_GAKUJUTU = "5";		
	
	/** �}�X�^��ʁF�R�����}�X�^�i��Ռ����j */
	public static final String MASTER_SHINSAIN_KIBAN    = "6";
	
	//2005/04/22�@�ǉ� ��������-------------------------------------
	//���R�@�}�X�^��荞�ݗp�Ɍ����҃}�X�^�ƌp���ۑ�}�X�^�̎�ʂ�ǉ�
	
	/** �}�X�^��ʁF�����҃}�X�^ */
	public static final String MASTER_KENKYUSHA = "7";
	
	/** �}�X�^��ʁF�p���ۑ�}�X�^ */
	public static final String MASTER_KEIZOKUKADAI = "8";

	//�ǉ� �����܂�-------------------------------------------------

	/** �}�X�^��ʁF����U�茋�ʏ�� */
	public static final String MASTER_WARIFURIKEKKA     = "9";	

	/** �}�X�^��ʁF�̈�}�X�^ */
	public static final String MASTER_RYOIKI	 = "10";	

	/** �}�X�^��ʁF�L�[���[�h�}�X�^ */
	public static final String MASTER_KEYWORD	 = "11";	
	
	/** �}�X�^��荞�ݎ�ʁF�V�K */
	public static final String MASTER_TORIKOMI_SHINKI   = "0";
	
	/** �}�X�^��荞�ݎ�ʁF�X�V */
	public static final String MASTER_TORIKOMI_KOSHIN   = "1";
	
	

	/**
	 * �}�X�^�Ǘ�������������B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @return						�擾�����}�X�^�Ǘ����
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public List selectList(UserInfo userInfo) 
		throws ApplicationException;
	
	
	/**
	 * CSV�t�@�C�����擾����
	 * @param userInfo ���[�U���
	 * @param masterShubetu�@���[�U���I�������}�X�^�t�@�C���ԍ�
	 * @return�@fileResource�@csv�t�@�C��
	 * @throws ApplicationException�@�A�v���P�[�V�����G���[�����������ꍇ
	 * @throws DataAccessException �f�[�^�x�[�X�A�N�Z�X���ɃG���[�����������ꍇ
	 */
	public FileResource getCsvFileResource(UserInfo userInfo, String masterShubetu) 
		throws ApplicationException;
		
	
	
	/**
	 * �}�X�^����荞�ށB
	 * ��荞�ރ}�X�^�̎�ނ́A��O�����Ŏw�肷��B
	 * �w��ł���}�X�^�͖{�C���^�t�F�[�X�Œ�`���Ă���uMASTER_XXXX�v�̂݁B
	 * ����ȊO�̏ꍇ�͗�O�𔭐�����B
	 * @param userInfo
	 * @param fileRes
	 * @param masterShubetu
	 * @param shinkiKoshinFlg
	 * @return
	 * @throws ApplicationException
	 */
	public MasterKanriInfo torikomimaster(UserInfo userInfo,
										   FileResource fileRes,
										   final String masterShubetu,
										   final String shinkiKoshinFlg)
		throws ApplicationException;
	
	
	/**
	 * �폜����Ă��Ȃ����Ə�񃊃X�g���擾����B
	 * @param userInfo
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public Page selectJigyoList(UserInfo userInfo, SearchInfo searchInfo) 
		throws ApplicationException;
		
	
	/**
	 * �w�莖�ƃf�[�^���폜����B
	 * ���Y���Ƃ̎��Ə��Ǘ��A�\���f�[�^���̍폜�t���O���I���ɂ���B
	 * @param userInfo
	 * @param jigyoPk
	 * @return
	 * @throws ApplicationException
	 */
	public JigyoKanriInfo deleteJigyo(UserInfo userInfo, JigyoKanriPk jigyoPk) 
		throws ApplicationException;
		
	

	
	
	
	
}