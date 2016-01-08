/*
 * �쐬��: 2005/03/28
 *
 */
package jp.go.jsps.kaken.model;

import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.model.vo.KenkyushaPk;
import jp.go.jsps.kaken.model.vo.KenkyushaSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * �����ҏ��̊Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * @author yoshikawa_h
 *
 */
public interface IKenkyushaMaintenance {
	
	/**
	 * �����ҏ�����������B
	 * 
	 * @param userInfo				���s���郆�[�U���
	 * @param pkInfo				�������郆�[�U���
	 * @return						�擾�������[�U���
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public KenkyushaInfo select(UserInfo userInfo,KenkyushaPk pkInfo) throws ApplicationException;
	
	
	/**
	 * ���o�^�\���ҏ�����������B
	 * @param userInfo				���s���郆�[�U���B
	 * @param searchInfo			�������
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public Page searchUnregist(UserInfo userInfo,ShinseishaSearchInfo searchInfo) throws ApplicationException;

	
	/**
	 * ���o�^�\���ҏ���o�^����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param addInfo			�o�^���
	 * @return						�������ʃy�[�W���
	 * @throws ApplicationException	
	 */
	public void registShinseishaFromKenkyusha(UserInfo userInfo,String[] kenkyuNo) throws ApplicationException;

	//2005/04/15 �ǉ� ��������----------------------------------------------------------------
	//���R �����ҏ��̏����̂���	
	/**
	 * ���̓f�[�^�`�F�b�N�B
	 * @param userInfo UserInfo
	 * @param info KenkyushaInfo
	 * @return �����ҏ���ShinseishaInfo
	 * @throws ApplicationException
	 * @throws ValidationException
	 */
	public KenkyushaInfo validate(UserInfo userInfo, KenkyushaInfo info, String mode) throws ApplicationException, ValidationException;
	
	/**
	 * �����ҏ��̒ǉ��B
	 * @param userInfo UserInfo
	 * @param addInfo KenkyushaInfo
	 * @return �o�^���������ҏ�������KenkyushaInfo
	 * @throws ApplicationException
	 */
	public void insert(UserInfo userInfo, KenkyushaInfo addInfo) throws ApplicationException;
	
	/**
	 * ���������ɍ��������ҏ����擾����B
	 * @param searchInfo	KenkyushaSearchInfo
	 * @return �����ҏ���Page
	 * @throws ApplicationException
	 */
	public Page search(KenkyushaSearchInfo searchInfo) throws ApplicationException;
	
	/**
	 * �����ҏ��̍X�V�B
	 * @param userInfo UserInfo
	 * @param updateInfo KenkyushaInfo
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo, KenkyushaInfo updateInfo) throws ApplicationException;
	
	/**
	 * �����ҏ��̍폜�B
	 * @param userInfo UserInfo
	 * @param deleteInfo KenkyushaInfo
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo, KenkyushaInfo deleteInfo) throws ApplicationException;
	
	/**
	 * �����ҏ��̎擾�B
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo KenkyushaPk
	 * @param lock boolean
	 * @return KenkyushaInfo
	 * @throws ApplicationException
	 */
	public KenkyushaInfo selectKenkyushaData(UserInfo userInfo, KenkyushaPk pkInfo, boolean lock) throws ApplicationException;

	//�ǉ� �����܂�--------------------------------------------------------------------------
	
	/**
	 * �\���҈ꊇ�o�^��ID�p�X���[�h�ʒm���𔭍s����B
	 * @param userInfo
	 * @param kenkyuNo
	 * @return FileResource
	 * @throws ApplicationException
	 */
	public FileResource createTsuchisho(UserInfo userInfo,	String[] kenkyuNo) throws ApplicationException;

	/**
	 * �����҃}�X�^�X�V�����擾����
	 * @return  �X�V��
	 */
	public String GetKenkyushaMeiboUpdateDate(UserInfo userInfo) throws ApplicationException;
	
    /**
     * �����Җ���_�E�����[�h�f�[�^���擾����
     * @param userInfo
     * @return
     * @throws ApplicationException
     */
	public List searchMeiboCsvData(UserInfo userInfo)   throws ApplicationException;
}
