/*======================================================================
 *    SYSTEM      : 
 *    Source name : IIkeninfoMaintenance.java
 *    Description : �ӌ��E�v�]���ɍX�V�����̃C���^�t�F�[�X
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *�@�@2005/05/23    1.0         Xiang Emin     �V�K�쐬
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.IkenInfo;
import jp.go.jsps.kaken.model.vo.IkenSearchInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * @author user1
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */
public interface IIkeninfoMaintenance {

	/**
	 * ���ӌ�����V�K�쐬����B
	 * 
	 * @param addInfo				�쐬���邲�ӌ����B
	 * @return						
	 * @throws ApplicationException	
	 */
	public void insert(IkenInfo addInfo) throws ApplicationException;

	/**
	 * �ӌ�������������B
	 * @param searchInfo           �ӌ������������
	 * @return						�擾�����\�����y�[�W�I�u�W�F�N�g
	 * @throws ApplicationException	
	 * @throws NoDataFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public Page searchIken(IkenSearchInfo searchInfo) 
		throws NoDataFoundException, ApplicationException;
	
	/**
	 * �ӌ����𒊏o����B
	 * @param system_no
	 * @param taisho_id
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public IkenInfo selectIkenDataInfo(String system_no, String taisho_id)
    	throws NoDataFoundException, ApplicationException;

	/**
	 * CSV�o�͗p�̈ӌ�������������B
	 * 
	 * @param searchInfo			��������������B
	 * @return						�擾�����ӌ����
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public List searchCsvData(IkenSearchInfo searchInfo) throws ApplicationException;
}
