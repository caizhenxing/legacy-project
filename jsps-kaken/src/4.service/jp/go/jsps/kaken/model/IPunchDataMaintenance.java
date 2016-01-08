/*
 * �쐬��: 2004/10/19
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
package jp.go.jsps.kaken.model;

import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.PunchDataKanriInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;


/**
 * �p���`�f�[�^�̊Ǘ����s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IPunchDataMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IPunchDataMaintenance {
	
	/**
	 * 
	 * @param userinfo
	 * @return
	 * @throws ApplicationException
	 */
	public List selectList(UserInfo userinfo) throws ApplicationException;

	
	/**
	 * �p���`�f�[�^���擾����
	 * @param userInfo ���[�U���
	 * @throws ApplicationException�@�A�v���P�[�V�����G���[�����������ꍇ
	 * @throws DataAccessException �f�[�^�x�[�X�A�N�Z�X���ɃG���[�����������ꍇ
	 */
	public FileResource getPunchDataResource(UserInfo userInfo, String punchShubetu) 
		throws ApplicationException;
		

	public PunchDataKanriInfo getPunchData (UserInfo userInfo, String punchShubetu) 
		throws ApplicationException;
		
	
}
