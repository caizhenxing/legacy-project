/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchCheckListTokuteiAction.java
 *    Description : �`�F�b�N���X�g�ꗗ�i����̈�j�A�N�V�����N���X
 *
 *    Author      : Admin
 *    Date        : 2005/05/24
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/24    V1.0        Admin          �V�K�쐬
 *    2006/06/19    V1.0        DIS.dyh        �ύX
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �`�F�b�N���X�g�ꗗ�i����̈�j�A�N�V�����N���X�B
 * �`�F�b�N���X�g�ꗗ�i����̈�j��ʂ�\������B
 * 
 */
public class SearchCheckListTokuteiAction extends BaseAction {

	//2005/04/11 �ǉ� ��������--------------------------------------------------
	//��ID�������@�֎�t���ȍ~�̂��̂ɂ��ĕ\������悤�C��

	/** �����@�֎�t��(��ID:03)�ȍ~�̏�ID */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,		//�����@�֎�t��
			StatusCode.STATUS_GAKUSIN_SHORITYU,		//�w�U��t��
//			StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,			//�����@�֋p��
			StatusCode.STATUS_GAKUSIN_JYURI,			//�w�U��
			StatusCode.STATUS_GAKUSIN_FUJYURI,		//�w�U�s��
			StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,		//�R��������U�菈����
			StatusCode.STATUS_WARIFURI_CHECK_KANRYO,			//����U��`�F�b�N����
			StatusCode.STATUS_1st_SHINSATYU,			//�ꎟ�R����
			StatusCode.STATUS_1st_SHINSA_KANRYO,			//�ꎟ�R���F����
			StatusCode.STATUS_2nd_SHINSA_KANRYO				//�񎟐R������
	};
	//�ǉ� �����܂�-------------------------------------------------------------
	
	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());

//// 20050601 Start ���������Ɏ��Ƌ敪��ǉ����������֐��ɕύX
//      checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
//// Horikoshi End
//2006/06/19 dyh add start �����F���j���[�n���l��ύX
        // ���������Ɏ��ƃR�[�h��ݒ�
        checkInfo.setJigyoCd(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU);
//2006/06/19 dyh add end
//      2006/08/18 ���Ƌ敪�ǉ�        
        checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);

		//2005/04/11 �ǉ� ��������--------------------------------------------------
		//��ID�������@�֎�t���ȍ~�̂��̂ɂ��ĕ\������悤�C��
		checkInfo.setSearchJokyoId(JIGYO_IDS);
		//�ǉ� �����܂�-------------------------------------------------------------


		//2005/05/19 �ǉ� ��������--------------------------------------------------
		//���R �f�[�^�����݂��Ȃ��ꍇ��page��EMPTY_PAGE���Z�b�g���邽��try-catch�̒ǉ�
		Page result = null;
		try{
            // �������s
            result = getSystemServise(
                    IServiceName.CHECKLIST_MAINTENANCE_SERVICE)
// 20050701
//                    .selectCheckList(container.getUserInfo(),checkInfo);
                    .selectCheckList(container.getUserInfo(), checkInfo, true);
// Horikoshi
		}catch(NoDataFoundException e){
			//0���̃y�[�W�I�u�W�F�N�g�𐶐�
			result = Page.EMPTY_PAGE;
		}
		//�ǉ� �����܂�------------------------------------------------------------
		
		//�����������t�H�[�����Z�b�g����B
		request.setAttribute(IConstants.RESULT_INFO, result);
		
		return forwardSuccess(mapping);
	}
}