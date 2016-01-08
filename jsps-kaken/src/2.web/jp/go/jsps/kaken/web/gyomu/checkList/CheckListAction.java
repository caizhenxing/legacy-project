/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : CheckListAction.java
 *    Description : �`�F�b�N���X�g�A�N�V�����N���X�B
 *
 *    Author      : Admin
 *    Date        : 2005/04/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/12    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �`�F�b�N���X�g�A�N�V�����N���X�B
 * �`�F�b�N���X�g�̏����擾����B
 * 
 * @author masuo_t
 */
public class CheckListAction extends BaseAction {
	
	//�����\������Ԃ��w��
	private static String[] JIGYO_IDS = new String[]{
		StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,		//�����@�֎�t��
		StatusCode.STATUS_GAKUSIN_SHORITYU,				//�w�U��t��
		StatusCode.STATUS_GAKUSIN_JYURI,				//�w�U��
		StatusCode.STATUS_GAKUSIN_FUJYURI,				//�w�U�s��
		StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,	//�R��������U�菈����
		StatusCode.STATUS_WARIFURI_CHECK_KANRYO,		//����U��`�F�b�N����
		StatusCode.STATUS_1st_SHINSATYU,				//�ꎟ�R����
		StatusCode.STATUS_1st_SHINSA_KANRYO,			//�ꎟ�R���F����
		StatusCode.STATUS_2nd_SHINSA_KANRYO				//�񎟐R������
    };

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		CheckListForm checkForm = (CheckListForm)request.getSession().getAttribute("checkListForm");	
		String shozokuCd = checkForm.getShozokuCd();
		if(shozokuCd != null){
			checkInfo.setShozokuCd(shozokuCd);
		}

		//20060216  dhy update
		//checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
		CheckListSearchForm searchForm=(CheckListSearchForm)request.getSession().getAttribute("checkListSearchForm");
		checkInfo.setJigyoKubun(searchForm.getJigyoKbn());
		//20060216  end
// Horikoshi End
		//20060307 �`�F�b�N���X�g�̌����C��
		checkInfo.setSearchJokyoId(JIGYO_IDS);

		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		
		//2005/05/19 �ǉ� ��������--------------------------------------------------
		//���R�@�`�F�b�N���X�g��ʂ̃^�C�g�����擾�̂���
		
		//�^�C�g���\�����̎擾
		Page titleResult = 
					 getSystemServise(
						  IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectTitle(checkInfo);
		//�^�C�g���\�������t�H�[���ɃZ�b�g����
		request.setAttribute(IConstants.TITLE_INFO, titleResult);
		
		//�ǉ� �����܂�-------------------------------------------------------------
		
		
		Page result = null;
		//2005/05/19 �ǉ� ��������--------------------------------------------------
		//���R �f�[�^�����݂��Ȃ��ꍇ��page��EMPTY_PAGE���Z�b�g���邽��try-catch�̒ǉ�
		try{	
			//�o��
			result = 
				getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectListData(
					container.getUserInfo(),
					checkInfo);
		}catch(NoDataFoundException e){
			//0���̃y�[�W�I�u�W�F�N�g�𐶐�
			result = Page.EMPTY_PAGE;
		}
		//�ǉ� �����܂�------------------------------------------------------------	

		//�\���������t�H�[���ɃZ�b�g����B
		request.setAttribute(IConstants.RESULT_INFO, result);
		
		return forwardSuccess(mapping);
	}
}