/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/4/5
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.bukyokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.BukyokuSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ǒS���ҏ�񌟍��A�N�V�����N���X�B
 * ���ǒS���ҏ��ꗗ��ʂ�\������B
 * 
 */
public class SearchListAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		BukyokuSearchInfo searchInfo = new BukyokuSearchInfo();
		searchInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());

		//2005/05/19 �ǉ� ��������--------------------------------------------------
		//���R �f�[�^�����݂��Ȃ��ꍇ��page��EMPTY_PAGE���Z�b�g���邽��try-catch�̒ǉ�
		Page result = null;
		try{		
		//�������s
			result =
				getSystemServise(
					IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).searchBukyokuList(
					container.getUserInfo(),
					searchInfo);
		}catch(NoDataFoundException e){
			//0���̃y�[�W�I�u�W�F�N�g�𐶐�
			result = Page.EMPTY_PAGE;
		}
		//�ǉ� �����܂�------------------------------------------------------------	

		//�o�^���ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,result);

		//�t�H�[�����̏�����
		removeFormBean(mapping,request);

		return forwardSuccess(mapping);
	}
}
